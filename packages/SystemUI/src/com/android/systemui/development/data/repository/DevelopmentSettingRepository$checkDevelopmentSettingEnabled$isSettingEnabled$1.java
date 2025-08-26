package com.android.systemui.development.data.repository;

import com.android.systemui.util.settings.GlobalSettings;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DevelopmentSettingRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1(DevelopmentSettingRepository developmentSettingRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = developmentSettingRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        GlobalSettings globalSettings = this.this$0.globalSettings;
        DevelopmentSettingRepository.Companion.getClass();
        return Boolean.valueOf(globalSettings.getInt("development_settings_enabled", DevelopmentSettingRepository.DEFAULT_ENABLED) != 0);
    }
}
