package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl;
import com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor;
import java.text.NumberFormat;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

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

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b0, code lost:
    
        if (r7 == r0) goto L38;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String string;
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            VolumeDialogRingerInteractor volumeDialogRingerInteractor = this.this$0.ringerInteractor;
            this.label = 1;
            obj = ((VolumeDialogRingerFeedbackRepositoryImpl) volumeDialogRingerInteractor.ringerFeedbackRepository).getToastCount(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        int iIntValue = ((Number) obj).intValue();
        if (iIntValue > 12) {
            return Unit.INSTANCE;
        }
        int i3 = this.$ringerMode;
        if (i3 == 0) {
            string = this.this$0.applicationContext.getString(17043598);
        } else if (i3 == 1 || i3 != 2) {
            string = this.this$0.applicationContext.getString(17043599);
        } else {
            VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel = this.this$0;
            int i4 = volumeDialogRingerDrawerViewModel.level;
            string = (i4 == -1 || (i = volumeDialogRingerDrawerViewModel.levelMax) == -1) ? null : volumeDialogRingerDrawerViewModel.applicationContext.getString(R.string.volume_dialog_ringer_guidance_ring, NumberFormat.getPercentInstance().format(i4 / i));
        }
        if (string != null) {
            Toast.makeText(this.this$0.applicationContext, string, 0).show();
        }
        VolumeDialogRingerInteractor volumeDialogRingerInteractor2 = this.this$0.ringerInteractor;
        this.label = 2;
        Object objUpdateToastCount = ((VolumeDialogRingerFeedbackRepositoryImpl) volumeDialogRingerInteractor2.ringerFeedbackRepository).updateToastCount(iIntValue, this);
        if (objUpdateToastCount != coroutineSingletons) {
            objUpdateToastCount = Unit.INSTANCE;
        }
    }
}
