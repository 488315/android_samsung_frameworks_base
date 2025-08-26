package com.android.systemui.volume.dialog.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class VolumeDialogVisibilityInteractor$computeTimeout$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ VolumeDialogVisibilityInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogVisibilityInteractor$computeTimeout$1(VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = volumeDialogVisibilityInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return VolumeDialogVisibilityInteractor.m3216access$computeTimeout5sfh64U(this.this$0, this);
    }
}
