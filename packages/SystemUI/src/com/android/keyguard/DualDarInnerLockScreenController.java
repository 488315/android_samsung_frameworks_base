package com.android.keyguard;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

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
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
    public boolean mIsImeShown = false;
    public final KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.DualDarInnerLockScreenController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDualDARInnerLockscreenRequirementChanged(int i) {
            DualDarInnerLockScreenController dualDarInnerLockScreenController = DualDarInnerLockScreenController.this;
            if (dualDarInnerLockScreenController.mUpdateMonitor.isDualDarInnerAuthRequired(i)) {
                return;
            }
            DualDarInnerLockScreenController.m825$$Nest$mdismissInnerLockScreen(dualDarInnerLockScreenController, i);
        }
    };
    public final AnonymousClass4 mCallback = new AnonymousClass4();

    /* renamed from: com.android.keyguard.DualDarInnerLockScreenController$4, reason: invalid class name */
    public final class AnonymousClass4 implements KeyguardSecurityCallback {
        public AnonymousClass4() {
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public final void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode) {
            DualDarInnerLockScreenController.this.mHandler.post(new DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0(this, i, 1));
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
            return DualDarInnerLockScreenController.this.mHandler.post(new DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0(this, i, 0));
        }
    }

    public final class Factory {
        public final Context mContext;
        public final Handler mHandler;
        public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
        public final LayoutInflater mLayoutInflater;
        public final KeyguardSecSecurityContainer mParent;
        public final SelectedUserInteractor mSelectedUserInteractor;
        public final KeyguardUpdateMonitor mUpdateMonitor;

        public Factory(Context context, KeyguardSecSecurityContainer keyguardSecSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory, SelectedUserInteractor selectedUserInteractor) {
            this.mContext = context;
            this.mParent = keyguardSecSecurityContainer;
            this.mUpdateMonitor = keyguardUpdateMonitor;
            this.mHandler = handler;
            this.mLayoutInflater = layoutInflater;
            this.mKeyguardSecurityViewControllerFactory = factory;
            this.mSelectedUserInteractor = selectedUserInteractor;
        }
    }

    /* renamed from: -$$Nest$mdismissInnerLockScreen, reason: not valid java name */
    public static void m825$$Nest$mdismissInnerLockScreen(DualDarInnerLockScreenController dualDarInnerLockScreenController, int i) {
        dualDarInnerLockScreenController.mHandler.removeCallbacksAndMessages(null);
        int mainUserId = ((KnoxStateMonitorImpl) dualDarInnerLockScreenController.mKnoxStateMonitor).getMainUserId(i);
        KeyguardInputView keyguardInputView = dualDarInnerLockScreenController.mBaseView;
        if (keyguardInputView != null && keyguardInputView.isAttachedToWindow() && mainUserId == dualDarInnerLockScreenController.mSelectedUserInteractor.getSelectedUserId()) {
            dualDarInnerLockScreenController.hide();
            KeyguardSecurityCallback keyguardSecurityCallback = dualDarInnerLockScreenController.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.dismiss(true, i, KeyguardSecurityModel.SecurityMode.Invalid);
            }
        }
    }

    public DualDarInnerLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardSecurityContainerController keyguardSecurityContainerController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityCallback keyguardSecurityCallback, DualDarKeyguardSecurityCallback dualDarKeyguardSecurityCallback, Handler handler, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory, SelectedUserInteractor selectedUserInteractor) {
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
        this.mSelectedUserInteractor = selectedUserInteractor;
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
        ((KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2) this.mDualDarKeyguardSecurityCallback).onSecurityModeChanged(this.mParentController.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.Password);
        this.mUpdateMonitor.dispatchDualDarInnerLockScreenState(((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId()), false);
    }

    public final void updateLayoutMargins(ConstraintLayout constraintLayout, View view) {
        int i;
        int i2;
        int i3;
        int rotation = DeviceState.getRotation(constraintLayout.getResources().getConfiguration().windowConfiguration.getRotation());
        if (!LsRune.SECURITY_NAVBAR_ENABLED || view == null) {
            return;
        }
        Resources resources = constraintLayout.getResources();
        int i4 = 0;
        this.mIsPassword = this.mLockPatternUtils.getCredentialTypeForUser(((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getInnerAuthUserId(UserHandle.getCallingUserId())) == 4;
        this.mNavigationBarHeight = resources.getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        boolean isTablet = DeviceType.isTablet();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (isTablet) {
            int dimensionPixelSize = constraintLayout.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.kg_emergency_button_margin_bottom_for_tablet_fingerprint) + DeviceState.getInDisplayFingerprintHeight();
            boolean isInDisplayFingerprintMarginAccepted = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
            if (rotation == 1 || rotation == 2 || rotation == 3) {
                if (!this.mIsPassword || !this.mIsImeShown) {
                    i4 = this.mNavigationBarHeight;
                }
            } else if (!this.mIsPassword || !this.mIsImeShown) {
                i4 = isInDisplayFingerprintMarginAccepted ? dimensionPixelSize : this.mNavigationBarHeight;
            }
            updateLayoutParams(0, 0, i4, constraintLayout, view);
            return;
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(this.mContext) || LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !this.mBaseViewController.needsInput()) {
            int inDisplayFingerprintHeight = DeviceState.getInDisplayFingerprintHeight();
            boolean isInDisplayFingerprintMarginAccepted2 = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
            if (rotation == 1) {
                i = this.mNavigationBarHeight;
                i2 = i;
            } else if (rotation != 3) {
                boolean z = (!DeviceState.isInDisplayFpSensorPositionHigh()) & isInDisplayFingerprintMarginAccepted2;
                if (this.mIsPassword && this.mIsImeShown) {
                    inDisplayFingerprintHeight = 0;
                } else if (!z) {
                    inDisplayFingerprintHeight = this.mNavigationBarHeight;
                }
                i3 = inDisplayFingerprintHeight;
                i = 0;
                i2 = 0;
            } else {
                if (!isInDisplayFingerprintMarginAccepted2) {
                    inDisplayFingerprintHeight = this.mNavigationBarHeight;
                }
                i2 = this.mNavigationBarHeight;
                i = inDisplayFingerprintHeight;
            }
            i3 = 0;
        } else {
            i = 0;
            i2 = 0;
            i3 = 0;
        }
        updateLayoutParams(i, i2, i3, constraintLayout, view);
    }

    public final void updateLayoutParams(int i, int i2, int i3, ConstraintLayout constraintLayout, View view) {
        if (view == null) {
            Log.d("DualDarInnerLockScreenController", "updateLayoutParams securityViewFlipper is null");
            return;
        }
        Resources resources = constraintLayout.getResources();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        if (DeviceType.isTablet()) {
            ((ViewGroup.MarginLayoutParams) layoutParams).width = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_message_area_width_tablet);
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
        } else if (LsRune.SECURITY_SUB_DISPLAY_LOCK && !DeviceState.isSmartViewDisplayWithFitToAspectRatio(constraintLayout.getContext())) {
            Context context = constraintLayout.getContext();
            int i4 = SecurityUtils.sPINContainerBottomMargin;
            boolean z = context.getResources().getConfiguration().semDisplayDeviceType == 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = z ? SecurityUtils.getMainSecurityViewFlipperSize(constraintLayout.getContext(), this.mIsPassword) : -1;
            layoutParams.startToStart = z ? 0 : -1;
            layoutParams.endToEnd = z ? 0 : -1;
        }
        layoutParams.setMargins(i, 0, i2, i3);
        view.setLayoutParams(layoutParams);
    }
}
