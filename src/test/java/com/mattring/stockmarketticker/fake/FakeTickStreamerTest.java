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
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Currently just an eyeball test on stdout.
 * @author Matthew Ring
 */
public class FakeTickStreamerTest {
    
    public FakeTickStreamerTest() {
    }

    /**
     * Test of generate method, of class FakeTickStreamer.
     */
    @Test
    public void testGenerate() {
        FakeTickStreamer fakeTicker = new FakeTickStreamer(new Random());
        fakeTicker
                .generate(new EodPoint("ABCD", 20150101, 10d, 12d, 8d, 11d, 10000d), 100, "FASDAQ", "MATS", "FASDAQ")
                .forEachOrdered(t -> System.out.println(t));
        System.out.println();
        fakeTicker
                .generate(new EodPoint("EFGH", 20150101, 10d, 12d, 8d, 9d, 10000d), 100, "MATS", "FASDAQ", "MATS")
                .forEachOrdered(t -> System.out.println(t));
        assertTrue(true);
    }
    
}
