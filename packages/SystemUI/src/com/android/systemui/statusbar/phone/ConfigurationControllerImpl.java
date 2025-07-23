package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.LocaleList;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConfigurationControllerImpl implements ConfigurationController, StatusBarConfigurationController {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ConfigurationControllerImpl create(Context context);
    }

    public ConfigurationControllerImpl(Context context) {
        this.context = context;
        Rect rect = new Rect();
        this.maxBounds = rect;
        Configuration configuration = context.getResources().getConfiguration();
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

    public final boolean isLayoutRtl() {
        return this.layoutDirection == 1;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        ArrayList arrayList;
        int i;
        synchronized (this.listeners) {
            arrayList = new ArrayList(this.listeners);
        }
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
            if (((ArrayList) this.listeners).contains(configurationListener)) {
                configurationListener.onConfigChanged(configuration);
            }
        }
        float f = configuration.fontScale;
        int i4 = configuration.densityDpi;
        int i5 = configuration.uiMode & 48;
        boolean z = i5 != this.uiMode;
        if (i4 != this.density || f != this.fontScale || (this.inCarMode && z)) {
            int size2 = arrayList.size();
            int i6 = 0;
            while (i6 < size2) {
                Object obj2 = arrayList.get(i6);
                i6++;
                ConfigurationController.ConfigurationListener configurationListener2 = (ConfigurationController.ConfigurationListener) obj2;
                if (((ArrayList) this.listeners).contains(configurationListener2)) {
                    configurationListener2.onDensityOrFontScaleChanged();
                }
            }
            this.density = i4;
            this.fontScale = f;
        }
        int i7 = configuration.smallestScreenWidthDp;
        if (i7 != this.smallestScreenWidth) {
            this.smallestScreenWidth = i7;
            int size3 = arrayList.size();
            int i8 = 0;
            while (i8 < size3) {
                Object obj3 = arrayList.get(i8);
                i8++;
                ConfigurationController.ConfigurationListener configurationListener3 = (ConfigurationController.ConfigurationListener) obj3;
                if (((ArrayList) this.listeners).contains(configurationListener3)) {
                    configurationListener3.onSmallestScreenWidthChanged();
                }
            }
        }
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        if (!Intrinsics.areEqual(maxBounds, this.maxBounds)) {
            this.maxBounds.set(maxBounds);
            int size4 = arrayList.size();
            int i9 = 0;
            while (i9 < size4) {
                Object obj4 = arrayList.get(i9);
                i9++;
                ConfigurationController.ConfigurationListener configurationListener4 = (ConfigurationController.ConfigurationListener) obj4;
                if (((ArrayList) this.listeners).contains(configurationListener4)) {
                    configurationListener4.onMaxBoundsChanged();
                }
            }
        }
        LocaleList locales = configuration.getLocales();
        if (!Intrinsics.areEqual(locales, this.localeList)) {
            this.localeList = locales;
            int size5 = arrayList.size();
            int i10 = 0;
            while (i10 < size5) {
                Object obj5 = arrayList.get(i10);
                i10++;
                ConfigurationController.ConfigurationListener configurationListener5 = (ConfigurationController.ConfigurationListener) obj5;
                if (((ArrayList) this.listeners).contains(configurationListener5)) {
                    configurationListener5.onLocaleListChanged();
                }
            }
        }
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && (i = configuration.semDisplayDeviceType) != this.displayDeviceType) {
            this.displayDeviceType = i;
            int size6 = arrayList.size();
            int i11 = 0;
            while (i11 < size6) {
                Object obj6 = arrayList.get(i11);
                i11++;
                ConfigurationController.ConfigurationListener configurationListener6 = (ConfigurationController.ConfigurationListener) obj6;
                if (((ArrayList) this.listeners).contains(configurationListener6)) {
                    configurationListener6.onDisplayDeviceTypeChanged();
                }
            }
        }
        if (z) {
            this.context.getTheme().applyStyle(this.context.getThemeResId(), true);
            this.uiMode = i5;
            int size7 = arrayList.size();
            int i12 = 0;
            while (i12 < size7) {
                Object obj7 = arrayList.get(i12);
                i12++;
                ConfigurationController.ConfigurationListener configurationListener7 = (ConfigurationController.ConfigurationListener) obj7;
                if (((ArrayList) this.listeners).contains(configurationListener7)) {
                    configurationListener7.onUiModeChanged();
                }
            }
        }
        if (this.layoutDirection != configuration.getLayoutDirection()) {
            this.layoutDirection = configuration.getLayoutDirection();
            int size8 = arrayList.size();
            int i13 = 0;
            while (i13 < size8) {
                Object obj8 = arrayList.get(i13);
                i13++;
                ConfigurationController.ConfigurationListener configurationListener8 = (ConfigurationController.ConfigurationListener) obj8;
                if (((ArrayList) this.listeners).contains(configurationListener8)) {
                    configurationListener8.onLayoutDirectionChanged(this.layoutDirection == 1);
                }
            }
        }
        if ((this.lastConfig.updateFrom(configuration) & Integer.MIN_VALUE) != 0) {
            int size9 = arrayList.size();
            int i14 = 0;
            while (i14 < size9) {
                Object obj9 = arrayList.get(i14);
                i14++;
                ConfigurationController.ConfigurationListener configurationListener9 = (ConfigurationController.ConfigurationListener) obj9;
                if (((ArrayList) this.listeners).contains(configurationListener9)) {
                    configurationListener9.onThemeChanged();
                }
            }
        }
        int i15 = configuration.orientation;
        if (this.orientation != i15) {
            this.orientation = i15;
            int size10 = arrayList.size();
            while (i2 < size10) {
                Object obj10 = arrayList.get(i2);
                i2++;
                ConfigurationController.ConfigurationListener configurationListener10 = (ConfigurationController.ConfigurationListener) obj10;
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
