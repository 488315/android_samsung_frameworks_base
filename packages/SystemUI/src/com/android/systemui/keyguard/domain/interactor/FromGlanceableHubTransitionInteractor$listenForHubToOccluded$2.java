package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
final class FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromGlanceableHubTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            Boolean bool2 = (Boolean) obj2;
            bool2.booleanValue();
            return new Pair(bool, bool2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2(FromGlanceableHubTransitionInteractor fromGlanceableHubTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromGlanceableHubTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final int i = 1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            FromGlanceableHubTransitionInteractor fromGlanceableHubTransitionInteractor = this.this$0;
            Utils.Companion companion = Utils.Companion;
            KeyguardInteractor keyguardInteractor = fromGlanceableHubTransitionInteractor.keyguardInteractor;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardInteractor.isKeyguardOccluded, keyguardInteractor.isDreaming, AnonymousClass3.INSTANCE);
            Duration.Companion companion2 = Duration.Companion;
            final int i3 = 0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(companion.sampleFilter(FlowKt.m3481debounceHG0u8IE(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, DurationKt.toDuration(200, DurationUnit.MILLISECONDS)), this.this$0.communalSceneInteractor.isLaunchingWidget, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i3) {
                        case 0:
                            return Boolean.valueOf(!((Boolean) obj2).booleanValue());
                        default:
                            Pair pair = (Pair) obj2;
                            return Boolean.valueOf(((Boolean) pair.component1()).booleanValue() && !((Boolean) pair.component2()).booleanValue());
                    }
                }
            }), fromGlanceableHubTransitionInteractor, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i) {
                        case 0:
                            return Boolean.valueOf(!((Boolean) obj2).booleanValue());
                        default:
                            Pair pair = (Pair) obj2;
                            return Boolean.valueOf(((Boolean) pair.component1()).booleanValue() && !((Boolean) pair.component2()).booleanValue());
                    }
                }
            });
            final FromGlanceableHubTransitionInteractor fromGlanceableHubTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2.6
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    CommunalSceneInteractor communalSceneInteractor = fromGlanceableHubTransitionInteractor2.communalSceneInteractor;
                    SceneKey sceneKey = CommunalScenes.Blank;
                    CommunalTransitionKeys.INSTANCE.getClass();
                    communalSceneInteractor.changeScene(sceneKey, "hub to occluded", CommunalTransitionKeys.SimpleFade, KeyguardState.OCCLUDED);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
