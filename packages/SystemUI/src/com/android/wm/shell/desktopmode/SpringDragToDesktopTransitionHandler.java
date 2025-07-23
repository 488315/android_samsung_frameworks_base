package com.android.wm.shell.desktopmode;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import com.android.wm.shell.desktopmode.SpringDragToDesktopTransitionHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MoveToDesktopAnimator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SpringDragToDesktopTransitionHandler extends DragToDesktopTransitionHandler {
    public static final Companion Companion;
    public static final float FREEFORM_TASKS_ANIM_OFFSET;
    public static final float FREEFORM_TASKS_INITIAL_SCALE;
    public static final float POSITION_SPRING_DAMPING_RATIO;
    public static final float POSITION_SPRING_STIFFNESS;
    public static final float SIZE_SPRING_DAMPING_RATIO;
    public static final float SIZE_SPRING_STIFFNESS;
    public final PhysicsAnimator.SpringConfig positionSpringConfig;
    public final PhysicsAnimator.SpringConfig sizeSpringConfig;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float getAnimationFraction(Rect rect, Rect rect2, Rect rect3) {
            float height;
            int height2;
            int height3;
            if (rect.width() != rect2.width()) {
                height = rect3.width() - rect.width();
                height2 = rect2.width();
                height3 = rect.width();
            } else {
                if (rect.height() == rect2.height()) {
                    String str = "same start and end sizes, returning 0: startBounds=" + rect + ", endBounds=" + rect2 + ", animBounds=" + rect3;
                    ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                    String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
                    SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "SpringDragToDesktopTransitionHandler", new Object[0]);
                    ProtoLog.v(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
                    return 0.0f;
                }
                height = rect3.height() - rect.height();
                height2 = rect2.height();
                height3 = rect.height();
            }
            return height / (height2 - height3);
        }

        public final float propertyValue(String str, float f, float f2) {
            return SystemProperties.getInt(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("persist.wm.debug.desktop_transitions.drag_to_desktop.", str), (int) (f2 * f)) / f;
        }

        private Companion() {
        }

        public static /* synthetic */ void getSYSTEM_PROPERTIES_GROUP$annotations() {
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        FREEFORM_TASKS_INITIAL_SCALE = companion.propertyValue("freeform_tasks_initial_scale", 100.0f, 0.9f);
        FREEFORM_TASKS_ANIM_OFFSET = companion.propertyValue("freeform_tasks_anim_offset", 100.0f, 0.5f);
        POSITION_SPRING_STIFFNESS = companion.propertyValue("position_stiffness", 1.0f, 200.0f);
        POSITION_SPRING_DAMPING_RATIO = companion.propertyValue("position_damping_ratio", 100.0f, 0.75f);
        SIZE_SPRING_STIFFNESS = companion.propertyValue("size_stiffness", 1.0f, 200.0f);
        SIZE_SPRING_DAMPING_RATIO = companion.propertyValue("size_damping_ratio", 100.0f, 1.0f);
    }

    public SpringDragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional<BubbleController> optional, DesktopState desktopState) {
        this(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, null, desktopState, 64, null);
    }

    @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler
    public final void animateEndDragToDesktop(SurfaceControl.Transaction transaction, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        final DragToDesktopTransitionHandler.TransitionState requireTransitionState = requireTransitionState();
        TransitionInfo.Change draggedTaskChange = requireTransitionState.getDraggedTaskChange();
        if (draggedTaskChange == null) {
            throw new IllegalStateException("Expected non-null change of dragged task");
        }
        final SurfaceControl leash = draggedTaskChange.getLeash();
        final List freeformTaskChanges = requireTransitionState.getFreeformTaskChanges();
        final Rect startAbsBounds = draggedTaskChange.getStartAbsBounds();
        final Rect endAbsBounds = draggedTaskChange.getEndAbsBounds();
        MoveToDesktopAnimator dragAnimator = requireTransitionState.getDragAnimator();
        dragAnimator.velocityTracker.computeCurrentVelocity(1000);
        PointF pointF = new PointF(dragAnimator.velocityTracker.getXVelocity(), dragAnimator.velocityTracker.getYVelocity());
        requireTransitionState.getDragAnimator().cancelAnimator();
        final float scale = requireTransitionState.getDragAnimator().getScale();
        PointF pointF2 = requireTransitionState.getDragAnimator().position;
        Rect rect = new Rect(startAbsBounds);
        rect.offset((int) pointF2.x, (int) pointF2.y);
        Companion.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", "animateEndDragToDesktop: startBounds=" + startAbsBounds + ", endBounds=" + endAbsBounds + ", startScale=" + scale + ", startPosition=" + pointF2 + ", startBoundsWithOffset=" + rect);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "SpringDragToDesktopTransitionHandler", new Object[0]);
        ProtoLog.v(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
        DesktopTasksController$dragToDesktopStateListener$1 desktopTasksController$dragToDesktopStateListener$1 = this.dragToDesktopStateListener;
        if (desktopTasksController$dragToDesktopStateListener$1 != null) {
            desktopTasksController$dragToDesktopStateListener$1.removeVisualIndicator();
        }
        DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = this.onTaskResizeAnimationListener;
        if (desktopModeOnTaskResizeAnimationListener == null) {
            desktopModeOnTaskResizeAnimationListener = null;
        }
        desktopModeOnTaskResizeAnimationListener.onAnimationStart(requireTransitionState.getDraggedTaskId(), transaction, rect);
        final SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.transactionSupplier.get();
        PhysicsAnimator.Companion.getClass();
        PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(rect);
        FloatProperties$Companion$RECT_X$1 floatProperties$Companion$RECT_X$1 = FloatProperties.RECT_X;
        float f = endAbsBounds.left;
        float f2 = pointF.x;
        PhysicsAnimator.SpringConfig springConfig = this.positionSpringConfig;
        companion.spring(floatProperties$Companion$RECT_X$1, f, f2, springConfig);
        companion.spring(FloatProperties.RECT_Y, endAbsBounds.top, pointF.y, springConfig);
        FloatProperties$Companion$RECT_WIDTH$1 floatProperties$Companion$RECT_WIDTH$1 = FloatProperties.RECT_WIDTH;
        float width = endAbsBounds.width();
        PhysicsAnimator.SpringConfig springConfig2 = this.sizeSpringConfig;
        companion.spring(floatProperties$Companion$RECT_WIDTH$1, width, 0.0f, springConfig2);
        companion.spring(FloatProperties.RECT_HEIGHT, endAbsBounds.height(), 0.0f, springConfig2);
        companion.updateListeners.add(new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.desktopmode.SpringDragToDesktopTransitionHandler$animateEndDragToDesktop$1
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj) {
                Rect rect2 = (Rect) obj;
                float animationFraction = SpringDragToDesktopTransitionHandler.Companion.getAnimationFraction(startAbsBounds, endAbsBounds, rect2);
                float f3 = 1;
                float f4 = scale;
                float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f4, animationFraction, f4);
                float f5 = SpringDragToDesktopTransitionHandler.FREEFORM_TASKS_ANIM_OFFSET;
                float max = f5 != 1.0f ? Math.max(animationFraction - f5, 0.0f) / (1.0f - f5) : 0.0f;
                float f6 = SpringDragToDesktopTransitionHandler.FREEFORM_TASKS_INITIAL_SCALE;
                float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f6, max, f6);
                SurfaceControl.Transaction transaction3 = transaction2;
                SurfaceControl surfaceControl = leash;
                List<TransitionInfo.Change> list = freeformTaskChanges;
                transaction3.setScale(surfaceControl, m$1, m$1);
                transaction3.setPosition(surfaceControl, rect2.left, rect2.top);
                for (TransitionInfo.Change change : list) {
                    float f7 = f3 - m$12;
                    float f8 = 2;
                    transaction3.setPosition(change.getLeash(), ((change.getEndAbsBounds().width() * f7) / f8) + change.getEndAbsBounds().left, ((change.getEndAbsBounds().height() * f7) / f8) + change.getEndAbsBounds().top);
                    if (change.getMode() == 4) {
                        transaction3.setAlpha(change.getLeash(), f3 - max);
                    } else {
                        transaction3.setScale(change.getLeash(), m$12, m$12);
                        transaction3.setAlpha(change.getLeash(), max);
                    }
                }
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener2 = this.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener2 == null) {
                    desktopModeOnTaskResizeAnimationListener2 = null;
                }
                desktopModeOnTaskResizeAnimationListener2.onBoundsChange(requireTransitionState.getDraggedTaskId(), transaction2, rect2);
            }
        });
        companion.withEndActions(new Function0() { // from class: com.android.wm.shell.desktopmode.SpringDragToDesktopTransitionHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SpringDragToDesktopTransitionHandler.Companion companion2 = SpringDragToDesktopTransitionHandler.Companion;
                SpringDragToDesktopTransitionHandler springDragToDesktopTransitionHandler = SpringDragToDesktopTransitionHandler.this;
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener2 = springDragToDesktopTransitionHandler.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener2 == null) {
                    desktopModeOnTaskResizeAnimationListener2 = null;
                }
                desktopModeOnTaskResizeAnimationListener2.onAnimationEnd(requireTransitionState.getDraggedTaskId());
                transitionFinishCallback.onTransitionFinished(null);
                springDragToDesktopTransitionHandler.transitionState = null;
                springDragToDesktopTransitionHandler.interactionJankMonitor.end(116);
                return Unit.INSTANCE;
            }
        });
        companion.start();
    }

    @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler
    public final DragToDesktopTransitionHandler.DragToDesktopLayers calculateStartDragToDesktopLayers(TransitionInfo transitionInfo) {
        return new DragToDesktopTransitionHandler.DragToDesktopLayers(-1, RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1), (transitionInfo.getChanges().size() * 2) - 1, transitionInfo.getChanges().size() * 2);
    }

    @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler
    public final void setupEndDragToDesktop(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        super.setupEndDragToDesktop(transitionInfo, transaction, transaction2);
        for (TransitionInfo.Change change : requireTransitionState().getFreeformTaskChanges()) {
            float f = change.getEndAbsBounds().left;
            float width = change.getEndAbsBounds().width();
            float f2 = FREEFORM_TASKS_INITIAL_SCALE;
            float f3 = 1 - f2;
            float f4 = 2;
            transaction.setPosition(change.getLeash(), ((width * f3) / f4) + f, ((change.getEndAbsBounds().height() * f3) / f4) + change.getEndAbsBounds().top);
            transaction.setScale(change.getLeash(), f2, f2);
            transaction.setAlpha(change.getLeash(), 0.0f);
        }
    }

    public /* synthetic */ SpringDragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional optional, Supplier supplier, DesktopState desktopState, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, (i & 64) != 0 ? new Supplier() { // from class: com.android.wm.shell.desktopmode.SpringDragToDesktopTransitionHandler.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        } : supplier, desktopState);
    }

    public SpringDragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional<BubbleController> optional, Supplier<SurfaceControl.Transaction> supplier, DesktopState desktopState) {
        super(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, supplier, desktopState, null);
        this.positionSpringConfig = new PhysicsAnimator.SpringConfig(POSITION_SPRING_STIFFNESS, POSITION_SPRING_DAMPING_RATIO);
        this.sizeSpringConfig = new PhysicsAnimator.SpringConfig(SIZE_SPRING_STIFFNESS, SIZE_SPRING_DAMPING_RATIO);
    }
}
