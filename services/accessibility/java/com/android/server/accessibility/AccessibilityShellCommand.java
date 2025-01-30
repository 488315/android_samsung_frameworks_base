package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.wm.WindowManagerInternal;
import java.io.PrintWriter;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class AccessibilityShellCommand extends ShellCommand {
    public final AccessibilityManagerService mService;
    public final SystemActionPerformer mSystemActionPerformer;
    public final WindowManagerInternal mWindowManagerService = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);

    public AccessibilityShellCommand(AccessibilityManagerService accessibilityManagerService, SystemActionPerformer systemActionPerformer) {
        this.mService = accessibilityManagerService;
        this.mSystemActionPerformer = systemActionPerformer;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -1929073156:
                if (str.equals("notify-accessibilityevent-to-accessibilityservice")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1880035385:
                if (str.equals("notify-key-to-accessibilityservice")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1566500224:
                if (str.equals("toggle-accessibilityservice")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1479019855:
                if (str.equals("notify-gesture-to-accessibilityservice")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -859068373:
                if (str.equals("get-bind-instant-service-allowed")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3198785:
                if (str.equals("help")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 80873113:
                if (str.equals("send-touchevent-to-accessibilityinputfilter")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 789489311:
                if (str.equals("set-bind-instant-service-allowed")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1340897306:
                if (str.equals("start-trace")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1568918806:
                if (str.equals("toggle-accessibility-sec-debug-log")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1748820581:
                if (str.equals("call-system-action")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1857979322:
                if (str.equals("stop-trace")) {
                    c = 11;
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
                return runNotifyAccessibilityEvent();
            case 1:
                return runNotifyKeyEvent();
            case 2:
                return runToggleAccessibilityService();
            case 3:
                return runNotifyGestureEvent();
            case 4:
                return runGetBindInstantServiceAllowed();
            case 5:
                onHelp();
                return 0;
            case 6:
                return runSendMotionEventToInputFilter();
            case 7:
                return runSetBindInstantServiceAllowed();
            case '\b':
            case 11:
                return this.mService.getTraceManager().onShellCommand(str, this);
            case '\t':
                return runToggleSecDebugLog();
            case '\n':
                return runCallSystemAction();
            default:
                return -1;
        }
    }

    public final int runGetBindInstantServiceAllowed() {
        Integer parseUserId = parseUserId();
        if (parseUserId == null) {
            return -1;
        }
        getOutPrintWriter().println(Boolean.toString(this.mService.getBindInstantServiceAllowed(parseUserId.intValue())));
        return 0;
    }

    public final int runSetBindInstantServiceAllowed() {
        Integer parseUserId = parseUserId();
        if (parseUserId == null) {
            return -1;
        }
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error: no true/false specified");
            return -1;
        }
        this.mService.setBindInstantServiceAllowed(parseUserId.intValue(), Boolean.parseBoolean(nextArgRequired));
        return 0;
    }

    public final int runCallSystemAction() {
        String nextArg;
        int callingUid = Binder.getCallingUid();
        if ((callingUid != 0 && callingUid != 1000 && callingUid != 2000) || (nextArg = getNextArg()) == null) {
            return -1;
        }
        this.mSystemActionPerformer.performSystemAction(Integer.parseInt(nextArg));
        return 0;
    }

    public final Integer parseUserId() {
        String nextOption = getNextOption();
        if (nextOption != null) {
            if (nextOption.equals("--user")) {
                return Integer.valueOf(UserHandle.parseUserArg(getNextArgRequired()));
            }
            getErrPrintWriter().println("Unknown option: " + nextOption);
            return null;
        }
        return Integer.valueOf(ActivityManager.getCurrentUser());
    }

    public final boolean acceptCustomAccessibilityCommand() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000 && callingUid != 2000) {
            getErrPrintWriter().println("Error : Wrong process");
            return false;
        }
        if (isDebuggableBinary()) {
            return true;
        }
        getErrPrintWriter().println("Error : Wrong binary");
        return false;
    }

    public final boolean isDebuggableBinary() {
        String str = Build.TYPE;
        return "eng".equals(str) || "userdebug".equals(str);
    }

    public final int runNotifyGestureEvent() {
        if (!acceptCustomAccessibilityCommand()) {
            return -1;
        }
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error : need gesture id");
            return -1;
        }
        int parseInt = Integer.parseInt(nextArgRequired);
        String nextArg = getNextArg();
        this.mService.onGesture(new AccessibilityGestureEvent(parseInt, nextArg != null ? Integer.parseInt(nextArg) : 0));
        return 0;
    }

    public final int runNotifyKeyEvent() {
        if (!acceptCustomAccessibilityCommand()) {
            return -1;
        }
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error : need key code");
            return -1;
        }
        String nextArg = getNextArg();
        int parseInt = Integer.parseInt(nextArgRequired);
        if (nextArg == null) {
            long currentTimeMillis = System.currentTimeMillis();
            this.mService.notifyKeyEvent(new KeyEvent(currentTimeMillis, currentTimeMillis, 0, parseInt, 0), 0);
            this.mService.notifyKeyEvent(new KeyEvent(currentTimeMillis, currentTimeMillis + 10, 1, parseInt, 0), 0);
        } else {
            int parseInt2 = Integer.parseInt(nextArg);
            long currentTimeMillis2 = System.currentTimeMillis();
            this.mService.notifyKeyEvent(new KeyEvent(currentTimeMillis2, currentTimeMillis2, parseInt2, parseInt, 0), 0);
        }
        return 0;
    }

    public final int runNotifyAccessibilityEvent() {
        if (!acceptCustomAccessibilityCommand()) {
            return -1;
        }
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error : need event type");
            return -1;
        }
        String nextArg = getNextArg();
        AccessibilityEvent accessibilityEvent = new AccessibilityEvent(Integer.parseInt(nextArgRequired));
        if (nextArg != null) {
            accessibilityEvent.setPackageName(nextArg);
        }
        this.mService.sendAccessibilityEventForCurrentUserLocked(accessibilityEvent);
        return 0;
    }

    public final int runToggleAccessibilityService() {
        if (!acceptCustomAccessibilityCommand()) {
            return -1;
        }
        String nextArgRequired = getNextArgRequired();
        String nextArgRequired2 = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error : need package name");
            return -1;
        }
        if (nextArgRequired2 == null) {
            getErrPrintWriter().println("Error : need class name");
            return -1;
        }
        setAccessibilityServiceState(this.mService.getContext(), new ComponentName(nextArgRequired, nextArgRequired2));
        return 0;
    }

    public final void setAccessibilityServiceState(Context context, ComponentName componentName) {
        ArraySet arraySet = new ArraySet();
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        if (!TextUtils.isEmpty(string)) {
            for (String str : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString != null) {
                    arraySet.add(unflattenFromString);
                }
            }
        }
        if (arraySet.contains(componentName)) {
            arraySet.remove(componentName);
        } else {
            arraySet.add(componentName);
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString());
            sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        Settings.Secure.putString(context.getContentResolver(), "enabled_accessibility_services", sb.toString());
    }

    public final int runToggleSecDebugLog() {
        if (!acceptCustomAccessibilityCommand()) {
            return -1;
        }
        this.mService.setSecDebug(!r0.isSecDebugEnabled());
        getOutPrintWriter().println(this.mService.isSecDebugEnabled() ? "enabled" : "disabled");
        return 0;
    }

    public final int runSendMotionEventToInputFilter() {
        if (!acceptCustomAccessibilityCommand()) {
            return -1;
        }
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error : need x value for MotionEvent");
            return -1;
        }
        String nextArgRequired2 = getNextArgRequired();
        if (nextArgRequired2 == null) {
            getErrPrintWriter().println("Error : need y value for MotionEvent");
            return -1;
        }
        float parseFloat = Float.parseFloat(nextArgRequired);
        float parseFloat2 = Float.parseFloat(nextArgRequired2);
        String nextArg = getNextArg();
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = 0;
        pointerProperties.toolType = 1;
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = parseFloat;
        pointerCoords.y = parseFloat2;
        pointerCoords.pressure = 1.0f;
        pointerCoords.size = 1.0f;
        MotionEvent.PointerCoords[] pointerCoordsArr = {pointerCoords};
        MotionEvent.PointerProperties[] pointerPropertiesArr = {pointerProperties};
        if (nextArg == null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mService.injectInputEventToInputFilter(MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 5, 0, 4098, 0), 1073741824);
            this.mService.injectInputEventToInputFilter(MotionEvent.obtain(uptimeMillis, SystemClock.uptimeMillis(), 1, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 5, 0, 4098, 0), 1073741824);
        } else {
            int parseInt = Integer.parseInt(nextArg);
            long uptimeMillis2 = SystemClock.uptimeMillis();
            this.mService.injectInputEventToInputFilter(MotionEvent.obtain(uptimeMillis2, uptimeMillis2, parseInt, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 5, 0, 4098, 0), 1073741824);
        }
        return 0;
    }

    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Accessibility service (accessibility) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-bind-instant-service-allowed [--user <USER_ID>] true|false ");
        outPrintWriter.println("    Set whether binding to services provided by instant apps is allowed.");
        outPrintWriter.println("  get-bind-instant-service-allowed [--user <USER_ID>]");
        outPrintWriter.println("    Get whether binding to services provided by instant apps is allowed.");
        outPrintWriter.println("  call-system-action <ACTION_ID>");
        outPrintWriter.println("    Calls the system action with the given action id.");
        this.mService.getTraceManager().onHelp(outPrintWriter);
        if (isDebuggableBinary()) {
            outPrintWriter.println("  notify-gesture-to-accessibilityservice <GESTURE_ID>");
            outPrintWriter.println("    Notifys the gesture event with the given gesture id.");
            outPrintWriter.println("  notify-key-to-accessibilityservice <KEY_CODE> <(optional) ACTION>");
            outPrintWriter.println("    Notifys the key event with the given key code.");
            outPrintWriter.println("    If the ACTION is empty, down and up key event will be notified");
            outPrintWriter.println("  notify-accessibilityevent-to-accessibilityservice <EVENT_TYPE> <(optional) PACKAGE_NAME>");
            outPrintWriter.println("    Notifys the accessibility event with given event type");
            outPrintWriter.println("  toggle-accessibilityservice <PACKAGE_NAME> <CLASS_NAME>");
            outPrintWriter.println("    Toggles the given AccessibilityService");
            outPrintWriter.println("    for example, talkback");
            outPrintWriter.println("      PACKAGE NAME : com.google.android.marvin.talkback");
            outPrintWriter.println("      CLASS_NAME : com.google.android.marvin.talkback.TalkBackService");
            outPrintWriter.println("  toggle-accessibility-sec-debug-log");
            outPrintWriter.println("    Toggles sec debug log of Accessibility framework");
        }
    }
}
