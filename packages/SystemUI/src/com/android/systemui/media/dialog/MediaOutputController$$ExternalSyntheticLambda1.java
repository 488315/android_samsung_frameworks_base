package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
