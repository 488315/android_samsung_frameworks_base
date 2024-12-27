package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface PluginSystemUIWallpaperUtils {
    int getColorByName(String str);

    boolean hasAdaptiveColorResult();

    boolean isOpenThemeLook();

    boolean isWhiteKeyguardWallpaper(String str);

    boolean isWhiteSubUiWallpaper(int i);

    void registerCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback, int i);

    void registerSubUiCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback, int i);

    void removeCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback);

    void removeSubUiCallback(PluginSystemUIWidgetCallback pluginSystemUIWidgetCallback);
}
