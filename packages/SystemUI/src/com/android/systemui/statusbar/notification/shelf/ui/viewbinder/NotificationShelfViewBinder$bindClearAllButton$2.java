package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationShelfViewBinder$bindClearAllButton$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationShelfManager $shelfManager;
    final /* synthetic */ NotificationShelfViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bindClearAllButton$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationShelfManager $shelfManager;
        final /* synthetic */ NotificationShelfViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NotificationShelfViewModel notificationShelfViewModel, NotificationShelfManager notificationShelfManager, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = notificationShelfViewModel;
            this.$shelfManager = notificationShelfManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$shelfManager, continuation);
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
                Flow flow = this.$viewModel.clearAllButton.isVisible;
                final NotificationShelfManager notificationShelfManager = this.$shelfManager;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder.bindClearAllButton.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        NotificationShelfManager notificationShelfManager2 = NotificationShelfManager.this;
                        notificationShelfManager2.getClass();
                        Log.d("NotificationShelf", " setClearAllButtonVisible :" + booleanValue);
                        notificationShelfManager2.clearButtonVisible = booleanValue;
                        notificationShelfManager2.updateClearButton();
                        return Unit.INSTANCE;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$bindClearAllButton$2(NotificationShelfViewModel notificationShelfViewModel, NotificationShelfManager notificationShelfManager, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = notificationShelfViewModel;
        this.$shelfManager = notificationShelfManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationShelfViewBinder$bindClearAllButton$2 notificationShelfViewBinder$bindClearAllButton$2 = new NotificationShelfViewBinder$bindClearAllButton$2(this.$viewModel, this.$shelfManager, continuation);
        notificationShelfViewBinder$bindClearAllButton$2.L$0 = obj;
        return notificationShelfViewBinder$bindClearAllButton$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationShelfViewBinder$bindClearAllButton$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$viewModel, this.$shelfManager, null), 7);
    }
}
