package com.android.server.input;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Pair;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public final class InputShellCommand extends ShellCommand {
    public static final Map MODIFIER;
    public static final Map SOURCES;
    public final BiConsumer mInputEventInjector;

    static {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(113, 12288);
        arrayMap.put(114, 20480);
        arrayMap.put(57, 18);
        arrayMap.put(58, 34);
        arrayMap.put(59, 65);
        arrayMap.put(60, 129);
        arrayMap.put(117, 196608);
        arrayMap.put(118, 327680);
        MODIFIER = Collections.unmodifiableMap(arrayMap);
        ArrayMap arrayMap2 = new ArrayMap();
        arrayMap2.put(
                "keyboard",
                Integer.valueOf(
                        FrameworkStatsLog
                                .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP));
        arrayMap2.put("dpad", 513);
        arrayMap2.put("gamepad", 1025);
        arrayMap2.put("touchscreen", 4098);
        arrayMap2.put("mouse", 8194);
        arrayMap2.put("stylus", 16386);
        arrayMap2.put("trackball", 65540);
        arrayMap2.put("touchpad", 1048584);
        arrayMap2.put("touchnavigation", 2097152);
        arrayMap2.put("joystick", 16777232);
        arrayMap2.put("rotaryencoder", 4194304);
        SOURCES = Collections.unmodifiableMap(arrayMap2);
    }

    public InputShellCommand(BiConsumer biConsumer) {
        this.mInputEventInjector = biConsumer;
    }

    public static void sleep(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public final void injectKeyEvent(KeyEvent keyEvent, boolean z) {
        this.mInputEventInjector.accept(keyEvent, Integer.valueOf(z ? 0 : 2));
    }

    public final void injectMotionEvent(
            int i, int i2, long j, long j2, float f, float f2, float f3, int i3) {
        injectMotionEvent(
                i,
                i2,
                j,
                j2,
                Map.of(0, Float.valueOf(f), 1, Float.valueOf(f2), 2, Float.valueOf(f3)),
                i3);
    }

    public final void injectMotionEvent(int i, int i2, long j, long j2, Map map, int i3) {
        int i4;
        int i5;
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        MotionEvent.PointerProperties[] pointerPropertiesArr = {pointerProperties};
        pointerProperties.id = 0;
        switch (i) {
            case 4098:
            case 1048584:
            case 2097152:
                i4 = 1;
                break;
            case 8194:
            case 65540:
            case 131076:
                i4 = 3;
                break;
            case 16386:
            case 49154:
                i4 = 2;
                break;
            default:
                i4 = 0;
                break;
        }
        pointerProperties.toolType = i4;
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        MotionEvent.PointerCoords[] pointerCoordsArr = {pointerCoords};
        pointerCoords.size = 1.0f;
        for (Map.Entry entry : map.entrySet()) {
            pointerCoordsArr[0].setAxisValue(
                    ((Integer) entry.getKey()).intValue(), ((Float) entry.getValue()).floatValue());
        }
        int i6 = (i3 != -1 || (i & 2) == 0) ? i3 : 0;
        int[] deviceIds = InputDevice.getDeviceIds();
        int length = deviceIds.length;
        int i7 = 0;
        while (true) {
            if (i7 < length) {
                int i8 = deviceIds[i7];
                if (InputDevice.getDevice(i8).supportsSource(i)) {
                    i5 = i8;
                } else {
                    i7++;
                }
            } else {
                i5 = 0;
            }
        }
        this.mInputEventInjector.accept(
                MotionEvent.obtain(
                        j,
                        j2,
                        i2,
                        1,
                        pointerPropertiesArr,
                        pointerCoordsArr,
                        0,
                        0,
                        1.0f,
                        1.0f,
                        i5,
                        0,
                        i,
                        i6,
                        0),
                2);
    }

    public final int onCommand(String str) {
        int i;
        String str2 = str;
        Map map = SOURCES;
        if (map.containsKey(str2)) {
            i = ((Integer) map.get(str2)).intValue();
            str2 = getNextArgRequired();
        } else {
            i = 0;
        }
        int i2 = -1;
        if ("-d".equals(str2)) {
            String nextArgRequired = getNextArgRequired();
            if (!"INVALID_DISPLAY".equalsIgnoreCase(nextArgRequired)) {
                if ("DEFAULT_DISPLAY".equalsIgnoreCase(nextArgRequired)) {
                    i2 = 0;
                } else {
                    try {
                        int parseInt = Integer.parseInt(nextArgRequired);
                        if (parseInt != -1) {
                            i2 = Math.max(parseInt, 0);
                        }
                    } catch (NumberFormatException unused) {
                        throw new IllegalArgumentException(
                                "Error: Invalid arguments for display ID.");
                    }
                }
            }
            str2 = getNextArgRequired();
        }
        String str3 = str2;
        int i3 = i2;
        try {
            if ("text".equals(str3)) {
                runText(i, i3);
            } else if ("keyevent".equals(str3)) {
                runKeyEvent(i, i3);
            } else if ("tap".equals(str3)) {
                int i4 = i == 0 ? 4098 : i;
                float parseFloat = Float.parseFloat(getNextArgRequired());
                float parseFloat2 = Float.parseFloat(getNextArgRequired());
                long uptimeMillis = SystemClock.uptimeMillis();
                injectMotionEvent(
                        i4, 0, uptimeMillis, uptimeMillis, parseFloat, parseFloat2, 1.0f, i3);
                injectMotionEvent(
                        i4,
                        1,
                        uptimeMillis,
                        uptimeMillis,
                        parseFloat,
                        parseFloat2,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        i3);
            } else if ("swipe".equals(str3)) {
                if (i == 0) {
                    i = 4098;
                }
                sendSwipe(i, i3, false);
            } else if ("draganddrop".equals(str3)) {
                if (i == 0) {
                    i = 4098;
                }
                sendSwipe(i, i3, true);
            } else if ("press".equals(str3)) {
                int i5 = i == 0 ? 65540 : i;
                long uptimeMillis2 = SystemClock.uptimeMillis();
                injectMotionEvent(
                        i5,
                        0,
                        uptimeMillis2,
                        uptimeMillis2,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        1.0f,
                        i3);
                injectMotionEvent(
                        i5,
                        1,
                        uptimeMillis2,
                        uptimeMillis2,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        i3);
            } else if ("roll".equals(str3)) {
                if (i == 0) {
                    i = 65540;
                }
                float parseFloat3 = Float.parseFloat(getNextArgRequired());
                float parseFloat4 = Float.parseFloat(getNextArgRequired());
                long uptimeMillis3 = SystemClock.uptimeMillis();
                injectMotionEvent(
                        i,
                        2,
                        uptimeMillis3,
                        uptimeMillis3,
                        parseFloat3,
                        parseFloat4,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        i3);
            } else if ("scroll".equals(str3)) {
                runScroll(i, i3);
            } else if ("motionevent".equals(str3)) {
                runMotionEvent(i, i3);
            } else if ("keycombination".equals(str3)) {
                runKeyCombination(i, i3);
            } else {
                handleDefaultCommands(str3);
            }
            return 0;
        } catch (NumberFormatException unused2) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Error: Invalid arguments for command: ", str3));
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("Usage: input [<source>] [-d DISPLAY_ID] <command> [<arg>...]");
            outPrintWriter.println();
            outPrintWriter.println("The sources are: ");
            Iterator it = SOURCES.keySet().iterator();
            while (it.hasNext()) {
                outPrintWriter.println("      " + ((String) it.next()));
            }
            outPrintWriter.println(
                    "[axis_value] represents an option specifying the value of a given axis ");
            outPrintWriter.println(
                    "      The syntax is as follows: --axis <axis_name>,<axis_value>");
            outPrintWriter.println(
                    "            where <axis_name> is the name of the axis as defined in ");
            outPrintWriter.println(
                    "            MotionEvent without the AXIS_ prefix (e.g. SCROLL, X)");
            outPrintWriter.println(
                    "      Sample [axis_values] entry: `--axis Y,3`, `--axis SCROLL,-2`");
            outPrintWriter.println();
            outPrintWriter.printf(
                    "-d: specify the display ID.\n"
                        + "      (Default: %d for key event, %d for motion event if not"
                        + " specified.)",
                    -1, 0);
            outPrintWriter.println();
            outPrintWriter.println("The commands and default sources are:");
            outPrintWriter.println("      text <string> (Default: keyboard)");
            outPrintWriter.println(
                    "      keyevent [--longpress|--duration <duration to hold key down in ms>]"
                        + " [--doubletap] [--async] [--delay <duration between keycodes in ms>]"
                        + " <key code number or name> ... (Default: keyboard)");
            outPrintWriter.println("      tap <x> <y> (Default: touchscreen)");
            outPrintWriter.println(
                    "      swipe <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen)");
            outPrintWriter.println(
                    "      draganddrop <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen)");
            outPrintWriter.println("      press (Default: trackball)");
            outPrintWriter.println("      roll <dx> <dy> (Default: trackball)");
            outPrintWriter.println(
                    "      motionevent <DOWN|UP|MOVE|CANCEL> <x> <y> (Default: touchscreen)");
            outPrintWriter.println(
                    "      scroll (Default: rotaryencoder). Has the following syntax:");
            outPrintWriter.println(
                    "            scroll <x> <y> [axis_value] (for pointer-based sources)");
            outPrintWriter.println(
                    "            scroll [axis_value] (for non-pointer-based sources)");
            outPrintWriter.println("            Axis options: SCROLL, HSCROLL, VSCROLL");
            outPrintWriter.println(
                    "            None or one or multiple axis value options can be specified.");
            outPrintWriter.println(
                    "            To specify multiple axes, use one axis option for per axis.");
            outPrintWriter.println(
                    "            Example: `scroll --axis VSCROLL,2 --axis SCROLL,-2.4`");
            outPrintWriter.println(
                    "      keycombination [-t duration(ms)] <key code 1> <key code 2> ... (Default:"
                        + " keyboard, the key order is important here.)");
            outPrintWriter.close();
        } catch (Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void runKeyCombination(int i, int i2) {
        long j;
        String nextArgRequired = getNextArgRequired();
        if ("-t".equals(nextArgRequired)) {
            long parseInt = Integer.parseInt(getNextArgRequired());
            nextArgRequired = getNextArgRequired();
            j = parseInt;
        } else {
            j = 0;
        }
        IntArray intArray = new IntArray();
        while (nextArgRequired != null) {
            int keyCodeFromString = KeyEvent.keyCodeFromString(nextArgRequired);
            if (keyCodeFromString == 0) {
                throw new IllegalArgumentException("Unknown keycode: ".concat(nextArgRequired));
            }
            intArray.add(keyCodeFromString);
            nextArgRequired = getNextArg();
        }
        if (intArray.size() < 2) {
            throw new IllegalArgumentException("keycombination requires at least 2 keycodes");
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int size = intArray.size();
        KeyEvent[] keyEventArr = new KeyEvent[size];
        Integer num = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            int i5 = intArray.get(i3);
            int i6 = i3;
            KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, 0, i5, 0, i4, -1, 0, 0, i);
            keyEvent.setDisplayId(i2);
            keyEventArr[i6] = keyEvent;
            num = num;
            i4 |= ((Integer) MODIFIER.getOrDefault(Integer.valueOf(i5), num)).intValue();
            i3 = i6 + 1;
        }
        for (int i7 = 0; i7 < size; i7++) {
            injectKeyEvent(keyEventArr[i7], true);
        }
        sleep(j);
        int i8 = 0;
        while (i8 < size) {
            int keyCode = keyEventArr[i8].getKeyCode();
            Integer num2 = num;
            injectKeyEvent(
                    new KeyEvent(uptimeMillis, uptimeMillis, 1, keyCode, 0, i4, -1, 0, 0, i), true);
            i4 &= ~((Integer) MODIFIER.getOrDefault(Integer.valueOf(keyCode), num2)).intValue();
            i8++;
            num = num2;
        }
    }

    public final void runKeyEvent(int i, int i2) {
        boolean z;
        String nextArgRequired = getNextArgRequired();
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        long j = 0;
        long j2 = 0;
        do {
            z = true;
            if (!nextArgRequired.startsWith("--")) {
                break;
            }
            z2 = z2 || nextArgRequired.equals("--longpress");
            z3 = z3 || nextArgRequired.equals("--async");
            z4 = z4 || nextArgRequired.equals("--doubletap");
            if (nextArgRequired.equals("--delay")) {
                j = Long.parseLong(getNextArgRequired());
            } else if (nextArgRequired.equals("--duration")) {
                j2 = Long.parseLong(getNextArgRequired());
            }
            nextArgRequired = getNextArg();
        } while (nextArgRequired != null);
        boolean z5 = z3;
        boolean z6 = z4;
        long j3 = j;
        if (j2 > 0 && z2) {
            getErrPrintWriter()
                    .println("--duration and --longpress cannot be used at the same time.");
            throw new IllegalArgumentException(
                    "keyevent args should only contain either durationMs or longPress");
        }
        if (z2) {
            j2 = ViewConfiguration.getLongPressTimeout();
        }
        long j4 = j2;
        while (true) {
            if (!z && j3 > 0) {
                sleep(j3);
            }
            int keyCodeFromString = KeyEvent.keyCodeFromString(nextArgRequired);
            sendKeyEvent(j4, i, keyCodeFromString, i2, z5);
            if (z6) {
                sleep(ViewConfiguration.getDoubleTapMinTime());
                sendKeyEvent(j4, i, keyCodeFromString, i2, z5);
            }
            nextArgRequired = getNextArg();
            if (nextArgRequired == null) {
                return;
            } else {
                z = false;
            }
        }
    }

    public final void runMotionEvent(int i, int i2) {
        String nextArgRequired;
        int i3;
        float parseFloat;
        float parseFloat2;
        float f;
        float f2;
        int i4 = i == 0 ? 4098 : i;
        nextArgRequired = getNextArgRequired();
        String upperCase = nextArgRequired.toUpperCase();
        upperCase.getClass();
        switch (upperCase) {
            case "UP":
                i3 = 1;
                break;
            case "DOWN":
                i3 = 0;
                break;
            case "MOVE":
                i3 = 2;
                break;
            case "CANCEL":
                i3 = 3;
                break;
            default:
                throw new IllegalArgumentException("Unknown action: ".concat(nextArgRequired));
        }
        float f3 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        if (i3 == 0 || i3 == 2 || i3 == 1) {
            parseFloat = Float.parseFloat(getNextArgRequired());
            parseFloat2 = Float.parseFloat(getNextArgRequired());
        } else {
            String nextArg = getNextArg();
            String nextArg2 = getNextArg();
            if (nextArg == null || nextArg2 == null) {
                f2 = 0.0f;
                f = 0.0f;
                if (i3 != 0 || i3 == 2) {
                    f3 = 1.0f;
                }
                float f4 = f3;
                long uptimeMillis = SystemClock.uptimeMillis();
                injectMotionEvent(i4, i3, uptimeMillis, uptimeMillis, f2, f, f4, i2);
            }
            parseFloat = Float.parseFloat(nextArg);
            parseFloat2 = Float.parseFloat(nextArg2);
        }
        f2 = parseFloat;
        f = parseFloat2;
        if (i3 != 0) {}
        f3 = 1.0f;
        float f42 = f3;
        long uptimeMillis2 = SystemClock.uptimeMillis();
        injectMotionEvent(i4, i3, uptimeMillis2, uptimeMillis2, f2, f, f42, i2);
    }

    public final void runScroll(int i, int i2) {
        if (i == 0) {
            i = 4194304;
        }
        int i3 = i;
        boolean z = (i3 & 2) == 2;
        HashMap hashMap = new HashMap();
        if (z) {
            hashMap.put(0, Float.valueOf(Float.parseFloat(getNextArgRequired())));
            hashMap.put(1, Float.valueOf(Float.parseFloat(getNextArgRequired())));
        }
        Set of = Set.of(10, 9, 26);
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                injectMotionEvent(i3, 8, uptimeMillis, uptimeMillis, hashMap, i2);
                return;
            }
            if (!nextOption.equals("--axis")) {
                throw new IllegalArgumentException("Unsupported option: ".concat(nextOption));
            }
            String nextArgRequired = getNextArgRequired();
            String[] split = nextArgRequired.split(",");
            if (split.length != 2) {
                throw new IllegalArgumentException(
                        "Invalid --axis option value: ".concat(nextArgRequired));
            }
            String str = "AXIS_" + split[0];
            int axisFromString = MotionEvent.axisFromString(str);
            if (axisFromString == -1) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Invalid axis name: ", str));
            }
            if (!of.contains(Integer.valueOf(axisFromString))) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Unsupported axis: ", str));
            }
            Pair create =
                    Pair.create(
                            Integer.valueOf(axisFromString),
                            Float.valueOf(Float.parseFloat(split[1])));
            hashMap.put((Integer) create.first, (Float) create.second);
        }
    }

    public final void runText(int i, int i2) {
        if (i == 0) {
            i = FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP;
        }
        StringBuilder sb = new StringBuilder(getNextArgRequired());
        int i3 = 0;
        boolean z = false;
        while (i3 < sb.length()) {
            if (z) {
                if (sb.charAt(i3) == 's') {
                    sb.setCharAt(i3, ' ');
                    i3--;
                    sb.deleteCharAt(i3);
                }
                z = false;
            }
            if (sb.charAt(i3) == '%') {
                z = true;
            }
            i3++;
        }
        for (KeyEvent keyEvent : KeyCharacterMap.load(-1).getEvents(sb.toString().toCharArray())) {
            if (i != keyEvent.getSource()) {
                keyEvent.setSource(i);
            }
            keyEvent.setDisplayId(i2);
            injectKeyEvent(keyEvent, false);
        }
    }

    public final void sendKeyEvent(long j, int i, int i2, int i3, boolean z) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, 0, i2, 0, 0, -1, 0, 0, i);
        keyEvent.setDisplayId(i3);
        injectKeyEvent(keyEvent, z);
        long min = Math.min(j, ViewConfiguration.getLongPressTimeout());
        if (min > 0) {
            sleep(min);
            if (j >= ViewConfiguration.getLongPressTimeout()) {
                injectKeyEvent(
                        KeyEvent.changeTimeRepeat(
                                keyEvent,
                                uptimeMillis + ViewConfiguration.getLongPressTimeout(),
                                1,
                                128),
                        z);
                long j2 = j - min;
                if (j2 > 0) {
                    sleep(j2);
                }
            }
        }
        injectKeyEvent(KeyEvent.changeAction(keyEvent, 1), z);
    }

    public final void sendSwipe(int i, int i2, boolean z) {
        float parseFloat = Float.parseFloat(getNextArgRequired());
        float parseFloat2 = Float.parseFloat(getNextArgRequired());
        float parseFloat3 = Float.parseFloat(getNextArgRequired());
        float parseFloat4 = Float.parseFloat(getNextArgRequired());
        String nextArg = getNextArg();
        int parseInt = nextArg != null ? Integer.parseInt(nextArg) : -1;
        if (parseInt < 0) {
            parseInt = 300;
        }
        int i3 = parseInt;
        long uptimeMillis = SystemClock.uptimeMillis();
        injectMotionEvent(i, 0, uptimeMillis, uptimeMillis, parseFloat, parseFloat2, 1.0f, i2);
        if (z) {
            sleep(ViewConfiguration.getLongPressTimeout());
        }
        long j = uptimeMillis + i3;
        long uptimeMillis2 = SystemClock.uptimeMillis();
        while (uptimeMillis2 < j) {
            float f = (uptimeMillis2 - uptimeMillis) / i3;
            injectMotionEvent(
                    i,
                    2,
                    uptimeMillis,
                    uptimeMillis2,
                    ((parseFloat3 - parseFloat) * f) + parseFloat,
                    ((parseFloat4 - parseFloat2) * f) + parseFloat2,
                    1.0f,
                    i2);
            uptimeMillis2 = SystemClock.uptimeMillis();
        }
        injectMotionEvent(
                i,
                1,
                uptimeMillis,
                uptimeMillis2,
                parseFloat3,
                parseFloat4,
                FullScreenMagnificationGestureHandler.MAX_SCALE,
                i2);
    }
}
