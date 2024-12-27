package com.android.server.enterprise.ucm;

import android.app.AppGlobals;
import android.content.Context;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UCMStorageHelper {
    public static boolean isCallerValidPlatformApp(Context context) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || callingUid == 5250) {
            Log.d("UCMStorageHelper", "called by system/KnoxCore : return true");
            return true;
        }
        String nameForUid = context.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            return false;
        }
        Log.d("UCMStorageHelper", "isCallerValidPlatformApp ".concat(nameForUid));
        try {
            return AppGlobals.getPackageManager()
                            .checkSignatures(
                                    "android", nameForUid, UserHandle.getUserId(callingUid))
                    == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
