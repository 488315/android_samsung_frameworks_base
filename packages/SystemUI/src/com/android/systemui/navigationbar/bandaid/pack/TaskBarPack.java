package com.android.systemui.navigationbar.bandaid.pack;

import android.os.Bundle;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarTransitions;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TaskBarPack implements BandAidPack {
    public final List allBands;
    public final NavBarStore store;

    public TaskBarPack(NavBarStore navBarStore) {
        this.store = navBarStore;
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_TASKBAR;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.TASKBAR_PACK_PACKAGE_REMOVED;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnPackageRemoved.class);
        builder.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builder.moduleDependencies = Arrays.asList(TaskbarDelegate.class, NavBarRemoteViewManager.class);
        builder.priority = 0;
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = TaskBarPack.this;
                NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) ((NavBarStoreImpl) taskBarPack.store).getModule(NavBarRemoteViewManager.class, kit.displayId);
                if (!navBarRemoteViewManager.leftViewList.isEmpty() || !navBarRemoteViewManager.rightViewList.isEmpty()) {
                    String str = ((EventTypeFactory.EventType.OnPackageRemoved) kit.event).packageName;
                    boolean isExist = navBarRemoteViewManager.isExist(0, str);
                    NavBarStore navBarStore2 = taskBarPack.store;
                    if (isExist) {
                        ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, TaskBarPack.access$makeRemoteViewEventToRemove(taskBarPack, str, 0), 0.0f, 0.0f, 0, 0, 8126463, null)));
                    }
                    if (navBarRemoteViewManager.isExist(1, str)) {
                        ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, TaskBarPack.access$makeRemoteViewEventToRemove(taskBarPack, str, 1), 0.0f, 0.0f, 0, 0, 8126463, null)));
                    }
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = z;
        m.bandAidDependency = BandAid.TASKBAR_PACK_OPEN_THEME_CHANGED;
        m.targetEvents = Arrays.asList(EventTypeFactory.EventType.OnUseThemeDefaultChanged.class, EventTypeFactory.EventType.OnConfigChanged.class, EventTypeFactory.EventType.OnOpenThemeChanged.class);
        m.targetModules = Arrays.asList(NavBarStoreImpl.class, NavigationBar.class);
        m.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = TaskBarPack.this;
                if (kit.event instanceof EventTypeFactory.EventType.OnOpenThemeChanged) {
                    ((NavBarStateManagerImpl) kit.manager).updateUseThemeDefault();
                }
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarIconsAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.runeDependency = z;
        m2.bandAidDependency = BandAid.TASKBAR_PACK_ROTATION_LOCKED_CHANGED;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnRotationLockedChanged.class);
        m2.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m2.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = TaskBarPack.this;
                NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
                navBarEvents.eventType = NavBarEvents.EventType.ON_ROTATION_LOCKED_CHANGED;
                navBarEvents.rotationLocked = ((EventTypeFactory.EventType.OnRotationLockedChanged) kit.event).rotationLocked;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, navBarEvents, 0.0f, 0.0f, 0, 0, 8126463, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.runeDependency = z;
        m3.bandAidDependency = BandAid.TASKBAR_PACK_SET_REMOTEVIEW;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        m3.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        m3.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$7$1
            /* JADX WARN: Code restructure failed: missing block: B:6:0x0042, code lost:
            
                if (((com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0.manager).canShowKeyboardButtonOnLeft() != false) goto L10;
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
                    com.android.systemui.navigationbar.bandaid.pack.TaskBarPack r1 = com.android.systemui.navigationbar.bandaid.pack.TaskBarPack.this
                    com.android.systemui.navigationbar.store.EventTypeFactory$EventType r2 = r0.event
                    com.android.systemui.navigationbar.store.EventTypeFactory$EventType$OnSetRemoteView r2 = (com.android.systemui.navigationbar.store.EventTypeFactory.EventType.OnSetRemoteView) r2
                    com.android.systemui.shared.navigationbar.NavBarEvents r3 = new com.android.systemui.shared.navigationbar.NavBarEvents
                    r17 = 8191(0x1fff, float:1.1478E-41)
                    r18 = 0
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
                    r15 = 0
                    r16 = 0
                    r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
                    android.os.Bundle r2 = new android.os.Bundle
                    r2.<init>()
                    com.android.systemui.navigationbar.store.EventTypeFactory$EventType r4 = r0.event
                    com.android.systemui.navigationbar.store.EventTypeFactory$EventType$OnSetRemoteView r4 = (com.android.systemui.navigationbar.store.EventTypeFactory.EventType.OnSetRemoteView) r4
                    java.lang.String r5 = r4.requestClass
                    if (r5 == 0) goto L45
                    java.lang.String r6 = "honeyboard"
                    r7 = 0
                    boolean r5 = kotlin.text.StringsKt__StringsKt.contains(r5, r6, r7)
                    r6 = 1
                    if (r5 != r6) goto L45
                    com.android.systemui.navigationbar.store.NavBarStateManager r5 = r0.manager
                    com.android.systemui.navigationbar.store.NavBarStateManagerImpl r5 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r5
                    boolean r5 = r5.canShowKeyboardButtonOnLeft()
                    if (r5 == 0) goto L45
                    goto L47
                L45:
                    int r7 = r4.position
                L47:
                    java.lang.String r5 = "requestClass"
                    java.lang.String r6 = r4.requestClass
                    r2.putString(r5, r6)
                    android.widget.RemoteViews r5 = r4.remoteViews
                    java.lang.String r6 = "remoteViews"
                    r2.putParcelable(r6, r5)
                    java.lang.String r5 = "position"
                    r2.putInt(r5, r7)
                    java.lang.String r5 = "priority"
                    int r4 = r4.priority
                    r2.putInt(r5, r4)
                    com.android.systemui.shared.navigationbar.NavBarEvents$EventType r4 = com.android.systemui.shared.navigationbar.NavBarEvents.EventType.ON_UPDATE_NAVBAR_REMOTEVIEWS
                    r3.eventType = r4
                    r3.remoteViewBundle = r2
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                    java.lang.String r5 = "OnSetRemoteView "
                    r4.<init>(r5)
                    r4.append(r2)
                    java.lang.String r2 = r4.toString()
                    java.lang.String r4 = "TaskBarPack"
                    android.util.Log.d(r4, r2)
                    com.android.systemui.navigationbar.store.NavBarStore r1 = r1.store
                    com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateTaskBarNavBarEvents r2 = new com.android.systemui.navigationbar.store.NavBarStoreAction$UpdateTaskBarNavBarEvents
                    r22 = r3
                    com.android.systemui.navigationbar.store.NavBarStoreAction$Action r3 = new com.android.systemui.navigationbar.store.NavBarStoreAction$Action
                    r27 = 8126463(0x7bffff, float:1.13876E-38)
                    r28 = 0
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
                    r15 = 0
                    r16 = 0
                    r17 = 0
                    r18 = 0
                    r19 = 0
                    r20 = 0
                    r21 = 0
                    r23 = 0
                    r24 = 0
                    r25 = 0
                    r26 = 0
                    r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)
                    r2.<init>(r3)
                    com.android.systemui.navigationbar.store.NavBarStoreImpl r1 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r1
                    r1.apply(r0, r2)
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$7$1.apply(java.lang.Object):java.lang.Object");
            }
        };
        Band.Builder m4 = ColorPack$$ExternalSyntheticOutline0.m(m3, arrayList);
        m4.runeDependency = z;
        m4.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_VISIBILITY_BY_KNOX;
        m4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged.class);
        m4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m4.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = TaskBarPack.this;
                NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
                boolean isNavBarHiddenByKnox = ((NavBarStateManagerImpl) kit.manager).isNavBarHiddenByKnox();
                navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_TASKBAR_VIS_BY_KNOX;
                navBarEvents.hiddenByKnox = isNavBarHiddenByKnox;
                NavBarStore navBarStore2 = taskBarPack.store;
                NavBarStoreAction.UpdateTaskBarNavBarEvents updateTaskBarNavBarEvents = new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, navBarEvents, 0.0f, 0.0f, 0, 0, 8126463, null));
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, updateTaskBarNavBarEvents);
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarGoneStateFlag(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, isNavBarHiddenByKnox ? 8 : 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m5 = ColorPack$$ExternalSyntheticOutline0.m(m4, arrayList);
        m5.runeDependency = z;
        m5.bandAidDependency = BandAid.TASKBAR_PACK_ATTACHED_TO_WINDOW;
        m5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnTaskbarAttachedToWindow.class);
        m5.targetModules = Collections.singletonList(TaskbarDelegate.class);
        m5.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) TaskBarPack.this.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskbarStatus(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, true, null, 0.0f, 0.0f, 0, 0, 8257535, null)));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarIconsAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder m6 = ColorPack$$ExternalSyntheticOutline0.m(m5, arrayList);
        m6.runeDependency = z;
        m6.bandAidDependency = BandAid.TASKBAR_PACK_DETACHED_FROM_WINDOW;
        m6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnTaskbarDetachedFromWindow.class);
        m6.targetModules = Collections.singletonList(TaskbarDelegate.class);
        m6.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) TaskBarPack.this.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateTaskbarStatus(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8257535, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m7 = ColorPack$$ExternalSyntheticOutline0.m(m6, arrayList);
        m7.runeDependency = z;
        m7.bandAidDependency = BandAid.TASKBAR_PACK_CONFIG_CHANGED;
        m7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnConfigChanged.class);
        m7.targetModules = Collections.singletonList(TaskbarDelegate.class);
        m7.moduleDependencies = Arrays.asList(TaskbarDelegate.class, LightBarController.class, NavigationBarTransitions.class);
        m7.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$15$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = TaskBarPack.this;
                if (kit.states.darkMode != ((((EventTypeFactory.EventType.OnConfigChanged) kit.event).newConfig.uiMode & 32) != 0)) {
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m8 = ColorPack$$ExternalSyntheticOutline0.m(m7, arrayList);
        m8.runeDependency = z;
        m8.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_SIDE_BACK_INSETS;
        m8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSideBackGestureInsets.class);
        m8.targetModules = Arrays.asList(EdgeBackGestureHandler.class, TaskbarDelegate.class);
        m8.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m8.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$17$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = TaskBarPack.this;
                NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
                Bundle bundle = new Bundle();
                EventTypeFactory.EventType.OnUpdateSideBackGestureInsets onUpdateSideBackGestureInsets = (EventTypeFactory.EventType.OnUpdateSideBackGestureInsets) kit.event;
                bundle.putInt("leftWidth", onUpdateSideBackGestureInsets.leftWidth);
                bundle.putInt("rightWidth", onUpdateSideBackGestureInsets.rightWidth);
                navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_SIDE_BACK_GESTURE_INSETS;
                navBarEvents.insetsBundle = bundle;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, navBarEvents, 0.0f, 0.0f, 0, 0, 8126463, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m9 = ColorPack$$ExternalSyntheticOutline0.m(m8, arrayList);
        m9.runeDependency = z;
        m9.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_TASKBAR_AVAILABLE;
        m9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateTaskbarAvailable.class);
        m9.targetModules = Arrays.asList(NavBarStoreImpl.class, NavBarHelper.class, NavigationModeController.class);
        m9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                final Band.Kit kit = (Band.Kit) obj;
                return Boolean.valueOf(((NavBarStoreImpl) TaskBarPack.this.store).handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$19$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((NavigationBarControllerImpl) ((NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class))).updateNavbarForTaskbar();
                        ((NavBarStateManagerImpl) Band.Kit.this.manager).states.layoutChangedBeforeAttached = false;
                    }
                }));
            }
        };
        Band.Builder m10 = ColorPack$$ExternalSyntheticOutline0.m(m9, arrayList);
        m10.runeDependency = z;
        m10.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_A11Y_STATE_ON_USER_SWITCHED;
        m10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUserSwitched.class);
        m10.targetModules = Collections.singletonList(NavigationModeController.class);
        m10.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        m10.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$21$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) TaskBarPack.this.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateA11YStatus(null, 1, null));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m10.build());
    }

    public static final NavBarEvents access$makeRemoteViewEventToRemove(TaskBarPack taskBarPack, String str, int i) {
        taskBarPack.getClass();
        NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
        Bundle bundle = new Bundle();
        bundle.putString("requestClass", str);
        bundle.putParcelable("remoteViews", null);
        bundle.putInt(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, i);
        bundle.putInt(SystemUIAnalytics.QPNE_VID_PRIORITY, 0);
        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_NAVBAR_REMOTEVIEWS;
        navBarEvents.remoteViewBundle = bundle;
        return navBarEvents;
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
