package com.android.systemui.shade;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.Log;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationPanelView extends FrameLayout {
    public final Paint mAlphaPaint;
    public int mCurrentPanelAlpha;
    public boolean mDozing;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mOnConfigurationChangedListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mRtlChangeListener;
    public int mStatusBarState;
    public NotificationPanelViewController.TouchHandler mTouchHandler;
    public final SecTouchLogHelper mTouchLogHelper;

    public NotificationPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mAlphaPaint = paint;
        this.mTouchLogHelper = new SecTouchLogHelper();
        setWillNotDraw(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        setBackgroundColor(0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchConfigurationChanged(Configuration configuration) {
        Dialog dialog;
        super.dispatchConfigurationChanged(configuration);
        NotificationPanelViewController notificationPanelViewController = this.mOnConfigurationChangedListener.f$0;
        notificationPanelViewController.loadDimens();
        notificationPanelViewController.mKeyguardTouchAnimator.initDimens();
        PrivacyDialogController privacyDialogController = notificationPanelViewController.mPrivacyDialogController;
        if (privacyDialogController != null && (dialog = privacyDialogController.dialog) != null) {
            dialog.dismiss();
        }
        Context context = notificationPanelViewController.mView.getContext();
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController;
        keyguardEditModeControllerImpl.getClass();
        Log.m138d("KeyguardEditModeController", "onConfigurationChanged ");
        keyguardEditModeControllerImpl.initPreviewValues(context);
        if (QpRune.PANEL_DATA_USAGE_LABEL) {
            ((DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get()).onPanelConfigurationChanged(configuration);
        }
        MultiWindowEdgeDetector multiWindowEdgeDetector = notificationPanelViewController.mMultiWindowEdgeDetector;
        if (multiWindowEdgeDetector != null) {
            multiWindowEdgeDetector.onConfigurationChanged();
        }
        if (LsRune.KEYGUARD_DCM_LIVE_UX) {
            notificationPanelViewController.mMascotViewContainer.updateRes();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mCurrentPanelAlpha != 255) {
            canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), this.mAlphaPaint);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.mTouchLogHelper.printDispatchTouchEvent(motionEvent, "NPV");
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return !this.mDozing;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mTouchHandler.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = this.mRtlChangeListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda2.f$0;
            if (i != notificationPanelViewController.mOldLayoutDirection) {
                notificationPanelViewController.mOldLayoutDirection = i;
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }
}
