package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.ranges.ClosedFloatRange;

/* loaded from: classes3.dex */
final class VolumeDialogSliderViewModel$state$2 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ VolumeDialogSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderViewModel$state$2(VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = volumeDialogSliderViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        VolumeDialogSliderViewModel$state$2 volumeDialogSliderViewModel$state$2 = new VolumeDialogSliderViewModel$state$2(this.this$0, (Continuation) obj4);
        volumeDialogSliderViewModel$state$2.Z$0 = zBooleanValue;
        volumeDialogSliderViewModel$state$2.L$0 = (VolumeDialogStreamModel) obj2;
        volumeDialogSliderViewModel$state$2.L$1 = (Icon.Loaded) obj3;
        return volumeDialogSliderViewModel$state$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Resources.NotFoundException {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) this.L$0;
        Icon.Loaded loaded = (Icon.Loaded) this.L$1;
        Context context = this.this$0.context;
        float f = volumeDialogStreamModel.level;
        ClosedFloatRange closedFloatRange = new ClosedFloatRange(volumeDialogStreamModel.levelMin, volumeDialogStreamModel.levelMax);
        String string = volumeDialogStreamModel.remoteLabel;
        if (string == null) {
            string = context.getResources().getString(volumeDialogStreamModel.name);
        }
        return new VolumeDialogSliderStateModel(f, z, closedFloatRange, loaded, string);
    }
}
