package com.android.systemui.bixby2.controller.volume;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
