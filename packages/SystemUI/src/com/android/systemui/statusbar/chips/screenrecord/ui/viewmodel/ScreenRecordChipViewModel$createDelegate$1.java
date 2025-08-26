package com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class ScreenRecordChipViewModel$createDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public ScreenRecordChipViewModel$createDelegate$1(Object obj) {
        super(0, obj, ScreenRecordChipViewModel.class, "stopRecordingFromDialog", "stopRecordingFromDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ScreenRecordChipViewModel screenRecordChipViewModel = (ScreenRecordChipViewModel) this.receiver;
        ScreenRecordChipViewModel.Companion companion = ScreenRecordChipViewModel.Companion;
        screenRecordChipViewModel.getClass();
        LogLevel logLevel = LogLevel.INFO;
        ScreenRecordChipViewModel$$ExternalSyntheticLambda0 screenRecordChipViewModel$$ExternalSyntheticLambda0 = new ScreenRecordChipViewModel$$ExternalSyntheticLambda0();
        String str = ScreenRecordChipViewModel.TAG;
        LogBuffer logBuffer = screenRecordChipViewModel.logger;
        logBuffer.commit(logBuffer.obtain(str, logLevel, screenRecordChipViewModel$$ExternalSyntheticLambda0, null));
        screenRecordChipViewModel.chipTransitionHelper.onActivityStoppedFromDialog();
        screenRecordChipViewModel.shareToAppChipViewModel.chipTransitionHelper.onActivityStoppedFromDialog();
        screenRecordChipViewModel.interactor.stopRecording();
        return Unit.INSTANCE;
    }
}
