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
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig;
import com.samsung.android.sdk.scs.base.feature.Feature;
import com.samsung.android.sdk.scs.base.feature.FeatureStatusCache;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.FeatureHelper;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public abstract class ServiceExecutor extends ThreadPoolExecutor implements InternalServiceConnectionListener, Application.ActivityLifecycleCallbacks {
    private static final boolean CONNECTION_TIMER_ON = false;
    private static final String TAG = "ScsApi@ServiceExecutor";
    private final Condition mConnectionCondition;
    private final InternalServiceConnectionListener mConnectionListener;
    private final ReentrantLock mConnectionLock;
    private TimerTask mConnectionManagementTask;
    protected ConnectionManager mConnectionManager;
    protected final Context mContext;
    private boolean mIsConnected;
    private final AtomicInteger mTaskCount;

    /* renamed from: -$$Nest$munlockConnection, reason: not valid java name */
    public static void m3322$$Nest$munlockConnection(ServiceExecutor serviceExecutor, boolean z, String str) {
        serviceExecutor.mConnectionLock.lock();
        try {
            serviceExecutor.mIsConnected = z;
            Log.d(TAG, str);
            serviceExecutor.mConnectionCondition.signalAll();
        } finally {
            serviceExecutor.mConnectionLock.unlock();
        }
    }

    public ServiceExecutor(Context context, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mConnectionLock = reentrantLock;
        this.mConnectionCondition = reentrantLock.newCondition();
        this.mIsConnected = false;
        this.mConnectionListener = new InternalServiceConnectionListener() { // from class: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.1
            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(ServiceExecutor.TAG, "onConnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onConnected(componentName, iBinder);
                ServiceExecutor.m3322$$Nest$munlockConnection(serviceExecutor, true, "connected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onDisconnected(ComponentName componentName) {
                Log.d(ServiceExecutor.TAG, "onDisconnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onDisconnected(componentName);
                ServiceExecutor.m3322$$Nest$munlockConnection(serviceExecutor, false, "disconnected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onError() {
                Log.d(ServiceExecutor.TAG, "onError");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onError();
                ServiceExecutor.m3322$$Nest$munlockConnection(serviceExecutor, false, "onError, signal all");
            }
        };
        allowCoreThreadTimeOut(true);
        Log.d(TAG, "use application context");
        this.mContext = context.getApplicationContext();
        this.mTaskCount = new AtomicInteger(0);
        this.mConnectionManager = new ConnectionManager();
        Log.d(TAG, "ServiceExecutor. ctor()");
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        this.mTaskCount.getAndDecrement();
        Log.d(TAG, "afterExecute(). mTaskCount: " + this.mTaskCount);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01be  */
    @Override // java.util.concurrent.ThreadPoolExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void beforeExecute(Thread thread, Runnable runnable) throws PackageManager.NameNotFoundException {
        boolean zIsEmergencyMode;
        int iIntValue;
        PackageInfo packageInfo;
        String string;
        FeatureConfig featureConfig;
        int i = 1;
        super.beforeExecute(thread, runnable);
        Object[] objArr = {this, runnable};
        StringBuilder sb = new StringBuilder("task");
        for (int i2 = 0; i2 < 2; i2++) {
            Object obj = objArr[i2];
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
        }
        Log.i(TAG, sb.toString());
        if (runnable instanceof TaskRunnable) {
            String featureName = ((TaskRunnable) runnable).getFeatureName();
            Integer num = (Integer) FeatureStatusCache.statusMap.get(featureName);
            if ((num == null ? -1000 : num.intValue()) == -1000) {
                Context context = this.mContext;
                Map map = Feature.sinceVersionMap;
                Log.i("ScsApi@Feature", "checkFeature() : " + featureName + ", sdk : 4.0.26");
                if (context == null || featureName == null) {
                    Log.e("ScsApi@Feature", "checkFeature(). input is null. context: " + context + ", feature: " + featureName);
                    i = 300;
                } else {
                    try {
                        zIsEmergencyMode = SemEmergencyManager.isEmergencyMode(context);
                    } catch (Error | Exception e) {
                        Log.e("ScsApi@FrameworkWrapper", e.getMessage());
                        zIsEmergencyMode = false;
                    }
                    if (zIsEmergencyMode) {
                        Log.e("ScsApi@Feature", "checkFeature(). not supported in emergency mode");
                        i = 8;
                        FeatureStatusCache.setStatus(8, featureName);
                    } else {
                        String str = (Feature.isSIVSAvailableOSVersion(context) && Feature.SUPPORTED_SIVS_FEATURES.contains(featureName)) ? "com.samsung.android.intellivoiceservice" : Feature.SUPPORTED_VISUAL_FEATURES.contains(featureName) ? "com.samsung.android.aicore" : Feature.SUPPORTED_VISUAL_CLOUD_FEATURES.contains(featureName) ? "com.samsung.android.visual.cloudcore" : "com.samsung.android.scs";
                        try {
                            if (context.getPackageManager().getApplicationInfo(str, 128).enabled) {
                                String str2 = (Feature.isSIVSAvailableOSVersion(context) && Feature.SUPPORTED_SIVS_FEATURES.contains(featureName)) ? "scs_sivs_supported_feature_info" : (Feature.SUPPORTED_VISUAL_FEATURES.contains(featureName) || Feature.SUPPORTED_VISUAL_CLOUD_FEATURES.contains(featureName)) ? "scs_visual_supported_feature_info" : "scs_core_supported_feature_info";
                                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("getFeatureVersionFromSettings(), serviceApp : ", str, ", feature : ", featureName, ", settingKey : ");
                                sbM.append(str2);
                                Log.d("ScsApi@FeatureHelper", sbM.toString());
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
                                if (TextUtils.isEmpty(string)) {
                                    iIntValue = -2;
                                    if (iIntValue == -2) {
                                        Uri uri = Uri.parse((Feature.isSIVSAvailableOSVersion(context) && Feature.SUPPORTED_SIVS_FEATURES.contains(featureName)) ? "content://com.samsung.android.intellivoiceservice.feature" : Feature.SUPPORTED_VISUAL_FEATURES.contains(featureName) ? "content://com.samsung.android.aicore.feature" : Feature.SUPPORTED_VISUAL_CLOUD_FEATURES.contains(featureName) ? "content://com.samsung.android.visual.cloudcore.feature" : "content://com.samsung.android.scs.feature");
                                        Log.d("ScsApi@FeatureHelper", "getFeatureVersionFromProvider()");
                                        Bundle bundleCall = null;
                                        try {
                                            bundleCall = context.getContentResolver().call(uri, "featureSupportRequest", featureName, (Bundle) null);
                                        } catch (Exception e4) {
                                            Log.e("ScsApi@FeatureHelper", "checkScsFeature(). " + e4.getMessage());
                                        }
                                        if (bundleCall == null) {
                                            Log.e("ScsApi@FeatureHelper", "checkScsFeature(). retBundle == null!!!");
                                            iIntValue = -2;
                                        } else {
                                            iIntValue = bundleCall.getInt("constVersion");
                                        }
                                    }
                                    if (iIntValue == -2) {
                                        Log.e("ScsApi@Feature", "checkScsFeature(). retBundle == null!!!");
                                        i = 2000;
                                    } else if (iIntValue == 0) {
                                        android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("checkScsFeature(). ", featureName, " is not available!!"));
                                        i = 5;
                                    } else {
                                        if (iIntValue == -1) {
                                            android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("checkScsFeature(). SCS doesn't know ", featureName, ". SCS update might be required."));
                                        } else {
                                            Map map2 = Feature.sinceVersionMap;
                                            int iIntValue2 = map2.containsKey(featureName) ? ((Integer) map2.get(featureName)).intValue() : Integer.MAX_VALUE;
                                            if (iIntValue < iIntValue2) {
                                                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(iIntValue, "checkScsFeature(). ", featureName, ", scsVersion: ", ", sinceVersion: ");
                                                sbM890m.append(iIntValue2);
                                                Log.e("ScsApi@Feature", sbM890m.toString());
                                            } else {
                                                i = 0;
                                            }
                                        }
                                        i = 3;
                                    }
                                    FeatureStatusCache.setStatus(i, featureName);
                                } else {
                                    try {
                                        featureConfig = FeatureHelper.getFeatureConfig(string);
                                    } catch (Exception e5) {
                                        android.util.Log.d(Log.concatPrefixTag("ScsApi@FeatureHelper"), "Unexpected behaviour when reading global settings", e5);
                                    }
                                    if (packageInfo.versionName.compareTo(featureConfig.getAppVersion()) != 0) {
                                        iIntValue = -2;
                                        if (iIntValue == -2) {
                                        }
                                        if (iIntValue == -2) {
                                        }
                                        FeatureStatusCache.setStatus(i, featureName);
                                    } else {
                                        Integer orDefault = featureConfig.getFeatures().getOrDefault(featureName, -2);
                                        iIntValue = orDefault != null ? orDefault.intValue() : -2;
                                        Log.d("ScsApi@FeatureHelper", "Get feature version from global settings. feature : " + featureName + ", version : " + iIntValue);
                                        if (iIntValue == -2) {
                                        }
                                        if (iIntValue == -2) {
                                        }
                                        FeatureStatusCache.setStatus(i, featureName);
                                    }
                                }
                            } else {
                                android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), "checkFeature(). " + str + " has disabled.");
                                FeatureStatusCache.setStatus(2, featureName);
                                i = 2;
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            android.util.Log.w(Log.concatPrefixTag("ScsApi@Feature"), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("dump(), ", str, " does not exist"));
                            FeatureStatusCache.setStatus(1, featureName);
                        }
                    }
                }
                Log.d(TAG, "beforeExecute(). First check for " + featureName + ". status: " + i);
            }
        } else {
            Log.e(TAG, "Unexpected runnable!!!!");
        }
        this.mConnectionLock.lock();
        try {
            try {
                if (!this.mIsConnected) {
                    Log.d(TAG, "beforeExecute() : not connected, try to connect");
                    if (connect(this.mContext, getServiceIntent(), this.mConnectionListener)) {
                        Log.d(TAG, "beforeExecute() : before wait");
                        if (!this.mIsConnected) {
                            this.mConnectionCondition.await();
                        }
                        Log.d(TAG, "beforeExecute() : after wait");
                        if (!this.mIsConnected) {
                            thread.interrupt();
                        }
                    } else {
                        Log.e(TAG, "beforeExecute() : failed to bind service");
                        thread.interrupt();
                    }
                }
            } catch (InterruptedException | SecurityException e6) {
                e6.printStackTrace();
                thread.interrupt();
            }
            this.mConnectionLock.unlock();
            this.mTaskCount.getAndIncrement();
            Log.d(TAG, "beforeExecute(). mTaskCount: " + this.mTaskCount);
        } catch (Throwable th) {
            this.mConnectionLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean connect(Context context, Intent intent, InternalServiceConnectionListener internalServiceConnectionListener) {
        Log.d(TAG, "connect");
        boolean zBindService = true;
        if (this.mConnectionManager.isServiceConnected()) {
            return true;
        }
        ConnectionManager connectionManager = this.mConnectionManager;
        connectionManager.mInternalServiceConnectionListener = internalServiceConnectionListener;
        if (connectionManager.isServiceConnected()) {
            Log.d("ScsApi@ConnectionManager", "just return already bound service obj");
            return true;
        }
        if (context == null) {
            Log.e("ScsApi@ConnectionManager", "Context is null");
        } else {
            if (intent != null) {
                Log.d("ScsApi@ConnectionManager", "connectToService mIsConnected = " + connectionManager.mIsConnected);
                if (connectionManager.mIsConnected) {
                    Log.d("ScsApi@ConnectionManager", "already bound");
                } else {
                    Log.d("ScsApi@ConnectionManager", "Binding service with app context");
                    connectionManager.mContext = context;
                    zBindService = context.bindService(intent, connectionManager.mServiceConnection, 1);
                }
                Log.d("ScsApi@ConnectionManager", "connectToService result : " + zBindService);
                if (!zBindService) {
                    connectionManager.notifyServiceConnection(3, null, null);
                }
                return zBindService;
            }
            Log.e("ScsApi@ConnectionManager", "Intent is null");
        }
        zBindService = false;
        Log.d("ScsApi@ConnectionManager", "connectToService result : " + zBindService);
        if (!zBindService) {
        }
        return zBindService;
    }

    public void deInit() {
        Log.d(TAG, "deInit");
        ConnectionManager connectionManager = this.mConnectionManager;
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void finalize() {
        super.finalize();
        Log.d(TAG, "finalize");
        ConnectionManager connectionManager = this.mConnectionManager;
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    public abstract Intent getServiceIntent();

    public boolean isConnected() {
        return this.mConnectionManager.isServiceConnected();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed");
        deInit();
    }

    public ServiceExecutor(Activity activity, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mConnectionLock = reentrantLock;
        this.mConnectionCondition = reentrantLock.newCondition();
        this.mIsConnected = false;
        this.mConnectionListener = new InternalServiceConnectionListener() { // from class: com.samsung.android.sdk.scs.base.connection.ServiceExecutor.1
            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(ServiceExecutor.TAG, "onConnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onConnected(componentName, iBinder);
                ServiceExecutor.m3322$$Nest$munlockConnection(serviceExecutor, true, "connected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onDisconnected(ComponentName componentName) {
                Log.d(ServiceExecutor.TAG, "onDisconnected");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onDisconnected(componentName);
                ServiceExecutor.m3322$$Nest$munlockConnection(serviceExecutor, false, "disconnected, signal all");
            }

            @Override // com.samsung.android.sdk.scs.base.connection.InternalServiceConnectionListener
            public final void onError() {
                Log.d(ServiceExecutor.TAG, "onError");
                ServiceExecutor serviceExecutor = ServiceExecutor.this;
                serviceExecutor.onError();
                ServiceExecutor.m3322$$Nest$munlockConnection(serviceExecutor, false, "onError, signal all");
            }
        };
        allowCoreThreadTimeOut(true);
        Log.d(TAG, "use activity context");
        this.mContext = activity;
        activity.registerActivityLifecycleCallbacks(this);
        this.mTaskCount = new AtomicInteger(0);
        this.mConnectionManager = new ConnectionManager();
        Log.d(TAG, "ServiceExecutor. ctor()");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }
}
