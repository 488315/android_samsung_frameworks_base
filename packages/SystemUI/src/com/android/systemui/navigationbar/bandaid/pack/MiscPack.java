package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
                        ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, num.intValue(), false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                    }
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = BasicRune.NAVBAR_NEW_DEX;
        m.bandAidDependency = BandAid.MISC_PACK_CONTROL_NAVBAR_IN_NEW_DEX_MODE;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNewDexModeChanged.class);
        m.targetModules = Collections.singletonList(NavBarStoreImpl.class);
        m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((EventTypeFactory.EventType.OnNewDexModeChanged) kit.event).enabled ? 8 : 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.bandAidDependency = BandAid.MISC_PACK_SHOW_A11Y_SWIPE_UP_TIP_POPUP;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnShowA11YSwipeUpTipPopup.class);
        m2.targetModules = Collections.singletonList(NavigationBar.class);
        m2.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = NavBarStore.this;
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) kit.manager;
                boolean z = false;
                if (navBarStateManagerImpl.isGestureMode()) {
                    NavBarTipPopupUtil navBarTipPopupUtil = NavBarTipPopupUtil.INSTANCE;
                    Context context = navBarStateManagerImpl.context;
                    navBarTipPopupUtil.getClass();
                    if (Prefs.get(context).getInt("NavigationBarAccessibilityShortcutTipCount", 0) < 1) {
                        z = true;
                    }
                }
                navBarStateManagerImpl.logNavBarStates(Boolean.valueOf(z), "canShowA11ySwipeUpTipPopup");
                if (z) {
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.ShowA11ySwipeUpTipPopup(null, 1, null));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.bandAidDependency = BandAid.MISC_PACK_UPDATE_A11Y_STATE_ON_USER_SWITCHED;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUserSwitched.class);
        m3.targetModules = Collections.singletonList(NavigationModeController.class);
        m3.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$7$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateA11YStatus(null, 1, null));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m3.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
