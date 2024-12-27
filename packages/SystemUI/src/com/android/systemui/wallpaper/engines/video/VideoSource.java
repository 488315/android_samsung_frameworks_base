package com.android.systemui.wallpaper.engines.video;

import android.app.WallpaperManager;
import android.content.APKContents;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.engines.WallpaperSource;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.media.SemMediaPlayer;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import com.sec.ims.configuration.DATA;
import java.io.IOException;
import java.util.function.Consumer;

public final class VideoSource implements WallpaperSource {
    public final String TAG;
    public final Context mContext;
    public final CoverWallpaper mCoverWallpaper;
    public final int mType;
    public final int mUserId;
    public VideoLocation mVideoLocation;
    public final Point mVideoSize;
    public final int mWhich;

    public interface VideoLocation {
        void release();

        boolean setSourceToPlayer(SemMediaPlayer semMediaPlayer);

        void useMediaRetriever(Consumer consumer);
    }

    public final class VideoResource implements VideoLocation {
        public AssetFileDescriptor mAssetFdForPlay;
        public final Context mContext;
        public final String mFilename;
        public final String mPackageName;

        public VideoResource(Context context, String str, String str2) {
            this.mContext = context;
            this.mPackageName = str;
            this.mFilename = str2;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0095 -> B:23:0x0098). Please report as a decompilation issue!!! */
        public final AssetFileDescriptor getAssetFileDescriptor() {
            Context context;
            Resources resources;
            AssetManager assets;
            Context context2 = this.mContext;
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
            StringBuilder sb = new StringBuilder("getVideoFDFromPackage() pkgName = ");
            String str = this.mPackageName;
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "WallpaperUtils");
            AssetFileDescriptor assetFileDescriptor = null;
            if (context2 != null && !TextUtils.isEmpty(str)) {
                String str2 = this.mFilename;
                if (TextUtils.isEmpty(str2) || !"com.samsung.android.wallpaper.res".equals(str)) {
                    str2 = "video_1.mp4";
                }
                try {
                    context = context2.createPackageContext(str, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.d("WallpaperUtils", "Cannot find package name");
                    context = null;
                }
                if (context == null) {
                    APKContents aPKContents = new APKContents(APKContents.getMainThemePackagePath(str));
                    resources = aPKContents.getResources();
                    assets = aPKContents.getAssets();
                    if (resources == null && assets == null) {
                        Log.e("WallpaperUtils", "getVideoFDFromPackage: otherResources and otherAssets are null.");
                    }
                } else {
                    resources = context.getResources();
                    assets = context.getAssets();
                }
                try {
                    if ("com.samsung.android.wallpaper.res".equals(str)) {
                        String substring = str2.substring(0, str2.lastIndexOf(46));
                        if (resources != null) {
                            assetFileDescriptor = resources.openRawResourceFd(resources.getIdentifier(substring, "raw", str));
                        } else {
                            Log.e("WallpaperUtils", "getVideoFDFromPackage: otherResources is null");
                        }
                    } else if (assets == null) {
                        Log.e("WallpaperUtils", "getVideoFDFromPackage: assetManager is null");
                    } else {
                        assetFileDescriptor = assets.openFd(str2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return assetFileDescriptor;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final void release() {
            AssetFileDescriptor assetFileDescriptor = this.mAssetFdForPlay;
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor.close();
                } catch (IOException e) {
                    Log.e("ImageWallpaper[VideoResource]", "releaseAssetFileDescriptor: e=" + e);
                }
            }
            this.mAssetFdForPlay = null;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final boolean setSourceToPlayer(SemMediaPlayer semMediaPlayer) {
            Log.d("ImageWallpaper[VideoResource]", "setSourceToPlayer: asset type. " + this);
            AssetFileDescriptor assetFileDescriptor = this.mAssetFdForPlay;
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor.close();
                } catch (IOException e) {
                    Log.e("ImageWallpaper[VideoResource]", "releaseAssetFileDescriptor: e=" + e);
                }
            }
            AssetFileDescriptor assetFileDescriptor2 = getAssetFileDescriptor();
            this.mAssetFdForPlay = assetFileDescriptor2;
            if (assetFileDescriptor2 == null) {
                Log.e("ImageWallpaper[VideoResource]", "setSourceToPlayer: failed to get asset file descriptor");
                return false;
            }
            try {
                semMediaPlayer.init(assetFileDescriptor2);
                return true;
            } catch (IOException e2) {
                Log.e("ImageWallpaper[VideoResource]", "setSourceToPlayer: e=" + e2);
                return false;
            }
        }

        public final String toString() {
            return this.mPackageName + "/" + this.mFilename;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final void useMediaRetriever(Consumer consumer) {
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    AssetFileDescriptor assetFileDescriptor = getAssetFileDescriptor();
                    try {
                        if (assetFileDescriptor != null) {
                            mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                            consumer.accept(mediaMetadataRetriever);
                        } else {
                            Log.e("ImageWallpaper[VideoResource]", "useMediaRetriever: failed to get video source!");
                            consumer.accept(null);
                        }
                        if (assetFileDescriptor != null) {
                            assetFileDescriptor.close();
                        }
                        mediaMetadataRetriever.close();
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Log.e("ImageWallpaper[VideoResource]", "useMediaRetriever: e=" + e, e);
                consumer.accept(null);
            }
        }
    }

    public VideoSource(Context context, int i, int i2, int i3, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper) {
        String videoFilePath;
        VideoLocation videoLocation;
        new Consumer() { // from class: com.android.systemui.wallpaper.engines.video.VideoSource.1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                VideoSource videoSource = VideoSource.this;
                String str = videoSource.TAG;
                ((CoverWallpaperController) videoSource.mCoverWallpaper).getWallpaperType();
            }
        };
        this.TAG = "ImageWallpaper_" + i + "[VideoSource]";
        this.mType = i2;
        this.mUserId = i3;
        this.mContext = context;
        this.mCoverWallpaper = coverWallpaper;
        int sourceWhich = WhichChecker.getSourceWhich(i);
        this.mWhich = sourceWhich;
        if ((i2 & 2) != 2) {
            videoFilePath = WallpaperManager.getInstance(context).getVideoFilePath(WhichChecker.getSourceWhich(sourceWhich));
        } else if (WhichChecker.isWatchFace(sourceWhich) || WhichChecker.isVirtualDisplay(sourceWhich)) {
            videoFilePath = ((CoverWallpaperController) coverWallpaper).getWallpaperPath();
        } else {
            PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) pluginWallpaper;
            pluginWallpaperController.getClass();
            int screen = PluginWallpaperController.getScreen(sourceWhich);
            PluginWallpaperManager pluginWallpaperManager = pluginWallpaperController.mPluginWallpaperManager;
            if (pluginWallpaperManager.isFbeAvailable(screen)) {
                try {
                    pluginWallpaperController.mWallpaperManager.semSetDLSWallpaperColors(pluginWallpaperManager.getFbeSemWallpaperColors(screen), sourceWhich);
                } catch (IllegalArgumentException e) {
                    Log.e("PluginWallpaperController", "getWallpaperPath: " + e.getMessage());
                }
                videoFilePath = pluginWallpaperManager.getFbeWallpaperPath(screen);
            } else {
                videoFilePath = pluginWallpaperManager.getWallpaperPath(screen);
            }
        }
        boolean isEmpty = TextUtils.isEmpty(videoFilePath);
        String str = this.TAG;
        Point point = null;
        if (isEmpty) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
            String videoPackage = wallpaperManager.getVideoPackage(sourceWhich);
            String videoFileName = wallpaperManager.getVideoFileName(sourceWhich);
            if (TextUtils.isEmpty(videoPackage)) {
                Log.e(str, "VideoSource: failed to determine video location");
                videoLocation = null;
            } else {
                videoLocation = new VideoResource(this.mContext, videoPackage, videoFileName);
            }
        } else {
            videoLocation = new VideoFile(videoFilePath);
        }
        this.mVideoLocation = videoLocation;
        if (videoLocation != null) {
            final Point[] pointArr = {null};
            videoLocation.useMediaRetriever(new Consumer() { // from class: com.android.systemui.wallpaper.engines.video.VideoSource$$ExternalSyntheticLambda1
                public final /* synthetic */ boolean f$2 = true;

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    VideoSource videoSource = VideoSource.this;
                    Point[] pointArr2 = pointArr;
                    boolean z = this.f$2;
                    MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
                    videoSource.getClass();
                    if (mediaMetadataRetriever == null) {
                        return;
                    }
                    String extractMetadata = mediaMetadataRetriever.extractMetadata(18);
                    String extractMetadata2 = mediaMetadataRetriever.extractMetadata(19);
                    String extractMetadata3 = mediaMetadataRetriever.extractMetadata(24);
                    pointArr2[0] = (extractMetadata == null || extractMetadata2 == null) ? null : (z && (DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD.equals(extractMetadata3) || "270".equals(extractMetadata3))) ? new Point(Integer.parseInt(extractMetadata2), Integer.parseInt(extractMetadata)) : new Point(Integer.parseInt(extractMetadata), Integer.parseInt(extractMetadata2));
                }
            });
            point = pointArr[0];
        }
        this.mVideoSize = point;
        Log.i(str, "init: source=" + this.mVideoLocation + ", videoSize = " + this.mVideoSize);
    }

    public final boolean isFixedOrientation(boolean z) {
        PackageManager packageManager = this.mContext.getPackageManager();
        boolean z2 = true;
        boolean z3 = packageManager != null && packageManager.hasSystemFeature("com.samsung.feature.device_category_tablet");
        boolean z4 = Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE;
        if (z || z3) {
            z2 = false;
        } else {
            int i = this.mWhich;
            if (z4) {
                z2 = WhichChecker.isFlagEnabled(i, 16);
            } else if (WhichChecker.isWatchFace(i)) {
                z2 = new SemWallpaperProperties(this.mContext, WhichChecker.getSourceWhich(i), this.mUserId).isFixedOrientation();
            }
        }
        Log.i(this.TAG, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isFixedOrientation: , isTablet=", ", isFold=", ", isPreview=", z3, z4), z, ", isFixedOrientation=", z2));
        return z2;
    }

    public final class VideoFile implements VideoLocation {
        public final String mFilePathName;

        public VideoFile(String str) {
            this.mFilePathName = str;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final boolean setSourceToPlayer(SemMediaPlayer semMediaPlayer) {
            StringBuilder sb = new StringBuilder("setSourceToPlayer: path=");
            String str = this.mFilePathName;
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "ImageWallpaper[VideoFile]");
            try {
                semMediaPlayer.init(str);
                return true;
            } catch (IOException e) {
                Log.e("ImageWallpaper[VideoFile]", "setSourceToPlayer: e=" + e);
                return false;
            }
        }

        public final String toString() {
            return this.mFilePathName;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final void useMediaRetriever(Consumer consumer) {
            StringBuilder sb = new StringBuilder("useMediaRetriever: path=");
            String str = this.mFilePathName;
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "ImageWallpaper[VideoFile]");
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    mediaMetadataRetriever.setDataSource(str);
                    consumer.accept(mediaMetadataRetriever);
                    mediaMetadataRetriever.close();
                } finally {
                }
            } catch (Exception e) {
                Log.e("ImageWallpaper[VideoFile]", "useMediaRetriever: e=" + e, e);
                consumer.accept(null);
            }
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final void release() {
        }
    }
}
