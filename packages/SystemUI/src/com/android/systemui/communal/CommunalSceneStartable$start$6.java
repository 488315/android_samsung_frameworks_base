package com.android.systemui.communal;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class CommunalSceneStartable$start$6 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalSceneStartable this$0;

    /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$6$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ CommunalSceneStartable this$0;

        /* renamed from: com.android.systemui.communal.CommunalSceneStartable$start$6$1$1, reason: invalid class name and collision with other inner class name */
        final class C00711 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $it;
            int label;
            final /* synthetic */ CommunalSceneStartable this$0;

            public C00711(CommunalSceneStartable communalSceneStartable, boolean z, Continuation continuation) {
                super(2, continuation);
                this.this$0 = communalSceneStartable;
                this.$it = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00711(this.this$0, this.$it, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00711) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        public AnonymousClass1(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
            super(2, continuation);
            this.this$0 = communalSceneStartable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                C00711 c00711 = new C00711(communalSceneStartable, z, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, c00711, this) == coroutineSingletons) {
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

    public CommunalSceneStartable$start$6(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneStartable$start$6(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneStartable$start$6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalSceneStartable communalSceneStartable = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = communalSceneStartable.communalSceneInteractor.isIdleOnCommunal;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(communalSceneStartable, null);
            this.label = 1;
            if (FlowKt.collectLatest(readonlyStateFlow, anonymousClass1, this) == coroutineSingletons) {
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
