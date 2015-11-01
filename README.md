# StockMarketTicker

Version 0.2.0

ABOUT:
* Generates a finite stream of 'plausable' intraday ticks, based on a given EOD OHLCV data point.
* For demonstration use only. I wouldn't suggest training any trading models against the generated data.
* Java 8 compatible.

USAGE:
* Build it (with Maven)
* Use it:
```Java
FakeTickStreamer fakeTicker = new FakeTickStreamer(new Random());
fakeTicker
        .generate(new EodPoint("ABCD", "FASDAQ", 20150101, 10d, 12d, 8d, 11d, 10000d), 100)
        .forEachOrdered(t -> System.out.println(t));
```

LICENSE:
Apache 2.0 licensed.
