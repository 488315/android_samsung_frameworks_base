package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.view.animation.DecelerateInterpolator;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopImmersiveController implements Transitions.TransitionHandler, Transitions.TransitionObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener onTaskResizeAnimationListener;
    public final List pendingImmersiveTransitions;
    public final RectEvaluator rectEvaluator;
    public final ShellCommandHandler shellCommandHandler;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final Function0 transactionSupplier;
    public final Transitions transitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getFULL_IMMERSIVE_ANIM_DURATION_MS$annotations() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Direction {
        public static final /* synthetic */ Direction[] $VALUES;
        public static final Direction ENTER;
        public static final Direction EXIT;

        static {
            Direction direction = new Direction("ENTER", 0);
            ENTER = direction;
            Direction direction2 = new Direction("EXIT", 1);
            EXIT = direction2;
            Direction[] directionArr = {direction, direction2};
            $VALUES = directionArr;
            EnumEntriesKt.enumEntries(directionArr);
        }

        private Direction(String str, int i) {
        }

        public static Direction valueOf(String str) {
            return (Direction) Enum.valueOf(Direction.class, str);
        }

        public static Direction[] values() {
            return (Direction[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ExitReason {
        public static final /* synthetic */ ExitReason[] $VALUES;
        public static final ExitReason APP_NOT_IMMERSIVE;
        public static final ExitReason CLOSED;
        public static final ExitReason MINIMIZED;
        public static final ExitReason TASK_LAUNCH;
        public static final ExitReason USER_INTERACTION;

        static {
            ExitReason exitReason = new ExitReason("APP_NOT_IMMERSIVE", 0);
            APP_NOT_IMMERSIVE = exitReason;
            ExitReason exitReason2 = new ExitReason("USER_INTERACTION", 1);
            USER_INTERACTION = exitReason2;
            ExitReason exitReason3 = new ExitReason("TASK_LAUNCH", 2);
            TASK_LAUNCH = exitReason3;
            ExitReason exitReason4 = new ExitReason("MINIMIZED", 3);
            MINIMIZED = exitReason4;
            ExitReason exitReason5 = new ExitReason("CLOSED", 4);
            CLOSED = exitReason5;
            ExitReason[] exitReasonArr = {exitReason, exitReason2, exitReason3, exitReason4, exitReason5};
            $VALUES = exitReasonArr;
            EnumEntriesKt.enumEntries(exitReasonArr);
        }

        private ExitReason(String str, int i) {
        }

        public static ExitReason valueOf(String str) {
            return (ExitReason) Enum.valueOf(ExitReason.class, str);
        }

        public static ExitReason[] values() {
            return (ExitReason[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ExitResult {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Exit extends ExitResult {
            public final int exitingTask;
            public final Function1 runOnTransitionStart;

            public Exit(int i, Function1 function1) {
                super(null);
                this.exitingTask = i;
                this.runOnTransitionStart = function1;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Exit)) {
                    return false;
                }
                Exit exit = (Exit) obj;
                return this.exitingTask == exit.exitingTask && Intrinsics.areEqual(this.runOnTransitionStart, exit.runOnTransitionStart);
            }

            public final int hashCode() {
                return this.runOnTransitionStart.hashCode() + (Integer.hashCode(this.exitingTask) * 31);
            }

            public final String toString() {
                return "Exit(exitingTask=" + this.exitingTask + ", runOnTransitionStart=" + this.runOnTransitionStart + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class NoExit extends ExitResult {
            public static final NoExit INSTANCE = new NoExit();

            private NoExit() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NoExit);
            }

            public final int hashCode() {
                return 897436376;
            }

            public final String toString() {
                return "NoExit";
            }
        }

        public /* synthetic */ ExitResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Exit asExit() {
            if (this instanceof Exit) {
                return (Exit) this;
            }
            return null;
        }

        private ExitResult() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PendingTransition {
        public final boolean animate;
        public final Direction direction;
        public final int displayId;
        public final int taskId;
        public IBinder transition;

        public PendingTransition(int i, int i2, Direction direction, IBinder iBinder, boolean z) {
            this.taskId = i;
            this.displayId = i2;
            this.direction = direction;
            this.transition = iBinder;
            this.animate = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PendingTransition)) {
                return false;
            }
            PendingTransition pendingTransition = (PendingTransition) obj;
            return this.taskId == pendingTransition.taskId && this.displayId == pendingTransition.displayId && this.direction == pendingTransition.direction && Intrinsics.areEqual(this.transition, pendingTransition.transition) && this.animate == pendingTransition.animate;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.animate) + ((this.transition.hashCode() + ((this.direction.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, Integer.hashCode(this.taskId) * 31, 31)) * 31)) * 31);
        }

        public final String toString() {
            IBinder iBinder = this.transition;
            StringBuilder sb = new StringBuilder("PendingTransition(taskId=");
            sb.append(this.taskId);
            sb.append(", displayId=");
            sb.append(this.displayId);
            sb.append(", direction=");
            sb.append(this.direction);
            sb.append(", transition=");
            sb.append(iBinder);
            sb.append(", animate=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.animate, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransitionState {
        public final Direction direction;
        public final int displayId;
        public final int taskId;
        public final IBinder transition;

        public TransitionState(IBinder iBinder, int i, int i2, Direction direction) {
            this.transition = iBinder;
            this.displayId = i;
            this.taskId = i2;
            this.direction = direction;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TransitionState)) {
                return false;
            }
            TransitionState transitionState = (TransitionState) obj;
            return Intrinsics.areEqual(this.transition, transitionState.transition) && this.displayId == transitionState.displayId && this.taskId == transitionState.taskId && this.direction == transitionState.direction;
        }

        public final int hashCode() {
            return this.direction.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, this.transition.hashCode() * 31, 31), 31);
        }

        public final String toString() {
            return "TransitionState(transition=" + this.transition + ", displayId=" + this.displayId + ", taskId=" + this.taskId + ", direction=" + this.direction + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Direction.values().length];
            try {
                iArr[Direction.EXIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Direction.ENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ExitReason.values().length];
            try {
                iArr2[ExitReason.TASK_LAUNCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ExitReason.APP_NOT_IMMERSIVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public DesktopImmersiveController(ShellInit shellInit, Transitions transitions, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ShellCommandHandler shellCommandHandler, Function0 function0) {
        this.transitions = transitions;
        this.desktopUserRepositories = desktopUserRepositories;
        this.displayController = displayController;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.shellCommandHandler = shellCommandHandler;
        this.transactionSupplier = function0;
        this.pendingImmersiveTransitions = new ArrayList();
        this.rectEvaluator = new RectEvaluator();
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController.2
            @Override // java.lang.Runnable
            public final void run() {
                final DesktopImmersiveController desktopImmersiveController = DesktopImmersiveController.this;
                desktopImmersiveController.getClass();
                desktopImmersiveController.shellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$onInit$1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        DesktopImmersiveController desktopImmersiveController2 = DesktopImmersiveController.this;
                        int i = DesktopImmersiveController.$r8$clinit;
                        desktopImmersiveController2.getClass();
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "DesktopImmersiveController");
                        printWriter.println((str + "  ") + "pendingImmersiveTransitions=" + desktopImmersiveController2.pendingImmersiveTransitions);
                    }
                }, desktopImmersiveController);
            }
        }, this);
    }

    public static void addPendingImmersiveTransition$default(DesktopImmersiveController desktopImmersiveController, int i, int i2, Direction direction, IBinder iBinder) {
        ((ArrayList) desktopImmersiveController.pendingImmersiveTransitions).add(new PendingTransition(i, i2, direction, iBinder, true));
    }

    public static TransitionInfo.Change getTaskChange(TransitionInfo transitionInfo, int i) {
        Object obj;
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) obj).getTaskInfo();
            if (taskInfo != null && taskInfo.taskId == i) {
                break;
            }
        }
        return (TransitionInfo.Change) obj;
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopImmersive", objArr);
        ProtoLog.d(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public static void logV(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopImmersive", objArr);
        ProtoLog.v(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public final void animateResize(int i, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        Object obj;
        logD("animateResize for task#%d", Integer.valueOf(i));
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) obj).getTaskInfo();
            if (taskInfo != null && taskInfo.taskId == i) {
                break;
            }
        }
        TransitionInfo.Change change = (TransitionInfo.Change) obj;
        if (change != null) {
            animateResizeChange(change, transaction, transaction2, transitionFinishCallback);
            return;
        }
        logD("Did not find change for task#%d to animate", Integer.valueOf(i));
        transaction.apply();
        transitionFinishCallback.onTransitionFinished(null);
    }

    public final void animateResizeChange(TransitionInfo.Change change, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        taskInfo.getClass();
        final int i = taskInfo.taskId;
        final SurfaceControl leash = change.getLeash();
        Rect startAbsBounds = change.getStartAbsBounds();
        final Rect endAbsBounds = change.getEndAbsBounds();
        logD("Animating resize change for task#%d from %s to %s", Integer.valueOf(i), startAbsBounds, endAbsBounds);
        transaction.setPosition(leash, startAbsBounds.left, startAbsBounds.top).setWindowCrop(leash, startAbsBounds.width(), startAbsBounds.height()).show(leash);
        DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = this.onTaskResizeAnimationListener;
        if (desktopModeOnTaskResizeAnimationListener != null) {
            desktopModeOnTaskResizeAnimationListener.onAnimationStart(i, transaction, startAbsBounds);
        } else {
            transaction.apply();
        }
        final SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) this.transactionSupplier.invoke();
        ValueAnimator ofObject = ValueAnimator.ofObject(this.rectEvaluator, startAbsBounds, endAbsBounds);
        ofObject.setDuration(336L);
        ofObject.setInterpolator(new DecelerateInterpolator());
        ofObject.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$animateResizeChange$lambda$20$$inlined$addListener$default$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SurfaceControl.Transaction transaction4 = transaction2;
                SurfaceControl surfaceControl = leash;
                Rect rect = endAbsBounds;
                transaction4.setPosition(surfaceControl, rect.left, rect.top).setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height()).apply();
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener2 = this.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener2 != null) {
                    desktopModeOnTaskResizeAnimationListener2.onAnimationEnd(i);
                }
                transitionFinishCallback.onTransitionFinished(null);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$animateResizeChange$1$2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Rect rect = (Rect) valueAnimator.getAnimatedValue();
                transaction3.setPosition(leash, rect.left, rect.top).setWindowCrop(leash, rect.width(), rect.height()).apply();
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener2 = this.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener2 != null) {
                    desktopModeOnTaskResizeAnimationListener2.onBoundsChange(i, transaction3, rect);
                } else {
                    transaction3.apply();
                }
            }
        });
        ofObject.start();
    }

    public final ExitResult exitImmersiveIfApplicable(WindowContainerTransaction windowContainerTransaction, final int i, Integer num, ExitReason exitReason) {
        if (!DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            return ExitResult.NoExit.INSTANCE;
        }
        DesktopRepository.Desk activeDesk = this.desktopUserRepositories.getCurrent().desktopData.getActiveDesk(i);
        Integer num2 = activeDesk != null ? activeDesk.fullImmersiveTaskId : null;
        if (num2 == null) {
            return ExitResult.NoExit.INSTANCE;
        }
        final int intValue = num2.intValue();
        if (num != null && intValue == num.intValue()) {
            return ExitResult.NoExit.INSTANCE;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$1[exitReason.ordinal()];
        if (i2 == 1) {
            return ExitResult.NoExit.INSTANCE;
        }
        if (i2 == 2) {
            return ExitResult.NoExit.INSTANCE;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(intValue);
        if (runningTaskInfo == null) {
            return ExitResult.NoExit.INSTANCE;
        }
        logV("Appending immersive exit for task: %d in display: %d for reason: %s", num2, Integer.valueOf(i), exitReason);
        windowContainerTransaction.setBounds(runningTaskInfo.token, getExitDestinationBounds(runningTaskInfo));
        return new ExitResult.Exit(intValue, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                IBinder iBinder = (IBinder) obj;
                int i3 = DesktopImmersiveController.$r8$clinit;
                DesktopImmersiveController.Direction direction = DesktopImmersiveController.Direction.EXIT;
                ((ArrayList) DesktopImmersiveController.this.pendingImmersiveTransitions).add(new DesktopImmersiveController.PendingTransition(intValue, i, direction, iBinder, false));
                return Unit.INSTANCE;
            }
        });
    }

    public final Rect getExitDestinationBounds(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DisplayLayout displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId);
        if (displayLayout != null) {
            if (!DesktopModeFlags.ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE.isTrue()) {
                return DesktopModeUtils.calculateMaximizeBounds(displayLayout, runningTaskInfo);
            }
            Rect rect = (Rect) this.desktopUserRepositories.getCurrent().boundsBeforeFullImmersiveByTaskId.removeReturnOld(runningTaskInfo.taskId);
            return rect == null ? DesktopModeFlags.ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS.isTrue() ? DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, 0, null, 28) : DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout) : rect;
        }
        throw new IllegalStateException(("Expected non-null display layout for displayId: " + runningTaskInfo.displayId).toString());
    }

    public final PendingTransition getImmersiveTransition(IBinder iBinder) {
        Object obj;
        Iterator it = this.pendingImmersiveTransitions.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((PendingTransition) obj).transition, iBinder)) {
                break;
            }
        }
        return (PendingTransition) obj;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final boolean isImmersiveChange(IBinder iBinder, TransitionInfo.Change change) {
        ActivityManager.RunningTaskInfo taskInfo;
        List<PendingTransition> list = this.pendingImmersiveTransitions;
        if (list != null && list.isEmpty()) {
            return false;
        }
        for (PendingTransition pendingTransition : list) {
            if (Intrinsics.areEqual(pendingTransition.transition, iBinder) && (taskInfo = change.getTaskInfo()) != null) {
                if (pendingTransition.taskId == taskInfo.taskId) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isSwappingModes() {
        return ((ArrayList) this.pendingImmersiveTransitions).size() > 1;
    }

    public final void moveTaskToNonImmersive(ActivityManager.RunningTaskInfo runningTaskInfo, ExitReason exitReason) {
        if (!runningTaskInfo.isFreeform()) {
            throw new IllegalStateException("Task must already be in freeform");
        }
        if (!((ArrayList) this.pendingImmersiveTransitions).isEmpty()) {
            logV("Cannot start exit because transition(s) already in progress: %s", this.pendingImmersiveTransitions);
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$1[exitReason.ordinal()];
        if (i == 1 || i == 2) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setBounds(runningTaskInfo.token, getExitDestinationBounds(runningTaskInfo));
        logV("Moving task %d out of immersive mode, reason: %s", Integer.valueOf(runningTaskInfo.taskId), exitReason);
        IBinder startTransition = this.transitions.startTransition(6, windowContainerTransaction, this);
        int i2 = runningTaskInfo.taskId;
        int i3 = runningTaskInfo.displayId;
        Direction direction = Direction.EXIT;
        startTransition.getClass();
        addPendingImmersiveTransition$default(this, i2, i3, direction, startTransition);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
        if (!isSwappingModes()) {
            PendingTransition immersiveTransition = getImmersiveTransition(iBinder);
            if (immersiveTransition != null) {
                logV("Pending exit transition %s for task#%s finished", iBinder, immersiveTransition);
                ((ArrayList) this.pendingImmersiveTransitions).remove(immersiveTransition);
                return;
            }
            return;
        }
        Iterator it = ((ArrayList) this.pendingImmersiveTransitions).iterator();
        while (it.hasNext()) {
            PendingTransition pendingTransition = (PendingTransition) it.next();
            if (Intrinsics.areEqual(pendingTransition.transition, iBinder)) {
                logV("Pending exit transition %s for task#%s finished", iBinder, pendingTransition);
                it.remove();
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
        Object obj;
        int i = 0;
        if (isSwappingModes()) {
            ArrayList arrayList = (ArrayList) this.pendingImmersiveTransitions;
            int size = arrayList.size();
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                PendingTransition pendingTransition = (PendingTransition) obj2;
                if (Intrinsics.areEqual(pendingTransition.transition, iBinder)) {
                    logV("Pending transition %s for task#%s merged into %s", iBinder, Integer.valueOf(pendingTransition.taskId), iBinder2);
                    pendingTransition.transition = iBinder2;
                }
            }
            return;
        }
        ArrayList arrayList2 = (ArrayList) this.pendingImmersiveTransitions;
        int size2 = arrayList2.size();
        while (true) {
            if (i >= size2) {
                obj = null;
                break;
            }
            obj = arrayList2.get(i);
            i++;
            if (Intrinsics.areEqual(((PendingTransition) obj).transition, iBinder)) {
                break;
            }
        }
        PendingTransition pendingTransition2 = (PendingTransition) obj;
        if (pendingTransition2 != null) {
            logV("Pending transition %s for task#%s merged into %s", iBinder, Integer.valueOf(pendingTransition2.taskId), iBinder2);
            pendingTransition2.transition = iBinder2;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        boolean isSwappingModes = isSwappingModes();
        DesktopUserRepositories desktopUserRepositories = this.desktopUserRepositories;
        if (isSwappingModes) {
            DesktopRepository current = desktopUserRepositories.getCurrent();
            ArrayList arrayList = (ArrayList) this.pendingImmersiveTransitions;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                PendingTransition pendingTransition = (PendingTransition) obj;
                if (Intrinsics.areEqual(pendingTransition.transition, iBinder)) {
                    int i2 = pendingTransition.taskId;
                    TransitionInfo.Change taskChange = getTaskChange(transitionInfo, i2);
                    Direction direction = pendingTransition.direction;
                    if (taskChange == null) {
                        logV("Transition for task#%d in %s direction missing immersive change.", Integer.valueOf(i2), direction);
                    } else {
                        logV("Immersive transition for task#%d in %s direction verified", Integer.valueOf(i2), direction);
                        current.setTaskInFullImmersiveState(pendingTransition.displayId, i2, direction == Direction.ENTER);
                        if (DesktopModeFlags.ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE.isTrue()) {
                            int i3 = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
                            if (i3 == 1) {
                            } else {
                                if (i3 != 2) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                current.boundsBeforeFullImmersiveByTaskId.set(i2, new Rect(taskChange.getStartAbsBounds()));
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            return;
        }
        DesktopRepository current2 = desktopUserRepositories.getCurrent();
        PendingTransition immersiveTransition = getImmersiveTransition(iBinder);
        if (immersiveTransition != null) {
            int i4 = immersiveTransition.taskId;
            TransitionInfo.Change taskChange2 = getTaskChange(transitionInfo, i4);
            Direction direction2 = immersiveTransition.direction;
            if (taskChange2 == null) {
                logV("Transition for task#%d in %s direction missing immersive change.", Integer.valueOf(i4), direction2);
                return;
            }
            logV("Immersive transition for task#%d in %s direction verified", Integer.valueOf(i4), direction2);
            current2.setTaskInFullImmersiveState(immersiveTransition.displayId, i4, direction2 == Direction.ENTER);
            if (DesktopModeFlags.ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE.isTrue()) {
                int i5 = WhenMappings.$EnumSwitchMapping$0[direction2.ordinal()];
                if (i5 == 1) {
                } else {
                    if (i5 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    current2.boundsBeforeFullImmersiveByTaskId.set(i4, new Rect(taskChange2.getStartAbsBounds()));
                }
            }
        }
        List changes = transitionInfo.getChanges();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : changes) {
            if (((TransitionInfo.Change) obj2).getTaskInfo() != null) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        int i6 = 0;
        while (i6 < size2) {
            Object obj3 = arrayList2.get(i6);
            i6++;
            ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) obj3).getTaskInfo();
            taskInfo.getClass();
            if (current2.isTaskInFullImmersiveState(taskInfo.taskId)) {
                arrayList3.add(obj3);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        int size3 = arrayList3.size();
        int i7 = 0;
        while (i7 < size3) {
            Object obj4 = arrayList3.get(i7);
            i7++;
            TransitionInfo.Change change = (TransitionInfo.Change) obj4;
            if (change.getStartRotation() != change.getEndRotation()) {
                arrayList4.add(obj4);
            }
        }
        int size4 = arrayList4.size();
        int i8 = 0;
        while (i8 < size4) {
            Object obj5 = arrayList4.get(i8);
            i8++;
            TransitionInfo.Change change2 = (TransitionInfo.Change) obj5;
            ActivityManager.RunningTaskInfo taskInfo2 = change2.getTaskInfo();
            taskInfo2.getClass();
            logV("Detected immersive exit due to rotation for task#%d", Integer.valueOf(taskInfo2.taskId));
            ActivityManager.RunningTaskInfo taskInfo3 = change2.getTaskInfo();
            taskInfo3.getClass();
            int i9 = taskInfo3.displayId;
            ActivityManager.RunningTaskInfo taskInfo4 = change2.getTaskInfo();
            taskInfo4.getClass();
            current2.setTaskInFullImmersiveState(i9, taskInfo4.taskId, false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [int] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v9 */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        if (isSwappingModes()) {
            z = false;
            final ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) this.pendingImmersiveTransitions;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                PendingTransition pendingTransition = (PendingTransition) obj;
                if (Intrinsics.areEqual(pendingTransition.transition, iBinder)) {
                    if (pendingTransition.animate) {
                        arrayList.add(pendingTransition);
                    }
                }
            }
            Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
            int size2 = arrayList.size();
            int i2 = 0;
            final ?? r1 = z;
            while (i2 < size2) {
                Object obj2 = arrayList.get(i2);
                int i3 = i2 + 1;
                int i4 = r1 + 1;
                if (r1 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                final PendingTransition pendingTransition2 = (PendingTransition) obj2;
                logD("startAnimation transition=%s", pendingTransition2);
                final Transitions.TransitionFinishCallback transitionFinishCallback3 = transitionFinishCallback2;
                animateResize(pendingTransition2.taskId, transitionInfo, transaction, transaction2, new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$startAnimationForSwapModes$2$1
                    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                        if (r1 == CollectionsKt__CollectionsKt.getLastIndex(arrayList)) {
                            transitionFinishCallback3.onTransitionFinished(null);
                        }
                        ((ArrayList) this.pendingImmersiveTransitions).remove(pendingTransition2);
                    }
                });
                arrayList = arrayList;
                i2 = i3;
                r1 = i4;
                transitionFinishCallback2 = transitionFinishCallback;
            }
            return true;
        }
        z = false;
        final PendingTransition immersiveTransition = getImmersiveTransition(iBinder);
        if (immersiveTransition == null || !immersiveTransition.animate) {
            return z;
        }
        logD("startAnimation transition=%s", iBinder);
        animateResize(immersiveTransition.taskId, transitionInfo, transaction, transaction2, new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$startAnimation$1
            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                Transitions.TransitionFinishCallback.this.onTransitionFinished(null);
                ((ArrayList) this.pendingImmersiveTransitions).remove(immersiveTransition);
            }
        });
        return true;
    }

    public DesktopImmersiveController(ShellInit shellInit, Transitions transitions, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ShellCommandHandler shellCommandHandler) {
        this(shellInit, transitions, desktopUserRepositories, displayController, shellTaskOrganizer, shellCommandHandler, new DesktopImmersiveController$$ExternalSyntheticLambda0());
    }

    public final ExitResult exitImmersiveIfApplicable(WindowContainerTransaction windowContainerTransaction, final ActivityManager.RunningTaskInfo runningTaskInfo, ExitReason exitReason) {
        if (!DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            return ExitResult.NoExit.INSTANCE;
        }
        if (this.desktopUserRepositories.getCurrent().isTaskInFullImmersiveState(runningTaskInfo.taskId)) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, getExitDestinationBounds(runningTaskInfo));
            logV("Appending immersive exit for task: %d for reason: %s", Integer.valueOf(runningTaskInfo.taskId), exitReason);
            return new ExitResult.Exit(runningTaskInfo.taskId, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopImmersiveController$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                    IBinder iBinder = (IBinder) obj;
                    int i = DesktopImmersiveController.$r8$clinit;
                    int i2 = runningTaskInfo2.taskId;
                    int i3 = runningTaskInfo2.displayId;
                    DesktopImmersiveController.Direction direction = DesktopImmersiveController.Direction.EXIT;
                    ((ArrayList) DesktopImmersiveController.this.pendingImmersiveTransitions).add(new DesktopImmersiveController.PendingTransition(i2, i3, direction, iBinder, false));
                    return Unit.INSTANCE;
                }
            });
        }
        return ExitResult.NoExit.INSTANCE;
    }

    public static /* synthetic */ void getPendingImmersiveTransitions$annotations() {
    }
}
