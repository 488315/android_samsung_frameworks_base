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
import com.android.systemui.CscRune;
import com.android.systemui.Flags;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.Log;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;

public final class NotificationPanelView extends FrameLayout {
    public final Paint mAlphaPaint;
    public int mCurrentPanelAlpha;
    public boolean mDozing;
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mOnConfigurationChangedListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mRtlChangeListener;
    public NotificationPanelViewController.TouchHandler mTouchHandler;

    public NotificationPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mAlphaPaint = paint;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        setWillNotDraw(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        setBackgroundColor(0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchConfigurationChanged(Configuration configuration) {
        Dialog dialog;
        super.dispatchConfigurationChanged(configuration);
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        NotificationPanelViewController notificationPanelViewController = this.mOnConfigurationChangedListener.f$0;
        notificationPanelViewController.loadDimens();
        notificationPanelViewController.mKeyguardTouchAnimator.initDimens$5();
        PrivacyDialogController privacyDialogController = notificationPanelViewController.mPrivacyDialogController;
        if (privacyDialogController != null && (dialog = privacyDialogController.dialog) != null) {
            dialog.dismiss();
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
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
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
        Flags.sceneContainer();
        return !this.mDozing;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        return this.mTouchHandler.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = this.mRtlChangeListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda7.f$0;
            if (i != notificationPanelViewController.mOldLayoutDirection) {
                notificationPanelViewController.mOldLayoutDirection = i;
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        return true;
    }
}
