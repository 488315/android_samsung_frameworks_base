package com.android.systemui.controls.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ControlsBackupRestoreReceiver extends BroadcastReceiver {
    public final ControlsController controlsController;
    public final ControlsFileLoader controlsFileLoader;
    public final ControlsUtil controlsUtil;
    public final EncryptDecryptWrapper encryptDecryptWrapper;
    public final SecControlsController secControlsController;
    public final SecControlsUiController secControlsUiController;
    public final SecureSettings secureSettings;

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

    public static boolean isPackageInstalledAndEnabled(PackageManager packageManager, String str) throws PackageManager.NameNotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:66:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0239  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        File file;
        File file2;
        boolean zEncryptFile;
        ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult;
        ComponentName componentName;
        if (context == null || intent == null) {
            return;
        }
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
        ControlsBackUpRestore$BNRAction[] controlsBackUpRestore$BNRActionArrValues = ControlsBackUpRestore$BNRAction.values();
        int length = controlsBackUpRestore$BNRActionArrValues.length;
        int i = 0;
        while (i < length) {
            int i2 = length;
            ControlsBackUpRestore$BNRAction controlsBackUpRestore$BNRAction = controlsBackUpRestore$BNRActionArrValues[i];
            if (controlsBackUpRestore$BNRAction.getValue() == intExtra) {
                ControlsBackUpRestore$BNRSecurityLevel.Companion.getClass();
                ControlsBackUpRestore$BNRSecurityLevel[] controlsBackUpRestore$BNRSecurityLevelArrValues = ControlsBackUpRestore$BNRSecurityLevel.values();
                int length2 = controlsBackUpRestore$BNRSecurityLevelArrValues.length;
                int i3 = 0;
                while (i3 < length2) {
                    int i4 = i3;
                    ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel = controlsBackUpRestore$BNRSecurityLevelArrValues[i4];
                    ControlsBackUpRestore$BNRSecurityLevel[] controlsBackUpRestore$BNRSecurityLevelArr = controlsBackUpRestore$BNRSecurityLevelArrValues;
                    if (controlsBackUpRestore$BNRSecurityLevel.getValue() == intExtra2) {
                        ControlsBackUpRestore$BNRRequest controlsBackUpRestore$BNRRequest = new ControlsBackUpRestore$BNRRequest(action, str, controlsBackUpRestore$BNRAction, str2, str3, controlsBackUpRestore$BNRSecurityLevel, arrayList, stringExtra4, null);
                        Log.d("ControlsBackupRestoreManager", "onReceive request=" + controlsBackUpRestore$BNRRequest + "}");
                        String str4 = controlsBackUpRestore$BNRRequest.intentAction;
                        boolean zAreEqual = Intrinsics.areEqual(str4, "com.samsung.android.intent.action.REQUEST_BACKUP_DEVICE_CONTROLS");
                        ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel2 = controlsBackUpRestore$BNRRequest.securityLevel;
                        String str5 = controlsBackUpRestore$BNRRequest.sessionKey;
                        String str6 = controlsBackUpRestore$BNRRequest.savePath;
                        if (!zAreEqual) {
                            if (Intrinsics.areEqual(str4, "com.samsung.android.intent.action.REQUEST_RESTORE_DEVICE_CONTROLS")) {
                                str6.getClass();
                                Log.d("ControlsBackupRestoreManager", "start restore path = ".concat(str6));
                                ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult2 = ControlsBackUpRestore$BNRResult.SUCCESS;
                                ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode = ControlsBackUpRestore$BNRErrCode.SUCCESS;
                                try {
                                    file = new File(str6 + "/encrypt_controls.xml");
                                    file2 = new File(str6 + "/decrypt_controls.xml");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (this.encryptDecryptWrapper.decryptFile(file, file2, str5, controlsBackUpRestore$BNRSecurityLevel2)) {
                                    Log.d("ControlsBackupRestoreManager", "decryptFile pass");
                                    this.controlsFileLoader.getClass();
                                    ControlsBackupFormat controlsBackupFormatLoadResultXml = ControlsFileLoader.loadResultXml(file2);
                                    if (controlsBackupFormatLoadResultXml != null) {
                                        restore(context, controlsBackupFormatLoadResultXml);
                                        Log.d("ControlsBackupRestoreManager", "loadResultXml pass");
                                    } else {
                                        controlsBackUpRestore$BNRResult2 = ControlsBackUpRestore$BNRResult.FAIL;
                                        controlsBackUpRestore$BNRErrCode = ControlsBackUpRestore$BNRErrCode.INVALID_DATA;
                                    }
                                }
                                sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_RESTORE_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult2, controlsBackUpRestore$BNRErrCode, 0, controlsBackUpRestore$BNRRequest.source, null, null, 96, null));
                                Log.d("ControlsBackupRestoreManager", "end restore");
                                return;
                            }
                            return;
                        }
                        Log.d("ControlsBackupRestoreManager", "start backup");
                        ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult3 = ControlsBackUpRestore$BNRResult.SUCCESS;
                        ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode2 = ControlsBackUpRestore$BNRErrCode.SUCCESS;
                        boolean z = this.secureSettings.getIntForUser("lockscreen_show_controls", 0, -2) != 0;
                        boolean z2 = this.secureSettings.getIntForUser("lockscreen_allow_trivial_controls", 0, -2) != 0;
                        this.controlsUtil.getClass();
                        boolean z3 = Prefs.getBoolean(context, "ControlsOOBEManageAppsCompleted", false);
                        SelectedComponentRepository.SelectedComponent selectedComponent = ((SecSelectedComponentRepositoryImpl) ((SecControlsUiControllerImpl) this.secControlsUiController).secSelectedComponentRepository).getSelectedComponent(UserHandle.CURRENT);
                        ControlsBackupSetting controlsBackupSetting = new ControlsBackupSetting(z, z2, z3, (selectedComponent == null || (componentName = selectedComponent.componentName) == null) ? null : componentName.flattenToShortString());
                        ((ControlsControllerImpl) this.controlsController).getClass();
                        Favorites.INSTANCE.getClass();
                        ControlsBackupFormat controlsBackupFormat = new ControlsBackupFormat(controlsBackupSetting, new ControlsBackupControl(Favorites.getAllStructures()));
                        Log.d("ControlsBackupRestoreManager", "backupFormat = " + controlsBackupFormat);
                        try {
                            ControlsFileLoader controlsFileLoader = this.controlsFileLoader;
                            File file3 = new File(str6 + "/controls.xml");
                            controlsFileLoader.getClass();
                            File fileGenerateResultXML = ControlsFileLoader.generateResultXML(file3, controlsBackupFormat);
                            if (fileGenerateResultXML != null) {
                                Log.d("ControlsBackupRestoreManager", "generateResultXML pass");
                                zEncryptFile = this.encryptDecryptWrapper.encryptFile(fileGenerateResultXML, new File(str6 + "/encrypt_controls.xml"), str5, controlsBackUpRestore$BNRSecurityLevel2);
                                try {
                                    if (fileGenerateResultXML.exists()) {
                                        fileGenerateResultXML.delete();
                                    }
                                    Log.d("ControlsBackupRestoreManager", "encryptFile success = " + zEncryptFile);
                                } catch (Exception e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    EmergencyButtonController$$ExternalSyntheticOutline0.m("backup success = ", "ControlsBackupRestoreManager", zEncryptFile);
                                    if (zEncryptFile) {
                                    }
                                    sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_BACKUP_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult, controlsBackUpRestore$BNRErrCode2, 0, controlsBackUpRestore$BNRRequest.source, null, controlsBackUpRestore$BNRRequest.exportSessionTime));
                                    Log.d("ControlsBackupRestoreManager", "end backup");
                                    return;
                                }
                            } else {
                                zEncryptFile = false;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            zEncryptFile = false;
                        }
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("backup success = ", "ControlsBackupRestoreManager", zEncryptFile);
                        if (zEncryptFile) {
                            ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult4 = ControlsBackUpRestore$BNRResult.FAIL;
                            controlsBackUpRestore$BNRErrCode2 = ControlsBackUpRestore$BNRErrCode.INVALID_DATA;
                            controlsBackUpRestore$BNRResult = controlsBackUpRestore$BNRResult4;
                        } else {
                            controlsBackUpRestore$BNRResult = controlsBackUpRestore$BNRResult3;
                        }
                        sendResponse(context, new ControlsBackUpRestore$BNRResponse("com.samsung.android.intent.action.RESPONSE_BACKUP_DEVICE_CONTROLS", controlsBackUpRestore$BNRResult, controlsBackUpRestore$BNRErrCode2, 0, controlsBackUpRestore$BNRRequest.source, null, controlsBackUpRestore$BNRRequest.exportSessionTime));
                        Log.d("ControlsBackupRestoreManager", "end backup");
                        return;
                    }
                    i3 = i4 + 1;
                    controlsBackUpRestore$BNRSecurityLevelArrValues = controlsBackUpRestore$BNRSecurityLevelArr;
                }
                throw new NoSuchElementException("Array contains no element matching the predicate.");
            }
            i++;
            length = i2;
        }
        throw new NoSuchElementException("Array contains no element matching the predicate.");
    }

    public final void restore(Context context, ControlsBackupFormat controlsBackupFormat) {
        ComponentName componentNameUnflattenFromString;
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
        if (str != null && (componentNameUnflattenFromString = ComponentName.unflattenFromString(str)) != null) {
            Log.d("ControlsBackupRestoreManager", "restore cn = " + componentNameUnflattenFromString + ", packageName = " + componentNameUnflattenFromString.getPackageName());
            if (isPackageInstalledAndEnabled(context.getPackageManager(), componentNameUnflattenFromString.getPackageName())) {
                SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.secControlsUiController;
                secControlsUiControllerImpl.getClass();
                ComponentName componentNameUnflattenFromString2 = ComponentName.unflattenFromString(str);
                if (componentNameUnflattenFromString2 != null) {
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
                        if (componentNameUnflattenFromString2.equals(((ControlsServiceInfo) obj).componentName)) {
                            break;
                        }
                    }
                    ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
                    ComponentName componentName = controlsServiceInfo != null ? controlsServiceInfo.panelActivity : null;
                    SecSelectedComponentRepository secSelectedComponentRepository = secControlsUiControllerImpl.secSelectedComponentRepository;
                    if (componentName != null) {
                        ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), componentNameUnflattenFromString2)));
                    } else {
                        ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.ComponentItem(controlsServiceInfo != null ? controlsServiceInfo.loadLabel() : "", new ComponentInfo(componentNameUnflattenFromString2, new ArrayList()))));
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
                Object arrayList3 = linkedHashMap.get(componentName2);
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList();
                    linkedHashMap.put(componentName2, arrayList3);
                }
                ((List) arrayList3).add(obj3);
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
