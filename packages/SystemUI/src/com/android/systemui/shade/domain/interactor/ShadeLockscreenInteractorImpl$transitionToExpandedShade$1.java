package com.android.systemui.shade.domain.interactor;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class ShadeLockscreenInteractorImpl$transitionToExpandedShade$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $delay;
    int label;
    final /* synthetic */ ShadeLockscreenInteractorImpl this$0;

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeLockscreenInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeLockscreenInteractorImpl;
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
            ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl = this.this$0;
            shadeLockscreenInteractorImpl.getClass();
            SceneInteractor.changeScene$default(shadeLockscreenInteractorImpl.sceneInteractor, SceneFamilies.NotifShade, "ShadeLockscreenInteractorImpl.expandToNotifications", null, 12);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeLockscreenInteractorImpl$transitionToExpandedShade$1(long j, ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.$delay = j;
        this.this$0 = shadeLockscreenInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeLockscreenInteractorImpl$transitionToExpandedShade$1(this.$delay, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeLockscreenInteractorImpl$transitionToExpandedShade$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$delay;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl = this.this$0;
        CoroutineDispatcher coroutineDispatcher = shadeLockscreenInteractorImpl.mainDispatcher;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(shadeLockscreenInteractorImpl, null);
        this.label = 2;
        if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
