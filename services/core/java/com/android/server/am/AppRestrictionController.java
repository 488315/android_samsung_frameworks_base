package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.AppStandbyInfo;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.Xml;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.TriConsumer;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AppStateTracker;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.am.AppBatteryExemptionTracker;
import com.android.server.am.AppBatteryTracker;
import com.android.server.am.AppBindServiceEventsTracker;
import com.android.server.am.AppBroadcastEventsTracker;
import com.android.server.am.AppFGSTracker;
import com.android.server.am.AppRestrictionController;
import com.android.server.am.BaseAppStateTracker;
import com.android.server.apphibernation.AppHibernationService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.usage.AppStandbyInternal;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppRestrictionController {
    public static final String[] ROLES_IN_INTEREST = {"android.app.role.DIALER", "android.app.role.EMERGENCY"};
    public String errorMsg;
    public final SparseArrayMap mActiveUids;
    public final ActivityManagerService mActivityManagerService;
    public final AnonymousClass4 mAppIdleStateChangeListener;
    public final ArrayList mAppStateTrackers;
    public final AnonymousClass3 mBackgroundRestrictionListener;
    public final HandlerExecutor mBgExecutor;
    public final BgHandler mBgHandler;
    public final HandlerThread mBgHandlerThread;
    public ArraySet mBgRestrictionExemptioFromSysConfig;
    public final AnonymousClass1 mBootReceiver;
    public final AnonymousClass1 mBroadcastReceiver;
    public final SparseArray mCarrierPrivilegedApps;
    public final Object mCarrierPrivilegedLock;
    public volatile ArrayList mCarrierPrivilegesCallbacks;
    public final ConstantsObserver mConstantsObserver;
    public final Context mContext;
    public int[] mDeviceIdleAllowlist;
    public int[] mDeviceIdleExceptIdleAllowlist;
    public final TrackerInfo mEmptyTrackerInfo;
    public final HandlerExecutor mExecutor;
    public final Injector mInjector;
    public final Object mLock;
    public volatile boolean mLockedBootCompleted;
    public final NotificationHelper mNotificationHelper;
    public final CopyOnWriteArraySet mRestrictionListeners;
    final RestrictionSettings mRestrictionSettings;
    public final AtomicBoolean mRestrictionSettingsXmlLoaded;
    public final AppRestrictionController$$ExternalSyntheticLambda1 mRoleHolderChangedListener;
    public final Object mSettingsLock;
    public final ArraySet mSystemDeviceIdleAllowlist;
    public final ArraySet mSystemDeviceIdleExceptIdleAllowlist;
    public final HashMap mSystemModulesCache;
    public final ArrayList mTmpRunnables;
    public final AnonymousClass5 mUidObserver;
    public final SparseArray mUidRolesMapping;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.AppRestrictionController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            char c;
            int intExtra;
            ArrayList arrayList;
            int intExtra2;
            String schemeSpecificPart;
            int i = 0;
            switch (this.$r8$classId) {
                case 0:
                    intent.getAction();
                    String action = intent.getAction();
                    action.getClass();
                    switch (action.hashCode()) {
                        case -2061058799:
                            if (action.equals("android.intent.action.USER_REMOVED")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1749672628:
                            if (action.equals("android.intent.action.UID_REMOVED")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -755112654:
                            if (action.equals("android.intent.action.USER_STARTED")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -742246786:
                            if (action.equals("android.intent.action.USER_STOPPED")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1093296680:
                            if (action.equals("android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1121780209:
                            if (action.equals("android.intent.action.USER_ADDED")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1544582882:
                            if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1580442797:
                            if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra3 >= 0) {
                                AppRestrictionController appRestrictionController = (AppRestrictionController) this.this$0;
                                int size = appRestrictionController.mAppStateTrackers.size();
                                while (i < size) {
                                    ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i)).onUserRemoved(intExtra3);
                                    i++;
                                }
                                RestrictionSettings restrictionSettings = appRestrictionController.mRestrictionSettings;
                                synchronized (AppRestrictionController.this.mSettingsLock) {
                                    try {
                                        for (int numMaps = restrictionSettings.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                                            if (UserHandle.getUserId(restrictionSettings.mRestrictionLevels.keyAt(numMaps)) == intExtra3) {
                                                restrictionSettings.mRestrictionLevels.deleteAt(numMaps);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            return;
                        case 1:
                            if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) || (intExtra = intent.getIntExtra("android.intent.extra.UID", -1)) < 0) {
                                return;
                            }
                            AppRestrictionController appRestrictionController2 = (AppRestrictionController) this.this$0;
                            int size2 = appRestrictionController2.mAppStateTrackers.size();
                            while (i < size2) {
                                ((BaseAppStateTracker) appRestrictionController2.mAppStateTrackers.get(i)).onUidRemoved(intExtra);
                                i++;
                            }
                            RestrictionSettings restrictionSettings2 = appRestrictionController2.mRestrictionSettings;
                            synchronized (AppRestrictionController.this.mSettingsLock) {
                                restrictionSettings2.mRestrictionLevels.delete(intExtra);
                            }
                            if (AppRestrictionController.this.mRestrictionSettingsXmlLoaded.get()) {
                                restrictionSettings2.schedulePersistToXml(UserHandle.getUserId(intExtra));
                                return;
                            }
                            return;
                        case 2:
                            int intExtra4 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra4 >= 0) {
                                AppRestrictionController appRestrictionController3 = (AppRestrictionController) this.this$0;
                                appRestrictionController3.refreshAppRestrictionLevelForUser(intExtra4);
                                int size3 = appRestrictionController3.mAppStateTrackers.size();
                                while (i < size3) {
                                    ((BaseAppStateTracker) appRestrictionController3.mAppStateTrackers.get(i)).onUserStarted(intExtra4);
                                    i++;
                                }
                                return;
                            }
                            return;
                        case 3:
                            int intExtra5 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra5 >= 0) {
                                AppRestrictionController appRestrictionController4 = (AppRestrictionController) this.this$0;
                                int size4 = appRestrictionController4.mAppStateTrackers.size();
                                while (i < size4) {
                                    ((BaseAppStateTracker) appRestrictionController4.mAppStateTrackers.get(i)).onUserStopped(intExtra5);
                                    i++;
                                }
                                return;
                            }
                            return;
                        case 4:
                            AppRestrictionController appRestrictionController5 = (AppRestrictionController) this.this$0;
                            Injector injector = appRestrictionController5.mInjector;
                            if (injector.mTelephonyManager == null) {
                                injector.mTelephonyManager = (TelephonyManager) injector.mContext.getSystemService(TelephonyManager.class);
                            }
                            TelephonyManager telephonyManager = injector.mTelephonyManager;
                            if (telephonyManager != null && (arrayList = appRestrictionController5.mCarrierPrivilegesCallbacks) != null) {
                                for (int size5 = arrayList.size() - 1; size5 >= 0; size5--) {
                                    telephonyManager.unregisterCarrierPrivilegesCallback((TelephonyManager.CarrierPrivilegesCallback) arrayList.get(size5));
                                }
                                appRestrictionController5.mCarrierPrivilegesCallbacks = null;
                            }
                            ((AppRestrictionController) this.this$0).registerCarrierPrivilegesCallbacks();
                            return;
                        case 5:
                            if (intent.getIntExtra("android.intent.extra.user_handle", -1) >= 0) {
                                AppRestrictionController appRestrictionController6 = (AppRestrictionController) this.this$0;
                                int size6 = appRestrictionController6.mAppStateTrackers.size();
                                while (i < size6) {
                                    ((BaseAppStateTracker) appRestrictionController6.mAppStateTrackers.get(i)).getClass();
                                    i++;
                                }
                                return;
                            }
                            return;
                        case 6:
                            if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) || (intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1)) < 0) {
                                return;
                            }
                            AppRestrictionController appRestrictionController7 = (AppRestrictionController) this.this$0;
                            appRestrictionController7.refreshAppRestrictionLevelForUid(intExtra2, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM, 0, false);
                            int size7 = appRestrictionController7.mAppStateTrackers.size();
                            while (i < size7) {
                                ((BaseAppStateTracker) appRestrictionController7.mAppStateTrackers.get(i)).getClass();
                                i++;
                            }
                            return;
                        case 7:
                            int intExtra6 = intent.getIntExtra("android.intent.extra.UID", -1);
                            Uri data = intent.getData();
                            if (intExtra6 < 0 || data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                                return;
                            }
                            RestrictionSettings restrictionSettings3 = ((AppRestrictionController) this.this$0).mRestrictionSettings;
                            synchronized (AppRestrictionController.this.mSettingsLock) {
                                try {
                                    int indexOfKey = restrictionSettings3.mRestrictionLevels.indexOfKey(intExtra6);
                                    restrictionSettings3.mRestrictionLevels.delete(intExtra6, schemeSpecificPart);
                                    if (indexOfKey >= 0 && restrictionSettings3.mRestrictionLevels.numElementsForKeyAt(indexOfKey) == 0) {
                                        restrictionSettings3.mRestrictionLevels.deleteAt(indexOfKey);
                                    }
                                } finally {
                                }
                            }
                            if (AppRestrictionController.this.mRestrictionSettingsXmlLoaded.get()) {
                                restrictionSettings3.schedulePersistToXml(UserHandle.getUserId(intExtra6));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                case 1:
                    intent.getAction();
                    String action2 = intent.getAction();
                    action2.getClass();
                    if (action2.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                        AppRestrictionController appRestrictionController8 = (AppRestrictionController) this.this$0;
                        int size8 = appRestrictionController8.mAppStateTrackers.size();
                        while (i < size8) {
                            ((BaseAppStateTracker) appRestrictionController8.mAppStateTrackers.get(i)).onLockedBootCompleted();
                            i++;
                        }
                        appRestrictionController8.mLockedBootCompleted = true;
                        return;
                    }
                    return;
                default:
                    intent.getAction();
                    String action3 = intent.getAction();
                    action3.getClass();
                    if (action3.equals("com.android.server.am.ACTION_FGS_MANAGER_TRAMPOLINE")) {
                        ((NotificationHelper) this.this$0).cancelRequestBgRestrictedIfNecessary(intent.getIntExtra("android.intent.extra.UID", 0), intent.getStringExtra("android.intent.extra.PACKAGE_NAME"));
                        Intent intent2 = new Intent("android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER");
                        intent2.addFlags(16777216);
                        ((NotificationHelper) this.this$0).mContext.sendBroadcastAsUser(intent2, UserHandle.SYSTEM);
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BgHandler extends Handler {
        public final Injector mInjector;

        public BgHandler(Looper looper, Injector injector) {
            super(looper);
            this.mInjector = injector;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final AppRestrictionController appRestrictionController = this.mInjector.mAppRestrictionController;
            int i = 0;
            switch (message.what) {
                case 0:
                    int i2 = message.arg1;
                    String str = (String) message.obj;
                    boolean z = message.arg2 == 1;
                    int size = appRestrictionController.mAppStateTrackers.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i3)).onBackgroundRestrictionChanged(i2, z);
                    }
                    int appStandbyBucket = appRestrictionController.mInjector.getAppStandbyInternal().getAppStandbyBucket(str, UserHandle.getUserId(i2), SystemClock.elapsedRealtime(), false);
                    if (z) {
                        appRestrictionController.applyRestrictionLevel(str, i2, 50, appRestrictionController.mEmptyTrackerInfo, appStandbyBucket, true, 1024, 2);
                        appRestrictionController.mBgHandler.obtainMessage(9, i2, 0, str).sendToTarget();
                        return;
                    }
                    RestrictionSettings restrictionSettings = appRestrictionController.mRestrictionSettings;
                    synchronized (AppRestrictionController.this.mSettingsLock) {
                        RestrictionSettings.PkgSettings pkgSettings = (RestrictionSettings.PkgSettings) restrictionSettings.mRestrictionLevels.get(i2, str);
                        if (pkgSettings != null) {
                            i = pkgSettings.mLastRestrictionLevel;
                        }
                    }
                    int i4 = 5;
                    if (appStandbyBucket != 5) {
                        i4 = 40;
                        if (i == 40) {
                            i4 = 45;
                        }
                    }
                    Pair calcAppRestrictionLevel = appRestrictionController.calcAppRestrictionLevel(str, UserHandle.getUserId(i2), i2, i4, false, true);
                    appRestrictionController.applyRestrictionLevel(str, i2, ((Integer) calcAppRestrictionLevel.first).intValue(), (TrackerInfo) calcAppRestrictionLevel.second, appStandbyBucket, true, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE, 3);
                    return;
                case 1:
                    final int i5 = message.arg1;
                    final String str2 = (String) message.obj;
                    final int i6 = message.arg2;
                    appRestrictionController.mRestrictionListeners.forEach(new Consumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((ActivityManagerInternal.AppBackgroundRestrictionListener) obj).onRestrictionLevelChanged(i5, str2, i6);
                        }
                    });
                    return;
                case 2:
                    int i7 = message.arg2;
                    String str3 = (String) message.obj;
                    int i8 = message.arg1;
                    appRestrictionController.getClass();
                    if (!android.app.Flags.appRestrictionsApi() || appRestrictionController.mLockedBootCompleted) {
                        int packageUid = appRestrictionController.mInjector.getPackageManagerInternal().getPackageUid(str3, 819200L, i8);
                        Pair calcAppRestrictionLevel2 = appRestrictionController.calcAppRestrictionLevel(str3, i8, packageUid, i7, false, false);
                        appRestrictionController.applyRestrictionLevel(str3, packageUid, ((Integer) calcAppRestrictionLevel2.first).intValue(), (TrackerInfo) calcAppRestrictionLevel2.second, i7, false, 256, 0);
                        return;
                    }
                    return;
                case 3:
                    String str4 = (String) message.obj;
                    int packageUid2 = appRestrictionController.mInjector.getPackageManagerInternal().getPackageUid(str4, 819200L, message.arg1);
                    int size2 = appRestrictionController.mAppStateTrackers.size();
                    while (i < size2) {
                        ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i)).onUserInteractionStarted(str4, packageUid2);
                        i++;
                    }
                    return;
                case 4:
                    String str5 = (String) message.obj;
                    int i9 = message.arg1;
                    NotificationHelper notificationHelper = appRestrictionController.mNotificationHelper;
                    if (notificationHelper.mBgController.mConstantsObserver.mBgPromptAbusiveAppsToBgRestricted) {
                        Intent intent = new Intent("android.settings.VIEW_ADVANCED_POWER_USAGE_DETAIL");
                        intent.setData(Uri.fromParts("package", str5, null));
                        intent.addFlags(335544320);
                        PendingIntent.getActivityAsUser(notificationHelper.mContext, 0, intent, 201326592, null, UserHandle.of(UserHandle.getUserId(i9)));
                        notificationHelper.mBgController.hasForegroundServices(i9, str5);
                        AppFGSTracker appFGSTracker = notificationHelper.mBgController.mInjector.mAppFGSTracker;
                        synchronized (appFGSTracker.mLock) {
                            appFGSTracker.hasForegroundServiceNotificationsLocked(i9, str5);
                        }
                        boolean z2 = notificationHelper.mBgController.mConstantsObserver.mBgPromptFgsWithNotiToBgRestricted;
                        return;
                    }
                    return;
                case 5:
                    appRestrictionController.handleUidInactive(message.arg1);
                    return;
                case 6:
                    final int i10 = message.arg1;
                    synchronized (appRestrictionController.mSettingsLock) {
                        final AppStandbyInternal appStandbyInternal = appRestrictionController.mInjector.getAppStandbyInternal();
                        final int userId = UserHandle.getUserId(i10);
                        appRestrictionController.mRestrictionSettings.forEachPackageInUidLocked(i10, new TriConsumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda3
                            public final void accept(Object obj, Object obj2, Object obj3) {
                                AppRestrictionController appRestrictionController2 = AppRestrictionController.this;
                                int i11 = i10;
                                final AppStandbyInternal appStandbyInternal2 = appStandbyInternal;
                                final int i12 = userId;
                                final String str6 = (String) obj;
                                Integer num = (Integer) obj2;
                                final Integer num2 = (Integer) obj3;
                                if (appRestrictionController2.mConstantsObserver.mBgAutoRestrictedBucket && num.intValue() == 50) {
                                    appRestrictionController2.mActiveUids.add(i11, str6, new Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AppStandbyInternal appStandbyInternal3 = appStandbyInternal2;
                                            String str7 = str6;
                                            int i13 = i12;
                                            Integer num3 = num2;
                                            appStandbyInternal3.restrictApp(str7, i13, num3.intValue() & 65280, num3.intValue() & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                                        }
                                    });
                                } else {
                                    appRestrictionController2.mActiveUids.add(i11, str6, (Object) null);
                                }
                            }
                        });
                    }
                    return;
                case 7:
                    appRestrictionController.handleUidInactive(message.arg1);
                    int i11 = message.arg1;
                    int size3 = appRestrictionController.mAppStateTrackers.size();
                    while (i < size3) {
                        ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i)).onUidGone(i11);
                        i++;
                    }
                    return;
                case 8:
                    int i12 = message.arg1;
                    int i13 = message.arg2;
                    int size4 = appRestrictionController.mAppStateTrackers.size();
                    while (i < size4) {
                        ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i)).onUidProcStateChanged(i12, i13);
                        i++;
                    }
                    return;
                case 9:
                    appRestrictionController.mNotificationHelper.cancelRequestBgRestrictedIfNecessary(message.arg1, (String) message.obj);
                    return;
                case 10:
                    appRestrictionController.mRestrictionSettings.loadFromXml(true);
                    return;
                case 11:
                    appRestrictionController.mRestrictionSettings.persistToXml(message.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConstantsObserver implements DeviceConfig.OnPropertiesChangedListener {
        public volatile long mBgAbusiveNotificationMinIntervalMs;
        public volatile boolean mBgAutoRestrictAbusiveApps;
        public volatile boolean mBgAutoRestrictedBucket;
        public volatile long mBgLongFgsNotificationMinIntervalMs;
        public volatile boolean mBgPromptAbusiveAppsToBgRestricted;
        public volatile boolean mBgPromptFgsOnLongRunning;
        public volatile boolean mBgPromptFgsWithNotiOnLongRunning;
        public volatile boolean mBgPromptFgsWithNotiToBgRestricted;
        public volatile Set mBgRestrictionExemptedPackages = Collections.emptySet();
        public final boolean mDefaultBgPromptAbusiveAppToBgRestricted;
        public final boolean mDefaultBgPromptFgsWithNotiToBgRestricted;
        public final /* synthetic */ AppRestrictionController this$0;

        public ConstantsObserver(Context context, AppRestrictionController appRestrictionController) {
            this.this$0 = appRestrictionController;
            this.mDefaultBgPromptFgsWithNotiToBgRestricted = context.getResources().getBoolean(R.bool.config_bluetooth_sco_off_call);
            this.mDefaultBgPromptAbusiveAppToBgRestricted = context.getResources().getBoolean(R.bool.config_bluetooth_address_validation);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            String str;
            char c;
            Iterator it = properties.getKeyset().iterator();
            while (it.hasNext() && (str = (String) it.next()) != null && str.startsWith("bg_")) {
                switch (str.hashCode()) {
                    case -1918659497:
                        if (str.equals("bg_prompt_abusive_apps_to_bg_restricted")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1199889595:
                        if (str.equals("bg_auto_restrict_abusive_apps")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -582264882:
                        if (str.equals("bg_prompt_fgs_on_long_running")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -395763044:
                        if (str.equals("bg_auto_restricted_bucket_on_bg_restricted")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -157665503:
                        if (str.equals("bg_restriction_exempted_packages")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 854605367:
                        if (str.equals("bg_abusive_notification_minimal_interval")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 892275457:
                        if (str.equals("bg_long_fgs_notification_minimal_interval")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1771474142:
                        if (str.equals("bg_prompt_fgs_with_noti_on_long_running")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1965398671:
                        if (str.equals("bg_prompt_fgs_with_noti_to_bg_restricted")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        this.mBgPromptAbusiveAppsToBgRestricted = DeviceConfig.getBoolean("activity_manager", "bg_prompt_abusive_apps_to_bg_restricted", this.mDefaultBgPromptAbusiveAppToBgRestricted);
                        break;
                    case 1:
                        this.mBgAutoRestrictAbusiveApps = DeviceConfig.getBoolean("activity_manager", "bg_auto_restrict_abusive_apps", true);
                        break;
                    case 2:
                        this.mBgPromptFgsOnLongRunning = DeviceConfig.getBoolean("activity_manager", "bg_prompt_fgs_on_long_running", false);
                        break;
                    case 3:
                        updateBgAutoRestrictedBucketChanged();
                        break;
                    case 4:
                        updateBgRestrictionExemptedPackages();
                        break;
                    case 5:
                        this.mBgAbusiveNotificationMinIntervalMs = DeviceConfig.getLong("activity_manager", "bg_abusive_notification_minimal_interval", 2592000000L);
                        break;
                    case 6:
                        this.mBgLongFgsNotificationMinIntervalMs = DeviceConfig.getLong("activity_manager", "bg_long_fgs_notification_minimal_interval", 2592000000L);
                        break;
                    case 7:
                        this.mBgPromptFgsWithNotiOnLongRunning = DeviceConfig.getBoolean("activity_manager", "bg_prompt_fgs_with_noti_on_long_running", false);
                        break;
                    case '\b':
                        this.mBgPromptFgsWithNotiToBgRestricted = DeviceConfig.getBoolean("activity_manager", "bg_prompt_fgs_with_noti_to_bg_restricted", this.mDefaultBgPromptFgsWithNotiToBgRestricted);
                        break;
                }
                AppRestrictionController appRestrictionController = this.this$0;
                int size = appRestrictionController.mAppStateTrackers.size();
                for (int i = 0; i < size; i++) {
                    ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i)).mInjector.mAppStatePolicy.onPropertiesChanged(str);
                }
            }
        }

        public final void updateBgAutoRestrictedBucketChanged() {
            boolean z = this.mBgAutoRestrictedBucket;
            this.mBgAutoRestrictedBucket = DeviceConfig.getBoolean("activity_manager", "bg_auto_restricted_bucket_on_bg_restricted", false);
            if (z != this.mBgAutoRestrictedBucket) {
                AppRestrictionController appRestrictionController = this.this$0;
                final boolean z2 = this.mBgAutoRestrictedBucket;
                final AppStandbyInternal appStandbyInternal = appRestrictionController.mInjector.getAppStandbyInternal();
                final ArrayList arrayList = new ArrayList();
                synchronized (appRestrictionController.mSettingsLock) {
                    RestrictionSettings restrictionSettings = appRestrictionController.mRestrictionSettings;
                    for (int numMaps = restrictionSettings.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        int keyAt = restrictionSettings.mRestrictionLevels.keyAt(numMaps);
                        final Integer valueOf = Integer.valueOf(keyAt);
                        appRestrictionController.mRestrictionSettings.forEachPackageInUidLocked(keyAt, new TriConsumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda7
                            public final void accept(Object obj, Object obj2, Object obj3) {
                                Runnable runnable;
                                ArrayList arrayList2 = arrayList;
                                boolean z3 = z2;
                                final AppStandbyInternal appStandbyInternal2 = appStandbyInternal;
                                final Integer num = valueOf;
                                final String str = (String) obj;
                                final Integer num2 = (Integer) obj3;
                                if (((Integer) obj2).intValue() == 50) {
                                    if (z3) {
                                        final int i = 0;
                                        runnable = new Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda8
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i) {
                                                    case 0:
                                                        AppStandbyInternal appStandbyInternal3 = appStandbyInternal2;
                                                        String str2 = str;
                                                        Integer num3 = num;
                                                        Integer num4 = num2;
                                                        appStandbyInternal3.restrictApp(str2, UserHandle.getUserId(num3.intValue()), num4.intValue() & 65280, num4.intValue() & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                                                        break;
                                                    default:
                                                        AppStandbyInternal appStandbyInternal4 = appStandbyInternal2;
                                                        String str3 = str;
                                                        Integer num5 = num;
                                                        Integer num6 = num2;
                                                        appStandbyInternal4.maybeUnrestrictApp(str3, UserHandle.getUserId(num5.intValue()), 65280 & num6.intValue(), num6.intValue() & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE, 6);
                                                        break;
                                                }
                                            }
                                        };
                                    } else {
                                        final int i2 = 1;
                                        runnable = new Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda8
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i2) {
                                                    case 0:
                                                        AppStandbyInternal appStandbyInternal3 = appStandbyInternal2;
                                                        String str2 = str;
                                                        Integer num3 = num;
                                                        Integer num4 = num2;
                                                        appStandbyInternal3.restrictApp(str2, UserHandle.getUserId(num3.intValue()), num4.intValue() & 65280, num4.intValue() & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                                                        break;
                                                    default:
                                                        AppStandbyInternal appStandbyInternal4 = appStandbyInternal2;
                                                        String str3 = str;
                                                        Integer num5 = num;
                                                        Integer num6 = num2;
                                                        appStandbyInternal4.maybeUnrestrictApp(str3, UserHandle.getUserId(num5.intValue()), 65280 & num6.intValue(), num6.intValue() & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE, 6);
                                                        break;
                                                }
                                            }
                                        };
                                    }
                                    arrayList2.add(runnable);
                                }
                            }
                        });
                    }
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    ((Runnable) arrayList.get(i)).run();
                }
                appRestrictionController.mRestrictionListeners.forEach(new Consumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((ActivityManagerInternal.AppBackgroundRestrictionListener) obj).onAutoRestrictedBucketFeatureFlagChanged(z2);
                    }
                });
            }
        }

        public final void updateBgRestrictionExemptedPackages() {
            String string = DeviceConfig.getString("activity_manager", "bg_restriction_exempted_packages", (String) null);
            if (string == null) {
                this.mBgRestrictionExemptedPackages = Collections.emptySet();
                return;
            }
            String[] split = string.split(",");
            ArraySet arraySet = new ArraySet();
            for (String str : split) {
                arraySet.add(str);
            }
            this.mBgRestrictionExemptedPackages = Collections.unmodifiableSet(arraySet);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public ActivityManagerInternal mActivityManagerInternal;
        public AppBatteryExemptionTracker mAppBatteryExemptionTracker;
        public AppBatteryTracker mAppBatteryTracker;
        public AppFGSTracker mAppFGSTracker;
        public AppHibernationService.LocalService mAppHibernationInternal;
        public AppMediaSessionTracker mAppMediaSessionTracker;
        public AppOpsManager mAppOpsManager;
        public AppPermissionTracker mAppPermissionTracker;
        public AppRestrictionController mAppRestrictionController;
        public AppStandbyInternal mAppStandbyInternal;
        public AppStateTracker mAppStateTracker;
        public final Context mContext;
        public NotificationManager mNotificationManager;
        public PackageManagerInternal mPackageManagerInternal;
        public RoleManager mRoleManager;
        public TelephonyManager mTelephonyManager;
        public UserManagerInternal mUserManagerInternal;

        public Injector(Context context) {
            this.mContext = context;
        }

        public final AppStandbyInternal getAppStandbyInternal() {
            if (this.mAppStandbyInternal == null) {
                this.mAppStandbyInternal = (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
            }
            return this.mAppStandbyInternal;
        }

        public final PackageManagerInternal getPackageManagerInternal() {
            if (this.mPackageManagerInternal == null) {
                this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            }
            return this.mPackageManagerInternal;
        }

        public final RoleManager getRoleManager() {
            if (this.mRoleManager == null) {
                this.mRoleManager = (RoleManager) this.mContext.getSystemService(RoleManager.class);
            }
            return this.mRoleManager;
        }

        public final UserManagerInternal getUserManagerInternal() {
            if (this.mUserManagerInternal == null) {
                this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            }
            return this.mUserManagerInternal;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationHelper {
        public final AppRestrictionController mBgController;
        public final Context mContext;
        public final Injector mInjector;
        public final Object mLock;
        public final NotificationManager mNotificationManager;
        public final Object mSettingsLock;
        public static final String[] NOTIFICATION_TYPE_STRINGS = {"Abusive current drain", "Long-running FGS"};
        public static final String[] NOTIFICATION_TIME_ATTRS = {"last_batt_noti_ts", "last_long_fgs_noti_ts"};
        public final AnonymousClass1 mActionButtonReceiver = new AnonymousClass1(2, this);
        public int mNotificationIDStepper = 203105545;

        public NotificationHelper(AppRestrictionController appRestrictionController) {
            this.mBgController = appRestrictionController;
            Injector injector = appRestrictionController.mInjector;
            this.mInjector = injector;
            if (injector.mNotificationManager == null) {
                injector.mNotificationManager = (NotificationManager) injector.mContext.getSystemService(NotificationManager.class);
            }
            this.mNotificationManager = injector.mNotificationManager;
            this.mLock = appRestrictionController.mLock;
            this.mSettingsLock = appRestrictionController.mSettingsLock;
            this.mContext = injector.mContext;
        }

        public final void cancelRequestBgRestrictedIfNecessary(int i, String str) {
            synchronized (this.mSettingsLock) {
                try {
                    RestrictionSettings.PkgSettings restrictionSettingsLocked = this.mBgController.mRestrictionSettings.getRestrictionSettingsLocked(i, str);
                    if (restrictionSettingsLocked != null) {
                        int[] iArr = restrictionSettingsLocked.mNotificationId;
                        int i2 = 0;
                        if (iArr != null) {
                            i2 = iArr[0];
                        }
                        if (i2 > 0) {
                            this.mNotificationManager.cancel(i2);
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhoneCarrierPrivilegesCallback implements TelephonyManager.CarrierPrivilegesCallback {
        public final int mPhoneId;

        public PhoneCarrierPrivilegesCallback(int i) {
            this.mPhoneId = i;
        }

        public final void onCarrierPrivilegesChanged(Set set, Set set2) {
            synchronized (AppRestrictionController.this.mCarrierPrivilegedLock) {
                AppRestrictionController.this.mCarrierPrivilegedApps.put(this.mPhoneId, Collections.unmodifiableSet(set));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RestrictionSettings {
        public final SparseArrayMap mRestrictionLevels = new SparseArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class PkgSettings {
            public long[] mLastNotificationShownTime;
            public long mLevelChangeTime;
            public int[] mNotificationId;
            public final String mPackageName;
            public int mReason;
            public final int mUid;
            public int mLastRestrictionLevel = 0;
            public int mCurrentRestrictionLevel = 0;

            public PkgSettings(String str, int i) {
                this.mPackageName = str;
                this.mUid = i;
            }

            public final Object clone() {
                PkgSettings pkgSettings = RestrictionSettings.this.new PkgSettings(this.mPackageName, this.mUid);
                pkgSettings.mCurrentRestrictionLevel = this.mCurrentRestrictionLevel;
                pkgSettings.mLastRestrictionLevel = this.mLastRestrictionLevel;
                pkgSettings.mLevelChangeTime = this.mLevelChangeTime;
                pkgSettings.mReason = this.mReason;
                long[] jArr = this.mLastNotificationShownTime;
                if (jArr != null) {
                    pkgSettings.mLastNotificationShownTime = Arrays.copyOf(jArr, jArr.length);
                }
                int[] iArr = this.mNotificationId;
                if (iArr != null) {
                    pkgSettings.mNotificationId = Arrays.copyOf(iArr, iArr.length);
                }
                return pkgSettings;
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (obj == null || !(obj instanceof PkgSettings)) {
                    return false;
                }
                PkgSettings pkgSettings = (PkgSettings) obj;
                return pkgSettings.mUid == this.mUid && pkgSettings.mCurrentRestrictionLevel == this.mCurrentRestrictionLevel && pkgSettings.mLastRestrictionLevel == this.mLastRestrictionLevel && pkgSettings.mLevelChangeTime == this.mLevelChangeTime && pkgSettings.mReason == this.mReason && TextUtils.equals(pkgSettings.mPackageName, this.mPackageName) && Arrays.equals(pkgSettings.mLastNotificationShownTime, this.mLastNotificationShownTime) && Arrays.equals(pkgSettings.mNotificationId, this.mNotificationId);
            }

            public void setLastNotificationTime(int i, long j, boolean z) {
                if (this.mLastNotificationShownTime == null) {
                    this.mLastNotificationShownTime = new long[2];
                }
                this.mLastNotificationShownTime[i] = j;
                if (z) {
                    RestrictionSettings restrictionSettings = RestrictionSettings.this;
                    if (AppRestrictionController.this.mRestrictionSettingsXmlLoaded.get()) {
                        restrictionSettings.schedulePersistToXml(UserHandle.getUserId(this.mUid));
                    }
                }
            }

            public void setLevelChangeTime(long j) {
                this.mLevelChangeTime = j;
            }

            public final String toString() {
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "RestrictionLevel{");
                m.append(Integer.toHexString(System.identityHashCode(this)));
                m.append(':');
                m.append(this.mPackageName);
                m.append('/');
                m.append(UserHandle.formatUid(this.mUid));
                m.append("} ");
                m.append(ActivityManager.restrictionLevelToName(this.mCurrentRestrictionLevel));
                m.append('(');
                m.append(UsageStatsManager.reasonToString(this.mReason));
                m.append(')');
                return m.toString();
            }

            public final int update(int i, int i2, int i3) {
                int i4 = this.mCurrentRestrictionLevel;
                if (i != i4) {
                    this.mLastRestrictionLevel = i4;
                    this.mCurrentRestrictionLevel = i;
                    RestrictionSettings restrictionSettings = RestrictionSettings.this;
                    AppRestrictionController.this.mInjector.getClass();
                    this.mLevelChangeTime = System.currentTimeMillis();
                    this.mReason = (i2 & 65280) | (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                    AppRestrictionController.this.mBgHandler.obtainMessage(1, this.mUid, i, this.mPackageName).sendToTarget();
                }
                return this.mLastRestrictionLevel;
            }
        }

        public RestrictionSettings() {
        }

        public final Object clone() {
            RestrictionSettings restrictionSettings = AppRestrictionController.this.new RestrictionSettings();
            synchronized (AppRestrictionController.this.mSettingsLock) {
                try {
                    for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        for (int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                            restrictionSettings.mRestrictionLevels.add(this.mRestrictionLevels.keyAt(numMaps), (String) this.mRestrictionLevels.keyAt(numMaps, numElementsForKeyAt), (PkgSettings) ((PkgSettings) this.mRestrictionLevels.valueAt(numMaps, numElementsForKeyAt)).clone());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return restrictionSettings;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof RestrictionSettings)) {
                return false;
            }
            SparseArrayMap sparseArrayMap = ((RestrictionSettings) obj).mRestrictionLevels;
            synchronized (AppRestrictionController.this.mSettingsLock) {
                try {
                    if (sparseArrayMap.numMaps() != this.mRestrictionLevels.numMaps()) {
                        return false;
                    }
                    for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        int keyAt = this.mRestrictionLevels.keyAt(numMaps);
                        if (sparseArrayMap.numElementsForKey(keyAt) != this.mRestrictionLevels.numElementsForKeyAt(numMaps)) {
                            return false;
                        }
                        for (int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                            PkgSettings pkgSettings = (PkgSettings) this.mRestrictionLevels.valueAt(numMaps, numElementsForKeyAt);
                            if (!pkgSettings.equals(sparseArrayMap.get(keyAt, pkgSettings.mPackageName))) {
                                return false;
                            }
                        }
                    }
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void forEachPackageInUidLocked(int i, TriConsumer triConsumer) {
            int indexOfKey = this.mRestrictionLevels.indexOfKey(i);
            if (indexOfKey < 0) {
                return;
            }
            int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(indexOfKey);
            for (int i2 = 0; i2 < numElementsForKeyAt; i2++) {
                PkgSettings pkgSettings = (PkgSettings) this.mRestrictionLevels.valueAt(indexOfKey, i2);
                triConsumer.accept((String) this.mRestrictionLevels.keyAt(indexOfKey, i2), Integer.valueOf(pkgSettings.mCurrentRestrictionLevel), Integer.valueOf(pkgSettings.mReason));
            }
        }

        public final int getRestrictionLevel(int i) {
            synchronized (AppRestrictionController.this.mSettingsLock) {
                try {
                    int indexOfKey = this.mRestrictionLevels.indexOfKey(i);
                    if (indexOfKey < 0) {
                        return 0;
                    }
                    int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(indexOfKey);
                    if (numElementsForKeyAt == 0) {
                        return 0;
                    }
                    int i2 = 0;
                    for (int i3 = 0; i3 < numElementsForKeyAt; i3++) {
                        PkgSettings pkgSettings = (PkgSettings) this.mRestrictionLevels.valueAt(indexOfKey, i3);
                        if (pkgSettings != null) {
                            int i4 = pkgSettings.mCurrentRestrictionLevel;
                            if (i2 != 0) {
                                i4 = Math.min(i2, i4);
                            }
                            i2 = i4;
                        }
                    }
                    return i2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int getRestrictionLevel(int i, String str) {
            int restrictionLevel;
            synchronized (AppRestrictionController.this.mSettingsLock) {
                try {
                    PkgSettings restrictionSettingsLocked = getRestrictionSettingsLocked(i, str);
                    restrictionLevel = restrictionSettingsLocked == null ? getRestrictionLevel(i) : restrictionSettingsLocked.mCurrentRestrictionLevel;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return restrictionLevel;
        }

        public final PkgSettings getRestrictionSettingsLocked(int i, String str) {
            return (PkgSettings) this.mRestrictionLevels.get(i, str);
        }

        public File getXmlFileNameForUser(int i) {
            AppRestrictionController.this.mInjector.getClass();
            return new File(new File(Environment.getDataSystemDeDirectory(i), "apprestriction"), "settings.xml");
        }

        public void loadFromXml(boolean z) {
            FileInputStream fileInputStream;
            Throwable th;
            AppRestrictionController appRestrictionController = AppRestrictionController.this;
            for (int i : appRestrictionController.mInjector.getUserManagerInternal().getUserIds()) {
                File xmlFileNameForUser = getXmlFileNameForUser(i);
                if (xmlFileNameForUser.exists()) {
                    long[] jArr = new long[2];
                    try {
                        try {
                            FileInputStream fileInputStream2 = new FileInputStream(xmlFileNameForUser);
                            try {
                                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream2);
                                long elapsedRealtime = SystemClock.elapsedRealtime();
                                while (true) {
                                    int next = resolvePullParser.next();
                                    if (next == 1) {
                                        break;
                                    }
                                    if (next == 2) {
                                        String name = resolvePullParser.getName();
                                        if ("settings".equals(name)) {
                                            fileInputStream = fileInputStream2;
                                            try {
                                                loadOneFromXml(resolvePullParser, elapsedRealtime, jArr, z);
                                                fileInputStream2 = fileInputStream;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                th = th;
                                                fileInputStream.close();
                                                throw th;
                                            }
                                        } else {
                                            try {
                                                Slog.w("ActivityManager", "Unexpected tag name: " + name);
                                            } catch (Throwable th3) {
                                                th = th3;
                                                fileInputStream = fileInputStream2;
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th4) {
                                                    th.addSuppressed(th4);
                                                }
                                                throw th;
                                            }
                                        }
                                    }
                                }
                                fileInputStream2.close();
                            } catch (Throwable th5) {
                                th = th5;
                                fileInputStream = fileInputStream2;
                            }
                        } catch (Exception e) {
                            Slog.e("ActivityManager", "Caught exception while trying to load " + xmlFileNameForUser, e);
                            appRestrictionController.errorMsg = e.getMessage();
                        }
                    } catch (IOException | XmlPullParserException unused) {
                        continue;
                    }
                }
            }
            appRestrictionController.mRestrictionSettingsXmlLoaded.set(true);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        public final void loadOneFromXml(TypedXmlPullParser typedXmlPullParser, long j, long[] jArr, boolean z) {
            char c;
            char c2;
            for (int i = 0; i < jArr.length; i++) {
                jArr[i] = 0;
            }
            String str = null;
            int i2 = 256;
            long j2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < typedXmlPullParser.getAttributeCount(); i5++) {
                try {
                    String attributeName = typedXmlPullParser.getAttributeName(i5);
                    String attributeValue = typedXmlPullParser.getAttributeValue(i5);
                    switch (attributeName.hashCode()) {
                        case -934964668:
                            if (attributeName.equals("reason")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -807062458:
                            if (attributeName.equals("package")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 115792:
                            if (attributeName.equals("uid")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 69785859:
                            if (attributeName.equals("levelts")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 569868612:
                            if (attributeName.equals("curlevel")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    if (c == 0) {
                        i4 = Integer.parseInt(attributeValue);
                    } else if (c == 1) {
                        str = attributeValue;
                    } else if (c == 2) {
                        i3 = Integer.parseInt(attributeValue);
                    } else if (c == 3) {
                        j2 = Long.parseLong(attributeValue);
                    } else if (c != 4) {
                        if (attributeName.equals("last_long_fgs_noti_ts")) {
                            c2 = 1;
                        } else {
                            if (!attributeName.equals("last_batt_noti_ts")) {
                                throw new IllegalArgumentException();
                            }
                            c2 = 0;
                        }
                        jArr[c2] = Long.parseLong(attributeValue);
                    } else {
                        i2 = Integer.parseInt(attributeValue);
                    }
                } catch (IllegalArgumentException unused) {
                }
            }
            if (i4 != 0) {
                synchronized (AppRestrictionController.this.mSettingsLock) {
                    try {
                        PkgSettings restrictionSettingsLocked = getRestrictionSettingsLocked(i4, str);
                        if (restrictionSettingsLocked == null) {
                            return;
                        }
                        for (int i6 = 0; i6 < jArr.length; i6++) {
                            long[] jArr2 = restrictionSettingsLocked.mLastNotificationShownTime;
                            if ((jArr2 == null ? 0L : jArr2[i6]) == 0) {
                                long j3 = jArr[i6];
                                if (j3 != 0) {
                                    restrictionSettingsLocked.setLastNotificationTime(i6, j3, false);
                                }
                            }
                        }
                        if (restrictionSettingsLocked.mCurrentRestrictionLevel >= i3) {
                            return;
                        }
                        long j4 = j2;
                        int i7 = i3;
                        int appStandbyBucket = AppRestrictionController.this.mInjector.getAppStandbyInternal().getAppStandbyBucket(str, UserHandle.getUserId(i4), j, false);
                        if (z) {
                            AppRestrictionController appRestrictionController = AppRestrictionController.this;
                            appRestrictionController.applyRestrictionLevel(str, i4, i7, appRestrictionController.mEmptyTrackerInfo, appStandbyBucket, true, i2 & 65280, i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                        } else {
                            restrictionSettingsLocked.update(i7, 65280 & i2, i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                        }
                        synchronized (AppRestrictionController.this.mSettingsLock) {
                            restrictionSettingsLocked.setLevelChangeTime(j4);
                        }
                    } finally {
                    }
                }
            }
        }

        public void persistToXml(int i) {
            FileOutputStream fileOutputStream;
            File xmlFileNameForUser = getXmlFileNameForUser(i);
            File parentFile = xmlFileNameForUser.getParentFile();
            if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Failed to create folder for ", "ActivityManager");
                return;
            }
            AtomicFile atomicFile = new AtomicFile(xmlFileNameForUser);
            try {
                fileOutputStream = atomicFile.startWrite();
            } catch (Exception e) {
                e = e;
                fileOutputStream = null;
            }
            try {
                fileOutputStream.write(toXmlByteArray(i));
                atomicFile.finishWrite(fileOutputStream);
            } catch (Exception e2) {
                e = e2;
                Slog.e("ActivityManager", "Failed to write file " + xmlFileNameForUser, e);
                if (fileOutputStream != null) {
                    atomicFile.failWrite(fileOutputStream);
                }
            }
        }

        public void removeXml() {
            for (int i : AppRestrictionController.this.mInjector.getUserManagerInternal().getUserIds()) {
                getXmlFileNameForUser(i).delete();
            }
        }

        public void reset() {
            synchronized (AppRestrictionController.this.mSettingsLock) {
                try {
                    for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        this.mRestrictionLevels.deleteAt(numMaps);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void resetToDefault() {
            synchronized (AppRestrictionController.this.mSettingsLock) {
                this.mRestrictionLevels.forEach(new AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda2());
            }
        }

        public void scheduleLoadFromXml() {
            AppRestrictionController.this.mBgHandler.sendEmptyMessage(10);
        }

        public void schedulePersistToXml(int i) {
            AppRestrictionController.this.mBgHandler.obtainMessage(11, i, 0).sendToTarget();
        }

        public final byte[] toXmlByteArray(int i) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    synchronized (AppRestrictionController.this.mSettingsLock) {
                        try {
                            for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                                for (int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                                    PkgSettings pkgSettings = (PkgSettings) this.mRestrictionLevels.valueAt(numMaps, numElementsForKeyAt);
                                    int i2 = pkgSettings.mUid;
                                    if (UserHandle.getUserId(i2) == i) {
                                        resolveSerializer.startTag((String) null, "settings");
                                        resolveSerializer.attributeInt((String) null, "uid", i2);
                                        resolveSerializer.attribute((String) null, "package", pkgSettings.mPackageName);
                                        resolveSerializer.attributeInt((String) null, "curlevel", pkgSettings.mCurrentRestrictionLevel);
                                        resolveSerializer.attributeLong((String) null, "levelts", pkgSettings.mLevelChangeTime);
                                        resolveSerializer.attributeInt((String) null, "reason", pkgSettings.mReason);
                                        for (int i3 = 0; i3 < 2; i3++) {
                                            String str = NotificationHelper.NOTIFICATION_TIME_ATTRS[i3];
                                            long[] jArr = pkgSettings.mLastNotificationShownTime;
                                            resolveSerializer.attributeLong((String) null, str, jArr == null ? 0L : jArr[i3]);
                                        }
                                        resolveSerializer.endTag((String) null, "settings");
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    resolveSerializer.endDocument();
                    resolveSerializer.flush();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } catch (IOException unused) {
                return null;
            }
        }

        public final void update(int i, int i2, int i3, int i4, String str) {
            synchronized (AppRestrictionController.this.mSettingsLock) {
                try {
                    PkgSettings restrictionSettingsLocked = getRestrictionSettingsLocked(i, str);
                    if (restrictionSettingsLocked == null) {
                        restrictionSettingsLocked = new PkgSettings(str, i);
                        this.mRestrictionLevels.add(i, str, restrictionSettingsLocked);
                    }
                    restrictionSettingsLocked.update(i2, i3, i4);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrackerInfo {
        public final byte[] mInfo;
        public final int mType;

        public TrackerInfo() {
            this.mType = 0;
            this.mInfo = null;
        }

        public TrackerInfo(int i, byte[] bArr) {
            this.mType = i;
            this.mInfo = bArr;
        }
    }

    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.server.am.AppRestrictionController$4] */
    /* JADX WARN: Type inference failed for: r4v11, types: [com.android.server.am.AppRestrictionController$5] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.server.am.AppRestrictionController$3] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda1] */
    public AppRestrictionController(Context context, ActivityManagerService activityManagerService) {
        Injector injector = new Injector(context);
        ArrayList arrayList = new ArrayList();
        this.mAppStateTrackers = arrayList;
        this.mRestrictionSettings = new RestrictionSettings();
        this.mRestrictionListeners = new CopyOnWriteArraySet();
        this.mActiveUids = new SparseArrayMap();
        this.mTmpRunnables = new ArrayList();
        this.mDeviceIdleAllowlist = new int[0];
        this.mDeviceIdleExceptIdleAllowlist = new int[0];
        this.mSystemDeviceIdleAllowlist = new ArraySet();
        this.mSystemDeviceIdleExceptIdleAllowlist = new ArraySet();
        this.mLock = new Object();
        this.mSettingsLock = new Object();
        this.mRoleHolderChangedListener = new OnRoleHoldersChangedListener() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda1
            public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                AppRestrictionController.this.onRoleHoldersChanged(str, userHandle);
            }
        };
        this.mUidRolesMapping = new SparseArray();
        this.mSystemModulesCache = new HashMap();
        this.mCarrierPrivilegedLock = new Object();
        this.mCarrierPrivilegedApps = new SparseArray();
        this.mRestrictionSettingsXmlLoaded = new AtomicBoolean();
        this.mLockedBootCompleted = false;
        this.errorMsg = null;
        this.mEmptyTrackerInfo = new TrackerInfo();
        this.mBroadcastReceiver = new AnonymousClass1(0, this);
        this.mBootReceiver = new AnonymousClass1(1, this);
        this.mBackgroundRestrictionListener = new AppStateTracker.BackgroundRestrictedAppListener() { // from class: com.android.server.am.AppRestrictionController.3
            public final void updateBackgroundRestrictedForUidPackage(int i, String str, boolean z) {
                AppRestrictionController.this.mBgHandler.obtainMessage(0, i, z ? 1 : 0, str).sendToTarget();
            }
        };
        this.mAppIdleStateChangeListener = new AppStandbyInternal.AppIdleStateChangeListener() { // from class: com.android.server.am.AppRestrictionController.4
            public final void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
                AppRestrictionController.this.mBgHandler.obtainMessage(2, i, i2, str).sendToTarget();
            }

            public final void onUserInteractionStarted(String str, int i) {
                AppRestrictionController.this.mBgHandler.obtainMessage(3, i, 0, str).sendToTarget();
            }
        };
        this.mUidObserver = new UidObserver() { // from class: com.android.server.am.AppRestrictionController.5
            public final void onUidActive(int i) {
                AppRestrictionController.this.mBgHandler.obtainMessage(6, i, 0).sendToTarget();
            }

            public final void onUidGone(int i, boolean z) {
                AppRestrictionController.this.mBgHandler.obtainMessage(7, i, z ? 1 : 0).sendToTarget();
            }

            public final void onUidIdle(int i, boolean z) {
                AppRestrictionController.this.mBgHandler.obtainMessage(5, i, z ? 1 : 0).sendToTarget();
            }

            public final void onUidStateChanged(int i, int i2, long j, int i3) {
                AppRestrictionController.this.mBgHandler.obtainMessage(8, i, i2).sendToTarget();
            }
        };
        this.mInjector = injector;
        this.mContext = context;
        this.mActivityManagerService = activityManagerService;
        HandlerThread handlerThread = new HandlerThread("bgres-controller", 10);
        this.mBgHandlerThread = handlerThread;
        handlerThread.start();
        BgHandler bgHandler = new BgHandler(handlerThread.getLooper(), injector);
        this.mBgHandler = bgHandler;
        this.mBgExecutor = new HandlerExecutor(bgHandler);
        this.mConstantsObserver = new ConstantsObserver(context, this);
        this.mNotificationHelper = new NotificationHelper(this);
        injector.mAppRestrictionController = this;
        injector.mAppBatteryTracker = new AppBatteryTracker(context, this);
        AppBatteryExemptionTracker appBatteryExemptionTracker = new AppBatteryExemptionTracker(context, this);
        appBatteryExemptionTracker.mUidPackageStates = new UidProcessMap();
        long integer = appBatteryExemptionTracker.mContext.getResources().getInteger(R.integer.config_datause_throttle_kbitsps);
        BaseAppStateTracker.Injector injector2 = appBatteryExemptionTracker.mInjector;
        injector2.mAppStatePolicy = new AppBatteryExemptionTracker.AppBatteryExemptionPolicy(injector2, appBatteryExemptionTracker, "bg_battery_exemption_enabled", false, "bg_current_drain_window", integer);
        injector.mAppBatteryExemptionTracker = appBatteryExemptionTracker;
        injector.mAppFGSTracker = new AppFGSTracker(context, this);
        injector.mAppMediaSessionTracker = new AppMediaSessionTracker(context, this);
        injector.mAppPermissionTracker = new AppPermissionTracker(context, this);
        arrayList.add(injector.mAppBatteryTracker);
        arrayList.add(injector.mAppBatteryExemptionTracker);
        arrayList.add(injector.mAppFGSTracker);
        arrayList.add(injector.mAppMediaSessionTracker);
        arrayList.add(injector.mAppPermissionTracker);
        AppBroadcastEventsTracker appBroadcastEventsTracker = new AppBroadcastEventsTracker(context, this);
        BaseAppStateTracker.Injector injector3 = appBroadcastEventsTracker.mInjector;
        injector3.mAppStatePolicy = new AppBroadcastEventsTracker.AppBroadcastEventsPolicy(injector3, appBroadcastEventsTracker, "bg_broadcast_monitor_enabled", "bg_broadcast_window", "bg_ex_broadcast_threshold");
        arrayList.add(appBroadcastEventsTracker);
        AppBindServiceEventsTracker appBindServiceEventsTracker = new AppBindServiceEventsTracker(context, this);
        BaseAppStateTracker.Injector injector4 = appBindServiceEventsTracker.mInjector;
        injector4.mAppStatePolicy = new AppBindServiceEventsTracker.AppBindServiceEventsPolicy(injector4, appBindServiceEventsTracker, "bg_bind_svc_monitor_enabled", "bg_bind_svc_window", "bg_ex_bind_svc_threshold");
        arrayList.add(appBindServiceEventsTracker);
        this.mExecutor = new HandlerExecutor(injector.mAppRestrictionController.mActivityManagerService.mHandler);
    }

    public static int getRestrictionLevelStatsd(int i) {
        if (i == 10) {
            return 1;
        }
        if (i == 20) {
            return 2;
        }
        if (i == 30) {
            return 3;
        }
        if (i == 40) {
            return 4;
        }
        if (i != 50) {
            return i != 60 ? 0 : 6;
        }
        return 5;
    }

    public void addAppStateTracker(BaseAppStateTracker baseAppStateTracker) {
        this.mAppStateTrackers.add(baseAppStateTracker);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    public final void applyRestrictionLevel(final String str, final int i, final int i2, TrackerInfo trackerInfo, int i3, boolean z, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        Object obj;
        int i11;
        int appStandbyBucketReason;
        final AppStandbyInternal appStandbyInternal = this.mInjector.getAppStandbyInternal();
        TrackerInfo trackerInfo2 = trackerInfo == null ? this.mEmptyTrackerInfo : trackerInfo;
        synchronized (this.mSettingsLock) {
            try {
                final int restrictionLevel = this.mRestrictionSettings.getRestrictionLevel(i, str);
                if (restrictionLevel == i2) {
                    return;
                }
                boolean z2 = false;
                int i12 = 20;
                if (i3 != 5) {
                    if (i3 != 10 && i3 != 20 && i3 != 30 && i3 != 40) {
                        if (i3 != 45) {
                            i12 = 50;
                            if (i3 != 50) {
                                i12 = 0;
                            } else if (!android.app.Flags.appRestrictionsApi()) {
                            }
                        } else {
                            i12 = 40;
                        }
                    }
                    i12 = 30;
                }
                ?? r17 = 65280;
                if (i12 != i2 || (appStandbyBucketReason = appStandbyInternal.getAppStandbyBucketReason(str, UserHandle.getUserId(i), SystemClock.elapsedRealtime())) == 0) {
                    i6 = i4;
                    i7 = i5;
                } else {
                    i7 = appStandbyBucketReason & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
                    i6 = appStandbyBucketReason & 65280;
                }
                RestrictionSettings restrictionSettings = this.mRestrictionSettings;
                synchronized (AppRestrictionController.this.mSettingsLock) {
                    RestrictionSettings.PkgSettings pkgSettings = (RestrictionSettings.PkgSettings) restrictionSettings.mRestrictionLevels.get(i, str);
                    i8 = pkgSettings != null ? pkgSettings.mReason : 256;
                }
                int i13 = i8;
                final int i14 = i6;
                final int i15 = i7;
                this.mRestrictionSettings.update(i, i2, i14, i15, str);
                if (android.app.Flags.appRestrictionsApi() || (z && i3 != 5)) {
                    boolean z3 = true;
                    if (i2 < 40 || restrictionLevel >= 40) {
                        i9 = i14;
                        i10 = restrictionLevel;
                        if (i10 >= 40) {
                            if (i2 < 40) {
                                synchronized (this.mSettingsLock) {
                                    try {
                                        if (this.mActiveUids.indexOfKey(i, str) >= 0) {
                                            this.mActiveUids.add(i, str, (Object) null);
                                        }
                                    } finally {
                                    }
                                }
                                appStandbyInternal.maybeUnrestrictApp(str, UserHandle.getUserId(i), i13 & 65280, i13 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, i9, i15);
                                if (!android.app.Flags.appRestrictionsApi()) {
                                    logAppBackgroundRestrictionInfo(str, i, i10, i2, trackerInfo2, i9);
                                }
                            }
                        }
                    } else if (i3 == 45) {
                        i9 = i14;
                        i10 = restrictionLevel;
                    } else if (this.mConstantsObserver.mBgAutoRestrictedBucket || i2 == 40) {
                        Object obj2 = this.mSettingsLock;
                        synchronized (obj2) {
                            try {
                                try {
                                    if (this.mActiveUids.indexOfKey(i, str) >= 0) {
                                        obj = obj2;
                                        i11 = restrictionLevel;
                                        final TrackerInfo trackerInfo3 = trackerInfo2;
                                        this.mActiveUids.add(i, str, new Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                AppRestrictionController appRestrictionController = AppRestrictionController.this;
                                                AppStandbyInternal appStandbyInternal2 = appStandbyInternal;
                                                String str2 = str;
                                                int i16 = i;
                                                int i17 = i14;
                                                int i18 = i15;
                                                int i19 = restrictionLevel;
                                                int i20 = i2;
                                                AppRestrictionController.TrackerInfo trackerInfo4 = trackerInfo3;
                                                appRestrictionController.getClass();
                                                appStandbyInternal2.restrictApp(str2, UserHandle.getUserId(i16), i17, i18);
                                                appRestrictionController.logAppBackgroundRestrictionInfo(str2, i16, i19, i20, trackerInfo4, i17);
                                            }
                                        });
                                    } else {
                                        obj = obj2;
                                        i11 = restrictionLevel;
                                        z2 = true;
                                    }
                                    if (z2) {
                                        i9 = i14;
                                        appStandbyInternal.restrictApp(str, UserHandle.getUserId(i), i9, i15);
                                        if (!android.app.Flags.appRestrictionsApi()) {
                                            logAppBackgroundRestrictionInfo(str, i, i11, i2, trackerInfo2, i9);
                                        }
                                    } else {
                                        i9 = i14;
                                    }
                                    i10 = i11;
                                    z3 = z2;
                                } catch (Throwable th) {
                                    th = th;
                                    r17 = obj2;
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        }
                    } else {
                        i9 = i14;
                        i10 = restrictionLevel;
                    }
                    if (z3 && android.app.Flags.appRestrictionsApi() && i10 != 0) {
                        logAppBackgroundRestrictionInfo(str, i, i10, i2, trackerInfo2, i9);
                    }
                }
            } finally {
            }
        }
    }

    public final Pair calcAppRestrictionLevel(String str, int i, int i2, int i3, boolean z, boolean z2) {
        Injector injector = this.mInjector;
        if (injector.mAppHibernationInternal == null) {
            injector.mAppHibernationInternal = (AppHibernationService.LocalService) LocalServices.getService(AppHibernationService.LocalService.class);
        }
        boolean isHibernatingForUser = injector.mAppHibernationInternal.mService.isHibernatingForUser(str, i);
        TrackerInfo trackerInfo = this.mEmptyTrackerInfo;
        if (isHibernatingForUser) {
            return new Pair(60, trackerInfo);
        }
        int i4 = 20;
        TrackerInfo trackerInfo2 = null;
        if (i3 != 5) {
            if (i3 == 50 && !android.app.Flags.appRestrictionsApi()) {
                i4 = 50;
            } else {
                if (injector.mAppStateTracker == null) {
                    injector.mAppStateTracker = (AppStateTracker) LocalServices.getService(AppStateTracker.class);
                }
                if (injector.mAppStateTracker.isAppBackgroundRestricted(i2, str)) {
                    return new Pair(50, trackerInfo);
                }
                int i5 = i3 == 45 ? 40 : 30;
                if (z2) {
                    Pair calcAppRestrictionLevelFromTackers = calcAppRestrictionLevelFromTackers(i2, 100, str);
                    int intValue = ((Integer) calcAppRestrictionLevelFromTackers.first).intValue();
                    if (intValue == 20) {
                        return new Pair(20, (TrackerInfo) calcAppRestrictionLevelFromTackers.second);
                    }
                    if (intValue > i5) {
                        trackerInfo2 = (TrackerInfo) calcAppRestrictionLevelFromTackers.second;
                        i4 = intValue;
                    } else {
                        i4 = i5;
                    }
                    if (i4 == 50) {
                        if (z) {
                            this.mBgHandler.obtainMessage(4, i2, 0, str).sendToTarget();
                        }
                        Pair calcAppRestrictionLevelFromTackers2 = calcAppRestrictionLevelFromTackers(i2, 50, str);
                        i4 = ((Integer) calcAppRestrictionLevelFromTackers2.first).intValue();
                        trackerInfo2 = (TrackerInfo) calcAppRestrictionLevelFromTackers2.second;
                    }
                } else {
                    i4 = i5;
                }
            }
        }
        return new Pair(Integer.valueOf(i4), trackerInfo2);
    }

    public final Pair calcAppRestrictionLevelFromTackers(int i, int i2, String str) {
        int i3 = 0;
        BaseAppStateTracker baseAppStateTracker = null;
        int i4 = 0;
        for (int size = this.mAppStateTrackers.size() - 1; size >= 0; size--) {
            i3 = Math.max(i3, ((BaseAppStateTracker) this.mAppStateTrackers.get(size)).mInjector.mAppStatePolicy.getProposedRestrictionLevel(i, i2, str));
            if (i3 != i4) {
                baseAppStateTracker = (BaseAppStateTracker) this.mAppStateTrackers.get(size);
                i4 = i3;
            }
        }
        return new Pair(Integer.valueOf(i3), baseAppStateTracker == null ? this.mEmptyTrackerInfo : new TrackerInfo(baseAppStateTracker.getType(), baseAppStateTracker.getTrackerInfoForStatsd(i)));
    }

    public HandlerThread getBackgroundHandlerThread() {
        return this.mBgHandlerThread;
    }

    public final int getBackgroundRestrictionExemptionReason(int i) {
        int potentialSystemExemptionReason = getPotentialSystemExemptionReason(i);
        if (potentialSystemExemptionReason != -1) {
            return potentialSystemExemptionReason;
        }
        String[] packagesForUid = this.mInjector.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                int potentialSystemExemptionReason2 = getPotentialSystemExemptionReason(i, str);
                if (potentialSystemExemptionReason2 != -1) {
                    return potentialSystemExemptionReason2;
                }
            }
            for (String str2 : packagesForUid) {
                int potentialUserAllowedExemptionReason = getPotentialUserAllowedExemptionReason(i, str2);
                if (potentialUserAllowedExemptionReason != -1) {
                    return potentialUserAllowedExemptionReason;
                }
            }
        }
        return -1;
    }

    public final int getPotentialSystemExemptionReason(int i) {
        if (UserHandle.isCore(i)) {
            return 51;
        }
        int appId = UserHandle.getAppId(i);
        if (this.mSystemDeviceIdleAllowlist.contains(Integer.valueOf(appId)) || this.mSystemDeviceIdleExceptIdleAllowlist.contains(Integer.valueOf(appId))) {
            return 300;
        }
        if (UserManager.isDeviceInDemoMode(this.mContext)) {
            return 63;
        }
        int userId = UserHandle.getUserId(i);
        Injector injector = this.mInjector;
        if (injector.getUserManagerInternal().hasUserRestriction("no_control_apps", userId)) {
            return 323;
        }
        if (injector.mActivityManagerInternal == null) {
            injector.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        }
        ActivityManagerInternal activityManagerInternal = injector.mActivityManagerInternal;
        if (activityManagerInternal.isDeviceOwner(i)) {
            return 55;
        }
        if (activityManagerInternal.isProfileOwner(i)) {
            return 56;
        }
        int uidProcessState = activityManagerInternal.getUidProcessState(i);
        if (uidProcessState <= 0) {
            return 10;
        }
        return uidProcessState <= 1 ? 11 : -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x0075 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0056 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPotentialSystemExemptionReason(int r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 311
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.AppRestrictionController.getPotentialSystemExemptionReason(int, java.lang.String):int");
    }

    public final int getPotentialUserAllowedExemptionReason(int i, String str) {
        Injector injector = this.mInjector;
        if (injector.mAppOpsManager == null) {
            injector.mAppOpsManager = (AppOpsManager) injector.mContext.getSystemService(AppOpsManager.class);
        }
        AppOpsManager appOpsManager = injector.mAppOpsManager;
        if (appOpsManager.checkOpNoThrow(47, i, str) == 0) {
            return 68;
        }
        if (appOpsManager.checkOpNoThrow(94, i, str) == 0) {
            return 69;
        }
        if (isRoleHeldByUid(i, "android.app.role.DIALER")) {
            return FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ROLE_DIALER;
        }
        if (isRoleHeldByUid(i, "android.app.role.EMERGENCY")) {
            return FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ROLE_EMERGENCY;
        }
        int appId = UserHandle.getAppId(i);
        if (Arrays.binarySearch(this.mDeviceIdleAllowlist, appId) >= 0 || Arrays.binarySearch(this.mDeviceIdleExceptIdleAllowlist, appId) >= 0) {
            return 65;
        }
        if (injector.mActivityManagerInternal == null) {
            injector.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        }
        return injector.mActivityManagerInternal.isAssociatedCompanionApp(UserHandle.getUserId(i), i) ? 57 : -1;
    }

    public final AppBatteryTracker.ImmutableBatteryUsage getUidBatteryExemptedUsageSince(int i, int i2, long j, long j2) {
        AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage;
        AppBatteryExemptionTracker appBatteryExemptionTracker = this.mInjector.mAppBatteryExemptionTracker;
        if (!((AppBatteryExemptionTracker.AppBatteryExemptionPolicy) appBatteryExemptionTracker.mInjector.mAppStatePolicy).mTrackerEnabled) {
            return AppBatteryTracker.BATTERY_USAGE_NONE;
        }
        synchronized (appBatteryExemptionTracker.mLock) {
            try {
                AppBatteryExemptionTracker.UidBatteryStates uidBatteryStates = (AppBatteryExemptionTracker.UidBatteryStates) appBatteryExemptionTracker.mPkgEvents.get(i, "");
                if (uidBatteryStates != null) {
                    Pair batteryUsageSince = uidBatteryStates.getBatteryUsageSince(i2, j, j2);
                    AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage2 = (AppBatteryTracker.ImmutableBatteryUsage) batteryUsageSince.second;
                    int i3 = 0;
                    while (true) {
                        double[] dArr = immutableBatteryUsage2.mUsage;
                        if (i3 >= dArr.length) {
                            immutableBatteryUsage = (AppBatteryTracker.ImmutableBatteryUsage) batteryUsageSince.first;
                            break;
                        }
                        if (dArr[i3] > 0.0d) {
                            AppBatteryTracker.ImmutableBatteryUsage uidBatteryUsage = appBatteryExemptionTracker.mAppRestrictionController.getUidBatteryUsage(i);
                            AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage3 = (AppBatteryTracker.ImmutableBatteryUsage) batteryUsageSince.first;
                            immutableBatteryUsage3.getClass();
                            AppBatteryTracker.BatteryUsage batteryUsage = new AppBatteryTracker.BatteryUsage(immutableBatteryUsage3);
                            batteryUsage.add(uidBatteryUsage);
                            batteryUsage.subtract((AppBatteryTracker.BatteryUsage) batteryUsageSince.second);
                            immutableBatteryUsage = new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage);
                            break;
                        }
                        i3++;
                    }
                } else {
                    immutableBatteryUsage = AppBatteryTracker.BATTERY_USAGE_NONE;
                }
            } finally {
            }
        }
        return immutableBatteryUsage;
    }

    public final AppBatteryTracker.ImmutableBatteryUsage getUidBatteryUsage(int i) {
        AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage;
        AppBatteryTracker appBatteryTracker = this.mInjector.mAppBatteryTracker;
        appBatteryTracker.mInjector.getClass();
        boolean updateBatteryUsageStatsIfNecessary = appBatteryTracker.updateBatteryUsageStatsIfNecessary(System.currentTimeMillis(), false);
        synchronized (appBatteryTracker.mLock) {
            if (updateBatteryUsageStatsIfNecessary) {
                try {
                    appBatteryTracker.mBgHandler.removeCallbacks(appBatteryTracker.mBgBatteryUsageStatsPolling);
                    appBatteryTracker.scheduleBgBatteryUsageStatsCheck();
                } catch (Throwable th) {
                    throw th;
                }
            }
            AppBatteryTracker.BatteryUsage batteryUsage = (AppBatteryTracker.BatteryUsage) appBatteryTracker.mUidBatteryUsage.get(i);
            immutableBatteryUsage = batteryUsage != null ? new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage) : AppBatteryTracker.BATTERY_USAGE_NONE;
        }
        return immutableBatteryUsage;
    }

    public final void handleUidInactive(int i) {
        ArrayList arrayList = this.mTmpRunnables;
        synchronized (this.mSettingsLock) {
            try {
                int indexOfKey = this.mActiveUids.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                int numElementsForKeyAt = this.mActiveUids.numElementsForKeyAt(indexOfKey);
                for (int i2 = 0; i2 < numElementsForKeyAt; i2++) {
                    Runnable runnable = (Runnable) this.mActiveUids.valueAt(indexOfKey, i2);
                    if (runnable != null) {
                        arrayList.add(runnable);
                    }
                }
                this.mActiveUids.deleteAt(indexOfKey);
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((Runnable) arrayList.get(i3)).run();
                }
                arrayList.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasForegroundServices(int i, String str) {
        boolean z;
        AppFGSTracker appFGSTracker = this.mInjector.mAppFGSTracker;
        synchronized (appFGSTracker.mLock) {
            AppFGSTracker.PackageDurations packageDurations = (AppFGSTracker.PackageDurations) appFGSTracker.mPkgEvents.get(i, str);
            z = packageDurations != null && packageDurations.isActive(AppFGSTracker.PackageDurations.DEFAULT_INDEX);
        }
        return z;
    }

    public final void initRestrictionStates() {
        int[] userIds = this.mInjector.getUserManagerInternal().getUserIds();
        for (int i : userIds) {
            refreshAppRestrictionLevelForUser(i);
        }
        this.mRestrictionSettings.scheduleLoadFromXml();
        for (int i2 : userIds) {
            this.mRestrictionSettings.schedulePersistToXml(i2);
        }
    }

    public final boolean isRoleHeldByUid(int i, String str) {
        boolean z;
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = (ArrayList) this.mUidRolesMapping.get(i);
                z = arrayList != null && arrayList.indexOf(str) >= 0;
            } finally {
            }
        }
        return z;
    }

    public final void loadAppIdsFromPackageList(ArraySet arraySet, ArraySet arraySet2) {
        PackageManager packageManager = this.mInjector.mContext.getPackageManager();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo((String) arraySet.valueAt(size), 1048576);
                if (applicationInfo != null) {
                    arraySet2.add(Integer.valueOf(UserHandle.getAppId(applicationInfo.uid)));
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0099, code lost:
    
        if (r0 == 33) goto L62;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void logAppBackgroundRestrictionInfo(java.lang.String r20, int r21, int r22, int r23, com.android.server.am.AppRestrictionController.TrackerInfo r24, int r25) {
        /*
            Method dump skipped, instructions count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.AppRestrictionController.logAppBackgroundRestrictionInfo(java.lang.String, int, int, int, com.android.server.am.AppRestrictionController$TrackerInfo, int):void");
    }

    public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
        List roleHoldersAsUser = this.mInjector.getRoleManager().getRoleHoldersAsUser(str, userHandle);
        ArraySet arraySet = new ArraySet();
        int identifier = userHandle.getIdentifier();
        if (roleHoldersAsUser != null) {
            PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
            Iterator it = roleHoldersAsUser.iterator();
            while (it.hasNext()) {
                arraySet.add(Integer.valueOf(packageManagerInternal.getPackageUid((String) it.next(), 819200L, identifier)));
            }
        }
        synchronized (this.mLock) {
            try {
                for (int size = this.mUidRolesMapping.size() - 1; size >= 0; size--) {
                    int keyAt = this.mUidRolesMapping.keyAt(size);
                    if (UserHandle.getUserId(keyAt) == identifier) {
                        ArrayList arrayList = (ArrayList) this.mUidRolesMapping.valueAt(size);
                        int indexOf = arrayList.indexOf(str);
                        boolean contains = arraySet.contains(Integer.valueOf(keyAt));
                        if (indexOf >= 0) {
                            if (!contains) {
                                arrayList.remove(indexOf);
                                if (arrayList.isEmpty()) {
                                    this.mUidRolesMapping.removeAt(size);
                                }
                            }
                        } else if (contains) {
                            arrayList.add(str);
                            arraySet.remove(Integer.valueOf(keyAt));
                        }
                    }
                }
                for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(str);
                    this.mUidRolesMapping.put(((Integer) arraySet.valueAt(size2)).intValue(), arrayList2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSystemReady() {
        DeviceConfig.addOnPropertiesChangedListener("activity_manager", this.mBgExecutor, this.mConstantsObserver);
        ConstantsObserver constantsObserver = this.mConstantsObserver;
        constantsObserver.updateBgAutoRestrictedBucketChanged();
        constantsObserver.mBgAutoRestrictAbusiveApps = DeviceConfig.getBoolean("activity_manager", "bg_auto_restrict_abusive_apps", true);
        constantsObserver.mBgAbusiveNotificationMinIntervalMs = DeviceConfig.getLong("activity_manager", "bg_abusive_notification_minimal_interval", 2592000000L);
        constantsObserver.mBgLongFgsNotificationMinIntervalMs = DeviceConfig.getLong("activity_manager", "bg_long_fgs_notification_minimal_interval", 2592000000L);
        constantsObserver.mBgPromptFgsWithNotiToBgRestricted = DeviceConfig.getBoolean("activity_manager", "bg_prompt_fgs_with_noti_to_bg_restricted", constantsObserver.mDefaultBgPromptFgsWithNotiToBgRestricted);
        constantsObserver.mBgPromptFgsWithNotiOnLongRunning = DeviceConfig.getBoolean("activity_manager", "bg_prompt_fgs_with_noti_on_long_running", false);
        constantsObserver.mBgPromptFgsOnLongRunning = DeviceConfig.getBoolean("activity_manager", "bg_prompt_fgs_on_long_running", false);
        constantsObserver.mBgPromptAbusiveAppsToBgRestricted = DeviceConfig.getBoolean("activity_manager", "bg_prompt_abusive_apps_to_bg_restricted", constantsObserver.mDefaultBgPromptAbusiveAppToBgRestricted);
        constantsObserver.updateBgRestrictionExemptedPackages();
        SystemConfig systemConfig = SystemConfig.getInstance();
        this.mBgRestrictionExemptioFromSysConfig = systemConfig.mBgRestrictionExemption;
        loadAppIdsFromPackageList(systemConfig.mAllowInPowerSaveExceptIdle, this.mSystemDeviceIdleExceptIdleAllowlist);
        loadAppIdsFromPackageList(systemConfig.mAllowInPowerSave, this.mSystemDeviceIdleAllowlist);
        initRestrictionStates();
        List<ModuleInfo> installedModules = this.mInjector.mContext.getPackageManager().getInstalledModules(0);
        if (installedModules != null) {
            synchronized (this.mLock) {
                try {
                    Iterator<ModuleInfo> it = installedModules.iterator();
                    while (it.hasNext()) {
                        this.mSystemModulesCache.put(it.next().getPackageName(), Boolean.TRUE);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Injector injector = this.mInjector;
        int[] userIds = injector.getUserManagerInternal().getUserIds();
        String[] strArr = ROLES_IN_INTEREST;
        for (int i = 0; i < 2; i++) {
            String str = strArr[i];
            if (injector.getRoleManager().isRoleAvailable(str)) {
                for (int i2 : userIds) {
                    onRoleHoldersChanged(str, UserHandle.of(i2));
                }
            }
        }
        try {
            this.mInjector.getClass();
            ActivityManager.getService().registerUidObserver(this.mUidObserver, 15, 4, "android");
        } catch (RemoteException unused) {
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        Context context = this.mContext;
        AnonymousClass1 anonymousClass1 = this.mBroadcastReceiver;
        BgHandler bgHandler = this.mBgHandler;
        context.registerReceiverForAllUsers(anonymousClass1, intentFilter, null, bgHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.UID_REMOVED");
        this.mContext.registerReceiverForAllUsers(this.mBroadcastReceiver, intentFilter2, null, bgHandler);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        this.mContext.registerReceiverAsUser(this.mBootReceiver, UserHandle.SYSTEM, intentFilter3, null, this.mBgHandler);
        this.mContext.registerReceiverForAllUsers(this.mBroadcastReceiver, new IntentFilter("android.telephony.action.MULTI_SIM_CONFIG_CHANGED"), null, bgHandler);
        registerCarrierPrivilegesCallbacks();
        NotificationHelper notificationHelper = this.mNotificationHelper;
        notificationHelper.mContext.registerReceiverForAllUsers(notificationHelper.mActionButtonReceiver, new IntentFilter("com.android.server.am.ACTION_FGS_MANAGER_TRAMPOLINE"), "android.permission.MANAGE_ACTIVITY_TASKS", notificationHelper.mBgController.mBgHandler, 4);
        Injector injector2 = this.mInjector;
        if (injector2.mAppStateTracker == null) {
            injector2.mAppStateTracker = (AppStateTracker) LocalServices.getService(AppStateTracker.class);
        }
        injector2.mAppStateTracker.addBackgroundRestrictedAppListener(this.mBackgroundRestrictionListener);
        this.mInjector.getAppStandbyInternal().addListener(this.mAppIdleStateChangeListener);
        this.mInjector.getRoleManager().addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this.mRoleHolderChangedListener, UserHandle.ALL);
        Injector injector3 = this.mInjector;
        BgHandler bgHandler2 = this.mBgHandler;
        Runnable runnable = new Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AppRestrictionController appRestrictionController = AppRestrictionController.this;
                int size = appRestrictionController.mAppStateTrackers.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i3)).onSystemReady();
                }
            }
        };
        injector3.getClass();
        bgHandler2.post(runnable);
    }

    public final void refreshAppRestrictionLevelForUid(int i, int i2, int i3, boolean z) {
        Injector injector = this.mInjector;
        String[] packagesForUid = injector.mContext.getPackageManager().getPackagesForUid(i);
        if (ArrayUtils.isEmpty(packagesForUid)) {
            return;
        }
        AppStandbyInternal appStandbyInternal = injector.getAppStandbyInternal();
        int userId = UserHandle.getUserId(i);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i4 = 0;
        for (int length = packagesForUid.length; i4 < length; length = length) {
            String str = packagesForUid[i4];
            int appStandbyBucket = appStandbyInternal.getAppStandbyBucket(str, userId, elapsedRealtime, false);
            Pair calcAppRestrictionLevel = calcAppRestrictionLevel(str, userId, i, appStandbyBucket, z, true);
            applyRestrictionLevel(str, i, ((Integer) calcAppRestrictionLevel.first).intValue(), (TrackerInfo) calcAppRestrictionLevel.second, appStandbyBucket, true, i2, i3);
            i4++;
        }
    }

    public final void refreshAppRestrictionLevelForUser(int i) {
        Injector injector = this.mInjector;
        List<AppStandbyInfo> appStandbyBuckets = injector.getAppStandbyInternal().getAppStandbyBuckets(i);
        if (ArrayUtils.isEmpty(appStandbyBuckets)) {
            return;
        }
        PackageManagerInternal packageManagerInternal = injector.getPackageManagerInternal();
        for (AppStandbyInfo appStandbyInfo : appStandbyBuckets) {
            int packageUid = packageManagerInternal.getPackageUid(appStandbyInfo.mPackageName, 819200L, i);
            if (packageUid < 0) {
                Slog.e("ActivityManager", "Unable to find " + appStandbyInfo.mPackageName + "/u" + i);
            } else {
                Pair calcAppRestrictionLevel = calcAppRestrictionLevel(appStandbyInfo.mPackageName, i, packageUid, appStandbyInfo.mStandbyBucket, false, false);
                applyRestrictionLevel(appStandbyInfo.mPackageName, packageUid, ((Integer) calcAppRestrictionLevel.first).intValue(), (TrackerInfo) calcAppRestrictionLevel.second, appStandbyInfo.mStandbyBucket, true, 1024, 2);
            }
        }
    }

    public final void registerCarrierPrivilegesCallbacks() {
        Injector injector = this.mInjector;
        if (injector.mTelephonyManager == null) {
            injector.mTelephonyManager = (TelephonyManager) injector.mContext.getSystemService(TelephonyManager.class);
        }
        TelephonyManager telephonyManager = injector.mTelephonyManager;
        if (telephonyManager == null) {
            return;
        }
        int activeModemCount = telephonyManager.getActiveModemCount();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < activeModemCount; i++) {
            PhoneCarrierPrivilegesCallback phoneCarrierPrivilegesCallback = new PhoneCarrierPrivilegesCallback(i);
            arrayList.add(phoneCarrierPrivilegesCallback);
            telephonyManager.registerCarrierPrivilegesCallback(i, this.mExecutor, phoneCarrierPrivilegesCallback);
        }
        this.mCarrierPrivilegesCallbacks = arrayList;
    }

    public void resetRestrictionSettings() {
        synchronized (this.mSettingsLock) {
            this.mRestrictionSettings.reset();
        }
        initRestrictionStates();
    }

    public void tearDown() {
        DeviceConfig.removeOnPropertiesChangedListener(this.mConstantsObserver);
        try {
            this.mInjector.getClass();
            ActivityManager.getService().unregisterUidObserver(this.mUidObserver);
        } catch (RemoteException unused) {
        }
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mBootReceiver);
        this.mRestrictionSettings.removeXml();
    }
}
