package com.android.systemui.wallpaper.video;

import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.samsung.android.media.SemMediaPlayer;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VideoPlayer {
    public final Context mContext;
    public FileInputStream mFileInputStream;
    public final WallpaperLogger mLoggerWrapper;
    public final View mRootView;
    public SemMediaPlayer mSemMediaPlayer;
    public final SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public Surface mSurface;
    public HandlerThread mVideoPlayerThread;
    public boolean mIsPrepared = false;
    public boolean mIsPreparing = false;
    public boolean mIsRenderingStarted = false;
    public int mStartPosition = 0;
    public boolean mIsPendingStarted = false;
    public boolean mHasSurface = false;
    public final HashMap mMetadataMap = new HashMap();
    public boolean mBouncer = false;
    public final HandlerC36981 mMediaControlHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.video.VideoPlayer.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1001) {
                VideoPlayer.this.stopDrawing();
                WallpaperLogger wallpaperLogger = VideoPlayer.this.mLoggerWrapper;
                if (wallpaperLogger != null) {
                    ((WallpaperLoggerImpl) wallpaperLogger).log("VideoPlayer", "Video playing time out (5minutes)");
                }
            }
        }
    };
    public final C36992 mOnInitCompleteListener = new SemMediaPlayer.OnInitCompleteListener() { // from class: com.android.systemui.wallpaper.video.VideoPlayer.2
        public final void onInitComplete(SemMediaPlayer semMediaPlayer, SemMediaPlayer.TrackInfo[] trackInfoArr) {
            VideoPlayer videoPlayer = VideoPlayer.this;
            videoPlayer.mIsPrepared = true;
            int i = 0;
            videoPlayer.mIsPreparing = false;
            WallpaperLogger wallpaperLogger = videoPlayer.mLoggerWrapper;
            if (wallpaperLogger != null) {
                ((WallpaperLoggerImpl) wallpaperLogger).log("VideoPlayer", "onInitComplete!!");
            }
            VideoPlayer.this.getClass();
            Log.d("VideoPlayer", "setLooping() mp = " + semMediaPlayer);
            if (semMediaPlayer == null) {
                Log.w("VideoPlayer", "setLooping() mediaPlayer is null");
            } else {
                try {
                    semMediaPlayer.setLooping(true);
                    semMediaPlayer.setParameter(37001, 1);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Log.e("VideoPlayer", "failed setLooping");
                }
            }
            try {
                FileInputStream fileInputStream = VideoPlayer.this.mFileInputStream;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            View view = VideoPlayer.this.mRootView;
            boolean z = view != null && view.hasWindowFocus();
            boolean z2 = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).mDeviceInteractive;
            boolean isCarUiMode = WallpaperUtils.isCarUiMode(VideoPlayer.this.mContext);
            boolean z3 = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).mIsDreaming;
            StringBuilder sb = new StringBuilder("onInitComplete() p = ");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, VideoPlayer.this.mIsPendingStarted, " , focus = ", z, " , isDeviceInteractive = ");
            sb.append(z2);
            sb.append(" , hasSurface = ");
            sb.append(VideoPlayer.this.mHasSurface);
            sb.append(" , bouncer = ");
            sb.append(VideoPlayer.this.mBouncer);
            sb.append(" , startPosition = ");
            sb.append(VideoPlayer.this.mStartPosition);
            sb.append(" , isCarUiMode = ");
            sb.append(isCarUiMode);
            sb.append(" , isDreaming = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, z3, "VideoPlayer");
            VideoPlayer videoPlayer2 = VideoPlayer.this;
            if (videoPlayer2.mIsPendingStarted) {
                videoPlayer2.mIsPendingStarted = false;
                videoPlayer2.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer2, 300000, 0));
            }
            VideoPlayer videoPlayer3 = VideoPlayer.this;
            if (!videoPlayer3.mHasSurface) {
                WallpaperLogger wallpaperLogger2 = videoPlayer3.mLoggerWrapper;
                if (wallpaperLogger2 != null) {
                    ((WallpaperLoggerImpl) wallpaperLogger2).log("VideoPlayer", "setSurface because it had failed before");
                }
                VideoPlayer videoPlayer4 = VideoPlayer.this;
                videoPlayer4.setSurface(videoPlayer4.mSurface);
            }
            if (!z || VideoPlayer.this.mBouncer || !z2 || (isCarUiMode && z3)) {
                VideoPlayer videoPlayer5 = VideoPlayer.this;
                String videoFileName = WallpaperManager.getInstance(videoPlayer5.mContext).getVideoFileName(WallpaperUtils.sCurrentWhich);
                int i2 = videoPlayer5.mStartPosition;
                if (i2 != 0) {
                    videoPlayer5.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer5, i2, 1));
                    videoPlayer5.mStartPosition = 0;
                } else {
                    SemWallpaperResourcesInfo semWallpaperResourcesInfo = videoPlayer5.mSemWallpaperResourcesInfo;
                    if (semWallpaperResourcesInfo != null && semWallpaperResourcesInfo.isBlackFirstFrame(videoFileName) && videoPlayer5.mBouncer) {
                        int defaultVideoFrameInfo = semWallpaperResourcesInfo.getDefaultVideoFrameInfo(videoFileName);
                        int videoFrameCount = videoPlayer5.getVideoFrameCount();
                        int videoDuration = videoPlayer5.getVideoDuration();
                        if (defaultVideoFrameInfo > 0 && videoFrameCount > 0 && videoFrameCount >= defaultVideoFrameInfo) {
                            i = (int) ((defaultVideoFrameInfo / videoFrameCount) * videoDuration);
                            TooltipPopup$$ExternalSyntheticOutline0.m13m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("initSeekTime: ", i, " , count = ", videoFrameCount, " , requested index = "), defaultVideoFrameInfo, "VideoPlayer");
                        }
                    }
                    videoPlayer5.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer5, i, 1));
                }
                VideoPlayer.this.stopDrawing();
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wallpaper.video.VideoPlayer$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.wallpaper.video.VideoPlayer$2] */
    public VideoPlayer(Context context, View view, SemWallpaperResourcesInfo semWallpaperResourcesInfo, WallpaperLogger wallpaperLogger) {
        this.mContext = context;
        this.mRootView = view;
        this.mLoggerWrapper = wallpaperLogger;
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
        HandlerThread handlerThread = new HandlerThread("VideoPlayer");
        this.mVideoPlayerThread = handlerThread;
        handlerThread.start();
        this.mVideoPlayerThread.setPriority(10);
    }

    public final Bitmap getCurrentFrame() {
        SemMediaPlayer semMediaPlayer = this.mSemMediaPlayer;
        if (semMediaPlayer == null) {
            Log.w("VideoPlayer", "getCurrentFrame() mediaPlayer is null");
            return null;
        }
        try {
            return semMediaPlayer.getCurrentFrame();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.e("VideoPlayer", "failed getCurrentFrame");
            return null;
        }
    }

    public final int getCurrentPosition() {
        SemMediaPlayer semMediaPlayer = this.mSemMediaPlayer;
        if (semMediaPlayer == null) {
            Log.w("VideoPlayer", "getCurrentPosition() mediaPlayer is null");
            return 0;
        }
        try {
            return semMediaPlayer.getCurrentPosition();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.e("VideoPlayer", "failed getCurrentPosition()");
            return 0;
        }
    }

    public final Handler getThreadHandler() {
        HandlerThread handlerThread = this.mVideoPlayerThread;
        if (!handlerThread.isAlive()) {
            ((WallpaperLoggerImpl) this.mLoggerWrapper).log("VideoPlayer", "thread is not alive :" + handlerThread);
            handlerThread.quitSafely();
            HandlerThread handlerThread2 = new HandlerThread("VideoPlayer");
            this.mVideoPlayerThread = handlerThread2;
            handlerThread2.start();
            this.mVideoPlayerThread.setPriority(10);
        }
        return this.mVideoPlayerThread.getThreadHandler();
    }

    public final int getVideoDuration() {
        Integer num = (Integer) this.mMetadataMap.get(9);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public final int getVideoFrameCount() {
        Integer num = (Integer) this.mMetadataMap.get(32);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public final void releaseMediaPlayer(SemMediaPlayer semMediaPlayer) {
        Log.d("VideoPlayer", "releaseMediaPlayer() mp = " + semMediaPlayer);
        if (semMediaPlayer == null) {
            return;
        }
        try {
            this.mIsPrepared = false;
            this.mIsPreparing = false;
            semMediaPlayer.release();
            this.mSemMediaPlayer = null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.e("VideoPlayer", "releaseMediaPlayer() failed stop");
        }
    }

    public final void releaseResource(boolean z) {
        this.mIsRenderingStarted = false;
        if (z) {
            releaseMediaPlayer(this.mSemMediaPlayer);
        } else if (this.mVideoPlayerThread.isAlive()) {
            getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda2(this, 0));
        }
    }

    public final void setDataSource(MediaMetadataRetriever mediaMetadataRetriever, AssetFileDescriptor assetFileDescriptor, String str, Uri uri) {
        if (!TextUtils.isEmpty(str)) {
            mediaMetadataRetriever.setDataSource(str);
            return;
        }
        if (assetFileDescriptor != null) {
            mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
        } else if (uri != null) {
            mediaMetadataRetriever.setDataSource(this.mContext, uri);
        } else {
            Log.w("VideoPlayer", "getVideoScreenSize() file is invalid");
        }
    }

    public final void setSurface(final Surface surface) {
        getThreadHandler().post(new Runnable() { // from class: com.android.systemui.wallpaper.video.VideoPlayer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                VideoPlayer videoPlayer = VideoPlayer.this;
                Surface surface2 = surface;
                videoPlayer.mSurface = surface2;
                if (surface2 != null) {
                    try {
                        if (surface2.isValid()) {
                            videoPlayer.mSemMediaPlayer.setSurface(videoPlayer.mSurface);
                            Log.i("VideoPlayer", "setSurface() success, surface = " + videoPlayer.mSurface);
                            videoPlayer.mHasSurface = true;
                        }
                    } catch (Exception e) {
                        ((WallpaperLoggerImpl) videoPlayer.mLoggerWrapper).log("VideoPlayer", "setSurface: fail to setSurface, surface = " + videoPlayer.mSurface);
                        e.printStackTrace();
                        return;
                    }
                }
                Log.w("VideoPlayer", "setSurface() is null or not valid, surface = " + videoPlayer.mSurface);
                videoPlayer.mHasSurface = false;
            }
        });
    }

    public final void stopDrawing() {
        getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda2(this, 1));
    }

    public final void updateMediaMetadata(AssetFileDescriptor assetFileDescriptor, String str, Uri uri, int i) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                setDataSource(mediaMetadataRetriever, assetFileDescriptor, str, uri);
                this.mMetadataMap.put(Integer.valueOf(i), Integer.valueOf(Integer.parseInt(mediaMetadataRetriever.extractMetadata(i))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }
}
