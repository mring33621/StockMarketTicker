/*
 * Copyright 2015 Matthew.
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
import java.util.stream.Stream;

/**
 *
 * @author Matthew
 */
public class FakeTickStreamer {

    private final Random rng;

    public FakeTickStreamer(Random rng) {
        this.rng = rng;
    }

    public Stream<Tick> generate(EodPoint point, int numTicks) {
        final Supplier<Tick> supplier = new BiasedRandomWalkTickSupplier(point, numTicks, rng);
        final Stream<Tick> stream = Stream.generate(supplier).limit(numTicks);
        return stream;
    }
}
