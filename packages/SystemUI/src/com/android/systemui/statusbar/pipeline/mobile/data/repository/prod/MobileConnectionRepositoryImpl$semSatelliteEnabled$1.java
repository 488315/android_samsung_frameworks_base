package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import com.android.systemui.util.SettingsHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileInputLogger mobileInputLogger = this.$logger;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$semSatelliteEnabled$1$callback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    int i2 = SystemProperties.getInt("ril.tiantong.phone.id", -1);
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = mobileConnectionRepositoryImpl;
                    boolean zIsSatelliteEnabled = mobileConnectionRepositoryImpl2.settingsHelper.isSatelliteEnabled();
                    MobileInputLogger mobileInputLogger2 = mobileInputLogger;
                    mobileInputLogger2.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(16);
                    LogBuffer logBuffer = mobileInputLogger2.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.bool1 = zIsSatelliteEnabled;
                    logMessageImpl.int1 = i2;
                    logBuffer.commit(logMessageObtain);
                    StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("isSatelliteEnabled: ", i2, ", phoneId: ", mobileConnectionRepositoryImpl2.settingsHelper.isSatelliteEnabled(), ", slotId: ");
                    int i3 = mobileConnectionRepositoryImpl2.slotId;
                    sbM.append(i3);
                    Log.d("MobileConnectionRepositoryImpl", sbM.toString());
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(mobileConnectionRepositoryImpl2.settingsHelper.isSatelliteEnabled() && i2 == i3));
                }
            };
            ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(this.this$0.settingsHelper.isSatelliteEnabled() && SystemProperties.getInt("ril.tiantong.phone.id", -1) == this.this$0.slotId));
            MobileInputLogger mobileInputLogger2 = this.$logger;
            boolean zIsSatelliteEnabled = this.this$0.settingsHelper.isSatelliteEnabled();
            int i2 = SystemProperties.getInt("ril.tiantong.phone.id", -1);
            mobileInputLogger2.getClass();
            LogLevel logLevel = LogLevel.INFO;
            MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(16);
            LogBuffer logBuffer = mobileInputLogger2.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.bool1 = zIsSatelliteEnabled;
            logMessageImpl.int1 = i2;
            logBuffer.commit(logMessageObtain);
            this.this$0.settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor("satellite_mode_enabled"));
            MobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0 mobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0 = new MobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0(5, this.this$0, onChangedCallback);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, mobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
