package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x009c, code lost:
    
        if (r5.remove(r6, r7, r8, r9, r10, r12) != r0) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0067  */
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
            goto L9f
        L11:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L19:
            int r1 = r12.I$0
            kotlin.ResultKt.throwOnFailure(r13)
            goto L48
        L1f:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.user.domain.interactor.GuestUserInteractor r13 = r12.this$0
            com.android.systemui.user.data.repository.UserRepository r1 = r13.repository
            com.android.systemui.user.data.repository.UserRepositoryImpl r1 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r1
            int r4 = r1.mainUserId
            int r5 = r12.$targetUserId
            r6 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r5 != r6) goto L5e
            int r1 = r1.lastSelectedNonGuestUserId
            if (r1 == r4) goto L5a
            com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1$info$1 r5 = new com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1$info$1
            r6 = 0
            r5.<init>(r13, r1, r6)
            r12.I$0 = r4
            r12.label = r3
            kotlinx.coroutines.CoroutineDispatcher r13 = r13.backgroundDispatcher
            java.lang.Object r13 = kotlinx.coroutines.BuildersKt.withContext(r13, r5, r12)
            if (r13 != r0) goto L47
            goto L9e
        L47:
            r1 = r4
        L48:
            android.content.pm.UserInfo r13 = (android.content.pm.UserInfo) r13
            if (r13 == 0) goto L5c
            boolean r3 = r13.isEnabled()
            if (r3 == 0) goto L5c
            boolean r3 = r13.supportsSwitchTo()
            if (r3 == 0) goto L5c
            int r4 = r13.id
        L5a:
            r7 = r4
            goto L5f
        L5c:
            r7 = r1
            goto L5f
        L5e:
            r7 = r5
        L5f:
            android.content.pm.UserInfo r13 = r12.$currentUserInfo
            boolean r13 = r13.isEphemeral()
            if (r13 != 0) goto L80
            boolean r13 = r12.$forceRemoveGuestOnExit
            if (r13 == 0) goto L6c
            goto L80
        L6c:
            com.android.systemui.user.domain.interactor.GuestUserInteractor r13 = r12.this$0
            com.android.internal.logging.UiEventLogger r13 = r13.uiEventLogger
            com.android.systemui.qs.QSUserSwitcherEvent r0 = com.android.systemui.qs.QSUserSwitcherEvent.QS_USER_SWITCH
            r13.log(r0)
            kotlin.jvm.functions.Function1 r12 = r12.$switchUser
            java.lang.Integer r13 = new java.lang.Integer
            r13.<init>(r7)
            r12.mo779invoke(r13)
            goto L9f
        L80:
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
            if (r12 != r0) goto L9f
        L9e:
            return r0
        L9f:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
