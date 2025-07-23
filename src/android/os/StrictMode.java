package android.os;

import android.animation.ValueAnimator;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IBackgroundActivityLaunchCallback;
import android.app.IUnsafeIntentStrictModeCallback;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.INetworkManagementService;
import android.os.MessageQueue;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.storage.IStorageManager;
import android.os.strictmode.BackgroundActivityLaunchViolation;
import android.os.strictmode.ContentUriWithoutPermissionViolation;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.os.strictmode.CustomViolation;
import android.os.strictmode.DiskReadViolation;
import android.os.strictmode.DiskWriteViolation;
import android.os.strictmode.ExplicitGcViolation;
import android.os.strictmode.FileUriExposedViolation;
import android.os.strictmode.ImplicitDirectBootViolation;
import android.os.strictmode.IncorrectContextUseViolation;
import android.os.strictmode.InstanceCountViolation;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.os.strictmode.LeakedClosableViolation;
import android.os.strictmode.NetworkViolation;
import android.os.strictmode.NonSdkApiUsedViolation;
import android.os.strictmode.ResourceMismatchViolation;
import android.os.strictmode.ServiceConnectionLeakedViolation;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.os.strictmode.UnbufferedIoViolation;
import android.os.strictmode.UnsafeIntentLaunchViolation;
import android.os.strictmode.UntaggedSocketViolation;
import android.os.strictmode.Violation;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Printer;
import android.util.Singleton;
import android.util.SparseLongArray;
import android.view.IWindowManager;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.RuntimeInit;
import com.android.internal.util.FastPrintWriter;
import com.samsung.android.media.AudioParameter;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class StrictMode {
    private static final String CLEARTEXT_PROPERTY = "persist.sys.strictmode.clear";
    static final long DETECT_EXPLICIT_GC = 3400644;
    private static final int DETECT_THREAD_ALL = 65535;
    private static final int DETECT_THREAD_CUSTOM = 8;
    private static final int DETECT_THREAD_DISK_READ = 2;
    private static final int DETECT_THREAD_DISK_WRITE = 1;
    private static final int DETECT_THREAD_EXPLICIT_GC = 64;
    private static final int DETECT_THREAD_NETWORK = 4;
    private static final int DETECT_THREAD_RESOURCE_MISMATCH = 16;
    private static final int DETECT_THREAD_UNBUFFERED_IO = 32;
    private static final int DETECT_VM_ACTIVITY_LEAKS = 4;
    private static final int DETECT_VM_ALL = 65535;
    private static final int DETECT_VM_BACKGROUND_ACTIVITY_LAUNCH_ABORTED = 16384;
    private static final int DETECT_VM_CLEARTEXT_NETWORK = 64;
    private static final int DETECT_VM_CLOSABLE_LEAKS = 2;
    private static final int DETECT_VM_CONTENT_URI_WITHOUT_PERMISSION = 128;
    private static final int DETECT_VM_CREDENTIAL_PROTECTED_WHILE_LOCKED = 2048;
    private static final int DETECT_VM_CURSOR_LEAKS = 1;
    private static final int DETECT_VM_FILE_URI_EXPOSURE = 32;
    private static final int DETECT_VM_IMPLICIT_DIRECT_BOOT = 1024;
    private static final int DETECT_VM_INCORRECT_CONTEXT_USE = 4096;
    private static final int DETECT_VM_INSTANCE_LEAKS = 8;
    private static final int DETECT_VM_NON_SDK_API_USAGE = 512;
    private static final int DETECT_VM_REGISTRATION_LEAKS = 16;
    private static final int DETECT_VM_UNSAFE_INTENT_LAUNCH = 8192;
    private static final int DETECT_VM_UNTAGGED_SOCKET = 256;
    private static final boolean DISABLE = false;
    public static final String DISABLE_PROPERTY = "persist.sys.strictmode.disable";
    private static final ViolationLogger LOGCAT_LOGGER;
    private static final int MAX_OFFENSES_PER_LOOP = 10;
    private static final int MAX_SPAN_TAGS = 20;
    private static final long MIN_DIALOG_INTERVAL_MS = 30000;
    private static final long MIN_DROPBOX_INTERVAL_MS = 3000;
    private static final long MIN_LOG_INTERVAL_MS = 1000;
    private static final long MIN_VM_INTERVAL_MS = 1000;
    public static final int NETWORK_POLICY_ACCEPT = 0;
    public static final int NETWORK_POLICY_LOG = 1;
    public static final int NETWORK_POLICY_REJECT = 2;
    private static final Span NO_OP_SPAN;
    public static final int PENALTY_ALL = -65536;
    public static final int PENALTY_DEATH = 268435456;
    public static final int PENALTY_DEATH_ON_CLEARTEXT_NETWORK = 16777216;
    public static final int PENALTY_DEATH_ON_FILE_URI_EXPOSURE = 8388608;
    public static final int PENALTY_DEATH_ON_NETWORK = 33554432;
    public static final int PENALTY_DIALOG = 536870912;
    public static final int PENALTY_DROPBOX = 67108864;
    public static final int PENALTY_FLASH = 134217728;
    public static final int PENALTY_GATHER = Integer.MIN_VALUE;
    public static final int PENALTY_LOG = 1073741824;
    private static final ThreadLocal<AndroidBlockGuardPolicy> THREAD_ANDROID_POLICY;
    private static final ThreadLocal<Handler> THREAD_HANDLER;
    public static final String VISUAL_PROPERTY = "persist.sys.strictmode.visual";
    private static final BlockGuard.VmPolicy VM_ANDROID_POLICY;
    private static final ThreadLocal<ArrayList<ViolationInfo>> gatheredViolations;
    private static volatile boolean sCeStorageUnlocked;
    private static final AtomicInteger sDropboxCallsInFlight;
    private static final HashMap<Class, Integer> sExpectedActivityInstanceCount;
    private static boolean sIsIdlerRegistered;
    private static long sLastInstanceCountCheckMillis;
    private static final HashMap<Integer, Long> sLastVmViolationTime;
    private static volatile ViolationLogger sLogger;
    private static final Consumer<String> sNonSdkApiUsageConsumer;
    private static final MessageQueue.IdleHandler sProcessIdleHandler;
    private static final SparseLongArray sRealLastVmViolationTime;
    private static volatile IStorageManager sStorageManager;
    private static final ThreadLocal<ThreadSpanState> sThisThreadSpanState;
    private static final ThreadLocal<Executor> sThreadViolationExecutor;
    private static final ThreadLocal<OnThreadViolationListener> sThreadViolationListener;
    private static volatile UnsafeIntentStrictModeCallback sUnsafeIntentCallback;
    private static Singleton<IWindowManager> sWindowManager;
    private static final ThreadLocal<ArrayList<ViolationInfo>> violationsBeingTimed;
    private static final String TAG = "StrictMode";
    private static final boolean LOG_V = Log.isLoggable(TAG, 2);
    private static final HashMap<Class, Integer> EMPTY_CLASS_LIMIT_MAP = new HashMap<>();
    private static volatile VmPolicy sVmPolicy = VmPolicy.LAX;

    public interface OnThreadViolationListener {
        void onThreadViolation(Violation violation);
    }

    public interface OnVmViolationListener {
        void onVmViolation(Violation violation);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ThreadPolicyMask {
    }

    public interface ViolationLogger {
        void log(ViolationInfo violationInfo);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VmPolicyMask {
    }

    public static int getThreadPolicyMask$ravenwood() {
        return 0;
    }

    private static void onBinderStrictModePolicyChange$ravenwood(int i) {
    }

    static {
        ViolationLogger violationLogger = new ViolationLogger() { // from class: android.os.StrictMode$$ExternalSyntheticLambda1
            @Override // android.os.StrictMode.ViolationLogger
            public final void log(StrictMode.ViolationInfo violationInfo) {
                StrictMode.lambda$static$0(violationInfo);
            }
        };
        LOGCAT_LOGGER = violationLogger;
        sLogger = violationLogger;
        sThreadViolationListener = new ThreadLocal<>();
        sThreadViolationExecutor = new ThreadLocal<>();
        sDropboxCallsInFlight = new AtomicInteger(0);
        sNonSdkApiUsageConsumer = new Consumer() { // from class: android.os.StrictMode$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StrictMode.onVmPolicyViolation(new NonSdkApiUsedViolation((String) obj));
            }
        };
        gatheredViolations = new ThreadLocal<ArrayList<ViolationInfo>>() { // from class: android.os.StrictMode.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public ArrayList<ViolationInfo> initialValue() {
                return null;
            }
        };
        violationsBeingTimed = new ThreadLocal<ArrayList<ViolationInfo>>() { // from class: android.os.StrictMode.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public ArrayList<ViolationInfo> initialValue() {
                return new ArrayList<>();
            }
        };
        THREAD_HANDLER = new ThreadLocal<Handler>() { // from class: android.os.StrictMode.3
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public Handler initialValue() {
                return new Handler();
            }
        };
        THREAD_ANDROID_POLICY = new ThreadLocal<AndroidBlockGuardPolicy>() { // from class: android.os.StrictMode.4
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public AndroidBlockGuardPolicy initialValue() {
                return new AndroidBlockGuardPolicy(0);
            }
        };
        VM_ANDROID_POLICY = new BlockGuard.VmPolicy() { // from class: android.os.StrictMode.5
            public void onPathAccess(String str) {
                if (str == null) {
                    return;
                }
                if (str.startsWith("/data/user/") || str.startsWith("/data/media/") || str.startsWith("/data/system_ce/") || str.startsWith("/data/misc_ce/") || str.startsWith("/data/vendor_ce/") || str.startsWith("/storage/emulated/")) {
                    int indexOf = str.indexOf(47, str.indexOf(47, 1) + 1) + 1;
                    int indexOf2 = str.indexOf(47, indexOf);
                    if (indexOf2 == -1) {
                        return;
                    }
                    try {
                        StrictMode.onCredentialProtectedPathAccess(str, Integer.parseInt(str.substring(indexOf, indexOf2)));
                        return;
                    } catch (NumberFormatException unused) {
                        return;
                    }
                }
                if (str.startsWith("/data/data/")) {
                    StrictMode.onCredentialProtectedPathAccess(str, 0);
                }
            }
        };
        sLastInstanceCountCheckMillis = 0L;
        sIsIdlerRegistered = false;
        sProcessIdleHandler = new MessageQueue.IdleHandler() { // from class: android.os.StrictMode.6
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                long uptimeMillis = SystemClock.uptimeMillis();
                if (uptimeMillis - StrictMode.sLastInstanceCountCheckMillis <= 30000) {
                    return true;
                }
                StrictMode.sLastInstanceCountCheckMillis = uptimeMillis;
                StrictMode.conditionallyCheckInstanceCounts();
                return true;
            }
        };
        sCeStorageUnlocked = false;
        sLastVmViolationTime = new HashMap<>();
        sRealLastVmViolationTime = new SparseLongArray();
        NO_OP_SPAN = new Span() { // from class: android.os.StrictMode.7
            @Override // android.os.StrictMode.Span
            public void finish() {
            }
        };
        sThisThreadSpanState = new ThreadLocal<ThreadSpanState>() { // from class: android.os.StrictMode.8
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public ThreadSpanState initialValue() {
                return new ThreadSpanState();
            }
        };
        sWindowManager = new Singleton<IWindowManager>() { // from class: android.os.StrictMode.9
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Singleton
            public IWindowManager create() {
                return IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
            }
        };
        sExpectedActivityInstanceCount = new HashMap<>();
    }

    static /* synthetic */ void lambda$static$0(ViolationInfo violationInfo) {
        String str;
        if (violationInfo.durationMillis != -1) {
            str = "StrictMode policy violation; ~duration=" + violationInfo.durationMillis + " ms:";
        } else {
            str = "StrictMode policy violation:";
        }
        Log.d(TAG, str + " " + violationInfo.getStackTrace());
    }

    public static void setViolationLogger(ViolationLogger violationLogger) {
        if (violationLogger == null) {
            violationLogger = LOGCAT_LOGGER;
        }
        sLogger = violationLogger;
    }

    private StrictMode() {
    }

    public static final class ThreadPolicy {
        public static final ThreadPolicy LAX = new ThreadPolicy(0, null, null);
        final Executor mCallbackExecutor;
        final OnThreadViolationListener mListener;
        final int mask;

        private ThreadPolicy(int i, OnThreadViolationListener onThreadViolationListener, Executor executor) {
            this.mask = i;
            this.mListener = onThreadViolationListener;
            this.mCallbackExecutor = executor;
        }

        public String toString() {
            return "[StrictMode.ThreadPolicy; mask=" + this.mask + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public static final class Builder {
            private Executor mExecutor;
            private OnThreadViolationListener mListener;
            private int mMask;

            public Builder() {
                this.mMask = 0;
            }

            public Builder(ThreadPolicy threadPolicy) {
                this.mMask = 0;
                this.mMask = threadPolicy.mask;
                this.mListener = threadPolicy.mListener;
                this.mExecutor = threadPolicy.mCallbackExecutor;
            }

            public Builder detectAll() {
                detectDiskReads();
                detectDiskWrites();
                detectNetwork();
                int targetSdkVersion = VMRuntime.getRuntime().getTargetSdkVersion();
                if (targetSdkVersion >= 11) {
                    detectCustomSlowCalls();
                }
                if (targetSdkVersion >= 23) {
                    detectResourceMismatches();
                }
                if (targetSdkVersion >= 26) {
                    detectUnbufferedIo();
                }
                if (CompatChanges.isChangeEnabled(StrictMode.DETECT_EXPLICIT_GC)) {
                    detectExplicitGc();
                }
                return this;
            }

            public Builder permitAll() {
                return disable(65535);
            }

            public Builder detectNetwork() {
                return enable(4);
            }

            public Builder permitNetwork() {
                return disable(4);
            }

            public Builder detectDiskReads() {
                return enable(2);
            }

            public Builder permitDiskReads() {
                return disable(2);
            }

            public Builder detectCustomSlowCalls() {
                return enable(8);
            }

            public Builder permitCustomSlowCalls() {
                return disable(8);
            }

            public Builder permitResourceMismatches() {
                return disable(16);
            }

            public Builder detectUnbufferedIo() {
                return enable(32);
            }

            public Builder permitUnbufferedIo() {
                return disable(32);
            }

            public Builder detectResourceMismatches() {
                return enable(16);
            }

            public Builder detectDiskWrites() {
                return enable(1);
            }

            public Builder permitDiskWrites() {
                return disable(1);
            }

            public Builder detectExplicitGc() {
                return enable(64);
            }

            public Builder permitExplicitGc() {
                return disable(64);
            }

            public Builder penaltyDialog() {
                return enable(536870912);
            }

            public Builder penaltyDeath() {
                return enable(268435456);
            }

            public Builder penaltyDeathOnNetwork() {
                return enable(33554432);
            }

            public Builder penaltyFlashScreen() {
                return enable(134217728);
            }

            public Builder penaltyLog() {
                return enable(1073741824);
            }

            public Builder penaltyDropBox() {
                return enable(67108864);
            }

            public Builder penaltyListener(Executor executor, OnThreadViolationListener onThreadViolationListener) {
                if (executor == null) {
                    throw new NullPointerException("executor must not be null");
                }
                this.mListener = onThreadViolationListener;
                this.mExecutor = executor;
                return this;
            }

            public Builder penaltyListener(OnThreadViolationListener onThreadViolationListener, Executor executor) {
                return penaltyListener(executor, onThreadViolationListener);
            }

            private Builder enable(int i) {
                this.mMask = i | this.mMask;
                return this;
            }

            private Builder disable(int i) {
                this.mMask = (~i) & this.mMask;
                return this;
            }

            public ThreadPolicy build() {
                int i;
                if (this.mListener == null && (i = this.mMask) != 0 && (i & 1946157056) == 0) {
                    penaltyLog();
                }
                return new ThreadPolicy(this.mMask, this.mListener, this.mExecutor);
            }
        }
    }

    public static final class VmPolicy {
        public static final VmPolicy LAX = new VmPolicy(0, StrictMode.EMPTY_CLASS_LIMIT_MAP, null, null);
        final HashMap<Class, Integer> classInstanceLimit;
        final Executor mCallbackExecutor;
        final OnVmViolationListener mListener;
        final int mask;

        private VmPolicy(int i, HashMap<Class, Integer> hashMap, OnVmViolationListener onVmViolationListener, Executor executor) {
            if (hashMap == null) {
                throw new NullPointerException("classInstanceLimit == null");
            }
            this.mask = i;
            this.classInstanceLimit = hashMap;
            this.mListener = onVmViolationListener;
            this.mCallbackExecutor = executor;
        }

        public String toString() {
            return "[StrictMode.VmPolicy; mask=" + this.mask + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public static final class Builder {
            private HashMap<Class, Integer> mClassInstanceLimit;
            private boolean mClassInstanceLimitNeedCow;
            private Executor mExecutor;
            private OnVmViolationListener mListener;
            private int mMask;

            public Builder() {
                this.mClassInstanceLimitNeedCow = false;
                this.mMask = 0;
            }

            public Builder(VmPolicy vmPolicy) {
                this.mClassInstanceLimitNeedCow = false;
                this.mMask = vmPolicy.mask;
                this.mClassInstanceLimitNeedCow = true;
                this.mClassInstanceLimit = vmPolicy.classInstanceLimit;
                this.mListener = vmPolicy.mListener;
                this.mExecutor = vmPolicy.mCallbackExecutor;
            }

            public Builder setClassInstanceLimit(Class cls, int i) {
                if (cls == null) {
                    throw new NullPointerException("klass == null");
                }
                if (this.mClassInstanceLimitNeedCow) {
                    if (this.mClassInstanceLimit.containsKey(cls) && this.mClassInstanceLimit.get(cls).intValue() == i) {
                        return this;
                    }
                    this.mClassInstanceLimitNeedCow = false;
                    this.mClassInstanceLimit = (HashMap) this.mClassInstanceLimit.clone();
                } else if (this.mClassInstanceLimit == null) {
                    this.mClassInstanceLimit = new HashMap<>();
                }
                this.mMask |= 8;
                this.mClassInstanceLimit.put(cls, Integer.valueOf(i));
                return this;
            }

            public Builder detectActivityLeaks() {
                return enable(4);
            }

            public Builder permitActivityLeaks() {
                synchronized (StrictMode.class) {
                    StrictMode.sExpectedActivityInstanceCount.clear();
                }
                return disable(4);
            }

            public Builder detectNonSdkApiUsage() {
                return enable(512);
            }

            public Builder permitNonSdkApiUsage() {
                return disable(512);
            }

            public Builder detectAll() {
                detectLeakedSqlLiteObjects();
                int targetSdkVersion = VMRuntime.getRuntime().getTargetSdkVersion();
                if (targetSdkVersion >= 11) {
                    detectActivityLeaks();
                    detectLeakedClosableObjects();
                }
                if (targetSdkVersion >= 16) {
                    detectLeakedRegistrationObjects();
                }
                if (targetSdkVersion >= 18) {
                    detectFileUriExposure();
                }
                if (targetSdkVersion >= 23 && SystemProperties.getBoolean(StrictMode.CLEARTEXT_PROPERTY, false)) {
                    detectCleartextNetwork();
                }
                if (targetSdkVersion >= 26) {
                    detectContentUriWithoutPermission();
                    detectUntaggedSockets();
                }
                if (targetSdkVersion >= 29) {
                    detectCredentialProtectedWhileLocked();
                }
                if (targetSdkVersion >= 30) {
                    detectIncorrectContextUse();
                }
                if (targetSdkVersion >= 31) {
                    detectUnsafeIntentLaunch();
                }
                if (Flags.balStrictModeRo() && targetSdkVersion > 35) {
                    detectBlockedBackgroundActivityLaunch();
                }
                return this;
            }

            public Builder detectLeakedSqlLiteObjects() {
                return enable(1);
            }

            public Builder detectLeakedClosableObjects() {
                return enable(2);
            }

            public Builder detectLeakedRegistrationObjects() {
                return enable(16);
            }

            public Builder detectFileUriExposure() {
                return enable(32);
            }

            public Builder detectCleartextNetwork() {
                return enable(64);
            }

            public Builder detectContentUriWithoutPermission() {
                return enable(128);
            }

            public Builder detectUntaggedSockets() {
                return enable(256);
            }

            public Builder permitUntaggedSockets() {
                return disable(256);
            }

            public Builder detectImplicitDirectBoot() {
                return enable(1024);
            }

            public Builder permitImplicitDirectBoot() {
                return disable(1024);
            }

            public Builder detectCredentialProtectedWhileLocked() {
                return enable(2048);
            }

            public Builder permitCredentialProtectedWhileLocked() {
                return disable(2048);
            }

            public Builder detectIncorrectContextUse() {
                return enable(4096);
            }

            public Builder permitIncorrectContextUse() {
                return disable(4096);
            }

            public Builder detectUnsafeIntentLaunch() {
                return enable(8192);
            }

            public Builder permitUnsafeIntentLaunch() {
                return disable(8192);
            }

            public Builder detectBlockedBackgroundActivityLaunch() {
                return enable(16384);
            }

            public Builder ignoreBlockedBackgroundActivityLaunch() {
                return disable(16384);
            }

            public Builder penaltyDeath() {
                return enable(268435456);
            }

            public Builder penaltyDeathOnCleartextNetwork() {
                return enable(16777216);
            }

            public Builder penaltyDeathOnFileUriExposure() {
                return enable(8388608);
            }

            public Builder penaltyLog() {
                return enable(1073741824);
            }

            public Builder penaltyDropBox() {
                return enable(67108864);
            }

            public Builder penaltyListener(Executor executor, OnVmViolationListener onVmViolationListener) {
                if (executor == null) {
                    throw new NullPointerException("executor must not be null");
                }
                this.mListener = onVmViolationListener;
                this.mExecutor = executor;
                return this;
            }

            public Builder penaltyListener(OnVmViolationListener onVmViolationListener, Executor executor) {
                return penaltyListener(executor, onVmViolationListener);
            }

            private Builder enable(int i) {
                this.mMask = i | this.mMask;
                return this;
            }

            Builder disable(int i) {
                this.mMask = (~i) & this.mMask;
                return this;
            }

            public VmPolicy build() {
                int i;
                if (this.mListener == null && (i = this.mMask) != 0 && (i & 1946157056) == 0) {
                    penaltyLog();
                }
                int i2 = this.mMask;
                HashMap<Class, Integer> hashMap = this.mClassInstanceLimit;
                if (hashMap == null) {
                    hashMap = StrictMode.EMPTY_CLASS_LIMIT_MAP;
                }
                return new VmPolicy(i2, hashMap, this.mListener, this.mExecutor);
            }
        }
    }

    public static void setThreadPolicy(ThreadPolicy threadPolicy) {
        setThreadPolicyMask(threadPolicy.mask);
        sThreadViolationListener.set(threadPolicy.mListener);
        sThreadViolationExecutor.set(threadPolicy.mCallbackExecutor);
    }

    public static void setThreadPolicyMask(int i) {
        setBlockGuardPolicy(i);
        Binder.setThreadStrictModePolicy(i);
    }

    private static void setBlockGuardPolicy(int i) {
        AndroidBlockGuardPolicy androidBlockGuardPolicy;
        if (i == 0) {
            BlockGuard.setThreadPolicy(BlockGuard.LAX_POLICY);
            return;
        }
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            androidBlockGuardPolicy = (AndroidBlockGuardPolicy) threadPolicy;
        } else {
            androidBlockGuardPolicy = THREAD_ANDROID_POLICY.get();
            BlockGuard.setThreadPolicy(androidBlockGuardPolicy);
        }
        androidBlockGuardPolicy.setThreadPolicyMask(i);
    }

    private static void setBlockGuardVmPolicy(int i) {
        if ((i & 2048) != 0) {
            BlockGuard.setVmPolicy(VM_ANDROID_POLICY);
        } else {
            BlockGuard.setVmPolicy(BlockGuard.LAX_VM_POLICY);
        }
    }

    private static void setCloseGuardEnabled(boolean z) {
        if (!(CloseGuard.getReporter() instanceof AndroidCloseGuardReporter)) {
            CloseGuard.setReporter(new AndroidCloseGuardReporter());
        }
        CloseGuard.setEnabled(z);
    }

    public static int getThreadPolicyMask() {
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            return ((AndroidBlockGuardPolicy) threadPolicy).getThreadPolicyMask();
        }
        return 0;
    }

    public static ThreadPolicy getThreadPolicy() {
        return new ThreadPolicy(getThreadPolicyMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static ThreadPolicy allowThreadDiskWrites() {
        return new ThreadPolicy(allowThreadDiskWritesMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static int allowThreadDiskWritesMask() {
        int threadPolicyMask = getThreadPolicyMask();
        int i = threadPolicyMask & (-4);
        if (i != threadPolicyMask) {
            setThreadPolicyMask(i);
        }
        return threadPolicyMask;
    }

    public static ThreadPolicy allowThreadDiskReads() {
        return new ThreadPolicy(allowThreadDiskReadsMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static int allowThreadDiskReadsMask() {
        int threadPolicyMask = getThreadPolicyMask();
        int i = threadPolicyMask & (-3);
        if (i != threadPolicyMask) {
            setThreadPolicyMask(i);
        }
        return threadPolicyMask;
    }

    public static ThreadPolicy allowThreadViolations() {
        ThreadPolicy threadPolicy = getThreadPolicy();
        setThreadPolicyMask(0);
        return threadPolicy;
    }

    public static VmPolicy allowVmViolations() {
        VmPolicy vmPolicy = getVmPolicy();
        sVmPolicy = VmPolicy.LAX;
        return vmPolicy;
    }

    public static boolean isBundledSystemApp(ApplicationInfo applicationInfo) {
        if (applicationInfo == null || applicationInfo.packageName == null) {
            return true;
        }
        if (!applicationInfo.isSystemApp() || applicationInfo.packageName.equals("com.android.vending") || applicationInfo.packageName.equals("com.android.chrome") || applicationInfo.packageName.equals("com.android.phone")) {
            return false;
        }
        return applicationInfo.packageName.equals("android") || applicationInfo.packageName.startsWith("android.") || applicationInfo.packageName.startsWith("com.android.");
    }

    public static void initThreadDefaults(ApplicationInfo applicationInfo) {
        ThreadPolicy.Builder builder = new ThreadPolicy.Builder();
        if ((applicationInfo != null ? applicationInfo.targetSdkVersion : 10000) >= 11) {
            builder.detectNetwork();
            builder.penaltyDeathOnNetwork();
        }
        if (!Build.IS_USER && !SystemProperties.getBoolean(DISABLE_PROPERTY, false) && ((Build.IS_USERDEBUG || Build.IS_ENG) && isBundledSystemApp(applicationInfo))) {
            builder.detectAll();
            if (Build.IS_ENG) {
                builder.penaltyLog();
            }
        }
        setThreadPolicy(builder.build());
    }

    public static void initVmDefaults(ApplicationInfo applicationInfo) {
        VmPolicy.Builder builder = new VmPolicy.Builder();
        if ((applicationInfo != null ? applicationInfo.targetSdkVersion : 10000) >= 24) {
            builder.detectFileUriExposure();
            builder.penaltyDeathOnFileUriExposure();
        }
        if (!Build.IS_USER && !SystemProperties.getBoolean(DISABLE_PROPERTY, false)) {
            if (Build.IS_USERDEBUG) {
                if (isBundledSystemApp(applicationInfo)) {
                    builder.detectAll();
                    builder.permitActivityLeaks();
                }
            } else if (Build.IS_ENG && isBundledSystemApp(applicationInfo)) {
                builder.detectAll();
                builder.penaltyLog();
            }
        }
        setVmPolicy(builder.build());
    }

    public static void enableDeathOnFileUriExposure() {
        sVmPolicy = new VmPolicy(sVmPolicy.mask | 8388640, sVmPolicy.classInstanceLimit, sVmPolicy.mListener, sVmPolicy.mCallbackExecutor);
    }

    public static void disableDeathOnFileUriExposure() {
        sVmPolicy = new VmPolicy(sVmPolicy.mask & (-8388641), sVmPolicy.classInstanceLimit, sVmPolicy.mListener, sVmPolicy.mCallbackExecutor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean tooManyViolationsThisLoop() {
        return violationsBeingTimed.get().size() >= 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AndroidBlockGuardPolicy implements BlockGuard.Policy {
        private ArrayMap<Integer, Long> mLastViolationTime;
        private SparseLongArray mRealLastViolationTime;
        private int mThreadPolicyMask;

        public AndroidBlockGuardPolicy(int i) {
            this.mThreadPolicyMask = i;
        }

        public String toString() {
            return "AndroidBlockGuardPolicy; mPolicyMask=" + this.mThreadPolicyMask;
        }

        public int getPolicyMask() {
            return this.mThreadPolicyMask;
        }

        public void onWriteToDisk() {
            if ((this.mThreadPolicyMask & 1) == 0 || StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new DiskWriteViolation());
        }

        void onCustomSlowCall(String str) {
            if ((this.mThreadPolicyMask & 8) == 0 || StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new CustomViolation(str));
        }

        void onResourceMismatch(Object obj) {
            if ((this.mThreadPolicyMask & 16) == 0 || StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new ResourceMismatchViolation(obj));
        }

        public void onUnbufferedIO() {
            if ((this.mThreadPolicyMask & 32) == 0 || StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new UnbufferedIoViolation());
        }

        public void onReadFromDisk() {
            if ((this.mThreadPolicyMask & 2) == 0 || StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new DiskReadViolation());
        }

        public void onNetwork() {
            int i = this.mThreadPolicyMask;
            if ((i & 4) == 0) {
                return;
            }
            if ((i & 33554432) != 0) {
                throw new NetworkOnMainThreadException();
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new NetworkViolation());
        }

        public void onExplicitGc() {
            if ((this.mThreadPolicyMask & 64) == 0 || StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new ExplicitGcViolation());
        }

        public int getThreadPolicyMask() {
            return this.mThreadPolicyMask;
        }

        public void setThreadPolicyMask(int i) {
            this.mThreadPolicyMask = i;
        }

        void startHandlingViolationException(Violation violation) {
            ViolationInfo violationInfo = new ViolationInfo(violation, this.mThreadPolicyMask & (-65536));
            violationInfo.violationUptimeMillis = SystemClock.uptimeMillis();
            handleViolationWithTimingAttempt(violationInfo);
        }

        void handleViolationWithTimingAttempt(ViolationInfo violationInfo) {
            if (Looper.myLooper() == null || violationInfo.mPenaltyMask == 268435456) {
                violationInfo.durationMillis = -1;
                onThreadPolicyViolation(violationInfo);
                return;
            }
            final ArrayList arrayList = (ArrayList) StrictMode.violationsBeingTimed.get();
            if (arrayList.size() >= 10) {
                return;
            }
            arrayList.add(violationInfo);
            if (arrayList.size() > 1) {
                return;
            }
            final IWindowManager iWindowManager = violationInfo.penaltyEnabled(134217728) ? (IWindowManager) StrictMode.sWindowManager.get() : null;
            if (iWindowManager != null) {
                try {
                    iWindowManager.showStrictModeViolation(true);
                } catch (RemoteException unused) {
                }
            }
            ((Handler) StrictMode.THREAD_HANDLER.get()).postAtFrontOfQueue(new Runnable() { // from class: android.os.StrictMode$AndroidBlockGuardPolicy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    StrictMode.AndroidBlockGuardPolicy.this.lambda$handleViolationWithTimingAttempt$0(iWindowManager, arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleViolationWithTimingAttempt$0(IWindowManager iWindowManager, ArrayList arrayList) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = 0;
            if (iWindowManager != null) {
                try {
                    iWindowManager.showStrictModeViolation(false);
                } catch (RemoteException unused) {
                }
            }
            while (i < arrayList.size()) {
                ViolationInfo violationInfo = (ViolationInfo) arrayList.get(i);
                i++;
                violationInfo.violationNumThisLoop = i;
                violationInfo.durationMillis = (int) (uptimeMillis - violationInfo.violationUptimeMillis);
                onThreadPolicyViolation(violationInfo);
            }
            arrayList.clear();
        }

        void onThreadPolicyViolation(ViolationInfo violationInfo) {
            long j;
            if (StrictMode.LOG_V) {
                Log.d(StrictMode.TAG, "onThreadPolicyViolation; penalty=" + violationInfo.mPenaltyMask);
            }
            if (violationInfo.penaltyEnabled(Integer.MIN_VALUE)) {
                ArrayList arrayList = (ArrayList) StrictMode.gatheredViolations.get();
                if (arrayList == null) {
                    arrayList = new ArrayList(1);
                    StrictMode.gatheredViolations.set(arrayList);
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (violationInfo.getStackTrace().equals(((ViolationInfo) it.next()).getStackTrace())) {
                        return;
                    }
                }
                arrayList.add(violationInfo);
                return;
            }
            int hashCode = violationInfo.hashCode();
            Integer valueOf = Integer.valueOf(hashCode);
            long uptimeMillis = SystemClock.uptimeMillis();
            if (StrictMode.sLogger == StrictMode.LOGCAT_LOGGER) {
                SparseLongArray sparseLongArray = this.mRealLastViolationTime;
                if (sparseLongArray != null) {
                    valueOf.getClass();
                    long j2 = sparseLongArray.get(hashCode);
                    Long valueOf2 = Long.valueOf(j2);
                    if (valueOf2 != null) {
                        valueOf2.getClass();
                    } else {
                        j2 = 0;
                    }
                    StrictMode.clampViolationTimeMap(this.mRealLastViolationTime, Math.max(1000L, Math.max(30000L, 3000L)));
                    j = j2;
                } else {
                    this.mRealLastViolationTime = new SparseLongArray(1);
                    j = 0;
                }
                SparseLongArray sparseLongArray2 = this.mRealLastViolationTime;
                valueOf.getClass();
                sparseLongArray2.put(hashCode, uptimeMillis);
            } else {
                j = 0;
            }
            long j3 = j == 0 ? Long.MAX_VALUE : uptimeMillis - j;
            if (violationInfo.penaltyEnabled(1073741824) && j3 > 1000) {
                StrictMode.sLogger.log(violationInfo);
            }
            final Violation violation = violationInfo.mViolation;
            int i = (!violationInfo.penaltyEnabled(536870912) || j3 <= 30000) ? 0 : 536870912;
            if (violationInfo.penaltyEnabled(67108864) && j3 > 3000) {
                i |= 67108864;
            }
            if (i != 0) {
                if (violationInfo.mPenaltyMask == 67108864) {
                    StrictMode.dropboxViolationAsync(i, violationInfo);
                } else {
                    StrictMode.handleApplicationStrictModeViolation(i, violationInfo);
                }
            }
            if (violationInfo.penaltyEnabled(268435456)) {
                throw new RuntimeException("StrictMode ThreadPolicy violation", violation);
            }
            final OnThreadViolationListener onThreadViolationListener = (OnThreadViolationListener) StrictMode.sThreadViolationListener.get();
            Executor executor = (Executor) StrictMode.sThreadViolationExecutor.get();
            if (onThreadViolationListener == null || executor == null) {
                return;
            }
            try {
                executor.execute(new Runnable() { // from class: android.os.StrictMode$AndroidBlockGuardPolicy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StrictMode.AndroidBlockGuardPolicy.lambda$onThreadPolicyViolation$1(StrictMode.OnThreadViolationListener.this, violation);
                    }
                });
            } catch (RejectedExecutionException e) {
                Log.e(StrictMode.TAG, "ThreadPolicy penaltyCallback failed", e);
            }
        }

        static /* synthetic */ void lambda$onThreadPolicyViolation$1(OnThreadViolationListener onThreadViolationListener, Violation violation) {
            ThreadPolicy allowThreadViolations = StrictMode.allowThreadViolations();
            try {
                onThreadViolationListener.onThreadViolation(violation);
            } finally {
                StrictMode.setThreadPolicy(allowThreadViolations);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dropboxViolationAsync(final int i, final ViolationInfo violationInfo) {
        AtomicInteger atomicInteger = sDropboxCallsInFlight;
        int incrementAndGet = atomicInteger.incrementAndGet();
        if (incrementAndGet > 20) {
            atomicInteger.decrementAndGet();
            return;
        }
        if (LOG_V) {
            Log.d(TAG, "Dropboxing async; in-flight=" + incrementAndGet);
        }
        BackgroundThread.getHandler().post(new Runnable() { // from class: android.os.StrictMode$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                StrictMode.lambda$dropboxViolationAsync$2(i, violationInfo);
            }
        });
    }

    static /* synthetic */ void lambda$dropboxViolationAsync$2(int i, ViolationInfo violationInfo) {
        handleApplicationStrictModeViolation(i, violationInfo);
        int decrementAndGet = sDropboxCallsInFlight.decrementAndGet();
        if (LOG_V) {
            Log.d(TAG, "Dropbox complete; in-flight=" + decrementAndGet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleApplicationStrictModeViolation(int i, ViolationInfo violationInfo) {
        int threadPolicyMask = getThreadPolicyMask();
        try {
            setThreadPolicyMask(0);
            IActivityManager service = ActivityManager.getService();
            if (service == null) {
                Log.w(TAG, "No activity manager; failed to Dropbox violation.");
            } else {
                service.handleApplicationStrictModeViolation(RuntimeInit.getApplicationObject(), i, violationInfo);
            }
        } catch (RemoteException e) {
            if (!(e instanceof DeadObjectException)) {
                Log.e(TAG, "RemoteException handling StrictMode violation", e);
            }
        } finally {
            setThreadPolicyMask(threadPolicyMask);
        }
    }

    private static class AndroidCloseGuardReporter implements CloseGuard.Reporter {
        private AndroidCloseGuardReporter() {
        }

        public void report(String str, Throwable th) {
            StrictMode.onVmPolicyViolation(new LeakedClosableViolation(str, th));
        }

        public void report(String str) {
            StrictMode.onVmPolicyViolation(new LeakedClosableViolation(str));
        }
    }

    static boolean hasGatheredViolations() {
        return gatheredViolations.get() != null;
    }

    static void clearGatheredViolations() {
        gatheredViolations.set(null);
    }

    public static void conditionallyCheckInstanceCounts() {
        VmPolicy vmPolicy = getVmPolicy();
        int size = vmPolicy.classInstanceLimit.size();
        if (size == 0) {
            return;
        }
        int threadPolicyMask = getThreadPolicyMask();
        setThreadPolicyMask(0);
        System.gc();
        System.runFinalization();
        System.gc();
        setThreadPolicyMask(threadPolicyMask);
        Class[] clsArr = (Class[]) vmPolicy.classInstanceLimit.keySet().toArray(new Class[size]);
        long[] countInstancesOfClasses = VMDebug.countInstancesOfClasses(clsArr, false);
        for (int i = 0; i < clsArr.length; i++) {
            Class cls = clsArr[i];
            int intValue = vmPolicy.classInstanceLimit.get(cls).intValue();
            long j = countInstancesOfClasses[i];
            if (j > intValue) {
                onVmPolicyViolation(new InstanceCountViolation(cls, j, intValue));
            }
        }
    }

    public static void setVmPolicy(VmPolicy vmPolicy) {
        int i;
        synchronized (StrictMode.class) {
            sVmPolicy = vmPolicy;
            setCloseGuardEnabled(vmClosableObjectLeaksEnabled());
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                MessageQueue messageQueue = mainLooper.mQueue;
                if (vmPolicy.classInstanceLimit.size() != 0 && (sVmPolicy.mask & (-65536)) != 0) {
                    if (!sIsIdlerRegistered) {
                        messageQueue.addIdleHandler(sProcessIdleHandler);
                        sIsIdlerRegistered = true;
                    }
                }
                messageQueue.removeIdleHandler(sProcessIdleHandler);
                sIsIdlerRegistered = false;
            }
            if ((sVmPolicy.mask & 64) != 0) {
                if ((sVmPolicy.mask & 268435456) == 0 && (sVmPolicy.mask & 16777216) == 0) {
                    i = 1;
                }
                i = 2;
            } else {
                i = 0;
            }
            INetworkManagementService asInterface = INetworkManagementService.Stub.asInterface(ServiceManager.getService(Context.NETWORKMANAGEMENT_SERVICE));
            if (asInterface != null) {
                try {
                    asInterface.setUidCleartextNetworkPolicy(Process.myUid(), i);
                } catch (RemoteException unused) {
                }
            } else if (i != 0) {
                Log.w(TAG, "Dropping requested network policy due to missing service!");
            }
            if ((sVmPolicy.mask & 512) != 0) {
                VMRuntime.setNonSdkApiUsageConsumer(sNonSdkApiUsageConsumer);
                VMRuntime.setDedupeHiddenApiWarnings(false);
            } else {
                VMRuntime.setNonSdkApiUsageConsumer((Consumer) null);
                VMRuntime.setDedupeHiddenApiWarnings(true);
            }
            if ((sVmPolicy.mask & 8192) != 0) {
                registerIntentMatchingRestrictionCallback();
            }
            if ((sVmPolicy.mask & 16384) != 0) {
                registerBackgroundActivityLaunchCallback();
            }
            setBlockGuardVmPolicy(sVmPolicy.mask);
        }
    }

    private static void registerBackgroundActivityLaunchCallback() {
        try {
            IActivityTaskManager service = ActivityTaskManager.getService();
            if (service != null) {
                service.registerBackgroundActivityStartCallback(new BackgroundActivityLaunchCallback());
            }
        } catch (DeadObjectException unused) {
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException handling StrictMode violation", e);
        }
    }

    private static final class UnsafeIntentStrictModeCallback extends IUnsafeIntentStrictModeCallback.Stub {
        private UnsafeIntentStrictModeCallback() {
        }

        @Override // android.app.IUnsafeIntentStrictModeCallback
        public void onUnsafeIntent(int i, Intent intent) {
            if (StrictMode.vmUnsafeIntentLaunchEnabled()) {
                StrictMode.onUnsafeIntentLaunch(i, intent);
            }
        }
    }

    private static void registerIntentMatchingRestrictionCallback() {
        if (sUnsafeIntentCallback == null) {
            sUnsafeIntentCallback = new UnsafeIntentStrictModeCallback();
            try {
                ActivityManager.getService().registerStrictModeCallback(sUnsafeIntentCallback);
            } catch (RemoteException unused) {
            }
        }
    }

    private static final class BackgroundActivityLaunchCallback extends IBackgroundActivityLaunchCallback.Stub {
        private BackgroundActivityLaunchCallback() {
        }

        @Override // android.app.IBackgroundActivityLaunchCallback
        public void onBackgroundActivityLaunchAborted(String str) {
            if (StrictMode.vmBackgroundActivityLaunchEnabled()) {
                StrictMode.onBackgroundActivityLaunchAborted(str);
            }
        }
    }

    public static VmPolicy getVmPolicy() {
        VmPolicy vmPolicy;
        synchronized (StrictMode.class) {
            vmPolicy = sVmPolicy;
        }
        return vmPolicy;
    }

    public static void enableDefaults() {
        setThreadPolicy(new ThreadPolicy.Builder().detectAll().penaltyLog().build());
        setVmPolicy(new VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public static boolean vmSqliteObjectLeaksEnabled() {
        return (sVmPolicy.mask & 1) != 0;
    }

    public static boolean vmClosableObjectLeaksEnabled() {
        return (sVmPolicy.mask & 2) != 0;
    }

    public static boolean vmRegistrationLeaksEnabled() {
        return (sVmPolicy.mask & 16) != 0;
    }

    public static boolean vmFileUriExposureEnabled() {
        return (sVmPolicy.mask & 32) != 0;
    }

    public static boolean vmCleartextNetworkEnabled() {
        return (sVmPolicy.mask & 64) != 0;
    }

    public static boolean vmContentUriWithoutPermissionEnabled() {
        return (sVmPolicy.mask & 128) != 0;
    }

    public static boolean vmUntaggedSocketEnabled() {
        return (sVmPolicy.mask & 256) != 0;
    }

    public static boolean vmImplicitDirectBootEnabled() {
        return (sVmPolicy.mask & 1024) != 0;
    }

    public static boolean vmCredentialProtectedWhileLockedEnabled() {
        return (sVmPolicy.mask & 2048) != 0;
    }

    public static boolean vmIncorrectContextUseEnabled() {
        return (sVmPolicy.mask & 4096) != 0;
    }

    public static boolean vmUnsafeIntentLaunchEnabled() {
        return (sVmPolicy.mask & 8192) != 0;
    }

    public static boolean vmBackgroundActivityLaunchEnabled() {
        return (sVmPolicy.mask & 16384) != 0;
    }

    public static void onSqliteObjectLeaked(String str, Throwable th) {
        onVmPolicyViolation(new SqliteObjectLeakedViolation(str, th));
    }

    public static void onWebViewMethodCalledOnWrongThread(Throwable th) {
        onVmPolicyViolation(new WebViewMethodCalledOnWrongThreadViolation(th));
    }

    public static void onIntentReceiverLeaked(Throwable th) {
        onVmPolicyViolation(new IntentReceiverLeakedViolation(th));
    }

    public static void onServiceConnectionLeaked(Throwable th) {
        onVmPolicyViolation(new ServiceConnectionLeakedViolation(th));
    }

    public static void onFileUriExposed(Uri uri, String str) {
        String str2 = uri + " exposed beyond app through " + str;
        if ((sVmPolicy.mask & 8388608) != 0) {
            throw new FileUriExposedException(str2);
        }
        onVmPolicyViolation(new FileUriExposedViolation(str2));
    }

    public static void onContentUriWithoutPermission(Uri uri, String str) {
        onVmPolicyViolation(new ContentUriWithoutPermissionViolation(uri, str));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void onCleartextNetworkDetected(byte[] r5) {
        /*
            r0 = 0
            if (r5 == 0) goto L2e
            int r1 = r5.length
            r2 = 20
            r3 = 16
            if (r1 < r2) goto L19
            r1 = r5[r0]
            r1 = r1 & 240(0xf0, float:3.36E-43)
            r2 = 64
            if (r1 != r2) goto L19
            r1 = 4
            byte[] r2 = new byte[r1]
            java.lang.System.arraycopy(r5, r3, r2, r0, r1)
            goto L2f
        L19:
            int r1 = r5.length
            r2 = 40
            if (r1 < r2) goto L2e
            r1 = r5[r0]
            r1 = r1 & 240(0xf0, float:3.36E-43)
            r2 = 96
            if (r1 != r2) goto L2e
            byte[] r2 = new byte[r3]
            r1 = 24
            java.lang.System.arraycopy(r5, r1, r2, r0, r3)
            goto L2f
        L2e:
            r2 = 0
        L2f:
            int r1 = android.os.Process.myUid()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Detected cleartext network traffic from UID "
            r3.<init>(r4)
            r3.append(r1)
            if (r2 == 0) goto L4b
            java.lang.String r1 = " to "
            r3.append(r1)     // Catch: java.net.UnknownHostException -> L4b
            java.net.InetAddress r1 = java.net.InetAddress.getByAddress(r2)     // Catch: java.net.UnknownHostException -> L4b
            r3.append(r1)     // Catch: java.net.UnknownHostException -> L4b
        L4b:
            java.lang.String r5 = com.android.internal.util.HexDump.dumpHexString(r5)
            java.lang.String r5 = r5.trim()
            r3.append(r5)
            r5 = 32
            r3.append(r5)
            android.os.StrictMode$VmPolicy r5 = android.os.StrictMode.sVmPolicy
            int r5 = r5.mask
            r1 = 16777216(0x1000000, float:2.3509887E-38)
            r5 = r5 & r1
            if (r5 == 0) goto L65
            r0 = 1
        L65:
            android.os.strictmode.CleartextNetworkViolation r5 = new android.os.strictmode.CleartextNetworkViolation
            java.lang.String r1 = r3.toString()
            r5.<init>(r1)
            onVmPolicyViolation(r5, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.StrictMode.onCleartextNetworkDetected(byte[]):void");
    }

    public static void onUntaggedSocket() {
        onVmPolicyViolation(new UntaggedSocketViolation());
    }

    public static void onImplicitDirectBoot() {
        onVmPolicyViolation(new ImplicitDirectBootViolation());
    }

    public static void onIncorrectContextUsed(String str, Throwable th) {
        onVmPolicyViolation(new IncorrectContextUseViolation(str, th));
    }

    public static void assertConfigurationContext(Context context, String str) {
        if (!vmIncorrectContextUseEnabled() || context.isConfigurationContext()) {
            return;
        }
        String str2 = "Tried to access the API:" + str + " which needs to have proper configuration from a non-UI Context:" + context;
        String str3 = "The API:" + str + " needs a proper configuration. Use UI contexts such as an activity or a context created via createWindowContext(Display, int, Bundle) or  createConfigurationContext(Configuration) with a proper configuration.";
        IllegalAccessException illegalAccessException = new IllegalAccessException(str2);
        onIncorrectContextUsed(str3, illegalAccessException);
        Log.e(TAG, str2 + " " + str3, illegalAccessException);
    }

    public static void assertUiContext(Context context, String str) {
        if (!vmIncorrectContextUseEnabled() || context.isUiContext()) {
            return;
        }
        String str2 = "Tried to access UI related API:" + str + " from a non-UI Context:" + context;
        String str3 = str + " should be accessed from Activity or other UI Contexts. Use an Activity or a Context created with Context#createWindowContext(int, Bundle), which are adjusted to the configuration and visual bounds of an area on screen.";
        IllegalAccessException illegalAccessException = new IllegalAccessException(str2);
        onIncorrectContextUsed(str3, illegalAccessException);
        Log.e(TAG, str2 + " " + str3, illegalAccessException);
    }

    public static void onUnsafeIntentLaunch(Intent intent) {
        onVmPolicyViolation(new UnsafeIntentLaunchViolation(intent));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onUnsafeIntentLaunch(int i, Intent intent) {
        String str;
        if (i == 1) {
            str = "Launch of intent with null action: ";
        } else if (i == 2) {
            str = "Implicit intent matching internal non-exported component: ";
        } else if (i != 3) {
            return;
        } else {
            str = "Intent mismatch target component intent filter: ";
        }
        onVmPolicyViolation(new UnsafeIntentLaunchViolation(intent, str + intent));
    }

    public static void onBackgroundActivityLaunchAborted(String str) {
        onVmPolicyViolation(new BackgroundActivityLaunchViolation(str));
    }

    private static boolean isCeStorageUnlocked(int i) {
        IStorageManager iStorageManager = sStorageManager;
        if (iStorageManager == null && (iStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getService(AudioParameter.VALUE_MOUNT))) != null) {
            sStorageManager = iStorageManager;
        }
        if (iStorageManager == null) {
            return false;
        }
        try {
            return iStorageManager.isCeStorageUnlocked(i);
        } catch (RemoteException unused) {
            sStorageManager = null;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onCredentialProtectedPathAccess(String str, int i) {
        if (i == UserHandle.myUserId()) {
            if (sCeStorageUnlocked) {
                return;
            }
            if (isCeStorageUnlocked(i)) {
                sCeStorageUnlocked = true;
                return;
            }
        } else if (isCeStorageUnlocked(i)) {
            return;
        }
        onVmPolicyViolation(new CredentialProtectedWhileLockedViolation("Accessed credential protected path " + str + " while user " + i + " was locked"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void clampViolationTimeMap(SparseLongArray sparseLongArray, long j) {
        int i = 0;
        while (i < sparseLongArray.size()) {
            if (sparseLongArray.valueAt(i) < j) {
                sparseLongArray.removeAt(i);
            } else {
                i++;
            }
        }
    }

    public static void onVmPolicyViolation(Violation violation) {
        onVmPolicyViolation(violation, false);
    }

    public static void onVmPolicyViolation(final Violation violation, boolean z) {
        VmPolicy vmPolicy = getVmPolicy();
        boolean z2 = (vmPolicy.mask & 67108864) != 0;
        boolean z3 = (vmPolicy.mask & 268435456) != 0 || z;
        boolean z4 = (vmPolicy.mask & 1073741824) != 0;
        ViolationInfo violationInfo = new ViolationInfo(violation, vmPolicy.mask & (-65536));
        violationInfo.numAnimationsRunning = 0;
        violationInfo.tags = null;
        violationInfo.broadcastIntentAction = null;
        int hashCode = violationInfo.hashCode();
        Integer valueOf = Integer.valueOf(hashCode);
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = Long.MAX_VALUE;
        if (sLogger == LOGCAT_LOGGER) {
            SparseLongArray sparseLongArray = sRealLastVmViolationTime;
            synchronized (sparseLongArray) {
                valueOf.getClass();
                if (sparseLongArray.indexOfKey(hashCode) >= 0) {
                    valueOf.getClass();
                    j = uptimeMillis - sparseLongArray.get(hashCode);
                }
                if (j > 1000) {
                    valueOf.getClass();
                    sparseLongArray.put(hashCode, uptimeMillis);
                }
                clampViolationTimeMap(sparseLongArray, uptimeMillis - Math.max(1000L, 1000L));
            }
        }
        if (j <= 1000) {
            return;
        }
        if (z4 && sLogger != null && j > 1000) {
            sLogger.log(violationInfo);
        }
        if (z2) {
            if (z3) {
                handleApplicationStrictModeViolation(67108864, violationInfo);
            } else {
                dropboxViolationAsync(67108864, violationInfo);
            }
        }
        if (z3) {
            System.err.println("StrictMode VmPolicy violation with POLICY_DEATH; shutting down.");
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
        if (vmPolicy.mListener == null || vmPolicy.mCallbackExecutor == null) {
            return;
        }
        final OnVmViolationListener onVmViolationListener = vmPolicy.mListener;
        try {
            vmPolicy.mCallbackExecutor.execute(new Runnable() { // from class: android.os.StrictMode$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StrictMode.lambda$onVmPolicyViolation$3(StrictMode.OnVmViolationListener.this, violation);
                }
            });
        } catch (RejectedExecutionException e) {
            Log.e(TAG, "VmPolicy penaltyCallback failed", e);
        }
    }

    static /* synthetic */ void lambda$onVmPolicyViolation$3(OnVmViolationListener onVmViolationListener, Violation violation) {
        VmPolicy allowVmViolations = allowVmViolations();
        try {
            onVmViolationListener.onVmViolation(violation);
        } finally {
            setVmPolicy(allowVmViolations);
        }
    }

    static void writeGatheredViolationsToParcel(Parcel parcel) {
        ArrayList<ViolationInfo> arrayList = gatheredViolations.get();
        if (arrayList == null) {
            parcel.writeInt(0);
        } else {
            int min = Math.min(arrayList.size(), 3);
            parcel.writeInt(min);
            for (int i = 0; i < min; i++) {
                arrayList.get(i).writeToParcel(parcel, 0);
            }
        }
        gatheredViolations.set(null);
    }

    static void readAndHandleBinderCallViolations(Parcel parcel) {
        Throwable th = new Throwable();
        boolean z = (getThreadPolicyMask() & Integer.MIN_VALUE) != 0;
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            ViolationInfo violationInfo = new ViolationInfo(parcel, !z);
            violationInfo.addLocalStack(th);
            BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
            if (threadPolicy instanceof AndroidBlockGuardPolicy) {
                ((AndroidBlockGuardPolicy) threadPolicy).handleViolationWithTimingAttempt(violationInfo);
            }
        }
    }

    private static void onBinderStrictModePolicyChange(int i) {
        setBlockGuardPolicy(i);
    }

    public static class Span {
        private final ThreadSpanState mContainerState;
        private long mCreateMillis;
        private String mName;
        private Span mNext;
        private Span mPrev;

        Span(ThreadSpanState threadSpanState) {
            this.mContainerState = threadSpanState;
        }

        protected Span() {
            this.mContainerState = null;
        }

        public void finish() {
            ThreadSpanState threadSpanState = this.mContainerState;
            synchronized (threadSpanState) {
                if (this.mName == null) {
                    return;
                }
                Span span = this.mPrev;
                if (span != null) {
                    span.mNext = this.mNext;
                }
                Span span2 = this.mNext;
                if (span2 != null) {
                    span2.mPrev = span;
                }
                if (threadSpanState.mActiveHead == this) {
                    threadSpanState.mActiveHead = this.mNext;
                }
                threadSpanState.mActiveSize--;
                if (StrictMode.LOG_V) {
                    Log.d(StrictMode.TAG, "Span finished=" + this.mName + "; size=" + threadSpanState.mActiveSize);
                }
                this.mCreateMillis = -1L;
                this.mName = null;
                this.mPrev = null;
                this.mNext = null;
                if (threadSpanState.mFreeListSize < 5) {
                    this.mNext = threadSpanState.mFreeListHead;
                    threadSpanState.mFreeListHead = this;
                    threadSpanState.mFreeListSize++;
                }
            }
        }
    }

    private static class ThreadSpanState {
        public Span mActiveHead;
        public int mActiveSize;
        public Span mFreeListHead;
        public int mFreeListSize;

        private ThreadSpanState() {
        }
    }

    public static Span enterCriticalSpan(String str) {
        Span span;
        if (Build.IS_USER) {
            return NO_OP_SPAN;
        }
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("name must be non-null and non-empty");
        }
        ThreadSpanState threadSpanState = sThisThreadSpanState.get();
        synchronized (threadSpanState) {
            if (threadSpanState.mFreeListHead != null) {
                span = threadSpanState.mFreeListHead;
                threadSpanState.mFreeListHead = span.mNext;
                threadSpanState.mFreeListSize--;
            } else {
                span = new Span(threadSpanState);
            }
            span.mName = str;
            span.mCreateMillis = SystemClock.uptimeMillis();
            span.mNext = threadSpanState.mActiveHead;
            span.mPrev = null;
            threadSpanState.mActiveHead = span;
            threadSpanState.mActiveSize++;
            if (span.mNext != null) {
                span.mNext.mPrev = span;
            }
            if (LOG_V) {
                Log.d(TAG, "Span enter=" + str + "; size=" + threadSpanState.mActiveSize);
            }
        }
        return span;
    }

    public static void noteSlowCall(String str) {
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            ((AndroidBlockGuardPolicy) threadPolicy).onCustomSlowCall(str);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static void noteUntaggedSocket() {
        if (vmUntaggedSocketEnabled()) {
            onUntaggedSocket();
        }
    }

    public static void noteResourceMismatch(Object obj) {
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            ((AndroidBlockGuardPolicy) threadPolicy).onResourceMismatch(obj);
        }
    }

    public static void noteUnbufferedIO() {
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            threadPolicy.onUnbufferedIO();
        }
    }

    public static void noteDiskRead() {
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            threadPolicy.onReadFromDisk();
        }
    }

    public static void noteDiskWrite() {
        BlockGuard.Policy threadPolicy = BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof AndroidBlockGuardPolicy) {
            threadPolicy.onWriteToDisk();
        }
    }

    public static Object trackActivity(Object obj) {
        return new InstanceTracker(obj);
    }

    public static void incrementExpectedActivityCount(Class cls) {
        if (cls == null) {
            return;
        }
        synchronized (StrictMode.class) {
            if ((sVmPolicy.mask & 4) == 0) {
                return;
            }
            HashMap<Class, Integer> hashMap = sExpectedActivityInstanceCount;
            Integer num = hashMap.get(cls);
            hashMap.put(cls, Integer.valueOf((num == null ? InstanceTracker.getInstanceCount(cls) : num.intValue()) + 1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002c A[Catch: all -> 0x005c, TryCatch #0 {, blocks: (B:5:0x0006, B:7:0x000e, B:10:0x0010, B:12:0x001b, B:15:0x0022, B:17:0x002c, B:18:0x0037, B:19:0x0039, B:28:0x0030), top: B:4:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0030 A[Catch: all -> 0x005c, TryCatch #0 {, blocks: (B:5:0x0006, B:7:0x000e, B:10:0x0010, B:12:0x001b, B:15:0x0022, B:17:0x002c, B:18:0x0037, B:19:0x0039, B:28:0x0030), top: B:4:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void decrementExpectedActivityCount(java.lang.Class r5) {
        /*
            if (r5 != 0) goto L3
            goto L5b
        L3:
            java.lang.Class<android.os.StrictMode> r0 = android.os.StrictMode.class
            monitor-enter(r0)
            android.os.StrictMode$VmPolicy r1 = android.os.StrictMode.sVmPolicy     // Catch: java.lang.Throwable -> L5c
            int r1 = r1.mask     // Catch: java.lang.Throwable -> L5c
            r1 = r1 & 4
            if (r1 != 0) goto L10
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
            return
        L10:
            java.util.HashMap<java.lang.Class, java.lang.Integer> r1 = android.os.StrictMode.sExpectedActivityInstanceCount     // Catch: java.lang.Throwable -> L5c
            java.lang.Object r2 = r1.get(r5)     // Catch: java.lang.Throwable -> L5c
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Throwable -> L5c
            r3 = 0
            if (r2 == 0) goto L29
            int r4 = r2.intValue()     // Catch: java.lang.Throwable -> L5c
            if (r4 != 0) goto L22
            goto L29
        L22:
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L5c
            int r2 = r2 + (-1)
            goto L2a
        L29:
            r2 = r3
        L2a:
            if (r2 != 0) goto L30
            r1.remove(r5)     // Catch: java.lang.Throwable -> L5c
            goto L37
        L30:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L5c
            r1.put(r5, r4)     // Catch: java.lang.Throwable -> L5c
        L37:
            int r2 = r2 + 1
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
            int r0 = android.os.StrictMode.InstanceTracker.getInstanceCount(r5)
            if (r0 > r2) goto L41
            goto L5b
        L41:
            java.lang.System.gc()
            java.lang.System.runFinalization()
            java.lang.System.gc()
            long r0 = dalvik.system.VMDebug.countInstancesOfClass(r5, r3)
            long r3 = (long) r2
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 <= 0) goto L5b
            android.os.strictmode.InstanceCountViolation r3 = new android.os.strictmode.InstanceCountViolation
            r3.<init>(r5, r0, r2)
            onVmPolicyViolation(r3)
        L5b:
            return
        L5c:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.StrictMode.decrementExpectedActivityCount(java.lang.Class):void");
    }

    public static final class ViolationInfo implements Parcelable {
        public static final Parcelable.Creator<ViolationInfo> CREATOR = new Parcelable.Creator<ViolationInfo>() { // from class: android.os.StrictMode.ViolationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ViolationInfo createFromParcel(Parcel parcel) {
                return new ViolationInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ViolationInfo[] newArray(int i) {
                return new ViolationInfo[i];
            }
        };
        public String broadcastIntentAction;
        public int durationMillis;
        private final Deque<StackTraceElement[]> mBinderStack;
        private final int mPenaltyMask;
        private String mStackTrace;
        private final Violation mViolation;
        public int numAnimationsRunning;
        public long numInstances;
        public String[] tags;
        public int violationNumThisLoop;
        public long violationUptimeMillis;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        ViolationInfo(Violation violation, int i) {
            this.mBinderStack = new ArrayDeque();
            this.durationMillis = -1;
            int i2 = 0;
            this.numAnimationsRunning = 0;
            this.numInstances = -1L;
            this.mViolation = violation;
            this.mPenaltyMask = i;
            this.violationUptimeMillis = SystemClock.uptimeMillis();
            this.numAnimationsRunning = ValueAnimator.getCurrentAnimationsCount();
            Intent intentBeingBroadcast = ActivityThread.getIntentBeingBroadcast();
            if (intentBeingBroadcast != null) {
                this.broadcastIntentAction = intentBeingBroadcast.getAction();
            }
            ThreadSpanState threadSpanState = (ThreadSpanState) StrictMode.sThisThreadSpanState.get();
            if (violation instanceof InstanceCountViolation) {
                this.numInstances = ((InstanceCountViolation) violation).getNumberOfInstances();
            }
            synchronized (threadSpanState) {
                int i3 = threadSpanState.mActiveSize;
                i3 = i3 > 20 ? 20 : i3;
                if (i3 != 0) {
                    this.tags = new String[i3];
                    for (Span span = threadSpanState.mActiveHead; span != null && i2 < i3; span = span.mNext) {
                        this.tags[i2] = span.mName;
                        i2++;
                    }
                }
            }
        }

        public String getStackTrace() {
            if (this.mStackTrace == null) {
                StringWriter stringWriter = new StringWriter();
                FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) stringWriter, false, 256);
                this.mViolation.printStackTrace(fastPrintWriter);
                for (StackTraceElement[] stackTraceElementArr : this.mBinderStack) {
                    fastPrintWriter.append((CharSequence) "# via Binder call with stack:\n");
                    for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                        fastPrintWriter.append((CharSequence) "\tat ");
                        fastPrintWriter.append((CharSequence) stackTraceElement.toString());
                        fastPrintWriter.append('\n');
                    }
                }
                fastPrintWriter.flush();
                fastPrintWriter.close();
                this.mStackTrace = stringWriter.toString();
            }
            return this.mStackTrace;
        }

        public Class<? extends Violation> getViolationClass() {
            return this.mViolation.getClass();
        }

        public String getViolationDetails() {
            return this.mViolation.getMessage();
        }

        boolean penaltyEnabled(int i) {
            return (this.mPenaltyMask & i) != 0;
        }

        void addLocalStack(Throwable th) {
            this.mBinderStack.addFirst(th.getStackTrace());
        }

        public int hashCode() {
            Violation violation = this.mViolation;
            int hashCode = violation != null ? MetricsProto.MetricsEvent.TEXT_LONGPRESS + violation.hashCode() : 17;
            if (this.numAnimationsRunning != 0) {
                hashCode *= 37;
            }
            String str = this.broadcastIntentAction;
            if (str != null) {
                hashCode = (hashCode * 37) + str.hashCode();
            }
            String[] strArr = this.tags;
            if (strArr != null) {
                for (String str2 : strArr) {
                    hashCode = (hashCode * 37) + str2.hashCode();
                }
            }
            return hashCode;
        }

        public ViolationInfo(Parcel parcel) {
            this(parcel, false);
        }

        public ViolationInfo(Parcel parcel, boolean z) {
            this.mBinderStack = new ArrayDeque();
            this.durationMillis = -1;
            this.numAnimationsRunning = 0;
            this.numInstances = -1L;
            this.mViolation = (Violation) parcel.readSerializable(Violation.class.getClassLoader(), Violation.class);
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                int readInt2 = parcel.readInt();
                StackTraceElement[] stackTraceElementArr = new StackTraceElement[readInt2];
                for (int i2 = 0; i2 < readInt2; i2++) {
                    stackTraceElementArr[i2] = new StackTraceElement(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt());
                }
                this.mBinderStack.add(stackTraceElementArr);
            }
            int readInt3 = parcel.readInt();
            if (z) {
                this.mPenaltyMask = Integer.MAX_VALUE & readInt3;
            } else {
                this.mPenaltyMask = readInt3;
            }
            this.durationMillis = parcel.readInt();
            this.violationNumThisLoop = parcel.readInt();
            this.numAnimationsRunning = parcel.readInt();
            this.violationUptimeMillis = parcel.readLong();
            this.numInstances = parcel.readLong();
            this.broadcastIntentAction = parcel.readString();
            this.tags = parcel.readStringArray();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeSerializable(this.mViolation);
            parcel.writeInt(this.mBinderStack.size());
            for (StackTraceElement[] stackTraceElementArr : this.mBinderStack) {
                parcel.writeInt(stackTraceElementArr.length);
                for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                    parcel.writeString(stackTraceElement.getClassName());
                    parcel.writeString(stackTraceElement.getMethodName());
                    parcel.writeString(stackTraceElement.getFileName());
                    parcel.writeInt(stackTraceElement.getLineNumber());
                }
            }
            parcel.dataPosition();
            parcel.writeInt(this.mPenaltyMask);
            parcel.writeInt(this.durationMillis);
            parcel.writeInt(this.violationNumThisLoop);
            parcel.writeInt(this.numAnimationsRunning);
            parcel.writeLong(this.violationUptimeMillis);
            parcel.writeLong(this.numInstances);
            parcel.writeString(this.broadcastIntentAction);
            parcel.writeStringArray(this.tags);
            parcel.dataPosition();
        }

        public void dump(Printer printer, String str) {
            printer.println(str + "stackTrace: " + getStackTrace());
            printer.println(str + "penalty: " + this.mPenaltyMask);
            if (this.durationMillis != -1) {
                printer.println(str + "durationMillis: " + this.durationMillis);
            }
            if (this.numInstances != -1) {
                printer.println(str + "numInstances: " + this.numInstances);
            }
            if (this.violationNumThisLoop != 0) {
                printer.println(str + "violationNumThisLoop: " + this.violationNumThisLoop);
            }
            if (this.numAnimationsRunning != 0) {
                printer.println(str + "numAnimationsRunning: " + this.numAnimationsRunning);
            }
            printer.println(str + "violationUptimeMillis: " + this.violationUptimeMillis);
            if (this.broadcastIntentAction != null) {
                printer.println(str + "broadcastIntentAction: " + this.broadcastIntentAction);
            }
            String[] strArr = this.tags;
            if (strArr != null) {
                int length = strArr.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    printer.println(str + "tag[" + i2 + "]: " + strArr[i]);
                    i++;
                    i2++;
                }
            }
        }
    }

    private static final class InstanceTracker {
        private static final HashMap<Class<?>, Integer> sInstanceCounts = new HashMap<>();
        private final Class<?> mKlass;

        public InstanceTracker(Object obj) {
            Class<?> cls = obj.getClass();
            this.mKlass = cls;
            HashMap<Class<?>, Integer> hashMap = sInstanceCounts;
            synchronized (hashMap) {
                Integer num = hashMap.get(cls);
                hashMap.put(cls, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
            }
        }

        protected void finalize() throws Throwable {
            try {
                HashMap<Class<?>, Integer> hashMap = sInstanceCounts;
                synchronized (hashMap) {
                    Integer num = hashMap.get(this.mKlass);
                    if (num != null) {
                        int intValue = num.intValue() - 1;
                        if (intValue > 0) {
                            hashMap.put(this.mKlass, Integer.valueOf(intValue));
                        } else {
                            hashMap.remove(this.mKlass);
                        }
                    }
                }
            } finally {
                super.finalize();
            }
        }

        public static int getInstanceCount(Class<?> cls) {
            int intValue;
            HashMap<Class<?>, Integer> hashMap = sInstanceCounts;
            synchronized (hashMap) {
                Integer num = hashMap.get(cls);
                intValue = num != null ? num.intValue() : 0;
            }
            return intValue;
        }
    }
}
