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
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController.AuthRippleCommand;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthRippleController extends ViewController implements CoreStartable, KeyguardStateController.Callback, WakefulnessLifecycle.Observer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthController authController;
    public final AuthRippleController$authControllerCallback$1 authControllerCallback;
    public final AuthRippleController$biometricModeListener$1 biometricModeListener;
    public final BiometricUnlockController biometricUnlockController;
    public CircleReveal circleReveal;
    public final CommandRegistry commandRegistry;
    public final AuthRippleController$configurationChangedListener$1 configurationChangedListener;
    public final ConfigurationController configurationController;
    public final DisplayMetrics displayMetrics;
    public Point faceSensorLocation;
    public final FeatureFlags featureFlags;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AuthRippleCommand implements Command {
        public AuthRippleCommand() {
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            if (list.isEmpty()) {
                invalidCommand(printWriter);
                return;
            }
            String str = (String) list.get(0);
            int hashCode = str.hashCode();
            AuthRippleController authRippleController = AuthRippleController.this;
            switch (hashCode) {
                case -1375934236:
                    if (str.equals("fingerprint")) {
                        printWriter.println("fingerprint ripple sensorLocation=" + authRippleController.fingerprintSensorLocation);
                        authRippleController.showUnlockRipple(BiometricSourceType.FINGERPRINT);
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
                        int i = AuthRippleController.$r8$clinit;
                        ((AuthRippleView) authRippleController.mView).setSensorLocation(new Point(Integer.parseInt((String) list.get(1)), Integer.parseInt((String) list.get(2))));
                        authRippleController.showUnlockedRipple();
                        return;
                    }
                    break;
                case 3135069:
                    if (str.equals("face")) {
                        printWriter.println("face ripple sensorLocation=" + authRippleController.faceSensorLocation);
                        authRippleController.showUnlockRipple(BiometricSourceType.FACE);
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

        public final void invalidCommand(PrintWriter printWriter) {
            printWriter.println("invalid command");
            printWriter.println("Usage: adb shell cmd statusbar auth-ripple <command>");
            printWriter.println("Available commands:");
            printWriter.println("  dwell");
            printWriter.println("  fingerprint");
            printWriter.println("  face");
            printWriter.println("  custom <x-location: int> <y-location: int>");
        }
    }

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

    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.biometrics.AuthRippleController$biometricModeListener$1] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v21, types: [com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1] */
    public AuthRippleController(Context context, AuthController authController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, WakefulnessLifecycle wakefulnessLifecycle, CommandRegistry commandRegistry, NotificationShadeWindowController notificationShadeWindowController, Provider provider, StatusBarStateController statusBarStateController, DisplayMetrics displayMetrics, FeatureFlags featureFlags, KeyguardLogger keyguardLogger, BiometricUnlockController biometricUnlockController, LightRevealScrim lightRevealScrim, AuthRippleView authRippleView) {
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
        this.featureFlags = featureFlags;
        this.logger = keyguardLogger;
        this.biometricUnlockController = biometricUnlockController;
        this.lightRevealScrim = lightRevealScrim;
        this.udfpsRadius = -1.0f;
        this.biometricModeListener = new BiometricUnlockController.BiometricUnlockEventsListener(this) { // from class: com.android.systemui.biometrics.AuthRippleController$biometricModeListener$1
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i) && i != 0) {
                    int i2 = AuthRippleController.$r8$clinit;
                    ((AuthRippleView) AuthRippleController.this.mView).retractDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    int i = AuthRippleController.$r8$clinit;
                    ((AuthRippleView) AuthRippleController.this.mView).retractDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    int i2 = AuthRippleController.$r8$clinit;
                    ((AuthRippleView) AuthRippleController.this.mView).fadeDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerStateChanged(boolean z) {
                if (z) {
                    int i = AuthRippleController.$r8$clinit;
                    ((AuthRippleView) AuthRippleController.this.mView).fadeDwellRipple();
                }
            }
        };
        this.configurationChangedListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                int i = AuthRippleController.$r8$clinit;
                AuthRippleController.this.updateRippleColor();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                int i = AuthRippleController.$r8$clinit;
                AuthRippleController.this.updateRippleColor();
            }
        };
        this.udfpsControllerCallback = new UdfpsController.Callback() { // from class: com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1
            @Override // com.android.systemui.biometrics.UdfpsController.Callback
            public final void onFingerDown() {
                AuthRippleController.access$showDwellRipple(AuthRippleController.this);
            }

            @Override // com.android.systemui.biometrics.UdfpsController.Callback
            public final void onFingerUp() {
                int i = AuthRippleController.$r8$clinit;
                ((AuthRippleView) AuthRippleController.this.mView).retractDwellRipple();
            }
        };
        this.authControllerCallback = new AuthController.Callback() { // from class: com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onAllAuthenticatorsRegistered(int i) {
                int i2 = AuthRippleController.$r8$clinit;
                AuthRippleController.this.updateUdfpsDependentParams();
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onUdfpsLocationChanged(UdfpsOverlayParams udfpsOverlayParams) {
                int i = AuthRippleController.$r8$clinit;
                AuthRippleController.this.updateUdfpsDependentParams();
            }
        };
    }

    public static final void access$showDwellRipple(AuthRippleController authRippleController) {
        AuthController authController = authRippleController.authController;
        Point point = authController.mFingerprintSensorLocation;
        authRippleController.fingerprintSensorLocation = point;
        authRippleController.faceSensorLocation = authController.mFaceSensorLocation;
        if (point != null) {
            ((AuthRippleView) authRippleController.mView).setFingerprintSensorLocation(point, authRippleController.udfpsRadius);
            final AuthRippleView authRippleView = (AuthRippleView) authRippleController.mView;
            boolean isDozing = authRippleController.statusBarStateController.isDozing();
            Animator animator = authRippleView.unlockedRippleAnimator;
            if (animator != null && animator.isRunning()) {
                return;
            }
            Animator animator2 = authRippleView.dwellPulseOutAnimator;
            if (animator2 != null && animator2.isRunning()) {
                return;
            }
            if (isDozing) {
                authRippleView.dwellShader.setColor(-1);
            } else {
                authRippleView.dwellShader.setColor(authRippleView.lockScreenColorVal);
            }
            DwellRippleShader dwellRippleShader = authRippleView.dwellShader;
            dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.color, 255));
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 0.8f);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setDuration(authRippleView.dwellPulseDuration);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$dwellPulseOutRippleAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                    AuthRippleView.this.dwellShader.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    AuthRippleView.this.dwellShader.setTime(currentPlayTime);
                    AuthRippleView.this.invalidate();
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.8f, 1.0f);
            ofFloat2.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat2.setDuration(authRippleView.dwellExpandDuration);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                    AuthRippleView.this.dwellShader.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    AuthRippleView.this.dwellShader.setTime(currentPlayTime);
                    AuthRippleView.this.invalidate();
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(ofFloat, ofFloat2);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator3) {
                    AuthRippleView.this.drawDwell = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator3) {
                    Animator animator4 = AuthRippleView.this.retractDwellAnimator;
                    if (animator4 != null) {
                        animator4.cancel();
                    }
                    Animator animator5 = AuthRippleView.this.fadeDwellAnimator;
                    if (animator5 != null) {
                        animator5.cancel();
                    }
                    AuthRippleView.this.setVisibility(0);
                    AuthRippleView.this.drawDwell = true;
                }
            });
            animatorSet.start();
            authRippleView.dwellPulseOutAnimator = animatorSet;
        }
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardFadingAwayChanged() {
        Flags flags = Flags.INSTANCE;
        this.featureFlags.getClass();
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway && this.startLightRevealScrimOnKeyguardFadingAway) {
            ValueAnimator valueAnimator = this.lightRevealScrimAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.1f, 1.0f);
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat.setDuration(800L);
            ofFloat.setStartDelay(((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDelay);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    AuthRippleController authRippleController = AuthRippleController.this;
                    if (Intrinsics.areEqual(authRippleController.lightRevealScrim.revealEffect, authRippleController.circleReveal)) {
                        AuthRippleController.this.lightRevealScrim.setRevealAmount(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                    } else {
                        ofFloat.cancel();
                    }
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    AuthRippleController authRippleController = AuthRippleController.this;
                    if (Intrinsics.areEqual(authRippleController.lightRevealScrim.revealEffect, authRippleController.circleReveal)) {
                        AuthRippleController.this.lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
                    }
                    AuthRippleController.this.lightRevealScrimAnimator = null;
                }
            });
            ofFloat.start();
            this.lightRevealScrimAnimator = ofFloat;
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
        this.commandRegistry.registerCommand("auth-ripple", new Function0() { // from class: com.android.systemui.biometrics.AuthRippleController$onViewAttached$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AuthRippleController.this.new AuthRippleCommand();
            }
        });
        ((HashSet) this.biometricUnlockController.mBiometricUnlockEventsListeners).add(this.biometricModeListener);
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
        BiometricUnlockController biometricUnlockController = this.biometricUnlockController;
        ((HashSet) biometricUnlockController.mBiometricUnlockEventsListeners).remove(this.biometricModeListener);
        ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).setForcePluginOpen(this, false);
    }

    public final void showUnlockRipple(BiometricSourceType biometricSourceType) {
        boolean z = !((KeyguardStateControllerImpl) this.keyguardStateController).mShowing;
        boolean z2 = !this.keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(biometricSourceType);
        KeyguardLogger keyguardLogger = this.logger;
        if (z || z2) {
            keyguardLogger.notShowingUnlockRipple(z, z2);
            return;
        }
        AuthController authController = this.authController;
        Point point = authController.mFingerprintSensorLocation;
        this.fingerprintSensorLocation = point;
        Point point2 = authController.mFaceSensorLocation;
        this.faceSensorLocation = point2;
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
        DisplayMetrics displayMetrics = this.displayMetrics;
        if (biometricSourceType != biometricSourceType2) {
            if (biometricSourceType != BiometricSourceType.FACE || point2 == null) {
                return;
            }
            ((AuthRippleView) this.mView).setSensorLocation(point2);
            int i = point2.x;
            int i2 = point2.y;
            int max = Math.max(i, displayMetrics.widthPixels - i);
            int i3 = point2.y;
            this.circleReveal = new CircleReveal(i, i2, 0, Math.max(max, Math.max(i3, displayMetrics.heightPixels - i3)));
            keyguardLogger.showingUnlockRippleAt(point2.x, point2.y, "Face unlock ripple");
            showUnlockedRipple();
            return;
        }
        if (point != null) {
            ((AuthRippleView) this.mView).setFingerprintSensorLocation(point, this.udfpsRadius);
            int i4 = point.x;
            int i5 = point.y;
            int max2 = Math.max(i4, displayMetrics.widthPixels - i4);
            int i6 = point.y;
            this.circleReveal = new CircleReveal(i4, i5, 0, Math.max(max2, Math.max(i6, displayMetrics.heightPixels - i6)));
            keyguardLogger.showingUnlockRippleAt(point.x, point.y, "FP sensor radius: " + this.udfpsRadius);
            showUnlockedRipple();
        }
    }

    public final void showUnlockedRipple() {
        CircleReveal circleReveal;
        ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).setForcePluginOpen(this, true);
        Flags flags = Flags.INSTANCE;
        this.featureFlags.getClass();
        if ((this.statusBarStateController.isDozing() || this.biometricUnlockController.isWakeAndUnlock()) && (circleReveal = this.circleReveal) != null) {
            LightRevealScrim lightRevealScrim = this.lightRevealScrim;
            lightRevealScrim.setRevealAmount(0.0f);
            lightRevealScrim.setRevealEffect(circleReveal);
            this.startLightRevealScrimOnKeyguardFadingAway = true;
        }
        final AuthRippleView authRippleView = (AuthRippleView) this.mView;
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.biometrics.AuthRippleController$showUnlockedRipple$2
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(800L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$rippleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                AuthRippleView.this.rippleShader.setRawProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                AuthRippleView.this.rippleShader.setFloatUniform("in_time", currentPlayTime);
                AuthRippleView.this.invalidate();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                AuthRippleView authRippleView2 = AuthRippleView.this;
                authRippleView2.drawRipple = false;
                authRippleView2.setVisibility(8);
                AuthRippleView.this.unlockedRippleAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                AuthRippleView authRippleView2 = AuthRippleView.this;
                authRippleView2.drawRipple = true;
                authRippleView2.setVisibility(0);
            }
        });
        authRippleView.unlockedRippleAnimator = ofFloat;
        ofFloat.start();
    }

    public final void updateRippleColor() {
        AuthRippleView authRippleView = (AuthRippleView) this.mView;
        int colorAttrDefaultColor = com.android.settingslib.Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColorAccent, this.sysuiContext, 0);
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

    /* renamed from: getStartLightRevealScrimOnKeyguardFadingAway$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m104x6de90581() {
    }
}
