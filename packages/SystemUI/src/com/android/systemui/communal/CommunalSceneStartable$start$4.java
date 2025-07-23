package com.android.systemui.communal;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.shared.model.CommunalScenes;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSceneStartable$start$4 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalSceneStartable this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$4$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CommunalSceneStartable this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
            super(2, continuation);
            this.this$0 = communalSceneStartable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                SceneKey sceneKey = (SceneKey) pair.component2();
                this.this$0.isDreaming = booleanValue;
                CommunalScenes.INSTANCE.getClass();
                if (Intrinsics.areEqual(sceneKey, CommunalScenes.Communal) && booleanValue && this.this$0.timeoutJob == null) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneStartable$start$4(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneStartable$start$4(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneStartable$start$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalSceneStartable communalSceneStartable = this.this$0;
            Flow sample = FlowKt.sample(communalSceneStartable.keyguardInteractor.isDreaming, communalSceneStartable.communalSceneInteractor.currentScene, AnonymousClass3.INSTANCE);
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, null);
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.collectLatest(sample, anonymousClass4, this) == coroutineSingletons) {
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
