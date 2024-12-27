package com.android.server.audio;

import android.media.AudioDeviceInfo;

import java.util.function.ToIntFunction;

public final /* synthetic */ class AudioService$$ExternalSyntheticLambda1 implements ToIntFunction {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((AudioDeviceInfo) obj).getId();
            case 1:
                return Integer.parseInt((String) obj);
            default:
                return ((Integer) obj).intValue();
        }
    }
}
