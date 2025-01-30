package com.android.server.enterprise.dualdar;

import android.app.AppGlobals;
import android.content.ContentValues;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.storage.EdmStorageProvider;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class DualDARStorageHelper {
    public static int setDualDARProfile(Context context, Bundle bundle) {
        Log.d("DualDARStorageHelper", "setDualDARProfile called ");
        context.enforceCallingPermission("com.samsung.android.knox.permission.KNOX_DUAL_DAR_INTERNAL", "dualdar storage denied");
        if (bundle == null) {
            return -1;
        }
        String string = TextUtils.isEmpty(bundle.getString("dualdar-config-client-package")) ? "default" : bundle.getString("dualdar-config-client-package");
        String string2 = TextUtils.isEmpty(bundle.getString("dualdar-config-client-signature")) ? "default" : bundle.getString("dualdar-config-client-signature");
        String string3 = TextUtils.isEmpty(bundle.getString("dualdar-config-client-location")) ? "default" : bundle.getString("dualdar-config-client-location");
        Log.d("DualDARStorageHelper", "setDualDARProfile() - package Name " + string + " signature " + string2 + " location " + string3);
        ContentValues contentValues = new ContentValues();
        contentValues.put("configValue", (Integer) 1);
        contentValues.put("clientAppPackageName", string);
        contentValues.put("clientAppSignature", string2);
        contentValues.put("clientAppLocation", string3);
        if (new EdmStorageProvider(context).putValues("DUAL_DAR_CONFIG", contentValues)) {
            Log.d("DualDARStorageHelper", "setEnableDualDAR triggered successfully");
            return 0;
        }
        Log.e("DualDARStorageHelper", "setEnableDualDAR trigger failed");
        return 0;
    }

    public static Bundle getDualDARProfile(Context context) {
        Log.d("DualDARStorageHelper", "getDualDARProfile()");
        if (!isCallerValidPlatformApp(context)) {
            Log.e("DualDARStorageHelper", "Error ! caller not a valid platform app");
            return null;
        }
        List values = new EdmStorageProvider(context).getValues("DUAL_DAR_CONFIG", new String[]{"configValue", "clientAppPackageName", "clientAppSignature", "clientAppLocation"}, (ContentValues) null);
        if (values == null || values.size() == 0) {
            return null;
        }
        boolean z = false;
        ContentValues contentValues = (ContentValues) values.get(0);
        Integer asInteger = contentValues.getAsInteger("configValue");
        if (asInteger != null && asInteger.intValue() == 1) {
            z = true;
        }
        String asString = contentValues.getAsString("clientAppPackageName");
        String asString2 = contentValues.getAsString("clientAppSignature");
        String asString3 = contentValues.getAsString("clientAppLocation");
        Log.d("DualDARStorageHelper", "getDualDARProfile() - isEnableDualDAR " + z + "package Name " + asString + " signature " + asString2 + " packageLocation " + asString3);
        Bundle bundle = new Bundle();
        bundle.putBoolean("dualdar-config", z);
        bundle.putString("dualdar-config-client-package", asString);
        bundle.putString("dualdar-config-client-signature", asString2);
        bundle.putString("dualdar-config-client-location", asString3);
        return bundle;
    }

    public static boolean isCallerValidPlatformApp(Context context) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000) {
            Log.d("DualDARStorageHelper", "called by system : return true");
            return true;
        }
        if (callingUid == 5250) {
            Log.d("DualDARStorageHelper", "called by KnoxCore : return true");
            return true;
        }
        String nameForUid = context.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            return false;
        }
        Log.d("DualDARStorageHelper", "isCallerValidPlatformApp " + nameForUid);
        return isPlatformSigned(context, nameForUid, UserHandle.getUserId(callingUid));
    }

    public static boolean isPlatformSigned(Context context, String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkSignatures("android", str, i) == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
