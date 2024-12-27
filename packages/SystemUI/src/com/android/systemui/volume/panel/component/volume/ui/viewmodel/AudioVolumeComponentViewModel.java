package com.android.systemui.volume.panel.component.volume.ui.viewmodel;

import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel;
import com.android.systemui.volume.panel.component.volume.ui.viewmodel.SlidersExpandableViewModel;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import kotlin.collections.EmptyList;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class AudioVolumeComponentViewModel {
    public final CastVolumeSliderViewModel.Factory castVolumeSliderViewModelFactory;
    public final ReadonlyStateFlow isPlaybackActive;
    public final StateFlowImpl mutableIsExpanded = StateFlowKt.MutableStateFlow(null);
    public final ReadonlyStateFlow portraitExpandable;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow sliderViewModels;
    public final AudioStreamSliderViewModel.Factory streamSliderViewModelFactory;

    public AudioVolumeComponentViewModel(CoroutineScope coroutineScope, MediaOutputInteractor mediaOutputInteractor, MediaDeviceSessionInteractor mediaDeviceSessionInteractor, AudioStreamSliderViewModel.Factory factory, CastVolumeSliderViewModel.Factory factory2, AudioSlidersInteractor audioSlidersInteractor) {
        this.scope = coroutineScope;
        this.streamSliderViewModelFactory = factory;
        this.castVolumeSliderViewModelFactory = factory2;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.transformLatest(ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), new AudioVolumeComponentViewModel$special$$inlined$flatMapLatest$1(null, mediaDeviceSessionInteractor)), new AudioVolumeComponentViewModel$isPlaybackActive$2(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.portraitExpandable = FlowKt.stateIn(FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, null)), new AudioVolumeComponentViewModel$special$$inlined$flatMapLatest$2(null, this)), coroutineScope, startedEagerly, SlidersExpandableViewModel.Unavailable.INSTANCE);
        this.sliderViewModels = FlowKt.stateIn(FlowKt.transformLatest(audioSlidersInteractor.volumePanelSliders, new AudioVolumeComponentViewModel$sliderViewModels$1(this, null)), coroutineScope, startedEagerly, EmptyList.INSTANCE);
    }

    public final void onExpandedChanged(boolean z) {
        BuildersKt.launch$default(this.scope, null, null, new AudioVolumeComponentViewModel$onExpandedChanged$1(this, z, null), 3);
    }
}
