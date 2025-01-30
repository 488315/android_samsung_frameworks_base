package com.android.systemui.screenshot.sep;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AliveShotImageUtils {
    public static final Uri HANDLER_TRANSPARENCY_CONTENT_URI = Uri.parse("content://com.samsung.app.honeyspace.edge.settings.EdgeSettingProvider/handler_transparency");

    public static boolean isEdgePanelPresent(Context context) {
        if (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE") != null) {
            if (context != null && Settings.Secure.getIntForUser(context.getContentResolver(), "edge_enable", 0, -2) == 1) {
                return true;
            }
        }
        return false;
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
