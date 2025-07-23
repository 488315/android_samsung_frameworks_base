package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSliderViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeDialogSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, VolumeDialogSliderViewModel volumeDialogSliderViewModel) {
        super(3, continuation);
        this.this$0 = volumeDialogSliderViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSliderViewModel$special$$inlined$flatMapLatest$1 volumeDialogSliderViewModel$special$$inlined$flatMapLatest$1 = new VolumeDialogSliderViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        volumeDialogSliderViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        volumeDialogSliderViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return volumeDialogSliderViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow safeFlow;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) this.L$1;
            boolean z = volumeDialogStreamModel.muteSupported && volumeDialogStreamModel.muted;
            VolumeDialogSliderViewModel volumeDialogSliderViewModel = this.this$0;
            VolumeDialogSliderType volumeDialogSliderType = volumeDialogSliderViewModel.sliderType;
            boolean z2 = volumeDialogSliderType instanceof VolumeDialogSliderType.Stream;
            VolumeDialogSliderIconProvider volumeDialogSliderIconProvider = volumeDialogSliderViewModel.volumeDialogSliderIconProvider;
            if (z2) {
                int i2 = ((VolumeDialogSliderType.Stream) volumeDialogSliderType).audioStream;
                volumeDialogSliderIconProvider.getClass();
                AudioStream.Companion.getClass();
                if (AudioStream.supportedStreamTypes.contains(Integer.valueOf(i2))) {
                    AudioStream.m989constructorimpl(i2);
                    ZenModeInteractor zenModeInteractor = volumeDialogSliderIconProvider.zenModeInteractor;
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = zenModeInteractor.zenModeByStreamPredicates.containsKey(Integer.valueOf(i2)) ? zenModeInteractor.m3091activeModesBlockingStreamtLTdkI8(i2) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
                } else {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
                }
                Flow flow = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
                Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = (i2 == 2 || i2 == 5) ? ((AudioRepositoryImpl) volumeDialogSliderIconProvider.audioVolumeInteractor.audioRepository).ringerMode : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
                safeFlow = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22, new VolumeDialogSliderIconProvider$getStreamIcon$1(volumeDialogSliderIconProvider, i2, volumeDialogStreamModel.level, volumeDialogStreamModel.levelMin, volumeDialogStreamModel.levelMax, z, volumeDialogStreamModel.routedToBluetooth, null));
            } else if (volumeDialogSliderType instanceof VolumeDialogSliderType.RemoteMediaStream) {
                volumeDialogSliderIconProvider.getClass();
                safeFlow = new SafeFlow(new VolumeDialogSliderIconProvider$getCastIcon$1(z, volumeDialogSliderIconProvider, null));
            } else {
                if (!(volumeDialogSliderType instanceof VolumeDialogSliderType.AudioSharingStream)) {
                    throw new NoWhenBranchMatchedException();
                }
                volumeDialogSliderIconProvider.getClass();
                safeFlow = new SafeFlow(new VolumeDialogSliderIconProvider$getAudioSharingIcon$1(z, volumeDialogSliderIconProvider, null));
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, safeFlow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
