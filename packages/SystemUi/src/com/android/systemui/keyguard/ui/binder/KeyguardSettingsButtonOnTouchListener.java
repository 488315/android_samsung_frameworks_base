package com.android.systemui.keyguard.ui.binder;

import android.graphics.PointF;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.systemui.animation.view.LaunchableLinearLayout;
import com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSettingsButtonOnTouchListener implements View.OnTouchListener {
    public final PointF downPositionDisplayCoords = new PointF();
    public final KeyguardSettingsMenuViewModel viewModel;

    public KeyguardSettingsButtonOnTouchListener(LaunchableLinearLayout launchableLinearLayout, KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel) {
        this.viewModel = keyguardSettingsMenuViewModel;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                view.setPressed(false);
                PointF pointF = this.downPositionDisplayCoords;
                boolean z = MathUtils.dist(motionEvent.getRawX(), motionEvent.getRawY(), pointF.x, pointF.y) < ((float) ViewConfiguration.getTouchSlop());
                this.viewModel.interactor.onMenuTouchGestureEnded(z);
                if (z) {
                    view.performClick();
                }
            } else if (actionMasked == 3) {
                view.setPressed(false);
                this.viewModel.interactor.onMenuTouchGestureEnded(false);
            }
        } else {
            view.setPressed(true);
            this.downPositionDisplayCoords.set(motionEvent.getRawX(), motionEvent.getRawY());
            KeyguardLongPressInteractor keyguardLongPressInteractor = this.viewModel.interactor;
            StandaloneCoroutine standaloneCoroutine = keyguardLongPressInteractor.delayedHideMenuJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            keyguardLongPressInteractor.delayedHideMenuJob = null;
        }
        return true;
    }
}
