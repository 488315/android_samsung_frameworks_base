package com.android.systemui.shade;

import android.app.IActivityManager;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.SemBlurInfo;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecNotificationShadeWindowControllerHelperImpl implements SecNotificationShadeWindowControllerHelper {
    public static final int AWAKE_INTERVAL_DEFAULT_MS_LIVE_DEMO;
    public static final long AWAKE_INTERVAL_DEFAULT_MS_WITH_ACCESSIBILITY;
    public static final long AWAKE_INTERVAL_DEFAULT_MS_WITH_FACE;
    public static final int AWAKE_INTERVAL_DEFAULT_MS_WITH_POWER_SAVING;
    public static final float BLUR_AMOUNT;
    public static final String DEBUG_TAG;
    public static final int DEFAULT_DISPLAY_TIMEOUT;
    public static final int MSG_USER_ACTIVITY_TIMEOUT_CHANGED;
    public final IActivityManager activityManager;
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public BouncerColorCurve bouncerColorCurve;
    public ViewGroup bouncerContainer;
    public WindowManager.LayoutParams bouncerLp;
    public WindowManager.LayoutParams bouncerLpChanged;
    public final Context context;
    public final DisplayLifecycle displayLifecycle;
    public final EngineeringModeManagerWrapper engineerModeManager;
    public final KeyguardFastBioUnlockController fastUnlockController;
    public boolean isInitFinished;
    public boolean isKeyguardScreenRotation;
    public boolean isLastExpanded;
    public boolean isWhiteKeyguardWallpaper;
    public final KeyguardFoldController keyguardFoldController;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardViewMediatorHelper keyguardViewMediatorHelper;
    public final KeyguardWallpaper keyguardWallpaper;
    public ViewGroup notificationShadeView;
    public final Lazy pluginAODManagerLazy;
    public final PluginLockMediator pluginLockMediator;
    public final Lazy pluginLockStarManagerLazy;
    public final Lazy powerInteractorLazy;
    public final PowerManager powerManager;
    public Provider provider;
    public int rotation;
    public final CoroutineScope scope;
    private final SettingsHelper settingsHelper;
    private SettingsHelper.OnChangedCallback settingsHelperCallback;
    public final KeyguardVisibilityMonitor visibilityMonitor;
    public final WindowManager windowManager;
    public final SecNotificationShadeWindowControllerHelperImpl$handler$1 handler = new Handler() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$handler$1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == SecNotificationShadeWindowControllerHelperImpl.MSG_USER_ACTIVITY_TIMEOUT_CHANGED) {
                SecNotificationShadeWindowControllerHelperImpl.this.updateUserActivityTimeout(message.arg1 > 0);
            }
        }
    };
    public final SecNotificationShadeWindowControllerHelperImpl$pluginLockListener$1 pluginLockListener = new PluginLockListener.Window() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$pluginLockListener$1
        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void onScreenOrientationChangeRequired(boolean z) {
            SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(SecNotificationShadeWindowControllerHelperImpl.this, z);
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void onScreenTimeoutChanged(long j) {
            Log.d("NotificationShadeWindowController", "onScreenTimeoutChanged timeOut : " + j);
            String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
            secNotificationShadeWindowControllerHelperImpl.getCurrentState().lockTimeOutValue = j;
            secNotificationShadeWindowControllerHelperImpl.updateUserActivityTimeout(false);
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void onViewModeChanged(int i) {
            Log.d("NotificationShadeWindowController", "onViewModeChanged mode : " + i);
            boolean z = i == 1;
            if (LsRune.LOCKUI_BLUR) {
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
                if (z) {
                    Log.d("NotificationShadeWindowController", "prepareToApplyBlurDimEffect");
                    String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                    WindowManager.LayoutParams layoutParamsChanged = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
                    layoutParamsChanged.flags |= 2;
                    layoutParamsChanged.dimAmount = 0.125f;
                    layoutParamsChanged.samsungFlags |= 64;
                } else {
                    Log.d("NotificationShadeWindowController", "prepareToRemoveBlurDimEffect");
                    String str2 = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                    WindowManager.LayoutParams layoutParamsChanged2 = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
                    layoutParamsChanged2.flags &= -3;
                    layoutParamsChanged2.dimAmount = 0.0f;
                    layoutParamsChanged2.samsungFlags &= -65;
                }
            }
            updateBiometricRecognition(z);
            updateOverlayUserTimeout(z);
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void onViewModePageChanged(SemWallpaperColors semWallpaperColors) {
            if (semWallpaperColors != null) {
                boolean z = semWallpaperColors.get(256L).getFontColor() == 1;
                ViewGroup viewGroup = SecNotificationShadeWindowControllerHelperImpl.this.notificationShadeView;
                if (viewGroup != null) {
                    int systemUiVisibility = viewGroup.getSystemUiVisibility();
                    viewGroup.setSystemUiVisibility(z ? systemUiVisibility | 16 : systemUiVisibility & (-17));
                }
            }
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void updateBiometricRecognition(boolean z) {
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
            secNotificationShadeWindowControllerHelperImpl.powerManager.userActivity(SystemClock.uptimeMillis(), true);
            secNotificationShadeWindowControllerHelperImpl.keyguardUpdateMonitor.dispatchDlsBiometricMode(z);
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void updateOverlayUserTimeout(boolean z) {
            String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
            NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
            currentState.userScreenTimeOut = z;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.Window
        public final void updateWindowSecureState(boolean z) {
            String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
            NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
            currentState.securedWindow = z;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
    };
    public final SecNotificationShadeWindowControllerHelperImpl$pluginLockStarCallback$1 pluginLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$pluginLockStarCallback$1
        @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
        public final void onChangedLockStarData(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("LockStarCallback: onChangedLockStarEnabled: ", "NotificationShadeWindowController", z);
            SecNotificationShadeWindowControllerHelperImpl.access$updateLockStarUserActivityTimeout(SecNotificationShadeWindowControllerHelperImpl.this, z);
        }

        @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
        public final Bundle request(Bundle bundle) {
            android.util.Log.d("NotificationShadeWindowController", "LockStarCallback: request: " + bundle);
            if (!TextUtils.equals(bundle != null ? bundle.getString("type", "") : null, PluginLockStar.STATUS_BAR_TYPE)) {
                return new Bundle();
            }
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
            PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) secNotificationShadeWindowControllerHelperImpl.pluginLockStarManagerLazy.get();
            SecNotificationShadeWindowControllerHelperImpl.access$updateLockStarUserActivityTimeout(secNotificationShadeWindowControllerHelperImpl, pluginLockStarManager != null ? pluginLockStarManager.isLockStarEnabled() : false);
            return new Bundle();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Provider {
        public final Consumer applyConsumer;
        public final Supplier currentWindowStateSupplier;
        public final BooleanSupplier isDebuggableSupplier;
        public final Predicate isExpandedPredicate;
        public final Supplier lpChangedSupplier;
        public final Supplier lpSupplier;

        public Provider(Supplier<NotificationShadeWindowState> supplier, Supplier<WindowManager.LayoutParams> supplier2, Supplier<WindowManager.LayoutParams> supplier3, Predicate<Boolean> predicate, BooleanSupplier booleanSupplier, Consumer<NotificationShadeWindowState> consumer) {
            this.currentWindowStateSupplier = supplier;
            this.lpChangedSupplier = supplier2;
            this.lpSupplier = supplier3;
            this.isExpandedPredicate = predicate;
            this.isDebuggableSupplier = booleanSupplier;
            this.applyConsumer = consumer;
        }
    }

    static {
        new Companion(null);
        DEBUG_TAG = "KeyguardVisible";
        BLUR_AMOUNT = DeviceType.isOpticalFingerprintSupported() ? 0.07f : 0.1375f;
        DEFAULT_DISPLAY_TIMEOUT = 5000;
        AWAKE_INTERVAL_DEFAULT_MS_WITH_POWER_SAVING = 3000;
        AWAKE_INTERVAL_DEFAULT_MS_WITH_FACE = 6000L;
        AWAKE_INTERVAL_DEFAULT_MS_WITH_ACCESSIBILITY = 10000L;
        AWAKE_INTERVAL_DEFAULT_MS_LIVE_DEMO = 600000;
        MSG_USER_ACTIVITY_TIMEOUT_CHANGED = 101;
    }

    /* JADX WARN: Type inference failed for: r1v21, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$handler$1] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$pluginLockListener$1] */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$pluginLockStarCallback$1] */
    public SecNotificationShadeWindowControllerHelperImpl(Context context, PowerManager powerManager, WindowManager windowManager, DisplayLifecycle displayLifecycle, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, IActivityManager iActivityManager, KeyguardFoldController keyguardFoldController, KeyguardFastBioUnlockController keyguardFastBioUnlockController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, EngineeringModeManagerWrapper engineeringModeManagerWrapper, Lazy lazy, PluginLockMediator pluginLockMediator, KeyguardWallpaper keyguardWallpaper, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, Lazy lazy2, Lazy lazy3, KeyguardViewMediatorHelper keyguardViewMediatorHelper) {
        this.context = context;
        this.powerManager = powerManager;
        this.windowManager = windowManager;
        this.displayLifecycle = displayLifecycle;
        this.keyguardStateController = keyguardStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.activityManager = iActivityManager;
        this.keyguardFoldController = keyguardFoldController;
        this.fastUnlockController = keyguardFastBioUnlockController;
        this.visibilityMonitor = keyguardVisibilityMonitor;
        this.engineerModeManager = engineeringModeManagerWrapper;
        this.pluginAODManagerLazy = lazy;
        this.pluginLockMediator = pluginLockMediator;
        this.keyguardWallpaper = keyguardWallpaper;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.scope = coroutineScope;
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.pluginLockStarManagerLazy = lazy2;
        this.powerInteractorLazy = lazy3;
        this.keyguardViewMediatorHelper = keyguardViewMediatorHelper;
    }

    public static final void access$setScreenOrientation(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, boolean z) {
        secNotificationShadeWindowControllerHelperImpl.getClass();
        Log.d("NotificationShadeWindowController", "setScreenOrientation noSensor : " + z);
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        if (currentState.screenOrientationNoSensor != z) {
            currentState.screenOrientationNoSensor = z;
            secNotificationShadeWindowControllerHelperImpl.keyguardUpdateMonitor.setScreenOrientationNoSensorValue(z);
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
    }

    public static final void access$updateLockStarUserActivityTimeout(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, boolean z) {
        PluginLockStarManager pluginLockStarManager;
        LockStarValues lockStarValues;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        long j = -1;
        if (z && (pluginLockStarManager = (PluginLockStarManager) secNotificationShadeWindowControllerHelperImpl.pluginLockStarManagerLazy.get()) != null && (lockStarValues = pluginLockStarManager.getLockStarValues()) != null) {
            j = lockStarValues.getLockScreenOffTimeoutSeconds() * 1000;
        }
        currentState.lockStarTimeOutValue = j;
        currentState.userScreenTimeOut = j > 0;
        secNotificationShadeWindowControllerHelperImpl.updateUserActivityTimeout(false);
    }

    public final void addBouncer(ViewGroup viewGroup) {
        long j;
        this.bouncerContainer = viewGroup;
        if (viewGroup != null) {
            viewGroup.setVisibility(4);
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, 0, 2009, -2147483320, -3);
        this.bouncerLp = layoutParams;
        layoutParams.flags |= 16777216;
        layoutParams.gravity = 48;
        layoutParams.softInputMode = 16;
        layoutParams.setTitle("Bouncer");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.packageName = this.context.getPackageName();
        layoutParams.inputFeatures |= 2;
        layoutParams.samsungFlags |= 262144;
        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
        NotificationShadeWindowState currentState = getCurrentState();
        if (currentState.statusBarState == 1) {
            j = currentState.keyguardUserActivityTimeout;
            if (j < 10000) {
                j = 10000;
            }
        } else {
            j = -1;
        }
        layoutParams.userActivityTimeout = j;
        layoutParams.flags |= 67109888;
        if (LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        } else {
            layoutParams.layoutInDisplayCutoutMode = 3;
        }
        this.windowManager.addView(this.bouncerContainer, this.bouncerLp);
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        this.bouncerLpChanged = layoutParams2;
        layoutParams2.copyFrom(this.bouncerLp);
    }

    public final void apply(NotificationShadeWindowState notificationShadeWindowState) {
        Provider provider = this.provider;
        if (provider == null) {
            provider = null;
        }
        provider.applyConsumer.accept(notificationShadeWindowState);
    }

    public final void applyBouncer(NotificationShadeWindowState notificationShadeWindowState) {
        boolean isWhiteKeyguardWallpaper;
        int copyFrom;
        int i;
        ViewGroup viewGroup;
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            isWhiteKeyguardWallpaper = true;
            SemWallpaperColors cachedSemWallpaperColors = WallpaperUtils.getCachedSemWallpaperColors(!((KeyguardFoldControllerImpl) this.keyguardFoldController).isFoldOpened());
            if (cachedSemWallpaperColors == null || cachedSemWallpaperColors.get(512L).getFontColor() != 1) {
                isWhiteKeyguardWallpaper = false;
            }
        } else {
            isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        }
        boolean z = notificationShadeWindowState.bouncerShowing;
        if (!z || !notificationShadeWindowState.keyguardShowing || notificationShadeWindowState.coverAppShowing || notificationShadeWindowState.isCoverClosed) {
            WindowManager.LayoutParams layoutParams = this.bouncerLpChanged;
            if (layoutParams != null) {
                layoutParams.flags = (layoutParams.flags | 8) & (-131075);
                layoutParams.dimAmount = 0.0f;
                applyBouncerWindowBlur(0.0f, isWhiteKeyguardWallpaper);
                if (!this.isKeyguardScreenRotation) {
                    layoutParams.screenOrientation = -1;
                }
            }
        } else if (z) {
            if (LsRune.SECURITY_CAPTURED_BLUR && (viewGroup = this.bouncerContainer) != null) {
                if (notificationShadeWindowState.keyguardOccluded) {
                    if (isWhiteKeyguardWallpaper) {
                        viewGroup.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.bouncer_background_color_occluded_no_blur_white_bg, null));
                    } else {
                        viewGroup.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.bouncer_background_color_occluded, null));
                    }
                } else if (isWhiteKeyguardWallpaper) {
                    viewGroup.setBackgroundColor(viewGroup.getContext().getResources().getColor(R.color.bouncer_background_color_no_blur_white_bg, null));
                } else {
                    viewGroup.setBackgroundColor(0);
                }
            }
            WindowManager.LayoutParams layoutParams2 = this.bouncerLpChanged;
            if (layoutParams2 != null) {
                int i2 = layoutParams2.flags;
                int i3 = i2 & (-25);
                layoutParams2.flags = i3;
                if (notificationShadeWindowState.keyguardNeedsInput) {
                    layoutParams2.flags = i2 & (-131097);
                } else {
                    layoutParams2.flags = 131072 | i3;
                }
                if (LsRune.SECURITY_BLUR) {
                    if (this.settingsHelper.isReduceTransparencyEnabled()) {
                        layoutParams2.flags &= -3;
                    } else {
                        layoutParams2.flags |= 2;
                    }
                    applyBouncerWindowBlur(1.0f, isWhiteKeyguardWallpaper);
                }
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    layoutParams2.userActivityTimeout = -1L;
                    layoutParams2.screenDimDuration = -1L;
                } else {
                    Provider provider = this.provider;
                    if (provider == null) {
                        provider = null;
                    }
                    WindowManager.LayoutParams layoutParams3 = (WindowManager.LayoutParams) provider.lpSupplier.get();
                    if (layoutParams3 != null) {
                        long j = layoutParams3.userActivityTimeout;
                        if (j < 10000) {
                            j = 10000;
                        }
                        layoutParams2.userActivityTimeout = j;
                        layoutParams2.screenDimDuration = layoutParams3.screenDimDuration;
                    }
                }
                if (!this.isKeyguardScreenRotation && notificationShadeWindowState.keyguardOccluded) {
                    layoutParams2.screenOrientation = 5;
                }
            }
        }
        WindowManager.LayoutParams layoutParams4 = this.bouncerLpChanged;
        if (layoutParams4 != null) {
            layoutParams4.height = (notificationShadeWindowState.keyguardShowing || this.keyguardTransitionInteractor.getCurrentState() == KeyguardState.PRIMARY_BOUNCER) ? -1 : 0;
            if ((layoutParams4.flags & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) != 0) {
                layoutParams4.subtreeSystemUiVisibility |= PeripheralConstants.ErrorCode.ERROR_PLUGIN_CUSTOM_BASE;
            }
        }
        ViewGroup viewGroup2 = this.bouncerContainer;
        if (viewGroup2 != null && notificationShadeWindowState.bouncerShowing) {
            int systemUiVisibility = viewGroup2.getSystemUiVisibility();
            viewGroup2.setSystemUiVisibility(isWhiteKeyguardWallpaper ? systemUiVisibility | 16 : systemUiVisibility & (-17));
        }
        WindowManager.LayoutParams layoutParams5 = this.bouncerLpChanged;
        if (layoutParams5 != null) {
            if (notificationShadeWindowState.bouncerShowing) {
                Provider provider2 = this.provider;
                if (!(provider2 != null ? provider2 : null).isDebuggableSupplier.getAsBoolean() && (!LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW || !this.engineerModeManager.isCaptureEnabled)) {
                    i = layoutParams5.flags | 8192;
                    layoutParams5.flags = i;
                }
            }
            i = layoutParams5.flags & (-8193);
            layoutParams5.flags = i;
        }
        WindowManager.LayoutParams layoutParams6 = this.bouncerLp;
        if (layoutParams6 == null || (copyFrom = layoutParams6.copyFrom(this.bouncerLpChanged)) == 0) {
            return;
        }
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(layoutParams6.height, "Bouncer LP changed!!! = 0x", Integer.toHexString(copyFrom), ", h = ", "NotificationShadeWindowController");
        this.windowManager.updateViewLayout(this.bouncerContainer, layoutParams6);
    }

    public final void applyBouncerWindowBlur(float f, boolean z) {
        BouncerColorCurve bouncerColorCurve;
        ViewGroup viewGroup;
        if (!LsRune.SECURITY_COLOR_CURVE_BLUR) {
            WindowManager.LayoutParams layoutParams = this.bouncerLpChanged;
            if (layoutParams != null) {
                if (Float.compare(f, 0.0f) <= 0) {
                    layoutParams.samsungFlags &= -65;
                    return;
                } else {
                    layoutParams.samsungFlags |= 64;
                    layoutParams.dimAmount = BLUR_AMOUNT;
                    return;
                }
            }
            return;
        }
        if (this.bouncerContainer == null || (bouncerColorCurve = this.bouncerColorCurve) == null || bouncerColorCurve == null) {
            return;
        }
        if (Float.compare(bouncerColorCurve.mFraction, f) == 0 && this.isWhiteKeyguardWallpaper == z) {
            return;
        }
        bouncerColorCurve.setFraction(f, z);
        this.isWhiteKeyguardWallpaper = z;
        if (this.settingsHelper.isReduceTransparencyEnabled()) {
            ViewGroup viewGroup2 = this.bouncerContainer;
            if (viewGroup2 != null) {
                viewGroup2.semSetBlurInfo(null);
                return;
            }
            return;
        }
        if (Float.compare(f, 0.0f) <= 0 || (viewGroup = this.bouncerContainer) == null) {
            return;
        }
        SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
        builder.setRadius((int) bouncerColorCurve.mRadius);
        viewGroup.semSetBlurInfo(builder.setColorCurve(bouncerColorCurve.mSaturation, bouncerColorCurve.mCurve, bouncerColorCurve.mMinX, bouncerColorCurve.mMaxX, bouncerColorCurve.mMinY, bouncerColorCurve.mMaxY).build());
    }

    public final void applyHelper(NotificationShadeWindowState notificationShadeWindowState) {
        PluginAODManager pluginAODManager;
        WindowManager.LayoutParams layoutParamsChanged = getLayoutParamsChanged();
        if (notificationShadeWindowState.shouldHideNotificationShadeInMirror) {
            layoutParamsChanged.semAddExtensionFlags(Integer.MIN_VALUE);
        } else {
            layoutParamsChanged.semClearExtensionFlags(Integer.MIN_VALUE);
        }
        if (this.isInitFinished) {
            WindowManager.LayoutParams layoutParamsChanged2 = getLayoutParamsChanged();
            layoutParamsChanged2.semAddExtensionFlags(262144);
            int i = layoutParamsChanged2.screenOrientation;
            boolean z = notificationShadeWindowState.dozing;
            Lazy lazy = this.pluginAODManagerLazy;
            if ((z || !notificationShadeWindowState.keyguardFadingAway) && lazy != null && (pluginAODManager = (PluginAODManager) lazy.get()) != null) {
                boolean z2 = notificationShadeWindowState.dozing;
                boolean z3 = notificationShadeWindowState.isCoverClosed;
                PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                if (pluginAOD != null) {
                    pluginAOD.applyAODFlags(layoutParamsChanged2, z2, z3);
                }
            }
            if (lazy != null) {
                ((PluginAODManager) lazy.get()).mIsDifferentOrientation = (i == 2 || i == layoutParamsChanged2.screenOrientation) ? false : true;
            }
        }
        WindowManager.LayoutParams layoutParamsChanged3 = getLayoutParamsChanged();
        if (this.settingsHelper.isUltraPowerSavingMode() || this.settingsHelper.isEmergencyMode() || (notificationShadeWindowState.keyguardOccluded && ((WakefulnessModel) ((PowerInteractor) this.powerInteractorLazy.get()).detailedWakefulness.$$delegate_0.getValue()).isAwake())) {
            layoutParamsChanged3.flags &= -1048577;
            return;
        }
        if (LsRune.AOD_FULLSCREEN) {
            AODAmbientWallpaperHelper aODAmbientWallpaperHelper = this.aodAmbientWallpaperHelper;
            if (!aODAmbientWallpaperHelper.isAODFullScreenMode() || ((KeyguardViewMediator) aODAmbientWallpaperHelper.keyguardViewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
                if (!aODAmbientWallpaperHelper.isAODFullScreenMode()) {
                    return;
                }
                if (!this.fastUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED | KeyguardFastBioUnlockController.MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN) && !notificationShadeWindowState.forceVisibleForUnlockAnimation) {
                    return;
                }
            }
            layoutParamsChanged3.flags |= QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING;
        }
    }

    public final NotificationShadeWindowState getCurrentState() {
        Provider provider = this.provider;
        if (provider == null) {
            provider = null;
        }
        return (NotificationShadeWindowState) provider.currentWindowStateSupplier.get();
    }

    public final WindowManager.LayoutParams getLayoutParamsChanged() {
        Provider provider = this.provider;
        if (provider == null) {
            provider = null;
        }
        return (WindowManager.LayoutParams) provider.lpChangedSupplier.get();
    }

    public final long getUserActivityTimeout() {
        long j = DEFAULT_DISPLAY_TIMEOUT;
        NotificationShadeWindowState currentState = getCurrentState();
        PluginLockMediator pluginLockMediator = this.pluginLockMediator;
        if (pluginLockMediator != null ? pluginLockMediator.isDynamicLockEnabled() : false) {
            long j2 = currentState.lockTimeOutValue;
            if (j2 > 0) {
                j = j2;
            }
        }
        PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) this.pluginLockStarManagerLazy.get();
        if (pluginLockStarManager != null && pluginLockStarManager.isLockStarEnabled()) {
            long j3 = currentState.lockStarTimeOutValue;
            if (j3 > 0) {
                j = j3;
            }
        }
        long accessibilityInteractiveUiTimeout = this.settingsHelper.getAccessibilityInteractiveUiTimeout();
        if (accessibilityInteractiveUiTimeout > 0) {
            j = accessibilityInteractiveUiTimeout;
        }
        if (this.settingsHelper.isEmergencyMode() || this.settingsHelper.isPowerSavingMode()) {
            j = Math.min(AWAKE_INTERVAL_DEFAULT_MS_WITH_POWER_SAVING, j);
        }
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK", false)) {
            j = AWAKE_INTERVAL_DEFAULT_MS_LIVE_DEMO;
        }
        android.util.Log.d("NotificationShadeWindowController", "getUserActivityTimeout " + j);
        return j;
    }

    public final void initPost() {
        final NotificationShadeWindowState currentState = getCurrentState();
        this.isKeyguardScreenRotation = isLockScreenRotationAllowed();
        ArrayList arrayList = new ArrayList();
        if (DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.keyguardStateController).mContext)) {
            arrayList.add(Settings.System.getUriFor(SettingsHelper.INDEX_ROTATION_LOCK_SCREEN));
        }
        currentState.keyguardUserActivityTimeout = getUserActivityTimeout();
        arrayList.add(Settings.System.getUriFor(SettingsHelper.INDEX_POWERSAVING_SWITCH));
        arrayList.add(Settings.System.getUriFor(SettingsHelper.INDEX_PSM_SWITCH));
        arrayList.add(Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE));
        arrayList.add(Settings.Global.getUriFor(SettingsHelper.INDEX_LOW_POWER_MODE));
        arrayList.add(Settings.Secure.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_INTERACTIVE_UI_TIMEOUT_MS));
        if (!arrayList.isEmpty()) {
            SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initPost$2
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
                    if (uri != null && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ROTATION_LOCK_SCREEN))) {
                        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                        secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl.isLockScreenRotationAllowed();
                        return;
                    }
                    SecNotificationShadeWindowControllerHelperImpl$handler$1 secNotificationShadeWindowControllerHelperImpl$handler$1 = secNotificationShadeWindowControllerHelperImpl.handler;
                    int i = SecNotificationShadeWindowControllerHelperImpl.MSG_USER_ACTIVITY_TIMEOUT_CHANGED;
                    if (secNotificationShadeWindowControllerHelperImpl$handler$1.hasMessages(i)) {
                        secNotificationShadeWindowControllerHelperImpl$handler$1.removeMessages(i);
                    }
                    secNotificationShadeWindowControllerHelperImpl$handler$1.sendMessage(secNotificationShadeWindowControllerHelperImpl$handler$1.obtainMessage(i, 0, 0));
                }
            };
            this.settingsHelperCallback = onChangedCallback;
            SettingsHelper settingsHelper = this.settingsHelper;
            Uri[] uriArr = (Uri[]) arrayList.toArray(new Uri[0]);
            settingsHelper.registerCallback(onChangedCallback, (Uri[]) Arrays.copyOf(uriArr, uriArr.length));
        }
        PluginLockMediator pluginLockMediator = this.pluginLockMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.registerWindowListener(this.pluginLockListener);
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && !LsRune.KEYGUARD_SUB_DISPLAY_ROTATIONAL) {
            this.displayLifecycle.addObserver(new DisplayLifecycle.Observer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initPost$3
                @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
                public final void onFolderStateChanged(boolean z) {
                    SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
                    boolean z2 = secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation;
                    if (z) {
                        secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl.isLockScreenRotationAllowed();
                    } else {
                        secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation = false;
                    }
                    if (z2 != secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation) {
                        secNotificationShadeWindowControllerHelperImpl.apply(currentState);
                    }
                }
            });
        }
        PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) this.pluginLockStarManagerLazy.get();
        if (pluginLockStarManager != null) {
            pluginLockStarManager.registerCallback(PluginLockStar.TIMEOUT_TYPE, this.pluginLockStarCallback);
        }
        if (LsRune.SECURITY_COLOR_CURVE_BLUR) {
            this.bouncerColorCurve = new BouncerColorCurve();
        }
        this.isInitFinished = true;
    }

    public final boolean isLockScreenRotationAllowed() {
        boolean z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (!z || LsRune.KEYGUARD_SUB_DISPLAY_ROTATIONAL) {
            if (!DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) keyguardStateController).mContext) || !this.settingsHelper.isLockScreenRotationAllowed()) {
                return false;
            }
        } else if (!DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) keyguardStateController).mContext) || !this.settingsHelper.isLockScreenRotationAllowed() || !((KeyguardFoldControllerImpl) this.keyguardFoldController).isFoldOpened()) {
            return false;
        }
        return true;
    }

    public final void resetForceInvisible(boolean z) {
        NotificationShadeWindowState currentState = getCurrentState();
        if (currentState.forceInvisible) {
            Log.d(DEBUG_TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("resetForceInvisible ", z));
            currentState.forceInvisible = false;
            if (z) {
                apply(currentState);
            }
        }
    }

    public final void resetForceVisibleForUnlockAnimation() {
        NotificationShadeWindowState currentState = getCurrentState();
        if (currentState.forceVisibleForUnlockAnimation) {
            Log.d(DEBUG_TAG, "resetForceVisibleForUnlockAnimation true");
            currentState.forceVisibleForUnlockAnimation = false;
            apply(currentState);
        }
    }

    public final void setForceInvisible(boolean z) {
        NotificationShadeWindowState currentState = getCurrentState();
        if (currentState.forceInvisible) {
            return;
        }
        Log.d(DEBUG_TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setForceInVisible ", z));
        currentState.forceInvisible = true;
        if (z) {
            apply(currentState);
        }
    }

    public final void updateUserActivityTimeout(boolean z) {
        NotificationShadeWindowState currentState = getCurrentState();
        long userActivityTimeout = getUserActivityTimeout();
        if (currentState.keyguardUserActivityTimeout != userActivityTimeout) {
            currentState.keyguardUserActivityTimeout = userActivityTimeout;
            if (z || currentState.statusBarState == 1) {
                apply(currentState);
            }
        }
    }
}
