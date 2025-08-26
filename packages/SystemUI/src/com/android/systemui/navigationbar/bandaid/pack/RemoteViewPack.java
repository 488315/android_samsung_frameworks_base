package com.android.systemui.navigationbar.bandaid.pack;

import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBarTransitions;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.navigationbar.views.buttons.ContextualButtonGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
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
        builder.targetEvents = Arrays.asList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class, EventTypeFactory.EventType.OnInvalidateRemoteViews.class);
        builder.targetModules = Arrays.asList(SamsungNavigationBarView.class, ContextualButtonGroup.class);
        builder.moduleDependencies = Arrays.asList(NavBarRemoteViewManager.class, NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.this$0.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.InvalidateRemoteView(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.runeDependency = z;
        builderM.bandAidDependency = BandAid.REMOTE_VIEW_PACK_SET_REMOTEVIEW_CONTAINER;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateRemoteViewContainer.class);
        builderM.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM.moduleDependencies = Arrays.asList(NavBarRemoteViewManager.class, NavigationBarView.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = this.this$0;
                EventTypeFactory.EventType.OnUpdateRemoteViewContainer onUpdateRemoteViewContainer = (EventTypeFactory.EventType.OnUpdateRemoteViewContainer) kit.event;
                NavBarStore navBarStore2 = remoteViewPack.store;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewContainer(new NavBarStoreAction.Action(null, null, onUpdateRemoteViewContainer.leftContainer, onUpdateRemoteViewContainer.rightContainer, onUpdateRemoteViewContainer.contextualButtonVisible, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, onUpdateRemoteViewContainer.displayId, onUpdateRemoteViewContainer.rotation, 2097123, null)));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, ((EventTypeFactory.EventType.OnUpdateRemoteViewContainer) kit.event).darkIntensity, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388575, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.runeDependency = z;
        BandAid bandAid = BandAid.REMOTE_VIEW_PACK_SET_NAVBAR_SHORTCUT_TO_MANAGER;
        builderM2.bandAidDependency = bandAid;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        builderM2.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        builderM2.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$5$1
            /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) throws Resources.NotFoundException {
                int i2;
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = this.this$0;
                EventTypeFactory.EventType.OnSetRemoteView onSetRemoteView = (EventTypeFactory.EventType.OnSetRemoteView) kit.event;
                String str = onSetRemoteView.requestClass;
                if (str != null) {
                    i2 = (StringsKt__StringsKt.contains(str, "honeyboard", false) && ((NavBarStateManagerImpl) kit.manager).canShowKeyboardButtonOnLeft()) ? 0 : onSetRemoteView.position;
                }
                NavBarStoreAction.RemoteViewShortcut remoteViewShortcut = new NavBarStoreAction.RemoteViewShortcut(onSetRemoteView.requestClass, onSetRemoteView.remoteViews, i2, onSetRemoteView.priority);
                Log.d("RemoteViewPack", "OnSetRemoteView " + remoteViewShortcut);
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewShortcut(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, remoteViewShortcut, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388543, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.runeDependency = z;
        builderM3.bandAidDependency = bandAid;
        builderM3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        builderM3.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        builderM3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM3.priority = 2;
        builderM3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.this$0.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.InvalidateRemoteView(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.runeDependency = z;
        builderM4.bandAidDependency = BandAid.REMOTE_VIEW_PACK_UPDATE_DARK_INTENSITY;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateDarkIntensity.class);
        builderM4.targetModules = Arrays.asList(NavigationBarTransitions.class, NavigationBarView.class);
        builderM4.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = this.this$0;
                EventTypeFactory.EventType.OnUpdateDarkIntensity onUpdateDarkIntensity = (EventTypeFactory.EventType.OnUpdateDarkIntensity) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateRemoteViewDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, onUpdateDarkIntensity.darkIntensity, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388575, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM5 = ColorPack$$ExternalSyntheticOutline0.m(builderM4, arrayList);
        builderM5.runeDependency = z;
        builderM5.bandAidDependency = BandAid.REMOTE_VIEW_PACK_PACKAGE_REMOVED;
        builderM5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnPackageRemoved.class);
        builderM5.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM5.moduleDependencies = Collections.singletonList(NavBarRemoteViewManager.class);
        builderM5.priority = 2;
        builderM5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.RemoteViewPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                RemoteViewPack remoteViewPack = this.this$0;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) remoteViewPack.store;
                int i2 = kit.displayId;
                NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) navBarStoreImpl.getModule(NavBarRemoteViewManager.class, i2);
                if (!navBarRemoteViewManager.leftViewList.isEmpty() || !navBarRemoteViewManager.rightViewList.isEmpty()) {
                    final String str = ((EventTypeFactory.EventType.OnPackageRemoved) kit.event).packageName;
                    PriorityQueue priorityQueue = navBarRemoteViewManager.leftViewList;
                    final int i3 = 0;
                    final Function1 function1 = new Function1() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            String str2 = str;
                            NavBarRemoteView navBarRemoteView = (NavBarRemoteView) obj2;
                            switch (i3) {
                                case 0:
                                    int i4 = NavBarRemoteViewManager.$r8$clinit;
                                    break;
                                default:
                                    int i5 = NavBarRemoteViewManager.$r8$clinit;
                                    break;
                            }
                            return Boolean.valueOf(StringsKt__StringsJVMKt.equals(navBarRemoteView.requestClass, str2, false));
                        }
                    };
                    boolean zRemoveIf = priorityQueue.removeIf(new Predicate() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$sam$java_util_function_Predicate$0
                        @Override // java.util.function.Predicate
                        public final /* synthetic */ boolean test(Object obj2) {
                            return ((Boolean) function1.mo781invoke(obj2)).booleanValue();
                        }
                    });
                    PriorityQueue priorityQueue2 = navBarRemoteViewManager.rightViewList;
                    final int i4 = 1;
                    final Function1 function12 = new Function1() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            String str2 = str;
                            NavBarRemoteView navBarRemoteView = (NavBarRemoteView) obj2;
                            switch (i4) {
                                case 0:
                                    int i42 = NavBarRemoteViewManager.$r8$clinit;
                                    break;
                                default:
                                    int i5 = NavBarRemoteViewManager.$r8$clinit;
                                    break;
                            }
                            return Boolean.valueOf(StringsKt__StringsJVMKt.equals(navBarRemoteView.requestClass, str2, false));
                        }
                    };
                    if (priorityQueue2.removeIf(new Predicate() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$sam$java_util_function_Predicate$0
                        @Override // java.util.function.Predicate
                        public final /* synthetic */ boolean test(Object obj2) {
                            return ((Boolean) function12.mo781invoke(obj2)).booleanValue();
                        }
                    }) | zRemoveIf) {
                        navBarRemoteViewManager.updateRemoteViewContainer(kit.states.rotation, navBarRemoteViewManager.leftContainer, navBarRemoteViewManager.rightContainer, i2);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        arrayList.add(builderM5.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
