package com.android.keyguard.biometrics;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.OneShotPreDrawListener;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecLockIconView;
import com.android.keyguard.SecurityUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public class KeyguardBiometricViewController extends ViewController implements SystemUIWidgetCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public KeyguardBiometricsCountDownTimer biometricCountDownTimer;
    public final SystemUITextView biometricErrorText;
    public final SystemUITextView biometricLockOutMessage;
    public final FrameLayout biometricRetryContainer;
    public final SystemUIImageView biometricRetryIcon;
    public boolean bouncerShowing;
    public final ConfigurationController configurationController;
    public final KeyguardBiometricViewController$configurationListener$1 configurationListener;
    public CountDownTimer countDownTimer;
    public int currentUserId;
    public int debugCount;
    public int displayDeviceType;
    public final DisplayLifecycle displayLifecycle;
    public int drawableResId;
    public String errorString;
    public boolean isHiddenRetry;
    public boolean isLockOut;
    public boolean isLockStarEnabled;
    public boolean isRunning;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardBiometricViewController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public final SystemUIImageView lockIcon;
    public final KeyguardBiometricViewController$lockIconRunnable$1 lockIconRunnable;
    public final SecLockIconView lockIconView;
    public final KeyguardBiometricViewController$lockStarCallback$1 lockStarCallback;
    public final LottieAnimationView lottieIcon;
    public final KeyguardBiometricViewController$lottieIconRunnable$1 lottieIconRunnable;
    public int mCurrentRotation;
    public final KeyguardBiometricViewController$mDisplayLifeCycleObserver$1 mDisplayLifeCycleObserver;
    public boolean mShowingRetryButton;
    public final PluginLockStarManager pluginLockStarManager;
    public final PowerManager powerManager;
    private SettingsHelper.OnChangedCallback settingsListener;
    public VibrationUtil vibration;
    public final VibrationUtil vibrationUtil;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v31, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$lottieIconRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v32, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$lockIconRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v33, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$lockStarCallback$1] */
    /* JADX WARN: Type inference failed for: r1v35, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r1v36, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$mDisplayLifeCycleObserver$1] */
    /* JADX WARN: Type inference failed for: r1v37, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$keyguardUpdateMonitorCallback$1] */
    public KeyguardBiometricViewController(KeyguardBiometricView keyguardBiometricView, KeyguardUpdateMonitor keyguardUpdateMonitor, AccessibilityManager accessibilityManager, PowerManager powerManager, ConfigurationController configurationController, DisplayLifecycle displayLifecycle, VibrationUtil vibrationUtil, PluginLockStarManager pluginLockStarManager, WindowManager windowManager, Lazy lazy) {
        super(keyguardBiometricView);
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.accessibilityManager = accessibilityManager;
        this.powerManager = powerManager;
        this.configurationController = configurationController;
        this.displayLifecycle = displayLifecycle;
        this.vibrationUtil = vibrationUtil;
        this.pluginLockStarManager = pluginLockStarManager;
        this.biometricRetryContainer = (FrameLayout) ((KeyguardBiometricView) this.mView).requireViewById(R.id.keyguard_biometric_retry_container);
        this.biometricErrorText = (SystemUITextView) ((KeyguardBiometricView) this.mView).requireViewById(R.id.keyguard_biometric_error_text);
        this.biometricRetryIcon = (SystemUIImageView) ((KeyguardBiometricView) this.mView).requireViewById(R.id.keyguard_biometric_retry_icon);
        this.lockIcon = (SystemUIImageView) ((KeyguardBiometricView) this.mView).requireViewById(R.id.bouncer_lock_icon);
        this.lockIconView = (SecLockIconView) ((KeyguardBiometricView) this.mView).requireViewById(R.id.bouncer_lock_icon_view);
        this.lottieIcon = (LottieAnimationView) ((KeyguardBiometricView) this.mView).requireViewById(R.id.bouncer_lottie_icon);
        this.biometricLockOutMessage = (SystemUITextView) ((KeyguardBiometricView) this.mView).requireViewById(R.id.biometric_timeout_message);
        this.errorString = "";
        final Looper mainLooper = Looper.getMainLooper();
        new Handler(mainLooper) { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$mHandler$1
        };
        this.lottieIconRunnable = new Runnable() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$lottieIconRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                int i = KeyguardBiometricViewController.$r8$clinit;
                keyguardBiometricViewController.resetLockIconState();
                KeyguardBiometricViewController keyguardBiometricViewController2 = this.this$0;
                if (!keyguardBiometricViewController2.mShowingRetryButton) {
                    keyguardBiometricViewController2.acceptLockStarModifier();
                }
                KeyguardBiometricViewController keyguardBiometricViewController3 = this.this$0;
                keyguardBiometricViewController3.lockIconView.updateScanningFaceAnimation(keyguardBiometricViewController3.lockIcon);
            }
        };
        this.lockIconRunnable = new Runnable() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$lockIconRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                keyguardBiometricViewController.lockIconView.updateScanningFaceAnimation(keyguardBiometricViewController.lockIcon);
            }
        };
        this.lockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$lockStarCallback$1
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) throws Resources.NotFoundException {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                if (z2) {
                    keyguardBiometricViewController.isLockStarEnabled = z;
                }
                int i = KeyguardBiometricViewController.$r8$clinit;
                keyguardBiometricViewController.initLockStarLockIcon(z);
            }
        };
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) throws Resources.NotFoundException {
                int i = KeyguardBiometricViewController.$r8$clinit;
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                keyguardBiometricViewController.getClass();
                keyguardBiometricViewController.lockIconView.mIsOneHandModeEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isOneHandModeRunning();
                keyguardBiometricViewController.updateLockIcon();
                keyguardBiometricViewController.lockIconView.updateScanningFaceAnimation(keyguardBiometricViewController.lockIcon);
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) throws Resources.NotFoundException {
                int i = configuration.semDisplayDeviceType;
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                    if (keyguardBiometricViewController.displayDeviceType != i) {
                        keyguardBiometricViewController.displayDeviceType = i;
                        PluginLockStar pluginLockStar = keyguardBiometricViewController.pluginLockStarManager.mPluginLockStar;
                        boolean zIsLockStarEnabled = pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false;
                        keyguardBiometricViewController.isLockStarEnabled = zIsLockStarEnabled;
                        keyguardBiometricViewController.initLockStarLockIcon(zIsLockStarEnabled);
                        keyguardBiometricViewController.updateBiometricViewLayout();
                        keyguardBiometricViewController.updateLayout$3();
                        keyguardBiometricViewController.updateVisibility$3();
                    }
                }
            }
        };
        this.mDisplayLifeCycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$mDisplayLifeCycleObserver$1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i) throws Resources.NotFoundException {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                int rotation = DeviceState.getRotation(DeviceState.getRotation(((KeyguardBiometricView) ((ViewController) keyguardBiometricViewController).mView).defaultDisplay.getRotation()));
                if (keyguardBiometricViewController.mCurrentRotation != rotation) {
                    keyguardBiometricViewController.mCurrentRotation = rotation;
                    keyguardBiometricViewController.updateBiometricViewLayout();
                    keyguardBiometricViewController.clearView();
                    keyguardBiometricViewController.updateLockIconVisibility(keyguardBiometricViewController.bouncerShowing);
                    keyguardBiometricViewController.updateLockIcon();
                    keyguardBiometricViewController.resetLockIconState();
                    keyguardBiometricViewController.lockIconView.setOnClickListener(new KeyguardBiometricViewController$setLockIconOnClickListener$1(keyguardBiometricViewController));
                    SystemUIImageView systemUIImageView = keyguardBiometricViewController.lockIcon;
                    systemUIImageView.setClickable(false);
                    systemUIImageView.setAccessibilityDelegate(new KeyguardBiometricViewController$setLockIconOnClickListener$2());
                }
            }
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) throws Resources.NotFoundException {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    int i = KeyguardBiometricViewController.$r8$clinit;
                    KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                    keyguardBiometricViewController.errorString = keyguardBiometricViewController.getContext().getString(R.string.kg_face_no_match);
                    KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController, keyguardBiometricViewController.errorString);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) throws Resources.NotFoundException {
                boolean z;
                if (biometricSourceType == BiometricSourceType.FACE) {
                    if (i == 10002 || i == 10003 || i == 10005) {
                        z = true;
                    } else if (i == 100001) {
                        return;
                    } else {
                        z = false;
                    }
                    KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                    keyguardBiometricViewController.isHiddenRetry = z;
                    if (str != null) {
                        keyguardBiometricViewController.errorString = str;
                    }
                    if (i == 3) {
                        keyguardBiometricViewController.errorString = "";
                        KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController, "");
                    } else if (i != 1006) {
                        KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController, keyguardBiometricViewController.errorString);
                    } else {
                        keyguardBiometricViewController.vibration = keyguardBiometricViewController.vibrationUtil;
                        KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController, keyguardBiometricViewController.errorString);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                Log.d("KeyguardBiometricView", "onBiometricsLockoutChanged( " + z + " )");
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                long lockoutAttemptDeadline = keyguardBiometricViewController.keyguardUpdateMonitor.getLockoutAttemptDeadline();
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardBiometricViewController.keyguardUpdateMonitor;
                if (keyguardUpdateMonitor2.mDeviceInteractive && z && lockoutAttemptDeadline == 0) {
                    int failedBiometricUnlockAttempts = keyguardUpdateMonitor2.getFailedBiometricUnlockAttempts(keyguardBiometricViewController.currentUserId);
                    if (failedBiometricUnlockAttempts == 0 || failedBiometricUnlockAttempts % 5 != 0) {
                        Log.d("KeyguardBiometricView", "onBiometricsLockoutChanged( mCountdownTimer is working. )");
                    } else {
                        keyguardBiometricViewController.handleBiometricAttemptLockout(keyguardUpdateMonitor2.getLockoutBiometricAttemptDeadline());
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) throws Resources.NotFoundException {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                    if (keyguardBiometricViewController.isRunning != z) {
                        keyguardBiometricViewController.isRunning = z;
                        if (z) {
                            keyguardBiometricViewController.errorString = "";
                            keyguardBiometricViewController.updateLockIcon();
                        } else {
                            keyguardBiometricViewController.updateLockIcon();
                            keyguardBiometricViewController.lockIconView.updateScanningFaceAnimation(keyguardBiometricViewController.lockIcon);
                        }
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                keyguardBiometricViewController.lockIcon.setImageDrawable(keyguardBiometricViewController.getContext().getDrawable(z ? R.drawable.lock_ic_lock_ddar : R.drawable.lock_ic_lock_mtrl_00));
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) throws Resources.NotFoundException {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                if (keyguardBiometricViewController.bouncerShowing != z) {
                    keyguardBiometricViewController.bouncerShowing = z;
                    keyguardBiometricViewController.errorString = "";
                    long j = WallpaperEventNotifier.getInstance().mCurStatusFlag;
                    WallpaperEventNotifier.getInstance().getSemWallpaperColors(false);
                    keyguardBiometricViewController.getClass();
                    keyguardBiometricViewController.updateVisibility$3();
                    if (z) {
                        keyguardBiometricViewController.updateLayout$3();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() throws Resources.NotFoundException {
                final KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                keyguardBiometricViewController.isLockOut = keyguardBiometricViewController.keyguardUpdateMonitor.isTimerRunning();
                if (keyguardBiometricViewController.isRunning || keyguardBiometricViewController.isLockOut) {
                    keyguardBiometricViewController.clearView();
                    keyguardBiometricViewController.updateLockIcon();
                    keyguardBiometricViewController.updateLockIconVisibility(keyguardBiometricViewController.bouncerShowing);
                    keyguardBiometricViewController.resetBiometricLockOutTimer();
                    long lockoutAttemptDeadline = keyguardBiometricViewController.keyguardUpdateMonitor.getLockoutAttemptDeadline();
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    CountDownTimer countDownTimer = keyguardBiometricViewController.countDownTimer;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    keyguardBiometricViewController.countDownTimer = null;
                    keyguardBiometricViewController.countDownTimer = new CountDownTimer(lockoutAttemptDeadline - jElapsedRealtime) { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$handleAttemptLockout$1
                        @Override // android.os.CountDownTimer
                        public final void onFinish() {
                            keyguardBiometricViewController.updateBiometricViewLayout();
                        }

                        @Override // android.os.CountDownTimer
                        public final void onTick(long j) {
                        }
                    }.start();
                    keyguardBiometricViewController.updateBiometricViewLayout();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRemoteLockInfoChanged() {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                int i = keyguardBiometricViewController.keyguardUpdateMonitor.isRemoteLockEnabled() ? 8 : 0;
                keyguardBiometricViewController.lockIconView.setVisibility(i);
                keyguardBiometricViewController.biometricRetryIcon.setVisibility(i);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) throws Resources.NotFoundException {
                int i = KeyguardBiometricViewController.$r8$clinit;
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                keyguardBiometricViewController.clearView();
                keyguardBiometricViewController.updateLayout$3();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) throws Resources.NotFoundException {
                KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
                int strongAuthForUser = keyguardBiometricViewController.keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
                if ((strongAuthForUser & 1) == 0 && (strongAuthForUser & 2) == 0 && (strongAuthForUser & 4) == 0 && (strongAuthForUser & 8) == 0 && (strongAuthForUser & 16) == 0 && (strongAuthForUser & 32) == 0) {
                    return;
                }
                keyguardBiometricViewController.clearView();
                keyguardBiometricViewController.updateLayout$3();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                this.this$0.currentUserId = i;
            }
        };
    }

    public static final void access$onClickRetryButton(KeyguardBiometricViewController keyguardBiometricViewController) throws Resources.NotFoundException {
        if (keyguardBiometricViewController.isHiddenRetry) {
            return;
        }
        Log.d("KeyguardBiometricView", "onClick - Retry icon");
        keyguardBiometricViewController.powerManager.userActivity(SystemClock.uptimeMillis(), true);
        KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardBiometricViewController.keyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isFaceOptionEnabled()) {
            keyguardUpdateMonitor.requestFaceAuth("Face auth triggered due to retry button click.");
            keyguardBiometricViewController.updateVisibility$3();
            SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_RETRY_BIOMETRICS, "2");
        }
    }

    public static final void access$updateErrorText(final KeyguardBiometricViewController keyguardBiometricViewController, String str) throws Resources.NotFoundException {
        if (keyguardBiometricViewController.bouncerShowing && !keyguardBiometricViewController.isLockOut) {
            keyguardBiometricViewController.clearView();
            keyguardBiometricViewController.biometricRetryContainer.setVisibility(0);
            SystemUITextView systemUITextView = keyguardBiometricViewController.biometricErrorText;
            systemUITextView.setText(str);
            systemUITextView.setVisibility(0);
            OneShotPreDrawListener.add(systemUITextView, new Runnable() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$setErrorText$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardBiometricViewController keyguardBiometricViewController2 = this.this$0;
                    SystemUITextView systemUITextView2 = keyguardBiometricViewController2.biometricErrorText;
                    if (systemUITextView2.getText().length() == 0) {
                        SystemUITextView systemUITextView3 = keyguardBiometricViewController2.biometricErrorText;
                        systemUITextView3.setAlpha(0.0f);
                        systemUITextView3.setScaleX(0.7f);
                        systemUITextView3.setScaleY(0.7f);
                        return;
                    }
                    if (keyguardBiometricViewController2.isLockOut) {
                        return;
                    }
                    SystemUITextView systemUITextView4 = keyguardBiometricViewController2.biometricErrorText;
                    systemUITextView4.setAlpha(0.0f);
                    systemUITextView4.setScaleX(0.7f);
                    systemUITextView4.setScaleY(0.7f);
                    SpringForce springForce = new SpringForce(1.0f);
                    springForce.setStiffness(350.0f);
                    springForce.setDampingRatio(0.78f);
                    DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
                    SystemUITextView systemUITextView5 = keyguardBiometricViewController2.biometricErrorText;
                    SpringAnimation springAnimation = new SpringAnimation(systemUITextView5, anonymousClass4);
                    springAnimation.mSpring = springForce;
                    springAnimation.start();
                    SpringAnimation springAnimation2 = new SpringAnimation(systemUITextView5, DynamicAnimation.SCALE_Y);
                    springAnimation2.mSpring = springForce;
                    springAnimation2.start();
                    SpringAnimation springAnimation3 = new SpringAnimation(systemUITextView5, DynamicAnimation.ALPHA);
                    springAnimation3.mSpring = springForce;
                    springAnimation3.start();
                    boolean z = keyguardBiometricViewController2.isHiddenRetry;
                    SecLockIconView secLockIconView = keyguardBiometricViewController2.lockIconView;
                    LottieAnimationView lottieAnimationView = keyguardBiometricViewController2.lottieIcon;
                    SystemUIImageView systemUIImageView = keyguardBiometricViewController2.lockIcon;
                    if (z || !keyguardBiometricViewController2.needsToChangeRetryButton()) {
                        systemUIImageView.setVisibility(8);
                        lottieAnimationView.setVisibility(0);
                        secLockIconView.showBiometricErrorAnimation(lottieAnimationView, systemUITextView2.getX(), keyguardBiometricViewController2.vibration);
                    } else {
                        systemUIImageView.setVisibility(0);
                        lottieAnimationView.setVisibility(8);
                        secLockIconView.showBiometricErrorAnimation(systemUIImageView, systemUITextView2.getX(), keyguardBiometricViewController2.vibration);
                    }
                    keyguardBiometricViewController2.vibration = null;
                }
            });
            keyguardBiometricViewController.biometricRetryIcon.setVisibility((!keyguardBiometricViewController.bouncerShowing || keyguardBiometricViewController.isHiddenRetry) ? 8 : 0);
            keyguardBiometricViewController.updateLockIcon();
        }
        if (((KeyguardBiometricView) keyguardBiometricViewController.mView).getVisibility() == 0 && keyguardBiometricViewController.accessibilityManager.isEnabled()) {
            ((KeyguardBiometricView) keyguardBiometricViewController.mView).announceForAccessibility(str);
        }
    }

    public final void acceptLockStarModifier() {
        boolean zIsLockStarEnabled;
        PluginLockStar pluginLockStar;
        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        PluginLockStarManager pluginLockStarManager = this.pluginLockStarManager;
        if (z) {
            zIsLockStarEnabled = this.isLockStarEnabled;
        } else {
            PluginLockStar pluginLockStar2 = pluginLockStarManager.mPluginLockStar;
            zIsLockStarEnabled = pluginLockStar2 != null ? pluginLockStar2.isLockStarEnabled() : false;
        }
        if (zIsLockStarEnabled && (pluginLockStar = pluginLockStarManager.mPluginLockStar) != null) {
            PluginLockStar.Modifier modifier = pluginLockStar.getModifier("lockIconVisibility");
            if (modifier != null) {
                modifier.accept(this.lockIconView);
            }
            PluginLockStar.Modifier modifier2 = pluginLockStar.getModifier("lockIconDrawable");
            if (modifier2 != null) {
                modifier2.accept(this.lockIcon);
            }
        }
    }

    public final void clearView() {
        SystemUITextView systemUITextView = this.biometricErrorText;
        systemUITextView.setText("");
        systemUITextView.setVisibility(8);
        this.biometricRetryIcon.setVisibility(8);
        this.biometricRetryContainer.setVisibility(8);
        SystemUIImageView systemUIImageView = this.lockIcon;
        int visibility = systemUIImageView.getVisibility();
        SecLockIconView secLockIconView = this.lockIconView;
        if (visibility == 0) {
            secLockIconView.initBiometricErrorIndicationAnimationValue(systemUIImageView, true, this.lockIconRunnable);
            return;
        }
        LottieAnimationView lottieAnimationView = this.lottieIcon;
        if (lottieAnimationView.getVisibility() == 0) {
            secLockIconView.initBiometricErrorIndicationAnimationValue(lottieAnimationView, true, this.lottieIconRunnable);
        }
    }

    public final void handleBiometricAttemptLockout(long j) {
        long jElapsedRealtime = j - SystemClock.elapsedRealtime();
        KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer = this.biometricCountDownTimer;
        if (keyguardBiometricsCountDownTimer != null) {
            keyguardBiometricsCountDownTimer.stop();
        }
        this.biometricCountDownTimer = null;
        SystemUITextView systemUITextView = this.biometricLockOutMessage;
        systemUITextView.setVisibility(8);
        Log.d("KeyguardBiometricView", "handleBiometricsAttemptLockout( elapsedRealtimeDeadline = " + j + " )");
        this.biometricCountDownTimer = new KeyguardBiometricsCountDownTimer(getContext(), jElapsedRealtime, 1000L, this.biometricLockOutMessage);
        systemUITextView.setVisibility(0);
        KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer2 = this.biometricCountDownTimer;
        if (keyguardBiometricsCountDownTimer2 != null) {
            keyguardBiometricsCountDownTimer2.start();
        }
    }

    public final void initLockStarLockIcon(boolean z) throws Resources.NotFoundException {
        SecLockIconView secLockIconView = this.lockIconView;
        secLockIconView.mIsLockStarEnabled = z;
        updateLockIconDrawable(false, true);
        if (z) {
            return;
        }
        secLockIconView.setAlpha(1.0f);
        this.lockIcon.updateImage();
    }

    public final boolean needsToChangeRetryButton() {
        return !(getResources().getConfiguration().orientation == 2) ? !(LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && this.keyguardUpdateMonitor.isFingerprintOptionEnabled() && getResources().getBoolean(R.bool.max_screen_zoom_lock_icon_policy)) : DeviceType.isTablet() && !getResources().getBoolean(R.bool.small_tablet_landscape_lock_icon_policy);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        ((KeyguardBiometricView) this.mView).bringToFront();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        this.displayLifecycle.addObserver(this.mDisplayLifeCycleObserver);
        this.biometricRetryContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$inflateRetryView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                view.getVisibility();
                KeyguardBiometricViewController.access$onClickRetryButton(this.this$0);
            }
        });
        this.biometricRetryContainer.setVisibility(8);
        this.lottieIcon.setVisibility(0);
        this.biometricRetryContainer.setBackground(getResources().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY) ? R.drawable.retry_container_ripple_whitebg_drawable : R.drawable.retry_container_ripple_drawable));
        this.biometricErrorText.setMaxFontScale(1.1f);
        this.biometricLockOutMessage.setMaxFontScale(1.0f);
        updateLayout$3();
        updateLockContainerMargin$1();
        this.lockIconView.setOnClickListener(new KeyguardBiometricViewController$setLockIconOnClickListener$1(this));
        SystemUIImageView systemUIImageView = this.lockIcon;
        systemUIImageView.setClickable(false);
        systemUIImageView.setAccessibilityDelegate(new KeyguardBiometricViewController$setLockIconOnClickListener$2());
        this.lockIconView.mIsOneHandModeEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isOneHandModeRunning();
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.settingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING));
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
            this.displayDeviceType = getContext().getResources().getConfiguration().semDisplayDeviceType;
        }
        PluginLockStarManager pluginLockStarManager = this.pluginLockStarManager;
        PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
        boolean zIsLockStarEnabled = pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false;
        this.isLockStarEnabled = zIsLockStarEnabled;
        this.lockIconView.mIsLockStarEnabled = zIsLockStarEnabled;
        pluginLockStarManager.registerCallback("KeyguardBiometricView", this.lockStarCallback);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer = this.biometricCountDownTimer;
        if (keyguardBiometricsCountDownTimer != null) {
            keyguardBiometricsCountDownTimer.stop();
        }
        this.biometricCountDownTimer = null;
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
        this.displayLifecycle.removeObserver(this.mDisplayLifeCycleObserver);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.settingsListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        }
        this.pluginLockStarManager.unregisterCallback("KeyguardBiometricView");
    }

    public final void resetBiometricLockOutTimer() {
        if (this.keyguardUpdateMonitor.getLockoutAttemptDeadline() != 0) {
            KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer = this.biometricCountDownTimer;
            if (keyguardBiometricsCountDownTimer != null) {
                keyguardBiometricsCountDownTimer.stop();
            }
            this.biometricCountDownTimer = null;
            this.biometricLockOutMessage.setVisibility(8);
        }
    }

    public final void resetLockIconState() {
        this.lottieIcon.setVisibility(4);
        this.lockIcon.setVisibility(0);
    }

    public final void startLockIconAnimation(boolean z) {
        ObjectAnimator objectAnimatorOfFloat;
        ((KeyguardBiometricView) this.mView).setAlpha(0.0f);
        if (z) {
            SpringForce springForce = new SpringForce(1.0f);
            springForce.setStiffness(200.0f);
            springForce.setDampingRatio(0.71f);
            SpringAnimation springAnimation = new SpringAnimation(this.mView, DynamicAnimation.SCALE_X);
            SpringAnimation springAnimation2 = new SpringAnimation(this.mView, DynamicAnimation.SCALE_Y);
            springAnimation.mSpring = springForce;
            springAnimation.mValue = 0.7f;
            springAnimation.mStartValueIsSet = true;
            springAnimation.start();
            springAnimation2.mSpring = springForce;
            springAnimation2.mValue = 0.7f;
            springAnimation2.mStartValueIsSet = true;
            springAnimation2.start();
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mView, (Property<T, Float>) View.ALPHA, 0.0f, 1.0f);
            KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.17f, 0.17f, 0.4f, 1.0f, objectAnimatorOfFloat);
            objectAnimatorOfFloat.setDuration(300L);
        } else {
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mView, (Property<T, Float>) View.ALPHA, 1.0f, 0.0f);
            KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.33f, 0.0f, 0.1f, 1.0f, objectAnimatorOfFloat);
            objectAnimatorOfFloat.setDuration(200L);
        }
        objectAnimatorOfFloat.start();
    }

    public final void updateBiometricViewLayout() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        KeyguardSecurityModel.SecurityMode currentSecurityMode = keyguardUpdateMonitor.getCurrentSecurityMode();
        if (LsRune.SECURITY_SWIPE_BOUNCER && currentSecurityMode == KeyguardSecurityModel.SecurityMode.Swipe) {
            ((KeyguardBiometricView) this.mView).setVisibility(8);
            return;
        }
        boolean z = currentSecurityMode == KeyguardSecurityModel.SecurityMode.Password;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
        ConstraintSet constraintSet = new ConstraintSet();
        int id = ((KeyguardBiometricView) this.mView).getId();
        boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        constraintSet.constrainWidth(id, (z2 && getContext().getResources().getConfiguration().semDisplayDeviceType == 0 && !DeviceState.isSmartViewFitToActiveDisplay()) ? SecurityUtils.getMainSecurityViewFlipperSize(getContext(), z) : 0);
        constraintSet.constrainHeight(((KeyguardBiometricView) this.mView).getId(), -2);
        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, 0);
        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, 0, 7);
        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 3, 0, 3);
        ((KeyguardBiometricView) this.mView).setPadding(0, 0, 0, 0);
        int rotation = DeviceState.getRotation(DeviceState.getRotation(((KeyguardBiometricView) this.mView).defaultDisplay.getRotation()));
        if (rotation == 1 || rotation == 3) {
            boolean zIsHiddenInputContainer = keyguardUpdateMonitor.isHiddenInputContainer();
            if (DeviceType.isTablet() || !DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || zIsHiddenInputContainer || (z2 && getContext().getResources().getConfiguration().semDisplayDeviceType == 0 && !DeviceState.isSmartViewFitToActiveDisplay())) {
                updateLockContainerMargin$1();
                constraintSet.applyTo((ConstraintLayout) ((KeyguardBiometricView) this.mView).getParent());
                return;
            }
            Rect bounds = getResources().getConfiguration().windowConfiguration.getBounds();
            int iWidth = DeviceState.shouldEnableKeyguardScreenRotation(getContext()) ? bounds.width() : Math.min(bounds.width(), bounds.height());
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
            int inDisplayFingerprintHeight = DeviceState.getInDisplayFingerprintHeight();
            boolean zIsInDisplayFingerprintMarginAccepted = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
            if (zIsInDisplayFingerprintMarginAccepted) {
                Configuration configuration = getResources().getConfiguration();
                int i = SecurityUtils.sPINContainerBottomMargin;
                boolean z3 = configuration.getLayoutDirection() == 1;
                dimensionPixelSize2 += ((z3 || rotation != 3) && !(z3 && rotation == 1)) ? 0 : inDisplayFingerprintHeight - dimensionPixelSize2;
            }
            constraintSet.constrainWidth(((KeyguardBiometricView) this.mView).getId(), SecurityUtils.calculateLandscapeViewWidth(iWidth, getContext()));
            constraintSet.constrainHeight(((KeyguardBiometricView) this.mView).getId(), -2);
            constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, -1, 7);
            constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, dimensionPixelSize2);
            ((KeyguardBiometricView) this.mView).setPadding(0, 0, dimensionPixelSize, 0);
            if (zIsInDisplayFingerprintMarginAccepted && z) {
                if (rotation == 1) {
                    ((KeyguardBiometricView) this.mView).setPadding(0, 0, dimensionPixelSize, 0);
                } else {
                    ((KeyguardBiometricView) this.mView).setPadding(dimensionPixelSize, 0, 0, 0);
                }
            }
        } else {
            constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, 0);
            constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, 0, 7);
            ((KeyguardBiometricView) this.mView).setPadding(0, 0, 0, 0);
        }
        constraintSet.applyTo((ConstraintLayout) ((KeyguardBiometricView) this.mView).getParent());
        updateLockContainerMargin$1();
    }

    public final void updateLayout$3() throws Resources.NotFoundException {
        if (this.bouncerShowing) {
            updateLockIcon();
            updateBiometricViewLayout();
            resetBiometricLockOutTimer();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
            long lockoutAttemptDeadline = keyguardUpdateMonitor.getLockoutAttemptDeadline();
            long lockoutBiometricAttemptDeadline = keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline();
            if (lockoutAttemptDeadline == 0 && lockoutBiometricAttemptDeadline != 0) {
                handleBiometricAttemptLockout(lockoutBiometricAttemptDeadline);
                return;
            }
            SystemUITextView systemUITextView = this.biometricLockOutMessage;
            systemUITextView.setText("");
            systemUITextView.setVisibility(8);
        }
    }

    public final void updateLockContainerMargin$1() {
        SecLockIconView secLockIconView = this.lockIconView;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) secLockIconView.getLayoutParams();
        layoutParams.topMargin = SecurityUtils.getLockIconTopMargin(getContext());
        secLockIconView.setLayoutParams(layoutParams);
    }

    public final void updateLockIcon() throws Resources.NotFoundException {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        boolean z = (!keyguardUpdateMonitor.isFaceOptionEnabled() || !keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(keyguardUpdateMonitor.getFaceStrongBiometric()) || this.isHiddenRetry || this.isRunning || this.isLockOut || keyguardUpdateMonitor.isFullscreenBouncer() || keyguardUpdateMonitor.getUserUnlockedWithBiometric(this.currentUserId) || keyguardUpdateMonitor.isKeyguardUnlocking()) ? false : true;
        if (keyguardUpdateMonitor.isForgotPasswordView()) {
            z = false;
        }
        this.mShowingRetryButton = z;
        if (keyguardUpdateMonitor.isRemoteLockEnabled() && !keyguardUpdateMonitor.isFMMLock()) {
            this.lockIconView.setVisibility(8);
            this.biometricRetryContainer.setVisibility(8);
        } else if (needsToChangeRetryButton()) {
            updateLockIconDrawable(z, false);
            this.biometricRetryContainer.setVisibility(8);
        } else {
            updateLockIconDrawable(false, false);
            this.biometricRetryContainer.setVisibility(z ? 0 : 8);
            this.biometricRetryIcon.setVisibility(z ? 0 : 8);
        }
        this.debugCount = 0;
    }

    public final void updateLockIconDrawable(boolean z, boolean z2) throws Resources.NotFoundException {
        int i = z ? R.drawable.ic_biometric_retry_button : R.drawable.lock_ic_lock_mtrl_00;
        int dimensionPixelSize = z ? getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_retry_icon_size) : getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_height);
        if (i != this.drawableResId || z2) {
            this.drawableResId = i;
            Drawable drawable = getResources().getDrawable(this.drawableResId);
            SystemUIImageView systemUIImageView = this.lockIcon;
            systemUIImageView.setImageDrawable(drawable);
            ViewGroup.LayoutParams layoutParams = systemUIImageView.getLayoutParams();
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = dimensionPixelSize;
            systemUIImageView.setLayoutParams(layoutParams);
            if (z) {
                return;
            }
            acceptLockStarModifier();
        }
    }

    public final void updateLockIconVisibility(boolean z) throws Resources.NotFoundException {
        boolean zIsLockStarEnabled = false;
        this.lockIconView.setVisibility((z && this.keyguardUpdateMonitor.isSecure()) ? 0 : 8);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            zIsLockStarEnabled = this.isLockStarEnabled;
        } else {
            PluginLockStar pluginLockStar = this.pluginLockStarManager.mPluginLockStar;
            if (pluginLockStar != null) {
                zIsLockStarEnabled = pluginLockStar.isLockStarEnabled();
            }
        }
        initLockStarLockIcon(zIsLockStarEnabled);
    }

    public final void updateVisibility$3() throws Resources.NotFoundException {
        clearView();
        boolean z = this.bouncerShowing;
        updateLockIconVisibility(z);
        if (!z) {
            resetLockIconState();
        }
        ((KeyguardBiometricView) this.mView).setVisibility(z ? 0 : 8);
    }
}
