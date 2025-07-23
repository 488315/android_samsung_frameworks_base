package com.android.systemui.statusbar.disableflags.domain.interactor;

import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepository;
import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisableFlagsInteractor {
    public final ReadonlyStateFlow disableFlags;

    public DisableFlagsInteractor(DisableFlagsRepository disableFlagsRepository) {
        this.disableFlags = ((DisableFlagsRepositoryImpl) disableFlagsRepository).disableFlags;
    }
}
