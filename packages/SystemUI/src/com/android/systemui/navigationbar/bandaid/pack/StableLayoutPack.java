package com.android.systemui.navigationbar.bandaid.pack;

import android.R;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
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
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarInflaterView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes2.dex */
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
        builder.targetModules = Arrays.asList(NavigationBarControllerImpl.class, NavigationBar.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
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
            public final void accept(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
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
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.runeDependency = BasicRune.NAVBAR_KNOX_MONITOR;
        builderM.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_NAVBAR_ATTACHED_TO_WINDOW;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        builderM.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM.priority = 0;
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                }
                ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = false;
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.runeDependency = z;
        builderM2.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_INFLATE_LAYOUT_ID;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetInflateLayoutID.class);
        builderM2.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                boolean z2 = ((EventTypeFactory.EventType.GetInflateLayoutID) kit.event).vertical;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                LayoutProvider layoutProvider = navBarStateManagerImpl.states.layoutProvider;
                layoutProvider.getClass();
                int verticalLayoutID = layoutProvider.getVerticalLayoutID(z2);
                navBarStateManagerImpl.logNavBarStates(Integer.valueOf(verticalLayoutID), "getLayoutID(vertical: " + z2 + ")");
                return Integer.valueOf(verticalLayoutID);
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.runeDependency = z;
        builderM3.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_DEFAULT_LAYOUT;
        builderM3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDefaultLayout.class);
        builderM3.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        builderM3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) ((Band.Kit) obj).manager;
                return navBarStateManagerImpl.isGestureMode() ? navBarStateManagerImpl.getGesturalLayout(navBarStateManagerImpl.isBottomGestureMode(true)) : navBarStateManagerImpl.getDefaultLayout();
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.runeDependency = z;
        builderM4.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_INFLATE_NAVBAR;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetInflateButtonWidth.class);
        builderM4.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                StableLayoutPack stableLayoutPack = this.this$0;
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
                        layoutProvider.getClass();
                        Point point = navBarStateManagerImpl.states.displaySize;
                        boolean z2 = getInflateButtonWidth.landscape;
                        int spaceWidth = layoutProvider.getSpaceWidth(point, z2, false);
                        LayoutProvider layoutProvider2 = navBarStateManagerImpl.states.layoutProvider;
                        layoutProvider2.getClass();
                        int buttonWidth = layoutProvider2.getButtonWidth(navBarStateManagerImpl.states.displaySize, z2);
                        LayoutProvider layoutProvider3 = navBarStateManagerImpl.states.layoutProvider;
                        layoutProvider3.getClass();
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
                boolean zContains = ((ArrayList) stableLayoutPack.mMainKeyList).contains(str7);
                boolean z3 = getInflateButtonWidth.landscape;
                if (!zContains) {
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (!str7.startsWith(SamsungNavigationBarInflaterView.navkey)) {
                        ArrayList arrayList2 = (ArrayList) stableLayoutPack.mExtraKeyList;
                        String str8 = getInflateButtonWidth.buttonSpec;
                        if (arrayList2.contains(str8) || str8.startsWith("key")) {
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
                        layoutProvider4.getClass();
                        int buttonDistanceSize2 = layoutProvider4.getButtonDistanceSize(navBarStateManagerImpl2.states.displaySize, z3);
                        navBarStateManagerImpl2.logNavBarStates(Integer.valueOf(buttonDistanceSize2), "getButtonDistanceSize(land: " + z3 + ")");
                        return Integer.valueOf(buttonDistanceSize2);
                    }
                }
                return Integer.valueOf(((NavBarStateManagerImpl) navBarStateManager).getButtonWidth(z3));
            }
        };
        Band.Builder builderM5 = ColorPack$$ExternalSyntheticOutline0.m(builderM4, arrayList);
        builderM5.runeDependency = z;
        builderM5.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_BAR_LAYOUT_PARAMS;
        builderM5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetBarLayoutParams.class);
        builderM5.targetModules = Collections.singletonList(NavigationBar.class);
        builderM5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetBarLayoutParams getBarLayoutParams = (EventTypeFactory.EventType.GetBarLayoutParams) kit.event;
                int i2 = getBarLayoutParams.rotation;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                int barWidth = navBarStateManagerImpl.shouldShowSUWStyle() ? -1 : navBarStateManagerImpl.navBarLayoutParams.getBarWidth(navBarStateManagerImpl.states.canMove, i2);
                int i3 = getBarLayoutParams.rotation;
                return new NavBarStoreAction.NavBarLayoutInfo(barWidth, navBarStateManagerImpl.getNavBarHeight(i3), navBarStateManagerImpl.shouldShowSUWStyle() ? navBarStateManagerImpl.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size) : navBarStateManagerImpl.navBarLayoutParams.getBarInsetHeight(navBarStateManagerImpl.states.canMove, i3), navBarStateManagerImpl.shouldShowSUWStyle() ? -1 : navBarStateManagerImpl.navBarLayoutParams.getBarInsetWidth(navBarStateManagerImpl.states.canMove, i3), navBarStateManagerImpl.shouldShowSUWStyle() ? 80 : navBarStateManagerImpl.navBarLayoutParams.getBarGravity(navBarStateManagerImpl.states.canMove, i3));
            }
        };
        Band.Builder builderM6 = ColorPack$$ExternalSyntheticOutline0.m(builderM5, arrayList);
        builderM6.runeDependency = z;
        builderM6.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_INFLATE_NAVBAR_SIDE_PADDING;
        builderM6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarSidePadding.class);
        builderM6.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        builderM6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStateManager navBarStateManager = kit.manager;
                boolean zIsSideAndBottomGestureMode$default = NavBarStateManager.isSideAndBottomGestureMode$default(navBarStateManager);
                boolean z2 = ((EventTypeFactory.EventType.GetNavBarSidePadding) kit.event).landscape;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStateManager;
                LayoutProvider layoutProvider = navBarStateManagerImpl.states.layoutProvider;
                layoutProvider.getClass();
                int spaceSidePadding = layoutProvider.getSpaceSidePadding(navBarStateManagerImpl.states.displaySize, z2, zIsSideAndBottomGestureMode$default);
                navBarStateManagerImpl.logNavBarStates(Integer.valueOf(spaceSidePadding), "getNavBarSidePadding");
                return Integer.valueOf(spaceSidePadding);
            }
        };
        Band.Builder builderM7 = ColorPack$$ExternalSyntheticOutline0.m(builderM6, arrayList);
        builderM7.runeDependency = z;
        builderM7.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_BAR_LAYOUT_PARAMS_CHANGED;
        builderM7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged.class);
        builderM7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM7.moduleDependencies = Collections.singletonList(NavigationBar.class);
        builderM7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                ((NavBarStoreImpl) navBarStore).apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
            }
        };
        Band.Builder builderM8 = ColorPack$$ExternalSyntheticOutline0.m(builderM7, arrayList);
        builderM8.runeDependency = z;
        builderM8.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_BUTTON_TO_HIDE_KEYBOARD_CHANGED;
        builderM8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonToHideKeyboardChanged.class);
        builderM8.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM8.moduleDependencies = Collections.singletonList(NavigationBar.class);
        builderM8.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$17$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM9 = ColorPack$$ExternalSyntheticOutline0.m(builderM8, arrayList);
        builderM9.runeDependency = z;
        builderM9.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_NAVBAR_INSETS;
        builderM9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarInsets.class);
        builderM9.targetModules = Collections.singletonList(NavigationBar.class);
        builderM9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                int i2 = ((EventTypeFactory.EventType.GetNavBarInsets) kit.event).insetHeight;
                Insets insetsOf = i2 != -1 ? Insets.of(0, 0, 0, i2) : null;
                EventTypeFactory.EventType.GetNavBarInsets getNavBarInsets = (EventTypeFactory.EventType.GetNavBarInsets) kit.event;
                if (getNavBarInsets.insetWidth == -1 || !((NavBarStateManagerImpl) kit.manager).isBottomGestureMode(false)) {
                    return insetsOf;
                }
                int i3 = getNavBarInsets.rotation;
                return i3 == 1 ? Insets.of(0, 0, getNavBarInsets.insetWidth, 0) : i3 == 3 ? Insets.of(getNavBarInsets.insetWidth, 0, 0, 0) : insetsOf;
            }
        };
        Band.Builder builderM10 = ColorPack$$ExternalSyntheticOutline0.m(builderM9, arrayList);
        builderM10.runeDependency = z;
        builderM10.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_IME_INSETS;
        builderM10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetImeInsets.class);
        builderM10.targetModules = Collections.singletonList(NavigationBar.class);
        builderM10.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$21$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                if (((NavBarStateManagerImpl) kit.manager).canShowKeyboardButtonForRotation(((EventTypeFactory.EventType.GetImeInsets) kit.event).rotation)) {
                    return null;
                }
                EventTypeFactory.EventType.GetImeInsets getImeInsets = (EventTypeFactory.EventType.GetImeInsets) kit.event;
                if (!getImeInsets.canMove) {
                    return Insets.of(0, 0, 0, getImeInsets.insetHeight);
                }
                int i2 = getImeInsets.rotation;
                if (i2 != -1 && i2 != 0) {
                    if (i2 == 1) {
                        return Insets.of(0, 0, getImeInsets.insetWidth, 0);
                    }
                    if (i2 != 2) {
                        if (i2 != 3) {
                            return null;
                        }
                        return Insets.of(getImeInsets.insetWidth, 0, 0, 0);
                    }
                }
                return Insets.of(0, 0, 0, getImeInsets.insetHeight);
            }
        };
        Band.Builder builderM11 = ColorPack$$ExternalSyntheticOutline0.m(builderM10, arrayList);
        builderM11.runeDependency = z;
        builderM11.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_MANDATORY_INSETS;
        builderM11.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetMandatoryInsets.class);
        builderM11.targetModules = Collections.singletonList(NavigationBar.class);
        builderM11.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$23$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) navBarStateManagerImpl.interactorFactory.get(GestureNavigationSettingsInteractor.class);
                int dimensionPixelSize = gestureNavigationSettingsInteractor != null ? gestureNavigationSettingsInteractor.bottomInsets : navBarStateManagerImpl.context.getResources().getDimensionPixelSize(R.dimen.seekbar_track_background_height_material);
                if (!navBarStateManagerImpl.isGestureMode()) {
                    return null;
                }
                EventTypeFactory.EventType.GetMandatoryInsets getMandatoryInsets = (EventTypeFactory.EventType.GetMandatoryInsets) kit.event;
                if (!getMandatoryInsets.canMove) {
                    return Insets.of(0, 0, 0, dimensionPixelSize);
                }
                int i2 = getMandatoryInsets.rotation;
                if (i2 != -1 && i2 != 0) {
                    if (i2 == 1) {
                        return Insets.of(0, 0, dimensionPixelSize, 0);
                    }
                    if (i2 != 2) {
                        if (i2 != 3) {
                            return null;
                        }
                        return Insets.of(dimensionPixelSize, 0, 0, 0);
                    }
                }
                return Insets.of(0, 0, 0, dimensionPixelSize);
            }
        };
        arrayList.add(builderM11.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
