package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.LocaleList;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.List;

public final class ConfigurationControllerImpl implements ConfigurationController {
    public final Context context;
    public int density;
    public int displayDeviceType;
    public float fontScale;
    public final boolean inCarMode;
    public int layoutDirection;
    public LocaleList localeList;
    public final Rect maxBounds;
    public int orientation;
    public int smallestScreenWidth;
    public int uiMode;
    public final List listeners = new ArrayList();
    public final Configuration lastConfig = new Configuration();

    public ConfigurationControllerImpl(Context context) {
        Rect rect = new Rect();
        this.maxBounds = rect;
        Configuration configuration = context.getResources().getConfiguration();
        this.context = context;
        this.fontScale = configuration.fontScale;
        this.density = configuration.densityDpi;
        this.smallestScreenWidth = configuration.smallestScreenWidthDp;
        rect.set(configuration.windowConfiguration.getMaxBounds());
        int i = configuration.uiMode;
        this.inCarMode = (i & 15) == 3;
        this.uiMode = i & 48;
        this.localeList = configuration.getLocales();
        this.layoutDirection = configuration.getLayoutDirection();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            this.displayDeviceType = configuration.semDisplayDeviceType;
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
        synchronized (this.listeners) {
            ((ArrayList) this.listeners).add(configurationListener);
        }
        configurationListener.onDensityOrFontScaleChanged();
    }

    public final String getNightModeName() {
        int i = this.uiMode & 48;
        return i != 0 ? i != 16 ? i != 32 ? "err" : "night" : "day" : "undefined";
    }

    public final boolean isLayoutRtl() {
        return this.layoutDirection == 1;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        ArrayList<ConfigurationController.ConfigurationListener> arrayList;
        int i;
        synchronized (this.listeners) {
            arrayList = new ArrayList(this.listeners);
        }
        for (ConfigurationController.ConfigurationListener configurationListener : arrayList) {
            if (((ArrayList) this.listeners).contains(configurationListener)) {
                configurationListener.onConfigChanged(configuration);
            }
        }
        float f = configuration.fontScale;
        int i2 = configuration.densityDpi;
        int i3 = configuration.uiMode & 48;
        boolean z = i3 != this.uiMode;
        if (i2 != this.density || f != this.fontScale || (this.inCarMode && z)) {
            for (ConfigurationController.ConfigurationListener configurationListener2 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener2)) {
                    configurationListener2.onDensityOrFontScaleChanged();
                }
            }
            this.density = i2;
            this.fontScale = f;
        }
        int i4 = configuration.smallestScreenWidthDp;
        if (i4 != this.smallestScreenWidth) {
            this.smallestScreenWidth = i4;
            for (ConfigurationController.ConfigurationListener configurationListener3 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener3)) {
                    configurationListener3.onSmallestScreenWidthChanged();
                }
            }
        }
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        if (!maxBounds.equals(this.maxBounds)) {
            this.maxBounds.set(maxBounds);
            for (ConfigurationController.ConfigurationListener configurationListener4 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener4)) {
                    configurationListener4.onMaxBoundsChanged();
                }
            }
        }
        LocaleList locales = configuration.getLocales();
        if (!locales.equals(this.localeList)) {
            this.localeList = locales;
            for (ConfigurationController.ConfigurationListener configurationListener5 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener5)) {
                    configurationListener5.onLocaleListChanged();
                }
            }
        }
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && (i = configuration.semDisplayDeviceType) != this.displayDeviceType) {
            this.displayDeviceType = i;
            for (ConfigurationController.ConfigurationListener configurationListener6 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener6)) {
                    configurationListener6.onDisplayDeviceTypeChanged();
                }
            }
        }
        if (z) {
            this.context.getTheme().applyStyle(this.context.getThemeResId(), true);
            this.uiMode = i3;
            for (ConfigurationController.ConfigurationListener configurationListener7 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener7)) {
                    configurationListener7.onUiModeChanged();
                }
            }
        }
        if (this.layoutDirection != configuration.getLayoutDirection()) {
            this.layoutDirection = configuration.getLayoutDirection();
            for (ConfigurationController.ConfigurationListener configurationListener8 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener8)) {
                    configurationListener8.onLayoutDirectionChanged(this.layoutDirection == 1);
                }
            }
        }
        if ((this.lastConfig.updateFrom(configuration) & Integer.MIN_VALUE) != 0) {
            for (ConfigurationController.ConfigurationListener configurationListener9 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener9)) {
                    configurationListener9.onThemeChanged();
                }
            }
        }
        int i5 = configuration.orientation;
        if (this.orientation != i5) {
            this.orientation = i5;
            for (ConfigurationController.ConfigurationListener configurationListener10 : arrayList) {
                if (((ArrayList) this.listeners).contains(configurationListener10)) {
                    configurationListener10.onOrientationChanged(this.orientation);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
        synchronized (this.listeners) {
            ((ArrayList) this.listeners).remove(configurationListener);
        }
    }
}
