package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BrightnessAnimationIcon {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LottieAnimationView brightnessIcon;
    public float iconAnimationValue;
    public final Lazy settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new BrightnessAnimationIcon$$ExternalSyntheticLambda0());

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

    public BrightnessAnimationIcon(LottieAnimationView lottieAnimationView) {
        this.brightnessIcon = lottieAnimationView;
    }

    public final void init(Context context) {
        LottieAnimationView lottieAnimationView = this.brightnessIcon;
        if (lottieAnimationView == null) {
            return;
        }
        lottieAnimationView.setAnimation("brightness_icon_85.json");
        Resources resources = context.getResources();
        if (DeviceState.isOpenTheme(context) || ((SettingsHelper) this.settingsHelper$delegate.getValue()).isColorThemeEnabled() || resources.getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            lottieAnimationView.addValueCallback(new KeyPath("normal 2", "**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(resources.getColor(R.color.animated_brightness_sun_icon_color, null), PorterDuff.Mode.SRC_ATOP)));
        }
    }

    public final void play(int i, int i2) {
        LottieAnimationView lottieAnimationView = this.brightnessIcon;
        if (lottieAnimationView == null) {
            return;
        }
        Float fValueOf = Float.valueOf(i / i2);
        if (Math.abs(this.iconAnimationValue - fValueOf.floatValue()) <= 1.0E-6d) {
            fValueOf = null;
        }
        if (fValueOf != null) {
            float fFloatValue = fValueOf.floatValue();
            this.iconAnimationValue = fFloatValue;
            lottieAnimationView.setProgress(fFloatValue);
        }
    }
}
