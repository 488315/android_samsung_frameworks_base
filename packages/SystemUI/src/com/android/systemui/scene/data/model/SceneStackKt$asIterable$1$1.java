package com.android.systemui.scene.data.model;

import com.android.compose.animation.scene.SceneKey;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;

/* loaded from: classes2.dex */
final class SceneStackKt$asIterable$1$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ SceneStack $this_asIterable;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SceneStackKt$asIterable$1$1(SceneStack sceneStack, Continuation continuation) {
        super(2, continuation);
        this.$this_asIterable = sceneStack;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SceneStackKt$asIterable$1$1 sceneStackKt$asIterable$1$1 = new SceneStackKt$asIterable$1$1(this.$this_asIterable, continuation);
        sceneStackKt$asIterable$1$1.L$0 = obj;
        return sceneStackKt$asIterable$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SceneStackKt$asIterable$1$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005b, code lost:
    
        if (r1.yieldAll(r3, r5) != r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            SceneStack sceneStack = this.$this_asIterable;
            if (!Intrinsics.areEqual(sceneStack, EmptyStack.INSTANCE)) {
                if (!(sceneStack instanceof StackedNodes)) {
                    throw new NoWhenBranchMatchedException();
                }
                SceneKey sceneKey = ((StackedNodes) this.$this_asIterable).head;
                this.L$0 = sequenceScope;
                this.label = 1;
                if (sequenceScope.yield(sceneKey, this) != coroutineSingletons) {
                    SceneStackKt$asIterable$$inlined$Iterable$1 sceneStackKt$asIterable$$inlined$Iterable$1 = new SceneStackKt$asIterable$$inlined$Iterable$1(((StackedNodes) this.$this_asIterable).tail);
                    this.L$0 = null;
                    this.label = 2;
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        sequenceScope = (SequenceScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        SceneStackKt$asIterable$$inlined$Iterable$1 sceneStackKt$asIterable$$inlined$Iterable$12 = new SceneStackKt$asIterable$$inlined$Iterable$1(((StackedNodes) this.$this_asIterable).tail);
        this.L$0 = null;
        this.label = 2;
    }
}
