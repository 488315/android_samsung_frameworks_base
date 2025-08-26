package com.android.systemui.navigationbar.bandaid.pack;

import android.content.res.Resources;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarFrame;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.statusbar.phone.LightBarController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;

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
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarGoneStateFlag(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((EventTypeFactory.EventType.OnUpdateNavBarVisibility) kit.event).visibility, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        boolean z = BasicRune.NAVBAR_KNOX_MONITOR;
        builderM.runeDependency = z;
        BandAid bandAid = BandAid.VIS_PACK_UPDATE_NAVBAR_VISIBILITY_BY_KNOX;
        builderM.bandAidDependency = bandAid;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged.class);
        builderM.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((NavBarStateManagerImpl) kit.manager).isNavBarHiddenByKnox() ? 8 : 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.runeDependency = z;
        builderM2.bandAidDependency = bandAid;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        builderM2.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM2.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isNavBarHiddenByKnox()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 8, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.bandAidDependency = BandAid.VIS_PACK_REEVAULATE_NAVBAR;
        builderM3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarIconHintChanged.class);
        builderM3.targetModules = Collections.singletonList(NavigationBar.class);
        builderM3.moduleDependencies = Collections.singletonList(LightBarController.class);
        builderM3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.bandAidDependency = BandAid.VIS_PACK_UPDATE_SYSTEMUI_STATE_FLAG;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSysUiStateFlag.class);
        builderM4.targetModules = Collections.singletonList(LauncherProxyService.class);
        builderM4.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.VisibilityPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(1099511627776L, ((NavigationBarView) navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId)).getRootView().getVisibility() == 8));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                return navBarStoreImpl;
            }
        };
        arrayList.add(builderM4.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
