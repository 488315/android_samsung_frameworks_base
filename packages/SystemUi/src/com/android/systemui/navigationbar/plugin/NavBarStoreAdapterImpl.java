package com.android.systemui.navigationbar.plugin;

import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.bandaid.BandAid;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import com.android.systemui.navigationbar.bandaid.pack.SPluginPack;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavBarReflectUtil;
import com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarStoreAdapterImpl implements NavBarStoreAdapter {
    public static final Companion Companion = new Companion(null);
    public static volatile NavBarStoreAdapter INSTANCE;
    public final LogWrapper logWrapper;
    public final NavBarStore navBarStore;
    public final SPluginPack pluginPack;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            this.logWrapper.m100e("NavBarStoreAdapterImpl", "An error occurred while running the addPack(): " + e);
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
            logWrapper.m98d("NavBarStoreAdapterImpl", "getNavBarState() value: ".concat(str));
            NavBarStateManager.States states = ((NavBarStoreImpl) this.navBarStore).getNavStateManager(i).states;
            if (Intrinsics.areEqual(str, "disable1")) {
                return Integer.valueOf(states.disable1);
            }
            if (Intrinsics.areEqual(str, "disable2")) {
                return Integer.valueOf(states.disable2);
            }
            return null;
        } catch (Exception e) {
            logWrapper.m100e("NavBarStoreAdapterImpl", "An error occurred while running the getNavBarState(): " + e);
            return null;
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final Object getValue(String str, int i) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.m98d("NavBarStoreAdapterImpl", "getValue() value: ".concat(str));
            NavBarStateManager navStateManager = ((NavBarStoreImpl) this.navBarStore).getNavStateManager(i);
            int hashCode = str.hashCode();
            NavBarRemoteViewManager navBarRemoteViewManager = navStateManager.navBarRemoteViewManager;
            switch (hashCode) {
                case 228881256:
                    if (str.equals("getHomeHandleLand")) {
                        return Integer.valueOf(navStateManager.getGestureWidth(true));
                    }
                    return null;
                case 229014014:
                    if (str.equals("getHomeHandlePort")) {
                        return Integer.valueOf(navStateManager.getGestureWidth(false));
                    }
                    return null;
                case 1278685168:
                    if (str.equals("rightRemoteViewExist")) {
                        return Boolean.valueOf(navBarRemoteViewManager.rightViewList.isEmpty() ? false : true);
                    }
                    return null;
                case 1602028240:
                    if (str.equals("sPayShowing")) {
                        return Boolean.valueOf(navStateManager.states.sPayShowing);
                    }
                    return null;
                case 1689083557:
                    if (str.equals("leftRemoteViewExist")) {
                        return Boolean.valueOf(navBarRemoteViewManager.leftViewList.isEmpty() ? false : true);
                    }
                    return null;
                default:
                    return null;
            }
        } catch (Exception e) {
            logWrapper.m100e("NavBarStoreAdapterImpl", "An error occurred while running the getValue(): " + e);
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
            this.logWrapper.m98d("NavBarStoreAdapterImpl", "An error occurred while running the initPack(): " + e);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void removeBand(final String str) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.m98d("NavBarStoreAdapterImpl", "removeBand() event: ".concat(str));
            ((ArrayList) this.pluginPack.allBands).removeIf(new Predicate() { // from class: com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl$removeBand$1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    Object obj2;
                    String str2 = str;
                    Iterator it = ((Band) obj).targetEvents.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            obj2 = null;
                            break;
                        }
                        obj2 = it.next();
                        if (StringsKt__StringsKt.contains(((Type) obj2).toString(), str2, false)) {
                            break;
                        }
                    }
                    return obj2 != null;
                }
            });
        } catch (Exception e) {
            logWrapper.m98d("NavBarStoreAdapterImpl", "An error occurred while running the removeBand(): " + e);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void removePack() {
        try {
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.navBarStore;
            navBarStoreImpl.packs = ((BandAidPackFactory) navBarStoreImpl.bandAidPackFactory).getPacks(navBarStoreImpl);
        } catch (Exception e) {
            this.logWrapper.m100e("NavBarStoreAdapterImpl", "An error occurred while running the removePack(): " + e);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void addBand(String str, final Runnable runnable, int i, List list, Object obj) {
        LogWrapper logWrapper = this.logWrapper;
        try {
            logWrapper.m98d("NavBarStoreAdapterImpl", "addBand() event: ".concat(str));
            removeBand(str);
            int i2 = Band.$r8$clinit;
            Band.Builder builder = new Band.Builder();
            builder.bandAidDependency = BandAid.PLUGIN_PACK_RUN_PLUGIN_ACTIONS;
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
            logWrapper.m100e("NavBarStoreAdapterImpl", "An error occurred while running the addBand(): ");
            e.printStackTrace();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter
    public final void apply(String str, String str2, int i) {
        NavBarReflectUtil.runFakeStoreAction(this.navBarStore, str, str2, i);
    }
}
