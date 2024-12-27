package com.android.systemui.volume.panel.component.volume.ui.viewmodel;

import com.android.systemui.volume.panel.component.volume.domain.model.SliderType;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class AudioVolumeComponentViewModel$sliderViewModels$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AudioVolumeComponentViewModel this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.volume.panel.component.volume.ui.viewmodel.AudioVolumeComponentViewModel$sliderViewModels$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$transformLatest;
        final /* synthetic */ List<SliderType> $sliderTypes;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AudioVolumeComponentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(List<? extends SliderType> list, FlowCollector flowCollector, AudioVolumeComponentViewModel audioVolumeComponentViewModel, Continuation continuation) {
            super(2, continuation);
            this.$sliderTypes = list;
            this.$$this$transformLatest = flowCollector;
            this.this$0 = audioVolumeComponentViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$sliderTypes, this.$$this$transformLatest, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            SliderViewModel create;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                List<SliderType> list = this.$sliderTypes;
                AudioVolumeComponentViewModel audioVolumeComponentViewModel = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                for (SliderType sliderType : list) {
                    if (sliderType instanceof SliderType.Stream) {
                        int i2 = ((SliderType.Stream) sliderType).stream;
                        audioVolumeComponentViewModel.getClass();
                        create = audioVolumeComponentViewModel.streamSliderViewModelFactory.create(new AudioStreamSliderViewModel.FactoryAudioStreamWrapper(i2, null), coroutineScope);
                    } else {
                        if (!(sliderType instanceof SliderType.MediaDeviceCast)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        create = audioVolumeComponentViewModel.castVolumeSliderViewModelFactory.create(((SliderType.MediaDeviceCast) sliderType).session, coroutineScope);
                    }
                    arrayList.add(create);
                }
                FlowCollector flowCollector = this.$$this$transformLatest;
                this.label = 1;
                if (flowCollector.emit(arrayList, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioVolumeComponentViewModel$sliderViewModels$1(AudioVolumeComponentViewModel audioVolumeComponentViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = audioVolumeComponentViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AudioVolumeComponentViewModel$sliderViewModels$1 audioVolumeComponentViewModel$sliderViewModels$1 = new AudioVolumeComponentViewModel$sliderViewModels$1(this.this$0, (Continuation) obj3);
        audioVolumeComponentViewModel$sliderViewModels$1.L$0 = (FlowCollector) obj;
        audioVolumeComponentViewModel$sliderViewModels$1.L$1 = (List) obj2;
        return audioVolumeComponentViewModel$sliderViewModels$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((List) this.L$1, (FlowCollector) this.L$0, this.this$0, null);
            this.L$0 = null;
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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
