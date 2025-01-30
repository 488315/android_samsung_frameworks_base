package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.service.voice.IVoiceInteractionSession;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.TaskFragmentAnimationParams;
import android.window.WindowContainerToken;
import com.android.internal.app.ResolverActivity;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.function.QuintPredicate;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.am.AppTimeTracker;
import com.android.server.am.UserState;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.LaunchParamsController;
import com.android.server.wm.RootWindowContainer;
import com.android.server.wm.Task;
import com.android.server.wm.TransitionController;
import com.android.server.policy.PermissionPolicyInternal;
import com.android.server.utils.Slogf;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.ipm.SecIpmManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class RootWindowContainer extends WindowContainer implements DisplayManager.DisplayListener {
    public final AttachApplicationHelper mAttachApplicationHelper;
    public final Consumer mCloseSystemDialogsConsumer;
    public String mCloseSystemDialogsReason;
    public int mCurrentUser;
    public long mDeXUserActivityTimeout;
    public DisplayContent mDefaultDisplay;
    public int mDefaultMinSizeOfResizeableTaskDp;
    public String mDestroyAllActivitiesReason;
    public final Runnable mDestroyAllActivitiesRunnable;
    public final mDestroyTargetAllActivities mDestroyTargetAllActivitiesRunnable;
    public final DeviceStateController mDeviceStateController;
    public final SparseArray mDisplayAccessUIDs;
    public DisplayManager mDisplayManager;
    public DisplayManagerInternal mDisplayManagerInternal;
    public final ActivityTaskManagerInternal.SleepTokenAcquirer mDisplayOffTokenAcquirer;
    public final DisplayRotationCoordinator mDisplayRotationCoordinator;
    public final SparseArray mDisplayTransactions;
    public FinishDisabledPackageActivitiesHelper mFinishDisabledPackageActivitiesHelper;
    public final Handler mHandler;
    public Object mLastWindowFreezeSource;
    public boolean mObscureApplicationContentOnSecondaryDisplays;
    public boolean mOrientationChangeComplete;
    public final RankTaskLayersRunnable mRankTaskLayersRunnable;
    public float mScreenBrightnessOverride;
    public long mScreenDimDuration;
    public ActivityTaskManagerService mService;
    public final SparseArray mSleepTokens;
    public boolean mSustainedPerformanceModeCurrent;
    public boolean mSustainedPerformanceModeEnabled;
    public boolean mTaskLayersChanged;
    public ActivityTaskSupervisor mTaskSupervisor;
    public final FindTaskResult mTmpFindTaskResult;
    public int mTmpTaskLayerRank;
    public final ArrayMap mTopFocusedAppByProcess;
    public int mTopFocusedDisplayId;
    public boolean mUpdateRotation;
    public long mUserActivityTimeout;
    public SparseIntArray mUserRootTaskInFront;
    public boolean mWallpaperActionPending;
    public WindowManagerService mWindowManager;

    @Override // com.android.server.wm.ConfigurationContainer
    public String getName() {
        return "ROOT";
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isAttached() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isOnTop() {
        return true;
    }

    /* renamed from: com.android.server.wm.RootWindowContainer$1 */
    public class RunnableC29661 implements Runnable {
        public RunnableC29661() {
        }

        @Override // java.lang.Runnable
        public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock = RootWindowContainer.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    try {
                        RootWindowContainer.this.mTaskSupervisor.beginDeferResume();
                        RootWindowContainer.this.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                RootWindowContainer.RunnableC29661.this.lambda$run$0((ActivityRecord) obj);
                            }
                        });
                    } finally {
                        RootWindowContainer.this.mTaskSupervisor.endDeferResume();
                        RootWindowContainer.this.resumeFocusedTasksTopActivities();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(ActivityRecord activityRecord) {
            if (activityRecord.finishing || !activityRecord.isDestroyable()) {
                return;
            }
            activityRecord.destroyImmediately(RootWindowContainer.this.mDestroyAllActivitiesReason);
        }
    }

    public class mDestroyTargetAllActivities implements Runnable {
        public String reason;
        public WindowProcessController wpcowner;

        public mDestroyTargetAllActivities() {
        }

        public void setParam(WindowProcessController windowProcessController, String str) {
            this.wpcowner = windowProcessController;
            this.reason = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock = RootWindowContainer.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    try {
                        RootWindowContainer.this.mTaskSupervisor.beginDeferResume();
                        RootWindowContainer.this.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$mDestroyTargetAllActivities$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                RootWindowContainer.mDestroyTargetAllActivities.this.lambda$run$0((ActivityRecord) obj);
                            }
                        });
                    } finally {
                        RootWindowContainer.this.mTaskSupervisor.endDeferResume();
                        RootWindowContainer.this.resumeFocusedTasksTopActivities();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(ActivityRecord activityRecord) {
            if (activityRecord.finishing || !activityRecord.isDestroyable()) {
                return;
            }
            WindowProcessController windowProcessController = this.wpcowner;
            if (windowProcessController == null || activityRecord.app == windowProcessController) {
                activityRecord.destroyImmediately(this.reason);
            }
        }
    }

    public class FindTaskResult implements Predicate {
        public ComponentName cls;
        public Uri documentData;
        public boolean isDocument;
        public int mActivityType;
        public ActivityRecord mCandidateRecord;
        public ActivityRecord mIdealRecord;
        public ActivityInfo mInfo;
        public Intent mIntent;
        public String mTaskAffinity;
        public int userId;

        public void init(int i, String str, Intent intent, ActivityInfo activityInfo) {
            this.mActivityType = i;
            this.mTaskAffinity = str;
            this.mIntent = intent;
            this.mInfo = activityInfo;
            this.mIdealRecord = null;
            this.mCandidateRecord = null;
        }

        public void process(WindowContainer windowContainer) {
            this.cls = this.mIntent.getComponent();
            if (this.mInfo.targetActivity != null) {
                ActivityInfo activityInfo = this.mInfo;
                this.cls = new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
            }
            this.userId = UserHandle.getUserId(this.mInfo.applicationInfo.uid);
            Intent intent = this.mIntent;
            boolean isDocument = intent.isDocument() & (intent != null);
            this.isDocument = isDocument;
            this.documentData = isDocument ? this.mIntent.getData() : null;
            if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -814760297, 0, (String) null, new Object[]{String.valueOf(this.mInfo), String.valueOf(windowContainer)});
            }
            windowContainer.forAllLeafTasks(this);
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x00e9  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00f1  */
        @Override // java.util.function.Predicate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean test(Task task) {
            Uri uri;
            boolean z;
            int launchTaskIdForSingleInstancePerTask;
            String str;
            int launchTaskIdForAliasManagedTarget;
            if (!ConfigurationContainer.isCompatibleActivityType(this.mActivityType, task.getActivityType())) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -373110070, 0, (String) null, new Object[]{String.valueOf(task)});
                }
                return false;
            }
            if (task.voiceSession != null) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 51927339, 0, (String) null, new Object[]{String.valueOf(task)});
                }
                return false;
            }
            if (task.mUserId != this.userId) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -399343789, 0, (String) null, new Object[]{String.valueOf(task)});
                }
                return false;
            }
            ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity(false);
            if (topNonFinishingActivity == null || topNonFinishingActivity.finishing || topNonFinishingActivity.mUserId != this.userId || topNonFinishingActivity.launchMode == 3) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -1575977269, 0, (String) null, new Object[]{String.valueOf(task), String.valueOf(topNonFinishingActivity)});
                }
                return false;
            }
            if (!ConfigurationContainer.isCompatibleActivityType(topNonFinishingActivity.getActivityType(), this.mActivityType)) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -1022146708, 0, (String) null, new Object[]{String.valueOf(task)});
                }
                return false;
            }
            if (topNonFinishingActivity.mIsAliasActivity) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -2028316258, 0, (String) null, new Object[]{String.valueOf(task)});
                }
                return false;
            }
            Intent intent = task.intent;
            Intent intent2 = task.affinityIntent;
            if (intent != null && intent.isDocument()) {
                uri = intent.getData();
            } else if (intent2 != null && intent2.isDocument()) {
                uri = intent2.getData();
            } else {
                uri = null;
                z = false;
                if (!task.isAliasManaged() && (launchTaskIdForAliasManagedTarget = this.mIntent.getLaunchTaskIdForAliasManagedTarget()) != -1) {
                    if (task.mTaskId != launchTaskIdForAliasManagedTarget) {
                        return false;
                    }
                    this.mIdealRecord = topNonFinishingActivity;
                    return true;
                }
                launchTaskIdForSingleInstancePerTask = this.mIntent.getLaunchTaskIdForSingleInstancePerTask();
                if (launchTaskIdForSingleInstancePerTask == -1) {
                    if (task.mTaskId != launchTaskIdForSingleInstancePerTask) {
                        return false;
                    }
                    this.mIdealRecord = topNonFinishingActivity;
                    return true;
                }
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ComponentName componentName = task.realActivity;
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 1192413464, 0, (String) null, new Object[]{String.valueOf(componentName != null ? componentName.flattenToShortString() : ""), String.valueOf(task.rootAffinity), String.valueOf(this.mIntent.getComponent().flattenToShortString()), String.valueOf(this.mTaskAffinity)});
                }
                ComponentName componentName2 = task.realActivity;
                if (componentName2 != null && componentName2.compareTo(this.cls) == 0 && Objects.equals(this.documentData, uri)) {
                    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 1947936538, 0, (String) null, (Object[]) null);
                    }
                    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 1557732761, 0, (String) null, new Object[]{String.valueOf(this.mIntent), String.valueOf(topNonFinishingActivity.intent)});
                    }
                    this.mIdealRecord = topNonFinishingActivity;
                    return true;
                }
                if (intent2 != null && intent2.getComponent() != null && intent2.getComponent().compareTo(this.cls) == 0 && Objects.equals(this.documentData, uri)) {
                    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 1947936538, 0, (String) null, (Object[]) null);
                    }
                    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 1557732761, 0, (String) null, new Object[]{String.valueOf(this.mIntent), String.valueOf(topNonFinishingActivity.intent)});
                    }
                    this.mIdealRecord = topNonFinishingActivity;
                    return true;
                }
                if (!this.isDocument && !z && this.mIdealRecord == null && this.mCandidateRecord == null && (str = task.rootAffinity) != null) {
                    if (!str.equals(this.mTaskAffinity) || !task.isSameRequiredDisplayCategory(this.mInfo) || topNonFinishingActivity.mAliasChild) {
                        return false;
                    }
                    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, 2039056415, 0, (String) null, (Object[]) null);
                    }
                    this.mCandidateRecord = topNonFinishingActivity;
                } else if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -775004869, 0, (String) null, new Object[]{String.valueOf(task)});
                }
                return false;
            }
            z = true;
            if (!task.isAliasManaged()) {
            }
            launchTaskIdForSingleInstancePerTask = this.mIntent.getLaunchTaskIdForSingleInstancePerTask();
            if (launchTaskIdForSingleInstancePerTask == -1) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(WindowState windowState) {
        if (windowState.mHasSurface) {
            try {
                windowState.mClient.closeSystemDialogs(this.mCloseSystemDialogsReason);
            } catch (RemoteException unused) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RootWindowContainer(WindowManagerService windowManagerService) {
        super(windowManagerService);
        this.mLastWindowFreezeSource = null;
        this.mScreenBrightnessOverride = Float.NaN;
        this.mUserActivityTimeout = -1L;
        this.mScreenDimDuration = -1L;
        this.mUpdateRotation = false;
        this.mObscureApplicationContentOnSecondaryDisplays = false;
        this.mSustainedPerformanceModeEnabled = false;
        this.mSustainedPerformanceModeCurrent = false;
        this.mOrientationChangeComplete = true;
        this.mWallpaperActionPending = false;
        this.mTopFocusedDisplayId = -1;
        this.mTopFocusedAppByProcess = new ArrayMap();
        this.mDeXUserActivityTimeout = -1L;
        this.mDisplayAccessUIDs = new SparseArray();
        this.mDisplayTransactions = new SparseArray();
        this.mUserRootTaskInFront = new SparseIntArray(2);
        this.mSleepTokens = new SparseArray();
        this.mDefaultMinSizeOfResizeableTaskDp = -1;
        this.mTaskLayersChanged = true;
        this.mRankTaskLayersRunnable = new RankTaskLayersRunnable();
        this.mAttachApplicationHelper = new AttachApplicationHelper();
        this.mDestroyAllActivitiesRunnable = new RunnableC29661();
        this.mDestroyTargetAllActivitiesRunnable = new mDestroyTargetAllActivities();
        this.mTmpFindTaskResult = new FindTaskResult();
        this.mCloseSystemDialogsConsumer = new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda37
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$new$0((WindowState) obj);
            }
        };
        this.mFinishDisabledPackageActivitiesHelper = new FinishDisabledPackageActivitiesHelper();
        this.mHandler = new MyHandler(windowManagerService.f1749mH.getLooper());
        ActivityTaskManagerService activityTaskManagerService = windowManagerService.mAtmService;
        this.mService = activityTaskManagerService;
        ActivityTaskSupervisor activityTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mTaskSupervisor = activityTaskSupervisor;
        activityTaskSupervisor.mRootWindowContainer = this;
        Objects.requireNonNull(activityTaskManagerService);
        this.mDisplayOffTokenAcquirer = activityTaskManagerService.new SleepTokenAcquirerImpl("Display-off");
        this.mDeviceStateController = new DeviceStateController(windowManagerService.mContext, windowManagerService.mGlobalLock);
        this.mDisplayRotationCoordinator = new DisplayRotationCoordinator();
    }

    public boolean updateFocusedWindowLocked(int i, boolean z) {
        this.mTopFocusedAppByProcess.clear();
        boolean z2 = false;
        int i2 = -1;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            DisplayContent displayContent = (DisplayContent) this.mChildren.get(size);
            z2 |= displayContent.updateFocusedWindowLocked(i, z, i2);
            WindowState windowState = displayContent.mCurrentFocus;
            if (windowState != null) {
                int i3 = windowState.mSession.mPid;
                if (this.mTopFocusedAppByProcess.get(Integer.valueOf(i3)) == null) {
                    this.mTopFocusedAppByProcess.put(Integer.valueOf(i3), windowState.mActivityRecord);
                }
                if (i2 == -1) {
                    i2 = displayContent.getDisplayId();
                }
            } else if (i2 == -1 && displayContent.mFocusedApp != null) {
                i2 = displayContent.getDisplayId();
            }
        }
        if (i2 == -1) {
            i2 = 0;
        }
        if (this.mTopFocusedDisplayId != i2) {
            this.mTopFocusedDisplayId = i2;
            this.mWmService.mInputManager.setFocusedDisplay(i2);
            this.mWmService.mPolicy.setTopFocusedDisplay(i2);
            this.mWmService.mAccessibilityController.setFocusedDisplay(i2);
            if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 312030608, 1, (String) null, new Object[]{Long.valueOf(i2)});
            }
            this.mService.mMultiTaskingController.notifyFocusedDisplayChangedLocked(i2);
        }
        if (z2) {
            this.mWmService.mExt.mPolicyExt.onFocusChangedLw(getDisplayContent(i2).mCurrentFocus, i2);
        }
        if (CoreRune.FW_TSP_STATE_CONTROLLER && z2 && i2 == 0) {
            this.mWmService.mExt.mTspStateController.updateWindowPolicy(getDisplayContent(0).mCurrentFocus);
        }
        return z2;
    }

    public DisplayContent getTopFocusedDisplayContent() {
        DisplayContent displayContent = getDisplayContent(this.mTopFocusedDisplayId);
        return displayContent != null ? displayContent : getDisplayContent(0);
    }

    @Override // com.android.server.wm.WindowContainer
    public void onChildPositionChanged(WindowContainer windowContainer) {
        this.mWmService.updateFocusedWindowLocked(0, !r3.mPerDisplayFocusEnabled);
        this.mTaskSupervisor.updateTopResumedActivityIfNeeded("onChildPositionChanged");
    }

    public void onSettingsRetrieved() {
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            DisplayContent displayContent = (DisplayContent) this.mChildren.get(i);
            if (this.mWmService.mDisplayWindowSettings.updateSettingsForDisplay(displayContent)) {
                displayContent.reconfigureDisplayLocked();
                if (displayContent.isDefaultDisplay) {
                    this.mWmService.mAtmService.updateConfigurationLocked(this.mWmService.computeNewConfiguration(displayContent.getDisplayId()), null, false);
                }
            }
        }
    }

    public boolean isLayoutNeeded() {
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            if (((DisplayContent) this.mChildren.get(i)).isLayoutNeeded()) {
                return true;
            }
        }
        return false;
    }

    public void getWindowsByName(ArrayList arrayList, String str) {
        int i;
        try {
            i = Integer.parseInt(str, 16);
            str = null;
        } catch (RuntimeException unused) {
            i = 0;
        }
        getWindowsByName(arrayList, str, i);
    }

    public final void getWindowsByName(final ArrayList arrayList, final String str, final int i) {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda55
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$getWindowsByName$1(str, arrayList, i, (WindowState) obj);
            }
        }, true);
    }

    public static /* synthetic */ void lambda$getWindowsByName$1(String str, ArrayList arrayList, int i, WindowState windowState) {
        if (str != null) {
            if (windowState.mAttrs.getTitle().toString().contains(str)) {
                arrayList.add(windowState);
            }
        } else if (System.identityHashCode(windowState) == i) {
            arrayList.add(windowState);
        }
    }

    public ActivityRecord getActivityRecord(IBinder iBinder) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = ((DisplayContent) this.mChildren.get(size)).getActivityRecord(iBinder);
            if (activityRecord != null) {
                return activityRecord;
            }
        }
        return null;
    }

    public WindowToken getWindowToken(IBinder iBinder) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowToken windowToken = ((DisplayContent) this.mChildren.get(size)).getWindowToken(iBinder);
            if (windowToken != null) {
                return windowToken;
            }
        }
        return null;
    }

    public DisplayContent getWindowTokenDisplay(WindowToken windowToken) {
        if (windowToken == null) {
            return null;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            DisplayContent displayContent = (DisplayContent) this.mChildren.get(size);
            if (displayContent.getWindowToken(windowToken.token) == windowToken) {
                return displayContent;
            }
        }
        return null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void dispatchConfigurationToChild(DisplayContent displayContent, Configuration configuration) {
        if (displayContent.isDefaultDisplay) {
            displayContent.performDisplayOverrideConfigUpdate(configuration);
        } else {
            displayContent.onConfigurationChanged(configuration);
        }
    }

    public void refreshSecureSurfaceState() {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$refreshSecureSurfaceState$2((WindowState) obj);
            }
        }, true);
    }

    public static /* synthetic */ void lambda$refreshSecureSurfaceState$2(WindowState windowState) {
        if (windowState.mHasSurface) {
            windowState.mWinAnimator.setSecureLocked(windowState.isSecureLocked());
        }
    }

    public void updateHiddenWhileSuspendedState(final ArraySet arraySet, final boolean z) {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$updateHiddenWhileSuspendedState$3(arraySet, z, (WindowState) obj);
            }
        }, false);
    }

    public static /* synthetic */ void lambda$updateHiddenWhileSuspendedState$3(ArraySet arraySet, boolean z, WindowState windowState) {
        if (arraySet.contains(windowState.getOwningPackage())) {
            windowState.setHiddenWhileSuspended(z);
        }
    }

    public void updateHiddenWhileProfileLockedStateLocked(final int i, final boolean z) {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda34
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$updateHiddenWhileProfileLockedStateLocked$4(i, z, (WindowState) obj);
            }
        }, false);
    }

    public static /* synthetic */ void lambda$updateHiddenWhileProfileLockedStateLocked$4(int i, boolean z, WindowState windowState) {
        if (i == UserHandle.getUserId(windowState.getOwningUid())) {
            windowState.setHiddenWhileProfileLockedStateLocked(z);
        }
    }

    public void updateAppOpsState() {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda52
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WindowState) obj).updateAppOpsState();
            }
        }, false);
    }

    public static /* synthetic */ boolean lambda$canShowStrictModeViolation$6(int i, WindowState windowState) {
        return windowState.mSession.mPid == i && windowState.isVisible();
    }

    public boolean canShowStrictModeViolation(final int i) {
        return getWindow(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$canShowStrictModeViolation$6;
                lambda$canShowStrictModeViolation$6 = RootWindowContainer.lambda$canShowStrictModeViolation$6(i, (WindowState) obj);
                return lambda$canShowStrictModeViolation$6;
            }
        }) != null;
    }

    public void closeSystemDialogs(String str) {
        this.mCloseSystemDialogsReason = str;
        forAllWindows(this.mCloseSystemDialogsConsumer, false);
    }

    public void closeSystemDialogs(String str, int i) {
        DisplayContent displayContent = getDisplayContent(i);
        if (displayContent == null) {
            Slog.e(StartingSurfaceController.TAG, "closeSystemDialogs: cannot find display #" + i);
            return;
        }
        this.mCloseSystemDialogsReason = str;
        displayContent.forAllWindows(this.mCloseSystemDialogsConsumer, false);
    }

    public boolean hasPendingLayoutChanges(WindowAnimator windowAnimator) {
        int size = this.mChildren.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            int i2 = ((DisplayContent) this.mChildren.get(i)).pendingLayoutChanges;
            if ((i2 & 4) != 0) {
                windowAnimator.mBulkUpdateParams |= 2;
            }
            if (i2 != 0) {
                z = true;
            }
        }
        return z;
    }

    public boolean reclaimSomeSurfaceMemory(WindowStateAnimator windowStateAnimator, String str, boolean z) {
        boolean z2;
        WindowSurfaceController windowSurfaceController = windowStateAnimator.mSurfaceController;
        EventLogTags.writeWmNoSurfaceMemory(windowStateAnimator.mWin.toString(), windowStateAnimator.mSession.mPid, str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Slog.i(StartingSurfaceController.TAG, "Out of memory for surface!  Looking for leaks...");
            int size = this.mChildren.size();
            boolean z3 = false;
            for (int i = 0; i < size; i++) {
                z3 |= ((DisplayContent) this.mChildren.get(i)).destroyLeakedSurfaces();
            }
            if (z3) {
                z2 = false;
            } else {
                Slog.w(StartingSurfaceController.TAG, "No leaked surfaces; killing applications!");
                final SparseIntArray sparseIntArray = new SparseIntArray();
                z2 = false;
                for (int i2 = 0; i2 < size; i2++) {
                    ((DisplayContent) this.mChildren.get(i2)).forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda19
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RootWindowContainer.this.lambda$reclaimSomeSurfaceMemory$7(sparseIntArray, (WindowState) obj);
                        }
                    }, false);
                    if (sparseIntArray.size() > 0) {
                        int size2 = sparseIntArray.size();
                        int[] iArr = new int[size2];
                        for (int i3 = 0; i3 < size2; i3++) {
                            iArr[i3] = sparseIntArray.keyAt(i3);
                        }
                        try {
                            try {
                                if (this.mWmService.mActivityManager.killPids(iArr, "Free memory", z)) {
                                    z2 = true;
                                }
                            } catch (RemoteException unused) {
                            }
                        } catch (RemoteException unused2) {
                        }
                    }
                }
            }
            if (z3 || z2) {
                Slog.w(StartingSurfaceController.TAG, "Looks like we have reclaimed some memory, clearing surface for retry.");
                if (windowSurfaceController != null) {
                    if (ProtoLogCache.WM_SHOW_SURFACE_ALLOC_enabled) {
                        ProtoLogImpl.i(ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 399841913, 0, (String) null, new Object[]{String.valueOf(windowStateAnimator.mWin)});
                    }
                    SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get();
                    windowStateAnimator.destroySurface(transaction);
                    transaction.apply();
                    ActivityRecord activityRecord = windowStateAnimator.mWin.mActivityRecord;
                    if (activityRecord != null) {
                        activityRecord.removeStartingWindow();
                    }
                }
                try {
                    windowStateAnimator.mWin.mClient.dispatchGetNewSurface();
                } catch (RemoteException unused3) {
                }
            }
            return z3 || z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reclaimSomeSurfaceMemory$7(SparseIntArray sparseIntArray, WindowState windowState) {
        if (this.mWmService.mForceRemoves.contains(windowState)) {
            return;
        }
        WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
        if (windowStateAnimator.mSurfaceController != null) {
            int i = windowStateAnimator.mSession.mPid;
            sparseIntArray.append(i, i);
        }
    }

    public void performSurfacePlacement() {
        Trace.traceBegin(32L, "performSurfacePlacement");
        try {
            performSurfacePlacementNoTrace();
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public void performSurfacePlacementNoTrace() {
        WindowManagerService windowManagerService = this.mWmService;
        if (windowManagerService.mFocusMayChange) {
            windowManagerService.mFocusMayChange = false;
            windowManagerService.updateFocusedWindowLocked(3, false);
        }
        float f = Float.NaN;
        this.mScreenBrightnessOverride = Float.NaN;
        this.mUserActivityTimeout = -1L;
        this.mScreenDimDuration = -1L;
        this.mObscureApplicationContentOnSecondaryDisplays = false;
        this.mSustainedPerformanceModeCurrent = false;
        WindowManagerService windowManagerService2 = this.mWmService;
        windowManagerService2.mTransactionSequence++;
        this.mDeXUserActivityTimeout = -1L;
        DisplayContent defaultDisplayContentLocked = windowManagerService2.getDefaultDisplayContentLocked();
        WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
        Trace.traceBegin(32L, "applySurfaceChanges");
        this.mWmService.openSurfaceTransaction();
        try {
            try {
                applySurfaceChangesTransaction();
            } catch (RuntimeException e) {
                Slog.wtf(StartingSurfaceController.TAG, "Unhandled exception in Window Manager", e);
            }
            this.mWmService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
            this.mWmService.mAtmService.mTaskFragmentOrganizerController.dispatchPendingEvents();
            this.mWmService.mSyncEngine.onSurfacePlacement();
            this.mWmService.mAnimator.executeAfterPrepareSurfacesRunnables();
            checkAppTransitionReady(windowSurfacePlacer);
            RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
            if (recentsAnimationController != null) {
                recentsAnimationController.checkAnimationReady(defaultDisplayContentLocked.mWallpaperController);
            }
            this.mWmService.mAtmService.mBackNavigationController.checkAnimationReady(defaultDisplayContentLocked.mWallpaperController);
            for (int i = 0; i < this.mChildren.size(); i++) {
                DisplayContent displayContent = (DisplayContent) this.mChildren.get(i);
                if (displayContent.mWallpaperMayChange) {
                    if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WALLPAPER, 535103992, 0, (String) null, (Object[]) null);
                    }
                    displayContent.pendingLayoutChanges |= 4;
                }
            }
            WindowManagerService windowManagerService3 = this.mWmService;
            if (windowManagerService3.mFocusMayChange) {
                windowManagerService3.mFocusMayChange = false;
                windowManagerService3.updateFocusedWindowLocked(2, false);
            }
            if (isLayoutNeeded()) {
                defaultDisplayContentLocked.pendingLayoutChanges |= 1;
            }
            handleResizingWindows();
            if (this.mWmService.mDisplayFrozen && ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -666510420, 3, (String) null, new Object[]{Boolean.valueOf(this.mOrientationChangeComplete)});
            }
            if (this.mOrientationChangeComplete) {
                WindowManagerService windowManagerService4 = this.mWmService;
                if (windowManagerService4.mWindowsFreezingScreen != 0) {
                    windowManagerService4.mWindowsFreezingScreen = 0;
                    windowManagerService4.mLastFinishedFreezeSource = this.mLastWindowFreezeSource;
                    windowManagerService4.f1749mH.removeMessages(11);
                }
                this.mWmService.stopFreezingDisplayLocked();
            }
            int size = this.mWmService.mDestroySurface.size();
            if (size > 0) {
                do {
                    size--;
                    WindowState windowState = (WindowState) this.mWmService.mDestroySurface.get(size);
                    windowState.mDestroying = false;
                    DisplayContent displayContent2 = windowState.getDisplayContent();
                    if (displayContent2.mInputMethodWindow == windowState) {
                        displayContent2.setInputMethodWindowLocked(null);
                    }
                    if (displayContent2.mWallpaperController.isWallpaperTarget(windowState)) {
                        displayContent2.pendingLayoutChanges |= 4;
                    }
                    windowState.destroySurfaceUnchecked();
                } while (size > 0);
                this.mWmService.mDestroySurface.clear();
            }
            for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                DisplayContent displayContent3 = (DisplayContent) this.mChildren.get(i2);
                if (displayContent3.pendingLayoutChanges != 0) {
                    displayContent3.setLayoutNeeded();
                }
            }
            if (!this.mWmService.mDisplayFrozen) {
                float f2 = this.mScreenBrightnessOverride;
                if (f2 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f2 <= 1.0f) {
                    f = f2;
                }
                this.mHandler.obtainMessage(1, Float.floatToIntBits(f), 0).sendToTarget();
                this.mHandler.obtainMessage(2, Long.valueOf(this.mUserActivityTimeout)).sendToTarget();
                this.mHandler.obtainMessage(10, Long.valueOf(this.mDeXUserActivityTimeout)).sendToTarget();
                this.mHandler.obtainMessage(11, Long.valueOf(this.mScreenDimDuration)).sendToTarget();
            }
            boolean z = this.mSustainedPerformanceModeCurrent;
            if (z != this.mSustainedPerformanceModeEnabled) {
                this.mSustainedPerformanceModeEnabled = z;
                this.mWmService.mPowerManagerInternal.setPowerMode(2, z);
            }
            if (this.mUpdateRotation) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1103115659, 0, (String) null, (Object[]) null);
                }
                this.mUpdateRotation = updateRotationUnchecked();
            }
            if (!this.mWmService.mWaitingForDrawnCallbacks.isEmpty() || (this.mOrientationChangeComplete && !isLayoutNeeded() && !this.mUpdateRotation)) {
                this.mWmService.checkDrawnWindowsLocked();
            }
            forAllDisplays(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda40
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RootWindowContainer.lambda$performSurfacePlacementNoTrace$8((DisplayContent) obj);
                }
            });
            this.mWmService.enableScreenIfNeededLocked();
            this.mWmService.scheduleAnimationLocked();
        } finally {
            this.mWmService.closeSurfaceTransaction("performLayoutAndPlaceSurfaces");
            Trace.traceEnd(32L);
        }
    }

    public static /* synthetic */ void lambda$performSurfacePlacementNoTrace$8(DisplayContent displayContent) {
        displayContent.getInputMonitor().updateInputWindowsLw(true);
        displayContent.updateSystemGestureExclusion();
        displayContent.updateKeepClearAreas();
        displayContent.updateTouchExcludeRegion();
    }

    public final void checkAppTransitionReady(WindowSurfacePlacer windowSurfacePlacer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            DisplayContent displayContent = (DisplayContent) this.mChildren.get(size);
            if (displayContent.mAppTransition.isReady()) {
                displayContent.mAppTransitionController.handleAppTransitionReady();
            }
            if (displayContent.mAppTransition.isRunning() && !displayContent.isAppTransitioning()) {
                displayContent.handleAnimatingStoppedAndTransition();
            }
        }
    }

    public final void applySurfaceChangesTransaction() {
        DisplayContent displayContent = this.mDefaultDisplay;
        DisplayInfo displayInfo = displayContent.getDisplayInfo();
        int i = displayInfo.logicalWidth;
        int i2 = displayInfo.logicalHeight;
        SurfaceControl.Transaction syncTransaction = displayContent.getSyncTransaction();
        Watermark watermark = this.mWmService.mWatermark;
        if (watermark != null) {
            watermark.positionSurface(i, i2, syncTransaction);
        }
        StrictModeFlash strictModeFlash = this.mWmService.mStrictModeFlash;
        if (strictModeFlash != null) {
            strictModeFlash.positionSurface(i, i2, syncTransaction);
        }
        EmulatorDisplayOverlay emulatorDisplayOverlay = this.mWmService.mEmulatorDisplayOverlay;
        if (emulatorDisplayOverlay != null) {
            emulatorDisplayOverlay.positionSurface(i, i2, displayContent.getRotation(), syncTransaction);
        }
        int size = this.mChildren.size();
        for (int i3 = 0; i3 < size; i3++) {
            DisplayContent displayContent2 = (DisplayContent) this.mChildren.get(i3);
            displayContent2.applySurfaceChangesTransaction();
            this.mDisplayTransactions.append(displayContent2.mDisplayId, displayContent2.getSyncTransaction());
        }
        this.mWmService.mDisplayManagerInternal.performTraversal(syncTransaction, this.mDisplayTransactions);
        this.mDisplayTransactions.clear();
        if (syncTransaction != displayContent.mSyncTransaction) {
            SurfaceControl.mergeToGlobalTransaction(syncTransaction);
        }
    }

    public final void handleResizingWindows() {
        for (int size = this.mWmService.mResizingWindows.size() - 1; size >= 0; size--) {
            WindowState windowState = (WindowState) this.mWmService.mResizingWindows.get(size);
            if (!windowState.mAppFreezing && !windowState.getDisplayContent().mWaitingForConfig) {
                windowState.reportResized();
                this.mWmService.mResizingWindows.remove(size);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean handleNotObscuredLocked(WindowState windowState, boolean z, boolean z2) {
        boolean z3;
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        int i = layoutParams.flags;
        boolean isOnScreen = windowState.isOnScreen();
        boolean isDisplayed = windowState.isDisplayed();
        int i2 = layoutParams.privateFlags;
        if (this.mWmService.mExt.mExtraDisplayPolicy.shouldNotTopDisplay(windowState.getDisplayId())) {
            return windowState.mHasSurface && isDisplayed;
        }
        if (ProtoLogCache.WM_DEBUG_KEEP_SCREEN_ON_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, -481924678, 508, (String) null, new Object[]{String.valueOf(windowState), Boolean.valueOf(windowState.mHasSurface), Boolean.valueOf(isOnScreen), Boolean.valueOf(windowState.isDisplayed()), Long.valueOf(windowState.mAttrs.userActivityTimeout)});
        }
        if (windowState.mHasSurface && isOnScreen) {
            if (windowState.getDisplayId() != 2 || windowState.mAttrs.userActivityTimeout < 0 || this.mDeXUserActivityTimeout >= 0) {
                if (!z2) {
                    long j = windowState.mAttrs.userActivityTimeout;
                    if (j >= 0 && this.mUserActivityTimeout < 0) {
                        this.mUserActivityTimeout = j;
                        if (ProtoLogCache.WM_DEBUG_KEEP_SCREEN_ON_enabled) {
                            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 221540118, 1, (String) null, new Object[]{Long.valueOf(j)});
                        }
                    }
                }
            } else if (this.mWmService.mPolicy.isKeyguardShowing()) {
                this.mDeXUserActivityTimeout = windowState.mAttrs.userActivityTimeout;
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d(StartingSurfaceController.TAG, "mDeXUserActivityTimeout set to " + this.mDeXUserActivityTimeout);
                }
            }
            if (!z2) {
                long j2 = windowState.mAttrs.screenDimDuration;
                if (j2 >= 0 && this.mScreenDimDuration < 0) {
                    this.mScreenDimDuration = j2;
                }
            }
            if (!z2 && layoutParams.userActivityTimeout == 5226) {
                layoutParams.userActivityTimeout = -1L;
                layoutParams.screenDimDuration = -1L;
            }
        }
        if (!windowState.mHasSurface || !isDisplayed) {
            return false;
        }
        if (!z2 && windowState.mAttrs.screenBrightness >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && Float.isNaN(this.mScreenBrightnessOverride) && windowState.getDisplayId() != 2) {
            this.mScreenBrightnessOverride = windowState.mAttrs.screenBrightness;
        }
        int i3 = layoutParams.type;
        DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent != null && displayContent.isDefaultDisplay) {
            if (windowState.isDreamWindow() || this.mWmService.mPolicy.isKeyguardShowing()) {
                this.mObscureApplicationContentOnSecondaryDisplays = true;
            }
        } else if (displayContent == null || (this.mObscureApplicationContentOnSecondaryDisplays && !displayContent.isKeyguardAlwaysUnlocked() && (!z || i3 != 2009))) {
            z3 = false;
            if ((65536 & i2) != 0) {
                return z3;
            }
            this.mSustainedPerformanceModeCurrent = true;
            return z3;
        }
        z3 = true;
        if ((65536 & i2) != 0) {
        }
    }

    public boolean updateRotationUnchecked() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((DisplayContent) this.mChildren.get(size)).getDisplayRotation().updateRotationAndSendNewConfigIfChanged()) {
                z = true;
            }
        }
        return z;
    }

    public boolean copyAnimToLayoutParams() {
        boolean z;
        WindowManagerService windowManagerService = this.mWmService;
        WindowAnimator windowAnimator = windowManagerService.mAnimator;
        int i = windowAnimator.mBulkUpdateParams;
        if ((i & 1) != 0) {
            this.mUpdateRotation = true;
            z = true;
        } else {
            z = false;
        }
        if (this.mOrientationChangeComplete) {
            this.mLastWindowFreezeSource = windowAnimator.mLastWindowFreezeSource;
            if (windowManagerService.mWindowsFreezingScreen != 0) {
                z = true;
            }
        }
        if ((i & 2) != 0) {
            this.mWallpaperActionPending = true;
        }
        return z;
    }

    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                RootWindowContainer.this.mWmService.mPowerManagerInternal.setScreenBrightnessOverrideFromWindowManager(Float.intBitsToFloat(message.arg1));
                return;
            }
            if (i == 2) {
                RootWindowContainer.this.mWmService.mPowerManagerInternal.setUserActivityTimeoutOverrideFromWindowManager(((Long) message.obj).longValue());
                return;
            }
            if (i != 3) {
                if (i == 10) {
                    RootWindowContainer.this.mWmService.mPowerManagerInternal.setUserActivityTimeoutForDexOverrideFromWindowManager(((Long) message.obj).longValue());
                    return;
                } else {
                    if (i != 11) {
                        return;
                    }
                    RootWindowContainer.this.mWmService.mPowerManagerInternal.setScreenDimDurationOverrideFromWindowManager(((Long) message.obj).longValue());
                    return;
                }
            }
            WindowManagerGlobalLock windowManagerGlobalLock = RootWindowContainer.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer.this.sendSleepTransition((DisplayContent) message.obj);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public void dumpDisplayContents(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER DISPLAY CONTENTS (dumpsys window displays)");
        if (this.mWmService.mDisplayReady) {
            int size = this.mChildren.size();
            for (int i = 0; i < size; i++) {
                ((DisplayContent) this.mChildren.get(i)).dump(printWriter, "  ", true);
            }
            return;
        }
        printWriter.println("  NO DISPLAY");
    }

    public void dumpTopFocusedDisplayId(PrintWriter printWriter) {
        printWriter.print("  mTopFocusedDisplayId=");
        printWriter.println(this.mTopFocusedDisplayId);
    }

    public void dumpLayoutNeededDisplayIds(PrintWriter printWriter) {
        if (isLayoutNeeded()) {
            printWriter.print("  mLayoutNeeded on displays=");
            int size = this.mChildren.size();
            for (int i = 0; i < size; i++) {
                DisplayContent displayContent = (DisplayContent) this.mChildren.get(i);
                if (displayContent.isLayoutNeeded()) {
                    printWriter.print(displayContent.getDisplayId());
                }
            }
            printWriter.println();
        }
    }

    public void dumpWindowsNoHeader(final PrintWriter printWriter, final boolean z, final ArrayList arrayList) {
        final int[] iArr = new int[1];
        forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda54
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$dumpWindowsNoHeader$9(arrayList, printWriter, iArr, z, (WindowState) obj);
            }
        }, true);
    }

    public static /* synthetic */ void lambda$dumpWindowsNoHeader$9(ArrayList arrayList, PrintWriter printWriter, int[] iArr, boolean z, WindowState windowState) {
        if (arrayList == null || arrayList.contains(windowState)) {
            printWriter.println("  Window #" + iArr[0] + " " + windowState + XmlUtils.STRING_ARRAY_SEPARATOR);
            windowState.dump(printWriter, "    ", z || arrayList != null);
            iArr[0] = iArr[0] + 1;
        }
    }

    public void dumpTokens(PrintWriter printWriter, boolean z) {
        printWriter.println("  All tokens:");
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((DisplayContent) this.mChildren.get(size)).dumpTokens(printWriter, z);
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || isVisible()) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            this.mTaskSupervisor.getKeyguardController().dumpDebug(protoOutputStream, 1146756268037L);
            protoOutputStream.write(1133871366150L, this.mTaskSupervisor.mRecentTasks.isRecentsComponentHomeActivity(this.mCurrentUser));
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeChild(DisplayContent displayContent) {
        super.removeChild((WindowContainer) displayContent);
        if (this.mTopFocusedDisplayId == displayContent.getDisplayId()) {
            this.mWmService.updateFocusedWindowLocked(0, true);
        }
    }

    public void forAllDisplays(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            consumer.accept((DisplayContent) this.mChildren.get(size));
        }
    }

    public void forAllDisplayPolicies(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            consumer.accept(((DisplayContent) this.mChildren.get(size)).getDisplayPolicy());
        }
    }

    public WindowState getCurrentInputMethodWindow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowState windowState = ((DisplayContent) this.mChildren.get(size)).mInputMethodWindow;
            if (windowState != null) {
                return windowState;
            }
        }
        return null;
    }

    public void getDisplayContextsWithNonToastVisibleWindows(final int i, List list) {
        if (list == null) {
            return;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            DisplayContent displayContent = (DisplayContent) this.mChildren.get(size);
            if (displayContent.getWindow(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda38
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getDisplayContextsWithNonToastVisibleWindows$10;
                    lambda$getDisplayContextsWithNonToastVisibleWindows$10 = RootWindowContainer.lambda$getDisplayContextsWithNonToastVisibleWindows$10(i, (WindowState) obj);
                    return lambda$getDisplayContextsWithNonToastVisibleWindows$10;
                }
            }) != null) {
                list.add(displayContent.getDisplayUiContext());
            }
        }
    }

    public static /* synthetic */ boolean lambda$getDisplayContextsWithNonToastVisibleWindows$10(int i, WindowState windowState) {
        return i == windowState.mSession.mPid && windowState.isVisibleNow() && windowState.mAttrs.type != 2005;
    }

    public Context getDisplayUiContext(int i) {
        if (getDisplayContent(i) != null) {
            return getDisplayContent(i).getDisplayUiContext();
        }
        return null;
    }

    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
        DisplayManager displayManager = (DisplayManager) this.mService.mContext.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this, this.mService.mUiHandler);
        this.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        for (Display display : this.mDisplayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_BUILT_IN")) {
            DisplayContent displayContent = new DisplayContent(display, this, this.mDeviceStateController);
            addChild(displayContent, Integer.MIN_VALUE);
            if (displayContent.mDisplayId == 0) {
                this.mDefaultDisplay = displayContent;
            }
        }
        TaskDisplayArea defaultTaskDisplayArea = getDefaultTaskDisplayArea();
        defaultTaskDisplayArea.getOrCreateRootHomeTask(true);
        positionChildAt(Integer.MAX_VALUE, defaultTaskDisplayArea.mDisplayContent, false);
    }

    public void onDisplayManagerReceivedDeviceState(int i) {
        this.mDeviceStateController.onDeviceStateReceivedByDisplayManager(i);
    }

    public DisplayContent getDefaultDisplay() {
        return this.mDefaultDisplay;
    }

    public DisplayRotationCoordinator getDisplayRotationCoordinator() {
        return this.mDisplayRotationCoordinator;
    }

    public TaskDisplayArea getDefaultTaskDisplayArea() {
        return this.mDefaultDisplay.getDefaultTaskDisplayArea();
    }

    public DisplayContent getDisplayContent(String str) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent.mDisplay.isValid() && displayContent.mDisplay.getUniqueId().equals(str)) {
                return displayContent;
            }
        }
        return null;
    }

    public DisplayContent getDisplayContent(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent.mDisplayId == i) {
                return displayContent;
            }
        }
        return null;
    }

    public DisplayContent getDisplayContentOrCreate(int i) {
        Display display;
        DisplayContent displayContent = getDisplayContent(i);
        if (displayContent != null) {
            return displayContent;
        }
        DisplayManager displayManager = this.mDisplayManager;
        if (displayManager == null || (display = displayManager.getDisplay(i)) == null) {
            return null;
        }
        DisplayContent displayContent2 = new DisplayContent(display, this, this.mDeviceStateController);
        addChild(displayContent2, Integer.MIN_VALUE);
        return displayContent2;
    }

    public ActivityRecord getDefaultDisplayHomeActivityForUser(int i) {
        return getDefaultTaskDisplayArea().getHomeActivityForUser(i);
    }

    public boolean startHomeOnAllDisplays(int i, String str) {
        boolean z = false;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            z |= startHomeOnDisplay(i, str, ((DisplayContent) getChildAt(childCount)).mDisplayId);
        }
        return z;
    }

    public void startHomeOnEmptyDisplays(final String str) {
        forAllTaskDisplayAreas(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$startHomeOnEmptyDisplays$11(str, (TaskDisplayArea) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startHomeOnEmptyDisplays$11(String str, TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea.topRunningActivity() == null) {
            startHomeOnTaskDisplayArea(this.mWmService.getUserAssignedToDisplay(taskDisplayArea.getDisplayId()), str, taskDisplayArea, false, false);
        }
    }

    public boolean startHomeOnDisplay(int i, String str, int i2) {
        return startHomeOnDisplay(i, str, i2, false, false);
    }

    public boolean startHomeOnDisplay(int i, String str, int i2, boolean z, boolean z2) {
        return startHomeOnDisplay(i, str, i2, z, z2, 0);
    }

    public boolean startHomeOnDisplay(final int i, final String str, int i2, final boolean z, final boolean z2, final int i3) {
        if (i2 == -1) {
            Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            i2 = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getDisplayId() : 0;
        }
        return ((Boolean) getDisplayContent(i2).reduceOnAllTaskDisplayAreas(new BiFunction() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda35
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Boolean lambda$startHomeOnDisplay$12;
                lambda$startHomeOnDisplay$12 = RootWindowContainer.this.lambda$startHomeOnDisplay$12(i, str, z, z2, i3, (TaskDisplayArea) obj, (Boolean) obj2);
                return lambda$startHomeOnDisplay$12;
            }
        }, Boolean.FALSE)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$startHomeOnDisplay$12(int i, String str, boolean z, boolean z2, int i2, TaskDisplayArea taskDisplayArea, Boolean bool) {
        return Boolean.valueOf(startHomeOnTaskDisplayArea(i, str, taskDisplayArea, z, z2, 0) | bool.booleanValue());
    }

    public boolean startHomeOnTaskDisplayArea(int i, String str, TaskDisplayArea taskDisplayArea, boolean z, boolean z2) {
        return startHomeOnTaskDisplayArea(i, str, taskDisplayArea, z, z2, 0);
    }

    public boolean startHomeOnTaskDisplayArea(int i, String str, TaskDisplayArea taskDisplayArea, boolean z, boolean z2, int i2) {
        Intent homeIntent;
        ActivityInfo resolveHomeActivity;
        if (taskDisplayArea == null) {
            Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null) {
                taskDisplayArea = topDisplayFocusedRootTask.getDisplayArea();
            } else {
                taskDisplayArea = getDefaultTaskDisplayArea();
            }
        }
        int displayId = taskDisplayArea.getDisplayId();
        int dexModeLocked = this.mService.mDexController.getDexModeLocked();
        if ((dexModeLocked == 2 && displayId == 2) || (dexModeLocked == 1 && displayId == 0)) {
            this.mService.mDexController.startDexHomeLocked(displayId);
            return true;
        }
        if (displayId == 2) {
            return false;
        }
        if (taskDisplayArea == getDefaultTaskDisplayArea() || this.mWmService.shouldPlacePrimaryHomeOnDisplay(taskDisplayArea.getDisplayId(), i)) {
            homeIntent = this.mService.getHomeIntent();
            resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        } else if (shouldPlaceSecondaryHomeOnDisplayArea(taskDisplayArea)) {
            Pair resolveSecondaryHomeActivity = resolveSecondaryHomeActivity(i, taskDisplayArea);
            resolveHomeActivity = (ActivityInfo) resolveSecondaryHomeActivity.first;
            homeIntent = (Intent) resolveSecondaryHomeActivity.second;
        } else {
            resolveHomeActivity = null;
            homeIntent = null;
        }
        if (resolveHomeActivity == null || homeIntent == null || !canStartHomeOnDisplayArea(resolveHomeActivity, taskDisplayArea, z)) {
            return false;
        }
        homeIntent.setComponent(new ComponentName(resolveHomeActivity.applicationInfo.packageName, resolveHomeActivity.name));
        homeIntent.setFlags(homeIntent.getFlags() | 268435456);
        if (z2) {
            homeIntent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
            if (this.mWindowManager.getRecentsAnimationController() != null) {
                this.mWindowManager.getRecentsAnimationController().cancelAnimationForHomeStart();
            }
            if (taskDisplayArea.getDisplayId() == 0) {
                this.mService.mMultiTaskingController.minimizeAllTasksLocked(0, true);
            }
            if (CoreRune.MW_SA_LOGGING && taskDisplayArea.getDisplayId() == 0 && taskDisplayArea.isSplitScreenModeActivated()) {
                CoreSaLogger.logForAdvanced("1005", "Tap 'Home' button");
            }
        }
        homeIntent.putExtra("android.intent.extra.EXTRA_START_REASON", str);
        this.mService.getActivityStartController().startHomeActivity(homeIntent, resolveHomeActivity, str + XmlUtils.STRING_ARRAY_SEPARATOR + i + XmlUtils.STRING_ARRAY_SEPARATOR + UserHandle.getUserId(resolveHomeActivity.applicationInfo.uid) + XmlUtils.STRING_ARRAY_SEPARATOR + taskDisplayArea.getDisplayId(), taskDisplayArea);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0036, code lost:
    
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ActivityInfo resolveHomeActivity(int i, Intent intent) {
        ActivityInfo activityInfo;
        ComponentName component = intent.getComponent();
        try {
        } catch (RemoteException unused) {
            activityInfo = null;
        }
        if (component != null) {
            activityInfo = AppGlobals.getPackageManager().getActivityInfo(component, 1024L, i);
        } else {
            ResolveInfo resolveIntent = this.mTaskSupervisor.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mService.mContext.getContentResolver()), i, 1024, Binder.getCallingUid(), Binder.getCallingPid());
            if (resolveIntent != null) {
                activityInfo = resolveIntent.activityInfo;
            }
            activityInfo = null;
        }
        if (activityInfo == null) {
            Slogf.wtf(StartingSurfaceController.TAG, new Exception(), "No home screen found for %s and user %d", intent, Integer.valueOf(i));
            return null;
        }
        ActivityInfo activityInfo2 = new ActivityInfo(activityInfo);
        activityInfo2.applicationInfo = this.mService.getAppInfoForUser(activityInfo2.applicationInfo, i);
        return activityInfo2;
    }

    public Pair resolveSecondaryHomeActivity(int i, TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea == getDefaultTaskDisplayArea()) {
            throw new IllegalArgumentException("resolveSecondaryHomeActivity: Should not be default task container");
        }
        Intent homeIntent = this.mService.getHomeIntent();
        ActivityInfo resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        if (resolveHomeActivity != null) {
            if (ResolverActivity.class.getName().equals(resolveHomeActivity.name)) {
                resolveHomeActivity = null;
            } else {
                if (this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(taskDisplayArea.getDisplayId())) {
                    ActivityTaskManagerService activityTaskManagerService = this.mService;
                    homeIntent = activityTaskManagerService.getSecondaryHomeIntent(activityTaskManagerService.getSysUiServiceComponentLocked().getPackageName());
                } else {
                    homeIntent = this.mService.getSecondaryHomeIntent(resolveHomeActivity.applicationInfo.packageName);
                }
                List resolveActivities = resolveActivities(i, homeIntent);
                int size = resolveActivities.size();
                String str = resolveHomeActivity.name;
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        resolveHomeActivity = null;
                        break;
                    }
                    ResolveInfo resolveInfo = (ResolveInfo) resolveActivities.get(i2);
                    if (resolveInfo.activityInfo.name.equals(str)) {
                        resolveHomeActivity = resolveInfo.activityInfo;
                        break;
                    }
                    i2++;
                }
                if (resolveHomeActivity == null && size > 0) {
                    resolveHomeActivity = ((ResolveInfo) resolveActivities.get(0)).activityInfo;
                }
            }
        }
        if (resolveHomeActivity != null && !canStartHomeOnDisplayArea(resolveHomeActivity, taskDisplayArea, false)) {
            resolveHomeActivity = null;
        }
        if (resolveHomeActivity == null) {
            homeIntent = this.mService.getSecondaryHomeIntent(null);
            resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        }
        return Pair.create(resolveHomeActivity, homeIntent);
    }

    public List resolveActivities(int i, Intent intent) {
        try {
            return AppGlobals.getPackageManager().queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mService.mContext.getContentResolver()), 1024L, i).getList();
        } catch (RemoteException unused) {
            return new ArrayList();
        }
    }

    public boolean resumeHomeActivity(ActivityRecord activityRecord, String str, TaskDisplayArea taskDisplayArea) {
        if (!this.mService.isBooting() && !this.mService.isBooted()) {
            return false;
        }
        if (taskDisplayArea == null) {
            taskDisplayArea = getDefaultTaskDisplayArea();
        }
        TaskDisplayArea taskDisplayArea2 = taskDisplayArea;
        ActivityRecord homeActivity = taskDisplayArea2.getHomeActivity();
        String str2 = str + " resumeHomeActivity";
        if (homeActivity != null && !homeActivity.finishing) {
            homeActivity.moveFocusableActivityToTop(str2);
            return resumeFocusedTasksTopActivities(homeActivity.getRootTask(), activityRecord, null);
        }
        return startHomeOnTaskDisplayArea(this.mWmService.getUserAssignedToDisplay(taskDisplayArea2.getDisplayId()), str2, taskDisplayArea2, false, false);
    }

    public boolean shouldPlaceSecondaryHomeOnDisplayArea(TaskDisplayArea taskDisplayArea) {
        if (getDefaultTaskDisplayArea() == taskDisplayArea) {
            throw new IllegalArgumentException("shouldPlaceSecondaryHomeOnDisplay: Should not be on default task container");
        }
        if (taskDisplayArea == null || !taskDisplayArea.canHostHomeTask()) {
            return false;
        }
        if (taskDisplayArea.getDisplayId() != 0 && !this.mService.mSupportsMultiDisplay) {
            return false;
        }
        if (!this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(taskDisplayArea.getDisplayId())) {
            if (!(Settings.Global.getInt(this.mService.mContext.getContentResolver(), "device_provisioned", 0) != 0) || !StorageManager.isUserKeyUnlocked(this.mCurrentUser)) {
                return false;
            }
        }
        if (taskDisplayArea.getDisplayId() == 2 && this.mService.mDexController.getDexModeLocked() == 2) {
            return true;
        }
        DisplayContent displayContent = taskDisplayArea.getDisplayContent();
        return (displayContent == null || displayContent.isRemoved() || !displayContent.supportsSystemDecorations()) ? false : true;
    }

    public boolean canStartHomeOnDisplayArea(ActivityInfo activityInfo, TaskDisplayArea taskDisplayArea, boolean z) {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mFactoryTest == 1 && activityTaskManagerService.mTopAction == null) {
            return false;
        }
        WindowProcessController processController = activityTaskManagerService.getProcessController(activityInfo.processName, activityInfo.applicationInfo.uid);
        if (!z && processController != null && processController.isInstrumenting()) {
            return false;
        }
        int displayId = taskDisplayArea != null ? taskDisplayArea.getDisplayId() : -1;
        if (displayId != 0 && (displayId == -1 || (displayId != this.mService.mVr2dDisplayId && !this.mWmService.shouldPlacePrimaryHomeOnDisplay(displayId)))) {
            if (!shouldPlaceSecondaryHomeOnDisplayArea(taskDisplayArea)) {
                return false;
            }
            if (displayId == 2 && this.mService.mDexController.getDexModeLocked() == 2) {
                return true;
            }
            int i = activityInfo.launchMode;
            if (!((i == 2 || i == 3) ? false : true)) {
                return false;
            }
        }
        return true;
    }

    public boolean ensureVisibilityAndConfig(ActivityRecord activityRecord, int i, boolean z, boolean z2) {
        WindowProcessController windowProcessController;
        ensureActivitiesVisible(null, 0, false, false);
        if (i == -1) {
            return true;
        }
        DisplayContent displayContent = getDisplayContent(i);
        Configuration updateOrientation = displayContent != null ? displayContent.updateOrientation(activityRecord, true) : null;
        if (activityRecord != null) {
            if (i == 1 && (windowProcessController = activityRecord.app) != null && !windowProcessController.isActivityConfigOverrideAllowed() && activityRecord.getParent() != null) {
                activityRecord.onConfigurationChanged(activityRecord.getParent().getConfiguration());
            }
            activityRecord.reportDescendantOrientationChangeIfNeeded();
        }
        if (activityRecord != null && z && updateOrientation != null) {
            activityRecord.frozenBeforeDestroy = true;
        }
        if (displayContent != null) {
            return displayContent.updateDisplayOverrideConfigurationLocked(updateOrientation, activityRecord, z2, null);
        }
        return true;
    }

    public List getTopVisibleActivities() {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$getTopVisibleActivities$13(arrayList2, topDisplayFocusedRootTask, arrayList, (Task) obj);
            }
        });
        return arrayList;
    }

    public static /* synthetic */ void lambda$getTopVisibleActivities$13(ArrayList arrayList, Task task, ArrayList arrayList2, Task task2) {
        ActivityRecord topNonFinishingActivity;
        ActivityRecord topNonFinishingActivity2;
        if (!task2.shouldBeVisible(null) || (topNonFinishingActivity = task2.getTopNonFinishingActivity()) == null) {
            return;
        }
        arrayList.clear();
        arrayList.add(new ActivityAssistInfo(topNonFinishingActivity));
        Task adjacentTask = topNonFinishingActivity.getTask().getAdjacentTask();
        if (adjacentTask != null && (topNonFinishingActivity2 = adjacentTask.getTopNonFinishingActivity()) != null) {
            arrayList.add(new ActivityAssistInfo(topNonFinishingActivity2));
        }
        if (task2 == task) {
            arrayList2.addAll(0, arrayList);
        } else {
            arrayList2.addAll(arrayList);
        }
    }

    public Task getTopDisplayFocusedRootTask() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            Task focusedRootTask = ((DisplayContent) getChildAt(childCount)).getFocusedRootTask();
            if (focusedRootTask != null) {
                return focusedRootTask;
            }
        }
        return null;
    }

    public ActivityRecord getTopResumedActivity() {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null) {
            return null;
        }
        ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
        return (topResumedActivity == null || topResumedActivity.app == null) ? (ActivityRecord) getItemFromTaskDisplayAreas(new Function() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TaskDisplayArea) obj).getFocusedActivity();
            }
        }) : topResumedActivity;
    }

    public boolean isTopDisplayFocusedRootTask(Task task) {
        return task != null && task == getTopDisplayFocusedRootTask();
    }

    public boolean attachApplication(WindowProcessController windowProcessController) {
        try {
            return this.mAttachApplicationHelper.process(windowProcessController);
        } finally {
            this.mAttachApplicationHelper.reset();
        }
    }

    public void ensureActivitiesVisible(ActivityRecord activityRecord, int i, boolean z) {
        ensureActivitiesVisible(activityRecord, i, z, true);
    }

    public void ensureActivitiesVisible(ActivityRecord activityRecord, int i, boolean z, boolean z2) {
        if (this.mTaskSupervisor.inActivityVisibilityUpdate()) {
            return;
        }
        if (this.mTaskSupervisor.isRootVisibilityUpdateDeferred() && i == 0) {
            return;
        }
        this.mTaskSupervisor.beginActivityVisibilityUpdate();
        try {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                ((DisplayContent) getChildAt(childCount)).ensureActivitiesVisible(activityRecord, i, z, z2);
            }
        } finally {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    public boolean switchUser(final int i, UserState userState) {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        int rootTaskId = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getRootTaskId() : -1;
        removeRootTasksInWindowingModes(2);
        this.mService.mMultiTaskingController.minimizeAllTasksLocked(0, true);
        this.mUserRootTaskInFront.put(this.mCurrentUser, rootTaskId);
        this.mCurrentUser = i;
        this.mTaskSupervisor.mStartingUsers.add(userState);
        forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Task) obj).switchUser(i);
            }
        });
        Task rootTask = getRootTask(this.mUserRootTaskInFront.get(i));
        if (rootTask == null) {
            rootTask = getDefaultTaskDisplayArea().getOrCreateRootHomeTask();
        }
        boolean isActivityTypeHome = rootTask.isActivityTypeHome();
        if (rootTask.isOnHomeDisplay()) {
            rootTask.moveToFront("switchUserOnHomeDisplay");
        } else {
            resumeHomeActivity(null, "switchUserOnOtherDisplay", getDefaultTaskDisplayArea());
        }
        return isActivityTypeHome;
    }

    public void removeUser(int i) {
        this.mUserRootTaskInFront.delete(i);
    }

    public void updateUserRootTask(int i, Task task) {
        if (i != this.mCurrentUser) {
            if (task == null) {
                task = getDefaultTaskDisplayArea().getOrCreateRootHomeTask();
            }
            this.mUserRootTaskInFront.put(i, task.getRootTaskId());
        }
    }

    public void moveRootTaskToTaskDisplayArea(int i, TaskDisplayArea taskDisplayArea, boolean z) {
        Task rootTask = getRootTask(i);
        if (rootTask == null) {
            throw new IllegalArgumentException("moveRootTaskToTaskDisplayArea: Unknown rootTaskId=" + i);
        }
        TaskDisplayArea displayArea = rootTask.getDisplayArea();
        if (displayArea == null) {
            throw new IllegalStateException("moveRootTaskToTaskDisplayArea: rootTask=" + rootTask + " is not attached to any task display area.");
        }
        if (taskDisplayArea == null) {
            throw new IllegalArgumentException("moveRootTaskToTaskDisplayArea: Unknown taskDisplayArea=" + taskDisplayArea);
        }
        if (displayArea == taskDisplayArea) {
            throw new IllegalArgumentException("Trying to move rootTask=" + rootTask + " to its current taskDisplayArea=" + taskDisplayArea);
        }
        rootTask.reparent(taskDisplayArea, z);
        rootTask.resumeNextFocusAfterReparent();
    }

    public void moveRootTaskToDisplay(int i, int i2, boolean z) {
        DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2);
        if (displayContentOrCreate == null) {
            throw new IllegalArgumentException("moveRootTaskToDisplay: Unknown displayId=" + i2);
        }
        moveRootTaskToTaskDisplayArea(i, displayContentOrCreate.getDefaultTaskDisplayArea(), z);
    }

    public void moveActivityToPinnedRootTask(ActivityRecord activityRecord, ActivityRecord activityRecord2, String str) {
        moveActivityToPinnedRootTask(activityRecord, activityRecord2, str, null);
    }

    public void moveActivityToPinnedRootTask(ActivityRecord activityRecord, ActivityRecord activityRecord2, String str, Transition transition) {
        Task build;
        TaskDisplayArea displayArea = activityRecord.getDisplayArea();
        Task task = activityRecord.getTask();
        TransitionController transitionController = task.mTransitionController;
        if (transition == null && !transitionController.isCollecting() && transitionController.getTransitionPlayer() != null) {
            transition = transitionController.createTransition(10);
        }
        transitionController.deferTransitionReady();
        this.mService.deferWindowLayout();
        try {
            Task rootPinnedTask = displayArea.getRootPinnedTask();
            if (rootPinnedTask != null) {
                transitionController.collect(rootPinnedTask);
                removeRootTasksInWindowingModes(2);
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mService.mChangeTransitController.handleEnteringPipIfNeeded(activityRecord, transition);
            }
            if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX) {
                BlurWallpaperLetterbox.onMoveActivityToPinnedRootTask(activityRecord.mDisplayContent, task, activityRecord);
            }
            activityRecord.getDisplayContent().prepareAppTransition(0);
            transitionController.collect(task);
            activityRecord.setWindowingMode(activityRecord.getWindowingMode());
            TaskFragment organizedTaskFragment = activityRecord.getOrganizedTaskFragment();
            TaskFragment taskFragment = activityRecord.getTaskFragment();
            if (task.getNonFinishingActivityCount() == 1) {
                task.maybeApplyLastRecentsAnimationTransaction();
                if (task.getParent() != displayArea) {
                    task.reparent(displayArea, true);
                }
                task.forAllTaskFragments(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda28
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RootWindowContainer.lambda$moveActivityToPinnedRootTask$15((TaskFragment) obj);
                    }
                });
                build = task;
            } else {
                build = new Task.Builder(this.mService).setActivityType(activityRecord.getActivityType()).setOnTop(true).setActivityInfo(activityRecord.info).setParent(displayArea).setIntent(activityRecord.intent).setDeferTaskAppear(true).setHasBeenVisible(true).setWindowingMode(taskFragment.getWindowingMode()).build();
                activityRecord.setLastParentBeforePip(activityRecord2);
                build.setLastNonFullscreenBounds(task.mLastNonFullscreenBounds);
                build.setBoundsUnchecked(taskFragment.getBounds());
                PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = task.mLastRecentsAnimationTransaction;
                if (pictureInPictureSurfaceTransaction != null) {
                    build.setLastRecentsAnimationTransaction(pictureInPictureSurfaceTransaction, task.mLastRecentsAnimationOverlay);
                    task.clearLastRecentsAnimationTransaction(false);
                } else {
                    task.resetSurfaceControlTransforms();
                }
                if (organizedTaskFragment != null && organizedTaskFragment.getNonFinishingActivityCount() == 1 && organizedTaskFragment.getTopNonFinishingActivity() == activityRecord) {
                    organizedTaskFragment.mClearedTaskFragmentForPip = true;
                }
                transitionController.collect(build);
                if (transitionController.isShellTransitionsEnabled()) {
                    build.setWindowingMode(2);
                }
                activityRecord.reparent(build, Integer.MAX_VALUE, str);
                build.maybeApplyLastRecentsAnimationTransaction();
                ActivityRecord topMostActivity = task.getTopMostActivity();
                if (topMostActivity != null && topMostActivity.isState(ActivityRecord.State.STOPPED) && task.getDisplayContent().mAppTransition.containsTransitRequest(4)) {
                    task.getDisplayContent().mClosingApps.add(topMostActivity);
                    topMostActivity.mRequestForceTransition = true;
                }
            }
            build.setWindowingMode(2);
            if (activityRecord.getOptions() != null && activityRecord.getOptions().isLaunchIntoPip()) {
                this.mWindowManager.mTaskSnapshotController.recordSnapshot(task, false);
                build.setBounds(activityRecord.pictureInPictureArgs.getSourceRectHint());
            }
            build.setDeferTaskAppear(false);
            activityRecord.mWaitForEnteringPinnedMode = true;
            activityRecord.supportsEnterPipOnTaskSwitch = false;
            if (organizedTaskFragment != null && organizedTaskFragment.mClearedTaskFragmentForPip && organizedTaskFragment.isTaskVisibleRequested()) {
                this.mService.mTaskFragmentOrganizerController.dispatchPendingInfoChangedEvent(organizedTaskFragment);
            }
            this.mService.continueWindowLayout();
            try {
                ensureActivitiesVisible(null, 0, false);
                if (transition != null) {
                    transitionController.requestStartTransition(transition, build, null, null);
                    transition.setReady(build, true);
                }
                resumeFocusedTasksTopActivities();
                notifyActivityPipModeChanged(activityRecord.getTask(), activityRecord);
            } finally {
            }
        } catch (Throwable th) {
            this.mService.continueWindowLayout();
            try {
                ensureActivitiesVisible(null, 0, false);
                throw th;
            } finally {
            }
        }
    }

    public static /* synthetic */ void lambda$moveActivityToPinnedRootTask$15(TaskFragment taskFragment) {
        if (taskFragment.isOrganizedTaskFragment()) {
            taskFragment.resetAdjacentTaskFragment();
            taskFragment.setCompanionTaskFragment(null);
            taskFragment.setAnimationParams(TaskFragmentAnimationParams.DEFAULT);
            if (taskFragment.getTopNonFinishingActivity() != null) {
                taskFragment.setRelativeEmbeddedBounds(new Rect());
                taskFragment.updateRequestedOverrideConfiguration(Configuration.EMPTY);
            }
        }
    }

    public void notifyActivityPipModeChanged(Task task, ActivityRecord activityRecord) {
        boolean z = activityRecord != null;
        if (z) {
            this.mService.getTaskChangeNotificationController().notifyActivityPinned(activityRecord);
        } else {
            this.mService.getTaskChangeNotificationController().notifyActivityUnpinned();
        }
        this.mWindowManager.mPolicy.setPipVisibilityLw(z);
        ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get()).setTrustedOverlay(task.getSurfaceControl(), z).apply();
        if (z) {
            return;
        }
        task.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ActivityRecord) obj).clearWaitForEnteringPinnedMode("exit_pip");
            }
        });
    }

    public void executeAppTransitionForAllDisplay() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ((DisplayContent) getChildAt(childCount)).mDisplayContent.executeAppTransition();
        }
    }

    public ActivityRecord findTask(ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea) {
        return findTask(activityRecord.getActivityType(), activityRecord.taskAffinity, activityRecord.intent, activityRecord.info, taskDisplayArea);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0067 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ActivityRecord findTask(final int i, final String str, final Intent intent, final ActivityInfo activityInfo, final TaskDisplayArea taskDisplayArea) {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        SecIpmManager secIpmManager;
        if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -1559645910, 0, (String) null, new Object[]{String.valueOf(i), String.valueOf(str), String.valueOf(intent), String.valueOf(activityInfo), String.valueOf(taskDisplayArea)});
        }
        this.mTmpFindTaskResult.init(i, str, intent, activityInfo);
        if (taskDisplayArea != null) {
            this.mTmpFindTaskResult.process(taskDisplayArea);
            FindTaskResult findTaskResult = this.mTmpFindTaskResult;
            ActivityRecord activityRecord3 = findTaskResult.mIdealRecord;
            if (activityRecord3 != null) {
                return activityRecord3;
            }
            ActivityRecord activityRecord4 = findTaskResult.mCandidateRecord;
            if (activityRecord4 != null) {
                activityRecord = activityRecord4;
                final AtomicReference atomicReference = new AtomicReference();
                activityRecord2 = (ActivityRecord) getItemFromTaskDisplayAreas(new Function() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda5
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        ActivityRecord lambda$findTask$17;
                        lambda$findTask$17 = RootWindowContainer.this.lambda$findTask$17(taskDisplayArea, i, str, intent, activityInfo, atomicReference, (TaskDisplayArea) obj);
                        return lambda$findTask$17;
                    }
                });
                if (activityRecord2 == null) {
                    return activityRecord2;
                }
                if (ProtoLogGroup.WM_DEBUG_TASKS.isEnabled() && activityRecord == null && ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_TASKS, -1376035390, 0, (String) null, (Object[]) null);
                }
                if (activityRecord == null && (secIpmManager = (SecIpmManager) this.mService.mContext.getSystemService("PkgPredictorService")) != null) {
                    ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                    secIpmManager.dexFilePreload(applicationInfo.uid, activityInfo.packageName, applicationInfo.getCodePath());
                }
                return activityRecord;
            }
        }
        activityRecord = null;
        final AtomicReference atomicReference2 = new AtomicReference();
        activityRecord2 = (ActivityRecord) getItemFromTaskDisplayAreas(new Function() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ActivityRecord lambda$findTask$17;
                lambda$findTask$17 = RootWindowContainer.this.lambda$findTask$17(taskDisplayArea, i, str, intent, activityInfo, atomicReference2, (TaskDisplayArea) obj);
                return lambda$findTask$17;
            }
        });
        if (activityRecord2 == null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ActivityRecord lambda$findTask$17(TaskDisplayArea taskDisplayArea, int i, String str, Intent intent, ActivityInfo activityInfo, AtomicReference atomicReference, TaskDisplayArea taskDisplayArea2) {
        if (taskDisplayArea2 == taskDisplayArea) {
            return null;
        }
        this.mTmpFindTaskResult.process(taskDisplayArea2);
        ActivityRecord activityRecord = this.mTmpFindTaskResult.mIdealRecord;
        if (activityRecord != null) {
            return activityRecord;
        }
        return null;
    }

    public int finishTopCrashedActivities(final WindowProcessController windowProcessController, final String str) {
        final Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        final Task[] taskArr = new Task[1];
        forAllTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$finishTopCrashedActivities$18(WindowProcessController.this, str, topDisplayFocusedRootTask, taskArr, (Task) obj);
            }
        });
        Task task = taskArr[0];
        if (task != null) {
            return task.mTaskId;
        }
        return -1;
    }

    public static /* synthetic */ void lambda$finishTopCrashedActivities$18(WindowProcessController windowProcessController, String str, Task task, Task[] taskArr, Task task2) {
        Task finishTopCrashedActivityLocked = task2.finishTopCrashedActivityLocked(windowProcessController, str);
        if (task2 == task || taskArr[0] == null) {
            taskArr[0] = finishTopCrashedActivityLocked;
        }
    }

    public boolean resumeFocusedTasksTopActivities() {
        return resumeFocusedTasksTopActivities(null, null, null);
    }

    public boolean resumeFocusedTasksTopActivities(Task task, ActivityRecord activityRecord, ActivityOptions activityOptions) {
        return resumeFocusedTasksTopActivities(task, activityRecord, activityOptions, false);
    }

    public boolean resumeFocusedTasksTopActivities(final Task task, final ActivityRecord activityRecord, final ActivityOptions activityOptions, boolean z) {
        if (!this.mTaskSupervisor.readyToResume()) {
            return false;
        }
        boolean resumeTopActivityUncheckedLocked = (task == null || !(task.isTopRootTaskInDisplayArea() || getTopDisplayFocusedRootTask() == task)) ? false : task.resumeTopActivityUncheckedLocked(activityRecord, activityOptions, z);
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            final boolean[] zArr = new boolean[1];
            final boolean z2 = resumeTopActivityUncheckedLocked;
            displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RootWindowContainer.lambda$resumeFocusedTasksTopActivities$19(Task.this, zArr, z2, activityOptions, activityRecord, (Task) obj);
                }
            });
            boolean z3 = zArr[0];
            boolean z4 = resumeTopActivityUncheckedLocked | z3;
            if (!z3 && ((displayContent.getDisplayId() != 2 || this.mService.mDexController.getDexModeLocked() != 0) && ((!displayContent.isRemoteAppDisplay() || displayContent.topRunningActivity(false) != null) && (!displayContent.isAppCastingDisplay() || displayContent.topRunningActivity(false) != null)))) {
                Task focusedRootTask = displayContent.getFocusedRootTask();
                if (focusedRootTask != null) {
                    z4 |= focusedRootTask.resumeTopActivityUncheckedLocked(activityRecord, activityOptions);
                    if (!z4 && activityRecord != null && activityRecord.mAliasChild && activityRecord.isState(ActivityRecord.State.INITIALIZING) && !activityRecord.isVisibleRequested() && focusedRootTask == task && focusedRootTask.inFreeformWindowingMode() && focusedRootTask.shouldBeVisible(null)) {
                        focusedRootTask.ensureActivitiesVisible(null, 0, true);
                    }
                } else if (task == null) {
                    resumeTopActivityUncheckedLocked = resumeHomeActivity(null, "no-focusable-task", displayContent.getDefaultTaskDisplayArea()) | z4;
                }
            }
            resumeTopActivityUncheckedLocked = z4;
        }
        return resumeTopActivityUncheckedLocked;
    }

    public static /* synthetic */ void lambda$resumeFocusedTasksTopActivities$19(Task task, boolean[] zArr, boolean z, ActivityOptions activityOptions, ActivityRecord activityRecord, Task task2) {
        ActivityRecord activityRecord2 = task2.topRunningActivity();
        if (!task2.isFocusableAndVisible() || activityRecord2 == null) {
            return;
        }
        if (task2 == task) {
            zArr[0] = zArr[0] | z;
        } else if (activityRecord2.isState(ActivityRecord.State.RESUMED) && activityRecord2 == task2.getDisplayArea().topRunningActivity()) {
            task2.executeAppTransition(activityOptions);
        } else {
            zArr[0] = zArr[0] | activityRecord2.makeActiveIfNeeded(activityRecord);
        }
    }

    public void sendSleepTransition(final DisplayContent displayContent) {
        final Transition transition = new Transition(12, 0, displayContent.mTransitionController, this.mWmService.mSyncEngine);
        TransitionController.OnStartCollect onStartCollect = new TransitionController.OnStartCollect() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda47
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z) {
                RootWindowContainer.lambda$sendSleepTransition$20(DisplayContent.this, transition, z);
            }
        };
        if (!displayContent.mTransitionController.isCollecting()) {
            if (this.mWindowManager.mSyncEngine.hasActiveSync()) {
                Slog.w(StartingSurfaceController.TAG, "Ongoing sync outside of a transition.");
            }
            displayContent.mTransitionController.moveToCollecting(transition);
            onStartCollect.onCollectStarted(false);
            return;
        }
        displayContent.mTransitionController.startCollectOrQueue(transition, onStartCollect);
    }

    public static /* synthetic */ void lambda$sendSleepTransition$20(DisplayContent displayContent, Transition transition, boolean z) {
        if (z && !displayContent.shouldSleep() && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX || !transition.isInKeyguardTransition())) {
            transition.abort();
        } else {
            displayContent.mTransitionController.requestStartTransition(transition, null, null, null);
            transition.playNow();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void applySleepTokens(boolean z) {
        boolean z2;
        Task focusedRootTask;
        int i;
        Task taskOccludingKeyguard;
        boolean z3 = false;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            final DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            final boolean shouldSleep = displayContent.shouldSleep();
            if (shouldSleep != displayContent.isSleeping()) {
                displayContent.setIsSleeping(shouldSleep);
                if (displayContent.mTransitionController.isShellTransitionsEnabled() && !z3 && shouldSleep && !displayContent.mAllSleepTokens.isEmpty()) {
                    if (!this.mHandler.hasMessages(3)) {
                        Handler handler = this.mHandler;
                        handler.sendMessageDelayed(handler.obtainMessage(3, displayContent), 1000L);
                    }
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && displayContent.mTransitionController.isCollecting() && displayContent.mTransitionController.getCollectingTransition().isInKeyguardTransition()) {
                        displayContent.mTransitionController.getCollectingTransition().setReady(displayContent, true);
                    }
                    z3 = true;
                }
                if (z) {
                    if (!shouldSleep && displayContent.mTransitionController.isShellTransitionsEnabled() && !displayContent.mTransitionController.isCollecting()) {
                        if (!displayContent.getDisplayPolicy().isAwake()) {
                            i = 11;
                        } else if (displayContent.isKeyguardOccluded()) {
                            taskOccludingKeyguard = displayContent.getTaskOccludingKeyguard();
                            i = 8;
                            if (i != 0) {
                                TransitionController transitionController = displayContent.mTransitionController;
                                transitionController.requestStartTransition(transitionController.createTransition(i), taskOccludingKeyguard, null, null);
                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                                    z2 = true;
                                    displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda12
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            RootWindowContainer.this.lambda$applySleepTokens$22(shouldSleep, displayContent, (Task) obj);
                                        }
                                    });
                                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && z2) {
                                        final Transition collectingTransition = displayContent.mTransitionController.getCollectingTransition();
                                        focusedRootTask = displayContent.getFocusedRootTask();
                                        if (focusedRootTask == null) {
                                            focusedRootTask.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda13
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    RootWindowContainer.lambda$applySleepTokens$23(Transition.this, (ActivityRecord) obj);
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        } else {
                            i = 0;
                        }
                        taskOccludingKeyguard = null;
                        if (i != 0) {
                        }
                    }
                    z2 = false;
                    displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RootWindowContainer.this.lambda$applySleepTokens$22(shouldSleep, displayContent, (Task) obj);
                        }
                    });
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                        final Transition collectingTransition2 = displayContent.mTransitionController.getCollectingTransition();
                        focusedRootTask = displayContent.getFocusedRootTask();
                        if (focusedRootTask == null) {
                        }
                    }
                }
            }
        }
        if (z3) {
            return;
        }
        this.mHandler.removeMessages(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySleepTokens$22(boolean z, DisplayContent displayContent, Task task) {
        if (z) {
            task.goToSleepIfPossible(false);
            return;
        }
        task.forAllLeafTasksAndLeafTaskFragments(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda42
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TaskFragment) obj).awakeFromSleeping();
            }
        }, true);
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && displayContent.isDefaultDisplay && this.mWmService.isFolded() && this.mService.mMultiWindowFoldController.isHoldingSplitScreen()) {
            this.mService.mMultiWindowFoldController.scheduleWakeUpInFoldingState(false);
        }
        if (task.isFocusedRootTaskOnDisplay() && !this.mTaskSupervisor.getKeyguardController().isKeyguardOrAodShowing(displayContent.mDisplayId)) {
            task.resumeTopActivityUncheckedLocked(null, null);
        }
        if (!task.mReparenting || task.getDisplayArea() == null || task.getDisplayId() == task.getDisplayArea().getDisplayId()) {
            task.ensureActivitiesVisible(null, 0, false);
        }
    }

    public static /* synthetic */ void lambda$applySleepTokens$23(Transition transition, ActivityRecord activityRecord) {
        if (!activityRecord.isVisibleRequested() || transition.isInTransition(activityRecord)) {
            return;
        }
        transition.collect(activityRecord);
    }

    public Task getRootTask(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            Task rootTask = ((DisplayContent) getChildAt(childCount)).getRootTask(i);
            if (rootTask != null) {
                return rootTask;
            }
        }
        return null;
    }

    public Task getRootTask(int i, int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            Task rootTask = ((DisplayContent) getChildAt(childCount)).getRootTask(i, i2);
            if (rootTask != null) {
                return rootTask;
            }
        }
        return null;
    }

    public final Task getRootTask(int i, int i2, int i3) {
        DisplayContent displayContent = getDisplayContent(i3);
        if (displayContent == null) {
            return null;
        }
        return displayContent.getRootTask(i, i2);
    }

    public final ActivityTaskManager.RootTaskInfo getRootTaskInfo(final Task task) {
        final ActivityTaskManager.RootTaskInfo rootTaskInfo = new ActivityTaskManager.RootTaskInfo();
        task.fillTaskInfo(rootTaskInfo);
        DisplayContent displayContent = task.getDisplayContent();
        if (displayContent == null) {
            rootTaskInfo.position = -1;
        } else {
            final int[] iArr = new int[1];
            final boolean[] zArr = new boolean[1];
            displayContent.forAllRootTasks(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda21
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getRootTaskInfo$24;
                    lambda$getRootTaskInfo$24 = RootWindowContainer.lambda$getRootTaskInfo$24(Task.this, zArr, iArr, (Task) obj);
                    return lambda$getRootTaskInfo$24;
                }
            }, false);
            rootTaskInfo.position = zArr[0] ? iArr[0] : -1;
        }
        rootTaskInfo.visible = task.shouldBeVisible(null);
        task.getBounds(rootTaskInfo.bounds);
        int descendantTaskCount = task.getDescendantTaskCount();
        rootTaskInfo.childTaskIds = new int[descendantTaskCount];
        rootTaskInfo.childTaskNames = new String[descendantTaskCount];
        rootTaskInfo.childTaskBounds = new Rect[descendantTaskCount];
        rootTaskInfo.childTaskUserIds = new int[descendantTaskCount];
        final int[] iArr2 = {0};
        task.forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$getRootTaskInfo$25(iArr2, rootTaskInfo, (Task) obj);
            }
        }, false);
        ActivityRecord activityRecord = task.topRunningActivity();
        rootTaskInfo.topActivity = activityRecord != null ? activityRecord.intent.getComponent() : null;
        return rootTaskInfo;
    }

    public static /* synthetic */ boolean lambda$getRootTaskInfo$24(Task task, boolean[] zArr, int[] iArr, Task task2) {
        if (task == task2) {
            zArr[0] = true;
            return true;
        }
        iArr[0] = iArr[0] + 1;
        return false;
    }

    public static /* synthetic */ void lambda$getRootTaskInfo$25(int[] iArr, ActivityTaskManager.RootTaskInfo rootTaskInfo, Task task) {
        String str;
        int i = iArr[0];
        rootTaskInfo.childTaskIds[i] = task.mTaskId;
        String[] strArr = rootTaskInfo.childTaskNames;
        ComponentName componentName = task.origActivity;
        if (componentName != null) {
            str = componentName.flattenToString();
        } else {
            ComponentName componentName2 = task.realActivity;
            if (componentName2 != null) {
                str = componentName2.flattenToString();
            } else {
                str = task.getTopNonFinishingActivity() != null ? task.getTopNonFinishingActivity().packageName : "unknown";
            }
        }
        strArr[i] = str;
        rootTaskInfo.childTaskBounds[i] = task.mAtmService.getTaskBounds(task.mTaskId);
        rootTaskInfo.childTaskUserIds[i] = task.mUserId;
        iArr[0] = i + 1;
    }

    public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i) {
        Task rootTask = getRootTask(i);
        if (rootTask != null) {
            return getRootTaskInfo(rootTask);
        }
        return null;
    }

    public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) {
        Task rootTask = getRootTask(i, i2);
        if (rootTask != null) {
            return getRootTaskInfo(rootTask);
        }
        return null;
    }

    public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2, int i3) {
        Task rootTask = getRootTask(i, i2, i3);
        if (rootTask != null) {
            return getRootTaskInfo(rootTask);
        }
        return null;
    }

    public ArrayList getAllRootTaskInfos(int i) {
        final ArrayList arrayList = new ArrayList();
        if (i == -1) {
            forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda29
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RootWindowContainer.this.lambda$getAllRootTaskInfos$26(arrayList, (Task) obj);
                }
            });
            return arrayList;
        }
        DisplayContent displayContent = getDisplayContent(i);
        if (displayContent == null) {
            return arrayList;
        }
        displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$getAllRootTaskInfos$27(arrayList, (Task) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAllRootTaskInfos$26(ArrayList arrayList, Task task) {
        arrayList.add(getRootTaskInfo(task));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAllRootTaskInfos$27(ArrayList arrayList, Task task) {
        arrayList.add(getRootTaskInfo(task));
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
        if (i != 0) {
            Slog.v(StartingSurfaceController.TAG, "Display added displayId=" + i);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i);
                if (displayContentOrCreate == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (this.mService.isBooted() || this.mService.isBooting()) {
                    if (i != 0) {
                        Slog.d(StartingSurfaceController.TAG, "onDisplayAdded, displayId=" + i + " display=" + displayContentOrCreate.mDisplay);
                    }
                    if (i == 2) {
                        this.mService.mDexController.activateDexDisplayLocked(displayContentOrCreate);
                    }
                    startSystemDecorations(displayContentOrCreate);
                }
                this.mWmService.mPossibleDisplayInfoMapper.removePossibleDisplayInfos(i);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void startSystemDecorations(DisplayContent displayContent) {
        if (this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(displayContent.getDisplayId()) && this.mCurrentUser != 0) {
            startHomeOnDisplay(0, "displayAdded", displayContent.getDisplayId());
        } else {
            startHomeOnDisplay(this.mCurrentUser, "displayAdded", displayContent.getDisplayId());
        }
        displayContent.getDisplayPolicy().notifyDisplayReady();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
        if (i != 0) {
            Slog.v(StartingSurfaceController.TAG, "Display removed displayId=" + i);
        }
        if (i == 0) {
            throw new IllegalArgumentException("Can't remove the primary display.");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = getDisplayContent(i);
                if (displayContent == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.d(StartingSurfaceController.TAG, "onDisplayRemoved, displayId=" + i);
                displayContent.remove();
                this.mWmService.mPossibleDisplayInfoMapper.removePossibleDisplayInfos(i);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.onDisplayChanged();
                }
                this.mWmService.mPossibleDisplayInfoMapper.removePossibleDisplayInfos(i);
                updateDisplayImePolicyCache();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void updateDisplayImePolicyCache() {
        final ArrayMap arrayMap = new ArrayMap();
        forAllDisplays(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$updateDisplayImePolicyCache$28(arrayMap, (DisplayContent) obj);
            }
        });
        this.mWmService.mDisplayImePolicyCache = Collections.unmodifiableMap(arrayMap);
    }

    public static /* synthetic */ void lambda$updateDisplayImePolicyCache$28(ArrayMap arrayMap, DisplayContent displayContent) {
        arrayMap.put(Integer.valueOf(displayContent.getDisplayId()), Integer.valueOf(displayContent.getImePolicy()));
    }

    public void updateUIDsPresentOnDisplay() {
        this.mDisplayAccessUIDs.clear();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent.isPrivate()) {
                this.mDisplayAccessUIDs.append(displayContent.mDisplayId, displayContent.getPresentUIDs());
            }
        }
        this.mDisplayManagerInternal.setDisplayAccessUIDs(this.mDisplayAccessUIDs);
    }

    public void prepareForShutdown() {
        for (int i = 0; i < getChildCount(); i++) {
            createSleepToken("shutdown", ((DisplayContent) getChildAt(i)).mDisplayId);
        }
    }

    public SleepToken createSleepToken(String str, int i) {
        return createSleepToken(str, i, false);
    }

    public SleepToken createSleepToken(String str, int i, boolean z) {
        DisplayContent displayContent;
        if (i == 4) {
            displayContent = getDisplayContentOrCreate(i);
        } else {
            displayContent = getDisplayContent(i);
        }
        if (displayContent == null) {
            throw new IllegalArgumentException("Invalid display: " + i);
        }
        int makeSleepTokenKey = makeSleepTokenKey(str, i);
        SleepToken sleepToken = (SleepToken) this.mSleepTokens.get(makeSleepTokenKey);
        if (sleepToken == null) {
            SleepToken sleepToken2 = new SleepToken(str, i, z);
            this.mSleepTokens.put(makeSleepTokenKey, sleepToken2);
            displayContent.mAllSleepTokens.add(sleepToken2);
            EventLogTags.writeWmSleepToken(i, 1, str);
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -317761482, 4, (String) null, new Object[]{String.valueOf(str), Long.valueOf(i)});
            }
            return sleepToken2;
        }
        throw new RuntimeException("Create the same sleep token twice: " + sleepToken);
    }

    public void removeSleepToken(SleepToken sleepToken) {
        if (!this.mSleepTokens.contains(sleepToken.mHashKey)) {
            Slog.d(StartingSurfaceController.TAG, "Remove non-exist sleep token: " + sleepToken + " from " + Debug.getCallers(6));
        }
        this.mSleepTokens.remove(sleepToken.mHashKey);
        EventLogTags.writeWmSleepToken(sleepToken.mDisplayId, 0, sleepToken.mTag);
        DisplayContent displayContent = getDisplayContent(sleepToken.mDisplayId);
        if (displayContent == null) {
            Slog.d(StartingSurfaceController.TAG, "Remove sleep token for non-existing display: " + sleepToken + " from " + Debug.getCallers(6));
            return;
        }
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -436553282, 4, (String) null, new Object[]{String.valueOf(sleepToken.mTag), Long.valueOf(sleepToken.mDisplayId)});
        }
        displayContent.mAllSleepTokens.remove(sleepToken);
        if (displayContent.mAllSleepTokens.isEmpty()) {
            this.mService.updateSleepIfNeededLocked();
            if ((!this.mTaskSupervisor.getKeyguardController().isDisplayOccluded(displayContent.mDisplayId) && sleepToken.mTag.equals("keyguard")) || sleepToken.mTag.equals("Display-off") || sleepToken.mTag.equals("cover-virtual")) {
                displayContent.mSkipAppTransitionAnimation = true;
            }
        }
    }

    public void addStartingWindowsForVisibleActivities() {
        final ArrayList arrayList = new ArrayList();
        forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$addStartingWindowsForVisibleActivities$29(arrayList, (ActivityRecord) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$addStartingWindowsForVisibleActivities$29(ArrayList arrayList, ActivityRecord activityRecord) {
        Task task = activityRecord.getTask();
        if (activityRecord.isVisibleRequested() && activityRecord.mStartingData == null && !arrayList.contains(task)) {
            activityRecord.showStartingWindow(true);
            arrayList.add(task);
        }
    }

    public void invalidateTaskLayers() {
        if (this.mTaskLayersChanged) {
            return;
        }
        this.mTaskLayersChanged = true;
        this.mService.f1734mH.post(this.mRankTaskLayersRunnable);
    }

    public void rankTaskLayers() {
        if (this.mTaskLayersChanged) {
            this.mTaskLayersChanged = false;
            this.mService.f1734mH.removeCallbacks(this.mRankTaskLayersRunnable);
        }
        this.mTmpTaskLayerRank = 0;
        forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$rankTaskLayers$31((Task) obj);
            }
        }, true);
        if (this.mTaskSupervisor.inActivityVisibilityUpdate()) {
            return;
        }
        this.mTaskSupervisor.computeProcessActivityStateBatch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rankTaskLayers$31(Task task) {
        int i = task.mLayerRank;
        ActivityRecord activityRecord = task.topRunningActivityLocked();
        if (activityRecord != null && activityRecord.isVisibleRequested()) {
            int i2 = this.mTmpTaskLayerRank + 1;
            this.mTmpTaskLayerRank = i2;
            task.mLayerRank = i2;
        } else {
            task.mLayerRank = -1;
        }
        if (task.mLayerRank != i) {
            task.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda51
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RootWindowContainer.this.lambda$rankTaskLayers$30((ActivityRecord) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rankTaskLayers$30(ActivityRecord activityRecord) {
        if (activityRecord.hasProcess()) {
            this.mTaskSupervisor.onProcessActivityStateChanged(activityRecord.app, true);
        }
    }

    public void clearOtherAppTimeTrackers(final AppTimeTracker appTimeTracker) {
        forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$clearOtherAppTimeTrackers$32(AppTimeTracker.this, (ActivityRecord) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$clearOtherAppTimeTrackers$32(AppTimeTracker appTimeTracker, ActivityRecord activityRecord) {
        if (activityRecord.appTimeTracker != appTimeTracker) {
            activityRecord.appTimeTracker = null;
        }
    }

    public void scheduleDestroyAllActivities(String str) {
        this.mDestroyAllActivitiesReason = str;
        this.mService.f1734mH.post(this.mDestroyAllActivitiesRunnable);
    }

    public void scheduleDestroyAllActivities(WindowProcessController windowProcessController, String str) {
        this.mDestroyTargetAllActivitiesRunnable.setParam(windowProcessController, str);
        this.mService.f1734mH.post(this.mDestroyTargetAllActivitiesRunnable);
    }

    public boolean putTasksToSleep(final boolean z, final boolean z2) {
        final boolean[] zArr = {true};
        forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda36
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$putTasksToSleep$33(z, zArr, z2, (Task) obj);
            }
        });
        return zArr[0];
    }

    public static /* synthetic */ void lambda$putTasksToSleep$33(boolean z, boolean[] zArr, boolean z2, Task task) {
        if (z) {
            zArr[0] = zArr[0] & task.goToSleepIfPossible(z2);
        } else {
            task.ensureActivitiesVisible(null, 0, false);
        }
    }

    public ActivityRecord findActivity(Intent intent, ActivityInfo activityInfo, boolean z) {
        ComponentName component = intent.getComponent();
        if (activityInfo.targetActivity != null) {
            component = new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
        }
        int userId = UserHandle.getUserId(activityInfo.applicationInfo.uid);
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new QuintPredicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda39
            public final boolean test(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                boolean matchesActivity;
                matchesActivity = RootWindowContainer.matchesActivity((ActivityRecord) obj, ((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue(), (Intent) obj4, (ComponentName) obj5);
                return matchesActivity;
            }
        }, PooledLambda.__(ActivityRecord.class), Integer.valueOf(userId), Boolean.valueOf(z), intent, component);
        ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    public static boolean matchesActivity(ActivityRecord activityRecord, int i, boolean z, Intent intent, ComponentName componentName) {
        if (activityRecord.canBeTopRunning() && activityRecord.mUserId == i) {
            if (z) {
                if (activityRecord.intent.filterEquals(intent)) {
                    return true;
                }
            } else if (activityRecord.mActivityComponent.equals(componentName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAwakeDisplay() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            if (!((DisplayContent) getChildAt(childCount)).shouldSleep()) {
                return true;
            }
        }
        return false;
    }

    public Task getOrCreateRootTask(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, boolean z) {
        return getOrCreateRootTask(activityRecord, activityOptions, task, null, z, null, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Task getOrCreateRootTask(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, Task task2, boolean z, LaunchParamsController.LaunchParams launchParams, int i) {
        TaskDisplayArea taskDisplayArea;
        int launchDisplayId;
        DisplayContent displayContent;
        int launchTaskId;
        Task fromWindowContainerToken;
        if (activityOptions != null && (fromWindowContainerToken = Task.fromWindowContainerToken(activityOptions.getLaunchRootTask())) != null && canLaunchOnDisplay(activityRecord, fromWindowContainerToken)) {
            return fromWindowContainerToken;
        }
        if (activityOptions != null && (launchTaskId = activityOptions.getLaunchTaskId()) != -1) {
            activityOptions.setLaunchTaskId(-1);
            Task anyTaskForId = anyTaskForId(launchTaskId, 2, activityOptions, z);
            activityOptions.setLaunchTaskId(launchTaskId);
            if (canLaunchOnDisplay(activityRecord, anyTaskForId)) {
                return anyTaskForId.getRootTask();
            }
        }
        TaskDisplayArea taskDisplayArea2 = null;
        if (launchParams == null || (taskDisplayArea = launchParams.mPreferredTaskDisplayArea) == null) {
            if (activityOptions != null) {
                WindowContainerToken launchTaskDisplayArea = activityOptions.getLaunchTaskDisplayArea();
                taskDisplayArea = launchTaskDisplayArea != null ? (TaskDisplayArea) WindowContainer.fromBinder(launchTaskDisplayArea.asBinder()) : null;
                if (taskDisplayArea == null && (launchDisplayId = activityOptions.getLaunchDisplayId()) != -1 && (displayContent = getDisplayContent(launchDisplayId)) != null) {
                    taskDisplayArea = displayContent.getDefaultTaskDisplayArea();
                }
            } else {
                taskDisplayArea = null;
            }
        }
        int resolveActivityType = resolveActivityType(activityRecord, activityOptions, task);
        if (taskDisplayArea != null) {
            if (canLaunchOnDisplay(activityRecord, taskDisplayArea.getDisplayId())) {
                if (MultiWindowCoreState.MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED && taskDisplayArea.needToEnsureLaunchSplitRootTask(activityRecord, activityOptions, task2, task)) {
                    return taskDisplayArea.ensureLaunchSplitRootTask(activityRecord, activityOptions, task, task2, launchParams, i, resolveActivityType, z);
                }
                return taskDisplayArea.getOrCreateRootTask(activityRecord, activityOptions, task, task2, launchParams, i, resolveActivityType, z);
            }
            taskDisplayArea = null;
        }
        Task rootTask = task != null ? task.getRootTask() : null;
        if (rootTask == null && activityRecord != null) {
            rootTask = activityRecord.getRootTask();
        }
        int i2 = launchParams != null ? launchParams.mWindowingMode : 0;
        if (rootTask != null) {
            taskDisplayArea = rootTask.getDisplayArea();
            if (taskDisplayArea != null && canLaunchOnDisplay(activityRecord, taskDisplayArea.mDisplayContent.mDisplayId)) {
                if (ForceLaunchWindowingModeUtils.shouldApplyForceLaunchWindowingMode(activityOptions, activityRecord, task)) {
                    i2 = ForceLaunchWindowingModeUtils.resolveForceLaunchWindowingMode(activityOptions, activityRecord, task);
                }
                if (i2 == 0) {
                    i2 = taskDisplayArea.resolveWindowingMode(activityRecord, activityOptions, task);
                }
                if (rootTask.isCompatible(i2, resolveActivityType) || (rootTask.mCreatedByOrganizer && i2 != 5)) {
                    return rootTask;
                }
            }
            return (taskDisplayArea2 != null ? getDefaultTaskDisplayArea() : taskDisplayArea2).getOrCreateRootTask(activityRecord, activityOptions, task, task2, launchParams, i, resolveActivityType, z);
        }
        taskDisplayArea2 = taskDisplayArea;
        return (taskDisplayArea2 != null ? getDefaultTaskDisplayArea() : taskDisplayArea2).getOrCreateRootTask(activityRecord, activityOptions, task, task2, launchParams, i, resolveActivityType, z);
    }

    public final boolean canLaunchOnDisplay(ActivityRecord activityRecord, Task task) {
        if (task == null) {
            Slog.w(StartingSurfaceController.TAG, "canLaunchOnDisplay(), invalid task: " + task);
            return false;
        }
        if (!task.isAttached()) {
            Slog.w(StartingSurfaceController.TAG, "canLaunchOnDisplay(), Task is not attached: " + task);
            return false;
        }
        return canLaunchOnDisplay(activityRecord, task.getTaskDisplayArea().getDisplayId());
    }

    public final boolean canLaunchOnDisplay(ActivityRecord activityRecord, int i) {
        if (activityRecord == null || activityRecord.canBeLaunchedOnDisplay(i)) {
            return true;
        }
        Slog.w(StartingSurfaceController.TAG, "Not allow to launch " + activityRecord + " on display " + i);
        return false;
    }

    public int resolveActivityType(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task) {
        int activityType = activityRecord != null ? activityRecord.getActivityType() : 0;
        if (activityType == 0 && task != null) {
            activityType = task.getActivityType();
        }
        if (activityType != 0) {
            return activityType;
        }
        if (activityOptions != null) {
            activityType = activityOptions.getLaunchActivityType();
        }
        if (activityType != 0) {
            return activityType;
        }
        return 1;
    }

    public Task getNextFocusableRootTask(Task task, boolean z) {
        Task nextFocusableRootTask;
        TaskDisplayArea displayArea = task.getDisplayArea();
        if (displayArea == null) {
            displayArea = getDisplayContent(task.mPrevDisplayId).getDefaultTaskDisplayArea();
        }
        Task nextFocusableRootTask2 = displayArea.getNextFocusableRootTask(task, z);
        if (nextFocusableRootTask2 != null) {
            return nextFocusableRootTask2;
        }
        if (displayArea.mDisplayContent.supportsSystemDecorations()) {
            return null;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (displayContent != displayArea.mDisplayContent && (nextFocusableRootTask = displayContent.getDefaultTaskDisplayArea().getNextFocusableRootTask(task, z)) != null) {
                return nextFocusableRootTask;
            }
        }
        return null;
    }

    public void closeSystemDialogActivities(final String str) {
        forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$closeSystemDialogActivities$34(str, (ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closeSystemDialogActivities$34(String str, ActivityRecord activityRecord) {
        if ((activityRecord.info.flags & 256) != 0 || shouldCloseAssistant(activityRecord, str)) {
            activityRecord.finishIfPossible(str, true);
        }
    }

    public void closeSystemDialogActivities(final String str, int i) {
        DisplayContent displayContent = getDisplayContent(i);
        if (displayContent == null) {
            Slog.e(StartingSurfaceController.TAG, "closeSystemDialogActivities: cannot find display #" + i);
            return;
        }
        displayContent.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$closeSystemDialogActivities$35(str, (ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closeSystemDialogActivities$35(String str, ActivityRecord activityRecord) {
        if ((activityRecord.info.flags & 256) != 0 || shouldCloseAssistant(activityRecord, str)) {
            activityRecord.finishIfPossible(str, true);
        }
    }

    public boolean hasVisibleWindowAboveButDoesNotOwnNotificationShade(final int i) {
        final boolean[] zArr = {false};
        return forAllWindows(new ToBooleanFunction() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda0
            public final boolean apply(Object obj) {
                boolean lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$36;
                lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$36 = RootWindowContainer.lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$36(i, zArr, (WindowState) obj);
                return lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$36;
            }
        }, true);
    }

    public static /* synthetic */ boolean lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$36(int i, boolean[] zArr, WindowState windowState) {
        if (windowState.mOwnerUid == i && windowState.isVisible()) {
            zArr[0] = true;
        }
        if (windowState.mAttrs.type == 2040) {
            return zArr[0] && windowState.mOwnerUid != i;
        }
        return false;
    }

    public final boolean shouldCloseAssistant(ActivityRecord activityRecord, String str) {
        if (activityRecord.isActivityTypeAssistant() && str != "assist") {
            return this.mWmService.mAssistantOnTopOfDream;
        }
        return false;
    }

    public class FinishDisabledPackageActivitiesHelper implements Predicate {
        public final ArrayList mCollectedActivities = new ArrayList();
        public boolean mDoit;
        public boolean mEvenPersistent;
        public Set mFilterByClasses;
        public Task mLastTask;
        public boolean mOnlyRemoveNoProcess;
        public String mPackageName;
        public int mUserId;

        public FinishDisabledPackageActivitiesHelper() {
        }

        public final void reset(String str, Set set, boolean z, boolean z2, int i, boolean z3) {
            this.mPackageName = str;
            this.mFilterByClasses = set;
            this.mDoit = z;
            this.mEvenPersistent = z2;
            this.mUserId = i;
            this.mOnlyRemoveNoProcess = z3;
            this.mLastTask = null;
        }

        public boolean process(String str, Set set, boolean z, boolean z2, int i, boolean z3) {
            reset(str, set, z, z2, i, z3);
            RootWindowContainer.this.forAllActivities(this);
            int size = this.mCollectedActivities.size();
            boolean z4 = false;
            for (int i2 = 0; i2 < size; i2++) {
                ActivityRecord activityRecord = (ActivityRecord) this.mCollectedActivities.get(i2);
                if (this.mOnlyRemoveNoProcess) {
                    if (!activityRecord.hasProcess()) {
                        Slog.i(StartingSurfaceController.TAG, "  Force removing " + activityRecord);
                        activityRecord.cleanUp(false, false);
                        activityRecord.removeFromHistory("force-stop");
                    }
                } else {
                    Slog.i(StartingSurfaceController.TAG, "  Force finishing " + activityRecord);
                    activityRecord.finishIfPossible("force-stop", true);
                }
                z4 = true;
            }
            this.mCollectedActivities.clear();
            return z4;
        }

        @Override // java.util.function.Predicate
        public boolean test(ActivityRecord activityRecord) {
            Set set;
            boolean z = (activityRecord.packageName.equals(this.mPackageName) && ((set = this.mFilterByClasses) == null || set.contains(activityRecord.mActivityComponent.getClassName()))) || (this.mPackageName == null && activityRecord.mUserId == this.mUserId);
            boolean z2 = !activityRecord.hasProcess();
            int i = this.mUserId;
            if ((i == -1 || activityRecord.mUserId == i) && ((z || activityRecord.getTask() == this.mLastTask) && (z2 || this.mEvenPersistent || !activityRecord.app.isPersistent()))) {
                if (!this.mDoit) {
                    return !activityRecord.finishing;
                }
                this.mCollectedActivities.add(activityRecord);
                this.mLastTask = activityRecord.getTask();
            }
            return false;
        }
    }

    public boolean finishDisabledPackageActivities(String str, Set set, boolean z, boolean z2, int i, boolean z3) {
        return this.mFinishDisabledPackageActivitiesHelper.process(str, set, z, z2, i, z3);
    }

    public void updateActivityApplicationInfo(final ApplicationInfo applicationInfo) {
        final String str = applicationInfo.packageName;
        final int userId = UserHandle.getUserId(applicationInfo.uid);
        forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$updateActivityApplicationInfo$37(userId, str, applicationInfo, (ActivityRecord) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$updateActivityApplicationInfo$37(int i, String str, ApplicationInfo applicationInfo, ActivityRecord activityRecord) {
        if (activityRecord.mUserId == i && str.equals(activityRecord.packageName)) {
            activityRecord.updateApplicationInfo(applicationInfo);
        }
    }

    public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) {
        final IBinder asBinder = iVoiceInteractionSession.asBinder();
        forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Task) obj).finishIfVoiceTask(asBinder);
            }
        }, true);
    }

    public void removeRootTasksInWindowingModes(int... iArr) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ((DisplayContent) getChildAt(childCount)).removeRootTasksInWindowingModes(iArr);
        }
    }

    public void removeRootTasksWithActivityTypes(int... iArr) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ((DisplayContent) getChildAt(childCount)).removeRootTasksWithActivityTypes(iArr);
        }
    }

    public ActivityRecord topRunningActivity() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ActivityRecord activityRecord = ((DisplayContent) getChildAt(childCount)).topRunningActivity();
            if (activityRecord != null) {
                return activityRecord;
            }
        }
        return null;
    }

    public boolean allResumedActivitiesIdle() {
        Task focusedRootTask;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (!displayContent.isSleeping() && (focusedRootTask = displayContent.getFocusedRootTask()) != null && focusedRootTask.hasActivity()) {
                ActivityRecord topResumedActivity = focusedRootTask.getTopResumedActivity();
                if (topResumedActivity == null || !topResumedActivity.idle) {
                    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, -938271693, 1, (String) null, new Object[]{Long.valueOf(focusedRootTask.getRootTaskId()), String.valueOf(topResumedActivity)});
                    }
                    return false;
                }
                if (this.mTransitionController.isTransientLaunch(topResumedActivity)) {
                    return false;
                }
            }
        }
        this.mService.endLaunchPowerMode(1);
        return true;
    }

    public boolean allResumedActivitiesVisible() {
        final boolean[] zArr = {false};
        if (forAllRootTasks(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$allResumedActivitiesVisible$39;
                lambda$allResumedActivitiesVisible$39 = RootWindowContainer.lambda$allResumedActivitiesVisible$39(zArr, (Task) obj);
                return lambda$allResumedActivitiesVisible$39;
            }
        })) {
            return false;
        }
        return zArr[0];
    }

    public static /* synthetic */ boolean lambda$allResumedActivitiesVisible$39(boolean[] zArr, Task task) {
        ActivityRecord topResumedActivity = task.getTopResumedActivity();
        if (topResumedActivity != null) {
            if (!topResumedActivity.nowVisible) {
                return true;
            }
            zArr[0] = true;
        }
        return false;
    }

    public boolean allPausedActivitiesComplete() {
        final boolean[] zArr = {true};
        if (forAllLeafTasks(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$allPausedActivitiesComplete$40;
                lambda$allPausedActivitiesComplete$40 = RootWindowContainer.lambda$allPausedActivitiesComplete$40(zArr, (Task) obj);
                return lambda$allPausedActivitiesComplete$40;
            }
        })) {
            return false;
        }
        return zArr[0];
    }

    public static /* synthetic */ boolean lambda$allPausedActivitiesComplete$40(boolean[] zArr, Task task) {
        ActivityRecord topPausingActivity = task.getTopPausingActivity();
        if (topPausingActivity != null && !topPausingActivity.isState(ActivityRecord.State.PAUSED, ActivityRecord.State.STOPPED, ActivityRecord.State.STOPPING, ActivityRecord.State.FINISHING)) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STATES, 895158150, 0, (String) null, new Object[]{String.valueOf(topPausingActivity), String.valueOf(topPausingActivity.getState())});
            }
            if (!ProtoLogGroup.WM_DEBUG_STATES.isEnabled()) {
                return true;
            }
            zArr[0] = false;
        }
        return false;
    }

    public void lockAllProfileTasks(final int i) {
        forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.this.lambda$lockAllProfileTasks$42(i, (Task) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lockAllProfileTasks$42(final int i, Task task) {
        ActivityRecord activityRecord = task.topRunningActivity();
        if ((activityRecord == null || activityRecord.finishing || !"android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER".equals(activityRecord.intent.getAction()) || !activityRecord.packageName.equals(this.mService.getSysUiServiceComponentLocked().getPackageName())) && task.getActivity(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$lockAllProfileTasks$41;
                lambda$lockAllProfileTasks$41 = RootWindowContainer.lambda$lockAllProfileTasks$41(i, (ActivityRecord) obj);
                return lambda$lockAllProfileTasks$41;
            }
        }) != null) {
            this.mService.getTaskChangeNotificationController().notifyTaskProfileLocked(task.getTaskInfo(), i);
        }
    }

    public static /* synthetic */ boolean lambda$lockAllProfileTasks$41(int i, ActivityRecord activityRecord) {
        return !activityRecord.finishing && activityRecord.mUserId == i;
    }

    public Task anyTaskForId(int i) {
        return anyTaskForId(i, 2);
    }

    public Task anyTaskForId(int i, int i2) {
        return anyTaskForId(i, i2, null, false);
    }

    public Task anyTaskForId(int i, int i2, ActivityOptions activityOptions, boolean z) {
        Task task;
        Task orCreateRootTask;
        if (i2 != 2 && activityOptions != null) {
            throw new IllegalArgumentException("Should not specify activity options for non-restore lookup");
        }
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new AppTransition$$ExternalSyntheticLambda2(), PooledLambda.__(Task.class), Integer.valueOf(i));
        Task task2 = getTask(obtainPredicate);
        obtainPredicate.recycle();
        if (task2 != null) {
            if (activityOptions != null && (orCreateRootTask = getOrCreateRootTask(null, activityOptions, task2, z)) != null && task2.getRootTask() != orCreateRootTask && task2.getParent() != orCreateRootTask) {
                task2.reparent(orCreateRootTask, z, z ? 0 : 2, true, true, "anyTaskForId");
            }
            return task2;
        }
        if (i2 == 0 || (task = this.mTaskSupervisor.mRecentTasks.getTask(i)) == null) {
            return null;
        }
        if (i2 == 1 || this.mTaskSupervisor.restoreRecentTaskLocked(task, activityOptions, z)) {
            return task;
        }
        return null;
    }

    public void getRunningTasks(int i, List list, int i2, int i3, ArraySet arraySet, int i4) {
        RootWindowContainer rootWindowContainer;
        if (i4 != -1) {
            DisplayContent displayContent = getDisplayContent(i4);
            if (displayContent == null) {
                return;
            } else {
                rootWindowContainer = displayContent;
            }
        } else {
            rootWindowContainer = this;
        }
        this.mTaskSupervisor.getRunningTasks().getTasks(i, list, i2, this.mService.getRecentTasks(), rootWindowContainer, i3, arraySet);
    }

    public void startPowerModeLaunchIfNeeded(boolean z, final ActivityRecord activityRecord) {
        ActivityOptions options;
        int i = 1;
        if (!z && activityRecord != null && activityRecord.app != null) {
            final boolean[] zArr = {true};
            final boolean[] zArr2 = {true};
            forAllTaskDisplayAreas(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RootWindowContainer.lambda$startPowerModeLaunchIfNeeded$43(zArr, zArr2, activityRecord, (TaskDisplayArea) obj);
                }
            });
            if (!zArr[0] && !zArr2[0]) {
                return;
            }
        }
        if ((activityRecord != null ? activityRecord.isKeyguardLocked() : this.mDefaultDisplay.isKeyguardLocked()) && activityRecord != null && !activityRecord.isLaunchSourceType(3) && ((options = activityRecord.getOptions()) == null || options.getSourceInfo() == null || options.getSourceInfo().type != 3)) {
            i = 5;
        }
        this.mService.startLaunchPowerMode(i);
    }

    public static /* synthetic */ void lambda$startPowerModeLaunchIfNeeded$43(boolean[] zArr, boolean[] zArr2, ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea) {
        ActivityRecord focusedActivity = taskDisplayArea.getFocusedActivity();
        WindowProcessController windowProcessController = focusedActivity == null ? null : focusedActivity.app;
        zArr[0] = zArr[0] & (windowProcessController == null);
        if (windowProcessController != null) {
            zArr2[0] = zArr2[0] & (!windowProcessController.equals(activityRecord.app));
        }
    }

    public int getTaskToShowPermissionDialogOn(final String str, final int i) {
        final PermissionPolicyInternal permissionPolicyInternal = this.mService.getPermissionPolicyInternal();
        if (permissionPolicyInternal == null) {
            return -1;
        }
        final int[] iArr = {-1};
        forAllLeafTaskFragments(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTaskToShowPermissionDialogOn$45;
                lambda$getTaskToShowPermissionDialogOn$45 = RootWindowContainer.lambda$getTaskToShowPermissionDialogOn$45(PermissionPolicyInternal.this, i, str, iArr, (TaskFragment) obj);
                return lambda$getTaskToShowPermissionDialogOn$45;
            }
        });
        return iArr[0];
    }

    public static /* synthetic */ boolean lambda$getTaskToShowPermissionDialogOn$45(final PermissionPolicyInternal permissionPolicyInternal, int i, String str, int[] iArr, TaskFragment taskFragment) {
        ActivityRecord activity = taskFragment.getActivity(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTaskToShowPermissionDialogOn$44;
                lambda$getTaskToShowPermissionDialogOn$44 = RootWindowContainer.lambda$getTaskToShowPermissionDialogOn$44(PermissionPolicyInternal.this, (ActivityRecord) obj);
                return lambda$getTaskToShowPermissionDialogOn$44;
            }
        });
        if (activity == null || !activity.isUid(i) || !Objects.equals(str, activity.packageName) || !permissionPolicyInternal.shouldShowNotificationDialogForTask(activity.getTask().getTaskInfo(), str, activity.launchedFromPackage, activity.intent, activity.getName())) {
            return false;
        }
        iArr[0] = activity.getTask().mTaskId;
        return true;
    }

    public static /* synthetic */ boolean lambda$getTaskToShowPermissionDialogOn$44(PermissionPolicyInternal permissionPolicyInternal, ActivityRecord activityRecord) {
        return activityRecord.canBeTopRunning() && activityRecord.isVisibleRequested() && !permissionPolicyInternal.isIntentToPermissionDialog(activityRecord.intent);
    }

    public int getTopFocusedDisplayId() {
        return this.mTopFocusedDisplayId;
    }

    @Override // com.android.server.wm.WindowContainer
    public void positionChildAt(int i, DisplayContent displayContent, boolean z) {
        int otherDisplayId;
        DisplayContent displayContent2;
        int displayId = displayContent.getDisplayId();
        if (i == Integer.MAX_VALUE) {
            if (this.mWmService.mExt.mExtraDisplayPolicy.shouldNotTopDisplay(displayId)) {
                Slog.i(StartingSurfaceController.TAG, "positionChildAt: can't gain focus display=" + displayContent);
                return;
            }
        } else if (i == Integer.MIN_VALUE && (otherDisplayId = this.mWmService.mExt.mExtraDisplayPolicy.getOtherDisplayId(displayId)) != -1 && (displayContent2 = this.mWmService.mRoot.getDisplayContent(otherDisplayId)) != null) {
            super.positionChildAt(Integer.MIN_VALUE, (WindowContainer) displayContent2, z);
            i = 1;
        }
        super.positionChildAt(i, (WindowContainer) displayContent, z);
    }

    public ArrayList getDumpActivities(final String str, final boolean z, boolean z2, final int i) {
        if (z2) {
            Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null) {
                return topDisplayFocusedRootTask.getDumpActivitiesLocked(str, i);
            }
            return new ArrayList();
        }
        RecentTasks recentTasks = this.mWindowManager.mAtmService.getRecentTasks();
        final int recentsComponentUid = recentTasks != null ? recentTasks.getRecentsComponentUid() : -1;
        final ArrayList arrayList = new ArrayList();
        forAllLeafTasks(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getDumpActivities$46;
                lambda$getDumpActivities$46 = RootWindowContainer.lambda$getDumpActivities$46(recentsComponentUid, z, arrayList, str, i, (Task) obj);
                return lambda$getDumpActivities$46;
            }
        });
        return arrayList;
    }

    public static /* synthetic */ boolean lambda$getDumpActivities$46(int i, boolean z, ArrayList arrayList, String str, int i2, Task task) {
        boolean z2 = task.effectiveUid == i;
        if (!z || task.shouldBeVisible(null) || z2) {
            arrayList.addAll(task.getDumpActivitiesLocked(str, i2));
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.println("topDisplayFocusedRootTask=" + getTopDisplayFocusedRootTask());
        for (int childCount = getChildCount() + (-1); childCount >= 0; childCount--) {
            ((DisplayContent) getChildAt(childCount)).dump(printWriter, str, z);
        }
    }

    public void dumpDisplayConfigs(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("Display override configurations:");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            DisplayContent displayContent = (DisplayContent) getChildAt(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.print(displayContent.mDisplayId);
            printWriter.print(": ");
            printWriter.println(displayContent.getRequestedOverrideConfiguration());
        }
    }

    public boolean dumpActivities(final FileDescriptor fileDescriptor, final PrintWriter printWriter, final boolean z, final boolean z2, final String str, int i) {
        final boolean[] zArr = {false};
        final boolean[] zArr2 = {false};
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) getChildAt(childCount);
            if (zArr[0]) {
                printWriter.println();
            }
            if (i == -1 || displayContent.mDisplayId == i) {
                printWriter.print("Display #");
                printWriter.print(displayContent.mDisplayId);
                printWriter.println(" (activities from top to bottom):");
                displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda43
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RootWindowContainer.lambda$dumpActivities$47(zArr2, printWriter, fileDescriptor, z, z2, str, zArr, (Task) obj);
                    }
                });
                displayContent.forAllTaskDisplayAreas(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda44
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RootWindowContainer.lambda$dumpActivities$49(zArr, printWriter, str, zArr2, (TaskDisplayArea) obj);
                    }
                });
            }
        }
        boolean dumpHistoryList = zArr[0] | ActivityTaskSupervisor.dumpHistoryList(fileDescriptor, printWriter, this.mTaskSupervisor.mFinishingActivities, "  ", "Fin", false, !z, false, str, true, new Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                printWriter.println("  Activities waiting to finish:");
            }
        }, null);
        zArr[0] = dumpHistoryList;
        boolean dumpHistoryList2 = ActivityTaskSupervisor.dumpHistoryList(fileDescriptor, printWriter, this.mTaskSupervisor.mStoppingActivities, "  ", "Stop", false, !z, false, str, true, new Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                printWriter.println("  Activities waiting to stop:");
            }
        }, null) | dumpHistoryList;
        zArr[0] = dumpHistoryList2;
        return dumpHistoryList2;
    }

    public static /* synthetic */ void lambda$dumpActivities$47(boolean[] zArr, PrintWriter printWriter, FileDescriptor fileDescriptor, boolean z, boolean z2, String str, boolean[] zArr2, Task task) {
        if (zArr[0]) {
            printWriter.println();
        }
        boolean dump = task.dump(fileDescriptor, printWriter, z, z2, str, false);
        zArr[0] = dump;
        zArr2[0] = dump | zArr2[0];
    }

    public static /* synthetic */ void lambda$dumpActivities$49(boolean[] zArr, final PrintWriter printWriter, String str, boolean[] zArr2, TaskDisplayArea taskDisplayArea) {
        zArr[0] = ActivityTaskSupervisor.printThisActivity(printWriter, taskDisplayArea.getFocusedActivity(), str, zArr2[0], "    Resumed: ", new Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                printWriter.println("  Resumed activities in task display areas (from top to bottom):");
            }
        }) | zArr[0];
    }

    public static int makeSleepTokenKey(String str, int i) {
        return (str + i).hashCode();
    }

    public final class SleepToken {
        public final long mAcquireTime = SystemClock.uptimeMillis();
        public final int mDisplayId;
        public final int mHashKey;
        public final boolean mIsSwappingDisplay;
        public final String mTag;

        public SleepToken(String str, int i, boolean z) {
            this.mTag = str;
            this.mDisplayId = i;
            this.mIsSwappingDisplay = z;
            this.mHashKey = RootWindowContainer.makeSleepTokenKey(str, i);
        }

        public boolean isDisplaySwapping() {
            if (SystemClock.uptimeMillis() - this.mAcquireTime > 1000) {
                return false;
            }
            return this.mIsSwappingDisplay;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"");
            sb.append(this.mTag);
            sb.append("\", display ");
            sb.append(this.mDisplayId);
            sb.append(this.mIsSwappingDisplay ? " is swapping " : "");
            sb.append(", acquire at ");
            sb.append(TimeUtils.formatUptime(this.mAcquireTime));
            sb.append("}");
            return sb.toString();
        }

        public void writeTagToProto(ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mTag);
        }
    }

    public ActivityRecord findActivityLockedByPackage(final int i, final String str) {
        final AtomicReference atomicReference = new AtomicReference();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ((DisplayContent) getChildAt(childCount)).forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RootWindowContainer.lambda$findActivityLockedByPackage$53(str, i, atomicReference, (Task) obj);
                }
            });
        }
        return (ActivityRecord) atomicReference.get();
    }

    public static /* synthetic */ void lambda$findActivityLockedByPackage$53(final String str, final int i, final AtomicReference atomicReference, Task task) {
        task.forAllActivities(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda41
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RootWindowContainer.lambda$findActivityLockedByPackage$52(str, i, atomicReference, (ActivityRecord) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$findActivityLockedByPackage$52(String str, int i, AtomicReference atomicReference, ActivityRecord activityRecord) {
        if (activityRecord.packageName.equals(str) && activityRecord.mUserId == i && !activityRecord.isState(ActivityRecord.State.FINISHING)) {
            atomicReference.set(activityRecord);
        }
    }

    public class RankTaskLayersRunnable implements Runnable {
        public RankTaskLayersRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock = RootWindowContainer.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (RootWindowContainer.this.mTaskLayersChanged) {
                        RootWindowContainer.this.mTaskLayersChanged = false;
                        RootWindowContainer.this.rankTaskLayers();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public class AttachApplicationHelper implements Consumer, Predicate {
        public WindowProcessController mApp;
        public boolean mHasActivityStarted;
        public RemoteException mRemoteException;
        public ActivityRecord mTop;

        public AttachApplicationHelper() {
        }

        public void reset() {
            this.mHasActivityStarted = false;
            this.mRemoteException = null;
            this.mApp = null;
            this.mTop = null;
        }

        public boolean process(WindowProcessController windowProcessController) {
            this.mApp = windowProcessController;
            for (int childCount = RootWindowContainer.this.getChildCount() - 1; childCount >= 0; childCount--) {
                ((DisplayContent) RootWindowContainer.this.getChildAt(childCount)).forAllRootTasks((Consumer) this);
                RemoteException remoteException = this.mRemoteException;
                if (remoteException != null) {
                    throw remoteException;
                }
            }
            if (!this.mHasActivityStarted) {
                RootWindowContainer.this.ensureActivitiesVisible(null, 0, false);
            }
            return this.mHasActivityStarted;
        }

        @Override // java.util.function.Consumer
        public void accept(Task task) {
            ActivityRecord activityRecord;
            if (this.mRemoteException != null) {
                return;
            }
            if (CoreRune.SYSFW_APP_PREL && (activityRecord = task.topRunningActivity()) != null && activityRecord.mIsPrelMode) {
                this.mTop = activityRecord;
                task.forAllActivities((Predicate) this);
            }
            if (task.getVisibility(null) == 2) {
                return;
            }
            this.mTop = task.topRunningActivity();
            task.forAllActivities((Predicate) this);
        }

        @Override // java.util.function.Predicate
        public boolean test(ActivityRecord activityRecord) {
            if (!activityRecord.finishing && activityRecord.showToCurrentUser() && ((activityRecord.visibleIgnoringKeyguard || (CoreRune.SYSFW_APP_PREL && activityRecord.mIsPrelMode)) && activityRecord.app == null)) {
                WindowProcessController windowProcessController = this.mApp;
                if (windowProcessController.mUid == activityRecord.info.applicationInfo.uid && windowProcessController.mName.equals(activityRecord.processName)) {
                    try {
                        if (RootWindowContainer.this.mTaskSupervisor.realStartActivityLocked(activityRecord, this.mApp, this.mTop == activityRecord && activityRecord.getTask().canBeResumed(activityRecord), true)) {
                            this.mHasActivityStarted = true;
                        }
                        return false;
                    } catch (RemoteException e) {
                        Slog.w(StartingSurfaceController.TAG, "Exception in new application when starting activity " + this.mTop, e);
                        this.mRemoteException = e;
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
