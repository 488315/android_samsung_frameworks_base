package com.android.systemui.samsung.quicksetting.ui.panel;

import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0052, code lost:
    
        if (r1.m988setVolumeZdW0WiI(3, r6, r5) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AudioVolumeInteractor audioVolumeInteractor = this.this$0.volumeInteractor;
            AudioStream.m991constructorimpl(3);
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8 = audioVolumeInteractor.m986getAudioStreamtLTdkI8(3);
            this.label = 1;
            obj = FlowKt.first(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8, this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        AudioStreamModel audioStreamModel = (AudioStreamModel) obj;
        float f = this.$volume;
        int i2 = audioStreamModel.maxVolume;
        int iRound = Math.round((f * (i2 - r6)) + audioStreamModel.minVolume);
        AudioVolumeInteractor audioVolumeInteractor2 = this.this$0.volumeInteractor;
        AudioStream.m991constructorimpl(3);
        this.label = 2;
    }
}
