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
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = mobileConnectionRepositoryImpl;
                    mobileInputLogger.logSimSettingChanged(0, mobileConnectionRepositoryImpl2.settingsHelper.isSimSettingOn(0));
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(mobileConnectionRepositoryImpl2.settingsHelper.isSimSettingOn(0)));
                }
            };
            final MobileInputLogger mobileInputLogger2 = this.$logger;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.this$0;
            final ?? r3 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl3 = mobileConnectionRepositoryImpl2;
                    mobileInputLogger2.logSimSettingChanged(1, mobileConnectionRepositoryImpl3.settingsHelper.isSimSettingOn(1));
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(mobileConnectionRepositoryImpl3.settingsHelper.isSimSettingOn(1)));
                }
            };
            this.this$0.settingsHelper.registerCallback(r1, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON));
            this.$logger.logSimSettingChanged(0, this.this$0.isSimSettingOn(0));
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                this.this$0.settingsHelper.registerCallback(r3, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON));
                this.$logger.logSimSettingChanged(1, this.this$0.isSimSettingOn(1));
            }
            ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.TRUE);
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl4 = mobileConnectionRepositoryImpl3;
                    SettingsHelper settingsHelper = mobileConnectionRepositoryImpl4.settingsHelper;
                    final MobileConnectionRepositoryImpl$simSettingsChanged$1$callback1$1 mobileConnectionRepositoryImpl$simSettingsChanged$1$callback1$1 = r1;
                    settingsHelper.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$1$1
                        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                        public final void onChanged(Uri uri) {
                        }
                    });
                    SettingsHelper settingsHelper2 = mobileConnectionRepositoryImpl4.settingsHelper;
                    final MobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1 mobileConnectionRepositoryImpl$simSettingsChanged$1$callback2$1 = r3;
                    settingsHelper2.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$simSettingsChanged$1$1$2
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
