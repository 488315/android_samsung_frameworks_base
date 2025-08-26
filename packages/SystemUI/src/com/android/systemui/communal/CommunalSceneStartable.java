package com.android.systemui.communal;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.util.settings.SystemSettings;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class CommunalSceneStartable implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public static final int DEFAULT_SCREEN_TIMEOUT = 15000;
    public final CoroutineScope bgScope;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public boolean isDreaming;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public int screenTimeout = DEFAULT_SCREEN_TIMEOUT;
    public final SystemSettings systemSettings;
    public StandaloneCoroutine timeoutJob;
    public final UiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C01611 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ CommunalSceneStartable this$0;

            /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C01621 extends SuspendLambda implements Function2 {
                final /* synthetic */ boolean $it;
                int label;
                final /* synthetic */ CommunalSceneStartable this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01621(CommunalSceneStartable communalSceneStartable, boolean z, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = communalSceneStartable;
                    this.$it = z;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C01621(this.this$0, this.$it, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01621) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    NotificationShadeWindowController notificationShadeWindowController = this.this$0.notificationShadeWindowController;
                    boolean z = this.$it;
                    NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
                    NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                    notificationShadeWindowState.glanceableHubShowing = z;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01611(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
                super(2, continuation);
                this.this$0 = communalSceneStartable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01611 c01611 = new C01611(this.this$0, continuation);
                c01611.Z$0 = ((Boolean) obj).booleanValue();
                return c01611;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((C01611) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    CommunalSceneStartable communalSceneStartable = this.this$0;
                    CoroutineDispatcher coroutineDispatcher = communalSceneStartable.mainDispatcher;
                    C01621 c01621 = new C01621(communalSceneStartable, z, null);
                    this.label = 1;
                    if (BuildersKt.withContext(coroutineDispatcher, c01621, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalSceneStartable.this.new AnonymousClass1(continuation);
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
                CommunalSceneStartable communalSceneStartable = CommunalSceneStartable.this;
                ReadonlyStateFlow readonlyStateFlow = communalSceneStartable.communalSceneInteractor.isIdleOnCommunal;
                C01611 c01611 = new C01611(communalSceneStartable, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlyStateFlow, c01611, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalSceneStartable.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalSceneStartable communalSceneStartable = CommunalSceneStartable.this;
            SystemSettings systemSettings = communalSceneStartable.systemSettings;
            CommunalSceneStartable.Companion.getClass();
            communalSceneStartable.screenTimeout = systemSettings.getIntForUser("screen_off_timeout", CommunalSceneStartable.DEFAULT_SCREEN_TIMEOUT, -2);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            int label;

            public AnonymousClass1(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
                anonymousClass1.L$0 = (SceneKey) obj;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                SceneKey sceneKey = (SceneKey) this.L$0;
                CommunalScenes.INSTANCE.getClass();
                return Boolean.valueOf(Intrinsics.areEqual(sceneKey, CommunalScenes.Communal));
            }
        }

        /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$3$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ CommunalSceneStartable this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
                super(2, continuation);
                this.this$0 = communalSceneStartable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                boolean z = this.Z$0;
                CommunalSceneStartable communalSceneStartable = this.this$0;
                StandaloneCoroutine standaloneCoroutine = communalSceneStartable.timeoutJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                communalSceneStartable.timeoutJob = null;
                if (z) {
                    CommunalSceneStartable communalSceneStartable2 = this.this$0;
                    if (communalSceneStartable2.timeoutJob == null) {
                        communalSceneStartable2.timeoutJob = CoroutineTracingKt.launchTraced$default(communalSceneStartable2.bgScope, null, null, new CommunalSceneStartable$startHubTimeout$1(communalSceneStartable2, null), 7);
                    }
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalSceneStartable.this.new AnonymousClass3(continuation);
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
                CommunalSceneStartable communalSceneStartable = CommunalSceneStartable.this;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(communalSceneStartable.communalSceneInteractor.currentScene, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), communalSceneStartable.communalInteractor.userActivity), new AnonymousClass1(null));
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(CommunalSceneStartable.this, null);
                this.label = 1;
                if (kotlinx.coroutines.flow.FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, anonymousClass2, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$4$3, reason: invalid class name */
        final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

            public AnonymousClass3() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return new Pair(bool, (SceneKey) obj2);
            }
        }

        /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$4$4, reason: invalid class name and collision with other inner class name */
        final class C01634 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ CommunalSceneStartable this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01634(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
                super(2, continuation);
                this.this$0 = communalSceneStartable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01634 c01634 = new C01634(this.this$0, continuation);
                c01634.L$0 = obj;
                return c01634;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01634) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Pair pair = (Pair) this.L$0;
                    boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                    SceneKey sceneKey = (SceneKey) pair.component2();
                    this.this$0.isDreaming = zBooleanValue;
                    CommunalScenes.INSTANCE.getClass();
                    if (Intrinsics.areEqual(sceneKey, CommunalScenes.Communal) && zBooleanValue && this.this$0.timeoutJob == null) {
                        this.label = 1;
                        if (DelayKt.delay(500L, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                    return Unit.INSTANCE;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CommunalSceneInteractor.changeScene$default(this.this$0.communalSceneInteractor, CommunalScenes.Blank, "dream started after timeout", null, null, 12);
                this.this$0.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_TIMEOUT);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalSceneStartable.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CommunalSceneStartable communalSceneStartable = CommunalSceneStartable.this;
                Flow flowSample = com.android.systemui.util.kotlin.FlowKt.sample(communalSceneStartable.keyguardInteractor.isDreaming, communalSceneStartable.communalSceneInteractor.currentScene, AnonymousClass3.INSTANCE);
                C01634 c01634 = new C01634(CommunalSceneStartable.this, null);
                this.label = 1;
                if (kotlinx.coroutines.flow.FlowKt.collectLatest(flowSample, c01634, this) == coroutineSingletons) {
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

    public CommunalSceneStartable(CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalSceneInteractor communalSceneInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, SystemSettings systemSettings, NotificationShadeWindowController notificationShadeWindowController, CoroutineScope coroutineScope, CoroutineScope coroutineScope2, CoroutineDispatcher coroutineDispatcher, UiEventLogger uiEventLogger) {
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.systemSettings = systemSettings;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.bgScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.uiEventLogger = uiEventLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CommunalSettingsInteractor communalSettingsInteractor = this.communalSettingsInteractor;
        if (communalSettingsInteractor.isCommunalFlagEnabled()) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
            CoroutineScope coroutineScope = this.bgScope;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, anonymousClass1, 7);
            communalSettingsInteractor.isV2FlagEnabled();
            kotlinx.coroutines.flow.FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), SettingsProxyExt.INSTANCE.observerFlow(this.systemSettings, "screen_off_timeout")), new AnonymousClass2(null)), coroutineScope);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(null), 7);
            communalSettingsInteractor.isV2FlagEnabled();
        }
    }
}
