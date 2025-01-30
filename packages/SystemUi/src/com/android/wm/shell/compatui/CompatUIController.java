package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIWindowManager;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CompatUIController implements DisplayController.OnDisplaysChangedListener, DisplayImeController.ImePositionProcessor, KeyguardChangeListener {
    public LetterboxEduWindowManager mActiveLetterboxEduLayout;
    public ReachabilityEduWindowManager mActiveReachabilityEduLayout;
    public CompatUICallback mCallback;
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final CompatUIShellCommandHandler mCompatUIShellCommandHandler;
    public final Context mContext;
    public boolean mDeferVisibilityUpdate;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final DockStateReader mDockStateReader;
    public final CompatUIController$$ExternalSyntheticLambda0 mHandleFixedRotation;
    public final DisplayImeController mImeController;
    public boolean mKeyguardShowing;
    public final ShellExecutor mMainExecutor;
    public final ShellController mShellController;
    public final SyncTransactionQueue mSyncQueue;
    public final Lazy mTransitionsLazy;
    public boolean mWaitingForFixedRotation;
    public final Set mDisplaysWithIme = new ArraySet(1);
    public final SparseArray mOnInsetsChangedListeners = new SparseArray(0);
    public final SparseArray mActiveCompatLayouts = new SparseArray(0);
    public final SparseArray mActiveBoundsCompatLayouts = new SparseArray(0);
    public final SparseArray mTaskIdToRestartDialogWindowManagerMap = new SparseArray(0);
    public final Set mSetOfTaskIdsShowingRestartDialog = new HashSet();
    public final SparseArray mDisplayContextCache = new SparseArray(0);
    public final CompatUIWindowManager.CompatUIHintsState mCompatUIHintsState = new CompatUIWindowManager.CompatUIHintsState();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CompatUICallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PerDisplayOnInsetsChangedListener implements DisplayInsetsController.OnInsetsChangedListener {
        public final int mDisplayId;
        public final InsetsState mInsetsState = new InsetsState();

        public PerDisplayOnInsetsChangedListener(int i) {
            this.mDisplayId = i;
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            InsetsState insetsState2 = this.mInsetsState;
            if (insetsState2.equals(insetsState)) {
                return;
            }
            insetsState2.set(insetsState);
            CompatUIController compatUIController = CompatUIController.this;
            DisplayController displayController = compatUIController.mDisplayController;
            int i = this.mDisplayId;
            compatUIController.forAllLayouts(new CompatUIController$$ExternalSyntheticLambda4(i), new CompatUIController$$ExternalSyntheticLambda3(displayController.getDisplayLayout(i), 0));
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
            insetsChanged(insetsState);
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda0] */
    public CompatUIController(Context context, ShellInit shellInit, ShellController shellController, DisplayController displayController, DisplayInsetsController displayInsetsController, DisplayImeController displayImeController, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor, Lazy lazy, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration, CompatUIShellCommandHandler compatUIShellCommandHandler) {
        final int i = 1;
        final int i2 = 0;
        this.mHandleFixedRotation = new Runnable(this) { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUIController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        CompatUIController compatUIController = this.f$0;
                        compatUIController.mWaitingForFixedRotation = false;
                        if (compatUIController.mDeferVisibilityUpdate) {
                            compatUIController.mDeferVisibilityUpdate = false;
                            compatUIController.forAllLayouts(new CompatUIController$$ExternalSyntheticLambda2(2), new CompatUIController$$ExternalSyntheticLambda1(compatUIController, 6));
                            break;
                        }
                        break;
                    default:
                        CompatUIController compatUIController2 = this.f$0;
                        CopyOnWriteArrayList copyOnWriteArrayList = compatUIController2.mShellController.mKeyguardChangeListeners;
                        copyOnWriteArrayList.remove(compatUIController2);
                        copyOnWriteArrayList.add(compatUIController2);
                        compatUIController2.mDisplayController.addDisplayWindowListener(compatUIController2);
                        compatUIController2.mImeController.addPositionProcessor(compatUIController2);
                        CompatUIShellCommandHandler compatUIShellCommandHandler2 = compatUIController2.mCompatUIShellCommandHandler;
                        compatUIShellCommandHandler2.mShellCommandHandler.addCommandCallback("compatui", compatUIShellCommandHandler2, compatUIShellCommandHandler2);
                        break;
                }
            }
        };
        this.mContext = context;
        this.mShellController = shellController;
        this.mDisplayController = displayController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mImeController = displayImeController;
        this.mSyncQueue = syncTransactionQueue;
        this.mMainExecutor = shellExecutor;
        this.mTransitionsLazy = lazy;
        this.mDockStateReader = dockStateReader;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mCompatUIShellCommandHandler = compatUIShellCommandHandler;
        shellInit.addInitCallback(new Runnable(this) { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda0
            public final /* synthetic */ CompatUIController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        CompatUIController compatUIController = this.f$0;
                        compatUIController.mWaitingForFixedRotation = false;
                        if (compatUIController.mDeferVisibilityUpdate) {
                            compatUIController.mDeferVisibilityUpdate = false;
                            compatUIController.forAllLayouts(new CompatUIController$$ExternalSyntheticLambda2(2), new CompatUIController$$ExternalSyntheticLambda1(compatUIController, 6));
                            break;
                        }
                        break;
                    default:
                        CompatUIController compatUIController2 = this.f$0;
                        CopyOnWriteArrayList copyOnWriteArrayList = compatUIController2.mShellController.mKeyguardChangeListeners;
                        copyOnWriteArrayList.remove(compatUIController2);
                        copyOnWriteArrayList.add(compatUIController2);
                        compatUIController2.mDisplayController.addDisplayWindowListener(compatUIController2);
                        compatUIController2.mImeController.addPositionProcessor(compatUIController2);
                        CompatUIShellCommandHandler compatUIShellCommandHandler2 = compatUIController2.mCompatUIShellCommandHandler;
                        compatUIShellCommandHandler2.mShellCommandHandler.addCommandCallback("compatui", compatUIShellCommandHandler2, compatUIShellCommandHandler2);
                        break;
                }
            }
        }, this);
    }

    public BoundsCompatUIWindowManager createBoundsCompatUiWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new BoundsCompatUIWindowManager(context, taskInfo, this.mSyncQueue, this.mCallback, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), this);
    }

    public CompatUIWindowManager createCompatUiWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new CompatUIWindowManager(context, taskInfo, this.mSyncQueue, this.mCallback, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), this.mCompatUIHintsState, this.mCompatUIConfiguration, new CompatUIController$$ExternalSyntheticLambda1(this, 1));
    }

    public LetterboxEduWindowManager createLetterboxEduWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new LetterboxEduWindowManager(context, taskInfo, this.mSyncQueue, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), (Transitions) this.mTransitionsLazy.get(), new CompatUIController$$ExternalSyntheticLambda1(this, 0), this.mDockStateReader, this.mCompatUIConfiguration);
    }

    public final void createOrUpdateReachabilityEduLayout(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        ReachabilityEduWindowManager reachabilityEduWindowManager = this.mActiveReachabilityEduLayout;
        if (reachabilityEduWindowManager != null) {
            if (!reachabilityEduWindowManager.needsToBeRecreated(taskInfo, taskListener)) {
                ReachabilityEduWindowManager reachabilityEduWindowManager2 = this.mActiveReachabilityEduLayout;
                if (reachabilityEduWindowManager2.updateCompatInfo(taskInfo, taskListener, showOnDisplay(reachabilityEduWindowManager2.mDisplayId))) {
                    return;
                }
                this.mActiveReachabilityEduLayout = null;
                return;
            }
            this.mActiveReachabilityEduLayout.release();
            this.mActiveReachabilityEduLayout = null;
        }
        Context orCreateDisplayContext = getOrCreateDisplayContext(taskInfo.displayId);
        if (orCreateDisplayContext == null) {
            return;
        }
        ReachabilityEduWindowManager createReachabilityEduWindowManager = createReachabilityEduWindowManager(orCreateDisplayContext, taskInfo, taskListener);
        if (createReachabilityEduWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
            ReachabilityEduWindowManager reachabilityEduWindowManager3 = this.mActiveReachabilityEduLayout;
            if (reachabilityEduWindowManager3 != null) {
                reachabilityEduWindowManager3.release();
            }
            this.mActiveReachabilityEduLayout = createReachabilityEduWindowManager;
        }
    }

    public ReachabilityEduWindowManager createReachabilityEduWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new ReachabilityEduWindowManager(context, taskInfo, this.mSyncQueue, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), this.mCompatUIConfiguration, this.mMainExecutor);
    }

    public RestartDialogWindowManager createRestartDialogWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new RestartDialogWindowManager(context, taskInfo, this.mSyncQueue, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), (Transitions) this.mTransitionsLazy.get(), new CompatUIController$$ExternalSyntheticLambda1(this, 2), new CompatUIController$$ExternalSyntheticLambda1(this, 3), this.mCompatUIConfiguration);
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.println();
        printWriter.println(str + "CompatUIController:");
        int i = 0;
        while (true) {
            SparseArray sparseArray = this.mActiveBoundsCompatLayouts;
            if (i >= sparseArray.size()) {
                return;
            }
            BoundsCompatUIWindowManager boundsCompatUIWindowManager = (BoundsCompatUIWindowManager) sparseArray.get(sparseArray.keyAt(i));
            if (boundsCompatUIWindowManager != null) {
                printWriter.println(str + "#" + i + " " + boundsCompatUIWindowManager);
            }
            i++;
        }
    }

    public final void forAllLayouts(Predicate predicate, Consumer consumer) {
        int i = 0;
        int i2 = 0;
        while (true) {
            SparseArray sparseArray = this.mActiveCompatLayouts;
            if (i2 >= sparseArray.size()) {
                break;
            }
            CompatUIWindowManager compatUIWindowManager = (CompatUIWindowManager) sparseArray.get(sparseArray.keyAt(i2));
            if (compatUIWindowManager != null && predicate.test(compatUIWindowManager)) {
                consumer.accept(compatUIWindowManager);
            }
            i2++;
        }
        LetterboxEduWindowManager letterboxEduWindowManager = this.mActiveLetterboxEduLayout;
        if (letterboxEduWindowManager != null && predicate.test(letterboxEduWindowManager)) {
            consumer.accept(this.mActiveLetterboxEduLayout);
        }
        int i3 = 0;
        while (true) {
            SparseArray sparseArray2 = this.mActiveBoundsCompatLayouts;
            if (i3 >= sparseArray2.size()) {
                break;
            }
            BoundsCompatUIWindowManager boundsCompatUIWindowManager = (BoundsCompatUIWindowManager) sparseArray2.get(sparseArray2.keyAt(i3));
            if (boundsCompatUIWindowManager != null && predicate.test(boundsCompatUIWindowManager)) {
                consumer.accept(boundsCompatUIWindowManager);
            }
            i3++;
        }
        while (true) {
            SparseArray sparseArray3 = this.mTaskIdToRestartDialogWindowManagerMap;
            if (i >= sparseArray3.size()) {
                break;
            }
            RestartDialogWindowManager restartDialogWindowManager = (RestartDialogWindowManager) sparseArray3.get(sparseArray3.keyAt(i));
            if (restartDialogWindowManager != null && predicate.test(restartDialogWindowManager)) {
                consumer.accept(restartDialogWindowManager);
            }
            i++;
        }
        ReachabilityEduWindowManager reachabilityEduWindowManager = this.mActiveReachabilityEduLayout;
        if (reachabilityEduWindowManager == null || !predicate.test(reachabilityEduWindowManager)) {
            return;
        }
        consumer.accept(this.mActiveReachabilityEduLayout);
    }

    public final Context getOrCreateDisplayContext(int i) {
        Context context = this.mContext;
        if (i == 0) {
            return context;
        }
        SparseArray sparseArray = this.mDisplayContextCache;
        WeakReference weakReference = (WeakReference) sparseArray.get(i);
        Context context2 = weakReference != null ? (Context) weakReference.get() : null;
        if (context2 != null) {
            return context2;
        }
        Display display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i);
        if (display == null) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Cannot get context for display ", i, "CompatUIController");
            return context2;
        }
        Context createDisplayContext = context.createDisplayContext(display);
        sparseArray.put(i, new WeakReference(createDisplayContext));
        return createDisplayContext;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCompatInfoChanged(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        LetterboxEduWindowManager letterboxEduWindowManager;
        Context orCreateDisplayContext;
        RestartDialogWindowManager restartDialogWindowManager;
        Context orCreateDisplayContext2;
        Set set = this.mSetOfTaskIdsShowingRestartDialog;
        if (taskInfo != null && !taskInfo.topActivityInSizeCompat) {
            ((HashSet) set).remove(Integer.valueOf(taskInfo.taskId));
        }
        if (taskInfo.configuration == null || taskListener == null) {
            removeLayouts(taskInfo.taskId);
            return;
        }
        if (CoreRune.FW_BOUNDS_COMPAT_UI) {
            if (!(!(CoreRune.FW_FIXED_ASPECT_RATIO_MODE && taskInfo.topActivityInFixedAspectRatio) && taskInfo.topActivityInSizeCompat)) {
                if (taskInfo.topActivityBounds == null) {
                    Log.e("CompatUIController", "no activity bounds");
                    removeLayouts(taskInfo.taskId);
                    return;
                }
                SparseArray sparseArray = this.mActiveBoundsCompatLayouts;
                BoundsCompatUIWindowManager boundsCompatUIWindowManager = (BoundsCompatUIWindowManager) sparseArray.get(taskInfo.taskId);
                if (boundsCompatUIWindowManager != null) {
                    if (boundsCompatUIWindowManager.updateCompatInfo(taskInfo, taskListener, showOnDisplay(boundsCompatUIWindowManager.mDisplayId))) {
                        return;
                    }
                    sparseArray.remove(taskInfo.taskId);
                    return;
                }
                Context orCreateDisplayContext3 = getOrCreateDisplayContext(taskInfo.displayId);
                if (orCreateDisplayContext3 == null) {
                    return;
                }
                BoundsCompatUIWindowManager createBoundsCompatUiWindowManager = createBoundsCompatUiWindowManager(orCreateDisplayContext3, taskInfo, taskListener);
                if (createBoundsCompatUiWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
                    sparseArray.put(taskInfo.taskId, createBoundsCompatUiWindowManager);
                    return;
                }
                return;
            }
        }
        SparseArray sparseArray2 = this.mActiveCompatLayouts;
        CompatUIWindowManager compatUIWindowManager = (CompatUIWindowManager) sparseArray2.get(taskInfo.taskId);
        if (compatUIWindowManager != null) {
            if (compatUIWindowManager.needsToBeRecreated(taskInfo, taskListener)) {
                sparseArray2.remove(taskInfo.taskId);
                compatUIWindowManager.release();
            } else {
                if (!compatUIWindowManager.updateCompatInfo(taskInfo, taskListener, showOnDisplay(compatUIWindowManager.mDisplayId))) {
                    sparseArray2.remove(taskInfo.taskId);
                }
                letterboxEduWindowManager = this.mActiveLetterboxEduLayout;
                if (letterboxEduWindowManager != null) {
                    if (letterboxEduWindowManager.needsToBeRecreated(taskInfo, taskListener)) {
                        this.mActiveLetterboxEduLayout.release();
                        this.mActiveLetterboxEduLayout = null;
                    } else {
                        LetterboxEduWindowManager letterboxEduWindowManager2 = this.mActiveLetterboxEduLayout;
                        if (!letterboxEduWindowManager2.updateCompatInfo(taskInfo, taskListener, showOnDisplay(letterboxEduWindowManager2.mDisplayId))) {
                            this.mActiveLetterboxEduLayout = null;
                        }
                        SparseArray sparseArray3 = this.mTaskIdToRestartDialogWindowManagerMap;
                        restartDialogWindowManager = (RestartDialogWindowManager) sparseArray3.get(taskInfo.taskId);
                        if (restartDialogWindowManager != null) {
                            if (!restartDialogWindowManager.needsToBeRecreated(taskInfo, taskListener)) {
                                restartDialogWindowManager.mRequestRestartDialog = ((HashSet) set).contains(Integer.valueOf(taskInfo.taskId));
                                if (!restartDialogWindowManager.updateCompatInfo(taskInfo, taskListener, showOnDisplay(restartDialogWindowManager.mDisplayId))) {
                                    sparseArray3.remove(taskInfo.taskId);
                                }
                                if (this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(taskInfo.userId), false)) {
                                    return;
                                }
                                createOrUpdateReachabilityEduLayout(taskInfo, taskListener);
                                return;
                            }
                            sparseArray3.remove(taskInfo.taskId);
                            restartDialogWindowManager.release();
                        }
                        orCreateDisplayContext2 = getOrCreateDisplayContext(taskInfo.displayId);
                        if (orCreateDisplayContext2 != null) {
                            RestartDialogWindowManager createRestartDialogWindowManager = createRestartDialogWindowManager(orCreateDisplayContext2, taskInfo, taskListener);
                            createRestartDialogWindowManager.mRequestRestartDialog = ((HashSet) set).contains(Integer.valueOf(taskInfo.taskId));
                            if (createRestartDialogWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
                                sparseArray3.put(taskInfo.taskId, createRestartDialogWindowManager);
                            }
                        }
                        if (this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(taskInfo.userId), false)) {
                        }
                    }
                }
                orCreateDisplayContext = getOrCreateDisplayContext(taskInfo.displayId);
                if (orCreateDisplayContext != null) {
                    LetterboxEduWindowManager createLetterboxEduWindowManager = createLetterboxEduWindowManager(orCreateDisplayContext, taskInfo, taskListener);
                    if (createLetterboxEduWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
                        LetterboxEduWindowManager letterboxEduWindowManager3 = this.mActiveLetterboxEduLayout;
                        if (letterboxEduWindowManager3 != null) {
                            letterboxEduWindowManager3.release();
                        }
                        this.mActiveLetterboxEduLayout = createLetterboxEduWindowManager;
                    }
                }
                SparseArray sparseArray32 = this.mTaskIdToRestartDialogWindowManagerMap;
                restartDialogWindowManager = (RestartDialogWindowManager) sparseArray32.get(taskInfo.taskId);
                if (restartDialogWindowManager != null) {
                }
                orCreateDisplayContext2 = getOrCreateDisplayContext(taskInfo.displayId);
                if (orCreateDisplayContext2 != null) {
                }
                if (this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(taskInfo.userId), false)) {
                }
            }
        }
        Context orCreateDisplayContext4 = getOrCreateDisplayContext(taskInfo.displayId);
        if (orCreateDisplayContext4 != null) {
            CompatUIWindowManager createCompatUiWindowManager = createCompatUiWindowManager(orCreateDisplayContext4, taskInfo, taskListener);
            if (createCompatUiWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
                sparseArray2.put(taskInfo.taskId, createCompatUiWindowManager);
            }
        }
        letterboxEduWindowManager = this.mActiveLetterboxEduLayout;
        if (letterboxEduWindowManager != null) {
        }
        orCreateDisplayContext = getOrCreateDisplayContext(taskInfo.displayId);
        if (orCreateDisplayContext != null) {
        }
        SparseArray sparseArray322 = this.mTaskIdToRestartDialogWindowManagerMap;
        restartDialogWindowManager = (RestartDialogWindowManager) sparseArray322.get(taskInfo.taskId);
        if (restartDialogWindowManager != null) {
        }
        orCreateDisplayContext2 = getOrCreateDisplayContext(taskInfo.displayId);
        if (orCreateDisplayContext2 != null) {
        }
        if (this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(taskInfo.userId), false)) {
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        PerDisplayOnInsetsChangedListener perDisplayOnInsetsChangedListener = new PerDisplayOnInsetsChangedListener(i);
        CompatUIController.this.mDisplayInsetsController.addInsetsChangedListener(perDisplayOnInsetsChangedListener.mDisplayId, perDisplayOnInsetsChangedListener);
        this.mOnInsetsChangedListeners.put(i, perDisplayOnInsetsChangedListener);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        forAllLayouts(new CompatUIController$$ExternalSyntheticLambda4(i), new CompatUIController$$ExternalSyntheticLambda3(this.mDisplayController.getDisplayLayout(i), 0));
        if (CoreRune.FW_BOUNDS_COMPAT_UI && i == 0 && this.mWaitingForFixedRotation) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHandleFixedRotation);
            this.mWaitingForFixedRotation = false;
            if (this.mDeferVisibilityUpdate) {
                this.mDeferVisibilityUpdate = false;
                forAllLayouts(new CompatUIController$$ExternalSyntheticLambda2(2), new CompatUIController$$ExternalSyntheticLambda1(this, 6));
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        this.mDisplayContextCache.remove(i);
        SparseArray sparseArray = this.mOnInsetsChangedListeners;
        PerDisplayOnInsetsChangedListener perDisplayOnInsetsChangedListener = (PerDisplayOnInsetsChangedListener) sparseArray.get(i);
        if (perDisplayOnInsetsChangedListener != null) {
            CompatUIController.this.mDisplayInsetsController.removeInsetsChangedListener(perDisplayOnInsetsChangedListener.mDisplayId, perDisplayOnInsetsChangedListener);
            sparseArray.remove(i);
        }
        ArrayList arrayList = new ArrayList();
        forAllLayouts(new CompatUIController$$ExternalSyntheticLambda4(i), new CompatUIController$$ExternalSyntheticLambda3(arrayList, 1));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            removeLayouts(((Integer) arrayList.get(size)).intValue());
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onFixedRotationStarted(int i, int i2) {
        if (CoreRune.FW_BOUNDS_COMPAT_UI && i == 0) {
            this.mWaitingForFixedRotation = true;
            HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
            CompatUIController$$ExternalSyntheticLambda0 compatUIController$$ExternalSyntheticLambda0 = this.mHandleFixedRotation;
            handlerExecutor.removeCallbacks(compatUIController$$ExternalSyntheticLambda0);
            handlerExecutor.executeDelayed(2000L, compatUIController$$ExternalSyntheticLambda0);
        }
    }

    @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
    public final void onImeVisibilityChanged(final int i, boolean z) {
        Set set = this.mDisplaysWithIme;
        if (z) {
            ((ArraySet) set).add(Integer.valueOf(i));
        } else {
            ((ArraySet) set).remove(Integer.valueOf(i));
        }
        forAllLayouts(new CompatUIController$$ExternalSyntheticLambda4(i), new Consumer() { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CompatUIWindowManagerAbstract) obj).updateVisibility(CompatUIController.this.showOnDisplay(i));
            }
        });
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2) {
        this.mKeyguardShowing = z;
        if (CoreRune.FW_BOUNDS_COMPAT_UI && this.mWaitingForFixedRotation && !z) {
            this.mDeferVisibilityUpdate = true;
            forAllLayouts(new CompatUIController$$ExternalSyntheticLambda2(0), new CompatUIController$$ExternalSyntheticLambda1(this, 4));
        } else {
            forAllLayouts(new CompatUIController$$ExternalSyntheticLambda2(1), new CompatUIController$$ExternalSyntheticLambda1(this, 5));
        }
    }

    public final void removeLayouts(int i) {
        SparseArray sparseArray = this.mActiveCompatLayouts;
        CompatUIWindowManager compatUIWindowManager = (CompatUIWindowManager) sparseArray.get(i);
        if (compatUIWindowManager != null) {
            compatUIWindowManager.release();
            sparseArray.remove(i);
        }
        LetterboxEduWindowManager letterboxEduWindowManager = this.mActiveLetterboxEduLayout;
        if (letterboxEduWindowManager != null && letterboxEduWindowManager.mTaskId == i) {
            letterboxEduWindowManager.release();
            this.mActiveLetterboxEduLayout = null;
        }
        if (CoreRune.FW_BOUNDS_COMPAT_UI) {
            SparseArray sparseArray2 = this.mActiveBoundsCompatLayouts;
            BoundsCompatUIWindowManager boundsCompatUIWindowManager = (BoundsCompatUIWindowManager) sparseArray2.get(i);
            if (boundsCompatUIWindowManager != null) {
                boundsCompatUIWindowManager.release();
                sparseArray2.remove(i);
            }
        }
        SparseArray sparseArray3 = this.mTaskIdToRestartDialogWindowManagerMap;
        RestartDialogWindowManager restartDialogWindowManager = (RestartDialogWindowManager) sparseArray3.get(i);
        if (restartDialogWindowManager != null) {
            restartDialogWindowManager.release();
            sparseArray3.remove(i);
            ((HashSet) this.mSetOfTaskIdsShowingRestartDialog).remove(Integer.valueOf(i));
        }
        ReachabilityEduWindowManager reachabilityEduWindowManager = this.mActiveReachabilityEduLayout;
        if (reachabilityEduWindowManager == null || reachabilityEduWindowManager.mTaskId != i) {
            return;
        }
        reachabilityEduWindowManager.release();
        this.mActiveReachabilityEduLayout = null;
    }

    public final boolean showOnDisplay(int i) {
        if (!this.mKeyguardShowing) {
            if (!((ArraySet) this.mDisplaysWithIme).contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }
}
