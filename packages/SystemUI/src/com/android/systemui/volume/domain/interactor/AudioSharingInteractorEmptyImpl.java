package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AudioSharingInteractorEmptyImpl implements AudioSharingInteractor {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isInAudioSharing = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 primaryDevice = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 secondaryDevice = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
    public final EmptyFlow volume = EmptyFlow.INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Object audioSharingVolumeBarAvailable(Context context, Continuation continuation) {
        return Boolean.FALSE;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getPrimaryDevice() {
        return this.primaryDevice;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getSecondaryDevice() {
        return this.secondaryDevice;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getVolume() {
        return this.volume;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final int getVolumeMax() {
        return 0;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow isInAudioSharing() {
        return this.isInAudioSharing;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final void setStreamVolume(int i) {
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final void handlePrimaryGroupChange() {
    }
}
