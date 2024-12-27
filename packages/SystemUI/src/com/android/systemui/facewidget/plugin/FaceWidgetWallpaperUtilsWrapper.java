package com.android.systemui.facewidget.plugin;

import android.app.SemWallpaperColors;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.annotations.VersionCheckingProxy;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWidgetCallback;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.widget.SystemUIWidgetCallback;
import java.io.PrintWriter;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FaceWidgetWallpaperUtilsWrapper implements PluginSystemUIWallpaperUtils, Dumpable {
    public final HashMap mCallbackMap = new HashMap();
    public final KeyguardWallpaper mKeyguardWallpaper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FaceWidgetSystemUIWidgetCallbackWrapper implements SystemUIWidgetCallback, Dumpable {
        public final long mArea;
        public final PluginSystemUIWidgetCallback mCallback;

        public FaceWidgetSystemUIWidgetCallbackWrapper(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback, long j) {
            this.mCallback = pluginSystemUIWidgetCallback;
            this.mArea = j;
        }

        @Override // com.android.systemui.Dumpable
        public final void dump(PrintWriter printWriter, String[] strArr) {
            printWriter.println("     Callback : " + this.mCallback);
            printWriter.println("      flag : " + KeyguardWallpaperColors.getChangeFlagsString(this.mArea));
        }

        @Override // com.android.systemui.widget.SystemUIWidgetCallback
        public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
            PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback = this.mCallback;
            if (pluginSystemUIWidgetCallback != null) {
                int i = (int) j;
                ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("updateStyle: updateFlag = ", KeyguardWallpaperColors.getChangeFlagsString(i), ", colors = "), semWallpaperColors == null ? "null" : semWallpaperColors.toSimpleString(), "FaceWidgetWallpaperUtilsWrapper");
                pluginSystemUIWidgetCallback.updateStyle(i, semWallpaperColors);
            }
        }
    }

    public FaceWidgetWallpaperUtilsWrapper(KeyguardWallpaper keyguardWallpaper) {
        this.mKeyguardWallpaper = keyguardWallpaper;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(" --------------------------------------------- ");
        printWriter.println(" FaceWidgetWallpaperUtilsWrapper");
        printWriter.println("   callbacks");
        for (FaceWidgetSystemUIWidgetCallbackWrapper faceWidgetSystemUIWidgetCallbackWrapper : this.mCallbackMap.values()) {
            if (faceWidgetSystemUIWidgetCallbackWrapper != null) {
                faceWidgetSystemUIWidgetCallbackWrapper.dump(printWriter, strArr);
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final int getColorByName(String str) {
        return -1;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final boolean hasAdaptiveColorResult() {
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final boolean isOpenThemeLook() {
        return WallpaperUtils.isOpenThemeLockWallpaper();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final boolean isWhiteKeyguardWallpaper(String str) {
        return WallpaperUtils.isWhiteKeyguardWallpaper(str);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final boolean isWhiteSubUiWallpaper(int i) {
        if (i < 0) {
            return false;
        }
        return ((KeyguardWallpaperController) this.mKeyguardWallpaper).getHint((long) i, true).getFontColor() == 1;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final synchronized void registerCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback, int i) {
        registerCallback(false, pluginSystemUIWidgetCallback, i);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final synchronized void registerSubUiCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback, int i) {
        registerCallback(true, pluginSystemUIWidgetCallback, i);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final synchronized void removeCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback) {
        removeCallback(false, pluginSystemUIWidgetCallback);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils
    public final synchronized void removeSubUiCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback) {
        removeCallback(true, pluginSystemUIWidgetCallback);
    }

    public final synchronized void registerCallback(boolean z, PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback, int i) {
        if (pluginSystemUIWidgetCallback != null) {
            try {
                if (!this.mCallbackMap.containsKey(pluginSystemUIWidgetCallback)) {
                    long j = i;
                    FaceWidgetSystemUIWidgetCallbackWrapper faceWidgetSystemUIWidgetCallbackWrapper = new FaceWidgetSystemUIWidgetCallbackWrapper((PluginSystemUIWidgetCallback) new VersionCheckingProxy(PluginSystemUIWidgetCallback.class, pluginSystemUIWidgetCallback, new FaceWidgetWallpaperUtilsWrapper$$ExternalSyntheticLambda0()).get(), j);
                    this.mCallbackMap.put(pluginSystemUIWidgetCallback, faceWidgetSystemUIWidgetCallbackWrapper);
                    Log.d("FaceWidgetWallpaperUtilsWrapper", "registerCallback: flags = " + KeyguardWallpaperColors.getChangeFlagsString(j));
                    if (z) {
                        if (WallpaperEventNotifier.getInstance() != null) {
                            WallpaperEventNotifier.getInstance().registerCallback(true, faceWidgetSystemUIWidgetCallbackWrapper, j);
                        }
                    } else {
                        WallpaperUtils.registerSystemUIWidgetCallback(faceWidgetSystemUIWidgetCallbackWrapper, j);
                    }
                }
            } finally {
            }
        }
    }

    public final synchronized void removeCallback(boolean z, PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback) {
        if (pluginSystemUIWidgetCallback != null) {
            try {
                if (this.mCallbackMap.containsKey(pluginSystemUIWidgetCallback)) {
                    FaceWidgetSystemUIWidgetCallbackWrapper faceWidgetSystemUIWidgetCallbackWrapper = (FaceWidgetSystemUIWidgetCallbackWrapper) this.mCallbackMap.get(pluginSystemUIWidgetCallback);
                    this.mCallbackMap.remove(pluginSystemUIWidgetCallback);
                    if (z) {
                        if (WallpaperEventNotifier.getInstance() != null) {
                            WallpaperEventNotifier.getInstance().removeCallback(true, faceWidgetSystemUIWidgetCallbackWrapper);
                        }
                    } else {
                        WallpaperUtils.removeSystemUIWidgetCallback(faceWidgetSystemUIWidgetCallbackWrapper);
                    }
                }
            } finally {
            }
        }
    }
}
