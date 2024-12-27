package com.android.server.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.util.Log;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class RemoteDisplayProviderWatcher {
    public static final boolean DEBUG = Log.isLoggable("RemoteDisplayProvider", 3);
    public final Callback mCallback;
    public final Context mContext;
    public final Handler mHandler;
    public final PackageManager mPackageManager;
    public boolean mRunning;
    public final int mUserId;
    public final ArrayList mProviders = new ArrayList();
    public final AnonymousClass1 mScanPackagesReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.media.RemoteDisplayProviderWatcher.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (RemoteDisplayProviderWatcher.DEBUG) {
                        Slog.d(
                                "RemoteDisplayProvider",
                                "Received package manager broadcast: " + intent);
                    }
                    RemoteDisplayProviderWatcher.m671$$Nest$mscanPackages(
                            RemoteDisplayProviderWatcher.this);
                }
            };
    public final AnonymousClass2 mScanPackagesRunnable =
            new Runnable() { // from class: com.android.server.media.RemoteDisplayProviderWatcher.2
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteDisplayProviderWatcher.m671$$Nest$mscanPackages(
                            RemoteDisplayProviderWatcher.this);
                }
            };

    public interface Callback {}

    /* renamed from: -$$Nest$mscanPackages, reason: not valid java name */
    public static void m671$$Nest$mscanPackages(
            RemoteDisplayProviderWatcher remoteDisplayProviderWatcher) {
        Callback callback;
        if (remoteDisplayProviderWatcher.mRunning) {
            Intent intent = new Intent("com.android.media.remotedisplay.RemoteDisplayProvider");
            PackageManager packageManager = remoteDisplayProviderWatcher.mPackageManager;
            int i = remoteDisplayProviderWatcher.mUserId;
            Iterator it = packageManager.queryIntentServicesAsUser(intent, 0, i).iterator();
            int i2 = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                int i3 = -1;
                callback = remoteDisplayProviderWatcher.mCallback;
                if (!hasNext) {
                    break;
                }
                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                if (serviceInfo != null) {
                    String str = serviceInfo.permission;
                    if (str == null || !str.equals("android.permission.BIND_REMOTE_DISPLAY")) {
                        StringBuilder sb =
                                new StringBuilder(
                                        "Ignoring remote display provider service because it did"
                                            + " not require the BIND_REMOTE_DISPLAY permission in"
                                            + " its manifest: ");
                        sb.append(serviceInfo.packageName);
                        sb.append("/");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                sb, serviceInfo.name, "RemoteDisplayProvider");
                    } else if (remoteDisplayProviderWatcher.mPackageManager.checkPermission(
                                    "android.permission.REMOTE_DISPLAY_PROVIDER",
                                    serviceInfo.packageName)
                            != 0) {
                        StringBuilder sb2 =
                                new StringBuilder(
                                        "Ignoring remote display provider service because it does"
                                            + " not have the REMOTE_DISPLAY_PROVIDER permission: ");
                        sb2.append(serviceInfo.packageName);
                        sb2.append("/");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                sb2, serviceInfo.name, "RemoteDisplayProvider");
                    } else {
                        String str2 = serviceInfo.packageName;
                        String str3 = serviceInfo.name;
                        int size = remoteDisplayProviderWatcher.mProviders.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size) {
                                break;
                            }
                            RemoteDisplayProviderProxy remoteDisplayProviderProxy =
                                    (RemoteDisplayProviderProxy)
                                            remoteDisplayProviderWatcher.mProviders.get(i4);
                            if (remoteDisplayProviderProxy
                                            .mComponentName
                                            .getPackageName()
                                            .equals(str2)
                                    && remoteDisplayProviderProxy
                                            .mComponentName
                                            .getClassName()
                                            .equals(str3)) {
                                i3 = i4;
                                break;
                            }
                            i4++;
                        }
                        if (i3 < 0) {
                            RemoteDisplayProviderProxy remoteDisplayProviderProxy2 =
                                    new RemoteDisplayProviderProxy(
                                            remoteDisplayProviderWatcher.mContext,
                                            new ComponentName(
                                                    serviceInfo.packageName, serviceInfo.name),
                                            i);
                            remoteDisplayProviderProxy2.start();
                            int i5 = i2 + 1;
                            remoteDisplayProviderWatcher.mProviders.add(
                                    i2, remoteDisplayProviderProxy2);
                            MediaRouterService.UserHandler userHandler =
                                    (MediaRouterService.UserHandler) callback;
                            userHandler.getClass();
                            remoteDisplayProviderProxy2.mDisplayStateCallback = userHandler;
                            remoteDisplayProviderProxy2.setDiscoveryMode(
                                    userHandler.mDiscoveryMode);
                            remoteDisplayProviderProxy2.setSelectedDisplay(null);
                            MediaRouterService.UserHandler.ProviderRecord providerRecord =
                                    new MediaRouterService.UserHandler.ProviderRecord(
                                            remoteDisplayProviderProxy2);
                            userHandler.mProviderRecords.add(providerRecord);
                            providerRecord.updateDescriptor(
                                    remoteDisplayProviderProxy2.mDisplayState);
                            userHandler.scheduleUpdateClientState();
                            i2 = i5;
                        } else if (i3 >= i2) {
                            RemoteDisplayProviderProxy remoteDisplayProviderProxy3 =
                                    (RemoteDisplayProviderProxy)
                                            remoteDisplayProviderWatcher.mProviders.get(i3);
                            remoteDisplayProviderProxy3.start();
                            if (remoteDisplayProviderProxy3.mActiveConnection == null
                                    && remoteDisplayProviderProxy3.mRunning
                                    && (remoteDisplayProviderProxy3.mDiscoveryMode != 0
                                            || remoteDisplayProviderProxy3.mSelectedDisplayId
                                                    != null)) {
                                remoteDisplayProviderProxy3.unbind();
                                remoteDisplayProviderProxy3.bind();
                            }
                            Collections.swap(remoteDisplayProviderWatcher.mProviders, i3, i2);
                            i2++;
                        }
                    }
                }
            }
            if (i2 < remoteDisplayProviderWatcher.mProviders.size()) {
                for (int size2 = remoteDisplayProviderWatcher.mProviders.size() - 1;
                        size2 >= i2;
                        size2--) {
                    RemoteDisplayProviderProxy remoteDisplayProviderProxy4 =
                            (RemoteDisplayProviderProxy)
                                    remoteDisplayProviderWatcher.mProviders.get(size2);
                    MediaRouterService.UserHandler userHandler2 =
                            (MediaRouterService.UserHandler) callback;
                    int size3 = userHandler2.mProviderRecords.size();
                    int i6 = 0;
                    while (true) {
                        if (i6 >= size3) {
                            i6 = -1;
                            break;
                        } else if (((MediaRouterService.UserHandler.ProviderRecord)
                                                userHandler2.mProviderRecords.get(i6))
                                        .mProvider
                                == remoteDisplayProviderProxy4) {
                            break;
                        } else {
                            i6++;
                        }
                    }
                    if (i6 >= 0) {
                        ((MediaRouterService.UserHandler.ProviderRecord)
                                        userHandler2.mProviderRecords.remove(i6))
                                .updateDescriptor(null);
                        remoteDisplayProviderProxy4.mDisplayStateCallback = null;
                        remoteDisplayProviderProxy4.setDiscoveryMode(0);
                        userHandler2.checkSelectedRouteState();
                        userHandler2.scheduleUpdateClientState();
                    }
                    remoteDisplayProviderWatcher.mProviders.remove(remoteDisplayProviderProxy4);
                    remoteDisplayProviderProxy4.stop();
                }
            }
        }
    }

    public RemoteDisplayProviderWatcher(
            Context context, Callback callback, Handler handler, int i) {
        this.mContext = context;
        this.mCallback = callback;
        this.mHandler = handler;
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
    }
}
