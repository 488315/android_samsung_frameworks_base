package com.android.wm.shell.freeform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.WindowContainerTransaction;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.protolog.ProtoLog;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.desktopmode.DesktopModeLoggerTransitionObserver;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda5;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.freeform.FreeformTaskListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda2;
import com.android.wm.shell.windowdecor.FreeformOutline;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.widget.OutlineView;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

/* loaded from: classes3.dex */
public class FreeformTaskListener implements ShellTaskOrganizer.TaskListener, ShellTaskOrganizer.FocusListener {
    public int mCaptionType;
    public final Context mContext;
    public final DesktopModeLoggerTransitionObserver mDesktopModeLoggerTransitionObserver;
    public final DesktopState mDesktopState;
    public final Optional mDesktopTasksController;
    public final Optional mDesktopUserRepositories;
    public final DisplayImeController mDisplayImeController;
    public ActivityManager.RunningTaskInfo mFocusedTaskInfo;
    public final ImePositionProcessor mImePositionProcessor;
    public final LaunchAdjacentController mLaunchAdjacentController;
    public ActivityManager.RunningTaskInfo mPrevFocusedTaskInfo;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Optional mTaskChangeListener;
    public final WindowDecorViewModel mWindowDecorationViewModel;
    public final SparseArray mTasks = new SparseArray();
    public final ArrayList mExcludeImeInsetsTarget = new ArrayList();

    public class ImePositionProcessor implements DisplayImeController.ImePositionProcessor {
        public final DesktopModeWindowDecorViewModel mDecorViewModel;
        public final int mDisplayId;
        public int mEndImeTop;
        public boolean mImeShown;
        public int mStartImeTop;

        public /* synthetic */ ImePositionProcessor(FreeformTaskListener freeformTaskListener, int i, DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i2) {
            this(i, desktopModeWindowDecorViewModel);
        }

        public final boolean canImePositioning(int i) {
            if (i != this.mDisplayId) {
                return false;
            }
            FreeformTaskListener freeformTaskListener = FreeformTaskListener.this;
            ActivityManager.RunningTaskInfo runningTaskInfo = freeformTaskListener.mFocusedTaskInfo;
            if (runningTaskInfo != null && runningTaskInfo.displayId == i) {
                return true;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo2 = freeformTaskListener.mPrevFocusedTaskInfo;
            return runningTaskInfo2 != null && runningTaskInfo2.displayId == i;
        }

        public final DesktopModeWindowDecoration getPrevFocusedDecoration() {
            ActivityManager.RunningTaskInfo runningTaskInfo = FreeformTaskListener.this.mPrevFocusedTaskInfo;
            int taskId = runningTaskInfo != null ? runningTaskInfo.getTaskId() : -1;
            if (taskId != -1) {
                return (DesktopModeWindowDecoration) this.mDecorViewModel.mWindowDecorByTaskId.get(taskId);
            }
            return null;
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeControlTargetChanged(int i, boolean z) {
            if (this.mDisplayId == i && !z && this.mImeShown) {
                this.mImeShown = false;
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeEndPositioning(int i, boolean z, SurfaceControl.Transaction transaction) {
            if (canImePositioning(i)) {
                FreeformTaskListener freeformTaskListener = FreeformTaskListener.this;
                int i2 = this.mDisplayId;
                if (z && this.mImeShown && freeformTaskListener.mDisplayImeController.isImeShowing(i2) && freeformTaskListener.mImePositionProcessor.mImeShown) {
                    Log.d("ImePositionProcessor", "onImeEndPositioning: restart show animation during showing Ime");
                    z = false;
                }
                if (z) {
                    DisplayImeController.PerDisplay perDisplay = (DisplayImeController.PerDisplay) freeformTaskListener.mDisplayImeController.mImePerDisplay.get(i2);
                    if (!(perDisplay == null ? false : perDisplay.mHasImeLeash)) {
                        z = false;
                    }
                }
                DesktopModeWindowDecoration prevFocusedDecoration = getPrevFocusedDecoration();
                int focusedTaskId = freeformTaskListener.getFocusedTaskId();
                DesktopModeWindowDecoration desktopModeWindowDecoration = focusedTaskId != -1 ? (DesktopModeWindowDecoration) this.mDecorViewModel.mWindowDecorByTaskId.get(focusedTaskId) : null;
                if (prevFocusedDecoration != null) {
                    prevFocusedDecoration.mFreeformAdjustImeController.onImeEndPositioning(transaction, z);
                    Log.d("ImePositionProcessor", "onImeEndPositioning: focus lost tid #" + getPrevFocusedDecoration());
                }
                freeformTaskListener.mPrevFocusedTaskInfo = null;
                if (desktopModeWindowDecoration != null) {
                    desktopModeWindowDecoration.mFreeformAdjustImeController.onImeEndPositioning(transaction, z);
                    Log.d("ImePositionProcessor", "onImeEndPositioning: tid #" + freeformTaskListener.getFocusedTaskId());
                }
                if (this.mImeShown) {
                    return;
                }
                setExcludeImeInsets$1(false);
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImePositionChanged(int i, int i2, SurfaceControl.Transaction transaction) {
            float f;
            if (canImePositioning(i)) {
                int i3 = this.mEndImeTop;
                int i4 = this.mStartImeTop;
                int i5 = i3 - i4;
                if (i5 == 0) {
                    Log.w("ImePositionProcessor", "getProgress: can't divide by zero");
                    f = 0.0f;
                } else {
                    f = (i2 - i4) / i5;
                }
                DesktopModeWindowDecoration prevFocusedDecoration = getPrevFocusedDecoration();
                int focusedTaskId = FreeformTaskListener.this.getFocusedTaskId();
                DesktopModeWindowDecoration desktopModeWindowDecoration = focusedTaskId != -1 ? (DesktopModeWindowDecoration) this.mDecorViewModel.mWindowDecorByTaskId.get(focusedTaskId) : null;
                if (prevFocusedDecoration != null) {
                    FreeformAdjustImeController freeformAdjustImeController = prevFocusedDecoration.mFreeformAdjustImeController;
                    int i6 = freeformAdjustImeController.mLastYOffset;
                    float f2 = i6;
                    int i7 = freeformAdjustImeController.mTargetYOffset;
                    int iM$1 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(i7, f2, f, f2);
                    if (i6 != i7 && iM$1 != freeformAdjustImeController.mYOffsetForIme) {
                        freeformAdjustImeController.mYOffsetForIme = iM$1;
                        freeformAdjustImeController.imePositionChanged(iM$1, transaction);
                    }
                }
                if (desktopModeWindowDecoration != null) {
                    FreeformAdjustImeController freeformAdjustImeController2 = desktopModeWindowDecoration.mFreeformAdjustImeController;
                    int i8 = freeformAdjustImeController2.mLastYOffset;
                    float f3 = i8;
                    int i9 = freeformAdjustImeController2.mTargetYOffset;
                    int iM$12 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(i9, f3, f, f3);
                    if (i8 == i9 || iM$12 == freeformAdjustImeController2.mYOffsetForIme) {
                        return;
                    }
                    freeformAdjustImeController2.mYOffsetForIme = iM$12;
                    freeformAdjustImeController2.imePositionChanged(iM$12, transaction);
                }
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeRequested(int i, boolean z) {
            if (i != this.mDisplayId) {
                return;
            }
            setExcludeImeInsets$1(z);
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final int onImeStartPositioning(int i, int i2, boolean z, boolean z2, int i3) {
            int i4;
            if (!canImePositioning(i)) {
                return 0;
            }
            this.mStartImeTop = z ? i2 : i3;
            if (z) {
                i2 = i3;
            }
            this.mEndImeTop = i2;
            this.mImeShown = z;
            DesktopModeWindowDecoration prevFocusedDecoration = getPrevFocusedDecoration();
            FreeformTaskListener freeformTaskListener = FreeformTaskListener.this;
            int focusedTaskId = freeformTaskListener.getFocusedTaskId();
            DesktopModeWindowDecoration desktopModeWindowDecoration = focusedTaskId != -1 ? (DesktopModeWindowDecoration) this.mDecorViewModel.mWindowDecorByTaskId.get(focusedTaskId) : null;
            if (prevFocusedDecoration != null) {
                prevFocusedDecoration.mFreeformAdjustImeController.onImeStartPositioning(false, freeformTaskListener.mPrevFocusedTaskInfo, false, this.mEndImeTop);
                StringBuilder sb = new StringBuilder("onImeStartPositioning: focus lost tid #");
                ActivityManager.RunningTaskInfo runningTaskInfo = freeformTaskListener.mPrevFocusedTaskInfo;
                RecyclerView$$ExternalSyntheticOutline0.m(runningTaskInfo != null ? runningTaskInfo.getTaskId() : -1, "ImePositionProcessor", sb);
                i4 = 0;
            } else {
                i4 = 1;
            }
            if (desktopModeWindowDecoration != null) {
                desktopModeWindowDecoration.mFreeformAdjustImeController.onImeStartPositioning(z, freeformTaskListener.mFocusedTaskInfo, true, this.mEndImeTop);
                Log.d("ImePositionProcessor", "onImeStartPositioning: tid #" + freeformTaskListener.getFocusedTaskId() + ", showing=" + z);
                i4 = 0;
            }
            if (this.mImeShown) {
                setExcludeImeInsets$1(false);
            }
            return i4;
        }

        public final void setExcludeImeInsets$1(boolean z) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            FreeformTaskListener freeformTaskListener = FreeformTaskListener.this;
            ActivityManager.RunningTaskInfo runningTaskInfo = freeformTaskListener.mFocusedTaskInfo;
            if (runningTaskInfo == null) {
                return;
            }
            if (z) {
                windowContainerTransaction.setExcludeImeInsets(runningTaskInfo.token, true);
                freeformTaskListener.mExcludeImeInsetsTarget.add(freeformTaskListener.mFocusedTaskInfo);
            } else {
                freeformTaskListener.mExcludeImeInsetsTarget.forEach(new FreeformTaskListener$$ExternalSyntheticLambda2(windowContainerTransaction));
                freeformTaskListener.mExcludeImeInsetsTarget.clear();
            }
            freeformTaskListener.mShellTaskOrganizer.applyTransaction(windowContainerTransaction);
        }

        private ImePositionProcessor(int i, DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel) {
            this.mDisplayId = i;
            this.mDecorViewModel = desktopModeWindowDecorViewModel;
        }
    }

    public class State {
        public SurfaceControl mLeash;
        public ActivityManager.RunningTaskInfo mTaskInfo;

        public /* synthetic */ State(int i) {
            this();
        }

        private State() {
        }
    }

    public FreeformTaskListener(Context context, DisplayImeController displayImeController, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Optional<DesktopUserRepositories> optional, Optional<DesktopTasksController> optional2, DesktopModeLoggerTransitionObserver desktopModeLoggerTransitionObserver, LaunchAdjacentController launchAdjacentController, WindowDecorViewModel windowDecorViewModel, Optional<TaskChangeListener> optional3, DesktopState desktopState) {
        this.mCaptionType = -1;
        this.mContext = context;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mWindowDecorationViewModel = windowDecorViewModel;
        this.mDesktopUserRepositories = optional;
        this.mDesktopTasksController = optional2;
        this.mDesktopModeLoggerTransitionObserver = desktopModeLoggerTransitionObserver;
        this.mLaunchAdjacentController = launchAdjacentController;
        this.mDesktopState = desktopState;
        this.mTaskChangeListener = optional3;
        if (((DesktopStateImpl) desktopState).isFreeformEnabled) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformTaskListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FreeformTaskListener freeformTaskListener = this.f$0;
                    boolean zInDesktopWindowing = MultiWindowManager.getInstance().inDesktopWindowing();
                    DesktopStateImpl.Companion.getClass();
                    DesktopStateImpl.Companion.setInDesktopWindowing(zInDesktopWindowing);
                    ShellTaskOrganizer shellTaskOrganizer2 = freeformTaskListener.mShellTaskOrganizer;
                    shellTaskOrganizer2.addListenerForType(freeformTaskListener, -5);
                    freeformTaskListener.mDesktopState.getClass();
                    synchronized (shellTaskOrganizer2.mLock) {
                        try {
                            shellTaskOrganizer2.mFocusListeners.add(freeformTaskListener);
                            ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer2.mLastFocusedTaskInfo;
                            if (runningTaskInfo != null) {
                                freeformTaskListener.onFocusTaskChanged(runningTaskInfo);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }, this);
        }
        int i = 0;
        if (CoreRune.MW_CAPTION_FREEFORM_TYPE) {
            this.mCaptionType = Settings.Global.getInt(context.getContentResolver(), "freeform_caption_type", 0);
        }
        if (CoreRune.MW_CAPTION && (windowDecorViewModel instanceof DesktopModeWindowDecorViewModel)) {
            this.mDisplayImeController = displayImeController;
            ImePositionProcessor imePositionProcessor = new ImePositionProcessor(this, context.getDisplayId(), (DesktopModeWindowDecorViewModel) windowDecorViewModel, i);
            this.mImePositionProcessor = imePositionProcessor;
            displayImeController.addPositionProcessor(imePositionProcessor);
        }
        shellTaskOrganizer.registerMultiWindowCoreStateListener(new ShellTaskOrganizer.MultiWindowCoreStateChangeListener() { // from class: com.android.wm.shell.freeform.FreeformTaskListener$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.ShellTaskOrganizer.MultiWindowCoreStateChangeListener
            public final boolean onMultiWindowCoreStateChanged(int i2) {
                FreeformTaskListener freeformTaskListener = this.f$0;
                int size = freeformTaskListener.mTasks.size();
                if (size < 1 || (i2 & 1) == 0 || MultiWindowCoreState.MW_ENABLED) {
                    return false;
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                for (int i3 = size - 1; i3 >= 0; i3--) {
                    FreeformTaskListener.State state = (FreeformTaskListener.State) freeformTaskListener.mTasks.valueAt(i3);
                    int displayId = state.mTaskInfo.getDisplayId();
                    boolean zIsNewDexMode = state.mTaskInfo.configuration.isNewDexMode();
                    if (displayId == 0 && !zIsNewDexMode) {
                        windowContainerTransaction.setWindowingMode(state.mTaskInfo.token, 1);
                        windowContainerTransaction.setBounds(state.mTaskInfo.token, (Rect) null);
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo = state.mTaskInfo;
                    if (runningTaskInfo.isVisible) {
                        windowContainerTransaction.reorder(runningTaskInfo.token, false);
                    }
                }
                return true;
            }
        });
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        builder.setParent(findTaskSurface(i));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump$2(PrintWriter printWriter, String str) {
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        printWriter.println(str + this);
        printWriter.println(strM + this.mTasks.size() + " tasks");
    }

    public final SurfaceControl findTaskSurface(int i) {
        if (this.mTasks.contains(i)) {
            return ((State) this.mTasks.get(i)).mLeash;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "There is no surface for taskId="));
    }

    public final int getFocusedTaskId() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mFocusedTaskInfo;
        if (runningTaskInfo != null) {
            return runningTaskInfo.getTaskId();
        }
        return -1;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean hasChild() {
        return this.mTasks.size() > 0;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean isMultiWindow() {
        return true;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.FocusListener
    public final void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.isFocused) {
            if (runningTaskInfo.getWindowingMode() == 5) {
                updateFocusedTaskInfo(runningTaskInfo);
            } else {
                updateFocusedTaskInfo(null);
            }
        }
        if (runningTaskInfo.getWindowingMode() != 5 || DesktopModeFlags.ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS.isTrue()) {
            return;
        }
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, "Freeform Task Focus Changed: #%d focused=%b", new Object[]{Integer.valueOf(runningTaskInfo.taskId), Boolean.valueOf(runningTaskInfo.isFocused)});
        if (((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode && runningTaskInfo.isFocused && this.mDesktopUserRepositories.isPresent()) {
            ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getProfile(runningTaskInfo.userId).addTask(runningTaskInfo.displayId, runningTaskInfo.taskId, runningTaskInfo.isVisible);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        if (this.mTasks.get(runningTaskInfo.taskId) != null) {
            throw new IllegalStateException("Task appeared more than once: #" + runningTaskInfo.taskId);
        }
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, "Freeform Task Appeared: #%d", new Object[]{Integer.valueOf(runningTaskInfo.taskId)});
        State state = new State(0);
        state.mTaskInfo = runningTaskInfo;
        state.mLeash = surfaceControl;
        this.mTasks.put(runningTaskInfo.taskId, state);
        if (!DesktopModeFlags.ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS.isTrue() && ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode) {
            this.mDesktopUserRepositories.ifPresent(new FreeformTaskListener$$ExternalSyntheticLambda2(runningTaskInfo, 2));
        }
        updateLaunchAdjacentController$1();
        if (runningTaskInfo.isFocused) {
            updateFocusedTaskInfo(runningTaskInfo);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        State state = (State) this.mTasks.get(runningTaskInfo.taskId);
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, "Freeform Task Info Changed: #%d", new Object[]{Integer.valueOf(runningTaskInfo.taskId)});
        this.mDesktopTasksController.ifPresent(new FreeformTaskListener$$ExternalSyntheticLambda2(runningTaskInfo, 0));
        this.mWindowDecorationViewModel.onTaskInfoChanged(runningTaskInfo);
        state.mTaskInfo = runningTaskInfo;
        if (((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode) {
            if (DesktopModeFlags.ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS.isTrue()) {
                this.mTaskChangeListener.ifPresent(new FreeformTaskListener$$ExternalSyntheticLambda2(runningTaskInfo, 1));
            } else if (this.mDesktopUserRepositories.isPresent()) {
                ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getProfile(runningTaskInfo.userId).updateTask(runningTaskInfo.displayId, runningTaskInfo.taskId, runningTaskInfo.isVisible);
            }
        }
        updateLaunchAdjacentController$1();
        if (getFocusedTaskId() == runningTaskInfo.taskId) {
            updateFocusedTaskInfo(runningTaskInfo);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, "Freeform Task Vanished: #%d", new Object[]{Integer.valueOf(runningTaskInfo.taskId)});
        this.mTasks.remove(runningTaskInfo.taskId);
        if (!DesktopModeFlags.ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS.isTrue() && ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode && this.mDesktopUserRepositories.isPresent()) {
            DesktopRepository profile = ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getProfile(runningTaskInfo.userId);
            if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue() || !profile.isMinimizedTask(runningTaskInfo.taskId)) {
                profile.desktopData.forAllDesks(new DesktopRepository$$ExternalSyntheticLambda5(runningTaskInfo.taskId, profile));
                profile.removeTask(runningTaskInfo.displayId, runningTaskInfo.taskId);
            }
        }
        DesktopModeLoggerTransitionObserver desktopModeLoggerTransitionObserver = this.mDesktopModeLoggerTransitionObserver;
        if (desktopModeLoggerTransitionObserver.visibleFreeformTaskInfos.indexOfKey(runningTaskInfo.taskId) >= 0) {
            SparseArray sparseArray = new SparseArray();
            SparseArrayKt.putAll(sparseArray, desktopModeLoggerTransitionObserver.visibleFreeformTaskInfos);
            sparseArray.remove(runningTaskInfo.taskId);
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: processing tasks after task vanished %s", new Object[]{Integer.valueOf(sparseArray.size())});
            desktopModeLoggerTransitionObserver.identifyLogEventAndUpdateState(null, null, desktopModeLoggerTransitionObserver.visibleFreeformTaskInfos, sparseArray, null);
        }
        this.mWindowDecorationViewModel.onTaskVanished(runningTaskInfo);
        updateLaunchAdjacentController$1();
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, findTaskSurface(i));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void resetStashedFreeform(int i, boolean z) {
        if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
            if (!this.mTasks.contains(i)) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "resetStashedFreeform failed. There is no task for taskId="));
            }
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) ((DesktopModeWindowDecorViewModel) this.mWindowDecorationViewModel).mWindowDecorByTaskId.get(i);
            if (desktopModeWindowDecoration == null) {
                return;
            }
            desktopModeWindowDecoration.mTaskPositioner.resetStashedFreeform(z);
        }
    }

    public final WindowContainerTransaction resizeTasksByFreeformCaptionType(int i) {
        int i2 = 1;
        if (this.mCaptionType == i) {
            return null;
        }
        this.mCaptionType = i;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            ActivityManager.RunningTaskInfo runningTaskInfo = ((State) this.mTasks.valueAt(size)).mTaskInfo;
            WindowDecorViewModel windowDecorViewModel = this.mWindowDecorationViewModel;
            if (windowDecorViewModel instanceof DesktopModeWindowDecorViewModel) {
                int i3 = this.mCaptionType;
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) ((DesktopModeWindowDecorViewModel) windowDecorViewModel).mWindowDecorByTaskId.get(runningTaskInfo.taskId);
                if (desktopModeWindowDecoration != null) {
                    if (desktopModeWindowDecoration.mCaptionType == i3) {
                        ClockEventController$$ExternalSyntheticOutline0.m(i3, "setCaptionType: caption type has already been set. type= ", "DesktopModeWindowDecoration");
                    } else {
                        if (desktopModeWindowDecoration.isHandleMenuActive()) {
                            desktopModeWindowDecoration.closeHandleMenu();
                        }
                        WindowDecoration.RelayoutParams relayoutParams = desktopModeWindowDecoration.mRelayoutParams;
                        if (relayoutParams.mCaptionType != i3) {
                            relayoutParams.mCaptionType = i3;
                        }
                        desktopModeWindowDecoration.setCaptionVisibility(desktopModeWindowDecoration.mResult.mRootView, false);
                        if (desktopModeWindowDecoration.mFreeformOutline != null) {
                            int iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(desktopModeWindowDecoration.mContext.getResources(), i3 == 0 ? R.dimen.mw_handle_freeform_inset : SystemBarUtils.getDesktopViewAppHeaderHeightId());
                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                            Rect bounds = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                            WindowDecoration.RelayoutResult relayoutResult = desktopModeWindowDecoration.mResult;
                            relayoutResult.mCaptionHeight = iLoadDimensionPixelSize;
                            if (i3 == 0) {
                                WindowDecoration.RelayoutParams relayoutParams2 = desktopModeWindowDecoration.mRelayoutParams;
                                relayoutParams2.mInsetSourceFlags = (relayoutParams2.mInsetSourceFlags & (-33)) | 64;
                            } else {
                                WindowDecoration.RelayoutParams relayoutParams3 = desktopModeWindowDecoration.mRelayoutParams;
                                relayoutParams3.mInsetSourceFlags = (relayoutParams3.mInsetSourceFlags & (-65)) | 32;
                            }
                            desktopModeWindowDecoration.updateCaptionInsets(desktopModeWindowDecoration.mRelayoutParams, windowContainerTransaction2, relayoutResult, bounds);
                            desktopModeWindowDecoration.mBgExecutor.execute(new DesktopModeWindowDecoration$$ExternalSyntheticLambda2(desktopModeWindowDecoration, windowContainerTransaction2, i2));
                            final FreeformOutline freeformOutline = desktopModeWindowDecoration.mFreeformOutline;
                            float f = iLoadDimensionPixelSize;
                            final ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
                            final boolean z = desktopModeWindowDecoration.mHasGlobalFocus;
                            final Region region = desktopModeWindowDecoration.mExclusionRegion;
                            final OutlineView outlineView = freeformOutline.getOutlineView();
                            if (outlineView != null) {
                                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(outlineView.mCaptionHeight, f);
                                valueAnimatorOfFloat.setInterpolator(InterpolatorUtils.ONE_EASING);
                                valueAnimatorOfFloat.setDuration(400L);
                                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(freeformOutline, outlineView) { // from class: com.android.wm.shell.windowdecor.FreeformOutline.1
                                    public final /* synthetic */ OutlineView val$outlineView;

                                    {
                                        this.val$outlineView = outlineView;
                                    }

                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        OutlineView outlineView2 = this.val$outlineView;
                                        if (outlineView2 != null) {
                                            outlineView2.mCaptionHeight = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                            this.val$outlineView.invalidate();
                                        }
                                    }
                                });
                                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.FreeformOutline.2
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        FreeformOutline.this.mDecoration.relayout(runningTaskInfo2, z, region);
                                    }
                                });
                                valueAnimatorOfFloat.start();
                            }
                        }
                    }
                }
            }
        }
        Settings.Global.putInt(this.mContext.getContentResolver(), "freeform_caption_type", i);
        return windowContainerTransaction;
    }

    public final String toString() {
        return "FreeformTaskListener";
    }

    public final void updateFocusedTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        if (runningTaskInfo != null && this.mDesktopTasksController.isPresent()) {
            if (((RootTaskDesksOrganizer) ((DesktopTasksController) this.mDesktopTasksController.get()).desksOrganizer).deskRootsByDeskId.contains(runningTaskInfo.taskId)) {
                return;
            }
        }
        if ((runningTaskInfo != null || this.mFocusedTaskInfo != null) && (runningTaskInfo == null || (runningTaskInfo2 = this.mFocusedTaskInfo) == null || runningTaskInfo.taskId != runningTaskInfo2.taskId)) {
            this.mPrevFocusedTaskInfo = this.mFocusedTaskInfo;
        }
        if (this.mFocusedTaskInfo != runningTaskInfo) {
            this.mFocusedTaskInfo = runningTaskInfo;
            Log.d("FreeformTaskListener", "updateFocusedTaskInfo: tid=" + getFocusedTaskId());
        }
    }

    public final void updateLaunchAdjacentController$1() {
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            return;
        }
        int i = 0;
        while (true) {
            int size = this.mTasks.size();
            LaunchAdjacentController launchAdjacentController = this.mLaunchAdjacentController;
            if (i >= size) {
                launchAdjacentController.setLaunchAdjacentEnabled(true);
                return;
            } else {
                if (((State) this.mTasks.valueAt(i)).mTaskInfo.isVisible) {
                    launchAdjacentController.setLaunchAdjacentEnabled(false);
                    return;
                }
                i++;
            }
        }
    }
}
