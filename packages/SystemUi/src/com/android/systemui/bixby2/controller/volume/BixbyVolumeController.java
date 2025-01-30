package com.android.systemui.bixby2.controller.volume;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BixbyVolumeController extends VolumeType {
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
