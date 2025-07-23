package com.android.systemui.user.data.repository;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UserSwitcherRepositoryImpl$isEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$isEnabled$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r5v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object invokeSuspend$updateState(kotlinx.coroutines.channels.ProducerScope r5, com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$updateState$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 r0 = (com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 r0 = new com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$updateState$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            java.lang.Object r6 = r0.L$0
            com.android.systemui.common.coroutine.ChannelExt r6 = (com.android.systemui.common.coroutine.ChannelExt) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L59
        L2f:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L37:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.common.coroutine.ChannelExt r7 = com.android.systemui.common.coroutine.ChannelExt.INSTANCE
            r0.L$0 = r7
            r0.L$1 = r5
            r0.label = r3
            int r2 = com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl.$r8$clinit
            r6.getClass()
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2 r2 = new com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2
            r3 = 0
            r2.<init>(r6, r3)
            kotlinx.coroutines.CoroutineDispatcher r6 = r6.bgDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r2, r0)
            if (r6 != r1) goto L56
            return r1
        L56:
            r4 = r7
            r7 = r6
            r6 = r4
        L59:
            java.lang.String r0 = "UserSwitcherRepositoryImpl"
            com.android.systemui.common.coroutine.ChannelExt.trySendWithFailureLogging$default(r6, r5, r7, r0)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend$updateState(kotlinx.coroutines.channels.ProducerScope, com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserSwitcherRepositoryImpl$isEnabled$1 userSwitcherRepositoryImpl$isEnabled$1 = new UserSwitcherRepositoryImpl$isEnabled$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$isEnabled$1.L$0 = obj;
        return userSwitcherRepositoryImpl$isEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$isEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0067, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r11, r10) == r0) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L6a
        L10:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L18:
            java.lang.Object r1 = r10.L$1
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1 r1 = (com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1) r1
            java.lang.Object r3 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r11)
            goto L57
        L24:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            r5 = r11
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl r11 = r10.this$0
            com.android.systemui.util.settings.GlobalSettings r7 = r11.globalSetting
            com.android.systemui.user.data.repository.UserRepository r1 = r11.userRepository
            com.android.systemui.user.data.repository.UserRepositoryImpl r1 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r1
            android.content.pm.UserInfo r1 = r1.getSelectedUserInfo()
            int r9 = r1.id
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1 r4 = new com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl r6 = r10.this$0
            android.os.Handler r8 = r11.bgHandler
            r4.<init>(r7, r8, r9)
            r4.setListening(r3)
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl r11 = r10.this$0
            r10.L$0 = r5
            r10.L$1 = r4
            r10.label = r3
            java.lang.Object r11 = invokeSuspend$updateState(r5, r11, r10)
            if (r11 != r0) goto L55
            goto L69
        L55:
            r1 = r4
            r3 = r5
        L57:
            com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0 r11 = new com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0
            r11.<init>()
            r1 = 0
            r10.L$0 = r1
            r10.L$1 = r1
            r10.label = r2
            java.lang.Object r10 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r11, r10)
            if (r10 != r0) goto L6a
        L69:
            return r0
        L6a:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
