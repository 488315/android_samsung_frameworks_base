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
final class SettingsProxy$registerContentObserverAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $name;
    final /* synthetic */ ContentObserver $settingsObserver;
    int label;
    final /* synthetic */ SettingsProxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxy$registerContentObserverAsync$1(SettingsProxy settingsProxy, String str, ContentObserver contentObserver, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingsProxy;
        this.$name = str;
        this.$settingsObserver = contentObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingsProxy$registerContentObserverAsync$1(this.this$0, this.$name, this.$settingsObserver, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SettingsProxy settingsProxy = this.this$0;
        settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.$name), this.$settingsObserver);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SettingsProxy$registerContentObserverAsync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
