package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SamsungNavigationBarView;
import com.android.systemui.navigationbar.SecTaskBarManagerImpl;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
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
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = z;
        m.bandAidDependency = BandAid.GESTURE_PACK_EDGE_BACK_GESTURE_DISABLE_BY_POLICY;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged.class);
        m.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged onEdgeBackGestureDisabledByPolicyChanged = (EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged) kit.event;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                boolean z3 = navBarStateManagerImpl.states.navigationMode == 2;
                navBarStateManagerImpl.logNavBarStates(Boolean.valueOf(z3), "isSideAndBottomGestureMode");
                if (z3) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateEdgeBackGestureDisabledPolicy(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, onEdgeBackGestureDisabledByPolicyChanged.disabled, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8380415, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.runeDependency = z && BasicRune.NAVBAR_REMOTEVIEW;
        m2.bandAidDependency = BandAid.GESTURE_PACK_SHOW_FLOATING_GAMETOOLS_ICON;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarTransitionModeChanged.class);
        m2.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBar.class, TaskbarDelegate.class, SecTaskBarManagerImpl.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                EventTypeFactory.EventType.OnNavBarTransitionModeChanged onNavBarTransitionModeChanged = (EventTypeFactory.EventType.OnNavBarTransitionModeChanged) kit.event;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                if (navBarStateManagerImpl.isGestureMode() && navBarStateManagerImpl.canShowFloatingGameTools(true) && onNavBarTransitionModeChanged.transitionMode == 1) {
                    navBarStateManagerImpl.getSettingHelper().setGameToolsEnabled(true);
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.runeDependency = z;
        m3.bandAidDependency = BandAid.GESTURE_PACK_SET_HINT_VISIBILITY;
        m3.targetEvents = CollectionsKt__CollectionsKt.listOf(EventTypeFactory.EventType.OnUpdateSpayVisibility.class, EventTypeFactory.EventType.OnSetGestureHintVisibility.class);
        m3.targetModules = CollectionsKt__CollectionsKt.listOf(NavigationBar.class, SamsungNavigationBarView.class);
        m3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m3.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$7$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((NavBarStoreImpl) NavBarStore.this).apply((Band.Kit) obj, new NavBarStoreAction.UpdateGestureHintVisibility(null, 1, null));
            }
        };
        Band.Builder m4 = ColorPack$$ExternalSyntheticOutline0.m(m3, arrayList);
        m4.runeDependency = z;
        m4.bandAidDependency = BandAid.GESTURE_PACK_RESET_HINT_VI;
        m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.ResetBottomGestureHintVI.class);
        m4.targetModules = Collections.singletonList(OverviewProxyService.class);
        m4.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ResetHintVI(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m5 = ColorPack$$ExternalSyntheticOutline0.m(m4, arrayList);
        m5.runeDependency = z;
        m5.bandAidDependency = BandAid.GESTURE_PACK_START_HINT_VI;
        m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.StartBottomGestureHintVI.class);
        m5.targetModules = Collections.singletonList(OverviewProxyService.class);
        m5.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.StartBottomGestureHintVI startBottomGestureHintVI = (EventTypeFactory.EventType.StartBottomGestureHintVI) kit.event;
                if (((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.StartHintVI(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, new NavBarStoreAction.GestureHintVIInfo(startBottomGestureHintVI.hintId, 0, 0, 0L, 14, null), null, false, false, null, 0.0f, 0.0f, 0, 0, 8372223, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m6 = ColorPack$$ExternalSyntheticOutline0.m(m5, arrayList);
        m6.runeDependency = z;
        m6.bandAidDependency = BandAid.GESTURE_PACK_MOVE_HINT_VI;
        m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.MoveBottomGestureHintDistance.class);
        m6.targetModules = Collections.singletonList(OverviewProxyService.class);
        m6.moduleDependencies = Collections.singletonList(GestureHintAnimator.class);
        m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.MoveBottomGestureHintDistance moveBottomGestureHintDistance = (EventTypeFactory.EventType.MoveBottomGestureHintDistance) kit.event;
                if (((NavBarStateManagerImpl) kit.manager).isGestureHintEnabled()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.MoveHintVI(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, new NavBarStoreAction.GestureHintVIInfo(moveBottomGestureHintDistance.hintId, moveBottomGestureHintDistance.distanceX, moveBottomGestureHintDistance.distanceY, moveBottomGestureHintDistance.duration), null, false, false, null, 0.0f, 0.0f, 0, 0, 8372223, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m7 = ColorPack$$ExternalSyntheticOutline0.m(m6, arrayList);
        m7.runeDependency = z;
        m7.bandAidDependency = BandAid.GESTURE_PACK_KNOX_HARD_KEY_INTENT_POLICY;
        m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged.class);
        m7.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m7.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m7.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$15$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                if (navBarStateManagerImpl.isGestureMode()) {
                    NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
                    action.sysUiFlagInfoList.add(new NavBarStoreAction.SysUiFlagInfo(SemWallpaperColorsWrapper.LOCKSCREEN_HELP_TEXT, navBarStateManagerImpl.states.hardKeyIntentPolicy));
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateSysUiFlags(action));
                }
            }
        };
        Band.Builder m8 = ColorPack$$ExternalSyntheticOutline0.m(m7, arrayList);
        m8.runeDependency = z;
        m8.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_SYSTEMUI_STATE_FLAG;
        m8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSysUiStateFlag.class);
        m8.targetModules = CollectionsKt__CollectionsKt.listOf(OverviewProxyService.class, SamsungNavigationBarView.class);
        m8.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m8.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$17$1
            /* JADX WARN: Code restructure failed: missing block: B:9:0x005b, code lost:
            
                if (r5 == false) goto L14;
             */
            @Override // java.util.function.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void accept(java.lang.Object r31) {
                /*
                    r30 = this;
                    r0 = r31
                    com.android.systemui.navigationbar.bandaid.Band$Kit r0 = (com.android.systemui.navigationbar.bandaid.Band.Kit) r0
                    r1 = r30
                    com.android.systemui.navigationbar.store.NavBarStore r1 = com.android.systemui.navigationbar.store.NavBarStore.this
                    com.android.systemui.navigationbar.store.NavBarStateManager r2 = r0.manager
                    com.android.systemui.navigationbar.store.NavBarStateManagerImpl r2 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r2
                    boolean r3 = r2.isGestureMode()
                    if (r3 == 0) goto L89
                    com.android.systemui.navigationbar.store.NavBarStoreAction$Action r3 = new com.android.systemui.navigationbar.store.NavBarStoreAction$Action
                    r4 = r3
                    r28 = 8388607(0x7fffff, float:1.1754942E-38)
                    r29 = 0
                    r5 = 0
                    r6 = 0
                    r7 = 0
                    r8 = 0
                    r9 = 0
                    r10 = 0
                    r11 = 0
                    r12 = 0
                    r13 = 0
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
                    r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)
                    r4 = 1
                    boolean r5 = r2.isGameMode(r4)
                    r6 = 0
                    if (r5 == 0) goto L5e
                    com.android.systemui.navigationbar.model.NavBarStates r5 = r2.states
                    int r5 = r5.iconHint
                    r5 = r5 & r4
                    if (r5 == 0) goto L51
                    r5 = r4
                    goto L52
                L51:
                    r5 = r6
                L52:
                    java.lang.Boolean r7 = java.lang.Boolean.valueOf(r5)
                    java.lang.String r8 = "isIMEShowing"
                    r2.logNavBarStates(r7, r8)
                    if (r5 != 0) goto L5e
                    goto L5f
                L5e:
                    r4 = r6
                L5f:
                    com.android.systemui.navigationbar.model.NavBarStates r2 = r2.states
                    boolean r2 = r2.hardKeyIntentPolicy
                    java.util.List r5 = r3.sysUiFlagInfoList
                    com.android.systemui.navigationbar.store.NavBarStoreAction$SysUiFlagInfo r6 = new com.android.systemui.navigationbar.store.NavBarStoreAction$SysUiFlagInfo
                    r7 = 274877906944(0x4000000000, double:1.358077306218E-312)
                    r6.<init>(r7, r2)
                    r5.add(r6)
                    com.android.systemui.navigationbar.store.NavBarStoreAction$SysUiFlagInfo r2 = new com.android.systemui.navigationbar.store.NavBarStoreAction$SysUiFlagInfo
                    r6 = 17179869184(0x400000000, double:8.4879831639E-314)
                    r2.<init>(r6, r4)
                    r5.add(r2)
                    com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateSysUiFlags r2 = new com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateSysUiFlags
                    r2.<init>(r3)
                    com.android.systemui.navigationbar.store.NavBarStoreImpl r1 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r1
                    r1.apply(r0, r2)
                L89:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.bandaid.pack.GesturePack$17$1.accept(java.lang.Object):void");
            }
        };
        Band.Builder m9 = ColorPack$$ExternalSyntheticOutline0.m(m8, arrayList);
        m9.runeDependency = z;
        m9.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_ONEHAND_MODE_INFO;
        m9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOneHandModeChanged.class);
        m9.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$19$1
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
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateOneHandModeInfo(new NavBarStoreAction.Action(new NavBarStoreAction.OneHandModeInfo(Integer.parseInt((String) split$default.get(0)), Integer.parseInt((String) split$default.get(1)), Float.parseFloat((String) split$default.get(2))), null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388606, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m10 = ColorPack$$ExternalSyntheticOutline0.m(m9, arrayList);
        m10.runeDependency = z;
        m10.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_REGION_SAMPLING_RECT;
        m10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnOneHandModeChanged.class);
        m10.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m10.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m10.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$21$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if (((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateRegionSamplingRect(null, 1, null));
                }
            }
        };
        Band.Builder m11 = ColorPack$$ExternalSyntheticOutline0.m(m10, arrayList);
        m11.runeDependency = z && BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        m11.bandAidDependency = BandAid.GESTURE_PACK_RECALCULATE_INSET_SCALE;
        m11.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnFoldStateChanged.class);
        m11.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m11.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$23$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.OnFoldStateChanged onFoldStateChanged = (EventTypeFactory.EventType.OnFoldStateChanged) kit.event;
                if (((NavBarStateManagerImpl) kit.manager).isGestureMode()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.RecalculateGestureInsetScale(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, onFoldStateChanged.folded, false, null, 0.0f, 0.0f, 0, 0, 8323071, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m12 = ColorPack$$ExternalSyntheticOutline0.m(m11, arrayList);
        if (BasicRune.NAVBAR_REMOTEVIEW && z) {
            z2 = true;
        }
        m12.runeDependency = z2;
        m12.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_GAMETOOLS_VISIBILITY;
        m12.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m12.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        m12.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$25$1
            /* JADX WARN: Code restructure failed: missing block: B:7:0x0056, code lost:
            
                if (r4 == false) goto L10;
             */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object apply(java.lang.Object r30) {
                /*
                    r29 = this;
                    r0 = r30
                    com.android.systemui.navigationbar.bandaid.Band$Kit r0 = (com.android.systemui.navigationbar.bandaid.Band.Kit) r0
                    r1 = r29
                    com.android.systemui.navigationbar.store.NavBarStore r1 = com.android.systemui.navigationbar.store.NavBarStore.this
                    com.android.systemui.navigationbar.store.NavBarStoreAction$Action r15 = new com.android.systemui.navigationbar.store.NavBarStoreAction$Action
                    r2 = r15
                    r26 = 8388607(0x7fffff, float:1.1754942E-38)
                    r27 = 0
                    r3 = 0
                    r4 = 0
                    r5 = 0
                    r6 = 0
                    r7 = 0
                    r8 = 0
                    r9 = 0
                    r10 = 0
                    r11 = 0
                    r12 = 0
                    r13 = 0
                    r14 = 0
                    r16 = 0
                    r28 = r15
                    r15 = r16
                    r17 = 0
                    r18 = 0
                    r19 = 0
                    r20 = 0
                    r21 = 0
                    r22 = 0
                    r23 = 0
                    r24 = 0
                    r25 = 0
                    r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
                    com.android.systemui.navigationbar.store.NavBarStateManager r2 = r0.manager
                    com.android.systemui.navigationbar.store.NavBarStateManagerImpl r2 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r2
                    r3 = 1
                    boolean r4 = r2.isGameMode(r3)
                    r5 = 0
                    if (r4 == 0) goto L5b
                    com.android.systemui.navigationbar.model.NavBarStates r4 = r2.states
                    int r4 = r4.iconHint
                    r4 = r4 & r3
                    if (r4 == 0) goto L4c
                    r4 = r3
                    goto L4d
                L4c:
                    r4 = r5
                L4d:
                    java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)
                    java.lang.String r7 = "isIMEShowing"
                    r2.logNavBarStates(r6, r7)
                    if (r4 != 0) goto L5b
                L58:
                    r2 = r28
                    goto L5d
                L5b:
                    r3 = r5
                    goto L58
                L5d:
                    java.util.List r4 = r2.sysUiFlagInfoList
                    com.android.systemui.navigationbar.store.NavBarStoreAction$SysUiFlagInfo r5 = new com.android.systemui.navigationbar.store.NavBarStoreAction$SysUiFlagInfo
                    r6 = 17179869184(0x400000000, double:8.4879831639E-314)
                    r5.<init>(r6, r3)
                    r4.add(r5)
                    com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateSysUiFlags r3 = new com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateSysUiFlags
                    r3.<init>(r2)
                    com.android.systemui.navigationbar.store.NavBarStoreImpl r1 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r1
                    r1.apply(r0, r3)
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.bandaid.pack.GesturePack$25$1.apply(java.lang.Object):java.lang.Object");
            }
        };
        Band.Builder m13 = ColorPack$$ExternalSyntheticOutline0.m(m12, arrayList);
        m13.runeDependency = z;
        m13.bandAidDependency = BandAid.GESTURE_PACK_BOTTOM_SENSITIVITY_CHANGED;
        m13.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnBottomSensitivityChanged.class);
        m13.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m13.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$27$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                if ((!((NavBarStateManagerImpl) r0).isGestureHintEnabled()) & ((NavBarStateManagerImpl) kit.manager).isBottomGestureMode(false)) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateNavBarLayoutParams(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m14 = ColorPack$$ExternalSyntheticOutline0.m(m13, arrayList);
        m14.runeDependency = z;
        m14.bandAidDependency = BandAid.GESTURE_PACK_UPDATE_ACTIVE_INDICATOR_SPRING_PARAMS;
        m14.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams.class);
        m14.targetModules = EmptyList.INSTANCE;
        m14.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.GesturePack$29$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams onUpdateBackGestureActiveIndicatorParams = (EventTypeFactory.EventType.OnUpdateBackGestureActiveIndicatorParams) kit.event;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateIndicatorSpringParams(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, onUpdateBackGestureActiveIndicatorParams.stiffness, onUpdateBackGestureActiveIndicatorParams.dampingRatio, 0, 0, 6815743, null)));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m14.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
