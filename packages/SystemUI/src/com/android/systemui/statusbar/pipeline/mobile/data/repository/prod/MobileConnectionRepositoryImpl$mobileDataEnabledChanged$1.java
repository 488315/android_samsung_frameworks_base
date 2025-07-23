package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1 mobileConnectionRepositoryImpl$mobileDataEnabledChanged$1 = new MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1(this.this$0, continuation);
        mobileConnectionRepositoryImpl$mobileDataEnabledChanged$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$mobileDataEnabledChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SettingsHelper settingsHelper;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1$callback$1
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    SettingsHelper settingsHelper2;
                    Log.d("MobileConnectionRepositoryImpl", "onChange: MOBILE_DATA");
                    settingsHelper2 = MobileConnectionRepositoryImpl.this.settingsHelper;
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Boolean.valueOf(settingsHelper2.isMobileDataEnabled()));
                }
            };
            settingsHelper = this.this$0.settingsHelper;
            settingsHelper.registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA));
            MobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0 mobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0 = new MobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0(3, this.this$0, onChangedCallback);
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
