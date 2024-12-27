package com.android.systemui.shade;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.util.ViewController;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

final class NotificationsQSContainerController$onInit$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationsQSContainerController this$0;

    /* renamed from: com.android.systemui.shade.NotificationsQSContainerController$onInit$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ NotificationsQSContainerController this$0;

        public AnonymousClass1(NotificationsQSContainerController notificationsQSContainerController, Continuation continuation) {
            super(2, continuation);
            this.this$0 = notificationsQSContainerController;
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
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow isQsExpanded = ((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.isQsExpanded();
                final NotificationsQSContainerController notificationsQSContainerController = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.NotificationsQSContainerController.onInit.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        View view;
                        ((Boolean) obj2).getClass();
                        view = ((ViewController) NotificationsQSContainerController.this).mView;
                        ((NotificationsQuickSettingsContainer) view).invalidate();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (isQsExpanded.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public NotificationsQSContainerController$onInit$1(NotificationsQSContainerController notificationsQSContainerController, Continuation continuation) {
        super(3, continuation);
        this.this$0 = notificationsQSContainerController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationsQSContainerController$onInit$1 notificationsQSContainerController$onInit$1 = new NotificationsQSContainerController$onInit$1(this.this$0, (Continuation) obj3);
        notificationsQSContainerController$onInit$1.L$0 = (LifecycleOwner) obj;
        return notificationsQSContainerController$onInit$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.this$0, null), 3);
        return Unit.INSTANCE;
    }
}
