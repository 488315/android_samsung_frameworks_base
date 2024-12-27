package com.android.server.chimera;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class PreferenceProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return "";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String str3;
        String str4;
        String str5;
        ChimeraManager chimeraManager;
        ChimeraManagerService chimeraManagerService =
                (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
        if (chimeraManagerService == null
                || (chimeraManager = chimeraManagerService.mChimeraManager) == null) {
            str3 = "";
            str4 = "";
            str5 = str4;
        } else {
            SettingRepository settingRepository = chimeraManager.mSettingRepository;
            settingRepository.mSystemRepository.getClass();
            str3 =
                    SystemProperties.get("ro.slmk.use_bg_keeping_policy", "false").equals("true")
                            ? "Default,Conservative,Aggressive"
                            : "Default,Conservative";
            str5 = "2.0";
            str4 =
                    settingRepository.mIsCustomMode
                            ? settingRepository.mIsConservativeMode ? "Conservative" : "Aggressive"
                            : "Default";
        }
        MatrixCursor matrixCursor =
                new MatrixCursor(new String[] {"VERSION", "SUPPORTED_MODE", "CURRENT_MODE"});
        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        newRow.add("VERSION", str5);
        newRow.add("SUPPORTED_MODE", str3);
        newRow.add("CURRENT_MODE", str4);
        matrixCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        ChimeraManager chimeraManager;
        ChimeraManagerService chimeraManagerService =
                (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
        if (chimeraManagerService != null
                && (chimeraManager = chimeraManagerService.mChimeraManager) != null) {
            String asString = contentValues.getAsString("MODE");
            boolean equals = "Aggressive".equals(asString);
            SettingRepository settingRepository = chimeraManager.mSettingRepository;
            if (equals) {
                if (settingRepository.mIsCustomMode && !settingRepository.mIsConservativeMode) {
                    return 0;
                }
                Log.d("PreferenceProvider", "custom mode = true, false");
                settingRepository.enableCustomMode(true, false);
                chimeraManager.createPolicyHandler();
            } else if ("Conservative".equals(asString)) {
                if (settingRepository.mIsCustomMode && settingRepository.mIsConservativeMode) {
                    return 0;
                }
                Log.d("PreferenceProvider", "custom mode = true, true");
                settingRepository.enableCustomMode(true, true);
                chimeraManager.createPolicyHandler();
            } else {
                if (!settingRepository.mIsCustomMode) {
                    return 0;
                }
                Log.d("PreferenceProvider", "custom mode = false, false");
                settingRepository.enableCustomMode(false, false);
                chimeraManager.createPolicyHandler();
            }
        }
        return 0;
    }
}
