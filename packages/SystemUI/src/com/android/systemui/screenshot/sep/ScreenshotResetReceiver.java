package com.android.systemui.screenshot.sep;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;

public class ScreenshotResetReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        if (data == null || !"com.samsung.android.app.smartcapture".equals(data.getSchemeSpecificPart())) {
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Log.i("Screenshot", "resetScreenshotSettings called.");
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_KNOX_SUPPORT_TACTICAL_MODE")) {
            Settings.System.putInt(contentResolver, "enable_smart_capture", 0);
        } else {
            Settings.System.putInt(contentResolver, "enable_smart_capture", 1);
        }
        Settings.System.putInt(contentResolver, "exclude_systemui_screenshots", 0);
        Settings.System.putInt(contentResolver, "delete_shared_screenshots", 0);
        Settings.System.putString(contentResolver, "smart_capture_screenshot_format", "JPG");
        Settings.System.putString(contentResolver, "screenshot_current_save_dir", "external_primary:DCIM/Screenshots");
        Settings.System.putInt(contentResolver, "save_original_screenshots", 0);
    }
}
