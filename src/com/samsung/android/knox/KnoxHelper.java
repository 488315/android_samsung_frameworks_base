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

    public static List<LauncherActivityInfo> getActivityList(Context context, ILauncherApps iLauncherApps, String str, UserHandle userHandle) {
        int userId = context.getUserId();
        Log.d(TAG, "getActivityList callingUserId: " + userId + ", target user: " + userHandle.getIdentifier());
        if (userId == userHandle.getIdentifier()) {
            return null;
        }
        if (!SemPersonaManager.isSecureFolderId(userHandle.getIdentifier()) && !SemPersonaManager.isAppSeparationUserId(userHandle.getIdentifier())) {
            return null;
        }
        Log.v(TAG, "source and target users are different, and caller is knox container or target user is for secure folder/ separated apps, so request cannot be granted!");
        return Collections.EMPTY_LIST;
    }
}
