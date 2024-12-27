package com.android.server.media;

import android.media.MediaRoute2Info;

import java.util.function.Function;

public final /* synthetic */ class AudioManagerRouteController$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new AudioManagerRouteController.MediaRoute2InfoHolder(
                        (MediaRoute2Info) obj, 0, true);
            default:
                return ((AudioManagerRouteController.MediaRoute2InfoHolder) obj).mMediaRoute2Info;
        }
    }
}
