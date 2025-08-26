package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.function.Function;

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
