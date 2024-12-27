package com.android.systemui.qs.tiles.base.interactor;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$9$$inlined$filter$1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

public final class DisabledByPolicyInteractorImpl implements DisabledByPolicyInteractor {
    public final ActivityStarter activityStarter;
    public final CoroutineDispatcher backgroundDispatcher;
    public final RestrictedLockProxy restrictedLockProxy;

    public DisabledByPolicyInteractorImpl(Context context, ActivityStarter activityStarter, RestrictedLockProxy restrictedLockProxy, CoroutineDispatcher coroutineDispatcher) {
        this.activityStarter = activityStarter;
        this.restrictedLockProxy = restrictedLockProxy;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object isDisabled(UserHandle userHandle, String str, QSTileViewModelImpl$filterByPolicy$lambda$9$$inlined$filter$1.AnonymousClass2.AnonymousClass1 anonymousClass1) {
        return BuildersKt.withContext(this.backgroundDispatcher, new DisabledByPolicyInteractorImpl$isDisabled$2(this, userHandle, str, null), anonymousClass1);
    }
}
