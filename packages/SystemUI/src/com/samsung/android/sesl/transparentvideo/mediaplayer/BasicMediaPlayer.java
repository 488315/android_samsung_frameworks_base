package com.samsung.android.sesl.transparentvideo.mediaplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.nexus.video.BuildConfig;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import com.samsung.android.sesl.transparentvideo.renderer.VideoTextureListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class BasicMediaPlayer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Function1 errorListener;
    public boolean hasMedia;
    public boolean hasSurface;
    public boolean isPrepared;
    public boolean isPreparing;
    public boolean isReleased;
    public boolean isSeeking;
    public MediaCallState lastCallState;
    public final MediaPlayer mediaPlayer;
    public Long pendingSeekPosition;
    public final List taskQueue;

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
    public final class MediaCallState {
        public static final /* synthetic */ MediaCallState[] $VALUES;
        public static final MediaCallState NONE;
        public static final MediaCallState PAUSE;
        public static final MediaCallState PLAY;

        static {
            MediaCallState mediaCallState = new MediaCallState(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = mediaCallState;
            MediaCallState mediaCallState2 = new MediaCallState("PLAY", 1);
            PLAY = mediaCallState2;
            MediaCallState mediaCallState3 = new MediaCallState("PAUSE", 2);
            PAUSE = mediaCallState3;
            MediaCallState[] mediaCallStateArr = {mediaCallState, mediaCallState2, mediaCallState3};
            $VALUES = mediaCallStateArr;
            EnumEntriesKt.enumEntries(mediaCallStateArr);
        }

        private MediaCallState(String str, int i) {
        }

        public static MediaCallState valueOf(String str) {
            return (MediaCallState) Enum.valueOf(MediaCallState.class, str);
        }

        public static MediaCallState[] values() {
            return (MediaCallState[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
    }

    public BasicMediaPlayer(final VideoTextureListener videoTextureListener, final Consumer<Size> consumer, final Runnable runnable) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda1
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public final void onVideoSizeChanged(MediaPlayer mediaPlayer2, int i, int i2) {
                VideoTextureListener videoTextureListener2 = VideoTextureListener.this;
                Consumer consumer2 = consumer;
                int i3 = BasicMediaPlayer.$r8$clinit;
                Size size = new Size(i, i2);
                videoTextureListener2.onVideoSizeChanged(size);
                if (consumer2 != null) {
                    consumer2.accept(size);
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda2
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer2) {
                BasicMediaPlayer basicMediaPlayer = BasicMediaPlayer.this;
                Runnable runnable2 = runnable;
                if (basicMediaPlayer.isReleased) {
                    Log.d("BasicMediaPlayer", "media player is released on preparing. skipped all queued media operations.");
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m(basicMediaPlayer.taskQueue.size(), "prepare done. running queued items ", "BasicMediaPlayer");
                Iterator it = basicMediaPlayer.taskQueue.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                basicMediaPlayer.taskQueue.clear();
                basicMediaPlayer.isPrepared = true;
                basicMediaPlayer.isPreparing = false;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        this.mediaPlayer = mediaPlayer;
        this.taskQueue = Collections.synchronizedList(new LinkedList());
        this.lastCallState = MediaCallState.NONE;
    }

    public static final void access$seekInternal(long j, BasicMediaPlayer basicMediaPlayer) {
        MediaCallState mediaCallState = basicMediaPlayer.lastCallState;
        if (mediaCallState == MediaCallState.PAUSE || mediaCallState == MediaCallState.NONE) {
            basicMediaPlayer.mediaPlayer.start();
            basicMediaPlayer.mediaPlayer.seekTo(j, 3);
            basicMediaPlayer.mediaPlayer.pause();
        } else if (mediaCallState == MediaCallState.PLAY) {
            basicMediaPlayer.mediaPlayer.seekTo(j, 3);
        }
        basicMediaPlayer.isSeeking = true;
    }

    public final void pause() {
        runOnPrepared(new Function0() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$pause$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.i("BasicMediaPlayer", "pause() lastCallState=" + BasicMediaPlayer.this.lastCallState);
                try {
                    BasicMediaPlayer basicMediaPlayer = BasicMediaPlayer.this;
                    basicMediaPlayer.lastCallState = BasicMediaPlayer.MediaCallState.PAUSE;
                    if (basicMediaPlayer.mediaPlayer.isPlaying()) {
                        BasicMediaPlayer.this.mediaPlayer.pause();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("BasicMediaPlayer", "Exception: " + Unit.INSTANCE);
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void play() {
        runOnPrepared(new Function0() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$play$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.i("BasicMediaPlayer", "play() lastCallState=" + BasicMediaPlayer.this.lastCallState);
                if (!BasicMediaPlayer.this.mediaPlayer.isPlaying()) {
                    BasicMediaPlayer.this.mediaPlayer.start();
                }
                BasicMediaPlayer.this.lastCallState = BasicMediaPlayer.MediaCallState.PLAY;
                return Unit.INSTANCE;
            }
        });
    }

    public final void release() {
        Log.d("BasicMediaPlayer", BuildConfig.BUILD_TYPE);
        this.mediaPlayer.release();
        this.hasMedia = false;
        this.hasSurface = false;
        this.isPrepared = false;
        this.isReleased = true;
        this.taskQueue.clear();
    }

    public final void runOnPrepared(final Function0 function0) {
        if (this.isReleased) {
            Log.w("BasicMediaPlayer", "media player has been released. skipped media operation.");
            return;
        }
        if (!this.hasMedia) {
            Log.d("BasicMediaPlayer", "no media input. ignored the request.");
            return;
        }
        if (!this.hasSurface) {
            Log.d("BasicMediaPlayer", "no surface.");
        }
        if (this.isPrepared) {
            function0.invoke();
            return;
        }
        this.taskQueue.add(new Runnable(function0) { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda0
            public final /* synthetic */ Lambda f$0;

            /* JADX WARN: Multi-variable type inference failed */
            {
                this.f$0 = (Lambda) function0;
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
            @Override // java.lang.Runnable
            public final void run() {
                ?? r1 = this.f$0;
                int i = BasicMediaPlayer.$r8$clinit;
                r1.invoke();
            }
        });
        if (!this.isPreparing) {
            this.mediaPlayer.prepareAsync();
        }
        this.isPreparing = true;
    }

    public final void seekTo() {
        final long j = 0;
        runOnPrepared(new Function0() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$seekTo$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.i("BasicMediaPlayer", "seekTo(" + j + ") lastCallState=" + this.lastCallState);
                final BasicMediaPlayer basicMediaPlayer = this;
                basicMediaPlayer.mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$seekTo$1$$ExternalSyntheticLambda0
                    @Override // android.media.MediaPlayer.OnSeekCompleteListener
                    public final void onSeekComplete(MediaPlayer mediaPlayer) {
                        BasicMediaPlayer basicMediaPlayer2 = BasicMediaPlayer.this;
                        basicMediaPlayer2.isSeeking = false;
                        Long l = basicMediaPlayer2.pendingSeekPosition;
                        if (l != null) {
                            BasicMediaPlayer.access$seekInternal(l.longValue(), basicMediaPlayer2);
                        }
                        basicMediaPlayer2.pendingSeekPosition = null;
                    }
                });
                BasicMediaPlayer basicMediaPlayer2 = this;
                if (basicMediaPlayer2.isSeeking) {
                    basicMediaPlayer2.pendingSeekPosition = Long.valueOf(j);
                    Log.w("BasicMediaPlayer", "Already seeking! just updated pending seek position to " + this.pendingSeekPosition + ".");
                } else {
                    BasicMediaPlayer.access$seekInternal(j, basicMediaPlayer2);
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final Object setMedia(AssetFileDescriptor assetFileDescriptor, Continuation continuation) {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        Object withContext = BuildersKt.withContext(DefaultIoScheduler.INSTANCE, new BasicMediaPlayer$setMedia$2(this, assetFileDescriptor, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object setMedia(Context context, Uri uri, Continuation continuation) {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        Object withContext = BuildersKt.withContext(DefaultIoScheduler.INSTANCE, new BasicMediaPlayer$setMedia$4(this, context, uri, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public /* synthetic */ BasicMediaPlayer(VideoTextureListener videoTextureListener, Consumer consumer, Runnable runnable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(videoTextureListener, consumer, (i & 4) != 0 ? null : runnable);
    }
}
