package com.android.systemui.wallpaper.provider;

import android.app.SemWallpaperResourcesInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class VideoThumbnailGenerator$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ VideoThumbnailGenerator f$0;
    public final /* synthetic */ VideoSource f$1;
    public final /* synthetic */ Context f$2;
    public final /* synthetic */ Bitmap[] f$3;
    public final /* synthetic */ SemWallpaperProperties f$4;

    public /* synthetic */ VideoThumbnailGenerator$$ExternalSyntheticLambda0(VideoThumbnailGenerator videoThumbnailGenerator, VideoSource videoSource, Context context, Bitmap[] bitmapArr, SemWallpaperProperties semWallpaperProperties) {
        this.f$0 = videoThumbnailGenerator;
        this.f$1 = videoSource;
        this.f$2 = context;
        this.f$3 = bitmapArr;
        this.f$4 = semWallpaperProperties;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) throws NumberFormatException {
        VideoSource videoSource = this.f$1;
        Context context = this.f$2;
        Bitmap[] bitmapArr = this.f$3;
        SemWallpaperProperties semWallpaperProperties = this.f$4;
        MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
        VideoSource.VideoLocation videoLocation = videoSource.mVideoLocation;
        if (videoLocation instanceof VideoSource.VideoResource) {
            VideoSource.VideoResource videoResource = (VideoSource.VideoResource) videoLocation;
            if ("com.samsung.android.wallpaper.res".equals(videoResource.mPackageName)) {
                int defaultVideoFrameInfo = new SemWallpaperResourcesInfo(context).getDefaultVideoFrameInfo(videoResource.mFilename);
                long videoFrameTime = VideoThumbnailGenerator.getVideoFrameTime(mediaMetadataRetriever, defaultVideoFrameInfo);
                MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
                bitmapParams.setPreferredConfig(Bitmap.Config.ARGB_8888);
                Log.i("ImageWallpaper[VideoThumbnailGenerator]", "generateThumbnail: frameNo=" + defaultVideoFrameInfo + ", " + videoFrameTime + "us");
                bitmapArr[0] = mediaMetadataRetriever.getFrameAtTime(videoFrameTime, 2, bitmapParams);
            }
        }
        if (bitmapArr[0] == null) {
            int intProperty = semWallpaperProperties.getIntProperty("thumbnailFrameNo", 0);
            long videoFrameTime2 = VideoThumbnailGenerator.getVideoFrameTime(mediaMetadataRetriever, intProperty);
            Log.i("ImageWallpaper[VideoThumbnailGenerator]", "generateThumbnail: frameNo=" + intProperty + ", " + videoFrameTime2 + "us");
            bitmapArr[0] = mediaMetadataRetriever.getFrameAtTime(videoFrameTime2);
        }
    }
}
