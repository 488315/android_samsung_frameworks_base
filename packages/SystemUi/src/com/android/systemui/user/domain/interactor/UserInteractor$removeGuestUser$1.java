package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$removeGuestUser$1", m277f = "UserInteractor.kt", m278l = {521}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserInteractor$removeGuestUser$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $guestUserId;
    final /* synthetic */ int $targetUserId;
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$removeGuestUser$1$1 */
    final /* synthetic */ class C35561 extends FunctionReferenceImpl implements Function1 {
        public C35561(Object obj) {
            super(1, obj, UserInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            UserInteractor userInteractor = (UserInteractor) this.receiver;
            int i = UserInteractor.$r8$clinit;
            userInteractor.showDialog((ShowDialogRequestModel) obj);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$removeGuestUser$1$2 */
    final /* synthetic */ class C35572 extends FunctionReferenceImpl implements Function0 {
        public C35572(Object obj) {
            super(0, obj, UserInteractor.class, "dismissDialog", "dismissDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((UserInteractor) this.receiver).dismissDialog();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$removeGuestUser$1$3 */
    final /* synthetic */ class C35583 extends AdaptedFunctionReference implements Function1 {
        public C35583(Object obj) {
            super(1, obj, UserInteractor.class, "selectUser", "selectUser(ILcom/android/systemui/qs/user/UserSwitchDialogController$DialogShower;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            int intValue = ((Number) obj).intValue();
            UserInteractor userInteractor = (UserInteractor) this.receiver;
            int i = UserInteractor.$r8$clinit;
            userInteractor.selectUser(intValue, null);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$removeGuestUser$1(UserInteractor userInteractor, int i, int i2, Continuation<? super UserInteractor$removeGuestUser$1> continuation) {
        super(2, continuation);
        this.this$0 = userInteractor;
        this.$guestUserId = i;
        this.$targetUserId = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserInteractor$removeGuestUser$1(this.this$0, this.$guestUserId, this.$targetUserId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserInteractor$removeGuestUser$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            C35561 c35561 = new C35561(this.this$0);
            C35572 c35572 = new C35572(this.this$0);
            C35583 c35583 = new C35583(this.this$0);
            this.label = 1;
            if (guestUserInteractor.remove(i2, i3, c35561, c35572, c35583, this) == coroutineSingletons) {
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
