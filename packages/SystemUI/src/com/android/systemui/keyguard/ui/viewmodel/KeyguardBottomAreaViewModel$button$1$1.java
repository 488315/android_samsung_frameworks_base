package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;

final class KeyguardBottomAreaViewModel$button$1$1 extends SuspendLambda implements Function6 {
    final /* synthetic */ KeyguardQuickAffordancePosition $position;
    final /* synthetic */ KeyguardBottomAreaViewModel.PreviewMode $previewMode;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    public KeyguardBottomAreaViewModel$button$1$1(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardBottomAreaViewModel.PreviewMode previewMode, Continuation continuation) {
        super(6, continuation);
        this.$position = keyguardQuickAffordancePosition;
        this.this$0 = keyguardBottomAreaViewModel;
        this.$previewMode = previewMode;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj5).booleanValue();
        KeyguardBottomAreaViewModel$button$1$1 keyguardBottomAreaViewModel$button$1$1 = new KeyguardBottomAreaViewModel$button$1$1(this.$position, this.this$0, this.$previewMode, (Continuation) obj6);
        keyguardBottomAreaViewModel$button$1$1.L$0 = (KeyguardQuickAffordanceModel) obj;
        keyguardBottomAreaViewModel$button$1$1.Z$0 = booleanValue;
        keyguardBottomAreaViewModel$button$1$1.Z$1 = booleanValue2;
        keyguardBottomAreaViewModel$button$1$1.L$1 = (String) obj4;
        keyguardBottomAreaViewModel$button$1$1.Z$2 = booleanValue3;
        return keyguardBottomAreaViewModel$button$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardQuickAffordanceModel keyguardQuickAffordanceModel = (KeyguardQuickAffordanceModel) this.L$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        String str = (String) this.L$1;
        boolean z3 = this.Z$2;
        String slotId = this.$position.toSlotId();
        boolean areEqual = Intrinsics.areEqual(str, slotId);
        final KeyguardBottomAreaViewModel keyguardBottomAreaViewModel = this.this$0;
        KeyguardBottomAreaViewModel.PreviewMode previewMode = this.$previewMode;
        boolean z4 = previewMode.isInPreviewMode;
        boolean z5 = !z4 && z;
        boolean z6 = z2 && !z4;
        boolean z7 = z4 && previewMode.shouldHighlightSelectedAffordance && areEqual;
        boolean z8 = z4 && previewMode.shouldHighlightSelectedAffordance && !areEqual;
        int i = KeyguardBottomAreaViewModel.$r8$clinit;
        keyguardBottomAreaViewModel.getClass();
        if (keyguardQuickAffordanceModel instanceof KeyguardQuickAffordanceModel.Visible) {
            KeyguardQuickAffordanceModel.Visible visible = (KeyguardQuickAffordanceModel.Visible) keyguardQuickAffordanceModel;
            return new KeyguardQuickAffordanceViewModel(visible.configKey, true, z5, visible.icon, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$toViewModel$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    KeyguardQuickAffordanceViewModel.OnClickedParameters onClickedParameters = (KeyguardQuickAffordanceViewModel.OnClickedParameters) obj2;
                    KeyguardBottomAreaViewModel.this.quickAffordanceInteractor.onQuickAffordanceTriggered(onClickedParameters.configKey, onClickedParameters.expandable, onClickedParameters.slotId);
                    return Unit.INSTANCE;
                }
            }, z6, !z4 && (visible.activationState instanceof ActivationState.Active), z7, z3, z8, slotId);
        }
        if (keyguardQuickAffordanceModel instanceof KeyguardQuickAffordanceModel.Hidden) {
            return new KeyguardQuickAffordanceViewModel(null, false, false, null, null, false, false, false, false, false, slotId, 1023, null);
        }
        throw new NoWhenBranchMatchedException();
    }
}
