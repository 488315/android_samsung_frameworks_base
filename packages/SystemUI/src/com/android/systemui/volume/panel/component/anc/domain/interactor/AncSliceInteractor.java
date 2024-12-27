package com.android.systemui.volume.panel.component.anc.domain.interactor;

import com.android.systemui.volume.domain.interactor.AudioOutputInteractor;
import com.android.systemui.volume.panel.component.anc.data.repository.AncSliceRepository;
import com.android.systemui.volume.panel.component.anc.domain.model.AncSlices;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AncSliceInteractor {
    public final AncSliceRepository ancSliceRepository;
    public final ReadonlyStateFlow ancSlices;
    public final AudioOutputInteractor audioOutputInteractor;
    public final StateFlowImpl buttonSliceWidth;
    public final StateFlowImpl popupSliceWidth;

    public AncSliceInteractor(AudioOutputInteractor audioOutputInteractor, AncSliceRepository ancSliceRepository, CoroutineScope coroutineScope) {
        this.audioOutputInteractor = audioOutputInteractor;
        this.ancSliceRepository = ancSliceRepository;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(1);
        this.buttonSliceWidth = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(1);
        this.popupSliceWidth = MutableStateFlow2;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.transformLatest(MutableStateFlow, new AncSliceInteractor$special$$inlined$flatMapLatest$1(null, this)), FlowKt.transformLatest(MutableStateFlow2, new AncSliceInteractor$special$$inlined$flatMapLatest$2(null, this)), new AncSliceInteractor$ancSlices$3(null));
        SharingStarted.Companion.getClass();
        this.ancSlices = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, AncSlices.Unavailable.INSTANCE);
    }
}
