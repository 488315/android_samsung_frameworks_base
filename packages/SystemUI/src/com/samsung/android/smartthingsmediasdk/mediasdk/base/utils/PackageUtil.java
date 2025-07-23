package com.samsung.android.smartthingsmediasdk.mediasdk.base.utils;

import android.util.Log;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import java.util.List;
import kotlin.Result;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PackageUtil {
    public static final PackageUtil INSTANCE = new PackageUtil();

    private PackageUtil() {
    }

    public static int compareVersion(String str) {
        Object failure;
        Object failure2;
        Object failure3;
        DLog.Companion.getClass();
        Log.d(DLog.TAG, DLog.Companion.formatMessage("PackageUtil", "compareVersionName", "version1: " + str + " | version2: 1.8.17.22"));
        try {
            int i = Result.$r8$clinit;
            failure = StringsKt__StringsKt.split$default(str, new String[]{"."}, 0, 6);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Object obj = EmptyList.INSTANCE;
        if (failure instanceof Result.Failure) {
            failure = obj;
        }
        List list = (List) failure;
        try {
            failure2 = StringsKt__StringsKt.split$default("1.8.17.22", new String[]{"."}, 0, 6);
        } catch (Throwable th2) {
            int i3 = Result.$r8$clinit;
            failure2 = new Result.Failure(th2);
        }
        Object obj2 = EmptyList.INSTANCE;
        if (failure2 instanceof Result.Failure) {
            failure2 = obj2;
        }
        List list2 = (List) failure2;
        if (list.isEmpty() || list2.isEmpty() || list.size() != list2.size()) {
            DLog.Companion.getClass();
            Log.e(DLog.TAG, DLog.Companion.formatMessage("PackageUtil", "Versions are invalid.", "Version1: " + list + " | Version2: " + list2));
            return -1;
        }
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            try {
                int i5 = Result.$r8$clinit;
                failure3 = Integer.valueOf(Intrinsics.compare(Integer.parseInt((String) list.get(i4)), Integer.parseInt((String) list2.get(i4))));
            } catch (Throwable th3) {
                int i6 = Result.$r8$clinit;
                failure3 = new Result.Failure(th3);
            }
            if (failure3 instanceof Result.Failure) {
                failure3 = -1;
            }
            int intValue = ((Number) failure3).intValue();
            if (intValue != 0) {
                return intValue;
            }
        }
        return 0;
    }
}
