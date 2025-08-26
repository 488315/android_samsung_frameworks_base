package com.android.systemui.volume.dialog.ui.viewmodel;

import android.content.DialogInterface;
import android.os.Trace;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.dialog.VolumeDialog;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogCsdWarningInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.dialog.shared.model.CsdWarningConfigModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.ui.VolumeDialogUiEvent;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

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

    /* renamed from: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogPluginViewModel$launchVolumeDialog$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = VolumeDialogPluginViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((VolumeDialogVisibilityModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            VolumeDialogVisibilityModel volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) this.L$0;
            final VolumeDialogPluginViewModel volumeDialogPluginViewModel = VolumeDialogPluginViewModel.this;
            if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Visible) {
                VolumeDialog volumeDialog = (VolumeDialog) volumeDialogPluginViewModel.volumeDialogProvider.get();
                volumeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogPluginViewModel$showDialog$1$1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        Object value;
                        VolumeDialogVisibilityModel volumeDialogVisibilityModel2;
                        VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = volumeDialogPluginViewModel.dialogVisibilityInteractor;
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
                LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).int1 = i2;
                logBuffer.commit(logMessageObtain);
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
                LogMessage logMessageObtain2 = logBuffer2.obtain("SysUI_VolumeDialog", logLevel2, volumeDialogLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) logMessageObtain2).int1 = i4;
                logBuffer2.commit(logMessageObtain2);
            }
            return Unit.INSTANCE;
        }
    }

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
        FlowKt.launchIn(FlowKt.mapLatest(this.dialogVisibilityInteractor.dialogVisibility, new AnonymousClass1(null)), this.coroutineScope);
    }
}
