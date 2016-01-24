/*
 * Copyright 2016 Matthew.
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
package com.mattring.streams;

import java.util.Arrays;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mring
 */
public class SupplierFromIteratorTest {
    
    public SupplierFromIteratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class SupplierFromIterator.
     */
    @Test
    public void testGet() {
        Iterator<String> iter = Arrays.asList("1", "2", "3").iterator();
        SupplierFromIterator<String> uut = new SupplierFromIterator<>(iter);
        String[] results = new String[4];
        for (int i = 0; i < 4; i++) {
            results[i] = uut.get();
            System.out.println(results[i]);
        }
        assertArrayEquals(new String[] {"1", "2", "3", null}, results);
    }
    
}
