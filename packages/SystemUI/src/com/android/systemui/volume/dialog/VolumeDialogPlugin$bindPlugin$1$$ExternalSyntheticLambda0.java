package com.android.systemui.volume.dialog;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogPluginViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogPlugin$bindPlugin$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogPluginViewModel f$0;

    public /* synthetic */ VolumeDialogPlugin$bindPlugin$1$$ExternalSyntheticLambda0(VolumeDialogPluginViewModel volumeDialogPluginViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogPluginViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.dialogSafetyWarningInteractor.stateInteractor.setSafetyWarning(VolumeDialogSafetyWarningModel.Invisible.INSTANCE);
                break;
            default:
                this.f$0.dialogCsdWarningInteractor.stateInteractor.setCsdWarning(VolumeDialogCsdWarningModel.Invisible.INSTANCE);
                break;
        }
        return Unit.INSTANCE;
    }
}
