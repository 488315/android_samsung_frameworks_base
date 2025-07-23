package com.samsung.android.nexus.video;

import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.media.SemMediaPlayer;
import com.samsung.android.nexus.base.layer.BaseLayer;
import com.samsung.android.nexus.base.layer.NexusLayerParams;
import com.samsung.android.nexus.base.reflection.ReservedAction;
import com.samsung.android.nexus.base.reflection.ReservedActionQueue;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.egl.world.BaseWorld;
import com.samsung.android.nexus.video.VideoPlayer;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VideoLayer extends BaseLayer {
    private static final int RESET_MAX_CNT = 10000;
    private static final int RESET_REASON_CORE = 0;
    private static final int RESET_REASON_GL_ERROR = 2;
    private static final int RESET_REASON_MEDIA_ERROR = 1;
    private SemMediaPlayer.OnPlaybackCompleteListener completionListener;
    private SemMediaPlayer.OnErrorListener errorListener;
    private boolean isAutoPlayNextMediaSource;
    private boolean isLoopingMediaSources;
    private AssetFileDescriptor mAssetFd;
    private final BackupData mBackupData;
    private FileDescriptor mFd;
    private boolean mIsCreated;
    private boolean mNeedToRecreate;
    private final Rect mObjectRect;
    private VideoGL mOldVideoGl;
    private VideoPlayer mPlayer;
    private final ReservedActionQueue mReservedActions;
    private final ResetLogger mResetLogger;
    private Uri mUri;
    private VideoGL mVideoGl;
    private SemMediaPlayer.OnInitCompleteListener preparedListener;
    private SemMediaPlayer.OnSeekCompleteListener seekCompleteListener;
    private VideoStateChangedListener videoStateChangedListener;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "VideoLayer";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BackupData {
        private RectF boundRect;
        private Float contrast;
        private RectF cropRect;
        private Float globalAlpha;
        private Float hdrSaturation;
        private float[] hsvFilterColor;
        private Boolean isHdrModeEnabled;
        private Boolean isTransparencyEnabled;
        private Boolean looping;
        private Float objectHeight;
        private Float objectWidth;
        private Float offsetX;
        private Float offsetY;
        private Float offsetZ;
        private float[] rgbFilterColor;
        private Float rotationAngle;
        private Float rotationX;
        private Float rotationY;
        private Float rotationZ;
        private Float scale;
        private int worldHeight;
        private int worldWidth;

        public BackupData(int i, int i2, Float f, Float f2, Boolean bool, Boolean bool2, Float f3, Float f4, Float f5, Float f6, Float f7, Float f8, Float f9, Float f10, Float f11, Float f12, Float f13, RectF rectF, RectF rectF2, float[] fArr, float[] fArr2, Boolean bool3) {
            this.worldWidth = i;
            this.worldHeight = i2;
            this.contrast = f;
            this.hdrSaturation = f2;
            this.isHdrModeEnabled = bool;
            this.isTransparencyEnabled = bool2;
            this.objectWidth = f3;
            this.objectHeight = f4;
            this.rotationAngle = f5;
            this.rotationX = f6;
            this.rotationY = f7;
            this.rotationZ = f8;
            this.scale = f9;
            this.offsetX = f10;
            this.offsetY = f11;
            this.offsetZ = f12;
            this.globalAlpha = f13;
            this.cropRect = rectF;
            this.boundRect = rectF2;
            this.rgbFilterColor = fArr;
            this.hsvFilterColor = fArr2;
            this.looping = bool3;
        }

        public final RectF getBoundRect() {
            return this.boundRect;
        }

        public final Float getContrast() {
            return this.contrast;
        }

        public final RectF getCropRect() {
            return this.cropRect;
        }

        public final Float getGlobalAlpha() {
            return this.globalAlpha;
        }

        public final Float getHdrSaturation() {
            return this.hdrSaturation;
        }

        public final float[] getHsvFilterColor() {
            return this.hsvFilterColor;
        }

        public final Boolean getLooping() {
            return this.looping;
        }

        public final Float getObjectHeight() {
            return this.objectHeight;
        }

        public final Float getObjectWidth() {
            return this.objectWidth;
        }

        public final Float getOffsetX() {
            return this.offsetX;
        }

        public final Float getOffsetY() {
            return this.offsetY;
        }

        public final Float getOffsetZ() {
            return this.offsetZ;
        }

        public final float[] getRgbFilterColor() {
            return this.rgbFilterColor;
        }

        public final Float getRotationAngle() {
            return this.rotationAngle;
        }

        public final Float getRotationX() {
            return this.rotationX;
        }

        public final Float getRotationY() {
            return this.rotationY;
        }

        public final Float getRotationZ() {
            return this.rotationZ;
        }

        public final Float getScale() {
            return this.scale;
        }

        public final int getWorldHeight() {
            return this.worldHeight;
        }

        public final int getWorldWidth() {
            return this.worldWidth;
        }

        public final Boolean isHdrModeEnabled() {
            return this.isHdrModeEnabled;
        }

        public final Boolean isTransparencyEnabled() {
            return this.isTransparencyEnabled;
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
        
            if (r2 != null) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void restore() {
            /*
                r5 = this;
                java.lang.Float r0 = r5.contrast
                if (r0 == 0) goto Ld
                float r0 = r0.floatValue()
                com.samsung.android.nexus.video.VideoLayer r1 = com.samsung.android.nexus.video.VideoLayer.this
                r1.setContrast(r0)
            Ld:
                java.lang.Float r0 = r5.hdrSaturation
                if (r0 == 0) goto L1a
                float r0 = r0.floatValue()
                com.samsung.android.nexus.video.VideoLayer r1 = com.samsung.android.nexus.video.VideoLayer.this
                r1.setHdrSaturation(r0)
            L1a:
                java.lang.Float r0 = r5.offsetZ
                if (r0 == 0) goto L3d
                float r0 = r0.floatValue()
                java.lang.Float r1 = r5.offsetX
                r2 = 0
                if (r1 == 0) goto L3a
                float r1 = r1.floatValue()
                java.lang.Float r3 = r5.offsetY
                if (r3 == 0) goto L3a
                float r2 = r3.floatValue()
                com.samsung.android.nexus.video.VideoLayer r3 = com.samsung.android.nexus.video.VideoLayer.this
                r3.setOffset(r1, r2, r0)
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
            L3a:
                if (r2 == 0) goto L3d
                goto L54
            L3d:
                java.lang.Float r0 = r5.offsetX
                if (r0 == 0) goto L54
                float r0 = r0.floatValue()
                java.lang.Float r1 = r5.offsetY
                if (r1 == 0) goto L54
                float r1 = r1.floatValue()
                com.samsung.android.nexus.video.VideoLayer r2 = com.samsung.android.nexus.video.VideoLayer.this
                r2.setOffsetXY(r0, r1)
                kotlin.Unit r0 = kotlin.Unit.INSTANCE
            L54:
                java.lang.Float r0 = r5.globalAlpha
                if (r0 == 0) goto L61
                float r0 = r0.floatValue()
                com.samsung.android.nexus.video.VideoLayer r1 = com.samsung.android.nexus.video.VideoLayer.this
                r1.setGlobalAlpha(r0)
            L61:
                com.samsung.android.nexus.video.VideoLayer r0 = com.samsung.android.nexus.video.VideoLayer.this
                int r1 = r5.worldWidth
                int r2 = r5.worldHeight
                r0.onSizeChanged(r1, r2)
                java.lang.Boolean r1 = r5.isHdrModeEnabled
                if (r1 == 0) goto L75
                boolean r1 = r1.booleanValue()
                r0.setHdrModeEnabled(r1)
            L75:
                java.lang.Float r1 = r5.objectWidth
                if (r1 == 0) goto L88
                float r1 = r1.floatValue()
                java.lang.Float r2 = r5.objectHeight
                if (r2 == 0) goto L88
                float r2 = r2.floatValue()
                r0.setSize(r1, r2)
            L88:
                java.lang.Float r1 = r5.rotationAngle
                if (r1 == 0) goto Lab
                float r1 = r1.floatValue()
                java.lang.Float r2 = r5.rotationX
                if (r2 == 0) goto Lab
                float r2 = r2.floatValue()
                java.lang.Float r3 = r5.rotationY
                if (r3 == 0) goto Lab
                float r3 = r3.floatValue()
                java.lang.Float r4 = r5.rotationZ
                if (r4 == 0) goto Lab
                float r4 = r4.floatValue()
                r0.setRotation(r1, r2, r3, r4)
            Lab:
                java.lang.Float r1 = r5.scale
                if (r1 == 0) goto Lb6
                float r1 = r1.floatValue()
                r0.setScale(r1)
            Lb6:
                android.graphics.RectF r1 = r5.cropRect
                if (r1 == 0) goto Lbd
                r0.setCropRect(r1)
            Lbd:
                android.graphics.RectF r1 = r5.boundRect
                if (r1 == 0) goto Lc4
                r0.setBoundRect(r1)
            Lc4:
                float[] r1 = r5.rgbFilterColor
                if (r1 == 0) goto Lcb
                r0.setRgbFilterColor(r1)
            Lcb:
                float[] r1 = r5.hsvFilterColor
                r0.setHsvFilterColor(r1)
                java.lang.Boolean r5 = r5.looping
                if (r5 == 0) goto Ldb
                boolean r5 = r5.booleanValue()
                r0.setLooping(r5)
            Ldb:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.nexus.video.VideoLayer.BackupData.restore():void");
        }

        public final void setBoundRect(RectF rectF) {
            this.boundRect = rectF;
        }

        public final void setContrast(Float f) {
            this.contrast = f;
        }

        public final void setCropRect(RectF rectF) {
            this.cropRect = rectF;
        }

        public final void setGlobalAlpha(Float f) {
            this.globalAlpha = f;
        }

        public final void setHdrModeEnabled(Boolean bool) {
            this.isHdrModeEnabled = bool;
        }

        public final void setHdrSaturation(Float f) {
            this.hdrSaturation = f;
        }

        public final void setHsvFilterColor(float[] fArr) {
            this.hsvFilterColor = fArr;
        }

        public final void setLooping(Boolean bool) {
            this.looping = bool;
        }

        public final void setObjectHeight(Float f) {
            this.objectHeight = f;
        }

        public final void setObjectWidth(Float f) {
            this.objectWidth = f;
        }

        public final void setOffsetX(Float f) {
            this.offsetX = f;
        }

        public final void setOffsetY(Float f) {
            this.offsetY = f;
        }

        public final void setOffsetZ(Float f) {
            this.offsetZ = f;
        }

        public final void setRgbFilterColor(float[] fArr) {
            this.rgbFilterColor = fArr;
        }

        public final void setRotationAngle(Float f) {
            this.rotationAngle = f;
        }

        public final void setRotationX(Float f) {
            this.rotationX = f;
        }

        public final void setRotationY(Float f) {
            this.rotationY = f;
        }

        public final void setRotationZ(Float f) {
            this.rotationZ = f;
        }

        public final void setScale(Float f) {
            this.scale = f;
        }

        public final void setTransparencyEnabled(Boolean bool) {
            this.isTransparencyEnabled = bool;
        }

        public final void setWorldHeight(int i) {
            this.worldHeight = i;
        }

        public final void setWorldWidth(int i) {
            this.worldWidth = i;
        }

        public /* synthetic */ BackupData(VideoLayer videoLayer, int i, int i2, Float f, Float f2, Boolean bool, Boolean bool2, Float f3, Float f4, Float f5, Float f6, Float f7, Float f8, Float f9, Float f10, Float f11, Float f12, Float f13, RectF rectF, RectF rectF2, float[] fArr, float[] fArr2, Boolean bool3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? null : f, (i3 & 8) != 0 ? null : f2, (i3 & 16) != 0 ? null : bool, (i3 & 32) != 0 ? null : bool2, (i3 & 64) != 0 ? null : f3, (i3 & 128) != 0 ? null : f4, (i3 & 256) != 0 ? null : f5, (i3 & 512) != 0 ? null : f6, (i3 & 1024) != 0 ? null : f7, (i3 & 2048) != 0 ? null : f8, (i3 & 4096) != 0 ? null : f9, (i3 & 8192) != 0 ? null : f10, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : f11, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : f12, (i3 & 65536) != 0 ? null : f13, (i3 & 131072) != 0 ? null : rectF, (i3 & 262144) != 0 ? null : rectF2, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? null : fArr, (i3 & 1048576) != 0 ? new float[]{0.0f, 0.5f, 0.5f} : fArr2, (i3 & 2097152) != 0 ? null : bool3);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResetLogger {
        private final ArrayList<Integer> mResetCount = CollectionsKt__CollectionsKt.arrayListOf(0, 0, 0);

        public ResetLogger() {
        }

        public final void addCount(int i) {
            ArrayList<Integer> arrayList = this.mResetCount;
            arrayList.set(i, Integer.valueOf(arrayList.get(i).intValue() + 1));
            if (Intrinsics.compare(this.mResetCount.get(i).intValue(), 10000) >= 0) {
                this.mResetCount.set(i, 0);
            }
        }

        public final void print() {
            Log.i(VideoLayer.TAG, "Reset count by core = " + this.mResetCount.get(0) + ", media = " + this.mResetCount.get(1) + ", GL = " + this.mResetCount.get(2));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface VideoStateChangedListener {
        void onStateChanged(VideoPlayer.VideoState videoState);
    }

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;

        static {
            int[] iArr = new int[VideoPlayer.VideoState.values().length];
            $EnumSwitchMapping$0 = iArr;
            VideoPlayer.VideoState videoState = VideoPlayer.VideoState.INITIALIZED;
            iArr[videoState.ordinal()] = 1;
            VideoPlayer.VideoState videoState2 = VideoPlayer.VideoState.PAUSED;
            iArr[videoState2.ordinal()] = 2;
            VideoPlayer.VideoState videoState3 = VideoPlayer.VideoState.STARTED;
            iArr[videoState3.ordinal()] = 3;
            int[] iArr2 = new int[VideoPlayer.VideoState.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[videoState.ordinal()] = 1;
            iArr2[videoState2.ordinal()] = 2;
            iArr2[videoState3.ordinal()] = 3;
            int[] iArr3 = new int[VideoPlayer.VideoState.values().length];
            $EnumSwitchMapping$2 = iArr3;
            iArr3[videoState.ordinal()] = 1;
            iArr3[videoState2.ordinal()] = 2;
            iArr3[videoState3.ordinal()] = 3;
            int[] iArr4 = new int[VideoPlayer.VideoState.values().length];
            $EnumSwitchMapping$3 = iArr4;
            iArr4[videoState.ordinal()] = 1;
            iArr4[videoState2.ordinal()] = 2;
            iArr4[videoState3.ordinal()] = 3;
            int[] iArr5 = new int[VideoPlayer.VideoState.values().length];
            $EnumSwitchMapping$4 = iArr5;
            iArr5[VideoPlayer.VideoState.IDLE.ordinal()] = 1;
            iArr5[videoState.ordinal()] = 2;
            iArr5[videoState2.ordinal()] = 3;
            iArr5[videoState3.ordinal()] = 4;
        }
    }

    public VideoLayer(Rect rect) {
        this.mObjectRect = rect;
        this.mReservedActions = new ReservedActionQueue();
        this.mResetLogger = new ResetLogger();
        this.mBackupData = new BackupData(this, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 4194303, null);
    }

    private final void clearAsync() {
        Log.i(TAG, "Clear async");
        VideoGL videoGL = this.mOldVideoGl;
        if (videoGL != null) {
            videoGL.clear();
        }
        this.mOldVideoGl = null;
    }

    private final void clearLocked(boolean z) {
        Log.i(TAG, "Clear locked");
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer != null) {
            videoPlayer.release();
        }
        this.mPlayer = null;
        if (z) {
            this.mOldVideoGl = this.mVideoGl;
        } else {
            VideoGL videoGL = this.mVideoGl;
            if (videoGL != null) {
                videoGL.clear();
            }
        }
        this.mVideoGl = null;
        this.mIsCreated = false;
    }

    public static /* synthetic */ void clearLocked$default(VideoLayer videoLayer, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        videoLayer.clearLocked(z);
    }

    private final void create() {
        VideoPlayer videoPlayer;
        VideoPlayer videoPlayer2 = new VideoPlayer();
        videoPlayer2.setCompletionListener(new SemMediaPlayer.OnPlaybackCompleteListener() { // from class: com.samsung.android.nexus.video.VideoLayer$create$$inlined$apply$lambda$1
            public final void onPlaybackComplete(SemMediaPlayer semMediaPlayer) {
                Log.i(VideoLayer.TAG, "OnPlaybackCompleteListener : " + semMediaPlayer.getCurrentPosition());
                if (VideoLayer.this.isAutoPlayNextMediaSource()) {
                    VideoLayer.this.setDataSource();
                }
                SemMediaPlayer.OnPlaybackCompleteListener completionListener = VideoLayer.this.getCompletionListener();
                if (completionListener != null) {
                    completionListener.onPlaybackComplete(semMediaPlayer);
                }
            }
        });
        videoPlayer2.setSeekCompleteListener(new SemMediaPlayer.OnSeekCompleteListener() { // from class: com.samsung.android.nexus.video.VideoLayer$create$$inlined$apply$lambda$2
            public final void onSeekComplete(SemMediaPlayer semMediaPlayer) {
                Log.i(VideoLayer.TAG, "OnSeekCompleteListener : " + semMediaPlayer.getCurrentPosition());
                SemMediaPlayer.OnSeekCompleteListener seekCompleteListener = VideoLayer.this.getSeekCompleteListener();
                if (seekCompleteListener != null) {
                    seekCompleteListener.onSeekComplete(semMediaPlayer);
                }
            }
        });
        videoPlayer2.setErrorListener(new SemMediaPlayer.OnErrorListener() { // from class: com.samsung.android.nexus.video.VideoLayer$create$$inlined$apply$lambda$3
            public final boolean onError(SemMediaPlayer semMediaPlayer, int i, int i2) {
                Log.e(VideoLayer.TAG, "MediaPlayer error = " + i + " , " + i2);
                if ((i != 1 || i2 != Integer.MIN_VALUE) && i != -38) {
                    return false;
                }
                VideoLayer.this.reset(1);
                SemMediaPlayer.OnErrorListener errorListener = VideoLayer.this.getErrorListener();
                if (errorListener != null) {
                    errorListener.onError(semMediaPlayer, i, i2);
                }
                return true;
            }
        });
        videoPlayer2.setPreparedListener(new SemMediaPlayer.OnInitCompleteListener() { // from class: com.samsung.android.nexus.video.VideoLayer$create$$inlined$apply$lambda$4
            public final void onInitComplete(SemMediaPlayer semMediaPlayer, SemMediaPlayer.TrackInfo[] trackInfoArr) {
                ReservedActionQueue reservedActionQueue;
                Log.i(VideoLayer.TAG, "OnInitCompleteListener : " + semMediaPlayer.getCurrentPosition());
                SemMediaPlayer.OnInitCompleteListener preparedListener = VideoLayer.this.getPreparedListener();
                if (preparedListener != null) {
                    preparedListener.onInitComplete(semMediaPlayer, trackInfoArr);
                }
                reservedActionQueue = VideoLayer.this.mReservedActions;
                VideoLayer videoLayer = VideoLayer.this;
                synchronized (reservedActionQueue.mList) {
                    try {
                    } catch (ConcurrentModificationException e) {
                        Log.e("ReservedActionQueue", "doAction : " + e.getMessage());
                    }
                    if (reservedActionQueue.mList.isEmpty()) {
                        return;
                    }
                    ArrayList arrayList = reservedActionQueue.mList;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ReservedAction reservedAction = (ReservedAction) obj;
                        Log.i("ReservedActionQueue", "doAction : " + reservedAction.mMethodName);
                        reservedAction.doAction(videoLayer);
                    }
                    reservedActionQueue.mList.clear();
                }
            }
        });
        this.mPlayer = videoPlayer2;
        videoPlayer2.onCreate(getNexusContext());
        this.mVideoGl = prepareVideoGl();
        setDataSource();
        this.mIsCreated = true;
        VideoPlayer videoPlayer3 = this.mPlayer;
        if ((videoPlayer3 != null ? videoPlayer3.getMPlayerState() : null) != VideoPlayer.VideoState.STARTED || (videoPlayer = this.mPlayer) == null) {
            return;
        }
        videoPlayer.play();
    }

    private final VideoGL prepareVideoGl() {
        Log.i(TAG, "Prepare GL");
        return new VideoGL(getAppContext(), this.mPlayer, this.mObjectRect);
    }

    private final void resetAsync() {
        clearAsync();
        create();
        this.mBackupData.restore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean setDataSource() {
        Uri uri = this.mUri;
        if (uri == null && this.mFd == null && this.mAssetFd == null) {
            return false;
        }
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer == null) {
            this.mReservedActions.add(new ReservedAction("setDataSource", null, null));
            Unit unit = Unit.INSTANCE;
            return false;
        }
        if (uri != null) {
            videoPlayer.setMediaUri(uri);
        }
        AssetFileDescriptor assetFileDescriptor = this.mAssetFd;
        if (assetFileDescriptor != null) {
            videoPlayer.setMediaAssetFd(assetFileDescriptor);
        }
        FileDescriptor fileDescriptor = this.mFd;
        if (fileDescriptor != null) {
            videoPlayer.setMediaFd(fileDescriptor);
        }
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setVideoOrientation(videoPlayer.getVideoOrientation());
        } else {
            this.mReservedActions.add(new ReservedAction("setVideoOrientation", new Class[]{String.class}, new String[]{videoPlayer.getVideoOrientation()}));
            Unit unit2 = Unit.INSTANCE;
        }
        VideoStateChangedListener videoStateChangedListener = this.videoStateChangedListener;
        if (videoStateChangedListener != null) {
            videoStateChangedListener.onStateChanged(videoPlayer.getMPlayerState());
        }
        return true;
    }

    public void clear() {
        Log.i(TAG, "Clear");
        clearLocked$default(this, false, 1, null);
    }

    public final SemMediaPlayer.OnPlaybackCompleteListener getCompletionListener() {
        return this.completionListener;
    }

    public final float getContrast() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getContrast();
        }
        return -1.0f;
    }

    public final int getCurrentPosition() {
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer != null) {
            return videoPlayer.getCurrentPosition();
        }
        return 0;
    }

    public final SemMediaPlayer.OnErrorListener getErrorListener() {
        return this.errorListener;
    }

    public final float getGlobalAlpha() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getGlobalAlpha();
        }
        return 1.0f;
    }

    public final boolean getHdrModeEnabled() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getHdrModeEnabled();
        }
        return false;
    }

    public final float getHdrSaturation() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getHdrSaturation();
        }
        return -1.0f;
    }

    public final float getHsvHue() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getHsvHue();
        }
        return 0.0f;
    }

    public final float getHsvSaturation() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getHsvSaturation();
        }
        return 0.5f;
    }

    public final float getHsvValue() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getHsvValue();
        }
        return 0.5f;
    }

    public final float getOffsetX() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getOffsetX();
        }
        return 0.0f;
    }

    public final float getOffsetY() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getOffsetY();
        }
        return 0.0f;
    }

    public final SemMediaPlayer.OnInitCompleteListener getPreparedListener() {
        return this.preparedListener;
    }

    public final SemMediaPlayer.OnSeekCompleteListener getSeekCompleteListener() {
        return this.seekCompleteListener;
    }

    public final boolean getTransparencyEnabled() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getTransparencyEnabled();
        }
        return false;
    }

    public final VideoStateChangedListener getVideoStateChangedListener() {
        return this.videoStateChangedListener;
    }

    public final BaseWorld getWorld() {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            return videoGL.getWorld();
        }
        return null;
    }

    public final boolean isAutoPlayNextMediaSource() {
        return this.isAutoPlayNextMediaSource;
    }

    public final boolean isLoopingMediaSources() {
        return this.isLoopingMediaSources;
    }

    public final boolean isPlaying() {
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer != null) {
            return videoPlayer.isPlaying();
        }
        return false;
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public void onCreate() {
        create();
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public void onDestroy() {
        Log.i(TAG, "Destroyed");
        clear();
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public void onDraw() {
        if (this.mNeedToRecreate) {
            this.mNeedToRecreate = false;
            resetAsync();
        }
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.onDrawFrame();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public void onSizeChanged(int i, int i2) {
        BackupData backupData = this.mBackupData;
        backupData.setWorldWidth(i);
        backupData.setWorldHeight(i2);
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setWorldSize(i, i2);
            return;
        }
        ReservedActionQueue reservedActionQueue = this.mReservedActions;
        Class cls = Integer.TYPE;
        reservedActionQueue.add(new ReservedAction("onSizeChanged", new Class[]{cls, cls}, new Integer[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        Unit unit = Unit.INSTANCE;
    }

    public void onVisibilityChanged(boolean z) {
    }

    public final void pausePlayer() {
        Log.i(TAG, "Pause player.");
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer == null) {
            this.mReservedActions.add(new ReservedAction("pausePlayer", null, null));
            Unit unit = Unit.INSTANCE;
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$3[videoPlayer.getMPlayerState().ordinal()];
        if (i != 1 && i != 2 && i != 3) {
            this.mReservedActions.add(new ReservedAction("pausePlayer", null, null));
            return;
        }
        videoPlayer.pause();
        VideoStateChangedListener videoStateChangedListener = this.videoStateChangedListener;
        if (videoStateChangedListener != null) {
            videoStateChangedListener.onStateChanged(videoPlayer.getMPlayerState());
        }
    }

    public void reset() {
        reset(0);
    }

    public final void seekToPlayer(int i) {
        Log.i(TAG, "Seek to " + i);
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer == null) {
            this.mReservedActions.add(new ReservedAction("seekToPlayer", new Class[]{Integer.TYPE}, new Integer[]{Integer.valueOf(i)}));
            Unit unit = Unit.INSTANCE;
            return;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$4[videoPlayer.getMPlayerState().ordinal()];
        if (i2 == 1) {
            this.mReservedActions.add(new ReservedAction("seekToPlayer", new Class[]{Integer.TYPE}, new Integer[]{Integer.valueOf(i)}));
            return;
        }
        if (i2 == 2 || i2 == 3 || i2 == 4) {
            videoPlayer.seekTo(i);
            VideoStateChangedListener videoStateChangedListener = this.videoStateChangedListener;
            if (videoStateChangedListener != null) {
                videoStateChangedListener.onStateChanged(videoPlayer.getMPlayerState());
            }
        }
    }

    public final void setAutoPlayNextMediaSource(boolean z) {
        this.isAutoPlayNextMediaSource = z;
    }

    public final void setBoundRect(RectF rectF) {
        this.mBackupData.setBoundRect(new RectF(rectF));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setBoundRect(rectF);
        } else {
            this.mReservedActions.add(new ReservedAction("setBoundRect", new Class[]{RectF.class}, new RectF[]{rectF}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setCompletionListener(SemMediaPlayer.OnPlaybackCompleteListener onPlaybackCompleteListener) {
        this.completionListener = onPlaybackCompleteListener;
    }

    public final void setContrast(float f) {
        this.mBackupData.setContrast(Float.valueOf(f));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setContrast(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setContrast", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setCropRect(RectF rectF) {
        this.mBackupData.setCropRect(new RectF(rectF));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setCropRect(rectF);
        } else {
            this.mReservedActions.add(new ReservedAction("setCropRect", new Class[]{RectF.class}, new RectF[]{rectF}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setErrorListener(SemMediaPlayer.OnErrorListener onErrorListener) {
        this.errorListener = onErrorListener;
    }

    public final void setGlobalAlpha(float f) {
        this.mBackupData.setGlobalAlpha(Float.valueOf(f));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setGlobalAlpha(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setGlobalAlpha", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setHdrModeEnabled(boolean z) {
        this.mBackupData.setHdrModeEnabled(Boolean.valueOf(z));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHdrModeEnabled(z);
        } else {
            this.mReservedActions.add(new ReservedAction("setHdrModeEnabled", new Class[]{Boolean.TYPE}, new Boolean[]{Boolean.valueOf(z)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setHdrSaturation(float f) {
        this.mBackupData.setHdrSaturation(Float.valueOf(f));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHdrSaturation(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setHdrSaturation", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setHsvFilterColor(float[] fArr) {
        this.mBackupData.setHsvFilterColor((float[]) fArr.clone());
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHsvFilterColor(fArr);
        } else {
            this.mReservedActions.add(new ReservedAction("setHsvFilterColor", new Class[]{float[].class}, new float[][]{fArr}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setHsvHue(float f) {
        this.mBackupData.getHsvFilterColor()[0] = f;
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHsvHue(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setHsvHue", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setHsvSaturation(float f) {
        this.mBackupData.getHsvFilterColor()[1] = f;
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHsvSaturation(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setHsvSaturation", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setHsvValue(float f) {
        this.mBackupData.getHsvFilterColor()[2] = f;
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHsvValue(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setHsvValue", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setLooping(boolean z) {
        this.mBackupData.setLooping(Boolean.valueOf(z));
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer == null) {
            this.mReservedActions.add(new ReservedAction("setLooping", new Class[]{Boolean.TYPE}, new Boolean[]{Boolean.valueOf(z)}));
            Unit unit = Unit.INSTANCE;
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[videoPlayer.getMPlayerState().ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            videoPlayer.setLooping(z);
        } else {
            this.mReservedActions.add(new ReservedAction("setLooping", new Class[]{Boolean.TYPE}, new Boolean[]{Boolean.valueOf(z)}));
        }
    }

    public final void setLoopingMediaSources(boolean z) {
        this.isLoopingMediaSources = z;
    }

    public final void setMediaSource(Uri uri) {
        this.mUri = uri;
        if (this.mIsCreated) {
            setDataSource();
        }
    }

    public final void setOffset(float f, float f2, float f3) {
        BackupData backupData = this.mBackupData;
        backupData.setOffsetX(Float.valueOf(f));
        backupData.setOffsetY(Float.valueOf(f2));
        backupData.setOffsetZ(Float.valueOf(f3));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setOffset(f, f2, f3);
            return;
        }
        ReservedActionQueue reservedActionQueue = this.mReservedActions;
        Class cls = Float.TYPE;
        reservedActionQueue.add(new ReservedAction("setOffset", new Class[]{cls, cls, cls}, new Float[]{Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)}));
        Unit unit = Unit.INSTANCE;
    }

    public final void setOffsetXY(float f, float f2) {
        BackupData backupData = this.mBackupData;
        backupData.setOffsetX(Float.valueOf(f));
        backupData.setOffsetY(Float.valueOf(f2));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setOffsetXY(f, f2);
            return;
        }
        ReservedActionQueue reservedActionQueue = this.mReservedActions;
        Class cls = Float.TYPE;
        reservedActionQueue.add(new ReservedAction("setOffsetXY", new Class[]{cls, cls}, new Float[]{Float.valueOf(f), Float.valueOf(f2)}));
        Unit unit = Unit.INSTANCE;
    }

    public final void setPreparedListener(SemMediaPlayer.OnInitCompleteListener onInitCompleteListener) {
        this.preparedListener = onInitCompleteListener;
    }

    public final void setRgbFilterColor(float[] fArr) {
        this.mBackupData.setRgbFilterColor(fArr != null ? (float[]) fArr.clone() : null);
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setRgbFilterColor(fArr);
        } else {
            this.mReservedActions.add(new ReservedAction("setRgbFilterColor", new Class[]{float[].class}, new float[][]{fArr}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setRotation(float f, float f2, float f3, float f4) {
        BackupData backupData = this.mBackupData;
        backupData.setRotationAngle(Float.valueOf(f));
        backupData.setRotationX(Float.valueOf(f2));
        backupData.setRotationY(Float.valueOf(f3));
        backupData.setRotationZ(Float.valueOf(f4));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setRotation(f, f2, f3, f4);
            return;
        }
        ReservedActionQueue reservedActionQueue = this.mReservedActions;
        Class cls = Float.TYPE;
        reservedActionQueue.add(new ReservedAction("setRotation", new Class[]{cls, cls, cls, cls}, new Float[]{Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4)}));
        Unit unit = Unit.INSTANCE;
    }

    public final void setScale(float f) {
        this.mBackupData.setScale(Float.valueOf(f));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setScale(f);
        } else {
            this.mReservedActions.add(new ReservedAction("setScale", new Class[]{Float.TYPE}, new Float[]{Float.valueOf(f)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setSeekCompleteListener(SemMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.seekCompleteListener = onSeekCompleteListener;
    }

    public final void setSize(float f, float f2) {
        BackupData backupData = this.mBackupData;
        backupData.setObjectWidth(Float.valueOf(f));
        backupData.setObjectHeight(Float.valueOf(f2));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setSize(f, f2);
            return;
        }
        ReservedActionQueue reservedActionQueue = this.mReservedActions;
        Class cls = Float.TYPE;
        reservedActionQueue.add(new ReservedAction("setSize", new Class[]{cls, cls}, new Float[]{Float.valueOf(f), Float.valueOf(f2)}));
        Unit unit = Unit.INSTANCE;
    }

    public final void setTransparencyEnabled(boolean z) {
        this.mBackupData.setTransparencyEnabled(Boolean.valueOf(z));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setTransparencyEnabled(z);
        } else {
            this.mReservedActions.add(new ReservedAction("setTransparencyEnabled", new Class[]{Boolean.TYPE}, new Boolean[]{Boolean.valueOf(z)}));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setVideoOrientation(String str) {
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setVideoOrientation(str);
        }
    }

    public final void setVideoStateChangedListener(VideoStateChangedListener videoStateChangedListener) {
        this.videoStateChangedListener = videoStateChangedListener;
    }

    public final void startPlayer() {
        Log.i(TAG, "Start player.");
        this.mResetLogger.print();
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer == null) {
            this.mReservedActions.add(new ReservedAction("startPlayer", null, null));
            Unit unit = Unit.INSTANCE;
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$1[videoPlayer.getMPlayerState().ordinal()];
        if (i != 1 && i != 2 && i != 3) {
            this.mReservedActions.add(new ReservedAction("startPlayer", null, null));
            return;
        }
        videoPlayer.play();
        VideoStateChangedListener videoStateChangedListener = this.videoStateChangedListener;
        if (videoStateChangedListener != null) {
            videoStateChangedListener.onStateChanged(videoPlayer.getMPlayerState());
        }
    }

    public final void stopPlayer() {
        Log.i(TAG, "Stop player.");
        VideoPlayer videoPlayer = this.mPlayer;
        if (videoPlayer == null) {
            this.mReservedActions.add(new ReservedAction("stopPlayer", null, null));
            Unit unit = Unit.INSTANCE;
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$2[videoPlayer.getMPlayerState().ordinal()];
        if (i != 1 && i != 2 && i != 3) {
            this.mReservedActions.add(new ReservedAction("stopPlayer", null, null));
            return;
        }
        videoPlayer.stop();
        VideoStateChangedListener videoStateChangedListener = this.videoStateChangedListener;
        if (videoStateChangedListener != null) {
            videoStateChangedListener.onStateChanged(videoPlayer.getMPlayerState());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reset(int i) {
        this.mResetLogger.addCount(i);
        clearLocked(true);
        this.mNeedToRecreate = true;
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public /* bridge */ /* synthetic */ void onVisibilityChanged(Boolean bool) {
        onVisibilityChanged(bool.booleanValue());
    }

    public final void setMediaSource(AssetFileDescriptor assetFileDescriptor) {
        this.mAssetFd = assetFileDescriptor;
        if (this.mIsCreated) {
            setDataSource();
        }
    }

    public VideoLayer() {
        this(null);
    }

    public final void setMediaSource(FileDescriptor fileDescriptor) {
        this.mFd = fileDescriptor;
        if (this.mIsCreated) {
            setDataSource();
        }
    }

    public final void setBoundRect(Rect rect) {
        setBoundRect(new RectF(rect));
    }

    public final void setHdrModeEnabled(boolean z, float f, float f2) {
        BackupData backupData = this.mBackupData;
        backupData.setHdrModeEnabled(Boolean.valueOf(z));
        backupData.setHdrSaturation(Float.valueOf(f));
        backupData.setContrast(Float.valueOf(f2));
        VideoGL videoGL = this.mVideoGl;
        if (videoGL != null) {
            videoGL.setHdrModeEnabled(z, f, f2);
            return;
        }
        ReservedActionQueue reservedActionQueue = this.mReservedActions;
        Class cls = Float.TYPE;
        reservedActionQueue.add(new ReservedAction("setHdrModeEnabled", new Class[]{Boolean.TYPE, cls, cls}, new Object[]{Boolean.valueOf(z), Float.valueOf(f), Float.valueOf(f2)}));
        Unit unit = Unit.INSTANCE;
    }

    public void onAmbientModeChanged() {
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public void onLayerParamsChanged(NexusLayerParams nexusLayerParams) {
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public void onTapEvent(int i, int i2, long j) {
    }
}
