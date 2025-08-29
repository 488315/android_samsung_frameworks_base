package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class BouncerToGoneFlows {
    public final KeyguardTransitionAnimationFlow animationFlow;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final ShadeInteractor shadeInteractor;
    public final SysuiStatusBarStateController statusBarStateController;

    public BouncerToGoneFlows(SysuiStatusBarStateController sysuiStatusBarStateController, PrimaryBouncerInteractor primaryBouncerInteractor, Lazy lazy, ShadeInteractor shadeInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.shadeInteractor = shadeInteractor;
        this.animationFlow = keyguardTransitionAnimationFlow;
    }

    /* renamed from: createScrimAlphaFlow-KLykuaI, reason: not valid java name */
    public final ChannelFlowTransformLatest m2619createScrimAlphaFlowKLykuaI(long j, KeyguardState keyguardState, Function0 function0) {
        Edge stateToContent;
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
        Ref$BooleanRef ref$BooleanRef3 = new Ref$BooleanRef();
        if (keyguardState == KeyguardState.PRIMARY_BOUNCER) {
            Edge.Companion.getClass();
            stateToContent = Edge.INVALID;
        } else {
            Edge.Companion companion = Edge.Companion;
            SceneKey sceneKey = Scenes.Gone;
            companion.getClass();
            stateToContent = new Edge.StateToContent(keyguardState, sceneKey);
        }
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = this.animationFlow.m2613setupVtjQ1oo(j, stateToContent);
        Edge.Companion companion2 = Edge.Companion;
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion2.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = flowBuilderM2613setupVtjQ1oo.setupWithoutSceneContainer(new Edge.StateToState(keyguardState, keyguardState2));
        final StateFlow anyExpansion = ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.getAnyExpansion();
        return FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow-KLykuaI$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow-KLykuaI$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow-KLykuaI$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = anyExpansion.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new BouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1(null, flowBuilder, j, ref$BooleanRef2, this, ref$BooleanRef3, function0, ref$BooleanRef));
    }

    /* renamed from: showAllNotifications-VtjQ1oo, reason: not valid java name */
    public final Flow m2620showAllNotificationsVtjQ1oo(long j, KeyguardState keyguardState) {
        Edge stateToContent;
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        if (keyguardState == KeyguardState.PRIMARY_BOUNCER) {
            Edge.Companion.getClass();
            stateToContent = Edge.INVALID;
        } else {
            Edge.Companion companion = Edge.Companion;
            SceneKey sceneKey = Scenes.Gone;
            companion.getClass();
            stateToContent = new Edge.StateToContent(keyguardState, sceneKey);
        }
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = this.animationFlow.m2613setupVtjQ1oo(j, stateToContent);
        Edge.Companion companion2 = Edge.Companion;
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion2.getClass();
        final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo.setupWithoutSceneContainer(new Edge.StateToState(keyguardState, keyguardState2)), j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Float) obj).floatValue();
                return Float.valueOf(ref$BooleanRef.element ? 1.0f : 0.0f);
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ref$BooleanRef.element = ((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide;
                return Unit.INSTANCE;
            }
        }, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 196);
        return FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$showAllNotifications-VtjQ1oo$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$showAllNotifications-VtjQ1oo$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$showAllNotifications-VtjQ1oo$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() == 1.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }
}
