package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.AndroidImageBitmap_androidKt;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.core.content.res.ResourcesCompat;
import androidx.datastore.core.DataStore;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.entity.MediaInfoExt;
import com.android.systemui.media.mediaoutput.ext.BitmapExtKt;
import com.android.systemui.media.mediaoutput.ext.MediaControllerExtKt;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.badge.MusicShareKt;
import com.android.systemui.media.mediaoutput.icons.feature.IcAuracastKt;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class MediaSessionController implements MediaSession {
    public final StateFlowImpl _actionsFlow;
    public final StateFlowImpl _appColorSchemeFlow;
    public final StateFlowImpl _appIconFlow;
    public final StateFlowImpl _artistFlow;
    public final StateFlowImpl _durationFlow;
    public final StateFlowImpl _mediaActionsFlow;
    public final StateFlowImpl _playbackStateFlow;
    public final StateFlowImpl _positionFlow;
    public final StateFlowImpl _thumbColorSchemeFlow;
    public final StateFlowImpl _thumbnailFlow;
    public final StateFlowImpl _titleFlow;
    public final ReadonlyStateFlow actionsFlow;
    public final ReadonlyStateFlow appColorSchemeFlow;
    public final ReadonlyStateFlow appIconFlow;
    public final Lazy appName$delegate;
    public final ReadonlyStateFlow artistFlow;
    public final MediaSessionController$callback$1 callback;
    public final ColorSchemeLoader colorSchemeLoader;
    public final Context context;
    public final ContextScope coroutineScope;
    public final DataStore dataStore;
    public final ReadonlyStateFlow durationFlow;
    public final Lazy id$delegate;
    public boolean isClosed;
    public boolean isGrayscaleThumbnail;
    public final ReadonlyStateFlow mediaActionsFlow;
    public final MediaController mediaController;
    public final String packageName;
    public final ReadonlyStateFlow playbackStateFlow;
    public final ReadonlyStateFlow positionFlow;
    public final ProgressRunner progressRunner;
    public final Lazy resources$delegate;
    public final ReadonlyStateFlow thumbColorSchemeFlow;
    public final ReadonlyStateFlow thumbnailFlow;
    public int thumbnailNullCount;
    public final ReadonlyStateFlow titleFlow;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

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

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0059, code lost:
        
            if (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r3, r6, r5) == r0) goto L16;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            MediaController mediaController;
            MediaSessionController mediaSessionController;
            MediaController mediaController2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MediaSessionController mediaSessionController2 = MediaSessionController.this;
                mediaController = mediaSessionController2.mediaController;
                MediaMetadata metadata = mediaController.getMetadata();
                this.L$0 = mediaController;
                this.L$1 = mediaSessionController2;
                this.L$2 = mediaController;
                this.label = 1;
                if (MediaSessionController.access$update(mediaSessionController2, metadata, this) != coroutineSingletons) {
                    mediaSessionController = mediaSessionController2;
                    mediaController2 = mediaController;
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            mediaController = (MediaController) this.L$2;
            mediaSessionController = (MediaSessionController) this.L$1;
            mediaController2 = (MediaController) this.L$0;
            ResultKt.throwOnFailure(obj);
            PlaybackState playbackState = mediaController.getPlaybackState();
            this.L$0 = mediaController2;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaSessionController.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x006e, code lost:
        
            if (r1.collect(r6, r5) == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            MediaSessionController mediaSessionController;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                mediaSessionController = MediaSessionController.this;
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = mediaSessionController.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$2 dataStoreDebugLabsExt$special$$inlined$map$2 = new DataStoreDebugLabsExt$special$$inlined$map$2(dataStore.getData());
                this.L$0 = mediaSessionController;
                this.label = 1;
                obj = FlowKt.firstOrNull(dataStoreDebugLabsExt$special$$inlined$map$2, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            mediaSessionController = (MediaSessionController) this.L$0;
            ResultKt.throwOnFailure(obj);
            Boolean bool = (Boolean) obj;
            mediaSessionController.isGrayscaleThumbnail = bool != null ? bool.booleanValue() : false;
            DataStoreDebugLabsExt dataStoreDebugLabsExt2 = DataStoreDebugLabsExt.INSTANCE;
            DataStore dataStore2 = MediaSessionController.this.dataStore;
            dataStoreDebugLabsExt2.getClass();
            DataStoreDebugLabsExt$special$$inlined$map$2 dataStoreDebugLabsExt$special$$inlined$map$22 = new DataStoreDebugLabsExt$special$$inlined$map$2(dataStore2.getData());
            final MediaSessionController mediaSessionController2 = MediaSessionController.this;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    mediaSessionController2.isGrayscaleThumbnail = ((Boolean) obj2).booleanValue();
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = null;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ MediaSessionController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(MediaSessionController mediaSessionController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaSessionController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaSessionController.this.new AnonymousClass3(continuation);
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
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(MediaSessionController.this, null);
                this.label = 1;
                if (BuildersKt.withContext(handlerContext, anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaSessionController.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ImageVector imageVector;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MediaSessionController mediaSessionController = MediaSessionController.this;
                StateFlowImpl stateFlowImpl = mediaSessionController._appIconFlow;
                ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
                Bundle extras = mediaSessionController.mediaController.getExtras();
                if (extras != null ? extras.getBoolean("auracast_assistant_notification_channel") : false) {
                    Icons.Feature feature = Icons.Feature.INSTANCE;
                    imageVector = (ImageVector) IcAuracastKt.IcAuracast$delegate.getValue();
                } else {
                    Icons.Badge badge = Icons.Badge.INSTANCE;
                    imageVector = (ImageVector) MusicShareKt.MusicShare$delegate.getValue();
                }
                companion.getClass();
                ImageVectorConverterPainter converter = ImageVectorConverterPainter.Companion.toConverter(imageVector);
                this.label = 1;
                stateFlowImpl.updateState(null, converter);
                if (Unit.INSTANCE == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass5(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass5 anonymousClass5 = MediaSessionController.this.new AnonymousClass5((Continuation) obj3);
            anonymousClass5.L$0 = (Painter) obj;
            anonymousClass5.L$1 = (ColorScheme) obj2;
            return anonymousClass5.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            ColorScheme colorScheme;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Painter painter = (Painter) this.L$0;
                colorScheme = (ColorScheme) this.L$1;
                StateFlowImpl stateFlowImpl = MediaSessionController.this._appIconFlow;
                this.L$0 = colorScheme;
                this.label = 1;
                stateFlowImpl.setValue(painter);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            colorScheme = (ColorScheme) this.L$0;
            ResultKt.throwOnFailure(obj);
            StateFlowImpl stateFlowImpl2 = MediaSessionController.this._appColorSchemeFlow;
            this.L$0 = null;
            this.label = 2;
            stateFlowImpl2.setValue(colorScheme);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ProgressRunner {
        public final Function1 callback;
        public final ContextScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.Default);
        public StandaloneCoroutine processingJob;

        public ProgressRunner(Function1 function1) {
            this.callback = function1;
        }

        public final synchronized void state(boolean z) {
            Log.d("MediaSessionController", "ProgressRunner state = " + z);
            if (!z) {
                StandaloneCoroutine standaloneCoroutine = this.processingJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                this.processingJob = null;
            } else if (this.processingJob == null) {
                this.processingJob = BuildersKt.launch$default(this.coroutineScope, null, null, new MediaSessionController$ProgressRunner$play$2(this, null), 3);
            }
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$close$1, reason: invalid class name and case insensitive filesystem */
    final class C09411 extends SuspendLambda implements Function2 {
        int label;

        public C09411(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaSessionController.this.new C09411(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09411) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0033, code lost:
        
            if (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r6, (android.media.session.PlaybackState) null, r5) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MediaSessionController mediaSessionController = MediaSessionController.this;
                this.label = 1;
                if (MediaSessionController.access$update(mediaSessionController, (MediaMetadata) null, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            MediaSessionController mediaSessionController2 = MediaSessionController.this;
            this.label = 2;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$callback$1] */
    public MediaSessionController(Context context, DataStore dataStore, MediaController mediaController) {
        final int i = 0;
        this.context = context;
        this.dataStore = dataStore;
        this.mediaController = mediaController;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        ContextScope contextScopeCoroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
        this.coroutineScope = contextScopeCoroutineScope;
        this.id$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaSessionController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return this.f$0.packageName;
                    case 1:
                        MediaSessionController mediaSessionController = this.f$0;
                        return PackageManagerExtKt.getAppLabel(mediaSessionController.context.getPackageManager(), mediaSessionController.packageName);
                    default:
                        MediaSessionController mediaSessionController2 = this.f$0;
                        return mediaSessionController2.context.getPackageManager().getResourcesForApplication(mediaSessionController2.packageName);
                }
            }
        });
        String packageName = mediaController.getPackageName();
        this.packageName = packageName;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._titleFlow = stateFlowImplMutableStateFlow;
        this.titleFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._artistFlow = stateFlowImplMutableStateFlow2;
        this.artistFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(0L);
        this._durationFlow = stateFlowImplMutableStateFlow3;
        this.durationFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(0L);
        this._positionFlow = stateFlowImplMutableStateFlow4;
        this.positionFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(null);
        this._thumbnailFlow = stateFlowImplMutableStateFlow5;
        this.thumbnailFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(0);
        this._playbackStateFlow = stateFlowImplMutableStateFlow6;
        this.playbackStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(0L);
        this._actionsFlow = stateFlowImplMutableStateFlow7;
        this.actionsFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._mediaActionsFlow = stateFlowImplMutableStateFlow8;
        this.mediaActionsFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._appIconFlow = stateFlowImplMutableStateFlow9;
        this.appIconFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(null);
        this._appColorSchemeFlow = stateFlowImplMutableStateFlow10;
        this.appColorSchemeFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
        StateFlowImpl stateFlowImplMutableStateFlow11 = StateFlowKt.MutableStateFlow(null);
        this._thumbColorSchemeFlow = stateFlowImplMutableStateFlow11;
        this.thumbColorSchemeFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow11);
        ColorSchemeLoader colorSchemeLoader = new ColorSchemeLoader();
        this.colorSchemeLoader = colorSchemeLoader;
        this.progressRunner = new ProgressRunner(new MediaSessionController$progressRunner$1(this, null));
        final int i2 = 1;
        this.appName$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaSessionController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return this.f$0.packageName;
                    case 1:
                        MediaSessionController mediaSessionController = this.f$0;
                        return PackageManagerExtKt.getAppLabel(mediaSessionController.context.getPackageManager(), mediaSessionController.packageName);
                    default:
                        MediaSessionController mediaSessionController2 = this.f$0;
                        return mediaSessionController2.context.getPackageManager().getResourcesForApplication(mediaSessionController2.packageName);
                }
            }
        });
        final int i3 = 2;
        this.resources$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaSessionController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return this.f$0.packageName;
                    case 1:
                        MediaSessionController mediaSessionController = this.f$0;
                        return PackageManagerExtKt.getAppLabel(mediaSessionController.context.getPackageManager(), mediaSessionController.packageName);
                    default:
                        MediaSessionController mediaSessionController2 = this.f$0;
                        return mediaSessionController2.context.getPackageManager().getResourcesForApplication(mediaSessionController2.packageName);
                }
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
                Log.d("MediaSessionController", "onPlaybackStateChanged() - ".concat(MediaControllerExtKt.toLogText(mediaMetadata)));
                MediaSessionController mediaSessionController = this.this$0;
                BuildersKt.launch$default(mediaSessionController.coroutineScope, null, null, new MediaSessionController$callback$1$onMetadataChanged$1(mediaSessionController, mediaMetadata, null), 3);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                Log.d("MediaSessionController", "onPlaybackStateChanged() - " + playbackState);
                MediaSessionController mediaSessionController = this.this$0;
                BuildersKt.launch$default(mediaSessionController.coroutineScope, null, null, new MediaSessionController$callback$1$onPlaybackStateChanged$1(mediaSessionController, playbackState, null), 3);
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
                this.this$0.close();
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionEvent(String str, Bundle bundle) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onSessionEvent() - ", str, "MediaSessionController");
            }
        };
        Log.d("MediaSessionController", "init()");
        BuildersKt.launch$default(contextScopeCoroutineScope, null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(contextScopeCoroutineScope, null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(contextScopeCoroutineScope, null, null, new AnonymousClass3(null), 3);
        SessionController.Companion.getClass();
        if (SessionController.Companion.BLUETOOTH_MEDIA_SESSION_PACKAGE.contains(packageName)) {
            BuildersKt.launch$default(contextScopeCoroutineScope, null, null, new AnonymousClass4(null), 3);
        } else {
            BuildersKt.launch$default(colorSchemeLoader.coroutineScope, null, null, new ColorSchemeLoader$process$2(context, new AnonymousClass5(null), packageName, null), 3);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0254, code lost:
    
        if (kotlin.Unit.INSTANCE != r4) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x010c, code lost:
    
        if (kotlin.Unit.INSTANCE != r4) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x023a A[PHI: r0 r1
      0x023a: PHI (r0v28 android.media.MediaMetadata) = (r0v25 android.media.MediaMetadata), (r0v32 android.media.MediaMetadata) binds: [B:100:0x0237, B:13:0x003d] A[DONT_GENERATE, DONT_INLINE]
      0x023a: PHI (r1v22 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController) = 
      (r1v19 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
      (r1v25 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
     binds: [B:100:0x0237, B:13:0x003d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00da A[PHI: r0
      0x00da: PHI (r0v4 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController) = 
      (r0v1 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
      (r0v6 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
     binds: [B:28:0x00d6, B:20:0x0091] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f0 A[PHI: r0
      0x00f0: PHI (r0v7 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController) = 
      (r0v4 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
      (r0v9 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
     binds: [B:31:0x00ec, B:19:0x0089] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0100 A[PHI: r0
      0x0100: PHI (r0v10 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController) = 
      (r0v7 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
      (r0v14 com.android.systemui.media.mediaoutput.controller.media.MediaSessionController)
     binds: [B:34:0x00fc, B:18:0x0080] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x021d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$update(MediaSessionController mediaSessionController, MediaMetadata mediaMetadata, ContinuationImpl continuationImpl) {
        MediaSessionController$update$1 mediaSessionController$update$1;
        Bitmap bitmapAsAndroidBitmap;
        String string;
        Object resourceString;
        MediaSessionController mediaSessionController2;
        MediaMetadata mediaMetadata2;
        String string2;
        MediaSessionController mediaSessionController3 = mediaSessionController;
        MediaMetadata mediaMetadata3 = mediaMetadata;
        mediaSessionController3.getClass();
        if (continuationImpl instanceof MediaSessionController$update$1) {
            mediaSessionController$update$1 = (MediaSessionController$update$1) continuationImpl;
            int i = mediaSessionController$update$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                mediaSessionController$update$1.label = i - Integer.MIN_VALUE;
            } else {
                mediaSessionController$update$1 = new MediaSessionController$update$1(mediaSessionController3, continuationImpl);
            }
        }
        Object obj = mediaSessionController$update$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (mediaSessionController$update$1.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                Log.d("MediaSessionController", "update() - MediaMetadata = ".concat(MediaControllerExtKt.toLogText(mediaMetadata3)));
                if (mediaMetadata3 != null) {
                    Bitmap bitmap = mediaMetadata3.getBitmap("android.media.metadata.ALBUM_ART");
                    if (bitmap == null && (bitmap = mediaMetadata3.getBitmap("android.media.metadata.ART")) == null) {
                        bitmap = mediaMetadata3.getBitmap("android.media.metadata.DISPLAY_ICON");
                    }
                    StateFlowImpl stateFlowImpl = mediaSessionController3._thumbnailFlow;
                    if (bitmap != null) {
                        Bitmap bitmap2 = mediaSessionController3.isGrayscaleThumbnail ? bitmap : null;
                        if (bitmap2 != null) {
                            int width = bitmap2.getWidth();
                            int height = bitmap2.getHeight();
                            Bitmap.Config config = bitmap2.getConfig();
                            if (config == null) {
                                config = Bitmap.Config.ARGB_8888;
                            }
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                            Paint paint = new Paint();
                            ColorMatrix colorMatrix = new ColorMatrix();
                            colorMatrix.setSaturation(0.0f);
                            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                            Unit unit = Unit.INSTANCE;
                            canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
                            if (bitmapCreateBitmap != null) {
                                bitmap = bitmapCreateBitmap;
                            }
                        }
                        ImageBitmap imageBitmap = (ImageBitmap) stateFlowImpl.getValue();
                        if (((imageBitmap == null || (bitmapAsAndroidBitmap = AndroidImageBitmap_androidKt.asAndroidBitmap(imageBitmap)) == null || !Intrinsics.areEqual(BitmapExtKt.getHash(bitmapAsAndroidBitmap), BitmapExtKt.getHash(bitmap))) ? mediaMetadata3 : null) != null) {
                            MediaSessionController$update$3$2 mediaSessionController$update$3$2 = new MediaSessionController$update$3$2(mediaSessionController3, null);
                            ColorSchemeLoader colorSchemeLoader = mediaSessionController3.colorSchemeLoader;
                            StandaloneCoroutine standaloneCoroutine = colorSchemeLoader.processingJob;
                            if (standaloneCoroutine != null) {
                                standaloneCoroutine.cancel(null);
                            }
                            colorSchemeLoader.processingJob = BuildersKt.launch$default(colorSchemeLoader.coroutineScope, null, null, new ColorSchemeLoader$process$1(bitmap, mediaSessionController$update$3$2, null), 3);
                            mediaSessionController3.thumbnailNullCount = 0;
                        }
                    } else {
                        int i2 = mediaSessionController3.thumbnailNullCount + 1;
                        mediaSessionController3.thumbnailNullCount = i2;
                        if (i2 > 2) {
                            Log.d("MediaSessionController", "update() - thumbnail is null");
                            mediaSessionController$update$1.L$0 = mediaSessionController3;
                            mediaSessionController$update$1.L$1 = mediaMetadata3;
                            mediaSessionController$update$1.label = 6;
                            stateFlowImpl.setValue(null);
                            if (Unit.INSTANCE != coroutineSingletons) {
                                StateFlowImpl stateFlowImpl2 = mediaSessionController3._thumbColorSchemeFlow;
                                mediaSessionController$update$1.L$0 = mediaSessionController3;
                                mediaSessionController$update$1.L$1 = mediaMetadata3;
                                mediaSessionController$update$1.label = 7;
                                stateFlowImpl2.setValue(null);
                                if (Unit.INSTANCE != coroutineSingletons) {
                                }
                            }
                        }
                    }
                    StateFlowImpl stateFlowImpl3 = mediaSessionController3._titleFlow;
                    string = mediaMetadata3.getString("android.media.metadata.TITLE");
                    resourceString = string;
                    if (string == null) {
                        CharSequence title = mediaMetadata3.getDescription().getTitle();
                        if (title != null && (string2 = title.toString()) != null) {
                            int length = string2.length();
                            resourceString = string2;
                            if (length <= 0) {
                            }
                        }
                        resourceString = null;
                    }
                    if (resourceString == null) {
                        resourceString = new ResourceString(R.string.no_title, null, 2, null);
                    }
                    mediaSessionController$update$1.L$0 = mediaSessionController3;
                    mediaSessionController$update$1.L$1 = mediaMetadata3;
                    mediaSessionController$update$1.label = 8;
                    stateFlowImpl3.updateState(null, resourceString);
                    if (Unit.INSTANCE != coroutineSingletons) {
                        MediaMetadata mediaMetadata4 = mediaMetadata3;
                        mediaSessionController2 = mediaSessionController3;
                        mediaMetadata2 = mediaMetadata4;
                        StateFlowImpl stateFlowImpl4 = mediaSessionController2._artistFlow;
                        String string3 = mediaMetadata2.getString("android.media.metadata.ARTIST");
                        mediaSessionController$update$1.L$0 = mediaSessionController2;
                        mediaSessionController$update$1.L$1 = mediaMetadata2;
                        mediaSessionController$update$1.label = 9;
                        stateFlowImpl4.setValue(string3);
                        if (Unit.INSTANCE != coroutineSingletons) {
                            StateFlowImpl stateFlowImpl5 = mediaSessionController2._durationFlow;
                            Long l = new Long(mediaMetadata2.getLong("android.media.metadata.DURATION"));
                            mediaSessionController$update$1.L$0 = null;
                            mediaSessionController$update$1.L$1 = null;
                            mediaSessionController$update$1.label = 10;
                            stateFlowImpl5.updateState(null, l);
                            break;
                        }
                    }
                } else {
                    ResourceString resourceString2 = new ResourceString(R.string.no_title, null, 2, null);
                    mediaSessionController$update$1.L$0 = mediaSessionController3;
                    mediaSessionController$update$1.label = 1;
                    mediaSessionController3._titleFlow.updateState(null, resourceString2);
                    if (Unit.INSTANCE != coroutineSingletons) {
                        StateFlowImpl stateFlowImpl6 = mediaSessionController3._artistFlow;
                        mediaSessionController$update$1.L$0 = mediaSessionController3;
                        mediaSessionController$update$1.label = 2;
                        stateFlowImpl6.setValue(null);
                        if (Unit.INSTANCE != coroutineSingletons) {
                            StateFlowImpl stateFlowImpl7 = mediaSessionController3._durationFlow;
                            Long l2 = new Long(0L);
                            mediaSessionController$update$1.L$0 = mediaSessionController3;
                            mediaSessionController$update$1.label = 3;
                            stateFlowImpl7.updateState(null, l2);
                            if (Unit.INSTANCE != coroutineSingletons) {
                                StateFlowImpl stateFlowImpl8 = mediaSessionController3._thumbnailFlow;
                                mediaSessionController$update$1.L$0 = mediaSessionController3;
                                mediaSessionController$update$1.label = 4;
                                stateFlowImpl8.setValue(null);
                                if (Unit.INSTANCE != coroutineSingletons) {
                                    StateFlowImpl stateFlowImpl9 = mediaSessionController3._thumbColorSchemeFlow;
                                    mediaSessionController$update$1.L$0 = null;
                                    mediaSessionController$update$1.label = 5;
                                    stateFlowImpl9.setValue(null);
                                    break;
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl62 = mediaSessionController3._artistFlow;
                mediaSessionController$update$1.L$0 = mediaSessionController3;
                mediaSessionController$update$1.label = 2;
                stateFlowImpl62.setValue(null);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl72 = mediaSessionController3._durationFlow;
                Long l22 = new Long(0L);
                mediaSessionController$update$1.L$0 = mediaSessionController3;
                mediaSessionController$update$1.label = 3;
                stateFlowImpl72.updateState(null, l22);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl82 = mediaSessionController3._thumbnailFlow;
                mediaSessionController$update$1.L$0 = mediaSessionController3;
                mediaSessionController$update$1.label = 4;
                stateFlowImpl82.setValue(null);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl92 = mediaSessionController3._thumbColorSchemeFlow;
                mediaSessionController$update$1.L$0 = null;
                mediaSessionController$update$1.label = 5;
                stateFlowImpl92.setValue(null);
                break;
            case 5:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 6:
                MediaMetadata mediaMetadata5 = (MediaMetadata) mediaSessionController$update$1.L$1;
                MediaSessionController mediaSessionController4 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                mediaMetadata3 = mediaMetadata5;
                mediaSessionController3 = mediaSessionController4;
                StateFlowImpl stateFlowImpl22 = mediaSessionController3._thumbColorSchemeFlow;
                mediaSessionController$update$1.L$0 = mediaSessionController3;
                mediaSessionController$update$1.L$1 = mediaMetadata3;
                mediaSessionController$update$1.label = 7;
                stateFlowImpl22.setValue(null);
                if (Unit.INSTANCE != coroutineSingletons) {
                    StateFlowImpl stateFlowImpl32 = mediaSessionController3._titleFlow;
                    string = mediaMetadata3.getString("android.media.metadata.TITLE");
                    resourceString = string;
                    if (string == null) {
                    }
                    if (resourceString == null) {
                    }
                    mediaSessionController$update$1.L$0 = mediaSessionController3;
                    mediaSessionController$update$1.L$1 = mediaMetadata3;
                    mediaSessionController$update$1.label = 8;
                    stateFlowImpl32.updateState(null, resourceString);
                    if (Unit.INSTANCE != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            case 7:
                MediaMetadata mediaMetadata6 = (MediaMetadata) mediaSessionController$update$1.L$1;
                MediaSessionController mediaSessionController5 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                mediaMetadata3 = mediaMetadata6;
                mediaSessionController3 = mediaSessionController5;
                StateFlowImpl stateFlowImpl322 = mediaSessionController3._titleFlow;
                string = mediaMetadata3.getString("android.media.metadata.TITLE");
                resourceString = string;
                if (string == null) {
                }
                if (resourceString == null) {
                }
                mediaSessionController$update$1.L$0 = mediaSessionController3;
                mediaSessionController$update$1.L$1 = mediaMetadata3;
                mediaSessionController$update$1.label = 8;
                stateFlowImpl322.updateState(null, resourceString);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                mediaMetadata2 = (MediaMetadata) mediaSessionController$update$1.L$1;
                mediaSessionController2 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl42 = mediaSessionController2._artistFlow;
                String string32 = mediaMetadata2.getString("android.media.metadata.ARTIST");
                mediaSessionController$update$1.L$0 = mediaSessionController2;
                mediaSessionController$update$1.L$1 = mediaMetadata2;
                mediaSessionController$update$1.label = 9;
                stateFlowImpl42.setValue(string32);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                mediaMetadata2 = (MediaMetadata) mediaSessionController$update$1.L$1;
                mediaSessionController2 = (MediaSessionController) mediaSessionController$update$1.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl52 = mediaSessionController2._durationFlow;
                Long l3 = new Long(mediaMetadata2.getLong("android.media.metadata.DURATION"));
                mediaSessionController$update$1.L$0 = null;
                mediaSessionController$update$1.L$1 = null;
                mediaSessionController$update$1.label = 10;
                stateFlowImpl52.updateState(null, l3);
                break;
            case 10:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void close() {
        Log.d("MediaSessionController", "stop()");
        this.isClosed = true;
        C09411 c09411 = new C09411(null);
        ContextScope contextScope = this.coroutineScope;
        BuildersKt.launch$default(contextScope, null, null, c09411, 3);
        StandaloneCoroutine standaloneCoroutine = this.colorSchemeLoader.processingJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressRunner.state(false);
        this.mediaController.unregisterCallback(this.callback);
        CoroutineScopeKt.cancel(contextScope, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0140  */
    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void execute(long j, long j2) {
        List<PlaybackState.CustomAction> customActions;
        Object next;
        SaEvent saEvent;
        List<PlaybackState.CustomAction> customActions2;
        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("execute() - ", j, " : ");
        sbM.append(j2);
        Log.d("MediaSessionController", sbM.toString());
        MediaController mediaController = this.mediaController;
        SessionController.Companion companion = SessionController.Companion;
        if (j == 8) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 89);
            return;
        }
        if (j == 16) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 88);
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.MediaPrevious.INSTANCE);
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
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.MediaPlayPause mediaPlayPause = SaEvent.MediaPlayPause.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.Action("Play")};
            moSaLogging.getClass();
            MoSaLogging.send(mediaPlayPause, saCustomArr);
            return;
        }
        if (j == 2) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 127);
            MoSaLogging moSaLogging2 = MoSaLogging.INSTANCE;
            SaEvent.MediaPlayPause mediaPlayPause2 = SaEvent.MediaPlayPause.INSTANCE;
            SaCustom[] saCustomArr2 = {new SaCustom.Action("Pause")};
            moSaLogging2.getClass();
            MoSaLogging.send(mediaPlayPause2, saCustomArr2);
            return;
        }
        if (j == 32) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 87);
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.MediaNext.INSTANCE);
            return;
        }
        if (j == 64) {
            companion.getClass();
            SessionController.Companion.dispatchMediaButtonEvent(mediaController, 90);
            return;
        }
        if (j == 256) {
            ProgressRunner progressRunner = this.progressRunner;
            if (j2 < 0) {
                progressRunner.state(false);
                return;
            }
            progressRunner.state(isPlaying());
            BuildersKt.launch$default(this.coroutineScope, null, null, new MediaSessionController$execute$1$1(this, j2, null), 3);
            mediaController.getTransportControls().seekTo(j2);
            return;
        }
        PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null || (customActions = playbackState.getCustomActions()) == null) {
            return;
        }
        Iterator<T> it = customActions.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((PlaybackState.CustomAction) next).getIcon() == j) {
                    break;
                }
            }
        }
        PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) next;
        if (customAction != null) {
            this.mediaController.getTransportControls().sendCustomAction(customAction, customAction.getExtras());
            PlaybackState playbackState2 = mediaController.getPlaybackState();
            if (playbackState2 == null || (customActions2 = playbackState2.getCustomActions()) == null) {
                saEvent = SaEvent.MediaControlCustomButton2.INSTANCE;
            } else {
                int iIndexOf = customActions2.indexOf(customAction);
                if ((iIndexOf == 0 ? Integer.valueOf(iIndexOf) : null) == null || (saEvent = SaEvent.MediaControlCustomButton1.INSTANCE) == null) {
                }
            }
            MoSaLogging moSaLogging3 = MoSaLogging.INSTANCE;
            SaCustom[] saCustomArr3 = {new SaCustom.Name(customAction.getName().toString())};
            moSaLogging3.getClass();
            MoSaLogging.send(saEvent, saCustomArr3);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getActionsFlow() {
        return this.actionsFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getAppColorSchemeFlow() {
        return this.appColorSchemeFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getAppIconFlow() {
        return this.appIconFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getAppName() {
        return (String) this.appName$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getArtistFlow() {
        return this.artistFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return Arrays.asList(new Pair(UniversalCredentialUtil.AGENT_TITLE, this._titleFlow.getValue()), new Pair("artist", this._artistFlow.getValue()), new Pair("duration", this._durationFlow.getValue()), new Pair(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, this._positionFlow.getValue()), new Pair("playbackState", this._playbackStateFlow.getValue()), new Pair(SystemUIAnalytics.QPNE_VID_ACTIONS, this._actionsFlow.getValue()), new Pair("mediaActions", this._mediaActionsFlow.getValue()));
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getDurationFlow() {
        return this.durationFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getId() {
        return (String) this.id$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getMediaActionsFlow() {
        return this.mediaActionsFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getPackageName() {
        return this.packageName;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getPlaybackStateFlow() {
        return this.playbackStateFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getPositionFlow() {
        return this.positionFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getThumbColorSchemeFlow() {
        return this.thumbColorSchemeFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getThumbnailFlow() {
        return this.thumbnailFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final ReadonlyStateFlow getTitleFlow() {
        return this.titleFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final boolean isClosed() {
        return this.isClosed;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final boolean isError() {
        return ((Number) this._playbackStateFlow.getValue()).intValue() != 7;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final boolean isPlaying() {
        MediaInfoExt mediaInfoExt = MediaInfoExt.INSTANCE;
        int iIntValue = ((Number) this._playbackStateFlow.getValue()).intValue();
        mediaInfoExt.getClass();
        return iIntValue == 3 || iIntValue == 6;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final boolean isSameToken(MediaSession.Token token) {
        return Intrinsics.areEqual(this.mediaController.getSessionToken(), token);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final boolean isSupportAction(long j) {
        return (((Number) this._actionsFlow.getValue()).longValue() & j) != 0;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void run() {
        this.progressRunner.state(isPlaying());
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void stop() {
        this.progressRunner.state(false);
    }

    public final String toString() {
        return toLogText();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:143:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:144:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:145:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:147:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$update(MediaSessionController mediaSessionController, PlaybackState playbackState, ContinuationImpl continuationImpl) {
        MediaSessionController$update$5 mediaSessionController$update$5;
        ArrayList arrayList;
        ArrayList arrayList2;
        MediaAction mediaAction;
        MediaAction mediaAction2;
        ArrayList arrayList3;
        MediaSessionController$update$5 mediaSessionController$update$52;
        MediaAction mediaAction3;
        boolean z;
        Object failure;
        char c;
        ArrayList arrayList4;
        MediaAction mediaAction4;
        String action;
        PlaybackState playbackState2;
        MediaSessionController mediaSessionController2;
        List list;
        MediaSessionController mediaSessionController3 = mediaSessionController;
        PlaybackState playbackState3 = playbackState;
        boolean z2 = true;
        mediaSessionController3.getClass();
        if (continuationImpl instanceof MediaSessionController$update$5) {
            mediaSessionController$update$5 = (MediaSessionController$update$5) continuationImpl;
            int i = mediaSessionController$update$5.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                mediaSessionController$update$5.label = i - Integer.MIN_VALUE;
            } else {
                mediaSessionController$update$5 = new MediaSessionController$update$5(mediaSessionController3, continuationImpl);
            }
        }
        Object obj = mediaSessionController$update$5.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        char c2 = 2;
        switch (mediaSessionController$update$5.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                Log.d("MediaSessionController", "update() - PlaybackState = " + playbackState3);
                StateFlowImpl stateFlowImpl = mediaSessionController3._positionFlow;
                if (playbackState3 == null) {
                    Long l = new Long(0L);
                    mediaSessionController$update$5.L$0 = mediaSessionController3;
                    mediaSessionController$update$5.label = 1;
                    stateFlowImpl.updateState(null, l);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    StateFlowImpl stateFlowImpl2 = mediaSessionController3._playbackStateFlow;
                    Integer num = new Integer(0);
                    mediaSessionController$update$5.L$0 = mediaSessionController3;
                    mediaSessionController$update$5.label = 2;
                    stateFlowImpl2.updateState(null, num);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    StateFlowImpl stateFlowImpl3 = mediaSessionController3._actionsFlow;
                    Long l2 = new Long(0L);
                    mediaSessionController$update$5.L$0 = mediaSessionController3;
                    mediaSessionController$update$5.label = 3;
                    stateFlowImpl3.updateState(null, l2);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    StateFlowImpl stateFlowImpl4 = mediaSessionController3._mediaActionsFlow;
                    EmptyList emptyList = EmptyList.INSTANCE;
                    mediaSessionController$update$5.L$0 = null;
                    mediaSessionController$update$5.label = 4;
                    stateFlowImpl4.setValue(emptyList);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                ArrayList arrayList5 = new ArrayList();
                Resources resources = (Resources) mediaSessionController3.resources$delegate.getValue();
                List<PlaybackState.CustomAction> customActions = playbackState3.getCustomActions();
                if (customActions != null) {
                    ArrayList arrayList6 = new ArrayList();
                    for (PlaybackState.CustomAction customAction : customActions) {
                        try {
                            int i2 = Result.$r8$clinit;
                            int icon = customAction.getIcon();
                            ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
                            failure = resources.getDrawable(icon, null);
                            z = z2;
                        } catch (Throwable th) {
                            int i3 = Result.$r8$clinit;
                            z = z2;
                            failure = new Result.Failure(th);
                        }
                        if (failure instanceof Result.Failure) {
                            failure = null;
                        }
                        Drawable drawable = (Drawable) failure;
                        if (drawable != null) {
                            MediaAction.Companion companion = MediaAction.Companion;
                            customAction.getClass();
                            companion.getClass();
                            c = c2;
                            arrayList4 = arrayList5;
                            long icon2 = customAction.getIcon();
                            TintDrawablePainter.Companion.getClass();
                            TintDrawablePainter converter = TintDrawablePainter.Companion.toConverter(drawable);
                            CharSequence name = customAction.getName();
                            if (name == null || (action = name.toString()) == null) {
                                action = customAction.getAction();
                            }
                            String str = action;
                            str.getClass();
                            mediaAction4 = new MediaAction(icon2, converter, str, false, 8, null);
                        } else {
                            c = c2;
                            arrayList4 = arrayList5;
                            mediaAction4 = null;
                        }
                        if (mediaAction4 != null) {
                            arrayList6.add(mediaAction4);
                        }
                        c2 = c;
                        z2 = z;
                        arrayList5 = arrayList4;
                    }
                    arrayList = arrayList5;
                    arrayList2 = new ArrayList(arrayList6);
                } else {
                    arrayList = arrayList5;
                    arrayList2 = new ArrayList();
                }
                MediaInfoExt mediaInfoExt = MediaInfoExt.INSTANCE;
                int state = playbackState3.getState();
                mediaInfoExt.getClass();
                if (state != 3 && state != 6) {
                    MediaAction.Companion.getClass();
                    mediaAction = MediaAction.play;
                } else {
                    MediaAction.Companion.getClass();
                    mediaAction = MediaAction.pause;
                }
                arrayList.add(MediaAction.copy$default(mediaAction, null, MediaInfoExt.isSet(playbackState3.getActions(), 518L), 7));
                if (MediaInfoExt.isSet(playbackState3.getActions(), 16L)) {
                    MediaAction.Companion.getClass();
                    mediaAction2 = MediaAction.previous;
                } else if (MediaInfoExt.isSet(playbackState3.getActions(), 8L)) {
                    MediaAction.Companion.getClass();
                    mediaAction2 = MediaAction.rewind;
                } else {
                    ArrayList arrayList7 = !arrayList2.isEmpty() ? arrayList2 : null;
                    mediaAction2 = arrayList7 != null ? (MediaAction) arrayList7.remove(0) : null;
                }
                arrayList3 = arrayList;
                if (mediaAction2 != null) {
                    arrayList3.add(0, mediaAction2);
                }
                mediaSessionController$update$52 = mediaSessionController$update$5;
                if (MediaInfoExt.isSet(playbackState3.getActions(), 32L)) {
                    MediaAction.Companion.getClass();
                    mediaAction3 = MediaAction.next;
                } else if (MediaInfoExt.isSet(playbackState3.getActions(), 64L)) {
                    MediaAction.Companion.getClass();
                    mediaAction3 = MediaAction.forward;
                } else {
                    ArrayList arrayList8 = !arrayList2.isEmpty() ? arrayList2 : null;
                    mediaAction3 = arrayList8 != null ? (MediaAction) arrayList8.remove(0) : null;
                }
                if (mediaAction3 != null) {
                    arrayList3.add(mediaAction3);
                }
                int size = arrayList2.size();
                int i4 = 0;
                while (i4 < size) {
                    Object obj2 = arrayList2.get(i4);
                    i4++;
                    MediaAction mediaAction5 = (MediaAction) obj2;
                    if ((arrayList3.size() < 5 ? arrayList3 : null) != null) {
                        if (arrayList3.size() % 2 == 0) {
                            arrayList3.add(mediaAction5);
                        } else {
                            arrayList3.add(0, mediaAction5);
                        }
                    }
                }
                Long l3 = new Long(playbackState3.getPosition());
                mediaSessionController$update$52.L$0 = mediaSessionController3;
                mediaSessionController$update$52.L$1 = playbackState3;
                mediaSessionController$update$52.L$2 = arrayList3;
                mediaSessionController$update$52.label = 5;
                stateFlowImpl.updateState(null, l3);
                if (Unit.INSTANCE == coroutineSingletons) {
                    return coroutineSingletons;
                }
                StateFlowImpl stateFlowImpl5 = mediaSessionController3._playbackStateFlow;
                Integer num2 = new Integer(playbackState3.getState());
                mediaSessionController$update$52.L$0 = mediaSessionController3;
                mediaSessionController$update$52.L$1 = playbackState3;
                mediaSessionController$update$52.L$2 = arrayList3;
                mediaSessionController$update$52.label = 6;
                stateFlowImpl5.updateState(null, num2);
                if (Unit.INSTANCE != coroutineSingletons) {
                    return coroutineSingletons;
                }
                playbackState2 = playbackState3;
                mediaSessionController2 = mediaSessionController3;
                list = arrayList3;
                StateFlowImpl stateFlowImpl6 = mediaSessionController2._actionsFlow;
                Long l4 = new Long(playbackState2.getActions());
                mediaSessionController$update$52.L$0 = mediaSessionController2;
                mediaSessionController$update$52.L$1 = list;
                mediaSessionController$update$52.L$2 = null;
                mediaSessionController$update$52.label = 7;
                stateFlowImpl6.updateState(null, l4);
                if (Unit.INSTANCE == coroutineSingletons) {
                    return coroutineSingletons;
                }
                StateFlowImpl stateFlowImpl7 = mediaSessionController2._mediaActionsFlow;
                mediaSessionController$update$52.L$0 = null;
                mediaSessionController$update$52.L$1 = null;
                mediaSessionController$update$52.label = 8;
                stateFlowImpl7.setValue(list);
                if (Unit.INSTANCE == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            case 1:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$5.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl22 = mediaSessionController3._playbackStateFlow;
                Integer num3 = new Integer(0);
                mediaSessionController$update$5.L$0 = mediaSessionController3;
                mediaSessionController$update$5.label = 2;
                stateFlowImpl22.updateState(null, num3);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                StateFlowImpl stateFlowImpl32 = mediaSessionController3._actionsFlow;
                Long l22 = new Long(0L);
                mediaSessionController$update$5.L$0 = mediaSessionController3;
                mediaSessionController$update$5.label = 3;
                stateFlowImpl32.updateState(null, l22);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                StateFlowImpl stateFlowImpl42 = mediaSessionController3._mediaActionsFlow;
                EmptyList emptyList2 = EmptyList.INSTANCE;
                mediaSessionController$update$5.L$0 = null;
                mediaSessionController$update$5.label = 4;
                stateFlowImpl42.setValue(emptyList2);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            case 2:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$5.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl322 = mediaSessionController3._actionsFlow;
                Long l222 = new Long(0L);
                mediaSessionController$update$5.L$0 = mediaSessionController3;
                mediaSessionController$update$5.label = 3;
                stateFlowImpl322.updateState(null, l222);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                StateFlowImpl stateFlowImpl422 = mediaSessionController3._mediaActionsFlow;
                EmptyList emptyList22 = EmptyList.INSTANCE;
                mediaSessionController$update$5.L$0 = null;
                mediaSessionController$update$5.label = 4;
                stateFlowImpl422.setValue(emptyList22);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            case 3:
                mediaSessionController3 = (MediaSessionController) mediaSessionController$update$5.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl4222 = mediaSessionController3._mediaActionsFlow;
                EmptyList emptyList222 = EmptyList.INSTANCE;
                mediaSessionController$update$5.L$0 = null;
                mediaSessionController$update$5.label = 4;
                stateFlowImpl4222.setValue(emptyList222);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            case 4:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 5:
                ?? r1 = (List) mediaSessionController$update$5.L$2;
                playbackState3 = (PlaybackState) mediaSessionController$update$5.L$1;
                MediaSessionController mediaSessionController4 = (MediaSessionController) mediaSessionController$update$5.L$0;
                ResultKt.throwOnFailure(obj);
                arrayList3 = r1;
                mediaSessionController3 = mediaSessionController4;
                mediaSessionController$update$52 = mediaSessionController$update$5;
                StateFlowImpl stateFlowImpl52 = mediaSessionController3._playbackStateFlow;
                Integer num22 = new Integer(playbackState3.getState());
                mediaSessionController$update$52.L$0 = mediaSessionController3;
                mediaSessionController$update$52.L$1 = playbackState3;
                mediaSessionController$update$52.L$2 = arrayList3;
                mediaSessionController$update$52.label = 6;
                stateFlowImpl52.updateState(null, num22);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                break;
            case 6:
                list = (List) mediaSessionController$update$5.L$2;
                PlaybackState playbackState4 = (PlaybackState) mediaSessionController$update$5.L$1;
                MediaSessionController mediaSessionController5 = (MediaSessionController) mediaSessionController$update$5.L$0;
                ResultKt.throwOnFailure(obj);
                playbackState2 = playbackState4;
                mediaSessionController2 = mediaSessionController5;
                mediaSessionController$update$52 = mediaSessionController$update$5;
                StateFlowImpl stateFlowImpl62 = mediaSessionController2._actionsFlow;
                Long l42 = new Long(playbackState2.getActions());
                mediaSessionController$update$52.L$0 = mediaSessionController2;
                mediaSessionController$update$52.L$1 = list;
                mediaSessionController$update$52.L$2 = null;
                mediaSessionController$update$52.label = 7;
                stateFlowImpl62.updateState(null, l42);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                StateFlowImpl stateFlowImpl72 = mediaSessionController2._mediaActionsFlow;
                mediaSessionController$update$52.L$0 = null;
                mediaSessionController$update$52.L$1 = null;
                mediaSessionController$update$52.label = 8;
                stateFlowImpl72.setValue(list);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            case 7:
                list = (List) mediaSessionController$update$5.L$1;
                mediaSessionController2 = (MediaSessionController) mediaSessionController$update$5.L$0;
                ResultKt.throwOnFailure(obj);
                mediaSessionController$update$52 = mediaSessionController$update$5;
                StateFlowImpl stateFlowImpl722 = mediaSessionController2._mediaActionsFlow;
                mediaSessionController$update$52.L$0 = null;
                mediaSessionController$update$52.L$1 = null;
                mediaSessionController$update$52.label = 8;
                stateFlowImpl722.setValue(list);
                if (Unit.INSTANCE == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            case 8:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
