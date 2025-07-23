package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
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
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply(kit, new NavBarStoreAction.SetNavBarVisibility(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, ((EventTypeFactory.EventType.OnDesktopModeChanged) kit.event).enabled ? 8 : 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8384511, null)));
                return navBarStoreImpl;
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.bandAidDependency = BandAid.MISC_PACK_SHOW_A11Y_SWIPE_UP_TIP_POPUP;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnShowA11YSwipeUpTipPopup.class);
        m.targetModules = Collections.singletonList(NavigationBar.class);
        m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$3$1
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
                    if (Prefs.getInt(context, "NavigationBarAccessibilityShortcutTipCount", 0) < 1) {
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
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.bandAidDependency = BandAid.MISC_PACK_UPDATE_A11Y_STATE_ON_USER_SWITCHED;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUserSwitched.class);
        m2.targetModules = Collections.singletonList(NavigationModeController.class);
        m2.moduleDependencies = Collections.singletonList(NavigationBar.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$5$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) NavBarStore.this;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateA11YStatus(null, 1, null));
                return navBarStoreImpl;
            }
        };
        arrayList.add(m2.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
