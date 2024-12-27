package com.android.systemui.brightness.data.repository;

import android.content.Context;
import android.content.pm.UserInfo;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.Flags;
import com.android.systemui.utils.UserRestrictionChecker;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        final RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_config_brightness", i);
        if (checkIfRestrictionEnforced != null) {
            return new Object(checkIfRestrictionEnforced) { // from class: com.android.systemui.utils.PolicyRestriction$Restricted
                public final RestrictedLockUtils.EnforcedAdmin admin;

                {
                    this.admin = checkIfRestrictionEnforced;
                }

                public final boolean equals(Object obj2) {
                    if (this == obj2) {
                        return true;
                    }
                    return (obj2 instanceof PolicyRestriction$Restricted) && Intrinsics.areEqual(this.admin, ((PolicyRestriction$Restricted) obj2).admin);
                }

                public final int hashCode() {
                    return this.admin.hashCode();
                }

                public final String toString() {
                    return "Restricted(admin=" + this.admin + ")";
                }
            };
        }
        Flags.FEATURE_FLAGS.getClass();
        BrightnessPolicyRepositoryImpl brightnessPolicyRepositoryImpl2 = this.this$0;
        UserRestrictionChecker userRestrictionChecker2 = brightnessPolicyRepositoryImpl2.userRestrictionChecker;
        Context context2 = brightnessPolicyRepositoryImpl2.applicationContext;
        int i2 = userInfo.id;
        userRestrictionChecker2.getClass();
        if (!RestrictedLockUtilsInternal.hasBaseUserRestriction(context2, "no_config_brightness", i2)) {
            return new Object() { // from class: com.android.systemui.utils.PolicyRestriction$NoRestriction
                public final boolean equals(Object obj2) {
                    return this == obj2 || (obj2 instanceof PolicyRestriction$NoRestriction);
                }

                public final int hashCode() {
                    return 204915163;
                }

                public final String toString() {
                    return "NoRestriction";
                }
            };
        }
        final RestrictedLockUtils.EnforcedAdmin enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin();
        return new Object(enforcedAdmin) { // from class: com.android.systemui.utils.PolicyRestriction$Restricted
            public final RestrictedLockUtils.EnforcedAdmin admin;

            {
                this.admin = enforcedAdmin;
            }

            public final boolean equals(Object obj2) {
                if (this == obj2) {
                    return true;
                }
                return (obj2 instanceof PolicyRestriction$Restricted) && Intrinsics.areEqual(this.admin, ((PolicyRestriction$Restricted) obj2).admin);
            }

            public final int hashCode() {
                return this.admin.hashCode();
            }

            public final String toString() {
                return "Restricted(admin=" + this.admin + ")";
            }
        };
    }
}
