package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.platform.ViewConfiguration;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
    final /* synthetic */ Function0 $onClick;
    final /* synthetic */ ViewConfiguration $viewConfiguration;
    long J$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1(ViewConfiguration viewConfiguration, Function0 function0, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$viewConfiguration = viewConfiguration;
        this.$onClick = function0;
        this.$isPressed$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1(this.$viewConfiguration, this.$onClick, this.$isPressed$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004d, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r3, r7) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x004f, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002d, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r4, r7) == r0) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0040  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x004d -> B:6:0x0050). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1e
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            long r3 = r7.J$0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L50
        L12:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1a:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L30
        L1e:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.compose.ui.platform.ViewConfiguration r8 = r7.$viewConfiguration
            long r4 = r8.getLongPressTimeoutMillis()
            r7.label = r3
            java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r4, r7)
            if (r8 != r0) goto L30
            goto L4f
        L30:
            r3 = 200(0xc8, double:9.9E-322)
        L32:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r8 = r7.$isPressed$delegate
            java.lang.Object r8 = r8.getValue()
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L5d
            kotlin.jvm.functions.Function0 r8 = r7.$onClick
            r8.invoke()
            r7.J$0 = r3
            r7.label = r2
            java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r3, r7)
            if (r8 != r0) goto L50
        L4f:
            return r0
        L50:
            float r8 = (float) r3
            r1 = 1063675494(0x3f666666, float:0.9)
            float r8 = r8 * r1
            long r3 = (long) r8
            r5 = 100
            long r3 = java.lang.Math.max(r3, r5)
            goto L32
        L5d:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
