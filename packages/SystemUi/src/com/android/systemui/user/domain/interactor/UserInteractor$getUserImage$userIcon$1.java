package com.android.systemui.user.domain.interactor;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$getUserImage$userIcon$1", m277f = "UserInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserInteractor$getUserImage$userIcon$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$getUserImage$userIcon$1(UserInteractor userInteractor, int i, Continuation<? super UserInteractor$getUserImage$userIcon$1> continuation) {
        super(2, continuation);
        this.this$0 = userInteractor;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserInteractor$getUserImage$userIcon$1(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserInteractor$getUserImage$userIcon$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
