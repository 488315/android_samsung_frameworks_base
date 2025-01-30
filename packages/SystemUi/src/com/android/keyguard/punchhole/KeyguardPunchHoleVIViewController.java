package com.android.keyguard.punchhole;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPunchHoleVIViewController extends ViewController implements SystemUIWidgetCallback {
    public final ConfigurationController mConfigurationController;
    public final C08755 mConfigurationListener;
    public final C08744 mDisplayLifeCycleObserver;
    public final DisplayLifecycle mDisplayLifecycle;
    public final C08777 mEditModeListener;
    public final Handler mHandler;
    public boolean mIsBouncerVI;
    public boolean mIsLockStarEnabled;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public int mLastDensityDpi;
    public int mLastLayoutDirection;
    public final C08766 mLockStarCallback;
    public PluginLockStar.Modifier mPlayModifier;
    public final PluginLockStarManager mPluginLockStarManager;
    public final C08711 mPunchHoleCallback;
    public final KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda2 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public final SettingsHelper mSettingsHelper;
    public final KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda1 mSettingsListener;
    public PluginLockStar.Modifier mStopModifier;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final C08733 mWakefulnessObserver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$1 */
    public final class C08711 {
        public C08711() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final ConfigurationController mConfigurationController;
        public final DisplayLifecycle mDisplayLifecycle;
        public final Handler mHandler;
        public final KeyguardEditModeController mKeyguardEditModeController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final PluginLockStarManager mPluginLockStarManager;
        public final SecRotationWatcher mRotationWatcher;
        public final SettingsHelper mSettingsHelper;
        public final WakefulnessLifecycle mWakefulnessLifecycle;

        public Factory(Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, DisplayLifecycle displayLifecycle, SecRotationWatcher secRotationWatcher, ConfigurationController configurationController, KeyguardEditModeController keyguardEditModeController, PluginLockStarManager pluginLockStarManager) {
            this.mHandler = handler;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mWakefulnessLifecycle = wakefulnessLifecycle;
            this.mSettingsHelper = settingsHelper;
            this.mDisplayLifecycle = displayLifecycle;
            this.mRotationWatcher = secRotationWatcher;
            this.mConfigurationController = configurationController;
            this.mKeyguardEditModeController = keyguardEditModeController;
            this.mPluginLockStarManager = pluginLockStarManager;
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$7] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$3] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$4] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$5] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$6] */
    public KeyguardPunchHoleVIViewController(KeyguardPunchHoleVIView keyguardPunchHoleVIView, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, DisplayLifecycle displayLifecycle, SecRotationWatcher secRotationWatcher, ConfigurationController configurationController, KeyguardEditModeController keyguardEditModeController, PluginLockStarManager pluginLockStarManager) {
        super(keyguardPunchHoleVIView);
        this.mIsBouncerVI = false;
        this.mLastLayoutDirection = 0;
        this.mLastDensityDpi = 0;
        this.mIsLockStarEnabled = false;
        this.mPunchHoleCallback = new C08711();
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType != BiometricSourceType.FACE) {
                    return;
                }
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                if (z) {
                    keyguardPunchHoleVIViewController.startVI();
                } else if (((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).mIsAnimationPlaying) {
                    keyguardPunchHoleVIViewController.stopVI();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                Log.d(((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG, "onKeyguardBouncerFullyShowingChanged");
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
                    KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda2 keyguardPunchHoleVIViewController$$ExternalSyntheticLambda2 = keyguardPunchHoleVIViewController.mRotationConsumer;
                    SecRotationWatcher secRotationWatcher2 = keyguardPunchHoleVIViewController.mRotationWatcher;
                    if (isFaceOptionEnabled) {
                        secRotationWatcher2.addCallback(keyguardPunchHoleVIViewController$$ExternalSyntheticLambda2);
                    } else {
                        secRotationWatcher2.removeCallback(keyguardPunchHoleVIViewController$$ExternalSyntheticLambda2);
                    }
                }
            }
        };
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.3
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                Log.d(((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG, "onFinishedWakingUp");
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    View view = keyguardPunchHoleVIViewController.mView;
                    if (((KeyguardPunchHoleVIView) view).mIsConfigUpdateNecessary) {
                        ((KeyguardPunchHoleVIView) view).mIsConfigUpdateNecessary = false;
                    }
                }
                ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).updateScreenConfig();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).mLastWakeReason == 9) {
                    ((KeyguardPunchHoleVIView) KeyguardPunchHoleVIViewController.this.mView).mIsConfigUpdateNecessary = true;
                }
            }
        };
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                if (((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).mIsAnimationPlaying) {
                    keyguardPunchHoleVIViewController.stopVI();
                } else if (((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isFaceDetectionRunning()) {
                    keyguardPunchHoleVIViewController.startVI();
                }
            }
        };
        this.mDisplayLifeCycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.4
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i) {
                ((KeyguardPunchHoleVIView) KeyguardPunchHoleVIViewController.this.mView).updateScreenConfig();
            }

            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (z) {
                    KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                    if (((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).mIsAnimationPlaying) {
                        keyguardPunchHoleVIViewController.stopVI();
                    }
                }
            }
        };
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda2
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                ((KeyguardPunchHoleVIView) KeyguardPunchHoleVIViewController.this.mView).updateScreenConfig();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.5
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = configuration.densityDpi;
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                int layoutDirection = ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).getLayoutDirection();
                View view = keyguardPunchHoleVIViewController.mView;
                int i2 = ((KeyguardPunchHoleVIView) view).mLastDisplayDeviceType;
                int i3 = configuration.semDisplayDeviceType;
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && i2 != i3) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onConfigChanged() display device type ", i2, " -> ", i3, ((KeyguardPunchHoleVIView) view).TAG);
                    View view2 = keyguardPunchHoleVIViewController.mView;
                    ((KeyguardPunchHoleVIView) view2).mLastDisplayDeviceType = i3;
                    ((KeyguardPunchHoleVIView) view2).updateScreenConfig();
                    return;
                }
                if (keyguardPunchHoleVIViewController.mLastDensityDpi == i && keyguardPunchHoleVIViewController.mLastLayoutDirection == layoutDirection) {
                    return;
                }
                String str = ((KeyguardPunchHoleVIView) view).TAG;
                StringBuilder sb = new StringBuilder("onConfigChanged() density ");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, keyguardPunchHoleVIViewController.mLastDensityDpi, " -> ", i, ", direction ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb, keyguardPunchHoleVIViewController.mLastLayoutDirection, " -> ", layoutDirection, str);
                keyguardPunchHoleVIViewController.mLastDensityDpi = i;
                keyguardPunchHoleVIViewController.mLastLayoutDirection = layoutDirection;
                ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).updateVILocation();
            }
        };
        this.mLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController.6
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarEnabled(boolean z) {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = KeyguardPunchHoleVIViewController.this;
                if (z2) {
                    keyguardPunchHoleVIViewController.mIsLockStarEnabled = z;
                }
                KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = (KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView;
                FrameLayout frameLayout = keyguardPunchHoleVIView2.mLockStarVIView;
                if (!z) {
                    frameLayout.removeAllViews();
                    keyguardPunchHoleVIViewController.setPunchHoleVI();
                    return;
                }
                keyguardPunchHoleVIView2.mAppliedVIFileName = null;
                keyguardPunchHoleVIViewController.acceptModifier(true);
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
        this.mRotationWatcher = secRotationWatcher;
        this.mConfigurationController = configurationController;
        this.mKeyguardEditModeController = keyguardEditModeController;
        this.mPluginLockStarManager = pluginLockStarManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0046, code lost:
    
        if (r3.toString().contains("bouncer") != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void acceptModifier(boolean z) {
        PluginLockStar pluginLockStar = this.mPluginLockStarManager.mPluginLockStar;
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK ? this.mIsLockStarEnabled : pluginLockStar != null && pluginLockStar.isLockStarEnabled()) {
            this.mPlayModifier = pluginLockStar.getModifier("punchHoleVIPlay");
            PluginLockStar.Modifier modifier = pluginLockStar.getModifier("punchHoleVIStop");
            this.mStopModifier = modifier;
            FrameLayout frameLayout = ((KeyguardPunchHoleVIView) this.mView).mLockStarVIView;
            if (!z) {
                if (modifier != null) {
                    modifier.accept(frameLayout);
                }
            } else if (this.mPlayModifier != null) {
                Object tag = frameLayout.getTag();
                String str = tag != null ? "bouncer" : "";
                StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, ",whitebg:");
                m2m.append(WallpaperUtils.isWhiteKeyguardWallpaper(((KeyguardPunchHoleVIView) this.mView).mVIDirector.mIsBouncer ? "background" : "statusbar"));
                frameLayout.setTag(m2m.toString());
                this.mPlayModifier.accept(frameLayout);
            }
        }
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
                acceptModifier(true);
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
            acceptModifier(z);
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

    public final void setPunchHoleVI() {
        final String m16m;
        final KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
        Log.d(keyguardPunchHoleVIView.TAG, "setFaceRecognitionVI()");
        if (!LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
            Log.d(keyguardPunchHoleVIView.TAG, "setFaceRecognitionVI() return - face recognition vi is not supported by product feature");
            return;
        }
        if (keyguardPunchHoleVIView.mVIDirector == null) {
            String str = keyguardPunchHoleVIView.TAG;
            StringBuilder sb = new StringBuilder("setFaceRecognitionVI() return - mVIDirector is null (");
            VIDirectorFactory.Companion.getClass();
            sb.append(VIDirectorFactory.vendorName);
            sb.append(")");
            Log.e(str, sb.toString());
            return;
        }
        if (keyguardPunchHoleVIView.mCurrentAnimation != 1) {
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(new StringBuilder("setAnimation() "), keyguardPunchHoleVIView.mCurrentAnimation, " -> 1", keyguardPunchHoleVIView.TAG);
            keyguardPunchHoleVIView.mCurrentAnimation = 1;
        }
        VIDirector vIDirector = keyguardPunchHoleVIView.mVIDirector;
        if (WallpaperUtils.isWhiteKeyguardWallpaper(vIDirector.mIsBouncer ? "background" : "statusbar")) {
            Log.d("KeyguardPunchHoleVIView_VIDirector", "getFaceRecognitionVIFileName() - file name = " + vIDirector.mVIFileName + "_whitebg.json");
            m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), vIDirector.mVIFileName, "_whitebg.json");
        } else {
            Log.d("KeyguardPunchHoleVIView_VIDirector", "getFaceRecognitionVIFileName() - file name = " + vIDirector.mVIFileName + ".json");
            m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), vIDirector.mVIFileName, ".json");
        }
        if (TextUtils.isEmpty(m16m)) {
            AbstractC0000x2c234b15.m3m("prepareVI() - return, no VI file : ", m16m, keyguardPunchHoleVIView.TAG);
            return;
        }
        if (TextUtils.equals(m16m, keyguardPunchHoleVIView.mAppliedVIFileName)) {
            AbstractC0000x2c234b15.m3m("prepareVI() - return, already applied : ", m16m, keyguardPunchHoleVIView.TAG);
            return;
        }
        keyguardPunchHoleVIView.mAppliedVIFileName = m16m;
        LottieTask fromAsset = LottieCompositionFactory.fromAsset(keyguardPunchHoleVIView.getContext(), m16m);
        fromAsset.addListener(new LottieListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda0
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = KeyguardPunchHoleVIView.this;
                Log.d(keyguardPunchHoleVIView2.TAG, "prepareVI() - VI is prepared");
                keyguardPunchHoleVIView2.mVIView.setComposition((LottieComposition) obj);
                keyguardPunchHoleVIView2.mVIView.setRepeatCount(-1);
                keyguardPunchHoleVIView2.setPrepareState(3);
                keyguardPunchHoleVIView2.mHandler.removeCallbacks(keyguardPunchHoleVIView2.updateVILocationRunnable);
                keyguardPunchHoleVIView2.mHandler.post(keyguardPunchHoleVIView2.updateVILocationRunnable);
                KeyguardPunchHoleVIViewController.C08711 c08711 = keyguardPunchHoleVIView2.mPunchHoleCallback;
                if (c08711 != null) {
                    KeyguardPunchHoleVIViewController.this.startVI();
                }
            }
        });
        fromAsset.addFailureListener(new LottieListener() { // from class: com.android.keyguard.punchhole.KeyguardPunchHoleVIView$$ExternalSyntheticLambda1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                KeyguardPunchHoleVIView keyguardPunchHoleVIView2 = KeyguardPunchHoleVIView.this;
                Log.e(keyguardPunchHoleVIView2.TAG, "Unable to parse json composition : " + m16m);
                keyguardPunchHoleVIView2.setPrepareState(0);
            }
        });
        keyguardPunchHoleVIView.mIsAnimationPlaying = false;
        keyguardPunchHoleVIView.setPrepareState(1);
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
            View view = this.mView;
            if (((KeyguardPunchHoleVIView) view).mIsConfigUpdateNecessary) {
                Log.d(((KeyguardPunchHoleVIView) view).TAG, "startVI() - return, Fold open - necessary to update VI position");
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
        boolean z = false;
        if (i != 3) {
            if (i == 1) {
                keyguardPunchHoleVIView.setPrepareState(2);
            }
            Log.d(keyguardPunchHoleVIView.TAG, "startVI() - return, not prepared");
        } else if (keyguardPunchHoleVIView.mIsAnimationPlaying && (vIDirector = keyguardPunchHoleVIView.mVIDirector) != null && keyguardPunchHoleVIView.mLastUpdatedRotation == vIDirector.getScreenRotation()) {
            Log.d(keyguardPunchHoleVIView.TAG, "startVI() - return, animation is already playing");
        } else {
            Log.d(keyguardPunchHoleVIView.TAG, "startVI()");
            keyguardPunchHoleVIView.mIsAnimationPlaying = true;
            z = true;
        }
        if (z) {
            playAnimation(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void stopVI() {
        boolean z;
        KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) this.mView;
        if (keyguardPunchHoleVIView.mPreparedState != 3) {
            Log.d(keyguardPunchHoleVIView.TAG, "stopVI() - return, not prepared");
        } else {
            if (keyguardPunchHoleVIView.mIsAnimationPlaying) {
                Log.d(keyguardPunchHoleVIView.TAG, "stopVI()");
                keyguardPunchHoleVIView.mIsAnimationPlaying = false;
                z = true;
                if (z) {
                    return;
                }
                playAnimation(false);
                return;
            }
            Log.d(keyguardPunchHoleVIView.TAG, "stopVI() - return, animation is not playing");
        }
        z = false;
        if (z) {
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        Log.d(((KeyguardPunchHoleVIView) this.mView).TAG, "updateStyle setPunchHoleVI");
        setPunchHoleVI();
    }
}
