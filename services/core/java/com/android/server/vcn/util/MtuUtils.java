package com.android.server.vcn.util;

import android.net.ipsec.ike.ChildSaProposal;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class MtuUtils {
    public static final Map AUTHCRYPT_ALGORITHM_OVERHEAD;
    public static final Map AUTH_ALGORITHM_OVERHEAD;
    public static final Map CRYPT_ALGORITHM_OVERHEAD;
    public static final String TAG = "MtuUtils";

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

    public static int getMtu(List list, int i, int i2, boolean z) {
        if (i2 <= 0) {
            return 1280;
        }
        Iterator it = list.iterator();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (it.hasNext()) {
            ChildSaProposal childSaProposal = (ChildSaProposal) it.next();
            Iterator<Pair<Integer, Integer>> it2 = childSaProposal.getEncryptionAlgorithms().iterator();
            while (it2.hasNext()) {
                int intValue = ((Integer) it2.next().first).intValue();
                Map map = AUTHCRYPT_ALGORITHM_OVERHEAD;
                if (map.containsKey(Integer.valueOf(intValue))) {
                    i3 = Math.max(i3, ((Integer) map.get(Integer.valueOf(intValue))).intValue());
                } else {
                    Map map2 = CRYPT_ALGORITHM_OVERHEAD;
                    if (map2.containsKey(Integer.valueOf(intValue))) {
                        i4 = Math.max(i4, ((Integer) map2.get(Integer.valueOf(intValue))).intValue());
                    } else {
                        Slog.wtf(TAG, "Unknown encryption algorithm requested: " + intValue);
                        return 1280;
                    }
                }
            }
            Iterator<Integer> it3 = childSaProposal.getIntegrityAlgorithms().iterator();
            while (it3.hasNext()) {
                int intValue2 = it3.next().intValue();
                Map map3 = AUTH_ALGORITHM_OVERHEAD;
                if (map3.containsKey(Integer.valueOf(intValue2))) {
                    i5 = Math.max(i5, ((Integer) map3.get(Integer.valueOf(intValue2))).intValue());
                } else {
                    Slog.wtf(TAG, "Unknown integrity algorithm requested: " + intValue2);
                    return 1280;
                }
            }
        }
        int i6 = z ? 78 : 50;
        return Math.min(Math.min(i, (i2 - i3) - i6), ((i2 - i4) - i5) - i6);
    }
}
