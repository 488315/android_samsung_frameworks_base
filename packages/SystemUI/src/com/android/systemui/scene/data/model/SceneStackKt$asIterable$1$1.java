package com.android.systemui.scene.data.model;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005b, code lost:
    
        if (r1.yieldAll(r3, r5) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x005d, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0044, code lost:
    
        if (r1.yield(r6, r5) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L64
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L20:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            r1 = r6
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            com.android.systemui.scene.data.model.SceneStack r6 = r5.$this_asIterable
            com.android.systemui.scene.data.model.EmptyStack r4 = com.android.systemui.scene.data.model.EmptyStack.INSTANCE
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r4)
            if (r4 != 0) goto L64
            boolean r6 = r6 instanceof com.android.systemui.scene.data.model.StackedNodes
            if (r6 == 0) goto L5e
            com.android.systemui.scene.data.model.SceneStack r6 = r5.$this_asIterable
            com.android.systemui.scene.data.model.StackedNodes r6 = (com.android.systemui.scene.data.model.StackedNodes) r6
            com.android.compose.animation.scene.SceneKey r6 = r6.head
            r5.L$0 = r1
            r5.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = r1.yield(r6, r5)
            if (r6 != r0) goto L47
            goto L5d
        L47:
            com.android.systemui.scene.data.model.SceneStack r6 = r5.$this_asIterable
            com.android.systemui.scene.data.model.StackedNodes r6 = (com.android.systemui.scene.data.model.StackedNodes) r6
            com.android.systemui.scene.data.model.SceneStack r6 = r6.tail
            com.android.systemui.scene.data.model.SceneStackKt$asIterable$$inlined$Iterable$1 r3 = new com.android.systemui.scene.data.model.SceneStackKt$asIterable$$inlined$Iterable$1
            r3.<init>(r6)
            r6 = 0
            r5.L$0 = r6
            r5.label = r2
            java.lang.Object r5 = r1.yieldAll(r3, r5)
            if (r5 != r0) goto L64
        L5d:
            return r0
        L5e:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        L64:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.data.model.SceneStackKt$asIterable$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
