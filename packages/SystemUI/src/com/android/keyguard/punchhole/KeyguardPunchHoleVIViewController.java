package com.android.keyguard.punchhole;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
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

public final class KeyguardPunchHoleVIViewController extends ViewController implements SystemUIWidgetCallback {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass5 mConfigurationListener;
    public final AnonymousClass4 mDisplayLifeCycleObserver;
    public final DisplayLifecycle mDisplayLifecycle;
    public final AnonymousClass7 mEditModeListener;
    public final Handler mHandler;
    public boolean mIsBouncerVI;
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
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public final class Factory {
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
    public static void m851$r8$lambda$1DuoWxem1eLxLd0EvhmzdXc9U0(KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController) {
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
                if (z) {
                    keyguardPunchHoleVIViewController.startVI();
                } else if (((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mIsAnimationPlaying) {
                    keyguardPunchHoleVIViewController.stopVI();
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
                    boolean isFaceOptionEnabled = keyguardPunchHoleVIViewController.mKeyguardUpdateMonitor.isFaceOptionEnabled();
                    AnonymousClass4 anonymousClass4 = keyguardPunchHoleVIViewController.mDisplayLifeCycleObserver;
                    DisplayLifecycle displayLifecycle2 = keyguardPunchHoleVIViewController.mDisplayLifecycle;
                    if (isFaceOptionEnabled) {
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
                KeyguardPunchHoleVIViewController.m851$r8$lambda$1DuoWxem1eLxLd0EvhmzdXc9U0(KeyguardPunchHoleVIViewController.this);
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
                    if (((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mIsAnimationPlaying) {
                        keyguardPunchHoleVIViewController.stopVI();
                    }
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
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, keyguardPunchHoleVIViewController.mLastDensityDpi, " -> ", i, ", direction ");
                    KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, keyguardPunchHoleVIViewController.mLastLayoutDirection, " -> ", layoutDirection, str);
                    keyguardPunchHoleVIViewController.mLastDensityDpi = i;
                    keyguardPunchHoleVIViewController.mLastLayoutDirection = layoutDirection;
                    ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).updateVILocation();
                }
            }
        };
        this.mLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.6
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                if (z2) {
                    keyguardPunchHoleVIViewController.mIsLockStarEnabled = z;
                }
                FrameLayout frameLayout = ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mLockStarVIView;
                if (!z) {
                    frameLayout.removeAllViews();
                    keyguardPunchHoleVIViewController.setPunchHoleVI();
                    return;
                }
                ((KeyguardPunchHoleVIView) ((ViewController) keyguardPunchHoleVIViewController).mView).mAppliedVIFileName = null;
                keyguardPunchHoleVIViewController.acceptModifier$1(true);
                if (keyguardPunchHoleVIViewController.mIsBouncerVI) {
                    return;
                }
                frameLayout.setVisibility(4);
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
    
        if (r0.toString().contains("bouncer") != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void acceptModifier$1(boolean r4) {
        /*
            r3 = this;
            com.android.systemui.lockstar.PluginLockStarManager r0 = r3.mPluginLockStarManager
            com.samsung.systemui.splugins.lockstar.PluginLockStar r0 = r0.mPluginLockStar
            boolean r1 = com.android.systemui.LsRune.SECURITY_SUB_DISPLAY_LOCK
            if (r1 == 0) goto Lb
            boolean r1 = r3.mIsLockStarEnabled
            goto L16
        Lb:
            if (r0 == 0) goto L15
            boolean r1 = r0.isLockStarEnabled()
            if (r1 == 0) goto L15
            r1 = 1
            goto L16
        L15:
            r1 = 0
        L16:
            if (r1 == 0) goto L70
            T extends android.view.View r1 = r3.mView
            com.android.keyguard.punchhole.KeyguardPunchHoleVIView r1 = (com.android.keyguard.punchhole.KeyguardPunchHoleVIView) r1
            android.widget.FrameLayout r1 = r1.mLockStarVIView
            if (r4 == 0) goto L65
            java.lang.String r4 = "punchHoleVIPlay"
            com.samsung.systemui.splugins.lockstar.PluginLockStar$Modifier r4 = r0.getModifier(r4)
            if (r4 == 0) goto L70
            java.lang.Object r0 = r1.getTag()
            if (r0 == 0) goto L3b
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "bouncer"
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L3b
            goto L3d
        L3b:
            java.lang.String r2 = ""
        L3d:
            java.lang.String r0 = ",whitebg:"
            java.lang.StringBuilder r0 = android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r2, r0)
            T extends android.view.View r3 = r3.mView
            com.android.keyguard.punchhole.KeyguardPunchHoleVIView r3 = (com.android.keyguard.punchhole.KeyguardPunchHoleVIView) r3
            com.android.keyguard.punchhole.VIDirector r3 = r3.mVIDirector
            boolean r3 = r3.mIsBouncer
            if (r3 == 0) goto L50
            java.lang.String r3 = "background"
            goto L53
        L50:
            java.lang.String r3 = "statusbar"
        L53:
            boolean r3 = com.android.systemui.wallpaper.WallpaperUtils.isWhiteKeyguardWallpaper(r3)
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r1.setTag(r3)
            r4.accept(r1)
            goto L70
        L65:
            java.lang.String r3 = "punchHoleVIStop"
            com.samsung.systemui.splugins.lockstar.PluginLockStar$Modifier r3 = r0.getModifier(r3)
            if (r3 == 0) goto L70
            r3.accept(r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.acceptModifier$1(boolean):void");
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        VIDirector vIDirector;
        boolean z;
        ((KeyguardPunchHoleVIView) this.mView).bringToFront();
        if (this.mIsBouncerVI && (vIDirector = ((KeyguardPunchHoleVIView) this.mView).mVIDirector) != null) {
            boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
            boolean z3 = false;
            PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
            if (z2) {
                PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
                this.mIsLockStarEnabled = pluginLockStar != null && pluginLockStar.isLockStarEnabled();
            }
            vIDirector.mIsBouncer = true;
            if (z2) {
                z = this.mIsLockStarEnabled;
            } else {
                PluginLockStar pluginLockStar2 = pluginLockStarManager.mPluginLockStar;
                if (pluginLockStar2 != null && pluginLockStar2.isLockStarEnabled()) {
                    z3 = true;
                }
                z = z3;
            }
            if (z) {
                ((KeyguardPunchHoleVIView) this.mView).mLockStarVIView.setTag("bouncer");
                acceptModifier$1(true);
            }
        }
        setPunchHoleVI();
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
        if (z2) {
            acceptModifier$1(z);
            ((KeyguardPunchHoleVIView) this.mView).mLockStarVIView.setVisibility(z ? 0 : 4);
            return;
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
        ((KeyguardPunchHoleVIView) this.mView).TAG = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), ((KeyguardPunchHoleVIView) this.mView).TAG, "_Bouncer");
    }

    public final void setPunchHoleVI() {
        final String m;
        final KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
        Log.d(keyguardPunchHoleVIView.TAG, "setFaceRecognitionVI()");
        if (!LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
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
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("setAnimation() "), keyguardPunchHoleVIView.mCurrentAnimation, " -> 1", keyguardPunchHoleVIView.TAG);
                keyguardPunchHoleVIView.mCurrentAnimation = 1;
            }
            VIDirector vIDirector = keyguardPunchHoleVIView.mVIDirector;
            if (WallpaperUtils.isWhiteKeyguardWallpaper(vIDirector.mIsBouncer ? "background" : "statusbar")) {
                Log.d("KeyguardPunchHoleVIView_VIDirector", "getFaceRecognitionVIFileName() - file name = " + vIDirector.mVIFileName + "_whitebg.json");
                m = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), vIDirector.mVIFileName, "_whitebg.json");
            } else {
                Log.d("KeyguardPunchHoleVIView_VIDirector", "getFaceRecognitionVIFileName() - file name = " + vIDirector.mVIFileName + ".json");
                m = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), vIDirector.mVIFileName, ".json");
            }
            if (TextUtils.isEmpty(m)) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("prepareVI() - return, no VI file : ", m, keyguardPunchHoleVIView.TAG);
            } else if (TextUtils.equals(m, keyguardPunchHoleVIView.mAppliedVIFileName)) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("prepareVI() - return, already applied : ", m, keyguardPunchHoleVIView.TAG);
            } else {
                keyguardPunchHoleVIView.mAppliedVIFileName = m;
                LottieTask fromAsset = LottieCompositionFactory.fromAsset(keyguardPunchHoleVIView.getContext(), m);
                fromAsset.addListener(new LottieListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda1
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = KeyguardPunchHoleVIView.this;
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
                fromAsset.addFailureListener(new LottieListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda2
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = KeyguardPunchHoleVIView.this;
                        Log.e(keyguardPunchHoleVIView2.TAG, "Unable to parse json composition : " + m);
                        keyguardPunchHoleVIView2.setPrepareState(0);
                    }
                });
                keyguardPunchHoleVIView.mIsAnimationPlaying = false;
                keyguardPunchHoleVIView.setPrepareState(1);
            }
        }
        if (((KeyguardPunchHoleVIView) this.mView).mIsAnimationPlaying) {
            return;
        }
        playAnimation(false);
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
            T t = this.mView;
            if (((KeyguardPunchHoleVIView) t).mIsConfigUpdateNecessary) {
                Log.d(((KeyguardPunchHoleVIView) t).TAG, "startVI() - return, Fold open - necessary to update VI position");
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
        KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
        int i = keyguardPunchHoleVIView.mPreparedState;
        if (i != 3) {
            if (i == 1) {
                keyguardPunchHoleVIView.setPrepareState(2);
            }
            Log.d(keyguardPunchHoleVIView.TAG, "startVI() - return, not prepared");
        } else {
            if (keyguardPunchHoleVIView.mIsAnimationPlaying && (vIDirector = keyguardPunchHoleVIView.mVIDirector) != null && keyguardPunchHoleVIView.mLastUpdatedRotation == vIDirector.getScreenRotation()) {
                Log.d(keyguardPunchHoleVIView.TAG, "startVI() - return, animation is already playing");
                return;
            }
            Log.d(keyguardPunchHoleVIView.TAG, "startVI()");
            keyguardPunchHoleVIView.mIsAnimationPlaying = true;
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

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "updateStyle setPunchHoleVI");
        setPunchHoleVI();
    }
}
