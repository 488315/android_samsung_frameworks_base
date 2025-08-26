package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
final class KeyguardQuickAffordancesCombinedViewModel$button$1$1 extends SuspendLambda implements Function6 {
    final /* synthetic */ KeyguardQuickAffordancePosition $position;
    final /* synthetic */ KeyguardQuickAffordancesCombinedViewModel.PreviewMode $previewMode;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ KeyguardQuickAffordancesCombinedViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordancesCombinedViewModel$button$1$1(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardQuickAffordancesCombinedViewModel.PreviewMode previewMode, Continuation continuation) {
        super(6, continuation);
        this.$position = keyguardQuickAffordancePosition;
        this.this$0 = keyguardQuickAffordancesCombinedViewModel;
        this.$previewMode = previewMode;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj5).booleanValue();
        KeyguardQuickAffordancesCombinedViewModel$button$1$1 keyguardQuickAffordancesCombinedViewModel$button$1$1 = new KeyguardQuickAffordancesCombinedViewModel$button$1$1(this.$position, this.this$0, this.$previewMode, (Continuation) obj6);
        keyguardQuickAffordancesCombinedViewModel$button$1$1.L$0 = (KeyguardQuickAffordanceModel) obj;
        keyguardQuickAffordancesCombinedViewModel$button$1$1.Z$0 = zBooleanValue;
        keyguardQuickAffordancesCombinedViewModel$button$1$1.Z$1 = zBooleanValue2;
        keyguardQuickAffordancesCombinedViewModel$button$1$1.L$1 = (String) obj4;
        keyguardQuickAffordancesCombinedViewModel$button$1$1.Z$2 = zBooleanValue3;
        return keyguardQuickAffordancesCombinedViewModel$button$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardQuickAffordanceModel keyguardQuickAffordanceModel = (KeyguardQuickAffordanceModel) this.L$0;
        boolean z2 = this.Z$0;
        boolean z3 = this.Z$1;
        String str = (String) this.L$1;
        boolean z4 = this.Z$2;
        String slotId = this.$position.toSlotId();
        boolean zAreEqual = Intrinsics.areEqual(str, slotId);
        final KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = this.this$0;
        KeyguardQuickAffordancesCombinedViewModel.PreviewMode previewMode = this.$previewMode;
        boolean z5 = previewMode.isInPreviewMode;
        boolean z6 = false;
        if (z5 || !z2) {
            z = false;
        } else {
            z = false;
            z6 = true;
        }
        boolean z7 = (!z3 || z5) ? z : true;
        boolean z8 = previewMode.shouldHighlightSelectedAffordance;
        boolean z9 = (z5 && z8 && zAreEqual) ? true : z;
        boolean z10 = (z5 && z8 && !zAreEqual) ? true : z;
        int i = KeyguardQuickAffordancesCombinedViewModel.$r8$clinit;
        keyguardQuickAffordancesCombinedViewModel.getClass();
        if (keyguardQuickAffordanceModel instanceof KeyguardQuickAffordanceModel.Visible) {
            KeyguardQuickAffordanceModel.Visible visible = (KeyguardQuickAffordanceModel.Visible) keyguardQuickAffordanceModel;
            return new KeyguardQuickAffordanceViewModel(visible.configKey, false, z6, visible.icon, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    KeyguardQuickAffordanceViewModel.OnClickedParameters onClickedParameters = (KeyguardQuickAffordanceViewModel.OnClickedParameters) obj2;
                    keyguardQuickAffordancesCombinedViewModel.quickAffordanceInteractor.onQuickAffordanceTriggered(onClickedParameters.configKey, onClickedParameters.expandable, onClickedParameters.slotId);
                    return Unit.INSTANCE;
                }
            }, z7, (z5 || !(visible.activationState instanceof ActivationState.Active)) ? z : true, z9, z4, z10, slotId);
        }
        if (keyguardQuickAffordanceModel instanceof KeyguardQuickAffordanceModel.Hidden) {
            return new KeyguardQuickAffordanceViewModel(null, false, false, null, null, false, false, false, false, false, slotId, 1023, null);
        }
        throw new NoWhenBranchMatchedException();
    }
}
