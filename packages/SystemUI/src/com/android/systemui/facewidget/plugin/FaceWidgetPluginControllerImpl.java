package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetPluginControllerImpl implements Dumpable {
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
            printWriter.println("    App version = " + pluginFaceWidgetManager.mAppPluginVersion + ", SystemUI version = 4006");
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
