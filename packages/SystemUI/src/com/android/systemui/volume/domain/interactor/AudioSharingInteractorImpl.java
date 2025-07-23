package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import com.android.settingslib.volume.data.repository.AudioSharingRepository;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AudioSharingInteractorImpl implements AudioSharingInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioSharingRepository audioSharingRepository;
    public final AudioVolumeInteractor audioVolumeInteractor;
    public final CoroutineContext backgroundCoroutineContext;
    public final CoroutineScope coroutineScope;
    public final StateFlow isInAudioSharing;
    public final Flow volume;
    public final int volumeMax = 255;

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

    public AudioSharingInteractorImpl(CoroutineScope coroutineScope, CoroutineContext coroutineContext, AudioVolumeInteractor audioVolumeInteractor, AudioSharingRepository audioSharingRepository) {
        this.coroutineScope = coroutineScope;
        this.backgroundCoroutineContext = coroutineContext;
        this.audioVolumeInteractor = audioVolumeInteractor;
        this.audioSharingRepository = audioSharingRepository;
        this.isInAudioSharing = audioSharingRepository.getInAudioSharing();
        this.volume = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(audioSharingRepository.getSecondaryGroupId(), audioSharingRepository.getVolumeMap(), new AudioSharingInteractorImpl$volume$1(null)));
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Object audioSharingVolumeBarAvailable(Context context, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundCoroutineContext, new AudioSharingInteractorImpl$audioSharingVolumeBarAvailable$2(context, null), continuation);
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getPrimaryDevice() {
        return this.audioSharingRepository.getPrimaryDevice();
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getSecondaryDevice() {
        return this.audioSharingRepository.getSecondaryDevice();
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getVolume() {
        return this.volume;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final int getVolumeMax() {
        return this.volumeMax;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final void handlePrimaryGroupChange() {
        BuildersKt.launch$default(this.coroutineScope, null, null, new AudioSharingInteractorImpl$handlePrimaryGroupChange$1(this, null), 3);
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow isInAudioSharing() {
        return this.isInAudioSharing;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final void setStreamVolume(int i) {
        BuildersKt.launch$default(this.coroutineScope, null, null, new AudioSharingInteractorImpl$setStreamVolume$1(this, i, null), 3);
    }
}
