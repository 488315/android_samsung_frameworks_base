package com.android.server.usage;

import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackagesTokenData {
    public int counter = 1;
    public final SparseArray tokensToPackagesMap = new SparseArray();
    public final ArrayMap packagesToTokensMap = new ArrayMap();
    public final ArrayMap removedPackagesMap = new ArrayMap();
    public final ArraySet removedPackageTokens = new ArraySet();

    public final int getPackageTokenOrAdd(long j, String str) {
        Long l = (Long) this.removedPackagesMap.get(str);
        if (l != null && l.longValue() > j) {
            return -1;
        }
        ArrayMap arrayMap = (ArrayMap) this.packagesToTokensMap.get(str);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.packagesToTokensMap.put(str, arrayMap);
        }
        int intValue = ((Integer) arrayMap.getOrDefault(str, -1)).intValue();
        if (intValue != -1) {
            return intValue;
        }
        int i = this.counter;
        this.counter = i + 1;
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m(str);
        arrayMap.put(str, Integer.valueOf(i));
        this.tokensToPackagesMap.put(i, m);
        return i;
    }

    public final String getString(int i, int i2) {
        try {
            return (String) ((ArrayList) this.tokensToPackagesMap.get(i)).get(i2);
        } catch (IndexOutOfBoundsException unused) {
            return null;
        } catch (NullPointerException e) {
            Slog.e("PackagesTokenData", "Unable to find tokenized strings for package " + i, e);
            return null;
        }
    }

    public final int getTokenOrAdd(int i, String str, String str2) {
        if (str.equals(str2)) {
            return 0;
        }
        int intValue =
                ((Integer) ((ArrayMap) this.packagesToTokensMap.get(str)).getOrDefault(str2, -1))
                        .intValue();
        if (intValue != -1) {
            return intValue;
        }
        int size = ((ArrayList) this.tokensToPackagesMap.get(i)).size();
        ((ArrayMap) this.packagesToTokensMap.get(str)).put(str2, Integer.valueOf(size));
        ((ArrayList) this.tokensToPackagesMap.get(i)).add(str2);
        return size;
    }

    public final int removePackage(long j, String str) {
        this.removedPackagesMap.put(str, Long.valueOf(j));
        if (!this.packagesToTokensMap.containsKey(str)) {
            return -1;
        }
        Integer num = (Integer) ((ArrayMap) this.packagesToTokensMap.get(str)).get(str);
        int intValue = num.intValue();
        this.packagesToTokensMap.remove(str);
        this.tokensToPackagesMap.delete(intValue);
        this.removedPackageTokens.add(num);
        return intValue;
    }
}
