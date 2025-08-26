package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.VisualIndicatorViewContainer;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;

/* loaded from: classes3.dex */
public final class VisualIndicatorViewContainer$fadeOutIndicator$1 implements Runnable {
    public final /* synthetic */ DesktopModeVisualIndicator.IndicatorType $currentType;
    public final /* synthetic */ int $displayId;
    public final /* synthetic */ Runnable $finishCallback;
    public final /* synthetic */ DisplayLayout $layout;
    public final /* synthetic */ SnapEventHandler $snapEventHandler;
    public final /* synthetic */ VisualIndicatorViewContainer this$0;

    public VisualIndicatorViewContainer$fadeOutIndicator$1(VisualIndicatorViewContainer visualIndicatorViewContainer, DesktopModeVisualIndicator.IndicatorType indicatorType, DisplayLayout displayLayout, int i, SnapEventHandler snapEventHandler, Runnable runnable) {
        this.this$0 = visualIndicatorViewContainer;
        this.$currentType = indicatorType;
        this.$layout = displayLayout;
        this.$displayId = i;
        this.$snapEventHandler = snapEventHandler;
        this.$finishCallback = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final VisualIndicatorViewContainer visualIndicatorViewContainer = this.this$0;
        View view = visualIndicatorViewContainer.indicatorView;
        if (view != null) {
            DesktopModeVisualIndicator.IndicatorType indicatorType = this.$currentType;
            DisplayLayout displayLayout = this.$layout;
            int i = this.$displayId;
            SnapEventHandler snapEventHandler = this.$snapEventHandler;
            final Runnable runnable = this.$finishCallback;
            DesktopModeVisualIndicator.IndicatorType indicatorTypeValueOf = DesktopModeVisualIndicator.IndicatorType.valueOf(indicatorType.name());
            VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getClass();
            Rect indicatorBounds = VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout, indicatorTypeValueOf, visualIndicatorViewContainer.bubbleBoundsProvider, i, snapEventHandler);
            VisualIndicatorViewContainer.VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorViewContainer.VisualIndicatorAnimator(view, indicatorBounds, VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getMinBounds(indicatorBounds), displayLayout, i);
            visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
            VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.setupIndicatorAnimation(visualIndicatorAnimator, VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_OUT_ANIM, indicatorTypeValueOf);
            visualIndicatorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$fadeOutIndicator$1$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        visualIndicatorViewContainer.mainExecutor.execute(runnable2);
                    }
                }
            });
            visualIndicatorAnimator.start();
        }
    }
}
