package com.android.systemui.volume.dialog.domain.interactor;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class VolumeDialogCsdWarningInteractor$csdWarning$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ VolumeDialogCsdWarningModel $model;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogCsdWarningInteractor$csdWarning$2$1(VolumeDialogCsdWarningModel volumeDialogCsdWarningModel, Continuation continuation) {
        super(2, continuation);
        this.$model = volumeDialogCsdWarningModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogCsdWarningInteractor$csdWarning$2$1 volumeDialogCsdWarningInteractor$csdWarning$2$1 = new VolumeDialogCsdWarningInteractor$csdWarning$2$1(this.$model, continuation);
        volumeDialogCsdWarningInteractor$csdWarning$2$1.L$0 = obj;
        return volumeDialogCsdWarningInteractor$csdWarning$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogCsdWarningInteractor$csdWarning$2$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0063, code lost:
    
        if (r1.emit(null, r6) != r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            Integer num = new Integer(((VolumeDialogCsdWarningModel.Visible) this.$model).warning);
            this.L$0 = flowCollector2;
            this.label = 1;
            if (flowCollector2.emit(num, this) != coroutineSingletons) {
                flowCollector = flowCollector2;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.L$0 = null;
            this.label = 3;
        }
        long j = ((VolumeDialogCsdWarningModel.Visible) this.$model).duration;
        this.L$0 = flowCollector;
        this.label = 2;
        if (DelayKt.m3469delayVtjQ1oo(j, this) != coroutineSingletons) {
            this.L$0 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
