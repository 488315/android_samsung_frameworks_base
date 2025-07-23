package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuiltInDeviceController$adjustVolume$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BuiltInDeviceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuiltInDeviceController$adjustVolume$2$1(BuiltInDeviceController builtInDeviceController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = builtInDeviceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BuiltInDeviceController$adjustVolume$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuiltInDeviceController$adjustVolume$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006b, code lost:
    
        if (r9.updateDevices$1(r1, false, r8) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006d, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0055, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(200, r8) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004a, code lost:
    
        if (r9.updateDevices$1(r1, true, r8) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0035, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(300, r8) == r0) goto L25;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 4
            r3 = 3
            r4 = 1
            r5 = 2
            if (r1 == 0) goto L2a
            if (r1 == r4) goto L26
            if (r1 == r5) goto L22
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            kotlin.ResultKt.throwOnFailure(r9)
            goto L6e
        L16:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1e:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L58
        L22:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L4d
        L26:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L38
        L2a:
            kotlin.ResultKt.throwOnFailure(r9)
            r8.label = r4
            r6 = 300(0x12c, double:1.48E-321)
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r6, r8)
            if (r9 != r0) goto L38
            goto L6d
        L38:
            com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController r9 = r8.this$0
            android.media.AudioManager r1 = r9.audioManager
            android.media.AudioDeviceInfo[] r1 = r1.getDevices(r5)
            java.util.List r1 = kotlin.collections.ArraysKt___ArraysKt.toList(r1)
            r8.label = r5
            java.lang.Object r9 = r9.updateDevices$1(r1, r4, r8)
            if (r9 != r0) goto L4d
            goto L6d
        L4d:
            r8.label = r3
            r3 = 200(0xc8, double:9.9E-322)
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r3, r8)
            if (r9 != r0) goto L58
            goto L6d
        L58:
            com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController r9 = r8.this$0
            android.media.AudioManager r1 = r9.audioManager
            android.media.AudioDeviceInfo[] r1 = r1.getDevices(r5)
            java.util.List r1 = kotlin.collections.ArraysKt___ArraysKt.toList(r1)
            r8.label = r2
            r2 = 0
            java.lang.Object r9 = r9.updateDevices$1(r1, r2, r8)
            if (r9 != r0) goto L6e
        L6d:
            return r0
        L6e:
            com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController r8 = r8.this$0
            r9 = 0
            r8.updateJob = r9
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$adjustVolume$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
