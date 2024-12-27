package com.android.server.am;

import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.RecentTasks;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import com.google.android.collect.Lists;
import com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser;
import com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.pm.PmLog;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ActivityManagerServiceExt {
    public static final int MAX_LONG_LIVE_APP;
    public ActivityTaskManagerService mAtmService;
    public final BgHandler mBgHandler;
    public final AnonymousClass1 mBrAllowListCallback;
    public boolean mCanTmoPkgAvoidForceStop;
    public final Context mContext;
    public final Handler mDeXHandler;
    public ArrayList mDeXKillProcesses;
    public final Set mForceKeepAliveProcesses;
    public final AnonymousClass2 mForceKillForDeXRunnable;
    public final Set mForceKillPackages;
    public final Set mForceStopReasons;
    public final MetaDataCollector mMetaDataCollector;
    public SharedPreferences mPref;
    public final HashSet mRelaxedBroadcastActions;
    public final ActivityManagerService mService;
    public final ArrayList persistentApps;
    public static final String CSC_VERSION = SystemProperties.get("ril.official_cscver", "DUMMY");
    public static final File PRE_BOOT_CSC_FILE =
            new File(new File(Environment.getDataDirectory(), "system"), "pre_boot_csc.dat");
    public BroadcastReceiverListParser mParser = new BroadcastReceiverListParserWithScpm();
    public final Map mBroadcastReceiverNotInAllowlist = new ArrayMap();
    public final ArrayList mLongLiveAppByPackages = new ArrayList();
    public final ArrayList mLongLiveCallbacks = new ArrayList();
    public Optional mCb4Process = Optional.empty();
    public final ArrayMap mBrMap = new ArrayMap();
    public final ArrayList mExceptionList = new ArrayList(List.of("android"));
    public final ArrayList mDiscardList = new ArrayList();

    /* renamed from: com.android.server.am.ActivityManagerServiceExt$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            MetaDataCollector metaDataCollector;
            boolean z;
            switch (this.$r8$classId) {
                case 0:
                    ActivityManagerService activityManagerService =
                            ((ActivityManagerServiceExt) this.this$0).mService;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            ArrayList arrayList =
                                    ((ActivityManagerServiceExt) this.this$0).mDeXKillProcesses;
                            if (arrayList != null) {
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    ActivityManagerServiceExt activityManagerServiceExt =
                                            (ActivityManagerServiceExt) this.this$0;
                                    activityManagerServiceExt.forceKillProcessesForDeXExitLocked(
                                            (ProcessRecord)
                                                    activityManagerServiceExt.mDeXKillProcesses.get(
                                                            i));
                                }
                            }
                        } finally {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    synchronized (((MetaDataCollector) this.this$0)) {
                        metaDataCollector = (MetaDataCollector) this.this$0;
                        z = metaDataCollector.mLoaded;
                    }
                    if (ActivityManagerServiceExt.this.mService.mSystemReady && !z) {
                        MetaDataCollector metaDataCollector2 = (MetaDataCollector) this.this$0;
                        if (ActivityManagerServiceExt.this.mContext.getPackageManager() == null) {
                            Slog.v("MetaDataCollector", "PackageManager is not ready yet.");
                        } else {
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                            intentFilter.addDataScheme("package");
                            ActivityManagerServiceExt activityManagerServiceExt2 =
                                    ActivityManagerServiceExt.this;
                            activityManagerServiceExt2.mContext.registerReceiverAsUser(
                                    metaDataCollector2.mPackageReceiver,
                                    UserHandle.ALL,
                                    intentFilter,
                                    null,
                                    activityManagerServiceExt2.mBgHandler);
                            Slog.v("MetaDataCollector", "begin");
                            List<PackageInfo> installedPackages =
                                    ActivityManagerServiceExt.this
                                            .mContext
                                            .getPackageManager()
                                            .getInstalledPackages(128);
                            synchronized (metaDataCollector2) {
                                try {
                                    Iterator<PackageInfo> it = installedPackages.iterator();
                                    while (it.hasNext()) {
                                        metaDataCollector2.loadMetaDataOnceLocked(
                                                it.next().applicationInfo);
                                        metaDataCollector2.mLoaded = true;
                                    }
                                    Slog.v("MetaDataCollector", "loadInternal() done.");
                                } finally {
                                }
                            }
                        }
                    }
                    MetaDataCollector.m171$$Nest$mscheduleLoad((MetaDataCollector) this.this$0);
                    return;
            }
        }
    }

    public final class BgHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
        }
    }

    public final class BrCountInfo {
        public int mCnt;
        public int mMaxCnt;
        public long mTotalCnt;
    }

    public final class LongLiveCallback {
        public final String packageName;
        public final WeakReference ref;

        public LongLiveCallback(ProcessRecord processRecord, String str) {
            this.ref = new WeakReference(processRecord);
            this.packageName = str;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.packageName);
            sb.append(":");
            sb.append(
                    this.ref.get() == null
                            ? PackageManagerShellCommandDataLoader.STDIN_PATH
                            : Integer.valueOf(((ProcessRecord) this.ref.get()).mPid));
            return sb.toString();
        }
    }

    public final class MetaDataCollector {
        public final Map mBooleanMetaDataMap = new HashMap();
        public final String[] mBooleanMetaDataKeys = {
            "com.samsung.android.persistent.downloadable"
        };
        public final Map mStringMetaDataMap = new HashMap();
        public final String[] mStringMetaDataKeys = {
            "com.samsung.android.dex.kill_process_timeout"
        };
        public boolean mLoaded = false;
        public final AnonymousClass2 mLoadRunnable = new AnonymousClass2(1, this);
        public final AnonymousClass2 mPackageReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.am.ActivityManagerServiceExt.MetaDataCollector.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Uri data;
                        String action = intent.getAction();
                        Slog.d("MetaDataCollector", "onReceive: begin action=" + action);
                        String str = null;
                        if (("android.intent.action.PACKAGE_ADDED".equals(action)
                                        || "android.intent.action.PACKAGE_REMOVED".equals(action)
                                        || "android.intent.action.PACKAGE_REPLACED".equals(action))
                                && (data = intent.getData()) != null) {
                            str = data.getSchemeSpecificPart();
                        }
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                "onReceive: done pkgName=", str, "MetaDataCollector");
                        if (str == null) {
                            return;
                        }
                        try {
                            ApplicationInfo applicationInfo =
                                    AppGlobals.getPackageManager()
                                            .getApplicationInfo(str, 128L, context.getUserId());
                            synchronized (MetaDataCollector.this) {
                                MetaDataCollector.this.loadMetaDataOnceLocked(applicationInfo);
                            }
                            if (applicationInfo != null) {
                                ActivityManagerService activityManagerService =
                                        ActivityManagerServiceExt.this.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService) {
                                    try {
                                        ProcessRecord processRecordLocked =
                                                ActivityManagerServiceExt.this.mService.mProcessList
                                                        .getProcessRecordLocked(
                                                                applicationInfo.uid, str);
                                        if (processRecordLocked != null) {
                                            ActivityManagerServiceExt.this.mService
                                                    .parseDexKillProcessTimeout(
                                                            processRecordLocked);
                                        }
                                    } catch (Throwable th) {
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        } catch (RemoteException e) {
                            Slog.v(
                                    "MetaDataCollector",
                                    "getApplicationInfo: failed for " + str + " - " + e,
                                    e);
                        }
                    }
                };

        /* renamed from: -$$Nest$mhasBooleanMetaData, reason: not valid java name */
        public static boolean m170$$Nest$mhasBooleanMetaData(
                MetaDataCollector metaDataCollector, String str, String str2) {
            boolean z;
            Set set;
            Bundle bundle;
            synchronized (metaDataCollector) {
                z = metaDataCollector.mLoaded;
                set = (Set) ((HashMap) metaDataCollector.mBooleanMetaDataMap).get(str2);
            }
            boolean z2 = false;
            if (!z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ApplicationInfo applicationInfo =
                            AppGlobals.getPackageManager()
                                    .getApplicationInfo(
                                            str,
                                            128L,
                                            ActivityManagerServiceExt.this.mContext.getUserId());
                    if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                        z2 = bundle.getBoolean(str2);
                    }
                } catch (RemoteException e) {
                    Slog.v("MetaDataCollector", "getApplicationInfo failed: " + e, e);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
                return z2;
            }
            if (set != null) {
                synchronized (set) {
                    z2 = set.contains(str);
                }
            }
            return z2;
        }

        /* renamed from: -$$Nest$mscheduleLoad, reason: not valid java name */
        public static void m171$$Nest$mscheduleLoad(MetaDataCollector metaDataCollector) {
            boolean z;
            synchronized (metaDataCollector) {
                z = metaDataCollector.mLoaded;
            }
            if (ActivityManagerServiceExt.this.mService.mSystemReady && z) {
                return;
            }
            ActivityManagerServiceExt.this.mBgHandler.removeCallbacks(
                    metaDataCollector.mLoadRunnable);
            ActivityManagerServiceExt.this.mBgHandler.postDelayed(
                    metaDataCollector.mLoadRunnable, 1000L);
        }

        /* JADX WARN: Type inference failed for: r2v9, types: [com.android.server.am.ActivityManagerServiceExt$MetaDataCollector$2] */
        public MetaDataCollector() {}

        public final void loadMetaDataOnceLocked(ApplicationInfo applicationInfo) {
            if (applicationInfo == null) {
                return;
            }
            for (String str : this.mBooleanMetaDataKeys) {
                Bundle bundle = applicationInfo.metaData;
                boolean z = bundle == null ? false : bundle.getBoolean(str);
                String str2 = applicationInfo.packageName;
                Set set = (Set) ((HashMap) this.mBooleanMetaDataMap).get(str);
                if (set == null) {
                    set = new HashSet();
                    ((HashMap) this.mBooleanMetaDataMap).put(str, set);
                }
                synchronized (set) {
                    if (z) {
                        try {
                            set.add(str2);
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else {
                        set.remove(str2);
                    }
                }
            }
            for (String str3 : this.mStringMetaDataKeys) {
                Bundle bundle2 = applicationInfo.metaData;
                String string = bundle2 == null ? null : bundle2.getString(str3);
                String str4 = applicationInfo.packageName;
                if (str4 != null && str3 != null) {
                    Pair pair = new Pair(str4, str3);
                    if (string == null) {
                        ((HashMap) this.mStringMetaDataMap).remove(pair);
                    } else {
                        ((HashMap) this.mStringMetaDataMap).put(pair, string);
                    }
                }
            }
        }
    }

    static {
        MAX_LONG_LIVE_APP =
                CoreRune.FW_DEDICATED_MEMORY ? DynamicHiddenApp.MAX_NEVERKILLEDAPP_NUM : 1;
    }

    public ActivityManagerServiceExt(
            Context context, ActivityManagerService activityManagerService) {
        PackageFeatureCallback packageFeatureCallback =
                new PackageFeatureCallback() { // from class:
                                               // com.android.server.am.ActivityManagerServiceExt.1
                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                    public final void onPackageFeatureDataChanged(
                            PackageFeatureData packageFeatureData) {}

                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                    public final void onUnformattedPackageFeatureFileChanged(
                            String str, Function function) {
                        FileDescriptor fileDescriptor = (FileDescriptor) function.apply(str);
                        if (fileDescriptor == null) {
                            return;
                        }
                        ActivityManagerService activityManagerService2 =
                                ActivityManagerServiceExt.this.mService;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService2) {
                            try {
                                if (!(ActivityManagerServiceExt.this.mParser
                                        instanceof BroadcastReceiverListParserWithScpm)) {
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                BroadcastReceiverListParser updateParserIfNeeded =
                                        BroadcastReceiverListParserWithScpm.updateParserIfNeeded(
                                                fileDescriptor);
                                if (updateParserIfNeeded != null) {
                                    ActivityManagerServiceExt.this.mParser = updateParserIfNeeded;
                                    boolean isWorkCompChangedEnabled =
                                            updateParserIfNeeded.isWorkCompChangedEnabled();
                                    if (PMRune.PM_WA_WORK_COMP_CHANGED
                                            != isWorkCompChangedEnabled) {
                                        PMRune.PM_WA_WORK_COMP_CHANGED = isWorkCompChangedEnabled;
                                        PmLog.logDebugInfoAndLogcat(
                                                "PM_WA_WORK_COMP_CHANGED change to "
                                                        + isWorkCompChangedEnabled);
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            } catch (Throwable th) {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                    }
                };
        HashSet hashSet = new HashSet();
        this.mForceStopReasons = hashSet;
        HashSet hashSet2 = new HashSet();
        this.mForceKillPackages = hashSet2;
        HashSet hashSet3 = new HashSet();
        this.mForceKeepAliveProcesses = hashSet3;
        this.mForceKillForDeXRunnable = new AnonymousClass2(0, this);
        this.mDeXKillProcesses = null;
        this.mRelaxedBroadcastActions = null;
        new HashSet();
        this.persistentApps = Lists.newArrayList(new String[] {Constants.SYSTEMUI_PACKAGE_NAME});
        this.mContext = context;
        this.mService = activityManagerService;
        this.mParser.parseAllowList();
        PackageFeature.BROADCAST_RECEIVER_ALLOW_LIST.registerCallback(packageFeatureCallback);
        String[] stringArray = context.getResources().getStringArray(17236300);
        if (stringArray.length > 0) {
            this.mRelaxedBroadcastActions = new HashSet(Arrays.asList(stringArray));
        }
        new ThreadLocal();
        new ArrayList();
        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS zzz");
        new Date();
        this.mBgHandler = new BgHandler(BackgroundThread.get().getLooper(), null, false);
        MetaDataCollector metaDataCollector = new MetaDataCollector();
        this.mMetaDataCollector = metaDataCollector;
        MetaDataCollector.m171$$Nest$mscheduleLoad(metaDataCollector);
        hashSet2.add("com.tencent.mm");
        hashSet3.add("com.tencent.mm:exdevice");
        hashSet.add("installPackageLI");
        hashSet.add("pkg removed");
        hashSet.add("pkg changed");
        this.mDeXHandler = new Handler(context.getMainLooper());
        Slog.v("ActivityManagerServiceExt", "ActivityManagerServiceExt :: created");
    }

    public static void updateNeverKill(
            RemoteCallback remoteCallback, boolean z, String str, int i) {
        if (i <= 0) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Slog.v("ActivityManagerServiceExt", "updateNeverKill:" + str + "," + i + "," + z);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_NEVER_KILL_PACKAGE", str);
            bundle.putInt("KEY_NEVER_KILL_PID", i);
            remoteCallback.sendResult(bundle);
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean addLongLivePackageLocked(String str) {
        Slog.i(
                "ActivityManagerServiceExt",
                "longLivePackage " + this.mLongLiveAppByPackages + " + " + str);
        if (this.mLongLiveAppByPackages.contains(str)
                || this.mLongLiveAppByPackages.size() >= MAX_LONG_LIVE_APP) {
            return false;
        }
        this.mLongLiveAppByPackages.add(str);
        markAsLongLivePackageIfAliveLocked(str);
        return saveLongLivePackage();
    }

    public final void dumpLongLivePackageLocked(PrintWriter printWriter, boolean z) {
        if (this.mLongLiveAppByPackages.isEmpty()) {
            return;
        }
        printWriter.println();
        if (z) {
            printWriter.println(
                    "-------------------------------------------------------------------------------");
        }
        printWriter.println("ACTIVITY MANAGER - LONG LIVE APP");
        printWriter.print("    longLiveAppByPackages: ");
        printWriter.println(this.mLongLiveAppByPackages);
        printWriter.print("    Max=");
        printWriter.println(MAX_LONG_LIVE_APP);
    }

    public final void forceKillProcessesForDeXExitLocked(ProcessRecord processRecord) {
        if (processRecord.mState.mSetProcState > 13) {
            processRecord.mRemoved = true;
            this.mService.mProcessList.removeProcessLocked(
                    processRecord, false, true, 13, 0, "kill dex related process when exit", true);
            BootReceiver$$ExternalSyntheticOutline0.m(
                    new StringBuilder("kill dex related process succeeded - "),
                    processRecord.processName,
                    "ActivityManagerServiceExt");
        } else {
            StringBuilder sb =
                    new StringBuilder("kill dex related process failed for procstate - ");
            sb.append(processRecord.processName);
            sb.append(":");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    sb, processRecord.mState.mSetProcState, "ActivityManagerServiceExt");
        }
    }

    public final boolean forceStopPackageLocked(
            int i, int i2, int i3, boolean z, boolean z2, String str, String str2) {
        boolean z3;
        if (MetaDataCollector.m170$$Nest$mhasBooleanMetaData(
                        this.mMetaDataCollector, str, "com.samsung.android.persistent.downloadable")
                && ((HashSet) this.mForceStopReasons).contains(str2)) {
            Slog.v(
                    "ActivityManagerServiceExt",
                    "forceStopPackageLocked with revised flags for " + str);
            z3 = true;
            boolean z4 = z3;
            return this.mService.forceStopPackageLocked(
                    str, i, z4, z, true, z4, z2, false, i2, str2, i3);
        }
        z3 = false;
        boolean z42 = z3;
        return this.mService.forceStopPackageLocked(
                str, i, z42, z, true, z42, z2, false, i2, str2, i3);
    }

    public final ArrayList getDedicatedProcessesLocked(int i) {
        ArrayList arrayList;
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RecentTasks.UserToProcMap userToProcMap =
                        activityTaskManagerService.mRecentTasks.mUserToProcs;
                if (i == -1) {
                    arrayList = new ArrayList();
                    int size = userToProcMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.addAll(userToProcMap.get(userToProcMap.keyAt(i2)).keySet());
                    }
                } else {
                    arrayList = new ArrayList(userToProcMap.get(i).keySet());
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return arrayList;
    }

    public final void initLongLivePackageLocked() {
        if (this.mPref == null) {
            try {
                this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
            } catch (RuntimeException unused) {
            }
        }
        SharedPreferences sharedPreferences = this.mPref;
        if (sharedPreferences != null) {
            try {
                sharedPreferences.getStringSet("LONG_LIVE_BY_PACKAGE", null);
            } catch (ClassCastException unused2) {
                String string = this.mPref.getString("LONG_LIVE_BY_PACKAGE", null);
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "migrateTo Set ", string, "ActivityManagerServiceExt");
                if (this.mPref == null) {
                    this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
                }
                SharedPreferences.Editor edit = this.mPref.edit();
                edit.remove("LONG_LIVE_BY_PACKAGE");
                edit.commit();
                if (!TextUtils.isEmpty(string)) {
                    this.mLongLiveAppByPackages.add(string);
                    saveLongLivePackage();
                    this.mLongLiveAppByPackages.clear();
                }
            }
        }
        Set<String> stringSet = this.mPref.getStringSet("LONG_LIVE_BY_PACKAGE", null);
        if (stringSet != null) {
            this.mLongLiveAppByPackages.addAll(stringSet);
            Iterator it = this.mLongLiveAppByPackages.iterator();
            while (it.hasNext()) {
                markAsLongLivePackageIfAliveLocked((String) it.next());
            }
        }
    }

    public final IntentFilter intentFilterForReceiverAllowlist(
            IIntentReceiver iIntentReceiver, int i, final IntentFilter intentFilter, String str) {
        if (intentFilter == null
                || str == null
                || iIntentReceiver == null
                || !((i == 0 || i == -1) && this.mParser.isInRestrictedPackageList(str))) {
            return intentFilter;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> actionsIterator = intentFilter.actionsIterator();
        if (actionsIterator == null) {
            return intentFilter;
        }
        while (actionsIterator.hasNext()) {
            String next = actionsIterator.next();
            if (this.mParser.getRestricedIntent().contains(next)
                    && !this.mParser.isInAllowList(next, str, intentFilter)) {
                arrayList.add(next);
                if (((ArrayMap) this.mBroadcastReceiverNotInAllowlist).containsKey(next)) {
                    List list = (List) ((ArrayMap) this.mBroadcastReceiverNotInAllowlist).get(next);
                    if (list != null && !list.contains(str)) {
                        list.add(str);
                    }
                } else {
                    ((ArrayMap) this.mBroadcastReceiverNotInAllowlist)
                            .put(next, PortStatus_1_1$$ExternalSyntheticOutline0.m(str));
                }
                Slog.d(
                        "ActivityManagerServiceExt",
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "action:", next, " callerPackage:", str, " is not registered!"));
            }
        }
        arrayList.forEach(
                new Consumer() { // from class:
                                 // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        intentFilter.removeAction((String) obj);
                    }
                });
        return intentFilter;
    }

    public final boolean isNeedRestrictToSendBr(BroadcastRecord broadcastRecord) {
        int i;
        Intent intent = broadcastRecord.intent;
        String str = broadcastRecord.callerPackage;
        if (str == null) {
            str = "android";
        }
        boolean contains = this.mExceptionList.contains(str);
        ArrayMap arrayMap = (ArrayMap) this.mBrMap.get(str);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.mBrMap.put(str, arrayMap);
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            action = "EMPTY_ACTION";
            i = 3000;
        } else {
            i = NetworkConstants.ETHER_MTU;
        }
        BrCountInfo brCountInfo = (BrCountInfo) arrayMap.get(action);
        if (brCountInfo == null) {
            brCountInfo = new BrCountInfo();
            brCountInfo.mCnt = 0;
            brCountInfo.mMaxCnt = 0;
            brCountInfo.mTotalCnt = 0L;
            arrayMap.put(action, brCountInfo);
        }
        int i2 = brCountInfo.mCnt;
        if (i2 < i || contains) {
            broadcastRecord.mCounted = true;
            brCountInfo.mCnt = i2 + 1;
            brCountInfo.mTotalCnt++;
            arrayMap.put(action, brCountInfo);
            return false;
        }
        String intent2 = intent.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .format(new Date(System.currentTimeMillis())));
        sb.append(" from=");
        RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str, " key=", intent2);
        if (this.mDiscardList.size() > 10) {
            this.mDiscardList.remove(0);
        }
        this.mDiscardList.add(sb.toString());
        Slog.d(
                "ActivityManagerServiceExt",
                "Too many Broadcast Requested :: This BR will not be sent from="
                        + str
                        + " intent="
                        + intent);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x013b, code lost:

       if ("force-keep-alive".equals(r9.mAdjType) == false) goto L22;
    */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0108 A[Catch: all -> 0x00cf, TryCatch #5 {all -> 0x00cf, blocks: (B:20:0x0085, B:25:0x0143, B:29:0x00a4, B:30:0x00ac, B:32:0x00b2, B:38:0x00d6, B:40:0x00dc, B:43:0x00fa, B:45:0x0108, B:46:0x0110, B:48:0x0114, B:53:0x0120, B:56:0x0128, B:60:0x0132, B:62:0x013d, B:66:0x00ea, B:81:0x014f, B:76:0x01ae, B:84:0x0160, B:86:0x0169, B:88:0x019e), top: B:19:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0114 A[Catch: all -> 0x00cf, TryCatch #5 {all -> 0x00cf, blocks: (B:20:0x0085, B:25:0x0143, B:29:0x00a4, B:30:0x00ac, B:32:0x00b2, B:38:0x00d6, B:40:0x00dc, B:43:0x00fa, B:45:0x0108, B:46:0x0110, B:48:0x0114, B:53:0x0120, B:56:0x0128, B:60:0x0132, B:62:0x013d, B:66:0x00ea, B:81:0x014f, B:76:0x01ae, B:84:0x0160, B:86:0x0169, B:88:0x019e), top: B:19:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void killAllBackgroundProcessesExcept(int r27, int r28, android.os.Bundle r29) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ActivityManagerServiceExt.killAllBackgroundProcessesExcept(int,"
                    + " int, android.os.Bundle):void");
    }

    public final void markAsLongLivePackageIfAliveLocked(String str) {
        ActivityManagerProcLock activityManagerProcLock = this.mService.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerProcLock) {
            try {
                this.mService.mProcessList.forEachLruProcessesLOSP(
                        new ActivityManagerServiceExt$$ExternalSyntheticLambda0(this, str, 2),
                        false);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final boolean removeLongLivePackageLocked(String str) {
        Slog.i(
                "ActivityManagerServiceExt",
                "longLivePackage " + this.mLongLiveAppByPackages + " - " + str);
        if (!this.mLongLiveAppByPackages.contains(str)) {
            return false;
        }
        this.mLongLiveAppByPackages.remove(str);
        for (int size = this.mLongLiveCallbacks.size() - 1; size >= 0; size--) {
            LongLiveCallback longLiveCallback =
                    (LongLiveCallback) this.mLongLiveCallbacks.get(size);
            if (str.equals(longLiveCallback.packageName)) {
                ProcessRecord processRecord = (ProcessRecord) longLiveCallback.ref.get();
                if (processRecord != null) {
                    processRecord.mDedicated = false;
                    this.mCb4Process.ifPresent(
                            new ActivityManagerServiceExt$$ExternalSyntheticLambda4(
                                    this, longLiveCallback, processRecord, 1));
                }
                this.mLongLiveCallbacks.remove(size);
            }
        }
        return saveLongLivePackage();
    }

    public final void removeLongLivePackageWhenUninstalledLocked(String str) {
        if (this.mLongLiveAppByPackages.contains(str)) {
            Slog.v(
                    "ActivityManagerServiceExt",
                    "Uninstalled:"
                            + this.mLongLiveAppByPackages
                            + PackageManagerShellCommandDataLoader.STDIN_PATH
                            + str);
            this.mLongLiveAppByPackages.remove(str);
            if (this.mLongLiveAppByPackages.size() == 0) {
                this.mLongLiveCallbacks.clear();
            } else {
                for (int size = this.mLongLiveCallbacks.size() - 1; size >= 0; size--) {
                    LongLiveCallback longLiveCallback =
                            (LongLiveCallback) this.mLongLiveCallbacks.get(size);
                    if (((ProcessRecord) longLiveCallback.ref.get()) == null
                            || str.equals(longLiveCallback.packageName)) {
                        this.mLongLiveCallbacks.remove(size);
                    }
                }
            }
            saveLongLivePackage();
        }
    }

    public final void removeLongLiveTaskLocked(int i, String str) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                activityTaskManagerService.mRecentTasks.removeDedicatedProcessFromPackage(i, str);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void restoreAllAdjs() {
        if (this.mLongLiveCallbacks.size() <= 0) {
            return;
        }
        Iterator it = this.mLongLiveCallbacks.iterator();
        while (it.hasNext()) {
            LongLiveCallback longLiveCallback = (LongLiveCallback) it.next();
            ProcessRecord processRecord = (ProcessRecord) longLiveCallback.ref.get();
            if (processRecord != null) {
                processRecord.mDedicated = false;
                this.mCb4Process.ifPresent(
                        new ActivityManagerServiceExt$$ExternalSyntheticLambda4(
                                this, longLiveCallback, processRecord, 0));
            }
        }
        this.mLongLiveCallbacks.clear();
    }

    public final boolean saveLongLivePackage() {
        if (this.mPref == null) {
            this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        SharedPreferences.Editor edit = this.mPref.edit();
        edit.putStringSet("LONG_LIVE_BY_PACKAGE", new ArraySet(this.mLongLiveAppByPackages));
        return edit.commit();
    }

    public final boolean setLongLivePackageLocked(String str) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("setLongLivePackage ", str, "+");
        m.append(this.mLongLiveAppByPackages);
        Slog.v("ActivityManagerServiceExt", m.toString());
        if (TextUtils.isEmpty(str)) {
            restoreAllAdjs();
            this.mLongLiveAppByPackages.clear();
            if (this.mPref == null) {
                this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
            }
            SharedPreferences.Editor edit = this.mPref.edit();
            edit.remove("LONG_LIVE_BY_PACKAGE");
            return edit.commit();
        }
        if (str.equals(
                this.mLongLiveAppByPackages.isEmpty()
                        ? null
                        : (String) this.mLongLiveAppByPackages.get(0))) {
            return false;
        }
        restoreAllAdjs();
        this.mLongLiveAppByPackages.clear();
        this.mLongLiveAppByPackages.add(str);
        markAsLongLivePackageIfAliveLocked(str);
        return saveLongLivePackage();
    }

    public final void setLongLiveTask(int i, final boolean z) {
        Slog.v("ActivityManagerServiceExt", "setLongLiveTask " + i + "," + z);
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                Pair longLiveTask = this.mAtmService.setLongLiveTask(i, z);
                Object obj = longLiveTask.first;
                if (obj != null) {
                    final String str = (String) obj;
                    final int intValue = ((Integer) longLiveTask.second).intValue();
                    ActivityManagerProcLock activityManagerProcLock = this.mService.mProcLock;
                    ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerProcLock) {
                        try {
                            this.mService.mProcessList.forEachLruProcessesLOSP(
                                    new Consumer() { // from class:
                                                     // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj2) {
                                            final ActivityManagerServiceExt
                                                    activityManagerServiceExt =
                                                            ActivityManagerServiceExt.this;
                                            int i2 = intValue;
                                            final String str2 = str;
                                            final boolean z2 = z;
                                            final ProcessRecord processRecord =
                                                    (ProcessRecord) obj2;
                                            activityManagerServiceExt.getClass();
                                            if (i2 == processRecord.userId
                                                    && processRecord.processName.equals(str2)) {
                                                processRecord.mDedicated = z2;
                                                activityManagerServiceExt.mCb4Process.ifPresent(
                                                        new Consumer() { // from class:
                                                                         // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda11
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj3) {
                                                                ActivityManagerServiceExt
                                                                        activityManagerServiceExt2 =
                                                                                ActivityManagerServiceExt
                                                                                        .this;
                                                                boolean z3 = z2;
                                                                String str3 = str2;
                                                                ProcessRecord processRecord2 =
                                                                        processRecord;
                                                                activityManagerServiceExt2
                                                                        .getClass();
                                                                ActivityManagerServiceExt
                                                                        .updateNeverKill(
                                                                                (RemoteCallback)
                                                                                        obj3,
                                                                                z3,
                                                                                str3,
                                                                                processRecord2
                                                                                        .mPid);
                                                            }
                                                        });
                                            }
                                        }
                                    },
                                    false);
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }
}
