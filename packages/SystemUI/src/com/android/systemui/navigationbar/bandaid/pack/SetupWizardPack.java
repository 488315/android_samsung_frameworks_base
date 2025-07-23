package com.android.systemui.navigationbar.bandaid.pack;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPack;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarTransitions;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SetupWizardPack implements BandAidPack {
    public final List allBands;
    public final NavBarStore store;

    public SetupWizardPack(NavBarStore navBarStore) {
        this.store = navBarStore;
        ArrayList arrayList = new ArrayList();
        this.allBands = arrayList;
        int i = Band.$r8$clinit;
        Band.Builder builder = new Band.Builder();
        boolean z = BasicRune.NAVBAR_SETUP_WIZARD;
        builder.runeDependency = z;
        builder.bandAidDependency = BandAid.SETUPWIZARD_PACK_SET_NAVBAR_STYLE;
        builder.targetEvents = Arrays.asList(EventTypeFactory.EventType.OnNavBarStyleChanged.class, EventTypeFactory.EventType.OnNavBarAttachedToWindow.class, EventTypeFactory.EventType.OnKeyguardStateChanged.class);
        builder.targetModules = Arrays.asList(SamsungNavigationBarView.class, NavigationBar.class, NavigationModeController.class, NavBarStoreImpl.class);
        builder.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.bandaid.pack.SetupWizardPack$band$1$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                SetupWizardPack setupWizardPack = SetupWizardPack.this;
                if (((NavBarStateManagerImpl) kit.manager).shouldShowSUWStyle()) {
                    ((NavBarStoreImpl) setupWizardPack.store).apply(kit, new NavBarStoreAction.UpdateNavBarSUWStyle(null, 1, null));
                } else {
                    ((NavBarStoreImpl) setupWizardPack.store).apply(kit, new NavBarStoreAction.UpdateNavBarNormalStyle(null, 1, null));
                }
            }
        };
        Band.Builder m = ColorPack$$ExternalSyntheticOutline0.m(builder, arrayList);
        m.runeDependency = z;
        m.bandAidDependency = BandAid.SETUPWIZARD_PACK_UPDATE_DISABLE_FLAGS;
        m.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnSetDisableFlags.class);
        m.targetModules = Collections.singletonList(NavigationBar.class);
        m.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.SetupWizardPack$1$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                SetupWizardPack setupWizardPack = SetupWizardPack.this;
                if (((NavBarStateManagerImpl) kit.manager).shouldShowSUWStyle()) {
                    ((NavBarStoreImpl) setupWizardPack.store).apply(kit, new NavBarStoreAction.UpdateSUWDisabled(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, (((EventTypeFactory.EventType.OnSetDisableFlags) kit.event).disable1 & 4194304) != 0, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388479, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m2 = ColorPack$$ExternalSyntheticOutline0.m(m, arrayList);
        m2.runeDependency = z;
        m2.bandAidDependency = BandAid.SETUPWIZARD_PACK_UPDATE_DARK_INTENSITY;
        m2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnUpdateDarkIntensity.class);
        m2.targetModules = Collections.singletonList(NavigationBarTransitions.class);
        m2.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.SetupWizardPack$2$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                SetupWizardPack setupWizardPack = SetupWizardPack.this;
                if (((NavBarStateManagerImpl) kit.manager).shouldShowSUWStyle()) {
                    ((NavBarStoreImpl) setupWizardPack.store).apply(kit, new NavBarStoreAction.UpdateSUWDarkIntensity(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, ((EventTypeFactory.EventType.OnUpdateDarkIntensity) kit.event).darkIntensity, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388351, null)));
                }
                return Unit.INSTANCE;
            }
        };
        Band.Builder m3 = ColorPack$$ExternalSyntheticOutline0.m(m2, arrayList);
        m3.runeDependency = z;
        m3.bandAidDependency = BandAid.SETUPWIZARD_PACK_SET_NAVBAR_ICON_HINT;
        m3.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarIconHintChanged.class);
        m3.targetModules = Collections.singletonList(NavigationBar.class);
        m3.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        m3.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.SetupWizardPack$3$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                SetupWizardPack setupWizardPack = SetupWizardPack.this;
                if (((NavBarStateManagerImpl) kit.manager).shouldShowSUWStyle()) {
                    ((NavBarStoreImpl) setupWizardPack.store).apply(kit, new NavBarStoreAction.UpdateSUWIconHints(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, ((EventTypeFactory.EventType.OnNavBarIconHintChanged) kit.event).iconHint, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388095, null)));
                }
                return Unit.INSTANCE;
            }
        };
        arrayList.add(m3.build());
        Band.Builder builder2 = new Band.Builder();
        builder2.runeDependency = z;
        builder2.bandAidDependency = BandAid.SETUPWIZARD_PACK_UPDATE_A11Y_SERVICE;
        builder2.targetEvents = Collections.singletonList(EventTypeFactory.EventType.OnNavBarUpdateA11YService.class);
        builder2.targetModules = Collections.singletonList(NavigationBar.class);
        builder2.moduleDependencies = Collections.singletonList(NavigationBarView.class);
        builder2.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.pack.SetupWizardPack$4$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Band.Kit kit = (Band.Kit) obj;
                SetupWizardPack setupWizardPack = SetupWizardPack.this;
                if (((NavBarStateManagerImpl) kit.manager).shouldShowSUWStyle()) {
                    NavBarStore navBarStore2 = setupWizardPack.store;
                    EventTypeFactory.EventType.OnNavBarUpdateA11YService onNavBarUpdateA11YService = (EventTypeFactory.EventType.OnNavBarUpdateA11YService) kit.event;
                    ((NavBarStoreImpl) navBarStore2).apply(kit, new NavBarStoreAction.UpdateSUWA11yIcon(new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, onNavBarUpdateA11YService.clickable, onNavBarUpdateA11YService.longClickable, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8385535, null)));
                }
                return Unit.INSTANCE;
            }
        };
        arrayList.add(builder2.build());
    }

    @Override // com.android.systemui.navigationbar.bandaid.BandAidPack
    public final List getBands() {
        return this.allBands;
    }
}
