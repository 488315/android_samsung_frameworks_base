package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShareToAppChipViewModel$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShareToAppChipViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShareToAppChipViewModel$start$1(ShareToAppChipViewModel shareToAppChipViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shareToAppChipViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShareToAppChipViewModel$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShareToAppChipViewModel$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ShareToAppChipViewModel shareToAppChipViewModel = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = shareToAppChipViewModel.stopDialogDueToCallEndedState;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ShareToAppChipViewModel.this._stopDialogToShow.setValue((MediaProjectionStopDialogModel) obj2);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
