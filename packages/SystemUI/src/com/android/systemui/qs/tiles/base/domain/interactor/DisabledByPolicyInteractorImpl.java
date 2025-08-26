package com.android.systemui.qs.tiles.base.domain.interactor;

import android.content.Context;
import android.os.UserHandle;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.base.domain.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DisabledByPolicyInteractorImpl implements DisabledByPolicyInteractor {
    public final ActivityStarter activityStarter;
    public final CoroutineDispatcher backgroundDispatcher;
    public final RestrictedLockProxy restrictedLockProxy;

    /* renamed from: com.android.systemui.qs.tiles.base.domain.interactor.DisabledByPolicyInteractorImpl$isDisabled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        final /* synthetic */ String $userRestriction;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UserHandle userHandle, String str, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
            this.$userRestriction = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisabledByPolicyInteractorImpl.this.new AnonymousClass2(this.$user, this.$userRestriction, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            RestrictedLockProxy restrictedLockProxy = DisabledByPolicyInteractorImpl.this.restrictedLockProxy;
            RestrictedLockUtils.EnforcedAdmin enforcedAdminCheckIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(restrictedLockProxy.context, this.$userRestriction, this.$user.getIdentifier());
            if (enforcedAdminCheckIfRestrictionEnforced == null) {
                return DisabledByPolicyInteractor.PolicyResult.TileEnabled.INSTANCE;
            }
            return !RestrictedLockUtilsInternal.hasBaseUserRestriction(DisabledByPolicyInteractorImpl.this.restrictedLockProxy.context, this.$userRestriction, this.$user.getIdentifier()) ? new DisabledByPolicyInteractor.PolicyResult.TileDisabled(enforcedAdminCheckIfRestrictionEnforced) : DisabledByPolicyInteractor.PolicyResult.TileEnabled.INSTANCE;
        }
    }

    public DisabledByPolicyInteractorImpl(Context context, ActivityStarter activityStarter, RestrictedLockProxy restrictedLockProxy, CoroutineDispatcher coroutineDispatcher) {
        this.activityStarter = activityStarter;
        this.restrictedLockProxy = restrictedLockProxy;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object isDisabled(UserHandle userHandle, String str, QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1.AnonymousClass2.AnonymousClass1 anonymousClass1) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(userHandle, str, null), anonymousClass1);
    }
}
