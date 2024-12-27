package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.util.SettingsHelper;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1 mobileConnectionsRepositoryImpl$simSettingsChangeEvent$1 = new MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$simSettingsChangeEvent$1.L$0 = obj;
        return mobileConnectionsRepositoryImpl$simSettingsChangeEvent$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SettingsHelper settingsHelper;
        SettingsHelper settingsHelper2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
            final SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper3;
                    SettingsHelper settingsHelper4;
                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = MobileConnectionsRepositoryImpl.this;
                    MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl2.logger;
                    settingsHelper3 = mobileConnectionsRepositoryImpl2.settingsHelper;
                    mobileInputLogger.logSimSettingChanged(0, settingsHelper3.isSimSettingOn(0));
                    if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                        MobileInputLogger mobileInputLogger2 = mobileConnectionsRepositoryImpl2.logger;
                        settingsHelper4 = mobileConnectionsRepositoryImpl2.settingsHelper;
                        mobileInputLogger2.logSimSettingChanged(1, settingsHelper4.isSimSettingOn(1));
                    }
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            settingsHelper = this.this$0.settingsHelper;
            settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON));
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                settingsHelper2 = this.this$0.settingsHelper;
                settingsHelper2.registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON));
            }
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SettingsHelper settingsHelper3;
                    settingsHelper3 = MobileConnectionsRepositoryImpl.this.settingsHelper;
                    final SettingsHelper.OnChangedCallback onChangedCallback2 = onChangedCallback;
                    settingsHelper3.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl.simSettingsChangeEvent.1.1.1
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
