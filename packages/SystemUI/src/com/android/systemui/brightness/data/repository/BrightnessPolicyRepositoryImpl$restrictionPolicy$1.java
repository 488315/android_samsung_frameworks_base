package com.android.systemui.brightness.data.repository;

import android.content.Context;
import android.content.pm.UserInfo;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.utils.PolicyRestriction;
import com.android.systemui.utils.UserRestrictionChecker;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BrightnessPolicyRepositoryImpl$restrictionPolicy$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BrightnessPolicyRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrightnessPolicyRepositoryImpl$restrictionPolicy$1(BrightnessPolicyRepositoryImpl brightnessPolicyRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = brightnessPolicyRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BrightnessPolicyRepositoryImpl$restrictionPolicy$1 brightnessPolicyRepositoryImpl$restrictionPolicy$1 = new BrightnessPolicyRepositoryImpl$restrictionPolicy$1(this.this$0, continuation);
        brightnessPolicyRepositoryImpl$restrictionPolicy$1.L$0 = obj;
        return brightnessPolicyRepositoryImpl$restrictionPolicy$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BrightnessPolicyRepositoryImpl$restrictionPolicy$1) create((UserInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UserInfo userInfo = (UserInfo) this.L$0;
        BrightnessPolicyRepositoryImpl brightnessPolicyRepositoryImpl = this.this$0;
        UserRestrictionChecker userRestrictionChecker = brightnessPolicyRepositoryImpl.userRestrictionChecker;
        Context context = brightnessPolicyRepositoryImpl.applicationContext;
        int i = userInfo.id;
        userRestrictionChecker.getClass();
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_config_brightness", i);
        if (checkIfRestrictionEnforced != null) {
            return new PolicyRestriction.Restricted(checkIfRestrictionEnforced);
        }
        BrightnessPolicyRepositoryImpl brightnessPolicyRepositoryImpl2 = this.this$0;
        UserRestrictionChecker userRestrictionChecker2 = brightnessPolicyRepositoryImpl2.userRestrictionChecker;
        Context context2 = brightnessPolicyRepositoryImpl2.applicationContext;
        int i2 = userInfo.id;
        userRestrictionChecker2.getClass();
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(context2, "no_config_brightness", i2) ? new PolicyRestriction.Restricted(new RestrictedLockUtils.EnforcedAdmin()) : PolicyRestriction.NoRestriction.INSTANCE;
    }
}
