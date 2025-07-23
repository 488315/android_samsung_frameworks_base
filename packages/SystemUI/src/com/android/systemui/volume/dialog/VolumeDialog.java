package com.android.systemui.volume.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.ComponentDialog;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.volume.dialog.dagger.factory.VolumeDialogComponentFactory;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialog extends ComponentDialog {
    public final VolumeDialogComponentFactory componentFactory;
    public final VolumeDialogVisibilityInteractor visibilityInteractor;

    public VolumeDialog(Context context, VolumeDialogComponentFactory volumeDialogComponentFactory, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
        super(context, R.style.Theme_SystemUI_Dialog_Volume);
        this.componentFactory = volumeDialogComponentFactory;
        this.visibilityInteractor = volumeDialogVisibilityInteractor;
        Window window = getWindow();
        window.getClass();
        window.addFlags(android.R.string.config_systemAutomotiveCluster);
        window.addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        window.setType(2020);
        window.setWindowAnimations(-1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.setTitle("VolumeDialog");
        window.setAttributes(attributes);
        window.setLayout(-2, -1);
        window.setGravity(8388613);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.volume_dialog);
        RepeatWhenAttachedKt.repeatWhenAttached(requireViewById(R.id.volume_dialog), EmptyCoroutineContext.INSTANCE, new VolumeDialog$onCreate$1(this, null));
    }

    @Override // android.app.Dialog
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Object value;
        VolumeDialogVisibilityModel volumeDialogVisibilityModel;
        if (!isShowing() || motionEvent.getAction() != 4) {
            return false;
        }
        VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = this.visibilityInteractor;
        StateFlowImpl stateFlowImpl = volumeDialogVisibilityInteractor.repository.mutableDialogVisibility;
        do {
            value = stateFlowImpl.getValue();
            volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) value;
            VolumeDialogVisibilityModel.Dismissed dismissed = new VolumeDialogVisibilityModel.Dismissed(1);
            if (volumeDialogVisibilityModel.getClass() != VolumeDialogVisibilityModel.Dismissed.class) {
                ((VolumeTracerImpl) volumeDialogVisibilityInteractor.tracer).getClass();
                Trace.beginAsyncSection(VolumeTracerImpl.getMethodName(dismissed), dismissed.hashCode());
                volumeDialogVisibilityModel = dismissed;
            }
        } while (!stateFlowImpl.compareAndSet(value, volumeDialogVisibilityModel));
        return true;
    }
}
