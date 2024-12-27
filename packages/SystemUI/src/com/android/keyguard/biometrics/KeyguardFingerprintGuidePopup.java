package com.android.keyguard.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DeviceState;
import com.android.systemui.widget.SystemUITextView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class KeyguardFingerprintGuidePopup extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnimatorSet animatorSet;
    public boolean bouncerShowing;
    public int currentRotation;
    public final Display display;
    public final KeyguardFingerprintGuidePopup$displayLifeCycleObserver$1 displayLifeCycleObserver;
    public final DisplayLifecycle displayLifecycle;
    public SystemUITextView guideText;
    public final Handler handler;
    public String helpText;
    public final KeyguardFingerprintGuidePopup$hidePopupRunnable$1 hidePopupRunnable;
    public boolean isAnimating;
    public boolean isRunningState;
    public ViewGroup keyguardGuidePopup;
    public boolean keyguardShowing;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardFingerprintGuidePopup$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public final TelephonyManager telephonyManager;

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

    public KeyguardFingerprintGuidePopup(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public static final void access$reset(KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup) {
        AnimatorSet animatorSet;
        AnimatorSet animatorSet2;
        if ((keyguardFingerprintGuidePopup.isAnimating || ((animatorSet2 = keyguardFingerprintGuidePopup.animatorSet) != null && animatorSet2.isRunning())) && (animatorSet = keyguardFingerprintGuidePopup.animatorSet) != null) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        keyguardFingerprintGuidePopup.isAnimating = false;
    }

    public static final void access$updatePopupVisibility(KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup) {
        if (keyguardFingerprintGuidePopup.isRunningState && keyguardFingerprintGuidePopup.keyguardShowing) {
            if (keyguardFingerprintGuidePopup.keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() + keyguardFingerprintGuidePopup.keyguardUpdateMonitor.getLockoutAttemptDeadline() != 0 && DeviceState.getRotation(keyguardFingerprintGuidePopup.display.getRotation()) == 2 && keyguardFingerprintGuidePopup.keyguardUpdateMonitor.isFingerprintOptionEnabled()) {
                keyguardFingerprintGuidePopup.clearGuidePopup();
                keyguardFingerprintGuidePopup.setVisibility(0);
                return;
            }
        }
        if (keyguardFingerprintGuidePopup.getVisibility() == 0) {
            keyguardFingerprintGuidePopup.setVisibility(8);
        }
    }

    public final void clearGuidePopup() {
        SystemUITextView systemUITextView = this.guideText;
        if (systemUITextView != null) {
            systemUITextView.setVisibility(8);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.keyguardGuidePopup, "alpha", 1.0f, 0.0f);
            ofFloat.setDuration(100L);
            AnimatorSet animatorSet = new AnimatorSet();
            this.animatorSet = animatorSet;
            animatorSet.play(ofFloat);
            animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup$dismissAnimation$1$1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    KeyguardFingerprintGuidePopup.access$reset(KeyguardFingerprintGuidePopup.this);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    KeyguardFingerprintGuidePopup.access$reset(KeyguardFingerprintGuidePopup.this);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            animatorSet.start();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("KeyguardFingerprintGuidePopup", "onFinishInflate()");
        setVisibility(8);
        this.keyguardGuidePopup = (ViewGroup) findViewById(R.id.keyguard_fingerprint_guide_popup);
        SystemUITextView systemUITextView = (SystemUITextView) findViewById(R.id.keyguard_guide_text);
        this.guideText = systemUITextView;
        if (systemUITextView != null) {
            systemUITextView.setMaxFontScale(1.1f);
            systemUITextView.setMaxWidth((int) (DeviceState.getDisplayWidth(systemUITextView.getContext()) * 0.41f));
        }
    }

    public /* synthetic */ KeyguardFingerprintGuidePopup(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public KeyguardFingerprintGuidePopup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        Looper myLooper = Looper.myLooper();
        Intrinsics.checkNotNull(myLooper);
        this.handler = new Handler(myLooper);
        this.displayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        this.display = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getDisplay(0);
        this.hidePopupRunnable = new Runnable() { // from class: com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup$hidePopupRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                int i = KeyguardFingerprintGuidePopup.$r8$clinit;
                keyguardFingerprintGuidePopup.clearGuidePopup();
            }
        };
        this.telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.displayLifeCycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup$displayLifeCycleObserver$1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i) {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                int rotation = DeviceState.getRotation(keyguardFingerprintGuidePopup.display.getRotation());
                if (keyguardFingerprintGuidePopup.currentRotation != rotation) {
                    keyguardFingerprintGuidePopup.currentRotation = rotation;
                    KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                }
            }
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    KeyguardFingerprintGuidePopup.access$updatePopupVisibility(KeyguardFingerprintGuidePopup.this);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    int i2 = KeyguardFingerprintGuidePopup.$r8$clinit;
                    final KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                    if (DeviceState.getRotation(keyguardFingerprintGuidePopup.display.getRotation()) != 2 || keyguardFingerprintGuidePopup.isAnimating) {
                        return;
                    }
                    Log.d("KeyguardFingerprintGuidePopup", "onBiometricHelp(msgId : " + i + ", helpString : " + str);
                    SystemUITextView systemUITextView = keyguardFingerprintGuidePopup.guideText;
                    if (systemUITextView != null) {
                        keyguardFingerprintGuidePopup.helpText = str;
                        systemUITextView.setText(str);
                        systemUITextView.setVisibility(0);
                    }
                    int displayWidth = DeviceState.getDisplayWidth(keyguardFingerprintGuidePopup.getContext());
                    int inDisplayFingerprintImageSize = DeviceState.getInDisplayFingerprintImageSize();
                    int dimensionPixelSize = keyguardFingerprintGuidePopup.getResources().getDimensionPixelSize(R.dimen.kg_biometric_fingerprint_guide_popup_margin_left) + ((displayWidth + inDisplayFingerprintImageSize) / 2);
                    int dimensionPixelSize2 = keyguardFingerprintGuidePopup.getResources().getDimensionPixelSize(R.dimen.kg_biometric_fingerprint_guide_popup_margin_top) + (DeviceState.getInDisplayFingerprintHeight() - inDisplayFingerprintImageSize);
                    ViewGroup viewGroup = keyguardFingerprintGuidePopup.keyguardGuidePopup;
                    if (viewGroup != null) {
                        viewGroup.setTranslationX(dimensionPixelSize);
                        viewGroup.setTranslationY(dimensionPixelSize2);
                    }
                    if (keyguardFingerprintGuidePopup.handler.hasCallbacks(keyguardFingerprintGuidePopup.hidePopupRunnable)) {
                        keyguardFingerprintGuidePopup.handler.removeCallbacks(keyguardFingerprintGuidePopup.hidePopupRunnable);
                    }
                    if (!keyguardFingerprintGuidePopup.isAnimating || !Intrinsics.areEqual(keyguardFingerprintGuidePopup.helpText, "")) {
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(keyguardFingerprintGuidePopup.keyguardGuidePopup, "alpha", 0.0f, 1.0f);
                        ofFloat.setDuration(100L);
                        ofFloat.setInterpolator(new LinearInterpolator());
                        AnimatorSet animatorSet = new AnimatorSet();
                        keyguardFingerprintGuidePopup.animatorSet = animatorSet;
                        animatorSet.play(ofFloat);
                        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup$showMessage$1$1
                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                KeyguardFingerprintGuidePopup.this.isAnimating = true;
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationRepeat(Animator animator) {
                            }
                        });
                        animatorSet.start();
                    }
                    keyguardFingerprintGuidePopup.setVisibility(0);
                    keyguardFingerprintGuidePopup.handler.removeCallbacks(keyguardFingerprintGuidePopup.hidePopupRunnable);
                    keyguardFingerprintGuidePopup.handler.postDelayed(keyguardFingerprintGuidePopup.hidePopupRunnable, 3000L);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                if (z) {
                    if (keyguardFingerprintGuidePopup.getVisibility() == 0) {
                        keyguardFingerprintGuidePopup.clearGuidePopup();
                    }
                } else if (keyguardFingerprintGuidePopup.getVisibility() == 0) {
                    KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                    keyguardFingerprintGuidePopup.isRunningState = z;
                    KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                if (keyguardFingerprintGuidePopup.bouncerShowing != z) {
                    keyguardFingerprintGuidePopup.bouncerShowing = z;
                }
                if (keyguardFingerprintGuidePopup.getVisibility() == 0) {
                    keyguardFingerprintGuidePopup.clearGuidePopup();
                    if (z) {
                        return;
                    }
                    Log.d("KeyguardFingerprintGuidePopup", "set text preview - onKeyguardBouncerFullyShowingChanged");
                    KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                if (keyguardFingerprintGuidePopup.keyguardShowing != z) {
                    keyguardFingerprintGuidePopup.keyguardShowing = z;
                    if (z) {
                        KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                    } else {
                        keyguardFingerprintGuidePopup.clearGuidePopup();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                boolean isFingerprintOptionEnabled = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isFingerprintOptionEnabled();
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                if (!isFingerprintOptionEnabled) {
                    keyguardFingerprintGuidePopup.displayLifecycle.removeObserver(keyguardFingerprintGuidePopup.displayLifeCycleObserver);
                } else {
                    keyguardFingerprintGuidePopup.currentRotation = DeviceState.getRotation(keyguardFingerprintGuidePopup.display.getRotation());
                    keyguardFingerprintGuidePopup.displayLifecycle.addObserver(keyguardFingerprintGuidePopup.displayLifeCycleObserver);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                if (keyguardFingerprintGuidePopup.telephonyManager.semIsVideoCall()) {
                    if (i == 0) {
                        KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                    } else if (i == 1 || i == 2) {
                        keyguardFingerprintGuidePopup.clearGuidePopup();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                if (KeyguardUpdateMonitor.isSimPinSecure(i3)) {
                    KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                    if (keyguardFingerprintGuidePopup.getVisibility() == 0) {
                        keyguardFingerprintGuidePopup.clearGuidePopup();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                KeyguardFingerprintGuidePopup.access$updatePopupVisibility(KeyguardFingerprintGuidePopup.this);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSystemDialogsShowing() {
                KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = KeyguardFingerprintGuidePopup.this;
                if (keyguardFingerprintGuidePopup.getVisibility() == 0) {
                    KeyguardFingerprintGuidePopup.access$updatePopupVisibility(keyguardFingerprintGuidePopup);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustChanged(int i) {
                KeyguardFingerprintGuidePopup.access$updatePopupVisibility(KeyguardFingerprintGuidePopup.this);
            }
        };
    }
}
