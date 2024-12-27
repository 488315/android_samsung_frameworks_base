package com.android.server.connectivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.IPacProxyInstalledListener;
import android.net.IPacProxyManager;
import android.net.Network;
import android.net.ProxyInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.webkit.URLUtil;

import com.android.net.IProxyCallback;
import com.android.net.IProxyPortListener;
import com.android.net.IProxyService;
import com.android.net.module.util.PermissionUtils;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PacProxyService extends IPacProxyManager.Stub {
    public AlarmManager mAlarmManager;
    public AnonymousClass2 mConnection;
    public final Context mContext;
    public int mCurrentDelay;
    public String mCurrentPac;
    public volatile boolean mHasDownloaded;
    public volatile boolean mHasSentBroadcast;
    public final Handler mNetThreadHandler;
    public final PendingIntent mPacRefreshIntent;
    public AnonymousClass2 mProxyConnection;
    public IProxyService mProxyService;
    public volatile Uri mPacUrl = Uri.EMPTY;
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    public final Object mProxyLock = new Object();
    public final Object mBroadcastStateLock = new Object();
    public final AnonymousClass1 mPacDownloader =
            new Runnable() { // from class: com.android.server.connectivity.PacProxyService.1
                @Override // java.lang.Runnable
                public final void run() {
                    String str;
                    Uri uri = PacProxyService.this.mPacUrl;
                    if (Uri.EMPTY.equals(uri)) {
                        return;
                    }
                    int andSetThreadStatsTag = TrafficStats.getAndSetThreadStatsTag(-187);
                    try {
                        try {
                            str = PacProxyService.m370$$Nest$smget(uri);
                        } catch (IOException e) {
                            Log.w("PacProxyService", "Failed to load PAC file: " + e);
                            TrafficStats.setThreadStatsTag(andSetThreadStatsTag);
                            str = null;
                        }
                        if (str == null) {
                            PacProxyService pacProxyService = PacProxyService.this;
                            int i = pacProxyService.mCurrentDelay + 1;
                            if (i > 3) {
                                i = 3;
                            }
                            pacProxyService.mCurrentDelay = i;
                            pacProxyService.setDownloadIn(i);
                            return;
                        }
                        synchronized (PacProxyService.this.mProxyLock) {
                            try {
                                if (!str.equals(PacProxyService.this.mCurrentPac)) {
                                    PacProxyService pacProxyService2 = PacProxyService.this;
                                    IProxyService iProxyService = pacProxyService2.mProxyService;
                                    if (iProxyService == null) {
                                        Log.e(
                                                "PacProxyService",
                                                "setCurrentProxyScript: no proxy service");
                                    } else {
                                        try {
                                            iProxyService.setPacFile(str);
                                            pacProxyService2.mCurrentPac = str;
                                        } catch (RemoteException e2) {
                                            Log.e("PacProxyService", "Unable to set PAC file", e2);
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        PacProxyService.this.mHasDownloaded = true;
                        PacProxyService.m369$$Nest$msendProxyIfNeeded(PacProxyService.this);
                        PacProxyService pacProxyService3 = PacProxyService.this;
                        pacProxyService3.mCurrentDelay = 0;
                        pacProxyService3.setDownloadIn(4);
                    } finally {
                        TrafficStats.setThreadStatsTag(andSetThreadStatsTag);
                    }
                }
            };
    public int mLastPort = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.connectivity.PacProxyService$2, reason: invalid class name */
    public final class AnonymousClass2 implements ServiceConnection {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PacProxyService this$0;

        public /* synthetic */ AnonymousClass2(PacProxyService pacProxyService, int i) {
            this.$r8$classId = i;
            this.this$0 = pacProxyService;
        }

        private final void onServiceDisconnected$com$android$server$connectivity$PacProxyService$3(
                ComponentName componentName) {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mProxyLock) {
                        try {
                            Log.d(
                                    "PacProxyService",
                                    "Adding service com.android.net.IProxyService "
                                            + iBinder.getInterfaceDescriptor());
                        } catch (RemoteException e) {
                            Log.e("PacProxyService", "Remote Exception", e);
                        }
                        ServiceManager.addService("com.android.net.IProxyService", iBinder);
                        this.this$0.mProxyService = IProxyService.Stub.asInterface(iBinder);
                        PacProxyService pacProxyService = this.this$0;
                        IProxyService iProxyService = pacProxyService.mProxyService;
                        if (iProxyService == null) {
                            Log.e("PacProxyService", "No proxy service");
                        } else {
                            String str = pacProxyService.mCurrentPac;
                            if (str == null) {
                                pacProxyService.mNetThreadHandler.post(
                                        pacProxyService.mPacDownloader);
                            } else if (iProxyService == null) {
                                Log.e("PacProxyService", "setCurrentProxyScript: no proxy service");
                            } else {
                                try {
                                    iProxyService.setPacFile(str);
                                    pacProxyService.mCurrentPac = str;
                                } catch (RemoteException e2) {
                                    Log.e("PacProxyService", "Unable to set PAC file", e2);
                                }
                            }
                        }
                    }
                    return;
                default:
                    IProxyCallback asInterface = IProxyCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        try {
                            asInterface.getProxyPort(
                                    new IProxyPortListener.Stub() { // from class:
                                        // com.android.server.connectivity.PacProxyService$3$1
                                        public final void setProxyPort(int i) {
                                            PacProxyService pacProxyService2 =
                                                    PacProxyService.AnonymousClass2.this.this$0;
                                            if (pacProxyService2.mLastPort != -1) {
                                                pacProxyService2.mHasSentBroadcast = false;
                                            }
                                            PacProxyService.AnonymousClass2.this.this$0.mLastPort =
                                                    i;
                                            if (i == -1) {
                                                Log.e(
                                                        "PacProxyService",
                                                        "Received invalid port from Local Proxy,"
                                                            + " PAC will not be operational");
                                            } else {
                                                NetworkScorerAppManager$$ExternalSyntheticOutline0
                                                        .m(
                                                                i,
                                                                "Local proxy is bound on ",
                                                                "PacProxyService");
                                                PacProxyService.m369$$Nest$msendProxyIfNeeded(
                                                        PacProxyService.AnonymousClass2.this
                                                                .this$0);
                                            }
                                        }
                                    });
                            return;
                        } catch (RemoteException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    }
                    return;
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mProxyLock) {
                        this.this$0.mProxyService = null;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PacRefreshIntentReceiver extends BroadcastReceiver {
        public PacRefreshIntentReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            PacProxyService pacProxyService = PacProxyService.this;
            pacProxyService.mNetThreadHandler.post(pacProxyService.mPacDownloader);
        }
    }

    /* renamed from: -$$Nest$msendProxyIfNeeded, reason: not valid java name */
    public static void m369$$Nest$msendProxyIfNeeded(PacProxyService pacProxyService) {
        synchronized (pacProxyService.mBroadcastStateLock) {
            try {
                if (pacProxyService.mHasDownloaded && pacProxyService.mLastPort != -1) {
                    if (!pacProxyService.mHasSentBroadcast) {
                        ProxyInfo buildPacProxy =
                                ProxyInfo.buildPacProxy(
                                        pacProxyService.mPacUrl, pacProxyService.mLastPort);
                        int beginBroadcast = pacProxyService.mCallbacks.beginBroadcast();
                        for (int i = 0; i < beginBroadcast; i++) {
                            IPacProxyInstalledListener broadcastItem =
                                    pacProxyService.mCallbacks.getBroadcastItem(i);
                            if (broadcastItem != null) {
                                try {
                                    broadcastItem.onPacProxyInstalled(
                                            (Network) null, buildPacProxy);
                                } catch (RemoteException unused) {
                                }
                            }
                        }
                        pacProxyService.mCallbacks.finishBroadcast();
                        pacProxyService.mHasSentBroadcast = true;
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$smget, reason: not valid java name */
    public static String m370$$Nest$smget(Uri uri) {
        long j;
        if (!URLUtil.isValidUrl(uri.toString())) {
            throw new IOException("Malformed URL:" + uri);
        }
        try {
            URLConnection openConnection = new URL(uri.toString()).openConnection(Proxy.NO_PROXY);
            try {
                j = Long.parseLong(openConnection.getHeaderField("Content-Length"));
            } catch (NumberFormatException unused) {
                j = -1;
            }
            if (j > 20000000) {
                throw new IOException("PAC too big: " + j + " bytes");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            do {
                int read = openConnection.getInputStream().read(bArr);
                if (read == -1) {
                    return byteArrayOutputStream.toString();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } while (byteArrayOutputStream.size() <= 20000000);
            throw new IOException("PAC too big");
        } catch (IllegalArgumentException unused2) {
            throw new IOException("Incorrect proxy type for " + uri);
        } catch (UnsupportedOperationException unused3) {
            throw new IOException("Unsupported URL connection type for " + uri);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.connectivity.PacProxyService$1] */
    public PacProxyService(Context context) {
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("android.pacproxyservice", 0);
        handlerThread.start();
        this.mNetThreadHandler = new Handler(handlerThread.getLooper());
        this.mPacRefreshIntent =
                PendingIntent.getBroadcast(
                        context, 0, new Intent("android.net.proxy.PAC_REFRESH"), 67108864);
        context.registerReceiver(
                new PacRefreshIntentReceiver(), new IntentFilter("android.net.proxy.PAC_REFRESH"));
    }

    public final void addListener(IPacProxyInstalledListener iPacProxyInstalledListener) {
        PermissionUtils.enforceNetworkStackPermissionOr(
                this.mContext, new String[] {"android.permission.NETWORK_SETTINGS"});
        this.mCallbacks.register(iPacProxyInstalledListener);
    }

    public final void bind() {
        if (this.mContext == null) {
            Log.e("PacProxyService", "No context for binding");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.android.pacprocessor", "com.android.pacprocessor.PacService");
        if (this.mProxyConnection != null && this.mConnection != null) {
            this.mNetThreadHandler.post(this.mPacDownloader);
            return;
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this, 0);
        this.mConnection = anonymousClass2;
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.SYSTEM;
        context.bindServiceAsUser(intent, anonymousClass2, 1073741829, userHandle);
        Intent intent2 = new Intent();
        intent2.setClassName("com.android.proxyhandler", "com.android.proxyhandler.ProxyService");
        AnonymousClass2 anonymousClass22 = new AnonymousClass2(this, 1);
        this.mProxyConnection = anonymousClass22;
        this.mContext.bindServiceAsUser(
                intent2, anonymousClass22, 1073741829, this.mNetThreadHandler, userHandle);
    }

    public final AlarmManager getAlarmManager() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        }
        return this.mAlarmManager;
    }

    public final void removeListener(IPacProxyInstalledListener iPacProxyInstalledListener) {
        PermissionUtils.enforceNetworkStackPermissionOr(
                this.mContext, new String[] {"android.permission.NETWORK_SETTINGS"});
        this.mCallbacks.unregister(iPacProxyInstalledListener);
    }

    public final void setCurrentProxyScriptUrl(ProxyInfo proxyInfo) {
        PermissionUtils.enforceNetworkStackPermissionOr(
                this.mContext, new String[] {"android.permission.NETWORK_SETTINGS"});
        synchronized (this.mBroadcastStateLock) {
            if (proxyInfo != null) {
                try {
                    if (!Uri.EMPTY.equals(proxyInfo.getPacFileUrl())) {
                        if (!proxyInfo.getPacFileUrl().equals(this.mPacUrl)
                                || proxyInfo.getPort() <= 0) {
                            this.mPacUrl = proxyInfo.getPacFileUrl();
                            this.mCurrentDelay = 0;
                            this.mHasSentBroadcast = false;
                            this.mHasDownloaded = false;
                            getAlarmManager().cancel(this.mPacRefreshIntent);
                            bind();
                        }
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            getAlarmManager().cancel(this.mPacRefreshIntent);
            synchronized (this.mProxyLock) {
                this.mPacUrl = Uri.EMPTY;
                this.mCurrentPac = null;
                if (this.mProxyService != null) {
                    AnonymousClass2 anonymousClass2 = this.mConnection;
                    if (anonymousClass2 != null) {
                        this.mContext.unbindService(anonymousClass2);
                        this.mConnection = null;
                    }
                    AnonymousClass2 anonymousClass22 = this.mProxyConnection;
                    if (anonymousClass22 != null) {
                        this.mContext.unbindService(anonymousClass22);
                        this.mProxyConnection = null;
                    }
                    this.mProxyService = null;
                    this.mLastPort = -1;
                }
            }
        }
    }

    public final void setDownloadIn(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String str = SystemProperties.get("conn.pac_change_delay", "8 32 120 14400 43200");
        String string = Settings.Global.getString(contentResolver, "pac_change_delay");
        if (string != null) {
            str = string;
        }
        String[] split = str.split(" ");
        long parseLong = i < split.length ? Long.parseLong(split[i]) : 0L;
        getAlarmManager()
                .set(3, SystemClock.elapsedRealtime() + (parseLong * 1000), this.mPacRefreshIntent);
    }
}
