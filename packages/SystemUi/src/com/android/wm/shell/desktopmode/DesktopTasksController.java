package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ExecutorUtils;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.IDesktopMode;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.KtProtoLog;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopTasksController implements RemoteCallable, Transitions.TransitionHandler {
    public final Context context;
    public final DesktopModeTaskRepository desktopModeTaskRepository;
    public final DisplayController displayController;
    public final EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler;
    public final ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler;
    public final ShellExecutor mainExecutor;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final ShellController shellController;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final SyncTransactionQueue syncQueue;
    public final Transitions transitions;
    public DesktopModeVisualIndicator visualIndicator;
    public static final Companion Companion = new Companion(null);
    public static final int DESKTOP_DENSITY_OVERRIDE = SystemProperties.getInt("persist.wm.debug.desktop_mode_density", 0);
    public static final IntRange DESKTOP_DENSITY_ALLOWED_RANGE = new IntRange(100, 1000);
    public final DesktopTasksController$mOnAnimationFinishedCallback$1 mOnAnimationFinishedCallback = new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$mOnAnimationFinishedCallback$1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            SurfaceControlViewHost surfaceControlViewHost;
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj;
            DesktopModeVisualIndicator desktopModeVisualIndicator = DesktopTasksController.this.visualIndicator;
            if (desktopModeVisualIndicator != null && (surfaceControlViewHost = desktopModeVisualIndicator.mViewHost) != null) {
                surfaceControlViewHost.release();
                desktopModeVisualIndicator.mViewHost = null;
                SurfaceControl surfaceControl = desktopModeVisualIndicator.mLeash;
                if (surfaceControl != null) {
                    transaction.remove(surfaceControl);
                    desktopModeVisualIndicator.mLeash = null;
                }
            }
            DesktopTasksController.this.visualIndicator = null;
        }
    };
    public final DesktopModeImpl desktopMode = new DesktopModeImpl();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DesktopModeImpl implements DesktopMode {
        public DesktopModeImpl() {
        }

        @Override // com.android.wm.shell.desktopmode.DesktopMode
        public final void addDesktopGestureExclusionRegionListener(final Executor executor, final EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
            final DesktopTasksController desktopTasksController = DesktopTasksController.this;
            ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addDesktopGestureExclusionRegionListener$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                    Consumer consumer = edgeBackGestureHandler$$ExternalSyntheticLambda0;
                    Executor executor2 = executor;
                    DesktopModeTaskRepository desktopModeTaskRepository = desktopTasksController2.desktopModeTaskRepository;
                    desktopModeTaskRepository.desktopGestureExclusionListener = consumer;
                    desktopModeTaskRepository.desktopGestureExclusionExecutor = executor2;
                    executor2.execute(new DesktopModeTaskRepository$setTaskCornerListener$1(desktopModeTaskRepository));
                }
            });
        }

        @Override // com.android.wm.shell.desktopmode.DesktopMode
        public final void addVisibleTasksListener(final WMShell.C374814 c374814, final Executor executor) {
            final DesktopTasksController desktopTasksController = DesktopTasksController.this;
            ((HandlerExecutor) desktopTasksController.mainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                    desktopTasksController2.desktopModeTaskRepository.addVisibleTasksListener(c374814, executor);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IDesktopModeImpl extends IDesktopMode.Stub implements ExternalInterfaceBinder {
        public DesktopTasksController controller;

        public IDesktopModeImpl(DesktopTasksController desktopTasksController) {
            this.controller = desktopTasksController;
        }

        @Override // com.android.wm.shell.desktopmode.IDesktopMode
        public final int getVisibleTaskCount(final int i) {
            final int[] iArr = new int[1];
            ExecutorUtils.executeRemoteCallWithTaskPermission(this.controller, "getVisibleTaskCount", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$getVisibleTaskCount$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    iArr[0] = ((DesktopTasksController) obj).desktopModeTaskRepository.getVisibleTaskCount(i);
                }
            }, true);
            return iArr[0];
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.controller = null;
        }

        @Override // com.android.wm.shell.desktopmode.IDesktopMode
        public final void showDesktopApps(final int i) {
            ExecutorUtils.executeRemoteCallWithTaskPermission(this.controller, "showDesktopApps", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$showDesktopApps$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                    int i2 = i;
                    desktopTasksController.getClass();
                    KtProtoLog.Companion.getClass();
                    KtProtoLog.Companion.m235v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopTasksController: showDesktopApps", new Object[0]);
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    desktopTasksController.bringDesktopAppsToFront(windowContainerTransaction, i2);
                    if (windowContainerTransaction.isEmpty()) {
                        return;
                    }
                    if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                        desktopTasksController.transitions.startTransition(0, windowContainerTransaction, null);
                    } else {
                        desktopTasksController.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                    }
                }
            }, false);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.desktopmode.DesktopTasksController$mOnAnimationFinishedCallback$1] */
    public DesktopTasksController(Context context, ShellInit shellInit, ShellController shellController, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Transitions transitions, EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler, ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler, DesktopModeTaskRepository desktopModeTaskRepository, ShellExecutor shellExecutor) {
        this.context = context;
        this.shellController = shellController;
        this.displayController = displayController;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.syncQueue = syncTransactionQueue;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.transitions = transitions;
        this.enterDesktopTaskTransitionHandler = enterDesktopTaskTransitionHandler;
        this.exitDesktopTaskTransitionHandler = exitDesktopTaskTransitionHandler;
        this.desktopModeTaskRepository = desktopModeTaskRepository;
        this.mainExecutor = shellExecutor;
        if (DesktopModeStatus.IS_PROTO2_ENABLED) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.1
                @Override // java.lang.Runnable
                public final void run() {
                    final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    Companion companion = DesktopTasksController.Companion;
                    desktopTasksController.getClass();
                    KtProtoLog.Companion.getClass();
                    KtProtoLog.Companion.m234d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "Initialize DesktopTasksController", new Object[0]);
                    desktopTasksController.shellController.addExternalInterface("extra_shell_desktop_mode", new Supplier() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                            DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                            desktopTasksController2.getClass();
                            return new DesktopTasksController.IDesktopModeImpl(desktopTasksController2);
                        }
                    }, desktopTasksController);
                    desktopTasksController.transitions.addHandler(desktopTasksController);
                }
            }, this);
        }
    }

    public static void addMoveToDesktopChanges(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction) {
        windowContainerTransaction.setWindowingMode(windowContainerToken, 5);
        windowContainerTransaction.reorder(windowContainerToken, true);
        Companion.getClass();
        IntRange intRange = DESKTOP_DENSITY_ALLOWED_RANGE;
        int i = intRange.first;
        int i2 = intRange.last;
        int i3 = DESKTOP_DENSITY_OVERRIDE;
        if (i <= i3 && i3 <= i2) {
            windowContainerTransaction.setDensityDpi(windowContainerToken, i3);
        }
    }

    public final void addMoveToFullscreenChanges(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction) {
        windowContainerTransaction.setWindowingMode(windowContainerToken, 1);
        windowContainerTransaction.setBounds(windowContainerToken, (Rect) null);
        Companion.getClass();
        IntRange intRange = DESKTOP_DENSITY_ALLOWED_RANGE;
        int i = intRange.first;
        int i2 = intRange.last;
        int i3 = DESKTOP_DENSITY_OVERRIDE;
        if (i <= i3 && i3 <= i2) {
            windowContainerTransaction.setDensityDpi(windowContainerToken, this.context.getResources().getDisplayMetrics().densityDpi);
        }
    }

    public final void bringDesktopAppsToFront(WindowContainerTransaction windowContainerTransaction, int i) {
        KtProtoLog.Companion.getClass();
        KtProtoLog.Companion.m235v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopTasksController: bringDesktopAppsToFront", new Object[0]);
        DesktopModeTaskRepository desktopModeTaskRepository = this.desktopModeTaskRepository;
        ArraySet activeTasks = desktopModeTaskRepository.getActiveTasks(i);
        moveHomeTaskToFront(windowContainerTransaction);
        final List list = desktopModeTaskRepository.freeformTasksInZOrder;
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(activeTasks, new Comparator() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$bringDesktopAppsToFront$$inlined$sortedByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(list.indexOf((Integer) obj2)), Integer.valueOf(list.indexOf((Integer) obj)));
            }
        });
        ArrayList arrayList = new ArrayList();
        Iterator it = sortedWith.iterator();
        while (it.hasNext()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(((Integer) it.next()).intValue());
            if (runningTaskInfo != null) {
                arrayList.add(runningTaskInfo);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            windowContainerTransaction.reorder(((ActivityManager.RunningTaskInfo) it2.next()).token, true);
        }
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.context;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mainExecutor;
    }

    public final int getStatusBarHeight(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Rect stableInsets;
        DisplayLayout displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId);
        if (displayLayout == null || (stableInsets = displayLayout.stableInsets(false)) == null) {
            return 0;
        }
        return stableInsets.top;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        boolean z;
        boolean z2 = false;
        if (!((transitionRequestInfo.getType() == 1 || transitionRequestInfo.getType() == 3) && transitionRequestInfo.getTriggerTask() != null && transitionRequestInfo.getTriggerTask().getActivityType() == 1 && (transitionRequestInfo.getTriggerTask().getWindowingMode() == 1 || transitionRequestInfo.getTriggerTask().getWindowingMode() == 5))) {
            return null;
        }
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        int i = triggerTask.displayId;
        DesktopModeTaskRepository desktopModeTaskRepository = this.desktopModeTaskRepository;
        ArraySet activeTasks = desktopModeTaskRepository.getActiveTasks(i);
        if (triggerTask.getWindowingMode() == 1) {
            if (!activeTasks.isEmpty()) {
                Iterator it = activeTasks.iterator();
                while (it.hasNext()) {
                    if (desktopModeTaskRepository.isVisibleTask(((Integer) it.next()).intValue())) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (z) {
                KtProtoLog.Companion companion = KtProtoLog.Companion;
                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                Object[] objArr = {Integer.valueOf(triggerTask.taskId)};
                companion.getClass();
                KtProtoLog.Companion.m234d(shellProtoLogGroup, "DesktopTasksController: switch fullscreen task to freeform on transition taskId=%d", objArr);
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                addMoveToDesktopChanges(triggerTask.token, windowContainerTransaction);
                return windowContainerTransaction;
            }
        }
        if (triggerTask.getWindowingMode() == 5) {
            if (!activeTasks.isEmpty()) {
                Iterator it2 = activeTasks.iterator();
                while (it2.hasNext()) {
                    if (desktopModeTaskRepository.isVisibleTask(((Integer) it2.next()).intValue())) {
                        break;
                    }
                }
            }
            z2 = true;
            if (z2) {
                KtProtoLog.Companion companion2 = KtProtoLog.Companion;
                ShellProtoLogGroup shellProtoLogGroup2 = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                Object[] objArr2 = {Integer.valueOf(triggerTask.taskId)};
                companion2.getClass();
                KtProtoLog.Companion.m234d(shellProtoLogGroup2, "DesktopTasksController: switch freeform task to fullscreen oon transition taskId=%d", objArr2);
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                addMoveToFullscreenChanges(triggerTask.token, windowContainerTransaction2);
                return windowContainerTransaction2;
            }
        }
        return null;
    }

    public final void moveHomeTaskToFront(WindowContainerTransaction windowContainerTransaction) {
        Object obj;
        Iterator it = this.shellTaskOrganizer.getRunningTasks(this.context.getDisplayId()).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((ActivityManager.RunningTaskInfo) obj).getActivityType() == 2) {
                    break;
                }
            }
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
        if (runningTaskInfo != null) {
            windowContainerTransaction.reorder(runningTaskInfo.getToken(), true);
        }
    }

    public final void releaseVisualIndicator() {
        SurfaceControlViewHost surfaceControlViewHost;
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        DesktopModeVisualIndicator desktopModeVisualIndicator = this.visualIndicator;
        if (desktopModeVisualIndicator != null && (surfaceControlViewHost = desktopModeVisualIndicator.mViewHost) != null) {
            surfaceControlViewHost.release();
            desktopModeVisualIndicator.mViewHost = null;
            SurfaceControl surfaceControl = desktopModeVisualIndicator.mLeash;
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
                desktopModeVisualIndicator.mLeash = null;
            }
        }
        this.visualIndicator = null;
        this.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$releaseVisualIndicator$1
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                SurfaceControl.Transaction transaction3 = transaction;
                transaction2.merge(transaction3);
                transaction3.close();
            }
        });
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        return false;
    }
}
