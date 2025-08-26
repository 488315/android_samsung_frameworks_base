package com.android.systemui.volume;

import android.util.Slog;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogControllerImpl f$0;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda3(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogControllerImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        VolumeDialogControllerImpl volumeDialogControllerImpl = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                String str = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                volumeDialogControllerImpl.mVolumeLogger.onAudioSharingAvailabilityRequestedError("onSetStreamVolumeW", ((Throwable) obj).getMessage());
                break;
            case 1:
                String str2 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                if (((Boolean) obj).booleanValue()) {
                    Slog.d(VolumeDialogControllerImpl.TAG, "Start collect volume changes in audio sharing");
                    AudioSharingInteractor audioSharingInteractor = volumeDialogControllerImpl.mAudioSharingInteractor;
                    Flow volume = audioSharingInteractor.getVolume();
                    VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda9 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl, 7);
                    JavaAdapter javaAdapter = volumeDialogControllerImpl.mJavaAdapter;
                    javaAdapter.alwaysCollectFlow(volume, volumeDialogControllerImpl$$ExternalSyntheticLambda9);
                    javaAdapter.alwaysCollectFlow(audioSharingInteractor.isInAudioSharing(), new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl, 8));
                    break;
                }
                break;
            default:
                String str3 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                volumeDialogControllerImpl.mVolumeLogger.onAudioSharingAvailabilityRequestedError("register()", ((Throwable) obj).getMessage());
                break;
        }
        return null;
    }
}
