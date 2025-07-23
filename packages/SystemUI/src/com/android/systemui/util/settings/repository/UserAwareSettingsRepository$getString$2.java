package com.android.systemui.util.settings.repository;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
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
final class UserAwareSettingsRepository$getString$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $name;
    int label;
    final /* synthetic */ UserAwareSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAwareSettingsRepository$getString$2(UserAwareSettingsRepository userAwareSettingsRepository, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userAwareSettingsRepository;
        this.$name = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserAwareSettingsRepository$getString$2(this.this$0, this.$name, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        UserSettingsProxy userSettingsProxy;
        UserRepository userRepository;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        userSettingsProxy = this.this$0.userSettings;
        String str = this.$name;
        userRepository = this.this$0.userRepository;
        return userSettingsProxy.getStringForUser(str, ((UserRepositoryImpl) userRepository).getSelectedUserInfo().id);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((UserAwareSettingsRepository$getString$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
