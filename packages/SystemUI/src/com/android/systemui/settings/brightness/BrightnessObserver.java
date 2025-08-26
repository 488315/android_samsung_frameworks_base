package com.android.systemui.settings.brightness;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SwitchCompat;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class BrightnessObserver extends ContentObserver {
    public final Function0 autoBrightnessContainer;
    public final Function0 autoBrightnessSwitch;
    public final Function0 brightnessDetailSliderView;
    public final Context context;
    public final Function0 isSwitchChecked;

    public BrightnessObserver(Context context, Function0 function0, Function0 function02, Function0 function03, Function0 function04) {
        super(new Handler());
        this.context = context;
        this.autoBrightnessContainer = function0;
        this.autoBrightnessSwitch = function02;
        this.brightnessDetailSliderView = function03;
        this.isSwitchChecked = function04;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        BrightnessDetailSliderView brightnessDetailSliderView = (BrightnessDetailSliderView) this.brightnessDetailSliderView.invoke();
        if (brightnessDetailSliderView != null) {
            boolean z2 = Integer.parseInt(Settings.System.getStringForUser(this.context.getContentResolver(), "high_brightness_mode_pms_enter", -2)) != 0;
            EmergencyButtonController$$ExternalSyntheticOutline0.m("updateHighBrightnessModeEnter : ", "BrightnessDetailSlider", z2);
            brightnessDetailSliderView.mHighBrightnessModeEnter = z2;
        }
        SecBrightnessController.Companion.getClass();
        if (Intrinsics.areEqual(SecBrightnessController.BRIGHTNESS_MODE_URI, uri) || Intrinsics.areEqual(SecBrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI, uri)) {
            LinearLayout linearLayout = (LinearLayout) this.autoBrightnessContainer.invoke();
            if (linearLayout != null) {
                linearLayout.setClickable(true);
            }
            SwitchCompat switchCompat = (SwitchCompat) this.autoBrightnessSwitch.invoke();
            if (switchCompat != null) {
                switchCompat.setEnabled(true);
                switchCompat.setChecked(((Boolean) this.isSwitchChecked.invoke()).booleanValue());
            }
        }
    }
}
