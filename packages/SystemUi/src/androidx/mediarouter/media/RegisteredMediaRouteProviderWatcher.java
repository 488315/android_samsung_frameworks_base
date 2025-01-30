package androidx.mediarouter.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.util.Log;
import androidx.mediarouter.media.MediaRouter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RegisteredMediaRouteProviderWatcher {
    public final Callback mCallback;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public boolean mRunning;
    public final ArrayList mProviders = new ArrayList();
    public final C03461 mScanPackagesReceiver = new BroadcastReceiver() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            RegisteredMediaRouteProviderWatcher.this.scanPackages();
        }
    };
    public final RunnableC03472 mScanPackagesRunnable = new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher.2
        @Override // java.lang.Runnable
        public final void run() {
            RegisteredMediaRouteProviderWatcher.this.scanPackages();
        }
    };
    public final Handler mHandler = new Handler();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher$2] */
    public RegisteredMediaRouteProviderWatcher(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mPackageManager = context.getPackageManager();
    }

    public final void scanPackages() {
        ArrayList arrayList;
        Callback callback;
        int i;
        boolean z;
        if (this.mRunning) {
            new ArrayList();
            Intent intent = new Intent("android.media.MediaRoute2ProviderService");
            ArrayList arrayList2 = new ArrayList();
            PackageManager packageManager = this.mPackageManager;
            Iterator<ResolveInfo> it = packageManager.queryIntentServices(intent, 0).iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().serviceInfo);
            }
            Iterator<ResolveInfo> it2 = packageManager.queryIntentServices(new Intent("android.media.MediaRouteProviderService"), 0).iterator();
            int i2 = 0;
            while (true) {
                boolean hasNext = it2.hasNext();
                arrayList = this.mProviders;
                boolean z2 = true;
                callback = this.mCallback;
                if (!hasNext) {
                    break;
                }
                ServiceInfo serviceInfo = it2.next().serviceInfo;
                if (serviceInfo != null) {
                    if (MediaRouter.sGlobal == null ? false : MediaRouter.getGlobalRouter().mTransferReceiverDeclared) {
                        if (!arrayList2.isEmpty()) {
                            Iterator it3 = arrayList2.iterator();
                            while (it3.hasNext()) {
                                ServiceInfo serviceInfo2 = (ServiceInfo) it3.next();
                                if (serviceInfo.packageName.equals(serviceInfo2.packageName) && serviceInfo.name.equals(serviceInfo2.name)) {
                                    z = true;
                                    break;
                                }
                            }
                        }
                        z = false;
                        if (z) {
                        }
                    }
                    String str = serviceInfo.packageName;
                    String str2 = serviceInfo.name;
                    int size = arrayList.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            i3 = -1;
                            break;
                        }
                        RegisteredMediaRouteProvider registeredMediaRouteProvider = (RegisteredMediaRouteProvider) arrayList.get(i3);
                        if (registeredMediaRouteProvider.mComponentName.getPackageName().equals(str) && registeredMediaRouteProvider.mComponentName.getClassName().equals(str2)) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i3 < 0) {
                        RegisteredMediaRouteProvider registeredMediaRouteProvider2 = new RegisteredMediaRouteProvider(this.mContext, new ComponentName(serviceInfo.packageName, serviceInfo.name));
                        registeredMediaRouteProvider2.mControllerCallback = new RegisteredMediaRouteProviderWatcher$$ExternalSyntheticLambda0(this, registeredMediaRouteProvider2);
                        registeredMediaRouteProvider2.start();
                        i = i2 + 1;
                        arrayList.add(i2, registeredMediaRouteProvider2);
                        ((MediaRouter.GlobalMediaRouter) callback).addProvider(registeredMediaRouteProvider2);
                    } else if (i3 >= i2) {
                        RegisteredMediaRouteProvider registeredMediaRouteProvider3 = (RegisteredMediaRouteProvider) arrayList.get(i3);
                        registeredMediaRouteProvider3.start();
                        if (registeredMediaRouteProvider3.mActiveConnection == null) {
                            if (!registeredMediaRouteProvider3.mStarted || (registeredMediaRouteProvider3.mDiscoveryRequest == null && registeredMediaRouteProvider3.mControllerConnections.isEmpty())) {
                                z2 = false;
                            }
                            if (z2) {
                                registeredMediaRouteProvider3.unbind();
                                registeredMediaRouteProvider3.bind();
                            }
                        }
                        i = i2 + 1;
                        Collections.swap(arrayList, i3, i2);
                    }
                    i2 = i;
                }
            }
            if (i2 < arrayList.size()) {
                for (int size2 = arrayList.size() - 1; size2 >= i2; size2--) {
                    RegisteredMediaRouteProvider registeredMediaRouteProvider4 = (RegisteredMediaRouteProvider) arrayList.get(size2);
                    MediaRouter.GlobalMediaRouter globalMediaRouter = (MediaRouter.GlobalMediaRouter) callback;
                    MediaRouter.ProviderInfo findProviderInfo = globalMediaRouter.findProviderInfo(registeredMediaRouteProvider4);
                    if (findProviderInfo != null) {
                        registeredMediaRouteProvider4.getClass();
                        MediaRouter.checkCallingThread();
                        registeredMediaRouteProvider4.mCallback = null;
                        registeredMediaRouteProvider4.setDiscoveryRequest(null);
                        globalMediaRouter.updateProviderContents(findProviderInfo, null);
                        if (MediaRouter.DEBUG) {
                            Log.d("MediaRouter", "Provider removed: " + findProviderInfo);
                        }
                        globalMediaRouter.mCallbackHandler.post(514, findProviderInfo);
                        globalMediaRouter.mProviders.remove(findProviderInfo);
                    }
                    arrayList.remove(registeredMediaRouteProvider4);
                    registeredMediaRouteProvider4.mControllerCallback = null;
                    if (registeredMediaRouteProvider4.mStarted) {
                        if (RegisteredMediaRouteProvider.DEBUG) {
                            Log.d("MediaRouteProviderProxy", registeredMediaRouteProvider4 + ": Stopping");
                        }
                        registeredMediaRouteProvider4.mStarted = false;
                        registeredMediaRouteProvider4.updateBinding();
                    }
                }
            }
        }
    }
}
