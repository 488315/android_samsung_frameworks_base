package com.android.server.display.config;

import java.math.BigDecimal;
import java.math.BigInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HdrBrightnessConfig {
    public BigInteger brightnessDecreaseDebounceMillis;
    public BigInteger brightnessIncreaseDebounceMillis;
    public NonNegativeFloatToFloatMap brightnessMap;
    public BigDecimal screenBrightnessRampDecrease;
    public BigDecimal screenBrightnessRampIncrease;
}
