package com.android.systemui.bixby2.controller.volume;

/* loaded from: classes.dex */
public final class BixbyVolumeController extends VolumeType {
    public static final int $stable = 0;
    private final String streamTypeToString = "Bixby";

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStreamType() {
        return 11;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }
}
