package com.android.systemui.biometrics;

import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class UdfpsShell implements Command {
    public UdfpsController.UdfpsOverlayController udfpsOverlayController;

    public UdfpsShell(CommandRegistry commandRegistry) {
        commandRegistry.registerCommand("udfps", new Function0() { // from class: com.android.systemui.biometrics.UdfpsShell.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UdfpsShell.this;
            }
        });
    }

    public static MotionEvent obtainMotionEvent(float f, float f2, int i) {
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = 1;
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = f;
        pointerCoords.y = f2;
        pointerCoords.touchMinor = 10.0f;
        pointerCoords.touchMajor = 10.0f;
        return MotionEvent.obtain(0L, 0L, i, 1, new MotionEvent.PointerProperties[]{pointerProperties}, new MotionEvent.PointerCoords[]{pointerCoords}, 0, 0, 1.0f, 1.0f, 0, 0, 0, 0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.systemui.statusbar.commandline.Command
    public final void execute(PrintWriter printWriter, List list) {
        int i;
        int i2 = 0;
        if (list.size() == 1 && Intrinsics.areEqual(list.get(0), "hide")) {
            UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
            if (udfpsOverlayController != null) {
                udfpsOverlayController.hideUdfpsOverlay(0);
                return;
            }
            return;
        }
        if (list.size() == 2 && Intrinsics.areEqual(list.get(0), "show")) {
            String str = (String) list.get(1);
            switch (str.hashCode()) {
                case -945543637:
                    if (str.equals("auth-keyguard")) {
                        i2 = 4;
                    }
                    i = i2;
                    break;
                case -943067225:
                    if (str.equals("enroll-find-sensor")) {
                        i = 1;
                        break;
                    }
                    i = i2;
                    break;
                case -646572397:
                    if (str.equals("auth-bp")) {
                        i2 = 3;
                    }
                    i = i2;
                    break;
                case -19448152:
                    if (str.equals("auth-settings")) {
                        i2 = 6;
                    }
                    i = i2;
                    break;
                case 244570389:
                    if (str.equals("enroll-enrolling")) {
                        i = 2;
                        break;
                    }
                    i = i2;
                    break;
                case 902271659:
                    if (str.equals("auth-other")) {
                        i2 = 5;
                    }
                    i = i2;
                    break;
                default:
                    i = i2;
                    break;
            }
            UdfpsController.UdfpsOverlayController udfpsOverlayController2 = this.udfpsOverlayController;
            if (udfpsOverlayController2 != null) {
                udfpsOverlayController2.showUdfpsOverlay(2L, 0, i, new IUdfpsOverlayControllerCallback.Stub() { // from class: com.android.systemui.biometrics.UdfpsShell$showOverlay$1
                    public final void onUserCanceled() {
                        Log.e("UdfpsShell", "User cancelled");
                    }
                });
                return;
            }
            return;
        }
        if (list.size() == 1 && Intrinsics.areEqual(list.get(0), "onUiReady")) {
            onUiReady();
            return;
        }
        if (list.size() == 1 && Intrinsics.areEqual(list.get(0), "simFingerDown")) {
            simFingerDown();
            return;
        }
        if (list.size() == 1 && Intrinsics.areEqual(list.get(0), "simFingerUp")) {
            simFingerUp();
            return;
        }
        if (list.size() == 1 && Intrinsics.areEqual(list.get(0), "biometricPrompt")) {
            UdfpsController.UdfpsOverlayController udfpsOverlayController3 = this.udfpsOverlayController;
            if (udfpsOverlayController3 != null) {
                new BiometricPrompt.Builder(UdfpsController.this.mContext).setTitle("Test").setDeviceCredentialAllowed(true).setAllowBackgroundAuthentication(true).build().authenticate(new CancellationSignal(), new MediaRoute2Provider$$ExternalSyntheticLambda0(new Handler(Looper.getMainLooper())), new BiometricPrompt.AuthenticationCallback(udfpsOverlayController3) { // from class: com.android.systemui.biometrics.UdfpsController.UdfpsOverlayController.1
                    public AnonymousClass1(UdfpsOverlayController udfpsOverlayController32) {
                    }
                });
                return;
            }
            return;
        }
        if (list.size() == 2 && Intrinsics.areEqual(list.get(0), "setIgnoreDisplayTouches")) {
            setIgnoreDisplayTouches(Boolean.parseBoolean((String) list.get(1)));
            return;
        }
        printWriter.println("invalid command");
        printWriter.println("Usage: adb shell cmd statusbar udfps <cmd>");
        printWriter.println("Supported commands:");
        printWriter.println("  - show <reason>");
        printWriter.println("    -> supported reasons: [enroll-find-sensor, enroll-enrolling, auth-bp, auth-keyguard, auth-other, auth-settings]");
        printWriter.println("    -> reason otherwise defaults to unknown");
        printWriter.println("  - hide");
        printWriter.println("  - onUiReady");
        printWriter.println("  - simFingerDown");
        printWriter.println("    -> Simulates onFingerDown on sensor");
        printWriter.println("  - simFingerUp");
        printWriter.println("    -> Simulates onFingerUp on sensor");
        printWriter.println("  - biometricPrompt");
        printWriter.println("    -> Shows Biometric Prompt");
    }

    public final void onUiReady() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
        if (udfpsOverlayController != null) {
            UdfpsController udfpsController = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            udfpsController.mFingerprintManager.onUdfpsUiEvent(2, udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, 0);
        }
    }

    public final void setIgnoreDisplayTouches(boolean z) {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
        if (udfpsOverlayController != null) {
            UdfpsController udfpsController = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            udfpsController.mFingerprintManager.setIgnoreDisplayTouches(udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, udfpsController.mSensorProps.sensorId, z);
        }
    }

    public final void simFingerDown() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
        Intrinsics.checkNotNull(udfpsOverlayController);
        Rect rect = UdfpsController.this.mOverlayParams.sensorBounds;
        MotionEvent obtainMotionEvent = obtainMotionEvent(rect.exactCenterX(), rect.exactCenterY(), 0);
        UdfpsController.UdfpsOverlayController udfpsOverlayController2 = this.udfpsOverlayController;
        if (udfpsOverlayController2 != null) {
            UdfpsController udfpsController = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            UdfpsController.m891$$Nest$monTouch(udfpsController, udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, obtainMotionEvent, true);
        }
        MotionEvent obtainMotionEvent2 = obtainMotionEvent(rect.exactCenterX(), rect.exactCenterY(), 2);
        UdfpsController.UdfpsOverlayController udfpsOverlayController3 = this.udfpsOverlayController;
        if (udfpsOverlayController3 != null) {
            UdfpsController udfpsController2 = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsController2.mOverlay;
            UdfpsController.m891$$Nest$monTouch(udfpsController2, udfpsControllerOverlay2 != null ? udfpsControllerOverlay2.requestId : 0L, obtainMotionEvent2, true);
        }
        if (obtainMotionEvent != null) {
            obtainMotionEvent.recycle();
        }
        if (obtainMotionEvent2 != null) {
            obtainMotionEvent2.recycle();
        }
    }

    public final void simFingerUp() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
        Intrinsics.checkNotNull(udfpsOverlayController);
        Rect rect = UdfpsController.this.mOverlayParams.sensorBounds;
        MotionEvent obtainMotionEvent = obtainMotionEvent(rect.exactCenterX(), rect.exactCenterY(), 1);
        UdfpsController.UdfpsOverlayController udfpsOverlayController2 = this.udfpsOverlayController;
        if (udfpsOverlayController2 != null) {
            UdfpsController udfpsController = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            UdfpsController.m891$$Nest$monTouch(udfpsController, udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, obtainMotionEvent, true);
        }
        if (obtainMotionEvent != null) {
            obtainMotionEvent.recycle();
        }
    }
}
