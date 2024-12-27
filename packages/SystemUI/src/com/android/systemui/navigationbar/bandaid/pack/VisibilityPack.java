package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarFrame;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.phone.LightBarController;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class VisibilityPack implements BandAidPack {
    public final List allBands;

    public VisibilityPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        builder.bandAidDependency = BandAid.VIS_PACK_UPDATE_NAVBAR_VISIBILITY;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateNavBarVisibility.class);
        builder.targetModules = Collections.singletonList(NavigationBarFrame.class);
        builder.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarGoneStateFlag(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((EventTypeFactory.EventType.OnUpdateNavBarVisibility) kit.event).visibility, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        boolean z = BasicRune.NAVBAR_KNOX_MONITOR;
        m.runeDependency = z;
        BandAid bandAid = BandAid.VIS_PACK_UPDATE_NAVBAR_VISIBILITY_BY_KNOX;
        m.bandAidDependency = bandAid;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged.class);
        m.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((NavBarStateManagerImpl) kit.manager).isNavBarHiddenByKnox() ? 8 : 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.runeDependency = z;
        m2.bandAidDependency = bandAid;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        m2.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m2.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (((NavBarStateManagerImpl) kit.manager).isNavBarHiddenByKnox()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 8, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.bandAidDependency = BandAid.VIS_PACK_REEVAULATE_NAVBAR;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarIconHintChanged.class);
        m3.targetModules = Collections.singletonList(NavigationBar.class);
        m3.moduleDependencies = Collections.singletonList(LightBarController.class);
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m4 = ColorPack$$ExternalSyntheticOutline0.m(m3, arrayList);
        m4.bandAidDependency = BandAid.VIS_PACK_UPDATE_SYSTEMUI_STATE_FLAG;
        m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSysUiStateFlag.class);
        m4.targetModules = Collections.singletonList(OverviewProxyService.class);
        m4.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_MUSIC, ((NavigationBarView) navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId)).getRootView().getVisibility() == 8));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m4.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
