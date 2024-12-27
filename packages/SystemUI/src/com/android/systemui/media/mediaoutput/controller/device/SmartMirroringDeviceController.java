package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.core.os.BundleKt;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.SmartMirroringDevice;
import com.google.gson.Gson;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class SmartMirroringDeviceController extends DeviceController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy smartMirroringClient$delegate;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SmartMirroringDeviceController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SmartMirroringDeviceController smartMirroringDeviceController = SmartMirroringDeviceController.this;
                int i2 = SmartMirroringDeviceController.$r8$clinit;
                Flow flow = smartMirroringDeviceController.getSmartMirroringClient().connectionFlow;
                final SmartMirroringDeviceController smartMirroringDeviceController2 = SmartMirroringDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.1.1

                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ SmartMirroringDeviceController this$0;

                        public AnonymousClass2(SmartMirroringDeviceController smartMirroringDeviceController) {
                            this.this$0 = smartMirroringDeviceController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.util.List r21, kotlin.coroutines.Continuation r22) {
                            /*
                                Method dump skipped, instructions count: 285
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.AnonymousClass1.C01471.AnonymousClass2.emit(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object failure;
                        ((Boolean) obj2).getClass();
                        int i3 = SmartMirroringDeviceController.$r8$clinit;
                        SmartMirroringDeviceController smartMirroringDeviceController3 = SmartMirroringDeviceController.this;
                        SmartMirroringClient smartMirroringClient = smartMirroringDeviceController3.getSmartMirroringClient();
                        smartMirroringClient.getClass();
                        Log.d("SmartMirroringClient", "scanMirroring()");
                        Messenger messenger = smartMirroringClient.service;
                        if (messenger != null) {
                            AudioDeviceInfo[] devices = smartMirroringClient.audioManager.getDevices(2);
                            int length = devices.length;
                            int i4 = 0;
                            while (true) {
                                if (i4 >= length) {
                                    break;
                                }
                                if (devices[i4].getType() == 25) {
                                    messenger = null;
                                    break;
                                }
                                i4++;
                            }
                            if (messenger != null) {
                                try {
                                    int i5 = Result.$r8$clinit;
                                    messenger.send(Message.obtain(null, 3, 0, 0));
                                    failure = Unit.INSTANCE;
                                } catch (Throwable th) {
                                    int i6 = Result.$r8$clinit;
                                    failure = new Result.Failure(th);
                                }
                                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                                if (m2527exceptionOrNullimpl != null) {
                                    m2527exceptionOrNullimpl.printStackTrace();
                                }
                                Result.m2526boximpl(failure);
                            }
                        }
                        SmartMirroringClient smartMirroringClient2 = smartMirroringDeviceController3.getSmartMirroringClient();
                        smartMirroringClient2.getClass();
                        final CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new SmartMirroringClient$registerClient$1(smartMirroringClient2, null));
                        Object collect = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1

                            /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
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

                                public AnonymousClass2(FlowCollector flowCollector) {
                                    this.$this_unsafeFlow = flowCollector;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                                    /*
                                        r6 = this;
                                        boolean r0 = r8 instanceof com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r8
                                        com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1$2$1
                                        r0.<init>(r8)
                                    L18:
                                        java.lang.Object r8 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        goto L65
                                    L27:
                                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                        r6.<init>(r7)
                                        throw r6
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        java.util.List r7 = (java.util.List) r7
                                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                                        java.util.Iterator r8 = r7.iterator()
                                    L3a:
                                        boolean r2 = r8.hasNext()
                                        if (r2 == 0) goto L5a
                                        java.lang.Object r2 = r8.next()
                                        com.android.systemui.media.mediaoutput.controller.device.DeviceInfo r2 = (com.android.systemui.media.mediaoutput.controller.device.DeviceInfo) r2
                                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                                        java.lang.String r5 = "\t"
                                        r4.<init>(r5)
                                        r4.append(r2)
                                        java.lang.String r2 = r4.toString()
                                        java.lang.String r4 = "SmartMirroringDeviceController"
                                        android.util.Log.d(r4, r2)
                                        goto L3a
                                    L5a:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                        java.lang.Object r6 = r6.emit(r7, r0)
                                        if (r6 != r1) goto L65
                                        return r1
                                    L65:
                                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                        return r6
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1$1$emit$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector2, Continuation continuation2) {
                                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation2);
                                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
                            }
                        }.collect(new AnonymousClass2(smartMirroringDeviceController3), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SmartMirroringDeviceController(final Context context, final AudioManager audioManager) {
        this.smartMirroringClient$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$smartMirroringClient$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SmartMirroringClient(context, audioManager);
            }
        });
        Log.d("SmartMirroringDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void cancel(AudioDevice audioDevice) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "SmartMirroringDeviceController");
        if (audioDevice instanceof SmartMirroringDevice) {
            SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "disconnect()");
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                try {
                    int i = Result.$r8$clinit;
                    messenger.send(Message.obtain(null, 6, 0, 0));
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                Result.m2526boximpl(failure);
            }
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        Object failure;
        super.close();
        Log.d("SmartMirroringDeviceController", "close()");
        SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
        smartMirroringClient.getClass();
        Log.d("SmartMirroringClient", "close()");
        Log.d("SmartMirroringClient", "stopScanMirroring()");
        Messenger messenger = smartMirroringClient.service;
        if (messenger != null) {
            try {
                int i = Result.$r8$clinit;
                messenger.send(Message.obtain(null, 4, 0, 0));
                failure = Unit.INSTANCE;
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            Result.m2526boximpl(failure);
        }
    }

    public final SmartMirroringClient getSmartMirroringClient() {
        return (SmartMirroringClient) this.smartMirroringClient$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "SmartMirroringDeviceController");
        if (audioDevice instanceof SmartMirroringDevice) {
            SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
            DeviceInfo deviceInfo = ((SmartMirroringDevice) audioDevice).deviceInfo;
            if (deviceInfo == null) {
                deviceInfo = null;
            }
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "connect() - " + deviceInfo);
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                try {
                    int i = Result.$r8$clinit;
                    Message obtain = Message.obtain(null, 5, 0, 0);
                    obtain.setData(BundleKt.bundleOf(new Pair("deviceInfo", new Gson().toJson(deviceInfo))));
                    messenger.send(obtain);
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                Result.m2526boximpl(failure);
            }
        }
    }
}
