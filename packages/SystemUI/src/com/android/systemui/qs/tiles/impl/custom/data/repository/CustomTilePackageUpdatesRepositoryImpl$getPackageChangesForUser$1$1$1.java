package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.os.UserHandle;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class CustomTilePackageUpdatesRepositoryImpl$getPackageChangesForUser$1$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ CustomTilePackageUpdatesRepositoryImpl this$0;

    public CustomTilePackageUpdatesRepositoryImpl$getPackageChangesForUser$1$1$1(CustomTilePackageUpdatesRepositoryImpl customTilePackageUpdatesRepositoryImpl, UserHandle userHandle, Continuation continuation) {
        super(3, continuation);
        this.this$0 = customTilePackageUpdatesRepositoryImpl;
        this.$user = userHandle;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new CustomTilePackageUpdatesRepositoryImpl$getPackageChangesForUser$1$1$1(this.this$0, this.$user, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CustomTilePackageUpdatesRepositoryImpl customTilePackageUpdatesRepositoryImpl = this.this$0;
        Map map = customTilePackageUpdatesRepositoryImpl.perUserCache;
        UserHandle userHandle = this.$user;
        synchronized (map) {
            customTilePackageUpdatesRepositoryImpl.perUserCache.remove(userHandle);
        }
        return Unit.INSTANCE;
    }
}
