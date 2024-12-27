package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaDeviceViewModel$2$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SmartThingsMediaSdkManager $service;
    int label;
    final /* synthetic */ MediaDeviceViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDeviceViewModel$2$1$2$1(SmartThingsMediaSdkManager smartThingsMediaSdkManager, MediaDeviceViewModel mediaDeviceViewModel, Continuation continuation) {
        super(2, continuation);
        this.$service = smartThingsMediaSdkManager;
        this.this$0 = mediaDeviceViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaDeviceViewModel$2$1$2$1(this.$service, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDeviceViewModel$2$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MediaDeviceViewModel.Companion companion = MediaDeviceViewModel.Companion;
            SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.$service;
            companion.getClass();
            Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MediaDeviceViewModel$Companion$mediaChanged$1(smartThingsMediaSdkManager, null)), -1);
            final MediaDeviceViewModel mediaDeviceViewModel = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$2$1$2$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object access$updateDevices = MediaDeviceViewModel.access$updateDevices(MediaDeviceViewModel.this, continuation);
                    return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (buffer$default.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
