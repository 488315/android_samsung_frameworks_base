package com.android.keyguard;

import android.content.res.Configuration;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeDetector;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.widget.SystemUITextView;

public final class KeyguardSwipeViewController extends KeyguardInputViewController implements KeyguardTouchSwipeCallback {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final KeyguardTouchSwipeDetector mSwipeDetector;

    public final class SwipeTouchListener implements Gefingerpoken {
        public SwipeTouchListener() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            KeyguardSwipeViewController keyguardSwipeViewController = KeyguardSwipeViewController.this;
            keyguardSwipeViewController.mSwipeDetector.setIntercept(true);
            return keyguardSwipeViewController.mSwipeDetector.onTouchEvent(motionEvent);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardSwipeViewController$1] */
    public KeyguardSwipeViewController(KeyguardSwipeView keyguardSwipeView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, AccessibilityManager accessibilityManager, ConfigurationController configurationController, KeyguardTouchSwipeDetector keyguardTouchSwipeDetector, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardSwipeView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags, selectedUserInteractor);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSwipeViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardSwipeViewController.this.mSwipeDetector.initDimens$5();
            }
        };
        this.mConfigurationController = configurationController;
        this.mSwipeDetector = keyguardTouchSwipeDetector;
        keyguardTouchSwipeDetector.mUnlockCallback = this;
        SystemUITextView systemUITextView = (SystemUITextView) ((KeyguardSwipeView) this.mView).findViewById(R.id.keyguard_indication_text);
        systemUITextView.setText(R.string.kg_swipe_active_instructions);
        if (accessibilityManager.isTouchExplorationEnabled()) {
            systemUITextView.setText(R.string.kg_voice_assistant_active_instructions);
        } else {
            systemUITextView.setText(R.string.kg_swipe_active_instructions);
        }
    }

    @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
    public final void callUserActivity() {
        if (getKeyguardSecurityCallback() != null) {
            getKeyguardSecurityCallback().userActivity();
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        Log.d("KeyguardSwipeView", "onPause()");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "onResume(reason = + ", " )", "KeyguardSwipeView");
    }

    @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
    public final void onUnlockExecuted() {
        if (getKeyguardSecurityCallback() != null) {
            getKeyguardSecurityCallback().dismiss(false, this.mSelectedUserInteractor.getSelectedUserId(false), this.mSecurityMode);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        ((KeyguardSwipeView) this.mView).mSwipeTouchListener = new SwipeTouchListener();
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        ((KeyguardSwipeView) this.mView).mSwipeTouchListener = null;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        KeyguardTouchSwipeDetector keyguardTouchSwipeDetector = this.mSwipeDetector;
        keyguardTouchSwipeDetector.isUnlockExecuted = false;
        keyguardTouchSwipeDetector.distance = 0.0f;
        keyguardTouchSwipeDetector.updateDistanceCount = 0;
        PointF pointF = keyguardTouchSwipeDetector.touchDownPos;
        pointF.x = -1.0f;
        pointF.y = -1.0f;
        KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = keyguardTouchSwipeDetector.mDynamicLockInjector;
        if (keyguardTouchDymLockInjector != null) {
            keyguardTouchDymLockInjector.resetDynamicLock();
        }
    }
}
