package com.android.systemui.volume.panel.component.volume.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioSharingStreamSliderViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel;
import com.android.systemui.volume.panel.component.volume.ui.viewmodel.SlidersExpandableViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AudioVolumeComponentViewModel {
    public final AudioSharingStreamSliderViewModel.Factory audioSharingStreamSliderViewModelFactory;
    public final CastVolumeSliderViewModel.Factory castVolumeSliderViewModelFactory;
    public final ReadonlyStateFlow isActive;
    public final StateFlowImpl mutableIsExpanded = StateFlowKt.MutableStateFlow(null);
    public final ReadonlyStateFlow portraitExpandable;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow sliderViewModels;
    public final AudioStreamSliderViewModel.Factory streamSliderViewModelFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.panel.component.volume.ui.viewmodel.AudioVolumeComponentViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = AudioVolumeComponentViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AudioVolumeComponentViewModel.this.mutableIsExpanded.updateState(null, Boolean.valueOf(!this.Z$0));
            return Unit.INSTANCE;
        }
    }

    public AudioVolumeComponentViewModel(CoroutineScope coroutineScope, MediaOutputInteractor mediaOutputInteractor, MediaDeviceSessionInteractor mediaDeviceSessionInteractor, AudioStreamSliderViewModel.Factory factory, CastVolumeSliderViewModel.Factory factory2, AudioSharingStreamSliderViewModel.Factory factory3, AudioModeInteractor audioModeInteractor, AudioSlidersInteractor audioSlidersInteractor) {
        this.scope = coroutineScope;
        this.streamSliderViewModelFactory = factory;
        this.castVolumeSliderViewModelFactory = factory2;
        this.audioSharingStreamSliderViewModelFactory = factory3;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(audioModeInteractor.isOngoingCall, FlowKt.transformLatest(com.android.systemui.volume.panel.shared.model.ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), new AudioVolumeComponentViewModel$special$$inlined$flatMapLatest$1(null, mediaDeviceSessionInteractor)), new AudioVolumeComponentViewModel$isActive$2(null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, null);
        this.isActive = stateIn;
        this.portraitExpandable = FlowKt.stateIn(FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateIn), new AudioVolumeComponentViewModel$special$$inlined$flatMapLatest$2(null, this)), coroutineScope, startedEagerly, SlidersExpandableViewModel.Unavailable.INSTANCE);
        this.sliderViewModels = FlowKt.stateIn(FlowKt.transformLatest(audioSlidersInteractor.volumePanelSliders, new AudioVolumeComponentViewModel$sliderViewModels$1(this, null)), coroutineScope, startedEagerly, EmptyList.INSTANCE);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateIn), new AnonymousClass1(null)), coroutineScope);
    }

    public final void onExpandedChanged(boolean z) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AudioVolumeComponentViewModel$onExpandedChanged$1(this, z, null), 7);
    }
}
