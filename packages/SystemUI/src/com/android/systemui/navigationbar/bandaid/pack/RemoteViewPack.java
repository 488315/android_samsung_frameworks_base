package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = z;
        m.bandAidDependency = BandAid.REMOTE_VIEW_PACK_SET_REMOTEVIEW_CONTAINER;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateRemoteViewContainer.class);
        m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavBarRemoteViewManager.class, NavigationBarView.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnUpdateRemoteViewContainer onUpdateRemoteViewContainer = (EventTypeFactory.EventType.OnUpdateRemoteViewContainer) kit.event;
                NavBarStore navBarStore2 = remoteViewPack.store;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewContainer(new NavBarStoreAction.Action(null, null, onUpdateRemoteViewContainer.leftContainer, onUpdateRemoteViewContainer.rightContainer, onUpdateRemoteViewContainer.contextualButtonVisible, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, onUpdateRemoteViewContainer.displayId, onUpdateRemoteViewContainer.rotation, 2097123, null)));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, onUpdateRemoteViewContainer.darkIntensity, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388575, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.runeDependency = z;
        BandAid bandAid = BandAid.REMOTE_VIEW_PACK_SET_NAVBAR_SHORTCUT_TO_MANAGER;
        m2.bandAidDependency = bandAid;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m2.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        m2.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$5$1
            /* JADX WARN: Code restructure failed: missing block: B:6:0x0022, code lost:
            
                if (((com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0.manager).canShowKeyboardButtonOnLeft() != false) goto L10;
             */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object apply(java.lang.Object r33) {
                /*
                    r32 = this;
                    r0 = r33
                    com.android.systemui.navigationbar.bandaid.Band$Kit r0 = (com.android.systemui.navigationbar.bandaid.Band.Kit) r0
                    r1 = r32
                    com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack r1 = com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack.this
                    com.android.systemui.navigationbar.store.EventTypeFactory$EventType r2 = r0.event
                    com.android.systemui.navigationbar.store.EventTypeFactory$EventType$OnSetRemoteView r2 = (com.android.systemui.navigationbar.store.EventTypeFactory.EventType.OnSetRemoteView) r2
                    java.lang.String r3 = r2.requestClass
                    if (r3 == 0) goto L25
                    java.lang.String r4 = "honeyboard"
                    r5 = 0
                    boolean r3 = kotlin.text.StringsKt__StringsKt.contains(r3, r4, r5)
                    r4 = 1
                    if (r3 != r4) goto L25
                    com.android.systemui.navigationbar.store.NavBarStateManager r3 = r0.manager
                    com.android.systemui.navigationbar.store.NavBarStateManagerImpl r3 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r3
                    boolean r3 = r3.canShowKeyboardButtonOnLeft()
                    if (r3 == 0) goto L25
                    goto L27
                L25:
                    int r5 = r2.position
                L27:
                    com.android.systemui.navigationbar.store.NavBarStoreAction$RemoteViewShortcut r13 = new com.android.systemui.navigationbar.store.NavBarStoreAction$RemoteViewShortcut
                    android.widget.RemoteViews r3 = r2.remoteViews
                    java.lang.String r4 = r2.requestClass
                    int r2 = r2.priority
                    r13.<init>(r4, r3, r5, r2)
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    java.lang.String r3 = "OnSetRemoteView "
                    r2.<init>(r3)
                    r2.append(r13)
                    java.lang.String r2 = r2.toString()
                    java.lang.String r3 = "RemoteViewPack"
                    android.util.Log.d(r3, r2)
                    com.android.systemui.navigationbar.store.NavBarStore r1 = r1.store
                    com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateRemoteViewShortcut r2 = new com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateRemoteViewShortcut
                    com.android.systemui.navigationbar.store.NavBarStoreAction$Action r3 = new com.android.systemui.navigationbar.store.NavBarStoreAction$Action
                    r6 = r3
                    r30 = 8388543(0x7fffbf, float:1.1754852E-38)
                    r31 = 0
                    r7 = 0
                    r8 = 0
                    r9 = 0
                    r10 = 0
                    r11 = 0
                    r12 = 0
                    r14 = 0
                    r15 = 0
                    r16 = 0
                    r17 = 0
                    r18 = 0
                    r19 = 0
                    r20 = 0
                    r21 = 0
                    r22 = 0
                    r23 = 0
                    r24 = 0
                    r25 = 0
                    r26 = 0
                    r27 = 0
                    r28 = 0
                    r29 = 0
                    r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31)
                    r2.<init>(r3)
                    com.android.systemui.navigationbar.store.NavBarStoreImpl r1 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r1
                    r1.apply(r0, r2)
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$5$1.apply(java.lang.Object):java.lang.Object");
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.runeDependency = z;
        m3.bandAidDependency = bandAid;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m3.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        m3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m3.priority = 2;
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) RemoteViewPack.this.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.InvalidateRemoteView(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m4 = ColorPack$$ExternalSyntheticOutline0.m(m3, arrayList);
        m4.runeDependency = z;
        m4.bandAidDependency = BandAid.REMOTE_VIEW_PACK_UPDATE_DARK_INTENSITY;
        m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateDarkIntensity.class);
        m4.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBarTransitions.class, NavigationBarView.class);
        m4.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnUpdateDarkIntensity onUpdateDarkIntensity = (EventTypeFactory.EventType.OnUpdateDarkIntensity) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, onUpdateDarkIntensity.darkIntensity, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388575, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m5 = ColorPack$$ExternalSyntheticOutline0.m(m4, arrayList);
        m5.runeDependency = z;
        m5.bandAidDependency = BandAid.REMOTE_VIEW_PACK_PACKAGE_REMOVED;
        m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnPackageRemoved.class);
        m5.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m5.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        m5.priority = 2;
        m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = RemoteViewPack.this;
                EventTypeFactory.EventType.OnPackageRemoved onPackageRemoved = (EventTypeFactory.EventType.OnPackageRemoved) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                int i2 = kit.displayId;
                NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) navBarStoreImpl.getModule(NavBarRemoteViewManager.class, i2);
                if (!navBarRemoteViewManager.leftViewList.isEmpty() || !navBarRemoteViewManager.rightViewList.isEmpty()) {
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
        arrayList.add(m5.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
