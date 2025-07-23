package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController;
import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SmartMirroringDeviceController$2$2$2$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SmartMirroringDeviceController.AnonymousClass2.C02252.C02262 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMirroringDeviceController$2$2$2$emit$1(SmartMirroringDeviceController.AnonymousClass2.C02252.C02262 c02262, Continuation continuation) {
        super(continuation);
        this.this$0 = c02262;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((List) null, (Continuation) this);
    }
}
