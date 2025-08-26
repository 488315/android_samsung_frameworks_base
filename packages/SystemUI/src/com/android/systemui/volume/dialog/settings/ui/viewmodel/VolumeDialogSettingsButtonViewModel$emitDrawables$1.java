package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewModel$emitDrawables$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ VolumeDialogSettingsButtonViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSettingsButtonViewModel$emitDrawables$1(VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = volumeDialogSettingsButtonViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return VolumeDialogSettingsButtonViewModel.access$emitDrawables(this.this$0, null, null, this);
    }
}
