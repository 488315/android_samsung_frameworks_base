package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.MixedTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopMixedTransitionHandler implements MixedTransitionHandler, FreeformTaskTransitionStarter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CloseDesktopTaskTransitionHandler closeDesktopTaskTransitionHandler;
    public final DesktopImmersiveController desktopImmersiveController;
    public final DesktopMinimizationTransitionHandler desktopMinimizationTransitionHandler;
    public final FreeformTaskTransitionHandler freeformTaskTransitionHandler;
    public final List pendingMixedTransitions;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final Transitions transitions;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract class PendingMixedTransition {

        public final class Close extends PendingMixedTransition {
            public final IBinder transition;

            public Close(IBinder iBinder) {
                super(null);
                this.transition = iBinder;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Close) && Intrinsics.areEqual(this.transition, ((Close) obj).transition);
            }

            @Override // com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler.PendingMixedTransition
            public final IBinder getTransition() {
                return this.transition;
            }

            public final int hashCode() {
                return this.transition.hashCode();
            }

            public final String toString() {
                return "Close(transition=" + this.transition + ")";
            }
        }

        public final class Launch extends PendingMixedTransition {
            public final Integer exitingImmersiveTask;
            public final Integer launchingTask;
            public final Integer minimizingTask;
            public final IBinder transition;

            public Launch(IBinder iBinder, Integer num, Integer num2, Integer num3) {
                super(null);
                this.transition = iBinder;
                this.launchingTask = num;
                this.minimizingTask = num2;
                this.exitingImmersiveTask = num3;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Launch)) {
                    return false;
                }
                Launch launch = (Launch) obj;
                return Intrinsics.areEqual(this.transition, launch.transition) && Intrinsics.areEqual(this.launchingTask, launch.launchingTask) && Intrinsics.areEqual(this.minimizingTask, launch.minimizingTask) && Intrinsics.areEqual(this.exitingImmersiveTask, launch.exitingImmersiveTask);
            }

            @Override // com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler.PendingMixedTransition
            public final IBinder getTransition() {
                return this.transition;
            }

            public final int hashCode() {
                int iHashCode = this.transition.hashCode() * 31;
                Integer num = this.launchingTask;
                int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
                Integer num2 = this.minimizingTask;
                int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
                Integer num3 = this.exitingImmersiveTask;
                return iHashCode3 + (num3 != null ? num3.hashCode() : 0);
            }

            public final String toString() {
                return "Launch(transition=" + this.transition + ", launchingTask=" + this.launchingTask + ", minimizingTask=" + this.minimizingTask + ", exitingImmersiveTask=" + this.exitingImmersiveTask + ")";
            }
        }

        public final class Minimize extends PendingMixedTransition {
            public final boolean isLastTask;
            public final int minimizingTask;
            public final IBinder transition;

            public Minimize(IBinder iBinder, int i, boolean z) {
                super(null);
                this.transition = iBinder;
                this.minimizingTask = i;
                this.isLastTask = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Minimize)) {
                    return false;
                }
                Minimize minimize = (Minimize) obj;
                return Intrinsics.areEqual(this.transition, minimize.transition) && this.minimizingTask == minimize.minimizingTask && this.isLastTask == minimize.isLastTask;
            }

            @Override // com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler.PendingMixedTransition
            public final IBinder getTransition() {
                return this.transition;
            }

            public final int hashCode() {
                return Boolean.hashCode(this.isLastTask) + ReorderTile$$ExternalSyntheticOutline0.m(this.minimizingTask, this.transition.hashCode() * 31, 31);
            }

            public final String toString() {
                IBinder iBinder = this.transition;
                StringBuilder sb = new StringBuilder("Minimize(transition=");
                sb.append(iBinder);
                sb.append(", minimizingTask=");
                sb.append(this.minimizingTask);
                sb.append(", isLastTask=");
                return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isLastTask, ")");
            }
        }

        public final class MinimizeAll extends PendingMixedTransition {
            public final int displayId;
            public final IBinder transition;

            public MinimizeAll(IBinder iBinder, int i) {
                super(null);
                this.transition = iBinder;
                this.displayId = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof MinimizeAll)) {
                    return false;
                }
                MinimizeAll minimizeAll = (MinimizeAll) obj;
                return Intrinsics.areEqual(this.transition, minimizeAll.transition) && this.displayId == minimizeAll.displayId;
            }

            @Override // com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler.PendingMixedTransition
            public final IBinder getTransition() {
                return this.transition;
            }

            public final int hashCode() {
                return Integer.hashCode(this.displayId) + (this.transition.hashCode() * 31);
            }

            public final String toString() {
                return "MinimizeAll(transition=" + this.transition + ", displayId=" + this.displayId + ")";
            }
        }

        public /* synthetic */ PendingMixedTransition(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract IBinder getTransition();

        private PendingMixedTransition() {
        }
    }

    static {
        new Companion(null);
    }

    public DesktopMixedTransitionHandler(Context context, Transitions transitions, DesktopUserRepositories desktopUserRepositories, FreeformTaskTransitionHandler freeformTaskTransitionHandler, CloseDesktopTaskTransitionHandler closeDesktopTaskTransitionHandler, DesktopImmersiveController desktopImmersiveController, DesktopMinimizationTransitionHandler desktopMinimizationTransitionHandler, InteractionJankMonitor interactionJankMonitor, Handler handler, ShellInit shellInit, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.transitions = transitions;
        this.freeformTaskTransitionHandler = freeformTaskTransitionHandler;
        this.closeDesktopTaskTransitionHandler = closeDesktopTaskTransitionHandler;
        this.desktopImmersiveController = desktopImmersiveController;
        this.desktopMinimizationTransitionHandler = desktopMinimizationTransitionHandler;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                DesktopMixedTransitionHandler desktopMixedTransitionHandler = DesktopMixedTransitionHandler.this;
                desktopMixedTransitionHandler.transitions.addHandler(desktopMixedTransitionHandler);
            }
        }, this);
        this.pendingMixedTransitions = new ArrayList();
    }

    public static boolean dispatchToLeftoverHandler$default(DesktopMixedTransitionHandler desktopMixedTransitionHandler, IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        desktopMixedTransitionHandler.getClass();
        final Function0 function0 = null;
        return desktopMixedTransitionHandler.transitions.dispatchTransition(iBinder, transitionInfo, transaction, transaction2, new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler$dispatchToLeftoverHandler$1
            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                Function0 function02 = function0;
                if (function02 != null) {
                    function02.invoke();
                }
                transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
            }
        }, desktopMixedTransitionHandler, null) != null;
    }

    public static TransitionInfo.Change findLaunchChange(TransitionInfo transitionInfo) {
        Object next;
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            TransitionInfo.Change change = (TransitionInfo.Change) next;
            if (change.getMode() == 1 && change.getTaskInfo() != null) {
                ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                taskInfo.getClass();
                if (taskInfo.isFreeform()) {
                    break;
                }
            }
        }
        return (TransitionInfo.Change) next;
    }

    public static TransitionInfo.Change findTaskChange(TransitionInfo transitionInfo, int i) {
        Object next;
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) next).getTaskInfo();
            if (taskInfo != null && taskInfo.taskId == i) {
                break;
            }
        }
        return (TransitionInfo.Change) next;
    }

    public static void logV$6(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopMixedTransitionHandler", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static void logW$3(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopMixedTransitionHandler", objArr);
        ProtoLog.w(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static WindowContainerTransaction merge(WindowContainerTransaction windowContainerTransaction, WindowContainerTransaction windowContainerTransaction2) {
        return windowContainerTransaction2 == null ? windowContainerTransaction : windowContainerTransaction == null ? windowContainerTransaction2 : merge(windowContainerTransaction, windowContainerTransaction2);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(final IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        CollectionsKt__MutableCollectionsKt.removeAll(this.pendingMixedTransitions, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                IBinder iBinder2 = iBinder;
                int i = DesktopMixedTransitionHandler.$r8$clinit;
                return Boolean.valueOf(Intrinsics.areEqual(((DesktopMixedTransitionHandler.PendingMixedTransition) obj).getTransition(), iBinder2));
            }
        });
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        Object objValueOf;
        Object obj;
        TransitionInfo.Change changeFindLaunchChange;
        ActivityManager.RunningTaskInfo taskInfo;
        ActivityManager.RunningTaskInfo taskInfo2;
        ActivityManager.RunningTaskInfo taskInfo3;
        ArrayList arrayList = (ArrayList) this.pendingMixedTransitions;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            objValueOf = null;
            if (i >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(((PendingMixedTransition) obj).getTransition(), iBinder)) {
                break;
            }
        }
        PendingMixedTransition pendingMixedTransition = (PendingMixedTransition) obj;
        if (pendingMixedTransition == null) {
            logV$6("No pending desktop transition", new Object[0]);
            return false;
        }
        ((ArrayList) this.pendingMixedTransitions).remove(pendingMixedTransition);
        logV$6("Animating pending mixed transition: %s", pendingMixedTransition);
        if (pendingMixedTransition instanceof PendingMixedTransition.Close) {
            if (transitionInfo.getType() == 2) {
                Iterator it = transitionInfo.getChanges().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    TransitionInfo.Change change = (TransitionInfo.Change) next;
                    if (change.getMode() == 2 && !change.hasFlags(2) && ((taskInfo3 = change.getTaskInfo()) == null || taskInfo3.taskId != -1)) {
                        ActivityManager.RunningTaskInfo taskInfo4 = change.getTaskInfo();
                        if (taskInfo4 != null && taskInfo4.getWindowingMode() == 5) {
                            objValueOf = next;
                            break;
                        }
                    }
                }
                objValueOf = (TransitionInfo.Change) objValueOf;
            }
            if (objValueOf == null) {
                logW$3("Should have closing desktop task", new Object[0]);
                return false;
            }
            List<TransitionInfo.Change> changes = transitionInfo.getChanges();
            if (!(changes instanceof Collection) || !changes.isEmpty()) {
                for (TransitionInfo.Change change2 : changes) {
                    if (TransitionUtil.isClosingMode(change2.getMode()) && change2.getTaskInfo() != null) {
                        DesktopWallpaperActivity.Companion companion = DesktopWallpaperActivity.Companion;
                        ActivityManager.RunningTaskInfo taskInfo5 = change2.getTaskInfo();
                        taskInfo5.getClass();
                        companion.getClass();
                        if (DesktopWallpaperActivity.Companion.isWallpaperTask(taskInfo5)) {
                            return dispatchToLeftoverHandler$default(this, iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                        }
                    }
                }
            }
            return this.closeDesktopTaskTransitionHandler.startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
        }
        if (!(pendingMixedTransition instanceof PendingMixedTransition.Launch)) {
            if (!(pendingMixedTransition instanceof PendingMixedTransition.Minimize)) {
                if (pendingMixedTransition instanceof PendingMixedTransition.MinimizeAll) {
                    return this.desktopMinimizationTransitionHandler.startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                }
                throw new NoWhenBranchMatchedException();
            }
            PendingMixedTransition.Minimize minimize = (PendingMixedTransition.Minimize) pendingMixedTransition;
            if (!(transitionInfo.getType() == 1020 ? DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX.isTrue() : DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue())) {
                return false;
            }
            if (findTaskChange(transitionInfo, minimize.minimizingTask) != null) {
                return minimize.isLastTask ? dispatchToLeftoverHandler$default(this, iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback) : this.desktopMinimizationTransitionHandler.startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            logW$3("Should have minimizing desktop task", new Object[0]);
            return false;
        }
        PendingMixedTransition.Launch launch = (PendingMixedTransition.Launch) pendingMixedTransition;
        Integer num = launch.exitingImmersiveTask;
        TransitionInfo.Change changeFindTaskChange = num != null ? findTaskChange(transitionInfo, num.intValue()) : null;
        Integer num2 = launch.minimizingTask;
        TransitionInfo.Change changeFindTaskChange2 = num2 != null ? findTaskChange(transitionInfo, num2.intValue()) : null;
        Integer num3 = launch.launchingTask;
        if (num3 != null) {
            changeFindLaunchChange = findTaskChange(transitionInfo, num3.intValue());
            if (DesktopModeFlags.ENABLE_DESKTOP_OPENING_DEEPLINK_MINIMIZE_ANIMATION_BUGFIX.isTrue() && changeFindLaunchChange == null) {
                changeFindLaunchChange = findLaunchChange(transitionInfo);
            }
        } else {
            changeFindLaunchChange = findLaunchChange(transitionInfo);
        }
        if (changeFindLaunchChange == null) {
            if (changeFindTaskChange != null) {
                throw new IllegalStateException("Check failed.");
            }
            logV$6("No launch Change, returning", new Object[0]);
            return false;
        }
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = -1;
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        Transitions.TransitionFinishCallback transitionFinishCallback2 = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler$animateLaunchTransition$finishCb$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r5v1, types: [T, android.window.WindowContainerTransaction] */
            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                Ref$IntRef ref$IntRef2 = ref$IntRef;
                ref$IntRef2.element--;
                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                WindowContainerTransaction windowContainerTransaction2 = (WindowContainerTransaction) ref$ObjectRef2.element;
                int i2 = DesktopMixedTransitionHandler.$r8$clinit;
                this.getClass();
                ?? Merge = DesktopMixedTransitionHandler.merge(windowContainerTransaction2, windowContainerTransaction);
                ref$ObjectRef2.element = Merge;
                if (ref$IntRef2.element > 0) {
                    return;
                }
                transitionFinishCallback.onTransitionFinished(Merge);
            }
        };
        ActivityManager.RunningTaskInfo taskInfo6 = changeFindLaunchChange.getTaskInfo();
        taskInfo6.getClass();
        Integer numValueOf = Integer.valueOf(taskInfo6.taskId);
        Integer numValueOf2 = (changeFindTaskChange2 == null || (taskInfo2 = changeFindTaskChange2.getTaskInfo()) == null) ? null : Integer.valueOf(taskInfo2.taskId);
        if (changeFindTaskChange != null && (taskInfo = changeFindTaskChange.getTaskInfo()) != null) {
            objValueOf = Integer.valueOf(taskInfo.taskId);
        }
        logV$6("Animating mixed launch transition task#%d, minimizingTask#%s immersiveExitTask#%s", numValueOf, numValueOf2, objValueOf);
        if (DesktopModeFlags.ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX.isTrue() && changeFindTaskChange2 != null) {
            if (!TransitionUtil.isOpeningMode(transitionInfo.getType())) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            if (changeFindTaskChange2.getTaskInfo() == null) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            ActivityManager.RunningTaskInfo taskInfo7 = changeFindTaskChange2.getTaskInfo();
            taskInfo7.getClass();
            if (!taskInfo7.isFreeform() && taskInfo7.getWindowingMode() != 1) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            logV$6("Reparenting minimizing task#%d", Integer.valueOf(taskInfo7.taskId));
            this.rootTaskDisplayAreaOrganizer.reparentToDisplayArea(taskInfo7.displayId, transaction, changeFindTaskChange2.getLeash());
        }
        if (changeFindTaskChange == null) {
            ref$IntRef.element = 1;
            return dispatchToLeftoverHandler$default(this, iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback2);
        }
        ref$IntRef.element = 2;
        transitionInfo.getChanges().remove(changeFindTaskChange);
        this.desktopImmersiveController.animateResizeChange(changeFindTaskChange, transaction, transaction2, transitionFinishCallback2);
        return dispatchToLeftoverHandler$default(this, iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback2);
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startMinimizeAllTransition(WindowContainerTransaction windowContainerTransaction, int i) {
        IBinder iBinderStartTransition = this.transitions.startTransition(1020, windowContainerTransaction, this);
        List list = this.pendingMixedTransitions;
        iBinderStartTransition.getClass();
        ((ArrayList) list).add(new PendingMixedTransition.MinimizeAll(iBinderStartTransition, i));
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startMinimizedModeTransition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX.isTrue()) {
            return this.freeformTaskTransitionHandler.startMinimizedModeTransition(i, windowContainerTransaction, z);
        }
        IBinder iBinderStartTransition = this.transitions.startTransition(1020, windowContainerTransaction, this);
        List list = this.pendingMixedTransitions;
        iBinderStartTransition.getClass();
        ((ArrayList) list).add(new PendingMixedTransition.Minimize(iBinderStartTransition, i, z));
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startPipTransition(WindowContainerTransaction windowContainerTransaction) {
        return this.freeformTaskTransitionHandler.startPipTransition(windowContainerTransaction);
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startRemoveTransition(WindowContainerTransaction windowContainerTransaction) {
        if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS_BUGFIX.isTrue()) {
            return this.freeformTaskTransitionHandler.startRemoveTransition(windowContainerTransaction);
        }
        IBinder iBinderStartTransition = this.transitions.startTransition(2, windowContainerTransaction, this);
        List list = this.pendingMixedTransitions;
        iBinderStartTransition.getClass();
        ((ArrayList) list).add(new PendingMixedTransition.Close(iBinderStartTransition));
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final void startWindowingModeTransition(WindowContainerTransaction windowContainerTransaction, int i) {
        this.freeformTaskTransitionHandler.startWindowingModeTransition(windowContainerTransaction, i);
    }

    public static /* synthetic */ void getPendingMixedTransitions$annotations() {
    }
}
