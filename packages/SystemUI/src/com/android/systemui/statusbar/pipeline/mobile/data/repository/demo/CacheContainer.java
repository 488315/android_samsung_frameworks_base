package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$Mobile;

public final class CacheContainer {
    public final FakeNetworkEventModel$Mobile lastMobileState;
    public final DemoMobileConnectionRepository repo;

    public CacheContainer(DemoMobileConnectionRepository demoMobileConnectionRepository, FakeNetworkEventModel$Mobile fakeNetworkEventModel$Mobile) {
        this.repo = demoMobileConnectionRepository;
    }
}
