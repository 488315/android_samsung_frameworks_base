package com.android.systemui.statusbar.data.repository;

import com.android.systemui.display.data.repository.PerDisplayStore;
import com.android.systemui.display.data.repository.SingleDisplayStore;
import com.android.systemui.statusbar.events.PrivacyDotViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleDisplayPrivacyDotViewControllerStore implements PrivacyDotViewControllerStore, PerDisplayStore {
    public final /* synthetic */ SingleDisplayStore $$delegate_0;

    public SingleDisplayPrivacyDotViewControllerStore(PrivacyDotViewController privacyDotViewController) {
        this.$$delegate_0 = new SingleDisplayStore(privacyDotViewController);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return (PrivacyDotViewController) this.$$delegate_0.defaultDisplay;
    }
}
