package com.android.systemui.plugins.clocks;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.List;

public interface ClockFaceLayout {
    void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel);

    ConstraintSet applyConstraints(ConstraintSet constraintSet);

    ConstraintSet applyPreviewConstraints(ConstraintSet constraintSet);

    List<View> getViews();
}
