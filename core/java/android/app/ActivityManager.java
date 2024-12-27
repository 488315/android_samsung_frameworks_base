package android.app;

import android.Manifest;
import android.annotation.SystemApi;
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
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.hardware.HardwareBuffer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
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
import android.window.TaskSnapshot;

import com.android.internal.R;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.Preconditions;
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

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_AUDIO = 5;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_BLUETOOTH = 2;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_CAMERA = 1;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_CDM = 9;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_LOCATION = 3;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_MEDIA_PLAYBACK = 4;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_MICROPHONE = 6;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_PHONE_CALL = 7;

    @SystemApi public static final int FOREGROUND_SERVICE_API_TYPE_USB = 8;
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
    public static final int PROCESS_CAPABILITY_ALL = 127;
    public static final int PROCESS_CAPABILITY_ALL_IMPLICIT = 6;
    public static final int PROCESS_CAPABILITY_BFSL = 16;
    public static final int PROCESS_CAPABILITY_FOREGROUND_AUDIO_CONTROL = 64;

    @SystemApi public static final int PROCESS_CAPABILITY_FOREGROUND_CAMERA = 2;

    @SystemApi public static final int PROCESS_CAPABILITY_FOREGROUND_LOCATION = 1;

    @SystemApi public static final int PROCESS_CAPABILITY_FOREGROUND_MICROPHONE = 4;

    @SystemApi public static final int PROCESS_CAPABILITY_NONE = 0;
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
    private ActivityController mActivityController;
    Point mAppTaskThumbnailSize;
    private final Context mContext;
    private static String TAG = "ActivityManager";
    private static volatile boolean sSystemReady = false;
    private static volatile int sCurrentUser$ravenwood = -10000;
    private static final boolean DEVELOPMENT_FORCE_LOW_RAM =
            SystemProperties.getBoolean("debug.force_low_ram", false);
    private static final Singleton<IActivityManager> IActivityManagerSingleton =
            new Singleton<IActivityManager>() { // from class: android.app.ActivityManager.3
                @Override // android.util.Singleton
                public IActivityManager create() {
                    IBinder b = ServiceManager.getService("activity");
                    IActivityManager am = IActivityManager.Stub.asInterface(b);
                    return am;
                }
            };
    final ArrayMap<OnUidImportanceListener, MyUidObserver> mImportanceListeners = new ArrayMap<>();
    private final ArrayMap<UidFrozenStateChangedCallback, Executor> mFrozenStateChangedCallbacks =
            new ArrayMap<>();
    private final IUidFrozenStateChangedCallback mFrozenStateChangedCallback =
            new AnonymousClass1();
    private final Map<SemProcessListener, ProcessObserver> mProcessObserverMap = new HashMap();
    private final CopyOnWriteArrayList<SemProcessListener> mProcessListeners =
            new CopyOnWriteArrayList<>();
    private final ArrayList<AppStartInfoCallbackWrapper> mAppStartInfoCallbacks = new ArrayList<>();
    private IApplicationStartInfoCompleteListener mAppStartInfoCompleteListener = null;
    private final CopyOnWriteArrayList<SemActivityControllerListener> mActivityControllerListeners =
            new CopyOnWriteArrayList<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForegroundServiceApiEvent {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForegroundServiceApiType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface MoveTaskFlags {}

    @SystemApi
    public interface OnUidImportanceListener {
        void onUidImportance(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessCapability {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessResource {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface RestrictionLevel {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface RestrictionReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface RestrictionSource {}

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
    public @interface StopUserOnSwitch {}

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public interface UidFrozenStateChangedCallback {

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static final int UID_FROZEN_STATE_FROZEN = 1;

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static final int UID_FROZEN_STATE_UNFROZEN = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface UidFrozenState {}

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        void onUidFrozenStateChanged(int[] iArr, int[] iArr2);
    }

    static final class MyUidObserver extends UidObserver {
        final Context mContext;
        final OnUidImportanceListener mListener;

        MyUidObserver(OnUidImportanceListener listener, Context clientContext) {
            this.mListener = listener;
            this.mContext = clientContext;
        }

        @Override // android.app.UidObserver, android.app.IUidObserver
        public void onUidStateChanged(int uid, int procState, long procStateSeq, int capability) {
            this.mListener.onUidImportance(
                    uid,
                    RunningAppProcessInfo.procStateToImportanceForClient(procState, this.mContext));
        }

        @Override // android.app.UidObserver, android.app.IUidObserver
        public void onUidGone(int uid, boolean disabled) {
            this.mListener.onUidImportance(uid, 1000);
        }
    }

    /* renamed from: android.app.ActivityManager$1, reason: invalid class name */
    class AnonymousClass1 extends IUidFrozenStateChangedCallback.Stub {
        AnonymousClass1() {}

        @Override // android.app.IUidFrozenStateChangedCallback
        public void onUidFrozenStateChanged(final int[] uids, final int[] frozenStates) {
            synchronized (ActivityManager.this.mFrozenStateChangedCallbacks) {
                ActivityManager.this.mFrozenStateChangedCallbacks.forEach(
                        new BiConsumer() { // from class:
                            // android.app.ActivityManager$1$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                Executor executor = (Executor) obj2;
                                executor.execute(
                                        new Runnable() { // from class:
                                            // android.app.ActivityManager$1$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ActivityManager.UidFrozenStateChangedCallback.this
                                                        .onUidFrozenStateChanged(r2, r3);
                                            }
                                        });
                            }
                        });
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void registerUidFrozenStateChangedCallback(
            Executor executor, UidFrozenStateChangedCallback callback) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(callback, "callback cannot be null");
        synchronized (this.mFrozenStateChangedCallbacks) {
            if (this.mFrozenStateChangedCallbacks.containsKey(callback)) {
                throw new IllegalStateException("Callback already registered: " + callback);
            }
            this.mFrozenStateChangedCallbacks.put(callback, executor);
            if (this.mFrozenStateChangedCallbacks.size() > 1) {
                return;
            }
            try {
                getService()
                        .registerUidFrozenStateChangedCallback(this.mFrozenStateChangedCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void unregisterUidFrozenStateChangedCallback(UidFrozenStateChangedCallback callback) {
        Preconditions.checkNotNull(callback, "callback cannot be null");
        synchronized (this.mFrozenStateChangedCallbacks) {
            this.mFrozenStateChangedCallbacks.remove(callback);
            if (this.mFrozenStateChangedCallbacks.isEmpty()) {
                try {
                    getService()
                            .unregisterUidFrozenStateChangedCallback(
                                    this.mFrozenStateChangedCallback);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public int[] getUidFrozenState(int[] uids) {
        try {
            return getService().getUidFrozenState(uids);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void printCapabilitiesSummary(PrintWriter pw, int caps) {
        pw.print((caps & 1) != 0 ? DateFormat.STANDALONE_MONTH : '-');
        pw.print((caps & 2) != 0 ? 'C' : '-');
        pw.print((caps & 4) != 0 ? DateFormat.MONTH : '-');
        pw.print((caps & 8) != 0 ? PhoneNumberUtils.WILD : '-');
        pw.print((caps & 16) != 0 ? 'F' : '-');
        pw.print((caps & 32) != 0 ? 'U' : '-');
        pw.print((caps & 64) != 0 ? DateFormat.CAPITAL_AM_PM : '-');
    }

    public static void printCapabilitiesSummary(StringBuilder sb, int caps) {
        sb.append((caps & 1) != 0 ? DateFormat.STANDALONE_MONTH : '-');
        sb.append((caps & 2) != 0 ? 'C' : '-');
        sb.append((caps & 4) != 0 ? DateFormat.MONTH : '-');
        sb.append((caps & 8) != 0 ? PhoneNumberUtils.WILD : '-');
        sb.append((caps & 16) != 0 ? 'F' : '-');
        sb.append((caps & 32) != 0 ? 'U' : '-');
        sb.append((caps & 64) != 0 ? DateFormat.CAPITAL_AM_PM : '-');
    }

    public static void printCapabilitiesFull(PrintWriter pw, int caps) {
        printCapabilitiesSummary(pw, caps);
        int remain = caps & (-128);
        if (remain != 0) {
            pw.print("+0x");
            pw.print(Integer.toHexString(remain));
        }
    }

    public static String getCapabilitiesSummary(int caps) {
        StringBuilder sb = new StringBuilder();
        printCapabilitiesSummary(sb, caps);
        return sb.toString();
    }

    public static final int processStateAmToProto(int amInt) {
        switch (amInt) {
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

    public static final boolean isProcStateBackground(int procState) {
        return procState >= 8;
    }

    public static final boolean isProcStateCached(int procState) {
        return procState >= 16;
    }

    public static boolean isForegroundService(int procState) {
        return procState == 4;
    }

    private class ProcessObserver extends IProcessObserver.Stub {
        private ProcessObserver() {}

        @Override // android.app.IProcessObserver
        public void onProcessStarted(
                int pid, int processUid, int packageUid, String packageName, String processName) {}

        @Override // android.app.IProcessObserver
        public void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities) {
            if (ActivityManager.this.mProcessListeners != null
                    && !ActivityManager.this.mProcessListeners.isEmpty()) {
                Iterator it = ActivityManager.this.mProcessListeners.iterator();
                while (it.hasNext()) {
                    SemProcessListener i = (SemProcessListener) it.next();
                    i.onForegroundActivitiesChanged(pid, uid, foregroundActivities);
                }
            }
        }

        @Override // android.app.IProcessObserver
        public void onForegroundServicesChanged(int pid, int uid, int serviceTypes) {}

        @Override // android.app.IProcessObserver
        public void onProcessDied(int pid, int uid) {
            if (ActivityManager.this.mProcessListeners != null
                    && !ActivityManager.this.mProcessListeners.isEmpty()) {
                Iterator it = ActivityManager.this.mProcessListeners.iterator();
                while (it.hasNext()) {
                    SemProcessListener i = (SemProcessListener) it.next();
                    i.onProcessDied(pid, uid);
                }
            }
        }
    }

    ActivityManager(Context context, Handler handler) {
        this.mContext = context;
    }

    public static final boolean isStartResultSuccessful(int result) {
        return result >= 0 && result <= 99;
    }

    public static final boolean isStartResultFatalError(int result) {
        return -100 <= result && result <= -1;
    }

    public static String restrictionLevelToName(int level) {
        switch (level) {
            case 0:
                return "unknown";
            case 10:
                return "unrestricted";
            case 20:
                return "exempted";
            case 30:
                return "adaptive_bucket";
            case 40:
                return "restricted_bucket";
            case 50:
                return "background_restricted";
            case 60:
                return "stopped";
            case 70:
                return "user_only";
            case 90:
                return "custom";
            case 100:
                return "max";
            default:
                return String.valueOf(level);
        }
    }

    public int getFrontActivityScreenCompatMode() {
        try {
            return getTaskService().getFrontActivityScreenCompatMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFrontActivityScreenCompatMode(int mode) {
        try {
            getTaskService().setFrontActivityScreenCompatMode(mode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPackageScreenCompatMode(String packageName) {
        try {
            return getTaskService().getPackageScreenCompatMode(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageScreenCompatMode(String packageName, int mode) {
        try {
            getTaskService().setPackageScreenCompatMode(packageName, mode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getPackageAskScreenCompat(String packageName) {
        try {
            return getTaskService().getPackageAskScreenCompat(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageAskScreenCompat(String packageName, boolean ask) {
        try {
            getTaskService().setPackageAskScreenCompat(packageName, ask);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMemoryClass() {
        return staticGetMemoryClass();
    }

    public static int staticGetMemoryClass() {
        String vmHeapSize = SystemProperties.get("dalvik.vm.heapgrowthlimit", "");
        if (vmHeapSize != null && !"".equals(vmHeapSize)) {
            return Integer.parseInt(vmHeapSize.substring(0, vmHeapSize.length() - 1));
        }
        return staticGetLargeMemoryClass();
    }

    public int getLargeMemoryClass() {
        return staticGetLargeMemoryClass();
    }

    public static int staticGetLargeMemoryClass() {
        String vmHeapSize = SystemProperties.get("dalvik.vm.heapsize", "16m");
        return Integer.parseInt(vmHeapSize.substring(0, vmHeapSize.length() - 1));
    }

    public boolean isLowRamDevice() {
        return isLowRamDeviceStatic();
    }

    public static boolean isLowRamDeviceStatic() {
        return RoSystemProperties.CONFIG_LOW_RAM
                || (Build.IS_DEBUGGABLE && DEVELOPMENT_FORCE_LOW_RAM);
    }

    public static boolean isSmallBatteryDevice() {
        return RoSystemProperties.CONFIG_SMALL_BATTERY;
    }

    public static boolean isHighEndGfx() {
        return (isLowRamDeviceStatic()
                        || RoSystemProperties.CONFIG_AVOID_GFX_ACCEL
                        || Resources.getSystem().getBoolean(R.bool.config_avoidGfxAccel))
                ? false
                : true;
    }

    public long getTotalRam() {
        MemInfoReader memreader = new MemInfoReader();
        memreader.readMemInfo();
        return memreader.getTotalSize();
    }

    @Deprecated
    public static int getMaxRecentTasksStatic() {
        return ActivityTaskManager.getMaxRecentTasksStatic();
    }

    public static class TaskDescription implements Parcelable {
        private static final String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND =
                "task_description_color_background";
        private static final String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING =
                "task_description_color_background_floating";
        private static final String ATTR_TASKDESCRIPTIONCOLOR_PRIMARY = "task_description_color";
        private static final String ATTR_TASKDESCRIPTIONICON_FILENAME =
                "task_description_icon_filename";
        private static final String ATTR_TASKDESCRIPTIONICON_RESOURCE =
                "task_description_icon_resource";
        private static final String ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE =
                "task_description_icon_package";
        private static final String ATTR_TASKDESCRIPTIONLABEL = "task_description_label";
        public static final String ATTR_TASKDESCRIPTION_PREFIX = "task_description_";
        public static final Parcelable.Creator<TaskDescription> CREATOR =
                new Parcelable.Creator<TaskDescription>() { // from class:
                    // android.app.ActivityManager.TaskDescription.1
                    @Override // android.os.Parcelable.Creator
                    public TaskDescription createFromParcel(Parcel source) {
                        return new TaskDescription(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public TaskDescription[] newArray(int size) {
                        return new TaskDescription[size];
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

        public static final class Builder {
            private String mLabel = null;
            private int mIconRes = 0;
            private int mPrimaryColor = 0;
            private int mBackgroundColor = 0;
            private int mStatusBarColor = 0;
            private int mNavigationBarColor = 0;

            public Builder setLabel(String label) {
                this.mLabel = label;
                return this;
            }

            public Builder setIcon(int iconRes) {
                this.mIconRes = iconRes;
                return this;
            }

            public Builder setPrimaryColor(int color) {
                this.mPrimaryColor = color;
                return this;
            }

            public Builder setBackgroundColor(int color) {
                this.mBackgroundColor = color;
                return this;
            }

            public Builder setStatusBarColor(int color) {
                this.mStatusBarColor = color;
                return this;
            }

            public Builder setNavigationBarColor(int color) {
                this.mNavigationBarColor = color;
                return this;
            }

            public TaskDescription build() {
                Icon icon =
                        this.mIconRes == 0
                                ? null
                                : Icon.createWithResource(
                                        ActivityThread.currentPackageName(), this.mIconRes);
                return new TaskDescription(
                        this.mLabel,
                        icon,
                        this.mPrimaryColor,
                        this.mBackgroundColor,
                        this.mStatusBarColor,
                        this.mNavigationBarColor,
                        0,
                        0,
                        false,
                        false,
                        2,
                        -1,
                        -1,
                        0);
            }
        }

        @Deprecated
        public TaskDescription(String label, int iconRes, int colorPrimary) {
            this(
                    label,
                    Icon.createWithResource(ActivityThread.currentPackageName(), iconRes),
                    colorPrimary,
                    0,
                    0,
                    0,
                    0,
                    0,
                    false,
                    false,
                    2,
                    -1,
                    -1,
                    0);
            if (colorPrimary != 0 && Color.alpha(colorPrimary) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @Deprecated
        public TaskDescription(String label, int iconRes) {
            this(
                    label,
                    Icon.createWithResource(ActivityThread.currentPackageName(), iconRes),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    false,
                    false,
                    2,
                    -1,
                    -1,
                    0);
        }

        @Deprecated
        public TaskDescription(String label) {
            this(label, null, 0, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @Deprecated
        public TaskDescription() {
            this(null, null, 0, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @Deprecated
        public TaskDescription(String label, Bitmap icon, int colorPrimary) {
            this(
                    label,
                    icon != null ? Icon.createWithBitmap(icon) : null,
                    colorPrimary,
                    0,
                    0,
                    0,
                    0,
                    0,
                    false,
                    false,
                    2,
                    -1,
                    -1,
                    0);
            if (colorPrimary != 0 && Color.alpha(colorPrimary) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @Deprecated
        public TaskDescription(String label, Bitmap icon) {
            this(
                    label,
                    icon != null ? Icon.createWithBitmap(icon) : null,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    false,
                    false,
                    2,
                    -1,
                    -1,
                    0);
        }

        public TaskDescription(
                String label,
                Icon icon,
                int colorPrimary,
                int colorBackground,
                int statusBarColor,
                int navigationBarColor,
                int systemBarsAppearance,
                int topOpaqueSystemBarsAppearance,
                boolean ensureStatusBarContrastWhenTransparent,
                boolean ensureNavigationBarContrastWhenTransparent,
                int resizeMode,
                int minWidth,
                int minHeight,
                int colorBackgroundFloating) {
            this.mLabel = label;
            this.mIcon = icon;
            this.mColorPrimary = colorPrimary;
            this.mColorBackground = colorBackground;
            this.mStatusBarColor = statusBarColor;
            this.mNavigationBarColor = navigationBarColor;
            this.mSystemBarsAppearance = systemBarsAppearance;
            this.mTopOpaqueSystemBarsAppearance = topOpaqueSystemBarsAppearance;
            this.mEnsureStatusBarContrastWhenTransparent = ensureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent =
                    ensureNavigationBarContrastWhenTransparent;
            this.mResizeMode = resizeMode;
            this.mMinWidth = minWidth;
            this.mMinHeight = minHeight;
            this.mColorBackgroundFloating = colorBackgroundFloating;
        }

        public TaskDescription(TaskDescription td) {
            copyFrom(td);
        }

        public void copyFrom(TaskDescription other) {
            this.mLabel = other.mLabel;
            this.mIcon = other.mIcon;
            this.mIconFilename = other.mIconFilename;
            this.mColorPrimary = other.mColorPrimary;
            this.mColorBackground = other.mColorBackground;
            this.mStatusBarColor = other.mStatusBarColor;
            this.mNavigationBarColor = other.mNavigationBarColor;
            this.mSystemBarsAppearance = other.mSystemBarsAppearance;
            this.mTopOpaqueSystemBarsAppearance = other.mTopOpaqueSystemBarsAppearance;
            this.mEnsureStatusBarContrastWhenTransparent =
                    other.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent =
                    other.mEnsureNavigationBarContrastWhenTransparent;
            this.mResizeMode = other.mResizeMode;
            this.mMinWidth = other.mMinWidth;
            this.mMinHeight = other.mMinHeight;
            this.mColorBackgroundFloating = other.mColorBackgroundFloating;
        }

        public void copyFromPreserveHiddenFields(TaskDescription other) {
            this.mLabel = other.mLabel;
            this.mIcon = other.mIcon;
            this.mIconFilename = other.mIconFilename;
            this.mColorPrimary = other.mColorPrimary;
            if (other.mColorBackground != 0) {
                this.mColorBackground = other.mColorBackground;
            }
            if (other.mStatusBarColor != 0) {
                this.mStatusBarColor = other.mStatusBarColor;
            }
            if (other.mNavigationBarColor != 0) {
                this.mNavigationBarColor = other.mNavigationBarColor;
            }
            if (other.mSystemBarsAppearance != 0) {
                this.mSystemBarsAppearance = other.mSystemBarsAppearance;
            }
            if (other.mTopOpaqueSystemBarsAppearance != 0) {
                this.mTopOpaqueSystemBarsAppearance = other.mTopOpaqueSystemBarsAppearance;
            }
            this.mEnsureStatusBarContrastWhenTransparent =
                    other.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent =
                    other.mEnsureNavigationBarContrastWhenTransparent;
            if (other.mResizeMode != 2) {
                this.mResizeMode = other.mResizeMode;
            }
            if (other.mMinWidth != -1) {
                this.mMinWidth = other.mMinWidth;
            }
            if (other.mMinHeight != -1) {
                this.mMinHeight = other.mMinHeight;
            }
            if (other.mColorBackgroundFloating != 0) {
                this.mColorBackgroundFloating = other.mColorBackgroundFloating;
            }
        }

        private TaskDescription(Parcel source) {
            readFromParcel(source);
        }

        public void setLabel(String label) {
            this.mLabel = label;
        }

        public void setPrimaryColor(int primaryColor) {
            if (primaryColor != 0 && Color.alpha(primaryColor) != 255) {
                throw new RuntimeException("A TaskDescription's primary color should be opaque");
            }
            this.mColorPrimary = primaryColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            if (backgroundColor != 0 && Color.alpha(backgroundColor) != 255) {
                throw new RuntimeException("A TaskDescription's background color should be opaque");
            }
            this.mColorBackground = backgroundColor;
        }

        public void setBackgroundColorFloating(int backgroundColor) {
            if (backgroundColor != 0 && Color.alpha(backgroundColor) != 255) {
                throw new RuntimeException(
                        "A TaskDescription's background color floating should be opaque");
            }
            this.mColorBackgroundFloating = backgroundColor;
        }

        public void setStatusBarColor(int statusBarColor) {
            this.mStatusBarColor = statusBarColor;
        }

        public void setNavigationBarColor(int navigationBarColor) {
            this.mNavigationBarColor = navigationBarColor;
        }

        public void setIcon(Icon icon) {
            this.mIcon = icon;
        }

        public void setIconFilename(String iconFilename) {
            this.mIconFilename = iconFilename;
            if (iconFilename != null) {
                this.mIcon = null;
            }
        }

        public void setResizeMode(int resizeMode) {
            this.mResizeMode = resizeMode;
        }

        public void setMinWidth(int minWidth) {
            this.mMinWidth = minWidth;
        }

        public void setMinHeight(int minHeight) {
            this.mMinHeight = minHeight;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public Icon loadIcon() {
            if (this.mIcon != null) {
                return this.mIcon;
            }
            Bitmap loadedIcon = loadTaskDescriptionIcon(this.mIconFilename, UserHandle.myUserId());
            if (loadedIcon != null) {
                return Icon.createWithBitmap(loadedIcon);
            }
            return null;
        }

        @Deprecated
        public Bitmap getIcon() {
            Bitmap icon = getInMemoryIcon();
            if (icon != null) {
                return icon;
            }
            return loadTaskDescriptionIcon(this.mIconFilename, UserHandle.myUserId());
        }

        public Icon getRawIcon() {
            return this.mIcon;
        }

        public String getIconResourcePackage() {
            if (this.mIcon != null && this.mIcon.getType() == 2) {
                return this.mIcon.getResPackage();
            }
            return "";
        }

        public int getIconResource() {
            if (this.mIcon != null && this.mIcon.getType() == 2) {
                return this.mIcon.getResId();
            }
            return 0;
        }

        public String getIconFilename() {
            return this.mIconFilename;
        }

        public Bitmap getInMemoryIcon() {
            if (this.mIcon != null && this.mIcon.getType() == 1) {
                return this.mIcon.getBitmap();
            }
            return null;
        }

        public static Bitmap loadTaskDescriptionIcon(String iconFilename, int userId) {
            if (iconFilename != null) {
                try {
                    return ActivityManager.getTaskService()
                            .getTaskDescriptionIcon(iconFilename, userId);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return null;
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

        public void setEnsureStatusBarContrastWhenTransparent(
                boolean ensureStatusBarContrastWhenTransparent) {
            this.mEnsureStatusBarContrastWhenTransparent = ensureStatusBarContrastWhenTransparent;
        }

        public void setSystemBarsAppearance(int systemBarsAppearance) {
            this.mSystemBarsAppearance = systemBarsAppearance;
        }

        public void setTopOpaqueSystemBarsAppearance(int topOpaqueSystemBarsAppearance) {
            this.mTopOpaqueSystemBarsAppearance = topOpaqueSystemBarsAppearance;
        }

        public boolean getEnsureNavigationBarContrastWhenTransparent() {
            return this.mEnsureNavigationBarContrastWhenTransparent;
        }

        public void setEnsureNavigationBarContrastWhenTransparent(
                boolean ensureNavigationBarContrastWhenTransparent) {
            this.mEnsureNavigationBarContrastWhenTransparent =
                    ensureNavigationBarContrastWhenTransparent;
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

        public void saveToXml(TypedXmlSerializer out) throws IOException {
            if (this.mLabel != null) {
                out.attribute(null, ATTR_TASKDESCRIPTIONLABEL, this.mLabel);
            }
            if (this.mColorPrimary != 0) {
                out.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, this.mColorPrimary);
            }
            if (this.mColorBackground != 0) {
                out.attributeIntHex(
                        null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, this.mColorBackground);
            }
            if (this.mColorBackgroundFloating != 0) {
                out.attributeIntHex(
                        null,
                        ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING,
                        this.mColorBackgroundFloating);
            }
            if (this.mIconFilename != null) {
                out.attribute(null, ATTR_TASKDESCRIPTIONICON_FILENAME, this.mIconFilename);
            }
            if (this.mIcon != null && this.mIcon.getType() == 2) {
                out.attributeInt(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, this.mIcon.getResId());
                out.attribute(
                        null,
                        ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE,
                        this.mIcon.getResPackage());
            }
        }

        public void restoreFromXml(TypedXmlPullParser in) {
            String label = in.getAttributeValue(null, ATTR_TASKDESCRIPTIONLABEL);
            if (label != null) {
                setLabel(label);
            }
            int colorPrimary = in.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, 0);
            if (colorPrimary != 0) {
                setPrimaryColor(colorPrimary);
            }
            int colorBackground =
                    in.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, 0);
            if (colorBackground != 0) {
                setBackgroundColor(colorBackground);
            }
            int colorBackgroundFloating =
                    in.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING, 0);
            if (colorBackgroundFloating != 0) {
                setBackgroundColorFloating(colorBackgroundFloating);
            }
            String iconFilename = in.getAttributeValue(null, ATTR_TASKDESCRIPTIONICON_FILENAME);
            if (iconFilename != null) {
                setIconFilename(iconFilename);
            }
            int iconResourceId = in.getAttributeInt(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, 0);
            String iconResourcePackage =
                    in.getAttributeValue(null, ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE);
            if (iconResourceId != 0 && iconResourcePackage != null) {
                setIcon(Icon.createWithResource(iconResourcePackage, iconResourceId));
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            if (this.mLabel == null) {
                dest.writeInt(0);
            } else {
                dest.writeInt(1);
                dest.writeString(this.mLabel);
            }
            Bitmap bitmapIcon = getInMemoryIcon();
            if (this.mIcon == null || (bitmapIcon != null && bitmapIcon.isRecycled())) {
                dest.writeInt(0);
            } else {
                dest.writeInt(1);
                this.mIcon.writeToParcel(dest, 0);
            }
            dest.writeInt(this.mColorPrimary);
            dest.writeInt(this.mColorBackground);
            dest.writeInt(this.mStatusBarColor);
            dest.writeInt(this.mNavigationBarColor);
            dest.writeInt(this.mSystemBarsAppearance);
            dest.writeInt(this.mTopOpaqueSystemBarsAppearance);
            dest.writeBoolean(this.mEnsureStatusBarContrastWhenTransparent);
            dest.writeBoolean(this.mEnsureNavigationBarContrastWhenTransparent);
            dest.writeInt(this.mResizeMode);
            dest.writeInt(this.mMinWidth);
            dest.writeInt(this.mMinHeight);
            if (this.mIconFilename == null) {
                dest.writeInt(0);
            } else {
                dest.writeInt(1);
                dest.writeString(this.mIconFilename);
            }
            dest.writeInt(this.mColorBackgroundFloating);
        }

        public void readFromParcel(Parcel source) {
            this.mLabel = source.readInt() > 0 ? source.readString() : null;
            if (source.readInt() > 0) {
                this.mIcon = Icon.CREATOR.createFromParcel(source);
            }
            this.mColorPrimary = source.readInt();
            this.mColorBackground = source.readInt();
            this.mStatusBarColor = source.readInt();
            this.mNavigationBarColor = source.readInt();
            this.mSystemBarsAppearance = source.readInt();
            this.mTopOpaqueSystemBarsAppearance = source.readInt();
            this.mEnsureStatusBarContrastWhenTransparent = source.readBoolean();
            this.mEnsureNavigationBarContrastWhenTransparent = source.readBoolean();
            this.mResizeMode = source.readInt();
            this.mMinWidth = source.readInt();
            this.mMinHeight = source.readInt();
            this.mIconFilename = source.readInt() > 0 ? source.readString() : null;
            this.mColorBackgroundFloating = source.readInt();
        }

        public String toString() {
            return "TaskDescription Label: "
                    + this.mLabel
                    + " Icon: "
                    + this.mIcon
                    + " IconFilename: "
                    + this.mIconFilename
                    + " colorPrimary: "
                    + this.mColorPrimary
                    + " colorBackground: "
                    + this.mColorBackground
                    + " statusBarColor: "
                    + this.mStatusBarColor
                    + (this.mEnsureStatusBarContrastWhenTransparent
                            ? " (contrast when transparent)"
                            : "")
                    + " navigationBarColor: "
                    + this.mNavigationBarColor
                    + (this.mEnsureNavigationBarContrastWhenTransparent
                            ? " (contrast when transparent)"
                            : "")
                    + " resizeMode: "
                    + ActivityInfo.resizeModeToString(this.mResizeMode)
                    + " minWidth: "
                    + this.mMinWidth
                    + " minHeight: "
                    + this.mMinHeight
                    + " colorBackgrounFloating: "
                    + this.mColorBackgroundFloating
                    + " systemBarsAppearance: "
                    + this.mSystemBarsAppearance
                    + " topOpaqueSystemBarsAppearance: "
                    + this.mTopOpaqueSystemBarsAppearance;
        }

        public int hashCode() {
            int i = 17;
            if (this.mLabel != null) {
                i = (17 * 31) + this.mLabel.hashCode();
            }
            if (this.mIcon != null) {
                i = (i * 31) + this.mIcon.hashCode();
            }
            if (this.mIconFilename != null) {
                i = (i * 31) + this.mIconFilename.hashCode();
            }
            return (((((((((((((((((((((((i * 31) + this.mColorPrimary) * 31)
                                                                                                                                                                                    + this
                                                                                                                                                                                            .mColorBackground)
                                                                                                                                                                            * 31)
                                                                                                                                                                    + this
                                                                                                                                                                            .mColorBackgroundFloating)
                                                                                                                                                            * 31)
                                                                                                                                                    + this
                                                                                                                                                            .mStatusBarColor)
                                                                                                                                            * 31)
                                                                                                                                    + this
                                                                                                                                            .mNavigationBarColor)
                                                                                                                            * 31)
                                                                                                                    + this
                                                                                                                            .mSystemBarsAppearance)
                                                                                                            * 31)
                                                                                                    + this
                                                                                                            .mTopOpaqueSystemBarsAppearance)
                                                                                            * 31)
                                                                                    + (this
                                                                                                    .mEnsureStatusBarContrastWhenTransparent
                                                                                            ? 1
                                                                                            : 0))
                                                                            * 31)
                                                                    + (this
                                                                                    .mEnsureNavigationBarContrastWhenTransparent
                                                                            ? 1
                                                                            : 0))
                                                            * 31)
                                                    + this.mResizeMode)
                                            * 31)
                                    + this.mMinWidth)
                            * 31)
                    + this.mMinHeight;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TaskDescription)) {
                return false;
            }
            TaskDescription other = (TaskDescription) obj;
            return TextUtils.equals(this.mLabel, other.mLabel)
                    && TextUtils.equals(this.mIconFilename, other.mIconFilename)
                    && this.mIcon == other.mIcon
                    && this.mColorPrimary == other.mColorPrimary
                    && this.mColorBackground == other.mColorBackground
                    && this.mStatusBarColor == other.mStatusBarColor
                    && this.mNavigationBarColor == other.mNavigationBarColor
                    && this.mSystemBarsAppearance == other.mSystemBarsAppearance
                    && this.mTopOpaqueSystemBarsAppearance == other.mTopOpaqueSystemBarsAppearance
                    && this.mEnsureStatusBarContrastWhenTransparent
                            == other.mEnsureStatusBarContrastWhenTransparent
                    && this.mEnsureNavigationBarContrastWhenTransparent
                            == other.mEnsureNavigationBarContrastWhenTransparent
                    && this.mResizeMode == other.mResizeMode
                    && this.mMinWidth == other.mMinWidth
                    && this.mMinHeight == other.mMinHeight
                    && this.mColorBackgroundFloating == other.mColorBackgroundFloating;
        }

        public static boolean equals(TaskDescription td1, TaskDescription td2) {
            if (td1 == null && td2 == null) {
                return true;
            }
            if (td1 != null && td2 != null) {
                return td1.equals(td2);
            }
            return false;
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
        public static final Parcelable.Creator<RecentTaskInfo> CREATOR =
                new Parcelable.Creator<RecentTaskInfo>() { // from class:
                    // android.app.ActivityManager.RecentTaskInfo.1
                    @Override // android.os.Parcelable.Creator
                    public RecentTaskInfo createFromParcel(Parcel source) {
                        return new RecentTaskInfo(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public RecentTaskInfo[] newArray(int size) {
                        return new RecentTaskInfo[size];
                    }
                };

        @Deprecated public int affiliatedTaskId;
        public ArrayList<RecentTaskInfo> childrenTaskInfos;

        @Deprecated public CharSequence description;

        @Deprecated public int id;
        public PersistedTaskSnapshotData lastSnapshotData;

        @Deprecated public int persistentId;

        public static class PersistedTaskSnapshotData {
            public Point bufferSize;
            public Rect contentInsets;
            public Point taskSize;

            public void set(PersistedTaskSnapshotData other) {
                this.taskSize = other.taskSize;
                this.contentInsets = other.contentInsets;
                this.bufferSize = other.bufferSize;
            }

            public void set(TaskSnapshot snapshot) {
                Point point = null;
                if (snapshot == null) {
                    this.taskSize = null;
                    this.contentInsets = null;
                    this.bufferSize = null;
                } else {
                    HardwareBuffer buffer = snapshot.getHardwareBuffer();
                    this.taskSize = new Point(snapshot.getTaskSize());
                    this.contentInsets = new Rect(snapshot.getContentInsets());
                    if (buffer != null) {
                        point = new Point(buffer.getWidth(), buffer.getHeight());
                    }
                    this.bufferSize = point;
                }
            }
        }

        public RecentTaskInfo() {
            this.childrenTaskInfos = new ArrayList<>();
            this.lastSnapshotData = new PersistedTaskSnapshotData();
        }

        private RecentTaskInfo(Parcel source) {
            this.childrenTaskInfos = new ArrayList<>();
            this.lastSnapshotData = new PersistedTaskSnapshotData();
            readFromParcel(source);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.app.TaskInfo
        public void readFromParcel(Parcel source) {
            this.id = source.readInt();
            this.persistentId = source.readInt();
            this.childrenTaskInfos =
                    source.readArrayList(
                            RecentTaskInfo.class.getClassLoader(), RecentTaskInfo.class);
            this.lastSnapshotData.taskSize = (Point) source.readTypedObject(Point.CREATOR);
            this.lastSnapshotData.contentInsets = (Rect) source.readTypedObject(Rect.CREATOR);
            this.lastSnapshotData.bufferSize = (Point) source.readTypedObject(Point.CREATOR);
            super.readFromParcel(source);
        }

        @Override // android.app.TaskInfo, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.persistentId);
            dest.writeList(this.childrenTaskInfos);
            dest.writeTypedObject(this.lastSnapshotData.taskSize, flags);
            dest.writeTypedObject(this.lastSnapshotData.contentInsets, flags);
            dest.writeTypedObject(this.lastSnapshotData.bufferSize, flags);
            super.writeToParcel(dest, flags);
        }

        public void dump(PrintWriter pw, String indent) {
            pw.println();
            pw.print("   ");
            pw.print(" id=");
            pw.print(this.persistentId);
            pw.print(" userId=");
            pw.print(this.userId);
            pw.print(" hasTask=");
            boolean z = true;
            pw.print(this.id != -1);
            pw.print(" lastActiveTime=");
            pw.println(this.lastActiveTime);
            pw.print("   ");
            pw.print(" baseIntent=");
            pw.println(this.baseIntent);
            if (this.baseActivity != null) {
                pw.print("   ");
                pw.print(" baseActivity=");
                pw.println(this.baseActivity.toShortString());
            }
            if (this.topActivity != null) {
                pw.print("   ");
                pw.print(" topActivity=");
                pw.println(this.topActivity.toShortString());
            }
            if (this.origActivity != null) {
                pw.print("   ");
                pw.print(" origActivity=");
                pw.println(this.origActivity.toShortString());
            }
            if (this.realActivity != null) {
                pw.print("   ");
                pw.print(" realActivity=");
                pw.println(this.realActivity.toShortString());
            }
            pw.print("   ");
            pw.print(" isExcluded=");
            pw.print((this.baseIntent.getFlags() & 8388608) != 0);
            pw.print(" activityType=");
            pw.print(WindowConfiguration.activityTypeToString(getActivityType()));
            pw.print(" windowingMode=");
            pw.print(WindowConfiguration.windowingModeToString(getWindowingMode()));
            pw.print(" supportsMultiWindow=");
            pw.println(this.supportsMultiWindow);
            if (this.taskDescription != null) {
                pw.print("   ");
                TaskDescription td = this.taskDescription;
                pw.print(" taskDescription {");
                pw.print(" colorBackground=#");
                pw.print(Integer.toHexString(td.getBackgroundColor()));
                pw.print(" colorPrimary=#");
                pw.print(Integer.toHexString(td.getPrimaryColor()));
                pw.print(" iconRes=");
                pw.print(td.getIconResourcePackage() + "/" + td.getIconResource());
                pw.print(" iconBitmap=");
                if (td.getIconFilename() == null && td.getInMemoryIcon() == null) {
                    z = false;
                }
                pw.print(z);
                pw.print(" resizeMode=");
                pw.print(ActivityInfo.resizeModeToString(td.getResizeMode()));
                pw.print(" minWidth=");
                pw.print(td.getMinWidth());
                pw.print(" minHeight=");
                pw.print(td.getMinHeight());
                pw.print(" colorBackgroundFloating=#");
                pw.print(Integer.toHexString(td.getBackgroundColorFloating()));
                pw.println(" }");
            }
            pw.print("   ");
            pw.print(" lastSnapshotData {");
            pw.print(" taskSize=" + this.lastSnapshotData.taskSize);
            pw.print(" contentInsets=" + this.lastSnapshotData.contentInsets);
            pw.print(" bufferSize=" + this.lastSnapshotData.bufferSize);
            pw.println(" }");
        }
    }

    @Deprecated
    public List<RecentTaskInfo> getRecentTasks(int maxNum, int flags) throws SecurityException {
        if (maxNum < 0) {
            throw new IllegalArgumentException("The requested number of tasks should be >= 0");
        }
        return ActivityTaskManager.getInstance()
                .getRecentTasks(maxNum, flags, this.mContext.getUserId());
    }

    public static class RunningTaskInfo extends TaskInfo implements Parcelable {
        public static final Parcelable.Creator<RunningTaskInfo> CREATOR =
                new Parcelable.Creator<RunningTaskInfo>() { // from class:
                    // android.app.ActivityManager.RunningTaskInfo.1
                    @Override // android.os.Parcelable.Creator
                    public RunningTaskInfo createFromParcel(Parcel source) {
                        return new RunningTaskInfo(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public RunningTaskInfo[] newArray(int size) {
                        return new RunningTaskInfo[size];
                    }
                };

        @Deprecated public CharSequence description;

        @Deprecated public int id;

        @Deprecated public int numRunning;

        @Deprecated public Bitmap thumbnail;

        public RunningTaskInfo() {}

        private RunningTaskInfo(Parcel source) {
            readFromParcel(source);
        }

        public boolean semIsFreeform() {
            return this.configuration.windowConfiguration.getWindowingMode() == 5;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.app.TaskInfo
        public void readFromParcel(Parcel source) {
            this.id = source.readInt();
            super.readFromParcel(source);
        }

        @Override // android.app.TaskInfo, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            super.writeToParcel(dest, flags);
        }
    }

    public List<AppTask> getAppTasks() {
        ArrayList<AppTask> tasks = new ArrayList<>();
        try {
            List<IBinder> appTasks = getTaskService().getAppTasks(this.mContext.getOpPackageName());
            int numAppTasks = appTasks.size();
            for (int i = 0; i < numAppTasks; i++) {
                tasks.add(new AppTask(IAppTask.Stub.asInterface(appTasks.get(i))));
            }
            return tasks;
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

    public int addAppTask(
            Activity activity, Intent intent, TaskDescription description, Bitmap thumbnail) {
        Point size;
        float scale;
        synchronized (this) {
            ensureAppTaskThumbnailSizeLocked();
            size = this.mAppTaskThumbnailSize;
        }
        int tw = thumbnail.getWidth();
        int th = thumbnail.getHeight();
        if (tw != size.x || th != size.y) {
            Bitmap bm = Bitmap.createBitmap(size.x, size.y, thumbnail.getConfig());
            float dx = 0.0f;
            if (size.x * tw > size.y * th) {
                scale = size.x / th;
                dx = (size.y - (tw * scale)) * 0.5f;
            } else {
                scale = size.y / tw;
                float dy = (size.x - (th * scale)) * 0.5f;
            }
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postTranslate((int) (0.5f + dx), 0.0f);
            Canvas canvas = new Canvas(bm);
            canvas.drawBitmap(thumbnail, matrix, null);
            canvas.setBitmap(null);
            thumbnail = bm;
        }
        if (description == null) {
            description = new TaskDescription();
        }
        try {
            return getTaskService()
                    .addAppTask(activity.getActivityToken(), intent, description, thumbnail);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<RunningTaskInfo> getRunningTasks(int maxNum) throws SecurityException {
        return ActivityTaskManager.getInstance().getTasks(maxNum);
    }

    public boolean semRemoveTask(int taskId, int flags) throws SecurityException {
        try {
            return getTaskService().removeTaskWithFlags(taskId, flags);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void moveTaskToFront(int taskId, int flags) {
        moveTaskToFront(taskId, flags, null);
    }

    public void moveTaskToFront(int taskId, int flags, Bundle options) {
        try {
            ActivityThread thread = ActivityThread.currentActivityThread();
            IApplicationThread appThread = thread.getApplicationThread();
            String packageName = this.mContext.getOpPackageName();
            getTaskService().moveTaskToFront(appThread, packageName, taskId, flags, options);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActivityStartAllowedOnDisplay(Context context, int displayId, Intent intent) {
        try {
            return getTaskService()
                    .isActivityStartAllowedOnDisplay(
                            displayId,
                            intent,
                            intent.resolveTypeIfNeeded(context.getContentResolver()),
                            context.getUserId());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public static class RunningServiceInfo implements Parcelable {
        public static final Parcelable.Creator<RunningServiceInfo> CREATOR =
                new Parcelable.Creator<RunningServiceInfo>() { // from class:
                    // android.app.ActivityManager.RunningServiceInfo.1
                    @Override // android.os.Parcelable.Creator
                    public RunningServiceInfo createFromParcel(Parcel source) {
                        return new RunningServiceInfo(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public RunningServiceInfo[] newArray(int size) {
                        return new RunningServiceInfo[size];
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

        public RunningServiceInfo() {}

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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

        public void readFromParcel(Parcel source) {
            this.service = ComponentName.readFromParcel(source);
            this.pid = source.readInt();
            this.uid = source.readInt();
            this.process = source.readString();
            this.foreground = source.readInt() != 0;
            this.activeSince = source.readLong();
            this.started = source.readInt() != 0;
            this.clientCount = source.readInt();
            this.crashCount = source.readInt();
            this.lastActivityTime = source.readLong();
            this.restarting = source.readLong();
            this.flags = source.readInt();
            this.clientPackage = source.readString();
            this.clientLabel = source.readInt();
        }

        private RunningServiceInfo(Parcel source) {
            readFromParcel(source);
        }
    }

    @Deprecated
    public List<RunningServiceInfo> getRunningServices(int maxNum) throws SecurityException {
        try {
            return getService().getServices(maxNum, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PendingIntent getRunningServiceControlPanel(ComponentName service)
            throws SecurityException {
        try {
            return getService().getRunningServiceControlPanel(service);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class MemoryInfo implements Parcelable {
        public static final Parcelable.Creator<MemoryInfo> CREATOR =
                new Parcelable.Creator<
                        MemoryInfo>() { // from class: android.app.ActivityManager.MemoryInfo.1
                    @Override // android.os.Parcelable.Creator
                    public MemoryInfo createFromParcel(Parcel source) {
                        return new MemoryInfo(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public MemoryInfo[] newArray(int size) {
                        return new MemoryInfo[size];
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

        public MemoryInfo() {}

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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

        public void readFromParcel(Parcel source) {
            this.advertisedMem = source.readLong();
            this.availMem = source.readLong();
            this.totalMem = source.readLong();
            this.threshold = source.readLong();
            this.lowMemory = source.readInt() != 0;
            this.hiddenAppThreshold = source.readLong();
            this.secondaryServerThreshold = source.readLong();
            this.visibleAppThreshold = source.readLong();
            this.foregroundAppThreshold = source.readLong();
        }

        private MemoryInfo(Parcel source) {
            readFromParcel(source);
        }
    }

    public void getMemoryInfo(MemoryInfo outInfo) {
        try {
            getService().getMemoryInfo(outInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData(String packageName, IPackageDataObserver observer) {
        try {
            return getService()
                    .clearApplicationUserData(
                            packageName, false, observer, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData() {
        return clearApplicationUserData(this.mContext.getPackageName(), null);
    }

    @Deprecated
    public ParceledListSlice<GrantedUriPermission> getGrantedUriPermissions(String packageName) {
        return ((UriGrantsManager) this.mContext.getSystemService(Context.URI_GRANTS_SERVICE))
                .getGrantedUriPermissions(packageName);
    }

    @Deprecated
    public void clearGrantedUriPermissions(String packageName) {
        ((UriGrantsManager) this.mContext.getSystemService(Context.URI_GRANTS_SERVICE))
                .clearGrantedUriPermissions(packageName);
    }

    public static class ProcessErrorStateInfo implements Parcelable {
        public static final int CRASHED = 1;
        public static final Parcelable.Creator<ProcessErrorStateInfo> CREATOR =
                new Parcelable.Creator<ProcessErrorStateInfo>() { // from class:
                    // android.app.ActivityManager.ProcessErrorStateInfo.1
                    @Override // android.os.Parcelable.Creator
                    public ProcessErrorStateInfo createFromParcel(Parcel source) {
                        return new ProcessErrorStateInfo(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public ProcessErrorStateInfo[] newArray(int size) {
                        return new ProcessErrorStateInfo[size];
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

        public ProcessErrorStateInfo() {
            this.crashData = null;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.condition);
            dest.writeString(this.processName);
            dest.writeInt(this.pid);
            dest.writeInt(this.uid);
            dest.writeString(this.tag);
            dest.writeString(this.shortMsg);
            dest.writeString(this.longMsg);
            dest.writeString(this.stackTrace);
        }

        public void readFromParcel(Parcel source) {
            this.condition = source.readInt();
            this.processName = source.readString();
            this.pid = source.readInt();
            this.uid = source.readInt();
            this.tag = source.readString();
            this.shortMsg = source.readString();
            this.longMsg = source.readString();
            this.stackTrace = source.readString();
        }

        private ProcessErrorStateInfo(Parcel source) {
            this.crashData = null;
            readFromParcel(source);
        }
    }

    public List<ProcessErrorStateInfo> getProcessesInErrorState() {
        try {
            return getService().getProcessesInErrorState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class RunningAppProcessInfo implements Parcelable {
        public static final Parcelable.Creator<RunningAppProcessInfo> CREATOR =
                new Parcelable.Creator<RunningAppProcessInfo>() { // from class:
                    // android.app.ActivityManager.RunningAppProcessInfo.1
                    @Override // android.os.Parcelable.Creator
                    public RunningAppProcessInfo createFromParcel(Parcel source) {
                        return new RunningAppProcessInfo(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public RunningAppProcessInfo[] newArray(int size) {
                        return new RunningAppProcessInfo[size];
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

        @Deprecated public static final int IMPORTANCE_EMPTY = 500;
        public static final int IMPORTANCE_FOREGROUND = 100;
        public static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
        public static final int IMPORTANCE_GONE = 1000;
        public static final int IMPORTANCE_PERCEPTIBLE = 230;
        public static final int IMPORTANCE_PERCEPTIBLE_PRE_26 = 130;
        public static final int IMPORTANCE_SERVICE = 300;
        public static final int IMPORTANCE_TOP_SLEEPING = 325;

        @Deprecated public static final int IMPORTANCE_TOP_SLEEPING_PRE_28 = 150;
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
        public @interface Importance {}

        public static int procStateToImportance(int procState) {
            if (procState == 20) {
                return 1000;
            }
            if (procState >= 14) {
                return 400;
            }
            if (procState == 13) {
                return 350;
            }
            if (procState >= 12) {
                return 325;
            }
            if (procState >= 10) {
                return 300;
            }
            if (procState >= 8) {
                return 230;
            }
            if (procState >= 6) {
                return 200;
            }
            if (procState >= 4) {
                return 125;
            }
            return 100;
        }

        public static int procStateToImportanceForClient(int procState, Context clientContext) {
            return procStateToImportanceForTargetSdk(
                    procState, clientContext.getApplicationInfo().targetSdkVersion);
        }

        public static int procStateToImportanceForTargetSdk(int procState, int targetSdkVersion) {
            int importance = procStateToImportance(procState);
            if (targetSdkVersion < 26) {
                switch (importance) {
                    case 230:
                        return 130;
                    case 325:
                        return 150;
                    case 350:
                        return 170;
                }
            }
            return importance;
        }

        public static int importanceToProcState(int importance) {
            if (importance == 1000) {
                return 20;
            }
            if (importance >= 400) {
                return 14;
            }
            if (importance >= 350) {
                return 13;
            }
            if (importance >= 325) {
                return 12;
            }
            if (importance >= 300) {
                return 10;
            }
            if (importance >= 230) {
                return 8;
            }
            if (importance >= 200 || importance >= 150) {
                return 6;
            }
            if (importance >= 125) {
                return 4;
            }
            return 2;
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

        public RunningAppProcessInfo(String pProcessName, int pPid, String[] pArr) {
            this.processName = pProcessName;
            this.pid = pPid;
            this.pkgList = pArr;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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

        public void readFromParcel(Parcel source) {
            this.processName = source.readString();
            this.pid = source.readInt();
            this.uid = source.readInt();
            this.pkgList = source.readStringArray();
            this.pkgDeps = source.readStringArray();
            this.flags = source.readInt();
            this.lastTrimLevel = source.readInt();
            this.importance = source.readInt();
            this.lru = source.readInt();
            this.importanceReasonCode = source.readInt();
            this.importanceReasonPid = source.readInt();
            this.importanceReasonComponent = ComponentName.readFromParcel(source);
            this.importanceReasonImportance = source.readInt();
            this.processState = source.readInt();
            this.isFocused = source.readInt() != 0;
            this.lastActivityTime = source.readLong();
        }

        private RunningAppProcessInfo(Parcel source) {
            readFromParcel(source);
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

    public boolean setProcessMemoryTrimLevel(String process, int userId, int level) {
        try {
            return getService().setProcessMemoryTrimLevel(process, userId, level);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setFGSFilter(int uid, boolean enable) {
        try {
            return getService().setFGSFilter(uid, enable);
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

    public boolean isFreezableUid(int uid) {
        try {
            return getService().isFreezableUid(uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setProcessSlowdown(int pid, boolean enable) {
        try {
            return getService().setProcessSlowdown(pid, enable);
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
        try {
            return getService().getRunningAppProcesses();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ApplicationStartInfo> getHistoricalProcessStartReasons(int maxNum) {
        try {
            ParceledListSlice<ApplicationStartInfo> startInfos =
                    getService()
                            .getHistoricalProcessStartReasons(
                                    null, maxNum, this.mContext.getUserId());
            return startInfos == null ? Collections.emptyList() : startInfos.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<ApplicationStartInfo> getExternalHistoricalProcessStartReasons(
            String packageName, int maxNum) {
        try {
            ParceledListSlice<ApplicationStartInfo> startInfos =
                    getService()
                            .getHistoricalProcessStartReasons(
                                    packageName, maxNum, this.mContext.getUserId());
            return startInfos == null ? Collections.emptyList() : startInfos.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static final class AppStartInfoCallbackWrapper {
        final Executor mExecutor;
        final Consumer<ApplicationStartInfo> mListener;

        AppStartInfoCallbackWrapper(Executor executor, Consumer<ApplicationStartInfo> listener) {
            this.mExecutor = executor;
            this.mListener = listener;
        }
    }

    public void addApplicationStartInfoCompletionListener(
            Executor executor, Consumer<ApplicationStartInfo> listener) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(listener, "listener cannot be null");
        synchronized (this.mAppStartInfoCallbacks) {
            for (int i = 0; i < this.mAppStartInfoCallbacks.size(); i++) {
                if (listener.equals(this.mAppStartInfoCallbacks.get(i).mListener)) {
                    return;
                }
            }
            if (this.mAppStartInfoCompleteListener == null) {
                this.mAppStartInfoCompleteListener = new AnonymousClass2();
                try {
                    getService()
                            .addApplicationStartInfoCompleteListener(
                                    this.mAppStartInfoCompleteListener, this.mContext.getUserId());
                    if (1 != 0) {
                        this.mAppStartInfoCallbacks.add(
                                new AppStartInfoCallbackWrapper(executor, listener));
                    } else {
                        this.mAppStartInfoCompleteListener = null;
                        this.mAppStartInfoCallbacks.clear();
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                this.mAppStartInfoCallbacks.add(
                        new AppStartInfoCallbackWrapper(executor, listener));
            }
        }
    }

    /* renamed from: android.app.ActivityManager$2, reason: invalid class name */
    class AnonymousClass2 extends IApplicationStartInfoCompleteListener.Stub {
        AnonymousClass2() {}

        @Override // android.app.IApplicationStartInfoCompleteListener
        public void onApplicationStartInfoComplete(
                final ApplicationStartInfo applicationStartInfo) {
            synchronized (ActivityManager.this.mAppStartInfoCallbacks) {
                for (int i = 0; i < ActivityManager.this.mAppStartInfoCallbacks.size(); i++) {
                    final AppStartInfoCallbackWrapper callback =
                            (AppStartInfoCallbackWrapper)
                                    ActivityManager.this.mAppStartInfoCallbacks.get(i);
                    callback.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.app.ActivityManager$2$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ActivityManager.AppStartInfoCallbackWrapper.this.mListener
                                            .accept(applicationStartInfo);
                                }
                            });
                }
                ActivityManager.this.mAppStartInfoCallbacks.clear();
                ActivityManager.this.mAppStartInfoCompleteListener = null;
            }
        }
    }

    public void removeApplicationStartInfoCompletionListener(
            Consumer<ApplicationStartInfo> listener) {
        Preconditions.checkNotNull(listener, "listener cannot be null");
        synchronized (this.mAppStartInfoCallbacks) {
            int i = 0;
            while (true) {
                if (i >= this.mAppStartInfoCallbacks.size()) {
                    break;
                }
                AppStartInfoCallbackWrapper callback = this.mAppStartInfoCallbacks.get(i);
                if (!listener.equals(callback.mListener)) {
                    i++;
                } else {
                    this.mAppStartInfoCallbacks.remove(i);
                    break;
                }
            }
            if (this.mAppStartInfoCompleteListener != null
                    && this.mAppStartInfoCallbacks.isEmpty()) {
                try {
                    getService()
                            .removeApplicationStartInfoCompleteListener(
                                    this.mAppStartInfoCompleteListener, this.mContext.getUserId());
                    this.mAppStartInfoCompleteListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void addStartInfoTimestamp(int key, long timestampNs) {
        if (key <= 20 || key > 30) {
            throw new IllegalArgumentException("Key not in allowed range.");
        }
        try {
            getService().addStartInfoTimestamp(key, timestampNs, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ApplicationExitInfo> getHistoricalProcessExitReasons(
            String packageName, int pid, int maxNum) {
        try {
            ParceledListSlice<ApplicationExitInfo> r =
                    getService()
                            .getHistoricalProcessExitReasons(
                                    packageName, pid, maxNum, this.mContext.getUserId());
            return r == null ? Collections.emptyList() : r.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setProcessStateSummary(byte[] state) {
        try {
            getService().setProcessStateSummary(state);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isLowMemoryKillReportSupported() {
        return SystemProperties.getBoolean("persist.sys.lmk.reportkills", false);
    }

    public int getUidProcessState(int uid) {
        try {
            return getService().getUidProcessState(uid, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUidProcessCapabilities(int uid) {
        try {
            return getService().getUidProcessCapabilities(uid, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getPackageImportance(String packageName) {
        try {
            int procState =
                    getService()
                            .getPackageProcessState(packageName, this.mContext.getOpPackageName());
            return RunningAppProcessInfo.procStateToImportanceForClient(procState, this.mContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getUidImportance(int uid) {
        try {
            int procState = getService().getUidProcessState(uid, this.mContext.getOpPackageName());
            return RunningAppProcessInfo.procStateToImportanceForClient(procState, this.mContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getBindingUidImportance(int uid) {
        try {
            int procState =
                    getService().getBindingUidProcessState(uid, this.mContext.getOpPackageName());
            return RunningAppProcessInfo.procStateToImportanceForClient(procState, this.mContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnUidImportanceListener(
            OnUidImportanceListener listener, int importanceCutpoint) {
        addOnUidImportanceListenerInternal(listener, importanceCutpoint, null);
    }

    @SystemApi
    public void addOnUidImportanceListener(
            OnUidImportanceListener listener, int importanceCutpoint, int[] uids) {
        Objects.requireNonNull(listener);
        Objects.requireNonNull(uids);
        addOnUidImportanceListenerInternal(listener, importanceCutpoint, uids);
    }

    private void addOnUidImportanceListenerInternal(
            OnUidImportanceListener listener, int importanceCutpoint, int[] uids) {
        synchronized (this.mImportanceListeners) {
            if (this.mImportanceListeners.containsKey(listener)) {
                throw new IllegalArgumentException("Listener already registered: " + listener);
            }
            MyUidObserver observer = new MyUidObserver(listener, this.mContext);
            try {
                getService()
                        .registerUidObserverForUids(
                                observer,
                                3,
                                RunningAppProcessInfo.importanceToProcState(importanceCutpoint),
                                this.mContext.getOpPackageName(),
                                uids);
                this.mImportanceListeners.put(listener, observer);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void removeOnUidImportanceListener(OnUidImportanceListener listener) {
        synchronized (this.mImportanceListeners) {
            MyUidObserver observer = this.mImportanceListeners.remove(listener);
            if (observer == null) {
                throw new IllegalArgumentException("Listener not registered: " + listener);
            }
            try {
                getService().unregisterUidObserver(observer);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static void getMyMemoryState(RunningAppProcessInfo outState) {
        try {
            getService().getMyMemoryState(outState);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) {
        try {
            return getService().getProcessMemoryInfo(pids);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void restartPackage(String packageName) {
        killBackgroundProcesses(packageName);
    }

    public void killBackgroundProcesses(String packageName) {
        try {
            getService().killBackgroundProcesses(packageName, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void killUid(int uid, String reason) {
        try {
            getService().killUid(UserHandle.getAppId(uid), UserHandle.getUserId(uid), reason);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semKillUid(int uid, String reason) {
        killUid(uid, reason);
    }

    public void forceStopPackageAsUser(String packageName, int userId) {
        try {
            getService().forceStopPackage(packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void forceStopPackage(String packageName) {
        forceStopPackageAsUser(packageName, this.mContext.getUserId());
    }

    public void semForceStopPackage(String packageName) {
        forceStopPackage(packageName);
    }

    public void forceStopPackageAsUserEvenWhenStopping(String packageName, int userId) {
        try {
            getService().forceStopPackageEvenWhenStopping(packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDeviceLocales(LocaleList locales) {
        LocalePicker.updateLocales(locales);
    }

    @SystemApi
    public Collection<Locale> getSupportedLocales() {
        ArrayList<Locale> locales = new ArrayList<>();
        for (String localeTag : LocalePicker.getSupportedLocales(this.mContext)) {
            locales.add(Locale.forLanguageTag(localeTag));
        }
        return locales;
    }

    public ConfigurationInfo getDeviceConfigurationInfo() {
        try {
            return getTaskService().getDeviceConfigurationInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLauncherLargeIconDensity() {
        Resources res = this.mContext.getResources();
        int density = res.getDisplayMetrics().densityDpi;
        int sw = res.getConfiguration().smallestScreenWidthDp;
        if (sw < 600) {
            return density;
        }
        switch (density) {
            case 120:
                return 160;
            case 160:
                return 240;
            case 213:
                return 320;
            case 240:
                return 320;
            case 320:
                return 480;
            case 480:
                return 640;
            default:
                return (int) ((density * 1.5f) + 0.5f);
        }
    }

    public int getLauncherLargeIconSize() {
        return getLauncherLargeIconSizeInner(this.mContext);
    }

    static int getLauncherLargeIconSizeInner(Context context) {
        Resources res = context.getResources();
        int size = res.getDimensionPixelSize(17104896);
        int sw = res.getConfiguration().smallestScreenWidthDp;
        if (sw < 600) {
            return size;
        }
        int density = res.getDisplayMetrics().densityDpi;
        switch (density) {
            case 120:
                return (size * 160) / 120;
            case 160:
                return (size * 240) / 160;
            case 213:
                return (size * 320) / 240;
            case 240:
                return (size * 320) / 240;
            case 320:
                return (size * 480) / 320;
            case 480:
                return ((size * 320) * 2) / 480;
            default:
                return (int) ((size * 1.5f) + 0.5f);
        }
    }

    public static boolean isUserAMonkey() {
        try {
            return getService().isUserAMonkey();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isUserAMonkey$ravenwood() {
        return false;
    }

    @Deprecated
    public static boolean isRunningInTestHarness() {
        return SystemProperties.getBoolean("ro.test_harness", false);
    }

    public static boolean isRunningInUserTestHarness() {
        return SystemProperties.getBoolean("persist.sys.test_harness", false);
    }

    public void alwaysShowUnsupportedCompileSdkWarning(ComponentName activity) {
        try {
            getTaskService().alwaysShowUnsupportedCompileSdkWarning(activity);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean canAccessUnexportedComponents(int uid) {
        int appId = UserHandle.getAppId(uid);
        return appId == 0 || appId == 1000;
    }

    public static int checkComponentPermission(
            String permission, int uid, int owningUid, boolean exported) {
        return checkComponentPermission(permission, uid, 0, owningUid, exported);
    }

    public static int checkComponentPermission(
            String permission, int uid, int deviceId, int owningUid, boolean exported) {
        if (canAccessUnexportedComponents(uid)) {
            return 0;
        }
        if (UserHandle.isIsolated(uid)) {
            return -1;
        }
        if (owningUid >= 0 && UserHandle.isSameApp(uid, owningUid)) {
            return 0;
        }
        if (!exported) {
            return -1;
        }
        if (permission == null) {
            return 0;
        }
        if (((UserHandle.getUserId(owningUid) == 0
                                && SemDualAppManager.isDualAppId(UserHandle.getUserId(uid)))
                        || (UserHandle.getUserId(uid) == 0
                                && SemDualAppManager.isDualAppId(UserHandle.getUserId(owningUid))))
                && (Manifest.permission.INTERACT_ACROSS_USERS.equals(permission)
                        || Manifest.permission.INTERACT_ACROSS_USERS_FULL.equals(permission))) {
            return 0;
        }
        try {
            return AppGlobals.getPermissionManager().checkUidPermission(uid, permission, deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int checkUidPermission(String permission, int uid) {
        try {
            return AppGlobals.getPermissionManager().checkUidPermission(uid, permission, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int handleIncomingUser(
            int callingPid,
            int callingUid,
            int userId,
            boolean allowAll,
            boolean requireFull,
            String name,
            String callerPackage) {
        if (UserHandle.getUserId(callingUid) == userId) {
            return userId;
        }
        try {
            return getService()
                    .handleIncomingUser(
                            callingPid,
                            callingUid,
                            userId,
                            allowAll,
                            requireFull,
                            name,
                            callerPackage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static int getCurrentUser() {
        try {
            return getService().getCurrentUserId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int semGetCurrentUser() {
        return getCurrentUser();
    }

    public boolean switchUser(int userid) {
        try {
            return getService().switchUser(userid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean switchUser(UserHandle user) {
        Preconditions.checkArgument(user != null, "UserHandle cannot be null.");
        return switchUser(user.getIdentifier());
    }

    public boolean startUserInBackgroundVisibleOnDisplay(int userId, int displayId) {
        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
            throw new UnsupportedOperationException(
                    "device does not support users on secondary displays");
        }
        try {
            return getService().startUserInBackgroundVisibleOnDisplay(userId, displayId, null);
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

    public String getSwitchingFromUserMessage() {
        try {
            return getService().getSwitchingFromUserMessage();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public String getSwitchingToUserMessage() {
        try {
            return getService().getSwitchingToUserMessage();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void setStopUserOnSwitch(int value) {
        try {
            getService().setStopUserOnSwitch(value);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean startProfile(UserHandle userHandle) {
        try {
            return getService().startProfile(userHandle.getIdentifier());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean stopProfile(UserHandle userHandle) {
        try {
            return getService().stopProfile(userHandle.getIdentifier());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean updateMccMncConfiguration(String mcc, String mnc) {
        if (mcc == null || mnc == null) {
            throw new IllegalArgumentException("mcc or mnc cannot be null.");
        }
        try {
            return getService().updateMccMncConfiguration(mcc, mnc);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean stopUser(int userId) {
        if (userId == 0) {
            return false;
        }
        try {
            return getService().stopUserWithCallback(userId, null) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunning(int userId) {
        try {
            return getService().isUserRunning(userId, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVrModePackageEnabled(ComponentName component) {
        try {
            return getService().isVrModePackageEnabled(component);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void dumpPackageState(FileDescriptor fd, String packageName) {
        dumpPackageStateStatic(fd, packageName);
    }

    public static void dumpPackageStateStatic(FileDescriptor fd, String packageName) {
        FileOutputStream fout = new FileOutputStream(fd);
        PrintWriter pw = new FastPrintWriter(fout);
        dumpService(pw, fd, "package", new String[] {packageName});
        pw.println();
        dumpService(pw, fd, "activity", new String[] {"-a", "package", packageName});
        pw.println();
        dumpService(pw, fd, "meminfo", new String[] {"--local", "--package", packageName});
        pw.println();
        dumpService(pw, fd, ProcessStats.SERVICE_NAME, new String[] {packageName});
        pw.println();
        dumpService(pw, fd, Context.USAGE_STATS_SERVICE, new String[] {packageName});
        pw.println();
        dumpService(pw, fd, "batterystats", new String[] {packageName});
        pw.flush();
    }

    public static boolean isSystemReady() {
        if (!sSystemReady) {
            if (ActivityThread.isSystem()) {
                sSystemReady =
                        ((ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class))
                                .isSystemReady();
            } else {
                sSystemReady = true;
            }
        }
        return sSystemReady;
    }

    public static boolean isSystemReady$ravenwood() {
        return true;
    }

    public static void broadcastStickyIntent(Intent intent, int userId) {
        broadcastStickyIntent(intent, -1, null, userId);
    }

    public static void broadcastStickyIntent(Intent intent, int appOp, int userId) {
        broadcastStickyIntent(intent, appOp, null, userId);
    }

    public static void broadcastStickyIntent(Intent intent, int appOp, Bundle options, int userId) {
        broadcastStickyIntent(intent, null, appOp, options, userId);
    }

    public static void broadcastStickyIntent(
            Intent intent, String[] excludedPackages, int appOp, Bundle options, int userId) {
        try {
            getService()
                    .broadcastIntentWithFeature(
                            null,
                            null,
                            intent,
                            null,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            excludedPackages,
                            appOp,
                            options,
                            false,
                            true,
                            userId);
        } catch (RemoteException e) {
        }
    }

    public static void resumeAppSwitches() throws RemoteException {
        getService().resumeAppSwitches();
    }

    public static void noteWakeupAlarm(
            PendingIntent ps, WorkSource workSource, int sourceUid, String sourcePkg, String tag) {
        try {
            getService()
                    .noteWakeupAlarm(
                            ps != null ? ps.getTarget() : null,
                            workSource,
                            sourceUid,
                            sourcePkg,
                            tag);
        } catch (RemoteException e) {
        }
    }

    public static void noteAlarmStart(
            PendingIntent ps, WorkSource workSource, int sourceUid, String tag) {
        try {
            getService()
                    .noteAlarmStart(ps != null ? ps.getTarget() : null, workSource, sourceUid, tag);
        } catch (RemoteException e) {
        }
    }

    public static void noteAlarmFinish(
            PendingIntent ps, WorkSource workSource, int sourceUid, String tag) {
        try {
            getService()
                    .noteAlarmFinish(
                            ps != null ? ps.getTarget() : null, workSource, sourceUid, tag);
        } catch (RemoteException e) {
        }
    }

    public static IActivityManager getService() {
        return IActivityManagerSingleton.get();
    }

    public static IActivityTaskManager getTaskService() {
        return ActivityTaskManager.getService();
    }

    private static void dumpService(PrintWriter pw, FileDescriptor fd, String name, String[] args) {
        pw.print("DUMP OF SERVICE ");
        pw.print(name);
        pw.println(":");
        IBinder service = ServiceManager.checkService(name);
        if (service == null) {
            pw.println("  (Service not found)");
            pw.flush();
            return;
        }
        pw.flush();
        if (service instanceof Binder) {
            try {
                service.dump(fd, args);
                return;
            } catch (Throwable e) {
                pw.println("Failure dumping service:");
                e.printStackTrace(pw);
                pw.flush();
                return;
            }
        }
        TransferPipe tp = null;
        try {
            pw.flush();
            tp = new TransferPipe();
            tp.setBufferPrefix("  ");
            service.dumpAsync(tp.getWriteFd().getFileDescriptor(), args);
            tp.go(fd, JobInfo.MIN_BACKOFF_MILLIS);
        } catch (Throwable e2) {
            if (tp != null) {
                tp.kill();
            }
            pw.println("Failure dumping service:");
            e2.printStackTrace(pw);
        }
    }

    public void setWatchHeapLimit(long pssSize) {
        try {
            getService().setDumpHeapDebugLimit(null, 0, pssSize, this.mContext.getPackageName());
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

    public static void setVrThread(int tid) {
        try {
            getTaskService().setVrThread(tid);
        } catch (RemoteException e) {
        }
    }

    @SystemApi
    public static void setPersistentVrThread(int tid) {
        try {
            getService().setPersistentVrThread(tid);
        } catch (RemoteException e) {
        }
    }

    public void scheduleApplicationInfoChanged(List<String> packages, int userId) {
        try {
            getService().scheduleApplicationInfoChanged(packages, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProfileForeground(UserHandle userHandle) {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager != null) {
            for (UserInfo userInfo : userManager.getProfiles(getCurrentUser())) {
                if (userInfo.id == userHandle.getIdentifier()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @SystemApi
    public void killProcessesWhenImperceptible(int[] pids, String reason) {
        try {
            getService().killProcessesWhenImperceptible(pids, reason);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isProcStateConsideredInteraction(int procState) {
        return procState <= 2 || procState == 3;
    }

    public static String procStateToString(int procState) {
        switch (procState) {
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

        public AppTask(IAppTask task) {
            this.mAppTaskImpl = task;
        }

        public void finishAndRemoveTask() {
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
                ActivityThread thread = ActivityThread.currentActivityThread();
                IApplicationThread appThread = thread.getApplicationThread();
                String packageName = ActivityThread.currentPackageName();
                this.mAppTaskImpl.moveToFront(appThread, packageName);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void startActivity(Context context, Intent intent, Bundle options) {
            ActivityThread thread = ActivityThread.currentActivityThread();
            thread.getInstrumentation()
                    .execStartActivityFromAppTask(
                            context,
                            thread.getApplicationThread(),
                            this.mAppTaskImpl,
                            intent,
                            options);
        }

        public void setExcludeFromRecents(boolean exclude) {
            try {
                this.mAppTaskImpl.setExcludeFromRecents(exclude);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semUpdateConfiguration(Configuration values) {
        try {
            getTaskService().updateConfiguration(values);
        } catch (RemoteException e) {
        }
    }

    public void semKeepKeyguardWaitingForActivityDrawn() {}

    public void semRegisterActivityControllerListener(SemActivityControllerListener listener) {
        synchronized (this.mActivityControllerListeners) {
            if (this.mActivityControllerListeners.contains(listener)) {
                Log.w(TAG, "ActivityControllerListener already registered");
                return;
            }
            if (this.mActivityController == null) {
                this.mActivityController = new ActivityController();
            }
            try {
                Log.i(
                        TAG,
                        "semRegisterActivityControllerListener, listener=0x"
                                + Integer.toHexString(listener.hashCode()));
                this.mActivityControllerListeners.add(listener);
                getTaskService().setActivityController(this.mActivityController, false);
            } catch (RemoteException e) {
            }
        }
    }

    public void semUnregisterActivityControllerListener(SemActivityControllerListener listener) {
        synchronized (this.mActivityControllerListeners) {
            if (!this.mActivityControllerListeners.contains(listener)) {
                Log.w(TAG, "ActivityControllerListener no registered");
                return;
            }
            try {
                Log.i(
                        TAG,
                        "semUnregisterActivityControllerListener, listener=0x"
                                + Integer.toHexString(listener.hashCode()));
                this.mActivityControllerListeners.remove(listener);
                if (this.mActivityControllerListeners.isEmpty()) {
                    getTaskService().setActivityController(null, false);
                }
            } catch (RemoteException e) {
            }
        }
    }

    private class ActivityController extends IActivityController.Stub {
        private ActivityController() {}

        @Override // android.app.IActivityController
        public boolean activityStarting(Intent intent, String pkg) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener listener = (SemActivityControllerListener) it.next();
                if (!listener.onActivityStarting(intent, pkg)) {
                    Log.i(
                            ActivityManager.TAG,
                            "Tried to abort onActivityStarting, pkg="
                                    + pkg
                                    + ", listener=0x"
                                    + Integer.toHexString(listener.hashCode()));
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.IActivityController
        public boolean activityResuming(String pkg) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener listener = (SemActivityControllerListener) it.next();
                if (!listener.onActivityResuming(pkg)) {
                    Log.i(
                            ActivityManager.TAG,
                            "Tried to abort onActivityResuming, pkg="
                                    + pkg
                                    + ", listener=0x"
                                    + Integer.toHexString(listener.hashCode()));
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.IActivityController
        public boolean appCrashed(
                String processName,
                int pid,
                String shortMsg,
                String longMsg,
                long timeMillis,
                String stackTrace) {
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener listener = (SemActivityControllerListener) it.next();
                if (!listener.onAppCrashed(
                        processName, pid, shortMsg, longMsg, timeMillis, stackTrace)) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.IActivityController
        public int appEarlyNotResponding(String processName, int pid, String annotation) {
            int result = 0;
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener listener = (SemActivityControllerListener) it.next();
                int ret = listener.onAppEarlyNotResponding(processName, pid, annotation);
                if (ret != 0 && (ret == 1 || result != 1)) {
                    result = ret;
                }
            }
            return result;
        }

        @Override // android.app.IActivityController
        public int appNotResponding(String processName, int pid, String processStats) {
            int result = 0;
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener listener = (SemActivityControllerListener) it.next();
                int ret = listener.onAppNotResponding(processName, pid, processStats);
                if (ret != 0 && (ret == 1 || result != 1)) {
                    result = ret;
                }
            }
            return result;
        }

        @Override // android.app.IActivityController
        public int systemNotResponding(String message) {
            int result = 0;
            Iterator it = ActivityManager.this.mActivityControllerListeners.iterator();
            while (it.hasNext()) {
                SemActivityControllerListener listener = (SemActivityControllerListener) it.next();
                int ret = listener.onSystemNotResponding(message);
                if (ret != 0 && (ret == 1 || result != 1)) {
                    result = ret;
                }
            }
            return result;
        }
    }

    public void semSetProcessImportant(IBinder token, int pid, boolean isForeground) {
        try {
            getService().setProcessImportant(token, pid, isForeground, "setProcessImportant()");
        } catch (RemoteException e) {
        }
    }

    public boolean semSwitchUser(int userid) {
        try {
            return getService().switchUser(userid);
        } catch (RemoteException e) {
            Log.e(TAG, "semSwitchUser failed", e);
            return false;
        }
    }

    public void semCloseSystemDialogs(String reason) {
        try {
            getService().closeSystemDialogs(reason);
        } catch (RemoteException e) {
        }
    }

    public void semRegisterProcessListener(SemProcessListener listener) {
        ProcessObserver existingProcessObserver = this.mProcessObserverMap.get(listener);
        if (existingProcessObserver != null) {
            Log.w(TAG, "ProcessListener already registered");
            return;
        }
        ProcessObserver processObserver = new ProcessObserver();
        try {
            getService().registerProcessObserver(processObserver);
            this.mProcessObserverMap.put(listener, processObserver);
            this.mProcessListeners.add(listener);
        } catch (RemoteException e) {
        }
    }

    public void semUnregisterProcessListener(SemProcessListener listener) {
        ProcessObserver existingProcessObserver = this.mProcessObserverMap.get(listener);
        if (existingProcessObserver == null) {
            Log.w(TAG, "ProcessListener no registered");
            return;
        }
        try {
            getService().unregisterProcessObserver(existingProcessObserver);
            this.mProcessObserverMap.remove(listener);
            this.mProcessListeners.remove(listener);
        } catch (RemoteException e) {
        }
    }

    public List<String> getBugreportWhitelistedPackages() {
        try {
            return getService().getBugreportWhitelistedPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void appNotResponding(String reason) {
        try {
            getService().appNotResponding(reason);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void addHomeVisibilityListener(
            Executor executor, final HomeVisibilityListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        try {
            listener.init(this.mContext, executor);
            getService().registerProcessObserver(listener.mObserver);
            executor.execute(
                    new Runnable() { // from class:
                        // android.app.ActivityManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            r0.onHomeVisibilityChanged(
                                    HomeVisibilityListener.this.mIsHomeActivityVisible);
                        }
                    });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void removeHomeVisibilityListener(HomeVisibilityListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            getService().unregisterProcessObserver(listener.mObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setThemeOverlayReady(int userId) {
        try {
            getService().setThemeOverlayReady(userId);
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

    public void holdLock(IBinder token, int durationMs) {
        try {
            getService().holdLock(token, durationMs);
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

    public void forceDelayBroadcastDelivery(String targetPackage, long delayedDurationMs) {
        try {
            getService().forceDelayBroadcastDelivery(targetPackage, delayedDurationMs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProcessFrozen(int pid) {
        try {
            return getService().isProcessFrozen(pid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void noteForegroundResourceUseBegin(int apiType, int uid, int pid)
            throws SecurityException {
        try {
            getService().logFgsApiBegin(apiType, uid, pid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void noteForegroundResourceUseEnd(int apiType, int uid, int pid)
            throws SecurityException {
        try {
            getService().logFgsApiEnd(apiType, uid, pid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBackgroundRestrictionExemptionReason(int uid) {
        try {
            return getService().getBackgroundRestrictionExemptionReason(uid);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void noteAppRestrictionEnabled(
            String packageName,
            int uid,
            int restrictionLevel,
            boolean enabled,
            int reason,
            String subReason,
            int source,
            long threshold) {
        try {
            getService()
                    .noteAppRestrictionEnabled(
                            packageName,
                            uid,
                            restrictionLevel,
                            enabled,
                            reason,
                            subReason,
                            source,
                            threshold);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySystemPropertiesChanged() {
        IBinder binder = getService().asBinder();
        if (binder != null) {
            Parcel data = Parcel.obtain();
            try {
                binder.transact(IBinder.SYSPROPS_TRANSACTION, data, null, 0);
                data.recycle();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static final class PendingIntentInfo implements Parcelable {
        public static final Parcelable.Creator<PendingIntentInfo> CREATOR =
                new Parcelable.Creator<PendingIntentInfo>() { // from class:
                    // android.app.ActivityManager.PendingIntentInfo.1
                    @Override // android.os.Parcelable.Creator
                    public PendingIntentInfo createFromParcel(Parcel in) {
                        return new PendingIntentInfo(
                                in.readString(), in.readInt(), in.readBoolean(), in.readInt());
                    }

                    @Override // android.os.Parcelable.Creator
                    public PendingIntentInfo[] newArray(int size) {
                        return new PendingIntentInfo[size];
                    }
                };
        private final String mCreatorPackage;
        private final int mCreatorUid;
        private final boolean mImmutable;
        private final int mIntentSenderType;

        public PendingIntentInfo(
                String creatorPackage, int creatorUid, boolean immutable, int intentSenderType) {
            this.mCreatorPackage = creatorPackage;
            this.mCreatorUid = creatorUid;
            this.mImmutable = immutable;
            this.mIntentSenderType = intentSenderType;
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
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
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
            String resolvedType = intent.resolveTypeIfNeeded(this.mContext.getContentResolver());
            return getService()
                    .queryRegisteredReceiverPackages(
                            intent, resolvedType, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(
            int type, String packageName, int uid) {
        try {
            return getService().getRestrictionInfo(type, packageName, uid);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean canRestrict(int type, String packageName, int uid) {
        try {
            return getService().canRestrict(type, packageName, uid);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restrict(int type, int state, boolean byUser, String packageName, int uid) {
        try {
            return getService().restrict(type, state, byUser, packageName, uid);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int type) {
        try {
            return getService().getRestrictableList(type);
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

    public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int type) {
        try {
            return getService().getRestrictedList(type);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRestrictionInfo(
            SemAppRestrictionManager.RestrictionInfo info,
            List<SemAppRestrictionManager.AppRestrictionInfo> list) {
        try {
            return getService().updateRestrictionInfo(info, list);
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

    public List<PackageInfo> getInstalledPackageListFromMARs(int flags, int userId) {
        int callingUid = Binder.getCallingUid();
        if (callingUid >= 10000) {
            throw new SecurityException(
                    "Caller " + callingUid + " is not allowed to get pkgList from MARs");
        }
        try {
            ParceledListSlice<PackageInfo> parceledList =
                    getService().getInstalledPackageListFromMARs(flags, userId);
            return parceledList == null ? Collections.emptyList() : parceledList.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceStopPackageByAdmin(String packageName, int userId) {
        try {
            getService().forceStopPackageByAdmin(packageName, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getPackageFromAppProcesses(int pid) {
        try {
            return getService().getPackageFromAppProcesses(pid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getAppLockedPackageList() {
        try {
            return getTaskService().getAppLockedPackageList();
        } catch (RemoteException e) {
            return null;
        }
    }

    public void setAppLockedUnLockPackage(String packageName) {
        try {
            getTaskService().setAppLockedUnLockPackage(packageName);
        } catch (RemoteException e) {
        }
    }

    public boolean isAppLockedPackage(String packageName) {
        try {
            return getTaskService().isAppLockedPackage(packageName);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void clearAppLockedUnLockedApp() {
        try {
            getTaskService().clearAppLockedUnLockedApp();
        } catch (RemoteException e) {
        }
    }

    public String getAppLockedLockType() {
        try {
            return getTaskService().getAppLockedLockType();
        } catch (RemoteException e) {
            return null;
        }
    }

    public String getAppLockedCheckAction() {
        try {
            return getTaskService().getAppLockedCheckAction();
        } catch (RemoteException e) {
            return null;
        }
    }

    public void setAppLockedVerifying(String pkgName, boolean verifying) {
        try {
            getTaskService().setAppLockedVerifying(pkgName, verifying);
        } catch (RemoteException e) {
        }
    }

    public boolean isAppLockedVerifying(String pkgName) {
        try {
            return getTaskService().isAppLockedVerifying(pkgName);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setApplockLockedAppsPackage(String packages) {
        try {
            getTaskService().setApplockLockedAppsPackage(packages);
        } catch (RemoteException e) {
        }
    }

    public void setApplockLockedAppsClass(String classes) {
        try {
            getTaskService().setApplockLockedAppsClass(classes);
        } catch (RemoteException e) {
        }
    }

    public void setApplockType(int type) {
        try {
            getTaskService().setApplockType(type);
        } catch (RemoteException e) {
        }
    }

    public void setApplockEnabled(boolean enable) {
        try {
            getTaskService().setApplockEnabled(enable);
        } catch (RemoteException e) {
        }
    }

    public void setSsecureHiddenAppsPackages(String packages) {
        try {
            getTaskService().setSsecureHiddenAppsPackages(packages);
        } catch (RemoteException e) {
        }
    }

    public String getApplockLockedAppsPackage() {
        try {
            return getTaskService().getApplockLockedAppsPackage();
        } catch (RemoteException e) {
            return null;
        }
    }

    public String getApplockLockedAppsClass() {
        try {
            return getTaskService().getApplockLockedAppsClass();
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getApplockType() {
        try {
            return getTaskService().getApplockType();
        } catch (RemoteException e) {
            return 0;
        }
    }

    public boolean isApplockEnabled() {
        try {
            return getTaskService().isApplockEnabled();
        } catch (RemoteException e) {
            return false;
        }
    }

    public String getSsecureHiddenAppsPackages() {
        try {
            return getTaskService().getSsecureHiddenAppsPackages();
        } catch (RemoteException e) {
            return null;
        }
    }

    public Bitmap getResumedTaskThumbnail(int taskId) throws SecurityException {
        try {
            return getTaskService().getResumedTaskThumbnail(taskId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
