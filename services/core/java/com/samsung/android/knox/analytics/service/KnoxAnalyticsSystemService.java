package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.UserManager;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.analytics.activation.model.IActivationObserver;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.UploaderBroadcaster;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxAnalyticsSystemService extends SystemService {
    public static final String ANALYTICS_SERVICE = "knox_analytics";
    public static final String EVENT_FEATURE = "KNOX_ANALYTICS";
    public static final int EVENT_SCHEMA_VERSION = 1;
    public static final String KNOX_ANALYTICS_SYSTEM_SERVICE_VERSION = "v1.0.0";
    public static final int ONE_MINUTE_IN_MS = 60000;
    public static final String PACKAGE_NAME_PARAMETER_NAME = "pN";
    public static final String PERMISSION_TYPE_PARAMETER_NAME = "prm";
    public static final String PUB_KEY_MD5_PARAMETER_NAME = "pkMD5";
    public static final String PUB_KEY_SHA256_PARAMETER_NAME = "pkSHA256";
    public static final String SERVICE_ACTIVATION_EVENT_NAME = "activationChanged";
    public static final String STATUS_CHANGED_EVENT_NAME = "statusChanged";
    public static final String STATUS_PARAMETER_NAME = "st";
    public static final String TAG = "[KnoxAnalytics] KnoxAnalyticsSystemService";
    public static final int TRIGGER_ELM = 0;
    public static final String TRIGGER_EVENT_NAME = "serviceChanged";
    public static final String TRIGGER_PARAMETER_NAME = "srv";
    public ActivationMonitor mActivationMonitor;
    public IActivationObserver mActivationObserver;
    public final Object mBootCompletedLock;
    public int mBootPhase;
    public Context mContext;
    public DatabaseSizeObserver mDatabaseSizeObserver;
    public EventQueue mEventQueue;
    public KnoxAnalyticsServiceImpl mImpl;
    public boolean mIsSystemServiceRunning;
    public final Object mSystemServiceRunningLock;
    public UserManager mUserManager;
    public final Object mUserUnlockStatusLock;

    public KnoxAnalyticsSystemService(Context context) {
        super(context);
        this.mIsSystemServiceRunning = false;
        this.mBootPhase = 1000;
        this.mBootCompletedLock = new Object();
        this.mSystemServiceRunningLock = new Object();
        this.mUserUnlockStatusLock = new Object();
        this.mActivationObserver = new IActivationObserver() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.1
            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public final void onKnoxAnalyticsActivation(boolean z) {
                Log.d(KnoxAnalyticsSystemService.TAG, "onKnoxAnalyticsActivation()");
                KnoxAnalyticsSystemService.this.startSystemService();
                KnoxAnalyticsSystemService.this.broadcastAnalyticsStatus(true, false);
                KnoxAnalyticsSystemService.this.broadcastKAStatusToMDM(true, z, false);
                if (z) {
                    KnoxAnalyticsSystemService.this.logActivationEventAfterStart();
                }
            }

            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public final void onKnoxAnalyticsDeactivation(boolean z) {
                Log.d(KnoxAnalyticsSystemService.TAG, "onKnoxAnalyticsDeactivation()");
                KnoxAnalyticsSystemService.this.broadcastAnalyticsStatus(false, z);
                KnoxAnalyticsSystemService.this.broadcastKAStatusToMDM(false, false, z);
                KnoxAnalyticsSystemService.this.stopSystemService();
            }

            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public final void onStatusChanged(int i, boolean z, String str) {
                KnoxAnalyticsSystemService.this.logStatusChangedEventAfterStart(i, z, str);
            }

            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public final void onTriggerChanged(int i, boolean z, String str) {
                KnoxAnalyticsSystemService.this.logTriggerEventAfterStart(i, z, str);
            }
        };
        Log.d(TAG, "Constructor()");
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    public static AsyncTask runAsync(final Runnable runnable) {
        return new AsyncTask() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.8
            @Override // android.os.AsyncTask
            public final Void doInBackground(Void... voidArr) {
                runnable.run();
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public final void broadcastAnalyticsStatus(final boolean z, final boolean z2) {
        Log.d(TAG, "broadcastAnalyticsStatus()");
        if (this.mUserManager.isUserUnlockingOrUnlocked(0)) {
            UploaderBroadcaster.broadcastAnalyticsStatus(this.mContext, z, z2);
        } else {
            runAsync(new Runnable() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.3
                @Override // java.lang.Runnable
                public final void run() {
                    while (!KnoxAnalyticsSystemService.this.mUserManager.isUserUnlockingOrUnlocked(0)) {
                        try {
                            synchronized (KnoxAnalyticsSystemService.this.mUserUnlockStatusLock) {
                                KnoxAnalyticsSystemService.this.mUserUnlockStatusLock.wait();
                            }
                            Log.d(KnoxAnalyticsSystemService.TAG, "broadcastAnalyticsStatus(): after wait");
                        } catch (InterruptedException unused) {
                            Log.e(KnoxAnalyticsSystemService.TAG, "broadcastAnalyticsStatus(): Interrupted exception");
                        }
                    }
                    Log.d(KnoxAnalyticsSystemService.TAG, "broadcastAnalyticsStatus(): after while");
                    UploaderBroadcaster.broadcastAnalyticsStatus(KnoxAnalyticsSystemService.this.mContext, z, z2);
                }
            });
        }
    }

    public final void broadcastKAStatusToMDM(final boolean z, final boolean z2, final boolean z3) {
        Log.d(TAG, "broadcastKAStatusToMDM()");
        if (this.mUserManager.isUserUnlockingOrUnlocked(0)) {
            UploaderBroadcaster.broadcastKAStatusToMDM(this.mContext, z, z2, z3);
        } else {
            runAsync(new Runnable() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.4
                @Override // java.lang.Runnable
                public final void run() {
                    while (!KnoxAnalyticsSystemService.this.mUserManager.isUserUnlockingOrUnlocked(0)) {
                        try {
                            synchronized (KnoxAnalyticsSystemService.this.mUserUnlockStatusLock) {
                                KnoxAnalyticsSystemService.this.mUserUnlockStatusLock.wait();
                            }
                            Log.d(KnoxAnalyticsSystemService.TAG, "broadcastKAStatusToMDM(): after wait");
                        } catch (InterruptedException unused) {
                            Log.e(KnoxAnalyticsSystemService.TAG, "broadcastKAStatusToMDM(): Interrupted exception");
                        }
                    }
                    Log.d(KnoxAnalyticsSystemService.TAG, "broadcastKAStatusToMDM(): after while");
                    UploaderBroadcaster.broadcastKAStatusToMDM(KnoxAnalyticsSystemService.this.mContext, z, z2, z3);
                }
            });
        }
    }

    public final void checkVersioningBlobWhenBootCompleted() {
        Log.d(TAG, "checkVersioningBlobWhenBootCompleted()");
        runAsync(new Runnable() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.2
            @Override // java.lang.Runnable
            public final void run() {
                Log.d(KnoxAnalyticsSystemService.TAG, "checkVersioningBlobWhenBootCompleted(): run");
                while (true) {
                    KnoxAnalyticsSystemService knoxAnalyticsSystemService = KnoxAnalyticsSystemService.this;
                    if (knoxAnalyticsSystemService.mBootPhase >= 1000) {
                        Log.d(KnoxAnalyticsSystemService.TAG, "checkVersioningBlobWhenBootCompleted(): after while");
                        KnoxAnalyticsVersionCollector.checkVersioningBlob(KnoxAnalyticsSystemService.this.mContext);
                        KnoxAnalyticsQueryResolver.callNotifyVersioningCompleted(KnoxAnalyticsSystemService.this.mContext);
                        EventQueue eventQueue = KnoxAnalyticsSystemService.this.mEventQueue;
                        if (eventQueue != null) {
                            eventQueue.notifyVersioningCompleted();
                            return;
                        }
                        return;
                    }
                    try {
                        synchronized (knoxAnalyticsSystemService.mBootCompletedLock) {
                            KnoxAnalyticsSystemService.this.mBootCompletedLock.wait(60000L);
                        }
                        Log.d(KnoxAnalyticsSystemService.TAG, "checkVersioningBlobWhenBootCompleted(): after wait");
                    } catch (InterruptedException unused) {
                        Log.e(KnoxAnalyticsSystemService.TAG, "checkVersioningBlobWhenBootCompleted(): Interrupted exception");
                    }
                }
            }
        });
    }

    public final void deinitializeDatabaseSizeObserver() {
        Log.d(TAG, "deinitializeDatabaseSizeObserver()");
        DatabaseSizeObserver databaseSizeObserver = this.mDatabaseSizeObserver;
        if (databaseSizeObserver != null) {
            databaseSizeObserver.stop();
            this.mDatabaseSizeObserver = null;
        }
    }

    public final void initializeDatabaseSizeObserver() {
        Log.d(TAG, "initializeDatabaseSizeObserver()");
        if (this.mDatabaseSizeObserver == null) {
            this.mDatabaseSizeObserver = new DatabaseSizeObserver(this.mContext, this.mEventQueue);
        }
        this.mDatabaseSizeObserver.start();
    }

    public final void initializeEventQueue() {
        Log.d(TAG, "initializeEventQueue()");
        if (this.mEventQueue == null) {
            this.mEventQueue = new EventQueue(this.mContext);
        }
        this.mEventQueue.start();
    }

    public final void logActivationEventAfterStart() {
        Log.d(TAG, "logActivationEventAfterStart()");
        runAsync(new Runnable() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.7
            @Override // java.lang.Runnable
            public final void run() {
                Log.d(KnoxAnalyticsSystemService.TAG, "logActivationEventAfterStart(): run");
                while (true) {
                    KnoxAnalyticsSystemService knoxAnalyticsSystemService = KnoxAnalyticsSystemService.this;
                    if (knoxAnalyticsSystemService.mIsSystemServiceRunning) {
                        Log.d(KnoxAnalyticsSystemService.TAG, "logActivationEventAfterStart(): after while");
                        KnoxAnalyticsSystemService.this.logActivationUpdateEvent(true);
                        return;
                    } else {
                        try {
                            synchronized (knoxAnalyticsSystemService.mSystemServiceRunningLock) {
                                KnoxAnalyticsSystemService.this.mSystemServiceRunningLock.wait(60000L);
                            }
                            Log.d(KnoxAnalyticsSystemService.TAG, "logActivationEventAfterStart(): after wait");
                        } catch (InterruptedException unused) {
                            Log.e(KnoxAnalyticsSystemService.TAG, "logActivationEventAfterStart(): Interrupted exception");
                        }
                    }
                }
            }
        });
    }

    public final void logActivationUpdateEvent(boolean z) {
        String str = TAG;
        Log.d(str, "logActivationUpdateEvent(" + String.valueOf(z) + ")");
        EventQueue eventQueue = this.mEventQueue;
        if (eventQueue == null || !eventQueue.mIsStarted) {
            Log.e(str, "logActivationUpdateEvent(): EventQueue not started!");
            return;
        }
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_ANALYTICS", 1, SERVICE_ACTIVATION_EVENT_NAME);
        knoxAnalyticsData.setProperty(STATUS_PARAMETER_NAME, z);
        this.mEventQueue.postMessage(z ? 1 : 2, knoxAnalyticsData);
    }

    public final void logDeactivationEvent() {
        if (this.mIsSystemServiceRunning) {
            logActivationUpdateEvent(false);
        } else {
            Log.d(TAG, "Won't log deactivation because it is already off!");
        }
    }

    public final void logStatusChangedEvent(int i, boolean z, String str) {
        ArrayList arrayList;
        String str2 = TAG;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "logStatusChangedEvent( ", ", ");
        m.append(String.valueOf(z));
        m.append(")");
        Log.d(str2, m.toString());
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_ANALYTICS", 1, STATUS_CHANGED_EVENT_NAME);
        knoxAnalyticsData.setProperty(TRIGGER_PARAMETER_NAME, i);
        knoxAnalyticsData.setProperty(STATUS_PARAMETER_NAME, z);
        if (str != null && !str.isEmpty()) {
            knoxAnalyticsData.setProperty("pN", str);
            Context context = this.mContext;
            int i2 = 0;
            try {
                boolean hasPermission = Utils.hasPermission(context, str, Utils.standardPermissions);
                boolean hasPermission2 = Utils.hasPermission(context, str, Utils.customPermissions);
                boolean hasPermission3 = Utils.hasPermission(context, str, Utils.premiumPermissions);
                if (hasPermission) {
                    i2 = !hasPermission2 ? !hasPermission3 ? 1 : 2 : !hasPermission3 ? 3 : 4;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                android.util.Log.d("EnterpriseUtils", "NameNotFoundException");
            }
            knoxAnalyticsData.setProperty(PERMISSION_TYPE_PARAMETER_NAME, i2);
            ArrayList arrayList2 = null;
            try {
                arrayList = Utils.getApplicationPubKeyMD5(this.mContext, str);
            } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException | CertificateException e) {
                e = e;
                arrayList = null;
            }
            try {
                arrayList2 = Utils.getApplicationSignaturesSHA256(this.mContext, str);
            } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException | CertificateException e2) {
                e = e2;
                Log.e(TAG, "Exception " + e.getMessage());
                if (arrayList != null) {
                    String str3 = TAG;
                    StringBuilder m2 = DirEncryptService$$ExternalSyntheticOutline0.m(i, "log pubkey( ", ", ", str, ", ");
                    m2.append(arrayList);
                    m2.append(", ");
                    m2.append(arrayList2);
                    m2.append(")");
                    Log.d(str3, m2.toString());
                    knoxAnalyticsData.setProperty(PUB_KEY_MD5_PARAMETER_NAME, arrayList.toString());
                    knoxAnalyticsData.setProperty(PUB_KEY_SHA256_PARAMETER_NAME, arrayList2.toString());
                }
                this.mEventQueue.postMessage(1, knoxAnalyticsData);
            }
            if (arrayList != null && arrayList.size() > 0 && arrayList2 != null && arrayList2.size() > 0) {
                String str32 = TAG;
                StringBuilder m22 = DirEncryptService$$ExternalSyntheticOutline0.m(i, "log pubkey( ", ", ", str, ", ");
                m22.append(arrayList);
                m22.append(", ");
                m22.append(arrayList2);
                m22.append(")");
                Log.d(str32, m22.toString());
                knoxAnalyticsData.setProperty(PUB_KEY_MD5_PARAMETER_NAME, arrayList.toString());
                knoxAnalyticsData.setProperty(PUB_KEY_SHA256_PARAMETER_NAME, arrayList2.toString());
            }
        }
        this.mEventQueue.postMessage(1, knoxAnalyticsData);
    }

    public final void logStatusChangedEventAfterStart(final int i, final boolean z, final String str) {
        Log.d(TAG, "logStatusChangedEventAfterStart()");
        if (this.mIsSystemServiceRunning) {
            logStatusChangedEvent(i, z, str);
        } else {
            runAsync(new Runnable() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.6
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d(KnoxAnalyticsSystemService.TAG, "logStatusChangedEventAfterStart(): run");
                    while (true) {
                        KnoxAnalyticsSystemService knoxAnalyticsSystemService = KnoxAnalyticsSystemService.this;
                        if (knoxAnalyticsSystemService.mIsSystemServiceRunning) {
                            Log.d(KnoxAnalyticsSystemService.TAG, "logStatusChangedEventAfterStart(): after while");
                            KnoxAnalyticsSystemService.this.logStatusChangedEvent(i, z, str);
                            return;
                        } else {
                            try {
                                synchronized (knoxAnalyticsSystemService.mSystemServiceRunningLock) {
                                    KnoxAnalyticsSystemService.this.mSystemServiceRunningLock.wait(60000L);
                                }
                                Log.d(KnoxAnalyticsSystemService.TAG, "logStatusChangedEventAfterStart(): after wait");
                            } catch (InterruptedException unused) {
                                Log.e(KnoxAnalyticsSystemService.TAG, "logStatusChangedEventAfterStart(): Interrupted exception");
                            }
                        }
                    }
                }
            });
        }
    }

    public final void logTriggerEvent(int i, boolean z, String str) {
        String str2 = TAG;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "logTriggerEvent( ", ", ");
        m.append(String.valueOf(z));
        m.append(")");
        Log.d(str2, m.toString());
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_ANALYTICS", 1, TRIGGER_EVENT_NAME);
        knoxAnalyticsData.setProperty(TRIGGER_PARAMETER_NAME, i);
        knoxAnalyticsData.setProperty(STATUS_PARAMETER_NAME, z);
        if (str != null && !str.isEmpty() && i == 0) {
            knoxAnalyticsData.setProperty("pN", str);
        }
        this.mEventQueue.postMessage(1, knoxAnalyticsData);
    }

    public final void logTriggerEventAfterStart(final int i, final boolean z, final String str) {
        Log.d(TAG, "logTriggerEventAfterStart()");
        if (this.mIsSystemServiceRunning) {
            logTriggerEvent(i, z, str);
        } else {
            runAsync(new Runnable() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService.5
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d(KnoxAnalyticsSystemService.TAG, "logTriggerEventAfterStart(): run");
                    while (true) {
                        KnoxAnalyticsSystemService knoxAnalyticsSystemService = KnoxAnalyticsSystemService.this;
                        if (knoxAnalyticsSystemService.mIsSystemServiceRunning) {
                            Log.d(KnoxAnalyticsSystemService.TAG, "logTriggerEventAfterStart(): after while");
                            KnoxAnalyticsSystemService.this.logTriggerEvent(i, z, str);
                            return;
                        } else {
                            try {
                                synchronized (knoxAnalyticsSystemService.mSystemServiceRunningLock) {
                                    KnoxAnalyticsSystemService.this.mSystemServiceRunningLock.wait(60000L);
                                }
                                Log.d(KnoxAnalyticsSystemService.TAG, "logTriggerEventAfterStart(): after wait");
                            } catch (InterruptedException unused) {
                                Log.e(KnoxAnalyticsSystemService.TAG, "logTriggerEventAfterStart(): Interrupted exception");
                            }
                        }
                    }
                }
            });
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        this.mBootPhase = i;
        if (i == 500) {
            Log.d(TAG, "onBootPhase(PHASE_SYSTEM_SERVICES_READY)");
        } else if (i == 1000) {
            Log.d(TAG, "onBootPhase(PHASE_BOOT_COMPLETED)");
            synchronized (this.mBootCompletedLock) {
                this.mBootCompletedLock.notifyAll();
            }
        }
        this.mActivationMonitor.onBootPhase(i);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Log.d(TAG, "onStart() knox_analytics");
        ActivationMonitor activationMonitor = new ActivationMonitor(this.mContext);
        this.mActivationMonitor = activationMonitor;
        activationMonitor.registerObserver(this.mActivationObserver);
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        Log.d(TAG, " onUserUnlocking()");
        synchronized (this.mUserUnlockStatusLock) {
            this.mUserUnlockStatusLock.notifyAll();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.os.IBinder, com.samsung.android.knox.analytics.service.KnoxAnalyticsServiceImpl] */
    public final void startSystemService() {
        String str = TAG;
        Log.d(str, "startSystemService()");
        if (this.mIsSystemServiceRunning) {
            Log.d(str, "startSystemService(): can't start, already running!");
            return;
        }
        initializeEventQueue();
        if (this.mImpl == null) {
            Log.d(str, "startSystemService(): new Impl");
            ?? knoxAnalyticsServiceImpl = new KnoxAnalyticsServiceImpl(getContext(), this.mActivationMonitor, this.mEventQueue);
            this.mImpl = knoxAnalyticsServiceImpl;
            publishBinderService(ANALYTICS_SERVICE, knoxAnalyticsServiceImpl);
            publishBinderService("knox_analytics_proxy", new KnoxAnalyticsProxyService(getContext()));
            checkVersioningBlobWhenBootCompleted();
        } else {
            this.mEventQueue.notifyVersioningCompleted();
        }
        this.mIsSystemServiceRunning = true;
        initializeDatabaseSizeObserver();
    }

    public final void stopSystemService() {
        String str = TAG;
        Log.d(str, "stopSystemService()");
        if (!this.mIsSystemServiceRunning) {
            Log.d(str, "stopSystemService(): can't stop, already stopped!");
            return;
        }
        deinitializeDatabaseSizeObserver();
        logDeactivationEvent();
        this.mIsSystemServiceRunning = false;
    }
}
