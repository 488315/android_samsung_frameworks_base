package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class SpatialAudioComponentInteractor$getAudioDeviceAttributes$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SpatialAudioComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpatialAudioComponentInteractor$getAudioDeviceAttributes$1(SpatialAudioComponentInteractor spatialAudioComponentInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = spatialAudioComponentInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return SpatialAudioComponentInteractor.access$getAudioDeviceAttributes(this.this$0, null, this);
    }
}
