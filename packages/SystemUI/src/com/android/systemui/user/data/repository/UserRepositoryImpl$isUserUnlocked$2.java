package com.android.systemui.user.data.repository;

import android.os.UserHandle;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UserRepositoryImpl$isUserUnlocked$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $userHandle;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$isUserUnlocked$2(UserRepositoryImpl userRepositoryImpl, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
        this.$userHandle = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserRepositoryImpl$isUserUnlocked$2 userRepositoryImpl$isUserUnlocked$2 = new UserRepositoryImpl$isUserUnlocked$2(this.this$0, this.$userHandle, continuation);
        userRepositoryImpl$isUserUnlocked$2.L$0 = obj;
        return userRepositoryImpl$isUserUnlocked$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$isUserUnlocked$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004c, code lost:
    
        if (r1.emit(r7, r6) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        if (r7 == r0) goto L15;
     */
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
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4f
        L11:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L19:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L44
        L21:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            r1 = r7
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            com.android.systemui.user.data.repository.UserRepositoryImpl r7 = r6.this$0
            android.os.UserHandle r5 = r6.$userHandle
            r6.L$0 = r1
            r6.label = r4
            int r4 = com.android.systemui.user.data.repository.UserRepositoryImpl.$r8$clinit
            r7.getClass()
            com.android.systemui.user.data.repository.UserRepositoryImpl$getUnlockedState$2 r4 = new com.android.systemui.user.data.repository.UserRepositoryImpl$getUnlockedState$2
            r4.<init>(r5, r7, r2)
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r4, r6)
            if (r7 != r0) goto L44
            goto L4e
        L44:
            r6.L$0 = r2
            r6.label = r3
            java.lang.Object r6 = r1.emit(r7, r6)
            if (r6 != r0) goto L4f
        L4e:
            return r0
        L4f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
