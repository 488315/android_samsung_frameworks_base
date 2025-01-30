package com.android.systemui.facewidget.plugin;

import android.app.SemWallpaperColors;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.annotations.VersionCheckingProxy;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWidgetCallback;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.widget.SystemUIWidgetCallback;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetWallpaperUtilsWrapper implements PluginSystemUIWallpaperUtils {
    public final HashMap mCallbackMap = new HashMap();
    public final KeyguardWallpaper mKeyguardWallpaper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FaceWidgetSystemUIWidgetCallbackWrapper implements SystemUIWidgetCallback {
        public final PluginSystemUIWidgetCallback mCallback;

        public FaceWidgetSystemUIWidgetCallbackWrapper(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback) {
            this.mCallback = pluginSystemUIWidgetCallback;
        }

        @Override // com.android.systemui.widget.SystemUIWidgetCallback
        public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
            PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback = this.mCallback;
            if (pluginSystemUIWidgetCallback != null) {
                int i = (int) j;
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("updateStyle: updateFlag = ", KeyguardWallpaperColors.getChangeFlagsString(i), ", colors = ");
                m4m.append(semWallpaperColors == null ? "null" : semWallpaperColors.toSimpleString());
                Log.d("FaceWidgetWallpaperUtilsWrapper", m4m.toString());
                pluginSystemUIWidgetCallback.updateStyle(i, semWallpaperColors);
            }
        }
    }

    public FaceWidgetWallpaperUtilsWrapper(KeyguardWallpaper keyguardWallpaper) {
        this.mKeyguardWallpaper = keyguardWallpaper;
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
        return WallpaperUtils.mSettingsHelper.isOpenThemeLockWallpaper();
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
            if (!this.mCallbackMap.containsKey(pluginSystemUIWidgetCallback)) {
                FaceWidgetSystemUIWidgetCallbackWrapper faceWidgetSystemUIWidgetCallbackWrapper = new FaceWidgetSystemUIWidgetCallbackWrapper((PluginSystemUIWidgetCallback) new VersionCheckingProxy(PluginSystemUIWidgetCallback.class, pluginSystemUIWidgetCallback, new FaceWidgetWallpaperUtilsWrapper$$ExternalSyntheticLambda0()).get());
                this.mCallbackMap.put(pluginSystemUIWidgetCallback, faceWidgetSystemUIWidgetCallbackWrapper);
                long j = i;
                Log.d("FaceWidgetWallpaperUtilsWrapper", "registerCallback: flags = " + KeyguardWallpaperColors.getChangeFlagsString(j));
                if (z) {
                    if (WallpaperEventNotifier.getInstance() != null) {
                        WallpaperEventNotifier.getInstance().registerCallback(true, faceWidgetSystemUIWidgetCallbackWrapper, j);
                    }
                } else {
                    WallpaperUtils.registerSystemUIWidgetCallback(faceWidgetSystemUIWidgetCallbackWrapper, j);
                }
            }
        }
    }

    public final synchronized void removeCallback(boolean z, PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback) {
        if (pluginSystemUIWidgetCallback != null) {
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
        }
    }
}
