package com.android.server.soundtrigger;


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
