package com.android.systemui.media.mediaoutput.viewmodel;

import android.media.session.MediaSessionManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaSessionViewModel$Companion$activeMediaChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaSessionManager $this_activeMediaChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionViewModel$Companion$activeMediaChanges$1(MediaSessionManager mediaSessionManager, Continuation continuation) {
        super(2, continuation);
        this.$this_activeMediaChanges = mediaSessionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionViewModel$Companion$activeMediaChanges$1 mediaSessionViewModel$Companion$activeMediaChanges$1 = new MediaSessionViewModel$Companion$activeMediaChanges$1(this.$this_activeMediaChanges, continuation);
        mediaSessionViewModel$Companion$activeMediaChanges$1.L$0 = obj;
        return mediaSessionViewModel$Companion$activeMediaChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionViewModel$Companion$activeMediaChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0054, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r5, r6) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0056, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0037, code lost:
    
        if (r7 == r0) goto L15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v4, types: [android.media.session.MediaSessionManager$OnActiveSessionsChangedListener, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$listener$1] */
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
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L57
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L3a
        L20:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            r1 = r7
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.collections.EmptyList r7 = kotlin.collections.EmptyList.INSTANCE
            r6.L$0 = r1
            r6.label = r3
            r3 = r1
            kotlinx.coroutines.channels.ChannelCoroutine r3 = (kotlinx.coroutines.channels.ChannelCoroutine) r3
            kotlinx.coroutines.channels.Channel r3 = r3._channel
            java.lang.Object r7 = r3.send(r7, r6)
            if (r7 != r0) goto L3a
            goto L56
        L3a:
            com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$listener$1 r7 = new com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$listener$1
            r7.<init>()
            android.media.session.MediaSessionManager r3 = r6.$this_activeMediaChanges
            r4 = 0
            r3.addOnActiveSessionsChangedListener(r7, r4)
            android.media.session.MediaSessionManager r3 = r6.$this_activeMediaChanges
            com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$$ExternalSyntheticLambda0 r5 = new com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$$ExternalSyntheticLambda0
            r5.<init>()
            r6.L$0 = r4
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r5, r6)
            if (r6 != r0) goto L57
        L56:
            return r0
        L57:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
