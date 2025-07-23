package com.android.systemui.wallpaper.provider;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class VideoThumbnailGenerator {
    public static long getVideoFrameTime(MediaMetadataRetriever mediaMetadataRetriever, int i) {
        if (i == 0) {
            return 0L;
        }
        String extractMetadata = mediaMetadataRetriever.extractMetadata(32);
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(9);
        if (TextUtils.isEmpty(extractMetadata) || TextUtils.isEmpty(extractMetadata2)) {
            return 0L;
        }
        int parseInt = Integer.parseInt(extractMetadata);
        int parseInt2 = Integer.parseInt(extractMetadata2);
        if (parseInt <= 0 || i <= 0 || i > parseInt) {
            return 0L;
        }
        return (int) ((i / parseInt) * parseInt2 * 1000);
    }
}
