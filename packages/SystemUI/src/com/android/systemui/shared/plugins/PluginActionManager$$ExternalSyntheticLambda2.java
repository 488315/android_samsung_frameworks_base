package com.android.systemui.shared.plugins;

import android.util.Log;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginFragment;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginActionManager$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginActionManager f$0;
    public final /* synthetic */ PluginInstance f$1;

    public /* synthetic */ PluginActionManager$$ExternalSyntheticLambda2(PluginActionManager pluginActionManager, PluginInstance pluginInstance, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginActionManager;
        this.f$1 = pluginInstance;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PluginActionManager pluginActionManager = this.f$0;
                PluginInstance pluginInstance = this.f$1;
                pluginActionManager.getClass();
                PluginActionManager.onPluginDisconnected(pluginInstance);
                return;
            case 1:
                PluginActionManager pluginActionManager2 = this.f$0;
                PluginInstance pluginInstance2 = this.f$1;
                pluginActionManager2.getClass();
                Log.d("PluginActionManager", "onPluginConnected");
                pluginActionManager2.mContext.getSharedPreferences("plugin_prefs", 0).edit().putBoolean("plugins", true).apply();
                synchronized (pluginInstance2) {
                    if (!pluginInstance2.mListener.onPluginAttached(pluginInstance2)) {
                        if (pluginInstance2.mPlugin != null) {
                            pluginInstance2.log("onCreate: auto-unload");
                            pluginInstance2.unloadPlugin();
                        }
                        return;
                    } else {
                        if (pluginInstance2.mPlugin == null) {
                            pluginInstance2.log("onCreate auto-load");
                            pluginInstance2.loadPlugin();
                            return;
                        }
                        pluginInstance2.log("onCreate: load callbacks");
                        pluginInstance2.mPluginFactory.checkVersion(pluginInstance2.mPlugin);
                        Plugin plugin = pluginInstance2.mPlugin;
                        if (!(plugin instanceof PluginFragment)) {
                            plugin.onCreate(pluginInstance2.mAppContext, pluginInstance2.mPluginContext);
                        }
                        pluginInstance2.mListener.onPluginLoaded(pluginInstance2.mPlugin, pluginInstance2.mPluginContext, pluginInstance2);
                        return;
                    }
                }
            case 2:
                PluginActionManager pluginActionManager3 = this.f$0;
                PluginInstance pluginInstance3 = this.f$1;
                pluginActionManager3.getClass();
                PluginActionManager.onPluginDisconnected(pluginInstance3);
                return;
            default:
                PluginActionManager pluginActionManager4 = this.f$0;
                PluginInstance pluginInstance4 = this.f$1;
                pluginActionManager4.getClass();
                PluginActionManager.onPluginDisconnected(pluginInstance4);
                return;
        }
    }
}
