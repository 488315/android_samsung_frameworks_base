package com.android.systemui.development.data.repository;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserInfo $userInfo;
    int label;
    final /* synthetic */ DevelopmentSettingRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1(DevelopmentSettingRepository developmentSettingRepository, UserInfo userInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = developmentSettingRepository;
        this.$userInfo = userInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1(this.this$0, this.$userInfo, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.userManager.hasUserRestrictionForUser("no_debugging_features", this.$userInfo.getUserHandle()));
    }
}
