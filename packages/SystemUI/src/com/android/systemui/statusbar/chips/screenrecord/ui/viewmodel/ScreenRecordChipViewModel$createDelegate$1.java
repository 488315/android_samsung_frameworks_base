package com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel;

import com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class ScreenRecordChipViewModel$createDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public ScreenRecordChipViewModel$createDelegate$1(Object obj) {
        super(0, obj, ScreenRecordChipInteractor.class, "stopRecording", "stopRecording()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((ScreenRecordChipInteractor) this.receiver).stopRecording();
        return Unit.INSTANCE;
    }
}
