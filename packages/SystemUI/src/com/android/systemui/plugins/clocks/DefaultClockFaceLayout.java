package com.android.systemui.plugins.clocks;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Collections;
import java.util.List;

public final class DefaultClockFaceLayout implements ClockFaceLayout {
    private final View view;
    private final List<View> views;

    public DefaultClockFaceLayout(View view) {
        this.view = view;
        this.views = Collections.singletonList(view);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyConstraints(ConstraintSet constraintSet) {
        if (getViews().size() == 1) {
            return constraintSet;
        }
        throw new IllegalArgumentException("Should have only one container view when using DefaultClockFaceLayout");
    }

    public final View getView() {
        return this.view;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public List<View> getViews() {
        return this.views;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel) {
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyPreviewConstraints(ConstraintSet constraintSet) {
        return constraintSet;
    }
}
