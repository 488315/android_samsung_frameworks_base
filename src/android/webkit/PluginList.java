package android.webkit;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes4.dex */
public class PluginList {
    private ArrayList<Plugin> mPlugins = new ArrayList<>();

    @Deprecated
    public PluginList() {
    }

    @Deprecated
    public synchronized List getList() {
        return this.mPlugins;
    }

    @Deprecated
    public synchronized void addPlugin(Plugin plugin) {
        if (!this.mPlugins.contains(plugin)) {
            this.mPlugins.add(plugin);
        }
    }

    @Deprecated
    public synchronized void removePlugin(Plugin plugin) {
        int iIndexOf = this.mPlugins.indexOf(plugin);
        if (iIndexOf != -1) {
            this.mPlugins.remove(iIndexOf);
        }
    }

    @Deprecated
    public synchronized void clear() {
        this.mPlugins.clear();
    }

    @Deprecated
    public synchronized void pluginClicked(Context context, int i) {
        try {
            this.mPlugins.get(i).dispatchClickEvent(context);
        } catch (IndexOutOfBoundsException unused) {
        }
    }
}
