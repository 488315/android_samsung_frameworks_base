package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import kotlin.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationShelfViewBinder$bind$2$1$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationShelf $this_apply;
    final /* synthetic */ NotificationShelfViewModel $viewModel;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$2$1$3$1, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass1 implements FlowCollector, FunctionAdapter {
        public final /* synthetic */ NotificationShelf $tmp0;

        public AnonymousClass1(NotificationShelf notificationShelf) {
            this.$tmp0 = notificationShelf;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(Object obj, Continuation continuation) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            NotificationShelf notificationShelf = this.$tmp0;
            notificationShelf.mCanInteract = booleanValue;
            notificationShelf.updateInteractiveness();
            Unit unit = Unit.INSTANCE;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            return unit;
        }

        public final boolean equals(Object obj) {
            if ((obj instanceof FlowCollector) && (obj instanceof FunctionAdapter)) {
                return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
            }
            return false;
        }

        @Override // kotlin.jvm.internal.FunctionAdapter
        public final Function getFunctionDelegate() {
            return new AdaptedFunctionReference(2, this.$tmp0, NotificationShelf.class, "setCanInteract", "setCanInteract(Z)V", 4);
        }

        public final int hashCode() {
            return getFunctionDelegate().hashCode();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$bind$2$1$3(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = notificationShelfViewModel;
        this.$this_apply = notificationShelf;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationShelfViewBinder$bind$2$1$3(this.$viewModel, this.$this_apply, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationShelfViewBinder$bind$2$1$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = ((KeyguardRepositoryImpl) this.$viewModel.interactor.keyguardRepository).isKeyguardShowing;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_apply);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
