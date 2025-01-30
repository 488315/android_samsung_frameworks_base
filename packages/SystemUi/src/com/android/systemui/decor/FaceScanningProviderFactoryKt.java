package com.android.systemui.decor;

import android.view.DisplayCutout;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FaceScanningProviderFactoryKt {
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0015, code lost:
    
        if (r4 != 2) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x001c, code lost:
    
        if (r4 != 2) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x000e, code lost:
    
        if (r4 != 2) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int baseOnRotation0(int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        if (i2 == 1) {
            if (i != 0) {
                if (i != 1) {
                }
                return 2;
            }
            return 1;
        }
        if (i2 != 3) {
            if (i != 0) {
                if (i != 1) {
                }
                return 3;
            }
            return 2;
        }
        if (i != 0) {
            if (i != 1) {
            }
            return 0;
        }
        return 3;
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
