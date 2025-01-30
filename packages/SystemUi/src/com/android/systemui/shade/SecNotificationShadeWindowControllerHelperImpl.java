package com.android.systemui.shade;

import android.app.IActivityManager;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.SemBlurInfo;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import dagger.Lazy;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final KeyguardWallpaper keyguardWallpaper;
    public ViewGroup notificationShadeView;
    public final Lazy pluginAODManagerLazy;
    public final PluginLockMediator pluginLockMediator;
    public final PowerManager powerManager;
    public Provider provider;
    public int rotation;
    public final CoroutineScope scope;
    public final SettingsHelper settingsHelper;
    public SecNotificationShadeWindowControllerHelperImpl$initPost$2 settingsHelperCallback;
    public final KeyguardVisibilityMonitor visibilityMonitor;
    public final WindowManager windowManager;
    public final SecNotificationShadeWindowControllerHelperImpl$handler$1 handler = new Handler() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$handler$1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == SecNotificationShadeWindowControllerHelperImpl.MSG_USER_ACTIVITY_TIMEOUT_CHANGED) {
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = SecNotificationShadeWindowControllerHelperImpl.this;
                boolean z = message.arg1 > 0;
                NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
                long userActivityTimeout = secNotificationShadeWindowControllerHelperImpl.getUserActivityTimeout();
                if (currentState.keyguardUserActivityTimeout != userActivityTimeout) {
                    currentState.keyguardUserActivityTimeout = userActivityTimeout;
                    if (z || currentState.statusBarState == 1) {
                        secNotificationShadeWindowControllerHelperImpl.apply(currentState);
                    }
                }
            }
        }
    };
    public final C2456x44c40078 pluginLockListener = new C2456x44c40078(this);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        if (DeviceType.supportOpticalFingerprint == -1) {
            DeviceType.supportOpticalFingerprint = 1;
        }
        BLUR_AMOUNT = DeviceType.supportOpticalFingerprint == 1 ? 0.07f : 0.1375f;
        DEFAULT_DISPLAY_TIMEOUT = 5000;
        AWAKE_INTERVAL_DEFAULT_MS_WITH_POWER_SAVING = 3000;
        AWAKE_INTERVAL_DEFAULT_MS_WITH_FACE = 6000L;
        AWAKE_INTERVAL_DEFAULT_MS_WITH_ACCESSIBILITY = 10000L;
        AWAKE_INTERVAL_DEFAULT_MS_LIVE_DEMO = 600000;
        MSG_USER_ACTIVITY_TIMEOUT_CHANGED = 101;
    }

    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$handler$1] */
    public SecNotificationShadeWindowControllerHelperImpl(Context context, PowerManager powerManager, WindowManager windowManager, DisplayLifecycle displayLifecycle, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, IActivityManager iActivityManager, KeyguardFoldController keyguardFoldController, KeyguardFastBioUnlockController keyguardFastBioUnlockController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, EngineeringModeManagerWrapper engineeringModeManagerWrapper, Lazy lazy, PluginLockMediator pluginLockMediator, KeyguardWallpaper keyguardWallpaper, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, AODAmbientWallpaperHelper aODAmbientWallpaperHelper) {
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
    }

    public static final void access$setScreenOrientation(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, boolean z) {
        secNotificationShadeWindowControllerHelperImpl.getClass();
        Log.m138d("NotificationShadeWindowController", "setScreenOrientation noSensor : " + z);
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        if (currentState.screenOrientationNoSensor != z) {
            currentState.screenOrientationNoSensor = z;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
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
        int i = layoutParams.samsungFlags | 262144;
        layoutParams.samsungFlags = i;
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL) {
            layoutParams.samsungFlags = i | 131072;
        }
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
        layoutParams.layoutInDisplayCutoutMode = 1;
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
                layoutParams.flags = (-131073) & (layoutParams.flags | 8) & (-3);
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
                int i2 = layoutParams2.flags & (-9) & (-17);
                layoutParams2.flags = i2;
                if (notificationShadeWindowState.keyguardNeedsInput) {
                    layoutParams2.flags = (-131073) & i2;
                } else {
                    layoutParams2.flags = 131072 | i2;
                }
                if (LsRune.SECURITY_BLUR) {
                    if (this.settingsHelper.isReduceTransparencyEnabled()) {
                        layoutParams2.flags &= -3;
                        applyBouncerWindowBlur(0.0f, isWhiteKeyguardWallpaper);
                    } else {
                        layoutParams2.flags |= 2;
                        applyBouncerWindowBlur(1.0f, isWhiteKeyguardWallpaper);
                    }
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
            layoutParams4.height = (notificationShadeWindowState.keyguardShowing || this.keyguardTransitionInteractor.finishedKeyguardState.getValue() == KeyguardState.PRIMARY_BOUNCER) ? -1 : 0;
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
        if (copyFrom == 134217728 && (134217728 & layoutParams6.privateFlags) == 0) {
            return;
        }
        android.util.Log.d("NotificationShadeWindowController", "Bouncer LP changed!!! = 0x" + Integer.toHexString(copyFrom) + ", h = " + layoutParams6.height);
        this.windowManager.updateViewLayout(this.bouncerContainer, layoutParams6);
    }

    public final void applyBouncerWindowBlur(float f, boolean z) {
        BouncerColorCurve bouncerColorCurve;
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
            ViewGroup viewGroup = this.bouncerContainer;
            if (viewGroup != null) {
                viewGroup.semSetBlurInfo(null);
                return;
            }
            return;
        }
        ViewGroup viewGroup2 = this.bouncerContainer;
        if (viewGroup2 != null) {
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            builder.setRadius((int) bouncerColorCurve.mRadius);
            viewGroup2.semSetBlurInfo(builder.setColorCurve(bouncerColorCurve.mSaturation, bouncerColorCurve.mCurve, bouncerColorCurve.mMinX, bouncerColorCurve.mMaxX, bouncerColorCurve.mMinY, bouncerColorCurve.mMaxY).build());
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
        if (pluginLockMediator != null ? ((PluginLockMediatorImpl) pluginLockMediator).isDynamicLockEnabled() : false) {
            long j2 = currentState.lockTimeOutValue;
            if (j2 > 0) {
                j = j2;
            }
        }
        SettingsHelper settingsHelper = this.settingsHelper;
        long intValue = settingsHelper.mItemLists.get("accessibility_interactive_ui_timeout_ms").getIntValue();
        if (intValue > 0) {
            j = intValue;
        }
        if (settingsHelper.isEmergencyMode() || settingsHelper.isPowerSavingMode()) {
            j = Math.min(AWAKE_INTERVAL_DEFAULT_MS_WITH_POWER_SAVING, j);
        }
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK", false)) {
            j = AWAKE_INTERVAL_DEFAULT_MS_LIVE_DEMO;
        }
        android.util.Log.d("NotificationShadeWindowController", "getUserActivityTimeout " + j);
        return j;
    }

    public final boolean isLockScreenRotationAllowed() {
        boolean z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        SettingsHelper settingsHelper = this.settingsHelper;
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (!z || LsRune.KEYGUARD_SUB_DISPLAY_ROTATIONAL) {
            if (((KeyguardStateControllerImpl) keyguardStateController).isKeyguardScreenRotationAllowed() && settingsHelper.isLockScreenRotationAllowed()) {
                return true;
            }
        } else if (((KeyguardStateControllerImpl) keyguardStateController).isKeyguardScreenRotationAllowed() && settingsHelper.isLockScreenRotationAllowed() && ((KeyguardFoldControllerImpl) this.keyguardFoldController).isFoldOpened()) {
            return true;
        }
        return false;
    }

    public final void resetForceInvisible(boolean z) {
        NotificationShadeWindowState currentState = getCurrentState();
        if (currentState.forceInvisible) {
            Log.m138d(DEBUG_TAG, "resetForceInvisible " + z);
            currentState.forceInvisible = false;
            if (z) {
                apply(currentState);
            }
        }
    }

    public final void setForceInvisible(boolean z) {
        NotificationShadeWindowState currentState = getCurrentState();
        if (currentState.forceInvisible) {
            return;
        }
        Log.m138d(DEBUG_TAG, "setForceInVisible " + z);
        currentState.forceInvisible = true;
        if (z) {
            apply(currentState);
        }
    }
}
