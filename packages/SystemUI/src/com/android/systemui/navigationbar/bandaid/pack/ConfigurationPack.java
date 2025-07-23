package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (kit.states.darkMode != ((((EventTypeFactory.EventType.OnConfigChanged) kit.event).newConfig.uiMode & 32) != 0)) {
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = BasicRune.NAVBAR_ICON_MOVEMENT;
        m.bandAidDependency = BandAid.CONFIG_PACK_NAVBAR_ICON_MARQUEE;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarIconMarquee.class);
        m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (!((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.NavBarIconMarquee(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.bandAidDependency = BandAid.CONFIG_PACK_GET_DEADZONE_SIZE;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDeadZoneSize.class);
        m2.targetModules = Collections.singletonList(DeadZone.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$2$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                Context context = (Context) ((NavBarStoreImpl) navBarStore2).getModule(Context.class, kit.displayId);
                return Integer.valueOf(((EventTypeFactory.EventType.GetDeadZoneSize) kit.event).maxSize ? context.getResources().getDimensionPixelSize(R.dimen.samsung_navigation_bar_deadzone_size_max) : context.getResources().getDimensionPixelSize(R.dimen.samsung_navigation_bar_deadzone_size));
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.bandAidDependency = BandAid.CONFIG_PACK_KEY_ORDER_CHANGED;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonOrderChanged.class);
        m3.targetModules = Arrays.asList(NavBarStoreImpl.class, NavigationBar.class);
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                    ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = true;
                } else {
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m4 = ColorPack$$ExternalSyntheticOutline0.m(m3, arrayList);
        m4.bandAidDependency = BandAid.CONFIG_PACK_KEY_POSITION_CHANGED;
        m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonPositionChanged.class);
        m4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$4$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                    ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = true;
                } else {
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m5 = ColorPack$$ExternalSyntheticOutline0.m(m4, arrayList);
        boolean z = BasicRune.NAVBAR_OPEN_THEME;
        m5.runeDependency = z;
        m5.bandAidDependency = BandAid.CONFIG_PACK_OPEN_THEME_CHANGED;
        m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOpenThemeChanged.class);
        m5.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m5.moduleDependencies = Arrays.asList(NavigationBarView.class, NavigationBarTransitions.class);
        m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                ((NavBarStateManagerImpl) kit.manager).updateUseThemeDefault();
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m6 = ColorPack$$ExternalSyntheticOutline0.m(m5, arrayList);
        m6.runeDependency = z;
        m6.bandAidDependency = BandAid.CONFIG_PACK_THEME_DEFAULT_CHANGED;
        m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUseThemeDefaultChanged.class);
        m6.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m6.moduleDependencies = Arrays.asList(NavigationBarView.class, NavigationBarTransitions.class);
        m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$6$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m7 = ColorPack$$ExternalSyntheticOutline0.m(m6, arrayList);
        m7.bandAidDependency = BandAid.CONFIG_PACK_ACTION_SOFT_RESET;
        m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSettingsSoftReset.class);
        m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m7.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ((NavBarStateManagerImpl) ((Band.Kit) obj).manager).updateUseThemeDefault();
                return Unit.INSTANCE;
            }
        };
        arrayList.add(m7.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
