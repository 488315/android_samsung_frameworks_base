package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.NavBarStore;
import java.util.ArrayList;
import java.util.List;

public final class SPluginPack implements BandAidPack {
    public final List allBands = new ArrayList();

    public SPluginPack(NavBarStore navBarStore) {
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
