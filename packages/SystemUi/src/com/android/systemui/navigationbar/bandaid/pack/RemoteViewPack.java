package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RemoteViewPack implements BandAidPack {
    public final List allBands;
    public final NavBarStore store;

    public RemoteViewPack(NavBarStore navBarStore) {
        this.store = navBarStore;
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_REMOTEVIEW;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.REMOTE_VIEW_PACK_INIT_REMOTE_VIEW_MANAGER;
        builder.targetEvents = CollectionsKt__CollectionsKt.listOf(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class, EventTypeFactory.EventType.OnInvalidateRemoteViews.class);
        builder.targetModules = CollectionsKt__CollectionsKt.listOf(SamsungNavigationBarView.class, ContextualButtonGroup.class);
        builder.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavBarRemoteViewManager.class, NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) RemoteViewPack.this.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.InvalidateRemoteView(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m = ColorPack$$ExternalSyntheticOutline0.m164m(builder, arrayList);
        m164m.runeDependency = z;
        m164m.bandAidDependency = BandAid.REMOTE_VIEW_PACK_SET_REMOTEVIEW_CONTAINER;
        m164m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateRemoteViewContainer.class);
        m164m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m164m.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavBarRemoteViewManager.class, NavigationBarView.class);
        m164m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnUpdateRemoteViewContainer onUpdateRemoteViewContainer = (EventTypeFactory.EventType.OnUpdateRemoteViewContainer) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewContainer(new NavBarStoreAction.Action(null, null, onUpdateRemoteViewContainer.leftContainer, onUpdateRemoteViewContainer.rightContainer, onUpdateRemoteViewContainer.contextualButtonVisible, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, onUpdateRemoteViewContainer.displayId, 2097123, null)));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, onUpdateRemoteViewContainer.darkIntensity, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194271, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m2 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m, arrayList);
        m164m2.runeDependency = z;
        BandAid bandAid = BandAid.REMOTE_VIEW_PACK_SET_NAVBAR_SHORTCUT_TO_MANAGER;
        m164m2.bandAidDependency = bandAid;
        m164m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m164m2.targetModules = Collections.singletonList(NavigationBarController.class);
        m164m2.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        m164m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnSetRemoteView onSetRemoteView = (EventTypeFactory.EventType.OnSetRemoteView) kit.event;
                NavBarStoreAction.RemoteViewShortcut remoteViewShortcut = new NavBarStoreAction.RemoteViewShortcut(onSetRemoteView.requestClass, onSetRemoteView.remoteViews, onSetRemoteView.position, onSetRemoteView.priority);
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewShortcut(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, remoteViewShortcut, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194239, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m3 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m2, arrayList);
        m164m3.runeDependency = z;
        m164m3.bandAidDependency = bandAid;
        m164m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m164m3.targetModules = Collections.singletonList(NavigationBarController.class);
        m164m3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m3.priority = 2;
        m164m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) RemoteViewPack.this.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.InvalidateRemoteView(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m4 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m3, arrayList);
        m164m4.runeDependency = z;
        m164m4.bandAidDependency = BandAid.REMOTE_VIEW_PACK_UPDATE_DARK_INTENSITY;
        m164m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateDarkIntensity.class);
        m164m4.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBarTransitions.class, NavigationBarView.class);
        m164m4.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        m164m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnUpdateDarkIntensity onUpdateDarkIntensity = (EventTypeFactory.EventType.OnUpdateDarkIntensity) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, onUpdateDarkIntensity.darkIntensity, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194271, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m5 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m4, arrayList);
        m164m5.runeDependency = z;
        m164m5.bandAidDependency = BandAid.REMOTE_VIEW_PACK_PACKAGE_REMOVED;
        m164m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnPackageRemoved.class);
        m164m5.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m5.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        m164m5.priority = 2;
        m164m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnPackageRemoved onPackageRemoved = (EventTypeFactory.EventType.OnPackageRemoved) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                int i2 = kit.displayId;
                NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) navBarStoreImpl.getModule(NavBarRemoteViewManager.class, i2);
                if (!(navBarRemoteViewManager.leftViewList.isEmpty() && navBarRemoteViewManager.rightViewList.isEmpty())) {
                    final String str = onPackageRemoved.packageName;
                    if (navBarRemoteViewManager.rightViewList.removeIf(new Predicate() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$removeIf$2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            return StringsKt__StringsJVMKt.equals(((NavBarRemoteView) obj2).requestClass, str, false);
                        }
                    }) | navBarRemoteViewManager.leftViewList.removeIf(new Predicate() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$removeIf$1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            return StringsKt__StringsJVMKt.equals(((NavBarRemoteView) obj2).requestClass, str, false);
                        }
                    })) {
                        navBarRemoteViewManager.updateRemoteViewContainer(kit.states.rotation, navBarRemoteViewManager.leftContainer, navBarRemoteViewManager.rightContainer, i2);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        arrayList.add(m164m5.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
