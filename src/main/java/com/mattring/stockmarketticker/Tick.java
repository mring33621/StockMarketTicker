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
public class Tick {

    public final long sequenceNum, timestamp;
    public final String symbol, exchange;
    public final int date;
    public final double bid, ask, last;

    public Tick(long sequenceNum, long timestamp, String symbol, String exchange, int date, double bid, double ask, double last) {
        this.sequenceNum = sequenceNum;
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.exchange = exchange;
        this.date = date;
        this.bid = bid;
        this.ask = ask;
        this.last = last;
    }
    
    public Tick(String symbol, String exchange, int date, double bid, double ask, double last) {
        this(SimpleSequence.next(), System.currentTimeMillis(), symbol, exchange, date, bid, ask, last);
    }
    
    @Override
    public String toString() {
        return "Tick{" + "sequenceNum=" + sequenceNum + ", timestamp=" + timestamp + ", symbol=" + symbol + ", exchange=" + exchange + ", date=" + date + ", bid=" + bid + ", ask=" + ask + ", last=" + last + '}';
    }
    
}
