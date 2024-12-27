package com.android.server.net.watchlist;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.net.IIpConnectivityMetrics;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.internal.net.INetworkWatchlistManager;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.HexDump;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.net.BaseNetdEventCallback;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NetworkWatchlistService extends INetworkWatchlistManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    IIpConnectivityMetrics mIpConnectivityMetrics;
    WatchlistLoggingHandler mNetworkWatchlistHandler;
    public volatile boolean mIsLoggingEnabled = false;
    public final Object mLoggingSwitchLock = new Object();
    public final AnonymousClass1 mNetdEventCallback = new BaseNetdEventCallback() { // from class: com.android.server.net.watchlist.NetworkWatchlistService.1
        public final void onConnectEvent(String str, int i, long j, int i2) {
            if (NetworkWatchlistService.this.mIsLoggingEnabled) {
                NetworkWatchlistService.this.mNetworkWatchlistHandler.asyncNetworkEvent(null, i2, new String[]{str});
            }
        }

        public final void onDnsEvent(int i, int i2, int i3, String str, String[] strArr, int i4, long j, int i5) {
            if (NetworkWatchlistService.this.mIsLoggingEnabled) {
                NetworkWatchlistService.this.mNetworkWatchlistHandler.asyncNetworkEvent(str, i5, strArr);
            }
        }
    };
    public final WatchlistConfig mConfig = WatchlistConfig.sInstance;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public NetworkWatchlistService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 550) {
                if (Settings.Global.getInt(getContext().getContentResolver(), "network_watchlist_enabled", 1) == 0) {
                    int i2 = NetworkWatchlistService.$r8$clinit;
                    Slog.i("NetworkWatchlistService", "Network Watchlist service is disabled");
                    return;
                }
                try {
                    this.mService.mConfig.getClass();
                    try {
                        File file = new File("/data/misc/network_watchlist/network_watchlist_for_test.xml");
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (Exception unused) {
                        Log.e("WatchlistConfig", "Unable to delete test config");
                    }
                    NetworkWatchlistService networkWatchlistService = this.mService;
                    networkWatchlistService.getClass();
                    networkWatchlistService.mIpConnectivityMetrics = IIpConnectivityMetrics.Stub.asInterface(ServiceManager.getService("connmetrics"));
                    this.mService.startWatchlistLogging();
                } catch (RemoteException unused2) {
                }
                Context context = getContext();
                int i3 = ReportWatchlistJobService.$r8$clinit;
                ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new JobInfo.Builder(882313, new ComponentName(context, (Class<?>) ReportWatchlistJobService.class)).setPeriodic(ReportWatchlistJobService.REPORT_WATCHLIST_RECORDS_PERIOD_MILLIS).setRequiresDeviceIdle(true).setRequiresBatteryNotLow(true).setPersisted(false).build());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [android.os.IBinder, com.android.server.net.watchlist.NetworkWatchlistService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            if (Settings.Global.getInt(getContext().getContentResolver(), "network_watchlist_enabled", 1) == 0) {
                int i = NetworkWatchlistService.$r8$clinit;
                Slog.i("NetworkWatchlistService", "Network Watchlist service is disabled");
            } else {
                ?? networkWatchlistService = new NetworkWatchlistService(getContext());
                this.mService = networkWatchlistService;
                publishBinderService("network_watchlist", networkWatchlistService);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.net.watchlist.NetworkWatchlistService$1] */
    public NetworkWatchlistService(Context context) {
        this.mContext = context;
        WatchlistLoggingHandler watchlistLoggingHandler = new WatchlistLoggingHandler(context, Watchdog$$ExternalSyntheticOutline0.m(10, "NetworkWatchlistService", false).getLooper());
        this.mNetworkWatchlistHandler = watchlistLoggingHandler;
        watchlistLoggingHandler.sendMessage(watchlistLoggingHandler.obtainMessage(2));
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.net.watchlist.NetworkWatchlistService$1] */
    public NetworkWatchlistService(Context context, ServiceThread serviceThread, WatchlistLoggingHandler watchlistLoggingHandler, IIpConnectivityMetrics iIpConnectivityMetrics) {
        this.mContext = context;
        this.mNetworkWatchlistHandler = watchlistLoggingHandler;
        this.mIpConnectivityMetrics = iIpConnectivityMetrics;
    }

    public static void enforceWatchlistLoggingPermission() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            throw new SecurityException(String.format("Uid %d has no permission to change watchlist setting.", Integer.valueOf(callingUid)));
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "NetworkWatchlistService", printWriter)) {
            WatchlistConfig watchlistConfig = this.mConfig;
            byte[] watchlistConfigHash = watchlistConfig.getWatchlistConfigHash();
            StringBuilder sb = new StringBuilder("Watchlist config hash: ");
            sb.append(watchlistConfigHash != null ? HexDump.toHexString(watchlistConfigHash) : null);
            printWriter.println(sb.toString());
            printWriter.println("Domain CRC32 digest list:");
            if (watchlistConfig.mDomainDigests != null) {
                watchlistConfig.mDomainDigests.crc32s.dump(printWriter);
            }
            printWriter.println("Domain SHA256 digest list:");
            if (watchlistConfig.mDomainDigests != null) {
                Iterator it = watchlistConfig.mDomainDigests.sha256Digests.mDigestSet.iterator();
                while (it.hasNext()) {
                    printWriter.println((String) it.next());
                }
                printWriter.println("");
            }
            printWriter.println("Ip CRC32 digest list:");
            if (watchlistConfig.mIpDigests != null) {
                watchlistConfig.mIpDigests.crc32s.dump(printWriter);
            }
            printWriter.println("Ip SHA256 digest list:");
            if (watchlistConfig.mIpDigests != null) {
                Iterator it2 = watchlistConfig.mIpDigests.sha256Digests.mDigestSet.iterator();
                while (it2.hasNext()) {
                    printWriter.println((String) it2.next());
                }
                printWriter.println("");
            }
        }
    }

    public final byte[] getWatchlistConfigHash() {
        return this.mConfig.getWatchlistConfigHash();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            new NetworkWatchlistShellCommand(this, this.mContext).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        } else {
            Slog.w("NetworkWatchlistService", "Only shell is allowed to call network watchlist shell commands");
        }
    }

    public final void reloadWatchlist() {
        enforceWatchlistLoggingPermission();
        Slog.i("NetworkWatchlistService", "Reloading watchlist");
        this.mConfig.reloadConfig();
    }

    public final void reportWatchlistIfNecessary() {
        WatchlistLoggingHandler watchlistLoggingHandler = this.mNetworkWatchlistHandler;
        watchlistLoggingHandler.sendMessage(watchlistLoggingHandler.obtainMessage(2));
    }

    public final boolean startWatchlistLogging() {
        enforceWatchlistLoggingPermission();
        return startWatchlistLoggingImpl();
    }

    public boolean startWatchlistLoggingImpl() throws RemoteException {
        synchronized (this.mLoggingSwitchLock) {
            if (this.mIsLoggingEnabled) {
                Slog.w("NetworkWatchlistService", "Watchlist logging is already running");
                return true;
            }
            try {
                if (!this.mIpConnectivityMetrics.addNetdEventCallback(2, this.mNetdEventCallback)) {
                    return false;
                }
                this.mIsLoggingEnabled = true;
                return true;
            } catch (RemoteException unused) {
                return false;
            }
        }
    }

    public final boolean stopWatchlistLogging() {
        enforceWatchlistLoggingPermission();
        return stopWatchlistLoggingImpl();
    }

    public boolean stopWatchlistLoggingImpl() {
        synchronized (this.mLoggingSwitchLock) {
            try {
                if (!this.mIsLoggingEnabled) {
                    Slog.w("NetworkWatchlistService", "Watchlist logging is not running");
                    return true;
                }
                this.mIsLoggingEnabled = false;
                try {
                    return this.mIpConnectivityMetrics.removeNetdEventCallback(2);
                } catch (RemoteException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
