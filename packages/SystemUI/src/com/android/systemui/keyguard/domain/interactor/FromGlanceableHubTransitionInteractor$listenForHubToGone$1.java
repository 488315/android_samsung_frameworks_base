package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.communal.shared.model.EditModeState;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromGlanceableHubTransitionInteractor$listenForHubToGone$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromGlanceableHubTransitionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor$listenForHubToGone$1$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return new Pair(bool, (EditModeState) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGlanceableHubTransitionInteractor$listenForHubToGone$1(FromGlanceableHubTransitionInteractor fromGlanceableHubTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromGlanceableHubTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromGlanceableHubTransitionInteractor$listenForHubToGone$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGlanceableHubTransitionInteractor$listenForHubToGone$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromGlanceableHubTransitionInteractor fromGlanceableHubTransitionInteractor = this.this$0;
            BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
            Flow sample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(booleanFlowOperators.allOf(fromGlanceableHubTransitionInteractor.keyguardInteractor.isKeyguardGoingAway, booleanFlowOperators.noneOf(fromGlanceableHubTransitionInteractor.communalSceneInteractor.isLaunchingWidget)), fromGlanceableHubTransitionInteractor, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0(0)), this.this$0.communalSceneInteractor.editModeState, AnonymousClass4.INSTANCE);
            final FromGlanceableHubTransitionInteractor fromGlanceableHubTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor$listenForHubToGone$1.5
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    EditModeState editModeState = (EditModeState) ((Pair) obj2).component2();
                    if (editModeState == EditModeState.STARTING || editModeState == EditModeState.SHOWING) {
                        Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromGlanceableHubTransitionInteractor.this, KeyguardState.GONE, null, null, null, continuation, 14);
                        return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                    }
                    CommunalSceneInteractor communalSceneInteractor = FromGlanceableHubTransitionInteractor.this.communalSceneInteractor;
                    SceneKey sceneKey = CommunalScenes.Blank;
                    CommunalTransitionKeys.INSTANCE.getClass();
                    communalSceneInteractor.changeScene(sceneKey, "hub to gone", CommunalTransitionKeys.SimpleFade, KeyguardState.GONE);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
