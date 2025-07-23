package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.NavBarStore;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SPluginPack implements BandAidPack {
    public final List allBands = new ArrayList();

    public SPluginPack(NavBarStore navBarStore) {
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
