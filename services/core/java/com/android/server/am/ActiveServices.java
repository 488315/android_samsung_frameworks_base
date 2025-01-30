package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BackgroundStartPrivileges;
import android.app.ForegroundServiceDelegationOptions;
import android.app.ForegroundServiceStartNotAllowedException;
import android.app.ForegroundServiceTypePolicy;
import android.app.IApplicationThread;
import android.app.IForegroundServiceObserver;
import android.app.IServiceConnection;
import android.app.InvalidForegroundServiceTypeException;
import android.app.MissingForegroundServiceTypeException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteServiceException;
import android.app.ServiceStartArgs;
import android.app.StartForegroundCalledOnStoppedServiceException;
import android.app.admin.DevicePolicyEventLogger;
import android.app.compat.CompatChanges;
import android.appwidget.AppWidgetManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.CompatibilityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerExemptionManager;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.TransactionTooLargeException;
import android.os.UserHandle;
import android.os.UserManager;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.webkit.WebViewZygote;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.SomeArgs;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AppStateTracker;
import com.android.server.DualAppManagerService;
import com.android.server.LocalServices;
import com.android.server.appop.AppOpsService;
import com.android.server.chimera.ChimeraManagerService;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ComponentAliasResolver;
import com.android.server.am.ServiceRecord;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.wm.ActivityServiceConnectionsHolder;
import com.android.server.uri.NeededUriGrants;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.emergencymode.Elog;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class ActiveServices {
    public final ActivityManagerService mAm;
    public AppStateTracker mAppStateTracker;
    public AppWidgetManagerInternal mAppWidgetManagerInternal;
    public String mCachedDeviceProvisioningPackage;
    public final ForegroundServiceTypeLoggerModule mFGSLogger;
    public String mLastAnrDump;
    public int mMaxStartingBackground;
    public static final AtomicReference sNumForegroundServices = new AtomicReference(new Pair(0, 0));
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final List mRevivalServicesMessages = new ArrayList();
    public boolean delayServiceEnable = false;
    public final int delayServiceCount = 3;
    public final SparseArray mServiceMap = new SparseArray();
    public final ArrayMap mServiceConnections = new ArrayMap();
    public final ArrayList mPendingServices = new ArrayList();
    public final ArrayList mRestartingServices = new ArrayList();
    public final ArrayList mDestroyingServices = new ArrayList();
    public final ArrayList mPendingFgsNotifications = new ArrayList();
    public final ArrayMap mFgsDelegations = new ArrayMap();
    public long mBindServiceSeqCounter = 0;
    public boolean mFgsDeferralRateLimited = true;
    public final SparseLongArray mFgsDeferralEligible = new SparseLongArray();
    public final RemoteCallbackList mFgsObservers = new RemoteCallbackList();
    public ArrayMap mPendingBringups = new ArrayMap();
    public ArrayList mTmpCollectionResults = null;
    public final SparseArray mFgsAppOpCallbacks = new SparseArray();
    public final ArraySet mRestartBackoffDisabledPackages = new ArraySet();
    public boolean mScreenOn = true;
    public ArraySet mAllowListWhileInUsePermissionInFgs = new ArraySet();
    public final Runnable mLastAnrDumpClearer = new Runnable() { // from class: com.android.server.am.ActiveServices.1
        @Override // java.lang.Runnable
        public void run() {
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    ActiveServices.this.mLastAnrDump = null;
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    };
    public final Runnable mPostDeferredFGSNotifications = new Runnable() { // from class: com.android.server.am.ActiveServices.5
        @Override // java.lang.Runnable
        public void run() {
            long uptimeMillis = SystemClock.uptimeMillis();
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    for (int size = ActiveServices.this.mPendingFgsNotifications.size() - 1; size >= 0; size--) {
                        ServiceRecord serviceRecord = (ServiceRecord) ActiveServices.this.mPendingFgsNotifications.get(size);
                        if (serviceRecord.fgDisplayTime <= uptimeMillis) {
                            ActiveServices.this.mPendingFgsNotifications.remove(size);
                            if (serviceRecord.isForeground && serviceRecord.app != null) {
                                serviceRecord.postNotification(true);
                                serviceRecord.mFgsNotificationShown = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    };

    public final class ActiveForegroundApp {
        public boolean mAppOnTop;
        public long mEndTime;
        public long mHideTime;
        public CharSequence mLabel;
        public int mNumActive;
        public String mPackageName;
        public boolean mShownWhileScreenOn;
        public boolean mShownWhileTop;
        public long mStartTime;
        public long mStartVisibleTime;
        public int mUid;
    }

    public static String fgsStopReasonToString(int i) {
        return i != 1 ? i != 2 ? "UNKNOWN" : "STOP_SERVICE" : "STOP_FOREGROUND";
    }

    public class BackgroundRestrictedListener implements AppStateTracker.BackgroundRestrictedAppListener {
        public BackgroundRestrictedListener() {
        }

        public void updateBackgroundRestrictedForUidPackage(int i, String str, boolean z) {
            if (MARsPolicyManager.MARs_ENABLE && MARsPolicyManager.getInstance().isChinaPolicyEnabled() && MARsPolicyManager.getInstance().isMARsTarget(str, UserHandle.getUserId(i))) {
                return;
            }
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    ActiveServices.this.mAm.mProcessList.updateBackgroundRestrictedForUidPackageLocked(i, str, z);
                    if (!ActiveServices.this.isForegroundServiceAllowedInBackgroundRestricted(i, str) && !ActiveServices.this.isTempAllowedByAlarmClock(i)) {
                        ActiveServices.this.stopAllForegroundServicesLocked(i, str);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public void stopAllForegroundServicesLocked(int i, String str) {
        ServiceMap serviceMapLocked = getServiceMapLocked(UserHandle.getUserId(i));
        int size = serviceMapLocked.mServicesByInstanceName.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.valueAt(i2);
            ServiceInfo serviceInfo = serviceRecord.serviceInfo;
            if ((i == serviceInfo.applicationInfo.uid || str.equals(serviceInfo.packageName)) && serviceRecord.isForeground && serviceRecord.mAllowStartForegroundAtEntering != 301 && !isDeviceProvisioningPackage(serviceRecord.packageName)) {
                arrayList.add(serviceRecord);
            }
        }
        int size2 = arrayList.size();
        for (int i3 = 0; i3 < size2; i3++) {
            setServiceForegroundInnerLocked((ServiceRecord) arrayList.get(i3), 0, null, 0, 0);
        }
    }

    public final class ServiceMap extends Handler {
        public final ArrayMap mActiveForegroundApps;
        public boolean mActiveForegroundAppsChanged;
        public final ArrayList mDelayServiceList;
        public final ArrayList mDelayedStartList;
        public final ArrayList mPendingRemoveForegroundApps;
        public final ArrayMap mServicesByInstanceName;
        public final ArrayMap mServicesByIntent;
        public final ArrayList mStartingBackground;
        public final int mUserId;

        public ServiceMap(Looper looper, int i) {
            super(looper);
            this.mServicesByInstanceName = new ArrayMap();
            this.mServicesByIntent = new ArrayMap();
            this.mDelayedStartList = new ArrayList();
            this.mDelayServiceList = new ArrayList();
            this.mStartingBackground = new ArrayList();
            this.mActiveForegroundApps = new ArrayMap();
            this.mPendingRemoveForegroundApps = new ArrayList();
            this.mUserId = i;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ActivityManagerService activityManagerService = ActiveServices.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        rescheduleDelayedStartsLocked();
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 2) {
                ActiveServices.this.updateForegroundApps(this);
                return;
            }
            if (i == 3) {
                ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        rescheduleDelayedStartsLocked();
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i != 101) {
                return;
            }
            ActivityManagerService activityManagerService3 = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService3) {
                try {
                    startDelayedServiceLocked();
                } finally {
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public void ensureNotStartingBackgroundLocked(ServiceRecord serviceRecord) {
            if (this.mStartingBackground.remove(serviceRecord)) {
                removeMessages(3);
                sendMessage(obtainMessage(3));
            }
            this.mDelayedStartList.remove(serviceRecord);
        }

        public void rescheduleDelayedStartsLocked() {
            String str;
            String str2;
            removeMessages(1);
            long uptimeMillis = SystemClock.uptimeMillis();
            int size = this.mStartingBackground.size();
            int i = 0;
            while (true) {
                str = "ActivityManager";
                if (i >= size) {
                    break;
                }
                ServiceRecord serviceRecord = (ServiceRecord) this.mStartingBackground.get(i);
                if (serviceRecord.startingBgTimeout <= uptimeMillis) {
                    Slog.i("ActivityManager", "Waited long enough for: " + serviceRecord);
                    this.mStartingBackground.remove(i);
                    size += -1;
                    i += -1;
                }
                i++;
            }
            while (this.mDelayedStartList.size() > 0 && this.mStartingBackground.size() < ActiveServices.this.mMaxStartingBackground) {
                ServiceRecord serviceRecord2 = (ServiceRecord) this.mDelayedStartList.remove(0);
                serviceRecord2.delayed = false;
                if (serviceRecord2.pendingStarts.size() <= 0) {
                    Slog.wtf(str, "**** NO PENDING STARTS! " + serviceRecord2 + " startReq=" + serviceRecord2.startRequested + " delayedStop=" + serviceRecord2.delayedStop);
                } else {
                    try {
                        ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord2.pendingStarts.get(0);
                        str2 = str;
                        try {
                            ActiveServices.this.startServiceInnerLocked(this, startItem.intent, serviceRecord2, false, true, startItem.callingId, startItem.mCallingProcessName, startItem.mCallingProcessState, serviceRecord2.startRequested, startItem.mCallingPackageName);
                        } catch (TransactionTooLargeException unused) {
                        }
                    } catch (TransactionTooLargeException unused2) {
                    }
                    str = str2;
                }
                str2 = str;
                str = str2;
            }
            if (this.mStartingBackground.size() > 0) {
                long j = ((ServiceRecord) this.mStartingBackground.get(0)).startingBgTimeout;
                if (j > uptimeMillis) {
                    uptimeMillis = j;
                }
                sendMessageAtTime(obtainMessage(1), uptimeMillis);
            }
            int size2 = this.mStartingBackground.size();
            ActiveServices activeServices = ActiveServices.this;
            if (size2 < activeServices.mMaxStartingBackground) {
                activeServices.mAm.backgroundServicesFinishedLocked(this.mUserId);
            }
        }

        public void startDelayedServiceLocked() {
            removeMessages(101);
            if (!ActiveServices.this.mAm.getAppLaunchFlag()) {
                while (this.mDelayServiceList.size() > 0) {
                    startOrBindServiceLocked((ServiceRecord) this.mDelayServiceList.remove(0));
                }
                return;
            }
            while (this.mDelayServiceList.size() > 0) {
                if (SystemClock.uptimeMillis() >= ((ServiceRecord) this.mDelayServiceList.get(0)).delayTimeout) {
                    startOrBindServiceLocked((ServiceRecord) this.mDelayServiceList.remove(0));
                } else {
                    sendMessageDelayed(obtainMessage(101), 50L);
                    return;
                }
            }
        }

        public void startOrBindServiceLocked(ServiceRecord serviceRecord) {
            try {
                serviceRecord.delayService = false;
                serviceRecord.delayServiceStop = true;
                if (serviceRecord.pendingBinds.size() > 0) {
                    ServiceRecord.BindItem bindItem = (ServiceRecord.BindItem) serviceRecord.pendingBinds.get(0);
                    ActiveServices.this.bindServiceLocked(bindItem.caller, bindItem.token, bindItem.service, bindItem.resolvedType, bindItem.connection, bindItem.flags, bindItem.instanceName, bindItem.isSdkSandboxService, bindItem.sdkSandboxClientAppUid, bindItem.sdkSandboxClientAppPackage, bindItem.sdkSandboxClientApplicationThread, bindItem.callingPackage, bindItem.userId);
                } else if (serviceRecord.pendingStarts.size() > 0) {
                    ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.pendingStarts.get(0);
                    ActiveServices.this.startServiceInnerLocked(this, startItem.intent, serviceRecord, false, true, startItem.callingId, startItem.mCallingProcessName, startItem.mCallingProcessState, serviceRecord.startRequested, startItem.mCallingPackageName);
                } else {
                    Slog.w("ActivityManager", "no pendingStarts or pendingBinds: " + serviceRecord.shortInstanceName);
                }
            } catch (TransactionTooLargeException unused) {
                Slog.w("ActivityManager", "start or bind delayed service fail");
            }
        }
    }

    public ActiveServices(ActivityManagerService activityManagerService) {
        int i = 0;
        this.mAm = activityManagerService;
        try {
            i = Integer.parseInt(SystemProperties.get("ro.config.max_starting_bg", "0"));
        } catch (RuntimeException unused) {
        }
        this.mMaxStartingBackground = i <= 0 ? 25 : i;
        ServiceManager.getService("platform_compat");
        this.mFGSLogger = new ForegroundServiceTypeLoggerModule();
    }

    public void setMaxStartingBackground() {
        int i;
        try {
            i = Integer.parseInt(SystemProperties.get("ro.config.max_starting_bg", "0"));
        } catch (RuntimeException unused) {
            i = 0;
        }
        if (i <= 0) {
            i = ActivityManager.isLowRamDeviceStatic() ? 1 : 8;
        }
        this.mMaxStartingBackground = i;
        Slog.i("ActivityManager", "MaxStartingBackground is set. ( current : " + this.mMaxStartingBackground + " )");
    }

    public void systemServicesReady() {
        getAppStateTracker().addBackgroundRestrictedAppListener(new BackgroundRestrictedListener());
        this.mAppWidgetManagerInternal = (AppWidgetManagerInternal) LocalServices.getService(AppWidgetManagerInternal.class);
        setAllowListWhileInUsePermissionInFgs();
        initSystemExemptedFgsTypePermission();
        initMediaProjectFgsTypeCustomPermission();
    }

    public final AppStateTracker getAppStateTracker() {
        if (this.mAppStateTracker == null) {
            this.mAppStateTracker = (AppStateTracker) LocalServices.getService(AppStateTracker.class);
        }
        return this.mAppStateTracker;
    }

    public final void setAllowListWhileInUsePermissionInFgs() {
        String attentionServicePackageName = this.mAm.mContext.getPackageManager().getAttentionServicePackageName();
        if (!TextUtils.isEmpty(attentionServicePackageName)) {
            this.mAllowListWhileInUsePermissionInFgs.add(attentionServicePackageName);
        }
        String systemCaptionsServicePackageName = this.mAm.mContext.getPackageManager().getSystemCaptionsServicePackageName();
        if (TextUtils.isEmpty(systemCaptionsServicePackageName)) {
            return;
        }
        this.mAllowListWhileInUsePermissionInFgs.add(systemCaptionsServicePackageName);
    }

    public ServiceRecord getServiceByNameLocked(ComponentName componentName, int i) {
        return (ServiceRecord) getServiceMapLocked(i).mServicesByInstanceName.get(componentName);
    }

    public boolean hasBackgroundServicesLocked(int i) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        return serviceMap != null && serviceMap.mStartingBackground.size() >= this.mMaxStartingBackground;
    }

    public boolean hasForegroundServiceNotificationLocked(String str, int i, String str2) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        if (serviceMap != null) {
            for (int i2 = 0; i2 < serviceMap.mServicesByInstanceName.size(); i2++) {
                ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i2);
                if (serviceRecord.appInfo.packageName.equals(str) && serviceRecord.isForeground && Objects.equals(serviceRecord.foregroundNoti.getChannelId(), str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final ServiceMap getServiceMapLocked(int i) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        if (serviceMap != null) {
            return serviceMap;
        }
        ServiceMap serviceMap2 = new ServiceMap(this.mAm.mHandler.getLooper(), i);
        this.mServiceMap.put(i, serviceMap2);
        return serviceMap2;
    }

    public ArrayMap getServicesLocked(int i) {
        return getServiceMapLocked(i).mServicesByInstanceName;
    }

    public final boolean appRestrictedAnyInBackground(int i, String str) {
        AppStateTracker appStateTracker;
        if ((MARsPolicyManager.MARs_ENABLE && MARsPolicyManager.getInstance().isChinaPolicyEnabled() && MARsPolicyManager.getInstance().isMARsTarget(str, UserHandle.getUserId(i)) && !MARsVersionManager.getInstance().isAdjustRestrictionMatch(15, null, str, null)) || (appStateTracker = getAppStateTracker()) == null) {
            return false;
        }
        return appStateTracker.isAppBackgroundRestricted(i, str);
    }

    public static String getProcessNameForService(ServiceInfo serviceInfo, ComponentName componentName, String str, String str2, boolean z, boolean z2) {
        if (z) {
            return str2;
        }
        if ((serviceInfo.flags & 2) == 0) {
            return serviceInfo.processName;
        }
        if (z2) {
            return str + ":ishared:" + str2;
        }
        return serviceInfo.processName + XmlUtils.STRING_ARRAY_SEPARATOR + componentName.getClassName();
    }

    public static void traceInstant(String str, ServiceRecord serviceRecord) {
        if (Trace.isTagEnabled(64L)) {
            Trace.instant(64L, str + (serviceRecord.getComponentName() != null ? serviceRecord.getComponentName().toShortString() : "(?)"));
        }
    }

    public ComponentName startServiceLocked(IApplicationThread iApplicationThread, Intent intent, String str, int i, int i2, boolean z, String str2, String str3, int i3, boolean z2, int i4, String str4, String str5) {
        return startServiceLocked(iApplicationThread, intent, str, i, i2, z, str2, str3, i3, BackgroundStartPrivileges.NONE, z2, i4, str4, str5);
    }

    public ComponentName startServiceLocked(IApplicationThread iApplicationThread, Intent intent, String str, int i, int i2, boolean z, String str2, String str3, int i3, BackgroundStartPrivileges backgroundStartPrivileges) {
        return startServiceLocked(iApplicationThread, intent, str, i, i2, z, str2, str3, i3, backgroundStartPrivileges, false, -1, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0208  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ComponentName startServiceLocked(IApplicationThread iApplicationThread, Intent intent, String str, int i, int i2, boolean z, String str2, String str3, int i3, BackgroundStartPrivileges backgroundStartPrivileges, boolean z2, int i4, String str4, String str5) {
        int i5;
        boolean z3;
        String str6;
        String str7;
        int i6;
        boolean z4;
        Intent intent2;
        int i7;
        String str8;
        boolean z5;
        boolean z6;
        boolean z7;
        ServiceLookupResult serviceLookupResult;
        int i8;
        int appStartModeLOSP;
        int i9;
        int i10;
        boolean z8;
        ProcessRecord processRecord;
        int checkOpNoThrow;
        int userId = this.mAm.mContext.getUserId();
        if (iApplicationThread != null) {
            ProcessRecord recordForAppLOSP = this.mAm.getRecordForAppLOSP(iApplicationThread);
            if (recordForAppLOSP == null) {
                throw new SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + i + ") when starting service " + intent);
            }
            boolean z9 = recordForAppLOSP.mState.getSetSchedGroup() > 0;
            String str9 = recordForAppLOSP.info.packageName;
            int i11 = recordForAppLOSP.userId;
            int pid = recordForAppLOSP.getPid();
            HostingRecord hostingRecord = recordForAppLOSP.getHostingRecord();
            z3 = z9;
            str7 = hostingRecord != null ? hostingRecord.toStringForTracker() : null;
            str6 = str9;
            i5 = i11;
            i6 = pid;
        } else {
            i5 = userId;
            z3 = true;
            str6 = null;
            str7 = null;
            i6 = 0;
        }
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, str5, z2, i4, str4, str, str2, i, i2, i3, true, z3, false, false, null, false);
        if (retrieveServiceLocked == null) {
            return null;
        }
        ServiceRecord serviceRecord = retrieveServiceLocked.record;
        if (serviceRecord == null) {
            String str10 = retrieveServiceLocked.permission;
            if (str10 == null) {
                str10 = "private to package";
            }
            return new ComponentName("!", str10);
        }
        traceInstant("startService(): ", serviceRecord);
        setFgsRestrictionLocked(str2, i, i2, intent, serviceRecord, i3, backgroundStartPrivileges, false, !z);
        if (!this.mAm.mUserController.exists(serviceRecord.userId)) {
            Slog.w("ActivityManager", "Trying to start service with non-existent user! " + serviceRecord.userId);
            return null;
        }
        int i12 = z2 ? i4 : serviceRecord.appInfo.uid;
        String str11 = z2 ? str4 : serviceRecord.packageName;
        int i13 = serviceRecord.appInfo.targetSdkVersion;
        if (z2) {
            try {
                try {
                    i13 = AppGlobals.getPackageManager().getApplicationInfo(str11, 1024L, i3).targetSdkVersion;
                } catch (RemoteException unused) {
                }
            } catch (RemoteException unused2) {
            }
            int i14 = i13;
            z4 = ((this.mAm.isUidActiveLOSP(i12) ^ true) || !appRestrictedAnyInBackground(i12, str11) || isTempAllowedByAlarmClock(i12) || isDeviceProvisioningPackage(str11)) ? false : true;
            if (z) {
                logFgsBackgroundStart(serviceRecord);
                if (serviceRecord.mAllowStartForeground == -1 && isBgFgsRestrictionEnabled(serviceRecord)) {
                    String str12 = "startForegroundService() not allowed due to mAllowStartForeground false: service " + serviceRecord.shortInstanceName;
                    Slog.w("ActivityManager", str12);
                    showFgsBgRestrictedNotificationLocked(serviceRecord);
                    logFGSStateChangeLocked(serviceRecord, 3, 0, 0, 0);
                    if (CompatChanges.isChangeEnabled(174041399L, i2)) {
                        throw new ForegroundServiceStartNotAllowedException(str12);
                    }
                    return null;
                }
            }
            if (z && (checkOpNoThrow = this.mAm.getAppOpsManager().checkOpNoThrow(76, i12, str11)) != 0) {
                if (checkOpNoThrow != 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("startForegroundService not allowed due to app op: service ");
                    intent2 = intent;
                    sb.append(intent2);
                    sb.append(" to ");
                    sb.append(serviceRecord.shortInstanceName);
                    sb.append(" from pid=");
                    i7 = i;
                    sb.append(i7);
                    sb.append(" uid=");
                    sb.append(i2);
                    sb.append(" pkg=");
                    str8 = str2;
                    sb.append(str8);
                    Slog.w("ActivityManager", sb.toString());
                    z6 = true;
                    z5 = false;
                    if (z4 && (serviceRecord.startRequested || z5)) {
                        z7 = z5;
                        i8 = -1;
                        serviceLookupResult = retrieveServiceLocked;
                        i9 = i7;
                    } else {
                        z7 = z5;
                        serviceLookupResult = retrieveServiceLocked;
                        String str13 = str8;
                        Intent intent3 = intent2;
                        i8 = -1;
                        appStartModeLOSP = this.mAm.getAppStartModeLOSP(i12, str11, i14, i, false, false, z4);
                        if (appStartModeLOSP != 0) {
                            Slog.w("ActivityManager", "Background start not allowed: service " + intent3 + " to " + serviceRecord.shortInstanceName + " from pid=" + i + " uid=" + i2 + " pkg=" + str13 + " startFg?=" + z7);
                            if (appStartModeLOSP == 1 || z6) {
                                return null;
                            }
                            if (z4 && z7) {
                                return null;
                            }
                            return new ComponentName("?", "app is in background uid " + this.mAm.mProcessList.getUidRecordLOSP(i12));
                        }
                        i9 = i;
                    }
                    if (MARsPolicyManager.MARs_ENABLE) {
                        BaseRestrictionMgr baseRestrictionMgr = BaseRestrictionMgr.getInstance();
                        ComponentName componentName = serviceRecord.name;
                        int i15 = serviceRecord.userId;
                        ProcessRecord processRecord2 = serviceRecord.app;
                        i10 = i9;
                        z8 = z7;
                        if (baseRestrictionMgr.isRestrictedPackage(componentName, str6, i5, "startService", intent, i15, str7, i6, processRecord2 != null ? processRecord2.getPid() : i15)) {
                            return null;
                        }
                    } else {
                        i10 = i9;
                        z8 = z7;
                    }
                    boolean z10 = (i14 >= 26 || !z8) ? z8 : false;
                    synchronized (this.mAm.mPidsSelfLocked) {
                        processRecord = this.mAm.mPidsSelfLocked.get(i10);
                    }
                    String str14 = processRecord != null ? processRecord.processName : str2;
                    if (processRecord != null && processRecord.getThread() != null && !processRecord.isKilled()) {
                        i8 = processRecord.mState.getCurProcState();
                    }
                    serviceRecord.updateProcessStateOnRequest();
                    ServiceLookupResult serviceLookupResult2 = serviceLookupResult;
                    if (deferServiceBringupIfFrozenLocked(serviceRecord, intent, str2, str3, i2, i, str14, i8, z10, z3, i3, backgroundStartPrivileges, false, null) || !requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord, str2, str3, i2, intent, z3, i3, false, null)) {
                        return null;
                    }
                    ComponentName startServiceInnerLocked = startServiceInnerLocked(serviceRecord, intent, i2, i, str14, i8, z10, z3, backgroundStartPrivileges, str2);
                    return (serviceLookupResult2.aliasComponent == null || startServiceInnerLocked.getPackageName().startsWith("!") || startServiceInnerLocked.getPackageName().startsWith("?")) ? startServiceInnerLocked : serviceLookupResult2.aliasComponent;
                }
                if (checkOpNoThrow != 3) {
                    return new ComponentName("!!", "foreground not allowed as per app op");
                }
            }
            intent2 = intent;
            i7 = i;
            str8 = str2;
            z5 = z;
            z6 = false;
            if (z4) {
            }
            z7 = z5;
            serviceLookupResult = retrieveServiceLocked;
            String str132 = str8;
            Intent intent32 = intent2;
            i8 = -1;
            appStartModeLOSP = this.mAm.getAppStartModeLOSP(i12, str11, i14, i, false, false, z4);
            if (appStartModeLOSP != 0) {
            }
        }
        int i142 = i13;
        if (this.mAm.isUidActiveLOSP(i12) ^ true) {
        }
        if (z) {
        }
        if (z) {
            if (checkOpNoThrow != 1) {
            }
        }
        intent2 = intent;
        i7 = i;
        str8 = str2;
        z5 = z;
        z6 = false;
        if (z4) {
        }
        z7 = z5;
        serviceLookupResult = retrieveServiceLocked;
        String str1322 = str8;
        Intent intent322 = intent2;
        i8 = -1;
        appStartModeLOSP = this.mAm.getAppStartModeLOSP(i12, str11, i142, i, false, false, z4);
        if (appStartModeLOSP != 0) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x015f, code lost:
    
        if (r3.mState.getCurProcState() >= 10) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x019a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ComponentName startServiceInnerLocked(ServiceRecord serviceRecord, Intent intent, int i, int i2, String str, int i3, boolean z, boolean z2, BackgroundStartPrivileges backgroundStartPrivileges, String str2) {
        boolean z3;
        boolean z4;
        boolean z5;
        NeededUriGrants checkGrantUriPermissionFromIntent = this.mAm.mUgmInternal.checkGrantUriPermissionFromIntent(intent, i, serviceRecord.packageName, serviceRecord.userId);
        unscheduleServiceRestartLocked(serviceRecord, i, false, "start service");
        boolean z6 = serviceRecord.startRequested;
        serviceRecord.lastActivity = SystemClock.uptimeMillis();
        serviceRecord.startRequested = true;
        serviceRecord.delayedStop = false;
        serviceRecord.fgRequired = z;
        serviceRecord.pendingStarts.add(new ServiceRecord.StartItem(serviceRecord, false, serviceRecord.makeNextStartId(), intent, checkGrantUriPermissionFromIntent, i, str, str2, i3));
        if (serviceRecord.isForeground || serviceRecord.fgRequired) {
            if (!ActivityManagerService.doesReasonCodeAllowSchedulingUserInitiatedJobs(serviceRecord.mAllowWhileInUsePermissionInFgsReason) && !this.mAm.canScheduleUserInitiatedJobs(i, i2, str2)) {
                z5 = false;
                serviceRecord.updateAllowUiJobScheduling(z5);
            }
            z5 = true;
            serviceRecord.updateAllowUiJobScheduling(z5);
        } else {
            serviceRecord.updateAllowUiJobScheduling(false);
        }
        if (z) {
            synchronized (this.mAm.mProcessStats.mLock) {
                ServiceState tracker = serviceRecord.getTracker();
                if (tracker != null) {
                    z3 = true;
                    tracker.setForeground(true, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                } else {
                    z3 = true;
                }
            }
            AppOpsService appOpsService = this.mAm.mAppOpsService;
            appOpsService.startOperation(AppOpsManager.getToken(appOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null, true, false, null, false, 0, -1);
        } else {
            z3 = true;
        }
        ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
        if (!this.delayServiceEnable) {
            z4 = false;
        } else if (serviceRecord.delayService) {
            int i4 = serviceRecord.delayCount;
            if (i4 < 3) {
                serviceRecord.delayCount = i4 + (z3 ? 1 : 0);
                return serviceRecord.name;
            }
            z4 = false;
            serviceRecord.delayService = false;
            serviceMapLocked.mDelayServiceList.remove(serviceRecord);
        } else {
            z4 = false;
            if (serviceRecord.delayServiceStop) {
                serviceRecord.delayServiceStop = false;
            } else if (this.mAm.getAppLaunchFlag() && shouldDelay(serviceRecord)) {
                serviceRecord.delayService = z3;
                serviceRecord.serviceDelayed = z3;
                serviceRecord.delayTimeout = SystemClock.uptimeMillis() + 500;
                serviceRecord.delayCount = z3 ? 1 : 0;
                serviceMapLocked.mDelayServiceList.add(serviceRecord);
                serviceMapLocked.sendMessageDelayed(serviceMapLocked.obtainMessage(101), 50L);
                return serviceRecord.name;
            }
        }
        if (!z2 && !z && serviceRecord.app == null && this.mAm.mUserController.hasStartedUserState(serviceRecord.userId)) {
            ProcessRecord processRecordLocked = this.mAm.getProcessRecordLocked(serviceRecord.processName, serviceRecord.appInfo.uid);
            if (processRecordLocked == null || processRecordLocked.mState.getCurProcState() > 11) {
                if (serviceRecord.delayed) {
                    return serviceRecord.name;
                }
                if (serviceMapLocked.mStartingBackground.size() >= this.mMaxStartingBackground) {
                    Slog.i("ActivityManager", "Delaying start of: " + serviceRecord);
                    serviceMapLocked.mDelayedStartList.add(serviceRecord);
                    serviceRecord.delayed = z3;
                    return serviceRecord.name;
                }
            }
            if (backgroundStartPrivileges.allowsAny()) {
                serviceRecord.allowBgActivityStartsOnServiceStart(backgroundStartPrivileges);
            }
            return startServiceInnerLocked(serviceMapLocked, intent, serviceRecord, z2, z3, i, str, i3, z6, str2);
        }
        z3 = z4;
        if (backgroundStartPrivileges.allowsAny()) {
        }
        return startServiceInnerLocked(serviceMapLocked, intent, serviceRecord, z2, z3, i, str, i3, z6, str2);
    }

    public final boolean requestStartTargetPermissionsReviewIfNeededLocked(final ServiceRecord serviceRecord, String str, String str2, int i, final Intent intent, final boolean z, final int i2, boolean z2, final IServiceConnection iServiceConnection) {
        if (!this.mAm.getPackageManagerInternal().isPermissionsReviewRequired(serviceRecord.packageName, serviceRecord.userId)) {
            return true;
        }
        if (!z) {
            StringBuilder sb = new StringBuilder();
            sb.append("u");
            sb.append(serviceRecord.userId);
            sb.append(z2 ? " Binding" : " Starting");
            sb.append(" a service in package");
            sb.append(serviceRecord.packageName);
            sb.append(" requires a permissions review");
            Slog.w("ActivityManager", sb.toString());
            return false;
        }
        final Intent intent2 = new Intent("android.intent.action.REVIEW_PERMISSIONS");
        intent2.addFlags(411041792);
        intent2.putExtra("android.intent.extra.PACKAGE_NAME", serviceRecord.packageName);
        if (z2) {
            intent2.putExtra("android.intent.extra.REMOTE_CALLBACK", (Parcelable) new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.am.ActiveServices.2
                public void onResult(Bundle bundle) {
                    ActivityManagerService activityManagerService;
                    ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService2) {
                        try {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (!ActiveServices.this.mPendingServices.contains(serviceRecord)) {
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                PackageManagerInternal packageManagerInternal = ActiveServices.this.mAm.getPackageManagerInternal();
                                ServiceRecord serviceRecord2 = serviceRecord;
                                if (!packageManagerInternal.isPermissionsReviewRequired(serviceRecord2.packageName, serviceRecord2.userId)) {
                                    try {
                                        ActiveServices.this.bringUpServiceLocked(serviceRecord, intent.getFlags(), z, false, false, false, true);
                                        activityManagerService = ActiveServices.this.mAm;
                                    } catch (RemoteException unused) {
                                        activityManagerService = ActiveServices.this.mAm;
                                    } catch (Throwable th) {
                                        ActiveServices.this.mAm.updateOomAdjPendingTargetsLocked(6);
                                        throw th;
                                    }
                                    activityManagerService.updateOomAdjPendingTargetsLocked(6);
                                } else {
                                    ActiveServices.this.unbindServiceLocked(iServiceConnection);
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        } catch (Throwable th2) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th2;
                        }
                    }
                }
            }));
        } else {
            ActivityManagerService activityManagerService = this.mAm;
            intent2.putExtra("android.intent.extra.INTENT", new IntentSender(activityManagerService.mPendingIntentController.getIntentSender(4, str, str2, i, i2, null, null, 0, new Intent[]{intent}, new String[]{intent.resolveType(activityManagerService.mContext.getContentResolver())}, 1409286144, null)));
        }
        this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.ActiveServices.3
            @Override // java.lang.Runnable
            public void run() {
                ActiveServices.this.mAm.mContext.startActivityAsUser(intent2, new UserHandle(i2));
            }
        });
        return false;
    }

    public final boolean deferServiceBringupIfFrozenLocked(final ServiceRecord serviceRecord, final Intent intent, final String str, final String str2, final int i, final int i2, final String str3, final int i3, final boolean z, final boolean z2, final int i4, final BackgroundStartPrivileges backgroundStartPrivileges, final boolean z3, final IServiceConnection iServiceConnection) {
        if (!this.mAm.getPackageManagerInternal().isPackageFrozen(serviceRecord.packageName, i, serviceRecord.userId)) {
            return false;
        }
        ArrayList arrayList = (ArrayList) this.mPendingBringups.get(serviceRecord);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mPendingBringups.put(serviceRecord, arrayList);
        }
        arrayList.add(new Runnable() { // from class: com.android.server.am.ActiveServices.4
            @Override // java.lang.Runnable
            public void run() {
                ActivityManagerService activityManagerService;
                ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        if (!ActiveServices.this.mPendingBringups.containsKey(serviceRecord)) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (!ActiveServices.this.requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord, str, str2, i, intent, z2, i4, z3, iServiceConnection)) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (z3) {
                            try {
                                ActiveServices.this.bringUpServiceLocked(serviceRecord, intent.getFlags(), z2, false, false, false, true);
                                activityManagerService = ActiveServices.this.mAm;
                            } catch (TransactionTooLargeException unused) {
                                activityManagerService = ActiveServices.this.mAm;
                            } catch (Throwable th) {
                                ActiveServices.this.mAm.updateOomAdjPendingTargetsLocked(6);
                                throw th;
                            }
                            activityManagerService.updateOomAdjPendingTargetsLocked(6);
                        } else {
                            try {
                                ActiveServices.this.startServiceInnerLocked(serviceRecord, intent, i, i2, str3, i3, z, z2, backgroundStartPrivileges, str);
                            } catch (TransactionTooLargeException unused2) {
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th2) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
            }
        });
        return true;
    }

    public void schedulePendingServiceStartLocked(String str, int i) {
        int size = this.mPendingBringups.size();
        while (true) {
            for (int i2 = size - 1; i2 >= 0 && size > 0; i2--) {
                ServiceRecord serviceRecord = (ServiceRecord) this.mPendingBringups.keyAt(i2);
                if (serviceRecord.userId == i && TextUtils.equals(serviceRecord.packageName, str)) {
                    ArrayList arrayList = (ArrayList) this.mPendingBringups.valueAt(i2);
                    if (arrayList != null) {
                        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                            ((Runnable) arrayList.get(size2)).run();
                        }
                        arrayList.clear();
                    }
                    int size3 = this.mPendingBringups.size();
                    this.mPendingBringups.remove(serviceRecord);
                    if (size != size3) {
                        break;
                    } else {
                        size = this.mPendingBringups.size();
                    }
                }
            }
            return;
            size = this.mPendingBringups.size();
        }
    }

    public ComponentName startServiceInnerLocked(ServiceMap serviceMap, Intent intent, ServiceRecord serviceRecord, boolean z, boolean z2, int i, String str, int i2, boolean z3, String str2) {
        String str3;
        int i3;
        int i4;
        synchronized (this.mAm.mProcessStats.mLock) {
            ServiceState tracker = serviceRecord.getTracker();
            if (tracker != null) {
                tracker.setStarted(true, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
            }
        }
        serviceRecord.callStart = false;
        int i5 = serviceRecord.appInfo.uid;
        String packageName = serviceRecord.name.getPackageName();
        String className = serviceRecord.name.getClassName();
        FrameworkStatsLog.write(99, i5, packageName, className, 1);
        this.mAm.mBatteryStatsService.noteServiceStartRunning(i5, packageName, className);
        String bringUpServiceLocked = bringUpServiceLocked(serviceRecord, intent.getFlags(), z, false, false, false, true);
        this.mAm.updateOomAdjPendingTargetsLocked(6);
        if (bringUpServiceLocked != null) {
            return new ComponentName("!!", bringUpServiceLocked);
        }
        int i6 = (serviceRecord.appInfo.flags & 2097152) != 0 ? 2 : 1;
        String action = intent.getAction();
        ProcessRecord processRecord = serviceRecord.app;
        if (processRecord == null || processRecord.getThread() == null) {
            str3 = str;
            i3 = 3;
            i4 = i;
        } else if (z3 || !serviceRecord.getConnections().isEmpty()) {
            i4 = i;
            i3 = 2;
            str3 = str;
        } else {
            i4 = i;
            str3 = str;
            i3 = 1;
        }
        FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i5, i, action, 1, false, i3, getShortProcessNameForStats(i4, str3), getShortServiceNameForStats(serviceRecord), i6, packageName, str2, i2, serviceRecord.mProcessStateOnRequest);
        if (serviceRecord.startRequested && z2) {
            boolean z4 = serviceMap.mStartingBackground.size() == 0;
            serviceMap.mStartingBackground.add(serviceRecord);
            serviceRecord.startingBgTimeout = SystemClock.uptimeMillis() + this.mAm.mConstants.BG_START_TIMEOUT;
            if (z4) {
                serviceMap.rescheduleDelayedStartsLocked();
            }
        } else if (z || serviceRecord.fgRequired) {
            serviceMap.ensureNotStartingBackgroundLocked(serviceRecord);
        }
        return serviceRecord.name;
    }

    public final String getShortProcessNameForStats(int i, String str) {
        String[] packagesForUid = this.mAm.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length == 1) {
            if (TextUtils.equals(packagesForUid[0], str)) {
                return null;
            }
            if (str != null && str.startsWith(packagesForUid[0])) {
                return str.substring(packagesForUid[0].length());
            }
        }
        return str;
    }

    public final String getShortServiceNameForStats(ServiceRecord serviceRecord) {
        ComponentName componentName = serviceRecord.getComponentName();
        if (componentName != null) {
            return componentName.getShortClassName();
        }
        return null;
    }

    public final void stopServiceLocked(ServiceRecord serviceRecord, boolean z) {
        traceInstant("stopService(): ", serviceRecord);
        try {
            Trace.traceBegin(64L, "stopServiceLocked()");
            if (serviceRecord.delayed) {
                serviceRecord.delayedStop = true;
                return;
            }
            maybeStopShortFgsTimeoutLocked(serviceRecord);
            int i = serviceRecord.appInfo.uid;
            String packageName = serviceRecord.name.getPackageName();
            String className = serviceRecord.name.getClassName();
            FrameworkStatsLog.write(99, i, packageName, className, 2);
            this.mAm.mBatteryStatsService.noteServiceStopRunning(i, packageName, className);
            serviceRecord.startRequested = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            serviceRecord.callStart = false;
            bringDownServiceIfNeededLocked(serviceRecord, false, false, z, "stopService");
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public int stopServiceLocked(IApplicationThread iApplicationThread, Intent intent, String str, int i, boolean z, int i2, String str2, String str3) {
        ProcessRecord recordForAppLOSP = this.mAm.getRecordForAppLOSP(iApplicationThread);
        if (iApplicationThread != null && recordForAppLOSP == null) {
            throw new SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + Binder.getCallingPid() + ") when stopping service " + intent);
        }
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, str3, z, i2, str2, str, null, Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, false, false, null, false);
        if (retrieveServiceLocked == null) {
            return 0;
        }
        ServiceRecord serviceRecord = retrieveServiceLocked.record;
        if (serviceRecord == null) {
            return -1;
        }
        if (isForceStopDisabled(serviceRecord.packageName, i, null, null, null)) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            stopServiceLocked(retrieveServiceLocked.record, false);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void stopInBackgroundLocked(int i) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(UserHandle.getUserId(i));
        if (serviceMap != null) {
            ArrayList arrayList = null;
            for (int size = serviceMap.mServicesByInstanceName.size() - 1; size >= 0; size--) {
                ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(size);
                ApplicationInfo applicationInfo = serviceRecord.appInfo;
                int i2 = applicationInfo.uid;
                if (i2 == i && serviceRecord.startRequested && this.mAm.getAppStartModeLOSP(i2, serviceRecord.packageName, applicationInfo.targetSdkVersion, -1, false, false, false) != 0) {
                    if (isForceStopDisabled(serviceRecord.packageName, serviceRecord.userId, null, null, null)) {
                        Slog.d("ActivityManager", "cannot stop service due to MDM policy restriction");
                    } else {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        String str = serviceRecord.shortInstanceName;
                        EventLogTags.writeAmStopIdleService(serviceRecord.appInfo.uid, str);
                        StringBuilder sb = new StringBuilder(64);
                        sb.append("Stopping service due to app idle: ");
                        UserHandle.formatUid(sb, serviceRecord.appInfo.uid);
                        sb.append(" ");
                        TimeUtils.formatDuration(serviceRecord.createRealTime - SystemClock.elapsedRealtime(), sb);
                        sb.append(" ");
                        sb.append(str);
                        Slog.w("ActivityManager", sb.toString());
                        arrayList.add(serviceRecord);
                        if (appRestrictedAnyInBackground(serviceRecord.appInfo.uid, serviceRecord.packageName)) {
                            cancelForegroundNotificationLocked(serviceRecord);
                        }
                    }
                }
            }
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i3 = size2 - 1; i3 >= 0; i3--) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) arrayList.get(i3);
                    serviceRecord2.delayed = false;
                    serviceMap.ensureNotStartingBackgroundLocked(serviceRecord2);
                    stopServiceLocked(serviceRecord2, true);
                }
                if (size2 > 0) {
                    this.mAm.updateOomAdjPendingTargetsLocked(18);
                }
            }
        }
    }

    public void killMisbehavingService(ServiceRecord serviceRecord, int i, int i2, String str, int i3) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (!serviceRecord.destroying) {
                    stopServiceLocked(serviceRecord, false);
                } else {
                    ServiceRecord serviceRecord2 = (ServiceRecord) getServiceMapLocked(serviceRecord.userId).mServicesByInstanceName.remove(serviceRecord.instanceName);
                    if (serviceRecord2 != null) {
                        stopServiceLocked(serviceRecord2, false);
                    }
                }
                this.mAm.crashApplicationWithType(i, i2, str, -1, "Bad notification for startForeground", true, i3);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public IBinder peekServiceLocked(Intent intent, String str, String str2) {
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, null, str, str2, Binder.getCallingPid(), Binder.getCallingUid(), UserHandle.getCallingUserId(), false, false, false, false, false);
        if (retrieveServiceLocked != null) {
            ServiceRecord serviceRecord = retrieveServiceLocked.record;
            if (serviceRecord == null) {
                throw new SecurityException("Permission Denial: Accessing service from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " requires " + retrieveServiceLocked.permission);
            }
            IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.get(serviceRecord.intent);
            if (intentBindRecord != null) {
                return intentBindRecord.binder;
            }
        }
        return null;
    }

    public boolean stopServiceTokenLocked(ComponentName componentName, IBinder iBinder, int i) {
        ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, UserHandle.getCallingUserId());
        if (findServiceLocked == null) {
            return false;
        }
        if (i >= 0) {
            ServiceRecord.StartItem findDeliveredStart = findServiceLocked.findDeliveredStart(i, false, false);
            if (findDeliveredStart != null) {
                while (findServiceLocked.deliveredStarts.size() > 0) {
                    ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) findServiceLocked.deliveredStarts.remove(0);
                    startItem.removeUriPermissionsLocked();
                    if (startItem == findDeliveredStart) {
                        break;
                    }
                }
            }
            if (findServiceLocked.getLastStartId() != i) {
                return false;
            }
            if (findServiceLocked.deliveredStarts.size() > 0) {
                Slog.w("ActivityManager", "stopServiceToken startId " + i + " is last, but have " + findServiceLocked.deliveredStarts.size() + " remaining args");
            }
        }
        maybeStopShortFgsTimeoutLocked(findServiceLocked);
        int i2 = findServiceLocked.appInfo.uid;
        String packageName = findServiceLocked.name.getPackageName();
        String className = findServiceLocked.name.getClassName();
        FrameworkStatsLog.write(99, i2, packageName, className, 2);
        this.mAm.mBatteryStatsService.noteServiceStopRunning(i2, packageName, className);
        findServiceLocked.startRequested = false;
        if (findServiceLocked.tracker != null) {
            synchronized (this.mAm.mProcessStats.mLock) {
                findServiceLocked.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
            }
        }
        findServiceLocked.callStart = false;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        bringDownServiceIfNeededLocked(findServiceLocked, false, false, false, "stopServiceToken");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public void setServiceForegroundLocked(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) {
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            if (findServiceLocked != null) {
                setServiceForegroundInnerLocked(findServiceLocked, i, notification, i2, i3);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getForegroundServiceTypeLocked(ComponentName componentName, IBinder iBinder) {
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            return findServiceLocked != null ? findServiceLocked.foregroundServiceType : 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean foregroundAppShownEnoughLocked(ActiveForegroundApp activeForegroundApp, long j) {
        long j2;
        activeForegroundApp.mHideTime = Long.MAX_VALUE;
        if (activeForegroundApp.mShownWhileTop) {
            return true;
        }
        if (this.mScreenOn || activeForegroundApp.mShownWhileScreenOn) {
            long j3 = activeForegroundApp.mStartVisibleTime;
            if (activeForegroundApp.mStartTime != j3) {
                j2 = this.mAm.mConstants.FGSERVICE_SCREEN_ON_AFTER_TIME;
            } else {
                j2 = this.mAm.mConstants.FGSERVICE_MIN_SHOWN_TIME;
            }
            long j4 = j3 + j2;
            if (j >= j4) {
                return true;
            }
            long j5 = j + this.mAm.mConstants.FGSERVICE_MIN_REPORT_TIME;
            if (j5 > j4) {
                j4 = j5;
            }
            activeForegroundApp.mHideTime = j4;
        } else {
            long j6 = activeForegroundApp.mEndTime + this.mAm.mConstants.FGSERVICE_SCREEN_ON_BEFORE_TIME;
            if (j >= j6) {
                return true;
            }
            activeForegroundApp.mHideTime = j6;
        }
        return false;
    }

    public void updateForegroundApps(ServiceMap serviceMap) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (serviceMap != null) {
                    serviceMap.mPendingRemoveForegroundApps.clear();
                    int size = serviceMap.mActiveForegroundApps.size() - 1;
                    long j = Long.MAX_VALUE;
                    while (size >= 0) {
                        ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size);
                        if (activeForegroundApp.mEndTime != 0) {
                            if (foregroundAppShownEnoughLocked(activeForegroundApp, elapsedRealtime)) {
                                serviceMap.mPendingRemoveForegroundApps.add((String) serviceMap.mActiveForegroundApps.keyAt(size));
                                serviceMap.mActiveForegroundAppsChanged = true;
                                size--;
                            } else {
                                long j2 = activeForegroundApp.mHideTime;
                                if (j2 < j) {
                                    j = j2;
                                }
                            }
                        }
                        if (!activeForegroundApp.mAppOnTop && !isForegroundServiceAllowedInBackgroundRestricted(activeForegroundApp.mUid, activeForegroundApp.mPackageName)) {
                            int size2 = serviceMap.mActiveForegroundApps.size();
                            stopAllForegroundServicesLocked(activeForegroundApp.mUid, activeForegroundApp.mPackageName);
                            int size3 = size2 - serviceMap.mActiveForegroundApps.size();
                            if (size3 > 1) {
                                size = (size - size3) + 1;
                            }
                        }
                        size--;
                    }
                    for (int size4 = serviceMap.mPendingRemoveForegroundApps.size() - 1; size4 >= 0; size4--) {
                        serviceMap.mActiveForegroundApps.remove(serviceMap.mPendingRemoveForegroundApps.get(size4));
                    }
                    serviceMap.removeMessages(2);
                    if (j < Long.MAX_VALUE) {
                        serviceMap.sendMessageAtTime(serviceMap.obtainMessage(2), (j + SystemClock.uptimeMillis()) - SystemClock.elapsedRealtime());
                    }
                }
                serviceMap.mActiveForegroundAppsChanged = false;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void requestUpdateActiveForegroundAppsLocked(ServiceMap serviceMap, long j) {
        Message obtainMessage = serviceMap.obtainMessage(2);
        if (j != 0) {
            serviceMap.sendMessageAtTime(obtainMessage, (j + SystemClock.uptimeMillis()) - SystemClock.elapsedRealtime());
        } else {
            serviceMap.mActiveForegroundAppsChanged = true;
            serviceMap.sendMessage(obtainMessage);
        }
    }

    public final void decActiveForegroundAppLocked(ServiceMap serviceMap, ServiceRecord serviceRecord) {
        ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.get(serviceRecord.packageName);
        if (activeForegroundApp != null) {
            int i = activeForegroundApp.mNumActive - 1;
            activeForegroundApp.mNumActive = i;
            if (i <= 0) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                activeForegroundApp.mEndTime = elapsedRealtime;
                if (foregroundAppShownEnoughLocked(activeForegroundApp, elapsedRealtime)) {
                    serviceMap.mActiveForegroundApps.remove(serviceRecord.packageName);
                    serviceMap.mActiveForegroundAppsChanged = true;
                    requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
                } else {
                    long j = activeForegroundApp.mHideTime;
                    if (j < Long.MAX_VALUE) {
                        requestUpdateActiveForegroundAppsLocked(serviceMap, j);
                    }
                }
            }
        }
    }

    public void updateScreenStateLocked(boolean z) {
        if (this.mScreenOn != z) {
            this.mScreenOn = z;
            if (z) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                for (int size = this.mServiceMap.size() - 1; size >= 0; size--) {
                    ServiceMap serviceMap = (ServiceMap) this.mServiceMap.valueAt(size);
                    boolean z2 = false;
                    long j = Long.MAX_VALUE;
                    for (int size2 = serviceMap.mActiveForegroundApps.size() - 1; size2 >= 0; size2--) {
                        ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size2);
                        if (activeForegroundApp.mEndTime == 0) {
                            if (!activeForegroundApp.mShownWhileScreenOn) {
                                activeForegroundApp.mShownWhileScreenOn = true;
                                activeForegroundApp.mStartVisibleTime = elapsedRealtime;
                            }
                        } else {
                            if (!activeForegroundApp.mShownWhileScreenOn && activeForegroundApp.mStartVisibleTime == activeForegroundApp.mStartTime) {
                                activeForegroundApp.mStartVisibleTime = elapsedRealtime;
                                activeForegroundApp.mEndTime = elapsedRealtime;
                            }
                            if (foregroundAppShownEnoughLocked(activeForegroundApp, elapsedRealtime)) {
                                serviceMap.mActiveForegroundApps.remove(activeForegroundApp.mPackageName);
                                serviceMap.mActiveForegroundAppsChanged = true;
                                z2 = true;
                            } else {
                                long j2 = activeForegroundApp.mHideTime;
                                if (j2 < j) {
                                    j = j2;
                                }
                            }
                        }
                    }
                    if (z2) {
                        requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
                    } else if (j < Long.MAX_VALUE) {
                        requestUpdateActiveForegroundAppsLocked(serviceMap, j);
                    }
                }
            }
        }
    }

    public void foregroundServiceProcStateChangedLocked(UidRecord uidRecord) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(UserHandle.getUserId(uidRecord.getUid()));
        if (serviceMap != null) {
            boolean z = false;
            for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size);
                if (activeForegroundApp.mUid == uidRecord.getUid()) {
                    if (uidRecord.getCurProcState() <= 2) {
                        if (!activeForegroundApp.mAppOnTop) {
                            activeForegroundApp.mAppOnTop = true;
                            z = true;
                        }
                        activeForegroundApp.mShownWhileTop = true;
                    } else if (activeForegroundApp.mAppOnTop) {
                        activeForegroundApp.mAppOnTop = false;
                        z = true;
                    }
                }
            }
            if (z) {
                requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
            }
        }
    }

    public final boolean isForegroundServiceAllowedInBackgroundRestricted(ProcessRecord processRecord) {
        ProcessStateRecord processStateRecord = processRecord.mState;
        if (!isDeviceProvisioningPackage(processRecord.info.packageName) && processStateRecord.isBackgroundRestricted() && processStateRecord.getSetProcState() > 3) {
            return processStateRecord.getSetProcState() == 4 && processStateRecord.isSetBoundByNonBgRestrictedApp();
        }
        return true;
    }

    public final boolean isForegroundServiceAllowedInBackgroundRestricted(int i, String str) {
        ProcessRecord processInPackage;
        UidRecord uidRecordLOSP = this.mAm.mProcessList.getUidRecordLOSP(i);
        return (uidRecordLOSP == null || (processInPackage = uidRecordLOSP.getProcessInPackage(str)) == null || !isForegroundServiceAllowedInBackgroundRestricted(processInPackage)) ? false : true;
    }

    public final boolean isTempAllowedByAlarmClock(int i) {
        ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i);
        return isAllowlistedForFgsStartLOSP != null && isAllowlistedForFgsStartLOSP.mReasonCode == 301;
    }

    public void logFgsApiBeginLocked(int i, int i2, int i3) {
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceApiEventBegin(i, i2, i3, "");
        }
    }

    public void logFgsApiEndLocked(int i, int i2, int i3) {
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceApiEventEnd(i, i2, i3);
        }
    }

    public void logFgsApiStateChangedLocked(int i, int i2, int i3, int i4) {
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceApiStateChanged(i, i2, i3, i4);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x03ef A[Catch: all -> 0x0518, TryCatch #5 {all -> 0x0518, blocks: (B:85:0x0305, B:87:0x0309, B:89:0x0323, B:92:0x032c, B:95:0x0342, B:101:0x037d, B:102:0x03b5, B:106:0x03ef, B:108:0x03f5, B:109:0x03fa, B:111:0x040b, B:113:0x0413, B:115:0x041f, B:117:0x0436, B:119:0x043c, B:122:0x0446, B:123:0x044c, B:124:0x0462, B:125:0x0469, B:127:0x0480, B:128:0x0486, B:135:0x04a3, B:136:0x04e2, B:140:0x04ed, B:141:0x04f5, B:143:0x04ff, B:144:0x0502, B:148:0x04f3, B:152:0x04a0, B:176:0x03c6, B:177:0x03dc, B:178:0x0385, B:181:0x038f, B:183:0x0398, B:185:0x03ab, B:192:0x036f, B:193:0x0374, B:210:0x02d7, B:216:0x02e2, B:138:0x04e3, B:139:0x04ec, B:130:0x0487, B:132:0x048d, B:133:0x049c), top: B:55:0x01d0, inners: #1, #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x051c  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x053f  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0683 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x03e7  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0247  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setServiceForegroundInnerLocked(ServiceRecord serviceRecord, int i, Notification notification, int i2, int i3) {
        boolean z;
        int checkOpNoThrow;
        boolean z2;
        boolean z3;
        ProcessServiceRecord processServiceRecord;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        int i6;
        UidRecord uidRecord;
        int i7;
        int i8;
        int i9;
        boolean z6;
        boolean z7;
        int i10;
        boolean z8;
        Pair pair;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12 = notification;
        if (i == 0) {
            if (serviceRecord.isForeground) {
                traceInstant("stopForeground(): ", serviceRecord);
                ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
                if (serviceMapLocked != null) {
                    decActiveForegroundAppLocked(serviceMapLocked, serviceRecord);
                }
                maybeStopShortFgsTimeoutLocked(serviceRecord);
                if ((i2 & 1) != 0) {
                    cancelForegroundNotificationLocked(serviceRecord);
                    serviceRecord.foregroundId = 0;
                    serviceRecord.foregroundNoti = null;
                } else if (serviceRecord.appInfo.targetSdkVersion >= 21) {
                    if (!serviceRecord.mFgsNotificationShown) {
                        serviceRecord.postNotification(false);
                    }
                    dropFgsNotificationStateLocked(serviceRecord);
                    if ((i2 & 2) != 0) {
                        serviceRecord.foregroundId = 0;
                        serviceRecord.foregroundNoti = null;
                    }
                }
                serviceRecord.isForeground = false;
                serviceRecord.mFgsExitTime = SystemClock.uptimeMillis();
                synchronized (this.mAm.mProcessStats.mLock) {
                    ServiceState tracker = serviceRecord.getTracker();
                    if (tracker != null) {
                        tracker.setForeground(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                    }
                }
                AppOpsService appOpsService = this.mAm.mAppOpsService;
                appOpsService.finishOperation(AppOpsManager.getToken(appOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
                unregisterAppOpCallbackLocked(serviceRecord);
                long j = serviceRecord.mFgsExitTime;
                long j2 = serviceRecord.mFgsEnterTime;
                logFGSStateChangeLocked(serviceRecord, 2, j > j2 ? (int) (j - j2) : 0, 1, 0);
                serviceRecord.foregroundServiceType = 0;
                synchronized (this.mFGSLogger) {
                    this.mFGSLogger.logForegroundServiceStop(serviceRecord.appInfo.uid, serviceRecord);
                }
                serviceRecord.mFgsNotificationWasDeferred = false;
                signalForegroundServiceObserversLocked(serviceRecord);
                resetFgsRestrictionLocked(serviceRecord);
                this.mAm.updateForegroundServiceUsageStats(serviceRecord.name, serviceRecord.userId, false);
                ProcessRecord processRecord = serviceRecord.app;
                if (processRecord != null) {
                    this.mAm.updateLruProcessLocked(processRecord, false, null);
                    updateServiceForegroundLocked(serviceRecord.app.mServices, true);
                }
                updateNumForegroundServicesLocked();
                return;
            }
            return;
        }
        if (z12 == 0) {
            throw new IllegalArgumentException("null notification");
        }
        traceInstant("startForeground(): ", serviceRecord);
        if (serviceRecord.appInfo.isInstantApp()) {
            AppOpsManager appOpsManager = this.mAm.getAppOpsManager();
            ApplicationInfo applicationInfo = serviceRecord.appInfo;
            int checkOpNoThrow2 = appOpsManager.checkOpNoThrow(68, applicationInfo.uid, applicationInfo.packageName);
            if (checkOpNoThrow2 != 0) {
                if (checkOpNoThrow2 == 1) {
                    Slog.w("ActivityManager", "Instant app " + serviceRecord.appInfo.packageName + " does not have permission to create foreground services, ignoring.");
                    return;
                }
                if (checkOpNoThrow2 == 2) {
                    throw new SecurityException("Instant app " + serviceRecord.appInfo.packageName + " does not have permission to create foreground services");
                }
                this.mAm.enforcePermission("android.permission.INSTANT_APP_FOREGROUND_SERVICE", serviceRecord.app.getPid(), serviceRecord.appInfo.uid, "startForeground");
            }
        } else if (serviceRecord.appInfo.targetSdkVersion >= 28) {
            this.mAm.enforcePermission("android.permission.FOREGROUND_SERVICE", serviceRecord.app.getPid(), serviceRecord.appInfo.uid, "startForeground");
        }
        int foregroundServiceType = serviceRecord.serviceInfo.getForegroundServiceType();
        int i11 = i3 == -1 ? foregroundServiceType : i3;
        if ((i11 & foregroundServiceType) != i11 && !SystemProperties.getBoolean("debug.skip_fgs_manifest_type_check", false)) {
            String str = "foregroundServiceType " + String.format("0x%08X", Integer.valueOf(i11)) + " is not a subset of foregroundServiceType attribute " + String.format("0x%08X", Integer.valueOf(foregroundServiceType)) + " in service element of manifest file";
            if (!serviceRecord.appInfo.isInstantApp() || CompatChanges.isChangeEnabled(261055255L, serviceRecord.appInfo.uid)) {
                throw new IllegalArgumentException(str);
            }
            Slog.w("ActivityManager", str + "\nThis will be an exception once the target SDK level is UDC");
        }
        if ((i11 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0 && i11 != 2048) {
            Slog.w("ActivityManager", "startForeground(): FOREGROUND_SERVICE_TYPE_SHORT_SERVICE is combined with other types. SHORT_SERVICE will be ignored.");
            i11 &= -2049;
        }
        int i12 = i11;
        if (serviceRecord.fgRequired) {
            serviceRecord.fgRequired = false;
            serviceRecord.fgWaiting = false;
            this.mAm.mHandler.removeMessages(66, serviceRecord);
            z = true;
        } else {
            z = false;
        }
        boolean z13 = z;
        ProcessServiceRecord processServiceRecord2 = serviceRecord.app.mServices;
        try {
            checkOpNoThrow = this.mAm.getAppOpsManager().checkOpNoThrow(76, serviceRecord.appInfo.uid, serviceRecord.packageName);
        } catch (Throwable th) {
            th = th;
            z12 = 0;
        }
        try {
            if (checkOpNoThrow != 0) {
                if (checkOpNoThrow == 1) {
                    Slog.w("ActivityManager", "Service.startForeground() not allowed due to app op: service " + serviceRecord.shortInstanceName);
                    z2 = true;
                    if (!z2 || isForegroundServiceAllowedInBackgroundRestricted(serviceRecord.app) || isTempAllowedByAlarmClock(serviceRecord.app.uid)) {
                        z3 = z2;
                    } else {
                        Slog.w("ActivityManager", "Service.startForeground() not allowed due to bg restriction: service " + serviceRecord.shortInstanceName);
                        updateServiceForegroundLocked(processServiceRecord2, false);
                        z3 = true;
                    }
                    boolean isBgFgsRestrictionEnabled = isBgFgsRestrictionEnabled(serviceRecord);
                    if (z3) {
                        if (i12 == 2048 && !serviceRecord.startRequested) {
                            throw new StartForegroundCalledOnStoppedServiceException("startForeground(SHORT_SERVICE) called on a service that's not started.");
                        }
                        boolean isShortFgs = serviceRecord.isShortFgs();
                        boolean z14 = i12 == 2048;
                        serviceRecord.shouldTriggerShortFgsTimeout(SystemClock.uptimeMillis());
                        try {
                            if (serviceRecord.isForeground && isShortFgs) {
                                serviceRecord.mAllowStartForeground = -1;
                                processServiceRecord = processServiceRecord2;
                                i7 = i12;
                                setFgsRestrictionLocked(serviceRecord.serviceInfo.packageName, serviceRecord.app.getPid(), serviceRecord.appInfo.uid, serviceRecord.intent.getIntent(), serviceRecord, serviceRecord.userId, BackgroundStartPrivileges.NONE, false, false);
                                if (serviceRecord.mAllowStartForeground == -1) {
                                    Slog.w("ActivityManager", "FGS type change to/from SHORT_SERVICE:  BFSL DENIED.");
                                }
                                if (isBgFgsRestrictionEnabled && serviceRecord.mAllowStartForeground == -1) {
                                    z9 = false;
                                    if (z9) {
                                        if (z14) {
                                            z10 = true;
                                            z11 = false;
                                            z6 = z11;
                                            z7 = z10;
                                            i8 = -1;
                                            i10 = 0;
                                        }
                                    } else if (z14) {
                                        z11 = true;
                                        z10 = false;
                                        z6 = z11;
                                        z7 = z10;
                                        i8 = -1;
                                        i10 = 0;
                                    }
                                    z11 = false;
                                    z10 = false;
                                    z6 = z11;
                                    z7 = z10;
                                    i8 = -1;
                                    i10 = 0;
                                }
                                z9 = true;
                                if (z9) {
                                }
                                z11 = false;
                                z10 = false;
                                z6 = z11;
                                z7 = z10;
                                i8 = -1;
                                i10 = 0;
                            } else {
                                processServiceRecord = processServiceRecord2;
                                i7 = i12;
                                int i13 = serviceRecord.mStartForegroundCount;
                                if (i13 == 0) {
                                    if (!serviceRecord.fgRequired) {
                                        long elapsedRealtime = SystemClock.elapsedRealtime() - serviceRecord.createRealTime;
                                        if (elapsedRealtime > this.mAm.mConstants.mFgsStartForegroundTimeoutMs) {
                                            resetFgsRestrictionLocked(serviceRecord);
                                            i8 = -1;
                                            setFgsRestrictionLocked(serviceRecord.serviceInfo.packageName, serviceRecord.app.getPid(), serviceRecord.appInfo.uid, serviceRecord.intent.getIntent(), serviceRecord, serviceRecord.userId, BackgroundStartPrivileges.NONE, false, false);
                                            String str2 = "startForegroundDelayMs:" + elapsedRealtime;
                                            if (serviceRecord.mInfoAllowStartForeground != null) {
                                                serviceRecord.mInfoAllowStartForeground += "; " + str2;
                                            } else {
                                                serviceRecord.mInfoAllowStartForeground = str2;
                                            }
                                            i9 = 0;
                                            serviceRecord.mLoggedInfoAllowStartForeground = false;
                                        }
                                    }
                                    i8 = -1;
                                    i9 = 0;
                                } else {
                                    i8 = -1;
                                    i9 = 0;
                                    i9 = 0;
                                    if (i13 >= 1) {
                                        setFgsRestrictionLocked(serviceRecord.serviceInfo.packageName, serviceRecord.app.getPid(), serviceRecord.appInfo.uid, serviceRecord.intent.getIntent(), serviceRecord, serviceRecord.userId, BackgroundStartPrivileges.NONE, false, false);
                                    }
                                }
                                z6 = i9 == true ? 1 : 0;
                                z7 = z6;
                                i10 = i9;
                            }
                            if (!serviceRecord.mAllowWhileInUsePermissionInFgs) {
                                Slog.w("ActivityManager", "Foreground service started from background can not have location/camera/microphone access: service " + serviceRecord.shortInstanceName);
                            }
                            if (!z7) {
                                logFgsBackgroundStart(serviceRecord);
                                if (serviceRecord.mAllowStartForeground == i8 && isBgFgsRestrictionEnabled) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Service.startForeground() not allowed due to mAllowStartForeground false: service ");
                                    sb.append(serviceRecord.shortInstanceName);
                                    sb.append(isShortFgs ? " (Called on SHORT_SERVICE)" : "");
                                    String sb2 = sb.toString();
                                    Slog.w("ActivityManager", sb2);
                                    showFgsBgRestrictedNotificationLocked(serviceRecord);
                                    updateServiceForegroundLocked(processServiceRecord, true);
                                    logFGSStateChangeLocked(serviceRecord, 3, 0, 0, 0);
                                    if (CompatChanges.isChangeEnabled(174041399L, serviceRecord.appInfo.uid)) {
                                        throw new ForegroundServiceStartNotAllowedException(sb2);
                                    }
                                    z8 = true;
                                    i4 = i7;
                                    if (z8) {
                                        if (i4 != 0) {
                                            int i14 = 1073741824;
                                            if ((i4 & 1073741824) == 0) {
                                                i14 = i10;
                                            }
                                            int highestOneBit = Integer.highestOneBit(i4);
                                            int i15 = i4;
                                            Pair pair2 = null;
                                            while (true) {
                                                if (highestOneBit == 0) {
                                                    z4 = true;
                                                    pair = pair2;
                                                    break;
                                                }
                                                Pair validateForegroundServiceType = validateForegroundServiceType(serviceRecord, highestOneBit, i14, i3);
                                                i15 &= ~highestOneBit;
                                                z4 = true;
                                                if (((Integer) validateForegroundServiceType.first).intValue() != 1) {
                                                    pair = validateForegroundServiceType;
                                                    break;
                                                } else {
                                                    highestOneBit = Integer.highestOneBit(i15);
                                                    pair2 = validateForegroundServiceType;
                                                }
                                            }
                                        } else {
                                            pair = validateForegroundServiceType(serviceRecord, i4, i10, i3);
                                            z4 = true;
                                        }
                                        int intValue = ((Integer) pair.first).intValue();
                                        if (pair.second != null) {
                                            logFGSStateChangeLocked(serviceRecord, 3, 0, 0, ((Integer) pair.first).intValue());
                                            throw ((RuntimeException) pair.second);
                                        }
                                        i5 = intValue;
                                        z5 = z6;
                                        z3 = z8;
                                        z12 = i10;
                                    } else {
                                        z4 = true;
                                        z5 = z6;
                                        z3 = z8;
                                        i5 = i10;
                                        z12 = i10;
                                    }
                                }
                            }
                            z8 = z3;
                            i4 = i7;
                            if (z8) {
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            z12 = 0;
                            if (z13) {
                                synchronized (this.mAm.mProcessStats.mLock) {
                                    ServiceState tracker2 = serviceRecord.getTracker();
                                    if (tracker2 != null) {
                                        tracker2.setForeground(z12, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                                    }
                                }
                            }
                            if (z) {
                                AppOpsService appOpsService2 = this.mAm.mAppOpsService;
                                appOpsService2.finishOperation(AppOpsManager.getToken(appOpsService2), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
                            }
                            throw th;
                        }
                    } else {
                        processServiceRecord = processServiceRecord2;
                        i4 = i12;
                        z12 = 0;
                        z4 = true;
                        i5 = 0;
                        z5 = false;
                    }
                    if (!z3) {
                        if (serviceRecord.foregroundId != i) {
                            cancelForegroundNotificationLocked(serviceRecord);
                            serviceRecord.foregroundId = i;
                        }
                        boolean z15 = z4;
                        notification.flags |= 64;
                        serviceRecord.foregroundNoti = notification;
                        serviceRecord.foregroundServiceType = i4;
                        if (serviceRecord.isForeground) {
                            i6 = 2;
                            z13 = z13;
                        } else {
                            ServiceMap serviceMapLocked2 = getServiceMapLocked(serviceRecord.userId);
                            if (serviceMapLocked2 != null) {
                                ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMapLocked2.mActiveForegroundApps.get(serviceRecord.packageName);
                                if (activeForegroundApp == null) {
                                    activeForegroundApp = new ActiveForegroundApp();
                                    activeForegroundApp.mPackageName = serviceRecord.packageName;
                                    activeForegroundApp.mUid = serviceRecord.appInfo.uid;
                                    activeForegroundApp.mShownWhileScreenOn = this.mScreenOn;
                                    ProcessRecord processRecord2 = serviceRecord.app;
                                    if (processRecord2 == null || (uidRecord = processRecord2.getUidRecord()) == null) {
                                        i6 = 2;
                                    } else {
                                        i6 = 2;
                                        boolean z16 = uidRecord.getCurProcState() <= 2 ? z15 ? 1 : 0 : z12;
                                        activeForegroundApp.mShownWhileTop = z16;
                                        activeForegroundApp.mAppOnTop = z16;
                                    }
                                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                                    activeForegroundApp.mStartVisibleTime = elapsedRealtime2;
                                    activeForegroundApp.mStartTime = elapsedRealtime2;
                                    serviceMapLocked2.mActiveForegroundApps.put(serviceRecord.packageName, activeForegroundApp);
                                    requestUpdateActiveForegroundAppsLocked(serviceMapLocked2, 0L);
                                } else {
                                    i6 = 2;
                                }
                                activeForegroundApp.mNumActive += z15 ? 1 : 0;
                            } else {
                                i6 = 2;
                            }
                            serviceRecord.isForeground = z15;
                            serviceRecord.mAllowStartForegroundAtEntering = serviceRecord.mAllowStartForeground;
                            serviceRecord.mAllowWhileInUsePermissionInFgsAtEntering = serviceRecord.mAllowWhileInUsePermissionInFgs;
                            serviceRecord.mStartForegroundCount += z15 ? 1 : 0;
                            serviceRecord.mFgsEnterTime = SystemClock.uptimeMillis();
                            if (z13) {
                                z13 = z12;
                            } else {
                                synchronized (this.mAm.mProcessStats.mLock) {
                                    ServiceState tracker3 = serviceRecord.getTracker();
                                    if (tracker3 != null) {
                                        tracker3.setForeground(z15, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                                    }
                                }
                                z13 = z13;
                            }
                            AppOpsService appOpsService3 = this.mAm.mAppOpsService;
                            appOpsService3.startOperation(AppOpsManager.getToken(appOpsService3), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null, true, false, "", false, 0, -1);
                            registerAppOpCallbackLocked(serviceRecord);
                            this.mAm.updateForegroundServiceUsageStats(serviceRecord.name, serviceRecord.userId, z15);
                            logFGSStateChangeLocked(serviceRecord, 1, 0, 0, i5);
                            synchronized (this.mFGSLogger) {
                                this.mFGSLogger.logForegroundServiceStart(serviceRecord.appInfo.uid, z12, serviceRecord);
                            }
                            updateNumForegroundServicesLocked();
                            z13 = z13;
                        }
                        signalForegroundServiceObserversLocked(serviceRecord);
                        serviceRecord.postNotification(z15);
                        if (serviceRecord.app != null) {
                            updateServiceForegroundLocked(processServiceRecord, z15);
                        }
                        getServiceMapLocked(serviceRecord.userId).ensureNotStartingBackgroundLocked(serviceRecord);
                        this.mAm.notifyPackageUse(serviceRecord.serviceInfo.packageName, i6);
                        maybeUpdateShortFgsTrackingLocked(serviceRecord, z5);
                    }
                    if (z13) {
                        synchronized (this.mAm.mProcessStats.mLock) {
                            ServiceState tracker4 = serviceRecord.getTracker();
                            if (tracker4 != null) {
                                tracker4.setForeground((boolean) z12, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                            }
                        }
                    }
                    if (z) {
                        return;
                    }
                    AppOpsService appOpsService4 = this.mAm.mAppOpsService;
                    appOpsService4.finishOperation(AppOpsManager.getToken(appOpsService4), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
                    return;
                }
                if (checkOpNoThrow != 3) {
                    throw new SecurityException("Foreground not allowed as per app op");
                }
            }
            if (z3) {
            }
            if (!z3) {
            }
            if (z13) {
            }
            if (z) {
            }
        } catch (Throwable th3) {
            th = th3;
            if (z13) {
            }
            if (z) {
            }
            throw th;
        }
        z2 = false;
        if (z2) {
        }
        z3 = z2;
        boolean isBgFgsRestrictionEnabled2 = isBgFgsRestrictionEnabled(serviceRecord);
    }

    public final boolean withinFgsDeferRateLimit(ServiceRecord serviceRecord, long j) {
        if (j < serviceRecord.fgDisplayTime) {
            return false;
        }
        return j < this.mFgsDeferralEligible.get(serviceRecord.appInfo.uid, 0L);
    }

    public final Pair validateForegroundServiceType(ServiceRecord serviceRecord, int i, int i2, int i3) {
        Object obj;
        ForegroundServiceTypePolicy defaultPolicy = ForegroundServiceTypePolicy.getDefaultPolicy();
        ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = defaultPolicy.getForegroundServiceTypePolicyInfo(i, i2);
        Context context = this.mAm.mContext;
        String str = serviceRecord.packageName;
        ProcessRecord processRecord = serviceRecord.app;
        int checkForegroundServiceTypePolicy = defaultPolicy.checkForegroundServiceTypePolicy(context, str, processRecord.uid, processRecord.getPid(), serviceRecord.mAllowWhileInUsePermissionInFgs, foregroundServiceTypePolicyInfo);
        if (checkForegroundServiceTypePolicy == 2) {
            String str2 = "Starting FGS with type " + ServiceInfo.foregroundServiceTypeToLabel(i) + " code=" + checkForegroundServiceTypePolicy + " callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion;
            Slog.wtfQuiet("ActivityManager", str2);
            Slog.w("ActivityManager", str2);
        } else {
            if (checkForegroundServiceTypePolicy != 3) {
                if (checkForegroundServiceTypePolicy == 4) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Starting FGS with type ");
                    sb.append(ServiceInfo.foregroundServiceTypeToLabel(i));
                    sb.append(" code=");
                    sb.append(checkForegroundServiceTypePolicy);
                    sb.append(" callerApp=");
                    sb.append(serviceRecord.app);
                    sb.append(" targetSDK=");
                    sb.append(serviceRecord.app.info.targetSdkVersion);
                    sb.append(" requiredPermissions=");
                    sb.append(foregroundServiceTypePolicyInfo.toPermissionString());
                    sb.append(foregroundServiceTypePolicyInfo.hasForegroundOnlyPermission() ? " and the app must be in the eligible state/exemptions to access the foreground only permission" : "");
                    String sb2 = sb.toString();
                    Slog.wtfQuiet("ActivityManager", sb2);
                    Slog.w("ActivityManager", sb2);
                } else if (checkForegroundServiceTypePolicy == 5) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Starting FGS with type ");
                    sb3.append(ServiceInfo.foregroundServiceTypeToLabel(i));
                    sb3.append(" callerApp=");
                    sb3.append(serviceRecord.app);
                    sb3.append(" targetSDK=");
                    sb3.append(serviceRecord.app.info.targetSdkVersion);
                    sb3.append(" requires permissions: ");
                    sb3.append(foregroundServiceTypePolicyInfo.toPermissionString());
                    sb3.append(foregroundServiceTypePolicyInfo.hasForegroundOnlyPermission() ? " and the app must be in the eligible state/exemptions to access the foreground only permission" : "");
                    obj = new SecurityException(sb3.toString());
                }
            } else if (i3 == -1 && i == 0) {
                obj = new MissingForegroundServiceTypeException("Starting FGS without a type  callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion);
            } else {
                obj = new InvalidForegroundServiceTypeException("Starting FGS with type " + ServiceInfo.foregroundServiceTypeToLabel(i) + " callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion + " has been prohibited");
            }
            return Pair.create(Integer.valueOf(checkForegroundServiceTypePolicy), obj);
        }
        obj = null;
        return Pair.create(Integer.valueOf(checkForegroundServiceTypePolicy), obj);
    }

    public class SystemExemptedFgsTypePermission extends ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        public SystemExemptedFgsTypePermission() {
            super("System exempted");
        }

        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            AppRestrictionController appRestrictionController = ActiveServices.this.mAm.mAppRestrictionController;
            int potentialSystemExemptionReason = appRestrictionController.getPotentialSystemExemptionReason(i);
            if (potentialSystemExemptionReason == -1 && (potentialSystemExemptionReason = appRestrictionController.getPotentialSystemExemptionReason(i, str)) == -1) {
                potentialSystemExemptionReason = appRestrictionController.getPotentialUserAllowedExemptionReason(i, str);
            }
            if (potentialSystemExemptionReason == -1 && ArrayUtils.contains(ActiveServices.this.mAm.getPackageManagerInternal().getKnownPackageNames(2, 0), str)) {
                potentialSystemExemptionReason = 326;
            }
            if (potentialSystemExemptionReason != 10 && potentialSystemExemptionReason != 11 && potentialSystemExemptionReason != 51 && potentialSystemExemptionReason != 63 && potentialSystemExemptionReason != 65 && potentialSystemExemptionReason != 300 && potentialSystemExemptionReason != 55 && potentialSystemExemptionReason != 56 && potentialSystemExemptionReason != 326 && potentialSystemExemptionReason != 327) {
                switch (potentialSystemExemptionReason) {
                    case FrameworkStatsLog.f109x9a09c896 /* 319 */:
                    case 320:
                    case 321:
                    case 322:
                    case 323:
                    case FrameworkStatsLog.f58x60da79b1 /* 324 */:
                        break;
                    default:
                        return -1;
                }
            }
            return 0;
        }
    }

    public final void initSystemExemptedFgsTypePermission() {
        ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = ForegroundServiceTypePolicy.getDefaultPolicy().getForegroundServiceTypePolicyInfo(1024, 0);
        if (foregroundServiceTypePolicyInfo != null) {
            foregroundServiceTypePolicyInfo.setCustomPermission(new SystemExemptedFgsTypePermission());
        }
    }

    public class MediaProjectionFgsTypeCustomPermission extends ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        public MediaProjectionFgsTypeCustomPermission() {
            super("Media projection screen capture permission");
        }

        public int checkPermission(Context context, int i, int i2, String str, boolean z) {
            return ActiveServices.this.mAm.isAllowedMediaProjectionNoOpCheck(i) ? 0 : -1;
        }
    }

    public final void initMediaProjectFgsTypeCustomPermission() {
        ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = ForegroundServiceTypePolicy.getDefaultPolicy().getForegroundServiceTypePolicyInfo(32, 0);
        if (foregroundServiceTypePolicyInfo != null) {
            foregroundServiceTypePolicyInfo.setCustomPermission(new MediaProjectionFgsTypeCustomPermission());
        }
    }

    public ActivityManagerInternal.ServiceNotificationPolicy applyForegroundServiceNotificationLocked(Notification notification, String str, int i, String str2, int i2) {
        if (str != null) {
            return ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE;
        }
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i2);
        if (serviceMap == null) {
            return ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE;
        }
        for (int i3 = 0; i3 < serviceMap.mServicesByInstanceName.size(); i3++) {
            ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i3);
            if (serviceRecord.isForeground && i == serviceRecord.foregroundId && str2.equals(serviceRecord.appInfo.packageName)) {
                notification.flags |= 64;
                serviceRecord.foregroundNoti = notification;
                if (shouldShowFgsNotificationLocked(serviceRecord)) {
                    serviceRecord.mFgsNotificationDeferred = false;
                    return ActivityManagerInternal.ServiceNotificationPolicy.SHOW_IMMEDIATELY;
                }
                startFgsDeferralTimerLocked(serviceRecord);
                return ActivityManagerInternal.ServiceNotificationPolicy.UPDATE_ONLY;
            }
        }
        return ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE;
    }

    public final boolean shouldShowFgsNotificationLocked(ServiceRecord serviceRecord) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (!this.mAm.mConstants.mFlagFgsNotificationDeferralEnabled) {
            return true;
        }
        if ((serviceRecord.mFgsNotificationDeferred && uptimeMillis >= serviceRecord.fgDisplayTime) || withinFgsDeferRateLimit(serviceRecord, uptimeMillis)) {
            return true;
        }
        if (this.mAm.mConstants.mFlagFgsNotificationDeferralApiGated) {
            if (serviceRecord.appInfo.targetSdkVersion < 31) {
                return true;
            }
        }
        if (serviceRecord.mFgsNotificationShown) {
            return true;
        }
        return !serviceRecord.foregroundNoti.isForegroundDisplayForceDeferred() && (serviceRecord.foregroundNoti.shouldShowForegroundImmediately() || (serviceRecord.foregroundServiceType & 54) != 0);
    }

    public final void startFgsDeferralTimerLocked(ServiceRecord serviceRecord) {
        long uptimeMillis = SystemClock.uptimeMillis();
        int i = serviceRecord.appInfo.uid;
        long j = uptimeMillis + (serviceRecord.isShortFgs() ? this.mAm.mConstants.mFgsNotificationDeferralIntervalForShort : this.mAm.mConstants.mFgsNotificationDeferralInterval);
        for (int i2 = 0; i2 < this.mPendingFgsNotifications.size(); i2++) {
            ServiceRecord serviceRecord2 = (ServiceRecord) this.mPendingFgsNotifications.get(i2);
            if (serviceRecord2 == serviceRecord) {
                return;
            }
            if (i == serviceRecord2.appInfo.uid) {
                j = Math.min(j, serviceRecord2.fgDisplayTime);
            }
        }
        if (this.mFgsDeferralRateLimited) {
            this.mFgsDeferralEligible.put(i, (serviceRecord.isShortFgs() ? this.mAm.mConstants.mFgsNotificationDeferralExclusionTimeForShort : this.mAm.mConstants.mFgsNotificationDeferralExclusionTime) + j);
        }
        serviceRecord.fgDisplayTime = j;
        serviceRecord.mFgsNotificationDeferred = true;
        serviceRecord.mFgsNotificationWasDeferred = true;
        serviceRecord.mFgsNotificationShown = false;
        this.mPendingFgsNotifications.add(serviceRecord);
        if (serviceRecord.appInfo.targetSdkVersion < 31) {
            Slog.i("ActivityManager", "Deferring FGS notification in legacy app " + serviceRecord.appInfo.packageName + "/" + UserHandle.formatUid(serviceRecord.appInfo.uid) + " : " + serviceRecord.foregroundNoti);
        }
        this.mAm.mHandler.postAtTime(this.mPostDeferredFGSNotifications, j);
    }

    public boolean enableFgsNotificationRateLimitLocked(boolean z) {
        if (z != this.mFgsDeferralRateLimited) {
            this.mFgsDeferralRateLimited = z;
            if (!z) {
                this.mFgsDeferralEligible.clear();
            }
        }
        return z;
    }

    public final void removeServiceNotificationDeferralsLocked(String str, int i) {
        for (int size = this.mPendingFgsNotifications.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingFgsNotifications.get(size);
            if (i == serviceRecord.userId && serviceRecord.appInfo.packageName.equals(str)) {
                this.mPendingFgsNotifications.remove(size);
            }
        }
    }

    public void onForegroundServiceNotificationUpdateLocked(boolean z, Notification notification, int i, String str, int i2) {
        int i3;
        int size = this.mPendingFgsNotifications.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingFgsNotifications.get(size);
            if (i2 == serviceRecord.userId && i == serviceRecord.foregroundId && serviceRecord.appInfo.packageName.equals(str) && z) {
                serviceRecord.mFgsNotificationShown = true;
                serviceRecord.mFgsNotificationDeferred = false;
                this.mPendingFgsNotifications.remove(size);
            }
            size--;
        }
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i2);
        if (serviceMap != null) {
            for (i3 = 0; i3 < serviceMap.mServicesByInstanceName.size(); i3++) {
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i3);
                if (serviceRecord2.isForeground && i == serviceRecord2.foregroundId && serviceRecord2.appInfo.packageName.equals(str)) {
                    serviceRecord2.foregroundNoti = notification;
                }
            }
        }
    }

    public final void registerAppOpCallbackLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.app == null) {
            return;
        }
        int i = serviceRecord.appInfo.uid;
        AppOpCallback appOpCallback = (AppOpCallback) this.mFgsAppOpCallbacks.get(i);
        if (appOpCallback == null) {
            appOpCallback = new AppOpCallback(serviceRecord.app, this.mAm.getAppOpsManager());
            this.mFgsAppOpCallbacks.put(i, appOpCallback);
        }
        appOpCallback.registerLocked();
    }

    public final void unregisterAppOpCallbackLocked(ServiceRecord serviceRecord) {
        int i = serviceRecord.appInfo.uid;
        AppOpCallback appOpCallback = (AppOpCallback) this.mFgsAppOpCallbacks.get(i);
        if (appOpCallback != null) {
            appOpCallback.unregisterLocked();
            if (appOpCallback.isObsoleteLocked()) {
                this.mFgsAppOpCallbacks.remove(i);
            }
        }
    }

    public final class AppOpCallback {
        public static final int[] LOGGED_AP_OPS = {0, 1, 27, 26};
        public final AppOpsManager mAppOpsManager;
        public final ProcessRecord mProcessRecord;
        public final SparseIntArray mAcceptedOps = new SparseIntArray();
        public final SparseIntArray mRejectedOps = new SparseIntArray();
        public final Object mCounterLock = new Object();
        public final SparseIntArray mAppOpModes = new SparseIntArray();
        public int mNumFgs = 0;
        public boolean mDestroyed = false;
        public final AppOpsManager.OnOpNotedInternalListener mOpNotedCallback = new AppOpsManager.OnOpNotedInternalListener() { // from class: com.android.server.am.ActiveServices.AppOpCallback.1
            public void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
                AppOpCallback.this.incrementOpCountIfNeeded(i, i2, i4);
            }
        };
        public final AppOpsManager.OnOpStartedListener mOpStartedCallback = new AppOpsManager.OnOpStartedListener() { // from class: com.android.server.am.ActiveServices.AppOpCallback.2
            public void onOpStarted(int i, int i2, String str, String str2, int i3, int i4) {
                AppOpCallback.this.incrementOpCountIfNeeded(i, i2, i4);
            }
        };

        public static int modeToEnum(int i) {
            if (i == 0) {
                return 1;
            }
            if (i != 1) {
                return i != 4 ? 0 : 3;
            }
            return 2;
        }

        public AppOpCallback(ProcessRecord processRecord, AppOpsManager appOpsManager) {
            this.mProcessRecord = processRecord;
            this.mAppOpsManager = appOpsManager;
            for (int i : LOGGED_AP_OPS) {
                this.mAppOpModes.put(i, appOpsManager.unsafeCheckOpRawNoThrow(i, processRecord.uid, processRecord.info.packageName));
            }
        }

        public final void incrementOpCountIfNeeded(int i, int i2, int i3) {
            if (i2 == this.mProcessRecord.uid && isNotTop()) {
                incrementOpCount(i, i3 == 0);
            }
        }

        public final boolean isNotTop() {
            return this.mProcessRecord.mState.getCurProcState() != 2;
        }

        public final void incrementOpCount(int i, boolean z) {
            synchronized (this.mCounterLock) {
                SparseIntArray sparseIntArray = z ? this.mAcceptedOps : this.mRejectedOps;
                int indexOfKey = sparseIntArray.indexOfKey(i);
                if (indexOfKey < 0) {
                    sparseIntArray.put(i, 1);
                } else {
                    sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
                }
            }
        }

        public void registerLocked() {
            if (isObsoleteLocked()) {
                Slog.wtf("ActivityManager", "Trying to register on a stale AppOpCallback.");
                return;
            }
            int i = this.mNumFgs + 1;
            this.mNumFgs = i;
            if (i == 1) {
                AppOpsManager appOpsManager = this.mAppOpsManager;
                int[] iArr = LOGGED_AP_OPS;
                appOpsManager.startWatchingNoted(iArr, this.mOpNotedCallback);
                this.mAppOpsManager.startWatchingStarted(iArr, this.mOpStartedCallback);
            }
        }

        public void unregisterLocked() {
            int i = this.mNumFgs - 1;
            this.mNumFgs = i;
            if (i <= 0) {
                this.mDestroyed = true;
                logFinalValues();
                this.mAppOpsManager.stopWatchingNoted(this.mOpNotedCallback);
                this.mAppOpsManager.stopWatchingStarted(this.mOpStartedCallback);
            }
        }

        public boolean isObsoleteLocked() {
            return this.mDestroyed;
        }

        public final void logFinalValues() {
            synchronized (this.mCounterLock) {
                for (int i : LOGGED_AP_OPS) {
                    int i2 = this.mAcceptedOps.get(i);
                    int i3 = this.mRejectedOps.get(i);
                    if (i2 > 0 || i3 > 0) {
                        FrameworkStatsLog.write(256, this.mProcessRecord.uid, i, modeToEnum(this.mAppOpModes.get(i)), i2, i3);
                    }
                }
            }
        }
    }

    public final void cancelForegroundNotificationLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.foregroundNoti != null) {
            ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
            if (serviceMapLocked != null) {
                for (int size = serviceMapLocked.mServicesByInstanceName.size() - 1; size >= 0; size--) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.valueAt(size);
                    if (serviceRecord2 != serviceRecord && serviceRecord2.isForeground && serviceRecord2.foregroundId == serviceRecord.foregroundId && serviceRecord2.packageName.equals(serviceRecord.packageName)) {
                        return;
                    }
                }
            }
            serviceRecord.cancelNotification();
        }
    }

    public final void updateServiceForegroundLocked(ProcessServiceRecord processServiceRecord, boolean z) {
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            ServiceRecord runningServiceAt = processServiceRecord.getRunningServiceAt(numberOfRunningServices);
            if (runningServiceAt.isForeground || runningServiceAt.fgRequired) {
                int i2 = runningServiceAt.foregroundServiceType;
                int i3 = i | i2;
                if (i2 == 0) {
                    z2 = true;
                    z3 = true;
                } else {
                    z3 = true;
                }
                i = i3;
            }
        }
        this.mAm.updateProcessForegroundLocked(processServiceRecord.mApp, z3, i, z2, z);
        processServiceRecord.setHasReportedForegroundServices(z3);
    }

    public void unscheduleShortFgsTimeoutLocked(ServiceRecord serviceRecord) {
        this.mAm.mHandler.removeMessages(78, serviceRecord);
        this.mAm.mHandler.removeMessages(77, serviceRecord);
        this.mAm.mHandler.removeMessages(76, serviceRecord);
    }

    public final void maybeUpdateShortFgsTrackingLocked(ServiceRecord serviceRecord, boolean z) {
        if (!serviceRecord.isShortFgs()) {
            serviceRecord.clearShortFgsInfo();
            unscheduleShortFgsTimeoutLocked(serviceRecord);
            return;
        }
        boolean hasShortFgsInfo = serviceRecord.hasShortFgsInfo();
        if (z || !hasShortFgsInfo) {
            traceInstant("short FGS start/extend: ", serviceRecord);
            serviceRecord.setShortFgsInfo(SystemClock.uptimeMillis());
            unscheduleShortFgsTimeoutLocked(serviceRecord);
            this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(76, serviceRecord), serviceRecord.getShortFgsInfo().getTimeoutTime());
            return;
        }
        serviceRecord.getShortFgsInfo().update();
    }

    public final void maybeStopShortFgsTimeoutLocked(ServiceRecord serviceRecord) {
        serviceRecord.clearShortFgsInfo();
        if (serviceRecord.isShortFgs()) {
            unscheduleShortFgsTimeoutLocked(serviceRecord);
        }
    }

    public void onShortFgsTimeout(ServiceRecord serviceRecord) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                if (!serviceRecord.shouldTriggerShortFgsTimeout(uptimeMillis)) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.e("ActivityManager", "Short FGS timed out: " + serviceRecord);
                traceInstant("short FGS timeout: ", serviceRecord);
                long j = serviceRecord.mFgsEnterTime;
                logFGSStateChangeLocked(serviceRecord, 5, uptimeMillis > j ? (int) (uptimeMillis - j) : 0, 0, 0);
                try {
                    serviceRecord.app.getThread().scheduleTimeoutService(serviceRecord, serviceRecord.getShortFgsInfo().getStartId());
                } catch (RemoteException e) {
                    Slog.w("ActivityManager", "Exception from scheduleTimeoutService: " + e.toString());
                }
                this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(77, serviceRecord), serviceRecord.getShortFgsInfo().getProcStateDemoteTime());
                this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(78, serviceRecord), serviceRecord.getShortFgsInfo().getAnrTime());
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean shouldServiceTimeOutLocked(ComponentName componentName, IBinder iBinder) {
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            if (findServiceLocked != null) {
                return findServiceLocked.shouldTriggerShortFgsTimeout(SystemClock.uptimeMillis());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onShortFgsProcstateTimeout(ServiceRecord serviceRecord) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (!serviceRecord.shouldDemoteShortFgsProcState(SystemClock.uptimeMillis())) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.e("ActivityManager", "Short FGS procstate demoted: " + serviceRecord);
                traceInstant("short FGS demote: ", serviceRecord);
                this.mAm.updateOomAdjLocked(serviceRecord.app, 13);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void onShortFgsAnrTimeout(ServiceRecord serviceRecord) {
        TimeoutRecord forShortFgsTimeout = TimeoutRecord.forShortFgsTimeout("A foreground service of FOREGROUND_SERVICE_TYPE_SHORT_SERVICE did not stop within a timeout: " + serviceRecord.getComponentName());
        forShortFgsTimeout.mLatencyTracker.waitingOnAMSLockStarted();
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                forShortFgsTimeout.mLatencyTracker.waitingOnAMSLockEnded();
                if (!serviceRecord.shouldTriggerShortFgsAnr(SystemClock.uptimeMillis())) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.e("ActivityManager", "Short FGS ANR'ed: " + serviceRecord);
                traceInstant("short FGS ANR: ", serviceRecord);
                this.mAm.appNotResponding(serviceRecord.app, forShortFgsTimeout);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void updateAllowlistManagerLocked(ProcessServiceRecord processServiceRecord) {
        processServiceRecord.mAllowlistManager = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            if (processServiceRecord.getRunningServiceAt(numberOfRunningServices).allowlistManager) {
                processServiceRecord.mAllowlistManager = true;
                return;
            }
        }
    }

    public final void stopServiceAndUpdateAllowlistManagerLocked(ServiceRecord serviceRecord) {
        maybeStopShortFgsTimeoutLocked(serviceRecord);
        ProcessServiceRecord processServiceRecord = serviceRecord.app.mServices;
        processServiceRecord.stopService(serviceRecord);
        processServiceRecord.updateBoundClientUids();
        if (serviceRecord.allowlistManager) {
            updateAllowlistManagerLocked(processServiceRecord);
        }
    }

    public void updateServiceConnectionActivitiesLocked(ProcessServiceRecord processServiceRecord) {
        ArraySet arraySet = null;
        for (int i = 0; i < processServiceRecord.numberOfConnections(); i++) {
            ProcessRecord processRecord = processServiceRecord.getConnectionAt(i).binding.service.app;
            if (processRecord != null && processRecord != processServiceRecord.mApp) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                } else if (arraySet.contains(processRecord)) {
                }
                arraySet.add(processRecord);
                updateServiceClientActivitiesLocked(processRecord.mServices, null, false);
            }
        }
    }

    public final boolean updateServiceClientActivitiesLocked(ProcessServiceRecord processServiceRecord, ConnectionRecord connectionRecord, boolean z) {
        ProcessRecord processRecord;
        if (connectionRecord != null && (processRecord = connectionRecord.binding.client) != null && !processRecord.hasActivities()) {
            return false;
        }
        boolean z2 = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0 && !z2; numberOfRunningServices--) {
            ArrayMap connections = processServiceRecord.getRunningServiceAt(numberOfRunningServices).getConnections();
            for (int size = connections.size() - 1; size >= 0 && !z2; size--) {
                ArrayList arrayList = (ArrayList) connections.valueAt(size);
                int size2 = arrayList.size() - 1;
                while (true) {
                    if (size2 < 0) {
                        break;
                    }
                    ProcessRecord processRecord2 = ((ConnectionRecord) arrayList.get(size2)).binding.client;
                    if (processRecord2 != null && processRecord2 != processServiceRecord.mApp && processRecord2.hasActivities()) {
                        z2 = true;
                        break;
                    }
                    size2--;
                }
            }
        }
        if (z2 == processServiceRecord.hasClientActivities()) {
            return false;
        }
        processServiceRecord.setHasClientActivities(z2);
        if (z) {
            this.mAm.updateLruProcessLocked(processServiceRecord.mApp, z2, null);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:139:0x041c A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x04c7 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x04da A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x04e8 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x04f2 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x04fe A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0521 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0531 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0546 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0596 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x05e6 A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:203:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x064a A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x06bb A[Catch: all -> 0x06d9, TryCatch #0 {all -> 0x06d9, blocks: (B:125:0x03cd, B:127:0x03e2, B:129:0x03ee, B:130:0x03f4, B:137:0x0413, B:139:0x041c, B:140:0x0425, B:143:0x0433, B:146:0x0444, B:148:0x04c7, B:149:0x04ca, B:151:0x04da, B:152:0x04e0, B:154:0x04e8, B:155:0x04ea, B:157:0x04f2, B:158:0x04f5, B:160:0x04fe, B:161:0x0500, B:163:0x0505, B:165:0x0509, B:167:0x050f, B:169:0x0517, B:170:0x051d, B:172:0x0521, B:173:0x0527, B:175:0x0531, B:176:0x053b, B:178:0x0546, B:180:0x0568, B:184:0x0577, B:186:0x0596, B:188:0x05a0, B:189:0x05a6, B:191:0x05aa, B:192:0x05ac, B:194:0x05b6, B:199:0x05d3, B:201:0x05e6, B:204:0x05f0, B:206:0x0605, B:213:0x0619, B:215:0x064a, B:217:0x0650, B:221:0x0659, B:222:0x069b, B:224:0x06a6, B:226:0x06ac, B:227:0x06be, B:232:0x0662, B:233:0x0657, B:234:0x06b2, B:236:0x06bb, B:240:0x05c0, B:242:0x05ca, B:255:0x0411, B:132:0x03f5, B:134:0x03fb, B:135:0x040d), top: B:124:0x03cd, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x05e1  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x04df  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0442  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0431  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02ce  */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v49 */
    /* JADX WARN: Type inference failed for: r0v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r18v4 */
    /* JADX WARN: Type inference failed for: r18v5, types: [int] */
    /* JADX WARN: Type inference failed for: r18v6 */
    /* JADX WARN: Type inference failed for: r18v7 */
    /* JADX WARN: Type inference failed for: r54v0, types: [com.android.server.am.ActiveServices] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int bindServiceLocked(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, boolean z, int i, String str3, IApplicationThread iApplicationThread2, String str4, int i2) {
        ActivityServiceConnectionsHolder activityServiceConnectionsHolder;
        Intent intent2;
        int i3;
        PendingIntent pendingIntent;
        boolean z2;
        ServiceRecord serviceRecord;
        ProcessRecord processRecord;
        Intent intent3;
        ProcessRecord processRecord2;
        ?? r0;
        ProcessServiceRecord processServiceRecord;
        ProcessRecord processRecord3;
        boolean z3;
        boolean z4;
        ConnectionRecord connectionRecord;
        boolean z5;
        ProcessRecord processRecord4;
        ProcessRecord processRecord5;
        ArrayList arrayList;
        boolean z6;
        boolean z7;
        ProcessRecord processRecord6;
        ProcessRecord processRecord7;
        boolean z8;
        ProcessRecord processRecord8;
        ?? r18;
        int i4;
        IntentBindRecord intentBindRecord;
        boolean z9;
        ProcessStateRecord processStateRecord;
        int i5;
        int i6;
        String str5;
        boolean z10;
        Intent intent4 = intent;
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        ProcessRecord recordForAppLOSP = this.mAm.getRecordForAppLOSP(iApplicationThread);
        if (recordForAppLOSP == null) {
            throw new SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + callingPid + ") when binding service " + intent4);
        }
        if (iBinder != null) {
            ActivityServiceConnectionsHolder serviceConnectionsHolder = this.mAm.mAtmInternal.getServiceConnectionsHolder(iBinder);
            if (serviceConnectionsHolder == null) {
                Slog.w("ActivityManager", "Binding with unknown activity: " + iBinder);
                return 0;
            }
            activityServiceConnectionsHolder = serviceConnectionsHolder;
        } else {
            activityServiceConnectionsHolder = null;
        }
        boolean z11 = recordForAppLOSP.info.uid == 1000;
        if (z11) {
            intent4.setDefusable(true);
            PendingIntent pendingIntent2 = (PendingIntent) intent4.getParcelableExtra("android.intent.extra.client_intent");
            if (pendingIntent2 != null) {
                int intExtra = intent4.getIntExtra("android.intent.extra.client_label", 0);
                if (intExtra != 0) {
                    intent4 = intent.cloneFilter();
                }
                intent2 = intent4;
                pendingIntent = pendingIntent2;
                i3 = intExtra;
            } else {
                intent2 = intent4;
                pendingIntent = pendingIntent2;
                i3 = 0;
            }
        } else {
            intent2 = intent4;
            i3 = 0;
            pendingIntent = null;
        }
        if ((j & 134217728) != 0) {
            this.mAm.enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "BIND_TREAT_LIKE_ACTIVITY");
        }
        if ((j & 524288) != 0 && !z11) {
            throw new SecurityException("Non-system caller (pid=" + callingPid + ") set BIND_SCHEDULE_LIKE_TOP_APP when binding service " + intent2);
        }
        if ((j & 16777216) != 0 && !z11) {
            throw new SecurityException("Non-system caller " + iApplicationThread + " (pid=" + callingPid + ") set BIND_ALLOW_WHITELIST_MANAGEMENT when binding service " + intent2);
        }
        long j2 = j & 4194304;
        if (j2 != 0 && !z11) {
            throw new SecurityException("Non-system caller " + iApplicationThread + " (pid=" + callingPid + ") set BIND_ALLOW_INSTANT when binding service " + intent2);
        }
        if ((j & 65536) != 0 && !z11) {
            throw new SecurityException("Non-system caller (pid=" + callingPid + ") set BIND_ALMOST_PERCEPTIBLE when binding service " + intent2);
        }
        if ((j & 1048576) != 0) {
            this.mAm.enforceCallingPermission("android.permission.START_ACTIVITIES_FROM_BACKGROUND", "BIND_ALLOW_BACKGROUND_ACTIVITY_STARTS");
        }
        if ((j & 262144) != 0) {
            this.mAm.enforceCallingPermission("android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND", "BIND_ALLOW_FOREGROUND_SERVICE_STARTS_FROM_BACKGROUND");
        }
        boolean z12 = recordForAppLOSP.mState.getSetSchedGroup() > 0;
        boolean z13 = ((j & Integer.toUnsignedLong(Integer.MIN_VALUE)) == 0 && (j & 4611686018427387904L) == 0) ? false : true;
        boolean z14 = j2 != 0;
        boolean z15 = (j & 8192) != 0;
        ProcessRecord recordForAppLOSP2 = i > 0 ? this.mAm.getRecordForAppLOSP(iApplicationThread2) : null;
        boolean z16 = z12;
        Intent intent5 = intent2;
        ActivityServiceConnectionsHolder activityServiceConnectionsHolder2 = activityServiceConnectionsHolder;
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent2, str2, z, i, str3, str, str4, callingPid, callingUid, i2, true, z16, z13, z14, null, z15);
        if (retrieveServiceLocked == null) {
            return 0;
        }
        ServiceRecord serviceRecord2 = retrieveServiceLocked.record;
        int i7 = -1;
        if (serviceRecord2 == null) {
            return -1;
        }
        if (this.delayServiceEnable) {
            ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord2.userId);
            if (serviceRecord2.delayService) {
                int i8 = serviceRecord2.delayCount;
                if (i8 < 3) {
                    serviceRecord2.delayCount = i8 + 1;
                    return 0;
                }
                z10 = true;
                serviceRecord2.delayService = false;
                serviceMapLocked.mDelayServiceList.remove(serviceRecord2);
            } else {
                z10 = true;
                if (serviceRecord2.delayServiceStop) {
                    serviceRecord2.delayServiceStop = false;
                } else {
                    if (this.mAm.getAppLaunchFlag() && shouldDelay(serviceRecord2)) {
                        serviceRecord2.pendingBinds.add(new ServiceRecord.BindItem(iApplicationThread, iBinder, intent5, str, iServiceConnection, j, str2, z, i, str3, iApplicationThread2, str4, i2));
                        serviceRecord2.delayService = true;
                        serviceRecord2.serviceDelayed = true;
                        serviceRecord2.delayTimeout = SystemClock.uptimeMillis() + 500;
                        serviceRecord2.delayCount = 1;
                        serviceMapLocked.mDelayServiceList.add(serviceRecord2);
                        serviceMapLocked.sendMessageDelayed(serviceMapLocked.obtainMessage(101), 50L);
                        return 0;
                    }
                    z2 = false;
                    serviceRecord = serviceRecord2;
                }
            }
            r0 = 0;
            processRecord = recordForAppLOSP2;
            processRecord2 = recordForAppLOSP;
            serviceRecord = serviceRecord2;
            intent3 = intent5;
            AppBindRecord retrieveAppBindingLocked = serviceRecord.retrieveAppBindingLocked(intent3, processRecord2, processRecord);
            processServiceRecord = retrieveAppBindingLocked.client.mServices;
            if (processServiceRecord.numberOfConnections() < this.mAm.mConstants.mMaxServiceConnectionsPerProcess) {
                Slog.w("ActivityManager", "bindService exceeded max service connection number per process, callerApp:" + processRecord2.processName + " intent:" + intent3);
                return r0;
            }
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord3 = this.mAm.mPidsSelfLocked.get(callingPid);
            }
            String str6 = processRecord3 != null ? processRecord3.processName : str4;
            if (processRecord3 != null && processRecord3.getThread() != null && !processRecord3.isKilled()) {
                i7 = processRecord3.mState.getCurProcState();
            }
            int i9 = i7;
            serviceRecord.updateProcessStateOnRequest();
            if (MARsPolicyManager.MARs_ENABLE) {
                int userId = this.mAm.mContext.getUserId();
                if (processRecord2.info != null) {
                    str5 = processRecord2.info.packageName;
                    i6 = processRecord2.userId;
                    i5 = processRecord2.getPid();
                } else {
                    i5 = r0;
                    i6 = userId;
                    str5 = null;
                }
                HostingRecord hostingRecord = processRecord2.getHostingRecord();
                String stringForTracker = hostingRecord != null ? hostingRecord.toStringForTracker() : null;
                String str7 = getHostingRecordTriggerType(serviceRecord) == "job" ? "job" : "bindService";
                MARsPolicyManager.getInstance().onSpecialBindServiceActions(serviceRecord.packageName, intent3.getAction(), serviceRecord.userId, str5);
                BaseRestrictionMgr baseRestrictionMgr = BaseRestrictionMgr.getInstance();
                ComponentName componentName = serviceRecord.name;
                int i10 = serviceRecord.userId;
                ProcessRecord processRecord9 = serviceRecord.app;
                if (baseRestrictionMgr.isRestrictedPackage(componentName, str5, i6, str7, intent3, i10, stringForTracker, i5, processRecord9 != null ? processRecord9.getPid() : i10)) {
                    return r0;
                }
            }
            Intent intent6 = intent3;
            ProcessRecord processRecord10 = processRecord2;
            ServiceRecord serviceRecord3 = serviceRecord;
            boolean deferServiceBringupIfFrozenLocked = deferServiceBringupIfFrozenLocked(serviceRecord, intent3, str4, null, callingUid, callingPid, str6, i9, false, z16, i2, BackgroundStartPrivileges.NONE, true, iServiceConnection);
            boolean z17 = (deferServiceBringupIfFrozenLocked || requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord3, str4, null, callingUid, intent6, z16, i2, true, iServiceConnection)) ? r0 : true;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                unscheduleServiceRestartLocked(serviceRecord3, processRecord10.info.uid, r0, "bind service");
                if ((j & 1) != 0) {
                    serviceRecord3.lastActivity = SystemClock.uptimeMillis();
                    if (!serviceRecord3.hasAutoCreateConnections()) {
                        synchronized (this.mAm.mProcessStats.mLock) {
                            ServiceState tracker = serviceRecord3.getTracker();
                            if (tracker != null) {
                                z3 = true;
                                tracker.setBound(true, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                            } else {
                                z3 = true;
                            }
                        }
                        if ((j & 2097152) != 0) {
                            this.mAm.requireAllowedAssociationsLocked(serviceRecord3.appInfo.packageName);
                        }
                        boolean z18 = (serviceRecord3.appInfo.flags & 2097152) == 0 ? z3 : r0;
                        z4 = serviceRecord3.startRequested;
                        boolean z19 = serviceRecord3.getConnections().isEmpty() ? z3 : r0;
                        ActivityManagerService activityManagerService = this.mAm;
                        int i11 = processRecord10.uid;
                        String str8 = processRecord10.processName;
                        int curProcState = processRecord10.mState.getCurProcState();
                        ApplicationInfo applicationInfo = serviceRecord3.appInfo;
                        activityManagerService.startAssociationLocked(i11, str8, curProcState, applicationInfo.uid, applicationInfo.longVersionCode, serviceRecord3.instanceName, serviceRecord3.processName);
                        this.mAm.grantImplicitAccess(processRecord10.userId, intent6, processRecord10.uid, UserHandle.getAppId(serviceRecord3.appInfo.uid));
                        connectionRecord = new ConnectionRecord(retrieveAppBindingLocked, activityServiceConnectionsHolder2, iServiceConnection, j, i3, pendingIntent, processRecord10.uid, processRecord10.processName, str4, retrieveServiceLocked.aliasComponent);
                        IBinder asBinder = iServiceConnection.asBinder();
                        serviceRecord3.addConnection(asBinder, connectionRecord);
                        retrieveAppBindingLocked.connections.add(connectionRecord);
                        if (activityServiceConnectionsHolder2 != null) {
                            activityServiceConnectionsHolder2.addConnection(connectionRecord);
                        }
                        processServiceRecord.addConnection(connectionRecord);
                        connectionRecord.startAssociationIfNeeded();
                        if (connectionRecord.hasFlag(8)) {
                            z5 = true;
                        } else {
                            z5 = true;
                            processServiceRecord.setHasAboveClient(true);
                        }
                        if (connectionRecord.hasFlag(16777216)) {
                            serviceRecord3.allowlistManager = z5;
                        }
                        if (connectionRecord.hasFlag(1048576)) {
                            serviceRecord3.setAllowedBgActivityStartsByBinding(z5);
                        }
                        if (connectionRecord.hasFlag(32768)) {
                            serviceRecord3.isNotAppComponentUsage = z5;
                        }
                        processRecord4 = serviceRecord3.app;
                        if (processRecord4 != null && (processStateRecord = processRecord4.mState) != null && processStateRecord.getCurProcState() <= 2 && connectionRecord.hasFlag(65536)) {
                            serviceRecord3.lastTopAlmostPerceptibleBindRequestUptimeMs = SystemClock.uptimeMillis();
                        }
                        processRecord5 = serviceRecord3.app;
                        if (processRecord5 != null) {
                            updateServiceClientActivitiesLocked(processRecord5.mServices, connectionRecord, true);
                        }
                        arrayList = (ArrayList) this.mServiceConnections.get(asBinder);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            this.mServiceConnections.put(asBinder, arrayList);
                        }
                        arrayList.add(connectionRecord);
                        if (connectionRecord.hasFlag(1)) {
                            z6 = false;
                            z7 = false;
                        } else {
                            serviceRecord3.lastActivity = SystemClock.uptimeMillis();
                            if (bringUpServiceLocked(serviceRecord3, intent6.getFlags(), z16, false, z17, deferServiceBringupIfFrozenLocked, true) != null) {
                                this.mAm.updateOomAdjPendingTargetsLocked(4);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return 0;
                            }
                            z6 = false;
                            z7 = true;
                        }
                        setFgsRestrictionLocked(str4, callingPid, callingUid, intent6, serviceRecord3, i2, BackgroundStartPrivileges.NONE, true, false);
                        processRecord6 = serviceRecord3.app;
                        if (processRecord6 == null) {
                            ProcessServiceRecord processServiceRecord2 = processRecord6.mServices;
                            if (connectionRecord.hasFlag(134217728)) {
                                z8 = true;
                                processServiceRecord2.setTreatLikeActivity(true);
                            } else {
                                z8 = true;
                            }
                            if (serviceRecord3.allowlistManager) {
                                processServiceRecord2.mAllowlistManager = z8;
                            }
                            ActivityManagerService activityManagerService2 = this.mAm;
                            ProcessRecord processRecord11 = serviceRecord3.app;
                            if (processRecord10.hasActivitiesOrRecentTasks() && processServiceRecord2.hasClientActivities()) {
                                processRecord7 = processRecord10;
                                z9 = z8;
                                activityManagerService2.updateLruProcessLocked(processRecord11, z9, retrieveAppBindingLocked.client);
                                this.mAm.enqueueOomAdjTargetLocked(serviceRecord3.app);
                                z7 = z8;
                            }
                            processRecord7 = processRecord10;
                            if (processRecord7.mState.getCurProcState() > 2 || !connectionRecord.hasFlag(134217728)) {
                                z9 = z6;
                                activityManagerService2.updateLruProcessLocked(processRecord11, z9, retrieveAppBindingLocked.client);
                                this.mAm.enqueueOomAdjTargetLocked(serviceRecord3.app);
                                z7 = z8;
                            }
                            z9 = z8;
                            activityManagerService2.updateLruProcessLocked(processRecord11, z9, retrieveAppBindingLocked.client);
                            this.mAm.enqueueOomAdjTargetLocked(serviceRecord3.app);
                            z7 = z8;
                        } else {
                            processRecord7 = processRecord10;
                            z8 = true;
                        }
                        if (z7) {
                            this.mAm.updateOomAdjPendingTargetsLocked(4);
                        }
                        boolean z20 = !z18 ? 2 : z8;
                        int i12 = serviceRecord3.appInfo.uid;
                        String shortAction = ActivityManagerService.getShortAction(intent6.getAction());
                        processRecord8 = serviceRecord3.app;
                        if (processRecord8 != null && processRecord8.getThread() != null) {
                            if (!z4 && !z19) {
                                r18 = z8;
                                FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i12, callingUid, shortAction, 2, false, (int) r18, getShortProcessNameForStats(callingUid, processRecord7.processName), getShortServiceNameForStats(serviceRecord3), z20 ? 1 : 0, serviceRecord3.packageName, processRecord7.info.packageName, processRecord7.mState.getCurProcState(), serviceRecord3.mProcessStateOnRequest);
                                if (serviceRecord3.app != null) {
                                    IntentBindRecord intentBindRecord2 = retrieveAppBindingLocked.intent;
                                    if (intentBindRecord2.received) {
                                        ComponentName componentName2 = retrieveServiceLocked.aliasComponent;
                                        if (componentName2 == null) {
                                            componentName2 = serviceRecord3.name;
                                        }
                                        try {
                                            connectionRecord.conn.connected(componentName2, intentBindRecord2.binder, z6);
                                        } catch (Exception e) {
                                            Slog.w("ActivityManager", "Failure sending service " + serviceRecord3.shortInstanceName + " to connection " + connectionRecord.conn.asBinder() + " (in " + connectionRecord.binding.client.processName + ")", e);
                                        }
                                        i4 = 1;
                                        if (retrieveAppBindingLocked.intent.apps.size() == 1) {
                                            IntentBindRecord intentBindRecord3 = retrieveAppBindingLocked.intent;
                                            if (intentBindRecord3.doRebind) {
                                                requestServiceBindingLocked(serviceRecord3, intentBindRecord3, z16, true);
                                            }
                                        }
                                        maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                                        getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        notifyBindingServiceEventLocked(processRecord7, str4);
                                        return i4;
                                    }
                                }
                                i4 = 1;
                                intentBindRecord = retrieveAppBindingLocked.intent;
                                if (!intentBindRecord.requested) {
                                    requestServiceBindingLocked(serviceRecord3, intentBindRecord, z16, z6);
                                }
                                maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                                getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                notifyBindingServiceEventLocked(processRecord7, str4);
                                return i4;
                            }
                            r18 = 2;
                            FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i12, callingUid, shortAction, 2, false, (int) r18, getShortProcessNameForStats(callingUid, processRecord7.processName), getShortServiceNameForStats(serviceRecord3), z20 ? 1 : 0, serviceRecord3.packageName, processRecord7.info.packageName, processRecord7.mState.getCurProcState(), serviceRecord3.mProcessStateOnRequest);
                            if (serviceRecord3.app != null) {
                            }
                            i4 = 1;
                            intentBindRecord = retrieveAppBindingLocked.intent;
                            if (!intentBindRecord.requested) {
                            }
                            maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                            getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            notifyBindingServiceEventLocked(processRecord7, str4);
                            return i4;
                        }
                        r18 = 3;
                        FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i12, callingUid, shortAction, 2, false, (int) r18, getShortProcessNameForStats(callingUid, processRecord7.processName), getShortServiceNameForStats(serviceRecord3), z20 ? 1 : 0, serviceRecord3.packageName, processRecord7.info.packageName, processRecord7.mState.getCurProcState(), serviceRecord3.mProcessStateOnRequest);
                        if (serviceRecord3.app != null) {
                        }
                        i4 = 1;
                        intentBindRecord = retrieveAppBindingLocked.intent;
                        if (!intentBindRecord.requested) {
                        }
                        maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                        getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        notifyBindingServiceEventLocked(processRecord7, str4);
                        return i4;
                    }
                }
                z3 = true;
                if ((j & 2097152) != 0) {
                }
                if ((serviceRecord3.appInfo.flags & 2097152) == 0) {
                }
                z4 = serviceRecord3.startRequested;
                if (serviceRecord3.getConnections().isEmpty()) {
                }
                ActivityManagerService activityManagerService3 = this.mAm;
                int i112 = processRecord10.uid;
                String str82 = processRecord10.processName;
                int curProcState2 = processRecord10.mState.getCurProcState();
                ApplicationInfo applicationInfo2 = serviceRecord3.appInfo;
                activityManagerService3.startAssociationLocked(i112, str82, curProcState2, applicationInfo2.uid, applicationInfo2.longVersionCode, serviceRecord3.instanceName, serviceRecord3.processName);
                this.mAm.grantImplicitAccess(processRecord10.userId, intent6, processRecord10.uid, UserHandle.getAppId(serviceRecord3.appInfo.uid));
                connectionRecord = new ConnectionRecord(retrieveAppBindingLocked, activityServiceConnectionsHolder2, iServiceConnection, j, i3, pendingIntent, processRecord10.uid, processRecord10.processName, str4, retrieveServiceLocked.aliasComponent);
                IBinder asBinder2 = iServiceConnection.asBinder();
                serviceRecord3.addConnection(asBinder2, connectionRecord);
                retrieveAppBindingLocked.connections.add(connectionRecord);
                if (activityServiceConnectionsHolder2 != null) {
                }
                processServiceRecord.addConnection(connectionRecord);
                connectionRecord.startAssociationIfNeeded();
                if (connectionRecord.hasFlag(8)) {
                }
                if (connectionRecord.hasFlag(16777216)) {
                }
                if (connectionRecord.hasFlag(1048576)) {
                }
                if (connectionRecord.hasFlag(32768)) {
                }
                processRecord4 = serviceRecord3.app;
                if (processRecord4 != null) {
                    serviceRecord3.lastTopAlmostPerceptibleBindRequestUptimeMs = SystemClock.uptimeMillis();
                }
                processRecord5 = serviceRecord3.app;
                if (processRecord5 != null) {
                }
                arrayList = (ArrayList) this.mServiceConnections.get(asBinder2);
                if (arrayList == null) {
                }
                arrayList.add(connectionRecord);
                if (connectionRecord.hasFlag(1)) {
                }
                setFgsRestrictionLocked(str4, callingPid, callingUid, intent6, serviceRecord3, i2, BackgroundStartPrivileges.NONE, true, false);
                processRecord6 = serviceRecord3.app;
                if (processRecord6 == null) {
                }
                if (z7) {
                }
                if (!z18) {
                }
                int i122 = serviceRecord3.appInfo.uid;
                String shortAction2 = ActivityManagerService.getShortAction(intent6.getAction());
                processRecord8 = serviceRecord3.app;
                if (processRecord8 != null) {
                    if (!z4) {
                        r18 = z8;
                        FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i122, callingUid, shortAction2, 2, false, (int) r18, getShortProcessNameForStats(callingUid, processRecord7.processName), getShortServiceNameForStats(serviceRecord3), z20 ? 1 : 0, serviceRecord3.packageName, processRecord7.info.packageName, processRecord7.mState.getCurProcState(), serviceRecord3.mProcessStateOnRequest);
                        if (serviceRecord3.app != null) {
                        }
                        i4 = 1;
                        intentBindRecord = retrieveAppBindingLocked.intent;
                        if (!intentBindRecord.requested) {
                        }
                        maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                        getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        notifyBindingServiceEventLocked(processRecord7, str4);
                        return i4;
                    }
                    r18 = 2;
                    FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i122, callingUid, shortAction2, 2, false, (int) r18, getShortProcessNameForStats(callingUid, processRecord7.processName), getShortServiceNameForStats(serviceRecord3), z20 ? 1 : 0, serviceRecord3.packageName, processRecord7.info.packageName, processRecord7.mState.getCurProcState(), serviceRecord3.mProcessStateOnRequest);
                    if (serviceRecord3.app != null) {
                    }
                    i4 = 1;
                    intentBindRecord = retrieveAppBindingLocked.intent;
                    if (!intentBindRecord.requested) {
                    }
                    maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                    getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    notifyBindingServiceEventLocked(processRecord7, str4);
                    return i4;
                }
                r18 = 3;
                FrameworkStatsLog.write(FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i122, callingUid, shortAction2, 2, false, (int) r18, getShortProcessNameForStats(callingUid, processRecord7.processName), getShortServiceNameForStats(serviceRecord3), z20 ? 1 : 0, serviceRecord3.packageName, processRecord7.info.packageName, processRecord7.mState.getCurProcState(), serviceRecord3.mProcessStateOnRequest);
                if (serviceRecord3.app != null) {
                }
                i4 = 1;
                intentBindRecord = retrieveAppBindingLocked.intent;
                if (!intentBindRecord.requested) {
                }
                maybeLogBindCrossProfileService(i2, str4, processRecord7.info.uid);
                getServiceMapLocked(serviceRecord3.userId).ensureNotStartingBackgroundLocked(serviceRecord3);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                notifyBindingServiceEventLocked(processRecord7, str4);
                return i4;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        z2 = false;
        serviceRecord = serviceRecord2;
        processRecord = recordForAppLOSP2;
        intent3 = intent5;
        processRecord2 = recordForAppLOSP;
        r0 = z2;
        AppBindRecord retrieveAppBindingLocked2 = serviceRecord.retrieveAppBindingLocked(intent3, processRecord2, processRecord);
        processServiceRecord = retrieveAppBindingLocked2.client.mServices;
        if (processServiceRecord.numberOfConnections() < this.mAm.mConstants.mMaxServiceConnectionsPerProcess) {
        }
    }

    public final void notifyBindingServiceEventLocked(ProcessRecord processRecord, String str) {
        ApplicationInfo applicationInfo = processRecord.info;
        if (applicationInfo != null) {
            str = applicationInfo.packageName;
        }
        if (str != null) {
            this.mAm.mHandler.obtainMessage(75, processRecord.uid, 0, str).sendToTarget();
        }
    }

    public final void maybeLogBindCrossProfileService(int i, String str, int i2) {
        int userId;
        if (UserHandle.isCore(i2) || (userId = UserHandle.getUserId(i2)) == i || !this.mAm.mUserController.isSameProfileGroup(userId, i)) {
            return;
        }
        DevicePolicyEventLogger.createEvent(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE).setStrings(new String[]{str}).write();
    }

    public void publishServiceLocked(ServiceRecord serviceRecord, Intent intent, IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (serviceRecord != null) {
            try {
                Intent.FilterComparison filterComparison = new Intent.FilterComparison(intent);
                IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.get(filterComparison);
                if (intentBindRecord != null && !intentBindRecord.received) {
                    intentBindRecord.binder = iBinder;
                    intentBindRecord.requested = true;
                    intentBindRecord.received = true;
                    ArrayMap connections = serviceRecord.getConnections();
                    for (int size = connections.size() - 1; size >= 0; size--) {
                        ArrayList arrayList = (ArrayList) connections.valueAt(size);
                        for (int i = 0; i < arrayList.size(); i++) {
                            ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i);
                            if (filterComparison.equals(connectionRecord.binding.intent.intent)) {
                                ComponentName componentName = connectionRecord.aliasComponent;
                                if (componentName == null) {
                                    componentName = serviceRecord.name;
                                }
                                try {
                                    connectionRecord.conn.connected(componentName, iBinder, false);
                                } catch (Exception e) {
                                    Slog.w("ActivityManager", "Failure sending service " + serviceRecord.shortInstanceName + " to connection " + connectionRecord.conn.asBinder() + " (in " + connectionRecord.binding.client.processName + ")", e);
                                }
                            }
                        }
                    }
                }
                serviceDoneExecutingLocked(serviceRecord, this.mDestroyingServices.contains(serviceRecord), false, false, 20);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void updateServiceGroupLocked(IServiceConnection iServiceConnection, int i, int i2) {
        ArrayList arrayList = (ArrayList) this.mServiceConnections.get(iServiceConnection.asBinder());
        if (arrayList == null) {
            throw new IllegalArgumentException("Could not find connection for " + iServiceConnection.asBinder());
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = ((ConnectionRecord) arrayList.get(size)).binding.service;
            if (serviceRecord != null && (serviceRecord.serviceInfo.flags & 2) != 0) {
                ProcessRecord processRecord = serviceRecord.app;
                if (processRecord != null) {
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (i > 0) {
                        processServiceRecord.setConnectionService(serviceRecord);
                        processServiceRecord.setConnectionGroup(i);
                        processServiceRecord.setConnectionImportance(i2);
                    } else {
                        processServiceRecord.setConnectionService(null);
                        processServiceRecord.setConnectionGroup(0);
                        processServiceRecord.setConnectionImportance(0);
                    }
                } else if (i > 0) {
                    serviceRecord.pendingConnectionGroup = i;
                    serviceRecord.pendingConnectionImportance = i2;
                } else {
                    serviceRecord.pendingConnectionGroup = 0;
                    serviceRecord.pendingConnectionImportance = 0;
                }
            }
        }
    }

    public boolean unbindServiceLocked(IServiceConnection iServiceConnection) {
        long j;
        IntentBindRecord intentBindRecord;
        Intent.FilterComparison filterComparison;
        String num;
        IBinder asBinder = iServiceConnection.asBinder();
        ArrayList arrayList = (ArrayList) this.mServiceConnections.get(asBinder);
        if (arrayList == null) {
            Slog.w("ActivityManager", "Unbind failed: could not find connection for " + iServiceConnection.asBinder());
            return false;
        }
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        long j2 = 64;
        try {
            if (Trace.isTagEnabled(64L)) {
                if (arrayList.size() > 0) {
                    ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(0);
                    num = connectionRecord.binding.service.shortInstanceName + " from " + connectionRecord.clientProcessName;
                } else {
                    num = Integer.toString(callingPid);
                }
                Trace.traceBegin(64L, "unbindServiceLocked: " + num);
            }
            while (arrayList.size() > 0) {
                ConnectionRecord connectionRecord2 = (ConnectionRecord) arrayList.get(0);
                removeConnectionLocked(connectionRecord2, null, null, true);
                if (arrayList.size() > 0 && arrayList.get(0) == connectionRecord2) {
                    Slog.wtf("ActivityManager", "Connection " + connectionRecord2 + " not removed for binder " + asBinder);
                    arrayList.remove(0);
                }
                ProcessRecord processRecord = connectionRecord2.binding.service.app;
                if (processRecord != null) {
                    if (FreecessController.getInstance().getFreecessEnabled()) {
                        try {
                            FreecessController.getInstance().protectFreezePackage(processRecord.uid, "unbindService", 2000L);
                            FreecessController freecessController = FreecessController.getInstance();
                            ServiceRecord serviceRecord = connectionRecord2.binding.service;
                            freecessController.protectFreezePackage(serviceRecord.packageName, serviceRecord.userId, "unbindService", 2000L);
                        } catch (Throwable th) {
                            th = th;
                            j = 64;
                            Trace.traceEnd(j);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    if (MARsPolicyManager.MARs_ENABLE && (intentBindRecord = connectionRecord2.binding.intent) != null && (filterComparison = intentBindRecord.intent) != null && filterComparison.getIntent() != null) {
                        ProcessRecord processRecord2 = connectionRecord2.binding.client;
                        String str = (processRecord2 == null || processRecord2.info == null) ? null : connectionRecord2.binding.client.info.packageName;
                        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.getInstance();
                        AppBindRecord appBindRecord = connectionRecord2.binding;
                        mARsPolicyManager.onSpecialUnBindServiceActions(appBindRecord.service.packageName, appBindRecord.intent.intent.getIntent().getAction(), connectionRecord2.binding.service.userId, str);
                    }
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processServiceRecord.mAllowlistManager) {
                        updateAllowlistManagerLocked(processServiceRecord);
                    }
                    if (connectionRecord2.hasFlag(134217728)) {
                        processServiceRecord.setTreatLikeActivity(true);
                        this.mAm.updateLruProcessLocked(processRecord, true, null);
                    }
                    this.mAm.enqueueOomAdjTargetLocked(processRecord);
                }
                j2 = 64;
            }
            this.mAm.updateOomAdjPendingTargetsLocked(5);
            Trace.traceEnd(64L);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th2) {
            th = th2;
            j = j2;
        }
    }

    public void unbindFinishedLocked(ServiceRecord serviceRecord, Intent intent, boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (serviceRecord != null) {
            try {
                IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.get(new Intent.FilterComparison(intent));
                boolean contains = this.mDestroyingServices.contains(serviceRecord);
                if (intentBindRecord != null) {
                    if (intentBindRecord.apps.size() > 0 && !contains) {
                        int size = intentBindRecord.apps.size() - 1;
                        while (true) {
                            if (size >= 0) {
                                ProcessRecord processRecord = ((AppBindRecord) intentBindRecord.apps.valueAt(size)).client;
                                if (processRecord != null && processRecord.mState.getSetSchedGroup() > 0) {
                                    z2 = true;
                                    break;
                                }
                                size--;
                            } else {
                                z2 = false;
                                break;
                            }
                        }
                        try {
                            requestServiceBindingLocked(serviceRecord, intentBindRecord, z2, true);
                        } catch (TransactionTooLargeException unused) {
                        }
                    } else {
                        intentBindRecord.doRebind = true;
                    }
                }
                serviceDoneExecutingLocked(serviceRecord, contains, false, false, 5);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final ServiceRecord findServiceLocked(ComponentName componentName, IBinder iBinder, int i) {
        ServiceRecord serviceByNameLocked = getServiceByNameLocked(componentName, i);
        if (serviceByNameLocked == iBinder) {
            return serviceByNameLocked;
        }
        return null;
    }

    public final class ServiceLookupResult {
        public final ComponentName aliasComponent;
        public final String permission;
        public final ServiceRecord record;

        public ServiceLookupResult(ServiceRecord serviceRecord, ComponentName componentName) {
            this.record = serviceRecord;
            this.permission = null;
            this.aliasComponent = componentName;
        }

        public ServiceLookupResult(String str) {
            this.record = null;
            this.permission = str;
            this.aliasComponent = null;
        }
    }

    public class ServiceRestarter implements Runnable {
        public ServiceRecord mService;

        public ServiceRestarter() {
        }

        public void setService(ServiceRecord serviceRecord) {
            this.mService = serviceRecord;
        }

        @Override // java.lang.Runnable
        public void run() {
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    ActiveServices.this.performServiceRestartLocked(this.mService);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final ServiceLookupResult retrieveServiceLocked(Intent intent, String str, String str2, String str3, int i, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return retrieveServiceLocked(intent, str, false, -1, null, str2, str3, i, i2, i3, z, z2, z3, z4, null, z5);
    }

    public final String generateAdditionalSeInfoFromService(Intent intent) {
        return (intent == null || intent.getAction() == null) ? "" : (intent.getAction().equals("android.service.voice.HotwordDetectionService") || intent.getAction().equals("android.service.voice.VisualQueryDetectionService") || intent.getAction().equals("android.service.wearable.WearableSensingService")) ? ":isolatedComputeApp" : "";
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x029e, code lost:
    
        if ((r4.flags & 2) != 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x02a8, code lost:
    
        throw new java.lang.IllegalArgumentException("Service cannot be both sdk sandbox and isolated");
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x07e2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x020f  */
    /* JADX WARN: Type inference failed for: r0v44, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v51, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v61, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r10v1, types: [int] */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v2, types: [int] */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v10 */
    /* JADX WARN: Type inference failed for: r17v12 */
    /* JADX WARN: Type inference failed for: r17v14 */
    /* JADX WARN: Type inference failed for: r17v15 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v20 */
    /* JADX WARN: Type inference failed for: r17v22 */
    /* JADX WARN: Type inference failed for: r17v23 */
    /* JADX WARN: Type inference failed for: r17v24 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.am.ComponentAliasResolver] */
    /* JADX WARN: Type inference failed for: r2v26, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v35, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v68, types: [android.content.pm.PackageManagerInternal] */
    /* JADX WARN: Type inference failed for: r49v0, types: [com.android.server.am.ActiveServices] */
    /* JADX WARN: Type inference failed for: r6v20, types: [android.content.pm.PackageManagerInternal] */
    /* JADX WARN: Type inference failed for: r7v19, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ServiceLookupResult retrieveServiceLocked(Intent intent, String str, boolean z, int i, String str2, String str3, String str4, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, boolean z5, ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, boolean z6) {
        ComponentName componentName;
        ComponentName componentName2;
        ServiceRecord serviceRecord;
        ActiveServices activeServices;
        String str5;
        String str6;
        String str7;
        String str8;
        ServiceRecord serviceRecord2;
        int permissionToOpCode;
        long j;
        ServiceInfo serviceInfo;
        String packageName;
        ?? validateAssociationAllowedLocked;
        Object obj;
        ?? r7;
        int i5;
        Object obj2;
        String str9;
        int i6;
        String str10;
        ComponentName componentName3;
        ComponentName componentName4;
        String str11;
        ServiceInfo serviceInfo2;
        ServiceMap serviceMap;
        ServiceRecord serviceRecord3;
        Object obj3;
        int i7;
        Object obj4;
        ApplicationInfo applicationInfo;
        ServiceRecord serviceRecord4;
        String str12;
        if (z && str == null) {
            throw new IllegalArgumentException("No instanceName provided for sdk sandbox process");
        }
        int handleIncomingUser = this.mAm.mUserController.handleIncomingUser(i2, i3, i4, false, getAllowMode(intent, str4), "service", str4);
        ServiceMap serviceMapLocked = getServiceMapLocked(handleIncomingUser);
        ComponentAliasResolver.Resolution resolveService = this.mAm.mComponentAliasResolver.resolveService(intent, str3, 0, handleIncomingUser, i3);
        if (str == null) {
            componentName = intent.getComponent();
        } else {
            ComponentName component = intent.getComponent();
            if (component == null) {
                throw new IllegalArgumentException("Can't use custom instance name '" + str + "' without expicit component in Intent");
            }
            componentName = new ComponentName(component.getPackageName(), component.getClassName() + XmlUtils.STRING_ARRAY_SEPARATOR + str);
        }
        if (SemDualAppManager.isDualAppId(UserHandle.getUserId(i3))) {
            if (componentName != null) {
                str12 = componentName.getPackageName();
            } else {
                str12 = intent.getPackage();
            }
            if (str4 != null && !str4.equals(str12) && DualAppManagerService.shouldForwardToOwner(str12)) {
                serviceMapLocked = getServiceMapLocked(0);
                handleIncomingUser = 0;
            }
        }
        ServiceRecord serviceRecord5 = componentName != null ? (ServiceRecord) serviceMapLocked.mServicesByInstanceName.get(componentName) : null;
        if (serviceRecord5 == null && !z4 && str == null) {
            serviceRecord5 = (ServiceRecord) serviceMapLocked.mServicesByIntent.get(new Intent.FilterComparison(intent));
        }
        if (serviceRecord5 != null) {
            componentName2 = componentName;
            if (this.mAm.getPackageManagerInternal().filterAppAccess(serviceRecord5.packageName, i3, handleIncomingUser)) {
                Slog.w("ActivityManager", "Unable to start service " + intent + " U=" + handleIncomingUser + ": not found");
                return null;
            }
            if ((serviceRecord5.serviceInfo.flags & 4) != 0 && !str4.equals(serviceRecord5.packageName)) {
                serviceRecord = null;
                if (serviceRecord != null && foregroundServiceDelegationOptions != null) {
                    ServiceInfo serviceInfo3 = new ServiceInfo();
                    try {
                        applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(foregroundServiceDelegationOptions.mClientPackageName, 1024L, handleIncomingUser);
                    } catch (RemoteException unused) {
                        applicationInfo = null;
                    }
                    if (applicationInfo == null) {
                        throw new SecurityException("startForegroundServiceDelegate failed, could not resolve client package " + str4);
                    }
                    if (applicationInfo.uid != foregroundServiceDelegationOptions.mClientUid) {
                        throw new SecurityException("startForegroundServiceDelegate failed, uid:" + applicationInfo.uid + " does not match clientUid:" + foregroundServiceDelegationOptions.mClientUid);
                    }
                    serviceInfo3.applicationInfo = applicationInfo;
                    serviceInfo3.packageName = applicationInfo.packageName;
                    serviceInfo3.mForegroundServiceType = foregroundServiceDelegationOptions.mForegroundServiceTypes;
                    serviceInfo3.processName = applicationInfo.processName;
                    ComponentName component2 = intent.getComponent();
                    serviceInfo3.name = component2.getClassName();
                    if (z2) {
                        Intent.FilterComparison filterComparison = new Intent.FilterComparison(intent.cloneFilter());
                        ServiceRestarter serviceRestarter = new ServiceRestarter();
                        String processNameForService = getProcessNameForService(serviceInfo3, component2, str4, null, false, false);
                        ActivityManagerService activityManagerService = this.mAm;
                        ApplicationInfo applicationInfo2 = serviceInfo3.applicationInfo;
                        serviceRecord4 = new ServiceRecord(activityManagerService, component2, component2, applicationInfo2.packageName, applicationInfo2.uid, filterComparison, serviceInfo3, z3, serviceRestarter, processNameForService, -1, null, false);
                        serviceRestarter.setService(serviceRecord4);
                        serviceMapLocked.mServicesByInstanceName.put(component2, serviceRecord4);
                        serviceMapLocked.mServicesByIntent.put(filterComparison, serviceRecord4);
                        serviceRecord4.mRecentCallingPackage = str4;
                        serviceRecord4.mRecentCallingUid = i3;
                    } else {
                        serviceRecord4 = serviceRecord;
                    }
                    StringBuilder sb = new StringBuilder();
                    ApplicationInfo applicationInfo3 = serviceRecord4.appInfo;
                    sb.append(applicationInfo3.seInfo);
                    sb.append(generateAdditionalSeInfoFromService(intent));
                    applicationInfo3.seInfo = sb.toString();
                    return new ServiceLookupResult(serviceRecord4, (ComponentName) resolveService.getAlias());
                }
                ServiceMap serviceMap2 = serviceMapLocked;
                if (serviceRecord == null) {
                    try {
                        str6 = " and ";
                        j = z5 ? 276825088 : 268436480;
                        str7 = componentName2;
                        str5 = "ActivityManager";
                        str8 = str4;
                        try {
                            ResolveInfo resolveService2 = this.mAm.getPackageManagerInternal().resolveService(intent, str3, j, handleIncomingUser, i3);
                            serviceInfo = resolveService2 != null ? resolveService2.serviceInfo : null;
                        } catch (RemoteException unused2) {
                            activeServices = this;
                        }
                    } catch (RemoteException unused3) {
                    }
                    if (serviceInfo == null) {
                        Slog.w(str5, "Unable to start service " + intent + " U=" + handleIncomingUser + ": not found");
                        return null;
                    }
                    if (str != null && (serviceInfo.flags & 2) == 0 && !z) {
                        throw new IllegalArgumentException("Can't use instance name '" + str + "' with non-isolated non-sdk sandbox service '" + serviceInfo.name + "'");
                    }
                    ComponentName componentName5 = new ComponentName(serviceInfo.applicationInfo.packageName, serviceInfo.name);
                    activeServices = this;
                    ComponentName componentName6 = str7 != 0 ? str7 : componentName5;
                    try {
                        ActivityManagerService activityManagerService2 = activeServices.mAm;
                        packageName = componentName6.getPackageName();
                        int i8 = serviceInfo.applicationInfo.uid;
                        validateAssociationAllowedLocked = activityManagerService2.validateAssociationAllowedLocked(str8, i3, packageName, i8);
                        try {
                        } catch (RemoteException unused4) {
                            obj = validateAssociationAllowedLocked;
                            i5 = i8;
                        }
                    } catch (RemoteException unused5) {
                        str7 = "Service lookup failed: ";
                        handleIncomingUser = "association not allowed between packages ";
                        serviceRecord2 = serviceRecord;
                        if (serviceRecord2 == null) {
                        }
                    }
                    if (validateAssociationAllowedLocked == 0) {
                        ?? sb2 = new StringBuilder();
                        ?? r72 = "association not allowed between packages ";
                        try {
                            sb2.append(r72);
                            sb2.append(str8);
                            packageName = str6;
                            try {
                                sb2.append(packageName);
                                sb2.append(componentName6.getPackageName());
                                String sb3 = sb2.toString();
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("Service lookup failed: ");
                                sb4.append(sb3);
                                Slog.w(str5, sb4.toString());
                                return activeServices.new ServiceLookupResult(sb3);
                            } catch (RemoteException unused6) {
                                obj = "Service lookup failed: ";
                                i5 = r72;
                                str6 = packageName;
                                r7 = i5;
                                str7 = obj;
                                handleIncomingUser = r7;
                                serviceRecord2 = serviceRecord;
                                if (serviceRecord2 == null) {
                                }
                            }
                        } catch (RemoteException unused7) {
                            str7 = "Service lookup failed: ";
                            r7 = r72;
                            handleIncomingUser = r7;
                            serviceRecord2 = serviceRecord;
                            if (serviceRecord2 == null) {
                            }
                        }
                    } else {
                        ApplicationInfo applicationInfo4 = serviceInfo.applicationInfo;
                        try {
                            String str13 = applicationInfo4.packageName;
                            int i9 = applicationInfo4.uid;
                            try {
                                int i10 = serviceInfo.flags;
                                str6 = str6;
                                try {
                                    try {
                                        if ((i10 & 4) == 0) {
                                            str9 = ": not found";
                                            i6 = i9;
                                            str10 = " U=";
                                            if (z4) {
                                                throw new SecurityException("BIND_EXTERNAL_SERVICE failed, " + componentName6 + " is not an externalService");
                                            }
                                            componentName3 = componentName6;
                                            componentName4 = componentName5;
                                        } else if (z4) {
                                            i6 = i9;
                                            if (!serviceInfo.exported) {
                                                throw new SecurityException("BIND_EXTERNAL_SERVICE failed, " + componentName5 + " is not exported");
                                            }
                                            if ((i10 & 2) == 0) {
                                                throw new SecurityException("BIND_EXTERNAL_SERVICE failed, " + componentName5 + " is not an isolatedProcess");
                                            }
                                            if (!activeServices.mAm.getPackageManagerInternal().isSameApp(str8, i3, handleIncomingUser)) {
                                                throw new SecurityException("BIND_EXTERNAL_SERVICE failed, calling package not owned by calling UID ");
                                            }
                                            str9 = ": not found";
                                            str10 = " U=";
                                            ApplicationInfo applicationInfo5 = AppGlobals.getPackageManager().getApplicationInfo(str8, 1024L, handleIncomingUser);
                                            if (applicationInfo5 == null) {
                                                throw new SecurityException("BIND_EXTERNAL_SERVICE failed, could not resolve client package " + str8);
                                            }
                                            ServiceInfo serviceInfo4 = new ServiceInfo(serviceInfo);
                                            ApplicationInfo applicationInfo6 = new ApplicationInfo(serviceInfo4.applicationInfo);
                                            serviceInfo4.applicationInfo = applicationInfo6;
                                            applicationInfo6.packageName = applicationInfo5.packageName;
                                            applicationInfo6.uid = applicationInfo5.uid;
                                            ComponentName componentName7 = new ComponentName(applicationInfo5.packageName, componentName6.getClassName());
                                            ComponentName componentName8 = new ComponentName(applicationInfo5.packageName, str == null ? componentName5.getClassName() : componentName5.getClassName() + XmlUtils.STRING_ARRAY_SEPARATOR + str);
                                            intent.setComponent(componentName7);
                                            componentName4 = componentName8;
                                            componentName3 = componentName7;
                                            serviceInfo = serviceInfo4;
                                        } else {
                                            throw new SecurityException("BIND_EXTERNAL_SERVICE required for " + componentName6);
                                        }
                                        if (z6) {
                                            int i11 = serviceInfo.flags;
                                            if ((i11 & 2) == 0) {
                                                throw new SecurityException("BIND_SHARED_ISOLATED_PROCESS failed, " + componentName4 + " is not an isolatedProcess");
                                            }
                                            if ((i11 & 16) == 0) {
                                                throw new SecurityException("BIND_SHARED_ISOLATED_PROCESS failed, " + componentName4 + " has not set the allowSharedIsolatedProcess  attribute.");
                                            }
                                            if (str == null) {
                                                throw new IllegalArgumentException("instanceName must be provided for binding a service into a shared isolated process.");
                                            }
                                        }
                                        if (handleIncomingUser > 0) {
                                            if (activeServices.mAm.isSingleton(serviceInfo.processName, serviceInfo.applicationInfo, serviceInfo.name, serviceInfo.flags) && activeServices.mAm.isValidSingletonCall(i3, serviceInfo.applicationInfo.uid)) {
                                                ServiceMap serviceMapLocked2 = activeServices.getServiceMapLocked(0);
                                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                                try {
                                                    String str14 = str10;
                                                    obj3 = "Service lookup failed: ";
                                                    obj4 = "association not allowed between packages ";
                                                    str11 = str13;
                                                    try {
                                                        ResolveInfo resolveService3 = activeServices.mAm.getPackageManagerInternal().resolveService(intent, str3, j, 0, i3);
                                                        if (resolveService3 == null) {
                                                            Slog.w(str5, "Unable to resolve service " + intent + str14 + 0 + str9);
                                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                                            return null;
                                                        }
                                                        serviceInfo = resolveService3.serviceInfo;
                                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                                        serviceMap2 = serviceMapLocked2;
                                                        i7 = 0;
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                                        throw th;
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                }
                                            } else {
                                                obj3 = "Service lookup failed: ";
                                                i7 = handleIncomingUser;
                                                obj4 = "association not allowed between packages ";
                                                str11 = str13;
                                            }
                                            ServiceInfo serviceInfo5 = new ServiceInfo(serviceInfo);
                                            serviceInfo5.applicationInfo = activeServices.mAm.getAppInfoForUser(serviceInfo5.applicationInfo, i7);
                                            serviceInfo2 = serviceInfo5;
                                            handleIncomingUser = obj4;
                                            str7 = obj3;
                                        } else {
                                            str7 = "Service lookup failed: ";
                                            handleIncomingUser = "association not allowed between packages ";
                                            str11 = str13;
                                            serviceInfo2 = serviceInfo;
                                        }
                                        serviceMap = serviceMap2;
                                        serviceRecord3 = (ServiceRecord) serviceMap.mServicesByInstanceName.get(componentName3);
                                    } catch (RemoteException unused8) {
                                        str7 = "Service lookup failed: ";
                                        handleIncomingUser = "association not allowed between packages ";
                                        serviceRecord2 = serviceRecord;
                                        if (serviceRecord2 == null) {
                                        }
                                    }
                                } catch (RemoteException unused9) {
                                }
                            } catch (RemoteException unused10) {
                                obj2 = "Service lookup failed: ";
                                str6 = str6;
                                str7 = obj2;
                                handleIncomingUser = "association not allowed between packages ";
                                serviceRecord2 = serviceRecord;
                                if (serviceRecord2 == null) {
                                }
                            }
                        } catch (RemoteException unused11) {
                            obj2 = "Service lookup failed: ";
                        }
                        if (serviceRecord3 == null && z2) {
                            try {
                                Intent.FilterComparison filterComparison2 = new Intent.FilterComparison(intent.cloneFilter());
                                ServiceRestarter serviceRestarter2 = new ServiceRestarter();
                                ServiceRecord serviceRecord6 = new ServiceRecord(activeServices.mAm, componentName4, componentName3, str11, i6, filterComparison2, serviceInfo2, z3, serviceRestarter2, getProcessNameForService(serviceInfo2, componentName3, str4, str, z, z6), i, str2, z6);
                                try {
                                    serviceRestarter2.setService(serviceRecord6);
                                    serviceMap.mServicesByInstanceName.put(componentName3, serviceRecord6);
                                    serviceMap.mServicesByIntent.put(filterComparison2, serviceRecord6);
                                    for (int size = activeServices.mPendingServices.size() - 1; size >= 0; size--) {
                                        ServiceRecord serviceRecord7 = (ServiceRecord) activeServices.mPendingServices.get(size);
                                        if (serviceRecord7.serviceInfo.applicationInfo.uid == serviceInfo2.applicationInfo.uid && serviceRecord7.instanceName.equals(componentName3)) {
                                            activeServices.mPendingServices.remove(size);
                                        }
                                    }
                                    for (int size2 = activeServices.mPendingBringups.size() - 1; size2 >= 0; size2--) {
                                        ServiceRecord serviceRecord8 = (ServiceRecord) activeServices.mPendingBringups.keyAt(size2);
                                        if (serviceRecord8.serviceInfo.applicationInfo.uid == serviceInfo2.applicationInfo.uid && serviceRecord8.instanceName.equals(componentName3)) {
                                            activeServices.mPendingBringups.removeAt(size2);
                                        }
                                    }
                                } catch (RemoteException unused12) {
                                }
                                serviceRecord = serviceRecord6;
                            } catch (RemoteException unused13) {
                            }
                            serviceRecord2 = serviceRecord;
                            if (serviceRecord2 == null) {
                                return null;
                            }
                            serviceRecord2.mRecentCallingPackage = str8;
                            serviceRecord2.mRecentCallingUid = i3;
                            try {
                                serviceRecord2.mRecentCallerApplicationInfo = activeServices.mAm.mContext.getPackageManager().getApplicationInfoAsUser(str8, 0, UserHandle.getUserId(i3));
                            } catch (PackageManager.NameNotFoundException unused14) {
                            }
                            if (!activeServices.mAm.validateAssociationAllowedLocked(str8, i3, serviceRecord2.packageName, serviceRecord2.appInfo.uid)) {
                                String str15 = handleIncomingUser + str8 + str6 + serviceRecord2.packageName;
                                Slog.w(str5, str7 + str15);
                                return activeServices.new ServiceLookupResult(str15);
                            }
                            if (!activeServices.mAm.mIntentFirewall.checkService(serviceRecord2.name, intent, i3, i2, str3, serviceRecord2.appInfo)) {
                                return activeServices.new ServiceLookupResult("blocked by firewall");
                            }
                            if (ActivityManagerService.checkComponentPermission(serviceRecord2.permission, i2, i3, serviceRecord2.appInfo.uid, serviceRecord2.exported) != 0) {
                                if (!serviceRecord2.exported) {
                                    Slog.w(str5, "Permission Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " that is not exported from uid " + serviceRecord2.appInfo.uid);
                                    return activeServices.new ServiceLookupResult("not exported from uid " + serviceRecord2.appInfo.uid);
                                }
                                Slog.w(str5, "Permission Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " requires " + serviceRecord2.permission);
                                return activeServices.new ServiceLookupResult(serviceRecord2.permission);
                            }
                            if (("android.permission.BIND_HOTWORD_DETECTION_SERVICE".equals(serviceRecord2.permission) || "android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE".equals(serviceRecord2.permission)) && i3 != 1000) {
                                Slog.w(str5, "Permission Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " requiring permission " + serviceRecord2.permission + " can only be bound to from the system.");
                                return activeServices.new ServiceLookupResult("can only be bound to by the system.");
                            }
                            String str16 = serviceRecord2.permission;
                            if (str16 != null && str8 != null && (permissionToOpCode = AppOpsManager.permissionToOpCode(str16)) != -1 && activeServices.mAm.getAppOpsManager().checkOpNoThrow(permissionToOpCode, i3, str8) != 0) {
                                Slog.w(str5, "Appop Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " requires appop " + AppOpsManager.opToName(permissionToOpCode));
                                return null;
                            }
                            StringBuilder sb5 = new StringBuilder();
                            ApplicationInfo applicationInfo7 = serviceRecord2.appInfo;
                            sb5.append(applicationInfo7.seInfo);
                            sb5.append(generateAdditionalSeInfoFromService(intent));
                            applicationInfo7.seInfo = sb5.toString();
                            return activeServices.new ServiceLookupResult(serviceRecord2, (ComponentName) resolveService.getAlias());
                        }
                        serviceRecord = serviceRecord3;
                        serviceRecord2 = serviceRecord;
                        if (serviceRecord2 == null) {
                        }
                    }
                }
                activeServices = this;
                str5 = "ActivityManager";
                str6 = " and ";
                str7 = "Service lookup failed: ";
                handleIncomingUser = "association not allowed between packages ";
                str8 = str4;
                serviceRecord2 = serviceRecord;
                if (serviceRecord2 == null) {
                }
            }
        } else {
            componentName2 = componentName;
        }
        serviceRecord = serviceRecord5;
        if (serviceRecord != null) {
        }
        ServiceMap serviceMap22 = serviceMapLocked;
        if (serviceRecord == null) {
        }
        activeServices = this;
        str5 = "ActivityManager";
        str6 = " and ";
        str7 = "Service lookup failed: ";
        handleIncomingUser = "association not allowed between packages ";
        str8 = str4;
        serviceRecord2 = serviceRecord;
        if (serviceRecord2 == null) {
        }
    }

    public final int getAllowMode(Intent intent, String str) {
        return (str == null || intent.getComponent() == null || !str.equals(intent.getComponent().getPackageName())) ? 1 : 3;
    }

    public final boolean bumpServiceExecutingLocked(ServiceRecord serviceRecord, boolean z, String str, int i) {
        boolean z2;
        ProcessRecord processRecord;
        ProcessRecord processRecord2;
        boolean z3 = false;
        if (this.mAm.mBootPhase >= 600 || (processRecord2 = serviceRecord.app) == null || processRecord2.getPid() != ActivityManagerService.MY_PID) {
            z2 = true;
        } else {
            Slog.w("ActivityManager", "Too early to start/bind service in system_server: Phase=" + this.mAm.mBootPhase + " " + serviceRecord.getComponentName());
            z2 = false;
        }
        if (serviceRecord != null && MARsPolicyManager.MARs_ENABLE && FreecessController.getInstance().getFreecessEnabled()) {
            if (MARsPolicyManager.getInstance().checkIsChinaModel() && FreecessController.getInstance().isPidUfzEnabled() && (("unbind".equals(str) || "destroy".equals(str)) && FreecessController.getInstance().isPackageFreezed(serviceRecord.packageName, serviceRecord.userId))) {
                if (serviceRecord.app != null) {
                    Slog.d("ActivityManager", "Chain destruct kill: pid=" + serviceRecord.app.getPid());
                    Process.killProcess(serviceRecord.app.getPid());
                }
            } else {
                if (serviceRecord.app != null) {
                    FreecessController.getInstance().protectFreezePackage(serviceRecord.app.uid, "ServiceANR", 2000L);
                }
                FreecessController.getInstance().protectFreezePackage(serviceRecord.packageName, serviceRecord.userId, "ServiceANR", 2000L);
            }
        }
        if (serviceRecord.executeNesting == 0) {
            serviceRecord.executeFg = z;
            synchronized (this.mAm.mProcessStats.mLock) {
                ServiceState tracker = serviceRecord.getTracker();
                if (tracker != null) {
                    tracker.setExecuting(true, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            ProcessRecord processRecord3 = serviceRecord.app;
            if (processRecord3 != null) {
                ProcessServiceRecord processServiceRecord = processRecord3.mServices;
                processServiceRecord.startExecutingService(serviceRecord);
                processServiceRecord.setExecServicesFg(processServiceRecord.shouldExecServicesFg() || z);
                if (z2 && processServiceRecord.numberOfExecutingServices() == 1) {
                    scheduleServiceTimeoutLocked(serviceRecord.app);
                }
            }
        } else {
            ProcessRecord processRecord4 = serviceRecord.app;
            if (processRecord4 != null && z) {
                ProcessServiceRecord processServiceRecord2 = processRecord4.mServices;
                if (!processServiceRecord2.shouldExecServicesFg()) {
                    processServiceRecord2.setExecServicesFg(true);
                    if (z2) {
                        scheduleServiceTimeoutLocked(serviceRecord.app);
                    }
                }
            }
        }
        if (i != 0 && (processRecord = serviceRecord.app) != null && processRecord.mState.getCurProcState() > 10) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            this.mAm.updateOomAdjPendingTargetsLocked(i);
            z3 = true;
        }
        serviceRecord.executeFg |= z;
        serviceRecord.executeNesting++;
        serviceRecord.executingStart = SystemClock.uptimeMillis();
        return z3;
    }

    public final boolean requestServiceBindingLocked(ServiceRecord serviceRecord, IntentBindRecord intentBindRecord, boolean z, boolean z2) {
        ProcessRecord processRecord = serviceRecord.app;
        if (processRecord == null || processRecord.getThread() == null) {
            return false;
        }
        if (MARsPolicyManager.MARs_ENABLE) {
            this.mAm.mOomAdjuster.mCachedAppOptimizer.unfreezeTemporarily(serviceRecord.app, 4);
        }
        if ((!intentBindRecord.requested || z2) && intentBindRecord.apps.size() > 0) {
            try {
                bumpServiceExecutingLocked(serviceRecord, z, "bind", 4);
                if (Trace.isTagEnabled(64L)) {
                    Trace.instant(64L, "requestServiceBinding=" + intentBindRecord.intent.getIntent() + ". bindSeq=" + this.mBindServiceSeqCounter);
                }
                IApplicationThread thread = serviceRecord.app.getThread();
                Intent intent = intentBindRecord.intent.getIntent();
                int reportedProcState = serviceRecord.app.mState.getReportedProcState();
                long j = this.mBindServiceSeqCounter;
                this.mBindServiceSeqCounter = 1 + j;
                thread.scheduleBindService(serviceRecord, intent, z2, reportedProcState, j);
                if (!z2) {
                    intentBindRecord.requested = true;
                }
                intentBindRecord.hasBound = true;
                intentBindRecord.doRebind = false;
            } catch (TransactionTooLargeException e) {
                boolean contains = this.mDestroyingServices.contains(serviceRecord);
                serviceDoneExecutingLocked(serviceRecord, contains, contains, false, 5);
                throw e;
            } catch (RemoteException unused) {
                boolean contains2 = this.mDestroyingServices.contains(serviceRecord);
                serviceDoneExecutingLocked(serviceRecord, contains2, contains2, false, 5);
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x016b  */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v10, types: [int] */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.util.ArrayList] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean scheduleServiceRestartLocked(ServiceRecord serviceRecord, boolean z) {
        boolean z2;
        boolean z3;
        String str;
        Boolean isRescheduleExceptionPackage;
        boolean z4;
        String str2;
        boolean z5;
        boolean z6;
        boolean z7;
        int i = 0;
        if (this.mAm.mAtmInternal.isShuttingDown()) {
            Slog.w("ActivityManager", "Not scheduling restart of crashed service " + serviceRecord.shortInstanceName + " - system is shutting down");
            return false;
        }
        ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
        if (serviceMapLocked.mServicesByInstanceName.get(serviceRecord.instanceName) != serviceRecord) {
            Slog.wtf("ActivityManager", "Attempting to schedule restart of " + serviceRecord + " when found in map: " + ((ServiceRecord) serviceMapLocked.mServicesByInstanceName.get(serviceRecord.instanceName)));
            return false;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int indexOf = this.mRestartingServices.indexOf(serviceRecord);
        boolean z8 = indexOf != -1;
        if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) == 0) {
            ActivityManagerConstants activityManagerConstants = this.mAm.mConstants;
            long j = activityManagerConstants.SERVICE_RESTART_DURATION;
            long j2 = activityManagerConstants.SERVICE_RESET_RUN_DURATION;
            int size = serviceRecord.deliveredStarts.size();
            if (size > 0) {
                int i2 = size - 1;
                boolean z9 = false;
                while (i2 >= 0) {
                    ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.deliveredStarts.get(i2);
                    startItem.removeUriPermissionsLocked();
                    if (startItem.intent != null) {
                        if (!z || (startItem.deliveryCount < 3 && startItem.doneExecutingCount < 6)) {
                            serviceRecord.pendingStarts.add(i, startItem);
                            long uptimeMillis2 = (SystemClock.uptimeMillis() - startItem.deliveredTime) * 2;
                            if (j < uptimeMillis2) {
                                j = uptimeMillis2;
                            }
                            if (j2 < uptimeMillis2) {
                                j2 = uptimeMillis2;
                            }
                        } else {
                            Slog.w("ActivityManager", "Canceling start item " + startItem.intent + " in service " + serviceRecord.shortInstanceName);
                            z9 = true;
                        }
                    }
                    i2--;
                    i = 0;
                }
                serviceRecord.deliveredStarts.clear();
                z4 = z9;
            } else {
                z4 = false;
            }
            if (z) {
                boolean canStopIfKilled = serviceRecord.canStopIfKilled(z4);
                if (canStopIfKilled && !serviceRecord.hasAutoCreateConnections()) {
                    return false;
                }
                str2 = (!serviceRecord.startRequested || canStopIfKilled) ? "connection" : "start-requested";
            } else {
                str2 = "always";
            }
            serviceRecord.totalRestartCount++;
            long j3 = serviceRecord.restartDelay;
            if (j3 == 0) {
                serviceRecord.restartCount++;
                serviceRecord.restartDelay = j;
            } else {
                if (serviceRecord.crashCount > 1) {
                    serviceRecord.restartDelay = this.mAm.mConstants.BOUND_SERVICE_CRASH_RESTART_DURATION * (r4 - 1);
                } else {
                    z5 = z8;
                    if (uptimeMillis > serviceRecord.restartTime + j2) {
                        serviceRecord.restartCount = 1;
                        serviceRecord.restartDelay = j;
                    } else {
                        long j4 = j3 * this.mAm.mConstants.SERVICE_RESTART_DURATION_FACTOR;
                        serviceRecord.restartDelay = j4;
                        if (j4 < j) {
                            serviceRecord.restartDelay = j;
                        }
                    }
                    if (!isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
                        long j5 = serviceRecord.restartDelay + uptimeMillis;
                        serviceRecord.mEarliestRestartTime = j5;
                        serviceRecord.nextRestartTime = j5;
                        if (z5) {
                            this.mRestartingServices.remove(indexOf);
                            z6 = false;
                        } else {
                            z6 = z5;
                        }
                        if (this.mRestartingServices.isEmpty()) {
                            long max = Math.max(getExtraRestartTimeInBetweenLocked() + uptimeMillis, serviceRecord.nextRestartTime);
                            serviceRecord.nextRestartTime = max;
                            serviceRecord.restartDelay = max - uptimeMillis;
                        } else {
                            long extraRestartTimeInBetweenLocked = getExtraRestartTimeInBetweenLocked() + this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN;
                            do {
                                long j6 = serviceRecord.nextRestartTime;
                                for (int size2 = this.mRestartingServices.size() - 1; size2 >= 0; size2--) {
                                    long j7 = ((ServiceRecord) this.mRestartingServices.get(size2)).nextRestartTime;
                                    if (j6 >= j7 - extraRestartTimeInBetweenLocked) {
                                        long j8 = j7 + extraRestartTimeInBetweenLocked;
                                        if (j6 < j8) {
                                            serviceRecord.nextRestartTime = j8;
                                            serviceRecord.restartDelay = j8 - uptimeMillis;
                                            z7 = true;
                                            break;
                                        }
                                    }
                                    if (j6 >= j7 + extraRestartTimeInBetweenLocked) {
                                        break;
                                    }
                                }
                                z7 = false;
                            } while (z7);
                        }
                        z3 = z6;
                    } else {
                        long j9 = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                        serviceRecord.restartDelay = j9;
                        serviceRecord.nextRestartTime = j9 + uptimeMillis;
                        z3 = z5;
                    }
                    str = str2;
                    z2 = false;
                }
            }
            z5 = z8;
            if (!isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
            }
            str = str2;
            z2 = false;
        } else {
            serviceRecord.totalRestartCount++;
            z2 = false;
            serviceRecord.restartCount = 0;
            serviceRecord.restartDelay = 0L;
            serviceRecord.mEarliestRestartTime = 0L;
            serviceRecord.nextRestartTime = uptimeMillis;
            z3 = z8;
            str = "persistent";
        }
        serviceRecord.mRestartSchedulingTime = uptimeMillis;
        if (!z3) {
            if (indexOf == -1) {
                serviceRecord.createdFromFg = z2;
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.makeRestarting(this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            int size3 = this.mRestartingServices.size();
            ?? r7 = z2;
            while (true) {
                if (r7 >= size3) {
                    break;
                }
                if (((ServiceRecord) this.mRestartingServices.get(r7)).nextRestartTime > serviceRecord.nextRestartTime) {
                    this.mRestartingServices.add(r7, serviceRecord);
                    z2 = true;
                    break;
                }
                r7++;
            }
            if (!z2) {
                this.mRestartingServices.add(serviceRecord);
            }
        }
        cancelForegroundNotificationLocked(serviceRecord);
        ChimeraManagerService chimeraManagerService = (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
        if (chimeraManagerService != null && (isRescheduleExceptionPackage = chimeraManagerService.isRescheduleExceptionPackage(serviceRecord.packageName)) != null) {
            long j10 = isRescheduleExceptionPackage.booleanValue() ? 180000L : this.mAm.mConstants.SERVICE_RESTART_DURATION;
            serviceRecord.restartDelay = j10;
            serviceRecord.nextRestartTime = j10 + uptimeMillis;
        }
        performScheduleRestartLocked(serviceRecord, "Scheduling", str, uptimeMillis);
        return true;
    }

    public void performScheduleRestartLocked(ServiceRecord serviceRecord, String str, String str2, long j) {
        if (serviceRecord.fgRequired && serviceRecord.fgWaiting) {
            this.mAm.mHandler.removeMessages(66, serviceRecord);
            serviceRecord.fgWaiting = false;
        }
        this.mAm.mHandler.removeCallbacks(serviceRecord.restarter);
        this.mAm.mHandler.postAtTime(serviceRecord.restarter, serviceRecord.nextRestartTime);
        serviceRecord.nextRestartTime = j + serviceRecord.restartDelay;
        Slog.w("ActivityManager", str + " restart of crashed service " + serviceRecord.shortInstanceName + " in " + serviceRecord.restartDelay + "ms for " + str2);
        EventLog.writeEvent(30035, Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, Long.valueOf(serviceRecord.restartDelay));
    }

    public void rescheduleServiceRestartOnMemoryPressureIfNeededLocked(int i, int i2, String str, long j) {
        ActivityManagerConstants activityManagerConstants = this.mAm.mConstants;
        if (activityManagerConstants.mEnableExtraServiceRestartDelayOnMemPressure) {
            long[] jArr = activityManagerConstants.mExtraServiceRestartDelayOnMemPressure;
            performRescheduleServiceRestartOnMemoryPressureLocked(jArr[i], jArr[i2], str, j);
        }
    }

    public void rescheduleServiceRestartOnMemoryPressureIfNeededLocked(boolean z, boolean z2, long j) {
        if (z == z2) {
            return;
        }
        long j2 = this.mAm.mConstants.mExtraServiceRestartDelayOnMemPressure[this.mAm.mAppProfiler.getLastMemoryLevelLocked()];
        long j3 = z ? j2 : 0L;
        if (!z2) {
            j2 = 0;
        }
        performRescheduleServiceRestartOnMemoryPressureLocked(j3, j2, "config", j);
    }

    public void rescheduleServiceRestartIfPossibleLocked(long j, long j2, String str, long j3) {
        long j4;
        long j5;
        long j6;
        long j7 = j + j2;
        long j8 = j7 * 2;
        int size = this.mRestartingServices.size();
        int i = -1;
        int i2 = 0;
        long j9 = j3;
        while (i2 < size) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i2);
            if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) != 0 || !isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
                j4 = j7;
                j5 = j8;
                j9 = serviceRecord.nextRestartTime;
                i = i2;
            } else {
                long j10 = j9 + j7;
                long j11 = j8;
                long j12 = serviceRecord.mEarliestRestartTime;
                if (j10 <= j12) {
                    serviceRecord.nextRestartTime = Math.max(j3, Math.max(j12, i2 > 0 ? ((ServiceRecord) this.mRestartingServices.get(i2 - 1)).nextRestartTime + j7 : 0L));
                } else {
                    if (j9 <= j3) {
                        serviceRecord.nextRestartTime = Math.max(j3, Math.max(j12, serviceRecord.mRestartSchedulingTime + j));
                    } else {
                        serviceRecord.nextRestartTime = Math.max(j3, j10);
                    }
                    int i3 = i + 1;
                    if (i2 > i3) {
                        this.mRestartingServices.remove(i2);
                        this.mRestartingServices.add(i3, serviceRecord);
                    }
                }
                int i4 = i;
                long j13 = j9;
                int i5 = i + 1;
                while (true) {
                    if (i5 > i2) {
                        j4 = j7;
                        j5 = j11;
                        break;
                    }
                    ServiceRecord serviceRecord2 = (ServiceRecord) this.mRestartingServices.get(i5);
                    long j14 = serviceRecord2.nextRestartTime;
                    if (i5 == 0) {
                        j4 = j7;
                        j6 = j13;
                    } else {
                        j4 = j7;
                        j6 = ((ServiceRecord) this.mRestartingServices.get(i5 - 1)).nextRestartTime;
                    }
                    long j15 = j14 - j6;
                    j5 = j11;
                    if (j15 >= j5) {
                        break;
                    }
                    i4 = i5;
                    j13 = serviceRecord2.nextRestartTime;
                    i5++;
                    j11 = j5;
                    j7 = j4;
                }
                serviceRecord.restartDelay = serviceRecord.nextRestartTime - j3;
                performScheduleRestartLocked(serviceRecord, "Rescheduling", str, j3);
                i = i4;
                j9 = j13;
            }
            i2++;
            j8 = j5;
            j7 = j4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0055, code lost:
    
        if (r3 != r0) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void performRescheduleServiceRestartOnMemoryPressureLocked(long j, long j2, String str, long j3) {
        boolean z;
        long j4 = j2 - j;
        if (j4 == 0) {
            return;
        }
        if (j4 <= 0) {
            if (j4 < 0) {
                rescheduleServiceRestartIfPossibleLocked(j2, this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN, str, j3);
                return;
            }
            return;
        }
        long j5 = this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN + j2;
        int size = this.mRestartingServices.size();
        long j6 = j3;
        for (int i = 0; i < size; i++) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i);
            if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) != 0 || !isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
                j6 = serviceRecord.nextRestartTime;
            } else {
                if (j6 <= j3) {
                    long j7 = serviceRecord.nextRestartTime;
                    long max = Math.max(j3, Math.max(serviceRecord.mEarliestRestartTime, serviceRecord.mRestartSchedulingTime + j2));
                    serviceRecord.nextRestartTime = max;
                } else {
                    if (serviceRecord.nextRestartTime - j6 < j5) {
                        serviceRecord.nextRestartTime = Math.max(j6 + j5, j3);
                        z = true;
                    }
                    z = false;
                }
                long j8 = serviceRecord.nextRestartTime;
                serviceRecord.restartDelay = j8 - j3;
                if (z) {
                    performScheduleRestartLocked(serviceRecord, "Rescheduling", str, j3);
                }
                j6 = j8;
            }
        }
    }

    public long getExtraRestartTimeInBetweenLocked() {
        ActivityManagerService activityManagerService = this.mAm;
        if (!activityManagerService.mConstants.mEnableExtraServiceRestartDelayOnMemPressure) {
            return 0L;
        }
        return this.mAm.mConstants.mExtraServiceRestartDelayOnMemPressure[activityManagerService.mAppProfiler.getLastMemoryLevelLocked()];
    }

    public final void performServiceRestartLocked(ServiceRecord serviceRecord) {
        if (this.mRestartingServices.contains(serviceRecord)) {
            if (!isServiceNeededLocked(serviceRecord, false, false)) {
                Slog.wtf("ActivityManager", "Restarting service that is not needed: " + serviceRecord);
                return;
            }
            try {
                bringUpServiceLocked(serviceRecord, serviceRecord.intent.getIntent().getFlags(), serviceRecord.createdFromFg, true, false, false, true);
            } catch (TransactionTooLargeException unused) {
            } catch (Throwable th) {
                this.mAm.updateOomAdjPendingTargetsLocked(6);
                throw th;
            }
            this.mAm.updateOomAdjPendingTargetsLocked(6);
        }
    }

    public final boolean unscheduleServiceRestartLocked(ServiceRecord serviceRecord, int i, boolean z, String str) {
        if (!z && serviceRecord.restartDelay == 0) {
            return false;
        }
        boolean remove = this.mRestartingServices.remove(serviceRecord);
        if (remove || i != serviceRecord.appInfo.uid) {
            serviceRecord.resetRestartCounter();
        }
        if (remove) {
            clearRestartingIfNeededLocked(serviceRecord);
            EventLog.writeEvent(1000102, Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, Long.valueOf(serviceRecord.restartDelay), str);
        }
        this.mAm.mHandler.removeCallbacks(serviceRecord.restarter);
        return true;
    }

    public final void clearRestartingIfNeededLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.restartTracker != null) {
            boolean z = true;
            int size = this.mRestartingServices.size() - 1;
            while (true) {
                if (size < 0) {
                    z = false;
                    break;
                } else if (((ServiceRecord) this.mRestartingServices.get(size)).restartTracker == serviceRecord.restartTracker) {
                    break;
                } else {
                    size--;
                }
            }
            if (z) {
                return;
            }
            synchronized (this.mAm.mProcessStats.mLock) {
                serviceRecord.restartTracker.setRestarting(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
            }
            serviceRecord.restartTracker = null;
        }
    }

    public void setServiceRestartBackoffEnabledLocked(String str, boolean z, String str2) {
        if (!z) {
            if (this.mRestartBackoffDisabledPackages.contains(str)) {
                return;
            }
            this.mRestartBackoffDisabledPackages.add(str);
            long uptimeMillis = SystemClock.uptimeMillis();
            int size = this.mRestartingServices.size();
            for (int i = 0; i < size; i++) {
                ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i);
                if (TextUtils.equals(serviceRecord.packageName, str)) {
                    long j = serviceRecord.nextRestartTime - uptimeMillis;
                    long j2 = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                    if (j > j2) {
                        serviceRecord.restartDelay = j2;
                        serviceRecord.nextRestartTime = j2 + uptimeMillis;
                        performScheduleRestartLocked(serviceRecord, "Rescheduling", str2, uptimeMillis);
                    }
                }
                Collections.sort(this.mRestartingServices, new Comparator() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int lambda$setServiceRestartBackoffEnabledLocked$0;
                        lambda$setServiceRestartBackoffEnabledLocked$0 = ActiveServices.lambda$setServiceRestartBackoffEnabledLocked$0((ServiceRecord) obj, (ServiceRecord) obj2);
                        return lambda$setServiceRestartBackoffEnabledLocked$0;
                    }
                });
            }
            return;
        }
        removeServiceRestartBackoffEnabledLocked(str);
    }

    public static /* synthetic */ int lambda$setServiceRestartBackoffEnabledLocked$0(ServiceRecord serviceRecord, ServiceRecord serviceRecord2) {
        return (int) (serviceRecord.nextRestartTime - serviceRecord2.nextRestartTime);
    }

    public final void removeServiceRestartBackoffEnabledLocked(String str) {
        this.mRestartBackoffDisabledPackages.remove(str);
    }

    public boolean isServiceRestartBackoffEnabledLocked(String str) {
        return !this.mRestartBackoffDisabledPackages.contains(str);
    }

    public final String bringUpServiceLocked(ServiceRecord serviceRecord, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        try {
            if (Trace.isTagEnabled(64L)) {
                Trace.traceBegin(64L, "bringUpServiceLocked: " + serviceRecord.shortInstanceName);
            }
            return bringUpServiceInnerLocked(serviceRecord, i, z, z2, z3, z4, z5);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0316 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x03be  */
    /* JADX WARN: Type inference failed for: r19v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r19v2 */
    /* JADX WARN: Type inference failed for: r19v7 */
    /* JADX WARN: Type inference failed for: r22v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r22v3 */
    /* JADX WARN: Type inference failed for: r22v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String bringUpServiceInnerLocked(ServiceRecord serviceRecord, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        String str;
        boolean z6;
        String str2;
        String str3;
        String str4;
        ProcessRecord processRecord;
        HostingRecord byAppZygote;
        ProcessRecord processRecord2;
        String str5;
        String str6;
        long j;
        long j2;
        ProcessRecord startProcessLocked;
        String str7;
        long j3;
        String str8;
        String str9;
        ProcessRecord processRecord3 = serviceRecord.app;
        if (processRecord3 != null && processRecord3.getThread() != null) {
            sendServiceArgsLocked(serviceRecord, z, false);
            return null;
        }
        if (!z2 && this.mRestartingServices.contains(serviceRecord)) {
            return null;
        }
        if (this.mRestartingServices.remove(serviceRecord)) {
            clearRestartingIfNeededLocked(serviceRecord);
        }
        if (serviceRecord.delayed) {
            getServiceMapLocked(serviceRecord.userId).mDelayedStartList.remove(serviceRecord);
            serviceRecord.delayed = false;
        }
        String str10 = "ActivityManager";
        if (!this.mAm.mUserController.hasStartedUserState(serviceRecord.userId)) {
            String str11 = "Unable to launch app " + serviceRecord.appInfo.packageName + "/" + serviceRecord.appInfo.uid + " for service " + serviceRecord.intent.getIntent() + ": user " + serviceRecord.userId + " is stopped";
            Slog.w("ActivityManager", str11);
            bringDownServiceLocked(serviceRecord, z5);
            return str11;
        }
        if (this.mAm.needToBlockImsRequest(serviceRecord.appInfo.packageName, serviceRecord.userId)) {
            String str12 = "[IMS-AM] Block bringUpServiceLocked() of [" + serviceRecord.appInfo.packageName + "] for non-active user [" + serviceRecord.userId + "]";
            Slog.w("ActivityManager", str12);
            bringDownServiceLocked(serviceRecord, z5);
            return str12;
        }
        if (!serviceRecord.appInfo.packageName.equals(serviceRecord.mRecentCallingPackage) && !serviceRecord.isNotAppComponentUsage) {
            this.mAm.mUsageStatsService.reportEvent(serviceRecord.packageName, serviceRecord.userId, 31);
        }
        try {
            this.mAm.mPackageManagerInt.setPackageStoppedState(serviceRecord.packageName, false, serviceRecord.userId);
        } catch (IllegalArgumentException e) {
            Slog.w("ActivityManager", "Failed trying to unstop package " + serviceRecord.packageName + ": " + e);
        }
        if (MARsPolicyManager.MARs_ENABLE) {
            ProcessRecord processRecord4 = serviceRecord.app;
            HostingRecord hostingRecord = processRecord4 != null ? processRecord4.getHostingRecord() : null;
            if (hostingRecord != null) {
                str = hostingRecord.toStringForTracker();
                ServiceInfo serviceInfo = serviceRecord.serviceInfo;
                z6 = (serviceInfo.flags & 2) == 0;
                String str13 = serviceRecord.processName;
                ComponentName componentName = serviceRecord.instanceName;
                String str14 = serviceRecord.definingPackageName;
                int i2 = serviceRecord.definingUid;
                String str15 = serviceInfo.processName;
                long hostingRecordTriggerType = getHostingRecordTriggerType(serviceRecord);
                long j4 = str14;
                HostingRecord hostingRecord2 = new HostingRecord("service", componentName, j4, i2, str15, hostingRecordTriggerType, str);
                if (z6) {
                    ProcessRecord processRecordLocked = this.mAm.getProcessRecordLocked(str13, serviceRecord.appInfo.uid);
                    if (processRecordLocked != null) {
                        IApplicationThread thread = processRecordLocked.getThread();
                        int pid = processRecordLocked.getPid();
                        UidRecord uidRecord = processRecordLocked.getUidRecord();
                        try {
                            if (thread != null) {
                                try {
                                    if (Trace.isTagEnabled(64L)) {
                                        Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord.shortInstanceName);
                                    }
                                    ApplicationInfo applicationInfo = serviceRecord.appInfo;
                                    try {
                                        processRecordLocked.addPackage(applicationInfo.packageName, applicationInfo.longVersionCode, this.mAm.mProcessStats);
                                        j3 = 64;
                                        str8 = "Exception when starting service ";
                                        str7 = str13;
                                        str9 = "ActivityManager";
                                        str2 = "Unable to launch app ";
                                        try {
                                            realStartServiceLocked(serviceRecord, processRecordLocked, thread, pid, uidRecord, z, z5);
                                            Trace.traceEnd(64L);
                                            return null;
                                        } catch (TransactionTooLargeException e2) {
                                            throw e2;
                                        } catch (RemoteException e3) {
                                            e = e3;
                                            str10 = str9;
                                            Slog.w(str10, str8 + serviceRecord.shortInstanceName, e);
                                            Trace.traceEnd(j3);
                                            str3 = str10;
                                            processRecord = processRecordLocked;
                                            byAppZygote = hostingRecord2;
                                            str4 = str7;
                                            if (processRecord == null) {
                                            }
                                            if (serviceRecord.fgRequired) {
                                            }
                                            if (!this.mPendingServices.contains(serviceRecord)) {
                                            }
                                            if (serviceRecord.delayedStop) {
                                            }
                                            return null;
                                        }
                                    } catch (TransactionTooLargeException e4) {
                                        throw e4;
                                    } catch (RemoteException e5) {
                                        e = e5;
                                        str8 = "Exception when starting service ";
                                        str7 = str13;
                                        str9 = "ActivityManager";
                                        str2 = "Unable to launch app ";
                                        j3 = 64;
                                    } catch (Throwable th) {
                                        th = th;
                                        hostingRecordTriggerType = 64;
                                        Trace.traceEnd(hostingRecordTriggerType);
                                        throw th;
                                    }
                                } catch (TransactionTooLargeException e6) {
                                    throw e6;
                                } catch (RemoteException e7) {
                                    e = e7;
                                    j3 = 64;
                                    str8 = "Exception when starting service ";
                                    str7 = str13;
                                    str9 = "ActivityManager";
                                    str2 = "Unable to launch app ";
                                } catch (Throwable th2) {
                                    th = th2;
                                    hostingRecordTriggerType = 64;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                    str7 = str13;
                    str2 = "Unable to launch app ";
                    str3 = str10;
                    processRecord = processRecordLocked;
                    byAppZygote = hostingRecord2;
                    str4 = str7;
                } else {
                    str2 = "Unable to launch app ";
                    if (serviceRecord.inSharedIsolatedProcess) {
                        ProcessList processList = this.mAm.mProcessList;
                        ApplicationInfo applicationInfo2 = serviceRecord.appInfo;
                        str4 = str13;
                        ProcessRecord sharedIsolatedProcess = processList.getSharedIsolatedProcess(str4, applicationInfo2.uid, applicationInfo2.packageName);
                        if (sharedIsolatedProcess != null) {
                            IApplicationThread thread2 = sharedIsolatedProcess.getThread();
                            int pid2 = sharedIsolatedProcess.getPid();
                            UidRecord uidRecord2 = sharedIsolatedProcess.getUidRecord();
                            serviceRecord.isolationHostProc = sharedIsolatedProcess;
                            try {
                                if (thread2 != null) {
                                    try {
                                        if (Trace.isTagEnabled(64L)) {
                                            j2 = 64;
                                            try {
                                                Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord.shortInstanceName);
                                            } catch (TransactionTooLargeException e8) {
                                                throw e8;
                                            } catch (RemoteException e9) {
                                                e = e9;
                                                j = 64;
                                                processRecord2 = sharedIsolatedProcess;
                                                str5 = "ActivityManager";
                                                str6 = "Exception when starting service ";
                                                str3 = str5;
                                                Slog.w(str3, str6 + serviceRecord.shortInstanceName, e);
                                                Trace.traceEnd(j);
                                                processRecord = processRecord2;
                                                byAppZygote = hostingRecord2;
                                                if (processRecord == null) {
                                                }
                                                if (serviceRecord.fgRequired) {
                                                }
                                                if (!this.mPendingServices.contains(serviceRecord)) {
                                                }
                                                if (serviceRecord.delayedStop) {
                                                }
                                                return null;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                j4 = 64;
                                                Trace.traceEnd(j4);
                                                throw th;
                                            }
                                        } else {
                                            j2 = 64;
                                        }
                                        j = j2;
                                        processRecord2 = sharedIsolatedProcess;
                                        str5 = "ActivityManager";
                                        str6 = "Exception when starting service ";
                                        try {
                                            realStartServiceLocked(serviceRecord, sharedIsolatedProcess, thread2, pid2, uidRecord2, z, z5);
                                            Trace.traceEnd(j);
                                            return null;
                                        } catch (TransactionTooLargeException e10) {
                                            throw e10;
                                        } catch (RemoteException e11) {
                                            e = e11;
                                            str3 = str5;
                                            Slog.w(str3, str6 + serviceRecord.shortInstanceName, e);
                                            Trace.traceEnd(j);
                                            processRecord = processRecord2;
                                            byAppZygote = hostingRecord2;
                                            if (processRecord == null) {
                                            }
                                            if (serviceRecord.fgRequired) {
                                            }
                                            if (!this.mPendingServices.contains(serviceRecord)) {
                                            }
                                            if (serviceRecord.delayedStop) {
                                            }
                                            return null;
                                        }
                                    } catch (TransactionTooLargeException e12) {
                                        throw e12;
                                    } catch (RemoteException e13) {
                                        e = e13;
                                        processRecord2 = sharedIsolatedProcess;
                                        str5 = "ActivityManager";
                                        str6 = "Exception when starting service ";
                                        j = 64;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        j4 = 64;
                                    }
                                }
                            } catch (Throwable th6) {
                                th = th6;
                            }
                        }
                        processRecord2 = sharedIsolatedProcess;
                        str3 = "ActivityManager";
                        processRecord = processRecord2;
                    } else {
                        str3 = "ActivityManager";
                        str4 = str13;
                        processRecord = serviceRecord.isolationHostProc;
                        if (WebViewZygote.isMultiprocessEnabled() && serviceRecord.serviceInfo.packageName.equals(WebViewZygote.getPackageName())) {
                            hostingRecord2 = HostingRecord.byWebviewZygote(serviceRecord.instanceName, serviceRecord.definingPackageName, serviceRecord.definingUid, serviceRecord.serviceInfo.processName);
                        }
                        ServiceInfo serviceInfo2 = serviceRecord.serviceInfo;
                        if ((serviceInfo2.flags & 8) != 0) {
                            byAppZygote = HostingRecord.byAppZygote(serviceRecord.instanceName, serviceRecord.definingPackageName, serviceRecord.definingUid, serviceInfo2.processName);
                        }
                    }
                    byAppZygote = hostingRecord2;
                }
                if (processRecord == null && !z3 && !z4) {
                    if (!serviceRecord.isSdkSandbox) {
                        startProcessLocked = this.mAm.startSdkSandboxProcessLocked(str4, serviceRecord.appInfo, true, i, byAppZygote, 0, Process.toSdkSandboxUid(serviceRecord.sdkSandboxClientAppUid), serviceRecord.sdkSandboxClientAppPackage);
                        serviceRecord.isolationHostProc = startProcessLocked;
                    } else {
                        startProcessLocked = this.mAm.startProcessLocked(str4, serviceRecord.appInfo, true, i, byAppZygote, 0, false, z6);
                    }
                    if (startProcessLocked != null) {
                        String str16 = str2 + serviceRecord.appInfo.packageName + "/" + serviceRecord.appInfo.uid + " for service " + serviceRecord.intent.getIntent() + ": process is bad";
                        Slog.w(str3, str16);
                        bringDownServiceLocked(serviceRecord, z5);
                        return str16;
                    }
                    if (z6) {
                        serviceRecord.isolationHostProc = startProcessLocked;
                    }
                }
                if (serviceRecord.fgRequired) {
                    this.mAm.tempAllowlistUidLocked(serviceRecord.appInfo.uid, r1.mConstants.mServiceStartForegroundTimeoutMs, FrameworkStatsLog.f111x2b18bc4b, "fg-service-launch", 0, serviceRecord.mRecentCallingUid);
                }
                if (!this.mPendingServices.contains(serviceRecord)) {
                    this.mPendingServices.add(serviceRecord);
                }
                if (serviceRecord.delayedStop) {
                    serviceRecord.delayedStop = false;
                    if (serviceRecord.startRequested) {
                        stopServiceLocked(serviceRecord, z5);
                    }
                }
                return null;
            }
        }
        str = null;
        ServiceInfo serviceInfo3 = serviceRecord.serviceInfo;
        if ((serviceInfo3.flags & 2) == 0) {
        }
        String str132 = serviceRecord.processName;
        ComponentName componentName2 = serviceRecord.instanceName;
        String str142 = serviceRecord.definingPackageName;
        int i22 = serviceRecord.definingUid;
        String str152 = serviceInfo3.processName;
        long hostingRecordTriggerType2 = getHostingRecordTriggerType(serviceRecord);
        long j42 = str142;
        HostingRecord hostingRecord22 = new HostingRecord("service", componentName2, j42, i22, str152, hostingRecordTriggerType2, str);
        if (z6) {
        }
        if (processRecord == null) {
            if (!serviceRecord.isSdkSandbox) {
            }
            if (startProcessLocked != null) {
            }
        }
        if (serviceRecord.fgRequired) {
        }
        if (!this.mPendingServices.contains(serviceRecord)) {
        }
        if (serviceRecord.delayedStop) {
        }
        return null;
    }

    public final String getHostingRecordTriggerType(ServiceRecord serviceRecord) {
        return ("android.permission.BIND_JOB_SERVICE".equals(serviceRecord.permission) && serviceRecord.mRecentCallingUid == 1000) ? "job" : "unknown";
    }

    public final void requestServiceBindingsLocked(ServiceRecord serviceRecord, boolean z) {
        for (int size = serviceRecord.bindings.size() - 1; size >= 0 && requestServiceBindingLocked(serviceRecord, (IntentBindRecord) serviceRecord.bindings.valueAt(size), z, false); size--) {
        }
    }

    public final void realStartServiceLocked(ServiceRecord serviceRecord, ProcessRecord processRecord, IApplicationThread iApplicationThread, int i, UidRecord uidRecord, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        if (iApplicationThread == null) {
            throw new RemoteException();
        }
        serviceRecord.setProcess(processRecord, iApplicationThread, i, uidRecord);
        long uptimeMillis = SystemClock.uptimeMillis();
        serviceRecord.lastActivity = uptimeMillis;
        serviceRecord.restartTime = uptimeMillis;
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        boolean startService = processServiceRecord.startService(serviceRecord);
        bumpServiceExecutingLocked(serviceRecord, z, "create", 0);
        this.mAm.updateLruProcessLocked(processRecord, false, null);
        updateServiceForegroundLocked(processServiceRecord, false);
        this.mAm.enqueueOomAdjTargetLocked(processRecord);
        this.mAm.updateOomAdjLocked(processRecord, 6);
        try {
            try {
                int i2 = serviceRecord.appInfo.uid;
                String packageName = serviceRecord.name.getPackageName();
                String className = serviceRecord.name.getClassName();
                FrameworkStatsLog.write(100, i2, packageName, className);
                this.mAm.mBatteryStatsService.noteServiceStartLaunch(i2, packageName, className);
                this.mAm.notifyPackageUse(serviceRecord.serviceInfo.packageName, 1);
                iApplicationThread.scheduleCreateService(serviceRecord, serviceRecord.serviceInfo, (CompatibilityInfo) null, processRecord.mState.getReportedProcState());
                serviceRecord.postNotification(false);
                if (serviceRecord.allowlistManager) {
                    processServiceRecord.mAllowlistManager = true;
                }
                requestServiceBindingsLocked(serviceRecord, z);
                updateServiceClientActivitiesLocked(processServiceRecord, null, true);
                if (startService) {
                    processServiceRecord.addBoundClientUidsOfNewService(serviceRecord);
                }
                if (serviceRecord.startRequested && serviceRecord.callStart && serviceRecord.pendingStarts.size() == 0) {
                    serviceRecord.pendingStarts.add(new ServiceRecord.StartItem(serviceRecord, false, serviceRecord.makeNextStartId(), null, null, 0, null, null, -1));
                }
                sendServiceArgsLocked(serviceRecord, z, true);
                if (serviceRecord.delayed) {
                    getServiceMapLocked(serviceRecord.userId).mDelayedStartList.remove(serviceRecord);
                    z4 = false;
                    serviceRecord.delayed = false;
                } else {
                    z4 = false;
                }
                if (serviceRecord.delayedStop) {
                    serviceRecord.delayedStop = z4;
                    if (serviceRecord.startRequested) {
                        stopServiceLocked(serviceRecord, z2);
                    }
                }
            } catch (DeadObjectException e) {
                Slog.w("ActivityManager", "Application dead when creating service " + serviceRecord);
                this.mAm.appDiedLocked(processRecord, "Died when creating service");
                throw e;
            }
        } catch (Throwable th) {
            boolean contains = this.mDestroyingServices.contains(serviceRecord);
            serviceDoneExecutingLocked(serviceRecord, contains, contains, false, 19);
            if (startService) {
                processServiceRecord.stopService(serviceRecord);
                z3 = false;
                serviceRecord.setProcess(null, null, 0, null);
            } else {
                z3 = false;
            }
            if (!contains && !this.mRestartingServices.contains(serviceRecord)) {
                scheduleServiceRestartLocked(serviceRecord, z3);
            }
            throw th;
        }
    }

    public final void sendServiceArgsLocked(ServiceRecord serviceRecord, boolean z, boolean z2) {
        int i;
        int size = serviceRecord.pendingStarts.size();
        if (size == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (serviceRecord.pendingStarts.size() <= 0) {
                break;
            }
            ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.pendingStarts.remove(0);
            if (startItem.intent != null || size <= 1) {
                startItem.deliveredTime = SystemClock.uptimeMillis();
                serviceRecord.deliveredStarts.add(startItem);
                startItem.deliveryCount++;
                NeededUriGrants neededUriGrants = startItem.neededGrants;
                if (neededUriGrants != null) {
                    this.mAm.mUgmInternal.grantUriPermissionUncheckedFromIntent(neededUriGrants, startItem.getUriPermissionsLocked());
                }
                this.mAm.grantImplicitAccess(serviceRecord.userId, startItem.intent, startItem.callingId, UserHandle.getAppId(serviceRecord.appInfo.uid));
                bumpServiceExecutingLocked(serviceRecord, z, "start", 0);
                if (serviceRecord.fgRequired && !serviceRecord.fgWaiting) {
                    if (!serviceRecord.isForeground) {
                        scheduleServiceForegroundTransitionTimeoutLocked(serviceRecord);
                    } else {
                        serviceRecord.fgRequired = false;
                    }
                }
                i = startItem.deliveryCount > 1 ? 2 : 0;
                if (startItem.doneExecutingCount > 0) {
                    i |= 1;
                }
                arrayList.add(new ServiceStartArgs(startItem.taskRemoved, startItem.f1633id, i, startItem.intent));
            }
        }
        if (!z2) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            this.mAm.updateOomAdjPendingTargetsLocked(6);
        }
        ParceledListSlice parceledListSlice = new ParceledListSlice(arrayList);
        parceledListSlice.setInlineCountLimit(4);
        try {
            serviceRecord.app.getThread().scheduleServiceArgs(serviceRecord, parceledListSlice);
            e = null;
        } catch (TransactionTooLargeException e) {
            e = e;
            Slog.w("ActivityManager", "Failed delivering service starts", e);
        } catch (RemoteException e2) {
            e = e2;
            Slog.w("ActivityManager", "Failed delivering service starts", e);
        } catch (Exception e3) {
            e = e3;
            Slog.w("ActivityManager", "Unexpected exception", e);
        }
        if (e != null) {
            boolean contains = this.mDestroyingServices.contains(serviceRecord);
            int size2 = arrayList.size();
            while (i < size2) {
                serviceDoneExecutingLocked(serviceRecord, contains, contains, true, 19);
                i++;
            }
            this.mAm.updateOomAdjPendingTargetsLocked(19);
            if (e instanceof TransactionTooLargeException) {
                throw ((TransactionTooLargeException) e);
            }
        }
    }

    public final boolean isServiceNeededLocked(ServiceRecord serviceRecord, boolean z, boolean z2) {
        if (serviceRecord.startRequested) {
            return true;
        }
        if (!z) {
            z2 = serviceRecord.hasAutoCreateConnections();
        }
        return z2;
    }

    public final void bringDownServiceIfNeededLocked(ServiceRecord serviceRecord, boolean z, boolean z2, boolean z3, String str) {
        if (isServiceNeededLocked(serviceRecord, z, z2) || this.mPendingServices.contains(serviceRecord)) {
            return;
        }
        bringDownServiceLocked(serviceRecord, z3);
    }

    public final void bringDownServiceLocked(final ServiceRecord serviceRecord, boolean z) {
        boolean z2;
        if (serviceRecord.isShortFgs()) {
            Slog.w("ActivityManager", "Short FGS brought down without stopping: " + serviceRecord);
            maybeStopShortFgsTimeoutLocked(serviceRecord);
        }
        ArrayMap connections = serviceRecord.getConnections();
        int size = connections.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            ArrayList arrayList = (ArrayList) connections.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i);
                connectionRecord.serviceDead = true;
                connectionRecord.stopAssociation();
                try {
                    connectionRecord.conn.connected(serviceRecord.name, (IBinder) null, true);
                } catch (Exception e) {
                    Slog.w("ActivityManager", "Failure disconnecting service " + serviceRecord.shortInstanceName + " to connection " + ((ConnectionRecord) arrayList.get(i)).conn.asBinder() + " (in " + ((ConnectionRecord) arrayList.get(i)).binding.client.processName + ")", e);
                }
            }
            size--;
        }
        ProcessRecord processRecord = serviceRecord.app;
        if (processRecord == null || processRecord.getThread() == null) {
            z2 = false;
        } else {
            boolean z3 = false;
            for (int size2 = serviceRecord.bindings.size() - 1; size2 >= 0; size2--) {
                IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.valueAt(size2);
                if (intentBindRecord.hasBound) {
                    try {
                        z3 |= bumpServiceExecutingLocked(serviceRecord, false, "bring down unbind", 5);
                        intentBindRecord.hasBound = false;
                        intentBindRecord.requested = false;
                        serviceRecord.app.getThread().scheduleUnbindService(serviceRecord, intentBindRecord.intent.getIntent());
                    } catch (Exception e2) {
                        Slog.w("ActivityManager", "Exception when unbinding service " + serviceRecord.shortInstanceName, e2);
                        serviceProcessGoneLocked(serviceRecord, z);
                    }
                }
            }
            z2 = z3;
        }
        if (serviceRecord.fgRequired) {
            Slog.w("ActivityManager", "Bringing down service while still waiting for start foreground: " + serviceRecord);
            serviceRecord.fgRequired = false;
            serviceRecord.fgWaiting = false;
            synchronized (this.mAm.mProcessStats.mLock) {
                ServiceState tracker = serviceRecord.getTracker();
                if (tracker != null) {
                    tracker.setForeground(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            AppOpsService appOpsService = this.mAm.mAppOpsService;
            appOpsService.finishOperation(AppOpsManager.getToken(appOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
            this.mAm.mHandler.removeMessages(66, serviceRecord);
            if (serviceRecord.app != null) {
                Message obtainMessage = this.mAm.mHandler.obtainMessage(69);
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = serviceRecord.app;
                obtain.arg2 = serviceRecord.toString();
                obtain.arg3 = serviceRecord.getComponentName();
                obtainMessage.obj = obtain;
                this.mAm.mHandler.sendMessage(obtainMessage);
            }
        }
        serviceRecord.destroyTime = SystemClock.uptimeMillis();
        ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
        ServiceRecord serviceRecord2 = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.remove(serviceRecord.instanceName);
        if (serviceRecord2 != null && serviceRecord2 != serviceRecord) {
            serviceMapLocked.mServicesByInstanceName.put(serviceRecord.instanceName, serviceRecord2);
            if (!serviceRecord.destroying) {
                throw new IllegalStateException("Bringing down " + serviceRecord + " but actually running " + serviceRecord2);
            }
        }
        serviceMapLocked.mServicesByIntent.remove(serviceRecord.intent);
        serviceRecord.totalRestartCount = 0;
        unscheduleServiceRestartLocked(serviceRecord, 0, true, "bring down");
        for (int size3 = this.mPendingServices.size() - 1; size3 >= 0; size3--) {
            if (this.mPendingServices.get(size3) == serviceRecord) {
                this.mPendingServices.remove(size3);
            }
        }
        this.mPendingBringups.remove(serviceRecord);
        cancelForegroundNotificationLocked(serviceRecord);
        boolean z4 = serviceRecord.isForeground;
        if (z4) {
            maybeStopShortFgsTimeoutLocked(serviceRecord);
            decActiveForegroundAppLocked(serviceMapLocked, serviceRecord);
            synchronized (this.mAm.mProcessStats.mLock) {
                ServiceState tracker2 = serviceRecord.getTracker();
                if (tracker2 != null) {
                    tracker2.setForeground(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            AppOpsService appOpsService2 = this.mAm.mAppOpsService;
            appOpsService2.finishOperation(AppOpsManager.getToken(appOpsService2), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
            unregisterAppOpCallbackLocked(serviceRecord);
            long uptimeMillis = SystemClock.uptimeMillis();
            serviceRecord.mFgsExitTime = uptimeMillis;
            long j = serviceRecord.mFgsEnterTime;
            logFGSStateChangeLocked(serviceRecord, 2, uptimeMillis > j ? (int) (uptimeMillis - j) : 0, 2, 0);
            synchronized (this.mFGSLogger) {
                this.mFGSLogger.logForegroundServiceStop(serviceRecord.appInfo.uid, serviceRecord);
            }
            this.mAm.updateForegroundServiceUsageStats(serviceRecord.name, serviceRecord.userId, false);
        }
        serviceRecord.isForeground = false;
        serviceRecord.mFgsNotificationWasDeferred = false;
        dropFgsNotificationStateLocked(serviceRecord);
        serviceRecord.foregroundId = 0;
        serviceRecord.foregroundNoti = null;
        resetFgsRestrictionLocked(serviceRecord);
        if (z4) {
            signalForegroundServiceObserversLocked(serviceRecord);
        }
        serviceRecord.clearDeliveredStartsLocked();
        serviceRecord.pendingStarts.clear();
        serviceMapLocked.mDelayedStartList.remove(serviceRecord);
        if (serviceRecord.app != null) {
            this.mAm.mBatteryStatsService.noteServiceStopLaunch(serviceRecord.appInfo.uid, serviceRecord.name.getPackageName(), serviceRecord.name.getClassName());
            stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
            if (serviceRecord.app.getThread() != null) {
                this.mAm.updateLruProcessLocked(serviceRecord.app, false, null);
                updateServiceForegroundLocked(serviceRecord.app.mServices, false);
                if (serviceRecord.mIsFgsDelegate) {
                    if (serviceRecord.mFgsDelegation.mConnection != null) {
                        this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActiveServices.lambda$bringDownServiceLocked$1(ServiceRecord.this);
                            }
                        });
                    }
                    int size4 = this.mFgsDelegations.size() - 1;
                    while (true) {
                        if (size4 < 0) {
                            break;
                        }
                        if (this.mFgsDelegations.valueAt(size4) == serviceRecord) {
                            this.mFgsDelegations.removeAt(size4);
                            break;
                        }
                        size4--;
                    }
                } else {
                    try {
                        z2 |= bumpServiceExecutingLocked(serviceRecord, false, "destroy", z2 ? 0 : 19);
                        this.mDestroyingServices.add(serviceRecord);
                        serviceRecord.destroying = true;
                        serviceRecord.app.getThread().scheduleStopService(serviceRecord);
                    } catch (Exception e3) {
                        Slog.w("ActivityManager", "Exception when destroying service " + serviceRecord.shortInstanceName, e3);
                        serviceProcessGoneLocked(serviceRecord, z);
                    }
                }
            }
        }
        if (!z2) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            if (!z) {
                this.mAm.updateOomAdjPendingTargetsLocked(19);
            }
        }
        if (serviceRecord.bindings.size() > 0) {
            serviceRecord.bindings.clear();
        }
        Runnable runnable = serviceRecord.restarter;
        if (runnable instanceof ServiceRestarter) {
            ((ServiceRestarter) runnable).setService(null);
        }
        synchronized (this.mAm.mProcessStats.mLock) {
            int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
            if (serviceRecord.tracker != null) {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                serviceRecord.tracker.setStarted(false, memFactorLocked, uptimeMillis2);
                serviceRecord.tracker.setBound(false, memFactorLocked, uptimeMillis2);
                if (serviceRecord.executeNesting == 0) {
                    serviceRecord.tracker.clearCurrentOwner(serviceRecord, false);
                    serviceRecord.tracker = null;
                }
            }
        }
        serviceMapLocked.ensureNotStartingBackgroundLocked(serviceRecord);
        updateNumForegroundServicesLocked();
    }

    public static /* synthetic */ void lambda$bringDownServiceLocked$1(ServiceRecord serviceRecord) {
        ForegroundServiceDelegation foregroundServiceDelegation = serviceRecord.mFgsDelegation;
        foregroundServiceDelegation.mConnection.onServiceDisconnected(foregroundServiceDelegation.mOptions.getComponentName());
    }

    public final void dropFgsNotificationStateLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.foregroundNoti == null) {
            return;
        }
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(serviceRecord.userId);
        boolean z = false;
        if (serviceMap != null) {
            int size = serviceMap.mServicesByInstanceName.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i);
                if (serviceRecord2 != serviceRecord && serviceRecord2.isForeground && serviceRecord.foregroundId == serviceRecord2.foregroundId && serviceRecord.appInfo.packageName.equals(serviceRecord2.appInfo.packageName)) {
                    z = true;
                    break;
                }
                i++;
            }
        } else {
            Slog.wtf("ActivityManager", "FGS " + serviceRecord + " not found!");
        }
        if (z) {
            return;
        }
        serviceRecord.stripForegroundServiceFlagFromNotification();
    }

    public void removeConnectionLocked(ConnectionRecord connectionRecord, ProcessRecord processRecord, ActivityServiceConnectionsHolder activityServiceConnectionsHolder, boolean z) {
        ProcessRecord processRecord2;
        IBinder asBinder = connectionRecord.conn.asBinder();
        AppBindRecord appBindRecord = connectionRecord.binding;
        ServiceRecord serviceRecord = appBindRecord.service;
        ArrayList arrayList = (ArrayList) serviceRecord.getConnections().get(asBinder);
        if (arrayList != null) {
            arrayList.remove(connectionRecord);
            if (arrayList.size() == 0) {
                serviceRecord.removeConnection(asBinder);
            }
        }
        if (appBindRecord.connections.size() > 100) {
            Slog.e("ActivityManager", "Too many connections(" + appBindRecord.connections.size() + ") owned by" + appBindRecord.client);
        }
        appBindRecord.connections.remove(connectionRecord);
        connectionRecord.stopAssociation();
        ActivityServiceConnectionsHolder activityServiceConnectionsHolder2 = connectionRecord.activity;
        if (activityServiceConnectionsHolder2 != null && activityServiceConnectionsHolder2 != activityServiceConnectionsHolder) {
            activityServiceConnectionsHolder2.removeConnection(connectionRecord);
        }
        ProcessRecord processRecord3 = appBindRecord.client;
        if (processRecord3 != processRecord) {
            ProcessServiceRecord processServiceRecord = processRecord3.mServices;
            processServiceRecord.removeConnection(connectionRecord);
            if (connectionRecord.hasFlag(8)) {
                processServiceRecord.updateHasAboveClientLocked();
            }
            if (connectionRecord.hasFlag(16777216)) {
                serviceRecord.updateAllowlistManager();
                if (!serviceRecord.allowlistManager && (processRecord2 = serviceRecord.app) != null) {
                    updateAllowlistManagerLocked(processRecord2.mServices);
                }
            }
            if (connectionRecord.hasFlag(1048576)) {
                serviceRecord.updateIsAllowedBgActivityStartsByBinding();
            }
            if (connectionRecord.hasFlag(65536)) {
                processServiceRecord.updateHasTopStartedAlmostPerceptibleServices();
            }
            ProcessRecord processRecord4 = serviceRecord.app;
            if (processRecord4 != null) {
                updateServiceClientActivitiesLocked(processRecord4.mServices, connectionRecord, true);
            }
        }
        ArrayList arrayList2 = (ArrayList) this.mServiceConnections.get(asBinder);
        if (arrayList2 != null) {
            arrayList2.remove(connectionRecord);
            if (arrayList2.size() == 0) {
                this.mServiceConnections.remove(asBinder);
            }
        }
        ActivityManagerService activityManagerService = this.mAm;
        ProcessRecord processRecord5 = appBindRecord.client;
        int i = processRecord5.uid;
        String str = processRecord5.processName;
        ApplicationInfo applicationInfo = serviceRecord.appInfo;
        activityManagerService.stopAssociationLocked(i, str, applicationInfo.uid, applicationInfo.longVersionCode, serviceRecord.instanceName, serviceRecord.processName);
        if (appBindRecord.connections.size() == 0) {
            appBindRecord.intent.apps.remove(appBindRecord.client);
        }
        if (connectionRecord.serviceDead) {
            return;
        }
        ProcessRecord processRecord6 = serviceRecord.app;
        if (processRecord6 != null && processRecord6.getThread() != null && appBindRecord.intent.apps.size() == 0 && appBindRecord.intent.hasBound) {
            try {
                bumpServiceExecutingLocked(serviceRecord, false, "unbind", 5);
                if (appBindRecord.client != serviceRecord.app && connectionRecord.notHasFlag(32) && serviceRecord.app.mState.getSetProcState() <= 13) {
                    this.mAm.updateLruProcessLocked(serviceRecord.app, false, null);
                }
                IntentBindRecord intentBindRecord = appBindRecord.intent;
                intentBindRecord.hasBound = false;
                intentBindRecord.doRebind = false;
                serviceRecord.app.getThread().scheduleUnbindService(serviceRecord, appBindRecord.intent.intent.getIntent());
            } catch (Exception e) {
                Slog.w("ActivityManager", "Exception when unbinding service " + serviceRecord.shortInstanceName, e);
                serviceProcessGoneLocked(serviceRecord, z);
            }
        }
        if (serviceRecord.getConnections().isEmpty()) {
            this.mPendingServices.remove(serviceRecord);
            this.mPendingBringups.remove(serviceRecord);
        }
        if (connectionRecord.hasFlag(1)) {
            boolean hasAutoCreateConnections = serviceRecord.hasAutoCreateConnections();
            if (!hasAutoCreateConnections && serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setBound(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            bringDownServiceIfNeededLocked(serviceRecord, true, hasAutoCreateConnections, z, "removeConnection");
        }
    }

    public void serviceDoneExecutingLocked(ServiceRecord serviceRecord, int i, int i2, int i3, boolean z) {
        boolean contains = this.mDestroyingServices.contains(serviceRecord);
        if (serviceRecord != null) {
            if (i == 1) {
                serviceRecord.callStart = true;
                if (i3 != 1000) {
                    serviceRecord.startCommandResult = i3;
                }
                if (i3 == 0 || i3 == 1) {
                    serviceRecord.findDeliveredStart(i2, false, true);
                    serviceRecord.stopIfKilled = false;
                } else if (i3 == 2) {
                    serviceRecord.findDeliveredStart(i2, false, true);
                    if (serviceRecord.getLastStartId() == i2) {
                        serviceRecord.stopIfKilled = true;
                    }
                } else if (i3 == 3) {
                    ServiceRecord.StartItem findDeliveredStart = serviceRecord.findDeliveredStart(i2, false, false);
                    if (findDeliveredStart != null) {
                        findDeliveredStart.deliveryCount = 0;
                        findDeliveredStart.doneExecutingCount++;
                        serviceRecord.stopIfKilled = true;
                    }
                } else if (i3 == 1000) {
                    serviceRecord.findDeliveredStart(i2, true, true);
                } else {
                    throw new IllegalArgumentException("Unknown service start result: " + i3);
                }
                if (i3 == 0) {
                    serviceRecord.callStart = false;
                }
            } else if (i == 2) {
                if (!contains) {
                    if (serviceRecord.app != null) {
                        Slog.w("ActivityManager", "Service done with onDestroy, but not inDestroying: " + serviceRecord + ", app=" + serviceRecord.app);
                    }
                } else if (serviceRecord.executeNesting != 1) {
                    Slog.w("ActivityManager", "Service done with onDestroy, but executeNesting=" + serviceRecord.executeNesting + ": " + serviceRecord);
                    serviceRecord.executeNesting = 1;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            serviceDoneExecutingLocked(serviceRecord, contains, contains, z, 20);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return;
        }
        Slog.w("ActivityManager", "Done executing unknown service from pid " + Binder.getCallingPid());
    }

    public final void serviceProcessGoneLocked(ServiceRecord serviceRecord, boolean z) {
        if (serviceRecord.tracker != null) {
            synchronized (this.mAm.mProcessStats.mLock) {
                int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                long uptimeMillis = SystemClock.uptimeMillis();
                serviceRecord.tracker.setExecuting(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setForeground(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setBound(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setStarted(false, memFactorLocked, uptimeMillis);
            }
        }
        serviceDoneExecutingLocked(serviceRecord, true, true, z, 12);
    }

    public final void serviceDoneExecutingLocked(ServiceRecord serviceRecord, boolean z, boolean z2, boolean z3, int i) {
        int i2 = serviceRecord.executeNesting - 1;
        serviceRecord.executeNesting = i2;
        if (i2 <= 0) {
            ProcessRecord processRecord = serviceRecord.app;
            if (processRecord != null) {
                ProcessServiceRecord processServiceRecord = processRecord.mServices;
                processServiceRecord.setExecServicesFg(false);
                processServiceRecord.stopExecutingService(serviceRecord);
                if (processServiceRecord.numberOfExecutingServices() == 0) {
                    this.mAm.mHandler.removeMessages(12, serviceRecord.app);
                } else if (serviceRecord.executeFg) {
                    int numberOfExecutingServices = processServiceRecord.numberOfExecutingServices() - 1;
                    while (true) {
                        if (numberOfExecutingServices < 0) {
                            break;
                        }
                        if (processServiceRecord.getExecutingServiceAt(numberOfExecutingServices).executeFg) {
                            processServiceRecord.setExecServicesFg(true);
                            break;
                        }
                        numberOfExecutingServices--;
                    }
                }
                if (z) {
                    this.mDestroyingServices.remove(serviceRecord);
                    serviceRecord.bindings.clear();
                }
                if (z3) {
                    this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
                } else {
                    this.mAm.updateOomAdjLocked(serviceRecord.app, i);
                }
            }
            serviceRecord.executeFg = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    serviceRecord.tracker.setExecuting(false, memFactorLocked, uptimeMillis);
                    serviceRecord.tracker.setForeground(false, memFactorLocked, uptimeMillis);
                    if (z2) {
                        serviceRecord.tracker.clearCurrentOwner(serviceRecord, false);
                        serviceRecord.tracker = null;
                    }
                }
            }
            if (z2) {
                ProcessRecord processRecord2 = serviceRecord.app;
                if (processRecord2 != null && !processRecord2.isPersistent()) {
                    stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
                }
                serviceRecord.setProcess(null, null, 0, null);
            }
        }
    }

    public boolean attachApplicationLocked(ProcessRecord processRecord, String str) {
        boolean z;
        ServiceRecord serviceRecord;
        long j;
        processRecord.mState.setBackgroundRestricted(appRestrictedAnyInBackground(processRecord.uid, processRecord.info.packageName));
        if (this.mPendingServices.size() > 0) {
            ServiceRecord serviceRecord2 = null;
            int i = 0;
            z = false;
            while (i < this.mPendingServices.size()) {
                try {
                    serviceRecord = (ServiceRecord) this.mPendingServices.get(i);
                } catch (RemoteException e) {
                    e = e;
                }
                try {
                    if (processRecord == serviceRecord.isolationHostProc || (processRecord.uid == serviceRecord.appInfo.uid && str.equals(serviceRecord.processName))) {
                        IApplicationThread thread = processRecord.getThread();
                        int pid = processRecord.getPid();
                        UidRecord uidRecord = processRecord.getUidRecord();
                        this.mPendingServices.remove(i);
                        int i2 = i - 1;
                        ApplicationInfo applicationInfo = serviceRecord.appInfo;
                        processRecord.addPackage(applicationInfo.packageName, applicationInfo.longVersionCode, this.mAm.mProcessStats);
                        try {
                            if (Trace.isTagEnabled(64L)) {
                                Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord.shortInstanceName);
                            }
                            j = 64;
                            try {
                                realStartServiceLocked(serviceRecord, processRecord, thread, pid, uidRecord, serviceRecord.createdFromFg, true);
                                Trace.traceEnd(64L);
                                if (!isServiceNeededLocked(serviceRecord, false, false)) {
                                    bringDownServiceLocked(serviceRecord, true);
                                }
                                this.mAm.updateOomAdjPendingTargetsLocked(6);
                                z = true;
                                i = i2;
                            } catch (Throwable th) {
                                th = th;
                                Trace.traceEnd(j);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            j = 64;
                        }
                    }
                    i++;
                    serviceRecord2 = serviceRecord;
                } catch (RemoteException e2) {
                    e = e2;
                    serviceRecord2 = serviceRecord;
                    Slog.w("ActivityManager", "Exception in new application when starting service " + serviceRecord2.shortInstanceName, e);
                    throw e;
                }
            }
        } else {
            z = false;
        }
        if (this.mRestartingServices.size() > 0) {
            boolean z2 = false;
            for (int i3 = 0; i3 < this.mRestartingServices.size(); i3++) {
                ServiceRecord serviceRecord3 = (ServiceRecord) this.mRestartingServices.get(i3);
                if (processRecord == serviceRecord3.isolationHostProc || (processRecord.uid == serviceRecord3.appInfo.uid && str.equals(serviceRecord3.processName))) {
                    this.mAm.mHandler.removeCallbacks(serviceRecord3.restarter);
                    this.mAm.mHandler.post(serviceRecord3.restarter);
                    z2 = true;
                }
            }
            if (z2) {
                this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActiveServices.this.lambda$attachApplicationLocked$2();
                    }
                });
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachApplicationLocked$2() {
        long uptimeMillis = SystemClock.uptimeMillis();
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                rescheduleServiceRestartIfPossibleLocked(getExtraRestartTimeInBetweenLocked(), this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN, "other", uptimeMillis);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void processStartTimedOutLocked(ProcessRecord processRecord) {
        int size = this.mPendingServices.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingServices.get(i);
            if ("com.android.systemui".equals(serviceRecord.processName)) {
                String str = "Do not bringing down SystemUI services : " + serviceRecord;
                Slog.wtf("ActivityManager", str);
                this.mRevivalServicesMessages.add(DATE_FORMATTER.format(Long.valueOf(System.currentTimeMillis())) + " " + str);
            } else if ((processRecord.uid == serviceRecord.appInfo.uid && processRecord.processName.equals(serviceRecord.processName)) || serviceRecord.isolationHostProc == processRecord) {
                Slog.w("ActivityManager", "Forcing bringing down service: " + serviceRecord);
                serviceRecord.isolationHostProc = null;
                this.mPendingServices.remove(i);
                size = this.mPendingServices.size();
                i--;
                bringDownServiceLocked(serviceRecord, true);
                z = true;
            }
            i++;
        }
        if (z) {
            this.mAm.updateOomAdjPendingTargetsLocked(12);
        }
    }

    public final boolean collectPackageServicesLocked(String str, Set set, boolean z, boolean z2, ArrayMap arrayMap) {
        ProcessRecord processRecord;
        boolean z3 = false;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(size);
            if ((str == null || (serviceRecord.packageName.equals(str) && (set == null || set.contains(serviceRecord.name.getClassName())))) && ((processRecord = serviceRecord.app) == null || z || !processRecord.isPersistent())) {
                if (!z2) {
                    return true;
                }
                Slog.i("ActivityManager", "  Force stopping service " + serviceRecord);
                ProcessRecord processRecord2 = serviceRecord.app;
                if (processRecord2 != null && !processRecord2.isPersistent()) {
                    stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
                }
                ProcessRecord processRecord3 = serviceRecord.app;
                if (processRecord3 != null) {
                    this.mAm.mHandler.removeMessages(12, processRecord3);
                }
                serviceRecord.setProcess(null, null, 0, null);
                serviceRecord.isolationHostProc = null;
                if (this.mTmpCollectionResults == null) {
                    this.mTmpCollectionResults = new ArrayList();
                }
                this.mTmpCollectionResults.add(serviceRecord);
                z3 = true;
            }
        }
        return z3;
    }

    public boolean bringDownDisabledPackageServicesLocked(String str, Set set, int i, boolean z, boolean z2, boolean z3) {
        ArrayList arrayList = this.mTmpCollectionResults;
        if (arrayList != null) {
            arrayList.clear();
        }
        if (i == -1) {
            for (int size = this.mServiceMap.size() - 1; size >= 0; size--) {
                r2 |= collectPackageServicesLocked(str, set, z, z3, ((ServiceMap) this.mServiceMap.valueAt(size)).mServicesByInstanceName);
                if (!z3 && r2) {
                    return true;
                }
                if (z3 && set == null) {
                    forceStopPackageLocked(str, ((ServiceMap) this.mServiceMap.valueAt(size)).mUserId);
                }
            }
        } else {
            ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
            r2 = serviceMap != null ? collectPackageServicesLocked(str, set, z, z3, serviceMap.mServicesByInstanceName) : false;
            if (z3 && set == null) {
                forceStopPackageLocked(str, i);
            }
        }
        ArrayList arrayList2 = this.mTmpCollectionResults;
        if (arrayList2 != null) {
            int size2 = arrayList2.size();
            for (int i2 = size2 - 1; i2 >= 0; i2--) {
                bringDownServiceLocked((ServiceRecord) this.mTmpCollectionResults.get(i2), true);
            }
            if (size2 > 0) {
                this.mAm.updateOomAdjPendingTargetsLocked(22);
            }
            if (z2 && !this.mTmpCollectionResults.isEmpty()) {
                final ArrayList arrayList3 = (ArrayList) this.mTmpCollectionResults.clone();
                this.mAm.mHandler.postDelayed(new Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActiveServices.lambda$bringDownDisabledPackageServicesLocked$3(arrayList3);
                    }
                }, 250L);
            }
            this.mTmpCollectionResults.clear();
        }
        return r2;
    }

    public static /* synthetic */ void lambda$bringDownDisabledPackageServicesLocked$3(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((ServiceRecord) arrayList.get(i)).cancelNotification();
        }
    }

    public final void signalForegroundServiceObserversLocked(ServiceRecord serviceRecord) {
        int beginBroadcast = this.mFgsObservers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mFgsObservers.getBroadcastItem(i).onForegroundStateChanged(serviceRecord, serviceRecord.appInfo.packageName, serviceRecord.userId, serviceRecord.isForeground);
            } catch (RemoteException unused) {
            }
        }
        this.mFgsObservers.finishBroadcast();
    }

    public boolean registerForegroundServiceObserverLocked(int i, IForegroundServiceObserver iForegroundServiceObserver) {
        try {
            int size = this.mServiceMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceMap serviceMap = (ServiceMap) this.mServiceMap.valueAt(i2);
                if (serviceMap != null) {
                    int size2 = serviceMap.mServicesByInstanceName.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i3);
                        if (serviceRecord.isForeground) {
                            ApplicationInfo applicationInfo = serviceRecord.appInfo;
                            if (i == applicationInfo.uid) {
                                iForegroundServiceObserver.onForegroundStateChanged(serviceRecord, applicationInfo.packageName, serviceRecord.userId, true);
                            }
                        }
                    }
                }
            }
            this.mFgsObservers.register(iForegroundServiceObserver);
            return true;
        } catch (RemoteException unused) {
            Slog.e("ActivityManager", "Bad FGS observer from uid " + i);
            return false;
        }
    }

    public void forceStopPackageLocked(String str, int i) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        if (serviceMap != null && serviceMap.mActiveForegroundApps.size() > 0) {
            for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                if (((ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size)).mPackageName.equals(str)) {
                    serviceMap.mActiveForegroundApps.removeAt(size);
                    serviceMap.mActiveForegroundAppsChanged = true;
                }
            }
            if (serviceMap.mActiveForegroundAppsChanged) {
                requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
            }
        }
        for (int size2 = this.mPendingBringups.size() - 1; size2 >= 0; size2--) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingBringups.keyAt(size2);
            if (TextUtils.equals(serviceRecord.packageName, str) && serviceRecord.userId == i) {
                this.mPendingBringups.removeAt(size2);
            }
        }
        removeServiceRestartBackoffEnabledLocked(str);
        removeServiceNotificationDeferralsLocked(str, i);
    }

    public void cleanUpServices(int i, ComponentName componentName, Intent intent) {
        ArrayList arrayList = new ArrayList();
        ArrayMap servicesLocked = getServicesLocked(i);
        boolean z = true;
        for (int size = servicesLocked.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = (ServiceRecord) servicesLocked.valueAt(size);
            if (serviceRecord.packageName.equals(componentName.getPackageName())) {
                arrayList.add(serviceRecord);
            }
        }
        boolean z2 = false;
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            ServiceRecord serviceRecord2 = (ServiceRecord) arrayList.get(size2);
            if (serviceRecord2.startRequested) {
                if ((serviceRecord2.serviceInfo.flags & (z ? 1 : 0)) != 0) {
                    Slog.i("ActivityManager", "Stopping service " + serviceRecord2.shortInstanceName + ": remove task");
                    stopServiceLocked(serviceRecord2, z);
                    z2 = z ? 1 : 0;
                } else {
                    serviceRecord2.pendingStarts.add(new ServiceRecord.StartItem(serviceRecord2, true, serviceRecord2.getLastStartId(), intent, null, 0, null, null, -1));
                    ProcessRecord processRecord = serviceRecord2.app;
                    if (processRecord == null || processRecord.getThread() == null) {
                        z = true;
                    } else {
                        z = true;
                        z = true;
                        try {
                            sendServiceArgsLocked(serviceRecord2, true, false);
                        } catch (TransactionTooLargeException unused) {
                        }
                    }
                }
            }
        }
        if (z2) {
            this.mAm.updateOomAdjPendingTargetsLocked(17);
        }
    }

    public final void killServicesLocked(ProcessRecord processRecord, boolean z, boolean z2) {
        boolean z3;
        SemEmergencyManager semEmergencyManager;
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        for (int numberOfConnections = processServiceRecord.numberOfConnections() - 1; numberOfConnections >= 0; numberOfConnections--) {
            removeConnectionLocked(processServiceRecord.getConnectionAt(numberOfConnections), processRecord, null, true);
        }
        updateServiceConnectionActivitiesLocked(processServiceRecord);
        processServiceRecord.removeAllConnections();
        processServiceRecord.mAllowlistManager = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            ServiceRecord runningServiceAt = processServiceRecord.getRunningServiceAt(numberOfRunningServices);
            this.mAm.mBatteryStatsService.noteServiceStopLaunch(runningServiceAt.appInfo.uid, runningServiceAt.name.getPackageName(), runningServiceAt.name.getClassName());
            ProcessRecord processRecord2 = runningServiceAt.app;
            if (processRecord2 != processRecord && processRecord2 != null && !processRecord2.isPersistent()) {
                runningServiceAt.app.mServices.stopService(runningServiceAt);
                runningServiceAt.app.mServices.updateBoundClientUids();
            }
            runningServiceAt.setProcess(null, null, 0, null);
            runningServiceAt.isolationHostProc = null;
            runningServiceAt.executeNesting = 0;
            synchronized (this.mAm.mProcessStats.mLock) {
                runningServiceAt.forceClearTracker();
            }
            this.mDestroyingServices.remove(runningServiceAt);
            for (int size = runningServiceAt.bindings.size() - 1; size >= 0; size--) {
                IntentBindRecord intentBindRecord = (IntentBindRecord) runningServiceAt.bindings.valueAt(size);
                intentBindRecord.binder = null;
                intentBindRecord.hasBound = false;
                intentBindRecord.received = false;
                intentBindRecord.requested = false;
                for (int size2 = intentBindRecord.apps.size() - 1; size2 >= 0; size2--) {
                    ProcessRecord processRecord3 = (ProcessRecord) intentBindRecord.apps.keyAt(size2);
                    if (!processRecord3.isKilledByAm() && processRecord3.getThread() != null) {
                        AppBindRecord appBindRecord = (AppBindRecord) intentBindRecord.apps.valueAt(size2);
                        for (int size3 = appBindRecord.connections.size() - 1; size3 >= 0; size3--) {
                            ConnectionRecord connectionRecord = (ConnectionRecord) appBindRecord.connections.valueAt(size3);
                            if (!connectionRecord.hasFlag(1) || !connectionRecord.notHasFlag(48)) {
                            }
                        }
                    }
                }
            }
        }
        if (!CoreRune.EM_SUPPORTED || (semEmergencyManager = SemEmergencyManager.getInstance(this.mAm.mContext)) == null || !semEmergencyManager.checkInvalidProcess(processRecord.info.packageName) || processRecord.isPersistent()) {
            z3 = z;
        } else {
            Elog.d("ActivityManager", "Skip scheduling service: original allowRestart[" + z + "] name=" + processRecord.info.packageName + "/" + processRecord.processName + " uid=" + processRecord.userId + " pid=" + processRecord.getPid());
            z3 = false;
        }
        ServiceMap serviceMapLocked = getServiceMapLocked(processRecord.userId);
        for (int numberOfRunningServices2 = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices2 >= 0; numberOfRunningServices2--) {
            ServiceRecord runningServiceAt2 = processServiceRecord.getRunningServiceAt(numberOfRunningServices2);
            if (!processRecord.isPersistent()) {
                processServiceRecord.stopService(runningServiceAt2);
                processServiceRecord.updateBoundClientUids();
            }
            ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.get(runningServiceAt2.instanceName);
            if (serviceRecord != runningServiceAt2) {
                if (serviceRecord != null) {
                    Slog.wtf("ActivityManager", "Service " + runningServiceAt2 + " in process " + processRecord + " not same as in map: " + serviceRecord);
                }
            } else if (z3 && runningServiceAt2.crashCount >= this.mAm.mConstants.BOUND_SERVICE_MAX_CRASH_RETRY && (runningServiceAt2.serviceInfo.applicationInfo.flags & 8) == 0) {
                Slog.w("ActivityManager", "Service crashed " + runningServiceAt2.crashCount + " times, stopping: " + runningServiceAt2);
                Object[] objArr = new Object[4];
                objArr[0] = Integer.valueOf(runningServiceAt2.userId);
                objArr[1] = Integer.valueOf(runningServiceAt2.crashCount);
                objArr[2] = runningServiceAt2.shortInstanceName;
                ProcessRecord processRecord4 = runningServiceAt2.app;
                objArr[3] = Integer.valueOf(processRecord4 != null ? processRecord4.getPid() : -1);
                EventLog.writeEvent(30034, objArr);
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (!z3 || !this.mAm.mUserController.isUserRunning(runningServiceAt2.userId, 0)) {
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (z2) {
                if (!FreecessController.getInstance().shouldDropServiceRestart(processRecord.info.packageName, processRecord.userId)) {
                    this.mAm.pendingScheduleServiceRestart(processRecord.uid, runningServiceAt2);
                }
            } else {
                tryScheduleServiceRestartLocked(runningServiceAt2);
            }
        }
        this.mAm.updateOomAdjPendingTargetsLocked(19);
        if (!z3 || z2) {
            processServiceRecord.stopAllServices();
            processServiceRecord.clearBoundClientUids();
            for (int size4 = this.mRestartingServices.size() - 1; size4 >= 0; size4--) {
                ServiceRecord serviceRecord2 = (ServiceRecord) this.mRestartingServices.get(size4);
                if (serviceRecord2.processName.equals(processRecord.processName) && serviceRecord2.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mRestartingServices.remove(size4);
                    clearRestartingIfNeededLocked(serviceRecord2);
                }
            }
            for (int size5 = this.mPendingServices.size() - 1; size5 >= 0; size5--) {
                ServiceRecord serviceRecord3 = (ServiceRecord) this.mPendingServices.get(size5);
                if (serviceRecord3.processName.equals(processRecord.processName) && serviceRecord3.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mPendingServices.remove(size5);
                }
            }
            for (int size6 = this.mPendingBringups.size() - 1; size6 >= 0; size6--) {
                ServiceRecord serviceRecord4 = (ServiceRecord) this.mPendingBringups.keyAt(size6);
                if (serviceRecord4.processName.equals(processRecord.processName) && serviceRecord4.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mPendingBringups.removeAt(size6);
                }
            }
        }
        int size7 = this.mDestroyingServices.size();
        while (size7 > 0) {
            size7--;
            ServiceRecord serviceRecord5 = (ServiceRecord) this.mDestroyingServices.get(size7);
            if (serviceRecord5.app == processRecord) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord5.forceClearTracker();
                }
                this.mDestroyingServices.remove(size7);
            }
        }
        processServiceRecord.stopAllExecutingServices();
    }

    public final void tryScheduleServiceRestartLocked(ServiceRecord serviceRecord) {
        if (!scheduleServiceRestartLocked(serviceRecord, true)) {
            bringDownServiceLocked(serviceRecord, true);
            return;
        }
        if (serviceRecord.canStopIfKilled(false)) {
            serviceRecord.startRequested = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
        }
    }

    public ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked(ServiceRecord serviceRecord) {
        ActivityManager.RunningServiceInfo runningServiceInfo = new ActivityManager.RunningServiceInfo();
        runningServiceInfo.service = serviceRecord.name;
        ProcessRecord processRecord = serviceRecord.app;
        if (processRecord != null) {
            runningServiceInfo.pid = processRecord.getPid();
        }
        runningServiceInfo.uid = serviceRecord.appInfo.uid;
        runningServiceInfo.process = serviceRecord.processName;
        runningServiceInfo.foreground = serviceRecord.isForeground;
        runningServiceInfo.activeSince = serviceRecord.createRealTime;
        runningServiceInfo.started = serviceRecord.startRequested;
        runningServiceInfo.clientCount = serviceRecord.getConnections().size();
        runningServiceInfo.crashCount = serviceRecord.crashCount;
        runningServiceInfo.lastActivityTime = serviceRecord.lastActivity;
        if (serviceRecord.isForeground) {
            runningServiceInfo.flags |= 2;
        }
        if (serviceRecord.startRequested) {
            runningServiceInfo.flags |= 1;
        }
        ProcessRecord processRecord2 = serviceRecord.app;
        if (processRecord2 != null && processRecord2.getPid() == ActivityManagerService.MY_PID) {
            runningServiceInfo.flags |= 4;
        }
        ProcessRecord processRecord3 = serviceRecord.app;
        if (processRecord3 != null && processRecord3.isPersistent()) {
            runningServiceInfo.flags |= 8;
        }
        ArrayMap connections = serviceRecord.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) connections.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i);
                if (connectionRecord.clientLabel != 0) {
                    runningServiceInfo.clientPackage = connectionRecord.binding.client.info.packageName;
                    runningServiceInfo.clientLabel = connectionRecord.clientLabel;
                    return runningServiceInfo;
                }
            }
        }
        return runningServiceInfo;
    }

    public List getRunningServiceInfoLocked(int i, int i2, int i3, boolean z, boolean z2) {
        ProcessRecord processRecord;
        ProcessRecord processRecord2;
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i4 = 0;
        try {
            if (z2) {
                int[] users = this.mAm.mUserController.getUsers();
                for (int i5 = 0; i5 < users.length && arrayList.size() < i; i5++) {
                    ArrayMap servicesLocked = getServicesLocked(users[i5]);
                    for (int i6 = 0; i6 < servicesLocked.size() && arrayList.size() < i; i6++) {
                        arrayList.add(makeRunningServiceInfoLocked((ServiceRecord) servicesLocked.valueAt(i6)));
                    }
                }
                while (i4 < this.mRestartingServices.size() && arrayList.size() < i) {
                    ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i4);
                    ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked = makeRunningServiceInfoLocked(serviceRecord);
                    makeRunningServiceInfoLocked.restarting = serviceRecord.nextRestartTime;
                    arrayList.add(makeRunningServiceInfoLocked);
                    i4++;
                }
            } else {
                int userId = UserHandle.getUserId(i3);
                ArrayMap servicesLocked2 = getServicesLocked(userId);
                for (int i7 = 0; i7 < servicesLocked2.size() && arrayList.size() < i; i7++) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) servicesLocked2.valueAt(i7);
                    if (z || ((processRecord2 = serviceRecord2.app) != null && processRecord2.uid == i3)) {
                        arrayList.add(makeRunningServiceInfoLocked(serviceRecord2));
                    }
                }
                while (i4 < this.mRestartingServices.size() && arrayList.size() < i) {
                    ServiceRecord serviceRecord3 = (ServiceRecord) this.mRestartingServices.get(i4);
                    if (serviceRecord3.userId == userId && (z || ((processRecord = serviceRecord3.app) != null && processRecord.uid == i3))) {
                        ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked2 = makeRunningServiceInfoLocked(serviceRecord3);
                        makeRunningServiceInfoLocked2.restarting = serviceRecord3.nextRestartTime;
                        arrayList.add(makeRunningServiceInfoLocked2);
                    }
                    i4++;
                }
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public PendingIntent getRunningServiceControlPanelLocked(ComponentName componentName) {
        ServiceRecord serviceByNameLocked = getServiceByNameLocked(componentName, UserHandle.getUserId(Binder.getCallingUid()));
        if (serviceByNameLocked == null) {
            return null;
        }
        ArrayMap connections = serviceByNameLocked.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) connections.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                if (((ConnectionRecord) arrayList.get(i)).clientIntent != null) {
                    return ((ConnectionRecord) arrayList.get(i)).clientIntent;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Finally extract failed */
    public void serviceTimeout(ProcessRecord processRecord) {
        long j;
        TimeoutRecord timeoutRecord;
        ServiceRecord serviceRecord;
        long j2;
        try {
            Trace.traceBegin(64L, "serviceTimeout()");
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (!processRecord.isDebugging()) {
                        ProcessServiceRecord processServiceRecord = processRecord.mServices;
                        if (processServiceRecord.numberOfExecutingServices() != 0 && processRecord.getThread() != null && !processRecord.isKilled()) {
                            long uptimeMillis = SystemClock.uptimeMillis();
                            if (processServiceRecord.shouldExecServicesFg()) {
                                j = this.mAm.mConstants.SERVICE_TIMEOUT;
                            } else {
                                j = this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT;
                            }
                            long j3 = uptimeMillis - j;
                            int numberOfExecutingServices = processServiceRecord.numberOfExecutingServices() - 1;
                            long j4 = 0;
                            while (true) {
                                timeoutRecord = null;
                                if (numberOfExecutingServices < 0) {
                                    serviceRecord = null;
                                    break;
                                }
                                serviceRecord = processServiceRecord.getExecutingServiceAt(numberOfExecutingServices);
                                long j5 = serviceRecord.executingStart;
                                if (j5 < j3) {
                                    break;
                                }
                                if (j5 > j4) {
                                    j4 = j5;
                                }
                                numberOfExecutingServices--;
                            }
                            if (serviceRecord != null && this.mAm.mProcessList.isInLruListLOSP(processRecord)) {
                                Slog.w("ActivityManager", "Timeout executing service: " + serviceRecord);
                                StringWriter stringWriter = new StringWriter();
                                FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 1024);
                                fastPrintWriter.println(serviceRecord);
                                serviceRecord.dump((PrintWriter) fastPrintWriter, "    ");
                                fastPrintWriter.close();
                                this.mLastAnrDump = stringWriter.toString();
                                this.mAm.mHandler.removeCallbacks(this.mLastAnrDumpClearer);
                                this.mAm.mHandler.postDelayed(this.mLastAnrDumpClearer, 7200000L);
                                timeoutRecord = TimeoutRecord.forServiceExec("executing service " + serviceRecord.shortInstanceName);
                            } else {
                                Message obtainMessage = this.mAm.mHandler.obtainMessage(12);
                                obtainMessage.obj = processRecord;
                                ActivityManagerService.MainHandler mainHandler = this.mAm.mHandler;
                                if (processServiceRecord.shouldExecServicesFg()) {
                                    j2 = this.mAm.mConstants.SERVICE_TIMEOUT;
                                } else {
                                    j2 = this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT;
                                }
                                mainHandler.sendMessageAtTime(obtainMessage, j4 + j2);
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            if (timeoutRecord != null) {
                                this.mAm.mAnrHelper.appNotResponding(processRecord, timeoutRecord);
                            }
                            return;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    /* JADX WARN: Finally extract failed */
    public void serviceForegroundTimeout(ServiceRecord serviceRecord) {
        try {
            Trace.traceBegin(64L, "serviceForegroundTimeout()");
            TimeoutRecord forServiceStartWithEndTime = TimeoutRecord.forServiceStartWithEndTime("Context.startForegroundService() did not then call Service.startForeground(): " + serviceRecord, SystemClock.uptimeMillis());
            forServiceStartWithEndTime.mLatencyTracker.waitingOnAMSLockStarted();
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    forServiceStartWithEndTime.mLatencyTracker.waitingOnAMSLockEnded();
                    if (serviceRecord.fgRequired && serviceRecord.fgWaiting && !serviceRecord.destroying) {
                        ProcessRecord processRecord = serviceRecord.app;
                        if (processRecord == null || !processRecord.isDebugging()) {
                            serviceRecord.fgWaiting = false;
                            stopServiceLocked(serviceRecord, false);
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            if (processRecord != null) {
                                Message obtainMessage = this.mAm.mHandler.obtainMessage(67);
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = processRecord;
                                obtain.arg2 = forServiceStartWithEndTime;
                                obtainMessage.obj = obtain;
                                this.mAm.mHandler.sendMessageDelayed(obtainMessage, r6.mConstants.mServiceStartForegroundAnrDelayMs);
                            }
                            return;
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public void serviceForegroundTimeoutANR(ProcessRecord processRecord, TimeoutRecord timeoutRecord) {
        this.mAm.mAnrHelper.appNotResponding(processRecord, timeoutRecord);
    }

    public void updateServiceApplicationInfoLocked(ApplicationInfo applicationInfo) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(UserHandle.getUserId(applicationInfo.uid));
        if (serviceMap != null) {
            ArrayMap arrayMap = serviceMap.mServicesByInstanceName;
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(size);
                if (applicationInfo.packageName.equals(serviceRecord.appInfo.packageName)) {
                    serviceRecord.appInfo = applicationInfo;
                    serviceRecord.serviceInfo.applicationInfo = applicationInfo;
                }
            }
        }
    }

    public void serviceForegroundCrash(ProcessRecord processRecord, String str, ComponentName componentName) {
        this.mAm.crashApplicationWithTypeWithExtras(processRecord.uid, processRecord.getPid(), processRecord.info.packageName, processRecord.userId, "Context.startForegroundService() did not then call Service.startForeground(): " + str, false, 1, RemoteServiceException.ForegroundServiceDidNotStartInTimeException.createExtrasForService(componentName));
    }

    public void scheduleServiceTimeoutLocked(ProcessRecord processRecord) {
        if (processRecord.mServices.numberOfExecutingServices() == 0 || processRecord.getThread() == null) {
            return;
        }
        Message obtainMessage = this.mAm.mHandler.obtainMessage(12);
        obtainMessage.obj = processRecord;
        this.mAm.mHandler.sendMessageDelayed(obtainMessage, processRecord.mServices.shouldExecServicesFg() ? this.mAm.mConstants.SERVICE_TIMEOUT : this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT);
    }

    public void scheduleServiceForegroundTransitionTimeoutLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.app.mServices.numberOfExecutingServices() == 0 || serviceRecord.app.getThread() == null) {
            return;
        }
        Message obtainMessage = this.mAm.mHandler.obtainMessage(66);
        obtainMessage.obj = serviceRecord;
        serviceRecord.fgWaiting = true;
        this.mAm.mHandler.sendMessageDelayed(obtainMessage, r3.mConstants.mServiceStartForegroundTimeoutMs);
    }

    public final class ServiceDumper {
        public final String[] args;
        public final boolean dumpAll;
        public final String dumpPackage;

        /* renamed from: fd */
        public final FileDescriptor f1627fd;
        public final ActivityManagerService.ItemMatcher matcher;

        /* renamed from: pw */
        public final PrintWriter f1628pw;
        public final ArrayList services = new ArrayList();
        public final long nowReal = SystemClock.elapsedRealtime();
        public boolean needSep = false;
        public boolean printedAnything = false;
        public boolean printed = false;

        public ServiceDumper(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, int i, boolean z, String str) {
            this.f1627fd = fileDescriptor;
            this.f1628pw = printWriter;
            this.args = strArr;
            this.dumpAll = z;
            this.dumpPackage = str;
            ActivityManagerService.ItemMatcher itemMatcher = new ActivityManagerService.ItemMatcher();
            this.matcher = itemMatcher;
            itemMatcher.build(strArr, i);
            for (int i2 : ActiveServices.this.mAm.mUserController.getUsers()) {
                ServiceMap serviceMapLocked = ActiveServices.this.getServiceMapLocked(i2);
                if (serviceMapLocked.mServicesByInstanceName.size() > 0) {
                    for (int i3 = 0; i3 < serviceMapLocked.mServicesByInstanceName.size(); i3++) {
                        ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.valueAt(i3);
                        if (this.matcher.match(serviceRecord, serviceRecord.name) && (str == null || str.equals(serviceRecord.appInfo.packageName))) {
                            this.services.add(serviceRecord);
                        }
                    }
                }
            }
        }

        public final void dumpHeaderLocked() {
            this.f1628pw.println("ACTIVITY MANAGER SERVICES (dumpsys activity services)");
            if (ActiveServices.this.mLastAnrDump != null) {
                this.f1628pw.println("  Last ANR service:");
                this.f1628pw.print(ActiveServices.this.mLastAnrDump);
                this.f1628pw.println();
            }
        }

        public void dumpLocked() {
            dumpHeaderLocked();
            try {
                for (int i : ActiveServices.this.mAm.mUserController.getUsers()) {
                    int i2 = 0;
                    while (i2 < this.services.size() && ((ServiceRecord) this.services.get(i2)).userId != i) {
                        i2++;
                    }
                    this.printed = false;
                    if (i2 < this.services.size()) {
                        this.needSep = false;
                        while (i2 < this.services.size()) {
                            ServiceRecord serviceRecord = (ServiceRecord) this.services.get(i2);
                            i2++;
                            if (serviceRecord.userId != i) {
                                break;
                            } else {
                                dumpServiceLocalLocked(serviceRecord);
                            }
                        }
                        this.needSep |= this.printed;
                    }
                    dumpUserRemainsLocked(i);
                }
            } catch (Exception e) {
                Slog.w("ActivityManager", "Exception in dumpServicesLocked", e);
            }
            dumpRemainsLocked();
        }

        public void dumpWithClient() {
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    dumpHeaderLocked();
                } finally {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            try {
                for (int i : ActiveServices.this.mAm.mUserController.getUsers()) {
                    int i2 = 0;
                    while (i2 < this.services.size() && ((ServiceRecord) this.services.get(i2)).userId != i) {
                        i2++;
                    }
                    this.printed = false;
                    if (i2 < this.services.size()) {
                        this.needSep = false;
                        while (i2 < this.services.size()) {
                            ServiceRecord serviceRecord = (ServiceRecord) this.services.get(i2);
                            i2++;
                            if (serviceRecord.userId != i) {
                                break;
                            }
                            ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService2) {
                                try {
                                    dumpServiceLocalLocked(serviceRecord);
                                } finally {
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            dumpServiceClient(serviceRecord);
                        }
                        this.needSep |= this.printed;
                    }
                    ActivityManagerService activityManagerService3 = ActiveServices.this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService3) {
                        try {
                            dumpUserRemainsLocked(i);
                        } finally {
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Exception e) {
                Slog.w("ActivityManager", "Exception in dumpServicesLocked", e);
            }
            ActivityManagerService activityManagerService4 = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService4) {
                try {
                    dumpRemainsLocked();
                } finally {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public final void dumpUserHeaderLocked(int i) {
            if (!this.printed) {
                if (this.printedAnything) {
                    this.f1628pw.println();
                }
                this.f1628pw.println("  User " + i + " active services:");
                this.printed = true;
            }
            this.printedAnything = true;
            if (this.needSep) {
                this.f1628pw.println();
            }
        }

        public final void dumpServiceLocalLocked(ServiceRecord serviceRecord) {
            dumpUserHeaderLocked(serviceRecord.userId);
            this.f1628pw.print("  * ");
            this.f1628pw.println(serviceRecord);
            if (this.dumpAll) {
                serviceRecord.dump(this.f1628pw, "    ");
                this.needSep = true;
                return;
            }
            this.f1628pw.print("    app=");
            this.f1628pw.println(serviceRecord.app);
            this.f1628pw.print("    created=");
            TimeUtils.formatDuration(serviceRecord.createRealTime, this.nowReal, this.f1628pw);
            this.f1628pw.print(" started=");
            this.f1628pw.print(serviceRecord.startRequested);
            this.f1628pw.print(" connections=");
            ArrayMap connections = serviceRecord.getConnections();
            this.f1628pw.println(connections.size());
            if (connections.size() > 0) {
                this.f1628pw.println("    Connections:");
                for (int i = 0; i < connections.size(); i++) {
                    ArrayList arrayList = (ArrayList) connections.valueAt(i);
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i2);
                        this.f1628pw.print("      ");
                        this.f1628pw.print(connectionRecord.binding.intent.intent.getIntent().toShortString(false, false, false, false));
                        this.f1628pw.print(" -> ");
                        ProcessRecord processRecord = connectionRecord.binding.client;
                        this.f1628pw.println(processRecord != null ? processRecord.toShortString() : "null");
                    }
                }
            }
        }

        public final void dumpServiceClient(ServiceRecord serviceRecord) {
            IApplicationThread thread;
            ProcessRecord processRecord = serviceRecord.app;
            if (processRecord == null || (thread = processRecord.getThread()) == null) {
                return;
            }
            this.f1628pw.println("    Client:");
            this.f1628pw.flush();
            try {
                TransferPipe transferPipe = new TransferPipe();
                try {
                    thread.dumpService(transferPipe.getWriteFd(), serviceRecord, this.args);
                    transferPipe.setBufferPrefix("      ");
                    transferPipe.go(this.f1627fd, 2000L);
                    transferPipe.kill();
                } catch (Throwable th) {
                    transferPipe.kill();
                    throw th;
                }
            } catch (RemoteException unused) {
                this.f1628pw.println("      Got a RemoteException while dumping the service");
            } catch (IOException e) {
                this.f1628pw.println("      Failure while dumping the service: " + e);
            }
            this.needSep = true;
        }

        public final void dumpUserRemainsLocked(int i) {
            String str;
            String str2;
            ServiceMap serviceMapLocked = ActiveServices.this.getServiceMapLocked(i);
            this.printed = false;
            int size = serviceMapLocked.mDelayedStartList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mDelayedStartList.get(i2);
                if (this.matcher.match(serviceRecord, serviceRecord.name) && ((str2 = this.dumpPackage) == null || str2.equals(serviceRecord.appInfo.packageName))) {
                    if (!this.printed) {
                        if (this.printedAnything) {
                            this.f1628pw.println();
                        }
                        this.f1628pw.println("  User " + i + " delayed start services:");
                        this.printed = true;
                    }
                    this.printedAnything = true;
                    this.f1628pw.print("  * Delayed start ");
                    this.f1628pw.println(serviceRecord);
                }
            }
            this.printed = false;
            int size2 = serviceMapLocked.mStartingBackground.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMapLocked.mStartingBackground.get(i3);
                if (this.matcher.match(serviceRecord2, serviceRecord2.name) && ((str = this.dumpPackage) == null || str.equals(serviceRecord2.appInfo.packageName))) {
                    if (!this.printed) {
                        if (this.printedAnything) {
                            this.f1628pw.println();
                        }
                        this.f1628pw.println("  User " + i + " starting in background:");
                        this.printed = true;
                    }
                    this.printedAnything = true;
                    this.f1628pw.print("  * Starting bg ");
                    this.f1628pw.println(serviceRecord2);
                }
            }
        }

        public final void dumpRemainsLocked() {
            String str;
            ProcessRecord processRecord;
            String str2;
            String str3;
            String str4;
            boolean z = false;
            if (ActiveServices.this.mPendingServices.size() > 0) {
                this.printed = false;
                for (int i = 0; i < ActiveServices.this.mPendingServices.size(); i++) {
                    ServiceRecord serviceRecord = (ServiceRecord) ActiveServices.this.mPendingServices.get(i);
                    if (this.matcher.match(serviceRecord, serviceRecord.name) && ((str4 = this.dumpPackage) == null || str4.equals(serviceRecord.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.f1628pw.println();
                            }
                            this.needSep = true;
                            this.f1628pw.println("  Pending services:");
                            this.printed = true;
                        }
                        this.f1628pw.print("  * Pending ");
                        this.f1628pw.println(serviceRecord);
                        serviceRecord.dump(this.f1628pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (ActiveServices.this.mRestartingServices.size() > 0) {
                this.printed = false;
                for (int i2 = 0; i2 < ActiveServices.this.mRestartingServices.size(); i2++) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) ActiveServices.this.mRestartingServices.get(i2);
                    if (this.matcher.match(serviceRecord2, serviceRecord2.name) && ((str3 = this.dumpPackage) == null || str3.equals(serviceRecord2.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.f1628pw.println();
                            }
                            this.needSep = true;
                            this.f1628pw.println("  Restarting services:");
                            this.printed = true;
                        }
                        this.f1628pw.print("  * Restarting ");
                        this.f1628pw.println(serviceRecord2);
                        serviceRecord2.dump(this.f1628pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (ActiveServices.this.mDestroyingServices.size() > 0) {
                this.printed = false;
                for (int i3 = 0; i3 < ActiveServices.this.mDestroyingServices.size(); i3++) {
                    ServiceRecord serviceRecord3 = (ServiceRecord) ActiveServices.this.mDestroyingServices.get(i3);
                    if (this.matcher.match(serviceRecord3, serviceRecord3.name) && ((str2 = this.dumpPackage) == null || str2.equals(serviceRecord3.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.f1628pw.println();
                            }
                            this.needSep = true;
                            this.f1628pw.println("  Destroying services:");
                            this.printed = true;
                        }
                        this.f1628pw.print("  * Destroy ");
                        this.f1628pw.println(serviceRecord3);
                        serviceRecord3.dump(this.f1628pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (this.dumpAll) {
                this.printed = false;
                for (int i4 = 0; i4 < ActiveServices.this.mServiceConnections.size(); i4++) {
                    ArrayList arrayList = (ArrayList) ActiveServices.this.mServiceConnections.valueAt(i4);
                    for (int i5 = 0; i5 < arrayList.size(); i5++) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i5);
                        ActivityManagerService.ItemMatcher itemMatcher = this.matcher;
                        ServiceRecord serviceRecord4 = connectionRecord.binding.service;
                        if (itemMatcher.match(serviceRecord4, serviceRecord4.name) && ((str = this.dumpPackage) == null || ((processRecord = connectionRecord.binding.client) != null && str.equals(processRecord.info.packageName)))) {
                            this.printedAnything = true;
                            if (!this.printed) {
                                if (this.needSep) {
                                    this.f1628pw.println();
                                }
                                this.needSep = true;
                                this.f1628pw.println("  Connection bindings to services:");
                                this.printed = true;
                            }
                            this.f1628pw.print("  * ");
                            this.f1628pw.println(connectionRecord);
                            connectionRecord.dump(this.f1628pw, "    ");
                        }
                    }
                }
            }
            if (this.matcher.all) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int[] users = ActiveServices.this.mAm.mUserController.getUsers();
                int length = users.length;
                int i6 = 0;
                while (i6 < length) {
                    int i7 = users[i6];
                    ServiceMap serviceMap = (ServiceMap) ActiveServices.this.mServiceMap.get(i7);
                    if (serviceMap != null) {
                        boolean z2 = z;
                        for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                            ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size);
                            String str5 = this.dumpPackage;
                            if (str5 == null || str5.equals(activeForegroundApp.mPackageName)) {
                                if (!z2) {
                                    this.printedAnything = true;
                                    if (this.needSep) {
                                        this.f1628pw.println();
                                    }
                                    this.needSep = true;
                                    this.f1628pw.print("Active foreground apps - user ");
                                    this.f1628pw.print(i7);
                                    this.f1628pw.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                                    z2 = true;
                                }
                                this.f1628pw.print("  #");
                                this.f1628pw.print(size);
                                this.f1628pw.print(": ");
                                this.f1628pw.println(activeForegroundApp.mPackageName);
                                if (activeForegroundApp.mLabel != null) {
                                    this.f1628pw.print("    mLabel=");
                                    this.f1628pw.println(activeForegroundApp.mLabel);
                                }
                                this.f1628pw.print("    mNumActive=");
                                this.f1628pw.print(activeForegroundApp.mNumActive);
                                this.f1628pw.print(" mAppOnTop=");
                                this.f1628pw.print(activeForegroundApp.mAppOnTop);
                                this.f1628pw.print(" mShownWhileTop=");
                                this.f1628pw.print(activeForegroundApp.mShownWhileTop);
                                this.f1628pw.print(" mShownWhileScreenOn=");
                                this.f1628pw.println(activeForegroundApp.mShownWhileScreenOn);
                                this.f1628pw.print("    mStartTime=");
                                boolean z3 = z2;
                                TimeUtils.formatDuration(activeForegroundApp.mStartTime - elapsedRealtime, this.f1628pw);
                                this.f1628pw.print(" mStartVisibleTime=");
                                TimeUtils.formatDuration(activeForegroundApp.mStartVisibleTime - elapsedRealtime, this.f1628pw);
                                this.f1628pw.println();
                                if (activeForegroundApp.mEndTime != 0) {
                                    this.f1628pw.print("    mEndTime=");
                                    TimeUtils.formatDuration(activeForegroundApp.mEndTime - elapsedRealtime, this.f1628pw);
                                    this.f1628pw.println();
                                }
                                z2 = z3;
                            }
                        }
                        if (serviceMap.hasMessagesOrCallbacks()) {
                            if (this.needSep) {
                                this.f1628pw.println();
                            }
                            this.printedAnything = true;
                            this.needSep = true;
                            this.f1628pw.print("  Handler - user ");
                            this.f1628pw.print(i7);
                            this.f1628pw.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                            serviceMap.dumpMine(new PrintWriterPrinter(this.f1628pw), "    ");
                        }
                    }
                    i6++;
                    z = false;
                }
            }
            if (this.printedAnything) {
                return;
            }
            this.f1628pw.println("  (nothing)");
        }
    }

    public ServiceDumper newServiceDumperLocked(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, int i, boolean z, String str) {
        return new ServiceDumper(fileDescriptor, printWriter, strArr, i, z, str);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long start = protoOutputStream.start(j);
                for (int i : this.mAm.mUserController.getUsers()) {
                    ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
                    if (serviceMap != null) {
                        long start2 = protoOutputStream.start(2246267895809L);
                        protoOutputStream.write(1120986464257L, i);
                        ArrayMap arrayMap = serviceMap.mServicesByInstanceName;
                        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                            ((ServiceRecord) arrayMap.valueAt(i2)).dumpDebug(protoOutputStream, 2246267895810L);
                        }
                        protoOutputStream.end(start2);
                    }
                }
                protoOutputStream.end(start);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public boolean dumpService(FileDescriptor fileDescriptor, final PrintWriter printWriter, String str, int[] iArr, String[] strArr, int i, boolean z) {
        int[] users;
        try {
            boolean z2 = false;
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(false);
            ArrayList arrayList = new ArrayList();
            Predicate filterRecord = DumpUtils.filterRecord(str);
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                if (iArr == null) {
                    try {
                        users = this.mAm.mUserController.getUsers();
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                } else {
                    users = iArr;
                }
                for (int i2 : users) {
                    ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i2);
                    if (serviceMap != null) {
                        ArrayMap arrayMap = serviceMap.mServicesByInstanceName;
                        for (int i3 = 0; i3 < arrayMap.size(); i3++) {
                            ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(i3);
                            if (filterRecord.test(serviceRecord)) {
                                arrayList.add(serviceRecord);
                            }
                        }
                    }
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            if (arrayList.size() <= 0) {
                return false;
            }
            arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ServiceRecord) obj).getComponentName();
                }
            }));
            int i4 = 0;
            while (i4 < arrayList.size()) {
                if (z2) {
                    printWriter.println();
                }
                if (((ServiceRecord) arrayList.get(i4)).shortInstanceName.contains("SystemUIService")) {
                    printWriter.print("  SystemUI Revival Count:");
                    printWriter.println(this.mRevivalServicesMessages.size());
                    if (this.mRevivalServicesMessages.size() > 0) {
                        this.mRevivalServicesMessages.forEach(new Consumer() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda9
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ActiveServices.lambda$dumpService$4(printWriter, (String) obj);
                            }
                        });
                    }
                }
                dumpService("", fileDescriptor, printWriter, (ServiceRecord) arrayList.get(i4), strArr, z);
                i4++;
                z2 = true;
            }
            return true;
        } finally {
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
        }
    }

    public static /* synthetic */ void lambda$dumpService$4(PrintWriter printWriter, String str) {
        printWriter.println("    " + str);
    }

    public final void dumpService(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, ServiceRecord serviceRecord, String[] strArr, boolean z) {
        IApplicationThread thread;
        String str2 = str + "  ";
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                printWriter.print(str);
                printWriter.print("SERVICE ");
                printWriter.print(serviceRecord.shortInstanceName);
                printWriter.print(" ");
                printWriter.print(Integer.toHexString(System.identityHashCode(serviceRecord)));
                printWriter.print(" pid=");
                ProcessRecord processRecord = serviceRecord.app;
                if (processRecord != null) {
                    printWriter.print(processRecord.getPid());
                    printWriter.print(" user=");
                    printWriter.println(serviceRecord.userId);
                } else {
                    printWriter.println("(not running)");
                }
                if (z) {
                    serviceRecord.dump(printWriter, str2);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        ProcessRecord processRecord2 = serviceRecord.app;
        if (processRecord2 == null || (thread = processRecord2.getThread()) == null) {
            return;
        }
        printWriter.print(str);
        printWriter.println("  Client:");
        printWriter.flush();
        try {
            TransferPipe transferPipe = new TransferPipe();
            try {
                thread.dumpService(transferPipe.getWriteFd(), serviceRecord, strArr);
                transferPipe.setBufferPrefix(str + "    ");
                transferPipe.go(fileDescriptor);
                transferPipe.kill();
            } catch (Throwable th2) {
                transferPipe.kill();
                throw th2;
            }
        } catch (RemoteException unused) {
            printWriter.println(str + "    Got a RemoteException while dumping the service");
        } catch (IOException e) {
            printWriter.println(str + "    Failure while dumping the service: " + e);
        }
    }

    public final boolean isForceStopDisabled(String str, int i, String str2, String str3, String str4) {
        if (str == null) {
            return false;
        }
        try {
            IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
            if (asInterface != null) {
                return asInterface.isApplicationForceStopDisabled(str, i, str2, str3, str4, false);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public final void setFgsRestrictionLocked(String str, int i, int i2, Intent intent, ServiceRecord serviceRecord, int i3, BackgroundStartPrivileges backgroundStartPrivileges, boolean z, boolean z2) {
        if (!this.mAm.mConstants.mFlagBackgroundFgsStartRestrictionEnabled) {
            if (!serviceRecord.mAllowWhileInUsePermissionInFgs) {
                serviceRecord.mAllowWhileInUsePermissionInFgsReason = 1;
            }
            serviceRecord.mAllowWhileInUsePermissionInFgs = true;
        }
        if (!serviceRecord.mAllowWhileInUsePermissionInFgs || serviceRecord.mAllowStartForeground == -1) {
            int shouldAllowFgsWhileInUsePermissionLocked = shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, serviceRecord.app, backgroundStartPrivileges);
            if (!serviceRecord.mAllowWhileInUsePermissionInFgs) {
                serviceRecord.mAllowWhileInUsePermissionInFgs = shouldAllowFgsWhileInUsePermissionLocked != -1;
                serviceRecord.mAllowWhileInUsePermissionInFgsReason = shouldAllowFgsWhileInUsePermissionLocked;
            }
            if (serviceRecord.mAllowStartForeground == -1) {
                serviceRecord.mAllowStartForeground = shouldAllowFgsStartForegroundWithBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked, str, i, i2, intent, serviceRecord, backgroundStartPrivileges, z);
            }
        }
    }

    public void resetFgsRestrictionLocked(ServiceRecord serviceRecord) {
        serviceRecord.mAllowWhileInUsePermissionInFgs = false;
        serviceRecord.mAllowWhileInUsePermissionInFgsReason = -1;
        serviceRecord.mAllowStartForeground = -1;
        serviceRecord.mInfoAllowStartForeground = null;
        serviceRecord.mInfoTempFgsAllowListReason = null;
        serviceRecord.mLoggedInfoAllowStartForeground = false;
        serviceRecord.updateAllowUiJobScheduling(false);
    }

    public boolean canStartForegroundServiceLocked(int i, int i2, String str) {
        if (!this.mAm.mConstants.mFlagBackgroundFgsStartRestrictionEnabled) {
            return true;
        }
        int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, null, BackgroundStartPrivileges.NONE), i, i2, str, null, BackgroundStartPrivileges.NONE);
        if (shouldAllowFgsStartForegroundNoBindingCheckLocked == -1 && canBindingClientStartFgsLocked(i2) != null) {
            shouldAllowFgsStartForegroundNoBindingCheckLocked = 54;
        }
        return shouldAllowFgsStartForegroundNoBindingCheckLocked != -1;
    }

    public final int shouldAllowFgsWhileInUsePermissionLocked(String str, int i, final int i2, ProcessRecord processRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        ActiveInstrumentation activeInstrumentation;
        Integer num;
        int uidStateLocked = this.mAm.getUidStateLocked(i2);
        int reasonCodeFromProcState = uidStateLocked <= 2 ? PowerExemptionManager.getReasonCodeFromProcState(uidStateLocked) : -1;
        if (reasonCodeFromProcState == -1 && this.mAm.mAtmInternal.isUidForeground(i2)) {
            reasonCodeFromProcState = 50;
        }
        if (reasonCodeFromProcState == -1 && backgroundStartPrivileges.allowsBackgroundActivityStarts()) {
            reasonCodeFromProcState = 53;
        }
        if (reasonCodeFromProcState == -1) {
            int appId = UserHandle.getAppId(i2);
            if (appId == 0 || appId == 1000 || appId == 1027 || appId == 2000) {
                reasonCodeFromProcState = 51;
            }
        }
        if (reasonCodeFromProcState == -1 && (num = (Integer) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer lambda$shouldAllowFgsWhileInUsePermissionLocked$5;
                lambda$shouldAllowFgsWhileInUsePermissionLocked$5 = ActiveServices.lambda$shouldAllowFgsWhileInUsePermissionLocked$5(i2, (ProcessRecord) obj);
                return lambda$shouldAllowFgsWhileInUsePermissionLocked$5;
            }
        })) != null) {
            reasonCodeFromProcState = num.intValue();
        }
        if (reasonCodeFromProcState == -1 && this.mAm.mInternal.isTempAllowlistedForFgsWhileInUse(i2)) {
            return 70;
        }
        if (reasonCodeFromProcState == -1 && processRecord != null && (activeInstrumentation = processRecord.getActiveInstrumentation()) != null && activeInstrumentation.mHasBackgroundActivityStartsPermission) {
            reasonCodeFromProcState = 60;
        }
        if (reasonCodeFromProcState == -1 && this.mAm.checkPermission("android.permission.START_ACTIVITIES_FROM_BACKGROUND", i, i2) == 0) {
            reasonCodeFromProcState = 58;
        }
        if (reasonCodeFromProcState == -1) {
            if (verifyPackage(str, i2)) {
                if (this.mAllowListWhileInUsePermissionInFgs.contains(str)) {
                    reasonCodeFromProcState = 65;
                }
            } else {
                EventLog.writeEvent(1397638484, "215003903", Integer.valueOf(i2), "callingPackage:" + str + " does not belong to callingUid:" + i2);
            }
        }
        if (reasonCodeFromProcState == -1 && this.mAm.mInternal.isDeviceOwner(i2)) {
            return 55;
        }
        return reasonCodeFromProcState;
    }

    public static /* synthetic */ Integer lambda$shouldAllowFgsWhileInUsePermissionLocked$5(int i, ProcessRecord processRecord) {
        return (processRecord.uid == i && processRecord.getWindowProcessController().areBackgroundFgsStartsAllowed()) ? 52 : null;
    }

    public final String canBindingClientStartFgsLocked(final int i) {
        final ArraySet arraySet = new ArraySet();
        Pair pair = (Pair) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Pair lambda$canBindingClientStartFgsLocked$7;
                lambda$canBindingClientStartFgsLocked$7 = ActiveServices.this.lambda$canBindingClientStartFgsLocked$7(i, arraySet, (ProcessRecord) obj);
                return lambda$canBindingClientStartFgsLocked$7;
            }
        });
        if (pair != null) {
            return (String) pair.second;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Pair lambda$canBindingClientStartFgsLocked$7(int i, ArraySet arraySet, ProcessRecord processRecord) {
        if (processRecord.uid != i) {
            return null;
        }
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        int size = processServiceRecord.mServices.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayMap connections = ((ServiceRecord) processServiceRecord.mServices.valueAt(i2)).getConnections();
            int size2 = connections.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ArrayList arrayList = (ArrayList) connections.valueAt(i3);
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i4);
                    ProcessRecord processRecord2 = connectionRecord.binding.client;
                    if (!processRecord2.isPersistent()) {
                        int i5 = processRecord2.mPid;
                        int i6 = processRecord2.uid;
                        if (i6 != i && !arraySet.contains(Integer.valueOf(i6))) {
                            String str = connectionRecord.clientPackageName;
                            int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked(str, i5, i6, null, BackgroundStartPrivileges.NONE), i5, i6, str, null, BackgroundStartPrivileges.NONE);
                            if (shouldAllowFgsStartForegroundNoBindingCheckLocked != -1) {
                                return new Pair(Integer.valueOf(shouldAllowFgsStartForegroundNoBindingCheckLocked), str);
                            }
                            arraySet.add(Integer.valueOf(i6));
                        }
                    }
                }
            }
        }
        return null;
    }

    public final int shouldAllowFgsStartForegroundWithBindingCheckLocked(int i, String str, int i2, int i3, Intent intent, ServiceRecord serviceRecord, BackgroundStartPrivileges backgroundStartPrivileges, boolean z) {
        String str2;
        ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i3);
        serviceRecord.mInfoTempFgsAllowListReason = isAllowlistedForFgsStartLOSP;
        int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(i, i2, i3, str, serviceRecord, backgroundStartPrivileges);
        String str3 = null;
        int i4 = -1;
        if (shouldAllowFgsStartForegroundNoBindingCheckLocked == -1) {
            str2 = canBindingClientStartFgsLocked(i3);
            if (str2 != null) {
                shouldAllowFgsStartForegroundNoBindingCheckLocked = 54;
            }
        } else {
            str2 = null;
        }
        int uidStateLocked = this.mAm.getUidStateLocked(i3);
        try {
            i4 = this.mAm.mContext.getPackageManager().getTargetSdkVersion(str);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        boolean z2 = (this.mAm.getUidProcessCapabilityLocked(i3) & 16) != 0;
        StringBuilder sb = new StringBuilder();
        sb.append("[callingPackage: ");
        sb.append(str);
        sb.append("; callingUid: ");
        sb.append(i3);
        sb.append("; uidState: ");
        sb.append(ProcessList.makeProcStateString(uidStateLocked));
        sb.append("; uidBFSL: ");
        sb.append(z2 ? "[BFSL]" : "n/a");
        sb.append("; intent: ");
        sb.append(intent);
        sb.append("; code:");
        sb.append(PowerExemptionManager.reasonCodeToString(shouldAllowFgsStartForegroundNoBindingCheckLocked));
        sb.append("; tempAllowListReason:<");
        if (isAllowlistedForFgsStartLOSP != null) {
            str3 = isAllowlistedForFgsStartLOSP.mReason + ",reasonCode:" + PowerExemptionManager.reasonCodeToString(isAllowlistedForFgsStartLOSP.mReasonCode) + ",duration:" + isAllowlistedForFgsStartLOSP.mDuration + ",callingUid:" + isAllowlistedForFgsStartLOSP.mCallingUid;
        }
        sb.append(str3);
        sb.append(">; targetSdkVersion:");
        sb.append(serviceRecord.appInfo.targetSdkVersion);
        sb.append("; callerTargetSdkVersion:");
        sb.append(i4);
        sb.append("; startForegroundCount:");
        sb.append(serviceRecord.mStartForegroundCount);
        sb.append("; bindFromPackage:");
        sb.append(str2);
        sb.append(": isBindService:");
        sb.append(z);
        sb.append("]");
        String sb2 = sb.toString();
        if (!sb2.equals(serviceRecord.mInfoAllowStartForeground)) {
            serviceRecord.mLoggedInfoAllowStartForeground = false;
            serviceRecord.mInfoAllowStartForeground = sb2;
        }
        return shouldAllowFgsStartForegroundNoBindingCheckLocked;
    }

    public final int shouldAllowFgsStartForegroundNoBindingCheckLocked(int i, int i2, final int i3, String str, ServiceRecord serviceRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        String stringForUser;
        ComponentName unflattenFromString;
        ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP;
        int uidStateLocked;
        if (i == -1 && (uidStateLocked = this.mAm.getUidStateLocked(i3)) <= 2) {
            i = PowerExemptionManager.getReasonCodeFromProcState(uidStateLocked);
        }
        if (i == -1) {
            final boolean z = (this.mAm.getUidProcessCapabilityLocked(i3) & 16) != 0;
            Integer num = (Integer) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Integer lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$8;
                    lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$8 = ActiveServices.this.lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$8(i3, z, (ProcessRecord) obj);
                    return lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$8;
                }
            });
            if (num != null) {
                i = num.intValue();
            }
        }
        if (i == -1 && this.mAm.checkPermission("android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND", i2, i3) == 0) {
            i = 59;
        }
        if (i == -1 && backgroundStartPrivileges.allowsBackgroundFgsStarts()) {
            i = 53;
        }
        if (i == -1 && this.mAm.mAtmInternal.hasSystemAlertWindowPermission(i3, i2, str)) {
            i = 62;
        }
        if (i == -1 && this.mAm.mInternal.isAssociatedCompanionApp(UserHandle.getUserId(i3), i3) && (isPermissionGranted("android.permission.REQUEST_COMPANION_START_FOREGROUND_SERVICES_FROM_BACKGROUND", i2, i3) || isPermissionGranted("android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND", i2, i3))) {
            i = 57;
        }
        if (i == -1 && (isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i3)) != null) {
            i = isAllowlistedForFgsStartLOSP == ActivityManagerService.FAKE_TEMP_ALLOW_LIST_ITEM ? 300 : isAllowlistedForFgsStartLOSP.mReasonCode;
        }
        if (i == -1 && UserManager.isDeviceInDemoMode(this.mAm.mContext)) {
            i = 63;
        }
        if (i == -1 && this.mAm.mInternal.isProfileOwner(i3)) {
            i = 56;
        }
        if (i == -1) {
            AppOpsManager appOpsManager = this.mAm.getAppOpsManager();
            if (this.mAm.mConstants.mFlagSystemExemptPowerRestrictionsEnabled && appOpsManager.checkOpNoThrow(128, i3, str) == 0) {
                i = FrameworkStatsLog.TIF_TUNE_CHANGED;
            }
        }
        if (i == -1) {
            AppOpsManager appOpsManager2 = this.mAm.getAppOpsManager();
            if (appOpsManager2.checkOpNoThrow(47, i3, str) == 0) {
                i = 68;
            } else if (appOpsManager2.checkOpNoThrow(94, i3, str) == 0) {
                i = 69;
            }
        }
        if (i == -1 && (stringForUser = Settings.Secure.getStringForUser(this.mAm.mContext.getContentResolver(), "default_input_method", UserHandle.getUserId(i3))) != null && (unflattenFromString = ComponentName.unflattenFromString(stringForUser)) != null && unflattenFromString.getPackageName().equals(str)) {
            i = 71;
        }
        if (i == -1 && this.mAm.mConstants.mFgsAllowOptOut && serviceRecord != null && serviceRecord.appInfo.hasRequestForegroundServiceExemption()) {
            return 1000;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$8(int i, boolean z, ProcessRecord processRecord) {
        if (processRecord.uid != i) {
            return null;
        }
        int curProcState = processRecord.mState.getCurProcState();
        if (curProcState <= 3 || (z && curProcState <= 5)) {
            return Integer.valueOf(PowerExemptionManager.getReasonCodeFromProcState(curProcState));
        }
        ActiveInstrumentation activeInstrumentation = processRecord.getActiveInstrumentation();
        if (activeInstrumentation != null && activeInstrumentation.mHasBackgroundForegroundServiceStartsPermission) {
            return 61;
        }
        long lastInvisibleTime = processRecord.mState.getLastInvisibleTime();
        return (lastInvisibleTime <= 0 || lastInvisibleTime >= Long.MAX_VALUE || SystemClock.elapsedRealtime() - lastInvisibleTime >= this.mAm.mConstants.mFgToBgFgsGraceDuration) ? null : 67;
    }

    public final boolean isPermissionGranted(String str, int i, int i2) {
        return this.mAm.checkPermission(str, i, i2) == 0;
    }

    public final void showFgsBgRestrictedNotificationLocked(ServiceRecord serviceRecord) {
        if (this.mAm.mConstants.mFgsStartRestrictionNotificationEnabled) {
            Context context = this.mAm.mContext;
            String str = "App restricted: " + serviceRecord.mRecentCallingPackage;
            long currentTimeMillis = System.currentTimeMillis();
            ((NotificationManager) context.getSystemService(NotificationManager.class)).notifyAsUser(Long.toString(currentTimeMillis), 61, new Notification.Builder(context, SystemNotificationChannels.ALERTS).setGroup("com.android.fgs-bg-restricted").setSmallIcon(17304295).setWhen(0L).setColor(context.getColor(R.color.system_notification_accent_color)).setTicker("Foreground Service BG-Launch Restricted").setContentTitle("Foreground Service BG-Launch Restricted").setContentText(str).setStyle(new Notification.BigTextStyle().bigText(DATE_FORMATTER.format(Long.valueOf(currentTimeMillis)) + " " + serviceRecord.mInfoAllowStartForeground)).build(), UserHandle.ALL);
        }
    }

    public final boolean isBgFgsRestrictionEnabled(ServiceRecord serviceRecord) {
        return this.mAm.mConstants.mFlagFgsStartRestrictionEnabled && CompatChanges.isChangeEnabled(170668199L, serviceRecord.appInfo.uid) && (!this.mAm.mConstants.mFgsStartRestrictionCheckCallerTargetSdk || CompatChanges.isChangeEnabled(170668199L, serviceRecord.mRecentCallingUid));
    }

    public final void logFgsBackgroundStart(ServiceRecord serviceRecord) {
        if (serviceRecord.mLoggedInfoAllowStartForeground) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Background started FGS: ");
        sb.append(serviceRecord.mAllowStartForeground != -1 ? "Allowed " : "Disallowed ");
        sb.append(serviceRecord.mInfoAllowStartForeground);
        sb.append(serviceRecord.isShortFgs() ? " (Called on SHORT_SERVICE)" : "");
        String sb2 = sb.toString();
        if (serviceRecord.mAllowStartForeground != -1) {
            if (ActivityManagerUtils.shouldSamplePackageForAtom(serviceRecord.packageName, this.mAm.mConstants.mFgsStartAllowedLogSampleRate)) {
                Slog.wtfQuiet("ActivityManager", sb2);
            }
            Slog.i("ActivityManager", sb2);
        } else {
            Slog.wtfQuiet("ActivityManager", sb2);
            Slog.w("ActivityManager", sb2);
        }
        serviceRecord.mLoggedInfoAllowStartForeground = true;
    }

    public final void logFGSStateChangeLocked(ServiceRecord serviceRecord, int i, int i2, int i3, int i4) {
        boolean z;
        int i5;
        char c;
        char c2;
        int i6;
        if (ActivityManagerUtils.shouldSamplePackageForAtom(serviceRecord.packageName, this.mAm.mConstants.mFgsAtomSampleRate)) {
            if (i == 1 || i == 2 || i == 5) {
                z = serviceRecord.mAllowWhileInUsePermissionInFgsAtEntering;
                i5 = serviceRecord.mAllowStartForegroundAtEntering;
            } else {
                z = serviceRecord.mAllowWhileInUsePermissionInFgs;
                i5 = serviceRecord.mAllowStartForeground;
            }
            boolean z2 = z;
            int i7 = i5;
            ApplicationInfo applicationInfo = serviceRecord.mRecentCallerApplicationInfo;
            int i8 = applicationInfo != null ? applicationInfo.targetSdkVersion : 0;
            ApplicationInfo applicationInfo2 = serviceRecord.appInfo;
            int i9 = applicationInfo2.uid;
            String str = serviceRecord.shortInstanceName;
            int i10 = applicationInfo2.targetSdkVersion;
            int i11 = serviceRecord.mRecentCallingUid;
            ActivityManagerService.FgsTempAllowListItem fgsTempAllowListItem = serviceRecord.mInfoTempFgsAllowListReason;
            int i12 = fgsTempAllowListItem != null ? fgsTempAllowListItem.mCallingUid : -1;
            boolean z3 = serviceRecord.mFgsNotificationWasDeferred;
            boolean z4 = serviceRecord.mFgsNotificationShown;
            int i13 = serviceRecord.mStartForegroundCount;
            int hashComponentNameForAtom = ActivityManagerUtils.hashComponentNameForAtom(str);
            boolean z5 = serviceRecord.mFgsHasNotificationPermission;
            int i14 = serviceRecord.foregroundServiceType;
            boolean z6 = serviceRecord.mIsFgsDelegate;
            ForegroundServiceDelegation foregroundServiceDelegation = serviceRecord.mFgsDelegation;
            FrameworkStatsLog.write(60, i9, str, i, z2, i7, i10, i11, i8, i12, z3, z4, i2, i13, hashComponentNameForAtom, z5, i14, i4, z6, foregroundServiceDelegation != null ? foregroundServiceDelegation.mOptions.mClientUid : -1, foregroundServiceDelegation != null ? foregroundServiceDelegation.mOptions.mDelegationService : 0, 0, null, null, this.mAm.getUidStateLocked(serviceRecord.appInfo.uid), this.mAm.getUidProcessCapabilityLocked(serviceRecord.appInfo.uid), this.mAm.getUidStateLocked(serviceRecord.mRecentCallingUid), this.mAm.getUidProcessCapabilityLocked(serviceRecord.mRecentCallingUid), 0L, 0L);
            if (i == 1) {
                i6 = 30100;
                c = 2;
            } else {
                c = 2;
                if (i == 2) {
                    i6 = 30102;
                } else {
                    if (i != 3) {
                        c2 = 5;
                        if (i == 5) {
                            i6 = 30103;
                            Object[] objArr = new Object[12];
                            objArr[0] = Integer.valueOf(serviceRecord.userId);
                            objArr[1] = serviceRecord.shortInstanceName;
                            objArr[c] = Integer.valueOf(z2 ? 1 : 0);
                            objArr[3] = PowerExemptionManager.reasonCodeToString(i7);
                            objArr[4] = Integer.valueOf(serviceRecord.appInfo.targetSdkVersion);
                            objArr[c2] = Integer.valueOf(i8);
                            objArr[6] = Integer.valueOf(serviceRecord.mFgsNotificationWasDeferred ? 1 : 0);
                            objArr[7] = Integer.valueOf(serviceRecord.mFgsNotificationShown ? 1 : 0);
                            objArr[8] = Integer.valueOf(i2);
                            objArr[9] = Integer.valueOf(serviceRecord.mStartForegroundCount);
                            objArr[10] = fgsStopReasonToString(i3);
                            objArr[11] = Integer.valueOf(serviceRecord.foregroundServiceType);
                            EventLog.writeEvent(i6, objArr);
                        }
                        return;
                    }
                    i6 = 30101;
                }
            }
            c2 = 5;
            Object[] objArr2 = new Object[12];
            objArr2[0] = Integer.valueOf(serviceRecord.userId);
            objArr2[1] = serviceRecord.shortInstanceName;
            objArr2[c] = Integer.valueOf(z2 ? 1 : 0);
            objArr2[3] = PowerExemptionManager.reasonCodeToString(i7);
            objArr2[4] = Integer.valueOf(serviceRecord.appInfo.targetSdkVersion);
            objArr2[c2] = Integer.valueOf(i8);
            objArr2[6] = Integer.valueOf(serviceRecord.mFgsNotificationWasDeferred ? 1 : 0);
            objArr2[7] = Integer.valueOf(serviceRecord.mFgsNotificationShown ? 1 : 0);
            objArr2[8] = Integer.valueOf(i2);
            objArr2[9] = Integer.valueOf(serviceRecord.mStartForegroundCount);
            objArr2[10] = fgsStopReasonToString(i3);
            objArr2[11] = Integer.valueOf(serviceRecord.foregroundServiceType);
            EventLog.writeEvent(i6, objArr2);
        }
    }

    public final void updateNumForegroundServicesLocked() {
        sNumForegroundServices.set(this.mAm.mProcessList.getNumForegroundServices());
    }

    public boolean canAllowWhileInUsePermissionInFgsLocked(int i, int i2, String str) {
        return shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, null, BackgroundStartPrivileges.NONE) != -1;
    }

    public boolean canAllowWhileInUsePermissionInFgsLocked(int i, int i2, String str, ProcessRecord processRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        return shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, processRecord, backgroundStartPrivileges) != -1;
    }

    public final boolean verifyPackage(String str, int i) {
        if (i == 0 || i == 1000) {
            return true;
        }
        return this.mAm.getPackageManagerInternal().isSameApp(str, i, UserHandle.getUserId(i));
    }

    public boolean startForegroundServiceDelegateLocked(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, final ServiceConnection serviceConnection) {
        ProcessRecord processRecord;
        IApplicationThread thread;
        ProcessRecord processRecord2;
        IApplicationThread iApplicationThread;
        ServiceRecord serviceRecord;
        Slog.v("ActivityManager", "startForegroundServiceDelegateLocked " + foregroundServiceDelegationOptions.getDescription());
        final ComponentName componentName = foregroundServiceDelegationOptions.getComponentName();
        for (int size = this.mFgsDelegations.size() - 1; size >= 0; size--) {
            if (((ForegroundServiceDelegation) this.mFgsDelegations.keyAt(size)).mOptions.isSameDelegate(foregroundServiceDelegationOptions)) {
                Slog.e("ActivityManager", "startForegroundServiceDelegate " + foregroundServiceDelegationOptions.getDescription() + " already exists, multiple connections are not allowed");
                return false;
            }
        }
        int i = foregroundServiceDelegationOptions.mClientPid;
        int i2 = foregroundServiceDelegationOptions.mClientUid;
        int userId = UserHandle.getUserId(i2);
        String str = foregroundServiceDelegationOptions.mClientPackageName;
        if (!canStartForegroundServiceLocked(i, i2, str)) {
            Slog.d("ActivityManager", "startForegroundServiceDelegateLocked aborted, app is in the background");
            return false;
        }
        IApplicationThread iApplicationThread2 = foregroundServiceDelegationOptions.mClientAppThread;
        if (iApplicationThread2 != null) {
            iApplicationThread = iApplicationThread2;
            processRecord2 = this.mAm.getRecordForAppLOSP(iApplicationThread2);
        } else {
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord = this.mAm.mPidsSelfLocked.get(i);
                thread = processRecord.getThread();
            }
            processRecord2 = processRecord;
            iApplicationThread = thread;
        }
        if (processRecord2 == null) {
            throw new SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + i + ") when startForegroundServiceDelegateLocked " + componentName);
        }
        Intent intent = new Intent();
        intent.setComponent(componentName);
        ProcessRecord processRecord3 = processRecord2;
        IApplicationThread iApplicationThread3 = iApplicationThread;
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, null, false, -1, null, null, str, i, i2, userId, true, false, false, false, foregroundServiceDelegationOptions, false);
        if (retrieveServiceLocked == null || (serviceRecord = retrieveServiceLocked.record) == null) {
            Slog.d("ActivityManager", "startForegroundServiceDelegateLocked retrieveServiceLocked returns null");
            return false;
        }
        serviceRecord.setProcess(processRecord3, iApplicationThread3, i, null);
        serviceRecord.mIsFgsDelegate = true;
        final ForegroundServiceDelegation foregroundServiceDelegation = new ForegroundServiceDelegation(foregroundServiceDelegationOptions, serviceConnection);
        serviceRecord.mFgsDelegation = foregroundServiceDelegation;
        this.mFgsDelegations.put(foregroundServiceDelegation, serviceRecord);
        serviceRecord.isForeground = true;
        serviceRecord.mFgsEnterTime = SystemClock.uptimeMillis();
        serviceRecord.foregroundServiceType = foregroundServiceDelegationOptions.mForegroundServiceTypes;
        setFgsRestrictionLocked(str, i, i2, intent, serviceRecord, userId, BackgroundStartPrivileges.NONE, false, false);
        ProcessServiceRecord processServiceRecord = processRecord3.mServices;
        processServiceRecord.startService(serviceRecord);
        updateServiceForegroundLocked(processServiceRecord, true);
        synchronized (this.mAm.mProcessStats.mLock) {
            ServiceState tracker = serviceRecord.getTracker();
            if (tracker != null) {
                tracker.setForeground(true, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
            }
        }
        this.mAm.mBatteryStatsService.noteServiceStartRunning(i2, str, componentName.getClassName());
        AppOpsService appOpsService = this.mAm.mAppOpsService;
        appOpsService.startOperation(AppOpsManager.getToken(appOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null, true, false, null, false, 0, -1);
        registerAppOpCallbackLocked(serviceRecord);
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceStart(serviceRecord.appInfo.uid, 0, serviceRecord);
        }
        logFGSStateChangeLocked(serviceRecord, 1, 0, 0, 0);
        if (serviceConnection != null) {
            this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ActiveServices.lambda$startForegroundServiceDelegateLocked$9(serviceConnection, componentName, foregroundServiceDelegation);
                }
            });
        }
        signalForegroundServiceObserversLocked(serviceRecord);
        return true;
    }

    public static /* synthetic */ void lambda$startForegroundServiceDelegateLocked$9(ServiceConnection serviceConnection, ComponentName componentName, ForegroundServiceDelegation foregroundServiceDelegation) {
        serviceConnection.onServiceConnected(componentName, foregroundServiceDelegation.mBinder);
    }

    public void stopForegroundServiceDelegateLocked(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions) {
        ServiceRecord serviceRecord;
        int size = this.mFgsDelegations.size();
        while (true) {
            size--;
            if (size < 0) {
                serviceRecord = null;
                break;
            } else if (((ForegroundServiceDelegation) this.mFgsDelegations.keyAt(size)).mOptions.isSameDelegate(foregroundServiceDelegationOptions)) {
                Slog.d("ActivityManager", "stopForegroundServiceDelegateLocked " + foregroundServiceDelegationOptions.getDescription());
                serviceRecord = (ServiceRecord) this.mFgsDelegations.valueAt(size);
                break;
            }
        }
        if (serviceRecord != null) {
            bringDownServiceLocked(serviceRecord, false);
            return;
        }
        Slog.e("ActivityManager", "stopForegroundServiceDelegateLocked delegate does not exist " + foregroundServiceDelegationOptions.getDescription());
    }

    public void stopForegroundServiceDelegateLocked(ServiceConnection serviceConnection) {
        ServiceRecord serviceRecord;
        int size = this.mFgsDelegations.size();
        while (true) {
            size--;
            if (size < 0) {
                serviceRecord = null;
                break;
            }
            ForegroundServiceDelegation foregroundServiceDelegation = (ForegroundServiceDelegation) this.mFgsDelegations.keyAt(size);
            if (foregroundServiceDelegation.mConnection == serviceConnection) {
                Slog.d("ActivityManager", "stopForegroundServiceDelegateLocked " + foregroundServiceDelegation.mOptions.getDescription());
                serviceRecord = (ServiceRecord) this.mFgsDelegations.valueAt(size);
                break;
            }
        }
        if (serviceRecord != null) {
            bringDownServiceLocked(serviceRecord, false);
        } else {
            Slog.e("ActivityManager", "stopForegroundServiceDelegateLocked delegate does not exist");
        }
    }

    public static void getClientPackages(ServiceRecord serviceRecord, ArraySet arraySet) {
        ArrayMap connections = serviceRecord.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) connections.valueAt(size);
            int size2 = arrayList.size();
            for (int i = 0; i < size2; i++) {
                ProcessRecord processRecord = ((ConnectionRecord) arrayList.get(i)).binding.client;
                if (processRecord != null) {
                    arraySet.add(processRecord.info.packageName);
                }
            }
        }
    }

    public ArraySet getClientPackagesLocked(String str) {
        ArraySet arraySet = new ArraySet();
        for (int i : this.mAm.mUserController.getUsers()) {
            ArrayMap servicesLocked = getServicesLocked(i);
            int size = servicesLocked.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceRecord serviceRecord = (ServiceRecord) servicesLocked.valueAt(i2);
                if (serviceRecord.name.getPackageName().equals(str)) {
                    getClientPackages(serviceRecord, arraySet);
                }
            }
        }
        return arraySet;
    }

    public final boolean isDeviceProvisioningPackage(String str) {
        if (this.mCachedDeviceProvisioningPackage == null) {
            this.mCachedDeviceProvisioningPackage = this.mAm.mContext.getResources().getString(R.string.default_notification_channel_label);
        }
        String str2 = this.mCachedDeviceProvisioningPackage;
        return str2 != null && str2.equals(str);
    }

    public final boolean shouldDelay(ServiceRecord serviceRecord) {
        ProcessList processList = this.mAm.mProcessList;
        String str = serviceRecord.processName;
        ApplicationInfo applicationInfo = serviceRecord.appInfo;
        if (processList.getSharedIsolatedProcess(str, applicationInfo.uid, applicationInfo.packageName) != null || this.mAm.getProcessRecordLocked(serviceRecord.processName, serviceRecord.appInfo.uid) != null) {
            return false;
        }
        String str2 = serviceRecord.packageName;
        if (str2 != null) {
            return (str2.startsWith("com.samsung") || serviceRecord.packageName.startsWith("com.sec") || serviceRecord.packageName.startsWith("com.google") || serviceRecord.packageName.startsWith("com.android") || serviceRecord.packageName.contains(".cts.") || serviceRecord.packageName.endsWith(".cts") || !serviceRecord.packageName.equals(serviceRecord.mRecentCallingPackage)) ? false : true;
        }
        return true;
    }
}
