package com.android.systemui.navigationbar.bandaid.pack;

import android.R;
import android.graphics.Insets;
import android.graphics.Point;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarInflaterView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

public final class StableLayoutPack implements BandAidPack {
    public final List allBands;
    public final List mExtraKeyList;
    public final List mMainKeyList;

    public StableLayoutPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        SamsungNavigationBarInflaterView.Companion companion = SamsungNavigationBarInflaterView.Companion;
        companion.getClass();
        String str = SamsungNavigationBarInflaterView.leftGestureHint;
        companion.getClass();
        String str2 = SamsungNavigationBarInflaterView.centerGestureHint;
        companion.getClass();
        this.mMainKeyList = CollectionsKt__CollectionsKt.mutableListOf(BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, "back", "recent", str, str2, SamsungNavigationBarInflaterView.rightGestureHint);
        companion.getClass();
        String str3 = SamsungNavigationBarInflaterView.pin;
        companion.getClass();
        String str4 = SamsungNavigationBarInflaterView.leftRemoteView;
        companion.getClass();
        this.mExtraKeyList = CollectionsKt__CollectionsKt.mutableListOf("menu_ime", "space", "ime_switcher", "clipboard", "contextual", str3, str4, SamsungNavigationBarInflaterView.rightRemoteView, "left", "right");
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        boolean z = BasicRune.NAVBAR_STABLE_LAYOUT;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_NAVBAR_CONFIG_CHANGED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarConfigChanged.class);
        builder.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBarControllerImpl.class, NavigationBar.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                Ref$BooleanRef ref$BooleanRef2 = Ref$BooleanRef.this;
                EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) kit.event;
                ref$BooleanRef2.element = false;
                NavBarStates navBarStates = kit.states;
                if (navBarStates.canMove != onNavBarConfigChanged.canMove || navBarStates.supportPhoneLayoutProvider != onNavBarConfigChanged.supportPhoneLayoutProvider || navBarStates.navigationMode != onNavBarConfigChanged.navigationMode || navBarStates.displayChanged) {
                    ref$BooleanRef2.element = true;
                }
                return Unit.INSTANCE;
            }
        };
        builder.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$1$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                Ref$BooleanRef ref$BooleanRef2 = Ref$BooleanRef.this;
                NavBarStore navBarStore2 = navBarStore;
                if (ref$BooleanRef2.element) {
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                    if (navBarStoreImpl.getModule(NavigationBarView.class, kit.displayId) == null) {
                        ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = true;
                    } else {
                        navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                    }
                }
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = BasicRune.NAVBAR_KNOX_MONITOR;
        m.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_NAVBAR_ATTACHED_TO_WINDOW;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m.priority = 0;
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                if (navBarStateManagerImpl.states.layoutChangedBeforeAttached) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                }
                navBarStateManagerImpl.states.layoutChangedBeforeAttached = false;
                return Unit.INSTANCE;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.runeDependency = z;
        m2.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_INFLATE_LAYOUT_ID;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetInflateLayoutID.class);
        m2.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                boolean z2 = ((EventTypeFactory.EventType.GetInflateLayoutID) kit.event).vertical;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                LayoutProvider layoutProvider = navBarStateManagerImpl.states.layoutProvider;
                Intrinsics.checkNotNull(layoutProvider);
                int verticalLayoutID = layoutProvider.getVerticalLayoutID(z2);
                navBarStateManagerImpl.logNavBarStates(Integer.valueOf(verticalLayoutID), "getLayoutID(vertical: " + z2 + ")");
                return Integer.valueOf(verticalLayoutID);
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.runeDependency = z;
        m3.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_DEFAULT_LAYOUT;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDefaultLayout.class);
        m3.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) ((Band.Kit) obj).manager;
                return navBarStateManagerImpl.isGestureMode() ? navBarStateManagerImpl.getGesturalLayout(navBarStateManagerImpl.isBottomGestureMode(true)) : navBarStateManagerImpl.getDefaultLayout();
            }
        };
        Band.Builder m4 = ColorPack$$ExternalSyntheticOutline0.m(m3, arrayList);
        m4.runeDependency = z;
        m4.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_INFLATE_NAVBAR;
        m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetInflateButtonWidth.class);
        m4.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                StableLayoutPack stableLayoutPack = StableLayoutPack.this;
                EventTypeFactory.EventType.GetInflateButtonWidth getInflateButtonWidth = (EventTypeFactory.EventType.GetInflateButtonWidth) kit.event;
                int i2 = kit.displayId;
                NavBarStateManager navBarStateManager = kit.manager;
                if (i2 == 1) {
                    String str5 = getInflateButtonWidth.buttonSpec;
                    stableLayoutPack.getClass();
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (Intrinsics.areEqual(SamsungNavigationBarInflaterView.buttonSpace, str5)) {
                        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStateManager;
                        LayoutProvider layoutProvider = navBarStateManagerImpl.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider);
                        Point point = navBarStateManagerImpl.states.displaySize;
                        boolean z2 = getInflateButtonWidth.landscape;
                        int spaceWidth = layoutProvider.getSpaceWidth(point, z2, false);
                        LayoutProvider layoutProvider2 = navBarStateManagerImpl.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider2);
                        int buttonWidth = layoutProvider2.getButtonWidth(navBarStateManagerImpl.states.displaySize, z2);
                        LayoutProvider layoutProvider3 = navBarStateManagerImpl.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider3);
                        int buttonDistanceSize = spaceWidth + buttonWidth + layoutProvider3.getButtonDistanceSize(navBarStateManagerImpl.states.displaySize, z2);
                        navBarStateManagerImpl.logNavBarStates(Integer.valueOf(buttonDistanceSize), "getButtonSpaceSize(land: " + z2 + ")");
                        return Integer.valueOf(buttonDistanceSize);
                    }
                }
                if (i2 == 1) {
                    String str6 = getInflateButtonWidth.buttonSpec;
                    stableLayoutPack.getClass();
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (Intrinsics.areEqual(SamsungNavigationBarInflaterView.leftRemoteView, str6) || Intrinsics.areEqual(SamsungNavigationBarInflaterView.rightRemoteView, str6)) {
                        return Integer.valueOf(((NavBarStateManagerImpl) navBarStateManager).getButtonWidth(getInflateButtonWidth.landscape));
                    }
                }
                String str7 = getInflateButtonWidth.buttonSpec;
                boolean contains = stableLayoutPack.mMainKeyList.contains(str7);
                boolean z3 = getInflateButtonWidth.landscape;
                if (!contains) {
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (!str7.startsWith(SamsungNavigationBarInflaterView.navkey)) {
                        List list = stableLayoutPack.mExtraKeyList;
                        String str8 = getInflateButtonWidth.buttonSpec;
                        if (list.contains(str8) || str8.startsWith("key")) {
                            return Integer.valueOf(((NavBarStateManagerImpl) navBarStateManager).getSpaceWidth(z3));
                        }
                        if ("home_handle".equals(str8)) {
                            return Integer.valueOf(((NavBarStateManagerImpl) navBarStateManager).getGestureWidth(z3));
                        }
                        if (!Intrinsics.areEqual(SamsungNavigationBarInflaterView.keymargin, str8)) {
                            return null;
                        }
                        NavBarStateManagerImpl navBarStateManagerImpl2 = (NavBarStateManagerImpl) navBarStateManager;
                        LayoutProvider layoutProvider4 = navBarStateManagerImpl2.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider4);
                        int buttonDistanceSize2 = layoutProvider4.getButtonDistanceSize(navBarStateManagerImpl2.states.displaySize, z3);
                        navBarStateManagerImpl2.logNavBarStates(Integer.valueOf(buttonDistanceSize2), "getButtonDistanceSize(land: " + z3 + ")");
                        return Integer.valueOf(buttonDistanceSize2);
                    }
                }
                return Integer.valueOf(((NavBarStateManagerImpl) navBarStateManager).getButtonWidth(z3));
            }
        };
        Band.Builder m5 = ColorPack$$ExternalSyntheticOutline0.m(m4, arrayList);
        m5.runeDependency = z;
        m5.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_BAR_LAYOUT_PARAMS;
        m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetBarLayoutParams.class);
        m5.targetModules = Collections.singletonList(NavigationBar.class);
        m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetBarLayoutParams getBarLayoutParams = (EventTypeFactory.EventType.GetBarLayoutParams) kit.event;
                int i2 = getBarLayoutParams.rotation;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                int barWidth = navBarStateManagerImpl.navBarLayoutParams.getBarWidth(navBarStateManagerImpl.states.canMove, i2);
                BarLayoutParams barLayoutParams = navBarStateManagerImpl.navBarLayoutParams;
                boolean z2 = navBarStateManagerImpl.states.canMove;
                int i3 = getBarLayoutParams.rotation;
                return new NavBarStoreAction.NavBarLayoutInfo(barWidth, barLayoutParams.getBarHeight(z2, i3), navBarStateManagerImpl.shouldShowSUWStyle() ? navBarStateManagerImpl.context.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top) : navBarStateManagerImpl.navBarLayoutParams.getBarInsetHeight(navBarStateManagerImpl.states.canMove, i3), navBarStateManagerImpl.navBarLayoutParams.getBarInsetWidth(navBarStateManagerImpl.states.canMove, i3), navBarStateManagerImpl.navBarLayoutParams.getBarGravity(navBarStateManagerImpl.states.canMove, i3));
            }
        };
        Band.Builder m6 = ColorPack$$ExternalSyntheticOutline0.m(m5, arrayList);
        m6.runeDependency = z;
        m6.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_INFLATE_NAVBAR_SIDE_PADDING;
        m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarSidePadding.class);
        m6.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetNavBarSidePadding getNavBarSidePadding = (EventTypeFactory.EventType.GetNavBarSidePadding) kit.event;
                NavBarStateManager navBarStateManager = kit.manager;
                boolean isSideAndBottomGestureMode$default = NavBarStateManager.isSideAndBottomGestureMode$default(navBarStateManager);
                boolean z2 = getNavBarSidePadding.landscape;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStateManager;
                LayoutProvider layoutProvider = navBarStateManagerImpl.states.layoutProvider;
                Intrinsics.checkNotNull(layoutProvider);
                int spaceSidePadding = layoutProvider.getSpaceSidePadding(navBarStateManagerImpl.states.displaySize, z2, isSideAndBottomGestureMode$default);
                navBarStateManagerImpl.logNavBarStates(Integer.valueOf(spaceSidePadding), "getNavBarSidePadding");
                return Integer.valueOf(spaceSidePadding);
            }
        };
        Band.Builder m7 = ColorPack$$ExternalSyntheticOutline0.m(m6, arrayList);
        m7.runeDependency = z;
        m7.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_BAR_LAYOUT_PARAMS_CHANGED;
        m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged.class);
        m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m7.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((NavBarStoreImpl) NavBarStore.this).apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
            }
        };
        Band.Builder m8 = ColorPack$$ExternalSyntheticOutline0.m(m7, arrayList);
        m8.runeDependency = z;
        m8.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_BUTTON_TO_HIDE_KEYBOARD_CHANGED;
        m8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonToHideKeyboardChanged.class);
        m8.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m8.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m8.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$17$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m9 = ColorPack$$ExternalSyntheticOutline0.m(m8, arrayList);
        m9.runeDependency = z;
        m9.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_NAVBAR_INSETS;
        m9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarInsets.class);
        m9.targetModules = Collections.singletonList(NavigationBar.class);
        m9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetNavBarInsets getNavBarInsets = (EventTypeFactory.EventType.GetNavBarInsets) kit.event;
                int i2 = getNavBarInsets.insetHeight;
                Insets of = i2 != -1 ? Insets.of(0, 0, 0, i2) : null;
                if (getNavBarInsets.insetWidth == -1 || !((NavBarStateManagerImpl) kit.manager).isBottomGestureMode(false)) {
                    return of;
                }
                int i3 = getNavBarInsets.rotation;
                return i3 == 1 ? Insets.of(0, 0, getNavBarInsets.insetWidth, 0) : i3 == 3 ? Insets.of(getNavBarInsets.insetWidth, 0, 0, 0) : of;
            }
        };
        Band.Builder m10 = ColorPack$$ExternalSyntheticOutline0.m(m9, arrayList);
        m10.runeDependency = z;
        m10.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_IME_INSETS;
        m10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetImeInsets.class);
        m10.targetModules = Collections.singletonList(NavigationBar.class);
        m10.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$21$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetImeInsets getImeInsets = (EventTypeFactory.EventType.GetImeInsets) kit.event;
                if (!((NavBarStateManagerImpl) kit.manager).canShowKeyboardButtonForRotation(getImeInsets.rotation)) {
                    if (!getImeInsets.canMove) {
                        return Insets.of(0, 0, 0, getImeInsets.insetHeight);
                    }
                    int i2 = getImeInsets.rotation;
                    if (i2 != -1 && i2 != 0) {
                        if (i2 == 1) {
                            return Insets.of(0, 0, getImeInsets.insetWidth, 0);
                        }
                        if (i2 != 2) {
                            if (i2 == 3) {
                                return Insets.of(getImeInsets.insetWidth, 0, 0, 0);
                            }
                        }
                    }
                    return Insets.of(0, 0, 0, getImeInsets.insetHeight);
                }
                return null;
            }
        };
        Band.Builder m11 = ColorPack$$ExternalSyntheticOutline0.m(m10, arrayList);
        m11.runeDependency = z;
        m11.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_MANDATORY_INSETS;
        m11.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetMandatoryInsets.class);
        m11.targetModules = Collections.singletonList(NavigationBar.class);
        m11.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$23$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetMandatoryInsets getMandatoryInsets = (EventTypeFactory.EventType.GetMandatoryInsets) kit.event;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) navBarStateManagerImpl.interactorFactory.get(GestureNavigationSettingsInteractor.class);
                int dimensionPixelSize = gestureNavigationSettingsInteractor != null ? gestureNavigationSettingsInteractor.bottomInsets : navBarStateManagerImpl.context.getResources().getDimensionPixelSize(R.dimen.resolver_icon_margin);
                if (navBarStateManagerImpl.isGestureMode()) {
                    if (!getMandatoryInsets.canMove) {
                        return Insets.of(0, 0, 0, dimensionPixelSize);
                    }
                    int i2 = getMandatoryInsets.rotation;
                    if (i2 != -1 && i2 != 0) {
                        if (i2 == 1) {
                            return Insets.of(0, 0, dimensionPixelSize, 0);
                        }
                        if (i2 != 2) {
                            if (i2 == 3) {
                                return Insets.of(dimensionPixelSize, 0, 0, 0);
                            }
                        }
                    }
                    return Insets.of(0, 0, 0, dimensionPixelSize);
                }
                return null;
            }
        };
        arrayList.add(m11.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
