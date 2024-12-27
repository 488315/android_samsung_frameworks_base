package com.android.systemui.user.domain.interactor;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class UserSwitcherInteractor$getUserImage$userIcon$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

    public UserSwitcherInteractor$getUserImage$userIcon$1(UserSwitcherInteractor userSwitcherInteractor, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherInteractor;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherInteractor$getUserImage$userIcon$1(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherInteractor$getUserImage$userIcon$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Bitmap userIcon = this.this$0.manager.getUserIcon(this.$userId);
        if (userIcon == null) {
            return null;
        }
        int dimensionPixelSize = this.this$0.applicationContext.getResources().getDimensionPixelSize(R.dimen.bouncer_user_switcher_icon_size);
        return Icon.scaleDownIfNecessary(userIcon, dimensionPixelSize, dimensionPixelSize);
    }
}
