package com.android.systemui.media.mediaoutput.viewmodel;

import android.provider.Settings;
import androidx.datastore.preferences.core.MutablePreferences;
import com.android.systemui.media.mediaoutput.common.PreferenceKeys;
import java.util.Arrays;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SettingViewModel$updateSystemSettings$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SettingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$updateSystemSettings$2(SettingViewModel settingViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingViewModel$updateSystemSettings$2 settingViewModel$updateSystemSettings$2 = new SettingViewModel$updateSystemSettings$2(this.this$0, continuation);
        settingViewModel$updateSystemSettings$2.L$0 = obj;
        return settingViewModel$updateSystemSettings$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$updateSystemSettings$2) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
        PreferenceKeys preferenceKeys = PreferenceKeys.INSTANCE;
        preferenceKeys.getClass();
        Integer num = new Integer(!(((Boolean) mutablePreferences.get(PreferenceKeys.MIRRORING_PRIORITY)) != null ? r1.booleanValue() : 1));
        preferenceKeys.getClass();
        Boolean bool = (Boolean) mutablePreferences.get(PreferenceKeys.SPOTIFY_CASTING_PRIORITY);
        Integer num2 = new Integer(bool != null ? bool.booleanValue() : true ? 0 : 2);
        preferenceKeys.getClass();
        Boolean bool2 = (Boolean) mutablePreferences.get(PreferenceKeys.SHOW_MUSIC_SHARE);
        Settings.System.putInt(this.this$0.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", CollectionsKt___CollectionsKt.sumOfInt(Arrays.asList(num, num2, new Integer(bool2 != null ? bool2.booleanValue() : true ? 0 : 4))));
        return Unit.INSTANCE;
    }
}
