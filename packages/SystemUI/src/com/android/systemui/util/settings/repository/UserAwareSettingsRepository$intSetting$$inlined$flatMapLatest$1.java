package com.android.systemui.util.settings.repository;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class UserAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ int $defaultValue$inlined;
    final /* synthetic */ String $name$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ UserAwareSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1(Continuation continuation, UserAwareSettingsRepository userAwareSettingsRepository, String str, int i) {
        super(3, continuation);
        this.this$0 = userAwareSettingsRepository;
        this.$name$inlined = str;
        this.$defaultValue$inlined = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (UserInfo) obj2, (Continuation) obj3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final UserInfo userInfo = (UserInfo) this.L$1;
            final UserAwareSettingsRepository userAwareSettingsRepository = this.this$0;
            final String str = this.$name$inlined;
            int i2 = userInfo.id;
            final int i3 = this.$defaultValue$inlined;
            Flow flow = userAwareSettingsRepository.settingObserver(str, i2, new Function0() { // from class: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$intSetting$1$1
                @Override // kotlin.jvm.functions.Function0
                public final Integer invoke() {
                    return Integer.valueOf(userAwareSettingsRepository.userSettings.getIntForUser(str, i3, userInfo.id));
                }
            });
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
        UserAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1 userAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1 = new UserAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1(continuation, this.this$0, this.$name$inlined, this.$defaultValue$inlined);
        userAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1.L$0 = flowCollector;
        userAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1.L$1 = userInfo;
        return userAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }
}
