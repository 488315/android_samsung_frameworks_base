package com.android.systemui.bixby2.interactor;

import android.content.Context;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShareViaActionInteractor_Factory implements Provider {
    private final Provider contextProvider;

    public ShareViaActionInteractor_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static ShareViaActionInteractor_Factory create(Provider provider) {
        return new ShareViaActionInteractor_Factory(provider);
    }

    public static ShareViaActionInteractor newInstance(Context context) {
        return new ShareViaActionInteractor(context);
    }

    @Override // javax.inject.Provider
    public ShareViaActionInteractor get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
