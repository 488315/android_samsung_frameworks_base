package com.android.systemui.user.data.repository;

import android.R;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.data.repository.UserRepositoryImpl$getSettings$2", m277f = "UserRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserRepositoryImpl$getSettings$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$getSettings$2(UserRepositoryImpl userRepositoryImpl, Continuation<? super UserRepositoryImpl$getSettings$2> continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserRepositoryImpl$getSettings$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$getSettings$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UserRepositoryImpl userRepositoryImpl = this.this$0;
        boolean z = userRepositoryImpl.globalSettings.getIntForUser(userRepositoryImpl.appContext.getResources().getBoolean(R.bool.config_enableMultipleAdmins) ? 1 : 0, 0, "lockscreenSimpleUserSwitcher") != 0;
        boolean z2 = this.this$0.globalSettings.getIntForUser(0, 0, "add_users_when_locked") != 0;
        UserRepositoryImpl userRepositoryImpl2 = this.this$0;
        return new UserSwitcherSettingsModel(z, z2, userRepositoryImpl2.globalSettings.getIntForUser(userRepositoryImpl2.appContext.getResources().getBoolean(R.bool.config_preferenceFragmentClipToPadding) ? 1 : 0, 0, "user_switcher_enabled") != 0);
    }
}
