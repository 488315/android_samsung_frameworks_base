package com.android.systemui.volume.dialog.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogViewModel$dialogTitle$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeDialogViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewModel$dialogTitle$2(VolumeDialogViewModel volumeDialogViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialogViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogViewModel$dialogTitle$2 volumeDialogViewModel$dialogTitle$2 = new VolumeDialogViewModel$dialogTitle$2(this.this$0, (Continuation) obj3);
        volumeDialogViewModel$dialogTitle$2.L$0 = (VolumeDialogStateModel) obj;
        volumeDialogViewModel$dialogTitle$2.L$1 = (VolumeDialogSliderType) obj2;
        return volumeDialogViewModel$dialogTitle$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VolumeDialogStateModel volumeDialogStateModel = (VolumeDialogStateModel) this.L$0;
        VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(((VolumeDialogSliderType) this.L$1).getAudioStream(), volumeDialogStateModel.streamModels);
        if (volumeDialogStreamModel == null) {
            return "";
        }
        Context context = this.this$0.context;
        String str = volumeDialogStreamModel.remoteLabel;
        if (str == null) {
            str = context.getResources().getString(volumeDialogStreamModel.name);
        }
        String string = context.getString(R.string.volume_dialog_title, str);
        return string == null ? "" : string;
    }
}
