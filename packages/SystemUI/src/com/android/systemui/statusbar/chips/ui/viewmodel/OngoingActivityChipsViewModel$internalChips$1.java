package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OngoingActivityChipsViewModel$internalChips$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OngoingActivityChipsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingActivityChipsViewModel$internalChips$1(OngoingActivityChipsViewModel ongoingActivityChipsViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = ongoingActivityChipsViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        OngoingActivityChipsViewModel$internalChips$1 ongoingActivityChipsViewModel$internalChips$1 = new OngoingActivityChipsViewModel$internalChips$1(this.this$0, (Continuation) obj3);
        ongoingActivityChipsViewModel$internalChips$1.L$0 = (OngoingActivityChipsViewModel.ChipBundle) obj;
        ongoingActivityChipsViewModel$internalChips$1.Z$0 = booleanValue;
        return ongoingActivityChipsViewModel$internalChips$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        OngoingActivityChipsViewModel.MostImportantChipResult access$pickMostImportantChip = OngoingActivityChipsViewModel.access$pickMostImportantChip(this.this$0, (OngoingActivityChipsViewModel.ChipBundle) this.L$0);
        OngoingActivityChipsViewModel.InternalChipModel internalChipModel = access$pickMostImportantChip.mostImportantChip;
        if (internalChipModel instanceof OngoingActivityChipsViewModel.InternalChipModel.Inactive) {
            return new OngoingActivityChipsViewModel.InternalMultipleOngoingActivityChipsModel(internalChipModel, internalChipModel);
        }
        if (!(internalChipModel instanceof OngoingActivityChipsViewModel.InternalChipModel.Active)) {
            throw new NoWhenBranchMatchedException();
        }
        OngoingActivityChipsViewModel.InternalChipModel internalChipModel2 = OngoingActivityChipsViewModel.access$pickMostImportantChip(this.this$0, access$pickMostImportantChip.remainingChips).mostImportantChip;
        boolean z = internalChipModel2 instanceof OngoingActivityChipsViewModel.InternalChipModel.Active;
        return new OngoingActivityChipsViewModel.InternalMultipleOngoingActivityChipsModel(internalChipModel, internalChipModel2);
    }
}
