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

final class MobileConnectionRepositoryImpl$simSettingsChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    public MobileConnectionRepositoryImpl$simSettingsChanged$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryImpl;
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$simSettingsChanged$1 mobileConnectionRepositoryImpl$simSettingsChanged$1 = new MobileConnectionRepositoryImpl$simSettingsChanged$1(this.this$0, this.$logger, continuation);
        mobileConnectionRepositoryImpl$simSettingsChanged$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$simSettingsChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$simSettingsChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SettingsHelper settingsHelper;
        SettingsHelper settingsHelper2;
        SettingsHelper settingsHelper3;
        SettingsHelper settingsHelper4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            final SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$callback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper5;
                    SettingsHelper settingsHelper6;
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = mobileConnectionRepositoryImpl;
                    settingsHelper5 = mobileConnectionRepositoryImpl2.settingsHelper;
                    boolean isSimSettingOn = settingsHelper5.isSimSettingOn(0);
                    MobileInputLogger mobileInputLogger2 = MobileInputLogger.this;
                    mobileInputLogger2.logSimSettingChanged(0, isSimSettingOn);
                    if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                        settingsHelper6 = mobileConnectionRepositoryImpl2.settingsHelper;
                        mobileInputLogger2.logSimSettingChanged(1, settingsHelper6.isSimSettingOn(1));
                    }
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            settingsHelper = this.this$0.settingsHelper;
            settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON));
            MobileInputLogger mobileInputLogger2 = this.$logger;
            settingsHelper2 = this.this$0.settingsHelper;
            mobileInputLogger2.logSimSettingChanged(0, settingsHelper2.isSimSettingOn(0));
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                settingsHelper3 = this.this$0.settingsHelper;
                settingsHelper3.registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON));
                MobileInputLogger mobileInputLogger3 = this.$logger;
                settingsHelper4 = this.this$0.settingsHelper;
                mobileInputLogger3.logSimSettingChanged(1, settingsHelper4.isSimSettingOn(1));
            }
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SettingsHelper settingsHelper5;
                    settingsHelper5 = MobileConnectionRepositoryImpl.this.settingsHelper;
                    final SettingsHelper.OnChangedCallback onChangedCallback2 = onChangedCallback;
                    settingsHelper5.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl.simSettingsChanged.1.1.1
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
