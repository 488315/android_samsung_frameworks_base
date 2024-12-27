package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import dagger.Lazy;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AlternateBouncerInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Lazy $keyguardTransitionInteractor$inlined;
    final /* synthetic */ Lazy $sceneInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AlternateBouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, Lazy lazy, Lazy lazy2, AlternateBouncerInteractor alternateBouncerInteractor) {
        super(3, continuation);
        this.$keyguardTransitionInteractor$inlined = lazy;
        this.$sceneInteractor$inlined = lazy2;
        this.this$0 = alternateBouncerInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerInteractor$special$$inlined$flatMapLatest$1 alternateBouncerInteractor$special$$inlined$flatMapLatest$1 = new AlternateBouncerInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$keyguardTransitionInteractor$inlined, this.$sceneInteractor$inlined, this.this$0);
        alternateBouncerInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        alternateBouncerInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return alternateBouncerInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                ReadonlyStateFlow readonlyStateFlow = ((KeyguardTransitionInteractor) this.$keyguardTransitionInteractor$inlined.get()).currentKeyguardState;
                Flags.sceneContainer();
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Scenes.Lockscreen), AlternateBouncerInteractor$canShowAlternateBouncer$1$2.INSTANCE), new AlternateBouncerInteractor$canShowAlternateBouncer$lambda$5$$inlined$flatMapLatest$1(null, this.this$0));
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
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
