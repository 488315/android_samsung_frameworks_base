package com.android.server.appwidget;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.BroadcastOptions;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetManagerInternal;
import android.appwidget.AppWidgetProviderInfo;
import android.appwidget.PendingHostUpdate;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.TransactionTooLargeException;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TypedValue;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.widget.RemoteViews;
import com.android.internal.app.SuspendedAppActivity;
import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.widget.IRemoteViewsFactory;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.WidgetBackupProvider;
import com.android.server.am.MARsPolicyManager;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.emergencymode.Elog;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AppWidgetServiceImpl extends IAppWidgetService.Stub
    implements WidgetBackupProvider,
        DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener {
  public static final AtomicLong UPDATE_COUNTER = new AtomicLong();
  public ActivityManager mActivityManager;
  public ActivityManagerInternal mActivityManagerInternal;
  public AlarmManager mAlarmManager;
  public AppOpsManager mAppOpsManager;
  public AppOpsManagerInternal mAppOpsManagerInternal;
  public BackupRestoreController mBackupRestoreController;
  public Handler mCallbackHandler;
  public final Context mContext;
  public DevicePolicyManagerInternal mDevicePolicyManagerInternal;
  public Bundle mInteractiveBroadcast;
  public boolean mIsCombinedBroadcastEnabled;
  public boolean mIsProviderInfoPersisted;
  public KeyguardManager mKeyguardManager;
  public Locale mLocale;
  public int mMaxWidgetBitmapMemory;
  public IPackageManager mPackageManager;
  public PackageManagerInternal mPackageManagerInternal;
  public boolean mSafeMode;
  public Handler mSaveStateHandler;
  public int mScreenDensity;
  public SecurityPolicy mSecurityPolicy;
  public SemPersonaManager mSpm;
  public int mThemeSeq;
  public UsageStatsManagerInternal mUsageStatsManagerInternal;
  public UserManager mUserManager;
  public final BroadcastReceiver mBroadcastReceiver =
      new BroadcastReceiver() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
          if (action == null) {
            Log.e("AppWidgetServiceImpl", "action is null");
            return;
          }
          switch (action) {
            case "android.intent.action.MANAGED_PROFILE_UNAVAILABLE":
            case "android.intent.action.MANAGED_PROFILE_AVAILABLE":
              synchronized (AppWidgetServiceImpl.this.mLock) {
                AppWidgetServiceImpl.this.reloadWidgetsMaskedState(intExtra);
              }
              return;
            case "android.intent.action.PACKAGES_SUSPENDED":
              AppWidgetServiceImpl.this.onPackageBroadcastReceived(intent, getSendingUserId());
              AppWidgetServiceImpl.this.updateWidgetPackageSuspensionMaskedState(
                  intent, true, getSendingUserId());
              return;
            case "com.samsung.intent.action.EMERGENCY_STATE_CHANGED":
              if (CoreRune.EM_SUPPORTED) {
                int intExtra2 = intent.getIntExtra("reason", 0);
                if (intExtra2 >= 0) {
                  AppWidgetServiceImpl.this.mEmergencyState = intExtra2;
                }
                if (intExtra2 == 5) {
                  AppWidgetServiceImpl.this.onEmergencyModeDisabled(0);
                  return;
                }
                return;
              }
              return;
            case "android.intent.action.CONFIGURATION_CHANGED":
              AppWidgetServiceImpl.this.onConfigurationChanged();
              return;
            case "android.intent.action.PACKAGES_UNSUSPENDED":
              AppWidgetServiceImpl.this.onPackageBroadcastReceived(intent, getSendingUserId());
              AppWidgetServiceImpl.this.updateWidgetPackageSuspensionMaskedState(
                  intent, false, getSendingUserId());
              return;
            default:
              AppWidgetServiceImpl.this.onPackageBroadcastReceived(intent, getSendingUserId());
              return;
          }
        }
      };
  public final HashMap mRemoteViewsServicesAppWidgets = new HashMap();
  public final Object mLock = new Object();
  public final ArrayList mWidgets = new ArrayList();
  public final ArrayList mHosts = new ArrayList();
  public final List mProviders = Collections.synchronizedList(new ArrayList());
  public String mSkipPackageName = null;
  public final ArraySet mPackagesWithBindWidgetPermission = new ArraySet();
  public final SparseBooleanArray mLoadedUserIds = new SparseBooleanArray();
  public final Object mWidgetPackagesLock = new Object();
  public final SparseArray mWidgetPackages = new SparseArray();
  public int mEmergencyState = 0;
  public final SparseIntArray mNextAppWidgetIds = new SparseIntArray();
  public final File mFdFile = new File("/proc/self/fd");
  public final HashMap mPackageToPidMap = new HashMap();
  public final HashMap mPidToPackageMap = new HashMap();
  public final HashMap mLastSeqNumber = new HashMap();
  public final ArrayList mUpdateScreenSizeProviders = new ArrayList();
  public String mThemePkg = null;
  public boolean isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
  public String[] mHostHistory = new String[7];
  public String[] mProvidersHistory = new String[100];
  public String[] mAlarmHistory = new String[7];
  public int mHostHistoryIdx = 0;
  public int mProvidersHistoryIndex = 0;
  public int mAlarmHistoryIndex = 0;

  public AppWidgetServiceImpl(Context context) {
    this.mContext = context;
  }

  public Map getAllWidgets(String str, int i) {
    HashMap hashMap = new HashMap();
    try {
    } catch (Exception e) {
      Slog.e("AppWidgetServiceImpl", "error in getAllWidgets", e);
    }
    if (Binder.getCallingUid() != Process.myUid()) {
      throw new SecurityException("Can only be called by system user");
    }
    Iterator it = this.mHosts.iterator();
    while (it.hasNext()) {
      Host host = (Host) it.next();
      if (UserHandle.getUserId(host.f1640id.uid) == i) {
        boolean z = str == null;
        if (z || (!z && str.equals(host.f1640id.packageName))) {
          Iterator it2 = host.widgets.iterator();
          while (it2.hasNext()) {
            Widget widget = (Widget) it2.next();
            if (hashMap.isEmpty()) {
              ArrayList arrayList = new ArrayList();
              arrayList.add(Integer.valueOf(widget.appWidgetId));
              hashMap.put(widget.provider.info, arrayList);
            } else {
              ArrayList arrayList2 = (ArrayList) hashMap.get(widget.provider.info);
              if (arrayList2 != null) {
                arrayList2.add(Integer.valueOf(widget.appWidgetId));
                hashMap.put(widget.provider.info, arrayList2);
              } else if (arrayList2 == null) {
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(Integer.valueOf(widget.appWidgetId));
                hashMap.put(widget.provider.info, arrayList3);
              }
            }
          }
        }
      }
    }
    return hashMap;
  }

  public List getAllProvidersForProfile(int i, int i2, boolean z) {
    if (Binder.getCallingUid() != Process.myUid()) {
      throw new SecurityException("Can only be called by system user");
    }
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(i2);
      int size = this.mProviders.size();
      this.mContext.getPackageManager();
      ArrayList arrayList = null;
      for (int i3 = 0; i3 < size; i3++) {
        Provider provider = (Provider) this.mProviders.get(i3);
        if (provider == null) {
          Log.e(
              "AppWidgetServiceImpl",
              "getAllProvidersForProfile provider is null skip index: " + i3 + " size:" + size);
        } else {
          AppWidgetProviderInfo appWidgetProviderInfo = provider.info;
          if (!provider.zombie
              && (appWidgetProviderInfo.widgetCategory & i) != 0
              && appWidgetProviderInfo.getProfile().getIdentifier() == i2
              && (!z
                  || this.mSecurityPolicy.isProviderWhiteListed(
                      provider.f1641id.componentName.getPackageName(), i2))) {
            if (arrayList == null) {
              arrayList = new ArrayList();
            }
            arrayList.add(cloneIfLocalBinder(appWidgetProviderInfo));
          }
        }
      }
      if (arrayList == null || !isAppSeparationPresent(0)) {
        return arrayList;
      }
      return updateAppWidgetProviderInfoListWithAppSeparation(arrayList);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onStart() {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (contentResolver != null) {
      Slog.d("AppWidgetServiceImpl", "set appwidget_prevent_remove_all");
      Settings.Global.putInt(contentResolver, "appwidget_prevent_remove_all", 1);
    }
    this.mPackageManager = AppGlobals.getPackageManager();
    this.mSpm = (SemPersonaManager) this.mContext.getSystemService(SemPersonaManager.class);
    this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
    this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService("appops");
    this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
    this.mDevicePolicyManagerInternal =
        (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
    this.mPackageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    this.mSaveStateHandler = BackgroundThread.getHandler();
    ServiceThread serviceThread = new ServiceThread("AppWidgetServiceImpl", -2, false);
    serviceThread.start();
    this.mCallbackHandler = new CallbackHandler(serviceThread.getLooper());
    Object[] objArr = 0;
    this.mBackupRestoreController = new BackupRestoreController();
    this.mSecurityPolicy = new SecurityPolicy();
    this.mIsProviderInfoPersisted =
        !ActivityManager.isLowRamDeviceStatic()
            && DeviceConfig.getBoolean("systemui", "persists_widget_provider_info", true);
    this.mIsCombinedBroadcastEnabled =
        DeviceConfig.getBoolean("systemui", "combined_broadcast_enabled", true);
    if (!this.mIsProviderInfoPersisted) {
      Slog.d(
          "AppWidgetServiceImpl", "App widget provider info will not be persisted on this device");
    }
    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
    makeBasic.setBackgroundActivityStartsAllowed(false);
    makeBasic.setInteractive(true);
    this.mInteractiveBroadcast = makeBasic.toBundle();
    computeMaximumWidgetBitmapMemory();
    registerBroadcastReceiver();
    registerOnCrossProfileProvidersChangedListener();
    LocalServices.addService(AppWidgetManagerInternal.class, new AppWidgetManagerLocal());
    this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
    this.mLocale = Locale.getDefault();
    Configuration configuration = this.mContext.getResources().getConfiguration();
    this.mScreenDensity = configuration.densityDpi;
    this.mThemeSeq = configuration.themeSeq;
    ContainerStateReceiver.register(
        this.mContext,
        new ContainerStateReceiver() { // from class:
          // com.android.server.appwidget.AppWidgetServiceImpl.2
          public void onContainerAdminLocked(Context context, int i, Bundle bundle) {
            Slog.d("AppWidgetServiceImpl", "onContainerAdminLocked is triggered: " + i);
            synchronized (AppWidgetServiceImpl.this.mLock) {
              AppWidgetServiceImpl.this.reloadWidgetsMaskedState(i);
            }
          }

          public void onContainerAdminUnlocked(Context context, int i, Bundle bundle) {
            Slog.d("AppWidgetServiceImpl", "onContainerAdminUnlocked is triggered: " + i);
            synchronized (AppWidgetServiceImpl.this.mLock) {
              AppWidgetServiceImpl.this.reloadWidgetsMaskedState(i);
            }
          }
        });
  }

  public void systemServicesReady() {
    this.mActivityManagerInternal =
        (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    this.mAppOpsManagerInternal =
        (AppOpsManagerInternal) LocalServices.getService(AppOpsManagerInternal.class);
    this.mUsageStatsManagerInternal =
        (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
  }

  public final void computeMaximumWidgetBitmapMemory() {
    Display displayNoVerify = this.mContext.getDisplayNoVerify();
    Point point = new Point();
    displayNoVerify.getRealSize(point);
    this.mMaxWidgetBitmapMemory = point.x * 8 * point.y;
  }

  public final void registerBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, this.mCallbackHandler);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addDataScheme("package");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter2, null, this.mCallbackHandler);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
    intentFilter3.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter3, null, this.mCallbackHandler);
    IntentFilter intentFilter4 = new IntentFilter();
    intentFilter4.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
    intentFilter4.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter4, null, this.mCallbackHandler);
    IntentFilter intentFilter5 = new IntentFilter();
    intentFilter5.addAction("android.intent.action.PACKAGES_SUSPENDED");
    intentFilter5.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter5, null, this.mCallbackHandler);
    if (CoreRune.EM_SUPPORTED) {
      IntentFilter intentFilter6 = new IntentFilter();
      intentFilter6.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
      this.mContext.registerReceiverAsUser(
          this.mBroadcastReceiver, UserHandle.ALL, intentFilter6, null, null);
    }
  }

  public final void registerOnCrossProfileProvidersChangedListener() {
    DevicePolicyManagerInternal devicePolicyManagerInternal = this.mDevicePolicyManagerInternal;
    if (devicePolicyManagerInternal != null) {
      devicePolicyManagerInternal.addOnCrossProfileWidgetProvidersChangeListener(this);
    }
  }

  public void setSafeMode(boolean z) {
    this.mSafeMode = z;
  }

  public final void onConfigurationChanged() {
    boolean z;
    boolean z2;
    int i;
    Locale locale;
    Configuration configuration = this.mContext.getResources().getConfiguration();
    if (configuration.densityDpi != this.mScreenDensity) {
      computeMaximumWidgetBitmapMemory();
      this.mScreenDensity = configuration.densityDpi;
      z = true;
    } else {
      z = false;
    }
    if (configuration.themeSeq != this.mThemeSeq) {
      String string =
          Settings.System.getString(
              this.mContext.getContentResolver(), "current_sec_active_themepackage");
      z2 =
          (string == null && string != this.mThemePkg)
              || !(string == null || string.equals(this.mThemePkg));
      this.mThemePkg = string;
      this.mThemeSeq = configuration.themeSeq;
    } else {
      z2 = false;
    }
    Locale locale2 = Locale.getDefault();
    if (locale2 == null || (locale = this.mLocale) == null || !locale2.equals(locale) || z || z2) {
      this.mLocale = locale2;
      if (CoreRune.EM_SUPPORTED && ((i = this.mEmergencyState) == 2 || i == 4)) {
        Log.i("AppWidgetServiceImpl", "Emergency mode is now changing." + this.mEmergencyState);
        return;
      }
      synchronized (this.mLock) {
        ArrayList arrayList = new ArrayList(this.mProviders);
        HashSet hashSet = new HashSet();
        boolean isEmergencyMode =
            CoreRune.EM_SUPPORTED ? SemEmergencyManager.isEmergencyMode(this.mContext) : false;
        int i2 = -1;
        String str = null;
        SparseIntArray sparseIntArray = null;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
          Provider provider = (Provider) arrayList.get(size);
          if (provider == null) {
            Log.e(
                "AppWidgetServiceImpl",
                "onConfigurationChanged installedProviders is null skip index: " + size);
          } else {
            int userId = provider.getUserId();
            if (this.mUserManager.isUserUnlockingOrUnlocked(userId)
                && !isProfileWithLockedParent(userId)) {
              ensureGroupStateLoadedLocked(userId);
              if (!hashSet.contains(provider.f1641id)) {
                String packageName = provider.f1641id.componentName.getPackageName();
                int userId2 = provider.getUserId();
                if (userId2 != i2 || packageName == null || !packageName.equals(str)) {
                  if (updateProvidersForPackageLocked(
                      packageName, userId2, hashSet, true, isEmergencyMode)) {
                    if (sparseIntArray == null) {
                      sparseIntArray = new SparseIntArray();
                    }
                    int groupParent = this.mSecurityPolicy.getGroupParent(provider.getUserId());
                    sparseIntArray.put(groupParent, groupParent);
                  }
                  str = packageName;
                  i2 = userId2;
                }
              }
            }
          }
        }
        if (sparseIntArray != null) {
          int size2 = sparseIntArray.size();
          for (int i3 = 0; i3 < size2; i3++) {
            saveGroupStateAsync(sparseIntArray.get(i3));
          }
        }
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:49:0x00b6, code lost:

     r1 = r9.length;
     r2 = false;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x00b8, code lost:

     if (r4 >= r1) goto L98;
  */
  /* JADX WARN: Code restructure failed: missing block: B:51:0x00ba, code lost:

     r2 = r2 | removeHostsAndProvidersForPackageLocked(r9[r4], r19);
     r4 = r4 + 1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:53:0x00c4, code lost:

     r4 = r2;
  */
  /* JADX WARN: Code restructure failed: missing block: B:59:0x00c9, code lost:

     if (r1 == null) goto L66;
  */
  /* JADX WARN: Code restructure failed: missing block: B:61:0x00d1, code lost:

     if (r1.getBoolean("android.intent.extra.REPLACING", false) != false) goto L67;
  */
  /* JADX WARN: Code restructure failed: missing block: B:62:0x00d3, code lost:

     r11 = true;
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Removed duplicated region for block: B:29:0x0084  */
  /* JADX WARN: Removed duplicated region for block: B:55:0x0163 A[Catch: all -> 0x0172, TryCatch #0 {, blocks: (B:34:0x008c, B:36:0x0094, B:39:0x009c, B:44:0x00aa, B:49:0x00b6, B:51:0x00ba, B:55:0x0163, B:56:0x016e, B:60:0x00cb, B:63:0x00d6, B:65:0x00da, B:66:0x00e3, B:68:0x00e8, B:70:0x0118, B:72:0x0120, B:74:0x015b, B:75:0x013e, B:78:0x0152, B:80:0x0158, B:89:0x0170), top: B:33:0x008c }] */
  /* JADX WARN: Removed duplicated region for block: B:95:0x0175 A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void onPackageBroadcastReceived(Intent intent, int i) {
    boolean z;
    boolean z2;
    boolean z3;
    String[] stringArrayExtra;
    boolean z4;
    String str;
    int uidForPackage;
    String schemeSpecificPart;
    String action = intent.getAction();
    if (action == null) {
      Log.e("AppWidgetServiceImpl", "action is null");
      return;
    }
    z = true;
    z2 = false;
    int i2 = 0;
    switch (action) {
      case "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE":
        z3 = false;
        stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
        z4 = false;
        if (stringArrayExtra != null || stringArrayExtra.length == 0) {
          return;
        }
        synchronized (this.mLock) {
          if (this.mUserManager.isUserUnlockingOrUnlocked(i) && !isProfileWithLockedParent(i)) {
            ensureGroupStateLoadedLocked(i, false);
            Bundle extras = intent.getExtras();
            if (!z3 && !z4) {
              if (extras != null && extras.getBoolean("android.intent.extra.REPLACING", false)) {
                z = false;
                break;
              }
              if (z2) {
                saveGroupStateAsync(i);
                scheduleNotifyGroupHostsForProvidersChangedLocked(i);
                this.mBackupRestoreController.widgetComponentsChanged(i);
              }
              return;
            }
            Object[] objArr = false;
            boolean isEmergencyMode =
                CoreRune.EM_SUPPORTED ? SemEmergencyManager.isEmergencyMode(this.mContext) : false;
            int length = stringArrayExtra.length;
            int i3 = 0;
            boolean z5 = false;
            while (i3 < length) {
              String str2 = stringArrayExtra[i3];
              Log.i(
                  "AppWidgetServiceImpl",
                  "Received broadcast: "
                      + action
                      + ", Updates all providers with "
                      + str2
                      + ", userId: "
                      + i);
              if (!TextUtils.isEmpty(this.mSkipPackageName) && this.mSkipPackageName.equals(str2)) {
                Log.i(
                    "AppWidgetServiceImpl",
                    "Skip Update onProvider changed " + this.mSkipPackageName);
                this.mSkipPackageName = null;
                str = action;
              } else {
                str = action;
                z5 |= updateProvidersForPackageLocked(str2, i, null, false, isEmergencyMode);
                if (objArr != false && i == 0 && (uidForPackage = getUidForPackage(str2, i)) >= 0) {
                  resolveHostUidLocked(str2, uidForPackage);
                }
              }
              i3++;
              action = str;
            }
            z2 = z5;
            if (z2) {}
            return;
          }
          return;
        }
      case "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE":
        z3 = true;
        stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
        z4 = false;
        if (stringArrayExtra != null) {
          return;
        } else {
          return;
        }
      case "android.intent.action.PACKAGES_SUSPENDED":
      case "android.intent.action.PACKAGES_UNSUSPENDED":
        stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
        z4 = true;
        z3 = false;
        if (stringArrayExtra != null) {}
        break;
      default:
        Uri data = intent.getData();
        if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
          return;
        }
        z3 = "android.intent.action.PACKAGE_ADDED".equals(action);
        z4 = "android.intent.action.PACKAGE_CHANGED".equals(action);
        stringArrayExtra = new String[] {schemeSpecificPart};
        if (stringArrayExtra != null) {}
        break;
    }
  }

  public void reloadWidgetsMaskedStateForGroup(int i) {
    if (this.mUserManager.isUserUnlockingOrUnlocked(i)) {
      synchronized (this.mLock) {
        reloadWidgetsMaskedState(i);
        for (int i2 : this.mUserManager.getEnabledProfileIds(i)) {
          reloadWidgetsMaskedState(i2);
        }
      }
    }
  }

  public final void reloadWidgetsMaskedState(int i) {
    boolean z;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      UserInfo userInfo = this.mUserManager.getUserInfo(i);
      boolean z2 = !this.mUserManager.isUserUnlockingOrUnlocked(i);
      boolean isQuietModeEnabled = userInfo.isQuietModeEnabled();
      boolean isSuperLocked = userInfo.isSuperLocked();
      int size = this.mProviders.size();
      for (int i2 = 0; i2 < size; i2++) {
        Provider provider = (Provider) this.mProviders.get(i2);
        if (provider == null) {
          Log.i(
              "AppWidgetServiceImpl",
              "reloadWidgetsMaskedState provider is null. i:" + i2 + " size:" + size);
        } else if (provider.getUserId() == i) {
          boolean maskedByLockedProfileLocked =
              provider.setMaskedByLockedProfileLocked(z2)
                  | provider.setMaskedByQuietProfileLocked(isQuietModeEnabled)
                  | provider.setMaskedBySuperProfileLocked(isSuperLocked);
          try {
            try {
              z =
                  this.mPackageManager.isPackageSuspendedForUser(
                      provider.f1641id.componentName.getPackageName(), provider.getUserId());
            } catch (RemoteException e) {
              Slog.e("AppWidgetServiceImpl", "Failed to query application info", e);
            }
          } catch (IllegalArgumentException unused) {
            z = false;
          }
          maskedByLockedProfileLocked |= provider.setMaskedBySuspendedPackageLocked(z);
          if (maskedByLockedProfileLocked) {
            if (provider.isMaskedLocked()) {
              maskWidgetsViewsLocked(provider, null);
            } else {
              unmaskWidgetsViewsLocked(provider);
            }
          }
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void updateWidgetPackageSuspensionMaskedState(Intent intent, boolean z, int i) {
    String[] stringArrayExtra =
        intent.getStringArrayExtra("android.intent.extra.changed_package_list");
    if (stringArrayExtra == null) {
      return;
    }
    ArraySet arraySet = new ArraySet(Arrays.asList(stringArrayExtra));
    synchronized (this.mLock) {
      int size = this.mProviders.size();
      for (int i2 = 0; i2 < size; i2++) {
        Provider provider = (Provider) this.mProviders.get(i2);
        if (provider == null) {
          Log.i(
              "AppWidgetServiceImpl",
              "updateWidgetPackageSuspensionMaskedState provider is null. i:"
                  + i2
                  + " size:"
                  + size);
        } else if (provider.getUserId() == i
            && arraySet.contains(provider.f1641id.componentName.getPackageName())
            && provider.setMaskedBySuspendedPackageLocked(z)) {
          if (provider.isMaskedLocked()) {
            maskWidgetsViewsLocked(provider, null);
          } else {
            unmaskWidgetsViewsLocked(provider);
          }
        }
      }
    }
  }

  public final void maskWidgetsViewsLocked(Provider provider, Widget widget) {
    boolean z;
    Intent createConfirmDeviceCredentialIntent;
    Icon createWithResource;
    int size = provider.widgets.size();
    if (size == 0) {
      return;
    }
    RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), 17367516);
    ApplicationInfo applicationInfo = provider.info.providerInfo.applicationInfo;
    int userId = provider.getUserId();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (provider.maskedBySuperLocked) {
        createConfirmDeviceCredentialIntent = SemPersonaManager.createLockdownIntent(userId);
        z = false;
      } else {
        z = true;
        if (provider.maskedByQuietProfile) {
          createConfirmDeviceCredentialIntent =
              UnlaunchableAppActivity.createInQuietModeDialogIntent(userId);
        } else if (provider.maskedBySuspendedPackage) {
          boolean hasBadge = this.mUserManager.hasBadge(userId);
          String suspendingPackage =
              this.mPackageManagerInternal.getSuspendingPackage(
                  applicationInfo.packageName, userId);
          if ("android".equals(suspendingPackage)) {
            createConfirmDeviceCredentialIntent =
                this.mDevicePolicyManagerInternal.createShowAdminSupportIntent(userId, true);
          } else {
            createConfirmDeviceCredentialIntent =
                SuspendedAppActivity.createSuspendedAppInterceptIntent(
                    applicationInfo.packageName,
                    suspendingPackage,
                    this.mPackageManagerInternal.getSuspendedDialogInfo(
                        applicationInfo.packageName, suspendingPackage, userId),
                    (Bundle) null,
                    (IntentSender) null,
                    userId);
          }
          z = hasBadge;
        } else {
          createConfirmDeviceCredentialIntent =
              this.mKeyguardManager.createConfirmDeviceCredentialIntent(null, null, userId);
          if (createConfirmDeviceCredentialIntent != null) {
            createConfirmDeviceCredentialIntent.setFlags(276824064);
          }
        }
      }
      int i = applicationInfo.icon;
      if (i != 0) {
        createWithResource = Icon.createWithResource(applicationInfo.packageName, i);
      } else {
        createWithResource = Icon.createWithResource(this.mContext, R.drawable.sym_def_app_icon);
      }
      remoteViews.setImageViewIcon(16910011, createWithResource);
      if (!z) {
        remoteViews.setViewVisibility(16910012, 4);
      }
      for (int i2 = 0; i2 < size; i2++) {
        Widget widget2 = (Widget) provider.widgets.get(i2);
        if (widget == null || widget == widget2) {
          if (createConfirmDeviceCredentialIntent != null) {
            remoteViews.setOnClickPendingIntent(
                R.id.background,
                PendingIntent.getActivity(
                    this.mContext,
                    widget2.appWidgetId,
                    createConfirmDeviceCredentialIntent,
                    201326592));
          }
          if (widget2.replaceWithMaskedViewsLocked(remoteViews)) {
            scheduleNotifyUpdateAppWidgetLocked(widget2, widget2.getEffectiveViewsLocked());
          }
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void unmaskWidgetsViewsLocked(Provider provider) {
    int size = provider.widgets.size();
    for (int i = 0; i < size; i++) {
      Widget widget = (Widget) provider.widgets.get(i);
      if (widget.clearMaskedViewsLocked()) {
        scheduleNotifyUpdateAppWidgetLocked(widget, widget.getEffectiveViewsLocked());
      }
    }
  }

  public final void resolveHostUidLocked(String str, int i) {
    int size = this.mHosts.size();
    for (int i2 = 0; i2 < size; i2++) {
      Host host = (Host) this.mHosts.get(i2);
      HostId hostId = host.f1640id;
      if (hostId.uid == -1 && str.equals(hostId.packageName)) {
        HostId hostId2 = host.f1640id;
        host.f1640id = new HostId(i, hostId2.hostId, hostId2.packageName);
        return;
      }
    }
  }

  public final void ensureGroupStateLoadedLocked(int i) {
    ensureGroupStateLoadedLocked(i, true);
  }

  public final void ensureGroupStateLoadedLocked(int i, boolean z) {
    if (z && !isUserRunningAndUnlocked(i)) {
      throw new IllegalStateException(
          "User " + i + " must be unlocked for widgets to be available");
    }
    if (z && isProfileWithLockedParent(i)) {
      throw new IllegalStateException("Profile " + i + " must have unlocked parent");
    }
    int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
    IntArray intArray = new IntArray(1);
    for (int i2 : enabledGroupProfileIds) {
      if (!this.mLoadedUserIds.get(i2)) {
        this.mLoadedUserIds.put(i2, true);
        intArray.add(i2);
      }
    }
    if (intArray.size() <= 0) {
      return;
    }
    int[] array = intArray.toArray();
    clearProvidersAndHostsTagsLocked();
    loadGroupWidgetProvidersLocked(array);
    loadGroupStateLocked(array);
  }

  public final boolean isUserRunningAndUnlocked(int i) {
    return this.mUserManager.isUserUnlockingOrUnlocked(i);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "AppWidgetServiceImpl", printWriter)) {
      synchronized (this.mLock) {
        if (strArr.length > 0 && "--proto".equals(strArr[0])) {
          dumpProto(fileDescriptor);
        } else {
          dumpInternalLocked(printWriter);
        }
      }
    }
  }

  public final void dumpProto(FileDescriptor fileDescriptor) {
    Slog.i("AppWidgetServiceImpl", "dump proto for " + this.mWidgets.size() + " widgets");
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
    int size = this.mWidgets.size();
    for (int i = 0; i < size; i++) {
      dumpProtoWidget(protoOutputStream, (Widget) this.mWidgets.get(i));
    }
    protoOutputStream.flush();
  }

  public final void dumpProtoWidget(ProtoOutputStream protoOutputStream, Widget widget) {
    if (widget.host == null || widget.provider == null) {
      Slog.d(
          "AppWidgetServiceImpl",
          "skip dumping widget because host or provider is null: widget.host="
              + widget.host
              + " widget.provider="
              + widget.provider);
      return;
    }
    long start = protoOutputStream.start(2246267895809L);
    protoOutputStream.write(1133871366145L, widget.host.getUserId() != widget.provider.getUserId());
    protoOutputStream.write(1133871366146L, widget.host.callbacks == null);
    protoOutputStream.write(1138166333443L, widget.host.f1640id.packageName);
    protoOutputStream.write(1138166333444L, widget.provider.f1641id.componentName.getPackageName());
    protoOutputStream.write(1138166333445L, widget.provider.f1641id.componentName.getClassName());
    Bundle bundle = widget.options;
    if (bundle != null) {
      protoOutputStream.write(1133871366154L, bundle.getBoolean("appWidgetRestoreCompleted"));
      protoOutputStream.write(1120986464262L, widget.options.getInt("appWidgetMinWidth", 0));
      protoOutputStream.write(1120986464263L, widget.options.getInt("appWidgetMinHeight", 0));
      protoOutputStream.write(1120986464264L, widget.options.getInt("appWidgetMaxWidth", 0));
      protoOutputStream.write(1120986464265L, widget.options.getInt("appWidgetMaxHeight", 0));
    }
    protoOutputStream.end(start);
  }

  public final void dumpInternalLocked(PrintWriter printWriter) {
    int size = this.mProviders.size();
    printWriter.println("Providers:");
    for (int i = 0; i < size; i++) {
      dumpProviderLocked((Provider) this.mProviders.get(i), i, printWriter);
    }
    int size2 = this.mWidgets.size();
    printWriter.println(" ");
    printWriter.println("Widgets:");
    for (int i2 = 0; i2 < size2; i2++) {
      dumpWidget((Widget) this.mWidgets.get(i2), i2, printWriter);
    }
    int size3 = this.mHosts.size();
    printWriter.println(" ");
    printWriter.println("Hosts:");
    for (int i3 = 0; i3 < size3; i3++) {
      dumpHost((Host) this.mHosts.get(i3), i3, printWriter);
    }
    int size4 = this.mPackagesWithBindWidgetPermission.size();
    printWriter.println(" ");
    printWriter.println("Grants:");
    for (int i4 = 0; i4 < size4; i4++) {
      dumpGrant((Pair) this.mPackagesWithBindWidgetPermission.valueAt(i4), i4, printWriter);
    }
    int length = this.mHostHistory.length;
    printWriter.println(" ");
    printWriter.println("Host history:");
    for (int i5 = 0; i5 < length; i5++) {
      if (this.mHostHistory[i5] != null) {
        printWriter.print("  ");
        printWriter.println(this.mHostHistory[i5]);
      }
    }
    int length2 = this.mProvidersHistory.length;
    printWriter.println(" ");
    printWriter.println("Appwidget providers history:");
    for (int i6 = 0; i6 < length2; i6++) {
      if (this.mProvidersHistory[i6] != null) {
        printWriter.print("  ");
        printWriter.println(this.mProvidersHistory[i6]);
      }
    }
    int length3 = this.mAlarmHistory.length;
    printWriter.println(" ");
    printWriter.println("AppWidget alarm history:");
    for (int i7 = 0; i7 < length3; i7++) {
      if (this.mAlarmHistory[i7] != null) {
        printWriter.print("  ");
        printWriter.println(this.mAlarmHistory[i7]);
      }
    }
    printWriter.println(" ");
    printWriter.println("AppWidget restore history:");
    printWriter.println(
        AppWidgetLogWrapper.getLogText() == null ? "" : AppWidgetLogWrapper.getLogText());
  }

  public ParceledListSlice startListening(
      IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      if (this.mSecurityPolicy.isInstantAppLocked(str, callingUserId)) {
        Slog.w("AppWidgetServiceImpl", "Instant package " + str + " cannot host app widgets");
        return ParceledListSlice.emptyList();
      }
      ensureGroupStateLoadedLocked(callingUserId);
      Host lookupOrAddHostLocked =
          lookupOrAddHostLocked(new HostId(Binder.getCallingUid(), i, str));
      lookupOrAddHostLocked.callbacks = iAppWidgetHost;
      Log.i("AppWidgetServiceImpl", "startListening callbacks : " + iAppWidgetHost);
      updateHostHistoryLocked("Start listening : " + lookupOrAddHostLocked.f1640id);
      long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
      ArrayList arrayList = new ArrayList(iArr.length);
      LongSparseArray longSparseArray = new LongSparseArray();
      for (int i2 : iArr) {
        longSparseArray.clear();
        lookupOrAddHostLocked.getPendingUpdatesForIdLocked(this.mContext, i2, longSparseArray);
        int size = longSparseArray.size();
        for (int i3 = 0; i3 < size; i3++) {
          arrayList.add((PendingHostUpdate) longSparseArray.valueAt(i3));
        }
      }
      lookupOrAddHostLocked.lastWidgetUpdateSequenceNo = incrementAndGet;
      return new ParceledListSlice(arrayList);
    }
  }

  public void stopListening(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId, false);
      Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
      if (lookupHostLocked != null) {
        lookupHostLocked.callbacks = null;
        pruneHostLocked(lookupHostLocked);
        this.mAppOpsManagerInternal.updateAppWidgetVisibility(
            lookupHostLocked.getWidgetUidsIfBound(), false);
        Log.i("AppWidgetServiceImpl", "stopListening callbacks : null");
        updateHostHistoryLocked("Stop listening : " + lookupHostLocked.f1640id);
      }
    }
  }

  public int allocateAppWidgetId(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    Slog.i("AppWidgetServiceImpl", "allocateAppWidgetId() " + callingUserId);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      if (this.mSecurityPolicy.isInstantAppLocked(str, callingUserId)) {
        Slog.w("AppWidgetServiceImpl", "Instant package " + str + " cannot host app widgets");
        return 0;
      }
      ensureGroupStateLoadedLocked(callingUserId);
      if (this.mNextAppWidgetIds.indexOfKey(callingUserId) < 0) {
        this.mNextAppWidgetIds.put(callingUserId, 1);
      }
      int incrementAndGetAppWidgetIdLocked = incrementAndGetAppWidgetIdLocked(callingUserId);
      Host lookupOrAddHostLocked =
          lookupOrAddHostLocked(new HostId(Binder.getCallingUid(), i, str));
      Widget widget = new Widget();
      widget.appWidgetId = incrementAndGetAppWidgetIdLocked;
      widget.host = lookupOrAddHostLocked;
      lookupOrAddHostLocked.widgets.add(widget);
      addWidgetLocked(widget);
      saveGroupStateAsync(callingUserId);
      Slog.i(
          "AppWidgetServiceImpl",
          "Allocated widget id "
              + incrementAndGetAppWidgetIdLocked
              + " for host "
              + lookupOrAddHostLocked.f1640id);
      return incrementAndGetAppWidgetIdLocked;
    }
  }

  public void setAppWidgetHidden(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId, false);
      Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
      if (lookupHostLocked != null) {
        this.mAppOpsManagerInternal.updateAppWidgetVisibility(
            lookupHostLocked.getWidgetUidsIfBound(), false);
      }
    }
  }

  public void deleteAppWidgetId(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        return;
      }
      deleteAppWidgetLocked(lookupWidgetLocked);
      saveGroupStateAsync(callingUserId);
      if (this.isProductDEV) {
        Slog.i(
            "AppWidgetServiceImpl",
            "Deleted widget id "
                + i
                + " for host "
                + lookupWidgetLocked.host.f1640id
                + ", callingPackage = "
                + str);
        StringBuilder sb = new StringBuilder();
        sb.append("deleteAppWidgetId ");
        sb.append(i);
        sb.append(".u");
        sb.append(callingUserId);
        writeLogToFile(sb.toString());
      }
    }
  }

  public boolean hasBindAppWidgetPermission(String str, int i) {
    this.mSecurityPolicy.enforceModifyAppWidgetBindPermissions(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(i);
      if (getUidForPackage(str, i) < 0) {
        return false;
      }
      return this.mPackagesWithBindWidgetPermission.contains(Pair.create(Integer.valueOf(i), str));
    }
  }

  public void setBindAppWidgetPermission(String str, int i, boolean z) {
    this.mSecurityPolicy.enforceModifyAppWidgetBindPermissions(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(i);
      if (getUidForPackage(str, i) < 0) {
        return;
      }
      Pair create = Pair.create(Integer.valueOf(i), str);
      if (z) {
        this.mPackagesWithBindWidgetPermission.add(create);
      } else {
        this.mPackagesWithBindWidgetPermission.remove(create);
      }
      saveGroupStateAsync(i);
    }
  }

  public IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) {
    IntentSender intentSender;
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        throw new IllegalArgumentException("Bad widget id " + i);
      }
      Provider provider = lookupWidgetLocked.provider;
      if (provider == null) {
        throw new IllegalArgumentException("Widget not bound " + i);
      }
      Intent intent = new Intent("android.appwidget.action.APPWIDGET_CONFIGURE");
      intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
      intent.setComponent(provider.getInfoLocked(this.mContext).configure);
      intent.setFlags(i2 & (-196));
      ActivityOptions pendingIntentCreatorBackgroundActivityStartMode =
          ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        intentSender =
            PendingIntent.getActivityAsUser(
                    this.mContext,
                    0,
                    intent,
                    1409286144,
                    pendingIntentCreatorBackgroundActivityStartMode.toBundle(),
                    new UserHandle(provider.getUserId()))
                .getIntentSender();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return intentSender;
  }

  public IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2) {
    IntentSender intentSender;
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        throw new IllegalArgumentException("Bad widget id " + i);
      }
      Provider provider = lookupWidgetLocked.provider;
      if (provider == null) {
        throw new IllegalArgumentException("Widget not bound " + i);
      }
      Intent intent = new Intent("android.appwidget.action.SEM_APPWIDGET_CONFIGURE");
      intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
      intent.setComponent(provider.info.semConfigure);
      intent.setFlags(i2 & (-196));
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        intentSender =
            PendingIntent.getActivityAsUser(
                    this.mContext,
                    0,
                    intent,
                    1409286144,
                    null,
                    new UserHandle(provider.getUserId()))
                .getIntentSender();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    return intentSender;
  }

  public boolean bindAppWidgetId(
      String str, int i, int i2, ComponentName componentName, Bundle bundle) {
    int callingUserId = UserHandle.getCallingUserId();
    Log.i("AppWidgetServiceImpl", "bindAppWidgetId() " + i + ", from " + callingUserId);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    if (!this.mSecurityPolicy.isEnabledGroupProfile(i2)
        || !this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(
            componentName.getPackageName(), i2)) {
      return false;
    }
    String packageName = componentName.getPackageName();
    if (!MARsPolicyManager.getInstance().isSCPMTarget(packageName, callingUserId)) {
      MARsPolicyManager.getInstance().cancelDisablePolicy(packageName, callingUserId, 0);
    }
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      if (!this.mSecurityPolicy.hasCallerBindPermissionOrBindWhiteListedLocked(str)) {
        return false;
      }
      if (bundle != null && KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str)) {
        String string = bundle.getString("appWidgetIdForceAllocMode");
        int i3 = bundle.getInt("appWidgetIdForceAllocHostId", -1);
        if (string != null && i3 > 0) {
          Slog.d(
              "AppWidgetServiceImpl",
              "appWidgetId : " + i + ".HostId : " + i3 + ".alloc : " + string);
          allocateAppWidgetIdWhileBindingLocked(str, i3, i, string);
        }
      }
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        Slog.e("AppWidgetServiceImpl", "Bad widget id " + i);
        return false;
      }
      if (lookupWidgetLocked.provider != null) {
        Slog.e(
            "AppWidgetServiceImpl",
            "Widget id " + i + " already bound to: " + lookupWidgetLocked.provider.f1641id);
        return false;
      }
      int uidForPackage = getUidForPackage(componentName.getPackageName(), i2);
      if (uidForPackage < 0) {
        Slog.e(
            "AppWidgetServiceImpl",
            "Package " + componentName.getPackageName() + " not installed  for profile " + i2);
        return false;
      }
      Provider lookupProviderLocked =
          lookupProviderLocked(new ProviderId(uidForPackage, componentName));
      if (lookupProviderLocked == null) {
        Slog.e(
            "AppWidgetServiceImpl", "No widget provider " + componentName + " for profile " + i2);
        return false;
      }
      if (lookupProviderLocked.zombie) {
        Slog.e(
            "AppWidgetServiceImpl",
            "Can't bind to a 3rd party provider in safe mode " + lookupProviderLocked);
        return false;
      }
      lookupWidgetLocked.provider = lookupProviderLocked;
      Bundle cloneIfLocalBinder = bundle != null ? cloneIfLocalBinder(bundle) : new Bundle();
      lookupWidgetLocked.options = cloneIfLocalBinder;
      if (!cloneIfLocalBinder.containsKey("appWidgetCategory")) {
        lookupWidgetLocked.options.putInt("appWidgetCategory", 1);
      }
      lookupProviderLocked.widgets.add(lookupWidgetLocked);
      onWidgetProviderAddedOrChangedLocked(lookupWidgetLocked);
      if (lookupProviderLocked.widgets.size() == 1) {
        sendEnableAndUpdateIntentLocked(lookupProviderLocked, new int[] {i}, false);
      } else {
        sendUpdateIntentLocked(lookupProviderLocked, new int[] {i}, true, false);
      }
      registerForBroadcastsLocked(lookupProviderLocked, getWidgetIds(lookupProviderLocked.widgets));
      saveGroupStateAsync(callingUserId);
      Slog.i(
          "AppWidgetServiceImpl",
          "Bound widget " + i + " to provider " + lookupProviderLocked.f1641id);
      if (this.isProductDEV) {
        Slog.i(
            "AppWidgetServiceImpl",
            "Bound widget "
                + i
                + " to provider "
                + lookupProviderLocked.f1641id
                + ", callingPackage = "
                + str);
      }
      return true;
    }
  }

  public final boolean allocateAppWidgetIdWhileBindingLocked(
      String str, int i, int i2, String str2) {
    Host host;
    int callingUserId = UserHandle.getCallingUserId();
    if (this.mNextAppWidgetIds.indexOfKey(callingUserId) < 0) {
      this.mNextAppWidgetIds.put(callingUserId, 1);
    }
    Iterator it = this.mWidgets.iterator();
    boolean z = false;
    while (it.hasNext()) {
      Widget widget = (Widget) it.next();
      if (widget.appWidgetId == i2
          && (host = widget.host) != null
          && host.getUserId() == callingUserId) {
        z = true;
      }
    }
    if ("ifEmpty".equals(str2)) {
      if (z) {
        Slog.d("AppWidgetServiceImpl", "Can not allocate ID " + i2 + " #1");
        return false;
      }
      Host lookupOrAddHostLocked =
          lookupOrAddHostLocked(new HostId(Binder.getCallingUid(), i, str));
      Widget widget2 = new Widget();
      widget2.appWidgetId = i2;
      widget2.host = lookupOrAddHostLocked;
      lookupOrAddHostLocked.widgets.add(widget2);
      addWidgetLocked(widget2);
      setMinAppWidgetIdLocked(callingUserId, i2);
      if (this.isProductDEV) {
        Slog.i(
            "AppWidgetServiceImpl",
            "Allocated widget id " + i2 + " for host " + lookupOrAddHostLocked.f1640id);
      }
    }
    return true;
  }

  public int[] getAppWidgetIds(ComponentName componentName) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Provider lookupProviderLocked =
          lookupProviderLocked(new ProviderId(Binder.getCallingUid(), componentName));
      if (lookupProviderLocked != null) {
        return getWidgetIds(lookupProviderLocked.widgets);
      }
      return new int[0];
    }
  }

  public int[] getAppWidgetIdsForHost(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
      if (lookupHostLocked != null) {
        return getWidgetIds(lookupHostLocked.widgets);
      }
      return new int[0];
    }
  }

  public boolean bindRemoteViewsService(
      String str,
      int i,
      Intent intent,
      IApplicationThread iApplicationThread,
      IBinder iBinder,
      IServiceConnection iServiceConnection,
      long j) {
    int callingUserId = UserHandle.getCallingUserId();
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        throw new IllegalArgumentException("Bad widget id: " + i);
      }
      if (lookupWidgetLocked.provider == null) {
        throw new IllegalArgumentException("No provider for widget " + i);
      }
      ComponentName component = intent.getComponent();
      if (component == null) {
        Log.e("AppWidgetServiceImpl", "Component is null, Intent = " + intent);
        return false;
      }
      if (!component
          .getPackageName()
          .equals(lookupWidgetLocked.provider.f1641id.componentName.getPackageName())) {
        throw new SecurityException(
            "The taget service not in the same package as the widget provider");
      }
      this.mSecurityPolicy.enforceServiceExistsAndRequiresBindRemoteViewsPermission(
          component, lookupWidgetLocked.provider.getUserId());
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (ActivityManager.getService()
                .bindService(
                    iApplicationThread,
                    iBinder,
                    intent,
                    intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                    iServiceConnection,
                    j & 33554433,
                    this.mContext.getOpPackageName(),
                    lookupWidgetLocked.provider.getUserId())
            != 0) {
          incrementAppWidgetServiceRefCount(
              i,
              Pair.create(
                  Integer.valueOf(lookupWidgetLocked.provider.f1641id.uid),
                  new Intent.FilterComparison(intent)));
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return true;
        }
      } catch (RemoteException unused) {
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    }
  }

  public void deleteHost(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
      if (lookupHostLocked == null) {
        return;
      }
      deleteHostLocked(lookupHostLocked);
      saveGroupStateAsync(callingUserId);
      if (this.isProductDEV) {
        writeLogToFile("deleteHost " + i + " from " + str + ".u" + callingUserId);
      }
    }
  }

  public void deleteAllHosts() {
    int callingUserId = UserHandle.getCallingUserId();
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      boolean z = false;
      for (int size = this.mHosts.size() - 1; size >= 0; size--) {
        Host host = (Host) this.mHosts.get(size);
        if (host.f1640id.uid == Binder.getCallingUid()) {
          deleteHostLocked(host);
          z = true;
        }
      }
      if (z) {
        saveGroupStateAsync(callingUserId);
        if (this.isProductDEV) {
          writeLogToFile("deleteAllHosts from " + Binder.getCallingUid() + ".u" + callingUserId);
        }
      }
    }
  }

  public AppWidgetProviderInfo getAppWidgetInfo(String str, int i) {
    Provider provider;
    int callingUserId = UserHandle.getCallingUserId();
    Log.i("AppWidgetServiceImpl", "getAppWidgetInfo() " + i + ", from " + callingUserId);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked != null
          && (provider = lookupWidgetLocked.provider) != null
          && !provider.zombie) {
        return cloneIfLocalBinder(provider.getInfoLocked(this.mContext));
      }
      if (lookupWidgetLocked != null) {
        Log.e(
            "AppWidgetServiceImpl",
            "widget provider = " + lookupWidgetLocked.provider + " = " + i + ", " + callingUserId);
        if (lookupWidgetLocked.provider != null) {
          Log.e(
              "AppWidgetServiceImpl",
              "widget provider zombi = " + lookupWidgetLocked.provider.zombie);
        }
      } else {
        Log.e("AppWidgetServiceImpl", "widget is null = " + i + ", " + callingUserId);
      }
      return null;
    }
  }

  public RemoteViews getAppWidgetViews(String str, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        return null;
      }
      return cloneIfLocalBinder(lookupWidgetLocked.getEffectiveViewsLocked());
    }
  }

  public void updateAppWidgetOptions(String str, int i, Bundle bundle) {
    int callingUserId = UserHandle.getCallingUserId();
    Log.i(
        "AppWidgetServiceImpl",
        "updateAppWidgetOptions callingPackage :"
            + str
            + ", appWidgetId : "
            + i
            + ", options = "
            + bundle);
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked == null) {
        return;
      }
      lookupWidgetLocked.options.putAll(bundle);
      sendOptionsChangedIntentLocked(lookupWidgetLocked);
      saveGroupStateAsync(callingUserId);
    }
  }

  public Bundle getAppWidgetOptions(String str, int i) {
    Bundle bundle;
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
      if (lookupWidgetLocked != null && (bundle = lookupWidgetLocked.options) != null) {
        return cloneIfLocalBinder(bundle);
      }
      return Bundle.EMPTY;
    }
  }

  public void semSetSkipPackageChanged(String str) {
    if (TextUtils.isEmpty(str)) {
      return;
    }
    this.mSkipPackageName = str;
  }

  public void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) {
    updateAppWidgetIds(str, iArr, remoteViews, false);
  }

  public void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) {
    updateAppWidgetIds(str, iArr, remoteViews, true);
  }

  public void notifyProviderInheritance(ComponentName[] componentNameArr) {
    AppWidgetProviderInfo appWidgetProviderInfo;
    int callingUserId = UserHandle.getCallingUserId();
    if (componentNameArr == null) {
      return;
    }
    for (ComponentName componentName : componentNameArr) {
      if (componentName == null) {
        return;
      }
      this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
    }
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      for (ComponentName componentName2 : componentNameArr) {
        Provider lookupProviderLocked =
            lookupProviderLocked(new ProviderId(Binder.getCallingUid(), componentName2));
        if (lookupProviderLocked != null
            && (appWidgetProviderInfo = lookupProviderLocked.info) != null) {
          appWidgetProviderInfo.isExtendedFromAppWidgetProvider = true;
        }
        return;
      }
      saveGroupStateAsync(callingUserId);
    }
  }

  public void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(str);
    if (iArr == null || iArr.length == 0) {
      return;
    }
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      for (int i2 : iArr) {
        Widget lookupWidgetLocked = lookupWidgetLocked(i2, Binder.getCallingUid(), str);
        if (lookupWidgetLocked != null) {
          scheduleNotifyAppWidgetViewDataChanged(lookupWidgetLocked, i);
        }
      }
    }
  }

  public void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
      Provider lookupProviderLocked = lookupProviderLocked(providerId);
      if (lookupProviderLocked == null) {
        Slog.w("AppWidgetServiceImpl", "Provider doesn't exist " + providerId);
        return;
      }
      ArrayList arrayList = lookupProviderLocked.widgets;
      int size = arrayList.size();
      for (int i = 0; i < size; i++) {
        updateAppWidgetInstanceLocked((Widget) arrayList.get(i), remoteViews, false);
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void updateAppWidgetProviderInfo(ComponentName componentName, String str) {
    int callingUserId = UserHandle.getCallingUserId();
    this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
      Provider lookupProviderLocked = lookupProviderLocked(providerId);
      if (lookupProviderLocked == null) {
        throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
      }
      if (Objects.equals(lookupProviderLocked.infoTag, str)) {
        return;
      }
      String str2 = str == null ? "android.appwidget.provider" : str;
      ActivityInfo activityInfo = lookupProviderLocked.getPartialInfoLocked().providerInfo;
      AppWidgetProviderInfo parseAppWidgetProviderInfo =
          activityInfo != null
              ? parseAppWidgetProviderInfo(this.mContext, providerId, activityInfo, str2)
              : null;
      if (parseAppWidgetProviderInfo == null) {
        throw new IllegalArgumentException(
            "Unable to parse " + str2 + " meta-data to a valid AppWidget provider");
      }
      lookupProviderLocked.setInfoLocked(parseAppWidgetProviderInfo);
      lookupProviderLocked.infoTag = str;
      int size = lookupProviderLocked.widgets.size();
      for (int i = 0; i < size; i++) {
        Widget widget = (Widget) lookupProviderLocked.widgets.get(i);
        scheduleNotifyProviderChangedLocked(widget);
        updateAppWidgetInstanceLocked(widget, widget.views, false);
      }
      saveGroupStateAsync(callingUserId);
      scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
    }
  }

  public boolean isRequestPinAppWidgetSupported() {
    synchronized (this.mLock) {
      if (this.mSecurityPolicy.isCallerInstantAppLocked()) {
        Slog.w(
            "AppWidgetServiceImpl",
            "Instant uid " + Binder.getCallingUid() + " query information about app widgets");
        return false;
      }
      return ((ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class))
          .isRequestPinItemSupported(UserHandle.getCallingUserId(), 2);
    }
  }

  public boolean requestPinAppWidget(
      String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) {
    int callingUid = Binder.getCallingUid();
    int userId = UserHandle.getUserId(callingUid);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(userId);
      Provider lookupProviderLocked =
          lookupProviderLocked(new ProviderId(callingUid, componentName));
      if (lookupProviderLocked != null && !lookupProviderLocked.zombie) {
        AppWidgetProviderInfo infoLocked = lookupProviderLocked.getInfoLocked(this.mContext);
        if ((infoLocked.widgetCategory & 1) == 0) {
          return false;
        }
        return ((ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class))
            .requestPinAppWidget(str, infoLocked, bundle, intentSender, userId);
      }
      return false;
    }
  }

  public ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str) {
    boolean z;
    int identifier;
    int callingUserId = UserHandle.getCallingUserId();
    int callingUid = Binder.getCallingUid();
    if (!this.mSecurityPolicy.isEnabledGroupProfile(i2)) {
      return null;
    }
    synchronized (this.mLock) {
      if (this.mSecurityPolicy.isCallerInstantAppLocked()) {
        Slog.w(
            "AppWidgetServiceImpl",
            "Instant uid " + callingUid + " cannot access widget providers");
        return ParceledListSlice.emptyList();
      }
      ensureGroupStateLoadedLocked(callingUserId);
      ArrayList arrayList = new ArrayList();
      int size = this.mProviders.size();
      for (int i3 = 0; i3 < size; i3++) {
        Provider provider = (Provider) this.mProviders.get(i3);
        if (provider == null) {
          Log.i(
              "AppWidgetServiceImpl",
              "getInstalledProvidersForProfile provider is null. i:" + i3 + " size:" + size);
        } else {
          AppWidgetProviderInfo infoLocked = provider.getInfoLocked(this.mContext);
          String packageName = provider.f1641id.componentName.getPackageName();
          if (str != null && !packageName.equals(str)) {
            z = false;
            if (!provider.zombie
                && (infoLocked.widgetCategory & i) != 0
                && z
                && (identifier = infoLocked.getProfile().getIdentifier()) == i2
                && this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(
                    packageName, identifier)
                && !this.mPackageManagerInternal.filterAppAccess(packageName, callingUid, i2)) {
              arrayList.add(cloneIfLocalBinder(infoLocked));
            }
          }
          z = true;
          if (!provider.zombie) {
            arrayList.add(cloneIfLocalBinder(infoLocked));
          }
        }
      }
      if (isAppSeparationPresent(0)) {
        return new ParceledListSlice(updateAppWidgetProviderInfoListWithAppSeparation(arrayList));
      }
      return new ParceledListSlice(arrayList);
    }
  }

  public void changeHostIds(String str, int[] iArr, int i) {
    int callingUserId = UserHandle.getCallingUserId();
    if (iArr == null || iArr.length == 0) {
      return;
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      for (int i2 : iArr) {
        Widget lookupWidgetLocked = lookupWidgetLocked(i2, Binder.getCallingUid(), str);
        if (lookupWidgetLocked != null) {
          Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
          Log.i(
              "AppWidgetServiceImpl",
              "changeHostIds callingPackage :"
                  + str
                  + ", appWidgetId : "
                  + i2
                  + ", host = "
                  + lookupHostLocked);
          if (lookupHostLocked != null) {
            lookupWidgetLocked.host.widgets.remove(lookupWidgetLocked);
            lookupWidgetLocked.host = lookupHostLocked;
            lookupHostLocked.widgets.add(lookupWidgetLocked);
          }
        }
      }
    }
  }

  public final void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews, boolean z) {
    int callingUserId = UserHandle.getCallingUserId();
    if (iArr == null || iArr.length == 0) {
      return;
    }
    this.mSecurityPolicy.enforceCallFromPackage(str);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(callingUserId);
      for (int i : iArr) {
        Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
        if (lookupWidgetLocked != null) {
          updateAppWidgetInstanceLocked(lookupWidgetLocked, remoteViews, z);
        }
      }
    }
  }

  public final int incrementAndGetAppWidgetIdLocked(int i) {
    int peekNextAppWidgetIdLocked = peekNextAppWidgetIdLocked(i) + 1;
    this.mNextAppWidgetIds.put(i, peekNextAppWidgetIdLocked);
    return peekNextAppWidgetIdLocked;
  }

  public final void setMinAppWidgetIdLocked(int i, int i2) {
    if (peekNextAppWidgetIdLocked(i) < i2) {
      this.mNextAppWidgetIds.put(i, i2);
    }
  }

  public final int peekNextAppWidgetIdLocked(int i) {
    if (this.mNextAppWidgetIds.indexOfKey(i) < 0) {
      return 1;
    }
    return this.mNextAppWidgetIds.get(i);
  }

  public final Host lookupOrAddHostLocked(HostId hostId) {
    Host lookupHostLocked = lookupHostLocked(hostId);
    if (lookupHostLocked != null) {
      return lookupHostLocked;
    }
    Host host = new Host();
    host.f1640id = hostId;
    this.mHosts.add(host);
    Log.i("AppWidgetServiceImpl", "lookupOrAddHostLocked = " + hostId);
    return host;
  }

  public final void deleteHostLocked(Host host) {
    for (int size = host.widgets.size() - 1; size >= 0; size--) {
      deleteAppWidgetLocked((Widget) host.widgets.remove(size));
    }
    this.mHosts.remove(host);
    host.callbacks = null;
    Log.i("AppWidgetServiceImpl", "deleteHostLocked callbacks : null");
    updateHostHistoryLocked("Host deleted : " + host.f1640id);
  }

  public final void deleteAppWidgetLocked(Widget widget) {
    if (widget.provider == null) {
      Log.d("AppWidgetServiceImpl", "Provider is not yet set for widget " + widget.appWidgetId);
    } else {
      decrementAppWidgetServiceRefCount(widget);
    }
    Host host = widget.host;
    host.widgets.remove(widget);
    pruneHostLocked(host);
    removeWidgetLocked(widget);
    Provider provider = widget.provider;
    if (provider != null) {
      provider.widgets.remove(widget);
      if (provider.zombie) {
        return;
      }
      sendDeletedIntentLocked(widget);
      if (provider.widgets.isEmpty()) {
        cancelBroadcastsLocked(provider);
        sendDisabledIntentLocked(provider);
      } else {
        registerForBroadcastsLocked(provider, getWidgetIds(provider.widgets));
      }
    }
  }

  public final void cancelBroadcastsLocked(Provider provider) {
    final PendingIntent pendingIntent = provider.broadcast;
    if (pendingIntent != null) {
      this.mSaveStateHandler.post(
          new Runnable() { // from class:
            // com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              AppWidgetServiceImpl.this.lambda$cancelBroadcastsLocked$0(pendingIntent);
            }
          });
      updateAlarmHistoryLocked("cancel =" + provider);
      provider.broadcast = null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancelBroadcastsLocked$0(PendingIntent pendingIntent) {
    this.mAlarmManager.cancel(pendingIntent);
    pendingIntent.cancel();
  }

  public final void destroyRemoteViewsService(final Intent intent, Widget widget) {
    ServiceConnection serviceConnection =
        new ServiceConnection() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.3
          @Override // android.content.ServiceConnection
          public void onServiceDisconnected(ComponentName componentName) {}

          @Override // android.content.ServiceConnection
          public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
              IRemoteViewsFactory.Stub.asInterface(iBinder).onDestroy(intent);
            } catch (RemoteException e) {
              Slog.e("AppWidgetServiceImpl", "Error calling remove view factory", e);
            }
            AppWidgetServiceImpl.this.mContext.unbindService(this);
          }

          @Override // android.content.ServiceConnection
          public void onNullBinding(ComponentName componentName) {
            AppWidgetServiceImpl.this.mContext.unbindService(this);
          }
        };
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mContext.bindServiceAsUser(
          intent, serviceConnection, 33554433, widget.provider.f1641id.getProfile());
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void incrementAppWidgetServiceRefCount(int i, Pair pair) {
    HashSet hashSet;
    if (this.mRemoteViewsServicesAppWidgets.containsKey(pair)) {
      hashSet = (HashSet) this.mRemoteViewsServicesAppWidgets.get(pair);
    } else {
      HashSet hashSet2 = new HashSet();
      this.mRemoteViewsServicesAppWidgets.put(pair, hashSet2);
      hashSet = hashSet2;
    }
    hashSet.add(Integer.valueOf(i));
  }

  public final void decrementAppWidgetServiceRefCount(Widget widget) {
    Iterator it = this.mRemoteViewsServicesAppWidgets.keySet().iterator();
    while (it.hasNext()) {
      Pair pair = (Pair) it.next();
      HashSet hashSet = (HashSet) this.mRemoteViewsServicesAppWidgets.get(pair);
      if (hashSet.remove(Integer.valueOf(widget.appWidgetId)) && hashSet.isEmpty()) {
        destroyRemoteViewsService(((Intent.FilterComparison) pair.second).getIntent(), widget);
        it.remove();
      }
    }
  }

  public final void saveGroupStateAsync(int i) {
    this.mSaveStateHandler.post(new SaveStateRunnable(i));
  }

  public final void updateAppWidgetInstanceLocked(
      Widget widget, RemoteViews remoteViews, boolean z) {
    Provider provider;
    RemoteViews remoteViews2;
    int estimateMemoryUsage;
    RemoteViews remoteViews3;
    if (widget == null
        || (provider = widget.provider) == null
        || provider.zombie
        || widget.host.zombie) {
      return;
    }
    if (z && (remoteViews3 = widget.views) != null) {
      remoteViews3.mergeRemoteViews(remoteViews);
    } else {
      widget.views = remoteViews;
    }
    if (UserHandle.getAppId(Binder.getCallingUid()) != 1000
        && (remoteViews2 = widget.views) != null
        && (estimateMemoryUsage = remoteViews2.estimateMemoryUsage())
            > this.mMaxWidgetBitmapMemory) {
      widget.views = null;
      throw new IllegalArgumentException(
          "RemoteViews for widget update exceeds maximum bitmap memory usage (used: "
              + estimateMemoryUsage
              + ", max: "
              + this.mMaxWidgetBitmapMemory
              + ")");
    }
    scheduleNotifyUpdateAppWidgetLocked(widget, widget.getEffectiveViewsLocked());
  }

  public final void scheduleNotifyAppWidgetViewDataChanged(Widget widget, int i) {
    Host host;
    Provider provider;
    if (i == 0 || i == 1) {
      return;
    }
    long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
    if (widget != null) {
      widget.updateSequenceNos.put(i, incrementAndGet);
    }
    if (widget == null
        || (host = widget.host) == null
        || host.zombie
        || host.callbacks == null
        || (provider = widget.provider) == null
        || provider.zombie) {
      return;
    }
    SomeArgs obtain = SomeArgs.obtain();
    Host host2 = widget.host;
    obtain.arg1 = host2;
    obtain.arg2 = host2.callbacks;
    obtain.arg3 = Long.valueOf(incrementAndGet);
    obtain.argi1 = widget.appWidgetId;
    obtain.argi2 = i;
    this.mCallbackHandler.obtainMessage(4, obtain).sendToTarget();
  }

  public final void handleNotifyAppWidgetViewDataChanged(
      Host host, IAppWidgetHost iAppWidgetHost, int i, int i2, long j) {
    try {
      iAppWidgetHost.viewDataChanged(i, i2);
      host.lastWidgetUpdateSequenceNo = j;
    } catch (RemoteException unused) {
      r1 = host.callbacks != iAppWidgetHost;
      iAppWidgetHost = null;
    }
    synchronized (this.mLock) {
      if (iAppWidgetHost == null) {
        String str =
            "Host dead #4 : " + host.f1640id + ", appWidgetId : " + i + ", callbackChanged : " + r1;
        Slog.e("AppWidgetServiceImpl", str);
        updateHostHistoryLocked(str);
        if (!r1) {
          host.callbacks = null;
        }
        for (Pair pair : this.mRemoteViewsServicesAppWidgets.keySet()) {
          if (((HashSet) this.mRemoteViewsServicesAppWidgets.get(pair))
              .contains(Integer.valueOf(i))) {
            bindService(
                ((Intent.FilterComparison) pair.second).getIntent(),
                new ServiceConnection() { // from class:
                  // com.android.server.appwidget.AppWidgetServiceImpl.4
                  @Override // android.content.ServiceConnection
                  public void onServiceDisconnected(ComponentName componentName) {}

                  @Override // android.content.ServiceConnection
                  public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    try {
                      IRemoteViewsFactory.Stub.asInterface(iBinder).onDataSetChangedAsync();
                    } catch (RemoteException e) {
                      Slog.e("AppWidgetServiceImpl", "Error calling onDataSetChangedAsync()", e);
                    }
                    AppWidgetServiceImpl.this.mContext.unbindService(this);
                  }

                  @Override // android.content.ServiceConnection
                  public void onNullBinding(ComponentName componentName) {
                    AppWidgetServiceImpl.this.mContext.unbindService(this);
                  }
                },
                new UserHandle(UserHandle.getUserId(((Integer) pair.first).intValue())));
          }
        }
      }
    }
  }

  public final void scheduleNotifyUpdateAppWidgetLocked(Widget widget, RemoteViews remoteViews) {
    Provider provider;
    long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
    if (widget != null) {
      if (widget.trackingUpdate) {
        widget.trackingUpdate = false;
        Log.i("AppWidgetServiceImpl", "Widget update received " + widget.toString());
        Trace.asyncTraceEnd(
            64L,
            "appwidget update-intent " + widget.provider.f1641id.toString(),
            widget.appWidgetId);
      }
      widget.updateSequenceNos.put(0, incrementAndGet);
    }
    if (widget == null || (provider = widget.provider) == null || provider.zombie) {
      return;
    }
    Host host = widget.host;
    if (host.callbacks == null || host.zombie || widget.transactionError != null) {
      return;
    }
    if (remoteViews != null) {
      RemoteViews remoteViews2 = new RemoteViews(remoteViews);
      remoteViews2.setProviderInstanceId(incrementAndGet);
      remoteViews = remoteViews2;
    }
    SomeArgs obtain = SomeArgs.obtain();
    Host host2 = widget.host;
    obtain.arg1 = host2;
    obtain.arg2 = host2.callbacks;
    obtain.arg3 = remoteViews;
    obtain.arg4 = Long.valueOf(incrementAndGet);
    obtain.argi1 = widget.appWidgetId;
    this.mCallbackHandler.obtainMessage(1, obtain).sendToTarget();
  }

  public final void handleNotifyUpdateAppWidget(
      Host host, IAppWidgetHost iAppWidgetHost, int i, RemoteViews remoteViews, long j) {
    HashSet hashSet;
    String str = remoteViews == null ? "#NULL#" : remoteViews.getPackage();
    int pidFromPackage = getPidFromPackage(str);
    Log.d("AppWidgetServiceImpl", "handleNotifyUpdateAppWidget, appWidgetId = " + i);
    synchronized (this.mLock) {
      if (remoteViews != null) {
        int sequenceNumber = remoteViews.getSequenceNumber();
        Integer num =
            pidFromPackage == 0
                ? null
                : (Integer) this.mLastSeqNumber.get(Integer.valueOf(pidFromPackage));
        int intValue = num == null ? 800 : num.intValue();
        if (pidFromPackage != 0 && sequenceNumber > intValue + 200) {
          this.mLastSeqNumber.put(Integer.valueOf(pidFromPackage), Integer.valueOf(sequenceNumber));
          int fdFromPackage = getFdFromPackage(pidFromPackage, str);
          File[] listFiles = this.mFdFile.listFiles();
          int length = listFiles == null ? 0 : listFiles.length;
          if (fdFromPackage > 64) {
            Widget lookupWidgetLocked = lookupWidgetLocked(i, host.f1640id);
            deleteAppWidgetLocked(lookupWidgetLocked);
            Intent intent = new Intent("com.sec.android.launcher.action.UNBIND_WIDGET");
            intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
            sendBroadcastAsUser(intent, lookupWidgetLocked.provider.info.getProfile(), true);
            Intent intent2 = new Intent("com.samsung.android.appwidget.action.APPWIDGET_UNBIND");
            intent2.putExtra("appWidgetPackageName", str);
            sendBroadcastAsUser(intent2, lookupWidgetLocked.provider.info.getProfile(), true);
            System.gc();
            Slog.e(
                "AppWidgetServiceImpl",
                "Detected abnormal operation#1. seqNumber="
                    + sequenceNumber
                    + " pidFd="
                    + fdFromPackage
                    + " serverFd="
                    + length
                    + " kill widget="
                    + lookupWidgetLocked);
            return;
          }
          Log.d(
              "AppWidgetServiceImpl",
              "handleNotifyUpdateAppWidget("
                  + i
                  + ") mLastSeqNumber="
                  + this.mLastSeqNumber
                  + "%n views.estimateMemoryUsage()="
                  + remoteViews.estimateMemoryUsage()
                  + String.format(
                      "%nTotal Memory : %6.2f MB",
                      Double.valueOf(Runtime.getRuntime().totalMemory() / 1048576.0d))
                  + " pidFd="
                  + fdFromPackage
                  + " serverFd="
                  + length);
        } else if (sequenceNumber < intValue - 200) {
          HashMap hashMap = this.mLastSeqNumber;
          Integer valueOf = Integer.valueOf(pidFromPackage);
          if (sequenceNumber < 800) {
            sequenceNumber = 800;
          }
          hashMap.put(valueOf, Integer.valueOf(sequenceNumber));
        }
      }
      try {
        iAppWidgetHost.updateAppWidget(i, remoteViews);
        host.lastWidgetUpdateSequenceNo = j;
        if (pidFromPackage != 0) {
          String str2 = (String) this.mPidToPackageMap.get(Integer.valueOf(pidFromPackage));
          if (str2 != null && (hashSet = (HashSet) this.mPackageToPidMap.get(str2)) != null) {
            hashSet.remove(Integer.valueOf(pidFromPackage));
          }
          this.mPidToPackageMap.put(Integer.valueOf(pidFromPackage), str);
          HashSet hashSet2 = (HashSet) this.mPackageToPidMap.get(str);
          if (hashSet2 == null) {
            hashSet2 = new HashSet();
          }
          hashSet2.add(Integer.valueOf(pidFromPackage));
          this.mPackageToPidMap.put(str, hashSet2);
        }
      } catch (RemoteException e) {
        synchronized (this.mLock) {
          boolean z = host.callbacks != iAppWidgetHost;
          if (z) {
            Log.d("AppWidgetServiceImpl", "Skip callback clear #1.appWidgetId : " + i);
          } else if (e instanceof TransactionTooLargeException) {
            Log.d(
                "AppWidgetServiceImpl",
                "Skip callback clear #1.appWidgetId : "
                    + i
                    + " by TransactionTooLargeException. views.getPackage() = "
                    + str);
            Widget lookupWidgetLocked2 = lookupWidgetLocked(i, host.f1640id);
            if (lookupWidgetLocked2 != null) {
              lookupWidgetLocked2.transactionError = toTimestampFormat(e.toString());
            }
            File[] listFiles2 = this.mFdFile.listFiles();
            if (getFdFromPackage(pidFromPackage, str) <= 64) {
              Log.d(
                  "AppWidgetServiceImpl",
                  "TransactionTooLargeException, App occupied fd are under 64, not kill widget");
            } else if (listFiles2 != null && lookupWidgetLocked2 != null) {
              Log.d(
                  "AppWidgetServiceImpl",
                  "TransactionTooLargeException, App occupied fd are over 64, system fd count = "
                      + listFiles2.length
                      + ", kill widget, w = "
                      + lookupWidgetLocked2);
              deleteAppWidgetLocked(lookupWidgetLocked2);
              Intent intent3 = new Intent("com.sec.android.launcher.action.UNBIND_WIDGET");
              intent3.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
              sendBroadcastAsUser(intent3, lookupWidgetLocked2.provider.info.getProfile(), true);
              Intent intent4 = new Intent("com.samsung.android.appwidget.action.APPWIDGET_UNBIND");
              intent4.putExtra("appWidgetPackageName", str);
              sendBroadcastAsUser(intent4, lookupWidgetLocked2.provider.info.getProfile(), true);
              System.gc();
            } else {
              StringBuilder sb = new StringBuilder();
              sb.append(
                  "TransactionTooLargeException, failed to remove widget because : (fds == null)"
                      + " = ");
              sb.append(listFiles2 == null);
              sb.append(", w = ");
              sb.append(lookupWidgetLocked2);
              Log.d("AppWidgetServiceImpl", sb.toString());
            }
          } else {
            host.callbacks = null;
          }
          Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host.f1640id, e);
          updateHostHistoryLocked(
              "Dead host #1 : " + host.f1640id + " appWidgetId : " + i + " " + e + " " + z);
        }
      }
    }
  }

  public final void scheduleNotifyProviderChangedLocked(Widget widget) {
    Provider provider;
    long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
    if (widget != null) {
      widget.updateSequenceNos.clear();
      widget.updateSequenceNos.append(1, incrementAndGet);
    }
    if (widget == null || (provider = widget.provider) == null || provider.zombie) {
      return;
    }
    Host host = widget.host;
    if (host.callbacks == null || host.zombie) {
      return;
    }
    SomeArgs obtain = SomeArgs.obtain();
    Host host2 = widget.host;
    obtain.arg1 = host2;
    obtain.arg2 = host2.callbacks;
    obtain.arg3 = widget.provider.getInfoLocked(this.mContext);
    obtain.arg4 = Long.valueOf(incrementAndGet);
    obtain.argi1 = widget.appWidgetId;
    this.mCallbackHandler.obtainMessage(2, obtain).sendToTarget();
  }

  public final void handleNotifyProviderChanged(
      Host host,
      IAppWidgetHost iAppWidgetHost,
      int i,
      AppWidgetProviderInfo appWidgetProviderInfo,
      long j) {
    try {
      iAppWidgetHost.providerChanged(i, appWidgetProviderInfo);
      host.lastWidgetUpdateSequenceNo = j;
    } catch (RemoteException e) {
      synchronized (this.mLock) {
        if (host.callbacks != iAppWidgetHost) {
          Log.d("AppWidgetServiceImpl", "Skip callback clear #2.appWidgetId : " + i);
        } else {
          Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host.f1640id + ", " + i, e);
          updateHostHistoryLocked("Host dead #2 : " + host.f1640id + ", " + i + " " + e);
          host.callbacks = null;
        }
      }
    }
  }

  public final void scheduleNotifyAppWidgetRemovedLocked(Widget widget) {
    Provider provider;
    long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
    if (widget != null) {
      if (widget.trackingUpdate) {
        widget.trackingUpdate = false;
        Log.i("AppWidgetServiceImpl", "Widget removed " + widget.toString());
        Trace.asyncTraceEnd(
            64L,
            "appwidget update-intent " + widget.provider.f1641id.toString(),
            widget.appWidgetId);
      }
      widget.updateSequenceNos.clear();
    }
    if (widget == null || (provider = widget.provider) == null || provider.zombie) {
      return;
    }
    Host host = widget.host;
    if (host.callbacks == null || host.zombie) {
      return;
    }
    SomeArgs obtain = SomeArgs.obtain();
    Host host2 = widget.host;
    obtain.arg1 = host2;
    obtain.arg2 = host2.callbacks;
    obtain.arg3 = Long.valueOf(incrementAndGet);
    obtain.argi1 = widget.appWidgetId;
    this.mCallbackHandler.obtainMessage(5, obtain).sendToTarget();
  }

  public final void handleNotifyAppWidgetRemoved(
      Host host, IAppWidgetHost iAppWidgetHost, int i, long j) {
    try {
      iAppWidgetHost.appWidgetRemoved(i);
      host.lastWidgetUpdateSequenceNo = j;
    } catch (RemoteException e) {
      synchronized (this.mLock) {
        Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host.f1640id, e);
        host.callbacks = null;
      }
    }
  }

  public final void scheduleNotifyGroupHostsForProvidersChangedLocked(int i) {
    int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
    for (int size = this.mHosts.size() - 1; size >= 0; size--) {
      Host host = (Host) this.mHosts.get(size);
      int length = enabledGroupProfileIds.length;
      boolean z = false;
      int i2 = 0;
      while (true) {
        if (i2 >= length) {
          break;
        }
        if (host.getUserId() == enabledGroupProfileIds[i2]) {
          z = true;
          break;
        }
        i2++;
      }
      if (z && host != null && !host.zombie && host.callbacks != null) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = host;
        obtain.arg2 = host.callbacks;
        this.mCallbackHandler.obtainMessage(3, obtain).sendToTarget();
      }
    }
  }

  public final void handleNotifyProvidersChanged(Host host, IAppWidgetHost iAppWidgetHost) {
    try {
      iAppWidgetHost.providersChanged();
    } catch (RemoteException e) {
      synchronized (this.mLock) {
        if (host.callbacks != iAppWidgetHost) {
          Log.d("AppWidgetServiceImpl", "Skip callback clear #3.");
        } else {
          Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host.f1640id, e);
          updateHostHistoryLocked("Host dead #3 : " + host.f1640id + ", " + e);
          host.callbacks = null;
        }
      }
    }
  }

  public static boolean isLocalBinder() {
    return Process.myPid() == Binder.getCallingPid();
  }

  public static RemoteViews cloneIfLocalBinder(RemoteViews remoteViews) {
    return (!isLocalBinder() || remoteViews == null) ? remoteViews : remoteViews.clone();
  }

  public static AppWidgetProviderInfo cloneIfLocalBinder(
      AppWidgetProviderInfo appWidgetProviderInfo) {
    return (!isLocalBinder() || appWidgetProviderInfo == null)
        ? appWidgetProviderInfo
        : appWidgetProviderInfo.clone();
  }

  public static Bundle cloneIfLocalBinder(Bundle bundle) {
    return (!isLocalBinder() || bundle == null) ? bundle : (Bundle) bundle.clone();
  }

  public final Widget lookupWidgetLocked(int i, int i2, String str) {
    int size = this.mWidgets.size();
    for (int i3 = 0; i3 < size; i3++) {
      Widget widget = (Widget) this.mWidgets.get(i3);
      if (widget.appWidgetId == i && this.mSecurityPolicy.canAccessAppWidget(widget, i2, str)) {
        return widget;
      }
    }
    return null;
  }

  public final Provider lookupProviderLocked(ProviderId providerId) {
    int size = this.mProviders.size();
    int i = 0;
    while (true) {
      String str = null;
      if (i >= size) {
        break;
      }
      try {
        Provider provider = (Provider) this.mProviders.get(i);
        if (provider == null) {
          if (i > 0) {
            int i2 = i - 1;
            if (this.mProviders.get(i2) != null) {
              str = ((Provider) this.mProviders.get(i2)).f1641id.componentName.toString();
            }
          }
          Log.e(
              "AppWidgetServiceImpl",
              "lookupProviderLocked provider is null size:"
                  + size
                  + " index:"
                  + i
                  + " prevComp:"
                  + str);
          String callers = Debug.getCallers(5);
          StringBuilder sb = new StringBuilder();
          sb.append("caller:");
          sb.append(callers);
          Log.e("AppWidgetServiceImpl", sb.toString());
        } else if (provider.f1641id.equals(providerId)) {
          return provider;
        }
        i++;
      } catch (ArrayIndexOutOfBoundsException e) {
        Log.e("AppWidgetServiceImpl", "lookupProviderLocked " + e);
        return null;
      }
    }
  }

  public final Host lookupHostLocked(HostId hostId) {
    int size = this.mHosts.size();
    for (int i = 0; i < size; i++) {
      Host host = (Host) this.mHosts.get(i);
      if (host.f1640id.equals(hostId)) {
        return host;
      }
    }
    return null;
  }

  public final void pruneHostLocked(Host host) {
    if (host.widgets.size() == 0 && host.callbacks == null) {
      this.mHosts.remove(host);
    }
  }

  public final void loadGroupWidgetProvidersLocked(int[] iArr) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
    boolean isEmergencyMode =
        CoreRune.EM_SUPPORTED ? SemEmergencyManager.isEmergencyMode(this.mContext) : false;
    ArrayList arrayList = null;
    for (int i : iArr) {
      List queryIntentReceivers = queryIntentReceivers(intent, i, isEmergencyMode);
      if (queryIntentReceivers != null && !queryIntentReceivers.isEmpty()) {
        if (arrayList == null) {
          arrayList = new ArrayList();
        }
        arrayList.addAll(queryIntentReceivers);
      }
    }
    int size = arrayList == null ? 0 : arrayList.size();
    updateProvidersHistoryLocked(
        "loadGroupWidgetProvidersLocked, profileIds = "
            + Arrays.toString(iArr)
            + ", allReceivers.size() = "
            + size);
    boolean[] zArr = new boolean[size];
    ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
    CountDownLatch countDownLatch = new CountDownLatch(size);
    for (int i2 = 0; i2 < size; i2++) {
      newCachedThreadPool.execute(
          new AddProviderLockedRunnable(countDownLatch, (ResolveInfo) arrayList.get(i2), i2, zArr));
    }
    try {
      countDownLatch.await(3000L, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      e.printStackTrace();
    }
    newCachedThreadPool.shutdown();
    if (countDownLatch.getCount() > 0) {
      Slog.e("AppWidgetServiceImpl", "unhandled receivers exist, retry add unhandled providers");
      for (int i3 = 0; i3 < size; i3++) {
        if (!zArr[i3]) {
          addProviderLocked((ResolveInfo) arrayList.get(i3));
        }
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final boolean addProviderLocked(ResolveInfo resolveInfo) {
    if ((resolveInfo.activityInfo.applicationInfo.flags & 262144) != 0) {
      return false;
    }
    if (CoreRune.EM_SUPPORTED
        && SemEmergencyManager.isEmergencyMode(this.mContext)
        && !resolveInfo.activityInfo.enabled) {
      return false;
    }
    ActivityInfo activityInfo = resolveInfo.activityInfo;
    ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
    Object[] objArr = 0;
    Object[] objArr2 = 0;
    ProviderId providerId =
        new ProviderId(resolveInfo.activityInfo.applicationInfo.uid, componentName);
    Provider lookupProviderLocked = lookupProviderLocked(providerId);
    if (lookupProviderLocked == null) {
      lookupProviderLocked = lookupProviderLocked(new ProviderId(-1, componentName));
    }
    AppWidgetProviderInfo createPartialProviderInfo =
        createPartialProviderInfo(providerId, resolveInfo, lookupProviderLocked);
    if (createPartialProviderInfo == null) {
      return false;
    }
    if (lookupProviderLocked != null) {
      if (!lookupProviderLocked.zombie || this.mSafeMode) {
        return true;
      }
      lookupProviderLocked.f1641id = providerId;
      lookupProviderLocked.zombie = false;
      lookupProviderLocked.setPartialInfoLocked(createPartialProviderInfo);
      return true;
    }
    Provider provider = new Provider();
    provider.f1641id = providerId;
    provider.setPartialInfoLocked(createPartialProviderInfo);
    this.mProviders.add(provider);
    return true;
  }

  public final void deleteWidgetsLocked(Provider provider, int i) {
    int size = provider.widgets.size();
    if (!CoreRune.EM_SUPPORTED || !SemEmergencyManager.isEmergencyMode(this.mContext)) {
      for (int i2 = size - 1; i2 >= 0; i2--) {
        Widget widget = (Widget) provider.widgets.get(i2);
        if (i == -1 || i == widget.host.getUserId()) {
          provider.widgets.remove(i2);
          updateAppWidgetInstanceLocked(widget, null, false);
          widget.host.widgets.remove(widget);
          removeWidgetLocked(widget);
          widget.provider = null;
          pruneHostLocked(widget.host);
          widget.host = null;
        }
      }
      return;
    }
    for (int i3 = size - 1; i3 >= 0; i3--) {
      try {
        updateAppWidgetInstanceLocked((Widget) provider.widgets.get(i3), null, false);
        cancelBroadcastsLocked(provider);
        ComponentName componentName = provider.info.provider;
        if (componentName != null) {
          Elog.d(
              "AppWidgetServiceImpl", "deleteProviderLocked : " + componentName.getPackageName());
        } else {
          Elog.d("AppWidgetServiceImpl", "deleteProviderLocked component is null");
        }
      } catch (Exception e) {
        Elog.d("AppWidgetServiceImpl", "deleteProviderLocked e : " + e);
        e.printStackTrace();
      }
    }
  }

  public final void deleteProviderLocked(Provider provider) {
    synchronized (this.mLock) {
      deleteWidgetsLocked(provider, -1);
      String callers = Debug.getCallers(5);
      AppWidgetLogWrapper.m2i(
          "AppWidgetServiceImpl",
          "removeProviderLocked Provider is removed " + provider + " at " + this.mProviders.size());
      StringBuilder sb = new StringBuilder();
      sb.append("caller:");
      sb.append(callers);
      AppWidgetLogWrapper.m2i("AppWidgetServiceImpl", sb.toString());
      this.mProviders.remove(provider);
      cancelBroadcastsLocked(provider);
    }
  }

  public final void sendEnableAndUpdateIntentLocked(Provider provider, int[] iArr, boolean z) {
    AppWidgetProviderInfo appWidgetProviderInfo;
    if (!(this.mIsCombinedBroadcastEnabled
        && (appWidgetProviderInfo = provider.info) != null
        && appWidgetProviderInfo.isExtendedFromAppWidgetProvider)) {
      sendEnableIntentLocked(provider);
      sendUpdateIntentLocked(provider, iArr, true, z);
    } else {
      Intent intent = new Intent("android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE");
      intent.putExtra("appWidgetIds", iArr);
      intent.setComponent(provider.f1641id.componentName);
      sendBroadcastAsUser(intent, provider.f1641id.getProfile(), true);
    }
  }

  public final void sendEnableIntentLocked(Provider provider) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_ENABLED");
    intent.addFlags(268435456);
    intent.setComponent(provider.f1641id.componentName);
    sendBroadcastAsUser(intent, provider.f1641id.getProfile(), true);
  }

  public final void sendUpdateIntentLocked(Provider provider, int[] iArr, boolean z, boolean z2) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
    if (!z2) {
      intent.addFlags(268435456);
    }
    intent.addFlags(67108864);
    intent.putExtra("appWidgetIds", iArr);
    intent.setComponent(provider.f1641id.componentName);
    sendBroadcastAsUser(intent, provider.f1641id.getProfile(), z);
  }

  public final void sendDeletedIntentLocked(Widget widget) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_DELETED");
    intent.addFlags(268435456);
    intent.setComponent(widget.provider.f1641id.componentName);
    intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, widget.appWidgetId);
    sendBroadcastAsUser(intent, widget.provider.f1641id.getProfile(), false);
  }

  public final void sendDisabledIntentLocked(Provider provider) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_DISABLED");
    intent.addFlags(268435456);
    intent.setComponent(provider.f1641id.componentName);
    sendBroadcastAsUser(intent, provider.f1641id.getProfile(), false);
  }

  public void sendOptionsChangedIntentLocked(Widget widget) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS");
    intent.addFlags(268435456);
    intent.setComponent(widget.provider.f1641id.componentName);
    intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, widget.appWidgetId);
    intent.putExtra("appWidgetOptions", widget.options);
    if (this.isProductDEV) {
      Log.d(
          "AppWidgetServiceImpl",
          "sendOptionsChangedIntentLocked, widget = " + widget + ", options = " + widget.options);
    }
    sendBroadcastAsUser(intent, widget.provider.f1641id.getProfile(), true);
  }

  public final void registerForBroadcastsLocked(Provider provider, int[] iArr) {
    AppWidgetProviderInfo infoLocked = provider.getInfoLocked(this.mContext);
    if (infoLocked.updatePeriodMillis > 0) {
      Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
      intent.putExtra("appWidgetIds", iArr);
      intent.setComponent(infoLocked.provider);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        provider.broadcast =
            PendingIntent.getBroadcastAsUser(
                this.mContext, 1, intent, 201326592, infoLocked.getProfile());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (provider.broadcast != null) {
          final long max = Math.max(infoLocked.updatePeriodMillis, 1800000);
          final PendingIntent pendingIntent = provider.broadcast;
          this.mSaveStateHandler.post(
              new Runnable() { // from class:
                // com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  AppWidgetServiceImpl.this.lambda$registerForBroadcastsLocked$1(
                      max, pendingIntent);
                }
              });
          updateAlarmHistoryLocked("add =" + provider);
          return;
        }
        return;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
    cancelBroadcastsLocked(provider);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerForBroadcastsLocked$1(
      long j, PendingIntent pendingIntent) {
    this.mAlarmManager.setInexactRepeating(2, SystemClock.elapsedRealtime() + j, j, pendingIntent);
  }

  public static int[] getWidgetIds(ArrayList arrayList) {
    int size = arrayList.size();
    int[] iArr = new int[size];
    for (int i = 0; i < size; i++) {
      iArr[i] = ((Widget) arrayList.get(i)).appWidgetId;
    }
    return iArr;
  }

  public static void dumpProviderLocked(Provider provider, int i, PrintWriter printWriter) {
    AppWidgetProviderInfo partialInfoLocked = provider.getPartialInfoLocked();
    printWriter.print("  [");
    printWriter.print(i);
    printWriter.print("] provider ");
    printWriter.println(provider.f1641id);
    printWriter.print("    min=(");
    printWriter.print(partialInfoLocked.minWidth);
    printWriter.print("x");
    printWriter.print(partialInfoLocked.minHeight);
    printWriter.print(")   minResize=(");
    printWriter.print(partialInfoLocked.minResizeWidth);
    printWriter.print("x");
    printWriter.print(partialInfoLocked.minResizeHeight);
    printWriter.print(") updatePeriodMillis=");
    printWriter.print(partialInfoLocked.updatePeriodMillis);
    printWriter.print(" resizeMode=");
    printWriter.print(partialInfoLocked.resizeMode);
    printWriter.print(" widgetCategory=");
    printWriter.print(partialInfoLocked.widgetCategory);
    printWriter.print(" autoAdvanceViewId=");
    printWriter.print(partialInfoLocked.autoAdvanceViewId);
    printWriter.print(" initialLayout=#");
    printWriter.print(Integer.toHexString(partialInfoLocked.initialLayout));
    printWriter.print(" initialKeyguardLayout=#");
    printWriter.print(Integer.toHexString(partialInfoLocked.initialKeyguardLayout));
    printWriter.print("   zombie=");
    printWriter.println(provider.zombie);
  }

  public static void dumpHost(Host host, int i, PrintWriter printWriter) {
    printWriter.print("  [");
    printWriter.print(i);
    printWriter.print("] hostId=");
    printWriter.println(host.f1640id);
    printWriter.print("    callbacks=");
    printWriter.println(host.callbacks);
    printWriter.print("    widgets.size=");
    printWriter.print(host.widgets.size());
    printWriter.print(" zombie=");
    printWriter.println(host.zombie);
  }

  public static void dumpGrant(Pair pair, int i, PrintWriter printWriter) {
    printWriter.print("  [");
    printWriter.print(i);
    printWriter.print(']');
    printWriter.print(" user=");
    printWriter.print(pair.first);
    printWriter.print(" package=");
    printWriter.println((String) pair.second);
  }

  public static void dumpWidget(Widget widget, int i, PrintWriter printWriter) {
    printWriter.print("  [");
    printWriter.print(i);
    printWriter.print("] id=");
    printWriter.println(widget.appWidgetId);
    printWriter.print("    host=");
    printWriter.println(widget.host.f1640id);
    if (widget.provider != null) {
      printWriter.print("    provider=");
      printWriter.println(widget.provider.f1641id);
    }
    if (widget.host != null) {
      printWriter.print("    host.callbacks=");
      printWriter.println(widget.host.callbacks);
    }
    if (widget.views != null) {
      printWriter.print("    views=");
      printWriter.println(widget.views);
    }
    if (widget.options != null) {
      printWriter.print("    options=");
      printWriter.println(widget.options);
    }
    if (widget.transactionError != null) {
      printWriter.print("    transactionError=");
      printWriter.println(widget.transactionError);
    }
  }

  public static void serializeProvider(TypedXmlSerializer typedXmlSerializer, Provider provider) {
    Objects.requireNonNull(typedXmlSerializer);
    Objects.requireNonNull(provider);
    serializeProviderInner(typedXmlSerializer, provider, false);
  }

  public static void serializeProviderWithProviderInfo(
      TypedXmlSerializer typedXmlSerializer, Provider provider) {
    Objects.requireNonNull(typedXmlSerializer);
    Objects.requireNonNull(provider);
    serializeProviderInner(typedXmlSerializer, provider, true);
  }

  public static void serializeProviderInner(
      TypedXmlSerializer typedXmlSerializer, Provider provider, boolean z) {
    Objects.requireNonNull(typedXmlSerializer);
    Objects.requireNonNull(provider);
    typedXmlSerializer.startTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
    typedXmlSerializer.attribute(
        (String) null, "pkg", provider.f1641id.componentName.getPackageName());
    typedXmlSerializer.attribute(
        (String) null, "cl", provider.f1641id.componentName.getClassName());
    typedXmlSerializer.attributeIntHex((String) null, "tag", provider.tag);
    if (!TextUtils.isEmpty(provider.infoTag)) {
      typedXmlSerializer.attribute((String) null, "info_tag", provider.infoTag);
    }
    if (z && !provider.mInfoParsed) {
      Slog.d(
          "AppWidgetServiceImpl",
          "Provider info from " + provider.f1641id.componentName + " won't be persisted.");
    }
    if (z && provider.mInfoParsed) {
      AppWidgetXmlUtil.writeAppWidgetProviderInfoLocked(typedXmlSerializer, provider.info);
    }
    typedXmlSerializer.endTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
  }

  public static void serializeHost(TypedXmlSerializer typedXmlSerializer, Host host) {
    typedXmlSerializer.startTag((String) null, "h");
    typedXmlSerializer.attribute((String) null, "pkg", host.f1640id.packageName);
    typedXmlSerializer.attributeIntHex((String) null, "id", host.f1640id.hostId);
    typedXmlSerializer.attributeIntHex((String) null, "tag", host.tag);
    typedXmlSerializer.endTag((String) null, "h");
  }

  public static void serializeAppWidget(
      TypedXmlSerializer typedXmlSerializer, Widget widget, boolean z) {
    typedXmlSerializer.startTag((String) null, "g");
    typedXmlSerializer.attributeIntHex((String) null, "id", widget.appWidgetId);
    typedXmlSerializer.attributeIntHex((String) null, "rid", widget.restoredId);
    typedXmlSerializer.attributeIntHex((String) null, "h", widget.host.tag);
    Provider provider = widget.provider;
    if (provider != null) {
      typedXmlSerializer.attributeIntHex(
          (String) null, KnoxAnalyticsDataConverter.PAYLOAD, provider.tag);
    }
    Bundle bundle = widget.options;
    if (bundle != null) {
      int i = bundle.getInt("appWidgetMinWidth");
      int i2 = widget.options.getInt("appWidgetMinHeight");
      int i3 = widget.options.getInt("appWidgetMaxWidth");
      int i4 = widget.options.getInt("appWidgetMaxHeight");
      if (i <= 0) {
        i = 0;
      }
      typedXmlSerializer.attributeIntHex((String) null, "min_width", i);
      if (i2 <= 0) {
        i2 = 0;
      }
      typedXmlSerializer.attributeIntHex((String) null, "min_height", i2);
      if (i3 <= 0) {
        i3 = 0;
      }
      typedXmlSerializer.attributeIntHex((String) null, "max_width", i3);
      if (i4 <= 0) {
        i4 = 0;
      }
      typedXmlSerializer.attributeIntHex((String) null, "max_height", i4);
      typedXmlSerializer.attributeIntHex(
          (String) null, "host_category", widget.options.getInt("appWidgetCategory"));
      if (z) {
        typedXmlSerializer.attributeBoolean(
            (String) null,
            "restore_completed",
            widget.options.getBoolean("appWidgetRestoreCompleted"));
      }
      int i5 = widget.options.getInt("semAppWidgetColumnSpan");
      int i6 = widget.options.getInt("semAppWidgetRowSpan");
      if (i5 > 0 && i6 > 0) {
        typedXmlSerializer.attribute((String) null, "column_span", Integer.toHexString(i5));
        typedXmlSerializer.attribute((String) null, "row_span", Integer.toHexString(i6));
      }
    }
    typedXmlSerializer.endTag((String) null, "g");
  }

  public static Bundle parseWidgetIdOptions(TypedXmlPullParser typedXmlPullParser) {
    Bundle bundle = new Bundle();
    if (typedXmlPullParser.getAttributeBoolean((String) null, "restore_completed", false)) {
      bundle.putBoolean("appWidgetRestoreCompleted", true);
    }
    int attributeIntHex = typedXmlPullParser.getAttributeIntHex((String) null, "min_width", -1);
    if (attributeIntHex != -1) {
      bundle.putInt("appWidgetMinWidth", attributeIntHex);
    }
    int attributeIntHex2 = typedXmlPullParser.getAttributeIntHex((String) null, "min_height", -1);
    if (attributeIntHex2 != -1) {
      bundle.putInt("appWidgetMinHeight", attributeIntHex2);
    }
    int attributeIntHex3 = typedXmlPullParser.getAttributeIntHex((String) null, "max_width", -1);
    if (attributeIntHex3 != -1) {
      bundle.putInt("appWidgetMaxWidth", attributeIntHex3);
    }
    int attributeIntHex4 = typedXmlPullParser.getAttributeIntHex((String) null, "max_height", -1);
    if (attributeIntHex4 != -1) {
      bundle.putInt("appWidgetMaxHeight", attributeIntHex4);
    }
    int attributeIntHex5 =
        typedXmlPullParser.getAttributeIntHex((String) null, "host_category", -1);
    if (attributeIntHex5 != -1) {
      bundle.putInt("appWidgetCategory", attributeIntHex5);
    }
    int attributeIntHex6 = typedXmlPullParser.getAttributeIntHex((String) null, "column_span", -1);
    if (attributeIntHex6 != -1) {
      bundle.putInt("semAppWidgetColumnSpan", attributeIntHex6);
    }
    int attributeIntHex7 = typedXmlPullParser.getAttributeIntHex((String) null, "row_span", -1);
    if (attributeIntHex7 != -1) {
      bundle.putInt("semAppWidgetRowSpan", attributeIntHex7);
    }
    return bundle;
  }

  public List getWidgetParticipants(int i) {
    return this.mBackupRestoreController.getWidgetParticipants(i);
  }

  public byte[] getWidgetState(String str, int i) {
    return this.mBackupRestoreController.getWidgetState(str, i);
  }

  public void systemRestoreStarting(int i) {
    this.mBackupRestoreController.systemRestoreStarting(i);
  }

  public void restoreWidgetState(String str, byte[] bArr, int i) {
    this.mBackupRestoreController.restoreWidgetState(str, bArr, i);
  }

  public void systemRestoreFinished(int i) {
    this.mBackupRestoreController.systemRestoreFinished(i);
  }

  public final AppWidgetProviderInfo createPartialProviderInfo(
      ProviderId providerId, ResolveInfo resolveInfo, Provider provider) {
    Bundle bundle = resolveInfo.activityInfo.metaData;
    if (bundle == null) {
      return null;
    }
    if (!((provider == null
                || TextUtils.isEmpty(provider.infoTag)
                || bundle.getInt(provider.infoTag) == 0)
            ? false
            : true)
        && !(bundle.getInt("android.appwidget.provider") != 0)) {
      return null;
    }
    AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
    appWidgetProviderInfo.provider = providerId.componentName;
    appWidgetProviderInfo.providerInfo = resolveInfo.activityInfo;
    return appWidgetProviderInfo;
  }

  public static AppWidgetProviderInfo parseAppWidgetProviderInfo(
      Context context, ProviderId providerId, ActivityInfo activityInfo, String str) {
    int next;
    String string;
    PackageManager packageManager = context.getPackageManager();
    try {
      XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(packageManager, str);
      try {
        if (loadXmlMetaData == null) {
          Slog.w(
              "AppWidgetServiceImpl",
              "No " + str + " meta-data for AppWidget provider '" + providerId + '\'');
          if (loadXmlMetaData != null) {
            loadXmlMetaData.close();
          }
          return null;
        }
        AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
        do {
          next = loadXmlMetaData.next();
          if (next == 1) {
            break;
          }
        } while (next != 2);
        if (!"appwidget-provider".equals(loadXmlMetaData.getName())) {
          Slog.w(
              "AppWidgetServiceImpl",
              "Meta-data does not start with appwidget-provider tag for AppWidget provider "
                  + providerId.componentName
                  + " for user "
                  + providerId.uid);
          loadXmlMetaData.close();
          return null;
        }
        AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
        appWidgetProviderInfo.provider = providerId.componentName;
        appWidgetProviderInfo.providerInfo = activityInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          Resources resourcesForApplication =
              packageManager.getResourcesForApplication(
                  packageManager.getApplicationInfoAsUser(
                      activityInfo.packageName, 0, UserHandle.getUserId(providerId.uid)));
          Binder.restoreCallingIdentity(clearCallingIdentity);
          TypedArray obtainAttributes =
              resourcesForApplication.obtainAttributes(
                  asAttributeSet, com.android.internal.R.styleable.AppWidgetProviderInfo);
          TypedValue peekValue = obtainAttributes.peekValue(1);
          appWidgetProviderInfo.minWidth = peekValue != null ? peekValue.data : 0;
          TypedValue peekValue2 = obtainAttributes.peekValue(2);
          appWidgetProviderInfo.minHeight = peekValue2 != null ? peekValue2.data : 0;
          TypedValue peekValue3 = obtainAttributes.peekValue(9);
          appWidgetProviderInfo.minResizeWidth =
              peekValue3 != null ? peekValue3.data : appWidgetProviderInfo.minWidth;
          TypedValue peekValue4 = obtainAttributes.peekValue(10);
          appWidgetProviderInfo.minResizeHeight =
              peekValue4 != null ? peekValue4.data : appWidgetProviderInfo.minHeight;
          TypedValue peekValue5 = obtainAttributes.peekValue(15);
          appWidgetProviderInfo.maxResizeWidth = peekValue5 != null ? peekValue5.data : 0;
          TypedValue peekValue6 = obtainAttributes.peekValue(16);
          appWidgetProviderInfo.maxResizeHeight = peekValue6 != null ? peekValue6.data : 0;
          appWidgetProviderInfo.targetCellWidth = obtainAttributes.getInt(17, 0);
          appWidgetProviderInfo.targetCellHeight = obtainAttributes.getInt(18, 0);
          appWidgetProviderInfo.updatePeriodMillis = obtainAttributes.getInt(3, 0);
          appWidgetProviderInfo.initialLayout = obtainAttributes.getResourceId(4, 0);
          appWidgetProviderInfo.initialKeyguardLayout = obtainAttributes.getResourceId(11, 0);
          String string2 = obtainAttributes.getString(5);
          if (string2 != null) {
            appWidgetProviderInfo.configure =
                new ComponentName(providerId.componentName.getPackageName(), string2);
          }
          Bundle bundle = activityInfo.metaData;
          if (bundle != null
              && (string = bundle.getString("android.appwidget.provider.semConfigureActivity"))
                  != null) {
            appWidgetProviderInfo.semConfigure =
                new ComponentName(providerId.componentName.getPackageName(), string);
          }
          appWidgetProviderInfo.label = activityInfo.loadLabel(packageManager).toString();
          appWidgetProviderInfo.icon = activityInfo.getIconResource();
          appWidgetProviderInfo.previewImage = obtainAttributes.getResourceId(6, 0);
          appWidgetProviderInfo.previewLayout = obtainAttributes.getResourceId(14, 0);
          appWidgetProviderInfo.autoAdvanceViewId = obtainAttributes.getResourceId(7, -1);
          appWidgetProviderInfo.resizeMode = obtainAttributes.getInt(8, 0);
          appWidgetProviderInfo.widgetCategory = obtainAttributes.getInt(12, 1);
          boolean z =
              bundle != null
                  && AppWidgetManager.SEM_APPWIDGET_STYLE_COMPLICATION.equals(
                      bundle.getString("widgetStyle"));
          appWidgetProviderInfo.widgetFeatures = obtainAttributes.getInt(13, 0);
          appWidgetProviderInfo.descriptionRes = obtainAttributes.getResourceId(0, 0);
          obtainAttributes.recycle();
          if (!z) {
            for (int i = 0; i < asAttributeSet.getAttributeCount(); i++) {
              String attributeName = asAttributeSet.getAttributeName(i);
              if ("widgetStyle".equals(attributeName)) {
                if ((asAttributeSet.getAttributeIntValue(i, 0) & 2) == 2) {
                  z = true;
                }
              } else {
                if ("targetHost".equals(attributeName)) {
                  if ((asAttributeSet.getAttributeIntValue(i, 0) & 1) != 0) {}
                  z = true;
                }
              }
            }
          }
          if (z) {
            appWidgetProviderInfo.widgetCategory = IInstalld.FLAG_FORCE;
          }
          loadXmlMetaData.close();
          return appWidgetProviderInfo;
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      } finally {
      }
    } catch (PackageManager.NameNotFoundException
        | IOException
        | IllegalArgumentException
        | XmlPullParserException e) {
      Slog.w(
          "AppWidgetServiceImpl",
          "XML parsing failed for AppWidget provider "
              + providerId.componentName
              + " for user "
              + providerId.uid,
          e);
      return null;
    }
  }

  public final int getUidForPackage(String str, int i) {
    PackageInfo packageInfo;
    ApplicationInfo applicationInfo;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      packageInfo = this.mPackageManager.getPackageInfo(str, 0L, i);
      Binder.restoreCallingIdentity(clearCallingIdentity);
    } catch (RemoteException unused) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      packageInfo = null;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
      return -1;
    }
    return applicationInfo.uid;
  }

  public final ActivityInfo getProviderInfo(ComponentName componentName, int i) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
    intent.setComponent(componentName);
    List queryIntentReceivers =
        queryIntentReceivers(
            intent,
            i,
            CoreRune.EM_SUPPORTED ? SemEmergencyManager.isEmergencyMode(this.mContext) : false);
    if (queryIntentReceivers.isEmpty()) {
      return null;
    }
    return ((ResolveInfo) queryIntentReceivers.get(0)).activityInfo;
  }

  public final boolean isKnoxUser(int i) {
    return SemPersonaManager.isKnoxId(i);
  }

  public final boolean isAppSeparationPresent(int i) {
    SemPersonaManager semPersonaManager;
    return SemPersonaManager.isDoEnabled(i)
        && (semPersonaManager = this.mSpm) != null
        && semPersonaManager.isAppSeparationPresent();
  }

  public final List updateAppWidgetProviderInfoListWithAppSeparation(List list) {
    HashSet hashSet = new HashSet(this.mSpm.getSeparatedAppsList());
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      AppWidgetProviderInfo appWidgetProviderInfo = (AppWidgetProviderInfo) it.next();
      if (!hashSet.contains(appWidgetProviderInfo.getActivityInfo().packageName)) {
        arrayList.add(appWidgetProviderInfo);
      }
    }
    return arrayList;
  }

  public final List queryIntentReceivers(Intent intent, int i, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int i2 = (isProfileWithUnlockedParent(i) ? 269222016 : 268435584) | 1024;
      if (z && !isKnoxUser(i)) {
        i2 |= 512;
      }
      List list =
          this.mPackageManager
              .queryIntentReceivers(
                  intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), i2, i)
              .getList();
      AppWidgetLogWrapper.m2i(
          "AppWidgetServiceImpl",
          "queryIntentReceivers intent : "
              + intent
              + "  userid : "
              + i
              + "  flags : "
              + i2
              + " result : "
              + list.size());
      return list;
    } catch (RemoteException e) {
      AppWidgetLogWrapper.m2i(
          "AppWidgetServiceImpl", "Fail to get providerinfo by RemoteException" + e.getMessage());
      return Collections.emptyList();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void handleUserUnlocked(int i) {
    if (isProfileWithLockedParent(i)) {
      return;
    }
    if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
      Slog.w("AppWidgetServiceImpl", "User " + i + " is no longer unlocked - exiting");
      return;
    }
    long elapsedRealtime = SystemClock.elapsedRealtime();
    synchronized (this.mLock) {
      Trace.traceBegin(64L, "appwidget ensure");
      ensureGroupStateLoadedLocked(i);
      Trace.traceEnd(64L);
      Trace.traceBegin(64L, "appwidget reload");
      reloadWidgetsMaskedStateForGroup(this.mSecurityPolicy.getGroupParent(i));
      Trace.traceEnd(64L);
      int size = this.mProviders.size();
      for (int i2 = 0; i2 < size; i2++) {
        final Provider provider = (Provider) this.mProviders.get(i2);
        if (provider != null && provider.getUserId() == i) {
          if (provider.widgets.size() > 0) {
            Trace.traceBegin(
                64L, "appwidget init " + provider.f1641id.componentName.getPackageName());
            provider.widgets.forEach(
                new Consumer() { // from class:
                  // com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda2
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    AppWidgetServiceImpl.lambda$handleUserUnlocked$2(
                        AppWidgetServiceImpl.Provider.this, (AppWidgetServiceImpl.Widget) obj);
                  }
                });
            int[] widgetIds = getWidgetIds(provider.widgets);
            sendEnableAndUpdateIntentLocked(provider, widgetIds, false);
            registerForBroadcastsLocked(provider, widgetIds);
            Trace.traceEnd(64L);
          }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("handleUserUnlocked provider is null:");
        sb.append(provider == null);
        Log.i("AppWidgetServiceImpl", sb.toString());
      }
    }
    Slog.i(
        "AppWidgetServiceImpl",
        "Processing of handleUserUnlocked u"
            + i
            + " took "
            + (SystemClock.elapsedRealtime() - elapsedRealtime)
            + " ms");
  }

  public static /* synthetic */ void lambda$handleUserUnlocked$2(Provider provider, Widget widget) {
    widget.trackingUpdate = true;
    Trace.asyncTraceBegin(
        64L, "appwidget update-intent " + provider.f1641id.toString(), widget.appWidgetId);
    Log.i("AppWidgetServiceImpl", "Widget update scheduled on unlock " + widget.toString());
  }

  public final void loadGroupStateLocked(int[] iArr) {
    ArrayList arrayList = new ArrayList();
    int i = 0;
    for (int i2 : iArr) {
      try {
        FileInputStream openRead = getSavedStateFile(i2).openRead();
        try {
          i = readProfileStateFromFileLocked(openRead, i2, arrayList);
          if (openRead != null) {
            openRead.close();
          }
        } catch (Throwable th) {
          if (openRead != null) {
            try {
              openRead.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
          }
          throw th;
        }
      } catch (IOException e) {
        Slog.w("AppWidgetServiceImpl", "Failed to read state: " + e);
        if (this.isProductDEV) {
          writeLogToFile("Failed to read state.u" + i2 + " : " + e);
        }
      }
    }
    if (i >= 0) {
      bindLoadedWidgetsLocked(arrayList);
      performUpgradeLocked(i);
      return;
    }
    Log.w("AppWidgetServiceImpl", "Failed to read state, clearing widgets and hosts.");
    clearWidgetsLocked();
    this.mHosts.clear();
    int size = this.mProviders.size();
    for (int i3 = 0; i3 < size; i3++) {
      if (this.mProviders.get(i3) == null) {
        Log.e(
            "AppWidgetServiceImpl",
            i3 + " is null, pre total size = " + size + ",but  now = " + this.mProviders.size());
      } else {
        ((Provider) this.mProviders.get(i3)).widgets.clear();
      }
    }
  }

  public final void bindLoadedWidgetsLocked(List list) {
    for (int size = list.size() - 1; size >= 0; size--) {
      LoadedWidgetState loadedWidgetState = (LoadedWidgetState) list.remove(size);
      Widget widget = loadedWidgetState.widget;
      Provider findProviderByTag = findProviderByTag(loadedWidgetState.providerTag);
      widget.provider = findProviderByTag;
      if (findProviderByTag != null) {
        Host findHostByTag = findHostByTag(loadedWidgetState.hostTag);
        widget.host = findHostByTag;
        if (findHostByTag != null) {
          widget.provider.widgets.add(widget);
          widget.host.widgets.add(widget);
          addWidgetLocked(widget);
        }
      }
    }
  }

  public final Provider findProviderByTag(int i) {
    if (i < 0) {
      return null;
    }
    int size = this.mProviders.size();
    for (int i2 = 0; i2 < size; i2++) {
      Provider provider = (Provider) this.mProviders.get(i2);
      if (provider == null) {
        Log.i(
            "AppWidgetServiceImpl",
            "findProviderByTag provider is null. i:" + i2 + " size:" + size);
      } else if (provider.tag == i) {
        return provider;
      }
    }
    return null;
  }

  public final Host findHostByTag(int i) {
    if (i < 0) {
      return null;
    }
    int size = this.mHosts.size();
    for (int i2 = 0; i2 < size; i2++) {
      Host host = (Host) this.mHosts.get(i2);
      if (host.tag == i) {
        return host;
      }
    }
    return null;
  }

  public void addWidgetLocked(Widget widget) {
    Log.i(
        "AppWidgetServiceImpl",
        "addWidgetLocked, widget: "
            + widget
            + ", widget id: "
            + widget.appWidgetId
            + ", Callers: "
            + Debug.getCallers(7));
    this.mWidgets.add(widget);
    onWidgetProviderAddedOrChangedLocked(widget);
  }

  public void onWidgetProviderAddedOrChangedLocked(Widget widget) {
    Provider provider = widget.provider;
    if (provider == null) {
      return;
    }
    int userId = provider.getUserId();
    synchronized (this.mWidgetPackagesLock) {
      ArraySet arraySet = (ArraySet) this.mWidgetPackages.get(userId);
      if (arraySet == null) {
        SparseArray sparseArray = this.mWidgetPackages;
        ArraySet arraySet2 = new ArraySet();
        sparseArray.put(userId, arraySet2);
        arraySet = arraySet2;
      }
      arraySet.add(widget.provider.f1641id.componentName.getPackageName());
    }
    if (widget.provider.isMaskedLocked()) {
      maskWidgetsViewsLocked(widget.provider, widget);
    } else {
      widget.clearMaskedViewsLocked();
    }
  }

  public void removeWidgetLocked(Widget widget) {
    Log.i(
        "AppWidgetServiceImpl",
        "removeWidgetLocked, widget: "
            + widget
            + ", widget id: "
            + widget.appWidgetId
            + ", Callers: "
            + Debug.getCallers(7));
    this.mWidgets.remove(widget);
    onWidgetRemovedLocked(widget);
    scheduleNotifyAppWidgetRemovedLocked(widget);
  }

  public final void onWidgetRemovedLocked(Widget widget) {
    Provider provider = widget.provider;
    if (provider == null) {
      return;
    }
    int userId = provider.getUserId();
    String packageName = widget.provider.f1641id.componentName.getPackageName();
    synchronized (this.mWidgetPackagesLock) {
      ArraySet arraySet = (ArraySet) this.mWidgetPackages.get(userId);
      if (arraySet == null) {
        return;
      }
      int size = this.mWidgets.size();
      for (int i = 0; i < size; i++) {
        Widget widget2 = (Widget) this.mWidgets.get(i);
        Provider provider2 = widget2.provider;
        if (provider2 != null
            && provider2.getUserId() == userId
            && packageName.equals(widget2.provider.f1641id.componentName.getPackageName())) {
          return;
        }
      }
      arraySet.remove(packageName);
    }
  }

  public void clearWidgetsLocked() {
    this.mWidgets.clear();
    onWidgetsClearedLocked();
  }

  public final void onWidgetsClearedLocked() {
    synchronized (this.mWidgetPackagesLock) {
      this.mWidgetPackages.clear();
    }
  }

  public boolean isBoundWidgetPackage(String str, int i) {
    if (Binder.getCallingUid() != 1000) {
      throw new SecurityException("Only the system process can call this");
    }
    synchronized (this.mWidgetPackagesLock) {
      ArraySet arraySet = (ArraySet) this.mWidgetPackages.get(i);
      if (arraySet == null) {
        return false;
      }
      return arraySet.contains(str);
    }
  }

  public final void saveStateLocked(int i) {
    boolean z;
    tagProvidersAndHosts();
    int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
    try {
      z = ActivityManager.getService().getCurrentUser().isMaintenanceMode();
    } catch (RemoteException e) {
      Log.e("AppWidgetServiceImpl", "Couldn't get user info", e);
      z = false;
    }
    for (int i2 : enabledGroupProfileIds) {
      if (!z || i2 == 77) {
        AtomicFile savedStateFile = getSavedStateFile(i2);
        try {
          FileOutputStream startWrite = savedStateFile.startWrite();
          if (writeProfileStateToFileLocked(startWrite, i2)) {
            savedStateFile.finishWrite(startWrite);
          } else {
            savedStateFile.failWrite(startWrite);
            Slog.w("AppWidgetServiceImpl", "Failed to save state, restoring backup.");
          }
        } catch (IOException e2) {
          Slog.w("AppWidgetServiceImpl", "Failed open state file for write: " + e2);
        }
      }
    }
  }

  public final void tagProvidersAndHosts() {
    int size = this.mProviders.size();
    for (int i = 0; i < size; i++) {
      Provider provider = (Provider) this.mProviders.get(i);
      if (provider == null) {
        Log.i(
            "AppWidgetServiceImpl", "findProviderByTag provider is null. i:" + i + " size:" + size);
      } else {
        provider.tag = i;
      }
    }
    int size2 = this.mHosts.size();
    for (int i2 = 0; i2 < size2; i2++) {
      ((Host) this.mHosts.get(i2)).tag = i2;
    }
  }

  public final void clearProvidersAndHostsTagsLocked() {
    int size = this.mProviders.size();
    for (int i = 0; i < size; i++) {
      Provider provider = (Provider) this.mProviders.get(i);
      if (provider == null) {
        Log.i(
            "AppWidgetServiceImpl",
            "clearProvidersAndHostsTagsLocked provider is null. i:" + i + " size:" + size);
      } else {
        provider.tag = -1;
      }
    }
    int size2 = this.mHosts.size();
    for (int i2 = 0; i2 < size2; i2++) {
      ((Host) this.mHosts.get(i2)).tag = -1;
    }
  }

  public final boolean writeProfileStateToFileLocked(FileOutputStream fileOutputStream, int i) {
    try {
      TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
      resolveSerializer.startDocument((String) null, Boolean.TRUE);
      resolveSerializer.startTag((String) null, "gs");
      resolveSerializer.attributeInt((String) null, "version", 1);
      int size = this.mProviders.size();
      for (int i2 = 0; i2 < size; i2++) {
        Provider provider = (Provider) this.mProviders.get(i2);
        if (provider == null) {
          Log.i(
              "AppWidgetServiceImpl",
              "writeProfileStateToFileLocked provider is null. i:" + i2 + " size:" + size);
        } else if (provider.getUserId() == i) {
          if (this.mIsProviderInfoPersisted) {
            serializeProviderWithProviderInfo(resolveSerializer, provider);
          } else if (provider.shouldBePersisted()) {
            serializeProvider(resolveSerializer, provider);
          }
        }
      }
      int size2 = this.mHosts.size();
      for (int i3 = 0; i3 < size2; i3++) {
        Host host = (Host) this.mHosts.get(i3);
        if (host.getUserId() == i) {
          serializeHost(resolveSerializer, host);
        }
      }
      int size3 = this.mWidgets.size();
      for (int i4 = 0; i4 < size3; i4++) {
        Widget widget = (Widget) this.mWidgets.get(i4);
        if (widget.host.getUserId() == i) {
          serializeAppWidget(resolveSerializer, widget, true);
        }
      }
      Iterator it = this.mPackagesWithBindWidgetPermission.iterator();
      while (it.hasNext()) {
        Pair pair = (Pair) it.next();
        if (((Integer) pair.first).intValue() == i) {
          resolveSerializer.startTag((String) null, "b");
          resolveSerializer.attribute((String) null, "packageName", (String) pair.second);
          resolveSerializer.endTag((String) null, "b");
        }
      }
      resolveSerializer.endTag((String) null, "gs");
      resolveSerializer.endDocument();
      return true;
    } catch (IOException e) {
      Slog.w("AppWidgetServiceImpl", "Failed to write state: " + e);
      AppWidgetLogWrapper.m2i("AppWidgetServiceImpl", "Failed to write appwidgets.xml: " + e);
      return false;
    }
  }

  public final void onEmergencyModeDisabled(int i) {
    Elog.v("AppWidgetServiceImpl", "onEmergencyModeDisabled()." + i);
    synchronized (this.mLock) {
      ensureGroupStateLoadedLocked(i);
      Iterator it = new ArrayList(this.mProviders).iterator();
      boolean z = false;
      while (it.hasNext()) {
        Provider provider = (Provider) it.next();
        if (provider.getUserId() == i) {
          String packageName = provider.info.provider.getPackageName();
          if (provider.widgets.size() > 0) {
            z |= updateProvidersForPackageLocked(packageName, i, null, false, true);
          }
        }
      }
      if (z) {
        saveGroupStateAsync(i);
        scheduleNotifyGroupHostsForProvidersChangedLocked(i);
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:16:0x0376 A[LOOP:0: B:8:0x0024->B:16:0x0376, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:17:0x0375 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:25:0x03b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:84:0x020e A[Catch: IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, TryCatch #6 {IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, blocks: (B:60:0x005d, B:67:0x007c, B:70:0x0083, B:73:0x009a, B:75:0x00aa, B:79:0x00e1, B:79:0x00e1, B:79:0x00e1, B:79:0x00e1, B:79:0x00e1, B:81:0x00e5, B:81:0x00e5, B:81:0x00e5, B:81:0x00e5, B:81:0x00e5, B:84:0x020e, B:84:0x020e, B:84:0x020e, B:84:0x020e, B:84:0x020e, B:86:0x0253, B:86:0x0253, B:86:0x0253, B:86:0x0253, B:86:0x0253, B:87:0x012c, B:87:0x012c, B:87:0x012c, B:87:0x012c, B:87:0x012c, B:89:0x0130, B:89:0x0130, B:89:0x0130, B:89:0x0130, B:89:0x0130, B:91:0x0138, B:91:0x0138, B:91:0x0138, B:91:0x0138, B:91:0x0138, B:93:0x0155, B:93:0x0155, B:93:0x0155, B:93:0x0155, B:93:0x0155, B:95:0x0199, B:95:0x0199, B:95:0x0199, B:95:0x0199, B:95:0x0199, B:97:0x01a5, B:97:0x01a5, B:97:0x01a5, B:97:0x01a5, B:97:0x01a5, B:108:0x01f4, B:108:0x01f4, B:108:0x01f4, B:108:0x01f4, B:108:0x01f4, B:118:0x0277, B:118:0x0277, B:118:0x0277, B:118:0x0277, B:118:0x0277, B:122:0x0291, B:122:0x0291, B:122:0x0291, B:122:0x0291, B:122:0x0291), top: B:59:0x005d }] */
  /* JADX WARN: Removed duplicated region for block: B:86:0x0253 A[Catch: IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, TryCatch #6 {IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException -> 0x035b, blocks: (B:60:0x005d, B:67:0x007c, B:70:0x0083, B:73:0x009a, B:75:0x00aa, B:79:0x00e1, B:79:0x00e1, B:79:0x00e1, B:79:0x00e1, B:79:0x00e1, B:81:0x00e5, B:81:0x00e5, B:81:0x00e5, B:81:0x00e5, B:81:0x00e5, B:84:0x020e, B:84:0x020e, B:84:0x020e, B:84:0x020e, B:84:0x020e, B:86:0x0253, B:86:0x0253, B:86:0x0253, B:86:0x0253, B:86:0x0253, B:87:0x012c, B:87:0x012c, B:87:0x012c, B:87:0x012c, B:87:0x012c, B:89:0x0130, B:89:0x0130, B:89:0x0130, B:89:0x0130, B:89:0x0130, B:91:0x0138, B:91:0x0138, B:91:0x0138, B:91:0x0138, B:91:0x0138, B:93:0x0155, B:93:0x0155, B:93:0x0155, B:93:0x0155, B:93:0x0155, B:95:0x0199, B:95:0x0199, B:95:0x0199, B:95:0x0199, B:95:0x0199, B:97:0x01a5, B:97:0x01a5, B:97:0x01a5, B:97:0x01a5, B:97:0x01a5, B:108:0x01f4, B:108:0x01f4, B:108:0x01f4, B:108:0x01f4, B:108:0x01f4, B:118:0x0277, B:118:0x0277, B:118:0x0277, B:118:0x0277, B:118:0x0277, B:122:0x0291, B:122:0x0291, B:122:0x0291, B:122:0x0291, B:122:0x0291), top: B:59:0x005d }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int readProfileStateFromFileLocked(
      FileInputStream fileInputStream, int i, List list) {
    Object obj;
    File file;
    boolean isEmergencyMode;
    boolean z;
    int i2;
    int i3;
    int i4;
    int i5;
    String str;
    TypedXmlPullParser typedXmlPullParser;
    int i6;
    String str2;
    int i7;
    int i8;
    int uidForPackage;
    int i9;
    int i10;
    String str3;
    String str4;
    TypedXmlPullParser typedXmlPullParser2;
    Exception exc;
    Provider provider;
    ActivityInfo activityInfo;
    int i11 = i;
    String str5 = "h";
    String str6 = KnoxAnalyticsDataConverter.PAYLOAD;
    try {
      TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
      int i12 = 0;
      if (CoreRune.EM_SUPPORTED) {
        try {
          isEmergencyMode = SemEmergencyManager.isEmergencyMode(this.mContext);
        } catch (IOException
            | IndexOutOfBoundsException
            | NullPointerException
            | NumberFormatException
            | XmlPullParserException e) {
          obj = e;
          Slog.w("AppWidgetServiceImpl", "failed parsing " + obj);
          AppWidgetLogWrapper.m2i("AppWidgetServiceImpl", "Failed to read appwidgets.xml: " + obj);
          file = new File("/data/log/dump_appwidgets.xml");
          if (file.exists()) {}
          try {
            file.createNewFile();
          } catch (Exception e2) {
            e2.printStackTrace();
          }
          try {
            FileOutputStream fileOutputStream =
                new FileOutputStream("/data/log/dump_appwidgets.xml");
            try {
              Files.copy(getStateFile(i).toPath(), fileOutputStream);
              fileOutputStream.close();
              return -1;
            } finally {
            }
          } catch (Exception e3) {
            e3.printStackTrace();
            return -1;
          }
        }
      } else {
        isEmergencyMode = false;
      }
      int i13 = -1;
      int i14 = -1;
      int i15 = -1;
      while (true) {
        int next = resolvePullParser.next();
        if (next == 2) {
          String name = resolvePullParser.getName();
          if ("gs".equals(name)) {
            i15 = resolvePullParser.getAttributeInt((String) null, "version", i12);
            z = isEmergencyMode;
            i2 = i14;
            i7 = next;
            i5 = i11;
            str = str6;
            typedXmlPullParser = resolvePullParser;
            i6 = i12;
            i8 = 1;
            str2 = str5;
            if (i7 == i8) {
              return i15;
            }
            str5 = str2;
            i12 = i6;
            str6 = str;
            i14 = i2;
            resolvePullParser = typedXmlPullParser;
            i11 = i5;
            isEmergencyMode = z;
          } else {
            if (str6.equals(name)) {
              i13++;
              try {
                String attributeValue = resolvePullParser.getAttributeValue((String) null, "pkg");
                String attributeValue2 = resolvePullParser.getAttributeValue((String) null, "cl");
                String canonicalPackageName =
                    getCanonicalPackageName(attributeValue, attributeValue2, i11);
                if (canonicalPackageName != null
                    && (uidForPackage = getUidForPackage(canonicalPackageName, i11)) >= 0) {
                  i9 = i15;
                  ComponentName componentName =
                      new ComponentName(canonicalPackageName, attributeValue2);
                  ActivityInfo providerInfo = getProviderInfo(componentName, i11);
                  if (providerInfo == null) {
                    str4 = str5;
                    str3 = str6;
                    typedXmlPullParser = resolvePullParser;
                    z = isEmergencyMode;
                    i2 = i14;
                    i10 = next;
                    i5 = i;
                    i15 = i9;
                    i7 = i10;
                    str = str3;
                    str2 = str4;
                  } else {
                    i10 = next;
                    str3 = str6;
                    ProviderId providerId = new ProviderId(uidForPackage, componentName);
                    Provider lookupProviderLocked = lookupProviderLocked(providerId);
                    if (lookupProviderLocked == null) {
                      StringBuilder sb = new StringBuilder();
                      i2 = i14;
                      sb.append("readProfileStateFromFileLocked mProviders size: ");
                      sb.append(this.mProviders.size());
                      AppWidgetLogWrapper.m2i("AppWidgetServiceImpl", sb.toString());
                    } else {
                      i2 = i14;
                    }
                    str4 = str5;
                    if (lookupProviderLocked == null) {
                      z = isEmergencyMode;
                      if (this.mSafeMode) {
                        AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
                        appWidgetProviderInfo.provider = providerId.componentName;
                        appWidgetProviderInfo.providerInfo = providerInfo;
                        provider = new Provider();
                        provider.setPartialInfoLocked(appWidgetProviderInfo);
                        provider.zombie = true;
                        provider.f1641id = providerId;
                        this.mProviders.add(provider);
                        Log.i(
                            "AppWidgetServiceImpl",
                            "Safe mode Provider is added "
                                + provider
                                + " at "
                                + this.mProviders.size());
                        typedXmlPullParser2 = resolvePullParser;
                        lookupProviderLocked = provider;
                        if (lookupProviderLocked == null) {
                          String callers = Debug.getCallers(6);
                          AppWidgetLogWrapper.m2i(
                              "AppWidgetServiceImpl",
                              "lookupProvider is failed, package: "
                                  + canonicalPackageName
                                  + ", componentName: "
                                  + componentName
                                  + ", uid: "
                                  + uidForPackage
                                  + ", mProviders size: "
                                  + this.mProviders.size());
                          StringBuilder sb2 = new StringBuilder();
                          sb2.append("caller:");
                          sb2.append(callers);
                          AppWidgetLogWrapper.m2i("AppWidgetServiceImpl", sb2.toString());
                          i13 = i13;
                          typedXmlPullParser = typedXmlPullParser2;
                          i5 = i;
                          i15 = i9;
                          i7 = i10;
                          str = str3;
                          str2 = str4;
                        } else {
                          i13 = i13;
                          typedXmlPullParser = typedXmlPullParser2;
                          lookupProviderLocked.tag =
                              typedXmlPullParser.getAttributeIntHex((String) null, "tag", i13);
                          lookupProviderLocked.infoTag =
                              typedXmlPullParser.getAttributeValue((String) null, "info_tag");
                          i5 = i;
                          i15 = i9;
                          i7 = i10;
                          str = str3;
                          str2 = str4;
                        }
                      }
                    } else {
                      z = isEmergencyMode;
                    }
                    if (this.mIsProviderInfoPersisted) {
                      AppWidgetProviderInfo readAppWidgetProviderInfoLocked =
                          AppWidgetXmlUtil.readAppWidgetProviderInfoLocked(resolvePullParser);
                      typedXmlPullParser2 = resolvePullParser;
                      if (readAppWidgetProviderInfoLocked == null) {
                        StringBuilder sb3 = new StringBuilder();
                        activityInfo = providerInfo;
                        sb3.append("Unable to load widget provider info from xml for ");
                        sb3.append(providerId.componentName);
                        Slog.d("AppWidgetServiceImpl", sb3.toString());
                      } else {
                        activityInfo = providerInfo;
                      }
                      if (lookupProviderLocked == null) {
                        String callers2 = Debug.getCallers(6);
                        AppWidgetLogWrapper.m2i(
                            "AppWidgetServiceImpl",
                            "lookupProvider is failed, package: "
                                + canonicalPackageName
                                + ", componentName: "
                                + componentName
                                + ", uid: "
                                + uidForPackage
                                + ", mProviders size: "
                                + this.mProviders.size());
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("caller:");
                        sb4.append(callers2);
                        AppWidgetLogWrapper.m2i("AppWidgetServiceImpl", sb4.toString());
                        i13 = i13;
                        typedXmlPullParser = typedXmlPullParser2;
                        i5 = i;
                        i15 = i9;
                        i7 = i10;
                        str = str3;
                        str2 = str4;
                      } else if (readAppWidgetProviderInfoLocked != null) {
                        readAppWidgetProviderInfoLocked.provider = providerId.componentName;
                        readAppWidgetProviderInfoLocked.providerInfo = activityInfo;
                        lookupProviderLocked.setInfoLocked(readAppWidgetProviderInfoLocked);
                      }
                    } else {
                      typedXmlPullParser2 = resolvePullParser;
                      if (CoreRune.EM_SUPPORTED && lookupProviderLocked == null && z) {
                        try {
                          provider = new Provider();
                          try {
                            AppWidgetProviderInfo appWidgetProviderInfo2 =
                                new AppWidgetProviderInfo();
                            provider.info = appWidgetProviderInfo2;
                            appWidgetProviderInfo2.provider = providerId.componentName;
                            appWidgetProviderInfo2.providerInfo = providerInfo;
                            provider.zombie = false;
                            provider.f1641id = providerId;
                            this.mProviders.add(provider);
                            Elog.d(
                                "AppWidgetServiceImpl",
                                "readProfileStateFromFileLocked "
                                    + provider.f1641id
                                    + "/"
                                    + canonicalPackageName);
                            lookupProviderLocked = provider;
                          } catch (Exception e4) {
                            exc = e4;
                            lookupProviderLocked = provider;
                            Elog.d(
                                "AppWidgetServiceImpl",
                                "readProfileStateFromFileLocked e : " + exc);
                            exc.printStackTrace();
                            if (lookupProviderLocked == null) {}
                          }
                        } catch (Exception e5) {
                          exc = e5;
                        }
                      }
                    }
                    if (lookupProviderLocked == null) {}
                  }
                }
                str4 = str5;
                str3 = str6;
                typedXmlPullParser = resolvePullParser;
                z = isEmergencyMode;
                i2 = i14;
                i9 = i15;
                i10 = next;
                i5 = i;
                i15 = i9;
                i7 = i10;
                str = str3;
                str2 = str4;
              } catch (IOException
                  | IndexOutOfBoundsException
                  | NullPointerException
                  | NumberFormatException
                  | XmlPullParserException e6) {
                e = e6;
                obj = e;
                Slog.w("AppWidgetServiceImpl", "failed parsing " + obj);
                AppWidgetLogWrapper.m2i(
                    "AppWidgetServiceImpl", "Failed to read appwidgets.xml: " + obj);
                file = new File("/data/log/dump_appwidgets.xml");
                if (file.exists()) {
                  try {
                    file.delete();
                  } catch (Exception e7) {
                    e7.printStackTrace();
                  }
                }
                file.createNewFile();
                FileOutputStream fileOutputStream2 =
                    new FileOutputStream("/data/log/dump_appwidgets.xml");
                Files.copy(getStateFile(i).toPath(), fileOutputStream2);
                fileOutputStream2.close();
                return -1;
              }
            } else {
              String str7 = str6;
              typedXmlPullParser = resolvePullParser;
              z = isEmergencyMode;
              i2 = i14;
              i3 = i15;
              i4 = next;
              str2 = str5;
              if (str2.equals(name)) {
                int i16 = i2 + 1;
                Host host = new Host();
                String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "pkg");
                i5 = i;
                try {
                  int uidForPackage2 = getUidForPackage(attributeValue3, i5);
                  if (uidForPackage2 < 0) {
                    if (CoreRune.EM_SUPPORTED) {
                      if (!z && !SemEmergencyManager.getInstance(this.mContext).isModifying()) {
                        host.zombie = true;
                      }
                    } else {
                      host.zombie = true;
                    }
                  }
                  if (!host.zombie || this.mSafeMode) {
                    int attributeIntHex =
                        typedXmlPullParser.getAttributeIntHex((String) null, "id");
                    host.tag = typedXmlPullParser.getAttributeIntHex((String) null, "tag", i16);
                    host.f1640id = new HostId(uidForPackage2, attributeIntHex, attributeValue3);
                    this.mHosts.add(host);
                  }
                  i2 = i16;
                  i15 = i3;
                  i7 = i4;
                  str = str7;
                } catch (IOException
                    | IndexOutOfBoundsException
                    | NullPointerException
                    | NumberFormatException
                    | XmlPullParserException e8) {
                  e = e8;
                  obj = e;
                  Slog.w("AppWidgetServiceImpl", "failed parsing " + obj);
                  AppWidgetLogWrapper.m2i(
                      "AppWidgetServiceImpl", "Failed to read appwidgets.xml: " + obj);
                  file = new File("/data/log/dump_appwidgets.xml");
                  if (file.exists()) {}
                  file.createNewFile();
                  FileOutputStream fileOutputStream22 =
                      new FileOutputStream("/data/log/dump_appwidgets.xml");
                  Files.copy(getStateFile(i).toPath(), fileOutputStream22);
                  fileOutputStream22.close();
                  return -1;
                }
              } else {
                i5 = i;
                if ("b".equals(name)) {
                  String attributeValue4 =
                      typedXmlPullParser.getAttributeValue((String) null, "packageName");
                  if (getUidForPackage(attributeValue4, i5) >= 0) {
                    this.mPackagesWithBindWidgetPermission.add(
                        Pair.create(Integer.valueOf(i), attributeValue4));
                  }
                } else if ("g".equals(name)) {
                  Widget widget = new Widget();
                  int attributeIntHex2 = typedXmlPullParser.getAttributeIntHex((String) null, "id");
                  widget.appWidgetId = attributeIntHex2;
                  setMinAppWidgetIdLocked(i5, attributeIntHex2 + 1);
                  i6 = 0;
                  widget.restoredId =
                      typedXmlPullParser.getAttributeIntHex((String) null, "rid", 0);
                  widget.options = parseWidgetIdOptions(typedXmlPullParser);
                  str = str7;
                  list.add(
                      new LoadedWidgetState(
                          widget,
                          typedXmlPullParser.getAttributeIntHex((String) null, str2),
                          typedXmlPullParser.getAttributeValue((String) null, str) != null
                              ? typedXmlPullParser.getAttributeIntHex((String) null, str)
                              : -1));
                }
                str = str7;
                i6 = 0;
              }
            }
            i6 = 0;
            i8 = 1;
            if (i7 == i8) {}
          }
        } else {
          z = isEmergencyMode;
          i2 = i14;
          i3 = i15;
          i4 = next;
          i5 = i11;
          str = str6;
          typedXmlPullParser = resolvePullParser;
          i6 = i12;
          str2 = str5;
        }
        i15 = i3;
        i7 = i4;
        i8 = 1;
        if (i7 == i8) {}
      }
    } catch (IOException
        | IndexOutOfBoundsException
        | NullPointerException
        | NumberFormatException
        | XmlPullParserException e9) {
      e = e9;
    }
  }

  public final void performUpgradeLocked(int i) {
    int uidForPackage;
    if (i < 1) {
      Slog.v("AppWidgetServiceImpl", "Upgrading widget database from " + i + " to 1");
    }
    if (i == 0) {
      Host lookupHostLocked = lookupHostLocked(new HostId(Process.myUid(), 1262836039, "android"));
      if (lookupHostLocked != null
          && (uidForPackage = getUidForPackage("com.android.keyguard", 0)) >= 0) {
        lookupHostLocked.f1640id = new HostId(uidForPackage, 1262836039, "com.android.keyguard");
      }
      i = 1;
    }
    if (i != 1) {
      throw new IllegalStateException("Failed to upgrade widget database");
    }
  }

  public static File getStateFile(int i) {
    return new File(Environment.getUserSystemDirectory(i), "appwidgets.xml");
  }

  public static AtomicFile getSavedStateFile(int i) {
    File userSystemDirectory = Environment.getUserSystemDirectory(i);
    File stateFile = getStateFile(i);
    if (!stateFile.exists() && i == 0) {
      if (!userSystemDirectory.exists()) {
        userSystemDirectory.mkdirs();
      }
      new File("/data/system/appwidgets.xml").renameTo(stateFile);
    }
    return new AtomicFile(stateFile);
  }

  public void onUserStopped(int i) {
    synchronized (this.mLock) {
      int size = this.mWidgets.size() - 1;
      while (true) {
        boolean z = false;
        if (size < 0) {
          break;
        }
        Widget widget = (Widget) this.mWidgets.get(size);
        boolean z2 = widget.host.getUserId() == i;
        Provider provider = widget.provider;
        boolean z3 = provider != null;
        if (z3 && provider.getUserId() == i) {
          z = true;
        }
        if (z2 && (!z3 || z)) {
          removeWidgetLocked(widget);
          widget.host.widgets.remove(widget);
          widget.host = null;
          if (z3) {
            widget.provider.widgets.remove(widget);
            widget.provider = null;
          }
        }
        size--;
      }
      boolean z4 = false;
      for (int size2 = this.mHosts.size() - 1; size2 >= 0; size2--) {
        Host host = (Host) this.mHosts.get(size2);
        if (host.getUserId() == i) {
          z4 |= !host.widgets.isEmpty();
          deleteHostLocked(host);
        }
      }
      for (int size3 = this.mPackagesWithBindWidgetPermission.size() - 1; size3 >= 0; size3--) {
        if (((Integer) ((Pair) this.mPackagesWithBindWidgetPermission.valueAt(size3)).first)
                .intValue()
            == i) {
          this.mPackagesWithBindWidgetPermission.removeAt(size3);
        }
      }
      int indexOfKey = this.mLoadedUserIds.indexOfKey(i);
      if (indexOfKey >= 0) {
        this.mLoadedUserIds.removeAt(indexOfKey);
      }
      int indexOfKey2 = this.mNextAppWidgetIds.indexOfKey(i);
      if (indexOfKey2 >= 0) {
        this.mNextAppWidgetIds.removeAt(indexOfKey2);
      }
      if (z4) {
        saveGroupStateAsync(i);
      }
    }
  }

  public final void applyResourceOverlaysToWidgetsLocked(Set set, int i, boolean z) {
    ApplicationInfo applicationInfo;
    AppWidgetProviderInfo appWidgetProviderInfo;
    ActivityInfo activityInfo;
    ApplicationInfo applicationInfo2;
    int size = this.mProviders.size();
    for (int i2 = 0; i2 < size; i2++) {
      Provider provider = (Provider) this.mProviders.get(i2);
      if (provider == null) {
        Log.i(
            "AppWidgetServiceImpl",
            "applyResourceOverlaysToWidgetsLocked provider is null. i:" + i2 + " size:" + size);
      } else if (provider.getUserId() == i) {
        String packageName = provider.f1641id.componentName.getPackageName();
        if (z || set.contains(packageName)) {
          try {
            applicationInfo = this.mPackageManager.getApplicationInfo(packageName, 1024L, i);
          } catch (RemoteException e) {
            Slog.w(
                "AppWidgetServiceImpl",
                "Failed to retrieve app info for " + packageName + " userId=" + i,
                e);
            applicationInfo = null;
          }
          if (applicationInfo != null
              && (appWidgetProviderInfo = provider.info) != null
              && (activityInfo = appWidgetProviderInfo.providerInfo) != null
              && (applicationInfo2 = activityInfo.applicationInfo) != null
              && applicationInfo.sourceDir.equals(applicationInfo2.sourceDir)) {
            ApplicationInfo applicationInfo3 = new ApplicationInfo(applicationInfo2);
            String[] strArr = applicationInfo.overlayPaths;
            applicationInfo3.overlayPaths = strArr == null ? null : (String[]) strArr.clone();
            String[] strArr2 = applicationInfo.resourceDirs;
            applicationInfo3.resourceDirs = strArr2 != null ? (String[]) strArr2.clone() : null;
            provider.info.providerInfo.applicationInfo = applicationInfo3;
            int size2 = provider.widgets.size();
            for (int i3 = 0; i3 < size2; i3++) {
              Widget widget = (Widget) provider.widgets.get(i3);
              RemoteViews remoteViews = widget.views;
              if (remoteViews != null) {
                remoteViews.updateAppInfo(applicationInfo3);
              }
              RemoteViews remoteViews2 = widget.maskedViews;
              if (remoteViews2 != null) {
                remoteViews2.updateAppInfo(applicationInfo3);
              }
            }
          }
        }
      }
    }
  }

  public final boolean updateProvidersForPackageLocked(
      String str, int i, Set set, boolean z, boolean z2) {
    String str2;
    HashSet hashSet = new HashSet();
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
    intent.setPackage(str);
    List queryIntentReceivers = queryIntentReceivers(intent, i, z2);
    int size = queryIntentReceivers == null ? 0 : queryIntentReceivers.size();
    int i2 = 0;
    boolean z3 = false;
    while (true) {
      if (i2 >= size) {
        break;
      }
      ResolveInfo resolveInfo = (ResolveInfo) queryIntentReceivers.get(i2);
      ActivityInfo activityInfo = resolveInfo.activityInfo;
      if ((activityInfo.applicationInfo.flags & 262144) == 0
          && str.equals(activityInfo.packageName)) {
        ProviderId providerId =
            new ProviderId(
                activityInfo.applicationInfo.uid,
                new ComponentName(activityInfo.packageName, activityInfo.name));
        Provider lookupProviderLocked = lookupProviderLocked(providerId);
        if (lookupProviderLocked == null) {
          if (addProviderLocked(resolveInfo)) {
            hashSet.add(providerId);
            z3 = true;
            i2++;
          }
        } else {
          AppWidgetProviderInfo createPartialProviderInfo =
              createPartialProviderInfo(providerId, resolveInfo, lookupProviderLocked);
          if (createPartialProviderInfo != null) {
            hashSet.add(providerId);
            lookupProviderLocked.setPartialInfoLocked(createPartialProviderInfo);
            int size2 = lookupProviderLocked.widgets.size();
            if (size2 > 0) {
              int[] widgetIds = getWidgetIds(lookupProviderLocked.widgets);
              registerForBroadcastsLocked(lookupProviderLocked, widgetIds);
              for (int i3 = 0; i3 < size2; i3++) {
                Widget widget = (Widget) lookupProviderLocked.widgets.get(i3);
                widget.views = null;
                scheduleNotifyProviderChangedLocked(widget);
              }
              sendUpdateIntentLocked(lookupProviderLocked, widgetIds, false, z);
              z3 = true;
              i2++;
            }
          }
          z3 = true;
          i2++;
        }
      }
      i2++;
    }
    int size3 = this.mProviders.size();
    for (int i4 = size3 - 1; i4 >= 0; i4--) {
      Provider provider = (Provider) this.mProviders.get(i4);
      if (provider == null) {
        if (i4 > 0) {
          int i5 = i4 - 1;
          if (this.mProviders.get(i5) != null) {
            str2 = ((Provider) this.mProviders.get(i5)).f1641id.componentName.toString();
            Log.e(
                "AppWidgetServiceImpl",
                "updateProvidersForPackageLocked provider is null size:"
                    + size3
                    + " index:"
                    + i4
                    + " prevComp:"
                    + str2);
            String callers = Debug.getCallers(5);
            StringBuilder sb = new StringBuilder();
            sb.append("caller:");
            sb.append(callers);
            Log.e("AppWidgetServiceImpl", sb.toString());
          }
        }
        str2 = null;
        Log.e(
            "AppWidgetServiceImpl",
            "updateProvidersForPackageLocked provider is null size:"
                + size3
                + " index:"
                + i4
                + " prevComp:"
                + str2);
        String callers2 = Debug.getCallers(5);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("caller:");
        sb2.append(callers2);
        Log.e("AppWidgetServiceImpl", sb2.toString());
      } else if (str.equals(provider.f1641id.componentName.getPackageName())
          && provider.getUserId() == i
          && !hashSet.contains(provider.f1641id)) {
        if (set != null) {
          set.add(provider.f1641id);
        }
        deleteProviderLocked(provider);
        z3 = true;
      }
    }
    return z3;
  }

  public final void removeWidgetsForPackageLocked(String str, int i, int i2) {
    int size = this.mProviders.size();
    for (int i3 = 0; i3 < size; i3++) {
      Provider provider = (Provider) this.mProviders.get(i3);
      if (provider == null) {
        Log.i(
            "AppWidgetServiceImpl",
            "removeWidgetsForPackageLocked provider is null. i:"
                + i3
                + " size:"
                + size
                + "pkg: "
                + str);
      } else if (str.equals(provider.f1641id.componentName.getPackageName())
          && provider.getUserId() == i
          && provider.widgets.size() > 0) {
        deleteWidgetsLocked(provider, i2);
      }
    }
  }

  public final boolean removeProvidersForPackageLocked(String str, int i) {
    int size = this.mProviders.size();
    boolean z = false;
    for (int i2 = size - 1; i2 >= 0; i2--) {
      Provider provider = (Provider) this.mProviders.get(i2);
      if (provider == null) {
        Log.i(
            "AppWidgetServiceImpl",
            "removeProvidersForPackageLocked provider is null. i:"
                + i2
                + " size:"
                + size
                + "pkg: "
                + str);
      } else if (str.equals(provider.f1641id.componentName.getPackageName())
          && provider.getUserId() == i) {
        deleteProviderLocked(provider);
        z = true;
      }
    }
    return z;
  }

  public final boolean removeHostsAndProvidersForPackageLocked(String str, int i) {
    boolean removeProvidersForPackageLocked = removeProvidersForPackageLocked(str, i);
    for (int size = this.mHosts.size() - 1; size >= 0; size--) {
      Host host = (Host) this.mHosts.get(size);
      if (str.equals(host.f1640id.packageName) && host.getUserId() == i) {
        deleteHostLocked(host);
        removeProvidersForPackageLocked = true;
      }
    }
    return removeProvidersForPackageLocked;
  }

  public final String getCanonicalPackageName(String str, String str2, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      AppGlobals.getPackageManager().getReceiverInfo(new ComponentName(str, str2), 0L, i);
      return str;
    } catch (RemoteException unused) {
      String[] currentToCanonicalPackageNames =
          this.mContext.getPackageManager().currentToCanonicalPackageNames(new String[] {str});
      if (currentToCanonicalPackageNames != null && currentToCanonicalPackageNames.length > 0) {
        return currentToCanonicalPackageNames[0];
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return null;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void sendBroadcastAsUser(Intent intent, UserHandle userHandle, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mContext.sendBroadcastAsUser(
          intent, userHandle, null, z ? this.mInteractiveBroadcast : null);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void bindService(
      Intent intent, ServiceConnection serviceConnection, UserHandle userHandle) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mContext.bindServiceAsUser(intent, serviceConnection, 33554433, userHandle);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void onCrossProfileWidgetProvidersChanged(int i, List list) {
    int profileParent = this.mSecurityPolicy.getProfileParent(i);
    if (profileParent != i) {
      synchronized (this.mLock) {
        ArraySet arraySet = new ArraySet();
        int size = this.mProviders.size();
        for (int i2 = 0; i2 < size; i2++) {
          Provider provider = (Provider) this.mProviders.get(i2);
          if (provider == null) {
            Log.i(
                "AppWidgetServiceImpl",
                "onCrossProfileWidgetProvidersChanged provider is null. i:" + i2 + " size:" + size);
          } else if (provider.getUserId() == i) {
            arraySet.add(provider.f1641id.componentName.getPackageName());
          }
        }
        int size2 = list.size();
        boolean isEmergencyMode =
            CoreRune.EM_SUPPORTED ? SemEmergencyManager.isEmergencyMode(this.mContext) : false;
        boolean z = false;
        for (int i3 = 0; i3 < size2; i3++) {
          String str = (String) list.get(i3);
          arraySet.remove(str);
          z |= updateProvidersForPackageLocked(str, i, null, false, isEmergencyMode);
        }
        int size3 = arraySet.size();
        for (int i4 = 0; i4 < size3; i4++) {
          removeWidgetsForPackageLocked((String) arraySet.valueAt(i4), i, profileParent);
        }
        if (z || size3 > 0) {
          saveGroupStateAsync(i);
          scheduleNotifyGroupHostsForProvidersChangedLocked(i);
        }
      }
    }
  }

  public final boolean isProfileWithLockedParent(int i) {
    UserInfo profileParent;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      UserInfo userInfo = this.mUserManager.getUserInfo(i);
      if (userInfo != null
          && userInfo.isProfile()
          && (profileParent = this.mUserManager.getProfileParent(i)) != null) {
        if (!isUserRunningAndUnlocked(profileParent.getUserHandle().getIdentifier())) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return true;
        }
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean isProfileWithUnlockedParent(int i) {
    UserInfo profileParent;
    UserInfo userInfo = this.mUserManager.getUserInfo(i);
    return userInfo != null
        && userInfo.isProfile()
        && (profileParent = this.mUserManager.getProfileParent(i)) != null
        && this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle());
  }

  public void noteAppWidgetTapped(String str, int i) {
    this.mSecurityPolicy.enforceCallFromPackage(str);
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (this.mActivityManagerInternal.getUidProcessState(callingUid) > 2) {
        return;
      }
      synchronized (this.mLock) {
        Widget lookupWidgetLocked = lookupWidgetLocked(i, callingUid, str);
        if (lookupWidgetLocked == null) {
          return;
        }
        ProviderId providerId = lookupWidgetLocked.provider.f1641id;
        String packageName = providerId.componentName.getPackageName();
        if (packageName == null) {
          return;
        }
        SparseArray sparseArray = new SparseArray();
        sparseArray.put(providerId.uid, packageName);
        this.mAppOpsManagerInternal.updateAppWidgetVisibility(sparseArray, true);
        this.mUsageStatsManagerInternal.reportEvent(
            packageName, UserHandle.getUserId(providerId.uid), 7);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final class CallbackHandler extends Handler {
    public CallbackHandler(Looper looper) {
      super(looper, null, false);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        SomeArgs someArgs = (SomeArgs) message.obj;
        Host host = (Host) someArgs.arg1;
        IAppWidgetHost iAppWidgetHost = (IAppWidgetHost) someArgs.arg2;
        RemoteViews remoteViews = (RemoteViews) someArgs.arg3;
        long longValue = ((Long) someArgs.arg4).longValue();
        int i2 = someArgs.argi1;
        someArgs.recycle();
        AppWidgetServiceImpl.this.handleNotifyUpdateAppWidget(
            host, iAppWidgetHost, i2, remoteViews, longValue);
        return;
      }
      if (i == 2) {
        SomeArgs someArgs2 = (SomeArgs) message.obj;
        Host host2 = (Host) someArgs2.arg1;
        IAppWidgetHost iAppWidgetHost2 = (IAppWidgetHost) someArgs2.arg2;
        AppWidgetProviderInfo appWidgetProviderInfo = (AppWidgetProviderInfo) someArgs2.arg3;
        long longValue2 = ((Long) someArgs2.arg4).longValue();
        int i3 = someArgs2.argi1;
        someArgs2.recycle();
        AppWidgetServiceImpl.this.handleNotifyProviderChanged(
            host2, iAppWidgetHost2, i3, appWidgetProviderInfo, longValue2);
        return;
      }
      if (i == 3) {
        SomeArgs someArgs3 = (SomeArgs) message.obj;
        Host host3 = (Host) someArgs3.arg1;
        IAppWidgetHost iAppWidgetHost3 = (IAppWidgetHost) someArgs3.arg2;
        someArgs3.recycle();
        AppWidgetServiceImpl.this.handleNotifyProvidersChanged(host3, iAppWidgetHost3);
        return;
      }
      if (i == 4) {
        SomeArgs someArgs4 = (SomeArgs) message.obj;
        Host host4 = (Host) someArgs4.arg1;
        IAppWidgetHost iAppWidgetHost4 = (IAppWidgetHost) someArgs4.arg2;
        long longValue3 = ((Long) someArgs4.arg3).longValue();
        int i4 = someArgs4.argi1;
        int i5 = someArgs4.argi2;
        someArgs4.recycle();
        AppWidgetServiceImpl.this.handleNotifyAppWidgetViewDataChanged(
            host4, iAppWidgetHost4, i4, i5, longValue3);
        return;
      }
      if (i != 5) {
        return;
      }
      SomeArgs someArgs5 = (SomeArgs) message.obj;
      Host host5 = (Host) someArgs5.arg1;
      IAppWidgetHost iAppWidgetHost5 = (IAppWidgetHost) someArgs5.arg2;
      long longValue4 = ((Long) someArgs5.arg3).longValue();
      int i6 = someArgs5.argi1;
      someArgs5.recycle();
      AppWidgetServiceImpl.this.handleNotifyAppWidgetRemoved(
          host5, iAppWidgetHost5, i6, longValue4);
    }
  }

  public final class SecurityPolicy {
    public SecurityPolicy() {}

    public boolean isEnabledGroupProfile(int i) {
      return isParentOrProfile(UserHandle.getCallingUserId(), i) && isProfileEnabled(i);
    }

    public int[] getEnabledGroupProfileIds(int i) {
      int groupParent = getGroupParent(i);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return AppWidgetServiceImpl.this.mUserManager.getEnabledProfileIds(groupParent);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void enforceServiceExistsAndRequiresBindRemoteViewsPermission(
        ComponentName componentName, int i) {
      ServiceInfo serviceInfo;
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        serviceInfo =
            AppWidgetServiceImpl.this.mPackageManager.getServiceInfo(componentName, 4096L, i);
      } catch (RemoteException unused) {
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
      if (serviceInfo == null) {
        throw new SecurityException("Service " + componentName + " not installed for user " + i);
      }
      if (!"android.permission.BIND_REMOTEVIEWS".equals(serviceInfo.permission)) {
        throw new SecurityException(
            "Service "
                + componentName
                + " in user "
                + i
                + "does not require android.permission.BIND_REMOTEVIEWS");
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void enforceModifyAppWidgetBindPermissions(String str) {
      AppWidgetServiceImpl.this.mContext.enforceCallingPermission(
          "android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS",
          "hasBindAppWidgetPermission packageName=" + str);
    }

    public boolean isCallerInstantAppLocked() {
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        String[] packagesForUid =
            AppWidgetServiceImpl.this.mPackageManager.getPackagesForUid(callingUid);
        if (!ArrayUtils.isEmpty(packagesForUid)) {
          boolean isInstantApp =
              AppWidgetServiceImpl.this.mPackageManager.isInstantApp(
                  packagesForUid[0], UserHandle.getUserId(callingUid));
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return isInstantApp;
        }
      } catch (RemoteException unused) {
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    }

    public boolean isInstantAppLocked(String str, int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        boolean isInstantApp = AppWidgetServiceImpl.this.mPackageManager.isInstantApp(str, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isInstantApp;
      } catch (RemoteException unused) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }

    public void enforceCallFromPackage(String str) {
      AppWidgetServiceImpl.this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
    }

    public boolean hasCallerBindPermissionOrBindWhiteListedLocked(String str) {
      try {
        AppWidgetServiceImpl.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.BIND_APPWIDGET", null);
        return true;
      } catch (SecurityException unused) {
        return isCallerBindAppWidgetWhiteListedLocked(str);
      }
    }

    public final boolean isCallerBindAppWidgetWhiteListedLocked(String str) {
      int callingUserId = UserHandle.getCallingUserId();
      if (AppWidgetServiceImpl.this.getUidForPackage(str, callingUserId) < 0) {
        throw new IllegalArgumentException("No package " + str + " for user " + callingUserId);
      }
      synchronized (AppWidgetServiceImpl.this.mLock) {
        AppWidgetServiceImpl.this.ensureGroupStateLoadedLocked(callingUserId);
        return AppWidgetServiceImpl.this.mPackagesWithBindWidgetPermission.contains(
            Pair.create(Integer.valueOf(callingUserId), str));
      }
    }

    public boolean canAccessAppWidget(Widget widget, int i, String str) {
      Provider provider;
      if (isHostInPackageForUid(widget.host, i, str)
          || isProviderInPackageForUid(widget.provider, i, str)
          || isHostAccessingProvider(widget.host, widget.provider, i, str)) {
        return true;
      }
      int userId = UserHandle.getUserId(i);
      return (widget.host.getUserId() == userId
              || ((provider = widget.provider) != null && provider.getUserId() == userId))
          && AppWidgetServiceImpl.this.mContext.checkCallingPermission(
                  "android.permission.BIND_APPWIDGET")
              == 0;
    }

    public final boolean isParentOrProfile(int i, int i2) {
      return i == i2 || getProfileParent(i2) == i;
    }

    public boolean isProviderInCallerOrInProfileAndWhitelListed(String str, int i) {
      int callingUserId = UserHandle.getCallingUserId();
      if (i == callingUserId) {
        return true;
      }
      if (getProfileParent(i) != callingUserId) {
        return false;
      }
      return isProviderWhiteListed(str, i);
    }

    public boolean isProviderWhiteListed(String str, int i) {
      if (AppWidgetServiceImpl.this.mDevicePolicyManagerInternal == null) {
        return false;
      }
      if (SemDualAppManager.isDualAppId(i)) {
        return true;
      }
      return AppWidgetServiceImpl.this
          .mDevicePolicyManagerInternal
          .getCrossProfileWidgetProviders(i)
          .contains(str);
    }

    public int getProfileParent(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        UserInfo profileParent = AppWidgetServiceImpl.this.mUserManager.getProfileParent(i);
        if (profileParent != null) {
          return profileParent.getUserHandle().getIdentifier();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return -10;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getGroupParent(int i) {
      int profileParent = AppWidgetServiceImpl.this.mSecurityPolicy.getProfileParent(i);
      return profileParent != -10 ? profileParent : i;
    }

    public boolean isHostInPackageForUid(Host host, int i, String str) {
      HostId hostId = host.f1640id;
      return hostId.uid == i && hostId.packageName.equals(str);
    }

    public boolean isProviderInPackageForUid(Provider provider, int i, String str) {
      if (provider != null) {
        ProviderId providerId = provider.f1641id;
        if (providerId.uid == i && providerId.componentName.getPackageName().equals(str)) {
          return true;
        }
      }
      return false;
    }

    public boolean isHostAccessingProvider(Host host, Provider provider, int i, String str) {
      return host.f1640id.uid == i
          && provider != null
          && provider.f1641id.componentName.getPackageName().equals(str);
    }

    public final boolean isProfileEnabled(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        UserInfo userInfo = AppWidgetServiceImpl.this.mUserManager.getUserInfo(i);
        if (userInfo != null) {
          if (userInfo.isEnabled()) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
          }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
  }

  public final class Provider {
    public PendingIntent broadcast;

    /* renamed from: id */
    public ProviderId f1641id;
    public AppWidgetProviderInfo info;
    public String infoTag;
    public boolean mInfoParsed;
    public boolean maskedByLockedProfile;
    public boolean maskedByQuietProfile;
    public boolean maskedBySuperLocked;
    public boolean maskedBySuspendedPackage;
    public int tag;
    public ArrayList widgets;
    public boolean zombie;

    public Provider() {
      this.widgets = new ArrayList();
      this.mInfoParsed = false;
      this.tag = -1;
    }

    public int getUserId() {
      return UserHandle.getUserId(this.f1641id.uid);
    }

    public boolean isInPackageForUser(String str, int i) {
      return getUserId() == i && this.f1641id.componentName.getPackageName().equals(str);
    }

    public boolean hostedByPackageForUser(String str, int i) {
      int size = this.widgets.size();
      for (int i2 = 0; i2 < size; i2++) {
        Widget widget = (Widget) this.widgets.get(i2);
        if (str.equals(widget.host.f1640id.packageName) && widget.host.getUserId() == i) {
          return true;
        }
      }
      return false;
    }

    public AppWidgetProviderInfo getInfoLocked(Context context) {
      if (!this.mInfoParsed) {
        if (!this.zombie) {
          AppWidgetProviderInfo parseAppWidgetProviderInfo =
              !TextUtils.isEmpty(this.infoTag)
                  ? AppWidgetServiceImpl.parseAppWidgetProviderInfo(
                      context, this.f1641id, this.info.providerInfo, this.infoTag)
                  : null;
          if (parseAppWidgetProviderInfo == null) {
            parseAppWidgetProviderInfo =
                AppWidgetServiceImpl.parseAppWidgetProviderInfo(
                    context, this.f1641id, this.info.providerInfo, "android.appwidget.provider");
          }
          if (parseAppWidgetProviderInfo != null) {
            this.info = parseAppWidgetProviderInfo;
          }
        }
        this.mInfoParsed = true;
      }
      return this.info;
    }

    public AppWidgetProviderInfo getPartialInfoLocked() {
      return this.info;
    }

    public void setPartialInfoLocked(AppWidgetProviderInfo appWidgetProviderInfo) {
      this.info = appWidgetProviderInfo;
      this.mInfoParsed = false;
    }

    public void setInfoLocked(AppWidgetProviderInfo appWidgetProviderInfo) {
      this.info = appWidgetProviderInfo;
      this.mInfoParsed = true;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Provider{");
      sb.append(this.f1641id);
      sb.append(this.zombie ? " Z" : "");
      sb.append('}');
      return sb.toString();
    }

    public boolean setMaskedByQuietProfileLocked(boolean z) {
      boolean z2 = this.maskedByQuietProfile;
      this.maskedByQuietProfile = z;
      return z != z2;
    }

    public boolean setMaskedByLockedProfileLocked(boolean z) {
      boolean z2 = this.maskedByLockedProfile;
      this.maskedByLockedProfile = z;
      return z != z2;
    }

    public boolean setMaskedBySuspendedPackageLocked(boolean z) {
      boolean z2 = this.maskedBySuspendedPackage;
      this.maskedBySuspendedPackage = z;
      return z != z2;
    }

    public boolean setMaskedBySuperProfileLocked(boolean z) {
      boolean z2 = this.maskedBySuperLocked;
      this.maskedBySuperLocked = z;
      return z != z2;
    }

    public boolean isMaskedLocked() {
      return this.maskedByQuietProfile
          || this.maskedByLockedProfile
          || this.maskedBySuspendedPackage
          || this.maskedBySuperLocked;
    }

    public boolean shouldBePersisted() {
      return (this.widgets.isEmpty() && TextUtils.isEmpty(this.infoTag)) ? false : true;
    }
  }

  public final class ProviderId {
    public final ComponentName componentName;
    public final int uid;

    public ProviderId(int i, ComponentName componentName) {
      this.uid = i;
      this.componentName = componentName;
    }

    public UserHandle getProfile() {
      return UserHandle.getUserHandleForUid(this.uid);
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || ProviderId.class != obj.getClass()) {
        return false;
      }
      ProviderId providerId = (ProviderId) obj;
      if (this.uid != providerId.uid) {
        return false;
      }
      ComponentName componentName = this.componentName;
      if (componentName == null) {
        if (providerId.componentName != null) {
          return false;
        }
      } else if (!componentName.equals(providerId.componentName)) {
        return false;
      }
      return true;
    }

    public int hashCode() {
      int i = this.uid * 31;
      ComponentName componentName = this.componentName;
      return i + (componentName != null ? componentName.hashCode() : 0);
    }

    public String toString() {
      return "ProviderId{user:"
          + UserHandle.getUserId(this.uid)
          + ", app:"
          + UserHandle.getAppId(this.uid)
          + ", cmp:"
          + this.componentName
          + '}';
    }
  }

  public final class Host {
    public IAppWidgetHost callbacks;

    /* renamed from: id */
    public HostId f1640id;
    public long lastWidgetUpdateSequenceNo;
    public int tag;
    public ArrayList widgets;
    public boolean zombie;

    public Host() {
      this.widgets = new ArrayList();
      this.tag = -1;
    }

    public int getUserId() {
      return UserHandle.getUserId(this.f1640id.uid);
    }

    public boolean isInPackageForUser(String str, int i) {
      return getUserId() == i && this.f1640id.packageName.equals(str);
    }

    public final boolean hostsPackageForUser(String str, int i) {
      int size = this.widgets.size();
      for (int i2 = 0; i2 < size; i2++) {
        Provider provider = ((Widget) this.widgets.get(i2)).provider;
        if (provider != null
            && provider.getUserId() == i
            && str.equals(provider.f1641id.componentName.getPackageName())) {
          return true;
        }
      }
      return false;
    }

    public void getPendingUpdatesForIdLocked(
        Context context, int i, LongSparseArray longSparseArray) {
      PendingHostUpdate updateAppWidget;
      long j = this.lastWidgetUpdateSequenceNo;
      int size = this.widgets.size();
      for (int i2 = 0; i2 < size; i2++) {
        Widget widget = (Widget) this.widgets.get(i2);
        if (widget.appWidgetId == i) {
          for (int size2 = widget.updateSequenceNos.size() - 1; size2 >= 0; size2--) {
            long valueAt = widget.updateSequenceNos.valueAt(size2);
            if (valueAt > j) {
              int keyAt = widget.updateSequenceNos.keyAt(size2);
              if (keyAt == 0) {
                updateAppWidget =
                    PendingHostUpdate.updateAppWidget(
                        i,
                        AppWidgetServiceImpl.cloneIfLocalBinder(widget.getEffectiveViewsLocked()));
              } else if (keyAt == 1) {
                updateAppWidget =
                    PendingHostUpdate.providerChanged(i, widget.provider.getInfoLocked(context));
              } else {
                updateAppWidget = PendingHostUpdate.viewDataChanged(i, keyAt);
              }
              longSparseArray.put(valueAt, updateAppWidget);
            }
          }
          return;
        }
      }
      longSparseArray.put(this.lastWidgetUpdateSequenceNo, PendingHostUpdate.appWidgetRemoved(i));
    }

    public SparseArray getWidgetUidsIfBound() {
      SparseArray sparseArray = new SparseArray();
      for (int size = this.widgets.size() - 1; size >= 0; size--) {
        Widget widget = (Widget) this.widgets.get(size);
        Provider provider = widget.provider;
        if (provider == null) {
          Slog.d("AppWidgetServiceHost", "Widget with no provider " + widget.toString());
        } else {
          ProviderId providerId = provider.f1641id;
          sparseArray.put(providerId.uid, providerId.componentName.getPackageName());
        }
      }
      return sparseArray;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Host{");
      sb.append(this.f1640id);
      sb.append(this.zombie ? " Z" : "");
      sb.append('}');
      return sb.toString();
    }
  }

  public final class HostId {
    public final int hostId;
    public final String packageName;
    public final int uid;

    public HostId(int i, int i2, String str) {
      this.uid = i;
      this.hostId = i2;
      this.packageName = str;
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || HostId.class != obj.getClass()) {
        return false;
      }
      HostId hostId = (HostId) obj;
      if (this.uid != hostId.uid || this.hostId != hostId.hostId) {
        return false;
      }
      String str = this.packageName;
      if (str == null) {
        if (hostId.packageName != null) {
          return false;
        }
      } else if (!str.equals(hostId.packageName)) {
        return false;
      }
      return true;
    }

    public int hashCode() {
      int i = ((this.uid * 31) + this.hostId) * 31;
      String str = this.packageName;
      return i + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
      return "HostId{user:"
          + UserHandle.getUserId(this.uid)
          + ", app:"
          + UserHandle.getAppId(this.uid)
          + ", hostId:"
          + this.hostId
          + ", pkg:"
          + this.packageName
          + '}';
    }
  }

  public final class Widget {
    public int appWidgetId;
    public Host host;
    public RemoteViews maskedViews;
    public Bundle options;
    public Provider provider;
    public int restoredId;
    public boolean trackingUpdate;
    public String transactionError;
    public SparseLongArray updateSequenceNos;
    public RemoteViews views;

    public Widget() {
      this.transactionError = null;
      this.updateSequenceNos = new SparseLongArray(2);
      this.trackingUpdate = false;
    }

    public String toString() {
      return "AppWidgetId{" + this.appWidgetId + ':' + this.host + ':' + this.provider + '}';
    }

    public final boolean replaceWithMaskedViewsLocked(RemoteViews remoteViews) {
      this.maskedViews = remoteViews;
      this.transactionError = null;
      return true;
    }

    public final boolean clearMaskedViewsLocked() {
      if (this.maskedViews == null) {
        return false;
      }
      this.maskedViews = null;
      this.transactionError = null;
      return true;
    }

    public RemoteViews getEffectiveViewsLocked() {
      RemoteViews remoteViews = this.maskedViews;
      return remoteViews != null ? remoteViews : this.views;
    }
  }

  public class LoadedWidgetState {
    public final int hostTag;
    public final int providerTag;
    public final Widget widget;

    public LoadedWidgetState(Widget widget, int i, int i2) {
      this.widget = widget;
      this.hostTag = i;
      this.providerTag = i2;
    }
  }

  public final class SaveStateRunnable implements Runnable {
    public final int mUserId;

    public SaveStateRunnable(int i) {
      this.mUserId = i;
    }

    @Override // java.lang.Runnable
    public void run() {
      synchronized (AppWidgetServiceImpl.this.mLock) {
        AppWidgetServiceImpl.this.ensureGroupStateLoadedLocked(this.mUserId, false);
        AppWidgetServiceImpl.this.saveStateLocked(this.mUserId);
      }
    }
  }

  public final class AddProviderLockedRunnable implements Runnable {
    public final int mIndex;
    public final CountDownLatch mLatch;
    public final boolean[] mReceiversCheckArray;
    public final ResolveInfo mRi;

    public AddProviderLockedRunnable(
        CountDownLatch countDownLatch, ResolveInfo resolveInfo, int i, boolean[] zArr) {
      this.mLatch = countDownLatch;
      this.mRi = resolveInfo;
      this.mIndex = i;
      this.mReceiversCheckArray = zArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.util.concurrent.CountDownLatch] */
    @Override // java.lang.Runnable
    public void run() {
      try {
        try {
          AppWidgetServiceImpl.this.addProviderLocked(this.mRi);
          this.mReceiversCheckArray[this.mIndex] = true;
        } catch (Exception e) {
          Slog.e("AppWidgetServiceImpl", "addProviderLocked failed " + this.mRi);
          e.printStackTrace();
          this.mReceiversCheckArray[this.mIndex] = true;
        }
        this = this.mLatch;
        this.countDown();
      } catch (Throwable th) {
        this.mReceiversCheckArray[this.mIndex] = true;
        this.mLatch.countDown();
        throw th;
      }
    }
  }

  public final class BackupRestoreController {
    public boolean mHasSystemRestoreFinished;
    public final SparseArray mPrunedAppsPerUser;
    public final HashMap mUpdatesByHost;
    public final HashMap mUpdatesByProvider;

    public BackupRestoreController() {
      this.mPrunedAppsPerUser = new SparseArray();
      this.mUpdatesByProvider = new HashMap();
      this.mUpdatesByHost = new HashMap();
    }

    public List getWidgetParticipants(int i) {
      Slog.i("BackupRestoreController", "Getting widget participants for user: " + i);
      HashSet hashSet = new HashSet();
      synchronized (AppWidgetServiceImpl.this.mLock) {
        int size = AppWidgetServiceImpl.this.mWidgets.size();
        for (int i2 = 0; i2 < size; i2++) {
          Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i2);
          if (isProviderAndHostInUser(widget, i)) {
            hashSet.add(widget.host.f1640id.packageName);
            Provider provider = widget.provider;
            if (provider != null) {
              hashSet.add(provider.f1641id.componentName.getPackageName());
            }
          }
        }
      }
      return new ArrayList(hashSet);
    }

    public byte[] getWidgetState(String str, int i) {
      Slog.i("BackupRestoreController", "Getting widget state for user: " + i);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      synchronized (AppWidgetServiceImpl.this.mLock) {
        if (!packageNeedsWidgetBackupLocked(str, i)) {
          return null;
        }
        try {
          TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
          newFastSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
          newFastSerializer.startDocument((String) null, Boolean.TRUE);
          newFastSerializer.startTag((String) null, "ws");
          newFastSerializer.attributeInt((String) null, "version", 2);
          newFastSerializer.attribute((String) null, "pkg", str);
          int size = AppWidgetServiceImpl.this.mProviders.size();
          int i2 = 0;
          for (int i3 = 0; i3 < size; i3++) {
            Provider provider = (Provider) AppWidgetServiceImpl.this.mProviders.get(i3);
            if (provider == null) {
              Log.i(
                  "BackupRestoreController",
                  "getWidgetState provider is null. i:" + i3 + " size:" + size);
            } else if (provider.shouldBePersisted()
                && (provider.isInPackageForUser(str, i)
                    || provider.hostedByPackageForUser(str, i))) {
              provider.tag = i2;
              AppWidgetServiceImpl.serializeProvider(newFastSerializer, provider);
              i2++;
            }
          }
          int size2 = AppWidgetServiceImpl.this.mHosts.size();
          int i4 = 0;
          for (int i5 = 0; i5 < size2; i5++) {
            Host host = (Host) AppWidgetServiceImpl.this.mHosts.get(i5);
            if (!host.widgets.isEmpty()
                && (host.isInPackageForUser(str, i) || host.hostsPackageForUser(str, i))) {
              host.tag = i4;
              AppWidgetServiceImpl.serializeHost(newFastSerializer, host);
              i4++;
            }
          }
          int size3 = AppWidgetServiceImpl.this.mWidgets.size();
          for (int i6 = 0; i6 < size3; i6++) {
            Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i6);
            Provider provider2 = widget.provider;
            if (widget.host.isInPackageForUser(str, i)
                || (provider2 != null && provider2.isInPackageForUser(str, i))) {
              AppWidgetServiceImpl.serializeAppWidget(newFastSerializer, widget, false);
            }
          }
          newFastSerializer.endTag((String) null, "ws");
          newFastSerializer.endDocument();
          return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
          Slog.w("BackupRestoreController", "Unable to save widget state for " + str);
          return null;
        }
      }
    }

    public void systemRestoreStarting(int i) {
      Slog.i("BackupRestoreController", "System restore starting for user: " + i);
      synchronized (AppWidgetServiceImpl.this.mLock) {
        this.mHasSystemRestoreFinished = false;
        getPrunedAppsLocked(i).clear();
        this.mUpdatesByProvider.clear();
        this.mUpdatesByHost.clear();
      }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void restoreWidgetState(String str, byte[] bArr, int i) {
      ArrayList arrayList;
      ArrayList arrayList2;
      TypedXmlPullParser newFastPullParser;
      int next;
      Slog.i(
          "BackupRestoreController", "Restoring widget state for user:" + i + " package: " + str);
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
      try {
        try {
          arrayList = new ArrayList();
          arrayList2 = new ArrayList();
          newFastPullParser = Xml.newFastPullParser();
          newFastPullParser.setInput(byteArrayInputStream, StandardCharsets.UTF_8.name());
        } catch (IOException | XmlPullParserException unused) {
          Slog.w("BackupRestoreController", "Unable to restore widget state for " + str);
        }
        synchronized (AppWidgetServiceImpl.this.mLock) {
          do {
            next = newFastPullParser.next();
            if (next == 2) {
              String name = newFastPullParser.getName();
              Object[] objArr = 0;
              Object[] objArr2 = 0;
              if ("ws".equals(name)) {
                int attributeInt = newFastPullParser.getAttributeInt((String) null, "version");
                if (attributeInt > 2) {
                  Slog.w(
                      "BackupRestoreController", "Unable to process state version " + attributeInt);
                } else if (!str.equals(newFastPullParser.getAttributeValue((String) null, "pkg"))) {
                  Slog.w("BackupRestoreController", "Package mismatch in ws");
                }
                return;
              }
              int i2 = -1;
              if (KnoxAnalyticsDataConverter.PAYLOAD.equals(name)) {
                ComponentName componentName =
                    new ComponentName(
                        newFastPullParser.getAttributeValue((String) null, "pkg"),
                        newFastPullParser.getAttributeValue((String) null, "cl"));
                Provider findProviderLocked = findProviderLocked(componentName, i);
                if (findProviderLocked == null) {
                  AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
                  appWidgetProviderInfo.provider = componentName;
                  Provider provider = new Provider();
                  provider.f1641id = new ProviderId(i2, componentName);
                  provider.setPartialInfoLocked(appWidgetProviderInfo);
                  provider.zombie = true;
                  AppWidgetServiceImpl.this.mProviders.add(provider);
                  Log.i(
                      "BackupRestoreController",
                      "restoreWidgetState Provider is added "
                          + provider
                          + " at "
                          + AppWidgetServiceImpl.this.mProviders.size());
                  findProviderLocked = provider;
                }
                Slog.i("BackupRestoreController", "   provider " + findProviderLocked.f1641id);
                arrayList.add(findProviderLocked);
              } else if ("h".equals(name)) {
                String attributeValue = newFastPullParser.getAttributeValue((String) null, "pkg");
                Host lookupOrAddHostLocked =
                    AppWidgetServiceImpl.this.lookupOrAddHostLocked(
                        new HostId(
                            AppWidgetServiceImpl.this.getUidForPackage(attributeValue, i),
                            newFastPullParser.getAttributeIntHex((String) null, "id"),
                            attributeValue));
                arrayList2.add(lookupOrAddHostLocked);
                Slog.i(
                    "BackupRestoreController",
                    "   host[" + arrayList2.size() + "]: {" + lookupOrAddHostLocked.f1640id + "}");
              } else if ("g".equals(name)) {
                int attributeIntHex = newFastPullParser.getAttributeIntHex((String) null, "id");
                Host host =
                    (Host) arrayList2.get(newFastPullParser.getAttributeIntHex((String) null, "h"));
                int attributeIntHex2 =
                    newFastPullParser.getAttributeIntHex(
                        (String) null, KnoxAnalyticsDataConverter.PAYLOAD, -1);
                Provider provider2 =
                    attributeIntHex2 != -1 ? (Provider) arrayList.get(attributeIntHex2) : null;
                pruneWidgetStateLocked(host.f1640id.packageName, i);
                if (provider2 != null) {
                  pruneWidgetStateLocked(provider2.f1641id.componentName.getPackageName(), i);
                }
                Widget findRestoredWidgetLocked =
                    findRestoredWidgetLocked(attributeIntHex, host, provider2);
                if (findRestoredWidgetLocked == null) {
                  findRestoredWidgetLocked = new Widget();
                  findRestoredWidgetLocked.appWidgetId =
                      AppWidgetServiceImpl.this.incrementAndGetAppWidgetIdLocked(i);
                  findRestoredWidgetLocked.restoredId = attributeIntHex;
                  findRestoredWidgetLocked.options =
                      AppWidgetServiceImpl.parseWidgetIdOptions(newFastPullParser);
                  findRestoredWidgetLocked.host = host;
                  host.widgets.add(findRestoredWidgetLocked);
                  findRestoredWidgetLocked.provider = provider2;
                  if (provider2 != null) {
                    provider2.widgets.add(findRestoredWidgetLocked);
                  }
                  Slog.i(
                      "BackupRestoreController",
                      "New restored id " + attributeIntHex + " now " + findRestoredWidgetLocked);
                  AppWidgetServiceImpl.this.addWidgetLocked(findRestoredWidgetLocked);
                }
                Provider provider3 = findRestoredWidgetLocked.provider;
                if (provider3 != null && provider3.getPartialInfoLocked() != null) {
                  stashProviderRestoreUpdateLocked(
                      findRestoredWidgetLocked.provider,
                      attributeIntHex,
                      findRestoredWidgetLocked.appWidgetId);
                } else {
                  Slog.w(
                      "BackupRestoreController",
                      "Missing provider for restored widget " + findRestoredWidgetLocked);
                }
                stashHostRestoreUpdateLocked(
                    findRestoredWidgetLocked.host,
                    attributeIntHex,
                    findRestoredWidgetLocked.appWidgetId);
                Slog.i(
                    "BackupRestoreController",
                    "   instance: "
                        + attributeIntHex
                        + " -> "
                        + findRestoredWidgetLocked.appWidgetId
                        + " :: p="
                        + findRestoredWidgetLocked.provider);
              }
            }
          } while (next != 1);
        }
      } finally {
        AppWidgetServiceImpl.this.saveGroupStateAsync(i);
      }
    }

    public void systemRestoreFinished(int i) {
      Slog.i("BackupRestoreController", "systemRestoreFinished for " + i);
      synchronized (AppWidgetServiceImpl.this.mLock) {
        this.mHasSystemRestoreFinished = true;
        maybeSendWidgetRestoreBroadcastsLocked(i);
      }
    }

    public void widgetComponentsChanged(int i) {
      synchronized (AppWidgetServiceImpl.this.mLock) {
        if (this.mHasSystemRestoreFinished) {
          maybeSendWidgetRestoreBroadcastsLocked(i);
        }
      }
    }

    public final void maybeSendWidgetRestoreBroadcastsLocked(int i) {
      ArrayList arrayList;
      Slog.i("BackupRestoreController", "maybeSendWidgetRestoreBroadcasts for " + i);
      UserHandle userHandle = new UserHandle(i);
      Iterator it = this.mUpdatesByProvider.entrySet().iterator();
      while (true) {
        boolean z = true;
        if (!it.hasNext()) {
          break;
        }
        Map.Entry entry = (Map.Entry) it.next();
        Provider provider = (Provider) entry.getKey();
        if (!provider.zombie) {
          ArrayList arrayList2 = (ArrayList) entry.getValue();
          int countPendingUpdates = countPendingUpdates(arrayList2);
          Slog.i(
              "BackupRestoreController",
              "Provider " + provider + " pending: " + countPendingUpdates);
          if (countPendingUpdates > 0) {
            int[] iArr = new int[countPendingUpdates];
            int[] iArr2 = new int[countPendingUpdates];
            int size = arrayList2.size();
            int i2 = 0;
            int i3 = 0;
            while (i3 < size) {
              RestoreUpdateRecord restoreUpdateRecord = (RestoreUpdateRecord) arrayList2.get(i3);
              if (!restoreUpdateRecord.notified) {
                restoreUpdateRecord.notified = z;
                iArr[i2] = restoreUpdateRecord.oldId;
                iArr2[i2] = restoreUpdateRecord.newId;
                i2++;
                Slog.i(
                    "BackupRestoreController",
                    "   " + restoreUpdateRecord.oldId + " => " + restoreUpdateRecord.newId);
              }
              i3++;
              z = true;
            }
            sendWidgetRestoreBroadcastLocked(
                "android.appwidget.action.APPWIDGET_RESTORED",
                provider,
                null,
                iArr,
                iArr2,
                userHandle);
          }
        }
      }
      for (Map.Entry entry2 : this.mUpdatesByHost.entrySet()) {
        Host host = (Host) entry2.getKey();
        if (host.f1640id.uid != -1) {
          ArrayList arrayList3 = (ArrayList) entry2.getValue();
          int countPendingUpdates2 = countPendingUpdates(arrayList3);
          Slog.i("BackupRestoreController", "Host " + host + " pending: " + countPendingUpdates2);
          if (countPendingUpdates2 > 0) {
            int[] iArr3 = new int[countPendingUpdates2];
            int[] iArr4 = new int[countPendingUpdates2];
            int size2 = arrayList3.size();
            int i4 = 0;
            int i5 = 0;
            while (i4 < size2) {
              RestoreUpdateRecord restoreUpdateRecord2 = (RestoreUpdateRecord) arrayList3.get(i4);
              if (restoreUpdateRecord2.notified) {
                arrayList = arrayList3;
              } else {
                restoreUpdateRecord2.notified = true;
                iArr3[i5] = restoreUpdateRecord2.oldId;
                iArr4[i5] = restoreUpdateRecord2.newId;
                i5++;
                StringBuilder sb = new StringBuilder();
                sb.append("   ");
                arrayList = arrayList3;
                sb.append(restoreUpdateRecord2.oldId);
                sb.append(" => ");
                sb.append(restoreUpdateRecord2.newId);
                Slog.i("BackupRestoreController", sb.toString());
              }
              i4++;
              arrayList3 = arrayList;
            }
            sendWidgetRestoreBroadcastLocked(
                "android.appwidget.action.APPWIDGET_HOST_RESTORED",
                null,
                host,
                iArr3,
                iArr4,
                userHandle);
          }
        }
      }
    }

    public final Provider findProviderLocked(ComponentName componentName, int i) {
      int size = AppWidgetServiceImpl.this.mProviders.size();
      for (int i2 = 0; i2 < size; i2++) {
        Provider provider = (Provider) AppWidgetServiceImpl.this.mProviders.get(i2);
        if (provider == null) {
          Log.i(
              "BackupRestoreController",
              "findProviderLocked provider is null. i:" + i2 + " size:" + size);
        } else if (provider.getUserId() == i
            && provider.f1641id.componentName.equals(componentName)) {
          return provider;
        }
      }
      return null;
    }

    public final Widget findRestoredWidgetLocked(int i, Host host, Provider provider) {
      Slog.i(
          "BackupRestoreController",
          "Find restored widget: id=" + i + " host=" + host + " provider=" + provider);
      if (provider != null && host != null) {
        int size = AppWidgetServiceImpl.this.mWidgets.size();
        for (int i2 = 0; i2 < size; i2++) {
          Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i2);
          if (widget.restoredId == i
              && widget.host.f1640id.equals(host.f1640id)
              && widget.provider.f1641id.equals(provider.f1641id)) {
            Slog.i("BackupRestoreController", "   Found at " + i2 + " : " + widget);
            return widget;
          }
        }
      }
      return null;
    }

    public final boolean packageNeedsWidgetBackupLocked(String str, int i) {
      int size = AppWidgetServiceImpl.this.mWidgets.size();
      for (int i2 = 0; i2 < size; i2++) {
        Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i2);
        if (isProviderAndHostInUser(widget, i)) {
          if (widget.host.isInPackageForUser(str, i)) {
            return true;
          }
          Provider provider = widget.provider;
          if (provider != null && provider.isInPackageForUser(str, i)) {
            return true;
          }
        }
      }
      return false;
    }

    public final void stashProviderRestoreUpdateLocked(Provider provider, int i, int i2) {
      ArrayList arrayList = (ArrayList) this.mUpdatesByProvider.get(provider);
      if (arrayList == null) {
        arrayList = new ArrayList();
        this.mUpdatesByProvider.put(provider, arrayList);
      } else if (alreadyStashed(arrayList, i, i2)) {
        Slog.i(
            "BackupRestoreController",
            "ID remap " + i + " -> " + i2 + " already stashed for " + provider);
        return;
      }
      arrayList.add(new RestoreUpdateRecord(i, i2));
    }

    public final boolean alreadyStashed(ArrayList arrayList, int i, int i2) {
      int size = arrayList.size();
      for (int i3 = 0; i3 < size; i3++) {
        RestoreUpdateRecord restoreUpdateRecord = (RestoreUpdateRecord) arrayList.get(i3);
        if (restoreUpdateRecord.oldId == i && restoreUpdateRecord.newId == i2) {
          return true;
        }
      }
      return false;
    }

    public final void stashHostRestoreUpdateLocked(Host host, int i, int i2) {
      ArrayList arrayList = (ArrayList) this.mUpdatesByHost.get(host);
      if (arrayList == null) {
        arrayList = new ArrayList();
        this.mUpdatesByHost.put(host, arrayList);
      } else if (alreadyStashed(arrayList, i, i2)) {
        Slog.i(
            "BackupRestoreController",
            "ID remap " + i + " -> " + i2 + " already stashed for " + host);
        return;
      }
      arrayList.add(new RestoreUpdateRecord(i, i2));
    }

    public final void sendWidgetRestoreBroadcastLocked(
        String str, Provider provider, Host host, int[] iArr, int[] iArr2, UserHandle userHandle) {
      Intent intent = new Intent(str);
      intent.putExtra("appWidgetOldIds", iArr);
      intent.putExtra("appWidgetIds", iArr2);
      if (provider != null) {
        intent.setComponent(provider.f1641id.componentName);
        AppWidgetServiceImpl.this.sendBroadcastAsUser(intent, userHandle, true);
      }
      if (host != null) {
        intent.setComponent(null);
        intent.setPackage(host.f1640id.packageName);
        intent.putExtra("hostId", host.f1640id.hostId);
        AppWidgetServiceImpl.this.sendBroadcastAsUser(intent, userHandle, true);
      }
    }

    public final void pruneWidgetStateLocked(String str, int i) {
      Set prunedAppsLocked = getPrunedAppsLocked(i);
      if (!prunedAppsLocked.contains(str)) {
        Slog.i("BackupRestoreController", "pruning widget state for restoring package " + str);
        for (int size = AppWidgetServiceImpl.this.mWidgets.size() + (-1); size >= 0; size--) {
          Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(size);
          Host host = widget.host;
          Provider provider = widget.provider;
          if (host.hostsPackageForUser(str, i)
              || (provider != null && provider.isInPackageForUser(str, i))) {
            host.widgets.remove(widget);
            if (provider != null) {
              provider.widgets.remove(widget);
            }
            AppWidgetServiceImpl.this.decrementAppWidgetServiceRefCount(widget);
            AppWidgetServiceImpl.this.removeWidgetLocked(widget);
          }
        }
        prunedAppsLocked.add(str);
        return;
      }
      Slog.i("BackupRestoreController", "already pruned " + str + ", continuing normally");
    }

    public final Set getPrunedAppsLocked(int i) {
      if (!this.mPrunedAppsPerUser.contains(i)) {
        this.mPrunedAppsPerUser.set(i, new ArraySet());
      }
      return (Set) this.mPrunedAppsPerUser.get(i);
    }

    public final boolean isProviderAndHostInUser(Widget widget, int i) {
      Provider provider;
      return widget.host.getUserId() == i
          && ((provider = widget.provider) == null || provider.getUserId() == i);
    }

    public final int countPendingUpdates(ArrayList arrayList) {
      int size = arrayList.size();
      int i = 0;
      for (int i2 = 0; i2 < size; i2++) {
        if (!((RestoreUpdateRecord) arrayList.get(i2)).notified) {
          i++;
        }
      }
      return i;
    }

    public class RestoreUpdateRecord {
      public int newId;
      public boolean notified = false;
      public int oldId;

      public RestoreUpdateRecord(int i, int i2) {
        this.oldId = i;
        this.newId = i2;
      }
    }
  }

  public class AppWidgetManagerLocal extends AppWidgetManagerInternal {
    public AppWidgetManagerLocal() {}

    public ArraySet getHostedWidgetPackages(int i) {
      ArraySet arraySet;
      synchronized (AppWidgetServiceImpl.this.mLock) {
        int size = AppWidgetServiceImpl.this.mWidgets.size();
        arraySet = null;
        for (int i2 = 0; i2 < size; i2++) {
          Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i2);
          if (widget.host.f1640id.uid == i && widget.provider != null) {
            if (arraySet == null) {
              arraySet = new ArraySet();
            }
            arraySet.add(widget.provider.f1641id.componentName.getPackageName());
          }
        }
      }
      return arraySet;
    }

    public void unlockUser(int i) {
      AppWidgetServiceImpl.this.handleUserUnlocked(i);
    }

    public void applyResourceOverlaysToWidgets(Set set, int i, boolean z) {
      synchronized (AppWidgetServiceImpl.this.mLock) {
        AppWidgetServiceImpl.this.applyResourceOverlaysToWidgetsLocked(new HashSet(set), i, z);
      }
    }
  }

  public final String toTimestampFormat(String str) {
    Calendar calendar = Calendar.getInstance();
    return String.format(
        Locale.US,
        "[%02d-%02d %02d:%02d:%02d.%03d] %s",
        Integer.valueOf(calendar.get(2) + 1),
        Integer.valueOf(calendar.get(5)),
        Integer.valueOf(calendar.get(11)),
        Integer.valueOf(calendar.get(12)),
        Integer.valueOf(calendar.get(13)),
        Integer.valueOf(calendar.get(14)),
        str);
  }

  public final void updateHostHistoryLocked(String str) {
    if (this.mHostHistoryIdx < 7) {
      String timestampFormat = toTimestampFormat(str);
      String[] strArr = this.mHostHistory;
      int i = this.mHostHistoryIdx;
      this.mHostHistoryIdx = i + 1;
      strArr[i] = timestampFormat;
    }
    if (this.mHostHistoryIdx >= 7) {
      this.mHostHistoryIdx = 0;
    }
  }

  public final void updateProvidersHistoryLocked(String str) {
    if (this.mProvidersHistoryIndex < 100) {
      String timestampFormat = toTimestampFormat(str);
      String[] strArr = this.mProvidersHistory;
      int i = this.mProvidersHistoryIndex;
      this.mProvidersHistoryIndex = i + 1;
      strArr[i] = timestampFormat;
    }
    if (this.mProvidersHistoryIndex >= 100) {
      this.mProvidersHistoryIndex = 0;
    }
  }

  public final void updateAlarmHistoryLocked(String str) {
    if (this.mAlarmHistoryIndex < 7) {
      String timestampFormat = toTimestampFormat(str);
      String[] strArr = this.mAlarmHistory;
      int i = this.mAlarmHistoryIndex;
      this.mAlarmHistoryIndex = i + 1;
      strArr[i] = timestampFormat;
    }
    if (this.mAlarmHistoryIndex >= 7) {
      this.mAlarmHistoryIndex = 0;
    }
  }

  public final void writeLogToFile(String str) {
    boolean z;
    String timestampFormat = toTimestampFormat(str);
    if (!new File("/data/log").exists()) {
      return;
    }
    File file = new File("/data/log/appwidget.log");
    try {
      try {
        try {
          if (!file.exists()) {
            try {
              file.createNewFile();
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else if (file.length() > 1048576) {
            z = false;
            FileOutputStream fileOutputStream = new FileOutputStream(file, z);
            FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
            fastPrintWriter.println(timestampFormat);
            fastPrintWriter.flush();
            FileUtils.sync(fileOutputStream);
            fastPrintWriter.close();
            fileOutputStream.close();
            return;
          }
          fastPrintWriter.println(timestampFormat);
          fastPrintWriter.flush();
          FileUtils.sync(fileOutputStream);
          fastPrintWriter.close();
          fileOutputStream.close();
          return;
        } finally {
        }
        FastPrintWriter fastPrintWriter2 = new FastPrintWriter(fileOutputStream);
      } finally {
      }
      FileOutputStream fileOutputStream2 = new FileOutputStream(file, z);
    } catch (IOException e2) {
      e2.printStackTrace();
      return;
    }
    z = true;
  }

  public abstract class AppWidgetLogWrapper {
    public static FileHandler widget_fileHandler;
    public static Logger widget_logger;
    public static final SimpleDateFormat formatter =
        new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
    public static final Date date = new Date();

    static {
      try {
        FileHandler fileHandler =
            new FileHandler("/data/log/appwidget_history_log%g.txt", 10240, 2, true);
        widget_fileHandler = fileHandler;
        fileHandler.setFormatter(
            new Formatter() { // from class:
              // com.android.server.appwidget.AppWidgetServiceImpl.AppWidgetLogWrapper.1
              @Override // java.util.logging.Formatter
              public String format(LogRecord logRecord) {
                AppWidgetLogWrapper.date.setTime(System.currentTimeMillis());
                StringBuilder sb = new StringBuilder(80);
                sb.append(AppWidgetLogWrapper.formatter.format(AppWidgetLogWrapper.date));
                sb.append(logRecord.getMessage());
                return sb.toString();
              }
            });
        Logger logger = Logger.getLogger(AppWidgetLogWrapper.class.getName());
        widget_logger = logger;
        logger.addHandler(widget_fileHandler);
        widget_logger.setLevel(Level.ALL);
        widget_logger.setUseParentHandlers(false);
      } catch (Exception e) {
        Log.i("AppWidgetLogWrapper", "Can not use AppWidgetLogWrapper " + e.toString());
      }
    }

    /* renamed from: i */
    public static void m2i(String str, String str2) {
      Logger logger = widget_logger;
      if (logger != null) {
        logger.log(
            Level.INFO,
            String.format("V %s(%d): %s\n", str, Integer.valueOf(Binder.getCallingPid()), str2));
      }
      Log.i(str, str2);
    }

    public static StringBuilder getLogText() {
      File[] fileArr = new File[2];
      for (int i = 0; i < 2; i++) {
        fileArr[i] = new File("/data/log/appwidget_history_log" + i + ".txt");
      }
      StringBuilder sb = new StringBuilder();
      for (int i2 = 0; i2 < 2; i2++) {
        File file = fileArr[i2];
        if (file != null && file.exists()) {
          try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
              try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                  break;
                }
                sb.append(readLine);
                sb.append('\n');
              } finally {
              }
            }
            bufferedReader.close();
            sb.append('\n');
          } catch (IOException e) {
            Log.e("AppWidgetLogWrapper", "Can not use getWidgetLogText : " + e);
            return null;
          }
        }
      }
      return sb;
    }
  }

  public final Widget lookupWidgetLocked(int i, HostId hostId) {
    Host host;
    if (i >= 0 && hostId != null) {
      Iterator it = this.mWidgets.iterator();
      while (it.hasNext()) {
        Widget widget = (Widget) it.next();
        if (widget.appWidgetId == i
            && (host = widget.host) != null
            && hostId.equals(host.f1640id)) {
          return widget;
        }
      }
    }
    return null;
  }

  public final int getPidFromPackage(String str) {
    String str2;
    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
        this.mActivityManager.getRunningAppProcesses();
    Iterator<ActivityManager.RunningAppProcessInfo> it =
        runningAppProcesses == null ? null : runningAppProcesses.iterator();
    while (it != null && it.hasNext()) {
      ActivityManager.RunningAppProcessInfo next = it.next();
      if (next != null && (str2 = next.processName) != null && str2.equals(str)) {
        return next.pid;
      }
    }
    return 0;
  }

  public final int getFdFromPackage(int i, String str) {
    HashSet hashSet = (HashSet) this.mPackageToPidMap.get(str);
    if (hashSet == null) {
      hashSet = new HashSet();
    }
    if (i != 0) {
      hashSet.add(Integer.valueOf(i));
    }
    if (hashSet.size() > 0) {
      return getAshmemFdCount(new ArrayList(hashSet));
    }
    return 0;
  }

  public final int getAshmemFdCount(ArrayList arrayList) {
    StringBuilder sb;
    String readLine;
    long uptimeMillis = SystemClock.uptimeMillis();
    BufferedReader bufferedReader = null;
    BufferedReader bufferedReader2 = null;
    int i = 0;
    try {
      try {
        BufferedReader bufferedReader3 =
            new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/maps"));
        while (true) {
          try {
            readLine = bufferedReader3.readLine();
            if (readLine != null) {
              int indexOf = readLine.indexOf("dev/ashmem/Parcel Blob_");
              if (indexOf >= 0) {
                StringBuilder sb2 = new StringBuilder();
                int i2 = indexOf + 23;
                while (true) {
                  int i3 = i2 + 1;
                  char charAt = readLine.charAt(i2);
                  if (charAt < '0' || charAt > '9') {
                    break;
                  }
                  sb2.append(charAt);
                  i2 = i3;
                }
                if (sb2.length() > 0) {
                  int parseInt = Integer.parseInt(sb2.toString());
                  Iterator it = arrayList.iterator();
                  while (true) {
                    if (!it.hasNext()) {
                      break;
                    }
                    if (((Integer) it.next()).intValue() == parseInt) {
                      i++;
                      break;
                    }
                  }
                }
              }
            } else {
              try {
                break;
              } catch (IOException e) {
                e = e;
                sb = new StringBuilder();
                sb.append("exception to close buffer reader : ");
                sb.append(e.getMessage());
                Log.e("AppWidgetServiceImpl", sb.toString());
                Log.d(
                    "AppWidgetServiceImpl",
                    "getAshmemFdCount returning : pid = "
                        + arrayList
                        + ", fd count = "
                        + i
                        + " ("
                        + (SystemClock.uptimeMillis() - uptimeMillis)
                        + ")");
                return i;
              }
            }
          } catch (Exception e2) {
            e = e2;
            bufferedReader2 = bufferedReader3;
            Log.e(
                "AppWidgetServiceImpl",
                "exception to get process name by proc : " + e.getMessage());
            bufferedReader = bufferedReader2;
            if (bufferedReader2 != null) {
              try {
                bufferedReader2.close();
                bufferedReader = bufferedReader2;
              } catch (IOException e3) {
                e = e3;
                sb = new StringBuilder();
                sb.append("exception to close buffer reader : ");
                sb.append(e.getMessage());
                Log.e("AppWidgetServiceImpl", sb.toString());
                Log.d(
                    "AppWidgetServiceImpl",
                    "getAshmemFdCount returning : pid = "
                        + arrayList
                        + ", fd count = "
                        + i
                        + " ("
                        + (SystemClock.uptimeMillis() - uptimeMillis)
                        + ")");
                return i;
              }
            }
            Log.d(
                "AppWidgetServiceImpl",
                "getAshmemFdCount returning : pid = "
                    + arrayList
                    + ", fd count = "
                    + i
                    + " ("
                    + (SystemClock.uptimeMillis() - uptimeMillis)
                    + ")");
            return i;
          } catch (Throwable th) {
            th = th;
            bufferedReader = bufferedReader3;
            if (bufferedReader != null) {
              try {
                bufferedReader.close();
              } catch (IOException e4) {
                Log.e(
                    "AppWidgetServiceImpl",
                    "exception to close buffer reader : " + e4.getMessage());
              }
            }
            throw th;
          }
        }
        bufferedReader3.close();
        bufferedReader = readLine;
      } catch (Throwable th2) {
        th = th2;
      }
    } catch (Exception e5) {
      e = e5;
    }
    Log.d(
        "AppWidgetServiceImpl",
        "getAshmemFdCount returning : pid = "
            + arrayList
            + ", fd count = "
            + i
            + " ("
            + (SystemClock.uptimeMillis() - uptimeMillis)
            + ")");
    return i;
  }
}
