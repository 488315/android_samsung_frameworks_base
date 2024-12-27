package com.android.systemui.plugins.keyguardstatusview;

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
