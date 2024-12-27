package com.android.server.tv;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TvRemoteProviderWatcher {
    public static final boolean DEBUG = Log.isLoggable("TvRemoteProviderWatcher", 2);
    public final Context mContext;
    public final Handler mHandler;
    public final Object mLock;
    public final PackageManager mPackageManager;
    public final ArrayList mProviderProxies;
    public boolean mRunning;
    public final AnonymousClass1 mScanPackagesReceiver;
    public final AnonymousClass2 mScanPackagesRunnable;
    public final Set mUnbundledServicePackages;
    public final int mUserId;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.tv.TvRemoteProviderWatcher$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.tv.TvRemoteProviderWatcher$2] */
    public TvRemoteProviderWatcher(Context context, Object obj) {
        Handler handler = new Handler(true);
        this.mProviderProxies = new ArrayList();
        this.mUnbundledServicePackages = new HashSet();
        this.mScanPackagesReceiver = new BroadcastReceiver() { // from class: com.android.server.tv.TvRemoteProviderWatcher.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (TvRemoteProviderWatcher.DEBUG) {
                    Slog.d("TvRemoteProviderWatcher", "Received package manager broadcast: " + intent);
                }
                TvRemoteProviderWatcher tvRemoteProviderWatcher = TvRemoteProviderWatcher.this;
                tvRemoteProviderWatcher.mHandler.post(tvRemoteProviderWatcher.mScanPackagesRunnable);
            }
        };
        this.mScanPackagesRunnable = new Runnable() { // from class: com.android.server.tv.TvRemoteProviderWatcher.2
            @Override // java.lang.Runnable
            public final void run() {
                TvRemoteProviderWatcher tvRemoteProviderWatcher = TvRemoteProviderWatcher.this;
                if (tvRemoteProviderWatcher.mRunning) {
                    boolean z = TvRemoteProviderWatcher.DEBUG;
                    if (z) {
                        Log.d("TvRemoteProviderWatcher", "scanPackages()");
                    }
                    Intent intent = new Intent("com.android.media.tv.remoteprovider.TvRemoteProvider");
                    PackageManager packageManager = tvRemoteProviderWatcher.mPackageManager;
                    int i = tvRemoteProviderWatcher.mUserId;
                    Iterator it = packageManager.queryIntentServicesAsUser(intent, 0, i).iterator();
                    int i2 = 0;
                    while (it.hasNext()) {
                        ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                        if (serviceInfo != null && tvRemoteProviderWatcher.verifyServiceTrusted(serviceInfo)) {
                            String str = serviceInfo.packageName;
                            String str2 = serviceInfo.name;
                            int size = tvRemoteProviderWatcher.mProviderProxies.size();
                            int i3 = 0;
                            while (true) {
                                if (i3 >= size) {
                                    i3 = -1;
                                    break;
                                }
                                TvRemoteProviderProxy tvRemoteProviderProxy = (TvRemoteProviderProxy) tvRemoteProviderWatcher.mProviderProxies.get(i3);
                                if (tvRemoteProviderProxy.mComponentName.getPackageName().equals(str) && tvRemoteProviderProxy.mComponentName.getClassName().equals(str2)) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                            if (i3 < 0) {
                                Context context2 = tvRemoteProviderWatcher.mContext;
                                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                                int i4 = serviceInfo.applicationInfo.uid;
                                TvRemoteProviderProxy tvRemoteProviderProxy2 = new TvRemoteProviderProxy(context2, tvRemoteProviderWatcher.mLock, componentName, i);
                                tvRemoteProviderProxy2.start();
                                tvRemoteProviderWatcher.mProviderProxies.add(i2, tvRemoteProviderProxy2);
                                i2++;
                            } else if (i3 >= i2) {
                                TvRemoteProviderProxy tvRemoteProviderProxy3 = (TvRemoteProviderProxy) tvRemoteProviderWatcher.mProviderProxies.get(i3);
                                tvRemoteProviderProxy3.start();
                                if (tvRemoteProviderProxy3.mRunning && !tvRemoteProviderProxy3.mConnected) {
                                    tvRemoteProviderProxy3.unbind();
                                    tvRemoteProviderProxy3.bind();
                                }
                                Collections.swap(tvRemoteProviderWatcher.mProviderProxies, i3, i2);
                                i2++;
                            }
                        }
                    }
                    if (z) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "scanPackages() targetIndex ", "TvRemoteProviderWatcher");
                    }
                    if (i2 < tvRemoteProviderWatcher.mProviderProxies.size()) {
                        for (int size2 = tvRemoteProviderWatcher.mProviderProxies.size() - 1; size2 >= i2; size2--) {
                            TvRemoteProviderProxy tvRemoteProviderProxy4 = (TvRemoteProviderProxy) tvRemoteProviderWatcher.mProviderProxies.get(size2);
                            tvRemoteProviderWatcher.mProviderProxies.remove(tvRemoteProviderProxy4);
                            if (tvRemoteProviderProxy4.mRunning) {
                                if (TvRemoteProviderProxy.DEBUG) {
                                    Slog.d("TvRemoteProviderProxy", tvRemoteProviderProxy4 + ": Stopping");
                                }
                                tvRemoteProviderProxy4.mRunning = false;
                                tvRemoteProviderProxy4.unbind();
                            }
                        }
                    }
                }
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = UserHandle.myUserId();
        this.mPackageManager = context.getPackageManager();
        this.mLock = obj;
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
        simpleStringSplitter.setString(context.getString(R.string.face_authenticated_no_confirmation_required));
        simpleStringSplitter.forEach(new Consumer() { // from class: com.android.server.tv.TvRemoteProviderWatcher$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                TvRemoteProviderWatcher tvRemoteProviderWatcher = TvRemoteProviderWatcher.this;
                tvRemoteProviderWatcher.getClass();
                String trim = ((String) obj2).trim();
                if (trim.isEmpty()) {
                    return;
                }
                ((HashSet) tvRemoteProviderWatcher.mUnbundledServicePackages).add(trim);
            }
        });
    }

    public boolean verifyServiceTrusted(ServiceInfo serviceInfo) {
        String str = serviceInfo.permission;
        if (str == null || !str.equals("android.permission.BIND_TV_REMOTE_SERVICE")) {
            StringBuilder sb = new StringBuilder("Ignoring atv remote provider service because it did not require the BIND_TV_REMOTE_SERVICE permission in its manifest: ");
            sb.append(serviceInfo.packageName);
            sb.append("/");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, serviceInfo.name, "TvRemoteProviderWatcher");
            return false;
        }
        if (((HashSet) this.mUnbundledServicePackages).contains(serviceInfo.packageName)) {
            if (this.mPackageManager.checkPermission("android.permission.TV_VIRTUAL_REMOTE_CONTROLLER", serviceInfo.packageName) == 0) {
                return true;
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Ignoring atv remote provider service because its package does not have TV_VIRTUAL_REMOTE_CONTROLLER permission: "), serviceInfo.packageName, "TvRemoteProviderWatcher");
            return false;
        }
        StringBuilder sb2 = new StringBuilder("Ignoring atv remote provider service because the package has not been set and/or whitelisted: ");
        sb2.append(serviceInfo.packageName);
        sb2.append("/");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb2, serviceInfo.name, "TvRemoteProviderWatcher");
        return false;
    }
}
