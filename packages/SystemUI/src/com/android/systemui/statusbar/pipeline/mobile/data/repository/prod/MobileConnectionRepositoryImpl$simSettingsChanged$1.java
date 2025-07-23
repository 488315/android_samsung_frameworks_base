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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryImpl$simSettingsChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$callback1$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SettingsHelper settingsHelper;
        SettingsHelper settingsHelper2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            final ?? r1 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$callback1$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper3;
                    SettingsHelper settingsHelper4;
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = mobileConnectionRepositoryImpl;
                    settingsHelper3 = mobileConnectionRepositoryImpl2.settingsHelper;
                    MobileInputLogger.this.logSimSettingChanged(0, settingsHelper3.isSimSettingOn(0));
                    settingsHelper4 = mobileConnectionRepositoryImpl2.settingsHelper;
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Boolean.valueOf(settingsHelper4.isSimSettingOn(0)));
                }
            };
            final MobileInputLogger mobileInputLogger2 = this.$logger;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.this$0;
            final ?? r3 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper3;
                    SettingsHelper settingsHelper4;
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl3 = mobileConnectionRepositoryImpl2;
                    settingsHelper3 = mobileConnectionRepositoryImpl3.settingsHelper;
                    MobileInputLogger.this.logSimSettingChanged(1, settingsHelper3.isSimSettingOn(1));
                    settingsHelper4 = mobileConnectionRepositoryImpl3.settingsHelper;
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Boolean.valueOf(settingsHelper4.isSimSettingOn(1)));
                }
            };
            settingsHelper = this.this$0.settingsHelper;
            settingsHelper.registerCallback(r1, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON));
            this.$logger.logSimSettingChanged(0, this.this$0.isSimSettingOn(0));
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                settingsHelper2 = this.this$0.settingsHelper;
                settingsHelper2.registerCallback(r3, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON));
                this.$logger.logSimSettingChanged(1, this.this$0.isSimSettingOn(1));
            }
            ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Boolean.TRUE);
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SettingsHelper settingsHelper3;
                    SettingsHelper settingsHelper4;
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl4 = MobileConnectionRepositoryImpl.this;
                    settingsHelper3 = mobileConnectionRepositoryImpl4.settingsHelper;
                    final MobileConnectionRepositoryImpl$simSettingsChanged$1$callback1$1 mobileConnectionRepositoryImpl$simSettingsChanged$1$callback1$1 = r1;
                    settingsHelper3.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$1$1
                        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                        public final void onChanged(Uri uri) {
                        }
                    });
                    settingsHelper4 = mobileConnectionRepositoryImpl4.settingsHelper;
                    final MobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1 mobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1 = r3;
                    settingsHelper4.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$1$2
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
