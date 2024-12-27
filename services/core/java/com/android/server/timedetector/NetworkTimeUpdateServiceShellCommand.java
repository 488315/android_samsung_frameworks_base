package com.android.server.timedetector;

import android.net.Network;
import android.os.Binder;
import android.os.ShellCommand;
import android.util.NtpTrustedTime;
import com.android.server.timedetector.NetworkTimeUpdateService;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NetworkTimeUpdateServiceShellCommand extends ShellCommand {
    public final NetworkTimeUpdateService mNetworkTimeUpdateService;

    public NetworkTimeUpdateServiceShellCommand(NetworkTimeUpdateService networkTimeUpdateService) {
        Objects.requireNonNull(networkTimeUpdateService);
        this.mNetworkTimeUpdateService = networkTimeUpdateService;
    }

    public final int onCommand(String str) {
        Duration duration;
        Network network;
        boolean forceRefresh;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        duration = null;
        switch (str) {
            case "set_server_config_for_tests":
                ArrayList arrayList = new ArrayList();
                while (true) {
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        if (arrayList.isEmpty()) {
                            throw new IllegalArgumentException("Missing required option: ----server");
                        }
                        if (duration == null) {
                            throw new IllegalArgumentException("Missing required option: ----timeout_millis");
                        }
                        this.mNetworkTimeUpdateService.setServerConfigForTests(new NtpTrustedTime.NtpConfig(arrayList, duration));
                        return 0;
                    }
                    if (nextArg.equals("--timeout_millis")) {
                        duration = Duration.ofMillis(Integer.parseInt(getNextArgRequired()));
                    } else {
                        if (!nextArg.equals("--server")) {
                            throw new IllegalArgumentException("Unknown option: ".concat(nextArg));
                        }
                        try {
                            arrayList.add(NtpTrustedTime.parseNtpUriStrict(getNextArgRequired()));
                        } catch (URISyntaxException e) {
                            throw new IllegalArgumentException("Bad NTP server value", e);
                        }
                    }
                }
            case "reset_server_config_for_tests":
                this.mNetworkTimeUpdateService.setServerConfigForTests(null);
                return 0;
            case "force_refresh":
                NetworkTimeUpdateService networkTimeUpdateService = this.mNetworkTimeUpdateService;
                networkTimeUpdateService.mContext.enforceCallingPermission("android.permission.SET_TIME", "force network time refresh");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (networkTimeUpdateService.mLock) {
                        network = networkTimeUpdateService.mDefaultNetwork;
                    }
                    if (network == null) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        forceRefresh = false;
                    } else {
                        NetworkTimeUpdateService.EngineImpl engineImpl = networkTimeUpdateService.mEngine;
                        NetworkTimeUpdateService.AnonymousClass1 anonymousClass1 = networkTimeUpdateService.mRefreshCallbacks;
                        Long l = (Long) engineImpl.mElapsedRealtimeMillisSupplier.get();
                        l.longValue();
                        synchronized (engineImpl) {
                            engineImpl.mLastRefreshAttemptElapsedRealtimeMillis = l;
                        }
                        forceRefresh = engineImpl.mNtpTrustedTime.forceRefresh(network);
                        engineImpl.logToDebugAndDumpsys("forceRefreshForTests: refreshSuccessful=" + forceRefresh);
                        if (forceRefresh) {
                            NtpTrustedTime.TimeResult cachedTimeResult = engineImpl.mNtpTrustedTime.getCachedTimeResult();
                            if (cachedTimeResult == null) {
                                engineImpl.logToDebugAndDumpsys("forceRefreshForTests: cachedTimeResult unexpectedly null");
                            } else {
                                NetworkTimeUpdateService.EngineImpl.makeNetworkTimeSuggestion(cachedTimeResult, "EngineImpl.forceRefreshForTests()", anonymousClass1);
                            }
                        }
                    }
                    getOutPrintWriter().println(forceRefresh);
                    return 0;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Network Time Update Service (%s) commands:\n", "network_time_update_service");
        outPrintWriter.printf("  help\n", new Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "force_refresh");
        outPrintWriter.printf("    Refreshes the latest time. Prints whether it was successful.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "set_server_config_for_tests");
        outPrintWriter.printf("    Sets the NTP server config for tests. The config is not persisted.\n", new Object[0]);
        outPrintWriter.printf("      Options: %s <uri> [%s <additional uris>]+ %s <millis>\n", "--server", "--server", "--timeout_millis");
        outPrintWriter.printf("      NTP server URIs must be in the form \"ntp://hostname\" or \"ntp://hostname:port\"\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "reset_server_config_for_tests");
        outPrintWriter.printf("    Resets/clears the NTP server config set via %s.\n", "set_server_config_for_tests");
        outPrintWriter.println();
    }
}
