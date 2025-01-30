package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository", m277f = "CarrierConfigRepository.kt", m278l = {101}, m279m = "startObservingCarrierConfigUpdates")
/* loaded from: classes2.dex */
final class CarrierConfigRepository$startObservingCarrierConfigUpdates$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CarrierConfigRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierConfigRepository$startObservingCarrierConfigUpdates$1(CarrierConfigRepository carrierConfigRepository, Continuation<? super CarrierConfigRepository$startObservingCarrierConfigUpdates$1> continuation) {
        super(continuation);
        this.this$0 = carrierConfigRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.startObservingCarrierConfigUpdates(this);
    }
}
