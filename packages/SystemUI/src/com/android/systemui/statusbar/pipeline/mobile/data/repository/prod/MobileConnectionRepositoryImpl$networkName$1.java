package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModelKt;
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
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MobileConnectionRepositoryImpl$networkName$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NetworkNameModel $defaultNetworkName;
    final /* synthetic */ MobileInputLogger $logger;
    final /* synthetic */ String $networkNameSeparator;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$networkName$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, MobileInputLogger mobileInputLogger, String str, NetworkNameModel networkNameModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryImpl;
        this.$logger = mobileInputLogger;
        this.$networkNameSeparator = str;
        this.$defaultNetworkName = networkNameModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$networkName$1 mobileConnectionRepositoryImpl$networkName$1 = new MobileConnectionRepositoryImpl$networkName$1(this.this$0, this.$logger, this.$networkNameSeparator, this.$defaultNetworkName, continuation);
        mobileConnectionRepositoryImpl$networkName$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$networkName$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$networkName$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$networkName$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final String str = this.$networkNameSeparator;
            final NetworkNameModel networkNameModel = this.$defaultNetworkName;
            final ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$networkName$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1) == MobileConnectionRepositoryImpl.this.subId) {
                        mobileInputLogger.logServiceProvidersUpdatedBroadcast(intent);
                        SendChannel sendChannel = producerScope;
                        Object networkNameModel2 = NetworkNameModelKt.toNetworkNameModel(intent, str);
                        if (networkNameModel2 == null) {
                            networkNameModel2 = networkNameModel;
                        }
                        ((ChannelCoroutine) sendChannel).mo2552trySendJP2dKIU(networkNameModel2);
                    }
                }
            };
            this.this$0.context.registerReceiver(r1, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"));
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$networkName$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionRepositoryImpl.this.context.unregisterReceiver(r1);
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
