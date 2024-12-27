package com.android.systemui.volume.panel.component.volume.domain.interactor;

import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.component.volume.domain.model.SliderType;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AudioSlidersInteractor {
    public final ReadonlyStateFlow volumePanelSliders;

    public AudioSlidersInteractor(CoroutineScope coroutineScope, MediaOutputInteractor mediaOutputInteractor, AudioRepository audioRepository) {
        SafeFlow safeFlow = new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$3(new Flow[]{mediaOutputInteractor.activeMediaDeviceSessions, ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), ((AudioRepositoryImpl) audioRepository).getCommunicationDevice()}, null, new AudioSlidersInteractor$volumePanelSliders$1(this, null)));
        SharingStarted.Companion.getClass();
        this.volumePanelSliders = FlowKt.stateIn(safeFlow, coroutineScope, SharingStarted.Companion.Eagerly, EmptyList.INSTANCE);
    }

    public static final void access$addStream(AudioSlidersInteractor audioSlidersInteractor, ListBuilder listBuilder, int i) {
        audioSlidersInteractor.getClass();
        AudioStream.m871constructorimpl(i);
        listBuilder.add(new SliderType.Stream(i, null));
    }
}
