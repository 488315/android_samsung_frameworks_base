package com.android.systemui.media.mediaoutput.ext;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("MediaMetadata {title=", string, ", artist=", string2, ", duration=");
        m.append(j);
        m.append(", thumb=");
        m.append(z);
        m.append("}");
        String sb = m.toString();
        return sb == null ? "null" : sb;
    }
}
