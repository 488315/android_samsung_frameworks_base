package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.IUidObserver;
import android.app.IUriGrantsManager;
import android.app.UidObserver;
import android.app.UriGrantsManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IShortcutService;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserPackage;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.text.format.TimeMigrationUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.IWindowManager;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.IParcelFileDescriptorFactory;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.StatLogger;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.uri.UriGrantsManagerInternal;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.PmLog;
import com.samsung.android.server.p025pm.ShortcutThread;
import com.samsung.android.server.p025pm.monetization.MonetizationUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import libcore.io.IoUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class ShortcutService extends IShortcutService.Stub {
  static final int DEFAULT_ICON_PERSIST_QUALITY = 100;
  static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;
  static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;
  static final int DEFAULT_MAX_SHORTCUTS_PER_ACTIVITY = 15;
  static final int DEFAULT_MAX_SHORTCUTS_PER_APP = 100;
  static final int DEFAULT_MAX_UPDATES_PER_INTERVAL = 10;
  static final long DEFAULT_RESET_INTERVAL_SEC = 86400;
  static final int DEFAULT_SAVE_DELAY_MS = 3000;
  static final String DIRECTORY_DUMP = "shortcut_dump";
  static final String DIRECTORY_PER_USER = "shortcut_service";
  static final String FILENAME_BASE_STATE = "shortcut_service.xml";
  static final String FILENAME_USER_PACKAGES = "shortcuts.xml";
  static final String FILENAME_USER_PACKAGES_RESERVE_COPY = "shortcuts.xml.reservecopy";
  public final ActivityManagerInternal mActivityManagerInternal;
  public final AtomicBoolean mBootCompleted;
  public ComponentName mChooserActivity;
  public final Context mContext;
  public List mDirtyUserIds;
  public final Handler mHandler;
  public final IPackageManager mIPackageManager;
  public Bitmap.CompressFormat mIconPersistFormat;
  public int mIconPersistQuality;
  public final boolean mIsAppSearchEnabled;
  public int mLastLockedUser;
  public Exception mLastWtfStacktrace;
  public final ArrayList mListeners;
  public final Object mLock;
  public int mMaxIconDimension;
  public int mMaxShortcuts;
  public int mMaxShortcutsPerApp;
  public int mMaxUpdatesPerInterval;
  public final MetricsLogger mMetricsLogger;
  public final Object mNonPersistentUsersLock;
  public final OnRoleHoldersChangedListener mOnRoleHoldersChangedListener;
  public final PackageManagerInternal mPackageManagerInternal;
  final BroadcastReceiver mPackageMonitor;
  public final AtomicLong mRawLastResetTime;
  public final BroadcastReceiver mReceiver;
  public long mResetInterval;
  public final RoleManager mRoleManager;
  public int mSaveDelayMillis;
  public final Runnable mSaveDirtyInfoRunner;
  public SettingChangeObserver mSettingChangeObserver;
  public final ArrayList mShortcutChangeCallbacks;
  public final ShortcutDumpFiles mShortcutDumpFiles;
  public final SparseArray mShortcutNonPersistentUsers;
  public final ShortcutRequestPinProcessor mShortcutRequestPinProcessor;
  public final AtomicBoolean mShutdown;
  public final BroadcastReceiver mShutdownReceiver;
  public AtomicBoolean mSmartSwitchBackupAllowed;
  public final StatLogger mStatLogger;
  public final SparseLongArray mUidLastForegroundElapsedTime;
  public final IUidObserver mUidObserver;
  public final SparseIntArray mUidState;
  public final SparseBooleanArray mUnlockedUsers;
  public final IUriGrantsManager mUriGrantsManager;
  public final UriGrantsManagerInternal mUriGrantsManagerInternal;
  public final IBinder mUriPermissionOwner;
  public final UsageStatsManagerInternal mUsageStatsManagerInternal;
  public final UserManagerInternal mUserManagerInternal;
  public final SparseArray mUsers;
  public int mWtfCount;
  public final Object mWtfLock;
  public AtomicBoolean reschedule;
  public int retryCount;
  public long startTime;
  static final String DEFAULT_ICON_PERSIST_FORMAT = Bitmap.CompressFormat.PNG.name();
  public static final AtomicBoolean sIsEmergencyMode = new AtomicBoolean();
  public static List EMPTY_RESOLVE_INFO = new ArrayList(0);
  public static Predicate ACTIVITY_NOT_EXPORTED =
      new Predicate() { // from class: com.android.server.pm.ShortcutService.1
        @Override // java.util.function.Predicate
        public boolean test(ResolveInfo resolveInfo) {
          return !resolveInfo.activityInfo.exported;
        }
      };
  public static Predicate ACTIVITY_NOT_INSTALLED = new Predicate() { // from class:
        // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda23
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
          boolean lambda$static$0;
          lambda$static$0 = ShortcutService.lambda$static$0((ResolveInfo) obj);
          return lambda$static$0;
        }
      };
  public static Predicate PACKAGE_NOT_INSTALLED =
      new Predicate() { // from class: com.android.server.pm.ShortcutService.2
        @Override // java.util.function.Predicate
        public boolean test(PackageInfo packageInfo) {
          return !ShortcutService.isInstalled(packageInfo);
        }
      };

  interface ConfigConstants {}

  interface Stats {}

  public static boolean isClockValid(long j) {
    return j >= 1420070400;
  }

  public boolean injectShouldPerformVerification() {
    return false;
  }

  public final boolean isProcessStateForeground(int i) {
    return i <= 5;
  }

  public class SettingChangeObserver extends ContentObserver {
    public Context mContext;

    public SettingChangeObserver(Context context) {
      super(new Handler());
      this.mContext = context;
      onChange(true);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      boolean z2 =
          Settings.System.getInt(this.mContext.getContentResolver(), "emergency_mode", 0) == 1;
      if (ShortcutService.sIsEmergencyMode.get() != z2) {
        Slog.d(
            "ShortcutService",
            "EmergencyMode changes: " + ShortcutService.sIsEmergencyMode.get() + " -> " + z2);
        ShortcutService.sIsEmergencyMode.set(z2);
      }
    }
  }

  public static /* synthetic */ boolean lambda$static$0(ResolveInfo resolveInfo) {
    return !isInstalled(resolveInfo.activityInfo);
  }

  class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String str, Throwable th) {
      super(str, th);
    }
  }

  public ShortcutService(Context context) {
    this(context, ShortcutThread.get().getLooper(), false);
  }

  public ShortcutService(Context context, Looper looper, boolean z) {
    this.mSmartSwitchBackupAllowed = new AtomicBoolean(false);
    Object obj = new Object();
    this.mLock = obj;
    this.mNonPersistentUsersLock = new Object();
    this.mWtfLock = new Object();
    this.mListeners = new ArrayList(1);
    this.mShortcutChangeCallbacks = new ArrayList(1);
    this.mRawLastResetTime = new AtomicLong(0L);
    this.mUsers = new SparseArray();
    this.mShortcutNonPersistentUsers = new SparseArray();
    this.mUidState = new SparseIntArray();
    this.mUidLastForegroundElapsedTime = new SparseLongArray();
    this.mDirtyUserIds = new ArrayList();
    this.mBootCompleted = new AtomicBoolean();
    this.mShutdown = new AtomicBoolean();
    this.mUnlockedUsers = new SparseBooleanArray();
    this.mStatLogger =
        new StatLogger(
            new String[] {
              "getHomeActivities()",
              "Launcher permission check",
              "getPackageInfo()",
              "getPackageInfo(SIG)",
              "getApplicationInfo",
              "cleanupDanglingBitmaps",
              "getActivity+metadata",
              "getInstalledPackages",
              "checkPackageChanges",
              "getApplicationResources",
              "resourceNameLookup",
              "getLauncherActivity",
              "checkLauncherActivity",
              "isActivityEnabled",
              "packageUpdateCheck",
              "asyncPreloadUserDelay",
              "getDefaultLauncher()"
            });
    this.mWtfCount = 0;
    this.mMetricsLogger = new MetricsLogger();
    this.mOnRoleHoldersChangedListener = new C21953();
    this.mUidObserver = new UidObserverC21964();
    this.startTime = 0L;
    this.reschedule = new AtomicBoolean(false);
    this.retryCount = 0;
    this.mSaveDirtyInfoRunner =
        new Runnable() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda17
          @Override // java.lang.Runnable
          public final void run() {
            ShortcutService.this.scheduleSaveDirtyInfo();
          }
        };
    this.mLastLockedUser = -1;
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.pm.ShortcutService.5
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if (ShortcutService.this.mBootCompleted.get()) {
              try {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                  ShortcutService.this.handleLocaleChanged();
                }
              } catch (Exception e) {
                ShortcutService.this.wtf("Exception in mReceiver.onReceive", e);
              }
            }
          }
        };
    this.mReceiver = broadcastReceiver;
    BroadcastReceiver broadcastReceiver2 =
        new BroadcastReceiver() { // from class: com.android.server.pm.ShortcutService.6
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            if (intExtra == -10000) {
              Slog.w("ShortcutService", "Intent broadcast does not contain user handle: " + intent);
              return;
            }
            String action = intent.getAction();
            long injectClearCallingIdentity = ShortcutService.this.injectClearCallingIdentity();
            try {
              try {
              } catch (Exception e) {
                ShortcutService.this.wtf("Exception in mPackageMonitor.onReceive", e);
              }
              synchronized (ShortcutService.this.mLock) {
                if (ShortcutService.this.isUserUnlockedL(intExtra)) {
                  Uri data = intent.getData();
                  String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                  if (schemeSpecificPart == null) {
                    Slog.w(
                        "ShortcutService",
                        "Intent broadcast does not contain package name: " + intent);
                    return;
                  }
                  char c = 0;
                  boolean booleanExtra =
                      intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                  switch (action.hashCode()) {
                    case 172491798:
                      if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        c = 2;
                        break;
                      }
                      c = 65535;
                      break;
                    case 267468725:
                      if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                        c = 3;
                        break;
                      }
                      c = 65535;
                      break;
                    case 525384130:
                      if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 1;
                        break;
                      }
                      c = 65535;
                      break;
                    case 1544582882:
                      if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                        break;
                      }
                      c = 65535;
                      break;
                    default:
                      c = 65535;
                      break;
                  }
                  if (c != 0) {
                    if (c != 1) {
                      if (c == 2) {
                        int intExtra2 = intent.getIntExtra("EM_PKG_HADNLER_ID", -1);
                        if (intExtra2 != -1) {
                          Slog.d(
                              "ShortcutService",
                              "Skip PACKAGE_CHANGED intent from Emergency mode: "
                                  + intExtra2
                                  + ", pkg: "
                                  + schemeSpecificPart);
                        } else {
                          ShortcutService.this.handlePackageChanged(schemeSpecificPart, intExtra);
                        }
                      } else if (c == 3) {
                        ShortcutService.this.handlePackageDataCleared(schemeSpecificPart, intExtra);
                      }
                    } else if (!booleanExtra) {
                      ShortcutService.this.handlePackageRemoved(schemeSpecificPart, intExtra);
                    }
                  } else if (booleanExtra) {
                    ShortcutService.this.handlePackageUpdateFinished(schemeSpecificPart, intExtra);
                  } else {
                    ShortcutService.this.handlePackageAdded(schemeSpecificPart, intExtra);
                  }
                }
              }
            } finally {
              ShortcutService.this.injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
          }
        };
    this.mPackageMonitor = broadcastReceiver2;
    BroadcastReceiver broadcastReceiver3 =
        new BroadcastReceiver() { // from class: com.android.server.pm.ShortcutService.7
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            synchronized (ShortcutService.this.mLock) {
              if (ShortcutService.this.mHandler.hasCallbacks(
                  ShortcutService.this.mSaveDirtyInfoRunner)) {
                ShortcutService.this.mHandler.removeCallbacks(
                    ShortcutService.this.mSaveDirtyInfoRunner);
                ShortcutService.this.forEachLoadedUserLocked(
                    new ShortcutService$7$$ExternalSyntheticLambda0());
                ShortcutService.this.saveDirtyInfo();
              }
              ShortcutService.this.mShutdown.set(true);
            }
          }
        };
    this.mShutdownReceiver = broadcastReceiver3;
    Objects.requireNonNull(context);
    this.mContext = context;
    LocalServices.addService(ShortcutServiceInternal.class, new LocalService());
    Handler handler = new Handler(looper);
    this.mHandler = handler;
    this.mIPackageManager = AppGlobals.getPackageManager();
    PackageManagerInternal packageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    Objects.requireNonNull(packageManagerInternal);
    this.mPackageManagerInternal = packageManagerInternal;
    UserManagerInternal userManagerInternal =
        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    Objects.requireNonNull(userManagerInternal);
    this.mUserManagerInternal = userManagerInternal;
    UsageStatsManagerInternal usageStatsManagerInternal =
        (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
    Objects.requireNonNull(usageStatsManagerInternal);
    this.mUsageStatsManagerInternal = usageStatsManagerInternal;
    ActivityManagerInternal activityManagerInternal =
        (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    Objects.requireNonNull(activityManagerInternal);
    this.mActivityManagerInternal = activityManagerInternal;
    IUriGrantsManager service = UriGrantsManager.getService();
    Objects.requireNonNull(service);
    this.mUriGrantsManager = service;
    UriGrantsManagerInternal uriGrantsManagerInternal =
        (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
    Objects.requireNonNull(uriGrantsManagerInternal);
    UriGrantsManagerInternal uriGrantsManagerInternal2 = uriGrantsManagerInternal;
    this.mUriGrantsManagerInternal = uriGrantsManagerInternal2;
    this.mUriPermissionOwner = uriGrantsManagerInternal2.newUriPermissionOwner("ShortcutService");
    RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
    Objects.requireNonNull(roleManager);
    this.mRoleManager = roleManager;
    this.mShortcutRequestPinProcessor = new ShortcutRequestPinProcessor(this, obj);
    this.mShortcutDumpFiles = new ShortcutDumpFiles(this);
    this.mIsAppSearchEnabled =
        DeviceConfig.getBoolean("systemui", "shortcut_appsearch_integration", true)
            && !injectIsLowRamDevice();
    if (z) {
      return;
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
    intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
    intentFilter.addDataScheme("package");
    intentFilter.setPriority(1000);
    context.registerReceiverAsUser(broadcastReceiver2, UserHandle.ALL, intentFilter, null, handler);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
    intentFilter2.setPriority(1000);
    context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter2, null, handler);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("android.intent.action.ACTION_SHUTDOWN");
    intentFilter3.setPriority(1000);
    context.registerReceiverAsUser(
        broadcastReceiver3, UserHandle.SYSTEM, intentFilter3, null, handler);
    try {
      this.mSettingChangeObserver = new SettingChangeObserver(context);
      context
          .getContentResolver()
          .registerContentObserver(
              Settings.System.getUriFor("emergency_mode"), false, this.mSettingChangeObserver);
    } catch (Exception unused) {
    }
    injectRegisterUidObserver(this.mUidObserver, 3);
    injectRegisterRoleHoldersListener(this.mOnRoleHoldersChangedListener);
  }

  public boolean isAppSearchEnabled() {
    return this.mIsAppSearchEnabled;
  }

  public long getStatStartTime() {
    return this.mStatLogger.getTime();
  }

  public void logDurationStat(int i, long j) {
    this.mStatLogger.logDurationStat(i, j);
  }

  public String injectGetLocaleTagsForUser(int i) {
    return LocaleList.getDefault().toLanguageTags();
  }

  /* renamed from: com.android.server.pm.ShortcutService$3 */
  public class C21953 implements OnRoleHoldersChangedListener {
    public C21953() {}

    public void onRoleHoldersChanged(String str, final UserHandle userHandle) {
      if ("android.app.role.HOME".equals(str)) {
        ShortcutService.this.injectPostToHandler(
            new Runnable() { // from class:
              // com.android.server.pm.ShortcutService$3$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                ShortcutService.C21953.this.lambda$onRoleHoldersChanged$0(userHandle);
              }
            });
      }
      PmLog.logRoleHoldersChanged(
          str, ShortcutService.this.mRoleManager.getRoleHoldersAsUser(str, userHandle), userHandle);
      if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
        try {
          if (ShortcutService.this.mIPackageManager.isFirstBoot()
              && "android.app.role.SMS".equals(str)) {
            MonetizationUtils.getInstance(ShortcutService.this.mContext).updateState();
          }
        } catch (RemoteException unused) {
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRoleHoldersChanged$0(UserHandle userHandle) {
      ShortcutService.this.handleOnDefaultLauncherChanged(userHandle.getIdentifier());
    }
  }

  public void handleOnDefaultLauncherChanged(int i) {
    this.mUriGrantsManagerInternal.revokeUriPermissionFromOwner(
        this.mUriPermissionOwner, null, -1, 0);
    synchronized (this.mLock) {
      if (isUserLoadedLocked(i)) {
        getUserShortcutsLocked(i).setCachedLauncher(null);
      }
    }
  }

  /* renamed from: com.android.server.pm.ShortcutService$4 */
  public class UidObserverC21964 extends UidObserver {
    public UidObserverC21964() {}

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUidStateChanged$0(int i, int i2) {
      ShortcutService.this.handleOnUidStateChanged(i, i2);
    }

    public void onUidStateChanged(final int i, final int i2, long j, int i3) {
      ShortcutService.this.injectPostToHandler(
          new Runnable() { // from class:
            // com.android.server.pm.ShortcutService$4$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              ShortcutService.UidObserverC21964.this.lambda$onUidStateChanged$0(i, i2);
            }
          });
    }

    public void onUidGone(final int i, boolean z) {
      ShortcutService.this.injectPostToHandler(
          new Runnable() { // from class:
            // com.android.server.pm.ShortcutService$4$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              ShortcutService.UidObserverC21964.this.lambda$onUidGone$1(i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUidGone$1(int i) {
      ShortcutService.this.handleOnUidStateChanged(i, 20);
    }
  }

  public void handleOnUidStateChanged(int i, int i2) {
    Trace.traceBegin(524288L, "shortcutHandleOnUidStateChanged");
    synchronized (this.mLock) {
      this.mUidState.put(i, i2);
      if (isProcessStateForeground(i2)) {
        this.mUidLastForegroundElapsedTime.put(i, injectElapsedRealtime());
      }
    }
    Trace.traceEnd(524288L);
  }

  public boolean isUidForegroundLocked(int i) {
    if (i == 1000 || isProcessStateForeground(this.mUidState.get(i, 20))) {
      return true;
    }
    return isProcessStateForeground(this.mActivityManagerInternal.getUidProcessState(i));
  }

  public long getUidLastForegroundElapsedTimeLocked(int i) {
    return this.mUidLastForegroundElapsedTime.get(i);
  }

  public final class Lifecycle extends SystemService {
    public final ShortcutService mService;

    public Lifecycle(Context context) {
      super(context);
      this.mService = new ShortcutService(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
      publishBinderService("shortcut", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
      this.mService.onBootPhase(i);
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
      this.mService.handleStopUser(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
      this.mService.handleUnlockUser(targetUser.getUserIdentifier());
    }
  }

  public void onBootPhase(int i) {
    if (i == 480) {
      initialize();
    } else {
      if (i != 1000) {
        return;
      }
      this.mBootCompleted.set(true);
    }
  }

  public void handleUnlockUser(final int i) {
    synchronized (this.mUnlockedUsers) {
      this.mUnlockedUsers.put(i, true);
    }
    final long statStartTime = getStatStartTime();
    injectRunOnNewThread(
        new Runnable() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda9
          @Override // java.lang.Runnable
          public final void run() {
            ShortcutService.this.lambda$handleUnlockUser$1(statStartTime, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleUnlockUser$1(long j, int i) {
    Trace.traceBegin(524288L, "shortcutHandleUnlockUser");
    synchronized (this.mLock) {
      logDurationStat(15, j);
      getUserShortcutsLocked(i);
    }
    Trace.traceEnd(524288L);
  }

  public void handleStopUser(int i) {
    Trace.traceBegin(524288L, "shortcutHandleStopUser");
    synchronized (this.mLock) {
      unloadUserLocked(i);
      synchronized (this.mUnlockedUsers) {
        this.mUnlockedUsers.put(i, false);
      }
    }
    Trace.traceEnd(524288L);
  }

  public final void unloadUserLocked(int i) {
    getUserShortcutsLocked(i).cancelAllInFlightTasks();
    saveDirtyInfo();
    this.mUsers.delete(i);
  }

  public final ResilientAtomicFile getBaseStateFile() {
    return new ResilientAtomicFile(
        new File(injectSystemDataPath(), FILENAME_BASE_STATE),
        new File(injectSystemDataPath(), "shortcut_service.xml.backup"),
        new File(injectSystemDataPath(), "shortcut_service.xml.reservecopy"),
        505,
        "base shortcut",
        null);
  }

  public final void initialize() {
    synchronized (this.mLock) {
      loadConfigurationLocked();
      loadBaseStateLocked();
    }
  }

  public final void loadConfigurationLocked() {
    updateConfigurationLocked(injectShortcutManagerConstants());
  }

  public boolean updateConfigurationLocked(String str) {
    boolean z;
    long j;
    KeyValueListParser keyValueListParser = new KeyValueListParser(',');
    try {
      keyValueListParser.setString(str);
      z = true;
    } catch (IllegalArgumentException e) {
      Slog.e("ShortcutService", "Bad shortcut manager settings", e);
      z = false;
    }
    this.mSaveDelayMillis = Math.max(0, (int) keyValueListParser.getLong("save_delay_ms", 3000L));
    this.mResetInterval =
        Math.max(
            1L,
            keyValueListParser.getLong("reset_interval_sec", DEFAULT_RESET_INTERVAL_SEC) * 1000);
    this.mMaxUpdatesPerInterval =
        Math.max(0, (int) keyValueListParser.getLong("max_updates_per_interval", 10L));
    this.mMaxShortcuts = Math.max(0, (int) keyValueListParser.getLong("max_shortcuts", 15L));
    this.mMaxShortcutsPerApp =
        Math.max(0, (int) keyValueListParser.getLong("max_shortcuts_per_app", 100L));
    if (injectIsLowRamDevice()) {
      j = keyValueListParser.getLong("max_icon_dimension_dp_lowram", 48L);
    } else {
      j = keyValueListParser.getLong("max_icon_dimension_dp", 96L);
    }
    this.mMaxIconDimension = injectDipToPixel(Math.max(1, (int) j));
    this.mIconPersistFormat =
        Bitmap.CompressFormat.valueOf(
            keyValueListParser.getString("icon_format", DEFAULT_ICON_PERSIST_FORMAT));
    this.mIconPersistQuality = (int) keyValueListParser.getLong("icon_quality", 100L);
    return z;
  }

  public String injectShortcutManagerConstants() {
    return Settings.Global.getString(
        this.mContext.getContentResolver(), "shortcut_manager_constants");
  }

  public int injectDipToPixel(int i) {
    return (int) TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics());
  }

  public static String parseStringAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
    return typedXmlPullParser.getAttributeValue((String) null, str);
  }

  public static boolean parseBooleanAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
    return parseLongAttribute(typedXmlPullParser, str) == 1;
  }

  public static boolean parseBooleanAttribute(
      TypedXmlPullParser typedXmlPullParser, String str, boolean z) {
    return parseLongAttribute(typedXmlPullParser, str, z ? 1L : 0L) == 1;
  }

  public static int parseIntAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
    return (int) parseLongAttribute(typedXmlPullParser, str);
  }

  public static int parseIntAttribute(TypedXmlPullParser typedXmlPullParser, String str, int i) {
    return (int) parseLongAttribute(typedXmlPullParser, str, i);
  }

  public static long parseLongAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
    return parseLongAttribute(typedXmlPullParser, str, 0L);
  }

  public static long parseLongAttribute(TypedXmlPullParser typedXmlPullParser, String str, long j) {
    String parseStringAttribute = parseStringAttribute(typedXmlPullParser, str);
    if (TextUtils.isEmpty(parseStringAttribute)) {
      return j;
    }
    try {
      return Long.parseLong(parseStringAttribute);
    } catch (NumberFormatException unused) {
      Slog.e("ShortcutService", "Error parsing long " + parseStringAttribute);
      return j;
    }
  }

  public static ComponentName parseComponentNameAttribute(
      TypedXmlPullParser typedXmlPullParser, String str) {
    String parseStringAttribute = parseStringAttribute(typedXmlPullParser, str);
    if (TextUtils.isEmpty(parseStringAttribute)) {
      return null;
    }
    return ComponentName.unflattenFromString(parseStringAttribute);
  }

  public static Intent parseIntentAttributeNoDefault(
      TypedXmlPullParser typedXmlPullParser, String str) {
    String parseStringAttribute = parseStringAttribute(typedXmlPullParser, str);
    if (!TextUtils.isEmpty(parseStringAttribute)) {
      try {
        return Intent.parseUri(parseStringAttribute, 0);
      } catch (URISyntaxException e) {
        Slog.e("ShortcutService", "Error parsing intent", e);
      }
    }
    return null;
  }

  public static Intent parseIntentAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
    Intent parseIntentAttributeNoDefault = parseIntentAttributeNoDefault(typedXmlPullParser, str);
    return parseIntentAttributeNoDefault == null
        ? new Intent("android.intent.action.VIEW")
        : parseIntentAttributeNoDefault;
  }

  public static void writeTagValue(TypedXmlSerializer typedXmlSerializer, String str, String str2) {
    if (TextUtils.isEmpty(str2)) {
      return;
    }
    typedXmlSerializer.startTag((String) null, str);
    typedXmlSerializer.attribute((String) null, "value", str2);
    typedXmlSerializer.endTag((String) null, str);
  }

  public static void writeTagValue(TypedXmlSerializer typedXmlSerializer, String str, long j) {
    writeTagValue(typedXmlSerializer, str, Long.toString(j));
  }

  public static void writeTagExtra(
      TypedXmlSerializer typedXmlSerializer, String str, PersistableBundle persistableBundle) {
    if (persistableBundle == null) {
      return;
    }
    typedXmlSerializer.startTag((String) null, str);
    persistableBundle.saveToXml(typedXmlSerializer);
    typedXmlSerializer.endTag((String) null, str);
  }

  public static void writeAttr(
      TypedXmlSerializer typedXmlSerializer, String str, CharSequence charSequence) {
    if (TextUtils.isEmpty(charSequence)) {
      return;
    }
    typedXmlSerializer.attribute((String) null, str, charSequence.toString());
  }

  public static void writeAttr(TypedXmlSerializer typedXmlSerializer, String str, long j) {
    writeAttr(typedXmlSerializer, str, String.valueOf(j));
  }

  public static void writeAttr(TypedXmlSerializer typedXmlSerializer, String str, boolean z) {
    if (z) {
      writeAttr(typedXmlSerializer, str, "1");
    } else {
      writeAttr(typedXmlSerializer, str, "0");
    }
  }

  public static void writeAttr(
      TypedXmlSerializer typedXmlSerializer, String str, ComponentName componentName) {
    if (componentName == null) {
      return;
    }
    writeAttr(typedXmlSerializer, str, componentName.flattenToString());
  }

  public static void writeAttr(TypedXmlSerializer typedXmlSerializer, String str, Intent intent) {
    if (intent == null) {
      return;
    }
    writeAttr(typedXmlSerializer, str, intent.toUri(0));
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:27:0x003a
  	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
  	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
  	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
  */
  public void saveBaseState() {
    /*
        r7 = this;
        com.android.server.pm.ResilientAtomicFile r0 = r7.getBaseStateFile()
        r1 = 0
        java.lang.Object r2 = r7.mLock     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
        monitor-enter(r2)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
        java.io.FileOutputStream r3 = r0.startWrite()     // Catch: java.lang.Throwable -> L3a
        monitor-exit(r2)     // Catch: java.lang.Throwable -> L37
        com.android.modules.utils.TypedXmlSerializer r2 = android.util.Xml.resolveSerializer(r3)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        r2.startDocument(r1, r4)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        java.lang.String r4 = "root"
        r2.startTag(r1, r4)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        java.lang.String r4 = "last_reset_time"
        java.util.concurrent.atomic.AtomicLong r7 = r7.mRawLastResetTime     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        long r5 = r7.get()     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        writeTagValue(r2, r4, r5)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        java.lang.String r7 = "root"
        r2.endTag(r1, r7)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        r2.endDocument()     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        r0.finishWrite(r3)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L3d
        goto L5d
    L34:
        r7 = move-exception
        r1 = r3
        goto L40
    L37:
        r7 = move-exception
        r1 = r3
        goto L3b
    L3a:
        r7 = move-exception
    L3b:
        monitor-exit(r2)     // Catch: java.lang.Throwable -> L3a
        throw r7     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
    L3d:
        r7 = move-exception
        goto L63
    L3f:
        r7 = move-exception
    L40:
        java.lang.String r2 = "ShortcutService"
        java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3d
        r3.<init>()     // Catch: java.lang.Throwable -> L3d
        java.lang.String r4 = "Failed to write to file "
        r3.append(r4)     // Catch: java.lang.Throwable -> L3d
        java.io.File r4 = r0.getBaseFile()     // Catch: java.lang.Throwable -> L3d
        r3.append(r4)     // Catch: java.lang.Throwable -> L3d
        java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L3d
        android.util.Slog.e(r2, r3, r7)     // Catch: java.lang.Throwable -> L3d
        r0.failWrite(r1)     // Catch: java.lang.Throwable -> L3d
    L5d:
        if (r0 == 0) goto L62
        r0.close()
    L62:
        return
    L63:
        if (r0 == 0) goto L6d
        r0.close()     // Catch: java.lang.Throwable -> L69
        goto L6d
    L69:
        r0 = move-exception
        r7.addSuppressed(r0)
    L6d:
        throw r7
    */
    throw new UnsupportedOperationException(
        "Method not decompiled: com.android.server.pm.ShortcutService.saveBaseState():void");
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x0079 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:27:0x0064 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void loadBaseStateLocked() {
    FileInputStream fileInputStream;
    char c;
    this.mRawLastResetTime.set(0L);
    ResilientAtomicFile baseStateFile = getBaseStateFile();
    try {
      try {
        try {
          fileInputStream = baseStateFile.openRead();
        } catch (FileNotFoundException unused) {
        }
      } catch (Exception e) {
        e = e;
        fileInputStream = null;
      }
      try {
        if (fileInputStream == null) {
          throw new FileNotFoundException(baseStateFile.getBaseFile().getAbsolutePath());
        }
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
        while (true) {
          int next = resolvePullParser.next();
          if (next == 1) {
            break;
          }
          if (next == 2) {
            int depth = resolvePullParser.getDepth();
            String name = resolvePullParser.getName();
            if (depth == 1) {
              if (!"root".equals(name)) {
                Slog.e("ShortcutService", "Invalid root tag: " + name);
                baseStateFile.close();
                return;
              }
            } else {
              if (name.hashCode() == -68726522 && name.equals("last_reset_time")) {
                c = 0;
                if (c != 0) {
                  this.mRawLastResetTime.set(parseLongAttribute(resolvePullParser, "value"));
                } else {
                  Slog.e("ShortcutService", "Invalid tag: " + name);
                }
              }
              c = 65535;
              if (c != 0) {}
            }
          }
        }
        if (baseStateFile != null) {
          baseStateFile.close();
        }
        getLastResetTimeLocked();
      } catch (Exception e2) {
        e = e2;
        baseStateFile.failRead(fileInputStream, e);
        loadBaseStateLocked();
        baseStateFile.close();
      }
    } catch (Throwable th) {
      if (baseStateFile != null) {
        try {
          baseStateFile.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public final ResilientAtomicFile getUserFile(int i) {
    return new ResilientAtomicFile(
        new File(injectUserDataPath(i), FILENAME_USER_PACKAGES),
        new File(injectUserDataPath(i), "shortcuts.xml.backup"),
        new File(injectUserDataPath(i), FILENAME_USER_PACKAGES_RESERVE_COPY),
        505,
        "user shortcut",
        null);
  }

  public boolean needRescheduleLocked() {
    if (this.retryCount < 5
        && this.startTime != 0
        && SystemClock.uptimeMillis() - this.startTime > 5000) {
      this.reschedule.set(true);
    }
    return this.reschedule.get();
  }

  public final void saveUser(int i) {
    saveUser(i, false);
  }

  public final void saveUser(int i, boolean z) {
    FileOutputStream startWrite;
    ResilientAtomicFile userFile = getUserFile(i);
    try {
      try {
        try {
          Slog.d("ShortcutService", "Saving to " + userFile);
          if (z) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            synchronized (this.mLock) {
              this.startTime = SystemClock.uptimeMillis();
              saveUserInternalLocked(i, byteArrayOutputStream, false);
              this.startTime = 0L;
              if (this.reschedule.getAndSet(false)) {
                this.retryCount++;
                scheduleSaveInner(i, 20000);
                Slog.d("ShortcutService", "reschedule saveUser " + i);
              } else {
                this.retryCount = 0;
                FileOutputStream startWrite2 = userFile.startWrite();
                byteArrayOutputStream.writeTo(startWrite2);
                userFile.finishWrite(startWrite2);
              }
            }
          } else {
            synchronized (this.mLock) {
              startWrite = userFile.startWrite();
              saveUserInternalLocked(i, startWrite, false);
            }
            userFile.finishWrite(startWrite);
          }
          Slog.d("ShortcutService", "finish Saving to " + userFile + " schedule: " + z);
          cleanupDanglingBitmapDirectoriesLocked(i);
          synchronized (this.mLock) {
            this.startTime = 0L;
          }
        } catch (IOException | XmlPullParserException e) {
          Slog.e("ShortcutService", "Failed to write to file " + userFile, e);
          userFile.failWrite(null);
          synchronized (this.mLock) {
            this.startTime = 0L;
          }
        }
        if (userFile != null) {
          userFile.close();
        }
        getUserShortcutsLocked(i).logSharingShortcutStats(this.mMetricsLogger);
      } catch (Throwable th) {
        if (userFile != null) {
          try {
            userFile.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
        }
        throw th;
      }
    } catch (Throwable th3) {
      synchronized (this.mLock) {
        this.startTime = 0L;
        throw th3;
      }
    }
  }

  public final void saveUserInternalLocked(int i, OutputStream outputStream, boolean z) {
    TypedXmlSerializer resolveSerializer;
    if (z) {
      resolveSerializer = Xml.newFastSerializer();
      resolveSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
    } else {
      resolveSerializer = Xml.resolveSerializer(outputStream);
    }
    resolveSerializer.startDocument((String) null, Boolean.TRUE);
    getUserShortcutsLocked(i).saveToXml(resolveSerializer, z);
    resolveSerializer.endDocument();
    outputStream.flush();
  }

  public static IOException throwForInvalidTag(int i, String str) {
    throw new IOException(
        String.format("Invalid tag '%s' found at depth %d", str, Integer.valueOf(i)));
  }

  public static void warnForInvalidTag(int i, String str) {
    Slog.w(
        "ShortcutService",
        String.format("Invalid tag '%s' found at depth %d", str, Integer.valueOf(i)));
  }

  public final ShortcutUser loadUserLocked(int i) {
    FileInputStream fileInputStream;
    Exception e;
    ResilientAtomicFile userFile = getUserFile(i);
    try {
      try {
        fileInputStream = userFile.openRead();
        if (fileInputStream != null) {
          try {
            ShortcutUser loadUserInternal = loadUserInternal(i, fileInputStream, false);
            userFile.close();
            return loadUserInternal;
          } catch (Exception e2) {
            e = e2;
            userFile.failRead(fileInputStream, e);
            ShortcutUser loadUserLocked = this.loadUserLocked(i);
            userFile.close();
            return loadUserLocked;
          }
        }
        userFile.close();
        return null;
      } catch (Exception e3) {
        fileInputStream = null;
        e = e3;
      }
    } catch (Throwable th) {
      if (userFile != null) {
        try {
          userFile.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public final ShortcutUser loadUserInternal(int i, InputStream inputStream, boolean z) {
    TypedXmlPullParser resolvePullParser;
    ShortcutUser shortcutUser = null;
    if (z) {
      resolvePullParser = Xml.newFastPullParser();
      resolvePullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
    } else {
      resolvePullParser = Xml.resolvePullParser(inputStream);
    }
    while (true) {
      int next = resolvePullParser.next();
      if (next == 1) {
        return shortcutUser;
      }
      if (next == 2) {
        int depth = resolvePullParser.getDepth();
        String name = resolvePullParser.getName();
        if (depth == 1 && "user".equals(name)) {
          shortcutUser = ShortcutUser.loadFromXml(this, resolvePullParser, i, z);
        } else {
          throwForInvalidTag(depth, name);
        }
      }
    }
  }

  public final void scheduleSaveBaseState() {
    scheduleSaveInner(-10000);
  }

  public void scheduleSaveUser(int i) {
    scheduleSaveInner(i);
  }

  public final void scheduleSaveInner(int i) {
    scheduleSaveInner(i, this.mSaveDelayMillis);
  }

  public final void scheduleSaveInner(int i, int i2) {
    synchronized (this.mLock) {
      if (!this.mDirtyUserIds.contains(Integer.valueOf(i))) {
        this.mDirtyUserIds.add(Integer.valueOf(i));
      }
    }
    this.mHandler.removeCallbacks(this.mSaveDirtyInfoRunner);
    this.mHandler.postDelayed(this.mSaveDirtyInfoRunner, i2);
  }

  public void saveDirtyInfo() {
    saveDirtyInfo(false);
  }

  public void saveDirtyInfo(boolean z) {
    List list;
    if (this.mShutdown.get()) {
      return;
    }
    try {
      try {
        Trace.traceBegin(524288L, "shortcutSaveDirtyInfo");
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
          list = this.mDirtyUserIds;
          this.mDirtyUserIds = arrayList;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
          int intValue = ((Integer) list.get(size)).intValue();
          if (intValue == -10000) {
            saveBaseState();
          } else {
            saveUser(intValue, z);
          }
        }
      } catch (Exception e) {
        wtf("Exception in saveDirtyInfo", e);
      }
    } finally {
      Trace.traceEnd(524288L);
    }
  }

  public void scheduleSaveDirtyInfo() {
    saveDirtyInfo(true);
  }

  public long getLastResetTimeLocked() {
    updateTimesLocked();
    return this.mRawLastResetTime.get();
  }

  public long getNextResetTimeLocked() {
    updateTimesLocked();
    return this.mRawLastResetTime.get() + this.mResetInterval;
  }

  public final void updateTimesLocked() {
    long injectCurrentTimeMillis = injectCurrentTimeMillis();
    long j = this.mRawLastResetTime.get();
    if (j != 0) {
      if (injectCurrentTimeMillis < j) {
        if (isClockValid(injectCurrentTimeMillis)) {
          Slog.w("ShortcutService", "Clock rewound");
        }
        injectCurrentTimeMillis = j;
      } else {
        long j2 = this.mResetInterval;
        if (j + j2 <= injectCurrentTimeMillis) {
          injectCurrentTimeMillis = ((injectCurrentTimeMillis / j2) * j2) + (j % j2);
        }
        injectCurrentTimeMillis = j;
      }
    }
    this.mRawLastResetTime.set(injectCurrentTimeMillis);
    if (j != injectCurrentTimeMillis) {
      scheduleSaveBaseState();
    }
  }

  public boolean isUserUnlockedL(int i) {
    synchronized (this.mUnlockedUsers) {
      if (this.mUnlockedUsers.get(i)) {
        return true;
      }
      return this.mUserManagerInternal.isUserUnlockingOrUnlocked(i);
    }
  }

  public void throwIfUserLockedL(int i) {
    if (isUserUnlockedL(i)) {
      return;
    }
    throw new IllegalStateException("User " + i + " is locked or not running");
  }

  public final boolean isUserLoadedLocked(int i) {
    return this.mUsers.get(i) != null;
  }

  public ShortcutUser getUserShortcutsLocked(int i) {
    if (!isUserUnlockedL(i)) {
      if (i != this.mLastLockedUser) {
        wtf("User still locked");
        this.mLastLockedUser = i;
      }
    } else {
      this.mLastLockedUser = -1;
    }
    ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.get(i);
    if (shortcutUser == null) {
      shortcutUser = loadUserLocked(i);
      if (shortcutUser == null) {
        shortcutUser = new ShortcutUser(this, i);
      }
      this.mUsers.put(i, shortcutUser);
      checkPackageChanges(i);
    }
    return shortcutUser;
  }

  public ShortcutNonPersistentUser getNonPersistentUserLocked(int i) {
    ShortcutNonPersistentUser shortcutNonPersistentUser =
        (ShortcutNonPersistentUser) this.mShortcutNonPersistentUsers.get(i);
    if (shortcutNonPersistentUser != null) {
      return shortcutNonPersistentUser;
    }
    ShortcutNonPersistentUser shortcutNonPersistentUser2 = new ShortcutNonPersistentUser(this, i);
    this.mShortcutNonPersistentUsers.put(i, shortcutNonPersistentUser2);
    return shortcutNonPersistentUser2;
  }

  public void forEachLoadedUserLocked(Consumer consumer) {
    for (int size = this.mUsers.size() - 1; size >= 0; size--) {
      consumer.accept((ShortcutUser) this.mUsers.valueAt(size));
    }
  }

  public ShortcutPackage getPackageShortcutsLocked(String str, int i) {
    return getUserShortcutsLocked(i).getPackageShortcuts(str);
  }

  public ShortcutPackage getPackageShortcutsForPublisherLocked(String str, int i) {
    ShortcutPackage packageShortcuts = getUserShortcutsLocked(i).getPackageShortcuts(str);
    packageShortcuts.getUser().onCalledByPublisher(str);
    return packageShortcuts;
  }

  public ShortcutLauncher getLauncherShortcutsLocked(String str, int i, int i2) {
    return getUserShortcutsLocked(i).getLauncherShortcuts(str, i2);
  }

  public void cleanupBitmapsForPackage(int i, String str) {
    File file = new File(getUserBitmapFilePath(i), str);
    if (file.isDirectory()) {
      if (FileUtils.deleteContents(file) && file.delete()) {
        return;
      }
      Slog.w("ShortcutService", "Unable to remove directory " + file);
    }
  }

  public final void cleanupDanglingBitmapDirectoriesLocked(int i) {
    long statStartTime = getStatStartTime();
    final ArrayMap arrayMap = new ArrayMap();
    synchronized (this.mLock) {
      getUserShortcutsLocked(i)
          .forAllPackages(
              new Consumer() { // from class:
                // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ShortcutService.lambda$cleanupDanglingBitmapDirectoriesLocked$2(
                      arrayMap, (ShortcutPackage) obj);
                }
              });
    }
    File[] listFiles = getUserBitmapFilePath(i).listFiles();
    if (listFiles == null) {
      return;
    }
    for (File file : listFiles) {
      if (file.isDirectory()) {
        String name = file.getName();
        ShortcutPackage shortcutPackage = (ShortcutPackage) arrayMap.get(name);
        if (shortcutPackage == null) {
          cleanupBitmapsForPackage(i, name);
        } else {
          shortcutPackage.cleanupDanglingBitmapFiles(file);
        }
      }
    }
    logDurationStat(5, statStartTime);
  }

  public static /* synthetic */ void lambda$cleanupDanglingBitmapDirectoriesLocked$2(
      ArrayMap arrayMap, ShortcutPackage shortcutPackage) {
    arrayMap.put(shortcutPackage.getPackageName(), shortcutPackage);
  }

  class FileOutputStreamWithPath extends FileOutputStream {
    public final File mFile;

    public FileOutputStreamWithPath(File file) {
      super(file);
      this.mFile = file;
    }

    public File getFile() {
      return this.mFile;
    }
  }

  public FileOutputStreamWithPath openIconFileForWrite(int i, ShortcutInfo shortcutInfo) {
    String str;
    File file = new File(getUserBitmapFilePath(i), shortcutInfo.getPackage());
    if (!file.isDirectory()) {
      file.mkdirs();
      if (!file.isDirectory()) {
        throw new IOException("Unable to create directory " + file);
      }
      SELinux.restorecon(file);
    }
    String valueOf = String.valueOf(injectCurrentTimeMillis());
    int i2 = 0;
    while (true) {
      StringBuilder sb = new StringBuilder();
      if (i2 == 0) {
        str = valueOf;
      } else {
        str = valueOf + "_" + i2;
      }
      sb.append(str);
      sb.append(".png");
      File file2 = new File(file, sb.toString());
      if (!file2.exists()) {
        return new FileOutputStreamWithPath(file2);
      }
      i2++;
    }
  }

  public void saveIconAndFixUpShortcutLocked(
      ShortcutPackage shortcutPackage, ShortcutInfo shortcutInfo) {
    if (shortcutInfo.hasIconFile() || shortcutInfo.hasIconResource() || shortcutInfo.hasIconUri()) {
      return;
    }
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      shortcutPackage.removeIcon(shortcutInfo);
      Icon icon = shortcutInfo.getIcon();
      if (icon == null) {
        return;
      }
      int i = this.mMaxIconDimension;
      try {
        int type = icon.getType();
        if (type == 1) {
          icon.getBitmap();
        } else if (type == 2) {
          injectValidateIconResPackage(shortcutInfo, icon);
          shortcutInfo.setIconResourceId(icon.getResId());
          shortcutInfo.addFlags(4);
          return;
        } else if (type == 4) {
          shortcutInfo.setIconUri(icon.getUriString());
          shortcutInfo.addFlags(32768);
          return;
        } else {
          if (type != 5) {
            if (type != 6) {
              throw ShortcutInfo.getInvalidIconException();
            }
            shortcutInfo.setIconUri(icon.getUriString());
            shortcutInfo.addFlags(33280);
            return;
          }
          icon.getBitmap();
          i = (int) (i * ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f));
        }
        shortcutPackage.saveBitmap(
            shortcutInfo, i, this.mIconPersistFormat, this.mIconPersistQuality);
      } finally {
        shortcutInfo.clearIcon();
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
    }
  }

  public void injectValidateIconResPackage(ShortcutInfo shortcutInfo, Icon icon) {
    if (!shortcutInfo.getPackage().equals(icon.getResPackage())) {
      throw new IllegalArgumentException("Icon resource must reside in shortcut owner package");
    }
  }

  public static Bitmap shrinkBitmap(Bitmap bitmap, int i) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    if (width <= i && height <= i) {
      return bitmap;
    }
    int max = Math.max(width, height);
    int i2 = (width * i) / max;
    int i3 = (height * i) / max;
    Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
    new Canvas(createBitmap)
        .drawBitmap(
            bitmap,
            (Rect) null,
            new RectF(
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                i2,
                i3),
            (Paint) null);
    return createBitmap;
  }

  public void fixUpShortcutResourceNamesAndValues(ShortcutInfo shortcutInfo) {
    Resources injectGetResourcesForApplicationAsUser =
        injectGetResourcesForApplicationAsUser(shortcutInfo.getPackage(), shortcutInfo.getUserId());
    if (injectGetResourcesForApplicationAsUser != null) {
      long statStartTime = getStatStartTime();
      try {
        shortcutInfo.lookupAndFillInResourceNames(injectGetResourcesForApplicationAsUser);
        logDurationStat(10, statStartTime);
        shortcutInfo.resolveResourceStrings(injectGetResourcesForApplicationAsUser);
      } catch (Throwable th) {
        logDurationStat(10, statStartTime);
        throw th;
      }
    }
  }

  public final boolean isCallerSystem() {
    return UserHandle.isSameApp(injectBinderCallingUid(), 1000);
  }

  public final boolean isCallerShell() {
    int injectBinderCallingUid = injectBinderCallingUid();
    return injectBinderCallingUid == 2000 || injectBinderCallingUid == 0;
  }

  public ComponentName injectChooserActivity() {
    if (this.mChooserActivity == null) {
      this.mChooserActivity =
          ComponentName.unflattenFromString(
              this.mContext.getResources().getString(R.string.contentServiceSync));
    }
    return this.mChooserActivity;
  }

  public final boolean isCallerChooserActivity() {
    int injectBinderCallingUid = injectBinderCallingUid();
    ComponentName injectChooserActivity = injectChooserActivity();
    return injectChooserActivity != null
        && UserHandle.getAppId(injectGetPackageUid(injectChooserActivity.getPackageName(), 0))
            == UserHandle.getAppId(injectBinderCallingUid);
  }

  public final void enforceSystemOrShell() {
    if (!isCallerSystem() && !isCallerShell()) {
      throw new SecurityException("Caller must be system or shell");
    }
  }

  public final void enforceShell() {
    if (!isCallerShell()) {
      throw new SecurityException("Caller must be shell");
    }
  }

  public final void enforceSystem() {
    if (!isCallerSystem()) {
      throw new SecurityException("Caller must be system");
    }
  }

  public final void enforceResetThrottlingPermission() {
    if (isCallerSystem()) {
      return;
    }
    enforceCallingOrSelfPermission("android.permission.RESET_SHORTCUT_MANAGER_THROTTLING", null);
  }

  public final void enforceCallingOrSelfPermission(String str, String str2) {
    if (isCallerSystem()) {
      return;
    }
    injectEnforceCallingPermission(str, str2);
  }

  public void injectEnforceCallingPermission(String str, String str2) {
    this.mContext.enforceCallingPermission(str, str2);
  }

  public final void verifyCallerUserId(int i) {
    if (!isCallerSystem() && UserHandle.getUserId(injectBinderCallingUid()) != i) {
      throw new SecurityException("Invalid user-ID");
    }
  }

  public final void verifyCaller(String str, int i) {
    Preconditions.checkStringNotEmpty(str, "packageName");
    if (isCallerSystem()) {
      return;
    }
    int injectBinderCallingUid = injectBinderCallingUid();
    if (UserHandle.getUserId(injectBinderCallingUid) != i) {
      throw new SecurityException("Invalid user-ID");
    }
    if (injectGetPackageUid(str, i) != injectBinderCallingUid) {
      throw new SecurityException("Calling package name mismatch");
    }
    Preconditions.checkState(!isEphemeralApp(str, i), "Ephemeral apps can't use ShortcutManager");
  }

  public final void verifyShortcutInfoPackage(String str, ShortcutInfo shortcutInfo) {
    if (shortcutInfo == null) {
      return;
    }
    if (!Objects.equals(str, shortcutInfo.getPackage())) {
      EventLog.writeEvent(1397638484, "109824443", -1, "");
      throw new SecurityException("Shortcut package name mismatch");
    }
    if (UserHandle.getUserId(injectBinderCallingUid()) != shortcutInfo.getUserId()) {
      throw new SecurityException("User-ID in shortcut doesn't match the caller");
    }
  }

  public final void verifyShortcutInfoPackages(String str, List list) {
    int size = list.size();
    for (int i = 0; i < size; i++) {
      verifyShortcutInfoPackage(str, (ShortcutInfo) list.get(i));
    }
  }

  public void injectPostToHandler(Runnable runnable) {
    this.mHandler.post(runnable);
  }

  public void injectRunOnNewThread(Runnable runnable) {
    new Thread(runnable).start();
  }

  public void injectPostToHandlerDebounced(Object obj, Runnable runnable) {
    Objects.requireNonNull(obj);
    Objects.requireNonNull(runnable);
    synchronized (this.mLock) {
      this.mHandler.removeCallbacksAndMessages(obj);
      this.mHandler.postDelayed(runnable, obj, 100L);
    }
  }

  public void enforceMaxActivityShortcuts(int i) {
    if (i > this.mMaxShortcuts) {
      throw new IllegalArgumentException("Max number of dynamic shortcuts exceeded");
    }
  }

  public int getMaxActivityShortcuts() {
    return this.mMaxShortcuts;
  }

  public int getMaxAppShortcuts() {
    return this.mMaxShortcutsPerApp;
  }

  public void packageShortcutsChanged(ShortcutPackage shortcutPackage, List list, List list2) {
    Objects.requireNonNull(shortcutPackage);
    String packageName = shortcutPackage.getPackageName();
    int packageUserId = shortcutPackage.getPackageUserId();
    injectPostToHandlerDebounced(
        shortcutPackage, notifyListenerRunnable(packageName, packageUserId));
    notifyShortcutChangeCallbacks(packageName, packageUserId, list, list2);
    shortcutPackage.scheduleSave();
  }

  public final void notifyListeners(String str, int i) {
    injectPostToHandler(notifyListenerRunnable(str, i));
  }

  public final Runnable notifyListenerRunnable(final String str, final int i) {
    return new Runnable() { // from class:
      // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda25
      @Override // java.lang.Runnable
      public final void run() {
        ShortcutService.this.lambda$notifyListenerRunnable$3(i, str);
      }
    };
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyListenerRunnable$3(int i, String str) {
    try {
      synchronized (this.mLock) {
        if (isUserUnlockedL(i)) {
          ArrayList arrayList = new ArrayList(this.mListeners);
          for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((ShortcutServiceInternal.ShortcutChangeListener) arrayList.get(size))
                .onShortcutChanged(str, i);
          }
        }
      }
    } catch (Exception unused) {
    }
  }

  public final void notifyShortcutChangeCallbacks(
      final String str, final int i, List list, List list2) {
    final List removeNonKeyFields = removeNonKeyFields(list);
    final List removeNonKeyFields2 = removeNonKeyFields(list2);
    final UserHandle of = UserHandle.of(i);
    injectPostToHandler(
        new Runnable() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda13
          @Override // java.lang.Runnable
          public final void run() {
            ShortcutService.this.lambda$notifyShortcutChangeCallbacks$4(
                i, removeNonKeyFields, str, of, removeNonKeyFields2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyShortcutChangeCallbacks$4(
      int i, List list, String str, UserHandle userHandle, List list2) {
    try {
      synchronized (this.mLock) {
        if (isUserUnlockedL(i)) {
          ArrayList arrayList = new ArrayList(this.mShortcutChangeCallbacks);
          for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (!CollectionUtils.isEmpty(list)) {
              ((LauncherApps.ShortcutChangeCallback) arrayList.get(size))
                  .onShortcutsAddedOrUpdated(str, list, userHandle);
            }
            if (!CollectionUtils.isEmpty(list2)) {
              ((LauncherApps.ShortcutChangeCallback) arrayList.get(size))
                  .onShortcutsRemoved(str, list2, userHandle);
            }
          }
        }
      }
    } catch (Exception unused) {
    }
  }

  public final List removeNonKeyFields(List list) {
    if (CollectionUtils.isEmpty(list)) {
      return list;
    }
    int size = list.size();
    ArrayList arrayList = new ArrayList(size);
    for (int i = 0; i < size; i++) {
      ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i);
      if (shortcutInfo.hasKeyFieldsOnly()) {
        arrayList.add(shortcutInfo);
      } else {
        arrayList.add(shortcutInfo.clone(4));
      }
    }
    return arrayList;
  }

  public final void fixUpIncomingShortcutInfo(ShortcutInfo shortcutInfo, boolean z, boolean z2) {
    if (shortcutInfo.isReturnedByServer()) {
      Log.w(
          "ShortcutService",
          "Re-publishing ShortcutInfo returned by server is not supported. Some information such as"
              + " icon may lost from shortcut.");
    }
    if (shortcutInfo.getActivity() != null) {
      Preconditions.checkState(
          shortcutInfo.getPackage().equals(shortcutInfo.getActivity().getPackageName()),
          "Cannot publish shortcut: activity "
              + shortcutInfo.getActivity()
              + " does not belong to package "
              + shortcutInfo.getPackage());
      Preconditions.checkState(
          injectIsMainActivity(shortcutInfo.getActivity(), shortcutInfo.getUserId()),
          "Cannot publish shortcut: activity "
              + shortcutInfo.getActivity()
              + " is not main activity");
    }
    if (!z) {
      shortcutInfo.enforceMandatoryFields(z2);
      if (!z2) {
        Preconditions.checkState(
            shortcutInfo.getActivity() != null,
            "Cannot publish shortcut: target activity is not set");
      }
    }
    if (shortcutInfo.getIcon() != null) {
      ShortcutInfo.validateIcon(shortcutInfo.getIcon());
      validateIconURI(shortcutInfo);
    }
    shortcutInfo.replaceFlags(shortcutInfo.getFlags() & IInstalld.FLAG_FORCE);
  }

  public final void validateIconURI(ShortcutInfo shortcutInfo) {
    int injectBinderCallingUid = injectBinderCallingUid();
    Icon icon = shortcutInfo.getIcon();
    if (icon == null) {
      return;
    }
    int type = icon.getType();
    if (type == 4 || type == 6) {
      Uri uri = icon.getUri();
      this.mUriGrantsManagerInternal.checkGrantUriPermission(
          injectBinderCallingUid,
          shortcutInfo.getPackage(),
          ContentProvider.getUriWithoutUserId(uri),
          1,
          ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(injectBinderCallingUid)));
    }
  }

  public final void fixUpIncomingShortcutInfo(ShortcutInfo shortcutInfo, boolean z) {
    fixUpIncomingShortcutInfo(shortcutInfo, z, false);
  }

  public void validateShortcutForPinRequest(ShortcutInfo shortcutInfo) {
    fixUpIncomingShortcutInfo(shortcutInfo, false, true);
  }

  public final void fillInDefaultActivity(List list) {
    ComponentName componentName = null;
    for (int size = list.size() - 1; size >= 0; size--) {
      ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(size);
      if (shortcutInfo.getActivity() == null) {
        if (componentName == null) {
          componentName =
              injectGetDefaultMainActivity(shortcutInfo.getPackage(), shortcutInfo.getUserId());
          Preconditions.checkState(
              componentName != null,
              "Launcher activity not found for package " + shortcutInfo.getPackage());
        }
        shortcutInfo.setActivity(componentName);
      }
    }
  }

  public final void assignImplicitRanks(List list) {
    for (int size = list.size() - 1; size >= 0; size--) {
      ((ShortcutInfo) list.get(size)).setImplicitRank(size);
    }
  }

  public final List setReturnedByServer(List list) {
    for (int size = list.size() - 1; size >= 0; size--) {
      ((ShortcutInfo) list.get(size)).setReturnedByServer();
    }
    return list;
  }

  public boolean setDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) {
    verifyCaller(str, i);
    boolean injectHasUnlimitedShortcutsApiCallsPermission =
        injectHasUnlimitedShortcutsApiCallsPermission(
            injectBinderCallingPid(), injectBinderCallingUid());
    List list = parceledListSlice.getList();
    verifyShortcutInfoPackages(str, list);
    int size = list.size();
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      ShortcutPackage packageShortcutsForPublisherLocked =
          getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list, true);
      packageShortcutsForPublisherLocked.ensureNoBitmapIconIfShortcutIsLongLived(list);
      fillInDefaultActivity(list);
      packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(list, 0);
      if (!packageShortcutsForPublisherLocked.tryApiCall(
          injectHasUnlimitedShortcutsApiCallsPermission)) {
        return false;
      }
      packageShortcutsForPublisherLocked.clearAllImplicitRanks();
      assignImplicitRanks(list);
      for (int i2 = 0; i2 < size; i2++) {
        fixUpIncomingShortcutInfo((ShortcutInfo) list.get(i2), false);
      }
      ArrayList arrayList = new ArrayList();
      packageShortcutsForPublisherLocked.findAll(
          arrayList,
          new Predicate() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$setDynamicShortcuts$5;
              lambda$setDynamicShortcuts$5 =
                  ShortcutService.lambda$setDynamicShortcuts$5((ShortcutInfo) obj);
              return lambda$setDynamicShortcuts$5;
            }
          },
          4);
      List deleteAllDynamicShortcuts =
          packageShortcutsForPublisherLocked.deleteAllDynamicShortcuts();
      for (int i3 = 0; i3 < size; i3++) {
        packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut((ShortcutInfo) list.get(i3));
      }
      packageShortcutsForPublisherLocked.adjustRanks();
      packageShortcutsChanged(
          packageShortcutsForPublisherLocked,
          prepareChangedShortcuts(
              arrayList, list, deleteAllDynamicShortcuts, packageShortcutsForPublisherLocked),
          deleteAllDynamicShortcuts);
      verifyStates();
      return true;
    }
  }

  public static /* synthetic */ boolean lambda$setDynamicShortcuts$5(ShortcutInfo shortcutInfo) {
    return shortcutInfo.isVisibleToPublisher()
        && shortcutInfo.isDynamic()
        && (shortcutInfo.isCached() || shortcutInfo.isPinned());
  }

  public boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) {
    verifyCaller(str, i);
    boolean injectHasUnlimitedShortcutsApiCallsPermission =
        injectHasUnlimitedShortcutsApiCallsPermission(
            injectBinderCallingPid(), injectBinderCallingUid());
    List list = parceledListSlice.getList();
    verifyShortcutInfoPackages(str, list);
    int size = list.size();
    final ArrayList arrayList = new ArrayList(1);
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      final ShortcutPackage packageShortcutsForPublisherLocked =
          getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list, true);
      packageShortcutsForPublisherLocked.ensureNoBitmapIconIfShortcutIsLongLived(list);
      packageShortcutsForPublisherLocked.ensureAllShortcutsVisibleToLauncher(list);
      packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(list, 2);
      boolean tryApiCall =
          packageShortcutsForPublisherLocked.tryApiCall(
              injectHasUnlimitedShortcutsApiCallsPermission);
      if (!tryApiCall) {
        return false;
      }
      packageShortcutsForPublisherLocked.clearAllImplicitRanks();
      assignImplicitRanks(list);
      for (int i2 = 0; i2 < size; i2++) {
        final ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i2);
        fixUpIncomingShortcutInfo(shortcutInfo, true);
        packageShortcutsForPublisherLocked.mutateShortcut(
            shortcutInfo.getId(),
            null,
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda16
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.this.lambda$updateShortcuts$6(
                    shortcutInfo,
                    packageShortcutsForPublisherLocked,
                    arrayList,
                    (ShortcutInfo) obj);
              }
            });
      }
      packageShortcutsForPublisherLocked.adjustRanks();
      if (arrayList.isEmpty()) {
        arrayList = null;
      }
      packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
      verifyStates();
      return true;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateShortcuts$6(
      ShortcutInfo shortcutInfo,
      ShortcutPackage shortcutPackage,
      List list,
      ShortcutInfo shortcutInfo2) {
    if (shortcutInfo2 == null || !shortcutInfo2.isVisibleToPublisher()) {
      return;
    }
    if (shortcutInfo2.isEnabled() != shortcutInfo.isEnabled()) {
      Slog.w("ShortcutService", "ShortcutInfo.enabled cannot be changed with updateShortcuts()");
    }
    if (shortcutInfo2.isLongLived() != shortcutInfo.isLongLived()) {
      Slog.w("ShortcutService", "ShortcutInfo.longLived cannot be changed with updateShortcuts()");
    }
    if (shortcutInfo.hasRank()) {
      shortcutInfo2.setRankChanged();
      shortcutInfo2.setImplicitRank(shortcutInfo.getImplicitRank());
    }
    boolean z = shortcutInfo.getIcon() != null;
    if (z) {
      shortcutPackage.removeIcon(shortcutInfo2);
    }
    shortcutInfo2.copyNonNullFieldsFrom(shortcutInfo);
    shortcutInfo2.setTimestamp(injectCurrentTimeMillis());
    if (z) {
      saveIconAndFixUpShortcutLocked(shortcutPackage, shortcutInfo2);
    }
    if (z || shortcutInfo.hasStringResources()) {
      fixUpShortcutResourceNamesAndValues(shortcutInfo2);
    }
    list.add(shortcutInfo2);
  }

  public boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) {
    verifyCaller(str, i);
    boolean injectHasUnlimitedShortcutsApiCallsPermission =
        injectHasUnlimitedShortcutsApiCallsPermission(
            injectBinderCallingPid(), injectBinderCallingUid());
    List list = parceledListSlice.getList();
    verifyShortcutInfoPackages(str, list);
    int size = list.size();
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      ShortcutPackage packageShortcutsForPublisherLocked =
          getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list, true);
      packageShortcutsForPublisherLocked.ensureNoBitmapIconIfShortcutIsLongLived(list);
      fillInDefaultActivity(list);
      packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(list, 1);
      packageShortcutsForPublisherLocked.clearAllImplicitRanks();
      assignImplicitRanks(list);
      if (!packageShortcutsForPublisherLocked.tryApiCall(
          injectHasUnlimitedShortcutsApiCallsPermission)) {
        return false;
      }
      ArrayList arrayList = null;
      for (int i2 = 0; i2 < size; i2++) {
        ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i2);
        fixUpIncomingShortcutInfo(shortcutInfo, false);
        shortcutInfo.setRankChanged();
        packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(shortcutInfo);
        if (arrayList == null) {
          arrayList = new ArrayList(1);
        }
        arrayList.add(shortcutInfo);
      }
      packageShortcutsForPublisherLocked.adjustRanks();
      packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
      verifyStates();
      return true;
    }
  }

  public void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) {
    List list;
    verifyCaller(str, i);
    verifyShortcutInfoPackage(str, shortcutInfo);
    ArrayList arrayList = new ArrayList();
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      ShortcutPackage packageShortcutsForPublisherLocked =
          getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureNotImmutable(shortcutInfo.getId(), true);
      fillInDefaultActivity(Arrays.asList(shortcutInfo));
      if (!shortcutInfo.hasRank()) {
        shortcutInfo.setRank(0);
      }
      packageShortcutsForPublisherLocked.clearAllImplicitRanks();
      shortcutInfo.setImplicitRank(0);
      fixUpIncomingShortcutInfo(shortcutInfo, false);
      shortcutInfo.setRankChanged();
      if (!packageShortcutsForPublisherLocked.pushDynamicShortcut(shortcutInfo, arrayList)) {
        list = null;
      } else {
        if (arrayList.isEmpty()) {
          return;
        }
        list = Collections.singletonList((ShortcutInfo) arrayList.get(0));
        arrayList.clear();
      }
      arrayList.add(shortcutInfo);
      packageShortcutsForPublisherLocked.adjustRanks();
      packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, list);
      packageShortcutsForPublisherLocked.reportShortcutUsed(
          this.mUsageStatsManagerInternal, shortcutInfo.getId());
      verifyStates();
    }
  }

  public void requestPinShortcut(
      String str,
      ShortcutInfo shortcutInfo,
      IntentSender intentSender,
      int i,
      AndroidFuture androidFuture) {
    Objects.requireNonNull(shortcutInfo);
    Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
    Preconditions.checkArgument(
        true ^ shortcutInfo.isExcludedFromSurfaces(1),
        "Shortcut excluded from launcher cannot be pinned");
    androidFuture.complete(
        String.valueOf(requestPinItem(str, i, shortcutInfo, null, null, intentSender)));
  }

  public void createShortcutResultIntent(
      String str, ShortcutInfo shortcutInfo, int i, AndroidFuture androidFuture) {
    Intent createShortcutResultIntent;
    Objects.requireNonNull(shortcutInfo);
    Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
    verifyCaller(str, i);
    verifyShortcutInfoPackage(str, shortcutInfo);
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      createShortcutResultIntent =
          this.mShortcutRequestPinProcessor.createShortcutResultIntent(shortcutInfo, i);
    }
    verifyStates();
    androidFuture.complete(createShortcutResultIntent);
  }

  public final boolean requestPinItem(
      String str,
      int i,
      ShortcutInfo shortcutInfo,
      AppWidgetProviderInfo appWidgetProviderInfo,
      Bundle bundle,
      IntentSender intentSender) {
    return requestPinItem(
        str,
        i,
        shortcutInfo,
        appWidgetProviderInfo,
        bundle,
        intentSender,
        injectBinderCallingPid(),
        injectBinderCallingUid(),
        0);
  }

  public final boolean requestPinItem(
      String str,
      int i,
      ShortcutInfo shortcutInfo,
      AppWidgetProviderInfo appWidgetProviderInfo,
      Bundle bundle,
      IntentSender intentSender,
      int i2,
      int i3,
      int i4) {
    boolean requestPinItemLocked;
    verifyCaller(str, i);
    if (shortcutInfo == null || !injectHasAccessShortcutsPermission(i2, i3)) {
      verifyShortcutInfoPackage(str, shortcutInfo);
    }
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      Preconditions.checkState(
          isUidForegroundLocked(i3),
          "Calling application must have a foreground activity or a foreground service");
      if (shortcutInfo != null) {
        ShortcutPackage packageShortcutsForPublisherLocked =
            getPackageShortcutsForPublisherLocked(shortcutInfo.getPackage(), i);
        if (packageShortcutsForPublisherLocked.isShortcutExistsAndInvisibleToPublisher(
            shortcutInfo.getId())) {
          packageShortcutsForPublisherLocked.updateInvisibleShortcutForPinRequestWith(shortcutInfo);
          packageShortcutsChanged(
              packageShortcutsForPublisherLocked, Collections.singletonList(shortcutInfo), null);
        }
      }
      requestPinItemLocked =
          this.mShortcutRequestPinProcessor.requestPinItemLocked(
              shortcutInfo, appWidgetProviderInfo, bundle, i, intentSender, i4);
    }
    verifyStates();
    return requestPinItemLocked;
  }

  public void disableShortcuts(String str, List list, CharSequence charSequence, int i, int i2) {
    ShortcutPackage packageShortcutsForPublisherLocked;
    ArrayList arrayList;
    ArrayList arrayList2;
    verifyCaller(str, i2);
    Objects.requireNonNull(list, "shortcutIds must be provided");
    synchronized (this.mLock) {
      throwIfUserLockedL(i2);
      packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i2);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
      String charSequence2 = charSequence == null ? null : charSequence.toString();
      arrayList = null;
      arrayList2 = null;
      for (int size = list.size() - 1; size >= 0; size--) {
        String str2 = (String) Preconditions.checkStringNotEmpty((String) list.get(size));
        if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
          ShortcutInfo disableWithId =
              packageShortcutsForPublisherLocked.disableWithId(
                  str2, charSequence2, i, false, true, 1);
          if (disableWithId == null) {
            if (arrayList == null) {
              arrayList = new ArrayList(1);
            }
            arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
          } else {
            if (arrayList2 == null) {
              arrayList2 = new ArrayList(1);
            }
            arrayList2.add(disableWithId);
          }
        }
      }
      packageShortcutsForPublisherLocked.adjustRanks();
    }
    packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
    verifyStates();
  }

  public void enableShortcuts(String str, List list, int i) {
    ShortcutPackage packageShortcutsForPublisherLocked;
    ArrayList arrayList;
    verifyCaller(str, i);
    Objects.requireNonNull(list, "shortcutIds must be provided");
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
      arrayList = null;
      for (int size = list.size() - 1; size >= 0; size--) {
        String str2 = (String) Preconditions.checkStringNotEmpty((String) list.get(size));
        if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
          packageShortcutsForPublisherLocked.enableWithId(str2);
          if (arrayList == null) {
            arrayList = new ArrayList(1);
          }
          arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
        }
      }
    }
    packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
    verifyStates();
  }

  public void removeDynamicShortcuts(String str, List list, int i) {
    ShortcutPackage packageShortcutsForPublisherLocked;
    ArrayList arrayList;
    ArrayList arrayList2;
    verifyCaller(str, i);
    Objects.requireNonNull(list, "shortcutIds must be provided");
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
      arrayList = null;
      arrayList2 = null;
      for (int size = list.size() - 1; size >= 0; size--) {
        String str2 = (String) Preconditions.checkStringNotEmpty((String) list.get(size));
        if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
          ShortcutInfo deleteDynamicWithId =
              packageShortcutsForPublisherLocked.deleteDynamicWithId(str2, true, false);
          if (deleteDynamicWithId == null) {
            if (arrayList == null) {
              arrayList = new ArrayList(1);
            }
            arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
          } else {
            if (arrayList2 == null) {
              arrayList2 = new ArrayList(1);
            }
            arrayList2.add(deleteDynamicWithId);
          }
        }
      }
      packageShortcutsForPublisherLocked.adjustRanks();
    }
    packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
    verifyStates();
  }

  public void removeAllDynamicShortcuts(String str, int i) {
    ShortcutPackage packageShortcutsForPublisherLocked;
    List deleteAllDynamicShortcuts;
    List prepareChangedShortcuts;
    verifyCaller(str, i);
    ArrayList arrayList = new ArrayList();
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.findAll(
          arrayList,
          new Predicate() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeAllDynamicShortcuts$7;
              lambda$removeAllDynamicShortcuts$7 =
                  ShortcutService.lambda$removeAllDynamicShortcuts$7((ShortcutInfo) obj);
              return lambda$removeAllDynamicShortcuts$7;
            }
          },
          4);
      deleteAllDynamicShortcuts = packageShortcutsForPublisherLocked.deleteAllDynamicShortcuts();
      prepareChangedShortcuts =
          prepareChangedShortcuts(
              arrayList,
              (List) null,
              deleteAllDynamicShortcuts,
              packageShortcutsForPublisherLocked);
    }
    packageShortcutsChanged(
        packageShortcutsForPublisherLocked, prepareChangedShortcuts, deleteAllDynamicShortcuts);
    verifyStates();
  }

  public static /* synthetic */ boolean lambda$removeAllDynamicShortcuts$7(
      ShortcutInfo shortcutInfo) {
    return shortcutInfo.isVisibleToPublisher()
        && shortcutInfo.isDynamic()
        && (shortcutInfo.isCached() || shortcutInfo.isPinned());
  }

  public void removeLongLivedShortcuts(String str, List list, int i) {
    ShortcutPackage packageShortcutsForPublisherLocked;
    ArrayList arrayList;
    ArrayList arrayList2;
    verifyCaller(str, i);
    Objects.requireNonNull(list, "shortcutIds must be provided");
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
      packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
      arrayList = null;
      arrayList2 = null;
      for (int size = list.size() - 1; size >= 0; size--) {
        String str2 = (String) Preconditions.checkStringNotEmpty((String) list.get(size));
        if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
          ShortcutInfo deleteLongLivedWithId =
              packageShortcutsForPublisherLocked.deleteLongLivedWithId(str2, true);
          if (deleteLongLivedWithId != null) {
            if (arrayList2 == null) {
              arrayList2 = new ArrayList(1);
            }
            arrayList2.add(deleteLongLivedWithId);
          } else {
            if (arrayList == null) {
              arrayList = new ArrayList(1);
            }
            arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
          }
        }
      }
      packageShortcutsForPublisherLocked.adjustRanks();
    }
    packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
    verifyStates();
  }

  public ParceledListSlice getShortcuts(String str, int i, int i2) {
    ParceledListSlice shortcutsWithQueryLocked;
    verifyCaller(str, i2);
    synchronized (this.mLock) {
      throwIfUserLockedL(i2);
      boolean z = true;
      int i3 = (i & 2) != 0 ? 1 : 0;
      boolean z2 = (i & 4) != 0;
      boolean z3 = (i & 1) != 0;
      if ((i & 8) == 0) {
        z = false;
      }
      final int i4 = (z2 ? 2 : 0) | i3 | (z3 ? 32 : 0) | (z ? 1610629120 : 0);
      shortcutsWithQueryLocked =
          getShortcutsWithQueryLocked(
              str,
              i2,
              9,
              new Predicate() { // from class:
                // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda28
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$getShortcuts$8;
                  lambda$getShortcuts$8 =
                      ShortcutService.lambda$getShortcuts$8(i4, (ShortcutInfo) obj);
                  return lambda$getShortcuts$8;
                }
              });
    }
    return shortcutsWithQueryLocked;
  }

  public static /* synthetic */ boolean lambda$getShortcuts$8(int i, ShortcutInfo shortcutInfo) {
    return shortcutInfo.isVisibleToPublisher() && (i & shortcutInfo.getFlags()) != 0;
  }

  public ParceledListSlice getShareTargets(String str, final IntentFilter intentFilter, int i) {
    ParceledListSlice parceledListSlice;
    Preconditions.checkStringNotEmpty(str, "packageName");
    Objects.requireNonNull(intentFilter, "intentFilter");
    if (!isCallerChooserActivity()) {
      verifyCaller(str, i);
    }
    enforceCallingOrSelfPermission("android.permission.MANAGE_APP_PREDICTIONS", "getShareTargets");
    ComponentName injectChooserActivity = injectChooserActivity();
    final String packageName =
        injectChooserActivity != null
            ? injectChooserActivity.getPackageName()
            : this.mContext.getPackageName();
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      final ArrayList arrayList = new ArrayList();
      getUserShortcutsLocked(i)
          .forAllPackages(
              new Consumer() { // from class:
                // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda18
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ShortcutService.lambda$getShareTargets$9(
                      arrayList, intentFilter, packageName, (ShortcutPackage) obj);
                }
              });
      parceledListSlice = new ParceledListSlice(arrayList);
    }
    return parceledListSlice;
  }

  public static /* synthetic */ void lambda$getShareTargets$9(
      List list, IntentFilter intentFilter, String str, ShortcutPackage shortcutPackage) {
    list.addAll(shortcutPackage.getMatchingShareTargets(intentFilter, str));
  }

  public boolean hasShareTargets(String str, String str2, int i) {
    boolean hasShareTargets;
    verifyCaller(str, i);
    enforceCallingOrSelfPermission("android.permission.MANAGE_APP_PREDICTIONS", "hasShareTargets");
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      hasShareTargets = getPackageShortcutsLocked(str2, i).hasShareTargets();
    }
    return hasShareTargets;
  }

  public boolean isSharingShortcut(
      int i, String str, String str2, String str3, int i2, IntentFilter intentFilter) {
    verifyCaller(str, i);
    enforceCallingOrSelfPermission(
        "android.permission.MANAGE_APP_PREDICTIONS", "isSharingShortcut");
    synchronized (this.mLock) {
      throwIfUserLockedL(i2);
      throwIfUserLockedL(i);
      List matchingShareTargets =
          getPackageShortcutsLocked(str2, i2).getMatchingShareTargets(intentFilter);
      int size = matchingShareTargets.size();
      for (int i3 = 0; i3 < size; i3++) {
        if (((ShortcutManager.ShareShortcutInfo) matchingShareTargets.get(i3))
            .getShortcutInfo()
            .getId()
            .equals(str3)) {
          return true;
        }
      }
      return false;
    }
  }

  public final ParceledListSlice getShortcutsWithQueryLocked(
      String str, int i, int i2, Predicate predicate) {
    ArrayList arrayList = new ArrayList();
    getPackageShortcutsForPublisherLocked(str, i).findAll(arrayList, predicate, i2);
    return new ParceledListSlice(setReturnedByServer(arrayList));
  }

  public int getMaxShortcutCountPerActivity(String str, int i) {
    verifyCaller(str, i);
    return this.mMaxShortcuts;
  }

  public int getRemainingCallCount(String str, int i) {
    int apiCallCount;
    verifyCaller(str, i);
    boolean injectHasUnlimitedShortcutsApiCallsPermission =
        injectHasUnlimitedShortcutsApiCallsPermission(
            injectBinderCallingPid(), injectBinderCallingUid());
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      apiCallCount =
          this.mMaxUpdatesPerInterval
              - getPackageShortcutsForPublisherLocked(str, i)
                  .getApiCallCount(injectHasUnlimitedShortcutsApiCallsPermission);
    }
    return apiCallCount;
  }

  public long getRateLimitResetTime(String str, int i) {
    long nextResetTimeLocked;
    verifyCaller(str, i);
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      nextResetTimeLocked = getNextResetTimeLocked();
    }
    return nextResetTimeLocked;
  }

  public int getIconMaxDimensions(String str, int i) {
    int i2;
    verifyCaller(str, i);
    synchronized (this.mLock) {
      i2 = this.mMaxIconDimension;
    }
    return i2;
  }

  public void reportShortcutUsed(String str, String str2, int i) {
    verifyCaller(str, i);
    Objects.requireNonNull(str2);
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      ShortcutPackage packageShortcutsForPublisherLocked =
          getPackageShortcutsForPublisherLocked(str, i);
      if (packageShortcutsForPublisherLocked.findShortcutById(str2) == null) {
        Log.w(
            "ShortcutService",
            String.format("reportShortcutUsed: package %s doesn't have shortcut %s", str, str2));
      } else {
        packageShortcutsForPublisherLocked.reportShortcutUsed(
            this.mUsageStatsManagerInternal, str2);
      }
    }
  }

  public boolean isRequestPinItemSupported(int i, int i2) {
    verifyCallerUserId(i);
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      return this.mShortcutRequestPinProcessor.isRequestPinItemSupported(i, i2);
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
    }
  }

  public void resetThrottling() {
    enforceSystemOrShell();
    resetThrottlingInner(getCallingUserId());
  }

  public void resetThrottlingInner(int i) {
    synchronized (this.mLock) {
      if (!isUserUnlockedL(i)) {
        Log.w("ShortcutService", "User " + i + " is locked or not running");
        return;
      }
      getUserShortcutsLocked(i).resetThrottling();
      scheduleSaveUser(i);
      Slog.i("ShortcutService", "ShortcutManager: throttling counter reset for user " + i);
    }
  }

  public void resetAllThrottlingInner() {
    this.mRawLastResetTime.set(injectCurrentTimeMillis());
    scheduleSaveBaseState();
    Slog.i("ShortcutService", "ShortcutManager: throttling counter reset for all users");
  }

  public void onApplicationActive(String str, int i) {
    enforceResetThrottlingPermission();
    synchronized (this.mLock) {
      if (isUserUnlockedL(i)) {
        getPackageShortcutsLocked(str, i).resetRateLimitingForCommandLineNoSaving();
        scheduleSaveUser(i);
      }
    }
  }

  public boolean hasShortcutHostPermission(String str, int i, int i2, int i3) {
    if (canSeeAnyPinnedShortcut(str, i, i2, i3)) {
      return true;
    }
    long statStartTime = getStatStartTime();
    try {
      return hasShortcutHostPermissionInner(str, i);
    } finally {
      logDurationStat(4, statStartTime);
    }
  }

  public boolean canSeeAnyPinnedShortcut(String str, int i, int i2, int i3) {
    boolean hasHostPackage;
    if (injectHasAccessShortcutsPermission(i2, i3)) {
      return true;
    }
    synchronized (this.mNonPersistentUsersLock) {
      hasHostPackage = getNonPersistentUserLocked(i).hasHostPackage(str);
    }
    return hasHostPackage;
  }

  public boolean injectHasAccessShortcutsPermission(int i, int i2) {
    return this.mContext.checkPermission("android.permission.ACCESS_SHORTCUTS", i, i2) == 0;
  }

  public boolean injectHasUnlimitedShortcutsApiCallsPermission(int i, int i2) {
    return this.mContext.checkPermission("android.permission.UNLIMITED_SHORTCUTS_API_CALLS", i, i2)
        == 0;
  }

  public boolean hasShortcutHostPermissionInner(String str, int i) {
    synchronized (this.mLock) {
      throwIfUserLockedL(i);
      String defaultLauncher = getDefaultLauncher(i);
      if (defaultLauncher == null) {
        return false;
      }
      return defaultLauncher.equals(str);
    }
  }

  public String getDefaultLauncher(int i) {
    long statStartTime = getStatStartTime();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      synchronized (this.mLock) {
        throwIfUserLockedL(i);
        ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        String cachedLauncher = userShortcutsLocked.getCachedLauncher();
        if (cachedLauncher != null) {
          return cachedLauncher;
        }
        long statStartTime2 = getStatStartTime();
        String injectGetHomeRoleHolderAsUser =
            injectGetHomeRoleHolderAsUser(getParentOrSelfUserId(i));
        logDurationStat(0, statStartTime2);
        if (injectGetHomeRoleHolderAsUser != null) {
          userShortcutsLocked.setCachedLauncher(injectGetHomeRoleHolderAsUser);
        } else {
          Slog.e("ShortcutService", "Default launcher not found. user: " + i);
        }
        return injectGetHomeRoleHolderAsUser;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      logDurationStat(16, statStartTime);
    }
  }

  public void setShortcutHostPackage(String str, String str2, int i) {
    synchronized (this.mNonPersistentUsersLock) {
      getNonPersistentUserLocked(i).setShortcutHostPackage(str, str2);
    }
  }

  public final void cleanUpPackageForAllLoadedUsers(
      final String str, final int i, final boolean z) {
    synchronized (this.mLock) {
      forEachLoadedUserLocked(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutService.this.lambda$cleanUpPackageForAllLoadedUsers$10(
                  str, i, z, (ShortcutUser) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cleanUpPackageForAllLoadedUsers$10(
      String str, int i, boolean z, ShortcutUser shortcutUser) {
    cleanUpPackageLocked(str, shortcutUser.getUserId(), i, z);
  }

  public void cleanUpPackageLocked(final String str, int i, final int i2, boolean z) {
    boolean isUserLoadedLocked = isUserLoadedLocked(i);
    ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
    final ShortcutPackage removePackage = i2 == i ? userShortcutsLocked.removePackage(str) : null;
    boolean z2 = removePackage != null;
    final ShortcutLauncher removeLauncher = userShortcutsLocked.removeLauncher(i2, str);
    userShortcutsLocked.forAllLaunchers(
        new Consumer() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda20
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((ShortcutLauncher) obj).cleanUpPackage(str, i2);
          }
        });
    userShortcutsLocked.forAllPackages(
        new Consumer() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda21
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((ShortcutPackage) obj).refreshPinnedFlags();
          }
        });
    if (z2) {
      notifyListeners(str, i);
    }
    if (z && i2 == i) {
      userShortcutsLocked.rescanPackageIfNeeded(str, true);
    }
    if (!z && i2 == i) {
      injectPostToHandler(
          new Runnable() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
              ShortcutService.lambda$cleanUpPackageLocked$13(ShortcutPackage.this, removeLauncher);
            }
          });
    }
    if (isUserLoadedLocked) {
      return;
    }
    unloadUserLocked(i);
  }

  public static /* synthetic */ void lambda$cleanUpPackageLocked$13(
      ShortcutPackage shortcutPackage, ShortcutLauncher shortcutLauncher) {
    if (shortcutPackage != null) {
      shortcutPackage.removeShortcutPackageItem();
    }
    if (shortcutLauncher != null) {
      shortcutLauncher.removeShortcutPackageItem();
    }
  }

  public class LocalService extends ShortcutServiceInternal {
    public LocalService() {}

    public List getShortcuts(
        final int i,
        final String str,
        final long j,
        String str2,
        List list,
        final List list2,
        final ComponentName componentName,
        final int i2,
        final int i3,
        final int i4,
        final int i5) {
      final ArrayList arrayList;
      ArrayList arrayList2 = new ArrayList();
      int i6 =
          ("com.sec.android.app.desktoplauncher".equals(str)
                  && ShortcutService.isSystem(ShortcutService.this.getApplicationInfo(str, i)))
              ? 25
              : 27;
      if ((i2 & 4) != 0) {
        i6 = 4;
      } else if ((i2 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
        i6 &= -17;
      }
      final int i7 = i6;
      final List list3 = str2 == null ? null : list;
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i3);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i3, i)
            .attemptToRestoreIfNeededAndSave();
        if (str2 != null) {
          arrayList = arrayList2;
          getShortcutsInnerLocked(
              i, str, str2, list3, list2, j, componentName, i2, i3, arrayList2, i7, i4, i5);
        } else {
          arrayList = arrayList2;
          ShortcutService.this
              .getUserShortcutsLocked(i3)
              .forAllPackages(
                  new Consumer() { // from class:
                    // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      ShortcutService.LocalService.this.lambda$getShortcuts$0(
                          i,
                          str,
                          list3,
                          list2,
                          j,
                          componentName,
                          i2,
                          i3,
                          arrayList,
                          i7,
                          i4,
                          i5,
                          (ShortcutPackage) obj);
                    }
                  });
        }
      }
      return ShortcutService.this.setReturnedByServer(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShortcuts$0(
        int i,
        String str,
        List list,
        List list2,
        long j,
        ComponentName componentName,
        int i2,
        int i3,
        ArrayList arrayList,
        int i4,
        int i5,
        int i6,
        ShortcutPackage shortcutPackage) {
      getShortcutsInnerLocked(
          i,
          str,
          shortcutPackage.getPackageName(),
          list,
          list2,
          j,
          componentName,
          i2,
          i3,
          arrayList,
          i4,
          i5,
          i6);
    }

    public final void getShortcutsInnerLocked(
        int i,
        String str,
        String str2,
        List list,
        List list2,
        long j,
        ComponentName componentName,
        int i2,
        int i3,
        ArrayList arrayList,
        int i4,
        int i5,
        int i6) {
      ArraySet arraySet = list == null ? null : new ArraySet(list);
      ShortcutPackage packageShortcutsIfExists =
          ShortcutService.this.getUserShortcutsLocked(i3).getPackageShortcutsIfExists(str2);
      if (packageShortcutsIfExists == null) {
        return;
      }
      boolean z = ShortcutService.this.canSeeAnyPinnedShortcut(str, i, i5, i6) && (i2 & 1024) != 0;
      packageShortcutsIfExists.findAll(
          arrayList,
          getFilterFromQuery(arraySet, list2, j, componentName, i2 | (z ? 2 : 0), z),
          i4,
          str,
          i,
          z);
    }

    public final Predicate getFilterFromQuery(
        final ArraySet arraySet,
        List list,
        final long j,
        final ComponentName componentName,
        int i,
        final boolean z) {
      final ArraySet arraySet2 = list == null ? null : new ArraySet(list);
      final boolean z2 = (i & 1) != 0;
      final boolean z3 = (i & 2) != 0;
      final boolean z4 = (i & 8) != 0;
      final boolean z5 = (i & 16) != 0;
      return new Predicate() { // from class:
        // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda9
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
          boolean lambda$getFilterFromQuery$1;
          lambda$getFilterFromQuery$1 =
              ShortcutService.LocalService.lambda$getFilterFromQuery$1(
                  j, arraySet, arraySet2, componentName, z2, z3, z, z4, z5, (ShortcutInfo) obj);
          return lambda$getFilterFromQuery$1;
        }
      };
    }

    public static /* synthetic */ boolean lambda$getFilterFromQuery$1(
        long j,
        ArraySet arraySet,
        ArraySet arraySet2,
        ComponentName componentName,
        boolean z,
        boolean z2,
        boolean z3,
        boolean z4,
        boolean z5,
        ShortcutInfo shortcutInfo) {
      if (shortcutInfo.getLastChangedTimestamp() < j) {
        return false;
      }
      if (arraySet != null && !arraySet.contains(shortcutInfo.getId())) {
        return false;
      }
      if (arraySet2 != null && !arraySet2.contains(shortcutInfo.getLocusId())) {
        return false;
      }
      if (componentName != null
          && shortcutInfo.getActivity() != null
          && !shortcutInfo.getActivity().equals(componentName)) {
        return false;
      }
      if (z && shortcutInfo.isDynamic()) {
        return true;
      }
      if ((z2 || z3) && shortcutInfo.isPinned()) {
        return true;
      }
      if (z4 && shortcutInfo.isDeclaredInManifest()) {
        return true;
      }
      return z5 && shortcutInfo.isCached();
    }

    public void getShortcutsAsync(
        int i,
        String str,
        long j,
        String str2,
        List list,
        List list2,
        ComponentName componentName,
        int i2,
        int i3,
        int i4,
        int i5,
        final AndroidFuture androidFuture) {
      ShortcutPackage packageShortcutsIfExists;
      final List shortcuts =
          getShortcuts(i, str, j, str2, list, list2, componentName, i2, i3, i4, i5);
      if (list == null || str2 == null || shortcuts.size() >= list.size()) {
        androidFuture.complete(shortcuts);
        return;
      }
      synchronized (ShortcutService.this.mLock) {
        packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i3).getPackageShortcutsIfExists(str2);
      }
      if (packageShortcutsIfExists == null) {
        androidFuture.complete(shortcuts);
        return;
      }
      final ArraySet arraySet = new ArraySet(list);
      ((List)
              shortcuts.stream()
                  .map(
                      new Function() { // from class:
                        // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return ((ShortcutInfo) obj).getId();
                        }
                      })
                  .collect(Collectors.toList()))
          .forEach(
              new Consumer() { // from class:
                // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  arraySet.remove((String) obj);
                }
              });
      final int i6 =
          (i2 & 4) != 0
              ? 4
              : (i2 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0 ? 11 : 27;
      packageShortcutsIfExists.getShortcutByIdsAsync(
          arraySet,
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutService.LocalService.lambda$getShortcutsAsync$3(
                  i6, shortcuts, androidFuture, (List) obj);
            }
          });
    }

    public static /* synthetic */ ShortcutInfo lambda$getShortcutsAsync$2(
        int i, ShortcutInfo shortcutInfo) {
      return shortcutInfo.clone(i);
    }

    public static /* synthetic */ void lambda$getShortcutsAsync$3(
        final int i, List list, AndroidFuture androidFuture, List list2) {
      if (list2 != null) {
        Stream map =
            list2.stream()
                .map(
                    new Function() { // from class:
                      // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda12
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        ShortcutInfo lambda$getShortcutsAsync$2;
                        lambda$getShortcutsAsync$2 =
                            ShortcutService.LocalService.lambda$getShortcutsAsync$2(
                                i, (ShortcutInfo) obj);
                        return lambda$getShortcutsAsync$2;
                      }
                    });
        Objects.requireNonNull(list);
        map.forEach(new ShortcutPackage$$ExternalSyntheticLambda13(list));
      }
      androidFuture.complete(list);
    }

    public boolean isPinnedByCaller(int i, String str, String str2, String str3, int i2) {
      boolean z;
      Preconditions.checkStringNotEmpty(str2, "packageName");
      Preconditions.checkStringNotEmpty(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        ShortcutInfo shortcutInfoLocked = getShortcutInfoLocked(i, str, str2, str3, i2, false);
        z = shortcutInfoLocked != null && shortcutInfoLocked.isPinned();
      }
      return z;
    }

    public final ShortcutInfo getShortcutInfoLocked(
        int i, String str, String str2, final String str3, int i2, boolean z) {
      Preconditions.checkStringNotEmpty(str2, "packageName");
      Preconditions.checkStringNotEmpty(str3, "shortcutId");
      ShortcutService.this.throwIfUserLockedL(i2);
      ShortcutService.this.throwIfUserLockedL(i);
      ShortcutPackage packageShortcutsIfExists =
          ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
      if (packageShortcutsIfExists == null) {
        return null;
      }
      ArrayList arrayList = new ArrayList(1);
      packageShortcutsIfExists.findAll(
          arrayList,
          new Predicate() { // from class:
            // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$getShortcutInfoLocked$4;
              lambda$getShortcutInfoLocked$4 =
                  ShortcutService.LocalService.lambda$getShortcutInfoLocked$4(
                      str3, (ShortcutInfo) obj);
              return lambda$getShortcutInfoLocked$4;
            }
          },
          0,
          str,
          i,
          z);
      if (arrayList.size() == 0) {
        return null;
      }
      return (ShortcutInfo) arrayList.get(0);
    }

    public static /* synthetic */ boolean lambda$getShortcutInfoLocked$4(
        String str, ShortcutInfo shortcutInfo) {
      return str.equals(shortcutInfo.getId());
    }

    public final void getShortcutInfoAsync(
        int i, String str, String str2, int i2, final Consumer consumer) {
      ShortcutPackage packageShortcutsIfExists;
      Preconditions.checkStringNotEmpty(str, "packageName");
      Preconditions.checkStringNotEmpty(str2, "shortcutId");
      ShortcutService.this.throwIfUserLockedL(i2);
      ShortcutService.this.throwIfUserLockedL(i);
      synchronized (ShortcutService.this.mLock) {
        packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str);
      }
      if (packageShortcutsIfExists == null) {
        consumer.accept(null);
      } else {
        packageShortcutsIfExists.getShortcutByIdsAsync(
            Collections.singleton(str2),
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda10
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.LocalService.lambda$getShortcutInfoAsync$5(consumer, (List) obj);
              }
            });
      }
    }

    public static /* synthetic */ void lambda$getShortcutInfoAsync$5(Consumer consumer, List list) {
      consumer.accept((list == null || list.isEmpty()) ? null : (ShortcutInfo) list.get(0));
    }

    public void pinShortcuts(int i, String str, String str2, List list, int i2) {
      ShortcutPackage packageShortcutsIfExists;
      ArrayList arrayList;
      List prepareChangedShortcuts;
      Preconditions.checkStringNotEmpty(str2, "packageName");
      Objects.requireNonNull(list, "shortcutIds");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutLauncher launcherShortcutsLocked =
            ShortcutService.this.getLauncherShortcutsLocked(str, i2, i);
        launcherShortcutsLocked.attemptToRestoreIfNeededAndSave();
        packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (packageShortcutsIfExists != null) {
          arrayList = new ArrayList();
          packageShortcutsIfExists.findAll(
              arrayList,
              new Predicate() { // from class:
                // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$pinShortcuts$6;
                  lambda$pinShortcuts$6 =
                      ShortcutService.LocalService.lambda$pinShortcuts$6((ShortcutInfo) obj);
                  return lambda$pinShortcuts$6;
                }
              },
              4,
              str,
              i,
              false);
        } else {
          arrayList = null;
        }
        ArraySet pinnedShortcutIds = launcherShortcutsLocked.getPinnedShortcutIds(str2, i2);
        launcherShortcutsLocked.pinShortcuts(i2, str2, list, false);
        if (pinnedShortcutIds != null && arrayList != null) {
          for (int i3 = 0; i3 < arrayList.size(); i3++) {
            pinnedShortcutIds.remove(((ShortcutInfo) arrayList.get(i3)).getId());
          }
        }
        prepareChangedShortcuts =
            ShortcutService.this.prepareChangedShortcuts(
                pinnedShortcutIds, new ArraySet(list), arrayList, packageShortcutsIfExists);
      }
      if (packageShortcutsIfExists != null) {
        ShortcutService.this.packageShortcutsChanged(
            packageShortcutsIfExists, prepareChangedShortcuts, arrayList);
      }
      ShortcutService.this.verifyStates();
    }

    public static /* synthetic */ boolean lambda$pinShortcuts$6(ShortcutInfo shortcutInfo) {
      return (!shortcutInfo.isVisibleToPublisher()
              || !shortcutInfo.isPinned()
              || shortcutInfo.isCached()
              || shortcutInfo.isDynamic()
              || shortcutInfo.isDeclaredInManifest())
          ? false
          : true;
    }

    public void cacheShortcuts(int i, String str, String str2, List list, int i2, int i3) {
      updateCachedShortcutsInternal(i, str, str2, list, i2, i3, true);
    }

    public void uncacheShortcuts(int i, String str, String str2, List list, int i2, int i3) {
      updateCachedShortcutsInternal(i, str, str2, list, i2, i3, false);
    }

    public List getShareTargets(String str, IntentFilter intentFilter, int i) {
      return ShortcutService.this.getShareTargets(str, intentFilter, i).getList();
    }

    public boolean isSharingShortcut(
        int i, String str, String str2, String str3, int i2, IntentFilter intentFilter) {
      Preconditions.checkStringNotEmpty(str, "callingPackage");
      Preconditions.checkStringNotEmpty(str2, "packageName");
      Preconditions.checkStringNotEmpty(str3, "shortcutId");
      return ShortcutService.this.isSharingShortcut(i, str, str2, str3, i2, intentFilter);
    }

    public final void updateCachedShortcutsInternal(
        int i, String str, String str2, List list, int i2, int i3, boolean z) {
      Preconditions.checkStringNotEmpty(str2, "packageName");
      Objects.requireNonNull(list, "shortcutIds");
      Preconditions.checkState((1610629120 & i3) != 0, "invalid cacheFlags");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        int size = list.size();
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (size != 0 && packageShortcutsIfExists != null) {
          ArrayList arrayList = null;
          ArrayList arrayList2 = null;
          for (int i4 = 0; i4 < size; i4++) {
            String str3 = (String) Preconditions.checkStringNotEmpty((String) list.get(i4));
            ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
            if (findShortcutById != null && z != findShortcutById.hasFlags(i3)) {
              if (z) {
                if (findShortcutById.isLongLived()) {
                  findShortcutById.addFlags(i3);
                  if (arrayList == null) {
                    arrayList = new ArrayList(1);
                  }
                  arrayList.add(findShortcutById);
                } else {
                  Log.w(
                      "ShortcutService",
                      "Only long lived shortcuts can get cached. Ignoring id "
                          + findShortcutById.getId());
                }
              } else {
                findShortcutById.clearFlags(i3);
                ShortcutInfo deleteLongLivedWithId =
                    (findShortcutById.isDynamic() || findShortcutById.isCached())
                        ? null
                        : packageShortcutsIfExists.deleteLongLivedWithId(str3, true);
                if (deleteLongLivedWithId == null) {
                  if (arrayList == null) {
                    arrayList = new ArrayList(1);
                  }
                  arrayList.add(findShortcutById);
                } else {
                  if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                  }
                  arrayList2.add(deleteLongLivedWithId);
                }
              }
            }
          }
          ShortcutService.this.packageShortcutsChanged(
              packageShortcutsIfExists, arrayList, arrayList2);
          ShortcutService.this.verifyStates();
        }
      }
    }

    public Intent[] createShortcutIntents(
        int i, String str, String str2, String str3, int i2, int i3, int i4) {
      Preconditions.checkStringNotEmpty(str2, "packageName can't be empty");
      Preconditions.checkStringNotEmpty(str3, "shortcutId can't be empty");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        boolean canSeeAnyPinnedShortcut =
            ShortcutService.this.canSeeAnyPinnedShortcut(str, i, i3, i4);
        ShortcutInfo shortcutInfoLocked =
            getShortcutInfoLocked(i, str, str2, str3, i2, canSeeAnyPinnedShortcut);
        if (shortcutInfoLocked != null
            && shortcutInfoLocked.isEnabled()
            && (shortcutInfoLocked.isAlive() || canSeeAnyPinnedShortcut)) {
          return shortcutInfoLocked.getIntents();
        }
        Log.e("ShortcutService", "Shortcut " + str3 + " does not exist or disabled");
        return null;
      }
    }

    public void createShortcutIntentsAsync(
        int i,
        String str,
        String str2,
        String str3,
        int i2,
        int i3,
        int i4,
        final AndroidFuture androidFuture) {
      Preconditions.checkStringNotEmpty(str2, "packageName can't be empty");
      Preconditions.checkStringNotEmpty(str3, "shortcutId can't be empty");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        boolean canSeeAnyPinnedShortcut =
            ShortcutService.this.canSeeAnyPinnedShortcut(str, i, i3, i4);
        ShortcutInfo shortcutInfoLocked =
            getShortcutInfoLocked(i, str, str2, str3, i2, canSeeAnyPinnedShortcut);
        if (shortcutInfoLocked != null) {
          if (shortcutInfoLocked.isEnabled()
              && (shortcutInfoLocked.isAlive() || canSeeAnyPinnedShortcut)) {
            androidFuture.complete(shortcutInfoLocked.getIntents());
            return;
          }
          Log.e("ShortcutService", "Shortcut " + str3 + " does not exist or disabled");
          androidFuture.complete((Object) null);
          return;
        }
        getShortcutInfoAsync(
            i,
            str2,
            str3,
            i2,
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda4
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.LocalService.lambda$createShortcutIntentsAsync$7(
                    androidFuture, (ShortcutInfo) obj);
              }
            });
      }
    }

    public static /* synthetic */ void lambda$createShortcutIntentsAsync$7(
        AndroidFuture androidFuture, ShortcutInfo shortcutInfo) {
      androidFuture.complete(shortcutInfo == null ? null : shortcutInfo.getIntents());
    }

    public void addListener(ShortcutServiceInternal.ShortcutChangeListener shortcutChangeListener) {
      synchronized (ShortcutService.this.mLock) {
        ArrayList arrayList = ShortcutService.this.mListeners;
        Objects.requireNonNull(shortcutChangeListener);
        arrayList.add(shortcutChangeListener);
      }
    }

    public void addShortcutChangeCallback(
        LauncherApps.ShortcutChangeCallback shortcutChangeCallback) {
      synchronized (ShortcutService.this.mLock) {
        ArrayList arrayList = ShortcutService.this.mShortcutChangeCallbacks;
        Objects.requireNonNull(shortcutChangeCallback);
        arrayList.add(shortcutChangeCallback);
      }
    }

    public int getShortcutIconResId(int i, String str, String str2, String str3, int i2) {
      Objects.requireNonNull(str, "callingPackage");
      Objects.requireNonNull(str2, "packageName");
      Objects.requireNonNull(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        int i3 = 0;
        if (packageShortcutsIfExists == null) {
          return 0;
        }
        ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
        if (findShortcutById != null && findShortcutById.hasIconResource()) {
          i3 = findShortcutById.getIconResourceId();
        }
        return i3;
      }
    }

    public String getShortcutStartingThemeResName(
        int i, String str, String str2, String str3, int i2) {
      Objects.requireNonNull(str, "callingPackage");
      Objects.requireNonNull(str2, "packageName");
      Objects.requireNonNull(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (packageShortcutsIfExists == null) {
          return null;
        }
        ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
        return findShortcutById != null ? findShortcutById.getStartingThemeResName() : null;
      }
    }

    public ParcelFileDescriptor getShortcutIconFd(
        int i, String str, String str2, String str3, int i2) {
      Objects.requireNonNull(str, "callingPackage");
      Objects.requireNonNull(str2, "packageName");
      Objects.requireNonNull(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (packageShortcutsIfExists == null) {
          return null;
        }
        ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
        if (findShortcutById == null) {
          return null;
        }
        return getShortcutIconParcelFileDescriptor(packageShortcutsIfExists, findShortcutById);
      }
    }

    public void getShortcutIconFdAsync(
        int i, String str, String str2, String str3, int i2, final AndroidFuture androidFuture) {
      Objects.requireNonNull(str, "callingPackage");
      Objects.requireNonNull(str2, "packageName");
      Objects.requireNonNull(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        final ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (packageShortcutsIfExists == null) {
          androidFuture.complete((Object) null);
          return;
        }
        ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
        if (findShortcutById != null) {
          androidFuture.complete(
              getShortcutIconParcelFileDescriptor(packageShortcutsIfExists, findShortcutById));
        } else {
          getShortcutInfoAsync(
              i,
              str2,
              str3,
              i2,
              new Consumer() { // from class:
                // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ShortcutService.LocalService.this.lambda$getShortcutIconFdAsync$8(
                      androidFuture, packageShortcutsIfExists, (ShortcutInfo) obj);
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShortcutIconFdAsync$8(
        AndroidFuture androidFuture, ShortcutPackage shortcutPackage, ShortcutInfo shortcutInfo) {
      androidFuture.complete(getShortcutIconParcelFileDescriptor(shortcutPackage, shortcutInfo));
    }

    public final ParcelFileDescriptor getShortcutIconParcelFileDescriptor(
        ShortcutPackage shortcutPackage, ShortcutInfo shortcutInfo) {
      if (shortcutPackage != null && shortcutInfo != null && shortcutInfo.hasIconFile()) {
        String bitmapPathMayWait = shortcutPackage.getBitmapPathMayWait(shortcutInfo);
        if (bitmapPathMayWait == null) {
          Slog.w("ShortcutService", "null bitmap detected in getShortcutIconFd()");
          return null;
        }
        try {
          return ParcelFileDescriptor.open(new File(bitmapPathMayWait), 268435456);
        } catch (FileNotFoundException unused) {
          Slog.e("ShortcutService", "Icon file not found: " + bitmapPathMayWait);
        }
      }
      return null;
    }

    public String getShortcutIconUri(int i, String str, String str2, String str3, int i2) {
      Objects.requireNonNull(str, "launcherPackage");
      Objects.requireNonNull(str2, "packageName");
      Objects.requireNonNull(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (packageShortcutsIfExists == null) {
          return null;
        }
        ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
        if (findShortcutById == null) {
          return null;
        }
        return getShortcutIconUriInternal(i, str, str2, findShortcutById, i2);
      }
    }

    public void getShortcutIconUriAsync(
        final int i,
        final String str,
        final String str2,
        String str3,
        final int i2,
        final AndroidFuture androidFuture) {
      Objects.requireNonNull(str, "launcherPackage");
      Objects.requireNonNull(str2, "packageName");
      Objects.requireNonNull(str3, "shortcutId");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.throwIfUserLockedL(i2);
        ShortcutService.this.throwIfUserLockedL(i);
        ShortcutService.this
            .getLauncherShortcutsLocked(str, i2, i)
            .attemptToRestoreIfNeededAndSave();
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
        if (packageShortcutsIfExists == null) {
          androidFuture.complete((Object) null);
          return;
        }
        ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
        if (findShortcutById != null) {
          androidFuture.complete(getShortcutIconUriInternal(i, str, str2, findShortcutById, i2));
        } else {
          getShortcutInfoAsync(
              i,
              str2,
              str3,
              i2,
              new Consumer() { // from class:
                // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ShortcutService.LocalService.this.lambda$getShortcutIconUriAsync$9(
                      androidFuture, i, str, str2, i2, (ShortcutInfo) obj);
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShortcutIconUriAsync$9(
        AndroidFuture androidFuture,
        int i,
        String str,
        String str2,
        int i2,
        ShortcutInfo shortcutInfo) {
      androidFuture.complete(
          shortcutInfo == null ? null : getShortcutIconUriInternal(i, str, str2, shortcutInfo, i2));
    }

    public final String getShortcutIconUriInternal(
        int i, String str, String str2, ShortcutInfo shortcutInfo, int i2) {
      if (!shortcutInfo.hasIconUri()) {
        return null;
      }
      String iconUri = shortcutInfo.getIconUri();
      if (iconUri == null) {
        Slog.w("ShortcutService", "null uri detected in getShortcutIconUri()");
        return null;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        ShortcutService.this.mUriGrantsManager.grantUriPermissionFromOwner(
            ShortcutService.this.mUriPermissionOwner,
            ShortcutService.this.mPackageManagerInternal.getPackageUid(str2, 268435456L, i2),
            str,
            Uri.parse(iconUri),
            1,
            i2,
            i);
        return iconUri;
      } catch (Exception e) {
        Slog.e("ShortcutService", "Failed to grant uri access to " + str + " for " + iconUri, e);
        return null;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean hasShortcutHostPermission(int i, String str, int i2, int i3) {
      return ShortcutService.this.hasShortcutHostPermission(str, i, i2, i3);
    }

    public void setShortcutHostPackage(String str, String str2, int i) {
      ShortcutService.this.setShortcutHostPackage(str, str2, i);
    }

    public boolean requestPinAppWidget(
        String str,
        AppWidgetProviderInfo appWidgetProviderInfo,
        Bundle bundle,
        IntentSender intentSender,
        int i) {
      Objects.requireNonNull(appWidgetProviderInfo);
      return ShortcutService.this.requestPinItem(
          str, i, null, appWidgetProviderInfo, bundle, intentSender);
    }

    public boolean isRequestPinItemSupported(int i, int i2) {
      return ShortcutService.this.isRequestPinItemSupported(i, i2);
    }

    public boolean isForegroundDefaultLauncher(String str, int i) {
      Objects.requireNonNull(str);
      String defaultLauncher = ShortcutService.this.getDefaultLauncher(UserHandle.getUserId(i));
      if (defaultLauncher == null || !str.equals(defaultLauncher)) {
        return false;
      }
      synchronized (ShortcutService.this.mLock) {
        return ShortcutService.this.isUidForegroundLocked(i);
      }
    }

    public void shutdown() {
      try {
        ConcurrentUtils.newFixedThreadPool(1, "shortcuts-writes", 0)
            .submit(
                new Callable() { // from class:
                  // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda3
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    Boolean lambda$shutdown$10;
                    lambda$shutdown$10 = ShortcutService.LocalService.this.lambda$shutdown$10();
                    return lambda$shutdown$10;
                  }
                })
            .get(20L, TimeUnit.SECONDS);
      } catch (Exception unused) {
        PmLog.logCriticalInfoAndLogcat("Timeout while saving shortcuts");
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$shutdown$10() {
      synchronized (ShortcutService.this.mLock) {
        if (ShortcutService.this.mHandler.hasCallbacks(ShortcutService.this.mSaveDirtyInfoRunner)) {
          ShortcutService.this.mHandler.removeCallbacks(ShortcutService.this.mSaveDirtyInfoRunner);
          ShortcutService.this.forEachLoadedUserLocked(
              new ShortcutService$7$$ExternalSyntheticLambda0());
          ShortcutService.this.saveDirtyInfo();
        }
        ShortcutService.this.mShutdown.set(true);
      }
      return Boolean.TRUE;
    }
  }

  public void handleLocaleChanged() {
    handleLocaleChanged(false);
  }

  public void handleLocaleChanged(final boolean z) {
    scheduleSaveBaseState();
    synchronized (this.mLock) {
      long injectClearCallingIdentity = injectClearCallingIdentity();
      try {
        forEachLoadedUserLocked(
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda6
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((ShortcutUser) obj).detectLocaleChange(z);
              }
            });
      } finally {
        injectRestoreCallingIdentity(injectClearCallingIdentity);
      }
    }
  }

  public void checkPackageChanges(int i) {
    if (injectIsSafeModeEnabled()) {
      Slog.i("ShortcutService", "Safe mode, skipping checkPackageChanges()");
      return;
    }
    long statStartTime = getStatStartTime();
    try {
      final ArrayList arrayList = new ArrayList();
      synchronized (this.mLock) {
        ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        userShortcutsLocked.forAllPackageItems(
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda27
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.this.lambda$checkPackageChanges$15(
                    arrayList, (ShortcutPackageItem) obj);
              }
            });
        if (arrayList.size() > 0) {
          for (int size = arrayList.size() - 1; size >= 0; size--) {
            UserPackage userPackage = (UserPackage) arrayList.get(size);
            cleanUpPackageLocked(userPackage.packageName, i, userPackage.userId, false);
          }
        }
        rescanUpdatedPackagesLocked(i, userShortcutsLocked.getLastAppScanTime());
      }
      logDurationStat(8, statStartTime);
      verifyStates();
    } catch (Throwable th) {
      logDurationStat(8, statStartTime);
      throw th;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$checkPackageChanges$15(
      ArrayList arrayList, ShortcutPackageItem shortcutPackageItem) {
    if (shortcutPackageItem.getPackageInfo().isShadow()
        || isPackageInstalled(
            shortcutPackageItem.getPackageName(), shortcutPackageItem.getPackageUserId())) {
      return;
    }
    arrayList.add(
        UserPackage.of(
            shortcutPackageItem.getPackageUserId(), shortcutPackageItem.getPackageName()));
  }

  public final void rescanUpdatedPackagesLocked(final int i, long j) {
    final ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
    long injectCurrentTimeMillis = injectCurrentTimeMillis();
    forUpdatedPackages(
        i,
        j,
        !injectBuildFingerprint().equals(userShortcutsLocked.getLastAppScanOsFingerprint()),
        new Consumer() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda24
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ShortcutService.this.lambda$rescanUpdatedPackagesLocked$16(
                userShortcutsLocked, i, (ApplicationInfo) obj);
          }
        });
    userShortcutsLocked.setLastAppScanTime(injectCurrentTimeMillis);
    userShortcutsLocked.setLastAppScanOsFingerprint(injectBuildFingerprint());
    scheduleSaveUser(i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$rescanUpdatedPackagesLocked$16(
      ShortcutUser shortcutUser, int i, ApplicationInfo applicationInfo) {
    shortcutUser.attemptToRestoreIfNeededAndSave(this, applicationInfo.packageName, i);
    shortcutUser.rescanPackageIfNeeded(applicationInfo.packageName, true);
  }

  public final void handlePackageAdded(String str, int i) {
    synchronized (this.mLock) {
      ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
      userShortcutsLocked.attemptToRestoreIfNeededAndSave(this, str, i);
      userShortcutsLocked.rescanPackageIfNeeded(str, true);
    }
    verifyStates();
  }

  public final void handlePackageUpdateFinished(String str, int i) {
    synchronized (this.mLock) {
      ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
      userShortcutsLocked.attemptToRestoreIfNeededAndSave(this, str, i);
      if (isPackageInstalled(str, i)) {
        userShortcutsLocked.rescanPackageIfNeeded(str, true);
      }
    }
    verifyStates();
  }

  public final void handlePackageRemoved(String str, int i) {
    cleanUpPackageForAllLoadedUsers(str, i, false);
    verifyStates();
  }

  public final void handlePackageDataCleared(String str, int i) {
    cleanUpPackageForAllLoadedUsers(str, i, true);
    verifyStates();
  }

  public final void handlePackageChanged(String str, int i) {
    if (!isPackageInstalled(str, i)) {
      handlePackageRemoved(str, i);
      return;
    }
    synchronized (this.mLock) {
      getUserShortcutsLocked(i).rescanPackageIfNeeded(str, true);
    }
    verifyStates();
  }

  public final PackageInfo getPackageInfoWithSignatures(String str, int i) {
    return getPackageInfo(str, i, true);
  }

  public final PackageInfo getPackageInfo(String str, int i) {
    return getPackageInfo(str, i, false);
  }

  public int injectGetPackageUid(String str, int i) {
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        return this.mIPackageManager.getPackageUid(str, 537666048L, i);
      } catch (RemoteException e) {
        Slog.wtf("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        return -1;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
    }
  }

  public final PackageInfo getPackageInfo(String str, int i, boolean z) {
    return isInstalledOrNull(injectPackageInfoWithUninstalled(str, i, z));
  }

  public final PackageInfo getPackageInfoAsUser(String str, int i) {
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        return this.mIPackageManager.getPackageInfo(str, 0L, i);
      } catch (RemoteException e) {
        Slog.i("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        return null;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
    }
  }

  public PackageInfo injectPackageInfoWithUninstalled(String str, int i, boolean z) {
    long statStartTime = getStatStartTime();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        PackageInfo packageInfo =
            this.mIPackageManager.getPackageInfo(str, (z ? 134217728 : 0) | 537666048, i);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        logDurationStat(z ? 2 : 1, statStartTime);
        return packageInfo;
      } catch (RemoteException e) {
        Slog.wtf("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        logDurationStat(z ? 2 : 1, statStartTime);
        return null;
      }
    } catch (Throwable th) {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      logDurationStat(z ? 2 : 1, statStartTime);
      throw th;
    }
  }

  public final ApplicationInfo getApplicationInfo(String str, int i) {
    return isInstalledOrNull(injectApplicationInfoWithUninstalled(str, i));
  }

  public ApplicationInfo injectApplicationInfoWithUninstalled(String str, int i) {
    long statStartTime = getStatStartTime();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        return this.mIPackageManager.getApplicationInfo(str, 537666048L, i);
      } catch (RemoteException e) {
        Slog.wtf("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        logDurationStat(3, statStartTime);
        return null;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      logDurationStat(3, statStartTime);
    }
  }

  public final ActivityInfo getActivityInfoWithMetadata(ComponentName componentName, int i) {
    return isInstalledOrNull(injectGetActivityInfoWithMetadataWithUninstalled(componentName, i));
  }

  public ActivityInfo injectGetActivityInfoWithMetadataWithUninstalled(
      ComponentName componentName, int i) {
    long statStartTime = getStatStartTime();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        return this.mIPackageManager.getActivityInfo(componentName, 537666176L, i);
      } catch (RemoteException e) {
        Slog.wtf("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        logDurationStat(6, statStartTime);
        return null;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      logDurationStat(6, statStartTime);
    }
  }

  public final List getInstalledPackages(int i) {
    long statStartTime = getStatStartTime();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        List injectGetPackagesWithUninstalled = injectGetPackagesWithUninstalled(i);
        injectGetPackagesWithUninstalled.removeIf(PACKAGE_NOT_INSTALLED);
        return injectGetPackagesWithUninstalled;
      } catch (RemoteException e) {
        Slog.wtf("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        logDurationStat(7, statStartTime);
        return null;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      logDurationStat(7, statStartTime);
    }
  }

  public List injectGetPackagesWithUninstalled(int i) {
    ParceledListSlice installedPackages = this.mIPackageManager.getInstalledPackages(537666048L, i);
    if (installedPackages == null) {
      return Collections.emptyList();
    }
    return installedPackages.getList();
  }

  public final void forUpdatedPackages(int i, long j, boolean z, Consumer consumer) {
    List installedPackages = getInstalledPackages(i);
    for (int size = installedPackages.size() - 1; size >= 0; size--) {
      PackageInfo packageInfo = (PackageInfo) installedPackages.get(size);
      if (z || packageInfo.lastUpdateTime >= j) {
        consumer.accept(packageInfo.applicationInfo);
      }
    }
  }

  public final boolean isApplicationFlagSet(String str, int i, int i2) {
    ApplicationInfo injectApplicationInfoWithUninstalled =
        injectApplicationInfoWithUninstalled(str, i);
    return injectApplicationInfoWithUninstalled != null
        && (injectApplicationInfoWithUninstalled.flags & i2) == i2;
  }

  public final boolean isEnabled(ActivityInfo activityInfo, int i) {
    if (activityInfo == null) {
      return false;
    }
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        int componentEnabledSetting =
            this.mIPackageManager.getComponentEnabledSetting(activityInfo.getComponentName(), i);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        return (componentEnabledSetting == 0 && activityInfo.enabled)
            || componentEnabledSetting == 1;
      } catch (RemoteException e) {
        Slog.wtf("ShortcutService", "RemoteException", e);
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        return false;
      }
    } catch (Throwable th) {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      throw th;
    }
  }

  public static boolean isSystem(ActivityInfo activityInfo) {
    return activityInfo != null && isSystem(activityInfo.applicationInfo);
  }

  public static boolean isSystem(ApplicationInfo applicationInfo) {
    return (applicationInfo == null || (applicationInfo.flags & 129) == 0) ? false : true;
  }

  public static boolean isInstalled(ApplicationInfo applicationInfo) {
    return applicationInfo != null
        && (applicationInfo.enabled || sIsEmergencyMode.get())
        && (applicationInfo.flags & 8388608) != 0;
  }

  public static boolean isEphemeralApp(ApplicationInfo applicationInfo) {
    return applicationInfo != null && applicationInfo.isInstantApp();
  }

  public static boolean isInstalled(PackageInfo packageInfo) {
    return packageInfo != null && isInstalled(packageInfo.applicationInfo);
  }

  public static boolean isInstalled(ActivityInfo activityInfo) {
    return activityInfo != null && isInstalled(activityInfo.applicationInfo);
  }

  public static ApplicationInfo isInstalledOrNull(ApplicationInfo applicationInfo) {
    if (isInstalled(applicationInfo)) {
      return applicationInfo;
    }
    return null;
  }

  public static PackageInfo isInstalledOrNull(PackageInfo packageInfo) {
    if (isInstalled(packageInfo)) {
      return packageInfo;
    }
    return null;
  }

  public static ActivityInfo isInstalledOrNull(ActivityInfo activityInfo) {
    if (isInstalled(activityInfo)) {
      return activityInfo;
    }
    return null;
  }

  public boolean isPackageInstalled(String str, int i) {
    return getApplicationInfo(str, i) != null;
  }

  public boolean isEphemeralApp(String str, int i) {
    return isEphemeralApp(getApplicationInfo(str, i));
  }

  public XmlResourceParser injectXmlMetaData(ActivityInfo activityInfo, String str) {
    return activityInfo.loadXmlMetaData(this.mContext.getPackageManager(), str);
  }

  public Resources injectGetResourcesForApplicationAsUser(String str, int i) {
    long statStartTime = getStatStartTime();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      try {
        return this.mContext
            .createContextAsUser(UserHandle.of(i), 0)
            .getPackageManager()
            .getResourcesForApplication(str);
      } catch (PackageManager.NameNotFoundException unused) {
        Slog.e("ShortcutService", "Resources of package " + str + " for user " + i + " not found");
        injectRestoreCallingIdentity(injectClearCallingIdentity);
        logDurationStat(9, statStartTime);
        return null;
      }
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      logDurationStat(9, statStartTime);
    }
  }

  public final Intent getMainActivityIntent() {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.LAUNCHER");
    return intent;
  }

  public List queryActivities(Intent intent, String str, ComponentName componentName, int i) {
    Objects.requireNonNull(str);
    intent.setPackage(str);
    if (componentName != null) {
      intent.setComponent(componentName);
    }
    return queryActivities(intent, i, true);
  }

  public List queryActivities(Intent intent, final int i, boolean z) {
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      List queryIntentActivitiesAsUser =
          this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 537666048, i);
      if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() == 0) {
        return EMPTY_RESOLVE_INFO;
      }
      queryIntentActivitiesAsUser.removeIf(ACTIVITY_NOT_INSTALLED);
      queryIntentActivitiesAsUser.removeIf(
          new Predicate() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$queryActivities$17;
              lambda$queryActivities$17 =
                  ShortcutService.this.lambda$queryActivities$17(i, (ResolveInfo) obj);
              return lambda$queryActivities$17;
            }
          });
      if (z) {
        queryIntentActivitiesAsUser.removeIf(ACTIVITY_NOT_EXPORTED);
      }
      return queryIntentActivitiesAsUser;
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$queryActivities$17(int i, ResolveInfo resolveInfo) {
    ActivityInfo activityInfo = resolveInfo.activityInfo;
    return (isSystem(activityInfo) || isEnabled(activityInfo, i)) ? false : true;
  }

  public ComponentName injectGetDefaultMainActivity(String str, int i) {
    long statStartTime = getStatStartTime();
    try {
      ComponentName componentName = null;
      List queryActivities = queryActivities(getMainActivityIntent(), str, null, i);
      if (queryActivities.size() != 0) {
        componentName = ((ResolveInfo) queryActivities.get(0)).activityInfo.getComponentName();
      }
      return componentName;
    } finally {
      logDurationStat(11, statStartTime);
    }
  }

  public boolean injectIsMainActivity(ComponentName componentName, int i) {
    if (sIsEmergencyMode.get()) {
      return true;
    }
    long statStartTime = getStatStartTime();
    try {
      if (componentName == null) {
        wtf("null activity detected");
        return false;
      }
      if ("android.__dummy__".equals(componentName.getClassName())) {
        return true;
      }
      return queryActivities(
                  getMainActivityIntent(), componentName.getPackageName(), componentName, i)
              .size()
          > 0;
    } finally {
      logDurationStat(12, statStartTime);
    }
  }

  public ComponentName getDummyMainActivity(String str) {
    return new ComponentName(str, "android.__dummy__");
  }

  public boolean isDummyMainActivity(ComponentName componentName) {
    return componentName != null && "android.__dummy__".equals(componentName.getClassName());
  }

  public List injectGetMainActivities(String str, int i) {
    long statStartTime = getStatStartTime();
    try {
      return queryActivities(getMainActivityIntent(), str, null, i);
    } finally {
      logDurationStat(12, statStartTime);
    }
  }

  public boolean injectIsActivityEnabledAndExported(ComponentName componentName, int i) {
    long statStartTime = getStatStartTime();
    try {
      return queryActivities(new Intent(), componentName.getPackageName(), componentName, i).size()
          > 0;
    } finally {
      logDurationStat(13, statStartTime);
    }
  }

  public ComponentName injectGetPinConfirmationActivity(String str, int i, int i2) {
    Objects.requireNonNull(str);
    Iterator it =
        queryActivities(
                new Intent(
                        i2 == 1
                            ? "android.content.pm.action.CONFIRM_PIN_SHORTCUT"
                            : "android.content.pm.action.CONFIRM_PIN_APPWIDGET")
                    .setPackage(str),
                i,
                false)
            .iterator();
    if (it.hasNext()) {
      return ((ResolveInfo) it.next()).activityInfo.getComponentName();
    }
    return null;
  }

  public boolean injectIsSafeModeEnabled() {
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      boolean isSafeModeEnabled =
          IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSafeModeEnabled();
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      return isSafeModeEnabled;
    } catch (RemoteException unused) {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      return false;
    } catch (Throwable th) {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
      throw th;
    }
  }

  public int getParentOrSelfUserId(int i) {
    return this.mUserManagerInternal.getProfileParentId(i);
  }

  public void injectSendIntentSender(IntentSender intentSender, Intent intent) {
    if (intentSender == null) {
      return;
    }
    try {
      intentSender.sendIntent(
          this.mContext,
          0,
          intent,
          null,
          null,
          null,
          ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(2).toBundle());
    } catch (IntentSender.SendIntentException e) {
      Slog.w("ShortcutService", "sendIntent failed().", e);
    }
  }

  public boolean shouldBackupApp(String str, int i) {
    return isApplicationFlagSet(str, i, 32768);
  }

  public static boolean shouldBackupApp(PackageInfo packageInfo) {
    return (packageInfo.applicationInfo.flags & 32768) != 0;
  }

  public byte[] getBackupPayload(int i) {
    enforceSystem();
    synchronized (this.mLock) {
      if (!isUserUnlockedL(i)) {
        wtf("Can't backup: user " + i + " is locked or not running");
        return null;
      }
      ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
      if (userShortcutsLocked == null) {
        wtf("Can't backup: user not found: id=" + i);
        return null;
      }
      userShortcutsLocked.forAllPackageItems(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((ShortcutPackageItem) obj).refreshPackageSignatureAndSave();
            }
          });
      userShortcutsLocked.forAllPackages(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((ShortcutPackage) obj).rescanPackageIfNeeded(false, true);
            }
          });
      userShortcutsLocked.forAllLaunchers(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((ShortcutLauncher) obj).ensurePackageInfo();
            }
          });
      scheduleSaveUser(i);
      saveDirtyInfo();
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(32768);
      try {
        saveUserInternalLocked(i, byteArrayOutputStream, true);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.mShortcutDumpFiles.save("backup-1-payload.txt", byteArray);
        return byteArray;
      } catch (IOException | XmlPullParserException e) {
        Slog.w("ShortcutService", "Backup failed.", e);
        return null;
      }
    }
  }

  public void applyRestore(byte[] bArr, int i) {
    enforceSystem();
    synchronized (this.mLock) {
      if (!isUserUnlockedL(i)) {
        wtf("Can't restore: user " + i + " is locked or not running");
        return;
      }
      this.mShortcutDumpFiles.save(
          "restore-0-start.txt",
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutService.this.lambda$applyRestore$21((PrintWriter) obj);
            }
          });
      this.mShortcutDumpFiles.save("restore-1-payload.xml", bArr);
      try {
        ShortcutUser loadUserInternal = loadUserInternal(i, new ByteArrayInputStream(bArr), true);
        this.mShortcutDumpFiles.save(
            "restore-2.txt",
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda11
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.this.dumpInner((PrintWriter) obj);
              }
            });
        getUserShortcutsLocked(i).mergeRestoredFile(loadUserInternal);
        this.mShortcutDumpFiles.save(
            "restore-3.txt",
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda11
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.this.dumpInner((PrintWriter) obj);
              }
            });
        rescanUpdatedPackagesLocked(i, 0L);
        this.mShortcutDumpFiles.save(
            "restore-4.txt",
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda11
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.this.dumpInner((PrintWriter) obj);
              }
            });
        this.mShortcutDumpFiles.save(
            "restore-5-finish.txt",
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda12
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.this.lambda$applyRestore$22((PrintWriter) obj);
              }
            });
        saveUser(i);
      } catch (InvalidFileFormatException | IOException | XmlPullParserException e) {
        Slog.w("ShortcutService", "Restoration failed.", e);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyRestore$21(PrintWriter printWriter) {
    printWriter.print("Start time: ");
    dumpCurrentTime(printWriter);
    printWriter.println();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyRestore$22(PrintWriter printWriter) {
    printWriter.print("Finish time: ");
    dumpCurrentTime(printWriter);
    printWriter.println();
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, "ShortcutService", printWriter)) {
      dumpNoCheck(fileDescriptor, printWriter, strArr);
    }
  }

  public void dumpNoCheck(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    DumpFilter parseDumpArgs = parseDumpArgs(strArr);
    if (parseDumpArgs.shouldDumpCheckIn()) {
      dumpCheckin(printWriter, parseDumpArgs.shouldCheckInClear());
      return;
    }
    if (parseDumpArgs.shouldDumpMain()) {
      dumpInner(printWriter, parseDumpArgs);
      printWriter.println();
    }
    if (parseDumpArgs.shouldDumpUid()) {
      dumpUid(printWriter);
      printWriter.println();
    }
    if (parseDumpArgs.shouldDumpFiles()) {
      dumpDumpFiles(printWriter);
      printWriter.println();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:42:0x00ff, code lost:

     if (r2 >= r6.length) goto L93;
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x0101, code lost:

     r0.addPackage(r6[r2]);
     r2 = r2 + 1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:0x010a, code lost:

     return r0;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static DumpFilter parseDumpArgs(String[] strArr) {
    DumpFilter dumpFilter = new DumpFilter();
    if (strArr != null) {
      int i = 0;
      while (true) {
        if (i >= strArr.length) {
          break;
        }
        int i2 = i + 1;
        String str = strArr[i];
        if ("-c".equals(str)) {
          dumpFilter.setDumpCheckIn(true);
        } else if ("--checkin".equals(str)) {
          dumpFilter.setDumpCheckIn(true);
          dumpFilter.setCheckInClear(true);
        } else if ("-a".equals(str) || "--all".equals(str)) {
          dumpFilter.setDumpUid(true);
          dumpFilter.setDumpFiles(true);
        } else if ("-u".equals(str) || "--uid".equals(str)) {
          dumpFilter.setDumpUid(true);
        } else if ("-f".equals(str) || "--files".equals(str)) {
          dumpFilter.setDumpFiles(true);
        } else if ("-n".equals(str) || "--no-main".equals(str)) {
          dumpFilter.setDumpMain(false);
        } else if ("--user".equals(str)) {
          if (i2 >= strArr.length) {
            throw new IllegalArgumentException("Missing user ID for --user");
          }
          i = i2 + 1;
          try {
            dumpFilter.addUser(Integer.parseInt(strArr[i2]));
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID", e);
          }
        } else if ("-p".equals(str) || "--package".equals(str)) {
          if (i2 >= strArr.length) {
            throw new IllegalArgumentException("Missing package name for --package");
          }
          i = i2 + 1;
          dumpFilter.addPackageRegex(strArr[i2]);
          dumpFilter.setDumpDetails(false);
        } else {
          if (str.startsWith(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
            throw new IllegalArgumentException("Unknown option " + str);
          }
          i = i2;
        }
        i = i2;
      }
    } else {
      return dumpFilter;
    }
  }

  public class DumpFilter {
    public boolean mDumpCheckIn = false;
    public boolean mCheckInClear = false;
    public boolean mDumpMain = true;
    public boolean mDumpUid = false;
    public boolean mDumpFiles = false;
    public boolean mDumpDetails = true;
    public List mPackagePatterns = new ArrayList();
    public List mUsers = new ArrayList();

    public void addPackageRegex(String str) {
      this.mPackagePatterns.add(Pattern.compile(str));
    }

    public void addPackage(String str) {
      addPackageRegex(Pattern.quote(str));
    }

    public void addUser(int i) {
      this.mUsers.add(Integer.valueOf(i));
    }

    public boolean isPackageMatch(String str) {
      if (this.mPackagePatterns.size() == 0) {
        return true;
      }
      for (int i = 0; i < this.mPackagePatterns.size(); i++) {
        if (((Pattern) this.mPackagePatterns.get(i)).matcher(str).find()) {
          return true;
        }
      }
      return false;
    }

    public boolean isUserMatch(int i) {
      if (this.mUsers.size() == 0) {
        return true;
      }
      for (int i2 = 0; i2 < this.mUsers.size(); i2++) {
        if (((Integer) this.mUsers.get(i2)).intValue() == i) {
          return true;
        }
      }
      return false;
    }

    public boolean shouldDumpCheckIn() {
      return this.mDumpCheckIn;
    }

    public void setDumpCheckIn(boolean z) {
      this.mDumpCheckIn = z;
    }

    public boolean shouldCheckInClear() {
      return this.mCheckInClear;
    }

    public void setCheckInClear(boolean z) {
      this.mCheckInClear = z;
    }

    public boolean shouldDumpMain() {
      return this.mDumpMain;
    }

    public void setDumpMain(boolean z) {
      this.mDumpMain = z;
    }

    public boolean shouldDumpUid() {
      return this.mDumpUid;
    }

    public void setDumpUid(boolean z) {
      this.mDumpUid = z;
    }

    public boolean shouldDumpFiles() {
      return this.mDumpFiles;
    }

    public void setDumpFiles(boolean z) {
      this.mDumpFiles = z;
    }

    public boolean shouldDumpDetails() {
      return this.mDumpDetails;
    }

    public void setDumpDetails(boolean z) {
      this.mDumpDetails = z;
    }
  }

  public final void dumpInner(PrintWriter printWriter) {
    dumpInner(printWriter, new DumpFilter());
  }

  public final void dumpInner(PrintWriter printWriter, DumpFilter dumpFilter) {
    synchronized (this.mLock) {
      if (dumpFilter.shouldDumpDetails()) {
        long injectCurrentTimeMillis = injectCurrentTimeMillis();
        printWriter.print("Now: [");
        printWriter.print(injectCurrentTimeMillis);
        printWriter.print("] ");
        printWriter.print(formatTime(injectCurrentTimeMillis));
        printWriter.print("  Raw last reset: [");
        printWriter.print(this.mRawLastResetTime.get());
        printWriter.print("] ");
        printWriter.print(formatTime(this.mRawLastResetTime.get()));
        long lastResetTimeLocked = getLastResetTimeLocked();
        printWriter.print("  Last reset: [");
        printWriter.print(lastResetTimeLocked);
        printWriter.print("] ");
        printWriter.print(formatTime(lastResetTimeLocked));
        long nextResetTimeLocked = getNextResetTimeLocked();
        printWriter.print("  Next reset: [");
        printWriter.print(nextResetTimeLocked);
        printWriter.print("] ");
        printWriter.print(formatTime(nextResetTimeLocked));
        printWriter.println();
        printWriter.println();
        printWriter.print("  Config:");
        printWriter.print("    Max icon dim: ");
        printWriter.println(this.mMaxIconDimension);
        printWriter.print("    Icon format: ");
        printWriter.println(this.mIconPersistFormat);
        printWriter.print("    Icon quality: ");
        printWriter.println(this.mIconPersistQuality);
        printWriter.print("    saveDelayMillis: ");
        printWriter.println(this.mSaveDelayMillis);
        printWriter.print("    resetInterval: ");
        printWriter.println(this.mResetInterval);
        printWriter.print("    maxUpdatesPerInterval: ");
        printWriter.println(this.mMaxUpdatesPerInterval);
        printWriter.print("    maxShortcutsPerActivity: ");
        printWriter.println(this.mMaxShortcuts);
        printWriter.println();
        this.mStatLogger.dump(printWriter, "  ");
        synchronized (this.mWtfLock) {
          printWriter.println();
          printWriter.print("  #Failures: ");
          printWriter.println(this.mWtfCount);
          if (this.mLastWtfStacktrace != null) {
            printWriter.print("  Last failure stack trace: ");
            printWriter.println(Log.getStackTraceString(this.mLastWtfStacktrace));
          }
        }
        printWriter.println();
      }
      for (int i = 0; i < this.mUsers.size(); i++) {
        ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.valueAt(i);
        if (dumpFilter.isUserMatch(shortcutUser.getUserId())) {
          shortcutUser.dump(printWriter, "  ", dumpFilter);
          printWriter.println();
        }
      }
      for (int i2 = 0; i2 < this.mShortcutNonPersistentUsers.size(); i2++) {
        ShortcutNonPersistentUser shortcutNonPersistentUser =
            (ShortcutNonPersistentUser) this.mShortcutNonPersistentUsers.valueAt(i2);
        if (dumpFilter.isUserMatch(shortcutNonPersistentUser.getUserId())) {
          shortcutNonPersistentUser.dump(printWriter, "  ", dumpFilter);
          printWriter.println();
        }
      }
    }
  }

  public final void dumpUid(PrintWriter printWriter) {
    synchronized (this.mLock) {
      printWriter.println("** SHORTCUT MANAGER UID STATES (dumpsys shortcut -n -u)");
      for (int i = 0; i < this.mUidState.size(); i++) {
        int keyAt = this.mUidState.keyAt(i);
        int valueAt = this.mUidState.valueAt(i);
        printWriter.print("    UID=");
        printWriter.print(keyAt);
        printWriter.print(" state=");
        printWriter.print(valueAt);
        if (isProcessStateForeground(valueAt)) {
          printWriter.print("  [FG]");
        }
        printWriter.print("  last FG=");
        printWriter.print(this.mUidLastForegroundElapsedTime.get(keyAt));
        printWriter.println();
      }
    }
  }

  public static String formatTime(long j) {
    return TimeMigrationUtils.formatMillisWithFixedFormat(j);
  }

  public final void dumpCurrentTime(PrintWriter printWriter) {
    printWriter.print(formatTime(injectCurrentTimeMillis()));
  }

  public final void dumpCheckin(PrintWriter printWriter, boolean z) {
    synchronized (this.mLock) {
      try {
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.mUsers.size(); i++) {
          jSONArray.put(((ShortcutUser) this.mUsers.valueAt(i)).dumpCheckin(z));
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("shortcut", jSONArray);
        jSONObject.put("lowRam", injectIsLowRamDevice());
        jSONObject.put("iconSize", this.mMaxIconDimension);
        printWriter.println(jSONObject.toString(1));
      } catch (JSONException e) {
        Slog.e("ShortcutService", "Unable to write in json", e);
      }
    }
  }

  public final void dumpDumpFiles(PrintWriter printWriter) {
    synchronized (this.mLock) {
      printWriter.println("** SHORTCUT MANAGER FILES (dumpsys shortcut -n -f)");
      this.mShortcutDumpFiles.dumpAll(printWriter);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    enforceShell();
    long injectClearCallingIdentity = injectClearCallingIdentity();
    try {
      resultReceiver.send(
          new MyShellCommand()
              .exec(
                  this,
                  fileDescriptor,
                  fileDescriptor2,
                  fileDescriptor3,
                  strArr,
                  shellCallback,
                  resultReceiver),
          null);
    } finally {
      injectRestoreCallingIdentity(injectClearCallingIdentity);
    }
  }

  class CommandException extends Exception {
    public CommandException(String str) {
      super(str);
    }
  }

  public class MyShellCommand extends ShellCommand {
    public int mShortcutMatchFlags;
    public int mUserId;

    public MyShellCommand() {
      this.mUserId = 0;
      this.mShortcutMatchFlags = 15;
    }

    public final void parseOptionsLocked(boolean z) {
      while (true) {
        String nextOption = getNextOption();
        if (nextOption == null) {
          return;
        }
        if (!nextOption.equals("--flags")) {
          if (!nextOption.equals("--user")) {
            throw new CommandException("Unknown option: " + nextOption);
          }
          if (z) {
            int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
            this.mUserId = parseUserArg;
            if (!ShortcutService.this.isUserUnlockedL(parseUserArg)) {
              throw new CommandException("User " + this.mUserId + " is not running or locked");
            }
          }
        }
        this.mShortcutMatchFlags = Integer.parseInt(getNextArgRequired());
      }
    }

    public int onCommand(String str) {
      char c;
      if (str == null) {
        return handleDefaultCommands(str);
      }
      PrintWriter outPrintWriter = getOutPrintWriter();
      try {
        switch (str.hashCode()) {
          case -1610733672:
            if (str.equals("has-shortcut-access")) {
              c = '\t';
              break;
            }
            c = 65535;
            break;
          case -1117067818:
            if (str.equals("verify-states")) {
              c = '\b';
              break;
            }
            c = 65535;
            break;
          case -749565587:
            if (str.equals("clear-shortcuts")) {
              c = 6;
              break;
            }
            c = 65535;
            break;
          case -276993226:
            if (str.equals("get-shortcuts")) {
              c = 7;
              break;
            }
            c = 65535;
            break;
          case -139706031:
            if (str.equals("reset-all-throttling")) {
              c = 1;
              break;
            }
            c = 65535;
            break;
          case -76794781:
            if (str.equals("override-config")) {
              c = 2;
              break;
            }
            c = 65535;
            break;
          case 188791973:
            if (str.equals("reset-throttling")) {
              c = 0;
              break;
            }
            c = 65535;
            break;
          case 1190495043:
            if (str.equals("get-default-launcher")) {
              c = 4;
              break;
            }
            c = 65535;
            break;
          case 1411888601:
            if (str.equals("unload-user")) {
              c = 5;
              break;
            }
            c = 65535;
            break;
          case 1964247424:
            if (str.equals("reset-config")) {
              c = 3;
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
            handleResetThrottling();
            break;
          case 1:
            handleResetAllThrottling();
            break;
          case 2:
            handleOverrideConfig();
            break;
          case 3:
            handleResetConfig();
            break;
          case 4:
            handleGetDefaultLauncher();
            break;
          case 5:
            handleUnloadUser();
            break;
          case 6:
            handleClearShortcuts();
            break;
          case 7:
            handleGetShortcuts();
            break;
          case '\b':
            handleVerifyStates();
            break;
          case '\t':
            handleHasShortcutAccess();
            break;
          default:
            return handleDefaultCommands(str);
        }
        outPrintWriter.println("Success");
        return 0;
      } catch (CommandException e) {
        outPrintWriter.println("Error: " + e.getMessage());
        return 1;
      }
    }

    public void onHelp() {
      PrintWriter outPrintWriter = getOutPrintWriter();
      outPrintWriter.println("Usage: cmd shortcut COMMAND [options ...]");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut reset-throttling [--user USER_ID]");
      outPrintWriter.println("    Reset throttling for all packages and users");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut reset-all-throttling");
      outPrintWriter.println("    Reset the throttling state for all users");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut override-config CONFIG");
      outPrintWriter.println("    Override the configuration for testing (will last until reboot)");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut reset-config");
      outPrintWriter.println("    Reset the configuration set with \"update-config\"");
      outPrintWriter.println();
      outPrintWriter.println("[Deprecated] cmd shortcut get-default-launcher [--user USER_ID]");
      outPrintWriter.println("    Show the default launcher");
      outPrintWriter.println(
          "    Note: This command is deprecated. Callers should query the default launcher from"
              + " RoleManager instead.");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut unload-user [--user USER_ID]");
      outPrintWriter.println("    Unload a user from the memory");
      outPrintWriter.println("    (This should not affect any observable behavior)");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut clear-shortcuts [--user USER_ID] PACKAGE");
      outPrintWriter.println("    Remove all shortcuts from a package, including pinned shortcuts");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut get-shortcuts [--user USER_ID] [--flags FLAGS] PACKAGE");
      outPrintWriter.println("    Show the shortcuts for a package that match the given flags");
      outPrintWriter.println();
      outPrintWriter.println("cmd shortcut has-shortcut-access [--user USER_ID] PACKAGE");
      outPrintWriter.println(
          "    Prints \"true\" if the package can access shortcuts, \"false\" otherwise");
      outPrintWriter.println();
    }

    public final void handleResetThrottling() {
      synchronized (ShortcutService.this.mLock) {
        parseOptionsLocked(true);
        Slog.i("ShellCommand", "cmd: handleResetThrottling: user=" + this.mUserId);
        ShortcutService.this.resetThrottlingInner(this.mUserId);
      }
    }

    public final void handleResetAllThrottling() {
      Slog.i("ShellCommand", "cmd: handleResetAllThrottling");
      ShortcutService.this.resetAllThrottlingInner();
    }

    public final void handleOverrideConfig() {
      String nextArgRequired = getNextArgRequired();
      Slog.i("ShellCommand", "cmd: handleOverrideConfig: " + nextArgRequired);
      synchronized (ShortcutService.this.mLock) {
        if (!ShortcutService.this.updateConfigurationLocked(nextArgRequired)) {
          throw new CommandException("override-config failed.  See logcat for details.");
        }
      }
    }

    public final void handleResetConfig() {
      Slog.i("ShellCommand", "cmd: handleResetConfig");
      synchronized (ShortcutService.this.mLock) {
        ShortcutService.this.loadConfigurationLocked();
      }
    }

    public final void handleGetDefaultLauncher() {
      synchronized (ShortcutService.this.mLock) {
        parseOptionsLocked(true);
        String defaultLauncher = ShortcutService.this.getDefaultLauncher(this.mUserId);
        if (defaultLauncher == null) {
          throw new CommandException("Failed to get the default launcher for user " + this.mUserId);
        }
        ArrayList arrayList = new ArrayList();
        ShortcutService.this.mPackageManagerInternal.getHomeActivitiesAsUser(
            arrayList, ShortcutService.this.getParentOrSelfUserId(this.mUserId));
        Iterator it = arrayList.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          ComponentInfo componentInfo = ((ResolveInfo) it.next()).getComponentInfo();
          if (componentInfo.packageName.equals(defaultLauncher)) {
            getOutPrintWriter().println("Launcher: " + componentInfo.getComponentName());
            break;
          }
        }
      }
    }

    public final void handleUnloadUser() {
      synchronized (ShortcutService.this.mLock) {
        parseOptionsLocked(true);
        Slog.i("ShellCommand", "cmd: handleUnloadUser: user=" + this.mUserId);
        ShortcutService.this.handleStopUser(this.mUserId);
      }
    }

    public final void handleClearShortcuts() {
      synchronized (ShortcutService.this.mLock) {
        parseOptionsLocked(true);
        String nextArgRequired = getNextArgRequired();
        Slog.i(
            "ShellCommand",
            "cmd: handleClearShortcuts: user" + this.mUserId + ", " + nextArgRequired);
        ShortcutService.this.cleanUpPackageForAllLoadedUsers(nextArgRequired, this.mUserId, true);
      }
    }

    public final void handleGetShortcuts() {
      synchronized (ShortcutService.this.mLock) {
        parseOptionsLocked(true);
        String nextArgRequired = getNextArgRequired();
        Slog.i(
            "ShellCommand",
            "cmd: handleGetShortcuts: user="
                + this.mUserId
                + ", flags="
                + this.mShortcutMatchFlags
                + ", package="
                + nextArgRequired);
        ShortcutPackage packageShortcutsIfExists =
            ShortcutService.this
                .getUserShortcutsLocked(this.mUserId)
                .getPackageShortcutsIfExists(nextArgRequired);
        if (packageShortcutsIfExists == null) {
          return;
        }
        packageShortcutsIfExists.dumpShortcuts(getOutPrintWriter(), this.mShortcutMatchFlags);
      }
    }

    public final void handleVerifyStates() {
      try {
        ShortcutService.this.verifyStatesForce();
      } catch (Throwable th) {
        throw new CommandException(
            th.getMessage()
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
                + Log.getStackTraceString(th));
      }
    }

    public final void handleHasShortcutAccess() {
      synchronized (ShortcutService.this.mLock) {
        parseOptionsLocked(true);
        getOutPrintWriter()
            .println(
                Boolean.toString(
                    ShortcutService.this.hasShortcutHostPermissionInner(
                        getNextArgRequired(), this.mUserId)));
      }
    }
  }

  public long injectCurrentTimeMillis() {
    return System.currentTimeMillis();
  }

  public long injectElapsedRealtime() {
    return SystemClock.elapsedRealtime();
  }

  public long injectUptimeMillis() {
    return SystemClock.uptimeMillis();
  }

  public int injectBinderCallingUid() {
    return IShortcutService.Stub.getCallingUid();
  }

  public int injectBinderCallingPid() {
    return IShortcutService.Stub.getCallingPid();
  }

  public final int getCallingUserId() {
    return UserHandle.getUserId(injectBinderCallingUid());
  }

  public long injectClearCallingIdentity() {
    return Binder.clearCallingIdentity();
  }

  public void injectRestoreCallingIdentity(long j) {
    Binder.restoreCallingIdentity(j);
  }

  public String injectBuildFingerprint() {
    return Build.FINGERPRINT;
  }

  public final void wtf(String str) {
    wtf(str, null);
  }

  public void wtf(String str, Throwable th) {
    if (th == null) {
      th = new RuntimeException("Stacktrace");
    }
    synchronized (this.mWtfLock) {
      this.mWtfCount++;
      this.mLastWtfStacktrace = new Exception("Last failure was logged here:");
    }
    Slog.wtf("ShortcutService", str, th);
  }

  public File injectSystemDataPath() {
    return Environment.getDataSystemDirectory();
  }

  public File injectUserDataPath(int i) {
    return new File(Environment.getDataSystemCeDirectory(i), DIRECTORY_PER_USER);
  }

  public File getDumpPath() {
    return new File(injectUserDataPath(0), DIRECTORY_DUMP);
  }

  public boolean injectIsLowRamDevice() {
    return ActivityManager.isLowRamDeviceStatic();
  }

  public void injectRegisterUidObserver(IUidObserver iUidObserver, int i) {
    try {
      ActivityManager.getService().registerUidObserver(iUidObserver, i, -1, (String) null);
    } catch (RemoteException unused) {
    }
  }

  public void injectRegisterRoleHoldersListener(
      OnRoleHoldersChangedListener onRoleHoldersChangedListener) {
    this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(
        this.mContext.getMainExecutor(), onRoleHoldersChangedListener, UserHandle.ALL);
  }

  public String injectGetHomeRoleHolderAsUser(int i) {
    List roleHoldersAsUser =
        this.mRoleManager.getRoleHoldersAsUser("android.app.role.HOME", UserHandle.of(i));
    if (roleHoldersAsUser.isEmpty()) {
      return null;
    }
    return (String) roleHoldersAsUser.get(0);
  }

  public File getUserBitmapFilePath(int i) {
    return new File(injectUserDataPath(i), "bitmaps");
  }

  public SparseArray getShortcutsForTest() {
    return this.mUsers;
  }

  public int getMaxShortcutsForTest() {
    return this.mMaxShortcuts;
  }

  public int getMaxUpdatesPerIntervalForTest() {
    return this.mMaxUpdatesPerInterval;
  }

  public long getResetIntervalForTest() {
    return this.mResetInterval;
  }

  public int getMaxIconDimensionForTest() {
    return this.mMaxIconDimension;
  }

  public Bitmap.CompressFormat getIconPersistFormatForTest() {
    return this.mIconPersistFormat;
  }

  public int getIconPersistQualityForTest() {
    return this.mIconPersistQuality;
  }

  public ShortcutPackage getPackageShortcutForTest(String str, int i) {
    synchronized (this.mLock) {
      ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.get(i);
      if (shortcutUser == null) {
        return null;
      }
      return (ShortcutPackage) shortcutUser.getAllPackagesForTest().get(str);
    }
  }

  public ShortcutInfo getPackageShortcutForTest(String str, String str2, int i) {
    synchronized (this.mLock) {
      ShortcutPackage packageShortcutForTest = getPackageShortcutForTest(str, i);
      if (packageShortcutForTest == null) {
        return null;
      }
      return packageShortcutForTest.findShortcutById(str2);
    }
  }

  public void updatePackageShortcutForTest(String str, String str2, int i, Consumer consumer) {
    synchronized (this.mLock) {
      ShortcutPackage packageShortcutForTest = getPackageShortcutForTest(str, i);
      if (packageShortcutForTest == null) {
        return;
      }
      consumer.accept(packageShortcutForTest.findShortcutById(str2));
    }
  }

  public ShortcutLauncher getLauncherShortcutForTest(String str, int i) {
    synchronized (this.mLock) {
      ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.get(i);
      if (shortcutUser == null) {
        return null;
      }
      return (ShortcutLauncher) shortcutUser.getAllLaunchersForTest().get(UserPackage.of(i, str));
    }
  }

  public ShortcutRequestPinProcessor getShortcutRequestPinProcessorForTest() {
    return this.mShortcutRequestPinProcessor;
  }

  public final void verifyStates() {
    if (injectShouldPerformVerification()) {
      verifyStatesInner();
    }
  }

  public final void verifyStatesForce() {
    verifyStatesInner();
  }

  public final void verifyStatesInner() {
    synchronized (this.mLock) {
      forEachLoadedUserLocked(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutService.lambda$verifyStatesInner$23((ShortcutUser) obj);
            }
          });
    }
  }

  public static /* synthetic */ void lambda$verifyStatesInner$23(ShortcutUser shortcutUser) {
    shortcutUser.forAllPackageItems(
        new Consumer() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda29
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((ShortcutPackageItem) obj).verifyStates();
          }
        });
  }

  public void waitForBitmapSavesForTest() {
    synchronized (this.mLock) {
      forEachLoadedUserLocked(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutService.lambda$waitForBitmapSavesForTest$24((ShortcutUser) obj);
            }
          });
    }
  }

  public static /* synthetic */ void lambda$waitForBitmapSavesForTest$24(
      ShortcutUser shortcutUser) {
    shortcutUser.forAllPackageItems(
        new Consumer() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda32
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((ShortcutPackageItem) obj).waitForBitmapSaves();
          }
        });
  }

  public final List prepareChangedShortcuts(
      ArraySet arraySet, ArraySet arraySet2, List list, ShortcutPackage shortcutPackage) {
    if (shortcutPackage == null) {
      return null;
    }
    if (CollectionUtils.isEmpty(arraySet) && CollectionUtils.isEmpty(arraySet2)) {
      return null;
    }
    final ArraySet arraySet3 = new ArraySet();
    if (!CollectionUtils.isEmpty(arraySet)) {
      arraySet3.addAll(arraySet);
    }
    if (!CollectionUtils.isEmpty(arraySet2)) {
      arraySet3.addAll(arraySet2);
    }
    if (!CollectionUtils.isEmpty(list)) {
      list.removeIf(
          new Predicate() { // from class:
            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$prepareChangedShortcuts$25;
              lambda$prepareChangedShortcuts$25 =
                  ShortcutService.lambda$prepareChangedShortcuts$25(arraySet3, (ShortcutInfo) obj);
              return lambda$prepareChangedShortcuts$25;
            }
          });
    }
    ArrayList arrayList = new ArrayList();
    shortcutPackage.findAll(
        arrayList,
        new Predicate() { // from class:
          // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda31
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$prepareChangedShortcuts$26;
            lambda$prepareChangedShortcuts$26 =
                ShortcutService.lambda$prepareChangedShortcuts$26(arraySet3, (ShortcutInfo) obj);
            return lambda$prepareChangedShortcuts$26;
          }
        },
        4);
    return arrayList;
  }

  public static /* synthetic */ boolean lambda$prepareChangedShortcuts$25(
      ArraySet arraySet, ShortcutInfo shortcutInfo) {
    return arraySet.contains(shortcutInfo.getId());
  }

  public static /* synthetic */ boolean lambda$prepareChangedShortcuts$26(
      ArraySet arraySet, ShortcutInfo shortcutInfo) {
    return arraySet.contains(shortcutInfo.getId());
  }

  public final List prepareChangedShortcuts(
      List list, List list2, List list3, ShortcutPackage shortcutPackage) {
    ArraySet arraySet = new ArraySet();
    addShortcutIdsToSet(arraySet, list);
    ArraySet arraySet2 = new ArraySet();
    addShortcutIdsToSet(arraySet2, list2);
    return prepareChangedShortcuts(arraySet, arraySet2, list3, shortcutPackage);
  }

  public final void addShortcutIdsToSet(ArraySet arraySet, List list) {
    if (CollectionUtils.isEmpty(list)) {
      return;
    }
    int size = list.size();
    for (int i = 0; i < size; i++) {
      arraySet.add(((ShortcutInfo) list.get(i)).getId());
    }
  }

  public void requestPinShortcutAsDisplay(
      String str,
      ShortcutInfo shortcutInfo,
      IntentSender intentSender,
      int i,
      int i2,
      AndroidFuture androidFuture) {
    Objects.requireNonNull(shortcutInfo);
    Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
    Preconditions.checkArgument(
        true ^ shortcutInfo.isExcludedFromSurfaces(1),
        "Shortcut excluded from launcher cannot be pinned");
    androidFuture.complete(
        String.valueOf(
            requestPinItem(
                str,
                i,
                shortcutInfo,
                null,
                null,
                intentSender,
                injectBinderCallingPid(),
                injectBinderCallingUid(),
                i2)));
  }

  public void allowSmartSwitchBackup(boolean z) {
    this.mSmartSwitchBackupAllowed.set(z);
  }

  public final void enforceScloudBackupWritePermission() {
    injectEnforceCallingPermission("com.samsung.android.scloud.backup.lib.write", null);
  }

  public boolean isSmartSwitchBackupAllowed() {
    return this.mSmartSwitchBackupAllowed.get();
  }

  public void restoreBitmapsFromBackupService(
      ParcelFileDescriptor parcelFileDescriptor, String str, String str2) {
    enforceScloudBackupWritePermission();
    try {
      ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
          new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
      try {
        FileOutputStream openIconFileForWriteSmartSwitch =
            openIconFileForWriteSmartSwitch(0, str, str2);
        try {
          byte[] bArr = new byte[1024];
          while (true) {
            int read = autoCloseInputStream.read(bArr);
            if (read <= 0) {
              break;
            } else {
              openIconFileForWriteSmartSwitch.write(bArr, 0, read);
            }
          }
          if (openIconFileForWriteSmartSwitch != null) {
            openIconFileForWriteSmartSwitch.close();
          }
          autoCloseInputStream.close();
        } finally {
        }
      } finally {
      }
    } catch (Exception unused) {
    }
  }

  public final FileOutputStream openIconFileForWriteSmartSwitch(int i, String str, String str2) {
    File file = new File(getUserBitmapFilePath(i), str);
    if (!file.isDirectory()) {
      file.mkdirs();
      if (!file.isDirectory()) {
        Slog.d("ShortcutService", "Unable to create directory " + file);
        throw new IOException("Unable to create directory " + file);
      }
      SELinux.restorecon(file);
    }
    File file2 = new File(file, str2);
    if (file2.exists()) {
      Slog.d("ShortcutService", "Unable to create file - already exists " + file2);
      throw new IOException("Unable to create file - already exists " + file2);
    }
    return new FileOutputStream(file2);
  }

  public IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() {
    enforceScloudBackupWritePermission();
    return new IParcelFileDescriptorFactory
        .Stub() { // from class: com.android.server.pm.ShortcutService.8
      public ParcelFileDescriptor open(String str, int i) {
        String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (split.length < 2) {
          throw new IllegalArgumentException("Invalid filename: " + str);
        }
        if (!FileUtils.isValidExtFilename(split[0]) || !FileUtils.isValidExtFilename(split[1])) {
          Log.d("ShortcutService", "Invalid filename: " + split[0] + ", " + split[1]);
          throw new IllegalArgumentException("Invalid filename: " + str);
        }
        Log.d("ShortcutService", "filename: " + split[0] + "/" + split[1]);
        try {
          File file =
              new File(new File(ShortcutService.this.getUserBitmapFilePath(0), split[0]), split[1]);
          FileDescriptor open = Os.open(file.getAbsolutePath(), OsConstants.O_RDONLY, 402);
          Log.d("ShortcutService", "openfile: " + file.getAbsolutePath());
          return new ParcelFileDescriptor(open);
        } catch (ErrnoException e) {
          Log.d("ShortcutService", "Failed to open: " + e.getMessage());
          throw new RemoteException("Failed to open: " + e.getMessage());
        }
      }
    };
  }

  public String[] getBitmapPathList(int i) {
    enforceScloudBackupWritePermission();
    final ArrayList arrayList = new ArrayList();
    getUserShortcutsLocked(i)
        .forAllPackages(
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda19
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutService.lambda$getBitmapPathList$27(arrayList, (ShortcutPackage) obj);
              }
            });
    return (String[]) arrayList.toArray(new String[arrayList.size()]);
  }

  public static /* synthetic */ void lambda$getBitmapPathList$27(
      ArrayList arrayList, ShortcutPackage shortcutPackage) {
    Iterator it = shortcutPackage.semGetUsedBitmapFiles().iterator();
    while (it.hasNext()) {
      arrayList.add(
          shortcutPackage.getPackageName()
              + XmlUtils.STRING_ARRAY_SEPARATOR
              + ((String) it.next()));
    }
  }

  public ParcelFileDescriptor getBackupShortcut(int i) {
    PmLog.logDebugInfoAndLogcat("Start Shortcut Backup");
    enforceScloudBackupWritePermission();
    synchronized (this.mLock) {
      if (!isUserUnlockedL(i)) {
        PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : user locked - " + i);
        return null;
      }
      ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
      if (userShortcutsLocked == null) {
        PmLog.logDebugInfoAndLogcat(
            "Shortcut Backup failed : Can't backup: user not found: id=" + i);
        return null;
      }
      allowSmartSwitchBackup(true);
      try {
        userShortcutsLocked.forAllPackageItems(
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda7
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((ShortcutPackageItem) obj).refreshPackageSignatureAndSave();
              }
            });
        userShortcutsLocked.forAllLaunchers(
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda8
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((ShortcutLauncher) obj).ensurePackageInfo();
              }
            });
        scheduleSaveUser(i);
        saveDirtyInfo();
        File file = new File("/data/misc/shortcutbnr");
        File file2 = new File(file, "shortcut.br");
        try {
          if (!file.exists()) {
            file.mkdirs();
          }
          if (file2.exists()) {
            file2.delete();
          }
          try {
            try {
              FileOutputStream fileOutputStream = new FileOutputStream(file2);
              try {
                saveUserInternalLocked(i, fileOutputStream, true);
                FileDescriptor open = Os.open(file2.getAbsolutePath(), OsConstants.O_RDONLY, 402);
                fileOutputStream.close();
                PmLog.logDebugInfoAndLogcat("Success Shortcut Backup");
                return new ParcelFileDescriptor(open);
              } catch (Throwable th) {
                try {
                  fileOutputStream.close();
                } catch (Throwable th2) {
                  th.addSuppressed(th2);
                }
                throw th;
              }
            } catch (Exception e) {
              PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : " + e);
              return null;
            }
          } catch (IOException | XmlPullParserException e2) {
            PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : " + e2);
            return null;
          }
        } catch (Exception e3) {
          PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : " + e3);
          return null;
        }
      } finally {
        allowSmartSwitchBackup(false);
      }
    }
  }

  public int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) {
    PmLog.logDebugInfoAndLogcat("Start Shortcut Restoration");
    enforceScloudBackupWritePermission();
    synchronized (this.mLock) {
      if (!isUserUnlockedL(i)) {
        PmLog.logDebugInfoAndLogcat("Shortcut Restoration failed : user locked - " + i);
        return 1;
      }
      ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
      allowSmartSwitchBackup(true);
      try {
        try {
          ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
              new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
          try {
            try {
              ShortcutUser loadUserInternal = loadUserInternal(i, autoCloseInputStream, true);
              allowSmartSwitchBackup(true);
              IoUtils.closeQuietly(autoCloseInputStream);
              userShortcutsLocked.mergeRestoredFile(loadUserInternal);
              rescanUpdatedPackagesLocked(i, 0L);
              saveUser(i);
              allowSmartSwitchBackup(false);
              PmLog.logDebugInfoAndLogcat("Success Shortcut Restoration");
              return 0;
            } catch (Throwable th) {
              IoUtils.closeQuietly(autoCloseInputStream);
              throw th;
            }
          } catch (InvalidFileFormatException | IOException | XmlPullParserException e) {
            PmLog.logDebugInfoAndLogcat("Shortcut Restoration failed : " + e);
            IoUtils.closeQuietly(autoCloseInputStream);
            allowSmartSwitchBackup(false);
            return 1;
          }
        } catch (Exception e2) {
          PmLog.logDebugInfoAndLogcat("Shortcut Restoration failed : " + e2);
          allowSmartSwitchBackup(false);
          return 1;
        }
      } catch (Throwable th2) {
        allowSmartSwitchBackup(false);
        throw th2;
      }
    }
  }
}
