package com.android.systemui.navigationbar.bandaid.pack;

import android.provider.Settings;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GesturePack implements BandAidPack {
    public final List allBands;

    public GesturePack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_GESTURE;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.GESTURE_PACK_SET_HINT_GROUP;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarAttachedToWindow.class);
        builder.targetModules = Collections.singletonList(SamsungNavigationBarView.class);
        builder.moduleDependencies = CollectionsKt__CollectionsKt.listOf(NavigationBarView.class, GestureHintAnimator.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetGestureHintViewGroup(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateGestureHintVisibility(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m = ColorPack$$ExternalSyntheticOutline0.m164m(builder, arrayList);
        m164m.runeDependency = z;
        m164m.bandAidDependency = BandAid.GESTURE_PACK_EDGE_BACK_GESTURE_DISABLE_BY_POLICY;
        m164m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged.class);
        m164m.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged onEdgeBackGestureDisabledByPolicyChanged = (EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged) kit.event;
                if (kit.manager.isSideAndBottomGestureMode(true)) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, onEdgeBackGestureDisabledByPolicyChanged.disabled, null, null, false, false, null, 0.0f, 0.0f, 0, 4186111, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m2 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m, arrayList);
        m164m2.runeDependency = z && BasicRune.NAVBAR_REMOTEVIEW;
        m164m2.bandAidDependency = BandAid.GESTURE_PACK_SHOW_FLOATING_GAMETOOLS_ICON;
        m164m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarTransitionModeChanged.class);
        m164m2.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBar.class, TaskbarDelegate.class);
        m164m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.OnNavBarTransitionModeChanged onNavBarTransitionModeChanged = (EventTypeFactory.EventType.OnNavBarTransitionModeChanged) kit.event;
                NavBarStateManager navBarStateManager = kit.manager;
                if (navBarStateManager.isGestureMode() && navBarStateManager.canShowFloatingGameTools(true) && onNavBarTransitionModeChanged.transitionMode == 1) {
                    Settings.Secure.putInt(navBarStateManager.settingsHelper.mResolver, "game_show_floating_icon", 1);
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m3 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m2, arrayList);
        m164m3.runeDependency = z;
        m164m3.bandAidDependency = BandAid.GESTURE_PACK_SET_HINT_VISIBILITY;
        m164m3.targetEvents = CollectionsKt__CollectionsKt.listOf(EventTypeFactory.EventType.OnUpdateSpayVisibility.class, EventTypeFactory.EventType.OnSetGestureHintVisibility.class);
        m164m3.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBar.class, SamsungNavigationBarView.class);
        m164m3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m3.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$7$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((NavBarStoreImpl) NavBarStore.this).apply((Band.Kit) obj, new NavBarStoreAction.UpdateGestureHintVisibility(null, 1, null));
            }
        };
        Band.Builder m164m4 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m3, arrayList);
        m164m4.runeDependency = z;
        m164m4.bandAidDependency = BandAid.GESTURE_PACK_RESET_HINT_VI;
        m164m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.ResetBottomGestureHintVI.class);
        m164m4.targetModules = Collections.singletonList(OverviewProxyService.class);
        m164m4.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        m164m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (kit.manager.isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ResetHintVI(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m5 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m4, arrayList);
        m164m5.runeDependency = z;
        m164m5.bandAidDependency = BandAid.GESTURE_PACK_START_HINT_VI;
        m164m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.StartBottomGestureHintVI.class);
        m164m5.targetModules = Collections.singletonList(OverviewProxyService.class);
        m164m5.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        m164m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.StartBottomGestureHintVI startBottomGestureHintVI = (EventTypeFactory.EventType.StartBottomGestureHintVI) kit.event;
                if (kit.manager.isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.StartHintVI(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, new NavBarStoreAction.GestureHintVIInfo(startBottomGestureHintVI.hintId, 0, 0, 0L, 14, null), null, false, false, null, 0.0f, 0.0f, 0, 4177919, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m6 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m5, arrayList);
        m164m6.runeDependency = z;
        m164m6.bandAidDependency = BandAid.GESTURE_PACK_MOVE_HINT_VI;
        m164m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.MoveBottomGestureHintDistance.class);
        m164m6.targetModules = Collections.singletonList(OverviewProxyService.class);
        m164m6.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        m164m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.MoveBottomGestureHintDistance moveBottomGestureHintDistance = (EventTypeFactory.EventType.MoveBottomGestureHintDistance) kit.event;
                if (kit.manager.isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.MoveHintVI(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, new NavBarStoreAction.GestureHintVIInfo(moveBottomGestureHintDistance.hintId, moveBottomGestureHintDistance.distanceX, moveBottomGestureHintDistance.distanceY, moveBottomGestureHintDistance.duration), null, false, false, null, 0.0f, 0.0f, 0, 4177919, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m7 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m6, arrayList);
        m164m7.runeDependency = z;
        m164m7.bandAidDependency = BandAid.GESTURE_PACK_KNOX_HARD_KEY_INTENT_POLICY;
        m164m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged.class);
        m164m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m7.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManager navBarStateManager = kit.manager;
                if (navBarStateManager.isGestureMode()) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194303, null);
                    action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_NIO_TEXT, navBarStateManager.states.hardKeyIntentPolicy));
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                }
            }
        };
        Band.Builder m164m8 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m7, arrayList);
        m164m8.runeDependency = z;
        m164m8.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_SYSTEMUI_STATE_FLAG;
        m164m8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSysUiStateFlag.class);
        m164m8.targetModules = CollectionsKt__CollectionsKt.listOf(OverviewProxyService.class, SamsungNavigationBarView.class);
        m164m8.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m8.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$17$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManager navBarStateManager = kit.manager;
                if (navBarStateManager.isGestureMode()) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194303, null);
                    boolean z2 = navBarStateManager.isGameMode(true) && !navBarStateManager.isIMEShowing(true);
                    NavBarStoreAction.SysUiFlagInfo sysUiFlagInfo = new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_NIO_TEXT, navBarStateManager.states.hardKeyIntentPolicy);
                    List list = action.sysUiFlagInfoList;
                    list.add(sysUiFlagInfo);
                    list.add(new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_STATUS_BAR, z2));
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                }
            }
        };
        Band.Builder m164m9 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m8, arrayList);
        m164m9.runeDependency = z;
        m164m9.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_ONEHAND_MODE_INFO;
        m164m9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOneHandModeChanged.class);
        m164m9.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                String str = ((EventTypeFactory.EventType.OnOneHandModeChanged) kit.event).info;
                if (str == null) {
                    return null;
                }
                List split$default = StringsKt__StringsKt.split$default(str, new String[]{";"}, 0, 6);
                if (split$default.size() >= 3) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateOneHandModeInfo(new NavBarStoreAction.Action(new NavBarStoreAction.OneHandModeInfo(Integer.parseInt((String) split$default.get(0)), Integer.parseInt((String) split$default.get(1)), Float.parseFloat((String) split$default.get(2))), null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194302, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m10 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m9, arrayList);
        m164m10.runeDependency = z;
        m164m10.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_REGION_SAMPLING_RECT;
        m164m10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOneHandModeChanged.class);
        m164m10.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m10.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m10.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$21$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (kit.manager.isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateRegionSamplingRect(null, 1, null));
                }
            }
        };
        Band.Builder m164m11 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m10, arrayList);
        m164m11.runeDependency = z && BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        m164m11.bandAidDependency = BandAid.GESTURE_PACK_RECALCULATE_INSET_SCALE;
        m164m11.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnFoldStateChanged.class);
        m164m11.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m11.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$23$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.OnFoldStateChanged onFoldStateChanged = (EventTypeFactory.EventType.OnFoldStateChanged) kit.event;
                if (kit.manager.isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.RecalculateGestureInsetScale(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, onFoldStateChanged.folded, false, null, 0.0f, 0.0f, 0, 4128767, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m12 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m11, arrayList);
        m164m12.runeDependency = BasicRune.NAVBAR_REMOTEVIEW && z;
        m164m12.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_GAMETOOLS_VISIBILITY;
        m164m12.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m164m12.targetModules = Collections.singletonList(NavigationBarController.class);
        m164m12.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$25$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194303, null);
                NavBarStateManager navBarStateManager = kit.manager;
                int i2 = NavBarStateManager.$r8$clinit;
                action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_STATUS_BAR, navBarStateManager.isGameMode(true) && !navBarStateManager.isIMEShowing(true)));
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m13 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m12, arrayList);
        m164m13.runeDependency = z;
        m164m13.bandAidDependency = BandAid.GESTURE_PACK_BOTTOM_SENSITIVITY_CHANGED;
        m164m13.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnBottomSensitivityChanged.class);
        m164m13.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m13.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$27$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManager navBarStateManager = kit.manager;
                int i2 = NavBarStateManager.$r8$clinit;
                if ((!navBarStateManager.isGestureHintEnabled()) & navBarStateManager.isBottomGestureMode(false)) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m14 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m13, arrayList);
        m164m14.runeDependency = z;
        m164m14.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_ACTIVE_INDICATOR_SPRING_PARAMS;
        m164m14.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams.class);
        m164m14.targetModules = EmptyList.INSTANCE;
        m164m14.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$29$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams onUpdateBackGestureActiveIndicatorParams = (EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateIndicatorSpringParams(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, onUpdateBackGestureActiveIndicatorParams.stiffness, onUpdateBackGestureActiveIndicatorParams.dampingRatio, 0, 2621439, null)));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m164m14.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
