package com.android.server.display.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public final class DisplayConfiguration {
    public BigInteger ambientLightHorizonLong;
    public BigInteger ambientLightHorizonShort;
    public AutoBrightness autoBrightness;
    public DensityMapping densityMapping;
    public EvenDimmerMode evenDimmer;
    public HdrBrightnessConfig hdrBrightnessConfig;
    public HighBrightnessMode highBrightnessMode;
    public IdleScreenRefreshRateTimeout idleScreenRefreshRateTimeout;
    public SensorDetails lightSensor;
    public LuxThrottling luxThrottling;
    public String name;
    public PowerThrottlingConfig powerThrottlingConfig;
    public List proxSensor;
    public DisplayQuirks quirks;
    public RefreshRateConfigs refreshRate;
    public BigDecimal screenBrightnessCapForWearBedtimeMode;
    public BigDecimal screenBrightnessDefault;
    public NitsMap screenBrightnessMap;
    public BigInteger screenBrightnessRampDecreaseMaxIdleMillis;
    public BigInteger screenBrightnessRampDecreaseMaxMillis;
    public BigDecimal screenBrightnessRampFastDecrease;
    public BigDecimal screenBrightnessRampFastIncrease;
    public BigInteger screenBrightnessRampIncreaseMaxIdleMillis;
    public BigInteger screenBrightnessRampIncreaseMaxMillis;
    public BigDecimal screenBrightnessRampSlowDecrease;
    public BigDecimal screenBrightnessRampSlowDecreaseIdle;
    public BigDecimal screenBrightnessRampSlowIncrease;
    public BigDecimal screenBrightnessRampSlowIncreaseIdle;
    public SensorDetails screenOffBrightnessSensor;
    public IntegerArray screenOffBrightnessSensorValueToLux;
    public Boolean supportsVrr;
    public SensorDetails tempSensor;
    public ThermalThrottling thermalThrottling;
    public UsiVersion usiVersion;
}
