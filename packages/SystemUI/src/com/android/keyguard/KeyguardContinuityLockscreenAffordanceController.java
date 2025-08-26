package com.android.keyguard;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.ViewController;
import com.android.systemui.widget.SystemUITextView;

/* loaded from: classes.dex */
public class KeyguardContinuityLockscreenAffordanceController extends ViewController {
    public LinearLayout mContinuityLayout;
    public SystemUITextView mContinuitytextView;
    public boolean mForceIsDismissible;
    public final AnonymousClass1 mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final StatusBarStateController mStatusBarStateController;
    public final AnonymousClass2 mStatusBarStateListener;
    public BaseKeyguardCallback mUpdateMonitorCallback;

    /* renamed from: com.android.keyguard.KeyguardContinuityLockscreenAffordanceController$2, reason: invalid class name */
    public class AnonymousClass2 implements StatusBarStateController.StateListener {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            KeyguardContinuityLockscreenAffordanceController keyguardContinuityLockscreenAffordanceController = KeyguardContinuityLockscreenAffordanceController.this;
            if (i != 1 || ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isFoldOpened() || !keyguardContinuityLockscreenAffordanceController.mForceIsDismissible || !keyguardContinuityLockscreenAffordanceController.mKeyguardUpdateMonitor.getUserCanSkipBouncer(keyguardContinuityLockscreenAffordanceController.mSelectedUserInteractor.getSelectedUserId())) {
                ((KeyguardContinuityLockscreenAffordanceArea) ((ViewController) keyguardContinuityLockscreenAffordanceController).mView).setVisibility(8);
            } else {
                ((KeyguardContinuityLockscreenAffordanceArea) ((ViewController) keyguardContinuityLockscreenAffordanceController).mView).setVisibility(0);
                ((KeyguardContinuityLockscreenAffordanceArea) ((ViewController) keyguardContinuityLockscreenAffordanceController).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardContinuityLockscreenAffordanceController$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((KeyguardContinuityLockscreenAffordanceArea) ((ViewController) KeyguardContinuityLockscreenAffordanceController.this).mView).mContinuityLottieView.playAnimation();
                    }
                });
            }
        }
    }

    public class BaseKeyguardCallback extends KeyguardUpdateMonitorCallback {
        public BaseKeyguardCallback() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onForceIsDismissibleChanged(boolean z) {
            KeyguardContinuityLockscreenAffordanceController keyguardContinuityLockscreenAffordanceController = KeyguardContinuityLockscreenAffordanceController.this;
            keyguardContinuityLockscreenAffordanceController.mForceIsDismissible = z;
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onForceIsDismissibleChanged : "), keyguardContinuityLockscreenAffordanceController.mForceIsDismissible, "KeyguardContinuityLockscreenAffordanceController");
            if (keyguardContinuityLockscreenAffordanceController.mForceIsDismissible) {
                keyguardContinuityLockscreenAffordanceController.updateLayout$6();
            } else {
                ((KeyguardContinuityLockscreenAffordanceArea) ((ViewController) keyguardContinuityLockscreenAffordanceController).mView).setVisibility(8);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardContinuityLockscreenAffordanceController$1] */
    public KeyguardContinuityLockscreenAffordanceController(KeyguardContinuityLockscreenAffordanceArea keyguardContinuityLockscreenAffordanceArea, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardContinuityLockscreenAffordanceArea);
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.keyguard.KeyguardContinuityLockscreenAffordanceController.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                KeyguardContinuityLockscreenAffordanceController keyguardContinuityLockscreenAffordanceController = KeyguardContinuityLockscreenAffordanceController.this;
                int selectedUserId = keyguardContinuityLockscreenAffordanceController.mSelectedUserInteractor.getSelectedUserId();
                if (!keyguardContinuityLockscreenAffordanceController.mKeyguardUpdateMonitor.isSecure(selectedUserId) || keyguardContinuityLockscreenAffordanceController.mKeyguardUpdateMonitor.getUserCanSkipBouncer(selectedUserId)) {
                    return;
                }
                ((KeyguardContinuityLockscreenAffordanceArea) ((ViewController) keyguardContinuityLockscreenAffordanceController).mView).setVisibility(8);
            }
        };
        this.mStatusBarStateListener = new AnonymousClass2();
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.util.ViewController
    public final void destroy() {
        super.destroy();
        onViewDetached();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new BaseKeyguardCallback();
        }
        keyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new BaseKeyguardCallback();
        }
        keyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mKeyguardStateCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
    }

    public final void updateLayout$6() {
        int rotation = DeviceState.shouldEnableKeyguardScreenRotation(getContext()) ? DeviceState.getRotation(getResources().getConfiguration().windowConfiguration.getRotation()) : 0;
        ListPopupWindow$$ExternalSyntheticOutline0.m(rotation, "updateLayout() : ", "KeyguardContinuityLockscreenAffordanceController");
        this.mContinuityLayout = (LinearLayout) ((KeyguardContinuityLockscreenAffordanceArea) this.mView).findViewById(R.id.keyguard_indication_continuity_vi_view);
        this.mContinuitytextView = (SystemUITextView) ((KeyguardContinuityLockscreenAffordanceArea) this.mView).findViewById(R.id.keyguard_indication_continuity_vi_text_view);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mContinuityLayout.getLayoutParams();
        if (rotation == 0 || rotation == 2) {
            layoutParams.gravity = 81;
            layoutParams.setMargins(0, 0, 0, (int) (DeviceState.getDisplayHeight(getContext()) * 0.2d));
        } else if (rotation == 1) {
            layoutParams.gravity = 21;
            layoutParams.setMargins(0, 0, (int) (DeviceState.getDisplayHeight(getContext()) * 0.058d), 0);
        } else {
            layoutParams.gravity = 19;
            layoutParams.setMargins((int) (DeviceState.getDisplayHeight(getContext()) * 0.058d), 0, 0, 0);
        }
        this.mContinuityLayout.setLayoutParams(layoutParams);
        this.mContinuitytextView.setText(getResources().getString(R.string.kg_continuity_lockscreen_affordance_text));
    }
}
