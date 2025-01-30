package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.viewmodel.C1751xfa49bb21;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6$invokeSuspend$$inlined$flatMapLatest$1", m277f = "KeyguardSecBottomAreaViewBinder.kt", m278l = {190}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1$1$6$invokeSuspend$$inlined$flatMapLatest$1 */
/* loaded from: classes.dex */
public final class C1744x5446f995 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1744x5446f995(Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        super(3, continuation);
        this.$viewModel$inlined = keyguardBottomAreaViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C1744x5446f995 c1744x5446f995 = new C1744x5446f995((Continuation) obj3, this.$viewModel$inlined);
        c1744x5446f995.L$0 = (FlowCollector) obj;
        c1744x5446f995.L$1 = obj2;
        return c1744x5446f995.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int intValue = ((Number) this.L$1).intValue();
            KeyguardBottomAreaViewModel keyguardBottomAreaViewModel = this.$viewModel$inlined;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new C1751xfa49bb21(keyguardBottomAreaViewModel.keyguardInteractor.dozeAmount, keyguardBottomAreaViewModel, intValue));
            this.label = 1;
            if (FlowKt.emitAll(this, distinctUntilChanged, flowCollector) == coroutineSingletons) {
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
