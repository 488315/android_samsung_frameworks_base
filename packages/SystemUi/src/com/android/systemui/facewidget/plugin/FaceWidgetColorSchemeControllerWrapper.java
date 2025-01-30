package com.android.systemui.facewidget.plugin;

import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeCallback;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeController;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetColorSchemeControllerWrapper implements PluginFaceWidgetColorSchemeController {
    public final List mCallbackList = new ArrayList();

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeController
    public final void registerCallback(PluginFaceWidgetColorSchemeCallback pluginFaceWidgetColorSchemeCallback) {
        List list = this.mCallbackList;
        if (((ArrayList) list).contains(pluginFaceWidgetColorSchemeCallback)) {
            return;
        }
        ((ArrayList) list).add(pluginFaceWidgetColorSchemeCallback);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeController
    public final void unregisterAllCallbacks() {
        ((ArrayList) this.mCallbackList).clear();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeController
    public final void unregisterCallback(PluginFaceWidgetColorSchemeCallback pluginFaceWidgetColorSchemeCallback) {
        ((ArrayList) this.mCallbackList).remove(pluginFaceWidgetColorSchemeCallback);
    }
}
