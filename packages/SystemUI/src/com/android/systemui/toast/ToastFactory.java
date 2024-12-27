package com.android.systemui.toast;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.ToastPlugin;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ToastFactory implements Dumpable {
    public final LayoutInflater mLayoutInflater;
    public ToastPlugin mPlugin;

    public ToastFactory(LayoutInflater layoutInflater, PluginManager pluginManager, DumpManager dumpManager) {
        this.mLayoutInflater = layoutInflater;
        dumpManager.registerDumpable("ToastFactory", this);
        pluginManager.addPluginListener(new PluginListener() { // from class: com.android.systemui.toast.ToastFactory.1
            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginConnected(Plugin plugin, Context context) {
                ToastFactory.this.mPlugin = (ToastPlugin) plugin;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginDisconnected(Plugin plugin) {
                ToastFactory toastFactory = ToastFactory.this;
                if (((ToastPlugin) plugin).equals(toastFactory.mPlugin)) {
                    toastFactory.mPlugin = null;
                }
            }
        }, ToastPlugin.class, false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ToastFactory:", "    mAttachedPlugin=");
        m.append(this.mPlugin);
        printWriter.println(m.toString());
    }
}
