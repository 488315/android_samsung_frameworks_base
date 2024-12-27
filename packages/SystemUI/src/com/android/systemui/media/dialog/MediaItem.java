package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.Optional;

public final class MediaItem {
    public final Optional mMediaDeviceOptional;
    public final int mMediaItemType;
    public final String mTitle;

    private MediaItem(MediaDevice mediaDevice, String str, int i) {
        this.mMediaDeviceOptional = Optional.ofNullable(mediaDevice);
        this.mTitle = str;
        this.mMediaItemType = i;
    }

    public static MediaItem createDeviceMediaItem(MediaDevice mediaDevice) {
        return new MediaItem(mediaDevice, mediaDevice.getName(), 0);
    }

    public static MediaItem createGroupDividerMediaItem(String str) {
        return new MediaItem(null, str, 1);
    }

    public static MediaItem createPairNewDeviceMediaItem() {
        return new MediaItem(null, null, 2);
    }
}
