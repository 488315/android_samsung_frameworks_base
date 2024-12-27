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
import com.android.media.flags.Flags;
import com.android.server.media.MediaRouter2ServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MediaRoute2ProviderWatcher {
    public static final boolean DEBUG = Log.isLoggable("MR2ProviderWatcher", 3);
    public static final PackageManager.ResolveInfoFlags RESOLVE_INFO_FLAGS = PackageManager.ResolveInfoFlags.of(64);
    public final Callback mCallback;
    public final Context mContext;
    public final Handler mHandler;
    public final PackageManager mPackageManager;
    public boolean mRunning;
    public final int mUserId;
    public final ArrayList mProxies = new ArrayList();
    public final AnonymousClass1 mScanPackagesReceiver = new BroadcastReceiver() { // from class: com.android.server.media.MediaRoute2ProviderWatcher.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (MediaRoute2ProviderWatcher.DEBUG) {
                Slog.d("MR2ProviderWatcher", "Received package manager broadcast: " + intent);
            }
            MediaRoute2ProviderWatcher mediaRoute2ProviderWatcher = MediaRoute2ProviderWatcher.this;
            mediaRoute2ProviderWatcher.getClass();
            MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0 mediaRoute2ProviderWatcher$$ExternalSyntheticLambda0 = new MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(mediaRoute2ProviderWatcher);
            Handler handler = mediaRoute2ProviderWatcher.mHandler;
            if (handler.hasCallbacks(mediaRoute2ProviderWatcher$$ExternalSyntheticLambda0)) {
                return;
            }
            handler.post(new MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(mediaRoute2ProviderWatcher));
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {
    }

    public static void $r8$lambda$uxDP7YbvEhxettSyL1qhqc8pTDY(MediaRoute2ProviderWatcher mediaRoute2ProviderWatcher) {
        Callback callback;
        boolean z;
        if (mediaRoute2ProviderWatcher.mRunning) {
            Iterator it = mediaRoute2ProviderWatcher.mPackageManager.queryIntentServicesAsUser(new Intent("android.media.MediaRoute2ProviderService"), RESOLVE_INFO_FLAGS, mediaRoute2ProviderWatcher.mUserId).iterator();
            int i = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                callback = mediaRoute2ProviderWatcher.mCallback;
                if (!hasNext) {
                    break;
                }
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null) {
                    Iterator<String> categoriesIterator = resolveInfo.filter.categoriesIterator();
                    if (categoriesIterator != null) {
                        boolean z2 = false;
                        while (categoriesIterator.hasNext()) {
                            z2 |= "android.media.MediaRoute2ProviderService.SELF_SCAN_ONLY".equals(categoriesIterator.next());
                        }
                        z = z2;
                    } else {
                        z = false;
                    }
                    String str = serviceInfo.packageName;
                    String str2 = serviceInfo.name;
                    int size = mediaRoute2ProviderWatcher.mProxies.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            i2 = -1;
                            break;
                        }
                        MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy = (MediaRoute2ProviderServiceProxy) mediaRoute2ProviderWatcher.mProxies.get(i2);
                        if (mediaRoute2ProviderServiceProxy.mComponentName.getPackageName().equals(str) && mediaRoute2ProviderServiceProxy.mComponentName.getClassName().equals(str2)) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i2 < 0) {
                        MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy2 = new MediaRoute2ProviderServiceProxy(mediaRoute2ProviderWatcher.mContext, mediaRoute2ProviderWatcher.mHandler.getLooper(), new ComponentName(serviceInfo.packageName, serviceInfo.name), z, mediaRoute2ProviderWatcher.mUserId);
                        Slog.i("MR2ProviderWatcher", "Enabling proxy for MediaRoute2ProviderService: " + mediaRoute2ProviderServiceProxy2.mComponentName);
                        mediaRoute2ProviderServiceProxy2.start(false);
                        int i3 = i + 1;
                        mediaRoute2ProviderWatcher.mProxies.add(i, mediaRoute2ProviderServiceProxy2);
                        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) callback;
                        userHandler.getClass();
                        mediaRoute2ProviderServiceProxy2.mCallback = userHandler;
                        userHandler.mRouteProviders.add(mediaRoute2ProviderServiceProxy2);
                        MediaRouter2ServiceImpl.UserRecord userRecord = userHandler.mUserRecord;
                        mediaRoute2ProviderServiceProxy2.updateDiscoveryPreference(userRecord.mActivelyScanningPackages, userRecord.mCompositeDiscoveryPreference);
                        i = i3;
                    } else if (i2 >= i) {
                        ((MediaRoute2ProviderServiceProxy) mediaRoute2ProviderWatcher.mProxies.get(i2)).start(!Flags.enablePreventionOfKeepAliveRouteProviders());
                        Collections.swap(mediaRoute2ProviderWatcher.mProxies, i2, i);
                        i++;
                    }
                }
            }
            if (i < mediaRoute2ProviderWatcher.mProxies.size()) {
                for (int size2 = mediaRoute2ProviderWatcher.mProxies.size() - 1; size2 >= i; size2--) {
                    MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy3 = (MediaRoute2ProviderServiceProxy) mediaRoute2ProviderWatcher.mProxies.get(size2);
                    Slog.i("MR2ProviderWatcher", "Disabling proxy for MediaRoute2ProviderService: " + mediaRoute2ProviderServiceProxy3.mComponentName);
                    ((MediaRouter2ServiceImpl.UserHandler) callback).mRouteProviders.remove(mediaRoute2ProviderServiceProxy3);
                    mediaRoute2ProviderWatcher.mProxies.remove(mediaRoute2ProviderServiceProxy3);
                    mediaRoute2ProviderServiceProxy3.stop();
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.media.MediaRoute2ProviderWatcher$1] */
    public MediaRoute2ProviderWatcher(Context context, Callback callback, Handler handler, int i) {
        this.mContext = context;
        this.mCallback = callback;
        this.mHandler = handler;
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
    }
}
