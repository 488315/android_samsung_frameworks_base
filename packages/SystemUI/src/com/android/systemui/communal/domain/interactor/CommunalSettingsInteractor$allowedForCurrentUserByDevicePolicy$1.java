package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalSettingsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1(CommunalSettingsInteractor communalSettingsInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSettingsInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1 communalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1 = new CommunalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1(this.this$0, continuation);
        communalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1.L$0 = obj;
        return communalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1) create((UserInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((CommunalSettingsRepositoryImpl) this.this$0.repository).getAllowedByDevicePolicy((UserInfo) this.L$0);
    }
}
