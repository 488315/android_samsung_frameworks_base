package com.android.systemui.user.ui.dialog;

import android.app.Dialog;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSwitcherDialogCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1(UserSwitcherDialogCoordinator userSwitcherDialogCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherDialogCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(((UserSwitcherInteractor) this.this$0.interactor.get()).dialogDismissRequests);
            final UserSwitcherDialogCoordinator userSwitcherDialogCoordinator = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    UserSwitcherDialogCoordinator userSwitcherDialogCoordinator2 = UserSwitcherDialogCoordinator.this;
                    Dialog dialog = userSwitcherDialogCoordinator2.currentDialog;
                    if (dialog != null && dialog.isShowing()) {
                        dialog.cancel();
                    }
                    ((UserSwitcherInteractor) userSwitcherDialogCoordinator2.interactor.get())._dialogDismissRequests.setValue(null);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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
