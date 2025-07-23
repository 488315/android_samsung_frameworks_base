package com.android.systemui.controls.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.Prefs;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.panels.SecSelectedComponentRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsBackupRestoreReceiver extends BroadcastReceiver {
    public final ControlsController controlsController;
    public final ControlsFileLoader controlsFileLoader;
    public final ControlsUtil controlsUtil;
    public final EncryptDecryptWrapper encryptDecryptWrapper;
    public final SecControlsController secControlsController;
    public final SecControlsUiController secControlsUiController;
    public final SecureSettings secureSettings;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

    /* JADX WARN: Removed duplicated region for block: B:58:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b0  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r20, android.content.Intent r21) {
        /*
            Method dump skipped, instructions count: 629
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.util.ControlsBackupRestoreReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    public final void restore(Context context, ControlsBackupFormat controlsBackupFormat) {
        ComponentName unflattenFromString;
        Object obj;
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
                SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.secControlsUiController;
                secControlsUiControllerImpl.getClass();
                ComponentName unflattenFromString2 = ComponentName.unflattenFromString(str);
                if (unflattenFromString2 != null) {
                    ArrayList arrayList = (ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) secControlsUiControllerImpl.controlsListingController.get())).getCurrentServices();
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            obj = null;
                            break;
                        }
                        obj = arrayList.get(i);
                        i++;
                        if (unflattenFromString2.equals(((ControlsServiceInfo) obj).componentName)) {
                            break;
                        }
                    }
                    ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
                    ComponentName componentName = controlsServiceInfo != null ? controlsServiceInfo.panelActivity : null;
                    SecSelectedComponentRepository secSelectedComponentRepository = secControlsUiControllerImpl.secSelectedComponentRepository;
                    if (componentName != null) {
                        ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), unflattenFromString2)));
                    } else {
                        ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.ComponentItem(controlsServiceInfo != null ? controlsServiceInfo.loadLabel() : "", new ComponentInfo(unflattenFromString2, new ArrayList()))));
                    }
                }
            }
        }
        List list = controlsBackupFormat.controls.structures;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list) {
            if (isPackageInstalledAndEnabled(context.getPackageManager(), ((StructureInfo) obj2).componentName.getPackageName())) {
                arrayList2.add(obj2);
            }
        }
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.secControlsController;
        if (controlsControllerImpl.confirmAvailability()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj3 = arrayList2.get(i2);
                i2++;
                ComponentName componentName2 = ((StructureInfo) obj3).componentName;
                Object obj4 = linkedHashMap.get(componentName2);
                if (obj4 == null) {
                    obj4 = new ArrayList();
                    linkedHashMap.put(componentName2, obj4);
                }
                ((List) obj4).add(obj3);
            }
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                controlsControllerImpl.replaceFavoritesForComponent(new ComponentInfo((ComponentName) entry.getKey(), (List) entry.getValue()), false);
            }
            Log.d("ControlsControllerImpl", "restore backupStructures = " + arrayList2);
            Favorites.INSTANCE.getClass();
            Log.d("ControlsControllerImpl", "restore result = " + Favorites.getAllStructures());
        }
    }
}
