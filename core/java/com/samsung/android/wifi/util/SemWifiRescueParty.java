package com.samsung.android.wifi.util;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemWifiRescueParty {
  private static final String APEX_WIFI_DATA_PATH = "apexdata/com.android.wifi";
  private static final String MISC_PATH = "/data/misc/";
  private static final String MISC_USER_PATH = "/data/misc_ce/";
  private static final String SYSTEM_DB_PATH = "/data/system/";
  private static final String TAG = "SemWifiRescueParty";

  public static int resetAllWifiStoredData(int[] userIds) {
    int counter = 0;
    if (removeFile("/data/system/wifigeofence.db")) {
      counter = 0 + 1;
    }
    if (removeFile("/data/system/wifigeofence.db-journal")) {
      counter++;
    }
    if (removeFile("/data/system/WifiHistory.db")) {
      counter++;
    }
    if (removeFile("/data/system/WifiHistory.db-journal")) {
      counter++;
    }
    if (removeFile("/data/system/WifiConfigStore.db")) {
      counter++;
    }
    if (removeFile("/data/system/WifiConfigStore.db-journal")) {
      counter++;
    }
    int counter2 =
        counter
            + removeFiles("/data/misc/wifi")
            + removeFiles("/data/misc/apexdata/com.android.wifi");
    for (int userId : userIds) {
      counter2 =
          counter2
              + removeFiles(MISC_USER_PATH + userId + "/wifi")
              + removeFiles(MISC_USER_PATH + userId + "/" + APEX_WIFI_DATA_PATH);
    }
    Log.m96e(TAG, "reset all Wi-Fi stored files: " + counter2);
    return counter2;
  }

  private static boolean removeFile(String path) {
    try {
      return new File(path).delete();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private static int removeFiles(String path) {
    List<String> filesList = new ArrayList<>();
    List<String> folderList = new ArrayList<>();
    fetchCompleteList(filesList, folderList, path);
    int counter = 0;
    for (String filePath : filesList) {
      if (removeFile(filePath)) {
        counter++;
      }
    }
    for (String filePath2 : folderList) {
      if (removeFile(filePath2)) {
        counter++;
      }
    }
    return counter;
  }

  private static void fetchCompleteList(
      List<String> filesList, List<String> folderList, String path) {
    File file = new File(path);
    File[] listOfFile = file.listFiles();
    if (listOfFile != null) {
      for (File tempFile : listOfFile) {
        if (tempFile.isDirectory()) {
          folderList.add(tempFile.getAbsolutePath());
          fetchCompleteList(filesList, folderList, tempFile.getAbsolutePath());
        } else {
          filesList.add(tempFile.getAbsolutePath());
        }
      }
    }
  }
}
