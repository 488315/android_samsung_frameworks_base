package com.android.server.audio;

import android.media.AudioSystem;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioDeviceInventory$$ExternalSyntheticLambda2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioDeviceInventory f$0;

    public /* synthetic */ AudioDeviceInventory$$ExternalSyntheticLambda2(
            AudioDeviceInventory audioDeviceInventory, int i) {
        this.$r8$classId = i;
        this.f$0 = audioDeviceInventory;
    }

    public final int deviceRoleAction(int i, int i2, List list) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.setDevicesRoleForStrategy(i, i2, list);
            case 1:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.setDevicesRoleForStrategy(i, i2, list);
            case 2:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
            case 3:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.removeDevicesRoleForStrategy(i, i2, list);
            case 4:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
            case 5:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
            case 6:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.clearDevicesRoleForStrategy(i, i2);
            case 7:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.removeDevicesRoleForStrategy(i, i2, list);
            case 8:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
            default:
                this.f$0.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.clearDevicesRoleForCapturePreset(i, i2);
        }
    }
}
