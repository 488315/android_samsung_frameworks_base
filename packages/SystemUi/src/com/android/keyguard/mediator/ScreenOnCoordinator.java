package com.android.keyguard.mediator;

import android.os.Handler;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import java.util.Optional;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScreenOnCoordinator {
    public final FoldAodAnimationController foldAodAnimationController;
    public final Handler mainHandler;
    public final PendingTasksContainer pendingTasks = new PendingTasksContainer();
    public final UnfoldLightRevealOverlayAnimation unfoldLightRevealAnimation;

    public ScreenOnCoordinator(Optional<SysUIUnfoldComponent> optional, Handler handler) {
        this.mainHandler = handler;
        this.unfoldLightRevealAnimation = (UnfoldLightRevealOverlayAnimation) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$unfoldLightRevealAnimation$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SysUIUnfoldComponent) obj).getUnfoldLightRevealOverlayAnimation();
            }
        }).orElse(null);
        this.foldAodAnimationController = (FoldAodAnimationController) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$foldAodAnimationController$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SysUIUnfoldComponent) obj).getFoldAodAnimationController();
            }
        }).orElse(null);
    }
}
