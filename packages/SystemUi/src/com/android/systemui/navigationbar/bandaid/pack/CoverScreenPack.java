package com.android.systemui.navigationbar.bandaid.pack;

import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverScreenPack implements BandAidPack {
    public final List allBands;
    public int coverWindowState;
    public final int gestureSidePadding;
    public final int sidePadding;
    public final NavBarStore store;

    public CoverScreenPack(NavBarStore navBarStore) {
        this.store = navBarStore;
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        this.sidePadding = 399;
        this.gestureSidePadding = 66;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.COVER_SCREEN_PACK_NAVBAR_VISIBILITY_CHANGED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged.class);
        builder.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBar.class, NavBarStoreImpl.class);
        builder.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = CoverScreenPack.this;
                EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged onNavBarLargeCoverScreenVisibilityChanged = (EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged) kit.event;
                if (!kit.manager.isLargeCoverScreenSyncEnabled()) {
                    CoverScreenPack.access$updateLargeCoverNavBarVisibility(coverScreenPack, kit, (onNavBarLargeCoverScreenVisibilityChanged.imeShown || onNavBarLargeCoverScreenVisibilityChanged.coverTask) ? 0 : 8);
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m = ColorPack$$ExternalSyntheticOutline0.m164m(builder, arrayList);
        m164m.runeDependency = z;
        m164m.bandAidDependency = BandAid.COVER_SCREEN_PACK_NAVBAR_ATTACHED_TO_WINDOW;
        m164m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        m164m.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m164m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = CoverScreenPack.this;
                int i2 = kit.displayId;
                if (i2 == 1) {
                    NavigationBar navigationBar = (NavigationBar) ((NavBarStoreImpl) coverScreenPack.store).getModule(NavigationBar.class, i2);
                    navigationBar.setWindowState(i2, 2, coverScreenPack.coverWindowState);
                    AccessibilityGestureHandler accessibilityGestureHandler = navigationBar.mNavBarHelper.mAccessibilityGestureHandler;
                    accessibilityGestureHandler.isAttached = true;
                    Log.d(accessibilityGestureHandler.tag, "onNavBarAttached");
                    accessibilityGestureHandler.updateIsEnabled();
                    NavBarStateManager navBarStateManager = kit.manager;
                    if (!navBarStateManager.isLargeCoverScreenSyncEnabled()) {
                        CoverScreenPack.access$updateLargeCoverNavBarVisibility(coverScreenPack, kit, navBarStateManager.isLargeCoverTaskEnabled() ? 0 : 8);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        m164m.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$3$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((NavBarStoreImpl) CoverScreenPack.this.store).apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
            }
        };
        Band.Builder m164m2 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m, arrayList);
        m164m2.runeDependency = z;
        m164m2.bandAidDependency = BandAid.COVER_SCREEN_PACK_NAVBAR_DETACHED_TO_WINDOW;
        m164m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarDetachedFromWindow.class);
        m164m2.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m164m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                View rootView;
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = CoverScreenPack.this;
                int i2 = kit.displayId;
                if (i2 == 1) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194303, null);
                    NavigationBarView navigationBarView = (NavigationBarView) ((NavBarStoreImpl) coverScreenPack.store).getModule(NavigationBarView.class, 0);
                    action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_NIO, (navigationBarView == null || (rootView = navigationBarView.getRootView()) == null || rootView.getVisibility() != 8) ? false : true));
                    NavBarStoreAction.UpdateSysUiFlags updateSysUiFlags = new NavBarStoreAction.UpdateSysUiFlags(action);
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) coverScreenPack.store;
                    navBarStoreImpl.apply(kit, updateSysUiFlags);
                    AccessibilityGestureHandler accessibilityGestureHandler = ((NavigationBar) navBarStoreImpl.getModule(NavigationBar.class, i2)).mNavBarHelper.mAccessibilityGestureHandler;
                    accessibilityGestureHandler.isAttached = false;
                    Log.d(accessibilityGestureHandler.tag, "onNavBarDetached");
                    accessibilityGestureHandler.disposeInputChannel();
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m3 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m2, arrayList);
        m164m3.runeDependency = z;
        m164m3.bandAidDependency = BandAid.COVER_SCREEN_PACK_GET_NAVBAR_PADDING;
        m164m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding.class);
        m164m3.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        m164m3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = CoverScreenPack.this;
                EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding getNavBarLargeCoverScreenPadding = (EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding) kit.event;
                if (kit.manager.isGestureMode()) {
                    int i2 = getNavBarLargeCoverScreenPadding.rotation;
                    return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? new Rect(0, 0, coverScreenPack.sidePadding, 0) : new Rect(coverScreenPack.gestureSidePadding, 0, 0, 0) : new Rect(0, 0, 0, 0) : new Rect(0, 0, coverScreenPack.gestureSidePadding, 0) : new Rect(0, 0, coverScreenPack.sidePadding, 0);
                }
                int i3 = getNavBarLargeCoverScreenPadding.rotation;
                return i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? new Rect(0, 0, coverScreenPack.sidePadding, 0) : new Rect(0, 0, 0, coverScreenPack.sidePadding) : new Rect(coverScreenPack.sidePadding, 0, 0, 0) : new Rect(0, coverScreenPack.sidePadding, 0, 0) : new Rect(0, 0, coverScreenPack.sidePadding, 0);
            }
        };
        Band.Builder m164m4 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m3, arrayList);
        m164m4.runeDependency = z;
        m164m4.bandAidDependency = BandAid.COVER_SCREEN_PACK_FOLD_STATE_CHANGED;
        m164m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnFoldStateChanged.class);
        m164m4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m4.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = CoverScreenPack.this;
                if (!((EventTypeFactory.EventType.OnFoldStateChanged) kit.event).folded) {
                    ((NavBarStoreImpl) coverScreenPack.store).apply(kit, new NavBarStoreAction.UpdateDefaultNavigationBarStatus(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m5 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m4, arrayList);
        m164m5.runeDependency = z;
        m164m5.bandAidDependency = BandAid.COVER_SCREEN_PACK_GET_DEADZONE_SIZE;
        m164m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDeadZoneSize.class);
        m164m5.targetModules = Collections.singletonList(DeadZone.class);
        m164m5.priority = 2;
        m164m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                if (kit.displayId != 1) {
                    return Unit.INSTANCE;
                }
                boolean z2 = ((EventTypeFactory.EventType.GetDeadZoneSize) kit.event).maxSize;
                return 0;
            }
        };
        Band.Builder m164m6 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m5, arrayList);
        m164m6.runeDependency = z;
        m164m6.bandAidDependency = BandAid.COVER_SCREEN_PACK_COVER_WINDOW_STATE;
        m164m6.targetEvents = CollectionsKt__CollectionsKt.listOf(EventTypeFactory.EventType.OnNavBarWindowStateShowing.class, EventTypeFactory.EventType.OnNavBarWindowStateHidden.class);
        m164m6.targetModules = Collections.singletonList(NavBarHelper.class);
        m164m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = CoverScreenPack.this;
                if (kit.displayId == 1) {
                    coverScreenPack.coverWindowState = kit.event instanceof EventTypeFactory.EventType.OnNavBarWindowStateShowing ? 0 : 2;
                }
                return Unit.INSTANCE;
            }
        };
        m164m6.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$13$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final CoverScreenPack coverScreenPack = CoverScreenPack.this;
                if (((Band.Kit) obj).displayId == 0 && ((NavBarStoreImpl) coverScreenPack.store).getNavStateManager(1).isCoverDisplayNavBarEnabled()) {
                    ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$13$2$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            NavigationBar navigationBar = (NavigationBar) ((NavBarStoreImpl) CoverScreenPack.this.store).getModule(NavigationBar.class, 1);
                            if (navigationBar != null) {
                                navigationBar.updateSystemUiStateFlags();
                            }
                        }
                    }, 250L);
                }
            }
        };
        Band.Builder m164m7 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m6, arrayList);
        m164m7.runeDependency = z;
        m164m7.bandAidDependency = BandAid.COVER_SCREEN_PACK_ROTATION_CHANGED;
        m164m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnCoverRotationChanged.class);
        m164m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m7.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) CoverScreenPack.this.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
            }
        };
        arrayList.add(m164m7.build());
    }

    public static final void access$updateLargeCoverNavBarVisibility(CoverScreenPack coverScreenPack, Band.Kit kit, int i) {
        coverScreenPack.getClass();
        NavBarStoreAction.SetNavBarVisibility setNavBarVisibility = new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, i, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4190207, null));
        NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) coverScreenPack.store;
        navBarStoreImpl.apply(kit, setNavBarVisibility);
        NavigationBar navigationBar = (NavigationBar) navBarStoreImpl.getModule(NavigationBar.class, kit.displayId);
        RegionSamplingHelper regionSamplingHelper = navigationBar.mRegionSamplingHelper;
        regionSamplingHelper.mIsWindowGone = i == 8;
        regionSamplingHelper.updateSamplingListener();
        boolean z = i == 8;
        EdgeBackGestureHandler edgeBackGestureHandler = navigationBar.mEdgeBackGestureHandler;
        edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled = z;
        edgeBackGestureHandler.updateIsEnabled();
        edgeBackGestureHandler.updateCurrentUserResources();
        boolean z2 = i == 8;
        AccessibilityGestureHandler accessibilityGestureHandler = navigationBar.mNavBarHelper.mAccessibilityGestureHandler;
        accessibilityGestureHandler.isCoverNavBarVisible = !z2;
        Log.d(accessibilityGestureHandler.tag, "coverNavbarVisibilityChanged");
        accessibilityGestureHandler.updateIsEnabled();
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
