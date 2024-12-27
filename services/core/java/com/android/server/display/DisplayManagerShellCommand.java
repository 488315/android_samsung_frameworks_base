package com.android.server.display;

import android.app.NotificationManager;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.display.DisplayManager;
import android.net.util.NetworkConstants;
import android.os.Message;
import android.os.ShellCommand;
import android.util.Slog;
import android.view.Display;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Predicate;

public final class DisplayManagerShellCommand extends ShellCommand {
    public final DisplayManagerFlags mFlags;
    public final DisplayManagerService mService;

    public DisplayManagerShellCommand(
            DisplayManagerService displayManagerService, DisplayManagerFlags displayManagerFlags) {
        this.mService = displayManagerService;
        this.mFlags = displayManagerFlags;
    }

    public final int addBrightnessWeights() {
        if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            getErrPrintWriter().println("Error: available for sec long-term model");
        }
        try {
            String[] split = getNextArg().split(":");
            float parseFloat = Float.parseFloat(split[0]);
            float parseFloat2 = Float.parseFloat(split[1]);
            float parseFloat3 = Float.parseFloat(split[2]);
            float parseFloat4 = Float.parseFloat(split[3]);
            DisplayManagerService displayManagerService = this.mService;
            synchronized (displayManagerService.mSyncRoot) {
                ((DisplayPowerController)
                                ((DisplayPowerControllerInterface)
                                        displayManagerService.mDisplayPowerControllers.get(0)))
                        .addBrightnessWeights(parseFloat, parseFloat2, parseFloat3, parseFloat4);
            }
            return 0;
        } catch (Exception unused) {
            getErrPrintWriter().println("Error: failed to add brightness weights");
            return 1;
        }
    }

    /* JADX WARN: Failed to clean up code after switch over string restore
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v20 android.view.Display$Mode, still in use, count: 4, list:
      (r1v20 android.view.Display$Mode) from 0x05d8: IF  (r1v20 android.view.Display$Mode) == (null android.view.Display$Mode)  -> B:247:0x05da A[HIDDEN]
      (r1v20 android.view.Display$Mode) from 0x05ef: INVOKE (r1v20 android.view.Display$Mode) VIRTUAL call: android.view.Display.Mode.getPhysicalWidth():int A[MD:():int (c), WRAPPED]
      (r1v20 android.view.Display$Mode) from 0x05fb: INVOKE (r1v20 android.view.Display$Mode) VIRTUAL call: android.view.Display.Mode.getPhysicalHeight():int A[MD:():int (c), WRAPPED]
      (r1v20 android.view.Display$Mode) from 0x0605: INVOKE (r1v20 android.view.Display$Mode) VIRTUAL call: android.view.Display.Mode.getRefreshRate():float A[MD:():float (c), WRAPPED]
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
    	at jadx.core.utils.InsnRemover.removeAllMarked(InsnRemover.java:271)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.markCodeForRemoval(SwitchOverStringVisitor.java:153)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:117)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public final int onCommand(String str) {
        char c;
        boolean z;
        boolean z2;
        int i;
        float f;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        float f2 = -1.0f;
        String str2 = null;
        Display.Mode activeDisplayModeAtStartLocked = null;
        float f3 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        switch (str.hashCode()) {
            case -1505467592:
                if (str.equals("reset-brightness-configuration")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1459563384:
                if (str.equals("get-displays")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1226903944:
                if (str.equals("enable-display")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1115843753:
                if (str.equals("ab-add-brightness-weights")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1042655374:
                if (str.equals("ab-test-enable")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1021080420:
                if (str.equals("get-user-disabled-hdr-types")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -840680372:
                if (str.equals("undock")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -731435249:
                if (str.equals("dwb-logging-enable")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -687141135:
                if (str.equals("set-user-preferred-display-mode")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -601773083:
                if (str.equals("get-user-preferred-display-mode")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -464562757:
                if (str.equals("show-notification")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -153369857:
                if (str.equals("ab-inject-lux")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 3088947:
                if (str.equals("dock")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 10139357:
                if (str.equals("disable-display")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 276125397:
                if (str.equals("cancel-notifications")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 483509981:
                if (str.equals("ab-logging-enable")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 844013735:
                if (str.equals("power-off")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 847215243:
                if (str.equals("dwb-set-cct")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 858510247:
                if (str.equals("power-on")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 1023356633:
                if (str.equals("ab-test-disable")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1089842382:
                if (str.equals("ab-logging-disable")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1257739979:
                if (str.equals("ab-short-term-reset")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 1265268983:
                if (str.equals("get-active-display-mode-at-start")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1399894526:
                if (str.equals("get-ambient-brightness-info")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1428935945:
                if (str.equals("set-match-content-frame-rate-pref")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 1604823708:
                if (str.equals("set-brightness")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 1863255293:
                if (str.equals("get-match-content-frame-rate-pref")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1873686952:
                if (str.equals("dmd-logging-disable")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1894268611:
                if (str.equals("dmd-logging-enable")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 1928353192:
                if (str.equals("set-user-disabled-hdr-types")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1984196985:
                if (str.equals("set-hdr-ramp-rate")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 2076592732:
                if (str.equals("clear-user-preferred-display-mode")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 2081245916:
                if (str.equals("dwb-logging-disable")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mService.resetBrightnessConfigurations();
                return 0;
            case 1:
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                final ArrayList arrayList = new ArrayList();
                boolean z3 = false;
                boolean z4 = false;
                while (true) {
                    String nextOption = getNextOption();
                    if (nextOption == null) {
                        String nextArg = getNextArg();
                        if (nextArg != null) {
                            if (str2 != null) {
                                outPrintWriter2.println(
                                        "Error: the category has been specified both with the -c"
                                            + " option and the positional argument. Please select"
                                            + " only one category.");
                            } else {
                                str2 = nextArg;
                            }
                        }
                        Display[] displays =
                                ((DisplayManager)
                                                this.mService
                                                        .getContext()
                                                        .getSystemService(DisplayManager.class))
                                        .getDisplays(str2);
                        Object[] objArr = displays;
                        if (z4) {
                            objArr =
                                    (Display[])
                                            Arrays.stream(displays)
                                                    .filter(
                                                            new Predicate() { // from class:
                                                                              // com.android.server.display.DisplayManagerShellCommand$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    return arrayList.contains(
                                                                            Integer.valueOf(
                                                                                    ((Display) obj)
                                                                                            .getType()));
                                                                }
                                                            })
                                                    .toArray(
                                                            new DisplayManagerShellCommand$$ExternalSyntheticLambda1());
                        }
                        if (!z3) {
                            outPrintWriter2.println("Displays:");
                        }
                        for (int i2 = 0; i2 < objArr.length; i2++) {
                            outPrintWriter2.println(
                                    z3 ? Integer.valueOf(objArr[i2].getDisplayId()) : objArr[i2]);
                        }
                        return 0;
                    }
                    switch (nextOption.hashCode()) {
                        case 0:
                            if (nextOption.equals("")) {
                                z = false;
                                break;
                            }
                            z = -1;
                            break;
                        case 1494:
                            if (nextOption.equals("-c")) {
                                z = true;
                                break;
                            }
                            z = -1;
                            break;
                        case NetworkConstants.ETHER_MTU /* 1500 */:
                            if (nextOption.equals("-i")) {
                                z = 2;
                                break;
                            }
                            z = -1;
                            break;
                        case 1511:
                            if (nextOption.equals("-t")) {
                                z = 3;
                                break;
                            }
                            z = -1;
                            break;
                        case 66265758:
                            if (nextOption.equals("--category")) {
                                z = 4;
                                break;
                            }
                            z = -1;
                            break;
                        case 220627777:
                            if (nextOption.equals("--ids-only")) {
                                z = 5;
                                break;
                            }
                            z = -1;
                            break;
                        case 1333445850:
                            if (nextOption.equals("--type")) {
                                z = 6;
                                break;
                            }
                            z = -1;
                            break;
                        default:
                            z = -1;
                            break;
                    }
                    switch (z) {
                        case false:
                        case true:
                        case true:
                            if (str2 != null) {
                                outPrintWriter2.println(
                                        "Error: the category has been specified more than one time."
                                            + " Please select only one category.");
                                break;
                            } else {
                                str2 = getNextArgRequired();
                            }
                        case true:
                        case true:
                            z3 = true;
                            continue;
                        case true:
                        case true:
                            String upperCase = getNextArgRequired().toUpperCase(Locale.ENGLISH);
                            upperCase.getClass();
                            switch (upperCase.hashCode()) {
                                case -1038134325:
                                    if (upperCase.equals("EXTERNAL")) {
                                        z2 = false;
                                        break;
                                    }
                                    z2 = -1;
                                    break;
                                case -373305296:
                                    if (upperCase.equals("OVERLAY")) {
                                        z2 = true;
                                        break;
                                    }
                                    z2 = -1;
                                    break;
                                case 2664213:
                                    if (upperCase.equals("WIFI")) {
                                        z2 = 2;
                                        break;
                                    }
                                    z2 = -1;
                                    break;
                                case 433141802:
                                    if (upperCase.equals("UNKNOWN")) {
                                        z2 = 3;
                                        break;
                                    }
                                    z2 = -1;
                                    break;
                                case 1184148203:
                                    if (upperCase.equals("VIRTUAL")) {
                                        z2 = 4;
                                        break;
                                    }
                                    z2 = -1;
                                    break;
                                case 1353037501:
                                    if (upperCase.equals("INTERNAL")) {
                                        z2 = 5;
                                        break;
                                    }
                                    z2 = -1;
                                    break;
                                default:
                                    z2 = -1;
                                    break;
                            }
                            switch (z2) {
                                case false:
                                    i = 2;
                                    break;
                                case true:
                                    i = 4;
                                    break;
                                case true:
                                    i = 3;
                                    break;
                                case true:
                                    i = 0;
                                    break;
                                case true:
                                    i = 5;
                                    break;
                                case true:
                                    i = 1;
                                    break;
                                default:
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            outPrintWriter2,
                                            "Error: argument for display type should be one of"
                                                + " 'UNKNOWN', 'INTERNAL', 'EXTERNAL', 'WIFI',"
                                                + " 'OVERLAY', 'VIRTUAL', but got '",
                                            upperCase,
                                            "' instead.");
                                    i = -1;
                                    break;
                            }
                            if (i == -1) {
                                break;
                            } else {
                                arrayList.add(Integer.valueOf(i));
                                z4 = true;
                            }
                        default:
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    outPrintWriter2, "Error: unknown option '", nextOption, "'");
                            break;
                    }
                }
                return 1;
            case 2:
                return setDisplayEnabled(true);
            case 3:
                return addBrightnessWeights();
            case 4:
                setTestModeEnabled(true);
                return 0;
            case 5:
                int[] userDisabledHdrTypes =
                        ((DisplayManager)
                                        this.mService
                                                .getContext()
                                                .getSystemService(DisplayManager.class))
                                .getUserDisabledHdrTypes();
                getOutPrintWriter()
                        .println(
                                "User disabled HDR types: "
                                        + Arrays.toString(userDisabledHdrTypes));
                return 0;
            case 6:
                this.mService.setDockedAndIdleEnabled(false);
                return 0;
            case 7:
                setDisplayWhiteBalanceLoggingEnabled(true);
                return 0;
            case '\b':
                String nextArg2 = getNextArg();
                if (nextArg2 == null) {
                    getErrPrintWriter().println("Error: no width specified");
                } else {
                    String nextArg3 = getNextArg();
                    if (nextArg3 == null) {
                        getErrPrintWriter().println("Error: no height specified");
                    } else {
                        String nextArg4 = getNextArg();
                        if (nextArg4 == null) {
                            getErrPrintWriter().println("Error: no refresh-rate specified");
                        } else {
                            try {
                                int parseInt = Integer.parseInt(nextArg2);
                                int parseInt2 = Integer.parseInt(nextArg3);
                                float parseFloat = Float.parseFloat(nextArg4);
                                if ((parseInt >= 0 && parseInt2 >= 0)
                                        || parseFloat
                                                > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                                    String nextArg5 = getNextArg();
                                    if (nextArg5 != null) {
                                        try {
                                            r6 = Integer.parseInt(nextArg5);
                                        } catch (NumberFormatException unused) {
                                            getErrPrintWriter()
                                                    .println("Error: invalid format of display ID");
                                        }
                                    }
                                    this.mService.setUserPreferredDisplayModeInternal(
                                            r6, new Display.Mode(parseInt, parseInt2, parseFloat));
                                    return 0;
                                }
                                getErrPrintWriter()
                                        .println(
                                                "Error: invalid value of resolution (width, height)"
                                                    + " and refresh rate");
                            } catch (NumberFormatException unused2) {
                                getErrPrintWriter()
                                        .println(
                                                "Error: invalid format of width, height or refresh"
                                                    + " rate");
                            }
                        }
                    }
                }
                return 1;
            case '\t':
                String nextArg6 = getNextArg();
                if (nextArg6 != null) {
                    try {
                        r6 = Integer.parseInt(nextArg6);
                    } catch (NumberFormatException unused3) {
                        getErrPrintWriter().println("Error: invalid format of display ID");
                        return 1;
                    }
                }
                if (r1 == null) {
                    getOutPrintWriter().println("User preferred display mode: null");
                    return 0;
                }
                getOutPrintWriter()
                        .println(
                                "User preferred display mode: "
                                        + r1.getPhysicalWidth()
                                        + " "
                                        + r1.getPhysicalHeight()
                                        + " "
                                        + r1.getRefreshRate());
                return 0;
            case '\n':
                String nextArg7 = getNextArg();
                if (nextArg7 != null) {
                    switch (nextArg7) {
                        case "on-cable-dp-incapable":
                            this.mService.mDisplayNotificationManager
                                    .onCableNotCapableDisplayPort();
                            return 0;
                        case "on-hotplug-error":
                            this.mService.mDisplayNotificationManager.onHotplugConnectionError();
                            return 0;
                        case "on-link-training-failure":
                            this.mService.mDisplayNotificationManager
                                    .onDisplayPortLinkTrainingFailure();
                            return 0;
                        default:
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    getErrPrintWriter(),
                                    "Error: unexpected notification type=",
                                    nextArg7,
                                    ", use one of: on-hotplug-error, on-link-training-failure,"
                                        + " on-cable-dp-incapable");
                            break;
                    }
                } else {
                    getErrPrintWriter()
                            .println(
                                    "Error: no notificationType specified, use one of:"
                                        + " on-hotplug-error, on-link-training-failure,"
                                        + " on-cable-dp-incapable");
                }
                return 1;
            case 11:
                String nextArg8 = getNextArg();
                if (nextArg8 == null) {
                    throw new RuntimeException("lux argument is required!");
                }
                float parseFloat2 = Float.parseFloat(nextArg8);
                String nextArg9 = getNextArg();
                float parseFloat3 = nextArg9 != null ? Float.parseFloat(nextArg9) : 0.0f;
                String nextArg10 = getNextArg();
                if (nextArg10 != null) {
                    f3 = Float.parseFloat(nextArg10);
                }
                final SensorEvent sensorEvent =
                        new SensorEvent(null, 0, 0L, new float[] {parseFloat2, parseFloat3, f3});
                Slog.d(
                        "DisplayManagerShellCommand",
                        "injectLux: lux: "
                                + sensorEvent.values[0]
                                + " minLux: "
                                + sensorEvent.values[1]
                                + " fromRear: "
                                + sensorEvent.values[2]);
                outPrintWriter.println(this.mService.getAmbientBrightnessInfo(parseFloat2));
                DisplayManagerService displayManagerService = this.mService;
                synchronized (displayManagerService.mSyncRoot) {
                    final AutomaticBrightnessController automaticBrightnessController =
                            ((DisplayPowerController)
                                            ((DisplayPowerControllerInterface)
                                                    displayManagerService.mDisplayPowerControllers
                                                            .get(0)))
                                    .mAutomaticBrightnessController;
                    if (automaticBrightnessController != null) {
                        automaticBrightnessController.mHandler.post(
                                new Runnable() { // from class:
                                                 // com.android.server.display.AutomaticBrightnessController$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AutomaticBrightnessController
                                                automaticBrightnessController2 =
                                                        AutomaticBrightnessController.this;
                                        SensorEvent sensorEvent2 = sensorEvent;
                                        automaticBrightnessController2.getClass();
                                        if (sensorEvent2.values[0]
                                                >= FullScreenMagnificationGestureHandler
                                                        .MAX_SCALE) {
                                            automaticBrightnessController2.mInjectedLuxEvent =
                                                    sensorEvent2;
                                            automaticBrightnessController2.onSensorChangedInternal(
                                                    sensorEvent2);
                                        } else if (automaticBrightnessController2.mInjectedLuxEvent
                                                != null) {
                                            automaticBrightnessController2.onSensorChangedInternal(
                                                    automaticBrightnessController2
                                                            .mLastRealLuxEventDuringInjection);
                                            automaticBrightnessController2.mInjectedLuxEvent = null;
                                        }
                                    }
                                });
                    }
                }
                return 0;
            case '\f':
                this.mService.setDockedAndIdleEnabled(true);
                return 0;
            case '\r':
                return setDisplayEnabled(false);
            case 14:
                NotificationManager notificationManager =
                        this.mService.mDisplayNotificationManager.mNotificationManager;
                if (notificationManager == null) {
                    Slog.e(
                            "DisplayNotificationManager",
                            "Can't cancelNotifications: NotificationManager is null");
                } else {
                    notificationManager.cancel("DisplayNotificationManager", 1);
                }
                return 0;
            case 15:
                setAutoBrightnessLoggingEnabled(true);
                return 0;
            case 16:
                return requestDisplayPower(false);
            case 17:
                String nextArg11 = getNextArg();
                if (nextArg11 == null) {
                    getErrPrintWriter().println("Error: no cct specified");
                } else {
                    try {
                        float parseFloat4 = Float.parseFloat(nextArg11);
                        DisplayManagerService displayManagerService2 = this.mService;
                        synchronized (displayManagerService2.mSyncRoot) {
                            DisplayPowerControllerInterface displayPowerControllerInterface =
                                    (DisplayPowerControllerInterface)
                                            displayManagerService2.mDisplayPowerControllers.get(0);
                            if (displayPowerControllerInterface != null) {
                                Message obtainMessage =
                                        ((DisplayPowerController) displayPowerControllerInterface)
                                                .mHandler.obtainMessage();
                                obtainMessage.what = 15;
                                obtainMessage.arg1 = Float.floatToIntBits(parseFloat4);
                                obtainMessage.sendToTarget();
                            }
                        }
                        return 0;
                    } catch (NumberFormatException unused4) {
                        getErrPrintWriter().println("Error: cct should be a number");
                    }
                }
                return 1;
            case 18:
                return requestDisplayPower(true);
            case 19:
                setTestModeEnabled(false);
                return 0;
            case 20:
                setAutoBrightnessLoggingEnabled(false);
                return 0;
            case 21:
                DisplayManagerService displayManagerService3 = this.mService;
                synchronized (displayManagerService3.mSyncRoot) {
                    DisplayPowerController displayPowerController =
                            (DisplayPowerController)
                                    ((DisplayPowerControllerInterface)
                                            displayManagerService3.mDisplayPowerControllers.get(0));
                    if (displayPowerController.mAutomaticBrightnessController != null) {
                        PowerHistorian powerHistorian = displayPowerController.mPowerHistorian;
                        powerHistorian.getClass();
                        powerHistorian.addRecord(
                                3, new PowerHistorian.MessageRecord("ShortTermModel: shell cmd"));
                        displayPowerController.mAutomaticBrightnessController.resetShortTermModel();
                    }
                }
                return 0;
            case 22:
                String nextArg12 = getNextArg();
                if (nextArg12 == null) {
                    getErrPrintWriter().println("Error: no displayId specified");
                } else {
                    try {
                        int parseInt3 = Integer.parseInt(nextArg12);
                        DisplayManagerService displayManagerService4 = this.mService;
                        synchronized (displayManagerService4.mSyncRoot) {
                            try {
                                DisplayDevice deviceForDisplayLocked =
                                        displayManagerService4.getDeviceForDisplayLocked(parseInt3);
                                if (deviceForDisplayLocked != null) {
                                    activeDisplayModeAtStartLocked =
                                            deviceForDisplayLocked
                                                    .getActiveDisplayModeAtStartLocked();
                                }
                            } finally {
                            }
                        }
                        if (activeDisplayModeAtStartLocked == null) {
                            getOutPrintWriter().println("Boot display mode: null");
                            return 0;
                        }
                        getOutPrintWriter()
                                .println(
                                        "Boot display mode: "
                                                + activeDisplayModeAtStartLocked.getPhysicalWidth()
                                                + " "
                                                + activeDisplayModeAtStartLocked.getPhysicalHeight()
                                                + " "
                                                + activeDisplayModeAtStartLocked.getRefreshRate());
                        return 0;
                    } catch (NumberFormatException unused5) {
                        getErrPrintWriter().println("Error: invalid displayId");
                    }
                }
                return 1;
            case 23:
                String nextArg13 = getNextArg();
                if (nextArg13 == null) {
                    getErrPrintWriter().println("Error: no lux specified");
                } else {
                    try {
                        f2 = Float.parseFloat(nextArg13);
                    } catch (NumberFormatException unused6) {
                    }
                    if (f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        outPrintWriter.println(this.mService.getAmbientBrightnessInfo(f2));
                        return 0;
                    }
                    getErrPrintWriter().println("Error: lux should be a positive number");
                }
                return 1;
            case 24:
                String nextArg14 = getNextArg();
                if (nextArg14 == null) {
                    getErrPrintWriter().println("Error: no matchContentFrameRatePref specified");
                } else {
                    try {
                        int parseInt4 = Integer.parseInt(nextArg14);
                        if (parseInt4 >= 0) {
                            if (parseInt4 != 0) {
                                if (parseInt4 != 1) {
                                    if (parseInt4 != 2) {
                                        Slog.e(
                                                "DisplayManagerShellCommand",
                                                parseInt4
                                                        + " is not a valid value of"
                                                        + " matchContentFrameRate type.");
                                    }
                                }
                            }
                            return 0;
                        }
                        getErrPrintWriter()
                                .println("Error: invalid value of matchContentFrameRatePreference");
                    } catch (NumberFormatException unused7) {
                        getErrPrintWriter()
                                .println(
                                        "Error: invalid format of matchContentFrameRatePreference");
                    }
                }
                return 1;
            case 25:
                String nextArg15 = getNextArg();
                if (nextArg15 == null) {
                    getErrPrintWriter().println("Error: no brightness specified");
                } else {
                    try {
                        f2 = Float.parseFloat(nextArg15);
                    } catch (NumberFormatException unused8) {
                    }
                    if (f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE && f2 <= 1.0f) {
                        ((DisplayManager)
                                        this.mService
                                                .getContext()
                                                .getSystemService(DisplayManager.class))
                                .setBrightness(0, f2);
                        return 0;
                    }
                    getErrPrintWriter()
                            .println("Error: brightness should be a number between 0 and 1");
                }
                return 1;
            case 26:
                DisplayManager displayManager =
                        (DisplayManager)
                                this.mService.getContext().getSystemService(DisplayManager.class);
                getOutPrintWriter()
                        .println(
                                "Match content frame rate type: "
                                        + displayManager.getMatchContentFrameRateUserPreference());
                return 0;
            case 27:
                setDisplayModeDirectorLoggingEnabled(false);
                return 0;
            case 28:
                setDisplayModeDirectorLoggingEnabled(true);
                return 0;
            case 29:
                String[] peekRemainingArgs = peekRemainingArgs();
                if (peekRemainingArgs == null) {
                    getErrPrintWriter().println("Error: no userDisabledHdrTypes specified");
                } else {
                    int[] iArr = new int[peekRemainingArgs.length];
                    try {
                        int length = peekRemainingArgs.length;
                        int i3 = 0;
                        int i4 = 0;
                        while (i3 < length) {
                            int i5 = i4 + 1;
                            iArr[i4] = Integer.parseInt(peekRemainingArgs[i3]);
                            i3++;
                            i4 = i5;
                        }
                        ((DisplayManager)
                                        this.mService
                                                .getContext()
                                                .getSystemService(DisplayManager.class))
                                .setUserDisabledHdrTypes(iArr);
                        return 0;
                    } catch (NumberFormatException unused9) {
                        getErrPrintWriter()
                                .println("Error: invalid format of userDisabledHdrTypes");
                    }
                }
                return 1;
            case 30:
                String nextArg16 = getNextArg();
                if (nextArg16 == null) {
                    getErrPrintWriter().println("Error: no rate specified");
                } else {
                    try {
                        String[] split = nextArg16.split(",");
                        f = Float.parseFloat(split[0]);
                        try {
                            f2 = Float.parseFloat(split[1]);
                        } catch (NumberFormatException unused10) {
                        }
                    } catch (NumberFormatException unused11) {
                        f = -1.0f;
                    }
                    if (f >= FullScreenMagnificationGestureHandler.MAX_SCALE
                            && f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        DisplayPowerController displayPowerController2 =
                                (DisplayPowerController)
                                        ((DisplayPowerControllerInterface)
                                                this.mService.mDisplayPowerControllers.get(0));
                        displayPowerController2.mBrightnessRampRateHdrIncrease = f;
                        displayPowerController2.mBrightnessRampRateHdrDecrease = f2;
                        return 0;
                    }
                    getErrPrintWriter().println("Error: rate should be a positive number");
                }
                return 1;
            case 31:
                if (getNextArg() != null) {
                    try {
                    } catch (NumberFormatException unused12) {
                        getErrPrintWriter().println("Error: invalid format of display ID");
                        return 1;
                    }
                }
                return 0;
            case ' ':
                setDisplayWhiteBalanceLoggingEnabled(false);
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Display manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  show-notification NOTIFICATION_TYPE");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Show notification for one of the following types: on-hotplug-error,"
                    + " on-link-training-failure, on-cable-dp-incapable",
                "  cancel-notifications",
                "    Cancel notifications.",
                "  set-brightness BRIGHTNESS");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Sets the current brightness to BRIGHTNESS (a number between 0 and 1).",
                "  reset-brightness-configuration",
                "    Reset the brightness to its default configuration.",
                "  ab-logging-enable");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Enable auto-brightness logging.",
                "  ab-logging-disable",
                "    Disable auto-brightness logging.",
                "  dwb-logging-enable");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Enable display white-balance logging.",
                "  dwb-logging-disable",
                "    Disable display white-balance logging.",
                "  dmd-logging-enable");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Enable display mode director logging.",
                "  dmd-logging-disable",
                "    Disable display mode director logging.",
                "  dwb-set-cct CCT");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Sets the ambient color temperature override to CCT (use -1 to disable).",
                "  get-ambient-brightness-info [lux]",
                "    Gets the ambient brightness thresholds and brightness info.",
                "  ab-short-term-reset");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Do short-term reset immediately.",
                "  ab-test-enable",
                "    Enable adaptive brightness test mode for sec long-term model.",
                "  ab-test-disable");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Disable adaptive brightness test mode for sec long-term model.",
                "  ab-add-brightness-weights"
                    + " [lux]:[brightness(0.0f~1.0f)]:[durationSec]:[continuity]",
                "    Add brightness weights directly  for sec long-term model.",
                "  set-user-preferred-display-mode WIDTH HEIGHT REFRESH-RATE DISPLAY_ID"
                    + " (optional)");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Sets the user preferred display mode which has fields WIDTH, HEIGHT and"
                    + " REFRESH-RATE. If DISPLAY_ID is passed, the mode change is applied to"
                    + " displaywith id = DISPLAY_ID, else mode change is applied globally.",
                "  clear-user-preferred-display-mode DISPLAY_ID (optional)",
                "    Clears the user preferred display mode. If DISPLAY_ID is passed, the mode is"
                    + " cleared for  display with id = DISPLAY_ID, else mode is cleared globally.",
                "  get-user-preferred-display-mode DISPLAY_ID (optional)");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Returns the user preferred display mode or null if no mode is set by user.If"
                    + " DISPLAY_ID is passed, the mode for display with id = DISPLAY_ID is"
                    + " returned, else global display mode is returned.",
                "  get-active-display-mode-at-start DISPLAY_ID",
                "    Returns the display mode which was found at boot time of display with id ="
                    + " DISPLAY_ID",
                "  set-match-content-frame-rate-pref PREFERENCE");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Sets the match content frame rate preference as PREFERENCE ",
                "  get-match-content-frame-rate-pref",
                "    Returns the match content frame rate preference",
                "  set-user-disabled-hdr-types TYPES...");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Sets the user disabled HDR types as TYPES",
                "  get-user-disabled-hdr-types",
                "    Returns the user disabled HDR types",
                "  get-displays [-c|--category CATEGORY] [-i|--ids-only] [-t|--type TYPE]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    [CATEGORY]",
                "    Returns the current displays. Can specify string category among",
                "    DisplayManager.DISPLAY_CATEGORY_*; must use the actual string value.",
                "    Can choose to print only the ids of the displays. Can filter by");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    display types. For example, '--type external'",
                "  dock",
                "    Sets brightness to docked + idle screen brightness mode",
                "  undock");
        outPrintWriter.println("    Sets brightness to active (normal) screen brightness mode");
        if (this.mFlags.mConnectedDisplayManagementFlagState.isEnabled()) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  enable-display DISPLAY_ID",
                    "    Enable the DISPLAY_ID. Only possible if this is a connected display.",
                    "  disable-display DISPLAY_ID",
                    "    Disable the DISPLAY_ID. Only possible if this is a connected display.");
        }
        outPrintWriter.println();
        Intent.printIntentArgsHelp(outPrintWriter, "");
    }

    public final int requestDisplayPower(boolean z) {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no displayId specified");
            return 1;
        }
        try {
            this.mService.requestDisplayPower(Integer.parseInt(nextArg), z);
            return 0;
        } catch (NumberFormatException unused) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    getErrPrintWriter(), "Error: invalid displayId: '", nextArg, "'");
            return 1;
        }
    }

    public final void setAutoBrightnessLoggingEnabled(boolean z) {
        AutomaticBrightnessController automaticBrightnessController;
        DisplayManagerService displayManagerService = this.mService;
        synchronized (displayManagerService.mSyncRoot) {
            DisplayPowerControllerInterface displayPowerControllerInterface =
                    (DisplayPowerControllerInterface)
                            displayManagerService.mDisplayPowerControllers.get(0);
            if (displayPowerControllerInterface != null
                    && (automaticBrightnessController =
                                    ((DisplayPowerController) displayPowerControllerInterface)
                                            .mAutomaticBrightnessController)
                            != null
                    && automaticBrightnessController.mLoggingEnabled != z) {
                for (int i = 0;
                        i < automaticBrightnessController.mBrightnessMappingStrategyMap.size();
                        i++) {
                    BrightnessMappingStrategy brightnessMappingStrategy =
                            (BrightnessMappingStrategy)
                                    automaticBrightnessController.mBrightnessMappingStrategyMap
                                            .valueAt(i);
                    if (brightnessMappingStrategy.mLoggingEnabled != z) {
                        brightnessMappingStrategy.mLoggingEnabled = z;
                        if (BrightnessMappingStrategy.UserOffsetManager.sDebugLogging != z) {
                            BrightnessMappingStrategy.UserOffsetManager.sDebugLogging = z;
                        }
                    }
                }
                automaticBrightnessController.mLoggingEnabled = z;
            }
        }
    }

    public final int setDisplayEnabled(boolean z) {
        if (!this.mFlags.mConnectedDisplayManagementFlagState.isEnabled()) {
            getErrPrintWriter()
                    .println("Error: external display management is not available on this device.");
            return 1;
        }
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no displayId specified");
            return 1;
        }
        try {
            this.mService.enableConnectedDisplay(Integer.parseInt(nextArg), z);
            return 0;
        } catch (NumberFormatException unused) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    getErrPrintWriter(), "Error: invalid displayId: '", nextArg, "'");
            return 1;
        }
    }

    public final void setDisplayModeDirectorLoggingEnabled(boolean z) {
        DisplayManagerService displayManagerService = this.mService;
        synchronized (displayManagerService.mSyncRoot) {
            DisplayModeDirector displayModeDirector = displayManagerService.mDisplayModeDirector;
            if (displayModeDirector.mLoggingEnabled != z) {
                displayModeDirector.mLoggingEnabled = z;
                DisplayModeDirector.BrightnessObserver brightnessObserver =
                        displayModeDirector.mBrightnessObserver;
                if (brightnessObserver.mLoggingEnabled != z) {
                    brightnessObserver.mLoggingEnabled = z;
                    DisplayModeDirector.BrightnessObserver.LightSensorEventListener
                            lightSensorEventListener = brightnessObserver.mLightSensorListener;
                    if (lightSensorEventListener.mLoggingEnabled != z) {
                        lightSensorEventListener.mLoggingEnabled = z;
                    }
                }
                displayModeDirector.mSkinThermalStatusObserver.mLoggingEnabled = z;
                displayModeDirector.mVotesStorage.mLoggingEnabled = z;
            }
        }
    }

    public final void setDisplayWhiteBalanceLoggingEnabled(boolean z) {
        DisplayManagerService displayManagerService = this.mService;
        synchronized (displayManagerService.mSyncRoot) {
            DisplayPowerControllerInterface displayPowerControllerInterface =
                    (DisplayPowerControllerInterface)
                            displayManagerService.mDisplayPowerControllers.get(0);
            if (displayPowerControllerInterface != null) {
                Message obtainMessage =
                        ((DisplayPowerController) displayPowerControllerInterface)
                                .mHandler.obtainMessage();
                obtainMessage.what = 16;
                obtainMessage.arg1 = z ? 1 : 0;
                obtainMessage.sendToTarget();
            }
        }
    }

    public final void setTestModeEnabled(boolean z) {
        if (!PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
            getErrPrintWriter().println("Error: available for sec long-term model");
        }
        DisplayManagerService displayManagerService = this.mService;
        synchronized (displayManagerService.mSyncRoot) {
            AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                    ((DisplayPowerController)
                                    ((DisplayPowerControllerInterface)
                                            displayManagerService.mDisplayPowerControllers.get(0)))
                            .mAdaptiveBrightnessLongtermModelBuilder;
            if (adaptiveBrightnessLongtermModelBuilder != null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "setTestModeEnabled:", "AdaptiveBrightnessLongtermModelBuilder", z);
                if (adaptiveBrightnessLongtermModelBuilder.mTestModeEnabled != z) {
                    adaptiveBrightnessLongtermModelBuilder.mTestModeEnabled = z;
                    AdaptiveBrightnessLongtermModelBuilder.ModelBuilderHandler modelBuilderHandler =
                            adaptiveBrightnessLongtermModelBuilder.mBgHandler;
                    if (z) {
                        modelBuilderHandler.obtainMessage(2).sendToTarget();
                    } else {
                        modelBuilderHandler.obtainMessage(3).sendToTarget();
                    }
                }
            }
        }
    }
}
