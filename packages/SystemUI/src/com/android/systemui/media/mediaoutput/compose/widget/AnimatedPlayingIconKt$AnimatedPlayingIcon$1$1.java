package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Integer> $currentIndex$delegate;
    final /* synthetic */ List<ImageVector> $icons;
    final /* synthetic */ long $intervalMillis;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1(long j, List<ImageVector> list, MutableState<Integer> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$intervalMillis = j;
        this.$icons = list;
        this.$currentIndex$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1(this.$intervalMillis, this.$icons, this.$currentIndex$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0022 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0020 -> B:12:0x0023). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r6)
            goto L23
        Ld:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L15:
            kotlin.ResultKt.throwOnFailure(r6)
        L18:
            long r3 = r5.$intervalMillis
            r5.label = r2
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r6 != r0) goto L23
            return r0
        L23:
            androidx.compose.runtime.MutableState<java.lang.Integer> r6 = r5.$currentIndex$delegate
            java.lang.Object r1 = r6.getValue()
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            int r1 = r1 + r2
            java.util.List<androidx.compose.ui.graphics.vector.ImageVector> r3 = r5.$icons
            int r3 = r3.size()
            int r1 = r1 % r3
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r6.setValue(r1)
            goto L18
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIconKt$AnimatedPlayingIcon$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
