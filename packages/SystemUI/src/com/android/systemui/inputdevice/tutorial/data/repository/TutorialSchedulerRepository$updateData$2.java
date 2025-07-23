package com.android.systemui.inputdevice.tutorial.data.repository;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TutorialSchedulerRepository$updateData$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Preferences.Key $key;
    final /* synthetic */ Object $value;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerRepository$updateData$2(Preferences.Key key, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$key = key;
        this.$value = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TutorialSchedulerRepository$updateData$2 tutorialSchedulerRepository$updateData$2 = new TutorialSchedulerRepository$updateData$2(this.$key, this.$value, continuation);
        tutorialSchedulerRepository$updateData$2.L$0 = obj;
        return tutorialSchedulerRepository$updateData$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerRepository$updateData$2) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((MutablePreferences) this.L$0).setUnchecked$datastore_preferences_core(this.$key, this.$value);
        return Unit.INSTANCE;
    }
}
