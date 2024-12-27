package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class SettingViewModel$setCastingPriority$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Preferences.Key $key;
    final /* synthetic */ int $value;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$setCastingPriority$1$1$1(Preferences.Key key, int i, Continuation continuation) {
        super(2, continuation);
        this.$key = key;
        this.$value = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingViewModel$setCastingPriority$1$1$1 settingViewModel$setCastingPriority$1$1$1 = new SettingViewModel$setCastingPriority$1$1$1(this.$key, this.$value, continuation);
        settingViewModel$setCastingPriority$1$1$1.L$0 = obj;
        return settingViewModel$setCastingPriority$1$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$setCastingPriority$1$1$1) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((MutablePreferences) this.L$0).setUnchecked$datastore_preferences_core(this.$key, new Integer(this.$value));
        return Unit.INSTANCE;
    }
}
