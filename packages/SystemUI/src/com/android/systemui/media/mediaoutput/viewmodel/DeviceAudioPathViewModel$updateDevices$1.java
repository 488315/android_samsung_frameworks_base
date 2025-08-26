package com.android.systemui.media.mediaoutput.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class DeviceAudioPathViewModel$updateDevices$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DeviceAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceAudioPathViewModel$updateDevices$1(DeviceAudioPathViewModel deviceAudioPathViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = deviceAudioPathViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DeviceAudioPathViewModel.access$updateDevices(this.this$0, this);
    }
}
