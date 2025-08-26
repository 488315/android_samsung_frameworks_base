package com.android.systemui.statusbar.disableflags.domain.interactor;

import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepository;
import com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class DisableFlagsInteractor {
    public final ReadonlyStateFlow disableFlags;

    public DisableFlagsInteractor(DisableFlagsRepository disableFlagsRepository) {
        this.disableFlags = ((DisableFlagsRepositoryImpl) disableFlagsRepository).disableFlags;
    }
}
