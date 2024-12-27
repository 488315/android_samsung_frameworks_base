package com.android.server.enterprise.restriction;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SimDBProxy {
    public static SimDBProxy mSimDBProxy;
    public final EdmStorageProvider mEdmStorageProvider;

    public SimDBProxy(Context context) {
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public final boolean addSimcard(int i, String str, String str2) {
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        int adminByField = edmStorageProvider.getAdminByField("SimTable", "SimIccId", str);
        if (adminByField == i) {
            return true;
        }
        if (adminByField != -1) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                i, contentValues, "adminUid", "SimIccId", str);
        contentValues.put("SimPinCode", str2);
        return edmStorageProvider.putValuesNoUpdate("SimTable", contentValues);
    }

    public final String getPincode(String str) {
        String string =
                this.mEdmStorageProvider.getString("SimTable", "SimIccId", str, "SimPinCode");
        if (TextUtils.isEmpty(string)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Could not find pincode for iccId ", str, "SimDBProxy");
        } else {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Successfully found pincode for iccId ", str, "SimDBProxy");
        }
        return string;
    }
}
