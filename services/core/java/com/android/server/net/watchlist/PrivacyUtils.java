package com.android.server.net.watchlist;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.privacy.DifferentialPrivacyEncoder;
import android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig;
import android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PrivacyUtils {
    public static Map createDpEncodedReportMap(
            boolean z,
            byte[] bArr,
            List list,
            WatchlistReportDbHelper.AggregatedResult aggregatedResult) {
        int size = list.size();
        HashMap hashMap = new HashMap(size);
        for (int i = 0; i < size; i++) {
            String str = (String) list.get(i);
            boolean z2 = true;
            if (((z ? createSecureDPEncoder(bArr, str) : createInsecureDPEncoderForTest(str))
                                    .encodeBoolean(aggregatedResult.appDigestList.contains(str))[0]
                            & 1)
                    != 1) {
                z2 = false;
            }
            hashMap.put(str, Boolean.valueOf(z2));
        }
        return hashMap;
    }

    public static DifferentialPrivacyEncoder createInsecureDPEncoderForTest(String str) {
        return LongitudinalReportingEncoder.createInsecureEncoderForTest(
                new LongitudinalReportingConfig(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "watchlist_encoder:", str),
                        0.469d,
                        0.28d,
                        1.0d));
    }

    public static DifferentialPrivacyEncoder createSecureDPEncoder(byte[] bArr, String str) {
        return LongitudinalReportingEncoder.createEncoder(
                new LongitudinalReportingConfig(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "watchlist_encoder:", str),
                        0.469d,
                        0.28d,
                        1.0d),
                bArr);
    }
}
