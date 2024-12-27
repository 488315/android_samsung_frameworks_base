package com.android.systemui.navigationbar.bandaid;

import com.android.systemui.navigationbar.bandaid.pack.ColorPack;
import com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack;
import com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack;
import com.android.systemui.navigationbar.bandaid.pack.GesturePack;
import com.android.systemui.navigationbar.bandaid.pack.MiscPack;
import com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack;
import com.android.systemui.navigationbar.bandaid.pack.SetupWizardPack;
import com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack;
import com.android.systemui.navigationbar.bandaid.pack.TaskBarPack;
import com.android.systemui.navigationbar.bandaid.pack.VisibilityPack;
import com.android.systemui.navigationbar.store.NavBarStore;
import java.util.ArrayList;
import java.util.List;

public final class BandAidPackFactory implements BandAidPackFactoryBase {
    public final List packs = new ArrayList();

    public final List getPacks(NavBarStore navBarStore) {
        if (((ArrayList) this.packs).isEmpty()) {
            ((ArrayList) this.packs).add(new StableLayoutPack(navBarStore));
            ((ArrayList) this.packs).add(new ConfigurationPack(navBarStore));
            ((ArrayList) this.packs).add(new RemoteViewPack(navBarStore));
            ((ArrayList) this.packs).add(new SetupWizardPack(navBarStore));
            ((ArrayList) this.packs).add(new VisibilityPack(navBarStore));
            ((ArrayList) this.packs).add(new GesturePack(navBarStore));
            ((ArrayList) this.packs).add(new MiscPack(navBarStore));
            ((ArrayList) this.packs).add(new ColorPack(navBarStore));
            ((ArrayList) this.packs).add(new TaskBarPack(navBarStore));
            ((ArrayList) this.packs).add(new CoverScreenPack(navBarStore));
        }
        return new ArrayList(this.packs);
    }
}
