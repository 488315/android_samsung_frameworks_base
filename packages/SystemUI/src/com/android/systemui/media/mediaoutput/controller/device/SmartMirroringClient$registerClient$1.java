package com.android.systemui.media.mediaoutput.controller.device;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.systemui.media.mediaoutput.controller.device.SmartMirroringClient;
import com.google.gson.Gson;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SmartMirroringClient$registerClient$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SmartMirroringClient this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMirroringClient$registerClient$1(SmartMirroringClient smartMirroringClient, Continuation continuation) {
        super(2, continuation);
        this.this$0 = smartMirroringClient;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SmartMirroringClient$registerClient$1 smartMirroringClient$registerClient$1 = new SmartMirroringClient$registerClient$1(this.this$0, continuation);
        smartMirroringClient$registerClient$1.L$0 = obj;
        return smartMirroringClient$registerClient$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartMirroringClient$registerClient$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final LinkedHashMap linkedHashMap = new LinkedHashMap();
            final Looper mainLooper = Looper.getMainLooper();
            final Messenger messenger = new Messenger(new Handler(mainLooper) { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringClient$registerClient$1$clientMessenger$1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    DeviceInfo deviceInfo;
                    DeviceInfo deviceInfo2;
                    DeviceInfo deviceInfo3;
                    switch (message.what) {
                        case 1:
                            String string = message.getData().getString("deviceInfo");
                            if (string == null || (deviceInfo = (DeviceInfo) new Gson().fromJson(DeviceInfo.class, string)) == null) {
                                deviceInfo = null;
                            } else {
                                linkedHashMap.put(deviceInfo.key, deviceInfo);
                            }
                            Objects.toString(deviceInfo);
                            break;
                        case 2:
                            String string2 = message.getData().getString("deviceInfo");
                            if (string2 == null || (deviceInfo2 = (DeviceInfo) new Gson().fromJson(DeviceInfo.class, string2)) == null) {
                                deviceInfo2 = null;
                            } else {
                                linkedHashMap.remove(deviceInfo2.key);
                            }
                            Objects.toString(deviceInfo2);
                            break;
                        case 3:
                            String string3 = message.getData().getString("deviceInfo");
                            if (string3 == null || (deviceInfo3 = (DeviceInfo) new Gson().fromJson(DeviceInfo.class, string3)) == null) {
                                deviceInfo3 = null;
                            } else {
                                linkedHashMap.put(deviceInfo3.key, deviceInfo3);
                            }
                            Objects.toString(deviceInfo3);
                            break;
                        case 4:
                            SmartMirroringClient.Response.Companion companion = SmartMirroringClient.Response.Companion;
                            Bundle data = message.getData();
                            companion.getClass();
                            Log.d("SmartMirroringClient", "\tRSP_SCAN : " + SmartMirroringClient.Response.Companion.getParseResponse(data));
                            break;
                        case 5:
                            SmartMirroringClient.Response.Companion companion2 = SmartMirroringClient.Response.Companion;
                            Bundle data2 = message.getData();
                            companion2.getClass();
                            Log.d("SmartMirroringClient", "\tRSP_CONNECT : " + SmartMirroringClient.Response.Companion.getParseResponse(data2));
                            break;
                        case 6:
                            SmartMirroringClient.Response.Companion companion3 = SmartMirroringClient.Response.Companion;
                            Bundle data3 = message.getData();
                            companion3.getClass();
                            Log.d("SmartMirroringClient", "\tRSP_DISCONNECT : " + SmartMirroringClient.Response.Companion.getParseResponse(data3));
                            break;
                    }
                    Map map = linkedHashMap;
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new SmartMirroringClient$registerClient$1$clientMessenger$1$handleMessage$10(producerScope2, map, null), 3);
                }
            });
            Message obtain = Message.obtain((Handler) null, 1);
            obtain.replyTo = messenger;
            Messenger messenger2 = this.this$0.service;
            if (messenger2 != null) {
                try {
                    int i2 = Result.$r8$clinit;
                    messenger2.send(obtain);
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                Result.m2526boximpl(failure);
            }
            final SmartMirroringClient smartMirroringClient = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringClient$registerClient$1.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object failure2;
                    Log.d("SmartMirroringClient", "unregister client");
                    Message obtain2 = Message.obtain((Handler) null, 2);
                    obtain2.replyTo = messenger;
                    Messenger messenger3 = smartMirroringClient.service;
                    if (messenger3 != null) {
                        try {
                            int i4 = Result.$r8$clinit;
                            messenger3.send(obtain2);
                            failure2 = Unit.INSTANCE;
                        } catch (Throwable th2) {
                            int i5 = Result.$r8$clinit;
                            failure2 = new Result.Failure(th2);
                        }
                        Throwable m2527exceptionOrNullimpl2 = Result.m2527exceptionOrNullimpl(failure2);
                        if (m2527exceptionOrNullimpl2 != null) {
                            m2527exceptionOrNullimpl2.printStackTrace();
                        }
                        Result.m2526boximpl(failure2);
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
