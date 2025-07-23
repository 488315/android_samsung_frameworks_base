package com.android.systemui.plugins.clocks;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ClockFaceLayout {
    void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel);

    ConstraintSet applyConstraints(ConstraintSet constraintSet);

    ConstraintSet applyPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet);

    List<View> getViews();
}
