package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.SecTaskBarManagerImpl;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.navigationbar.PluginNavigationBar;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class PluginBarInteractionManager {
    public final Context mainContext;
    public final NavBarStoreImpl navBarStore;
    public final PluginBarInteractionManager$pluginListener$1 pluginListener = new SPluginListener() { // from class: com.android.systemui.navigationbar.plugin.PluginBarInteractionManager$pluginListener$1
        @Override // com.samsung.systemui.splugins.SPluginListener
        public final void onPluginConnected(SPlugin sPlugin, Context context) {
            NavBarStoreImpl navBarStoreImpl;
            TaskbarDelegate taskbarDelegate;
            PluginNavigationBar pluginNavigationBar;
            PluginNavigationBar pluginNavigationBar2;
            PluginNavigationBar pluginNavigationBar3 = (PluginNavigationBar) sPlugin;
            Log.d("PluginBarInteractionManager", "Plugin connected");
            PluginBarInteractionManager pluginBarInteractionManager = PluginBarInteractionManager.this;
            if (pluginNavigationBar3 != null) {
                pluginBarInteractionManager.pluginNavigationBar = pluginNavigationBar3;
                pluginNavigationBar3.connect();
            }
            Iterator it = pluginBarInteractionManager.navBarStore.navDependencies.keySet().iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                navBarStoreImpl = pluginBarInteractionManager.navBarStore;
                if (!hasNext) {
                    break;
                }
                Integer num = (Integer) it.next();
                Intrinsics.checkNotNull(num);
                NavigationBarView navigationBarView = (NavigationBarView) navBarStoreImpl.getModule(NavigationBarView.class, num.intValue());
                if (navigationBarView != null && (pluginNavigationBar2 = pluginBarInteractionManager.pluginNavigationBar) != null) {
                    pluginNavigationBar2.onAttachedToWindow(navigationBarView.getPluginBar());
                }
            }
            if (!BasicRune.NAVBAR_TASKBAR || (taskbarDelegate = (TaskbarDelegate) navBarStoreImpl.getModule(TaskbarDelegate.class, pluginBarInteractionManager.mainContext.getDisplayId())) == null || !taskbarDelegate.mInitialized || (pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar) == null) {
                return;
            }
            pluginNavigationBar.onAttachedToWindow(((SecTaskBarManagerImpl) taskbarDelegate).pluginTaskBar);
        }

        @Override // com.samsung.systemui.splugins.SPluginListener
        public final void onPluginDisconnected(SPlugin sPlugin, int i) {
            Log.d("PluginBarInteractionManager", "Plugin disconnected");
            PluginBarInteractionManager pluginBarInteractionManager = PluginBarInteractionManager.this;
            PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
            if (pluginNavigationBar != null) {
                pluginNavigationBar.disconnect();
            }
            pluginBarInteractionManager.pluginNavigationBar = null;
        }

        @Override // com.samsung.systemui.splugins.SPluginListener
        public final void onPluginLoadFailed(int i) {
            Log.d("PluginBarInteractionManager", "Plugin load failed");
            Settings.Global.putString(PluginBarInteractionManager.this.mainContext.getContentResolver(), "policy_control", "null");
        }
    };
    public final SPluginManager pluginManager;
    public PluginNavigationBar pluginNavigationBar;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.navigationbar.plugin.PluginBarInteractionManager$pluginListener$1] */
    public PluginBarInteractionManager(Context context, NavBarStoreImpl navBarStoreImpl, SPluginManager sPluginManager) {
        this.mainContext = context;
        this.navBarStore = navBarStoreImpl;
        this.pluginManager = sPluginManager;
    }
}
