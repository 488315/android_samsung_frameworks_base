package com.android.systemui.communal.data.repository;

import android.content.pm.UserInfo;
import com.android.systemui.log.core.Logger;
import com.android.systemui.settings.UserFileManagerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalPrefsRepositoryImpl$setBooleanKeyValueForUser$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $key;
    final /* synthetic */ String $logMsg;
    final /* synthetic */ UserInfo $user;
    int label;
    final /* synthetic */ CommunalPrefsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsRepositoryImpl$setBooleanKeyValueForUser$2(CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl, UserInfo userInfo, String str, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalPrefsRepositoryImpl;
        this.$user = userInfo;
        this.$key = str;
        this.$logMsg = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalPrefsRepositoryImpl$setBooleanKeyValueForUser$2(this.this$0, this.$user, this.$key, this.$logMsg, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalPrefsRepositoryImpl$setBooleanKeyValueForUser$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl = this.this$0;
        UserInfo userInfo = this.$user;
        int i = CommunalPrefsRepositoryImpl.$r8$clinit;
        communalPrefsRepositoryImpl.getClass();
        ((UserFileManagerImpl) communalPrefsRepositoryImpl.userFileManager).getSharedPreferences$1(userInfo.id, "communal_hub_prefs").edit().putBoolean(this.$key, true).apply();
        Logger.i$default((Logger) this.this$0.logger$delegate.getValue(), this.$logMsg, null, 2, null);
        return Unit.INSTANCE;
    }
}
