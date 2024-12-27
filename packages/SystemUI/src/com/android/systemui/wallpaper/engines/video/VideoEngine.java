package com.android.systemui.wallpaper.engines.video;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.engines.video.VideoController;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7;
import com.samsung.android.nexus.video.BuildConfig;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;
import java.util.HashMap;
import java.util.function.Consumer;

public final class VideoEngine extends WallpaperEngine {
    public final String TAG;
    public boolean mBouncerVisible;
    public DisplayState mDisplayState;
    public boolean mIsGoingToSleepFromNonKeyguard;
    public boolean mIsUnlockingFromScreenOffOrAod;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public StateSnapshot mLastPlayerUpdateSnapshot;
    public boolean mPlayerPausedForcefully;
    public SurfaceHolder mSurfaceHolder;
    public final VideoController mVideoController;
    public final VideoEngine$$ExternalSyntheticLambda0 mVideoPauseDispatcher;
    public final Point mVideoSize;
    public final VideoSource mVideoSource;
    public final Handler mWorkerHandler;

    /* renamed from: com.android.systemui.wallpaper.engines.video.VideoEngine$1, reason: invalid class name */
    public final class AnonymousClass1 implements VideoController.Callback {
        public AnonymousClass1() {
        }

        public final void onSurfaceAndPlayerReady() {
            int i = 2;
            VideoEngine videoEngine = VideoEngine.this;
            Log.i(videoEngine.TAG, "onSurfaceAndPlayerReady: " + videoEngine.mVideoSize + ", validSurface=" + videoEngine.mSurfaceHolder.getSurface().isValid());
            if ((videoEngine.mVideoSource.mType & 2) == 2) {
                ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) videoEngine.mCallback;
                anonymousClass2.getClass();
                int i2 = ImageWallpaper.IntegratedEngine.$r8$clinit;
                ImageWallpaper.IntegratedEngine integratedEngine = ImageWallpaper.IntegratedEngine.this;
                integratedEngine.getClass();
                WallpaperService.Engine.SurfaceData semCreateSurface = integratedEngine.semCreateSurface(true, 1.0f);
                if (semCreateSurface != null) {
                    ImageWallpaper.this.mMainThreadHandler.postDelayed(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7(integratedEngine, semCreateSurface, i), 500L);
                }
            }
            videoEngine.setVisibleRectOfSurface(videoEngine.getVisibleRectOfSurface());
        }
    }

    /* renamed from: com.android.systemui.wallpaper.engines.video.VideoEngine$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$wallpaper$live$sdk$data$DisplayState;

        static {
            int[] iArr = new int[DisplayState.values().length];
            $SwitchMap$com$samsung$android$wallpaper$live$sdk$data$DisplayState = iArr;
            try {
                iArr[DisplayState.ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$live$sdk$data$DisplayState[DisplayState.AOD_WITH_WALLPAPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$live$sdk$data$DisplayState[DisplayState.AOD_WITHOUT_WALLPAPER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$live$sdk$data$DisplayState[DisplayState.OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public final class StateSnapshot {
        public final DisplayState mDisplayState;

        public StateSnapshot(DisplayState displayState) {
            this.mDisplayState = displayState;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof StateSnapshot) && this.mDisplayState == ((StateSnapshot) obj).mDisplayState;
        }
    }

    public VideoEngine(VideoSource videoSource, WallpaperEngineCallback wallpaperEngineCallback, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(wallpaperEngineCallback);
        this.TAG = "ImageWallpaper";
        this.mDisplayState = DisplayState.NONE;
        new HashMap();
        this.mBouncerVisible = false;
        this.mPlayerPausedForcefully = false;
        this.mIsGoingToSleepFromNonKeyguard = false;
        this.mIsUnlockingFromScreenOffOrAod = false;
        this.mVideoPauseDispatcher = new Runnable() { // from class: com.android.systemui.wallpaper.engines.video.VideoEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VideoEngine videoEngine = VideoEngine.this;
                Log.i(videoEngine.TAG, "mVideoPauseDispatcher: video pause timer expired");
                videoEngine.mVideoController.pause(false);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mVideoSource = videoSource;
        this.mVideoSize = videoSource.mVideoSize;
        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        this.mWorkerHandler = threadHandler;
        this.mVideoController = new VideoController(videoSource, threadHandler, new AnonymousClass1());
        this.TAG = "ImageWallpaper_" + getWhich() + "[Video]";
    }

    public final void dispatchCurrentStateForMainOrSubDisplay(StateSnapshot stateSnapshot, StateSnapshot stateSnapshot2) {
        int which = getWhich();
        boolean z = this.mBouncerVisible;
        boolean z2 = this.mPlayerPausedForcefully;
        boolean z3 = (which & 3) == 2;
        boolean isWatchFace = WhichChecker.isWatchFace(which);
        int i = AnonymousClass2.$SwitchMap$com$samsung$android$wallpaper$live$sdk$data$DisplayState[stateSnapshot.mDisplayState.ordinal()];
        if (i == 1) {
            this.mWorkerHandler.removeCallbacks(this.mVideoPauseDispatcher);
            if (z2 || z) {
                this.mVideoController.pause(false);
                return;
            } else if (z3 && (this.mIsGoingToSleepFromNonKeyguard || this.mIsUnlockingFromScreenOffOrAod)) {
                this.mVideoController.pause(false);
                return;
            } else {
                playVideo();
                return;
            }
        }
        if (i != 2) {
            if (i == 3 || i == 4) {
                this.mWorkerHandler.removeCallbacks(this.mVideoPauseDispatcher);
                this.mVideoController.pause(true);
                return;
            }
            return;
        }
        if (stateSnapshot2.mDisplayState != DisplayState.ON) {
            this.mWorkerHandler.removeCallbacks(this.mVideoPauseDispatcher);
            this.mVideoController.pause(false);
        } else {
            int i2 = isWatchFace ? 100 : 400;
            this.mWorkerHandler.removeCallbacks(this.mVideoPauseDispatcher);
            this.mWorkerHandler.postDelayed(this.mVideoPauseDispatcher, i2);
        }
    }

    public final Rect getVisibleRectOfSurface() {
        Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
        boolean isEmpty = surfaceFrame.isEmpty();
        String str = this.TAG;
        Rect rect = null;
        if (isEmpty) {
            Log.w(str, "getVisibleRectOfSurface : frame is empty");
            return null;
        }
        VideoSource videoSource = this.mVideoSource;
        Point point = videoSource.mVideoSize;
        String str2 = videoSource.TAG;
        if (point == null) {
            Log.e(str2, "getCropHint: mVideoSize is null.");
        } else {
            int i = videoSource.mWhich;
            boolean isWatchFace = WhichChecker.isWatchFace(i);
            CoverWallpaper coverWallpaper = videoSource.mCoverWallpaper;
            if (isWatchFace) {
                CoverWallpaperController coverWallpaperController = (CoverWallpaperController) coverWallpaper;
                Rect fbeWallpaperRect = coverWallpaperController.isCoverWallpaperRequired() ? coverWallpaperController.isFbeAvailable() ? coverWallpaperController.mPluginWallpaperManager.getFbeWallpaperRect(1) : coverWallpaperController.mPluginWallpaperManager.getHomeWallpaperRect(CoverWallpaperController.getCoverMode()) : WallpaperManager.getInstance(videoSource.mContext).semGetWallpaperCropHint(i);
                if (fbeWallpaperRect == null || fbeWallpaperRect.isEmpty()) {
                    Log.w(str2, "getCropHint: videoCropHint = " + fbeWallpaperRect);
                } else {
                    int width = surfaceFrame.width();
                    int height = surfaceFrame.height();
                    if (width == 0 || height == 0) {
                        Log.w(str2, "getCropHint: screenWidth = " + width + ", screenHeight = " + height);
                    } else {
                        float width2 = videoSource.mVideoSize.x / fbeWallpaperRect.width();
                        float f = (-fbeWallpaperRect.left) * width2;
                        float f2 = (-fbeWallpaperRect.top) * width2;
                        rect = new Rect((int) f, (int) f2, (int) ((width * width2) + f), (int) ((height * width2) + f2));
                    }
                }
            } else if (WhichChecker.isVirtualDisplay(i)) {
                CoverWallpaperController coverWallpaperController2 = (CoverWallpaperController) coverWallpaper;
                Rect fbeWallpaperRect2 = coverWallpaperController2.isCoverWallpaperRequired() ? coverWallpaperController2.isFbeAvailable() ? coverWallpaperController2.mPluginWallpaperManager.getFbeWallpaperRect(1) : coverWallpaperController2.mPluginWallpaperManager.getHomeWallpaperRect(CoverWallpaperController.getCoverMode()) : WallpaperManager.getInstance(videoSource.mContext).semGetWallpaperCropHint(i);
                Bundle wallpaperExtras = WallpaperManager.getInstance(videoSource.mContext).getWallpaperExtras(i, videoSource.mUserId);
                if (fbeWallpaperRect2 == null || fbeWallpaperRect2.isEmpty() || wallpaperExtras == null) {
                    Log.w(str2, "getCropHint: videoCropHint = " + fbeWallpaperRect2 + ", extras = " + wallpaperExtras);
                } else {
                    int i2 = wallpaperExtras.getInt("coverScreenWidth");
                    int i3 = wallpaperExtras.getInt("coverScreenHeight");
                    if (i2 == 0 || i3 == 0) {
                        Log.w(str2, "getCropHint: screenWidth = " + i2 + ", screenHeight = " + i3);
                    } else {
                        float width3 = videoSource.mVideoSize.x / fbeWallpaperRect2.width();
                        float f3 = (-fbeWallpaperRect2.left) * width3;
                        float f4 = (-fbeWallpaperRect2.top) * width3;
                        rect = new Rect((int) f3, (int) f4, (int) ((i2 * width3) + f3), (int) ((i3 * width3) + f4));
                    }
                }
            } else {
                Point point2 = videoSource.mVideoSize;
                rect = new Rect(0, 0, point2.x, point2.y);
            }
        }
        if (this.mVideoSize == null || rect == null) {
            Log.w(str, "getVisibleRectOfSurface: video size = " + this.mVideoSize + ", crop hint = " + rect);
            return surfaceFrame;
        }
        Log.d(str, "getVisibleRectOfSurface: mVideoSize = " + this.mVideoSize + ", cropHint = " + rect);
        Rect rect2 = new Rect();
        rect2.set(rect);
        if (surfaceFrame.height() * rect.width() > rect.height() * surfaceFrame.width()) {
            float height2 = surfaceFrame.height() / rect.height();
            int width4 = (int) ((((rect.width() * height2) - surfaceFrame.width()) / height2) / 2.0f);
            rect2.left += width4;
            rect2.right -= width4;
        } else {
            float width5 = surfaceFrame.width() / rect.width();
            int height3 = (int) ((((rect.height() * width5) - surfaceFrame.height()) / width5) / 2.0f);
            rect2.top += height3;
            rect2.bottom -= height3;
        }
        rect2.toString();
        Point point3 = this.mVideoSize;
        Rect rect3 = new Rect();
        rect3.set(rect2);
        float width6 = surfaceFrame.width() / point3.x;
        float height4 = surfaceFrame.height() / point3.y;
        rect3.left = (int) (rect3.left * width6);
        rect3.right = (int) (rect3.right * width6);
        rect3.top = (int) (rect3.top * height4);
        rect3.bottom = (int) (rect3.bottom * height4);
        rect3.toString();
        surfaceFrame.toString();
        return rect3;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        Log.d(this.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onCommand: ", str));
        str.getClass();
        switch (str) {
            case "samsung.android.wallpaper.bouncervisibilitychanged":
                this.mBouncerVisible = bundle.getBoolean("visible");
                updatePlayerState();
                break;
            case "samsung.android.wallpaper.resume":
                this.mPlayerPausedForcefully = false;
                updatePlayerState();
                break;
            case "android.wallpaper.keyguardgoingaway":
                if (this.mDisplayState != DisplayState.ON) {
                    this.mIsUnlockingFromScreenOffOrAod = true;
                }
                updatePlayerState();
                break;
            case "samsung.android.wallpaper.pause":
                this.mPlayerPausedForcefully = true;
                updatePlayerState();
                break;
            case "android.wallpaper.goingtosleep":
                if (!this.mKeyguardUpdateMonitor.isKeyguardVisible()) {
                    this.mIsGoingToSleepFromNonKeyguard = true;
                    break;
                }
                break;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        WallpaperEngineCallback wallpaperEngineCallback = this.mCallback;
        Log.d(this.TAG, "onCreate: which = " + getWhich());
        Context appContext = getAppContext();
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) wallpaperEngineCallback;
        anonymousClass2.getClass();
        int i = ImageWallpaper.IntegratedEngine.$r8$clinit;
        DisplayState displayState = ImageWallpaper.IntegratedEngine.this.getDisplayState();
        this.mDisplayState = displayState;
        this.mLastPlayerUpdateSnapshot = new StateSnapshot(displayState);
        setFixedOrientation(this.mVideoSource.isFixedOrientation(ImageWallpaper.IntegratedEngine.this.isPreview()), getSurfaceHolder().getSurface().isValid());
        seekToFirstFrame();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        Log.d(this.TAG, "onDestroy");
        VideoController videoController = this.mVideoController;
        synchronized (videoController.mLock) {
            try {
                if (videoController.mIsReleased) {
                    Log.i(videoController.TAG, "release: already released");
                } else {
                    Log.i(videoController.TAG, "release: curSession=" + videoController.mActiveSession + ", enqueued=[" + videoController.mCommandQueue.listEnqueued() + "]");
                    VideoController.CommandQueue commandQueue = videoController.mCommandQueue;
                    synchronized (commandQueue) {
                        commandQueue.mQueue.clear();
                    }
                    VideoController.PlayerSession playerSession = videoController.mActiveSession;
                    if (playerSession == null) {
                        Log.i(videoController.TAG, "release: no active player");
                    } else {
                        playerSession.release();
                    }
                    videoController.mIsReleased = true;
                }
            } finally {
            }
        }
        VideoSource videoSource = this.mVideoSource;
        if (videoSource.mVideoLocation != null) {
            Log.d(videoSource.TAG, BuildConfig.BUILD_TYPE);
            videoSource.mVideoLocation.release();
            videoSource.mVideoLocation = null;
        }
        setVisibleRectOfSurface(null);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
        Log.i(this.TAG, "onDisplayStateChanged: " + displayState2 + " -> " + displayState);
        this.mDisplayState = displayState;
        this.mIsGoingToSleepFromNonKeyguard = false;
        updatePlayerState();
        this.mIsUnlockingFromScreenOffOrAod = false;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
        Bitmap bitmap;
        Log.d(this.TAG, "onGetScreenshot");
        VideoController videoController = this.mVideoController;
        synchronized (videoController.mLock) {
            try {
                if (videoController.mIsReleased) {
                    Log.i(videoController.TAG, "getCurrentVideoFrame: released");
                    bitmap = null;
                } else {
                    if (videoController.mActiveSession != null && videoController.isSurfaceAndPlayerReady()) {
                        bitmap = videoController.mActiveSession.getCurrentVideoFrame();
                    }
                    final VideoSource videoSource = videoController.mVideoSource;
                    VideoSource.VideoLocation videoLocation = videoSource.mVideoLocation;
                    if (videoLocation == null) {
                        bitmap = null;
                    } else {
                        final Bitmap[] bitmapArr = {null};
                        videoLocation.useMediaRetriever(new Consumer() { // from class: com.android.systemui.wallpaper.engines.video.VideoSource$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                VideoSource videoSource2 = VideoSource.this;
                                Bitmap[] bitmapArr2 = bitmapArr;
                                MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) obj;
                                videoSource2.getClass();
                                if (mediaMetadataRetriever == null) {
                                    return;
                                }
                                bitmapArr2[0] = mediaMetadataRetriever.getFrameAtTime(0L, 2);
                            }
                        });
                        bitmap = bitmapArr[0];
                    }
                }
            } finally {
            }
        }
        if (bitmap == null) {
            Log.w(this.TAG, "onGetScreenshot : failed to get video frame");
            return null;
        }
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder == null) {
            return new ScreenshotResults(bitmap);
        }
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        Rect centerCropRect = GraphicsUtils.getCenterCropRect(bitmap.getWidth(), bitmap.getHeight(), surfaceFrame.width(), surfaceFrame.height());
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, centerCropRect.left, centerCropRect.top, centerCropRect.width(), centerCropRect.height());
        if (createBitmap != bitmap) {
            bitmap.recycle();
        }
        return new ScreenshotResults(createBitmap);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i3, "onSurfaceChanged width = ", " , height = ", this.TAG);
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        if (this.mVideoController.isSurfaceReady()) {
            setVisibleRectOfSurface(getVisibleRectOfSurface());
            seekToFirstFrame();
            return;
        }
        VideoController videoController = this.mVideoController;
        Surface surface = surfaceHolder.getSurface();
        synchronized (videoController.mLock) {
            try {
                if (videoController.mIsReleased) {
                    Log.i(videoController.TAG, "setSurface: released");
                } else {
                    boolean z = false;
                    boolean z2 = videoController.mSurface == null && surface != null;
                    videoController.mSurface = surface;
                    boolean isSurfaceAndPlayerReady = videoController.isSurfaceAndPlayerReady();
                    Log.i(videoController.TAG, "setSurface: firstAssign=" + z2 + ", isReady=" + isSurfaceAndPlayerReady);
                    if (z2 && isSurfaceAndPlayerReady) {
                        z = true;
                    }
                    if (z) {
                        synchronized (videoController.mLock) {
                            try {
                                VideoController.PlayerSession playerSession = videoController.mActiveSession;
                                if (playerSession != null) {
                                    playerSession.onSurfaceAndPlayerReady(videoController.mSurface);
                                }
                            } finally {
                            }
                        }
                        ((AnonymousClass1) videoController.mCallback).onSurfaceAndPlayerReady();
                        videoController.dispatchCommands();
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onVisibilityChanged: engineVisible=", ", windowVisible=", ", displayState = ", z, isWindowVisible());
        m.append(this.mDisplayState);
        Log.d(this.TAG, m.toString());
        updatePlayerState();
    }

    public final void pauseAndSeekToFirstFrame() {
        VideoController videoController = this.mVideoController;
        synchronized (videoController.mLock) {
            try {
                if (videoController.mIsReleased) {
                    Log.i(videoController.TAG, "pauseAndSeekToFirstFrame: released");
                    return;
                }
                Log.i(videoController.TAG, "pauseAndSeekToFirstFrame");
                videoController.mCommandQueue.enqueueCommand(new VideoController.PauseAndSeekToFirstFrameCommand(videoController));
                videoController.dispatchCommands();
            } finally {
            }
        }
    }

    public final void playVideo() {
        VideoController videoController = this.mVideoController;
        synchronized (videoController.mLock) {
            try {
                if (videoController.mIsReleased) {
                    Log.i(videoController.TAG, "play: released");
                    return;
                }
                Log.i(videoController.TAG, "play");
                videoController.mCommandQueue.enqueueCommand(new VideoController.Command(videoController, VideoController.CommandAction.PLAY));
                videoController.dispatchCommands();
            } finally {
            }
        }
    }

    public final void seekToFirstFrame() {
        VideoController videoController = this.mVideoController;
        synchronized (videoController.mLock) {
            try {
                if (videoController.mIsReleased) {
                    Log.i(videoController.TAG, "seekTo: released");
                    return;
                }
                Log.i(videoController.TAG, "seekTo: to 0ms");
                videoController.mCommandQueue.enqueueCommand(new VideoController.SeekCommand(videoController, 0));
                videoController.dispatchCommands();
            } finally {
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void updatePlayerState() {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.video.VideoEngine.updatePlayerState():void");
    }
}
