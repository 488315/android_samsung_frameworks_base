package com.android.server.display.config;

import java.math.BigInteger;

public final class RefreshRateConfigs {
    public BigInteger defaultPeakRefreshRate;
    public BigInteger defaultRefreshRate;
    public BigInteger defaultRefreshRateInHbmHdr;
    public BigInteger defaultRefreshRateInHbmSunlight;
    public BlockingZoneConfig higherBlockingZoneConfigs;
    public NonNegativeFloatToFloatMap lowPowerSupportedModes;
    public BlockingZoneConfig lowerBlockingZoneConfigs;
    public RefreshRateZoneProfiles refreshRateZoneProfiles;
}
