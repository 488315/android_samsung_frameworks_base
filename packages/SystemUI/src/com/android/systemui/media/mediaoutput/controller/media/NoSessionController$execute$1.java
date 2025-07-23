package com.android.systemui.media.mediaoutput.controller.media;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NoSessionController$execute$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $action;
    int label;
    final /* synthetic */ NoSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoSessionController$execute$1(long j, NoSessionController noSessionController, Continuation continuation) {
        super(2, continuation);
        this.$action = j;
        this.this$0 = noSessionController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NoSessionController$execute$1(this.$action, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoSessionController$execute$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x008b, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006b, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(5000, r11) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004a, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00aa, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 0
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L29
            if (r1 == r6) goto L25
            if (r1 == r5) goto L21
            if (r1 == r4) goto L1c
            if (r1 != r3) goto L14
            goto L1c
        L14:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1c:
            kotlin.ResultKt.throwOnFailure(r12)
            goto Lad
        L21:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L6e
        L25:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L4d
        L29:
            kotlin.ResultKt.throwOnFailure(r12)
            long r7 = r11.$action
            r9 = 4
            int r12 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r12 != 0) goto L8e
            com.android.systemui.media.mediaoutput.controller.media.NoSessionController r12 = r11.this$0
            kotlinx.coroutines.flow.StateFlowImpl r12 = r12._mediaActionsFlow
            com.android.systemui.media.mediaoutput.entity.MediaAction$Companion r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.Companion
            r1.getClass()
            com.android.systemui.media.mediaoutput.entity.MediaAction r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.buffering
            java.util.List r1 = java.util.Collections.singletonList(r1)
            r11.label = r6
            r12.setValue(r1)
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            if (r12 != r0) goto L4d
            goto Lac
        L4d:
            com.android.systemui.media.mediaoutput.controller.media.NoSessionController r12 = r11.this$0
            android.media.AudioManager r12 = r12.audioManager
            android.view.KeyEvent r1 = new android.view.KeyEvent
            r3 = 126(0x7e, float:1.77E-43)
            r1.<init>(r2, r3)
            r12.dispatchMediaKeyEvent(r1)
            android.view.KeyEvent r1 = new android.view.KeyEvent
            r1.<init>(r6, r3)
            r12.dispatchMediaKeyEvent(r1)
            r11.label = r5
            r5 = 5000(0x1388, double:2.4703E-320)
            java.lang.Object r12 = kotlinx.coroutines.DelayKt.delay(r5, r11)
            if (r12 != r0) goto L6e
            goto Lac
        L6e:
            com.android.systemui.media.mediaoutput.controller.media.NoSessionController r12 = r11.this$0
            kotlinx.coroutines.flow.StateFlowImpl r12 = r12._mediaActionsFlow
            com.android.systemui.media.mediaoutput.entity.MediaAction$Companion r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.Companion
            r1.getClass()
            com.android.systemui.media.mediaoutput.entity.MediaAction r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.play
            r3 = 14
            r5 = 0
            com.android.systemui.media.mediaoutput.entity.MediaAction r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.copy$default(r1, r5, r2, r3)
            java.util.List r1 = java.util.Collections.singletonList(r1)
            r11.label = r4
            r12.setValue(r1)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            if (r11 != r0) goto Lad
            goto Lac
        L8e:
            r1 = -4
            int r12 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r12 != 0) goto Lad
            com.android.systemui.media.mediaoutput.controller.media.NoSessionController r12 = r11.this$0
            kotlinx.coroutines.flow.StateFlowImpl r12 = r12._mediaActionsFlow
            com.android.systemui.media.mediaoutput.entity.MediaAction$Companion r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.Companion
            r1.getClass()
            com.android.systemui.media.mediaoutput.entity.MediaAction r1 = com.android.systemui.media.mediaoutput.entity.MediaAction.play
            java.util.List r1 = java.util.Collections.singletonList(r1)
            r11.label = r3
            r12.setValue(r1)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            if (r11 != r0) goto Lad
        Lac:
            return r0
        Lad:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$execute$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
