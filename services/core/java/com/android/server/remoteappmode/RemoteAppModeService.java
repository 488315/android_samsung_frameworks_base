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
import android.content.IntentFilter;
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
import android.view.Surface;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.RemoteAppController;
import com.android.server.wm.RemoteAppControllerCallbacks;
import com.android.server.uri.NeededUriGrants;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class RemoteAppModeService extends IRemoteAppMode.Stub {
    public static final boolean DEBUG;
    public static final String TAG = "[RAMS]" + RemoteAppModeService.class.getSimpleName();
    public AnrCollector mAnrCollector;
    public final Context mContext;
    public int mCurrentUserId;
    public DisplayManager mDisplayManager;
    public final Handler mHandler;
    public InterceptedActivityRepo mInterceptedActivityRepo;
    public boolean mIsBootComplete;
    public boolean mIsRemoteAppModeEnabled;
    public boolean mIsStartActivityListenerRegistered;
    public int mLTWProtocolVersion;
    public LocalService mLocalService;
    public final Object mLock;
    public ProximityManager mProximityManager;
    public RFCommServiceLauncher mRFCommServiceLauncher;
    public final RemoteAppControllerCallbacks mRemoteAppControllerCallbacks;
    public final RemoteAppModeNotifier mRemoteAppModeNotifier;
    public final ContentResolver mResolver;
    public final RotationChangeNotifier mRotationChangeNotifier;
    public final SecureAppNotifier mSecureAppNotifier;
    public final StartActivityInterceptNotifier mStartActivityInterceptNotifier;
    public final TaskChangeNotifier mTaskChangeNotifier;
    public final ServiceThread mThread;
    public HashMap mTransferTaskMap;
    public ContentObserver mUserSetupCompleteObserver;
    public HashMap mVirtualDisplayMap;

    public int getProtocolVersion() {
        return 9;
    }

    static {
        DEBUG = Debug.semIsProductDev() || android.util.Log.isLoggable("RAMS", 3);
    }

    public final void saveCallerInfo(Intent intent, RemoteAppController.CallerInfo callerInfo, NeededUriGrants neededUriGrants, int i) {
        if (callerInfo == null && i == this.mCurrentUserId) {
            return;
        }
        this.mInterceptedActivityRepo.put(intent, new InterceptedActivityInfo(intent, callerInfo, neededUriGrants, i));
        Log.m83i(TAG, "saveCallerInfo - mInterceptedActivityRepo.put cInfo:" + callerInfo + ", intent:" + intent);
    }

    public final class Lifecycle extends SystemService {
        public RemoteAppModeService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.remoteappmode.RemoteAppModeService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            ?? remoteAppModeService = new RemoteAppModeService(getContext());
            this.mService = remoteAppModeService;
            publishBinderService("remoteappmode", remoteAppModeService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            RemoteAppModeService remoteAppModeService = this.mService;
            if (remoteAppModeService != null) {
                remoteAppModeService.onBootPhase(i);
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            this.mService.onUserStarting(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(SystemService.TargetUser targetUser) {
            this.mService.onUserUnlocking(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            this.mService.onUserSwitching(targetUser2.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(SystemService.TargetUser targetUser) {
            this.mService.onUserStopping(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(SystemService.TargetUser targetUser) {
            this.mService.onUserStopped(targetUser.getUserIdentifier());
        }
    }

    public class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        public void register() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PHONE_STATE");
            intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
            RemoteAppModeService.this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, RemoteAppModeService.this.mHandler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = RemoteAppModeService.DEBUG;
            if (z) {
                Log.m81d(RemoteAppModeService.TAG, "onReceive(), action=" + action);
            }
            if (!"android.intent.action.PHONE_STATE".equals(action) && "android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                if (z) {
                    Log.m81d(RemoteAppModeService.TAG, "Shutdown received with UserId: " + getSendingUserId());
                }
                if (getSendingUserId() == -1) {
                    RemoteAppModeService.this.mIsBootComplete = false;
                }
            }
        }
    }

    public final class LocalService extends RemoteAppModeManagerInternal {
        public LocalService() {
        }

        public void onConfigurationChanged(Configuration configuration, int i) {
            RemoteAppModeService.this.onConfigurationChanged(configuration, i);
        }

        public void onSecuredAppLaunched(int i, String str) {
            RemoteAppModeService.this.onSecuredAppLaunched(i, str);
        }

        public void onSecuredAppRemoved(int i, String str) {
            RemoteAppModeService.this.onSecuredAppRemoved(i, str);
        }
    }

    public RemoteAppModeService(Context context) {
        this.mLock = new Object();
        this.mIsStartActivityListenerRegistered = false;
        this.mIsBootComplete = false;
        this.mIsRemoteAppModeEnabled = false;
        this.mCurrentUserId = -10000;
        this.mLTWProtocolVersion = 3;
        this.mVirtualDisplayMap = new HashMap();
        this.mTransferTaskMap = new HashMap();
        this.mRemoteAppControllerCallbacks = new RemoteAppControllerCallbacks() { // from class: com.android.server.remoteappmode.RemoteAppModeService.1
            @Override // com.android.server.wm.RemoteAppControllerCallbacks
            public boolean onStartActivityInterceptedLocked(Intent intent, ActivityOptions activityOptions, ActivityInfo activityInfo, int i, boolean z, ActivityManager.RunningTaskInfo runningTaskInfo, RemoteAppController.CallerInfo callerInfo, NeededUriGrants neededUriGrants, int i2, int i3) {
                if (RemoteAppModeService.this.mStartActivityInterceptNotifier == null) {
                    return false;
                }
                synchronized (RemoteAppModeService.this.mLock) {
                    if (runningTaskInfo != null) {
                        if (RemoteAppModeService.this.mTransferTaskMap.containsKey(Integer.valueOf(runningTaskInfo.taskId))) {
                            RemoteAppModeService.this.mTransferTaskMap.remove(Integer.valueOf(runningTaskInfo.taskId));
                            return false;
                        }
                    }
                    intent.setRemoteAppLaunch(false);
                    RemoteAppModeService.this.saveCallerInfo(intent, callerInfo, neededUriGrants, i2);
                    RemoteAppModeService.this.mStartActivityInterceptNotifier.notify(intent, activityOptions, activityInfo, i, z, runningTaskInfo, i3);
                    return true;
                }
            }
        };
        ServiceThread serviceThread = new ServiceThread("remoteappmode", -4, false);
        this.mThread = serviceThread;
        serviceThread.start();
        Handler handler = new Handler(serviceThread.getLooper());
        this.mHandler = handler;
        this.mLocalService = null;
        this.mContext = context;
        context.setTheme(R.style.Theme.DeviceDefault.Light);
        this.mResolver = context.getContentResolver();
        this.mRemoteAppModeNotifier = new RemoteAppModeNotifier(context);
        this.mTaskChangeNotifier = new TaskChangeNotifier(context);
        this.mSecureAppNotifier = new SecureAppNotifier(context);
        this.mRotationChangeNotifier = new RotationChangeNotifier(context);
        this.mStartActivityInterceptNotifier = new StartActivityInterceptNotifier(context);
        this.mInterceptedActivityRepo = new InterceptedActivityRepo();
        this.mRFCommServiceLauncher = new RFCommServiceLauncher(context);
        this.mAnrCollector = new AnrCollector(context);
        this.mProximityManager = new ProximityManager(context, handler);
        new Receiver().register();
    }

    public final void onBootPhase(int i) {
        if (i == 500) {
            this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        }
    }

    public final void initializeStates() {
        if (isUserSetupComplete()) {
            return;
        }
        if (this.mUserSetupCompleteObserver == null) {
            this.mUserSetupCompleteObserver = new ContentObserver(null) { // from class: com.android.server.remoteappmode.RemoteAppModeService.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    super.onChange(z);
                    RemoteAppModeService.this.initializeStates();
                    RemoteAppModeService.this.mResolver.unregisterContentObserver(this);
                    RemoteAppModeService.this.mUserSetupCompleteObserver = null;
                }
            };
        }
        this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupCompleteObserver, this.mCurrentUserId);
    }

    public final void onUserStarting(int i) {
        if (DEBUG) {
            Log.m81d(TAG, "onStartUser(), userId=" + i);
        }
    }

    public final void onUserUnlocking(int i) {
        if (DEBUG) {
            Log.m81d(TAG, "onUnlockUser(), userId=" + i + ", CurrentUser=" + ActivityManager.getCurrentUser());
        }
        this.mIsBootComplete = true;
        if (i == ActivityManager.getCurrentUser()) {
            onUserChanged(i);
        }
        this.mProximityManager.register();
    }

    public final void onUserSwitching(int i) {
        if (DEBUG) {
            Log.m81d(TAG, "onSwitchUser(), userId=" + i);
        }
        onUserChanged(i);
    }

    public final void onUserStopping(int i) {
        if (DEBUG) {
            Log.m81d(TAG, "onStopUser(), userId=" + i + ", CurrentUser=" + ActivityManager.getCurrentUser());
        }
    }

    public final void onUserStopped(int i) {
        if (DEBUG) {
            Log.m81d(TAG, "onCleanupUser(), userId=" + i);
        }
    }

    public void setCurrentUserId(int i) {
        if (DEBUG) {
            Log.m81d(TAG, "setCurrentUserId(), userId=" + i);
        }
        this.mCurrentUserId = i;
    }

    public final void onUserChanged(int i) {
        if (i == this.mCurrentUserId) {
            return;
        }
        if (DEBUG) {
            Log.m81d(TAG, "onUserChanged(), userId=" + i);
        }
        setCurrentUserId(i);
        initializeStates();
    }

    public void onConfigurationChanged(Configuration configuration, int i) {
        if (configuration != null) {
            new Configuration(configuration);
        }
    }

    public final boolean isUserSetupComplete() {
        boolean z = Settings.Secure.getIntForUser(this.mResolver, "user_setup_complete", 0, this.mCurrentUserId) != 0;
        if (!z && DEBUG) {
            Log.m81d(TAG, "isUserSetupComplete()=false");
        }
        return z;
    }

    public final boolean isSystemReady() {
        if (this.mIsBootComplete && isUserSetupComplete() && !FactoryTest.isFactoryBinary() && this.mCurrentUserId != -10000) {
            return true;
        }
        Log.m83i(TAG, "isSystemReady(), mIsBootComplete=" + this.mIsBootComplete + ", isFactoryBinary=" + FactoryTest.isFactoryBinary() + ", mCurrentUserId=" + this.mCurrentUserId);
        return false;
    }

    public int createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, IVirtualDisplayAliveChecker iVirtualDisplayAliveChecker) {
        checkPermissionAndAAoWFeature("createVirtualDisplay");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            VirtualDisplay createVirtualDisplay = this.mDisplayManager.createVirtualDisplay(str, i, i2, i3, surface, 525569);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            VirtualDisplayInfo virtualDisplayInfo = new VirtualDisplayInfo(createVirtualDisplay, iVirtualDisplayAliveChecker.asBinder(), Binder.getCallingPid(), Binder.getCallingUid());
            try {
                iVirtualDisplayAliveChecker.asBinder().linkToDeath(virtualDisplayInfo, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            synchronized (this.mLock) {
                if (createVirtualDisplay != null) {
                    this.mVirtualDisplayMap.put(Integer.valueOf(createVirtualDisplay.getDisplay().getDisplayId()), virtualDisplayInfo);
                }
                checkRemoteAppModeEnabled();
            }
            if (createVirtualDisplay != null) {
                return createVirtualDisplay.getDisplay().getDisplayId();
            }
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void checkPermissionAndAAoWFeature(String str) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", str);
        if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_LTW_REMOTE_APP")) {
            throw new UnsupportedOperationException("LTW_REMOTE_APP feature is not enabled in this device.");
        }
    }

    public void releaseVirtualDisplay(int i) {
        checkPermissionAndAAoWFeature("releaseVirtualDisplay");
        synchronized (this.mLock) {
            if (this.mVirtualDisplayMap.containsKey(Integer.valueOf(i)) && ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).getVirtualDisplay().getDisplay().isValid()) {
                if (this.mLTWProtocolVersion < 5) {
                    closeAllTask(i);
                }
                ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).getVirtualDisplay().release();
                this.mVirtualDisplayMap.remove(Integer.valueOf(i));
                checkRemoteAppModeEnabled();
            }
        }
    }

    public final void closeAllTask(int i) {
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if (runningTasks != null) {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                if (runningTaskInfo.displayId == i) {
                    activityManager.semRemoveTask(runningTaskInfo.taskId, 0);
                }
            }
        }
    }

    public void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) {
        checkPermissionAndAAoWFeature("resizeVirtualDisplay");
        synchronized (this.mLock) {
            if (this.mVirtualDisplayMap.containsKey(Integer.valueOf(i)) && ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).getVirtualDisplay().getDisplay().isValid()) {
                ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).getVirtualDisplay().resize(i2, i3, i4);
                ((VirtualDisplayInfo) this.mVirtualDisplayMap.get(Integer.valueOf(i))).getVirtualDisplay().setSurface(surface);
            }
        }
    }

    public void moveDisplayToTop(final int i) {
        checkPermissionAndAAoWFeature("moveDisplayToTop");
        synchronized (this.mLock) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.remoteappmode.RemoteAppModeService.3
                @Override // java.lang.Runnable
                public void run() {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            WindowManager windowManager = (WindowManager) RemoteAppModeService.this.mContext.getSystemService("window");
                            if ((RemoteAppModeService.this.mVirtualDisplayMap.containsKey(Integer.valueOf(i)) && ((VirtualDisplayInfo) RemoteAppModeService.this.mVirtualDisplayMap.get(Integer.valueOf(i))).getVirtualDisplay().getDisplay().isValid()) || windowManager.getDefaultDisplay().getDisplayId() == i) {
                                WindowManagerGlobal.getWindowManagerService().moveDisplayToTop(i, "RemoteAppModeService");
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            });
        }
    }

    public void launchApplication(int i, String str, Intent intent, Bundle bundle) {
        checkPermissionAndAAoWFeature("launchApplication");
        RemoteAppController.CallerInfo callerInfo = null;
        ActivityOptions activityOptions = bundle != null ? new ActivityOptions(bundle) : null;
        String str2 = TAG;
        Log.m83i(str2, "launchApplication  - intent: " + intent);
        InterceptedActivityInfo interceptedActivityInfo = this.mInterceptedActivityRepo.get(intent);
        Log.m83i(str2, "launchApplication  - interceptedActivityInfo: " + interceptedActivityInfo);
        int i2 = this.mCurrentUserId;
        if (interceptedActivityInfo != null) {
            callerInfo = interceptedActivityInfo.getCallerInfo();
            i2 = interceptedActivityInfo.getUserId();
            this.mInterceptedActivityRepo.remove(intent);
        }
        startActivity(i, intent, activityOptions, interceptedActivityInfo, callerInfo, i2);
    }

    public final void startActivity(int i, Intent intent, ActivityOptions activityOptions, InterceptedActivityInfo interceptedActivityInfo, RemoteAppController.CallerInfo callerInfo, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (activityOptions == null) {
                try {
                    activityOptions = ActivityOptions.makeBasic();
                } catch (Exception e) {
                    Log.m81d(TAG, "exception  = " + e.getMessage());
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
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startActivityAsUser(Intent intent, Bundle bundle, int i) {
        if (hasExtraProfile(intent)) {
            this.mContext.startActivityAsUser(intent, bundle, ((UserManager) this.mContext.getSystemService("user")).getUserForSerialNumber(intent.getLongExtra("profile", -1L)));
            return;
        }
        this.mContext.startActivityAsUser(intent, bundle, UserHandle.of(i));
    }

    public final boolean hasExtraProfile(Intent intent) {
        try {
            return ((Intent) intent.clone()).hasExtra("profile");
        } catch (Exception e) {
            Log.m81d(TAG, "exception  = " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) {
        checkPermissionAndAAoWFeature("transferTaskWithoutIntercept");
        TaskInfo taskInfo = getTaskInfo(this.mContext, i);
        transferTaskWithoutInterceptInternal(taskInfo.baseIntent, i, i2, bundle, taskInfo);
    }

    public void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) {
        checkPermissionAndAAoWFeature("transferTaskUsingIntent");
        transferTaskWithoutInterceptInternal(intent, i, i2, bundle, getTaskInfo(this.mContext, i));
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
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            activityOptions.setLaunchDisplayId(i2);
            startActivityAsUser(intent, activityOptions.toBundle(), taskInfo.userId);
        }
    }

    public static TaskInfo getTaskInfo(Context context, int i) {
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

    public void setLTWProtocolVersion(int i) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        this.mLTWProtocolVersion = i;
    }

    public void startRFCommService() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mRFCommServiceLauncher.bindService(this.mContext);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stopRFCommService() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mRFCommServiceLauncher.unbindService(this.mContext);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isAllowed() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "isAllowed");
        boolean isSystemReady = isSystemReady();
        Log.m81d(TAG, "isAllowed = " + isSystemReady);
        return isSystemReady;
    }

    public final void onSecuredAppLaunched(int i, String str) {
        if (DEBUG) {
            Log.m83i(TAG, "onSecuredAppLaunched, taskId=" + i + ", packageName= " + str);
        }
        this.mSecureAppNotifier.notifySecuredAppLaunched(i, str);
    }

    public final void onSecuredAppRemoved(int i, String str) {
        if (DEBUG) {
            Log.m83i(TAG, "onSecureAppRemoved, taskId=" + i + ", packageName= " + str);
        }
        this.mSecureAppNotifier.notifySecuredAppRemoved(i, str);
    }

    public boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str) {
        checkPermissionAndAAoWFeature("registerTaskChangeListener");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mTaskChangeNotifier.registerTaskChangeListener(iTaskChangeListener, str, this.mLTWProtocolVersion >= 4);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) {
        checkPermissionAndAAoWFeature("unregisterTaskChangeListener");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mTaskChangeNotifier.unregisterTaskChangeListener(iTaskChangeListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) {
        checkPermissionAndAAoWFeature("registerSecureAppChangedListener");
        if (this.mLocalService == null) {
            LocalService localService = new LocalService();
            this.mLocalService = localService;
            LocalServices.addService(RemoteAppModeManagerInternal.class, localService);
        }
        return this.mSecureAppNotifier.registerSecureAppChangedListener(iSecureAppChangedListener, str);
    }

    public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) {
        checkPermissionAndAAoWFeature("unregisterSecureAppChangedListener");
        return this.mSecureAppNotifier.unregisterSecureAppChangedListener(iSecureAppChangedListener);
    }

    public boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) {
        checkPermissionAndAAoWFeature("registerRotationChangeListener");
        return this.mRotationChangeNotifier.registerRotationChangeListener(iRotationChangeListener, str, i);
    }

    public boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) {
        checkPermissionAndAAoWFeature("unregisterRotationChangeListener");
        return this.mRotationChangeNotifier.unregisterRotationChangeListener(iRotationChangeListener);
    }

    public boolean registerStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener, String str) {
        checkPermissionAndAAoWFeature("Permission required to register StartActivityInterceptListener");
        synchronized (this.mLock) {
            if (!this.mIsStartActivityListenerRegistered) {
                ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerRemoteAppControllerCallbacks(this.mRemoteAppControllerCallbacks);
                this.mIsStartActivityListenerRegistered = true;
            }
        }
        return this.mStartActivityInterceptNotifier.registerStartActivityInterceptListener(iStartActivityInterceptListener, str);
    }

    public boolean unregisterStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener) {
        checkPermissionAndAAoWFeature("Permission required to unregister StartActivityInterceptListener");
        synchronized (this.mLock) {
            ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).unregisterRemoteAppControllerCallbacks(this.mRemoteAppControllerCallbacks);
            this.mIsStartActivityListenerRegistered = false;
            this.mTransferTaskMap.clear();
        }
        this.mInterceptedActivityRepo.clear();
        return this.mStartActivityInterceptNotifier.unregisterStartActivityInterceptListener(iStartActivityInterceptListener);
    }

    public boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "registerRemoteAppModeListener");
        return this.mRemoteAppModeNotifier.registerRemoteAppModeListener(iRemoteAppModeListener, str);
    }

    public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "unregisterRemoteAppModeListener");
        return this.mRemoteAppModeNotifier.unregisterRemoteAppModeListener(iRemoteAppModeListener);
    }

    public final void checkRemoteAppModeEnabled() {
        boolean z;
        if (!this.mVirtualDisplayMap.isEmpty()) {
            Iterator it = this.mVirtualDisplayMap.values().iterator();
            while (it.hasNext()) {
                if (((VirtualDisplayInfo) it.next()).getVirtualDisplay().getDisplay().isValid()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (this.mIsRemoteAppModeEnabled != z) {
            if (DEBUG) {
                Log.m83i(TAG, "checkRemoteAppModeEnabled, isEnabled=" + z);
            }
            this.mIsRemoteAppModeEnabled = z;
            this.mRemoteAppModeNotifier.notifyRemoteAppModeStateChanged(z);
        }
    }

    public void clearAll() {
        checkPermissionAndAAoWFeature("Permission required to clear all resources");
        releaseAllVirtualDisplays();
        releaseSecureAppNotifier();
        releaseTaskChangeNotifier();
    }

    public void sendPendingIntent(PendingIntent pendingIntent) {
        checkPermissionAndAAoWFeature("sendPendingIntent");
        Intent intent = new Intent();
        intent.setRemoteAppLaunch(true);
        try {
            pendingIntent.send(this.mContext, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public boolean sendNotificationContent(StatusBarNotification statusBarNotification) {
        PendingIntent pendingIntent;
        checkPermissionAndAAoWFeature("sendNotificationContent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent();
            intent.setRemoteAppLaunch(true);
            if (statusBarNotification.getNotification().contentIntent != null) {
                pendingIntent = statusBarNotification.getNotification().contentIntent;
            } else {
                pendingIntent = statusBarNotification.getNotification().fullScreenIntent;
            }
            pendingIntent.send(this.mContext, 0, intent);
            return true;
        } catch (PendingIntent.CanceledException | NullPointerException e) {
            e.printStackTrace();
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) {
        checkPermissionAndAAoWFeature("sendNotificationAction");
        if (statusBarNotification.getNotification() == null || statusBarNotification.getNotification().actions == null || statusBarNotification.getNotification().actions.length <= i) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (intent == null) {
                intent = new Intent();
            }
            intent.setRemoteAppLaunch(true);
            statusBarNotification.getNotification().actions[i].actionIntent.send(this.mContext, 0, intent);
            return true;
        } catch (PendingIntent.CanceledException | NullPointerException e) {
            e.printStackTrace();
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void forceStopPackage(String str) {
        checkPermissionAndAAoWFeature("Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    ActivityManager.getService().forceStopPackage(str, this.mCurrentUserId);
                } catch (SecurityException e) {
                    Log.m82e(TAG, " removeTask: SecurityException " + e.getMessage());
                }
            } catch (RemoteException e2) {
                Log.m82e(TAG, " removeTask: RemoteException " + e2.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "Permission Denied");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAnrCollector.getLastAnr(str, parcelFileDescriptor);
            } catch (SecurityException e) {
                Log.m82e(TAG, " removeTask: SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void enableSendingUserPresentIntent(String str) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "enableSendingUserPresentIntent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mProximityManager.enableSendingUserPresentIntent(str);
            } catch (SecurityException e) {
                Log.m82e(TAG, " SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void disableSendingUserPresentIntent() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "disableSendingUserPresentIntent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mProximityManager.disableSendingUserPresentIntent();
            } catch (SecurityException e) {
                Log.m82e(TAG, " SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isSendingUserPresentEnabled() {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "isSendingUserPresentEnabled");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = this.mProximityManager.isSendingUserPresentEnabled();
            } catch (SecurityException e) {
                Log.m82e(TAG, " SecurityException " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSendingUserPresentExpiredTime(long j) {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "setExpiredTime");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mProximityManager.setExpiredTime(j);
            } catch (SecurityException e) {
                Log.m82e(TAG, " SecurityException " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long getSendingUserPresentExpiredTime() {
        this.mContext.enforceCallingOrSelfPermission("com.sec.android.permission.USE_LINK_TO_WINDOWS_REMOTE_APP_MODE", "setExpiredTime");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mProximityManager.getExpiredTime();
            } catch (SecurityException e) {
                Log.m82e(TAG, " SecurityException " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0L;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void releaseAllVirtualDisplays() {
        synchronized (this.mLock) {
            Iterator it = this.mVirtualDisplayMap.values().iterator();
            while (it.hasNext()) {
                ((VirtualDisplayInfo) it.next()).getVirtualDisplay().release();
            }
            this.mVirtualDisplayMap.clear();
            checkRemoteAppModeEnabled();
        }
    }

    public final void releaseSecureAppNotifier() {
        SecureAppNotifier secureAppNotifier = this.mSecureAppNotifier;
        if (secureAppNotifier != null) {
            secureAppNotifier.releaseAllListeners();
        }
    }

    public final void releaseTaskChangeNotifier() {
        TaskChangeNotifier taskChangeNotifier = this.mTaskChangeNotifier;
        if (taskChangeNotifier != null) {
            taskChangeNotifier.releaseAllListeners();
        }
    }

    public class VirtualDisplayInfo implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final int mPid;
        public final int mUid;
        public final VirtualDisplay mVirtualDisplay;

        public VirtualDisplayInfo(VirtualDisplay virtualDisplay, IBinder iBinder, int i, int i2) {
            this.mVirtualDisplay = virtualDisplay;
            this.mBinder = iBinder;
            this.mPid = i;
            this.mUid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.m81d(RemoteAppModeService.TAG, "VirtualDisplayInfo - binderDied");
            this.mBinder.unlinkToDeath(this, 0);
            this.mVirtualDisplay.release();
            synchronized (RemoteAppModeService.this.mLock) {
                RemoteAppModeService.this.mVirtualDisplayMap.remove(Integer.valueOf(this.mVirtualDisplay.getDisplay().getDisplayId()));
                RemoteAppModeService.this.checkRemoteAppModeEnabled();
            }
        }

        public VirtualDisplay getVirtualDisplay() {
            return this.mVirtualDisplay;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            if (strArr == null || strArr.length == 0 || "-a".equals(strArr[0])) {
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                indentingPrintWriter.println("RemoteAppModeService (dumpsys remoteappmode):");
                Log.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                dumpImpl(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                return;
            }
            if (DEBUG) {
                String str = strArr[0];
            }
        }
    }

    public final void dumpImpl(IndentingPrintWriter indentingPrintWriter) {
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
    }
}
