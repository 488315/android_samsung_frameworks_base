package com.android.server.net.watchlist;

import android.privacy.DifferentialPrivacyEncoder;
import android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig;
import android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder;
import com.android.server.net.watchlist.WatchlistReportDbHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class PrivacyUtils {
    public static DifferentialPrivacyEncoder createInsecureDPEncoderForTest(String str) {
        return LongitudinalReportingEncoder.createInsecureEncoderForTest(createLongitudinalReportingConfig(str));
    }

    public static DifferentialPrivacyEncoder createSecureDPEncoder(byte[] bArr, String str) {
        return LongitudinalReportingEncoder.createEncoder(createLongitudinalReportingConfig(str), bArr);
    }

    public static LongitudinalReportingConfig createLongitudinalReportingConfig(String str) {
        return new LongitudinalReportingConfig("watchlist_encoder:" + str, 0.469d, 0.28d, 1.0d);
    }

    public static Map createDpEncodedReportMap(boolean z, byte[] bArr, List list, WatchlistReportDbHelper.AggregatedResult aggregatedResult) {
        DifferentialPrivacyEncoder createInsecureDPEncoderForTest;
        int size = list.size();
        HashMap hashMap = new HashMap(size);
        for (int i = 0; i < size; i++) {
            String str = (String) list.get(i);
            if (z) {
                createInsecureDPEncoderForTest = createSecureDPEncoder(bArr, str);
            } else {
                createInsecureDPEncoderForTest = createInsecureDPEncoderForTest(str);
            }
            boolean z2 = true;
            if ((createInsecureDPEncoderForTest.encodeBoolean(aggregatedResult.appDigestList.contains(str))[0] & 1) != 1) {
                z2 = false;
            }
            hashMap.put(str, Boolean.valueOf(z2));
        }
        return hashMap;
    }
}
