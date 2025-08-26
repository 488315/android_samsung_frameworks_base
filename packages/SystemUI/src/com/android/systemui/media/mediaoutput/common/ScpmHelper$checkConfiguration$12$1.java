package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.MutablePreferences;
import com.android.systemui.media.mediaoutput.entity.Configuration;
import com.google.gson.Gson;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class ScpmHelper$checkConfiguration$12$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Gson $gson;
    final /* synthetic */ Configuration $it;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScpmHelper$checkConfiguration$12$1(Gson gson, Configuration configuration, Continuation continuation) {
        super(2, continuation);
        this.$gson = gson;
        this.$it = configuration;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScpmHelper$checkConfiguration$12$1 scpmHelper$checkConfiguration$12$1 = new ScpmHelper$checkConfiguration$12$1(this.$gson, this.$it, continuation);
        scpmHelper$checkConfiguration$12$1.L$0 = obj;
        return scpmHelper$checkConfiguration$12$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScpmHelper$checkConfiguration$12$1) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
        PreferenceKeys.INSTANCE.getClass();
        mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceKeys.SCPM_CONFIGURATION, this.$gson.toJson(this.$it));
        return Unit.INSTANCE;
    }
}
