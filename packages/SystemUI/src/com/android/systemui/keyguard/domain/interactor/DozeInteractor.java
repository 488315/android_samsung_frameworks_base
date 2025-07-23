package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.power.data.repository.PowerRepository;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DozeInteractor {
    public final KeyguardRepository keyguardRepository;

    public DozeInteractor(KeyguardRepository keyguardRepository, PowerRepository powerRepository, Lazy lazy) {
        this.keyguardRepository = keyguardRepository;
    }
}
