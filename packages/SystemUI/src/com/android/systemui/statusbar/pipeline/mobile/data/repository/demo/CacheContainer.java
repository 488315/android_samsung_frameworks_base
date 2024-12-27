package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$Mobile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CacheContainer {
    public final FakeNetworkEventModel$Mobile lastMobileState;
    public final DemoMobileConnectionRepository repo;

    public CacheContainer(DemoMobileConnectionRepository demoMobileConnectionRepository, FakeNetworkEventModel$Mobile fakeNetworkEventModel$Mobile) {
        this.repo = demoMobileConnectionRepository;
    }
}
