package com.samsung.systemui.splugins.extensions;

import com.samsung.systemui.splugins.volume.VolumeStreamState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumeStreamStateExt {
    private static final int DYNAMIC_STREAM_START_INDEX = 100;
    public static final VolumeStreamStateExt INSTANCE = new VolumeStreamStateExt();

    private VolumeStreamStateExt() {
    }

    public final boolean isRemoteStream(VolumeStreamState volumeStreamState) {
        return volumeStreamState.getStreamType() >= 100;
    }
}
