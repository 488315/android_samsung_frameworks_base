package com.android.keyguard.mediator;

import android.os.Handler;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ScreenOnCoordinator {
    public final FoldAodAnimationController foldAodAnimationController;
    public final Set fullScreenLightRevealAnimations;
    public final PendingTasksContainer pendingTasks = new PendingTasksContainer();

    public ScreenOnCoordinator(Optional<SysUIUnfoldComponent> optional, Handler handler) {
        this.foldAodAnimationController = (FoldAodAnimationController) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$foldAodAnimationController$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SysUIUnfoldComponent) obj).getFoldAodAnimationController();
            }
        }).orElse(null);
        this.fullScreenLightRevealAnimations = (Set) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$fullScreenLightRevealAnimations$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SysUIUnfoldComponent) obj).getFullScreenLightRevealAnimations();
            }
        }).orElse(null);
    }
}
