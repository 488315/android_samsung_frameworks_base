package com.android.systemui.navigationbar.bandaid.pack;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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
import kotlin.text.StringsKt__StringsKt;

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
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
                NavBarRemoteViewManager navBarRemoteViewManager = (NavBarRemoteViewManager) ((NavBarStoreImpl) taskBarPack.store).getModule(NavBarRemoteViewManager.class, kit.displayId);
                if (!navBarRemoteViewManager.leftViewList.isEmpty() || !navBarRemoteViewManager.rightViewList.isEmpty()) {
                    String str = ((EventTypeFactory.EventType.OnPackageRemoved) kit.event).packageName;
                    boolean zIsExist = navBarRemoteViewManager.isExist(0, str);
                    NavBarStore navBarStore2 = taskBarPack.store;
                    if (zIsExist) {
                        ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, TaskBarPack.access$makeRemoteViewEventToRemove(taskBarPack, str, 0), 0.0f, 0.0f, 0, 0, 8126463, null)));
                    }
                    if (navBarRemoteViewManager.isExist(1, str)) {
                        ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, TaskBarPack.access$makeRemoteViewEventToRemove(taskBarPack, str, 1), 0.0f, 0.0f, 0, 0, 8126463, null)));
                    }
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.runeDependency = z;
        builderM.bandAidDependency = BandAid.TASKBAR_PACK_OPEN_THEME_CHANGED;
        builderM.targetEvents = Arrays.asList(EventTypeFactory.EventType.OnUseThemeDefaultChanged.class, EventTypeFactory.EventType.OnConfigChanged.class, EventTypeFactory.EventType.OnOpenThemeChanged.class);
        builderM.targetModules = Arrays.asList(NavBarStoreImpl.class, NavigationBar.class);
        builderM.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
                if (kit.event instanceof EventTypeFactory.EventType.OnOpenThemeChanged) {
                    ((NavBarStateManagerImpl) kit.manager).updateUseThemeDefault();
                }
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarIconsAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM2 = ColorPack$$ExternalSyntheticOutline0.m(builderM, arrayList);
        builderM2.runeDependency = z;
        builderM2.bandAidDependency = BandAid.TASKBAR_PACK_ROTATION_LOCKED_CHANGED;
        builderM2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnRotationLockedChanged.class);
        builderM2.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM2.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
                NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
                navBarEvents.eventType = NavBarEvents.EventType.ON_ROTATION_LOCKED_CHANGED;
                navBarEvents.rotationLocked = ((EventTypeFactory.EventType.OnRotationLockedChanged) kit.event).rotationLocked;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, navBarEvents, 0.0f, 0.0f, 0, 0, 8126463, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM3 = ColorPack$$ExternalSyntheticOutline0.m(builderM2, arrayList);
        builderM3.runeDependency = z;
        builderM3.bandAidDependency = BandAid.TASKBAR_PACK_SET_REMOTEVIEW;
        builderM3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetRemoteView.class);
        builderM3.targetModules = Collections.singletonList(NavigationBarControllerImpl.class);
        builderM3.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$7$1
            /* JADX WARN: Removed duplicated region for block: B:9:0x0045  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) throws Resources.NotFoundException {
                int i2;
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
                NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
                Bundle bundle = new Bundle();
                EventTypeFactory.EventType.OnSetRemoteView onSetRemoteView = (EventTypeFactory.EventType.OnSetRemoteView) kit.event;
                String str = onSetRemoteView.requestClass;
                if (str != null) {
                    i2 = (StringsKt__StringsKt.contains(str, "honeyboard", false) && ((NavBarStateManagerImpl) kit.manager).canShowKeyboardButtonOnLeft()) ? 0 : onSetRemoteView.position;
                }
                bundle.putString("requestClass", onSetRemoteView.requestClass);
                bundle.putParcelable("remoteViews", onSetRemoteView.remoteViews);
                bundle.putInt(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, i2);
                bundle.putInt(SystemUIAnalytics.QPNE_VID_PRIORITY, onSetRemoteView.priority);
                navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_NAVBAR_REMOTEVIEWS;
                navBarEvents.remoteViewBundle = bundle;
                Log.d("TaskBarPack", "OnSetRemoteView " + bundle);
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, navBarEvents, 0.0f, 0.0f, 0, 0, 8126463, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM4 = ColorPack$$ExternalSyntheticOutline0.m(builderM3, arrayList);
        builderM4.runeDependency = z;
        builderM4.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_VISIBILITY_BY_KNOX;
        builderM4.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged.class);
        builderM4.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builderM4.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM4.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$9$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
                NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
                boolean zIsNavBarHiddenByKnox = ((NavBarStateManagerImpl) kit.manager).isNavBarHiddenByKnox();
                navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_TASKBAR_VIS_BY_KNOX;
                navBarEvents.hiddenByKnox = zIsNavBarHiddenByKnox;
                NavBarStore navBarStore2 = taskBarPack.store;
                NavBarStoreAction.UpdateTaskBarNavBarEvents updateTaskBarNavBarEvents = new NavBarStoreAction.UpdateTaskBarNavBarEvents(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, navBarEvents, 0.0f, 0.0f, 0, 0, 8126463, null));
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore2;
                navBarStoreImpl.apply(kit, updateTaskBarNavBarEvents);
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarGoneStateFlag(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, zIsNavBarHiddenByKnox ? 8 : 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM5 = ColorPack$$ExternalSyntheticOutline0.m(builderM4, arrayList);
        builderM5.runeDependency = z;
        builderM5.bandAidDependency = BandAid.TASKBAR_PACK_ATTACHED_TO_WINDOW;
        builderM5.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnTaskbarAttachedToWindow.class);
        builderM5.targetModules = Collections.singletonList(TaskbarDelegate.class);
        builderM5.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM5.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$11$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.this$0.store;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskbarStatus(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, true, null, 0.0f, 0.0f, 0, 0, 8257535, null)));
                navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateTaskBarIconsAndHints(null, 1, null));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM6 = ColorPack$$ExternalSyntheticOutline0.m(builderM5, arrayList);
        builderM6.runeDependency = z;
        builderM6.bandAidDependency = BandAid.TASKBAR_PACK_DETACHED_FROM_WINDOW;
        builderM6.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnTaskbarDetachedFromWindow.class);
        builderM6.targetModules = Collections.singletonList(TaskbarDelegate.class);
        builderM6.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM6.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$13$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.this$0.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateTaskbarStatus(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8257535, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder builderM7 = ColorPack$$ExternalSyntheticOutline0.m(builderM6, arrayList);
        builderM7.runeDependency = z;
        builderM7.bandAidDependency = BandAid.TASKBAR_PACK_CONFIG_CHANGED;
        builderM7.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnConfigChanged.class);
        builderM7.targetModules = Collections.singletonList(TaskbarDelegate.class);
        builderM7.moduleDependencies = Arrays.asList(TaskbarDelegate.class, LightBarController.class, NavigationBarTransitions.class);
        builderM7.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$15$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
                if (kit.states.darkMode != ((((EventTypeFactory.EventType.OnConfigChanged) kit.event).newConfig.uiMode & 32) != 0)) {
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskBarPack.store;
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.ReevaluateNavBar(null, 1, null));
                    navBarStoreImpl.apply(kit, new NavBarStoreAction.UpdateNavBarOpaqueColor(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder builderM8 = ColorPack$$ExternalSyntheticOutline0.m(builderM7, arrayList);
        builderM8.runeDependency = z;
        builderM8.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_SIDE_BACK_INSETS;
        builderM8.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateSideBackGestureInsets.class);
        builderM8.targetModules = Arrays.asList(EdgeBackGestureHandler.class, TaskbarDelegate.class);
        builderM8.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM8.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$17$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                TaskBarPack taskBarPack = this.this$0;
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
        Band.Builder builderM9 = ColorPack$$ExternalSyntheticOutline0.m(builderM8, arrayList);
        builderM9.runeDependency = z;
        builderM9.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_TASKBAR_AVAILABLE;
        builderM9.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateTaskbarAvailable.class);
        builderM9.targetModules = Arrays.asList(NavBarStoreImpl.class, NavBarHelper.class, NavigationModeController.class);
        builderM9.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$19$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                final Band.Kit kit = (Band.Kit) obj;
                return Boolean.valueOf(((NavBarStoreImpl) this.this$0.store).handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$19$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((NavigationBarControllerImpl) ((NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class))).updateNavbarForTaskbar();
                        ((NavBarStateManagerImpl) kit.manager).states.layoutChangedBeforeAttached = false;
                    }
                }));
            }
        };
        Band.Builder builderM10 = ColorPack$$ExternalSyntheticOutline0.m(builderM9, arrayList);
        builderM10.runeDependency = z;
        builderM10.bandAidDependency = BandAid.TASKBAR_PACK_UPDATE_A11Y_STATE_ON_USER_SWITCHED;
        builderM10.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUserSwitched.class);
        builderM10.targetModules = Collections.singletonList(NavigationModeController.class);
        builderM10.moduleDependencies = Collections.singletonList(TaskbarDelegate.class);
        builderM10.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.TaskBarPack$21$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.this$0.store;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateA11YStatus(null, 1, null));
                return navBarStoreImpl;
            }
        };
        arrayList.add(builderM10.build());
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
