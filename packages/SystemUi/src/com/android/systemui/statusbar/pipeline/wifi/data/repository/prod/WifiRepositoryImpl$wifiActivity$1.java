package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.net.wifi.WifiManager;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModelKt;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiActivity$1", m277f = "WifiRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_stopTcpDump}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiRepositoryImpl$wifiActivity$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WifiInputLogger $logger;
    final /* synthetic */ Executor $mainExecutor;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WifiRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiActivity$1(WifiRepositoryImpl wifiRepositoryImpl, Executor executor, WifiInputLogger wifiInputLogger, Continuation<? super WifiRepositoryImpl$wifiActivity$1> continuation) {
        super(2, continuation);
        this.this$0 = wifiRepositoryImpl;
        this.$mainExecutor = executor;
        this.$logger = wifiInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$wifiActivity$1 wifiRepositoryImpl$wifiActivity$1 = new WifiRepositoryImpl$wifiActivity$1(this.this$0, this.$mainExecutor, this.$logger, continuation);
        wifiRepositoryImpl$wifiActivity$1.L$0 = obj;
        return wifiRepositoryImpl$wifiActivity$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$wifiActivity$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final WifiInputLogger wifiInputLogger = this.$logger;
            final WifiManager.TrafficStateCallback trafficStateCallback = new WifiManager.TrafficStateCallback() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiActivity$1$callback$1
                public final void onStateChanged(int i2) {
                    WifiInputLogger wifiInputLogger2 = WifiInputLogger.this;
                    WifiRepositoryImpl.Companion.getClass();
                    wifiInputLogger2.logActivity(i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "INVALID" : "INOUT" : "OUT" : "IN" : PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE);
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(DataActivityModelKt.toWifiDataActivityModel(i2));
                }
            };
            this.this$0.wifiManager.registerTrafficStateCallback(this.$mainExecutor, trafficStateCallback);
            final WifiRepositoryImpl wifiRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiActivity$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    WifiRepositoryImpl.this.wifiManager.unregisterTrafficStateCallback(trafficStateCallback);
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
