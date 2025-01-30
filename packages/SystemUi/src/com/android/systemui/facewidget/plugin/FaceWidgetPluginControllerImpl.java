package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetPluginControllerImpl implements Dumpable {
    public final FaceWidgetNotificationController mNotificationManager;
    public final PluginFaceWidgetManager mPluginFaceWidgetManager;

    public FaceWidgetPluginControllerImpl(FaceWidgetNotificationController faceWidgetNotificationController, PluginFaceWidgetManager pluginFaceWidgetManager, DumpManager dumpManager) {
        this.mNotificationManager = faceWidgetNotificationController;
        this.mPluginFaceWidgetManager = pluginFaceWidgetManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "FaceWidgetPluginController", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("FaceWidget state:");
        PluginFaceWidgetManager pluginFaceWidgetManager = this.mPluginFaceWidgetManager;
        if (pluginFaceWidgetManager != null) {
            printWriter.println("    App version = " + pluginFaceWidgetManager.mAppPluginVersion + ", SystemUI version = 2014");
            PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.dump(null, printWriter, strArr);
            }
        }
    }
}
