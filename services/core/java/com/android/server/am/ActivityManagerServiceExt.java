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
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
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
import android.util.SparseArray;
import android.util.TimeUtils;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.wm.ActivityTaskManagerService;
import com.google.android.collect.Lists;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.PmLog;
import com.samsung.android.server.p025pm.allowlist.BroadcastReceiverListParser;
import com.samsung.android.server.p025pm.allowlist.BroadcastReceiverListParserWithScpm;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.CharArrayWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ActivityManagerServiceExt {
  public static int MAX_LONG_LIVE_APP;
  public final String TMO_PKG_NAME;
  public ActivityTaskManagerService mAtmService;
  public Handler mBgHandler;
  public boolean mCanTmoPkgAvoidForceStop;
  public final Context mContext;
  public Handler mDeXHandler;
  public ArrayList mDeXKillProcesses;
  public final HashSet mDexPrimaryProcessList;
  public final Set mForceKeepAliveProcesses;
  public final Runnable mForceKillForDeXRunnable;
  public final Set mForceKillPackages;
  public LocaleChangedHistory mLocaleChangedHistory;
  public MetaDataCollector mMetaDataCollector;
  public SharedPreferences mPref;
  public HashSet mRelaxedBroadcastActions;
  public final ActivityManagerService mService;
  public final ArrayList persistentApps;
  public static final String CSC_VERSION = SystemProperties.get("ril.official_cscver", "DUMMY");
  public static final File PRE_BOOT_CSC_FILE =
      new File(new File(Environment.getDataDirectory(), "system"), "pre_boot_csc.dat");
  public BroadcastReceiverListParser mParser = new BroadcastReceiverListParserWithScpm();
  public Map mBroadcastReceiverNotInAllowlist = new ArrayMap();
  public ArrayList mLongLiveAppByPackages = new ArrayList();
  public ArrayList mLongLiveCallbacks = new ArrayList();
  public Optional mCb4Process = Optional.empty();
  public ArrayMap mBrMap = new ArrayMap();
  public ArrayList mExceptionList = new ArrayList(List.of("android"));
  public ArrayList mDiscardList = new ArrayList();
  public final int MAX_DISCARD_BR_HISTORY = 10;
  public final int MAX_ALLOWED_BR_CNT = NetworkConstants.ETHER_MTU;
  public final int MAX_ALLOWED_BR_CNT_FOR_NULL = 3000;
  public PackageFeatureCallback mBrAllowListCallback =
      new PackageFeatureCallback() { // from class:
                                     // com.android.server.am.ActivityManagerServiceExt.1
        @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
        public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {}

        @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
        public void onUnformattedPackageFeatureFileChanged(String str, Function function) {
          FileDescriptor fileDescriptor = (FileDescriptor) function.apply(str);
          if (fileDescriptor == null) {
            return;
          }
          ActivityManagerService activityManagerService = ActivityManagerServiceExt.this.mService;
          ActivityManagerService.boostPriorityForLockedSection();
          synchronized (activityManagerService) {
            try {
              if (!(ActivityManagerServiceExt.this.mParser
                  instanceof BroadcastReceiverListParserWithScpm)) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
              }
              BroadcastReceiverListParser updateParserIfNeeded =
                  BroadcastReceiverListParserWithScpm.updateParserIfNeeded(fileDescriptor);
              if (updateParserIfNeeded != null) {
                ActivityManagerServiceExt.this.mParser = updateParserIfNeeded;
                boolean isWorkCompChangedEnabled =
                    ActivityManagerServiceExt.this.mParser.isWorkCompChangedEnabled();
                if (PMRune.PM_WA_WORK_COMP_CHANGED != isWorkCompChangedEnabled) {
                  PMRune.PM_WA_WORK_COMP_CHANGED = isWorkCompChangedEnabled;
                  PmLog.logDebugInfoAndLogcat(
                      "PM_WA_WORK_COMP_CHANGED change to " + isWorkCompChangedEnabled);
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
  public Set mForceStopReasons = new HashSet();

  /* JADX WARN: Multi-variable type inference failed */
  public ActivityManagerServiceExt(Context context, ActivityManagerService activityManagerService) {
    HashSet hashSet = new HashSet();
    this.mForceKillPackages = hashSet;
    HashSet hashSet2 = new HashSet();
    this.mForceKeepAliveProcesses = hashSet2;
    this.mForceKillForDeXRunnable =
        new Runnable() { // from class: com.android.server.am.ActivityManagerServiceExt.2
          @Override // java.lang.Runnable
          public void run() {
            ActivityManagerService activityManagerService2 =
                ActivityManagerServiceExt.this.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService2) {
              try {
                if (ActivityManagerServiceExt.this.mDeXKillProcesses != null) {
                  int size = ActivityManagerServiceExt.this.mDeXKillProcesses.size();
                  for (int i = 0; i < size; i++) {
                    ActivityManagerServiceExt activityManagerServiceExt =
                        ActivityManagerServiceExt.this;
                    activityManagerServiceExt.forceKillProcessesForDeXExitLocked(
                        (ProcessRecord) activityManagerServiceExt.mDeXKillProcesses.get(i));
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
    this.mDeXKillProcesses = null;
    this.mRelaxedBroadcastActions = null;
    this.mDexPrimaryProcessList = new HashSet();
    this.persistentApps = Lists.newArrayList(new String[] {"com.android.systemui"});
    this.TMO_PKG_NAME = "com.tmobile.echolocate";
    this.mContext = context;
    this.mService = activityManagerService;
    this.mParser.parseAllowList();
    PackageFeature.BROADCAST_RECEIVER_ALLOW_LIST.registerCallback(this.mBrAllowListCallback);
    initRelaxedBroadcastActions();
    this.mLocaleChangedHistory = new LocaleChangedHistory();
    this.mBgHandler = new BgHandler(BackgroundThread.get().getLooper());
    MetaDataCollector metaDataCollector = new MetaDataCollector();
    this.mMetaDataCollector = metaDataCollector;
    metaDataCollector.scheduleLoad();
    hashSet.add("com.tencent.mm");
    hashSet2.add("com.tencent.mm:exdevice");
    this.mForceStopReasons.add("installPackageLI");
    this.mForceStopReasons.add("pkg removed");
    this.mForceStopReasons.add("pkg changed");
    this.mDeXHandler = new Handler(context.getMainLooper());
    Slog.v("ActivityManagerServiceExt", "ActivityManagerServiceExt :: created");
  }

  public void setAtmService(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtmService = activityTaskManagerService;
  }

  public String[] queryRegisteredReceiverPackages(Intent intent, String str, int i) {
    String[] strArr;
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    int appId = UserHandle.getAppId(callingUid);
    if (appId != 1000
        && appId != 1001
        && appId != 2000
        && appId != 1002
        && appId != 1027
        && callingUid != 0) {
      String str2 =
          "Permission Denial: not allowed to query registered receiver packages from pid="
              + callingPid
              + ", uid="
              + callingUid;
      Slog.w("ActivityManagerServiceExt", str2);
      throw new SecurityException(str2);
    }
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        strArr = new String[0];
        Intent verifyBroadcastLocked = this.mService.verifyBroadcastLocked(intent);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          List queryIntent =
              this.mService.mReceiverResolver.queryIntent(
                  this.mService.getPackageManagerInternal().snapshot(),
                  verifyBroadcastLocked,
                  str,
                  false,
                  this.mService.mUserController.handleIncomingUser(
                      callingPid, callingUid, i, true, 0, "queryRegisteredReceiverPackages", null));
          if (queryIntent != null) {
            ArraySet arraySet = new ArraySet();
            for (int i2 = 0; i2 < queryIntent.size(); i2++) {
              arraySet.add(((BroadcastFilter) queryIntent.get(i2)).packageName);
            }
            strArr = (String[]) arraySet.toArray(new String[arraySet.size()]);
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
    return strArr;
  }

  public class LocaleChangedHistory {
    public final ThreadLocal mCallerInfo;
    public final Date mDate;
    public final SimpleDateFormat mDateFormat;
    public final List mHistoryList;

    public LocaleChangedHistory() {
      this.mCallerInfo = new ThreadLocal();
      this.mHistoryList = new ArrayList();
      this.mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS zzz");
      this.mDate = new Date();
    }

    public final void setCallerLocked(String str) {
      this.mCallerInfo.set(str);
    }

    public final void dumpLocked(
        FileDescriptor fileDescriptor, PrintWriter printWriter, boolean z) {
      printWriter.println();
      if (z) {
        printWriter.println(
            "-------------------------------------------------------------------------------");
      }
      printWriter.println("ACTIVITY MANAGER LOCALE CHANGED HISTORY");
      if (this.mHistoryList.size() == 0) {
        printWriter.println(" (nothing) ");
        return;
      }
      int i = 0;
      while (i < this.mHistoryList.size()) {
        LocaleChangedEntry localeChangedEntry = (LocaleChangedEntry) this.mHistoryList.get(i);
        i++;
        printWriter.print(String.format("#%d ", Integer.valueOf(i)));
        printWriter.print(localeChangedEntry.message);
        printWriter.print(" at ");
        this.mDateFormat.setTimeZone(localeChangedEntry.timeZone);
        this.mDate.setTime(localeChangedEntry.systemTimeMillis);
        printWriter.println(this.mDateFormat.format(this.mDate));
        int i2 = 4;
        for (StackTraceElement stackTraceElement : localeChangedEntry.callstack.getStackTrace()) {
          if (i2 == 0) {
            printWriter.println("    " + stackTraceElement);
          } else {
            i2--;
          }
        }
        printWriter.println();
      }
    }

    public final void addLocked(LocaleList localeList, LocaleList localeList2, boolean z) {
      String valueOf = String.valueOf(localeList);
      String valueOf2 = String.valueOf(localeList2);
      if (z || !valueOf.equals(valueOf2)) {
        this.mHistoryList.add(
            new LocaleChangedEntry(
                String.format(
                    "%s changed from %s to %s",
                    this.mCallerInfo.get(), valueOf, valueOf2 + " , forcedUpdate : " + z)));
        if (this.mHistoryList.size() > 10) {
          this.mHistoryList.remove(0);
        }
      }
    }

    public class LocaleChangedEntry {
      public final Throwable callstack;
      public final String message;
      public final long systemTimeMillis = System.currentTimeMillis();
      public final TimeZone timeZone;

      public LocaleChangedEntry(String str) {
        RuntimeException runtimeException = new RuntimeException();
        this.callstack = runtimeException;
        this.timeZone = TimeZone.getDefault();
        this.message = str;
        runtimeException.fillInStackTrace();
      }
    }
  }

  public void setUpdateConfigurationCallerLocked(int i) {
    ProcessRecord processRecord;
    synchronized (this.mService.mPidsSelfLocked) {
      processRecord = this.mService.mPidsSelfLocked.get(i);
    }
    StringBuilder sb = new StringBuilder();
    sb.append(i);
    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
    sb.append(processRecord == null ? null : processRecord.processName);
    this.mLocaleChangedHistory.setCallerLocked(sb.toString());
  }

  public void resetUpdateConfigurationCallerLocked() {
    this.mLocaleChangedHistory.setCallerLocked(null);
  }

  public void dumpLocaleChangedHistoryLocked(
      FileDescriptor fileDescriptor, PrintWriter printWriter, boolean z) {
    this.mLocaleChangedHistory.dumpLocked(fileDescriptor, printWriter, z);
  }

  public void addToLocaleChangedHistoryLocked(
      LocaleList localeList, LocaleList localeList2, boolean z) {
    this.mLocaleChangedHistory.addLocked(localeList, localeList2, z);
  }

  public final class BgHandler extends Handler {
    public BgHandler(Looper looper) {
      super(looper, null, false);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
    }
  }

  public class MetaDataCollector {
    public final String[] mBooleanMetaDataKeys;
    public final Map mBooleanMetaDataMap;
    public final Runnable mLoadRunnable;
    public boolean mLoaded;
    public final BroadcastReceiver mPackageReceiver;
    public final String[] mStringMetaDataKeys;
    public final Map mStringMetaDataMap;

    public MetaDataCollector() {
      this.mBooleanMetaDataMap = new HashMap();
      this.mBooleanMetaDataKeys =
          new String[] {
            "android.supports_size_changes",
            "com.samsung.android.keepalive.density",
            "com.samsung.android.persistent.downloadable",
            "com.samsung.android.foldable.fit_to_display",
            "com.samsung.android.multidisplay.keep_process_alive"
          };
      this.mStringMetaDataMap = new HashMap();
      this.mStringMetaDataKeys =
          new String[] {
            "com.samsung.android.multidisplay.primarydisplay",
            "com.samsung.android.dex.kill_process_timeout"
          };
      this.mLoaded = false;
      this.mLoadRunnable =
          new Runnable() { // from class:
                           // com.android.server.am.ActivityManagerServiceExt.MetaDataCollector.1
            @Override // java.lang.Runnable
            public void run() {
              boolean z;
              synchronized (MetaDataCollector.this) {
                z = MetaDataCollector.this.mLoaded;
              }
              if (ActivityManagerServiceExt.this.mService.mSystemReady && !z) {
                MetaDataCollector.this.loadInternal();
              }
              MetaDataCollector.this.scheduleLoad();
            }
          };
      this.mPackageReceiver =
          new BroadcastReceiver() { // from class:
                                    // com.android.server.am.ActivityManagerServiceExt.MetaDataCollector.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
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
              Slog.d("MetaDataCollector", "onReceive: done pkgName=" + str);
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
                          ActivityManagerServiceExt.this.mService.getProcessRecordLocked(
                              str, applicationInfo.uid);
                      if (processRecordLocked != null) {
                        ActivityManagerServiceExt.this.mService.parseApplicationInfoLocked(
                            processRecordLocked);
                      }
                    } finally {
                    }
                  }
                  ActivityManagerService.resetPriorityAfterLockedSection();
                }
                if (applicationInfo != null) {
                  ActivityManagerService activityManagerService2 =
                      ActivityManagerServiceExt.this.mService;
                  ActivityManagerService.boostPriorityForLockedSection();
                  synchronized (activityManagerService2) {
                    try {
                      ProcessRecord processRecordLocked2 =
                          ActivityManagerServiceExt.this.mService.getProcessRecordLocked(
                              str, applicationInfo.uid);
                      if (processRecordLocked2 != null) {
                        ActivityManagerServiceExt.this.mService.parseDexKillProcessTimeout(
                            processRecordLocked2);
                      }
                    } finally {
                    }
                  }
                  ActivityManagerService.resetPriorityAfterLockedSection();
                }
              } catch (RemoteException e) {
                Slog.v("MetaDataCollector", "getApplicationInfo: failed for " + str + " - " + e, e);
              }
            }
          };
    }

    public final void cacheMetaDataLocked(String str, String str2, boolean z) {
      Set set = (Set) this.mBooleanMetaDataMap.get(str2);
      if (set == null) {
        set = new HashSet();
        this.mBooleanMetaDataMap.put(str2, set);
      }
      synchronized (set) {
        if (z) {
          set.add(str);
        } else {
          set.remove(str);
        }
      }
    }

    public final void cacheStringMetaDataLocked(String str, String str2, String str3) {
      if (str == null || str2 == null) {
        return;
      }
      Pair pair = new Pair(str, str2);
      if (str3 == null) {
        this.mStringMetaDataMap.remove(pair);
      } else {
        this.mStringMetaDataMap.put(pair, str3);
      }
    }

    public final String getStringMetaData(String str, String str2) {
      Bundle bundle;
      if (str == null || str2 == null) {
        return null;
      }
      Pair pair = new Pair(str, str2);
      synchronized (this) {
        if (this.mLoaded) {
          return (String) this.mStringMetaDataMap.get(pair);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          ApplicationInfo applicationInfo =
              AppGlobals.getPackageManager()
                  .getApplicationInfo(
                      str, 128L, ActivityManagerServiceExt.this.mContext.getUserId());
          if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
            return bundle.getString(str2);
          }
          return null;
        } catch (RemoteException e) {
          Slog.v("MetaDataCollector", "getApplicationInfo failed: " + e, e);
          return null;
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public final boolean hasBooleanMetaData(String str, String str2) {
      boolean z;
      Set set;
      boolean contains;
      Bundle bundle;
      synchronized (this) {
        z = this.mLoaded;
        set = (Set) this.mBooleanMetaDataMap.get(str2);
      }
      if (!z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          ApplicationInfo applicationInfo =
              AppGlobals.getPackageManager()
                  .getApplicationInfo(
                      str, 128L, ActivityManagerServiceExt.this.mContext.getUserId());
          if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
            return bundle.getBoolean(str2);
          }
          return false;
        } catch (RemoteException e) {
          Slog.v("MetaDataCollector", "getApplicationInfo failed: " + e, e);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
      if (set == null) {
        return false;
      }
      synchronized (set) {
        contains = set.contains(str);
      }
      return contains;
    }

    public final void loadMetaDataOnceLocked(ApplicationInfo applicationInfo) {
      if (applicationInfo == null) {
        return;
      }
      for (String str : this.mBooleanMetaDataKeys) {
        Bundle bundle = applicationInfo.metaData;
        cacheMetaDataLocked(
            applicationInfo.packageName, str, bundle == null ? false : bundle.getBoolean(str));
      }
      for (String str2 : this.mStringMetaDataKeys) {
        Bundle bundle2 = applicationInfo.metaData;
        cacheStringMetaDataLocked(
            applicationInfo.packageName, str2, bundle2 == null ? null : bundle2.getString(str2));
      }
    }

    public final void loadInternal() {
      if (ActivityManagerServiceExt.this.mContext.getPackageManager() == null) {
        Slog.v("MetaDataCollector", "PackageManager is not ready yet.");
        return;
      }
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
      intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
      intentFilter.addDataScheme("package");
      ActivityManagerServiceExt activityManagerServiceExt = ActivityManagerServiceExt.this;
      activityManagerServiceExt.mContext.registerReceiverAsUser(
          this.mPackageReceiver,
          UserHandle.ALL,
          intentFilter,
          null,
          activityManagerServiceExt.mBgHandler);
      Slog.v("MetaDataCollector", "begin");
      List<PackageInfo> installedPackages =
          ActivityManagerServiceExt.this.mContext.getPackageManager().getInstalledPackages(128);
      synchronized (this) {
        Iterator<PackageInfo> it = installedPackages.iterator();
        while (it.hasNext()) {
          loadMetaDataOnceLocked(it.next().applicationInfo);
          this.mLoaded = true;
        }
        Slog.v("MetaDataCollector", "loadInternal() done.");
      }
    }

    public final void scheduleLoad() {
      boolean z;
      synchronized (this) {
        z = this.mLoaded;
      }
      if (ActivityManagerServiceExt.this.mService.mSystemReady && z) {
        return;
      }
      ActivityManagerServiceExt.this.mBgHandler.removeCallbacks(this.mLoadRunnable);
      ActivityManagerServiceExt.this.mBgHandler.postDelayed(this.mLoadRunnable, 1000L);
    }

    public final void dumpLocked(FileDescriptor fileDescriptor, PrintWriter printWriter) {
      printWriter.println("ACTIVITY MANAGER META DATA COLLECTOR");
      synchronized (this) {
        printWriter.println("  MetaData<Boolean>");
        int i = 0;
        if (this.mBooleanMetaDataMap.isEmpty()) {
          printWriter.println("  (nothing) ");
        } else {
          int i2 = 0;
          for (Map.Entry entry : this.mBooleanMetaDataMap.entrySet()) {
            printWriter.println("#" + i2 + " " + ((String) entry.getKey()));
            Iterator it = ((Set) entry.getValue()).iterator();
            while (it.hasNext()) {
              printWriter.println("    " + ((String) it.next()));
            }
            i2++;
          }
          printWriter.println();
        }
        printWriter.println("  MetaData<String>");
        if (this.mStringMetaDataMap.isEmpty()) {
          printWriter.println("  (nothing) ");
        } else {
          for (Map.Entry entry2 : this.mStringMetaDataMap.entrySet()) {
            printWriter.println(
                "#" + i + " " + entry2.getKey() + " value=" + ((String) entry2.getValue()));
            i++;
          }
          printWriter.println();
        }
      }
    }
  }

  public boolean hasMetaData(String str, String str2) {
    return this.mMetaDataCollector.hasBooleanMetaData(str, str2);
  }

  public String getStringMetaData(String str, String str2) {
    return this.mMetaDataCollector.getStringMetaData(str, str2);
  }

  public void dumpMetaDataLocked(FileDescriptor fileDescriptor, PrintWriter printWriter) {
    this.mMetaDataCollector.dumpLocked(fileDescriptor, printWriter);
  }

  static {
    MAX_LONG_LIVE_APP = CoreRune.FW_DEDICATED_MEMORY ? DynamicHiddenApp.MAX_NEVERKILLEDAPP_NUM : 1;
  }

  public boolean isCscVerChanged() {
    String str = "DUMMY";
    if ("DUMMY".equals(CSC_VERSION)) {
      Slog.wtf(
          "ActivityManagerServiceExt", "csc version of property is wrong", new RuntimeException());
    }
    try {
      FileInputStream fileInputStream = new FileInputStream(PRE_BOOT_CSC_FILE);
      try {
        DataInputStream dataInputStream =
            new DataInputStream(new BufferedInputStream(fileInputStream, 1024));
        try {
          str = dataInputStream.readUTF();
          dataInputStream.close();
          fileInputStream.close();
        } finally {
        }
      } catch (Throwable th) {
        try {
          fileInputStream.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
        throw th;
      }
    } catch (FileNotFoundException unused) {
    } catch (IOException e) {
      Slog.w("ActivityManagerServiceExt", "Failure reading pre boot csc", e);
    }
    return !CSC_VERSION.equals(str);
  }

  public void updatePreBootCscFile() {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(PRE_BOOT_CSC_FILE);
      try {
        DataOutputStream dataOutputStream =
            new DataOutputStream(new BufferedOutputStream(fileOutputStream, 1024));
        try {
          dataOutputStream.writeUTF(CSC_VERSION);
          dataOutputStream.flush();
          dataOutputStream.close();
          fileOutputStream.close();
        } finally {
        }
      } finally {
      }
    } catch (IOException e) {
      Slog.w("ActivityManagerServiceExt", "Failure writing last done pre-boot receivers", e);
      PRE_BOOT_CSC_FILE.delete();
    }
  }

  public class LongLiveCallback {
    public String packageName;
    public WeakReference ref;

    public LongLiveCallback(String str, ProcessRecord processRecord) {
      this.ref = new WeakReference(processRecord);
      this.packageName = str;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.packageName);
      sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
      sb.append(
          this.ref.get() == null
              ? PackageManagerShellCommandDataLoader.STDIN_PATH
              : Integer.valueOf(((ProcessRecord) this.ref.get()).getPid()));
      return sb.toString();
    }
  }

  public void registerDedicatedCallbackLocked(RemoteCallback remoteCallback, int i) {
    if (i == 1) {
      this.mAtmService.registerCallback4Task(remoteCallback);
    } else if (i == 0) {
      this.mCb4Process = Optional.ofNullable(remoteCallback);
    }
  }

  public final void updateNeverKill(RemoteCallback remoteCallback, boolean z, String str, int i) {
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

  public final boolean saveLongLivePackage() {
    if (this.mPref == null) {
      this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
    }
    SharedPreferences.Editor edit = this.mPref.edit();
    edit.putStringSet("LONG_LIVE_BY_PACKAGE", new ArraySet(this.mLongLiveAppByPackages));
    return edit.commit();
  }

  public final boolean removeLongLivePackage() {
    if (this.mPref == null) {
      this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
    }
    SharedPreferences.Editor edit = this.mPref.edit();
    edit.remove("LONG_LIVE_BY_PACKAGE");
    return edit.commit();
  }

  public final void migrateTo(SharedPreferences sharedPreferences) {
    if (sharedPreferences == null) {
      return;
    }
    try {
      sharedPreferences.getStringSet("LONG_LIVE_BY_PACKAGE", null);
    } catch (ClassCastException unused) {
      String string = this.mPref.getString("LONG_LIVE_BY_PACKAGE", null);
      Slog.d("ActivityManagerServiceExt", "migrateTo Set " + string);
      removeLongLivePackage();
      if (TextUtils.isEmpty(string)) {
        return;
      }
      this.mLongLiveAppByPackages.add(string);
      saveLongLivePackage();
      this.mLongLiveAppByPackages.clear();
    }
  }

  public void initLongLivePackageLocked() {
    if (this.mPref == null) {
      try {
        this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
      } catch (RuntimeException unused) {
      }
    }
    migrateTo(this.mPref);
    Set<String> stringSet = this.mPref.getStringSet("LONG_LIVE_BY_PACKAGE", null);
    if (stringSet != null) {
      this.mLongLiveAppByPackages.addAll(stringSet);
      Iterator it = this.mLongLiveAppByPackages.iterator();
      while (it.hasNext()) {
        markAsLongLivePackageIfAliveLocked((String) it.next());
      }
    }
  }

  public final void restoreAllAdjs() {
    if (this.mLongLiveCallbacks.size() <= 0) {
      return;
    }
    Iterator it = this.mLongLiveCallbacks.iterator();
    while (it.hasNext()) {
      final LongLiveCallback longLiveCallback = (LongLiveCallback) it.next();
      final ProcessRecord processRecord = (ProcessRecord) longLiveCallback.ref.get();
      if (processRecord != null) {
        processRecord.mDedicated = false;
        this.mCb4Process.ifPresent(
            new Consumer() { // from class:
                             // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActivityManagerServiceExt.this.lambda$restoreAllAdjs$0(
                    longLiveCallback, processRecord, (RemoteCallback) obj);
              }
            });
      }
    }
    this.mLongLiveCallbacks.clear();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$restoreAllAdjs$0(
      LongLiveCallback longLiveCallback,
      ProcessRecord processRecord,
      RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, false, longLiveCallback.packageName, processRecord.getPid());
  }

  public final void markAsLongLivePackageIfAliveLocked(final String str) {
    ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
    ActivityManagerService.boostPriorityForProcLockedSection();
    synchronized (activityManagerGlobalLock) {
      try {
        this.mService.mProcessList.forEachLruProcessesLOSP(
            false,
            new Consumer() { // from class:
                             // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActivityManagerServiceExt.this.lambda$markAsLongLivePackageIfAliveLocked$2(
                    str, (ProcessRecord) obj);
              }
            });
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterProcLockedSection();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$markAsLongLivePackageIfAliveLocked$2(
      final String str, final ProcessRecord processRecord) {
    if (processRecord.getPkgList().containsKey(str)) {
      processRecord.mDedicated = true;
      this.mLongLiveCallbacks.add(new LongLiveCallback(str, processRecord));
      this.mCb4Process.ifPresent(
          new Consumer() { // from class:
                           // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ActivityManagerServiceExt.this.lambda$markAsLongLivePackageIfAliveLocked$1(
                  str, processRecord, (RemoteCallback) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$markAsLongLivePackageIfAliveLocked$1(
      String str, ProcessRecord processRecord, RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, true, str, processRecord.getPid());
  }

  public boolean setLongLivePackageLocked(String str) {
    Slog.v(
        "ActivityManagerServiceExt",
        "setLongLivePackage " + str + "+" + this.mLongLiveAppByPackages);
    if (TextUtils.isEmpty(str)) {
      restoreAllAdjs();
      this.mLongLiveAppByPackages.clear();
      return removeLongLivePackage();
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

  public void promoteAsLongLivePackageIfNeededLocked(final ProcessRecord processRecord) {
    String[] packageList = processRecord.getPackageList();
    if (packageList == null || packageList.length == 0) {
      return;
    }
    int length = packageList.length;
    int i = 0;
    while (true) {
      if (i >= length) {
        break;
      }
      final String str = packageList[i];
      if (this.mLongLiveAppByPackages.contains(str)) {
        processRecord.mDedicated = true;
        this.mLongLiveCallbacks.add(new LongLiveCallback(str, processRecord));
        this.mCb4Process.ifPresent(
            new Consumer() { // from class:
                             // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda5
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActivityManagerServiceExt.this.lambda$promoteAsLongLivePackageIfNeededLocked$3(
                    str, processRecord, (RemoteCallback) obj);
              }
            });
        break;
      }
      i++;
    }
    for (int size = this.mLongLiveCallbacks.size() - 1; size >= 0; size--) {
      if (((LongLiveCallback) this.mLongLiveCallbacks.get(size)).ref.get() == null) {
        this.mLongLiveCallbacks.remove(size);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$promoteAsLongLivePackageIfNeededLocked$3(
      String str, ProcessRecord processRecord, RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, true, str, processRecord.getPid());
  }

  public boolean removeLongLivePackageLocked(String str) {
    Slog.i(
        "ActivityManagerServiceExt",
        "longLivePackage " + this.mLongLiveAppByPackages + " - " + str);
    if (!this.mLongLiveAppByPackages.contains(str)) {
      return false;
    }
    this.mLongLiveAppByPackages.remove(str);
    for (int size = this.mLongLiveCallbacks.size() - 1; size >= 0; size--) {
      final LongLiveCallback longLiveCallback =
          (LongLiveCallback) this.mLongLiveCallbacks.get(size);
      if (str.equals(longLiveCallback.packageName)) {
        final ProcessRecord processRecord = (ProcessRecord) longLiveCallback.ref.get();
        if (processRecord != null) {
          processRecord.mDedicated = false;
          this.mCb4Process.ifPresent(
              new Consumer() { // from class:
                               // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ActivityManagerServiceExt.this.lambda$removeLongLivePackageLocked$4(
                      longLiveCallback, processRecord, (RemoteCallback) obj);
                }
              });
        }
        this.mLongLiveCallbacks.remove(size);
      }
    }
    if (CoreRune.SAFE_DEBUG) {
      Slog.v("ActivityManagerServiceExt", "longLivePackage callback " + this.mLongLiveCallbacks);
    }
    return saveLongLivePackage();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeLongLivePackageLocked$4(
      LongLiveCallback longLiveCallback,
      ProcessRecord processRecord,
      RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, true, longLiveCallback.packageName, processRecord.getPid());
  }

  public boolean addLongLivePackageLocked(String str) {
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

  public boolean removeLongLivePackageWhenUninstalledLocked(String str) {
    if (str == null || !this.mLongLiveAppByPackages.contains(str)) {
      return false;
    }
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
        LongLiveCallback longLiveCallback = (LongLiveCallback) this.mLongLiveCallbacks.get(size);
        if (((ProcessRecord) longLiveCallback.ref.get()) == null
            || str.equals(longLiveCallback.packageName)) {
          this.mLongLiveCallbacks.remove(size);
        }
      }
    }
    return saveLongLivePackage();
  }

  public String getLongLivePackageLocked(int i) {
    if (this.mLongLiveAppByPackages.size() > i) {
      return (String) this.mLongLiveAppByPackages.get(i);
    }
    return null;
  }

  public ArrayList getLongLivePackagesLocked() {
    return this.mLongLiveAppByPackages;
  }

  public void removeLongLiveTaskLocked(String str, int i) {
    this.mAtmService.removeDedicatedProcessFromPackage(str, i);
  }

  public final void markAsDedicatedProcessNameIfAliveLocked(
      final String str, final int i, final boolean z) {
    ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
    ActivityManagerService.boostPriorityForProcLockedSection();
    synchronized (activityManagerGlobalLock) {
      try {
        this.mService.mProcessList.forEachLruProcessesLOSP(
            false,
            new Consumer() { // from class:
                             // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda2
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActivityManagerServiceExt.this.lambda$markAsDedicatedProcessNameIfAliveLocked$6(
                    i, str, z, (ProcessRecord) obj);
              }
            });
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterProcLockedSection();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$markAsDedicatedProcessNameIfAliveLocked$6(
      int i, final String str, final boolean z, final ProcessRecord processRecord) {
    if (i == processRecord.userId && processRecord.processName.equals(str)) {
      processRecord.mDedicated = z;
      this.mCb4Process.ifPresent(
          new Consumer() { // from class:
                           // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ActivityManagerServiceExt.this.lambda$markAsDedicatedProcessNameIfAliveLocked$5(
                  z, str, processRecord, (RemoteCallback) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$markAsDedicatedProcessNameIfAliveLocked$5(
      boolean z, String str, ProcessRecord processRecord, RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, z, str, processRecord.getPid());
  }

  public void setLongLiveTask(int i, boolean z) {
    Slog.v("ActivityManagerServiceExt", "setLongLiveTask " + i + "," + z);
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        Pair longLiveTask = this.mAtmService.setLongLiveTask(i, z);
        Object obj = longLiveTask.first;
        if (obj != null) {
          markAsDedicatedProcessNameIfAliveLocked(
              (String) obj, ((Integer) longLiveTask.second).intValue(), z);
        }
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  public boolean setLongLiveProcess(int i) {
    final ProcessRecord processRecord;
    Slog.v("ActivityManagerServiceExt", "setLongLiveProcess " + i);
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      if (i != -1) {
        try {
          synchronized (this.mService.mPidsSelfLocked) {
            processRecord = this.mService.mPidsSelfLocked.get(i);
          }
          if (processRecord != null && !processRecord.mDedicated) {
            processRecord.mDedicated = true;
            this.mCb4Process.ifPresent(
                new Consumer() { // from class:
                                 // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda6
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    ActivityManagerServiceExt.this.lambda$setLongLiveProcess$7(
                        processRecord, (RemoteCallback) obj);
                  }
                });
          }
        } catch (Throwable th) {
          ActivityManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setLongLiveProcess$7(
      ProcessRecord processRecord, RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, true, processRecord.processName, processRecord.getPid());
  }

  public void promoteAsDedicatedLocked(final ProcessRecord processRecord) {
    Slog.v("ActivityManagerServiceExt", "promoteAsDedicated " + processRecord.processName);
    processRecord.mDedicated = true;
    this.mCb4Process.ifPresent(
        new Consumer() { // from class:
                         // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ActivityManagerServiceExt.this.lambda$promoteAsDedicatedLocked$8(
                processRecord, (RemoteCallback) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$promoteAsDedicatedLocked$8(
      ProcessRecord processRecord, RemoteCallback remoteCallback) {
    updateNeverKill(remoteCallback, true, processRecord.processName, processRecord.getPid());
  }

  public ArrayList getDedicatedProcessesLocked(int i) {
    return this.mAtmService.getDedicatedProcesses(i);
  }

  public ArrayList getDedicatedTaskIdsLocked(int i) {
    return this.mAtmService.getDedicatedTaskIds(i);
  }

  public void dumpLongLivePackageLocked(
      FileDescriptor fileDescriptor, PrintWriter printWriter, boolean z) {
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

  public int getMaxLongLiveApps() {
    return MAX_LONG_LIVE_APP;
  }

  public boolean getAutoRemoveRecents(int i) {
    return this.mAtmService.getAutoRemoveRecents(i);
  }

  public class BrCountInfo {
    public int mCnt;
    public int mMaxCnt;
    public long mTotalCnt;

    public BrCountInfo() {
      this.mCnt = 0;
      this.mMaxCnt = 0;
      this.mTotalCnt = 0L;
    }
  }

  public boolean isNeedRestrictToSendBr(BroadcastRecord broadcastRecord) {
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
      arrayMap.put(action, brCountInfo);
    }
    int i2 = brCountInfo.mCnt;
    if (i2 >= i && !contains) {
      addDiscardBroadcastToHistory(str, intent.toString());
      Slog.d(
          "ActivityManagerServiceExt",
          "Too many Broadcast Requested :: This BR will not be sent from="
              + str
              + " intent="
              + intent);
      return true;
    }
    broadcastRecord.mCounted = true;
    brCountInfo.mCnt = i2 + 1;
    brCountInfo.mTotalCnt++;
    arrayMap.put(action, brCountInfo);
    return false;
  }

  public void addDiscardBroadcastToHistory(String str, String str2) {
    if (this.mDiscardList.size() > 10) {
      this.mDiscardList.remove(0);
    }
    this.mDiscardList.add(
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()))
            + " from="
            + str
            + " key="
            + str2);
  }

  public void updateBrMap(String str, Intent intent) {
    if (str == null) {
      str = "android";
    }
    ArrayMap arrayMap = (ArrayMap) this.mBrMap.get(str);
    if (arrayMap == null) {
      return;
    }
    String action = intent.getAction();
    if (TextUtils.isEmpty(action)) {
      action = "EMPTY_ACTION";
    }
    BrCountInfo brCountInfo = (BrCountInfo) arrayMap.get(action);
    if (brCountInfo == null) {
      return;
    }
    int i = brCountInfo.mMaxCnt;
    int i2 = brCountInfo.mCnt;
    if (i < i2) {
      brCountInfo.mMaxCnt = i2;
    }
    int i3 = i2 - 1;
    brCountInfo.mCnt = i3;
    if (i3 < 0) {
      brCountInfo.mCnt = 0;
    }
    arrayMap.put(action, brCountInfo);
  }

  public void dumpBroadcastRecordCount(final PrintWriter printWriter) {
    if (!this.mBrMap.isEmpty()) {
      printWriter.println();
      printWriter.println("  **BroadcastRecord Count dump**");
      this.mBrMap.forEach(
          new BiConsumer() { // from class:
                             // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              ActivityManagerServiceExt.lambda$dumpBroadcastRecordCount$10(
                  printWriter, (String) obj, (ArrayMap) obj2);
            }
          });
    }
    if (this.mDiscardList.isEmpty()) {
      return;
    }
    printWriter.println();
    printWriter.println("  **Discarded Broadcast dump**");
    Iterator it = this.mDiscardList.iterator();
    while (it.hasNext()) {
      printWriter.println("    " + ((String) it.next()));
    }
  }

  public static /* synthetic */ void lambda$dumpBroadcastRecordCount$10(
      final PrintWriter printWriter, String str, ArrayMap arrayMap) {
    printWriter.println("    Package [" + str + "]");
    arrayMap.forEach(
        new BiConsumer() { // from class:
                           // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda12
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            ActivityManagerServiceExt.lambda$dumpBroadcastRecordCount$9(
                printWriter, (String) obj, (ActivityManagerServiceExt.BrCountInfo) obj2);
          }
        });
  }

  public static /* synthetic */ void lambda$dumpBroadcastRecordCount$9(
      PrintWriter printWriter, String str, BrCountInfo brCountInfo) {
    printWriter.println(
        "        action="
            + str
            + " cnt="
            + brCountInfo.mCnt
            + " max="
            + brCountInfo.mMaxCnt
            + " total="
            + brCountInfo.mTotalCnt);
  }

  public IntentFilter intentFilterForReceiverAllowlist(
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
        if (this.mBroadcastReceiverNotInAllowlist.containsKey(next)) {
          List list = (List) this.mBroadcastReceiverNotInAllowlist.get(next);
          if (list != null && !list.contains(str)) {
            list.add(str);
          }
        } else {
          ArrayList arrayList2 = new ArrayList();
          arrayList2.add(str);
          this.mBroadcastReceiverNotInAllowlist.put(next, arrayList2);
        }
        Slog.d(
            "ActivityManagerServiceExt",
            "action:" + next + " callerPackage:" + str + " is not registered!");
      }
    }
    arrayList.forEach(
        new Consumer() { // from class:
                         // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda4
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            intentFilter.removeAction((String) obj);
          }
        });
    return intentFilter;
  }

  public void dumpBrAllowList(final PrintWriter printWriter) {
    if (!this.mBroadcastReceiverNotInAllowlist.isEmpty()) {
      printWriter.println();
      printWriter.println("BroadcastReceiverNotInAllowlist");
      this.mBroadcastReceiverNotInAllowlist.forEach(
          new BiConsumer() { // from class:
                             // com.android.server.am.ActivityManagerServiceExt$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              ActivityManagerServiceExt.lambda$dumpBrAllowList$14(
                  printWriter, (String) obj, (List) obj2);
            }
          });
    }
    BroadcastReceiverListParser broadcastReceiverListParser = this.mParser;
    if (broadcastReceiverListParser instanceof BroadcastReceiverListParserWithScpm) {
      ((BroadcastReceiverListParserWithScpm) broadcastReceiverListParser).dump(printWriter);
    }
  }

  public static /* synthetic */ void lambda$dumpBrAllowList$14(
      PrintWriter printWriter, String str, List list) {
    printWriter.println("Action [" + str + "]");
    printWriter.println("  Packages :");
    Iterator it = list.iterator();
    while (it.hasNext()) {
      printWriter.println("   - " + ((String) it.next()));
    }
  }

  public void notifyNewProcessRecord(ProcessRecord processRecord) {
    if (!Build.IS_USER
        && processRecord != null
        && "com.android.systemui".equals(processRecord.processName)
        && processRecord.userId == 0
        && (processRecord.info.flags & 9) == 9) {
      processRecord.setPersistent(true);
      processRecord.mState.setMaxAdj(-800);
    }
  }

  public boolean forceStopPackageLocked(
      String str,
      int i,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      boolean z5,
      int i2,
      String str2) {
    return forceStopPackageLocked(str, i, z, z2, z3, z4, z5, i2, str2, str == null ? 11 : 10);
  }

  public boolean forceStopPackageLocked(
      String str,
      int i,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      boolean z5,
      int i2,
      String str2,
      int i3) {
    boolean z6;
    boolean z7;
    if (this.mMetaDataCollector.hasBooleanMetaData(
            str, "com.samsung.android.persistent.downloadable")
        && this.mForceStopReasons.contains(str2)) {
      Slog.v("ActivityManagerServiceExt", "forceStopPackageLocked with revised flags for " + str);
      z6 = true;
      z7 = true;
      return this.mService.forceStopPackageLocked(str, i, z6, z2, z3, z7, z5, i2, str2, i3);
    }
    z6 = z;
    z7 = z4;
    return this.mService.forceStopPackageLocked(str, i, z6, z2, z3, z7, z5, i2, str2, i3);
  }

  public final boolean isKeepAlive(ProcessRecord processRecord) {
    String[] packageList = processRecord.getPackageList();
    if (packageList == null) {
      return false;
    }
    for (String str : packageList) {
      if (this.mMetaDataCollector.hasBooleanMetaData(
          str, "com.samsung.android.keepalive.density")) {
        return true;
      }
    }
    return false;
  }

  /* JADX WARN: Removed duplicated region for block: B:52:0x0108 A[Catch: all -> 0x01c5, TryCatch #2 {all -> 0x01c5, blocks: (B:26:0x0084, B:32:0x015a, B:33:0x00a0, B:36:0x00ac, B:37:0x00b2, B:39:0x00b8, B:45:0x00d9, B:47:0x00df, B:50:0x00fc, B:52:0x0108, B:56:0x0112, B:58:0x0118, B:63:0x0124, B:66:0x012c, B:70:0x0138, B:72:0x0146, B:73:0x014f, B:75:0x0156, B:79:0x00ed, B:88:0x0166, B:103:0x01c0, B:91:0x0176, B:93:0x0180, B:95:0x01b1), top: B:17:0x004f }] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0118 A[Catch: all -> 0x01c5, TryCatch #2 {all -> 0x01c5, blocks: (B:26:0x0084, B:32:0x015a, B:33:0x00a0, B:36:0x00ac, B:37:0x00b2, B:39:0x00b8, B:45:0x00d9, B:47:0x00df, B:50:0x00fc, B:52:0x0108, B:56:0x0112, B:58:0x0118, B:63:0x0124, B:66:0x012c, B:70:0x0138, B:72:0x0146, B:73:0x014f, B:75:0x0156, B:79:0x00ed, B:88:0x0166, B:103:0x01c0, B:91:0x0176, B:93:0x0180, B:95:0x01b1), top: B:17:0x004f }] */
  /* JADX WARN: Removed duplicated region for block: B:59:0x011c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void killAllBackgroundProcessesExcept(int i, int i2, Bundle bundle) {
    String string;
    Set set;
    Set set2;
    boolean z;
    boolean z2;
    Throwable th;
    Set set3;
    boolean z3;
    boolean z4;
    Set set4;
    boolean z5;
    if (this.mService.checkCallingPermission("android.permission.KILL_BACKGROUND_PROCESSES") != 0) {
      String str =
          "Permission Denial: killAllBackgroundProcessesExcept() from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " requires android.permission.KILL_BACKGROUND_PROCESSES";
      Slog.w("ActivityManagerServiceExt", str);
      throw new SecurityException(str);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    int i3 = 0;
    if (bundle != null) {
      try {
        string = bundle.getString("name");
        set = (Set) bundle.getSerializable("kill_packages");
        set2 = (Set) bundle.getSerializable("keep_packages");
        z = bundle.getBoolean("skip_sdk_version_check", false);
        z2 = true;
      } catch (Throwable th2) {
        th = th2;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } else {
      string = "multi-resolution";
      set = null;
      z = false;
      z2 = false;
      set2 = null;
    }
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    try {
      try {
        synchronized (activityManagerService) {
          try {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            try {
              synchronized (activityManagerGlobalLock) {
                try {
                  ArrayList arrayList = new ArrayList();
                  int size = this.mService.mProcessList.getProcessNamesLOSP().getMap().size();
                  while (i3 < size) {
                    SparseArray sparseArray =
                        (SparseArray)
                            this.mService.mProcessList.getProcessNamesLOSP().getMap().valueAt(i3);
                    int i4 = size;
                    int size2 = sparseArray.size();
                    long j = clearCallingIdentity;
                    int i5 = 0;
                    while (i5 < size2) {
                      ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i5);
                      SparseArray sparseArray2 = sparseArray;
                      int i6 = size2;
                      if (processRecord.mState.getSetProcState() == 20) {
                        set3 = set;
                        set4 = set2;
                      } else {
                        boolean z6 = processRecord.mState.getSetProcState() == 2;
                        Iterator it = this.mForceKillPackages.iterator();
                        while (true) {
                          if (!it.hasNext()) {
                            break;
                          }
                          boolean z7 = z6;
                          Iterator it2 = it;
                          if (processRecord.info.packageName.startsWith((String) it.next())) {
                            z6 = true;
                            break;
                          } else {
                            it = it2;
                            z6 = z7;
                          }
                        }
                        if (z2) {
                          String str2 = processRecord.info.packageName;
                          if (set == null || !set.contains(str2)) {
                            if (set2 != null && set2.contains(str2)) {
                              set3 = set;
                              z3 = true;
                              z4 = false;
                              set4 = set2;
                              if (!this.mForceKeepAliveProcesses.contains(processRecord.processName)
                                  && !isKeepAlive(processRecord)) {
                                z5 = false;
                                if (processRecord.isRemoved()) {
                                  arrayList.add(processRecord);
                                } else {
                                  if (!z5
                                      && !z3
                                      && ((z || i < 0 || processRecord.info.targetSdkVersion < i)
                                          && (i2 < 0
                                              || processRecord.mState.getSetProcState() > i2
                                              || z6
                                              || z4
                                              || "force-keep-alive"
                                                  .equals(processRecord.mState.getAdjType())))) {
                                    processRecord.setRemoved(true);
                                    arrayList.add(processRecord);
                                  } else if (processRecord.skipToFinishActivities()) {
                                    processRecord.setSkipToFinishActivities(false);
                                    i5++;
                                    sparseArray = sparseArray2;
                                    size2 = i6;
                                    set = set3;
                                    set2 = set4;
                                  }
                                  i5++;
                                  sparseArray = sparseArray2;
                                  size2 = i6;
                                  set = set3;
                                  set2 = set4;
                                }
                              }
                              z5 = true;
                              if (processRecord.isRemoved()) {}
                            }
                          } else {
                            set3 = set;
                            z3 = false;
                            z4 = true;
                            set4 = set2;
                            if (!this.mForceKeepAliveProcesses.contains(
                                processRecord.processName)) {
                              z5 = false;
                              if (processRecord.isRemoved()) {}
                            }
                            z5 = true;
                            if (processRecord.isRemoved()) {}
                          }
                        }
                        set3 = set;
                        z3 = false;
                        z4 = false;
                        set4 = set2;
                        if (!this.mForceKeepAliveProcesses.contains(processRecord.processName)) {}
                        z5 = true;
                        if (processRecord.isRemoved()) {}
                      }
                      i5++;
                      sparseArray = sparseArray2;
                      size2 = i6;
                      set = set3;
                      set2 = set4;
                    }
                    i3++;
                    size = i4;
                    clearCallingIdentity = j;
                    set = set;
                  }
                  long j2 = clearCallingIdentity;
                  int size3 = arrayList.size();
                  for (int i7 = 0; i7 < size3; i7++) {
                    this.mService.mProcessList.removeProcessLocked(
                        (ProcessRecord) arrayList.get(i7),
                        false,
                        true,
                        13,
                        "kill all background except(policy=" + string + ")");
                  }
                  ActivityManagerService.resetPriorityAfterProcLockedSection();
                  ActivityManagerService.resetPriorityAfterLockedSection();
                  Binder.restoreCallingIdentity(j2);
                } catch (Throwable th3) {
                  th = th3;
                  ActivityManagerService.resetPriorityAfterProcLockedSection();
                  throw th;
                }
              }
            } catch (Throwable th4) {
              th = th4;
            }
          } catch (Throwable th5) {
            th = th5;
            ActivityManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
      } catch (Throwable th6) {
        th = th6;
      }
    } catch (Throwable th7) {
      th = th7;
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public void killProcessWhenDexExit() {
    int i;
    SparseArray sparseArray;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ActivityManagerService activityManagerService = this.mService;
      ActivityManagerService.boostPriorityForLockedSection();
      synchronized (activityManagerService) {
        try {
          ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
          ActivityManagerService.boostPriorityForProcLockedSection();
          synchronized (activityManagerGlobalLock) {
            try {
              ArrayList arrayList = new ArrayList();
              ArrayList arrayList2 = new ArrayList();
              int size = this.mService.mProcessList.getProcessNamesLOSP().getMap().size();
              int i2 = 0;
              for (int i3 = 0; i3 < size; i3++) {
                SparseArray sparseArray2 =
                    (SparseArray)
                        this.mService.mProcessList.getProcessNamesLOSP().getMap().valueAt(i3);
                int size2 = sparseArray2.size();
                int i4 = 0;
                while (i4 < size2) {
                  ProcessRecord processRecord = (ProcessRecord) sparseArray2.valueAt(i4);
                  if (processRecord.mState.getSetProcState() != 20) {
                    int killProcessTimeout = processRecord.getKillProcessTimeout();
                    if (killProcessTimeout == 0) {
                      arrayList.add(processRecord);
                    } else if (killProcessTimeout > 0) {
                      i = size;
                      StringBuilder sb = new StringBuilder();
                      sparseArray = sparseArray2;
                      sb.append("kill dex related process - scheduleKillProcessesForDeXExit :");
                      sb.append(processRecord.processName);
                      sb.append("(");
                      sb.append(String.valueOf(processRecord.getKillProcessTimeout()));
                      sb.append(")");
                      Slog.d("ActivityManagerServiceExt", sb.toString());
                      arrayList2.add(processRecord);
                      if (killProcessTimeout > i2) {
                        i2 = killProcessTimeout;
                      }
                      i4++;
                      size = i;
                      sparseArray2 = sparseArray;
                    }
                  }
                  i = size;
                  sparseArray = sparseArray2;
                  i4++;
                  size = i;
                  sparseArray2 = sparseArray;
                }
              }
              int size3 = arrayList.size();
              for (int i5 = 0; i5 < size3; i5++) {
                forceKillProcessesForDeXExitLocked((ProcessRecord) arrayList.get(i5));
              }
              if (arrayList2.size() > 0) {
                scheduleKillProcessesForDeXExit(arrayList2, i2);
              }
            } catch (Throwable th) {
              ActivityManagerService.resetPriorityAfterProcLockedSection();
              throw th;
            }
          }
          ActivityManagerService.resetPriorityAfterProcLockedSection();
        } catch (Throwable th2) {
          ActivityManagerService.resetPriorityAfterLockedSection();
          throw th2;
        }
      }
      ActivityManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void cancelKillProcessWhenDexExit() {
    ActivityManagerService activityManagerService = this.mService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        this.mDeXHandler.removeCallbacks(this.mForceKillForDeXRunnable);
        this.mDeXKillProcesses = null;
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
  }

  public final void forceKillProcessesForDeXExitLocked(ProcessRecord processRecord) {
    if (processRecord.mState.getSetProcState() > 13) {
      processRecord.setRemoved(true);
      this.mService.mProcessList.removeProcessLocked(
          processRecord, false, true, 13, "kill dex related process when exit");
      Slog.d(
          "ActivityManagerServiceExt",
          "kill dex related process succeeded - " + processRecord.processName);
      return;
    }
    Slog.d(
        "ActivityManagerServiceExt",
        "kill dex related process failed for procstate - "
            + processRecord.processName
            + XmlUtils.STRING_ARRAY_SEPARATOR
            + processRecord.mState.getSetProcState());
  }

  public final void scheduleKillProcessesForDeXExit(ArrayList arrayList, int i) {
    this.mDeXHandler.removeCallbacks(this.mForceKillForDeXRunnable);
    this.mDeXKillProcesses = arrayList;
    this.mDeXHandler.postDelayed(this.mForceKillForDeXRunnable, i);
  }

  public void addBroadcastSummaryHistoryLocked(
      BroadcastHistory broadcastHistory, BroadcastRecord broadcastRecord) {
    broadcastHistory.mBroadcastSummaryHistoryToString[broadcastHistory.mSummaryHistoryNext] =
        broadcastRecord.intent.toShortString(true, true, true, false);
  }

  public void addAbortedBroadcastToHistoryLocked(
      BroadcastHistory broadcastHistory, BroadcastRecord broadcastRecord) {
    broadcastHistory.mAbortedBroadcastHistory[broadcastHistory.mAbortedHistoryNext] =
        broadcastRecordToStringLocked(broadcastRecord);
    broadcastHistory.mAbortedHistoryNext =
        broadcastHistory.ringAdvance(
            broadcastHistory.mAbortedHistoryNext,
            1,
            BroadcastHistory.MAX_ABORTED_BROADCAST_HISTORY);
  }

  public String broadcastRecordToStringLocked(BroadcastRecord broadcastRecord) {
    CharArrayWriter charArrayWriter = new CharArrayWriter();
    broadcastRecord.dump(
        new PrintWriter(charArrayWriter), "    ", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    return charArrayWriter.toString();
  }

  public void printReceiverTime(
      BroadcastRecord broadcastRecord, PrintWriter printWriter, int i, long j) {
    printWriter.print(", [disp=");
    TimeUtils.formatDuration(broadcastRecord.receiversDispatchTime[i], j, printWriter);
    printWriter.print(", fin=");
    TimeUtils.formatDuration(broadcastRecord.receiversFinishTime[i], j, printWriter);
    printWriter.print(", dur=");
    long j2 = broadcastRecord.receiversFinishTime[i];
    if (j2 != 0) {
      TimeUtils.formatDuration(j2 - broadcastRecord.receiversDispatchTime[i], printWriter);
    } else {
      printWriter.print("--");
    }
    printWriter.println("]");
  }

  public void dumpForBroadcastQueueLocked(
      BroadcastHistory broadcastHistory, String str, PrintWriter printWriter, boolean z) {
    int i = broadcastHistory.mAbortedHistoryNext;
    boolean z2 = false;
    int i2 = i;
    int i3 = -1;
    do {
      i2 = broadcastHistory.ringAdvance(i2, -1, BroadcastHistory.MAX_ABORTED_BROADCAST_HISTORY);
      String str2 = broadcastHistory.mAbortedBroadcastHistory[i2];
      if (str2 != null) {
        i3++;
        if (!z2) {
          if (z) {
            printWriter.println();
          }
          printWriter.println("  Aborted Historical broadcasts [" + str + "]:");
          z = true;
          z2 = true;
        }
        printWriter.print("  Aborted Historical Broadcast " + str + " #");
        printWriter.print(i3);
        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
        printWriter.println(str2);
      }
    } while (i2 != i);
  }

  public final void initRelaxedBroadcastActions() {
    String[] stringArray = this.mContext.getResources().getStringArray(17236290);
    if (stringArray.length > 0) {
      this.mRelaxedBroadcastActions = new HashSet(Arrays.asList(stringArray));
    }
  }

  public boolean isRelaxedBroadcastAction(String str) {
    HashSet hashSet = this.mRelaxedBroadcastActions;
    return hashSet != null && hashSet.contains(str);
  }

  public void handleAppDiedLocked(ProcessRecord processRecord) {
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mDexPrimaryProcessList.iterator();
    while (it.hasNext()) {
      Pair pair = (Pair) it.next();
      if (((String) pair.first).equals(processRecord.processName)
          && ((Integer) pair.second).intValue() == processRecord.uid) {
        arrayList.add(pair);
      }
    }
    this.mDexPrimaryProcessList.removeAll(arrayList);
    if (processRecord.skipToFinishActivities()) {
      processRecord.setSkipToFinishActivities(false);
    }
  }

  public void dumpDexPrimaryProcess(FileDescriptor fileDescriptor, PrintWriter printWriter) {
    if (this.mDexPrimaryProcessList.isEmpty()) {
      return;
    }
    printWriter.print("    mDexPrimaryProcessList: size=");
    printWriter.println(this.mDexPrimaryProcessList.size());
    Iterator it = this.mDexPrimaryProcessList.iterator();
    while (it.hasNext()) {
      Pair pair = (Pair) it.next();
      printWriter.print("     {packageName=");
      printWriter.print((String) pair.first);
      printWriter.print(", app uid=");
      printWriter.print(pair.second);
      printWriter.println("}");
    }
  }

  public void setPersistent(ProcessRecord processRecord) {
    ProcessStateRecord processStateRecord = processRecord.mState;
    processStateRecord.setCurrentSchedulingGroup(2);
    processStateRecord.setSetSchedGroup(2);
    processRecord.setPersistent(true);
    processStateRecord.setMaxAdj(-800);
  }

  public void setPersistentIfNeeded(ProcessRecord processRecord) {
    if (!processRecord.isPersistent()
        && processRecord.userId == 0
        && (processRecord.info.flags & 9) == 9
        && this.persistentApps.contains(processRecord.processName)) {
      Slog.v("ActivityManagerServiceExt", "Set as " + processRecord.processName);
      setPersistent(processRecord);
    }
  }

  public boolean shouldAvoidForceStopForTmoPkg(String str) {
    return this.mCanTmoPkgAvoidForceStop && "com.tmobile.echolocate".equals(str);
  }

  public void initTmoForceStopPolicy() {
    this.mCanTmoPkgAvoidForceStop =
        this.mService.getPackageManagerInternal().isSystemPackage("com.tmobile.echolocate");
  }
}
