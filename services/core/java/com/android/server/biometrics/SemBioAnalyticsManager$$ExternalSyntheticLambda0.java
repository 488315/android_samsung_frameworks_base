package com.android.server.biometrics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.LinkedHashMap;

public final /* synthetic */ class SemBioAnalyticsManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemBioAnalyticsManager f$0;
    public final /* synthetic */ SemBioAnalyticsManager.EventData f$1;

    public /* synthetic */ SemBioAnalyticsManager$$ExternalSyntheticLambda0(
            SemBioAnalyticsManager semBioAnalyticsManager,
            SemBioAnalyticsManager.EventData eventData,
            int i) {
        this.$r8$classId = i;
        this.f$0 = semBioAnalyticsManager;
        this.f$1 = eventData;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBioAnalyticsManager semBioAnalyticsManager = this.f$0;
                SemBioAnalyticsManager.EventData eventData = this.f$1;
                if (semBioAnalyticsManager.mContext == null) {
                    semBioAnalyticsManager.mPendingRequestBeforeBootComplete.add(eventData);
                    break;
                } else if (eventData != null) {
                    if (SemBioAnalyticsManager.DEBUG) {
                        Log.d("BiometricService.AM", "SA: " + eventData.toString());
                    }
                    SemBioAnalyticsManager.DQAManager dQAManager = semBioAnalyticsManager.mDqaMgr;
                    if (dQAManager != null && (eventData.mType & 2) != 0) {
                        if (dQAManager.mFaceBigDataNameMap.containsKey(eventData.mFeature)) {
                            if (dQAManager.isUsingPackageNameAsExtra(eventData.mFeature)) {
                                dQAManager.mFaceBigDataNameMap.put(
                                        eventData.mFeature,
                                        dQAManager.updateAppCountNum(
                                                (String)
                                                        ((LinkedHashMap)
                                                                        dQAManager
                                                                                .mFaceBigDataNameMap)
                                                                .get(eventData.mFeature),
                                                eventData.mExtra));
                            } else {
                                String str =
                                        (String)
                                                ((LinkedHashMap) dQAManager.mFaceBigDataNameMap)
                                                        .get(eventData.mFeature);
                                dQAManager.mFaceBigDataNameMap.put(
                                        eventData.mFeature,
                                        String.valueOf(
                                                (str == "" ? 0 : Integer.parseInt(str)) + 1));
                            }
                            long currentTimeMillis = System.currentTimeMillis();
                            long j = dQAManager.mPreviousSavedTime;
                            if (j == 0
                                    || currentTimeMillis - j
                                            > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                                dQAManager.mPreviousSavedTime = currentTimeMillis;
                                SemBioAnalyticsManager.DQAManager.writeDqaDataToFile(
                                        "settings_face_ext_bigdata.xml",
                                        SemBioAnalyticsManager.DQAManager.getDqaDataFormatToSave(
                                                dQAManager.mFaceBigDataNameMap));
                            }
                        } else {
                            dQAManager.sendFaceBigData(eventData.mFeature, eventData.mExtra);
                        }
                    }
                    if (SemBiometricFeature.FEATURE_LOGGING_MODE && (eventData.mType & 1) != 0) {
                        Intent intent = new Intent();
                        Bundle m142m =
                                AccountManagerService$$ExternalSyntheticOutline0.m142m(
                                        "tracking_id", "4G3-399-5448102");
                        m142m.putString(
                                LauncherConfigurationInternal.KEY_FEATURE_INT, eventData.mFeature);
                        String str2 = eventData.mExtra;
                        if (str2 != null) {
                            m142m.putString("extra", str2);
                        }
                        String str3 = eventData.mExtra2;
                        if (str3 != null) {
                            m142m.putString("extra2", str3);
                        }
                        m142m.putString("pkg_name", "com.android.server.biometrics.sensors.face");
                        m142m.putString("type", "ev");
                        m142m.putString("is_summary", "true");
                        intent.setAction(
                                "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
                        intent.putExtras(m142m);
                        intent.setPackage("com.sec.android.diagmonagent");
                        try {
                            semBioAnalyticsManager.mContext.sendBroadcast(intent);
                            break;
                        } catch (Exception e) {
                            Log.w(
                                    "BiometricService.AM",
                                    "SemAnalyticsManager.insertLog: " + e.getMessage());
                            return;
                        }
                    }
                }
                break;
            default:
                SemBioAnalyticsManager semBioAnalyticsManager2 = this.f$0;
                SemBioAnalyticsManager.EventData eventData2 = this.f$1;
                if (semBioAnalyticsManager2.mContext != null) {
                    semBioAnalyticsManager2.fpHandleData(eventData2);
                    break;
                } else {
                    semBioAnalyticsManager2.mPendingRequestBeforeBootComplete.add(eventData2);
                    break;
                }
        }
    }
}
