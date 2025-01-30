package com.android.server.display;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Message;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.util.Slog;
import android.view.Display;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.power.PowerManagerUtil;
import java.io.PrintWriter;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class DisplayManagerShellCommand extends ShellCommand {
    public static String RANDOM_STR_1KBYTE = "JDe5AqtM8GLVo5tHzUw9NKpoQ0FpjUZh6p1hlO1SEcdIgn5ulm6oF5pWIKjJGgK6FXavJ9EzffRSFs7FOs4RgxAnb6fTbNzQbPRU9iYVK1WmTgPJjglYWjyuqgzgqzTZAhlB2Hul9L5ONZ90NkHvhLovcLdbd8dZBP2uzaz0F1CpTjwT7LU2p8pjI10YpInyRynNFewJM53rsvsbhK6n6SM1r681sK4VOJXmS4eTzBJ3yUbvB4itKeO2QQK0BoNfKMlvhEnJ5tfeG2PNsRsnlRED4h7uHeg4iCMwx3LdUCZh1ZPjlB4d5i3wrf2tZ8nf9yjh8TVQiBZfjOyRsASOEvtOdmvS3n1MCYJ405mw1gGWtl4gzYXnOArOqVR9699udkxAmw2nLDK7n5eA418eWEqcUSMO88H8T5xwG48RdxMFjusvnXj7BDrcPOhPT0s4y33vtmdha7xis51z5WmiI4t82g3NYelOvND8KQe0Zq9rUOqnrfS0OSGpQic4hXmz";
    public final DisplayManagerService mService;

    public DisplayManagerShellCommand(DisplayManagerService displayManagerService) {
        this.mService = displayManagerService;
    }

    public int onCommand(String str) {
        PrintWriter outPrintWriter;
        int i;
        String[] strArr;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        outPrintWriter = getOutPrintWriter();
        i = 1;
        switch (str) {
            case "reset-brightness-configuration":
                return resetBrightnessConfiguration();
            case "get-displays":
                return getDisplays();
            case "ab-add-brightness-weights":
                return addBrightnessWeights(outPrintWriter);
            case "ab-test-enable":
                return setTestModeEnabled(true);
            case "get-user-disabled-hdr-types":
                return getUserDisabledHdrTypes();
            case "undock":
                return unsetDockedAndIdle();
            case "dwb-logging-enable":
                return setDisplayWhiteBalanceLoggingEnabled(true);
            case "set-user-preferred-display-mode":
                return setUserPreferredDisplayMode();
            case "get-user-preferred-display-mode":
                return getUserPreferredDisplayMode();
            case "ab-inject-lux":
                return injectLux(outPrintWriter);
            case "dock":
                return setDockedAndIdle();
            case "ab-logging-enable":
                return setAutoBrightnessLoggingEnabled(true);
            case "dwb-set-cct":
                return setAmbientColorTemperatureOverride();
            case "ab-test-disable":
                return setTestModeEnabled(false);
            case "ab-logging-disable":
                return setAutoBrightnessLoggingEnabled(false);
            case "ab-short-term-reset":
                return doShortTermReset();
            case "get-active-display-mode-at-start":
                return getActiveDisplayModeAtStart();
            case "get-ambient-brightness-info":
                return getAmbientBrightnessInfo(outPrintWriter);
            case "set-match-content-frame-rate-pref":
                return setMatchContentFrameRateUserPreference();
            case "set-brightness":
                return setBrightness();
            case "get-match-content-frame-rate-pref":
                return getMatchContentFrameRateUserPreference();
            case "dmd-logging-disable":
                return setDisplayModeDirectorLoggingEnabled(false);
            case "dmd-logging-enable":
                return setDisplayModeDirectorLoggingEnabled(true);
            case "set-user-disabled-hdr-types":
                return setUserDisabledHdrTypes();
            case "set-hdr-ramp-rate":
                return setHdrRampRate(outPrintWriter);
            case "clear-user-preferred-display-mode":
                return clearUserPreferredDisplayMode();
            case "dwb-logging-disable":
                return setDisplayWhiteBalanceLoggingEnabled(false);
            default:
                if (str.startsWith("bufferFill")) {
                    try {
                        strArr = str.split(PackageManagerShellCommandDataLoader.STDIN_PATH, 2);
                    } catch (Exception unused) {
                        strArr = null;
                    }
                    if (strArr == null || strArr.length < 2) {
                        outPrintWriter.println("Cannot parse bufferFill-[size in kbytes] from input [" + str + "], will use default size.");
                    } else {
                        try {
                            int parseInt = Integer.parseInt(strArr[1]);
                            if (parseInt <= 0) {
                                outPrintWriter.println("Got a size <= 0 from input [" + str + "], will use default size.");
                            } else {
                                i = parseInt;
                            }
                        } catch (Exception unused2) {
                            outPrintWriter.println("Exception occured while parsing bufferFill-[size in kbytes] from input [" + str + "], will use default size.");
                        }
                    }
                    if (!"1".equals(SystemProperties.get("persist.sys.testlab", "0"))) {
                        outPrintWriter.println("Error: bufferFill command can only be operated when persist.sys.testlab=1");
                        return 0;
                    }
                    outPrintWriter.println("Filling " + i + " KByte(s) to Async binder buffer...");
                    return debugAsyncBufferFill(i);
                }
                return handleDefaultCommands(str);
        }
    }

    public int debugAsyncBufferFill(int i) {
        Message obtainMessage = this.mService.getDisplayHandler().obtainMessage(29, 9, 0);
        Bundle bundle = new Bundle();
        try {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < i; i2++) {
                sb.append(RANDOM_STR_1KBYTE);
            }
            bundle.putString("bufferFill", sb.toString());
        } catch (Exception unused) {
        }
        obtainMessage.setData(bundle);
        this.mService.getDisplayHandler().sendMessage(obtainMessage);
        return 0;
    }

    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Display manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  set-brightness BRIGHTNESS");
        outPrintWriter.println("    Sets the current brightness to BRIGHTNESS (a number between 0 and 1).");
        outPrintWriter.println("  reset-brightness-configuration");
        outPrintWriter.println("    Reset the brightness to its default configuration.");
        outPrintWriter.println("  ab-logging-enable");
        outPrintWriter.println("    Enable auto-brightness logging.");
        outPrintWriter.println("  ab-logging-disable");
        outPrintWriter.println("    Disable auto-brightness logging.");
        outPrintWriter.println("  dwb-logging-enable");
        outPrintWriter.println("    Enable display white-balance logging.");
        outPrintWriter.println("  dwb-logging-disable");
        outPrintWriter.println("    Disable display white-balance logging.");
        outPrintWriter.println("  dmd-logging-enable");
        outPrintWriter.println("    Enable display mode director logging.");
        outPrintWriter.println("  dmd-logging-disable");
        outPrintWriter.println("    Disable display mode director logging.");
        outPrintWriter.println("  dwb-set-cct CCT");
        outPrintWriter.println("    Sets the ambient color temperature override to CCT (use -1 to disable).");
        outPrintWriter.println("  get-ambient-brightness-info [lux]");
        outPrintWriter.println("    Gets the ambient brightness thresholds and brightness info.");
        outPrintWriter.println("  ab-short-term-reset");
        outPrintWriter.println("    Do short-term reset immediately.");
        outPrintWriter.println("  ab-test-enable");
        outPrintWriter.println("    Enable adaptive brightness test mode for sec long-term model.");
        outPrintWriter.println("  ab-test-disable");
        outPrintWriter.println("    Disable adaptive brightness test mode for sec long-term model.");
        outPrintWriter.println("  ab-add-brightness-weights [lux]:[brightness(0.0f~1.0f)]:[durationSec]:[continuity]");
        outPrintWriter.println("    Add brightness weights directly  for sec long-term model.");
        outPrintWriter.println("  set-user-preferred-display-mode WIDTH HEIGHT REFRESH-RATE DISPLAY_ID (optional)");
        outPrintWriter.println("    Sets the user preferred display mode which has fields WIDTH, HEIGHT and REFRESH-RATE. If DISPLAY_ID is passed, the mode change is applied to displaywith id = DISPLAY_ID, else mode change is applied globally.");
        outPrintWriter.println("  clear-user-preferred-display-mode DISPLAY_ID (optional)");
        outPrintWriter.println("    Clears the user preferred display mode. If DISPLAY_ID is passed, the mode is cleared for  display with id = DISPLAY_ID, else mode is cleared globally.");
        outPrintWriter.println("  get-user-preferred-display-mode DISPLAY_ID (optional)");
        outPrintWriter.println("    Returns the user preferred display mode or null if no mode is set by user.If DISPLAY_ID is passed, the mode for display with id = DISPLAY_ID is returned, else global display mode is returned.");
        outPrintWriter.println("  get-active-display-mode-at-start DISPLAY_ID");
        outPrintWriter.println("    Returns the display mode which was found at boot time of display with id = DISPLAY_ID");
        outPrintWriter.println("  set-match-content-frame-rate-pref PREFERENCE");
        outPrintWriter.println("    Sets the match content frame rate preference as PREFERENCE ");
        outPrintWriter.println("  get-match-content-frame-rate-pref");
        outPrintWriter.println("    Returns the match content frame rate preference");
        outPrintWriter.println("  set-user-disabled-hdr-types TYPES...");
        outPrintWriter.println("    Sets the user disabled HDR types as TYPES");
        outPrintWriter.println("  get-user-disabled-hdr-types");
        outPrintWriter.println("    Returns the user disabled HDR types");
        outPrintWriter.println("  get-displays [CATEGORY]");
        outPrintWriter.println("    Returns the current displays. Can specify string category among");
        outPrintWriter.println("    DisplayManager.DISPLAY_CATEGORY_*; must use the actual string value.");
        outPrintWriter.println("  dock");
        outPrintWriter.println("    Sets brightness to docked + idle screen brightness mode");
        outPrintWriter.println("  undock");
        outPrintWriter.println("    Sets brightness to active (normal) screen brightness mode");
        outPrintWriter.println("  bufferFill-[size]");
        outPrintWriter.println("    Debug operation: Fill [size] KBytes to async binder buffer by making testing async binder call.");
        outPrintWriter.println();
        Intent.printIntentArgsHelp(outPrintWriter, "");
    }

    public final int getDisplays() {
        Display[] displays = ((DisplayManager) this.mService.getContext().getSystemService(DisplayManager.class)).getDisplays(getNextArg());
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Displays:");
        for (Display display : displays) {
            outPrintWriter.println("  " + display);
        }
        return 0;
    }

    public final int setBrightness() {
        float f;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no brightness specified");
            return 1;
        }
        try {
            f = Float.parseFloat(nextArg);
        } catch (NumberFormatException unused) {
            f = -1.0f;
        }
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f > 1.0f) {
            getErrPrintWriter().println("Error: brightness should be a number between 0 and 1");
            return 1;
        }
        ((DisplayManager) this.mService.getContext().getSystemService(DisplayManager.class)).setBrightness(0, f);
        return 0;
    }

    public final int resetBrightnessConfiguration() {
        this.mService.resetBrightnessConfigurations();
        return 0;
    }

    public final int setAutoBrightnessLoggingEnabled(boolean z) {
        this.mService.setAutoBrightnessLoggingEnabled(z);
        return 0;
    }

    public final int setDisplayWhiteBalanceLoggingEnabled(boolean z) {
        this.mService.setDisplayWhiteBalanceLoggingEnabled(z);
        return 0;
    }

    public final int setDisplayModeDirectorLoggingEnabled(boolean z) {
        this.mService.setDisplayModeDirectorLoggingEnabled(z);
        return 0;
    }

    public final int setAmbientColorTemperatureOverride() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no cct specified");
            return 1;
        }
        try {
            this.mService.setAmbientColorTemperatureOverride(Float.parseFloat(nextArg));
            return 0;
        } catch (NumberFormatException unused) {
            getErrPrintWriter().println("Error: cct should be a number");
            return 1;
        }
    }

    public final int getAmbientBrightnessInfo(PrintWriter printWriter) {
        float f;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no lux specified");
            return 1;
        }
        try {
            f = Float.parseFloat(nextArg);
        } catch (NumberFormatException unused) {
            f = -1.0f;
        }
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            getErrPrintWriter().println("Error: lux should be a positive number");
            return 1;
        }
        printWriter.println(this.mService.getAmbientBrightnessInfo(f));
        return 0;
    }

    public final int doShortTermReset() {
        this.mService.doShortTermReset();
        return 0;
    }

    public final int setTestModeEnabled(boolean z) {
        if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            getErrPrintWriter().println("Error: available for sec long-term model");
        }
        this.mService.setTestModeEnabled(z);
        return 0;
    }

    public final int injectLux(PrintWriter printWriter) {
        String nextArg = getNextArg();
        if (nextArg != null) {
            float parseFloat = Float.parseFloat(nextArg);
            String nextArg2 = getNextArg();
            float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            float parseFloat2 = nextArg2 != null ? Float.parseFloat(nextArg2) : 0.0f;
            String nextArg3 = getNextArg();
            if (nextArg3 != null) {
                f = Float.parseFloat(nextArg3);
            }
            SensorEvent sensorEvent = new SensorEvent(null, 0, 0L, new float[]{parseFloat, parseFloat2, f});
            Slog.d("DisplayManagerShellCommand", "injectLux: lux: " + sensorEvent.values[0] + " minLux: " + sensorEvent.values[1] + " fromRear: " + sensorEvent.values[2]);
            printWriter.println(this.mService.getAmbientBrightnessInfo(parseFloat));
            this.mService.injectLux(sensorEvent);
            return 0;
        }
        throw new RuntimeException("lux argument is required!");
    }

    public final int addBrightnessWeights(PrintWriter printWriter) {
        if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            getErrPrintWriter().println("Error: available for sec long-term model");
        }
        try {
            String[] split = getNextArg().split(XmlUtils.STRING_ARRAY_SEPARATOR);
            this.mService.addBrightnessWeights(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
            return 0;
        } catch (Exception unused) {
            getErrPrintWriter().println("Error: failed to add brightness weights");
            return 1;
        }
    }

    public final int setUserPreferredDisplayMode() {
        int parseInt;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no width specified");
            return 1;
        }
        String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            getErrPrintWriter().println("Error: no height specified");
            return 1;
        }
        String nextArg3 = getNextArg();
        if (nextArg3 == null) {
            getErrPrintWriter().println("Error: no refresh-rate specified");
            return 1;
        }
        try {
            int parseInt2 = Integer.parseInt(nextArg);
            int parseInt3 = Integer.parseInt(nextArg2);
            float parseFloat = Float.parseFloat(nextArg3);
            if ((parseInt2 < 0 || parseInt3 < 0) && parseFloat <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                getErrPrintWriter().println("Error: invalid value of resolution (width, height) and refresh rate");
                return 1;
            }
            String nextArg4 = getNextArg();
            if (nextArg4 != null) {
                try {
                    parseInt = Integer.parseInt(nextArg4);
                } catch (NumberFormatException unused) {
                    getErrPrintWriter().println("Error: invalid format of display ID");
                    return 1;
                }
            } else {
                parseInt = -1;
            }
            this.mService.setUserPreferredDisplayModeInternal(parseInt, new Display.Mode(parseInt2, parseInt3, parseFloat));
            return 0;
        } catch (NumberFormatException unused2) {
            getErrPrintWriter().println("Error: invalid format of width, height or refresh rate");
            return 1;
        }
    }

    public final int clearUserPreferredDisplayMode() {
        int parseInt;
        String nextArg = getNextArg();
        if (nextArg != null) {
            try {
                parseInt = Integer.parseInt(nextArg);
            } catch (NumberFormatException unused) {
                getErrPrintWriter().println("Error: invalid format of display ID");
                return 1;
            }
        } else {
            parseInt = -1;
        }
        this.mService.setUserPreferredDisplayModeInternal(parseInt, null);
        return 0;
    }

    public final int getUserPreferredDisplayMode() {
        int parseInt;
        String nextArg = getNextArg();
        if (nextArg != null) {
            try {
                parseInt = Integer.parseInt(nextArg);
            } catch (NumberFormatException unused) {
                getErrPrintWriter().println("Error: invalid format of display ID");
                return 1;
            }
        } else {
            parseInt = -1;
        }
        Display.Mode userPreferredDisplayModeInternal = this.mService.getUserPreferredDisplayModeInternal(parseInt);
        if (userPreferredDisplayModeInternal == null) {
            getOutPrintWriter().println("User preferred display mode: null");
            return 0;
        }
        getOutPrintWriter().println("User preferred display mode: " + userPreferredDisplayModeInternal.getPhysicalWidth() + " " + userPreferredDisplayModeInternal.getPhysicalHeight() + " " + userPreferredDisplayModeInternal.getRefreshRate());
        return 0;
    }

    public final int getActiveDisplayModeAtStart() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no displayId specified");
            return 1;
        }
        try {
            Display.Mode activeDisplayModeAtStart = this.mService.getActiveDisplayModeAtStart(Integer.parseInt(nextArg));
            if (activeDisplayModeAtStart == null) {
                getOutPrintWriter().println("Boot display mode: null");
                return 0;
            }
            getOutPrintWriter().println("Boot display mode: " + activeDisplayModeAtStart.getPhysicalWidth() + " " + activeDisplayModeAtStart.getPhysicalHeight() + " " + activeDisplayModeAtStart.getRefreshRate());
            return 0;
        } catch (NumberFormatException unused) {
            getErrPrintWriter().println("Error: invalid displayId");
            return 1;
        }
    }

    public final int setMatchContentFrameRateUserPreference() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no matchContentFrameRatePref specified");
            return 1;
        }
        try {
            int parseInt = Integer.parseInt(nextArg);
            if (parseInt < 0) {
                getErrPrintWriter().println("Error: invalid value of matchContentFrameRatePreference");
                return 1;
            }
            ((DisplayManager) this.mService.getContext().getSystemService(DisplayManager.class)).setRefreshRateSwitchingType(toRefreshRateSwitchingType(parseInt));
            return 0;
        } catch (NumberFormatException unused) {
            getErrPrintWriter().println("Error: invalid format of matchContentFrameRatePreference");
            return 1;
        }
    }

    public final int getMatchContentFrameRateUserPreference() {
        DisplayManager displayManager = (DisplayManager) this.mService.getContext().getSystemService(DisplayManager.class);
        getOutPrintWriter().println("Match content frame rate type: " + displayManager.getMatchContentFrameRateUserPreference());
        return 0;
    }

    public final int setUserDisabledHdrTypes() {
        String[] peekRemainingArgs = peekRemainingArgs();
        if (peekRemainingArgs == null) {
            getErrPrintWriter().println("Error: no userDisabledHdrTypes specified");
            return 1;
        }
        int[] iArr = new int[peekRemainingArgs.length];
        try {
            int length = peekRemainingArgs.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i2 + 1;
                iArr[i2] = Integer.parseInt(peekRemainingArgs[i]);
                i++;
                i2 = i3;
            }
            ((DisplayManager) this.mService.getContext().getSystemService(DisplayManager.class)).setUserDisabledHdrTypes(iArr);
            return 0;
        } catch (NumberFormatException unused) {
            getErrPrintWriter().println("Error: invalid format of userDisabledHdrTypes");
            return 1;
        }
    }

    public final int getUserDisabledHdrTypes() {
        int[] userDisabledHdrTypes = ((DisplayManager) this.mService.getContext().getSystemService(DisplayManager.class)).getUserDisabledHdrTypes();
        getOutPrintWriter().println("User disabled HDR types: " + Arrays.toString(userDisabledHdrTypes));
        return 0;
    }

    public final int toRefreshRateSwitchingType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        Slog.e("DisplayManagerShellCommand", i + " is not a valid value of matchContentFrameRate type.");
        return -1;
    }

    public final int setDockedAndIdle() {
        this.mService.setDockedAndIdleEnabled(true, 0);
        return 0;
    }

    public final int unsetDockedAndIdle() {
        this.mService.setDockedAndIdleEnabled(false, 0);
        return 0;
    }

    public final int setHdrRampRate(PrintWriter printWriter) {
        float f;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no rate specified");
            return 1;
        }
        float f2 = -1.0f;
        try {
            String[] split = nextArg.split(",");
            f = Float.parseFloat(split[0]);
            try {
                f2 = Float.parseFloat(split[1]);
            } catch (NumberFormatException unused) {
            }
        } catch (NumberFormatException unused2) {
            f = -1.0f;
        }
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            getErrPrintWriter().println("Error: rate should be a positive number");
            return 1;
        }
        this.mService.setHdrRampRate(f, f2);
        return 0;
    }
}
