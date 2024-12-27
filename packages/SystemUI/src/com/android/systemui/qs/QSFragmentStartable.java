package com.android.systemui.qs;

import com.android.systemui.CoreStartable;
import com.android.systemui.fragments.FragmentService;
import javax.inject.Provider;

public final class QSFragmentStartable implements CoreStartable {
    public final FragmentService fragmentService;
    public final Provider qsFragmentLegacyProvider;

    public QSFragmentStartable(FragmentService fragmentService, Provider provider) {
        this.fragmentService = fragmentService;
        this.qsFragmentLegacyProvider = provider;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.fragmentService.addFragmentInstantiationProvider(QSFragmentLegacy.class, this.qsFragmentLegacyProvider);
    }
}
