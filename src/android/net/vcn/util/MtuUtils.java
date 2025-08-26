package android.net.vcn.util;

import android.net.ipsec.ike.ChildSaProposal;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class MtuUtils {
    private static final Map<Integer, Integer> AUTHCRYPT_ALGORITHM_OVERHEAD;
    private static final Map<Integer, Integer> AUTH_ALGORITHM_OVERHEAD;
    private static final Map<Integer, Integer> CRYPT_ALGORITHM_OVERHEAD;
    private static final int GENERIC_ESP_OVERHEAD_MAX_V4 = 78;
    private static final int GENERIC_ESP_OVERHEAD_MAX_V6 = 50;
    private static final String TAG = "MtuUtils";

    static {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(0, 0);
        arrayMap.put(2, 12);
        arrayMap.put(5, 12);
        arrayMap.put(12, 32);
        arrayMap.put(13, 48);
        arrayMap.put(14, 64);
        arrayMap.put(8, 12);
        AUTH_ALGORITHM_OVERHEAD = Collections.unmodifiableMap(arrayMap);
        ArrayMap arrayMap2 = new ArrayMap();
        arrayMap2.put(3, 15);
        arrayMap2.put(12, 31);
        arrayMap2.put(13, 11);
        CRYPT_ALGORITHM_OVERHEAD = Collections.unmodifiableMap(arrayMap2);
        ArrayMap arrayMap3 = new ArrayMap();
        arrayMap3.put(18, 19);
        arrayMap3.put(19, 23);
        arrayMap3.put(20, 27);
        arrayMap3.put(28, 27);
        AUTHCRYPT_ALGORITHM_OVERHEAD = Collections.unmodifiableMap(arrayMap3);
    }

    public static int getMtu(List<ChildSaProposal> list, int i, int i2, boolean z) {
        if (i2 <= 0) {
            return 1280;
        }
        int iMax = 0;
        int iMax2 = 0;
        int iMax3 = 0;
        for (ChildSaProposal childSaProposal : list) {
            Iterator<Pair<Integer, Integer>> it = childSaProposal.getEncryptionAlgorithms().iterator();
            while (it.hasNext()) {
                Integer num = it.next().first;
                int iIntValue = num.intValue();
                Map<Integer, Integer> map = AUTHCRYPT_ALGORITHM_OVERHEAD;
                if (map.containsKey(num)) {
                    iMax = Math.max(iMax, map.get(num).intValue());
                } else {
                    Map<Integer, Integer> map2 = CRYPT_ALGORITHM_OVERHEAD;
                    if (map2.containsKey(num)) {
                        iMax2 = Math.max(iMax2, map2.get(num).intValue());
                    } else {
                        Slog.wtf(TAG, "Unknown encryption algorithm requested: " + iIntValue);
                        return 1280;
                    }
                }
            }
            for (Integer num2 : childSaProposal.getIntegrityAlgorithms()) {
                int iIntValue2 = num2.intValue();
                Map<Integer, Integer> map3 = AUTH_ALGORITHM_OVERHEAD;
                if (map3.containsKey(num2)) {
                    iMax3 = Math.max(iMax3, map3.get(num2).intValue());
                } else {
                    Slog.wtf(TAG, "Unknown integrity algorithm requested: " + iIntValue2);
                    return 1280;
                }
            }
        }
        int i3 = z ? 78 : 50;
        return Math.min(Math.min(i, (i2 - iMax) - i3), ((i2 - iMax2) - iMax3) - i3);
    }
}
