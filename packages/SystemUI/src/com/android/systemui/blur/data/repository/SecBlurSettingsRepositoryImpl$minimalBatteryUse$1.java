package com.android.systemui.blur.data.repository;

import android.net.Uri;
import android.provider.Settings;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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

/* loaded from: classes.dex */
final class SecBlurSettingsRepositoryImpl$minimalBatteryUse$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SettingsHelper $settingsHelper;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecBlurSettingsRepositoryImpl$minimalBatteryUse$1(SettingsHelper settingsHelper, Continuation continuation) {
        super(2, continuation);
        this.$settingsHelper = settingsHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecBlurSettingsRepositoryImpl$minimalBatteryUse$1 secBlurSettingsRepositoryImpl$minimalBatteryUse$1 = new SecBlurSettingsRepositoryImpl$minimalBatteryUse$1(this.$settingsHelper, continuation);
        secBlurSettingsRepositoryImpl$minimalBatteryUse$1.L$0 = obj;
        return secBlurSettingsRepositoryImpl$minimalBatteryUse$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecBlurSettingsRepositoryImpl$minimalBatteryUse$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final SettingsHelper settingsHelper = this.$settingsHelper;
            SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.blur.data.repository.SecBlurSettingsRepositoryImpl$minimalBatteryUse$1$settingsCallback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    if (uri == null) {
                        return;
                    }
                    if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE)) || uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE))) {
                        SecBlurSettingsRepositoryImpl.Companion.getClass();
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("isUltraPowerSavingMode changed ", SecBlurSettingsRepositoryImpl.TAG, settingsHelper.isUltraPowerSavingMode());
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(settingsHelper.isUltraPowerSavingMode()));
                    }
                }
            };
            this.$settingsHelper.registerCallback(onChangedCallback, Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE));
            SecBlurSettingsRepositoryImpl$blurReduced$1$$ExternalSyntheticLambda0 secBlurSettingsRepositoryImpl$blurReduced$1$$ExternalSyntheticLambda0 = new SecBlurSettingsRepositoryImpl$blurReduced$1$$ExternalSyntheticLambda0(this.$settingsHelper, onChangedCallback, 1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, secBlurSettingsRepositoryImpl$blurReduced$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
