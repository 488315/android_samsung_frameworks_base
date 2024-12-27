package com.android.server.display.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public final class PowerThrottlingConfig {
    public BigDecimal brightnessLowestCapAllowed;
    public BigInteger pollingWindowMillis;
    public List powerThrottlingMap;
}
