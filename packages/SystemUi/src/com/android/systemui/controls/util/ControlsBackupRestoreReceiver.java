package com.android.systemui.controls.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.ui.CustomControlsUiController;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.util.settings.SecureSettings;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsBackupRestoreReceiver extends BroadcastReceiver {
    public final ControlsController controlsController;
    public final ControlsFileLoader controlsFileLoader;
    public final ControlsLogger controlsLogger;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final CustomControlsController customControlsController;
    public final CustomControlsUiController customControlsUiController;
    public final EncryptDecryptWrapper encryptDecryptWrapper;
    public final SecureSettings secureSettings;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public ControlsBackupRestoreReceiver(ControlsController controlsController, CustomControlsController customControlsController, CustomControlsUiController customControlsUiController, ControlsFileLoader controlsFileLoader, EncryptDecryptWrapper encryptDecryptWrapper, SecureSettings secureSettings, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, ControlsLogger controlsLogger) {
        this.controlsController = controlsController;
        this.customControlsController = customControlsController;
        this.customControlsUiController = customControlsUiController;
        this.controlsFileLoader = controlsFileLoader;
        this.encryptDecryptWrapper = encryptDecryptWrapper;
        this.secureSettings = secureSettings;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.controlsLogger = controlsLogger;
    }

    public final boolean isPackageInstalledAndEnabled(PackageManager packageManager, String str) {
        try {
            packageManager.getPackageInfo(str, 1);
            boolean z = packageManager.getApplicationInfo(str, 0).enabled;
            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "Already Installed " + str + ", enabled=" + z);
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "Not Installed " + str);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x026e  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        boolean z;
        File file;
        File file2;
        boolean z2;
        boolean z3;
        ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult;
        ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode;
        if (context == null || intent == null) {
            return;
        }
        ((ControlsRuneWrapperImpl) this.controlsRuneWrapper).getClass();
        if (BasicRune.CONTROLS_MANAGE_BACKUP_RESOTRE) {
            String stringExtra = intent.getStringExtra("SAVE_PATH");
            String str = stringExtra == null ? "" : stringExtra;
            if (intent.getStringArrayListExtra("SAVE_PATH_URLS") == null) {
                new ArrayList();
            }
            int intExtra = intent.getIntExtra("ACTION", 0);
            String stringExtra2 = intent.getStringExtra("SESSION_KEY");
            String str2 = stringExtra2 == null ? "" : stringExtra2;
            String stringExtra3 = intent.getStringExtra("SOURCE");
            String str3 = stringExtra3 == null ? "" : stringExtra3;
            String stringExtra4 = intent.getStringExtra("EXPORT_SESSION_TIME");
            int intExtra2 = intent.getIntExtra("SECURITY_LEVEL", 0);
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("EXTRA_BACKUP_ITEM");
            if (stringArrayListExtra == null) {
                stringArrayListExtra = new ArrayList<>();
            }
            ArrayList<String> arrayList = stringArrayListExtra;
            String action = intent.getAction();
            ControlsBackUpRestore$BNRAction.Companion.getClass();
            for (ControlsBackUpRestore$BNRAction controlsBackUpRestore$BNRAction : ControlsBackUpRestore$BNRAction.values()) {
                if (controlsBackUpRestore$BNRAction.getValue() == intExtra) {
                    ControlsBackUpRestore$BNRSecurityLevel.Companion.getClass();
                    for (ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel : ControlsBackUpRestore$BNRSecurityLevel.values()) {
                        if (controlsBackUpRestore$BNRSecurityLevel.getValue() == intExtra2) {
                            ControlsBackUpRestore$BNRRequest controlsBackUpRestore$BNRRequest = new ControlsBackUpRestore$BNRRequest(action, str, controlsBackUpRestore$BNRAction, str2, str3, controlsBackUpRestore$BNRSecurityLevel, arrayList, stringExtra4, null);
                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "onReceive request=" + controlsBackUpRestore$BNRRequest + "}");
                            String str4 = controlsBackUpRestore$BNRRequest.intentAction;
                            boolean areEqual = Intrinsics.areEqual(str4, "com.samsung.android.intent.action.REQUEST_BACKUP_DEVICE_CONTROLS");
                            ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel2 = controlsBackUpRestore$BNRRequest.securityLevel;
                            String str5 = controlsBackUpRestore$BNRRequest.sessionKey;
                            String str6 = controlsBackUpRestore$BNRRequest.savePath;
                            if (!areEqual) {
                                if (Intrinsics.areEqual(str4, "com.samsung.android.intent.action.REQUEST_RESTORE_DEVICE_CONTROLS")) {
                                    ControlsLogger controlsLogger = this.controlsLogger;
                                    Intrinsics.checkNotNull(str6);
                                    ControlsLogger.printLog$default(controlsLogger, "ControlsBackupRestoreManager", "start restore ".concat(str6));
                                    ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult2 = ControlsBackUpRestore$BNRResult.SUCCESS;
                                    ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode2 = ControlsBackUpRestore$BNRErrCode.SUCCESS;
                                    try {
                                        file = new File(str6 + "/encrypt_controls.xml");
                                        file2 = new File(str6 + "/decrypt_controls.xml");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (this.encryptDecryptWrapper.decryptFile(file, file2, str5, controlsBackUpRestore$BNRSecurityLevel2)) {
                                        ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "decryptFile pass");
                                        ControlsBackupFormat loadResultXml = this.controlsFileLoader.loadResultXml(file2);
                                        if (loadResultXml != null) {
                                            restore(context, loadResultXml);
                                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "loadResultXml pass");
                                            z = true;
                                            if (!z) {
                                                controlsBackUpRestore$BNRResult2 = ControlsBackUpRestore$BNRResult.FAIL;
                                                controlsBackUpRestore$BNRErrCode2 = ControlsBackUpRestore$BNRErrCode.INVALID_DATA;
                                            }
                                            sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_RESTORE_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult2, controlsBackUpRestore$BNRErrCode2, 0, controlsBackUpRestore$BNRRequest.source, null, null, 96, null));
                                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "end restore");
                                            return;
                                        }
                                    }
                                    z = false;
                                    if (!z) {
                                    }
                                    sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_RESTORE_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult2, controlsBackUpRestore$BNRErrCode2, 0, controlsBackUpRestore$BNRRequest.source, null, null, 96, null));
                                    ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "end restore");
                                    return;
                                }
                                return;
                            }
                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "start backup");
                            ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult3 = ControlsBackUpRestore$BNRResult.SUCCESS;
                            ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode3 = ControlsBackUpRestore$BNRErrCode.SUCCESS;
                            boolean z4 = this.secureSettings.getIntForUser(0, -2, "lockscreen_show_controls") != 0;
                            boolean z5 = this.secureSettings.getIntForUser(0, -2, "lockscreen_allow_trivial_controls") != 0;
                            this.controlsUtil.getClass();
                            ControlsBackupFormat controlsBackupFormat = new ControlsBackupFormat(new ControlsBackupSetting(z4, z5, Prefs.getBoolean(context, "ControlsOOBEManageAppsCompleted", false), ((CustomControlsUiControllerImpl) this.customControlsUiController).sharedPreferences.getString("controls_custom_component", null)), new ControlsBackupControl(((ControlsControllerImpl) this.controlsController).getFavorites()));
                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "backupFormat=" + controlsBackupFormat);
                            try {
                                File generateResultXML = this.controlsFileLoader.generateResultXML(new File(str6 + "/controls.xml"), controlsBackupFormat);
                                if (generateResultXML != null) {
                                    ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "generateResultXML pass");
                                    z3 = this.encryptDecryptWrapper.encryptFile(generateResultXML, new File(str6 + "/encrypt_controls.xml"), str5, controlsBackUpRestore$BNRSecurityLevel2);
                                    try {
                                        if (generateResultXML.exists()) {
                                            generateResultXML.delete();
                                        }
                                        ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "encryptFile " + z3);
                                    } catch (Exception e2) {
                                        e = e2;
                                        z2 = z3;
                                        e.printStackTrace();
                                        z3 = z2;
                                        ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "backup success=" + z3);
                                        if (z3) {
                                        }
                                        sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_BACKUP_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult, controlsBackUpRestore$BNRErrCode, 0, controlsBackUpRestore$BNRRequest.source, null, controlsBackUpRestore$BNRRequest.exportSessionTime));
                                        ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "end backup");
                                        return;
                                    }
                                } else {
                                    z3 = false;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                z2 = false;
                            }
                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "backup success=" + z3);
                            if (z3) {
                                controlsBackUpRestore$BNRResult = ControlsBackUpRestore$BNRResult.FAIL;
                                controlsBackUpRestore$BNRErrCode = ControlsBackUpRestore$BNRErrCode.INVALID_DATA;
                            } else {
                                controlsBackUpRestore$BNRResult = controlsBackUpRestore$BNRResult3;
                                controlsBackUpRestore$BNRErrCode = controlsBackUpRestore$BNRErrCode3;
                            }
                            sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_BACKUP_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult, controlsBackUpRestore$BNRErrCode, 0, controlsBackUpRestore$BNRRequest.source, null, controlsBackUpRestore$BNRRequest.exportSessionTime));
                            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "end backup");
                            return;
                        }
                    }
                    throw new NoSuchElementException("Array contains no element matching the predicate.");
                }
            }
            throw new NoSuchElementException("Array contains no element matching the predicate.");
        }
    }

    public final void restore(Context context, ControlsBackupFormat controlsBackupFormat) {
        ComponentName unflattenFromString;
        ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "restore=" + controlsBackupFormat);
        SecureSettings secureSettings = this.secureSettings;
        ControlsBackupSetting controlsBackupSetting = controlsBackupFormat.setting;
        secureSettings.putIntForUser(controlsBackupSetting.showDevice ? 1 : 0, -2, "lockscreen_show_controls");
        this.secureSettings.putIntForUser(controlsBackupSetting.controlDevice ? 1 : 0, -2, "lockscreen_allow_trivial_controls");
        if (controlsBackupSetting.isOOBECompleted) {
            this.controlsUtil.getClass();
            Prefs.putBoolean(context, "ControlsOOBEManageAppsCompleted", true);
        }
        String str = controlsBackupSetting.selectedComponent;
        if (str != null && (unflattenFromString = ComponentName.unflattenFromString(str)) != null) {
            ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "restore " + unflattenFromString + ", packageName = " + unflattenFromString.getPackageName());
            if (isPackageInstalledAndEnabled(context.getPackageManager(), unflattenFromString.getPackageName())) {
                ((CustomControlsUiControllerImpl) this.customControlsUiController).sharedPreferences.edit().putString("controls_custom_component", str).commit();
            }
        }
        List list = controlsBackupFormat.controls.structures;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (isPackageInstalledAndEnabled(context.getPackageManager(), ((StructureInfo) obj).componentName.getPackageName())) {
                arrayList.add(obj);
            }
        }
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.customControlsController;
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
            Log.d("ControlsControllerImpl", "restore backupStructures=" + arrayList);
            Favorites.INSTANCE.getClass();
            Log.d("ControlsControllerImpl", "restore result=" + Favorites.getAllStructures());
        }
    }

    public final void sendResponse(Context context, ControlsBackUpRestore$BNRResponse controlsBackUpRestore$BNRResponse) {
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
        ControlsLogger.printLog$default(this.controlsLogger, "ControlsBackupRestoreManager", "send response");
    }
}
