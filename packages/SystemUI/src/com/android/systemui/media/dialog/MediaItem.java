package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MediaItem {
    public final boolean mIsFirstDeviceInGroup;
    public final Optional mMediaDeviceOptional;
    public final int mMediaItemType;
    public final String mTitle;

    private MediaItem(MediaDevice mediaDevice, String str, int i, boolean z) {
        this(mediaDevice, str, i, z, false, false);
    }

    public static MediaItem createDeviceMediaItem(MediaDevice mediaDevice) {
        return new MediaItem(mediaDevice, mediaDevice.getName(), 0, false);
    }

    public static MediaItem createGroupDividerMediaItem(String str) {
        return new MediaItem(null, str, 1, false);
    }

    public static MediaItem createPairNewDeviceMediaItem() {
        return new MediaItem(null, null, 2, false);
    }

    private MediaItem(MediaDevice mediaDevice, String str, int i, boolean z, boolean z2, boolean z3) {
        this.mMediaDeviceOptional = Optional.ofNullable(mediaDevice);
        this.mTitle = str;
        this.mMediaItemType = i;
        this.mIsFirstDeviceInGroup = z;
    }

    public static MediaItem createDeviceMediaItem(MediaDevice mediaDevice, boolean z) {
        return new MediaItem(mediaDevice, mediaDevice.getName(), 0, z);
    }
}
