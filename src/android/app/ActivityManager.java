package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.IActivityController;
import android.app.IActivityManager;
import android.app.IAppTask;
import android.app.IApplicationStartInfoCompleteListener;
import android.app.IProcessObserver;
import android.app.IUidFrozenStateChangedCallback;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IpcDataCache;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.security.keystore.KeyProperties;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Singleton;
import android.util.Size;
import com.android.internal.R;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.Preconditions;
import com.android.internal.util.RateLimitingCache;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ActivityManager {
    public static final String ACTION_REPORT_HEAP_LIMIT = "android.app.action.REPORT_HEAP_LIMIT";
    public static final int ADD_RECENT_PAIRED_TASKS = 64;
    public static final int APP_START_MODE_DELAYED = 1;
    public static final int APP_START_MODE_DELAYED_RIGID = 2;
    public static final int APP_START_MODE_DISABLED = 3;
    public static final int APP_START_MODE_NORMAL = 0;
    public static final int ASSIST_CONTEXT_AUTOFILL = 2;
    public static final int ASSIST_CONTEXT_BASIC = 0;
    public static final int ASSIST_CONTEXT_CONTENT = 3;
    public static final int ASSIST_CONTEXT_FULL = 1;
    public static final int BROADCAST_FAILED_USER_STOPPED = -2;
    public static final int BROADCAST_STICKY_CANT_HAVE_PERMISSION = -1;
    public static final int BROADCAST_SUCCESS = 0;
    public static final int COMPAT_MODE_ALWAYS = -1;
    public static final int COMPAT_MODE_DISABLED = 0;
    public static final int COMPAT_MODE_ENABLED = 1;
    public static final int COMPAT_MODE_NEVER = -2;
    public static final int COMPAT_MODE_TOGGLE = 2;
    public static final int COMPAT_MODE_UNKNOWN = -3;
    private static final boolean DEVELOPMENT_FORCE_LOW_RAM;
    public static final long DROP_CLOSE_SYSTEM_DIALOGS = 174664120;
    private static final int FIRST_START_FATAL_ERROR_CODE = -100;
    private static final int FIRST_START_NON_FATAL_ERROR_CODE = 100;
    private static final int FIRST_START_SUCCESS_CODE = 0;
    public static final int FLAG_AND_LOCKED = 2;
    public static final int FLAG_AND_UNLOCKED = 4;
    public static final int FLAG_AND_UNLOCKING_OR_UNLOCKED = 8;
    public static final int FLAG_OR_STOPPED = 1;
    public static final int FOREGROUND_SERVICE_API_EVENT_BEGIN = 1;
    public static final int FOREGROUND_SERVICE_API_EVENT_END = 2;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_AUDIO = 5;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_BLUETOOTH = 2;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_CAMERA = 1;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_CDM = 9;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_LOCATION = 3;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_MEDIA_PLAYBACK = 4;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_MICROPHONE = 6;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_PHONE_CALL = 7;

    @SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_USB = 8;
    private static final Singleton<IActivityManager> IActivityManagerSingleton;
    public static final int INSTR_FLAG_ALWAYS_CHECK_SIGNATURE = 16;
    public static final int INSTR_FLAG_DISABLE_HIDDEN_API_CHECKS = 1;
    public static final int INSTR_FLAG_DISABLE_ISOLATED_STORAGE = 2;
    public static final int INSTR_FLAG_DISABLE_TEST_API_CHECKS = 4;
    public static final int INSTR_FLAG_INSTRUMENT_SDK_IN_SANDBOX = 64;
    public static final int INSTR_FLAG_INSTRUMENT_SDK_SANDBOX = 32;
    public static final int INSTR_FLAG_NO_RESTART = 8;
    public static final int INTENT_SENDER_ACTIVITY = 2;
    public static final int INTENT_SENDER_ACTIVITY_RESULT = 3;
    public static final int INTENT_SENDER_BROADCAST = 1;
    public static final int INTENT_SENDER_FOREGROUND_SERVICE = 5;
    public static final int INTENT_SENDER_SERVICE = 4;
    public static final int INTENT_SENDER_UNKNOWN = 0;
    private static final int LAST_START_FATAL_ERROR_CODE = -1;
    private static final int LAST_START_NON_FATAL_ERROR_CODE = 199;
    private static final int LAST_START_SUCCESS_CODE = 99;
    public static final long LOCK_DOWN_CLOSE_SYSTEM_DIALOGS = 174664365;
    public static final int LOCK_TASK_MODE_LOCKED = 1;
    public static final int LOCK_TASK_MODE_NONE = 0;
    public static final int LOCK_TASK_MODE_PINNED = 2;
    public static final int MAX_PROCESS_STATE = 20;
    public static final String META_HOME_ALTERNATE = "android.app.home.alternate";
    public static final int MIN_PROCESS_STATE = 0;
    public static final int MOVE_TASK_NO_USER_ACTION = 2;
    public static final int MOVE_TASK_WITH_HOME = 1;
    public static final int PROCESS_CAPABILITY_ALL = 255;
    public static final int PROCESS_CAPABILITY_ALL_IMPLICIT = 6;
    public static final int PROCESS_CAPABILITY_BFSL = 16;
    public static final int PROCESS_CAPABILITY_CPU_TIME = 128;
    public static final int PROCESS_CAPABILITY_FOREGROUND_AUDIO_CONTROL = 64;

    @SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_CAMERA = 2;

    @SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_LOCATION = 1;

    @SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_MICROPHONE = 4;

    @SystemApi
    public static final int PROCESS_CAPABILITY_NONE = 0;
    public static final int PROCESS_CAPABILITY_POWER_RESTRICTED_NETWORK = 8;
    public static final int PROCESS_CAPABILITY_USER_RESTRICTED_NETWORK = 32;
    public static final int PROCESS_RESOURCE_VIEW = 1;
    public static final int PROCESS_STATE_BACKUP = 9;
    public static final int PROCESS_STATE_BOUND_FOREGROUND_SERVICE = 5;
    public static final int PROCESS_STATE_BOUND_TOP = 3;
    public static final int PROCESS_STATE_CACHED_ACTIVITY = 16;
    public static final int PROCESS_STATE_CACHED_ACTIVITY_CLIENT = 17;
    public static final int PROCESS_STATE_CACHED_EMPTY = 19;
    public static final int PROCESS_STATE_CACHED_RECENT = 18;
    public static final int PROCESS_STATE_FOREGROUND_SERVICE = 4;
    public static final int PROCESS_STATE_HEAVY_WEIGHT = 13;
    public static final int PROCESS_STATE_HOME = 14;
    public static final int PROCESS_STATE_IMPORTANT_BACKGROUND = 7;
    public static final int PROCESS_STATE_IMPORTANT_FOREGROUND = 6;
    public static final int PROCESS_STATE_LAST_ACTIVITY = 15;
    public static final int PROCESS_STATE_NONEXISTENT = 20;
    public static final int PROCESS_STATE_PERSISTENT = 0;
    public static final int PROCESS_STATE_PERSISTENT_UI = 1;
    public static final int PROCESS_STATE_RECEIVER = 11;
    public static final int PROCESS_STATE_SERVICE = 10;
    public static final int PROCESS_STATE_TOP = 2;
    public static final int PROCESS_STATE_TOP_SLEEPING = 12;
    public static final int PROCESS_STATE_TRANSIENT_BACKGROUND = 8;
    public static final int PROCESS_STATE_UNKNOWN = -1;
    public static final int RECENT_IGNORE_DESK_TYPE = 32;
    public static final int RECENT_IGNORE_UNAVAILABLE = 2;
    public static final int RECENT_WITH_ALIAS_TARGET = 16;
    public static final int RECENT_WITH_EXCLUDED = 1;
    public static final int REMOVED_FREEFORM_TASK_BY_DRAG = 128;
    public static final int REMOVE_ALL_RECENT_TASKS = 8;
    public static final int REMOVE_ALL_RECENT_TASKS_EXCEPT_TOP_TASK = 32;
    public static final int REMOVE_TASK_EXCEPT_RECENTS = 16;
    public static final int RESTRICTION_LEVEL_ADAPTIVE_BUCKET = 30;
    public static final int RESTRICTION_LEVEL_BACKGROUND_RESTRICTED = 50;
    public static final int RESTRICTION_LEVEL_CUSTOM = 90;
    public static final int RESTRICTION_LEVEL_EXEMPTED = 20;
    public static final int RESTRICTION_LEVEL_FORCE_STOPPED = 60;
    public static final int RESTRICTION_LEVEL_MAX = 100;
    public static final int RESTRICTION_LEVEL_RESTRICTED_BUCKET = 40;
    public static final int RESTRICTION_LEVEL_UNKNOWN = 0;
    public static final int RESTRICTION_LEVEL_UNRESTRICTED = 10;
    public static final int RESTRICTION_LEVEL_USER_LAUNCH_ONLY = 70;
    public static final int RESTRICTION_REASON_DEFAULT = 1;
    public static final int RESTRICTION_REASON_DORMANT = 2;
    public static final int RESTRICTION_REASON_OTHER = 7;
    public static final int RESTRICTION_REASON_POLICY = 6;
    public static final int RESTRICTION_REASON_SYSTEM_HEALTH = 5;
    public static final int RESTRICTION_REASON_USAGE = 3;
    public static final int RESTRICTION_REASON_USER = 4;
    public static final int RESTRICTION_SOURCE_COMMAND_LINE = 4;
    public static final int RESTRICTION_SOURCE_REMOTE_TRIGGER = 5;
    public static final int RESTRICTION_SOURCE_SYSTEM = 3;
    public static final int RESTRICTION_SOURCE_USER = 1;
    public static final int RESTRICTION_SOURCE_USER_NUDGED = 2;
    public static final int RESTRICTION_SUBREASON_MAX_LENGTH = 16;
    public static final int SEM_REMOVE_TASK_IMMEDIATELY = 4;
    public static final int START_ABORTED = 102;
    public static final int START_ASSISTANT_HIDDEN_SESSION = -90;
    public static final int START_ASSISTANT_NOT_ACTIVE_SESSION = -89;
    public static final int START_BLOCKED_BY_MARs = -200;
    public static final int START_BLOCKED_BY_MDM = -101;
    public static final int START_CANCELED = -96;
    public static final int START_CANCELLED_BY_BLOCK_3RD_JUMP = -103;
    public static final int START_CANCELLED_BY_TEMPERATURE = -102;
    public static final int START_CLASS_NOT_FOUND = -92;
    public static final int START_DELIVERED_TO_TOP = 3;
    public static final int START_FLAG_DEBUG = 2;
    public static final int START_FLAG_DEBUG_SUSPEND = 16;
    public static final int START_FLAG_NATIVE_DEBUGGING = 8;
    public static final int START_FLAG_ONLY_IF_NEEDED = 1;
    public static final int START_FLAG_TRACK_ALLOCATION = 4;
    public static final int START_FORWARD_AND_REQUEST_CONFLICT = -93;
    public static final int START_INTENT_NOT_RESOLVED = -91;
    public static final int START_NOT_ACTIVITY = -95;
    public static final int START_NOT_CURRENT_USER_ACTIVITY = -98;
    public static final int START_NOT_VOICE_COMPATIBLE = -97;
    public static final int START_PENDING_BY_MT_INTERCEPT = 10;
    public static final int START_PERMISSION_DENIED = -94;
    public static final int START_RETURN_INTENT_TO_CALLER = 1;
    public static final int START_RETURN_LOCK_TASK_MODE_VIOLATION = 101;
    public static final int START_SUCCESS = 0;
    public static final int START_SWITCHES_CANCELED = 100;
    public static final int START_TASK_TO_FRONT = 2;
    public static final int START_VOICE_HIDDEN_SESSION = -100;
    public static final int START_VOICE_NOT_ACTIVE_SESSION = -99;
    public static final int STOP_USER_ON_SWITCH_DEFAULT = -1;
    public static final int STOP_USER_ON_SWITCH_FALSE = 0;
    public static final int STOP_USER_ON_SWITCH_TRUE = 1;
    private static String TAG = "ActivityManager";
    public static final int UID_OBSERVER_ACTIVE = 8;
    public static final int UID_OBSERVER_CACHED = 16;
    public static final int UID_OBSERVER_CAPABILITY = 32;
    public static final int UID_OBSERVER_GONE = 2;
    public static final int UID_OBSERVER_IDLE = 4;
    public static final int UID_OBSERVER_PROCSTATE = 1;
    public static final int UID_OBSERVER_PROC_OOM_ADJ = 64;
    public static final int USER_OP_ERROR_IS_SYSTEM = -3;
    public static final int USER_OP_ERROR_RELATED_USERS_CANNOT_STOP = -4;
    public static final int USER_OP_IS_CURRENT = -2;
    public static final int USER_OP_SUCCESS = 0;
    public static final int USER_OP_UNKNOWN_USER = -1;
    private static final IpcDataCache<Void, Integer> mGetCurrentUserIdCache;
    private static final IpcDataCache.QueryHandler<Void, Integer> mGetCurrentUserIdQuery;
    private static volatile int sCurrentUser$ravenwood = 0;
    private static volatile boolean sSystemReady = false;
    private ActivityController mActivityController;
    Point mAppTaskThumbnailSize;
    private final Context mContext;
    private static final RateLimitingCache<List<RunningAppProcessInfo>> mRunningProcessesCache = new RateLimitingCache<>(10, 4);
    private static final RateLimitingCache<List<ProcessErrorStateInfo>> mErrorProcessesCache = new RateLimitingCache<>(10, 2);
    private static final RateLimitingCache<MemoryInfo> mMemoryInfoCache = new RateLimitingCache<>(10);
    private static final MemoryInfo mRateLimitedMemInfo = new MemoryInfo();
    private static final RateLimitingCache<RunningAppProcessInfo> mMyMemoryStateCache = new RateLimitingCache<>(10, 2);
    private static final RunningAppProcessInfo mRateLimitedMemState = new RunningAppProcessInfo();
    final ArrayMap<OnUidImportanceListener, MyUidObserver> mImportanceListeners = new ArrayMap<>();
    private final ArrayMap<UidFrozenStateChangedCallback, Executor> mFrozenStateChangedCallbacks = new ArrayMap<>();
    private final IUidFrozenStateChangedCallback mFrozenStateChangedCallback = new AnonymousClass2();
    private final Map<SemProcessListener, ProcessObserver> mProcessObserverMap = new HashMap();
    private final CopyOnWriteArrayList<SemProcessListener> mProcessListeners = new CopyOnWriteArrayList<>();
    private final ArrayList<AppStartInfoCallbackWrapper> mAppStartInfoCallbacks = new ArrayList<>();
    private IApplicationStartInfoCompleteListener mAppStartInfoCompleteListener = null;
    private final CopyOnWriteArrayList<SemActivityControllerListener> mActivityControllerListeners = new CopyOnWriteArrayList<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForegroundServiceApiEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForegroundServiceApiType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MoveTaskFlags {
    }

    @SystemApi
    public interface OnUidImportanceListener {
        void onUidImportance(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessResource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RestrictionLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RestrictionReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RestrictionSource {
    }

    public interface SemActivityControllerListener {
        boolean onActivityResuming(String str);

        boolean onActivityStarting(Intent intent, String str);

        boolean onAppCrashed(String str, int i, String str2, String str3, long j, String str4);

        int onAppEarlyNotResponding(String str, int i, String str2);

        int onAppNotResponding(String str, int i, String str2);

        int onSystemNotResponding(String str);
    }

    public interface SemProcessListener {
        void onForegroundActivitiesChanged(int i, int i2, boolean z);

        void onProcessDied(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StopUserOnSwitch {
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public interface UidFrozenStateChangedCallback {

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static final int UID_FROZEN_STATE_FROZEN = 1;

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static final int UID_FROZEN_STATE_UNFROZEN = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface UidFrozenState {
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        void onUidFrozenStateChanged(int[] iArr, int[] iArr2);
    }

    public static boolean isForegroundService(int i) {
        return i == 4;
    }

    public static final boolean isProcStateBackground(int i) {
        return i >= 8;
    }

    public static final boolean isProcStateCached(int i) {
        return i >= 16;
    }

    public static boolean isProcStateConsideredInteraction(int i) {
        return i <= 2 || i == 3;
    }

    public static final boolean isStartResultFatalError(int i) {
        return -100 <= i && i <= -1;
    }

    public static final boolean isStartResultSuccessful(int i) {
        return i >= 0 && i <= 99;
    }

    public static boolean isSystemReady$ravenwood() {
        return true;
    }

    public static boolean isUserAMonkey$ravenwood() {
        return false;
    }

    public static final int processStateAmToProto(int i) {
        switch (i) {
            case -1:
                return 999;
            case 0:
                return 1000;
            case 1:
                return 1001;
            case 2:
                return 1002;
            case 3:
                return 1020;
            case 4:
                return 1003;
            case 5:
                return 1004;
            case 6:
                return 1005;
            case 7:
                return 1006;
            case 8:
                return 1007;
            case 9:
                return 1008;
            case 10:
                return 1009;
            case 11:
                return 1010;
            case 12:
                return 1011;
            case 13:
                return 1012;
            case 14:
                return 1013;
            case 15:
                return 1014;
            case 16:
                return 1015;
            case 17:
                return 1016;
            case 18:
                return 1017;
            case 19:
                return 1018;
            case 20:
                return 1019;
            default:
                return 998;
        }
    }

    public void semKeepKeyguardWaitingForActivityDrawn() {
    }

    static final class MyUidObserver extends UidObserver {
        final Context mContext;
        final OnUidImportanceListener mListener;

        MyUidObserver(OnUidImportanceListener onUidImportanceListener, Context context) {
            this.mListener = onUidImportanceListener;
            this.mContext = context;
        }

        @Override // android.app.UidObserver, android.app.IUidObserver
        public void onUidStateChanged(int i, int i2, long j, int i3) {
            this.mListener.onUidImportance(i, RunningAppProcessInfo.procStateToImportanceForClient(i2, this.mContext));
        }

        @Override // android.app.UidObserver, android.app.IUidObserver
        public void onUidGone(int i, boolean z) {
            this.mListener.onUidImportance(i, 1000);
        }
    }

    static {
        IpcDataCache.QueryHandler<Void, Integer> queryHandler = new IpcDataCache.QueryHandler<Void, Integer>() { // from class: android.app.ActivityManager.1
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(Void r1) {
                try {
                    return Integer.valueOf(ActivityManager.getService().getCurrentUserId());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(Void r1) {
                return !Flags.cacheGetCurrentUserId();
            }
        };
        mGetCurrentUserIdQuery = queryHandler;
        mGetCurrentUserIdCache = new IpcDataCache<>(1, "system_server", "getCurrentUserId", "CurrentUserIdCache", queryHandler);
        sCurrentUser$ravenwood = -10000;
        DEVELOPMENT_FORCE_LOW_RAM = SystemProperties.getBoolean("debug.force_low_ram", false);
        IActivityManagerSingleton = new Singleton<IActivityManager>() { // from class: android.app.ActivityManager.4
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Singleton
            public IActivityManager create() {
                return IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
            }
        };
    }

    public static void invalidateGetCurrentUserIdCache() {
        IpcDataCache.invalidateCache("system_server", "getCurrentUserId");
    }

    /* renamed from: android.app.ActivityManager$2, reason: invalid class name */
    class AnonymousClass2 extends IUidFrozenStateChangedCallback.Stub {
        AnonymousClass2() {
        }

        @Override // android.app.IUidFrozenStateChangedCallback
        public void onUidFrozenStateChanged(final int[] iArr, final int[] iArr2) {
            synchronized (ActivityManager.this.mFrozenStateChangedCallbacks) {
                ActivityManager.this.mFrozenStateChangedCallbacks.forEach(new BiConsumer() { // from class: android.app.ActivityManager$2$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Executor executor = (Executor) obj2;
                        executor.execute(new Runnable() { // from class: android.app.ActivityManager$2$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActivityManager.UidFrozenStateChangedCallback.this.onUidFrozenStateChanged(r2, r3);
                            }
                        });
                    }
                });
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void registerUidFrozenStateChangedCallback(Executor executor, UidFrozenStateChangedCallback uidFrozenStateChangedCallback) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(uidFrozenStateChangedCallback, "callback cannot be null");
        synchronized (this.mFrozenStateChangedCallbacks) {
            if (this.mFrozenStateChangedCallbacks.containsKey(uidFrozenStateChangedCallback)) {
                throw new IllegalStateException("Callback already registered: " + uidFrozenStateChangedCallback);
            }
            this.mFrozenStateChangedCallbacks.put(uidFrozenStateChangedCallback, executor);
            if (this.mFrozenStateChangedCallbacks.size() > 1) {
                return;
            }
            try {
                getService().registerUidFrozenStateChangedCallback(this.mFrozenStateChangedCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void unregisterUidFrozenStateChangedCallback(UidFrozenStateChangedCallback uidFrozenStateChangedCallback) {
        Preconditions.checkNotNull(uidFrozenStateChangedCallback, "callback cannot be null");
        synchronized (this.mFrozenStateChangedCallbacks) {
            this.mFrozenStateChangedCallbacks.remove(uidFrozenStateChangedCallback);
            if (this.mFrozenStateChangedCallbacks.isEmpty()) {
                try {
                    getService().unregisterUidFrozenStateChangedCallback(this.mFrozenStateChangedCallback);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int[] getUidFrozenState(int[] iArr) {
        try {
            return getService().getUidFrozenState(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void printCapabilitiesSummary(PrintWriter printWriter, int i) {
        printWriter.print((i & 1) != 0 ? DateFormat.STANDALONE_MONTH : '-');
        printWriter.print((i & 2) != 0 ? 'C' : '-');
        printWriter.print((i & 4) != 0 ? DateFormat.MONTH : '-');
        printWriter.print((i & 8) != 0 ? PhoneNumberUtils.WILD : '-');
        printWriter.print((i & 16) != 0 ? 'F' : '-');
        printWriter.print((i & 32) != 0 ? 'U' : '-');
        printWriter.print((i & 64) != 0 ? DateFormat.CAPITAL_AM_PM : '-');
        printWriter.print((i & 128) != 0 ? 'T' : '-');
    }

    public static void printCapabilitiesSummary(StringBuilder sb, int i) {
        sb.append((i & 1) != 0 ? DateFormat.STANDALONE_MONTH : '-');
        sb.append((i & 2) != 0 ? 'C' : '-');
        sb.append((i & 4) != 0 ? DateFormat.MONTH : '-');
        sb.append((i & 8) != 0 ? PhoneNumberUtils.WILD : '-');
        sb.append((i & 16) != 0 ? 'F' : '-');
        sb.append((i & 32) != 0 ? 'U' : '-');
        sb.append((i & 64) != 0 ? DateFormat.CAPITAL_AM_PM : '-');
        sb.append((i & 128) != 0 ? 'T' : '-');
    }

    public static void printCapabilitiesFull(PrintWriter printWriter, int i) {
        printCapabilitiesSummary(printWriter, i);
        int i2 = i & (-256);
        if (i2 != 0) {
            printWriter.print("+0x");
            printWriter.print(Integer.toHexString(i2));
        }
    }

    public static String getCapabilitiesSummary(int i) {
        StringBuilder sb = new StringBuilder();
        printCapabilitiesSummary(sb, i);
        return sb.toString();
    }

    public static final boolean isProcStateJankPerceptible(int i) {
        if (!Flags.jankPerceptibleNarrow() || Flags.jankPerceptibleNarrowHoldback()) {
            return !isProcStateCached(i);
        }
        return i == 1 || i == 2 || i == 6 || i == 12;
    }

    private class ProcessObserver extends IProcessObserver.Stub {
        @Override // android.app.IProcessObserver
        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        @Override // android.app.IProcessObserver
        public void onProcessStarted(int i, int i2, int i3, String str, String str2) {
        }

        private ProcessObserver() {
        }

        @Override // android.app.IProcessObserver
        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            if (ActivityManager.this.mProcessListeners == null || ActivityManager.this.mProcessListeners.isEmpty()) {
                return;
            }
            Iterator it = ActivityManager.this.mProcessListeners.iterator();
            while (it.hasNext()) {
                ((SemProcessListener) it.next()).onForegroundActivitiesChanged(i, i2, z);
            }
        }

        @Override // android.app.IProcessObserver
        public void onProcessDied(int i, int i2) {
            if (ActivityManager.this.mProcessListeners == null || ActivityManager.this.mProcessListeners.isEmpty()) {
                return;
            }
            Iterator it = ActivityManager.this.mProcessListeners.iterator();
            while (it.hasNext()) {
                ((SemProcessListener) it.next()).onProcessDied(i, i2);
            }
        }
    }

    ActivityManager(Context context, Handler handler) {
        this.mContext = context;
    }

    public static String restrictionLevelToName(int i) {
        if (i == 0) {
            return "unknown";
        }
        if (i == 10) {
            return "unrestricted";
        }
        if (i == 20) {
            return "exempted";
        }
        if (i == 30) {
            return "adaptive_bucket";
        }
        if (i == 40) {
            return "restricted_bucket";
        }
        if (i == 50) {
            return "background_restricted";
        }
        if (i == 60) {
            return "stopped";
        }
        if (i == 70) {
            return "user_only";
        }
        if (i == 90) {
            return "custom";
        }
        if (i == 100) {
            return "max";
        }
        return String.valueOf(i);
    }

    public int getFrontActivityScreenCompatMode() {
        try {
            return getTaskService().getFrontActivityScreenCompatMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFrontActivityScreenCompatMode(int i) {
        try {
            getTaskService().setFrontActivityScreenCompatMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPackageScreenCompatMode(String str) {
        try {
            return getTaskService().getPackageScreenCompatMode(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageScreenCompatMode(String str, int i) {
        try {
            getTaskService().setPackageScreenCompatMode(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getPackageAskScreenCompat(String str) {
        try {
            return getTaskService().getPackageAskScreenCompat(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageAskScreenCompat(String str, boolean z) {
        try {
            getTaskService().setPackageAskScreenCompat(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMemoryClass() {
        return staticGetMemoryClass();
    }

    public static int staticGetMemoryClass() {
        String str = SystemProperties.get("dalvik.vm.heapgrowthlimit", "");
        if (str != null && !"".equals(str)) {
            return Integer.parseInt(str.substring(0, str.length() - 1));
        }
        return staticGetLargeMemoryClass();
    }

    public int getLargeMemoryClass() {
        return staticGetLargeMemoryClass();
    }

    public static int staticGetLargeMemoryClass() {
        return Integer.parseInt(SystemProperties.get("dalvik.vm.heapsize", "16m").substring(0, r0.length() - 1));
    }

    public boolean isLowRamDevice() {
        return isLowRamDeviceStatic();
    }

    public static boolean isLowRamDeviceStatic() {
        if (RoSystemProperties.CONFIG_LOW_RAM) {
            return true;
        }
        return Build.IS_DEBUGGABLE && DEVELOPMENT_FORCE_LOW_RAM;
    }

    public static boolean isSmallBatteryDevice() {
        return RoSystemProperties.CONFIG_SMALL_BATTERY;
    }

    public static boolean isHighEndGfx() {
        return (isLowRamDeviceStatic() || RoSystemProperties.CONFIG_AVOID_GFX_ACCEL || Resources.getSystem().getBoolean(R.bool.config_avoidGfxAccel)) ? false : true;
    }

    public long getTotalRam() {
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        return memInfoReader.getTotalSize();
    }

    @Deprecated
    public static int getMaxRecentTasksStatic() {
        return ActivityTaskManager.getMaxRecentTasksStatic();
    }

    public static class TaskDescription implements Parcelable {
        private static final String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND = "task_description_color_background";
        private static final String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING = "task_description_color_background_floating";
        private static final String ATTR_TASKDESCRIPTIONCOLOR_PRIMARY = "task_description_color";
        private static final String ATTR_TASKDESCRIPTIONICON_FILENAME = "task_description_icon_filename";
        private static final String ATTR_TASKDESCRIPTIONICON_RESOURCE = "task_description_icon_resource";
        private static final String ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE = "task_description_icon_package";
        private static final String ATTR_TASKDESCRIPTIONLABEL = "task_description_label";
        public static final String ATTR_TASKDESCRIPTION_PREFIX = "task_description_";
        public static final Parcelable.Creator<TaskDescription> CREATOR = new Parcelable.Creator<TaskDescription>() { // from class: android.app.ActivityManager.TaskDescription.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TaskDescription createFromParcel(Parcel parcel) {
                return new TaskDescription(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TaskDescription[] newArray(int i) {
                return new TaskDescription[i];
            }
        };
        private int mColorBackground;
        private int mColorBackgroundFloating;
        private int mColorPrimary;
        private boolean mColorsAreDetermined;
        private boolean mEnsureNavigationBarContrastWhenTransparent;
        private boolean mEnsureStatusBarContrastWhenTransparent;
        private Icon mIcon;
        private String mIconFilename;
        private String mLabel;
        private int mMinHeight;
        private int mMinWidth;
        private int mNavigationBarColor;
        private int mResizeMode;
        private int mStatusBarColor;
        private int mSystemBarsAppearance;
        private int mTopOpaqueSystemBarsAppearance;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static final class Builder {
            private String mLabel = null;
            private int mIconRes = 0;
            private int mPrimaryColor = 0;
            private int mBackgroundColor = 0;
            private int mStatusBarColor = 0;
            private int mNavigationBarColor = 0;

            public Builder setLabel(String str) {
                this.mLabel = str;
                return this;
            }

            public Builder setIcon(int i) {
                this.mIconRes = i;
                return this;
            }

            public Builder setPrimaryColor(int i) {
                this.mPrimaryColor = i;
                return this;
            }

            public Builder setBackgroundColor(int i) {
                this.mBackgroundColor = i;
                return this;
            }

            public Builder setStatusBarColor(int i) {
                this.mStatusBarColor = i;
                return this;
            }

            public Builder setNavigationBarColor(int i) {
                this.mNavigationBarColor = i;
                return this;
            }

            public TaskDescription build() {
                return new TaskDescription(this.mLabel, this.mIconRes == 0 ? null : Icon.createWithResource(ActivityThread.currentPackageName(), this.mIconRes), this.mPrimaryColor, this.mBackgroundColor, this.mStatusBarColor, this.mNavigationBarColor, 0, 0, false, false, 2, -1, -1, 0);
            }
        }

        @Deprecated
        public TaskDescription(String str, int i, int i2) {
            this(str, Icon.createWithResource(ActivityThread.currentPackageName(), i), i2, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
            if (i2 != 0 && Color.alpha(i2) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @Deprecated
        public TaskDescription(String str, int i) {
            this(str, Icon.createWithResource(ActivityThread.currentPackageName(), i), 0, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @Deprecated
        public TaskDescription(String str) {
            this(str, null, 0, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @Deprecated
        public TaskDescription() {
            this(null, null, 0, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @Deprecated
        public TaskDescription(String str, Bitmap bitmap, int i) {
            this(str, bitmap != null ? Icon.createWithBitmap(bitmap) : null, i, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
            if (i != 0 && Color.alpha(i) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @Deprecated
        public TaskDescription(String str, Bitmap bitmap) {
            this(str, bitmap != null ? Icon.createWithBitmap(bitmap) : null, 0, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        public TaskDescription(String str, Icon icon, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, int i7, int i8, int i9, int i10) {
            this.mLabel = str;
            this.mIcon = icon;
            this.mColorPrimary = i;
            this.mColorBackground = i2;
            this.mStatusBarColor = i3;
            this.mNavigationBarColor = i4;
            this.mSystemBarsAppearance = i5;
            this.mTopOpaqueSystemBarsAppearance = i6;
            this.mEnsureStatusBarContrastWhenTransparent = z;
            this.mEnsureNavigationBarContrastWhenTransparent = z2;
            this.mResizeMode = i7;
            this.mMinWidth = i8;
            this.mMinHeight = i9;
            this.mColorBackgroundFloating = i10;
        }

        public TaskDescription(TaskDescription taskDescription) {
            copyFrom(taskDescription);
        }

        public void copyFrom(TaskDescription taskDescription) {
            this.mLabel = taskDescription.mLabel;
            this.mIcon = taskDescription.mIcon;
            this.mIconFilename = taskDescription.mIconFilename;
            this.mColorPrimary = taskDescription.mColorPrimary;
            this.mColorBackground = taskDescription.mColorBackground;
            this.mStatusBarColor = taskDescription.mStatusBarColor;
            this.mNavigationBarColor = taskDescription.mNavigationBarColor;
            this.mSystemBarsAppearance = taskDescription.mSystemBarsAppearance;
            this.mTopOpaqueSystemBarsAppearance = taskDescription.mTopOpaqueSystemBarsAppearance;
            this.mEnsureStatusBarContrastWhenTransparent = taskDescription.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent = taskDescription.mEnsureNavigationBarContrastWhenTransparent;
            this.mResizeMode = taskDescription.mResizeMode;
            this.mMinWidth = taskDescription.mMinWidth;
            this.mMinHeight = taskDescription.mMinHeight;
            this.mColorBackgroundFloating = taskDescription.mColorBackgroundFloating;
        }

        public void copyFromPreserveHiddenFields(TaskDescription taskDescription) {
            this.mLabel = taskDescription.mLabel;
            this.mIcon = taskDescription.mIcon;
            this.mIconFilename = taskDescription.mIconFilename;
            this.mColorPrimary = taskDescription.mColorPrimary;
            int i = taskDescription.mColorBackground;
            if (i != 0) {
                this.mColorBackground = i;
            }
            int i2 = taskDescription.mStatusBarColor;
            if (i2 != 0) {
                this.mStatusBarColor = i2;
            }
            int i3 = taskDescription.mNavigationBarColor;
            if (i3 != 0) {
                this.mNavigationBarColor = i3;
            }
            int i4 = taskDescription.mSystemBarsAppearance;
            if (i4 != 0) {
                this.mSystemBarsAppearance = i4;
            }
            int i5 = taskDescription.mTopOpaqueSystemBarsAppearance;
            if (i5 != 0) {
                this.mTopOpaqueSystemBarsAppearance = i5;
            }
            this.mEnsureStatusBarContrastWhenTransparent = taskDescription.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent = taskDescription.mEnsureNavigationBarContrastWhenTransparent;
            int i6 = taskDescription.mResizeMode;
            if (i6 != 2) {
                this.mResizeMode = i6;
            }
            int i7 = taskDescription.mMinWidth;
            if (i7 != -1) {
                this.mMinWidth = i7;
            }
            int i8 = taskDescription.mMinHeight;
            if (i8 != -1) {
                this.mMinHeight = i8;
            }
            int i9 = taskDescription.mColorBackgroundFloating;
            if (i9 != 0) {
                this.mColorBackgroundFloating = i9;
            }
        }

        private TaskDescription(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void setLabel(String str) {
            this.mLabel = str;
        }

        public void setPrimaryColor(int i) {
            if (i != 0 && Color.alpha(i) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
            this.mColorPrimary = i;
        }

        public void setBackgroundColor(int i) {
            if (i != 0 && Color.alpha(i) != 255) {
                throw new RuntimeException("A TaskDescription's background color should be opaque");
            }
            this.mColorBackground = i;
        }

        public void setBackgroundColorFloating(int i) {
            if (i != 0 && Color.alpha(i) != 255) {
                throw new RuntimeException("A TaskDescription's background color floating should be opaque");
            }
            this.mColorBackgroundFloating = i;
        }

        public void setStatusBarColor(int i) {
            this.mStatusBarColor = i;
        }

        public void setNavigationBarColor(int i) {
            this.mNavigationBarColor = i;
        }

        public void setIcon(Icon icon) {
            this.mIcon = icon;
        }

        public void setIconFilename(String str) {
            this.mIconFilename = str;
            if (str != null) {
                this.mIcon = null;
            }
        }

        public void setResizeMode(int i) {
            this.mResizeMode = i;
        }

        public void setMinWidth(int i) {
            this.mMinWidth = i;
        }

        public void setMinHeight(int i) {
            this.mMinHeight = i;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public Icon loadIcon() {
            Icon icon = this.mIcon;
            if (icon != null) {
                return icon;
            }
            Bitmap loadTaskDescriptionIcon = loadTaskDescriptionIcon(this.mIconFilename, UserHandle.myUserId());
            if (loadTaskDescriptionIcon != null) {
                return Icon.createWithBitmap(loadTaskDescriptionIcon);
            }
            return null;
        }

        @Deprecated
        public Bitmap getIcon() {
            Bitmap inMemoryIcon = getInMemoryIcon();
            return inMemoryIcon != null ? inMemoryIcon : loadTaskDescriptionIcon(this.mIconFilename, UserHandle.myUserId());
        }

        public Icon getRawIcon() {
            return this.mIcon;
        }

        public String getIconResourcePackage() {
            Icon icon = this.mIcon;
            if (icon != null && icon.getType() == 2) {
                return this.mIcon.getResPackage();
            }
            return "";
        }

        public int getIconResource() {
            Icon icon = this.mIcon;
            if (icon == null || icon.getType() != 2) {
                return 0;
            }
            return this.mIcon.getResId();
        }

        public String getIconFilename() {
            return this.mIconFilename;
        }

        public Bitmap getInMemoryIcon() {
            Icon icon = this.mIcon;
            if (icon == null || icon.getType() != 1) {
                return null;
            }
            return this.mIcon.getBitmap();
        }

        public static Bitmap loadTaskDescriptionIcon(String str, int i) {
            if (str == null) {
                return null;
            }
            try {
                return ActivityManager.getTaskService().getTaskDescriptionIcon(str, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getPrimaryColor() {
            return this.mColorPrimary;
        }

        public int getBackgroundColor() {
            return this.mColorBackground;
        }

        public int getBackgroundColorFloating() {
            return this.mColorBackgroundFloating;
        }

        public int getStatusBarColor() {
            return this.mStatusBarColor;
        }

        public int getNavigationBarColor() {
            return this.mNavigationBarColor;
        }

        public boolean getEnsureStatusBarContrastWhenTransparent() {
            return this.mEnsureStatusBarContrastWhenTransparent;
        }

        public int getSystemBarsAppearance() {
            return this.mSystemBarsAppearance;
        }

        public int getTopOpaqueSystemBarsAppearance() {
            return this.mTopOpaqueSystemBarsAppearance;
        }

        public void setEnsureStatusBarContrastWhenTransparent(boolean z) {
            this.mEnsureStatusBarContrastWhenTransparent = z;
        }

        public void setSystemBarsAppearance(int i) {
            this.mSystemBarsAppearance = i;
        }

        public void setTopOpaqueSystemBarsAppearance(int i) {
            this.mTopOpaqueSystemBarsAppearance = i;
        }

        public boolean getEnsureNavigationBarContrastWhenTransparent() {
            return this.mEnsureNavigationBarContrastWhenTransparent;
        }

        public void setEnsureNavigationBarContrastWhenTransparent(boolean z) {
            this.mEnsureNavigationBarContrastWhenTransparent = z;
        }

        public int getResizeMode() {
            return this.mResizeMode;
        }

        public int getMinWidth() {
            return this.mMinWidth;
        }

        public int getMinHeight() {
            return this.mMinHeight;
        }

        public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
            String str = this.mLabel;
            if (str != null) {
                typedXmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONLABEL, str);
            }
            int i = this.mColorPrimary;
            if (i != 0) {
                typedXmlSerializer.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, i);
            }
            int i2 = this.mColorBackground;
            if (i2 != 0) {
                typedXmlSerializer.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, i2);
            }
            int i3 = this.mColorBackgroundFloating;
            if (i3 != 0) {
                typedXmlSerializer.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING, i3);
            }
            String str2 = this.mIconFilename;
            if (str2 != null) {
                typedXmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONICON_FILENAME, str2);
            }
            Icon icon = this.mIcon;
            if (icon == null || icon.getType() != 2) {
                return;
            }
            typedXmlSerializer.attributeInt(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, this.mIcon.getResId());
            typedXmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE, this.mIcon.getResPackage());
        }

        public void restoreFromXml(TypedXmlPullParser typedXmlPullParser) {
            String attributeValue = typedXmlPullParser.getAttributeValue(null, ATTR_TASKDESCRIPTIONLABEL);
            if (attributeValue != null) {
                setLabel(attributeValue);
            }
            int attributeIntHex = typedXmlPullParser.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, 0);
            if (attributeIntHex != 0) {
                setPrimaryColor(attributeIntHex);
            }
            int attributeIntHex2 = typedXmlPullParser.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, 0);
            if (attributeIntHex2 != 0) {
                setBackgroundColor(attributeIntHex2);
            }
            int attributeIntHex3 = typedXmlPullParser.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING, 0);
            if (attributeIntHex3 != 0) {
                setBackgroundColorFloating(attributeIntHex3);
            }
            String attributeValue2 = typedXmlPullParser.getAttributeValue(null, ATTR_TASKDESCRIPTIONICON_FILENAME);
            if (attributeValue2 != null) {
                setIconFilename(attributeValue2);
            }
            int attributeInt = typedXmlPullParser.getAttributeInt(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, 0);
            String attributeValue3 = typedXmlPullParser.getAttributeValue(null, ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE);
            if (attributeInt == 0 || attributeValue3 == null) {
                return;
            }
            setIcon(Icon.createWithResource(attributeValue3, attributeInt));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            if (this.mLabel == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeString(this.mLabel);
            }
            Bitmap inMemoryIcon = getInMemoryIcon();
            if (this.mIcon == null || (inMemoryIcon != null && inMemoryIcon.isRecycled())) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.mIcon.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mColorPrimary);
            parcel.writeInt(this.mColorBackground);
            parcel.writeInt(this.mStatusBarColor);
            parcel.writeInt(this.mNavigationBarColor);
            parcel.writeInt(this.mSystemBarsAppearance);
            parcel.writeInt(this.mTopOpaqueSystemBarsAppearance);
            parcel.writeBoolean(this.mEnsureStatusBarContrastWhenTransparent);
            parcel.writeBoolean(this.mEnsureNavigationBarContrastWhenTransparent);
            parcel.writeInt(this.mResizeMode);
            parcel.writeInt(this.mMinWidth);
            parcel.writeInt(this.mMinHeight);
            if (this.mIconFilename == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeString(this.mIconFilename);
            }
            parcel.writeInt(this.mColorBackgroundFloating);
        }

        public void readFromParcel(Parcel parcel) {
            this.mLabel = parcel.readInt() > 0 ? parcel.readString() : null;
            if (parcel.readInt() > 0) {
                this.mIcon = Icon.CREATOR.createFromParcel(parcel);
            }
            this.mColorPrimary = parcel.readInt();
            this.mColorBackground = parcel.readInt();
            this.mStatusBarColor = parcel.readInt();
            this.mNavigationBarColor = parcel.readInt();
            this.mSystemBarsAppearance = parcel.readInt();
            this.mTopOpaqueSystemBarsAppearance = parcel.readInt();
            this.mEnsureStatusBarContrastWhenTransparent = parcel.readBoolean();
            this.mEnsureNavigationBarContrastWhenTransparent = parcel.readBoolean();
            this.mResizeMode = parcel.readInt();
            this.mMinWidth = parcel.readInt();
            this.mMinHeight = parcel.readInt();
            this.mIconFilename = parcel.readInt() > 0 ? parcel.readString() : null;
            this.mColorBackgroundFloating = parcel.readInt();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("TaskDescription Label: ");
            sb.append(this.mLabel);
            sb.append(" Icon: ");
            sb.append(this.mIcon);
            sb.append(" IconFilename: ");
            sb.append(this.mIconFilename);
            sb.append(" colorPrimary: ");
            sb.append(this.mColorPrimary);
            sb.append(" colorBackground: ");
            sb.append(this.mColorBackground);
            sb.append(" statusBarColor: ");
            sb.append(this.mStatusBarColor);
            sb.append(this.mEnsureStatusBarContrastWhenTransparent ? " (contrast when transparent)" : "");
            sb.append(" navigationBarColor: ");
            sb.append(this.mNavigationBarColor);
            sb.append(this.mEnsureNavigationBarContrastWhenTransparent ? " (contrast when transparent)" : "");
            sb.append(" resizeMode: ");
            sb.append(ActivityInfo.resizeModeToString(this.mResizeMode));
            sb.append(" minWidth: ");
            sb.append(this.mMinWidth);
            sb.append(" minHeight: ");
            sb.append(this.mMinHeight);
            sb.append(" colorBackgrounFloating: ");
            sb.append(this.mColorBackgroundFloating);
            sb.append(" systemBarsAppearance: ");
            sb.append(this.mSystemBarsAppearance);
            sb.append(" topOpaqueSystemBarsAppearance: ");
            sb.append(this.mTopOpaqueSystemBarsAppearance);
            return sb.toString();
        }

        public int hashCode() {
            String str = this.mLabel;
            int hashCode = str != null ? 527 + str.hashCode() : 17;
            Icon icon = this.mIcon;
            if (icon != null) {
                hashCode = (hashCode * 31) + icon.hashCode();
            }
            String str2 = this.mIconFilename;
            if (str2 != null) {
                hashCode = (hashCode * 31) + str2.hashCode();
            }
            return (((((((((((((((((((((((hashCode * 31) + this.mColorPrimary) * 31) + this.mColorBackground) * 31) + this.mColorBackgroundFloating) * 31) + this.mStatusBarColor) * 31) + this.mNavigationBarColor) * 31) + this.mSystemBarsAppearance) * 31) + this.mTopOpaqueSystemBarsAppearance) * 31) + (this.mEnsureStatusBarContrastWhenTransparent ? 1 : 0)) * 31) + (this.mEnsureNavigationBarContrastWhenTransparent ? 1 : 0)) * 31) + this.mResizeMode) * 31) + this.mMinWidth) * 31) + this.mMinHeight;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TaskDescription)) {
                return false;
            }
            TaskDescription taskDescription = (TaskDescription) obj;
            return TextUtils.equals(this.mLabel, taskDescription.mLabel) && TextUtils.equals(this.mIconFilename, taskDescription.mIconFilename) && this.mIcon == taskDescription.mIcon && this.mColorPrimary == taskDescription.mColorPrimary && this.mColorBackground == taskDescription.mColorBackground && this.mStatusBarColor == taskDescription.mStatusBarColor && this.mNavigationBarColor == taskDescription.mNavigationBarColor && this.mSystemBarsAppearance == taskDescription.mSystemBarsAppearance && this.mTopOpaqueSystemBarsAppearance == taskDescription.mTopOpaqueSystemBarsAppearance && this.mEnsureStatusBarContrastWhenTransparent == taskDescription.mEnsureStatusBarContrastWhenTransparent && this.mEnsureNavigationBarContrastWhenTransparent == taskDescription.mEnsureNavigationBarContrastWhenTransparent && this.mResizeMode == taskDescription.mResizeMode && this.mMinWidth == taskDescription.mMinWidth && this.mMinHeight == taskDescription.mMinHeight && this.mColorBackgroundFloating == taskDescription.mColorBackgroundFloating;
        }

        public static boolean equals(TaskDescription taskDescription, TaskDescription taskDescription2) {
            if (taskDescription == null && taskDescription2 == null) {
                return true;
            }
            if (taskDescription == null || taskDescription2 == null) {
                return false;
            }
            return taskDescription.equals(taskDescription2);
        }

        public int getDeviceDefaultNavigationBarColor(Context context) {
            return context.getResources().getColor(R.color.navbar_light_theme_color, null);
        }

        public void setColorsAreDetermined() {
            this.mColorsAreDetermined = true;
        }

        public boolean getColorsAreDetermined() {
            return this.mColorsAreDetermined;
        }
    }

    public static class RecentTaskInfo extends TaskInfo implements Parcelable {
        public static final Parcelable.Creator<RecentTaskInfo> CREATOR = new Parcelable.Creator<RecentTaskInfo>() { // from class: android.app.ActivityManager.RecentTaskInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RecentTaskInfo createFromParcel(Parcel parcel) {
                return new RecentTaskInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RecentTaskInfo[] newArray(int i) {
                return new RecentTaskInfo[i];
            }
        };

        @Deprecated
        public int affiliatedTaskId;

        @Deprecated
        public CharSequence description;

        @Deprecated
        public int id;

        @Deprecated
        public int persistentId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RecentTaskInfo() {
        }

        private RecentTaskInfo(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readInt();
            this.persistentId = parcel.readInt();
            super.readTaskFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.persistentId);
            super.writeTaskToParcel(parcel, i);
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.println();
            printWriter.print("   ");
            printWriter.print(" id=");
            printWriter.print(this.persistentId);
            printWriter.print(" userId=");
            printWriter.print(this.userId);
            printWriter.print(" hasTask=");
            boolean z = true;
            printWriter.print(this.id != -1);
            printWriter.print(" lastActiveTime=");
            printWriter.println(this.lastActiveTime);
            printWriter.print("   ");
            printWriter.print(" baseIntent=");
            printWriter.println(this.baseIntent);
            if (this.baseActivity != null) {
                printWriter.print("   ");
                printWriter.print(" baseActivity=");
                printWriter.println(this.baseActivity.toShortString());
            }
            if (this.topActivity != null) {
                printWriter.print("   ");
                printWriter.print(" topActivity=");
                printWriter.println(this.topActivity.toShortString());
            }
            if (this.origActivity != null) {
                printWriter.print("   ");
                printWriter.print(" origActivity=");
                printWriter.println(this.origActivity.toShortString());
            }
            if (this.realActivity != null) {
                printWriter.print("   ");
                printWriter.print(" realActivity=");
                printWriter.println(this.realActivity.toShortString());
            }
            printWriter.print("   ");
            printWriter.print(" isExcluded=");
            printWriter.print((this.baseIntent.getFlags() & 8388608) != 0);
            printWriter.print(" activityType=");
            printWriter.print(WindowConfiguration.activityTypeToString(getActivityType()));
            printWriter.print(" windowingMode=");
            printWriter.print(WindowConfiguration.windowingModeToString(getWindowingMode()));
            printWriter.print(" supportsMultiWindow=");
            printWriter.println(this.supportsMultiWindow);
            if (this.taskDescription != null) {
                printWriter.print("   ");
                TaskDescription taskDescription = this.taskDescription;
                printWriter.print(" taskDescription {");
                printWriter.print(" colorBackground=#");
                printWriter.print(Integer.toHexString(taskDescription.getBackgroundColor()));
                printWriter.print(" colorPrimary=#");
                printWriter.print(Integer.toHexString(taskDescription.getPrimaryColor()));
                printWriter.print(" iconRes=");
                printWriter.print(taskDescription.getIconResourcePackage() + "/" + taskDescription.getIconResource());
                printWriter.print(" iconBitmap=");
                if (taskDescription.getIconFilename() == null && taskDescription.getInMemoryIcon() == null) {
                    z = false;
                }
                printWriter.print(z);
                printWriter.print(" resizeMode=");
                printWriter.print(ActivityInfo.resizeModeToString(taskDescription.getResizeMode()));
                printWriter.print(" minWidth=");
                printWriter.print(taskDescription.getMinWidth());
                printWriter.print(" minHeight=");
                printWriter.print(taskDescription.getMinHeight());
                printWriter.print(" colorBackgroundFloating=#");
                printWriter.print(Integer.toHexString(taskDescription.getBackgroundColorFloating()));
                printWriter.println(" }");
            }
            printWriter.print("   ");
        }
    }

    @Deprecated
    public List<RecentTaskInfo> getRecentTasks(int i, int i2) throws SecurityException {
        if (i < 0) {
            throw new IllegalArgumentException("The requested number of tasks should be >= 0");
        }
        return ActivityTaskManager.getInstance().getRecentTasks(i, i2, this.mContext.getUserId());
    }

    public static class RunningTaskInfo extends TaskInfo implements Parcelable {
        public static final Parcelable.Creator<RunningTaskInfo> CREATOR = new Parcelable.Creator<RunningTaskInfo>() { // from class: android.app.ActivityManager.RunningTaskInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RunningTaskInfo createFromParcel(Parcel parcel) {
                return new RunningTaskInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RunningTaskInfo[] newArray(int i) {
                return new RunningTaskInfo[i];
            }
        };

        @Deprecated
        public CharSequence description;

        @Deprecated
        public int id;

        @Deprecated
        public int numRunning;

        @Deprecated
        public Bitmap thumbnail;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RunningTaskInfo() {
        }

        private RunningTaskInfo(Parcel parcel) {
            readFromParcel(parcel);
        }

        public boolean semIsFreeform() {
            return this.configuration.windowConfiguration.getWindowingMode() == 5;
        }

        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readInt();
            super.readTaskFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.id);
            super.writeTaskToParcel(parcel, i);
        }
    }

    public List<AppTask> getAppTasks() {
        ArrayList arrayList = new ArrayList();
        try {
            List<IBinder> appTasks = getTaskService().getAppTasks(this.mContext.getOpPackageName());
            int size = appTasks.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(new AppTask(IAppTask.Stub.asInterface(appTasks.get(i))));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Size getAppTaskThumbnailSize() {
        Size size;
        synchronized (this) {
            ensureAppTaskThumbnailSizeLocked();
            size = new Size(this.mAppTaskThumbnailSize.x, this.mAppTaskThumbnailSize.y);
        }
        return size;
    }

    private void ensureAppTaskThumbnailSizeLocked() {
        if (this.mAppTaskThumbnailSize == null) {
            try {
                this.mAppTaskThumbnailSize = getTaskService().getAppTaskThumbnailSize();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int addAppTask(Activity activity, Intent intent, TaskDescription taskDescription, Bitmap bitmap) {
        Point point;
        float f;
        float f2;
        synchronized (this) {
            ensureAppTaskThumbnailSizeLocked();
            point = this.mAppTaskThumbnailSize;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != point.x || height != point.y) {
            Bitmap createBitmap = Bitmap.createBitmap(point.x, point.y, bitmap.getConfig());
            if (point.x * width > point.y * height) {
                f = point.x / height;
                f2 = (point.y - (width * f)) * 0.5f;
            } else {
                f = point.y / width;
                int i = point.x;
                f2 = 0.0f;
            }
            Matrix matrix = new Matrix();
            matrix.setScale(f, f);
            matrix.postTranslate((int) (f2 + 0.5f), 0.0f);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawBitmap(bitmap, matrix, null);
            canvas.setBitmap(null);
            bitmap = createBitmap;
        }
        if (taskDescription == null) {
            taskDescription = new TaskDescription();
        }
        try {
            return getTaskService().addAppTask(activity.getActivityToken(), intent, taskDescription, bitmap);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<RunningTaskInfo> getRunningTasks(int i) throws SecurityException {
        return ActivityTaskManager.getInstance().getTasks(i);
    }

    public boolean semRemoveTask(int i, int i2) throws SecurityException {
        try {
            return getTaskService().removeTaskWithFlags(i, i2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void moveTaskToFront(int i, int i2) {
        moveTaskToFront(i, i2, null);
    }

    public void moveTaskToFront(int i, int i2, Bundle bundle) {
        try {
            getTaskService().moveTaskToFront(ActivityThread.currentActivityThread().getApplicationThread(), this.mContext.getOpPackageName(), i, i2, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActivityStartAllowedOnDisplay(Context context, int i, Intent intent) {
        try {
            return getTaskService().isActivityStartAllowedOnDisplay(i, intent, intent.resolveTypeIfNeeded(context.getContentResolver()), context.getUserId());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public static class RunningServiceInfo implements Parcelable {
        public static final Parcelable.Creator<RunningServiceInfo> CREATOR = new Parcelable.Creator<RunningServiceInfo>() { // from class: android.app.ActivityManager.RunningServiceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RunningServiceInfo createFromParcel(Parcel parcel) {
                return new RunningServiceInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RunningServiceInfo[] newArray(int i) {
                return new RunningServiceInfo[i];
            }
        };
        public static final int FLAG_FOREGROUND = 2;
        public static final int FLAG_PERSISTENT_PROCESS = 8;
        public static final int FLAG_STARTED = 1;
        public static final int FLAG_SYSTEM_PROCESS = 4;
        public long activeSince;
        public int clientCount;
        public int clientLabel;
        public String clientPackage;
        public int crashCount;
        public int flags;
        public boolean foreground;
        public long lastActivityTime;
        public int pid;
        public String process;
        public long restarting;
        public ComponentName service;
        public boolean started;
        public int uid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RunningServiceInfo() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            ComponentName.writeToParcel(this.service, parcel);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeString(this.process);
            parcel.writeInt(this.foreground ? 1 : 0);
            parcel.writeLong(this.activeSince);
            parcel.writeInt(this.started ? 1 : 0);
            parcel.writeInt(this.clientCount);
            parcel.writeInt(this.crashCount);
            parcel.writeLong(this.lastActivityTime);
            parcel.writeLong(this.restarting);
            parcel.writeInt(this.flags);
            parcel.writeString(this.clientPackage);
            parcel.writeInt(this.clientLabel);
        }

        public void readFromParcel(Parcel parcel) {
            this.service = ComponentName.readFromParcel(parcel);
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.process = parcel.readString();
            this.foreground = parcel.readInt() != 0;
            this.activeSince = parcel.readLong();
            this.started = parcel.readInt() != 0;
            this.clientCount = parcel.readInt();
            this.crashCount = parcel.readInt();
            this.lastActivityTime = parcel.readLong();
            this.restarting = parcel.readLong();
            this.flags = parcel.readInt();
            this.clientPackage = parcel.readString();
            this.clientLabel = parcel.readInt();
        }

        private RunningServiceInfo(Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    @Deprecated
    public List<RunningServiceInfo> getRunningServices(int i) throws SecurityException {
        try {
            return getService().getServices(i, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws SecurityException {
        try {
            return getService().getRunningServiceControlPanel(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class MemoryInfo implements Parcelable {
        public static final Parcelable.Creator<MemoryInfo> CREATOR = new Parcelable.Creator<MemoryInfo>() { // from class: android.app.ActivityManager.MemoryInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MemoryInfo createFromParcel(Parcel parcel) {
                return new MemoryInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MemoryInfo[] newArray(int i) {
                return new MemoryInfo[i];
            }
        };
        public long advertisedMem;
        public long availMem;
        public long foregroundAppThreshold;
        public long hiddenAppThreshold;
        public boolean lowMemory;
        public long secondaryServerThreshold;
        public long threshold;
        public long totalMem;
        public long visibleAppThreshold;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public MemoryInfo() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.advertisedMem);
            parcel.writeLong(this.availMem);
            parcel.writeLong(this.totalMem);
            parcel.writeLong(this.threshold);
            parcel.writeInt(this.lowMemory ? 1 : 0);
            parcel.writeLong(this.hiddenAppThreshold);
            parcel.writeLong(this.secondaryServerThreshold);
            parcel.writeLong(this.visibleAppThreshold);
            parcel.writeLong(this.foregroundAppThreshold);
        }

        public void readFromParcel(Parcel parcel) {
            this.advertisedMem = parcel.readLong();
            this.availMem = parcel.readLong();
            this.totalMem = parcel.readLong();
            this.threshold = parcel.readLong();
            this.lowMemory = parcel.readInt() != 0;
            this.hiddenAppThreshold = parcel.readLong();
            this.secondaryServerThreshold = parcel.readLong();
            this.visibleAppThreshold = parcel.readLong();
            this.foregroundAppThreshold = parcel.readLong();
        }

        public void copyTo(MemoryInfo memoryInfo) {
            memoryInfo.advertisedMem = this.advertisedMem;
            memoryInfo.availMem = this.availMem;
            memoryInfo.totalMem = this.totalMem;
            memoryInfo.threshold = this.threshold;
            memoryInfo.lowMemory = this.lowMemory;
            memoryInfo.hiddenAppThreshold = this.hiddenAppThreshold;
            memoryInfo.secondaryServerThreshold = this.secondaryServerThreshold;
            memoryInfo.visibleAppThreshold = this.visibleAppThreshold;
            memoryInfo.foregroundAppThreshold = this.foregroundAppThreshold;
        }

        private MemoryInfo(Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    public void getMemoryInfo(MemoryInfo memoryInfo) {
        if (Flags.rateLimitGetMemoryInfo()) {
            RateLimitingCache<MemoryInfo> rateLimitingCache = mMemoryInfoCache;
            synchronized (rateLimitingCache) {
                rateLimitingCache.get(new RateLimitingCache.ValueFetcher() { // from class: android.app.ActivityManager$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.RateLimitingCache.ValueFetcher
                    public final Object fetchValue() {
                        ActivityManager.MemoryInfo lambda$getMemoryInfo$0;
                        lambda$getMemoryInfo$0 = ActivityManager.this.lambda$getMemoryInfo$0();
                        return lambda$getMemoryInfo$0;
                    }
                });
                mRateLimitedMemInfo.copyTo(memoryInfo);
            }
            return;
        }
        getMemoryInfoInternal(memoryInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ MemoryInfo lambda$getMemoryInfo$0() {
        MemoryInfo memoryInfo = mRateLimitedMemInfo;
        getMemoryInfoInternal(memoryInfo);
        return memoryInfo;
    }

    private void getMemoryInfoInternal(MemoryInfo memoryInfo) {
        try {
            getService().getMemoryInfo(memoryInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver) {
        try {
            return getService().clearApplicationUserData(str, false, iPackageDataObserver, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData() {
        return clearApplicationUserData(this.mContext.getPackageName(), null);
    }

    @Deprecated
    public ParceledListSlice<GrantedUriPermission> getGrantedUriPermissions(String str) {
        return ((UriGrantsManager) this.mContext.getSystemService(Context.URI_GRANTS_SERVICE)).getGrantedUriPermissions(str);
    }

    @Deprecated
    public void clearGrantedUriPermissions(String str) {
        ((UriGrantsManager) this.mContext.getSystemService(Context.URI_GRANTS_SERVICE)).clearGrantedUriPermissions(str);
    }

    public static class ProcessErrorStateInfo implements Parcelable {
        public static final int CRASHED = 1;
        public static final Parcelable.Creator<ProcessErrorStateInfo> CREATOR = new Parcelable.Creator<ProcessErrorStateInfo>() { // from class: android.app.ActivityManager.ProcessErrorStateInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessErrorStateInfo createFromParcel(Parcel parcel) {
                return new ProcessErrorStateInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessErrorStateInfo[] newArray(int i) {
                return new ProcessErrorStateInfo[i];
            }
        };
        public static final int NOT_RESPONDING = 2;
        public static final int NO_ERROR = 0;
        public int condition;
        public byte[] crashData;
        public String longMsg;
        public int pid;
        public String processName;
        public String shortMsg;
        public String stackTrace;
        public String tag;
        public int uid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ProcessErrorStateInfo() {
            this.crashData = null;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.condition);
            parcel.writeString(this.processName);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeString(this.tag);
            parcel.writeString(this.shortMsg);
            parcel.writeString(this.longMsg);
            parcel.writeString(this.stackTrace);
        }

        public void readFromParcel(Parcel parcel) {
            this.condition = parcel.readInt();
            this.processName = parcel.readString();
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.tag = parcel.readString();
            this.shortMsg = parcel.readString();
            this.longMsg = parcel.readString();
            this.stackTrace = parcel.readString();
        }

        private ProcessErrorStateInfo(Parcel parcel) {
            this.crashData = null;
            readFromParcel(parcel);
        }
    }

    public List<ProcessErrorStateInfo> getProcessesInErrorState() {
        if (Flags.rateLimitGetProcessesInErrorState()) {
            return mErrorProcessesCache.get(new RateLimitingCache.ValueFetcher() { // from class: android.app.ActivityManager$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.RateLimitingCache.ValueFetcher
                public final Object fetchValue() {
                    List lambda$getProcessesInErrorState$1;
                    lambda$getProcessesInErrorState$1 = ActivityManager.this.lambda$getProcessesInErrorState$1();
                    return lambda$getProcessesInErrorState$1;
                }
            });
        }
        return lambda$getProcessesInErrorState$1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getProcessesInErrorStateInternal, reason: merged with bridge method [inline-methods] */
    public List<ProcessErrorStateInfo> lambda$getProcessesInErrorState$1() {
        try {
            return getService().getProcessesInErrorState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class RunningAppProcessInfo implements Parcelable {
        public static final Parcelable.Creator<RunningAppProcessInfo> CREATOR = new Parcelable.Creator<RunningAppProcessInfo>() { // from class: android.app.ActivityManager.RunningAppProcessInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RunningAppProcessInfo createFromParcel(Parcel parcel) {
                return new RunningAppProcessInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RunningAppProcessInfo[] newArray(int i) {
                return new RunningAppProcessInfo[i];
            }
        };
        public static final int FLAG_CANT_SAVE_STATE = 1;
        public static final int FLAG_HAS_ACTIVITIES = 4;
        public static final int FLAG_HAS_SERVICES = 8;
        public static final int FLAG_PERSISTENT = 2;
        public static final int IMPORTANCE_BACKGROUND = 400;
        public static final int IMPORTANCE_CACHED = 400;
        public static final int IMPORTANCE_CANT_SAVE_STATE = 350;
        public static final int IMPORTANCE_CANT_SAVE_STATE_PRE_26 = 170;

        @Deprecated
        public static final int IMPORTANCE_EMPTY = 500;
        public static final int IMPORTANCE_FOREGROUND = 100;
        public static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
        public static final int IMPORTANCE_GONE = 1000;
        public static final int IMPORTANCE_PERCEPTIBLE = 230;
        public static final int IMPORTANCE_PERCEPTIBLE_PRE_26 = 130;
        public static final int IMPORTANCE_SERVICE = 300;
        public static final int IMPORTANCE_TOP_SLEEPING = 325;

        @Deprecated
        public static final int IMPORTANCE_TOP_SLEEPING_PRE_28 = 150;
        public static final int IMPORTANCE_VISIBLE = 200;
        public static final int REASON_PROVIDER_IN_USE = 1;
        public static final int REASON_SERVICE_IN_USE = 2;
        public static final int REASON_UNKNOWN = 0;
        public long avgPss;
        public int flags;
        public int importance;
        public int importanceReasonCode;
        public ComponentName importanceReasonComponent;
        public int importanceReasonImportance;
        public int importanceReasonPid;
        public boolean isFocused;
        public boolean isProtectedInPicked;
        public long lastActivityTime;
        public long lastPss;
        public long lastSwapPss;
        public int lastTrimLevel;
        public int lru;
        public long maxPss;
        public long minPss;
        public int pid;
        public String[] pkgDeps;
        public String[] pkgList;
        public String processName;
        public int processState;
        public int uid;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Importance {
        }

        public static int importanceToProcState(int i) {
            if (i == 1000) {
                return 20;
            }
            if (i >= 400) {
                return 14;
            }
            if (i >= 350) {
                return 13;
            }
            if (i >= 325) {
                return 12;
            }
            if (i >= 300) {
                return 10;
            }
            if (i >= 230) {
                return 8;
            }
            if (i < 200 && i < 150) {
                return i >= 125 ? 4 : 2;
            }
            return 6;
        }

        public static int procStateToImportance(int i) {
            if (i == 20) {
                return 1000;
            }
            if (i >= 14) {
                return 400;
            }
            if (i == 13) {
                return 350;
            }
            if (i >= 12) {
                return 325;
            }
            if (i >= 10) {
                return 300;
            }
            if (i >= 8) {
                return 230;
            }
            if (i >= 6) {
                return 200;
            }
            return i >= 4 ? 125 : 100;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static int procStateToImportanceForClient(int i, Context context) {
            return procStateToImportanceForTargetSdk(i, context.getApplicationInfo().targetSdkVersion);
        }

        public static int procStateToImportanceForTargetSdk(int i, int i2) {
            int procStateToImportance = procStateToImportance(i);
            if (i2 >= 26) {
                return procStateToImportance;
            }
            if (procStateToImportance == 230) {
                return 130;
            }
            if (procStateToImportance == 325) {
                return 150;
            }
            if (procStateToImportance != 350) {
                return procStateToImportance;
            }
            return 170;
        }

        public int semGetProcessState() {
            return this.processState;
        }

        public RunningAppProcessInfo() {
            this.importance = 100;
            this.importanceReasonCode = 0;
            this.processState = 6;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        public RunningAppProcessInfo(String str, int i, String[] strArr) {
            this.processName = str;
            this.pid = i;
            this.pkgList = strArr;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.processName);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeStringArray(this.pkgList);
            parcel.writeStringArray(this.pkgDeps);
            parcel.writeInt(this.flags);
            parcel.writeInt(this.lastTrimLevel);
            parcel.writeInt(this.importance);
            parcel.writeInt(this.lru);
            parcel.writeInt(this.importanceReasonCode);
            parcel.writeInt(this.importanceReasonPid);
            ComponentName.writeToParcel(this.importanceReasonComponent, parcel);
            parcel.writeInt(this.importanceReasonImportance);
            parcel.writeInt(this.processState);
            parcel.writeInt(this.isFocused ? 1 : 0);
            parcel.writeLong(this.lastActivityTime);
        }

        public void readFromParcel(Parcel parcel) {
            this.processName = parcel.readString();
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.pkgList = parcel.readStringArray();
            this.pkgDeps = parcel.readStringArray();
            this.flags = parcel.readInt();
            this.lastTrimLevel = parcel.readInt();
            this.importance = parcel.readInt();
            this.lru = parcel.readInt();
            this.importanceReasonCode = parcel.readInt();
            this.importanceReasonPid = parcel.readInt();
            this.importanceReasonComponent = ComponentName.readFromParcel(parcel);
            this.importanceReasonImportance = parcel.readInt();
            this.processState = parcel.readInt();
            this.isFocused = parcel.readInt() != 0;
            this.lastActivityTime = parcel.readLong();
        }

        public void copyTo(RunningAppProcessInfo runningAppProcessInfo) {
            runningAppProcessInfo.pid = this.pid;
            runningAppProcessInfo.uid = this.uid;
            runningAppProcessInfo.flags = this.flags;
            runningAppProcessInfo.lastTrimLevel = this.lastTrimLevel;
            runningAppProcessInfo.importance = this.importance;
            runningAppProcessInfo.lru = this.lru;
            runningAppProcessInfo.importanceReasonCode = this.importanceReasonCode;
            runningAppProcessInfo.processState = this.processState;
            runningAppProcessInfo.isFocused = this.isFocused;
            runningAppProcessInfo.lastActivityTime = this.lastActivityTime;
        }

        private RunningAppProcessInfo(Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    public List<ApplicationInfo> getRunningExternalApplications() {
        try {
            return getService().getRunningExternalApplications();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBackgroundRestricted() {
        try {
            return getService().isBackgroundRestricted(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setProcessMemoryTrimLevel(String str, int i, int i2) {
        try {
            return getService().setProcessMemoryTrimLevel(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setFGSFilter(int i, boolean z) {
        try {
            return getService().setFGSFilter(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetAbnormalList() {
        try {
            getService().resetAbnormalList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFreezableUid(int i) {
        try {
            return getService().isFreezableUid(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setProcessSlowdown(int i, boolean z) {
        try {
            return getService().setProcessSlowdown(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getIsolatedPids() {
        try {
            return getService().getIsolatedProcessList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<RunningAppProcessInfo> getRunningAppProcesses() {
        if (!Flags.rateLimitGetRunningAppProcesses()) {
            return lambda$getRunningAppProcesses$2();
        }
        return mRunningProcessesCache.get(new RateLimitingCache.ValueFetcher() { // from class: android.app.ActivityManager$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.RateLimitingCache.ValueFetcher
            public final Object fetchValue() {
                List lambda$getRunningAppProcesses$2;
                lambda$getRunningAppProcesses$2 = ActivityManager.this.lambda$getRunningAppProcesses$2();
                return lambda$getRunningAppProcesses$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getRunningAppProcessesInternal, reason: merged with bridge method [inline-methods] */
    public List<RunningAppProcessInfo> lambda$getRunningAppProcesses$2() {
        try {
            return getService().getRunningAppProcesses();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ApplicationStartInfo> getHistoricalProcessStartReasons(int i) {
        try {
            ParceledListSlice<ApplicationStartInfo> historicalProcessStartReasons = getService().getHistoricalProcessStartReasons(null, i, this.mContext.getUserId());
            return historicalProcessStartReasons == null ? Collections.EMPTY_LIST : historicalProcessStartReasons.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<ApplicationStartInfo> getExternalHistoricalProcessStartReasons(String str, int i) {
        try {
            ParceledListSlice<ApplicationStartInfo> historicalProcessStartReasons = getService().getHistoricalProcessStartReasons(str, i, this.mContext.getUserId());
            return historicalProcessStartReasons == null ? Collections.EMPTY_LIST : historicalProcessStartReasons.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AppStartInfoCallbackWrapper {
        final Executor mExecutor;
        final Consumer<ApplicationStartInfo> mListener;

        AppStartInfoCallbackWrapper(Executor executor, Consumer<ApplicationStartInfo> consumer) {
            this.mExecutor = executor;
            this.mListener = consumer;
        }
    }

    public void addApplicationStartInfoCompletionListener(Executor executor, Consumer<ApplicationStartInfo> consumer) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(consumer, "listener cannot be null");
        synchronized (this.mAppStartInfoCallbacks) {
            for (int i = 0; i < this.mAppStartInfoCallbacks.size(); i++) {
                if (consumer.equals(this.mAppStartInfoCallbacks.get(i).mListener)) {
                    return;
                }
            }
            if (this.mAppStartInfoCompleteListener == null) {
                this.mAppStartInfoCompleteListener = new AnonymousClass3();
                try {
                    getService().addApplicationStartInfoCompleteListener(this.mAppStartInfoCompleteListener, this.mContext.getUserId());
                    this.mAppStartInfoCallbacks.add(new AppStartInfoCallbackWrapper(executor, consumer));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                this.mAppStartInfoCallbacks.add(new AppStartInfoCallbackWrapper(executor, consumer));
            }
        }
    }

    /* renamed from: android.app.ActivityManager$3, reason: invalid class name */
    class AnonymousClass3 extends IApplicationStartInfoCompleteListener.Stub {
        AnonymousClass3() {
        }

        @Override // android.app.IApplicationStartInfoCompleteListener
        public void onApplicationStartInfoComplete(final ApplicationStartInfo applicationStartInfo) {
            synchronized (ActivityManager.this.mAppStartInfoCallbacks) {
                for (int i = 0; i < ActivityManager.this.mAppStartInfoCallbacks.size(); i++) {
                    final AppStartInfoCallbackWrapper appStartInfoCallbackWrapper = (AppStartInfoCallbackWrapper) ActivityManager.this.mAppStartInfoCallbacks.get(i);
                    appStartInfoCallbackWrapper.mExecutor.execute(new Runnable() { // from class: android.app.ActivityManager$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityManager.AppStartInfoCallbackWrapper.this.mListener.accept(applicationStartInfo);
                        }
                    });
                }
                ActivityManager.this.mAppStartInfoCallbacks.clear();
                ActivityManager.this.mAppStartInfoCompleteListener = null;
            }
        }
    }

    public void removeApplicationStartInfoCompletionListener(Consumer<ApplicationStartInfo> consumer) {
        Preconditions.checkNotNull(consumer, "listener cannot be null");
        synchronized (this.mAppStartInfoCallbacks) {
            int i = 0;
            while (true) {
                if (i >= this.mAppStartInfoCallbacks.size()) {
                    break;
                }
                if (consumer.equals(this.mAppStartInfoCallbacks.get(i).mListener)) {
                    this.mAppStartInfoCallbacks.remove(i);
                    break;
                }
                i++;
            }
            if (this.mAppStartInfoCompleteListener != null && this.mAppStartInfoCallbacks.isEmpty()) {
                try {
                    getService().removeApplicationStartInfoCompleteListener(this.mAppStartInfoCompleteListener, this.mContext.getUserId());
                    this.mAppStartInfoCompleteListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void addStartInfoTimestamp(int i, long j) {
        if (i <= 20 || i > 30) {
            throw new IllegalArgumentException("Key not in allowed range.");
        }
        try {
            getService().addStartInfoTimestamp(i, j, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ApplicationExitInfo> getHistoricalProcessExitReasons(String str, int i, int i2) {
        try {
            ParceledListSlice<ApplicationExitInfo> historicalProcessExitReasons = getService().getHistoricalProcessExitReasons(str, i, i2, this.mContext.getUserId());
            return historicalProcessExitReasons == null ? Collections.EMPTY_LIST : historicalProcessExitReasons.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setProcessStateSummary(byte[] bArr) {
        try {
            getService().setProcessStateSummary(bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isLowMemoryKillReportSupported() {
        return SystemProperties.getBoolean("persist.sys.lmk.reportkills", false);
    }

    public int getUidProcessState(int i) {
        try {
            return getService().getUidProcessState(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUidProcessCapabilities(int i) {
        try {
            return getService().getUidProcessCapabilities(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getPackageImportance(String str) {
        try {
            return RunningAppProcessInfo.procStateToImportanceForClient(getService().getPackageProcessState(str, this.mContext.getOpPackageName()), this.mContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getUidImportance(int i) {
        try {
            return RunningAppProcessInfo.procStateToImportanceForClient(getService().getUidProcessState(i, this.mContext.getOpPackageName()), this.mContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getBindingUidImportance(int i) {
        try {
            return RunningAppProcessInfo.procStateToImportanceForClient(getService().getBindingUidProcessState(i, this.mContext.getOpPackageName()), this.mContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnUidImportanceListener(OnUidImportanceListener onUidImportanceListener, int i) {
        addOnUidImportanceListenerInternal(onUidImportanceListener, i, null);
    }

    @SystemApi
    public void addOnUidImportanceListener(OnUidImportanceListener onUidImportanceListener, int i, int[] iArr) {
        Objects.requireNonNull(onUidImportanceListener);
        Objects.requireNonNull(iArr);
        addOnUidImportanceListenerInternal(onUidImportanceListener, i, iArr);
    }

    private void addOnUidImportanceListenerInternal(OnUidImportanceListener onUidImportanceListener, int i, int[] iArr) {
        synchronized (this.mImportanceListeners) {
            if (this.mImportanceListeners.containsKey(onUidImportanceListener)) {
                throw new IllegalArgumentException("Listener already registered: " + onUidImportanceListener);
            }
            MyUidObserver myUidObserver = new MyUidObserver(onUidImportanceListener, this.mContext);
            try {
                getService().registerUidObserverForUids(myUidObserver, 3, RunningAppProcessInfo.importanceToProcState(i), this.mContext.getOpPackageName(), iArr);
                this.mImportanceListeners.put(onUidImportanceListener, myUidObserver);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void removeOnUidImportanceListener(OnUidImportanceListener onUidImportanceListener) {
        synchronized (this.mImportanceListeners) {
            MyUidObserver remove = this.mImportanceListeners.remove(onUidImportanceListener);
            if (remove == null) {
                throw new IllegalArgumentException("Listener not registered: " + onUidImportanceListener);
            }
            try {
                getService().unregisterUidObserver(remove);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static void getMyMemoryState(RunningAppProcessInfo runningAppProcessInfo) {
        if (Flags.rateLimitGetMyMemoryState()) {
            RateLimitingCache<RunningAppProcessInfo> rateLimitingCache = mMyMemoryStateCache;
            synchronized (rateLimitingCache) {
                rateLimitingCache.get(new RateLimitingCache.ValueFetcher() { // from class: android.app.ActivityManager$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.RateLimitingCache.ValueFetcher
                    public final Object fetchValue() {
                        return ActivityManager.lambda$getMyMemoryState$3();
                    }
                });
                mRateLimitedMemState.copyTo(runningAppProcessInfo);
            }
            return;
        }
        getMyMemoryStateInternal(runningAppProcessInfo);
    }

    static /* synthetic */ RunningAppProcessInfo lambda$getMyMemoryState$3() {
        RunningAppProcessInfo runningAppProcessInfo = mRateLimitedMemState;
        getMyMemoryStateInternal(runningAppProcessInfo);
        return runningAppProcessInfo;
    }

    private static void getMyMemoryStateInternal(RunningAppProcessInfo runningAppProcessInfo) {
        try {
            getService().getMyMemoryState(runningAppProcessInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) {
        try {
            return getService().getProcessMemoryInfo(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void restartPackage(String str) {
        killBackgroundProcesses(str);
    }

    public void killBackgroundProcesses(String str) {
        try {
            getService().killBackgroundProcesses(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void killUid(int i, String str) {
        try {
            getService().killUid(UserHandle.getAppId(i), UserHandle.getUserId(i), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semKillUid(int i, String str) {
        killUid(i, str);
    }

    public void forceStopPackageAsUser(String str, int i) {
        try {
            getService().forceStopPackage(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void forceStopPackage(String str) {
        forceStopPackageAsUser(str, this.mContext.getUserId());
    }

    public void semForceStopPackage(String str) {
        forceStopPackage(str);
    }

    public void forceStopPackageAsUserEvenWhenStopping(String str, int i) {
        try {
            getService().forceStopPackageEvenWhenStopping(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopPackageForUser(String str) {
        try {
            getService().stopAppForUser(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDeviceLocales(LocaleList localeList) {
        LocalePicker.updateLocales(localeList);
    }

    @SystemApi
    public Collection<Locale> getSupportedLocales() {
        ArrayList arrayList = new ArrayList();
        for (String str : LocalePicker.getSupportedLocales(this.mContext)) {
            arrayList.add(Locale.forLanguageTag(str));
        }
        return arrayList;
    }

    public ConfigurationInfo getDeviceConfigurationInfo() {
        try {
            return getTaskService().getDeviceConfigurationInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLauncherLargeIconDensity() {
        Resources resources = this.mContext.getResources();
        int i = resources.getDisplayMetrics().densityDpi;
        if (resources.getConfiguration().smallestScreenWidthDp < 600) {
            return i;
        }
        if (i == 120) {
            return 160;
        }
        if (i == 160) {
            return 240;
        }
        if (i == 213 || i == 240) {
            return 320;
        }
        if (i == 320) {
            return 480;
        }
        if (i != 480) {
            return (int) ((i * 1.5f) + 0.5f);
        }
        return 640;
    }

    public int getLauncherLargeIconSize() {
        return getLauncherLargeIconSizeInner(this.mContext);
    }

    static int getLauncherLargeIconSizeInner(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(17104896);
        if (resources.getConfiguration().smallestScreenWidthDp < 600) {
            return dimensionPixelSize;
        }
        int i = resources.getDisplayMetrics().densityDpi;
        if (i == 120) {
            return (dimensionPixelSize * 160) / 120;
        }
        if (i == 160) {
            return (dimensionPixelSize * 240) / 160;
        }
        if (i == 213) {
            return (dimensionPixelSize * 320) / 240;
        }
        if (i == 240) {
            return (dimensionPixelSize * 320) / 240;
        }
        if (i != 320) {
            return i != 480 ? (int) ((dimensionPixelSize * 1.5f) + 0.5f) : (dimensionPixelSize * 640) / 480;
        }
        return (dimensionPixelSize * 480) / 320;
    }

    public static boolean isUserAMonkey() {
        try {
            return getService().isUserAMonkey();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public static boolean isRunningInTestHarness() {
        return SystemProperties.getBoolean("ro.test_harness", false);
    }

    public static boolean isRunningInUserTestHarness() {
        return SystemProperties.getBoolean("persist.sys.test_harness", false);
    }

    public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) {
        try {
            getTaskService().alwaysShowUnsupportedCompileSdkWarning(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean canAccessUnexportedComponents(int i) {
        int appId = UserHandle.getAppId(i);
        return appId == 0 || appId == 1000;
    }

    public static int checkComponentPermission(String str, int i, int i2, boolean z) {
        return checkComponentPermission(str, i, 0, i2, z);
    }

    public static int checkComponentPermission(String str, int i, int i2, int i3, boolean z) {
        if (canAccessUnexportedComponents(i)) {
            return 0;
        }
        if (UserHandle.isIsolated(i)) {
            return -1;
        }
        if (i3 >= 0 && UserHandle.isSameApp(i, i3)) {
            return 0;
        }
        if (!z) {
            return -1;
        }
        if (str == null) {
            return 0;
        }
        if (((UserHandle.getUserId(i3) == 0 && SemDualAppManager.isDualAppId(UserHandle.getUserId(i))) || (UserHandle.getUserId(i) == 0 && SemDualAppManager.isDualAppId(UserHandle.getUserId(i3)))) && (Manifest.permission.INTERACT_ACROSS_USERS.equals(str) || Manifest.permission.INTERACT_ACROSS_USERS_FULL.equals(str))) {
            return 0;
        }
        try {
            return AppGlobals.getPermissionManager().checkUidPermission(i, str, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int checkUidPermission(String str, int i) {
        try {
            return AppGlobals.getPermissionManager().checkUidPermission(i, str, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) {
        if (UserHandle.getUserId(i2) == i3) {
            return i3;
        }
        try {
            return getService().handleIncomingUser(i, i2, i3, z, z2, str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static int getCurrentUser() {
        return mGetCurrentUserIdCache.query(null).intValue();
    }

    public static int semGetCurrentUser() {
        return getCurrentUser();
    }

    public boolean switchUser(int i) {
        try {
            return getService().switchUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean switchUser(UserHandle userHandle) {
        Preconditions.checkArgument(userHandle != null, "UserHandle cannot be null.");
        return switchUser(userHandle.getIdentifier());
    }

    public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2) {
        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
            throw new UnsupportedOperationException("device does not support users on secondary displays");
        }
        try {
            return getService().startUserInBackgroundVisibleOnDisplay(i, i2, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getDisplayIdsForStartingVisibleBackgroundUsers() {
        try {
            return getService().getDisplayIdsForStartingVisibleBackgroundUsers();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getSwitchingFromUserMessage(int i) {
        try {
            return getService().getSwitchingFromUserMessage(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getSwitchingToUserMessage(int i) {
        try {
            return getService().getSwitchingToUserMessage(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStopUserOnSwitch(int i) {
        try {
            getService().setStopUserOnSwitch(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean startProfile(UserHandle userHandle) {
        try {
            return getService().startProfile(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean stopProfile(UserHandle userHandle) {
        try {
            return getService().stopProfile(userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean updateMccMncConfiguration(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("mcc or mnc cannot be null.");
        }
        try {
            return getService().updateMccMncConfiguration(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean stopUser(int i) {
        if (i == 0) {
            return false;
        }
        try {
            return getService().stopUserWithCallback(i, null) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunning(int i) {
        try {
            return getService().isUserRunning(i, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVrModePackageEnabled(ComponentName componentName) {
        try {
            return getService().isVrModePackageEnabled(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void dumpPackageState(FileDescriptor fileDescriptor, String str) {
        dumpPackageStateStatic(fileDescriptor, str);
    }

    public static void dumpPackageStateStatic(FileDescriptor fileDescriptor, String str) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        dumpService(fastPrintWriter, fileDescriptor, "package", new String[]{str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, "activity", new String[]{"-a", "package", str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, "meminfo", new String[]{"--local", "--package", str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, ProcessStats.SERVICE_NAME, new String[]{str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, Context.USAGE_STATS_SERVICE, new String[]{str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, "batterystats", new String[]{str});
        fastPrintWriter.flush();
    }

    public static boolean isSystemReady() {
        if (!sSystemReady) {
            if (ActivityThread.isSystem()) {
                sSystemReady = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).isSystemReady();
            } else {
                sSystemReady = true;
            }
        }
        return sSystemReady;
    }

    public static void broadcastStickyIntent(Intent intent, int i) {
        broadcastStickyIntent(intent, -1, null, i);
    }

    public static void broadcastStickyIntent(Intent intent, int i, int i2) {
        broadcastStickyIntent(intent, i, null, i2);
    }

    public static void broadcastStickyIntent(Intent intent, int i, Bundle bundle, int i2) {
        broadcastStickyIntent(intent, null, i, bundle, i2);
    }

    public static void broadcastStickyIntent(Intent intent, String[] strArr, int i, Bundle bundle, int i2) {
        try {
            getService().broadcastIntentWithFeature(null, null, intent, null, null, -1, null, null, null, null, strArr, i, bundle, false, true, i2);
        } catch (RemoteException unused) {
        }
    }

    public static void resumeAppSwitches() throws RemoteException {
        getService().resumeAppSwitches();
    }

    public static void noteWakeupAlarm(PendingIntent pendingIntent, WorkSource workSource, int i, String str, String str2) {
        try {
            getService().noteWakeupAlarm(pendingIntent != null ? pendingIntent.getTarget() : null, workSource, i, str, str2);
        } catch (RemoteException unused) {
        }
    }

    public static void noteAlarmStart(PendingIntent pendingIntent, WorkSource workSource, int i, String str) {
        try {
            getService().noteAlarmStart(pendingIntent != null ? pendingIntent.getTarget() : null, workSource, i, str);
        } catch (RemoteException unused) {
        }
    }

    public static void noteAlarmFinish(PendingIntent pendingIntent, WorkSource workSource, int i, String str) {
        try {
            getService().noteAlarmFinish(pendingIntent != null ? pendingIntent.getTarget() : null, workSource, i, str);
        } catch (RemoteException unused) {
        }
    }

    public static IActivityManager getService() {
        return IActivityManagerSingleton.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IActivityTaskManager getTaskService() {
        return ActivityTaskManager.getService();
    }

    private static void dumpService(PrintWriter printWriter, FileDescriptor fileDescriptor, String str, String[] strArr) {
        TransferPipe transferPipe;
        printWriter.print("DUMP OF SERVICE ");
        printWriter.print(str);
        printWriter.println(":");
        IBinder checkService = ServiceManager.checkService(str);
        if (checkService == null) {
            printWriter.println("  (Service not found)");
            printWriter.flush();
            return;
        }
        printWriter.flush();
        if (checkService instanceof Binder) {
            try {
                checkService.dump(fileDescriptor, strArr);
                return;
            } catch (Throwable th) {
                printWriter.println("Failure dumping service:");
                th.printStackTrace(printWriter);
                printWriter.flush();
                return;
            }
        }
        TransferPipe transferPipe2 = null;
        try {
            printWriter.flush();
            transferPipe = new TransferPipe();
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            transferPipe.setBufferPrefix("  ");
            checkService.dumpAsync(transferPipe.getWriteFd().getFileDescriptor(), strArr);
            transferPipe.go(fileDescriptor, JobInfo.MIN_BACKOFF_MILLIS);
        } catch (Throwable th3) {
            th = th3;
            transferPipe2 = transferPipe;
            if (transferPipe2 != null) {
                transferPipe2.kill();
            }
            printWriter.println("Failure dumping service:");
            th.printStackTrace(printWriter);
        }
    }

    public void setWatchHeapLimit(long j) {
        try {
            getService().setDumpHeapDebugLimit(null, 0, j, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWatchHeapLimit() {
        try {
            getService().setDumpHeapDebugLimit(null, 0, 0L, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isInLockTaskMode() {
        return getLockTaskModeState() != 0;
    }

    public int getLockTaskModeState() {
        try {
            return getTaskService().getLockTaskModeState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setVrThread(int i) {
        try {
            getTaskService().setVrThread(i);
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    public static void setPersistentVrThread(int i) {
        try {
            getService().setPersistentVrThread(i);
        } catch (RemoteException unused) {
        }
    }

    public void scheduleApplicationInfoChanged(List<String> list, int i) {
        try {
            getService().scheduleApplicationInfoChanged(list, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProfileForeground(UserHandle userHandle) {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return false;
        }
        Iterator<UserInfo> it = userManager.getProfiles(getCurrentUser()).iterator();
        while (it.hasNext()) {
            if (it.next().id == userHandle.getIdentifier()) {
                return true;
            }
        }
        return false;
    }

    @SystemApi
    public void killProcessesWhenImperceptible(int[] iArr, String str) {
        try {
            getService().killProcessesWhenImperceptible(iArr, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String procStateToString(int i) {
        switch (i) {
            case 0:
                return "PER ";
            case 1:
                return "PERU";
            case 2:
                return "TOP ";
            case 3:
                return "BTOP";
            case 4:
                return "FGS ";
            case 5:
                return "BFGS";
            case 6:
                return "IMPF";
            case 7:
                return "IMPB";
            case 8:
                return "TRNB";
            case 9:
                return "BKUP";
            case 10:
                return "SVC ";
            case 11:
                return "RCVR";
            case 12:
                return "TPSL";
            case 13:
                return "HVY ";
            case 14:
                return "HOME";
            case 15:
                return "LAST";
            case 16:
                return "CAC ";
            case 17:
                return "CACC";
            case 18:
                return "CRE ";
            case 19:
                return "CEM ";
            case 20:
                return KeyProperties.DIGEST_NONE;
            default:
                return "??";
        }
    }

    public static class AppTask {
        private IAppTask mAppTaskImpl;

        public AppTask(IAppTask iAppTask) {
            this.mAppTaskImpl = iAppTask;
        }

        public void finishAndRemoveTask() {
            if (Instrumentation.DEBUG_FINISH_ACTIVITY) {
                Log.d(Instrumentation.TAG, "AppTask#finishAndRemoveTask: task=" + getTaskInfo(), new Throwable());
            }
            try {
                this.mAppTaskImpl.finishAndRemoveTask();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public RecentTaskInfo getTaskInfo() {
            try {
                return this.mAppTaskImpl.getTaskInfo();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void moveToFront() {
            try {
                this.mAppTaskImpl.moveToFront(ActivityThread.currentActivityThread().getApplicationThread(), ActivityThread.currentPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void startActivity(Context context, Intent intent, Bundle bundle) {
            ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
            currentActivityThread.getInstrumentation().execStartActivityFromAppTask(context, currentActivityThread.getApplicationThread(), this.mAppTaskImpl, intent, bundle);
        }

        public void setExcludeFromRecents(boolean z) {
            try {
                this.mAppTaskImpl.setExcludeFromRecents(z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semUpdateConfiguration(Configuration configuration) {
        try {
            getTaskService().updateConfiguration(configuration);
        } catch (RemoteException unused) {
        }
    }

    public void semRegisterActivityControllerListener(SemActivityControllerListener semActivityControllerListener) {
        synchronized (this.mActivityControllerListeners) {
            if (this.mActivityControllerListeners.contains(semActivityControllerListener)) {
                Log.w(TAG, "ActivityControllerListener already registered");
                return;
            }
            if (this.mActivityController == null) {
                this.mActivityController = new ActivityController();
            }
            try {
                Log.i(TAG, "semRegisterActivityControllerListener, listener=0x" + Integer.toHexString(semActivityControllerListener.hashCode()));
                this.mActivityControllerListeners.add(semActivityControllerListener);
                getTaskService().setActivityController(this.mActivityController, false);
            } catch (RemoteException unused) {
            }
        }
    }

    public void semUnregisterActivityControllerListener(SemActivityControllerListener semActivityControllerListener) {
        synchronized (this.mActivityControllerListeners) {
            if (!this.mActivityControllerListeners.contains(semActivityControllerListener)) {
                Log.w(TAG, "ActivityControllerListener no registered");
                return;
            }
            try {
                Log.i(TAG, "semUnregisterActivityControllerListener, listener=0x" + Integer.toHexString(semActivityControllerListener.hashCode()));
                this.mActivityControllerListeners.remove(semActivityControllerListener);
                if (this.mActivityControllerListeners.isEmpty()) {
                    getTaskService().setActivityController(null, false);
                }
            } catch (RemoteException unused) {
            }
        }
    }

    private class ActivityController extends IActivityController.Stub {
        private ActivityController() {
        }

        @Override // android.app.IActivityController
        public boolean activityStarting(Intent intent, String str) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener semActivityControllerListener = (SemActivityControllerListener) it.next();
                if (!semActivityControllerListener.onActivityStarting(intent, str)) {
                    Log.i(ActivityManager.TAG, "Tried to abort onActivityStarting, pkg=" + str + ", listener=0x" + Integer.toHexString(semActivityControllerListener.hashCode()));
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.IActivityController
        public boolean activityResuming(String str) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener semActivityControllerListener = (SemActivityControllerListener) it.next();
                if (!semActivityControllerListener.onActivityResuming(str)) {
                    Log.i(ActivityManager.TAG, "Tried to abort onActivityResuming, pkg=" + str + ", listener=0x" + Integer.toHexString(semActivityControllerListener.hashCode()));
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.IActivityController
        public boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                if (!((SemActivityControllerListener) it.next()).onAppCrashed(str, i, str2, str3, j, str4)) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.IActivityController
        public int appEarlyNotResponding(String str, int i, String str2) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                int onAppEarlyNotResponding = ((SemActivityControllerListener) it.next()).onAppEarlyNotResponding(str, i, str2);
                if (onAppEarlyNotResponding != 0 && (onAppEarlyNotResponding == 1 || i2 != 1)) {
                    i2 = onAppEarlyNotResponding;
                }
            }
            return i2;
        }

        @Override // android.app.IActivityController
        public int appNotResponding(String str, int i, String str2) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                int onAppNotResponding = ((SemActivityControllerListener) it.next()).onAppNotResponding(str, i, str2);
                if (onAppNotResponding != 0 && (onAppNotResponding == 1 || i2 != 1)) {
                    i2 = onAppNotResponding;
                }
            }
            return i2;
        }

        @Override // android.app.IActivityController
        public int systemNotResponding(String str) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            int i = 0;
            while (it.hasNext()) {
                int onSystemNotResponding = ((SemActivityControllerListener) it.next()).onSystemNotResponding(str);
                if (onSystemNotResponding != 0 && (onSystemNotResponding == 1 || i != 1)) {
                    i = onSystemNotResponding;
                }
            }
            return i;
        }
    }

    public void semSetProcessImportant(IBinder iBinder, int i, boolean z) {
        try {
            getService().setProcessImportant(iBinder, i, z, "setProcessImportant()");
        } catch (RemoteException unused) {
        }
    }

    public boolean semSwitchUser(int i) {
        try {
            return getService().switchUser(i);
        } catch (RemoteException e) {
            Log.e(TAG, "semSwitchUser failed", e);
            return false;
        }
    }

    public void semCloseSystemDialogs(String str) {
        try {
            getService().closeSystemDialogs(str);
        } catch (RemoteException unused) {
        }
    }

    public void semRegisterProcessListener(SemProcessListener semProcessListener) {
        if (this.mProcessObserverMap.get(semProcessListener) != null) {
            Log.w(TAG, "ProcessListener already registered");
            return;
        }
        ProcessObserver processObserver = new ProcessObserver();
        try {
            getService().registerProcessObserver(processObserver);
            this.mProcessObserverMap.put(semProcessListener, processObserver);
            this.mProcessListeners.add(semProcessListener);
        } catch (RemoteException unused) {
        }
    }

    public void semUnregisterProcessListener(SemProcessListener semProcessListener) {
        ProcessObserver processObserver = this.mProcessObserverMap.get(semProcessListener);
        if (processObserver == null) {
            Log.w(TAG, "ProcessListener no registered");
            return;
        }
        try {
            getService().unregisterProcessObserver(processObserver);
            this.mProcessObserverMap.remove(semProcessListener);
            this.mProcessListeners.remove(semProcessListener);
        } catch (RemoteException unused) {
        }
    }

    public List<String> getBugreportWhitelistedPackages() {
        try {
            return getService().getBugreportWhitelistedPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void appNotResponding(String str) {
        try {
            getService().appNotResponding(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void addHomeVisibilityListener(Executor executor, final HomeVisibilityListener homeVisibilityListener) {
        Preconditions.checkNotNull(homeVisibilityListener);
        Preconditions.checkNotNull(executor);
        try {
            homeVisibilityListener.init(this.mContext, executor);
            getService().registerProcessObserver(homeVisibilityListener.mObserver);
            executor.execute(new Runnable() { // from class: android.app.ActivityManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    r0.onHomeVisibilityChanged(HomeVisibilityListener.this.mIsHomeActivityVisible);
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void removeHomeVisibilityListener(HomeVisibilityListener homeVisibilityListener) {
        Preconditions.checkNotNull(homeVisibilityListener);
        try {
            getService().unregisterProcessObserver(homeVisibilityListener.mObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setThemeOverlayReady(int i) {
        try {
            getService().setThemeOverlayReady(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetAppErrors() {
        try {
            getService().resetAppErrors();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void holdLock(IBinder iBinder, int i) {
        try {
            getService().holdLock(iBinder, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForBroadcastIdle() {
        try {
            getService().waitForBroadcastIdle();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void forceDelayBroadcastDelivery(String str, long j) {
        try {
            getService().forceDelayBroadcastDelivery(str, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProcessFrozen(int i) {
        try {
            return getService().isProcessFrozen(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void noteForegroundResourceUseBegin(int i, int i2, int i3) throws SecurityException {
        try {
            getService().logFgsApiBegin(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void noteForegroundResourceUseEnd(int i, int i2, int i3) throws SecurityException {
        try {
            getService().logFgsApiEnd(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBackgroundRestrictionExemptionReason(int i) {
        try {
            return getService().getBackgroundRestrictionExemptionReason(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void noteAppRestrictionEnabled(String str, int i, int i2, boolean z, int i3, String str2, int i4, long j) {
        try {
            getService().noteAppRestrictionEnabled(str, i, i2, z, i3, str2, i4, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySystemPropertiesChanged() {
        IBinder asBinder = getService().asBinder();
        if (asBinder != null) {
            Parcel obtain = Parcel.obtain();
            try {
                asBinder.transact(IBinder.SYSPROPS_TRANSACTION, obtain, null, 0);
                obtain.recycle();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static final class PendingIntentInfo implements Parcelable {
        public static final Parcelable.Creator<PendingIntentInfo> CREATOR = new Parcelable.Creator<PendingIntentInfo>() { // from class: android.app.ActivityManager.PendingIntentInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PendingIntentInfo createFromParcel(Parcel parcel) {
                return new PendingIntentInfo(parcel.readString(), parcel.readInt(), parcel.readBoolean(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PendingIntentInfo[] newArray(int i) {
                return new PendingIntentInfo[i];
            }
        };
        private final String mCreatorPackage;
        private final int mCreatorUid;
        private final boolean mImmutable;
        private final int mIntentSenderType;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PendingIntentInfo(String str, int i, boolean z, int i2) {
            this.mCreatorPackage = str;
            this.mCreatorUid = i;
            this.mImmutable = z;
            this.mIntentSenderType = i2;
        }

        public String getCreatorPackage() {
            return this.mCreatorPackage;
        }

        public int getCreatorUid() {
            return this.mCreatorUid;
        }

        public boolean isImmutable() {
            return this.mImmutable;
        }

        public int getIntentSenderType() {
            return this.mIntentSenderType;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mCreatorPackage);
            parcel.writeInt(this.mCreatorUid);
            parcel.writeBoolean(this.mImmutable);
            parcel.writeInt(this.mIntentSenderType);
        }
    }

    public String[] queryRegisteredReceiverPackages(Intent intent) {
        if (intent == null) {
            return new String[0];
        }
        try {
            return getService().queryRegisteredReceiverPackages(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(int i, String str, int i2) {
        try {
            return getService().getRestrictionInfo(i, str, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean canRestrict(int i, String str, int i2) {
        try {
            return getService().canRestrict(i, str, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restrict(int i, int i2, boolean z, String str, int i3) {
        try {
            return getService().restrict(i, i2, z, str, i3);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int i) {
        try {
            return getService().getRestrictableList(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList() {
        try {
            return getService().getAllRestrictedList();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int i) {
        try {
            return getService().getRestrictedList(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRestrictionInfo(SemAppRestrictionManager.RestrictionInfo restrictionInfo, List<SemAppRestrictionManager.AppRestrictionInfo> list) {
        try {
            return getService().updateRestrictionInfo(restrictionInfo, list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list) {
        try {
            return getService().clearRestrictionInfo(list);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PackageInfo> getInstalledPackageListFromMARs(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid >= 10000) {
            throw new SecurityException("Caller " + callingUid + " is not allowed to get pkgList from MARs");
        }
        try {
            ParceledListSlice<PackageInfo> installedPackageListFromMARs = getService().getInstalledPackageListFromMARs(i, i2);
            return installedPackageListFromMARs == null ? Collections.EMPTY_LIST : installedPackageListFromMARs.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceStopPackageByAdmin(String str, int i) {
        try {
            getService().forceStopPackageByAdmin(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getPackageFromAppProcesses(int i) {
        try {
            return getService().getPackageFromAppProcesses(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getAppLockedPackageList() {
        try {
            return getTaskService().getAppLockedPackageList();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void setAppLockedUnLockPackage(String str) {
        try {
            getTaskService().setAppLockedUnLockPackage(str);
        } catch (RemoteException unused) {
        }
    }

    public boolean isAppLockedPackage(String str) {
        try {
            return getTaskService().isAppLockedPackage(str);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void clearAppLockedUnLockedApp() {
        try {
            getTaskService().clearAppLockedUnLockedApp();
        } catch (RemoteException unused) {
        }
    }

    public String getAppLockedLockType() {
        try {
            return getTaskService().getAppLockedLockType();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public String getAppLockedCheckAction() {
        try {
            return getTaskService().getAppLockedCheckAction();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void setAppLockedVerifying(String str, boolean z) {
        try {
            getTaskService().setAppLockedVerifying(str, z);
        } catch (RemoteException unused) {
        }
    }

    public boolean isAppLockedVerifying(String str) {
        try {
            return getTaskService().isAppLockedVerifying(str);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setApplockLockedAppsPackage(String str) {
        try {
            getTaskService().setApplockLockedAppsPackage(str);
        } catch (RemoteException unused) {
        }
    }

    public void setApplockLockedAppsClass(String str) {
        try {
            getTaskService().setApplockLockedAppsClass(str);
        } catch (RemoteException unused) {
        }
    }

    public void setApplockType(int i) {
        try {
            getTaskService().setApplockType(i);
        } catch (RemoteException unused) {
        }
    }

    public void setApplockEnabled(boolean z) {
        try {
            getTaskService().setApplockEnabled(z);
        } catch (RemoteException unused) {
        }
    }

    public void setSsecureHiddenAppsPackages(String str) {
        try {
            getTaskService().setSsecureHiddenAppsPackages(str);
        } catch (RemoteException unused) {
        }
    }

    public String getApplockLockedAppsPackage() {
        try {
            return getTaskService().getApplockLockedAppsPackage();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public String getApplockLockedAppsClass() {
        try {
            return getTaskService().getApplockLockedAppsClass();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getApplockType() {
        try {
            return getTaskService().getApplockType();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public boolean isApplockEnabled() {
        try {
            return getTaskService().isApplockEnabled();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public String getSsecureHiddenAppsPackages() {
        try {
            return getTaskService().getSsecureHiddenAppsPackages();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public Bitmap getResumedTaskThumbnail(int i) throws SecurityException {
        try {
            return getTaskService().getResumedTaskThumbnail(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
