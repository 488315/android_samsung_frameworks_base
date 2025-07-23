package com.android.systemui.inputdevice.tutorial.data.repository;

import androidx.datastore.preferences.core.MutablePreferences;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TutorialSchedulerRepository$clear$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public TutorialSchedulerRepository$clear$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TutorialSchedulerRepository$clear$2 tutorialSchedulerRepository$clear$2 = new TutorialSchedulerRepository$clear$2(continuation);
        tutorialSchedulerRepository$clear$2.L$0 = obj;
        return tutorialSchedulerRepository$clear$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerRepository$clear$2) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
        mutablePreferences.checkNotFrozen$datastore_preferences_core();
        mutablePreferences.preferencesMap.clear();
        return Unit.INSTANCE;
    }
}
