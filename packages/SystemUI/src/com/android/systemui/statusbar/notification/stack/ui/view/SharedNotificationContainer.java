package com.android.systemui.statusbar.notification.stack.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;

/* loaded from: classes3.dex */
public final class SharedNotificationContainer extends ConstraintLayout {
    public final ConstraintSet baseConstraintSet;

    public SharedNotificationContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ConstraintSet constraintSet = new ConstraintSet();
        this.baseConstraintSet = constraintSet;
        setOptimizationLevel(getOptimizationLevel() | 64);
        constraintSet.create(R.id.nssl_guideline, 1);
        constraintSet.setGuidelinePercent(R.id.nssl_guideline, 0.5f);
        constraintSet.applyTo(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        QsAnimatorState.INSTANCE.getClass();
        if (QsAnimatorState.isCustomizerShowing || QsAnimatorState.isDetailShowing) {
            return false;
        }
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled && ((SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class)).isQSState()) {
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateConstraints(SharedNotificationContainerViewModel.HorizontalPosition horizontalPosition, int i, int i2, int i3, float f, int i4, int i5, float f2, SecQsUiDisplayModeInteractor.UiDisplayMode uiDisplayMode) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.baseConstraintSet);
        int i6 = horizontalPosition instanceof SharedNotificationContainerViewModel.HorizontalPosition.MiddleToEdge ? R.id.nssl_guideline : 0;
        constraintSet.setAlpha(R.id.notification_stack_scroller, f);
        constraintSet.setVisibility(R.id.notification_stack_scroller, i4);
        constraintSet.connect(R.id.notification_stack_scroller, 6, i6, 6, i);
        constraintSet.connect(R.id.notification_stack_scroller, 7, 0, 7, i2);
        constraintSet.connect(R.id.notification_stack_scroller, 4, 0, 4, i3);
        constraintSet.connect(R.id.notification_stack_scroller, 3, 0, 3, 0);
        constraintSet.constrainWidth(R.id.notification_stack_scroller, i5);
        if (uiDisplayMode == SecQsUiDisplayModeInteractor.UiDisplayMode.LARGE) {
            constraintSet.setTranslationX(R.id.notification_stack_scroller, f2);
        }
        QsAnimatorState.INSTANCE.getClass();
        if (QsAnimatorState.isCustomizerShowing || QsAnimatorState.isDetailShowing) {
            constraintSet.setVisibility(R.id.notification_stack_scroller, 4);
        } else {
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && ((SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class)).isQSState()) {
            }
        }
        constraintSet.applyTo(this);
    }
}
