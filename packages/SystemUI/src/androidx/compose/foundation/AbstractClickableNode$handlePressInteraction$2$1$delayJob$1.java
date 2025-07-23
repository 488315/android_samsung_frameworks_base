package androidx.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AbstractClickableNode$handlePressInteraction$2$1$delayJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ long $offset;
    Object L$0;
    int label;
    final /* synthetic */ AbstractClickableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClickableNode$handlePressInteraction$2$1$delayJob$1(AbstractClickableNode abstractClickableNode, long j, MutableInteractionSource mutableInteractionSource, Continuation continuation) {
        super(2, continuation);
        this.this$0 = abstractClickableNode;
        this.$offset = j;
        this.$interactionSource = mutableInteractionSource;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AbstractClickableNode$handlePressInteraction$2$1$delayJob$1(this.this$0, this.$offset, this.$interactionSource, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AbstractClickableNode$handlePressInteraction$2$1$delayJob$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0063, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r4, r6) == r0) goto L25;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            java.lang.Object r0 = r6.L$0
            androidx.compose.foundation.interaction.PressInteraction$Press r0 = (androidx.compose.foundation.interaction.PressInteraction$Press) r0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L7c
        L14:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L66
        L20:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.foundation.AbstractClickableNode r7 = r6.this$0
            androidx.compose.foundation.AbstractClickableNode$TraverseKey r1 = androidx.compose.foundation.AbstractClickableNode.TraverseKey
            r7.getClass()
            kotlin.jvm.internal.Ref$BooleanRef r1 = new kotlin.jvm.internal.Ref$BooleanRef
            r1.<init>()
            androidx.compose.foundation.gestures.ScrollableContainerNode$TraverseKey r4 = androidx.compose.foundation.gestures.ScrollableContainerNode.TraverseKey
            androidx.compose.foundation.ClickableKt$hasScrollableContainer$1 r5 = new androidx.compose.foundation.ClickableKt$hasScrollableContainer$1
            r5.<init>()
            androidx.compose.ui.node.TraversableNodeKt.traverseAncestors(r7, r4, r5)
            boolean r1 = r1.element
            if (r1 != 0) goto L5b
            int r1 = androidx.compose.foundation.Clickable_androidKt.$r8$clinit
            android.view.View r7 = androidx.compose.ui.node.DelegatableNode_androidKt.requireView(r7)
            android.view.ViewParent r7 = r7.getParent()
        L47:
            if (r7 == 0) goto L66
            boolean r1 = r7 instanceof android.view.ViewGroup
            if (r1 == 0) goto L66
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            boolean r1 = r7.shouldDelayChildPressedState()
            if (r1 == 0) goto L56
            goto L5b
        L56:
            android.view.ViewParent r7 = r7.getParent()
            goto L47
        L5b:
            long r4 = androidx.compose.foundation.Clickable_androidKt.TapIndicationDelay
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r7 != r0) goto L66
            goto L7a
        L66:
            androidx.compose.foundation.interaction.PressInteraction$Press r7 = new androidx.compose.foundation.interaction.PressInteraction$Press
            long r3 = r6.$offset
            r1 = 0
            r7.<init>(r3, r1)
            androidx.compose.foundation.interaction.MutableInteractionSource r1 = r6.$interactionSource
            r6.L$0 = r7
            r6.label = r2
            java.lang.Object r1 = r1.emit(r7, r6)
            if (r1 != r0) goto L7b
        L7a:
            return r0
        L7b:
            r0 = r7
        L7c:
            androidx.compose.foundation.AbstractClickableNode r6 = r6.this$0
            r6.pressInteraction = r0
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractClickableNode$handlePressInteraction$2$1$delayJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
