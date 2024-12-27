package com.android.server.display.config;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class HdrBrightnessConfig {
    public BigInteger brightnessDecreaseDebounceMillis;
    public BigInteger brightnessIncreaseDebounceMillis;
    public NonNegativeFloatToFloatMap brightnessMap;
    public BigDecimal screenBrightnessRampDecrease;
    public BigDecimal screenBrightnessRampIncrease;
}
