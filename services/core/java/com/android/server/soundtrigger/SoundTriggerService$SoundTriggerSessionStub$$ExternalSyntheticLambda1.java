package com.android.server.soundtrigger;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onSoundTriggerDeviceStateUpdate(
            DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
        switch (this.$r8$classId) {
            case 0:
                ((SoundTriggerService.SoundTriggerSessionStub) this.f$0)
                        .mSoundTriggerHelper.onDeviceStateChanged(soundTriggerDeviceState);
                break;
            default:
                ((SoundTriggerService.LocalSoundTriggerService.SessionImpl) this.f$0)
                        .mSoundTriggerHelper.onDeviceStateChanged(soundTriggerDeviceState);
                break;
        }
    }
}
