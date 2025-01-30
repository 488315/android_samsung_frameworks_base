package com.android.systemui.volume.util;

import android.content.Context;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependency;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConfigurationWrapper {
    public final Context context;
    public int density;
    public int displayType;
    public float fontScale;
    public Locale locale;
    public final LogWrapper log;
    public boolean nightMode;
    public int orientation;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        logWrapper.m98d("ConfigurationWrapper", String.valueOf(this));
    }

    public final boolean isDensityOrFontScaleChanged() {
        ContextUtils.INSTANCE.getClass();
        Context context = this.context;
        int i = context.getResources().getConfiguration().densityDpi;
        float f = context.getResources().getConfiguration().fontScale;
        Locale locale = context.getResources().getConfiguration().locale;
        boolean isNightMode = ContextUtils.isNightMode(context);
        LogWrapper logWrapper = this.log;
        logWrapper.m98d("ConfigurationWrapper", "this=" + this + " / density=" + i + ", fontScale=" + f + ", locale=" + locale + ", nightMode=" + isNightMode);
        if (i == this.density) {
            if ((f == this.fontScale) && locale == this.locale && isNightMode == this.nightMode) {
                return false;
            }
        }
        logWrapper.m98d("ConfigurationWrapper", "density or font or scale has been changed!");
        this.density = i;
        this.fontScale = f;
        this.locale = locale;
        this.nightMode = isNightMode;
        return true;
    }

    public final boolean isDisplayTypeChanged() {
        ContextUtils.INSTANCE.getClass();
        Integer valueOf = Integer.valueOf(this.context.getResources().getConfiguration().semDisplayDeviceType);
        if (!(this.displayType != valueOf.intValue())) {
            valueOf = null;
        }
        if (valueOf == null) {
            return false;
        }
        this.displayType = valueOf.intValue();
        return true;
    }

    public final boolean isOrientationChanged() {
        ContextUtils.INSTANCE.getClass();
        Integer valueOf = Integer.valueOf(this.context.getResources().getConfiguration().orientation);
        if (!(this.orientation != valueOf.intValue())) {
            valueOf = null;
        }
        if (valueOf == null) {
            return false;
        }
        this.orientation = valueOf.intValue();
        this.log.m98d("ConfigurationWrapper", "orientation has been changed!");
        return true;
    }

    public final String toString() {
        boolean z = this.nightMode;
        int i = this.orientation;
        int i2 = this.density;
        float f = this.fontScale;
        Locale locale = this.locale;
        int i3 = this.displayType;
        StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("[ nightMode=", z, ", orientation=", i, ", density=");
        m66m.append(i2);
        m66m.append(", fontScale=");
        m66m.append(f);
        m66m.append(", locale=");
        m66m.append(locale);
        m66m.append(", displayType=");
        m66m.append(i3);
        m66m.append(" ]");
        return m66m.toString();
    }
}
