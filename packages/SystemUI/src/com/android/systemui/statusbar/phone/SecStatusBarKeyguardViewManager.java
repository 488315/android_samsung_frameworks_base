package com.android.systemui.statusbar.phone;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardPluginControllerImpl;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityCallback;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardTextBuilder;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.biometrics.KeyguardBiometricToastView;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.ui.BouncerView;
import com.android.systemui.bouncer.ui.BouncerViewImpl;
import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import dagger.Lazy;
import java.util.Optional;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SecStatusBarKeyguardViewManager extends StatusBarKeyguardViewManager {
    public BiometricUnlockController mBiometricUnlockController;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public int mCurrentOrientation;
    public final KeyguardFastBioUnlockController mFastBioUnlockController;
    public boolean mIsCoverClosed;
    public final KeyguardStateController mKeyguardStateController;
    public final Lazy mKeyguardUnlockAnimationControllerLazy;
    public boolean mKeyguardUnlocking;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
    public final KeyguardViewMediatorHelper mKeyguardViewMediatorHelper;
    public boolean mLastKeyguardUnlocking;
    public boolean mLaunchEditMode;
    public ViewGroup mLockIconContainer;
    public View mNotificationContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    private final SettingsHelper mSettingsHelper;
    public final Lazy mShadeControllerLazy;
    public ShadeLockscreenInteractor mShadeLockscreenInteractor;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final AnonymousClass3 mSystemUIWidgetCallback;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$3] */
    public SecStatusBarKeyguardViewManager(KeyguardViewMediatorHelper keyguardViewMediatorHelper, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, SettingsHelper settingsHelper, Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, KeyguardMessageAreaController.Factory factory, Optional<SysUIUnfoldComponent> optional, Lazy lazy2, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, ActivityStarter activityStarter, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, Lazy lazy3, Lazy lazy4, SelectedUserInteractor selectedUserInteractor, JavaAdapter javaAdapter, Lazy lazy5, StatusBarKeyguardViewManagerInteractor statusBarKeyguardViewManagerInteractor) {
        super(context, viewMediatorCallback, lockPatternUtils, sysuiStatusBarStateController, configurationController, keyguardUpdateMonitor, dreamOverlayStateController, navigationModeController, dockManager, notificationShadeWindowController, keyguardStateController, factory, optional, lazy2, latencyTracker, keyguardSecurityModel, primaryBouncerCallbackInteractor, primaryBouncerInteractor, bouncerView, alternateBouncerInteractor, udfpsOverlayInteractor, activityStarter, keyguardTransitionInteractor, coroutineDispatcher, lazy3, lazy4, selectedUserInteractor, javaAdapter, lazy5, statusBarKeyguardViewManagerInteractor);
        this.mCurrentOrientation = 1;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i) {
                SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                if (secStatusBarKeyguardViewManager.mCurrentOrientation != i) {
                    secStatusBarKeyguardViewManager.mCurrentOrientation = i;
                    secStatusBarKeyguardViewManager.updateLockContainerMargin();
                    BiometricUnlockController biometricUnlockController = secStatusBarKeyguardViewManager.mBiometricUnlockController;
                    KeyguardBiometricToastView keyguardBiometricToastView = biometricUnlockController.mBiometricToastView;
                    if (keyguardBiometricToastView != null) {
                        keyguardBiometricToastView.setViewAttribution(biometricUnlockController.mUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) biometricUnlockController.mSelectedUserInteractor.get()).getSelectedUserId(false)));
                    }
                }
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                if (DeviceType.isTablet() && LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && biometricSourceType.equals(BiometricSourceType.FINGERPRINT)) {
                    SecStatusBarKeyguardViewManager.this.updateLockContainerMargin();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onEmergencyCallAction() {
                boolean z = LsRune.SECURITY_ESIM;
                SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                if (z) {
                    secStatusBarKeyguardViewManager.mKeyguardUpdateManager.clearESimRemoved();
                }
                if (((KeyguardStateControllerImpl) secStatusBarKeyguardViewManager.mKeyguardStateController).mOccluded) {
                    secStatusBarKeyguardViewManager.reset(true);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                if (secStatusBarKeyguardViewManager.isFullscreenBouncer()) {
                    secStatusBarKeyguardViewManager.updateStates();
                }
            }
        };
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.3
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                if ((j & 512) != 0) {
                    SecStatusBarKeyguardViewManager.this.updateBouncerNavigationBar(WallpaperUtils.isWhiteKeyguardWallpaper("background"));
                }
            }
        };
        this.mConfigurationController = configurationController;
        this.mKeyguardViewMediatorHelper = keyguardViewMediatorHelper;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mFastBioUnlockController = keyguardFastBioUnlockController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardUnlockAnimationControllerLazy = lazy;
        this.mShadeControllerLazy = lazy2;
        this.mSettingsHelper = settingsHelper;
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, boolean z2, boolean z3) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            cancelPendingWakeupAction();
            if (this.mDozing && !isWakeAndUnlocking() && (!z3 || keyguardStateControllerImpl.mOccluded)) {
                this.mPendingWakeupAction = new StatusBarKeyguardViewManager.DismissWithActionRequest(onDismissAction, runnable, z, null);
                return;
            }
            boolean isFullscreenBouncer = isFullscreenBouncer();
            PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
            if (isFullscreenBouncer && this.mKeyguardUpdateManager.isActiveDismissAction()) {
                primaryBouncerInteractor.show(true);
            }
            if (z) {
                if (z2) {
                    this.mAfterKeyguardGoneAction = onDismissAction;
                    this.mKeyguardGoneCancelAction = runnable;
                    primaryBouncerInteractor.show(true);
                } else {
                    this.mAfterKeyguardGoneAction = onDismissAction;
                }
            } else if (z2) {
                primaryBouncerInteractor.setDismissAction(onDismissAction, runnable);
                primaryBouncerInteractor.show(true);
            } else {
                primaryBouncerInteractor.setDismissAction(onDismissAction, runnable);
            }
        }
        updateStates();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void folderOpenAndDismiss() {
        if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide) {
            dismissAndCollapse();
        } else {
            ((ShadeController) this.mShadeControllerLazy.get()).makeExpandedInvisible();
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final Bundle getBouncerMessage() {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate == null) {
            return null;
        }
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = delegate.$securityContainerController;
        KeyguardSecurityModel.SecurityMode securityMode = keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(keyguardSecSecurityContainerController.mSelectedUserInteractor.getSelectedUserId(false));
        KeyguardPluginControllerImpl keyguardPluginControllerImpl = keyguardSecSecurityContainerController.mKeyguardPluginController;
        keyguardPluginControllerImpl.getClass();
        Bundle bundle = new Bundle();
        int i = KeyguardPluginControllerImpl.AnonymousClass2.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
        if (i != 1 && i != 2 && i != 3) {
            return bundle;
        }
        KeyguardTextBuilder keyguardTextBuilder = keyguardPluginControllerImpl.mKeyguardTextBuilder;
        bundle.putCharSequence(PluginSubScreen.KEY_BOUNCER_MESSAGE, keyguardTextBuilder.getDefaultSecurityMessage(securityMode));
        int bouncerPromptReason = keyguardPluginControllerImpl.mViewMediatorCallback.getBouncerPromptReason();
        keyguardPluginControllerImpl.mPromptReason = bouncerPromptReason;
        String str = "";
        if (bouncerPromptReason != 0 && keyguardPluginControllerImpl.mKeyguardUpdateMonitor.getLockoutAttemptDeadline() <= 0) {
            String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, keyguardPluginControllerImpl.mPromptReason);
            if (!TextUtils.isEmpty(promptSecurityMessage)) {
                int i2 = keyguardPluginControllerImpl.mPromptReason;
                if (i2 == 2 || i2 == 7 || i2 == 17) {
                    keyguardPluginControllerImpl.mStrongAuthPopupMessage = keyguardTextBuilder.getStrongAuthTimeOutMessage(securityMode);
                } else {
                    keyguardPluginControllerImpl.mStrongAuthPopupMessage = "";
                }
                str = promptSecurityMessage;
            }
        }
        bundle.putCharSequence(PluginSubScreen.KEY_STRONG_AUTH_MESSAGE, str);
        bundle.putCharSequence(PluginSubScreen.KEY_STRONG_AUTH_POPUP_MESSAGE, keyguardPluginControllerImpl.mStrongAuthPopupMessage);
        return bundle;
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final Bundle getIncorrectBouncerMessage() {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate == null) {
            return null;
        }
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = delegate.$securityContainerController;
        int i = 0;
        KeyguardSecurityModel.SecurityMode securityMode = keyguardSecSecurityContainerController.mSecurityModel.getSecurityMode(keyguardSecSecurityContainerController.mSelectedUserInteractor.getSelectedUserId(false));
        KeyguardPluginControllerImpl keyguardPluginControllerImpl = keyguardSecSecurityContainerController.mKeyguardPluginController;
        keyguardPluginControllerImpl.getClass();
        Bundle bundle = new Bundle();
        int[] iArr = KeyguardPluginControllerImpl.AnonymousClass2.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;
        int i2 = iArr[securityMode.ordinal()];
        if (i2 != 1 && i2 != 2 && i2 != 3) {
            return bundle;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
        int remainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(2);
        int i3 = iArr[securityMode.ordinal()];
        if (i3 == 1) {
            i = R.string.kg_incorrect_pin;
        } else if (i3 == 2) {
            i = R.string.kg_incorrect_pattern;
        } else if (i3 == 3) {
            i = R.string.kg_incorrect_password;
        }
        String string = keyguardPluginControllerImpl.mContext.getResources().getString(i);
        if (remainingAttempt > 0) {
            string = ComponentActivity$1$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, " ("), keyguardPluginControllerImpl.mContext.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
        }
        bundle.putCharSequence(PluginSubScreen.KEY_INCORRECT_BOUNCER_MESSAGE, string);
        int remainingAttempt2 = keyguardUpdateMonitor.getRemainingAttempt(1);
        bundle.putCharSequence(PluginSubScreen.KEY_AUTO_WIPE_WARNING_MESSAGE, remainingAttempt2 > 0 ? keyguardPluginControllerImpl.mKeyguardTextBuilder.getWarningAutoWipeMessage(keyguardUpdateMonitor.getFailedUnlockAttempts(keyguardPluginControllerImpl.mSelectedUserInteractor.getSelectedUserId()), remainingAttempt2) : "");
        return bundle;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean getLastNavBarVisible() {
        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
        return super.getLastNavBarVisible() || this.mLastKeyguardUnlocking;
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final ViewGroup getLockIconContainer() {
        return this.mLockIconContainer;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void hide(long j, long j2) {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (((KeyguardStateControllerImpl) keyguardStateController).mShowing && !((KeyguardStateControllerImpl) keyguardStateController).mOccluded) {
            if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide) {
                Log.d("SecStatusBarKeyguardViewManager", "leaveOpenOnKeyguardHide true");
            } else {
                ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).start(false);
            }
        }
        super.hide(j, j2);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void hideBouncer(boolean z) {
        boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (z2 && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isUnlockOnFoldOpened() && LsRune.SECURITY_SWIPE_BOUNCER && !primaryBouncerInteractor.isSwipeBouncer()) {
            android.util.Log.d("SecStatusBarKeyguardViewManager", "do not hideBouncer when folder is opening");
            return;
        }
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            setShowSwipeBouncer(false);
        }
        primaryBouncerInteractor.hide();
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            delegate.$securityContainerController.setSecurityContainerVisibility(4);
        }
        if (z) {
            KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository;
            keyguardBouncerRepositoryImpl._primaryBouncerInflate.updateState(null, Boolean.FALSE);
        }
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            cancelPostAuthActions();
        }
        cancelPendingWakeupAction();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final boolean interceptRestKey(KeyEvent keyEvent) {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        return delegate != null && delegate.$securityContainerController.interceptRestKey(keyEvent);
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final boolean isLaunchEditMode() {
        return this.mLaunchEditMode;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isNavBarVisible() {
        boolean z;
        boolean z2 = LsRune.COVER_SUPPORTED && this.mIsCoverClosed;
        boolean z3 = this.mKeyguardUnlocking;
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (((KeyguardStateControllerImpl) keyguardStateController).mShowing && !((KeyguardStateControllerImpl) keyguardStateController).mOccluded) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            if (centralSurfacesImpl.mIsDlsOverlay && centralSurfacesImpl.mState == 1) {
                z = true;
                return (z2 && super.isNavBarVisible()) || z3 || z || (!((KeyguardStateControllerImpl) keyguardStateController).mShowing && !((KeyguardStateControllerImpl) keyguardStateController).mOccluded && ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone());
            }
        }
        z = false;
        if (z2) {
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final boolean isPanelFullyCollapsed() {
        return this.mShadeLockscreenInteractor.isFullyCollapsed();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean needsFullscreenBouncer() {
        return SecurityUtils.checkFullscreenBouncer(this.mKeyguardSecurityModel.getSecurityMode(this.mSelectedUserInteractor.getSelectedUserId()));
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onBackPressed() {
        resetKeyguardDismissAction();
        PrimaryBouncerInteractor primaryBouncerInteractor = super.mPrimaryBouncerInteractor;
        if (primaryBouncerInteractor.isFullyShowing()) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            centralSurfacesImpl.releaseGestureWakeLock();
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
            if (LsRune.SECURITY_ESIM) {
                this.mKeyguardUpdateManager.clearESimRemoved();
            }
            boolean z = ((CentralSurfacesImpl) this.mCentralSurfaces).mScrimController.mState == ScrimState.BOUNCER_SCRIMMED;
            if (!primaryBouncerInteractor.isScrimmed() || needsFullscreenBouncer()) {
                reset(z);
                return;
            }
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).setFoldOpenState(0);
            }
            this.mShadeLockscreenInteractor.resetViews(false);
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
            if (statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
            }
            ((CentralSurfacesImpl) this.mCentralSurfaces).userActivity();
            if (LsRune.SECURITY_SWIPE_BOUNCER && this.mPrimaryBouncerInteractor.isSwipeBouncer()) {
                showBouncerOrKeyguard(z);
            } else {
                hideBouncer(false);
            }
            updateStates();
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void onCoverSwitchStateChanged(boolean z) {
        if (LsRune.SECURITY_SWIPE_BOUNCER && this.mPrimaryBouncerInteractor.isSwipeBouncer() && z) {
            setShowSwipeBouncer(false);
        }
        this.mIsCoverClosed = z;
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        hideBouncer(true);
        updateLockContainerMargin();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void onDismissCancelled() {
        this.mShadeLockscreenInteractor.onDismissCancelled();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onFinishedGoingToSleep() {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (!primaryBouncerInteractor.isFullyShowing() || (delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate()) == null) {
            return;
        }
        delegate.$securityContainerController.onPause();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onKeyguardFadedAway$1() {
        if (LsRune.SECURITY_BOUNCER_WINDOW) {
            android.util.Log.d("SecStatusBarKeyguardViewManager", "onKeyguardFadedAway");
            final int i = 0;
            ((CentralSurfacesImpl) this.mCentralSurfaces).getNotificationShadeWindowViewController().mView.post(new Runnable(this) { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda0
                public final /* synthetic */ SecStatusBarKeyguardViewManager f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = i;
                    SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = this.f$0;
                    switch (i2) {
                        case 0:
                            ((NotificationShadeWindowControllerImpl) secStatusBarKeyguardViewManager.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            break;
                        default:
                            ((NotificationShadeWindowControllerImpl) secStatusBarKeyguardViewManager.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            break;
                    }
                }
            });
        } else {
            final int i2 = 1;
            this.mNotificationContainer.postDelayed(new Runnable(this) { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager$$ExternalSyntheticLambda0
                public final /* synthetic */ SecStatusBarKeyguardViewManager f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i2;
                    SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = this.f$0;
                    switch (i22) {
                        case 0:
                            ((NotificationShadeWindowControllerImpl) secStatusBarKeyguardViewManager.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            break;
                        default:
                            ((NotificationShadeWindowControllerImpl) secStatusBarKeyguardViewManager.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                            break;
                    }
                }
            }, 100L);
        }
        this.mShadeLockscreenInteractor.resetViewGroupFade();
        ((CentralSurfacesImpl) this.mCentralSurfaces).finishKeyguardFadingAway();
        this.mBiometricUnlockController.finishKeyguardFadingAway();
        this.mLaunchEditMode = false;
        WindowManagerGlobal.getInstance().trimMemory(40);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onStartedGoingToSleep() {
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class);
        if (keyguardVisibilityMonitor.cancelExecToken != null) {
            Log.d("KeyguardVisible", "cancelAll");
            keyguardVisibilityMonitor.cancelExecToken(false);
        }
        super.onStartedGoingToSleep();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onStartedWakingUp() {
        updateLockContainerMargin();
        super.onStartedWakingUp();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isBouncerOnFoldOpened()) {
            return;
        }
        updateResources$1();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void onTrimMemory(int i) {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            delegate.$securityContainerController.onTrimMemory(i);
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void onWakeAndUnlock() {
        ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void postSetOccluded(boolean z) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardState(keyguardStateControllerImpl.mShowing, keyguardStateControllerImpl.mOccluded);
        if (this.mKeyguardUpdateManager.isEarlyWakeUp() && z) {
            cancelPendingWakeupAction();
            reset(true);
        } else if (!this.mDozing || (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && z && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isBouncerOnFoldOpened())) {
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && this.mDozing) {
                Log.d("SecStatusBarKeyguardViewManager", "hideBounce even though fold opened");
            }
            reset(z);
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && z) {
            ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).setFoldOpenState(0);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void registerCentralSurfaces(CentralSurfaces centralSurfaces, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
        this.mBiometricUnlockController = biometricUnlockController;
        this.mShadeLockscreenInteractor = shadeLockscreenInteractor;
        this.mNotificationContainer = view;
        super.registerCentralSurfaces(centralSurfaces, shadeLockscreenInteractor, shadeExpansionStateManager, biometricUnlockController, view);
        this.mKeyguardUpdateManager.registerCallback(this.mUpdateMonitorCallback);
        this.mCurrentOrientation = this.mContext.getResources().getConfiguration().orientation;
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        if (LsRune.SECURITY_BOUNCER_WINDOW) {
            ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).registerCallback(false, this.mSystemUIWidgetCallback, 512L);
        }
        KeyguardUnlockAnimationController keyguardUnlockAnimationController = (KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get();
        keyguardUnlockAnimationController.listeners.add(new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.4
            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationStarted(boolean z, boolean z2) {
                if (z) {
                    SecStatusBarKeyguardViewManager.this.updateStates();
                }
            }
        });
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager.5
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStatePreChange(int i, int i2) {
                if (i == 1 && i2 == 0) {
                    SecStatusBarKeyguardViewManager secStatusBarKeyguardViewManager = SecStatusBarKeyguardViewManager.this;
                    if (((KeyguardStateControllerImpl) secStatusBarKeyguardViewManager.mKeyguardStateController).mShowing || !((CentralSurfacesImpl) secStatusBarKeyguardViewManager.mCentralSurfaces).mBouncerShowing) {
                        return;
                    }
                    secStatusBarKeyguardViewManager.updateStates();
                }
            }
        });
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void registerLockIconContainer(ViewGroup viewGroup) {
        this.mLockIconContainer = viewGroup;
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void requestUnlock(String str) {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardPluginControllerImpl keyguardPluginControllerImpl = delegate.$securityContainerController.mKeyguardPluginController;
            keyguardPluginControllerImpl.getClass();
            android.util.Log.d("KeyguardPluginController", "requestUnlock");
            SelectedUserInteractor selectedUserInteractor = keyguardPluginControllerImpl.mSelectedUserInteractor;
            int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
            LockscreenCredential createNone = TextUtils.isEmpty(str) ? LockscreenCredential.createNone() : keyguardPluginControllerImpl.mLockPatternUtils.isLockPasswordEnabled(selectedUserId) ? LockPatternUtils.isQualityAlphabeticPassword(keyguardPluginControllerImpl.mLockPatternUtils.getKeyguardStoredPasswordQuality(selectedUserId)) ? LockscreenCredential.createPasswordOrNone(str) : LockscreenCredential.createPinOrNone(str) : keyguardPluginControllerImpl.mLockPatternUtils.isLockPatternEnabled(selectedUserId) ? LockscreenCredential.createPattern(LockPatternUtils.byteArrayToPattern(str.getBytes())) : LockscreenCredential.createPasswordOrNone(str);
            if (createNone == null) {
                android.util.Log.e("KeyguardPluginController", "credential null : failed to get credential type");
                return;
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
            int credentialTypeForUser = keyguardUpdateMonitor.getCredentialTypeForUser(selectedUserId);
            if (keyguardUpdateMonitor.getUserHasTrust(selectedUserId) || keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock()) {
                credentialTypeForUser = -1;
            }
            if (createNone.isNone() && credentialTypeForUser != -1) {
                ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(credentialTypeForUser, "credential none, but credentialType is ", "KeyguardPluginController");
                return;
            }
            if (credentialTypeForUser == 1 || credentialTypeForUser == 2 || credentialTypeForUser == 3 || credentialTypeForUser == 4) {
                AsyncTask asyncTask = keyguardPluginControllerImpl.mPendingLockCheck;
                if (asyncTask != null) {
                    asyncTask.cancel(false);
                }
                keyguardPluginControllerImpl.mLatencyTracker.onActionStart(3);
                keyguardPluginControllerImpl.mLatencyTracker.onActionStart(4);
                keyguardPluginControllerImpl.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardPluginControllerImpl.mLockPatternUtils, createNone, selectedUserId, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardPluginControllerImpl.1
                    public final /* synthetic */ LockscreenCredential val$credential;
                    public final /* synthetic */ int val$userId;

                    public AnonymousClass1(int selectedUserId2, LockscreenCredential createNone2) {
                        r2 = selectedUserId2;
                        r3 = createNone2;
                    }

                    public final void onCancelled() {
                        KeyguardPluginControllerImpl.this.mLatencyTracker.onActionEnd(4);
                        r3.zeroize();
                    }

                    public final void onChecked(boolean z, int i) {
                        KeyguardPluginControllerImpl.this.mLatencyTracker.onActionEnd(4);
                        KeyguardPluginControllerImpl keyguardPluginControllerImpl2 = KeyguardPluginControllerImpl.this;
                        keyguardPluginControllerImpl2.mPendingLockCheck = null;
                        if (!z) {
                            KeyguardPluginControllerImpl.m835$$Nest$monPasswordChecked(keyguardPluginControllerImpl2, r2, false, i);
                        }
                        r3.zeroize();
                    }

                    public final void onEarlyMatched() {
                        KeyguardPluginControllerImpl.this.mLatencyTracker.onActionEnd(3);
                        KeyguardPluginControllerImpl.m835$$Nest$monPasswordChecked(KeyguardPluginControllerImpl.this, r2, true, 0);
                        r3.zeroize();
                    }
                });
                return;
            }
            boolean z = selectedUserInteractor.getSelectedUserId(false) == selectedUserId2;
            KeyguardSecurityCallback keyguardSecurityCallback = keyguardPluginControllerImpl.mKeyguardCallback;
            keyguardSecurityCallback.reportUnlockAttempt(selectedUserId2, 0, true);
            if (z) {
                keyguardSecurityCallback.dismiss(false, selectedUserId2, KeyguardSecurityModel.SecurityMode.Invalid);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void reset(boolean z) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            boolean z2 = keyguardStateControllerImpl.mOccluded;
            this.mShadeLockscreenInteractor.resetViews(true);
            int i = KeyguardFastBioUnlockController.MODE_FLAG_ENABLED;
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastBioUnlockController;
            if (keyguardFastBioUnlockController.isMode(i)) {
                VisibilityController visibilityController = keyguardFastBioUnlockController.curVisibilityController;
                if (visibilityController != null) {
                    visibilityController.resetForceInvisible(true);
                }
                keyguardFastBioUnlockController.reset();
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
            if (!z2 || (this.mDozing && !keyguardUpdateMonitor.isEarlyWakeUp())) {
                showBouncerOrKeyguard(z);
            } else {
                ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
                if (z || needsFullscreenBouncer()) {
                    hideBouncer(false);
                }
            }
            hideAlternateBouncer(false);
            keyguardUpdateMonitor.mHandler.obtainMessage(312).sendToTarget();
            updateStates();
            resetKeyguardDismissAction();
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void resetKeyguardDismissAction() {
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        boolean willDismissWithAction = primaryBouncerInteractor.willDismissWithAction();
        if (willDismissWithAction || this.mAfterKeyguardGoneAction != null) {
            LogUtil.d("SecStatusBarKeyguardViewManager", "resetKeyguardDismissAction hasDismissAction=%d hasGoneAction=%d", Integer.valueOf(LogUtil.getInt(willDismissWithAction)), Integer.valueOf(LogUtil.getInt(this.mAfterKeyguardGoneAction)));
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
        if (keyguardUpdateMonitor.isActiveDismissAction()) {
            keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
            if (needsFullscreenBouncer()) {
                primaryBouncerInteractor.show(true);
                keyguardUpdateMonitor.sendKeyguardStateUpdated(this.mLastShowing, this.mLastOccluded, this.mLastPrimaryBouncerShowing, true);
            }
        }
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = delegate.$securityContainerController;
            keyguardSecSecurityContainerController.mUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
            keyguardSecSecurityContainerController.setOnDismissAction(null, null);
        }
        this.mAfterKeyguardGoneAction = null;
        ((KeyguardViewMediatorHelperImpl) this.mKeyguardViewMediatorHelper).goingAwayWithAnimation = true;
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void sendKeyguardViewState(boolean z, boolean z2, boolean z3) {
        if (z == this.mLastShowing && z2 == this.mLastOccluded && z3 == this.mLastPrimaryBouncerShowing) {
            return;
        }
        if (z3 == this.mLastPrimaryBouncerShowing || !((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).playingCannedUnlockAnimation) {
            this.mKeyguardUpdateManager.sendKeyguardStateUpdated(z, z2, z3, false);
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void setLaunchEditMode() {
        this.mLaunchEditMode = true;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void setOccluded(boolean z, boolean z2) {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (((KeyguardStateControllerImpl) keyguardStateController).mShowing && z != ((KeyguardStateControllerImpl) keyguardStateController).mOccluded) {
            ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).start(!z);
        }
        super.setOccluded(z, z2);
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void setShowSwipeBouncer(boolean z) {
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        primaryBouncerInteractor.getClass();
        android.util.Log.d("PrimaryBouncerInteractor", "setShowSwipeBouncer " + z);
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) primaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            delegate.$securityContainerController.mIsSwipeBouncer = z;
        }
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).mIsSwipeBouncer = z;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void shouldSubtleWindowAnimationsForUnlock() {
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastBioUnlockController;
        if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode() || !keyguardFastBioUnlockController.isFastUnlockMode()) {
            return;
        }
        this.mSettingsHelper.isEnabledBiometricUnlockVI();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void show(Bundle bundle) {
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class);
        if (keyguardVisibilityMonitor.cancelExecToken != null) {
            Log.d("KeyguardVisible", "cancelAll");
            keyguardVisibilityMonitor.cancelExecToken(false);
        }
        super.show(bundle);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void showBouncerOrKeyguard(boolean z) {
        boolean z2 = this.mDozing;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (z2 || !needsFullscreenBouncer()) {
            ((CentralSurfacesImpl) this.mCentralSurfaces).showKeyguard();
            if (z) {
                hideBouncer(false);
                KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository;
                keyguardBouncerRepositoryImpl._primaryBouncerInflate.updateState(null, Boolean.TRUE);
            }
        } else {
            boolean z3 = this.mDozing;
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_INTERNAL);
            if (z3 == this.mDozing) {
                primaryBouncerInteractor.show(true);
            } else {
                android.util.Log.d("SecStatusBarKeyguardViewManager", "showBouncerOrKeyguard dozing is changed");
                showBouncerOrKeyguard(z);
            }
        }
        updateStates();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void updateBouncerNavigationBar(boolean z) {
        FrameLayout frameLayout = ((CentralSurfacesImpl) this.mCentralSurfaces).mBouncerContainer;
        int systemUiVisibility = frameLayout.getSystemUiVisibility();
        frameLayout.setSystemUiVisibility(z ? systemUiVisibility | 16 : systemUiVisibility & (-17));
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void updateDlsNaviBarVisibility() {
        updateNavigationBarVisibility(isNavBarVisible());
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void updateKeyguardUnlocking() {
        boolean z;
        if (!((KeyguardViewMediatorHelperImpl) this.mKeyguardViewMediatorHelper).isKeyguardHiding()) {
            KeyguardStateController keyguardStateController = this.mKeyguardStateController;
            if (((KeyguardStateControllerImpl) keyguardStateController).mKeyguardGoingAway || ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway) {
                z = true;
                this.mKeyguardUnlocking = z;
            }
        }
        z = false;
        this.mKeyguardUnlocking = z;
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void updateLastKeyguardUnlocking() {
        this.mLastKeyguardUnlocking = this.mKeyguardUnlocking;
    }

    public final void updateLockContainerMargin() {
        int i = 0;
        int rotation = DeviceState.getRotation(((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getDisplay(0).getRotation());
        if (this.mLockIconContainer == null) {
            return;
        }
        if (!DeviceType.isTablet()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mLockIconContainer.getLayoutParams();
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
            if (rotation != 1 && rotation != 3) {
                i = this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin);
            }
            layoutParams.topMargin = dimensionPixelSize + i;
            this.mLockIconContainer.setLayoutParams(layoutParams);
            return;
        }
        Resources resources = this.mContext.getResources();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mLockIconContainer.getLayoutParams();
        if (!isBouncerShowing() || resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin_tablet) == 0) {
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.status_bar_height);
            if (rotation != 1 && rotation != 3) {
                i = resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin);
            }
            layoutParams2.topMargin = dimensionPixelSize2 + i;
        } else {
            layoutParams2.topMargin = resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin_tablet);
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && rotation == 2) {
            layoutParams2.topMargin = this.mKeyguardUpdateManager.isInDisplayFingerprintMarginAccepted() ? DeviceState.getInDisplayFingerprintHeight() : resources.getDimensionPixelSize(R.dimen.status_bar_height);
        }
        this.mLockIconContainer.setLayoutParams(layoutParams2);
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void updateLockoutWarningMessage() {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.mPrimaryBouncerInteractor.primaryBouncerView).getDelegate();
        if (delegate != null) {
            KeyguardPluginControllerImpl keyguardPluginControllerImpl = delegate.$securityContainerController.mKeyguardPluginController;
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPluginControllerImpl.mKeyguardUpdateMonitor;
            int remainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(1);
            String warningAutoWipeMessage = remainingAttempt > 0 ? keyguardPluginControllerImpl.mKeyguardTextBuilder.getWarningAutoWipeMessage(keyguardUpdateMonitor.getFailedUnlockAttempts(keyguardPluginControllerImpl.mSelectedUserInteractor.getSelectedUserId()), remainingAttempt) : "";
            if (warningAutoWipeMessage.isEmpty() && keyguardPluginControllerImpl.mKeyguardUpdateMonitor.getLockoutAttemptDeadline() > 0) {
                warningAutoWipeMessage = keyguardPluginControllerImpl.mContext.getString(R.string.kg_too_many_failed_attempts_warning);
            }
            if (warningAutoWipeMessage.isEmpty()) {
                return;
            }
            DesktopManager desktopManager = keyguardPluginControllerImpl.mDesktopManager;
            if (desktopManager.isDesktopMode()) {
                desktopManager.notifyKeyguardLockout(true, warningAutoWipeMessage, "", "");
            }
        }
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void updateNavigationBarVisibility() {
        updateStates();
    }
}
