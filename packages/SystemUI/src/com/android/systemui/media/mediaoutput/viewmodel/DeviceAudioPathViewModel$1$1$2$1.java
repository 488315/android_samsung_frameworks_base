package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class DeviceAudioPathViewModel$1$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SmartThingsMediaSdkManager $serviceClient;
    int label;
    final /* synthetic */ DeviceAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceAudioPathViewModel$1$1$2$1(SmartThingsMediaSdkManager smartThingsMediaSdkManager, DeviceAudioPathViewModel deviceAudioPathViewModel, Continuation continuation) {
        super(2, continuation);
        this.$serviceClient = smartThingsMediaSdkManager;
        this.this$0 = deviceAudioPathViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceAudioPathViewModel$1$1$2$1(this.$serviceClient, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceAudioPathViewModel$1$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DeviceAudioPathViewModel.Companion companion = DeviceAudioPathViewModel.Companion;
            SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.$serviceClient;
            companion.getClass();
            final Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new DeviceAudioPathViewModel$Companion$mediaoutputChanged$1(smartThingsMediaSdkManager, null)), -1, 2);
            final DeviceAudioPathViewModel deviceAudioPathViewModel = this.this$0;
            Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$1$1$2$1$invokeSuspend$$inlined$filter$1

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$1$1$2$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ DeviceAudioPathViewModel this$0;

                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$1$1$2$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        Object L$1;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, DeviceAudioPathViewModel deviceAudioPathViewModel) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = deviceAudioPathViewModel;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            if (Intrinsics.areEqual((String) obj, this.this$0.deviceId)) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = flowBuffer$default.collect(new AnonymousClass2(flowCollector, deviceAudioPathViewModel), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            final DeviceAudioPathViewModel deviceAudioPathViewModel2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$1$1$2$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object objAccess$updateDevices = DeviceAudioPathViewModel.access$updateDevices(deviceAudioPathViewModel2, continuation);
                    return objAccess$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$updateDevices : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
