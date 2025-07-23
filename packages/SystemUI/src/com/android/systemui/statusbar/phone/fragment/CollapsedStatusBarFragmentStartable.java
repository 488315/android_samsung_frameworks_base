package com.android.systemui.statusbar.phone.fragment;

import com.android.systemui.CoreStartable;
import com.android.systemui.fragments.FragmentService;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CollapsedStatusBarFragmentStartable implements CoreStartable {
    public final Provider collapsedstatusBarFragmentProvider;
    public final FragmentService fragmentService;

    public CollapsedStatusBarFragmentStartable(FragmentService fragmentService, Provider provider) {
        this.fragmentService = fragmentService;
        this.collapsedstatusBarFragmentProvider = provider;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.fragmentService.addFragmentInstantiationProvider(CollapsedStatusBarFragment.class, this.collapsedstatusBarFragmentProvider);
    }
}
