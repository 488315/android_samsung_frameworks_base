package com.android.server.wm;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.media.projection.MediaProjection;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Base64;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowInsets;
import android.widget.Toast;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.am.ActivityManagerService;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.DexController;
import com.android.server.uri.NeededUriGrants;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.multiwindow.IDexSnappingCallback;
import com.samsung.android.multiwindow.IDexTransientCaptionDelayListener;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DexController implements IController {
    public final ActivityTaskManagerService mAtm;
    public ActivityTaskManagerInternal.SleepTokenAcquirer mDeactivateDexSleepTokenAcquirer;
    public boolean mDexDisplayActivated;
    public float mDexFontScale;
    public final DexActivityStartInterceptor mDexInterceptor;
    public DexMetaKeyPolicy mDexMetaKeyPolicy;
    public boolean mDexStandaloneRotationEnabled;
    public boolean mDexTouchPadEnabled;
    public float mGlobalFontScaleForRestore;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public HandlerC2894H f1738mH;
    public boolean mIsDexDeveloperMode;
    public boolean mIsDexForceImmersiveModeEnabled;
    public boolean mIsInDexDisplaySizeChanging;
    public boolean mIsInDexForceImmersiveMode;
    public boolean mStartFromRecentInfo;
    public boolean mUpdatedFontScaleForDexDual;
    public WindowManagerService mWm;
    public static final boolean SAFE_DEBUG = CoreRune.SAFE_DEBUG;
    public static final Set DEFAULT_ALLOW_HOME_SET = Set.of("TP+fe8M5uStQvlunzY6n5uiGTr6ReHrxNWA2QXUmsbo=", "9jgH8FMKl5YrmkLKzhPt0BPyunVOn5QZd4RXlHG+m3U=", "ntxM9ozBwRd3xqwAhxYRewH46bxRXjgtRewzTdBekgc=", "5LmLdKeONhZxMkwo4Z8PX72qMPwRt7aEqQGAXXrBEYk=", "SPlqtyOkQMcV+iLM67vecvg2Or3jcHS+/2TBTCIcX6Q=", "5oo37SkHJlg9Fi08Q6gJjx2yE6xywWNxwerw09xkRcI=");
    public static int UPDATE_DEX_IME_STATE_DELAY_MS = FrameworkStatsLog.f446x58c6c32;
    public final ArrayList mSCPMLaunchBlockList = new PackageSpecialManagementList(PackageFeature.DEX_LAUNCH_B);
    public boolean mRequestedDexDisplayEnabled = false;
    public boolean mLastReportedDexDisplayState = false;
    public VirtualDisplay mDexDisplay = null;
    public final SparseArray mDisplayContexts = new SparseArray();
    public final Point mDexDisplaySize = new Point();
    public final DexRestartPackageList mSCPMRestartList = new DexRestartPackageList(PackageFeature.DEX_LAUNCH_RESTART);
    public final ArrayList mForceRestartList = new ArrayList() { // from class: com.android.server.wm.DexController.1
        {
            add("com.android.settings");
        }
    };
    public final PendingActivityInfo mPendingActivityInfo = new PendingActivityInfo();
    public int mLastDexMode = 0;
    public int mSourceDisplayId = -1;
    public int mTargetDisplayId = -1;
    public WindowState mLastInputMethodInputTarget = null;
    public boolean mDexImeWindowVisibleInDefaultDisplay = false;
    public final Runnable mUpdateDexImeStateRunnable = new Runnable() { // from class: com.android.server.wm.DexController.2
        @Override // java.lang.Runnable
        public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock = DexController.this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (DexController.this.updateDexImeWindowStateIfNeededLocked()) {
                        DexController.this.mWm.requestTraversal();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    };
    public int mDexOnPcState = 0;
    public final List mMinimizedToggleTasks = new ArrayList();
    public final RemoteCallbackList mDexSnappingCallbacks = new RemoteCallbackList();
    public HashMap mGameAppsMap = new HashMap();
    public boolean mIsNewDexHomeEnabled = false;
    public int mDisplayFreeformMaxCount = 0;
    public int mDexStarShowingDelayTime = -1;
    public final RemoteCallbackList mDexTransientCaptionDelayCallbacks = new RemoteCallbackList();
    public final ToBooleanFunction mCheckDexPrimayProcess = new ToBooleanFunction() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda4
        public final boolean apply(Object obj) {
            boolean lambda$new$13;
            lambda$new$13 = DexController.this.lambda$new$13((WindowProcessController) obj);
            return lambda$new$13;
        }
    };
    public final ToBooleanFunction mCheckDexProcess = new ToBooleanFunction() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda5
        public final boolean apply(Object obj) {
            boolean lambda$new$14;
            lambda$new$14 = DexController.lambda$new$14((WindowProcessController) obj);
            return lambda$new$14;
        }
    };
    public final ArrayList mWaitingTransitionFinishedTokens = new ArrayList();

    public static boolean isDefaultOrDexDisplay(int i) {
        return i == 0 || i == 2;
    }

    public static boolean isDisplayFocusChangeExcludeWindow(int i) {
        return i == 2011 || i == 2012 || i == 2019;
    }

    public final void pkgDataChanged() {
    }

    public DexController(ActivityTaskManagerService activityTaskManagerService) {
        this.mDexMetaKeyPolicy = null;
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mDexInterceptor = new DexActivityStartInterceptor(this, activityTaskManagerService);
        Objects.requireNonNull(activityTaskManagerService);
        this.mDeactivateDexSleepTokenAcquirer = activityTaskManagerService.new SleepTokenAcquirerImpl("DexController");
        this.mDexMetaKeyPolicy = new DexMetaKeyPolicy(activityTaskManagerService, this);
    }

    public final boolean isNewDexEnabled() {
        return CoreRune.SECONDARY_LAUNCHER_ACTIVITY_SUPPORT_FOR_DEX && DesktopModeSettings.getSettings(this.mAtm.mContext.getContentResolver(), "enable_new_dex_home", false);
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.f1738mH = new HandlerC2894H(this.mAtm.f1734mH.getLooper());
    }

    @Override // com.android.server.wm.IController
    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
    }

    public void doPendingStartRecent() {
        this.mStartFromRecentInfo = true;
    }

    public void finishPendingStartRecent() {
        this.mStartFromRecentInfo = false;
    }

    public boolean isPendingStartRecent() {
        return this.mStartFromRecentInfo;
    }

    public void adjustConfigurationForDexIfNeeded(Configuration configuration, WindowProcessController windowProcessController) {
        int displayId = windowProcessController.getDisplayId();
        if (displayId == -1 && windowProcessController.needBindAppToDexConfig()) {
            windowProcessController.unsetBindAppToDexConfig();
            displayId = 2;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (displayId == 2) {
                try {
                    DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(displayId);
                    if (displayContent != null) {
                        configuration.setTo(displayContent.getConfiguration());
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (this.mAtm.mDexCompatController.resolveDexCompatConfigurationLocked(null, windowProcessController.mInfo, windowProcessController.getDisplayId(), configuration, "bindApplication")) {
                windowProcessController.mIsAppliedDexCompatConfiguration = true;
            } else if (windowProcessController.mIsAppliedDexCompatConfiguration) {
                windowProcessController.mIsAppliedDexCompatConfiguration = false;
                configuration.dexCompatEnabled = 1;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void adjustDexConfigurationLocked(Configuration configuration, DisplayContent displayContent) {
        boolean z;
        DesktopModeManagerInternal desktopModeManagerInternal = (DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class);
        if (desktopModeManagerInternal == null) {
            Slog.w("DexController", "adjustDexConfigurationLocked: Cannot found DesktopModeService");
            return;
        }
        int displayId = displayContent.getDisplayId();
        int i = displayContent.getConfiguration().dexMode;
        boolean z2 = i == 2 || i == 1;
        if (displayContent.isDefaultDisplay) {
            z = desktopModeManagerInternal.isDesktopModeForPreparing(101);
        } else {
            z = displayId == 2;
        }
        if (z) {
            configuration.semDesktopModeEnabled = 1;
            configuration.dexMode = displayContent.isDefaultDisplay ? 1 : 2;
            configuration.uiMode = (this.mAtm.mWindowManager.mRoot.getConfiguration().uiMode & (-16)) | 2;
            if (displayContent.isDefaultDisplay) {
                displayContent.getDefaultTaskDisplayArea().setWindowingMode(5);
            }
            configuration.fontScale = getDexFontScale();
            return;
        }
        configuration.semDesktopModeEnabled = 0;
        configuration.dexMode = 0;
        if (displayContent.isDefaultDisplay) {
            if (z2) {
                int currentUiMode = desktopModeManagerInternal.getCurrentUiMode();
                if (currentUiMode == -1) {
                    currentUiMode = this.mAtm.mWindowManager.mRoot.getConfiguration().uiMode;
                }
                configuration.uiMode = currentUiMode;
                displayContent.getDefaultTaskDisplayArea().setWindowingMode(1);
            }
            configuration.fontScale = getGlobalFontScale();
        }
    }

    public boolean goodToChangeMode(int i, int i2) {
        if (i == 2 || i2 == 2) {
            Slog.i("DexController", "goodToChangeMode: dual-mode is not need to wait stopped sate");
            return true;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (Task task : this.mAtm.mRootWindowContainer.getDisplayContent(0).getRootTasks(0, 1)) {
                    ActivityRecord activityRecord = task.topRunningActivityLocked();
                    if (activityRecord == null) {
                        Slog.i("DexController", "goodToChangeMode: no topRunning. t" + task.mTaskId);
                    } else if (activityRecord.stateNotNeeded) {
                        Slog.i("DexController", "goodToChangeMode: stateNotNeeded, r=" + activityRecord);
                    } else {
                        switch (AbstractC28933.$SwitchMap$com$android$server$wm$ActivityRecord$State[activityRecord.getState().ordinal()]) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                Slog.i("DexController", "goodToChangeMode: t#" + task.mTaskId + " haveState=" + activityRecord.hasSavedState() + ", r=" + activityRecord);
                                break;
                            default:
                                return false;
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return true;
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    /* renamed from: com.android.server.wm.DexController$3 */
    public abstract /* synthetic */ class AbstractC28933 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$wm$ActivityRecord$State;

        static {
            int[] iArr = new int[ActivityRecord.State.values().length];
            $SwitchMap$com$android$server$wm$ActivityRecord$State = iArr;
            try {
                iArr[ActivityRecord.State.STOPPED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[ActivityRecord.State.INITIALIZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[ActivityRecord.State.FINISHING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[ActivityRecord.State.DESTROYING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[ActivityRecord.State.DESTROYED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[ActivityRecord.State.RESTARTING_PROCESS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void updateDexModeIfNeededLocked(String str) {
        int dexModeLocked = getDexModeLocked();
        if (this.mLastDexMode != dexModeLocked) {
            if (SAFE_DEBUG) {
                Slog.i("DexController", "updateDesktopModeIfNeededLocked, lastDexMode=" + this.mLastDexMode + " currentDexMode=" + dexModeLocked + " (reason:" + str + ")");
            }
            if (dexModeLocked == 0 || dexModeLocked == 2) {
                this.mAtm.mFreeformController.bindFreeformContainerService("dex_off");
            } else {
                this.mAtm.mFreeformController.unbindFreeformContainerService("dex_on");
            }
            if (dexModeLocked != 0) {
                this.f1738mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        DexController.this.lambda$updateDexModeIfNeededLocked$0();
                    }
                });
            }
            boolean z = dexModeLocked != 0;
            this.mAtm.mMultiWindowEnableController.setMultiWindowForceEnabledForUser("DexController", z ? "Desktop On" : "Desktop Off", z, -1);
            resetToggleTasksLocked();
            try {
                this.mAtm.deferWindowLayout();
                DesktopModeManagerInternal desktopModeManagerInternal = (DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class);
                int i = this.mLastDexMode;
                if (i == 2 && dexModeLocked == 1) {
                    setTasksToDisplayLocked(2, 0);
                } else if ((i == 1 && dexModeLocked == 2) || desktopModeManagerInternal.getModeToModeChangeType() == 2) {
                    setTasksToDisplayLocked(0, 2);
                }
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(0);
                int i2 = this.mLastDexMode;
                if (i2 == 1 && dexModeLocked == 0) {
                    changeAllRootTasksToFullscreenLocked(displayContent);
                } else if (i2 == 0 && dexModeLocked == 1) {
                    if (displayContent.getDefaultTaskDisplayArea().hasPinnedTask()) {
                        this.mAtm.mTaskSupervisor.removeRootTask(displayContent.getDefaultTaskDisplayArea().getRootPinnedTask());
                    }
                    if (displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
                        displayContent.getDefaultTaskDisplayArea().onStageSplitScreenDismissed(null);
                    }
                    moveTasksToFreeformLocked(displayContent);
                }
                if (dexModeLocked == 1) {
                    this.mAtm.mRootWindowContainer.getDisplayContent(0).getDefaultTaskDisplayArea().moveHomeActivityToTop("dex standalone activated");
                } else if (dexModeLocked == 2) {
                    DisplayContent displayContent2 = this.mAtm.mRootWindowContainer.getDisplayContent(2);
                    if (displayContent2 != null) {
                        displayContent2.getDefaultTaskDisplayArea().moveHomeActivityToTop("dex dual activated");
                    } else {
                        Slog.w("DexController", "updateDexModeIfNeededLocked() dexDc is null");
                    }
                }
                this.mAtm.continueWindowLayout();
                this.mAtm.mWindowManager.mExt.mPolicyExt.onDexModeChangedLw(dexModeLocked);
                if (this.mLastDexMode == 2) {
                    setDexImeWindowStateLocked(false);
                }
                this.mLastDexMode = dexModeLocked;
            } catch (Throwable th) {
                this.mAtm.continueWindowLayout();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDexModeIfNeededLocked$0() {
        this.mAtm.mMultiTaskingController.readAllSettings();
    }

    public void setTasksToDisplayLocked(int i, int i2) {
        if (this.mAtm.mRootWindowContainer.getDisplayContent(i) == null) {
            Slog.w("DexController", "moveTasksToDisplayLocked: no source display #" + i);
            return;
        }
        if (this.mAtm.mRootWindowContainer.getDisplayContent(i2) == null) {
            Slog.w("DexController", "moveTasksToDisplayLocked: no target display #" + i2);
            return;
        }
        if (i2 == 2 && !this.mDexDisplayActivated) {
            Slog.w("DexController", "moveTasksToDisplayLocked: no dex dual mode");
        } else {
            this.mSourceDisplayId = i;
            this.mTargetDisplayId = i2;
        }
    }

    public void moveTasksToDisplayIfNeededLocked(int i) {
        if (i == -1 || this.mTargetDisplayId != i) {
            return;
        }
        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(this.mSourceDisplayId);
        final DisplayContent displayContent2 = this.mAtm.mRootWindowContainer.getDisplayContent(this.mTargetDisplayId);
        if (displayContent == null || displayContent2 == null) {
            return;
        }
        displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexController.this.lambda$moveTasksToDisplayIfNeededLocked$1(displayContent2, (Task) obj);
            }
        });
        this.mSourceDisplayId = -1;
        this.mTargetDisplayId = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveTasksToDisplayIfNeededLocked$1(DisplayContent displayContent, Task task) {
        if (task.isActivityTypeHome() || task.isActivityTypeRecents() || task.mCreatedByOrganizer) {
            return;
        }
        task.getRootTask().reparent(displayContent.getDefaultTaskDisplayArea(), false);
        if (task.inFreeformWindowingMode()) {
            this.mAtm.mTaskSupervisor.getLaunchParamsController().layoutTask(task, null, null, null, null);
        }
    }

    public final void changeAllRootTasksToFullscreenLocked(DisplayContent displayContent) {
        displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexController.lambda$changeAllRootTasksToFullscreenLocked$2((Task) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$changeAllRootTasksToFullscreenLocked$2(Task task) {
        if (task.isActivityTypeHomeOrRecents()) {
            return;
        }
        boolean inFreeformWindowingMode = task.inFreeformWindowingMode();
        boolean z = task.hasOverrideBounds() && task.inFullscreenWindowingMode() && task.getRequestedOverrideWindowingMode() == 0;
        if (inFreeformWindowingMode || z) {
            if (SAFE_DEBUG) {
                Slog.d("DexController", "changeAllRootTasksToFullscreenLocked: " + task + ", overrideConfig==" + task.getRequestedOverrideConfiguration());
            }
            task.moveToBack("deactivate standalone", null);
            task.ensureActivitiesVisible(null, 0, false);
            task.setWindowingMode(1);
            if (task.hasOverrideBounds()) {
                Slog.d("DexController", "changeAllRootTasksToFullscreenLocked: resize to full, isResizeable:" + task.isResizeable());
                task.resize(null, 0, false);
            }
        }
    }

    public final void moveTasksToFreeformLocked(final DisplayContent displayContent) {
        displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexController.this.lambda$moveTasksToFreeformLocked$5(displayContent, (Task) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveTasksToFreeformLocked$5(DisplayContent displayContent, final Task task) {
        boolean z = SAFE_DEBUG;
        if (z) {
            Slog.d("DexController", "moveTasksToFreeformLocked: task=" + task);
            Slog.d("DexController", "moveTasksToFreeformLocked: prev overrideConfig=" + task.getRequestedOverrideConfiguration());
        }
        ActivityRecord rootActivity = task.getRootActivity();
        if (rootActivity == null || !rootActivity.isActivityTypeStandardOrUndefined() || !task.isActivityTypeStandardOrUndefined()) {
            if (z) {
                Slog.d("DexController", "moveTasksToFreeformLocked: skip move task=" + task);
                return;
            }
            return;
        }
        if (isExcludedTaskOrNonRecentTask(task) || getNonStartableActivityInDexMode(task) != null) {
            this.f1738mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    DexController.this.lambda$moveTasksToFreeformLocked$4(task);
                }
            });
            return;
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(5);
        if (displayContent.isDexMode()) {
            task.updateDexCompatModeLocked(null, null);
        }
        this.mAtm.mTaskSupervisor.getLaunchParamsController().layoutTask(task, null, null, null, makeBasic);
        if (displayContent.getBounds().equals(task.getBounds())) {
            task.setWindowingMode(1);
            Slog.d("DexController", "moveTaskToFreeformLocked: has fullscreen dex persistent bounds task= " + task);
        } else if (task.getRequestedOverrideWindowingMode() != 5) {
            task.setWindowingMode(5);
        }
        if (z) {
            Slog.d("DexController", "moveTasksToFreeformLocked: new overrideConfig=" + task.getRequestedOverrideConfiguration());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveTasksToFreeformLocked$4(Task task) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (SAFE_DEBUG) {
                    Slog.d("DexController", "moveTasksToFreeformLocked: remove task=" + task);
                }
                this.mAtm.mTaskSupervisor.removeTask(task, false, true, "moveTasksToFreeformLocked");
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean isExcludedTaskOrNonRecentTask(Task task) {
        Intent intent;
        if (task.inRecents && ((intent = task.intent) == null || (intent.getFlags() & 8388608) == 0)) {
            return false;
        }
        if (!SAFE_DEBUG) {
            return true;
        }
        Slog.d("DexController", "isExcludedTaskOrNonRecentTask(), Task =" + task);
        return true;
    }

    public int enableDexDisplay(int i, int i2, int i3) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT && this.mAtm.mNewDexController.mNewDexModeSet) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                setRequestedDexDisplayEnabledLocked(true);
                this.mIsNewDexHomeEnabled = isNewDexEnabled();
                if (this.mDexDisplay == null) {
                    if (!createDexDisplayLocked(i, i2, i3)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                } else {
                    DisplayInfo displayInfo = new DisplayInfo();
                    Display display = this.mDexDisplay.getDisplay();
                    DisplayContent displayContentOrCreate = this.mAtm.mRootWindowContainer.getDisplayContentOrCreate(display.getDisplayId());
                    display.getDisplayInfo(displayInfo);
                    if (i != displayInfo.logicalWidth || i2 != displayInfo.logicalHeight || i3 != displayInfo.logicalDensityDpi) {
                        setDisplaySizeAndDensityLocked(displayContentOrCreate, i, i2, i3);
                    }
                }
                this.mWm.mDisplayManagerInternal.updateDexDisplayState(true);
                int displayId = this.mDexDisplay.getDisplay().getDisplayId();
                WindowManagerService.resetPriorityAfterLockedSection();
                return displayId;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean createDexDisplayLocked(int i, int i2, int i3) {
        VirtualDisplay createVirtualDisplay = DisplayManagerGlobal.getInstance().createVirtualDisplay(this.mAtm.mContext, (MediaProjection) null, new VirtualDisplayConfig.Builder("Desktop", i, i2, i3).setFlags(265225).build(), (VirtualDisplay.Callback) null, (Executor) null);
        this.mDexDisplay = createVirtualDisplay;
        if (createVirtualDisplay == null) {
            Slog.w("DexController", "enableDexDisplay: Failed to create a display for DeX");
            return false;
        }
        this.mDexInterceptor.setDexDisplay(createVirtualDisplay);
        createDisplayContextIfNeededLocked(this.mDexDisplay.getDisplay());
        return true;
    }

    public int disableDexDisplay() {
        int updateDexDisplayState;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int dexDisplayStateLocked = getDexDisplayStateLocked();
                updateDexDisplayState = this.mWm.mDisplayManagerInternal.updateDexDisplayState(false);
                setRequestedDexDisplayEnabledLocked(false);
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(2);
                if (displayContent != null) {
                    if (isDexForceImmersiveModeEnabled()) {
                        displayContent.getInsetsPolicy().hideTransient();
                    }
                    if (dexDisplayStateLocked == 1) {
                        deactivateDexDisplayLocked(displayContent);
                        updateDexDisplayState = displayContent.getDisplayId();
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return updateDexDisplayState;
    }

    public final int getDexDisplayStateLocked() {
        VirtualDisplay virtualDisplay = this.mDexDisplay;
        if (virtualDisplay != null) {
            return virtualDisplay.getDisplay().getState();
        }
        return 0;
    }

    public final void setRequestedDexDisplayEnabledLocked(boolean z) {
        if (this.mRequestedDexDisplayEnabled != z) {
            this.mRequestedDexDisplayEnabled = z;
            Slog.i("DexController", "setRequestedDexDisplayEnabledLocked: " + z);
        }
    }

    public void activateDexDisplayLocked(DisplayContent displayContent) {
        String str;
        if (displayContent.getDisplayId() != 2) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("activateDexDisplayLocked: currentDisplayState=");
        sb.append(Display.stateToString(getDexDisplayStateLocked()));
        sb.append(", mRequestedDexDisplayEnabled=");
        sb.append(this.mRequestedDexDisplayEnabled);
        if (SAFE_DEBUG) {
            str = ", Caller=" + Debug.getCallers(3);
        } else {
            str = "";
        }
        sb.append(str);
        Slog.i("DexController", sb.toString());
        if (this.mRequestedDexDisplayEnabled && updateDexDisplayStateLocked(true)) {
            displayContent.updateDisplayOverrideConfigurationLocked();
            notifyDexDisplayStateLocked(true);
            displayContent.getDisplayPolicy().notifyDisplayReady();
            this.f1738mH.sendEmptyMessage(4);
        }
    }

    public void deactivateDexDisplayLocked(DisplayContent displayContent) {
        String str;
        if (displayContent.getDisplayId() != 2) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("deactivateDexDisplayLocked: currentDisplayState=");
        sb.append(Display.stateToString(getDexDisplayStateLocked()));
        sb.append(", mRequestedDexDisplayEnabled=");
        sb.append(this.mRequestedDexDisplayEnabled);
        if (SAFE_DEBUG) {
            str = ", Callers=" + Debug.getCallers(3);
        } else {
            str = "";
        }
        sb.append(str);
        Slog.i("DexController", sb.toString());
        if (!this.mRequestedDexDisplayEnabled && updateDexDisplayStateLocked(false)) {
            this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(2, false);
            this.mAtm.mTaskSupervisor.getKeyguardController().handleDexDisplayDisabled();
            this.mWm.moveDisplayToTop(0, "deactivateDexDisplay");
            notifyDexDisplayStateLocked(false);
        }
        this.f1738mH.sendEmptyMessage(5);
    }

    public final boolean updateDexDisplayStateLocked(boolean z) {
        VirtualDisplay virtualDisplay;
        if (this.mDexDisplayActivated == z) {
            return false;
        }
        Slog.i("DexController", "updateDexDisplayStateLocked: " + z);
        this.mDexDisplayActivated = z;
        if (z && (virtualDisplay = this.mDexDisplay) != null) {
            virtualDisplay.getDisplay().getRealSize(this.mDexDisplaySize);
        }
        updateDexModeIfNeededLocked(z ? "activate" : "deactivate");
        updateSleepTokenLocked(z);
        return true;
    }

    public final void notifyDexDisplayStateLocked(boolean z) {
        DesktopModeManagerInternal desktopModeManagerInternal = (DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class);
        if (desktopModeManagerInternal == null) {
            Slog.w("DexController", "notifyDexDisplayStateLocked: failed, dexService is null");
            return;
        }
        if (this.mLastReportedDexDisplayState != z) {
            Slog.d("DexController", "notifyDexDisplayStateLocked: dexDisplayEnabled=" + z);
            this.mLastReportedDexDisplayState = z;
            desktopModeManagerInternal.onDesktopDisplayConfigured(z);
        }
    }

    public void performDisplayOverrideConfigUpdate(int i, int i2) {
        if ((i & 7296) == 0) {
            return;
        }
        if (i2 == 2) {
            VirtualDisplay virtualDisplay = this.mDexDisplay;
            if (virtualDisplay == null) {
                Slog.w("DexController", "performDisplayOverrideConfigUpdate: mDexDisplay is null");
            } else {
                createDisplayContextIfNeededLocked(virtualDisplay.getDisplay());
                this.mDexDisplay.getDisplay().getRealSize(this.mDexDisplaySize);
            }
        }
        this.mAtm.mDexCompatController.loadResources(i2);
    }

    public final void createDisplayContextIfNeededLocked(Display display) {
        if (display.getDisplayId() != 2) {
            return;
        }
        int displayId = display.getDisplayId();
        Context createDisplayContext = this.mAtm.mContext.createDisplayContext(display);
        if (createDisplayContext != null) {
            this.mDisplayContexts.put(displayId, createDisplayContext);
            if (SAFE_DEBUG) {
                Slog.i("DexController", "createDisplayContext: #" + displayId);
            }
        }
    }

    public Context getDisplayContext(int i) {
        if (i != 0) {
            return (Context) this.mDisplayContexts.get(i, null);
        }
        return null;
    }

    public void setDisplaySizeAndDensityLocked(DisplayContent displayContent, int i, int i2, int i3) {
        int displayId = displayContent.getDisplayId();
        if (displayId != 0 && displayId != 2) {
            Slog.w("DexController", "setDisplaySizeAndDensityLocked: failed, invalid id #" + displayId);
            return;
        }
        Slog.d("DexController", "setDisplaySizeAndDensityLocked: #" + displayId + ", " + i + "x" + i2 + ", " + i3 + "dpi, Callers=" + Debug.getCallers(4));
        int i4 = displayContent.mBaseDisplayDensity;
        boolean z = i4 != i3;
        if (displayContent.mBaseDisplayWidth == i && displayContent.mBaseDisplayHeight == i2 && i4 == i3) {
            displayContent.sendNewConfiguration();
        } else if (displayId == 2) {
            try {
                this.mIsInDexDisplaySizeChanging = true;
                displayContent.setForcedSizeDensity(i, i2, i3, false, false, -1);
                Transition collectingTransition = this.mAtm.getTransitionController().getCollectingTransition();
                if (collectingTransition != null) {
                    collectingTransition.setDisplayChangeTransitionFlag(displayContent, false);
                }
            } finally {
                this.mIsInDexDisplaySizeChanging = false;
            }
        } else {
            displayContent.setForcedSizeDensity(i, i2, i3, false, false, -1);
        }
        displayContent.reconfigureDisplayLocked();
        if (z) {
            this.mAtm.mWindowManager.mInputManager.reloadPointerIcons();
        }
    }

    public int getDexModeLocked() {
        if (this.mDexDisplayActivated) {
            return 2;
        }
        if (this.mAtm.getGlobalConfiguration().semDesktopModeEnabled == 1) {
            return this.mAtm.getGlobalConfiguration().dexMode;
        }
        return 0;
    }

    public boolean isDexDisplayActivated() {
        return this.mDexDisplayActivated;
    }

    public void updateDexStandaloneRotationEnabled(final boolean z) {
        this.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                DexController.this.lambda$updateDexStandaloneRotationEnabled$6(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDexStandaloneRotationEnabled$6(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDexStandaloneRotationEnabled = z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean isDexStandaloneRotationEnabledLocked() {
        return this.mDexStandaloneRotationEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0089, code lost:
    
        if (r6 != 0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getInitProcessDisplayId(WindowProcessController windowProcessController) {
        int i;
        int displayId;
        if (!shouldRestartProcess(windowProcessController.mName)) {
            return -1;
        }
        if (windowProcessController.isKeepProcessAlive()) {
            if (SAFE_DEBUG) {
                Slog.i("DexController", "init process display: mKeepProcessAlive=true, app=" + windowProcessController);
            }
            i = 0;
        } else {
            i = -1;
        }
        if (i == -1) {
            Task taskHasActivityIsWaitingToRun = getTaskHasActivityIsWaitingToRun(windowProcessController);
            if (taskHasActivityIsWaitingToRun == null) {
                Iterator it = getPackageProcesses(windowProcessController).iterator();
                while (it.hasNext()) {
                    WindowProcessController windowProcessController2 = (WindowProcessController) it.next();
                    if (windowProcessController2 != windowProcessController && windowProcessController2.hasThread() && windowProcessController2.hasActivities()) {
                        displayId = windowProcessController2.getDisplayId();
                        Slog.d("DexController", "init process display: set packageProcessId, displayId=" + displayId + ", same_package_proc=" + windowProcessController2 + ", app=" + windowProcessController);
                        i = displayId;
                        break;
                    }
                }
            } else {
                displayId = taskHasActivityIsWaitingToRun.getDisplayId();
                if (displayId != 2) {
                }
                i = displayId;
                break;
            }
        }
        if (i == -1) {
            i = windowProcessController.getDisplayId();
        }
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public final ArrayList getPackageProcesses(WindowProcessController windowProcessController) {
        ArrayList arrayList = new ArrayList();
        SparseArray pidMap = this.mAtm.mProcessMap.getPidMap();
        for (int size = pidMap.size() - 1; size >= 0; size--) {
            WindowProcessController windowProcessController2 = (WindowProcessController) pidMap.valueAt(size);
            Iterator it = windowProcessController.getPackageList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (windowProcessController2.containsPackage((String) it.next())) {
                    arrayList.add(windowProcessController2);
                    break;
                }
            }
        }
        return arrayList;
    }

    public final Task getTaskHasActivityIsWaitingToRun(final WindowProcessController windowProcessController) {
        final String str = windowProcessController.mName;
        return this.mAtm.mRootWindowContainer.getRootTask(new Predicate() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTaskHasActivityIsWaitingToRun$7;
                lambda$getTaskHasActivityIsWaitingToRun$7 = DexController.lambda$getTaskHasActivityIsWaitingToRun$7(WindowProcessController.this, str, (Task) obj);
                return lambda$getTaskHasActivityIsWaitingToRun$7;
            }
        });
    }

    public static /* synthetic */ boolean lambda$getTaskHasActivityIsWaitingToRun$7(WindowProcessController windowProcessController, String str, Task task) {
        if (!task.shouldBeVisible(null)) {
            return false;
        }
        ActivityRecord activityRecord = task.topRunningActivity();
        if (activityRecord != null && activityRecord.app == null && windowProcessController.mUid == activityRecord.info.applicationInfo.uid && str.equals(activityRecord.processName)) {
            Slog.i("DexController", "getTaskHasActivityIsWaitingToRun: r=" + activityRecord + ", app=" + windowProcessController);
            return true;
        }
        ActivityRecord rootActivity = task.getRootActivity();
        if (activityRecord == null || str.equals(activityRecord.processName) || !activityRecord.isTaskOverlay() || activityRecord.occludesParent() || rootActivity == null || rootActivity.app != null || windowProcessController.mUid != rootActivity.info.applicationInfo.uid || !str.equals(rootActivity.processName)) {
            return false;
        }
        Slog.i("DexController", "getTaskHasActivityIsWaitingToRun: root=" + rootActivity + ", app=" + windowProcessController);
        return true;
    }

    public Point getDexDisplaySizeLocked() {
        return this.mDexDisplaySize;
    }

    public int getDexStarShowingDelayTime() {
        return this.mDexStarShowingDelayTime;
    }

    public void updateDexStarShowingDelayTime(final int i) {
        if (i != this.mDexStarShowingDelayTime) {
            this.mDexStarShowingDelayTime = i;
            synchronized (this.mDexTransientCaptionDelayCallbacks) {
                this.mDexTransientCaptionDelayCallbacks.broadcast(new Consumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DexController.lambda$updateDexStarShowingDelayTime$8(i, (IDexTransientCaptionDelayListener) obj);
                    }
                });
            }
            Slog.d("DexController", "update: mDexStarShowingDelayTime=" + this.mDexStarShowingDelayTime);
        }
    }

    public static /* synthetic */ void lambda$updateDexStarShowingDelayTime$8(int i, IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) {
        try {
            iDexTransientCaptionDelayListener.onDelayChanged(i);
        } catch (RemoteException e) {
            Slog.e("DexController", "updateDexStarShowingDelayTime. " + e);
        }
    }

    public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) {
        synchronized (this.mDexTransientCaptionDelayCallbacks) {
            this.mDexTransientCaptionDelayCallbacks.register(iDexTransientCaptionDelayListener);
        }
    }

    public final class PendingActivityInfo {
        public int mDisplayId;
        public DexRestartAppInfo mInfo;
        public boolean mIsValid;
        public final ArrayList mFindTaskResultList = new ArrayList();
        public final ArrayList mWaitingStoppedTasks = new ArrayList();
        public final ArrayList mInvisibleTasks = new ArrayList();
        public final ArrayList mWaitingTransitionFinishedTokens = new ArrayList();
        public final ArrayList mOrganizedTaskFragments = new ArrayList();

        public PendingActivityInfo() {
        }

        public void set(DexRestartAppInfo dexRestartAppInfo, ArrayList arrayList, int i) {
            this.mIsValid = true;
            this.mInfo = dexRestartAppInfo;
            this.mDisplayId = i;
            if (arrayList != null) {
                this.mFindTaskResultList.addAll(arrayList);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Task task = ((FindTaskResult) it.next()).mTask;
                    if (task.isOrganized()) {
                        this.mOrganizedTaskFragments.addAll(task.getTaskFragments());
                    }
                    ActivityRecord topActivity = task.getTopActivity(false, true);
                    if (DexController.SAFE_DEBUG) {
                        Slog.d("DexController", "set PendingActivityInfo for " + topActivity);
                    }
                    if (topActivity != null) {
                        if (topActivity.nowVisible) {
                            if (DexController.SAFE_DEBUG) {
                                Slog.d("DexController", "add waiting task #" + task.mTaskId);
                            }
                            this.mWaitingStoppedTasks.add(task);
                            if (!task.isAnimatingByRecents()) {
                                this.mWaitingTransitionFinishedTokens.add(topActivity.token);
                            }
                        } else {
                            this.mInvisibleTasks.add(task);
                        }
                    }
                }
                DexController.this.setWaitingTransitionFinished(this.mWaitingTransitionFinishedTokens);
            }
        }

        public boolean removeWaitingStoppedTask(Task task, String str) {
            if (!this.mWaitingStoppedTasks.remove(task)) {
                return false;
            }
            Slog.d("DexController", "removeWaitingStoppedTask: removed from " + task + ", reason=" + str + ", numWaitingTasks=" + this.mWaitingStoppedTasks.size());
            return true;
        }

        public boolean isWaitingStoppedTasksEmpty() {
            return this.mWaitingStoppedTasks.isEmpty();
        }

        public boolean isTransitionFinished() {
            return this.mWaitingTransitionFinishedTokens.isEmpty();
        }

        public void transitionFinished() {
            if (DexController.SAFE_DEBUG) {
                Slog.d("DexController", "transitionFinished. caller=" + Debug.getCaller());
            }
            this.mWaitingTransitionFinishedTokens.clear();
        }

        public boolean hasEmbeddedChild() {
            return this.mOrganizedTaskFragments.size() > 0;
        }

        public void embeddedDisposed(TaskFragment taskFragment) {
            this.mOrganizedTaskFragments.remove(taskFragment);
        }

        public void reset() {
            this.mIsValid = false;
            this.mDisplayId = 0;
            this.mFindTaskResultList.clear();
            this.mInfo = null;
            this.mWaitingStoppedTasks.clear();
            this.mWaitingTransitionFinishedTokens.clear();
            this.mInvisibleTasks.clear();
            DexController.this.setWaitingTransitionFinished(this.mWaitingTransitionFinishedTokens);
            this.mOrganizedTaskFragments.clear();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("DexRestartAppInfo ");
            sb.append(this.mInfo);
            sb.append(" mDisplayId = ");
            sb.append(this.mDisplayId);
            sb.append(" mFindTaskResultList size = ");
            sb.append(this.mFindTaskResultList.size());
            return sb.toString();
        }
    }

    public boolean intercept(Task task, ActivityRecord activityRecord, ActivityRecord activityRecord2, int i, int i2, int i3, ActivityOptions activityOptions, NeededUriGrants neededUriGrants, Task task2) {
        return this.mDexInterceptor.intercept(task, activityRecord, activityRecord2, i, i3, activityOptions, neededUriGrants, task2);
    }

    public boolean interceptStartActivityFromRecentsLocked(Task task, ActivityOptions activityOptions) {
        return this.mDexInterceptor.interceptStartFromRecents(task, activityOptions);
    }

    public void onTaskRemoved(Task task) {
        if (getDexModeLocked() == 2 && this.mPendingActivityInfo.removeWaitingStoppedTask(task, "taskRemoved") && this.mPendingActivityInfo.isWaitingStoppedTasksEmpty() && !this.mPendingActivityInfo.hasEmbeddedChild()) {
            scheduleReparentToDisplayAndStartPendingActivity(true);
        }
    }

    public void moveTaskToDefaultDisplayAndLayoutTask(Task task, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions) {
        task.mSkipSavingLaunchingState = true;
        if (task.mLastNonFullscreenBounds == null) {
            task.mLastNonFullscreenBounds = new Rect();
        }
        moveTaskToDisplayBackLocked(task, 0, "dex_disabled", activityOptions);
        task.mLastNonFullscreenBounds = null;
        ActivityInfo activityInfo = activityRecord.info;
        ActivityInfo.WindowLayout windowLayout = activityInfo != null ? activityInfo.windowLayout : null;
        if (activityOptions == null) {
            activityOptions = ActivityOptions.makeBasic();
        }
        ActivityOptions activityOptions2 = activityOptions;
        activityOptions2.setLaunchDisplayId(0);
        this.mAtm.mTaskSupervisor.getLaunchParamsController().layoutTask(task, windowLayout, activityRecord, activityRecord2, activityOptions2, 0);
        task.mSkipSavingLaunchingState = false;
    }

    public void scheduleKillProcessAndStartActivity(DexRestartAppInfo dexRestartAppInfo, int i) {
        HandlerC2894H handlerC2894H = this.f1738mH;
        handlerC2894H.sendMessage(handlerC2894H.obtainMessage(0, i, 0, dexRestartAppInfo));
    }

    public final void KillProcessAndStartActivity(DexRestartAppInfo dexRestartAppInfo, int i) {
        if (dexRestartAppInfo == null) {
            Slog.w("DexController", "DisplayChooserInfo is null. Abort to start pending activity");
            return;
        }
        if (SAFE_DEBUG) {
            Slog.d("DexController", "KILL_PROCESS_AND_START_ACTIVITY for " + dexRestartAppInfo);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPendingActivityInfo.reset();
                this.mPendingActivityInfo.set(dexRestartAppInfo, dexRestartAppInfo.getMovingTaskLocked(this, i), dexRestartAppInfo.mPreferredDisplayId);
                performMovingTasksBeforeKillProcessLocked(dexRestartAppInfo, "KillProcessAndStartActivity");
                Task task = dexRestartAppInfo.mReusedTask;
                if (task != null && task.getDisplayId() != dexRestartAppInfo.mPreferredDisplayId) {
                    Slog.e("DexController", "Move Task to display failed " + dexRestartAppInfo.mReusedTask);
                    performMovingTasksAfterKillProcessLocked();
                    this.mPendingActivityInfo.reset();
                    return;
                }
                WindowProcessController processController = this.mAtm.getProcessController(dexRestartAppInfo.getProcessName(), dexRestartAppInfo.getUid());
                SparseArray collectShouldKillProcess = collectShouldKillProcess(dexRestartAppInfo.mReusedTask, dexRestartAppInfo.getProcessName(), i);
                WindowManagerService.resetPriorityAfterLockedSection();
                if (processController != null) {
                    killProcessIfNeeded(processController, i, false);
                }
                if (collectShouldKillProcess.size() > 0) {
                    for (int size = collectShouldKillProcess.size() - 1; size >= 0; size--) {
                        killProcessIfNeeded((WindowProcessController) collectShouldKillProcess.valueAt(size), i, false);
                    }
                }
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        performMovingTasksAfterKillProcessLocked();
                        this.mPendingActivityInfo.reset();
                        dexRestartAppInfo.startResult(this.mAtm, i);
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public void scheduleMoveTasksBackAndStartPendingActivity(DexRestartAppInfo dexRestartAppInfo, int i) {
        HandlerC2894H handlerC2894H = this.f1738mH;
        handlerC2894H.sendMessage(handlerC2894H.obtainMessage(1, i, 0, dexRestartAppInfo));
    }

    public final void moveTasksBackAndStartPendingActivity(DexRestartAppInfo dexRestartAppInfo, int i) {
        boolean z;
        WindowProcessController windowProcessController;
        Task topRootTask;
        PictureInPictureParams pictureInPictureParams;
        if (dexRestartAppInfo == null) {
            Slog.w("DexController", "DisplayChooserInfo is null. Abort to start pending activity");
            return;
        }
        if (SAFE_DEBUG) {
            Slog.d("DexController", "MOVE_TASKS_BACK_AND_WAIT_ACTIVITY_STOP for " + dexRestartAppInfo);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ArrayList movingTaskLocked = dexRestartAppInfo.getMovingTaskLocked(this, i);
                this.mPendingActivityInfo.reset();
                z = true;
                if (!movingTaskLocked.isEmpty()) {
                    this.mPendingActivityInfo.set(dexRestartAppInfo, movingTaskLocked, i);
                    HashSet hashSet = new HashSet();
                    Iterator it = movingTaskLocked.iterator();
                    while (it.hasNext()) {
                        Task task = ((FindTaskResult) it.next()).mTask;
                        if (SAFE_DEBUG) {
                            Slog.d("DexController", "move task to bottom, task #" + task.mTaskId + " affinity=" + task.affinity + " to display #" + i + " from DisplayChooser.");
                        }
                        Task rootTask = task.inSplitScreenWindowingMode() ? task : task.getRootTask();
                        if (rootTask != null) {
                            if (!this.mDexDisplayActivated && rootTask.getDisplayId() == 2) {
                                ActivityRecord topActivity = task.getTopActivity(false, true);
                                if (topActivity != null && topActivity.isVisible()) {
                                    topActivity.setVisibility(false);
                                }
                            } else {
                                if (rootTask.isAnimatingByRecents() && this.mAtm.mWindowManager.getRecentsAnimationController() != null) {
                                    this.mAtm.mWindowManager.getRecentsAnimationController().cancelAnimationForDisplayChange();
                                }
                                hashSet.add(Integer.valueOf(rootTask.getDisplayId()));
                                task.setAvoidTrimDexPendingActivityTask(true);
                                if (this.mAtm.mTaskSupervisor.isRootVisibilityUpdateDeferred()) {
                                    this.mAtm.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                                }
                                rootTask.moveTaskToBack(task);
                                ActivityRecord topActivity2 = task.getTopActivity(false, true);
                                if (topActivity2 != null && (pictureInPictureParams = topActivity2.pictureInPictureArgs) != null && pictureInPictureParams.isAutoEnterEnabled()) {
                                    topActivity2.mTransitionController.setCanPipOnFinish(false);
                                    topActivity2.supportsEnterPipOnTaskSwitch = false;
                                }
                            }
                        }
                    }
                    Iterator it2 = hashSet.iterator();
                    while (it2.hasNext()) {
                        Integer num = (Integer) it2.next();
                        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(num.intValue());
                        if (displayContent != null && (topRootTask = displayContent.getTopRootTask()) != null) {
                            ActivityRecord topActivity3 = topRootTask.getTopActivity(false, true);
                            if (SAFE_DEBUG) {
                                Slog.d("DexController", "ensure visibility for d" + num + " next=" + topActivity3);
                            }
                            this.mAtm.mRootWindowContainer.ensureVisibilityAndConfig(topActivity3, num.intValue(), false, true);
                        }
                    }
                    if (SAFE_DEBUG) {
                        Slog.d("DexController", "Wait until activity stopped.");
                    }
                }
                if (!this.mPendingActivityInfo.isWaitingStoppedTasksEmpty() || this.mPendingActivityInfo.hasEmbeddedChild()) {
                    z = false;
                } else {
                    windowProcessController = movingTaskLocked.isEmpty() ? null : this.mAtm.getProcessController(dexRestartAppInfo.getProcessName(), dexRestartAppInfo.getUid());
                    this.mPendingActivityInfo.reset();
                }
            } finally {
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (z) {
            if (windowProcessController != null && shouldRestartProcess(windowProcessController.mName)) {
                killProcessIfNeeded(windowProcessController, i, false);
            }
            if (SAFE_DEBUG) {
                Slog.d("DexController", "start DisplayChooser result for " + dexRestartAppInfo);
            }
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    dexRestartAppInfo.startResult(this.mAtm, i);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
        }
        this.f1738mH.sendEmptyMessageDelayed(3, 4000L);
        scheduleReparentToDisplayAndStartPendingActivity(false);
    }

    public final void scheduleReparentToDisplayAndStartPendingActivity(boolean z) {
        if (SAFE_DEBUG) {
            Slog.d("DexController", "reparentToDisplayAndStartPendingActivity: immediately=" + z + ", Callers=" + Debug.getCallers(3));
        }
        this.f1738mH.removeMessages(2);
        if (z) {
            HandlerC2894H handlerC2894H = this.f1738mH;
            handlerC2894H.sendMessage(handlerC2894H.obtainMessage(2));
        } else {
            HandlerC2894H handlerC2894H2 = this.f1738mH;
            handlerC2894H2.sendMessageDelayed(handlerC2894H2.obtainMessage(2), 5000L);
        }
    }

    public final void reparentToDisplayAndStartPendingActivity() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DexRestartAppInfo dexRestartAppInfo = this.mPendingActivityInfo.mInfo;
                if (dexRestartAppInfo == null) {
                    Slog.w("DexController", "PendingActivityLaunch is null. Abort to start pending activity");
                    return;
                }
                if (SAFE_DEBUG) {
                    Slog.d("DexController", "REPARENT_TO_DISPLAY_AND_START_PENDING_ACTIVITY for " + dexRestartAppInfo);
                }
                performMovingTasksBeforeKillProcessLocked(dexRestartAppInfo, "reparentToDisplayAndStartPendingActivity");
                int i = this.mPendingActivityInfo.mDisplayId;
                WindowProcessController processController = this.mAtm.getProcessController(dexRestartAppInfo.getProcessName(), dexRestartAppInfo.getUid());
                SparseArray collectShouldKillProcess = collectShouldKillProcess(dexRestartAppInfo.mReusedTask, dexRestartAppInfo.getProcessName(), i);
                WindowManagerService.resetPriorityAfterLockedSection();
                if (processController != null) {
                    killProcessIfNeeded(processController, i, false);
                }
                if (collectShouldKillProcess.size() > 0) {
                    for (int size = collectShouldKillProcess.size() - 1; size >= 0; size--) {
                        killProcessIfNeeded((WindowProcessController) collectShouldKillProcess.valueAt(size), i, false);
                    }
                }
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        if (SAFE_DEBUG) {
                            Slog.d("DexController", "start DisplayChooser result for " + dexRestartAppInfo);
                        }
                        performMovingTasksAfterKillProcessLocked();
                        dexRestartAppInfo.startResult(this.mAtm, i);
                        this.mPendingActivityInfo.reset();
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public SparseArray collectShouldKillProcess(Task task, final String str, final int i) {
        final SparseArray sparseArray = new SparseArray();
        if (task != null) {
            task.forAllActivities(new Consumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DexController.this.lambda$collectShouldKillProcess$10(str, i, sparseArray, (ActivityRecord) obj);
                }
            }, true);
        }
        return sparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collectShouldKillProcess$10(String str, int i, SparseArray sparseArray, ActivityRecord activityRecord) {
        if (activityRecord.app == null || str.equals(activityRecord.processName) || !shouldKillProcess(activityRecord.app, i)) {
            return;
        }
        sparseArray.put(activityRecord.app.getPid(), activityRecord.app);
    }

    public void KillProcessAndWaitDisposed(DexRestartAppInfo dexRestartAppInfo, final int i) {
        if (dexRestartAppInfo == null) {
            Slog.w("DexController", "DisplayChooserInfo is null. Abort to kill and wait disposed");
            return;
        }
        final WindowProcessController processController = this.mAtm.getProcessController(dexRestartAppInfo.getProcessName(), dexRestartAppInfo.getUid());
        if (processController != null) {
            this.mPendingActivityInfo.reset();
            this.mPendingActivityInfo.set(dexRestartAppInfo, dexRestartAppInfo.getMovingTaskLocked(this, i), dexRestartAppInfo.mPreferredDisplayId);
            this.f1738mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    DexController.this.lambda$KillProcessAndWaitDisposed$11(processController, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$KillProcessAndWaitDisposed$11(WindowProcessController windowProcessController, int i) {
        killProcessIfNeeded(windowProcessController, i, false);
    }

    public final void performMovingTasksBeforeKillProcessLocked(DexRestartAppInfo dexRestartAppInfo, String str) {
        ActivityInfo activityInfo;
        PendingActivityLaunch pendingActivityLaunch;
        Iterator it = this.mPendingActivityInfo.mFindTaskResultList.iterator();
        while (it.hasNext()) {
            FindTaskResult findTaskResult = (FindTaskResult) it.next();
            Task task = findTaskResult.mTask;
            if (task == null) {
                Slog.w("DexController", str + ": skip handle task, " + findTaskResult.mTask);
            } else {
                task.mSkipSavingLaunchingState = true;
                if (task.mLastNonFullscreenBounds == null) {
                    task.mLastNonFullscreenBounds = new Rect();
                }
                findTaskResult.execute(str);
                if (findTaskResult.mTask.getDisplayId() == dexRestartAppInfo.mPreferredDisplayId) {
                    Task task2 = findTaskResult.mTask;
                    ActivityInfo.WindowLayout windowLayout = null;
                    task2.mLastNonFullscreenBounds = null;
                    ActivityRecord rootActivity = task2.getRootActivity();
                    if (rootActivity == null && (pendingActivityLaunch = dexRestartAppInfo.mPal) != null) {
                        rootActivity = pendingActivityLaunch.f1747r;
                    }
                    ActivityRecord activityRecord = rootActivity;
                    PendingActivityLaunch pendingActivityLaunch2 = dexRestartAppInfo.mPal;
                    ActivityRecord activityRecord2 = pendingActivityLaunch2 != null ? pendingActivityLaunch2.sourceRecord : null;
                    if (activityRecord != null && (activityInfo = activityRecord.info) != null) {
                        windowLayout = activityInfo.windowLayout;
                    }
                    dexRestartAppInfo.mOptions.setLaunchDisplayId(dexRestartAppInfo.mPreferredDisplayId);
                    this.mAtm.mTaskSupervisor.getLaunchParamsController().layoutTask(findTaskResult.mTask, windowLayout, activityRecord, activityRecord2, dexRestartAppInfo.mOptions, dexRestartAppInfo.mPreferredDisplayId);
                }
                findTaskResult.mTask.setAvoidTrimDexPendingActivityTask(false);
            }
        }
    }

    public final void performMovingTasksAfterKillProcessLocked() {
        Iterator it = this.mPendingActivityInfo.mFindTaskResultList.iterator();
        while (it.hasNext()) {
            ((FindTaskResult) it.next()).mTask.mSkipSavingLaunchingState = false;
        }
    }

    public static /* synthetic */ boolean lambda$isStoppedLocked$12(ActivityRecord activityRecord) {
        return !activityRecord.mAppStopped;
    }

    public final boolean isStoppedLocked(Task task) {
        return task.getActivity(new Predicate() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isStoppedLocked$12;
                lambda$isStoppedLocked$12 = DexController.lambda$isStoppedLocked$12((ActivityRecord) obj);
                return lambda$isStoppedLocked$12;
            }
        }) == null;
    }

    public void activityStopped(ActivityRecord activityRecord) {
        WindowProcessController windowProcessController;
        WindowProcessController windowProcessController2;
        if (SAFE_DEBUG) {
            Slog.d("DexController", "activityStoppedLocked : " + activityRecord);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean z = true;
                if (!this.mPendingActivityInfo.isWaitingStoppedTasksEmpty()) {
                    Task task = activityRecord.getTask();
                    if (task != null && isStoppedLocked(task)) {
                        this.mPendingActivityInfo.removeWaitingStoppedTask(task, "activityStopped");
                    }
                    if (this.mPendingActivityInfo.isWaitingStoppedTasksEmpty() && this.mPendingActivityInfo.isTransitionFinished()) {
                        if (this.mPendingActivityInfo.hasEmbeddedChild() && task != null && task.getRootProcess() != null && shouldRestartProcess(task.getRootProcess().mName)) {
                            windowProcessController = task.getRootProcess();
                            windowProcessController2 = activityRecord.app;
                            if (windowProcessController2 != null || windowProcessController2.getDisplayId() != 2 || activityRecord.app.isHomeProcess()) {
                                z = false;
                            }
                            if (!this.mDexDisplayActivated && z && activityRecord.app.allActivitiesStoppedAndInvisibleLocked()) {
                                windowProcessController = activityRecord.app;
                            }
                        } else {
                            Slog.d("DexController", "reparentToDisplayAndStartPendingActivity from activityStopped");
                            scheduleReparentToDisplayAndStartPendingActivity(true);
                        }
                    }
                }
                windowProcessController = null;
                windowProcessController2 = activityRecord.app;
                if (windowProcessController2 != null) {
                }
                z = false;
                if (!this.mDexDisplayActivated) {
                    windowProcessController = activityRecord.app;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (windowProcessController != null) {
            Slog.d("DexController", "killprocess from activityStopped");
            killProcessIfNeeded(windowProcessController, 2, "proc_dex_display_disabled", false);
        }
    }

    public void destroyedActivityStopped(ActivityRecord activityRecord) {
        WindowProcessController windowProcessController;
        if (SAFE_DEBUG) {
            Slog.d("DexController", "destroyedActivityStopped : " + activityRecord);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowProcessController = (WindowProcessController) this.mAtm.mProcessNames.get(activityRecord.processName, activityRecord.info.applicationInfo.uid);
                boolean z = (windowProcessController == null || windowProcessController.getDisplayId() != 2 || windowProcessController.isHomeProcess()) ? false : true;
                if (this.mDexDisplayActivated || !z || !windowProcessController.allActivitiesStoppedAndInvisibleLocked()) {
                    windowProcessController = null;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (windowProcessController != null) {
            Slog.d("DexController", "killprocess from destroyedActivityStopped");
            killProcessIfNeeded(windowProcessController, 0, "proc_dex_display_disabled", false);
        }
    }

    public void activityDestroyed(ActivityRecord activityRecord) {
        WindowProcessController windowProcessController;
        if (SAFE_DEBUG) {
            Slog.d("DexController", "activityDestroyed : " + activityRecord);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowProcessController = (WindowProcessController) this.mAtm.mProcessNames.get(activityRecord.processName, activityRecord.info.applicationInfo.uid);
                boolean z = (windowProcessController == null || windowProcessController.getDisplayId() != 2 || windowProcessController.isHomeProcess()) ? false : true;
                if (this.mDexDisplayActivated || !z || !windowProcessController.allActivitiesStoppedAndInvisibleLocked()) {
                    windowProcessController = null;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (windowProcessController != null) {
            Slog.d("DexController", "killprocess from activityDestroyed");
            killProcessIfNeeded(windowProcessController, 0, "proc_dex_display_disabled", true);
        }
    }

    public void embeddedDisposed(TaskFragment taskFragment) {
        if (SAFE_DEBUG) {
            Slog.d("DexController", "embeddedDisposed");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPendingActivityInfo.embeddedDisposed(taskFragment);
                if (!this.mPendingActivityInfo.hasEmbeddedChild() && this.mPendingActivityInfo.isWaitingStoppedTasksEmpty()) {
                    scheduleReparentToDisplayAndStartPendingActivity(true);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$13(WindowProcessController windowProcessController) {
        if (!isPrimaryDesktopDisplay(windowProcessController.getPrimaryDisplayName()) || !shouldKillProcess(windowProcessController, 2)) {
            return false;
        }
        if (!CoreRune.SAFE_DEBUG) {
            return true;
        }
        Slog.i("DexController", "mCheckDexPrimayProcess: " + windowProcessController);
        return true;
    }

    public static /* synthetic */ boolean lambda$new$14(WindowProcessController windowProcessController) {
        if (windowProcessController.getPid() == ActivityManagerService.MY_PID || windowProcessController.getDisplayId() != 2 || windowProcessController.isHomeProcess()) {
            return false;
        }
        boolean allActivitiesStoppedAndInvisibleLocked = windowProcessController.allActivitiesStoppedAndInvisibleLocked();
        if (CoreRune.SAFE_DEBUG) {
            Slog.i("DexController", "killAllProcessInDexDisplayLocked: allStoppedAndInvisible=" + allActivitiesStoppedAndInvisibleLocked + ", " + windowProcessController);
        }
        return allActivitiesStoppedAndInvisibleLocked;
    }

    public final void killAllProcessIfNeeded(ToBooleanFunction toBooleanFunction, String str) {
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                SparseArray pidMap = this.mAtm.mProcessMap.getPidMap();
                for (int size = pidMap.size() - 1; size >= 0; size--) {
                    WindowProcessController windowProcessController = (WindowProcessController) pidMap.valueAt(size);
                    if (toBooleanFunction.apply(windowProcessController)) {
                        arrayList.add(windowProcessController);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            killProcessIfNeeded((WindowProcessController) it.next(), 2, str, false);
        }
    }

    public boolean killProcessIfNeeded(WindowProcessController windowProcessController, int i, boolean z) {
        return killProcessIfNeeded(windowProcessController, i, "proc_display_changed", z);
    }

    public boolean killProcessIfNeeded(WindowProcessController windowProcessController, int i, String str, boolean z) {
        if (!shouldKillProcess(windowProcessController, i, str)) {
            return false;
        }
        String str2 = str + "(" + windowProcessController.getDisplayId() + "," + i + ")";
        if (z) {
            this.mAtm.f1734mH.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda8
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((ActivityManagerInternal) obj).killProcessForDex(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (String) obj4);
                }
            }, this.mAtm.mAmInternal, Integer.valueOf(windowProcessController.getPid()), Integer.valueOf(i), str2));
            return true;
        }
        this.mAtm.mAmInternal.killProcessForDex(windowProcessController.getPid(), i, str2);
        return true;
    }

    public boolean shouldKillProcess(WindowProcessController windowProcessController, int i) {
        return shouldKillProcess(windowProcessController, i, "proc_display_changed");
    }

    public boolean shouldKillProcess(WindowProcessController windowProcessController, int i, String str) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!isDefaultOrDexDisplay(i)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (this.mAtm.getProcessController(windowProcessController.mName, windowProcessController.mUid) != windowProcessController) {
                    if (SAFE_DEBUG) {
                        Slog.d("DexController", "Ignoring remove of inactive process: " + windowProcessController);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if ("proc_display_changed".equals(str)) {
                    if (windowProcessController.getDisplayId() == i) {
                        if (SAFE_DEBUG) {
                            Slog.d("DexController", "already in same display: " + windowProcessController);
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (i == 2 && !this.mDexDisplayActivated) {
                        if (SAFE_DEBUG) {
                            Slog.d("DexController", "Dex display is not activated: " + windowProcessController);
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (windowProcessController.getDisplayId() == -1 && !shouldRestartProcess(windowProcessController.mName)) {
                        if (SAFE_DEBUG) {
                            Slog.d("DexController", "No matter: " + windowProcessController + " d" + windowProcessController.getDisplayId());
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                }
                if (windowProcessController.isKeepProcessAlive()) {
                    if (SAFE_DEBUG) {
                        Slog.i("DexController", "Do not kill keepProcessAlive process, app=" + windowProcessController);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (windowProcessController.getPid() == ActivityManagerService.MY_PID) {
                    if (CoreRune.SAFE_DEBUG) {
                        Slog.w("DexController", "Do not kill system process, app=" + windowProcessController + " callers=" + Debug.getCallers(2));
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (!"startActivityForDexRestart".equals(str) && !"toggleFreeformWindowingMode".equals(str)) {
                    if (shouldRestartProcess(windowProcessController.mName)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void notifyAppTransitionFinishedIfNeeded(ActivityRecord activityRecord) {
        if (SAFE_DEBUG) {
            Slog.i("DexController", "notifyAppTransitionFinishedIfNeeded: " + activityRecord + " callers=" + Debug.getCallers(3));
        }
        if (this.mWaitingTransitionFinishedTokens.contains(activityRecord)) {
            this.mWaitingTransitionFinishedTokens.clear();
            this.f1738mH.sendEmptyMessage(6);
        }
    }

    public void setWaitingTransitionFinished(ArrayList arrayList) {
        this.mWaitingTransitionFinishedTokens.clear();
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            IBinder iBinder = (IBinder) it.next();
            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
            if (forTokenLocked != null) {
                if (SAFE_DEBUG) {
                    Slog.i("DexController", "setWaitingTransitionFinished: add " + iBinder);
                }
                this.mWaitingTransitionFinishedTokens.add(forTokenLocked);
            }
        }
    }

    public void notifyAppTransitionFinished() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Slog.d("DexController", "notifyAppTransitionFinished. isTransitionFinished=" + this.mPendingActivityInfo.isTransitionFinished());
                if (!this.mPendingActivityInfo.isTransitionFinished()) {
                    this.mPendingActivityInfo.transitionFinished();
                    this.f1738mH.removeMessages(3);
                    if (this.mPendingActivityInfo.isWaitingStoppedTasksEmpty() && !this.mPendingActivityInfo.hasEmbeddedChild()) {
                        scheduleReparentToDisplayAndStartPendingActivity(true);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void moveTaskToDisplayBackLocked(Task task, int i, String str, ActivityOptions activityOptions) {
        DisplayContent displayContent;
        if (task.getDisplayId() == i || (displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i)) == null || this.mAtm.mRootWindowContainer.getRootTask(task.getRootTaskId()) == null) {
            return;
        }
        Task orCreateRootTask = displayContent.getDefaultTaskDisplayArea().getOrCreateRootTask(task.getTopActivity(false, true), activityOptions != null ? activityOptions : ActivityOptions.makeBasic(), task, null, null, 0, task.getActivityType(), false);
        task.clearSizeCompatMode(false, true);
        if (orCreateRootTask.isOrganized() && orCreateRootTask != task) {
            task.getRequestedOverrideConfiguration().windowConfiguration.setBounds((Rect) null);
        }
        task.reparent(orCreateRootTask, false, 2, false, true, str);
        if (!(task.getDisplayId() == i)) {
            orCreateRootTask.removeImmediately();
        }
        if (SAFE_DEBUG) {
            Slog.d("DexController", "moveTaskToDisplayBackLocked: to d" + i + " " + task + " (reason=" + str + ")");
        }
    }

    public ArrayList getTaskLocked(String str, int i, boolean z, int i2) {
        return getTaskLocked(str, i, z, false, i2);
    }

    public ArrayList getTaskLocked(final String str, final int i, boolean z, boolean z2, int i2) {
        ActivityRecord activity;
        ActivityRecord activity2;
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            if (SAFE_DEBUG) {
                Slog.w("DexController", "getTaskLocked: processName=" + str);
            }
            return arrayList;
        }
        for (int childCount = this.mAtm.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) this.mAtm.mRootWindowContainer.getChildAt(childCount);
            if (displayContent.mDisplayId != i2) {
                final ArrayList arrayList2 = new ArrayList();
                displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DexController.lambda$getTaskLocked$15(arrayList2, (Task) obj);
                    }
                });
                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                    Task task = (Task) arrayList2.get(size);
                    if (z2 && !task.getTaskFragments().isEmpty() && (activity2 = task.getActivity(new Predicate() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$getTaskLocked$16;
                            lambda$getTaskLocked$16 = DexController.lambda$getTaskLocked$16(str, i, (ActivityRecord) obj);
                            return lambda$getTaskLocked$16;
                        }
                    }, false)) != null) {
                        arrayList.add(new FindTaskResult(activity2, i2));
                        return arrayList;
                    }
                    if ((!z || task.getTopActivity(false, true).isVisible()) && (activity = task.getActivity(new Predicate() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$getTaskLocked$17;
                            lambda$getTaskLocked$17 = DexController.lambda$getTaskLocked$17(str, i, (ActivityRecord) obj);
                            return lambda$getTaskLocked$17;
                        }
                    }, false)) != null) {
                        FindTaskResult findTaskResult = new FindTaskResult(activity, i2);
                        arrayList.add(findTaskResult);
                        if (SAFE_DEBUG) {
                            Slog.i("DexController", "getTaskLocked: add " + findTaskResult);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static /* synthetic */ void lambda$getTaskLocked$15(ArrayList arrayList, Task task) {
        if (task.isActivityTypeHomeOrRecents() || !task.isLeafTask() || task.getTopActivity(false, true) == null) {
            return;
        }
        arrayList.add(task);
    }

    public static /* synthetic */ boolean lambda$getTaskLocked$16(String str, int i, ActivityRecord activityRecord) {
        return str.equals(activityRecord.processName) && activityRecord.getUid() == i;
    }

    public static /* synthetic */ boolean lambda$getTaskLocked$17(String str, int i, ActivityRecord activityRecord) {
        return str.equals(activityRecord.processName) && activityRecord.getUid() == i;
    }

    public final class FindTaskResult {
        public final boolean mIsRootTask;
        public final String mProcessName;
        public final int mTargetDisplayId;
        public final Task mTask;
        public final int mUid;

        public FindTaskResult(ActivityRecord activityRecord, int i) {
            Task task = activityRecord.getTask();
            this.mTask = task;
            this.mIsRootTask = activityRecord.equals(task.getRootActivity());
            this.mUid = activityRecord.getUid();
            this.mProcessName = activityRecord.processName;
            this.mTargetDisplayId = i;
        }

        public void execute(String str) {
            if (this.mIsRootTask) {
                ActivityOptions activityOptions = DexController.this.mPendingActivityInfo.mInfo != null ? DexController.this.mPendingActivityInfo.mInfo.mOptions : null;
                if (activityOptions != null && (DexController.this.mPendingActivityInfo.mInfo.mReusedTask == null || DexController.this.mPendingActivityInfo.mInfo.mReusedTask != this.mTask)) {
                    DexController.this.moveTaskToDisplayBackLocked(this.mTask, this.mTargetDisplayId, str, null);
                    return;
                } else {
                    DexController.this.moveTaskToDisplayBackLocked(this.mTask, this.mTargetDisplayId, str, activityOptions);
                    return;
                }
            }
            final AtomicInteger atomicInteger = new AtomicInteger();
            this.mTask.forAllActivities(new Consumer() { // from class: com.android.server.wm.DexController$FindTaskResult$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DexController.FindTaskResult.this.lambda$execute$0(atomicInteger, (ActivityRecord) obj);
                }
            });
            if (atomicInteger.get() > -1) {
                Slog.d("DexController", "FindTaskResult_execute: performClear(Ndx=" + atomicInteger + "), " + this.mTask + ", reason=" + str);
                this.mTask.removeActivities(str, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$execute$0(AtomicInteger atomicInteger, ActivityRecord activityRecord) {
            if (this.mProcessName.equals(activityRecord.processName) && activityRecord.getUid() == this.mUid) {
                atomicInteger.set(this.mTask.getChildCount());
            }
        }

        public String toString() {
            return "{" + this.mTask + " targetDisplayId=" + this.mTargetDisplayId + " isRoot=" + this.mIsRootTask + "}";
        }
    }

    class DexRestartPackageList extends PackageSpecialManagementList {
        public DexRestartPackageList(PackageFeature packageFeature) {
            super(packageFeature);
        }

        @Override // com.samsung.android.server.packagefeature.util.PackageSpecialManagementList, com.samsung.android.server.packagefeature.PackageFeatureCallback
        public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
            super.onPackageFeatureDataChanged(packageFeatureData);
            DexController.this.pkgDataChanged();
        }
    }

    public boolean shouldRestartProcess(String str) {
        return this.mSCPMRestartList.contains(str) || (CoreRune.MW_EMBED_ACTIVITY && this.mForceRestartList.contains(str));
    }

    public static boolean isPrimaryDefaultDisplay(String str) {
        return "default".equals(str);
    }

    public static boolean isPrimaryDesktopDisplay(String str) {
        return "desktop".equals(str);
    }

    public int getDisplayIdFromPrimaryMetaDataLocked(String str) {
        if (str == null) {
            return -1;
        }
        if (isPrimaryDefaultDisplay(str)) {
            return 0;
        }
        return (isPrimaryDesktopDisplay(str) && this.mDexDisplayActivated) ? 2 : -1;
    }

    public void startDexHomeLocked(int i) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = i;
        HandlerC2894H handlerC2894H = this.f1738mH;
        handlerC2894H.sendMessage(handlerC2894H.obtainMessage(7, obtain));
    }

    public void showCanNotSwitchUserToast() {
        HandlerC2894H handlerC2894H = this.f1738mH;
        handlerC2894H.sendMessage(handlerC2894H.obtainMessage(10));
    }

    public final void updateSleepTokenLocked(boolean z) {
        if (z) {
            this.mDeactivateDexSleepTokenAcquirer.release(2);
            Slog.i("DexController", "updateSleepTokenLocked: sleepToken is released");
        } else {
            this.mDeactivateDexSleepTokenAcquirer.acquire(2);
            Slog.i("DexController", "updateSleepTokenLocked: sleepToken is acquired");
        }
    }

    public void setDoNotShowAgainChecked(boolean z) {
        this.mDexInterceptor.setDoNotShowAgainChecked(z);
    }

    public void updateDexDeveloperMode(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mIsDexDeveloperMode != z) {
                    Slog.d("DexController", "updateDexDeveloperMode prev=" + this.mIsDexDeveloperMode + " cur=" + z);
                    this.mIsDexDeveloperMode = z;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public int getDexPolicyFlags(ApplicationInfo applicationInfo) {
        return getDexPolicyFlags(null, applicationInfo);
    }

    public int getDexPolicyFlags(ActivityInfo activityInfo, ApplicationInfo applicationInfo) {
        if (isNotSupportApp(applicationInfo)) {
            return 2;
        }
        int i = (!isNotSupportHomeApp(activityInfo, applicationInfo) || this.mIsNewDexHomeEnabled) ? 0 : 4;
        return isGameApp(applicationInfo) ? i | 8 : i;
    }

    public final boolean isNotSupportApp(ApplicationInfo applicationInfo) {
        Bundle bundle = applicationInfo.metaData;
        if (bundle == null) {
            try {
                this.mAtm.mContext.getPackageManager().getApplicationInfo(applicationInfo.packageName, PackageManager.ApplicationInfoFlags.of(128L));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        if (bundle != null && bundle.getBoolean("com.samsung.android.dex.launchpolicy.notsupported")) {
            return true;
        }
        synchronized (this.mSCPMLaunchBlockList) {
            return this.mSCPMLaunchBlockList.contains(applicationInfo.packageName);
        }
    }

    public final boolean isNotSupportHomeApp(ActivityInfo activityInfo, ApplicationInfo applicationInfo) {
        Bundle bundle;
        String str = applicationInfo.packageName;
        if (str != null && !DEFAULT_ALLOW_HOME_SET.contains(toHashText(str)) && (activityInfo == null || (bundle = activityInfo.metaData) == null || !bundle.getBoolean("com.samsung.android.dex.launchpolicy.allow_home_activity", false))) {
            if (this.mAtm.mContext.getPackageManager().resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").setPackage(applicationInfo.packageName), PackageManager.ResolveInfoFlags.of(65536L), UserHandle.getUserId(applicationInfo.uid)) != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean isGameApp(ApplicationInfo applicationInfo) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mGameAppsMap.values().removeIf(new Predicate() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isGameApp$18;
                lambda$isGameApp$18 = DexController.lambda$isGameApp$18(elapsedRealtime, (Pair) obj);
                return lambda$isGameApp$18;
            }
        });
        Pair pair = (Pair) this.mGameAppsMap.get(applicationInfo.packageName);
        if (pair != null) {
            if (CoreRune.IS_DEBUG_LEVEL_MID) {
                Slog.d("DexController", "isGameApp : hit= " + applicationInfo.packageName);
            }
            return ((Boolean) pair.first).booleanValue();
        }
        try {
            boolean isGamePackage = SemGameManager.isGamePackage(applicationInfo.packageName);
            this.mGameAppsMap.put(applicationInfo.packageName, new Pair(Boolean.valueOf(isGamePackage), Long.valueOf(elapsedRealtime)));
            if (CoreRune.IS_DEBUG_LEVEL_MID) {
                Slog.d("DexController", "isGameApp : put=" + applicationInfo.packageName);
            }
            return isGamePackage;
        } catch (Exception unused) {
            return false;
        }
    }

    public static /* synthetic */ boolean lambda$isGameApp$18(long j, Pair pair) {
        return j - ((Long) pair.second).longValue() > 10000;
    }

    public ActivityRecord getNonStartableActivityInDexMode(Task task) {
        for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
            ActivityRecord asActivityRecord = task.getChildAt(childCount).asActivityRecord();
            if (asActivityRecord != null && shouldAbortStartActivity(asActivityRecord.info)) {
                return asActivityRecord;
            }
        }
        return null;
    }

    public boolean shouldAbortStartActivity(ActivityInfo activityInfo) {
        return (getDexPolicyFlags(activityInfo, activityInfo.applicationInfo) & 6) != 0;
    }

    public void showWarningToastIfNeeded(ActivityInfo activityInfo) {
        showWarningToastIfNeeded(activityInfo, null);
    }

    public void showWarningToastIfNeeded(ActivityInfo activityInfo, Task task) {
        final String warningStringFromDexPolicy;
        ActivityRecord activityRecord;
        if ((task == null || (activityRecord = task.topRunningActivity()) == null || !activityRecord.isState(ActivityRecord.State.RESUMED) || !isGameApp(activityInfo.applicationInfo)) && (warningStringFromDexPolicy = getWarningStringFromDexPolicy(activityInfo)) != null) {
            final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mAtm.mContext, R.style.Theme.DeviceDefault.Light);
            this.f1738mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    DexController.lambda$showWarningToastIfNeeded$19(contextThemeWrapper, warningStringFromDexPolicy);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$showWarningToastIfNeeded$19(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }

    public String getWarningStringFromDexPolicy(ActivityInfo activityInfo) {
        int dexPolicyFlags = getDexPolicyFlags(activityInfo, activityInfo.applicationInfo);
        Resources resources = this.mAtm.mContext.getResources();
        if ((dexPolicyFlags & 2) != 0) {
            return resources.getString(R.string.policydesc_disableKeyguardFeatures, loadLabel(activityInfo));
        }
        if ((dexPolicyFlags & 4) != 0) {
            return resources.getString(R.string.policydesc_encryptedStorage, loadLabel(activityInfo));
        }
        if ((dexPolicyFlags & 8) != 0) {
            return resources.getString(R.string.policydesc_expirePassword);
        }
        return null;
    }

    public final String loadLabel(ActivityInfo activityInfo) {
        CharSequence loadLabel = activityInfo.loadLabel(this.mAtm.mContext.getPackageManager());
        return loadLabel != null ? loadLabel.toString() : "";
    }

    public String toHashText(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(Charset.defaultCharset()));
            return Base64.encodeToString(messageDigest.digest(), 2);
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public boolean canBeDexImeAnchorLayer(WindowToken windowToken) {
        DisplayContent displayContent = windowToken.getDisplayContent();
        if (displayContent == null || !displayContent.isDefaultDisplay || !shouldShowDexImeInDefaultDisplayLocked()) {
            return false;
        }
        TaskbarController taskbarController = displayContent.getDisplayPolicy().mExt.getTaskbarController();
        return (taskbarController != null && taskbarController.isTaskbarToken(windowToken)) || windowToken.getWindowType() == 2019;
    }

    public void setInputMethodInputTargetLocked(WindowState windowState) {
        if (this.mLastInputMethodInputTarget != windowState) {
            this.mLastInputMethodInputTarget = windowState;
        }
    }

    public boolean shouldShowDexImeInDefaultDisplayLocked() {
        DisplayContent defaultDisplayContentLocked;
        boolean z = (!isLastInputTargetInDexDisplay() || (defaultDisplayContentLocked = this.mAtm.mWindowManager.getDefaultDisplayContentLocked()) == null || defaultDisplayContentLocked.mInputMethodWindow == null) ? false : true;
        updateDexImeWindowStateIfNeededLocked();
        return z;
    }

    public void onDexImeClientVisibleChangedLocked(boolean z) {
        if (z == this.mDexImeWindowVisibleInDefaultDisplay) {
            return;
        }
        if (z) {
            updateDexImeWindowStateIfNeededLocked();
        } else {
            this.f1738mH.removeCallbacks(this.mUpdateDexImeStateRunnable);
            this.f1738mH.postDelayed(this.mUpdateDexImeStateRunnable, UPDATE_DEX_IME_STATE_DELAY_MS);
        }
    }

    public boolean updateDexImeWindowStateIfNeededLocked() {
        DisplayContent defaultDisplayContentLocked;
        WindowState windowState;
        return setDexImeWindowStateLocked(isLastInputTargetInDexDisplay() && (defaultDisplayContentLocked = this.mAtm.mWindowManager.getDefaultDisplayContentLocked()) != null && (windowState = defaultDisplayContentLocked.mInputMethodWindow) != null && windowState.isVisible());
    }

    public final boolean setDexImeWindowStateLocked(boolean z) {
        if (this.mDexImeWindowVisibleInDefaultDisplay == z) {
            return false;
        }
        this.mDexImeWindowVisibleInDefaultDisplay = z;
        if (CoreRune.IS_DEBUG_LEVEL_MID) {
            Slog.i("DexController", "setDexImeWindowStateLocked: " + z + " Callers=" + Debug.getCallers(3));
        }
        this.mAtm.mWindowManager.mInputManager.setDexImePolicy(z);
        return true;
    }

    public final boolean isLastInputTargetInDexDisplay() {
        WindowState windowState;
        return getDexModeLocked() == 2 && (windowState = this.mLastInputMethodInputTarget) != null && windowState.getDisplayId() == 2;
    }

    public boolean forceShowSystemBars() {
        return this.mDexImeWindowVisibleInDefaultDisplay;
    }

    public final WindowState getCandidateImeTargetForDexLocked() {
        DisplayContent defaultDisplayContentLocked = this.mAtm.mWindowManager.getDefaultDisplayContentLocked();
        InputTarget imeInputTarget = defaultDisplayContentLocked.getImeInputTarget();
        if (imeInputTarget != null) {
            return imeInputTarget.getWindowState();
        }
        if (defaultDisplayContentLocked.getImeFallback() != null) {
            return defaultDisplayContentLocked.getImeFallback().getWindow();
        }
        return null;
    }

    public boolean showDexImeOnDefaultDisplayLocked() {
        WindowState candidateImeTargetForDexLocked = getCandidateImeTargetForDexLocked();
        if (candidateImeTargetForDexLocked == null) {
            return false;
        }
        DisplayContent defaultDisplayContentLocked = this.mAtm.mWindowManager.getDefaultDisplayContentLocked();
        WindowState window = candidateImeTargetForDexLocked.getImeControlTarget().getWindow();
        WindowState windowState = defaultDisplayContentLocked.mInputMethodWindow;
        defaultDisplayContentLocked.getInsetsStateController().getImeSourceProvider().scheduleShowImePostLayout(window, null);
        if (windowState != null && !windowState.isVisible() && windowState.isDrawn()) {
            windowState.mWinAnimator.resetDrawState();
            windowState.forceReportingResized();
        }
        if (!CoreRune.SAFE_DEBUG) {
            return true;
        }
        Slog.d("DexController", "showDexImeOnDefaultDisplayLocked: imeTarget=" + window);
        return true;
    }

    public boolean hideDexImeOnDefaultDisplayLocked() {
        boolean z;
        DisplayContent defaultDisplayContentLocked = this.mAtm.mWindowManager.getDefaultDisplayContentLocked();
        defaultDisplayContentLocked.getInsetsStateController().getImeSourceProvider().abortShowImePostLayout();
        InsetsControlTarget imeTarget = defaultDisplayContentLocked.getImeTarget(2);
        if (imeTarget != null) {
            z = true;
            imeTarget.hideInsets(WindowInsets.Type.ime(), true, null);
            if (CoreRune.SAFE_DEBUG) {
                Slog.d("DexController", "hideDexImeOnDefaultDisplayLocked: Callers=" + Debug.getCallers(3));
            }
        } else {
            z = false;
        }
        defaultDisplayContentLocked.getInsetsStateController().getImeSourceProvider().setImeShowing(false);
        return z;
    }

    public DexMetaKeyPolicy getDexMetaKeyPolicy() {
        return this.mDexMetaKeyPolicy;
    }

    public class DexMetaDataInfo {
        public DexController$Utils$TypedMetaDataValue mHeightValue;
        public DexController$Utils$TypedMetaDataValue mWidthValue;

        public DexMetaDataInfo(String str, String str2) {
            this.mWidthValue = DexController$Utils$TypedMetaDataValue.parseSizeMetaData(str);
            this.mHeightValue = DexController$Utils$TypedMetaDataValue.parseSizeMetaData(str2);
        }
    }

    public static DexMetaDataInfo parseDexMetadata(ActivityInfo activityInfo) {
        Object obj;
        Object obj2;
        String str;
        String str2;
        Bundle bundle = activityInfo.metaData;
        if (bundle == null) {
            bundle = null;
        }
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        Bundle bundle2 = applicationInfo != null ? applicationInfo.metaData : null;
        if (bundle == null && bundle2 == null) {
            return null;
        }
        if (bundle != null && bundle.get("com.samsung.android.dex.launchwidth") != null && bundle.get("com.samsung.android.dex.launchheight") != null) {
            obj = bundle.get("com.samsung.android.dex.launchwidth");
            obj2 = bundle.get("com.samsung.android.dex.launchheight");
        } else if (bundle2 == null || bundle2.get("com.samsung.android.dex.launchwidth") == null || bundle2.get("com.samsung.android.dex.launchheight") == null) {
            obj = null;
            obj2 = null;
        } else {
            Object obj3 = bundle2.get("com.samsung.android.dex.launchwidth");
            obj2 = bundle2.get("com.samsung.android.dex.launchheight");
            obj = obj3;
        }
        if (obj == null || obj2 == null) {
            str = null;
            str2 = null;
        } else {
            if (obj instanceof Integer) {
                str = Integer.toString(((Integer) obj).intValue());
            } else {
                str = obj instanceof String ? (String) obj : null;
            }
            if (obj2 instanceof Integer) {
                str2 = Integer.toString(((Integer) obj2).intValue());
            } else {
                str2 = obj2 instanceof String ? (String) obj2 : null;
            }
        }
        if (str == null || str2 == null) {
            return null;
        }
        return new DexMetaDataInfo(str, str2);
    }

    public Point getDexMetadataLaunchSizeLocked(DexMetaDataInfo dexMetaDataInfo, int i, boolean z, int i2) {
        DexController$Utils$TypedMetaDataValue dexController$Utils$TypedMetaDataValue;
        DexController$Utils$TypedMetaDataValue dexController$Utils$TypedMetaDataValue2;
        if (dexMetaDataInfo == null || (dexController$Utils$TypedMetaDataValue = dexMetaDataInfo.mWidthValue) == null || (dexController$Utils$TypedMetaDataValue2 = dexMetaDataInfo.mHeightValue) == null) {
            return null;
        }
        if (DexController$Utils$TypedMetaDataValue.isFullscreen(dexController$Utils$TypedMetaDataValue, dexController$Utils$TypedMetaDataValue2)) {
            return new Point(0, 0);
        }
        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null) {
            return null;
        }
        Configuration configuration = displayContent.getConfiguration();
        int dimensionPixelSize = DexController$Utils$TypedMetaDataValue.getDimensionPixelSize(dexMetaDataInfo.mWidthValue, configuration.densityDpi, this.mDexDisplaySize.x, 960);
        int dimensionPixelSize2 = DexController$Utils$TypedMetaDataValue.getDimensionPixelSize(dexMetaDataInfo.mHeightValue, configuration.densityDpi, this.mDexDisplaySize.y, 720);
        Rect appBounds = configuration.windowConfiguration.getAppBounds();
        Point point = new Point();
        point.x = Math.min(appBounds.width(), dimensionPixelSize);
        point.y = Math.min(appBounds.height(), dimensionPixelSize2);
        if (CoreRune.MD_DEX_COMPAT_CAPTION_WINDOW && z && appBounds.height() < dimensionPixelSize2) {
            point.y -= this.mAtm.mDexCompatController.getDecorCaptionHeight(i, i2);
        }
        return point;
    }

    public void setDexTouchPadEnabledLocked(boolean z) {
        if (this.mDexTouchPadEnabled != z) {
            this.mDexTouchPadEnabled = z;
            Slog.i("DexController", "setDexTouchPadEnabledLocked: enabled=" + this.mDexTouchPadEnabled);
        }
    }

    public boolean isDexTouchPadEnabledLocked() {
        return this.mDexTouchPadEnabled;
    }

    /* renamed from: com.android.server.wm.DexController$H */
    public final class HandlerC2894H extends Handler {
        public HandlerC2894H(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:56:0x0123, code lost:
        
            if (r0.getEnabled() == 4) goto L44;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(Message message) {
            boolean hasMinimizedToggleTasksLocked;
            switch (message.what) {
                case 0:
                    DexController.this.KillProcessAndStartActivity((DexRestartAppInfo) message.obj, message.arg1);
                    return;
                case 1:
                    DexController.this.moveTasksBackAndStartPendingActivity((DexRestartAppInfo) message.obj, message.arg1);
                    return;
                case 2:
                    DexController.this.reparentToDisplayAndStartPendingActivity();
                    return;
                case 3:
                    WindowManagerGlobalLock windowManagerGlobalLock = DexController.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            DexController.this.mPendingActivityInfo.transitionFinished();
                            Slog.w("DexController", "DISPLAY_REPARENT_TRANSITION_TIMEOUT " + DexController.this.mPendingActivityInfo);
                            DexController.this.setWaitingTransitionFinished(null);
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 4:
                    DexController dexController = DexController.this;
                    dexController.killAllProcessIfNeeded(dexController.mCheckDexPrimayProcess, "proc_dex_display_enabled");
                    return;
                case 5:
                    DexController dexController2 = DexController.this;
                    dexController2.killAllProcessIfNeeded(dexController2.mCheckDexProcess, "proc_dex_display_disabled");
                    return;
                case 6:
                    try {
                        DexController.this.notifyAppTransitionFinished();
                        return;
                    } catch (Exception unused) {
                        Slog.w("DexController", "Failed to notify AppTransitionFinished");
                        return;
                    }
                case 7:
                    Slog.d("DexController", "handleMessage: START_DEX_HOME");
                    int i = ((SomeArgs) message.obj).argi1;
                    DesktopModeManagerInternal desktopModeManagerInternal = (DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class);
                    if (desktopModeManagerInternal == null) {
                        Slog.w("DexController", "startHomeOnDexDisplay: Cannot found DesktopModeService");
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    try {
                        WindowManagerGlobalLock windowManagerGlobalLock2 = DexController.this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                hasMinimizedToggleTasksLocked = DexController.this.hasMinimizedToggleTasksLocked() & true;
                                if (!hasMinimizedToggleTasksLocked) {
                                    DexController.this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(i, true);
                                }
                            } finally {
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Slog.d("DexController", "START_DEX_HOME: minimizeAll for " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                        long currentTimeMillis2 = System.currentTimeMillis();
                        try {
                            desktopModeManagerInternal.startHome();
                            Slog.d("DexController", "START_DEX_HOME: startHome for " + (System.currentTimeMillis() - currentTimeMillis2) + "ms");
                            long currentTimeMillis3 = System.currentTimeMillis();
                            try {
                                SemDesktopModeState desktopModeState = desktopModeManagerInternal.getDesktopModeState();
                                WindowManagerGlobalLock windowManagerGlobalLock3 = DexController.this.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock3) {
                                    if (hasMinimizedToggleTasksLocked) {
                                        if (i != 2) {
                                            try {
                                                break;
                                            } finally {
                                            }
                                        }
                                        DexController.this.restoreToggleTasksToFrontLocked(i);
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            } finally {
                                Slog.d("DexController", "START_DEX_HOME: restoreToggle for " + (System.currentTimeMillis() - currentTimeMillis3) + "ms");
                            }
                        } catch (Throwable th) {
                            Slog.d("DexController", "START_DEX_HOME: startHome for " + (System.currentTimeMillis() - currentTimeMillis2) + "ms");
                            throw th;
                        }
                    } catch (Throwable th2) {
                        Slog.d("DexController", "START_DEX_HOME: minimizeAll for " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                        throw th2;
                    }
                case 8:
                case 9:
                default:
                    return;
                case 10:
                    Toast.makeText(new ContextThemeWrapper(DexController.this.mAtm.mContext, R.style.Theme.DeviceDefault.Light), DexController.this.mAtm.mContext.getResources().getString(R.string.foreground_service_multiple_separator), 0).show();
                    return;
                case 11:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    int i2 = someArgs.argi1;
                    Rect rect = (Rect) someArgs.arg1;
                    synchronized (DexController.this.mDexSnappingCallbacks) {
                        int beginBroadcast = DexController.this.mDexSnappingCallbacks.beginBroadcast();
                        for (int i3 = 0; i3 < beginBroadcast; i3++) {
                            try {
                                DexController.this.mDexSnappingCallbacks.getBroadcastItem(i3).onWindowSnappingChanged(i2, rect);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        DexController.this.mDexSnappingCallbacks.finishBroadcast();
                    }
                    return;
            }
        }
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[DexController]");
        printWriter.println(str + "mDexDisplayActivated=" + this.mDexDisplayActivated);
        printWriter.println(str + "mIsDexDeveloperMode=" + this.mIsDexDeveloperMode);
        printWriter.println(str + "mIsDexForceImmersiveModeEnabled=" + this.mIsDexForceImmersiveModeEnabled);
        printWriter.println(str + "mIsInDexForceImmersiveMode=" + this.mIsInDexForceImmersiveMode);
        printWriter.println(str + "mDexStandaloneRotationEnabled=" + this.mDexStandaloneRotationEnabled);
        if (getDexModeLocked() == 2) {
            printWriter.println(str + "mDexImeWindowVisibleInDefaultDisplay=" + this.mDexImeWindowVisibleInDefaultDisplay);
            printWriter.println(str + "mLastInputMethodInputTarget=" + this.mLastInputMethodInputTarget);
        }
        printWriter.println();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int getDexTaskInfoFlagsLocked(Task task) {
        boolean inMultiWindowMode = task.inMultiWindowMode();
        boolean z = inMultiWindowMode;
        if (ActivityInfo.isPreserveOrientationMode(task.mResizeMode)) {
            z = (inMultiWindowMode ? 1 : 0) | 4;
        }
        ?? r0 = z;
        if (task.isResizeable()) {
            r0 = (z ? 1 : 0) | 2;
        }
        return this.mIsDexDeveloperMode ? r0 | 2 : r0;
    }

    public float getGlobalFontScale() {
        return this.mGlobalFontScaleForRestore;
    }

    public void setGlobalFontScale(float f) {
        this.mGlobalFontScaleForRestore = f;
    }

    public float getDexFontScale() {
        return this.mDexFontScale;
    }

    public void updateDexFontScaleIfNeeded(float f) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int dexModeLocked = getDexModeLocked();
                boolean z = dexModeLocked == 2;
                if (this.mDexFontScale == f && (!z || this.mUpdatedFontScaleForDexDual)) {
                    Slog.d("DexController", "updateDexFontScaleIfNeeded: DexFontScale is same as scaleFactor " + f);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mDexFontScale = f;
                int i = z ? 2 : 0;
                if (dexModeLocked != 0 && dexModeLocked != 3) {
                    this.mUpdatedFontScaleForDexDual = z;
                    if (i == 2) {
                        this.mAtm.mExt.getCoreStateController().setVolatileState("dex_font_scale", Float.valueOf(f), 0, true, true, null);
                        this.mAtm.mRootWindowContainer.getDisplayContent(2).reconfigureDisplayLocked();
                    } else {
                        Configuration computeNewConfiguration = this.mAtm.mWindowManager.computeNewConfiguration(i);
                        if (computeNewConfiguration == null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        computeNewConfiguration.fontScale = f;
                        if (dexModeLocked == 1) {
                            this.mAtm.mWindowManager.startFreezingDisplay(0, 0);
                        }
                        this.mAtm.updateConfigurationLocked(computeNewConfiguration, null, false, false, -10000, false, null);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean handleDexMinimizeToggleLocked(DisplayContent displayContent) {
        if (!hasMinimizedToggleTasksLocked()) {
            return false;
        }
        restoreToggleTasksToFrontLocked(displayContent.mDisplayId);
        return true;
    }

    public void addToggleTaskLocked(Task task) {
        if (this.mMinimizedToggleTasks.contains(task)) {
            return;
        }
        this.mMinimizedToggleTasks.add(task);
    }

    public void removeToggleTaskLocked(Task task) {
        if (task == null || !this.mMinimizedToggleTasks.contains(task)) {
            return;
        }
        this.mMinimizedToggleTasks.remove(task);
    }

    public void restoreToggleTasksToFrontLocked(int i) {
        ArrayList arrayList = new ArrayList(this.mMinimizedToggleTasks);
        TransitionController transitionController = this.mAtm.getTransitionController();
        boolean z = transitionController.getCollectingTransition() == null;
        resetToggleTasksLocked();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Task task = (Task) arrayList.get(size);
            Task rootTask = task.getRootTask();
            if (rootTask != null && task.getDisplayId() == i) {
                if (z) {
                    transitionController.requestStartTransition(transitionController.createTransition(1), null, null, null);
                    z = false;
                }
                ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
                rootTask.moveTaskToFront(task, false, null, topNonFinishingActivity != null ? topNonFinishingActivity.appTimeTracker : null, "restoreToggleTasksToFrontLocked");
            }
        }
    }

    public void resetToggleTasksLocked() {
        this.mMinimizedToggleTasks.clear();
    }

    public boolean hasMinimizedToggleTasksLocked() {
        return !this.mMinimizedToggleTasks.isEmpty();
    }

    public boolean isInDexForceImmersiveMode() {
        return this.mIsInDexForceImmersiveMode;
    }

    public final void setDexForceImmersiveModeIn(boolean z) {
        this.mIsInDexForceImmersiveMode = z;
    }

    public boolean isDexForceImmersiveModeEnabled() {
        return this.mIsDexForceImmersiveModeEnabled;
    }

    public final void setDexForceImmersiveModeEnabled(boolean z) {
        this.mIsDexForceImmersiveModeEnabled = z;
    }

    public void updateForceImmersiveModeSetting(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (z != this.mIsDexForceImmersiveModeEnabled) {
                    setDexForceImmersiveModeEnabled(z);
                    Slog.d("DexController", "updateForceImmersiveModeSetting: mIsDexForceImmersiveModeEnabled=" + this.mIsDexForceImmersiveModeEnabled);
                    this.f1738mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            DexController.this.lambda$updateForceImmersiveModeSetting$20();
                        }
                    });
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateForceImmersiveModeSetting$20() {
        DisplayContent defaultDisplay;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
                if (dexModeLocked == 2) {
                    defaultDisplay = this.mAtm.mRootWindowContainer.getDisplayContent(2);
                } else {
                    defaultDisplay = dexModeLocked == 1 ? this.mAtm.mRootWindowContainer.getDefaultDisplay() : null;
                }
                if (defaultDisplay != null) {
                    defaultDisplay.getDisplayPolicy().mDecorInsets.invalidate();
                    defaultDisplay.reconfigureDisplayLocked();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void updateForceImmersiveModeState(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (z != this.mIsInDexForceImmersiveMode) {
                    setDexForceImmersiveModeIn(z);
                    Slog.d("DexController", "updateForceImmersiveModeSkip: mIsInDexForceImmersiveMode=" + this.mIsInDexForceImmersiveMode);
                    this.f1738mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda20
                        @Override // java.lang.Runnable
                        public final void run() {
                            DexController.this.lambda$updateForceImmersiveModeState$21();
                        }
                    });
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateForceImmersiveModeState$21() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int dexModeLocked = getDexModeLocked();
                int i = 2;
                if (dexModeLocked != 2) {
                    i = dexModeLocked == 1 ? 0 : -1;
                }
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.setLayoutNeeded();
                    this.mAtm.mWindowManager.mWindowPlacerLocked.performSurfacePlacement();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void scheduleNotifyDexSnappingCallback(int i, Rect rect) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.arg1 = rect;
        HandlerC2894H handlerC2894H = this.f1738mH;
        handlerC2894H.sendMessage(handlerC2894H.obtainMessage(11, obtain));
    }

    public void registerDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback) {
        synchronized (this.mDexSnappingCallbacks) {
            this.mDexSnappingCallbacks.register(iDexSnappingCallback);
        }
    }

    public void unregisterDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback) {
        synchronized (this.mDexSnappingCallbacks) {
            this.mDexSnappingCallbacks.unregister(iDexSnappingCallback);
        }
    }

    public void bringTaskToForeground(int i, int i2, int i3) {
        if (DesktopModeFeature.DEBUG) {
            Slog.d("DexController", "bringTaskToForeground(), taskId=" + i + ", targetDisplayId=" + i2 + ", targetWindowingMode=" + i3);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i);
                if (anyTaskForId != null && anyTaskForId.isActivityTypeStandard()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchDisplayId(i2);
                    makeBasic.setLaunchWindowingMode(i3);
                    ActivityRecord rootActivity = anyTaskForId.getRootActivity();
                    if (rootActivity == null) {
                        Slog.d("DexController", "bringTaskToForeground(): rootActivity is null.");
                        try {
                            this.mAtm.startActivityFromRecents(i, makeBasic.toBundle());
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    scheduleMoveTasksBackAndStartPendingActivity(DexRestartAppInfo.createStartActivityFromRecentsType(rootActivity.processName, rootActivity.getUid(), rootActivity.info.applicationInfo, anyTaskForId, makeBasic, i2), i2);
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getDisplayFreeformMaxCount() {
        return this.mDisplayFreeformMaxCount;
    }

    public void setDisplayFreeformMaxCount(int i) {
        this.mDisplayFreeformMaxCount = i;
    }
}
