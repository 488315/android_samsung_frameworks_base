package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.media.mediaoutput.analytics.MediaOutputLogging;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.MediaSessionInfo;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.icons.Icons$Badge;
import com.android.systemui.media.mediaoutput.icons.Icons$Feature;
import com.android.systemui.media.mediaoutput.icons.badge.MusicShareKt;
import com.android.systemui.media.mediaoutput.icons.feature.IcAuracastKt;
import com.android.systemui.monet.ColorScheme;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

public final class MediaSessionController extends SessionController {
    public final Lazy appName$delegate;
    public final MediaSessionController$callback$1 callback;
    public final ColorSchemeLoader colorSchemeLoader;
    public final MediaController mediaController;
    public MediaSessionInfo mediaInfoCache;
    public final Lazy packageName$delegate;
    public final ProgressRunner progressRunner;
    public final Lazy resources$delegate;
    public int thumbnailNullCount;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$1$1, reason: invalid class name and collision with other inner class name */
        final class C01491 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ MediaSessionController this$0;

            public C01491(MediaSessionController mediaSessionController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaSessionController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01491(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01491) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MediaSessionController mediaSessionController = this.this$0;
                mediaSessionController.mediaController.registerCallback(mediaSessionController.callback);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaSessionController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
                C01491 c01491 = new C01491(MediaSessionController.this, null);
                this.label = 1;
                if (BuildersKt.withContext(mainCoroutineDispatcher, c01491, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class ProgressRunner {
        public final Function0 callback;
        public final ContextScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.IO);
        public Job processingJob;

        public ProgressRunner(Function0 function0) {
            this.callback = function0;
        }

        public final synchronized void state(boolean z) {
            try {
                if (!z) {
                    Job job = this.processingJob;
                    if (job != null) {
                        job.cancel(null);
                    }
                    this.processingJob = null;
                } else if (this.processingJob == null) {
                    this.processingJob = BuildersKt.launch$default(this.coroutineScope, null, null, new MediaSessionController$ProgressRunner$play$2(this, null), 3);
                }
            } finally {
            }
        }
    }

    static {
        new Companion(null);
    }

    public MediaSessionController(final Context context, MediaController mediaController) {
        super(context, mediaController.getSessionToken());
        ImageVector imageVector;
        this.mediaController = mediaController;
        ColorSchemeLoader colorSchemeLoader = new ColorSchemeLoader();
        this.colorSchemeLoader = colorSchemeLoader;
        this.progressRunner = new ProgressRunner(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$progressRunner$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PlaybackState playbackState = MediaSessionController.this.mediaController.getPlaybackState();
                if (playbackState != null) {
                    MediaSessionController.update$default(MediaSessionController.this, null, playbackState, null, null, 13);
                }
                return Unit.INSTANCE;
            }
        });
        this.packageName$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$packageName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MediaSessionController.this.mediaController.getPackageName();
            }
        });
        this.appName$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$appName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PackageManagerExtKt.getAppLabel(context.getPackageManager(), this.getPackageName());
            }
        });
        this.resources$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$resources$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return context.getPackageManager().getResourcesForApplication(this.getPackageName());
            }
        });
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$callback$1
            @Override // android.media.session.MediaController.Callback
            public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
                Log.d("MediaSessionController", "onAudioInfoChanged() - " + playbackInfo);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onExtrasChanged(Bundle bundle) {
                Log.d("MediaSessionController", "onExtrasChanged() - " + bundle);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                Log.d("MediaSessionController", "onPlaybackStateChanged() - " + mediaMetadata);
                MediaSessionController.update$default(MediaSessionController.this, mediaMetadata, null, null, null, 14);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                Log.d("MediaSessionController", "onPlaybackStateChanged() - " + playbackState);
                MediaSessionController.update$default(MediaSessionController.this, null, playbackState, null, null, 13);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onQueueChanged(List list) {
                Log.d("MediaSessionController", "onQueueChanged() - " + list);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onQueueTitleChanged(CharSequence charSequence) {
                Log.d("MediaSessionController", "onQueueTitleChanged() - " + ((Object) charSequence));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionDestroyed() {
                Log.d("MediaSessionController", "onSessionDestroyed()");
                MediaSessionController.this.close();
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionEvent(String str, Bundle bundle) {
                Log.d("MediaSessionController", "onSessionEvent() - ".concat(str));
            }
        };
        Log.d("MediaSessionController", "init()");
        MediaSessionInfo.Companion.getClass();
        MediaSessionInfo copy$default = MediaSessionInfo.copy$default(MediaSessionInfo.empty, getPackageName(), getPackageName(), null, null, 0L, 0L, null, 0, 0L, null, null, null, null, 8188);
        this.mediaInfoCache = copy$default;
        this._mediaInfo.setValue(copy$default);
        BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(null), 3);
        String packageName = getPackageName();
        SessionController.Companion.getClass();
        if (SessionController.BLUETOOTH_MEDIA_SESSION_PACKAGE.contains(packageName)) {
            ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
            Bundle extras = mediaController.getExtras();
            if (extras != null ? extras.getBoolean("auracast_assistant_notification_channel") : false) {
                Icons$Feature icons$Feature = Icons$Feature.INSTANCE;
                imageVector = (ImageVector) IcAuracastKt.IcAuracast$delegate.getValue();
            } else {
                Icons$Badge icons$Badge = Icons$Badge.INSTANCE;
                imageVector = (ImageVector) MusicShareKt.MusicShare$delegate.getValue();
            }
            companion.getClass();
            update$default(this, null, null, null, new Pair(ImageVectorConverterPainter.Companion.toConverter(imageVector), null), 7);
        } else {
            BuildersKt.launch$default(colorSchemeLoader.coroutineScope, null, null, new ColorSchemeLoader$process$3(context, colorSchemeLoader, new Function2() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    MediaSessionController.update$default(MediaSessionController.this, null, null, null, new Pair((Painter) obj, (ColorScheme) obj2), 7);
                    return Unit.INSTANCE;
                }
            }, getPackageName(), null), 3);
        }
        update$default(this, null, null, null, null, 15);
    }

    public static void sendLoggingEvent(MediaOutputLogging.Event event, String str) {
        if (str == null) {
            MediaOutputLogging.sendEventLog$default(MediaOutputLogging.INSTANCE, MediaOutputLogging.ScreenId.MEDIA_OUTPUT, event);
            return;
        }
        MediaOutputLogging mediaOutputLogging = MediaOutputLogging.INSTANCE;
        MediaOutputLogging.ScreenId screenId = MediaOutputLogging.ScreenId.MEDIA_OUTPUT;
        Map mapOf = MapsKt__MapsJVMKt.mapOf(new Pair(MediaOutputLogging.CustomKey.ACTION, str));
        mediaOutputLogging.getClass();
        MediaOutputLogging.sendEventCDLog(screenId, event, mapOf);
    }

    public static void update$default(MediaSessionController mediaSessionController, MediaMetadata mediaMetadata, PlaybackState playbackState, Pair pair, Pair pair2, int i) {
        T t = mediaMetadata;
        if ((i & 1) != 0) {
            t = 0;
        }
        T t2 = playbackState;
        if ((i & 2) != 0) {
            t2 = 0;
        }
        Pair pair3 = (i & 4) != 0 ? null : pair;
        Pair pair4 = (i & 8) != 0 ? null : pair2;
        synchronized (mediaSessionController) {
            if (mediaSessionController.isClosed) {
                mediaSessionController.progressRunner.state(false);
                return;
            }
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = t;
            Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            ref$ObjectRef2.element = t2;
            if (t == 0 && t2 == 0 && pair3 == null && pair4 == null) {
                ref$ObjectRef.element = mediaSessionController.mediaController.getMetadata();
                ref$ObjectRef2.element = mediaSessionController.mediaController.getPlaybackState();
            }
            BuildersKt.launch$default(mediaSessionController.coroutineScope, null, null, new MediaSessionController$update$8(mediaSessionController, ref$ObjectRef, ref$ObjectRef2, pair3, pair4, null), 3);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void close() {
        Log.d("MediaSessionController", "stop()");
        MediaSessionInfo.Companion.getClass();
        update(MediaSessionInfo.empty);
        this.isClosed = true;
        Job job = this.colorSchemeLoader.processingJob;
        if (job != null) {
            job.cancel(null);
        }
        this.progressRunner.state(false);
        this.mediaController.unregisterCallback(this.callback);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void execute(long j, long j2) {
        List<PlaybackState.CustomAction> customActions;
        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("execute() - ", j, " : ");
        m.append(j2);
        Log.d("MediaSessionController", m.toString());
        MediaController mediaController = this.mediaController;
        SessionController.Companion companion = SessionController.Companion;
        if (j == 8) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 89);
            return;
        }
        Object obj = null;
        if (j == 16) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 88);
            sendLoggingEvent(MediaOutputLogging.Event.MEDIA_PREVIOUS_BUTTON, null);
            return;
        }
        if (j == 512) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 85);
            return;
        }
        if (j == 4) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 126);
            sendLoggingEvent(MediaOutputLogging.Event.MEDIA_PLAY_PAUSE, MediaOutputLogging.Details.PLAY.getDetail());
            return;
        }
        if (j == 2) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 127);
            sendLoggingEvent(MediaOutputLogging.Event.MEDIA_PLAY_PAUSE, MediaOutputLogging.Details.PAUSE.getDetail());
            return;
        }
        if (j == 32) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 87);
            sendLoggingEvent(MediaOutputLogging.Event.MEDIA_NEXT_BUTTON, null);
            return;
        }
        if (j == 64) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 90);
            return;
        }
        if (j == 256) {
            if (j2 < 0) {
                this.progressRunner.state(false);
                return;
            } else {
                this._mediaInfo.setValue(MediaSessionInfo.copy$default(this.mediaInfoCache, null, null, null, null, 0L, j2, null, 0, 0L, null, null, null, null, 8159));
                mediaController.getTransportControls().seekTo(j2);
                return;
            }
        }
        PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null || (customActions = playbackState.getCustomActions()) == null) {
            return;
        }
        Iterator<T> it = customActions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((PlaybackState.CustomAction) next).getIcon() == j) {
                obj = next;
                break;
            }
        }
        PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) obj;
        if (customAction != null) {
            this.mediaController.getTransportControls().sendCustomAction(customAction, customAction.getExtras());
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getAppName() {
        return (String) this.appName$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getPackageName() {
        return (String) this.packageName$delegate.getValue();
    }
}
