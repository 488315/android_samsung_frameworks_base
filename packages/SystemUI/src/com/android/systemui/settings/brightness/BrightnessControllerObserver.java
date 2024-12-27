package com.android.systemui.settings.brightness;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.settings.brightness.SecBrightnessController;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public final class BrightnessControllerObserver extends ContentObserver {
    public static final String TAG;
    public final Handler backgroundHandler;
    public final Context context;
    public final Handler handler;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(BrightnessControllerObserver.class).getSimpleName();
    }

    public BrightnessControllerObserver(Handler handler, Handler handler2, Context context) {
        super(new Handler());
        this.handler = handler;
        this.backgroundHandler = handler2;
        this.context = context;
    }

    public static final void onChange$toPost(final BrightnessControllerObserver brightnessControllerObserver, final String str, final int i, final int i2) {
        Log.d(TAG, "BrightnessObserver.onChange() : ".concat(str));
        final ContentResolver contentResolver = brightnessControllerObserver.context.getContentResolver();
        if (contentResolver != null) {
            brightnessControllerObserver.backgroundHandler.post(new Runnable() { // from class: com.android.systemui.settings.brightness.BrightnessControllerObserver$onChange$toPost$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    brightnessControllerObserver.handler.obtainMessage(i2, Settings.System.getIntForUser(contentResolver, str, i, -2), 0).sendToTarget();
                }
            });
        }
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        if (z) {
            return;
        }
        SecBrightnessController.Companion companion = SecBrightnessController.Companion;
        companion.getClass();
        if (Intrinsics.areEqual(uri, SecBrightnessController.USING_HIGH_BRIGHTNESS_DIALOG_URI)) {
            onChange$toPost(this, SettingsHelper.INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, 0, 6);
            return;
        }
        companion.getClass();
        if (Intrinsics.areEqual(uri, SecBrightnessController.SYSTEM_BRIGHTNESS_ENABLED_URI)) {
            Log.d(TAG, "BrightnessObserver.onChange() : pms_notification_panel_brightness_adjustment");
            final ContentResolver contentResolver = this.context.getContentResolver();
            if (contentResolver != null) {
                final String str = "pms_notification_panel_brightness_adjustment";
                final int i = 7;
                this.backgroundHandler.post(new Runnable() { // from class: com.android.systemui.settings.brightness.BrightnessControllerObserver$onChange$postString$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.handler.obtainMessage(i, 0, 0, Settings.System.getStringForUser(contentResolver, str, -2)).sendToTarget();
                    }
                });
                return;
            }
            return;
        }
        companion.getClass();
        if (Intrinsics.areEqual(uri, SecBrightnessController.HIGH_BRIGHTNESS_MODE_ENTER_URI)) {
            onChange$toPost(this, "high_brightness_mode_pms_enter", 0, 10);
            return;
        }
        companion.getClass();
        if (Intrinsics.areEqual(uri, SecBrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI)) {
            onChange$toPost(this, "display_outdoor_mode", 0, 8);
            return;
        }
        companion.getClass();
        if (Intrinsics.areEqual(uri, SecBrightnessController.SEC_AUTO_BRIGHTNESS_TRANSITION_TIME_URI)) {
            onChange$toPost(this, SettingsHelper.INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME, -1, 11);
        }
    }
}
