package com.android.keyguard.punchhole;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class KeyguardPunchHoleVIViewController extends ViewController implements SystemUIWidgetCallback {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass5 mConfigurationListener;
    public final AnonymousClass4 mDisplayLifeCycleObserver;
    public final DisplayLifecycle mDisplayLifecycle;
    public final AnonymousClass7 mEditModeListener;
    public final Handler mHandler;
    public boolean mIsBouncerVI;
    public boolean mIsFaceRunning;
    public boolean mIsLockStarEnabled;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public int mLastDensityDpi;
    public int mLastLayoutDirection;
    public final AnonymousClass6 mLockStarCallback;
    public final PluginLockStarManager mPluginLockStarManager;
    public final AnonymousClass1 mPunchHoleCallback;
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final AnonymousClass3 mWakefulnessObserver;

    /* renamed from: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public class Factory {
        public final ConfigurationController mConfigurationController;
        public final DisplayLifecycle mDisplayLifecycle;
        public final Handler mHandler;
        public final KeyguardEditModeController mKeyguardEditModeController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final PluginLockStarManager mPluginLockStarManager;
        private final SettingsHelper mSettingsHelper;
        public final WakefulnessLifecycle mWakefulnessLifecycle;

        public Factory(Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, DisplayLifecycle displayLifecycle, ConfigurationController configurationController, KeyguardEditModeController keyguardEditModeController, PluginLockStarManager pluginLockStarManager) {
            this.mHandler = handler;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mWakefulnessLifecycle = wakefulnessLifecycle;
            this.mSettingsHelper = settingsHelper;
            this.mDisplayLifecycle = displayLifecycle;
            this.mConfigurationController = configurationController;
            this.mKeyguardEditModeController = keyguardEditModeController;
            this.mPluginLockStarManager = pluginLockStarManager;
        }

        public final KeyguardPunchHoleVIViewController create(KeyguardPunchHoleVIView keyguardPunchHoleVIView) {
            return new KeyguardPunchHoleVIViewController(keyguardPunchHoleVIView, this.mHandler, this.mKeyguardUpdateMonitor, this.mWakefulnessLifecycle, this.mSettingsHelper, this.mDisplayLifecycle, this.mConfigurationController, this.mKeyguardEditModeController, this.mPluginLockStarManager);
        }
    }

    /* renamed from: $r8$lambda$1DuoW-xem1eLxLd0EvhmzdXc9U0, reason: not valid java name */
    public static void m973$r8$lambda$1DuoWxem1eLxLd0EvhmzdXc9U0(KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController) {
        if (((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).mIsAnimationPlaying) {
            keyguardPunchHoleVIViewController.stopVI();
        } else if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isFaceDetectionRunning()) {
            keyguardPunchHoleVIViewController.startVI();
        }
    }

    public static void $r8$lambda$ELksg8YKUHKs1dcJxyczqlKuknQ(KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController) {
        keyguardPunchHoleVIViewController.mKeyguardUpdateMonitor.removeCallback(keyguardPunchHoleVIViewController.mKeyguardUpdateMonitorCallback);
        keyguardPunchHoleVIViewController.mWakefulnessLifecycle.removeObserver(keyguardPunchHoleVIViewController.mWakefulnessObserver);
        keyguardPunchHoleVIViewController.mSettingsHelper.unregisterCallback(keyguardPunchHoleVIViewController.mSettingsListener);
        if (DeviceState.shouldEnableKeyguardScreenRotation(keyguardPunchHoleVIViewController.getContext())) {
            keyguardPunchHoleVIViewController.mDisplayLifecycle.removeObserver(keyguardPunchHoleVIViewController.mDisplayLifeCycleObserver);
        }
        ((ConfigurationControllerImpl) keyguardPunchHoleVIViewController.mConfigurationController).removeCallback(keyguardPunchHoleVIViewController.mConfigurationListener);
        WallpaperUtils.removeSystemUIWidgetCallback(keyguardPunchHoleVIViewController);
        ((ArrayList) ((KeyguardEditModeControllerImpl) keyguardPunchHoleVIViewController.mKeyguardEditModeController).listeners).remove(keyguardPunchHoleVIViewController.mEditModeListener);
        keyguardPunchHoleVIViewController.mPluginLockStarManager.unregisterCallback(((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG);
    }

    public static void $r8$lambda$cNlzCeNAAKTX1ka2SNKtt0InTQI(KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController) {
        ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).mPunchHoleCallback = keyguardPunchHoleVIViewController.mPunchHoleCallback;
        keyguardPunchHoleVIViewController.mKeyguardUpdateMonitor.registerCallback(keyguardPunchHoleVIViewController.mKeyguardUpdateMonitorCallback);
        keyguardPunchHoleVIViewController.mWakefulnessLifecycle.addObserver(keyguardPunchHoleVIViewController.mWakefulnessObserver);
        keyguardPunchHoleVIViewController.mSettingsHelper.registerCallback(keyguardPunchHoleVIViewController.mSettingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING));
        if (DeviceState.shouldEnableKeyguardScreenRotation(keyguardPunchHoleVIViewController.getContext())) {
            keyguardPunchHoleVIViewController.mDisplayLifecycle.addObserver(keyguardPunchHoleVIViewController.mDisplayLifeCycleObserver);
        }
        ((ConfigurationControllerImpl) keyguardPunchHoleVIViewController.mConfigurationController).addCallback(keyguardPunchHoleVIViewController.mConfigurationListener);
        WallpaperUtils.registerSystemUIWidgetCallback(keyguardPunchHoleVIViewController, 528L);
        ((ArrayList) ((KeyguardEditModeControllerImpl) keyguardPunchHoleVIViewController.mKeyguardEditModeController).listeners).add(keyguardPunchHoleVIViewController.mEditModeListener);
        keyguardPunchHoleVIViewController.mPluginLockStarManager.registerCallback(((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG, keyguardPunchHoleVIViewController.mLockStarCallback);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$3] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$4] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$5] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$6] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$7] */
    public KeyguardPunchHoleVIViewController(KeyguardPunchHoleVIView keyguardPunchHoleVIView, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, DisplayLifecycle displayLifecycle, ConfigurationController configurationController, KeyguardEditModeController keyguardEditModeController, PluginLockStarManager pluginLockStarManager) {
        super(keyguardPunchHoleVIView);
        this.mIsBouncerVI = false;
        this.mIsFaceRunning = false;
        this.mLastLayoutDirection = 0;
        this.mLastDensityDpi = 0;
        this.mIsLockStarEnabled = false;
        this.mPunchHoleCallback = new AnonymousClass1();
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                if (biometricSourceType != BiometricSourceType.FACE) {
                    return;
                }
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                keyguardPunchHoleVIViewController.mIsFaceRunning = z;
                if (z) {
                    keyguardPunchHoleVIViewController.startVI();
                } else if (((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mIsAnimationPlaying) {
                    keyguardPunchHoleVIViewController.stopVI();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFaceUnlockOptionChanged(boolean z) {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onFaceUnlockOptionChanged enabled = ", ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG, z);
                if (z) {
                    keyguardPunchHoleVIViewController.setPunchHoleVI();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                Log.d(((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG, "onKeyguardBouncerFullyShowingChanged");
                if (keyguardPunchHoleVIViewController.mKeyguardUpdateMonitor.isFaceDetectionRunning()) {
                    keyguardPunchHoleVIViewController.startVI();
                } else {
                    keyguardPunchHoleVIViewController.stopVI();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                if (DeviceState.shouldEnableKeyguardScreenRotation(keyguardPunchHoleVIViewController.getContext())) {
                    boolean zIsFaceOptionEnabled = keyguardPunchHoleVIViewController.mKeyguardUpdateMonitor.isFaceOptionEnabled();
                    AnonymousClass4 anonymousClass4 = keyguardPunchHoleVIViewController.mDisplayLifeCycleObserver;
                    DisplayLifecycle displayLifecycle2 = keyguardPunchHoleVIViewController.mDisplayLifecycle;
                    if (zIsFaceOptionEnabled) {
                        displayLifecycle2.addObserver(anonymousClass4);
                    } else {
                        displayLifecycle2.removeObserver(anonymousClass4);
                    }
                }
            }
        };
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.3
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                Log.d(((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG, "onFinishedWakingUp");
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mIsConfigUpdateNecessary) {
                    ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mIsConfigUpdateNecessary = false;
                }
                ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).updateScreenConfig();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).mLastWakeReason == 12) {
                    ((KeyguardPunchHoleVIView) ((ViewController) KeyguardPunchHoleVIViewController.this).mView).mIsConfigUpdateNecessary = true;
                }
            }
        };
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardPunchHoleVIViewController.m973$r8$lambda$1DuoWxem1eLxLd0EvhmzdXc9U0(this.f$0);
            }
        };
        this.mDisplayLifeCycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.4
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i) {
                ((KeyguardPunchHoleVIView) ((ViewController) KeyguardPunchHoleVIViewController.this).mView).updateScreenConfig();
            }

            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (z) {
                    KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                    if (!((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mIsAnimationPlaying || keyguardPunchHoleVIViewController.mIsFaceRunning) {
                        return;
                    }
                    keyguardPunchHoleVIViewController.stopVI();
                }
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.5
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = configuration.densityDpi;
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                int layoutDirection = ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).getLayoutDirection();
                int i2 = ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mLastDisplayDeviceType;
                int i3 = configuration.semDisplayDeviceType;
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && i2 != i3) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i3, "onConfigChanged() display device type ", " -> ", ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG);
                    ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mLastDisplayDeviceType = i3;
                    ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).updateScreenConfig();
                } else {
                    if (keyguardPunchHoleVIViewController.mLastDensityDpi == i && keyguardPunchHoleVIViewController.mLastLayoutDirection == layoutDirection) {
                        return;
                    }
                    String str = ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG;
                    StringBuilder sb = new StringBuilder("onConfigChanged() density ");
                    ViewPager$$ExternalSyntheticOutline0.m(sb, keyguardPunchHoleVIViewController.mLastDensityDpi, " -> ", i, ", direction ");
                    KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sb, keyguardPunchHoleVIViewController.mLastLayoutDirection, " -> ", layoutDirection, str);
                    keyguardPunchHoleVIViewController.mLastDensityDpi = i;
                    keyguardPunchHoleVIViewController.mLastLayoutDirection = layoutDirection;
                    ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).updateVILocation();
                }
            }
        };
        this.mLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.6
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onChangedLockStarData is ", ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG, z);
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    keyguardPunchHoleVIViewController.mIsLockStarEnabled = z;
                }
                if (z) {
                    keyguardPunchHoleVIViewController.updatePunchHoleColor();
                    return;
                }
                final KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = (KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView;
                keyguardPunchHoleVIView2.mVIView.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback(keyguardPunchHoleVIView2) { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView.2
                    public AnonymousClass2(final KeyguardPunchHoleVIView keyguardPunchHoleVIView22) {
                    }

                    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                    public final /* bridge */ /* synthetic */ Object getValue() {
                        return null;
                    }
                });
                keyguardPunchHoleVIViewController.setPunchHoleVI();
            }

            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final Bundle request(Bundle bundle) {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                Log.d(((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).TAG, "LockStarCallback: request: " + bundle);
                if (!TextUtils.equals(bundle.getString("type", ""), PluginLockStar.PUNCH_HOLE_TYPE)) {
                    return null;
                }
                keyguardPunchHoleVIViewController.updatePunchHoleColor();
                return null;
            }
        };
        this.mEditModeListener = new KeyguardEditModeController.Listener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.7
            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationEnded() {
                KeyguardPunchHoleVIViewController.this.startVI();
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationStarted(boolean z) {
                KeyguardPunchHoleVIViewController.this.stopVI();
            }
        };
        this.mHandler = handler;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mSettingsHelper = settingsHelper;
        this.mDisplayLifecycle = displayLifecycle;
        this.mConfigurationController = configurationController;
        this.mKeyguardEditModeController = keyguardEditModeController;
        this.mPluginLockStarManager = pluginLockStarManager;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        VIDirector vIDirector;
        ((KeyguardPunchHoleVIView) this.mView).bringToFront();
        if (!this.mIsBouncerVI || (vIDirector = ((KeyguardPunchHoleVIView) this.mView).mVIDirector) == null) {
            return;
        }
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            PluginLockStar pluginLockStar = this.mPluginLockStarManager.mPluginLockStar;
            this.mIsLockStarEnabled = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
        }
        vIDirector.mIsBouncer = true;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mHandler.post(new KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mHandler.post(new KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda0(this, 1));
    }

    public final void playAnimation(boolean z) {
        boolean z2;
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            z2 = this.mIsLockStarEnabled;
        } else {
            PluginLockStar pluginLockStar = this.mPluginLockStarManager.mPluginLockStar;
            z2 = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
        }
        if (z2 && z) {
            updatePunchHoleColor();
        }
        KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
        keyguardPunchHoleVIView.mVIView.setVisibility(z ? 0 : 4);
        if (z) {
            keyguardPunchHoleVIView.mVIView.playAnimation();
        } else {
            keyguardPunchHoleVIView.mVIView.pauseAnimation();
        }
    }

    public final void setBouncer() {
        this.mIsBouncerVI = true;
        ((KeyguardPunchHoleVIView) this.mView).TAG = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), ((KeyguardPunchHoleVIView) this.mView).TAG, "_Bouncer");
    }

    public final void setPunchHoleVI() {
        final String strM;
        boolean z = LsRune.SECURITY_PUNCH_HOLE_FACE_VI;
        if (z && this.mKeyguardUpdateMonitor.isFaceOptionEnabled()) {
            final KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
            Log.d(keyguardPunchHoleVIView.TAG, "setFaceRecognitionVI()");
            if (!z) {
                Log.d(keyguardPunchHoleVIView.TAG, "setFaceRecognitionVI() return - face recognition vi is not supported by product feature");
            } else if (keyguardPunchHoleVIView.mVIDirector == null) {
                String str = keyguardPunchHoleVIView.TAG;
                StringBuilder sb = new StringBuilder("setFaceRecognitionVI() return - mVIDirector is null (");
                VIDirectorFactory.Companion.getClass();
                sb.append(VIDirectorFactory.vendorName);
                sb.append(")");
                Log.e(str, sb.toString());
            } else {
                if (keyguardPunchHoleVIView.mCurrentAnimation != 1) {
                    EmergencyButtonController$$ExternalSyntheticOutline0.m(new StringBuilder("setAnimation() "), keyguardPunchHoleVIView.mCurrentAnimation, " -> 1", keyguardPunchHoleVIView.TAG);
                    keyguardPunchHoleVIView.mCurrentAnimation = 1;
                }
                VIDirector vIDirector = keyguardPunchHoleVIView.mVIDirector;
                if (WallpaperUtils.isWhiteKeyguardWallpaper(vIDirector.mIsBouncer ? BriefViewController.SUGGESTION_BACKGROUND_KEY : "statusbar")) {
                    Log.d("KeyguardPunchHoleVIView_VIDirector", "getFaceRecognitionVIFileName() - file name = " + vIDirector.mVIFileName + "_whitebg.json");
                    strM = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), vIDirector.mVIFileName, "_whitebg.json");
                } else {
                    Log.d("KeyguardPunchHoleVIView_VIDirector", "getFaceRecognitionVIFileName() - file name = " + vIDirector.mVIFileName + ".json");
                    strM = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), vIDirector.mVIFileName, ".json");
                }
                if (TextUtils.isEmpty(strM)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("prepareVI() - return, no VI file : ", strM, keyguardPunchHoleVIView.TAG);
                } else if (TextUtils.equals(strM, keyguardPunchHoleVIView.mAppliedVIFileName)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("prepareVI() - return, already applied : ", strM, keyguardPunchHoleVIView.TAG);
                } else {
                    keyguardPunchHoleVIView.mAppliedVIFileName = strM;
                    try {
                        LottieTask lottieTaskFromAsset = LottieCompositionFactory.fromAsset(keyguardPunchHoleVIView.getContext(), strM);
                        lottieTaskFromAsset.addListener(new LottieListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda1
                            @Override // com.airbnb.lottie.LottieListener
                            public final void onResult(Object obj) {
                                KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = keyguardPunchHoleVIView;
                                Log.d(keyguardPunchHoleVIView2.TAG, "prepareVI() - VI is prepared");
                                keyguardPunchHoleVIView2.mVIView.setComposition((LottieComposition) obj);
                                keyguardPunchHoleVIView2.mVIView.setRepeatCount(-1);
                                keyguardPunchHoleVIView2.setPrepareState(3);
                                keyguardPunchHoleVIView2.mHandler.removeCallbacks(keyguardPunchHoleVIView2.updateVILocationRunnable);
                                keyguardPunchHoleVIView2.mHandler.post(keyguardPunchHoleVIView2.updateVILocationRunnable);
                                KeyguardPunchHoleVIViewController.AnonymousClass1 anonymousClass1 = keyguardPunchHoleVIView2.mPunchHoleCallback;
                                if (anonymousClass1 != null) {
                                    KeyguardPunchHoleVIViewController.this.startVI();
                                }
                            }
                        });
                        lottieTaskFromAsset.addFailureListener(new LottieListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda2
                            @Override // com.airbnb.lottie.LottieListener
                            public final void onResult(Object obj) {
                                KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = keyguardPunchHoleVIView;
                                Log.e(keyguardPunchHoleVIView2.TAG, "Unable to parse json composition : " + strM);
                                keyguardPunchHoleVIView2.setPrepareState(0);
                            }
                        });
                        keyguardPunchHoleVIView.mIsAnimationPlaying = false;
                        keyguardPunchHoleVIView.setPrepareState(1);
                    } catch (Exception e) {
                        Log.e(keyguardPunchHoleVIView.TAG, "Failed to prepareVI : " + e.getMessage() + ", " + Debug.getCallers(10));
                        keyguardPunchHoleVIView.mIsAnimationPlaying = false;
                        keyguardPunchHoleVIView.setPrepareState(1);
                    }
                }
            }
            if (((KeyguardPunchHoleVIView) this.mView).mIsAnimationPlaying) {
                return;
            }
            playAnimation(false);
        }
    }

    public final void startVI() {
        VIDirector vIDirector;
        if (!this.mKeyguardUpdateMonitor.isFaceDetectionRunning()) {
            Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "startVI() - return, face recognition is stopped");
            return;
        }
        if (this.mWakefulnessLifecycle.mWakefulness != 2) {
            Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "startVI() - return, WakefulnessLifecycle is not WAKEFULNESS_AWAKE");
            return;
        }
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
            if (keyguardPunchHoleVIView.mIsConfigUpdateNecessary) {
                Log.d(keyguardPunchHoleVIView.TAG, "startVI() - return, Fold open - necessary to update VI position");
                return;
            }
        }
        if (DeviceState.isSmartViewFitToActiveDisplay()) {
            Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "startVI() - return, smart view");
            return;
        }
        if (this.mSettingsHelper.isOneHandModeRunning()) {
            Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "startVI() - return, one hand mode running");
            return;
        }
        if (((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).getVIRunning()) {
            Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "startVI() - return, edit mode VI running");
            return;
        }
        KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = (KeyguardPunchHoleVIView) this.mView;
        int i = keyguardPunchHoleVIView2.mPreparedState;
        if (i != 3) {
            if (i == 1) {
                keyguardPunchHoleVIView2.setPrepareState(2);
            }
            Log.d(keyguardPunchHoleVIView2.TAG, "startVI() - return, not prepared");
        } else {
            if (keyguardPunchHoleVIView2.mIsAnimationPlaying && (vIDirector = keyguardPunchHoleVIView2.mVIDirector) != null && keyguardPunchHoleVIView2.mLastUpdatedRotation == vIDirector.getScreenRotation()) {
                Log.d(keyguardPunchHoleVIView2.TAG, "startVI() - return, animation is already playing");
                return;
            }
            Log.d(keyguardPunchHoleVIView2.TAG, "startVI()");
            keyguardPunchHoleVIView2.mIsAnimationPlaying = true;
            playAnimation(true);
        }
    }

    public final void stopVI() {
        KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
        if (keyguardPunchHoleVIView.mPreparedState != 3) {
            Log.d(keyguardPunchHoleVIView.TAG, "stopVI() - return, not prepared");
        } else {
            if (!keyguardPunchHoleVIView.mIsAnimationPlaying) {
                Log.d(keyguardPunchHoleVIView.TAG, "stopVI() - return, animation is not playing");
                return;
            }
            Log.d(keyguardPunchHoleVIView.TAG, "stopVI()");
            keyguardPunchHoleVIView.mIsAnimationPlaying = false;
            playAnimation(false);
        }
    }

    public final void updatePunchHoleColor() {
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (pluginLockStarManager != null) {
            Color colorValueOf = null;
            if (pluginLockStarManager.getLockStarValues() != null) {
                try {
                    colorValueOf = Color.valueOf(pluginLockStarManager.getLockStarValues().getPunchHoleColor());
                } catch (Throwable th) {
                    Log.w("LStar|PluginLockStarManager", "getPunchHoleColor() failed " + th.getMessage());
                }
            }
            if (colorValueOf != null) {
                final int argb = colorValueOf.toArgb();
                final KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
                keyguardPunchHoleVIView.mVIView.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback(keyguardPunchHoleVIView, argb) { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView.1
                    public final /* synthetic */ int val$color;

                    public AnonymousClass1(final KeyguardPunchHoleVIView keyguardPunchHoleVIView2, final int argb2) {
                        this.val$color = argb2;
                    }

                    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                    public final Object getValue() {
                        return new PorterDuffColorFilter(this.val$color, PorterDuff.Mode.SRC_ATOP);
                    }
                });
            }
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "updateStyle setPunchHoleVI");
        setPunchHoleVI();
    }
}
