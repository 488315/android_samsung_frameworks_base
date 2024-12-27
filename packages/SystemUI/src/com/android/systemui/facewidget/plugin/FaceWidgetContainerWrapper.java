package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceWidgetContainerWrapper implements Dumpable {
    public View mClockContainer;
    public List mContentsContainerList;
    public final Context mContext;
    public View mFaceWidgetContainer;
    public PluginKeyguardStatusView mPluginKeyguardStatusView;

    public FaceWidgetContainerWrapper(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager != null) {
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
