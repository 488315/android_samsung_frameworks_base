package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArrayMap;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LatestProtectedPackageFilter implements IFilter {
    public ArrayMap mLatestProtectedPackages;
    public int mProtectedAppSizeForGame;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LatestProtectedPackageFilterHolder {
        public static final LatestProtectedPackageFilter INSTANCE;

        static {
            LatestProtectedPackageFilter latestProtectedPackageFilter = new LatestProtectedPackageFilter();
            latestProtectedPackageFilter.mProtectedAppSizeForGame = 0;
            latestProtectedPackageFilter.mLatestProtectedPackages = new ArrayMap();
            INSTANCE = latestProtectedPackageFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0043, code lost:
    
        if (r3 > 2) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0053 A[Catch: all -> 0x005a, TryCatch #0 {all -> 0x005a, blocks: (B:13:0x001b, B:15:0x0029, B:17:0x002f, B:26:0x0053, B:31:0x005e, B:35:0x0068, B:33:0x006a, B:39:0x0045, B:42:0x004a, B:44:0x004e, B:45:0x006d), top: B:12:0x001b }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005e A[Catch: all -> 0x005a, TryCatch #0 {all -> 0x005a, blocks: (B:13:0x001b, B:15:0x0029, B:17:0x002f, B:26:0x0053, B:31:0x005e, B:35:0x0068, B:33:0x006a, B:39:0x0045, B:42:0x004a, B:44:0x004e, B:45:0x006d), top: B:12:0x001b }] */
    @Override // com.android.server.am.mars.filter.IFilter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int filter(int r8, int r9, int r10, java.lang.String r11) {
        /*
            r7 = this;
            r9 = 0
            if (r11 != 0) goto L4
            return r9
        L4:
            boolean r0 = com.android.server.am.MARsPolicyManager.MARs_ENABLE
            com.android.server.am.MARsPolicyManager r0 = com.android.server.am.MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
            r0.getClass()
            boolean r0 = com.android.server.am.MARsPolicyManager.isChinaModel
            r1 = 11
            r2 = 15
            if (r0 == 0) goto L18
            if (r10 == r1) goto L17
            if (r10 != r2) goto L18
        L17:
            return r9
        L18:
            android.util.ArrayMap r0 = r7.mLatestProtectedPackages
            monitor-enter(r0)
            android.util.ArrayMap r3 = r7.mLatestProtectedPackages     // Catch: java.lang.Throwable -> L5a
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L5a
            java.lang.Object r8 = r3.get(r8)     // Catch: java.lang.Throwable -> L5a
            java.util.ArrayList r8 = (java.util.ArrayList) r8     // Catch: java.lang.Throwable -> L5a
            if (r8 == 0) goto L6d
            int r3 = r8.size()     // Catch: java.lang.Throwable -> L5a
            if (r3 <= 0) goto L6d
            int r3 = r8.size()     // Catch: java.lang.Throwable -> L5a
            int r4 = r3 + (-1)
            r5 = 9
            r6 = 2
            if (r10 == r5) goto L4a
            if (r10 == r1) goto L47
            if (r10 == r2) goto L43
            r1 = 18
            if (r10 == r1) goto L4a
            goto L50
        L43:
            if (r3 <= r6) goto L50
        L45:
            int r3 = r3 - r6
            goto L51
        L47:
            if (r3 <= r6) goto L50
            goto L45
        L4a:
            int r7 = r7.mProtectedAppSizeForGame     // Catch: java.lang.Throwable -> L5a
            if (r3 <= r7) goto L50
            int r3 = r3 - r7
            goto L51
        L50:
            r3 = r9
        L51:
            if (r10 != r2) goto L5c
            boolean r7 = com.android.server.am.mars.MARsUtils.getScreenOnState()     // Catch: java.lang.Throwable -> L5a
            if (r7 == 0) goto L6d
            goto L5c
        L5a:
            r7 = move-exception
            goto L6f
        L5c:
            if (r4 < r3) goto L6d
            java.lang.Object r7 = r8.get(r4)     // Catch: java.lang.Throwable -> L5a
            boolean r7 = r11.equals(r7)     // Catch: java.lang.Throwable -> L5a
            if (r7 == 0) goto L6a
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            return r6
        L6a:
            int r4 = r4 + (-1)
            goto L5c
        L6d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            return r9
        L6f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter.filter(int, int, int, java.lang.String):int");
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
    }

    public final void setLatestProtectedPkg(int i, String str) {
        ArrayMap arrayMap = this.mLatestProtectedPackages;
        if (arrayMap != null) {
            synchronized (arrayMap) {
                try {
                    ArrayList arrayList = (ArrayList) this.mLatestProtectedPackages.get(Integer.valueOf(i));
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        arrayList.add(str);
                    } else if (arrayList.contains(str)) {
                        arrayList.remove(str);
                        arrayList.add(str);
                    } else if (arrayList.size() == 6) {
                        arrayList.remove(0);
                        arrayList.add(str);
                    } else {
                        arrayList.add(str);
                    }
                    this.mLatestProtectedPackages.put(Integer.valueOf(i), arrayList);
                } finally {
                }
            }
        }
    }
}
