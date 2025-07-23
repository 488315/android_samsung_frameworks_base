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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback1$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback2$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
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
            final ?? r1 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback1$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper3;
                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = MobileConnectionsRepositoryImpl.this;
                    MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl2.logger;
                    settingsHelper3 = mobileConnectionsRepositoryImpl2.settingsHelper;
                    mobileInputLogger.logSimSettingChanged(0, settingsHelper3.isSimSettingOn(0));
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = this.this$0;
            final ?? r3 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback2$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper3;
                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl3 = MobileConnectionsRepositoryImpl.this;
                    MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl3.logger;
                    settingsHelper3 = mobileConnectionsRepositoryImpl3.settingsHelper;
                    mobileInputLogger.logSimSettingChanged(1, settingsHelper3.isSimSettingOn(1));
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            settingsHelper = this.this$0.settingsHelper;
            settingsHelper.registerCallback(r1, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM1_ON));
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                settingsHelper2 = this.this$0.settingsHelper;
                settingsHelper2.registerCallback(r3, Settings.Global.getUriFor(SettingsHelper.INDEX_MULTI_SIM_DEVICE_SIM2_ON));
            }
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SettingsHelper settingsHelper3;
                    SettingsHelper settingsHelper4;
                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl4 = MobileConnectionsRepositoryImpl.this;
                    settingsHelper3 = mobileConnectionsRepositoryImpl4.settingsHelper;
                    final MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback1$1 mobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback1$1 = r1;
                    settingsHelper3.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$1$1
                        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                        public final void onChanged(Uri uri) {
                        }
                    });
                    settingsHelper4 = mobileConnectionsRepositoryImpl4.settingsHelper;
                    final MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback2$1 mobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$callback2$1 = r3;
                    settingsHelper4.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1$1$2
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
