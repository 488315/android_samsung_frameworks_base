package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
