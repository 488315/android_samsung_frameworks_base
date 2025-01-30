package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.buttons.DeadZone;
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
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConfigurationPack implements BandAidPack {
    public final List allBands;

    public ConfigurationPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        builder.bandAidDependency = BandAid.CONFIG_PACK_CONFIG_CHANGED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnConfigChanged.class);
        builder.targetModules = Collections.singletonList(NavigationBarController.class);
        builder.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavigationBarView.class, LightBarController.class, NavigationBarTransitions.class);
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
        Band.Builder m164m = ColorPack$$ExternalSyntheticOutline0.m164m(builder, arrayList);
        m164m.runeDependency = BasicRune.NAVBAR_ICON_MOVEMENT;
        m164m.bandAidDependency = BandAid.CONFIG_PACK_NAVBAR_ICON_MARQUEE;
        m164m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarIconMarquee.class);
        m164m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m164m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (!kit.manager.isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.NavBarIconMarquee(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m2 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m, arrayList);
        m164m2.bandAidDependency = BandAid.CONFIG_PACK_GET_DEADZONE_SIZE;
        m164m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDeadZoneSize.class);
        m164m2.targetModules = Collections.singletonList(DeadZone.class);
        m164m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$2$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.GetDeadZoneSize getDeadZoneSize = (EventTypeFactory.EventType.GetDeadZoneSize) kit.event;
                Context context = (Context) ((NavBarStoreImpl) navBarStore2).getModule(Context.class, kit.displayId);
                return Integer.valueOf(getDeadZoneSize.maxSize ? context.getResources().getDimensionPixelSize(R.dimen.samsung_navigation_bar_deadzone_size_max) : context.getResources().getDimensionPixelSize(R.dimen.samsung_navigation_bar_deadzone_size));
            }
        };
        Band.Builder m164m3 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m2, arrayList);
        m164m3.bandAidDependency = BandAid.CONFIG_PACK_KEY_ORDER_CHANGED;
        m164m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonOrderChanged.class);
        m164m3.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                    kit.manager.states.layoutChangedBeforeAttached = true;
                } else {
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m4 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m3, arrayList);
        m164m4.bandAidDependency = BandAid.CONFIG_PACK_KEY_POSITION_CHANGED;
        m164m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonPositionChanged.class);
        m164m4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$4$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                    kit.manager.states.layoutChangedBeforeAttached = true;
                } else {
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m5 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m4, arrayList);
        boolean z = BasicRune.NAVBAR_OPEN_THEME;
        m164m5.runeDependency = z;
        m164m5.bandAidDependency = BandAid.CONFIG_PACK_OPEN_THEME_CHANGED;
        m164m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOpenThemeChanged.class);
        m164m5.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m5.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavigationBarView.class, NavigationBarTransitions.class);
        m164m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                kit.manager.updateUseThemeDefault();
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m6 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m5, arrayList);
        m164m6.runeDependency = z;
        m164m6.bandAidDependency = BandAid.CONFIG_PACK_THEME_DEFAULT_CHANGED;
        m164m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUseThemeDefaultChanged.class);
        m164m6.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m6.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavigationBarView.class, NavigationBarTransitions.class);
        m164m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$6$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m7 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m6, arrayList);
        m164m7.bandAidDependency = BandAid.CONFIG_PACK_ACTION_SOFT_RESET;
        m164m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSettingsSoftReset.class);
        m164m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m7.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.ConfigurationPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ((Band.Kit) obj).manager.updateUseThemeDefault();
                return Unit.INSTANCE;
            }
        };
        arrayList.add(m164m7.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
