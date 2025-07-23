package androidx.compose.foundation;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CombinedClickableNode$onClickKeyUpEvent$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $keyCode;
    long J$0;
    long J$1;
    int label;
    final /* synthetic */ CombinedClickableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CombinedClickableNode$onClickKeyUpEvent$2(CombinedClickableNode combinedClickableNode, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = combinedClickableNode;
        this.$keyCode = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CombinedClickableNode$onClickKeyUpEvent$2(this.this$0, this.$keyCode, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CombinedClickableNode$onClickKeyUpEvent$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x005b, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r4 - r6, r10) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005d, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(40, r10) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L5e
        L10:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L18:
            long r4 = r10.J$1
            long r6 = r10.J$0
            kotlin.ResultKt.throwOnFailure(r11)
            goto L43
        L20:
            kotlin.ResultKt.throwOnFailure(r11)
            androidx.compose.foundation.CombinedClickableNode r11 = r10.this$0
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = androidx.compose.ui.platform.CompositionLocalsKt.LocalViewConfiguration
            java.lang.Object r11 = androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt.currentValueOf(r11, r1)
            androidx.compose.ui.platform.ViewConfiguration r11 = (androidx.compose.ui.platform.ViewConfiguration) r11
            r11.getClass()
            long r4 = r11.getDoubleTapTimeoutMillis()
            r6 = 40
            r10.J$0 = r6
            r10.J$1 = r4
            r10.label = r3
            java.lang.Object r11 = kotlinx.coroutines.DelayKt.delay(r6, r10)
            if (r11 != r0) goto L43
            goto L5d
        L43:
            androidx.compose.foundation.CombinedClickableNode r11 = r10.this$0
            androidx.collection.MutableLongObjectMap r11 = r11.doubleKeyClickStates
            long r8 = r10.$keyCode
            java.lang.Object r11 = r11.get(r8)
            androidx.compose.foundation.CombinedClickableNode$DoubleKeyClickState r11 = (androidx.compose.foundation.CombinedClickableNode.DoubleKeyClickState) r11
            if (r11 != 0) goto L52
            goto L54
        L52:
            r11.doubleTapMinTimeMillisElapsed = r3
        L54:
            long r4 = r4 - r6
            r10.label = r2
            java.lang.Object r11 = kotlinx.coroutines.DelayKt.delay(r4, r10)
            if (r11 != r0) goto L5e
        L5d:
            return r0
        L5e:
            androidx.compose.foundation.CombinedClickableNode r10 = r10.this$0
            kotlin.jvm.functions.Function0 r10 = r10.onClick
            r10.invoke()
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.CombinedClickableNode$onClickKeyUpEvent$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
