package com.android.systemui.settings.multisim.ui.binder;

import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.ButtonType;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
final class MultiSIMPreferredBarViewBinder$bindButton$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Button $button;
    final /* synthetic */ ButtonType $type;
    final /* synthetic */ MultiSIMViewModel $vm;
    int label;

    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindButton$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Button $button;
        /* synthetic */ boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Button button, Continuation continuation) {
            super(2, continuation);
            this.$button = button;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$button, continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = (MultiSIMPreferredSlotView.PrefferedSlotButton) this.$button;
            if (z) {
                prefferedSlotButton.mSimNameText.setAlpha(0.35f);
                prefferedSlotButton.mCarrierNameText.setAlpha(0.35f);
                prefferedSlotButton.mSimNameOrAskText.setAlpha(0.35f);
                prefferedSlotButton.mSimImageForSimName.setAlpha(0.35f);
            } else {
                prefferedSlotButton.mSimNameText.setAlpha(0.8f);
                prefferedSlotButton.mCarrierNameText.setAlpha(0.6f);
                prefferedSlotButton.mSimNameOrAskText.setAlpha(0.8f);
                prefferedSlotButton.mSimImageForSimName.setAlpha(0.8f);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiSIMPreferredBarViewBinder$bindButton$2(MultiSIMViewModel multiSIMViewModel, ButtonType buttonType, Button button, Continuation continuation) {
        super(2, continuation);
        this.$vm = multiSIMViewModel;
        this.$type = buttonType;
        this.$button = button;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MultiSIMPreferredBarViewBinder$bindButton$2(this.$vm, this.$type, this.$button, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MultiSIMPreferredBarViewBinder$bindButton$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MultiSIMViewModel multiSIMViewModel = this.$vm;
            ButtonType buttonType = this.$type;
            MultiSIMViewModelImpl multiSIMViewModelImpl = (MultiSIMViewModelImpl) multiSIMViewModel;
            multiSIMViewModelImpl.getClass();
            Flow flow = buttonType == ButtonType.DATA ? multiSIMViewModelImpl.isButtonDisabledForData : multiSIMViewModelImpl.isButtonDisabled;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$button, null);
            this.label = 1;
            if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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
