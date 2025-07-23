package com.android.systemui.volume.dialog.ui.viewmodel;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogCsdWarningInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.model.CsdWarningConfigModel;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogPluginViewModel {
    public final CoroutineScope coroutineScope;
    public final ChannelFlowTransformLatest csdWarning;
    public final CsdWarningConfigModel csdWarningConfigModel;
    public final VolumeDialogCsdWarningInteractor dialogCsdWarningInteractor;
    public final VolumeDialogSafetyWarningInteractor dialogSafetyWarningInteractor;
    public final VolumeDialogVisibilityInteractor dialogVisibilityInteractor;
    public final VolumeDialogSafetyWarningInteractor$special$$inlined$map$1 isShowingSafetyWarning;
    public final VolumeDialogLogger logger;
    public final UiEventLogger uiEventLogger;
    public final Provider volumeDialogProvider;

    public VolumeDialogPluginViewModel(CoroutineScope coroutineScope, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, VolumeDialogSafetyWarningInteractor volumeDialogSafetyWarningInteractor, VolumeDialogCsdWarningInteractor volumeDialogCsdWarningInteractor, Provider provider, VolumeDialogLogger volumeDialogLogger, CsdWarningConfigModel csdWarningConfigModel, UiEventLogger uiEventLogger) {
        this.coroutineScope = coroutineScope;
        this.dialogVisibilityInteractor = volumeDialogVisibilityInteractor;
        this.dialogSafetyWarningInteractor = volumeDialogSafetyWarningInteractor;
        this.dialogCsdWarningInteractor = volumeDialogCsdWarningInteractor;
        this.volumeDialogProvider = provider;
        this.logger = volumeDialogLogger;
        this.csdWarningConfigModel = csdWarningConfigModel;
        this.uiEventLogger = uiEventLogger;
        this.isShowingSafetyWarning = volumeDialogSafetyWarningInteractor.isShowingSafetyWarning;
        this.csdWarning = volumeDialogCsdWarningInteractor.csdWarning;
    }

    public final void launchVolumeDialog() {
        FlowKt.launchIn(FlowKt.mapLatest(this.dialogVisibilityInteractor.dialogVisibility, new VolumeDialogPluginViewModel$launchVolumeDialog$1(this, null)), this.coroutineScope);
    }
}
