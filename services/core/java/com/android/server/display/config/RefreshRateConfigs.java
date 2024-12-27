package com.android.server.display.config;

import java.math.BigInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
