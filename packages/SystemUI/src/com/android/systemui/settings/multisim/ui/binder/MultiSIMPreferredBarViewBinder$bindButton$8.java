package com.android.systemui.settings.multisim.ui.binder;

import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
final class MultiSIMPreferredBarViewBinder$bindButton$8 extends SuspendLambda implements Function2 {
    final /* synthetic */ Button $button;
    final /* synthetic */ MultiSIMViewModel $vm;
    int label;

    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindButton$8$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Button $button;
        /* synthetic */ int I$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Button button, Continuation continuation) {
            super(2, continuation);
            this.$button = button;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$button, continuation);
            anonymousClass1.I$0 = ((Number) obj).intValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int i = this.I$0;
            MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = (MultiSIMPreferredSlotView.PrefferedSlotButton) this.$button;
            if (i == prefferedSlotButton.mSimSlotId) {
                prefferedSlotButton.mTextSimPrimary.setVisibility(0);
            } else {
                prefferedSlotButton.mTextSimPrimary.setVisibility(8);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiSIMPreferredBarViewBinder$bindButton$8(MultiSIMViewModel multiSIMViewModel, Button button, Continuation continuation) {
        super(2, continuation);
        this.$vm = multiSIMViewModel;
        this.$button = button;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MultiSIMPreferredBarViewBinder$bindButton$8(this.$vm, this.$button, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MultiSIMPreferredBarViewBinder$bindButton$8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow = ((MultiSIMViewModelImpl) this.$vm).simInfoPrimaryId;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$button, null);
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
