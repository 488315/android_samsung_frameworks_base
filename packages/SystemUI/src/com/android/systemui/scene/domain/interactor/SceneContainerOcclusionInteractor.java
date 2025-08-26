package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor$special$$inlined$map$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* loaded from: classes2.dex */
public final class SceneContainerOcclusionInteractor {
    public final ReadonlyStateFlow invisibleDueToOcclusion;
    public final ReadonlyStateFlow isAodFullyOrPartiallyShown;
    public final ReadonlyStateFlow isOccludingActivityShown;

    /* renamed from: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function4 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
            AnonymousClass1 anonymousClass1 = SceneContainerOcclusionInteractor.this.new AnonymousClass1((Continuation) obj4);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.L$0 = (ObservableTransitionState) obj2;
            anonymousClass1.Z$1 = zBooleanValue2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
            boolean z2 = this.Z$1;
            SceneContainerOcclusionInteractor.this.getClass();
            return Boolean.valueOf(SceneContainerOcclusionInteractor.invisibleDueToOcclusion(z, observableTransitionState, z2));
        }
    }

    public SceneContainerOcclusionInteractor(CoroutineScope coroutineScope, KeyguardOcclusionInteractor keyguardOcclusionInteractor, SceneInteractor sceneInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        KeyguardOcclusionInteractor$special$$inlined$map$1 keyguardOcclusionInteractor$special$$inlined$map$1 = keyguardOcclusionInteractor.isShowWhenLockedActivityOnTop;
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(keyguardOcclusionInteractor$special$$inlined$map$1, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.isOccludingActivityShown = readonlyStateFlowStateIn;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SceneContainerOcclusionInteractor$isAodFullyOrPartiallyShown$1(null), keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.AOD));
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isAodFullyOrPartiallyShown = readonlyStateFlowStateIn2;
        this.invisibleDueToOcclusion = FlowKt.stateIn(FlowKt.combine(readonlyStateFlowStateIn, sceneInteractor.transitionState, readonlyStateFlowStateIn2, new AnonymousClass1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(invisibleDueToOcclusion(((Boolean) readonlyStateFlowStateIn.$$delegate_0.getValue()).booleanValue(), (ObservableTransitionState) sceneInteractor.transitionState.$$delegate_0.getValue(), ((Boolean) readonlyStateFlowStateIn2.$$delegate_0.getValue()).booleanValue())));
    }

    public static boolean getCanBeOccluded(ContentKey contentKey) {
        if (Intrinsics.areEqual(contentKey, Overlays.NotificationsShade) || Intrinsics.areEqual(contentKey, Overlays.QuickSettingsShade) || Intrinsics.areEqual(contentKey, Overlays.Bouncer)) {
            return false;
        }
        if (Intrinsics.areEqual(contentKey, Scenes.Communal)) {
            return true;
        }
        if (Intrinsics.areEqual(contentKey, Scenes.Dream)) {
            return false;
        }
        if (Intrinsics.areEqual(contentKey, Scenes.Gone) || Intrinsics.areEqual(contentKey, Scenes.Lockscreen)) {
            return true;
        }
        if (Intrinsics.areEqual(contentKey, Scenes.QuickSettings) || Intrinsics.areEqual(contentKey, Scenes.Shade)) {
            return false;
        }
        throw new IllegalStateException(("ContentKey \"" + contentKey + "\" doesn't have a mapping for canBeOccluded!").toString());
    }

    public static boolean invisibleDueToOcclusion(boolean z, ObservableTransitionState observableTransitionState, boolean z2) {
        if (!z || z2) {
            return false;
        }
        if (!(observableTransitionState instanceof ObservableTransitionState.Idle)) {
            if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
                throw new NoWhenBranchMatchedException();
            }
            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
            return getCanBeOccluded(transition.fromContent) && getCanBeOccluded(transition.toContent);
        }
        ObservableTransitionState.Idle idle = (ObservableTransitionState.Idle) observableTransitionState;
        Set set = idle.currentOverlays;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (!getCanBeOccluded((OverlayKey) it.next())) {
                    return false;
                }
            }
        }
        return getCanBeOccluded(idle.currentScene);
    }
}
