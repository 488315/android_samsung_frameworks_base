package com.android.systemui.communal.ui.compose;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

final class CommunalHubKt$WidgetContent$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BaseCommunalViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    public CommunalHubKt$WidgetContent$1$1(BaseCommunalViewModel baseCommunalViewModel, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = baseCommunalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalHubKt$WidgetContent$1$1 communalHubKt$WidgetContent$1$1 = new CommunalHubKt$WidgetContent$1$1(this.$viewModel, continuation);
        communalHubKt$WidgetContent$1$1.L$0 = obj;
        return communalHubKt$WidgetContent$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$WidgetContent$1$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            final BaseCommunalViewModel baseCommunalViewModel = this.$viewModel;
            Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$1$1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    long j = ((Offset) obj2).packedValue;
                    BaseCommunalViewModel.this.onOpenEnableWorkProfileDialog();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (PointerInputScopeExtKt.observeTaps$default(pointerInputScope, true, function1, this, 1) == coroutineSingletons) {
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
