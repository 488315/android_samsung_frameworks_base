package com.samsung.systemui.splugins.extensions;

import com.samsung.systemui.splugins.volume.VolumeStreamState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
