package com.samsung.android.server.hwrs.samba;

import android.os.SystemClock;
import android.os.SystemService;
import android.util.Log;

import com.samsung.android.server.hwrs.utils.FileUtil;
import com.samsung.android.server.hwrs.utils.StorageServiceException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ServerManager {
    public static void startSmbServer() {
        Log.d("[HWRS_SYS]ServerManager", "startSmbServer");
        if (!SystemService.isStopped("ksmbd_stop")) {
            Log.d("[HWRS_SYS]ServerManager", "Server stopping is in progress, can't start server");
            throw new StorageServiceException("ServerBusy");
        }
        if (SystemService.isRunning("ksmbd_start")) {
            Log.d("[HWRS_SYS]ServerManager", "KSMBD already running");
            return;
        }
        FileUtil.deleteFile("/data/misc/hwrs/ksmbd.lock");
        Log.d("[HWRS_SYS]ServerManager", "start ksmbd prop");
        SystemService.start("ksmbd_start");
        waitForState("ksmbd_start", SystemService.State.RUNNING);
        Log.d("[HWRS_SYS]ServerManager", "Start KSMBD Successful!!!");
    }

    public static void stopSmbServer() {
        Log.d("[HWRS_SYS]ServerManager", "stopSmbServer");
        if (SystemService.isRunning("ksmbd_stop")) {
            Log.d("[HWRS_SYS]ServerManager", "KSMBD stopping...");
            return;
        }
        Log.d("[HWRS_SYS]ServerManager", "stop ksmbd prop");
        SystemService.start("ksmbd_stop");
        Log.d("[HWRS_SYS]ServerManager", "wait for  ksmbd_stop to stop");
        SystemService.State state = SystemService.State.STOPPED;
        waitForState("ksmbd_stop", state);
        Log.d("[HWRS_SYS]ServerManager", "wait for  ksmbd_start to stop");
        waitForState("ksmbd_start", state);
        Log.d("[HWRS_SYS]ServerManager", "Stop KSMBD Successful!!!");
    }

    public static void waitForState(String str, SystemService.State state) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + 10000;
        while (true) {
            SystemService.State state2 = SystemService.getState(str);
            if (state.equals(state2)) {
                return;
            }
            if (SystemClock.elapsedRealtime() >= elapsedRealtime) {
                throw new StorageServiceException(
                        "Service "
                                + str
                                + " currently "
                                + state2
                                + "; waited 10000ms for "
                                + state);
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                Log.e("[HWRS_SYS]ServerManager", "sleep: exception");
                e.printStackTrace();
            }
        }
    }
}
