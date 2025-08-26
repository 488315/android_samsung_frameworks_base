package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.power.data.repository.PowerRepository;
import dagger.Lazy;

/* loaded from: classes2.dex */
public final class DozeInteractor {
    public final KeyguardRepository keyguardRepository;

    public DozeInteractor(KeyguardRepository keyguardRepository, PowerRepository powerRepository, Lazy lazy) {
        this.keyguardRepository = keyguardRepository;
    }
}
