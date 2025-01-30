package com.android.keyguard;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DualDarInnerLockScreenController {
    public KeyguardInputView mBaseView;
    public KeyguardInputViewController mBaseViewController;
    public final Context mContext;
    public final DualDarKeyguardSecurityCallback mDualDarKeyguardSecurityCallback;
    public final Handler mHandler;
    public boolean mIsPassword;
    public final KeyguardSecurityCallback mKeyguardCallback;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final LayoutInflater mLayoutInflater;
    public final LockPatternUtils mLockPatternUtils;
    public int mNavigationBarHeight;
    public final KeyguardSecurityContainer mParent;
    public final KeyguardSecurityContainerController mParentController;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
    public boolean mIsImeShown = false;
    public final KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.DualDarInnerLockScreenController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDualDARInnerLockscreenRequirementChanged(int i) {
            DualDarInnerLockScreenController dualDarInnerLockScreenController = DualDarInnerLockScreenController.this;
            if (dualDarInnerLockScreenController.mUpdateMonitor.isDualDarInnerAuthRequired(i)) {
                return;
            }
            DualDarInnerLockScreenController.m351$$Nest$mdismissInnerLockScreen(dualDarInnerLockScreenController, i);
        }
    };
    public final C06504 mCallback = new C06504();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.DualDarInnerLockScreenController$4 */
    public final class C06504 implements KeyguardSecurityCallback {
        public C06504() {
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void dismiss(int i, KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
            DualDarInnerLockScreenController.this.mHandler.post(new DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0(this, i, 0));
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void onCancelClicked() {
            KeyguardSecurityCallback keyguardSecurityCallback = DualDarInnerLockScreenController.this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.onCancelClicked();
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void onUserInput() {
            KeyguardSecurityCallback keyguardSecurityCallback = DualDarInnerLockScreenController.this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.onUserInput();
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void reportUnlockAttempt(int i, int i2, boolean z) {
            DualDarInnerLockScreenController dualDarInnerLockScreenController = DualDarInnerLockScreenController.this;
            if (z) {
                dualDarInnerLockScreenController.mLockPatternUtils.reportSuccessfulPasswordAttempt(i);
                return;
            }
            KeyguardSecurityCallback keyguardSecurityCallback = dualDarInnerLockScreenController.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.reportUnlockAttempt(i, i2, z);
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void reset() {
            KeyguardSecurityCallback keyguardSecurityCallback = DualDarInnerLockScreenController.this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.reset();
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void userActivity() {
            KeyguardSecurityCallback keyguardSecurityCallback = DualDarInnerLockScreenController.this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.userActivity();
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
            return DualDarInnerLockScreenController.this.mHandler.post(new DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0(this, i, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final Context mContext;
        public final Handler mHandler;
        public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
        public final LayoutInflater mLayoutInflater;
        public final KeyguardSecSecurityContainer mParent;
        public final KeyguardUpdateMonitor mUpdateMonitor;

        public Factory(Context context, KeyguardSecSecurityContainer keyguardSecSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory) {
            this.mContext = context;
            this.mParent = keyguardSecSecurityContainer;
            this.mUpdateMonitor = keyguardUpdateMonitor;
            this.mHandler = handler;
            this.mLayoutInflater = layoutInflater;
            this.mKeyguardSecurityViewControllerFactory = factory;
        }
    }

    /* renamed from: -$$Nest$mdismissInnerLockScreen, reason: not valid java name */
    public static void m351$$Nest$mdismissInnerLockScreen(DualDarInnerLockScreenController dualDarInnerLockScreenController, int i) {
        dualDarInnerLockScreenController.mHandler.removeCallbacksAndMessages(null);
        int mainUserId = ((KnoxStateMonitorImpl) dualDarInnerLockScreenController.mKnoxStateMonitor).getMainUserId(i);
        KeyguardInputView keyguardInputView = dualDarInnerLockScreenController.mBaseView;
        if (keyguardInputView != null && keyguardInputView.isAttachedToWindow() && mainUserId == KeyguardUpdateMonitor.getCurrentUser()) {
            dualDarInnerLockScreenController.hide();
            KeyguardSecurityCallback keyguardSecurityCallback = dualDarInnerLockScreenController.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.dismiss(i, KeyguardSecurityModel.SecurityMode.Invalid, true);
            }
        }
    }

    public DualDarInnerLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardSecurityContainerController keyguardSecurityContainerController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityCallback keyguardSecurityCallback, DualDarKeyguardSecurityCallback dualDarKeyguardSecurityCallback, Handler handler, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory) {
        this.mContext = context;
        this.mHandler = handler;
        this.mParent = keyguardSecurityContainer;
        this.mParentController = keyguardSecurityContainerController;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardCallback = keyguardSecurityCallback;
        this.mDualDarKeyguardSecurityCallback = dualDarKeyguardSecurityCallback;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mLayoutInflater = layoutInflater;
        this.mKeyguardSecurityViewControllerFactory = factory;
    }

    public final void hide() {
        KeyguardInputView keyguardInputView = this.mBaseView;
        if (keyguardInputView == null || !keyguardInputView.isAttachedToWindow()) {
            return;
        }
        this.mBaseViewController.startDisappearAnimation(new Runnable(this) { // from class: com.android.keyguard.DualDarInnerLockScreenController.3
            @Override // java.lang.Runnable
            public final void run() {
            }
        });
        this.mParent.removeView(this.mBaseView);
        this.mBaseView = null;
        ((KeyguardSecSecurityContainerController$$ExternalSyntheticLambda3) this.mDualDarKeyguardSecurityCallback).onSecurityModeChanged(this.mParentController.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.Password);
        this.mUpdateMonitor.dispatchDualDarInnerLockScreenState(((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId()), false);
    }

    public final void updateLayoutMargins(KeyguardSecurityContainer keyguardSecurityContainer, KeyguardInputView keyguardInputView) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int rotation = DeviceState.getRotation(keyguardSecurityContainer.getResources().getConfiguration().windowConfiguration.getRotation());
        if (!LsRune.SECURITY_NAVBAR_ENABLED || keyguardInputView == null) {
            return;
        }
        Resources resources = keyguardSecurityContainer.getResources();
        int i6 = 0;
        this.mIsPassword = this.mLockPatternUtils.getCredentialTypeForUser(((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId())) == 4;
        this.mNavigationBarHeight = resources.getDimensionPixelSize(R.dimen.notification_content_margin_end);
        boolean isTablet = DeviceType.isTablet();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (isTablet) {
            int dimensionPixelSize = keyguardSecurityContainer.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.kg_emergency_button_margin_bottom_for_tablet_fingerprint) + DeviceState.sInDisplayFingerprintHeight;
            boolean isInDisplayFingerprintMarginAccepted = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
            if (rotation == 1 || rotation == 2 || rotation == 3) {
                if (!this.mIsPassword || !this.mIsImeShown) {
                    i6 = this.mNavigationBarHeight;
                }
            } else if (!this.mIsPassword || !this.mIsImeShown) {
                if (isInDisplayFingerprintMarginAccepted) {
                    i5 = dimensionPixelSize;
                    updateLayoutParams(0, 0, i5, keyguardSecurityContainer, keyguardInputView);
                    return;
                }
                i6 = this.mNavigationBarHeight;
            }
            i5 = i6;
            updateLayoutParams(0, 0, i5, keyguardSecurityContainer, keyguardInputView);
            return;
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(this.mContext) || LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !this.mBaseViewController.needsInput()) {
            int i7 = DeviceState.sInDisplayFingerprintHeight;
            boolean isInDisplayFingerprintMarginAccepted2 = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
            if (rotation == 1) {
                i7 = this.mNavigationBarHeight;
                i = i7;
            } else if (rotation != 3) {
                boolean z = (!DeviceState.isInDisplayFpSensorPositionHigh()) & isInDisplayFingerprintMarginAccepted2;
                if (this.mIsPassword && this.mIsImeShown) {
                    i7 = 0;
                } else if (!z) {
                    i7 = this.mNavigationBarHeight;
                }
                i4 = i7;
                i3 = 0;
                i2 = 0;
            } else {
                if (!isInDisplayFingerprintMarginAccepted2) {
                    i7 = this.mNavigationBarHeight;
                }
                i = this.mNavigationBarHeight;
            }
            i2 = i;
            i3 = i7;
            i4 = 0;
        } else {
            i3 = 0;
            i2 = 0;
            i4 = 0;
        }
        updateLayoutParams(i3, i2, i4, keyguardSecurityContainer, keyguardInputView);
    }

    public final void updateLayoutParams(int i, int i2, int i3, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardInputView keyguardInputView) {
        if (keyguardInputView == null) {
            Log.d("DualDarInnerLockScreenController", "updateLayoutParams securityViewFlipper is null");
            return;
        }
        Resources resources = keyguardSecurityContainer.getResources();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) keyguardInputView.getLayoutParams();
        if (DeviceType.isTablet()) {
            ((ViewGroup.MarginLayoutParams) layoutParams).width = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_message_area_width_tablet);
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
        } else if (LsRune.SECURITY_SUB_DISPLAY_LOCK && !DeviceState.isSmartViewDisplayWithFitToAspectRatio(keyguardSecurityContainer.getContext())) {
            boolean z = keyguardSecurityContainer.getContext().getResources().getConfiguration().semDisplayDeviceType == 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = z ? SecurityUtils.getMainSecurityViewFlipperSize(keyguardSecurityContainer.getContext(), this.mIsPassword) : -1;
            layoutParams.startToStart = z ? 0 : -1;
            layoutParams.endToEnd = z ? 0 : -1;
        }
        layoutParams.setMargins(i, 0, i2, i3);
        keyguardInputView.setLayoutParams(layoutParams);
    }
}
