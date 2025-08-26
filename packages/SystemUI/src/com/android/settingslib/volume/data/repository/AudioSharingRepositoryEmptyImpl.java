package com.android.settingslib.volume.data.repository;

import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class AudioSharingRepositoryEmptyImpl implements AudioSharingRepository {
    public final StateFlowImpl inAudioSharing = StateFlowKt.MutableStateFlow(Boolean.FALSE);
    public final StateFlowImpl primaryGroupId = StateFlowKt.MutableStateFlow(-1);
    public final StateFlowImpl secondaryGroupId = StateFlowKt.MutableStateFlow(-1);
    public final StateFlowImpl volumeMap = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final StateFlow getInAudioSharing() {
        return this.inAudioSharing;
    }

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final StateFlow getPrimaryDevice() {
        return StateFlowKt.MutableStateFlow(null);
    }

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final StateFlow getPrimaryGroupId() {
        return this.primaryGroupId;
    }

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final StateFlow getSecondaryDevice() {
        return StateFlowKt.MutableStateFlow(null);
    }

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final StateFlow getSecondaryGroupId() {
        return this.secondaryGroupId;
    }

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final StateFlow getVolumeMap() {
        return this.volumeMap;
    }

    @Override // com.android.settingslib.volume.data.repository.AudioSharingRepository
    public final Object setSecondaryVolume(int i, Continuation continuation) {
        return Unit.INSTANCE;
    }
}
