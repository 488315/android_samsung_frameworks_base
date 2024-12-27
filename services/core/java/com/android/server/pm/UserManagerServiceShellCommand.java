package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.os.RoSystemProperties;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.utils.Slogf;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UserManagerServiceShellCommand extends ShellCommand {
    public final Context mContext;
    public final LockPatternUtils mLockPatternUtils;
    public final UserManagerService mService;
    public final UserSystemPackageInstaller mSystemPackageInstaller;

    public UserManagerServiceShellCommand(
            UserManagerService userManagerService,
            UserSystemPackageInstaller userSystemPackageInstaller,
            LockPatternUtils lockPatternUtils,
            Context context) {
        this.mService = userManagerService;
        this.mSystemPackageInstaller = userSystemPackageInstaller;
        this.mLockPatternUtils = lockPatternUtils;
        this.mContext = context;
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        try {
            switch (str.hashCode()) {
                case -1698126264:
                    if (str.equals("is-visible-background-users-supported")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -66900680:
                    if (str.equals("is-headless-system-user-mode")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 340621931:
                    if (str.equals("can-switch-to-headless-system-user")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 681635871:
                    if (str.equals("is-main-user-permanent-admin")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 980857487:
                    if (str.equals("is-visible-background-users-on-default-display-supported")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1085270974:
                    if (str.equals("report-system-user-package-whitelist-problems")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1453420968:
                    if (str.equals("get-main-user")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1605325659:
                    if (str.equals("set-system-user-mode-emulation")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1802440755:
                    if (str.equals("is-user-visible")) {
                        c = 6;
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
                    return runList();
                case 1:
                    return runReportPackageAllowlistProblems();
                case 2:
                    return runSetSystemUserModeEmulation();
                case 3:
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    boolean z = false;
                    while (true) {
                        String nextOption = getNextOption();
                        if (nextOption == null) {
                            boolean isHeadlessSystemUserMode =
                                    this.mService.isHeadlessSystemUserMode();
                            if (z) {
                                outPrintWriter.printf(
                                        "effective=%b real=%b\n",
                                        Boolean.valueOf(isHeadlessSystemUserMode),
                                        Boolean.valueOf(
                                                RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER));
                            } else {
                                outPrintWriter.println(isHeadlessSystemUserMode);
                            }
                            return 0;
                        }
                        if (!nextOption.equals("-v") && !nextOption.equals("--verbose")) {
                            outPrintWriter.println("Invalid option: ".concat(nextOption));
                            return -1;
                        }
                        z = true;
                    }
                    break;
                case 4:
                    PrintWriter outPrintWriter2 = getOutPrintWriter();
                    boolean z2 = false;
                    while (true) {
                        String nextOption2 = getNextOption();
                        if (nextOption2 == null) {
                            boolean isVisibleBackgroundUsersEnabled =
                                    UserManager.isVisibleBackgroundUsersEnabled();
                            if (z2) {
                                outPrintWriter2.printf(
                                        "effective=%b real=%b\n",
                                        Boolean.valueOf(isVisibleBackgroundUsersEnabled),
                                        Boolean.valueOf(
                                                Resources.getSystem()
                                                        .getBoolean(
                                                                R.bool
                                                                        .config_oem_enabled_satellite_access_allow)));
                            } else {
                                outPrintWriter2.println(isVisibleBackgroundUsersEnabled);
                            }
                            return 0;
                        }
                        if (!nextOption2.equals("-v") && !nextOption2.equals("--verbose")) {
                            outPrintWriter2.println("Invalid option: ".concat(nextOption2));
                            return -1;
                        }
                        z2 = true;
                    }
                    break;
                case 5:
                    PrintWriter outPrintWriter3 = getOutPrintWriter();
                    boolean z3 = false;
                    while (true) {
                        String nextOption3 = getNextOption();
                        if (nextOption3 == null) {
                            boolean isVisibleBackgroundUsersOnDefaultDisplayEnabled =
                                    UserManager.isVisibleBackgroundUsersOnDefaultDisplayEnabled();
                            if (z3) {
                                outPrintWriter3.printf(
                                        "effective=%b real=%b\n",
                                        Boolean.valueOf(
                                                isVisibleBackgroundUsersOnDefaultDisplayEnabled),
                                        Boolean.valueOf(
                                                Resources.getSystem()
                                                        .getBoolean(
                                                                R.bool
                                                                        .config_offsetWallpaperToCenterOfLargestDisplay)));
                            } else {
                                outPrintWriter3.println(
                                        isVisibleBackgroundUsersOnDefaultDisplayEnabled);
                            }
                            return 0;
                        }
                        if (!nextOption3.equals("-v") && !nextOption3.equals("--verbose")) {
                            outPrintWriter3.println("Invalid option: ".concat(nextOption3));
                            return -1;
                        }
                        z3 = true;
                    }
                    break;
                case 6:
                    return runIsUserVisible();
                case 7:
                    PrintWriter outPrintWriter4 = getOutPrintWriter();
                    int mainUserId = this.mService.getMainUserId();
                    if (mainUserId == -10000) {
                        outPrintWriter4.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                        return 1;
                    }
                    outPrintWriter4.println(mainUserId);
                    return 0;
                case '\b':
                    PrintWriter outPrintWriter5 = getOutPrintWriter();
                    this.mService.getClass();
                    outPrintWriter5.println(
                            Resources.getSystem().getBoolean(R.bool.config_carrier_vt_available));
                    return 0;
                case '\t':
                    PrintWriter outPrintWriter6 = getOutPrintWriter();
                    this.mService.getClass();
                    outPrintWriter6.println(
                            Resources.getSystem()
                                    .getBoolean(
                                            R.bool
                                                    .config_letterboxIsDisplayAspectRatioForFixedOrientationLetterboxEnabled));
                    return 0;
                default:
                    return handleDefaultCommands(str);
            }
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, getOutPrintWriter());
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("User manager (user) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Prints this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  list [-v | --verbose] [--all]");
        outPrintWriter.println("    Prints all users on the system.");
        outPrintWriter.println();
        outPrintWriter.println(
                "  report-system-user-package-whitelist-problems [-v | --verbose] [--critical-only]"
                    + " [--mode MODE]");
        outPrintWriter.println(
                "    Reports all issues on user-type package allowlist XML files. Options:");
        outPrintWriter.println("    -v | --verbose: shows extra info, like number of issues");
        outPrintWriter.println(
                "    --critical-only: show only critical issues, excluding warnings");
        outPrintWriter.println(
                "    --mode MODE: shows what errors would be if device used mode MODE");
        outPrintWriter.println(
                "      (where MODE is the allowlist mode integer as defined by"
                    + " config_userTypePackageWhitelistMode)");
        outPrintWriter.println();
        outPrintWriter.println(
                "  set-system-user-mode-emulation [--reboot | --no-restart] <headless | full |"
                    + " default>");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Changes whether the system user is headless, full, or default (as defined by"
                    + " OEM).",
                "    WARNING: this command is meant just for development and debugging purposes.",
                "             It should NEVER be used on automated tests.",
                "    NOTE: by default it restarts the Android runtime, unless called with");
        outPrintWriter.println("          --reboot (which does a full reboot) or");
        outPrintWriter.println("          --no-restart (which requires a manual restart)");
        outPrintWriter.println();
        outPrintWriter.println("  is-headless-system-user-mode [-v | --verbose]");
        outPrintWriter.println("    Checks whether the device uses headless system user mode.");
        outPrintWriter.println(
                "  is-visible-background-users-on-default-display-supported [-v | --verbose]");
        outPrintWriter.println(
                "    Checks whether the device allows users to be start visible on background in"
                    + " the default display.");
        outPrintWriter.println("    It returns the effective mode, even when using emulation");
        outPrintWriter.println("    (to get the real mode as well, use -v or --verbose)");
        outPrintWriter.println();
        outPrintWriter.println("  is-visible-background-users-supported [-v | --verbose]");
        outPrintWriter.println(
                "    Checks whether the device allows users to be start visible on background.");
        outPrintWriter.println("    It returns the effective mode, even when using emulation");
        outPrintWriter.println("    (to get the real mode as well, use -v or --verbose)");
        outPrintWriter.println();
        outPrintWriter.println("  is-user-visible [--display DISPLAY_ID] <USER_ID>");
        outPrintWriter.println("    Checks if the given user is visible in the given display.");
        outPrintWriter.println(
                "    If the display option is not set, it uses the user's context to check");
        outPrintWriter.println(
                "    (so it emulates what apps would get from UserManager.isUserVisible())");
        outPrintWriter.println();
        outPrintWriter.println("  get-main-user ");
        outPrintWriter.println("    Displays main user id or message if there is no main user");
        outPrintWriter.println();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003d, code lost:

       if (r2 != (-1)) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runIsUserVisible() {
        /*
            r5 = this;
            java.io.PrintWriter r0 = r5.getOutPrintWriter()
            r1 = 0
        L5:
            java.lang.String r2 = r5.getNextOption()
            r3 = -1
            if (r2 == 0) goto L2b
            java.lang.String r1 = "--display"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L1e
            java.lang.String r5 = "Invalid option: "
            java.lang.String r5 = r5.concat(r2)
            r0.println(r5)
            return r3
        L1e:
            java.lang.String r1 = r5.getNextArgRequired()
            int r1 = java.lang.Integer.parseInt(r1)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            goto L5
        L2b:
            java.lang.String r2 = r5.getNextArgRequired()
            int r2 = android.os.UserHandle.parseUserArg(r2)
            r4 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r2 == r4) goto L6e
            r4 = -3
            if (r2 == r4) goto L6e
            r4 = -2
            if (r2 == r4) goto L40
            if (r2 == r3) goto L6e
            goto L44
        L40:
            int r2 = android.app.ActivityManager.getCurrentUser()
        L44:
            r3 = 0
            if (r1 == 0) goto L54
            com.android.server.pm.UserManagerService r5 = r5.mService
            int r1 = r1.intValue()
            com.android.server.pm.UserVisibilityMediator r5 = r5.mUserVisibilityMediator
            boolean r5 = r5.isUserVisible(r2, r1)
            goto L6a
        L54:
            android.os.UserHandle r1 = android.os.UserHandle.of(r2)
            android.content.Context r5 = r5.mContext
            android.content.Context r5 = r5.createContextAsUser(r1, r3)
            java.lang.Class<android.os.UserManager> r1 = android.os.UserManager.class
            java.lang.Object r5 = r5.getSystemService(r1)
            android.os.UserManager r5 = (android.os.UserManager) r5
            boolean r5 = r5.isUserVisible()
        L6a:
            r0.println(r5)
            return r3
        L6e:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            java.lang.Object[] r5 = new java.lang.Object[]{r5}
            java.lang.String r1 = "invalid value (%d) for --user option\n"
            r0.printf(r1, r5)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.UserManagerServiceShellCommand.runIsUserVisible():int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runList() {
        int i;
        String str;
        String str2;
        char c;
        UserManagerServiceShellCommand userManagerServiceShellCommand = this;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                IActivityManager service = ActivityManager.getService();
                ArrayList arrayList =
                        (ArrayList) userManagerServiceShellCommand.mService.getUsers(!z, false, !z);
                int size = arrayList.size();
                int i3 = -10000;
                if (z2) {
                    outPrintWriter.printf("%d users:\n\n", Integer.valueOf(size));
                    i = service.getCurrentUser().id;
                } else {
                    outPrintWriter.println("Users:");
                    i = -10000;
                }
                int i4 = 0;
                while (i4 < size) {
                    UserInfo userInfo = (UserInfo) arrayList.get(i4);
                    boolean isUserRunning = service.isUserRunning(userInfo.id, i2);
                    if (z2) {
                        DevicePolicyManagerInternal devicePolicyManagerInternal =
                                (DevicePolicyManagerInternal)
                                        LocalServices.getService(DevicePolicyManagerInternal.class);
                        if (devicePolicyManagerInternal != null) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                int deviceOwnerUserId =
                                        devicePolicyManagerInternal.getDeviceOwnerUserId();
                                int i5 = userInfo.id;
                                String str3 = deviceOwnerUserId == i5 ? " (device-owner)" : "";
                                String str4 =
                                        devicePolicyManagerInternal.getProfileOwnerAsUser(i5)
                                                        != null
                                                ? " (profile-owner)"
                                                : "";
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                str2 = str4;
                                str = str3;
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        } else {
                            str = "";
                            str2 = str;
                        }
                        int i6 = userInfo.id;
                        boolean z3 = i6 == i;
                        int i7 = userInfo.profileGroupId;
                        outPrintWriter.printf(
                                "%d: id=%d, name=%s, type=%s, flags=%s%s%s%s%s%s%s%s%s%s\n",
                                Integer.valueOf(i4),
                                Integer.valueOf(userInfo.id),
                                userInfo.name,
                                userInfo.userType.replace("android.os.usertype.", ""),
                                UserInfo.flagsToString(userInfo.flags),
                                i7 != i6 && i7 != i3
                                        ? AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                userInfo.profileGroupId,
                                                new StringBuilder(" (parentId="),
                                                ")")
                                        : "",
                                isUserRunning ? " (running)" : "",
                                userInfo.partial ? " (partial)" : "",
                                userInfo.preCreated ? " (pre-created)" : "",
                                userInfo.convertedFromPreCreated ? " (converted)" : "",
                                str,
                                str2,
                                z3 ? " (current)" : "",
                                userManagerServiceShellCommand.mService.isUserVisible(i6)
                                        ? " (visible)"
                                        : "");
                    } else {
                        outPrintWriter.printf(
                                "\t%s%s\n", userInfo, isUserRunning ? " running" : "");
                    }
                    i4++;
                    i2 = 0;
                    i3 = -10000;
                    userManagerServiceShellCommand = this;
                }
                return i2;
            }
            switch (nextOption.hashCode()) {
                case 1513:
                    if (nextOption.equals("-v")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 42995713:
                    if (nextOption.equals("--all")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1737088994:
                    if (nextOption.equals("--verbose")) {
                        c = 2;
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
                case 2:
                    z2 = true;
                    break;
                case 1:
                    z = true;
                    break;
                default:
                    outPrintWriter.println("Invalid option: ".concat(nextOption));
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runReportPackageAllowlistProblems() {
        char c;
        PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -1000;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                StringBuilder m =
                        FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                                "runReportPackageAllowlistProblems(): verbose=",
                                z,
                                ", criticalOnly=",
                                z2,
                                ", mode=");
                m.append(UserSystemPackageInstaller.modeToString(i));
                Slog.d("UserManagerServiceShellCommand", m.toString());
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(outPrintWriter, "  ");
                try {
                    this.mSystemPackageInstaller.dumpPackageWhitelistProblems(
                            indentingPrintWriter, i, z, z2);
                    indentingPrintWriter.close();
                    return 0;
                } catch (Throwable th) {
                    try {
                        indentingPrintWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            switch (nextOption.hashCode()) {
                case -1362766982:
                    if (nextOption.equals("--critical-only")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1513:
                    if (nextOption.equals("-v")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333227331:
                    if (nextOption.equals("--mode")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1737088994:
                    if (nextOption.equals("--verbose")) {
                        c = 3;
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
                    z2 = true;
                    break;
                case 1:
                case 3:
                    z = true;
                    break;
                case 2:
                    i = Integer.parseInt(getNextArgRequired());
                    break;
                default:
                    outPrintWriter.println("Invalid option: ".concat(nextOption));
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetSystemUserModeEmulation() {
        char c;
        boolean z = true;
        if (!Build.isDebuggable()) {
            getErrPrintWriter().println("Command not available on user builds");
        } else if (Binder.getCallingUid() == 0) {
            PrintWriter outPrintWriter = getOutPrintWriter();
            if (this.mLockPatternUtils.isSecure(0)) {
                outPrintWriter.println("Cannot change system user mode when it has a credential");
                return -1;
            }
            boolean z2 = false;
            boolean z3 = true;
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    if (z2 && !z3) {
                        getErrPrintWriter()
                                .println("You can use --reboot or --no-restart, but not both");
                        return -1;
                    }
                    String nextArgRequired = getNextArgRequired();
                    boolean isHeadlessSystemUserMode = UserManager.isHeadlessSystemUserMode();
                    nextArgRequired.getClass();
                    switch (nextArgRequired.hashCode()) {
                        case -1115062407:
                            if (nextArgRequired.equals("headless")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3154575:
                            if (nextArgRequired.equals("full")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1544803905:
                            if (nextArgRequired.equals("default")) {
                                c = 2;
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
                            z = true ^ isHeadlessSystemUserMode;
                            break;
                        case 1:
                            z = isHeadlessSystemUserMode;
                            break;
                        case 2:
                            break;
                        default:
                            getErrPrintWriter().printf("Invalid arg: %s\n", nextArgRequired);
                            return -1;
                    }
                    if (!z) {
                        outPrintWriter.printf(
                                "No change needed, system user is already %s\n",
                                isHeadlessSystemUserMode ? "headless" : "full");
                        return 0;
                    }
                    Slogf.d(
                            "UserManagerServiceShellCommand",
                            "Updating system property %s to %s",
                            "persist.debug.user_mode_emulation",
                            nextArgRequired);
                    SystemProperties.set("persist.debug.user_mode_emulation", nextArgRequired);
                    if (z2) {
                        Slog.i(
                                "UserManagerServiceShellCommand",
                                "Rebooting to finalize the changes");
                        outPrintWriter.println("Rebooting to finalize changes");
                        UiThread.getHandler()
                                .post(
                                        new UserManagerServiceShellCommand$$ExternalSyntheticLambda0());
                    } else if (z3) {
                        Slog.i("UserManagerServiceShellCommand", "Shutting PackageManager down");
                        ((PackageManagerInternal)
                                        LocalServices.getService(PackageManagerInternal.class))
                                .shutdown();
                        IActivityManager service = ActivityManager.getService();
                        if (service != null) {
                            try {
                                Slog.i(
                                        "UserManagerServiceShellCommand",
                                        "Shutting ActivityManager down");
                                service.shutdown(10000);
                            } catch (RemoteException e) {
                                Slog.e(
                                        "UserManagerServiceShellCommand",
                                        "Failed to shut down ActivityManager" + e);
                            }
                        }
                        int myPid = Process.myPid();
                        Slogf.i(
                                "UserManagerServiceShellCommand",
                                "Restarting Android runtime(PID=%d) to finalize changes",
                                Integer.valueOf(myPid));
                        outPrintWriter.println("Restarting Android runtime to finalize changes");
                        outPrintWriter.println(
                                "The restart may trigger a 'Broken pipe' message; this is to be"
                                    + " expected.");
                        outPrintWriter.flush();
                        Process.killProcess(myPid);
                    } else {
                        outPrintWriter.println(
                                "System user mode changed - please reboot (or restart Android"
                                    + " runtime) to continue");
                        outPrintWriter.println(
                                "NOTICE: after restart, some apps might be uninstalled (and their"
                                    + " data will be lost)");
                    }
                    return 0;
                }
                if (nextOption.equals("--no-restart")) {
                    z3 = false;
                } else {
                    if (!nextOption.equals("--reboot")) {
                        outPrintWriter.println("Invalid option: ".concat(nextOption));
                        return -1;
                    }
                    z2 = true;
                }
            }
        } else {
            getErrPrintWriter().println("Command only available on root user");
        }
        return -1;
    }
}
