package com.android.systemui.communal.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.data.repository.CommunalSceneTransitionRepository;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class CommunalSceneTransitionInteractor implements CoreStartable, CommunalSceneInteractor.OnSceneAboutToChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public UUID currentTransitionId;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final CoroutineDispatcher mainImmediateDispatcher;
    public final ReadonlyStateFlow nextKeyguardState;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 nextKeyguardStateInternal;
    public StandaloneCoroutine progressJob;
    public final CommunalSceneTransitionRepository repository;
    public final CommunalSceneInteractor sceneInteractor;
    public final CommunalSettingsInteractor settingsInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$collectProgress$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ObservableTransitionState.Transition $transition;
        int label;
        final /* synthetic */ CommunalSceneTransitionInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ObservableTransitionState.Transition transition, CommunalSceneTransitionInteractor communalSceneTransitionInteractor, Continuation continuation) {
            super(2, continuation);
            this.$transition = transition;
            this.this$0 = communalSceneTransitionInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$transition, this.this$0, continuation);
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
                Flow flow = this.$transition.progress;
                final CommunalSceneTransitionInteractor communalSceneTransitionInteractor = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor.collectProgress.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object objUpdateTransition;
                        float fFloatValue = ((Number) obj2).floatValue();
                        CommunalSceneTransitionInteractor communalSceneTransitionInteractor2 = communalSceneTransitionInteractor;
                        UUID uuid = communalSceneTransitionInteractor2.currentTransitionId;
                        if (uuid == null) {
                            objUpdateTransition = Unit.INSTANCE;
                        } else {
                            objUpdateTransition = communalSceneTransitionInteractor2.internalTransitionInteractor.updateTransition(uuid, RangesKt___RangesKt.coerceIn(fFloatValue, 0.0f, 1.0f), TransitionState.RUNNING, continuation);
                            if (objUpdateTransition != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                objUpdateTransition = Unit.INSTANCE;
                            }
                        }
                        return objUpdateTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? objUpdateTransition : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishCurrentTransition$1, reason: invalid class name and case insensitive filesystem */
    final class C08341 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08341(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            CommunalSceneTransitionInteractor communalSceneTransitionInteractor = CommunalSceneTransitionInteractor.this;
            int i = CommunalSceneTransitionInteractor.$r8$clinit;
            return communalSceneTransitionInteractor.finishCurrentTransition(this);
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishReversedTransitionTo$1, reason: invalid class name and case insensitive filesystem */
    final class C08351 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C08351(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            CommunalSceneTransitionInteractor communalSceneTransitionInteractor = CommunalSceneTransitionInteractor.this;
            int i = CommunalSceneTransitionInteractor.$r8$clinit;
            return communalSceneTransitionInteractor.finishReversedTransitionTo(null, this);
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$startTransition$1, reason: invalid class name and case insensitive filesystem */
    final class C08361 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08361(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            CommunalSceneTransitionInteractor communalSceneTransitionInteractor = CommunalSceneTransitionInteractor.this;
            int i = CommunalSceneTransitionInteractor.$r8$clinit;
            return communalSceneTransitionInteractor.startTransition(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public CommunalSceneTransitionInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, CommunalSettingsInteractor communalSettingsInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalSceneInteractor communalSceneInteractor, CommunalSceneTransitionRepository communalSceneTransitionRepository, PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.settingsInteractor = communalSettingsInteractor;
        this.applicationScope = coroutineScope;
        this.mainImmediateDispatcher = coroutineDispatcher;
        this.sceneInteractor = communalSceneInteractor;
        this.repository = communalSceneTransitionRepository;
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.combine(powerInteractor.isAsleep, keyguardInteractor.isDreamingWithOverlay, keyguardInteractor.isKeyguardOccluded, keyguardInteractor.isKeyguardGoingAway, keyguardInteractor.isKeyguardShowing, new CommunalSceneTransitionInteractor$nextKeyguardStateInternal$1(null)));
        this.nextKeyguardStateInternal = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(communalSceneTransitionRepository.nextLockscreenTargetState, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalSceneTransitionInteractor$nextKeyguardState$1(null), flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1), new CommunalSceneTransitionInteractor$nextKeyguardState$2(null));
        SharingStarted.Companion.getClass();
        this.nextKeyguardState = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, KeyguardState.LOCKSCREEN);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x005a, code lost:
    
        if (r5.finishCurrentTransition(r0) == r1) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008b, code lost:
    
        if (r5.transitionKtfTo(r6, r0) == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleIdle(CommunalSceneTransitionInteractor communalSceneTransitionInteractor, ObservableTransitionState observableTransitionState, ObservableTransitionState.Idle idle, Continuation continuation) {
        CommunalSceneTransitionInteractor$handleIdle$1 communalSceneTransitionInteractor$handleIdle$1;
        KeyguardState keyguardState;
        communalSceneTransitionInteractor.getClass();
        if (continuation instanceof CommunalSceneTransitionInteractor$handleIdle$1) {
            communalSceneTransitionInteractor$handleIdle$1 = (CommunalSceneTransitionInteractor$handleIdle$1) continuation;
            int i = communalSceneTransitionInteractor$handleIdle$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                communalSceneTransitionInteractor$handleIdle$1.label = i - Integer.MIN_VALUE;
            } else {
                communalSceneTransitionInteractor$handleIdle$1 = new CommunalSceneTransitionInteractor$handleIdle$1(communalSceneTransitionInteractor, continuation);
            }
        }
        Object obj = communalSceneTransitionInteractor$handleIdle$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = communalSceneTransitionInteractor$handleIdle$1.label;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            communalSceneTransitionInteractor = (CommunalSceneTransitionInteractor) communalSceneTransitionInteractor$handleIdle$1.L$0;
            ResultKt.throwOnFailure(obj);
            communalSceneTransitionInteractor.repository.nextLockscreenTargetState.setValue(null);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        if ((observableTransitionState instanceof ObservableTransitionState.Transition) && communalSceneTransitionInteractor.currentTransitionId != null && Intrinsics.areEqual(idle.currentScene, ((ObservableTransitionState.Transition) observableTransitionState).toContent)) {
            communalSceneTransitionInteractor$handleIdle$1.label = 1;
        } else {
            if (Intrinsics.areEqual(idle.currentScene, CommunalScenes.Communal)) {
                keyguardState = KeyguardState.GLANCEABLE_HUB;
            } else {
                if (communalSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to != KeyguardState.GLANCEABLE_HUB) {
                    return Unit.INSTANCE;
                }
                keyguardState = (KeyguardState) communalSceneTransitionInteractor.nextKeyguardState.$$delegate_0.getValue();
            }
            communalSceneTransitionInteractor$handleIdle$1.L$0 = communalSceneTransitionInteractor;
            communalSceneTransitionInteractor$handleIdle$1.label = 2;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00ae A[PHI: r10 r12
      0x00ae: PHI (r10v1 com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) = 
      (r10v0 com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor)
      (r10v0 com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor)
      (r10v10 com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor)
     binds: [B:26:0x0090, B:28:0x00aa, B:19:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x00ae: PHI (r12v1 com.android.compose.animation.scene.ObservableTransitionState$Transition) = 
      (r12v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r12v0 com.android.compose.animation.scene.ObservableTransitionState$Transition)
      (r12v4 com.android.compose.animation.scene.ObservableTransitionState$Transition)
     binds: [B:26:0x0090, B:28:0x00aa, B:19:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleTransition(CommunalSceneTransitionInteractor communalSceneTransitionInteractor, ObservableTransitionState observableTransitionState, ObservableTransitionState.Transition transition, Continuation continuation) throws Throwable {
        CommunalSceneTransitionInteractor$handleTransition$1 communalSceneTransitionInteractor$handleTransition$1;
        Object objStartTransition;
        CommunalSceneTransitionInteractor communalSceneTransitionInteractor2;
        ObservableTransitionState.Transition transition2;
        Object objStartTransition2;
        CommunalSceneTransitionInteractor communalSceneTransitionInteractor3;
        ObservableTransitionState.Transition transition3;
        communalSceneTransitionInteractor.getClass();
        if (continuation instanceof CommunalSceneTransitionInteractor$handleTransition$1) {
            communalSceneTransitionInteractor$handleTransition$1 = (CommunalSceneTransitionInteractor$handleTransition$1) continuation;
            int i = communalSceneTransitionInteractor$handleTransition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                communalSceneTransitionInteractor$handleTransition$1.label = i - Integer.MIN_VALUE;
            } else {
                communalSceneTransitionInteractor$handleTransition$1 = new CommunalSceneTransitionInteractor$handleTransition$1(communalSceneTransitionInteractor, continuation);
            }
        }
        Object obj = communalSceneTransitionInteractor$handleTransition$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = communalSceneTransitionInteractor$handleTransition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ContentKey contentKey = transition.fromContent;
            ContentKey contentKey2 = transition.toContent;
            if (!observableTransitionState.isTransitioning(contentKey, contentKey2)) {
                if (Intrinsics.areEqual(contentKey2, CommunalScenes.Communal)) {
                    if (communalSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to == KeyguardState.GLANCEABLE_HUB) {
                        KeyguardState keyguardState = ((TransitionStep) communalSceneTransitionInteractor.transitionInteractor.startedKeyguardTransitionStep.$$delegate_0.getValue()).from;
                        communalSceneTransitionInteractor$handleTransition$1.L$0 = communalSceneTransitionInteractor;
                        communalSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                        communalSceneTransitionInteractor$handleTransition$1.label = 1;
                        if (communalSceneTransitionInteractor.transitionKtfTo(keyguardState, communalSceneTransitionInteractor$handleTransition$1) != coroutineSingletons) {
                            communalSceneTransitionInteractor$handleTransition$1.L$0 = communalSceneTransitionInteractor;
                            communalSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                            communalSceneTransitionInteractor$handleTransition$1.label = 2;
                            objStartTransition = communalSceneTransitionInteractor.startTransition(new TransitionInfo("CommunalSceneTransitionInteractor", communalSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to, KeyguardState.GLANCEABLE_HUB, null, TransitionModeOnCanceled.RESET), communalSceneTransitionInteractor$handleTransition$1);
                            if (objStartTransition != coroutineSingletons) {
                            }
                            if (objStartTransition != coroutineSingletons) {
                            }
                        }
                    }
                } else if (Intrinsics.areEqual(contentKey2, CommunalScenes.Blank)) {
                    KeyguardState keyguardState2 = KeyguardState.GLANCEABLE_HUB;
                    communalSceneTransitionInteractor$handleTransition$1.L$0 = communalSceneTransitionInteractor;
                    communalSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                    communalSceneTransitionInteractor$handleTransition$1.label = 3;
                    if (communalSceneTransitionInteractor.transitionKtfTo(keyguardState2, communalSceneTransitionInteractor$handleTransition$1) != coroutineSingletons) {
                        communalSceneTransitionInteractor$handleTransition$1.L$0 = communalSceneTransitionInteractor;
                        communalSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                        communalSceneTransitionInteractor$handleTransition$1.label = 4;
                        communalSceneTransitionInteractor.getClass();
                        TransitionInfo transitionInfo = new TransitionInfo("CommunalSceneTransitionInteractor", KeyguardState.GLANCEABLE_HUB, (KeyguardState) communalSceneTransitionInteractor.nextKeyguardState.$$delegate_0.getValue(), null, TransitionModeOnCanceled.RESET);
                        communalSceneTransitionInteractor.repository.nextLockscreenTargetState.setValue(null);
                        objStartTransition2 = communalSceneTransitionInteractor.startTransition(transitionInfo, communalSceneTransitionInteractor$handleTransition$1);
                        if (objStartTransition2 != coroutineSingletons) {
                        }
                        if (objStartTransition2 != coroutineSingletons) {
                        }
                    }
                }
                return coroutineSingletons;
            }
            communalSceneTransitionInteractor.collectProgress(transition);
        } else {
            if (i2 == 1) {
                transition = (ObservableTransitionState.Transition) communalSceneTransitionInteractor$handleTransition$1.L$1;
                communalSceneTransitionInteractor = (CommunalSceneTransitionInteractor) communalSceneTransitionInteractor$handleTransition$1.L$0;
                ResultKt.throwOnFailure(obj);
                communalSceneTransitionInteractor$handleTransition$1.L$0 = communalSceneTransitionInteractor;
                communalSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                communalSceneTransitionInteractor$handleTransition$1.label = 2;
                objStartTransition = communalSceneTransitionInteractor.startTransition(new TransitionInfo("CommunalSceneTransitionInteractor", communalSceneTransitionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to, KeyguardState.GLANCEABLE_HUB, null, TransitionModeOnCanceled.RESET), communalSceneTransitionInteractor$handleTransition$1);
                if (objStartTransition != coroutineSingletons) {
                    objStartTransition = Unit.INSTANCE;
                }
                if (objStartTransition != coroutineSingletons) {
                    communalSceneTransitionInteractor2 = communalSceneTransitionInteractor;
                    transition2 = transition;
                    communalSceneTransitionInteractor2.collectProgress(transition2);
                }
                return coroutineSingletons;
            }
            if (i2 == 2) {
                transition2 = (ObservableTransitionState.Transition) communalSceneTransitionInteractor$handleTransition$1.L$1;
                communalSceneTransitionInteractor2 = (CommunalSceneTransitionInteractor) communalSceneTransitionInteractor$handleTransition$1.L$0;
                ResultKt.throwOnFailure(obj);
                communalSceneTransitionInteractor2.collectProgress(transition2);
            } else {
                if (i2 == 3) {
                    transition = (ObservableTransitionState.Transition) communalSceneTransitionInteractor$handleTransition$1.L$1;
                    communalSceneTransitionInteractor = (CommunalSceneTransitionInteractor) communalSceneTransitionInteractor$handleTransition$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    communalSceneTransitionInteractor$handleTransition$1.L$0 = communalSceneTransitionInteractor;
                    communalSceneTransitionInteractor$handleTransition$1.L$1 = transition;
                    communalSceneTransitionInteractor$handleTransition$1.label = 4;
                    communalSceneTransitionInteractor.getClass();
                    TransitionInfo transitionInfo2 = new TransitionInfo("CommunalSceneTransitionInteractor", KeyguardState.GLANCEABLE_HUB, (KeyguardState) communalSceneTransitionInteractor.nextKeyguardState.$$delegate_0.getValue(), null, TransitionModeOnCanceled.RESET);
                    communalSceneTransitionInteractor.repository.nextLockscreenTargetState.setValue(null);
                    objStartTransition2 = communalSceneTransitionInteractor.startTransition(transitionInfo2, communalSceneTransitionInteractor$handleTransition$1);
                    if (objStartTransition2 != coroutineSingletons) {
                        objStartTransition2 = Unit.INSTANCE;
                    }
                    if (objStartTransition2 != coroutineSingletons) {
                        communalSceneTransitionInteractor3 = communalSceneTransitionInteractor;
                        transition3 = transition;
                        communalSceneTransitionInteractor3.collectProgress(transition3);
                    }
                    return coroutineSingletons;
                }
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                transition3 = (ObservableTransitionState.Transition) communalSceneTransitionInteractor$handleTransition$1.L$1;
                communalSceneTransitionInteractor3 = (CommunalSceneTransitionInteractor) communalSceneTransitionInteractor$handleTransition$1.L$0;
                ResultKt.throwOnFailure(obj);
                communalSceneTransitionInteractor3.collectProgress(transition3);
            }
        }
        return Unit.INSTANCE;
    }

    public final void collectProgress(ObservableTransitionState.Transition transition) {
        StandaloneCoroutine standaloneCoroutine = this.progressJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressJob = CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainImmediateDispatcher, null, new AnonymousClass1(transition, this, null), 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object finishCurrentTransition(ContinuationImpl continuationImpl) {
        C08341 c08341;
        if (continuationImpl instanceof C08341) {
            c08341 = (C08341) continuationImpl;
            int i = c08341.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08341.label = i - Integer.MIN_VALUE;
            } else {
                c08341 = new C08341(continuationImpl);
            }
        }
        Object obj = c08341.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08341.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            UUID uuid = this.currentTransitionId;
            if (uuid == null) {
                return Unit.INSTANCE;
            }
            TransitionState transitionState = TransitionState.FINISHED;
            c08341.L$0 = this;
            c08341.label = 1;
            if (this.internalTransitionInteractor.updateTransition(uuid, 1.0f, transitionState, c08341) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (CommunalSceneTransitionInteractor) c08341.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.resetTransitionData();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object finishReversedTransitionTo(KeyguardState keyguardState, ContinuationImpl continuationImpl) throws Throwable {
        C08351 c08351;
        CommunalSceneTransitionInteractor communalSceneTransitionInteractor;
        CommunalSceneTransitionInteractor communalSceneTransitionInteractor2;
        if (continuationImpl instanceof C08351) {
            c08351 = (C08351) continuationImpl;
            int i = c08351.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08351.label = i - Integer.MIN_VALUE;
            } else {
                c08351 = new C08351(continuationImpl);
            }
        }
        Object objStartTransition = c08351.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08351.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStartTransition);
            InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor = this.internalTransitionInteractor;
            TransitionInfo transitionInfo = new TransitionInfo("CommunalSceneTransitionInteractor", internalKeyguardTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to, keyguardState, null, TransitionModeOnCanceled.REVERSE);
            c08351.L$0 = this;
            c08351.L$1 = this;
            c08351.label = 1;
            objStartTransition = ((KeyguardTransitionRepositoryImpl) internalKeyguardTransitionInteractor.repository).startTransition(transitionInfo, c08351);
            if (objStartTransition != coroutineSingletons) {
                communalSceneTransitionInteractor = this;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            communalSceneTransitionInteractor2 = (CommunalSceneTransitionInteractor) c08351.L$0;
            ResultKt.throwOnFailure(objStartTransition);
            communalSceneTransitionInteractor2.resetTransitionData();
            return Unit.INSTANCE;
        }
        this = (CommunalSceneTransitionInteractor) c08351.L$1;
        communalSceneTransitionInteractor = (CommunalSceneTransitionInteractor) c08351.L$0;
        ResultKt.throwOnFailure(objStartTransition);
        this.currentTransitionId = (UUID) objStartTransition;
        InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor2 = communalSceneTransitionInteractor.internalTransitionInteractor;
        UUID uuid = communalSceneTransitionInteractor.currentTransitionId;
        uuid.getClass();
        TransitionState transitionState = TransitionState.FINISHED;
        c08351.L$0 = communalSceneTransitionInteractor;
        c08351.L$1 = null;
        c08351.label = 2;
        if (internalKeyguardTransitionInteractor2.updateTransition(uuid, 1.0f, transitionState, c08351) != coroutineSingletons) {
            communalSceneTransitionInteractor2 = communalSceneTransitionInteractor;
            communalSceneTransitionInteractor2.resetTransitionData();
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }

    @Override // com.android.systemui.communal.domain.interactor.CommunalSceneInteractor.OnSceneAboutToChangeListener
    public final void onSceneAboutToChange(SceneKey sceneKey, KeyguardState keyguardState) {
        if (!Intrinsics.areEqual(sceneKey, CommunalScenes.Blank) || keyguardState == null) {
            return;
        }
        this.repository.nextLockscreenTargetState.updateState(null, keyguardState);
    }

    public final void resetTransitionData() {
        StandaloneCoroutine standaloneCoroutine = this.progressJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressJob = null;
        this.currentTransitionId = null;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.settingsInteractor.isCommunalFlagEnabled()) {
            CommunalSceneInteractor communalSceneInteractor = this.sceneInteractor;
            communalSceneInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SceneContainerFlag.$r8$clinit;
            communalSceneInteractor.onSceneAboutToChangeListener.add(this);
            CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainImmediateDispatcher, null, new CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1(this, null), 4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object startTransition(TransitionInfo transitionInfo, ContinuationImpl continuationImpl) throws Throwable {
        C08361 c08361;
        if (continuationImpl instanceof C08361) {
            c08361 = (C08361) continuationImpl;
            int i = c08361.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08361.label = i - Integer.MIN_VALUE;
            } else {
                c08361 = new C08361(continuationImpl);
            }
        }
        Object objStartTransition = c08361.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08361.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStartTransition);
            if (this.currentTransitionId != null) {
                resetTransitionData();
            }
            c08361.L$0 = this;
            c08361.label = 1;
            objStartTransition = ((KeyguardTransitionRepositoryImpl) this.internalTransitionInteractor.repository).startTransition(transitionInfo, c08361);
            if (objStartTransition == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (CommunalSceneTransitionInteractor) c08361.L$0;
            ResultKt.throwOnFailure(objStartTransition);
        }
        this.currentTransitionId = (UUID) objStartTransition;
        return Unit.INSTANCE;
    }

    public final Object transitionKtfTo(KeyguardState keyguardState, ContinuationImpl continuationImpl) throws Throwable {
        TransitionStep transitionStep = (TransitionStep) this.transitionInteractor.transitionState.$$delegate_0.getValue();
        KeyguardState keyguardState2 = transitionStep.to;
        if (keyguardState2 == keyguardState) {
            if (transitionStep.transitionState == TransitionState.FINISHED) {
                resetTransitionData();
                return Unit.INSTANCE;
            }
        }
        if (keyguardState == null || keyguardState2 == keyguardState) {
            Object objFinishCurrentTransition = finishCurrentTransition(continuationImpl);
            return objFinishCurrentTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? objFinishCurrentTransition : Unit.INSTANCE;
        }
        Object objFinishReversedTransitionTo = finishReversedTransitionTo(keyguardState, continuationImpl);
        return objFinishReversedTransitionTo == CoroutineSingletons.COROUTINE_SUSPENDED ? objFinishReversedTransitionTo : Unit.INSTANCE;
    }
}
