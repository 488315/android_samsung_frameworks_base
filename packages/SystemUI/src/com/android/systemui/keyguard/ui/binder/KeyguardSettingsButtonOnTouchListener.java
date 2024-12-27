package com.android.systemui.keyguard.ui.binder;

import android.graphics.PointF;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSettingsButtonOnTouchListener implements View.OnTouchListener {
    public final PointF downPositionDisplayCoords = new PointF();
    public final KeyguardSettingsMenuViewModel viewModel;

    public KeyguardSettingsButtonOnTouchListener(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel) {
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
                KeyguardLongPressInteractor keyguardLongPressInteractor = this.viewModel.interactor;
                if (z) {
                    keyguardLongPressInteractor.hideMenu();
                    keyguardLongPressInteractor.logger.log(KeyguardLongPressInteractor.LogEvents.LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED);
                    keyguardLongPressInteractor._shouldOpenSettings.updateState(null, Boolean.TRUE);
                } else {
                    keyguardLongPressInteractor.scheduleAutomaticMenuHiding();
                }
                if (z) {
                    view.performClick();
                }
            } else if (actionMasked == 3) {
                view.setPressed(false);
                this.viewModel.interactor.scheduleAutomaticMenuHiding();
            }
        } else {
            view.setPressed(true);
            this.downPositionDisplayCoords.set(motionEvent.getRawX(), motionEvent.getRawY());
            KeyguardLongPressInteractor keyguardLongPressInteractor2 = this.viewModel.interactor;
            Job job = keyguardLongPressInteractor2.delayedHideMenuJob;
            if (job != null) {
                job.cancel(null);
            }
            keyguardLongPressInteractor2.delayedHideMenuJob = null;
        }
        return true;
    }
}
