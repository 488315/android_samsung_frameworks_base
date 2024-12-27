package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
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
final class MobileConnectionRepositoryImpl$semSatelliteEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$semSatelliteEnabled$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryImpl;
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$semSatelliteEnabled$1 mobileConnectionRepositoryImpl$semSatelliteEnabled$1 = new MobileConnectionRepositoryImpl$semSatelliteEnabled$1(this.this$0, this.$logger, continuation);
        mobileConnectionRepositoryImpl$semSatelliteEnabled$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$semSatelliteEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$semSatelliteEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SettingsHelper settingsHelper;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            final SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$semSatelliteEnabled$1$callback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper2;
                    SettingsHelper settingsHelper3;
                    SettingsHelper settingsHelper4;
                    int i2 = SystemProperties.getInt("ril.tiantong.phone.id", -1);
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = mobileConnectionRepositoryImpl;
                    settingsHelper2 = mobileConnectionRepositoryImpl2.settingsHelper;
                    MobileInputLogger.this.logSatelliteEnabled(i2, settingsHelper2.isSatelliteEnabled());
                    settingsHelper3 = mobileConnectionRepositoryImpl2.settingsHelper;
                    boolean isSatelliteEnabled = settingsHelper3.isSatelliteEnabled();
                    int i3 = mobileConnectionRepositoryImpl2.slotId;
                    StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("isSatelliteEnabled: ", i2, ", phoneId: ", isSatelliteEnabled, ", slotId: ");
                    m.append(i3);
                    Log.d("MobileConnectionRepositoryImpl", m.toString());
                    settingsHelper4 = mobileConnectionRepositoryImpl2.settingsHelper;
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(settingsHelper4.isSatelliteEnabled() && i2 == mobileConnectionRepositoryImpl2.slotId));
                }
            };
            settingsHelper = this.this$0.settingsHelper;
            settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED));
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$semSatelliteEnabled$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SettingsHelper settingsHelper2;
                    settingsHelper2 = MobileConnectionRepositoryImpl.this.settingsHelper;
                    final SettingsHelper.OnChangedCallback onChangedCallback2 = onChangedCallback;
                    settingsHelper2.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl.semSatelliteEnabled.1.1.1
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
