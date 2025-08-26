package com.android.systemui.wallpaper.provider;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class VideoThumbnailGenerator {
    public static long getVideoFrameTime(MediaMetadataRetriever mediaMetadataRetriever, int i) throws NumberFormatException {
        if (i == 0) {
            return 0L;
        }
        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(32);
        String strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(9);
        if (TextUtils.isEmpty(strExtractMetadata) || TextUtils.isEmpty(strExtractMetadata2)) {
            return 0L;
        }
        int i2 = Integer.parseInt(strExtractMetadata);
        int i3 = Integer.parseInt(strExtractMetadata2);
        if (i2 <= 0 || i <= 0 || i > i2) {
            return 0L;
        }
        return (int) ((i / i2) * i3 * 1000);
    }
}
