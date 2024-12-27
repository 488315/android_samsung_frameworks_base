package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            printWriter.println("    App version = " + pluginFaceWidgetManager.mAppPluginVersion + ", SystemUI version = 3024");
            PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.dump(null, printWriter, strArr);
            }
            FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper = pluginFaceWidgetManager.mWallpaperUtilsWrapper;
            if (faceWidgetWallpaperUtilsWrapper != null) {
                faceWidgetWallpaperUtilsWrapper.dump(printWriter, strArr);
            }
        }
    }
}
