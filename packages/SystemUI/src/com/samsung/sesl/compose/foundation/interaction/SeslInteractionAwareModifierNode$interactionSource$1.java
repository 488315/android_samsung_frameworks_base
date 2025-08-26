package com.samsung.sesl.compose.foundation.interaction;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes4.dex */
final class SeslInteractionAwareModifierNode$interactionSource$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SeslInteractionAwareModifierNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslInteractionAwareModifierNode$interactionSource$1(SeslInteractionAwareModifierNode seslInteractionAwareModifierNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = seslInteractionAwareModifierNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslInteractionAwareModifierNode$interactionSource$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslInteractionAwareModifierNode$interactionSource$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SeslInteractionAwareModifierNode seslInteractionAwareModifierNode = this.this$0;
        StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(seslInteractionAwareModifierNode.getCoroutineScope(), null, null, new SeslInteractionAwareModifierNode$collectInteractionEvents$1(seslInteractionAwareModifierNode.interactionSource, seslInteractionAwareModifierNode, null), 3);
        StandaloneCoroutine standaloneCoroutine = seslInteractionAwareModifierNode.observeInteractionsJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        seslInteractionAwareModifierNode.observeInteractionsJob = standaloneCoroutineLaunch$default;
        return Unit.INSTANCE;
    }
}
