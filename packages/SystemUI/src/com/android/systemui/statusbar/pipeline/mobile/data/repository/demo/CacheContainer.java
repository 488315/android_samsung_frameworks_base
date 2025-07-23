package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CacheContainer {
    public final FakeNetworkEventModel.Mobile lastMobileState;
    public final DemoMobileConnectionRepository repo;

    public CacheContainer(DemoMobileConnectionRepository demoMobileConnectionRepository, FakeNetworkEventModel.Mobile mobile) {
        this.repo = demoMobileConnectionRepository;
        this.lastMobileState = mobile;
    }
}
