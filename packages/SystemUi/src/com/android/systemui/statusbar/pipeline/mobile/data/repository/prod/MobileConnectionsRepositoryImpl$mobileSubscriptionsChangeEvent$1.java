package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.Uri;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import com.android.systemui.BasicRune;
import com.android.systemui.util.SettingsHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {201}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation<? super MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1> continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 = new MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1.L$0 = obj;
        return mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.telephony.SubscriptionManager$OnSubscriptionsChangedListener, com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
            final ?? r1 = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1$callback$1
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public final void onSubscriptionsChanged() {
                    MobileConnectionsRepositoryImpl.this.logger.logOnSubscriptionsChanged();
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = this.this$0;
            mobileConnectionsRepositoryImpl2.subscriptionManager.addOnSubscriptionsChangedListener(ExecutorsKt.asExecutor(mobileConnectionsRepositoryImpl2.bgDispatcher), r1);
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl3 = this.this$0;
            final SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1$changedCallback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl4 = MobileConnectionsRepositoryImpl.this;
                    mobileConnectionsRepositoryImpl4.logger.logSimSettingChanged(0, mobileConnectionsRepositoryImpl4.simCardInfoUtil.isSimSettingOn(0));
                    if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                        mobileConnectionsRepositoryImpl4.logger.logSimSettingChanged(1, mobileConnectionsRepositoryImpl4.simCardInfoUtil.isSimSettingOn(1));
                    }
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor("phone1_on"));
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                this.this$0.settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor("phone2_on"));
            }
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl4 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionsRepositoryImpl.this.subscriptionManager.removeOnSubscriptionsChangedListener(r1);
                    MobileConnectionsRepositoryImpl.this.settingsHelper.unregisterCallback(new SettingsHelper.OnChangedCallback(onChangedCallback) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl.mobileSubscriptionsChangeEvent.1.1.1
                        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                        public final void onChanged(Uri uri) {
                        }
                    });
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
