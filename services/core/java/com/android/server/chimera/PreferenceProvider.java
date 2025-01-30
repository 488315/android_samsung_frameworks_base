package com.android.server.chimera;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ServiceManager;
import android.util.Log;

/* loaded from: classes.dex */
public class PreferenceProvider extends ContentProvider {
  @Override // android.content.ContentProvider
  public int delete(Uri uri, String str, String[] strArr) {
    return 0;
  }

  @Override // android.content.ContentProvider
  public String getType(Uri uri) {
    return "";
  }

  @Override // android.content.ContentProvider
  public Uri insert(Uri uri, ContentValues contentValues) {
    return null;
  }

  @Override // android.content.ContentProvider
  public boolean onCreate() {
    return true;
  }

  @Override // android.content.ContentProvider
  public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
    String str3;
    String str4;
    String str5;
    ChimeraManager chimeraManager;
    ChimeraManagerService chimeraManagerService =
        (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
    if (chimeraManagerService == null
        || (chimeraManager = chimeraManagerService.getChimeraManager()) == null) {
      str3 = "";
      str4 = "";
      str5 = str4;
    } else {
      SettingRepository settingRepository = chimeraManager.getSettingRepository();
      str3 = chimeraManager.getVersion();
      str5 = "Default,Conservative";
      if (settingRepository.isConservativeDefault()) {
        str5 = "Default,Conservative,Aggressive";
      }
      if (settingRepository.isCustomMode()) {
        str4 = settingRepository.isConservativeMode() ? "Conservative" : "Aggressive";
      } else {
        str4 = "Default";
      }
    }
    MatrixCursor matrixCursor =
        new MatrixCursor(new String[] {"VERSION", "SUPPORTED_MODE", "CURRENT_MODE"});
    MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
    newRow.add("VERSION", str3);
    newRow.add("SUPPORTED_MODE", str5);
    newRow.add("CURRENT_MODE", str4);
    matrixCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return matrixCursor;
  }

  @Override // android.content.ContentProvider
  public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
    ChimeraManager chimeraManager;
    ChimeraManagerService chimeraManagerService =
        (ChimeraManagerService) ServiceManager.getService("ChimeraManagerService");
    if (chimeraManagerService != null
        && (chimeraManager = chimeraManagerService.getChimeraManager()) != null) {
      SettingRepository settingRepository = chimeraManager.getSettingRepository();
      String asString = contentValues.getAsString("MODE");
      if ("Aggressive".equals(asString)) {
        if (settingRepository.isCustomMode() && !settingRepository.isConservativeMode()) {
          return 0;
        }
        Log.d("PreferenceProvider", "custom mode = true, false");
        settingRepository.enableCustomMode(true, false);
        chimeraManager.createPolicyHandler();
      } else if ("Conservative".equals(asString)) {
        if (settingRepository.isCustomMode() && settingRepository.isConservativeMode()) {
          return 0;
        }
        Log.d("PreferenceProvider", "custom mode = true, true");
        settingRepository.enableCustomMode(true, true);
        chimeraManager.createPolicyHandler();
      } else {
        if (!settingRepository.isCustomMode()) {
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
