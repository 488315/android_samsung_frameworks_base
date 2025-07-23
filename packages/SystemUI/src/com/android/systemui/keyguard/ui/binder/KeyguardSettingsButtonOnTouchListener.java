package com.android.systemui.keyguard.ui.binder;

import android.graphics.PointF;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.systemui.keyguard.domain.interactor.KeyguardTouchHandlingInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSettingsButtonOnTouchListener implements View.OnTouchListener {
    public final PointF downPositionDisplayCoords = new PointF();
    public final KeyguardSettingsMenuViewModel viewModel;

    public KeyguardSettingsButtonOnTouchListener(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel) {
        this.viewModel = keyguardSettingsMenuViewModel;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            view.setPressed(true);
            this.downPositionDisplayCoords.set(motionEvent.getRawX(), motionEvent.getRawY());
            KeyguardTouchHandlingInteractor keyguardTouchHandlingInteractor = this.viewModel.interactor;
            StandaloneCoroutine standaloneCoroutine = keyguardTouchHandlingInteractor.delayedHideMenuJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            keyguardTouchHandlingInteractor.delayedHideMenuJob = null;
            return true;
        }
        if (actionMasked == 1) {
            view.setPressed(false);
            PointF pointF = this.downPositionDisplayCoords;
            boolean z = MathUtils.dist(motionEvent.getRawX(), motionEvent.getRawY(), pointF.x, pointF.y) < ((float) ViewConfiguration.getTouchSlop());
            KeyguardTouchHandlingInteractor keyguardTouchHandlingInteractor2 = this.viewModel.interactor;
            if (z) {
                keyguardTouchHandlingInteractor2.hideMenu();
                keyguardTouchHandlingInteractor2.logger.log(KeyguardTouchHandlingInteractor.LogEvents.LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED);
                keyguardTouchHandlingInteractor2._shouldOpenSettings.updateState(null, Boolean.TRUE);
            } else {
                keyguardTouchHandlingInteractor2.scheduleAutomaticMenuHiding();
            }
            if (z) {
                view.performClick();
            }
        } else if (actionMasked == 3) {
            view.setPressed(false);
            this.viewModel.interactor.scheduleAutomaticMenuHiding();
            return true;
        }
        return true;
    }
}
