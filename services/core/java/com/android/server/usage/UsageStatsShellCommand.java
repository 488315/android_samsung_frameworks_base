package com.android.server.usage;

import android.app.ActivityManager;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.UserHandle;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsageStatsShellCommand extends ShellCommand {
    public final UsageStatsService mService;

    public UsageStatsShellCommand(UsageStatsService usageStatsService) {
        this.mService = usageStatsService;
    }

    public final int getUserId() {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                return i == -2 ? ActivityManager.getCurrentUser() : i;
            }
            if (!"-u".equals(nextOption) && !"--user".equals(nextOption)) {
                getErrPrintWriter().println("Error: unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        if (!str.equals("delete-package-data")) {
            if (!str.equals("clear-last-used-timestamps")) {
                return handleDefaultCommands(str);
            }
            String nextArgRequired = getNextArgRequired();
            int userId = getUserId();
            if (userId == -1) {
                return -1;
            }
            this.mService.mAppStandby.clearLastUsedTimestampsForTest(nextArgRequired, userId);
            return 0;
        }
        String nextArgRequired2 = getNextArgRequired();
        int userId2 = getUserId();
        if (userId2 == -1) {
            return -1;
        }
        UsageStatsService usageStatsService = this.mService;
        synchronized (usageStatsService.mLock) {
            UsageStatsDatabase usageStatsDatabase = ((UserUsageStatsService) usageStatsService.mUserState.get(userId2)).mDatabase;
            usageStatsDatabase.getClass();
            usageStatsDatabase.prunePackagesDataOnUpgrade(new HashMap(Collections.singletonMap(nextArgRequired2, Long.valueOf(SystemClock.elapsedRealtime()))));
        }
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("UsageStats service (usagestats) commands:");
        outPrintWriter.println("help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("clear-last-used-timestamps PACKAGE_NAME [-u | --user USER_ID]");
        outPrintWriter.println("    Clears the last used timestamps for the given package.");
        outPrintWriter.println();
        outPrintWriter.println("delete-package-data PACKAGE_NAME [-u | --user USER_ID]");
        outPrintWriter.println("    Deletes all the usage stats for the given package.");
        outPrintWriter.println();
    }
}
