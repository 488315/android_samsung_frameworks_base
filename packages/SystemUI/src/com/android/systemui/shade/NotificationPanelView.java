package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.systemui.CscRune;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.Log;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationPanelView extends FrameLayout {
    public final Paint mAlphaPaint;
    public int mCurrentPanelAlpha;
    public boolean mDozing;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mOnConfigurationChangedListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mRtlChangeListener;
    public NotificationPanelViewController.TouchHandler mTouchHandler;

    public NotificationPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mAlphaPaint = paint;
        int i = SceneContainerFlag.$r8$clinit;
        setWillNotDraw(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        setBackgroundColor(0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchConfigurationChanged(Configuration configuration) {
        PrivacyDialog privacyDialog;
        super.dispatchConfigurationChanged(configuration);
        int i = SceneContainerFlag.$r8$clinit;
        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mOnConfigurationChangedListener;
        notificationPanelViewController$$ExternalSyntheticLambda0.getClass();
        Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
        NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda0.f$0;
        notificationPanelViewController.loadDimens();
        notificationPanelViewController.mKeyguardTouchAnimator.initDimens$5();
        PrivacyDialogController privacyDialogController = notificationPanelViewController.mPrivacyDialogController;
        if (privacyDialogController != null && (privacyDialog = privacyDialogController.dialog) != null) {
            privacyDialog.dismiss();
        }
        Context context = notificationPanelViewController.mView.getContext();
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController;
        keyguardEditModeControllerImpl.getClass();
        Log.d("KeyguardEditModeController", "onConfigurationChanged ");
        keyguardEditModeControllerImpl.initPreviewValues(context);
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            ((DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get()).onPanelConfigurationChanged(configuration);
        }
        MultiWindowEdgeDetector multiWindowEdgeDetector = notificationPanelViewController.mMultiWindowEdgeDetector;
        if (multiWindowEdgeDetector != null) {
            multiWindowEdgeDetector.onConfigurationChanged();
        }
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            notificationPanelViewController.mMascotViewContainer.updateRes();
        }
        if (LsRune.SECURITY_CONTINUITY_LOCKSCREEN_VI) {
            notificationPanelViewController.mContinuityLockScreenContainerController.updateLayout$6();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int i = SceneContainerFlag.$r8$clinit;
        if (this.mCurrentPanelAlpha != 255) {
            canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), this.mAlphaPaint);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "NPV", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        int i = SceneContainerFlag.$r8$clinit;
        return !this.mDozing;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        return this.mTouchHandler.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        int i2 = SceneContainerFlag.$r8$clinit;
        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mRtlChangeListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda0.f$0;
            if (i != notificationPanelViewController.mOldLayoutDirection) {
                notificationPanelViewController.mOldLayoutDirection = i;
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        int i = SceneContainerFlag.$r8$clinit;
        return true;
    }
}
