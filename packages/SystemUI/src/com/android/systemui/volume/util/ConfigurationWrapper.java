package com.android.systemui.volume.util;

import android.content.Context;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependency;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
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
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        boolean zIsNightMode = ContextUtils.isNightMode(this.context);
        LogWrapper logWrapper = this.log;
        logWrapper.d("ConfigurationWrapper", "this=" + this + " / density=" + i + ", fontScale=" + f + ", locale=" + locale + ", nightMode=" + zIsNightMode);
        if (i == this.density && f == this.fontScale && locale == this.locale && zIsNightMode == this.nightMode) {
            return false;
        }
        logWrapper.d("ConfigurationWrapper", "density or font or scale has been changed!");
        this.density = i;
        this.fontScale = f;
        this.locale = locale;
        this.nightMode = zIsNightMode;
        return true;
    }

    public final boolean isDisplayTypeChanged() {
        ContextUtils contextUtils = ContextUtils.INSTANCE;
        Context context = this.context;
        contextUtils.getClass();
        int i = context.getResources().getConfiguration().semDisplayDeviceType;
        Integer numValueOf = Integer.valueOf(i);
        if (this.displayType == i) {
            numValueOf = null;
        }
        if (numValueOf == null) {
            return false;
        }
        this.displayType = numValueOf.intValue();
        return true;
    }

    public final boolean isOrientationChanged() {
        ContextUtils contextUtils = ContextUtils.INSTANCE;
        Context context = this.context;
        contextUtils.getClass();
        int i = context.getResources().getConfiguration().orientation;
        Integer numValueOf = Integer.valueOf(i);
        if (this.orientation == i) {
            numValueOf = null;
        }
        if (numValueOf == null) {
            return false;
        }
        this.orientation = numValueOf.intValue();
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
        StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("[ nightMode=", i, ", orientation=", z, ", density=");
        sbM.append(i2);
        sbM.append(", fontScale=");
        sbM.append(f);
        sbM.append(", locale=");
        sbM.append(locale);
        sbM.append(", displayType=");
        sbM.append(i3);
        sbM.append(" ]");
        return sbM.toString();
    }
}
