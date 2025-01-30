package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.p016qs.QSUserSwitcherEvent;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1", m277f = "GuestUserInteractor.kt", m278l = {148, 161}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
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
    public GuestUserInteractor$exit$1(GuestUserInteractor guestUserInteractor, int i, UserInfo userInfo, boolean z, Function1 function1, Function0 function0, Function1 function12, Continuation<? super GuestUserInteractor$exit$1> continuation) {
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a7 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int i;
        int i2;
        int i3;
        GuestUserInteractor guestUserInteractor;
        int i4;
        Function1 function1;
        Function0 function0;
        Function1 function12;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i5 = this.label;
        if (i5 == 0) {
            ResultKt.throwOnFailure(obj);
            GuestUserInteractor guestUserInteractor2 = this.this$0;
            UserRepository userRepository = guestUserInteractor2.repository;
            i = ((UserRepositoryImpl) userRepository).mainUserId;
            int i6 = this.$targetUserId;
            if (i6 != -10000) {
                i2 = i6;
                if (this.$currentUserInfo.isEphemeral()) {
                }
                this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
                guestUserInteractor = this.this$0;
                i4 = this.$currentUserInfo.id;
                function1 = this.$showDialog;
                function0 = this.$dismissDialog;
                function12 = this.$switchUser;
                this.label = 2;
                if (guestUserInteractor.remove(i4, i2, function1, function0, function12, this) == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            }
            int i7 = ((UserRepositoryImpl) userRepository).lastSelectedNonGuestUserId;
            if (i7 != ((UserRepositoryImpl) userRepository).mainUserId) {
                CoroutineDispatcher coroutineDispatcher = guestUserInteractor2.backgroundDispatcher;
                GuestUserInteractor$exit$1$info$1 guestUserInteractor$exit$1$info$1 = new GuestUserInteractor$exit$1$info$1(guestUserInteractor2, i7, null);
                this.I$0 = i;
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, guestUserInteractor$exit$1$info$1, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i3 = i;
            }
            i2 = i;
            if (!this.$currentUserInfo.isEphemeral() || this.$forceRemoveGuestOnExit) {
                this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
                guestUserInteractor = this.this$0;
                i4 = this.$currentUserInfo.id;
                function1 = this.$showDialog;
                function0 = this.$dismissDialog;
                function12 = this.$switchUser;
                this.label = 2;
                if (guestUserInteractor.remove(i4, i2, function1, function0, function12, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_SWITCH);
                this.$switchUser.invoke(new Integer(i2));
            }
            return Unit.INSTANCE;
        }
        if (i5 != 1) {
            if (i5 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        i3 = this.I$0;
        ResultKt.throwOnFailure(obj);
        UserInfo userInfo = (UserInfo) obj;
        if (userInfo == null || !userInfo.isEnabled() || !userInfo.supportsSwitchTo()) {
            i2 = i3;
            if (this.$currentUserInfo.isEphemeral()) {
            }
            this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
            guestUserInteractor = this.this$0;
            i4 = this.$currentUserInfo.id;
            function1 = this.$showDialog;
            function0 = this.$dismissDialog;
            function12 = this.$switchUser;
            this.label = 2;
            if (guestUserInteractor.remove(i4, i2, function1, function0, function12, this) == coroutineSingletons) {
            }
            return Unit.INSTANCE;
        }
        i = userInfo.id;
        i2 = i;
        if (this.$currentUserInfo.isEphemeral()) {
        }
        this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
        guestUserInteractor = this.this$0;
        i4 = this.$currentUserInfo.id;
        function1 = this.$showDialog;
        function0 = this.$dismissDialog;
        function12 = this.$switchUser;
        this.label = 2;
        if (guestUserInteractor.remove(i4, i2, function1, function0, function12, this) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
