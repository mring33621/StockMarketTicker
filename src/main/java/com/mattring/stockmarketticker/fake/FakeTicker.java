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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Matthew Ring
 */
public class FakeTicker {

    private final Random rng;

    public FakeTicker(Random rng) {
        this.rng = rng;
    }

    public List<Tick> generate(EodPoint point, int numTicks) {
        final int idxFor2ndHalfOfDay = (int) (numTicks * 0.50f);
        final int idxForFinal3rdOfDay = (int) (numTicks * 0.66f);
        final List<Tick> ticks = new ArrayList<>(numTicks);
        final Tick opening = new Tick(point.symbol, point.exchange, point.date, null, null, point.open);
        ticks.add(opening);
        double prev = point.open;
        double bias = 0.50d;
        for (int i = 0; i < numTicks - 2; i++) {
            double pct = rng.nextDouble() * 0.02d;
            double curr;
            if (rng.nextDouble() > bias) {
                // up
                curr = prev * (1d + pct);
                curr = Math.min(curr, point.high);
                bias = 0.45d; // biased toward another increase
            } else {
                // down
                curr = prev * (1d - pct);
                curr = Math.max(curr, point.low);
                bias = 0.55d; // biased toward another decrease
            }
            final Tick currTick = new Tick(point.symbol, point.exchange, point.date, null, null, curr);
            ticks.add(currTick);
            prev = curr;

            if (i > idxForFinal3rdOfDay) {
                // start pushing hard toward close
                if (curr < point.close) {
                    bias = 0.20d;
                } else {
                    bias = 0.80d;
                }
            } else if (i > idxFor2ndHalfOfDay) {
                // start pushing softly toward close
                if (curr < point.close) {
                    bias = 0.35d;
                } else {
                    bias = 0.65d;
                }
            }
        }
        final Tick closing = new Tick(point.symbol, point.exchange, point.date, null, null, point.close);
        ticks.add(closing);
        return ticks;
    }

}
