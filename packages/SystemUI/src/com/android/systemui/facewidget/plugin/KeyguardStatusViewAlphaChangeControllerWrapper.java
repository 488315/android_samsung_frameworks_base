package com.android.systemui.facewidget.plugin;

import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class KeyguardStatusViewAlphaChangeControllerWrapper implements PluginKeyguardStatusViewAlphaChangeController {
    public final List mListeners = new ArrayList();

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeController
    public final void registerListener(PluginKeyguardStatusViewAlphaChangeListener pluginKeyguardStatusViewAlphaChangeListener) {
        if (((ArrayList) this.mListeners).contains(pluginKeyguardStatusViewAlphaChangeListener)) {
            return;
        }
        ((ArrayList) this.mListeners).add(pluginKeyguardStatusViewAlphaChangeListener);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeController
    public final void unregisterAllListener() {
        ((ArrayList) this.mListeners).clear();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeController
    public final void unregisterListener(PluginKeyguardStatusViewAlphaChangeListener pluginKeyguardStatusViewAlphaChangeListener) {
        ((ArrayList) this.mListeners).remove(pluginKeyguardStatusViewAlphaChangeListener);
    }

    public final void updateAlpha(float f) {
        Iterator it = ((ArrayList) this.mListeners).iterator();
        while (it.hasNext()) {
            ((PluginKeyguardStatusViewAlphaChangeListener) it.next()).onKeyguardStatusViewAlphaChanged(f);
        }
    }
}
