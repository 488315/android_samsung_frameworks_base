package com.android.server.accessibility;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.ShellCommand;
import android.os.UserHandle;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class AccessibilityShellCommand extends ShellCommand {
    public final Context mContext;
    public final AccessibilityManagerService mService;
    public final SystemActionPerformer mSystemActionPerformer;

    public AccessibilityShellCommand(
            Context context,
            AccessibilityManagerService accessibilityManagerService,
            SystemActionPerformer systemActionPerformer) {
        this.mContext = context;
        this.mService = accessibilityManagerService;
        this.mSystemActionPerformer = systemActionPerformer;
    }

    public final boolean acceptCustomAccessibilityCommand() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000 && callingUid != 2000) {
            getErrPrintWriter().println("Error : Wrong process");
            return false;
        }
        String str = Build.TYPE;
        if ("eng".equals(str) || "userdebug".equals(str)) {
            return true;
        }
        getErrPrintWriter().println("Error : Wrong binary");
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:229:0x0408, code lost:

       if (r1.equals("write") == false) goto L224;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r25) {
        /*
            Method dump skipped, instructions count: 1464
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.AccessibilityShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Accessibility service (accessibility) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-bind-instant-service-allowed [--user <USER_ID>] true|false ");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Set whether binding to services provided by instant apps is allowed.",
                "  get-bind-instant-service-allowed [--user <USER_ID>]",
                "    Get whether binding to services provided by instant apps is allowed.",
                "  call-system-action <ACTION_ID>");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Calls the system action with the given action id.",
                "  check-hidraw [read|write|descriptor] <HIDRAW_NODE_PATH>",
                "    Checks if the system can perform various actions on the HIDRAW node.");
        this.mService.mTraceManager.getClass();
        outPrintWriter.println("  start-trace [-t LOGGING_TYPE [LOGGING_TYPE...]]");
        outPrintWriter.println(
                "    Start the debug tracing. If no option is present, full trace will be");
        outPrintWriter.println("    generated. Options are:");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      -t: Only generate tracing for the logging type(s) specified here.",
                "          LOGGING_TYPE can be any one of below:",
                "            IAccessibilityServiceConnection",
                "            IAccessibilityServiceClient");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "            IAccessibilityManager",
                "            IAccessibilityManagerClient",
                "            IAccessibilityInteractionConnection",
                "            IAccessibilityInteractionConnectionCallback");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "            IRemoteMagnificationAnimationCallback",
                "            IMagnificationConnection",
                "            IMagnificationConnectionCallback",
                "            WindowManagerInternal");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "            WindowsForAccessibilityCallback",
                "            MagnificationCallbacks",
                "            InputFilter",
                "            Gesture");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "            AccessibilityService",
                "            PMBroadcastReceiver",
                "            UserBroadcastReceiver",
                "            FingerprintGesture");
        outPrintWriter.println("  stop-trace");
        outPrintWriter.println("    Stop the debug tracing.");
        String str = Build.TYPE;
        if ("eng".equals(str) || "userdebug".equals(str)) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  notify-gesture-to-accessibilityservice <GESTURE_ID>",
                    "    Notifys the gesture event with the given gesture id.",
                    "  notify-key-to-accessibilityservice <KEY_CODE> <(optional) ACTION>",
                    "    Notifys the key event with the given key code.");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    If the ACTION is empty, down and up key event will be notified",
                    "  notify-accessibilityevent-to-accessibilityservice <EVENT_TYPE> <(optional)"
                        + " PACKAGE_NAME>",
                    "    Notifys the accessibility event with given event type",
                    "  toggle-accessibilityservice <PACKAGE_NAME> <CLASS_NAME>");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    Toggles the given AccessibilityService",
                    "    for example, talkback",
                    "      PACKAGE NAME : com.google.android.marvin.talkback",
                    "      CLASS_NAME : com.google.android.marvin.talkback.TalkBackService");
            outPrintWriter.println("  toggle-accessibility-sec-debug-log");
            outPrintWriter.println("    Toggles sec debug log of Accessibility framework");
        }
    }

    public final Integer parseUserId() {
        String nextOption = getNextOption();
        if (nextOption == null) {
            return Integer.valueOf(ActivityManager.getCurrentUser());
        }
        if (nextOption.equals("--user")) {
            return Integer.valueOf(UserHandle.parseUserArg(getNextArgRequired()));
        }
        getErrPrintWriter().println("Unknown option: ".concat(nextOption));
        return null;
    }
}
