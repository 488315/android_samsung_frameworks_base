package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
