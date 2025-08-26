package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class CastToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public CastToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1(Object obj) {
        super(0, obj, CastToOtherDeviceChipViewModel.class, "stopProjectingFromDialog", "stopProjectingFromDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel = (CastToOtherDeviceChipViewModel) this.receiver;
        CastToOtherDeviceChipViewModel.Companion companion = CastToOtherDeviceChipViewModel.Companion;
        castToOtherDeviceChipViewModel.getClass();
        LogLevel logLevel = LogLevel.INFO;
        CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0 castToOtherDeviceChipViewModel$$ExternalSyntheticLambda0 = new CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0(1);
        String str = CastToOtherDeviceChipViewModel.TAG;
        LogBuffer logBuffer = castToOtherDeviceChipViewModel.logger;
        logBuffer.commit(logBuffer.obtain(str, logLevel, castToOtherDeviceChipViewModel$$ExternalSyntheticLambda0, null));
        castToOtherDeviceChipViewModel.hideChipDuringDialogTransitionHelper.onActivityStoppedFromDialog();
        castToOtherDeviceChipViewModel.mediaProjectionChipInteractor.stopProjecting();
        return Unit.INSTANCE;
    }
}
