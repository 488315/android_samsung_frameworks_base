package com.android.server.enterprise.threatdefense;

import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.util.Hashtable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxAnalyticsThread {
    public static final String[] EVENT = {"PROC_USAGE", "PROC_PID_USAGE", "GET_PID_USAGE"};
    public static final Hashtable sApiCalledCount = new Hashtable();
    public final Object mLock = new Object();

    /* JADX WARN: Removed duplicated region for block: B:30:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void countApiCall(int r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r0 = "pkg="
            if (r10 == 0) goto Ld2
            r1 = 3
            if (r9 < r1) goto La
            goto Ld2
        La:
            java.lang.Object r1 = r8.mLock
            monitor-enter(r1)
            java.util.Hashtable r2 = com.android.server.enterprise.threatdefense.KnoxAnalyticsThread.sApiCalledCount     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Object r3 = r2.get(r10)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            if (r3 != 0) goto L4f
            java.util.Hashtable r3 = new java.util.Hashtable     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r3.<init>()     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r4 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r5 = 0
            java.lang.Long r7 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r3.put(r4, r7)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r4 = 1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Long r7 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r3.put(r4, r7)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r4 = 2
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r3.put(r4, r5)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r2.put(r10, r3)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            goto L4f
        L44:
            r8 = move-exception
            goto Ld0
        L47:
            r9 = move-exception
            goto Lac
        L49:
            r9 = move-exception
            goto Lb0
        L4b:
            r9 = move-exception
            goto Lb4
        L4d:
            r9 = move-exception
            goto Lb8
        L4f:
            java.lang.Object r3 = r2.get(r10)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.util.Hashtable r3 = (java.util.Hashtable) r3     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Object r3 = r3.get(r4)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Long r3 = (java.lang.Long) r3     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            long r3 = r3.longValue()     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r5 = 2147483646(0x7ffffffe, double:1.0609978945E-314)
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            r6 = 1
            if (r5 != 0) goto L99
            boolean r2 = com.android.server.enterprise.threatdefense.ThreatDefenseService.DEBUG     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            if (r2 == 0) goto L91
            java.lang.String r2 = "KnoxAnalyticsThread"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r5.append(r10)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.String r0 = ", api="
            r5.append(r0)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r5.append(r9)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.String r0 = ", c="
            r5.append(r0)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r5.append(r3)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            android.util.Log.d(r2, r0)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
        L91:
            long r3 = r3 + r6
            r8.sendKnoxAnalyticsLogs(r9, r10, r3)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r8.flush(r9, r10)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            goto Lcb
        L99:
            java.lang.Object r10 = r2.get(r10)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.util.Hashtable r10 = (java.util.Hashtable) r10     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            long r3 = r3 + r6
            java.lang.Long r0 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            r10.put(r9, r0)     // Catch: java.lang.Throwable -> L44 java.lang.UnsupportedOperationException -> L47 java.lang.IllegalArgumentException -> L49 java.lang.ClassCastException -> L4b java.lang.NullPointerException -> L4d
            goto Lcb
        Lac:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L44
            goto Lbb
        Lb0:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L44
            goto Lbb
        Lb4:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L44
            goto Lbb
        Lb8:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L44
        Lbb:
            java.lang.String r9 = "KnoxAnalyticsThread"
            java.lang.String r10 = "Reset"
            android.util.Log.e(r9, r10)     // Catch: java.lang.Throwable -> L44
            java.lang.Object r8 = r8.mLock     // Catch: java.lang.Throwable -> L44
            monitor-enter(r8)     // Catch: java.lang.Throwable -> L44
            java.util.Hashtable r9 = com.android.server.enterprise.threatdefense.KnoxAnalyticsThread.sApiCalledCount     // Catch: java.lang.Throwable -> Lcd
            r9.clear()     // Catch: java.lang.Throwable -> Lcd
            monitor-exit(r8)     // Catch: java.lang.Throwable -> Lcd
        Lcb:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L44
            return
        Lcd:
            r9 = move-exception
            monitor-exit(r8)     // Catch: java.lang.Throwable -> Lcd
            throw r9     // Catch: java.lang.Throwable -> L44
        Ld0:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L44
            throw r8
        Ld2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.threatdefense.KnoxAnalyticsThread.countApiCall(int, java.lang.String):void");
    }

    public final void flush(int i, String str) {
        long j;
        long j2;
        Hashtable hashtable;
        if (str == null || i >= 3) {
            Log.e("KnoxAnalyticsThread", "pkg=" + str + ", api=" + i);
            return;
        }
        long j3 = 0;
        try {
        } catch (NullPointerException e) {
            e = e;
            j = 0;
        }
        synchronized (this.mLock) {
            try {
                hashtable = sApiCalledCount;
                j2 = ((Long) ((Hashtable) hashtable.get(str)).get(Integer.valueOf(i))).longValue();
            } catch (Throwable th) {
                th = th;
                j = 0;
            }
            try {
                ((Hashtable) hashtable.get(str)).replace(Integer.valueOf(i), 0L);
                j3 = ((Long) ((Hashtable) hashtable.get(str)).get(Integer.valueOf(i))).longValue();
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Flush!! status: ", j2, "->");
                m.append(j3);
                Log.i("KnoxAnalyticsThread", m.toString());
            } catch (Throwable th2) {
                th = th2;
                long j4 = j3;
                j3 = j2;
                j = j4;
                while (true) {
                    try {
                        try {
                            throw th;
                        } catch (NullPointerException e2) {
                            e = e2;
                            e.printStackTrace();
                            long j5 = j3;
                            j3 = j;
                            j2 = j5;
                            StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m("Flush!! status: ", j2, "->");
                            m2.append(j3);
                            Log.i("KnoxAnalyticsThread", m2.toString());
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
            }
        }
    }

    public final void sendKnoxAnalyticsLogs(int i, String str, long j) {
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_MTD", 1, "MTD_USAGE_EVENT");
        knoxAnalyticsData.setProperty(KnoxAnalyticsDataConverter.EVENT, EVENT[i]);
        knoxAnalyticsData.setProperty("c", j);
        knoxAnalyticsData.setProperty("pN", str);
        if (j == 0) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Skip!! api=", "KnoxAnalyticsThread");
            return;
        }
        KnoxAnalytics.log(knoxAnalyticsData);
        if (ThreatDefenseService.DEBUG) {
            Log.d("KnoxAnalyticsThread", "KA Data : " + knoxAnalyticsData.toString());
        } else {
            Log.i("KnoxAnalyticsThread", "KA Data : " + knoxAnalyticsData.getPayload().toString());
        }
        flush(i, str);
    }
}
