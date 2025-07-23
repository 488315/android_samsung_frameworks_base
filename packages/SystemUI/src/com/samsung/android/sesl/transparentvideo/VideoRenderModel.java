package com.samsung.android.sesl.transparentvideo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel$$ExternalSyntheticLambda1;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$ErrorType;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import com.samsung.android.sesl.transparentvideo.renderer.VideoTextureListener;
import java.util.function.Consumer;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VideoRenderModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public BasicMediaPlayer mediaPlayer;
    public Surface mediaPlayerSurface;
    public final Runnable onCompletion;
    public final Function1 onError;
    public final Runnable onLoaded;
    public PendingState pendingState;
    public State state;
    public final VideoTextureListener textureListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PendingState {
        public static final /* synthetic */ PendingState[] $VALUES;
        public static final PendingState NONE;
        public static final PendingState PENDING_PLAY_MEDIA;

        static {
            PendingState pendingState = new PendingState(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = pendingState;
            PendingState pendingState2 = new PendingState("PENDING_PLAY_MEDIA", 1);
            PENDING_PLAY_MEDIA = pendingState2;
            PendingState[] pendingStateArr = {pendingState, pendingState2};
            $VALUES = pendingStateArr;
            EnumEntriesKt.enumEntries(pendingStateArr);
        }

        private PendingState(String str, int i) {
        }

        public static PendingState valueOf(String str) {
            return (PendingState) Enum.valueOf(PendingState.class, str);
        }

        public static PendingState[] values() {
            return (PendingState[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State DESTROYED;
        public static final State INITIALIZED;
        public static final State PAUSED;
        public static final State READY;
        public static final State RUNNING;

        static {
            State state = new State("INITIALIZED", 0);
            INITIALIZED = state;
            State state2 = new State("READY", 1);
            READY = state2;
            State state3 = new State("RUNNING", 2);
            RUNNING = state3;
            State state4 = new State("PAUSED", 3);
            PAUSED = state4;
            State state5 = new State("DESTROYED", 4);
            DESTROYED = state5;
            State[] stateArr = {state, state2, state3, state4, state5};
            $VALUES = stateArr;
            EnumEntriesKt.enumEntries(stateArr);
        }

        private State(String str, int i) {
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public VideoRenderModel(android.content.Context r11, android.view.TextureView r12, float r13, java.lang.Runnable r14, java.lang.Runnable r15, kotlinx.coroutines.CoroutineScope r16, kotlin.jvm.functions.Function1 r17, kotlinx.coroutines.CoroutineScope r18, int r19, kotlin.jvm.internal.DefaultConstructorMarker r20) {
        /*
            r10 = this;
            r0 = r19
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L1d
            com.samsung.android.sesl.transparentvideo.renderer.GLCoroutineDispatcher$Companion r0 = com.samsung.android.sesl.transparentvideo.renderer.GLCoroutineDispatcher.Companion
            r0.getClass()
            com.samsung.android.sesl.transparentvideo.renderer.GLCoroutineDispatcher r0 = com.samsung.android.sesl.transparentvideo.renderer.GLCoroutineDispatcher.instance
            kotlinx.coroutines.internal.ContextScope r0 = kotlinx.coroutines.CoroutineScopeKt.CoroutineScope(r0)
            r9 = r0
        L12:
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r7 = r16
            r8 = r17
            goto L20
        L1d:
            r9 = r18
            goto L12
        L20:
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sesl.transparentvideo.VideoRenderModel.<init>(android.content.Context, android.view.TextureView, float, java.lang.Runnable, java.lang.Runnable, kotlinx.coroutines.CoroutineScope, kotlin.jvm.functions.Function1, kotlinx.coroutines.CoroutineScope, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final void play() {
        BasicMediaPlayer basicMediaPlayer;
        this.textureListener.notifyPlaybackStarted();
        State state = this.state;
        if (state == State.RUNNING) {
            BasicMediaPlayer basicMediaPlayer2 = this.mediaPlayer;
            if (basicMediaPlayer2 == null || basicMediaPlayer2.mediaPlayer.isPlaying() || (basicMediaPlayer = this.mediaPlayer) == null) {
                return;
            }
            basicMediaPlayer.play();
            return;
        }
        if (state != State.READY && state != State.PAUSED) {
            this.pendingState = PendingState.PENDING_PLAY_MEDIA;
            Log.i("VideoRenderModel", "State not ready. Play pending. (state=" + state + ")");
            return;
        }
        BasicMediaPlayer basicMediaPlayer3 = this.mediaPlayer;
        if (basicMediaPlayer3 != null && !basicMediaPlayer3.isPrepared) {
            try {
                basicMediaPlayer3.mediaPlayer.prepare();
                basicMediaPlayer3.isPrepared = true;
            } catch (IllegalStateException e) {
                Function1 function1 = basicMediaPlayer3.errorListener;
                if (function1 != null) {
                    ((VideoRenderModel$setMediaInternal$2) function1).mo779invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.LOADING_INTERRUPTED, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("IllegalStateException during prepare: ", e.getMessage())));
                }
                Log.e("BasicMediaPlayer", "IllegalStateException in prepare: " + e.getMessage());
            }
        }
        BasicMediaPlayer basicMediaPlayer4 = this.mediaPlayer;
        if (basicMediaPlayer4 != null) {
            basicMediaPlayer4.play();
        }
        State state2 = State.RUNNING;
        this.state = state2;
        Log.i("VideoRenderModel", "Play requested. (state=" + state2 + ")");
    }

    public final void release() {
        BasicMediaPlayer basicMediaPlayer = this.mediaPlayer;
        if (basicMediaPlayer != null) {
            basicMediaPlayer.release();
        }
        this.mediaPlayer = null;
        Surface surface = this.mediaPlayerSurface;
        if (surface != null) {
            surface.release();
        }
        this.mediaPlayerSurface = null;
        this.textureListener.requestRelease();
        this.state = State.DESTROYED;
        Log.i("VideoRenderModel", "Release called. All resources will be cleaned up.");
    }

    public final StandaloneCoroutine setMediaInternal(Function2 function2, Function0 function0) {
        BasicMediaPlayer basicMediaPlayer = this.mediaPlayer;
        boolean z = basicMediaPlayer == null;
        if (basicMediaPlayer != null) {
            basicMediaPlayer.release();
        }
        VideoTextureListener videoTextureListener = this.textureListener;
        videoTextureListener.notifyPlaybackStarted();
        final BasicMediaPlayer basicMediaPlayer2 = new BasicMediaPlayer(videoTextureListener, null, this.onLoaded);
        Surface surface = this.mediaPlayerSurface;
        if (surface != null) {
            basicMediaPlayer2.mediaPlayer.setSurface(surface);
            basicMediaPlayer2.hasSurface = true;
        }
        State state = this.state;
        state.getClass();
        if (state == State.RUNNING || state == State.PAUSED) {
            this.state = State.READY;
        }
        if (z) {
            videoTextureListener.renderer.connectVideoSurfaceTexture();
        }
        this.mediaPlayer = basicMediaPlayer2;
        final VideoRenderModel$$ExternalSyntheticLambda1 videoRenderModel$$ExternalSyntheticLambda1 = new VideoRenderModel$$ExternalSyntheticLambda1(this, 1);
        basicMediaPlayer2.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda4
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) {
                BasicMediaPlayer basicMediaPlayer3 = BasicMediaPlayer.this;
                VideoRenderModel$$ExternalSyntheticLambda1 videoRenderModel$$ExternalSyntheticLambda12 = videoRenderModel$$ExternalSyntheticLambda1;
                Log.i("BasicMediaPlayer", "onComplete() lastCallState=" + basicMediaPlayer3.lastCallState);
                videoRenderModel$$ExternalSyntheticLambda12.run();
            }
        });
        BasicMediaPlayer basicMediaPlayer3 = this.mediaPlayer;
        if (basicMediaPlayer3 != null) {
            final VideoRenderModel$setMediaInternal$2 videoRenderModel$setMediaInternal$2 = new VideoRenderModel$setMediaInternal$2(this);
            basicMediaPlayer3.errorListener = videoRenderModel$setMediaInternal$2;
            basicMediaPlayer3.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda3
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Function1 function1 = Function1.this;
                    int i3 = BasicMediaPlayer.$r8$clinit;
                    function1.mo779invoke(new IMediaPlayer$MediaError(i != 1 ? i != 100 ? i != 200 ? i2 != -1010 ? i2 != -1007 ? i2 != -1004 ? i2 != -110 ? IMediaPlayer$ErrorType.UNKNOWN : IMediaPlayer$ErrorType.TIMED_OUT : IMediaPlayer$ErrorType.IO : IMediaPlayer$ErrorType.MALFORMED : IMediaPlayer$ErrorType.UNSUPPORTED : IMediaPlayer$ErrorType.NOT_VALID_FOR_PROGRESSIVE_PLAYBACK : IMediaPlayer$ErrorType.SERVER_DIED : IMediaPlayer$ErrorType.UNKNOWN, ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Error code: ", ", Extra code: ")));
                    return true;
                }
            });
        }
        return BuildersKt.launch$default(this.coroutineScope, null, null, new VideoRenderModel$setMediaInternal$3(function2, basicMediaPlayer2, function0, null), 3);
    }

    public VideoRenderModel(Context context, TextureView textureView, float f, Runnable runnable, Runnable runnable2, CoroutineScope coroutineScope, Function1 function1, CoroutineScope coroutineScope2) {
        this.context = context;
        this.onLoaded = runnable;
        this.onCompletion = runnable2;
        this.coroutineScope = coroutineScope;
        this.onError = function1;
        this.state = State.INITIALIZED;
        this.pendingState = PendingState.NONE;
        VideoTextureListener videoTextureListener = new VideoTextureListener(f, new Consumer() { // from class: com.samsung.android.sesl.transparentvideo.VideoRenderModel$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                VideoRenderModel videoRenderModel = VideoRenderModel.this;
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                BuildersKt.launch$default(videoRenderModel.coroutineScope, MainDispatcherLoader.dispatcher, null, new VideoRenderModel$textureListener$1$1(videoRenderModel, (SurfaceTexture) obj, null), 2);
            }
        }, new VideoRenderModel$$ExternalSyntheticLambda1(this, 0), coroutineScope2);
        this.textureListener = videoTextureListener;
        textureView.setSurfaceTextureListener(videoTextureListener);
        textureView.setOpaque(false);
        if (textureView.isAvailable()) {
            TextureView.SurfaceTextureListener surfaceTextureListener = textureView.getSurfaceTextureListener();
            surfaceTextureListener.getClass();
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.getClass();
            surfaceTextureListener.onSurfaceTextureAvailable(surfaceTexture, textureView.getWidth(), textureView.getHeight());
        }
    }
}
