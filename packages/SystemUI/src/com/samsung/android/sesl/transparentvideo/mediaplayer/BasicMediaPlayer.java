package com.samsung.android.sesl.transparentvideo.mediaplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.nexus.video.BuildConfig;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel$$ExternalSyntheticLambda5;
import com.samsung.android.sesl.transparentvideo.renderer.VideoTextureListener;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes4.dex */
public final class BasicMediaPlayer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public VideoRenderModel$$ExternalSyntheticLambda5 errorListener;
    public boolean hasMedia;
    public boolean hasSurface;
    public boolean isPrepared;
    public boolean isPreparing;
    public boolean isReleased;
    public boolean isSeeking;
    public boolean isSetMediaInProgress;
    public MediaCallState lastCallState;
    public final MediaPlayer mediaPlayer;
    public boolean pendingRelease;
    public Long pendingSeekPosition;
    public final List taskQueue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    /* renamed from: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$setMedia$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ AssetFileDescriptor $assetFd;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AssetFileDescriptor assetFileDescriptor, Continuation continuation) {
            super(2, continuation);
            this.$assetFd = assetFileDescriptor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BasicMediaPlayer.this.new AnonymousClass2(this.$assetFd, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BasicMediaPlayer basicMediaPlayer = BasicMediaPlayer.this;
            basicMediaPlayer.isSetMediaInProgress = true;
            try {
                if (basicMediaPlayer.isReleased) {
                    Log.w("BasicMediaPlayer", "Already released, aborting setMedia");
                    return Unit.INSTANCE;
                }
                if (basicMediaPlayer.hasMedia && basicMediaPlayer.executeSafely(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, IMediaPlayer$ErrorType.LOADING_INTERRUPTED, new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer, 8))) {
                    BasicMediaPlayer basicMediaPlayer2 = BasicMediaPlayer.this;
                    basicMediaPlayer2.hasMedia = false;
                    basicMediaPlayer2.isPrepared = false;
                    basicMediaPlayer2.isPreparing = false;
                }
                BasicMediaPlayer basicMediaPlayer3 = BasicMediaPlayer.this;
                if (basicMediaPlayer3.executeSafely("setDataSource", IMediaPlayer$ErrorType.LOADING_INTERRUPTED, new BasicMediaPlayer$$ExternalSyntheticLambda5(basicMediaPlayer3, this.$assetFd))) {
                    BasicMediaPlayer.this.hasMedia = true;
                }
                BasicMediaPlayer basicMediaPlayer4 = BasicMediaPlayer.this;
                basicMediaPlayer4.isSetMediaInProgress = false;
                if (basicMediaPlayer4.pendingRelease) {
                    Log.w("BasicMediaPlayer", "Executing pending release");
                    BasicMediaPlayer basicMediaPlayer5 = BasicMediaPlayer.this;
                    basicMediaPlayer5.pendingRelease = false;
                    basicMediaPlayer5.release();
                }
                return Unit.INSTANCE;
            } finally {
                BasicMediaPlayer basicMediaPlayer6 = BasicMediaPlayer.this;
                basicMediaPlayer6.isSetMediaInProgress = false;
                if (basicMediaPlayer6.pendingRelease) {
                    Log.w("BasicMediaPlayer", "Executing pending release");
                    BasicMediaPlayer basicMediaPlayer7 = BasicMediaPlayer.this;
                    basicMediaPlayer7.pendingRelease = false;
                    basicMediaPlayer7.release();
                }
            }
        }
    }

    /* renamed from: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$setMedia$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $context;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Context context, Uri uri, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$uri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BasicMediaPlayer.this.new AnonymousClass4(this.$context, this.$uri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final BasicMediaPlayer basicMediaPlayer = BasicMediaPlayer.this;
            basicMediaPlayer.isSetMediaInProgress = true;
            try {
                if (basicMediaPlayer.isReleased) {
                    Log.w("BasicMediaPlayer", "Already released, aborting setMedia");
                    return Unit.INSTANCE;
                }
                IMediaPlayer$ErrorType iMediaPlayer$ErrorType = IMediaPlayer$ErrorType.LOADING_INTERRUPTED;
                final Context context = this.$context;
                final Uri uri = this.$uri;
                if (basicMediaPlayer.executeSafely("setDataSource", iMediaPlayer$ErrorType, new Function0() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$setMedia$4$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                        basicMediaPlayer.mediaPlayer.setDataSource(context, uri);
                        return Unit.INSTANCE;
                    }
                })) {
                    BasicMediaPlayer.this.hasMedia = true;
                }
                BasicMediaPlayer basicMediaPlayer2 = BasicMediaPlayer.this;
                basicMediaPlayer2.isSetMediaInProgress = false;
                if (basicMediaPlayer2.pendingRelease) {
                    Log.w("BasicMediaPlayer", "Executing pending release");
                    BasicMediaPlayer basicMediaPlayer3 = BasicMediaPlayer.this;
                    basicMediaPlayer3.pendingRelease = false;
                    basicMediaPlayer3.release();
                }
                return Unit.INSTANCE;
            } finally {
                BasicMediaPlayer basicMediaPlayer4 = BasicMediaPlayer.this;
                basicMediaPlayer4.isSetMediaInProgress = false;
                if (basicMediaPlayer4.pendingRelease) {
                    Log.w("BasicMediaPlayer", "Executing pending release");
                    BasicMediaPlayer basicMediaPlayer5 = BasicMediaPlayer.this;
                    basicMediaPlayer5.pendingRelease = false;
                    basicMediaPlayer5.release();
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    public BasicMediaPlayer(final VideoTextureListener videoTextureListener, final Consumer<Size> consumer, final Runnable runnable) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda3
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public final void onVideoSizeChanged(MediaPlayer mediaPlayer2, int i, int i2) {
                VideoTextureListener videoTextureListener2 = videoTextureListener;
                Consumer consumer2 = consumer;
                int i3 = BasicMediaPlayer.$r8$clinit;
                Size size = new Size(i, i2);
                videoTextureListener2.onVideoSizeChanged(size);
                if (consumer2 != null) {
                    consumer2.accept(size);
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda4
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer2) {
                BasicMediaPlayer basicMediaPlayer = this.f$0;
                Runnable runnable2 = runnable;
                if (basicMediaPlayer.isReleased) {
                    Log.w("BasicMediaPlayer", "media player is released on preparing. skipped all queued media operations.");
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

    public final boolean executeSafely(String str, IMediaPlayer$ErrorType iMediaPlayer$ErrorType, Function0 function0) {
        if (this.isReleased) {
            Log.w("BasicMediaPlayer", "MediaPlayer has been released. Skipping API call: ".concat(str));
            return false;
        }
        try {
            function0.invoke();
            return true;
        } catch (IOException e) {
            String strM = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("IOException in ", str, ": ", e.getMessage());
            Log.e("BasicMediaPlayer", strM, e);
            VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda5 = this.errorListener;
            if (videoRenderModel$$ExternalSyntheticLambda5 != null) {
                videoRenderModel$$ExternalSyntheticLambda5.mo781invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.IO, strM));
            }
            return false;
        } catch (IllegalStateException e2) {
            String strM2 = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("IllegalStateException in ", str, ": ", e2.getMessage());
            Log.e("BasicMediaPlayer", strM2, e2);
            VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda52 = this.errorListener;
            if (videoRenderModel$$ExternalSyntheticLambda52 != null) {
                videoRenderModel$$ExternalSyntheticLambda52.mo781invoke(new IMediaPlayer$MediaError(iMediaPlayer$ErrorType, strM2));
            }
            return false;
        } catch (Exception e3) {
            String strM3 = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Unexpected error in ", str, ": ", e3.getMessage());
            Log.e("BasicMediaPlayer", strM3, e3);
            VideoRenderModel$$ExternalSyntheticLambda5 videoRenderModel$$ExternalSyntheticLambda53 = this.errorListener;
            if (videoRenderModel$$ExternalSyntheticLambda53 != null) {
                videoRenderModel$$ExternalSyntheticLambda53.mo781invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.UNKNOWN, strM3));
            }
            return false;
        }
    }

    public final void release() {
        if (this.isSetMediaInProgress) {
            Log.w("BasicMediaPlayer", "setMedia is in progress, deferring release");
            this.pendingRelease = true;
            return;
        }
        if (this.isReleased) {
            Log.w("BasicMediaPlayer", "Already released, skipping");
            return;
        }
        Log.d("BasicMediaPlayer", BuildConfig.BUILD_TYPE);
        try {
            this.mediaPlayer.release();
        } catch (Exception e) {
            Log.e("BasicMediaPlayer", "Exception during release: " + e.getMessage(), e);
        }
        this.hasMedia = false;
        this.hasSurface = false;
        this.isPrepared = false;
        this.isReleased = true;
        this.pendingRelease = false;
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
        this.taskQueue.add(new Runnable() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                Function0 function02 = function0;
                int i = BasicMediaPlayer.$r8$clinit;
                function02.invoke();
            }
        });
        if (!this.isPreparing) {
            executeSafely("prepareAsync (implicit)", IMediaPlayer$ErrorType.LOADING_INTERRUPTED, new BasicMediaPlayer$$ExternalSyntheticLambda2(this, 1));
        }
        this.isPreparing = true;
    }

    public final void seekInternal(final long j) {
        boolean zExecuteSafely;
        MediaCallState mediaCallState = this.lastCallState;
        if (mediaCallState == MediaCallState.PAUSE || mediaCallState == MediaCallState.NONE) {
            final int i = 0;
            zExecuteSafely = executeSafely("seekInternal(paused)", IMediaPlayer$ErrorType.INVALID_STATE, new Function0(this) { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda0
                public final /* synthetic */ BasicMediaPlayer f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws IllegalStateException {
                    switch (i) {
                        case 0:
                            BasicMediaPlayer basicMediaPlayer = this.f$0;
                            basicMediaPlayer.mediaPlayer.start();
                            basicMediaPlayer.mediaPlayer.seekTo(j, 3);
                            basicMediaPlayer.mediaPlayer.pause();
                            break;
                        default:
                            this.f$0.mediaPlayer.seekTo(j, 3);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
        } else {
            final int i2 = 1;
            zExecuteSafely = executeSafely("seekInternal(playing)", IMediaPlayer$ErrorType.INVALID_STATE, new Function0(this) { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda0
                public final /* synthetic */ BasicMediaPlayer f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() throws IllegalStateException {
                    switch (i2) {
                        case 0:
                            BasicMediaPlayer basicMediaPlayer = this.f$0;
                            basicMediaPlayer.mediaPlayer.start();
                            basicMediaPlayer.mediaPlayer.seekTo(j, 3);
                            basicMediaPlayer.mediaPlayer.pause();
                            break;
                        default:
                            this.f$0.mediaPlayer.seekTo(j, 3);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        if (zExecuteSafely) {
            this.isSeeking = true;
        }
    }

    public final Object setMedia(AssetFileDescriptor assetFileDescriptor, Continuation continuation) throws Throwable {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        Object objWithContext = BuildersKt.withContext(DefaultIoScheduler.INSTANCE, new AnonymousClass2(assetFileDescriptor, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object setMedia(Context context, Uri uri, Continuation continuation) throws Throwable {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        Object objWithContext = BuildersKt.withContext(DefaultIoScheduler.INSTANCE, new AnonymousClass4(context, uri, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public /* synthetic */ BasicMediaPlayer(VideoTextureListener videoTextureListener, Consumer consumer, Runnable runnable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(videoTextureListener, consumer, (i & 4) != 0 ? null : runnable);
    }
}
