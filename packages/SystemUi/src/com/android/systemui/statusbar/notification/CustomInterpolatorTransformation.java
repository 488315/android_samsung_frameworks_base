package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
