package com.android.systemui.wallpaper.provider;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VideoThumbnailGenerator {
    public static long getVideoFrameTime(MediaMetadataRetriever mediaMetadataRetriever, int i) {
        if (i == 0) {
            return 0L;
        }
        String extractMetadata = mediaMetadataRetriever.extractMetadata(32);
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(9);
        if (!TextUtils.isEmpty(extractMetadata) && !TextUtils.isEmpty(extractMetadata2)) {
            int parseInt = Integer.parseInt(extractMetadata);
            int parseInt2 = Integer.parseInt(extractMetadata2);
            if (parseInt > 0 && i > 0 && i <= parseInt) {
                return (int) ((i / parseInt) * parseInt2 * 1000);
            }
        }
        return 0L;
    }
}
