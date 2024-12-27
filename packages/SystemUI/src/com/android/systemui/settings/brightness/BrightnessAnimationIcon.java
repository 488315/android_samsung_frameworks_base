package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BrightnessAnimationIcon {
    public final LottieAnimationView brightnessIcon;
    public float iconAnimationValue;
    public final Lazy settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.BrightnessAnimationIcon$settingsHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        }
    });

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Float valueOf = Float.valueOf(i / i2);
        if (Math.abs(this.iconAnimationValue - valueOf.floatValue()) <= 1.0E-6d) {
            valueOf = null;
        }
        if (valueOf != null) {
            float floatValue = valueOf.floatValue();
            this.iconAnimationValue = floatValue;
            lottieAnimationView.setProgress(floatValue);
        }
    }
}
