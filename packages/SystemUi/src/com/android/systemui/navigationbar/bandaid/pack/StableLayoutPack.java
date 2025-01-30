package com.android.systemui.navigationbar.bandaid.pack;

import android.R;
import android.graphics.Insets;
import android.graphics.Point;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarInflaterView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        String str4 = SamsungNavigationBarInflaterView.extraBack;
        companion.getClass();
        String str5 = SamsungNavigationBarInflaterView.leftRemoteView;
        companion.getClass();
        this.mExtraKeyList = CollectionsKt__CollectionsKt.mutableListOf("menu_ime", "space", "ime_switcher", "clipboard", "contextual", str3, str4, str5, SamsungNavigationBarInflaterView.rightRemoteView, "left", "right");
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        boolean z = BasicRune.NAVBAR_STABLE_LAYOUT;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_NAVBAR_CONFIG_CHANGED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarConfigChanged.class);
        builder.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBarController.class, NavigationBar.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                Ref$BooleanRef ref$BooleanRef2 = Ref$BooleanRef.this;
                EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) kit.event;
                ref$BooleanRef2.element = false;
                NavBarStateManager.States states = kit.states;
                if (states.canMove != onNavBarConfigChanged.canMove || states.supportPhoneLayoutProvider != onNavBarConfigChanged.supportPhoneLayoutProvider || states.navigationMode != onNavBarConfigChanged.navigationMode || states.displayChanged) {
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
                        kit.manager.states.layoutChangedBeforeAttached = true;
                    } else {
                        navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                    }
                }
            }
        };
        Band.Builder m164m = ColorPack$$ExternalSyntheticOutline0.m164m(builder, arrayList);
        m164m.runeDependency = BasicRune.NAVBAR_KNOX_MONITOR;
        m164m.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_NAVBAR_ATTACHED_TO_WINDOW;
        m164m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        m164m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m164m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m.priority = 0;
        m164m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManager navBarStateManager = kit.manager;
                if (navBarStateManager.states.layoutChangedBeforeAttached) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                }
                navBarStateManager.states.layoutChangedBeforeAttached = false;
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m2 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m, arrayList);
        m164m2.runeDependency = z;
        m164m2.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_INFLATE_LAYOUT_ID;
        m164m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetInflateLayoutID.class);
        m164m2.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m164m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                boolean z2 = ((EventTypeFactory.EventType.GetInflateLayoutID) kit.event).vertical;
                NavBarStateManager navBarStateManager = kit.manager;
                LayoutProvider layoutProvider = navBarStateManager.states.layoutProvider;
                Intrinsics.checkNotNull(layoutProvider);
                int verticalLayoutID = layoutProvider.getVerticalLayoutID(z2);
                Integer valueOf = Integer.valueOf(verticalLayoutID);
                navBarStateManager.logNavBarStates(valueOf, "getLayoutID(vertical: " + z2 + ")");
                Intrinsics.checkNotNull(valueOf);
                return Integer.valueOf(valueOf.intValue());
            }
        };
        Band.Builder m164m3 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m2, arrayList);
        m164m3.runeDependency = z;
        m164m3.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_DEFAULT_LAYOUT;
        m164m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDefaultLayout.class);
        m164m3.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m164m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStateManager navBarStateManager = ((Band.Kit) obj).manager;
                if (!navBarStateManager.isGestureMode()) {
                    return navBarStateManager.getDefaultLayout();
                }
                boolean isBottomGestureMode = navBarStateManager.isBottomGestureMode(true);
                LayoutProvider layoutProvider = navBarStateManager.states.layoutProvider;
                Intrinsics.checkNotNull(layoutProvider);
                String gesturalLayout = layoutProvider.getGesturalLayout(isBottomGestureMode, true ^ navBarStateManager.settingsHelper.isNavBarButtonOrderDefault());
                navBarStateManager.logNavBarStates(gesturalLayout, "getGesturalLayout");
                Intrinsics.checkNotNull(gesturalLayout);
                return gesturalLayout;
            }
        };
        Band.Builder m164m4 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m3, arrayList);
        m164m4.runeDependency = z;
        m164m4.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_INFLATE_NAVBAR;
        m164m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetInflateButtonWidth.class);
        m164m4.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m164m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$9$1
            /* JADX WARN: Removed duplicated region for block: B:25:0x00c7  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x00d1  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) {
                boolean z2;
                Band.Kit kit = (Band.Kit) obj;
                StableLayoutPack stableLayoutPack = StableLayoutPack.this;
                EventTypeFactory.EventType.GetInflateButtonWidth getInflateButtonWidth = (EventTypeFactory.EventType.GetInflateButtonWidth) kit.event;
                int i2 = kit.displayId;
                NavBarStateManager navBarStateManager = kit.manager;
                if (i2 == 1) {
                    String str6 = getInflateButtonWidth.buttonSpec;
                    stableLayoutPack.getClass();
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (Intrinsics.areEqual(SamsungNavigationBarInflaterView.buttonSpace, str6)) {
                        LayoutProvider layoutProvider = navBarStateManager.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider);
                        Point point = navBarStateManager.states.displaySize;
                        boolean z3 = getInflateButtonWidth.landscape;
                        int spaceWidth = layoutProvider.getSpaceWidth(point, z3, false);
                        LayoutProvider layoutProvider2 = navBarStateManager.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider2);
                        int buttonWidth = layoutProvider2.getButtonWidth(navBarStateManager.states.displaySize, z3);
                        LayoutProvider layoutProvider3 = navBarStateManager.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider3);
                        int buttonDistanceSize = spaceWidth + buttonWidth + layoutProvider3.getButtonDistanceSize(navBarStateManager.states.displaySize, z3);
                        Integer valueOf = Integer.valueOf(buttonDistanceSize);
                        navBarStateManager.logNavBarStates(valueOf, "getButtonSpaceSize(land: " + z3 + ")");
                        Intrinsics.checkNotNull(valueOf);
                        return Integer.valueOf(valueOf.intValue());
                    }
                }
                if (i2 == 1) {
                    String str7 = getInflateButtonWidth.buttonSpec;
                    stableLayoutPack.getClass();
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (Intrinsics.areEqual(SamsungNavigationBarInflaterView.leftRemoteView, str7) || Intrinsics.areEqual(SamsungNavigationBarInflaterView.rightRemoteView, str7)) {
                        return Integer.valueOf(navBarStateManager.getButtonWidth(getInflateButtonWidth.landscape));
                    }
                }
                String str8 = getInflateButtonWidth.buttonSpec;
                if (!stableLayoutPack.mMainKeyList.contains(str8)) {
                    SamsungNavigationBarInflaterView.Companion.getClass();
                    if (!str8.startsWith(SamsungNavigationBarInflaterView.navkey)) {
                        z2 = false;
                        boolean z4 = getInflateButtonWidth.landscape;
                        if (!z2) {
                            return Integer.valueOf(navBarStateManager.getButtonWidth(z4));
                        }
                        List list = stableLayoutPack.mExtraKeyList;
                        String str9 = getInflateButtonWidth.buttonSpec;
                        if (list.contains(str9) || str9.startsWith("key")) {
                            return Integer.valueOf(navBarStateManager.getSpaceWidth(z4));
                        }
                        if (Intrinsics.areEqual("home_handle", str9)) {
                            return Integer.valueOf(navBarStateManager.getGestureWidth(z4));
                        }
                        SamsungNavigationBarInflaterView.Companion.getClass();
                        if (!Intrinsics.areEqual(SamsungNavigationBarInflaterView.keymargin, str9)) {
                            return null;
                        }
                        LayoutProvider layoutProvider4 = navBarStateManager.states.layoutProvider;
                        Intrinsics.checkNotNull(layoutProvider4);
                        Integer valueOf2 = Integer.valueOf(layoutProvider4.getButtonDistanceSize(navBarStateManager.states.displaySize, z4));
                        navBarStateManager.logNavBarStates(valueOf2, "getButtonDistanceSize(land: " + z4 + ")");
                        Intrinsics.checkNotNull(valueOf2);
                        return Integer.valueOf(valueOf2.intValue());
                    }
                }
                z2 = true;
                boolean z42 = getInflateButtonWidth.landscape;
                if (!z2) {
                }
            }
        };
        Band.Builder m164m5 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m4, arrayList);
        m164m5.runeDependency = z;
        m164m5.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_BAR_LAYOUT_PARAMS;
        m164m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetBarLayoutParams.class);
        m164m5.targetModules = Collections.singletonList(NavigationBar.class);
        m164m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetBarLayoutParams getBarLayoutParams = (EventTypeFactory.EventType.GetBarLayoutParams) kit.event;
                int i2 = getBarLayoutParams.rotation;
                NavBarStateManager navBarStateManager = kit.manager;
                int barWidth = navBarStateManager.navBarLayoutParams.getBarWidth(navBarStateManager.states.canMove, i2);
                BarLayoutParams barLayoutParams = navBarStateManager.navBarLayoutParams;
                boolean z2 = navBarStateManager.states.canMove;
                int i3 = getBarLayoutParams.rotation;
                return new NavBarStoreAction.NavBarLayoutInfo(barWidth, barLayoutParams.getBarHeight(z2, i3), navBarStateManager.shouldShowSUWStyle() ? navBarStateManager.context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end) : navBarStateManager.navBarLayoutParams.getBarInsetHeight(navBarStateManager.states.canMove, i3), navBarStateManager.navBarLayoutParams.getBarInsetWidth(navBarStateManager.states.canMove, i3), navBarStateManager.navBarLayoutParams.getBarGravity(navBarStateManager.states.canMove, i3));
            }
        };
        Band.Builder m164m6 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m5, arrayList);
        m164m6.runeDependency = z;
        m164m6.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_INFLATE_NAVBAR_SIDE_PADDING;
        m164m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarSidePadding.class);
        m164m6.targetModules = Collections.singletonList(SamsungNavigationBarInflaterView.class);
        m164m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetNavBarSidePadding getNavBarSidePadding = (EventTypeFactory.EventType.GetNavBarSidePadding) kit.event;
                int i2 = NavBarStateManager.$r8$clinit;
                NavBarStateManager navBarStateManager = kit.manager;
                boolean isSideAndBottomGestureMode = navBarStateManager.isSideAndBottomGestureMode(false);
                boolean z2 = getNavBarSidePadding.landscape;
                LayoutProvider layoutProvider = navBarStateManager.states.layoutProvider;
                Intrinsics.checkNotNull(layoutProvider);
                Integer valueOf = Integer.valueOf(layoutProvider.getSpaceSidePadding(navBarStateManager.states.displaySize, z2, isSideAndBottomGestureMode));
                navBarStateManager.logNavBarStates(valueOf, "getNavBarSidePadding");
                Intrinsics.checkNotNull(valueOf);
                return Integer.valueOf(valueOf.intValue());
            }
        };
        Band.Builder m164m7 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m6, arrayList);
        m164m7.runeDependency = z;
        m164m7.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_BAR_LAYOUT_PARAMS_CHANGED;
        m164m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged.class);
        m164m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m7.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m164m7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((NavBarStoreImpl) NavBarStore.this).apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
            }
        };
        Band.Builder m164m8 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m7, arrayList);
        m164m8.runeDependency = z;
        m164m8.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_BUTTON_TO_HIDE_KEYBOARD_CHANGED;
        m164m8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnButtonToHideKeyboardChanged.class);
        m164m8.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m8.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m164m8.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$17$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m9 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m8, arrayList);
        m164m9.runeDependency = z;
        m164m9.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_NAVBAR_INSETS;
        m164m9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarInsets.class);
        m164m9.targetModules = Collections.singletonList(NavigationBar.class);
        m164m9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetNavBarInsets getNavBarInsets = (EventTypeFactory.EventType.GetNavBarInsets) kit.event;
                int i2 = getNavBarInsets.insetHeight;
                Insets of = i2 != -1 ? Insets.of(0, 0, 0, i2) : null;
                if (getNavBarInsets.insetWidth == -1 || !kit.manager.isBottomGestureMode(false)) {
                    return of;
                }
                int i3 = getNavBarInsets.rotation;
                return i3 == 1 ? Insets.of(0, 0, getNavBarInsets.insetWidth, 0) : i3 == 3 ? Insets.of(getNavBarInsets.insetWidth, 0, 0, 0) : of;
            }
        };
        Band.Builder m164m10 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m9, arrayList);
        m164m10.runeDependency = z;
        m164m10.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_IME_INSETS;
        m164m10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetImeInsets.class);
        m164m10.targetModules = Collections.singletonList(NavigationBar.class);
        m164m10.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$21$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetImeInsets getImeInsets = (EventTypeFactory.EventType.GetImeInsets) kit.event;
                if (!kit.manager.canShowKeyboardButtonForRotation(getImeInsets.rotation)) {
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
        arrayList.add(m164m10.build());
        int i2 = Band.$r8$clinit;
        Band.Builder builder2 = new Band.Builder();
        builder2.runeDependency = z;
        builder2.bandAidDependency = BandAid.STABLE_LAYOUT_PACK_GET_MANDATORY_INSETS;
        builder2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetMandatoryInsets.class);
        builder2.targetModules = Collections.singletonList(NavigationBar.class);
        builder2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.StableLayoutPack$23$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.GetMandatoryInsets getMandatoryInsets = (EventTypeFactory.EventType.GetMandatoryInsets) kit.event;
                NavBarStateManager navBarStateManager = kit.manager;
                GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) navBarStateManager.interactorFactory.get(GestureNavigationSettingsInteractor.class);
                int dimensionPixelSize = gestureNavigationSettingsInteractor != null ? gestureNavigationSettingsInteractor.bottomInsets : navBarStateManager.context.getResources().getDimensionPixelSize(R.dimen.notification_conversation_header_separating_margin);
                if (navBarStateManager.isGestureMode()) {
                    if (!getMandatoryInsets.canMove) {
                        return Insets.of(0, 0, 0, dimensionPixelSize);
                    }
                    int i3 = getMandatoryInsets.rotation;
                    if (i3 != -1 && i3 != 0) {
                        if (i3 == 1) {
                            return Insets.of(0, 0, dimensionPixelSize, 0);
                        }
                        if (i3 != 2) {
                            if (i3 == 3) {
                                return Insets.of(dimensionPixelSize, 0, 0, 0);
                            }
                        }
                    }
                    return Insets.of(0, 0, 0, dimensionPixelSize);
                }
                return null;
            }
        };
        arrayList.add(builder2.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
