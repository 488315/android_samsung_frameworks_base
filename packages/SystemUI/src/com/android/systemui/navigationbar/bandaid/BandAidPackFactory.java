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
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class BandAidPackFactory implements BandAidPackFactoryBase {
    public final List packs = new ArrayList();

    public final List getPacks(NavBarStoreImpl navBarStoreImpl) {
        if (((ArrayList) this.packs).isEmpty()) {
            ((ArrayList) this.packs).add(new StableLayoutPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new ConfigurationPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new RemoteViewPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new SetupWizardPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new VisibilityPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new GesturePack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new MiscPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new ColorPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new TaskBarPack(navBarStoreImpl));
            ((ArrayList) this.packs).add(new CoverScreenPack(navBarStoreImpl));
        }
        return new ArrayList(this.packs);
    }
}
