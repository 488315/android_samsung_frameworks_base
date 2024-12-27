package com.android.systemui.controls.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.Prefs;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ControlsBackupRestoreReceiver extends BroadcastReceiver {
    public final ControlsController controlsController;
    public final ControlsFileLoader controlsFileLoader;
    public final ControlsUtil controlsUtil;
    public final EncryptDecryptWrapper encryptDecryptWrapper;
    public final SecControlsController secControlsController;
    public final SecControlsUiController secControlsUiController;
    public final SecureSettings secureSettings;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ControlsBackupRestoreReceiver(ControlsController controlsController, SecControlsController secControlsController, SecControlsUiController secControlsUiController, ControlsFileLoader controlsFileLoader, EncryptDecryptWrapper encryptDecryptWrapper, SecureSettings secureSettings, ControlsUtil controlsUtil) {
        this.controlsController = controlsController;
        this.secControlsController = secControlsController;
        this.secControlsUiController = secControlsUiController;
        this.controlsFileLoader = controlsFileLoader;
        this.encryptDecryptWrapper = encryptDecryptWrapper;
        this.secureSettings = secureSettings;
        this.controlsUtil = controlsUtil;
    }

    public static boolean isPackageInstalledAndEnabled(PackageManager packageManager, String str) {
        try {
            packageManager.getPackageInfo(str, 1);
            boolean z = packageManager.getApplicationInfo(str, 0).enabled;
            Log.d("ControlsBackupRestoreManager", "Already Installed " + str + ", enabled = " + z);
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Not Installed ", str, "ControlsBackupRestoreManager");
            return false;
        }
    }

    public static void sendResponse(Context context, ControlsBackUpRestore$BNRResponse controlsBackUpRestore$BNRResponse) {
        Intent intent = new Intent();
        intent.setAction(controlsBackUpRestore$BNRResponse.intentAction);
        intent.putExtra("RESULT", controlsBackUpRestore$BNRResponse.result.getValue());
        intent.putExtra("ERR_CODE", controlsBackUpRestore$BNRResponse.errCode.getValue());
        intent.putExtra("REQ_SIZE", controlsBackUpRestore$BNRResponse.reqSize);
        intent.putExtra("SOURCE", controlsBackUpRestore$BNRResponse.source);
        String str = controlsBackUpRestore$BNRResponse.exportSessionTime;
        if (str != null) {
            intent.putExtra("EXPORT_SESSION_TIME", str);
        }
        intent.putExtra("EXTRA_ERR_CODE", controlsBackUpRestore$BNRResponse.extraErrCode);
        context.sendBroadcast(intent, "com.wssnps.permission.COM_WSSNPS");
        Log.d("ControlsBackupRestoreManager", "send response");
    }

    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r25, android.content.Intent r26) {
        /*
            Method dump skipped, instructions count: 632
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.util.ControlsBackupRestoreReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    public final void restore(Context context, ControlsBackupFormat controlsBackupFormat) {
        ComponentName unflattenFromString;
        Log.d("ControlsBackupRestoreManager", "restore=" + controlsBackupFormat);
        SecureSettings secureSettings = this.secureSettings;
        ControlsBackupSetting controlsBackupSetting = controlsBackupFormat.setting;
        secureSettings.putIntForUser("lockscreen_show_controls", controlsBackupSetting.showDevice ? 1 : 0, -2);
        this.secureSettings.putIntForUser("lockscreen_allow_trivial_controls", controlsBackupSetting.controlDevice ? 1 : 0, -2);
        if (controlsBackupSetting.isOOBECompleted) {
            this.controlsUtil.getClass();
            Prefs.putBoolean(context, "ControlsOOBEManageAppsCompleted", true);
        }
        String str = controlsBackupSetting.selectedComponent;
        if (str != null && (unflattenFromString = ComponentName.unflattenFromString(str)) != null) {
            Log.d("ControlsBackupRestoreManager", "restore cn = " + unflattenFromString + ", packageName = " + unflattenFromString.getPackageName());
            if (isPackageInstalledAndEnabled(context.getPackageManager(), unflattenFromString.getPackageName())) {
                ((SecControlsUiControllerImpl) this.secControlsUiController).sharedPreferences.edit().putString("controls_custom_component", str).commit();
            }
        }
        List list = controlsBackupFormat.controls.structures;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (isPackageInstalledAndEnabled(context.getPackageManager(), ((StructureInfo) obj).componentName.getPackageName())) {
                arrayList.add(obj);
            }
        }
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.secControlsController;
        if (controlsControllerImpl.confirmAvailability()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                ComponentName componentName = ((StructureInfo) next).componentName;
                Object obj2 = linkedHashMap.get(componentName);
                if (obj2 == null) {
                    obj2 = new ArrayList();
                    linkedHashMap.put(componentName, obj2);
                }
                ((List) obj2).add(next);
            }
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                controlsControllerImpl.replaceFavoritesForComponent(new ComponentInfo((ComponentName) entry.getKey(), (List) entry.getValue()), false);
            }
            Log.d("ControlsControllerImpl", "restore backupStructures = " + arrayList);
            Favorites.INSTANCE.getClass();
            Log.d("ControlsControllerImpl", "restore result = " + Favorites.getAllStructures());
        }
    }
}
