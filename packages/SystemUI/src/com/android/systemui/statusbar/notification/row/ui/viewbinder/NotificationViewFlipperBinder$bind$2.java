package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.widget.ViewFlipper;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

final class NotificationViewFlipperBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ViewFlipper $viewFlipper;
    final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ViewFlipper $viewFlipper;
        final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
        int label;

        public AnonymousClass1(NotificationViewFlipperViewModel notificationViewFlipperViewModel, ViewFlipper viewFlipper, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = notificationViewFlipperViewModel;
            this.$viewFlipper = viewFlipper;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$viewFlipper, continuation);
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
                Flow flow = this.$viewModel.isPaused;
                final ViewFlipper viewFlipper = this.$viewFlipper;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder.bind.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        NotificationViewFlipperBinder notificationViewFlipperBinder = NotificationViewFlipperBinder.INSTANCE;
                        ViewFlipper viewFlipper2 = viewFlipper;
                        notificationViewFlipperBinder.getClass();
                        if (booleanValue) {
                            viewFlipper2.stopFlipping();
                        } else if (viewFlipper2.isAutoStart()) {
                            viewFlipper2.startFlipping();
                        }
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

    public NotificationViewFlipperBinder$bind$2(NotificationViewFlipperViewModel notificationViewFlipperViewModel, ViewFlipper viewFlipper, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = notificationViewFlipperViewModel;
        this.$viewFlipper = viewFlipper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationViewFlipperBinder$bind$2 notificationViewFlipperBinder$bind$2 = new NotificationViewFlipperBinder$bind$2(this.$viewModel, this.$viewFlipper, continuation);
        notificationViewFlipperBinder$bind$2.L$0 = obj;
        return notificationViewFlipperBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationViewFlipperBinder$bind$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$viewModel, this.$viewFlipper, null), 3);
    }
}
