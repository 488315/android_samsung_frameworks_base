package com.android.systemui.edgelighting;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Slog;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingManager;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.samsung.android.sepunion.SemEventDelegationManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EdgeLightingReceiver extends BroadcastReceiver {
    public final HandlerC12951 mHandler = new Handler(Looper.myLooper()) { // from class: com.android.systemui.edgelighting.EdgeLightingReceiver.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Context context = (Context) message.obj;
            int i = message.what;
            boolean z = false;
            if (i == 0) {
                String string = message.getData().getString("pkg_name");
                if (string != null) {
                    EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(context);
                    edgeLightingSettingManager.getClass();
                    List appInfoSupportingEdgeLighting = EdgeLightingSettingUtils.getAppInfoSupportingEdgeLighting(context.getPackageManager(), string);
                    if (appInfoSupportingEdgeLighting == null || appInfoSupportingEdgeLighting.size() == 0) {
                        Slog.d("EdgeLightingSettingManager", "addSilentInstalledPackage : no support package ".concat(string));
                        return;
                    }
                    if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
                        Slog.d("EdgeLightingSettingManager", "addSilentInstalledPackage : on, packageName = ".concat(string));
                        edgeLightingSettingManager.setEnablePackage(context, string);
                        EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, edgeLightingSettingManager.mAllApplication);
                        return;
                    }
                    Slog.d("EdgeLightingSettingManager", "addSilentInstalledPackage : off, packageName = ".concat(string));
                    SharedPreferences sharedPreferences = context.getSharedPreferences("edge_lighting_settings", 0);
                    Set<String> stringSet = sharedPreferences.getStringSet("silent_add_list", new HashSet());
                    stringSet.add(string);
                    EdgeLightingSettingManager.remove(sharedPreferences, "silent_add_list");
                    EdgeLightingSettingManager.putStringSet(sharedPreferences, "silent_add_list", stringSet);
                    Set<String> stringSet2 = sharedPreferences.getStringSet("silent_remove_list", new HashSet());
                    stringSet2.remove(string);
                    EdgeLightingSettingManager.remove(sharedPreferences, "silent_remove_list");
                    EdgeLightingSettingManager.putStringSet(sharedPreferences, "silent_remove_list", stringSet2);
                    return;
                }
                return;
            }
            if (i == 1) {
                String string2 = message.getData().getString("pkg_name");
                if (string2 != null) {
                    EdgeLightingSettingManager edgeLightingSettingManager2 = EdgeLightingSettingManager.getInstance(context);
                    edgeLightingSettingManager2.getClass();
                    SharedPreferences sharedPreferences2 = context.getSharedPreferences("edge_lighting_settings", 0);
                    SharedPreferences.Editor edit = sharedPreferences2.edit();
                    if (context.getPackageName().equals(string2)) {
                        Slog.d("EdgeLightingSettingManager", "removeSilentInstalledPackage : on, packageName = " + string2 + " return own package");
                        return;
                    }
                    edit.putString("update_package_name", string2);
                    HashMap hashMap = edgeLightingSettingManager2.mEnableSet;
                    if (hashMap.containsKey(string2)) {
                        edit.putBoolean("update_package_enable", true);
                    } else {
                        edit.putBoolean("update_package_enable", false);
                    }
                    if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
                        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("removeSilentInstalledPackage : on, packageName = ", string2, " AllApplication = ");
                        m4m.append(edgeLightingSettingManager2.mAllApplication);
                        Slog.d("EdgeLightingSettingManager", m4m.toString());
                        if (hashMap.containsKey(string2)) {
                            edgeLightingSettingManager2.setDisablePackage(context, string2);
                            EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, edgeLightingSettingManager2.mAllApplication);
                        }
                    } else {
                        Slog.d("EdgeLightingSettingManager", "removeSilentInstalledPackage : off, packageName = ".concat(string2));
                        if (hashMap.containsKey(string2)) {
                            Set<String> stringSet3 = sharedPreferences2.getStringSet("silent_remove_list", new HashSet());
                            stringSet3.add(string2);
                            EdgeLightingSettingManager.remove(sharedPreferences2, "silent_remove_list");
                            EdgeLightingSettingManager.putStringSet(sharedPreferences2, "silent_remove_list", stringSet3);
                            Set<String> stringSet4 = sharedPreferences2.getStringSet("silent_add_list", new HashSet());
                            stringSet4.remove(string2);
                            EdgeLightingSettingManager.remove(sharedPreferences2, "silent_add_list");
                            EdgeLightingSettingManager.putStringSet(sharedPreferences2, "silent_add_list", stringSet4);
                        }
                    }
                    edit.apply();
                    return;
                }
                return;
            }
            if (i == 2) {
                try {
                    String string3 = message.getData().getString("pkg_name");
                    if (string3 != null) {
                        EdgeLightingSettingManager.getInstance(context).replaceSilentInstalledPackage(context, string3);
                        return;
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    Slog.e("EdgeLightingReceiver", "PACKAGE_REPLACED error");
                    return;
                }
            }
            if (i != 3) {
                if (i == 4) {
                    EdgeLightingPolicyUpdateService.startActionUpdate(context);
                    return;
                }
                if (i != 5) {
                    return;
                }
                if (!Feature.FEATURE_SUPPORT_AOD) {
                    Settings.System.putIntForUser(context.getContentResolver(), "edge_lighting_show_condition", 1, -2);
                }
                EdgeLightingReceiver.m1533$$Nest$mregisterBroadcastReceiver(EdgeLightingReceiver.this, context, "com.samsung.android.app.edgelighting.PACKAGE_ADDED", new IntentFilter("android.intent.action.PACKAGE_ADDED"), HandlerC12951.class);
                EdgeLightingReceiver.m1533$$Nest$mregisterBroadcastReceiver(EdgeLightingReceiver.this, context, "com.samsung.android.app.edgelighting.PACKAGE_REMOVED", new IntentFilter("android.intent.action.PACKAGE_REMOVED"), HandlerC12951.class);
                EdgeLightingReceiver.m1533$$Nest$mregisterBroadcastReceiver(EdgeLightingReceiver.this, context, "com.samsung.android.app.edgelighting.PACKAGE_REPLACED", new IntentFilter("android.intent.action.PACKAGE_REPLACED"), HandlerC12951.class);
                EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, EdgeLightingSettingManager.getInstance(context).mAllApplication);
                return;
            }
            String string4 = message.getData().getString("pkg_name");
            if (string4 == null || !"com.samsung.android.edgelightingeffectunit".equals(string4)) {
                return;
            }
            try {
                z = !context.getPackageManager().getApplicationInfo(string4, 0).enabled;
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
            if (!z || EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(context.getContentResolver()).startsWith("preload/")) {
                return;
            }
            EdgeLightingStyleManager edgeLightingStyleManager = EdgeLightingStyleManager.getInstance();
            ContentResolver contentResolver = context.getContentResolver();
            String str = EdgeLightingStyleManager.getInstance().getDefalutStyle().mKey;
            edgeLightingStyleManager.getClass();
            Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", str, -2);
        }
    };

    /* renamed from: -$$Nest$mregisterBroadcastReceiver, reason: not valid java name */
    public static void m1533$$Nest$mregisterBroadcastReceiver(EdgeLightingReceiver edgeLightingReceiver, Context context, String str, IntentFilter intentFilter, Class cls) {
        edgeLightingReceiver.getClass();
        SemEventDelegationManager semEventDelegationManager = (SemEventDelegationManager) context.getSystemService("semeventdelegator");
        Intent intent = new Intent(str);
        intent.setClass(context, cls);
        semEventDelegationManager.registerIntentFilter(intentFilter, PendingIntent.getBroadcast(context, 0, intent, 167772160));
    }

    public static String getPackageName(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(action, " ");
        m2m.append(getPackageName(intent));
        Slog.d("EdgeLightingReceiver", m2m.toString());
        if ("com.samsung.android.app.edgelighting.PACKAGE_ADDED".equals(action)) {
            String packageName = getPackageName(intent);
            Bundle bundle = new Bundle();
            bundle.putString("pkg_name", packageName);
            Message obtainMessage = obtainMessage(0, context);
            obtainMessage.setData(bundle);
            sendMessage(obtainMessage);
            return;
        }
        if ("com.samsung.android.app.edgelighting.PACKAGE_REMOVED".equals(action)) {
            String packageName2 = getPackageName(intent);
            Bundle bundle2 = new Bundle();
            bundle2.putString("pkg_name", packageName2);
            Message obtainMessage2 = obtainMessage(1, context);
            obtainMessage2.setData(bundle2);
            sendMessage(obtainMessage2);
            return;
        }
        if ("com.samsung.android.app.edgelighting.PACKAGE_REPLACED".equals(action)) {
            String packageName3 = getPackageName(intent);
            Bundle bundle3 = new Bundle();
            bundle3.putString("pkg_name", packageName3);
            Message obtainMessage3 = obtainMessage(2, context);
            obtainMessage3.setData(bundle3);
            sendMessage(obtainMessage3);
            return;
        }
        if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
            String packageName4 = getPackageName(intent);
            Bundle bundle4 = new Bundle();
            bundle4.putString("pkg_name", packageName4);
            Message obtainMessage4 = obtainMessage(3, context);
            obtainMessage4.setData(bundle4);
            sendMessage(obtainMessage4);
            return;
        }
        if ("sec.app.policy.UPDATE.EdgeLighting".equals(action)) {
            sendMessage(obtainMessage(4, context));
        } else if ("android.intent.action.BOOT_COMPLETED".endsWith(action)) {
            sendMessage(obtainMessage(5, context));
        }
    }
}
