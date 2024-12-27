package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.data.repository.TrustRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class TrustInteractor {
    public final ReadonlyStateFlow isEnrolledAndEnabled;
    public final ReadonlyStateFlow isTrustAgentCurrentlyAllowed;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 isTrusted;

    public TrustInteractor(TrustRepository trustRepository) {
        TrustRepositoryImpl trustRepositoryImpl = (TrustRepositoryImpl) trustRepository;
        this.isEnrolledAndEnabled = trustRepositoryImpl.isCurrentUserTrustUsuallyManaged;
        this.isTrustAgentCurrentlyAllowed = trustRepositoryImpl.isCurrentUserTrustManaged();
        this.isTrusted = trustRepositoryImpl.isCurrentUserTrusted();
    }
}
