package com.android.systemui.screenshot.sep;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AliveShotImageUtils {
    public static final Uri HANDLER_TRANSPARENCY_CONTENT_URI = Uri.parse("content://com.samsung.app.honeyspace.edge.settings.EdgeSettingProvider/handler_transparency");

    public static boolean isEdgePanelPresent(Context context) {
        return (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE") == null || context == null || Settings.Secure.getIntForUser(context.getContentResolver(), "edge_enable", 0, -2) != 1) ? false : true;
    }

    public static void resetEdgeTransparency(int i, Context context) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("handler_transparency", Integer.valueOf(i));
            context.getContentResolver().insert(HANDLER_TRANSPARENCY_CONTENT_URI, contentValues);
        } catch (IllegalArgumentException unused) {
            Log.d("Screenshot", "resetEdgeTransparency, exception occurred");
        }
    }
}
