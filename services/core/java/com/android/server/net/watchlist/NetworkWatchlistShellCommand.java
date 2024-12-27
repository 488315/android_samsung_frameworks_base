package com.android.server.net.watchlist;

import android.content.Context;
import android.os.Binder;
import android.os.FileUtils;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ShellCommand;
import android.provider.Settings;
import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NetworkWatchlistShellCommand extends ShellCommand {
    public final Context mContext;
    public final NetworkWatchlistService mService;

    public NetworkWatchlistShellCommand(NetworkWatchlistService networkWatchlistService, Context context) {
        this.mContext = context;
        this.mService = networkWatchlistService;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0042 A[Catch: Exception -> 0x0026, TRY_LEAVE, TryCatch #0 {Exception -> 0x0026, blocks: (B:7:0x000c, B:15:0x0038, B:17:0x003d, B:19:0x0042, B:21:0x001c, B:24:0x0028), top: B:6:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r6) {
        /*
            r5 = this;
            if (r6 != 0) goto L7
            int r5 = r5.handleDefaultCommands(r6)
            return r5
        L7:
            java.io.PrintWriter r0 = r5.getOutPrintWriter()
            r1 = -1
            int r2 = r6.hashCode()     // Catch: java.lang.Exception -> L26
            r3 = 1757613042(0x68c30bf2, float:7.3686545E24)
            r4 = 1
            if (r2 == r3) goto L28
            r3 = 1854202282(0x6e84e1aa, float:2.0562416E28)
            if (r2 == r3) goto L1c
            goto L33
        L1c:
            java.lang.String r2 = "force-generate-report"
            boolean r2 = r6.equals(r2)     // Catch: java.lang.Exception -> L26
            if (r2 == 0) goto L33
            r2 = r4
            goto L34
        L26:
            r5 = move-exception
            goto L47
        L28:
            java.lang.String r2 = "set-test-config"
            boolean r2 = r6.equals(r2)     // Catch: java.lang.Exception -> L26
            if (r2 == 0) goto L33
            r2 = 0
            goto L34
        L33:
            r2 = r1
        L34:
            if (r2 == 0) goto L42
            if (r2 == r4) goto L3d
            int r5 = r5.handleDefaultCommands(r6)     // Catch: java.lang.Exception -> L26
            return r5
        L3d:
            int r5 = r5.runForceGenerateReport()     // Catch: java.lang.Exception -> L26
            return r5
        L42:
            int r5 = r5.runSetTestConfig()     // Catch: java.lang.Exception -> L26
            return r5
        L47:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r2 = "Exception: "
            r6.<init>(r2)
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r0.println(r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.net.watchlist.NetworkWatchlistShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Network watchlist manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-test-config your_watchlist_config.xml");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Set network watchlist test config file.", "  force-generate-report", "    Force generate watchlist test report.");
    }

    public final int runForceGenerateReport() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (WatchlistConfig.sInstance.mIsSecureConfig) {
                outPrintWriter.println("Error: Cannot force generate report under production config");
                return -1;
            }
            Settings.Global.putLong(this.mContext.getContentResolver(), "network_watchlist_last_report_time", 0L);
            NetworkWatchlistService networkWatchlistService = this.mService;
            long currentTimeMillis = System.currentTimeMillis();
            if (!networkWatchlistService.mConfig.mIsSecureConfig) {
                WatchlistLoggingHandler watchlistLoggingHandler = networkWatchlistService.mNetworkWatchlistHandler;
                Message obtainMessage = watchlistLoggingHandler.obtainMessage(3);
                obtainMessage.obj = Long.valueOf(currentTimeMillis);
                watchlistLoggingHandler.sendMessage(obtainMessage);
            }
            outPrintWriter.println("Success!");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Exception e) {
            outPrintWriter.println("Error: " + e);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int runSetTestConfig() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            String nextArgRequired = getNextArgRequired();
            ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArgRequired, "r");
            if (openFileForSystem == null) {
                outPrintWriter.println("Error: can't open input file " + nextArgRequired);
                return -1;
            }
            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(openFileForSystem);
            try {
                WatchlistConfig watchlistConfig = WatchlistConfig.sInstance;
                watchlistConfig.getClass();
                Log.i("WatchlistConfig", "Setting watchlist testing config");
                FileUtils.copyToFileOrThrow(autoCloseInputStream, new File("/data/misc/network_watchlist/network_watchlist_for_test.xml"));
                watchlistConfig.mIsSecureConfig = false;
                watchlistConfig.mXmlFile = new File("/data/misc/network_watchlist/network_watchlist_for_test.xml");
                watchlistConfig.reloadConfig();
                autoCloseInputStream.close();
                outPrintWriter.println("Success!");
                return 0;
            } finally {
            }
        } catch (Exception e) {
            outPrintWriter.println("Error: " + e.toString());
            return -1;
        }
    }
}
