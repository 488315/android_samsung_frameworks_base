package com.android.systemui.classifier;

import android.content.res.Resources;
import android.view.ViewConfiguration;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface FalsingModule {
    static float providesDoubleTapTouchSlop(Resources resources) {
        return resources.getDimension(R.dimen.double_tap_slop);
    }

    static boolean providesIsFoldableDevice(Resources resources) {
        try {
            return resources.getIntArray(android.R.array.networks_not_clear_data).length != 0;
        } catch (Resources.NotFoundException unused) {
            return false;
        }
    }

    static float providesLongTapTouchSlop(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledTouchSlop() * 1.25f;
    }
}
