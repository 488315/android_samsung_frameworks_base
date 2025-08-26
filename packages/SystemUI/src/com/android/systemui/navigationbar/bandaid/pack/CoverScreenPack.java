package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.navigationbar.views.buttons.DeadZone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;

/* loaded from: classes2.dex */
public final class CoverScreenPack implements BandAidPack {
    public final List allBands;
    public int coverWindowState;
    public final NavBarStore store;

    public CoverScreenPack(NavBarStore navBarStore) {
        this.store = navBarStore;
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.COVER_SCREEN_PACK_NAVBAR_VISIBILITY_CHANGED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged.class);
        builder.targetModules = Arrays.asList(NavigationBar.class, NavBarStoreImpl.class);
        builder.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = this.this$0;
                if (!((NavBarStateManagerImpl) kit.manager).isLargeCoverScreenSyncEnabled()) {
                    EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged onNavBarLargeCoverScreenVisibilityChanged = (EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged) kit.event;
                    CoverScreenPack.access$updateLargeCoverNavBarVisibility(coverScreenPack, kit, (onNavBarLargeCoverScreenVisibilityChanged.imeShown || onNavBarLargeCoverScreenVisibilityChanged.coverTask) ? 0 : 8);
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.runeDependency = z;
        builderM.bandAidDependency = BandAid.COVER_SCREEN_PACK_NAVBAR_ATTACHED_TO_WINDOW;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        builderM.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = this.this$0;
                int i2 = kit.displayId;
                if (i2 == 1) {
                    NavigationBar navigationBar = (NavigationBar) ((NavBarStoreImpl) coverScreenPack.store).getModule(NavigationBar.class, i2);
                    navigationBar.setWindowState(kit.displayId, 2, coverScreenPack.coverWindowState);
                    AccessibilityGestureHandler accessibilityGestureHandler = navigationBar.mNavBarHelper.mAccessibilityGestureHandler;
                    accessibilityGestureHandler.isAttached = true;
                    Log.d("AccessibilityGestureHandler", "onNavBarAttached");
                    accessibilityGestureHandler.updateIsEnabled();
                    NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                    if (!navBarStateManagerImpl.isLargeCoverScreenSyncEnabled()) {
                        CoverScreenPack.access$updateLargeCoverNavBarVisibility(coverScreenPack, kit, navBarStateManagerImpl.isLargeCoverTaskEnabled() ? 0 : 8);
                    }
                }
                return Unit.INSTANCE;
            }
        };
        builderM.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$3$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                ((NavBarStoreImpl) this.this$0.store).apply((Band.Kit) obj, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.runeDependency = z;
        builderM2.bandAidDependency = BandAid.COVER_SCREEN_PACK_NAVBAR_DETACHED_TO_WINDOW;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarDetachedFromWindow.class);
        builderM2.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                View rootView;
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = this.this$0;
                if (kit.displayId == 1) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                    NavigationBarView navigationBarView = (NavigationBarView) ((NavBarStoreImpl) coverScreenPack.store).getModule(NavigationBarView.class, 0);
                    action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(1099511627776L, (navigationBarView == null || (rootView = navigationBarView.getRootView()) == null || rootView.getVisibility() != 8) ? false : true));
                    NavBarStoreAction.UpdateSysUiFlags updateSysUiFlags = new NavBarStoreAction.UpdateSysUiFlags(action);
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) coverScreenPack.store;
                    navBarStoreImpl.apply(kit, updateSysUiFlags);
                    AccessibilityGestureHandler accessibilityGestureHandler = ((NavigationBar) navBarStoreImpl.getModule(NavigationBar.class, kit.displayId)).mNavBarHelper.mAccessibilityGestureHandler;
                    accessibilityGestureHandler.isAttached = false;
                    Log.d("AccessibilityGestureHandler", "onNavBarDetached");
                    accessibilityGestureHandler.disposeInputChannel();
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.runeDependency = z;
        builderM3.bandAidDependency = BandAid.COVER_SCREEN_PACK_GET_NAVBAR_PADDING;
        builderM3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding.class);
        builderM3.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builderM3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = this.this$0;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) coverScreenPack.store;
                int i2 = kit.displayId;
                Resources resources = ((Context) navBarStoreImpl.getModule(Context.class, i2)).getResources();
                Log.d("CoverScreenPack", "get cover screen padding, kit.displayId=" + i2 + ", resources=" + resources.getConfiguration());
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.large_cover_navigation_bar_side_padding);
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.large_cover_button_opposite_padding);
                int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.large_cover_button_inset_height);
                boolean zIsGestureMode = ((NavBarStateManagerImpl) kit.manager).isGestureMode();
                EventTypeFactory.EventType eventType = kit.event;
                if (zIsGestureMode) {
                    int i3 = ((EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding) eventType).rotation;
                    return i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? new Rect(0, 0, dimensionPixelSize, 0) : new Rect(dimensionPixelSize3, 0, 0, 0) : new Rect(0, 0, 0, 0) : new Rect(0, 0, dimensionPixelSize3, 0) : new Rect(0, 0, dimensionPixelSize, 0);
                }
                int i4 = ((EventTypeFactory.EventType.GetNavBarLargeCoverScreenPadding) eventType).rotation;
                return i4 != 0 ? i4 != 1 ? i4 != 2 ? i4 != 3 ? new Rect(dimensionPixelSize2, 0, dimensionPixelSize, 0) : new Rect(0, dimensionPixelSize2, 0, dimensionPixelSize) : new Rect(dimensionPixelSize, 0, dimensionPixelSize2, 0) : new Rect(0, dimensionPixelSize, 0, dimensionPixelSize2) : new Rect(dimensionPixelSize2, 0, dimensionPixelSize, 0);
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.runeDependency = z;
        builderM4.bandAidDependency = BandAid.COVER_SCREEN_PACK_FOLD_STATE_CHANGED;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnFoldStateChanged.class);
        builderM4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM4.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = this.this$0;
                if (!((EventTypeFactory.EventType.OnFoldStateChanged) kit.event).folded) {
                    ((NavBarStoreImpl) coverScreenPack.store).apply(kit, new NavBarStoreAction.UpdateDefaultNavigationBarStatus(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM5 = ColorPack$$ExternalSyntheticOutline0.m(builderM4, arrayList);
        builderM5.runeDependency = z;
        builderM5.bandAidDependency = BandAid.COVER_SCREEN_PACK_GET_DEADZONE_SIZE;
        builderM5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.GetDeadZoneSize.class);
        builderM5.targetModules = Collections.singletonList(DeadZone.class);
        builderM5.priority = 2;
        builderM5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$11$1
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
        Band.Builder builderM6 = ColorPack$$ExternalSyntheticOutline0.m(builderM5, arrayList);
        builderM6.runeDependency = z;
        builderM6.bandAidDependency = BandAid.COVER_SCREEN_PACK_COVER_WINDOW_STATE;
        builderM6.targetEvents = Arrays.asList(EventTypeFactory.EventType.OnNavBarWindowStateShowing.class, EventTypeFactory.EventType.OnNavBarWindowStateHidden.class);
        builderM6.targetModules = Collections.singletonList(NavBarHelper.class);
        builderM6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                CoverScreenPack coverScreenPack = this.this$0;
                if (kit.displayId == 1) {
                    coverScreenPack.coverWindowState = kit.event instanceof EventTypeFactory.EventType.OnNavBarWindowStateShowing ? 0 : 2;
                }
                return Unit.INSTANCE;
            }
        };
        builderM6.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$13$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final NavigationBar navigationBar;
                Handler handler;
                CoverScreenPack coverScreenPack = this.this$0;
                if (((Band.Kit) obj).displayId != 0 || !((NavBarStateManagerImpl) ((NavBarStoreImpl) coverScreenPack.store).getNavStateManager(1)).isCoverDisplayNavBarEnabled() || (navigationBar = (NavigationBar) ((NavBarStoreImpl) coverScreenPack.store).getModule(NavigationBar.class, 1)) == null || (handler = navigationBar.mHandler) == null) {
                    return;
                }
                handler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$13$2$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        navigationBar.updateSystemUiStateFlags();
                    }
                }, 250L);
            }
        };
        Band.Builder builderM7 = ColorPack$$ExternalSyntheticOutline0.m(builderM6, arrayList);
        builderM7.runeDependency = z;
        builderM7.bandAidDependency = BandAid.COVER_SCREEN_PACK_ROTATION_CHANGED;
        builderM7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnCoverRotationChanged.class);
        builderM7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM7.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.CoverScreenPack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.this$0.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.ReinflateNavBar(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarIconAndHints(null, 1, null));
            }
        };
        arrayList.add(builderM7.build());
    }

    public static final void access$updateLargeCoverNavBarVisibility(CoverScreenPack coverScreenPack, Band.Kit kit, int i) throws Resources.NotFoundException {
        coverScreenPack.getClass();
        NavBarStoreAction.SetNavBarVisibility setNavBarVisibility = new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, i, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null));
        NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) coverScreenPack.store;
        navBarStoreImpl.apply(kit, setNavBarVisibility);
        NavigationBar navigationBar = (NavigationBar) navBarStoreImpl.getModule(NavigationBar.class, kit.displayId);
        EdgeBackGestureHandler edgeBackGestureHandler = navigationBar.mEdgeBackGestureHandler;
        edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled = i == 8;
        edgeBackGestureHandler.updateIsEnabled();
        edgeBackGestureHandler.updateCurrentUserResources();
        boolean z = i == 8;
        AccessibilityGestureHandler accessibilityGestureHandler = navigationBar.mNavBarHelper.mAccessibilityGestureHandler;
        accessibilityGestureHandler.isCoverNavBarVisible = !z;
        Log.d("AccessibilityGestureHandler", "coverNavbarVisibilityChanged");
        accessibilityGestureHandler.updateIsEnabled();
        navigationBar.getView().updateNavButtonIcons();
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
