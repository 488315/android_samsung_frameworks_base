package com.android.systemui.navigationbar.bandaid.pack;

import android.content.res.Resources;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.recents.LauncherProxyService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
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
        boolean z2 = false;
        builder.moduleDependencies = Arrays.asList(NavigationBarView.class, GestureHintAnimator.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetGestureHintViewGroup(null, 1, null));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateGestureHintVisibility(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.runeDependency = z;
        builderM.bandAidDependency = BandAid.GESTURE_PACK_EDGE_BACK_GESTURE_DISABLE_BY_POLICY;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnEdgeBackGestureDisablePolicyChanged.class);
        builderM.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                boolean z3 = navBarStateManagerImpl.states.navigationMode == 2;
                navBarStateManagerImpl.logNavBarStates(Boolean.valueOf(z3), "isSideAndBottomGestureMode");
                if (z3) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, ((EventTypeFactory.EventType.OnEdgeBackGestureDisablePolicyChanged) kit.event).policy, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8380415, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.runeDependency = z && BasicRune.NAVBAR_REMOTEVIEW;
        builderM2.bandAidDependency = BandAid.GESTURE_PACK_SHOW_FLOATING_GAMETOOLS_ICON;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarTransitionModeChanged.class);
        builderM2.targetModules = Arrays.asList(NavigationBar.class, TaskbarDelegate.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                if (navBarStateManagerImpl.isGestureMode() && navBarStateManagerImpl.canShowFloatingGameTools(true) && ((EventTypeFactory.EventType.OnNavBarTransitionModeChanged) kit.event).transitionMode == 1) {
                    navBarStateManagerImpl.getSettingHelper().setGameToolsEnabled(true);
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.runeDependency = z;
        builderM3.bandAidDependency = BandAid.GESTURE_PACK_SET_HINT_VISIBILITY;
        builderM3.targetEvents = Arrays.asList(EventTypeFactory.EventType.OnUpdateSpayVisibility.class, EventTypeFactory.EventType.OnSetGestureHintVisibility.class);
        builderM3.targetModules = Arrays.asList(NavigationBar.class, SamsungNavigationBarView.class);
        builderM3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM3.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$7$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                ((NavBarStoreImpl) navBarStore).apply((Band.Kit) obj, new NavBarStoreAction.UpdateGestureHintVisibility(null, 1, null));
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.runeDependency = z;
        builderM4.bandAidDependency = BandAid.GESTURE_PACK_RESET_HINT_VI;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.ResetBottomGestureHintVI.class);
        builderM4.targetModules = Collections.singletonList(LauncherProxyService.class);
        builderM4.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ResetHintVI(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM5 = ColorPack$$ExternalSyntheticOutline0.m(builderM4, arrayList);
        builderM5.runeDependency = z;
        builderM5.bandAidDependency = BandAid.GESTURE_PACK_START_HINT_VI;
        builderM5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.StartBottomGestureHintVI.class);
        builderM5.targetModules = Collections.singletonList(LauncherProxyService.class);
        builderM5.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        builderM5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.StartHintVI(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, new NavBarStoreAction.GestureHintVIInfo(((EventTypeFactory.EventType.StartBottomGestureHintVI) kit.event).hintId, 0, 0, 0L, 14, null), null, false, false, null, 0.0f, 0.0f, 0, 0, 8372223, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM6 = ColorPack$$ExternalSyntheticOutline0.m(builderM5, arrayList);
        builderM6.runeDependency = z;
        builderM6.bandAidDependency = BandAid.GESTURE_PACK_MOVE_HINT_VI;
        builderM6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.MoveBottomGestureHintDistance.class);
        builderM6.targetModules = Collections.singletonList(LauncherProxyService.class);
        builderM6.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        builderM6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled()) {
                    EventTypeFactory.EventType.MoveBottomGestureHintDistance moveBottomGestureHintDistance = (EventTypeFactory.EventType.MoveBottomGestureHintDistance) kit.event;
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.MoveHintVI(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, new NavBarStoreAction.GestureHintVIInfo(moveBottomGestureHintDistance.hintId, moveBottomGestureHintDistance.distanceX, moveBottomGestureHintDistance.distanceY, moveBottomGestureHintDistance.duration), null, false, false, null, 0.0f, 0.0f, 0, 0, 8372223, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM7 = ColorPack$$ExternalSyntheticOutline0.m(builderM6, arrayList);
        builderM7.runeDependency = z;
        builderM7.bandAidDependency = BandAid.GESTURE_PACK_KNOX_HARD_KEY_INTENT_POLICY;
        builderM7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged.class);
        builderM7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM7.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                    action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(2199023255552L, ((NavBarStateManagerImpl) kit.manager).states.hardKeyIntentPolicy));
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                }
            }
        };
        Band.Builder builderM8 = ColorPack$$ExternalSyntheticOutline0.m(builderM7, arrayList);
        builderM8.runeDependency = z;
        builderM8.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_SYSTEMUI_STATE_FLAG;
        builderM8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSysUiStateFlag.class);
        builderM8.targetModules = Arrays.asList(LauncherProxyService.class, SamsungNavigationBarView.class);
        builderM8.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM8.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$17$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                    NavBarStateManager navBarStateManager = kit.manager;
                    NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navBarStateManager;
                    boolean z3 = navBarStateManagerImpl.isGameMode(true) && !NavBarStateManager.isIMEShowing$default(navBarStateManager);
                    boolean z4 = navBarStateManagerImpl.states.hardKeyIntentPolicy;
                    List list = action.sysUiFlagInfoList;
                    list.add(new NavBarStoreAction.SysUiFlagInfo(2199023255552L, z4));
                    list.add(new NavBarStoreAction.SysUiFlagInfo(137438953472L, z3));
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                }
            }
        };
        Band.Builder builderM9 = ColorPack$$ExternalSyntheticOutline0.m(builderM8, arrayList);
        builderM9.runeDependency = z;
        builderM9.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_ONEHAND_MODE_INFO;
        builderM9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOneHandModeChanged.class);
        builderM9.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                String str = ((EventTypeFactory.EventType.OnOneHandModeChanged) kit.event).info;
                if (str == null) {
                    return null;
                }
                List listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{";"}, 0, 6);
                if (listSplit$default.size() >= 3) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateOneHandModeInfo(new NavBarStoreAction.Action(new NavBarStoreAction.OneHandModeInfo(Integer.parseInt((String) listSplit$default.get(0)), Integer.parseInt((String) listSplit$default.get(1)), Float.parseFloat((String) listSplit$default.get(2))), null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388606, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM10 = ColorPack$$ExternalSyntheticOutline0.m(builderM9, arrayList);
        builderM10.runeDependency = z;
        builderM10.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_REGION_SAMPLING_RECT;
        builderM10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOneHandModeChanged.class);
        builderM10.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM10.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builderM10.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$21$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateRegionSamplingRect(null, 1, null));
                }
            }
        };
        Band.Builder builderM11 = ColorPack$$ExternalSyntheticOutline0.m(builderM10, arrayList);
        builderM11.runeDependency = z && BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        builderM11.bandAidDependency = BandAid.GESTURE_PACK_RECALCULATE_INSET_SCALE;
        builderM11.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnFoldStateChanged.class);
        builderM11.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM11.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$23$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.RecalculateGestureInsetScale(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, ((EventTypeFactory.EventType.OnFoldStateChanged) kit.event).folded, false, null, 0.0f, 0.0f, 0, 0, 8323071, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM12 = ColorPack$$ExternalSyntheticOutline0.m(builderM11, arrayList);
        if (BasicRune.NAVBAR_REMOTEVIEW && z) {
            z2 = true;
        }
        builderM12.runeDependency = z2;
        builderM12.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_GAMETOOLS_VISIBILITY;
        builderM12.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        builderM12.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        builderM12.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$25$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(137438953472L, ((NavBarStateManagerImpl) kit.manager).isGameMode(true) && !NavBarStateManager.isIMEShowing$default(kit.manager)));
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM13 = ColorPack$$ExternalSyntheticOutline0.m(builderM12, arrayList);
        builderM13.runeDependency = z;
        builderM13.bandAidDependency = BandAid.GESTURE_PACK_BOTTOM_SENSITIVITY_CHANGED;
        builderM13.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnBottomSensitivityChanged.class);
        builderM13.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM13.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$27$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                if (((NavBarStateManagerImpl) kit.manager).isBottomGestureMode(false) & (!((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled())) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM14 = ColorPack$$ExternalSyntheticOutline0.m(builderM13, arrayList);
        builderM14.runeDependency = z;
        builderM14.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_ACTIVE_INDICATOR_SPRING_PARAMS;
        builderM14.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams.class);
        builderM14.targetModules = EmptyList.INSTANCE;
        builderM14.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$29$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
                EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams onUpdateBackGestureActiveIndicatorParams = (EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateIndicatorSpringParams(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, onUpdateBackGestureActiveIndicatorParams.stiffness, onUpdateBackGestureActiveIndicatorParams.dampingRatio, 0, 0, 6815743, null)));
                return navBarStoreImpl;
            }
        };
        arrayList.add(builderM14.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
