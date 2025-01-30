package com.android.server.enterprise.restriction;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.storage.EdmStorageProvider;
import java.util.List;

/* loaded from: classes2.dex */
public class SimDBProxy {
    public static final String TAG = "SimDBProxy";
    public static SimDBProxy mSimDBProxy;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;

    public SimDBProxy(Context context) {
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public static SimDBProxy getInstance(Context context) {
        if (mSimDBProxy == null) {
            mSimDBProxy = new SimDBProxy(context);
        }
        return mSimDBProxy;
    }

    public boolean addSimcard(int i, String str, String str2) {
        int adminByField = this.mEdmStorageProvider.getAdminByField("SimTable", "SimIccId", str);
        if (adminByField == i) {
            return true;
        }
        if (adminByField != -1) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("SimIccId", str);
        contentValues.put("SimPinCode", str2);
        return this.mEdmStorageProvider.putValuesNoUpdate("SimTable", contentValues);
    }

    public boolean removeSimcard(int i, String str) {
        return this.mEdmStorageProvider.removeByAdminAndField("SimTable", i, "SimIccId", str);
    }

    public boolean removeSimcardsByAdmin(int i) {
        return this.mEdmStorageProvider.removeByAdmin("SimTable", i);
    }

    public boolean setPincode(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SimPinCode", str2);
        return this.mEdmStorageProvider.putValuesForAdminAndField("SimTable", i, "SimIccId", str, contentValues);
    }

    public String getPincode(String str) {
        String string = this.mEdmStorageProvider.getString("SimTable", "SimIccId", str, "SimPinCode");
        if (TextUtils.isEmpty(string)) {
            Log.d(TAG, "Could not find pincode for iccId " + str);
        } else {
            Log.d(TAG, "Successfully found pincode for iccId " + str);
        }
        return string;
    }

    public boolean hasAnySimcard() {
        return this.mEdmStorageProvider.getCount("SimTable", null) > 0;
    }

    public int getAdminBySimcard(String str) {
        return this.mEdmStorageProvider.getAdminByField("SimTable", "SimIccId", str);
    }

    public String[] getIccIdListByAdmin() {
        List values = this.mEdmStorageProvider.getValues("SimTable", new String[]{"SimIccId"}, new ContentValues());
        int size = values.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = ((ContentValues) values.get(i)).getAsString("SimIccId");
        }
        return strArr;
    }
}
