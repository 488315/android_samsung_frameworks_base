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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BandAidPackFactory implements BandAidPackFactoryBase {
    public final List packs = new ArrayList();

    public final List getPacks(NavBarStore navBarStore) {
        List list = this.packs;
        if (((ArrayList) list).isEmpty()) {
            ((ArrayList) list).add(new StableLayoutPack(navBarStore));
            ((ArrayList) list).add(new ConfigurationPack(navBarStore));
            ((ArrayList) list).add(new RemoteViewPack(navBarStore));
            ((ArrayList) list).add(new SetupWizardPack(navBarStore));
            ((ArrayList) list).add(new VisibilityPack(navBarStore));
            ((ArrayList) list).add(new GesturePack(navBarStore));
            ((ArrayList) list).add(new MiscPack(navBarStore));
            ((ArrayList) list).add(new ColorPack(navBarStore));
            ((ArrayList) list).add(new TaskBarPack(navBarStore));
            ((ArrayList) list).add(new CoverScreenPack(navBarStore));
        }
        return new ArrayList(list);
    }
}
