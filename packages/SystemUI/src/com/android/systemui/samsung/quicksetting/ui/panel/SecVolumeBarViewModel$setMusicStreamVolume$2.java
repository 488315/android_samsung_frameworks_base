package com.android.systemui.samsung.quicksetting.ui.panel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SecVolumeBarViewModel$setMusicStreamVolume$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $volume;
    int label;
    final /* synthetic */ SecVolumeBarViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecVolumeBarViewModel$setMusicStreamVolume$2(SecVolumeBarViewModel secVolumeBarViewModel, float f, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secVolumeBarViewModel;
        this.$volume = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecVolumeBarViewModel$setMusicStreamVolume$2(this.this$0, this.$volume, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecVolumeBarViewModel$setMusicStreamVolume$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0052, code lost:
    
        if (r1.m986setVolumeZdW0WiI(3, r6, r5) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0054, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
    
        if (r6 == r0) goto L15;
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
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1d
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r6)
            goto L55
        L11:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L19:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L34
        L1d:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel r6 = r5.this$0
            com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor r6 = r6.volumeInteractor
            com.android.settingslib.volume.shared.model.AudioStream.m989constructorimpl(r2)
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 r6 = r6.m984getAudioStreamtLTdkI8(r2)
            r5.label = r4
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r6, r5)
            if (r6 != r0) goto L34
            goto L54
        L34:
            com.android.settingslib.volume.shared.model.AudioStreamModel r6 = (com.android.settingslib.volume.shared.model.AudioStreamModel) r6
            float r1 = r5.$volume
            int r4 = r6.maxVolume
            int r6 = r6.minVolume
            int r4 = r4 - r6
            float r4 = (float) r4
            float r1 = r1 * r4
            float r6 = (float) r6
            float r1 = r1 + r6
            int r6 = java.lang.Math.round(r1)
            com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel r1 = r5.this$0
            com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor r1 = r1.volumeInteractor
            com.android.settingslib.volume.shared.model.AudioStream.m989constructorimpl(r2)
            r5.label = r3
            java.lang.Object r5 = r1.m986setVolumeZdW0WiI(r2, r6, r5)
            if (r5 != r0) goto L55
        L54:
            return r0
        L55:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$setMusicStreamVolume$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
