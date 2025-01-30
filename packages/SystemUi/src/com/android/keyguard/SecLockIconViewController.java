package com.android.keyguard;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockDataImpl;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import dagger.Lazy;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecLockIconViewController extends LockIconViewController implements PluginLockListener$State {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C08415 mConfigurationListener;
    public int mCurrentOrientation;
    public int mDisplayType;
    public boolean mIsDefaultLockViewMode;
    public boolean mIsLockStarEnabled;
    public final C08404 mKeyguardStateCallback;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final LockPatternUtils mLockPatternUtils;
    public final C08371 mLockStarCallback;
    public final PluginLockData mPluginLockData;
    public final PluginLockStarManager mPluginLockStarManager;
    public boolean mRunningFace;
    public final SettingsHelper mSettingsHelper;
    public final SecLockIconViewController$$ExternalSyntheticLambda0 mSettingsListener;
    public final Lazy mViewMediatorCallbackLazy;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.keyguard.SecLockIconViewController$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.keyguard.SecLockIconViewController$4] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.keyguard.SecLockIconViewController$5] */
    public SecLockIconViewController(SecLockIconView secLockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources, LockPatternUtils lockPatternUtils, SettingsHelper settingsHelper, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, Lazy lazy, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, PluginLockStarManager pluginLockStarManager, KeyguardEditModeController keyguardEditModeController) {
        super(secLockIconView, statusBarStateController, keyguardUpdateMonitor, keyguardViewController, keyguardStateController, falsingManager, authController, dumpManager, accessibilityManager, configurationController, delayableExecutor, vibratorHelper, authRippleController, resources, keyguardTransitionInteractor, keyguardInteractor, featureFlags, primaryBouncerInteractor, keyguardEditModeController);
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                ((SecLockIconView) secLockIconViewController.mView).mIsOneHandModeEnabled = secLockIconViewController.mSettingsHelper.isOneHandModeRunning();
                secLockIconViewController.updateVisibility();
            }
        };
        this.mIsDefaultLockViewMode = true;
        this.mLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.SecLockIconViewController.1
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarEnabled(boolean z) {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                if (z2) {
                    secLockIconViewController.mIsLockStarEnabled = z;
                }
                int i = SecLockIconViewController.$r8$clinit;
                SecLockIconView secLockIconView2 = (SecLockIconView) secLockIconViewController.mView;
                secLockIconView2.mIsLockStarEnabled = z;
                if (z) {
                    return;
                }
                secLockIconView2.setAlpha(1.0f);
            }
        };
        this.mIsLockStarEnabled = false;
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.SecLockIconViewController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                int i = SecLockIconViewController.$r8$clinit;
                ((SecLockIconView) SecLockIconViewController.this.mView).updateLockIconViewLayoutParams();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                    if (secLockIconViewController.mRunningFace != z) {
                        secLockIconViewController.mRunningFace = z;
                        secLockIconViewController.updateVisibility();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    return;
                }
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                if (secLockIconViewController.mKeyguardUpdateMonitor.isTimerRunning()) {
                    secLockIconViewController.mCanDismissLockScreen = false;
                    secLockIconViewController.updateVisibility();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                int strongAuthForUser = secLockIconViewController.mKeyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
                if ((strongAuthForUser & 1) == 0 && (strongAuthForUser & 2) == 0 && (strongAuthForUser & 4) == 0 && (strongAuthForUser & 8) == 0 && (strongAuthForUser & 16) == 0 && (strongAuthForUser & 32) == 0) {
                    return;
                }
                secLockIconViewController.updateVisibility();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustChanged(int i) {
                int i2 = SecLockIconViewController.$r8$clinit;
                ((SecLockIconView) SecLockIconViewController.this.mView).updateLockIconViewLayoutParams();
            }
        };
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.keyguard.SecLockIconViewController.4
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) secLockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
                secLockIconViewController.updateKeyguardShowing();
                secLockIconViewController.updateVisibility();
                ((SecLockIconView) secLockIconViewController.mView).updateLockIconViewLayoutParams();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.SecLockIconViewController.5
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                if (z) {
                    int i = secLockIconViewController.mDisplayType;
                    int i2 = configuration.semDisplayDeviceType;
                    if (i != i2) {
                        secLockIconViewController.mDisplayType = i2;
                        PluginLockStar pluginLockStar = secLockIconViewController.mPluginLockStarManager.mPluginLockStar;
                        boolean z2 = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
                        secLockIconViewController.mIsLockStarEnabled = z2;
                        SecLockIconView secLockIconView2 = (SecLockIconView) secLockIconViewController.mView;
                        secLockIconView2.mIsLockStarEnabled = z2;
                        if (!z2) {
                            secLockIconView2.setAlpha(1.0f);
                        }
                        secLockIconViewController.updateVisibility();
                    }
                }
                int i3 = secLockIconViewController.mCurrentOrientation;
                int i4 = configuration.orientation;
                if (i3 != i4) {
                    secLockIconViewController.mCurrentOrientation = i4;
                    secLockIconViewController.updateVisibility();
                    ((SecLockIconView) secLockIconViewController.mView).updateLockIconViewLayoutParams();
                }
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        this.mSettingsHelper = settingsHelper;
        this.mViewMediatorCallbackLazy = lazy;
        ((PluginLockMediatorImpl) pluginLockMediator).registerStateCallback(this);
        this.mPluginLockData = pluginLockData;
        this.mPluginLockStarManager = pluginLockStarManager;
    }

    public final void acceptModifier(boolean z) {
        boolean z2;
        boolean z3 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (z3) {
            z2 = this.mIsLockStarEnabled;
        } else {
            PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
            z2 = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
        }
        if (z2) {
            PluginLockStar pluginLockStar2 = pluginLockStarManager.mPluginLockStar;
            SystemUIImageView systemUIImageView = ((SecLockIconView) this.mView).mSecLockIcon;
            PluginLockStar.Modifier modifier = pluginLockStar2.getModifier("lockIconVisibility");
            PluginLockStar.Modifier modifier2 = pluginLockStar2.getModifier("lockIconAlpha");
            PluginLockStar.Modifier modifier3 = pluginLockStar2.getModifier("lockIconColor");
            PluginLockStar.Modifier modifier4 = pluginLockStar2.getModifier("lockIconDrawable");
            PluginLockStar.Modifier modifier5 = pluginLockStar2.getModifier("unlockIconDrawable");
            if (modifier != null) {
                modifier.accept(this.mView);
            }
            if (modifier2 != null) {
                if (systemUIImageView.getAlpha() != 1.0f) {
                    systemUIImageView.setAlpha(1.0f);
                }
                modifier2.accept(systemUIImageView);
            }
            if (modifier3 != null) {
                modifier3.accept(systemUIImageView);
            }
            if (z) {
                if (modifier4 != null) {
                    modifier4.accept(systemUIImageView);
                }
            } else if (modifier5 != null) {
                modifier5.accept(systemUIImageView);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final Bundle onUiInfoRequested(boolean z) {
        boolean shouldShowLockIcon = shouldShowLockIcon();
        Bundle bundle = new Bundle();
        bundle.putInt("lock_icon_visibility", shouldShowLockIcon ? 0 : 4);
        return bundle;
    }

    @Override // com.android.keyguard.LockIconViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, Settings.System.getUriFor("any_screen_running"));
        ((SecLockIconView) this.mView).setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.keyguard.SecLockIconViewController.2
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                view.setClickable(false);
            }
        });
        this.mDisplayType = getResources().getConfiguration().semDisplayDeviceType;
        this.mPluginLockStarManager.registerCallback("SecLockIconViewController", this.mLockStarCallback);
        updateVisibility();
    }

    @Override // com.android.keyguard.LockIconViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mKeyguardStateCallback);
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
        this.mPluginLockStarManager.unregisterCallback("SecLockIconViewController");
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onViewModeChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onViewModeChanged mode: ", i, "LockIconViewController");
        boolean z = i == 0;
        if (this.mIsDefaultLockViewMode != z) {
            this.mIsDefaultLockViewMode = z;
            updateVisibility();
        }
    }

    public final boolean shouldShowLockIcon() {
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mSecure && this.mIsDefaultLockViewMode;
        if (!z) {
            return z;
        }
        PluginLockData pluginLockData = this.mPluginLockData;
        if (((PluginLockDataImpl) pluginLockData).isAvailable()) {
            return ((PluginLockDataImpl) pluginLockData).getVisibility(7) == 0;
        }
        return z;
    }

    @Override // com.android.keyguard.LockIconViewController
    public final void updateVisibility() {
        if (!this.mIsKeyguardShowing || !shouldShowLockIcon() || this.mIsBiometricToastViewAnimating) {
            ((SecLockIconView) this.mView).setVisibility(4);
            return;
        }
        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(currentUser);
        if (this.mShowUnlockIcon && !userHasTrust && !this.mRunningFace && !this.mIsDozing && keyguardUpdateMonitor.isFaceOptionEnabled() && !((ViewMediatorCallback) this.mViewMediatorCallbackLazy.get()).isScreenOn()) {
            Log.d("SecLockIconViewController", "Skip update unlock icon on turning off screen");
            return;
        }
        boolean z = (this.mIsDozing || this.mIsBouncerShowing || this.mStatusBarState != 1) ? false : true;
        boolean z2 = this.mCanDismissLockScreen;
        this.mShowLockIcon = !z2 && z;
        this.mShowUnlockIcon = z2 && z;
        boolean z3 = userHasTrust && z;
        SecLockIconView secLockIconView = (SecLockIconView) this.mView;
        SystemUIImageView systemUIImageView = secLockIconView.mSecLockIcon;
        CharSequence contentDescription = secLockIconView.getContentDescription();
        if (z3) {
            ((SecLockIconView) this.mView).setImageDrawable(((SecLockIconView) this.mView).getIcon(R.drawable.lock_ic_lock_mtrl_19));
            ((SecLockIconView) this.mView).setVisibility(0);
            ((SecLockIconView) this.mView).setContentDescription(getResources().getString(R.string.kg_extend_lock_content_description));
            systemUIImageView.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("top") ? R.drawable.extend_lock_btn_bg_ripple_whitebg : R.drawable.extend_lock_btn_bg_ripple);
            systemUIImageView.setBackgroundResource(R.drawable.extend_lock_btn_bg_ripple);
            systemUIImageView.setClickable(true);
            View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                    int i = currentUser;
                    secLockIconViewController.mKeyguardUpdateMonitor.onLockIconPressed();
                    secLockIconViewController.mLockPatternUtils.requireCredentialEntry(i);
                    return false;
                }
            };
            systemUIImageView.setOnLongClickListener(onLongClickListener);
            ((SecLockIconView) this.mView).setOnLongClickListener(onLongClickListener);
            acceptModifier(false);
        } else if (this.mShowUnlockIcon) {
            Drawable icon = ((SecLockIconView) this.mView).getIcon(R.drawable.lock_unlock);
            ((SecLockIconView) this.mView).setImageDrawable(icon);
            ((SecLockIconView) this.mView).setVisibility(0);
            ((SecLockIconView) this.mView).setContentDescription(this.mUnlockedLabel);
            if (icon instanceof AnimationDrawable) {
                ((AnimationDrawable) icon).start();
            }
            acceptModifier(false);
        } else if (this.mShowLockIcon) {
            ((SecLockIconView) this.mView).setImageDrawable(((SecLockIconView) this.mView).getIcon(R.drawable.lock_ic_lock_mtrl_00));
            ((SecLockIconView) this.mView).setVisibility(0);
            ((SecLockIconView) this.mView).setContentDescription(this.mLockedLabel);
            acceptModifier(true);
        } else {
            ((SecLockIconView) this.mView).setVisibility(4);
            ((SecLockIconView) this.mView).setContentDescription(null);
        }
        if (!Objects.equals(contentDescription, ((SecLockIconView) this.mView).getContentDescription()) && ((SecLockIconView) this.mView).getContentDescription() != null) {
            SecLockIconView secLockIconView2 = (SecLockIconView) this.mView;
            secLockIconView2.announceForAccessibility(secLockIconView2.getContentDescription());
        }
        if (!z3 && systemUIImageView.getBackground() != null) {
            systemUIImageView.setBackground(null);
            systemUIImageView.setClickable(false);
        }
        ((SecLockIconView) this.mView).updateScanningFaceAnimation(systemUIImageView);
    }
}
