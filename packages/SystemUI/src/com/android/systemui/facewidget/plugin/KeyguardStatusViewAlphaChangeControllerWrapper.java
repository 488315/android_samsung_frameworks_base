package com.android.systemui.facewidget.plugin;

import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class KeyguardStatusViewAlphaChangeControllerWrapper implements PluginKeyguardStatusViewAlphaChangeController {
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
        ArrayList arrayList = (ArrayList) this.mListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PluginKeyguardStatusViewAlphaChangeListener) obj).onKeyguardStatusViewAlphaChanged(f);
        }
    }
}
