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

    private final EodPoint eodPoint;
    private final int numTicks;
    private final Random rng;

    private final int idxFor2ndHalfOfDay;
    private final int idxForFinal3rdOfDay;
    private final int idxForFinal4thOfDay;

    private int tickIdx;
    private Tick prevTick;
    private double bias;

    public BiasedRandomWalkTickSupplier(EodPoint eodPoint, int numTicks, Random rng) {

        this.eodPoint = eodPoint;
        this.numTicks = numTicks;
        this.rng = rng;

        // TODO: configurable time thresholds
        idxFor2ndHalfOfDay = (int) (numTicks * 0.50f);
        idxForFinal3rdOfDay = (int) (numTicks * 0.66f);
        idxForFinal4thOfDay = (int) (numTicks * 0.75f);

        tickIdx = 1;
        prevTick = null;
        bias = 0.50d; // TODO: configurable bias settings
    }

    @Override
    public Tick get() {

        Tick currTick;
        double currVal;

        if (tickIdx == 1) {

            // open
            currTick = new Tick(eodPoint.symbol, eodPoint.exchange, eodPoint.date, null, null, eodPoint.open);

        } else if (tickIdx < numTicks) {

            // return a random-ish walk tick, 
            // with a bias toward the previous direction,
            // or a bias toward the closing value, if later in the trading day
            final double pctChange = rng.nextDouble() * 0.02d; // TODO: make maxPct configurable

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

            currTick = new Tick(eodPoint.symbol, eodPoint.exchange, eodPoint.date, null, null, currVal);

        } else if (tickIdx > numTicks) {

            // no more ticks needed; return null
            currTick = null;

        } else {

            // tickIdx == numTicks; return the closing value
            currTick = new Tick(eodPoint.symbol, eodPoint.exchange, eodPoint.date, null, null, eodPoint.close);

        }

        prevTick = currTick;
        tickIdx++;

        return currTick;
    }

}
