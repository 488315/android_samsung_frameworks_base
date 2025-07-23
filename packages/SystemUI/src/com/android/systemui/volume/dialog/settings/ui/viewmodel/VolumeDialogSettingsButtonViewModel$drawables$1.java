package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewModel$drawables$1 extends SuspendLambda implements Function2 {
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ VolumeDialogSettingsButtonViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSettingsButtonViewModel$drawables$1(VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogSettingsButtonViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogSettingsButtonViewModel$drawables$1 volumeDialogSettingsButtonViewModel$drawables$1 = new VolumeDialogSettingsButtonViewModel$drawables$1(this.this$0, continuation);
        volumeDialogSettingsButtonViewModel$drawables$1.L$0 = obj;
        return volumeDialogSettingsButtonViewModel$drawables$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSettingsButtonViewModel$drawables$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0107, code lost:
    
        if (r5.emit(r1, r9) != r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0072, code lost:
    
        if (r10 == r0) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d6  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 269
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel$drawables$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
