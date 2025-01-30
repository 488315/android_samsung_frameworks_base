package com.android.systemui.biometrics;

import android.graphics.Rect;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.VibrationAttributes;
import android.util.Log;
import android.view.MotionEvent;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsShell implements Command {
    public UdfpsController.UdfpsOverlayController udfpsOverlayController;

    public UdfpsShell(CommandRegistry commandRegistry) {
        commandRegistry.registerCommand("udfps", new Function0() { // from class: com.android.systemui.biometrics.UdfpsShell.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UdfpsShell.this;
            }
        });
    }

    public static MotionEvent obtainMotionEvent(int i, float f, float f2) {
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
        if (list.size() != 2 || !Intrinsics.areEqual(list.get(0), "show")) {
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
            return;
        }
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
        }
    }

    public final void onUiReady() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
        if (udfpsOverlayController != null) {
            UdfpsController udfpsController = UdfpsController.this;
            VibrationAttributes vibrationAttributes = UdfpsController.UDFPS_VIBRATION_ATTRIBUTES;
            udfpsController.getClass();
            UdfpsController udfpsController2 = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController2.mOverlay;
            udfpsController2.mFingerprintManager.onUiReady(udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, 0);
        }
    }

    public final void simFingerDown() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.udfpsOverlayController;
        Intrinsics.checkNotNull(udfpsOverlayController);
        Rect rect = UdfpsController.this.mOverlayParams.sensorBounds;
        MotionEvent obtainMotionEvent = obtainMotionEvent(0, rect.exactCenterX(), rect.exactCenterY());
        UdfpsController.UdfpsOverlayController udfpsOverlayController2 = this.udfpsOverlayController;
        if (udfpsOverlayController2 != null) {
            UdfpsController udfpsController = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            udfpsController.onTouch(udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, obtainMotionEvent, true);
        }
        MotionEvent obtainMotionEvent2 = obtainMotionEvent(2, rect.exactCenterX(), rect.exactCenterY());
        UdfpsController.UdfpsOverlayController udfpsOverlayController3 = this.udfpsOverlayController;
        if (udfpsOverlayController3 != null) {
            UdfpsController udfpsController2 = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsController2.mOverlay;
            udfpsController2.onTouch(udfpsControllerOverlay2 != null ? udfpsControllerOverlay2.requestId : 0L, obtainMotionEvent2, true);
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
        MotionEvent obtainMotionEvent = obtainMotionEvent(1, rect.exactCenterX(), rect.exactCenterY());
        UdfpsController.UdfpsOverlayController udfpsOverlayController2 = this.udfpsOverlayController;
        if (udfpsOverlayController2 != null) {
            UdfpsController udfpsController = UdfpsController.this;
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            udfpsController.onTouch(udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : 0L, obtainMotionEvent, true);
        }
        if (obtainMotionEvent != null) {
            obtainMotionEvent.recycle();
        }
    }
}
