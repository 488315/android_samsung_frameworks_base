package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.kairos.internal.BuildScopeImpl$coalescingEvents$1$1;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModelKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class MobileConnectionRepositoryKairosImpl$networkName$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NetworkNameModel $defaultNetworkName;
    final /* synthetic */ MobileInputLogger $logger;
    final /* synthetic */ String $networkNameSeparator;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryKairosImpl$networkName$1$1(MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, MobileInputLogger mobileInputLogger, String str, NetworkNameModel networkNameModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryKairosImpl;
        this.$logger = mobileInputLogger;
        this.$networkNameSeparator = str;
        this.$defaultNetworkName = networkNameModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryKairosImpl$networkName$1$1 mobileConnectionRepositoryKairosImpl$networkName$1$1 = new MobileConnectionRepositoryKairosImpl$networkName$1$1(this.this$0, this.$logger, this.$networkNameSeparator, this.$defaultNetworkName, continuation);
        mobileConnectionRepositoryKairosImpl$networkName$1$1.L$0 = obj;
        return mobileConnectionRepositoryKairosImpl$networkName$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryKairosImpl$networkName$1$1) create((CoalescingEventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final CoalescingEventProducerScope coalescingEventProducerScope = (CoalescingEventProducerScope) this.L$0;
            final MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.this$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final String str = this.$networkNameSeparator;
            final NetworkNameModel networkNameModel = this.$defaultNetworkName;
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$networkName$1$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1) == mobileConnectionRepositoryKairosImpl.subId) {
                        mobileInputLogger.logServiceProvidersUpdatedBroadcast(intent);
                        CoalescingEventProducerScope coalescingEventProducerScope2 = coalescingEventProducerScope;
                        Object networkNameModel2 = NetworkNameModelKt.toNetworkNameModel(intent, str);
                        if (networkNameModel2 == null) {
                            networkNameModel2 = networkNameModel;
                        }
                        ((BuildScopeImpl$coalescingEvents$1$1) coalescingEventProducerScope2).emit(networkNameModel2);
                    }
                }
            };
            this.this$0.context.registerReceiver(broadcastReceiver, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"));
            MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 mobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 = new MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0(0, this.this$0, broadcastReceiver);
            this.label = 1;
            if (BuildScopeKt.awaitClose(mobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
