package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
    public static final Uri SYSTEM_BRIGHTNESS_ENABLED_URI = Settings.System.getUriFor("pms_notification_panel_brightness_adjustment");
    public static final Uri HIGH_BRIGHTNESS_MODE_ENTER_URI = Settings.System.getUriFor("high_brightness_mode_pms_enter");
    public static final Uri SCREEN_DISPLAY_OUTDOOR_MODE_URI = Settings.System.getUriFor("display_outdoor_mode");
    public static final Uri SEC_AUTO_BRIGHTNESS_TRANSITION_TIME_URI = Settings.System.getUriFor(SettingsHelper.INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecBrightnessController(Handler handler, ToggleSlider toggleSlider, Context context, Handler handler2) {
        Unit unit;
        this.handler = handler;
        this.control = toggleSlider;
        this.context = context;
        if (((PowerManager) context.getSystemService(PowerManager.class)) != null) {
            this.minimumBacklight = r0.getMinimumScreenBrightnessSetting();
            this.maximumBacklight = r0.getMaximumScreenBrightnessSetting();
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            this.minimumBacklight = 0.0f;
            this.maximumBacklight = 255.0f;
        }
        toggleSlider.setMax((int) (this.maximumBacklight - this.minimumBacklight));
        this.brightnessControllerObserver = new BrightnessControllerObserver(handler, handler2, context);
    }

    public final boolean handleMessage(Message message) {
        SecBrightnessSliderController secBrightnessSliderController;
        SecBrightnessSliderController secBrightnessSliderController2;
        SecBrightnessSliderController secBrightnessSliderController3;
        boolean z;
        SecBrightnessSliderController secBrightnessSliderController4;
        SecBrightnessSliderController secBrightnessSliderController5;
        int i = message.what;
        ToggleSlider toggleSlider = this.control;
        ApplicationInfo applicationInfo = null;
        switch (i) {
            case 6:
                BrightnessSliderController brightnessSliderController = toggleSlider instanceof BrightnessSliderController ? (BrightnessSliderController) toggleSlider : null;
                if (brightnessSliderController != null && (secBrightnessSliderController = brightnessSliderController.mSecBrightnessSliderController) != null) {
                    secBrightnessSliderController.highBrightnessDialogEnabled = message.arg1 == 0;
                }
                return true;
            case 7:
                BrightnessSliderController brightnessSliderController2 = toggleSlider instanceof BrightnessSliderController ? (BrightnessSliderController) toggleSlider : null;
                if (brightnessSliderController2 != null && (secBrightnessSliderController2 = brightnessSliderController2.mSecBrightnessSliderController) != null) {
                    Lazy lazy = secBrightnessSliderController2.packageManager$delegate;
                    String str = (String) message.obj;
                    boolean isEmpty = TextUtils.isEmpty(str);
                    try {
                        applicationInfo = ((PackageManager) lazy.getValue()).getApplicationInfo(str, 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                    if (applicationInfo != null) {
                        str = ((PackageManager) lazy.getValue()).getApplicationLabel(applicationInfo).toString();
                    }
                    secBrightnessSliderController2.appUsingBrightness = str;
                    secBrightnessSliderController2.updateSystemBrightnessEnabled(isEmpty);
                }
                return true;
            case 8:
                BrightnessSliderController brightnessSliderController3 = toggleSlider instanceof BrightnessSliderController ? (BrightnessSliderController) toggleSlider : null;
                if (brightnessSliderController3 != null && (secBrightnessSliderController3 = brightnessSliderController3.mSecBrightnessSliderController) != null) {
                    z = message.arg1 != 0;
                    secBrightnessSliderController3.outdoormode = z;
                    secBrightnessSliderController3.updateSystemBrightnessEnabled(!z);
                }
                return true;
            case 9:
                BrightnessSliderController brightnessSliderController4 = toggleSlider instanceof BrightnessSliderController ? (BrightnessSliderController) toggleSlider : null;
                if (brightnessSliderController4 != null && (secBrightnessSliderController4 = brightnessSliderController4.mSecBrightnessSliderController) != null) {
                    ((BrightnessAnimationIcon) secBrightnessSliderController4.brightnessIcon$delegate.getValue()).init(this.context);
                }
                return true;
            case 10:
                BrightnessSliderController brightnessSliderController5 = toggleSlider instanceof BrightnessSliderController ? (BrightnessSliderController) toggleSlider : null;
                if (brightnessSliderController5 != null && (secBrightnessSliderController5 = brightnessSliderController5.mSecBrightnessSliderController) != null) {
                    z = message.arg1 != 0;
                    Log.d(SecBrightnessSliderController.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("updateHighBrightnessModeEnter : ", z));
                    SecBrightnessSliderView secBrightnessSliderView = secBrightnessSliderController5.view.mSecBrightnessSliderView;
                    if (secBrightnessSliderView != null) {
                        secBrightnessSliderView.highBrightnessModeEnter = z;
                    }
                }
                return true;
            case 11:
                this.transitionTime = message.arg1;
                return true;
            default:
                return false;
        }
    }
}
