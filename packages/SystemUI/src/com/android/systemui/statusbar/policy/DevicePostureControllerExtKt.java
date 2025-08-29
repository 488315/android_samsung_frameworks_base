package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public abstract class DevicePostureControllerExtKt {

    /* renamed from: com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ DevicePostureController $this_devicePosture;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DevicePostureController devicePostureController, Continuation continuation) {
            super(2, continuation);
            this.$this_devicePosture = devicePostureController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_devicePosture, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1$callback$1, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final ?? r1 = new DevicePostureController.Callback() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1$callback$1
                    @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
                    public final void onPostureChanged(int i2) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Integer.valueOf(i2));
                    }
                };
                ((DevicePostureControllerImpl) this.$this_devicePosture).addCallback(r1);
                final DevicePostureController devicePostureController = this.$this_devicePosture;
                Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((DevicePostureControllerImpl) devicePostureController).removeCallback(r1);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.statusbar.policy.DevicePostureControllerExtKt$devicePosture$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ DevicePostureController $this_devicePosture;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(DevicePostureController devicePostureController, Continuation continuation) {
            super(2, continuation);
            this.$this_devicePosture = devicePostureController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_devicePosture, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Integer num = new Integer(((DevicePostureControllerImpl) this.$this_devicePosture).getDevicePosture());
                this.label = 1;
                if (flowCollector.emit(num, this) == coroutineSingletons) {
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

    public static final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 devicePosture(DevicePostureController devicePostureController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(devicePostureController, null), FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(devicePostureController, null)));
    }
}
