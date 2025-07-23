package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.data.repository.TrustRepositoryImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TrustInteractor {
    public final CoroutineScope applicationScope;
    public final ReadonlyStateFlow isEnrolledAndEnabled;
    public final ReadonlyStateFlow isTrustAgentCurrentlyAllowed;
    public final ReadonlyStateFlow isTrusted;
    public final TrustRepository repository;

    public TrustInteractor(CoroutineScope coroutineScope, TrustRepository trustRepository) {
        this.applicationScope = coroutineScope;
        this.repository = trustRepository;
        TrustRepositoryImpl trustRepositoryImpl = (TrustRepositoryImpl) trustRepository;
        this.isEnrolledAndEnabled = trustRepositoryImpl.isCurrentUserTrustUsuallyManaged;
        this.isTrustAgentCurrentlyAllowed = trustRepositoryImpl.isCurrentUserTrustManaged();
        this.isTrusted = trustRepositoryImpl.isCurrentUserTrusted();
    }
}
