package com.android.systemui.wallpaper.engines.video;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import com.sec.ims.configuration.DATA;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VideoSource$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ VideoSource f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ VideoSource$$ExternalSyntheticLambda0(VideoSource videoSource, Bitmap[] bitmapArr) {
        this.f$0 = videoSource;
        this.f$1 = bitmapArr;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                VideoSource videoSource = this.f$0;
                Bitmap[] bitmapArr = (Bitmap[]) this.f$1;
                MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
                videoSource.getClass();
                if (mediaMetadataRetriever != null) {
                    bitmapArr[0] = mediaMetadataRetriever.getFrameAtTime(0L, 2);
                    break;
                }
                break;
            default:
                Point[] pointArr = (Point[]) this.f$1;
                MediaMetadataRetriever mediaMetadataRetriever2 = (MediaMetadataRetriever) obj;
                if (mediaMetadataRetriever2 != null) {
                    String extractMetadata = mediaMetadataRetriever2.extractMetadata(18);
                    String extractMetadata2 = mediaMetadataRetriever2.extractMetadata(19);
                    String extractMetadata3 = mediaMetadataRetriever2.extractMetadata(24);
                    pointArr[0] = (extractMetadata == null || extractMetadata2 == null) ? null : (DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD.equals(extractMetadata3) || "270".equals(extractMetadata3)) ? new Point(Integer.parseInt(extractMetadata2), Integer.parseInt(extractMetadata)) : new Point(Integer.parseInt(extractMetadata), Integer.parseInt(extractMetadata2));
                    break;
                }
                break;
        }
    }

    public /* synthetic */ VideoSource$$ExternalSyntheticLambda0(VideoSource videoSource, Point[] pointArr) {
        this.f$0 = videoSource;
        this.f$1 = pointArr;
    }
}
