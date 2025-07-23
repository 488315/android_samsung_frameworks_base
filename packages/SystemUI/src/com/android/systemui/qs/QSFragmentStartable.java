package com.android.systemui.qs;

import com.android.systemui.CoreStartable;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.qs.composefragment.QSFragmentCompose;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSFragmentStartable implements CoreStartable {
    public final FragmentService fragmentService;
    public final Provider qsFragmentComposeProvider;
    public final Provider qsFragmentLegacyProvider;

    public QSFragmentStartable(FragmentService fragmentService, Provider provider, Provider provider2) {
        this.fragmentService = fragmentService;
        this.qsFragmentLegacyProvider = provider;
        this.qsFragmentComposeProvider = provider2;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        FragmentService fragmentService = this.fragmentService;
        fragmentService.addFragmentInstantiationProvider(QSFragmentLegacy.class, this.qsFragmentLegacyProvider);
        fragmentService.addFragmentInstantiationProvider(QSFragmentCompose.class, this.qsFragmentComposeProvider);
    }
}
