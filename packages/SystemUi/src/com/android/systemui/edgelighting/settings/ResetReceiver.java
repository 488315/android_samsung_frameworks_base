package com.android.systemui.edgelighting.settings;

import android.app.INotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.plugins.subscreen.SubRoom;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ResetReceiver extends BroadcastReceiver {
    public static void reset(Context context) {
        if (Feature.FEATURE_SUPPORT_EDGE_LIGHTING && Feature.FEATURE_SUPPORT_EDGE_LIGHTING_TILE) {
            EdgeLightingSettingUtils.initializeSettingValue(context.getContentResolver(), true);
            Settings.System.putIntForUser(context.getContentResolver(), "edge_lighting_thickness", 0, -2);
            EdgeLightingSettingUtils.setEdgeLightingColorType(context.getContentResolver(), 1);
            Settings.Global.putString(context.getContentResolver(), "edgelighting_recently_used_color", "");
            Settings.Global.putInt(context.getContentResolver(), "edgelighting_custom_color", -11761985);
            Settings.System.putIntForUser(context.getContentResolver(), "edge_lighting_transparency", 0, -2);
            EdgeLightingStyleManager edgeLightingStyleManager = EdgeLightingStyleManager.getInstance();
            ContentResolver contentResolver = context.getContentResolver();
            String str = EdgeLightingStyleManager.getInstance().getDefalutStyle().mKey;
            edgeLightingStyleManager.getClass();
            Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", str, -2);
            EdgeLightingSettingUtils.setEdgeLightingColorType(context.getContentResolver(), 1);
            EdgeLightingSettingUtils.setEdgeLightingBasicColorIndex(context.getContentResolver(), 100);
            Settings.System.putIntForUser(context.getContentResolver(), "edge_lighting", !Feature.isEdgeLightingDefaultOff() ? 1 : 0, -2);
            Settings.System.putIntForUser(context.getContentResolver(), "edge_lighting_duration", 0, -2);
            EdgeLightingSettingUtils.resetAppCustomColor(context);
            try {
                INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
                if (asInterface != null) {
                    asInterface.resetDefaultAllowEdgeLighting();
                }
            } catch (Exception unused) {
                Slog.d("ResetReceiver", "resetDefaultAllowEdgeLighting is failed");
            }
            SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
            edit.remove("version");
            edit.remove("all_application");
            edit.remove("enable_list");
            edit.apply();
            SharedPreferences.Editor edit2 = context.getSharedPreferences("edge_lighting_custom_text_color", 0).edit();
            edit2.clear();
            edit2.apply();
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)) {
            Slog.d("ResetReceiver", "onReceive : SOFT_RESET");
            reset(context);
        } else if ("com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action)) {
            Slog.d("ResetReceiver", "onReceive : DEMO_RESET_STARTED");
            reset(context);
        }
    }
}
