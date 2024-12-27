package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.function.Function;

public final /* synthetic */ class MediaOutputController$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MediaDevice mediaDevice = (MediaDevice) obj;
        switch (this.$r8$classId) {
            case 0:
                return MediaItem.createDeviceMediaItem(mediaDevice);
            default:
                return mediaDevice.getId();
        }
    }
}
