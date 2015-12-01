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

/**
 *
 * @author Matthew Ring
 */
public class BiasedRandomWalkTickSupplierTest {
    
    public BiasedRandomWalkTickSupplierTest() {
    }

    /**
     * Test of get method, of class BiasedRandomWalkTickSupplier.
     */
    @Test
    public void testGet() {
        
        final Random rng = new Random();
        
        BiasedRandomWalkTickSupplier supplier;
        
        int numTicks = 100;
        EodPoint point = new EodPoint("ABCD", 20150101, 10d, 12d, 8d, 11d, 10000d);
        supplier = 
                new BiasedRandomWalkTickSupplier(
                        point, 
                        numTicks, 
                        rng,
                        "FASDAQ",
                        "MATS",
                        "FASDAQ");
        for (int i = 0; i < numTicks + 3; i++) {
            System.out.println(supplier.get());
        }
        
        System.out.println();
        
        point = new EodPoint("EFGH", 20150101, 10d, 12d, 8d, 9d, 10000d);
        supplier = 
                new BiasedRandomWalkTickSupplier(
                        point, 
                        numTicks, 
                        rng,
                        "MATS",
                        "FASDAQ",
                        "MATS");
        for (int i = 0; i < numTicks + 3; i++) {
            System.out.println(supplier.get());
        }
    }
    
}
