package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavBarTipPopupUtil;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MiscPack implements BandAidPack {
    public final List allBands;

    public MiscPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        builder.runeDependency = BasicRune.NAVBAR_DESKTOP;
        builder.bandAidDependency = BandAid.MISC_PACK_CONTROL_NAVBAR_IN_DEX_STANDALONE;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnDesktopModeChanged.class);
        builder.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        builder.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                SemDesktopModeState semDesktopModeState = ((EventTypeFactory.EventType.OnDesktopModeChanged) kit.event).state;
                if (semDesktopModeState != null && semDesktopModeState.getDisplayType() == 101) {
                    int state = semDesktopModeState.getState();
                    int enabled = semDesktopModeState.getEnabled();
                    Integer num = (state == 50 && enabled == 4) ? 8 : (state == 50 && enabled == 2) ? 0 : (state == 0 && enabled == 4) ? 8 : null;
                    if (num != null) {
                        ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, num.intValue(), false, null, null, false, false, null, 0.0f, 0.0f, 0, 4190207, null)));
                    }
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m = ColorPack$$ExternalSyntheticOutline0.m164m(builder, arrayList);
        m164m.runeDependency = BasicRune.NAVBAR_NEW_DEX;
        m164m.bandAidDependency = BandAid.MISC_PACK_CONTROL_NAVBAR_IN_NEW_DEX_MODE;
        m164m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNewDexModeChanged.class);
        m164m.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m164m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((EventTypeFactory.EventType.OnNewDexModeChanged) kit.event).enabled ? 8 : 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4190207, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m164m2 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m, arrayList);
        m164m2.bandAidDependency = BandAid.MISC_PACK_SHOW_A11Y_SWIPE_UP_TIP_POPUP;
        m164m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnShowA11YSwipeUpTipPopup.class);
        m164m2.targetModules = Collections.singletonList(NavigationBar.class);
        m164m2.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m164m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManager navBarStateManager = kit.manager;
                boolean z = false;
                if (navBarStateManager.isGestureMode()) {
                    NavBarTipPopupUtil.INSTANCE.getClass();
                    if (Prefs.getInt(navBarStateManager.context, "NavigationBarAccessibilityShortcutTipCount", 0) < 1) {
                        z = true;
                    }
                }
                Boolean valueOf = Boolean.valueOf(z);
                navBarStateManager.logNavBarStates(valueOf, "canShowA11ySwipeUpTipPopup");
                Intrinsics.checkNotNull(valueOf);
                if (valueOf.booleanValue()) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ShowA11ySwipeUpTipPopup(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m164m3 = ColorPack$$ExternalSyntheticOutline0.m164m(m164m2, arrayList);
        m164m3.bandAidDependency = BandAid.MISC_PACK_UPDATE_A11Y_STATE_ON_USER_SWITCHED;
        m164m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUserSwitched.class);
        m164m3.targetModules = Collections.singletonList(NavigationModeController.class);
        m164m3.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m164m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateA11YStatus(null, 1, null));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m164m3.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
