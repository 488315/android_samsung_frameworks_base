package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarTransitions;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.navigationbar.views.buttons.DeadZone;
import com.android.systemui.statusbar.phone.LightBarController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;

/* loaded from: classes2.dex */
public final class ConfigurationPack implements BandAidPack {
    public final List allBands;

    public ConfigurationPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        builder.bandAidDependency = BandAid.CONFIG_PACK_CONFIG_CHANGED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnConfigChanged.class);
        builder.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        builder.moduleDependencies = Arrays.asList(NavigationBarView.class, LightBarController.class, NavigationBarTransitions.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$band$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (kit.states.darkMode != ((((EventTypeFactory.EventType.OnConfigChanged) kit.event).newConfig.uiMode & 32) != 0)) {
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.runeDependency = BasicRune.NAVBAR_ICON_MOVEMENT;
        builderM.bandAidDependency = BandAid.CONFIG_PACK_NAVBAR_ICON_MARQUEE;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarIconMarquee.class);
        builderM.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (!((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.NavBarIconMarquee(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.bandAidDependency = BandAid.CONFIG_PACK_GET_DEADZONE_SIZE;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDeadZoneSize.class);
        builderM2.targetModules = Collections.singletonList(DeadZone.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$2$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                Context context = (Context) ((NavBarStoreImpl) navBarStore2).getModule(Context.class, kit.displayId);
                return Integer.valueOf(((EventTypeFactory.EventType.GetDeadZoneSize) kit.event).maxSize ? context.getResources().getDimensionPixelSize(R.dimen.samsung_navigation_bar_deadzone_size_max) : context.getResources().getDimensionPixelSize(R.dimen.samsung_navigation_bar_deadzone_size));
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.bandAidDependency = BandAid.CONFIG_PACK_KEY_ORDER_CHANGED;
        builderM3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonOrderChanged.class);
        builderM3.targetModules = Arrays.asList(NavBarStoreImpl.class, NavigationBar.class);
        builderM3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                    ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = true;
                } else {
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.bandAidDependency = BandAid.CONFIG_PACK_KEY_POSITION_CHANGED;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonPositionChanged.class);
        builderM4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$4$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                    ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = true;
                } else {
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM5 = ColorPack$$ExternalSyntheticOutline0.m(builderM4, arrayList);
        boolean z = BasicRune.NAVBAR_OPEN_THEME;
        builderM5.runeDependency = z;
        builderM5.bandAidDependency = BandAid.CONFIG_PACK_OPEN_THEME_CHANGED;
        builderM5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOpenThemeChanged.class);
        builderM5.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM5.moduleDependencies = Arrays.asList(NavigationBarView.class, NavigationBarTransitions.class);
        builderM5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                ((NavBarStateManagerImpl) kit.manager).updateUseThemeDefault();
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM6 = ColorPack$$ExternalSyntheticOutline0.m(builderM5, arrayList);
        builderM6.runeDependency = z;
        builderM6.bandAidDependency = BandAid.CONFIG_PACK_THEME_DEFAULT_CHANGED;
        builderM6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUseThemeDefaultChanged.class);
        builderM6.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM6.moduleDependencies = Arrays.asList(NavigationBarView.class, NavigationBarTransitions.class);
        builderM6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$6$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM7 = ColorPack$$ExternalSyntheticOutline0.m(builderM6, arrayList);
        builderM7.bandAidDependency = BandAid.CONFIG_PACK_ACTION_SOFT_RESET;
        builderM7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSettingsSoftReset.class);
        builderM7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM7.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ((NavBarStateManagerImpl) ((Band.Kit) obj).manager).updateUseThemeDefault();
                return Unit.INSTANCE;
            }
        };
        arrayList.add(builderM7.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
