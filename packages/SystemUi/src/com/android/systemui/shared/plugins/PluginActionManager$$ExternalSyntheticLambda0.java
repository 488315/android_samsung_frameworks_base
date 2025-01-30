package com.android.systemui.shared.plugins;

import android.util.Log;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginFragment;
import com.android.systemui.plugins.PluginListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginActionManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginActionManager f$0;
    public final /* synthetic */ PluginInstance f$1;

    public /* synthetic */ PluginActionManager$$ExternalSyntheticLambda0(PluginActionManager pluginActionManager, PluginInstance pluginInstance, int i) {
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
                Log.d("PluginActionManager", "onPluginDisconnected");
                pluginInstance.unloadPlugin();
                pluginInstance.mListener.onPluginDetached(pluginInstance);
                break;
            case 1:
                PluginActionManager pluginActionManager2 = this.f$0;
                PluginInstance pluginInstance2 = this.f$1;
                pluginActionManager2.getClass();
                Log.d("PluginActionManager", "onPluginDisconnected");
                pluginInstance2.unloadPlugin();
                pluginInstance2.mListener.onPluginDetached(pluginInstance2);
                break;
            case 2:
                PluginActionManager pluginActionManager3 = this.f$0;
                PluginInstance pluginInstance3 = this.f$1;
                pluginActionManager3.getClass();
                Log.d("PluginActionManager", "onPluginDisconnected");
                pluginInstance3.unloadPlugin();
                pluginInstance3.mListener.onPluginDetached(pluginInstance3);
                break;
            default:
                PluginActionManager pluginActionManager4 = this.f$0;
                PluginInstance pluginInstance4 = this.f$1;
                pluginActionManager4.getClass();
                Log.d("PluginActionManager", "onPluginConnected");
                long currentTimeMillis = System.currentTimeMillis();
                pluginActionManager4.mContext.getSharedPreferences("plugin_prefs", 0).edit().putBoolean("plugins", true).apply();
                PluginListener pluginListener = pluginInstance4.mListener;
                if (pluginListener.onPluginAttached(pluginInstance4)) {
                    Plugin plugin = pluginInstance4.mPlugin;
                    if (plugin == null) {
                        pluginInstance4.loadPlugin();
                    } else {
                        pluginInstance4.mPluginFactory.checkVersion(plugin);
                        Plugin plugin2 = pluginInstance4.mPlugin;
                        if (!(plugin2 instanceof PluginFragment)) {
                            plugin2.onCreate(pluginInstance4.mAppContext, pluginInstance4.mPluginContext);
                        }
                        pluginListener.onPluginLoaded(pluginInstance4.mPlugin, pluginInstance4.mPluginContext, pluginInstance4);
                    }
                } else if (pluginInstance4.mPlugin != null) {
                    pluginInstance4.unloadPlugin();
                }
                Log.d("PluginActionManager", "onPluginConnected component=" + pluginInstance4.mComponentName + " elapsed=" + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                break;
        }
    }
}
