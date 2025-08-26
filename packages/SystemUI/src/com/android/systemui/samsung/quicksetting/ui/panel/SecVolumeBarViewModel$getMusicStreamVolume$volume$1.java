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
final class SecVolumeBarViewModel$getMusicStreamVolume$volume$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SecVolumeBarViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecVolumeBarViewModel$getMusicStreamVolume$volume$1(SecVolumeBarViewModel secVolumeBarViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secVolumeBarViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecVolumeBarViewModel$getMusicStreamVolume$volume$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecVolumeBarViewModel$getMusicStreamVolume$volume$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
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
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        AudioStreamModel audioStreamModel = (AudioStreamModel) obj;
        int i2 = audioStreamModel.volume;
        int i3 = audioStreamModel.minVolume;
        return new Float((i2 - i3) / (audioStreamModel.maxVolume - i3));
    }
}
