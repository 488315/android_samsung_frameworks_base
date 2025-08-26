package com.android.wm.shell.bubbles.bar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.BubblePopupView;
import com.android.wm.shell.taskview.TaskView;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BubbleEducationViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PhysicsAnimator animator;
    public final Context context;
    public BubblePopupView educationView;
    public final Listener listener;
    public ViewGroup rootView;
    public final Lazy springConfig$delegate = LazyKt__LazyJVMKt.lazy(new BubbleEducationViewController$$ExternalSyntheticLambda0(1));
    public final Lazy scrimView$delegate = LazyKt__LazyJVMKt.lazy(new BubbleEducationViewController$$ExternalSyntheticLambda2(this, 0));
    public final Lazy controller$delegate = LazyKt__LazyJVMKt.lazy(new BubbleEducationViewController$$ExternalSyntheticLambda2(this, 1));

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Listener {
    }

    static {
        new Companion(null);
    }

    public BubbleEducationViewController(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public static void hideEducation$default(final BubbleEducationViewController bubbleEducationViewController, boolean z) {
        TaskView taskView;
        final BubbleEducationViewController$$ExternalSyntheticLambda0 bubbleEducationViewController$$ExternalSyntheticLambda0 = new BubbleEducationViewController$$ExternalSyntheticLambda0(0);
        if (z) {
            bubbleEducationViewController.getClass();
            bubbleEducationViewController.animateTransition(false, new Function0() { // from class: com.android.wm.shell.bubbles.bar.BubbleEducationViewController$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TaskView taskView2;
                    int i = BubbleEducationViewController.$r8$clinit;
                    BubbleEducationViewController bubbleEducationViewController2 = this.f$0;
                    ViewGroup viewGroup = bubbleEducationViewController2.rootView;
                    if (viewGroup != null) {
                        viewGroup.removeView(bubbleEducationViewController2.educationView);
                    }
                    ViewGroup viewGroup2 = bubbleEducationViewController2.rootView;
                    if (viewGroup2 != null) {
                        viewGroup2.removeView((View) bubbleEducationViewController2.scrimView$delegate.getValue());
                    }
                    bubbleEducationViewController2.educationView = null;
                    bubbleEducationViewController2.rootView = null;
                    bubbleEducationViewController2.animator = null;
                    bubbleEducationViewController$$ExternalSyntheticLambda0.invoke();
                    BubbleBarExpandedView bubbleBarExpandedView = ((BubbleBarLayerView$$ExternalSyntheticLambda0) bubbleEducationViewController2.listener).f$0.mExpandedView;
                    if (bubbleBarExpandedView != null && (taskView2 = bubbleBarExpandedView.mTaskView) != null && bubbleBarExpandedView.mLayerBoundsSupplier != null) {
                        taskView2.mObscuredTouchRegion = null;
                        taskView2.invalidate();
                    }
                    return Unit.INSTANCE;
                }
            });
            return;
        }
        ViewGroup viewGroup = bubbleEducationViewController.rootView;
        if (viewGroup != null) {
            viewGroup.removeView(bubbleEducationViewController.educationView);
        }
        ViewGroup viewGroup2 = bubbleEducationViewController.rootView;
        if (viewGroup2 != null) {
            viewGroup2.removeView((View) bubbleEducationViewController.scrimView$delegate.getValue());
        }
        bubbleEducationViewController.educationView = null;
        bubbleEducationViewController.rootView = null;
        bubbleEducationViewController.animator = null;
        bubbleEducationViewController$$ExternalSyntheticLambda0.invoke();
        BubbleBarExpandedView bubbleBarExpandedView = ((BubbleBarLayerView$$ExternalSyntheticLambda0) bubbleEducationViewController.listener).f$0.mExpandedView;
        if (bubbleBarExpandedView == null || (taskView = bubbleBarExpandedView.mTaskView) == null || bubbleBarExpandedView.mLayerBoundsSupplier == null) {
            return;
        }
        taskView.mObscuredTouchRegion = null;
        taskView.invalidate();
    }

    public final void animateTransition(boolean z, Function0 function0) {
        PhysicsAnimator physicsAnimator = this.animator;
        if (physicsAnimator == null) {
            function0.invoke();
            return;
        }
        physicsAnimator.spring(DynamicAnimation.ALPHA, z ? 1.0f : 0.0f);
        physicsAnimator.spring(DynamicAnimation.SCALE_X, z ? 1.0f : 0.5f);
        physicsAnimator.spring(DynamicAnimation.SCALE_Y, z ? 1.0f : 0.5f);
        physicsAnimator.withEndActions(function0);
        physicsAnimator.start();
    }
}
