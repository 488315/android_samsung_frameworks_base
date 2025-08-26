package com.android.systemui.volume.dialog.ringer.ui.util;

import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/* loaded from: classes3.dex */
public abstract class RingerDrawerConstraintsUtilsKt {
    public static final void setButtonPositionLandscapeConstraints(ConstraintSet constraintSet, MotionLayout motionLayout, int i, View view) {
        int i2 = i + 1;
        if (motionLayout.getChildAt(i2) == null) {
            constraintSet.connect(view.getId(), 7, motionLayout.getId(), 7);
        } else {
            constraintSet.connect(view.getId(), 7, motionLayout.getChildAt(i2).getId(), 6);
        }
        constraintSet.connect(view.getId(), 4, motionLayout.getId(), 4);
        if (i == 1) {
            constraintSet.clear(view.getId(), 6);
        }
    }

    public static final void setButtonPositionPortraitConstraints(ConstraintSet constraintSet, MotionLayout motionLayout, int i, View view) {
        int i2 = i + 1;
        if (motionLayout.getChildAt(i2) == null) {
            constraintSet.connect(view.getId(), 4, motionLayout.getId(), 4);
        } else {
            constraintSet.connect(view.getId(), 4, motionLayout.getChildAt(i2).getId(), 3);
        }
        constraintSet.connect(view.getId(), 7, motionLayout.getId(), 7);
    }
}
