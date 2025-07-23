package android.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.app.LoadedApk;
import android.app.RemoteServiceException;
import android.app.admin.DevicePolicyManager;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.backup.BackupAgent;
import android.app.backup.FullBackup;
import android.app.compat.CompatChanges;
import android.app.sdksandbox.sandboxactivity.ActivityContextInfo;
import android.app.sdksandbox.sandboxactivity.SdkSandboxActivityAuthority;
import android.app.servertransaction.ActivityRelaunchItem;
import android.app.servertransaction.ActivityResultItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ClientTransactionListenerController;
import android.app.servertransaction.CoreStatesChangeItem;
import android.app.servertransaction.DestroyActivityItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.ResumeActivityItem;
import android.app.servertransaction.TransactionExecutor;
import android.app.servertransaction.TransactionExecutorHelper;
import android.app.slice.Slice;
import android.bluetooth.BluetoothFrameworkInitializer;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageManager;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ProviderInfoList;
import android.content.pm.ServiceInfo;
import android.content.pm.SystemFeaturesCache;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.ResourceTimer;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.content.res.loader.ResourcesLoader;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDebug;
import android.ddm.DdmHandleAppName;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.ExifInterface;
import android.media.MediaFrameworkInitializer;
import android.media.MediaFrameworkPlatformInitializer;
import android.media.MediaServiceManager;
import android.net.ConnectivityManager;
import android.net.Proxy;
import android.net.ProxyInfoWrapper;
import android.net.Uri;
import android.nfc.NfcFrameworkInitializer;
import android.nfc.NfcServiceManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.BluetoothServiceManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.DdmSyncStageUpdater;
import android.os.DdmSyncState;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.ProfilingFrameworkInitializer;
import android.os.ProfilingServiceManager;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SharedMemory;
import android.os.StatsFrameworkInitializer;
import android.os.StatsServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.TelephonyServiceManager;
import android.os.Trace;
import android.os.UserHandle;
import android.os.instrumentation.ExecutableMethodFileOffsets;
import android.os.instrumentation.IOffsetCallback;
import android.os.instrumentation.MethodDescriptor;
import android.os.instrumentation.MethodDescriptorParser;
import android.permission.IPermissionManager;
import android.provider.DeviceConfigInitializer;
import android.provider.DeviceConfigServiceManager;
import android.provider.Settings;
import android.renderscript.RenderScriptCacheDir;
import android.se.omapi.SeFrameworkInitializer;
import android.se.omapi.SeServiceManager;
import android.security.NetworkSecurityPolicy;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.telephony.TelephonyFrameworkInitializer;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.Display;
import android.view.IGraphicsStats;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillId;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.IContentCaptureOptionsCallback;
import android.view.translation.TranslationSpec;
import android.view.translation.UiTranslationSpec;
import android.webkit.WebView;
import android.window.ActivityWindowInfo;
import android.window.ConfigurationHelper;
import android.window.ITaskFragmentOrganizer;
import android.window.SizeConfigurationBuckets;
import android.window.SplashScreen;
import android.window.SplashScreenView;
import android.window.TaskFragmentTransaction;
import android.window.WindowContextInfo;
import android.window.WindowProviderService;
import android.window.WindowTokenClientController;
import com.android.internal.R;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;
import com.android.internal.os.ApplicationSharedMemory;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.DebugStore;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.SafeZipPathValidatorCallback;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledRunnable;
import com.android.org.conscrypt.OpenSSLProvider;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.samsung.android.app.AbnormalUsage;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.lock.LsConstants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.samsung.ucm.keystore.KnoxUcmKeyStoreProvider;
import com.samsung.ucm.keystore.UcmKeyStoreHelper;
import dalvik.annotation.optimization.NeverCompile;
import dalvik.system.CloseGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import dalvik.system.ZipPathValidator;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Provider;
import java.security.Security;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import libcore.io.ForwardingOs;
import libcore.io.IoUtils;
import libcore.net.event.NetworkEventDispatcher;
import libcore.util.NativeAllocationRegistry;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class ActivityThread extends ClientTransactionHandler implements ActivityThreadInternal {
    private static final int ACTIVITY_THREAD_CHECKIN_VERSION = 4;
    private static final long BINDER_CALLBACK_THROTTLE = 10100;
    private static final long CONTENT_PROVIDER_RETAIN_TIME = 1000;
    private static final boolean DEBUG_APP_INFO = false;
    private static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_BROADCAST = false;
    public static final boolean DEBUG_CONFIGURATION = false;
    static final String DEBUG_LEVEL;
    static final boolean DEBUG_LEVEL_LOW;
    public static final boolean DEBUG_MEMORY_TRIM = false;
    static final boolean DEBUG_MESSAGES = false;
    public static final boolean DEBUG_ORDER = false;
    private static final boolean DEBUG_PROVIDER = false;
    private static final boolean DEBUG_RESULTS = false;
    private static final boolean DEBUG_SERVICE = false;
    private static final String DEFAULT_FULL_BACKUP_AGENT = "android.app.backup.FullBackupAgent";
    private static final String HEAP_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s";
    private static final String HEAP_FULL_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s";
    public static final long INVALID_PROC_STATE_SEQ = -1;
    private static final long MIN_TIME_BETWEEN_GCS = 5000;
    private static final String ONE_ALT_COUNT_COLUMN = "%21s %8s %21s %8d";
    private static final String ONE_COUNT_COLUMN = "%21s %8d";
    public static final String PROC_START_SEQ_IDENT = "seq=";
    private static final int REQUEST_DIRECT_ACTIONS_RETRY_MAX_COUNT = 7;
    private static final long REQUEST_DIRECT_ACTIONS_RETRY_TIME_MS = 200;
    public static final int SERVICE_DONE_EXECUTING_ANON = 0;
    public static final int SERVICE_DONE_EXECUTING_REBIND = 3;
    public static final int SERVICE_DONE_EXECUTING_START = 1;
    public static final int SERVICE_DONE_EXECUTING_STOP = 2;
    public static final int SERVICE_DONE_EXECUTING_UNBIND = 4;
    private static final int SQLITE_MEM_RELEASED_EVENT_LOG_TAG = 75003;
    public static final String TAG = "ActivityThread";
    private static final String THREE_COUNT_COLUMNS = "%21s %8d %21s %8d %21s %8d";
    private static final double THRESHOLD_FOR_HEAPDUMP = 0.96d;
    private static final String TWO_COUNT_COLUMNS = "%21s %8d %21s %8d";
    private static final String TWO_COUNT_COLUMN_HEADER = "%21s %8s %21s %8s";
    private static final int VM_PROCESS_STATE_JANK_IMPERCEPTIBLE = 1;
    private static final int VM_PROCESS_STATE_JANK_PERCEPTIBLE = 0;
    static final boolean localLOGV = false;
    private static boolean mIsAnomalyDetected = false;
    private static volatile ActivityThread sCurrentActivityThread = null;
    private static final ThreadLocal<Intent> sCurrentBroadcastIntent;
    private static boolean sDisableAID = false;
    private static boolean sFixedAppContextDisplay;
    static volatile Handler sMainThreadHandler;
    static volatile IPackageManager sPackageManager;
    private static volatile IPermissionManager sPermissionManager;
    private AbnormalUsage mAbnormalUsage;
    final ArrayMap<IBinder, ActivityClientRecord> mActivities;
    final Map<IBinder, DestroyActivityItem> mActivitiesToBeDestroyed;
    final ArrayList<Application> mAllApplications;
    private final SparseArray<ArrayMap<String, BackupAgent>> mBackupAgentsByUser;
    AppBindData mBoundApplication;
    private CompatibilityInfo mCompatibilityInfo;
    Configuration mConfiguration;
    private final ConfigurationChangedListenerController mConfigurationChangedListenerController;
    private ConfigurationController mConfigurationController;
    private IContentCaptureOptionsCallback.Stub mContentCaptureOptionsCallback;
    Bundle mCoreSettings;
    private final Object mCoreSettingsLock;
    int mCurDefaultDisplayDpi;
    boolean mDensityCompatMode;
    private ArrayList<WeakReference<Context>> mDisplaySystemUiContexts;
    final Executor mExecutor;
    final GcIdler mGcIdler;
    boolean mGcIdlerScheduled;
    final ArrayMap<ProviderKey, ProviderKey> mGetProviderKeys;
    private volatile IGraphicsStats mGraphicsStatsService;
    final H mH;
    private IdsController mIdsController;
    Application mInitialApplication;
    Instrumentation mInstrumentation;
    String mInstrumentationAppDir;
    String mInstrumentationLibDir;
    String mInstrumentationPackageName;
    String[] mInstrumentationSplitAppDirs;
    String mInstrumentedAppDir;
    String mInstrumentedLibDir;
    String[] mInstrumentedSplitAppDirs;
    boolean mInstrumentingWithoutRestart;
    final ArrayList<WeakReference<AssistStructure>> mLastAssistStructures;
    private int mLastProcessState;
    int mLastReportedDeviceId;
    private int mLastSessionId;
    final ArrayMap<IBinder, ProviderClientRecord> mLocalProviders;
    final ArrayMap<ComponentName, ProviderClientRecord> mLocalProvidersByName;
    private final List<MultiWindowCoreState.MultiWindowCoreStateListener> mMultiWindowCoreStateListeners;
    final ArrayList<ActivityClientRecord> mNewActivities;
    private final AtomicInteger mNumLaunchingActivities;
    int mNumVisibleActivities;
    final ArrayMap<Activity, ArrayList<OnActivityPausedListener>> mOnPauseListeners;
    final ArrayMap<String, WeakReference<LoadedApk>> mPackages;
    private final ArrayMap<String, ApplicationInfo> mPendingAppInfoUpdates;
    Configuration mPendingConfiguration;
    private final ArrayMap<IBinder, Configuration> mPendingOverrideConfigs;
    Profiler mProfiler;
    final ArrayMap<ProviderKey, ProviderClientRecord> mProviderMap;
    final ArrayMap<IBinder, ProviderRefCount> mProviderRefCountMap;
    final PurgeIdler mPurgeIdler;
    boolean mPurgeIdlerScheduled;
    final ArrayList<ActivityClientRecord> mRelaunchingActivities;
    private Map<SafeCancellationTransport, CancellationSignal> mRemoteCancellations;
    final ArrayMap<String, WeakReference<LoadedApk>> mResourcePackages;
    private final ResourcesManager mResourcesManager;
    final ArrayMap<IBinder, Service> mServices;
    final ArrayMap<IBinder, CreateServiceData> mServicesData;
    boolean mSomeActivitiesChanged;
    private SplashScreen.SplashScreenManagerGlobal mSplashScreenGlobal;
    private long mStartSeq;
    private ContextImpl mSystemContext;
    boolean mSystemThread;
    private final TransactionExecutor mTransactionExecutor;
    private boolean mUpdateHttpProxyOnBind;
    Handler trackingHandler;
    final HandlerThread trackingThread;
    public int webviewPreloadState;
    public boolean webviewPreloaded;
    private static final boolean DEBUG_STORE_ENABLED = com.android.internal.os.Flags.debugStoreEnabled();
    private static final long LONG_MESSAGE_THRESHOLD_MS = Build.HW_TIMEOUT_MULTIPLIER * 2000;
    private final DdmSyncStageUpdater mDdmSyncStageUpdater = new DdmSyncStageUpdater();
    private final Object mNetworkPolicyLock = new Object();
    private long mBinderCallbackLast = -1;
    private long mNetworkBlockSeq = -1;
    final ApplicationThread mAppThread = new ApplicationThread();
    final Looper mLooper = Looper.myLooper();

    static /* synthetic */ ProviderKey lambda$getGetProviderKey$5(ProviderKey providerKey) {
        return providerKey;
    }

    private native void nInitZygoteChildHeapProfiling();

    private native void nPurgePendingResources();

    /* JADX INFO: Access modifiers changed from: private */
    public void relaunchActivityIfWebViewAttached(IBinder iBinder) {
    }

    static {
        String str = SystemProperties.get("ro.boot.debug_level", LsConstants.TAG_UNKNOWN);
        DEBUG_LEVEL = str;
        DEBUG_LEVEL_LOW = "0x4f4c".equalsIgnoreCase(str);
        sCurrentBroadcastIntent = new ThreadLocal<>();
        sFixedAppContextDisplay = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ProviderKey {
        final String authority;
        ContentProviderHolder mHolder;
        final Object mLock = new Object();
        final int userId;

        public ProviderKey(String str, int i) {
            this.authority = str;
            this.userId = i;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ProviderKey) {
                ProviderKey providerKey = (ProviderKey) obj;
                if (Objects.equals(this.authority, providerKey.authority) && this.userId == providerKey.userId) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            String str = this.authority;
            return this.userId ^ (str != null ? str.hashCode() : 0);
        }
    }

    public static final class ActivityClientRecord {
        Activity activity;
        ViewRootImpl.ActivityConfigCallback activityConfigCallback;
        ActivityInfo activityInfo;
        public IBinder assistToken;
        CompatibilityInfo compatInfo;
        Configuration createdConfig;
        String embeddedID;
        boolean hideForNow;
        int ident;
        public IBinder initialCallerInfoAccessToken;
        Intent intent;
        public final boolean isForward;
        boolean isTopResumedActivity;
        Activity.NonConfigurationInstances lastNonConfigurationInstances;
        boolean lastReportedTopResumedState;
        private final ActivityWindowInfo mActivityWindowInfo;
        boolean mIsUserLeaving;
        int mLastDisplayIdByRelaunch;
        private final ActivityWindowInfo mLastReportedActivityWindowInfo;
        int mLastReportedWindowingMode;
        boolean mLaunchedFromBubble;
        private int mLifecycleState;
        Window mPendingRemoveWindow;
        WindowManager mPendingRemoveWindowManager;
        boolean mPreserveWindow;
        ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
        private SizeConfigurationBuckets mSizeConfigurations;
        public IBinder mTaskFragmentToken;
        Configuration overrideConfig;
        public LoadedApk packageInfo;
        Activity parent;
        boolean paused;
        int pendingConfigChanges;
        List<ReferrerIntent> pendingIntents;
        List<ResultInfo> pendingResults;
        PersistableBundle persistentState;
        ProfilerInfo profilerInfo;
        String referrer;
        public IBinder shareableActivityToken;
        boolean startsNotResumed;
        Bundle state;
        boolean stopped;
        private final Configuration tmpConfig;
        public IBinder token;
        IVoiceInteractor voiceInteractor;
        Window window;

        public ActivityClientRecord() {
            this.mActivityWindowInfo = new ActivityWindowInfo();
            this.mLastReportedActivityWindowInfo = new ActivityWindowInfo();
            this.tmpConfig = new Configuration();
            this.mLastReportedWindowingMode = 0;
            this.mLastDisplayIdByRelaunch = -1;
            this.mLifecycleState = 0;
            this.isForward = false;
            init();
        }

        public ActivityClientRecord(IBinder iBinder, Intent intent, int i, ActivityInfo activityInfo, Configuration configuration, String str, IVoiceInteractor iVoiceInteractor, Bundle bundle, PersistableBundle persistableBundle, List<ResultInfo> list, List<ReferrerIntent> list2, ActivityOptions.SceneTransitionInfo sceneTransitionInfo, boolean z, ProfilerInfo profilerInfo, ClientTransactionHandler clientTransactionHandler, IBinder iBinder2, IBinder iBinder3, boolean z2, IBinder iBinder4, IBinder iBinder5, ActivityWindowInfo activityWindowInfo) {
            ActivityWindowInfo activityWindowInfo2 = new ActivityWindowInfo();
            this.mActivityWindowInfo = activityWindowInfo2;
            this.mLastReportedActivityWindowInfo = new ActivityWindowInfo();
            this.tmpConfig = new Configuration();
            this.mLastReportedWindowingMode = 0;
            this.mLastDisplayIdByRelaunch = -1;
            this.mLifecycleState = 0;
            this.token = iBinder;
            this.assistToken = iBinder2;
            this.shareableActivityToken = iBinder3;
            this.ident = i;
            this.intent = intent;
            this.referrer = str;
            this.voiceInteractor = iVoiceInteractor;
            this.activityInfo = activityInfo;
            this.state = bundle;
            this.persistentState = persistableBundle;
            this.pendingResults = list;
            this.pendingIntents = list2;
            this.isForward = z;
            this.profilerInfo = profilerInfo;
            this.overrideConfig = configuration;
            this.packageInfo = clientTransactionHandler.getPackageInfoNoCheck(activityInfo.applicationInfo);
            this.initialCallerInfoAccessToken = iBinder5;
            this.mSceneTransitionInfo = sceneTransitionInfo;
            this.mLaunchedFromBubble = z2;
            this.mTaskFragmentToken = iBinder4;
            activityWindowInfo2.set(activityWindowInfo);
            init();
        }

        private void init() {
            this.parent = null;
            this.embeddedID = null;
            this.paused = false;
            this.stopped = false;
            this.hideForNow = false;
            this.activityConfigCallback = new ViewRootImpl.ActivityConfigCallback() { // from class: android.app.ActivityThread.ActivityClientRecord.1
                @Override // android.view.ViewRootImpl.ActivityConfigCallback
                public void onConfigurationChanged(Configuration configuration, int i, ActivityWindowInfo activityWindowInfo) {
                    if (ActivityClientRecord.this.activity == null) {
                        throw new IllegalStateException("Received config update for non-existing activity");
                    }
                    if (activityWindowInfo == null) {
                        Log.w(ActivityThread.TAG, "Received empty ActivityWindowInfo update for r=" + ActivityClientRecord.this.activity);
                        activityWindowInfo = ActivityClientRecord.this.mActivityWindowInfo;
                    }
                    ActivityClientRecord.this.activity.mMainThread.handleActivityConfigurationChanged(ActivityClientRecord.this, configuration, i, activityWindowInfo, false);
                }
            };
        }

        public int getLifecycleState() {
            return this.mLifecycleState;
        }

        public void setState(int i) {
            this.mLifecycleState = i;
            if (i == 1) {
                this.paused = true;
                this.stopped = true;
                return;
            }
            if (i == 2) {
                this.paused = true;
                this.stopped = false;
                return;
            }
            if (i == 3) {
                this.paused = false;
                this.stopped = false;
            } else if (i == 4) {
                this.paused = true;
                this.stopped = false;
            } else {
                if (i != 5) {
                    return;
                }
                this.paused = true;
                this.stopped = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPreHoneycomb() {
            Activity activity = this.activity;
            return activity != null && activity.getApplicationInfo().targetSdkVersion < 11;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPreP() {
            Activity activity = this.activity;
            return activity != null && activity.getApplicationInfo().targetSdkVersion < 28;
        }

        public boolean isPersistable() {
            return this.activityInfo.persistableMode == 2;
        }

        public boolean isVisibleFromServer() {
            Activity activity = this.activity;
            return activity != null && activity.mVisibleFromServer;
        }

        public ActivityWindowInfo getActivityWindowInfo() {
            return this.mActivityWindowInfo;
        }

        public String toString() {
            Intent intent = this.intent;
            ComponentName component = intent != null ? intent.getComponent() : null;
            StringBuilder sb = new StringBuilder("ActivityRecord{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" token=");
            sb.append(this.token);
            sb.append(" ");
            sb.append(component == null ? "no component name" : component.toShortString());
            sb.append("}");
            return sb.toString();
        }

        public String getStateString() {
            StringBuilder sb = new StringBuilder("ActivityClientRecord{paused=");
            sb.append(this.paused);
            sb.append(", stopped=");
            sb.append(this.stopped);
            sb.append(", hideForNow=");
            sb.append(this.hideForNow);
            sb.append(", startsNotResumed=");
            sb.append(this.startsNotResumed);
            sb.append(", isForward=");
            sb.append(this.isForward);
            sb.append(", pendingConfigChanges=");
            sb.append(this.pendingConfigChanges);
            sb.append(", preserveWindow=");
            sb.append(this.mPreserveWindow);
            if (this.activity != null) {
                sb.append(", Activity{resumed=");
                sb.append(this.activity.mResumed);
                sb.append(", stopped=");
                sb.append(this.activity.mStopped);
                sb.append(", finished=");
                sb.append(this.activity.isFinishing());
                sb.append(", destroyed=");
                sb.append(this.activity.isDestroyed());
                sb.append(", startedActivity=");
                sb.append(this.activity.mStartedActivity);
                sb.append(", changingConfigurations=");
                sb.append(this.activity.mChangingConfigurations);
                sb.append("}");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    static final class ProviderClientRecord {
        final ContentProviderHolder mHolder;
        final ContentProvider mLocalProvider;
        final String[] mNames;
        final IContentProvider mProvider;

        ProviderClientRecord(String[] strArr, IContentProvider iContentProvider, ContentProvider contentProvider, ContentProviderHolder contentProviderHolder) {
            this.mNames = strArr;
            this.mProvider = iContentProvider;
            this.mLocalProvider = contentProvider;
            this.mHolder = contentProviderHolder;
        }
    }

    public static final class ReceiverData extends BroadcastReceiver.PendingResult {
        CompatibilityInfo compatInfo;
        ActivityInfo info;
        final Intent intent;
        final ActivityOptions mOptions;

        public ReceiverData(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, IBinder iBinder, int i2, int i3, String str2) {
            super(i, str, bundle, 0, z, z2, z3, iBinder, i2, intent.getFlags(), i3, str2);
            this.intent = intent;
            if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.supportWidgetIntentsOnConnectedDisplay()) {
                this.mOptions = ActivityOptions.fromBundle(bundle);
            } else {
                this.mOptions = null;
            }
        }

        public String toString() {
            return "ReceiverData{intent=" + this.intent + " packageName=" + this.info.packageName + " resultCode=" + getResultCode() + " resultData=" + getResultData() + " resultExtras=" + getResultExtras(false) + " sentFromUid=" + getSentFromUid() + " sentFromPackage=" + getSentFromPackage() + " mOptions=" + this.mOptions + "}";
        }
    }

    static final class CreateBackupAgentData {
        ApplicationInfo appInfo;
        int backupDestination;
        int backupMode;
        int userId;

        CreateBackupAgentData() {
        }

        public String toString() {
            return "CreateBackupAgentData{appInfo=" + this.appInfo + " backupAgent=" + this.appInfo.backupAgentName + " mode=" + this.backupMode + " userId=" + this.userId + "}";
        }
    }

    static final class CreateServiceData {
        CompatibilityInfo compatInfo;
        ServiceInfo info;
        Intent intent;
        IBinder token;

        CreateServiceData() {
        }

        public String toString() {
            return "CreateServiceData{token=" + this.token + " className=" + this.info.name + " packageName=" + this.info.packageName + " intent=" + this.intent + "}";
        }
    }

    static final class BindServiceData {
        long bindSeq;
        Intent intent;
        boolean rebind;
        IBinder token;

        BindServiceData() {
        }

        public String toString() {
            return "BindServiceData{token=" + this.token + " intent=" + this.intent + " bindSeq=" + this.bindSeq + "}";
        }
    }

    static final class ServiceArgsData {
        Intent args;
        int flags;
        int startId;
        boolean taskRemoved;
        IBinder token;

        ServiceArgsData() {
        }

        public String toString() {
            return "ServiceArgsData{token=" + this.token + " startId=" + this.startId + " args=" + this.args + "}";
        }
    }

    static final class AppBindData {
        ApplicationInfo appInfo;
        boolean appMonitoring;
        AutofillOptions autofillOptions;
        String buildSerial;
        CompatibilityInfo compatInfo;
        Configuration config;
        ContentCaptureOptions contentCaptureOptions;
        int debugMode;
        long[] disabledCompatChanges;
        boolean enableBinderTracking;
        LoadedApk info;
        ProfilerInfo initProfilerInfo;
        Bundle instrumentationArgs;
        ComponentName instrumentationName;
        IUiAutomationConnection instrumentationUiAutomationConnection;
        IInstrumentationWatcher instrumentationWatcher;
        boolean isSdkInSandbox;
        long[] mLoggableCompatChanges;
        SharedMemory mSerializedSystemFontMap;
        boolean persistent;
        String processName;
        List<ProviderInfo> providers;
        boolean restrictedBackupMode;
        String sdkSandboxClientAppPackage;
        String sdkSandboxClientAppVolumeUuid;
        long startRequestedElapsedTime;
        long startRequestedUptime;
        boolean trackAllocation;

        AppBindData() {
        }

        public String toString() {
            return "AppBindData{appInfo=" + this.appInfo + "}";
        }
    }

    static final class Profiler {
        boolean autoStopProfiler;
        boolean handlingProfiling;
        int mClockType;
        int mProfilerOutputVersion;
        ParcelFileDescriptor profileFd;
        String profileFile;
        boolean profiling;
        int samplingInterval;
        boolean streamingOutput;

        Profiler() {
        }

        public void setProfiler(ProfilerInfo profilerInfo) {
            ParcelFileDescriptor parcelFileDescriptor = profilerInfo.profileFd;
            if (this.profiling) {
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                        return;
                    } catch (IOException unused) {
                        return;
                    }
                }
                return;
            }
            ParcelFileDescriptor parcelFileDescriptor2 = this.profileFd;
            if (parcelFileDescriptor2 != null) {
                try {
                    parcelFileDescriptor2.close();
                } catch (IOException unused2) {
                }
            }
            this.profileFile = profilerInfo.profileFile;
            this.profileFd = parcelFileDescriptor;
            this.samplingInterval = profilerInfo.samplingInterval;
            this.autoStopProfiler = profilerInfo.autoStopProfiler;
            this.streamingOutput = profilerInfo.streamingOutput;
            this.mClockType = profilerInfo.clockType;
            this.mProfilerOutputVersion = profilerInfo.profilerOutputVersion;
        }

        public void startProfiling() {
            if (this.profileFd == null || this.profiling) {
                return;
            }
            try {
                int i = SystemProperties.getInt("debug.traceview-buffer-size-mb", 8);
                int flagsForOutputVersion = this.mClockType | ProfilerInfo.getFlagsForOutputVersion(this.mProfilerOutputVersion);
                String str = this.profileFile;
                FileDescriptor fileDescriptor = this.profileFd.getFileDescriptor();
                int i2 = i * 1048576;
                int i3 = this.samplingInterval;
                VMDebug.startMethodTracing(str, fileDescriptor, i2, flagsForOutputVersion, i3 != 0, i3, this.streamingOutput);
                this.profiling = true;
            } catch (RuntimeException e) {
                Slog.w(ActivityThread.TAG, "Profiling failed on path " + this.profileFile, e);
                try {
                    this.profileFd.close();
                    this.profileFd = null;
                } catch (IOException e2) {
                    Slog.w(ActivityThread.TAG, "Failure closing profile fd", e2);
                }
            }
        }

        public void stopProfiling() {
            if (this.profiling) {
                this.profiling = false;
                Debug.stopMethodTracing();
                ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException unused) {
                    }
                }
                this.profileFd = null;
                this.profileFile = null;
            }
        }
    }

    static final class DumpComponentInfo {
        String[] args;
        ParcelFileDescriptor fd;
        String prefix;
        IBinder token;

        DumpComponentInfo() {
        }
    }

    static final class ContextCleanupInfo {
        ContextImpl context;
        String what;
        String who;

        ContextCleanupInfo() {
        }
    }

    static final class DumpHeapData {
        public String dumpBitmaps;
        ParcelFileDescriptor fd;
        RemoteCallback finishCallback;
        public boolean mallocInfo;
        public boolean managed;
        String path;
        public boolean runGc;

        DumpHeapData() {
        }
    }

    static final class DumpResourcesData {
        public ParcelFileDescriptor fd;
        public RemoteCallback finishCallback;

        DumpResourcesData() {
        }
    }

    static final class UpdateCompatibilityData {
        CompatibilityInfo info;
        String pkg;

        UpdateCompatibilityData() {
        }
    }

    static final class RequestAssistContextExtras {
        IBinder activityToken;
        int flags;
        boolean fromCapture;
        IBinder requestToken;
        int requestType;
        int sessionId;

        RequestAssistContextExtras() {
        }
    }

    static final class ReceiverList {
        int index;
        List<ReceiverInfo> receivers;

        ReceiverList() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: Access modifiers changed from: private */
    public class ApplicationThread extends IApplicationThread.Stub {
        private static final String DB_CONNECTION_INFO_FORMAT = "  %8s %8s %14s %5d %5d %5d  %s";
        private static final String DB_CONNECTION_INFO_HEADER = "  %8s %8s %14s %5s %5s %5s  %s";
        private static final String DB_POOL_INFO_FORMAT = "  %13d %13d %13d  %s";
        private static final String DB_POOL_INFO_HEADER = "  %13s %13s %13s  %s";
        static final int START_SABINDER_TRACKING = 4;
        static final int STOP_SABINDER_TRACKING_AND_DUMP = 5;

        @Override // android.app.IApplicationThread
        public void relaunchActivityIfWebViewAttached(IBinder iBinder) {
        }

        private ApplicationThread() {
        }

        @Override // android.app.IApplicationThread
        public final void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, String str2) {
            long recordScheduleReceiver = ActivityThread.DEBUG_STORE_ENABLED ? DebugStore.recordScheduleReceiver() : -1L;
            ActivityThread.this.updateProcessState(i3, false);
            ReceiverData receiverData = new ReceiverData(intent, i, str, bundle, z, false, z2, ActivityThread.this.mAppThread.asBinder(), i2, i4, str2);
            receiverData.info = activityInfo;
            ActivityThread.this.sendMessage(113, receiverData);
            if (ActivityThread.DEBUG_STORE_ENABLED) {
                DebugStore.recordEventEnd(recordScheduleReceiver);
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleReceiverList(List<ReceiverInfo> list) throws RemoteException {
            for (int i = 0; i < list.size(); i++) {
                ReceiverInfo receiverInfo = list.get(i);
                if (receiverInfo.registered) {
                    scheduleRegisteredReceiver(receiverInfo.receiver, receiverInfo.intent, receiverInfo.resultCode, receiverInfo.data, receiverInfo.extras, receiverInfo.ordered, receiverInfo.sticky, receiverInfo.assumeDelivered, receiverInfo.sendingUser, receiverInfo.processState, receiverInfo.sendingUid, receiverInfo.sendingPackage);
                } else {
                    scheduleReceiver(receiverInfo.intent, receiverInfo.activityInfo, receiverInfo.compatInfo, receiverInfo.resultCode, receiverInfo.data, receiverInfo.extras, receiverInfo.sync, receiverInfo.assumeDelivered, receiverInfo.sendingUser, receiverInfo.processState, receiverInfo.sendingUid, receiverInfo.sendingPackage);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, int i, int i2, int i3) {
            CreateBackupAgentData createBackupAgentData = new CreateBackupAgentData();
            createBackupAgentData.appInfo = applicationInfo;
            createBackupAgentData.backupMode = i;
            createBackupAgentData.userId = i2;
            createBackupAgentData.backupDestination = i3;
            ActivityThread.this.sendMessage(128, createBackupAgentData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, int i) {
            CreateBackupAgentData createBackupAgentData = new CreateBackupAgentData();
            createBackupAgentData.appInfo = applicationInfo;
            createBackupAgentData.userId = i;
            ActivityThread.this.sendMessage(129, createBackupAgentData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) {
            ActivityThread.this.updateProcessState(i, false);
            CreateServiceData createServiceData = new CreateServiceData();
            createServiceData.token = iBinder;
            createServiceData.info = serviceInfo;
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "scheduleCreateService. token=" + iBinder);
            }
            ActivityThread.this.sendMessage(114, createServiceData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i, long j) {
            ActivityThread.this.updateProcessState(i, false);
            BindServiceData bindServiceData = new BindServiceData();
            bindServiceData.token = iBinder;
            bindServiceData.intent = intent;
            bindServiceData.rebind = z;
            bindServiceData.bindSeq = j;
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "scheduleBindService. token=" + iBinder + " bindSeq=" + j);
            }
            ActivityThread.this.sendMessage(121, bindServiceData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleUnbindService(IBinder iBinder, Intent intent) {
            BindServiceData bindServiceData = new BindServiceData();
            bindServiceData.token = iBinder;
            bindServiceData.intent = intent;
            bindServiceData.bindSeq = -1L;
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "scheduleUnbindService. token=" + iBinder);
            }
            ActivityThread.this.sendMessage(122, bindServiceData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) {
            List list = parceledListSlice.getList();
            for (int i = 0; i < list.size(); i++) {
                ServiceStartArgs serviceStartArgs = (ServiceStartArgs) list.get(i);
                ServiceArgsData serviceArgsData = new ServiceArgsData();
                serviceArgsData.token = iBinder;
                serviceArgsData.taskRemoved = serviceStartArgs.taskRemoved;
                serviceArgsData.startId = serviceStartArgs.startId;
                serviceArgsData.flags = serviceStartArgs.flags;
                serviceArgsData.args = serviceStartArgs.args;
                if (Trace.isTagEnabled(64L)) {
                    Trace.instant(64L, "scheduleServiceArgs. token=" + iBinder + " startId=" + serviceArgsData.startId);
                }
                ActivityThread.this.sendMessage(115, serviceArgsData);
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleStopService(IBinder iBinder) {
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "scheduleStopService. token=" + iBinder);
            }
            ActivityThread.this.sendMessage(116, iBinder);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleTimeoutService(IBinder iBinder, int i) {
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "scheduleTimeoutService. token=" + iBinder);
            }
            ActivityThread.this.sendMessage(167, iBinder, i);
        }

        @Override // android.app.IApplicationThread
        public final void schedulePing(RemoteCallback remoteCallback) {
            ActivityThread.this.sendMessage(168, remoteCallback);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleTimeoutServiceForType(IBinder iBinder, int i, int i2) {
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "scheduleTimeoutServiceForType. token=" + iBinder);
            }
            ActivityThread.this.sendMessage(172, iBinder, i, i2);
        }

        @Override // android.app.IApplicationThread
        public final void bindApplication(String str, ApplicationInfo applicationInfo, String str2, String str3, boolean z, ProviderInfoList providerInfoList, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str4, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] jArr, long[] jArr2, SharedMemory sharedMemory, FileDescriptor fileDescriptor, long j, long j2, boolean z6) {
            if (map != null) {
                ServiceManager.initServiceCache(map);
            }
            if (com.android.internal.os.Flags.applicationSharedMemoryEnabled()) {
                ApplicationSharedMemory fromFileDescriptor = ApplicationSharedMemory.fromFileDescriptor(fileDescriptor, false);
                if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.cacheSdkSystemFeatures()) {
                    SystemFeaturesCache.setInstance(new SystemFeaturesCache(fromFileDescriptor.readSystemFeaturesCache()));
                }
                fromFileDescriptor.closeFileDescriptor();
                ApplicationSharedMemory.setInstance(fromFileDescriptor);
            }
            Bundle bundle3 = bundle2.getBundle(MultiWindowCoreState.TAG);
            if (bundle3 != null) {
                CoreStatesChangeItem coreStatesChangeItem = new CoreStatesChangeItem(bundle3);
                ClientTransaction clientTransaction = new ClientTransaction(ActivityThread.this.mAppThread);
                clientTransaction.addTransactionItem(coreStatesChangeItem);
                try {
                    ActivityThread.this.mAppThread.scheduleTransaction(clientTransaction);
                } catch (RemoteException e) {
                    Slog.w(ActivityThread.TAG, e.getMessage());
                }
                bundle2.remove(MultiWindowCoreState.TAG);
            }
            ActivityThread.sFixedAppContextDisplay = z6;
            setCoreSettings(bundle2);
            AppBindData appBindData = new AppBindData();
            appBindData.processName = str;
            appBindData.appInfo = applicationInfo;
            appBindData.sdkSandboxClientAppVolumeUuid = str2;
            appBindData.sdkSandboxClientAppPackage = str3;
            appBindData.isSdkInSandbox = z;
            appBindData.providers = providerInfoList.getList();
            appBindData.instrumentationName = componentName;
            appBindData.instrumentationArgs = bundle;
            appBindData.instrumentationWatcher = iInstrumentationWatcher;
            appBindData.instrumentationUiAutomationConnection = iUiAutomationConnection;
            appBindData.debugMode = i;
            appBindData.enableBinderTracking = z2;
            appBindData.trackAllocation = z3;
            appBindData.restrictedBackupMode = z4;
            appBindData.persistent = z5;
            appBindData.config = configuration;
            appBindData.compatInfo = compatibilityInfo;
            appBindData.initProfilerInfo = profilerInfo;
            appBindData.buildSerial = str4;
            appBindData.autofillOptions = autofillOptions;
            appBindData.appMonitoring = ServiceManager.getService("knoxzt") != null;
            appBindData.contentCaptureOptions = contentCaptureOptions;
            appBindData.disabledCompatChanges = jArr;
            appBindData.mLoggableCompatChanges = jArr2;
            appBindData.mSerializedSystemFontMap = sharedMemory;
            appBindData.startRequestedElapsedTime = j;
            appBindData.startRequestedUptime = j2;
            updateCompatOverrideScale(compatibilityInfo);
            updateCompatOverrideDisplayRotation(compatibilityInfo);
            CompatibilityInfo.applyOverrideIfNeeded(configuration);
            ActivityThread.this.sendMessage(110, appBindData);
        }

        private void updateCompatOverrideScale(CompatibilityInfo compatibilityInfo) {
            if (compatibilityInfo.hasOverrideScaling()) {
                CompatibilityInfo.setOverrideInvertedScale(compatibilityInfo.applicationInvertedScale, compatibilityInfo.applicationDensityInvertedScale);
            } else {
                CompatibilityInfo.setOverrideInvertedScale(1.0f, 1.0f);
            }
        }

        private void updateCompatOverrideDisplayRotation(CompatibilityInfo compatibilityInfo) {
            if (compatibilityInfo.isOverrideDisplayRotationRequired()) {
                CompatibilityInfo.setOverrideDisplayRotation(compatibilityInfo.applicationDisplayRotation);
            } else {
                CompatibilityInfo.setOverrideDisplayRotation(-1);
            }
        }

        @Override // android.app.IApplicationThread
        public final void runIsolatedEntryPoint(String str, String[] strArr) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = strArr;
            ActivityThread.this.sendMessage(158, obtain);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleExit() {
            ActivityThread.this.sendMessage(111, null);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleSuicide() {
            ActivityThread.this.sendMessage(130, null);
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) {
            synchronized (ActivityThread.this.mResourcesManager) {
                ApplicationInfo applicationInfo2 = (ApplicationInfo) ActivityThread.this.mPendingAppInfoUpdates.put(applicationInfo.packageName, applicationInfo);
                if (applicationInfo2 != null && applicationInfo2.createTimestamp > applicationInfo.createTimestamp) {
                    Slog.w(ActivityThread.TAG, "Skipping application info changed for obsolete AI with TS " + applicationInfo.createTimestamp + " < already pending TS " + applicationInfo2.createTimestamp);
                    ActivityThread.this.mPendingAppInfoUpdates.put(applicationInfo.packageName, applicationInfo2);
                    return;
                }
                ActivityThread.this.mResourcesManager.appendPendingAppInfoUpdate(new String[]{applicationInfo.sourceDir}, applicationInfo);
                ActivityThread.this.mH.removeMessages(156, applicationInfo.packageName);
                ActivityThread.this.sendMessage(156, applicationInfo.packageName);
            }
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() {
            TimeZone.setDefault(null);
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() {
            InetAddress.clearDnsCache();
            NetworkEventDispatcher.getInstance().dispatchNetworkConfigurationChange();
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() {
            synchronized (ActivityThread.this) {
                Application application = ActivityThread.this.getApplication();
                if (application == null) {
                    ActivityThread.this.mUpdateHttpProxyOnBind = true;
                } else {
                    ActivityThread.updateHttpProxy(application);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void setHttpProxyInfo(ProxyInfoWrapper proxyInfoWrapper) {
            Proxy.setHttpProxySystemProperty(proxyInfoWrapper.getProxyInfo());
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() {
            ActivityThread.this.mH.removeMessages(120);
            ActivityThread.this.mH.sendMessage(ActivityThread.this.mH.obtainMessage(120));
        }

        @Override // android.app.IApplicationThread
        public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.args = strArr;
                    ActivityThread.this.sendMessage(123, dumpComponentInfo, 0, 0, true);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpService failed", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2) throws RemoteException {
            long recordScheduleRegisteredReceiver = ActivityThread.DEBUG_STORE_ENABLED ? DebugStore.recordScheduleRegisteredReceiver() : -1L;
            ActivityThread.this.updateProcessState(i3, false);
            if (iIntentReceiver instanceof LoadedApk.ReceiverDispatcher.InnerReceiver) {
                ((LoadedApk.ReceiverDispatcher.InnerReceiver) iIntentReceiver).performReceive(intent, i, str, bundle, z, z2, z3, i2, i4, str2);
            } else {
                if (!z3) {
                    Log.wtf(ActivityThread.TAG, "scheduleRegisteredReceiver() called for " + iIntentReceiver + " and " + intent + " without mechanism to finish delivery");
                }
                if (i4 != -1 || str2 != null) {
                    Log.wtf(ActivityThread.TAG, "scheduleRegisteredReceiver() called for " + iIntentReceiver + " and " + intent + " from " + str2 + " (UID: " + i4 + ") without mechanism to propagate the sender's identity");
                }
                iIntentReceiver.performReceive(intent, i, str, bundle, z, z2, i2);
            }
            if (ActivityThread.DEBUG_STORE_ENABLED) {
                DebugStore.recordEventEnd(recordScheduleRegisteredReceiver);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() {
            ActivityThread.this.sendMessage(124, null);
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) {
            ActivityThread.this.sendMessage(127, profilerInfo, z ? 1 : 0, i);
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean z, boolean z2, boolean z3, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) {
            DumpHeapData dumpHeapData = new DumpHeapData();
            dumpHeapData.managed = z;
            dumpHeapData.mallocInfo = z2;
            dumpHeapData.dumpBitmaps = str;
            dumpHeapData.runGc = z3;
            dumpHeapData.path = str2;
            try {
                try {
                    dumpHeapData.fd = parcelFileDescriptor.dup();
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    dumpHeapData.finishCallback = remoteCallback;
                    ActivityThread.this.sendMessage(135, dumpHeapData, 0, 0, true);
                } catch (IOException e) {
                    Slog.e(ActivityThread.TAG, "Failed to duplicate heap dump file descriptor", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(String str) {
            ActivityThread.this.sendMessage(155, str);
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(String str) {
            ActivityThread.this.sendMessage(162, str);
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int i) {
            try {
                Process.setProcessGroup(Process.myPid(), i);
            } catch (Exception e) {
                Slog.w(ActivityThread.TAG, "Failed setting process group to " + i, e);
            }
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int i, String[] strArr) {
            ActivityThread.this.sendMessage(133, strArr, i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(String str, int i, Bundle bundle) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = bundle;
            ActivityThread.this.sendMessage(134, obtain, i);
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) {
            DumpResourcesData dumpResourcesData = new DumpResourcesData();
            try {
                try {
                    dumpResourcesData.fd = parcelFileDescriptor.dup();
                    dumpResourcesData.finishCallback = remoteCallback;
                    ActivityThread.this.sendMessage(166, dumpResourcesData, 0, 0, false);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpResources failed", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.prefix = str;
                    dumpComponentInfo.args = strArr;
                    ActivityThread.this.sendMessage(136, dumpComponentInfo, 0, 0, true);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpActivity failed", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.args = strArr;
                    ActivityThread.this.sendMessage(141, dumpComponentInfo, 0, 0, true);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpProvider failed", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        @NeverCompile
        public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String[] strArr) {
            FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            try {
                dumpMemInfo(fastPrintWriter, memoryInfo, z, z2, z3, z4, z5, z6);
            } finally {
                fastPrintWriter.flush();
                IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @NeverCompile
        private void dumpMemInfoNativeAllocations(PrintWriter printWriter) {
            String str;
            printWriter.println(" ");
            printWriter.println(" Native Allocations");
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMN_HEADER, "", "Count", "", "Total(kB)");
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMN_HEADER, "", "------", "", "------");
            for (NativeAllocationRegistry.Metrics metrics : NativeAllocationRegistry.getMetrics()) {
                String className = metrics.getClassName();
                className.hashCode();
                if (className.equals("android.graphics.Bitmap")) {
                    str = "Bitmap";
                } else if (className.equals("android.hardware.HardwareBuffer")) {
                    str = "HardwareBuffer";
                } else {
                    str = "Other";
                }
                if (metrics.getMallocedCount() != 0 || metrics.getMallocedBytes() != 0) {
                    ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, str.concat(" (malloced):"), Long.valueOf(metrics.getMallocedCount()), "", Long.valueOf(metrics.getMallocedBytes() / 1024));
                }
                if (metrics.getNonmallocedCount() != 0 || metrics.getNonmallocedBytes() != 0) {
                    ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, str.concat(" (nonmalloced):"), Long.valueOf(metrics.getNonmallocedCount()), "", Long.valueOf(metrics.getNonmallocedBytes() / 1024));
                }
            }
        }

        @NeverCompile
        private void dumpMemInfo(PrintWriter printWriter, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            long nativeHeapSize = Debug.getNativeHeapSize() / 1024;
            long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeHeapFreeSize = Debug.getNativeHeapFreeSize() / 1024;
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long j = runtime.totalMemory() / 1024;
            long freeMemory = runtime.freeMemory() / 1024;
            long j2 = j - freeMemory;
            long[] countInstancesOfClasses = VMDebug.countInstancesOfClasses(new Class[]{ContextImpl.class, Activity.class, WebView.class, View.class, ViewRootImpl.class}, true);
            long j3 = countInstancesOfClasses[0];
            long j4 = countInstancesOfClasses[1];
            long j5 = countInstancesOfClasses[2];
            long j6 = countInstancesOfClasses[3];
            long j7 = countInstancesOfClasses[4];
            int globalAssetCount = AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = Debug.getBinderProxyObjectCount();
            int binderDeathObjectCount = Debug.getBinderDeathObjectCount();
            long globalAllocSize = Parcel.getGlobalAllocSize();
            long globalAllocCount = Parcel.getGlobalAllocCount();
            SQLiteDebug.PagerStats databaseInfo = SQLiteDebug.getDatabaseInfo();
            ActivityThread.dumpMemInfoTable(printWriter, memoryInfo, z, z2, z3, z4, Process.myPid(), ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown", nativeHeapSize, nativeHeapAllocatedSize, nativeHeapFreeSize, j, j2, freeMemory);
            if (z) {
                printWriter.print(j6);
                printWriter.print(',');
                printWriter.print(j7);
                printWriter.print(',');
                printWriter.print(j3);
                printWriter.print(',');
                printWriter.print(j4);
                printWriter.print(',');
                printWriter.print(globalAssetCount);
                printWriter.print(',');
                printWriter.print(globalAssetManagerCount);
                printWriter.print(',');
                printWriter.print(binderLocalObjectCount);
                printWriter.print(',');
                printWriter.print(binderProxyObjectCount);
                printWriter.print(',');
                printWriter.print(binderDeathObjectCount);
                printWriter.print(',');
                printWriter.print(databaseInfo.memoryUsed / 1024);
                printWriter.print(',');
                printWriter.print(databaseInfo.memoryUsed / 1024);
                printWriter.print(',');
                printWriter.print(databaseInfo.pageCacheOverflow / 1024);
                printWriter.print(',');
                printWriter.print(databaseInfo.largestMemAlloc / 1024);
                for (int i = 0; i < databaseInfo.dbStats.size(); i++) {
                    SQLiteDebug.DbStats dbStats = databaseInfo.dbStats.get(i);
                    printWriter.print(',');
                    printWriter.print(dbStats.dbName);
                    printWriter.print(',');
                    printWriter.print(dbStats.pageSize);
                    printWriter.print(',');
                    printWriter.print(dbStats.dbSize);
                    printWriter.print(',');
                    printWriter.print(dbStats.lookaside);
                    printWriter.print(',');
                    printWriter.print(dbStats.cacheHits);
                    printWriter.print(',');
                    printWriter.print(dbStats.cacheMisses);
                    printWriter.print(',');
                    printWriter.print(dbStats.cacheSize);
                }
                printWriter.println();
                return;
            }
            printWriter.println(" ");
            printWriter.println(" Objects");
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "Views:", Long.valueOf(j6), "ViewRootImpl:", Long.valueOf(j7));
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "AppContexts:", Long.valueOf(j3), "Activities:", Long.valueOf(j4));
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "Assets:", Integer.valueOf(globalAssetCount), "AssetManagers:", Integer.valueOf(globalAssetManagerCount));
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "Local Binders:", Integer.valueOf(binderLocalObjectCount), "Proxy Binders:", Integer.valueOf(binderProxyObjectCount));
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "Parcel memory:", Long.valueOf(globalAllocSize / 1024), "Parcel count:", Long.valueOf(globalAllocCount));
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "Death Recipients:", Integer.valueOf(binderDeathObjectCount), "WebViews:", Long.valueOf(j5));
            if (com.android.libcore.readonly.Flags.nativeMetrics()) {
                dumpMemInfoNativeAllocations(printWriter);
            }
            printWriter.println(" ");
            printWriter.println(" SQL");
            ActivityThread.printRow(printWriter, ActivityThread.ONE_COUNT_COLUMN, "MEMORY_USED:", Integer.valueOf(databaseInfo.memoryUsed / 1024));
            ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, "PAGECACHE_OVERFLOW:", Integer.valueOf(databaseInfo.pageCacheOverflow / 1024), "MALLOC_SIZE:", Integer.valueOf(databaseInfo.largestMemAlloc / 1024));
            printWriter.println(" ");
            int size = databaseInfo.dbStats.size();
            if (size > 0) {
                printWriter.println(" DATABASES");
                ActivityThread.printRow(printWriter, DB_CONNECTION_INFO_HEADER, "pgsz", "dbsz", "Lookaside(b)", "cache hits", "cache misses", "cache size", "Dbname");
                printWriter.println("PER CONNECTION STATS");
                for (int i2 = 0; i2 < size; i2++) {
                    SQLiteDebug.DbStats dbStats2 = databaseInfo.dbStats.get(i2);
                    if (!dbStats2.arePoolStats) {
                        ActivityThread.printRow(printWriter, DB_CONNECTION_INFO_FORMAT, dbStats2.pageSize > 0 ? String.valueOf(dbStats2.pageSize) : " ", dbStats2.dbSize > 0 ? String.valueOf(dbStats2.dbSize) : " ", dbStats2.lookaside > 0 ? String.valueOf(dbStats2.lookaside) : " ", Integer.valueOf(dbStats2.cacheHits), Integer.valueOf(dbStats2.cacheMisses), Integer.valueOf(dbStats2.cacheSize), dbStats2.dbName);
                    }
                }
                printWriter.println("POOL STATS");
                ActivityThread.printRow(printWriter, DB_POOL_INFO_HEADER, "cache hits", "cache misses", "cache size", "Dbname");
                for (int i3 = 0; i3 < size; i3++) {
                    SQLiteDebug.DbStats dbStats3 = databaseInfo.dbStats.get(i3);
                    if (dbStats3.arePoolStats) {
                        ActivityThread.printRow(printWriter, DB_POOL_INFO_FORMAT, Integer.valueOf(dbStats3.cacheHits), Integer.valueOf(dbStats3.cacheMisses), Integer.valueOf(dbStats3.cacheSize), dbStats3.dbName);
                    }
                }
            }
            String assetAllocations = AssetManager.getAssetAllocations();
            if (assetAllocations != null) {
                printWriter.println(" ");
                printWriter.println(" Asset Allocations");
                printWriter.print(assetAllocations);
            }
            if (z5) {
                boolean z7 = !(ActivityThread.this.mBoundApplication == null || (ActivityThread.this.mBoundApplication.appInfo.flags & 2) == 0) || Build.IS_DEBUGGABLE;
                printWriter.println(" ");
                printWriter.println(" Unreachable memory");
                printWriter.print(Debug.getUnreachableMemory(100, z7));
            }
            if (z6) {
                Debug.logAllocatorStats();
            }
        }

        @Override // android.app.IApplicationThread
        @NeverCompile
        public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                dumpMemInfo(protoOutputStream, memoryInfo, z, z2, z3, z4);
            } finally {
                protoOutputStream.flush();
                IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @NeverCompile
        private void dumpMemInfo(ProtoOutputStream protoOutputStream, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4) {
            long nativeHeapSize = Debug.getNativeHeapSize() / 1024;
            long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeHeapFreeSize = Debug.getNativeHeapFreeSize() / 1024;
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long j = runtime.totalMemory() / 1024;
            long freeMemory = runtime.freeMemory() / 1024;
            long j2 = j - freeMemory;
            long[] countInstancesOfClasses = VMDebug.countInstancesOfClasses(new Class[]{ContextImpl.class, Activity.class, WebView.class, View.class, ViewRootImpl.class}, true);
            long j3 = countInstancesOfClasses[0];
            long j4 = countInstancesOfClasses[1];
            long j5 = countInstancesOfClasses[2];
            long j6 = countInstancesOfClasses[3];
            long j7 = countInstancesOfClasses[4];
            int globalAssetCount = AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = Debug.getBinderProxyObjectCount();
            int binderDeathObjectCount = Debug.getBinderDeathObjectCount();
            long globalAllocSize = Parcel.getGlobalAllocSize();
            long globalAllocCount = Parcel.getGlobalAllocCount();
            SQLiteDebug.PagerStats databaseInfo = SQLiteDebug.getDatabaseInfo();
            long start = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1120986464257L, Process.myPid());
            protoOutputStream.write(1138166333442L, ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown");
            ActivityThread.dumpMemInfoTable(protoOutputStream, memoryInfo, z2, z3, nativeHeapSize, nativeHeapAllocatedSize, nativeHeapFreeSize, j, j2, freeMemory);
            protoOutputStream.end(start);
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, j6);
            long j8 = 1120986464258L;
            protoOutputStream.write(1120986464258L, j7);
            protoOutputStream.write(1120986464259L, j3);
            protoOutputStream.write(1120986464260L, j4);
            protoOutputStream.write(1120986464261L, globalAssetCount);
            protoOutputStream.write(1120986464262L, globalAssetManagerCount);
            protoOutputStream.write(1120986464263L, binderLocalObjectCount);
            protoOutputStream.write(1120986464264L, binderProxyObjectCount);
            protoOutputStream.write(1112396529673L, globalAllocSize / 1024);
            protoOutputStream.write(1120986464266L, globalAllocCount);
            protoOutputStream.write(1120986464267L, binderDeathObjectCount);
            protoOutputStream.write(1120986464269L, j5);
            protoOutputStream.end(start2);
            long start3 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, databaseInfo.memoryUsed / 1024);
            protoOutputStream.write(1120986464258L, databaseInfo.pageCacheOverflow / 1024);
            protoOutputStream.write(1120986464259L, databaseInfo.largestMemAlloc / 1024);
            int size = databaseInfo.dbStats.size();
            int i = 0;
            while (i < size) {
                SQLiteDebug.DbStats dbStats = databaseInfo.dbStats.get(i);
                long start4 = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1138166333441L, dbStats.dbName);
                protoOutputStream.write(j8, dbStats.pageSize);
                protoOutputStream.write(1120986464259L, dbStats.dbSize);
                protoOutputStream.write(1120986464260L, dbStats.lookaside);
                protoOutputStream.write(1120986464262L, dbStats.cacheHits);
                protoOutputStream.write(1120986464263L, dbStats.cacheMisses);
                protoOutputStream.write(1120986464264L, dbStats.cacheSize);
                protoOutputStream.end(start4);
                i++;
                j8 = 1120986464258L;
            }
            protoOutputStream.end(start3);
            String assetAllocations = AssetManager.getAssetAllocations();
            if (assetAllocations != null) {
                protoOutputStream.write(1138166333444L, assetAllocations);
            }
            if (z4) {
                protoOutputStream.write(1138166333445L, Debug.getUnreachableMemory(100, ((ActivityThread.this.mBoundApplication == null ? 0 : ActivityThread.this.mBoundApplication.appInfo.flags) & 2) != 0 || Build.IS_DEBUGGABLE));
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = null;
                    dumpComponentInfo.args = strArr;
                    ActivityThread.this.sendMessage(165, dumpComponentInfo, 0, 0, true);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                } catch (IOException e) {
                    Slog.w(ActivityThread.TAG, "dumpGfxInfo failed", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (Throwable th) {
                IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) {
            try {
                PropertyInvalidatedCache.dumpCacheInfo(parcelFileDescriptor, strArr);
                BroadcastStickyCache.dumpCacheInfo(parcelFileDescriptor);
            } finally {
                IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        private File getDatabasesDir(Context context) {
            return context.getDatabasePath(FullBackup.APK_TREE_TOKEN).getParentFile();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpDatabaseInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, boolean z) {
            FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            SQLiteDebug.dump(new PrintWriterPrinter(fastPrintWriter), strArr, z);
            fastPrintWriter.flush();
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, final String[] strArr) {
            try {
                if (ActivityThread.this.mSystemThread) {
                    final ParcelFileDescriptor dup = parcelFileDescriptor.dup();
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: android.app.ActivityThread.ApplicationThread.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                ApplicationThread.this.dumpDatabaseInfo(dup, strArr, true);
                            } finally {
                                IoUtils.closeQuietly(dup);
                            }
                        }
                    });
                    return;
                }
                dumpDatabaseInfo(parcelFileDescriptor, strArr, false);
            } catch (IOException unused) {
                Log.w(ActivityThread.TAG, "Could not dup FD " + parcelFileDescriptor.getFileDescriptor().getInt$());
            } finally {
                IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(IBinder iBinder) {
            ActivityThread.this.sendMessage(142, iBinder);
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) {
            requestAssistContextExtrasFromCapture(iBinder, iBinder2, i, i2, i3, false);
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtrasFromCapture(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, boolean z) {
            RequestAssistContextExtras requestAssistContextExtras = new RequestAssistContextExtras();
            requestAssistContextExtras.activityToken = iBinder;
            requestAssistContextExtras.requestToken = iBinder2;
            requestAssistContextExtras.requestType = i;
            requestAssistContextExtras.sessionId = i2;
            requestAssistContextExtras.flags = i3;
            requestAssistContextExtras.fromCapture = z;
            ActivityThread.this.sendMessage(143, requestAssistContextExtras);
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(Bundle bundle) {
            ActivityThread.this.sendMessage(138, bundle);
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) {
            UpdateCompatibilityData updateCompatibilityData = new UpdateCompatibilityData();
            updateCompatibilityData.pkg = str;
            updateCompatibilityData.info = compatibilityInfo;
            updateCompatOverrideScale(compatibilityInfo);
            updateCompatOverrideDisplayRotation(compatibilityInfo);
            ActivityThread.this.sendMessage(139, updateCompatibilityData);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int i) {
            PooledRunnable recycleOnUse = PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ActivityThread) obj).handleTrimMemory(((Integer) obj2).intValue());
                }
            }, ActivityThread.this, Integer.valueOf(i)).recycleOnUse();
            Choreographer mainThreadInstance = Choreographer.getMainThreadInstance();
            if (mainThreadInstance != null) {
                mainThreadInstance.postCallback(4, recycleOnUse, null);
            } else {
                ActivityThread.this.mH.post(recycleOnUse);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) {
            ActivityThread.this.sendMessage(144, iBinder, z ? 1 : 0);
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
            ActivityThread.this.sendMessage(146, new Pair(iBinder, sceneTransitionInfo));
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int i) {
            ActivityThread.this.updateProcessState(i, true);
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long j) {
            synchronized (ActivityThread.this.mNetworkPolicyLock) {
                ActivityThread.this.mNetworkBlockSeq = j;
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(ProviderInfo providerInfo) {
            ActivityThread.this.sendMessage(145, providerInfo);
        }

        @Override // android.app.IApplicationThread
        public final void updateTimePrefs(int i) {
            Boolean bool;
            if (i == 0) {
                bool = Boolean.FALSE;
            } else {
                bool = i == 1 ? Boolean.TRUE : null;
            }
            DateFormat.set24HourTimePref(bool);
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(IBinder iBinder) {
            ActivityThread.this.sendMessage(149, iBinder);
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] bArr) {
            if (StrictMode.vmCleartextNetworkEnabled()) {
                StrictMode.onCleartextNetworkDetected(bArr);
            }
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() {
            if (Binder.isSystemServerBinderTrackerEnabled) {
                Message obtain = Message.obtain();
                obtain.what = 4;
                ActivityThread.this.trackingHandler.sendMessage(obtain);
                return;
            }
            ActivityThread.this.sendMessage(150, null);
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) {
            try {
                ActivityThread.this.sendMessage(151, parcelFileDescriptor.dup());
            } catch (IOException unused) {
            } finally {
                IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) {
            try {
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = parcelFileDescriptor.dup();
                obtain.arg2 = str;
                obtain.arg3 = str2;
                obtain.argi1 = i;
                obtain.argi2 = i2;
                Message obtain2 = Message.obtain();
                obtain2.what = 5;
                obtain2.obj = obtain;
                ActivityThread.this.trackingHandler.sendMessage(obtain2);
            } catch (IOException unused) {
            } finally {
                IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = iBinder;
            obtain.arg2 = iVoiceInteractor;
            ActivityThread.this.sendMessage(154, obtain);
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() {
            NetworkSecurityPolicy.getInstance().handleTrustStorageUpdate();
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
            ActivityThread.this.scheduleTransaction(clientTransaction);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer iTaskFragmentOrganizer, TaskFragmentTransaction taskFragmentTransaction) throws RemoteException {
            iTaskFragmentOrganizer.onTransactionReady(taskFragmentTransaction);
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (remoteCallback != null) {
                SafeCancellationTransport createSafeCancellationTransport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle bundle = new Bundle();
                bundle.putBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL, createSafeCancellationTransport.asBinder());
                remoteCallback.sendResult(bundle);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                    ((ActivityThread) obj).handleRequestDirectActions((IBinder) obj2, (IVoiceInteractor) obj3, (CancellationSignal) obj4, (RemoteCallback) obj5, ((Integer) obj6).intValue());
                }
            }, ActivityThread.this, iBinder, iVoiceInteractor, cancellationSignal, remoteCallback2, 7));
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (remoteCallback != null) {
                SafeCancellationTransport createSafeCancellationTransport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle bundle2 = new Bundle();
                bundle2.putBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL, createSafeCancellationTransport.asBinder());
                remoteCallback.sendResult(bundle2);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                    ((ActivityThread) obj).handlePerformDirectAction((IBinder) obj2, (String) obj3, (Bundle) obj4, (CancellationSignal) obj5, (RemoteCallback) obj6);
                }
            }, ActivityThread.this, iBinder, str, bundle, cancellationSignal, remoteCallback2));
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(ContentProviderHolder contentProviderHolder, String str, int i, boolean z) {
            for (String str2 : str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
                ProviderKey getProviderKey = ActivityThread.this.getGetProviderKey(str2, i);
                synchronized (getProviderKey.mLock) {
                    getProviderKey.mHolder = contentProviderHolder;
                    getProviderKey.mLock.notifyAll();
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(ComponentName componentName, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, ApplicationInfo applicationInfo) {
            AppBindData appBindData = new AppBindData();
            appBindData.instrumentationName = componentName;
            appBindData.instrumentationArgs = bundle;
            appBindData.instrumentationWatcher = iInstrumentationWatcher;
            appBindData.instrumentationUiAutomationConnection = iUiAutomationConnection;
            appBindData.appInfo = applicationInfo;
            ActivityThread.this.sendMessage(170, appBindData);
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = iBinder;
            obtain.arg2 = Integer.valueOf(i);
            obtain.arg3 = translationSpec;
            obtain.arg4 = translationSpec2;
            obtain.arg5 = list;
            obtain.arg6 = uiTranslationSpec;
            ActivityThread.this.sendMessage(163, obtain);
        }

        @Override // android.app.IApplicationThread
        public void getExecutableMethodFileOffsets(MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) {
            VMDebug.ExecutableMethodFileOffsets executableMethodFileOffsets;
            Executable parseMethodDescriptor = MethodDescriptorParser.parseMethodDescriptor(getClass().getClassLoader(), methodDescriptor);
            if (com.android.internal.hidden_from_bootclasspath.com.android.art.flags.Flags.executableMethodFileOffsetsV2()) {
                executableMethodFileOffsets = VMDebug.getExecutableMethodFileOffsets(parseMethodDescriptor);
            } else if (parseMethodDescriptor instanceof Method) {
                executableMethodFileOffsets = VMDebug.getExecutableMethodFileOffsets((Method) parseMethodDescriptor);
            } else {
                throw new UnsupportedOperationException();
            }
            try {
                if (executableMethodFileOffsets == null) {
                    iOffsetCallback.onResult(null);
                    return;
                }
                ExecutableMethodFileOffsets executableMethodFileOffsets2 = new ExecutableMethodFileOffsets();
                executableMethodFileOffsets2.containerPath = executableMethodFileOffsets.getContainerPath();
                executableMethodFileOffsets2.containerOffset = executableMethodFileOffsets.getContainerOffset();
                executableMethodFileOffsets2.methodOffset = executableMethodFileOffsets.getMethodOffset();
                iOffsetCallback.onResult(executableMethodFileOffsets2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.app.IApplicationThread
        public void getProfileLength(String str) {
            if (CoreRune.SYSPERF_ACTIVE_APP_ADCP_ENABLE) {
                ActivityThread.this.sendMessage(169, str);
            }
        }

        @Override // android.app.IApplicationThread
        public void setFlingerFlag(String str) {
            ActivityThread.this.sendMessage(188, str);
        }

        @Override // android.app.IApplicationThread
        public void setViewVisibleFlag(int i) {
            ActivityThread activityThread = ActivityThread.this;
            activityThread.sendMessage(189, activityThread.getProcessName(), i);
        }

        @Override // android.app.IApplicationThread
        public void clearIdsTrainingData(boolean z) {
            IdsController unused = ActivityThread.this.mIdsController;
            IdsController.clearTrainingData(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SafeCancellationTransport createSafeCancellationTransport(CancellationSignal cancellationSignal) {
        SafeCancellationTransport safeCancellationTransport;
        synchronized (this) {
            if (this.mRemoteCancellations == null) {
                this.mRemoteCancellations = new ArrayMap();
            }
            safeCancellationTransport = new SafeCancellationTransport(this, cancellationSignal);
            this.mRemoteCancellations.put(safeCancellationTransport, cancellationSignal);
        }
        return safeCancellationTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CancellationSignal removeSafeCancellationTransport(SafeCancellationTransport safeCancellationTransport) {
        CancellationSignal remove;
        synchronized (this) {
            remove = this.mRemoteCancellations.remove(safeCancellationTransport);
            if (this.mRemoteCancellations.isEmpty()) {
                this.mRemoteCancellations = null;
            }
        }
        return remove;
    }

    private static final class SafeCancellationTransport extends ICancellationSignal.Stub {
        private final WeakReference<ActivityThread> mWeakActivityThread;

        SafeCancellationTransport(ActivityThread activityThread, CancellationSignal cancellationSignal) {
            this.mWeakActivityThread = new WeakReference<>(activityThread);
        }

        @Override // android.os.ICancellationSignal
        public void cancel() {
            CancellationSignal removeSafeCancellationTransport;
            ActivityThread activityThread = this.mWeakActivityThread.get();
            if (activityThread == null || (removeSafeCancellationTransport = activityThread.removeSafeCancellationTransport(this)) == null) {
                return;
            }
            removeSafeCancellationTransport.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void throwRemoteServiceException(String str, int i, Bundle bundle) {
        switch (i) {
            case 1:
                throw generateForegroundServiceDidNotStartInTimeException(str, bundle);
            case 2:
                throw new RemoteServiceException.CannotPostForegroundServiceNotificationException(str);
            case 3:
                throw new RemoteServiceException.BadForegroundServiceNotificationException(str);
            case 4:
                throw new RemoteServiceException.MissingRequestPasswordComplexityPermissionException(str);
            case 5:
                throw new RemoteServiceException.CrashedByAdbException(str);
            case 6:
                throw new RemoteServiceException.BadUserInitiatedJobNotificationException(str);
            case 7:
                throw generateForegroundServiceDidNotStopInTimeException(str, bundle);
            default:
                throw new RemoteServiceException(str + " (with unwknown typeId:" + i + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private RemoteServiceException.ForegroundServiceDidNotStartInTimeException generateForegroundServiceDidNotStartInTimeException(String str, Bundle bundle) {
        String serviceClassNameFromExtras = RemoteServiceException.ForegroundServiceDidNotStartInTimeException.getServiceClassNameFromExtras(bundle);
        throw new RemoteServiceException.ForegroundServiceDidNotStartInTimeException(str, serviceClassNameFromExtras == null ? null : Service.getStartForegroundServiceStackTrace(serviceClassNameFromExtras));
    }

    private RemoteServiceException.ForegroundServiceDidNotStopInTimeException generateForegroundServiceDidNotStopInTimeException(String str, Bundle bundle) {
        String serviceClassNameFromExtras = RemoteServiceException.ForegroundServiceDidNotStopInTimeException.getServiceClassNameFromExtras(bundle);
        throw new RemoteServiceException.ForegroundServiceDidNotStopInTimeException(str, serviceClassNameFromExtras == null ? null : Service.getStartForegroundServiceStackTrace(serviceClassNameFromExtras));
    }

    private class WebviewRunnable implements Runnable {
        private WebviewRunnable(ActivityThread activityThread) {
        }

        @Override // java.lang.Runnable
        public void run() {
            Process.setThreadPriority(10);
            try {
                Class<?> cls = Class.forName("android.webkit.WebViewFactory");
                Class[] clsArr = new Class[0];
                Method declaredMethod = cls.getDeclaredMethod("getProvider", null);
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(cls, null);
                }
            } catch (ReflectiveOperationException e) {
                throw new AndroidRuntimeException("webview invoke failed!!!", e);
            }
        }
    }

    class H extends Handler {
        public static final int ADCP_CHECK_PROFILE_SIZE = 169;
        public static final int APPLICATION_INFO_CHANGED = 156;
        public static final int ATTACH_AGENT = 155;
        public static final int ATTACH_STARTUP_AGENTS = 162;
        public static final int BBA_SET_FLINGER_FLAG = 188;
        public static final int BBA_SET_VISIBLE_FLAG = 189;
        public static final int BIND_APPLICATION = 110;
        public static final int BIND_SERVICE = 121;
        public static final int CLEAN_UP_CONTEXT = 119;
        public static final int CONFIGURATION_CHANGED = 118;
        public static final int CREATE_BACKUP_AGENT = 128;
        public static final int CREATE_SERVICE = 114;
        public static final int DESTROY_BACKUP_AGENT = 129;
        public static final int DISPATCH_PACKAGE_BROADCAST = 133;
        public static final int DUMP_ACTIVITY = 136;
        public static final int DUMP_GFXINFO = 165;
        public static final int DUMP_HEAP = 135;
        public static final int DUMP_PROVIDER = 141;
        public static final int DUMP_RESOURCES = 166;
        public static final int DUMP_SERVICE = 123;
        public static final int ENTER_ANIMATION_COMPLETE = 149;
        public static final int EXECUTE_TRANSACTION = 159;
        public static final int EXIT_APPLICATION = 111;
        public static final int FINISH_INSTRUMENTATION_WITHOUT_RESTART = 171;
        public static final int GC_WHEN_IDLE = 120;
        public static final int INSTALL_PROVIDER = 145;
        public static final int INSTRUMENT_WITHOUT_RESTART = 170;
        public static final int LOCAL_VOICE_INTERACTION_STARTED = 154;
        public static final int LOW_MEMORY = 124;
        public static final int ON_NEW_SCENE_TRANSITION_INFO = 146;
        public static final int PING = 168;
        public static final int PROFILER_CONTROL = 127;
        public static final int PURGE_RESOURCES = 161;
        public static final int RECEIVER = 113;
        public static final int RELAUNCH_ACTIVITY = 160;
        public static final int RELAUNCH_WEBVIEW_ACTIVITY = 174;
        public static final int REMOVE_PROVIDER = 131;
        public static final int REQUEST_ASSIST_CONTEXT_EXTRAS = 143;
        public static final int RUN_ISOLATED_ENTRY_POINT = 158;
        public static final int SCHEDULE_CRASH = 134;
        public static final int SERVICE_ARGS = 115;
        public static final int SET_CONTENT_CAPTURE_OPTIONS_CALLBACK = 164;
        public static final int SET_CORE_SETTINGS = 138;
        public static final int SLEEPING = 137;
        public static final int START_BINDER_TRACKING = 150;
        public static final int STOP_BINDER_TRACKING_AND_DUMP = 151;
        public static final int STOP_SERVICE = 116;
        public static final int SUICIDE = 130;
        public static final int TIMEOUT_SERVICE = 167;
        public static final int TIMEOUT_SERVICE_FOR_TYPE = 172;
        public static final int TRANSLUCENT_CONVERSION_COMPLETE = 144;
        public static final int UNBIND_SERVICE = 122;
        public static final int UNSTABLE_PROVIDER_DIED = 142;
        public static final int UPDATE_PACKAGE_COMPATIBILITY_INFO = 139;
        public static final int UPDATE_UI_TRANSLATION_STATE = 163;

        H() {
        }

        String codeToString(int i) {
            return Integer.toString(i);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:188:0x0529  */
        /* JADX WARN: Removed duplicated region for block: B:190:0x0530  */
        /* JADX WARN: Removed duplicated region for block: B:195:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r12) {
            /*
                Method dump skipped, instructions count: 1518
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.H.handleMessage(android.os.Message):void");
        }
    }

    private class Idler implements MessageQueue.IdleHandler {
        private Idler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            boolean z = (ActivityThread.this.mBoundApplication == null || ActivityThread.this.mProfiler.profileFd == null || !ActivityThread.this.mProfiler.autoStopProfiler) ? false : true;
            ActivityClient activityClient = ActivityClient.getInstance();
            while (ActivityThread.this.mNewActivities.size() > 0) {
                ActivityClientRecord remove = ActivityThread.this.mNewActivities.remove(0);
                if (remove.activity != null && !remove.activity.mFinished) {
                    activityClient.activityIdle(remove.token, remove.createdConfig, z);
                    remove.createdConfig = null;
                    EventLogTags.writeWmOnIdleCalled(remove.activityInfo.getComponentName().toShortString());
                }
            }
            if (z) {
                ActivityThread.this.mProfiler.stopProfiling();
            }
            return false;
        }
    }

    final class GcIdler implements MessageQueue.IdleHandler {
        GcIdler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            ActivityThread.this.doGcIfNeeded();
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    final class PurgeIdler implements MessageQueue.IdleHandler {
        PurgeIdler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public boolean queueIdle() {
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    public static ActivityThread currentActivityThread() {
        return sCurrentActivityThread;
    }

    public static boolean isSystem() {
        if (sCurrentActivityThread != null) {
            return sCurrentActivityThread.mSystemThread;
        }
        return false;
    }

    public static String currentOpPackageName() {
        ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || currentActivityThread.getApplication() == null) {
            return null;
        }
        return currentActivityThread.getApplication().getOpPackageName();
    }

    public static AttributionSource currentAttributionSource() {
        ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || currentActivityThread.getApplication() == null) {
            return null;
        }
        return currentActivityThread.getApplication().getAttributionSource();
    }

    public static String currentPackageName() {
        AppBindData appBindData;
        ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || (appBindData = currentActivityThread.mBoundApplication) == null) {
            return null;
        }
        return appBindData.appInfo.packageName;
    }

    public static String currentProcessName() {
        AppBindData appBindData;
        ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || (appBindData = currentActivityThread.mBoundApplication) == null) {
            return null;
        }
        return appBindData.processName;
    }

    public static Application currentApplication() {
        ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread != null) {
            return currentActivityThread.mInitialApplication;
        }
        return null;
    }

    public static IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            return sPackageManager;
        }
        sPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        return sPackageManager;
    }

    public static IPermissionManager getPermissionManager() {
        if (sPermissionManager != null) {
            return sPermissionManager;
        }
        sPermissionManager = IPermissionManager.Stub.asInterface(ServiceManager.getService("permissionmgr"));
        return sPermissionManager;
    }

    Resources getTopLevelResources(String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, LoadedApk loadedApk, Configuration configuration) {
        return this.mResourcesManager.getResources(null, str, strArr, strArr2, strArr3, strArr4, null, configuration, loadedApk.getCompatibilityInfo(), loadedApk.getClassLoader(), null);
    }

    public Handler getHandler() {
        return this.mH;
    }

    public final LoadedApk getPackageInfo(String str, CompatibilityInfo compatibilityInfo, int i) {
        return getPackageInfo(str, compatibilityInfo, i, UserHandle.myUserId());
    }

    public final LoadedApk getPackageInfo(String str, CompatibilityInfo compatibilityInfo, int i, int i2) {
        WeakReference<LoadedApk> weakReference;
        boolean z = UserHandle.myUserId() != i2;
        if (i2 < 0) {
            i2 = UserHandle.myUserId();
        }
        ApplicationInfo applicationInfoAsUserCached = PackageManager.getApplicationInfoAsUserCached(str, 268436480L, i2);
        synchronized (this.mResourcesManager) {
            if (z) {
                weakReference = null;
            } else if ((i & 1) != 0) {
                weakReference = this.mPackages.get(str);
            } else {
                weakReference = this.mResourcePackages.get(str);
            }
            LoadedApk loadedApk = weakReference != null ? weakReference.get() : null;
            if (applicationInfoAsUserCached == null || loadedApk == null) {
                if (applicationInfoAsUserCached != null) {
                    return getPackageInfo(applicationInfoAsUserCached, compatibilityInfo, i);
                }
                return null;
            }
            if (!isLoadedApkResourceDirsUpToDate(loadedApk, applicationInfoAsUserCached)) {
                ArrayList arrayList = new ArrayList();
                LoadedApk.makePaths(this, applicationInfoAsUserCached, arrayList);
                loadedApk.updateApplicationInfo(applicationInfoAsUserCached, arrayList);
            }
            if (loadedApk.isSecurityViolation() && (i & 2) == 0) {
                throw new SecurityException("Requesting code from " + str + " to be run in process " + this.mBoundApplication.processName + "/" + this.mBoundApplication.appInfo.uid);
            }
            return loadedApk;
        }
    }

    public final LoadedApk getPackageInfo(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = z && applicationInfo.uid != 0 && applicationInfo.uid != 1000 && (this.mBoundApplication == null || !UserHandle.isSameApp(applicationInfo.uid, this.mBoundApplication.appInfo.uid));
        boolean z3 = z && (1073741824 & i) != 0;
        if ((i & 3) == 1 && z2) {
            String str = "Requesting code from " + applicationInfo.packageName + " (with uid " + applicationInfo.uid + NavigationBarInflaterView.KEY_CODE_END;
            if (this.mBoundApplication != null) {
                str = str + " to be run in process " + this.mBoundApplication.processName + " (with uid " + this.mBoundApplication.appInfo.uid + NavigationBarInflaterView.KEY_CODE_END;
            }
            throw new SecurityException(str);
        }
        return getPackageInfo(applicationInfo, compatibilityInfo, null, z2, z, z3);
    }

    public final LoadedApk getPackageInfoNoCheck(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo) {
        return getPackageInfo(applicationInfo, compatibilityInfo, null, false, true, false);
    }

    @Override // android.app.ClientTransactionHandler
    public LoadedApk getPackageInfoNoCheck(ApplicationInfo applicationInfo) {
        return getPackageInfo(applicationInfo, this.mCompatibilityInfo, null, false, true, false);
    }

    public final LoadedApk peekPackageInfo(String str, boolean z) {
        WeakReference<LoadedApk> weakReference;
        LoadedApk loadedApk;
        synchronized (this.mResourcesManager) {
            if (z) {
                weakReference = this.mPackages.get(str);
            } else {
                weakReference = this.mResourcePackages.get(str);
            }
            loadedApk = weakReference != null ? weakReference.get() : null;
        }
        return loadedApk;
    }

    private LoadedApk getPackageInfo(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, boolean z, boolean z2, boolean z3) {
        return getPackageInfo(applicationInfo, compatibilityInfo, classLoader, z, z2, z3, Process.isSdkSandbox());
    }

    private LoadedApk getPackageInfo(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, boolean z, boolean z2, boolean z3, boolean z4) {
        WeakReference<LoadedApk> weakReference;
        boolean z5 = true;
        boolean z6 = UserHandle.myUserId() != UserHandle.getUserId(applicationInfo.uid);
        synchronized (this.mResourcesManager) {
            try {
                if (z6 || z4) {
                    weakReference = null;
                } else if (z2) {
                    weakReference = this.mPackages.get(applicationInfo.packageName);
                } else {
                    weakReference = this.mResourcePackages.get(applicationInfo.packageName);
                }
                LoadedApk loadedApk = weakReference != null ? weakReference.get() : null;
                if (loadedApk != null) {
                    if (!isLoadedApkResourceDirsUpToDate(loadedApk, applicationInfo)) {
                        if (loadedApk.getApplicationInfo().createTimestamp > applicationInfo.createTimestamp) {
                            Slog.w(TAG, "getPackageInfo() called with an older ApplicationInfo than the cached version for package " + applicationInfo.packageName);
                        } else {
                            Slog.v(TAG, "getPackageInfo() caused update to cached ApplicationInfo for package " + applicationInfo.packageName);
                            ArrayList arrayList = new ArrayList();
                            LoadedApk.makePaths(this, applicationInfo, arrayList);
                            loadedApk.updateApplicationInfo(applicationInfo, arrayList);
                        }
                    }
                    return loadedApk;
                }
                if (!z2 || (applicationInfo.flags & 4) == 0) {
                    z5 = false;
                }
                LoadedApk loadedApk2 = new LoadedApk(this, applicationInfo, compatibilityInfo, classLoader, z, z5, z3);
                if (this.mSystemThread && "android".equals(applicationInfo.packageName)) {
                    loadedApk2.installSystemApplicationInfo(applicationInfo, getSystemContext().mPackageInfo.getClassLoader());
                }
                if (!z6 && !z4) {
                    if (z2) {
                        this.mPackages.put(applicationInfo.packageName, new WeakReference<>(loadedApk2));
                    } else {
                        this.mResourcePackages.put(applicationInfo.packageName, new WeakReference<>(loadedApk2));
                    }
                }
                return loadedApk2;
            } finally {
            }
        }
    }

    private static boolean isLoadedApkResourceDirsUpToDate(LoadedApk loadedApk, ApplicationInfo applicationInfo) {
        Resources resources = loadedApk.mResources;
        return (resources == null || resources.getAssets().isUpToDate()) && Arrays.equals(ArrayUtils.defeatNullable(applicationInfo.resourceDirs), ArrayUtils.defeatNullable(loadedApk.getOverlayDirs())) && Arrays.equals(ArrayUtils.defeatNullable(applicationInfo.overlayPaths), ArrayUtils.defeatNullable(loadedApk.getOverlayPaths()));
    }

    ActivityThread() {
        this.trackingHandler = null;
        HandlerThread handlerThread = new HandlerThread("AppBinderTrackerThread");
        this.trackingThread = handlerThread;
        H h = new H();
        this.mH = h;
        this.mExecutor = new HandlerExecutor(h);
        this.mActivities = new ArrayMap<>();
        this.mPendingOverrideConfigs = new ArrayMap<>();
        this.mPendingAppInfoUpdates = new ArrayMap<>();
        this.mActivitiesToBeDestroyed = Collections.synchronizedMap(new ArrayMap());
        this.mNewActivities = new ArrayList<>();
        this.mNumVisibleActivities = 0;
        this.mNumLaunchingActivities = new AtomicInteger();
        this.mLastProcessState = -1;
        this.mLastAssistStructures = new ArrayList<>();
        this.mConfigurationChangedListenerController = new ConfigurationChangedListenerController();
        this.mLastReportedDeviceId = 0;
        this.mServicesData = new ArrayMap<>();
        this.mServices = new ArrayMap<>();
        this.mUpdateHttpProxyOnBind = false;
        this.mAllApplications = new ArrayList<>();
        this.mBackupAgentsByUser = new SparseArray<>();
        this.mInstrumentationPackageName = null;
        this.mInstrumentationAppDir = null;
        this.mInstrumentationSplitAppDirs = null;
        this.mInstrumentationLibDir = null;
        this.mInstrumentedAppDir = null;
        this.mInstrumentedSplitAppDirs = null;
        this.mInstrumentedLibDir = null;
        this.mSystemThread = false;
        this.mSomeActivitiesChanged = false;
        this.mPackages = new ArrayMap<>();
        this.mResourcePackages = new ArrayMap<>();
        this.mRelaunchingActivities = new ArrayList<>();
        this.mPendingConfiguration = null;
        this.mTransactionExecutor = new TransactionExecutor(this);
        this.mIdsController = null;
        this.mAbnormalUsage = new AbnormalUsage();
        this.mProviderMap = new ArrayMap<>();
        this.mProviderRefCountMap = new ArrayMap<>();
        this.mLocalProviders = new ArrayMap<>();
        this.mLocalProvidersByName = new ArrayMap<>();
        this.mGetProviderKeys = new ArrayMap<>();
        this.mOnPauseListeners = new ArrayMap<>();
        this.mGcIdler = new GcIdler();
        this.mPurgeIdler = new PurgeIdler();
        this.mPurgeIdlerScheduled = false;
        this.mGcIdlerScheduled = false;
        this.mCoreSettings = null;
        this.mCoreSettingsLock = new Object();
        this.mContentCaptureOptionsCallback = null;
        this.mMultiWindowCoreStateListeners = new ArrayList();
        this.webviewPreloaded = false;
        this.webviewPreloadState = -1;
        if (Binder.isSystemServerBinderTrackerEnabled) {
            handlerThread.start();
            this.trackingHandler = new Handler(handlerThread.getLooper()) { // from class: android.app.ActivityThread.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 4) {
                        ActivityThread.this.handleStartBinderTracking();
                    } else {
                        if (i != 5) {
                            return;
                        }
                        ActivityThread.this.handleStopBinderTrackingAndDump((ParcelFileDescriptor) ((SomeArgs) message.obj).arg1, (String) ((SomeArgs) message.obj).arg2, (String) ((SomeArgs) message.obj).arg3, ((SomeArgs) message.obj).argi1, ((SomeArgs) message.obj).argi2);
                        ((SomeArgs) message.obj).recycle();
                    }
                }
            };
        }
        this.mResourcesManager = ResourcesManager.getInstance();
    }

    public static ActivityThread createSystemActivityThreadForTesting() {
        ActivityThread activityThread = new ActivityThread();
        activityThread.mSystemThread = true;
        initializeSystemThread(activityThread);
        return activityThread;
    }

    public ApplicationThread getApplicationThread() {
        return this.mAppThread;
    }

    public Instrumentation getInstrumentation() {
        return this.mInstrumentation;
    }

    public boolean isProfiling() {
        Profiler profiler = this.mProfiler;
        return (profiler == null || profiler.profileFile == null || this.mProfiler.profileFd != null) ? false : true;
    }

    public String getProfileFilePath() {
        return this.mProfiler.profileFile;
    }

    public Looper getLooper() {
        return this.mLooper;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    @Override // android.app.ActivityThreadInternal
    public Application getApplication() {
        return this.mInitialApplication;
    }

    public String getProcessName() {
        return this.mBoundApplication.processName;
    }

    @Override // android.app.ActivityThreadInternal
    public ContextImpl getSystemContext() {
        ContextImpl contextImpl;
        synchronized (this) {
            if (this.mSystemContext == null) {
                this.mSystemContext = ContextImpl.createSystemContext(this);
            }
            contextImpl = this.mSystemContext;
        }
        return contextImpl;
    }

    public Context getSystemUiContext() {
        return getSystemUiContext(0);
    }

    public Context getSystemUiContext(int i) {
        synchronized (this) {
            if (this.mDisplaySystemUiContexts == null) {
                this.mDisplaySystemUiContexts = new ArrayList<>();
            }
            this.mDisplaySystemUiContexts.removeIf(new Predicate() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean refersTo;
                    refersTo = ((WeakReference) obj).refersTo(null);
                    return refersTo;
                }
            });
            Context systemUiContextNoCreateLocked = getSystemUiContextNoCreateLocked(i);
            if (systemUiContextNoCreateLocked != null) {
                return systemUiContextNoCreateLocked;
            }
            Context createSystemUiContext = ContextImpl.createSystemUiContext(getSystemContext(), i);
            this.mDisplaySystemUiContexts.add(new WeakReference<>(createSystemUiContext));
            return createSystemUiContext;
        }
    }

    public Context createSystemUiContextForTesting(int i) {
        return ContextImpl.createSystemUiContext(getSystemContext(), i);
    }

    @Override // android.app.ActivityThreadInternal
    public Context getSystemUiContextNoCreate() {
        synchronized (this) {
            if (this.mDisplaySystemUiContexts == null) {
                return null;
            }
            return getSystemUiContextNoCreateLocked(0);
        }
    }

    private Context getSystemUiContextNoCreateLocked(int i) {
        for (int i2 = 0; i2 < this.mDisplaySystemUiContexts.size(); i2++) {
            Context context = this.mDisplaySystemUiContexts.get(i2).get();
            if (context != null && context.getDisplayId() == i) {
                return context;
            }
        }
        return null;
    }

    void onSystemUiContextCleanup(final ContextImpl contextImpl) {
        synchronized (this) {
            ArrayList<WeakReference<Context>> arrayList = this.mDisplaySystemUiContexts;
            if (arrayList == null) {
                return;
            }
            arrayList.removeIf(new Predicate() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ActivityThread.lambda$onSystemUiContextCleanup$1(ContextImpl.this, (WeakReference) obj);
                }
            });
        }
    }

    static /* synthetic */ boolean lambda$onSystemUiContextCleanup$1(ContextImpl contextImpl, WeakReference weakReference) {
        return weakReference.refersTo(null) || weakReference.refersTo(contextImpl);
    }

    public void installSystemApplicationInfo(ApplicationInfo applicationInfo, ClassLoader classLoader) {
        synchronized (this) {
            getSystemContext().installSystemApplicationInfo(applicationInfo, classLoader);
            ContextImpl.getImpl(getSystemUiContext()).installSystemApplicationInfo(applicationInfo, classLoader);
            this.mProfiler = new Profiler();
        }
    }

    void scheduleGcIdler() {
        if (!this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void unscheduleGcIdler() {
        if (this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void schedulePurgeIdler() {
        if (!this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void unschedulePurgeIdler() {
        if (this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void doGcIfNeeded() {
        doGcIfNeeded("bg");
    }

    void doGcIfNeeded(String str) {
        this.mGcIdlerScheduled = false;
        if (BinderInternal.getLastGcTime() + 5000 < SystemClock.uptimeMillis()) {
            BinderInternal.forceGc(str);
        }
    }

    static void printRow(PrintWriter printWriter, String str, Object... objArr) {
        printWriter.println(String.format(Locale.US, str, objArr));
    }

    @NeverCompile
    public static void dumpMemInfoTable(PrintWriter printWriter, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, int i, String str, long j, long j2, long j3, long j4, long j5, long j6) {
        int i2;
        int i3;
        int i4;
        if (z) {
            printWriter.print(4);
            printWriter.print(',');
            printWriter.print(i);
            printWriter.print(',');
            printWriter.print(str);
            printWriter.print(',');
            printWriter.print(j);
            printWriter.print(',');
            printWriter.print(j4);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j + j4);
            printWriter.print(',');
            printWriter.print(j2);
            printWriter.print(',');
            printWriter.print(j5);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j2 + j5);
            printWriter.print(',');
            printWriter.print(j3);
            printWriter.print(',');
            printWriter.print(j6);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j3 + j6);
            printWriter.print(',');
            printWriter.print(memoryInfo.nativePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikPss);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherPss);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalPss());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSwappablePss());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSharedDirty());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSharedClean());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativePrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikPrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherPrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalPrivateDirty());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativePrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikPrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherPrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalPrivateClean());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSwappedOut());
            printWriter.print(',');
            if (memoryInfo.hasSwappedOutPss) {
                printWriter.print(memoryInfo.nativeSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo.dalvikSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo.otherSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo.getTotalSwappedOutPss());
                printWriter.print(',');
            } else {
                printWriter.print("N/A,");
                printWriter.print("N/A,");
                printWriter.print("N/A,");
                printWriter.print("N/A,");
            }
            for (int i5 = 0; i5 < 17; i5++) {
                printWriter.print(Debug.MemoryInfo.getOtherLabel(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherPss(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSwappablePss(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSharedDirty(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSharedClean(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherPrivateDirty(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherPrivateClean(i5));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSwappedOut(i5));
                printWriter.print(',');
                if (memoryInfo.hasSwappedOutPss) {
                    printWriter.print(memoryInfo.getOtherSwappedOutPss(i5));
                    printWriter.print(',');
                } else {
                    printWriter.print("N/A,");
                }
            }
            return;
        }
        if (!z4) {
            if (z2) {
                printRow(printWriter, HEAP_FULL_COLUMN, "", "Pss", "Pss", "Shared", "Private", "Shared", "Private", memoryInfo.hasSwappedOutPss ? "SwapPss" : "Swap", "Rss", "Heap", "Heap", "Heap");
                printRow(printWriter, HEAP_FULL_COLUMN, "", "Total", "Clean", "Dirty", "Dirty", "Clean", "Clean", "Dirty", "Total", "Size", "Alloc", "Free");
                printRow(printWriter, HEAP_FULL_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                printRow(printWriter, HEAP_FULL_COLUMN, "Native Heap", Integer.valueOf(memoryInfo.nativePss), Integer.valueOf(memoryInfo.nativeSwappablePss), Integer.valueOf(memoryInfo.nativeSharedDirty), Integer.valueOf(memoryInfo.nativePrivateDirty), Integer.valueOf(memoryInfo.nativeSharedClean), Integer.valueOf(memoryInfo.nativePrivateClean), Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut), Integer.valueOf(memoryInfo.nativeRss), Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
                printRow(printWriter, HEAP_FULL_COLUMN, "Dalvik Heap", Integer.valueOf(memoryInfo.dalvikPss), Integer.valueOf(memoryInfo.dalvikSwappablePss), Integer.valueOf(memoryInfo.dalvikSharedDirty), Integer.valueOf(memoryInfo.dalvikPrivateDirty), Integer.valueOf(memoryInfo.dalvikSharedClean), Integer.valueOf(memoryInfo.dalvikPrivateClean), Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut), Integer.valueOf(memoryInfo.dalvikRss), Long.valueOf(j4), Long.valueOf(j5), Long.valueOf(j6));
            } else {
                printRow(printWriter, HEAP_COLUMN, "", "Pss", "Private", "Private", memoryInfo.hasSwappedOutPss ? "SwapPss" : "Swap", "Rss", "Heap", "Heap", "Heap");
                printRow(printWriter, HEAP_COLUMN, "", "Total", "Dirty", "Clean", "Dirty", "Total", "Size", "Alloc", "Free");
                printRow(printWriter, HEAP_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                printRow(printWriter, HEAP_COLUMN, "Native Heap", Integer.valueOf(memoryInfo.nativePss), Integer.valueOf(memoryInfo.nativePrivateDirty), Integer.valueOf(memoryInfo.nativePrivateClean), Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut), Integer.valueOf(memoryInfo.nativeRss), Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
                printRow(printWriter, HEAP_COLUMN, "Dalvik Heap", Integer.valueOf(memoryInfo.dalvikPss), Integer.valueOf(memoryInfo.dalvikPrivateDirty), Integer.valueOf(memoryInfo.dalvikPrivateClean), Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut), Integer.valueOf(memoryInfo.dalvikRss), Long.valueOf(j4), Long.valueOf(j5), Long.valueOf(j6));
            }
            int i6 = memoryInfo.otherPss;
            int i7 = memoryInfo.otherSwappablePss;
            int i8 = memoryInfo.otherSharedDirty;
            int i9 = memoryInfo.otherPrivateDirty;
            int i10 = memoryInfo.otherSharedClean;
            int i11 = memoryInfo.otherPrivateClean;
            int i12 = memoryInfo.otherSwappedOut;
            int i13 = memoryInfo.otherSwappedOutPss;
            int i14 = memoryInfo.otherRss;
            int i15 = i8;
            int i16 = i6;
            int i17 = 0;
            int i18 = i7;
            while (i17 < 17) {
                int otherPss = memoryInfo.getOtherPss(i17);
                int otherSwappablePss = memoryInfo.getOtherSwappablePss(i17);
                int otherSharedDirty = memoryInfo.getOtherSharedDirty(i17);
                int otherPrivateDirty = memoryInfo.getOtherPrivateDirty(i17);
                int otherSharedClean = memoryInfo.getOtherSharedClean(i17);
                int otherPrivateClean = memoryInfo.getOtherPrivateClean(i17);
                int otherSwappedOut = memoryInfo.getOtherSwappedOut(i17);
                int otherSwappedOutPss = memoryInfo.getOtherSwappedOutPss(i17);
                int otherRss = memoryInfo.getOtherRss(i17);
                if (otherPss == 0 && otherSharedDirty == 0 && otherPrivateDirty == 0 && otherSharedClean == 0 && otherPrivateClean == 0 && otherRss == 0) {
                    if ((memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut) == 0) {
                        i3 = i16;
                        i4 = i14;
                        i17++;
                        i16 = i3;
                        i14 = i4;
                    }
                }
                if (z2) {
                    printRow(printWriter, HEAP_FULL_COLUMN, Debug.MemoryInfo.getOtherLabel(i17), Integer.valueOf(otherPss), Integer.valueOf(otherSwappablePss), Integer.valueOf(otherSharedDirty), Integer.valueOf(otherPrivateDirty), Integer.valueOf(otherSharedClean), Integer.valueOf(otherPrivateClean), Integer.valueOf(memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut), Integer.valueOf(otherRss), "", "", "");
                } else {
                    printRow(printWriter, HEAP_COLUMN, Debug.MemoryInfo.getOtherLabel(i17), Integer.valueOf(otherPss), Integer.valueOf(otherPrivateDirty), Integer.valueOf(otherPrivateClean), Integer.valueOf(memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut), Integer.valueOf(otherRss), "", "", "");
                }
                i3 = i16 - otherPss;
                i18 -= otherSwappablePss;
                i15 -= otherSharedDirty;
                i9 -= otherPrivateDirty;
                i10 -= otherSharedClean;
                i11 -= otherPrivateClean;
                i12 -= otherSwappedOut;
                i13 -= otherSwappedOutPss;
                i4 = i14 - otherRss;
                i17++;
                i16 = i3;
                i14 = i4;
            }
            if (z2) {
                Integer valueOf = Integer.valueOf(i16);
                Integer valueOf2 = Integer.valueOf(i18);
                Integer valueOf3 = Integer.valueOf(i15);
                Integer valueOf4 = Integer.valueOf(i9);
                Integer valueOf5 = Integer.valueOf(i10);
                Integer valueOf6 = Integer.valueOf(i11);
                if (memoryInfo.hasSwappedOutPss) {
                    i12 = i13;
                }
                printRow(printWriter, HEAP_FULL_COLUMN, LsConstants.TAG_UNKNOWN, valueOf, valueOf2, valueOf3, valueOf4, valueOf5, valueOf6, Integer.valueOf(i12), Integer.valueOf(i14), "", "", "");
                printRow(printWriter, HEAP_FULL_COLUMN, "TOTAL", Integer.valueOf(memoryInfo.getTotalPss()), Integer.valueOf(memoryInfo.getTotalSwappablePss()), Integer.valueOf(memoryInfo.getTotalSharedDirty()), Integer.valueOf(memoryInfo.getTotalPrivateDirty()), Integer.valueOf(memoryInfo.getTotalSharedClean()), Integer.valueOf(memoryInfo.getTotalPrivateClean()), Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.getTotalSwappedOutPss() : memoryInfo.getTotalSwappedOut()), Integer.valueOf(memoryInfo.getTotalRss()), Long.valueOf(j + j4), Long.valueOf(j2 + j5), Long.valueOf(j3 + j6));
            } else {
                Integer valueOf7 = Integer.valueOf(i16);
                Integer valueOf8 = Integer.valueOf(i9);
                Integer valueOf9 = Integer.valueOf(i11);
                if (memoryInfo.hasSwappedOutPss) {
                    i12 = i13;
                }
                printRow(printWriter, HEAP_COLUMN, LsConstants.TAG_UNKNOWN, valueOf7, valueOf8, valueOf9, Integer.valueOf(i12), Integer.valueOf(i14), "", "", "");
                printRow(printWriter, HEAP_COLUMN, "TOTAL", Integer.valueOf(memoryInfo.getTotalPss()), Integer.valueOf(memoryInfo.getTotalPrivateDirty()), Integer.valueOf(memoryInfo.getTotalPrivateClean()), Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.getTotalSwappedOutPss() : memoryInfo.getTotalSwappedOut()), Integer.valueOf(memoryInfo.getTotalRss()), Long.valueOf(j + j4), Long.valueOf(j2 + j5), Long.valueOf(j3 + j6));
            }
            if (z3) {
                printWriter.println(" ");
                printWriter.println(" Dalvik Details");
                while (i2 < 32) {
                    int otherPss2 = memoryInfo.getOtherPss(i2);
                    int otherSwappablePss2 = memoryInfo.getOtherSwappablePss(i2);
                    int otherSharedDirty2 = memoryInfo.getOtherSharedDirty(i2);
                    int otherPrivateDirty2 = memoryInfo.getOtherPrivateDirty(i2);
                    int otherSharedClean2 = memoryInfo.getOtherSharedClean(i2);
                    int otherPrivateClean2 = memoryInfo.getOtherPrivateClean(i2);
                    int otherSwappedOut2 = memoryInfo.getOtherSwappedOut(i2);
                    int otherSwappedOutPss2 = memoryInfo.getOtherSwappedOutPss(i2);
                    int otherRss2 = memoryInfo.getOtherRss(i2);
                    if (otherPss2 == 0 && otherSharedDirty2 == 0 && otherPrivateDirty2 == 0 && otherSharedClean2 == 0 && otherPrivateClean2 == 0) {
                        i2 = (memoryInfo.hasSwappedOutPss ? otherSwappedOutPss2 : otherSwappedOut2) == 0 ? i2 + 1 : 17;
                    }
                    if (z2) {
                        String otherLabel = Debug.MemoryInfo.getOtherLabel(i2);
                        Integer valueOf10 = Integer.valueOf(otherPss2);
                        Integer valueOf11 = Integer.valueOf(otherSwappablePss2);
                        Integer valueOf12 = Integer.valueOf(otherSharedDirty2);
                        Integer valueOf13 = Integer.valueOf(otherPrivateDirty2);
                        Integer valueOf14 = Integer.valueOf(otherSharedClean2);
                        Integer valueOf15 = Integer.valueOf(otherPrivateClean2);
                        if (memoryInfo.hasSwappedOutPss) {
                            otherSwappedOut2 = otherSwappedOutPss2;
                        }
                        printRow(printWriter, HEAP_FULL_COLUMN, otherLabel, valueOf10, valueOf11, valueOf12, valueOf13, valueOf14, valueOf15, Integer.valueOf(otherSwappedOut2), Integer.valueOf(otherRss2), "", "", "");
                    } else {
                        String otherLabel2 = Debug.MemoryInfo.getOtherLabel(i2);
                        Integer valueOf16 = Integer.valueOf(otherPss2);
                        Integer valueOf17 = Integer.valueOf(otherPrivateDirty2);
                        Integer valueOf18 = Integer.valueOf(otherPrivateClean2);
                        if (memoryInfo.hasSwappedOutPss) {
                            otherSwappedOut2 = otherSwappedOutPss2;
                        }
                        printRow(printWriter, HEAP_COLUMN, otherLabel2, valueOf16, valueOf17, valueOf18, Integer.valueOf(otherSwappedOut2), Integer.valueOf(otherRss2), "", "", "");
                    }
                }
            }
        }
        printWriter.println(" ");
        printWriter.println(" App Summary");
        printRow(printWriter, TWO_COUNT_COLUMN_HEADER, "", "Pss(KB)", "", "Rss(KB)");
        printRow(printWriter, TWO_COUNT_COLUMN_HEADER, "", "------", "", "------");
        printRow(printWriter, TWO_COUNT_COLUMNS, "Java Heap:", Integer.valueOf(memoryInfo.getSummaryJavaHeap()), "", Integer.valueOf(memoryInfo.getSummaryJavaHeapRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Native Heap:", Integer.valueOf(memoryInfo.getSummaryNativeHeap()), "", Integer.valueOf(memoryInfo.getSummaryNativeHeapRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Code:", Integer.valueOf(memoryInfo.getSummaryCode()), "", Integer.valueOf(memoryInfo.getSummaryCodeRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Stack:", Integer.valueOf(memoryInfo.getSummaryStack()), "", Integer.valueOf(memoryInfo.getSummaryStackRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Graphics:", Integer.valueOf(memoryInfo.getSummaryGraphics()), "", Integer.valueOf(memoryInfo.getSummaryGraphicsRss()));
        printRow(printWriter, ONE_COUNT_COLUMN, "Private Other:", Integer.valueOf(memoryInfo.getSummaryPrivateOther()));
        printRow(printWriter, ONE_COUNT_COLUMN, "System:", Integer.valueOf(memoryInfo.getSummarySystem()));
        printRow(printWriter, ONE_ALT_COUNT_COLUMN, "Unknown:", "", "", Integer.valueOf(memoryInfo.getSummaryUnknownRss()));
        printWriter.println(" ");
        if (memoryInfo.hasSwappedOutPss) {
            printRow(printWriter, THREE_COUNT_COLUMNS, "TOTAL PSS:", Integer.valueOf(memoryInfo.getSummaryTotalPss()), "TOTAL RSS:", Integer.valueOf(memoryInfo.getTotalRss()), "TOTAL SWAP PSS:", Integer.valueOf(memoryInfo.getSummaryTotalSwapPss()));
        } else {
            printRow(printWriter, THREE_COUNT_COLUMNS, "TOTAL PSS:", Integer.valueOf(memoryInfo.getSummaryTotalPss()), "TOTAL RSS:", Integer.valueOf(memoryInfo.getTotalRss()), "TOTAL SWAP (KB):", Integer.valueOf(memoryInfo.getSummaryTotalSwap()));
        }
    }

    private static void dumpMemoryInfo(ProtoOutputStream protoOutputStream, long j, String str, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, int i9) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1120986464258L, i);
        protoOutputStream.write(1120986464259L, i2);
        protoOutputStream.write(1120986464260L, i3);
        protoOutputStream.write(1120986464261L, i4);
        protoOutputStream.write(1120986464262L, i5);
        protoOutputStream.write(1120986464263L, i6);
        if (z) {
            protoOutputStream.write(1120986464265L, i8);
        } else {
            protoOutputStream.write(1120986464264L, i7);
        }
        protoOutputStream.write(1120986464266L, i9);
        protoOutputStream.end(start);
    }

    @NeverCompile
    public static void dumpMemInfoTable(ProtoOutputStream protoOutputStream, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, long j, long j2, long j3, long j4, long j5, long j6) {
        int i;
        Debug.MemoryInfo memoryInfo2;
        int i2;
        int i3;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        Debug.MemoryInfo memoryInfo3 = memoryInfo;
        if (!z2) {
            long start = protoOutputStream2.start(1146756268035L);
            dumpMemoryInfo(protoOutputStream2, 1146756268033L, "Native Heap", memoryInfo3.nativePss, memoryInfo3.nativeSwappablePss, memoryInfo3.nativeSharedDirty, memoryInfo3.nativePrivateDirty, memoryInfo3.nativeSharedClean, memoryInfo3.nativePrivateClean, memoryInfo3.hasSwappedOutPss, memoryInfo3.nativeSwappedOut, memoryInfo3.nativeSwappedOutPss, memoryInfo3.nativeRss);
            protoOutputStream2.write(1120986464258L, j);
            protoOutputStream2.write(1120986464259L, j2);
            protoOutputStream2.write(1120986464260L, j3);
            protoOutputStream2.end(start);
            long start2 = protoOutputStream2.start(1146756268036L);
            dumpMemoryInfo(protoOutputStream2, 1146756268033L, "Dalvik Heap", memoryInfo.dalvikPss, memoryInfo.dalvikSwappablePss, memoryInfo.dalvikSharedDirty, memoryInfo.dalvikPrivateDirty, memoryInfo.dalvikSharedClean, memoryInfo.dalvikPrivateClean, memoryInfo.hasSwappedOutPss, memoryInfo.dalvikSwappedOut, memoryInfo.dalvikSwappedOutPss, memoryInfo.dalvikRss);
            protoOutputStream2.write(1120986464258L, j4);
            protoOutputStream2.write(1120986464259L, j5);
            protoOutputStream2.write(1120986464260L, j6);
            protoOutputStream2.end(start2);
            Debug.MemoryInfo memoryInfo4 = memoryInfo;
            int i4 = memoryInfo4.otherPss;
            int i5 = memoryInfo4.otherSwappablePss;
            int i6 = memoryInfo4.otherSharedDirty;
            int i7 = memoryInfo4.otherPrivateDirty;
            int i8 = memoryInfo4.otherSharedClean;
            int i9 = i6;
            int i10 = i7;
            int i11 = i8;
            int i12 = memoryInfo4.otherPrivateClean;
            int i13 = memoryInfo4.otherSwappedOut;
            int i14 = memoryInfo4.otherSwappedOutPss;
            int i15 = memoryInfo4.otherRss;
            int i16 = i4;
            int i17 = 0;
            int i18 = i5;
            while (i17 < 17) {
                int otherPss = memoryInfo4.getOtherPss(i17);
                int otherSwappablePss = memoryInfo4.getOtherSwappablePss(i17);
                int otherSharedDirty = memoryInfo4.getOtherSharedDirty(i17);
                int otherPrivateDirty = memoryInfo4.getOtherPrivateDirty(i17);
                int otherSharedClean = memoryInfo4.getOtherSharedClean(i17);
                int otherPrivateClean = memoryInfo4.getOtherPrivateClean(i17);
                int otherSwappedOut = memoryInfo4.getOtherSwappedOut(i17);
                int otherSwappedOutPss = memoryInfo4.getOtherSwappedOutPss(i17);
                int otherRss = memoryInfo4.getOtherRss(i17);
                if (otherPss == 0 && otherSharedDirty == 0 && otherPrivateDirty == 0 && otherSharedClean == 0 && otherPrivateClean == 0 && otherRss == 0) {
                    if ((memoryInfo4.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut) == 0) {
                        i2 = i17;
                        i3 = i15;
                        memoryInfo2 = memoryInfo4;
                        memoryInfo4 = memoryInfo2;
                        i15 = i3;
                        i17 = i2 + 1;
                    }
                }
                int i19 = i15;
                memoryInfo2 = memoryInfo4;
                i2 = i17;
                dumpMemoryInfo(protoOutputStream, 2246267895813L, Debug.MemoryInfo.getOtherLabel(i17), otherPss, otherSwappablePss, otherSharedDirty, otherPrivateDirty, otherSharedClean, otherPrivateClean, memoryInfo4.hasSwappedOutPss, otherSwappedOut, otherSwappedOutPss, otherRss);
                i16 -= otherPss;
                i18 -= otherSwappablePss;
                i9 -= otherSharedDirty;
                i10 -= otherPrivateDirty;
                i11 -= otherSharedClean;
                i12 -= otherPrivateClean;
                i13 -= otherSwappedOut;
                i14 -= otherSwappedOutPss;
                i3 = i19 - otherRss;
                memoryInfo4 = memoryInfo2;
                i15 = i3;
                i17 = i2 + 1;
            }
            int i20 = i15;
            Debug.MemoryInfo memoryInfo5 = memoryInfo4;
            protoOutputStream2 = protoOutputStream;
            dumpMemoryInfo(protoOutputStream2, 1146756268038L, LsConstants.TAG_UNKNOWN, i16, i18, i9, i10, i11, i12, memoryInfo5.hasSwappedOutPss, i13, i14, i20);
            long start3 = protoOutputStream2.start(1146756268039L);
            dumpMemoryInfo(protoOutputStream2, 1146756268033L, "TOTAL", memoryInfo5.getTotalPss(), memoryInfo5.getTotalSwappablePss(), memoryInfo5.getTotalSharedDirty(), memoryInfo5.getTotalPrivateDirty(), memoryInfo5.getTotalSharedClean(), memoryInfo5.getTotalPrivateClean(), memoryInfo5.hasSwappedOutPss, memoryInfo5.getTotalSwappedOut(), memoryInfo5.getTotalSwappedOutPss(), memoryInfo5.getTotalRss());
            protoOutputStream2.write(1120986464258L, j + j4);
            protoOutputStream2.write(1120986464259L, j2 + j5);
            protoOutputStream2.write(1120986464260L, j3 + j6);
            protoOutputStream2.end(start3);
            if (z) {
                while (i < 32) {
                    int otherPss2 = memoryInfo.getOtherPss(i);
                    int otherSwappablePss2 = memoryInfo.getOtherSwappablePss(i);
                    int otherSharedDirty2 = memoryInfo.getOtherSharedDirty(i);
                    int otherPrivateDirty2 = memoryInfo.getOtherPrivateDirty(i);
                    int otherSharedClean2 = memoryInfo.getOtherSharedClean(i);
                    int otherPrivateClean2 = memoryInfo.getOtherPrivateClean(i);
                    int otherSwappedOut2 = memoryInfo.getOtherSwappedOut(i);
                    int otherSwappedOutPss2 = memoryInfo.getOtherSwappedOutPss(i);
                    int otherRss2 = memoryInfo.getOtherRss(i);
                    if (otherPss2 == 0 && otherSharedDirty2 == 0 && otherPrivateDirty2 == 0 && otherSharedClean2 == 0 && otherPrivateClean2 == 0) {
                        i = (memoryInfo.hasSwappedOutPss ? otherSwappedOutPss2 : otherSwappedOut2) == 0 ? i + 1 : 17;
                    }
                    dumpMemoryInfo(protoOutputStream2, 2246267895816L, Debug.MemoryInfo.getOtherLabel(i), otherPss2, otherSwappablePss2, otherSharedDirty2, otherPrivateDirty2, otherSharedClean2, otherPrivateClean2, memoryInfo.hasSwappedOutPss, otherSwappedOut2, otherSwappedOutPss2, otherRss2);
                }
            }
            memoryInfo3 = memoryInfo;
        }
        long start4 = protoOutputStream2.start(1146756268041L);
        protoOutputStream2.write(1120986464257L, memoryInfo3.getSummaryJavaHeap());
        protoOutputStream2.write(1120986464258L, memoryInfo3.getSummaryNativeHeap());
        protoOutputStream2.write(1120986464259L, memoryInfo3.getSummaryCode());
        protoOutputStream2.write(1120986464260L, memoryInfo3.getSummaryStack());
        protoOutputStream2.write(1120986464261L, memoryInfo3.getSummaryGraphics());
        protoOutputStream2.write(1120986464262L, memoryInfo3.getSummaryPrivateOther());
        protoOutputStream2.write(1120986464263L, memoryInfo3.getSummarySystem());
        if (memoryInfo3.hasSwappedOutPss) {
            protoOutputStream2.write(1120986464264L, memoryInfo3.getSummaryTotalSwapPss());
        } else {
            protoOutputStream2.write(1120986464264L, memoryInfo3.getSummaryTotalSwap());
        }
        protoOutputStream2.write(1120986464266L, memoryInfo3.getSummaryJavaHeapRss());
        protoOutputStream2.write(1120986464267L, memoryInfo3.getSummaryNativeHeapRss());
        protoOutputStream2.write(1120986464268L, memoryInfo3.getSummaryCodeRss());
        protoOutputStream2.write(1120986464269L, memoryInfo3.getSummaryStackRss());
        protoOutputStream2.write(1120986464270L, memoryInfo3.getSummaryGraphicsRss());
        protoOutputStream2.write(1120986464271L, memoryInfo3.getSummaryUnknownRss());
        protoOutputStream2.end(start4);
    }

    public void registerOnActivityPausedListener(Activity activity, OnActivityPausedListener onActivityPausedListener) {
        synchronized (this.mOnPauseListeners) {
            this.mOnPauseListeners.computeIfAbsent(activity, new Function() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ActivityThread.lambda$registerOnActivityPausedListener$2((Activity) obj);
                }
            }).add(onActivityPausedListener);
        }
    }

    static /* synthetic */ ArrayList lambda$registerOnActivityPausedListener$2(Activity activity) {
        return new ArrayList();
    }

    public void unregisterOnActivityPausedListener(Activity activity, OnActivityPausedListener onActivityPausedListener) {
        synchronized (this.mOnPauseListeners) {
            ArrayList<OnActivityPausedListener> arrayList = this.mOnPauseListeners.get(activity);
            if (arrayList != null) {
                arrayList.remove(onActivityPausedListener);
            }
        }
    }

    public final ActivityInfo resolveActivityInfo(Intent intent) {
        ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(this.mInitialApplication.getPackageManager(), 1024);
        if (resolveActivityInfo == null) {
            Instrumentation.checkStartActivityResult(-92, intent);
        }
        return resolveActivityInfo;
    }

    public final Activity startActivityNow(Activity activity, String str, Intent intent, ActivityInfo activityInfo, IBinder iBinder, Bundle bundle, Activity.NonConfigurationInstances nonConfigurationInstances, IBinder iBinder2, IBinder iBinder3) {
        ActivityClientRecord activityClientRecord = new ActivityClientRecord();
        activityClientRecord.token = iBinder;
        activityClientRecord.assistToken = iBinder2;
        activityClientRecord.shareableActivityToken = iBinder3;
        activityClientRecord.ident = 0;
        activityClientRecord.intent = intent;
        activityClientRecord.state = bundle;
        activityClientRecord.parent = activity;
        activityClientRecord.embeddedID = str;
        activityClientRecord.activityInfo = activityInfo;
        activityClientRecord.lastNonConfigurationInstances = nonConfigurationInstances;
        return performLaunchActivity(activityClientRecord, null);
    }

    @Override // android.app.ClientTransactionHandler
    public final Activity getActivity(IBinder iBinder) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            return activityClientRecord.activity;
        }
        return null;
    }

    public Activity getLastCreatedActivity() {
        if (this.mActivities.isEmpty()) {
            return null;
        }
        return this.mActivities.valueAt(r1.size() - 1).activity;
    }

    @Override // android.app.ClientTransactionHandler
    public ActivityClientRecord getActivityClient(IBinder iBinder) {
        return this.mActivities.get(iBinder);
    }

    public Configuration getConfiguration() {
        return this.mConfigurationController.getConfiguration();
    }

    public void addConfigurationChangedListener(Executor executor, Consumer<IBinder> consumer) {
        this.mConfigurationChangedListenerController.addListener(executor, consumer);
    }

    public void removeConfigurationChangedListener(Consumer<IBinder> consumer) {
        this.mConfigurationChangedListenerController.removeListener(consumer);
    }

    @Override // android.app.ClientTransactionHandler
    public void updatePendingConfiguration(Configuration configuration) {
        Configuration updatePendingConfiguration = this.mConfigurationController.updatePendingConfiguration(configuration);
        if (updatePendingConfiguration != null) {
            this.mPendingConfiguration = updatePendingConfiguration;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void updateProcessState(int i, boolean z) {
        synchronized (this.mAppThread) {
            int i2 = this.mLastProcessState;
            if (i2 == i) {
                return;
            }
            updateVmProcessState(i2, i);
            this.mLastProcessState = i;
            if (Trace.isTagEnabled(64L)) {
                Trace.instant(64L, "updateProcessState: processState=" + i);
            }
        }
    }

    private static int toVmProcessState(int i) {
        if (ActivityManager.isProcStateJankPerceptible(i)) {
            return 0;
        }
        return (Flags.jankPerceptibleNarrow() && !Flags.jankPerceptibleNarrowHoldback() && isSystem()) ? 0 : 1;
    }

    private void updateVmProcessState(int i, int i2) {
        int vmProcessState = toVmProcessState(i2);
        if (i == -1 || vmProcessState != toVmProcessState(i)) {
            VMRuntime.getRuntime().updateProcessState(vmProcessState);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void countLaunchingActivities(int i) {
        this.mNumLaunchingActivities.getAndAdd(i);
    }

    public void sendActivityResult(IBinder iBinder, String str, int i, int i2, Intent intent) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ResultInfo(str, i, i2, intent, iBinder));
        ClientTransaction clientTransaction = new ClientTransaction(this.mAppThread);
        clientTransaction.addTransactionItem(new ActivityResultItem(iBinder, arrayList));
        try {
            this.mAppThread.scheduleTransaction(clientTransaction);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.ClientTransactionHandler
    TransactionExecutor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    @Override // android.app.ClientTransactionHandler
    void sendMessage(int i, Object obj) {
        sendMessage(i, obj, 0, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, Object obj, int i2) {
        sendMessage(i, obj, i2, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, Object obj, int i2, int i3) {
        sendMessage(i, obj, i2, i3, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, Object obj, int i2, int i3, boolean z) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        if (z) {
            obtain.setAsynchronous(true);
        }
        this.mH.sendMessage(obtain);
    }

    final void scheduleContextCleanup(ContextImpl contextImpl, String str, String str2) {
        ContextCleanupInfo contextCleanupInfo = new ContextCleanupInfo();
        contextCleanupInfo.context = contextImpl;
        contextCleanupInfo.who = str;
        contextCleanupInfo.what = str2;
        sendMessage(119, contextCleanupInfo);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(18:0|1|(2:132|(1:134))(1:9)|10|(1:12)|13|(1:15)|16|(5:(2:18|(1:20)(15:130|22|23|(1:25)(1:127)|26|28|29|(1:31)|33|34|35|f7|(29:41|(1:43)|44|(1:106)(1:48)|49|50|51|52|53|54|55|56|57|58|59|60|(1:62)|63|(1:65)|66|(2:68|(1:72))(1:91)|73|(1:75)|76|(1:78)|79|(1:81)(1:90)|82|(1:84)(2:88|89))(1:107)|85|86))(1:131)|33|34|35|f7)|21|22|23|(0)(0)|26|28|29|(0)|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(22:0|1|(2:132|(1:134))(1:9)|10|(1:12)|13|(1:15)|16|(2:18|(1:20)(15:130|22|23|(1:25)(1:127)|26|28|29|(1:31)|33|34|35|f7|(29:41|(1:43)|44|(1:106)(1:48)|49|50|51|52|53|54|55|56|57|58|59|60|(1:62)|63|(1:65)|66|(2:68|(1:72))(1:91)|73|(1:75)|76|(1:78)|79|(1:81)(1:90)|82|(1:84)(2:88|89))(1:107)|85|86))(1:131)|21|22|23|(0)(0)|26|28|29|(0)|33|34|35|f7|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x026b, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x026f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0270, code lost:
    
        r27 = r4;
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00e1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x00eb, code lost:
    
        if (r29.mInstrumentation.onException(r9, r0) == false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x02c0, code lost:
    
        throw new java.lang.RuntimeException("Unable to instantiate activity " + r4 + ": " + r0.toString(), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x00e3, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x00e4, code lost:
    
        r9 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00ac A[Catch: Exception -> 0x00e3, TryCatch #7 {Exception -> 0x00e3, blocks: (B:25:0x00a3, B:26:0x00b0, B:127:0x00ac), top: B:23:0x00a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a3 A[Catch: Exception -> 0x00e3, TRY_ENTER, TryCatch #7 {Exception -> 0x00e3, blocks: (B:25:0x00a3, B:26:0x00b0, B:127:0x00ac), top: B:23:0x00a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00db A[Catch: Exception -> 0x00e1, TRY_LEAVE, TryCatch #9 {Exception -> 0x00e1, blocks: (B:29:0x00bc, B:31:0x00db), top: B:28:0x00bc }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.app.Activity performLaunchActivity(android.app.ActivityThread.ActivityClientRecord r30, android.content.Intent r31) {
        /*
            Method dump skipped, instructions count: 705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent):android.app.Activity");
    }

    @Override // android.app.ClientTransactionHandler
    public void handleStartActivity(ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        Activity activity = activityClientRecord.activity;
        if (!activityClientRecord.stopped) {
            throw new IllegalStateException("Can't start activity that is not stopped.");
        }
        AppBindData appBindData = this.mBoundApplication;
        if (appBindData != null) {
            setFlingerFlag(appBindData.processName, true);
        }
        if (activityClientRecord.activity.mFinished) {
            return;
        }
        unscheduleGcIdler();
        if (sceneTransitionInfo != null) {
            activity.mSceneTransitionInfo = sceneTransitionInfo;
        }
        activity.performStart("handleStartActivity");
        activityClientRecord.setState(2);
        if (pendingTransactionActions == null) {
            return;
        }
        if (pendingTransactionActions.shouldRestoreInstanceState()) {
            if (activityClientRecord.isPersistable()) {
                if (activityClientRecord.state != null || activityClientRecord.persistentState != null) {
                    this.mInstrumentation.callActivityOnRestoreInstanceState(activity, activityClientRecord.state, activityClientRecord.persistentState);
                }
            } else if (activityClientRecord.state != null) {
                this.mInstrumentation.callActivityOnRestoreInstanceState(activity, activityClientRecord.state);
            }
        }
        if (pendingTransactionActions.shouldCallOnPostCreate()) {
            activity.mCalled = false;
            Trace.traceBegin(32L, "onPostCreate");
            if (activityClientRecord.isPersistable()) {
                this.mInstrumentation.callActivityOnPostCreate(activity, activityClientRecord.state, activityClientRecord.persistentState);
            } else {
                this.mInstrumentation.callActivityOnPostCreate(activity, activityClientRecord.state);
            }
            Trace.traceEnd(32L);
            if (!activity.mCalled) {
                throw new SuperNotCalledException("Activity " + activityClientRecord.intent.getComponent().toShortString() + " did not call through to super.onPostCreate()");
            }
        }
        updateVisibility(activityClientRecord, true);
        this.mSomeActivitiesChanged = true;
    }

    private void checkAndBlockForNetworkAccess() {
        synchronized (this.mNetworkPolicyLock) {
            if (this.mNetworkBlockSeq != -1) {
                try {
                    ActivityManager.getService().waitForNetworkStateUpdate(this.mNetworkBlockSeq);
                    this.mNetworkBlockSeq = -1L;
                } catch (RemoteException unused) {
                }
                if (Flags.clearDnsCacheOnNetworkRulesUpdate()) {
                    InetAddress.clearDnsCache();
                }
            }
        }
    }

    private ContextImpl createBaseContextForActivity(ActivityClientRecord activityClientRecord) {
        ContextImpl createActivityContext = ContextImpl.createActivityContext(this, activityClientRecord.packageInfo, activityClientRecord.activityInfo, activityClientRecord.token, ActivityClient.getInstance().getDisplayId(activityClientRecord.token), activityClientRecord.overrideConfig);
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        String str = SystemProperties.get("debug.second-display.pkg");
        if (str != null && !str.isEmpty() && activityClientRecord.packageInfo.mPackageName.contains(str)) {
            for (int i : displayManagerGlobal.getDisplayIds()) {
                if (i != 0) {
                    return (ContextImpl) createActivityContext.createDisplayContext(displayManagerGlobal.getCompatibleDisplay(i, createActivityContext.getResources()));
                }
            }
        }
        return createActivityContext;
    }

    private ContextImpl createBaseContextForSandboxActivity(ActivityClientRecord activityClientRecord) {
        try {
            ActivityContextInfo activityContextInfo = SdkSandboxActivityAuthority.getInstance().getActivityContextInfo(activityClientRecord.intent);
            ContextImpl createActivityContext = ContextImpl.createActivityContext(this, getPackageInfo(activityContextInfo.getSdkApplicationInfo(), activityClientRecord.packageInfo.getCompatibilityInfo(), activityContextInfo.getContextFlags()), activityClientRecord.activityInfo, activityClientRecord.token, ActivityClient.getInstance().getDisplayId(activityClientRecord.token), activityClientRecord.overrideConfig);
            createActivityContext.mPackageInfo.makeApplicationInner(false, this.mInstrumentation);
            return createActivityContext;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Passed intent does not match an expected sandbox activity", e);
            return null;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "SDK customized context flag is disabled", e2);
            return null;
        } catch (Exception e3) {
            Log.e(TAG, "Failed to create context for sandbox activity", e3);
            return null;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public Activity handleLaunchActivity(ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions, int i, Intent intent) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        AppBindData appBindData = this.mBoundApplication;
        if (appBindData != null) {
            setFlingerFlag(appBindData.processName, true);
        }
        if (activityClientRecord.profilerInfo != null) {
            this.mProfiler.setProfiler(activityClientRecord.profilerInfo);
            this.mProfiler.startProfiling();
        }
        applyPendingApplicationInfoChanges(activityClientRecord.activityInfo.packageName);
        this.mConfigurationController.handleConfigurationChanged(null, null);
        updateDeviceIdForNonUIContexts(i);
        if (ThreadedRenderer.sRendererEnabled && (activityClientRecord.activityInfo.flags & 512) != 0) {
            if (CoreRune.GRAPHICS_RENDER_ENGINE_POLICY) {
                getRenderEngineType(activityClientRecord.activityInfo.packageName);
            }
            HardwareRenderer.preload();
        }
        WindowManagerGlobal.initialize();
        GraphicsEnvironment.hintActivityLaunch();
        Activity performLaunchActivity = performLaunchActivity(activityClientRecord, intent);
        if (performLaunchActivity != null) {
            activityClientRecord.createdConfig = new Configuration(this.mConfigurationController.getConfiguration());
            reportSizeConfigurations(activityClientRecord);
            if (!activityClientRecord.activity.mFinished && pendingTransactionActions != null) {
                pendingTransactionActions.setOldState(activityClientRecord.state);
                pendingTransactionActions.setRestoreInstanceState(true);
                pendingTransactionActions.setCallOnPostCreate(true);
            }
            handleActivityWindowInfoChanged(activityClientRecord);
            return performLaunchActivity;
        }
        ActivityClient.getInstance().finishActivity(activityClientRecord.token, 0, null, 0);
        return performLaunchActivity;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getRenderEngineType(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "ActivityThread"
            r1 = 1
            android.view.IGraphicsStats r2 = r5.mGraphicsStatsService     // Catch: java.lang.Exception -> L45
            if (r2 != 0) goto L1b
            java.lang.String r2 = "graphicsstats"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Exception -> L45
            if (r2 != 0) goto L15
            java.lang.String r5 = "getRenderEngineType - binder is null."
            android.util.Slog.w(r0, r5)     // Catch: java.lang.Exception -> L45
            return
        L15:
            android.view.IGraphicsStats r2 = android.view.IGraphicsStats.Stub.asInterface(r2)     // Catch: java.lang.Exception -> L45
            r5.mGraphicsStatsService = r2     // Catch: java.lang.Exception -> L45
        L1b:
            android.view.IGraphicsStats r5 = r5.mGraphicsStatsService     // Catch: java.lang.Exception -> L45
            int r5 = r5.requestRenderEngineFor(r6)     // Catch: java.lang.Exception -> L45
            if (r5 != 0) goto L26
            java.lang.String r2 = "GL"
            goto L28
        L26:
            java.lang.String r2 = "VK"
        L28:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L40
            r3.<init>()     // Catch: java.lang.Exception -> L40
            r3.append(r6)     // Catch: java.lang.Exception -> L40
            java.lang.String r6 = " will use render engine as "
            r3.append(r6)     // Catch: java.lang.Exception -> L40
            r3.append(r2)     // Catch: java.lang.Exception -> L40
            java.lang.String r6 = r3.toString()     // Catch: java.lang.Exception -> L40
            android.util.Slog.d(r0, r6)     // Catch: java.lang.Exception -> L40
            goto L5e
        L40:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
            goto L47
        L45:
            r5 = move-exception
            r6 = r1
        L47:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "mGraphicsStatsService has exception : "
            r2.<init>(r3)
            java.lang.String r5 = r5.getMessage()
            r2.append(r5)
            java.lang.String r5 = r2.toString()
            android.util.Slog.w(r0, r5)
            r5 = r6
        L5e:
            if (r5 != 0) goto L64
            android.graphics.HardwareRenderer.setRendererAsGl(r1)
            goto L68
        L64:
            r5 = 0
            android.graphics.HardwareRenderer.setRendererAsGl(r5)
        L68:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.getRenderEngineType(java.lang.String):void");
    }

    private void reportSizeConfigurations(ActivityClientRecord activityClientRecord) {
        Configuration[] sizeConfigurations;
        if (this.mActivitiesToBeDestroyed.containsKey(activityClientRecord.token) || (sizeConfigurations = activityClientRecord.activity.getResources().getSizeConfigurations()) == null) {
            return;
        }
        activityClientRecord.mSizeConfigurations = new SizeConfigurationBuckets(sizeConfigurations);
        ActivityClient.getInstance().reportSizeConfigurations(activityClientRecord.token, activityClientRecord.mSizeConfigurations);
    }

    private void deliverNewIntents(ActivityClientRecord activityClientRecord, List<ReferrerIntent> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ReferrerIntent referrerIntent = list.get(i);
            referrerIntent.setExtrasClassLoader(activityClientRecord.activity.getClassLoader());
            referrerIntent.prepareToEnterProcess(isProtectedComponent(activityClientRecord.activityInfo), activityClientRecord.activity.getAttributionSource());
            activityClientRecord.activity.mFragments.noteStateNotSaved();
            if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.contentUriPermissionApis()) {
                this.mInstrumentation.callActivityOnNewIntent(activityClientRecord.activity, referrerIntent, new ComponentCaller(activityClientRecord.token, referrerIntent.mCallerToken));
            } else {
                this.mInstrumentation.callActivityOnNewIntent(activityClientRecord.activity, referrerIntent);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleNewIntent(ActivityClientRecord activityClientRecord, List<ReferrerIntent> list) {
        checkAndBlockForNetworkAccess();
        deliverNewIntents(activityClientRecord, list);
    }

    public void handleRequestAssistContextExtras(RequestAssistContextExtras requestAssistContextExtras) {
        Uri uri;
        String contentFromDispatcher;
        boolean z = requestAssistContextExtras.requestType == 2;
        boolean z2 = requestAssistContextExtras.requestType == 3;
        if (this.mLastSessionId != requestAssistContextExtras.sessionId) {
            this.mLastSessionId = requestAssistContextExtras.sessionId;
            for (int size = this.mLastAssistStructures.size() - 1; size >= 0; size--) {
                AssistStructure assistStructure = this.mLastAssistStructures.get(size).get();
                if (assistStructure != null) {
                    assistStructure.clearSendChannel();
                }
                this.mLastAssistStructures.remove(size);
            }
        }
        Bundle bundle = new Bundle();
        AssistContent assistContent = z ? null : new AssistContent();
        long uptimeMillis = SystemClock.uptimeMillis();
        ActivityClientRecord activityClientRecord = this.mActivities.get(requestAssistContextExtras.activityToken);
        if (activityClientRecord != null) {
            if (z) {
                uri = null;
            } else {
                activityClientRecord.activity.getApplication().dispatchOnProvideAssistData(activityClientRecord.activity, bundle);
                activityClientRecord.activity.onProvideAssistData(bundle);
                uri = activityClientRecord.activity.onProvideReferrer();
            }
            if (requestAssistContextExtras.requestType == 1 || z || z2) {
                r4 = z2 ? null : new AssistStructure(activityClientRecord.activity, z, requestAssistContextExtras.flags);
                Intent intent = activityClientRecord.activity.getIntent();
                boolean z3 = activityClientRecord.window == null || (activityClientRecord.window.getAttributes().flags & 8192) == 0;
                if (intent == null || !z3) {
                    if (!z) {
                        assistContent.setDefaultIntent(new Intent());
                    }
                } else if (!z) {
                    Intent intent2 = new Intent(intent);
                    intent2.setFlags(intent2.getFlags() & (-67));
                    assistContent.setDefaultIntent(intent2);
                }
                if (!z) {
                    if (requestAssistContextExtras.fromCapture) {
                        assistContent.getExtras().putBoolean("fromCapture", true);
                    }
                    activityClientRecord.activity.onProvideAssistContent(assistContent);
                }
            }
            if (!z && activityClientRecord.activity != null && assistContent != null && assistContent.getStructuredData() == null && (contentFromDispatcher = getContentFromDispatcher(activityClientRecord)) != null) {
                assistContent.setStructuredData(contentFromDispatcher);
            }
        } else {
            uri = null;
        }
        if (!z2) {
            if (r4 == null) {
                r4 = new AssistStructure();
            }
            r4.setAcquisitionStartTime(uptimeMillis);
            r4.setAcquisitionEndTime(SystemClock.uptimeMillis());
            this.mLastAssistStructures.add(new WeakReference<>(r4));
        }
        try {
            ActivityTaskManager.getService().reportAssistContextExtras(requestAssistContextExtras.requestToken, bundle, r4, assistContent, uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0063, code lost:
    
        r6.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getContentFileName(java.lang.String r7) {
        /*
            r6 = this;
            android.app.ActivityThread r6 = currentActivityThread()
            android.app.Application r6 = r6.getApplication()
            android.content.ContentResolver r0 = r6.getContentResolver()
            android.net.Uri r1 = android.net.Uri.parse(r7)
            r4 = 0
            r5 = 0
            r2 = 0
            r3 = 0
            android.database.Cursor r6 = r0.query(r1, r2, r3, r4, r5)
            if (r6 == 0) goto L61
            int r0 = r6.getCount()     // Catch: java.lang.Throwable -> L53
            if (r0 == 0) goto L61
            boolean r0 = r6.moveToFirst()     // Catch: java.lang.Throwable -> L53
            if (r0 != 0) goto L27
            goto L61
        L27:
            java.lang.String r0 = "_data"
            int r0 = r6.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L53
            if (r0 >= 0) goto L35
            if (r6 == 0) goto L66
            r6.close()
            return r7
        L35:
            java.lang.String r0 = r6.getString(r0)     // Catch: java.lang.Throwable -> L53
            if (r0 != 0) goto L41
            if (r6 == 0) goto L66
            r6.close()
            return r7
        L41:
            java.lang.String r7 = "/"
            int r7 = r0.lastIndexOf(r7)     // Catch: java.lang.Throwable -> L53
            int r7 = r7 + 1
            java.lang.String r7 = r0.substring(r7)     // Catch: java.lang.Throwable -> L53
            if (r6 == 0) goto L52
            r6.close()
        L52:
            return r7
        L53:
            r0 = move-exception
            r7 = r0
            if (r6 == 0) goto L60
            r6.close()     // Catch: java.lang.Throwable -> L5b
            goto L60
        L5b:
            r0 = move-exception
            r6 = r0
            r7.addSuppressed(r6)
        L60:
            throw r7
        L61:
            if (r6 == 0) goto L66
            r6.close()
        L66:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.getContentFileName(java.lang.String):java.lang.String");
    }

    private String getContentFromDispatcher(ActivityClientRecord activityClientRecord) {
        new String[]{"", "", ""};
        try {
            String[] contentByTask = ActivityManager.getService().getContentByTask(activityClientRecord.activity.getTaskId());
            if (contentByTask == null || contentByTask[2] == null || activityClientRecord.activity.getApplicationInfo() == null || !contentByTask[2].equals(activityClientRecord.activity.getApplicationInfo().packageName)) {
                return null;
            }
            contentByTask[1] = getContentFileName(contentByTask[0]);
            return new JSONObject().put("file_metadata", new JSONObject().put("file_uri", contentByTask[0]).put("mime_type", "application/pdf").put("file_name", contentByTask[1]).put("is_work_profile", isWorkProfile())).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isWorkProfile() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) currentActivityThread().getApplication().getSystemService(Context.DEVICE_POLICY_SERVICE);
        List<ComponentName> activeAdmins = devicePolicyManager.getActiveAdmins();
        if (activeAdmins == null) {
            return false;
        }
        Iterator<ComponentName> it = activeAdmins.iterator();
        while (it.hasNext()) {
            if (devicePolicyManager.isProfileOwnerApp(it.next().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, CancellationSignal cancellationSignal, final RemoteCallback remoteCallback, int i) {
        final ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null) {
            Log.w(TAG, "requestDirectActions(): no activity for " + iBinder);
            remoteCallback.sendResult(null);
            return;
        }
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState < 2) {
            if (i > 0) {
                this.mH.sendMessageDelayed(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda7
                    @Override // com.android.internal.util.function.HexConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                        ((ActivityThread) obj).handleRequestDirectActions((IBinder) obj2, (IVoiceInteractor) obj3, (CancellationSignal) obj4, (RemoteCallback) obj5, ((Integer) obj6).intValue());
                    }
                }, this, iBinder, iVoiceInteractor, cancellationSignal, remoteCallback, Integer.valueOf(i - 1)), 200L);
                return;
            }
            Log.w(TAG, "requestDirectActions(" + activityClientRecord + "): wrong lifecycle: " + lifecycleState);
            remoteCallback.sendResult(null);
            return;
        }
        if (lifecycleState >= 5) {
            Log.w(TAG, "requestDirectActions(" + activityClientRecord + "): wrong lifecycle: " + lifecycleState);
            remoteCallback.sendResult(null);
            return;
        }
        if (activityClientRecord.activity.mVoiceInteractor == null || activityClientRecord.activity.mVoiceInteractor.mInteractor.asBinder() != iVoiceInteractor.asBinder()) {
            if (activityClientRecord.activity.mVoiceInteractor != null) {
                activityClientRecord.activity.mVoiceInteractor.destroy();
            }
            activityClientRecord.activity.mVoiceInteractor = new VoiceInteractor(iVoiceInteractor, activityClientRecord.activity, activityClientRecord.activity, Looper.myLooper());
        }
        activityClientRecord.activity.onGetDirectActions(cancellationSignal, new Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityThread.lambda$handleRequestDirectActions$3(ActivityThread.ActivityClientRecord.this, remoteCallback, (List) obj);
            }
        });
    }

    static /* synthetic */ void lambda$handleRequestDirectActions$3(ActivityClientRecord activityClientRecord, RemoteCallback remoteCallback, List list) {
        Objects.requireNonNull(list);
        Preconditions.checkCollectionElementsNotNull(list, Slice.HINT_ACTIONS);
        if (!list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ((DirectAction) list.get(i)).setSource(activityClientRecord.activity.getTaskId(), activityClientRecord.activity.getAssistToken());
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(DirectAction.KEY_ACTIONS_LIST, new ParceledListSlice(list));
            remoteCallback.sendResult(bundle);
            return;
        }
        remoteCallback.sendResult(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePerformDirectAction(IBinder iBinder, String str, Bundle bundle, CancellationSignal cancellationSignal, final RemoteCallback remoteCallback) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            int lifecycleState = activityClientRecord.getLifecycleState();
            if (lifecycleState < 2 || lifecycleState >= 5) {
                remoteCallback.sendResult(null);
                return;
            }
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Activity activity = activityClientRecord.activity;
            Objects.requireNonNull(remoteCallback);
            activity.onPerformDirectAction(str, bundle, cancellationSignal, new Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteCallback.this.sendResult((Bundle) obj);
                }
            });
            return;
        }
        remoteCallback.sendResult(null);
    }

    public void handleTranslucentConversionComplete(IBinder iBinder, boolean z) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.activity.onTranslucentConversionComplete(z);
        }
    }

    public void onNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.activity.onNewSceneTransitionInfo(sceneTransitionInfo);
        }
    }

    public void handleInstallProvider(ProviderInfo providerInfo) {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            installContentProviders(this.mInitialApplication, Arrays.asList(providerInfo));
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEnterAnimationComplete(IBinder iBinder) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.activity.dispatchEnterAnimationComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartBinderTracking() {
        if (Binder.isSystemServerBinderTrackerEnabled) {
            Binder.getTransactionTracker().clearTraces();
        }
        Binder.enableStackTracking();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            Binder.disableStackTracking();
            Binder.getTransactionTracker().writeTracesToFile(parcelFileDescriptor);
        } finally {
            IoUtils.closeQuietly(parcelFileDescriptor);
            Binder.getTransactionTracker().clearTraces();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) {
        try {
            Binder.getTransactionTracker().setBinderInfo(i, i2, str, str2);
            Binder.disableStackTracking();
            Binder.getTransactionTracker().writeTracesToFile(parcelFileDescriptor);
        } finally {
            IoUtils.closeQuietly(parcelFileDescriptor);
            Binder.getTransactionTracker().clearTraces();
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePictureInPictureRequested(ActivityClientRecord activityClientRecord) {
        if (activityClientRecord.activity.onPictureInPictureRequested()) {
            return;
        }
        schedulePauseWithUserLeaveHintAndReturnToCurrentState(activityClientRecord);
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePictureInPictureStateChanged(ActivityClientRecord activityClientRecord, PictureInPictureUiState pictureInPictureUiState) {
        activityClientRecord.activity.onPictureInPictureUiStateChanged(pictureInPictureUiState);
    }

    public void registerSplashScreenManager(SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal) {
        synchronized (this) {
            this.mSplashScreenGlobal = splashScreenManagerGlobal;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public boolean isHandleSplashScreenExit(IBinder iBinder) {
        boolean z;
        synchronized (this) {
            SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal = this.mSplashScreenGlobal;
            z = splashScreenManagerGlobal != null && splashScreenManagerGlobal.containsExitListener(iBinder);
        }
        return z;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleAttachSplashScreenView(ActivityClientRecord activityClientRecord, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, SurfaceControl surfaceControl) {
        DecorView decorView = activityClientRecord.window != null ? (DecorView) activityClientRecord.window.peekDecorView() : null;
        if (splashScreenViewParcelable != null && decorView != null) {
            createSplashScreen(activityClientRecord, decorView, splashScreenViewParcelable, surfaceControl);
        } else {
            Slog.e(TAG, "handleAttachSplashScreenView failed, unable to attach");
        }
    }

    private void createSplashScreen(ActivityClientRecord activityClientRecord, DecorView decorView, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, SurfaceControl surfaceControl) {
        SplashScreenView build = new SplashScreenView.Builder(activityClientRecord.activity).createFromParcel(splashScreenViewParcelable).build();
        build.attachHostWindow(activityClientRecord.window);
        decorView.addView(build);
        build.requestLayout();
        build.getViewTreeObserver().addOnPreDrawListener(new AnonymousClass2(build, activityClientRecord, decorView, surfaceControl));
    }

    /* renamed from: android.app.ActivityThread$2, reason: invalid class name */
    class AnonymousClass2 implements ViewTreeObserver.OnPreDrawListener {
        private boolean mHandled = false;
        final /* synthetic */ DecorView val$decorView;
        final /* synthetic */ ActivityClientRecord val$r;
        final /* synthetic */ SurfaceControl val$startingWindowLeash;
        final /* synthetic */ SplashScreenView val$view;

        AnonymousClass2(SplashScreenView splashScreenView, ActivityClientRecord activityClientRecord, DecorView decorView, SurfaceControl surfaceControl) {
            this.val$view = splashScreenView;
            this.val$r = activityClientRecord;
            this.val$decorView = decorView;
            this.val$startingWindowLeash = surfaceControl;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (this.mHandled) {
                return true;
            }
            this.mHandled = true;
            ActivityThread.this.syncTransferSplashscreenViewTransaction(this.val$view, this.val$r.token, this.val$decorView, this.val$startingWindowLeash);
            final SplashScreenView splashScreenView = this.val$view;
            splashScreenView.post(new Runnable() { // from class: android.app.ActivityThread$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityThread.AnonymousClass2.this.lambda$onPreDraw$0(splashScreenView);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$0(SplashScreenView splashScreenView) {
            splashScreenView.getViewTreeObserver().removeOnPreDrawListener(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportSplashscreenViewShown, reason: merged with bridge method [inline-methods] */
    public void lambda$syncTransferSplashscreenViewTransaction$4(IBinder iBinder, SplashScreenView splashScreenView) {
        Trace.instant(8L, "reportSplashscreenViewShown");
        ActivityClient.getInstance().reportSplashScreenAttached(iBinder);
        synchronized (this) {
            SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal = this.mSplashScreenGlobal;
            if (splashScreenManagerGlobal != null) {
                splashScreenManagerGlobal.handOverSplashScreenView(iBinder, splashScreenView);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncTransferSplashscreenViewTransaction(final SplashScreenView splashScreenView, final IBinder iBinder, View view, SurfaceControl surfaceControl) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.hide(surfaceControl);
        surfaceControl.release();
        splashScreenView.syncTransferSurfaceOnDraw();
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.useRtFrameCallbackForSplashScreenTransfer() && view.isHardwareAccelerated()) {
            view.getViewRootImpl().registerRtFrameCallback(new AnonymousClass3(transaction, view, iBinder, splashScreenView));
            return;
        }
        Trace.instant(8L, "transferSplashscreenView_software");
        view.getViewRootImpl().applyTransactionOnDraw(transaction);
        view.postOnAnimation(new Runnable() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityThread.this.lambda$syncTransferSplashscreenViewTransaction$4(iBinder, splashScreenView);
            }
        });
    }

    /* renamed from: android.app.ActivityThread$3, reason: invalid class name */
    class AnonymousClass3 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ View val$decorView;
        final /* synthetic */ IBinder val$token;
        final /* synthetic */ SurfaceControl.Transaction val$transaction;
        final /* synthetic */ SplashScreenView val$view;

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }

        AnonymousClass3(SurfaceControl.Transaction transaction, View view, IBinder iBinder, SplashScreenView splashScreenView) {
            this.val$transaction = transaction;
            this.val$decorView = view;
            this.val$token = iBinder;
            this.val$view = splashScreenView;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int i, long j) {
            final SurfaceControl.Transaction transaction = this.val$transaction;
            final View view = this.val$decorView;
            final IBinder iBinder = this.val$token;
            final SplashScreenView splashScreenView = this.val$view;
            return new HardwareRenderer.FrameCommitCallback() { // from class: android.app.ActivityThread$3$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    ActivityThread.AnonymousClass3.this.lambda$onFrameDraw$1(transaction, view, iBinder, splashScreenView, z);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$1(SurfaceControl.Transaction transaction, View view, final IBinder iBinder, final SplashScreenView splashScreenView, boolean z) {
            Trace.instant(8L, "transferSplashscreenView");
            transaction.apply();
            view.postOnAnimation(new Runnable() { // from class: android.app.ActivityThread$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityThread.AnonymousClass3.this.lambda$onFrameDraw$0(iBinder, splashScreenView);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDraw$0(IBinder iBinder, SplashScreenView splashScreenView) {
            ActivityThread.this.lambda$syncTransferSplashscreenViewTransaction$4(iBinder, splashScreenView);
        }
    }

    private void schedulePauseWithUserLeaveHintAndReturnToCurrentState(ActivityClientRecord activityClientRecord) {
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState == 3 || lifecycleState == 4) {
            if (lifecycleState == 3) {
                schedulePauseWithUserLeavingHint(activityClientRecord);
                scheduleResume(activityClientRecord);
            } else {
                if (lifecycleState != 4) {
                    return;
                }
                scheduleResume(activityClientRecord);
                schedulePauseWithUserLeavingHint(activityClientRecord);
            }
        }
    }

    private void schedulePauseWithUserLeavingHint(ActivityClientRecord activityClientRecord) {
        ClientTransaction clientTransaction = new ClientTransaction(this.mAppThread);
        clientTransaction.addTransactionItem(new PauseActivityItem(activityClientRecord.token, activityClientRecord.activity.isFinishing(), true, false, false));
        executeTransaction(clientTransaction);
    }

    private void scheduleResume(ActivityClientRecord activityClientRecord) {
        ClientTransaction clientTransaction = new ClientTransaction(this.mAppThread);
        clientTransaction.addTransactionItem(new ResumeActivityItem(activityClientRecord.token, false, false));
        executeTransaction(clientTransaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.voiceInteractor = iVoiceInteractor;
            activityClientRecord.activity.setVoiceInteractor(iVoiceInteractor);
            if (iVoiceInteractor == null) {
                activityClientRecord.activity.onLocalVoiceInteractionStopped();
            } else {
                activityClientRecord.activity.onLocalVoiceInteractionStarted();
            }
        }
    }

    private static boolean attemptAttachAgent(String str, ClassLoader classLoader) {
        try {
            VMDebug.attachAgent(str, classLoader);
            return true;
        } catch (IOException unused) {
            Slog.e(TAG, "Attaching agent with " + classLoader + " failed: " + str);
            return false;
        }
    }

    static void handleAttachAgent(String str, LoadedApk loadedApk) {
        ClassLoader classLoader = loadedApk != null ? loadedApk.getClassLoader() : null;
        if (attemptAttachAgent(str, classLoader) || classLoader == null) {
            return;
        }
        attemptAttachAgent(str, null);
    }

    static void handleAttachStartupAgents(String str) {
        try {
            Path path = ContextImpl.getCodeCacheDirBeforeBind(new File(str)).toPath();
            if (Files.exists(path, new LinkOption[0])) {
                Path resolve = path.resolve("startup_agents");
                if (Files.exists(resolve, new LinkOption[0])) {
                    DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(resolve);
                    try {
                        Iterator<Path> it = newDirectoryStream.iterator();
                        while (it.hasNext()) {
                            handleAttachAgent(it.next().toAbsolutePath().toString() + "=" + str, null);
                        }
                        if (newDirectoryStream != null) {
                            newDirectoryStream.close();
                        }
                    } finally {
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null) {
            Log.w(TAG, "updateUiTranslationState(): no activity for " + iBinder);
            return;
        }
        activityClientRecord.activity.updateUiTranslationState(i, translationSpec, translationSpec2, list, uiTranslationSpec);
    }

    public static Intent getIntentBeingBroadcast() {
        return sCurrentBroadcastIntent.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceiver(ReceiverData receiverData) {
        unscheduleGcIdler();
        String className = receiverData.intent.getComponent().getClassName();
        LoadedApk packageInfoNoCheck = getPackageInfoNoCheck(receiverData.info.applicationInfo);
        IActivityManager service = ActivityManager.getService();
        try {
            ContextImpl contextImpl = (ContextImpl) packageInfoNoCheck.makeApplicationInner(false, this.mInstrumentation).getBaseContext();
            if (receiverData.info.splitName != null) {
                contextImpl = (ContextImpl) contextImpl.createContextForSplit(receiverData.info.splitName);
            }
            if (receiverData.info.attributionTags != null && receiverData.info.attributionTags.length > 0) {
                contextImpl = (ContextImpl) contextImpl.createAttributionContext(receiverData.info.attributionTags[0]);
            }
            ContextImpl contextImpl2 = (ContextImpl) createDisplayContextIfNeeded(contextImpl, receiverData);
            ClassLoader classLoader = contextImpl2.getClassLoader();
            receiverData.intent.setExtrasClassLoader(classLoader);
            receiverData.intent.prepareToEnterProcess(isProtectedComponent(receiverData.info) || isProtectedBroadcast(receiverData.intent), contextImpl2.getAttributionSource());
            receiverData.setExtrasClassLoader(classLoader);
            BroadcastReceiver instantiateReceiver = packageInfoNoCheck.getAppFactory().instantiateReceiver(classLoader, receiverData.info.name, receiverData.intent);
            try {
                try {
                    ThreadLocal<Intent> threadLocal = sCurrentBroadcastIntent;
                    threadLocal.set(receiverData.intent);
                    instantiateReceiver.setPendingResult(receiverData);
                    instantiateReceiver.onReceive(contextImpl2.getReceiverRestrictedContext(), receiverData.intent);
                    threadLocal.set(null);
                } catch (Exception e) {
                    receiverData.sendFinished(service);
                    if (!this.mInstrumentation.onException(instantiateReceiver, e)) {
                        throw new RuntimeException("Unable to start receiver " + className + ": " + e.toString(), e);
                    }
                    sCurrentBroadcastIntent.set(null);
                }
                if (instantiateReceiver.getPendingResult() != null) {
                    receiverData.finish();
                }
            } catch (Throwable th) {
                sCurrentBroadcastIntent.set(null);
                throw th;
            }
        } catch (Exception e2) {
            receiverData.sendFinished(service);
            throw new RuntimeException("Unable to instantiate receiver " + className + ": " + e2.toString(), e2);
        }
    }

    public Context createDisplayContextIfNeeded(Context context, ReceiverData receiverData) {
        ActivityOptions activityOptions;
        int launchDisplayId;
        DisplayManager displayManager;
        if (!com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.supportWidgetIntentsOnConnectedDisplay() || (activityOptions = receiverData.mOptions) == null || (launchDisplayId = activityOptions.getLaunchDisplayId()) == -1 || (displayManager = (DisplayManager) context.getSystemService(DisplayManager.class)) == null) {
            return context;
        }
        Display display = displayManager.getDisplay(launchDisplayId);
        if (display == null) {
            Slog.w(TAG, "Unable to create a display context for nonexistent display " + launchDisplayId);
            return context;
        }
        return context.createDisplayContext(display);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreateBackupAgent(CreateBackupAgentData createBackupAgentData) {
        IBinder iBinder;
        try {
            if (getPackageManager().getPackageInfo(createBackupAgentData.appInfo.packageName, 0L, UserHandle.myUserId()).applicationInfo.uid != Process.myUid()) {
                Slog.w(TAG, "Asked to instantiate non-matching package " + createBackupAgentData.appInfo.packageName);
                return;
            }
            unscheduleGcIdler();
            LoadedApk packageInfoNoCheck = getPackageInfoNoCheck(createBackupAgentData.appInfo);
            String str = packageInfoNoCheck.mPackageName;
            if (str == null) {
                Slog.d(TAG, "Asked to create backup agent for nonexistent package");
                return;
            }
            String backupAgentName = getBackupAgentName(createBackupAgentData);
            try {
                Map backupAgentsForUser = getBackupAgentsForUser(createBackupAgentData.userId);
                BackupAgent backupAgent = (BackupAgent) backupAgentsForUser.get(str);
                if (backupAgent != null) {
                    iBinder = backupAgent.onBind();
                } else {
                    try {
                        BackupAgent backupAgent2 = (BackupAgent) packageInfoNoCheck.getClassLoader().loadClass(backupAgentName).newInstance();
                        ContextImpl createAppContext = ContextImpl.createAppContext(this, packageInfoNoCheck);
                        createAppContext.setOuterContext(backupAgent2);
                        backupAgent2.attach(createAppContext);
                        backupAgent2.onCreate(UserHandle.of(createBackupAgentData.userId), createBackupAgentData.backupDestination, getOperationTypeFromBackupMode(createBackupAgentData.backupMode));
                        iBinder = backupAgent2.onBind();
                        try {
                            backupAgentsForUser.put(str, backupAgent2);
                        } catch (Exception e) {
                            e = e;
                            Slog.e(TAG, "Agent threw during creation: " + e);
                            if (createBackupAgentData.backupMode != 2 && createBackupAgentData.backupMode != 3) {
                                throw e;
                            }
                            ActivityManager.getService().backupAgentCreated(str, iBinder, createBackupAgentData.userId);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        iBinder = null;
                    }
                }
                try {
                    ActivityManager.getService().backupAgentCreated(str, iBinder, createBackupAgentData.userId);
                } catch (RemoteException e3) {
                    throw e3.rethrowFromSystemServer();
                }
            } catch (Exception e4) {
                throw new RuntimeException("Unable to create BackupAgent " + backupAgentName + ": " + e4.toString(), e4);
            }
        } catch (RemoteException e5) {
            throw e5.rethrowFromSystemServer();
        }
    }

    private static int getOperationTypeFromBackupMode(int i) {
        if (i == 0 || i == 1) {
            return 0;
        }
        if (i == 2 || i == 3) {
            return 1;
        }
        Slog.w(TAG, "Invalid backup mode when initialising BackupAgent: " + i);
        return -1;
    }

    private String getBackupAgentName(CreateBackupAgentData createBackupAgentData) {
        String str = createBackupAgentData.appInfo.backupAgentName;
        return str == null ? (createBackupAgentData.backupMode == 1 || createBackupAgentData.backupMode == 3) ? DEFAULT_FULL_BACKUP_AGENT : str : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroyBackupAgent(CreateBackupAgentData createBackupAgentData) {
        String str = getPackageInfoNoCheck(createBackupAgentData.appInfo).mPackageName;
        ArrayMap<String, BackupAgent> backupAgentsForUser = getBackupAgentsForUser(createBackupAgentData.userId);
        BackupAgent backupAgent = backupAgentsForUser.get(str);
        if (backupAgent != null) {
            try {
                backupAgent.onDestroy();
            } catch (Exception e) {
                Slog.w(TAG, "Exception thrown in onDestroy by backup agent of " + createBackupAgentData.appInfo);
                e.printStackTrace();
            }
            backupAgentsForUser.remove(str);
            return;
        }
        Slog.w(TAG, "Attempt to destroy unknown backup agent " + createBackupAgentData);
    }

    private ArrayMap<String, BackupAgent> getBackupAgentsForUser(int i) {
        ArrayMap<String, BackupAgent> arrayMap = this.mBackupAgentsByUser.get(i);
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap<String, BackupAgent> arrayMap2 = new ArrayMap<>();
        this.mBackupAgentsByUser.put(i, arrayMap2);
        return arrayMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreateService(CreateServiceData createServiceData) {
        ActivityThread activityThread;
        ClassLoader classLoader;
        unscheduleGcIdler();
        LoadedApk packageInfoNoCheck = getPackageInfoNoCheck(createServiceData.info.applicationInfo);
        Service service = null;
        try {
            Application makeApplicationInner = packageInfoNoCheck.makeApplicationInner(false, this.mInstrumentation);
            if (createServiceData.info.splitName != null) {
                classLoader = packageInfoNoCheck.getSplitClassLoader(createServiceData.info.splitName);
            } else {
                classLoader = packageInfoNoCheck.getClassLoader();
            }
            Service instantiateService = packageInfoNoCheck.getAppFactory().instantiateService(classLoader, createServiceData.info.name, createServiceData.intent);
            try {
                ContextImpl impl = ContextImpl.getImpl(instantiateService.createServiceBaseContext(this, packageInfoNoCheck));
                if (createServiceData.info.splitName != null) {
                    impl = (ContextImpl) impl.createContextForSplit(createServiceData.info.splitName);
                }
                if (createServiceData.info.attributionTags != null && createServiceData.info.attributionTags.length > 0) {
                    impl = (ContextImpl) impl.createAttributionContext(createServiceData.info.attributionTags[0]);
                }
                ContextImpl contextImpl = impl;
                contextImpl.getResources().addLoaders((ResourcesLoader[]) makeApplicationInner.getResources().getLoaders().toArray(new ResourcesLoader[0]));
                contextImpl.setOuterContext(instantiateService);
                activityThread = this;
                try {
                    instantiateService.attach(contextImpl, activityThread, createServiceData.info.name, createServiceData.token, makeApplicationInner, ActivityManager.getService());
                    if (!instantiateService.isUiContext()) {
                        int i = activityThread.mLastReportedDeviceId;
                        if (i == 0) {
                            instantiateService.updateDeviceId(i);
                        } else {
                            VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) contextImpl.getSystemService(VirtualDeviceManager.class);
                            if (virtualDeviceManager != null && virtualDeviceManager.isValidVirtualDeviceId(activityThread.mLastReportedDeviceId)) {
                                instantiateService.updateDeviceId(activityThread.mLastReportedDeviceId);
                            }
                        }
                    }
                    instantiateService.onCreate();
                    activityThread.mServicesData.put(createServiceData.token, createServiceData);
                    activityThread.mServices.put(createServiceData.token, instantiateService);
                    try {
                        ActivityManager.getService().serviceDoneExecuting(createServiceData.token, 0, 0, 0, null);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Exception e2) {
                    e = e2;
                    service = instantiateService;
                    if (activityThread.mInstrumentation.onException(service, e)) {
                        return;
                    }
                    throw new RuntimeException("Unable to create service " + createServiceData.info.name + ": " + e.toString(), e);
                }
            } catch (Exception e3) {
                e = e3;
                activityThread = this;
            }
        } catch (Exception e4) {
            e = e4;
            activityThread = this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBindService(BindServiceData bindServiceData) {
        CreateServiceData createServiceData = this.mServicesData.get(bindServiceData.token);
        Service service = this.mServices.get(bindServiceData.token);
        if (service != null) {
            try {
                bindServiceData.intent.setExtrasClassLoader(service.getClassLoader());
                bindServiceData.intent.prepareToEnterProcess(isProtectedComponent(createServiceData.info), service.getAttributionSource());
                try {
                    if (!bindServiceData.rebind) {
                        ActivityManager.getService().publishService(bindServiceData.token, bindServiceData.intent, service.onBind(bindServiceData.intent));
                    } else {
                        service.onRebind(bindServiceData.intent);
                        ActivityManager.getService().serviceDoneExecuting(bindServiceData.token, 3, 0, 0, bindServiceData.intent);
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Exception e2) {
                if (this.mInstrumentation.onException(service, e2)) {
                    return;
                }
                throw new RuntimeException("Unable to bind to service " + service + " with " + bindServiceData.intent + ": " + e2.toString(), e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnbindService(BindServiceData bindServiceData) {
        CreateServiceData createServiceData = this.mServicesData.get(bindServiceData.token);
        Service service = this.mServices.get(bindServiceData.token);
        if (service != null) {
            try {
                bindServiceData.intent.setExtrasClassLoader(service.getClassLoader());
                bindServiceData.intent.prepareToEnterProcess(isProtectedComponent(createServiceData.info), service.getAttributionSource());
                try {
                    if (service.onUnbind(bindServiceData.intent)) {
                        ActivityManager.getService().unbindFinished(bindServiceData.token, bindServiceData.intent);
                    } else {
                        ActivityManager.getService().serviceDoneExecuting(bindServiceData.token, 4, 0, 0, bindServiceData.intent);
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Exception e2) {
                if (this.mInstrumentation.onException(service, e2)) {
                    return;
                }
                throw new RuntimeException("Unable to unbind to service " + service + " with " + bindServiceData.intent + ": " + e2.toString(), e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpGfxInfo(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            ThreadedRenderer.handleDumpGfxInfo(dumpComponentInfo.fd.getFileDescriptor(), dumpComponentInfo.args);
        } catch (Exception e) {
            Log.w(TAG, "Caught exception from dumpGfxInfo()", e);
        } finally {
            IoUtils.closeQuietly(dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpService(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            Service service = this.mServices.get(dumpComponentInfo.token);
            if (service != null) {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpComponentInfo.fd.getFileDescriptor()));
                service.dump(dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
                fastPrintWriter.flush();
            }
        } finally {
            IoUtils.closeQuietly(dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpResources(DumpResourcesData dumpResourcesData) {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpResourcesData.fd.getFileDescriptor()));
            Resources.dumpHistory(fastPrintWriter, "");
            fastPrintWriter.flush();
            ResourceTimer.dumpTimers(dumpResourcesData.fd.getFileDescriptor(), "-refresh");
            if (dumpResourcesData.finishCallback != null) {
                dumpResourcesData.finishCallback.sendResult(null);
            }
        } finally {
            IoUtils.closeQuietly(dumpResourcesData.fd);
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpActivity(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            ActivityClientRecord activityClientRecord = this.mActivities.get(dumpComponentInfo.token);
            if (activityClientRecord != null && activityClientRecord.activity != null) {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpComponentInfo.fd.getFileDescriptor()));
                activityClientRecord.activity.dumpInternal(dumpComponentInfo.prefix, dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
                fastPrintWriter.flush();
            }
        } finally {
            IoUtils.closeQuietly(dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpProvider(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            ProviderClientRecord providerClientRecord = this.mLocalProviders.get(dumpComponentInfo.token);
            if (providerClientRecord != null && providerClientRecord.mLocalProvider != null) {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpComponentInfo.fd.getFileDescriptor()));
                providerClientRecord.mLocalProvider.dump(dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
                fastPrintWriter.flush();
            }
        } finally {
            IoUtils.closeQuietly(dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleServiceArgs(ServiceArgsData serviceArgsData) {
        int i;
        CreateServiceData createServiceData = this.mServicesData.get(serviceArgsData.token);
        Service service = this.mServices.get(serviceArgsData.token);
        if (service != null) {
            try {
                if (serviceArgsData.args != null) {
                    serviceArgsData.args.setExtrasClassLoader(service.getClassLoader());
                    serviceArgsData.args.prepareToEnterProcess(isProtectedComponent(createServiceData.info), service.getAttributionSource());
                }
                if (!serviceArgsData.taskRemoved) {
                    i = service.onStartCommand(serviceArgsData.args, serviceArgsData.flags, serviceArgsData.startId);
                } else {
                    service.onTaskRemoved(serviceArgsData.args);
                    i = 1000;
                }
                int i2 = i;
                QueuedWork.waitToFinish();
                try {
                    ActivityManager.getService().serviceDoneExecuting(serviceArgsData.token, 1, serviceArgsData.startId, i2, null);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Exception e2) {
                if (this.mInstrumentation.onException(service, e2)) {
                    return;
                }
                throw new RuntimeException("Unable to start service " + service + " with " + serviceArgsData.args + ": " + e2.toString(), e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopService(IBinder iBinder) {
        IBinder iBinder2;
        this.mServicesData.remove(iBinder);
        Service remove = this.mServices.remove(iBinder);
        if (remove != null) {
            try {
                remove.onDestroy();
                remove.detachAndCleanUp();
                Context baseContext = remove.getBaseContext();
                if (baseContext instanceof ContextImpl) {
                    ((ContextImpl) baseContext).scheduleFinalCleanup(remove.getClassName(), "Service");
                }
                QueuedWork.waitToFinish();
                try {
                    iBinder2 = iBinder;
                } catch (RemoteException e) {
                    e = e;
                    iBinder2 = iBinder;
                }
                try {
                    try {
                        ActivityManager.getService().serviceDoneExecuting(iBinder2, 2, 0, 0, null);
                    } catch (Exception e2) {
                        e = e2;
                        if (!this.mInstrumentation.onException(remove, e)) {
                            throw new RuntimeException("Unable to stop service " + remove + ": " + e.toString(), e);
                        }
                        Slog.i(TAG, "handleStopService: exception for " + iBinder2, e);
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    throw e.rethrowFromSystemServer();
                }
            } catch (Exception e4) {
                e = e4;
                iBinder2 = iBinder;
            }
        } else {
            Slog.i(TAG, "handleStopService: token=" + iBinder + " not found.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeoutService(IBinder iBinder, int i) {
        Service service = this.mServices.get(iBinder);
        if (service != null) {
            try {
                service.callOnTimeout(i);
                return;
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(service, e)) {
                    throw new RuntimeException("Unable to call onTimeout on service " + service + ": " + e.toString(), e);
                }
                Slog.i(TAG, "handleTimeoutService: exception for " + iBinder, e);
                return;
            }
        }
        Slog.wtf(TAG, "handleTimeoutService: token=" + iBinder + " not found.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeoutServiceForType(IBinder iBinder, int i, int i2) {
        Service service = this.mServices.get(iBinder);
        if (service != null) {
            try {
                service.callOnTimeLimitExceeded(i, i2);
                return;
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(service, e)) {
                    throw new RuntimeException("Unable to call onTimeLimitExceeded on service " + service + ": " + e, e);
                }
                Slog.i(TAG, "handleTimeoutServiceForType: exception for " + iBinder, e);
                return;
            }
        }
        Slog.wtf(TAG, "handleTimeoutServiceForType: token=" + iBinder + " not found.");
    }

    public boolean performResumeActivity(ActivityClientRecord activityClientRecord, boolean z, String str) {
        if (activityClientRecord.activity.mFinished) {
            return false;
        }
        if (activityClientRecord.getLifecycleState() == 3) {
            if (!z) {
                IllegalStateException illegalStateException = new IllegalStateException("Trying to resume activity which is already resumed");
                Slog.e(TAG, illegalStateException.getMessage(), illegalStateException);
                Slog.e(TAG, activityClientRecord.getStateString());
            }
            return false;
        }
        if (z) {
            activityClientRecord.hideForNow = false;
            activityClientRecord.activity.mStartedActivity = false;
        }
        try {
            activityClientRecord.activity.onStateNotSaved();
            activityClientRecord.activity.mFragments.noteStateNotSaved();
            checkAndBlockForNetworkAccess();
            if (activityClientRecord.pendingIntents != null) {
                deliverNewIntents(activityClientRecord, activityClientRecord.pendingIntents);
                activityClientRecord.pendingIntents = null;
            }
            if (activityClientRecord.pendingResults != null) {
                deliverResults(activityClientRecord, activityClientRecord.pendingResults, str);
                activityClientRecord.pendingResults = null;
            }
            activityClientRecord.activity.performResume(activityClientRecord.startsNotResumed, str);
            if (!DEBUG_LEVEL_LOW) {
                long freeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                double maxMemory = Runtime.getRuntime().maxMemory() * THRESHOLD_FOR_HEAPDUMP;
                if (!mIsAnomalyDetected && freeMemory > maxMemory && ActivityManager.getService().isHeapDumpAllowed()) {
                    mIsAnomalyDetected = true;
                    try {
                        Slog.i(TAG, currentPackageName() + " is using " + (freeMemory / 1048576) + " MB, so start dumping for java heapdump");
                        StringBuilder sb = new StringBuilder("/data/log/core/");
                        sb.append(currentPackageName());
                        sb.append(".hprof");
                        Debug.dumpHprofData(sb.toString());
                    } catch (Exception e) {
                        Slog.w(TAG, "Cannot dump for java heapdump");
                        e.printStackTrace();
                    }
                }
            }
            activityClientRecord.state = null;
            activityClientRecord.persistentState = null;
            activityClientRecord.setState(3);
            reportTopResumedActivityChanged(activityClientRecord, activityClientRecord.isTopResumedActivity, "topWhenResuming");
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                throw new RuntimeException("Unable to resume activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
            }
        }
        return true;
    }

    static final void cleanUpPendingRemoveWindows(ActivityClientRecord activityClientRecord, boolean z) {
        if (!activityClientRecord.mPreserveWindow || z) {
            if (activityClientRecord.mPendingRemoveWindow != null) {
                activityClientRecord.mPendingRemoveWindowManager.removeViewImmediate(activityClientRecord.mPendingRemoveWindow.getDecorView());
                IBinder windowToken = activityClientRecord.mPendingRemoveWindow.getDecorView().getWindowToken();
                if (windowToken != null) {
                    WindowManagerGlobal.getInstance().closeAll(windowToken, activityClientRecord.activity.getClass().getName(), "Activity");
                }
            }
            activityClientRecord.mPendingRemoveWindow = null;
            activityClientRecord.mPendingRemoveWindowManager = null;
        }
    }

    private void scheduleVsyncSS(ActivityClientRecord activityClientRecord, boolean z) {
        Choreographer choreographer = Choreographer.getInstance();
        if (activityClientRecord != null && activityClientRecord.window != null && choreographer != null) {
            View decorView = activityClientRecord.window.getDecorView();
            if (!decorView.isFrameMetricsObservers()) {
                getIdsController().openIdsWindow(decorView.getRootView(), choreographer);
                if (getIdsController().doIds()) {
                    Objects.requireNonNull(choreographer);
                    choreographer.scheduleVsyncSS(2);
                } else {
                    Objects.requireNonNull(choreographer);
                    choreographer.scheduleVsyncSS(0);
                }
            }
        }
        if (z) {
            resetAIDFlag();
        }
    }

    private static void resetAIDFlag() {
        sDisableAID = true;
    }

    public IdsController getIdsController() {
        if (this.mIdsController == null) {
            this.mIdsController = new IdsController(getApplication());
        }
        return this.mIdsController;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleResumeActivity(ActivityClientRecord activityClientRecord, boolean z, boolean z2, boolean z3, String str) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        if (performResumeActivity(activityClientRecord, z, str) && !this.mActivitiesToBeDestroyed.containsKey(activityClientRecord.token)) {
            Activity activity = activityClientRecord.activity;
            int i = z2 ? 256 : 0;
            boolean z4 = activity.mStartedActivity;
            boolean z5 = !z4;
            if (z4) {
                z5 = ActivityClient.getInstance().willActivityBeVisible(activity.getActivityToken());
            }
            if (activityClientRecord.window == null && !activity.mFinished && z5) {
                activityClientRecord.window = activityClientRecord.activity.getWindow();
                View decorView = activityClientRecord.window.getDecorView();
                decorView.setVisibility(4);
                WindowManager windowManager = activity.getWindowManager();
                WindowManager.LayoutParams attributes = activityClientRecord.window.getAttributes();
                activity.mDecor = decorView;
                attributes.type = 1;
                attributes.softInputMode |= i;
                if (activityClientRecord.mPreserveWindow) {
                    activity.mWindowAdded = true;
                    activityClientRecord.mPreserveWindow = false;
                    ViewRootImpl viewRootImpl = decorView.getViewRootImpl();
                    if (viewRootImpl != null) {
                        viewRootImpl.notifyChildRebuilt();
                    }
                }
                if (activity.mVisibleFromClient) {
                    if (!activity.mWindowAdded) {
                        activity.mWindowAdded = true;
                        windowManager.addView(decorView, attributes);
                    } else {
                        activity.onWindowAttributesChanged(attributes);
                    }
                }
            } else if (!z5) {
                activityClientRecord.hideForNow = true;
                if (activityClientRecord.mPreserveWindow && activity.getWindow() != null && activity.getWindow().isPreserved()) {
                    Slog.d(TAG, "resumed but preserved window is not added on the activity. we finish it here.");
                    activityClientRecord.window = activityClientRecord.activity.getWindow();
                    View decorView2 = activityClientRecord.window.getDecorView();
                    decorView2.setVisibility(4);
                    activity.mDecor = decorView2;
                    activity.mWindowAdded = true;
                    activityClientRecord.mPreserveWindow = false;
                }
            }
            cleanUpPendingRemoveWindows(activityClientRecord, false);
            if (!activityClientRecord.activity.mFinished && z5 && activityClientRecord.activity.mDecor != null && !activityClientRecord.hideForNow) {
                ViewRootImpl viewRootImpl2 = activityClientRecord.window.getDecorView().getViewRootImpl();
                WindowManager.LayoutParams attributes2 = viewRootImpl2 != null ? viewRootImpl2.mWindowAttributes : activityClientRecord.window.getAttributes();
                if ((256 & attributes2.softInputMode) != i) {
                    attributes2.softInputMode = i | (attributes2.softInputMode & (-257));
                    if (activityClientRecord.activity.mVisibleFromClient) {
                        activity.getWindowManager().updateViewLayout(activityClientRecord.window.getDecorView(), attributes2);
                    }
                }
                activityClientRecord.activity.mVisibleFromServer = true;
                this.mNumVisibleActivities++;
                if (activityClientRecord.activity.mVisibleFromClient) {
                    activityClientRecord.activity.makeVisible();
                }
                if (z3) {
                    if (viewRootImpl2 != null) {
                        viewRootImpl2.dispatchCompatFakeFocus();
                    } else {
                        activityClientRecord.window.getDecorView().fakeFocusAfterAttachingToWindow();
                    }
                }
            }
            this.mNewActivities.add(activityClientRecord);
            Looper.myQueue().addIdleHandler(new Idler());
            if (sDisableAID) {
                return;
            }
            scheduleVsyncSS(activityClientRecord, z5);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleTopResumedActivityChanged(ActivityClientRecord activityClientRecord, boolean z, String str) {
        if (activityClientRecord.isTopResumedActivity == z) {
            if (!Build.IS_DEBUGGABLE) {
                Slog.w(TAG, "Activity top position already set to onTop=" + z);
                return;
            } else {
                Slog.e(TAG, "Activity top position already set to onTop=" + z);
            }
        }
        activityClientRecord.isTopResumedActivity = z;
        if (activityClientRecord.getLifecycleState() == 3) {
            reportTopResumedActivityChanged(activityClientRecord, z, "topStateChangedWhenResumed");
        }
    }

    private void reportTopResumedActivityChanged(ActivityClientRecord activityClientRecord, boolean z, String str) {
        if (activityClientRecord.lastReportedTopResumedState != z) {
            activityClientRecord.lastReportedTopResumedState = z;
            activityClientRecord.activity.performTopResumedActivityChanged(z, str);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePauseActivity(ActivityClientRecord activityClientRecord, boolean z, boolean z2, boolean z3, PendingTransactionActions pendingTransactionActions, String str) {
        if (z2) {
            performUserLeavingActivity(activityClientRecord);
        }
        if (z3) {
            activityClientRecord.activity.mIsInPictureInPictureMode = true;
        }
        performPauseActivity(activityClientRecord, z, str, pendingTransactionActions);
        if (activityClientRecord.isPreHoneycomb()) {
            QueuedWork.waitToFinish();
        }
        this.mSomeActivitiesChanged = true;
    }

    final void performUserLeavingActivity(ActivityClientRecord activityClientRecord) {
        this.mInstrumentation.callActivityOnPictureInPictureRequested(activityClientRecord.activity);
        this.mInstrumentation.callActivityOnUserLeaving(activityClientRecord.activity);
    }

    final Bundle performPauseActivity(IBinder iBinder, boolean z, String str, PendingTransactionActions pendingTransactionActions) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            return performPauseActivity(activityClientRecord, z, str, pendingTransactionActions);
        }
        return null;
    }

    private Bundle performPauseActivity(ActivityClientRecord activityClientRecord, boolean z, String str, PendingTransactionActions pendingTransactionActions) {
        ArrayList<OnActivityPausedListener> remove;
        if (activityClientRecord.paused) {
            if (activityClientRecord.activity.mFinished) {
                return null;
            }
            RuntimeException runtimeException = new RuntimeException("Performing pause of activity that is not resumed: " + activityClientRecord.intent.getComponent().toShortString());
            Slog.e(TAG, runtimeException.getMessage(), runtimeException);
        }
        if (z) {
            activityClientRecord.activity.mFinished = true;
        }
        boolean z2 = !activityClientRecord.activity.mFinished && activityClientRecord.isPreHoneycomb();
        if (z2) {
            callActivityOnSaveInstanceState(activityClientRecord);
        }
        performPauseActivityIfNeeded(activityClientRecord, str);
        synchronized (this.mOnPauseListeners) {
            remove = this.mOnPauseListeners.remove(activityClientRecord.activity);
        }
        int size = remove != null ? remove.size() : 0;
        for (int i = 0; i < size; i++) {
            remove.get(i).onPaused(activityClientRecord.activity);
        }
        Bundle oldState = pendingTransactionActions != null ? pendingTransactionActions.getOldState() : null;
        if (oldState != null && activityClientRecord.isPreHoneycomb()) {
            activityClientRecord.state = oldState;
        }
        if (z2) {
            return activityClientRecord.state;
        }
        return null;
    }

    private void performPauseActivityIfNeeded(ActivityClientRecord activityClientRecord, String str) {
        if (activityClientRecord.paused) {
            return;
        }
        reportTopResumedActivityChanged(activityClientRecord, false, "pausing");
        try {
            activityClientRecord.activity.mCalled = false;
            this.mInstrumentation.callActivityOnPause(activityClientRecord.activity);
        } catch (SuperNotCalledException e) {
            throw e;
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                throw new RuntimeException("Unable to pause activity " + safeToComponentShortString(activityClientRecord.intent) + ": " + e2.toString(), e2);
            }
        }
        if (!activityClientRecord.activity.mCalled) {
            throw new SuperNotCalledException("Activity " + safeToComponentShortString(activityClientRecord.intent) + " did not call through to super.onPause()");
        }
        activityClientRecord.setState(4);
        if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED) {
            performReleaseActivityFocusIfNeeded(activityClientRecord.token);
        }
    }

    final void performStopActivity(IBinder iBinder, boolean z, String str) {
        performStopActivityInner(this.mActivities.get(iBinder), null, z, false, str);
    }

    private static final class ProviderRefCount {
        public final ProviderClientRecord client;
        public final ContentProviderHolder holder;
        public boolean removePending;
        public int stableCount;
        public int unstableCount;

        ProviderRefCount(ContentProviderHolder contentProviderHolder, ProviderClientRecord providerClientRecord, int i, int i2) {
            this.holder = contentProviderHolder;
            this.client = providerClientRecord;
            this.stableCount = i;
            this.unstableCount = i2;
        }
    }

    private void performStopActivityInner(ActivityClientRecord activityClientRecord, PendingTransactionActions.StopInfo stopInfo, boolean z, boolean z2, String str) {
        if (activityClientRecord.stopped) {
            if (activityClientRecord.activity.mFinished) {
                return;
            }
            if (!z2) {
                RuntimeException runtimeException = new RuntimeException("Performing stop of activity that is already stopped: " + activityClientRecord.intent.getComponent().toShortString());
                Slog.e(TAG, runtimeException.getMessage(), runtimeException);
                Slog.e(TAG, activityClientRecord.getStateString());
            }
        }
        performPauseActivityIfNeeded(activityClientRecord, str);
        if (stopInfo != null) {
            try {
                stopInfo.setDescription(activityClientRecord.activity.onCreateDescription());
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                    throw new RuntimeException("Unable to save state of activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
        callActivityOnStop(activityClientRecord, z, str);
        this.mAbnormalUsage.checkAbnormalUsage();
    }

    private void callActivityOnStop(ActivityClientRecord activityClientRecord, boolean z, String str) {
        boolean z2 = z && !activityClientRecord.activity.mFinished && activityClientRecord.state == null && !activityClientRecord.isPreHoneycomb();
        boolean isPreP = activityClientRecord.isPreP();
        if (z2 && isPreP) {
            callActivityOnSaveInstanceState(activityClientRecord);
        }
        try {
            activityClientRecord.activity.performStop(activityClientRecord.mPreserveWindow, str);
        } catch (SuperNotCalledException e) {
            throw e;
        } catch (Exception e2) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                throw new RuntimeException("Unable to stop activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
            }
        }
        activityClientRecord.setState(5);
        if (!z2 || isPreP) {
            return;
        }
        callActivityOnSaveInstanceState(activityClientRecord);
    }

    private void updateVisibility(ActivityClientRecord activityClientRecord, boolean z) {
        View view = activityClientRecord.activity.mDecor;
        if (view != null) {
            if (z) {
                if (activityClientRecord.activity.mVisibleFromServer) {
                    return;
                }
                activityClientRecord.activity.mVisibleFromServer = true;
                this.mNumVisibleActivities++;
                if (activityClientRecord.activity.mVisibleFromClient) {
                    activityClientRecord.activity.makeVisible();
                    return;
                }
                return;
            }
            if (activityClientRecord.activity.mVisibleFromServer) {
                activityClientRecord.activity.mVisibleFromServer = false;
                this.mNumVisibleActivities--;
                view.setVisibility(4);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleStopActivity(ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions, boolean z, String str) {
        PendingTransactionActions.StopInfo stopInfo = new PendingTransactionActions.StopInfo();
        performStopActivityInner(activityClientRecord, stopInfo, true, z, str);
        updateVisibility(activityClientRecord, false);
        if (!activityClientRecord.isPreHoneycomb()) {
            QueuedWork.waitToFinish();
        }
        stopInfo.setActivity(activityClientRecord);
        stopInfo.setState(activityClientRecord.state);
        stopInfo.setPersistentState(activityClientRecord.persistentState);
        pendingTransactionActions.setStopInfo(stopInfo);
        this.mSomeActivitiesChanged = true;
    }

    @Override // android.app.ClientTransactionHandler
    public void reportStop(PendingTransactionActions pendingTransactionActions) {
        this.mH.post(pendingTransactionActions.getStopInfo());
    }

    @Override // android.app.ClientTransactionHandler
    public void performRestartActivity(ActivityClientRecord activityClientRecord, boolean z) {
        if (activityClientRecord.stopped) {
            activityClientRecord.activity.performRestart(z);
            if (z) {
                activityClientRecord.setState(2);
            }
            HardwareRenderer.preload();
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void reportRefresh(ActivityClientRecord activityClientRecord) {
        ActivityClient.getInstance().activityRefreshed(activityClientRecord.token);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetCoreSettings(Bundle bundle) {
        synchronized (this.mCoreSettingsLock) {
            this.mCoreSettings = bundle;
        }
        onCoreSettingsChange();
    }

    private void onCoreSettingsChange() {
        if (updateDebugViewAttributeState()) {
            relaunchAllActivities(true, "onCoreSettingsChange");
        }
        updateDefaultNavigationBarColor();
    }

    private boolean updateDebugViewAttributeState() {
        boolean z = View.sDebugViewAttributes;
        View.sDebugViewAttributesApplicationPackage = this.mCoreSettings.getString(Settings.Global.DEBUG_VIEW_ATTRIBUTES_APPLICATION_PACKAGE, "");
        AppBindData appBindData = this.mBoundApplication;
        View.sDebugViewAttributes = this.mCoreSettings.getInt(Settings.Global.DEBUG_VIEW_ATTRIBUTES, 0) != 0 || View.sDebugViewAttributesApplicationPackage.equals((appBindData == null || appBindData.appInfo == null) ? "<unknown-app>" : this.mBoundApplication.appInfo.packageName);
        return z != View.sDebugViewAttributes;
    }

    private void relaunchAllActivities(boolean z, String str) {
        Log.i(TAG, "Relaunch all activities: " + str);
        for (int size = this.mActivities.size() + (-1); size >= 0; size--) {
            scheduleRelaunchActivityIfPossible(this.mActivities.valueAt(size), z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdatePackageCompatibilityInfo(UpdateCompatibilityData updateCompatibilityData) {
        this.mCompatibilityInfo = updateCompatibilityData.info;
        LoadedApk peekPackageInfo = peekPackageInfo(updateCompatibilityData.pkg, false);
        if (peekPackageInfo != null) {
            peekPackageInfo.setCompatibilityInfo(updateCompatibilityData.info);
        }
        LoadedApk peekPackageInfo2 = peekPackageInfo(updateCompatibilityData.pkg, true);
        if (peekPackageInfo2 != null) {
            peekPackageInfo2.setCompatibilityInfo(updateCompatibilityData.info);
        }
        this.mConfigurationController.handleConfigurationChanged(updateCompatibilityData.info);
    }

    private void deliverResults(ActivityClientRecord activityClientRecord, List<ResultInfo> list, String str) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ResultInfo resultInfo = list.get(i);
            try {
                if (resultInfo.mData != null) {
                    resultInfo.mData.setExtrasClassLoader(activityClientRecord.activity.getClassLoader());
                    resultInfo.mData.prepareToEnterProcess(isProtectedComponent(activityClientRecord.activityInfo), activityClientRecord.activity.getAttributionSource());
                }
                if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.contentUriPermissionApis()) {
                    activityClientRecord.activity.dispatchActivityResult(resultInfo.mResultWho, resultInfo.mRequestCode, resultInfo.mResultCode, resultInfo.mData, new ComponentCaller(activityClientRecord.token, resultInfo.mCallerToken), str);
                } else {
                    activityClientRecord.activity.dispatchActivityResult(resultInfo.mResultWho, resultInfo.mRequestCode, resultInfo.mResultCode, resultInfo.mData, str);
                }
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                    throw new RuntimeException("Failure delivering result " + resultInfo + " to activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleSendResult(ActivityClientRecord activityClientRecord, List<ResultInfo> list, String str) {
        boolean z = activityClientRecord.paused;
        if (!activityClientRecord.activity.mFinished && activityClientRecord.activity.mDecor != null && activityClientRecord.hideForNow && !z) {
            updateVisibility(activityClientRecord, true);
        }
        if (!z) {
            try {
                activityClientRecord.activity.mCalled = false;
                this.mInstrumentation.callActivityOnPause(activityClientRecord.activity);
                if (!activityClientRecord.activity.mCalled) {
                    throw new SuperNotCalledException("Activity " + activityClientRecord.intent.getComponent().toShortString() + " did not call through to super.onPause()");
                }
            } catch (SuperNotCalledException e) {
                throw e;
            } catch (Exception e2) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                    throw new RuntimeException("Unable to pause activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
                }
            }
        }
        checkAndBlockForNetworkAccess();
        deliverResults(activityClientRecord, list, str);
        if (z) {
            return;
        }
        activityClientRecord.activity.performResume(false, str);
    }

    void performDestroyActivity(ActivityClientRecord activityClientRecord, boolean z, boolean z2, String str) {
        Class<?> cls = activityClientRecord.activity.getClass();
        if (z) {
            activityClientRecord.activity.mFinished = true;
        }
        performPauseActivityIfNeeded(activityClientRecord, "destroy");
        if (!activityClientRecord.stopped) {
            callActivityOnStop(activityClientRecord, false, "destroy");
        }
        if (z2) {
            try {
                activityClientRecord.lastNonConfigurationInstances = activityClientRecord.activity.retainNonConfigurationInstances();
            } catch (Exception e) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                    throw new RuntimeException("Unable to retain activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
        try {
            activityClientRecord.activity.mCalled = false;
            this.mInstrumentation.callActivityOnDestroy(activityClientRecord.activity);
        } catch (SuperNotCalledException e2) {
            throw e2;
        } catch (Exception e3) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e3)) {
                throw new RuntimeException("Unable to destroy activity " + safeToComponentShortString(activityClientRecord.intent) + ": " + e3.toString(), e3);
            }
        }
        if (!activityClientRecord.activity.mCalled) {
            throw new SuperNotCalledException("Activity " + safeToComponentShortString(activityClientRecord.intent) + " did not call through to super.onDestroy()");
        }
        if (activityClientRecord.window != null) {
            activityClientRecord.window.closeAllPanels();
        }
        activityClientRecord.setState(6);
        schedulePurgeIdler();
        synchronized (this) {
            SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal = this.mSplashScreenGlobal;
            if (splashScreenManagerGlobal != null) {
                splashScreenManagerGlobal.tokenDestroyed(activityClientRecord.token);
            }
        }
        synchronized (this.mResourcesManager) {
            this.mActivities.remove(activityClientRecord.token);
        }
        StrictMode.decrementExpectedActivityCount(cls);
    }

    private static String safeToComponentShortString(Intent intent) {
        ComponentName component = intent.getComponent();
        return component == null ? "[Unknown]" : component.toShortString();
    }

    @Override // android.app.ClientTransactionHandler
    public Map<IBinder, DestroyActivityItem> getActivitiesToBeDestroyed() {
        return this.mActivitiesToBeDestroyed;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleDestroyActivity(ActivityClientRecord activityClientRecord, boolean z, boolean z2, String str) {
        performDestroyActivity(activityClientRecord, z, z2, str);
        cleanUpPendingRemoveWindows(activityClientRecord, z);
        WindowManager windowManager = activityClientRecord.activity.getWindowManager();
        View view = activityClientRecord.activity.mDecor;
        if (view != null) {
            if (activityClientRecord.activity.mVisibleFromServer) {
                this.mNumVisibleActivities--;
            }
            IBinder windowToken = view.getWindowToken();
            if (activityClientRecord.activity.mWindowAdded) {
                if (activityClientRecord.mPreserveWindow) {
                    activityClientRecord.mPendingRemoveWindow = activityClientRecord.window;
                    activityClientRecord.mPendingRemoveWindowManager = windowManager;
                    activityClientRecord.window.clearContentView();
                } else {
                    ViewRootImpl viewRootImpl = view.getViewRootImpl();
                    if (viewRootImpl != null) {
                        viewRootImpl.setActivityConfigCallback(null);
                    }
                    windowManager.removeViewImmediate(view);
                }
            }
            if (windowToken != null && activityClientRecord.mPendingRemoveWindow == null) {
                WindowManagerGlobal.getInstance().closeAll(windowToken, activityClientRecord.activity.getClass().getName(), "Activity");
            } else if (activityClientRecord.mPendingRemoveWindow != null) {
                WindowManagerGlobal.getInstance().closeAllExceptView(activityClientRecord.token, view, activityClientRecord.activity.getClass().getName(), "Activity");
            }
            activityClientRecord.activity.mDecor = null;
        }
        if (activityClientRecord.mPendingRemoveWindow == null) {
            WindowManagerGlobal.getInstance().closeAll(activityClientRecord.token, activityClientRecord.activity.getClass().getName(), "Activity");
        }
        Context baseContext = activityClientRecord.activity.getBaseContext();
        if (baseContext instanceof ContextImpl) {
            ((ContextImpl) baseContext).scheduleFinalCleanup(activityClientRecord.activity.getClass().getName(), "Activity");
        }
        if (z) {
            ActivityClient.getInstance().activityDestroyed(activityClientRecord.token);
            this.mNewActivities.remove(activityClientRecord);
        }
        this.mSomeActivitiesChanged = true;
    }

    @Override // android.app.ClientTransactionHandler
    public ActivityClientRecord prepareRelaunchActivity(IBinder iBinder, List<ResultInfo> list, List<ReferrerIntent> list2, int i, MergedConfiguration mergedConfiguration, boolean z, ActivityWindowInfo activityWindowInfo) {
        boolean z2;
        ActivityClientRecord activityClientRecord;
        synchronized (this.mResourcesManager) {
            z2 = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.mRelaunchingActivities.size()) {
                    activityClientRecord = null;
                    break;
                }
                activityClientRecord = this.mRelaunchingActivities.get(i2);
                if (activityClientRecord.token == iBinder) {
                    if (list != null) {
                        if (activityClientRecord.pendingResults != null) {
                            activityClientRecord.pendingResults.addAll(list);
                        } else {
                            activityClientRecord.pendingResults = list;
                        }
                    }
                    if (list2 != null) {
                        if (activityClientRecord.pendingIntents != null) {
                            activityClientRecord.pendingIntents.addAll(list2);
                        } else {
                            activityClientRecord.pendingIntents = list2;
                        }
                    }
                } else {
                    i2++;
                }
            }
            if (activityClientRecord == null) {
                activityClientRecord = new ActivityClientRecord();
                activityClientRecord.token = iBinder;
                activityClientRecord.pendingResults = list;
                activityClientRecord.pendingIntents = list2;
                activityClientRecord.mPreserveWindow = z;
                this.mRelaunchingActivities.add(activityClientRecord);
                z2 = true;
            }
            activityClientRecord.createdConfig = mergedConfiguration.getGlobalConfiguration();
            activityClientRecord.overrideConfig = mergedConfiguration.getOverrideConfiguration();
            activityClientRecord.pendingConfigChanges |= i;
            activityClientRecord.mActivityWindowInfo.set(activityWindowInfo);
        }
        if (z2 && ActivityManager.isProcStateCached(this.mLastProcessState)) {
            this.mConfigurationController.updatePendingConfiguration(mergedConfiguration.getGlobalConfiguration());
        }
        if (z2) {
            return activityClientRecord;
        }
        return null;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleRelaunchActivity(ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Configuration configuration;
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        synchronized (this.mResourcesManager) {
            int size = this.mRelaunchingActivities.size();
            IBinder iBinder = activityClientRecord.token;
            boolean z = false;
            ActivityClientRecord activityClientRecord2 = null;
            int i = 0;
            int i2 = 0;
            while (i < size) {
                ActivityClientRecord activityClientRecord3 = this.mRelaunchingActivities.get(i);
                if (activityClientRecord3.token == iBinder) {
                    i2 |= activityClientRecord3.pendingConfigChanges;
                    this.mRelaunchingActivities.remove(i);
                    i--;
                    size--;
                    activityClientRecord2 = activityClientRecord3;
                }
                i++;
            }
            if (activityClientRecord2 == null) {
                return;
            }
            Configuration pendingConfiguration = this.mConfigurationController.getPendingConfiguration(true);
            this.mPendingConfiguration = null;
            if (activityClientRecord2.createdConfig != null && (((configuration = this.mConfigurationController.getConfiguration()) == null || (activityClientRecord2.createdConfig.isOtherSeqNewer(configuration) && configuration.diff(activityClientRecord2.createdConfig) != 0)) && (pendingConfiguration == null || activityClientRecord2.createdConfig.isOtherSeqNewer(pendingConfiguration)))) {
                pendingConfiguration = activityClientRecord2.createdConfig;
            }
            if (pendingConfiguration != null) {
                this.mConfigurationController.updateDefaultDensity(pendingConfiguration.densityDpi);
                this.mConfigurationController.handleConfigurationChanged(pendingConfiguration, null);
                this.mCurDefaultDisplayDpi = this.mConfigurationController.getCurDefaultDisplayDpi();
                this.mConfiguration = this.mConfigurationController.getConfiguration();
            }
            ActivityClientRecord activityClientRecord4 = this.mActivities.get(activityClientRecord2.token);
            if (activityClientRecord4 == null) {
                return;
            }
            activityClientRecord4.activity.mConfigChangeFlags |= i2;
            if (activityClientRecord4.activity.mWindowAdded && activityClientRecord2.mPreserveWindow) {
                z = true;
            }
            activityClientRecord4.mPreserveWindow = z;
            activityClientRecord4.activity.mChangingConfigurations = true;
            handleRelaunchActivityInner(activityClientRecord4, activityClientRecord2.pendingResults, activityClientRecord2.pendingIntents, pendingTransactionActions, activityClientRecord2.startsNotResumed, activityClientRecord2.overrideConfig, activityClientRecord2.mActivityWindowInfo, "handleRelaunchActivity");
        }
    }

    void scheduleRelaunchActivity(IBinder iBinder) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            Log.i(TAG, "Schedule relaunch activity: " + activityClientRecord.activityInfo.name);
            Log.d(TAG, "scheduleRelaunchActivity: preserveWindow=" + (activityClientRecord.stopped ^ true) + ", r=" + activityClientRecord.activity + ", caller=" + Debug.getCallers(8));
            scheduleRelaunchActivityIfPossible(activityClientRecord, activityClientRecord.stopped ^ true);
        }
    }

    private void scheduleRelaunchActivityIfPossible(ActivityClientRecord activityClientRecord, boolean z) {
        if ((activityClientRecord.activity == null || !activityClientRecord.activity.mFinished) && !(activityClientRecord.token instanceof Binder)) {
            if (z && activityClientRecord.window != null) {
                activityClientRecord.mPreserveWindow = true;
            }
            this.mH.removeMessages(160, activityClientRecord.token);
            sendMessage(160, activityClientRecord.token);
        }
    }

    public void handleRelaunchActivityLocally(IBinder iBinder) {
        ClientTransactionItem lifecycleRequestForCurrentState;
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null) {
            Log.w(TAG, "Activity to relaunch no longer exists");
            return;
        }
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState < 2 || lifecycleState > 5) {
            Log.w(TAG, "Activity state must be in [ON_START..ON_STOP] in order to be relaunched,current state is " + lifecycleState);
            return;
        }
        ActivityClient.getInstance().activityLocalRelaunch(activityClientRecord.token);
        ActivityRelaunchItem activityRelaunchItem = new ActivityRelaunchItem(activityClientRecord.token, null, null, 0, new MergedConfiguration(activityClientRecord.createdConfig != null ? activityClientRecord.createdConfig : this.mConfigurationController.getConfiguration(), activityClientRecord.overrideConfig), activityClientRecord.mPreserveWindow, activityClientRecord.getActivityWindowInfo());
        if (lifecycleState == 2 && hasResumedPopOver()) {
            lifecycleRequestForCurrentState = new ResumeActivityItem(iBinder, false, false);
        } else {
            lifecycleRequestForCurrentState = TransactionExecutorHelper.getLifecycleRequestForCurrentState(activityClientRecord);
        }
        ClientTransaction clientTransaction = new ClientTransaction(this.mAppThread);
        clientTransaction.addTransactionItem(activityRelaunchItem);
        clientTransaction.addTransactionItem(lifecycleRequestForCurrentState);
        executeTransaction(clientTransaction);
    }

    private void handleRelaunchActivityInner(ActivityClientRecord activityClientRecord, List<ResultInfo> list, List<ReferrerIntent> list2, PendingTransactionActions pendingTransactionActions, boolean z, Configuration configuration, ActivityWindowInfo activityWindowInfo, String str) {
        Intent intent = activityClientRecord.activity.mIntent;
        if (!activityClientRecord.paused) {
            performPauseActivity(activityClientRecord, false, str, (PendingTransactionActions) null);
        }
        if (!activityClientRecord.stopped) {
            callActivityOnStop(activityClientRecord, true, str);
        }
        handleDestroyActivity(activityClientRecord, false, true, str);
        activityClientRecord.mLastDisplayIdByRelaunch = activityClientRecord.activity.getDisplayId();
        activityClientRecord.activity = null;
        activityClientRecord.window = null;
        activityClientRecord.hideForNow = false;
        if (list != null) {
            if (activityClientRecord.pendingResults == null) {
                activityClientRecord.pendingResults = list;
            } else {
                activityClientRecord.pendingResults.addAll(list);
            }
        }
        if (list2 != null) {
            if (activityClientRecord.pendingIntents == null) {
                activityClientRecord.pendingIntents = list2;
            } else {
                activityClientRecord.pendingIntents.addAll(list2);
            }
        }
        activityClientRecord.startsNotResumed = z;
        activityClientRecord.overrideConfig = configuration;
        activityClientRecord.mActivityWindowInfo.set(activityWindowInfo);
        handleLaunchActivity(activityClientRecord, pendingTransactionActions, this.mLastReportedDeviceId, intent);
    }

    @Override // android.app.ClientTransactionHandler
    public void reportRelaunch(ActivityClientRecord activityClientRecord) {
        ActivityClient.getInstance().activityRelaunched(activityClientRecord.token);
    }

    private void callActivityOnSaveInstanceState(ActivityClientRecord activityClientRecord) {
        activityClientRecord.state = new Bundle();
        activityClientRecord.state.setAllowFds(false);
        if (activityClientRecord.isPersistable()) {
            activityClientRecord.persistentState = new PersistableBundle();
            this.mInstrumentation.callActivityOnSaveInstanceState(activityClientRecord.activity, activityClientRecord.state, activityClientRecord.persistentState);
        } else {
            this.mInstrumentation.callActivityOnSaveInstanceState(activityClientRecord.activity, activityClientRecord.state);
        }
    }

    @Override // android.app.ActivityThreadInternal
    public ArrayList<ComponentCallbacks2> collectComponentCallbacks(boolean z) {
        int i;
        ArrayList<ComponentCallbacks2> arrayList = new ArrayList<>();
        synchronized (this.mResourcesManager) {
            int size = this.mAllApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(this.mAllApplications.get(i2));
            }
            if (z) {
                for (int size2 = this.mActivities.size() - 1; size2 >= 0; size2--) {
                    Activity activity = this.mActivities.valueAt(size2).activity;
                    if (activity != null && !activity.mFinished) {
                        arrayList.add(activity);
                    }
                }
            }
            int size3 = this.mServices.size();
            for (int i3 = 0; i3 < size3; i3++) {
                Service valueAt = this.mServices.valueAt(i3);
                if (z || !(valueAt instanceof WindowProviderService)) {
                    arrayList.add(valueAt);
                }
            }
        }
        synchronized (this.mProviderMap) {
            int size4 = this.mLocalProviders.size();
            for (i = 0; i < size4; i++) {
                arrayList.add(this.mLocalProviders.valueAt(i).mLocalProvider);
            }
        }
        return arrayList;
    }

    private Configuration performConfigurationChangedForActivity(ActivityClientRecord activityClientRecord, Configuration configuration, int i, boolean z) {
        activityClientRecord.tmpConfig.setTo(configuration);
        if (activityClientRecord.overrideConfig != null) {
            activityClientRecord.tmpConfig.updateFrom(activityClientRecord.overrideConfig);
        }
        Configuration performActivityConfigurationChanged = performActivityConfigurationChanged(activityClientRecord, activityClientRecord.tmpConfig, activityClientRecord.overrideConfig, i, z);
        ConfigurationHelper.freeTextLayoutCachesIfNeeded(activityClientRecord.activity.mCurrentConfig.diff(activityClientRecord.tmpConfig));
        return performActivityConfigurationChanged;
    }

    private Configuration performActivityConfigurationChanged(ActivityClientRecord activityClientRecord, Configuration configuration, Configuration configuration2, int i, boolean z) {
        Configuration configuration3;
        Configuration configuration4;
        Activity activity = activityClientRecord.activity;
        IBinder activityToken = activity.getActivityToken();
        handleWindowingModeChangeIfNeeded(activityClientRecord, configuration);
        boolean isDifferentDisplay = ConfigurationHelper.isDifferentDisplay(activity.getDisplayId(), i);
        Configuration configuration5 = activity.getResources().getConfiguration();
        boolean z2 = true;
        boolean z3 = configuration5.diffPublicOnly(configuration) != 0;
        if (z3) {
            configuration3 = configuration;
            configuration4 = configuration2;
        } else {
            Boolean valueOf = Boolean.valueOf(z3);
            configuration3 = configuration;
            configuration4 = configuration2;
            if (!ConfigurationHelper.shouldUpdateResources(activityToken, configuration5, configuration3, configuration4, isDifferentDisplay, valueOf)) {
                z2 = false;
            }
        }
        boolean z4 = activity.getResources().getBoolean(R.bool.config_skipActivityRelaunchWhenDocking);
        int realConfigChanged = activity.mActivityInfo.getRealConfigChanged();
        if (z4 && onlyDeskInUiModeChanged(activity.mCurrentConfig, configuration3)) {
            realConfigChanged |= 512;
        }
        boolean shouldReportChange = shouldReportChange(activity.mCurrentConfig, configuration3, activityClientRecord.mSizeConfigurations, realConfigChanged, z);
        if (!z2 && !shouldReportChange && configuration5.windowConfiguration.getStageType() == configuration3.windowConfiguration.getStageType()) {
            return null;
        }
        Configuration overrideConfiguration = activity.getOverrideConfiguration();
        this.mResourcesManager.updateResourcesForActivity(activityToken, ConfigurationController.createNewConfigAndUpdateIfNotNull(configuration4, overrideConfiguration), i);
        Configuration createNewConfigAndUpdateIfNotNull = ConfigurationController.createNewConfigAndUpdateIfNotNull(configuration3, overrideConfiguration);
        if (isDifferentDisplay) {
            activity.dispatchMovedToDisplay(i, createNewConfigAndUpdateIfNotNull);
        }
        setActivityCurrentConfigIfPossible(activity, new Configuration(configuration3));
        activity.mConfigChangeFlags = 0;
        if (shouldReportChange) {
            activity.mCalled = false;
            activity.mCurrentConfig = new Configuration(configuration3);
            activity.onConfigurationChanged(createNewConfigAndUpdateIfNotNull);
            if (!activity.mCalled) {
                throw new SuperNotCalledException("Activity " + activity.getLocalClassName() + " did not call through to super.onConfigurationChanged()");
            }
        }
        this.mConfigurationChangedListenerController.dispatchOnConfigurationChanged(activity.getActivityToken());
        return createNewConfigAndUpdateIfNotNull;
    }

    private boolean onlyDeskInUiModeChanged(Configuration configuration, Configuration configuration2) {
        return (isInDeskUiMode(configuration) != isInDeskUiMode(configuration2)) && !((configuration.uiMode & (-16)) != (configuration2.uiMode & (-16)));
    }

    private static boolean isInDeskUiMode(Configuration configuration) {
        return (configuration.uiMode & 15) == 2;
    }

    public static boolean shouldReportChange(Configuration configuration, Configuration configuration2, SizeConfigurationBuckets sizeConfigurationBuckets, int i, boolean z) {
        int diffPublicOnly = configuration.diffPublicOnly(configuration2);
        if (diffPublicOnly == 0) {
            return false;
        }
        if (z) {
            return true;
        }
        if (android.content.res.Flags.handleAllConfigChanges() && (134217728 & i) != 0) {
            return true;
        }
        int filterDiff = SizeConfigurationBuckets.filterDiff(diffPublicOnly, configuration, configuration2, sizeConfigurationBuckets);
        if (filterDiff != 0) {
            diffPublicOnly = filterDiff;
        }
        return ((~i) & diffPublicOnly) == 0;
    }

    public final void applyConfigurationToResources(Configuration configuration) {
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyConfigurationToResources(configuration, null);
        }
    }

    private void updateDeviceIdForNonUIContexts(int i) {
        if (i == -1 || i == this.mLastReportedDeviceId) {
            return;
        }
        this.mLastReportedDeviceId = i;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mResourcesManager) {
            int size = this.mAllApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(this.mAllApplications.get(i2));
            }
            int size2 = this.mServices.size();
            for (int i3 = 0; i3 < size2; i3++) {
                Service valueAt = this.mServices.valueAt(i3);
                if (!valueAt.isUiContext()) {
                    arrayList.add(valueAt);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((Context) it.next()).updateDeviceId(i);
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleConfigurationChanged(Configuration configuration, int i) {
        this.mConfigurationController.handleConfigurationChanged(configuration);
        updateDeviceIdForNonUIContexts(i);
        this.mCurDefaultDisplayDpi = this.mConfigurationController.getCurDefaultDisplayDpi();
        this.mConfiguration = this.mConfigurationController.getConfiguration();
        this.mPendingConfiguration = this.mConfigurationController.getPendingConfiguration(false);
    }

    @Override // android.app.ClientTransactionHandler
    public void handleWindowContextInfoChanged(IBinder iBinder, WindowContextInfo windowContextInfo) {
        WindowTokenClientController.getInstance().onWindowContextInfoChanged(iBinder, windowContextInfo);
    }

    @Override // android.app.ClientTransactionHandler
    public void handleWindowContextWindowRemoval(IBinder iBinder) {
        WindowTokenClientController.getInstance().onWindowContextWindowRemoved(iBinder);
    }

    private void handleWindowingModeChangeIfNeeded(ActivityClientRecord activityClientRecord, Configuration configuration) {
        Activity activity = activityClientRecord.activity;
        int compatWindowingMode = CompatSandbox.getCompatWindowingMode(configuration, configuration.windowConfiguration.getWindowingMode());
        int i = activityClientRecord.mLastReportedWindowingMode;
        if (i == compatWindowingMode) {
            return;
        }
        if (compatWindowingMode == 2) {
            activity.dispatchPictureInPictureModeChanged(true, configuration);
        } else if (i == 2) {
            activity.dispatchPictureInPictureModeChanged(false, configuration);
        }
        boolean inMultiWindowMode = WindowConfiguration.inMultiWindowMode(i);
        boolean inMultiWindowMode2 = WindowConfiguration.inMultiWindowMode(compatWindowingMode);
        if (inMultiWindowMode != inMultiWindowMode2) {
            activity.dispatchMultiWindowModeChanged(inMultiWindowMode2, configuration);
        }
        activityClientRecord.mLastReportedWindowingMode = compatWindowingMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPendingApplicationInfoChanges(String str) {
        ApplicationInfo remove;
        synchronized (this.mResourcesManager) {
            remove = this.mPendingAppInfoUpdates.remove(str);
        }
        if (remove == null) {
            return;
        }
        handleApplicationInfoChanged(remove);
    }

    public void handleSystemApplicationInfoChanged(ApplicationInfo applicationInfo) {
        Preconditions.checkState(this.mSystemThread, "Must only be called in the system process");
        handleApplicationInfoChanged(applicationInfo);
    }

    public void handleApplicationInfoChanged(ApplicationInfo applicationInfo) {
        LoadedApk loadedApk;
        LoadedApk loadedApk2;
        synchronized (this.mResourcesManager) {
            WeakReference<LoadedApk> weakReference = this.mPackages.get(applicationInfo.packageName);
            loadedApk = weakReference != null ? weakReference.get() : null;
            WeakReference<LoadedApk> weakReference2 = this.mResourcePackages.get(applicationInfo.packageName);
            loadedApk2 = weakReference2 != null ? weakReference2.get() : null;
            for (ActivityClientRecord activityClientRecord : this.mActivities.values()) {
                if (activityClientRecord.activityInfo.applicationInfo.packageName.equals(applicationInfo.packageName)) {
                    activityClientRecord.activityInfo.applicationInfo = applicationInfo;
                    if (loadedApk == null && loadedApk2 == null) {
                        loadedApk = activityClientRecord.packageInfo;
                    }
                    activityClientRecord.packageInfo = loadedApk != null ? loadedApk : loadedApk2;
                }
            }
        }
        if (loadedApk != null) {
            ArrayList arrayList = new ArrayList();
            LoadedApk.makePaths(this, loadedApk.getApplicationInfo(), arrayList);
            loadedApk.updateApplicationInfo(applicationInfo, arrayList);
        }
        if (loadedApk2 != null) {
            ArrayList arrayList2 = new ArrayList();
            LoadedApk.makePaths(this, loadedApk2.getApplicationInfo(), arrayList2);
            loadedApk2.updateApplicationInfo(applicationInfo, arrayList2);
        }
        if (android.content.res.Flags.systemContextHandleAppInfoChanged() && this.mSystemThread) {
            ContextImpl systemContext = getSystemContext();
            if (systemContext.getPackageName().equals(applicationInfo.packageName)) {
                ArrayList arrayList3 = new ArrayList();
                LoadedApk.makePaths(this, systemContext.getApplicationInfo(), arrayList3);
                systemContext.mPackageInfo.updateApplicationInfo(applicationInfo, arrayList3);
            }
        }
        ResourcesImpl impl = getApplication().getResources().getImpl();
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyAllPendingAppInfoUpdates();
        }
        ResourcesImpl impl2 = getApplication().getResources().getImpl();
        if (impl == impl2 || Arrays.equals(impl.getAssets().getApkAssets(), impl2.getAssets().getApkAssets())) {
            return;
        }
        List asList = Arrays.asList(impl.getAssets().getApkPaths());
        List asList2 = Arrays.asList(impl2.getAssets().getApkPaths());
        ArrayList arrayList4 = new ArrayList(asList);
        arrayList4.removeAll(asList2);
        ArrayList arrayList5 = new ArrayList(asList2);
        arrayList5.removeAll(asList);
        Slog.i(TAG, "ApplicationInfo updating for " + applicationInfo.packageName + ", new timestamp: " + applicationInfo.createTimestamp + "\nassets removed: " + arrayList4 + "\nassets added: " + arrayList5);
    }

    @Override // android.app.ClientTransactionHandler
    public void updatePendingActivityConfiguration(IBinder iBinder, Configuration configuration) {
        synchronized (this.mPendingOverrideConfigs) {
            Configuration configuration2 = this.mPendingOverrideConfigs.get(iBinder);
            if (configuration2 == null || configuration2.isOtherSeqNewer(configuration)) {
                this.mPendingOverrideConfigs.put(iBinder, configuration);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleActivityConfigurationChanged(ActivityClientRecord activityClientRecord, Configuration configuration, int i, ActivityWindowInfo activityWindowInfo) {
        handleActivityConfigurationChanged(activityClientRecord, configuration, i, activityWindowInfo, true);
    }

    void handleActivityConfigurationChanged(ActivityClientRecord activityClientRecord, Configuration configuration, int i, ActivityWindowInfo activityWindowInfo, boolean z) {
        ClientTransactionListenerController clientTransactionListenerController = ClientTransactionListenerController.getInstance();
        Activity activity = activityClientRecord.activity;
        clientTransactionListenerController.onContextConfigurationPreChanged(activity);
        try {
            handleActivityConfigurationChangedInner(activityClientRecord, configuration, i, activityWindowInfo, z);
        } finally {
            clientTransactionListenerController.onContextConfigurationPostChanged(activity);
        }
    }

    private void handleActivityConfigurationChangedInner(ActivityClientRecord activityClientRecord, Configuration configuration, int i, ActivityWindowInfo activityWindowInfo, boolean z) {
        synchronized (this.mPendingOverrideConfigs) {
            if (configuration.isOtherSeqNewer(this.mPendingOverrideConfigs.get(activityClientRecord.token))) {
                return;
            }
            this.mPendingOverrideConfigs.remove(activityClientRecord.token);
            if (i == -1) {
                i = activityClientRecord.activity.getDisplayId();
            }
            boolean isDifferentDisplay = ConfigurationHelper.isDifferentDisplay(activityClientRecord.activity.getDisplayId(), i);
            if (activityClientRecord.overrideConfig == null || activityClientRecord.overrideConfig.isOtherSeqNewer(configuration) || isDifferentDisplay) {
                activityClientRecord.overrideConfig = configuration;
                activityClientRecord.mActivityWindowInfo.set(activityWindowInfo);
                ViewRootImpl viewRootImpl = activityClientRecord.activity.mDecor != null ? activityClientRecord.activity.mDecor.getViewRootImpl() : null;
                Configuration performConfigurationChangedForActivity = performConfigurationChangedForActivity(activityClientRecord, this.mConfigurationController.getCompatConfiguration(), isDifferentDisplay ? i : activityClientRecord.activity.getDisplayId(), z);
                if (viewRootImpl != null) {
                    if (isDifferentDisplay) {
                        viewRootImpl.onMovedToDisplay(i, performConfigurationChangedForActivity);
                    }
                    ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(activityClientRecord.activity.getActivityToken());
                    Iterator<ViewRootImpl> it = rootViews.iterator();
                    while (it.hasNext()) {
                        ViewRootImpl next = it.next();
                        if (next.mWindowAttributes.type != 3) {
                            next.updateConfiguration(i);
                        }
                    }
                    if (!rootViews.contains(viewRootImpl)) {
                        viewRootImpl.updateConfiguration(i);
                    }
                }
                this.mSomeActivitiesChanged = true;
                handleActivityWindowInfoChanged(activityClientRecord);
            }
        }
    }

    private void handleActivityWindowInfoChanged(ActivityClientRecord activityClientRecord) {
        if (activityClientRecord.mActivityWindowInfo.equals(activityClientRecord.mLastReportedActivityWindowInfo)) {
            return;
        }
        activityClientRecord.mLastReportedActivityWindowInfo.set(activityClientRecord.mActivityWindowInfo);
        ClientTransactionListenerController.getInstance().onActivityWindowInfoChanged(activityClientRecord.token, activityClientRecord.mActivityWindowInfo);
    }

    final void handleProfilerControl(boolean z, ProfilerInfo profilerInfo, int i) {
        if (!z) {
            if (i == 1) {
                if (!com.android.internal.hidden_from_bootclasspath.com.android.art.flags.Flags.alwaysEnableProfileCode()) {
                    if (profilerInfo != null) {
                    }
                    Slog.w(TAG, "Low overhead tracing feature is not enabled");
                    return;
                } else {
                    if (profilerInfo != null) {
                        VMDebug.dumpLowOverheadTrace(VMDebug.TraceDestination.fromFileDescriptor(profilerInfo.profileFd.getFileDescriptor()));
                    }
                    VMDebug.stopLowOverheadTrace();
                    return;
                }
            }
            this.mProfiler.stopProfiling();
            return;
        }
        try {
            if (i == 1) {
                if (!com.android.internal.hidden_from_bootclasspath.com.android.art.flags.Flags.alwaysEnableProfileCode()) {
                    Slog.w(TAG, "Low overhead tracing feature is not enabled");
                    return;
                } else {
                    VMDebug.startLowOverheadTraceForAllMethods();
                    return;
                }
            }
            this.mProfiler.setProfiler(profilerInfo);
            this.mProfiler.startProfiling();
        } catch (RuntimeException unused) {
            Slog.w(TAG, "Profiling failed on path " + profilerInfo.profileFile + " -- can the process access this path?");
        } finally {
            profilerInfo.closeFd();
        }
    }

    public void stopProfiling() {
        Profiler profiler = this.mProfiler;
        if (profiler != null) {
            profiler.stopProfiling();
        }
    }

    static void handleDumpHeap(DumpHeapData dumpHeapData) {
        ParcelFileDescriptor parcelFileDescriptor;
        if (dumpHeapData.runGc) {
            System.gc();
            System.runFinalization();
            System.gc();
        }
        try {
            parcelFileDescriptor = dumpHeapData.fd;
        } catch (IOException e) {
            if (!dumpHeapData.managed) {
                Slog.w(TAG, "Failed to dump heap", e);
            } else {
                Slog.w(TAG, "Managed heap dump failed on path " + dumpHeapData.path + " -- can the process access this path?", e);
            }
        } catch (RuntimeException e2) {
            Slog.wtf(TAG, "Heap dumper threw a runtime exception", e2);
        }
        try {
            if (dumpHeapData.managed) {
                Debug.dumpHprofData(dumpHeapData.path, parcelFileDescriptor.getFileDescriptor(), dumpHeapData.dumpBitmaps);
            } else if (dumpHeapData.mallocInfo) {
                Debug.dumpNativeMallocInfo(parcelFileDescriptor.getFileDescriptor());
            } else {
                Debug.dumpNativeHeap(parcelFileDescriptor.getFileDescriptor());
            }
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
            try {
                ActivityManager.getService().dumpHeapFinished(dumpHeapData.path);
                if (dumpHeapData.finishCallback != null) {
                    dumpHeapData.finishCallback.sendResult(null);
                }
            } catch (RemoteException e3) {
                throw e3.rethrowFromSystemServer();
            }
        } finally {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x0116, code lost:
    
        if (r5.get() != null) goto L63;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void handleDispatchPackageBroadcast(int r13, java.lang.String[] r14) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleDispatchPackageBroadcast(int, java.lang.String[]):void");
    }

    final void handleLowMemory() {
        ArrayList<ComponentCallbacks2> collectComponentCallbacks = collectComponentCallbacks(true);
        int size = collectComponentCallbacks.size();
        for (int i = 0; i < size; i++) {
            collectComponentCallbacks.get(i).onLowMemory();
        }
        if (Process.myUid() != 1000) {
            EventLog.writeEvent(SQLITE_MEM_RELEASED_EVENT_LOG_TAG, SQLiteDatabase.releaseMemory());
        }
        Canvas.freeCaches();
        Canvas.freeTextLayoutCaches();
        BinderInternal.forceGc("mem");
    }

    public static class ReclaimerLog {
        private static String reclaimerLogPath = "/proc/reclaimer_log";
        private static boolean sReclaimerLogSupport = true;
        private static boolean sReclaimerLogSupportChecked = false;

        private static boolean reclaimerLogSupported() {
            if (!sReclaimerLogSupportChecked) {
                sReclaimerLogSupportChecked = true;
                StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                if (!new File(reclaimerLogPath).exists()) {
                    sReclaimerLogSupport = false;
                }
                StrictMode.setThreadPolicy(allowThreadDiskReads);
            }
            return sReclaimerLogSupport;
        }

        public static boolean write(String str, boolean z) {
            if (z) {
                Slog.i("UMR", str);
            }
            if (!reclaimerLogSupported()) {
                return false;
            }
            StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
            try {
                try {
                    FileWriter fileWriter = new FileWriter(reclaimerLogPath);
                    try {
                        fileWriter.write("UMR: " + str);
                        fileWriter.flush();
                        fileWriter.close();
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        return true;
                    } catch (Throwable th) {
                        try {
                            fileWriter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    return false;
                }
            } catch (Throwable th3) {
                StrictMode.setThreadPolicy(allowThreadDiskWrites);
                throw th3;
            }
        }

        public static boolean write(String str) {
            return write(str, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTrimMemory(int i) {
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(64L, "trimMemory: " + i);
        }
        ReclaimerLog.write("B|trimMemory level=" + i, false);
        try {
            if (!Flags.skipBgMemTrimOnFgApp() || this.mLastProcessState > 4 || i < 40) {
                ArrayList<ComponentCallbacks2> collectComponentCallbacks = collectComponentCallbacks(true);
                int size = collectComponentCallbacks.size();
                for (int i2 = 0; i2 < size; i2++) {
                    collectComponentCallbacks.get(i2).onTrimMemory(i);
                }
                Trace.traceEnd(64L);
                WindowManagerGlobal.getInstance().trimMemory(i);
                ReclaimerLog.write("E|trimMemory", false);
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    private void setupGraphicsSupport(Context context) {
        Trace.traceBegin(64L, "setupGraphicsSupport");
        if (!"android".equals(context.getPackageName())) {
            File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                Log.v(TAG, "Unable to initialize \"java.io.tmpdir\" property due to missing cache directory");
            } else {
                String absolutePath = cacheDir.getAbsolutePath();
                System.setProperty("java.io.tmpdir", absolutePath);
                try {
                    Os.setenv("TMPDIR", absolutePath, true);
                } catch (ErrnoException e) {
                    Log.w(TAG, "Unable to initialize $TMPDIR", e);
                }
            }
            Context createDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
            File codeCacheDir = createDeviceProtectedStorageContext.getCodeCacheDir();
            File cacheDir2 = createDeviceProtectedStorageContext.getCacheDir();
            if (codeCacheDir == null || cacheDir2 == null) {
                Log.w(TAG, "Unable to use shader/script cache: missing code-cache directory");
            } else {
                try {
                    if (getPackageManager().getPackagesForUid(Process.myUid()) != null) {
                        HardwareRenderer.setupDiskCache(cacheDir2);
                        RenderScriptCacheDir.setupDiskCache(codeCacheDir);
                    }
                } catch (RemoteException e2) {
                    Trace.traceEnd(64L);
                    throw e2.rethrowFromSystemServer();
                }
            }
        }
        GraphicsEnvironment.getInstance().setup(context, this.mCoreSettings);
        Trace.traceEnd(64L);
    }

    private String getInstrumentationLibrary(ApplicationInfo applicationInfo, InstrumentationInfo instrumentationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null && applicationInfo.secondaryCpuAbi.equals(instrumentationInfo.secondaryCpuAbi)) {
            String instructionSet = VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi);
            String str = SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
            if (!str.isEmpty()) {
                instructionSet = str;
            }
            if (VMRuntime.getRuntime().vmInstructionSet().equals(instructionSet)) {
                return instrumentationInfo.secondaryNativeLibraryDir;
            }
        }
        return instrumentationInfo.nativeLibraryDir;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(20:0|1|(1:3)|4|(1:6)|7|(2:9|(72:11|12|(1:14)|15|(1:17)|18|(1:20)(1:221)|21|22|23|24|140|29|(1:31)(1:214)|32|(1:34)|(1:36)|37|(1:39)|40|(2:42|(1:44)(1:212))(1:213)|45|(1:47)(1:211)|48|(1:210)(1:50)|51|(1:206)|(1:203)|(1:202)(1:61)|62|(1:64)|65|(1:67)(1:201)|68|69|70|(1:72)|74|(4:76|77|78|79)(1:197)|80|(1:82)|(1:84)(1:192)|85|(1:87)(1:191)|88|(2:90|(1:92)(2:93|(1:95)))|96|97|98|2ee|(1:104)|105|(1:109)|110|(2:112|(1:116))|117|118|120|121|(1:123)|124|125|(1:127)|169|129|(3:131|132|(1:136))|142|143|144|(2:148|149)|152|(1:161)(2:159|160)))|222|12|(0)|15|(0)|18|(0)(0)|21|22|23|24|140|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0134, code lost:
    
        android.util.Slog.e(android.app.ActivityThread.TAG, "Failed to parse serialized system font map");
        android.graphics.Typeface.loadPreinstalledSystemFontMap();
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0141 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleBindApplication(android.app.ActivityThread.AppBindData r22) {
        /*
            Method dump skipped, instructions count: 1171
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.handleBindApplication(android.app.ActivityThread$AppBindData):void");
    }

    private void waitForDebugger(AppBindData appBindData) {
        IActivityManager service = ActivityManager.getService();
        Slog.w(TAG, "Application " + appBindData.info.getPackageName() + " is waiting for the debugger ...");
        try {
            service.showWaitingForDebugger(this.mAppThread, true);
            Debug.waitForDebugger();
            try {
                service.showWaitingForDebugger(this.mAppThread, false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private void suspendAllAndSendVmStart(AppBindData appBindData) {
        IActivityManager service = ActivityManager.getService();
        Slog.w(TAG, "Application " + appBindData.info.getPackageName() + " is suspending. Debugger needs to resume to continue.");
        try {
            service.showWaitingForDebugger(this.mAppThread, true);
            Debug.suspendAllAndSendVmStart();
            try {
                service.showWaitingForDebugger(this.mAppThread, false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private void initZipPathValidatorCallback() {
        if (CompatChanges.isChangeEnabled(SafeZipPathValidatorCallback.VALIDATE_ZIP_PATH_FOR_PATH_TRAVERSAL)) {
            ZipPathValidator.setCallback(new SafeZipPathValidatorCallback());
        } else {
            ZipPathValidator.clearCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetContentCaptureOptionsCallback(String str) {
        IBinder service;
        if (this.mContentCaptureOptionsCallback == null && (service = ServiceManager.getService(Context.CONTENT_CAPTURE_MANAGER_SERVICE)) != null) {
            IContentCaptureManager asInterface = IContentCaptureManager.Stub.asInterface(service);
            IContentCaptureOptionsCallback.Stub stub = new IContentCaptureOptionsCallback.Stub() { // from class: android.app.ActivityThread.6
                @Override // android.view.contentcapture.IContentCaptureOptionsCallback
                public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                    if (ActivityThread.this.mInitialApplication != null) {
                        ActivityThread.this.mInitialApplication.setContentCaptureOptions(contentCaptureOptions);
                    }
                }
            };
            this.mContentCaptureOptionsCallback = stub;
            try {
                asInterface.registerContentCaptureOptionsCallback(str, stub);
            } catch (RemoteException e) {
                Slog.w(TAG, "registerContentCaptureOptionsCallback() failed: " + str, e);
                this.mContentCaptureOptionsCallback = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInstrumentWithoutRestart(AppBindData appBindData) {
        try {
            appBindData.compatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
            appBindData.info = getPackageInfoNoCheck(appBindData.appInfo);
            this.mInstrumentingWithoutRestart = true;
            initInstrumentation(prepareInstrumentation(appBindData), appBindData, ContextImpl.createAppContext(this, appBindData.info));
            try {
                this.mInstrumentation.onCreate(appBindData.instrumentationArgs);
            } catch (Exception e) {
                throw new RuntimeException("Exception thrown in onCreate() of " + appBindData.instrumentationName + ": " + e.toString(), e);
            }
        } catch (Exception e2) {
            Slog.e(TAG, "Error in handleInstrumentWithoutRestart", e2);
        }
    }

    private InstrumentationInfo prepareInstrumentation(AppBindData appBindData) {
        try {
            InstrumentationInfo instrumentationInfoAsUser = getPackageManager().getInstrumentationInfoAsUser(appBindData.instrumentationName, 0, UserHandle.myUserId());
            if (instrumentationInfoAsUser == null) {
                throw new RuntimeException("Unable to find instrumentation info for: " + appBindData.instrumentationName);
            }
            if (!Objects.equals(appBindData.appInfo.primaryCpuAbi, instrumentationInfoAsUser.primaryCpuAbi) || !Objects.equals(appBindData.appInfo.secondaryCpuAbi, instrumentationInfoAsUser.secondaryCpuAbi)) {
                Slog.w(TAG, "Package uses different ABI(s) than its instrumentation: package[" + appBindData.appInfo.packageName + "]: " + appBindData.appInfo.primaryCpuAbi + ", " + appBindData.appInfo.secondaryCpuAbi + " instrumentation[" + instrumentationInfoAsUser.packageName + "]: " + instrumentationInfoAsUser.primaryCpuAbi + ", " + instrumentationInfoAsUser.secondaryCpuAbi);
            }
            this.mInstrumentationPackageName = instrumentationInfoAsUser.packageName;
            this.mInstrumentationAppDir = instrumentationInfoAsUser.sourceDir;
            this.mInstrumentationSplitAppDirs = instrumentationInfoAsUser.splitSourceDirs;
            this.mInstrumentationLibDir = getInstrumentationLibrary(appBindData.appInfo, instrumentationInfoAsUser);
            this.mInstrumentedAppDir = appBindData.info.getAppDir();
            this.mInstrumentedSplitAppDirs = appBindData.info.getSplitAppDirs();
            this.mInstrumentedLibDir = appBindData.info.getLibDir();
            return instrumentationInfoAsUser;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void initInstrumentation(InstrumentationInfo instrumentationInfo, AppBindData appBindData, ContextImpl contextImpl) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(instrumentationInfo.packageName, 0L, UserHandle.myUserId());
        } catch (RemoteException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            applicationInfo = new ApplicationInfo();
        }
        ApplicationInfo applicationInfo2 = applicationInfo;
        instrumentationInfo.copyTo(applicationInfo2);
        applicationInfo2.initForUser(UserHandle.myUserId());
        ContextImpl createAppContext = appBindData.isSdkInSandbox ? contextImpl : ContextImpl.createAppContext(this, getPackageInfo(applicationInfo2, appBindData.compatInfo, contextImpl.getClassLoader(), false, true, false), contextImpl.getOpPackageName());
        try {
            this.mInstrumentation = (Instrumentation) createAppContext.getClassLoader().loadClass(appBindData.instrumentationName.getClassName()).newInstance();
            this.mInstrumentation.init(this, createAppContext, contextImpl, new ComponentName(instrumentationInfo.packageName, instrumentationInfo.name), appBindData.instrumentationWatcher, appBindData.instrumentationUiAutomationConnection);
            if (this.mProfiler.profileFile != null && !instrumentationInfo.handleProfiling && this.mProfiler.profileFd == null) {
                this.mProfiler.handlingProfiling = true;
                File file = new File(this.mProfiler.profileFile);
                file.getParentFile().mkdirs();
                Debug.startMethodTracing(file.toString(), 8388608);
            }
            if (instrumentationInfo.packageName != null) {
                VMDebug.addApplication(instrumentationInfo.packageName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate instrumentation " + appBindData.instrumentationName + ": " + e.toString(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishInstrumentationWithoutRestart() {
        LoadedApk loadedApk = getApplication().mLoadedApk;
        String str = this.mInstrumentationPackageName;
        if (str != null && loadedApk != null && !str.equals(loadedApk.mPackageName)) {
            VMDebug.removeApplication(this.mInstrumentationPackageName);
        }
        this.mInstrumentation.onDestroy();
        this.mInstrumentationPackageName = null;
        this.mInstrumentationAppDir = null;
        this.mInstrumentationSplitAppDirs = null;
        this.mInstrumentationLibDir = null;
        this.mInstrumentedAppDir = null;
        this.mInstrumentedSplitAppDirs = null;
        this.mInstrumentedLibDir = null;
        this.mInstrumentingWithoutRestart = false;
    }

    final void finishInstrumentation(int i, Bundle bundle) {
        IActivityManager service = ActivityManager.getService();
        if (this.mProfiler.profileFile != null && this.mProfiler.handlingProfiling && this.mProfiler.profileFd == null) {
            Debug.stopMethodTracing();
        }
        try {
            service.finishInstrumentation(this.mAppThread, i, bundle);
            if (this.mInstrumentingWithoutRestart) {
                sendMessage(171, null);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void installContentProviders(Context context, List<ProviderInfo> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<ProviderInfo> it = list.iterator();
        while (it.hasNext()) {
            ActivityThread activityThread = this;
            Context context2 = context;
            ContentProviderHolder installProvider = activityThread.installProvider(context2, null, it.next(), false, true, true);
            if (installProvider != null) {
                installProvider.noReleaseNeeded = true;
                arrayList.add(installProvider);
            }
            this = activityThread;
            context = context2;
        }
        try {
            ActivityManager.getService().publishContentProviders(this.getApplicationThread(), arrayList);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x007d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.IContentProvider acquireProvider(android.content.Context r11, java.lang.String r12, int r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.acquireProvider(android.content.Context, java.lang.String, int, boolean):android.content.IContentProvider");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ProviderKey getGetProviderKey(String str, int i) {
        ProviderKey computeIfAbsent;
        ProviderKey providerKey = new ProviderKey(str, i);
        synchronized (this.mGetProviderKeys) {
            computeIfAbsent = this.mGetProviderKeys.computeIfAbsent(providerKey, new Function() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ActivityThread.lambda$getGetProviderKey$5((ActivityThread.ProviderKey) obj);
                }
            });
        }
        return computeIfAbsent;
    }

    private final void incProviderRefLocked(ProviderRefCount providerRefCount, boolean z) {
        int i = 0;
        try {
            if (z) {
                providerRefCount.stableCount++;
                if (providerRefCount.stableCount != 1) {
                    return;
                }
                if (providerRefCount.removePending) {
                    providerRefCount.removePending = false;
                    this.mH.removeMessages(131, providerRefCount);
                    i = -1;
                }
                ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 1, i);
            } else {
                providerRefCount.unstableCount++;
                if (providerRefCount.unstableCount != 1) {
                    return;
                }
                if (providerRefCount.removePending) {
                    providerRefCount.removePending = false;
                    this.mH.removeMessages(131, providerRefCount);
                    return;
                }
                ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 0, 1);
            }
        } catch (RemoteException unused) {
        }
    }

    public final IContentProvider acquireExistingProvider(Context context, String str, int i, boolean z) {
        synchronized (this.mProviderMap) {
            ProviderClientRecord providerClientRecord = this.mProviderMap.get(new ProviderKey(str, i));
            if (providerClientRecord == null) {
                return null;
            }
            IContentProvider iContentProvider = providerClientRecord.mProvider;
            IBinder asBinder = iContentProvider.asBinder();
            if (!asBinder.isBinderAlive()) {
                Log.i(TAG, "Acquiring provider " + str + " for user " + i + ": existing object's process dead");
                handleUnstableProviderDiedLocked(asBinder, true);
                return null;
            }
            ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(asBinder);
            if (providerRefCount != null) {
                incProviderRefLocked(providerRefCount, z);
            }
            return iContentProvider;
        }
    }

    public final boolean releaseProvider(IContentProvider iContentProvider, boolean z) {
        if (iContentProvider == null) {
            return false;
        }
        IBinder asBinder = iContentProvider.asBinder();
        synchronized (this.mProviderMap) {
            ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(asBinder);
            if (providerRefCount == null) {
                return false;
            }
            if (z) {
                if (providerRefCount.stableCount == 0) {
                    return false;
                }
                providerRefCount.stableCount--;
                if (providerRefCount.stableCount == 0) {
                    r1 = providerRefCount.unstableCount == 0 ? 1 : 0;
                    try {
                        ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, -1, r1);
                    } catch (RemoteException unused) {
                    }
                }
            } else {
                if (providerRefCount.unstableCount == 0) {
                    return false;
                }
                providerRefCount.unstableCount--;
                if (providerRefCount.unstableCount == 0) {
                    int i = providerRefCount.stableCount == 0 ? 1 : 0;
                    if (i == 0) {
                        try {
                            ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 0, -1);
                        } catch (RemoteException unused2) {
                        }
                    }
                    r1 = i;
                }
            }
            if (r1 != 0) {
                if (!providerRefCount.removePending) {
                    providerRefCount.removePending = true;
                    this.mH.sendMessageDelayed(this.mH.obtainMessage(131, providerRefCount), 1000L);
                } else {
                    Slog.w(TAG, "Duplicate remove pending of provider " + providerRefCount.holder.info.name);
                }
            }
            return true;
        }
    }

    final void completeRemoveProvider(ProviderRefCount providerRefCount) {
        synchronized (this.mProviderMap) {
            if (providerRefCount.removePending) {
                providerRefCount.removePending = false;
                IBinder asBinder = providerRefCount.holder.provider.asBinder();
                if (this.mProviderRefCountMap.get(asBinder) == providerRefCount) {
                    this.mProviderRefCountMap.remove(asBinder);
                }
                for (int size = this.mProviderMap.size() - 1; size >= 0; size--) {
                    if (this.mProviderMap.valueAt(size).mProvider.asBinder() == asBinder) {
                        this.mProviderMap.removeAt(size);
                    }
                }
                try {
                    ActivityManager.getService().removeContentProvider(providerRefCount.holder.connection, false);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    final void handleUnstableProviderDied(IBinder iBinder, boolean z) {
        synchronized (this.mProviderMap) {
            handleUnstableProviderDiedLocked(iBinder, z);
        }
    }

    final void handleUnstableProviderDiedLocked(IBinder iBinder, boolean z) {
        ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(iBinder);
        if (providerRefCount != null) {
            this.mProviderRefCountMap.remove(iBinder);
            for (int size = this.mProviderMap.size() - 1; size >= 0; size--) {
                ProviderClientRecord valueAt = this.mProviderMap.valueAt(size);
                if (valueAt != null && valueAt.mProvider.asBinder() == iBinder) {
                    Slog.i(TAG, "Removing dead content provider:" + valueAt.mProvider.toString());
                    this.mProviderMap.removeAt(size);
                }
            }
            if (z) {
                try {
                    ActivityManager.getService().unstableProviderDied(providerRefCount.holder.connection);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    final void appNotRespondingViaProvider(IBinder iBinder) {
        synchronized (this.mProviderMap) {
            ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(iBinder);
            if (providerRefCount != null) {
                try {
                    ActivityManager.getService().appNotRespondingViaProvider(providerRefCount.holder.connection);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private ProviderClientRecord installProviderAuthoritiesLocked(IContentProvider iContentProvider, ContentProvider contentProvider, ContentProviderHolder contentProviderHolder) {
        String[] split = contentProviderHolder.info.authority.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        int userId = UserHandle.getUserId(contentProviderHolder.info.applicationInfo.uid);
        if (iContentProvider != null) {
            for (String str : split) {
                str.hashCode();
                switch (str) {
                    case "com.android.contacts":
                    case "com.android.calendar":
                    case "call_log":
                    case "call_log_shadow":
                    case "telephony":
                    case "downloads":
                    case "com.android.blockednumber":
                        Binder.allowBlocking(iContentProvider.asBinder());
                        break;
                }
            }
        }
        ProviderClientRecord providerClientRecord = new ProviderClientRecord(split, iContentProvider, contentProvider, contentProviderHolder);
        for (String str2 : split) {
            ProviderKey providerKey = new ProviderKey(str2, userId);
            if (this.mProviderMap.get(providerKey) != null) {
                Slog.w(TAG, "Content provider " + providerClientRecord.mHolder.info.name + " already published as " + str2);
            } else {
                this.mProviderMap.put(providerKey, providerClientRecord);
            }
        }
        return providerClientRecord;
    }

    private ContentProviderHolder installProvider(Context context, ContentProviderHolder contentProviderHolder, ProviderInfo providerInfo, boolean z, boolean z2, boolean z3) {
        IContentProvider iContentProvider;
        ContentProviderHolder contentProviderHolder2;
        ContentProvider contentProvider = null;
        if (contentProviderHolder == null || contentProviderHolder.provider == null) {
            if (z) {
                Slog.d(TAG, "Loading provider " + providerInfo.authority + ": " + providerInfo.name);
            }
            ApplicationInfo applicationInfo = providerInfo.applicationInfo;
            if (context == null || !context.getPackageName().equals(applicationInfo.packageName)) {
                Application application = this.mInitialApplication;
                if (application == null || !application.getPackageName().equals(applicationInfo.packageName)) {
                    if (context != null) {
                        try {
                            context = context.createPackageContext(applicationInfo.packageName, 1);
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                    }
                    context = null;
                } else {
                    context = this.mInitialApplication;
                }
            }
            if (context == null) {
                Slog.w(TAG, "Unable to get context for package " + applicationInfo.packageName + " while loading content provider " + providerInfo.name);
                return null;
            }
            if (providerInfo.splitName != null) {
                try {
                    context = context.createContextForSplit(providerInfo.splitName);
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (providerInfo.attributionTags != null && providerInfo.attributionTags.length > 0) {
                context = context.createAttributionContext(providerInfo.attributionTags[0]);
            }
            try {
                ClassLoader classLoader = context.getClassLoader();
                LoadedApk peekPackageInfo = peekPackageInfo(applicationInfo.packageName, true);
                if (peekPackageInfo == null) {
                    peekPackageInfo = getSystemContext().mPackageInfo;
                }
                ContentProvider instantiateProvider = peekPackageInfo.getAppFactory().instantiateProvider(classLoader, providerInfo.name);
                IContentProvider iContentProvider2 = instantiateProvider.getIContentProvider();
                if (iContentProvider2 == null) {
                    Slog.e(TAG, "Failed to instantiate class " + providerInfo.name + " from sourceDir " + providerInfo.applicationInfo.sourceDir);
                    return null;
                }
                instantiateProvider.attachInfo(context, providerInfo);
                contentProvider = instantiateProvider;
                iContentProvider = iContentProvider2;
            } catch (Exception e2) {
                if (this.mInstrumentation.onException(null, e2)) {
                    return null;
                }
                throw new RuntimeException("Unable to get provider " + providerInfo.name + ": " + e2.toString(), e2);
            }
        } else {
            iContentProvider = contentProviderHolder.provider;
        }
        synchronized (this.mProviderMap) {
            IBinder asBinder = iContentProvider.asBinder();
            if (contentProvider != null) {
                ComponentName componentName = new ComponentName(providerInfo.packageName, providerInfo.name);
                ProviderClientRecord providerClientRecord = this.mLocalProvidersByName.get(componentName);
                if (providerClientRecord != null) {
                    IContentProvider iContentProvider3 = providerClientRecord.mProvider;
                } else {
                    ContentProviderHolder contentProviderHolder3 = new ContentProviderHolder(providerInfo);
                    contentProviderHolder3.provider = iContentProvider;
                    contentProviderHolder3.noReleaseNeeded = true;
                    providerClientRecord = installProviderAuthoritiesLocked(iContentProvider, contentProvider, contentProviderHolder3);
                    this.mLocalProviders.put(asBinder, providerClientRecord);
                    this.mLocalProvidersByName.put(componentName, providerClientRecord);
                }
                contentProviderHolder2 = providerClientRecord.mHolder;
            } else {
                ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(asBinder);
                if (providerRefCount == null) {
                    ProviderClientRecord installProviderAuthoritiesLocked = installProviderAuthoritiesLocked(iContentProvider, contentProvider, contentProviderHolder);
                    if (z2) {
                        providerRefCount = new ProviderRefCount(contentProviderHolder, installProviderAuthoritiesLocked, 1000, 1000);
                    } else if (z3) {
                        providerRefCount = new ProviderRefCount(contentProviderHolder, installProviderAuthoritiesLocked, 1, 0);
                    } else {
                        providerRefCount = new ProviderRefCount(contentProviderHolder, installProviderAuthoritiesLocked, 0, 1);
                    }
                    this.mProviderRefCountMap.put(asBinder, providerRefCount);
                } else if (!z2) {
                    incProviderRefLocked(providerRefCount, z3);
                    try {
                        ActivityManager.getService().removeContentProvider(contentProviderHolder.connection, z3);
                    } catch (RemoteException unused2) {
                    }
                }
                contentProviderHolder2 = providerRefCount.holder;
            }
        }
        return contentProviderHolder2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRunIsolatedEntryPoint(String str, String[] strArr) {
        try {
            Class.forName(str).getMethod("main", String[].class).invoke(null, strArr);
            System.exit(0);
        } catch (ReflectiveOperationException e) {
            throw new AndroidRuntimeException("runIsolatedEntryPoint failed", e);
        }
    }

    private void attach(boolean z, long j) {
        sCurrentActivityThread = this;
        this.mConfigurationController = new ConfigurationController(this);
        this.mSystemThread = z;
        this.mStartSeq = j;
        this.mDdmSyncStageUpdater.next(DdmSyncState.Stage.Attach);
        if (!z) {
            DdmHandleAppName.setAppName("<pre-initialized>", UserHandle.myUserId());
            RuntimeInit.setApplicationObject(this.mAppThread.asBinder());
            try {
                ActivityManager.getService().attachApplication(this.mAppThread, j);
                BinderInternal.addGcWatcher(new Runnable() { // from class: android.app.ActivityThread.7
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ActivityThread.this.mSomeActivitiesChanged) {
                            Runtime runtime = Runtime.getRuntime();
                            if (runtime.totalMemory() - runtime.freeMemory() > (runtime.maxMemory() * 3) / 4) {
                                ActivityThread.this.mSomeActivitiesChanged = false;
                                try {
                                    ActivityTaskManager.getService().releaseSomeActivities(ActivityThread.this.mAppThread);
                                } catch (RemoteException e) {
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                        }
                    }
                });
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            DdmHandleAppName.setAppName("system_process", UserHandle.myUserId());
            initializeSystemThread(this);
        }
        ViewRootImpl.addConfigCallback(new ViewRootImpl.ConfigChangedCallback() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda5
            @Override // android.view.ViewRootImpl.ConfigChangedCallback
            public final void onConfigurationChanged(Configuration configuration) {
                ActivityThread.this.lambda$attach$6(configuration);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$6(Configuration configuration) {
        synchronized (this.mResourcesManager) {
            if (this.mResourcesManager.applyConfigurationToResources(configuration, null)) {
                this.mConfigurationController.updateLocaleListFromAppContext(this.mInitialApplication.getApplicationContext());
                Configuration updatePendingConfiguration = this.mConfigurationController.updatePendingConfiguration(configuration);
                if (updatePendingConfiguration != null) {
                    sendMessage(118, configuration);
                    this.mPendingConfiguration = updatePendingConfiguration;
                }
            }
        }
    }

    private static void initializeSystemThread(ActivityThread activityThread) {
        if (activityThread.mSystemThread) {
            try {
                Instrumentation instrumentation = new Instrumentation();
                activityThread.mInstrumentation = instrumentation;
                instrumentation.basicInit(activityThread);
                Application makeApplicationInner = ContextImpl.createAppContext(activityThread, activityThread.getSystemContext().mPackageInfo).mPackageInfo.makeApplicationInner(true, null);
                activityThread.mInitialApplication = makeApplicationInner;
                makeApplicationInner.onCreate();
            } catch (Exception e) {
                throw new RuntimeException("Unable to instantiate Application():" + e, e);
            }
        }
    }

    public static ActivityThread systemMain() {
        ThreadedRenderer.initForSystemProcess();
        ActivityThread activityThread = new ActivityThread();
        activityThread.attach(true, 0L);
        return activityThread;
    }

    public static void updateHttpProxy(Context context) {
        Proxy.setHttpProxyConfiguration(((ConnectivityManager) context.getSystemService(ConnectivityManager.class)).getDefaultProxy());
    }

    public final void installSystemProviders(List<ProviderInfo> list) {
        if (list != null) {
            installContentProviders(this.mInitialApplication, list);
        }
    }

    Bundle getCoreSettings() {
        Bundle bundle;
        synchronized (this.mCoreSettingsLock) {
            bundle = this.mCoreSettings;
        }
        return bundle;
    }

    public int getIntCoreSetting(String str, int i) {
        synchronized (this.mCoreSettingsLock) {
            Bundle bundle = this.mCoreSettings;
            if (bundle == null) {
                return i;
            }
            return bundle.getInt(str, i);
        }
    }

    public String getStringCoreSetting(String str, String str2) {
        synchronized (this.mCoreSettingsLock) {
            Bundle bundle = this.mCoreSettings;
            if (bundle == null) {
                return str2;
            }
            return bundle.getString(str, str2);
        }
    }

    float getFloatCoreSetting(String str, float f) {
        synchronized (this.mCoreSettingsLock) {
            Bundle bundle = this.mCoreSettings;
            if (bundle == null) {
                return f;
            }
            return bundle.getFloat(str, f);
        }
    }

    private static class AndroidOs extends ForwardingOs {
        public static void install() {
            libcore.io.Os os;
            do {
                os = libcore.io.Os.getDefault();
            } while (!libcore.io.Os.compareAndSetDefault(os, new AndroidOs(os)));
        }

        private AndroidOs(libcore.io.Os os) {
            super(os);
        }

        private FileDescriptor openDeprecatedDataPath(String str, int i) throws ErrnoException {
            Uri translateDeprecatedDataPath = ContentResolver.translateDeprecatedDataPath(str);
            Log.v(ActivityThread.TAG, "Redirecting " + str + " to " + translateDeprecatedDataPath);
            ContentResolver contentResolver = ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(contentResolver.openFileDescriptor(translateDeprecatedDataPath, FileUtils.translateModePosixToString(i)).detachFd());
                return fileDescriptor;
            } catch (FileNotFoundException e) {
                throw new ErrnoException(e.getMessage(), OsConstants.ENOENT);
            } catch (SecurityException e2) {
                throw new ErrnoException(e2.getMessage(), OsConstants.EACCES);
            }
        }

        private void deleteDeprecatedDataPath(String str) throws ErrnoException {
            Uri translateDeprecatedDataPath = ContentResolver.translateDeprecatedDataPath(str);
            Log.v(ActivityThread.TAG, "Redirecting " + str + " to " + translateDeprecatedDataPath);
            try {
                if (ActivityThread.currentActivityThread().getApplication().getContentResolver().delete(translateDeprecatedDataPath, null, null) != 0) {
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                throw new ErrnoException(e.getMessage(), OsConstants.ENOENT);
            } catch (SecurityException e2) {
                throw new ErrnoException(e2.getMessage(), OsConstants.EACCES);
            }
        }

        public boolean access(String str, int i) throws ErrnoException {
            if (str != null && str.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                IoUtils.closeQuietly(openDeprecatedDataPath(str, FileUtils.translateModeAccessToPosix(i)));
                return true;
            }
            return super.access(str, i);
        }

        public FileDescriptor open(String str, int i, int i2) throws ErrnoException {
            if (str != null && str.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                return openDeprecatedDataPath(str, i2);
            }
            return super.open(str, i, i2);
        }

        public StructStat stat(String str) throws ErrnoException {
            if (str != null && str.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                FileDescriptor openDeprecatedDataPath = openDeprecatedDataPath(str, OsConstants.O_RDONLY);
                try {
                    return Os.fstat(openDeprecatedDataPath);
                } finally {
                    IoUtils.closeQuietly(openDeprecatedDataPath);
                }
            }
            return super.stat(str);
        }

        public void unlink(String str) throws ErrnoException {
            if (str != null && str.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                deleteDeprecatedDataPath(str);
            } else {
                super.unlink(str);
            }
        }

        public void remove(String str) throws ErrnoException {
            if (str != null && str.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
                deleteDeprecatedDataPath(str);
            } else {
                super.remove(str);
            }
        }

        public void rename(String str, String str2) throws ErrnoException {
            try {
                super.rename(str, str2);
            } catch (ErrnoException e) {
                if (e.errno == OsConstants.EXDEV && str.startsWith("/storage/emulated") && str2.startsWith("/storage/emulated")) {
                    Log.v(ActivityThread.TAG, "Recovering failed rename " + str + " to " + str2);
                    try {
                        Files.move(new File(str).toPath(), new File(str2).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        return;
                    } catch (IOException e2) {
                        Log.e(ActivityThread.TAG, "Rename recovery failed ", e2);
                        throw e;
                    }
                }
                throw e;
            }
        }
    }

    private static void setConscryptValidator() {
        Log.d(TAG, "setConscryptValidator");
        for (Provider provider : Security.getProviders()) {
            if (provider instanceof OpenSSLProvider) {
                Log.d(TAG, "setConscryptValidator - put");
                provider.put("CertPathValidator.PKIX", "android.sec.enterprise.certificate.DelegatingCertPathValidator");
                provider.put("CertPathValidator.PKIX ImplementedIn", ExifInterface.TAG_SOFTWARE);
                provider.put("CertPathValidator.PKIX ValidationAlgorithm", "RFC3280");
            }
        }
    }

    public static void main(String[] strArr) {
        Trace.traceBegin(64L, "ActivityThreadMain");
        AndroidOs.install();
        CloseGuard.setEnabled(false);
        Environment.initForCurrentUser();
        TrustedCertificateStore.setDefaultUserDirectory(Environment.getUserConfigDirectory(UserHandle.myUserId()));
        initializeMainlineModules();
        Looper.prepareMainLooper();
        Process.setArgV0("<pre-initialized>");
        long j = 0;
        if (strArr != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                String str = strArr[length];
                if (str != null && str.startsWith(PROC_START_SEQ_IDENT)) {
                    j = Long.parseLong(strArr[length].substring(4));
                }
            }
        }
        addUcmKeyStoreProviderForAppContext();
        setConscryptValidator();
        ActivityThread activityThread = new ActivityThread();
        activityThread.attach(false, j);
        if (sMainThreadHandler == null) {
            sMainThreadHandler = activityThread.getHandler();
        }
        Trace.traceEnd(64L);
        Looper.loop();
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

    public static void initializeMainlineModules() {
        TelephonyFrameworkInitializer.setTelephonyServiceManager(new TelephonyServiceManager());
        StatsFrameworkInitializer.setStatsServiceManager(new StatsServiceManager());
        MediaFrameworkPlatformInitializer.setMediaServiceManager(new MediaServiceManager());
        MediaFrameworkInitializer.setMediaServiceManager(new MediaServiceManager());
        BluetoothFrameworkInitializer.setBluetoothServiceManager(new BluetoothServiceManager());
        BluetoothFrameworkInitializer.setBinderCallsStatsInitializer(new Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BinderCallsStats.startForBluetooth((Context) obj);
            }
        });
        NfcFrameworkInitializer.setNfcServiceManager(new NfcServiceManager());
        DeviceConfigInitializer.setDeviceConfigServiceManager(new DeviceConfigServiceManager());
        SeFrameworkInitializer.setSeServiceManager(new SeServiceManager());
        if (android.server.Flags.telemetryApisService()) {
            ProfilingFrameworkInitializer.setProfilingServiceManager(new ProfilingServiceManager());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgePendingResources() {
        Trace.traceBegin(64L, "purgePendingResources");
        nPurgePendingResources();
        Trace.traceEnd(64L);
    }

    public static boolean isProtectedComponent(ActivityInfo activityInfo) {
        return isProtectedComponent(activityInfo, activityInfo.permission);
    }

    public static boolean isProtectedComponent(ServiceInfo serviceInfo) {
        return isProtectedComponent(serviceInfo, serviceInfo.permission);
    }

    private static boolean isProtectedComponent(ComponentInfo componentInfo, String str) {
        if (!StrictMode.vmUnsafeIntentLaunchEnabled()) {
            return false;
        }
        if (!componentInfo.exported) {
            return true;
        }
        if (str != null) {
            try {
                PermissionInfo permissionInfo = getPermissionManager().getPermissionInfo(str, currentOpPackageName(), 0);
                if (permissionInfo != null) {
                    if (permissionInfo.getProtection() == 2) {
                        return true;
                    }
                }
            } catch (RemoteException unused) {
            }
        }
        return false;
    }

    public static boolean isProtectedBroadcast(Intent intent) {
        if (!StrictMode.vmUnsafeIntentLaunchEnabled()) {
            return false;
        }
        try {
            return getPackageManager().isProtectedBroadcast(intent.getAction());
        } catch (RemoteException unused) {
            return false;
        }
    }

    void addApplication(Application application) {
        this.mAllApplications.add(application);
        VMDebug.addApplication(application.mLoadedApk.mPackageName);
    }

    @Override // android.app.ActivityThreadInternal
    public boolean isInDensityCompatMode() {
        return this.mDensityCompatMode;
    }

    public CompatibilityInfo getCompatInfo() {
        return this.mCompatibilityInfo;
    }

    private void updateDefaultNavigationBarColor() {
        Iterator<Map.Entry<IBinder, ActivityClientRecord>> it = this.mActivities.entrySet().iterator();
        while (it.hasNext()) {
            ActivityClientRecord value = it.next().getValue();
            if (value.window instanceof PhoneWindow) {
                ((PhoneWindow) value.window).setSettingsNavigationBarColor(this.mCoreSettings.getInt("navigationbar_current_color", ((PhoneWindow) value.window).getDeviceDefaultNavigationBarColor()));
                ((PhoneWindow) value.window).updateDefaultNavigationBarColor();
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleCoreStatesChanged(Bundle bundle) {
        int updateFrom = MultiWindowCoreState.getInstance().updateFrom(bundle);
        if (updateFrom != 0) {
            notifyMultiWindowCoreStateChanges(updateFrom);
        }
    }

    public void registerMultiWindowCoreStateListener(MultiWindowCoreState.MultiWindowCoreStateListener multiWindowCoreStateListener) {
        synchronized (this.mMultiWindowCoreStateListeners) {
            this.mMultiWindowCoreStateListeners.add(multiWindowCoreStateListener);
        }
    }

    public void unregisterMultiWindowCoreStateListener(MultiWindowCoreState.MultiWindowCoreStateListener multiWindowCoreStateListener) {
        synchronized (this.mMultiWindowCoreStateListeners) {
            this.mMultiWindowCoreStateListeners.remove(multiWindowCoreStateListener);
        }
    }

    private void notifyMultiWindowCoreStateChanges(int i) {
        synchronized (this.mMultiWindowCoreStateListeners) {
            Iterator<MultiWindowCoreState.MultiWindowCoreStateListener> it = this.mMultiWindowCoreStateListeners.iterator();
            while (it.hasNext()) {
                it.next().onMultiWindowCoreStateChanged(i);
            }
        }
    }

    public static boolean isFixedAppContextDisplay() {
        return sFixedAppContextDisplay;
    }

    public void dumpProcessAdjustmentInfo(PrintWriter printWriter) {
        printWriter.print("  ProcessConfig=");
        printWriter.println(getConfiguration());
        Application application = this.mInitialApplication;
        if (application != null) {
            printWriter.println("  Application ResourcesConfig=" + application.getResources().getConfiguration());
        }
    }

    private static void addUcmKeyStoreProviderForAppContext() {
        if (SystemProperties.getBoolean(KnoxUcmKeyStoreProvider.PROPERTY_UCM_CRYPTO, false)) {
            UcmKeyStoreHelper.addUcmProvider();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x003c A[Catch: Exception -> 0x005d, TRY_LEAVE, TryCatch #0 {Exception -> 0x005d, blocks: (B:16:0x000b, B:18:0x0027, B:5:0x002e, B:7:0x003c), top: B:15:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void getProfileSizeOfApp(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "mSecIpmManager setProfileLength "
            java.lang.String r1 = "/data/misc/profiles/cur/0/"
            boolean r2 = com.samsung.android.rune.CoreRune.SYSPERF_ACTIVE_APP_ADCP_ENABLE
            if (r2 == 0) goto L61
            if (r6 == 0) goto L2c
            java.io.File r2 = new java.io.File     // Catch: java.lang.Exception -> L5d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5d
            r3.<init>(r1)     // Catch: java.lang.Exception -> L5d
            r3.append(r6)     // Catch: java.lang.Exception -> L5d
            java.lang.String r1 = "/primary.prof"
            r3.append(r1)     // Catch: java.lang.Exception -> L5d
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Exception -> L5d
            r2.<init>(r1)     // Catch: java.lang.Exception -> L5d
            boolean r1 = r2.exists()     // Catch: java.lang.Exception -> L5d
            if (r1 == 0) goto L2c
            long r1 = r2.length()     // Catch: java.lang.Exception -> L5d
            goto L2e
        L2c:
            r1 = 0
        L2e:
            android.app.Application r5 = r5.getApplication()     // Catch: java.lang.Exception -> L5d
            java.lang.String r3 = "PkgPredictorService"
            java.lang.Object r5 = r5.getSystemService(r3)     // Catch: java.lang.Exception -> L5d
            com.samsung.android.ipm.SecIpmManager r5 = (com.samsung.android.ipm.SecIpmManager) r5     // Catch: java.lang.Exception -> L5d
            if (r5 == 0) goto L61
            java.lang.String r3 = "[secipm]"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5d
            r4.<init>(r0)     // Catch: java.lang.Exception -> L5d
            r4.append(r6)     // Catch: java.lang.Exception -> L5d
            java.lang.String r0 = " profile:"
            r4.append(r0)     // Catch: java.lang.Exception -> L5d
            r4.append(r1)     // Catch: java.lang.Exception -> L5d
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Exception -> L5d
            android.util.Slog.d(r3, r0)     // Catch: java.lang.Exception -> L5d
            int r0 = android.os.Process.myUid()     // Catch: java.lang.Exception -> L5d
            r5.setProfileLength(r6, r0, r1)     // Catch: java.lang.Exception -> L5d
            return
        L5d:
            r5 = move-exception
            r5.printStackTrace()
        L61:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ActivityThread.getProfileSizeOfApp(java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFlingerFlag(String str, boolean z) {
        Choreographer mainThreadInstance = Choreographer.getMainThreadInstance();
        if (mainThreadInstance != null) {
            mainThreadInstance.setIsFg(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewVisibleFlag(boolean z) {
        Choreographer mainThreadInstance = Choreographer.getMainThreadInstance();
        if (mainThreadInstance != null) {
            mainThreadInstance.setViewVisible(z);
        }
    }

    private void performReleaseActivityFocusIfNeeded(IBinder iBinder) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null || activityClientRecord.activity == null) {
            return;
        }
        activityClientRecord.activity.releaseActivityFocusIfNeeded();
    }

    public boolean mayStayActivityFocus(IBinder iBinder) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        return (activityClientRecord == null || activityClientRecord.paused || activityClientRecord.stopped) ? false : true;
    }

    private boolean hasResumedPopOver() {
        if (this.mActivities.isEmpty()) {
            return false;
        }
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            ActivityClientRecord valueAt = this.mActivities.valueAt(size);
            if (valueAt.getLifecycleState() == 3 && valueAt.overrideConfig != null && valueAt.overrideConfig.windowConfiguration.isPopOver()) {
                return true;
            }
        }
        return false;
    }

    private void setActivityCurrentConfigIfPossible(Activity activity, Configuration configuration) {
        Window window = activity.getWindow();
        if (window instanceof PhoneWindow) {
            ((PhoneWindow) window).setActivityCurrentConfig(configuration);
        }
    }

    private void handleDexTaskDockingChangeIfNeeded(Activity activity, Configuration configuration) {
        activity.onDexTaskDockingChanged(configuration.windowConfiguration.getDexTaskDockingState());
    }
}
