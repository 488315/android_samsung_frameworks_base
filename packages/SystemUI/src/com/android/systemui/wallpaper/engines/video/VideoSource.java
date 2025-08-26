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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.media.SemMediaPlayer;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.IOException;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class VideoSource {
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

    public class VideoResource implements VideoLocation {
        public AssetFileDescriptor mAssetFdForPlay;
        public final Context mContext;
        public final String mFilename;
        public final String mPackageName;

        public VideoResource(Context context, String str, String str2) {
            this.mContext = context;
            this.mPackageName = str;
            this.mFilename = str2;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x0095 -> B:37:0x0098). Please report as a decompilation issue!!! */
        public final AssetFileDescriptor getAssetFileDescriptor() throws Resources.NotFoundException, PackageManager.NameNotFoundException, IOException {
            Context contextCreatePackageContext;
            Resources resources;
            AssetManager assets;
            Context context = this.mContext;
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
            StringBuilder sb = new StringBuilder("getVideoFDFromPackage() pkgName = ");
            String str = this.mPackageName;
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "WallpaperUtils");
            AssetFileDescriptor assetFileDescriptorOpenFd = null;
            if (context != null && !TextUtils.isEmpty(str)) {
                String str2 = this.mFilename;
                if (TextUtils.isEmpty(str2) || !"com.samsung.android.wallpaper.res".equals(str)) {
                    str2 = "video_1.mp4";
                }
                try {
                    contextCreatePackageContext = context.createPackageContext(str, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.d("WallpaperUtils", "Cannot find package name");
                    contextCreatePackageContext = null;
                }
                if (contextCreatePackageContext == null) {
                    APKContents aPKContents = new APKContents(APKContents.getMainThemePackagePath(str));
                    resources = aPKContents.getResources();
                    assets = aPKContents.getAssets();
                    if (resources == null && assets == null) {
                        Log.e("WallpaperUtils", "getVideoFDFromPackage: otherResources and otherAssets are null.");
                    }
                } else {
                    resources = contextCreatePackageContext.getResources();
                    assets = contextCreatePackageContext.getAssets();
                }
                try {
                    if ("com.samsung.android.wallpaper.res".equals(str)) {
                        String strSubstring = str2.substring(0, str2.lastIndexOf(46));
                        if (resources != null) {
                            assetFileDescriptorOpenFd = resources.openRawResourceFd(resources.getIdentifier(strSubstring, "raw", str));
                        } else {
                            Log.e("WallpaperUtils", "getVideoFDFromPackage: otherResources is null");
                        }
                    } else if (assets == null) {
                        Log.e("WallpaperUtils", "getVideoFDFromPackage: assetManager is null");
                    } else {
                        assetFileDescriptorOpenFd = assets.openFd(str2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return assetFileDescriptorOpenFd;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final void release() throws IOException {
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
        public final boolean setSourceToPlayer(SemMediaPlayer semMediaPlayer) throws Resources.NotFoundException, PackageManager.NameNotFoundException, IOException {
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
        public final void useMediaRetriever(Consumer consumer) throws IOException {
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
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("useMediaRetriever: e=", e, "ImageWallpaper[VideoResource]", e);
                consumer.accept(null);
            }
        }
    }

    public VideoSource(Context context, int i, int i2, int i3, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper) {
        String videoFilePath;
        VideoLocation videoResource;
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
        boolean zIsEmpty = TextUtils.isEmpty(videoFilePath);
        String str = this.TAG;
        Point point = null;
        if (zIsEmpty) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
            String videoPackage = wallpaperManager.getVideoPackage(sourceWhich);
            String videoFileName = wallpaperManager.getVideoFileName(sourceWhich);
            if (TextUtils.isEmpty(videoPackage)) {
                Log.e(str, "VideoSource: failed to determine video location");
                videoResource = null;
            } else {
                videoResource = new VideoResource(this.mContext, videoPackage, videoFileName);
            }
        } else {
            videoResource = new VideoFile(videoFilePath);
        }
        this.mVideoLocation = videoResource;
        if (videoResource != null) {
            Point[] pointArr = {null};
            videoResource.useMediaRetriever(new VideoSource$$ExternalSyntheticLambda0(this, pointArr));
            point = pointArr[0];
        }
        this.mVideoSize = point;
        Log.i(str, "init: source=" + this.mVideoLocation + ", videoSize = " + this.mVideoSize);
    }

    public final boolean isFixedOrientation(boolean z) {
        PackageManager packageManager = this.mContext.getPackageManager();
        boolean zIsFixedOrientation = true;
        boolean z2 = packageManager != null && packageManager.hasSystemFeature("com.samsung.feature.device_category_tablet");
        boolean z3 = Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE;
        if (z || z2) {
            zIsFixedOrientation = false;
        } else {
            int i = this.mWhich;
            if (z3) {
                zIsFixedOrientation = WhichChecker.isFlagEnabled(i, 16);
            } else if (WhichChecker.isWatchFace(i)) {
                zIsFixedOrientation = new SemWallpaperProperties(this.mContext, WhichChecker.getSourceWhich(i), this.mUserId).isFixedOrientation();
            }
        }
        Log.i(this.TAG, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("isFixedOrientation: , isTablet=", ", isFold=", ", isPreview=", z2, z3), z, ", isFixedOrientation=", zIsFixedOrientation));
        return zIsFixedOrientation;
    }

    public class VideoFile implements VideoLocation {
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
        public final void useMediaRetriever(Consumer consumer) throws IOException {
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
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("useMediaRetriever: e=", e, "ImageWallpaper[VideoFile]", e);
                consumer.accept(null);
            }
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoSource.VideoLocation
        public final void release() {
        }
    }
}
