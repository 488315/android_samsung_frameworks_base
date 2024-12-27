package com.android.systemui.media.mediaoutput.controller.device;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class SmartMirroringClient$connectionFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SmartMirroringClient this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMirroringClient$connectionFlow$1(SmartMirroringClient smartMirroringClient, Continuation continuation) {
        super(2, continuation);
        this.this$0 = smartMirroringClient;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SmartMirroringClient$connectionFlow$1 smartMirroringClient$connectionFlow$1 = new SmartMirroringClient$connectionFlow$1(this.this$0, continuation);
        smartMirroringClient$connectionFlow$1.L$0 = obj;
        return smartMirroringClient$connectionFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartMirroringClient$connectionFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [T, com.android.systemui.media.mediaoutput.controller.device.SmartMirroringClient$connectionFlow$1$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            final SmartMirroringClient smartMirroringClient = this.this$0;
            ref$ObjectRef.element = new ServiceConnection() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringClient$connectionFlow$1.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Object failure;
                    Log.d("SmartMirroringClient", "onServiceConnected()");
                    ServiceConnection serviceConnection = null;
                    if (!Ref$BooleanRef.this.element) {
                        smartMirroringClient.service = new Messenger(iBinder);
                        ProducerScope producerScope2 = producerScope;
                        BuildersKt.launch$default(producerScope2, null, null, new SmartMirroringClient$connectionFlow$1$1$onServiceConnected$3(producerScope2, null), 3);
                        return;
                    }
                    Context context = smartMirroringClient.context;
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    try {
                        int i2 = Result.$r8$clinit;
                        T t = ref$ObjectRef2.element;
                        if (t != 0) {
                            serviceConnection = (ServiceConnection) t;
                        }
                        context.unbindService(serviceConnection);
                        failure = Unit.INSTANCE;
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                    if (m2527exceptionOrNullimpl != null) {
                        m2527exceptionOrNullimpl.printStackTrace();
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Log.d("SmartMirroringClient", "onServiceDisconnected()");
                    Ref$BooleanRef.this.element = true;
                    smartMirroringClient.service = null;
                    ProducerScope producerScope2 = producerScope;
                    BuildersKt.launch$default(producerScope2, null, null, new SmartMirroringClient$connectionFlow$1$1$onServiceDisconnected$1(producerScope2, null), 3);
                }
            };
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.samsung.android.smartmirroring", "com.samsung.android.smartmirroring.SmartMirroringService"));
            Context context = this.this$0.context;
            T t = ref$ObjectRef.element;
            context.bindService(intent, t == 0 ? null : (ServiceConnection) t, 1);
            final SmartMirroringClient smartMirroringClient2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringClient$connectionFlow$1.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object failure;
                    Log.d("SmartMirroringClient", "unbindService()");
                    Ref$BooleanRef.this.element = true;
                    Context context2 = smartMirroringClient2.context;
                    Ref$ObjectRef<ServiceConnection> ref$ObjectRef2 = ref$ObjectRef;
                    try {
                        int i2 = Result.$r8$clinit;
                        ServiceConnection serviceConnection = ref$ObjectRef2.element;
                        context2.unbindService(serviceConnection == null ? null : serviceConnection);
                        failure = Unit.INSTANCE;
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                    if (m2527exceptionOrNullimpl != null) {
                        m2527exceptionOrNullimpl.printStackTrace();
                    }
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
