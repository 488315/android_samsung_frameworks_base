package com.android.systemui.settings.brightness;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.settings.brightness.SecBrightnessController;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BrightnessObserver extends ContentObserver {
    public final Function0 autoBrightnessContainer;
    public final Function0 autoBrightnessSwitch;
    public final Context context;
    public final Function0 isSwitchChecked;

    public BrightnessObserver(Context context, Function0 function0, Function0 function02, Function0 function03) {
        super(new Handler());
        this.context = context;
        this.autoBrightnessContainer = function0;
        this.autoBrightnessSwitch = function02;
        this.isSwitchChecked = function03;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        SecBrightnessController.Companion companion = SecBrightnessController.Companion;
        companion.getClass();
        if (!Intrinsics.areEqual(SecBrightnessController.BRIGHTNESS_MODE_URI, uri)) {
            companion.getClass();
            if (!Intrinsics.areEqual(SecBrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI, uri)) {
                return;
            }
        }
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
