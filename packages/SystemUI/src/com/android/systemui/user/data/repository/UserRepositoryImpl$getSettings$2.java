package com.android.systemui.user.data.repository;

import android.R;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.util.SettingsHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class UserRepositoryImpl$getSettings$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    public UserRepositoryImpl$getSettings$2(UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
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
        boolean z = userRepositoryImpl.globalSettings.getInt("lockscreenSimpleUserSwitcher", userRepositoryImpl.appContext.getResources().getBoolean(R.bool.config_freeformWindowManagement) ? 1 : 0) != 0;
        boolean z2 = this.this$0.globalSettings.getInt("add_users_when_locked", 0) != 0;
        UserRepositoryImpl userRepositoryImpl2 = this.this$0;
        return new UserSwitcherSettingsModel(z, z2, userRepositoryImpl2.globalSettings.getInt(SettingsHelper.INDEX_USER_SWITCHER_ENABLED, userRepositoryImpl2.appContext.getResources().getBoolean(R.bool.config_spatial_audio_head_tracking_enabled_default) ? 1 : 0) != 0);
    }
}
