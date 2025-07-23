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
final class SettingsProxy$registerContentObserverAsync$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $name;
    final /* synthetic */ boolean $notifyForDescendants;
    final /* synthetic */ ContentObserver $settingsObserver;
    int label;
    final /* synthetic */ SettingsProxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxy$registerContentObserverAsync$5(SettingsProxy settingsProxy, String str, boolean z, ContentObserver contentObserver, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingsProxy;
        this.$name = str;
        this.$notifyForDescendants = z;
        this.$settingsObserver = contentObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingsProxy$registerContentObserverAsync$5(this.this$0, this.$name, this.$notifyForDescendants, this.$settingsObserver, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SettingsProxy settingsProxy = this.this$0;
        settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.$name), this.$notifyForDescendants, this.$settingsObserver);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SettingsProxy$registerContentObserverAsync$5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
