package com.android.systemui.display.data.repository;

import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.ShellTransitions;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class FocusedDisplayRepositoryImpl$focusedTask$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ShellTransitions $transitions;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FocusedDisplayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FocusedDisplayRepositoryImpl$focusedTask$1(ShellTransitions shellTransitions, FocusedDisplayRepositoryImpl focusedDisplayRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$transitions = shellTransitions;
        this.this$0 = focusedDisplayRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FocusedDisplayRepositoryImpl$focusedTask$1 focusedDisplayRepositoryImpl$focusedTask$1 = new FocusedDisplayRepositoryImpl$focusedTask$1(this.$transitions, this.this$0, continuation);
        focusedDisplayRepositoryImpl$focusedTask$1.L$0 = obj;
        return focusedDisplayRepositoryImpl$focusedTask$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FocusedDisplayRepositoryImpl$focusedTask$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl$focusedTask$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new FocusTransitionListener() { // from class: com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl$focusedTask$1$listener$1
                @Override // com.android.wm.shell.shared.FocusTransitionListener
                public final void onFocusedDisplayChanged(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Integer.valueOf(i2));
                }
            };
            this.$transitions.setFocusTransitionListener(r1, this.this$0.backgroundExecutor);
            final ShellTransitions shellTransitions = this.$transitions;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl$focusedTask$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    shellTransitions.unsetFocusTransitionListener(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
