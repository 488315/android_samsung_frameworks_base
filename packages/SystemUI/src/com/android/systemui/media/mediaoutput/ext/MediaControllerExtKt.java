package com.android.systemui.media.mediaoutput.ext;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public abstract class MediaControllerExtKt {
    public static final String toLogText(MediaMetadata mediaMetadata) {
        CharSequence title;
        if (mediaMetadata == null) {
            return "null";
        }
        String string = mediaMetadata.getString("android.media.metadata.TITLE");
        if (string == null && ((title = mediaMetadata.getDescription().getTitle()) == null || (string = title.toString()) == null || string.length() <= 0)) {
            string = null;
        }
        String string2 = mediaMetadata.getString("android.media.metadata.ARTIST");
        long j = mediaMetadata.getLong("android.media.metadata.DURATION");
        Bitmap bitmap = mediaMetadata.getBitmap("android.media.metadata.ALBUM_ART");
        if (bitmap == null && (bitmap = mediaMetadata.getBitmap("android.media.metadata.ART")) == null) {
            bitmap = mediaMetadata.getBitmap("android.media.metadata.DISPLAY_ICON");
        }
        boolean z = bitmap != null;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("MediaMetadata {title=", string, ", artist=", string2, ", duration=");
        sbM.append(j);
        sbM.append(", thumb=");
        sbM.append(z);
        sbM.append("}");
        String string3 = sbM.toString();
        return string3 == null ? "null" : string3;
    }
}
