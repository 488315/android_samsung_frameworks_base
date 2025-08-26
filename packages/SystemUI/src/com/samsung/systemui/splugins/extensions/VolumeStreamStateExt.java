package com.samsung.systemui.splugins.extensions;

import com.samsung.systemui.splugins.volume.VolumeStreamState;

/* loaded from: classes4.dex */
public final class VolumeStreamStateExt {
    public static final int $stable = 0;
    private static final int DYNAMIC_STREAM_START_INDEX = 100;
    public static final VolumeStreamStateExt INSTANCE = new VolumeStreamStateExt();

    private VolumeStreamStateExt() {
    }

    public final boolean isRemoteStream(VolumeStreamState volumeStreamState) {
        return volumeStreamState.getStreamType() >= 100;
    }
}
