package com.android.systemui.user.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserLogoutInteractor {
    public final CoroutineScope applicationScope;
    public final ReadonlyStateFlow isLogoutEnabled;
    public final UserRepository userRepository;

    public UserLogoutInteractor(UserRepository userRepository, CoroutineScope coroutineScope) {
        this.userRepository = userRepository;
        this.applicationScope = coroutineScope;
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userRepositoryImpl.isSecondaryUserLogoutEnabled, userRepositoryImpl.isLogoutToSystemUserEnabled, UserLogoutInteractor$isLogoutEnabled$1.INSTANCE);
        SharingStarted.Companion.getClass();
        this.isLogoutEnabled = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    public final void logOut() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new UserLogoutInteractor$logOut$1(this, null), 7);
    }
}
