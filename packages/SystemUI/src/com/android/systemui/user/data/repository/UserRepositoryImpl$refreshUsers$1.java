package com.android.systemui.user.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class UserRepositoryImpl$refreshUsers$1 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ UserRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = userRepositoryImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.this$0.manager.getAliveUsers();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$refreshUsers$1(UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserRepositoryImpl$refreshUsers$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$refreshUsers$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x009e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L22
            if (r1 == r4) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r7)
            goto L9a
        L12:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1a:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.MutableStateFlow r1 = (kotlinx.coroutines.flow.MutableStateFlow) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L3b
        L22:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.user.data.repository.UserRepositoryImpl r7 = r6.this$0
            kotlinx.coroutines.flow.StateFlowImpl r1 = r7._userInfos
            com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$1 r5 = new com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$1
            r5.<init>(r7, r3)
            r6.L$0 = r1
            r6.label = r4
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r5, r6)
            if (r7 != r0) goto L3b
            return r0
        L3b:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$1 r5 = new com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$1
            r5.<init>()
            java.util.List r7 = kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(r7, r5)
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$2 r5 = new com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$2
            r5.<init>()
            java.util.List r7 = kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(r7, r5)
            kotlinx.coroutines.flow.StateFlowImpl r1 = (kotlinx.coroutines.flow.StateFlowImpl) r1
            r1.setValue(r7)
            com.android.systemui.user.data.repository.UserRepositoryImpl r7 = r6.this$0
            kotlinx.coroutines.flow.StateFlowImpl r7 = r7._userInfos
            java.lang.Object r7 = r7.getValue()
            java.util.List r7 = (java.util.List) r7
            if (r7 == 0) goto L80
            com.android.systemui.user.data.repository.UserRepositoryImpl r1 = r6.this$0
            boolean r5 = android.os.UserManager.supportsMultipleUsers()
            r5 = r5 ^ r4
            int r7 = r7.size()
            if (r7 <= r5) goto L80
            android.content.Context r7 = r1.appContext
            java.lang.String r1 = "HasSeenMultiUser"
            com.android.systemui.Prefs.putBoolean(r7, r1, r4)
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            java.lang.String r7 = "UserRepository"
            java.lang.String r1 = "refreshUsers: put HasSeenMultiUser as true"
            android.util.Log.d(r7, r1)
        L80:
            com.android.systemui.user.data.repository.UserRepositoryImpl r7 = r6.this$0
            int r1 = r7.mainUserId
            r4 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r1 != r4) goto La6
            com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$mainUser$1 r1 = new com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$mainUser$1
            r1.<init>(r7, r3)
            r6.L$0 = r3
            r6.label = r2
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r1, r6)
            if (r7 != r0) goto L9a
            return r0
        L9a:
            android.os.UserHandle r7 = (android.os.UserHandle) r7
            if (r7 == 0) goto La6
            com.android.systemui.user.data.repository.UserRepositoryImpl r6 = r6.this$0
            int r7 = r7.getIdentifier()
            r6.mainUserId = r7
        La6:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
