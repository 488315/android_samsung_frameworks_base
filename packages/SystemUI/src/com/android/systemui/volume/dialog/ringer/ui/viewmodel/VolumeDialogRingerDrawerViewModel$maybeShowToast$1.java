package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogRingerDrawerViewModel$maybeShowToast$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $ringerMode;
    int label;
    final /* synthetic */ VolumeDialogRingerDrawerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogRingerDrawerViewModel$maybeShowToast$1(VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogRingerDrawerViewModel;
        this.$ringerMode = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeDialogRingerDrawerViewModel$maybeShowToast$1(this.this$0, this.$ringerMode, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogRingerDrawerViewModel$maybeShowToast$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b0, code lost:
    
        if (r7 == r0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00b2, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x002e, code lost:
    
        if (r8 == r0) goto L38;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1d
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r8)
            goto Lb3
        L11:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L19:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L32
        L1d:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r8 = r7.this$0
            com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor r8 = r8.ringerInteractor
            r7.label = r3
            com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepository r8 = r8.ringerFeedbackRepository
            com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl r8 = (com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl) r8
            java.lang.Object r8 = r8.getToastCount(r7)
            if (r8 != r0) goto L32
            goto Lb2
        L32:
            java.lang.Number r8 = (java.lang.Number) r8
            int r8 = r8.intValue()
            r1 = 12
            if (r8 <= r1) goto L3f
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L3f:
            int r1 = r7.$ringerMode
            if (r1 == 0) goto L84
            r4 = 17043595(0x104108b, float:2.425644E-38)
            if (r1 == r3) goto L7b
            if (r1 == r2) goto L53
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r1 = r7.this$0
            android.content.Context r1 = r1.applicationContext
            java.lang.String r1 = r1.getString(r4)
            goto L8f
        L53:
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r1 = r7.this$0
            int r3 = r1.level
            r4 = -1
            if (r3 == r4) goto L79
            int r5 = r1.levelMax
            if (r5 == r4) goto L79
            android.content.Context r1 = r1.applicationContext
            long r3 = (long) r3
            long r5 = (long) r5
            double r3 = (double) r3
            double r5 = (double) r5
            double r3 = r3 / r5
            java.text.NumberFormat r5 = java.text.NumberFormat.getPercentInstance()
            java.lang.String r3 = r5.format(r3)
            java.lang.Object[] r3 = new java.lang.Object[]{r3}
            r4 = 2131957385(0x7f131689, float:1.9551352E38)
            java.lang.String r1 = r1.getString(r4, r3)
            goto L8f
        L79:
            r1 = 0
            goto L8f
        L7b:
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r1 = r7.this$0
            android.content.Context r1 = r1.applicationContext
            java.lang.String r1 = r1.getString(r4)
            goto L8f
        L84:
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r1 = r7.this$0
            android.content.Context r1 = r1.applicationContext
            r3 = 17043594(0x104108a, float:2.4256437E-38)
            java.lang.String r1 = r1.getString(r3)
        L8f:
            if (r1 == 0) goto L9d
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r3 = r7.this$0
            android.content.Context r3 = r3.applicationContext
            r4 = 0
            android.widget.Toast r1 = android.widget.Toast.makeText(r3, r1, r4)
            r1.show()
        L9d:
            com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel r1 = r7.this$0
            com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor r1 = r1.ringerInteractor
            r7.label = r2
            com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepository r1 = r1.ringerFeedbackRepository
            com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl r1 = (com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl) r1
            java.lang.Object r7 = r1.updateToastCount(r8, r7)
            if (r7 != r0) goto Lae
            goto Lb0
        Lae:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        Lb0:
            if (r7 != r0) goto Lb3
        Lb2:
            return r0
        Lb3:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$maybeShowToast$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
