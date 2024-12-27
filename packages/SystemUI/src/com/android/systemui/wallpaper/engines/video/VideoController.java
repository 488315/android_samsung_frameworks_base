package com.android.systemui.wallpaper.engines.video;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.video.VideoController;
import com.android.systemui.wallpaper.engines.video.VideoEngine;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.samsung.android.media.SemMediaPlayer;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VideoController {
    public final String TAG;
    public PlayerSession mActiveSession;
    public final Callback mCallback;
    public final Handler mHandler;
    public Surface mSurface;
    public final VideoSource mVideoSource;
    public final CommandQueue mCommandQueue = new CommandQueue(this, 0);
    public boolean mIsReleased = false;
    public final VideoController mLock = this;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wallpaper.engines.video.VideoController$1, reason: invalid class name */
    public final class AnonymousClass1 implements PlayerSession.Callback {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class Command {
        public final CommandAction mAction;

        public Command(VideoController videoController, CommandAction commandAction) {
            this.mAction = commandAction;
        }

        public String toString() {
            return this.mAction.toString();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum CommandAction {
        PLAY,
        PAUSE,
        SEEK_TO,
        PAUSE_N_SEEK_TO_0
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CommandQueue {
        public final ArrayList mQueue;

        public /* synthetic */ CommandQueue(VideoController videoController, int i) {
            this(videoController);
        }

        public final synchronized void enqueueCommand(Command command) {
            this.mQueue.add(command);
        }

        public final synchronized String listEnqueued() {
            StringBuilder sb;
            try {
                sb = new StringBuilder();
                int size = this.mQueue.size();
                for (int i = 0; i < size; i++) {
                    sb.append(((Command) this.mQueue.get(i)).mAction);
                    if (i != size - 1) {
                        sb.append(", ");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
            return sb.toString();
        }

        public final synchronized Command peekFirstCommand() {
            if (this.mQueue.isEmpty()) {
                return null;
            }
            return (Command) this.mQueue.get(0);
        }

        private CommandQueue(VideoController videoController) {
            this.mQueue = new ArrayList();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PauseAndSeekToFirstFrameCommand extends Command {
        public final boolean mAllowRelease;

        public PauseAndSeekToFirstFrameCommand(VideoController videoController) {
            super(videoController, CommandAction.PAUSE_N_SEEK_TO_0);
            this.mAllowRelease = true;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoController.Command
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction.toString());
            sb.append("(releasable=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.mAllowRelease, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PauseCommand extends Command {
        public final boolean mAllowRelease;

        public PauseCommand(VideoController videoController, boolean z) {
            super(videoController, CommandAction.PAUSE);
            this.mAllowRelease = z;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoController.Command
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction.toString());
            sb.append("(releasable=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.mAllowRelease, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PlayerSession {
        public static int sNextId;
        public final String TAG;
        public final Callback mCallback;
        public final Handler mHandler;
        public final int mId;
        public final PlayerSession mLock;
        public SemMediaPlayer mPlayer;
        public PlayerState mPlayerState;
        public boolean mIsNeverDrawnAtSurface = true;
        public final VideoController$PlayerSession$$ExternalSyntheticLambda0 mAutoReleaseDispatcher = new Runnable() { // from class: com.android.systemui.wallpaper.engines.video.VideoController$PlayerSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VideoController.PlayerSession playerSession = VideoController.PlayerSession.this;
                playerSession.getClass();
                String str = playerSession.TAG;
                Log.i(str, "mAutoReleaseHandler: auto release timer expired. " + playerSession);
                if (playerSession.isReleased()) {
                    Log.i(str, "mAutoReleaseHandler: not need release");
                } else {
                    playerSession.release();
                }
            }
        };

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public interface Callback {
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.wallpaper.engines.video.VideoController$PlayerSession$$ExternalSyntheticLambda0] */
        public PlayerSession(VideoSource videoSource, Handler handler, Callback callback) {
            int i = sNextId;
            this.mId = i;
            sNextId = i + 1;
            String str = "ImageWallpaper_" + videoSource.mWhich + "[VideoController.Session#" + i + "]";
            this.TAG = str;
            this.mLock = this;
            PlayerState playerState = PlayerState.RELEASED;
            this.mPlayerState = playerState;
            this.mHandler = handler;
            this.mCallback = callback;
            Log.i(str, "createAndInitMediaPlayer");
            SemMediaPlayer semMediaPlayer = new SemMediaPlayer();
            semMediaPlayer.setParameter(35004, 1);
            semMediaPlayer.setParameter(37000, 1);
            semMediaPlayer.setOnSeekCompleteListener(new SemMediaPlayer.OnSeekCompleteListener() { // from class: com.android.systemui.wallpaper.engines.video.VideoController$PlayerSession$$ExternalSyntheticLambda1
                public final void onSeekComplete(SemMediaPlayer semMediaPlayer2) {
                    VideoController.PlayerSession playerSession = VideoController.PlayerSession.this;
                    if (playerSession.isReleased()) {
                        Log.i(playerSession.TAG, "onSeekComplete : player released");
                        return;
                    }
                    Log.i(playerSession.TAG, "onSeekComplete");
                    playerSession.setPlayerState(VideoController.PlayerState.PLAYER_READY);
                    synchronized (playerSession.mLock) {
                        playerSession.mIsNeverDrawnAtSurface = false;
                    }
                }
            });
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            semMediaPlayer.setOnInitCompleteListener(new SemMediaPlayer.OnInitCompleteListener() { // from class: com.android.systemui.wallpaper.engines.video.VideoController$PlayerSession$$ExternalSyntheticLambda2
                public final void onInitComplete(SemMediaPlayer semMediaPlayer2, SemMediaPlayer.TrackInfo[] trackInfoArr) {
                    VideoController.PlayerSession playerSession = VideoController.PlayerSession.this;
                    long j = elapsedRealtime;
                    boolean isReleased = playerSession.isReleased();
                    String str2 = playerSession.TAG;
                    if (isReleased) {
                        Log.i(str2, "onInitComplete : player released");
                        return;
                    }
                    Log.i(str2, "onInitComplete : " + playerSession + ", " + (SystemClock.elapsedRealtime() - j) + "ms");
                    playerSession.setPlayerState(VideoController.PlayerState.PLAYER_READY);
                }
            });
            setPlayerState(PlayerState.INITIALIZING);
            VideoSource.VideoLocation videoLocation = videoSource.mVideoLocation;
            if (!(videoLocation == null ? false : videoLocation.setSourceToPlayer(semMediaPlayer))) {
                Log.e(str, "createAndInitMediaPlayer: failed to init the video");
                semMediaPlayer.release();
                setPlayerState(playerState);
                semMediaPlayer = null;
            }
            this.mPlayer = semMediaPlayer;
        }

        public final int getCurrentPosition() {
            synchronized (this.mLock) {
                try {
                    if (isReleased()) {
                        Log.i(this.TAG, "getCurrentPosition: released state");
                        return 0;
                    }
                    SemMediaPlayer semMediaPlayer = this.mPlayer;
                    if (semMediaPlayer != null) {
                        return semMediaPlayer.getCurrentPosition();
                    }
                    Log.w(this.TAG, "getCurrentPosition: player is null. state=[" + this + "]");
                    return 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final Bitmap getCurrentVideoFrame() {
            Bitmap currentFrame;
            synchronized (this.mLock) {
                try {
                    if (isReleased()) {
                        Log.i(this.TAG, "getCurrentVideoFrame: player not initialized");
                        return null;
                    }
                    SemMediaPlayer semMediaPlayer = this.mPlayer;
                    if (semMediaPlayer == null) {
                        Log.i(this.TAG, "getCurrentVideoFrame: player is null");
                        return null;
                    }
                    if (semMediaPlayer.isPlaying()) {
                        Log.i(this.TAG, "getCurrentVideoFrame: video is playing");
                        this.mPlayer.pause();
                        currentFrame = this.mPlayer.getCurrentFrame();
                        this.mPlayer.start();
                    } else {
                        currentFrame = this.mPlayer.getCurrentFrame();
                    }
                    return currentFrame;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isPlayerReady() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mPlayerState == PlayerState.PLAYER_READY;
            }
            return z;
        }

        public final boolean isReleased() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mPlayerState == PlayerState.RELEASED;
            }
            return z;
        }

        public final void onSurfaceAndPlayerReady(Surface surface) {
            synchronized (this.mLock) {
                try {
                    SemMediaPlayer semMediaPlayer = this.mPlayer;
                    if (semMediaPlayer == null) {
                        Log.w(this.TAG, "onSurfaceAndPlayerReady: player is null. state=[" + this + "]");
                        return;
                    }
                    semMediaPlayer.setSurface(surface);
                    this.mPlayer.setVolume(0.0f, 0.0f);
                    this.mPlayer.setVideoScalingMode(2);
                    try {
                        this.mPlayer.setLooping(true);
                        this.mPlayer.setParameter(37001, 1);
                    } catch (IllegalStateException e) {
                        Log.e(this.TAG, "onSurfaceAndPlayerReady : failed setLooping. " + this + ", e=" + e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void pause(boolean z) {
            synchronized (this.mLock) {
                try {
                    if (!isPlayerReady()) {
                        Log.w(this.TAG, "pause: player not ready yet");
                    }
                    if (z) {
                        Handler handler = this.mHandler;
                        VideoController$PlayerSession$$ExternalSyntheticLambda0 videoController$PlayerSession$$ExternalSyntheticLambda0 = this.mAutoReleaseDispatcher;
                        handler.removeCallbacks(videoController$PlayerSession$$ExternalSyntheticLambda0);
                        handler.postDelayed(videoController$PlayerSession$$ExternalSyntheticLambda0, 1000L);
                    } else {
                        this.mHandler.removeCallbacks(this.mAutoReleaseDispatcher);
                    }
                    if (this.mPlayer != null) {
                        Log.i(this.TAG, "pause: SemMediaPlayer.pause");
                        this.mPlayer.pause();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void play() {
            synchronized (this.mLock) {
                try {
                    if (!isPlayerReady()) {
                        Log.w(this.TAG, "play: player not ready yet. state=[" + this + "]");
                        return;
                    }
                    Handler handler = this.mHandler;
                    VideoController$PlayerSession$$ExternalSyntheticLambda0 videoController$PlayerSession$$ExternalSyntheticLambda0 = this.mAutoReleaseDispatcher;
                    handler.removeCallbacks(videoController$PlayerSession$$ExternalSyntheticLambda0);
                    handler.postDelayed(videoController$PlayerSession$$ExternalSyntheticLambda0, 300000L);
                    SemMediaPlayer semMediaPlayer = this.mPlayer;
                    if (semMediaPlayer != null && !semMediaPlayer.isPlaying()) {
                        Log.i(this.TAG, "play: SemMediaPlayer.start");
                        this.mPlayer.start();
                        this.mIsNeverDrawnAtSurface = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void release() {
            Log.i(this.TAG, "release: " + this);
            this.mHandler.removeCallbacks(this.mAutoReleaseDispatcher);
            synchronized (this.mLock) {
                try {
                    if (this.mPlayer != null) {
                        Log.i(this.TAG, "release: SemMediaPlayer.release");
                        this.mPlayer.release();
                    }
                } catch (IllegalStateException e) {
                    Log.e(this.TAG, "release: e=" + e);
                }
                this.mPlayer = null;
            }
            setPlayerState(PlayerState.RELEASED);
        }

        public final void seekTo(int i) {
            synchronized (this.mLock) {
                try {
                    SemMediaPlayer semMediaPlayer = this.mPlayer;
                    if (semMediaPlayer == null) {
                        Log.w(this.TAG, "seekTo: player is null. state=[" + this + "]");
                        return;
                    }
                    boolean isPlaying = semMediaPlayer.isPlaying();
                    boolean z = false;
                    boolean z2 = !isPlaying && getCurrentPosition() == 0;
                    if (!this.mIsNeverDrawnAtSurface && i == 0 && z2) {
                        z = true;
                    }
                    if (z) {
                        Log.d(this.TAG, "seekTo: not need to seek");
                        return;
                    }
                    setPlayerState(PlayerState.SEEKING);
                    synchronized (this.mLock) {
                        Log.i(this.TAG, "seekTo: SemMediaPlayer.seekTo(" + i + ")");
                        this.mPlayer.seekTo(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setPlayerState(PlayerState playerState) {
            PlayerState playerState2;
            boolean z;
            synchronized (this.mLock) {
                playerState2 = this.mPlayerState;
                this.mPlayerState = playerState;
                z = playerState != playerState2;
            }
            if (z) {
                Log.d(this.TAG, "setPlayerState: state changed. " + playerState2 + " -> " + playerState);
                VideoController videoController = VideoController.this;
                synchronized (videoController.mLock) {
                    try {
                        PlayerSession playerSession = videoController.mActiveSession;
                        if (playerSession == null || this == playerSession) {
                            int ordinal = playerState.ordinal();
                            if (ordinal != 1) {
                                if (ordinal == 3) {
                                    synchronized (videoController.mLock) {
                                        videoController.mActiveSession = null;
                                    }
                                }
                            } else if (videoController.isSurfaceAndPlayerReady()) {
                                if (playerState2 == PlayerState.INITIALIZING) {
                                    ((VideoEngine.AnonymousClass1) videoController.mCallback).onSurfaceAndPlayerReady();
                                    synchronized (videoController.mLock) {
                                        try {
                                            PlayerSession playerSession2 = videoController.mActiveSession;
                                            if (playerSession2 != null) {
                                                playerSession2.onSurfaceAndPlayerReady(videoController.mSurface);
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                videoController.dispatchCommands();
                            } else if (!videoController.isSurfaceReady()) {
                                Log.i(videoController.TAG, "onStateChanged: surface is not ready yet");
                            }
                        } else {
                            Log.d(videoController.TAG, "onStateChanged: non-active session state changed. curActive=" + videoController.mActiveSession + ", stateChanged=" + this);
                        }
                    } finally {
                    }
                }
            }
        }

        public final String toString() {
            return "#" + this.mId + ", " + this.mPlayerState;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum PlayerState {
        INITIALIZING,
        PLAYER_READY,
        SEEKING,
        RELEASED
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SeekCommand extends Command {
        public final int mSeekTime;

        public SeekCommand(VideoController videoController, int i) {
            super(videoController, CommandAction.SEEK_TO);
            this.mSeekTime = i;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoController.Command
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction.toString());
            sb.append("(to=");
            return Anchor$$ExternalSyntheticOutline0.m(this.mSeekTime, "ms)", sb);
        }
    }

    public VideoController(VideoSource videoSource, Handler handler, Callback callback) {
        this.TAG = Anchor$$ExternalSyntheticOutline0.m(videoSource.mWhich, "[VideoController]", new StringBuilder("ImageWallpaper_"));
        this.mHandler = handler;
        this.mCallback = callback;
        this.mVideoSource = videoSource;
    }

    public final PlayerSession createPlayerSession() {
        Log.i(this.TAG, "createPlayerSession");
        try {
            return new PlayerSession(this.mVideoSource, this.mHandler, new AnonymousClass1());
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchCommands() {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.video.VideoController.dispatchCommands():void");
    }

    public final boolean isSurfaceAndPlayerReady() {
        boolean z;
        PlayerSession playerSession;
        synchronized (this.mLock) {
            try {
                z = isSurfaceReady() && (playerSession = this.mActiveSession) != null && playerSession.isPlayerReady();
            } finally {
            }
        }
        return z;
    }

    public final boolean isSurfaceReady() {
        boolean z;
        synchronized (this.mLock) {
            try {
                Surface surface = this.mSurface;
                z = surface != null && surface.isValid();
            } finally {
            }
        }
        return z;
    }

    public final void pause(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mIsReleased) {
                    Log.i(this.TAG, "pause: released");
                    return;
                }
                Log.i(this.TAG, "pause");
                this.mCommandQueue.enqueueCommand(new PauseCommand(this, z));
                dispatchCommands();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
