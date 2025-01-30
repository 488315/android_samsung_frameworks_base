package com.samsung.android.nexus.video;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.Surface;
import com.samsung.android.media.SemMediaPlayer;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.video.VideoPlayer;
import java.io.FileDescriptor;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VideoPlayer {
    public static final int MEDIA_ERROR_NOT_PREPARED = -38;
    public static final int MEDIA_ERROR_SYSTEM = Integer.MIN_VALUE;
    private static final int SEM_KEY_PARAMETER_EXCLUDE_AUDIO_TRACK = 35004;
    private static final int SEM_KEY_PARAMETER_SEAMLESS_LOOPING = 37001;
    private static final int SEM_KEY_PARAMETER_VIDEO_TEAR_DOWN = 37000;
    private SemMediaPlayer.OnPlaybackCompleteListener completionListener;
    private SemMediaPlayer.OnErrorListener errorListener;
    private boolean isLooping;
    private final SemMediaPlayer mMediaPlayer;
    private NexusContext mNexusContext;
    private VideoState mPlayerState;
    private AssetFileDescriptor mediaAssetFd;
    private FileDescriptor mediaFd;
    private Uri mediaUri;
    private SemMediaPlayer.OnInitCompleteListener preparedListener;
    private SemMediaPlayer.OnSeekCompleteListener seekCompleteListener;
    private String videoOrientation;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "VideoPlayer";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum VideoState {
        IDLE,
        INITIALIZED,
        STARTED,
        PAUSED
    }

    public VideoPlayer() {
        final SemMediaPlayer semMediaPlayer = new SemMediaPlayer();
        semMediaPlayer.setOnInitCompleteListener(new SemMediaPlayer.OnInitCompleteListener() { // from class: com.samsung.android.nexus.video.VideoPlayer$$special$$inlined$apply$lambda$1
            public final void onInitComplete(SemMediaPlayer semMediaPlayer2, SemMediaPlayer.TrackInfo[] trackInfoArr) {
                String str;
                str = VideoPlayer.TAG;
                Log.m262i(str, "Prepare is done.");
                this.setMPlayerState(VideoPlayer.VideoState.INITIALIZED);
                semMediaPlayer.setParameter(35004, 1);
                semMediaPlayer.setParameter(37000, 1);
                SemMediaPlayer.OnInitCompleteListener preparedListener = this.getPreparedListener();
                if (preparedListener != null) {
                    preparedListener.onInitComplete(semMediaPlayer2, trackInfoArr);
                }
                semMediaPlayer.setLooping(semMediaPlayer2.isLooping());
                if (this.getMPlayerState() == VideoPlayer.VideoState.STARTED) {
                    this.play();
                }
            }
        });
        semMediaPlayer.setOnPlaybackCompleteListener(new SemMediaPlayer.OnPlaybackCompleteListener() { // from class: com.samsung.android.nexus.video.VideoPlayer$$special$$inlined$apply$lambda$2
            public final void onPlaybackComplete(SemMediaPlayer semMediaPlayer2) {
                String str;
                str = VideoPlayer.TAG;
                Log.m262i(str, "Play completed.");
                SemMediaPlayer.OnPlaybackCompleteListener completionListener = VideoPlayer.this.getCompletionListener();
                if (completionListener != null) {
                    completionListener.onPlaybackComplete(semMediaPlayer2);
                }
            }
        });
        semMediaPlayer.setOnSeekCompleteListener(new SemMediaPlayer.OnSeekCompleteListener() { // from class: com.samsung.android.nexus.video.VideoPlayer$$special$$inlined$apply$lambda$3
            public final void onSeekComplete(SemMediaPlayer semMediaPlayer2) {
                String str;
                str = VideoPlayer.TAG;
                Log.m262i(str, "seekTo completed.");
                SemMediaPlayer.OnSeekCompleteListener seekCompleteListener = VideoPlayer.this.getSeekCompleteListener();
                if (seekCompleteListener != null) {
                    seekCompleteListener.onSeekComplete(semMediaPlayer2);
                }
            }
        });
        semMediaPlayer.setOnErrorListener(new SemMediaPlayer.OnErrorListener() { // from class: com.samsung.android.nexus.video.VideoPlayer$$special$$inlined$apply$lambda$4
            public final boolean onError(SemMediaPlayer semMediaPlayer2, int i, int i2) {
                String str;
                str = VideoPlayer.TAG;
                Log.m261e(str, "error: mp = " + semMediaPlayer2 + ", what = " + i + ", extra = " + i2);
                SemMediaPlayer.OnErrorListener errorListener = VideoPlayer.this.getErrorListener();
                if (errorListener == null) {
                    return true;
                }
                errorListener.onError(semMediaPlayer2, i, i2);
                return true;
            }
        });
        this.mMediaPlayer = semMediaPlayer;
        this.mPlayerState = VideoState.IDLE;
    }

    private final void loggingError(String str) {
        Log.m261e(TAG, str);
    }

    private final void setDataSourceAssetFd() {
        Log.m262i(TAG, "Set data asset source fd = " + this.mediaAssetFd + ", is looping = " + this.isLooping);
        if (this.mediaAssetFd == null) {
            loggingError("Set data source error. Media asset fd is null.");
            return;
        }
        if (this.mNexusContext == null) {
            loggingError("Set data source error. context is null.");
            return;
        }
        this.mPlayerState = VideoState.IDLE;
        try {
            SemMediaPlayer semMediaPlayer = this.mMediaPlayer;
            if (semMediaPlayer.isPlaying()) {
                stop();
            }
            semMediaPlayer.reset();
            AssetFileDescriptor assetFileDescriptor = this.mediaAssetFd;
            if (assetFileDescriptor == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            AssetFileDescriptor assetFileDescriptor2 = this.mediaAssetFd;
            if (assetFileDescriptor2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            long startOffset = assetFileDescriptor2.getStartOffset();
            AssetFileDescriptor assetFileDescriptor3 = this.mediaAssetFd;
            if (assetFileDescriptor3 != null) {
                semMediaPlayer.init(fileDescriptor, startOffset, assetFileDescriptor3.getLength());
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        } catch (Exception e) {
            if ((e instanceof IOException) || (e instanceof IllegalArgumentException) || (e instanceof RuntimeException) || (e instanceof IllegalStateException)) {
                Log.m261e(TAG, "Set data source fd failed : " + e.getMessage());
            }
        }
    }

    private final void setDataSourceFd() {
        Log.m262i(TAG, "Set data source fd = " + this.mediaFd + ", is looping = " + this.isLooping);
        if (this.mediaFd == null) {
            loggingError("Set data source error. Media fd is null.");
            return;
        }
        if (this.mNexusContext == null) {
            loggingError("Set data source error. context is null.");
            return;
        }
        this.mPlayerState = VideoState.IDLE;
        try {
            SemMediaPlayer semMediaPlayer = this.mMediaPlayer;
            if (semMediaPlayer.isPlaying()) {
                stop();
            }
            semMediaPlayer.reset();
            FileDescriptor fileDescriptor = this.mediaFd;
            if (fileDescriptor != null) {
                semMediaPlayer.init(fileDescriptor);
            }
        } catch (Exception e) {
            if ((e instanceof IOException) || (e instanceof IllegalArgumentException) || (e instanceof RuntimeException) || (e instanceof IllegalStateException)) {
                Log.m261e(TAG, "Set data source fd failed : " + e.getMessage());
            }
        }
    }

    private final void setDataSourceUri() {
        Log.m262i(TAG, "Set data source uri = " + this.mediaUri + ", is looping = " + this.isLooping);
        if (this.mediaUri == null) {
            loggingError("Set data source error. Media uri is null.");
            return;
        }
        if (this.mNexusContext == null) {
            loggingError("Set data source error. context is null.");
            return;
        }
        this.mPlayerState = VideoState.IDLE;
        try {
            SemMediaPlayer semMediaPlayer = this.mMediaPlayer;
            if (semMediaPlayer.isPlaying()) {
                stop();
            }
            semMediaPlayer.reset();
            NexusContext nexusContext = this.mNexusContext;
            if (nexusContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Context context = nexusContext.mContext;
            Uri uri = this.mediaUri;
            if (uri != null) {
                semMediaPlayer.init(context, uri);
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        } catch (Exception e) {
            if ((e instanceof IOException) || (e instanceof IllegalArgumentException) || (e instanceof RuntimeException) || (e instanceof IllegalStateException)) {
                Log.m261e(TAG, "Set data source fd failed : " + e.getMessage());
            }
        }
    }

    public final SemMediaPlayer.OnPlaybackCompleteListener getCompletionListener() {
        return this.completionListener;
    }

    /* renamed from: getCurrentPosition, reason: collision with other method in class */
    public final long m2791getCurrentPosition() {
        return this.mMediaPlayer.getCurrentPosition();
    }

    public final SemMediaPlayer.OnErrorListener getErrorListener() {
        return this.errorListener;
    }

    public final VideoState getMPlayerState() {
        return this.mPlayerState;
    }

    public final AssetFileDescriptor getMediaAssetFd() {
        return this.mediaAssetFd;
    }

    public final FileDescriptor getMediaFd() {
        return this.mediaFd;
    }

    public final Uri getMediaUri() {
        return this.mediaUri;
    }

    public final SemMediaPlayer.OnInitCompleteListener getPreparedListener() {
        return this.preparedListener;
    }

    public final SemMediaPlayer.OnSeekCompleteListener getSeekCompleteListener() {
        return this.seekCompleteListener;
    }

    public final String getVideoOrientation() {
        return this.videoOrientation;
    }

    public final Point getVideoSize() {
        SemMediaPlayer.TrackInfo[] trackInfo = this.mMediaPlayer.getTrackInfo();
        return new Point(trackInfo[1].getVideoWidth(), trackInfo[1].getVideoHeight());
    }

    public final boolean isLooping() {
        return this.isLooping;
    }

    public final boolean isPlaying() {
        try {
            return this.mMediaPlayer.isPlaying();
        } catch (IllegalStateException e) {
            Log.m261e(TAG, "Cannot set looping to the media player : " + e.getMessage());
            return false;
        }
    }

    public final void onCreate(NexusContext nexusContext) {
        this.mNexusContext = nexusContext;
    }

    public final void pause() {
        Log.m262i(TAG, "Pause : state = " + this.mPlayerState);
        if (this.mPlayerState.compareTo(VideoState.IDLE) > 0) {
            try {
                if (this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.pause();
                }
                this.mPlayerState = VideoState.PAUSED;
            } catch (IllegalStateException e) {
                Log.m261e(TAG, "Cannot pause media player : " + e.getMessage());
            }
        }
    }

    public final void play() {
        Log.m262i(TAG, "Start play state = " + this.mPlayerState + ", player looping state = " + this.mMediaPlayer.isLooping());
        if (this.mPlayerState.compareTo(VideoState.IDLE) > 0) {
            try {
                if (!this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.start();
                }
                this.mPlayerState = VideoState.STARTED;
            } catch (IllegalStateException e) {
                Log.m261e(TAG, "Cannot start media player : " + e.getMessage());
            }
        }
    }

    public final void release() {
        Log.m262i(TAG, "Release");
        stop();
        this.mMediaPlayer.setOnErrorListener((SemMediaPlayer.OnErrorListener) null);
        this.mMediaPlayer.setOnPlaybackCompleteListener((SemMediaPlayer.OnPlaybackCompleteListener) null);
        this.mMediaPlayer.setOnInitCompleteListener((SemMediaPlayer.OnInitCompleteListener) null);
        this.mMediaPlayer.release();
    }

    public final void seekTo(int i) {
        String str = TAG;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("Seek to position : ", i, " , state = ");
        m1m.append(this.mPlayerState);
        Log.m262i(str, m1m.toString());
        if (this.mPlayerState.compareTo(VideoState.IDLE) > 0) {
            try {
                this.mMediaPlayer.seekTo(i);
            } catch (IllegalArgumentException e) {
                Log.m261e(TAG, "Cannot seek to in media player : " + e.getMessage());
            } catch (IllegalStateException e2) {
                Log.m261e(TAG, "Cannot seek to in media player : " + e2.getMessage());
            }
        }
    }

    public final void setCompletionListener(SemMediaPlayer.OnPlaybackCompleteListener onPlaybackCompleteListener) {
        this.completionListener = onPlaybackCompleteListener;
    }

    public final void setErrorListener(SemMediaPlayer.OnErrorListener onErrorListener) {
        this.errorListener = onErrorListener;
    }

    public final void setLooping(boolean z) {
        Log.m262i(TAG, "Set looping : " + z);
        this.isLooping = z;
        try {
            this.mMediaPlayer.setLooping(z);
        } catch (IllegalStateException e) {
            Log.m261e(TAG, "Cannot get playing state of media player : " + e.getMessage());
        }
    }

    public final void setMPlayerState(VideoState videoState) {
        this.mPlayerState = videoState;
    }

    public final void setMediaAssetFd(AssetFileDescriptor assetFileDescriptor) {
        this.mediaAssetFd = assetFileDescriptor;
        setDataSourceAssetFd();
    }

    public final void setMediaFd(FileDescriptor fileDescriptor) {
        this.mediaFd = fileDescriptor;
        setDataSourceFd();
    }

    public final void setMediaUri(Uri uri) {
        this.mediaUri = uri;
        setDataSourceUri();
    }

    public final void setPreparedListener(SemMediaPlayer.OnInitCompleteListener onInitCompleteListener) {
        this.preparedListener = onInitCompleteListener;
    }

    public final void setSeekCompleteListener(SemMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.seekCompleteListener = onSeekCompleteListener;
    }

    public final void setSurface(Surface surface) {
        Log.m262i(TAG, "Set surface : " + surface);
        try {
            this.mMediaPlayer.setSurface(surface);
        } catch (IllegalStateException e) {
            Log.m261e(TAG, "Cannot set surface to media player : " + e.getMessage());
        }
    }

    public final void setVolume(float f) {
        Log.m262i(TAG, "Set volume : " + f);
        this.mMediaPlayer.setVolume(f, f);
    }

    public final void stop() {
        Log.m262i(TAG, "Stop : state = " + this.mPlayerState);
        VideoState videoState = this.mPlayerState;
        VideoState videoState2 = VideoState.IDLE;
        if (videoState.compareTo(videoState2) > 0) {
            try {
                if (this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.reset();
                }
                this.mPlayerState = videoState2;
            } catch (IllegalStateException e) {
                Log.m261e(TAG, "Cannot stop media player : " + e.getMessage());
            }
        }
    }

    public final int getCurrentPosition() {
        int currentPosition = this.mMediaPlayer.getCurrentPosition();
        Log.m262i(TAG, "Get current position : " + currentPosition);
        return currentPosition;
    }
}
