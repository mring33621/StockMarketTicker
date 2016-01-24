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
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Matthew Ring
 */
public class InterleavedSupplier<T> implements Supplier<T>{
    private final List<Iterator<T>> iters;
    private int pointer;

    public InterleavedSupplier(Stream<T>... streams) {
        iters = new ArrayList<>(streams.length);
        for (int i = 0; i < streams.length; i++) {
            iters.add(streams[i].iterator());
        }
    }
    
    public InterleavedSupplier(List<Stream<T>> streams, boolean dummy) {
        iters = new ArrayList<>(streams.size());
        for (Stream<T> stream : streams) {
            iters.add(stream.iterator());
        }
    }
    
    public InterleavedSupplier(List<List<T>> listOfLists) {
        iters = new ArrayList<>(listOfLists.size());
        for (List<T> list : listOfLists) {
            iters.add(list.iterator());
        }
    }

    @Override
    public T get() {
        T item = null;
        while (!iters.isEmpty()) {
            
            Iterator<T> currIter = iters.get(pointer);
            
            if (currIter.hasNext()) {
                item = currIter.next();
            } else {
                iters.remove(pointer);
            }
            
            if (++pointer >= iters.size()) {
                pointer = 0;
            }
            
            if (item != null) {
                return item;
            }
        }
        return item;
    }
    
}
