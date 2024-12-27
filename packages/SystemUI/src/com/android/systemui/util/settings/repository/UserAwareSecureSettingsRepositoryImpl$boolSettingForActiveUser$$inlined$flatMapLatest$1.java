package com.android.systemui.util.settings.repository;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $defaultValue$inlined;
    final /* synthetic */ String $name$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ UserAwareSecureSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1(Continuation continuation, UserAwareSecureSettingsRepositoryImpl userAwareSecureSettingsRepositoryImpl, String str, boolean z) {
        super(3, continuation);
        this.this$0 = userAwareSecureSettingsRepositoryImpl;
        this.$name$inlined = str;
        this.$defaultValue$inlined = z;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (UserInfo) obj2, (Continuation) obj3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            flow = this.this$0.settingObserver(this.$name$inlined, this.$defaultValue$inlined, ((UserInfo) this.L$1).id);
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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

    public final Object invoke(FlowCollector flowCollector, UserInfo userInfo, Continuation continuation) {
        UserAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1 userAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1 = new UserAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1(continuation, this.this$0, this.$name$inlined, this.$defaultValue$inlined);
        userAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1.L$0 = flowCollector;
        userAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1.L$1 = userInfo;
        return userAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }
}
