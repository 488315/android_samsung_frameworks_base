package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public interface AudioSharingInteractor {
    Object audioSharingVolumeBarAvailable(Context context, Continuation continuation);

    Flow getPrimaryDevice();

    Flow getSecondaryDevice();

    Flow getVolume();

    int getVolumeMax();

    void handlePrimaryGroupChange();

    Flow isInAudioSharing();

    void setStreamVolume(int i);
}
