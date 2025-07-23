package com.android.systemui.qs.tiles.base.domain.interactor;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisabledByPolicyInteractorImpl implements DisabledByPolicyInteractor {
    public final ActivityStarter activityStarter;
    public final CoroutineDispatcher backgroundDispatcher;
    public final RestrictedLockProxy restrictedLockProxy;

    public DisabledByPolicyInteractorImpl(Context context, ActivityStarter activityStarter, RestrictedLockProxy restrictedLockProxy, CoroutineDispatcher coroutineDispatcher) {
        this.activityStarter = activityStarter;
        this.restrictedLockProxy = restrictedLockProxy;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object isDisabled(UserHandle userHandle, String str, QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1.AnonymousClass2.AnonymousClass1 anonymousClass1) {
        return BuildersKt.withContext(this.backgroundDispatcher, new DisabledByPolicyInteractorImpl$isDisabled$2(this, userHandle, str, null), anonymousClass1);
    }
}
