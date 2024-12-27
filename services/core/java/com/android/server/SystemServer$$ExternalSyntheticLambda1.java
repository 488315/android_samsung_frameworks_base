package com.android.server;

import android.app.ApplicationErrorReport;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Pair;

import com.android.internal.os.RuntimeInit;
import com.android.internal.util.FrameworkStatsLog;

import java.util.LinkedList;

public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda1
        implements RuntimeInit.ApplicationWtfHandler {
    public final boolean handleApplicationWtf(
            IBinder iBinder,
            String str,
            boolean z,
            ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo,
            int i) {
        LinkedList linkedList = SystemServer.sPendingWtfs;
        int myPid = Process.myPid();
        int userId = UserHandle.getUserId(1000);
        EventLog.writeEvent(
                30040,
                Integer.valueOf(userId),
                Integer.valueOf(myPid),
                "system_server",
                -1,
                str,
                parcelableCrashInfo.exceptionMessage);
        FrameworkStatsLog.write(80, 1000, str, "system_server", myPid, 3);
        synchronized (SystemServer.class) {
            try {
                if (SystemServer.sPendingWtfs == null) {
                    SystemServer.sPendingWtfs = new LinkedList();
                }
                SystemServer.sPendingWtfs.add(new Pair(str, parcelableCrashInfo));
            } catch (Throwable th) {
                throw th;
            }
        }
        return false;
    }
}
