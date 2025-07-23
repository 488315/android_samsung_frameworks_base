package com.android.systemui.volume.dialog.domain.interactor;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:
    
        if (r1.emit(null, r6) == r0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0057, code lost:
    
        if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r6) == r0) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2b
            if (r1 == r4) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r7)
            goto L66
        L13:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1b:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5a
        L23:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L49
        L2b:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel r1 = r6.$model
            com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel$Visible r1 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel.Visible) r1
            int r1 = r1.warning
            java.lang.Integer r5 = new java.lang.Integer
            r5.<init>(r1)
            r6.L$0 = r7
            r6.label = r4
            java.lang.Object r1 = r7.emit(r5, r6)
            if (r1 != r0) goto L48
            goto L65
        L48:
            r1 = r7
        L49:
            com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel r7 = r6.$model
            com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel$Visible r7 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel.Visible) r7
            long r4 = r7.duration
            r6.L$0 = r1
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r6)
            if (r7 != r0) goto L5a
            goto L65
        L5a:
            r7 = 0
            r6.L$0 = r7
            r6.label = r2
            java.lang.Object r6 = r1.emit(r7, r6)
            if (r6 != r0) goto L66
        L65:
            return r0
        L66:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogCsdWarningInteractor$csdWarning$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
