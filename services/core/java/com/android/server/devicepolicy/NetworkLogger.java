package com.android.server.devicepolicy;

import android.app.admin.ConnectEvent;
import android.app.admin.DnsEvent;
import android.app.admin.NetworkEvent;
import android.content.pm.PackageManagerInternal;
import android.net.IIpConnectivityMetrics;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;

import com.android.server.ServiceThread;
import com.android.server.net.BaseNetdEventCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NetworkLogger {
    public final DevicePolicyManagerService mDpm;
    public ServiceThread mHandlerThread;
    public IIpConnectivityMetrics mIpConnectivityMetrics;
    public final AtomicBoolean mIsLoggingEnabled = new AtomicBoolean(false);
    public final AnonymousClass1 mNetdEventCallback =
            new BaseNetdEventCallback() { // from class:
                                          // com.android.server.devicepolicy.NetworkLogger.1
                public final void onConnectEvent(String str, int i, long j, int i2) {
                    if (NetworkLogger.this.mIsLoggingEnabled.get()) {
                        int i3 = NetworkLogger.this.mTargetUserId;
                        if (i3 == -1 || i3 == UserHandle.getUserId(i2)) {
                            sendNetworkEvent(
                                    new ConnectEvent(
                                            str, i, NetworkLogger.this.mPm.getNameForUid(i2), j));
                        }
                    }
                }

                public final void onDnsEvent(
                        int i,
                        int i2,
                        int i3,
                        String str,
                        String[] strArr,
                        int i4,
                        long j,
                        int i5) {
                    if (NetworkLogger.this.mIsLoggingEnabled.get()) {
                        int i6 = NetworkLogger.this.mTargetUserId;
                        if (i6 == -1 || i6 == UserHandle.getUserId(i5)) {
                            sendNetworkEvent(
                                    new DnsEvent(
                                            str,
                                            strArr,
                                            i4,
                                            NetworkLogger.this.mPm.getNameForUid(i5),
                                            j));
                        }
                    }
                }

                public final void sendNetworkEvent(NetworkEvent networkEvent) {
                    Message obtainMessage =
                            NetworkLogger.this.mNetworkLoggingHandler.obtainMessage(1);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("network_event", networkEvent);
                    obtainMessage.setData(bundle);
                    NetworkLogger.this.mNetworkLoggingHandler.sendMessage(obtainMessage);
                }
            };
    public NetworkLoggingHandler mNetworkLoggingHandler;
    public final PackageManagerInternal mPm;
    public final int mTargetUserId;

    public NetworkLogger(
            DevicePolicyManagerService devicePolicyManagerService,
            PackageManagerInternal packageManagerInternal,
            int i) {
        this.mDpm = devicePolicyManagerService;
        this.mPm = packageManagerInternal;
        this.mTargetUserId = i;
    }

    public final void discardLogs() {
        NetworkLoggingHandler networkLoggingHandler = this.mNetworkLoggingHandler;
        if (networkLoggingHandler != null) {
            synchronized (networkLoggingHandler) {
                networkLoggingHandler.mBatches.clear();
                networkLoggingHandler.mNetworkEvents = new ArrayList();
                Slog.d("NetworkLoggingHandler", "Discarded all network logs");
            }
        }
    }

    public final List retrieveLogs(final long j) {
        final NetworkLoggingHandler networkLoggingHandler = this.mNetworkLoggingHandler;
        synchronized (networkLoggingHandler) {
            int indexOfKey = networkLoggingHandler.mBatches.indexOfKey(j);
            if (indexOfKey < 0) {
                return null;
            }
            networkLoggingHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.devicepolicy.NetworkLoggingHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NetworkLoggingHandler networkLoggingHandler2 =
                                    NetworkLoggingHandler.this;
                            long j2 = j;
                            synchronized (networkLoggingHandler2) {
                                while (networkLoggingHandler2.mBatches.size() > 0
                                        && networkLoggingHandler2.mBatches.keyAt(0) <= j2) {
                                    try {
                                        networkLoggingHandler2.mBatches.removeAt(0);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    },
                    300000L);
            networkLoggingHandler.mLastRetrievedBatchToken = j;
            return (List) networkLoggingHandler.mBatches.valueAt(indexOfKey);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startNetworkLogging() {
        /*
            r11 = this;
            java.lang.String r0 = "NetworkLogger"
            java.lang.String r1 = "Starting network logging."
            android.util.Log.d(r0, r1)
            android.net.IIpConnectivityMetrics r1 = r11.mIpConnectivityMetrics
            r2 = 0
            r3 = 1
            if (r1 == 0) goto Lf
        Ld:
            r1 = r3
            goto L28
        Lf:
            com.android.server.devicepolicy.DevicePolicyManagerService r1 = r11.mDpm
            com.android.server.devicepolicy.DevicePolicyManagerService$Injector r1 = r1.mInjector
            r1.getClass()
            java.lang.String r1 = "connmetrics"
            android.os.IBinder r1 = android.os.ServiceManager.getService(r1)
            android.net.IIpConnectivityMetrics r1 = android.net.IIpConnectivityMetrics.Stub.asInterface(r1)
            if (r1 != 0) goto L25
            r1 = r2
            goto L28
        L25:
            r11.mIpConnectivityMetrics = r1
            goto Ld
        L28:
            if (r1 != 0) goto L30
            java.lang.String r11 = "Failed to register callback with IIpConnectivityMetrics."
            android.util.Slog.wtf(r0, r11)
            return r2
        L30:
            android.net.IIpConnectivityMetrics r1 = r11.mIpConnectivityMetrics     // Catch: android.os.RemoteException -> L63
            com.android.server.devicepolicy.NetworkLogger$1 r4 = r11.mNetdEventCallback     // Catch: android.os.RemoteException -> L63
            boolean r1 = r1.addNetdEventCallback(r3, r4)     // Catch: android.os.RemoteException -> L63
            if (r1 == 0) goto L65
            com.android.server.ServiceThread r1 = new com.android.server.ServiceThread     // Catch: android.os.RemoteException -> L63
            r4 = 10
            r1.<init>(r4, r0, r2)     // Catch: android.os.RemoteException -> L63
            r11.mHandlerThread = r1     // Catch: android.os.RemoteException -> L63
            r1.start()     // Catch: android.os.RemoteException -> L63
            com.android.server.devicepolicy.NetworkLoggingHandler r1 = new com.android.server.devicepolicy.NetworkLoggingHandler     // Catch: android.os.RemoteException -> L63
            com.android.server.ServiceThread r4 = r11.mHandlerThread     // Catch: android.os.RemoteException -> L63
            android.os.Looper r6 = r4.getLooper()     // Catch: android.os.RemoteException -> L63
            com.android.server.devicepolicy.DevicePolicyManagerService r7 = r11.mDpm     // Catch: android.os.RemoteException -> L63
            int r10 = r11.mTargetUserId     // Catch: android.os.RemoteException -> L63
            r8 = 0
            r5 = r1
            r5.<init>(r6, r7, r8, r10)     // Catch: android.os.RemoteException -> L63
            r11.mNetworkLoggingHandler = r1     // Catch: android.os.RemoteException -> L63
            r1.scheduleBatchFinalization()     // Catch: android.os.RemoteException -> L63
            java.util.concurrent.atomic.AtomicBoolean r11 = r11.mIsLoggingEnabled     // Catch: android.os.RemoteException -> L63
            r11.set(r3)     // Catch: android.os.RemoteException -> L63
            return r3
        L63:
            r11 = move-exception
            goto L66
        L65:
            return r2
        L66:
            java.lang.String r1 = "Failed to make remote calls to register the callback"
            android.util.Slog.wtf(r0, r1, r11)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.devicepolicy.NetworkLogger.startNetworkLogging():boolean");
    }

    public final boolean stopNetworkLogging() {
        Log.d("NetworkLogger", "Stopping network logging");
        this.mIsLoggingEnabled.set(false);
        discardLogs();
        try {
            try {
                if (this.mIpConnectivityMetrics == null) {
                    this.mDpm.mInjector.getClass();
                    IIpConnectivityMetrics asInterface =
                            IIpConnectivityMetrics.Stub.asInterface(
                                    ServiceManager.getService("connmetrics"));
                    if (asInterface == null) {
                        Slog.wtf(
                                "NetworkLogger",
                                "Failed to unregister callback with IIpConnectivityMetrics.");
                        ServiceThread serviceThread = this.mHandlerThread;
                        if (serviceThread != null) {
                            serviceThread.quitSafely();
                        }
                        return true;
                    }
                    this.mIpConnectivityMetrics = asInterface;
                }
                boolean removeNetdEventCallback =
                        this.mIpConnectivityMetrics.removeNetdEventCallback(1);
                ServiceThread serviceThread2 = this.mHandlerThread;
                if (serviceThread2 != null) {
                    serviceThread2.quitSafely();
                }
                return removeNetdEventCallback;
            } catch (RemoteException e) {
                Slog.wtf(
                        "NetworkLogger",
                        "Failed to make remote calls to unregister the callback",
                        e);
                ServiceThread serviceThread3 = this.mHandlerThread;
                if (serviceThread3 != null) {
                    serviceThread3.quitSafely();
                }
                return true;
            }
        } catch (Throwable th) {
            ServiceThread serviceThread4 = this.mHandlerThread;
            if (serviceThread4 != null) {
                serviceThread4.quitSafely();
            }
            throw th;
        }
    }
}
