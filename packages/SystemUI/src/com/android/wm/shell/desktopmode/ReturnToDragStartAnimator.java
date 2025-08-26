package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import java.util.function.Supplier;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ReturnToDragStartAnimator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Animator boundsAnimator;
    public final InteractionJankMonitor interactionJankMonitor;
    public DesktopModeWindowDecorViewModel.DesktopModeOnTaskRepositionAnimationListener taskRepositionAnimationListener;
    public final Supplier transactionSupplier;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ReturnToDragStartAnimator(Supplier<SurfaceControl.Transaction> supplier, InteractionJankMonitor interactionJankMonitor) {
        this.transactionSupplier = supplier;
        this.interactionJankMonitor = interactionJankMonitor;
    }

    public final void start(final int i, final SurfaceControl surfaceControl, final Rect rect, final Rect rect2, final Function0 function0) {
        final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.transactionSupplier.get();
        Animator animator = this.boundsAnimator;
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofObject(new RectEvaluator(), rect, rect2).setDuration(300L);
        duration.getClass();
        duration.addListener(new Animator.AnimatorListener(surfaceControl, rect2, i, function0, this, surfaceControl, rect, i) { // from class: com.android.wm.shell.desktopmode.ReturnToDragStartAnimator$start$lambda$2$$inlined$addListener$default$1
            public final /* synthetic */ Function0 $doOnEnd$inlined;
            public final /* synthetic */ Rect $endBounds$inlined;
            public final /* synthetic */ Rect $startBounds$inlined;
            public final /* synthetic */ int $taskId$inlined;
            public final /* synthetic */ int $taskId$inlined$1;
            public final /* synthetic */ SurfaceControl $taskSurface$inlined;
            public final /* synthetic */ SurfaceControl $taskSurface$inlined$1;

            {
                this.$taskSurface$inlined$1 = surfaceControl;
                this.$startBounds$inlined = rect;
                this.$taskId$inlined$1 = i;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.this$0.transactionSupplier.get();
                SurfaceControl surfaceControl2 = this.$taskSurface$inlined;
                Rect rect3 = this.$endBounds$inlined;
                transaction2.setPosition(surfaceControl2, rect3.left, rect3.top).show(this.$taskSurface$inlined).apply();
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskRepositionAnimationListener desktopModeOnTaskRepositionAnimationListener = this.this$0.taskRepositionAnimationListener;
                if (desktopModeOnTaskRepositionAnimationListener == null) {
                    desktopModeOnTaskRepositionAnimationListener = null;
                }
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.$taskId$inlined);
                if (desktopModeWindowDecoration != null) {
                    desktopModeWindowDecoration.setAnimatingTaskResizeOrReposition(false);
                }
                this.this$0.boundsAnimator = null;
                Function0 function02 = this.$doOnEnd$inlined;
                if (function02 != null) {
                    function02.invoke();
                }
                this.this$0.interactionJankMonitor.end(118);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.this$0.transactionSupplier.get();
                SurfaceControl surfaceControl2 = this.$taskSurface$inlined$1;
                Rect rect3 = this.$startBounds$inlined;
                transaction2.setPosition(surfaceControl2, rect3.left, rect3.top).show(this.$taskSurface$inlined$1).apply();
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskRepositionAnimationListener desktopModeOnTaskRepositionAnimationListener = this.this$0.taskRepositionAnimationListener;
                if (desktopModeOnTaskRepositionAnimationListener == null) {
                    desktopModeOnTaskRepositionAnimationListener = null;
                }
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.$taskId$inlined$1);
                if (desktopModeWindowDecoration != null) {
                    desktopModeWindowDecoration.setAnimatingTaskResizeOrReposition(true);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator2) {
            }
        });
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.ReturnToDragStartAnimator$start$1$3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Rect rect3 = (Rect) valueAnimator.getAnimatedValue();
                transaction.setPosition(surfaceControl, rect3.left, rect3.top).show(surfaceControl).apply();
            }
        });
        duration.start();
        this.boundsAnimator = duration;
    }

    public ReturnToDragStartAnimator(InteractionJankMonitor interactionJankMonitor) {
        this(new Supplier() { // from class: com.android.wm.shell.desktopmode.ReturnToDragStartAnimator.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        }, interactionJankMonitor);
    }
}
