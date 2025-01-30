package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaItem {
    public final Optional mMediaDeviceOptional;
    public final int mMediaItemType;
    public final String mTitle;

    public MediaItem() {
        this.mMediaDeviceOptional = Optional.empty();
        this.mTitle = null;
        this.mMediaItemType = 2;
    }

    public MediaItem(String str, int i) {
        this.mMediaDeviceOptional = Optional.empty();
        this.mTitle = str;
        this.mMediaItemType = i;
    }

    public MediaItem(MediaDevice mediaDevice) {
        this.mMediaDeviceOptional = Optional.of(mediaDevice);
        this.mTitle = mediaDevice.getName();
        this.mMediaItemType = 0;
    }
}
