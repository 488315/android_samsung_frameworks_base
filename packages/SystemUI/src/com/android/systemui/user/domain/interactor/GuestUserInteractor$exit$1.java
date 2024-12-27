package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class GuestUserInteractor$exit$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserInfo $currentUserInfo;
    final /* synthetic */ Function0 $dismissDialog;
    final /* synthetic */ boolean $forceRemoveGuestOnExit;
    final /* synthetic */ Function1 $showDialog;
    final /* synthetic */ Function1 $switchUser;
    final /* synthetic */ int $targetUserId;
    int I$0;
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$exit$1(GuestUserInteractor guestUserInteractor, int i, UserInfo userInfo, boolean z, Function1 function1, Function0 function0, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.this$0 = guestUserInteractor;
        this.$targetUserId = i;
        this.$currentUserInfo = userInfo;
        this.$forceRemoveGuestOnExit = z;
        this.$showDialog = function1;
        this.$dismissDialog = function0;
        this.$switchUser = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GuestUserInteractor$exit$1(this.this$0, this.$targetUserId, this.$currentUserInfo, this.$forceRemoveGuestOnExit, this.$showDialog, this.$dismissDialog, this.$switchUser, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GuestUserInteractor$exit$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a6 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1f
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r13)
            goto La7
        L11:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L19:
            int r1 = r12.I$0
            kotlin.ResultKt.throwOnFailure(r13)
            goto L50
        L1f:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.user.domain.interactor.GuestUserInteractor r13 = r12.this$0
            com.android.systemui.user.data.repository.UserRepository r1 = r13.repository
            r4 = r1
            com.android.systemui.user.data.repository.UserRepositoryImpl r4 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r4
            int r4 = r4.mainUserId
            int r5 = r12.$targetUserId
            r6 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r5 != r6) goto L66
            r5 = r1
            com.android.systemui.user.data.repository.UserRepositoryImpl r5 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r5
            int r5 = r5.lastSelectedNonGuestUserId
            com.android.systemui.user.data.repository.UserRepositoryImpl r1 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r1
            int r1 = r1.mainUserId
            if (r5 == r1) goto L62
            kotlinx.coroutines.CoroutineDispatcher r1 = r13.backgroundDispatcher
            com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1$info$1 r6 = new com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1$info$1
            r7 = 0
            r6.<init>(r13, r5, r7)
            r12.I$0 = r4
            r12.label = r3
            java.lang.Object r13 = kotlinx.coroutines.BuildersKt.withContext(r1, r6, r12)
            if (r13 != r0) goto L4f
            return r0
        L4f:
            r1 = r4
        L50:
            android.content.pm.UserInfo r13 = (android.content.pm.UserInfo) r13
            if (r13 == 0) goto L64
            boolean r3 = r13.isEnabled()
            if (r3 == 0) goto L64
            boolean r3 = r13.supportsSwitchTo()
            if (r3 == 0) goto L64
            int r4 = r13.id
        L62:
            r7 = r4
            goto L67
        L64:
            r7 = r1
            goto L67
        L66:
            r7 = r5
        L67:
            android.content.pm.UserInfo r13 = r12.$currentUserInfo
            boolean r13 = r13.isEphemeral()
            if (r13 != 0) goto L88
            boolean r13 = r12.$forceRemoveGuestOnExit
            if (r13 == 0) goto L74
            goto L88
        L74:
            com.android.systemui.user.domain.interactor.GuestUserInteractor r13 = r12.this$0
            com.android.internal.logging.UiEventLogger r13 = r13.uiEventLogger
            com.android.systemui.qs.QSUserSwitcherEvent r0 = com.android.systemui.qs.QSUserSwitcherEvent.QS_USER_SWITCH
            r13.log(r0)
            kotlin.jvm.functions.Function1 r12 = r12.$switchUser
            java.lang.Integer r13 = new java.lang.Integer
            r13.<init>(r7)
            r12.invoke(r13)
            goto La7
        L88:
            com.android.systemui.user.domain.interactor.GuestUserInteractor r13 = r12.this$0
            com.android.internal.logging.UiEventLogger r13 = r13.uiEventLogger
            com.android.systemui.qs.QSUserSwitcherEvent r1 = com.android.systemui.qs.QSUserSwitcherEvent.QS_USER_GUEST_REMOVE
            r13.log(r1)
            com.android.systemui.user.domain.interactor.GuestUserInteractor r5 = r12.this$0
            android.content.pm.UserInfo r13 = r12.$currentUserInfo
            int r6 = r13.id
            kotlin.jvm.functions.Function1 r8 = r12.$showDialog
            kotlin.jvm.functions.Function0 r9 = r12.$dismissDialog
            kotlin.jvm.functions.Function1 r10 = r12.$switchUser
            r12.label = r2
            r11 = r12
            java.lang.Object r12 = r5.remove(r6, r7, r8, r9, r10, r11)
            if (r12 != r0) goto La7
            return r0
        La7:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
