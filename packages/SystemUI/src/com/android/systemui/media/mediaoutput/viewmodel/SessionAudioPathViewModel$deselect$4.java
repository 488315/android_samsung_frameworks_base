package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SessionAudioPathViewModel$deselect$4 extends SuspendLambda implements Function1 {
    final /* synthetic */ AudioDevice $device;
    Object L$0;
    int label;
    final /* synthetic */ SessionAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionAudioPathViewModel$deselect$4(SessionAudioPathViewModel sessionAudioPathViewModel, AudioDevice audioDevice, Continuation continuation) {
        super(1, continuation);
        this.this$0 = sessionAudioPathViewModel;
        this.$device = audioDevice;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new SessionAudioPathViewModel$deselect$4(this.this$0, this.$device, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((SessionAudioPathViewModel$deselect$4) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:
    
        if (r5.deselect(r1, r4) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0030, code lost:
    
        if (r5 == r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L64
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            java.lang.Object r1 = r4.L$0
            com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel r1 = (com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L33
        L20:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel r1 = r4.this$0
            com.android.systemui.media.mediaoutput.entity.AudioDevice r5 = r4.$device
            r4.L$0 = r1
            r4.label = r3
            r3 = 0
            java.lang.Object r5 = com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.access$checkForBudsTogether(r1, r5, r3, r4)
            if (r5 != r0) goto L33
            goto L63
        L33:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            r3 = 0
            if (r5 != 0) goto L3d
            goto L3e
        L3d:
            r1 = r3
        L3e:
            if (r1 != 0) goto L43
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L43:
            com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel r5 = r4.this$0
            java.util.Map r5 = r5.controllerMap
            com.android.systemui.media.mediaoutput.entity.AudioDevice r1 = r4.$device
            com.android.systemui.media.mediaoutput.controller.device.ControllerType r1 = r1.getFinalControllerType()
            java.util.LinkedHashMap r5 = (java.util.LinkedHashMap) r5
            java.lang.Object r5 = r5.get(r1)
            com.android.systemui.media.mediaoutput.controller.device.DeviceController r5 = (com.android.systemui.media.mediaoutput.controller.device.DeviceController) r5
            if (r5 == 0) goto L64
            com.android.systemui.media.mediaoutput.entity.AudioDevice r1 = r4.$device
            r4.L$0 = r3
            r4.label = r2
            java.lang.Object r4 = r5.deselect(r1, r4)
            if (r4 != r0) goto L64
        L63:
            return r0
        L64:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$deselect$4.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
