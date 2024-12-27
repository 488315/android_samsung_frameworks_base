package com.android.server.display.config;

import java.util.Collections;
import java.util.List;

public final class RefreshRateData {
    public static final RefreshRateData DEFAULT_REFRESH_RATE_DATA = loadRefreshRateData(null, null);
    public final int defaultPeakRefreshRate;
    public final int defaultRefreshRate;
    public final int defaultRefreshRateInHbmHdr;
    public final int defaultRefreshRateInHbmSunlight;
    public final List lowLightBlockingZoneSupportedModes;
    public final List lowPowerSupportedModes;

    public RefreshRateData(int i, int i2, int i3, int i4, List list, List list2) {
        this.defaultRefreshRate = i;
        this.defaultPeakRefreshRate = i2;
        this.defaultRefreshRateInHbmHdr = i3;
        this.defaultRefreshRateInHbmSunlight = i4;
        this.lowPowerSupportedModes = Collections.unmodifiableList(list);
        this.lowLightBlockingZoneSupportedModes = Collections.unmodifiableList(list2);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.RefreshRateData loadRefreshRateData(
            com.android.server.display.config.DisplayConfiguration r9,
            android.content.res.Resources r10) {
        /*
            r0 = 0
            if (r9 != 0) goto L5
            r9 = r0
            goto L7
        L5:
            com.android.server.display.config.RefreshRateConfigs r9 = r9.refreshRate
        L7:
            if (r9 == 0) goto L13
            java.math.BigInteger r1 = r9.defaultRefreshRate
            if (r1 == 0) goto L13
            int r1 = r1.intValue()
        L11:
            r3 = r1
            goto L2c
        L13:
            if (r10 == 0) goto L29
            boolean r1 = com.samsung.android.rune.CoreRune.FW_VRR_POLICY
            if (r1 == 0) goto L21
            r1 = 17695014(0x10e0126, float:2.6082105E-38)
            int r1 = r10.getInteger(r1)
            goto L11
        L21:
            r1 = 17694843(0x10e007b, float:2.6081626E-38)
            int r1 = r10.getInteger(r1)
            goto L11
        L29:
            r1 = 60
            goto L11
        L2c:
            r1 = 0
            if (r9 == 0) goto L39
            java.math.BigInteger r2 = r9.defaultPeakRefreshRate
            if (r2 == 0) goto L39
            int r2 = r2.intValue()
        L37:
            r4 = r2
            goto L50
        L39:
            if (r10 == 0) goto L4f
            boolean r2 = com.samsung.android.rune.CoreRune.FW_VRR_POLICY
            if (r2 == 0) goto L47
            r2 = 17695013(0x10e0125, float:2.6082102E-38)
            int r2 = r10.getInteger(r2)
            goto L37
        L47:
            r2 = 17694841(0x10e0079, float:2.608162E-38)
            int r2 = r10.getInteger(r2)
            goto L37
        L4f:
            r4 = r1
        L50:
            if (r9 == 0) goto L5c
            java.math.BigInteger r2 = r9.defaultRefreshRateInHbmHdr
            if (r2 == 0) goto L5c
            int r2 = r2.intValue()
        L5a:
            r5 = r2
            goto L67
        L5c:
            if (r10 == 0) goto L66
            r2 = 17694844(0x10e007c, float:2.6081628E-38)
            int r2 = r10.getInteger(r2)
            goto L5a
        L66:
            r5 = r1
        L67:
            if (r9 == 0) goto L73
            java.math.BigInteger r2 = r9.defaultRefreshRateInHbmSunlight
            if (r2 == 0) goto L73
            int r10 = r2.intValue()
        L71:
            r6 = r10
            goto L7e
        L73:
            if (r10 == 0) goto L7d
            r1 = 17694845(0x10e007d, float:2.608163E-38)
            int r10 = r10.getInteger(r1)
            goto L71
        L7d:
            r6 = r1
        L7e:
            if (r9 != 0) goto L82
            r10 = r0
            goto L84
        L82:
            com.android.server.display.config.NonNegativeFloatToFloatMap r10 = r9.lowPowerSupportedModes
        L84:
            java.util.List r7 = com.android.server.display.config.SupportedModeData.load(r10)
            if (r9 != 0) goto L8c
            r9 = r0
            goto L8e
        L8c:
            com.android.server.display.config.BlockingZoneConfig r9 = r9.lowerBlockingZoneConfigs
        L8e:
            if (r9 != 0) goto L91
            goto L93
        L91:
            com.android.server.display.config.NonNegativeFloatToFloatMap r0 = r9.supportedModes
        L93:
            java.util.List r8 = com.android.server.display.config.SupportedModeData.load(r0)
            com.android.server.display.config.RefreshRateData r9 = new com.android.server.display.config.RefreshRateData
            r2 = r9
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.config.RefreshRateData.loadRefreshRateData(com.android.server.display.config.DisplayConfiguration,"
                    + " android.content.res.Resources):com.android.server.display.config.RefreshRateData");
    }

    public final String toString() {
        return "RefreshRateData {defaultRefreshRate: "
                + this.defaultRefreshRate
                + ", defaultPeakRefreshRate: "
                + this.defaultPeakRefreshRate
                + ", defaultRefreshRateInHbmHdr: "
                + this.defaultRefreshRateInHbmHdr
                + ", defaultRefreshRateInHbmSunlight: "
                + this.defaultRefreshRateInHbmSunlight
                + ", lowPowerSupportedModes="
                + this.lowPowerSupportedModes
                + ", lowLightBlockingZoneSupportedModes="
                + this.lowLightBlockingZoneSupportedModes
                + "} ";
    }
}
