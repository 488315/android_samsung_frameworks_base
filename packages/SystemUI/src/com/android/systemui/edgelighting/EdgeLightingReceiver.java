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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Slog;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingManager;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.policy.EdgeLightingPolicyUpdateService;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.popup.util.PopupUIUtil;
import com.samsung.android.sepunion.SemEventDelegationManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class EdgeLightingReceiver extends BroadcastReceiver {
    public final AnonymousClass1 mHandler = new Handler(Looper.myLooper()) { // from class: com.android.systemui.edgelighting.EdgeLightingReceiver.1
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
                    SharedPreferences.Editor editorEdit = sharedPreferences2.edit();
                    if (context.getPackageName().equals(string2)) {
                        Slog.d("EdgeLightingSettingManager", "removeSilentInstalledPackage : on, packageName = " + string2 + " return own package");
                        return;
                    }
                    editorEdit.putString("update_package_name", string2);
                    if (edgeLightingSettingManager2.mEnableSet.containsKey(string2)) {
                        editorEdit.putBoolean("update_package_enable", true);
                    } else {
                        editorEdit.putBoolean("update_package_enable", false);
                    }
                    if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
                        StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("removeSilentInstalledPackage : on, packageName = ", string2, " AllApplication = ");
                        sbM.append(edgeLightingSettingManager2.mAllApplication);
                        Slog.d("EdgeLightingSettingManager", sbM.toString());
                        if (edgeLightingSettingManager2.mEnableSet.containsKey(string2)) {
                            edgeLightingSettingManager2.setDisablePackage(context, string2);
                            EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, edgeLightingSettingManager2.mAllApplication);
                        }
                    } else {
                        Slog.d("EdgeLightingSettingManager", "removeSilentInstalledPackage : off, packageName = ".concat(string2));
                        if (edgeLightingSettingManager2.mEnableSet.containsKey(string2)) {
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
                    editorEdit.apply();
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
                IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                Class<?> cls = getClass();
                EdgeLightingReceiver edgeLightingReceiver = EdgeLightingReceiver.this;
                EdgeLightingReceiver.m2570$$Nest$mregisterBroadcastReceiver(edgeLightingReceiver, context, "com.samsung.android.app.edgelighting.PACKAGE_ADDED", intentFilter, cls);
                EdgeLightingReceiver.m2570$$Nest$mregisterBroadcastReceiver(edgeLightingReceiver, context, "com.samsung.android.app.edgelighting.PACKAGE_REMOVED", new IntentFilter("android.intent.action.PACKAGE_REMOVED"), getClass());
                EdgeLightingReceiver.m2570$$Nest$mregisterBroadcastReceiver(edgeLightingReceiver, context, "com.samsung.android.app.edgelighting.PACKAGE_REPLACED", new IntentFilter("android.intent.action.PACKAGE_REPLACED"), getClass());
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
            EdgeLightingStyleManager.setEdgeLightingStyleType(contentResolver, str);
        }
    };

    /* renamed from: -$$Nest$mregisterBroadcastReceiver, reason: not valid java name */
    public static void m2570$$Nest$mregisterBroadcastReceiver(EdgeLightingReceiver edgeLightingReceiver, Context context, String str, IntentFilter intentFilter, Class cls) {
        edgeLightingReceiver.getClass();
        SemEventDelegationManager semEventDelegationManager = (SemEventDelegationManager) context.getSystemService("semeventdelegator");
        Intent intent = new Intent(str);
        intent.setClass(context, cls);
        semEventDelegationManager.registerPendingIntent(intentFilter, PendingIntent.getBroadcast(context, 0, intent, 167772160), 0, (List) null);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(action, " ");
        Uri data = intent.getData();
        sbM.append(data != null ? data.getSchemeSpecificPart() : null);
        Slog.d("EdgeLightingReceiver", sbM.toString());
        if ("com.samsung.android.app.edgelighting.PACKAGE_ADDED".equals(action)) {
            Uri data2 = intent.getData();
            Bundle bundleM = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("pkg_name", data2 != null ? data2.getSchemeSpecificPart() : null);
            Message messageObtainMessage = obtainMessage(0, context);
            messageObtainMessage.setData(bundleM);
            sendMessage(messageObtainMessage);
            return;
        }
        if ("com.samsung.android.app.edgelighting.PACKAGE_REMOVED".equals(action)) {
            Uri data3 = intent.getData();
            Bundle bundleM2 = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("pkg_name", data3 != null ? data3.getSchemeSpecificPart() : null);
            Message messageObtainMessage2 = obtainMessage(1, context);
            messageObtainMessage2.setData(bundleM2);
            sendMessage(messageObtainMessage2);
            return;
        }
        if ("com.samsung.android.app.edgelighting.PACKAGE_REPLACED".equals(action)) {
            Uri data4 = intent.getData();
            Bundle bundleM3 = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("pkg_name", data4 != null ? data4.getSchemeSpecificPart() : null);
            Message messageObtainMessage3 = obtainMessage(2, context);
            messageObtainMessage3.setData(bundleM3);
            sendMessage(messageObtainMessage3);
            return;
        }
        if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
            Uri data5 = intent.getData();
            Bundle bundleM4 = KeyguardSecPatternView$$ExternalSyntheticOutline0.m("pkg_name", data5 != null ? data5.getSchemeSpecificPart() : null);
            Message messageObtainMessage4 = obtainMessage(3, context);
            messageObtainMessage4.setData(bundleM4);
            sendMessage(messageObtainMessage4);
            return;
        }
        if ("sec.app.policy.UPDATE.EdgeLighting".equals(action)) {
            sendMessage(obtainMessage(4, context));
        } else if (PopupUIUtil.ACTION_BOOT_COMPLETED.endsWith(action)) {
            sendMessage(obtainMessage(5, context));
        }
    }
}
