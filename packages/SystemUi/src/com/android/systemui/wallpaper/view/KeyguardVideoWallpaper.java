package com.android.systemui.wallpaper.view;

import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.LsRune;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.DeviceState;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.video.VideoPlayer;
import com.android.systemui.wallpaper.video.VideoPlayer$$ExternalSyntheticLambda3;
import com.samsung.android.media.SemMediaPlayer;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardVideoWallpaper extends SystemUIWallpaper implements SurfaceHolder.Callback {
    public final Context mContext;
    public final int mCurrentUserId;
    public final DozeParameters mDozeParameters;
    public final HandlerC37081 mHandler;
    public boolean mIsBlurEnabled;
    public boolean mIsCleanUp;
    public boolean mIsPendingSurfaceViewAdd;
    public boolean mIsSurfaceViewAdded;
    public boolean mIsThumbnailViewAdded;
    public final WallpaperLogger mLoggerWrapper;
    public final KeyguardVideoWallpaper$$ExternalSyntheticLambda0 mOnInfoListener;
    public final PluginWallpaperManager mPluginWallpaperMgr;
    public AssetFileDescriptor mRetrieverFd;
    public final View mRootView;
    public final SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public boolean mShowing;
    public int mStartPosition;
    public SurfaceHolder mSurfaceHolder;
    public final PointF mSurfaceScaleInfo;
    public SurfaceView mSurfaceView;
    public String mThemePackage;
    public Drawable mThumbnail;
    public AsyncTaskC37092 mThumbnailLoader;
    public ImageView mThumbnailView;
    public AssetFileDescriptor mVideoFileDescriptor;
    public String mVideoFileName;
    public String mVideoFilePath;
    public Uri mVideoFileUri;
    public final VideoPlayer mVideoPlayer;
    public Point mVideoScreenSize;

    static {
        Debug.semIsProductDev();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.wallpaper.view.KeyguardVideoWallpaper$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.wallpaper.view.KeyguardVideoWallpaper$$ExternalSyntheticLambda0] */
    public KeyguardVideoWallpaper(Context context, String str, String str2, Uri uri, String str3, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, PluginWallpaperManager pluginWallpaperManager, WallpaperLogger wallpaperLogger, Consumer<Boolean> consumer, boolean z, boolean z2, int i, boolean z3) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, false, z, z2);
        this.mVideoFilePath = null;
        this.mVideoFileDescriptor = null;
        this.mRetrieverFd = null;
        this.mVideoFileUri = null;
        boolean z4 = false;
        this.mIsThumbnailViewAdded = false;
        this.mIsPendingSurfaceViewAdd = false;
        this.mIsSurfaceViewAdded = false;
        this.mIsCleanUp = false;
        this.mStartPosition = 0;
        this.mIsBlurEnabled = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.view.KeyguardVideoWallpaper.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                VideoPlayer videoPlayer;
                int i2 = message.what;
                if (i2 != 1000) {
                    if (i2 == 1001 && (videoPlayer = KeyguardVideoWallpaper.this.mVideoPlayer) != null) {
                        videoPlayer.stopDrawing();
                        return;
                    }
                    return;
                }
                TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("removeThumbnailView, position : "), KeyguardVideoWallpaper.this.mStartPosition, "KeyguardVideoWallpaper");
                KeyguardVideoWallpaper keyguardVideoWallpaper = KeyguardVideoWallpaper.this;
                keyguardVideoWallpaper.drawVideo(keyguardVideoWallpaper.hasWindowFocus() && KeyguardVideoWallpaper.this.mShowing && WallpaperUtils.sDrawState, false);
                KeyguardVideoWallpaper keyguardVideoWallpaper2 = KeyguardVideoWallpaper.this;
                ImageView imageView = keyguardVideoWallpaper2.mThumbnailView;
                if (imageView != null && keyguardVideoWallpaper2.mIsThumbnailViewAdded) {
                    keyguardVideoWallpaper2.removeView(imageView);
                    keyguardVideoWallpaper2.mIsThumbnailViewAdded = false;
                }
                KeyguardVideoWallpaper keyguardVideoWallpaper3 = KeyguardVideoWallpaper.this;
                if (keyguardVideoWallpaper3.mStartPosition != 0) {
                    keyguardVideoWallpaper3.mThumbnail = null;
                    keyguardVideoWallpaper3.mStartPosition = 0;
                    keyguardVideoWallpaper3.updateDrawable(false);
                }
            }
        };
        this.mOnInfoListener = new SemMediaPlayer.OnInfoListener() { // from class: com.android.systemui.wallpaper.view.KeyguardVideoWallpaper$$ExternalSyntheticLambda0
            public final boolean onInfo(SemMediaPlayer semMediaPlayer, int i2, int i3) {
                KeyguardVideoWallpaper keyguardVideoWallpaper = KeyguardVideoWallpaper.this;
                keyguardVideoWallpaper.getClass();
                Log.i("KeyguardVideoWallpaper", "onInfo: i = " + i2 + " , i1 = " + i3);
                if (i2 != 3) {
                    return false;
                }
                keyguardVideoWallpaper.mVideoPlayer.mIsRenderingStarted = true;
                keyguardVideoWallpaper.mHandler.sendMessageAtFrontOfQueue(keyguardVideoWallpaper.mHandler.obtainMessage(1000));
                return false;
            }
        };
        this.mContext = context;
        this.mCurrentUserId = i;
        this.mPluginWallpaperMgr = pluginWallpaperManager;
        this.mLoggerWrapper = wallpaperLogger;
        this.mThemePackage = str2;
        this.mVideoFileName = str3;
        this.mVideoFileUri = uri;
        this.mDozeParameters = dozeParameters;
        View rootView = getRootView();
        this.mRootView = rootView;
        if (this.mIsKeyguardShowing && !this.mOccluded) {
            z4 = true;
        }
        this.mShowing = z4;
        SemWallpaperResourcesInfo semWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
        VideoPlayer videoPlayer = new VideoPlayer(context, rootView, semWallpaperResourcesInfo, wallpaperLogger);
        this.mVideoPlayer = videoPlayer;
        this.mBouncer = z3;
        videoPlayer.mBouncer = z3;
        this.mSurfaceScaleInfo = new PointF(1.0f, 1.0f);
        this.mVideoFilePath = str;
        this.mVideoFileDescriptor = getVideoFileDescriptor();
        this.mRetrieverFd = getVideoFileDescriptor();
        StringBuilder sb = new StringBuilder("KeyguardVideoWallpaper: path = ");
        sb.append(this.mVideoFilePath);
        sb.append(" , fd = ");
        sb.append(this.mVideoFileDescriptor);
        sb.append(", fileName = ");
        sb.append(this.mVideoFileName);
        sb.append(" , focus = ");
        sb.append(hasWindowFocus());
        sb.append(", mIsKeyguardShowing = ");
        sb.append(this.mIsKeyguardShowing);
        sb.append(" , mOccluded = ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mOccluded, "KeyguardVideoWallpaper");
        if (this.mShowing) {
            Log.d("KeyguardVideoWallpaper", "Showing state");
            initSurfaceViewIfNeeded();
        }
        WallpaperResultCallback wallpaperResultCallback2 = this.mWallpaperResultCallback;
        if (wallpaperResultCallback2 != null) {
            wallpaperResultCallback2.onPreviewReady();
        }
    }

    public final void addSurfaceViewIfNeeded() {
        initSurfaceViewIfNeeded();
        if (this.mIsSurfaceViewAdded) {
            return;
        }
        addView(this.mSurfaceView);
        this.mIsSurfaceViewAdded = true;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        Log.i("KeyguardVideoWallpaper", "cleanUp: ");
        this.mIsCleanUp = true;
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(this);
            if (this.mSurfaceHolder.getSurface() != null) {
                this.mSurfaceHolder.getSurface().release();
            }
            this.mSurfaceHolder = null;
        }
        ImageView imageView = this.mThumbnailView;
        if (imageView != null) {
            imageView.setBackground(null);
        }
        if (this.mVideoPlayer != null) {
            releaseMediaPlayer();
            this.mVideoPlayer.mVideoPlayerThread.quitSafely();
        }
        removeAllViews();
        this.mIsThumbnailViewAdded = false;
        this.mIsSurfaceViewAdded = false;
        this.mThumbnail = null;
    }

    public final void drawVideo(boolean z, boolean z2) {
        if (this.mVideoPlayer == null) {
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("drawVideo() ", z, ", force = ", z2, ", mDozeParameters.shouldControlScreenOff() = "), this.mDozeParameters.mControlScreenOffAnimation, "KeyguardVideoWallpaper");
        if (z) {
            removeMessages(1001);
            VideoPlayer videoPlayer = this.mVideoPlayer;
            videoPlayer.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer, 300000, 0));
        } else if (z2 || !WallpaperUtils.isAODShowLockWallpaperEnabled() || !this.mDozeParameters.mControlScreenOffAnimation) {
            this.mVideoPlayer.stopDrawing();
        } else if (((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).isDynamicWallpaperEnabled()) {
            Log.d("KeyguardVideoWallpaper", "drawVideo: isAODShowLockWallpaperEnabled. Delay stopDrawing until getting video frame is started.");
        } else {
            if (hasMessages(1001)) {
                return;
            }
            sendEmptyMessageDelayed(1001, 800L);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final Bitmap getCapturedWallpaper() {
        ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "getCapturedWallpaper : stop video because need to get current frame. position = " + this.mVideoPlayer.getCurrentPosition());
        this.mVideoPlayer.stopDrawing();
        final Bitmap[] bitmapArr = {null};
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder == null || surfaceHolder.getSurface() == null || !this.mSurfaceHolder.getSurface().isValid()) {
            bitmapArr[0] = this.mVideoPlayer.getCurrentFrame();
        } else {
            final int width = (int) (getWidth() * this.mSurfaceScaleInfo.x);
            final int height = (int) (getHeight() * this.mSurfaceScaleInfo.y);
            bitmapArr[0] = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            final HandlerThread handlerThread = new HandlerThread("PixelCopy");
            handlerThread.start();
            synchronized (handlerThread) {
                PixelCopy.request(this.mSurfaceHolder.getSurface(), bitmapArr[0], new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.android.systemui.wallpaper.view.KeyguardVideoWallpaper$$ExternalSyntheticLambda1
                    @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                    public final void onPixelCopyFinished(int i) {
                        VideoPlayer videoPlayer;
                        KeyguardVideoWallpaper keyguardVideoWallpaper = KeyguardVideoWallpaper.this;
                        Bitmap[] bitmapArr2 = bitmapArr;
                        int i2 = width;
                        int i3 = height;
                        HandlerThread handlerThread2 = handlerThread;
                        keyguardVideoWallpaper.getClass();
                        Log.i("KeyguardVideoWallpaper", "copy result = " + i);
                        if (i != 0 && (videoPlayer = keyguardVideoWallpaper.mVideoPlayer) != null) {
                            Bitmap currentFrame = videoPlayer.getCurrentFrame();
                            if (currentFrame == null) {
                                Bitmap videoFrame = WallpaperUtils.getVideoFrame(keyguardVideoWallpaper.mContext, keyguardVideoWallpaper.mRetrieverFd, keyguardVideoWallpaper.mVideoFilePath, keyguardVideoWallpaper.mVideoFileUri, keyguardVideoWallpaper.mVideoPlayer.getCurrentPosition() * 1000);
                                if (videoFrame != null) {
                                    bitmapArr2[0] = Bitmap.createScaledBitmap(videoFrame, i2, i3, true);
                                }
                            } else {
                                bitmapArr2[0] = currentFrame;
                            }
                        }
                        synchronized (handlerThread2) {
                            handlerThread2.notify();
                        }
                        handlerThread2.quitSafely();
                    }
                }, handlerThread.getThreadHandler());
                try {
                    handlerThread.wait(200L);
                } catch (InterruptedException unused) {
                    Log.w("KeyguardVideoWallpaper", "Failed to wait");
                    handlerThread.quitSafely();
                }
            }
        }
        return bitmapArr[0];
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final Bitmap getCapturedWallpaperForBlur() {
        Bitmap bitmap;
        VideoPlayer videoPlayer = this.mVideoPlayer;
        if (videoPlayer != null) {
            videoPlayer.stopDrawing();
            bitmap = this.mVideoPlayer.getCurrentFrame();
            ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "getCapturedWallpaperForBlur : bitmap = " + bitmap);
        } else {
            bitmap = null;
        }
        return bitmap == null ? WallpaperUtils.getVideoFrame(this.mContext, this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, 0L) : bitmap;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final int getCurrentPosition() {
        VideoPlayer videoPlayer = this.mVideoPlayer;
        if (videoPlayer != null) {
            return videoPlayer.getCurrentPosition();
        }
        return 0;
    }

    public final int getDefaultFrameMillis() {
        SemWallpaperResourcesInfo semWallpaperResourcesInfo = this.mSemWallpaperResourcesInfo;
        int i = 0;
        if (semWallpaperResourcesInfo == null) {
            return 0;
        }
        if (!semWallpaperResourcesInfo.isBlackFirstFrame(this.mVideoFileName)) {
            if (!LsRune.WALLPAPER_MAISON_MARGIELA_EDITION) {
                return 0;
            }
            Context context = this.mContext;
            boolean z = WallpaperUtils.mIsEmergencyMode;
            String videoFilePath = WallpaperManager.getInstance(context).getVideoFilePath(WallpaperUtils.sCurrentWhich);
            if (!(TextUtils.isEmpty(videoFilePath) ? false : videoFilePath.contains("/prism/etc/common/"))) {
                return 0;
            }
            this.mVideoPlayer.updateMediaMetadata(this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, 9);
            int videoDuration = this.mVideoPlayer.getVideoDuration();
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("getDefaultFrameMillis(omc): ", videoDuration, "KeyguardVideoWallpaper");
            return videoDuration;
        }
        int defaultVideoFrameInfo = this.mSemWallpaperResourcesInfo.getDefaultVideoFrameInfo(this.mVideoFileName);
        int videoFrameCount = this.mVideoPlayer.getVideoFrameCount();
        if (videoFrameCount == 0) {
            this.mVideoPlayer.updateMediaMetadata(this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, 32);
            videoFrameCount = this.mVideoPlayer.getVideoFrameCount();
        }
        int videoDuration2 = this.mVideoPlayer.getVideoDuration();
        if (videoDuration2 == 0) {
            this.mVideoPlayer.updateMediaMetadata(this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, 9);
            videoDuration2 = this.mVideoPlayer.getVideoDuration();
        }
        if (defaultVideoFrameInfo > 0 && videoFrameCount > 0 && videoFrameCount >= defaultVideoFrameInfo) {
            i = (int) ((defaultVideoFrameInfo / videoFrameCount) * videoDuration2);
        }
        TooltipPopup$$ExternalSyntheticOutline0.m13m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("getDefaultFrameMillis: ", i, " , count = ", videoFrameCount, " , requested index = "), defaultVideoFrameInfo, "KeyguardVideoWallpaper");
        return i;
    }

    public final AssetFileDescriptor getVideoFileDescriptor() {
        Context context = this.mContext;
        String str = this.mThemePackage;
        String str2 = this.mVideoFileName;
        boolean z = WallpaperUtils.mIsEmergencyMode;
        if (TextUtils.isEmpty(str2) || !"com.samsung.android.wallpaper.res".equals(str)) {
            str2 = "video_1.mp4";
        }
        AssetFileDescriptor videoFDFromPackage = WallpaperUtils.getVideoFDFromPackage(context, str, str2);
        if (isDefaultVideoWallpaper() && TextUtils.isEmpty(this.mVideoFilePath) && videoFDFromPackage == null) {
            int i = WallpaperUtils.sCurrentWhich;
            String defaultVideoWallpaperFileName = this.mSemWallpaperResourcesInfo.getDefaultVideoWallpaperFileName(i);
            if (defaultVideoWallpaperFileName == null || defaultVideoWallpaperFileName.equals(this.mVideoFileName)) {
                ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "This file was already set. : " + this.mVideoFileName);
            } else {
                ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "old file = " + this.mVideoFileName + ", default file name :" + this.mVideoFileName + " , which = " + i);
                this.mVideoFileName = defaultVideoWallpaperFileName;
                videoFDFromPackage = WallpaperUtils.getVideoFDFromPackage(this.mContext, "com.samsung.android.wallpaper.res", defaultVideoWallpaperFileName);
                if (videoFDFromPackage != null) {
                    WallpaperManager.getInstance(this.mContext).setVideoLockscreenWallpaper(null, "com.samsung.android.wallpaper.res", this.mVideoFileName, this.mCurrentUserId, i);
                } else {
                    ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "Can't find resources or fail to openFD : " + this.mVideoFileName);
                }
            }
        }
        return videoFDFromPackage;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final Bitmap getWallpaperBitmap() {
        int i = this.mStartPosition;
        if (i == 0) {
            i = getDefaultFrameMillis();
        }
        try {
            VideoPlayer videoPlayer = this.mVideoPlayer;
            return (videoPlayer == null || !videoPlayer.mIsRenderingStarted) ? WallpaperUtils.getVideoFrame(this.mContext, this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, i * 1000) : getCapturedWallpaper();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final void initSurfaceViewIfNeeded() {
        StringBuilder sb = new StringBuilder("initSurfaceViewIfNeeded: mSurfaceView == null? : ");
        sb.append(this.mSurfaceView == null);
        sb.append(" , showing = ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mShowing, "KeyguardVideoWallpaper");
        if (this.mSurfaceView == null) {
            SurfaceView surfaceView = new SurfaceView(this.mContext, null, 0, 0, true);
            this.mSurfaceView = surfaceView;
            SurfaceHolder holder = surfaceView.getHolder();
            this.mSurfaceHolder = holder;
            holder.addCallback(this);
        }
        if (this.mThumbnailView == null) {
            this.mThumbnailView = new ImageView(this.mContext);
        }
    }

    public final boolean isDefaultVideoWallpaper() {
        return (WallpaperManager.getInstance(this.mContext).getDefaultWallpaperType(WallpaperUtils.sCurrentWhich) == 8 && !TextUtils.isEmpty(this.mVideoFileName)) && !((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).isVideoWallpaperEnabled();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:26|(10:27|28|29|30|31|32|33|34|36|37)|(6:42|43|44|46|47|48)|63|64|65|46|47|48) */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0080, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0081, code lost:
    
        r8 = r5;
        r5 = r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadMediaPlayer(boolean z) {
        String str;
        String str2;
        Exception e;
        String str3;
        int i;
        int i2;
        SurfaceHolder surfaceHolder;
        Log.i("KeyguardVideoWallpaper", "mVideoFileDescriptor = " + this.mVideoFileDescriptor + " , mRetrieverFd = " + this.mRetrieverFd);
        if (this.mVideoScreenSize == null || z) {
            VideoPlayer videoPlayer = this.mVideoPlayer;
            AssetFileDescriptor assetFileDescriptor = this.mRetrieverFd;
            String str4 = this.mVideoFilePath;
            Uri uri = this.mVideoFileUri;
            videoPlayer.getClass();
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            int i3 = 640;
            int i4 = VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE;
            try {
                try {
                    videoPlayer.setDataSource(mediaMetadataRetriever, assetFileDescriptor, str4, uri);
                    str3 = mediaMetadataRetriever.extractMetadata(18);
                    try {
                        str = mediaMetadataRetriever.extractMetadata(19);
                        try {
                            str2 = mediaMetadataRetriever.extractMetadata(24);
                            try {
                            } catch (Exception e2) {
                                e = e2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            str2 = "";
                        }
                    } catch (Exception e4) {
                        str2 = "";
                        e = e4;
                        str = str2;
                    }
                } finally {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Exception e6) {
                str = "";
                str2 = str;
                e = e6;
                str3 = str2;
            }
            if (!DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD.equals(str2) && !"270".equals(str2)) {
                i = Integer.parseInt(str3);
                try {
                    i2 = Integer.parseInt(str);
                } catch (Exception e7) {
                    e = e7;
                    i3 = i;
                    e.printStackTrace();
                    Log.e("VideoPlayer", "getVideoScreenSize() occur exception");
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                    i = i3;
                    i2 = i4;
                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("getVideoScreenSize() w = ", str3, ", h = ", str, ", r = ");
                    m87m.append(str2);
                    Log.d("VideoPlayer", m87m.toString());
                    this.mVideoScreenSize = new Point(i, i2);
                    if (isDefaultVideoWallpaper()) {
                    }
                    this.mVideoPlayer.releaseResource(z);
                    final VideoPlayer videoPlayer2 = this.mVideoPlayer;
                    final AssetFileDescriptor assetFileDescriptor2 = this.mVideoFileDescriptor;
                    final String str5 = this.mVideoFilePath;
                    final Uri uri2 = this.mVideoFileUri;
                    SurfaceHolder surfaceHolder2 = this.mSurfaceHolder;
                    final Surface surface = surfaceHolder2 == null ? null : surfaceHolder2.getSurface();
                    final KeyguardVideoWallpaper$$ExternalSyntheticLambda0 keyguardVideoWallpaper$$ExternalSyntheticLambda0 = this.mOnInfoListener;
                    videoPlayer2.getThreadHandler().post(new Runnable() { // from class: com.android.systemui.wallpaper.video.VideoPlayer$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VideoPlayer videoPlayer3 = VideoPlayer.this;
                            AssetFileDescriptor assetFileDescriptor3 = assetFileDescriptor2;
                            String str6 = str5;
                            Uri uri3 = uri2;
                            Surface surface2 = surface;
                            SemMediaPlayer.OnInfoListener onInfoListener = keyguardVideoWallpaper$$ExternalSyntheticLambda0;
                            videoPlayer3.getClass();
                            Log.d("VideoPlayer", "initFile() filePath = " + str6 + ", fd = " + assetFileDescriptor3 + ", uri = " + uri3);
                            videoPlayer3.mIsRenderingStarted = false;
                            videoPlayer3.mIsPrepared = false;
                            videoPlayer3.mIsPreparing = true;
                            SemMediaPlayer semMediaPlayer = new SemMediaPlayer();
                            videoPlayer3.mSemMediaPlayer = semMediaPlayer;
                            semMediaPlayer.setParameter(35004, 1);
                            videoPlayer3.mSemMediaPlayer.setParameter(37000, 1);
                            if (surface2 != null) {
                                videoPlayer3.setSurface(surface2);
                            }
                            videoPlayer3.mSemMediaPlayer.setOnInitCompleteListener(videoPlayer3.mOnInitCompleteListener);
                            if (onInfoListener != null) {
                                videoPlayer3.mSemMediaPlayer.setOnInfoListener(onInfoListener);
                            }
                            try {
                                if (!TextUtils.isEmpty(str6)) {
                                    FileInputStream fileInputStream = new FileInputStream(new File(str6));
                                    videoPlayer3.mFileInputStream = fileInputStream;
                                    videoPlayer3.mSemMediaPlayer.init(fileInputStream.getFD());
                                    return;
                                }
                                if (assetFileDescriptor3 != null) {
                                    videoPlayer3.mSemMediaPlayer.init(assetFileDescriptor3.getFileDescriptor(), assetFileDescriptor3.getStartOffset(), assetFileDescriptor3.getLength());
                                } else if (uri3 != null) {
                                    videoPlayer3.mSemMediaPlayer.init(videoPlayer3.mContext, uri3);
                                } else {
                                    Log.e("VideoPlayer", "video file is invalid");
                                }
                            } catch (IOException e9) {
                                e9.printStackTrace();
                                Log.e("VideoPlayer", "mSemMediaPlayer is not exist");
                            }
                        }
                    });
                    surfaceHolder = this.mSurfaceHolder;
                    if (surfaceHolder != null) {
                    }
                    updateDrawable(z);
                }
                StringBuilder m87m2 = AbstractC0866xb1ce8deb.m87m("getVideoScreenSize() w = ", str3, ", h = ", str, ", r = ");
                m87m2.append(str2);
                Log.d("VideoPlayer", m87m2.toString());
                this.mVideoScreenSize = new Point(i, i2);
            }
            i2 = Integer.parseInt(str3);
            i = Integer.parseInt(str);
            StringBuilder m87m22 = AbstractC0866xb1ce8deb.m87m("getVideoScreenSize() w = ", str3, ", h = ", str, ", r = ");
            m87m22.append(str2);
            Log.d("VideoPlayer", m87m22.toString());
            this.mVideoScreenSize = new Point(i, i2);
        }
        if (isDefaultVideoWallpaper()) {
            if (this.mVideoPlayer.getVideoFrameCount() == 0 || z) {
                this.mVideoPlayer.updateMediaMetadata(this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, 32);
            }
            if (this.mVideoPlayer.getVideoDuration() == 0 || z) {
                this.mVideoPlayer.updateMediaMetadata(this.mRetrieverFd, this.mVideoFilePath, this.mVideoFileUri, 9);
            }
        }
        this.mVideoPlayer.releaseResource(z);
        final VideoPlayer videoPlayer22 = this.mVideoPlayer;
        final AssetFileDescriptor assetFileDescriptor22 = this.mVideoFileDescriptor;
        final String str52 = this.mVideoFilePath;
        final Uri uri22 = this.mVideoFileUri;
        SurfaceHolder surfaceHolder22 = this.mSurfaceHolder;
        final Surface surface2 = surfaceHolder22 == null ? null : surfaceHolder22.getSurface();
        final KeyguardVideoWallpaper$$ExternalSyntheticLambda0 keyguardVideoWallpaper$$ExternalSyntheticLambda02 = this.mOnInfoListener;
        videoPlayer22.getThreadHandler().post(new Runnable() { // from class: com.android.systemui.wallpaper.video.VideoPlayer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VideoPlayer videoPlayer3 = VideoPlayer.this;
                AssetFileDescriptor assetFileDescriptor3 = assetFileDescriptor22;
                String str6 = str52;
                Uri uri3 = uri22;
                Surface surface22 = surface2;
                SemMediaPlayer.OnInfoListener onInfoListener = keyguardVideoWallpaper$$ExternalSyntheticLambda02;
                videoPlayer3.getClass();
                Log.d("VideoPlayer", "initFile() filePath = " + str6 + ", fd = " + assetFileDescriptor3 + ", uri = " + uri3);
                videoPlayer3.mIsRenderingStarted = false;
                videoPlayer3.mIsPrepared = false;
                videoPlayer3.mIsPreparing = true;
                SemMediaPlayer semMediaPlayer = new SemMediaPlayer();
                videoPlayer3.mSemMediaPlayer = semMediaPlayer;
                semMediaPlayer.setParameter(35004, 1);
                videoPlayer3.mSemMediaPlayer.setParameter(37000, 1);
                if (surface22 != null) {
                    videoPlayer3.setSurface(surface22);
                }
                videoPlayer3.mSemMediaPlayer.setOnInitCompleteListener(videoPlayer3.mOnInitCompleteListener);
                if (onInfoListener != null) {
                    videoPlayer3.mSemMediaPlayer.setOnInfoListener(onInfoListener);
                }
                try {
                    if (!TextUtils.isEmpty(str6)) {
                        FileInputStream fileInputStream = new FileInputStream(new File(str6));
                        videoPlayer3.mFileInputStream = fileInputStream;
                        videoPlayer3.mSemMediaPlayer.init(fileInputStream.getFD());
                        return;
                    }
                    if (assetFileDescriptor3 != null) {
                        videoPlayer3.mSemMediaPlayer.init(assetFileDescriptor3.getFileDescriptor(), assetFileDescriptor3.getStartOffset(), assetFileDescriptor3.getLength());
                    } else if (uri3 != null) {
                        videoPlayer3.mSemMediaPlayer.init(videoPlayer3.mContext, uri3);
                    } else {
                        Log.e("VideoPlayer", "video file is invalid");
                    }
                } catch (IOException e9) {
                    e9.printStackTrace();
                    Log.e("VideoPlayer", "mSemMediaPlayer is not exist");
                }
            }
        });
        surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null && surfaceHolder.getSurfaceFrame() != null) {
            updateSurfaceScale(this.mSurfaceHolder.getSurfaceFrame().width(), this.mSurfaceHolder.getSurfaceFrame().height());
        }
        updateDrawable(z);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("onConfigurationChanged: "), configuration.orientation, "KeyguardVideoWallpaper");
        super.onConfigurationChanged(configuration);
        if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
            updateBlurState(this.mIsBlurEnabled);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onFaceAuthError() {
        Log.i("KeyguardVideoWallpaper", "onFaceAuthError(), pause video");
        drawVideo(false, true);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onFingerprintAuthSuccess(boolean z) {
        Log.i("KeyguardVideoWallpaper", "onFingerprintAuthSuccess()");
        drawVideo(false, true);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
        int defaultFrameMillis;
        VideoPlayer videoPlayer;
        this.mBouncer = z;
        if (z && !WallpaperUtils.isSubDisplay()) {
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && this.mVideoPlayer != null && isDefaultVideoWallpaper() && (defaultFrameMillis = getDefaultFrameMillis()) > 0 && (videoPlayer = this.mVideoPlayer) != null) {
                videoPlayer.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer, defaultFrameMillis, 1));
            }
            drawVideo(false, true);
        }
        VideoPlayer videoPlayer2 = this.mVideoPlayer;
        if (videoPlayer2 != null) {
            videoPlayer2.mBouncer = this.mBouncer;
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onKeyguardShowing(boolean z) {
        this.mIsKeyguardShowing = z;
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onKeyguardShowing = ", z, "KeyguardVideoWallpaper");
        VideoPlayer videoPlayer = this.mVideoPlayer;
        if (videoPlayer == null) {
            return;
        }
        if (!z) {
            if (WallpaperUtils.isAODShowLockWallpaperEnabled()) {
                return;
            }
            releaseMediaPlayer();
        } else {
            if (videoPlayer.mIsPrepared || videoPlayer.mIsPreparing) {
                return;
            }
            loadMediaPlayer(false);
            if (this.mIsPendingSurfaceViewAdd) {
                return;
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                addSurfaceViewIfNeeded();
            } else {
                post(new Runnable() { // from class: com.android.systemui.wallpaper.view.KeyguardVideoWallpaper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardVideoWallpaper.this.addSurfaceViewIfNeeded();
                    }
                });
            }
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onLayout called : ", i, " , ", i2, " , "), i3, " , ", i4, "KeyguardVideoWallpaper");
        if (this.mIsPendingSurfaceViewAdd && WallpaperUtils.isMainScreenRatio(getWidth(), getHeight())) {
            this.mIsPendingSurfaceViewAdd = false;
            if (!this.mShowing || this.mIsCleanUp) {
                return;
            }
            addSurfaceViewIfNeeded();
            Log.d("KeyguardVideoWallpaper", "SurfaceView is added, visibility = " + this.mSurfaceView.getVisibility());
            this.mSurfaceView.setVisibility(8);
            this.mSurfaceView.setVisibility(0);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onPause() {
        VideoPlayer videoPlayer;
        VideoPlayer videoPlayer2;
        boolean z;
        Log.i("KeyguardVideoWallpaper", "onPause: showing = " + this.mShowing + " , focus = " + hasWindowFocus() + " , mBouncer = " + this.mBouncer);
        if (WallpaperUtils.isAODShowLockWallpaperEnabled()) {
            if (this.mDozeParameters.mControlScreenOffAnimation || (videoPlayer = this.mVideoPlayer) == null) {
                return;
            }
            videoPlayer.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer, 0, 1));
            return;
        }
        if (this.mShowing) {
            if (!isDefaultVideoWallpaper() || !(z = LsRune.WALLPAPER_VIDEO_PLAY_RANDOM_POSITION)) {
                if ((LsRune.WALLPAPER_SUB_WATCHFACE || (hasWindowFocus() && !this.mBouncer)) && (videoPlayer2 = this.mVideoPlayer) != null) {
                    videoPlayer2.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer2, 0, 1));
                    return;
                }
                return;
            }
            VideoPlayer videoPlayer3 = this.mVideoPlayer;
            if (!videoPlayer3.mHasSurface) {
                if (videoPlayer3 != null) {
                    videoPlayer3.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer3, 0, 1));
                }
            } else if (isDefaultVideoWallpaper() && z) {
                int videoDuration = this.mVideoPlayer.getVideoDuration();
                Log.d("KeyguardVideoWallpaper", "videoDuration : " + videoDuration);
                int nextInt = videoDuration > 0 ? new Random().nextInt(videoDuration) : 0;
                VideoPlayer videoPlayer4 = this.mVideoPlayer;
                if (videoPlayer4 != null) {
                    videoPlayer4.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer4, nextInt, 1));
                }
            }
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onUnlock() {
        drawVideo(false, true);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mShowing = (i == 0 && this.mIsKeyguardShowing) || (this.mUpdateMonitor.mKeyguardShowing && WallpaperUtils.isAODShowLockWallpaperEnabled());
        WallpaperLogger wallpaperLogger = this.mLoggerWrapper;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("onVisibilityChanged: ", i, " , showingAndNotOccluded = ");
        m1m.append(this.mIsKeyguardShowing);
        m1m.append(" , showing = ");
        m1m.append(this.mUpdateMonitor.mKeyguardShowing);
        m1m.append(", mIsSurfaceViewAdded = ");
        m1m.append(this.mIsSurfaceViewAdded);
        m1m.append(" view = ");
        m1m.append(view);
        ((WallpaperLoggerImpl) wallpaperLogger).log("KeyguardVideoWallpaper", m1m.toString());
        if (!this.mShowing || this.mIsCleanUp) {
            if (this.mIsThumbnailViewAdded) {
                removeView(this.mThumbnailView);
                this.mIsThumbnailViewAdded = false;
            }
            this.mIsPendingSurfaceViewAdd = false;
            releaseMediaPlayer();
            if (this.mIsSurfaceViewAdded) {
                removeView(this.mSurfaceView);
                this.mIsSurfaceViewAdded = false;
                return;
            }
            return;
        }
        VideoPlayer videoPlayer = this.mVideoPlayer;
        if (videoPlayer != null && !videoPlayer.mIsPrepared && !videoPlayer.mIsPreparing) {
            loadMediaPlayer(false);
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && !WallpaperUtils.isSubDisplay() && !WallpaperUtils.isMainScreenRatio(getWidth(), getHeight())) {
            this.mIsPendingSurfaceViewAdd = true;
        }
        if (this.mIsPendingSurfaceViewAdd) {
            return;
        }
        addSurfaceViewIfNeeded();
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        int defaultFrameMillis;
        VideoPlayer videoPlayer;
        super.onWindowFocusChanged(z);
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("hasWindowFocus = ", z, " , state = ");
        m49m.append(WallpaperUtils.sDrawState);
        m49m.append(", blur = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(m49m, this.mIsBlurEnabled, "KeyguardVideoWallpaper");
        boolean z2 = this.mShowing && WallpaperUtils.sDrawState;
        if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
            z2 = z2 && !this.mIsBlurEnabled;
        }
        if (z) {
            drawVideo(z2, false);
            return;
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && !WallpaperUtils.isSubDisplay() && this.mVideoPlayer != null && isDefaultVideoWallpaper() && (defaultFrameMillis = getDefaultFrameMillis()) > 0 && (videoPlayer = this.mVideoPlayer) != null) {
            videoPlayer.getThreadHandler().post(new VideoPlayer$$ExternalSyntheticLambda3(videoPlayer, defaultFrameMillis, 1));
        }
        drawVideo(false, true);
    }

    public final void releaseMediaPlayer() {
        Log.d("KeyguardVideoWallpaper", "releaseMediaPlayer()");
        this.mVideoPlayer.releaseResource(false);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void setStartPosition(int i) {
        this.mStartPosition = i;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("setStartPosition: ", i, "KeyguardVideoWallpaper");
        VideoPlayer videoPlayer = this.mVideoPlayer;
        if (videoPlayer != null) {
            videoPlayer.mStartPosition = i;
            updateDrawable(true);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void setTransitionAnimationListener(KeyguardWallpaperController.C36634 c36634) {
        this.mTransitionAnimationListener = c36634;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        WallpaperLogger wallpaperLogger = this.mLoggerWrapper;
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("surfaceChanged: w = ", i2, ", h = ", i3, " , showing = ");
        m45m.append(this.mShowing);
        m45m.append(" , surface = ");
        m45m.append(surfaceHolder.getSurface());
        ((WallpaperLoggerImpl) wallpaperLogger).log("KeyguardVideoWallpaper", m45m.toString());
        updateSurfaceScale(i2, i3);
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "surfaceCreated: shoiwng = " + this.mShowing + " , frame = " + surfaceHolder.getSurfaceFrame() + ", prepared = " + this.mVideoPlayer.mIsPrepared + " , preparing = " + this.mVideoPlayer.mIsPreparing + " , focus = " + hasWindowFocus() + " , surface = " + surfaceHolder.getSurface());
        if (hasWindowFocus()) {
            drawVideo(this.mShowing && WallpaperUtils.sDrawState, false);
        }
        VideoPlayer videoPlayer = this.mVideoPlayer;
        if (!videoPlayer.mIsPrepared && !videoPlayer.mIsPreparing) {
            loadMediaPlayer(false);
        }
        if (this.mIsThumbnailViewAdded) {
            removeView(this.mThumbnailView);
            this.mIsThumbnailViewAdded = false;
        }
        ImageView imageView = this.mThumbnailView;
        if (imageView != null) {
            addView(imageView);
            this.mIsThumbnailViewAdded = true;
        }
        this.mVideoPlayer.setSurface(surfaceHolder.getSurface());
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        ((WallpaperLoggerImpl) this.mLoggerWrapper).log("KeyguardVideoWallpaper", "surfaceDestroyed: shoiwng = " + this.mShowing + " , surface = " + surfaceHolder.getSurface());
        if (this.mUpdateMonitor.mKeyguardShowing && WallpaperUtils.isAODShowLockWallpaperEnabled()) {
            releaseMediaPlayer();
        }
        this.mVideoPlayer.setSurface(null);
        this.mStartPosition = 0;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        int i = LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich;
        this.mVideoFileName = WallpaperManager.getInstance(this.mContext).getVideoFileName(i);
        if (((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).isDynamicWallpaperEnabled()) {
            if (((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).getWallpaperUri() != null) {
                this.mVideoFilePath = null;
                this.mVideoFileUri = ((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).getWallpaperUri();
            } else {
                this.mVideoFilePath = ((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).getWallpaperPath();
                this.mVideoFileUri = null;
            }
            this.mThemePackage = "";
        } else {
            this.mVideoFilePath = WallpaperManager.getInstance(this.mContext).getVideoFilePath(i);
            this.mThemePackage = WallpaperManager.getInstance(this.mContext).getVideoPackage(i);
            this.mVideoFileUri = null;
        }
        if (LsRune.KEYGUARD_FBE && !this.mUpdateMonitor.isUserUnlocked(this.mCurrentUserId)) {
            int screenId = PluginWallpaperManager.getScreenId(i);
            if (((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).isFbeWallpaperAvailable(screenId)) {
                this.mVideoFilePath = ((PluginWallpaperManagerImpl) this.mPluginWallpaperMgr).getFbeWallpaperPath(screenId);
                this.mThemePackage = "";
            }
        }
        Log.d("KeyguardVideoWallpaper", "update new video wallpaper! path = " + this.mVideoFilePath + ", pkg = " + this.mThemePackage + " , fileName = " + this.mVideoFileName + " , dls uri = " + this.mVideoFileUri);
        this.mVideoFileDescriptor = getVideoFileDescriptor();
        this.mRetrieverFd = getVideoFileDescriptor();
        ImageView imageView = this.mThumbnailView;
        if (imageView != null) {
            imageView.setBackground(null);
        }
        loadMediaPlayer(true);
        drawVideo(WallpaperUtils.sDrawState, false);
        WallpaperResultCallback wallpaperResultCallback = this.mWallpaperResultCallback;
        if (wallpaperResultCallback != null) {
            wallpaperResultCallback.onPreviewReady();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateBlurState(boolean z) {
        if (this.mIsBlurEnabled != z) {
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateBlurState: b = ", z, ", f = ");
            m49m.append(hasWindowFocus());
            m49m.append(", s = ");
            NotificationListener$$ExternalSyntheticOutline0.m123m(m49m, WallpaperUtils.sDrawState, "KeyguardVideoWallpaper");
            this.mIsBlurEnabled = z;
            drawVideo(!z && hasWindowFocus() && WallpaperUtils.sDrawState, false);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateDrawState(boolean z) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateDrawState() needDraw ", z, ", mBouncer = ");
        m49m.append(this.mBouncer);
        m49m.append(" , focus = ");
        m49m.append(hasWindowFocus());
        Log.d("KeyguardVideoWallpaper", m49m.toString());
        drawVideo(z, false);
        if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE || SystemUIWallpaper.isSubDisplay()) {
            return;
        }
        if (this.mBouncer || !hasWindowFocus()) {
            drawVideo(false, true);
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [android.os.AsyncTask, com.android.systemui.wallpaper.view.KeyguardVideoWallpaper$2] */
    public final void updateDrawable(boolean z) {
        if (this.mThumbnail == null || z) {
            AsyncTaskC37092 asyncTaskC37092 = this.mThumbnailLoader;
            if (asyncTaskC37092 != null && !asyncTaskC37092.isCancelled()) {
                cancel(true);
                this.mThumbnailLoader = null;
            }
            ?? r2 = new AsyncTask() { // from class: com.android.systemui.wallpaper.view.KeyguardVideoWallpaper.2
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    KeyguardVideoWallpaper keyguardVideoWallpaper = KeyguardVideoWallpaper.this;
                    Bitmap videoFrame = WallpaperUtils.getVideoFrame(keyguardVideoWallpaper.mContext, keyguardVideoWallpaper.mRetrieverFd, keyguardVideoWallpaper.mVideoFilePath, keyguardVideoWallpaper.mVideoFileUri, keyguardVideoWallpaper.mStartPosition * 1000);
                    if (videoFrame != null && (videoFrame.getWidth() >= 720 || videoFrame.getHeight() >= 720)) {
                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(videoFrame, (int) (videoFrame.getWidth() * 0.3f), (int) (videoFrame.getHeight() * 0.3f), true);
                        videoFrame.recycle();
                        videoFrame = createScaledBitmap;
                    }
                    if (videoFrame != null) {
                        keyguardVideoWallpaper.mThumbnail = new BitmapDrawable(videoFrame);
                    }
                    Log.i("KeyguardVideoWallpaper", "createThumbnail mThumbnail: " + keyguardVideoWallpaper.mThumbnail);
                    return null;
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    super.onPostExecute((Void) obj);
                    if (isCancelled()) {
                        return;
                    }
                    KeyguardVideoWallpaper keyguardVideoWallpaper = KeyguardVideoWallpaper.this;
                    if (keyguardVideoWallpaper.mThumbnailView == null) {
                        keyguardVideoWallpaper.mThumbnailView = new ImageView(KeyguardVideoWallpaper.this.mContext);
                    }
                    KeyguardVideoWallpaper keyguardVideoWallpaper2 = KeyguardVideoWallpaper.this;
                    keyguardVideoWallpaper2.mThumbnailView.setBackground(keyguardVideoWallpaper2.mThumbnail);
                    KeyguardVideoWallpaper.this.invalidate();
                    Log.i("KeyguardVideoWallpaper", "onPostExecute: mDrawable = " + KeyguardVideoWallpaper.this.mThumbnail);
                }
            };
            this.mThumbnailLoader = r2;
            r2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    public final void updateSurfaceScale(int i, int i2) {
        if (this.mVideoScreenSize == null || i <= 0 || i2 <= 0) {
            Log.e("KeyguardVideoWallpaper", "updateSurfaceScale() mVideoScreenSize is null");
            return;
        }
        if (this.mSurfaceView == null) {
            Log.e("KeyguardVideoWallpaper", "updateSurfaceScale() mSurfaceView is null");
            return;
        }
        float f = (r0.x * i2) / (r0.y * i);
        float f2 = f >= 1.0f ? f : 1.0f;
        float f3 = f <= 1.0f ? 1.0f / f : 1.0f;
        PointF pointF = this.mSurfaceScaleInfo;
        pointF.x = f2;
        pointF.y = f3;
        Log.i("KeyguardVideoWallpaper", "updateSurfaceScale: video size = " + this.mVideoScreenSize + ", height = " + i2 + ", sx = " + f2 + ", sy = " + f3);
        this.mSurfaceView.semResetRenderNodePosition();
        SurfaceView surfaceView = this.mSurfaceView;
        float f4 = ((float) i) * 0.5f;
        float f5 = ((float) i2) * 0.5f;
        surfaceView.setPivotX(f4);
        surfaceView.setPivotY(f5);
        surfaceView.setScaleX(f2);
        surfaceView.setScaleY(f3);
        ImageView imageView = this.mThumbnailView;
        if (imageView != null) {
            imageView.setPivotX(f4);
            imageView.setPivotY(f5);
            imageView.setScaleX(f2);
            imageView.setScaleY(f3);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void handleTouchEvent(MotionEvent motionEvent) {
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onResume() {
    }
}
