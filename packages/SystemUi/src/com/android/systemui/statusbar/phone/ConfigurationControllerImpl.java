package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.LocaleList;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConfigurationControllerImpl implements ConfigurationController {
    public final Context context;
    public int density;
    public int displayDeviceType;
    public float fontScale;
    public final boolean inCarMode;
    public int layoutDirection;
    public LocaleList localeList;
    public final Rect maxBounds;
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
        ((ArrayList) this.listeners).add(configurationListener);
        configurationListener.onDensityOrFontScaleChanged();
    }

    public final boolean isLayoutRtl() {
        return this.layoutDirection == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0048, code lost:
    
        if (r5 == false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:109:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x019a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        Rect maxBounds;
        Rect rect;
        LocaleList locales;
        int i2;
        List list = this.listeners;
        ArrayList<ConfigurationController.ConfigurationListener> arrayList = new ArrayList(list);
        for (ConfigurationController.ConfigurationListener configurationListener : arrayList) {
            if (((ArrayList) list).contains(configurationListener)) {
                configurationListener.onConfigChanged(configuration);
            }
        }
        float f = configuration.fontScale;
        int i3 = configuration.densityDpi;
        int i4 = configuration.uiMode & 48;
        boolean z = i4 != this.uiMode;
        if (i3 == this.density) {
            if (f == this.fontScale) {
                if (this.inCarMode) {
                }
                i = configuration.smallestScreenWidthDp;
                if (i != this.smallestScreenWidth) {
                    this.smallestScreenWidth = i;
                    for (ConfigurationController.ConfigurationListener configurationListener2 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener2)) {
                            configurationListener2.onSmallestScreenWidthChanged();
                        }
                    }
                }
                maxBounds = configuration.windowConfiguration.getMaxBounds();
                rect = this.maxBounds;
                if (!Intrinsics.areEqual(maxBounds, rect)) {
                    rect.set(maxBounds);
                    for (ConfigurationController.ConfigurationListener configurationListener3 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener3)) {
                            configurationListener3.onMaxBoundsChanged();
                        }
                    }
                }
                locales = configuration.getLocales();
                if (!Intrinsics.areEqual(locales, this.localeList)) {
                    this.localeList = locales;
                    for (ConfigurationController.ConfigurationListener configurationListener4 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener4)) {
                            configurationListener4.onLocaleListChanged();
                        }
                    }
                }
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && (i2 = configuration.semDisplayDeviceType) != this.displayDeviceType) {
                    this.displayDeviceType = i2;
                    for (ConfigurationController.ConfigurationListener configurationListener5 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener5)) {
                            configurationListener5.onDisplayDeviceTypeChanged();
                        }
                    }
                }
                if (z) {
                    Context context = this.context;
                    context.getTheme().applyStyle(context.getThemeResId(), true);
                    this.uiMode = i4;
                    for (ConfigurationController.ConfigurationListener configurationListener6 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener6)) {
                            configurationListener6.onUiModeChanged();
                        }
                    }
                }
                if (this.layoutDirection != configuration.getLayoutDirection()) {
                    this.layoutDirection = configuration.getLayoutDirection();
                    for (ConfigurationController.ConfigurationListener configurationListener7 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener7)) {
                            configurationListener7.onLayoutDirectionChanged(this.layoutDirection == 1);
                        }
                    }
                }
                if ((this.lastConfig.updateFrom(configuration) & VideoPlayer.MEDIA_ERROR_SYSTEM) == 0) {
                    for (ConfigurationController.ConfigurationListener configurationListener8 : arrayList) {
                        if (((ArrayList) list).contains(configurationListener8)) {
                            configurationListener8.onThemeChanged();
                        }
                    }
                    return;
                }
                return;
            }
        }
        for (ConfigurationController.ConfigurationListener configurationListener9 : arrayList) {
            if (((ArrayList) list).contains(configurationListener9)) {
                configurationListener9.onDensityOrFontScaleChanged();
            }
        }
        this.density = i3;
        this.fontScale = f;
        i = configuration.smallestScreenWidthDp;
        if (i != this.smallestScreenWidth) {
        }
        maxBounds = configuration.windowConfiguration.getMaxBounds();
        rect = this.maxBounds;
        if (!Intrinsics.areEqual(maxBounds, rect)) {
        }
        locales = configuration.getLocales();
        if (!Intrinsics.areEqual(locales, this.localeList)) {
        }
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            this.displayDeviceType = i2;
            while (r2.hasNext()) {
            }
        }
        if (z) {
        }
        if (this.layoutDirection != configuration.getLayoutDirection()) {
        }
        if ((this.lastConfig.updateFrom(configuration) & VideoPlayer.MEDIA_ERROR_SYSTEM) == 0) {
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.listeners).remove((ConfigurationController.ConfigurationListener) obj);
    }
}
