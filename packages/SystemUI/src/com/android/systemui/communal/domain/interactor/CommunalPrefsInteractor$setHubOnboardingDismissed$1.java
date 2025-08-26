package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalPrefsRepository;
import com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class CommunalPrefsInteractor$setHubOnboardingDismissed$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserInfo $user;
    int label;
    final /* synthetic */ CommunalPrefsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsInteractor$setHubOnboardingDismissed$1(CommunalPrefsInteractor communalPrefsInteractor, UserInfo userInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalPrefsInteractor;
        this.$user = userInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalPrefsInteractor$setHubOnboardingDismissed$1(this.this$0, this.$user, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalPrefsInteractor$setHubOnboardingDismissed$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalPrefsRepository communalPrefsRepository = this.this$0.repository;
            UserInfo userInfo = this.$user;
            this.label = 1;
            Object booleanKeyValueForUser = ((CommunalPrefsRepositoryImpl) communalPrefsRepository).setBooleanKeyValueForUser(userInfo, "hub_onboarding_dismissed", "Dismissed hub onboarding", this);
            if (booleanKeyValueForUser != coroutineSingletons) {
                booleanKeyValueForUser = Unit.INSTANCE;
            }
            if (booleanKeyValueForUser == coroutineSingletons) {
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
}
