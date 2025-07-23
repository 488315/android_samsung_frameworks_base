package com.android.systemui.settings.brightness;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecBrightnessController {
    public final BrightnessControllerObserver brightnessControllerObserver;
    public BrightnessDialog brightnessDialog;
    public final Context context;
    public final ToggleSlider control;
    public final Handler handler;
    public final float maximumBacklight;
    public final float minimumBacklight;
    public int sliderAnimationDuration;
    public int transitionTime = -1;
    public static final Companion Companion = new Companion(null);
    public static final Uri BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
    public static final Uri USING_HIGH_BRIGHTNESS_DIALOG_URI = Settings.System.getUriFor(SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN);
    public static final Uri HIGH_BRIGHTNESS_MODE_ENTER_URI = Settings.System.getUriFor("high_brightness_mode_pms_enter");
    public static final Uri SCREEN_DISPLAY_OUTDOOR_MODE_URI = Settings.System.getUriFor("display_outdoor_mode");
    public static final Uri SEC_AUTO_BRIGHTNESS_TRANSITION_TIME_URI = Settings.System.getUriFor(SettingsHelper.INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SecBrightnessController(Handler handler, ToggleSlider toggleSlider, Context context, Handler handler2) {
        this.handler = handler;
        this.control = toggleSlider;
        this.context = context;
        if (((PowerManager) context.getSystemService(PowerManager.class)) != null) {
            this.minimumBacklight = r0.getMinimumScreenBrightnessSetting();
            this.maximumBacklight = r0.getMaximumScreenBrightnessSetting();
        } else {
            this.minimumBacklight = 0.0f;
            this.maximumBacklight = 255.0f;
        }
        toggleSlider.setMax((int) (this.maximumBacklight - this.minimumBacklight));
        this.brightnessControllerObserver = new BrightnessControllerObserver(handler, handler2, context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0089, code lost:
    
        if (r2 != false) goto L76;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleMessage(android.os.Message r6) {
        /*
            Method dump skipped, instructions count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.brightness.SecBrightnessController.handleMessage(android.os.Message):boolean");
    }
}
