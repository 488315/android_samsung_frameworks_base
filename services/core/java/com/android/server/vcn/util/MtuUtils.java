package com.android.server.vcn.util;

import android.net.ipsec.ike.ChildSaProposal;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class MtuUtils {
    public static final Map AUTHCRYPT_ALGORITHM_OVERHEAD;
    public static final Map AUTH_ALGORITHM_OVERHEAD;
    public static final Map CRYPT_ALGORITHM_OVERHEAD;

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

    public static int getMtu(List list, int i, boolean z, int i2) {
        if (i2 <= 0) {
            return 1280;
        }
        Iterator it = list.iterator();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (it.hasNext()) {
            ChildSaProposal childSaProposal = (ChildSaProposal) it.next();
            Iterator<Pair<Integer, Integer>> it2 =
                    childSaProposal.getEncryptionAlgorithms().iterator();
            while (it2.hasNext()) {
                Integer num = (Integer) it2.next().first;
                int intValue = num.intValue();
                Map map = AUTHCRYPT_ALGORITHM_OVERHEAD;
                if (map.containsKey(num)) {
                    i3 = Math.max(i3, ((Integer) map.get(num)).intValue());
                } else {
                    Map map2 = CRYPT_ALGORITHM_OVERHEAD;
                    if (!map2.containsKey(num)) {
                        Slog.wtf("MtuUtils", "Unknown encryption algorithm requested: " + intValue);
                        return 1280;
                    }
                    i4 = Math.max(i4, ((Integer) map2.get(num)).intValue());
                }
            }
            for (Integer num2 : childSaProposal.getIntegrityAlgorithms()) {
                int intValue2 = num2.intValue();
                Map map3 = AUTH_ALGORITHM_OVERHEAD;
                if (!map3.containsKey(num2)) {
                    Slog.wtf("MtuUtils", "Unknown integrity algorithm requested: " + intValue2);
                    return 1280;
                }
                i5 = Math.max(i5, ((Integer) map3.get(num2)).intValue());
            }
        }
        int i6 = z ? 78 : 50;
        return Math.min(Math.min(i, (i2 - i3) - i6), ((i2 - i4) - i5) - i6);
    }
}
