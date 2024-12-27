package com.android.server.audio;

import com.android.server.audio.AudioService;
import com.samsung.android.server.audio.DeviceAliasManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioService$VolumeStreamState$$ExternalSyntheticLambda1 implements DeviceAliasManager.DeviceAliasRunner {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioService.VolumeStreamState f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AudioService$VolumeStreamState$$ExternalSyntheticLambda1(AudioService.VolumeStreamState volumeStreamState, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = volumeStreamState;
        this.f$1 = i;
    }

    @Override // com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner
    public final void run(int i) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setStreamVolumeIndex(this.f$1, i);
                break;
            default:
                this.f$0.mIndexMap.put(i, this.f$1);
                break;
        }
    }
}
