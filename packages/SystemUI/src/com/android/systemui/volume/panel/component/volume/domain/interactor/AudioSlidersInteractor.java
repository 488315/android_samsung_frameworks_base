package com.android.systemui.volume.panel.component.volume.domain.interactor;

import android.media.AudioSystem;
import com.android.settingslib.volume.data.repository.AudioSystemRepository;
import com.android.settingslib.volume.data.repository.AudioSystemRepositoryImpl;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.component.volume.domain.model.SliderType;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class AudioSlidersInteractor {
    public final AudioSystemRepository audioSystemRepository;
    public final ReadonlyStateFlow volumePanelSliders;

    public AudioSlidersInteractor(CoroutineScope coroutineScope, MediaOutputInteractor mediaOutputInteractor, AudioModeInteractor audioModeInteractor, AudioSystemRepository audioSystemRepository, AudioSharingInteractor audioSharingInteractor) {
        this.audioSystemRepository = audioSystemRepository;
        SafeFlow safeFlowCombineTransform = FlowKt.combineTransform(mediaOutputInteractor.activeMediaDeviceSessions, ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), audioModeInteractor.isOngoingCall, audioSharingInteractor.getVolume(), new AudioSlidersInteractor$volumePanelSliders$1(this, null));
        SharingStarted.Companion.getClass();
        this.volumePanelSliders = FlowKt.stateIn(safeFlowCombineTransform, coroutineScope, SharingStarted.Companion.Eagerly, EmptyList.INSTANCE);
    }

    public static final void access$addStream(AudioSlidersInteractor audioSlidersInteractor, ListBuilder listBuilder, int i) {
        if (!AudioSystem.isSingleVolume(((AudioSystemRepositoryImpl) audioSlidersInteractor.audioSystemRepository).context) || i == 3) {
            AudioStream.m991constructorimpl(i);
            listBuilder.add(new SliderType.Stream(i, null));
        }
    }
}
