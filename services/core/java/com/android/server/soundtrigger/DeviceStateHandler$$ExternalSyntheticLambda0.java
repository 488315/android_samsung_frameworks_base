package com.android.server.soundtrigger;


public final /* synthetic */ class DeviceStateHandler$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */
    SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1 f$0;
    public final /* synthetic */ DeviceStateHandler.SoundTriggerDeviceState f$1;

    public /* synthetic */ DeviceStateHandler$$ExternalSyntheticLambda0(
            SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                    soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1,
            DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState,
            int i) {
        this.$r8$classId = i;
        this.f$0 = soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1;
        this.f$1 = soundTriggerDeviceState;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onSoundTriggerDeviceStateUpdate(this.f$1);
                break;
            default:
                this.f$0.onSoundTriggerDeviceStateUpdate(this.f$1);
                break;
        }
    }
}
