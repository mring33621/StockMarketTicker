/*
 * Copyright 2016 Matthew Ring
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
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthew Ring
 */
public class InterleavedSuppliersTest {
    
    public InterleavedSuppliersTest() {
    }

    /**
     * Test of get method, of class InterleavedSupplier.
     */
    @Test
    public void testGet() {
        Supplier<String> s1 = 
                new SupplierFromIterator<>(
                        Arrays.asList("10", "11", "12", "13").iterator());
        Supplier<String> s2 = 
                new SupplierFromIterator<>(
                        Arrays.asList("20", "21", "22").iterator());
        Supplier<String> s3 = 
                new SupplierFromIterator<>(
                        Arrays.asList("30", "31", "32", "33").iterator());
        InterleavedSuppliers<String> uut = new InterleavedSuppliers(s1, s2, s3);
        Stream<String> uutStream = Stream.generate(uut);
        uutStream.limit(11).filter(s -> s != null).forEach(System.out::println);
    }
    
}
