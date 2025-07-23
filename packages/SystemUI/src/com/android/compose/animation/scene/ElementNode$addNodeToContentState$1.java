package com.android.compose.animation.scene;

import com.android.compose.animation.scene.ElementNode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ElementNode$addNodeToContentState$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ElementNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ElementNode$addNodeToContentState$1(ElementNode elementNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = elementNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ElementNode$addNodeToContentState$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ElementNode$addNodeToContentState$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ElementNode elementNode = this.this$0;
        ElementNode.Companion companion = ElementNode.Companion;
        int size = elementNode.getStateInContent().nodes.size();
        if (size == 1 && this.this$0.getStateInContent().nodes.contains(this.this$0)) {
            return Unit.INSTANCE;
        }
        ElementNode elementNode2 = this.this$0;
        throw new IllegalStateException((elementNode2.key + " was composed " + size + " times in " + elementNode2.getStateInContent().contents).toString());
    }
}
