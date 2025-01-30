package com.samsung.android.sdk.scs.base.connection;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.AbstractC0950x8906c950;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.scs.p047ai.sdkcommon.feature.FeatureConfig;
import com.samsung.android.sdk.scs.base.feature.Feature;
import com.samsung.android.sdk.scs.base.feature.FeatureStatusCache;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.FeatureHelper;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ServiceExecutor extends ThreadPoolExecutor implements InternalServiceConnectionListener, Application.ActivityLifecycleCallbacks {
    public final Condition mConnectionCondition;
    public final C47641 mConnectionListener;
    public final ReentrantLock mConnectionLock;
    public final ConnectionManager mConnectionManager;
    public final Context mContext;
    public boolean mIsConnected;
    public final AtomicInteger mTaskCount;

    /* renamed from: -$$Nest$munlockConnection, reason: not valid java name */
    public static void m2792$$Nest$munlockConnection(ServiceExecutor serviceExecutor, boolean z, String str) {
        serviceExecutor.mConnectionLock.lock();
        try {
            serviceExecutor.mIsConnected = z;
            Log.m266d("ScsApi@ServiceExecutor", str);
            serviceExecutor.mConnectionCondition.signalAll();
        } finally {
            serviceExecutor.mConnectionLock.unlock();
        }
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [com.samsung.android.sdk.scs.base.connection.ServiceExecutor$1] */
    public ServiceExecutor(Context context, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mConnectionLock = reentrantLock;
        this.mConnectionCondition = reentrantLock.newCondition();
        this.mIsConnected = false;
        this.mConnectionListener = new InternalServiceConnectionListener() { // from class: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.1
            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onConnected(ComponentName componentName, IBinder iBinder) {
                Log.m266d("ScsApi@ServiceExecutor", "onConnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onConnected(componentName, iBinder);
                ServiceExecutor.m2792$$Nest$munlockConnection(serviceExecutor, true, "connected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onDisconnected(ComponentName componentName) {
                Log.m266d("ScsApi@ServiceExecutor", "onDisconnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onDisconnected(componentName);
                ServiceExecutor.m2792$$Nest$munlockConnection(serviceExecutor, false, "disconnected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onError() {
                Log.m266d("ScsApi@ServiceExecutor", "onError");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.getClass();
                ServiceExecutor.m2792$$Nest$munlockConnection(serviceExecutor, false, "onError, signal all");
            }
        };
        allowCoreThreadTimeOut(true);
        Log.m266d("ScsApi@ServiceExecutor", "use application context");
        this.mContext = context.getApplicationContext();
        this.mTaskCount = new AtomicInteger(0);
        this.mConnectionManager = new ConnectionManager();
        Log.m266d("ScsApi@ServiceExecutor", "ServiceExecutor. ctor()");
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public final void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        this.mTaskCount.getAndDecrement();
        Log.m266d("ScsApi@ServiceExecutor", "afterExecute(). mTaskCount: " + this.mTaskCount);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01e9  */
    @Override // java.util.concurrent.ThreadPoolExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void beforeExecute(Thread thread, Runnable runnable) {
        int i;
        boolean z;
        int i2;
        PackageInfo packageInfo;
        String string;
        FeatureConfig featureConfig;
        super.beforeExecute(thread, runnable);
        Object[] objArr = {this, runnable};
        StringBuilder sb = new StringBuilder("task");
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i = 2;
            if (i4 >= 2) {
                break;
            }
            Object obj = objArr[i4];
            if (obj != null) {
                String simpleName = obj.getClass().getSimpleName();
                String hexString = Integer.toHexString(obj.hashCode());
                if (sb.length() > 0) {
                    sb.append(" >> ");
                }
                sb.append(simpleName);
                sb.append("@");
                sb.append(hexString);
            }
            i4++;
        }
        Log.m268i("ScsApi@ServiceExecutor", sb.toString());
        if (runnable instanceof TaskRunnable) {
            String featureName = ((TaskRunnable) runnable).getFeatureName();
            Integer num = (Integer) FeatureStatusCache.statusMap.get(featureName);
            if ((num == null ? -1000 : num.intValue()) == -1000) {
                Context context = this.mContext;
                Map map = Feature.sinceVersionMap;
                Log.m268i("ScsApi@Feature", "checkFeature() : " + featureName + ", sdk : 3.1.24");
                if (context == null || featureName == null) {
                    Log.m267e("ScsApi@Feature", "checkFeature(). input is null. context: " + context + ", feature: " + featureName);
                    i = 300;
                } else {
                    try {
                        z = SemEmergencyManager.isEmergencyMode(context);
                    } catch (Error | Exception e) {
                        Log.m267e("ScsApi@FrameworkWrapper", e.getMessage());
                        z = false;
                    }
                    if (z) {
                        Log.m267e("ScsApi@Feature", "checkFeature(). not supported in emergency mode");
                        i = 8;
                        FeatureStatusCache.setStatus(8, featureName);
                    } else {
                        List list = Feature.SUPPORTED_SBIS_FEATURES;
                        boolean contains = list.contains(featureName);
                        List list2 = Feature.SUPPORTED_SIVS_FEATURES;
                        String str = contains ? "com.samsung.android.sbrowserintelligenceservice" : list2.contains(featureName) ? "com.samsung.android.intellivoiceservice" : "com.samsung.android.scs";
                        try {
                            if (context.getPackageManager().getApplicationInfo(str, 128).enabled) {
                                String str2 = list.contains(featureName) ? "scs_sbis_supported_feature_info" : list2.contains(featureName) ? "scs_sivs_supported_feature_info" : "scs_core_supported_feature_info";
                                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("getFeatureVersionFromSettings(), serviceApp : ", str, ", feature : ", featureName, ", settingKey : ");
                                m87m.append(str2);
                                Log.m266d("ScsApi@FeatureHelper", m87m.toString());
                                try {
                                    packageInfo = context.getPackageManager().getPackageInfo(str, 128);
                                    try {
                                        string = Settings.Global.getString(context.getContentResolver(), str2);
                                    } catch (Exception e2) {
                                        android.util.Log.e(Log.concatPrefixTag("ScsApi@FeatureHelper"), "Failed to getString from global settings.", e2);
                                    }
                                } catch (PackageManager.NameNotFoundException e3) {
                                    android.util.Log.e(Log.concatPrefixTag("ScsApi@FeatureHelper"), "Failed to get package info.", e3);
                                }
                                if (!TextUtils.isEmpty(string)) {
                                    try {
                                        featureConfig = FeatureHelper.getFeatureConfig(string);
                                    } catch (Exception e4) {
                                        android.util.Log.d(Log.concatPrefixTag("ScsApi@FeatureHelper"), "Unexpected behaviour when reading global settings", e4);
                                    }
                                    if (packageInfo.versionName.compareTo(featureConfig.getAppVersion()) == 0) {
                                        Integer orDefault = featureConfig.getFeatures().getOrDefault(featureName, -2);
                                        i2 = orDefault != null ? orDefault.intValue() : -2;
                                        Log.m266d("ScsApi@FeatureHelper", "Get feature version from global settings. feature : " + featureName + ", version : " + i2);
                                        if (i2 == -2) {
                                            Uri parse = Uri.parse(list.contains(featureName) ? "content://com.samsung.android.sbrowserintelligenceservice.feature" : list2.contains(featureName) ? "content://com.samsung.android.intellivoiceservice.feature" : "content://com.samsung.android.scs.feature");
                                            Log.m266d("ScsApi@FeatureHelper", "getFeatureVersionFromProvider()");
                                            Bundle bundle = null;
                                            try {
                                                bundle = context.getContentResolver().call(parse, "featureSupportRequest", featureName, (Bundle) null);
                                            } catch (Exception e5) {
                                                Log.m267e("ScsApi@FeatureHelper", "checkScsFeature(). " + e5.getMessage());
                                            }
                                            if (bundle == null) {
                                                Log.m267e("ScsApi@FeatureHelper", "checkScsFeature(). retBundle == null!!!");
                                                i2 = -2;
                                            } else {
                                                i2 = bundle.getInt("constVersion");
                                            }
                                        }
                                        if (i2 != -2) {
                                            Log.m267e("ScsApi@Feature", "checkScsFeature(). retBundle == null!!!");
                                            i3 = 2000;
                                        } else if (i2 == 0) {
                                            android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), PathParser$$ExternalSyntheticOutline0.m29m("checkScsFeature(). ", featureName, " is not available!!"));
                                            i3 = 5;
                                        } else {
                                            if (i2 == -1) {
                                                android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), PathParser$$ExternalSyntheticOutline0.m29m("checkScsFeature(). SCS doesn't know ", featureName, ". SCS update might be required."));
                                            } else {
                                                Map map2 = Feature.sinceVersionMap;
                                                int intValue = map2.containsKey(featureName) ? ((Integer) map2.get(featureName)).intValue() : Integer.MAX_VALUE;
                                                if (i2 < intValue) {
                                                    StringBuilder m92m = AbstractC0950x8906c950.m92m("checkScsFeature(). ", featureName, ", scsVersion: ", i2, ", sinceVersion: ");
                                                    m92m.append(intValue);
                                                    Log.m267e("ScsApi@Feature", m92m.toString());
                                                }
                                            }
                                            i3 = 3;
                                        }
                                        FeatureStatusCache.setStatus(i3, featureName);
                                        i = i3;
                                    }
                                }
                                i2 = -2;
                                if (i2 == -2) {
                                }
                                if (i2 != -2) {
                                }
                                FeatureStatusCache.setStatus(i3, featureName);
                                i = i3;
                            } else {
                                android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), "checkFeature(). " + str + " has disabled.");
                                FeatureStatusCache.setStatus(2, featureName);
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), PathParser$$ExternalSyntheticOutline0.m29m("dump(), ", str, " does not exist"));
                            i = 1;
                            FeatureStatusCache.setStatus(1, featureName);
                        }
                    }
                }
                Log.m266d("ScsApi@ServiceExecutor", "beforeExecute(). First check for " + featureName + ". status: " + i);
            }
        } else {
            Log.m267e("ScsApi@ServiceExecutor", "Unexpected runnable!!!!");
        }
        this.mConnectionLock.lock();
        try {
            try {
                if (!this.mIsConnected) {
                    Log.m266d("ScsApi@ServiceExecutor", "beforeExecute() : not connected, try to connect");
                    if (connect(this.mContext, getServiceIntent(), this.mConnectionListener)) {
                        Log.m266d("ScsApi@ServiceExecutor", "beforeExecute() : before wait");
                        if (!this.mIsConnected) {
                            this.mConnectionCondition.await();
                        }
                        Log.m266d("ScsApi@ServiceExecutor", "beforeExecute() : after wait");
                        if (!this.mIsConnected) {
                            thread.interrupt();
                        }
                    } else {
                        Log.m267e("ScsApi@ServiceExecutor", "beforeExecute() : failed to bind service");
                        thread.interrupt();
                    }
                }
            } catch (InterruptedException e6) {
                e6.printStackTrace();
                thread.interrupt();
            }
            this.mConnectionLock.unlock();
            this.mTaskCount.getAndIncrement();
            Log.m266d("ScsApi@ServiceExecutor", "beforeExecute(). mTaskCount: " + this.mTaskCount);
        } catch (Throwable th) {
            this.mConnectionLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean connect(Context context, Intent intent, C47641 c47641) {
        Log.m266d("ScsApi@ServiceExecutor", "connect");
        ConnectionManager connectionManager = this.mConnectionManager;
        Log.m266d("ScsApi@ConnectionManager", "isServiceConnected = " + connectionManager.mIsConnected);
        boolean z = true;
        if (connectionManager.mIsConnected) {
            return true;
        }
        ConnectionManager connectionManager2 = this.mConnectionManager;
        connectionManager2.mInternalServiceConnectionListener = c47641;
        Log.m266d("ScsApi@ConnectionManager", "isServiceConnected = " + connectionManager2.mIsConnected);
        if (connectionManager2.mIsConnected) {
            Log.m266d("ScsApi@ConnectionManager", "just return already bound service obj");
        } else {
            if (context == null) {
                Log.m267e("ScsApi@ConnectionManager", "Context is null");
            } else if (intent == null) {
                Log.m267e("ScsApi@ConnectionManager", "Intent is null");
            } else {
                Log.m266d("ScsApi@ConnectionManager", "connectToService mIsConnected = " + connectionManager2.mIsConnected);
                if (connectionManager2.mIsConnected) {
                    Log.m266d("ScsApi@ConnectionManager", "already bound");
                } else {
                    Log.m266d("ScsApi@ConnectionManager", "Binding service with app context");
                    connectionManager2.mContext = context;
                    z = context.bindService(intent, connectionManager2.mServiceConnection, 1);
                }
                Log.m266d("ScsApi@ConnectionManager", "connectToService result : " + z);
                if (!z) {
                    connectionManager2.notifyServiceConnection(3, null, null);
                }
            }
            z = false;
            Log.m266d("ScsApi@ConnectionManager", "connectToService result : " + z);
            if (!z) {
            }
        }
        return z;
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public final void finalize() {
        super.finalize();
        Log.m266d("ScsApi@ServiceExecutor", "finalize");
        ConnectionManager connectionManager = this.mConnectionManager;
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    public abstract Intent getServiceIntent();

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        Log.m266d("ScsApi@ServiceExecutor", "onActivityDestroyed");
        Log.m266d("ScsApi@ServiceExecutor", "deInit");
        ConnectionManager connectionManager = this.mConnectionManager;
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [com.samsung.android.sdk.scs.base.connection.ServiceExecutor$1] */
    public ServiceExecutor(Activity activity, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mConnectionLock = reentrantLock;
        this.mConnectionCondition = reentrantLock.newCondition();
        this.mIsConnected = false;
        this.mConnectionListener = new InternalServiceConnectionListener() { // from class: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.1
            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onConnected(ComponentName componentName, IBinder iBinder) {
                Log.m266d("ScsApi@ServiceExecutor", "onConnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onConnected(componentName, iBinder);
                ServiceExecutor.m2792$$Nest$munlockConnection(serviceExecutor, true, "connected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onDisconnected(ComponentName componentName) {
                Log.m266d("ScsApi@ServiceExecutor", "onDisconnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onDisconnected(componentName);
                ServiceExecutor.m2792$$Nest$munlockConnection(serviceExecutor, false, "disconnected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onError() {
                Log.m266d("ScsApi@ServiceExecutor", "onError");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.getClass();
                ServiceExecutor.m2792$$Nest$munlockConnection(serviceExecutor, false, "onError, signal all");
            }
        };
        allowCoreThreadTimeOut(true);
        Log.m266d("ScsApi@ServiceExecutor", "use activity context");
        this.mContext = activity;
        activity.registerActivityLifecycleCallbacks(this);
        this.mTaskCount = new AtomicInteger(0);
        this.mConnectionManager = new ConnectionManager();
        Log.m266d("ScsApi@ServiceExecutor", "ServiceExecutor. ctor()");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }
}
