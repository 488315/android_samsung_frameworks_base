package com.android.systemui.volume.dialog.settings.ui.binder;

import android.os.Trace;
import android.view.View;
import android.widget.ImageButton;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor;
import com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.ui.VolumeDialogUiEvent;
import com.android.systemui.volume.dialog.ui.binder.ViewBinder;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSettingsButtonViewBinder implements ViewBinder {
    public final VolumeDialogViewModel dialogViewModel;
    public final VolumeDialogSettingsButtonViewModel viewModel;

    public VolumeDialogSettingsButtonViewBinder(VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel, VolumeDialogViewModel volumeDialogViewModel) {
        this.viewModel = volumeDialogSettingsButtonViewModel;
        this.dialogViewModel = volumeDialogViewModel;
    }

    @Override // com.android.systemui.volume.dialog.ui.binder.ViewBinder
    public final void bind(CoroutineScope coroutineScope, View view) {
        ImageButton imageButton = (ImageButton) view.requireViewById(R.id.volume_dialog_settings);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new VolumeDialogSettingsButtonViewBinder$bind$1(this, imageButton, null), 6);
        VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel = this.viewModel;
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogSettingsButtonViewModel.isVisible, new VolumeDialogSettingsButtonViewBinder$bind$2(imageButton, null)), coroutineScope);
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogSettingsButtonViewModel.icon, new VolumeDialogSettingsButtonViewBinder$bind$3(imageButton, null)), coroutineScope);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.dialog.settings.ui.binder.VolumeDialogSettingsButtonViewBinder$bind$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                Object value;
                VolumeDialogVisibilityModel volumeDialogVisibilityModel;
                VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel2 = VolumeDialogSettingsButtonViewBinder.this.viewModel;
                VolumeDialogSettingsButtonInteractor volumeDialogSettingsButtonInteractor = volumeDialogSettingsButtonViewModel2.interactor;
                volumeDialogSettingsButtonInteractor.volumePanelGlobalStateInteractor.setVisible(true);
                VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = volumeDialogSettingsButtonInteractor.visibilityInteractor;
                StateFlowImpl stateFlowImpl = volumeDialogVisibilityInteractor.repository.mutableDialogVisibility;
                do {
                    value = stateFlowImpl.getValue();
                    volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) value;
                    VolumeDialogVisibilityModel.Dismissed dismissed = new VolumeDialogVisibilityModel.Dismissed(5);
                    if (volumeDialogVisibilityModel.getClass() != VolumeDialogVisibilityModel.Dismissed.class) {
                        ((VolumeTracerImpl) volumeDialogVisibilityInteractor.tracer).getClass();
                        Trace.beginAsyncSection(VolumeTracerImpl.getMethodName(dismissed), dismissed.hashCode());
                        volumeDialogVisibilityModel = dismissed;
                    }
                } while (!stateFlowImpl.compareAndSet(value, volumeDialogVisibilityModel));
                volumeDialogSettingsButtonViewModel2.uiEventLogger.log(VolumeDialogUiEvent.VOLUME_DIALOG_SETTINGS_CLICK);
            }
        });
    }
}
