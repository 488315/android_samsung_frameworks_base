package com.android.systemui.volume.dialog.domain.interactor;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
public final class VolumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public VolumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1 volumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1 = new VolumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3);
        volumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        volumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return volumeDialogCsdWarningInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            VolumeDialogCsdWarningModel volumeDialogCsdWarningModel = (VolumeDialogCsdWarningModel) this.L$1;
            if (volumeDialogCsdWarningModel instanceof VolumeDialogCsdWarningModel.Visible) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new SafeFlow(new VolumeDialogCsdWarningInteractor$csdWarning$2$1(volumeDialogCsdWarningModel, null));
            } else {
                if (!(volumeDialogCsdWarningModel instanceof VolumeDialogCsdWarningModel.Invisible)) {
                    throw new NoWhenBranchMatchedException();
                }
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
