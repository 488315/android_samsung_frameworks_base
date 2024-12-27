package com.android.systemui.qs.tiles.base.interactor;

import android.os.UserHandle;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.qs.tiles.base.interactor.DisabledByPolicyInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DisabledByPolicyInteractorImpl$isDisabled$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    final /* synthetic */ String $userRestriction;
    int label;
    final /* synthetic */ DisabledByPolicyInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisabledByPolicyInteractorImpl$isDisabled$2(DisabledByPolicyInteractorImpl disabledByPolicyInteractorImpl, UserHandle userHandle, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = disabledByPolicyInteractorImpl;
        this.$user = userHandle;
        this.$userRestriction = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisabledByPolicyInteractorImpl$isDisabled$2(this.this$0, this.$user, this.$userRestriction, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisabledByPolicyInteractorImpl$isDisabled$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        RestrictedLockProxy restrictedLockProxy = this.this$0.restrictedLockProxy;
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(restrictedLockProxy.context, this.$userRestriction, this.$user.getIdentifier());
        if (checkIfRestrictionEnforced == null) {
            return DisabledByPolicyInteractor.PolicyResult.TileEnabled.INSTANCE;
        }
        return !RestrictedLockUtilsInternal.hasBaseUserRestriction(this.this$0.restrictedLockProxy.context, this.$userRestriction, this.$user.getIdentifier()) ? new DisabledByPolicyInteractor.PolicyResult.TileDisabled(checkIfRestrictionEnforced) : DisabledByPolicyInteractor.PolicyResult.TileEnabled.INSTANCE;
    }
}
