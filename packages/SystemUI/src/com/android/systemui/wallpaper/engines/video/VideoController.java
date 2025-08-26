package com.android.systemui.wallpaper.engines.video;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.android.systemui.wallpaper.engines.video.VideoController;
import com.android.systemui.wallpaper.engines.video.VideoEngine;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.samsung.android.media.SemMediaPlayer;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class VideoController {
    public final String TAG;
    public PlayerSession mActiveSession;
    public final Callback mCallback;
    public final Handler mHandler;
    public Surface mSurface;
    public final VideoSource mVideoSource;
    public final CommandQueue mCommandQueue = new CommandQueue(0);
    public boolean mIsReleased = false;
    public final VideoController mLock = this;

    /* renamed from: com.android.systemui.wallpaper.engines.video.VideoController$1, reason: invalid class name */
    public class AnonymousClass1 implements PlayerSession.Callback {
        public AnonymousClass1() {
        }
    }

    public interface Callback {
    }

    public class Command {
        public final CommandAction mAction;

        public Command(CommandAction commandAction) {
            this.mAction = commandAction;
        }

        public String toString() {
            return this.mAction.toString();
        }
    }

    enum CommandAction {
        PLAY,
        PAUSE,
        SEEK_TO,
        PAUSE_N_SEEK_TO_0,
        RELEASE_SESSION
    }

    public class CommandQueue {
        public final ArrayList mQueue;

        public /* synthetic */ CommandQueue(int i) {
            this();
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

        private CommandQueue() {
            this.mQueue = new ArrayList();
        }
    }

    public class PauseAndSeekToFirstFrameCommand extends Command {
        public final boolean mAllowRelease;

        public PauseAndSeekToFirstFrameCommand() {
            super(CommandAction.PAUSE_N_SEEK_TO_0);
            this.mAllowRelease = true;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoController.Command
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction.toString());
            sb.append("(releasable=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mAllowRelease, ")");
        }
    }

    public class PauseCommand extends Command {
        public final boolean mAllowRelease;

        public PauseCommand(boolean z) {
            super(CommandAction.PAUSE);
            this.mAllowRelease = z;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoController.Command
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction.toString());
            sb.append("(releasable=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mAllowRelease, ")");
        }
    }

    public class PlayerSession {
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
                VideoController.PlayerSession playerSession = this.f$0;
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
                    VideoController.PlayerSession playerSession = this.f$0;
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
            final long jElapsedRealtime = SystemClock.elapsedRealtime();
            semMediaPlayer.setOnInitCompleteListener(new SemMediaPlayer.OnInitCompleteListener() { // from class: com.android.systemui.wallpaper.engines.video.VideoController$PlayerSession$$ExternalSyntheticLambda2
                public final void onInitComplete(SemMediaPlayer semMediaPlayer2, SemMediaPlayer.TrackInfo[] trackInfoArr) {
                    VideoController.PlayerSession playerSession = this.f$0;
                    long j = jElapsedRealtime;
                    boolean zIsReleased = playerSession.isReleased();
                    String str2 = playerSession.TAG;
                    if (zIsReleased) {
                        Log.i(str2, "onInitComplete : player released");
                        return;
                    }
                    Log.i(str2, "onInitComplete : " + playerSession + ", " + (SystemClock.elapsedRealtime() - j) + "ms");
                    playerSession.setPlayerState(VideoController.PlayerState.PLAYER_READY);
                }
            });
            semMediaPlayer.setOnInfoListener(new SemMediaPlayer.OnInfoListener() { // from class: com.android.systemui.wallpaper.engines.video.VideoController$PlayerSession$$ExternalSyntheticLambda3
                public final boolean onInfo(SemMediaPlayer semMediaPlayer2, int i2, int i3) {
                    VideoController.PlayerSession playerSession = this.f$0;
                    playerSession.getClass();
                    Log.i(playerSession.TAG, "onInfo : what=" + i2 + ", extra=" + i3);
                    if (i2 == 3) {
                        playerSession.mIsNeverDrawnAtSurface = false;
                    }
                    return false;
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
                        VideoController$PlayerSession$$ExternalSyntheticLambda0 videoController$PlayerSession$$ExternalSyntheticLambda0 = this.mAutoReleaseDispatcher;
                        Handler handler = this.mHandler;
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
                    VideoController$PlayerSession$$ExternalSyntheticLambda0 videoController$PlayerSession$$ExternalSyntheticLambda0 = this.mAutoReleaseDispatcher;
                    Handler handler = this.mHandler;
                    handler.removeCallbacks(videoController$PlayerSession$$ExternalSyntheticLambda0);
                    handler.postDelayed(videoController$PlayerSession$$ExternalSyntheticLambda0, 300000L);
                    SemMediaPlayer semMediaPlayer = this.mPlayer;
                    if (semMediaPlayer != null && !semMediaPlayer.isPlaying()) {
                        Log.i(this.TAG, "play: SemMediaPlayer.start");
                        this.mPlayer.start();
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
                } catch (IllegalStateException e) {
                    Log.e(this.TAG, "release: e=" + e);
                }
                if (this.mPlayer != null) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    this.mPlayer.release();
                    long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                    Log.i(this.TAG, "release: SemMediaPlayer.release elapsed=" + jElapsedRealtime2 + "ms");
                    this.mPlayer = null;
                } else {
                    this.mPlayer = null;
                }
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
                    boolean zIsPlaying = semMediaPlayer.isPlaying();
                    boolean z = false;
                    boolean z2 = !zIsPlaying && getCurrentPosition() == 0;
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
                        if (playerSession != null && this != playerSession) {
                            Log.d(videoController.TAG, "onStateChanged: non-active session state changed. curActive=" + videoController.mActiveSession + ", stateChanged=" + this);
                            return;
                        }
                        int iOrdinal = playerState.ordinal();
                        if (iOrdinal != 1) {
                            if (iOrdinal != 3) {
                                return;
                            }
                            synchronized (videoController.mLock) {
                                videoController.mActiveSession = null;
                            }
                            return;
                        }
                        if (!videoController.isSurfaceAndPlayerReady()) {
                            if (videoController.isSurfaceReady()) {
                                return;
                            }
                            Log.i(videoController.TAG, "onStateChanged: surface is not ready yet");
                            return;
                        }
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
                    } finally {
                    }
                }
            }
        }

        public final String toString() {
            return "#" + this.mId + ", " + this.mPlayerState;
        }
    }

    enum PlayerState {
        INITIALIZING,
        PLAYER_READY,
        SEEKING,
        RELEASED
    }

    public class SeekCommand extends Command {
        public final int mSeekTime;

        public SeekCommand(int i) {
            super(CommandAction.SEEK_TO);
            this.mSeekTime = i;
        }

        @Override // com.android.systemui.wallpaper.engines.video.VideoController.Command
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction.toString());
            sb.append("(to=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mSeekTime, "ms)", sb);
        }
    }

    public VideoController(VideoSource videoSource, Handler handler, Callback callback) {
        this.TAG = ReorderTile$$ExternalSyntheticOutline0.m(videoSource.mWhich, "[VideoController]", new StringBuilder("ImageWallpaper_"));
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

    /* JADX WARN: Removed duplicated region for block: B:111:0x0147 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchCommands() {
        Command commandPeekFirstCommand;
        CommandQueue commandQueue;
        synchronized (this.mLock) {
            Log.i(this.TAG, "dispatchCommands: " + this.mActiveSession + ", enqueued=[" + this.mCommandQueue.listEnqueued() + "]");
        }
        while (true) {
            commandPeekFirstCommand = this.mCommandQueue.peekFirstCommand();
            if (commandPeekFirstCommand == null) {
                return;
            }
            synchronized (this.mLock) {
                try {
                    int iOrdinal = commandPeekFirstCommand.mAction.ordinal();
                    if (iOrdinal == 0) {
                        if (this.mActiveSession != null) {
                            if (!isSurfaceAndPlayerReady()) {
                                break;
                            } else {
                                this.mActiveSession.play();
                            }
                        } else {
                            PlayerSession playerSessionCreatePlayerSession = createPlayerSession();
                            this.mActiveSession = playerSessionCreatePlayerSession;
                            if (playerSessionCreatePlayerSession != null) {
                                break;
                            }
                        }
                    } else if (iOrdinal == 1) {
                        if (this.mActiveSession == null) {
                            Log.i(this.TAG, "dispatchCommands: no active session to pause");
                        } else if (isSurfaceAndPlayerReady()) {
                            this.mActiveSession.pause(((PauseCommand) commandPeekFirstCommand).mAllowRelease);
                        }
                        Log.i(this.TAG, "dispatchCommands: head=" + commandPeekFirstCommand + " -> consumed");
                        commandQueue = this.mCommandQueue;
                        synchronized (commandQueue) {
                        }
                    } else if (iOrdinal == 2) {
                        if (this.mActiveSession != null) {
                            if (!isSurfaceAndPlayerReady()) {
                                break;
                            }
                            int i = ((SeekCommand) commandPeekFirstCommand).mSeekTime;
                            Log.i(this.TAG, "dispatchCommands: perform seek. time=" + i);
                            this.mActiveSession.seekTo(i);
                        } else {
                            PlayerSession playerSessionCreatePlayerSession2 = createPlayerSession();
                            this.mActiveSession = playerSessionCreatePlayerSession2;
                            if (playerSessionCreatePlayerSession2 != null) {
                                break;
                            }
                        }
                    } else if (iOrdinal != 3) {
                        if (iOrdinal != 4) {
                            Log.w(this.TAG, "dispatchCommands: unexpected cmd : " + commandPeekFirstCommand.mAction);
                        } else {
                            PlayerSession playerSession = this.mActiveSession;
                            if (playerSession == null || playerSession.isReleased()) {
                                Log.i(this.TAG, "dispatchCommands: no active session to release");
                            } else if (isSurfaceAndPlayerReady()) {
                                this.mActiveSession.release();
                            }
                        }
                        Log.i(this.TAG, "dispatchCommands: head=" + commandPeekFirstCommand + " -> consumed");
                        commandQueue = this.mCommandQueue;
                        synchronized (commandQueue) {
                            if (commandQueue.peekFirstCommand() != null) {
                                commandQueue.mQueue.remove(0);
                            }
                        }
                    } else if (this.mActiveSession == null) {
                        PlayerSession playerSessionCreatePlayerSession3 = createPlayerSession();
                        this.mActiveSession = playerSessionCreatePlayerSession3;
                        if (playerSessionCreatePlayerSession3 == null) {
                        }
                    } else if (isSurfaceAndPlayerReady()) {
                        this.mActiveSession.pause(((PauseAndSeekToFirstFrameCommand) commandPeekFirstCommand).mAllowRelease);
                        this.mActiveSession.seekTo(0);
                    }
                } finally {
                }
            }
            Log.i(this.TAG, "dispatchCommands: head=" + commandPeekFirstCommand + " -> consumed");
            commandQueue = this.mCommandQueue;
            synchronized (commandQueue) {
            }
        }
        Log.i(this.TAG, "dispatchCommands: head=" + commandPeekFirstCommand + " -> will consume later");
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
                this.mCommandQueue.enqueueCommand(new PauseCommand(z));
                dispatchCommands();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
