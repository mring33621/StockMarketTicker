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
package com.mattring.stockmarketticker;

/**
 *
 * @author Matthew Ring
 */
public class EodPoint {
    
    public final String symbol, exchange;
    public final int date;
    public final double open, high, low, close, volume;

    public EodPoint(String symbol, String exchange, int date, double open, double high, double low, double close, double volume) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "EodPoint{" + "symbol=" + symbol + ", exchange=" + exchange + ", date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + '}';
    }
    
    
}
