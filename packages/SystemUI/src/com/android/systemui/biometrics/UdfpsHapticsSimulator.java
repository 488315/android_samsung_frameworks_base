package com.android.systemui.biometrics;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;

public final class UdfpsHapticsSimulator implements Command {
    public final AudioAttributes sonificationEffects = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    public UdfpsController udfpsController;
    public final VibratorHelper vibrator;

    public UdfpsHapticsSimulator(CommandRegistry commandRegistry, VibratorHelper vibratorHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.vibrator = vibratorHelper;
        commandRegistry.registerCommand("udfps-haptic", new Function0() { // from class: com.android.systemui.biometrics.UdfpsHapticsSimulator.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UdfpsHapticsSimulator.this;
            }
        });
    }

    public static void invalidCommand$1(PrintWriter printWriter) {
        printWriter.println("invalid command");
        printWriter.println("Usage: adb shell cmd statusbar udfps-haptic <haptic>");
        printWriter.println("Available commands:");
        printWriter.println("  start");
        printWriter.println("  success, always plays CLICK haptic");
        printWriter.println("  error, always plays DOUBLE_CLICK haptic");
    }

    @Override // com.android.systemui.statusbar.commandline.Command
    public final void execute(PrintWriter printWriter, List list) {
        if (list.isEmpty()) {
            invalidCommand$1(printWriter);
            return;
        }
        String str = (String) list.get(0);
        int hashCode = str.hashCode();
        VibratorHelper vibratorHelper = this.vibrator;
        if (hashCode != -1867169789) {
            if (hashCode != 96784904) {
                if (hashCode == 109757538 && str.equals(NetworkAnalyticsConstants.DataPoints.OPEN_TIME)) {
                    UdfpsController udfpsController = this.udfpsController;
                    if (udfpsController != null) {
                        udfpsController.playStartHaptic();
                        return;
                    }
                    return;
                }
            } else if (str.equals("error")) {
                vibratorHelper.vibrate(VibrationEffect.get(1), this.sonificationEffects);
                return;
            }
        } else if (str.equals("success")) {
            vibratorHelper.vibrate(VibrationEffect.get(0), this.sonificationEffects);
            return;
        }
        invalidCommand$1(printWriter);
    }
}
