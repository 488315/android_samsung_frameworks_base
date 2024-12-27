package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UserSwitcherInteractor$removeGuestUser$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $guestUserId;
    final /* synthetic */ int $targetUserId;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass1(Object obj) {
            super(1, obj, UserSwitcherInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.showDialog((ShowDialogRequestModel) obj);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass2(Object obj) {
            super(0, obj, UserSwitcherInteractor.class, "dismissDialog", "dismissDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.dismissDialog();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass3(Object obj) {
            super(1, obj, UserSwitcherInteractor.class, "switchUser", "switchUser(I)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            int intValue = ((Number) obj).intValue();
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.switchUser(intValue);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$removeGuestUser$1(UserSwitcherInteractor userSwitcherInteractor, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherInteractor;
        this.$guestUserId = i;
        this.$targetUserId = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherInteractor$removeGuestUser$1(this.this$0, this.$guestUserId, this.$targetUserId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherInteractor$removeGuestUser$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            GuestUserInteractor guestUserInteractor = this.this$0.guestUserInteractor;
            int i2 = this.$guestUserId;
            int i3 = this.$targetUserId;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0);
            this.label = 1;
            if (guestUserInteractor.remove(i2, i3, anonymousClass1, anonymousClass2, anonymousClass3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
