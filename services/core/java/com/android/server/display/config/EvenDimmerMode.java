package com.android.server.display.config;

import java.math.BigDecimal;

public final class EvenDimmerMode {
    public ComprehensiveBrightnessMap brightnessMapping;
    public Boolean enabled;
    public NitsMap luxToMinimumNitsMap;
    public BigDecimal transitionPoint;
}
