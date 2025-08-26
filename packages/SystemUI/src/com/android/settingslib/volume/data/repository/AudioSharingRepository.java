package com.android.settingslib.volume.data.repository;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public interface AudioSharingRepository {
    StateFlow getInAudioSharing();

    StateFlow getPrimaryDevice();

    StateFlow getPrimaryGroupId();

    StateFlow getSecondaryDevice();

    StateFlow getSecondaryGroupId();

    StateFlow getVolumeMap();

    Object setSecondaryVolume(int i, Continuation continuation);
}
