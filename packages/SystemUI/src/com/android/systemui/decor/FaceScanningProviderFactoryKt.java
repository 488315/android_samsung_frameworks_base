package com.android.systemui.decor;

import android.view.DisplayCutout;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class FaceScanningProviderFactoryKt {
    public static final int baseOnRotation0(int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        if (i2 == 1) {
            if (i == 0) {
                return 1;
            }
            if (i != 1) {
                return i != 2 ? 0 : 3;
            }
            return 2;
        }
        if (i2 != 3) {
            if (i == 0) {
                return 2;
            }
            if (i != 1) {
                return i != 2 ? 1 : 0;
            }
            return 3;
        }
        if (i == 0) {
            return 3;
        }
        if (i != 1) {
            return i != 2 ? 2 : 1;
        }
        return 0;
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
