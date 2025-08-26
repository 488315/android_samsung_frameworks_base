package com.android.systemui.navigationbar.bandaid.pack;

import android.content.Context;
import android.content.res.Resources;
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

/* loaded from: classes2.dex */
public final class MiscPack implements BandAidPack {
    public final List allBands;

    public MiscPack(final NavBarStore navBarStore) {
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        builder.bandAidDependency = BandAid.MISC_PACK_SHOW_A11Y_SWIPE_UP_TIP_POPUP;
        builder.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnShowA11YSwipeUpTipPopup.class);
        builder.targetModules = Collections.singletonList(NavigationBar.class);
        builder.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                Band.Kit kit = (Band.Kit) obj;
                NavBarStore navBarStore2 = navBarStore;
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
        Band.Builder builderM = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        builderM.bandAidDependency = BandAid.MISC_PACK_UPDATE_A11Y_STATE_ON_USER_SWITCHED;
        builderM.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUserSwitched.class);
        builderM.targetModules = Collections.singletonList(NavigationModeController.class);
        builderM.moduleDependencies = Collections.singletonList(NavigationBar.class);
        builderM.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.MiscPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) throws Resources.NotFoundException {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                navBarStoreImpl.apply((Band.Kit) obj, new NavBarStoreAction.UpdateA11YStatus(null, 1, null));
                return navBarStoreImpl;
            }
        };
        arrayList.add(builderM.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
