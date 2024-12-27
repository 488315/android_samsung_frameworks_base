package com.android.systemui.decor;

import android.view.DisplayCutout;
import java.util.ArrayList;
import java.util.List;

public abstract class FaceScanningProviderFactoryKt {
    /* JADX WARN: Code restructure failed: missing block: B:19:0x001c, code lost:
    
        if (r4 != 2) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0023, code lost:
    
        if (r4 != 2) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x000e, code lost:
    
        if (r4 != 2) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int baseOnRotation0(int r4, int r5) {
        /*
            if (r5 == 0) goto L26
            r0 = 0
            r1 = 3
            r2 = 2
            r3 = 1
            if (r5 == r3) goto L1f
            if (r5 == r1) goto L18
            if (r4 == 0) goto L16
            if (r4 == r3) goto L14
            if (r4 == r2) goto L12
        L10:
            r4 = r3
            goto L26
        L12:
            r4 = r0
            goto L26
        L14:
            r4 = r1
            goto L26
        L16:
            r4 = r2
            goto L26
        L18:
            if (r4 == 0) goto L14
            if (r4 == r3) goto L12
            if (r4 == r2) goto L10
            goto L16
        L1f:
            if (r4 == 0) goto L10
            if (r4 == r3) goto L16
            if (r4 == r2) goto L14
            goto L12
        L26:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.decor.FaceScanningProviderFactoryKt.baseOnRotation0(int, int):int");
    }

    public static final List getBoundBaseOnCurrentRotation(DisplayCutout displayCutout) {
        ArrayList arrayList = new ArrayList();
        if (!displayCutout.getBoundingRectLeft().isEmpty()) {
            arrayList.add(0);
        }
        if (!displayCutout.getBoundingRectTop().isEmpty()) {
            arrayList.add(1);
        }
        if (!displayCutout.getBoundingRectRight().isEmpty()) {
            arrayList.add(2);
        }
        if (!displayCutout.getBoundingRectBottom().isEmpty()) {
            arrayList.add(3);
        }
        return arrayList;
    }
}
