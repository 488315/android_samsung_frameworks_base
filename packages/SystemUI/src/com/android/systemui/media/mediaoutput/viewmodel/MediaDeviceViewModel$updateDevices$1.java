package com.android.systemui.media.mediaoutput.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class MediaDeviceViewModel$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaDeviceViewModel this$0;

    public MediaDeviceViewModel$updateDevices$1(MediaDeviceViewModel mediaDeviceViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaDeviceViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MediaDeviceViewModel.access$updateDevices(this.this$0, this);
    }
}
