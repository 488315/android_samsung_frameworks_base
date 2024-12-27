package com.android.server.servicewatcher;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class CurrentUserServiceSupplier$$ExternalSyntheticLambda0
        implements Comparator {
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0036, code lost:

       if (android.os.UserHandle.getUserId(r5) != 0) goto L10;
    */
    @Override // java.util.Comparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int compare(java.lang.Object r4, java.lang.Object r5) {
        /*
            r3 = this;
            com.android.server.servicewatcher.CurrentUserServiceSupplier$BoundServiceInfo r4 = (com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo) r4
            com.android.server.servicewatcher.CurrentUserServiceSupplier$BoundServiceInfo r5 = (com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo) r5
            if (r4 != r5) goto L8
            r3 = 0
            goto L3a
        L8:
            r3 = -1
            if (r4 != 0) goto Lc
            goto L3a
        Lc:
            r0 = 1
            if (r5 != 0) goto L11
        Lf:
            r3 = r0
            goto L3a
        L11:
            int r1 = r4.mVersion
            int r2 = r5.mVersion
            int r1 = java.lang.Integer.compare(r1, r2)
            if (r1 != 0) goto L39
            int r4 = r4.mUid
            int r2 = android.os.UserHandle.getUserId(r4)
            int r5 = r5.mUid
            if (r2 == 0) goto L2c
            int r2 = android.os.UserHandle.getUserId(r5)
            if (r2 != 0) goto L2c
            goto L3a
        L2c:
            int r3 = android.os.UserHandle.getUserId(r4)
            if (r3 != 0) goto L39
            int r3 = android.os.UserHandle.getUserId(r5)
            if (r3 == 0) goto L39
            goto Lf
        L39:
            r3 = r1
        L3a:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.servicewatcher.CurrentUserServiceSupplier$$ExternalSyntheticLambda0.compare(java.lang.Object,"
                    + " java.lang.Object):int");
    }
}
