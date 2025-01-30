package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarFragmentModule_ProvideOperatorFrameNameViewFactory implements Provider {
    public final Provider viewProvider;

    public StatusBarFragmentModule_ProvideOperatorFrameNameViewFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static Optional provideOperatorFrameNameView(PhoneStatusBarView phoneStatusBarView) {
        Optional ofNullable = Optional.ofNullable(phoneStatusBarView.findViewById(R.id.carrier_logo_wrap_for_hun));
        Preconditions.checkNotNullFromProvides(ofNullable);
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideOperatorFrameNameView((PhoneStatusBarView) this.viewProvider.get());
    }
}
