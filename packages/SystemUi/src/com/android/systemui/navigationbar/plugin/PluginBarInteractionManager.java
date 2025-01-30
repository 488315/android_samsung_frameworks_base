package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.navigationbar.PluginNavigationBar;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
                NavigationBarView navigationBarView = (NavigationBarView) navBarStoreImpl.getModule(NavigationBarView.class, ((Integer) it.next()).intValue());
                if (navigationBarView != null && (pluginNavigationBar2 = pluginBarInteractionManager.pluginNavigationBar) != null) {
                    pluginNavigationBar2.onAttachedToWindow(navigationBarView.getPluginBar());
                }
            }
            if (!BasicRune.NAVBAR_SUPPORT_TASKBAR || (taskbarDelegate = (TaskbarDelegate) navBarStoreImpl.getModule(TaskbarDelegate.class, pluginBarInteractionManager.mainContext.getDisplayId())) == null || !taskbarDelegate.mInitialized || (pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar) == null) {
                return;
            }
            pluginNavigationBar.onAttachedToWindow(taskbarDelegate.mPluginTaskBar);
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
    public PluginNavigationBar pluginNavigationBar;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public PluginBarInteractionManager(Context context, NavBarStoreImpl navBarStoreImpl) {
        this.mainContext = context;
        this.navBarStore = navBarStoreImpl;
    }
}
