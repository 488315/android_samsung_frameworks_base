package com.android.systemui.util.settings.repository;

import com.android.systemui.util.settings.UserSettingsProxy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SettingsForUserRepository$setBoolForUser$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $name;
    final /* synthetic */ int $userId;
    final /* synthetic */ boolean $value;
    int label;
    final /* synthetic */ SettingsForUserRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsForUserRepository$setBoolForUser$2(SettingsForUserRepository settingsForUserRepository, String str, boolean z, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingsForUserRepository;
        this.$name = str;
        this.$value = z;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingsForUserRepository$setBoolForUser$2(this.this$0, this.$name, this.$value, this.$userId, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        UserSettingsProxy userSettingsProxy;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        userSettingsProxy = this.this$0.userSettings;
        return Boolean.valueOf(userSettingsProxy.putBoolForUser(this.$name, this.$value, this.$userId));
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SettingsForUserRepository$setBoolForUser$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
