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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ToastFactory implements Dumpable {
    public ToastPlugin mPlugin;

    public ToastFactory(PluginManager pluginManager, DumpManager dumpManager) {
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "ToastFactory", this);
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

    public final SystemUIToast createToast(Context context, Context context2, CharSequence charSequence, String str, int i, int i2) {
        LayoutInflater from = LayoutInflater.from(context2);
        ToastPlugin toastPlugin = this.mPlugin;
        return toastPlugin != null ? new SystemUIToast(from, context, context2, charSequence, toastPlugin.createToast(charSequence, str, i), str, i, i2) : new SystemUIToast(from, context, context2, charSequence, str, i, i2);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ToastFactory:", "    mAttachedPlugin=");
        m.append(this.mPlugin);
        printWriter.println(m.toString());
    }
}
