package com.android.systemui.util.settings;

import android.database.ContentObserver;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SettingsProxy$unregisterContentObserverAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ContentObserver $settingsObserver;
    int label;
    final /* synthetic */ SettingsProxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxy$unregisterContentObserverAsync$1(SettingsProxy settingsProxy, ContentObserver contentObserver, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingsProxy;
        this.$settingsObserver = contentObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingsProxy$unregisterContentObserverAsync$1(this.this$0, this.$settingsObserver, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SettingsProxy settingsProxy = this.this$0;
            ContentObserver contentObserver = this.$settingsObserver;
            this.label = 1;
            if (settingsProxy.unregisterContentObserver(contentObserver, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SettingsProxy$unregisterContentObserverAsync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
