package com.samsung.android.knox;

import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.ILauncherApps;
import android.content.p002pm.LauncherActivityInfo;
import android.content.p002pm.LauncherActivityInfoInternal;
import android.content.p002pm.LauncherApps;
import android.content.p002pm.ParceledListSlice;
import android.p009os.Bundle;
import android.p009os.UserHandle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class KnoxHelper {
  private static boolean DEBUG = false;
  private static final String SEC_DESKTOP_LAUNCHER_PACKGE_NAME =
      "com.sec.android.app.desktoplauncher";
  private static final String SEC_LAUNCHER_PACKGE_NAME = "com.sec.android.app.launcher";
  static final String TAG = "KnoxHelper";

  private static List<LauncherActivityInfo> convertToActivityList(
      Context context,
      ParceledListSlice<LauncherActivityInfoInternal> internals,
      UserHandle user,
      boolean needFilter) {
    if (internals == null) {
      return Collections.EMPTY_LIST;
    }
    Bundle configAS = SemPersonaManager.getAppSeparationConfig();
    ArrayList<LauncherActivityInfo> lais = new ArrayList<>();
    if (configAS != null && context.getUserId() != user.getIdentifier()) {
      return Collections.EMPTY_LIST;
    }
    for (LauncherActivityInfoInternal internal : internals.getList()) {
      LauncherActivityInfo lai = LauncherApps.getLauncherActivityInfo(context, user, internal);
      if (DEBUG) {
        Log.m100v(TAG, "Returning activity for profile " + user + " : " + lai.getComponentName());
      }
      lais.add(lai);
    }
    return lais;
  }

  private static List<LauncherActivityInfo> convertToActivityList(
      Context context,
      ParceledListSlice<LauncherActivityInfoInternal> internals,
      UserHandle user,
      boolean needFilter,
      List<ComponentName> cList) {
    if (internals == null) {
      return Collections.EMPTY_LIST;
    }
    ArrayList<LauncherActivityInfo> lais = new ArrayList<>();
    for (LauncherActivityInfoInternal internal : internals.getList()) {
      if (cList != null && cList.size() > 0) {
        boolean needToSkip = true;
        Iterator<ComponentName> it = cList.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          ComponentName cName = it.next();
          if (DEBUG) {
            Log.m100v(
                TAG,
                "convertToActivityList "
                    + user
                    + " : "
                    + cName
                    + ","
                    + internal.getComponentName()
                    + ".equals():"
                    + cName.equals(internal.getComponentName()));
          }
          if (cName.equals(internal.getComponentName())) {
            needToSkip = false;
            break;
          }
        }
        if (needToSkip) {}
      }
      LauncherActivityInfo lai = LauncherApps.getLauncherActivityInfo(context, user, internal);
      if (DEBUG) {
        Log.m100v(TAG, "Returning activity for profile " + user + " : " + lai.getComponentName());
      }
      lais.add(lai);
    }
    return lais;
  }

  public static List<LauncherActivityInfo> getActivityList(
      Context context, ILauncherApps service, String packageName, UserHandle user) {
    int callingUid = context.getUserId();
    Log.m94d(
        TAG,
        "getActivityList callingUserId: " + callingUid + ", target user: " + user.getIdentifier());
    if (callingUid == user.getIdentifier()) {
      return null;
    }
    if (SemPersonaManager.isSecureFolderId(user.getIdentifier())
        || SemPersonaManager.isAppSeparationUserId(user.getIdentifier())) {
      Log.m100v(
          TAG,
          "source and target users are different, and caller is knox container or target user is"
              + " for secure folder/ separated apps, so request cannot be granted!");
      return Collections.EMPTY_LIST;
    }
    return null;
  }
}
