package com.android.systemui.wallpaper.provider;

import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedImageDrawable;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.gif.GifSource;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.engines.theme.AnimatedSource;
import com.android.systemui.wallpaper.engines.theme.MotionSource;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.android.systemui.wallpaper.theme.MotionWallpaper;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.nexus.video.BuildConfig;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail$Params;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

public final class ThumbnailGenerator {
    public final Context mContext;
    public final WallpaperManager mWallMgr;

    public ThumbnailGenerator(Context context) {
        this.mContext = context.getApplicationContext();
        this.mWallMgr = WallpaperManager.getInstance(context);
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.wallpaper.provider.VideoThumbnailGenerator$$ExternalSyntheticLambda0] */
    public final Bitmap generateThumbnail(final GetThumbnail$Params getThumbnail$Params) {
        Bitmap bitmap;
        int i;
        int i2;
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        WallpaperManager wallpaperManager = this.mWallMgr;
        int i3 = getThumbnail$Params.sourceWhich;
        int semGetWallpaperType = wallpaperManager.semGetWallpaperType(i3);
        if (semGetWallpaperType == 0 || semGetWallpaperType == -1) {
            new ImageThumbnailGenerator();
            final Context context = this.mContext;
            CoverWallpaperController coverWallpaperController = CoverWallpaperController.sInstance;
            int i4 = getThumbnail$Params.sourceWhich;
            final ImageSource imageSource = new ImageSource(context, coverWallpaperController, null, i4, getThumbnail$Params.userId, DisplayUtils.getDisplayIdByWhich(i4, context));
            final Bitmap[] bitmapArr = {null};
            imageSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.provider.ImageThumbnailGenerator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    boolean z;
                    int i5;
                    int i6;
                    GetThumbnail$Params getThumbnail$Params2 = GetThumbnail$Params.this;
                    Context context2 = context;
                    ImageSource imageSource2 = imageSource;
                    Bitmap[] bitmapArr2 = bitmapArr;
                    ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                    SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    int i7 = getThumbnail$Params2.which;
                    int i8 = getThumbnail$Params2.rotation;
                    Point displaySize = DisplayUtils.getDisplaySize(context2, i7, i8);
                    boolean isFixedOrientation = imageSource2.isFixedOrientation(false, settingsHelper);
                    if (!isFixedOrientation || (i5 = displaySize.x) <= (i6 = displaySize.y)) {
                        z = false;
                    } else {
                        displaySize.x = i6;
                        displaySize.y = i5;
                        z = true;
                    }
                    Bitmap bitmap2 = wallpaperImage.mBitmap;
                    if (bitmap2 == null) {
                        Log.e("ImageWallpaper[ImageThumbnailGenerator]", "generateThumbnail: failed to get wallpaper bitmap");
                        return;
                    }
                    ArrayList arrayList = wallpaperImage.mCropRects;
                    Rect rect = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
                    if (arrayList != null && !arrayList.isEmpty()) {
                        rect = IntelligentCropHelper.getNearestCropHint(displaySize, arrayList);
                    }
                    bitmapArr2[0] = BitmapUtils.getSizeLimitedCenterCropBitmap(bitmap2, rect, displaySize.x, displaySize.y, z ? -DisplayUtils.convertDisplayRotationToAngle(i8) : 0, false);
                    if (isFixedOrientation) {
                        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("generateThumbnail: isFixedOrientation=", "ImageWallpaper[ImageThumbnailGenerator]", isFixedOrientation);
                    }
                }
            });
            return bitmapArr[0];
        }
        int i5 = getThumbnail$Params.which;
        int i6 = getThumbnail$Params.rotation;
        if (semGetWallpaperType == 8) {
            final VideoThumbnailGenerator videoThumbnailGenerator = new VideoThumbnailGenerator();
            final Context context2 = this.mContext;
            final SemWallpaperProperties semWallpaperProperties = new SemWallpaperProperties(context2, i3, getThumbnail$Params.userId);
            String stringProperty = semWallpaperProperties.getStringProperty("thumbnailUri");
            if (!TextUtils.isEmpty(stringProperty)) {
                try {
                    fileInputStream = new FileInputStream(new File(stringProperty));
                    try {
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                    } finally {
                    }
                } catch (Exception e) {
                    EmergencyButton$$ExternalSyntheticOutline0.m("generateThumbnail: e=", e, "ImageWallpaper[VideoThumbnailGenerator]");
                }
                try {
                    bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                    if (bitmap == null) {
                        Log.w("ImageWallpaper[VideoThumbnailGenerator]", "generateThumbnail: failed to decode image file. file=" + stringProperty);
                    }
                    bufferedInputStream.close();
                    fileInputStream.close();
                    return bitmap;
                } finally {
                }
            }
            final Bitmap[] bitmapArr2 = {null};
            final VideoSource videoSource = new VideoSource(context2, getThumbnail$Params.sourceWhich, WhichChecker.isWatchFace(i3) ? 4 : 1, getThumbnail$Params.userId, CoverWallpaperController.sInstance, null);
            final ?? r1 = new Consumer() { // from class: com.android.systemui.wallpaper.provider.VideoThumbnailGenerator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    VideoThumbnailGenerator videoThumbnailGenerator2 = VideoThumbnailGenerator.this;
                    VideoSource videoSource2 = videoSource;
                    Context context3 = context2;
                    Bitmap[] bitmapArr3 = bitmapArr2;
                    SemWallpaperProperties semWallpaperProperties2 = semWallpaperProperties;
                    MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
                    videoThumbnailGenerator2.getClass();
                    VideoSource.VideoLocation videoLocation = videoSource2.mVideoLocation;
                    if (videoLocation instanceof VideoSource.VideoResource) {
                        VideoSource.VideoResource videoResource = (VideoSource.VideoResource) videoLocation;
                        if ("com.samsung.android.wallpaper.res".equals(videoResource.mPackageName)) {
                            int defaultVideoFrameInfo = new SemWallpaperResourcesInfo(context3).getDefaultVideoFrameInfo(videoResource.mFilename);
                            long videoFrameTime = VideoThumbnailGenerator.getVideoFrameTime(mediaMetadataRetriever, defaultVideoFrameInfo);
                            MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
                            bitmapParams.setPreferredConfig(Bitmap.Config.ARGB_8888);
                            Log.d("ImageWallpaper[VideoThumbnailGenerator]", "generateThumbnail: frameNo=" + defaultVideoFrameInfo + ", " + videoFrameTime + "us");
                            bitmapArr3[0] = mediaMetadataRetriever.getFrameAtTime(videoFrameTime, 2, bitmapParams);
                        }
                    }
                    if (bitmapArr3[0] == null) {
                        int intProperty = semWallpaperProperties2.getIntProperty("thumbnailFrameNo", 0);
                        long videoFrameTime2 = VideoThumbnailGenerator.getVideoFrameTime(mediaMetadataRetriever, intProperty);
                        StringBuilder sb = new StringBuilder("generateThumbnail: frameNo=");
                        sb.append(intProperty);
                        sb.append(", ");
                        sb.append(videoFrameTime2);
                        ExifInterface$$ExternalSyntheticOutline0.m(sb, "us", "ImageWallpaper[VideoThumbnailGenerator]");
                        bitmapArr3[0] = mediaMetadataRetriever.getFrameAtTime(videoFrameTime2);
                    }
                }
            };
            VideoSource.VideoLocation videoLocation = videoSource.mVideoLocation;
            if (videoLocation != null) {
                videoLocation.useMediaRetriever(new Consumer() { // from class: com.android.systemui.wallpaper.engines.video.VideoSource$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Consumer consumer = r1;
                        MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
                        if (mediaMetadataRetriever == null) {
                            return;
                        }
                        consumer.accept(mediaMetadataRetriever);
                    }
                });
            }
            if (bitmapArr2[0] != null) {
                boolean isFixedOrientation = videoSource.isFixedOrientation(false);
                Point displaySize = DisplayUtils.getDisplaySize(context2, i5, i6);
                if (isFixedOrientation && (i = displaySize.x) > (i2 = displaySize.y)) {
                    displaySize.x = i2;
                    displaySize.y = i;
                }
                Bitmap sizeLimitedCenterCropBitmap = BitmapUtils.getSizeLimitedCenterCropBitmap(bitmapArr2[0], GraphicsUtils.getCenterCropRect(bitmapArr2[0].getWidth(), bitmapArr2[0].getHeight(), displaySize.x, displaySize.y), displaySize.x, displaySize.y, isFixedOrientation ? -DisplayUtils.convertDisplayRotationToAngle(i6) : 0, true);
                Bitmap bitmap2 = bitmapArr2[0];
                if (bitmap2 != sizeLimitedCenterCropBitmap) {
                    bitmap2.recycle();
                }
                bitmap = sizeLimitedCenterCropBitmap;
            } else {
                bitmap = null;
            }
            if (videoSource.mVideoLocation != null) {
                Log.d(videoSource.TAG, BuildConfig.BUILD_TYPE);
                videoSource.mVideoLocation.release();
                videoSource.mVideoLocation = null;
            }
            return bitmap;
        }
        if (semGetWallpaperType == 4) {
            new AnimatedThumbnailGenerator();
            Context context3 = this.mContext;
            AnimatedSource animatedSource = new AnimatedSource(context3, i3, new FrameLayout(context3));
            Point displaySize2 = DisplayUtils.getDisplaySize(context3, i5, i6);
            int i7 = displaySize2.x;
            int i8 = displaySize2.y;
            try {
                animatedSource.getComplexAnimation();
            } catch (Throwable th) {
                Log.e(animatedSource.TAG, RowInflaterTask$$ExternalSyntheticOutline0.m("failed to get apk resource : e = ", th), th);
            }
            animatedSource.mRootView.measure(View.MeasureSpec.makeMeasureSpec(i7, 1073741824), View.MeasureSpec.makeMeasureSpec(i8, 1073741824));
            animatedSource.mRootView.layout(0, 0, i7, i8);
            Bitmap createBitmap = Bitmap.createBitmap(animatedSource.mRootView.getMeasuredWidth(), animatedSource.mRootView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            animatedSource.mRootView.draw(new Canvas(createBitmap));
            return createBitmap;
        }
        if (semGetWallpaperType != 1) {
            if (semGetWallpaperType != 5) {
                ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(semGetWallpaperType, "generateThumbnail : Unexpected wallpaper type - ", "ImageWallpaper[ThumbnailGenerator]");
                return null;
            }
            new GifThumbnailGenerator();
            Context context4 = this.mContext;
            Point displaySize3 = DisplayUtils.getDisplaySize(context4, i5, i6);
            Bitmap createBitmap2 = Bitmap.createBitmap(displaySize3.x, displaySize3.y, Bitmap.Config.ARGB_8888);
            GifSource gifSource = new GifSource(context4, i3, CoverWallpaperController.sInstance);
            Canvas canvas = new Canvas(createBitmap2);
            String gifPath = gifSource.getGifPath();
            Log.d("ImageWallpaper[GifThumbnailGenerator]", "generateThumbnail: gifPath = " + gifPath + ", displaySize = " + displaySize3);
            ((AnimatedImageDrawable) AnimatedImageDrawable.createFromPath(gifPath)).draw(canvas);
            return createBitmap2;
        }
        new MotionThumbnailGenerator();
        Context context5 = this.mContext;
        MotionWallpaper motionWallpaper = new MotionWallpaper(context5, i3, true);
        Log.d("ImageWallpaper[MotionThumbnailGenerator]", "generateThumbnail: rootView=" + motionWallpaper);
        MotionSource motionSource = new MotionSource(i3, motionWallpaper);
        Point displaySize4 = DisplayUtils.getDisplaySize(context5, i5, i6);
        int i9 = displaySize4.x;
        int i10 = displaySize4.y;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i9, 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i10, 1073741824);
        MotionWallpaper motionWallpaper2 = motionSource.mRootView;
        motionWallpaper2.measure(makeMeasureSpec, makeMeasureSpec2);
        motionWallpaper2.layout(0, 0, i9, i10);
        Bitmap createBitmap3 = Bitmap.createBitmap(motionWallpaper2.getMeasuredWidth(), motionWallpaper2.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap3);
        Log.d(motionSource.TAG, "generateThumbnail: rootView=" + motionWallpaper2);
        motionWallpaper2.init();
        motionWallpaper2.draw(canvas2);
        return createBitmap3;
    }
}
