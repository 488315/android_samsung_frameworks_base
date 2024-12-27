package com.samsung.android.knox;

import android.content.Context;
import android.content.pm.ILauncherApps;
import android.content.pm.LauncherActivityInfo;
import android.os.UserHandle;
import android.util.Log;

import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class KnoxHelper {
    private static boolean DEBUG = false;
    static final String TAG = "KnoxHelper";

    public static List<LauncherActivityInfo> getActivityList(
            Context context, ILauncherApps service, String packageName, UserHandle user) {
        int callingUid = context.getUserId();
        Log.d(
                TAG,
                "getActivityList callingUserId: "
                        + callingUid
                        + ", target user: "
                        + user.getIdentifier());
        if (callingUid == user.getIdentifier()) {
            return null;
        }
        if (SemPersonaManager.isSecureFolderId(user.getIdentifier())
                || SemPersonaManager.isAppSeparationUserId(user.getIdentifier())) {
            Log.v(
                    TAG,
                    "source and target users are different, and caller is knox container or target"
                        + " user is for secure folder/ separated apps, so request cannot be"
                        + " granted!");
            return Collections.EMPTY_LIST;
        }
        return null;
    }
}
