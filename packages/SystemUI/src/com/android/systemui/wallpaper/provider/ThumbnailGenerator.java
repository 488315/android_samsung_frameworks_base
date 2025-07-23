package com.android.systemui.wallpaper.provider;

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
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.gif.GifSource;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.engines.theme.AnimatedSource;
import com.android.systemui.wallpaper.engines.theme.MotionSource;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.android.systemui.wallpaper.provider.VideoThumbnailGenerator$$ExternalSyntheticLambda0;
import com.android.systemui.wallpaper.theme.MotionWallpaper;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.nexus.video.BuildConfig;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ThumbnailGenerator {
    public final Context mContext;
    public final WallpaperManager mWallMgr;

    public ThumbnailGenerator(Context context) {
        this.mContext = context.getApplicationContext();
        this.mWallMgr = WallpaperManager.getInstance(context);
    }

    public final Bitmap generateThumbnail(final GetThumbnail.Params params) {
        Bitmap bitmap;
        int i;
        int i2;
        WallpaperManager wallpaperManager = this.mWallMgr;
        int i3 = params.sourceWhich;
        int semGetWallpaperType = wallpaperManager.semGetWallpaperType(i3);
        if (semGetWallpaperType == 0 || semGetWallpaperType == -1) {
            new ImageThumbnailGenerator();
            final Context context = this.mContext;
            CoverWallpaperController coverWallpaperController = CoverWallpaperController.sInstance;
            int i4 = params.sourceWhich;
            final ImageSource imageSource = new ImageSource(context, coverWallpaperController, null, i4, params.userId, DisplayUtils.getDisplayIdByWhich(i4, context));
            final Bitmap[] bitmapArr = {null};
            imageSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.provider.ImageThumbnailGenerator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    boolean z;
                    int i5;
                    int i6;
                    GetThumbnail.Params params2 = GetThumbnail.Params.this;
                    Context context2 = context;
                    ImageSource imageSource2 = imageSource;
                    Bitmap[] bitmapArr2 = bitmapArr;
                    ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                    SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    int i7 = params2.which;
                    int i8 = params2.rotation;
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
                        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("generateThumbnail: isFixedOrientation=", "ImageWallpaper[ImageThumbnailGenerator]", isFixedOrientation);
                    }
                }
            });
            return bitmapArr[0];
        }
        int i5 = params.which;
        int i6 = params.rotation;
        if (semGetWallpaperType == 8) {
            VideoThumbnailGenerator videoThumbnailGenerator = new VideoThumbnailGenerator();
            Context context2 = this.mContext;
            SemWallpaperProperties semWallpaperProperties = new SemWallpaperProperties(context2, i3, params.userId);
            String stringProperty = semWallpaperProperties.getStringProperty("thumbnailUri");
            if (!TextUtils.isEmpty(stringProperty)) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(new File(stringProperty));
                    try {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        try {
                            Bitmap decodeStream = BitmapFactory.decodeStream(bufferedInputStream);
                            if (decodeStream == null) {
                                Log.w("ImageWallpaper[VideoThumbnailGenerator]", "generateThumbnail: failed to decode image file. file=" + stringProperty);
                            }
                            bufferedInputStream.close();
                            fileInputStream.close();
                            return decodeStream;
                        } finally {
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    EmergencyButton$$ExternalSyntheticOutline0.m("generateThumbnail: e=", e, "ImageWallpaper[VideoThumbnailGenerator]");
                }
            }
            Bitmap[] bitmapArr2 = {null};
            VideoSource videoSource = new VideoSource(context2, params.sourceWhich, WhichChecker.isWatchFace(i3) ? 4 : 1, params.userId, CoverWallpaperController.sInstance, null);
            final VideoThumbnailGenerator$$ExternalSyntheticLambda0 videoThumbnailGenerator$$ExternalSyntheticLambda0 = new VideoThumbnailGenerator$$ExternalSyntheticLambda0(videoThumbnailGenerator, videoSource, context2, bitmapArr2, semWallpaperProperties);
            VideoSource.VideoLocation videoLocation = videoSource.mVideoLocation;
            if (videoLocation != null) {
                videoLocation.useMediaRetriever(new Consumer() { // from class: com.android.systemui.wallpaper.engines.video.VideoSource$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        VideoThumbnailGenerator$$ExternalSyntheticLambda0 videoThumbnailGenerator$$ExternalSyntheticLambda02 = VideoThumbnailGenerator$$ExternalSyntheticLambda0.this;
                        MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
                        if (mediaMetadataRetriever == null) {
                            return;
                        }
                        videoThumbnailGenerator$$ExternalSyntheticLambda02.accept(mediaMetadataRetriever);
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
                bitmap = BitmapUtils.getSizeLimitedCenterCropBitmap(bitmapArr2[0], GraphicsUtils.getCenterCropRect(bitmapArr2[0].getWidth(), bitmapArr2[0].getHeight(), displaySize.x, displaySize.y), displaySize.x, displaySize.y, isFixedOrientation ? -DisplayUtils.convertDisplayRotationToAngle(i6) : 0, true);
                Bitmap bitmap2 = bitmapArr2[0];
                if (bitmap2 != bitmap) {
                    bitmap2.recycle();
                }
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
            FrameLayout frameLayout = new FrameLayout(context3);
            Log.i("ImageWallpaper[AnimatedThumbnailGenerator]", "generateThumbnail: rootView=" + frameLayout);
            AnimatedSource animatedSource = new AnimatedSource(context3, i3, frameLayout);
            Point displaySize2 = DisplayUtils.getDisplaySize(context3, i5, i6);
            int i7 = displaySize2.x;
            int i8 = displaySize2.y;
            try {
                animatedSource.createComplexAnimation(i7, i8);
            } catch (Throwable th) {
                Log.e(animatedSource.TAG, RowInflaterTask$$ExternalSyntheticOutline0.m("failed to get apk resource : e = ", th), th);
            }
            animatedSource.mRootView.measure(View.MeasureSpec.makeMeasureSpec(i7, 1073741824), View.MeasureSpec.makeMeasureSpec(i8, 1073741824));
            animatedSource.mRootView.layout(0, 0, i7, i8);
            Bitmap createBitmap = Bitmap.createBitmap(animatedSource.mRootView.getMeasuredWidth(), animatedSource.mRootView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            animatedSource.mRootView.draw(new Canvas(createBitmap));
            return createBitmap;
        }
        if (semGetWallpaperType == 1) {
            new MotionThumbnailGenerator();
            Context context4 = this.mContext;
            MotionWallpaper motionWallpaper = new MotionWallpaper(context4, i3, true);
            Log.i("ImageWallpaper[MotionThumbnailGenerator]", "generateThumbnail: rootView=" + motionWallpaper);
            MotionSource motionSource = new MotionSource(i3, motionWallpaper);
            Point displaySize3 = DisplayUtils.getDisplaySize(context4, i5, i6);
            int i9 = displaySize3.x;
            int i10 = displaySize3.y;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i9, 1073741824);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i10, 1073741824);
            MotionWallpaper motionWallpaper2 = motionSource.mRootView;
            motionWallpaper2.measure(makeMeasureSpec, makeMeasureSpec2);
            motionWallpaper2.layout(0, 0, i9, i10);
            Bitmap createBitmap2 = Bitmap.createBitmap(motionWallpaper2.getMeasuredWidth(), motionWallpaper2.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap2);
            Log.d(motionSource.TAG, "generateThumbnail: rootView=" + motionWallpaper2);
            motionWallpaper2.init();
            motionWallpaper2.draw(canvas);
            return createBitmap2;
        }
        if (semGetWallpaperType != 5) {
            ClockEventController$$ExternalSyntheticOutline0.m(semGetWallpaperType, "generateThumbnail : Unexpected wallpaper type - ", "ImageWallpaper[ThumbnailGenerator]");
            return null;
        }
        new GifThumbnailGenerator();
        Context context5 = this.mContext;
        String str = new GifSource(context5, i3, CoverWallpaperController.sInstance).mGifPath;
        Point displaySize4 = DisplayUtils.getDisplaySize(context5, i5, i6);
        Log.i("ImageWallpaper[GifThumbnailGenerator]", "generateThumbnail: gifPath = " + str + ", displaySize = " + displaySize4);
        AnimatedImageDrawable animatedImageDrawable = (AnimatedImageDrawable) AnimatedImageDrawable.createFromPath(str);
        if (animatedImageDrawable == null) {
            Log.w("ImageWallpaper[GifThumbnailGenerator]", "generateThumbnail: failed to get gif");
            return null;
        }
        Point point = new Point(animatedImageDrawable.getIntrinsicWidth(), animatedImageDrawable.getIntrinsicHeight());
        Bitmap createBitmap3 = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888);
        animatedImageDrawable.draw(new Canvas(createBitmap3));
        Bitmap sizeLimitedCenterCropBitmap = BitmapUtils.getSizeLimitedCenterCropBitmap(createBitmap3, GraphicsUtils.getCenterCropRect(point.x, point.y, displaySize4.x, displaySize4.y), displaySize4.x, displaySize4.y, 0, true);
        if (createBitmap3 != sizeLimitedCenterCropBitmap) {
            createBitmap3.recycle();
        }
        return sizeLimitedCenterCropBitmap;
    }
}
