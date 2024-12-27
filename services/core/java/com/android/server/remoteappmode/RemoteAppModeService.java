package com.android.server.remoteappmode;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.view.Surface;
import android.view.WindowManagerGlobal;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.remoteappmode.RemoteAppModeNotifier;
import com.android.server.remoteappmode.RemoteAppModeNotifier.RemoteAppModeListenerInfo;
import com.android.server.remoteappmode.RotationChangeNotifier;
import com.android.server.remoteappmode.RotationChangeNotifier.RotationChangedListenerInfo;
import com.android.server.remoteappmode.SecureAppNotifier;
import com.android.server.remoteappmode.SecureAppNotifier.SecureAppChangedListenerInfo;
import com.android.server.remoteappmode.StartActivityInterceptNotifier;
import com.android.server.remoteappmode.StartActivityInterceptNotifier.StartActivityInterceptListenerInfo;
import com.android.server.remoteappmode.TaskChangeNotifier;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.RemoteAppController;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.remoteappmode.IRemoteAppMode;
import com.samsung.android.remoteappmode.IRemoteAppModeListener;
import com.samsung.android.remoteappmode.IRotationChangeListener;
import com.samsung.android.remoteappmode.ISecureAppChangedListener;
import com.samsung.android.remoteappmode.IStartActivityInterceptListener;
import com.samsung.android.remoteappmode.ITaskChangeListener;
import com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker;
import com.samsung.android.remoteappmode.RemoteAppModeManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class RemoteAppModeService extends IRemoteAppMode.Stub {
    public static final boolean DEBUG;
    public final AnrCollector mAnrCollector;
    public final Context mContext;
    public DisplayManager mDisplayManager;
    public final Handler mHandler;
    public final InterceptedActivityRepo mInterceptedActivityRepo;
    public LocalService mLocalService;
    public final ProximityManager mProximityManager;
    public final RFCommServiceLauncher mRFCommServiceLauncher;
    public final RemoteAppModeNotifier mRemoteAppModeNotifier;
    public final ContentResolver mResolver;
    public final RotationChangeNotifier mRotationChangeNotifier;
    public final SecureAppNotifier mSecureAppNotifier;
    public final StartActivityInterceptNotifier mStartActivityInterceptNotifier;
    public final TaskChangeNotifier mTaskChangeNotifier;
    public ContentObserver mUserSetupCompleteObserver;
    public final Object mLock = new Object();
    public boolean mIsStartActivityListenerRegistered = false;
    public boolean mIsBootComplete = false;
    public boolean mIsRemoteAppModeEnabled = false;
    public int mCurrentUserId = -10000;
    public int mLTWProtocolVersion = 3;
    public final HashMap mVirtualDisplayMap = new HashMap();
    public final HashMap mTransferTaskMap = new HashMap();
    public final AnonymousClass1 mRemoteAppControllerCallbacks = new AnonymousClass1();

    /* renamed from: com.android.server.remoteappmode.RemoteAppModeService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public static String interceptReasonToString(int i) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? String.valueOf(i) : "INTERCEPT_REASON_APP_REQUESTED" : "TASK_IS_MOVING_TO_REMOTE_APP_DISPLAY" : "TASK_IS_MOVING_TO_DEFAULT_DISPLAY" : "OTHER_TASK_EXISTS_IN_REMOTE_APP_DISPLAY" : "UNDEFINED";
        }

        public final boolean onStartActivityInterceptedLocked(Intent intent, ActivityOptions activityOptions, ActivityInfo activityInfo, int i, boolean z, ActivityManager.RunningTaskInfo runningTaskInfo, RemoteAppController.CallerInfo callerInfo, int i2, int i3) {
            ArrayList arrayList;
            Bundle bundle;
            int i4;
            int i5;
            RemoteAppModeService remoteAppModeService = RemoteAppModeService.this;
            if (remoteAppModeService.mStartActivityInterceptNotifier == null) {
                return false;
            }
            synchronized (remoteAppModeService.mLock) {
                if (runningTaskInfo != null) {
                    try {
                        if (RemoteAppModeService.this.mTransferTaskMap.containsKey(Integer.valueOf(runningTaskInfo.taskId))) {
                            RemoteAppModeService.this.mTransferTaskMap.remove(Integer.valueOf(runningTaskInfo.taskId));
                            return false;
                        }
                    } finally {
                    }
                }
                intent.setRemoteAppLaunch(false);
                RemoteAppModeService remoteAppModeService2 = RemoteAppModeService.this;
                if (callerInfo != null || i2 != remoteAppModeService2.mCurrentUserId) {
                    InterceptedActivityRepo interceptedActivityRepo = remoteAppModeService2.mInterceptedActivityRepo;
                    InterceptedActivityInfo interceptedActivityInfo = new InterceptedActivityInfo();
                    interceptedActivityInfo.callerInfo = callerInfo;
                    interceptedActivityInfo.userId = i2;
                    interceptedActivityRepo.getClass();
                    int hashCode = intent.toString().hashCode();
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(hashCode, "mInterceptedActivityInfoMap.put - hashcode : ", ", intent : ");
                    m.append(intent.toString());
                    Log.d("[RAMS] InterceptedActivityRepo", m.toString());
                    synchronized (interceptedActivityRepo.mLock) {
                        interceptedActivityRepo.mInterceptedActivityInfoMap.put(Integer.valueOf(hashCode), interceptedActivityInfo);
                        Log.d("[RAMS] InterceptedActivityRepo", "mInterceptedActivityInfoMap size = " + interceptedActivityRepo.mInterceptedActivityInfoMap.size());
                    }
                    Log.i("[RAMS]RemoteAppModeService", "saveCallerInfo - mInterceptedActivityRepo.put cInfo:" + callerInfo + ", intent:" + intent);
                }
                StartActivityInterceptNotifier startActivityInterceptNotifier = RemoteAppModeService.this.mStartActivityInterceptNotifier;
                synchronized (startActivityInterceptNotifier.mStartActivityInterceptListeners) {
                    arrayList = new ArrayList(((ArrayMap) startActivityInterceptNotifier.mStartActivityInterceptListeners).values());
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    StartActivityInterceptNotifier.StartActivityInterceptListenerInfo startActivityInterceptListenerInfo = (StartActivityInterceptNotifier.StartActivityInterceptListenerInfo) it.next();
                    if (activityOptions != null) {
                        try {
                            bundle = activityOptions.toBundle();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        bundle = null;
                    }
                    Bundle bundle2 = bundle;
                    if (runningTaskInfo != null) {
                        i4 = runningTaskInfo.taskId;
                        i5 = runningTaskInfo.displayId;
                    } else {
                        i4 = -1;
                        i5 = -1;
                    }
                    startActivityInterceptListenerInfo.listener.onStartActivityIntercepted(intent, bundle2, activityInfo, i, z, i4, i5, i3);
                }
                return true;
            }
        }
    }

    public final class Lifecycle extends SystemService {
        public RemoteAppModeService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = null;
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            RemoteAppModeService remoteAppModeService = this.mService;
            if (remoteAppModeService != null) {
                if (i == 500) {
                    remoteAppModeService.mDisplayManager = (DisplayManager) remoteAppModeService.mContext.getSystemService("display");
                } else {
                    boolean z = RemoteAppModeService.DEBUG;
                    remoteAppModeService.getClass();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.remoteappmode.RemoteAppModeService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? remoteAppModeService = new RemoteAppModeService(getContext());
            this.mService = remoteAppModeService;
            publishBinderService("remoteappmode", remoteAppModeService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            RemoteAppModeService remoteAppModeService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = RemoteAppModeService.DEBUG;
            remoteAppModeService.getClass();
            if (RemoteAppModeService.DEBUG) {
                Log.d("[RAMS]RemoteAppModeService", "onStartUser(), userId=" + userIdentifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            RemoteAppModeService remoteAppModeService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = RemoteAppModeService.DEBUG;
            remoteAppModeService.getClass();
            if (RemoteAppModeService.DEBUG) {
                Log.d("[RAMS]RemoteAppModeService", "onCleanupUser(), userId=" + userIdentifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            RemoteAppModeService remoteAppModeService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = RemoteAppModeService.DEBUG;
            remoteAppModeService.getClass();
            if (RemoteAppModeService.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(userIdentifier, "onStopUser(), userId=", ", CurrentUser=");
                m.append(ActivityManager.getCurrentUser());
                Log.d("[RAMS]RemoteAppModeService", m.toString());
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            RemoteAppModeService remoteAppModeService = this.mService;
            int userIdentifier = targetUser2.getUserIdentifier();
            if (RemoteAppModeService.DEBUG) {
                remoteAppModeService.getClass();
                Log.d("[RAMS]RemoteAppModeService", "onSwitchUser(), userId=" + userIdentifier);
            }
            remoteAppModeService.onUserChanged(userIdentifier);
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            RemoteAppModeService remoteAppModeService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            if (RemoteAppModeService.DEBUG) {
                remoteAppModeService.getClass();
                Log.d("[RAMS]RemoteAppModeService", "onUnlockUser(), userId=" + userIdentifier + ", CurrentUser=" + ActivityManager.getCurrentUser());
            }
            remoteAppModeService.mIsBootComplete = true;
            if (userIdentifier == ActivityManager.getCurrentUser()) {
                remoteAppModeService.onUserChanged(userIdentifier);
            }
            ProximityManager proximityManager = remoteAppModeService.mProximityManager;
            if (proximityManager.isValid()) {
                proximityManager.ensureInitSharedPreference();
                proximityManager.mPackageName = proximityManager.sharedPreferences.getString("target_package_name", null);
                proximityManager.registerBroadcastReceiver();
            }
        }
    }

    public final class LocalService extends RemoteAppModeManagerInternal {
        public LocalService() {
        }

        public final void onConfigurationChanged(Configuration configuration, int i) {
            RemoteAppModeService.this.getClass();
        }

        public final void onSecuredAppLaunched(int i, String str) {
            ArrayList arrayList;
            RemoteAppModeService remoteAppModeService = RemoteAppModeService.this;
            if (RemoteAppModeService.DEBUG) {
                remoteAppModeService.getClass();
                Log.i("[RAMS]RemoteAppModeService", "onSecuredAppLaunched, taskId=" + i + ", packageName= " + str);
            }
            SecureAppNotifier secureAppNotifier = remoteAppModeService.mSecureAppNotifier;
            synchronized (secureAppNotifier.mSecureAppChangedListeners) {
                arrayList = new ArrayList(((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).values());
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((SecureAppNotifier.SecureAppChangedListenerInfo) it.next()).listener.onSecuredAppLaunched(i, str);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public final void onSecuredAppRemoved(int i, String str) {
            ArrayList arrayList;
            RemoteAppModeService remoteAppModeService = RemoteAppModeService.this;
            if (RemoteAppModeService.DEBUG) {
                remoteAppModeService.getClass();
                Log.i("[RAMS]RemoteAppModeService", "onSecureAppRemoved, taskId=" + i + ", packageName= " + str);
            }
            SecureAppNotifier secureAppNotifier = remoteAppModeService.mSecureAppNotifier;
            synchronized (secureAppNotifier.mSecureAppChangedListeners) {
                arrayList = new ArrayList(((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).values());
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((SecureAppNotifier.SecureAppChangedListenerInfo) it.next()).listener.onSecuredAppRemoved(i, str);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = RemoteAppModeService.DEBUG;
            if (z) {
                Log.d("[RAMS]RemoteAppModeService", "onReceive(), action=" + action);
            }
            if (!"android.intent.action.PHONE_STATE".equals(action) && "android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                if (z) {
                    Log.d("[RAMS]RemoteAppModeService", "Shutdown received with UserId: " + getSendingUserId());
                }
                if (getSendingUserId() == -1) {
                    RemoteAppModeService.this.mIsBootComplete = false;
                }
            }
        }
    }

    public final class VirtualDisplayInfo implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final VirtualDisplay mVirtualDisplay;

        public VirtualDisplayInfo(VirtualDisplay virtualDisplay, IBinder iBinder) {
            this.mVirtualDisplay = virtualDisplay;
            this.mBinder = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = RemoteAppModeService.DEBUG;
            Log.d("[RAMS]RemoteAppModeService", "VirtualDisplayInfo - binderDied");
            this.mBinder.unlinkToDeath(this, 0);
            this.mVirtualDisplay.release();
            synchronized (RemoteAppModeService.this.mLock) {
                RemoteAppModeService.this.mVirtualDisplayMap.remove(Integer.valueOf(this.mVirtualDisplay.getDisplay().getDisplayId()));
                RemoteAppModeService.this.checkRemoteAppModeEnabled();
            }
        }
    }

    static {
        DEBUG = Debug.semIsProductDev() || android.util.Log.isLoggable("RAMS", 3);
    }

    public RemoteAppModeService(Context context) {
        Handler handler = new Handler(Watchdog$$ExternalSyntheticOutline0.m(-4, "remoteappmode", false).getLooper());
        this.mHandler = handler;
        this.mLocalService = null;
        this.mContext = context;
        context.setTheme(R.style.Theme.DeviceDefault.Light);
        this.mResolver = context.getContentResolver();
        this.mRemoteAppModeNotifier = new RemoteAppModeNotifier();
        this.mTaskChangeNotifier = new TaskChangeNotifier(context);
        this.mSecureAppNotifier = new SecureAppNotifier();
        this.mRotationChangeNotifier = new RotationChangeNotifier();
        this.mStartActivityInterceptNotifier = new StartActivityInterceptNotifier();
        this.mInterceptedActivityRepo = new InterceptedActivityRepo();
        this.mRFCommServiceLauncher = new RFCommServiceLauncher(context);
        this.mAnrCollector = new AnrCollector(context);
        ProximityManager proximityManager = new ProximityManager();
        proximityManager.mContext = context;
        proximityManager.mHandler = handler;
        proximityManager.proximityReceiver = null;
        this.mProximityManager = proximityManager;
        context.registerReceiverAsUser(new Receiver(), UserHandle.ALL, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.PHONE_STATE", "android.intent.action.ACTION_SHUTDOWN"), null, handler);
    }

    public static TaskInfo getTaskInfo(int i) {
        List<ActivityManager.RunningTaskInfo> tasks = ActivityTaskManager.getInstance().getTasks(Integer.MAX_VALUE, false, true);
        if (tasks == null) {
            return null;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
            if (runningTaskInfo.taskId == i) {
                return runningTaskInfo;
            }
        }
        return null;
    }

    public final void checkPermissionAndAAoWFeature(String str) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", str);
        if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_LTW_REMOTE_APP")) {
            throw new UnsupportedOperationException("LTW_REMOTE_APP feature is not enabled in this device.");
        }
    }

    public final void checkRemoteAppModeEnabled() {
        boolean z;
        ArrayList arrayList;
        if (!this.mVirtualDisplayMap.isEmpty()) {
            Iterator it = this.mVirtualDisplayMap.values().iterator();
            while (it.hasNext()) {
                if (((VirtualDisplayInfo) it.next()).mVirtualDisplay.getDisplay().isValid()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (this.mIsRemoteAppModeEnabled != z) {
            if (DEBUG) {
                Log.i("[RAMS]RemoteAppModeService", "checkRemoteAppModeEnabled, isEnabled=" + z);
            }
            this.mIsRemoteAppModeEnabled = z;
            RemoteAppModeNotifier remoteAppModeNotifier = this.mRemoteAppModeNotifier;
            synchronized (remoteAppModeNotifier.mRemoteAppModeListeners) {
                arrayList = new ArrayList(((ArrayMap) remoteAppModeNotifier.mRemoteAppModeListeners).values());
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                try {
                    ((RemoteAppModeNotifier.RemoteAppModeListenerInfo) it2.next()).listener.onRemoteAppModeStateChanged(z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void clearAll() {
        checkPermissionAndAAoWFeature("Permission required to clear all resources");
        synchronized (this.mLock) {
            try {
                Iterator it = this.mVirtualDisplayMap.values().iterator();
                while (it.hasNext()) {
                    ((VirtualDisplayInfo) it.next()).mVirtualDisplay.release();
                }
                this.mVirtualDisplayMap.clear();
                checkRemoteAppModeEnabled();
            } finally {
            }
        }
        SecureAppNotifier secureAppNotifier = this.mSecureAppNotifier;
        if (secureAppNotifier != null) {
            synchronized (secureAppNotifier.mSecureAppChangedListeners) {
                for (IBinder iBinder : ((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).keySet()) {
                    try {
                        SecureAppNotifier.SecureAppChangedListenerInfo secureAppChangedListenerInfo = (SecureAppNotifier.SecureAppChangedListenerInfo) ((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).get(iBinder);
                        Objects.requireNonNull(secureAppChangedListenerInfo);
                        iBinder.unlinkToDeath(secureAppChangedListenerInfo, 0);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                ((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).clear();
            }
        }
        TaskChangeNotifier taskChangeNotifier = this.mTaskChangeNotifier;
        if (taskChangeNotifier != null) {
            Log.i("TaskChangeNotifier", "releaseAllListeners");
            synchronized (taskChangeNotifier.mTaskChangeListeners) {
                for (IBinder iBinder2 : ((ArrayMap) taskChangeNotifier.mTaskChangeListeners).keySet()) {
                    try {
                        TaskChangeNotifier.TaskChangeListenerInfo taskChangeListenerInfo = (TaskChangeNotifier.TaskChangeListenerInfo) ((ArrayMap) taskChangeNotifier.mTaskChangeListeners).get(iBinder2);
                        Objects.requireNonNull(taskChangeListenerInfo);
                        iBinder2.unlinkToDeath(taskChangeListenerInfo, 0);
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    }
                }
                ((ArrayMap) taskChangeNotifier.mTaskChangeListeners).clear();
            }
            synchronized (taskChangeNotifier.lockObject) {
                taskChangeNotifier.unregisterWatcherInternal();
                taskChangeNotifier.deinitTaskWatcherThread();
            }
        }
    }

    public final int createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, IVirtualDisplayAliveChecker iVirtualDisplayAliveChecker) {
        checkPermissionAndAAoWFeature("createVirtualDisplay");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            VirtualDisplay createVirtualDisplay = this.mDisplayManager.createVirtualDisplay(str, i, i2, i3, surface, 525569);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            IBinder asBinder = iVirtualDisplayAliveChecker.asBinder();
            Binder.getCallingPid();
            Binder.getCallingUid();
            VirtualDisplayInfo virtualDisplayInfo = new VirtualDisplayInfo(createVirtualDisplay, asBinder);
            try {
                iVirtualDisplayAliveChecker.asBinder().linkToDeath(virtualDisplayInfo, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            synchronized (this.mLock) {
                if (createVirtualDisplay != null) {
                    try {
                        this.mVirtualDisplayMap.put(Integer.valueOf(createVirtualDisplay.getDisplay().getDisplayId()), virtualDisplayInfo);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                checkRemoteAppModeEnabled();
            }
            if (createVirtualDisplay != null) {
                return createVirtualDisplay.getDisplay().getDisplayId();
            }
            return -1;
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final void disableSendingUserPresentIntent() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "disableSendingUserPresentIntent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mProximityManager.disableSendingUserPresentIntent();
            } catch (SecurityException e) {
                Log.e("[RAMS]RemoteAppModeService", " SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "[RAMS]RemoteAppModeService", printWriter)) {
            if (strArr == null || strArr.length == 0 || "-a".equals(strArr[0])) {
                PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                indentingPrintWriter.println("RemoteAppModeService (dumpsys remoteappmode):");
                indentingPrintWriter.println(Log.buildLogString('V', "[RAMS]StateStart", "=========================================================================="));
                Log.sSavedStates.dump(indentingPrintWriter);
                indentingPrintWriter.println(Log.buildLogString('V', "[RAMS]StateEnd", "=========================================================================="));
                indentingPrintWriter.println(Log.buildLogString('V', "[RAMS]SavedLogsStart", "=========================================================================="));
                Log.sSavedLogs.dump(indentingPrintWriter);
                indentingPrintWriter.println(Log.buildLogString('V', "[RAMS]SavedLogsEnd", "=========================================================================="));
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.printPair("mCurrentUserId", Integer.valueOf(this.mCurrentUserId));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("Configuration", this.mContext.getResources().getConfiguration());
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("DISPLAY_SIZE_FORCED", Settings.Global.getString(this.mResolver, "display_size_forced"));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("DISPLAY_DENSITY_FORCED", Settings.Secure.getStringForUser(this.mResolver, "display_density_forced", 0));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("SCREEN_OFF_TIMEOUT", Settings.System.getStringForUser(this.mResolver, "screen_off_timeout", this.mCurrentUserId));
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("SHOW_IME_WITH_HARD_KEYBOARD", Settings.Secure.getStringForUser(this.mResolver, "show_ime_with_hard_keyboard", this.mCurrentUserId));
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public final void enableSendingUserPresentIntent(String str) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "enableSendingUserPresentIntent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mProximityManager.enableSendingUserPresentIntent(str);
            } catch (SecurityException e) {
                Log.e("[RAMS]RemoteAppModeService", " SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void forceStopPackage(String str) {
        checkPermissionAndAAoWFeature("Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ActivityManager.getService().forceStopPackage(str, this.mCurrentUserId);
            } catch (RemoteException e) {
                Log.e("[RAMS]RemoteAppModeService", " removeTask: RemoteException " + e.getMessage());
            } catch (SecurityException e2) {
                Log.e("[RAMS]RemoteAppModeService", " removeTask: SecurityException " + e2.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAnrCollector.getLastAnr(str, parcelFileDescriptor);
            } catch (SecurityException e) {
                Log.e("[RAMS]RemoteAppModeService", " removeTask: SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getProtocolVersion() {
        return 9;
    }

    public final long getSendingUserPresentExpiredTime() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "setExpiredTime");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mProximityManager.getExpiredTime();
            } catch (SecurityException e) {
                Log.e("[RAMS]RemoteAppModeService", " SecurityException " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0L;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void initializeStates() {
        if (isUserSetupComplete()) {
            return;
        }
        if (this.mUserSetupCompleteObserver == null) {
            this.mUserSetupCompleteObserver = new ContentObserver() { // from class: com.android.server.remoteappmode.RemoteAppModeService.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    RemoteAppModeService.this.initializeStates();
                    RemoteAppModeService.this.mResolver.unregisterContentObserver(this);
                    RemoteAppModeService.this.mUserSetupCompleteObserver = null;
                }
            };
        }
        this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupCompleteObserver, this.mCurrentUserId);
    }

    public final boolean isAllowed() {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "isAllowed");
        if (!this.mIsBootComplete || !isUserSetupComplete() || FactoryTest.isFactoryBinary() || this.mCurrentUserId == -10000) {
            Log.i("[RAMS]RemoteAppModeService", "isSystemReady(), mIsBootComplete=" + this.mIsBootComplete + ", isFactoryBinary=" + FactoryTest.isFactoryBinary() + ", mCurrentUserId=" + this.mCurrentUserId);
            z = false;
        } else {
            z = true;
        }
        Log.d("[RAMS]RemoteAppModeService", "isAllowed = " + z);
        return z;
    }

    public final boolean isSendingUserPresentEnabled() {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "isSendingUserPresentEnabled");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ProximityManager proximityManager = this.mProximityManager;
                synchronized (proximityManager) {
                    z = proximityManager.proximityReceiver != null;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z;
            } catch (SecurityException e) {
                Log.e("[RAMS]RemoteAppModeService", " SecurityException " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isUserSetupComplete() {
        boolean z = Settings.Secure.getIntForUser(this.mResolver, "user_setup_complete", 0, this.mCurrentUserId) != 0;
        if (!z && DEBUG) {
            Log.d("[RAMS]RemoteAppModeService", "isUserSetupComplete()=false");
        }
        return z;
    }

    public final void launchApplication(int i, String str, Intent intent, Bundle bundle) {
        InterceptedActivityInfo interceptedActivityInfo;
        checkPermissionAndAAoWFeature("launchApplication");
        RemoteAppController.CallerInfo callerInfo = null;
        ActivityOptions activityOptions = bundle != null ? new ActivityOptions(bundle) : null;
        Log.i("[RAMS]RemoteAppModeService", "launchApplication  - intent: " + intent);
        InterceptedActivityRepo interceptedActivityRepo = this.mInterceptedActivityRepo;
        interceptedActivityRepo.getClass();
        int hashCode = intent.toString().hashCode();
        synchronized (interceptedActivityRepo.mLock) {
            try {
                interceptedActivityInfo = interceptedActivityRepo.mInterceptedActivityInfoMap.containsKey(Integer.valueOf(hashCode)) ? (InterceptedActivityInfo) interceptedActivityRepo.mInterceptedActivityInfoMap.get(Integer.valueOf(hashCode)) : null;
            } finally {
            }
        }
        Log.i("[RAMS]RemoteAppModeService", "launchApplication  - interceptedActivityInfo: " + interceptedActivityInfo);
        int i2 = this.mCurrentUserId;
        if (interceptedActivityInfo != null) {
            callerInfo = interceptedActivityInfo.callerInfo;
            i2 = interceptedActivityInfo.userId;
            InterceptedActivityRepo interceptedActivityRepo2 = this.mInterceptedActivityRepo;
            interceptedActivityRepo2.getClass();
            int hashCode2 = intent.toString().hashCode();
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(hashCode2, "mInterceptedActivityInfoMap.remove - hashcode : ", ", intent : ");
            m.append(intent.toString());
            Log.d("[RAMS] InterceptedActivityRepo", m.toString());
            synchronized (interceptedActivityRepo2.mLock) {
                interceptedActivityRepo2.mInterceptedActivityInfoMap.remove(Integer.valueOf(hashCode2));
                Log.d("[RAMS] InterceptedActivityRepo", "mInterceptedActivityInfoMap size = " + interceptedActivityRepo2.mInterceptedActivityInfoMap.size());
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (activityOptions == null) {
                try {
                    activityOptions = ActivityOptions.makeBasic();
                } catch (Exception e) {
                    Log.d("[RAMS]RemoteAppModeService", "exception  = " + e.getMessage());
                    e.printStackTrace();
                }
            }
            activityOptions.setLaunchDisplayId(i);
            if (interceptedActivityInfo == null) {
                startActivityAsUser(intent, activityOptions.toBundle(), i2);
            } else if (callerInfo != null) {
                ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).startRemoteActivityAsCaller(intent, callerInfo, i2, activityOptions.toBundle());
            } else {
                startActivityAsUser(intent, activityOptions.toBundle(), i2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void moveDisplayToTop(final int i) {
        checkPermissionAndAAoWFeature("moveDisplayToTop");
        synchronized (this.mLock) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.remoteappmode.RemoteAppModeService.3
                /* JADX WARN: Code restructure failed: missing block: B:14:0x004c, code lost:
                
                    if (r2.getDefaultDisplay().getDisplayId() == r2) goto L14;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        r5 = this;
                        long r0 = android.os.Binder.clearCallingIdentity()
                        com.android.server.remoteappmode.RemoteAppModeService r2 = com.android.server.remoteappmode.RemoteAppModeService.this     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        android.content.Context r2 = r2.mContext     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.lang.String r3 = "window"
                        java.lang.Object r2 = r2.getSystemService(r3)     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        android.view.WindowManager r2 = (android.view.WindowManager) r2     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        com.android.server.remoteappmode.RemoteAppModeService r3 = com.android.server.remoteappmode.RemoteAppModeService.this     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.util.HashMap r3 = r3.mVirtualDisplayMap     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        int r4 = r2     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        boolean r3 = r3.containsKey(r4)     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        if (r3 == 0) goto L42
                        com.android.server.remoteappmode.RemoteAppModeService r3 = com.android.server.remoteappmode.RemoteAppModeService.this     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.util.HashMap r3 = r3.mVirtualDisplayMap     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        int r4 = r2     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.lang.Object r3 = r3.get(r4)     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        com.android.server.remoteappmode.RemoteAppModeService$VirtualDisplayInfo r3 = (com.android.server.remoteappmode.RemoteAppModeService.VirtualDisplayInfo) r3     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        android.hardware.display.VirtualDisplay r3 = r3.mVirtualDisplay     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        android.view.Display r3 = r3.getDisplay()     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        boolean r3 = r3.isValid()     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        if (r3 != 0) goto L4e
                        goto L42
                    L3e:
                        r5 = move-exception
                        goto L62
                    L40:
                        r5 = move-exception
                        goto L5d
                    L42:
                        android.view.Display r2 = r2.getDefaultDisplay()     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        int r2 = r2.getDisplayId()     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        int r3 = r2     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        if (r2 != r3) goto L59
                    L4e:
                        android.view.IWindowManager r2 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        int r5 = r2     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                        java.lang.String r3 = "RemoteAppModeService"
                        r2.moveDisplayToTop(r5, r3)     // Catch: java.lang.Throwable -> L3e android.os.RemoteException -> L40
                    L59:
                        android.os.Binder.restoreCallingIdentity(r0)
                        goto L61
                    L5d:
                        r5.printStackTrace()     // Catch: java.lang.Throwable -> L3e
                        goto L59
                    L61:
                        return
                    L62:
                        android.os.Binder.restoreCallingIdentity(r0)
                        throw r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.remoteappmode.RemoteAppModeService.AnonymousClass3.run():void");
                }
            });
        }
    }

    public final void onUserChanged(int i) {
        if (i == this.mCurrentUserId) {
            return;
        }
        boolean z = DEBUG;
        if (z) {
            Log.d("[RAMS]RemoteAppModeService", "onUserChanged(), userId=" + i);
        }
        if (z) {
            Log.d("[RAMS]RemoteAppModeService", "setCurrentUserId(), userId=" + i);
        }
        this.mCurrentUserId = i;
        initializeStates();
    }

    public final boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "registerRemoteAppModeListener");
        RemoteAppModeNotifier remoteAppModeNotifier = this.mRemoteAppModeNotifier;
        synchronized (remoteAppModeNotifier.mRemoteAppModeListeners) {
            try {
                IBinder asBinder = iRemoteAppModeListener.asBinder();
                try {
                    if (str.length() > 100) {
                        str = str.substring(0, 100);
                    }
                    RemoteAppModeNotifier.RemoteAppModeListenerInfo remoteAppModeListenerInfo = remoteAppModeNotifier.new RemoteAppModeListenerInfo(iRemoteAppModeListener, str, Binder.getCallingPid(), Binder.getCallingUid());
                    asBinder.linkToDeath(remoteAppModeListenerInfo, 0);
                    ((ArrayMap) remoteAppModeNotifier.mRemoteAppModeListeners).put(asBinder, remoteAppModeListenerInfo);
                } catch (RemoteException unused) {
                    return false;
                }
            } finally {
            }
        }
        return true;
    }

    public final boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) {
        checkPermissionAndAAoWFeature("registerRotationChangeListener");
        RotationChangeNotifier rotationChangeNotifier = this.mRotationChangeNotifier;
        synchronized (rotationChangeNotifier.mRotationChangedListeners) {
            try {
                IBinder asBinder = iRotationChangeListener.asBinder();
                try {
                    if (str.length() > 100) {
                        str = str.substring(0, 100);
                    }
                    RotationChangeNotifier.RotationChangedListenerInfo rotationChangedListenerInfo = rotationChangeNotifier.new RotationChangedListenerInfo(iRotationChangeListener, str, Binder.getCallingPid(), Binder.getCallingUid());
                    rotationChangedListenerInfo.registerRotationWatcher(i);
                    asBinder.linkToDeath(rotationChangedListenerInfo, 0);
                    ((ArrayMap) rotationChangeNotifier.mRotationChangedListeners).put(asBinder, rotationChangedListenerInfo);
                } catch (RemoteException unused) {
                    return false;
                }
            } finally {
            }
        }
        return true;
    }

    public final boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) {
        checkPermissionAndAAoWFeature("registerSecureAppChangedListener");
        if (this.mLocalService == null) {
            LocalService localService = new LocalService();
            this.mLocalService = localService;
            LocalServices.addService(RemoteAppModeManagerInternal.class, localService);
        }
        SecureAppNotifier secureAppNotifier = this.mSecureAppNotifier;
        synchronized (secureAppNotifier.mSecureAppChangedListeners) {
            try {
                IBinder asBinder = iSecureAppChangedListener.asBinder();
                try {
                    if (str.length() > 100) {
                        str = str.substring(0, 100);
                    }
                    SecureAppNotifier.SecureAppChangedListenerInfo secureAppChangedListenerInfo = secureAppNotifier.new SecureAppChangedListenerInfo(iSecureAppChangedListener, str, Binder.getCallingPid(), Binder.getCallingUid());
                    asBinder.linkToDeath(secureAppChangedListenerInfo, 0);
                    ((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).put(asBinder, secureAppChangedListenerInfo);
                } catch (RemoteException unused) {
                    return false;
                }
            } finally {
            }
        }
        return true;
    }

    public final boolean registerStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener, String str) {
        checkPermissionAndAAoWFeature("Permission required to register StartActivityInterceptListener");
        synchronized (this.mLock) {
            try {
                if (!this.mIsStartActivityListenerRegistered) {
                    ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerRemoteAppControllerCallbacks(this.mRemoteAppControllerCallbacks);
                    this.mIsStartActivityListenerRegistered = true;
                }
            } finally {
            }
        }
        StartActivityInterceptNotifier startActivityInterceptNotifier = this.mStartActivityInterceptNotifier;
        synchronized (startActivityInterceptNotifier.mStartActivityInterceptListeners) {
            try {
                IBinder asBinder = iStartActivityInterceptListener.asBinder();
                try {
                    if (str.length() > 100) {
                        str = str.substring(0, 100);
                    }
                    StartActivityInterceptNotifier.StartActivityInterceptListenerInfo startActivityInterceptListenerInfo = startActivityInterceptNotifier.new StartActivityInterceptListenerInfo(iStartActivityInterceptListener, str, Binder.getCallingPid(), Binder.getCallingUid());
                    asBinder.linkToDeath(startActivityInterceptListenerInfo, 0);
                    ((ArrayMap) startActivityInterceptNotifier.mStartActivityInterceptListeners).put(asBinder, startActivityInterceptListenerInfo);
                } catch (RemoteException unused) {
                    return false;
                }
            } finally {
            }
        }
        return true;
    }

    public final boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str) {
        checkPermissionAndAAoWFeature("registerTaskChangeListener");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mTaskChangeNotifier.registerTaskChangeListener(iTaskChangeListener, str, this.mLTWProtocolVersion >= 4);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void releaseVirtualDisplay(int i) {
        ActivityManager activityManager;
        List<ActivityManager.RunningTaskInfo> runningTasks;
        checkPermissionAndAAoWFeature("releaseVirtualDisplay");
        synchronized (this.mLock) {
            try {
                if (this.mVirtualDisplayMap.containsKey(Integer.valueOf(i)) && ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).mVirtualDisplay.getDisplay().isValid()) {
                    if (this.mLTWProtocolVersion < 5 && (runningTasks = (activityManager = (ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE)) != null) {
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                            if (runningTaskInfo.displayId == i) {
                                activityManager.semRemoveTask(runningTaskInfo.taskId, 0);
                            }
                        }
                    }
                    ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).mVirtualDisplay.release();
                    this.mVirtualDisplayMap.remove(Integer.valueOf(i));
                    checkRemoteAppModeEnabled();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) {
        checkPermissionAndAAoWFeature("resizeVirtualDisplay");
        synchronized (this.mLock) {
            try {
                if (this.mVirtualDisplayMap.containsKey(Integer.valueOf(i)) && ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).mVirtualDisplay.getDisplay().isValid()) {
                    ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).mVirtualDisplay.resize(i2, i3, i4);
                    ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).mVirtualDisplay.setSurface(surface);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) {
        checkPermissionAndAAoWFeature("sendNotificationAction");
        if (statusBarNotification.getNotification() == null || statusBarNotification.getNotification().actions == null || statusBarNotification.getNotification().actions.length <= i) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (intent == null) {
            try {
                try {
                    intent = new Intent();
                } catch (PendingIntent.CanceledException | NullPointerException e) {
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Intent intent2 = intent;
        intent2.setRemoteAppLaunch(true);
        PendingIntent pendingIntent = statusBarNotification.getNotification().actions[i].actionIntent;
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(true);
        pendingIntent.send(this.mContext, 0, intent2, null, null, null, makeBasic.toBundle());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public final boolean sendNotificationContent(StatusBarNotification statusBarNotification) {
        checkPermissionAndAAoWFeature("sendNotificationContent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent();
                intent.setRemoteAppLaunch(true);
                PendingIntent pendingIntent = statusBarNotification.getNotification().contentIntent != null ? statusBarNotification.getNotification().contentIntent : statusBarNotification.getNotification().fullScreenIntent;
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(true);
                pendingIntent.send(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (PendingIntent.CanceledException | NullPointerException e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendPendingIntent(PendingIntent pendingIntent) {
        checkPermissionAndAAoWFeature("sendPendingIntent");
        Intent intent = new Intent();
        intent.setRemoteAppLaunch(true);
        try {
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(true);
            pendingIntent.send(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public final void setLTWProtocolVersion(int i) {
        this.mLTWProtocolVersion = i;
    }

    public final void setSendingUserPresentExpiredTime(long j) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "setExpiredTime");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ProximityManager proximityManager = this.mProximityManager;
                proximityManager.ensureInitSharedPreference();
                SharedPreferences.Editor edit = proximityManager.sharedPreferences.edit();
                edit.putLong("ltw_proximity_expired_time", j);
                edit.commit();
            } catch (SecurityException e) {
                Log.e("[RAMS]RemoteAppModeService", " SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startActivityAsUser(Intent intent, Bundle bundle, int i) {
        boolean z;
        try {
            z = ((Intent) intent.clone()).hasExtra("profile");
        } catch (Exception e) {
            Log.d("[RAMS]RemoteAppModeService", "exception  = " + e.getMessage());
            e.printStackTrace();
            z = false;
        }
        if (!z) {
            this.mContext.startActivityAsUser(intent, bundle, UserHandle.of(i));
            return;
        }
        this.mContext.startActivityAsUser(intent, bundle, ((UserManager) this.mContext.getSystemService("user")).getUserForSerialNumber(intent.getLongExtra("profile", -1L)));
    }

    public final void startRFCommService() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mRFCommServiceLauncher.bindService(this.mContext);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void stopRFCommService() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            RFCommServiceLauncher rFCommServiceLauncher = this.mRFCommServiceLauncher;
            Context context = this.mContext;
            Log.i("RFCommServiceLauncher", "unbindService - mBounded : " + rFCommServiceLauncher.mBounded);
            if (rFCommServiceLauncher.mBounded) {
                context.unbindService(rFCommServiceLauncher.mConnection);
                rFCommServiceLauncher.mBounded = false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) {
        checkPermissionAndAAoWFeature("transferTaskUsingIntent");
        transferTaskWithoutInterceptInternal(intent, i, i2, bundle, getTaskInfo(i));
    }

    public final void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) {
        checkPermissionAndAAoWFeature("transferTaskWithoutIntercept");
        TaskInfo taskInfo = getTaskInfo(i);
        transferTaskWithoutInterceptInternal(taskInfo.baseIntent, i, i2, bundle, taskInfo);
    }

    public final void transferTaskWithoutInterceptInternal(Intent intent, int i, int i2, Bundle bundle, TaskInfo taskInfo) {
        if (taskInfo != null) {
            synchronized (this.mLock) {
                this.mTransferTaskMap.put(Integer.valueOf(i), taskInfo);
            }
            ActivityOptions activityOptions = bundle != null ? new ActivityOptions(bundle) : null;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (activityOptions == null) {
                try {
                    activityOptions = ActivityOptions.makeBasic();
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            activityOptions.setLaunchDisplayId(i2);
            startActivityAsUser(intent, activityOptions.toBundle(), taskInfo.userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "unregisterRemoteAppModeListener");
        RemoteAppModeNotifier remoteAppModeNotifier = this.mRemoteAppModeNotifier;
        synchronized (remoteAppModeNotifier.mRemoteAppModeListeners) {
            try {
                RemoteAppModeNotifier.RemoteAppModeListenerInfo remoteAppModeListenerInfo = (RemoteAppModeNotifier.RemoteAppModeListenerInfo) ((ArrayMap) remoteAppModeNotifier.mRemoteAppModeListeners).remove(iRemoteAppModeListener.asBinder());
                if (remoteAppModeListenerInfo == null) {
                    return false;
                }
                remoteAppModeListenerInfo.listener.asBinder().unlinkToDeath(remoteAppModeListenerInfo, 0);
                return true;
            } finally {
            }
        }
    }

    public final boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) {
        checkPermissionAndAAoWFeature("unregisterRotationChangeListener");
        RotationChangeNotifier rotationChangeNotifier = this.mRotationChangeNotifier;
        synchronized (rotationChangeNotifier.mRotationChangedListeners) {
            try {
                RotationChangeNotifier.RotationChangedListenerInfo rotationChangedListenerInfo = (RotationChangeNotifier.RotationChangedListenerInfo) ((ArrayMap) rotationChangeNotifier.mRotationChangedListeners).remove(iRotationChangeListener.asBinder());
                if (rotationChangedListenerInfo == null) {
                    return false;
                }
                RemoteAppRotationWatcher remoteAppRotationWatcher = rotationChangedListenerInfo.mWatcher;
                if (remoteAppRotationWatcher != null) {
                    remoteAppRotationWatcher.listener = null;
                    WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(remoteAppRotationWatcher);
                }
                rotationChangedListenerInfo.listener.asBinder().unlinkToDeath(rotationChangedListenerInfo, 0);
                return true;
            } finally {
            }
        }
    }

    public final boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) {
        checkPermissionAndAAoWFeature("unregisterSecureAppChangedListener");
        SecureAppNotifier secureAppNotifier = this.mSecureAppNotifier;
        synchronized (secureAppNotifier.mSecureAppChangedListeners) {
            try {
                SecureAppNotifier.SecureAppChangedListenerInfo secureAppChangedListenerInfo = (SecureAppNotifier.SecureAppChangedListenerInfo) ((ArrayMap) secureAppNotifier.mSecureAppChangedListeners).remove(iSecureAppChangedListener.asBinder());
                if (secureAppChangedListenerInfo == null) {
                    return false;
                }
                secureAppChangedListenerInfo.listener.asBinder().unlinkToDeath(secureAppChangedListenerInfo, 0);
                return true;
            } finally {
            }
        }
    }

    public final boolean unregisterStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener) {
        checkPermissionAndAAoWFeature("Permission required to unregister StartActivityInterceptListener");
        synchronized (this.mLock) {
            ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).unregisterRemoteAppControllerCallbacks(this.mRemoteAppControllerCallbacks);
            this.mIsStartActivityListenerRegistered = false;
            this.mTransferTaskMap.clear();
        }
        InterceptedActivityRepo interceptedActivityRepo = this.mInterceptedActivityRepo;
        synchronized (interceptedActivityRepo.mLock) {
            interceptedActivityRepo.mInterceptedActivityInfoMap.clear();
        }
        StartActivityInterceptNotifier startActivityInterceptNotifier = this.mStartActivityInterceptNotifier;
        synchronized (startActivityInterceptNotifier.mStartActivityInterceptListeners) {
            try {
                StartActivityInterceptNotifier.StartActivityInterceptListenerInfo startActivityInterceptListenerInfo = (StartActivityInterceptNotifier.StartActivityInterceptListenerInfo) ((ArrayMap) startActivityInterceptNotifier.mStartActivityInterceptListeners).remove(iStartActivityInterceptListener.asBinder());
                if (startActivityInterceptListenerInfo == null) {
                    return false;
                }
                startActivityInterceptListenerInfo.listener.asBinder().unlinkToDeath(startActivityInterceptListenerInfo, 0);
                return true;
            } finally {
            }
        }
    }

    public final boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) {
        checkPermissionAndAAoWFeature("unregisterTaskChangeListener");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mTaskChangeNotifier.unregisterTaskChangeListener(iTaskChangeListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
