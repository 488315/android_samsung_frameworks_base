package com.android.systemui.volume.dialog.ui.viewmodel;

import android.content.DialogInterface;
import android.os.Trace;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.dialog.VolumeDialog;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.ui.VolumeDialogUiEvent;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogPluginViewModel$launchVolumeDialog$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogPluginViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogPluginViewModel$launchVolumeDialog$1(VolumeDialogPluginViewModel volumeDialogPluginViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogPluginViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogPluginViewModel$launchVolumeDialog$1 volumeDialogPluginViewModel$launchVolumeDialog$1 = new VolumeDialogPluginViewModel$launchVolumeDialog$1(this.this$0, continuation);
        volumeDialogPluginViewModel$launchVolumeDialog$1.L$0 = obj;
        return volumeDialogPluginViewModel$launchVolumeDialog$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogPluginViewModel$launchVolumeDialog$1) create((VolumeDialogVisibilityModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VolumeDialogVisibilityModel volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) this.L$0;
        final VolumeDialogPluginViewModel volumeDialogPluginViewModel = this.this$0;
        if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Visible) {
            VolumeDialog volumeDialog = (VolumeDialog) volumeDialogPluginViewModel.volumeDialogProvider.get();
            volumeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogPluginViewModel$showDialog$1$1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    Object value;
                    VolumeDialogVisibilityModel volumeDialogVisibilityModel2;
                    VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = VolumeDialogPluginViewModel.this.dialogVisibilityInteractor;
                    StateFlowImpl stateFlowImpl = volumeDialogVisibilityInteractor.repository.mutableDialogVisibility;
                    do {
                        value = stateFlowImpl.getValue();
                        volumeDialogVisibilityModel2 = (VolumeDialogVisibilityModel) value;
                        VolumeDialogVisibilityModel.Dismissed dismissed = new VolumeDialogVisibilityModel.Dismissed(0);
                        if (volumeDialogVisibilityModel2.getClass() != VolumeDialogVisibilityModel.Dismissed.class) {
                            ((VolumeTracerImpl) volumeDialogVisibilityInteractor.tracer).getClass();
                            Trace.beginAsyncSection(VolumeTracerImpl.getMethodName(dismissed), dismissed.hashCode());
                            volumeDialogVisibilityModel2 = dismissed;
                        }
                    } while (!stateFlowImpl.compareAndSet(value, volumeDialogVisibilityModel2));
                }
            });
            volumeDialog.show();
            VolumeDialogVisibilityModel.Visible visible = (VolumeDialogVisibilityModel.Visible) volumeDialogVisibilityModel;
            int i = visible.reason;
            VolumeDialogUiEvent volumeDialogUiEvent = i != 1 ? i != 2 ? i != 3 ? null : VolumeDialogUiEvent.VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED : VolumeDialogUiEvent.VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED : VolumeDialogUiEvent.VOLUME_DIALOG_SHOW_VOLUME_CHANGED;
            if (volumeDialogUiEvent != null) {
                volumeDialogPluginViewModel.uiEventLogger.log(volumeDialogUiEvent);
            }
            int i2 = visible.reason;
            VolumeDialogLogger volumeDialogLogger = volumeDialogPluginViewModel.logger;
            volumeDialogLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer = volumeDialogLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).int1 = i2;
            logBuffer.commit(obtain);
        }
        if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Dismissed) {
            VolumeDialogVisibilityModel.Dismissed dismissed = (VolumeDialogVisibilityModel.Dismissed) volumeDialogVisibilityModel;
            int i3 = dismissed.reason;
            VolumeDialogUiEvent volumeDialogUiEvent2 = i3 != 1 ? i3 != 2 ? i3 != 3 ? i3 != 4 ? i3 != 5 ? i3 != 7 ? i3 != 9 ? null : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_STREAM_GONE : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_SETTINGS : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_SCREEN_OFF : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_TIMEOUT : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_SYSTEM : VolumeDialogUiEvent.VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE;
            if (volumeDialogUiEvent2 != null) {
                volumeDialogPluginViewModel.uiEventLogger.log(volumeDialogUiEvent2);
            }
            VolumeDialogLogger volumeDialogLogger2 = volumeDialogPluginViewModel.logger;
            int i4 = dismissed.reason;
            volumeDialogLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda02 = new VolumeDialogLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer2 = volumeDialogLogger2.logBuffer;
            LogMessage obtain2 = logBuffer2.obtain("SysUI_VolumeDialog", logLevel2, volumeDialogLogger$$ExternalSyntheticLambda02, null);
            ((LogMessageImpl) obtain2).int1 = i4;
            logBuffer2.commit(obtain2);
        }
        return Unit.INSTANCE;
    }
}
