package com.android.systemui.statusbar.phone.fragment;

import com.android.systemui.CoreStartable;
import com.android.systemui.fragments.FragmentService;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
