package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x009c, code lost:
    
        if (r5.remove(r6, r7, r8, r9, r10, r12) != r0) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        int i2;
        int i3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = this.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            GuestUserInteractor guestUserInteractor = this.this$0;
            UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) guestUserInteractor.repository;
            i = userRepositoryImpl.mainUserId;
            int i5 = this.$targetUserId;
            if (i5 == -10000) {
                int i6 = userRepositoryImpl.lastSelectedNonGuestUserId;
                if (i6 != i) {
                    GuestUserInteractor$exit$1$info$1 guestUserInteractor$exit$1$info$1 = new GuestUserInteractor$exit$1$info$1(guestUserInteractor, i6, null);
                    this.I$0 = i;
                    this.label = 1;
                    obj = BuildersKt.withContext(guestUserInteractor.backgroundDispatcher, guestUserInteractor$exit$1$info$1, this);
                    if (obj != coroutineSingletons) {
                        i3 = i;
                    }
                    return coroutineSingletons;
                }
                i2 = i;
                if (!this.$currentUserInfo.isEphemeral() || this.$forceRemoveGuestOnExit) {
                    this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
                    GuestUserInteractor guestUserInteractor2 = this.this$0;
                    int i7 = this.$currentUserInfo.id;
                    Function1 function1 = this.$showDialog;
                    Function0 function0 = this.$dismissDialog;
                    Function1 function12 = this.$switchUser;
                    this.label = 2;
                } else {
                    this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_SWITCH);
                    this.$switchUser.mo781invoke(new Integer(i2));
                }
                return Unit.INSTANCE;
            }
            i2 = i5;
            if (this.$currentUserInfo.isEphemeral()) {
            }
            this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
            GuestUserInteractor guestUserInteractor22 = this.this$0;
            int i72 = this.$currentUserInfo.id;
            Function1 function13 = this.$showDialog;
            Function0 function02 = this.$dismissDialog;
            Function1 function122 = this.$switchUser;
            this.label = 2;
        } else {
            if (i4 != 1) {
                if (i4 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            i3 = this.I$0;
            ResultKt.throwOnFailure(obj);
        }
        UserInfo userInfo = (UserInfo) obj;
        if (userInfo != null && userInfo.isEnabled() && userInfo.supportsSwitchTo()) {
            i = userInfo.id;
            i2 = i;
            if (this.$currentUserInfo.isEphemeral()) {
            }
            this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
            GuestUserInteractor guestUserInteractor222 = this.this$0;
            int i722 = this.$currentUserInfo.id;
            Function1 function132 = this.$showDialog;
            Function0 function022 = this.$dismissDialog;
            Function1 function1222 = this.$switchUser;
            this.label = 2;
        } else {
            i2 = i3;
            if (this.$currentUserInfo.isEphemeral()) {
            }
            this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
            GuestUserInteractor guestUserInteractor2222 = this.this$0;
            int i7222 = this.$currentUserInfo.id;
            Function1 function1322 = this.$showDialog;
            Function0 function0222 = this.$dismissDialog;
            Function1 function12222 = this.$switchUser;
            this.label = 2;
        }
    }
}
