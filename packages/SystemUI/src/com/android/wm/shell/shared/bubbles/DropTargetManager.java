package com.android.wm.shell.shared.bubbles;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.ValueAnimator;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleViewProvider;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.shared.bubbles.DragZone;
import com.android.wm.shell.taskview.TaskView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DropTargetManager {
    public ValueAnimator animator;
    public final FrameLayout container;
    public final Context context;
    public final DragZoneChangedListener dragZoneChangedListener;
    public final DropTargetView dropTargetView;
    public final boolean isLayoutRtl;
    public final RectF morphRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    public DragState state;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class DragState {
        public DragZone currentDragZone;
        public final List dragZones;
        public final DragZone.Bubble initialDragZone;

        public DragState(DropTargetManager dropTargetManager, List<? extends DragZone> list, DraggedObject draggedObject) {
            DragZone.Bubble bubble;
            this.dragZones = list;
            if (draggedObject.getInitialLocation().isOnLeft(dropTargetManager.isLayoutRtl)) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    if (obj instanceof DragZone.Bubble.Left) {
                        arrayList.add(obj);
                    }
                }
                bubble = (DragZone.Bubble) CollectionsKt___CollectionsKt.first((List) arrayList);
            } else {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : list) {
                    if (obj2 instanceof DragZone.Bubble.Right) {
                        arrayList2.add(obj2);
                    }
                }
                bubble = (DragZone.Bubble) CollectionsKt___CollectionsKt.first((List) arrayList2);
            }
            this.initialDragZone = bubble;
            this.currentDragZone = bubble;
        }
    }

    public interface DragZoneChangedListener {
    }

    static {
        new Companion(null);
    }

    public DropTargetManager(Context context, FrameLayout frameLayout, DragZoneChangedListener dragZoneChangedListener) {
        this.context = context;
        this.container = frameLayout;
        this.dragZoneChangedListener = dragZoneChangedListener;
        this.dropTargetView = new DropTargetView(context);
        this.isLayoutRtl = frameLayout.isLayoutRtl();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.wm.shell.shared.bubbles.DropTargetManager$$ExternalSyntheticLambda0] */
    public final void onDragEnded() {
        DragState dragState = this.state;
        if (dragState == null) {
            return;
        }
        startFadeAnimation(this.dropTargetView.getAlpha(), 0.0f, new Function0() { // from class: com.android.wm.shell.shared.bubbles.DropTargetManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DropTargetManager dropTargetManager = this.f$0;
                dropTargetManager.container.removeView(dropTargetManager.dropTargetView);
                return Unit.INSTANCE;
            }
        });
        DragZone dragZone = dragState.currentDragZone;
        BubbleBarLayerView.AnonymousClass1 anonymousClass1 = (BubbleBarLayerView.AnonymousClass1) this.dragZoneChangedListener;
        BubbleBarLayerView bubbleBarLayerView = BubbleBarLayerView.this;
        BubbleViewProvider bubbleViewProvider = bubbleBarLayerView.mExpandedBubble;
        if (bubbleViewProvider == null || !(bubbleViewProvider instanceof Bubble)) {
            int i = BubbleBarLayerView.$r8$clinit;
            Log.w("BubbleBarLayerView", "dropped invalid bubble: " + bubbleBarLayerView.mExpandedBubble);
        } else {
            boolean z = dragZone instanceof DragZone.Bubble.Left;
            boolean z2 = dragZone instanceof DragZone.Bubble.Right;
            BubbleBarLayerView.LocationChangeListener locationChangeListener = anonymousClass1.val$locationChangeListener;
            if (!z && !z2) {
                BubbleBarLayerView.this.mBubbleController.getClass();
            }
            if (dragZone instanceof DragZone.FullScreen) {
                TaskView taskView = ((Bubble) bubbleBarLayerView.mExpandedBubble).mBubbleTaskView.taskView;
                taskView.mTaskViewController.moveTaskViewToFullscreen(taskView.mTaskViewTaskController);
                locationChangeListener.onRelease(anonymousClass1.mInitialLocation);
            } else if (z) {
                locationChangeListener.onRelease(BubbleBarLocation.LEFT);
            } else if (z2) {
                locationChangeListener.onRelease(BubbleBarLocation.RIGHT);
            }
        }
        this.state = null;
    }

    public final void startFadeAnimation(float f, float f2, final DropTargetManager$$ExternalSyntheticLambda0 dropTargetManager$$ExternalSyntheticLambda0) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        long j = f < f2 ? 150L : 100L;
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.bubbles.DropTargetManager.startFadeAnimation.1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                DropTargetManager.this.dropTargetView.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
            }
        });
        if (dropTargetManager$$ExternalSyntheticLambda0 != null) {
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.shared.bubbles.DropTargetManager$doOnEnd$1
                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    dropTargetManager$$ExternalSyntheticLambda0.invoke();
                }
            });
        }
        this.animator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.start(false);
    }
}
