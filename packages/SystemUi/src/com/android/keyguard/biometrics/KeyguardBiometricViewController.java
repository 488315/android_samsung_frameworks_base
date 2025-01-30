package com.android.keyguard.biometrics;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.OneShotPreDrawListener;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecLockIconView;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.SecurityUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import com.sec.ims.configuration.DATA;
import java.util.function.IntConsumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBiometricViewController extends ViewController implements SystemUIWidgetCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public PluginLockStar.Modifier alphaModifier;
    public KeyguardBiometricsCountDownTimer biometricCountDownTimer;
    public final SystemUITextView biometricErrorText;
    public final SystemUITextView biometricLockOutMessage;
    public final FrameLayout biometricRetryContainer;
    public final SystemUIImageView biometricRetryIcon;
    public boolean bouncerShowing;
    public PluginLockStar.Modifier colorModifier;
    public final ConfigurationController configurationController;
    public final KeyguardBiometricViewController$configurationListener$1 configurationListener;
    public CountDownTimer countDownTimer;
    public int debugCount;
    public int displayDeviceType;
    public int drawableResId;
    public String errorString;
    public boolean isHiddenRetry;
    public boolean isLockOut;
    public boolean isLockStarEnabled;
    public boolean isRunning;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public final SystemUIImageView lockIcon;
    public PluginLockStar.Modifier lockIconDrawableModifier;
    public final SecLockIconView lockIconView;
    public final KeyguardBiometricViewController$lockStarCallback$1 lockStarCallback;
    public final PluginLockStarManager pluginLockStarManager;
    public final PowerManager powerManager;
    public final KeyguardBiometricViewController$rotationConsumer$1 rotationConsumer;
    public final SecRotationWatcher rotationWatcher;
    public final KeyguardBiometricViewController$settingsListener$1 settingsListener;
    public PluginLockStar.Modifier visibilityModifier;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v26, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$lockStarCallback$1] */
    /* JADX WARN: Type inference failed for: r1v27, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$settingsListener$1] */
    /* JADX WARN: Type inference failed for: r1v28, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$rotationConsumer$1] */
    /* JADX WARN: Type inference failed for: r1v29, types: [com.android.keyguard.biometrics.KeyguardBiometricViewController$configurationListener$1] */
    public KeyguardBiometricViewController(KeyguardBiometricView keyguardBiometricView, KeyguardUpdateMonitor keyguardUpdateMonitor, AccessibilityManager accessibilityManager, PowerManager powerManager, SecRotationWatcher secRotationWatcher, ConfigurationController configurationController, PluginLockStarManager pluginLockStarManager) {
        super(keyguardBiometricView);
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.accessibilityManager = accessibilityManager;
        this.powerManager = powerManager;
        this.rotationWatcher = secRotationWatcher;
        this.configurationController = configurationController;
        this.pluginLockStarManager = pluginLockStarManager;
        this.biometricRetryContainer = (FrameLayout) ((KeyguardBiometricView) this.mView).findViewById(R.id.keyguard_biometric_retry_container);
        this.biometricErrorText = (SystemUITextView) ((KeyguardBiometricView) this.mView).findViewById(R.id.keyguard_biometric_error_text);
        this.biometricRetryIcon = (SystemUIImageView) ((KeyguardBiometricView) this.mView).findViewById(R.id.keyguard_biometric_retry_icon);
        this.lockIcon = (SystemUIImageView) ((KeyguardBiometricView) this.mView).findViewById(R.id.bouncer_lock_icon);
        this.lockIconView = (SecLockIconView) ((KeyguardBiometricView) this.mView).findViewById(R.id.bouncer_lock_icon_view);
        this.biometricLockOutMessage = (SystemUITextView) ((KeyguardBiometricView) this.mView).findViewById(R.id.biometric_timeout_message);
        this.errorString = "";
        this.lockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$lockStarCallback$1
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarEnabled(boolean z) {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                if (z2) {
                    keyguardBiometricViewController.isLockStarEnabled = z;
                }
                int i = KeyguardBiometricViewController.$r8$clinit;
                keyguardBiometricViewController.initLockStarLockIcon(z);
            }
        };
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                int i = KeyguardBiometricViewController.$r8$clinit;
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                keyguardBiometricViewController.getClass();
                keyguardBiometricViewController.lockIconView.mIsOneHandModeEnabled = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isOneHandModeRunning();
                keyguardBiometricViewController.updateLockIcon();
                keyguardBiometricViewController.lockIconView.updateScanningFaceAnimation(keyguardBiometricViewController.lockIcon);
            }
        };
        this.rotationConsumer = new IntConsumer() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$rotationConsumer$1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                KeyguardBiometricViewController.this.updateBiometricViewLayout();
                KeyguardBiometricViewController.this.clearView();
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                keyguardBiometricViewController.updateLockIconVisibility(keyguardBiometricViewController.bouncerShowing);
                if (!(KeyguardBiometricViewController.this.errorString.length() > 0)) {
                    KeyguardBiometricViewController.this.updateLockIcon();
                } else {
                    KeyguardBiometricViewController keyguardBiometricViewController2 = KeyguardBiometricViewController.this;
                    KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController2, keyguardBiometricViewController2.errorString);
                }
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = configuration.semDisplayDeviceType;
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                    if (keyguardBiometricViewController.displayDeviceType != i) {
                        keyguardBiometricViewController.displayDeviceType = i;
                        PluginLockStar pluginLockStar = keyguardBiometricViewController.pluginLockStarManager.mPluginLockStar;
                        boolean isLockStarEnabled = pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false;
                        keyguardBiometricViewController.isLockStarEnabled = isLockStarEnabled;
                        keyguardBiometricViewController.initLockStarLockIcon(isLockStarEnabled);
                        keyguardBiometricViewController.updateBiometricViewLayout();
                        keyguardBiometricViewController.updateLayout();
                        keyguardBiometricViewController.updateVisibility();
                    }
                }
            }
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    int i = KeyguardBiometricViewController.$r8$clinit;
                    KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                    keyguardBiometricViewController.errorString = keyguardBiometricViewController.getContext().getString(R.string.kg_face_no_match);
                    KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController, keyguardBiometricViewController.errorString);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
                boolean z;
                if (biometricSourceType == BiometricSourceType.FACE) {
                    if (i == 10002 || i == 10003 || i == 10005) {
                        z = true;
                    } else if (i == 100001) {
                        return;
                    } else {
                        z = false;
                    }
                    KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                    keyguardBiometricViewController.isHiddenRetry = z;
                    if (str != null) {
                        keyguardBiometricViewController.errorString = str;
                    }
                    if (i == 3) {
                        keyguardBiometricViewController.errorString = "";
                    }
                    KeyguardBiometricViewController.access$updateErrorText(keyguardBiometricViewController, keyguardBiometricViewController.errorString);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                Log.d("KeyguardBiometricView", "onBiometricsLockoutChanged( " + z + " )");
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                long lockoutAttemptDeadline = keyguardBiometricViewController.keyguardUpdateMonitor.getLockoutAttemptDeadline();
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardBiometricViewController.keyguardUpdateMonitor;
                if (keyguardUpdateMonitor2.mDeviceInteractive && z && lockoutAttemptDeadline == 0) {
                    int failedBiometricUnlockAttempts = keyguardUpdateMonitor2.getFailedBiometricUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser());
                    if (failedBiometricUnlockAttempts == 0 || failedBiometricUnlockAttempts % 5 != 0) {
                        Log.d("KeyguardBiometricView", "onBiometricsLockoutChanged( mCountdownTimer is working. )");
                    } else {
                        keyguardBiometricViewController.handleBiometricAttemptLockout(keyguardUpdateMonitor2.getLockoutBiometricAttemptDeadline());
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                    if (keyguardBiometricViewController.isRunning != z) {
                        keyguardBiometricViewController.isRunning = z;
                        if (z) {
                            keyguardBiometricViewController.errorString = "";
                            keyguardBiometricViewController.clearView();
                        }
                        keyguardBiometricViewController.updateLockIcon();
                        keyguardBiometricViewController.lockIconView.updateScanningFaceAnimation(keyguardBiometricViewController.lockIcon);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDualDarInnerLockScreenStateChanged(boolean z) {
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                keyguardBiometricViewController.lockIcon.setImageDrawable(keyguardBiometricViewController.getContext().getDrawable(z ? R.drawable.lock_ic_lock_ddar : R.drawable.lock_ic_lock_mtrl_00));
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                if (keyguardBiometricViewController.bouncerShowing != z) {
                    keyguardBiometricViewController.bouncerShowing = z;
                    keyguardBiometricViewController.errorString = "";
                    long j = WallpaperEventNotifier.getInstance().mCurStatusFlag;
                    WallpaperEventNotifier.getInstance().getSemWallpaperColors(false);
                    keyguardBiometricViewController.getClass();
                    keyguardBiometricViewController.updateVisibility();
                    if (z) {
                        keyguardBiometricViewController.updateLayout();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                final KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                keyguardBiometricViewController.isLockOut = keyguardBiometricViewController.keyguardUpdateMonitor.isTimerRunning();
                if (keyguardBiometricViewController.isRunning || keyguardBiometricViewController.isLockOut) {
                    keyguardBiometricViewController.clearView();
                    keyguardBiometricViewController.updateLockIcon();
                    keyguardBiometricViewController.updateLockIconVisibility(keyguardBiometricViewController.bouncerShowing);
                    keyguardBiometricViewController.resetBiometricLockOutTimer();
                    long lockoutAttemptDeadline = keyguardBiometricViewController.keyguardUpdateMonitor.getLockoutAttemptDeadline();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    CountDownTimer countDownTimer = keyguardBiometricViewController.countDownTimer;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    keyguardBiometricViewController.countDownTimer = null;
                    keyguardBiometricViewController.countDownTimer = new CountDownTimer(lockoutAttemptDeadline - elapsedRealtime) { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$handleAttemptLockout$1
                        @Override // android.os.CountDownTimer
                        public final void onFinish() {
                            KeyguardBiometricViewController.this.updateBiometricViewLayout();
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
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                int i = keyguardBiometricViewController.keyguardUpdateMonitor.isRemoteLockEnabled() ? 8 : 0;
                keyguardBiometricViewController.lockIconView.setVisibility(i);
                keyguardBiometricViewController.biometricRetryIcon.setVisibility(i);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                int i = KeyguardBiometricViewController.$r8$clinit;
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                keyguardBiometricViewController.clearView();
                keyguardBiometricViewController.updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                int strongAuthForUser = keyguardBiometricViewController.keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i);
                if ((strongAuthForUser & 1) == 0 && (strongAuthForUser & 2) == 0 && (strongAuthForUser & 4) == 0 && (strongAuthForUser & 8) == 0 && (strongAuthForUser & 16) == 0 && (strongAuthForUser & 32) == 0) {
                    return;
                }
                keyguardBiometricViewController.clearView();
                keyguardBiometricViewController.updateLayout();
            }
        };
    }

    public static final void access$onClickRetryButton(KeyguardBiometricViewController keyguardBiometricViewController) {
        if (keyguardBiometricViewController.isHiddenRetry) {
            return;
        }
        Log.d("KeyguardBiometricView", "onClick - Retry icon");
        keyguardBiometricViewController.powerManager.userActivity(SystemClock.uptimeMillis(), true);
        KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardBiometricViewController.keyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isFaceOptionEnabled()) {
            keyguardUpdateMonitor.requestFaceAuth("Face auth triggered due to retry button click.");
            keyguardBiometricViewController.updateVisibility();
            SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "1013", "2");
        }
    }

    public static final void access$updateErrorText(final KeyguardBiometricViewController keyguardBiometricViewController, String str) {
        if (keyguardBiometricViewController.bouncerShowing && !keyguardBiometricViewController.isLockOut) {
            keyguardBiometricViewController.clearView();
            keyguardBiometricViewController.biometricRetryContainer.setVisibility(0);
            SystemUITextView systemUITextView = keyguardBiometricViewController.biometricErrorText;
            systemUITextView.setText(str);
            systemUITextView.setVisibility(0);
            OneShotPreDrawListener.add(systemUITextView, new Runnable() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$setErrorText$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardBiometricViewController keyguardBiometricViewController2 = KeyguardBiometricViewController.this;
                    SystemUITextView systemUITextView2 = keyguardBiometricViewController2.biometricErrorText;
                    boolean z = systemUITextView2.getText().length() == 0;
                    SystemUITextView systemUITextView3 = keyguardBiometricViewController2.biometricErrorText;
                    if (z) {
                        systemUITextView3.setAlpha(0.0f);
                        systemUITextView3.setScaleX(0.7f);
                        systemUITextView3.setScaleY(0.7f);
                        return;
                    }
                    if (keyguardBiometricViewController2.isLockOut) {
                        return;
                    }
                    systemUITextView3.setAlpha(0.0f);
                    systemUITextView3.setScaleX(0.7f);
                    systemUITextView3.setScaleY(0.7f);
                    SpringForce springForce = new SpringForce(1.0f);
                    springForce.setStiffness(350.0f);
                    springForce.setDampingRatio(0.78f);
                    DynamicAnimation.C01934 c01934 = DynamicAnimation.SCALE_X;
                    SpringAnimation springAnimation = new SpringAnimation(systemUITextView3, c01934);
                    springAnimation.mSpring = springForce;
                    springAnimation.start();
                    DynamicAnimation.C01945 c01945 = DynamicAnimation.SCALE_Y;
                    SpringAnimation springAnimation2 = new SpringAnimation(systemUITextView3, c01945);
                    springAnimation2.mSpring = springForce;
                    springAnimation2.start();
                    SpringAnimation springAnimation3 = new SpringAnimation(systemUITextView3, DynamicAnimation.ALPHA);
                    springAnimation3.mSpring = springForce;
                    springAnimation3.start();
                    float x = systemUITextView2.getX();
                    SecLockIconView secLockIconView = keyguardBiometricViewController2.lockIconView;
                    SystemUIImageView systemUIImageView = keyguardBiometricViewController2.lockIcon;
                    if (systemUIImageView == null) {
                        secLockIconView.getClass();
                        return;
                    }
                    secLockIconView.initBiometricErrorIndicationAnimationValue(systemUIImageView, false);
                    int x2 = (int) ((systemUIImageView.getX() + secLockIconView.getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_text_margin)) - x);
                    int displayWidth = (DeviceState.getDisplayWidth(secLockIconView.getContext()) - secLockIconView.getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_min_height)) / 2;
                    if (x2 > displayWidth) {
                        x2 = displayWidth;
                    }
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(systemUIImageView, (Property<SystemUIImageView, Float>) View.TRANSLATION_X, x2 * (-1));
                    secLockIconView.mAnimTranslationX = ofFloat;
                    SecLockIconView$$ExternalSyntheticOutline0.m82m(0.4f, 0.5f, 0.0f, 1.0f, ofFloat);
                    secLockIconView.mAnimTranslationX.setDuration(400L);
                    secLockIconView.mAnimTranslationX.start();
                    SpringForce springForce2 = new SpringForce(0.72f);
                    springForce2.setStiffness(150.0f);
                    springForce2.setDampingRatio(0.48f);
                    SpringAnimation springAnimation4 = new SpringAnimation(systemUIImageView, c01934);
                    springAnimation4.mSpring = springForce2;
                    secLockIconView.mScaleXAnim = springAnimation4;
                    SpringAnimation springAnimation5 = new SpringAnimation(systemUIImageView, c01945);
                    springAnimation5.mSpring = springForce2;
                    secLockIconView.mScaleYAnim = springAnimation5;
                    secLockIconView.mScaleXAnim.start();
                    secLockIconView.mScaleYAnim.start();
                }
            });
            keyguardBiometricViewController.biometricRetryIcon.setVisibility((!keyguardBiometricViewController.bouncerShowing || keyguardBiometricViewController.isHiddenRetry) ? 8 : 0);
            keyguardBiometricViewController.updateLockIcon();
        }
        if (((KeyguardBiometricView) keyguardBiometricViewController.mView).getVisibility() == 0 && keyguardBiometricViewController.accessibilityManager.isEnabled()) {
            ((KeyguardBiometricView) keyguardBiometricViewController.mView).announceForAccessibility(str);
        }
    }

    public final void clearView() {
        SystemUITextView systemUITextView = this.biometricErrorText;
        systemUITextView.setText("");
        systemUITextView.setVisibility(8);
        this.biometricRetryIcon.setVisibility(8);
        this.biometricRetryContainer.setVisibility(8);
        this.lockIconView.initBiometricErrorIndicationAnimationValue(this.lockIcon, true);
    }

    public final void handleBiometricAttemptLockout(long j) {
        long elapsedRealtime = j - SystemClock.elapsedRealtime();
        KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer = this.biometricCountDownTimer;
        if (keyguardBiometricsCountDownTimer != null) {
            keyguardBiometricsCountDownTimer.stop();
        }
        this.biometricCountDownTimer = null;
        SystemUITextView systemUITextView = this.biometricLockOutMessage;
        systemUITextView.setVisibility(8);
        Log.d("KeyguardBiometricView", "handleBiometricsAttemptLockout( elapsedRealtimeDeadline = " + j + " )");
        this.biometricCountDownTimer = new KeyguardBiometricsCountDownTimer(getContext(), elapsedRealtime, 1000L, this.biometricLockOutMessage);
        systemUITextView.setVisibility(0);
        KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer2 = this.biometricCountDownTimer;
        if (keyguardBiometricsCountDownTimer2 != null) {
            keyguardBiometricsCountDownTimer2.start();
        }
    }

    public final void initLockStarLockIcon(boolean z) {
        SecLockIconView secLockIconView = this.lockIconView;
        secLockIconView.mIsLockStarEnabled = z;
        updateLockIconDrawable(false, true);
        if (z) {
            return;
        }
        secLockIconView.setAlpha(1.0f);
        this.lockIcon.updateImage();
    }

    public final boolean isLandscape() {
        int rotation = DeviceState.getRotation(DeviceState.getRotation(((KeyguardBiometricView) this.mView).defaultDisplay.getRotation()));
        return rotation == 1 || rotation == 3;
    }

    public final boolean needsToChangeRetryButton() {
        if (this.biometricRetryIcon.getVisibility() != 0) {
            return false;
        }
        if (isLandscape()) {
            if (DeviceType.isTablet() && !getResources().getBoolean(R.bool.small_tablet_landscape_lock_icon_policy)) {
                return false;
            }
        } else if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !this.keyguardUpdateMonitor.isFingerprintOptionEnabled() || !getResources().getBoolean(R.bool.max_screen_zoom_lock_icon_policy)) {
            return false;
        }
        return true;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        ((KeyguardBiometricView) this.mView).bringToFront();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$inflateRetryView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                view.getVisibility();
                KeyguardBiometricViewController.access$onClickRetryButton(KeyguardBiometricViewController.this);
            }
        };
        FrameLayout frameLayout = this.biometricRetryContainer;
        frameLayout.setOnClickListener(onClickListener);
        frameLayout.setVisibility(8);
        frameLayout.setBackground(getResources().getDrawable(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.retry_container_ripple_whitebg_drawable : R.drawable.retry_container_ripple_drawable));
        this.biometricErrorText.setMaxFontScale(1.1f);
        this.biometricLockOutMessage.setMaxFontScale(1.0f);
        updateLayout();
        updateLockContainerMargin();
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$setLockIconOnClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardBiometricViewController keyguardBiometricViewController = KeyguardBiometricViewController.this;
                int i = KeyguardBiometricViewController.$r8$clinit;
                if (keyguardBiometricViewController.needsToChangeRetryButton()) {
                    KeyguardBiometricViewController.access$onClickRetryButton(KeyguardBiometricViewController.this);
                }
                if (KeyguardBiometricViewController.this.isLandscape() && KeyguardBiometricViewController.this.keyguardUpdateMonitor.isFaceOptionEnabled()) {
                    KeyguardBiometricViewController.this.updateLockIcon();
                }
                KeyguardBiometricViewController keyguardBiometricViewController2 = KeyguardBiometricViewController.this;
                int i2 = keyguardBiometricViewController2.debugCount + 1;
                keyguardBiometricViewController2.debugCount = i2;
                if (i2 == 10) {
                    keyguardBiometricViewController2.keyguardUpdateMonitor.enableSecurityDebug();
                    KeyguardBiometricViewController.this.debugCount = 0;
                }
            }
        };
        SecLockIconView secLockIconView = this.lockIconView;
        secLockIconView.setOnClickListener(onClickListener2);
        SystemUIImageView systemUIImageView = this.lockIcon;
        systemUIImageView.setClickable(false);
        systemUIImageView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.keyguard.biometrics.KeyguardBiometricViewController$setLockIconOnClickListener$2
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (accessibilityNodeInfo != null) {
                    accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                }
            }
        });
        secLockIconView.mIsOneHandModeEnabled = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isOneHandModeRunning();
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this.settingsListener, Settings.System.getUriFor("any_screen_running"));
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
            this.displayDeviceType = getContext().getResources().getConfiguration().semDisplayDeviceType;
        }
        PluginLockStarManager pluginLockStarManager = this.pluginLockStarManager;
        PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
        boolean isLockStarEnabled = pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false;
        this.isLockStarEnabled = isLockStarEnabled;
        secLockIconView.mIsLockStarEnabled = isLockStarEnabled;
        pluginLockStarManager.registerCallback("KeyguardBiometricView", this.lockStarCallback);
        this.rotationWatcher.addCallback(this.rotationConsumer);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        KeyguardBiometricsCountDownTimer keyguardBiometricsCountDownTimer = this.biometricCountDownTimer;
        if (keyguardBiometricsCountDownTimer != null) {
            keyguardBiometricsCountDownTimer.stop();
        }
        this.biometricCountDownTimer = null;
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(this.settingsListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        }
        this.pluginLockStarManager.unregisterCallback("KeyguardBiometricView");
        this.rotationWatcher.removeCallback(this.rotationConsumer);
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

    public final void startLockIconAnimation(boolean z) {
        ObjectAnimator ofFloat;
        ((KeyguardBiometricView) this.mView).setAlpha(0.0f);
        if (z) {
            ofFloat = ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f);
            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.22f, 0.25f, 0.0f, 1.0f, ofFloat);
            ofFloat.setDuration(350L);
        } else {
            ofFloat = ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) View.ALPHA, 1.0f, 0.0f);
            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.1f, 1.0f, ofFloat);
            ofFloat.setDuration(200L);
        }
        ofFloat.start();
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x010d, code lost:
    
        if (com.android.systemui.util.DeviceState.isSmartViewFitToActiveDisplay() == false) goto L66;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x012e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBiometricViewLayout() {
        int i;
        int rotation;
        boolean isInDisplayFingerprintMarginAccepted;
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
        if (z2) {
            if ((getContext().getResources().getConfiguration().semDisplayDeviceType == 0) && !DeviceState.isSmartViewFitToActiveDisplay()) {
                i = SecurityUtils.getMainSecurityViewFlipperSize(getContext(), z);
                constraintSet.constrainWidth(id, i);
                constraintSet.constrainHeight(((KeyguardBiometricView) this.mView).getId(), -2);
                constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, 0);
                constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, 0, 7);
                constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 3, 0, 3);
                ((KeyguardBiometricView) this.mView).setPadding(0, 0, 0, 0);
                rotation = DeviceState.getRotation(DeviceState.getRotation(((KeyguardBiometricView) this.mView).defaultDisplay.getRotation()));
                if (rotation != 1 || rotation == 3) {
                    boolean isHiddenInputContainer = keyguardUpdateMonitor.isHiddenInputContainer();
                    if (!DeviceType.isTablet() && DeviceState.shouldEnableKeyguardScreenRotation(getContext()) && !isHiddenInputContainer) {
                        if (z2) {
                            if (getContext().getResources().getConfiguration().semDisplayDeviceType == 0) {
                            }
                        }
                        Rect bounds = getResources().getConfiguration().windowConfiguration.getBounds();
                        int width = !DeviceState.shouldEnableKeyguardScreenRotation(getContext()) ? bounds.width() : Math.min(bounds.width(), bounds.height());
                        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                        int i2 = DeviceState.sInDisplayFingerprintHeight;
                        isInDisplayFingerprintMarginAccepted = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
                        if (isInDisplayFingerprintMarginAccepted) {
                            boolean z3 = getResources().getConfiguration().getLayoutDirection() == 1;
                            dimensionPixelSize2 += ((z3 || rotation != 3) && !(z3 && rotation == 1)) ? 0 : i2 - dimensionPixelSize2;
                        }
                        constraintSet.constrainWidth(((KeyguardBiometricView) this.mView).getId(), SecurityUtils.calculateLandscapeViewWidth(width, getContext()));
                        constraintSet.constrainHeight(((KeyguardBiometricView) this.mView).getId(), -2);
                        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, -1, 7);
                        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, dimensionPixelSize2);
                        ((KeyguardBiometricView) this.mView).setPadding(0, 0, dimensionPixelSize, 0);
                        if (isInDisplayFingerprintMarginAccepted && z) {
                            if (rotation != 1) {
                                ((KeyguardBiometricView) this.mView).setPadding(0, 0, dimensionPixelSize, 0);
                            } else {
                                ((KeyguardBiometricView) this.mView).setPadding(dimensionPixelSize, 0, 0, 0);
                            }
                        }
                    }
                    constraintSet.applyTo((ConstraintLayout) ((KeyguardBiometricView) this.mView).getParent());
                }
                constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, 0);
                constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, 0, 7);
                ((KeyguardBiometricView) this.mView).setPadding(0, 0, 0, 0);
                constraintSet.applyTo((ConstraintLayout) ((KeyguardBiometricView) this.mView).getParent());
                updateLockContainerMargin();
                return;
            }
        }
        i = 0;
        constraintSet.constrainWidth(id, i);
        constraintSet.constrainHeight(((KeyguardBiometricView) this.mView).getId(), -2);
        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, 0);
        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, 0, 7);
        constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 3, 0, 3);
        ((KeyguardBiometricView) this.mView).setPadding(0, 0, 0, 0);
        rotation = DeviceState.getRotation(DeviceState.getRotation(((KeyguardBiometricView) this.mView).defaultDisplay.getRotation()));
        if (rotation != 1) {
        }
        boolean isHiddenInputContainer2 = keyguardUpdateMonitor.isHiddenInputContainer();
        if (!DeviceType.isTablet()) {
            if (z2) {
            }
            Rect bounds2 = getResources().getConfiguration().windowConfiguration.getBounds();
            if (!DeviceState.shouldEnableKeyguardScreenRotation(getContext())) {
            }
            int dimensionPixelSize22 = getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
            int i22 = DeviceState.sInDisplayFingerprintHeight;
            isInDisplayFingerprintMarginAccepted = keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted();
            if (isInDisplayFingerprintMarginAccepted) {
            }
            constraintSet.constrainWidth(((KeyguardBiometricView) this.mView).getId(), SecurityUtils.calculateLandscapeViewWidth(width, getContext()));
            constraintSet.constrainHeight(((KeyguardBiometricView) this.mView).getId(), -2);
            constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 7, -1, 7);
            constraintSet.connect(((KeyguardBiometricView) this.mView).getId(), 6, 0, 6, dimensionPixelSize22);
            ((KeyguardBiometricView) this.mView).setPadding(0, 0, dimensionPixelSize, 0);
            if (isInDisplayFingerprintMarginAccepted) {
                if (rotation != 1) {
                }
            }
            constraintSet.applyTo((ConstraintLayout) ((KeyguardBiometricView) this.mView).getParent());
            updateLockContainerMargin();
            return;
        }
        constraintSet.applyTo((ConstraintLayout) ((KeyguardBiometricView) this.mView).getParent());
    }

    public final void updateLayout() {
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0048, code lost:
    
        if (r5 != 3) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLockContainerMargin() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin_tablet);
        SecLockIconView secLockIconView = this.lockIconView;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) secLockIconView.getLayoutParams();
        if (LsRune.SECURITY_BIOMETRICS_TABLET) {
            int rotation = DeviceState.getRotation(DeviceState.getRotation(((KeyguardBiometricView) this.mView).defaultDisplay.getRotation()));
            if (rotation != 0) {
                if (rotation != 1) {
                    if (rotation == 2) {
                        if (this.keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted()) {
                            dimensionPixelSize = DeviceState.sInDisplayFingerprintHeight;
                        }
                        layoutParams.topMargin = dimensionPixelSize;
                    }
                }
                if (dimensionPixelSize3 != 0) {
                    dimensionPixelSize = dimensionPixelSize3;
                }
                layoutParams.topMargin = dimensionPixelSize;
            } else {
                layoutParams.topMargin = dimensionPixelSize + dimensionPixelSize2;
            }
        } else {
            layoutParams.topMargin = dimensionPixelSize;
            if (isLandscape()) {
                dimensionPixelSize2 = 0;
            }
            layoutParams.topMargin = dimensionPixelSize + dimensionPixelSize2;
        }
        secLockIconView.setLayoutParams(layoutParams);
    }

    public final void updateLockIcon() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        boolean z = (!keyguardUpdateMonitor.isFaceOptionEnabled() || !keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(keyguardUpdateMonitor.getFaceStrongBiometric()) || this.isHiddenRetry || this.isRunning || this.isLockOut || keyguardUpdateMonitor.isFullscreenBouncer()) ? false : true;
        boolean isRemoteLockEnabled = keyguardUpdateMonitor.isRemoteLockEnabled();
        FrameLayout frameLayout = this.biometricRetryContainer;
        if (isRemoteLockEnabled && !keyguardUpdateMonitor.isFMMLock()) {
            this.lockIconView.setVisibility(8);
            frameLayout.setVisibility(8);
        } else if (needsToChangeRetryButton()) {
            updateLockIconDrawable(z, false);
            frameLayout.setVisibility(8);
        } else {
            updateLockIconDrawable(false, false);
            frameLayout.setVisibility(z ? 0 : 8);
        }
        this.debugCount = 0;
    }

    public final void updateLockIconDrawable(boolean z, boolean z2) {
        boolean isLockStarEnabled;
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
            boolean z3 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
            PluginLockStarManager pluginLockStarManager = this.pluginLockStarManager;
            if (z3) {
                isLockStarEnabled = this.isLockStarEnabled;
            } else {
                PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
                isLockStarEnabled = pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false;
            }
            if (isLockStarEnabled) {
                PluginLockStar pluginLockStar2 = pluginLockStarManager.mPluginLockStar;
                this.visibilityModifier = pluginLockStar2 != null ? pluginLockStar2.getModifier("lockIconVisibility") : null;
                this.alphaModifier = pluginLockStar2 != null ? pluginLockStar2.getModifier("lockIconAlpha") : null;
                this.colorModifier = pluginLockStar2 != null ? pluginLockStar2.getModifier("lockIconColor") : null;
                this.lockIconDrawableModifier = pluginLockStar2 != null ? pluginLockStar2.getModifier("lockIconDrawable") : null;
                PluginLockStar.Modifier modifier = this.visibilityModifier;
                if (modifier != null) {
                    modifier.accept(this.lockIconView);
                }
                PluginLockStar.Modifier modifier2 = this.alphaModifier;
                if (modifier2 != null) {
                    modifier2.accept(systemUIImageView);
                }
                PluginLockStar.Modifier modifier3 = this.colorModifier;
                if (modifier3 != null) {
                    modifier3.accept(systemUIImageView);
                }
                PluginLockStar.Modifier modifier4 = this.lockIconDrawableModifier;
                if (modifier4 != null) {
                    modifier4.accept(systemUIImageView);
                }
            }
        }
    }

    public final void updateLockIconVisibility(boolean z) {
        boolean isLockStarEnabled;
        this.lockIconView.setVisibility(z ? 0 : 8);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            isLockStarEnabled = this.isLockStarEnabled;
        } else {
            PluginLockStar pluginLockStar = this.pluginLockStarManager.mPluginLockStar;
            isLockStarEnabled = pluginLockStar != null ? pluginLockStar.isLockStarEnabled() : false;
        }
        initLockStarLockIcon(isLockStarEnabled);
    }

    public final void updateVisibility() {
        clearView();
        boolean z = this.bouncerShowing;
        updateLockIconVisibility(z);
        ((KeyguardBiometricView) this.mView).setVisibility(z ? 0 : 8);
    }
}
