package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
