package com.android.systemui.volume.util;

import android.content.Context;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependency;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ConfigurationWrapper {
    public final Context context;
    public int density;
    public int displayType;
    public float fontScale;
    public Locale locale;
    public final LogWrapper log;
    public boolean nightMode;
    public int orientation;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ConfigurationWrapper(VolumeDependency volumeDependency) {
        Context context = (Context) volumeDependency.get(Context.class);
        this.context = context;
        LogWrapper logWrapper = (LogWrapper) volumeDependency.get(LogWrapper.class);
        this.log = logWrapper;
        this.nightMode = ContextUtils.isNightMode(context);
        ContextUtils.INSTANCE.getClass();
        this.orientation = context.getResources().getConfiguration().orientation;
        this.density = context.getResources().getConfiguration().densityDpi;
        this.fontScale = context.getResources().getConfiguration().fontScale;
        this.locale = context.getResources().getConfiguration().locale;
        this.displayType = context.getResources().getConfiguration().semDisplayDeviceType;
        logWrapper.d("ConfigurationWrapper", String.valueOf(this));
    }

    public final boolean isDensityOrFontScaleChanged() {
        ContextUtils contextUtils = ContextUtils.INSTANCE;
        Context context = this.context;
        contextUtils.getClass();
        int i = context.getResources().getConfiguration().densityDpi;
        float f = this.context.getResources().getConfiguration().fontScale;
        Locale locale = this.context.getResources().getConfiguration().locale;
        boolean isNightMode = ContextUtils.isNightMode(this.context);
        LogWrapper logWrapper = this.log;
        logWrapper.d("ConfigurationWrapper", "this=" + this + " / density=" + i + ", fontScale=" + f + ", locale=" + locale + ", nightMode=" + isNightMode);
        if (i == this.density && f == this.fontScale && locale == this.locale && isNightMode == this.nightMode) {
            return false;
        }
        logWrapper.d("ConfigurationWrapper", "density or font or scale has been changed!");
        this.density = i;
        this.fontScale = f;
        this.locale = locale;
        this.nightMode = isNightMode;
        return true;
    }

    public final boolean isDisplayTypeChanged() {
        ContextUtils contextUtils = ContextUtils.INSTANCE;
        Context context = this.context;
        contextUtils.getClass();
        int i = context.getResources().getConfiguration().semDisplayDeviceType;
        Integer valueOf = Integer.valueOf(i);
        if (this.displayType == i) {
            valueOf = null;
        }
        if (valueOf == null) {
            return false;
        }
        this.displayType = valueOf.intValue();
        return true;
    }

    public final boolean isOrientationChanged() {
        ContextUtils contextUtils = ContextUtils.INSTANCE;
        Context context = this.context;
        contextUtils.getClass();
        int i = context.getResources().getConfiguration().orientation;
        Integer valueOf = Integer.valueOf(i);
        if (this.orientation == i) {
            valueOf = null;
        }
        if (valueOf == null) {
            return false;
        }
        this.orientation = valueOf.intValue();
        this.log.d("ConfigurationWrapper", "orientation has been changed!");
        return true;
    }

    public final String toString() {
        boolean z = this.nightMode;
        int i = this.orientation;
        int i2 = this.density;
        float f = this.fontScale;
        Locale locale = this.locale;
        int i3 = this.displayType;
        StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("[ nightMode=", i, ", orientation=", z, ", density=");
        m.append(i2);
        m.append(", fontScale=");
        m.append(f);
        m.append(", locale=");
        m.append(locale);
        m.append(", displayType=");
        m.append(i3);
        m.append(" ]");
        return m.toString();
    }
}
