package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.util.DisplayMetrics;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.keyguard.logging.KeyguardLogger$$ExternalSyntheticLambda0;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController.AuthRippleCommand;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.deviceentry.domain.interactor.AuthRippleInteractor;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import javax.inject.Provider;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

/* loaded from: classes.dex */
public final class AuthRippleController extends ViewController implements CoreStartable, KeyguardStateController.Callback, WakefulnessLifecycle.Observer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthController authController;
    public final AuthRippleController$authControllerCallback$1 authControllerCallback;
    public final BiometricUnlockController biometricUnlockController;
    public CircleReveal circleReveal;
    public final CommandRegistry commandRegistry;
    public final AuthRippleController$configurationChangedListener$1 configurationChangedListener;
    public final ConfigurationController configurationController;
    public final DisplayMetrics displayMetrics;
    public final FacePropertyRepository facePropertyRepository;
    public Point faceSensorLocation;
    public Point fingerprintSensorLocation;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final AuthRippleController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public final LightRevealScrim lightRevealScrim;
    public ValueAnimator lightRevealScrimAnimator;
    public final KeyguardLogger logger;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public boolean startLightRevealScrimOnKeyguardFadingAway;
    public final StatusBarStateController statusBarStateController;
    public final Context sysuiContext;
    public UdfpsController udfpsController;
    public final AuthRippleController$udfpsControllerCallback$1 udfpsControllerCallback;
    public final Provider udfpsControllerProvider;
    public float udfpsRadius;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    public final class AuthRippleCommand implements Command {
        public AuthRippleCommand() {
        }

        public static void invalidCommand(PrintWriter printWriter) {
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "invalid command", "Usage: adb shell cmd statusbar auth-ripple <command>", "Available commands:", "  dwell");
            printWriter.println("  fingerprint");
            printWriter.println("  face");
            printWriter.println("  custom <x-location: int> <y-location: int>");
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            if (list.isEmpty()) {
                invalidCommand(printWriter);
                return;
            }
            String str = (String) list.get(0);
            int iHashCode = str.hashCode();
            AuthRippleController authRippleController = AuthRippleController.this;
            switch (iHashCode) {
                case -1375934236:
                    if (str.equals("fingerprint")) {
                        printWriter.println("fingerprint ripple sensorLocation=" + authRippleController.fingerprintSensorLocation);
                        AuthRippleController.access$showUnlockRippleInternal(authRippleController, BiometricSourceType.FINGERPRINT);
                        return;
                    }
                    break;
                case -1349088399:
                    if (str.equals("custom")) {
                        if (list.size() != 3 || StringsKt__StringNumberConversionsJVMKt.toFloatOrNull((String) list.get(1)) == null || StringsKt__StringNumberConversionsJVMKt.toFloatOrNull((String) list.get(2)) == null) {
                            invalidCommand(printWriter);
                            return;
                        }
                        printWriter.println("custom ripple sensorLocation=" + list.get(1) + ", " + list.get(2));
                        ((AuthRippleView) ((ViewController) authRippleController).mView).setSensorLocation(new Point(Integer.parseInt((String) list.get(1)), Integer.parseInt((String) list.get(2))));
                        authRippleController.showUnlockedRipple();
                        return;
                    }
                    break;
                case 3135069:
                    if (str.equals("face")) {
                        printWriter.println("face ripple sensorLocation=" + authRippleController.faceSensorLocation);
                        AuthRippleController.access$showUnlockRippleInternal(authRippleController, BiometricSourceType.FACE);
                        return;
                    }
                    break;
                case 95997746:
                    if (str.equals("dwell")) {
                        AuthRippleController.access$showDwellRipple(authRippleController);
                        printWriter.println("lock screen dwell ripple: \n\tsensorLocation=" + authRippleController.fingerprintSensorLocation + "\n\tudfpsRadius=" + authRippleController.udfpsRadius);
                        return;
                    }
                    break;
            }
            invalidCommand(printWriter);
        }
    }

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

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1] */
    public AuthRippleController(Context context, AuthController authController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, WakefulnessLifecycle wakefulnessLifecycle, CommandRegistry commandRegistry, NotificationShadeWindowController notificationShadeWindowController, Provider provider, StatusBarStateController statusBarStateController, DisplayMetrics displayMetrics, KeyguardLogger keyguardLogger, BiometricUnlockController biometricUnlockController, LightRevealScrim lightRevealScrim, AuthRippleInteractor authRippleInteractor, FacePropertyRepository facePropertyRepository, AuthRippleView authRippleView) {
        super(authRippleView);
        this.sysuiContext = context;
        this.authController = authController;
        this.configurationController = configurationController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.commandRegistry = commandRegistry;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.udfpsControllerProvider = provider;
        this.statusBarStateController = statusBarStateController;
        this.displayMetrics = displayMetrics;
        this.logger = keyguardLogger;
        this.biometricUnlockController = biometricUnlockController;
        this.lightRevealScrim = lightRevealScrim;
        this.facePropertyRepository = facePropertyRepository;
        this.udfpsRadius = -1.0f;
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i) && i != 0) {
                    ((AuthRippleView) ((ViewController) this.this$0).mView).retractDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    ((AuthRippleView) ((ViewController) this.this$0).mView).retractDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    ((AuthRippleView) ((ViewController) this.this$0).mView).fadeDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerStateChanged(boolean z) {
                if (z) {
                    ((AuthRippleView) ((ViewController) this.this$0).mView).fadeDwellRipple();
                }
            }
        };
        this.configurationChangedListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                int i = AuthRippleController.$r8$clinit;
                this.this$0.updateRippleColor();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                int i = AuthRippleController.$r8$clinit;
                this.this$0.updateRippleColor();
            }
        };
        this.udfpsControllerCallback = new UdfpsController.Callback() { // from class: com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1
            @Override // com.android.systemui.biometrics.UdfpsController.Callback
            public final void onFingerDown() {
                AuthRippleController authRippleController = this.this$0;
                if (authRippleController.keyguardUpdateMonitor.isFingerprintDetectionRunning()) {
                    AuthRippleController.access$showDwellRipple(authRippleController);
                }
            }

            @Override // com.android.systemui.biometrics.UdfpsController.Callback
            public final void onFingerUp() {
                ((AuthRippleView) ((ViewController) this.this$0).mView).retractDwellRipple();
            }
        };
        this.authControllerCallback = new AuthController.Callback() { // from class: com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onAllAuthenticatorsRegistered(int i) {
                int i2 = AuthRippleController.$r8$clinit;
                this.this$0.updateUdfpsDependentParams();
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onUdfpsLocationChanged(UdfpsOverlayParams udfpsOverlayParams) {
                int i = AuthRippleController.$r8$clinit;
                this.this$0.updateUdfpsDependentParams();
            }
        };
    }

    public static final void access$showDwellRipple(AuthRippleController authRippleController) {
        authRippleController.fingerprintSensorLocation = authRippleController.authController.mFingerprintSensorLocation;
        authRippleController.faceSensorLocation = (Point) ((FacePropertyRepositoryImpl) authRippleController.facePropertyRepository).sensorLocation.$$delegate_0.getValue();
        Point point = authRippleController.fingerprintSensorLocation;
        if (point != null) {
            ((AuthRippleView) authRippleController.mView).setFingerprintSensorLocation(point, authRippleController.udfpsRadius);
            final AuthRippleView authRippleView = (AuthRippleView) authRippleController.mView;
            boolean zIsDozing = authRippleController.statusBarStateController.isDozing();
            Animator animator = authRippleView.unlockedRippleAnimator;
            if (animator == null || !animator.isRunning()) {
                Animator animator2 = authRippleView.dwellPulseOutAnimator;
                if (animator2 == null || !animator2.isRunning()) {
                    if (zIsDozing) {
                        authRippleView.dwellShader.setColor(-1);
                    } else {
                        authRippleView.dwellShader.setColor(authRippleView.lockScreenColorVal);
                    }
                    DwellRippleShader dwellRippleShader = authRippleView.dwellShader;
                    dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.color, 255));
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 0.8f);
                    valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
                    valueAnimatorOfFloat.setDuration(authRippleView.dwellPulseDuration);
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$dwellPulseOutRippleAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            long currentPlayTime = valueAnimator.getCurrentPlayTime();
                            authRippleView.dwellShader.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            authRippleView.dwellShader.setTime(currentPlayTime);
                            authRippleView.invalidate();
                        }
                    });
                    ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.8f, 1.0f);
                    valueAnimatorOfFloat2.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                    valueAnimatorOfFloat2.setDuration(authRippleView.dwellExpandDuration);
                    valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            long currentPlayTime = valueAnimator.getCurrentPlayTime();
                            authRippleView.dwellShader.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            authRippleView.dwellShader.setTime(currentPlayTime);
                            authRippleView.invalidate();
                        }
                    });
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playSequentially(valueAnimatorOfFloat, valueAnimatorOfFloat2);
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator3) {
                            AuthRippleView authRippleView2 = authRippleView;
                            authRippleView2.drawDwell = false;
                            authRippleView2.invalidate();
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator3) {
                            Animator animator4 = authRippleView.retractDwellAnimator;
                            if (animator4 != null) {
                                animator4.cancel();
                            }
                            Animator animator5 = authRippleView.fadeDwellAnimator;
                            if (animator5 != null) {
                                animator5.cancel();
                            }
                            authRippleView.setVisibility(0);
                            authRippleView.drawDwell = true;
                        }
                    });
                    animatorSet.start();
                    authRippleView.dwellPulseOutAnimator = animatorSet;
                }
            }
        }
    }

    public static final void access$showUnlockRippleInternal(AuthRippleController authRippleController, BiometricSourceType biometricSourceType) {
        boolean z = ((KeyguardStateControllerImpl) authRippleController.keyguardStateController).mShowing;
        boolean z2 = !z;
        boolean zIsUnlockingWithBiometricAllowed = authRippleController.keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(biometricSourceType);
        boolean z3 = !zIsUnlockingWithBiometricAllowed;
        KeyguardLogger keyguardLogger = authRippleController.logger;
        if (!z || !zIsUnlockingWithBiometricAllowed) {
            keyguardLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer = keyguardLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("AuthRippleController", logLevel, keyguardLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.bool1 = z2;
            logMessageImpl.bool2 = z3;
            logBuffer.commit(logMessageObtain);
            return;
        }
        authRippleController.fingerprintSensorLocation = authRippleController.authController.mFingerprintSensorLocation;
        Point point = (Point) ((FacePropertyRepositoryImpl) authRippleController.facePropertyRepository).sensorLocation.$$delegate_0.getValue();
        authRippleController.faceSensorLocation = point;
        if (biometricSourceType != BiometricSourceType.FINGERPRINT) {
            if (biometricSourceType != BiometricSourceType.FACE || point == null) {
                return;
            }
            ((AuthRippleView) authRippleController.mView).setSensorLocation(point);
            int i = point.x;
            int i2 = point.y;
            int iMax = Math.max(i, authRippleController.displayMetrics.widthPixels - i);
            int i3 = point.y;
            authRippleController.circleReveal = new CircleReveal(i, i2, 0, Math.max(iMax, Math.max(i3, authRippleController.displayMetrics.heightPixels - i3)));
            keyguardLogger.showingUnlockRippleAt(point.x, point.y, "Face unlock ripple");
            authRippleController.showUnlockedRipple();
            return;
        }
        Point point2 = authRippleController.fingerprintSensorLocation;
        if (point2 != null) {
            ((AuthRippleView) authRippleController.mView).setFingerprintSensorLocation(point2, authRippleController.udfpsRadius);
            int i4 = point2.x;
            int i5 = point2.y;
            int iMax2 = Math.max(i4, authRippleController.displayMetrics.widthPixels - i4);
            int i6 = point2.y;
            authRippleController.circleReveal = new CircleReveal(i4, i5, 0, Math.max(iMax2, Math.max(i6, authRippleController.displayMetrics.heightPixels - i6)));
            keyguardLogger.showingUnlockRippleAt(point2.x, point2.y, "FP sensor radius: " + authRippleController.udfpsRadius);
            authRippleController.showUnlockedRipple();
        }
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardFadingAwayChanged() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if (keyguardStateControllerImpl.mKeyguardFadingAway && this.startLightRevealScrimOnKeyguardFadingAway) {
            ValueAnimator valueAnimator = this.lightRevealScrimAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.1f, 1.0f);
            valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            valueAnimatorOfFloat.setDuration(800L);
            valueAnimatorOfFloat.setStartDelay(keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    AuthRippleController authRippleController = this.this$0;
                    if (Intrinsics.areEqual(authRippleController.lightRevealScrim.revealEffect, authRippleController.circleReveal)) {
                        this.this$0.lightRevealScrim.setRevealAmount(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                    } else {
                        valueAnimatorOfFloat.cancel();
                    }
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    AuthRippleController authRippleController = this.this$0;
                    if (Intrinsics.areEqual(authRippleController.lightRevealScrim.revealEffect, authRippleController.circleReveal)) {
                        this.this$0.lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
                    }
                    this.this$0.lightRevealScrimAnimator = null;
                }
            });
            valueAnimatorOfFloat.start();
            this.lightRevealScrimAnimator = valueAnimatorOfFloat;
            this.startLightRevealScrimOnKeyguardFadingAway = false;
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        this.startLightRevealScrimOnKeyguardFadingAway = false;
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        this.authController.addCallback(this.authControllerCallback);
        updateRippleColor();
        updateUdfpsDependentParams();
        UdfpsController udfpsController = this.udfpsController;
        if (udfpsController != null) {
            ((HashSet) udfpsController.mCallbacks).add(this.udfpsControllerCallback);
        }
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationChangedListener);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(this);
        this.wakefulnessLifecycle.addObserver(this);
        this.commandRegistry.registerCommand("auth-ripple", new Function0() { // from class: com.android.systemui.biometrics.AuthRippleController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = AuthRippleController.$r8$clinit;
                return this.f$0.new AuthRippleCommand();
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        UdfpsController udfpsController = this.udfpsController;
        if (udfpsController != null) {
            ((HashSet) udfpsController.mCallbacks).remove(this.udfpsControllerCallback);
        }
        this.authController.removeCallback(this.authControllerCallback);
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationChangedListener);
        ((KeyguardStateControllerImpl) this.keyguardStateController).removeCallback(this);
        this.wakefulnessLifecycle.removeObserver(this);
        CommandRegistry commandRegistry = this.commandRegistry;
        synchronized (commandRegistry) {
            commandRegistry.commandMap.remove("auth-ripple");
        }
        ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).setForcePluginOpen(this, false);
    }

    public final void showUnlockedRipple() {
        CircleReveal circleReveal;
        ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).setForcePluginOpen(this, true);
        if ((this.statusBarStateController.isDozing() || this.biometricUnlockController.isWakeAndUnlock()) && (circleReveal = this.circleReveal) != null) {
            LightRevealScrim lightRevealScrim = this.lightRevealScrim;
            lightRevealScrim.setRevealAmount(0.0f);
            lightRevealScrim.setRevealEffect(circleReveal);
            this.startLightRevealScrimOnKeyguardFadingAway = true;
        }
        final AuthRippleView authRippleView = (AuthRippleView) this.mView;
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.biometrics.AuthRippleController.showUnlockedRipple.2
            @Override // java.lang.Runnable
            public final void run() {
                AuthRippleController authRippleController = AuthRippleController.this;
                ((NotificationShadeWindowControllerImpl) authRippleController.notificationShadeWindowController).setForcePluginOpen(authRippleController, false);
            }
        };
        Animator animator = authRippleView.unlockedRippleAnimator;
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(800L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$rippleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                authRippleView.rippleShader.setRawProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                authRippleView.rippleShader.setFloatUniform("in_time", currentPlayTime);
                authRippleView.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                AuthRippleView authRippleView2 = authRippleView;
                authRippleView2.drawRipple = false;
                authRippleView2.setVisibility(8);
                authRippleView.unlockedRippleAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                AuthRippleView authRippleView2 = authRippleView;
                authRippleView2.drawRipple = true;
                authRippleView2.setVisibility(0);
            }
        });
        authRippleView.unlockedRippleAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.start();
    }

    public final void updateRippleColor() {
        AuthRippleView authRippleView = (AuthRippleView) this.mView;
        int colorAttrDefaultColor = com.android.settingslib.Utils.getColorAttrDefaultColor(this.sysuiContext, R.attr.wallpaperTextColorAccent, 0);
        authRippleView.lockScreenColorVal = colorAttrDefaultColor;
        authRippleView.rippleShader.setColorUniform("in_color", ColorUtils.setAlphaComponent(colorAttrDefaultColor, 62));
    }

    public final void updateUdfpsDependentParams() {
        UdfpsController udfpsController;
        Rect rect;
        AuthController authController = this.authController;
        List list = authController.mUdfpsProps;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.udfpsController = (UdfpsController) this.udfpsControllerProvider.get();
        this.udfpsRadius = (authController.mUdfpsController == null || (rect = authController.mUdfpsBounds) == null) ? -1.0f : rect.height() / 2.0f;
        if (!((AuthRippleView) this.mView).isAttachedToWindow() || (udfpsController = this.udfpsController) == null) {
            return;
        }
        ((HashSet) udfpsController.mCallbacks).add(this.udfpsControllerCallback);
    }

    public static /* synthetic */ void getStartLightRevealScrimOnKeyguardFadingAway$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
