package com.samsung.android.sesl.transparentvideo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel$$ExternalSyntheticLambda1;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel$$ExternalSyntheticLambda5;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda2;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda5;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$ErrorType;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import com.samsung.android.sesl.transparentvideo.renderer.GLCoroutineDispatcher;
import com.samsung.android.sesl.transparentvideo.renderer.VideoTextureListener;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    /* renamed from: com.samsung.android.sesl.transparentvideo.VideoRenderModel$setMediaInternal$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ BasicMediaPlayer $basicMediaPlayer;
        final /* synthetic */ Function0 $onLoadedMediaSource;
        final /* synthetic */ Function2 $setMediaSource;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function2 function2, BasicMediaPlayer basicMediaPlayer, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$setMediaSource = function2;
            this.$basicMediaPlayer = basicMediaPlayer;
            this.$onLoadedMediaSource = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$setMediaSource, this.$basicMediaPlayer, this.$onLoadedMediaSource, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Function2 function2 = this.$setMediaSource;
                BasicMediaPlayer basicMediaPlayer = this.$basicMediaPlayer;
                this.label = 1;
                if (function2.invoke(basicMediaPlayer, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            BasicMediaPlayer basicMediaPlayer2 = this.$basicMediaPlayer;
            if (!basicMediaPlayer2.hasMedia) {
                Log.e("BasicMediaPlayer", "Cannot prepare: No media source set");
                VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda5 = basicMediaPlayer2.errorListener;
                if (videoRenderModel$$ExternalSyntheticLambda5 != null) {
                    videoRenderModel$$ExternalSyntheticLambda5.mo781invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.LOADING_INTERRUPTED, "No media source set before prepare"));
                }
            } else if (!basicMediaPlayer2.isPrepared && !basicMediaPlayer2.isPreparing) {
                basicMediaPlayer2.isPreparing = true;
                if (!basicMediaPlayer2.executeSafely("prepareAsync", IMediaPlayer$ErrorType.LOADING_INTERRUPTED, new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer2, 3))) {
                    basicMediaPlayer2.isPreparing = false;
                }
            }
            this.$onLoadedMediaSource.invoke();
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public VideoRenderModel(Context context, TextureView textureView, float f, Runnable runnable, Runnable runnable2, CoroutineScope coroutineScope, Function1 function1, CoroutineScope coroutineScope2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        CoroutineScope CoroutineScope;
        if ((i & 128) != 0) {
            GLCoroutineDispatcher.Companion.getClass();
            CoroutineScope = CoroutineScopeKt.CoroutineScope(GLCoroutineDispatcher.instance);
        } else {
            CoroutineScope = coroutineScope2;
        }
        this(context, textureView, f, runnable, runnable2, coroutineScope, function1, CoroutineScope);
    }

    public final void play() {
        BasicMediaPlayer basicMediaPlayer;
        this.textureListener.notifyPlaybackStarted();
        State state = this.state;
        State state2 = State.RUNNING;
        if (state == state2) {
            BasicMediaPlayer basicMediaPlayer2 = this.mediaPlayer;
            if (basicMediaPlayer2 == null || basicMediaPlayer2.mediaPlayer.isPlaying() || (basicMediaPlayer = this.mediaPlayer) == null) {
                return;
            }
            basicMediaPlayer.runOnPrepared(new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer, 0));
            return;
        }
        if (state != State.READY && state != State.PAUSED) {
            this.pendingState = PendingState.PENDING_PLAY_MEDIA;
            Log.i("VideoRenderModel", "State not ready. Play pending. (state=" + state + ")");
            return;
        }
        BasicMediaPlayer basicMediaPlayer3 = this.mediaPlayer;
        if (basicMediaPlayer3 != null) {
            if (!basicMediaPlayer3.hasMedia) {
                Log.e("BasicMediaPlayer", "Cannot prepare: No media source set");
                VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda5 = basicMediaPlayer3.errorListener;
                if (videoRenderModel$$ExternalSyntheticLambda5 != null) {
                    videoRenderModel$$ExternalSyntheticLambda5.mo781invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.LOADING_INTERRUPTED, "No media source set before prepare"));
                }
            } else if (!basicMediaPlayer3.isPrepared && !basicMediaPlayer3.isPreparing) {
                basicMediaPlayer3.isPreparing = true;
                if (!basicMediaPlayer3.executeSafely("prepare", IMediaPlayer$ErrorType.LOADING_INTERRUPTED, new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer3, 6))) {
                    basicMediaPlayer3.isPreparing = false;
                }
            }
        }
        BasicMediaPlayer basicMediaPlayer4 = this.mediaPlayer;
        if (basicMediaPlayer4 != null) {
            basicMediaPlayer4.runOnPrepared(new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer4, 0));
        }
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
            if (basicMediaPlayer2.executeSafely("setSurface", IMediaPlayer$ErrorType.INVALID_STATE, new BasicMediaPlayer$$ExternalSyntheticLambda5(basicMediaPlayer2, surface))) {
                basicMediaPlayer2.hasSurface = true;
            }
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
        basicMediaPlayer2.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda14
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) {
                BasicMediaPlayer basicMediaPlayer3 = basicMediaPlayer2;
                VideoRenderModel$$ExternalSyntheticLambda1 videoRenderModel$$ExternalSyntheticLambda12 = videoRenderModel$$ExternalSyntheticLambda1;
                Log.i("BasicMediaPlayer", "onComplete() lastCallState=" + basicMediaPlayer3.lastCallState);
                videoRenderModel$$ExternalSyntheticLambda12.run();
            }
        });
        BasicMediaPlayer basicMediaPlayer3 = this.mediaPlayer;
        if (basicMediaPlayer3 != null) {
            final VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda5 = new VideoRenderModel$$ExternalSyntheticLambda5(this);
            basicMediaPlayer3.errorListener = videoRenderModel$$ExternalSyntheticLambda5;
            basicMediaPlayer3.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda8
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda52 = videoRenderModel$$ExternalSyntheticLambda5;
                    int i3 = BasicMediaPlayer.$r8$clinit;
                    videoRenderModel$$ExternalSyntheticLambda52.mo781invoke(new IMediaPlayer$MediaError(i != 1 ? i != 100 ? i != 200 ? i2 != -1010 ? i2 != -1007 ? i2 != -1004 ? i2 != -110 ? IMediaPlayer$ErrorType.UNKNOWN : IMediaPlayer$ErrorType.TIMED_OUT : IMediaPlayer$ErrorType.IO : IMediaPlayer$ErrorType.MALFORMED : IMediaPlayer$ErrorType.UNSUPPORTED : IMediaPlayer$ErrorType.NOT_VALID_FOR_PROGRESSIVE_PLAYBACK : IMediaPlayer$ErrorType.SERVER_DIED : IMediaPlayer$ErrorType.UNKNOWN, ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Error code: ", ", Extra code: ")));
                    return true;
                }
            });
        }
        return BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass3(function2, basicMediaPlayer2, function0, null), 3);
    }

    public final void stop() {
        Log.i("VideoRenderModel", "Stop requested. (state=" + this.state + ")");
        if (this.state == State.RUNNING) {
            BasicMediaPlayer basicMediaPlayer = this.mediaPlayer;
            if (basicMediaPlayer != null) {
                basicMediaPlayer.runOnPrepared(new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer, 7));
            }
            this.state = State.READY;
        }
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
                VideoRenderModel videoRenderModel = this.f$0;
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
