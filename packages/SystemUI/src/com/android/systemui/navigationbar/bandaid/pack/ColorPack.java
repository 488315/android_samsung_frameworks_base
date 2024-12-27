package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ColorPack implements BandAidPack {
    public final List allBands;

    public ColorPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_LIGHTBAR;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.COLOR_PACK_REEVALUATE_NAVBAR;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnLightBarControllerCreated.class);
        builder.targetModules = Collections.singletonList(NavigationBar.class);
        builder.moduleDependencies = Collections.singletonList(LightBarController.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ColorPack$band$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = z;
        m.bandAidDependency = BandAid.COLOR_PACK_ON_UPDATE_REGION_SAMPLING_LISTENER;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateRegionSamplingListener.class);
        m.targetModules = Collections.singletonList(NavigationBar.class);
        m.moduleDependencies = Collections.singletonList(LightBarController.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ColorPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (!((EventTypeFactory.EventType.OnUpdateRegionSamplingListener) kit.event).registered) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateNavigationIcon(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        arrayList.add(m.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
