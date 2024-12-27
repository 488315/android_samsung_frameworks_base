package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import dagger.Lazy;

public final class DozeInteractor {
    public final KeyguardRepository keyguardRepository;

    public DozeInteractor(KeyguardRepository keyguardRepository, Lazy lazy) {
        this.keyguardRepository = keyguardRepository;
    }
}
