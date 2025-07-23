package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.mediarouter.data.repository.MediaRouterRepositoryImpl;
import com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastDevice;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class CastToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public CastToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1(Object obj) {
        super(0, obj, CastToOtherDeviceChipViewModel.class, "stopMediaRouterCastingFromDialog", "stopMediaRouterCastingFromDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel = (CastToOtherDeviceChipViewModel) this.receiver;
        CastToOtherDeviceChipViewModel.Companion companion = CastToOtherDeviceChipViewModel.Companion;
        castToOtherDeviceChipViewModel.getClass();
        LogLevel logLevel = LogLevel.INFO;
        CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0 castToOtherDeviceChipViewModel$$ExternalSyntheticLambda0 = new CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0(0);
        String str = CastToOtherDeviceChipViewModel.TAG;
        LogBuffer logBuffer = castToOtherDeviceChipViewModel.logger;
        logBuffer.commit(logBuffer.obtain(str, logLevel, castToOtherDeviceChipViewModel$$ExternalSyntheticLambda0, null));
        castToOtherDeviceChipViewModel.hideChipDuringDialogTransitionHelper.onActivityStoppedFromDialog();
        MediaRouterChipInteractor mediaRouterChipInteractor = castToOtherDeviceChipViewModel.mediaRouterChipInteractor;
        CastDevice castDevice = (CastDevice) mediaRouterChipInteractor.activeCastDevice.$$delegate_0.getValue();
        if (castDevice != null) {
            ((CastControllerImpl) ((MediaRouterRepositoryImpl) mediaRouterChipInteractor.mediaRouterRepository).castController).stopCasting(castDevice, 4);
        }
        return Unit.INSTANCE;
    }
}
