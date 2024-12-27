package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ FromAlternateBouncerTransitionInteractor $fromAlternateBouncerInteractor$inlined;
    final /* synthetic */ FromPrimaryBouncerTransitionInteractor $fromBouncerInteractor$inlined;
    final /* synthetic */ FromLockscreenTransitionInteractor $fromLockscreenInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor) {
        super(3, continuation);
        this.$fromLockscreenInteractor$inlined = fromLockscreenTransitionInteractor;
        this.$fromBouncerInteractor$inlined = fromPrimaryBouncerTransitionInteractor;
        this.$fromAlternateBouncerInteractor$inlined = fromAlternateBouncerTransitionInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1 windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1 = new WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$fromLockscreenInteractor$inlined, this.$fromBouncerInteractor$inlined, this.$fromAlternateBouncerInteractor$inlined);
        windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int i2 = WindowManagerLockscreenVisibilityInteractor.WhenMappings.$EnumSwitchMapping$0[((TransitionStep) this.L$1).from.ordinal()];
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = i2 != 1 ? i2 != 2 ? i2 != 3 ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : this.$fromAlternateBouncerInteractor$inlined.surfaceBehindVisibility : this.$fromBouncerInteractor$inlined.surfaceBehindVisibility : this.$fromLockscreenInteractor$inlined.surfaceBehindVisibility;
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
