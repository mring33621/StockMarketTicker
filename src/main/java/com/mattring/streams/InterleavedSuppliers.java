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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Matthew Ring
 */
public class InterleavedSuppliers<T> implements Supplier<T>{
    private final List<Supplier<T>> suppliers;
    private int pointer;

    public InterleavedSuppliers(Supplier<T>... suppliersArray) {
        suppliers = new ArrayList<>(suppliersArray.length);
        for (int i = 0; i < suppliersArray.length; i++) {
            suppliers.add(suppliersArray[i]);
        }
    }
    
    public InterleavedSuppliers(List<Supplier<T>> suppliers) {
        this.suppliers = new ArrayList<>(suppliers);
    }

    @Override
    public T get() {
        
        T item = null;
                
        while (!suppliers.isEmpty()) {
            
            Supplier<T> currSupplier = suppliers.get(pointer);
            item = currSupplier.get();
            
            if (item == null) {
                suppliers.remove(pointer);
            }
            
            if (++pointer >= suppliers.size()) {
                pointer = 0;
            }
            
            if (item != null) {
                return item;
            }
        }
        return item;
    }
    
}
