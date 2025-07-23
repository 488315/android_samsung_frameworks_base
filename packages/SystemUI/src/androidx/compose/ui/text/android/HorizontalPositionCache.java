package androidx.compose.ui.text.android;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HorizontalPositionCache {
    public int cachedKey = -1;
    public float cachedValue;
    public final TextLayout layout;

    public HorizontalPositionCache(TextLayout textLayout) {
        this.layout = textLayout;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float get(int r6, boolean r7, boolean r8, boolean r9) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            androidx.compose.ui.text.android.TextLayout r2 = r5.layout
            if (r7 == 0) goto L1b
            android.text.Layout r3 = r2.layout
            int r3 = androidx.compose.ui.text.android.LayoutCompat_androidKt.getLineForOffset(r3, r6, r7)
            android.text.Layout r4 = r2.layout
            int r4 = r4.getLineStart(r3)
            int r3 = r2.getLineEnd(r3)
            if (r6 == r4) goto L1d
            if (r6 != r3) goto L1b
            goto L1d
        L1b:
            r3 = r1
            goto L1e
        L1d:
            r3 = r0
        L1e:
            int r4 = r6 * 4
            if (r9 == 0) goto L26
            if (r3 == 0) goto L2b
            r0 = r1
            goto L2b
        L26:
            if (r3 == 0) goto L2a
            r0 = 2
            goto L2b
        L2a:
            r0 = 3
        L2b:
            int r4 = r4 + r0
            int r0 = r5.cachedKey
            if (r0 != r4) goto L33
            float r5 = r5.cachedValue
            return r5
        L33:
            if (r9 == 0) goto L3a
            float r6 = r2.getPrimaryHorizontal(r6, r7)
            goto L3e
        L3a:
            float r6 = r2.getSecondaryHorizontal(r6, r7)
        L3e:
            if (r8 == 0) goto L44
            r5.cachedKey = r4
            r5.cachedValue = r6
        L44:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.android.HorizontalPositionCache.get(int, boolean, boolean, boolean):float");
    }
}
