package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class CustomInterpolatorTransformation extends ViewTransformationHelper.CustomTransformation {
    public final int mViewType;

    public CustomInterpolatorTransformation(int i) {
        this.mViewType = i;
    }

    public boolean hasCustomTransformation() {
        return true;
    }

    @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
    public boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
        TransformState currentState;
        if (!hasCustomTransformation() || (currentState = transformableView.getCurrentState(this.mViewType)) == null) {
            return false;
        }
        CrossFadeHelper.fadeIn(transformState.mTransformedView, f, true);
        transformState.transformViewFrom(currentState, 17, this, f);
        currentState.recycle();
        return true;
    }

    @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
    public boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
        TransformState currentState;
        if (!hasCustomTransformation() || (currentState = transformableView.getCurrentState(this.mViewType)) == null) {
            return false;
        }
        CrossFadeHelper.fadeOut(transformState.mTransformedView, f, true);
        transformState.transformViewTo(currentState, 17, this, f);
        currentState.recycle();
        return true;
    }
}
