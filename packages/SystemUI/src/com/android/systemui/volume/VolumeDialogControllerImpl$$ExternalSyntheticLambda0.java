package com.android.systemui.volume;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogControllerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda0(VolumeDialogControllerImpl volumeDialogControllerImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = volumeDialogControllerImpl;
        this.f$1 = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = this.f$1;
        VolumeDialogControllerImpl volumeDialogControllerImpl = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                String str = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                volumeDialogControllerImpl.mVolumeLogger.onAudioSharingAvailabilityRequestedError("updateActiveStreamW", ((Throwable) obj).getMessage());
                volumeDialogControllerImpl.forceVolumeControlStreamW(i, false);
                break;
            case 1:
                String str2 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                volumeDialogControllerImpl.forceVolumeControlStreamW(i, ((Boolean) obj).booleanValue());
                break;
            default:
                String str3 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl.getClass();
                if (((Boolean) obj).booleanValue()) {
                    volumeDialogControllerImpl.mAudioSharingInteractor.setStreamVolume(i);
                    break;
                }
                break;
        }
        return null;
    }
}
