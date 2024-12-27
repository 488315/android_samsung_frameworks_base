package com.android.server.appwidget;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetManagerInternal;
import android.appwidget.AppWidgetProviderInfo;
import android.appwidget.PendingHostUpdate;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.TransactionTooLargeException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IntArray;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.SizeF;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.IRemoteViewsFactory;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.WidgetBackupProvider;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;
import com.android.server.appwidget.AppWidgetServiceImpl;
import com.android.server.pm.PackageManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
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
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.xmlpull.v1.XmlPullParserException;

public final class AppWidgetServiceImpl extends IAppWidgetService.Stub implements WidgetBackupProvider, DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener {
    public static final int[] APPWIDGET_WIDGET_SUPPORTED_SIZES;
    public static final boolean DEBUG;
    public static final long DEFAULT_GENERATED_PREVIEW_RESET_INTERVAL_MS;
    public static final int MIN_UPDATE_PERIOD;
    public static final AtomicLong UPDATE_COUNTER;
    public ActivityManager mActivityManager;
    public ActivityManagerInternal mActivityManagerInternal;
    public AlarmManager mAlarmManager;
    public AppOpsManager mAppOpsManager;
    public AppOpsManagerInternal mAppOpsManagerInternal;
    public BackupRestoreController mBackupRestoreController;
    public CallbackHandler mCallbackHandler;
    public final Context mContext;
    public long mDebugMonitorEndTime;
    public int mDebugMonitorProviderCount;
    public long mDebugMonitorProviderInfoLoadTime;
    public long mDebugMonitorStartTime;
    public DevicePolicyManagerInternal mDevicePolicyManagerInternal;
    public ApiCounter mGeneratedPreviewsApiCounter;
    public ApiCounter mGeneratedTemplatePreviewsApiCounter;
    public Bundle mInteractiveBroadcast;
    public boolean mIsCombinedBroadcastEnabled;
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
    public UsageStatsManagerInternal mUsageStatsManagerInternal;
    public UserManager mUserManager;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            char c;
            String schemeSpecificPart;
            byte b;
            byte b2;
            ApiCounter apiCounter;
            boolean z = false;
            int i = 1;
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            if (action == null) {
                Log.e("AppWidgetServiceImpl", "action is null");
                return;
            }
            boolean z2 = AppWidgetServiceImpl.DEBUG;
            if (z2) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(intExtra, "Received broadcast: ", action, " on user ", " pkg:"), intent.getPackage(), "AppWidgetServiceImpl");
            }
            Widget widget = null;
            SparseIntArray sparseIntArray = null;
            switch (action.hashCode()) {
                case -1238404651:
                    if (action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1001645458:
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -864107122:
                    if (action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -757780528:
                    if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -147579983:
                    if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 158859398:
                    if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 502473491:
                    if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 505380757:
                    if (action.equals("android.intent.action.TIME_SET")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 928080374:
                    if (action.equals("android.intent.action.PACKAGE_UNSTOPPED")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1290767157:
                    if (action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1779291251:
                    if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                        c = '\n';
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
                case 2:
                    synchronized (AppWidgetServiceImpl.this.mLock) {
                        AppWidgetServiceImpl.this.reloadWidgetsMaskedState(intExtra);
                    }
                    return;
                case 1:
                    AppWidgetServiceImpl.m250$$Nest$monPackageBroadcastReceived(AppWidgetServiceImpl.this, intent, getSendingUserId());
                    AppWidgetServiceImpl.m252$$Nest$mupdateWidgetPackageSuspensionMaskedState(AppWidgetServiceImpl.this, intent, true, getSendingUserId());
                    return;
                case 3:
                case '\b':
                    if (Flags.stayStopped()) {
                        AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                        appWidgetServiceImpl.getClass();
                        int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                        Uri data = intent.getData();
                        if (intExtra2 == -1 || data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                            return;
                        }
                        try {
                            boolean isPackageStoppedForUser = appWidgetServiceImpl.mPackageManager.isPackageStoppedForUser(schemeSpecificPart, UserHandle.getUserId(intExtra2));
                            if (z2) {
                                HeimdAllFsService$$ExternalSyntheticOutline0.m("AppWidgetServiceImpl", DirEncryptService$$ExternalSyntheticOutline0.m(intExtra2, "Updating package stopped masked state for uid ", " package ", schemeSpecificPart, " isStopped "), isPackageStoppedForUser);
                            }
                            synchronized (appWidgetServiceImpl.mLock) {
                                try {
                                    int size = appWidgetServiceImpl.mProviders.size();
                                    int i2 = 0;
                                    while (i2 < size) {
                                        Provider provider = (Provider) appWidgetServiceImpl.mProviders.get(i2);
                                        ProviderId providerId = provider.id;
                                        if (intExtra2 == providerId.uid && schemeSpecificPart.equals(providerId.componentName.getPackageName())) {
                                            boolean z3 = provider.maskedByStoppedPackage;
                                            provider.maskedByStoppedPackage = isPackageStoppedForUser;
                                            if (isPackageStoppedForUser != z3) {
                                                if (provider.isMaskedLocked()) {
                                                    appWidgetServiceImpl.maskWidgetsViewsLocked(provider, widget);
                                                    appWidgetServiceImpl.cancelBroadcastsLocked(provider);
                                                } else {
                                                    appWidgetServiceImpl.unmaskWidgetsViewsLocked(provider);
                                                    int size2 = provider.widgets.size();
                                                    if (size2 > 0) {
                                                        int[] iArr = new int[size2];
                                                        for (int i3 = z ? 1 : 0; i3 < size2; i3 += i) {
                                                            iArr[i3] = ((Widget) provider.widgets.get(i3)).appWidgetId;
                                                        }
                                                        appWidgetServiceImpl.registerForBroadcastsLocked(provider, iArr);
                                                        appWidgetServiceImpl.sendUpdateIntentLocked(provider, iArr, z, z);
                                                    }
                                                    int size3 = provider.pendingDeletedWidgetIds.size();
                                                    if (size3 > 0) {
                                                        if (AppWidgetServiceImpl.DEBUG) {
                                                            Slog.i("AppWidgetServiceImpl", "Sending missed deleted broadcasts for " + provider.id.componentName + " " + provider.pendingDeletedWidgetIds);
                                                        }
                                                        for (int i4 = z ? 1 : 0; i4 < size3; i4++) {
                                                            ProviderId providerId2 = provider.id;
                                                            ComponentName componentName = providerId2.componentName;
                                                            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(providerId2.uid);
                                                            int i5 = provider.pendingDeletedWidgetIds.get(i4);
                                                            Intent intent2 = new Intent("android.appwidget.action.APPWIDGET_DELETED");
                                                            intent2.addFlags(268435456);
                                                            intent2.setComponent(componentName);
                                                            intent2.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i5);
                                                            z = false;
                                                            appWidgetServiceImpl.sendBroadcastAsUser(intent2, userHandleForUid, false);
                                                        }
                                                        provider.pendingDeletedWidgetIds.clear();
                                                        if (size2 == 0) {
                                                            appWidgetServiceImpl.sendDisabledIntentLocked(provider);
                                                        }
                                                        appWidgetServiceImpl.saveGroupStateAsync(UserHandle.getUserHandleForUid(provider.id.uid).getIdentifier());
                                                        i = 1;
                                                    }
                                                }
                                            }
                                        }
                                        i2 += i;
                                        widget = null;
                                    }
                                } finally {
                                }
                            }
                            return;
                        } catch (Exception e) {
                            Slog.e("AppWidgetServiceImpl", "Failed to query package stopped state", e);
                            return;
                        }
                    }
                    return;
                case 4:
                case '\n':
                    if (intent.getIntExtra("reason", 0) == 5) {
                        Log.i("AppWidgetServiceImpl", "call onEmergencyModeDisabled");
                        try {
                            AppWidgetServiceImpl.m249$$Nest$monEmergencyModeDisabled(AppWidgetServiceImpl.this);
                            return;
                        } catch (Exception e2) {
                            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "excepion ", "AppWidgetServiceImpl");
                            return;
                        }
                    }
                    return;
                case 5:
                    AppWidgetServiceImpl appWidgetServiceImpl2 = AppWidgetServiceImpl.this;
                    Configuration configuration = appWidgetServiceImpl2.mContext.getResources().getConfiguration();
                    Locale locale = Locale.getDefault();
                    Locale locale2 = appWidgetServiceImpl2.mLocale;
                    if (locale2 == null || locale == null || !locale2.equals(locale)) {
                        appWidgetServiceImpl2.mLocale = locale;
                        b = true;
                    } else {
                        b = false;
                    }
                    if (b != false && (apiCounter = appWidgetServiceImpl2.mGeneratedTemplatePreviewsApiCounter) != null) {
                        for (ApiCounter.ApiCallRecord apiCallRecord : ((ArrayMap) apiCounter.mCallCount).values()) {
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            apiCallRecord.apiCallCount = 0;
                            apiCallRecord.lastResetTimeMs = elapsedRealtime;
                        }
                        Log.d("AppWidgetServiceImpl", "Template widget preview's records are cleared because of locale changed.");
                    }
                    if (configuration.densityDpi != appWidgetServiceImpl2.mScreenDensity) {
                        Display displayNoVerify = appWidgetServiceImpl2.mContext.getDisplayNoVerify();
                        Point point = new Point();
                        displayNoVerify.getRealSize(point);
                        appWidgetServiceImpl2.mMaxWidgetBitmapMemory = point.x * 8 * point.y;
                        appWidgetServiceImpl2.mScreenDensity = configuration.densityDpi;
                        b2 = true;
                    } else {
                        b2 = false;
                    }
                    if (b == true || b2 == true) {
                        synchronized (appWidgetServiceImpl2.mLock) {
                            try {
                                ArrayList arrayList = new ArrayList(appWidgetServiceImpl2.mProviders);
                                HashSet hashSet = new HashSet();
                                int i6 = -1;
                                String str = null;
                                for (int size4 = arrayList.size() - 1; size4 >= 0; size4--) {
                                    Provider provider2 = (Provider) arrayList.get(size4);
                                    if (provider2 == null) {
                                        Log.e("AppWidgetServiceImpl", "onConfigurationChanged installedProviders is null skip index: " + size4);
                                    } else {
                                        AppWidgetProviderInfo appWidgetProviderInfo = provider2.info;
                                        if (b2 == false || appWidgetProviderInfo == null || (appWidgetProviderInfo.widgetCategory & 2) == 0) {
                                            int userId = provider2.getUserId();
                                            if (appWidgetServiceImpl2.mUserManager.isUserUnlockingOrUnlocked(userId) && !appWidgetServiceImpl2.isProfileWithLockedParent(userId)) {
                                                appWidgetServiceImpl2.ensureGroupStateLoadedLocked(userId, true);
                                                if (!hashSet.contains(provider2.id)) {
                                                    String packageName = provider2.id.componentName.getPackageName();
                                                    int userId2 = provider2.getUserId();
                                                    if (userId2 != i6 || packageName == null || !packageName.equals(str)) {
                                                        if (appWidgetServiceImpl2.updateProvidersForPackageLocked(packageName, hashSet, userId2, true)) {
                                                            if (sparseIntArray == null) {
                                                                sparseIntArray = new SparseIntArray();
                                                            }
                                                            SecurityPolicy securityPolicy = appWidgetServiceImpl2.mSecurityPolicy;
                                                            int userId3 = provider2.getUserId();
                                                            int profileParent = AppWidgetServiceImpl.this.mSecurityPolicy.getProfileParent(userId3);
                                                            if (profileParent != -10) {
                                                                userId3 = profileParent;
                                                            }
                                                            sparseIntArray.put(userId3, userId3);
                                                        }
                                                        str = packageName;
                                                        i6 = userId2;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (sparseIntArray != null) {
                                    int size5 = sparseIntArray.size();
                                    for (int i7 = 0; i7 < size5; i7++) {
                                        appWidgetServiceImpl2.saveGroupStateAsync(sparseIntArray.get(i7));
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                case 6:
                    AppWidgetServiceImpl appWidgetServiceImpl3 = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl3.getClass();
                    TimeZone.getDefault();
                    appWidgetServiceImpl3.resetPreviewRecord(2);
                    return;
                case 7:
                    AppWidgetServiceImpl appWidgetServiceImpl4 = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl4.getClass();
                    LocalDateTime.now();
                    appWidgetServiceImpl4.resetPreviewRecord(1);
                    return;
                case '\t':
                    AppWidgetServiceImpl.m250$$Nest$monPackageBroadcastReceived(AppWidgetServiceImpl.this, intent, getSendingUserId());
                    AppWidgetServiceImpl.m252$$Nest$mupdateWidgetPackageSuspensionMaskedState(AppWidgetServiceImpl.this, intent, false, getSendingUserId());
                    return;
                default:
                    AppWidgetServiceImpl.m250$$Nest$monPackageBroadcastReceived(AppWidgetServiceImpl.this, intent, getSendingUserId());
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
    public final SparseIntArray mNextAppWidgetIds = new SparseIntArray();
    public final File mFdFile = new File("/proc/self/fd");
    public final HashMap mPackageToPidMap = new HashMap();
    public final HashMap mPidToPackageMap = new HashMap();
    public final HashMap mLastSeqNumber = new HashMap();
    public final boolean isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
    public final String[] mHostHistory = new String[7];
    public final String[] mProvidersHistory = new String[100];
    public final String[] mAlarmHistory = new String[7];
    public int mHostHistoryIdx = 0;
    public int mProvidersHistoryIndex = 0;
    public int mAlarmHistoryIndex = 0;

    public final class AddProviderLockedRunnable implements Runnable {
        public final int mIndex;
        public final CountDownLatch mLatch;
        public final boolean[] mReceiversCheckArray;
        public final ResolveInfo mRi;

        public AddProviderLockedRunnable(CountDownLatch countDownLatch, ResolveInfo resolveInfo, int i, boolean[] zArr) {
            this.mLatch = countDownLatch;
            this.mRi = resolveInfo;
            this.mIndex = i;
            this.mReceiversCheckArray = zArr;
        }

        @Override // java.lang.Runnable
        public final void run() {
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

    public final class ApiCounter {
        public final Map mCallCount;
        public int mMaxCallsPerInterval;
        public final LongSupplier mMonotonicClock;
        public long mResetIntervalMs;

        public final class ApiCallRecord {
            public int apiCallCount;
            public long lastResetTimeMs;
        }

        public ApiCounter(long j, int i) {
            AppWidgetServiceImpl$ApiCounter$$ExternalSyntheticLambda0 appWidgetServiceImpl$ApiCounter$$ExternalSyntheticLambda0 = new AppWidgetServiceImpl$ApiCounter$$ExternalSyntheticLambda0();
            this.mCallCount = new ArrayMap();
            this.mResetIntervalMs = j;
            this.mMaxCallsPerInterval = i;
            this.mMonotonicClock = appWidgetServiceImpl$ApiCounter$$ExternalSyntheticLambda0;
        }

        public final ApiCallRecord getOrCreateRecord(ProviderId providerId) {
            if (!((ArrayMap) this.mCallCount).containsKey(providerId)) {
                Map map = this.mCallCount;
                ApiCallRecord apiCallRecord = new ApiCallRecord();
                apiCallRecord.apiCallCount = 0;
                apiCallRecord.lastResetTimeMs = 0L;
                ((ArrayMap) map).put(providerId, apiCallRecord);
            }
            return (ApiCallRecord) ((ArrayMap) this.mCallCount).get(providerId);
        }

        public final boolean tryApiCall(ProviderId providerId) {
            ApiCallRecord orCreateRecord = getOrCreateRecord(providerId);
            long asLong = this.mMonotonicClock.getAsLong();
            if (asLong - orCreateRecord.lastResetTimeMs > this.mResetIntervalMs) {
                orCreateRecord.apiCallCount = 0;
                orCreateRecord.lastResetTimeMs = asLong;
            }
            int i = orCreateRecord.apiCallCount;
            if (i >= this.mMaxCallsPerInterval) {
                return false;
            }
            orCreateRecord.apiCallCount = i + 1;
            return true;
        }
    }

    public abstract class AppWidgetLogWrapper {
        public static final Logger widget_logger;
        public static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
        public static final Date date = new Date();

        static {
            try {
                FileHandler fileHandler = new FileHandler("/data/log/appwidget_history_log%g.txt", 10240, 2, true);
                fileHandler.setFormatter(new Formatter() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.AppWidgetLogWrapper.1
                    @Override // java.util.logging.Formatter
                    public final String format(LogRecord logRecord) {
                        Date date2 = AppWidgetLogWrapper.date;
                        date2.setTime(System.currentTimeMillis());
                        StringBuilder sb = new StringBuilder(80);
                        sb.append(AppWidgetLogWrapper.formatter.format(date2));
                        sb.append(logRecord.getMessage());
                        return sb.toString();
                    }
                });
                Logger logger = Logger.getLogger(AppWidgetLogWrapper.class.getName());
                widget_logger = logger;
                logger.addHandler(fileHandler);
                logger.setLevel(Level.ALL);
                logger.setUseParentHandlers(false);
            } catch (Exception e) {
                Log.i("AppWidgetLogWrapper", "Can not use AppWidgetLogWrapper " + e.toString());
            }
        }

        public static StringBuilder getLogText() {
            File[] fileArr = new File[2];
            for (int i = 0; i < 2; i++) {
                fileArr[i] = new File(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "/data/log/appwidget_history_log", ".txt"));
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

        public static void i(String str) {
            Logger logger = widget_logger;
            if (logger != null) {
                logger.log(Level.INFO, String.format("V %s(%d): %s%n", "AppWidgetServiceImpl", Integer.valueOf(Binder.getCallingPid()), str));
            }
            Log.i("AppWidgetServiceImpl", str);
        }
    }

    public final class AppWidgetManagerLocal extends AppWidgetManagerInternal {
        public AppWidgetManagerLocal() {
        }

        public final void applyResourceOverlaysToWidgets(Set set, int i, boolean z) {
            synchronized (AppWidgetServiceImpl.this.mLock) {
                AppWidgetServiceImpl.m246$$Nest$mapplyResourceOverlaysToWidgetsLocked(AppWidgetServiceImpl.this, new HashSet(set), i, z);
            }
        }

        public final ArraySet getHostedWidgetPackages(int i) {
            ArraySet arraySet;
            synchronized (AppWidgetServiceImpl.this.mLock) {
                try {
                    int size = AppWidgetServiceImpl.this.mWidgets.size();
                    arraySet = null;
                    for (int i2 = 0; i2 < size; i2++) {
                        Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i2);
                        if (widget.host.id.uid == i && widget.provider != null) {
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                            }
                            arraySet.add(widget.provider.id.componentName.getPackageName());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arraySet;
        }

        public final void unlockUser(int i) {
            AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
            if (appWidgetServiceImpl.isProfileWithLockedParent(i)) {
                return;
            }
            if (!appWidgetServiceImpl.mUserManager.isUserUnlockingOrUnlocked(i)) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "User ", " is no longer unlocked - exiting", "AppWidgetServiceImpl");
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (appWidgetServiceImpl.mLock) {
                try {
                    Trace.traceBegin(64L, "appwidget ensure");
                    appWidgetServiceImpl.ensureGroupStateLoadedLocked(i, true);
                    Trace.traceEnd(64L);
                    Trace.traceBegin(64L, "appwidget reload");
                    int profileParent = AppWidgetServiceImpl.this.mSecurityPolicy.getProfileParent(i);
                    if (profileParent == -10) {
                        profileParent = i;
                    }
                    appWidgetServiceImpl.reloadWidgetsMaskedStateForGroup(profileParent);
                    Trace.traceEnd(64L);
                    int size = appWidgetServiceImpl.mProviders.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        final Provider provider = (Provider) appWidgetServiceImpl.mProviders.get(i2);
                        if (provider.getUserId() == i && provider.widgets.size() > 0 && !provider.maskedByStoppedPackage) {
                            Trace.traceBegin(64L, "appwidget init " + provider.id.componentName.getPackageName());
                            provider.widgets.forEach(new Consumer() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda4
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    AppWidgetServiceImpl.Provider provider2 = AppWidgetServiceImpl.Provider.this;
                                    AppWidgetServiceImpl.Widget widget = (AppWidgetServiceImpl.Widget) obj;
                                    widget.trackingUpdate = true;
                                    Trace.asyncTraceBegin(64L, "appwidget update-intent " + provider2.id.toString(), widget.appWidgetId);
                                    Log.i("AppWidgetServiceImpl", "Widget update scheduled on unlock " + widget.toString());
                                }
                            });
                            int[] widgetIds = AppWidgetServiceImpl.getWidgetIds(provider.widgets);
                            appWidgetServiceImpl.sendEnableAndUpdateIntentLocked(provider, widgetIds);
                            appWidgetServiceImpl.registerForBroadcastsLocked(provider, widgetIds);
                            Trace.traceEnd(64L);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Processing of handleUserUnlocked u", " took ");
            m.append(SystemClock.elapsedRealtime() - elapsedRealtime);
            m.append(" ms");
            Slog.i("AppWidgetServiceImpl", m.toString());
        }
    }

    public final class BackupRestoreController {
        public boolean mHasSystemRestoreFinished;
        public final SparseArray mPrunedAppsPerUser = new SparseArray();
        public final HashMap mUpdatesByProvider = new HashMap();
        public final HashMap mUpdatesByHost = new HashMap();

        public final class RestoreUpdateRecord {
            public final int newId;
            public boolean notified = false;
            public final int oldId;

            public RestoreUpdateRecord(int i, int i2) {
                this.oldId = i;
                this.newId = i2;
            }
        }

        public BackupRestoreController() {
        }

        public final Widget findRestoredWidgetLocked(int i, Host host, Provider provider) {
            Slog.i("BackupRestoreController", "Find restored widget: id=" + i + " host=" + host + " provider=" + provider);
            if (provider != null) {
                AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                int size = appWidgetServiceImpl.mWidgets.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Widget widget = (Widget) appWidgetServiceImpl.mWidgets.get(i2);
                    if (widget.restoredId == i && widget.host.id.equals(host.id) && widget.provider.id.equals(provider.id)) {
                        Slog.i("BackupRestoreController", "   Found at " + i2 + " : " + widget);
                        return widget;
                    }
                }
            }
            return null;
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
                    int size = arrayList2.size();
                    int i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        if (!((RestoreUpdateRecord) arrayList2.get(i3)).notified) {
                            i2++;
                        }
                    }
                    Slog.i("BackupRestoreController", "Provider " + provider + " pending: " + i2);
                    if (i2 > 0) {
                        int[] iArr = new int[i2];
                        int[] iArr2 = new int[i2];
                        int size2 = arrayList2.size();
                        int i4 = 0;
                        int i5 = 0;
                        while (i5 < size2) {
                            RestoreUpdateRecord restoreUpdateRecord = (RestoreUpdateRecord) arrayList2.get(i5);
                            if (!restoreUpdateRecord.notified) {
                                restoreUpdateRecord.notified = z;
                                int i6 = restoreUpdateRecord.oldId;
                                iArr[i4] = i6;
                                int i7 = restoreUpdateRecord.newId;
                                iArr2[i4] = i7;
                                i4++;
                                Slog.i("BackupRestoreController", "   " + i6 + " => " + i7);
                            }
                            i5++;
                            z = true;
                        }
                        sendWidgetRestoreBroadcastLocked("android.appwidget.action.APPWIDGET_RESTORED", provider, null, iArr, iArr2, userHandle);
                    }
                }
            }
            for (Map.Entry entry2 : this.mUpdatesByHost.entrySet()) {
                Host host = (Host) entry2.getKey();
                if (host.id.uid != -1) {
                    ArrayList arrayList3 = (ArrayList) entry2.getValue();
                    int size3 = arrayList3.size();
                    int i8 = 0;
                    for (int i9 = 0; i9 < size3; i9++) {
                        if (!((RestoreUpdateRecord) arrayList3.get(i9)).notified) {
                            i8++;
                        }
                    }
                    Slog.i("BackupRestoreController", "Host " + host + " pending: " + i8);
                    if (i8 > 0) {
                        int[] iArr3 = new int[i8];
                        int[] iArr4 = new int[i8];
                        int size4 = arrayList3.size();
                        int i10 = 0;
                        int i11 = 0;
                        while (i10 < size4) {
                            RestoreUpdateRecord restoreUpdateRecord2 = (RestoreUpdateRecord) arrayList3.get(i10);
                            if (restoreUpdateRecord2.notified) {
                                arrayList = arrayList3;
                            } else {
                                restoreUpdateRecord2.notified = true;
                                int i12 = restoreUpdateRecord2.oldId;
                                iArr3[i11] = i12;
                                int i13 = restoreUpdateRecord2.newId;
                                iArr4[i11] = i13;
                                i11++;
                                arrayList = arrayList3;
                                Slog.i("BackupRestoreController", "   " + i12 + " => " + i13);
                            }
                            i10++;
                            arrayList3 = arrayList;
                        }
                        sendWidgetRestoreBroadcastLocked("android.appwidget.action.APPWIDGET_HOST_RESTORED", null, host, iArr3, iArr4, userHandle);
                    }
                }
            }
        }

        public final void pruneWidgetStateLocked(int i, String str) {
            if (!this.mPrunedAppsPerUser.contains(i)) {
                this.mPrunedAppsPerUser.set(i, new ArraySet());
            }
            Set set = (Set) this.mPrunedAppsPerUser.get(i);
            if (set.contains(str)) {
                BootReceiver$$ExternalSyntheticOutline0.m58m("already pruned ", str, ", continuing normally", "BackupRestoreController");
                return;
            }
            Slog.i("BackupRestoreController", "pruning widget state for restoring package " + str);
            AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
            for (int size = appWidgetServiceImpl.mWidgets.size() - 1; size >= 0; size--) {
                Widget widget = (Widget) appWidgetServiceImpl.mWidgets.get(size);
                Host host = widget.host;
                Provider provider = widget.provider;
                if (Host.m253$$Nest$mhostsPackageForUser(host, str, i) || (provider != null && provider.isInPackageForUser(i, str))) {
                    host.widgets.remove(widget);
                    if (provider != null) {
                        provider.widgets.remove(widget);
                    }
                    appWidgetServiceImpl.decrementAppWidgetServiceRefCount(widget);
                    appWidgetServiceImpl.removeWidgetLocked(widget);
                }
            }
            set.add(str);
        }

        public final void sendWidgetRestoreBroadcastLocked(String str, Provider provider, Host host, int[] iArr, int[] iArr2, UserHandle userHandle) {
            Intent intent = new Intent(str);
            intent.putExtra("appWidgetOldIds", iArr);
            intent.putExtra("appWidgetIds", iArr2);
            AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
            if (provider != null) {
                intent.setComponent(provider.id.componentName);
                appWidgetServiceImpl.sendBroadcastAsUser(intent, userHandle, true);
            }
            if (host != null) {
                intent.setComponent(null);
                intent.setPackage(host.id.packageName);
                intent.putExtra("hostId", host.id.hostId);
                appWidgetServiceImpl.sendBroadcastAsUser(intent, userHandle, true);
            }
        }

        public final void stashHostRestoreUpdateLocked(Host host, int i, int i2) {
            ArrayList arrayList = (ArrayList) this.mUpdatesByHost.get(host);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mUpdatesByHost.put(host, arrayList);
            } else {
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    RestoreUpdateRecord restoreUpdateRecord = (RestoreUpdateRecord) arrayList.get(i3);
                    if (restoreUpdateRecord.oldId == i && restoreUpdateRecord.newId == i2) {
                        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "ID remap ", " -> ", " already stashed for ");
                        m.append(host);
                        Slog.i("BackupRestoreController", m.toString());
                        return;
                    }
                }
            }
            arrayList.add(new RestoreUpdateRecord(i, i2));
        }

        public final void stashProviderRestoreUpdateLocked(Provider provider, int i, int i2) {
            ArrayList arrayList = (ArrayList) this.mUpdatesByProvider.get(provider);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mUpdatesByProvider.put(provider, arrayList);
            } else {
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    RestoreUpdateRecord restoreUpdateRecord = (RestoreUpdateRecord) arrayList.get(i3);
                    if (restoreUpdateRecord.oldId == i && restoreUpdateRecord.newId == i2) {
                        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "ID remap ", " -> ", " already stashed for ");
                        m.append(provider);
                        Slog.i("BackupRestoreController", m.toString());
                        return;
                    }
                }
            }
            arrayList.add(new RestoreUpdateRecord(i, i2));
        }
    }

    public final class CallbackHandler extends Handler {
        public CallbackHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    Host host = (Host) someArgs.arg1;
                    IAppWidgetHost iAppWidgetHost = (IAppWidgetHost) someArgs.arg2;
                    RemoteViews remoteViews = (RemoteViews) someArgs.arg3;
                    long longValue = ((Long) someArgs.arg4).longValue();
                    int i = someArgs.argi1;
                    someArgs.recycle();
                    AppWidgetServiceImpl.m248$$Nest$mhandleNotifyUpdateAppWidget(AppWidgetServiceImpl.this, host, iAppWidgetHost, i, remoteViews, longValue);
                    return;
                case 2:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    Host host2 = (Host) someArgs2.arg1;
                    IAppWidgetHost iAppWidgetHost2 = (IAppWidgetHost) someArgs2.arg2;
                    AppWidgetProviderInfo appWidgetProviderInfo = (AppWidgetProviderInfo) someArgs2.arg3;
                    long longValue2 = ((Long) someArgs2.arg4).longValue();
                    int i2 = someArgs2.argi1;
                    someArgs2.recycle();
                    AppWidgetServiceImpl.m247$$Nest$mhandleNotifyProviderChanged(AppWidgetServiceImpl.this, host2, iAppWidgetHost2, i2, appWidgetProviderInfo, longValue2);
                    return;
                case 3:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    Host host3 = (Host) someArgs3.arg1;
                    IAppWidgetHost iAppWidgetHost3 = (IAppWidgetHost) someArgs3.arg2;
                    someArgs3.recycle();
                    AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl.getClass();
                    try {
                        Slog.d("AppWidgetServiceImpl", "Trying to notify widget providers changed");
                        iAppWidgetHost3.providersChanged();
                        return;
                    } catch (RemoteException e) {
                        synchronized (appWidgetServiceImpl.mLock) {
                            try {
                                if (host3.callbacks != iAppWidgetHost3) {
                                    Log.d("AppWidgetServiceImpl", "Skip callback clear #3.");
                                } else {
                                    Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host3.id, e);
                                    appWidgetServiceImpl.updateHostHistoryLocked("Host dead #3 : " + host3.id + ", " + e);
                                    host3.callbacks = null;
                                }
                                return;
                            } finally {
                            }
                        }
                    }
                case 4:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    Host host4 = (Host) someArgs4.arg1;
                    IAppWidgetHost iAppWidgetHost4 = (IAppWidgetHost) someArgs4.arg2;
                    long longValue3 = ((Long) someArgs4.arg3).longValue();
                    int i3 = someArgs4.argi1;
                    int i4 = someArgs4.argi2;
                    someArgs4.recycle();
                    final AppWidgetServiceImpl appWidgetServiceImpl2 = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl2.getClass();
                    try {
                        Slog.d("AppWidgetServiceImpl", "Trying to notify widget view data changed");
                        iAppWidgetHost4.viewDataChanged(i3, i4);
                        host4.lastWidgetUpdateSequenceNo = longValue3;
                    } catch (RemoteException unused) {
                        r7 = host4.callbacks != iAppWidgetHost4;
                        iAppWidgetHost4 = null;
                    }
                    synchronized (appWidgetServiceImpl2.mLock) {
                        if (iAppWidgetHost4 == null) {
                            try {
                                String str = "Host dead #4 : " + host4.id + ", appWidgetId : " + i3 + ", callbackChanged : " + r7;
                                Slog.e("AppWidgetServiceImpl", str);
                                appWidgetServiceImpl2.updateHostHistoryLocked(str);
                                if (!r7) {
                                    host4.callbacks = null;
                                }
                                for (Pair pair : appWidgetServiceImpl2.mRemoteViewsServicesAppWidgets.keySet()) {
                                    if (((HashSet) appWidgetServiceImpl2.mRemoteViewsServicesAppWidgets.get(pair)).contains(Integer.valueOf(i3))) {
                                        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.4
                                            @Override // android.content.ServiceConnection
                                            public final void onNullBinding(ComponentName componentName) {
                                                AppWidgetServiceImpl.this.mContext.unbindService(this);
                                            }

                                            @Override // android.content.ServiceConnection
                                            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                                                Log.i("AppWidgetServiceImpl", "RemoteViewsService Connected " + componentName);
                                                try {
                                                    IRemoteViewsFactory.Stub.asInterface(iBinder).onDataSetChangedAsync();
                                                } catch (RemoteException e2) {
                                                    Slog.e("AppWidgetServiceImpl", "Error calling onDataSetChangedAsync()", e2);
                                                }
                                                AppWidgetServiceImpl.this.mContext.unbindService(this);
                                            }

                                            @Override // android.content.ServiceConnection
                                            public final void onServiceDisconnected(ComponentName componentName) {
                                            }
                                        };
                                        int userId = UserHandle.getUserId(((Integer) pair.first).intValue());
                                        Intent intent = ((Intent.FilterComparison) pair.second).getIntent();
                                        UserHandle userHandle = new UserHandle(userId);
                                        long clearCallingIdentity = Binder.clearCallingIdentity();
                                        try {
                                            appWidgetServiceImpl2.mContext.bindServiceAsUser(intent, serviceConnection, 33554433, userHandle);
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                        } catch (Throwable th) {
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                            throw th;
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    return;
                case 5:
                    SomeArgs someArgs5 = (SomeArgs) message.obj;
                    Host host5 = (Host) someArgs5.arg1;
                    IAppWidgetHost iAppWidgetHost5 = (IAppWidgetHost) someArgs5.arg2;
                    long longValue4 = ((Long) someArgs5.arg3).longValue();
                    int i5 = someArgs5.argi1;
                    someArgs5.recycle();
                    AppWidgetServiceImpl appWidgetServiceImpl3 = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl3.getClass();
                    try {
                        Slog.d("AppWidgetServiceImpl", "Trying to notify widget removed");
                        iAppWidgetHost5.appWidgetRemoved(i5);
                        host5.lastWidgetUpdateSequenceNo = longValue4;
                        return;
                    } catch (RemoteException e2) {
                        synchronized (appWidgetServiceImpl3.mLock) {
                            Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host5.id, e2);
                            host5.callbacks = null;
                            return;
                        }
                    }
                case 6:
                    SomeArgs someArgs6 = (SomeArgs) message.obj;
                    Host host6 = (Host) someArgs6.arg1;
                    IAppWidgetHost iAppWidgetHost6 = (IAppWidgetHost) someArgs6.arg2;
                    long longValue5 = ((Long) someArgs6.arg4).longValue();
                    int i6 = someArgs6.argi1;
                    someArgs6.recycle();
                    AppWidgetServiceImpl appWidgetServiceImpl4 = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl4.getClass();
                    try {
                        Slog.d("AppWidgetServiceImpl", "Trying to notify widget update deferred for id: " + i6);
                        iAppWidgetHost6.updateAppWidgetDeferred(i6);
                        host6.lastWidgetUpdateSequenceNo = longValue5;
                        return;
                    } catch (RemoteException e3) {
                        synchronized (appWidgetServiceImpl4.mLock) {
                            Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host6.id, e3);
                            host6.callbacks = null;
                            return;
                        }
                    }
                default:
                    return;
            }
        }
    }

    public final class Host {
        public IAppWidgetHost callbacks;
        public HostId id;
        public long lastWidgetUpdateSequenceNo;
        public boolean zombie;
        public final ArrayList widgets = new ArrayList();
        public int tag = -1;

        /* renamed from: -$$Nest$mhostsPackageForUser, reason: not valid java name */
        public static boolean m253$$Nest$mhostsPackageForUser(Host host, String str, int i) {
            int size = host.widgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                Provider provider = ((Widget) host.widgets.get(i2)).provider;
                if (provider != null && provider.getUserId() == i && str.equals(provider.id.componentName.getPackageName())) {
                    return true;
                }
            }
            return false;
        }

        public final void getPendingUpdatesForIdLocked(Context context, int i, LongSparseArray longSparseArray) {
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
                            if (keyAt != 0) {
                                updateAppWidget = keyAt != 1 ? PendingHostUpdate.viewDataChanged(i, keyAt) : PendingHostUpdate.providerChanged(i, widget.provider.getInfoLocked(context));
                            } else {
                                RemoteViews remoteViews = widget.maskedViews;
                                if (remoteViews == null) {
                                    remoteViews = widget.views;
                                }
                                if (Process.myPid() == Binder.getCallingPid() && remoteViews != null) {
                                    remoteViews = remoteViews.clone();
                                }
                                updateAppWidget = PendingHostUpdate.updateAppWidget(i, remoteViews);
                            }
                            longSparseArray.put(valueAt, updateAppWidget);
                        }
                    }
                    return;
                }
            }
            longSparseArray.put(this.lastWidgetUpdateSequenceNo, PendingHostUpdate.appWidgetRemoved(i));
        }

        public final int getUserId() {
            return UserHandle.getUserId(this.id.uid);
        }

        public final SparseArray getWidgetUidsIfBound() {
            SparseArray sparseArray = new SparseArray();
            for (int size = this.widgets.size() - 1; size >= 0; size--) {
                Widget widget = (Widget) this.widgets.get(size);
                Provider provider = widget.provider;
                if (provider == null) {
                    Slog.d("AppWidgetServiceHost", "Widget with no provider " + widget.toString());
                } else {
                    ProviderId providerId = provider.id;
                    sparseArray.put(providerId.uid, providerId.componentName.getPackageName());
                }
            }
            return sparseArray;
        }

        public final boolean isInPackageForUser(int i, String str) {
            return getUserId() == i && this.id.packageName.equals(str);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Host{");
            sb.append(this.id);
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

        public final boolean equals(Object obj) {
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
            String str = hostId.packageName;
            String str2 = this.packageName;
            if (str2 == null) {
                if (str != null) {
                    return false;
                }
            } else if (!str2.equals(str)) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            int i = ((this.uid * 31) + this.hostId) * 31;
            String str = this.packageName;
            return i + (str != null ? str.hashCode() : 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("HostId{user:");
            int i = this.uid;
            sb.append(UserHandle.getUserId(i));
            sb.append(", app:");
            sb.append(UserHandle.getAppId(i));
            sb.append(", hostId:");
            sb.append(this.hostId);
            sb.append(", pkg:");
            sb.append(this.packageName);
            sb.append('}');
            return sb.toString();
        }
    }

    public final class LoadedWidgetState {
        public final int hostTag;
        public final int providerTag;
        public final Widget widget;

        public LoadedWidgetState(Widget widget, int i, int i2) {
            this.widget = widget;
            this.hostTag = i;
            this.providerTag = i2;
        }
    }

    public final class Provider {
        public static final int[] WIDGET_CATEGORY_FLAGS = {1, 2, 4};
        public PendingIntent broadcast;
        public ProviderId id;
        public AppWidgetProviderInfo info;
        public String infoTag;
        public boolean mInfoParsed;
        public boolean maskedByLockedProfile;
        public boolean maskedByQuietProfile;
        public boolean maskedByStoppedPackage;
        public boolean maskedBySuperLocked;
        public boolean maskedBySuspendedPackage;
        public final IntArray pendingDeletedWidgetIds;
        public int tag;
        public final int[] templateSizes;
        public final int[] templateStyles;
        public boolean zombie;
        public final ArrayList widgets = new ArrayList();
        public final SparseArray generatedPreviews = new SparseArray(3);
        public final SparseArray templatePreviews = new SparseArray(14);

        public Provider() {
            int[] iArr = AppWidgetServiceImpl.APPWIDGET_WIDGET_SUPPORTED_SIZES;
            this.templateStyles = new int[]{1 << iArr.length, 2 << iArr.length};
            this.templateSizes = iArr;
            this.pendingDeletedWidgetIds = new IntArray();
            this.mInfoParsed = false;
            this.tag = -1;
        }

        public final AppWidgetProviderInfo getInfoLocked(Context context) {
            if (!this.mInfoParsed) {
                if (!this.zombie) {
                    AppWidgetProviderInfo parseAppWidgetProviderInfo = !TextUtils.isEmpty(this.infoTag) ? AppWidgetServiceImpl.parseAppWidgetProviderInfo(context, this.id, this.info.providerInfo, this.infoTag) : null;
                    if (parseAppWidgetProviderInfo == null) {
                        parseAppWidgetProviderInfo = AppWidgetServiceImpl.parseAppWidgetProviderInfo(context, this.id, this.info.providerInfo, "android.appwidget.provider");
                    }
                    if (parseAppWidgetProviderInfo != null) {
                        this.info = parseAppWidgetProviderInfo;
                        if (AppWidgetServiceImpl.DEBUG) {
                            Objects.requireNonNull(parseAppWidgetProviderInfo);
                        }
                        updateGeneratedPreviewCategoriesLocked();
                    }
                }
                this.mInfoParsed = true;
            }
            return this.info;
        }

        public final Bundle getTemplatePreviewLocked(int i, int i2) {
            if (this.templatePreviews == null) {
                return Bundle.EMPTY;
            }
            Bundle bundle = new Bundle();
            int size = this.templatePreviews.size();
            int[] iArr = new int[size];
            RemoteViews[] remoteViewsArr = new RemoteViews[size];
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                int keyAt = this.templatePreviews.keyAt(i4);
                int length = this.templateSizes.length;
                int i5 = keyAt >> length;
                int i6 = ((1 << length) - 1) & keyAt;
                if ((i5 & i2) != 0 && (i6 & i) != 0) {
                    iArr[i3] = keyAt;
                    remoteViewsArr[i3] = (RemoteViews) this.templatePreviews.valueAt(i4);
                    i3++;
                }
            }
            bundle.putIntArray("previewStates", iArr);
            bundle.putParcelableArray("previewRemoteViews", remoteViewsArr);
            return bundle;
        }

        public final int getUserId() {
            return UserHandle.getUserId(this.id.uid);
        }

        public final boolean hostedByPackageForUser(int i, String str) {
            int size = this.widgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                Widget widget = (Widget) this.widgets.get(i2);
                if (str.equals(widget.host.id.packageName) && widget.host.getUserId() == i) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isInPackageForUser(int i, String str) {
            return getUserId() == i && this.id.componentName.getPackageName().equals(str);
        }

        public final boolean isMaskedLocked() {
            return this.maskedByQuietProfile || this.maskedByLockedProfile || this.maskedBySuspendedPackage || this.maskedByStoppedPackage || this.maskedBySuperLocked;
        }

        public final void setPartialInfoLocked(AppWidgetProviderInfo appWidgetProviderInfo) {
            this.info = appWidgetProviderInfo;
            boolean z = AppWidgetServiceImpl.DEBUG;
            this.mInfoParsed = false;
        }

        public final void setTemplatePreviewLocked(int i, int i2, RemoteViews[] remoteViewsArr) {
            if (remoteViewsArr.length == 0) {
                return;
            }
            int[] iArr = this.templateSizes;
            int length = iArr.length;
            int i3 = 0;
            for (int i4 : this.templateStyles) {
                if (((i2 << length) & i4) != 0) {
                    for (int i5 : iArr) {
                        if ((i & i5) != 0) {
                            this.templatePreviews.put(i5 | i4, remoteViewsArr[i3]);
                            i3++;
                            if (i3 >= remoteViewsArr.length) {
                                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i3, i, "All preview data has been used for preview state / preview size : ", " / ", ", ");
                                m.append(i2);
                                Log.d("AppWidgetServiceImpl", m.toString());
                                updateTemplatePreviewCategoriesLocked();
                                return;
                            }
                        }
                    }
                }
            }
            updateTemplatePreviewCategoriesLocked();
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Provider{");
            sb.append(this.id);
            sb.append(this.zombie ? " Z" : "");
            sb.append('}');
            return sb.toString();
        }

        public final void updateGeneratedPreviewCategoriesLocked() {
            this.info.generatedPreviewCategories = 0;
            for (int i = 0; i < this.generatedPreviews.size(); i++) {
                this.info.generatedPreviewCategories |= this.generatedPreviews.keyAt(i);
            }
        }

        public final void updateTemplatePreviewCategoriesLocked() {
            int length = this.templateSizes.length;
            int i = 1 << length;
            int i2 = i - 1;
            AppWidgetProviderInfo appWidgetProviderInfo = this.info;
            appWidgetProviderInfo.hidden_semGeneratedColorfulPreviewStates = 0;
            appWidgetProviderInfo.hidden_semGeneratedMonotonePreviewStates = 0;
            for (int i3 = 0; i3 < this.templatePreviews.size(); i3++) {
                int keyAt = this.templatePreviews.keyAt(i3);
                int i4 = keyAt & i2;
                if ((keyAt & i) != 0) {
                    this.info.hidden_semGeneratedColorfulPreviewStates |= i4;
                }
                if ((keyAt & (2 << length)) != 0) {
                    AppWidgetProviderInfo appWidgetProviderInfo2 = this.info;
                    appWidgetProviderInfo2.hidden_semGeneratedMonotonePreviewStates = i4 | appWidgetProviderInfo2.hidden_semGeneratedMonotonePreviewStates;
                }
            }
            StringBuilder sb = new StringBuilder("updateTemplatePreviewCategoriesLocked ");
            sb.append(this.info.hidden_semGeneratedColorfulPreviewStates);
            sb.append(", ");
            GestureWakeup$$ExternalSyntheticOutline0.m(sb, this.info.hidden_semGeneratedMonotonePreviewStates, "AppWidgetServiceImpl");
        }
    }

    public final class ProviderId {
        public final ComponentName componentName;
        public final int uid;

        public ProviderId(int i, ComponentName componentName) {
            this.uid = i;
            this.componentName = componentName;
        }

        public final boolean equals(Object obj) {
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

        public final String getFalseReasonForEquals(Object obj) {
            if (this == obj) {
                return "Eqauls Object";
            }
            if (obj == null) {
                return "other Null";
            }
            if (ProviderId.class != obj.getClass()) {
                return "Different Class";
            }
            ProviderId providerId = (ProviderId) obj;
            if (this.uid != providerId.uid) {
                return "Different UID";
            }
            ComponentName componentName = this.componentName;
            return componentName == null ? providerId.componentName != null ? "this componentName Null" : "Eqauls Data" : !componentName.equals(providerId.componentName) ? "Different Component Name" : "Eqauls Data";
        }

        public final int hashCode() {
            int i = this.uid * 31;
            ComponentName componentName = this.componentName;
            return i + (componentName != null ? componentName.hashCode() : 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ProviderId{user:");
            int i = this.uid;
            sb.append(UserHandle.getUserId(i));
            sb.append(", app:");
            sb.append(UserHandle.getAppId(i));
            sb.append(", cmp:");
            sb.append(this.componentName);
            sb.append('}');
            return sb.toString();
        }
    }

    public final class SaveStateRunnable implements Runnable {
        public final int mUserId;

        public SaveStateRunnable(int i) {
            this.mUserId = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (AppWidgetServiceImpl.this.mLock) {
                Trace.traceBegin(64L, "convert_state_and_io");
                AppWidgetServiceImpl.this.ensureGroupStateLoadedLocked(this.mUserId, false);
                AppWidgetServiceImpl.m251$$Nest$msaveStateLocked(AppWidgetServiceImpl.this, this.mUserId);
                Trace.traceEnd(64L);
            }
        }
    }

    public final class SecurityPolicy {
        public SecurityPolicy() {
        }

        public static boolean isProviderInPackageForUid(Provider provider, int i, String str) {
            if (provider != null) {
                ProviderId providerId = provider.id;
                if (providerId.uid == i && providerId.componentName.getPackageName().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public final void enforceCallFromPackage(String str) {
            AppWidgetServiceImpl.this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
        }

        public final void enforceServiceExistsAndRequiresBindRemoteViewsPermission(int i, ComponentName componentName) {
            ServiceInfo serviceInfo;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                serviceInfo = AppWidgetServiceImpl.this.mPackageManager.getServiceInfo(componentName, 4096L, i);
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            if (serviceInfo == null) {
                throw new SecurityException("Service " + componentName + " not installed for user " + i);
            }
            if (!"android.permission.BIND_REMOTEVIEWS".equals(serviceInfo.permission)) {
                throw new SecurityException("Service " + componentName + " in user " + i + "does not require android.permission.BIND_REMOTEVIEWS");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }

        public final int[] getEnabledGroupProfileIds(int i) {
            AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
            int profileParent = appWidgetServiceImpl.mSecurityPolicy.getProfileParent(i);
            if (profileParent != -10) {
                i = profileParent;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return appWidgetServiceImpl.mUserManager.getEnabledProfileIds(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getProfileParent(int i) {
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

        public final boolean hasCallerBindPermissionOrBindWhiteListedLocked(String str) {
            try {
                AppWidgetServiceImpl.this.mContext.enforceCallingOrSelfPermission("android.permission.BIND_APPWIDGET", null);
            } catch (SecurityException unused) {
                int callingUserId = UserHandle.getCallingUserId();
                if (AppWidgetServiceImpl.this.getUidForPackage(callingUserId, str) < 0) {
                    throw new IllegalArgumentException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUserId, "No package ", str, " for user "));
                }
                synchronized (AppWidgetServiceImpl.this.mLock) {
                    try {
                        AppWidgetServiceImpl.this.ensureGroupStateLoadedLocked(callingUserId, true);
                        if (!AppWidgetServiceImpl.this.mPackagesWithBindWidgetPermission.contains(Pair.create(Integer.valueOf(callingUserId), str))) {
                            return false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return true;
        }

        public final boolean isCallerInstantAppLocked() {
            AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                String[] packagesForUid = appWidgetServiceImpl.mPackageManager.getPackagesForUid(callingUid);
                if (!ArrayUtils.isEmpty(packagesForUid)) {
                    boolean isInstantApp = appWidgetServiceImpl.mPackageManager.isInstantApp(packagesForUid[0], UserHandle.getUserId(callingUid));
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

        public final boolean isEnabledGroupProfile(int i) {
            int callingUserId = UserHandle.getCallingUserId();
            if (callingUserId == i || getProfileParent(i) == callingUserId) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    UserInfo userInfo = AppWidgetServiceImpl.this.mUserManager.getUserInfo(i);
                    if (userInfo != null) {
                        if (userInfo.isEnabled()) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return false;
        }

        public final boolean isInstantAppLocked(int i, String str) {
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

        public final boolean isProviderInCallerOrInProfileAndWhitelListed(int i, String str) {
            int callingUserId = UserHandle.getCallingUserId();
            if (i == callingUserId) {
                return true;
            }
            if (getProfileParent(i) != callingUserId) {
                return false;
            }
            AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
            if (appWidgetServiceImpl.mDevicePolicyManagerInternal == null) {
                return false;
            }
            if (SemDualAppManager.isDualAppId(i)) {
                return true;
            }
            return appWidgetServiceImpl.mDevicePolicyManagerInternal.getCrossProfileWidgetProviders(i).contains(str);
        }
    }

    public final class Widget {
        public int appWidgetId;
        public Host host;
        public RemoteViews maskedViews;
        public Bundle options;
        public Provider provider;
        public int restoredId;
        public RemoteViews views;
        public String transactionError = null;
        public final SparseLongArray updateSequenceNos = new SparseLongArray(2);
        public boolean trackingUpdate = false;

        public final String toString() {
            return "AppWidgetId{" + this.appWidgetId + ':' + this.host + ':' + this.provider + '}';
        }
    }

    /* renamed from: -$$Nest$mapplyResourceOverlaysToWidgetsLocked, reason: not valid java name */
    public static void m246$$Nest$mapplyResourceOverlaysToWidgetsLocked(AppWidgetServiceImpl appWidgetServiceImpl, Set set, int i, boolean z) {
        ApplicationInfo applicationInfo;
        AppWidgetProviderInfo appWidgetProviderInfo;
        ActivityInfo activityInfo;
        ApplicationInfo applicationInfo2;
        int size = appWidgetServiceImpl.mProviders.size();
        for (int i2 = 0; i2 < size; i2++) {
            Provider provider = (Provider) appWidgetServiceImpl.mProviders.get(i2);
            if (provider == null) {
                AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i2, size, "applyResourceOverlaysToWidgetsLocked provider is null. i:", " size:", "AppWidgetServiceImpl");
            } else if (provider.getUserId() == i) {
                String packageName = provider.id.componentName.getPackageName();
                if (z || ((HashSet) set).contains(packageName)) {
                    try {
                        applicationInfo = appWidgetServiceImpl.mPackageManager.getApplicationInfo(packageName, 1024L, i);
                    } catch (RemoteException e) {
                        Slog.w("AppWidgetServiceImpl", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Failed to retrieve app info for ", packageName, " userId="), e);
                        applicationInfo = null;
                    }
                    if (applicationInfo != null && (appWidgetProviderInfo = provider.info) != null && (activityInfo = appWidgetProviderInfo.providerInfo) != null && (applicationInfo2 = activityInfo.applicationInfo) != null && applicationInfo.sourceDir.equals(applicationInfo2.sourceDir)) {
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

    /* renamed from: -$$Nest$mhandleNotifyProviderChanged, reason: not valid java name */
    public static void m247$$Nest$mhandleNotifyProviderChanged(AppWidgetServiceImpl appWidgetServiceImpl, Host host, IAppWidgetHost iAppWidgetHost, int i, AppWidgetProviderInfo appWidgetProviderInfo, long j) {
        appWidgetServiceImpl.getClass();
        try {
            Slog.d("AppWidgetServiceImpl", "Trying to notify provider update");
            iAppWidgetHost.providerChanged(i, appWidgetProviderInfo);
            host.lastWidgetUpdateSequenceNo = j;
        } catch (RemoteException e) {
            synchronized (appWidgetServiceImpl.mLock) {
                try {
                    if (host.callbacks != iAppWidgetHost) {
                        Log.d("AppWidgetServiceImpl", "Skip callback clear #2.appWidgetId : " + i);
                    } else {
                        Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host.id + ", " + i, e);
                        appWidgetServiceImpl.updateHostHistoryLocked("Host dead #2 : " + host.id + ", " + i + " " + e);
                        host.callbacks = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyUpdateAppWidget, reason: not valid java name */
    public static void m248$$Nest$mhandleNotifyUpdateAppWidget(AppWidgetServiceImpl appWidgetServiceImpl, Host host, IAppWidgetHost iAppWidgetHost, int i, RemoteViews remoteViews, long j) {
        String str;
        int i2;
        HashSet hashSet;
        String str2;
        if (remoteViews == null) {
            str = "#NULL#";
        } else {
            appWidgetServiceImpl.getClass();
            str = remoteViews.getPackage();
        }
        String str3 = str;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = appWidgetServiceImpl.mActivityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses == null ? null : runningAppProcesses.iterator();
        while (it != null && it.hasNext()) {
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (next != null && (str2 = next.processName) != null && str2.equals(str3)) {
                i2 = next.pid;
                break;
            }
        }
        i2 = 0;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "handleNotifyUpdateAppWidget, appWidgetId = ", "AppWidgetServiceImpl");
        synchronized (appWidgetServiceImpl.mLock) {
            boolean z = true;
            if (remoteViews != null) {
                try {
                    int sequenceNumber = remoteViews.getSequenceNumber();
                    Integer num = i2 == 0 ? null : (Integer) appWidgetServiceImpl.mLastSeqNumber.get(Integer.valueOf(i2));
                    int intValue = num == null ? 800 : num.intValue();
                    if (i2 != 0 && sequenceNumber > intValue + 200) {
                        appWidgetServiceImpl.mLastSeqNumber.put(Integer.valueOf(i2), Integer.valueOf(sequenceNumber));
                        int fdFromPackage = appWidgetServiceImpl.getFdFromPackage(i2, str3);
                        File[] listFiles = appWidgetServiceImpl.mFdFile.listFiles();
                        int length = listFiles == null ? 0 : listFiles.length;
                        if (fdFromPackage > 64) {
                            Widget lookupWidgetLocked = appWidgetServiceImpl.lookupWidgetLocked(i, host.id);
                            appWidgetServiceImpl.deleteAppWidgetLocked(lookupWidgetLocked);
                            Intent intent = new Intent("com.sec.android.launcher.action.UNBIND_WIDGET");
                            intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
                            appWidgetServiceImpl.sendBroadcastAsUser(intent, lookupWidgetLocked.provider.info.getProfile(), true);
                            Intent intent2 = new Intent("com.samsung.android.appwidget.action.APPWIDGET_UNBIND");
                            intent2.putExtra("appWidgetPackageName", str3);
                            appWidgetServiceImpl.sendBroadcastAsUser(intent2, lookupWidgetLocked.provider.info.getProfile(), true);
                            System.gc();
                            Slog.e("AppWidgetServiceImpl", "Detected abnormal operation#1. seqNumber=" + sequenceNumber + " pidFd=" + fdFromPackage + " serverFd=" + length + " kill widget=" + lookupWidgetLocked);
                            return;
                        }
                        Log.d("AppWidgetServiceImpl", "handleNotifyUpdateAppWidget(" + i + ") mLastSeqNumber=" + appWidgetServiceImpl.mLastSeqNumber + "%n views.estimateMemoryUsage()=" + remoteViews.estimateMemoryUsage() + String.format("%nTotal Memory : %6.2f MB", Double.valueOf(Runtime.getRuntime().totalMemory() / 1048576.0d)) + " pidFd=" + fdFromPackage + " serverFd=" + length);
                    } else if (sequenceNumber < intValue - 200) {
                        HashMap hashMap = appWidgetServiceImpl.mLastSeqNumber;
                        Integer valueOf = Integer.valueOf(i2);
                        if (sequenceNumber < 800) {
                            sequenceNumber = 800;
                        }
                        hashMap.put(valueOf, Integer.valueOf(sequenceNumber));
                    }
                } finally {
                }
            }
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("Trying to notify widget update for package ");
                sb.append(remoteViews == null ? "null" : remoteViews.getPackage());
                sb.append(" with widget id: ");
                sb.append(i);
                Slog.d("AppWidgetServiceImpl", sb.toString());
                iAppWidgetHost.updateAppWidget(i, remoteViews);
                host.lastWidgetUpdateSequenceNo = j;
                if (i2 != 0) {
                    String str4 = (String) appWidgetServiceImpl.mPidToPackageMap.get(Integer.valueOf(i2));
                    if (str4 != null && (hashSet = (HashSet) appWidgetServiceImpl.mPackageToPidMap.get(str4)) != null) {
                        hashSet.remove(Integer.valueOf(i2));
                    }
                    appWidgetServiceImpl.mPidToPackageMap.put(Integer.valueOf(i2), str3);
                    HashSet hashSet2 = (HashSet) appWidgetServiceImpl.mPackageToPidMap.get(str3);
                    if (hashSet2 == null) {
                        hashSet2 = new HashSet();
                    }
                    hashSet2.add(Integer.valueOf(i2));
                    appWidgetServiceImpl.mPackageToPidMap.put(str3, hashSet2);
                }
            } catch (RemoteException e) {
                synchronized (appWidgetServiceImpl.mLock) {
                    try {
                        boolean z2 = host.callbacks != iAppWidgetHost;
                        if (z2) {
                            Log.d("AppWidgetServiceImpl", "Skip callback clear #1.appWidgetId : " + i);
                        } else if (e instanceof TransactionTooLargeException) {
                            Log.d("AppWidgetServiceImpl", "Skip callback clear #1.appWidgetId : " + i + " by TransactionTooLargeException. views.getPackage() = " + str3);
                            Widget lookupWidgetLocked2 = appWidgetServiceImpl.lookupWidgetLocked(i, host.id);
                            if (lookupWidgetLocked2 != null) {
                                lookupWidgetLocked2.transactionError = toTimestampFormat(e.toString());
                            }
                            File[] listFiles2 = appWidgetServiceImpl.mFdFile.listFiles();
                            if (appWidgetServiceImpl.getFdFromPackage(i2, str3) <= 64) {
                                Log.d("AppWidgetServiceImpl", "TransactionTooLargeException, App occupied fd are under 64, not kill widget");
                            } else if (listFiles2 == null || lookupWidgetLocked2 == null) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("TransactionTooLargeException, failed to remove widget because : (fds == null) = ");
                                if (listFiles2 != null) {
                                    z = false;
                                }
                                sb2.append(z);
                                sb2.append(", w = ");
                                sb2.append(lookupWidgetLocked2);
                                Log.d("AppWidgetServiceImpl", sb2.toString());
                            } else {
                                Log.d("AppWidgetServiceImpl", "TransactionTooLargeException, App occupied fd are over 64, system fd count = " + listFiles2.length + ", kill widget, w = " + lookupWidgetLocked2);
                                appWidgetServiceImpl.deleteAppWidgetLocked(lookupWidgetLocked2);
                                Intent intent3 = new Intent("com.sec.android.launcher.action.UNBIND_WIDGET");
                                intent3.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
                                appWidgetServiceImpl.sendBroadcastAsUser(intent3, lookupWidgetLocked2.provider.info.getProfile(), true);
                                Intent intent4 = new Intent("com.samsung.android.appwidget.action.APPWIDGET_UNBIND");
                                intent4.putExtra("appWidgetPackageName", str3);
                                appWidgetServiceImpl.sendBroadcastAsUser(intent4, lookupWidgetLocked2.provider.info.getProfile(), true);
                                System.gc();
                            }
                        } else {
                            host.callbacks = null;
                        }
                        Slog.e("AppWidgetServiceImpl", "Widget host dead: " + host.id, e);
                        appWidgetServiceImpl.updateHostHistoryLocked("Dead host #1 : " + host.id + " appWidgetId : " + i + " " + e + " " + z2);
                    } finally {
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$monEmergencyModeDisabled, reason: not valid java name */
    public static void m249$$Nest$monEmergencyModeDisabled(AppWidgetServiceImpl appWidgetServiceImpl) {
        appWidgetServiceImpl.getClass();
        Log.e("AppWidgetServiceImpl", "onEmergencyModeDisabled().0");
        synchronized (appWidgetServiceImpl.mLock) {
            try {
                appWidgetServiceImpl.ensureGroupStateLoadedLocked(0, true);
                Iterator it = new ArrayList(appWidgetServiceImpl.mProviders).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Provider provider = (Provider) it.next();
                    if (provider.getUserId() == 0) {
                        String packageName = provider.info.provider.getPackageName();
                        if (provider.widgets.size() > 0) {
                            z |= appWidgetServiceImpl.updateProvidersForPackageLocked(packageName, null, 0, false);
                        }
                    }
                }
                if (z) {
                    appWidgetServiceImpl.saveGroupStateAsync(0);
                    appWidgetServiceImpl.scheduleNotifyGroupHostsForProvidersChangedLocked(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0136, code lost:
    
        if (r1 == null) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x013e, code lost:
    
        if (r1.getBoolean("android.intent.extra.REPLACING", false) != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0140, code lost:
    
        r1 = true;
     */
    /* renamed from: -$$Nest$monPackageBroadcastReceived, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m250$$Nest$monPackageBroadcastReceived(com.android.server.appwidget.AppWidgetServiceImpl r16, android.content.Intent r17, int r18) {
        /*
            Method dump skipped, instructions count: 630
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.m250$$Nest$monPackageBroadcastReceived(com.android.server.appwidget.AppWidgetServiceImpl, android.content.Intent, int):void");
    }

    /* renamed from: -$$Nest$msaveStateLocked, reason: not valid java name */
    public static void m251$$Nest$msaveStateLocked(AppWidgetServiceImpl appWidgetServiceImpl, int i) {
        boolean z;
        appWidgetServiceImpl.tagProvidersAndHosts();
        int[] enabledGroupProfileIds = appWidgetServiceImpl.mSecurityPolicy.getEnabledGroupProfileIds(i);
        try {
            z = MaintenanceModeUtils.isMaintenanceModeUser(ActivityManager.getService().getCurrentUser());
        } catch (RemoteException e) {
            Log.e("AppWidgetServiceImpl", "Couldn't get user info", e);
            z = false;
        }
        for (int i2 : enabledGroupProfileIds) {
            if (!z || i2 == 77) {
                AtomicFile savedStateFile = getSavedStateFile(i2);
                try {
                    FileOutputStream startWrite = savedStateFile.startWrite();
                    if (appWidgetServiceImpl.writeProfileStateToStreamLocked(startWrite, i2)) {
                        savedStateFile.finishWrite(startWrite);
                    } else {
                        savedStateFile.failWrite(startWrite);
                        Slog.w("AppWidgetServiceImpl", "Failed to save state, restoring backup.");
                    }
                } catch (IOException e2) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m("Failed open state file for write: ", e2, "AppWidgetServiceImpl");
                }
            }
        }
    }

    /* renamed from: -$$Nest$mupdateWidgetPackageSuspensionMaskedState, reason: not valid java name */
    public static void m252$$Nest$mupdateWidgetPackageSuspensionMaskedState(AppWidgetServiceImpl appWidgetServiceImpl, Intent intent, boolean z, int i) {
        appWidgetServiceImpl.getClass();
        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
        if (stringArrayExtra == null) {
            return;
        }
        ArraySet arraySet = new ArraySet(Arrays.asList(stringArrayExtra));
        synchronized (appWidgetServiceImpl.mLock) {
            try {
                int size = appWidgetServiceImpl.mProviders.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Provider provider = (Provider) appWidgetServiceImpl.mProviders.get(i2);
                    if (provider == null) {
                        Log.i("AppWidgetServiceImpl", "updateWidgetPackageSuspensionMaskedState provider is null. i:" + i2 + " size:" + size);
                    } else if (provider.getUserId() == i && arraySet.contains(provider.id.componentName.getPackageName())) {
                        boolean z2 = provider.maskedBySuspendedPackage;
                        provider.maskedBySuspendedPackage = z;
                        if (z != z2) {
                            if (provider.isMaskedLocked()) {
                                appWidgetServiceImpl.maskWidgetsViewsLocked(provider, null);
                            } else {
                                appWidgetServiceImpl.unmaskWidgetsViewsLocked(provider);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static {
        boolean z = Build.IS_DEBUGGABLE;
        DEBUG = z;
        MIN_UPDATE_PERIOD = z ? 0 : 1800000;
        UPDATE_COUNTER = new AtomicLong();
        DEFAULT_GENERATED_PREVIEW_RESET_INTERVAL_MS = Duration.ofHours(1L).toMillis();
        APPWIDGET_WIDGET_SUPPORTED_SIZES = new int[]{1, 2, 4, 8, 16, 32, 64};
    }

    public AppWidgetServiceImpl(Context context) {
        this.mContext = context;
    }

    public static AppWidgetProviderInfo createPartialProviderInfo(ProviderId providerId, ResolveInfo resolveInfo, Provider provider) {
        Bundle bundle = resolveInfo.activityInfo.metaData;
        if (bundle == null) {
            return null;
        }
        if (!((provider == null || TextUtils.isEmpty(provider.infoTag) || bundle.getInt(provider.infoTag) == 0) ? false : true) && !(bundle.getInt("android.appwidget.provider") != 0)) {
            return null;
        }
        AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
        appWidgetProviderInfo.provider = providerId.componentName;
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        appWidgetProviderInfo.providerInfo = activityInfo;
        if (DEBUG) {
            Objects.requireNonNull(activityInfo);
        }
        return appWidgetProviderInfo;
    }

    public static void ensureTemplateWidgetPropertyCombinationIsValid(int i, int i2) {
        if ((i2 & 1) == 0 && (i2 & 2) == 0) {
            throw new IllegalArgumentException(NandswapManager$$ExternalSyntheticOutline0.m(i2, " is not a valid widget category"));
        }
        int length = (1 << APPWIDGET_WIDGET_SUPPORTED_SIZES.length) - 1;
        if (i <= 0 || i > length) {
            throw new IllegalArgumentException(NandswapManager$$ExternalSyntheticOutline0.m(i, " is not a valid widget category combination"));
        }
    }

    public static void ensureWidgetCategoryCombinationIsValid(int i) {
        if ((i & (-8)) != 0) {
            throw new IllegalArgumentException(NandswapManager$$ExternalSyntheticOutline0.m(i, " is not a valid widget category combination"));
        }
    }

    public static AtomicFile getSavedStateFile(int i) {
        File userSystemDirectory = Environment.getUserSystemDirectory(i);
        File file = new File(Environment.getUserSystemDirectory(i), "appwidgets.xml");
        if (!file.exists() && i == 0) {
            if (!userSystemDirectory.exists()) {
                userSystemDirectory.mkdirs();
            }
            new File("/data/system/appwidgets.xml").renameTo(file);
        }
        return new AtomicFile(file);
    }

    public static int[] getWidgetIds(ArrayList arrayList) {
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((Widget) arrayList.get(i)).appWidgetId;
        }
        return iArr;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.appwidget.AppWidgetProviderInfo parseAppWidgetProviderInfo(android.content.Context r12, com.android.server.appwidget.AppWidgetServiceImpl.ProviderId r13, android.content.pm.ActivityInfo r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.parseAppWidgetProviderInfo(android.content.Context, com.android.server.appwidget.AppWidgetServiceImpl$ProviderId, android.content.pm.ActivityInfo, java.lang.String):android.appwidget.AppWidgetProviderInfo");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.os.Bundle parseWidgetIdOptions(com.android.modules.utils.TypedXmlPullParser r6) {
        /*
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            r1 = 0
            java.lang.String r2 = "restore_completed"
            r3 = 0
            boolean r2 = r6.getAttributeBoolean(r1, r2, r3)
            if (r2 == 0) goto L17
            java.lang.String r2 = "appWidgetRestoreCompleted"
            r3 = 1
            r0.putBoolean(r2, r3)
        L17:
            java.lang.String r2 = "min_width"
            r3 = -1
            int r2 = r6.getAttributeIntHex(r1, r2, r3)
            if (r2 == r3) goto L27
            java.lang.String r4 = "appWidgetMinWidth"
            r0.putInt(r4, r2)
        L27:
            java.lang.String r2 = "min_height"
            int r2 = r6.getAttributeIntHex(r1, r2, r3)
            if (r2 == r3) goto L36
            java.lang.String r4 = "appWidgetMinHeight"
            r0.putInt(r4, r2)
        L36:
            java.lang.String r2 = "max_width"
            int r2 = r6.getAttributeIntHex(r1, r2, r3)
            if (r2 == r3) goto L45
            java.lang.String r4 = "appWidgetMaxWidth"
            r0.putInt(r4, r2)
        L45:
            java.lang.String r2 = "max_height"
            int r2 = r6.getAttributeIntHex(r1, r2, r3)
            if (r2 == r3) goto L54
            java.lang.String r4 = "appWidgetMaxHeight"
            r0.putInt(r4, r2)
        L54:
            java.lang.String r2 = "sizes"
            java.lang.String r2 = r6.getAttributeValue(r1, r2)
            if (r2 == 0) goto L90
            boolean r4 = r2.isEmpty()
            if (r4 == 0) goto L64
            goto L90
        L64:
            java.lang.String r4 = ","
            java.lang.String[] r2 = r2.split(r4)     // Catch: java.lang.NumberFormatException -> L88
            java.util.stream.Stream r2 = java.util.Arrays.stream(r2)     // Catch: java.lang.NumberFormatException -> L88
            com.android.server.appwidget.AppWidgetXmlUtil$$ExternalSyntheticLambda0 r4 = new com.android.server.appwidget.AppWidgetXmlUtil$$ExternalSyntheticLambda0     // Catch: java.lang.NumberFormatException -> L88
            r5 = 0
            r4.<init>(r5)     // Catch: java.lang.NumberFormatException -> L88
            java.util.stream.Stream r2 = r2.map(r4)     // Catch: java.lang.NumberFormatException -> L88
            com.android.server.appwidget.AppWidgetXmlUtil$$ExternalSyntheticLambda1 r4 = new com.android.server.appwidget.AppWidgetXmlUtil$$ExternalSyntheticLambda1     // Catch: java.lang.NumberFormatException -> L88
            r4.<init>()     // Catch: java.lang.NumberFormatException -> L88
            java.util.stream.Collector r4 = java.util.stream.Collectors.toCollection(r4)     // Catch: java.lang.NumberFormatException -> L88
            java.lang.Object r2 = r2.collect(r4)     // Catch: java.lang.NumberFormatException -> L88
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch: java.lang.NumberFormatException -> L88
            goto L91
        L88:
            r2 = move-exception
            java.lang.String r4 = "AppWidgetXmlUtil"
            java.lang.String r5 = "Error parsing widget sizes"
            android.util.Slog.e(r4, r5, r2)
        L90:
            r2 = r1
        L91:
            if (r2 == 0) goto L99
            java.lang.String r4 = "appWidgetSizes"
            r0.putParcelableArrayList(r4, r2)
        L99:
            java.lang.String r2 = "host_category"
            int r2 = r6.getAttributeIntHex(r1, r2, r3)
            if (r2 == r3) goto La8
            java.lang.String r4 = "appWidgetCategory"
            r0.putInt(r4, r2)
        La8:
            java.lang.String r2 = "column_span"
            int r2 = r6.getAttributeIntHex(r1, r2, r3)
            if (r2 == r3) goto Lb7
            java.lang.String r4 = "semAppWidgetColumnSpan"
            r0.putInt(r4, r2)
        Lb7:
            java.lang.String r2 = "row_span"
            int r6 = r6.getAttributeIntHex(r1, r2, r3)
            if (r6 == r3) goto Lc6
            java.lang.String r1 = "semAppWidgetRowSpan"
            r0.putInt(r1, r6)
        Lc6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.parseWidgetIdOptions(com.android.modules.utils.TypedXmlPullParser):android.os.Bundle");
    }

    public static void serializeAppWidget(TypedXmlSerializer typedXmlSerializer, Widget widget, boolean z) {
        typedXmlSerializer.startTag((String) null, "g");
        typedXmlSerializer.attributeIntHex((String) null, "id", widget.appWidgetId);
        typedXmlSerializer.attributeIntHex((String) null, "rid", widget.restoredId);
        typedXmlSerializer.attributeIntHex((String) null, "h", widget.host.tag);
        Provider provider = widget.provider;
        if (provider != null) {
            typedXmlSerializer.attributeIntHex((String) null, KnoxAnalyticsDataConverter.PAYLOAD, provider.tag);
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
            typedXmlSerializer.attributeIntHex((String) null, "host_category", widget.options.getInt("appWidgetCategory"));
            ArrayList parcelableArrayList = widget.options.getParcelableArrayList("appWidgetSizes", SizeF.class);
            if (parcelableArrayList != null) {
                typedXmlSerializer.attribute((String) null, "sizes", (String) parcelableArrayList.stream().map(new AppWidgetXmlUtil$$ExternalSyntheticLambda0(1)).collect(Collectors.joining(",")));
            }
            if (z) {
                typedXmlSerializer.attributeBoolean((String) null, "restore_completed", widget.options.getBoolean("appWidgetRestoreCompleted"));
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

    public static void serializeHost(TypedXmlSerializer typedXmlSerializer, Host host) {
        typedXmlSerializer.startTag((String) null, "h");
        typedXmlSerializer.attribute((String) null, "pkg", host.id.packageName);
        typedXmlSerializer.attributeIntHex((String) null, "id", host.id.hostId);
        typedXmlSerializer.attributeIntHex((String) null, "tag", host.tag);
        typedXmlSerializer.endTag((String) null, "h");
    }

    public static void serializeProvider(TypedXmlSerializer typedXmlSerializer, Provider provider, boolean z) {
        typedXmlSerializer.startTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
        typedXmlSerializer.attribute((String) null, "pkg", provider.id.componentName.getPackageName());
        typedXmlSerializer.attribute((String) null, "cl", provider.id.componentName.getClassName());
        typedXmlSerializer.attributeIntHex((String) null, "tag", provider.tag);
        if (!TextUtils.isEmpty(provider.infoTag)) {
            typedXmlSerializer.attribute((String) null, "info_tag", provider.infoTag);
        }
        if (z && provider.mInfoParsed) {
            AppWidgetProviderInfo appWidgetProviderInfo = provider.info;
            Objects.requireNonNull(appWidgetProviderInfo);
            typedXmlSerializer.attributeInt((String) null, "min_width", appWidgetProviderInfo.minWidth);
            typedXmlSerializer.attributeInt((String) null, "min_height", appWidgetProviderInfo.minHeight);
            typedXmlSerializer.attributeInt((String) null, "min_resize_width", appWidgetProviderInfo.minResizeWidth);
            typedXmlSerializer.attributeInt((String) null, "min_resize_height", appWidgetProviderInfo.minResizeHeight);
            typedXmlSerializer.attributeInt((String) null, "max_resize_width", appWidgetProviderInfo.maxResizeWidth);
            typedXmlSerializer.attributeInt((String) null, "max_resize_height", appWidgetProviderInfo.maxResizeHeight);
            typedXmlSerializer.attributeInt((String) null, "target_cell_width", appWidgetProviderInfo.targetCellWidth);
            typedXmlSerializer.attributeInt((String) null, "target_cell_height", appWidgetProviderInfo.targetCellHeight);
            typedXmlSerializer.attributeInt((String) null, "update_period_millis", appWidgetProviderInfo.updatePeriodMillis);
            typedXmlSerializer.attributeInt((String) null, "initial_layout", appWidgetProviderInfo.initialLayout);
            typedXmlSerializer.attributeInt((String) null, "initial_keyguard_layout", appWidgetProviderInfo.initialKeyguardLayout);
            ComponentName componentName = appWidgetProviderInfo.configure;
            if (componentName != null) {
                typedXmlSerializer.attribute((String) null, "configure", componentName.flattenToShortString());
            }
            ComponentName componentName2 = appWidgetProviderInfo.semConfigure;
            if (componentName2 != null) {
                typedXmlSerializer.attribute((String) null, "semConfigure", componentName2.flattenToShortString());
            }
            String str = appWidgetProviderInfo.label;
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "label", str);
            } else {
                Slog.e("AppWidgetXmlUtil", "Label is empty in " + appWidgetProviderInfo.provider);
            }
            typedXmlSerializer.attributeInt((String) null, KnoxCustomManagerService.ICON, appWidgetProviderInfo.icon);
            typedXmlSerializer.attributeInt((String) null, "preview_image", appWidgetProviderInfo.previewImage);
            typedXmlSerializer.attributeInt((String) null, "preview_layout", appWidgetProviderInfo.previewLayout);
            typedXmlSerializer.attributeInt((String) null, "auto_advance_view_id", appWidgetProviderInfo.autoAdvanceViewId);
            typedXmlSerializer.attributeInt((String) null, "resize_mode", appWidgetProviderInfo.resizeMode);
            typedXmlSerializer.attributeInt((String) null, "widget_category", appWidgetProviderInfo.widgetCategory);
            typedXmlSerializer.attributeInt((String) null, "widget_features", appWidgetProviderInfo.widgetFeatures);
            typedXmlSerializer.attributeInt((String) null, "description_res", appWidgetProviderInfo.descriptionRes);
            typedXmlSerializer.attributeBoolean((String) null, "provider_inheritance", appWidgetProviderInfo.isExtendedFromAppWidgetProvider);
            typedXmlSerializer.attribute((String) null, "os_fingerprint", Build.FINGERPRINT);
        }
        int size = provider.pendingDeletedWidgetIds.size();
        if (size > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < size; i++) {
                arrayList.add(String.valueOf(provider.pendingDeletedWidgetIds.get(i)));
            }
            typedXmlSerializer.attribute((String) null, "pending_deleted_ids", String.join(",", arrayList));
        }
        typedXmlSerializer.endTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
    }

    public static String toTimestampFormat(String str) {
        Calendar calendar = Calendar.getInstance();
        return String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str);
    }

    public static void writeLogToFile(String str) {
        boolean z;
        String timestampFormat = toTimestampFormat(str);
        if (!BatteryService$$ExternalSyntheticOutline0.m45m("/data/log")) {
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

    public final boolean addProviderLocked(ResolveInfo resolveInfo) {
        if ((resolveInfo.activityInfo.applicationInfo.flags & 262144) != 0) {
            return false;
        }
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
        ProviderId providerId = new ProviderId(resolveInfo.activityInfo.applicationInfo.uid, componentName);
        Provider lookupProviderLocked = lookupProviderLocked(providerId);
        if (lookupProviderLocked == null) {
            lookupProviderLocked = lookupProviderLocked(new ProviderId(-1, componentName));
        }
        AppWidgetProviderInfo createPartialProviderInfo = createPartialProviderInfo(providerId, resolveInfo, lookupProviderLocked);
        if ((com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.disablePrivateSpaceItemsOnHome() && android.multiuser.Flags.enablePrivateSpaceFeatures() && createPartialProviderInfo != null && this.mUserManager.getUserProperties(createPartialProviderInfo.getProfile()).areItemsRestrictedOnHomeScreen()) || createPartialProviderInfo == null) {
            return false;
        }
        if (lookupProviderLocked == null) {
            Provider provider = new Provider();
            provider.id = providerId;
            provider.setPartialInfoLocked(createPartialProviderInfo);
            this.mProviders.add(provider);
            return true;
        }
        if (!lookupProviderLocked.zombie || this.mSafeMode) {
            return true;
        }
        lookupProviderLocked.id = providerId;
        lookupProviderLocked.zombie = false;
        lookupProviderLocked.setPartialInfoLocked(createPartialProviderInfo);
        return true;
    }

    public final void addWidgetLocked(Widget widget) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "addWidgetLocked() " + widget);
        }
        Log.i("AppWidgetServiceImpl", "addWidgetLocked, widget: " + widget + ", widget id: " + widget.appWidgetId + ", Callers: " + Debug.getCallers(7));
        this.mWidgets.add(widget);
        onWidgetProviderAddedOrChangedLocked(widget);
    }

    public final int allocateAppWidgetId(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "allocateAppWidgetId() ", "AppWidgetServiceImpl");
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.isInstantAppLocked(callingUserId, str)) {
                    Slog.w("AppWidgetServiceImpl", "Instant package " + str + " cannot host app widgets");
                    return 0;
                }
                ensureGroupStateLoadedLocked(callingUserId, true);
                if (this.mNextAppWidgetIds.indexOfKey(callingUserId) < 0) {
                    this.mNextAppWidgetIds.put(callingUserId, 1);
                }
                int i2 = (this.mNextAppWidgetIds.indexOfKey(callingUserId) < 0 ? 1 : this.mNextAppWidgetIds.get(callingUserId)) + 1;
                this.mNextAppWidgetIds.put(callingUserId, i2);
                Host lookupOrAddHostLocked = lookupOrAddHostLocked(new HostId(Binder.getCallingUid(), i, str));
                Widget widget = new Widget();
                widget.appWidgetId = i2;
                widget.host = lookupOrAddHostLocked;
                lookupOrAddHostLocked.widgets.add(widget);
                addWidgetLocked(widget);
                saveGroupStateAsync(callingUserId);
                Slog.i("AppWidgetServiceImpl", "Allocated widget id " + i2 + " for host " + lookupOrAddHostLocked.id);
                return i2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void allocateAppWidgetIdWhileBindingLocked(int i, int i2, String str, String str2) {
        Host host;
        int callingUserId = UserHandle.getCallingUserId();
        if (this.mNextAppWidgetIds.indexOfKey(callingUserId) < 0) {
            this.mNextAppWidgetIds.put(callingUserId, 1);
        }
        Iterator it = this.mWidgets.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Widget widget = (Widget) it.next();
            if (widget.appWidgetId == i2 && (host = widget.host) != null && host.getUserId() == callingUserId) {
                z = true;
            }
        }
        if ("ifEmpty".equals(str2)) {
            if (z) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "Can not allocate ID ", " #1", "AppWidgetServiceImpl");
                return;
            }
            Host lookupOrAddHostLocked = lookupOrAddHostLocked(new HostId(Binder.getCallingUid(), i, str));
            Widget widget2 = new Widget();
            widget2.appWidgetId = i2;
            widget2.host = lookupOrAddHostLocked;
            lookupOrAddHostLocked.widgets.add(widget2);
            addWidgetLocked(widget2);
            if ((this.mNextAppWidgetIds.indexOfKey(callingUserId) >= 0 ? this.mNextAppWidgetIds.get(callingUserId) : 1) < i2) {
                this.mNextAppWidgetIds.put(callingUserId, i2);
            }
            if (this.isProductDEV) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "Allocated widget id ", " for host ");
                m.append(lookupOrAddHostLocked.id);
                Slog.i("AppWidgetServiceImpl", m.toString());
            }
        }
    }

    public final boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle) {
        boolean z;
        int i3;
        int callingUserId = UserHandle.getCallingUserId();
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i, callingUserId, "bindAppWidgetId() ", ", from ", "AppWidgetServiceImpl");
        this.mSecurityPolicy.enforceCallFromPackage(str);
        if (!this.mSecurityPolicy.isEnabledGroupProfile(i2) || !this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(i2, componentName.getPackageName())) {
            return false;
        }
        String packageName = componentName.getPackageName();
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = MARsPolicyManager.getMARsPackageInfo(mARsPolicyManager.mMARsRestrictedPackages, packageName, callingUserId);
                z = mARsPackageInfo == null ? false : mARsPackageInfo.isSCPMTarget;
            } finally {
            }
        }
        if (!z) {
            mARsPolicyManager.cancelDisablePolicy(packageName, callingUserId, 0);
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                if (!this.mSecurityPolicy.hasCallerBindPermissionOrBindWhiteListedLocked(str)) {
                    return false;
                }
                if (bundle == null || !KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str)) {
                    i3 = callingUserId;
                } else {
                    String string = bundle.getString("appWidgetIdForceAllocMode");
                    i3 = callingUserId;
                    int i4 = bundle.getInt("appWidgetIdForceAllocHostId", -1);
                    if (string != null && i4 > 0) {
                        Slog.d("AppWidgetServiceImpl", "appWidgetId : " + i + ".HostId : " + i4 + ".alloc : " + string);
                        allocateAppWidgetIdWhileBindingLocked(i4, i, str, string);
                    }
                }
                Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    Slog.e("AppWidgetServiceImpl", "Bad widget id " + i);
                    return false;
                }
                if (lookupWidgetLocked.provider != null) {
                    Slog.e("AppWidgetServiceImpl", "Widget id " + i + " already bound to: " + lookupWidgetLocked.provider.id);
                    return false;
                }
                int uidForPackage = getUidForPackage(i2, componentName.getPackageName());
                if (uidForPackage < 0) {
                    Slog.e("AppWidgetServiceImpl", "Package " + componentName.getPackageName() + " not installed  for profile " + i2);
                    return false;
                }
                Provider lookupProviderLocked = lookupProviderLocked(new ProviderId(uidForPackage, componentName));
                if (lookupProviderLocked == null) {
                    Slog.e("AppWidgetServiceImpl", "No widget provider " + componentName + " for profile " + i2 + " providers size:" + this.mProviders.size());
                    int size = this.mProviders.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        try {
                            Provider provider = (Provider) this.mProviders.get(i5);
                            if (componentName.equals(provider.id.componentName)) {
                                Slog.e("AppWidgetServiceImpl", "Have Same component but not equals reason:" + provider.id.getFalseReasonForEquals(componentName));
                                return false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Log.e("AppWidgetServiceImpl", "Exception on bindAppWidgetId" + e);
                            return false;
                        }
                    }
                    return false;
                }
                if (lookupProviderLocked.zombie) {
                    Slog.e("AppWidgetServiceImpl", "Can't bind to a 3rd party provider in safe mode " + lookupProviderLocked);
                    return false;
                }
                lookupWidgetLocked.provider = lookupProviderLocked;
                Bundle bundle2 = bundle != null ? Process.myPid() == Binder.getCallingPid() ? (Bundle) bundle.clone() : bundle : new Bundle();
                lookupWidgetLocked.options = bundle2;
                if (!bundle2.containsKey("appWidgetCategory")) {
                    lookupWidgetLocked.options.putInt("appWidgetCategory", 1);
                }
                lookupProviderLocked.widgets.add(lookupWidgetLocked);
                onWidgetProviderAddedOrChangedLocked(lookupWidgetLocked);
                if (lookupProviderLocked.widgets.size() == 1) {
                    sendEnableAndUpdateIntentLocked(lookupProviderLocked, new int[]{i});
                } else {
                    sendUpdateIntentLocked(lookupProviderLocked, new int[]{i}, true, false);
                }
                registerForBroadcastsLocked(lookupProviderLocked, getWidgetIds(lookupProviderLocked.widgets));
                saveGroupStateAsync(i3);
                Slog.i("AppWidgetServiceImpl", "Bound widget " + i + " to provider " + lookupProviderLocked.id);
                if (this.isProductDEV) {
                    Slog.i("AppWidgetServiceImpl", "Bound widget " + i + " to provider " + lookupProviderLocked.id + ", callingPackage = " + str);
                }
                return true;
            } finally {
            }
        }
    }

    public final boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, long j) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "bindRemoteViewsService() ", "AppWidgetServiceImpl");
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
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
                if (!component.getPackageName().equals(lookupWidgetLocked.provider.id.componentName.getPackageName())) {
                    throw new SecurityException("The taget service not in the same package as the widget provider");
                }
                this.mSecurityPolicy.enforceServiceExistsAndRequiresBindRemoteViewsPermission(lookupWidgetLocked.provider.getUserId(), component);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (ActivityManager.getService().bindService(iApplicationThread, iBinder, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), iServiceConnection, j & 33554433, this.mContext.getOpPackageName(), lookupWidgetLocked.provider.getUserId()) != 0) {
                        incrementAppWidgetServiceRefCount(i, Pair.create(Integer.valueOf(lookupWidgetLocked.provider.id.uid), new Intent.FilterComparison(intent)));
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
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void cancelBroadcastsLocked(Provider provider) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "cancelBroadcastsLocked() for " + provider);
        }
        final PendingIntent pendingIntent = provider.broadcast;
        if (pendingIntent != null) {
            this.mSaveStateHandler.post(new Runnable() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                    PendingIntent pendingIntent2 = pendingIntent;
                    appWidgetServiceImpl.mAlarmManager.cancel(pendingIntent2);
                    pendingIntent2.cancel();
                }
            });
            updateAlarmHistoryLocked("cancel =" + provider);
            provider.broadcast = null;
        }
    }

    public final void changeHostIds(String str, int[] iArr, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (iArr == null || iArr.length == 0) {
            return;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                for (int i2 : iArr) {
                    Widget lookupWidgetLocked = lookupWidgetLocked(i2, Binder.getCallingUid(), str);
                    if (lookupWidgetLocked != null) {
                        Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
                        Log.i("AppWidgetServiceImpl", "changeHostIds callingPackage :" + str + ", appWidgetId : " + i2 + ", host = " + lookupHostLocked);
                        if (lookupHostLocked != null) {
                            lookupWidgetLocked.host.widgets.remove(lookupWidgetLocked);
                            lookupWidgetLocked.host = lookupHostLocked;
                            lookupHostLocked.widgets.add(lookupWidgetLocked);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean clearPreviewsForUidLocked(int i) {
        boolean z;
        int size = this.mProviders.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            Provider provider = (Provider) this.mProviders.get(i2);
            if (provider.id.uid == i) {
                boolean z3 = true;
                if (provider.generatedPreviews.size() > 0) {
                    provider.generatedPreviews.clear();
                    provider.updateGeneratedPreviewCategoriesLocked();
                    z = true;
                } else {
                    z = false;
                }
                boolean z4 = z2 | z;
                if (provider.templatePreviews.size() > 0) {
                    provider.templatePreviews.clear();
                    provider.updateTemplatePreviewCategoriesLocked();
                } else {
                    z3 = false;
                }
                z2 = z4 | z3;
                if (this.mGeneratedTemplatePreviewsApiCounter != null) {
                    Log.d("AppWidgetServiceImpl", "Reset template widget preview's records due to clear of app data");
                    ApiCounter.ApiCallRecord orCreateRecord = this.mGeneratedTemplatePreviewsApiCounter.getOrCreateRecord(provider.id);
                    if (orCreateRecord != null) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        orCreateRecord.apiCallCount = 0;
                        orCreateRecord.lastResetTimeMs = elapsedRealtime;
                    }
                }
            }
        }
        return z2;
    }

    public final IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) {
        IntentSender intentSender;
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "createAppWidgetConfigIntentSender() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
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
                ActivityOptions pendingIntentCreatorBackgroundActivityStartMode = ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    intentSender = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1409286144, pendingIntentCreatorBackgroundActivityStartMode.toBundle(), new UserHandle(provider.getUserId())).getIntentSender();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return intentSender;
    }

    public final void decrementAppWidgetServiceRefCount(Widget widget) {
        Iterator it = this.mRemoteViewsServicesAppWidgets.keySet().iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            HashSet hashSet = (HashSet) this.mRemoteViewsServicesAppWidgets.get(pair);
            if (hashSet.remove(Integer.valueOf(widget.appWidgetId)) && hashSet.isEmpty() && !widget.provider.maskedByStoppedPackage) {
                final Intent intent = ((Intent.FilterComparison) pair.second).getIntent();
                ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.3
                    @Override // android.content.ServiceConnection
                    public final void onNullBinding(ComponentName componentName) {
                        AppWidgetServiceImpl.this.mContext.unbindService(this);
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        try {
                            IRemoteViewsFactory.Stub.asInterface(iBinder).onDestroy(intent);
                        } catch (RemoteException e) {
                            Slog.e("AppWidgetServiceImpl", "Error calling remove view factory", e);
                        }
                        AppWidgetServiceImpl.this.mContext.unbindService(this);
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                    }
                };
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mContext.bindServiceAsUser(intent, serviceConnection, 33554433, UserHandle.getUserHandleForUid(widget.provider.id.uid));
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    it.remove();
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
    }

    public final void deleteAllHosts() {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "deleteAllHosts() ", "AppWidgetServiceImpl");
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                boolean z = false;
                for (int size = this.mHosts.size() - 1; size >= 0; size--) {
                    Host host = (Host) this.mHosts.get(size);
                    if (host.id.uid == Binder.getCallingUid()) {
                        deleteHostLocked(host);
                        if (DEBUG) {
                            Slog.i("AppWidgetServiceImpl", "Deleted host " + host.id);
                        }
                        z = true;
                    }
                }
                if (z) {
                    saveGroupStateAsync(callingUserId);
                    if (this.isProductDEV) {
                        writeLogToFile("deleteAllHosts from " + Binder.getCallingUid() + ".u" + callingUserId);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void deleteAppWidgetId(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "deleteAppWidgetId() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    return;
                }
                deleteAppWidgetLocked(lookupWidgetLocked);
                saveGroupStateAsync(callingUserId);
                if (this.isProductDEV) {
                    Slog.i("AppWidgetServiceImpl", "Deleted widget id " + i + " for host " + lookupWidgetLocked.host.id + ", callingPackage = " + str);
                    StringBuilder sb = new StringBuilder("deleteAppWidgetId ");
                    sb.append(i);
                    sb.append(".u");
                    sb.append(callingUserId);
                    writeLogToFile(sb.toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void deleteAppWidgetLocked(Widget widget) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "deleteAppWidgetLocked() " + widget);
        }
        if (widget.provider == null) {
            GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("Provider is not yet set for widget "), widget.appWidgetId, "AppWidgetServiceImpl");
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
            if (provider.maskedByStoppedPackage) {
                provider.pendingDeletedWidgetIds.add(widget.appWidgetId);
            } else {
                ProviderId providerId = widget.provider.id;
                ComponentName componentName = providerId.componentName;
                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(providerId.uid);
                int i = widget.appWidgetId;
                Intent intent = new Intent("android.appwidget.action.APPWIDGET_DELETED");
                intent.addFlags(268435456);
                intent.setComponent(componentName);
                intent.putExtra(KnoxCustomManagerService.APPWIDGET_ID, i);
                sendBroadcastAsUser(intent, userHandleForUid, false);
            }
            if (!provider.widgets.isEmpty()) {
                registerForBroadcastsLocked(provider, getWidgetIds(provider.widgets));
                return;
            }
            cancelBroadcastsLocked(provider);
            if (provider.maskedByStoppedPackage) {
                return;
            }
            sendDisabledIntentLocked(provider);
        }
    }

    public final void deleteHost(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        boolean z = DEBUG;
        if (z) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "deleteHost() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
                if (lookupHostLocked == null) {
                    return;
                }
                deleteHostLocked(lookupHostLocked);
                saveGroupStateAsync(callingUserId);
                if (z) {
                    Slog.i("AppWidgetServiceImpl", "Deleted host " + lookupHostLocked.id);
                }
                if (this.isProductDEV) {
                    writeLogToFile("deleteHost " + i + " from " + str + ".u" + callingUserId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void deleteHostLocked(Host host) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "deleteHostLocked() " + host);
        }
        for (int size = host.widgets.size() - 1; size >= 0; size--) {
            deleteAppWidgetLocked((Widget) host.widgets.remove(size));
        }
        this.mHosts.remove(host);
        host.callbacks = null;
        Log.i("AppWidgetServiceImpl", "deleteHostLocked callbacks : null");
        updateHostHistoryLocked("Host deleted : " + host.id);
    }

    public final void deleteWidgetsLocked(Provider provider, int i) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "deleteWidgetsLocked() provider=" + provider + " userId=" + i);
        }
        for (int size = provider.widgets.size() - 1; size >= 0; size--) {
            Widget widget = (Widget) provider.widgets.get(size);
            if (i == -1 || i == widget.host.getUserId()) {
                provider.widgets.remove(size);
                updateAppWidgetInstanceLocked(widget, null, false);
                widget.host.widgets.remove(widget);
                removeWidgetLocked(widget);
                widget.provider = null;
                pruneHostLocked(widget.host);
                widget.host = null;
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "AppWidgetServiceImpl", printWriter)) {
            synchronized (this.mLock) {
                try {
                    if (strArr.length <= 0 || !"--proto".equals(strArr[0])) {
                        dumpInternalLocked(printWriter);
                    } else {
                        dumpProto(fileDescriptor);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void dumpInternalLocked(PrintWriter printWriter) {
        int size = this.mProviders.size();
        printWriter.println("Providers:");
        for (int i = 0; i < size; i++) {
            Provider provider = (Provider) this.mProviders.get(i);
            AppWidgetProviderInfo appWidgetProviderInfo = provider.info;
            printWriter.print("  [");
            printWriter.print(i);
            printWriter.print("] provider ");
            printWriter.println(provider.id);
            printWriter.print("    min=(");
            printWriter.print(appWidgetProviderInfo.minWidth);
            printWriter.print("x");
            printWriter.print(appWidgetProviderInfo.minHeight);
            printWriter.print(")   minResize=(");
            printWriter.print(appWidgetProviderInfo.minResizeWidth);
            printWriter.print("x");
            printWriter.print(appWidgetProviderInfo.minResizeHeight);
            printWriter.print(") updatePeriodMillis=");
            printWriter.print(appWidgetProviderInfo.updatePeriodMillis);
            printWriter.print(" resizeMode=");
            printWriter.print(appWidgetProviderInfo.resizeMode);
            printWriter.print(" widgetCategory=");
            printWriter.print(appWidgetProviderInfo.widgetCategory);
            printWriter.print(" autoAdvanceViewId=");
            printWriter.print(appWidgetProviderInfo.autoAdvanceViewId);
            printWriter.print(" initialLayout=#");
            printWriter.print(Integer.toHexString(appWidgetProviderInfo.initialLayout));
            printWriter.print(" initialKeyguardLayout=#");
            printWriter.print(Integer.toHexString(appWidgetProviderInfo.initialKeyguardLayout));
            printWriter.print("   zombie=");
            printWriter.println(provider.zombie);
        }
        int size2 = this.mWidgets.size();
        printWriter.println(" ");
        printWriter.println("Widgets:");
        for (int i2 = 0; i2 < size2; i2++) {
            Widget widget = (Widget) this.mWidgets.get(i2);
            printWriter.print("  [");
            printWriter.print(i2);
            printWriter.print("] id=");
            printWriter.println(widget.appWidgetId);
            printWriter.print("    host=");
            printWriter.println(widget.host.id);
            if (widget.provider != null) {
                printWriter.print("    provider=");
                printWriter.println(widget.provider.id);
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
        int size3 = this.mHosts.size();
        printWriter.println(" ");
        printWriter.println("Hosts:");
        for (int i3 = 0; i3 < size3; i3++) {
            Host host = (Host) this.mHosts.get(i3);
            printWriter.print("  [");
            printWriter.print(i3);
            printWriter.print("] hostId=");
            printWriter.println(host.id);
            printWriter.print("    callbacks=");
            printWriter.println(host.callbacks);
            printWriter.print("    widgets.size=");
            printWriter.print(host.widgets.size());
            printWriter.print(" zombie=");
            printWriter.println(host.zombie);
        }
        int size4 = this.mPackagesWithBindWidgetPermission.size();
        printWriter.println(" ");
        printWriter.println("Grants:");
        for (int i4 = 0; i4 < size4; i4++) {
            Pair pair = (Pair) this.mPackagesWithBindWidgetPermission.valueAt(i4);
            printWriter.print("  [");
            printWriter.print(i4);
            printWriter.print(']');
            printWriter.print(" user=");
            printWriter.print(pair.first);
            printWriter.print(" package=");
            printWriter.println((String) pair.second);
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
        printWriter.println(AppWidgetLogWrapper.getLogText() == null ? "" : AppWidgetLogWrapper.getLogText());
        printWriter.println(" ");
        printWriter.println("AppWidget last read provider info time:");
        printWriter.print("    startTime=");
        printWriter.println(this.mDebugMonitorStartTime);
        printWriter.print("    endTime=");
        printWriter.println(this.mDebugMonitorEndTime);
        printWriter.print("    providerCount=");
        printWriter.println(this.mDebugMonitorProviderCount);
        printWriter.print("    infoLoadTime=");
        printWriter.println(this.mDebugMonitorProviderInfoLoadTime);
    }

    public final void dumpProto(FileDescriptor fileDescriptor) {
        Slog.i("AppWidgetServiceImpl", "dump proto for " + this.mWidgets.size() + " widgets");
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        int size = this.mWidgets.size();
        for (int i = 0; i < size; i++) {
            Widget widget = (Widget) this.mWidgets.get(i);
            if (widget.host == null || widget.provider == null) {
                Slog.d("AppWidgetServiceImpl", "skip dumping widget because host or provider is null: widget.host=" + widget.host + " widget.provider=" + widget.provider);
            } else {
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1133871366145L, widget.host.getUserId() != widget.provider.getUserId());
                protoOutputStream.write(1133871366146L, widget.host.callbacks == null);
                protoOutputStream.write(1138166333443L, widget.host.id.packageName);
                protoOutputStream.write(1138166333444L, widget.provider.id.componentName.getPackageName());
                protoOutputStream.write(1138166333445L, widget.provider.id.componentName.getClassName());
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
        }
        protoOutputStream.flush();
    }

    public final void ensureGroupStateLoadedLocked(int i, boolean z) {
        int uidForPackage;
        Provider provider;
        Host host;
        if (z && !this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            throw new IllegalStateException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " must be unlocked for widgets to be available"));
        }
        if (z && isProfileWithLockedParent(i)) {
            throw new IllegalStateException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Profile ", " must have unlocked parent"));
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
        int size = this.mProviders.size();
        for (int i3 = 0; i3 < size; i3++) {
            Provider provider2 = (Provider) this.mProviders.get(i3);
            if (provider2 == null) {
                AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i3, size, "clearProvidersAndHostsTagsLocked provider is null. i:", " size:", "AppWidgetServiceImpl");
            } else {
                provider2.tag = -1;
            }
        }
        int size2 = this.mHosts.size();
        for (int i4 = 0; i4 < size2; i4++) {
            ((Host) this.mHosts.get(i4)).tag = -1;
        }
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        ArrayList arrayList = null;
        for (int i5 : array) {
            List queryIntentReceivers = queryIntentReceivers(i5, intent);
            if (queryIntentReceivers != null && !queryIntentReceivers.isEmpty()) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.addAll(queryIntentReceivers);
            }
        }
        int size3 = arrayList == null ? 0 : arrayList.size();
        String str = "loadGroupWidgetProvidersLocked, profileIds = " + Arrays.toString(array) + ", allReceivers.size() = " + size3;
        if (this.mProvidersHistoryIndex < 100) {
            String timestampFormat = toTimestampFormat(str);
            String[] strArr = this.mProvidersHistory;
            int i6 = this.mProvidersHistoryIndex;
            this.mProvidersHistoryIndex = i6 + 1;
            strArr[i6] = timestampFormat;
        }
        if (this.mProvidersHistoryIndex >= 100) {
            this.mProvidersHistoryIndex = 0;
        }
        boolean[] zArr = new boolean[size3];
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(size3);
        int i7 = 0;
        while (i7 < size3) {
            newCachedThreadPool.execute(new AddProviderLockedRunnable(countDownLatch, (ResolveInfo) arrayList.get(i7), i7, zArr));
            i7++;
            countDownLatch = countDownLatch;
        }
        CountDownLatch countDownLatch2 = countDownLatch;
        try {
            countDownLatch2.await(3000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newCachedThreadPool.shutdown();
        if (countDownLatch2.getCount() > 0) {
            Slog.e("AppWidgetServiceImpl", "unhandled receivers exist, retry add unhandled providers");
            for (int i8 = 0; i8 < size3; i8++) {
                if (!zArr[i8]) {
                    addProviderLocked((ResolveInfo) arrayList.get(i8));
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int i9 = 0;
        for (int i10 : array) {
            try {
                FileInputStream openRead = getSavedStateFile(i10).openRead();
                try {
                    i9 = readProfileStateFromFileLocked(openRead, i10, arrayList2);
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
            } catch (IOException e2) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("Failed to read state: ", e2, "AppWidgetServiceImpl");
                if (this.isProductDEV) {
                    writeLogToFile("Failed to read state.u" + i10 + " : " + e2);
                }
            }
        }
        if (i9 < 0) {
            Log.w("AppWidgetServiceImpl", "Failed to read state, clearing widgets and hosts.");
            if (DEBUG) {
                Slog.i("AppWidgetServiceImpl", "clearWidgetsLocked()");
            }
            this.mWidgets.clear();
            synchronized (this.mWidgetPackagesLock) {
                this.mWidgetPackages.clear();
            }
            this.mHosts.clear();
            int size4 = this.mProviders.size();
            for (int i11 = 0; i11 < size4; i11++) {
                if (this.mProviders.get(i11) == null) {
                    Log.e("AppWidgetServiceImpl", i11 + " is null, pre total size = " + size4 + ",but  now = " + this.mProviders.size());
                } else {
                    ((Provider) this.mProviders.get(i11)).widgets.clear();
                }
            }
            return;
        }
        for (int size5 = arrayList2.size() - 1; size5 >= 0; size5--) {
            LoadedWidgetState loadedWidgetState = (LoadedWidgetState) arrayList2.remove(size5);
            Widget widget = loadedWidgetState.widget;
            int i12 = loadedWidgetState.providerTag;
            if (i12 >= 0) {
                int size6 = this.mProviders.size();
                for (int i13 = 0; i13 < size6; i13++) {
                    provider = (Provider) this.mProviders.get(i13);
                    if (provider != null) {
                        if (provider.tag == i12) {
                            break;
                        }
                    } else {
                        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i13, size6, "findProviderByTag provider is null. i:", " size:", "AppWidgetServiceImpl");
                    }
                }
            }
            provider = null;
            widget.provider = provider;
            if (provider != null) {
                int i14 = loadedWidgetState.hostTag;
                if (i14 >= 0) {
                    int size7 = this.mHosts.size();
                    for (int i15 = 0; i15 < size7; i15++) {
                        host = (Host) this.mHosts.get(i15);
                        if (host.tag == i14) {
                            break;
                        }
                    }
                }
                host = null;
                widget.host = host;
                if (host != null) {
                    widget.provider.widgets.add(widget);
                    widget.host.widgets.add(widget);
                    addWidgetLocked(widget);
                }
            }
        }
        if (i9 < 1) {
            Slog.v("AppWidgetServiceImpl", "Upgrading widget database from " + i9 + " to 1");
        }
        if (i9 == 0) {
            Host lookupHostLocked = lookupHostLocked(new HostId(Process.myUid(), 1262836039, "android"));
            if (lookupHostLocked != null && (uidForPackage = getUidForPackage(0, "com.android.keyguard")) >= 0) {
                lookupHostLocked.id = new HostId(uidForPackage, 1262836039, "com.android.keyguard");
            }
            i9 = 1;
        }
        if (i9 != 1) {
            throw new IllegalStateException("Failed to upgrade widget database");
        }
    }

    public final List getAllProvidersForProfile(int i, int i2, boolean z) {
        SemPersonaManager semPersonaManager;
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i2, "getAllProvidersForProfile() ", "AppWidgetServiceImpl");
        }
        synchronized (this.mLock) {
            boolean z2 = true;
            try {
                ensureGroupStateLoadedLocked(i2, true);
                int size = this.mProviders.size();
                this.mContext.getPackageManager();
                ArrayList arrayList = null;
                for (int i3 = 0; i3 < size; i3++) {
                    Provider provider = (Provider) this.mProviders.get(i3);
                    if (provider == null) {
                        Log.e("AppWidgetServiceImpl", "getAllProvidersForProfile provider is null skip index: " + i3 + " size:" + size);
                    } else {
                        AppWidgetProviderInfo appWidgetProviderInfo = provider.info;
                        if (!provider.zombie && (appWidgetProviderInfo.widgetCategory & i) != 0 && appWidgetProviderInfo.getProfile().getIdentifier() == i2) {
                            if (z) {
                                SecurityPolicy securityPolicy = this.mSecurityPolicy;
                                String packageName = provider.id.componentName.getPackageName();
                                AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                                if (!(appWidgetServiceImpl.mDevicePolicyManagerInternal == null ? false : SemDualAppManager.isDualAppId(i2) ? true : appWidgetServiceImpl.mDevicePolicyManagerInternal.getCrossProfileWidgetProviders(i2).contains(packageName))) {
                                }
                            }
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            if (Process.myPid() == Binder.getCallingPid()) {
                                appWidgetProviderInfo = appWidgetProviderInfo.clone();
                            }
                            arrayList.add(appWidgetProviderInfo);
                        }
                    }
                }
                if (arrayList != null) {
                    if (!SemPersonaManager.isDoEnabled(0) || (semPersonaManager = this.mSpm) == null || !semPersonaManager.isAppSeparationPresent()) {
                        z2 = false;
                    }
                    if (z2) {
                        return updateAppWidgetProviderInfoListWithAppSeparation(arrayList);
                    }
                }
                return arrayList;
            } finally {
            }
        }
    }

    public final Map getAllWidgets(String str, int i) {
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
            if (UserHandle.getUserId(host.id.uid) == i) {
                boolean z = str == null;
                if (!z) {
                    if (!z && str.equals(host.id.packageName)) {
                    }
                }
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
        return hashMap;
    }

    public final int[] getAppWidgetIds(ComponentName componentName) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "getAppWidgetIds() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Provider lookupProviderLocked = lookupProviderLocked(new ProviderId(Binder.getCallingUid(), componentName));
                if (lookupProviderLocked != null) {
                    return getWidgetIds(lookupProviderLocked.widgets);
                }
                return new int[0];
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int[] getAppWidgetIdsForHost(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "getAppWidgetIdsForHost() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
                if (lookupHostLocked != null) {
                    return getWidgetIds(lookupHostLocked.widgets);
                }
                return new int[0];
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final AppWidgetProviderInfo getAppWidgetInfo(String str, int i) {
        Provider provider;
        int callingUserId = UserHandle.getCallingUserId();
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i, callingUserId, "getAppWidgetInfo() ", ", from ", "AppWidgetServiceImpl");
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                if (lookupWidgetLocked != null && (provider = lookupWidgetLocked.provider) != null && !provider.zombie) {
                    AppWidgetProviderInfo infoLocked = provider.getInfoLocked(this.mContext);
                    if (infoLocked == null) {
                        Slog.e("AppWidgetServiceImpl", "getAppWidgetInfo() returns null because widget.provider.getInfoLocked() returned null. appWidgetId=" + i + " userId=" + callingUserId + " widget=" + lookupWidgetLocked);
                        return null;
                    }
                    AppWidgetProviderInfo clone = Process.myPid() == Binder.getCallingPid() ? infoLocked.clone() : infoLocked;
                    if (clone == null) {
                        Slog.e("AppWidgetServiceImpl", "getAppWidgetInfo() returns null because cloneIfLocalBinder() returned null. appWidgetId=" + i + " userId=" + callingUserId + " widget=" + lookupWidgetLocked + " appWidgetProviderInfo=" + infoLocked);
                    }
                    return clone;
                }
                if (lookupWidgetLocked == null) {
                    Slog.e("AppWidgetServiceImpl", "getAppWidgetInfo() returns null because widget is null. appWidgetId=" + i + " userId=" + callingUserId);
                } else if (lookupWidgetLocked.provider == null) {
                    Slog.e("AppWidgetServiceImpl", "getAppWidgetInfo() returns null because widget.provider is null. appWidgetId=" + i + " userId=" + callingUserId + " widget=" + lookupWidgetLocked);
                } else {
                    Slog.e("AppWidgetServiceImpl", "getAppWidgetInfo() returns null because widget.provider is zombie. appWidgetId=" + i + " userId=" + callingUserId + " widget=" + lookupWidgetLocked);
                }
                if (lookupWidgetLocked != null) {
                    Log.e("AppWidgetServiceImpl", "widget provider = " + lookupWidgetLocked.provider + " = " + i + ", " + callingUserId);
                    if (lookupWidgetLocked.provider != null) {
                        Log.e("AppWidgetServiceImpl", "widget provider zombi = " + lookupWidgetLocked.provider.zombie);
                    }
                } else {
                    Log.e("AppWidgetServiceImpl", "widget is null = " + i + ", " + callingUserId);
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Bundle getAppWidgetOptions(String str, int i) {
        Bundle bundle;
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "getAppWidgetOptions() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null || (bundle = lookupWidgetLocked.options) == null) {
                    return Bundle.EMPTY;
                }
                if (Process.myPid() == Binder.getCallingPid()) {
                    bundle = (Bundle) bundle.clone();
                }
                return bundle;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final RemoteViews getAppWidgetViews(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "getAppWidgetViews() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    return null;
                }
                RemoteViews remoteViews = lookupWidgetLocked.maskedViews;
                if (remoteViews == null) {
                    remoteViews = lookupWidgetLocked.views;
                }
                if (Process.myPid() == Binder.getCallingPid() && remoteViews != null) {
                    remoteViews = remoteViews.clone();
                }
                return remoteViews;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getCanonicalPackageName(int i, String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AppGlobals.getPackageManager().getReceiverInfo(new ComponentName(str, str2), 0L, i);
            return str;
        } catch (RemoteException unused) {
            String[] currentToCanonicalPackageNames = this.mContext.getPackageManager().currentToCanonicalPackageNames(new String[]{str});
            if (currentToCanonicalPackageNames != null && currentToCanonicalPackageNames.length > 0) {
                return currentToCanonicalPackageNames[0];
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getFdFromPackage(int i, String str) {
        StringBuilder sb;
        HashSet hashSet = (HashSet) this.mPackageToPidMap.get(str);
        if (hashSet == null) {
            hashSet = new HashSet();
        }
        if (i != 0) {
            hashSet.add(Integer.valueOf(i));
        }
        int i2 = 0;
        if (hashSet.size() > 0) {
            ArrayList arrayList = new ArrayList(hashSet);
            long uptimeMillis = SystemClock.uptimeMillis();
            BufferedReader bufferedReader = null;
            ?? r5 = 0;
            BufferedReader bufferedReader2 = null;
            try {
                try {
                    BufferedReader bufferedReader3 = new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/maps"));
                    while (true) {
                        try {
                            String readLine = bufferedReader3.readLine();
                            if (readLine == null) {
                                try {
                                    break;
                                } catch (IOException e) {
                                    e = e;
                                    sb = new StringBuilder("exception to close buffer reader : ");
                                    sb.append(e.getMessage());
                                    Log.e("AppWidgetServiceImpl", sb.toString());
                                    Log.d("AppWidgetServiceImpl", "getAshmemFdCount returning : pid = " + arrayList + ", fd count = " + i2 + " (" + (SystemClock.uptimeMillis() - uptimeMillis) + ")");
                                    return i2;
                                }
                            }
                            r5 = readLine.indexOf("dev/ashmem/Parcel Blob_");
                            if (r5 >= 0) {
                                StringBuilder sb2 = new StringBuilder();
                                int i3 = r5 + 23;
                                while (true) {
                                    int i4 = i3 + 1;
                                    r5 = readLine.charAt(i3);
                                    if (r5 < 48 || r5 > 57) {
                                        break;
                                    }
                                    sb2.append((char) r5);
                                    i3 = i4;
                                }
                                if (sb2.length() > 0) {
                                    int parseInt = Integer.parseInt(sb2.toString());
                                    r5 = arrayList.iterator();
                                    while (true) {
                                        if (!r5.hasNext()) {
                                            break;
                                        }
                                        if (((Integer) r5.next()).intValue() == parseInt) {
                                            i2++;
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader2 = bufferedReader3;
                            Log.e("AppWidgetServiceImpl", "exception to get process name by proc : " + e.getMessage());
                            bufferedReader = bufferedReader2;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                    bufferedReader = bufferedReader2;
                                } catch (IOException e3) {
                                    e = e3;
                                    sb = new StringBuilder("exception to close buffer reader : ");
                                    sb.append(e.getMessage());
                                    Log.e("AppWidgetServiceImpl", sb.toString());
                                    Log.d("AppWidgetServiceImpl", "getAshmemFdCount returning : pid = " + arrayList + ", fd count = " + i2 + " (" + (SystemClock.uptimeMillis() - uptimeMillis) + ")");
                                    return i2;
                                }
                            }
                            Log.d("AppWidgetServiceImpl", "getAshmemFdCount returning : pid = " + arrayList + ", fd count = " + i2 + " (" + (SystemClock.uptimeMillis() - uptimeMillis) + ")");
                            return i2;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader3;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e4) {
                                    Log.e("AppWidgetServiceImpl", "exception to close buffer reader : " + e4.getMessage());
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader3.close();
                    bufferedReader = r5;
                } catch (Exception e5) {
                    e = e5;
                }
                Log.d("AppWidgetServiceImpl", "getAshmemFdCount returning : pid = " + arrayList + ", fd count = " + i2 + " (" + (SystemClock.uptimeMillis() - uptimeMillis) + ")");
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return i2;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.ParceledListSlice getInstalledProvidersForProfile(int r17, int r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.getInstalledProvidersForProfile(int, int, java.lang.String):android.content.pm.ParceledListSlice");
    }

    public final ActivityInfo getProviderInfo(int i, ComponentName componentName) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setComponent(componentName);
        List queryIntentReceivers = queryIntentReceivers(i, intent);
        if (queryIntentReceivers.isEmpty()) {
            return null;
        }
        return ((ResolveInfo) queryIntentReceivers.get(0)).activityInfo;
    }

    public final Bundle getTemplateWidgetPreview(String str, ComponentName componentName, int i, int i2, int i3) {
        int identifier;
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "getTemplateWidgetPreview() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        ensureTemplateWidgetPropertyCombinationIsValid(i2, i3);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(i, true);
                int size = this.mProviders.size();
                for (int i4 = 0; i4 < size; i4++) {
                    Provider provider = (Provider) this.mProviders.get(i4);
                    ComponentName componentName2 = provider.id.componentName;
                    if (!provider.zombie && componentName.equals(componentName2) && (identifier = provider.getInfoLocked(this.mContext).getProfile().getIdentifier()) == i) {
                        int callingUid = Binder.getCallingUid();
                        String packageName = componentName2.getPackageName();
                        boolean isProviderInCallerOrInProfileAndWhitelListed = this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(identifier, packageName);
                        boolean filterAppAccess = this.mPackageManagerInternal.filterAppAccess(callingUid, identifier, packageName, true);
                        this.mSecurityPolicy.getClass();
                        boolean isProviderInPackageForUid = SecurityPolicy.isProviderInPackageForUid(provider, callingUid, str);
                        boolean hasCallerBindPermissionOrBindWhiteListedLocked = this.mSecurityPolicy.hasCallerBindPermissionOrBindWhiteListedLocked(str);
                        Log.d("AppWidgetServiceImpl", "Permission : " + isProviderInCallerOrInProfileAndWhitelListed + " " + filterAppAccess + " " + isProviderInPackageForUid + " " + hasCallerBindPermissionOrBindWhiteListedLocked);
                        if (isProviderInCallerOrInProfileAndWhitelListed && !filterAppAccess && (isProviderInPackageForUid || hasCallerBindPermissionOrBindWhiteListedLocked)) {
                            return provider.getTemplatePreviewLocked(i2, i3);
                        }
                    }
                }
                return Bundle.EMPTY;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getUidForPackage(int i, String str) {
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

    public final List getWidgetParticipants(int i) {
        Provider provider;
        BackupRestoreController backupRestoreController = this.mBackupRestoreController;
        backupRestoreController.getClass();
        Slog.i("BackupRestoreController", "Getting widget participants for user: " + i);
        HashSet hashSet = new HashSet();
        synchronized (AppWidgetServiceImpl.this.mLock) {
            try {
                int size = AppWidgetServiceImpl.this.mWidgets.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Widget widget = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i2);
                    if (widget.host.getUserId() == i && ((provider = widget.provider) == null || provider.getUserId() == i)) {
                        hashSet.add(widget.host.id.packageName);
                        Provider provider2 = widget.provider;
                        if (provider2 != null) {
                            hashSet.add(provider2.id.componentName.getPackageName());
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return new ArrayList(hashSet);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x008b, code lost:
    
        if (r6 >= r9.generatedPreviews.size()) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0095, code lost:
    
        if ((r20 & r9.generatedPreviews.keyAt(r6)) == 0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a1, code lost:
    
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0097, code lost:
    
        r8 = (android.widget.RemoteViews) r9.generatedPreviews.valueAt(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a5, code lost:
    
        return r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.widget.RemoteViews getWidgetPreview(java.lang.String r17, android.content.ComponentName r18, int r19, int r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r19
            int r3 = android.os.UserHandle.getCallingUserId()
            boolean r4 = com.android.server.appwidget.AppWidgetServiceImpl.DEBUG
            if (r4 == 0) goto L16
            java.lang.String r4 = "AppWidgetServiceImpl"
            java.lang.String r5 = "getWidgetPreview() "
            com.android.server.HermesService$3$$ExternalSyntheticOutline0.m(r3, r5, r4)
        L16:
            com.android.server.appwidget.AppWidgetServiceImpl$SecurityPolicy r3 = r0.mSecurityPolicy
            r3.enforceCallFromPackage(r1)
            ensureWidgetCategoryCombinationIsValid(r20)
            java.lang.Object r3 = r0.mLock
            monitor-enter(r3)
            r4 = 1
            r0.ensureGroupStateLoadedLocked(r2, r4)     // Catch: java.lang.Throwable -> La6
            java.util.List r5 = r0.mProviders     // Catch: java.lang.Throwable -> La6
            int r5 = r5.size()     // Catch: java.lang.Throwable -> La6
            r6 = 0
            r7 = r6
        L2d:
            r8 = 0
            if (r7 >= r5) goto Lad
            java.util.List r9 = r0.mProviders     // Catch: java.lang.Throwable -> La6
            java.lang.Object r9 = r9.get(r7)     // Catch: java.lang.Throwable -> La6
            com.android.server.appwidget.AppWidgetServiceImpl$Provider r9 = (com.android.server.appwidget.AppWidgetServiceImpl.Provider) r9     // Catch: java.lang.Throwable -> La6
            com.android.server.appwidget.AppWidgetServiceImpl$ProviderId r10 = r9.id     // Catch: java.lang.Throwable -> La6
            android.content.ComponentName r10 = r10.componentName     // Catch: java.lang.Throwable -> La6
            boolean r11 = r9.zombie     // Catch: java.lang.Throwable -> La6
            if (r11 != 0) goto La8
            r11 = r18
            boolean r12 = r11.equals(r10)     // Catch: java.lang.Throwable -> La6
            if (r12 != 0) goto L49
            goto Laa
        L49:
            android.content.Context r12 = r0.mContext     // Catch: java.lang.Throwable -> La6
            android.appwidget.AppWidgetProviderInfo r12 = r9.getInfoLocked(r12)     // Catch: java.lang.Throwable -> La6
            android.os.UserHandle r12 = r12.getProfile()     // Catch: java.lang.Throwable -> La6
            int r12 = r12.getIdentifier()     // Catch: java.lang.Throwable -> La6
            if (r12 == r2) goto L5a
            goto Laa
        L5a:
            int r13 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> La6
            java.lang.String r10 = r10.getPackageName()     // Catch: java.lang.Throwable -> La6
            com.android.server.appwidget.AppWidgetServiceImpl$SecurityPolicy r14 = r0.mSecurityPolicy     // Catch: java.lang.Throwable -> La6
            boolean r14 = r14.isProviderInCallerOrInProfileAndWhitelListed(r12, r10)     // Catch: java.lang.Throwable -> La6
            android.content.pm.PackageManagerInternal r15 = r0.mPackageManagerInternal     // Catch: java.lang.Throwable -> La6
            boolean r10 = r15.filterAppAccess(r13, r12, r10, r4)     // Catch: java.lang.Throwable -> La6
            com.android.server.appwidget.AppWidgetServiceImpl$SecurityPolicy r12 = r0.mSecurityPolicy     // Catch: java.lang.Throwable -> La6
            r12.getClass()     // Catch: java.lang.Throwable -> La6
            boolean r12 = com.android.server.appwidget.AppWidgetServiceImpl.SecurityPolicy.isProviderInPackageForUid(r9, r13, r1)     // Catch: java.lang.Throwable -> La6
            com.android.server.appwidget.AppWidgetServiceImpl$SecurityPolicy r13 = r0.mSecurityPolicy     // Catch: java.lang.Throwable -> La6
            boolean r13 = r13.hasCallerBindPermissionOrBindWhiteListedLocked(r1)     // Catch: java.lang.Throwable -> La6
            if (r14 == 0) goto Laa
            if (r10 != 0) goto Laa
            if (r12 != 0) goto L85
            if (r13 == 0) goto Laa
        L85:
            android.util.SparseArray r0 = r9.generatedPreviews     // Catch: java.lang.Throwable -> La6
            int r0 = r0.size()     // Catch: java.lang.Throwable -> La6
            if (r6 >= r0) goto La4
            android.util.SparseArray r0 = r9.generatedPreviews     // Catch: java.lang.Throwable -> La6
            int r0 = r0.keyAt(r6)     // Catch: java.lang.Throwable -> La6
            r0 = r20 & r0
            if (r0 == 0) goto La1
            android.util.SparseArray r0 = r9.generatedPreviews     // Catch: java.lang.Throwable -> La6
            java.lang.Object r0 = r0.valueAt(r6)     // Catch: java.lang.Throwable -> La6
            r8 = r0
            android.widget.RemoteViews r8 = (android.widget.RemoteViews) r8     // Catch: java.lang.Throwable -> La6
            goto La4
        La1:
            int r6 = r6 + 1
            goto L85
        La4:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La6
            return r8
        La6:
            r0 = move-exception
            goto Laf
        La8:
            r11 = r18
        Laa:
            int r7 = r7 + 1
            goto L2d
        Lad:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La6
            return r8
        Laf:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La6
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.getWidgetPreview(java.lang.String, android.content.ComponentName, int, int):android.widget.RemoteViews");
    }

    public final byte[] getWidgetState(String str, int i) {
        int i2;
        Provider provider;
        Provider provider2;
        BackupRestoreController backupRestoreController = this.mBackupRestoreController;
        backupRestoreController.getClass();
        Slog.i("BackupRestoreController", "Getting widget state for user: " + i);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        synchronized (AppWidgetServiceImpl.this.mLock) {
            try {
                AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                int size = appWidgetServiceImpl.mWidgets.size();
                for (0; i2 < size; i2 + 1) {
                    Widget widget = (Widget) appWidgetServiceImpl.mWidgets.get(i2);
                    i2 = (widget.host.getUserId() != i || !((provider = widget.provider) == null || provider.getUserId() == i) || (!widget.host.isInPackageForUser(i, str) && ((provider2 = widget.provider) == null || !provider2.isInPackageForUser(i, str)))) ? i2 + 1 : 0;
                    try {
                        TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                        newFastSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                        newFastSerializer.startDocument((String) null, Boolean.TRUE);
                        newFastSerializer.startTag((String) null, "ws");
                        newFastSerializer.attributeInt((String) null, "version", 2);
                        newFastSerializer.attribute((String) null, "pkg", str);
                        int size2 = AppWidgetServiceImpl.this.mProviders.size();
                        int i3 = 0;
                        for (int i4 = 0; i4 < size2; i4++) {
                            Provider provider3 = (Provider) AppWidgetServiceImpl.this.mProviders.get(i4);
                            if (provider3 == null) {
                                Log.i("BackupRestoreController", "getWidgetState provider is null. i:" + i4 + " size:" + size2);
                            } else if ((!provider3.widgets.isEmpty() || !TextUtils.isEmpty(provider3.infoTag)) && (provider3.isInPackageForUser(i, str) || provider3.hostedByPackageForUser(i, str))) {
                                provider3.tag = i3;
                                serializeProvider(newFastSerializer, provider3, false);
                                i3++;
                            }
                        }
                        int size3 = AppWidgetServiceImpl.this.mHosts.size();
                        int i5 = 0;
                        for (int i6 = 0; i6 < size3; i6++) {
                            Host host = (Host) AppWidgetServiceImpl.this.mHosts.get(i6);
                            if (!host.widgets.isEmpty() && (host.isInPackageForUser(i, str) || Host.m253$$Nest$mhostsPackageForUser(host, str, i))) {
                                host.tag = i5;
                                serializeHost(newFastSerializer, host);
                                i5++;
                            }
                        }
                        int size4 = AppWidgetServiceImpl.this.mWidgets.size();
                        for (int i7 = 0; i7 < size4; i7++) {
                            Widget widget2 = (Widget) AppWidgetServiceImpl.this.mWidgets.get(i7);
                            Provider provider4 = widget2.provider;
                            if (widget2.host.isInPackageForUser(i, str) || (provider4 != null && provider4.isInPackageForUser(i, str))) {
                                serializeAppWidget(newFastSerializer, widget2, false);
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
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasBindAppWidgetPermission(String str, int i) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "hasBindAppWidgetPermission() " + UserHandle.getCallingUserId());
        }
        AppWidgetServiceImpl.this.mContext.enforceCallingPermission("android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS", "hasBindAppWidgetPermission packageName=" + str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(i, true);
                if (getUidForPackage(i, str) < 0) {
                    return false;
                }
                return this.mPackagesWithBindWidgetPermission.contains(Pair.create(Integer.valueOf(i), str));
            } catch (Throwable th) {
                throw th;
            }
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

    public final boolean isBoundWidgetPackage(String str, int i) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Only the system process can call this");
        }
        synchronized (this.mWidgetPackagesLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mWidgetPackages.get(i);
                if (arraySet == null) {
                    return false;
                }
                return arraySet.contains(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isProfileWithLockedParent(int i) {
        UserInfo profileParent;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo userInfo = this.mUserManager.getUserInfo(i);
            if (userInfo != null && userInfo.isProfile() && (profileParent = this.mUserManager.getProfileParent(i)) != null) {
                if (!this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle().getIdentifier())) {
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
        return userInfo != null && userInfo.isProfile() && (profileParent = this.mUserManager.getProfileParent(i)) != null && this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle());
    }

    public final boolean isRequestPinAppWidgetSupported() {
        synchronized (this.mLock) {
            try {
                if (!this.mSecurityPolicy.isCallerInstantAppLocked()) {
                    return ((ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class)).isRequestPinItemSupported(UserHandle.getCallingUserId(), 2);
                }
                Slog.w("AppWidgetServiceImpl", "Instant uid " + Binder.getCallingUid() + " query information about app widgets");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isTemplatePreviewUpdateAvailable(ComponentName componentName) {
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
                if (lookupProviderLocked(providerId) == null) {
                    throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                ApiCounter.ApiCallRecord orCreateRecord = this.mGeneratedTemplatePreviewsApiCounter.getOrCreateRecord(providerId);
                long elapsedRealtime = SystemClock.elapsedRealtime() - orCreateRecord.lastResetTimeMs;
                ApiCounter apiCounter = this.mGeneratedTemplatePreviewsApiCounter;
                if (elapsedRealtime > apiCounter.mResetIntervalMs) {
                    return true;
                }
                if (orCreateRecord.apiCallCount < apiCounter.mMaxCallsPerInterval) {
                    return true;
                }
                Log.d("AppWidgetServiceImpl", "Time remaining until update is possible : " + (this.mGeneratedTemplatePreviewsApiCounter.mResetIntervalMs - elapsedRealtime));
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Host lookupHostLocked(HostId hostId) {
        int size = this.mHosts.size();
        for (int i = 0; i < size; i++) {
            Host host = (Host) this.mHosts.get(i);
            if (host.id.equals(hostId)) {
                return host;
            }
        }
        return null;
    }

    public final Host lookupOrAddHostLocked(HostId hostId) {
        Host lookupHostLocked = lookupHostLocked(hostId);
        if (lookupHostLocked != null) {
            return lookupHostLocked;
        }
        Host host = new Host();
        host.id = hostId;
        this.mHosts.add(host);
        Log.i("AppWidgetServiceImpl", "lookupOrAddHostLocked = " + hostId);
        return host;
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
                            str = ((Provider) this.mProviders.get(i2)).id.componentName.toString();
                        }
                    }
                    StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(size, i, "lookupProviderLocked provider is null size:", " index:", " prevComp:");
                    m.append(str);
                    Log.e("AppWidgetServiceImpl", m.toString());
                    StorageManagerService$$ExternalSyntheticOutline0.m("caller:", Debug.getCallers(5), "AppWidgetServiceImpl");
                } else if (provider.id.equals(providerId)) {
                    return provider;
                }
                i++;
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e("AppWidgetServiceImpl", "lookupProviderLocked " + e);
                return null;
            }
        }
    }

    public final Widget lookupWidgetLocked(int i, int i2, String str) {
        int userId;
        Provider provider;
        int size = this.mWidgets.size();
        for (int i3 = 0; i3 < size; i3++) {
            Widget widget = (Widget) this.mWidgets.get(i3);
            if (widget.appWidgetId == i) {
                SecurityPolicy securityPolicy = this.mSecurityPolicy;
                securityPolicy.getClass();
                HostId hostId = widget.host.id;
                if ((hostId.uid != i2 || !hostId.packageName.equals(str)) && !SecurityPolicy.isProviderInPackageForUid(widget.provider, i2, str)) {
                    Host host = widget.host;
                    Provider provider2 = widget.provider;
                    if ((host.id.uid != i2 || provider2 == null || !provider2.id.componentName.getPackageName().equals(str)) && ((widget.host.getUserId() != (userId = UserHandle.getUserId(i2)) && ((provider = widget.provider) == null || provider.getUserId() != userId)) || AppWidgetServiceImpl.this.mContext.checkCallingPermission("android.permission.BIND_APPWIDGET") != 0)) {
                        if (DEBUG) {
                            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i2, "canAccessAppWidget() failed. packageName=", str, " uid=", " userId=");
                            m.append(userId);
                            m.append(" widget=");
                            m.append(widget);
                            Slog.i("AppWidgetServiceImpl", m.toString());
                        }
                    }
                }
                return widget;
            }
        }
        if (!DEBUG) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "cannot find widget for appWidgetId=", " uid=", " packageName="), str, "AppWidgetServiceImpl");
        return null;
    }

    public final Widget lookupWidgetLocked(int i, HostId hostId) {
        Host host;
        if (i >= 0 && hostId != null) {
            Iterator it = this.mWidgets.iterator();
            while (it.hasNext()) {
                Widget widget = (Widget) it.next();
                if (widget.appWidgetId == i && (host = widget.host) != null && hostId.equals(host.id)) {
                    return widget;
                }
            }
        }
        return null;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void maskWidgetsViewsLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider r19, com.android.server.appwidget.AppWidgetServiceImpl.Widget r20) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.maskWidgetsViewsLocked(com.android.server.appwidget.AppWidgetServiceImpl$Provider, com.android.server.appwidget.AppWidgetServiceImpl$Widget):void");
    }

    public final void noteAppWidgetTapped(String str, int i) {
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
                ProviderId providerId = lookupWidgetLocked.provider.id;
                String packageName = providerId.componentName.getPackageName();
                if (packageName == null) {
                    return;
                }
                SparseArray sparseArray = new SparseArray();
                sparseArray.put(providerId.uid, packageName);
                this.mAppOpsManagerInternal.updateAppWidgetVisibility(sparseArray, true);
                int userId = UserHandle.getUserId(providerId.uid);
                if (android.app.usage.Flags.userInteractionTypeApi()) {
                    PersistableBundle persistableBundle = new PersistableBundle();
                    persistableBundle.putString("android.app.usage.extra.EVENT_CATEGORY", "android.appwidget");
                    persistableBundle.putString("android.app.usage.extra.EVENT_ACTION", "tap");
                    this.mUsageStatsManagerInternal.reportUserInteractionEvent(packageName, userId, persistableBundle);
                } else {
                    this.mUsageStatsManagerInternal.reportEvent(userId, 7, packageName);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "notifyAppWidgetViewDataChanged() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        if (iArr == null || iArr.length == 0) {
            return;
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                for (int i2 : iArr) {
                    Widget lookupWidgetLocked = lookupWidgetLocked(i2, Binder.getCallingUid(), str);
                    if (lookupWidgetLocked != null) {
                        scheduleNotifyAppWidgetViewDataChanged(lookupWidgetLocked, i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyProviderInheritance(ComponentName[] componentNameArr) {
        AppWidgetProviderInfo appWidgetProviderInfo;
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "notifyProviderInheritance() ", "AppWidgetServiceImpl");
        }
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
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                for (ComponentName componentName2 : componentNameArr) {
                    Provider lookupProviderLocked = lookupProviderLocked(new ProviderId(Binder.getCallingUid(), componentName2));
                    if (lookupProviderLocked != null && (appWidgetProviderInfo = lookupProviderLocked.info) != null) {
                        appWidgetProviderInfo.isExtendedFromAppWidgetProvider = true;
                    }
                    return;
                }
                saveGroupStateAsync(callingUserId);
            } finally {
            }
        }
    }

    public final void onCrossProfileWidgetProvidersChanged(int i, List list) {
        int profileParent = this.mSecurityPolicy.getProfileParent(i);
        if (profileParent != i) {
            synchronized (this.mLock) {
                try {
                    ArraySet arraySet = new ArraySet();
                    int size = this.mProviders.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        Provider provider = (Provider) this.mProviders.get(i2);
                        if (provider == null) {
                            Log.i("AppWidgetServiceImpl", "onCrossProfileWidgetProvidersChanged provider is null. i:" + i2 + " size:" + size);
                        } else if (provider.getUserId() == i) {
                            arraySet.add(provider.id.componentName.getPackageName());
                        }
                    }
                    int size2 = list.size();
                    boolean z = false;
                    for (int i3 = 0; i3 < size2; i3++) {
                        String str = (String) list.get(i3);
                        arraySet.remove(str);
                        z |= updateProvidersForPackageLocked(str, null, i, false);
                    }
                    int size3 = arraySet.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        removeWidgetsForPackageLocked(i, profileParent, (String) arraySet.valueAt(i4));
                    }
                    if (z || size3 > 0) {
                        saveGroupStateAsync(i);
                        scheduleNotifyGroupHostsForProvidersChangedLocked(i);
                    }
                } finally {
                }
            }
        }
    }

    public final void onWidgetProviderAddedOrChangedLocked(Widget widget) {
        Provider provider = widget.provider;
        if (provider == null) {
            return;
        }
        int userId = provider.getUserId();
        synchronized (this.mWidgetPackagesLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mWidgetPackages.get(userId);
                if (arraySet == null) {
                    SparseArray sparseArray = this.mWidgetPackages;
                    ArraySet arraySet2 = new ArraySet();
                    sparseArray.put(userId, arraySet2);
                    arraySet = arraySet2;
                }
                arraySet.add(widget.provider.id.componentName.getPackageName());
            } catch (Throwable th) {
                throw th;
            }
        }
        if (widget.provider.isMaskedLocked()) {
            maskWidgetsViewsLocked(widget.provider, widget);
        } else if (widget.maskedViews != null) {
            widget.maskedViews = null;
            widget.transactionError = null;
        }
    }

    public final void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "partiallyUpdateAppWidgetIds() " + UserHandle.getCallingUserId());
        }
        updateAppWidgetIds(str, iArr, remoteViews, true);
    }

    public final void pruneHostLocked(Host host) {
        if (host.widgets.size() == 0 && host.callbacks == null) {
            if (DEBUG) {
                Slog.i("AppWidgetServiceImpl", "Pruning host " + host.id);
            }
            this.mHosts.remove(host);
        }
    }

    public final List queryIntentReceivers(int i, Intent intent) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPackageManager.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (isProfileWithUnlockedParent(i) ? 269222016 : 268435584) | 1024, i).getList();
        } catch (RemoteException unused) {
            return Collections.emptyList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int readProfileStateFromFileLocked(java.io.FileInputStream r21, int r22, java.util.List r23) {
        /*
            Method dump skipped, instructions count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appwidget.AppWidgetServiceImpl.readProfileStateFromFileLocked(java.io.FileInputStream, int, java.util.List):int");
    }

    public final void registerForBroadcastsLocked(Provider provider, int[] iArr) {
        AppWidgetProviderInfo infoLocked = provider.getInfoLocked(this.mContext);
        if (infoLocked.updatePeriodMillis <= 0) {
            cancelBroadcastsLocked(provider);
            return;
        }
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("appWidgetIds", iArr);
        intent.setComponent(infoLocked.provider);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            provider.broadcast = PendingIntent.getBroadcastAsUser(this.mContext, 1, intent, 201326592, infoLocked.getProfile());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (provider.broadcast != null) {
                final long max = Math.max(infoLocked.updatePeriodMillis, MIN_UPDATE_PERIOD);
                final PendingIntent pendingIntent = provider.broadcast;
                this.mSaveStateHandler.post(new Runnable() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                        long j = max;
                        appWidgetServiceImpl.mAlarmManager.setInexactRepeating(2, SystemClock.elapsedRealtime() + j, j, pendingIntent);
                    }
                });
                updateAlarmHistoryLocked("add =" + provider);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void reloadWidgetsMaskedState(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo userInfo = this.mUserManager.getUserInfo(i);
            boolean z4 = true;
            boolean z5 = !this.mUserManager.isUserUnlockingOrUnlocked(i);
            boolean isQuietModeEnabled = userInfo.isQuietModeEnabled();
            boolean isSuperLocked = userInfo.isSuperLocked();
            int size = this.mProviders.size();
            int i2 = 0;
            while (i2 < size) {
                Provider provider = (Provider) this.mProviders.get(i2);
                if (provider.getUserId() == i) {
                    boolean z6 = provider.maskedByLockedProfile;
                    provider.maskedByLockedProfile = z5;
                    boolean z7 = z5 != z6 ? z4 : false;
                    boolean z8 = provider.maskedByQuietProfile;
                    provider.maskedByQuietProfile = isQuietModeEnabled;
                    boolean z9 = z7 | (isQuietModeEnabled != z8 ? z4 : false);
                    boolean z10 = provider.maskedBySuperLocked;
                    provider.maskedBySuperLocked = isSuperLocked;
                    boolean z11 = (isSuperLocked != z10 ? z4 : false) | z9;
                    try {
                        try {
                            z = this.mPackageManager.isPackageSuspendedForUser(provider.id.componentName.getPackageName(), provider.getUserId());
                            z2 = this.mPackageManager.isPackageStoppedForUser(provider.id.componentName.getPackageName(), provider.getUserId());
                        } catch (IllegalArgumentException unused) {
                            z = false;
                            z2 = false;
                        }
                        boolean z12 = provider.maskedBySuspendedPackage;
                        provider.maskedBySuspendedPackage = z;
                        boolean z13 = z11 | (z != z12);
                        boolean z14 = provider.maskedByStoppedPackage;
                        provider.maskedByStoppedPackage = z2;
                        z3 = (z2 != z14) | z13;
                    } catch (RemoteException e) {
                        Slog.e("AppWidgetServiceImpl", "Failed to query application info", e);
                        z3 = z11;
                    }
                    if (z3) {
                        if (provider.isMaskedLocked()) {
                            maskWidgetsViewsLocked(provider, null);
                        } else {
                            unmaskWidgetsViewsLocked(provider);
                        }
                    }
                }
                i2++;
                z4 = true;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reloadWidgetsMaskedStateForGroup(int i) {
        if (this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            synchronized (this.mLock) {
                try {
                    reloadWidgetsMaskedState(i);
                    for (int i2 : this.mUserManager.getEnabledProfileIds(i)) {
                        reloadWidgetsMaskedState(i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean removeHostsAndProvidersForPackageLocked(int i, String str) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "removeHostsAndProvidersForPackageLocked() pkg=" + str + " userId=" + i);
        }
        int size = this.mProviders.size();
        boolean z = false;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Provider provider = (Provider) this.mProviders.get(i2);
            if (provider == null) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i2, size, "removeProvidersForPackageLocked provider is null. i:", " size:", "pkg: "), str, "AppWidgetServiceImpl");
            } else if (str.equals(provider.id.componentName.getPackageName()) && provider.getUserId() == i) {
                deleteWidgetsLocked(provider, -1);
                this.mProviders.remove(provider);
                ((ArrayMap) this.mGeneratedPreviewsApiCounter.mCallCount).remove(provider.id);
                ((ArrayMap) this.mGeneratedTemplatePreviewsApiCounter.mCallCount).remove(provider.id);
                cancelBroadcastsLocked(provider);
                z = true;
            }
        }
        for (int size2 = this.mHosts.size() - 1; size2 >= 0; size2--) {
            Host host = (Host) this.mHosts.get(size2);
            if (str.equals(host.id.packageName) && host.getUserId() == i) {
                deleteHostLocked(host);
                z = true;
            }
        }
        return z;
    }

    public final void removeTemplateWidgetPreview(ComponentName componentName, int i, int i2) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "removeTemplateWidgetPreview() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        ensureTemplateWidgetPropertyCombinationIsValid(i, i2);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Provider lookupProviderLocked = lookupProviderLocked(new ProviderId(Binder.getCallingUid(), componentName));
                if (lookupProviderLocked == null) {
                    throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                int[] iArr = lookupProviderLocked.templateSizes;
                boolean z = false;
                for (int i3 : iArr) {
                    if ((i & i3) != 0) {
                        z |= lookupProviderLocked.templatePreviews.removeReturnOld(i3 | (i2 << iArr.length)) != null;
                    }
                }
                if (z) {
                    lookupProviderLocked.updateTemplatePreviewCategoriesLocked();
                }
                if (z) {
                    scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeWidgetLocked(Widget widget) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "removeWidgetLocked() " + widget);
        }
        Log.i("AppWidgetServiceImpl", "removeWidgetLocked, widget: " + widget + ", widget id: " + widget.appWidgetId + ", Callers: " + Debug.getCallers(7));
        this.mWidgets.remove(widget);
        Provider provider = widget.provider;
        if (provider != null) {
            int userId = provider.getUserId();
            String packageName = widget.provider.id.componentName.getPackageName();
            synchronized (this.mWidgetPackagesLock) {
                try {
                    ArraySet arraySet = (ArraySet) this.mWidgetPackages.get(userId);
                    if (arraySet != null) {
                        int size = this.mWidgets.size();
                        int i = 0;
                        while (true) {
                            if (i >= size) {
                                arraySet.remove(packageName);
                                break;
                            }
                            Widget widget2 = (Widget) this.mWidgets.get(i);
                            Provider provider2 = widget2.provider;
                            if (provider2 != null && provider2.getUserId() == userId && packageName.equals(widget2.provider.id.componentName.getPackageName())) {
                            }
                            i++;
                        }
                    }
                } finally {
                }
            }
        }
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        if (widget.trackingUpdate) {
            widget.trackingUpdate = false;
            Log.i("AppWidgetServiceImpl", "Widget removed " + widget.toString());
            Trace.asyncTraceEnd(64L, "appwidget update-intent " + widget.provider.id.toString(), widget.appWidgetId);
        }
        widget.updateSequenceNos.clear();
        Provider provider3 = widget.provider;
        if (provider3 == null || provider3.zombie) {
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

    public final void removeWidgetPreview(ComponentName componentName, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "removeWidgetPreview() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        ensureWidgetCategoryCombinationIsValid(i);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Provider lookupProviderLocked = lookupProviderLocked(new ProviderId(Binder.getCallingUid(), componentName));
                if (lookupProviderLocked == null) {
                    throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                int[] iArr = Provider.WIDGET_CATEGORY_FLAGS;
                boolean z = false;
                for (int i2 = 0; i2 < 3; i2++) {
                    int i3 = iArr[i2];
                    if ((i & i3) != 0) {
                        z |= lookupProviderLocked.generatedPreviews.removeReturnOld(i3) != null;
                    }
                }
                if (z) {
                    lookupProviderLocked.updateGeneratedPreviewCategoriesLocked();
                }
                if (z) {
                    scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeWidgetsForPackageLocked(int i, int i2, String str) {
        int size = this.mProviders.size();
        for (int i3 = 0; i3 < size; i3++) {
            Provider provider = (Provider) this.mProviders.get(i3);
            if (provider == null) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i3, size, "removeWidgetsForPackageLocked provider is null. i:", " size:", "pkg: "), str, "AppWidgetServiceImpl");
            } else if (str.equals(provider.id.componentName.getPackageName()) && provider.getUserId() == i && provider.widgets.size() > 0) {
                deleteWidgetsLocked(provider, i2);
            }
        }
    }

    public final boolean requestPinAppWidget(String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) {
        ProviderId providerId;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(userId, "requestPinAppWidget() ", "AppWidgetServiceImpl");
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(userId, true);
                String packageName = componentName.getPackageName();
                if (((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).isSameApp(callingUid, userId, 0L, packageName)) {
                    providerId = new ProviderId(callingUid, componentName);
                } else {
                    if (this.mContext.checkPermission("android.permission.CLEAR_APP_USER_DATA", Binder.getCallingPid(), callingUid) != 0) {
                        return false;
                    }
                    providerId = new ProviderId(this.mPackageManagerInternal.getPackageUid(packageName, 0L, userId), componentName);
                }
                Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked != null && !lookupProviderLocked.zombie) {
                    AppWidgetProviderInfo infoLocked = lookupProviderLocked.getInfoLocked(this.mContext);
                    if ((infoLocked.widgetCategory & 1) == 0) {
                        return false;
                    }
                    return ((ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class)).requestPinAppWidget(str, infoLocked, bundle, intentSender, userId);
                }
                return false;
            } finally {
            }
        }
    }

    public final void resetPreviewRecord(int i) {
        List<Provider> list;
        AppWidgetProviderInfo appWidgetProviderInfo;
        if (this.mGeneratedTemplatePreviewsApiCounter == null || (list = this.mProviders) == null) {
            return;
        }
        for (Provider provider : list) {
            if (provider.templatePreviews.size() != 0 && (appWidgetProviderInfo = provider.info) != null && (appWidgetProviderInfo.hidden_semPreviewRecordResetStates & i) != 0) {
                Log.d("AppWidgetServiceImpl", "Reset " + appWidgetProviderInfo.provider.getClassName() + " preview record, " + i);
                ApiCounter.ApiCallRecord orCreateRecord = this.mGeneratedTemplatePreviewsApiCounter.getOrCreateRecord(provider.id);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                orCreateRecord.apiCallCount = 0;
                orCreateRecord.lastResetTimeMs = elapsedRealtime;
            }
        }
    }

    public final void resolveHostUidLocked(int i, String str) {
        int size = this.mHosts.size();
        for (int i2 = 0; i2 < size; i2++) {
            Host host = (Host) this.mHosts.get(i2);
            HostId hostId = host.id;
            if (hostId.uid == -1 && str.equals(hostId.packageName)) {
                if (DEBUG) {
                    Slog.i("AppWidgetServiceImpl", "host " + host.id + " resolved to uid " + i);
                }
                HostId hostId2 = host.id;
                host.id = new HostId(i, hostId2.hostId, hostId2.packageName);
                return;
            }
        }
    }

    public final void restoreWidgetState(String str, byte[] bArr, int i) {
        Provider provider;
        BackupRestoreController backupRestoreController = this.mBackupRestoreController;
        backupRestoreController.getClass();
        Slog.i("BackupRestoreController", "Restoring widget state for user:" + i + " package: " + str);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            try {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
                newFastPullParser.setInput(byteArrayInputStream, StandardCharsets.UTF_8.name());
                synchronized (AppWidgetServiceImpl.this.mLock) {
                    while (true) {
                        int next = newFastPullParser.next();
                        if (next == 2) {
                            String name = newFastPullParser.getName();
                            if ("ws".equals(name)) {
                                int attributeInt = newFastPullParser.getAttributeInt((String) null, "version");
                                if (attributeInt <= 2) {
                                    if (!str.equals(newFastPullParser.getAttributeValue((String) null, "pkg"))) {
                                        Slog.w("BackupRestoreController", "Package mismatch in ws");
                                        break;
                                    }
                                } else {
                                    Slog.w("BackupRestoreController", "Unable to process state version " + attributeInt);
                                    break;
                                }
                            } else if (KnoxAnalyticsDataConverter.PAYLOAD.equals(name)) {
                                ComponentName componentName = new ComponentName(newFastPullParser.getAttributeValue((String) null, "pkg"), newFastPullParser.getAttributeValue((String) null, "cl"));
                                AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                                int size = appWidgetServiceImpl.mProviders.size();
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= size) {
                                        provider = null;
                                        break;
                                    }
                                    provider = (Provider) appWidgetServiceImpl.mProviders.get(i2);
                                    if (provider != null) {
                                        if (provider.getUserId() == i && provider.id.componentName.equals(componentName)) {
                                            break;
                                        }
                                    } else {
                                        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i2, size, "findProviderLocked provider is null. i:", " size:", "BackupRestoreController");
                                    }
                                    i2++;
                                }
                                if (provider == null) {
                                    AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
                                    appWidgetProviderInfo.provider = componentName;
                                    provider = new Provider();
                                    provider.id = new ProviderId(-1, componentName);
                                    provider.setPartialInfoLocked(appWidgetProviderInfo);
                                    provider.zombie = true;
                                    AppWidgetServiceImpl.this.mProviders.add(provider);
                                    Log.i("BackupRestoreController", "restoreWidgetState Provider is added " + provider + " at " + AppWidgetServiceImpl.this.mProviders.size());
                                }
                                Slog.i("BackupRestoreController", "   provider " + provider.id);
                                arrayList.add(provider);
                            } else if ("h".equals(name)) {
                                String attributeValue = newFastPullParser.getAttributeValue((String) null, "pkg");
                                Host lookupOrAddHostLocked = AppWidgetServiceImpl.this.lookupOrAddHostLocked(new HostId(AppWidgetServiceImpl.this.getUidForPackage(i, attributeValue), newFastPullParser.getAttributeIntHex((String) null, "id"), attributeValue));
                                arrayList2.add(lookupOrAddHostLocked);
                                Slog.i("BackupRestoreController", "   host[" + arrayList2.size() + "]: {" + lookupOrAddHostLocked.id + "}");
                            } else if ("g".equals(name)) {
                                int attributeIntHex = newFastPullParser.getAttributeIntHex((String) null, "id");
                                Host host = (Host) arrayList2.get(newFastPullParser.getAttributeIntHex((String) null, "h"));
                                int attributeIntHex2 = newFastPullParser.getAttributeIntHex((String) null, KnoxAnalyticsDataConverter.PAYLOAD, -1);
                                Provider provider2 = attributeIntHex2 != -1 ? (Provider) arrayList.get(attributeIntHex2) : null;
                                backupRestoreController.pruneWidgetStateLocked(i, host.id.packageName);
                                if (provider2 != null) {
                                    backupRestoreController.pruneWidgetStateLocked(i, provider2.id.componentName.getPackageName());
                                }
                                Widget findRestoredWidgetLocked = backupRestoreController.findRestoredWidgetLocked(attributeIntHex, host, provider2);
                                if (findRestoredWidgetLocked == null) {
                                    findRestoredWidgetLocked = new Widget();
                                    AppWidgetServiceImpl appWidgetServiceImpl2 = AppWidgetServiceImpl.this;
                                    int i3 = (appWidgetServiceImpl2.mNextAppWidgetIds.indexOfKey(i) < 0 ? 1 : appWidgetServiceImpl2.mNextAppWidgetIds.get(i)) + 1;
                                    appWidgetServiceImpl2.mNextAppWidgetIds.put(i, i3);
                                    findRestoredWidgetLocked.appWidgetId = i3;
                                    findRestoredWidgetLocked.restoredId = attributeIntHex;
                                    findRestoredWidgetLocked.options = parseWidgetIdOptions(newFastPullParser);
                                    findRestoredWidgetLocked.host = host;
                                    host.widgets.add(findRestoredWidgetLocked);
                                    findRestoredWidgetLocked.provider = provider2;
                                    if (provider2 != null) {
                                        provider2.widgets.add(findRestoredWidgetLocked);
                                    }
                                    Slog.i("BackupRestoreController", "New restored id " + attributeIntHex + " now " + findRestoredWidgetLocked);
                                    AppWidgetServiceImpl.this.addWidgetLocked(findRestoredWidgetLocked);
                                }
                                Provider provider3 = findRestoredWidgetLocked.provider;
                                if (provider3 == null || provider3.info == null) {
                                    Slog.w("BackupRestoreController", "Missing provider for restored widget " + findRestoredWidgetLocked);
                                } else {
                                    backupRestoreController.stashProviderRestoreUpdateLocked(provider3, attributeIntHex, findRestoredWidgetLocked.appWidgetId);
                                }
                                backupRestoreController.stashHostRestoreUpdateLocked(findRestoredWidgetLocked.host, attributeIntHex, findRestoredWidgetLocked.appWidgetId);
                                Slog.i("BackupRestoreController", "   instance: " + attributeIntHex + " -> " + findRestoredWidgetLocked.appWidgetId + " :: p=" + findRestoredWidgetLocked.provider);
                            }
                        }
                        if (next == 1) {
                            break;
                        }
                    }
                }
            } catch (IOException | XmlPullParserException unused) {
                Slog.w("BackupRestoreController", "Unable to restore widget state for " + str);
            }
        } finally {
            AppWidgetServiceImpl.this.saveGroupStateAsync(i);
        }
    }

    public final void saveGroupStateAsync(int i) {
        if (!android.appwidget.flags.Flags.removeAppWidgetServiceIoFromCriticalPath()) {
            this.mSaveStateHandler.post(new SaveStateRunnable(i));
        } else {
            this.mSaveStateHandler.removeMessages(i);
            this.mSaveStateHandler.sendEmptyMessage(i);
        }
    }

    public final void scheduleNotifyAppWidgetViewDataChanged(Widget widget, int i) {
        Provider provider;
        if (i == 0 || i == 1) {
            return;
        }
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        widget.updateSequenceNos.put(i, incrementAndGet);
        Host host = widget.host;
        if (host == null || host.zombie || host.callbacks == null || (provider = widget.provider) == null || provider.zombie) {
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

    public final void scheduleNotifyGroupHostsForProvidersChangedLocked(int i) {
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
        for (int size = this.mHosts.size() - 1; size >= 0; size--) {
            Host host = (Host) this.mHosts.get(size);
            int length = enabledGroupProfileIds.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    if (host.getUserId() != enabledGroupProfileIds[i2]) {
                        i2++;
                    } else if (!host.zombie && host.callbacks != null) {
                        SomeArgs obtain = SomeArgs.obtain();
                        obtain.arg1 = host;
                        obtain.arg2 = host.callbacks;
                        this.mCallbackHandler.obtainMessage(3, obtain).sendToTarget();
                    }
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

    public final void scheduleNotifyUpdateAppWidgetLocked(Widget widget, RemoteViews remoteViews) {
        Provider provider;
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        if (widget != null) {
            if (widget.trackingUpdate) {
                widget.trackingUpdate = false;
                Log.i("AppWidgetServiceImpl", "Widget update received " + widget.toString());
                Trace.asyncTraceEnd(64L, "appwidget update-intent " + widget.provider.id.toString(), widget.appWidgetId);
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
        if (remoteViews == null || !remoteViews.isLegacyListRemoteViews()) {
            this.mCallbackHandler.obtainMessage(1, obtain).sendToTarget();
        } else {
            this.mCallbackHandler.obtainMessage(6, obtain).sendToTarget();
        }
    }

    public final IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2) {
        IntentSender intentSender;
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "createAppWidgetConfigIntentSender() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
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
                    intentSender = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1409286144, null, new UserHandle(provider.getUserId())).getIntentSender();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return intentSender;
    }

    public final void semSetSkipPackageChanged(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mSkipPackageName = str;
    }

    public final void sendBroadcastAsUser(Intent intent, UserHandle userHandle, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, userHandle, null, z ? this.mInteractiveBroadcast : null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendDisabledIntentLocked(Provider provider) {
        Intent m = BatteryService$$ExternalSyntheticOutline0.m(268435456, "android.appwidget.action.APPWIDGET_DISABLED");
        m.setComponent(provider.id.componentName);
        sendBroadcastAsUser(m, UserHandle.getUserHandleForUid(provider.id.uid), false);
    }

    public final void sendEnableAndUpdateIntentLocked(Provider provider, int[] iArr) {
        AppWidgetProviderInfo appWidgetProviderInfo;
        if (this.mIsCombinedBroadcastEnabled && (appWidgetProviderInfo = provider.info) != null && appWidgetProviderInfo.isExtendedFromAppWidgetProvider) {
            Intent intent = new Intent("android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE");
            intent.putExtra("appWidgetIds", iArr);
            intent.setComponent(provider.id.componentName);
            sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(provider.id.uid), true);
            return;
        }
        Intent intent2 = new Intent("android.appwidget.action.APPWIDGET_ENABLED");
        intent2.setComponent(provider.id.componentName);
        sendBroadcastAsUser(intent2, UserHandle.getUserHandleForUid(provider.id.uid), true);
        sendUpdateIntentLocked(provider, iArr, true, false);
    }

    public final void sendOptionsChangedIntentLocked(Widget widget) {
        Intent m = BatteryService$$ExternalSyntheticOutline0.m(268435456, "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS");
        m.setComponent(widget.provider.id.componentName);
        m.putExtra(KnoxCustomManagerService.APPWIDGET_ID, widget.appWidgetId);
        m.putExtra("appWidgetOptions", widget.options);
        if (this.isProductDEV) {
            Log.d("AppWidgetServiceImpl", "sendOptionsChangedIntentLocked, widget = " + widget + ", options = " + widget.options);
        }
        sendBroadcastAsUser(m, UserHandle.getUserHandleForUid(widget.provider.id.uid), true);
    }

    public final void sendUpdateIntentLocked(Provider provider, int[] iArr, boolean z, boolean z2) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("appWidgetIds", iArr);
        intent.setComponent(provider.id.componentName);
        if (!z2) {
            intent.addFlags(268435456);
        }
        intent.addFlags(67108864);
        intent.putExtra("appWidgetIds", iArr);
        intent.setComponent(provider.id.componentName);
        sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(provider.id.uid), z);
    }

    public final void setAppWidgetHidden(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "setAppWidgetHidden() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, false);
                Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
                if (lookupHostLocked != null) {
                    this.mAppOpsManagerInternal.updateAppWidgetVisibility(lookupHostLocked.getWidgetUidsIfBound(), false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setBindAppWidgetPermission(String str, int i, boolean z) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "setBindAppWidgetPermission() " + UserHandle.getCallingUserId());
        }
        AppWidgetServiceImpl.this.mContext.enforceCallingPermission("android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS", "hasBindAppWidgetPermission packageName=" + str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(i, true);
                if (getUidForPackage(i, str) < 0) {
                    return;
                }
                Pair create = Pair.create(Integer.valueOf(i), str);
                if (z) {
                    this.mPackagesWithBindWidgetPermission.add(create);
                } else {
                    this.mPackagesWithBindWidgetPermission.remove(create);
                }
                saveGroupStateAsync(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setTemplateWidgetPreview(ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "setTemplateWidgetPreview() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        ensureTemplateWidgetPropertyCombinationIsValid(i, i2);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
                Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                ApiCounter.ApiCallRecord orCreateRecord = this.mGeneratedTemplatePreviewsApiCounter.getOrCreateRecord(providerId);
                if (orCreateRecord != null) {
                    Log.d("AppWidgetServiceImpl", componentName.getClassName() + " preview API Record : " + orCreateRecord.apiCallCount + " / " + orCreateRecord.lastResetTimeMs);
                }
                if (this.mGeneratedTemplatePreviewsApiCounter.tryApiCall(providerId)) {
                    lookupProviderLocked.setTemplatePreviewLocked(i, i2, remoteViewsArr);
                    scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
                    return true;
                }
                Log.d("AppWidgetServiceImpl", "Api Calls are limited / limit info : " + this.mGeneratedTemplatePreviewsApiCounter.mMaxCallsPerInterval + " / " + this.mGeneratedTemplatePreviewsApiCounter.mResetIntervalMs);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "setWidgetPreview() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        ensureWidgetCategoryCombinationIsValid(i);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
                Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                boolean tryApiCall = this.mGeneratedPreviewsApiCounter.tryApiCall(providerId);
                if (!tryApiCall) {
                    return false;
                }
                int[] iArr = Provider.WIDGET_CATEGORY_FLAGS;
                for (int i2 = 0; i2 < 3; i2++) {
                    int i3 = iArr[i2];
                    if ((i & i3) != 0) {
                        lookupProviderLocked.generatedPreviews.put(i3, remoteViews);
                    }
                }
                lookupProviderLocked.updateGeneratedPreviewCategoriesLocked();
                scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParceledListSlice startListening(IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "startListening() ", "AppWidgetServiceImpl");
        } else {
            Slog.i("AppWidgetServiceImpl", "startListening() ");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.isInstantAppLocked(callingUserId, str)) {
                    Slog.w("AppWidgetServiceImpl", "Instant package " + str + " cannot host app widgets");
                    return ParceledListSlice.emptyList();
                }
                ensureGroupStateLoadedLocked(callingUserId, true);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                HostId hostId = new HostId(Binder.getCallingUid(), i, str);
                Host lookupOrAddHostLocked = lookupOrAddHostLocked(hostId);
                lookupOrAddHostLocked.callbacks = iAppWidgetHost;
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                if (elapsedRealtime2 - elapsedRealtime > 3000) {
                    Log.i("AppWidgetServiceImpl", "startListening callbacks : " + iAppWidgetHost + " intervalEnsure:" + elapsedRealtime + " intervalHostLock:" + elapsedRealtime2);
                } else {
                    Log.i("AppWidgetServiceImpl", "startListening callbacks : " + iAppWidgetHost + " intervalEnsure:" + elapsedRealtime + " intervalHostLock:" + elapsedRealtime2 + " LhostId:" + hostId);
                }
                updateHostHistoryLocked("Start listening : " + lookupOrAddHostLocked.id);
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
                Slog.i("AppWidgetServiceImpl", "startListening() finish");
                return new ParceledListSlice(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopListening(String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "stopListening() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, false);
                Host lookupHostLocked = lookupHostLocked(new HostId(Binder.getCallingUid(), i, str));
                if (lookupHostLocked != null) {
                    lookupHostLocked.callbacks = null;
                    pruneHostLocked(lookupHostLocked);
                    this.mAppOpsManagerInternal.updateAppWidgetVisibility(lookupHostLocked.getWidgetUidsIfBound(), false);
                    Log.i("AppWidgetServiceImpl", "stopListening callbacks : null");
                    updateHostHistoryLocked("Stop listening : " + lookupHostLocked.id);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void systemRestoreFinished(int i) {
        BackupRestoreController backupRestoreController = this.mBackupRestoreController;
        backupRestoreController.getClass();
        Slog.i("BackupRestoreController", "systemRestoreFinished for " + i);
        synchronized (AppWidgetServiceImpl.this.mLock) {
            backupRestoreController.mHasSystemRestoreFinished = true;
            backupRestoreController.maybeSendWidgetRestoreBroadcastsLocked(i);
        }
    }

    public final void systemRestoreStarting(int i) {
        BackupRestoreController backupRestoreController = this.mBackupRestoreController;
        backupRestoreController.getClass();
        Slog.i("BackupRestoreController", "System restore starting for user: " + i);
        synchronized (AppWidgetServiceImpl.this.mLock) {
            backupRestoreController.mHasSystemRestoreFinished = false;
            if (!backupRestoreController.mPrunedAppsPerUser.contains(i)) {
                backupRestoreController.mPrunedAppsPerUser.set(i, new ArraySet());
            }
            ((Set) backupRestoreController.mPrunedAppsPerUser.get(i)).clear();
            backupRestoreController.mUpdatesByProvider.clear();
            backupRestoreController.mUpdatesByHost.clear();
        }
    }

    public final void tagProvidersAndHosts() {
        int size = this.mProviders.size();
        for (int i = 0; i < size; i++) {
            Provider provider = (Provider) this.mProviders.get(i);
            if (provider == null) {
                AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i, size, "findProviderByTag provider is null. i:", " size:", "AppWidgetServiceImpl");
            } else {
                provider.tag = i;
            }
        }
        int size2 = this.mHosts.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Host) this.mHosts.get(i2)).tag = i2;
        }
    }

    public final void unmaskWidgetsViewsLocked(Provider provider) {
        int size = provider.widgets.size();
        for (int i = 0; i < size; i++) {
            Widget widget = (Widget) provider.widgets.get(i);
            if (widget.maskedViews != null) {
                widget.maskedViews = null;
                widget.transactionError = null;
                scheduleNotifyUpdateAppWidgetLocked(widget, widget.views);
            }
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

    public final void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) {
        if (DEBUG) {
            Slog.i("AppWidgetServiceImpl", "updateAppWidgetIds() " + UserHandle.getCallingUserId());
        }
        updateAppWidgetIds(str, iArr, remoteViews, false);
    }

    public final void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews, boolean z) {
        int callingUserId = UserHandle.getCallingUserId();
        if (iArr == null || iArr.length == 0) {
            return;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                for (int i : iArr) {
                    Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                    if (lookupWidgetLocked != null) {
                        updateAppWidgetInstanceLocked(lookupWidgetLocked, remoteViews, z);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAppWidgetInstanceLocked(Widget widget, RemoteViews remoteViews, boolean z) {
        Provider provider;
        RemoteViews remoteViews2;
        int estimateMemoryUsage;
        RemoteViews remoteViews3;
        if (widget == null || (provider = widget.provider) == null || provider.zombie || widget.host.zombie) {
            return;
        }
        if (!z || (remoteViews3 = widget.views) == null) {
            widget.views = remoteViews;
        } else {
            remoteViews3.mergeRemoteViews(remoteViews);
        }
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000 && (remoteViews2 = widget.views) != null && (estimateMemoryUsage = remoteViews2.estimateMemoryUsage()) > this.mMaxWidgetBitmapMemory) {
            widget.views = null;
            throw new IllegalArgumentException(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mMaxWidgetBitmapMemory, BatteryService$$ExternalSyntheticOutline0.m(estimateMemoryUsage, "RemoteViews for widget update exceeds maximum bitmap memory usage (used: ", ", max: "), ")"));
        }
        RemoteViews remoteViews4 = widget.maskedViews;
        if (remoteViews4 == null) {
            remoteViews4 = widget.views;
        }
        scheduleNotifyUpdateAppWidgetLocked(widget, remoteViews4);
    }

    public final void updateAppWidgetOptions(String str, int i, Bundle bundle) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "updateAppWidgetOptions() ", "AppWidgetServiceImpl");
        }
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "updateAppWidgetOptions callingPackage :", str, ", appWidgetId : ", ", options = ");
        m.append(bundle);
        Log.i("AppWidgetServiceImpl", m.toString());
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                Widget lookupWidgetLocked = lookupWidgetLocked(i, Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    return;
                }
                lookupWidgetLocked.options.putAll(bundle);
                sendOptionsChangedIntentLocked(lookupWidgetLocked);
                saveGroupStateAsync(callingUserId);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "updateAppWidgetProvider() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
                Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    Slog.w("AppWidgetServiceImpl", "Provider doesn't exist " + providerId);
                } else {
                    ArrayList arrayList = lookupProviderLocked.widgets;
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        updateAppWidgetInstanceLocked((Widget) arrayList.get(i), remoteViews, false);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAppWidgetProviderInfo(ComponentName componentName, String str) {
        int callingUserId = UserHandle.getCallingUserId();
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(callingUserId, "updateAppWidgetProvider() ", "AppWidgetServiceImpl");
        }
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, true);
                ProviderId providerId = new ProviderId(Binder.getCallingUid(), componentName);
                Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    throw new IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                if (Objects.equals(lookupProviderLocked.infoTag, str)) {
                    return;
                }
                String str2 = str == null ? "android.appwidget.provider" : str;
                AppWidgetProviderInfo parseAppWidgetProviderInfo = parseAppWidgetProviderInfo(this.mContext, providerId, lookupProviderLocked.info.providerInfo, str2);
                if (parseAppWidgetProviderInfo == null) {
                    throw new IllegalArgumentException("Unable to parse " + str2 + " meta-data to a valid AppWidget provider");
                }
                lookupProviderLocked.info = parseAppWidgetProviderInfo;
                lookupProviderLocked.mInfoParsed = true;
                lookupProviderLocked.infoTag = str;
                int size = lookupProviderLocked.widgets.size();
                for (int i = 0; i < size; i++) {
                    Widget widget = (Widget) lookupProviderLocked.widgets.get(i);
                    scheduleNotifyProviderChangedLocked(widget);
                    updateAppWidgetInstanceLocked(widget, widget.views, false);
                }
                saveGroupStateAsync(callingUserId);
                scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
            } catch (Throwable th) {
                throw th;
            }
        }
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

    public final boolean updateProvidersForPackageLocked(String str, Set set, int i, boolean z) {
        String str2;
        HashSet hashSet = new HashSet();
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setPackage(str);
        List queryIntentReceivers = queryIntentReceivers(i, intent);
        int size = queryIntentReceivers == null ? 0 : queryIntentReceivers.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentReceivers.get(i2);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if ((activityInfo.applicationInfo.flags & 262144) == 0 && str.equals(activityInfo.packageName)) {
                ProviderId providerId = new ProviderId(activityInfo.applicationInfo.uid, new ComponentName(activityInfo.packageName, activityInfo.name));
                Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked != null) {
                    AppWidgetProviderInfo createPartialProviderInfo = createPartialProviderInfo(providerId, resolveInfo, lookupProviderLocked);
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
                            z2 = true;
                        }
                    }
                } else if (addProviderLocked(resolveInfo)) {
                    hashSet.add(providerId);
                }
                z2 = true;
            }
        }
        int size3 = this.mProviders.size();
        for (int i4 = size3 - 1; i4 >= 0; i4--) {
            Provider provider = (Provider) this.mProviders.get(i4);
            if (provider == null) {
                if (i4 > 0) {
                    int i5 = i4 - 1;
                    if (this.mProviders.get(i5) != null) {
                        str2 = ((Provider) this.mProviders.get(i5)).id.componentName.toString();
                        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(size3, i4, "updateProvidersForPackageLocked provider is null size:", " index:", " prevComp:");
                        m.append(str2);
                        Log.e("AppWidgetServiceImpl", m.toString());
                        StorageManagerService$$ExternalSyntheticOutline0.m("caller:", Debug.getCallers(5), "AppWidgetServiceImpl");
                    }
                }
                str2 = null;
                StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(size3, i4, "updateProvidersForPackageLocked provider is null size:", " index:", " prevComp:");
                m2.append(str2);
                Log.e("AppWidgetServiceImpl", m2.toString());
                StorageManagerService$$ExternalSyntheticOutline0.m("caller:", Debug.getCallers(5), "AppWidgetServiceImpl");
            } else if (str.equals(provider.id.componentName.getPackageName()) && provider.getUserId() == i && !hashSet.contains(provider.id)) {
                if (set != null) {
                    ((HashSet) set).add(provider.id);
                }
                deleteWidgetsLocked(provider, -1);
                this.mProviders.remove(provider);
                ((ArrayMap) this.mGeneratedPreviewsApiCounter.mCallCount).remove(provider.id);
                ((ArrayMap) this.mGeneratedTemplatePreviewsApiCounter.mCallCount).remove(provider.id);
                cancelBroadcastsLocked(provider);
                z2 = true;
            }
        }
        return z2;
    }

    public final boolean writeProfileStateToStreamLocked(OutputStream outputStream, int i) {
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "gs");
            resolveSerializer.attributeInt((String) null, "version", 1);
            int size = this.mProviders.size();
            for (int i2 = 0; i2 < size; i2++) {
                Provider provider = (Provider) this.mProviders.get(i2);
                if (provider == null) {
                    Log.i("AppWidgetServiceImpl", "writeProfileStateToFileLocked provider is null. i:" + i2 + " size:" + size);
                } else if (provider.getUserId() == i) {
                    serializeProvider(resolveSerializer, provider, true);
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
            DeviceIdleController$$ExternalSyntheticOutline0.m("Failed to write state: ", e, "AppWidgetServiceImpl");
            return false;
        }
    }
}
