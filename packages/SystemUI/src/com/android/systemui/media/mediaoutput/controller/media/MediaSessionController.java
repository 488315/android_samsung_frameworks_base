package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.datastore.core.DataStore;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.MediaInfoExt;
import com.android.systemui.media.mediaoutput.ext.MediaControllerExtKt;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.badge.MusicShareKt;
import com.android.systemui.media.mediaoutput.icons.feature.IcAuracastKt;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
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
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:
        
            if (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r3, r6, r5) == r0) goto L16;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L2c
                if (r1 == r3) goto L1c
                if (r1 != r2) goto L14
                java.lang.Object r5 = r5.L$0
                android.media.session.MediaController r5 = (android.media.session.MediaController) r5
                kotlin.ResultKt.throwOnFailure(r6)
                goto L5c
            L14:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L1c:
                java.lang.Object r1 = r5.L$2
                android.media.session.MediaController r1 = (android.media.session.MediaController) r1
                java.lang.Object r3 = r5.L$1
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r3 = (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController) r3
                java.lang.Object r4 = r5.L$0
                android.media.session.MediaController r4 = (android.media.session.MediaController) r4
                kotlin.ResultKt.throwOnFailure(r6)
                goto L48
            L2c:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r6 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.this
                android.media.session.MediaController r1 = r6.mediaController
                android.media.MediaMetadata r4 = r1.getMetadata()
                r5.L$0 = r1
                r5.L$1 = r6
                r5.L$2 = r1
                r5.label = r3
                java.lang.Object r3 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r6, r4, r5)
                if (r3 != r0) goto L46
                goto L5b
            L46:
                r3 = r6
                r4 = r1
            L48:
                android.media.session.PlaybackState r6 = r1.getPlaybackState()
                r5.L$0 = r4
                r1 = 0
                r5.L$1 = r1
                r5.L$2 = r1
                r5.label = r2
                java.lang.Object r5 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r3, r6, r5)
                if (r5 != r0) goto L5c
            L5b:
                return r0
            L5c:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:16:0x006e, code lost:
        
            if (r1.collect(r6, r5) == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0070, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x003d, code lost:
        
            if (r6 == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L20
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r6)
                goto L71
            L10:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L18:
                java.lang.Object r1 = r5.L$0
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r1 = (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController) r1
                kotlin.ResultKt.throwOnFailure(r6)
                goto L40
            L20:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r1 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.this
                com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt r6 = com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt.INSTANCE
                androidx.datastore.core.DataStore r4 = r1.dataStore
                r6.getClass()
                kotlinx.coroutines.flow.Flow r6 = r4.getData()
                com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2 r4 = new com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2
                r4.<init>(r6)
                r5.L$0 = r1
                r5.label = r3
                java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r4, r5)
                if (r6 != r0) goto L40
                goto L70
            L40:
                java.lang.Boolean r6 = (java.lang.Boolean) r6
                if (r6 == 0) goto L49
                boolean r6 = r6.booleanValue()
                goto L4a
            L49:
                r6 = 0
            L4a:
                r1.isGrayscaleThumbnail = r6
                com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt r6 = com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt.INSTANCE
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r1 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.this
                androidx.datastore.core.DataStore r1 = r1.dataStore
                r6.getClass()
                kotlinx.coroutines.flow.Flow r6 = r1.getData()
                com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2 r1 = new com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2
                r1.<init>(r6)
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$2$1 r6 = new com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$2$1
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r3 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.this
                r6.<init>()
                r3 = 0
                r5.L$0 = r3
                r5.label = r2
                java.lang.Object r5 = r1.collect(r6, r5)
                if (r5 != r0) goto L71
            L70:
                return r0
            L71:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0049, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0038, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L20
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r6)
                goto L4c
            L10:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L18:
                java.lang.Object r1 = r5.L$0
                com.android.systemui.monet.ColorScheme r1 = (com.android.systemui.monet.ColorScheme) r1
                kotlin.ResultKt.throwOnFailure(r6)
                goto L3b
            L20:
                kotlin.ResultKt.throwOnFailure(r6)
                java.lang.Object r6 = r5.L$0
                androidx.compose.ui.graphics.painter.Painter r6 = (androidx.compose.ui.graphics.painter.Painter) r6
                java.lang.Object r1 = r5.L$1
                com.android.systemui.monet.ColorScheme r1 = (com.android.systemui.monet.ColorScheme) r1
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r4 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.this
                kotlinx.coroutines.flow.StateFlowImpl r4 = r4._appIconFlow
                r5.L$0 = r1
                r5.label = r3
                r4.setValue(r6)
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                if (r6 != r0) goto L3b
                goto L4b
            L3b:
                com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r6 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.this
                kotlinx.coroutines.flow.StateFlowImpl r6 = r6._appColorSchemeFlow
                r3 = 0
                r5.L$0 = r3
                r5.label = r2
                r6.setValue(r1)
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                if (r5 != r0) goto L4c
            L4b:
                return r0
            L4c:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.AnonymousClass5.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
        this.coroutineScope = CoroutineScope;
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._titleFlow = MutableStateFlow;
        this.titleFlow = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._artistFlow = MutableStateFlow2;
        this.artistFlow = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(0L);
        this._durationFlow = MutableStateFlow3;
        this.durationFlow = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(0L);
        this._positionFlow = MutableStateFlow4;
        this.positionFlow = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(null);
        this._thumbnailFlow = MutableStateFlow5;
        this.thumbnailFlow = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(0);
        this._playbackStateFlow = MutableStateFlow6;
        this.playbackStateFlow = FlowKt.asStateFlow(MutableStateFlow6);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(0L);
        this._actionsFlow = MutableStateFlow7;
        this.actionsFlow = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._mediaActionsFlow = MutableStateFlow8;
        this.mediaActionsFlow = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._appIconFlow = MutableStateFlow9;
        this.appIconFlow = FlowKt.asStateFlow(MutableStateFlow9);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(null);
        this._appColorSchemeFlow = MutableStateFlow10;
        this.appColorSchemeFlow = FlowKt.asStateFlow(MutableStateFlow10);
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(null);
        this._thumbColorSchemeFlow = MutableStateFlow11;
        this.thumbColorSchemeFlow = FlowKt.asStateFlow(MutableStateFlow11);
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
                MediaSessionController mediaSessionController = MediaSessionController.this;
                BuildersKt.launch$default(mediaSessionController.coroutineScope, null, null, new MediaSessionController$callback$1$onMetadataChanged$1(mediaSessionController, mediaMetadata, null), 3);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                Log.d("MediaSessionController", "onPlaybackStateChanged() - " + playbackState);
                MediaSessionController mediaSessionController = MediaSessionController.this;
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
                MediaSessionController.this.close();
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionEvent(String str, Bundle bundle) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onSessionEvent() - ", str, "MediaSessionController");
            }
        };
        Log.d("MediaSessionController", "init()");
        BuildersKt.launch$default(CoroutineScope, null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(CoroutineScope, null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(CoroutineScope, null, null, new AnonymousClass3(null), 3);
        SessionController.Companion.getClass();
        if (SessionController.Companion.BLUETOOTH_MEDIA_SESSION_PACKAGE.contains(packageName)) {
            BuildersKt.launch$default(CoroutineScope, null, null, new AnonymousClass4(null), 3);
        } else {
            BuildersKt.launch$default(colorSchemeLoader.coroutineScope, null, null, new ColorSchemeLoader$process$2(context, new AnonymousClass5(null), packageName, null), 3);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0254, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0237, code lost:
    
        if (kotlin.Unit.INSTANCE != r4) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01fd, code lost:
    
        if (r6 > 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x01db, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x010c, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00fc, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ec, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d6, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c7, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01c9, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L104;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$update(com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r17, android.media.MediaMetadata r18, kotlin.coroutines.jvm.internal.ContinuationImpl r19) {
        /*
            Method dump skipped, instructions count: 628
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(com.android.systemui.media.mediaoutput.controller.media.MediaSessionController, android.media.MediaMetadata, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void close() {
        Log.d("MediaSessionController", "stop()");
        this.isClosed = true;
        MediaSessionController$close$1 mediaSessionController$close$1 = new MediaSessionController$close$1(this, null);
        ContextScope contextScope = this.coroutineScope;
        BuildersKt.launch$default(contextScope, null, null, mediaSessionController$close$1, 3);
        StandaloneCoroutine standaloneCoroutine = this.colorSchemeLoader.processingJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressRunner.state(false);
        this.mediaController.unregisterCallback(this.callback);
        CoroutineScopeKt.cancel(contextScope, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x013d, code lost:
    
        if (r5 != null) goto L63;
     */
    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void execute(long r6, long r8) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.execute(long, long):void");
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
        int intValue = ((Number) this._playbackStateFlow.getValue()).intValue();
        mediaInfoExt.getClass();
        return intValue == 3 || intValue == 6;
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
    /* JADX WARN: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0033  */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$update(com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r28, android.media.session.PlaybackState r29, kotlin.coroutines.jvm.internal.ContinuationImpl r30) {
        /*
            Method dump skipped, instructions count: 790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(com.android.systemui.media.mediaoutput.controller.media.MediaSessionController, android.media.session.PlaybackState, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
