package com.android.keyguard;

import android.content.Context;
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
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import dagger.Lazy;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecLockIconViewController extends LegacyLockIconViewController implements PluginLockListener.State {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass8 mConfigurationListener;
    public int mCurrentOrientation;
    public int mDisplayType;
    public boolean mIsDefaultLockViewMode;
    public boolean mIsLockStarEnabled;
    public final AnonymousClass7 mKeyguardStateCallback;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final LockPatternUtils mLockPatternUtils;
    public final AnonymousClass1 mLockStarCallback;
    public final PluginLockData mPluginLockData;
    public final PluginLockStarManager mPluginLockStarManager;
    public boolean mRunningFace;
    public final SelectedUserInteractor mSelectedUserInteractor;
    private SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final AnonymousClass5 mStatusBarStateListener;
    public SecLockIconView mView;
    public final Lazy mViewMediatorCallbackLazy;

    /* renamed from: $r8$lambda$3-mmmdB6ihcNDQifpohr5hGzj84, reason: not valid java name */
    public static void m849$r8$lambda$3mmmdB6ihcNDQifpohr5hGzj84(SecLockIconViewController secLockIconViewController) {
        secLockIconViewController.mView.mIsOneHandModeEnabled = secLockIconViewController.mSettingsHelper.isOneHandModeRunning();
        secLockIconViewController.updateVisibility$1();
    }

    /* renamed from: -$$Nest$munregisterCallbacks, reason: not valid java name */
    public static void m850$$Nest$munregisterCallbacks(SecLockIconViewController secLockIconViewController) {
        ((ConfigurationControllerImpl) secLockIconViewController.mConfigurationController).removeCallback(secLockIconViewController.mConfigurationListener);
        secLockIconViewController.mKeyguardUpdateMonitor.removeCallback(secLockIconViewController.mKeyguardUpdateMonitorCallback);
        secLockIconViewController.mStatusBarStateController.removeCallback(secLockIconViewController.mStatusBarStateListener);
        ((KeyguardStateControllerImpl) secLockIconViewController.mKeyguardStateController).removeCallback(secLockIconViewController.mKeyguardStateCallback);
        secLockIconViewController.mSettingsHelper.unregisterCallback(secLockIconViewController.mSettingsListener);
        secLockIconViewController.mView.setAccessibilityDelegate(new View.AccessibilityDelegate(secLockIconViewController) { // from class: com.android.keyguard.SecLockIconViewController.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                view.setClickable(false);
            }
        });
        secLockIconViewController.mPluginLockStarManager.unregisterCallback("SecLockIconViewController");
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.keyguard.SecLockIconViewController$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.keyguard.SecLockIconViewController$5] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.keyguard.SecLockIconViewController$7] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.keyguard.SecLockIconViewController$8] */
    public SecLockIconViewController(SecLockIconView secLockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources, LockPatternUtils lockPatternUtils, SettingsHelper settingsHelper, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, Context context, Lazy lazy, Lazy lazy2, SelectedUserInteractor selectedUserInteractor, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, PluginLockStarManager pluginLockStarManager, KeyguardEditModeController keyguardEditModeController) {
        super(secLockIconView, statusBarStateController, keyguardUpdateMonitor, keyguardViewController, keyguardStateController, falsingManager, authController, dumpManager, accessibilityManager, configurationController, delayableExecutor, vibratorHelper, authRippleController, resources, keyguardTransitionInteractor, keyguardInteractor, featureFlags, primaryBouncerInteractor, context, lazy, keyguardEditModeController);
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SecLockIconViewController.m849$r8$lambda$3mmmdB6ihcNDQifpohr5hGzj84(SecLockIconViewController.this);
            }
        };
        this.mIsDefaultLockViewMode = true;
        this.mLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.SecLockIconViewController.1
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                if (z2) {
                    secLockIconViewController.mIsLockStarEnabled = z;
                }
                SecLockIconView secLockIconView2 = secLockIconViewController.mView;
                secLockIconView2.mIsLockStarEnabled = z;
                if (z) {
                    return;
                }
                secLockIconView2.setAlpha(1.0f);
            }
        };
        this.mIsLockStarEnabled = false;
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.SecLockIconViewController.5
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                FeatureFlags featureFlags2 = secLockIconViewController.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags2.getClass();
                secLockIconViewController.mIsDozing = z;
                secLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mStatusBarState = i;
                secLockIconViewController.updateVisibility$1();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.SecLockIconViewController.6
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mView.updateLockIconViewLayoutParams(secLockIconViewController.mSelectedUserInteractor.getSelectedUserId(false));
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                    if (secLockIconViewController.mRunningFace != z) {
                        secLockIconViewController.mRunningFace = z;
                        secLockIconViewController.updateVisibility$1();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerStateChanged(boolean z) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onKeyguardBouncerStateChanged : ", "SecLockIconViewController", z);
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mIsBouncerShowing = z;
                secLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    return;
                }
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                if (secLockIconViewController.mKeyguardUpdateMonitor.isTimerRunning()) {
                    secLockIconViewController.mCanDismissLockScreen = false;
                    secLockIconViewController.updateVisibility$1();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                int strongAuthForUser = secLockIconViewController.mKeyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
                if ((strongAuthForUser & 1) == 0 && (strongAuthForUser & 2) == 0 && (strongAuthForUser & 4) == 0 && (strongAuthForUser & 8) == 0 && (strongAuthForUser & 16) == 0 && (strongAuthForUser & 32) == 0) {
                    return;
                }
                secLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustChanged(int i) {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mView.updateLockIconViewLayoutParams(secLockIconViewController.mSelectedUserInteractor.getSelectedUserId());
            }
        };
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.keyguard.SecLockIconViewController.7
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) secLockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
                secLockIconViewController.updateKeyguardShowing();
                secLockIconViewController.updateVisibility$1();
                secLockIconViewController.mView.updateLockIconViewLayoutParams(secLockIconViewController.mSelectedUserInteractor.getSelectedUserId());
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                boolean z = secLockIconViewController.mCanDismissLockScreen;
                secLockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) secLockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
                secLockIconViewController.updateKeyguardShowing();
                if (z != secLockIconViewController.mCanDismissLockScreen) {
                    secLockIconViewController.updateVisibility$1();
                }
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.SecLockIconViewController.8
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = SecLockIconViewController.$r8$clinit;
                SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                secLockIconViewController.mUnlockedLabel = secLockIconViewController.getResources().getString(R.string.accessibility_unlock_button);
                secLockIconViewController.mLockedLabel = secLockIconViewController.getResources().getString(R.string.accessibility_lock_icon);
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    int i2 = secLockIconViewController.mDisplayType;
                    int i3 = configuration.semDisplayDeviceType;
                    if (i2 != i3) {
                        secLockIconViewController.mDisplayType = i3;
                        PluginLockStar pluginLockStar = secLockIconViewController.mPluginLockStarManager.mPluginLockStar;
                        boolean z = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
                        secLockIconViewController.mIsLockStarEnabled = z;
                        SecLockIconView secLockIconView2 = secLockIconViewController.mView;
                        secLockIconView2.mIsLockStarEnabled = z;
                        if (!z) {
                            secLockIconView2.setAlpha(1.0f);
                        }
                        secLockIconViewController.updateVisibility$1();
                    }
                }
                int i4 = secLockIconViewController.mCurrentOrientation;
                int i5 = configuration.orientation;
                if (i4 != i5) {
                    secLockIconViewController.mCurrentOrientation = i5;
                    secLockIconViewController.updateVisibility$1();
                    secLockIconViewController.mView.updateLockIconViewLayoutParams(secLockIconViewController.mSelectedUserInteractor.getSelectedUserId(false));
                }
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        this.mSettingsHelper = settingsHelper;
        this.mViewMediatorCallbackLazy = lazy2;
        this.mSelectedUserInteractor = selectedUserInteractor;
        pluginLockMediator.registerStateCallback(this);
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
            SystemUIImageView systemUIImageView = this.mView.mSecLockIcon;
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

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final Bundle onUiInfoRequested(boolean z) {
        boolean shouldShowLockIcon = shouldShowLockIcon();
        Bundle bundle = new Bundle();
        bundle.putInt("lock_icon_visibility", shouldShowLockIcon ? 0 : 4);
        return bundle;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onViewModeChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onViewModeChanged mode: ", "LockIconViewController");
        boolean z = i == 0;
        if (this.mIsDefaultLockViewMode != z) {
            this.mIsDefaultLockViewMode = z;
            updateVisibility$1();
        }
    }

    public final void registerCallbacks$2() {
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        this.mSettingsHelper.registerCallback(this.mSettingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING));
        this.mView.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.keyguard.SecLockIconViewController.3
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                view.setClickable(false);
            }
        });
        this.mDisplayType = getResources().getConfiguration().semDisplayDeviceType;
        this.mPluginLockStarManager.registerCallback("SecLockIconViewController", this.mLockStarCallback);
        updateVisibility$1();
    }

    public final boolean shouldShowLockIcon() {
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mSecure && this.mIsDefaultLockViewMode;
        if (!z) {
            return z;
        }
        PluginLockData pluginLockData = this.mPluginLockData;
        if (pluginLockData.isAvailable()) {
            return pluginLockData.getVisibility(7) == 0;
        }
        return z;
    }

    @Override // com.android.keyguard.LegacyLockIconViewController
    public final void updateVisibility$1() {
        Log.d("SecLockIconViewController", "updateVisibility");
        if (!this.mIsKeyguardShowing || !shouldShowLockIcon() || this.mIsBiometricToastViewAnimating) {
            this.mView.setVisibility(4);
            return;
        }
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(selectedUserId);
        if (this.mShowUnlockIcon && !userHasTrust && !this.mRunningFace && !this.mIsDozing && keyguardUpdateMonitor.isFaceOptionEnabled() && !((ViewMediatorCallback) this.mViewMediatorCallbackLazy.get()).isScreenOn()) {
            Log.d("SecLockIconViewController", "Skip update unlock icon on turning off screen");
            return;
        }
        boolean isLockScreen = isLockScreen();
        boolean z = this.mCanDismissLockScreen;
        this.mShowLockIcon = !z && isLockScreen;
        this.mShowUnlockIcon = z && isLockScreen;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateVisibility : isLockScreen = ", " mShowLockIcon = ", isLockScreen);
        m.append(this.mShowLockIcon);
        m.append(" mShowUnlockIcon = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(m, this.mShowUnlockIcon, "SecLockIconViewController");
        boolean z2 = userHasTrust && isLockScreen;
        SecLockIconView secLockIconView = this.mView;
        SystemUIImageView systemUIImageView = secLockIconView.mSecLockIcon;
        CharSequence contentDescription = secLockIconView.getContentDescription();
        if (z2) {
            Drawable icon = this.mView.getIcon(R.drawable.lock_ic_lock_mtrl_19);
            SystemUIImageView systemUIImageView2 = this.mView.mSecLockIcon;
            if (systemUIImageView2 != null) {
                systemUIImageView2.setImageDrawable(icon);
            }
            this.mView.setVisibility(0);
            this.mView.setContentDescription(getResources().getString(R.string.kg_extend_lock_content_description));
            systemUIImageView.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("top") ? R.drawable.extend_lock_btn_bg_ripple_whitebg : R.drawable.extend_lock_btn_bg_ripple);
            systemUIImageView.setClickable(true);
            View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    SecLockIconViewController secLockIconViewController = SecLockIconViewController.this;
                    secLockIconViewController.mKeyguardUpdateMonitor.onLockIconPressed();
                    secLockIconViewController.mLockPatternUtils.requireCredentialEntry(secLockIconViewController.mSelectedUserInteractor.getSelectedUserId());
                    return false;
                }
            };
            systemUIImageView.setOnLongClickListener(onLongClickListener);
            this.mView.setOnLongClickListener(onLongClickListener);
            acceptModifier(false);
        } else if (this.mShowUnlockIcon) {
            Drawable icon2 = this.mView.getIcon(R.drawable.lock_unlock);
            SystemUIImageView systemUIImageView3 = this.mView.mSecLockIcon;
            if (systemUIImageView3 != null) {
                systemUIImageView3.setImageDrawable(icon2);
            }
            this.mView.setVisibility(0);
            this.mView.setContentDescription(this.mUnlockedLabel);
            if (icon2 instanceof AnimationDrawable) {
                ((AnimationDrawable) icon2).start();
            }
            acceptModifier(false);
        } else if (this.mShowLockIcon) {
            Drawable icon3 = this.mView.getIcon(R.drawable.lock_ic_lock_mtrl_00);
            SystemUIImageView systemUIImageView4 = this.mView.mSecLockIcon;
            if (systemUIImageView4 != null) {
                systemUIImageView4.setImageDrawable(icon3);
            }
            this.mView.setVisibility(0);
            this.mView.setContentDescription(this.mLockedLabel);
            acceptModifier(true);
        } else {
            this.mView.setVisibility(4);
            this.mView.setContentDescription(null);
        }
        if (!Objects.equals(contentDescription, this.mView.getContentDescription()) && this.mView.getContentDescription() != null) {
            SecLockIconView secLockIconView2 = this.mView;
            secLockIconView2.announceForAccessibility(secLockIconView2.getContentDescription());
        }
        if (!z2 && systemUIImageView.getBackground() != null) {
            systemUIImageView.setBackground(null);
            systemUIImageView.setClickable(false);
        }
        this.mView.updateScanningFaceAnimation(systemUIImageView);
    }
}
