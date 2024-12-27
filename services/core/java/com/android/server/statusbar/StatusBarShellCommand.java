package com.android.server.statusbar;

import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.service.quicksettings.TileService;
import android.util.Pair;
import com.android.internal.util.GcUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StatusBarShellCommand extends ShellCommand {
    public static final StatusBarShellCommandToken sToken = new StatusBarShellCommandToken();
    public final Context mContext;
    public final StatusBarManagerService mInterface;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatusBarShellCommandToken extends Binder {
    }

    public StatusBarShellCommand(StatusBarManagerService statusBarManagerService, Context context) {
        this.mInterface = statusBarManagerService;
        this.mContext = context;
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            onHelp();
            return 1;
        }
        try {
            switch (str.hashCode()) {
                case -1282000806:
                    if (str.equals("add-tile")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1239176554:
                    if (str.equals("get-status-icons")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1067396926:
                    if (str.equals("tracing")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1052548778:
                    if (str.equals("send-disable-flag")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -919868578:
                    if (str.equals("run-gc")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -823073837:
                    if (str.equals("click-tile")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -632085587:
                    if (str.equals("collapse")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -498761126:
                    if (str.equals("set-tiles")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -339726761:
                    if (str.equals("remove-tile")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1499:
                    if (str.equals("-h")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 3095028:
                    if (str.equals("dump")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals("help")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 901899220:
                    if (str.equals("disable-for-setup")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1612300298:
                    if (str.equals("check-support")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1629310709:
                    if (str.equals("expand-notifications")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1672031734:
                    if (str.equals("expand-settings")) {
                        c = 1;
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
                    this.mInterface.expandNotificationsPanel();
                    return 0;
                case 1:
                    this.mInterface.expandSettingsPanel(null);
                    return 0;
                case 2:
                    this.mInterface.collapsePanels();
                    return 0;
                case 3:
                    this.mInterface.addTile(ComponentName.unflattenFromString(getNextArgRequired()));
                    return 0;
                case 4:
                    this.mInterface.remTile(ComponentName.unflattenFromString(getNextArgRequired()));
                    return 0;
                case 5:
                    StatusBarManagerService statusBarManagerService = this.mInterface;
                    String nextArgRequired = getNextArgRequired();
                    statusBarManagerService.enforceStatusBarOrShell();
                    if (statusBarManagerService.mBar != null) {
                        try {
                            statusBarManagerService.mBar.setQsTiles(nextArgRequired.split(","));
                        } catch (RemoteException unused) {
                        }
                    }
                    return 0;
                case 6:
                    this.mInterface.clickTile(ComponentName.unflattenFromString(getNextArgRequired()));
                    return 0;
                case 7:
                    getOutPrintWriter().println(String.valueOf(TileService.isQuickSettingsSupported()));
                    return 0;
                case '\b':
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    for (String str2 : this.mInterface.mContext.getResources().getStringArray(17236322)) {
                        outPrintWriter.println(str2);
                    }
                    return 0;
                case '\t':
                    String nextArgRequired2 = getNextArgRequired();
                    String packageName = this.mContext.getPackageName();
                    if (Boolean.parseBoolean(nextArgRequired2)) {
                        StatusBarManagerService statusBarManagerService2 = this.mInterface;
                        StatusBarShellCommandToken statusBarShellCommandToken = sToken;
                        statusBarManagerService2.disable(61145088, statusBarShellCommandToken, packageName);
                        this.mInterface.disable2(0, statusBarShellCommandToken, packageName);
                    } else {
                        StatusBarManagerService statusBarManagerService3 = this.mInterface;
                        StatusBarShellCommandToken statusBarShellCommandToken2 = sToken;
                        statusBarManagerService3.disable(0, statusBarShellCommandToken2, packageName);
                        this.mInterface.disable2(0, statusBarShellCommandToken2, packageName);
                    }
                    return 0;
                case '\n':
                    runSendDisableFlag();
                    return 0;
                case 11:
                    String nextArg = getNextArg();
                    nextArg.getClass();
                    if (nextArg.equals("stop")) {
                        this.mInterface.stopTracing();
                    } else if (nextArg.equals("start")) {
                        this.mInterface.startTracing();
                    }
                    return 0;
                case '\f':
                    StatusBarManagerService statusBarManagerService4 = this.mInterface;
                    statusBarManagerService4.getClass();
                    if (!Build.IS_DEBUGGABLE) {
                        throw new SecurityException("runGcForTest requires a debuggable build");
                    }
                    GcUtils.runGcAndFinalizersSync();
                    if (statusBarManagerService4.mBar != null) {
                        try {
                            statusBarManagerService4.mBar.runGcForTest();
                        } catch (RemoteException unused2) {
                        }
                    }
                    return 0;
                case '\r':
                case 14:
                    onHelp();
                    return 0;
                case 15:
                    return handleDefaultCommands(str);
                default:
                    this.mInterface.passThroughShellCommand(getAllArgs(), getOutFileDescriptor());
                    return 0;
            }
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m("Remote exception: ", e, getOutPrintWriter());
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Status bar commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  expand-notifications", "    Open the notifications panel.", "", "  expand-settings");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Open the notifications panel and expand quick settings if present.", "", "  collapse", "    Collapse the notifications and settings panel.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  add-tile COMPONENT", "    Add a TileService of the specified component", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  remove-tile COMPONENT", "    Remove a TileService of the specified component", "", "  set-tiles LIST-OF-TILES");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Sets the list of tiles as the current Quick Settings tiles", "", "  click-tile COMPONENT", "    Click on a TileService of the specified component");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "", "  check-support", "    Check if this device supports QS + APIs", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-status-icons", "    Print the list of status bar icons and the order they appear in", "", "  disable-for-setup DISABLE");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    If true, disable status bar components unsuitable for device setup", "", "  send-disable-flag FLAG...", "    Send zero or more disable flags (parsed individually) to StatusBarManager");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Valid options:", "        <blank>             - equivalent to \"none\"", "        none                - re-enables all components", "        search              - disable search");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        home                - disable naviagation home", "        recents             - disable recents/overview", "        notification-peek   - disable notification peeking", "        statusbar-expansion - disable status bar expansion");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "        system-icons        - disable system icons appearing in status bar", "        clock               - disable clock appearing in status bar", "        notification-icons  - disable notification icons from status bar", "");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  tracing (start | stop)", "    Start or stop SystemUI tracing", "", "  NOTE: any command not listed here will be passed through to IStatusBar");
        outPrintWriter.println("");
        outPrintWriter.println("  Commands implemented in SystemUI:");
        outPrintWriter.flush();
        this.mInterface.passThroughShellCommand(new String[0], getOutFileDescriptor());
    }

    public final void runSendDisableFlag() {
        String packageName = this.mContext.getPackageName();
        StatusBarManager.DisableInfo disableInfo = new StatusBarManager.DisableInfo();
        String nextArg = getNextArg();
        while (nextArg != null) {
            switch (nextArg) {
                case "system-icons":
                    disableInfo.setSystemIconsDisabled(true);
                    break;
                case "search":
                    disableInfo.setSearchDisabled(true);
                    break;
                case "notification-alerts":
                    disableInfo.setNotificationPeekingDisabled(true);
                    break;
                case "home":
                    disableInfo.setNagivationHomeDisabled(true);
                    break;
                case "clock":
                    disableInfo.setClockDisabled(true);
                    break;
                case "statusbar-expansion":
                    disableInfo.setStatusBarExpansionDisabled(true);
                    break;
                case "recents":
                    disableInfo.setRecentsDisabled(true);
                    break;
                case "notification-icons":
                    disableInfo.setNotificationIconsDisabled(true);
                    break;
            }
            nextArg = getNextArg();
        }
        Pair flags = disableInfo.toFlags();
        StatusBarManagerService statusBarManagerService = this.mInterface;
        int intValue = ((Integer) flags.first).intValue();
        StatusBarShellCommandToken statusBarShellCommandToken = sToken;
        statusBarManagerService.disable(intValue, statusBarShellCommandToken, packageName);
        this.mInterface.disable2(((Integer) flags.second).intValue(), statusBarShellCommandToken, packageName);
    }
}
