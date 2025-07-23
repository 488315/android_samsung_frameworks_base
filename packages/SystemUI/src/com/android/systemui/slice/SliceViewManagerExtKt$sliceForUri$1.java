package com.android.systemui.slice;

import android.net.Uri;
import androidx.slice.SliceViewManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SliceViewManagerExtKt$sliceForUri$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $sliceUri;
    final /* synthetic */ SliceViewManager $this_sliceForUri;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliceViewManagerExtKt$sliceForUri$1(SliceViewManager sliceViewManager, Uri uri, Continuation continuation) {
        super(2, continuation);
        this.$this_sliceForUri = sliceViewManager;
        this.$sliceUri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SliceViewManagerExtKt$sliceForUri$1 sliceViewManagerExtKt$sliceForUri$1 = new SliceViewManagerExtKt$sliceForUri$1(this.$this_sliceForUri, this.$sliceUri, continuation);
        sliceViewManagerExtKt$sliceForUri$1.L$0 = obj;
        return sliceViewManagerExtKt$sliceForUri$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SliceViewManagerExtKt$sliceForUri$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0066, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r5, r6) == r0) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
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
            if (r1 == 0) goto L24
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L69
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            java.lang.Object r1 = r6.L$1
            androidx.slice.SliceViewManager$SliceCallback r1 = (androidx.slice.SliceViewManager.SliceCallback) r1
            java.lang.Object r3 = r6.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4b
        L24:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
            com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$callback$1 r1 = new com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$callback$1
            r1.<init>()
            androidx.slice.SliceViewManager r4 = r6.$this_sliceForUri
            android.net.Uri r5 = r6.$sliceUri
            androidx.slice.Slice r4 = r4.bindSlice(r5)
            r6.L$0 = r7
            r6.L$1 = r1
            r6.label = r3
            r3 = r7
            kotlinx.coroutines.channels.ChannelCoroutine r3 = (kotlinx.coroutines.channels.ChannelCoroutine) r3
            kotlinx.coroutines.channels.Channel r3 = r3._channel
            java.lang.Object r3 = r3.send(r4, r6)
            if (r3 != r0) goto L4a
            goto L68
        L4a:
            r3 = r7
        L4b:
            androidx.slice.SliceViewManager r7 = r6.$this_sliceForUri
            android.net.Uri r4 = r6.$sliceUri
            r7.registerSliceCallback(r4, r1)
            androidx.slice.SliceViewManager r7 = r6.$this_sliceForUri
            android.net.Uri r4 = r6.$sliceUri
            com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$$ExternalSyntheticLambda0 r5 = new com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1$$ExternalSyntheticLambda0
            r5.<init>()
            r7 = 0
            r6.L$0 = r7
            r6.L$1 = r7
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r5, r6)
            if (r6 != r0) goto L69
        L68:
            return r0
        L69:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.slice.SliceViewManagerExtKt$sliceForUri$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
