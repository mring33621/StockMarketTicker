/*
 * Copyright 2015 Matthew Ring.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mattring.stockmarketticker.fake;

import com.mattring.stockmarketticker.EodPoint;
import com.mattring.stockmarketticker.Tick;
import java.util.Random;
import java.util.function.Supplier;

/**
 *
 * @author Matthew Ring
 */
public class BiasedRandomWalkTickSupplier implements Supplier<Tick> {

    private static final double MAX_PCT_CHANGE_PER_TICK = 0.00987d; // TODO: make maxPct configurable

    private final EodPoint eodPoint;
    private final int numTicks;
    private final Random rng;
    private final String[] exchanges;

    private final int idxFor2ndHalfOfDay;
    private final int idxForFinal3rdOfDay;
    private final int idxForFinal4thOfDay;

    private int tickIdx;
    private Tick prevTick;
    private double bias;

    public BiasedRandomWalkTickSupplier(EodPoint eodPoint, int numTicks, Random rng, String... exchanges) {

        this.eodPoint = eodPoint;
        this.numTicks = numTicks;
        this.rng = rng;
        this.exchanges = exchanges;

        // TODO: configurable time thresholds
        idxFor2ndHalfOfDay = (int) (numTicks * 0.50f);
        idxForFinal3rdOfDay = (int) (numTicks * 0.66f);
        idxForFinal4thOfDay = (int) (numTicks * 0.75f);

        tickIdx = 1;
        prevTick = null;
        bias = 0.50d; // TODO: configurable bias settings
    }

    private double pickBiasedMovementBasedOnPrevTick() {

        double currVal;
        // return a random-ish walk tick,
        // with a bias toward the previous direction,
        // or a bias toward the closing value, if later in the trading day
        final double pctChange = rng.nextDouble() * MAX_PCT_CHANGE_PER_TICK;
        //
        // initial directional bias
        //
        if (rng.nextDouble() > bias) {
            // upward movement
            currVal = Math.min((prevTick.last * (1d + pctChange)), eodPoint.high);
            bias = 0.45d; // modestly biased toward another increase
        } else {
            // downward movement
            currVal = Math.max((prevTick.last * (1d - pctChange)), eodPoint.low);
            bias = 0.55d; // modestly biased toward another decrease
        }
        //
        // adj bias if nearing EOD
        //
        if (tickIdx > idxForFinal4thOfDay) {
            // start pushing hard toward closing value
            if (currVal < eodPoint.close) {
                bias = 0.15;
            } else {
                bias = 0.85;
            }
        } else if (tickIdx > idxForFinal3rdOfDay) {
            // start pushing moderately toward closing value
            if (currVal < eodPoint.close) {
                bias = 0.25;
            } else {
                bias = 0.75;
            }
        } else if (tickIdx > idxFor2ndHalfOfDay) {
            // start pushing softly toward closing value
            if (currVal < eodPoint.close) {
                bias = 0.35d;
            } else {
                bias = 0.65d;
            }
        }
        return currVal;
    }

    private double[] pickBidAskBasedOnLast(double last) {
        double pctChange = rng.nextDouble() * MAX_PCT_CHANGE_PER_TICK;
        final double bid = last * (1d - pctChange);
        pctChange = rng.nextDouble() * MAX_PCT_CHANGE_PER_TICK;
        final double ask = last * (1d + pctChange);
        return new double[]{bid, ask};
    }

    @Override
    public Tick get() {

        Tick currTick;

        if (tickIdx <= numTicks) {
            
            final String exchange = exchanges[rng.nextInt(exchanges.length)];

            if (tickIdx == 1) {

                // open
                currTick = new Tick(eodPoint.symbol, exchange, eodPoint.date, eodPoint.open - 0.01d, eodPoint.open + 0.01d, eodPoint.open);

            } else if (tickIdx < numTicks) {

                double currTradeVal = pickBiasedMovementBasedOnPrevTick();
                double[] nextBidAsk = pickBidAskBasedOnLast(currTradeVal);

                currTick = new Tick(eodPoint.symbol, exchange, eodPoint.date, nextBidAsk[0], nextBidAsk[1], currTradeVal);

            } else {

                // tickIdx == numTicks; return the closing value
                currTick = new Tick(eodPoint.symbol, exchange, eodPoint.date, eodPoint.close - 0.01d, eodPoint.close + 0.01d, eodPoint.close);

            }

            prevTick = currTick;
            tickIdx++;

        } else {
            
            // tickIdx > numTicks; no more ticks wanted
            
            currTick = null;
            prevTick = null;
            
        }

        return currTick;
    }

}
