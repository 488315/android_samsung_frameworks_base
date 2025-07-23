package com.android.systemui.navigationbar.plugin;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import com.android.systemui.navigationbar.bandaid.pack.SPluginPack;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavBarReflectUtil;
import com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NavBarStoreAdapterImpl implements NavBarStoreAdapter {
    public static final Companion Companion = new Companion(null);
    public static volatile NavBarStoreAdapterImpl INSTANCE;
    public final LogWrapper logWrapper;
    public final NavBarStore navBarStore;
    public final SPluginPack pluginPack;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public NavBarStoreAdapterImpl(NavBarStore navBarStore, LogWrapper logWrapper) {
        this.navBarStore = navBarStore;
        this.logWrapper = logWrapper;
        this.pluginPack = new SPluginPack(navBarStore);
        new ArrayList().add(new NavBarStoreAction.ForceHideGestureHint(null, 1, null));
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void addBand(String str, Runnable runnable, int i, List list) {
        addBand(str, runnable, i, list, null);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void addPack() {
        try {
            NavBarStore navBarStore = this.navBarStore;
            SPluginPack sPluginPack = this.pluginPack;
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
            if (navBarStoreImpl.packs.contains(sPluginPack)) {
                return;
            }
            navBarStoreImpl.packs.add(sPluginPack);
        } catch (Exception e) {
            this.logWrapper.e("NavBarStoreAdapterImpl", "An error occurred while running the addPack(): " + e);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void apply(String str, int i) {
        NavBarReflectUtil.runFakeStoreAction(this.navBarStore, str, "", i);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final Object getNavBarState(String str, int i) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.d("NavBarStoreAdapterImpl", "getNavBarState() value: " + str);
            NavBarStates navBarStates = ((NavBarStateManagerImpl) ((NavBarStoreImpl) this.navBarStore).getNavStateManager(i)).states;
            if (Intrinsics.areEqual(str, "disable1")) {
                return Integer.valueOf(navBarStates.disable1);
            }
            if (Intrinsics.areEqual(str, "disable2")) {
                return Integer.valueOf(navBarStates.disable2);
            }
            return null;
        } catch (Exception e) {
            logWrapper.e("NavBarStoreAdapterImpl", "An error occurred while running the getNavBarState(): " + e);
            return null;
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final Object getValue(String str, int i) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.d("NavBarStoreAdapterImpl", "getValue() value: " + str);
            NavBarStateManager navStateManager = ((NavBarStoreImpl) this.navBarStore).getNavStateManager(i);
            switch (str.hashCode()) {
                case 228881256:
                    if (str.equals("getHomeHandleLand")) {
                        return Integer.valueOf(((NavBarStateManagerImpl) navStateManager).getGestureWidth(true));
                    }
                    return null;
                case 229014014:
                    if (str.equals("getHomeHandlePort")) {
                        return Integer.valueOf(((NavBarStateManagerImpl) navStateManager).getGestureWidth(false));
                    }
                    return null;
                case 1278685168:
                    if (str.equals("rightRemoteViewExist")) {
                        return Boolean.valueOf(!((NavBarStateManagerImpl) navStateManager).navBarRemoteViewManager.rightViewList.isEmpty());
                    }
                    return null;
                case 1602028240:
                    if (str.equals("sPayShowing")) {
                        return Boolean.valueOf(((NavBarStateManagerImpl) navStateManager).states.sPayShowing);
                    }
                    return null;
                case 1689083557:
                    if (str.equals("leftRemoteViewExist")) {
                        return Boolean.valueOf(!((NavBarStateManagerImpl) navStateManager).navBarRemoteViewManager.leftViewList.isEmpty());
                    }
                    return null;
                default:
                    return null;
            }
        } catch (Exception e) {
            logWrapper.e("NavBarStoreAdapterImpl", "An error occurred while running the getValue(): " + e);
            return null;
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void initPack() {
        try {
            ((ArrayList) this.pluginPack.allBands).clear();
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.navBarStore;
            navBarStoreImpl.packs = ((BandAidPackFactory) navBarStoreImpl.bandAidPackFactory).getPacks(navBarStoreImpl);
        } catch (Exception e) {
            this.logWrapper.d("NavBarStoreAdapterImpl", "An error occurred while running the initPack(): " + e);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void removeBand(String str) {
        removeBand(str, -1);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void removePack() {
        try {
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.navBarStore;
            navBarStoreImpl.packs = ((BandAidPackFactory) navBarStoreImpl.bandAidPackFactory).getPacks(navBarStoreImpl);
        } catch (Exception e) {
            this.logWrapper.e("NavBarStoreAdapterImpl", "An error occurred while running the removePack(): " + e);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void addBand(String str, final Runnable runnable, int i, List list, Object obj) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.d("NavBarStoreAdapterImpl", "addBand() event: " + str);
            removeBand(str, i);
            int i2 = Band.$r8$clinit;
            Band.Builder builder = new Band.Builder();
            builder.bandAidDependency = BandAid.PLUGIN_PACK_RUN_PLUGIN_ACTIONS;
            builder.targetDisplayId = i;
            builder.targetModules = Collections.singletonList(NavBarReflectUtil.class);
            builder.targetEvents = EmptyList.INSTANCE;
            builder.priority = 2;
            builder.sPluginTag = str;
            builder.afterAction = new Consumer() { // from class: com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl$addBand$1$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            };
            ((ArrayList) this.pluginPack.allBands).add(builder.build());
        } catch (Exception e) {
            logWrapper.e("NavBarStoreAdapterImpl", "An error occurred while running the addBand(): ");
            e.printStackTrace();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void apply(String str, String str2, int i) {
        NavBarReflectUtil.runFakeStoreAction(this.navBarStore, str, str2, i);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void removeBand(final String str, final int i) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.d("NavBarStoreAdapterImpl", "removeBand() event: " + str);
            List list = this.pluginPack.allBands;
            final Function1 function1 = new Function1() { // from class: com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    boolean z;
                    Band band = (Band) obj;
                    NavBarStoreAdapterImpl.Companion companion = NavBarStoreAdapterImpl.Companion;
                    if (Intrinsics.areEqual(band.sPluginTag, str)) {
                        int i2 = band.targetDisplayId;
                        int i3 = i;
                        if (i2 == i3 || i3 == -1) {
                            z = true;
                            return Boolean.valueOf(z);
                        }
                    }
                    z = false;
                    return Boolean.valueOf(z);
                }
            };
            ((ArrayList) list).removeIf(new Predicate() { // from class: com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl$sam$java_util_function_Predicate$0
                @Override // java.util.function.Predicate
                public final /* synthetic */ boolean test(Object obj) {
                    return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
                }
            });
        } catch (Exception e) {
            logWrapper.d("NavBarStoreAdapterImpl", "An error occurred while running the removeBand(): " + e);
        }
    }
}
