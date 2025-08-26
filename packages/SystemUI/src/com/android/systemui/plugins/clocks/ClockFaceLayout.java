package com.android.systemui.plugins.clocks;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.List;

/* loaded from: classes2.dex */
public interface ClockFaceLayout {
    void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel);

    ConstraintSet applyConstraints(ConstraintSet constraintSet);

    ConstraintSet applyPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet);

    List<View> getViews();
}
