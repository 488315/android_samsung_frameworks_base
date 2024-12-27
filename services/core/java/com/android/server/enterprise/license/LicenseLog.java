package com.android.server.enterprise.license;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.util.Slog;

import com.android.server.enterprise.storage.EdmStorageProvider;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class LicenseLog {
    public static Bundle getLog(String str) {
        EdmStorageProvider edmStorageProvider = LicenseLogService.mEdmStorageProvider;
        try {
            Bundle bundle = new Bundle();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pkgName", str);
            ArrayList arrayList =
                    (ArrayList)
                            LicenseLogService.mEdmStorageProvider.getValuesList(
                                    "LICENSE_LOG",
                                    new String[] {"date", "id", "value"},
                                    contentValues);
            if (arrayList.isEmpty()) {
                return null;
            }
            Iterator it = arrayList.iterator();
            int i = 0;
            String str2 = null;
            String str3 = null;
            while (it.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it.next();
                if (contentValues2 != null) {
                    str3 = contentValues2.getAsString("date");
                    str2 = contentValues2.getAsString("id");
                    i = contentValues2.getAsInteger("value").intValue();
                }
                if (bundle.getBundle(str3) == null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(str2, i);
                    bundle.putBundle(str3, bundle2);
                } else {
                    bundle.getBundle(str3).putInt(str2, i);
                }
            }
            return bundle;
        } catch (Exception e) {
            Log.w("LicenseLogService", "getLog() failed");
            Slog.w("LicenseLogService", "getLog() failed", e);
            return null;
        }
    }
}
