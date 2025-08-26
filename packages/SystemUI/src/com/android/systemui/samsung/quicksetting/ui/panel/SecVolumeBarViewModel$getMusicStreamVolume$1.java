package com.android.systemui.samsung.quicksetting.ui.panel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class SecVolumeBarViewModel$getMusicStreamVolume$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SecVolumeBarViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecVolumeBarViewModel$getMusicStreamVolume$1(SecVolumeBarViewModel secVolumeBarViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = secVolumeBarViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return SecVolumeBarViewModel.access$getMusicStreamVolume(this.this$0, this);
    }
}
