package com.android.systemui.volume;

import android.content.Context;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ AudioSharingInteractor f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return this.f$0.audioSharingVolumeBarAvailable((Context) obj, (Continuation) obj2);
    }
}
