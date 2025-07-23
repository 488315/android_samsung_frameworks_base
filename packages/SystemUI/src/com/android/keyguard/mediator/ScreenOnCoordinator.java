package com.android.keyguard.mediator;

import android.os.Handler;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScreenOnCoordinator {
    public final FoldAodAnimationController foldAodAnimationController;
    public final Set fullScreenLightRevealAnimations;
    public final PendingTasksContainer pendingTasks;

    public ScreenOnCoordinator(Optional<SysUIUnfoldComponent> optional, Handler handler) {
        final ScreenOnCoordinator$foldAodAnimationController$1 screenOnCoordinator$foldAodAnimationController$1 = ScreenOnCoordinator$foldAodAnimationController$1.INSTANCE;
        this.foldAodAnimationController = (FoldAodAnimationController) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return Function1.this.mo779invoke(obj);
            }
        }).orElse(null);
        final ScreenOnCoordinator$fullScreenLightRevealAnimations$1 screenOnCoordinator$fullScreenLightRevealAnimations$1 = ScreenOnCoordinator$fullScreenLightRevealAnimations$1.INSTANCE;
        this.fullScreenLightRevealAnimations = (Set) optional.map(new Function() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return Function1.this.mo779invoke(obj);
            }
        }).orElse(null);
        this.pendingTasks = new PendingTasksContainer();
    }
}
