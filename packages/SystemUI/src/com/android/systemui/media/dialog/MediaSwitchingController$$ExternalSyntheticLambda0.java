package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaSwitchingController$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MediaDevice mediaDevice = (MediaDevice) obj;
        switch (this.$r8$classId) {
            case 0:
                return mediaDevice.getId();
            default:
                return MediaItem.createDeviceMediaItem(mediaDevice);
        }
    }
}
