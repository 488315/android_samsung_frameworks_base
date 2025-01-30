package com.android.server.enterprise.filter;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class KnoxNetworkFilterValidation {
    public static final boolean DBG = Debug.semIsProductDev();
    public static KnoxNetworkFilterValidation mKnoxNwFilterApiValidation;
    public Context mContext;
    public KnoxNetworkFilterHelper mKnoxNwFilterHelper;

    public KnoxNetworkFilterValidation(Context context) {
        this.mContext = context;
        this.mKnoxNwFilterHelper = KnoxNetworkFilterHelper.getInstance(context);
    }

    public static synchronized KnoxNetworkFilterValidation getInstance(Context context) {
        KnoxNetworkFilterValidation knoxNetworkFilterValidation;
        synchronized (KnoxNetworkFilterValidation.class) {
            if (mKnoxNwFilterApiValidation == null) {
                mKnoxNwFilterApiValidation = new KnoxNetworkFilterValidation(context);
            }
            knoxNetworkFilterValidation = mKnoxNwFilterApiValidation;
        }
        return knoxNetworkFilterValidation;
    }

    public int registerApplicationValidation(ContextInfo contextInfo, String str, String str2, Bundle bundle) {
        String signature;
        if (str == null || str.isEmpty()) {
            return -10;
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if (!this.mKnoxNwFilterHelper.isRegisterDbEmpty()) {
            return -9;
        }
        if (this.mKnoxNwFilterHelper.isPackageInstalled(userId, str)) {
            return (str2 == null || str2.isEmpty() || (signature = this.mKnoxNwFilterHelper.getSignature(userId, str)) == null || signature.equalsIgnoreCase(str2)) ? 0 : -3;
        }
        return -6;
    }

    public int unregisterApplicationValidation(ContextInfo contextInfo, String str) {
        if (str == null || str.isEmpty()) {
            return -10;
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        int i = contextInfo.mCallerUid;
        int registeredAppUid = this.mKnoxNwFilterHelper.getRegisteredAppUid(str);
        if (registeredAppUid == -1) {
            return -2;
        }
        if (registeredAppUid != i) {
            return -7;
        }
        if (!this.mKnoxNwFilterHelper.isPackageInstalled(userId, str)) {
            return -6;
        }
        String signature = this.mKnoxNwFilterHelper.getSignature(userId, str);
        String registeredAppSign = this.mKnoxNwFilterHelper.getRegisteredAppSign(str);
        return (registeredAppSign == null || registeredAppSign.isEmpty() || signature == null || signature.equalsIgnoreCase(registeredAppSign)) ? 0 : -3;
    }

    public int getInstanceValidation() {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String packageNameForUid = this.mKnoxNwFilterHelper.getPackageNameForUid(callingUid);
        return !this.mKnoxNwFilterHelper.isAppRegistered(userId, packageNameForUid, this.mKnoxNwFilterHelper.getSignature(userId, packageNameForUid)) ? -5 : 0;
    }

    public int setConfigValidation(String str, String str2) {
        if (str == null || str.isEmpty()) {
            return -7;
        }
        List profileListByVendor = this.mKnoxNwFilterHelper.getProfileListByVendor(Binder.getCallingUid());
        if (!profileListByVendor.isEmpty()) {
            Iterator it = profileListByVendor.iterator();
            while (it.hasNext()) {
                if (!str.equalsIgnoreCase((String) it.next())) {
                    return -6;
                }
            }
        } else if (str2 == null || str2.isEmpty()) {
            return -2;
        }
        if (str2 == null || str2.isEmpty()) {
            return 0;
        }
        try {
            try {
                new JSONObject(str2);
                return 0;
            } catch (JSONException unused) {
                new JSONArray(str2);
                return 0;
            }
        } catch (JSONException unused2) {
            return -3;
        }
    }

    public int registerListenersValidation(String str, String str2) {
        if (str == null || str.isEmpty()) {
            return -7;
        }
        List<String> profileListByVendor = this.mKnoxNwFilterHelper.getProfileListByVendor(Binder.getCallingUid());
        if (profileListByVendor.isEmpty()) {
            return -2;
        }
        for (String str3 : profileListByVendor) {
            if (!str.equalsIgnoreCase(str3)) {
                Log.e("knoxNwFilter-KnoxNetworkFilterValidation", "profile mismatch occurred between: " + str + " " + str3);
                return -6;
            }
        }
        if (str2 != null && !str2.isEmpty()) {
            try {
                new JSONObject(str2);
                if (!this.mKnoxNwFilterHelper.checkIfValidListeners(str2)) {
                    return -3;
                }
            } catch (JSONException unused) {
                return -3;
            }
        }
        return 0;
    }

    public int startValidation(String str) {
        if (str == null || str.isEmpty()) {
            return -7;
        }
        List profileListByVendor = this.mKnoxNwFilterHelper.getProfileListByVendor(Binder.getCallingUid());
        if (profileListByVendor.isEmpty()) {
            return -2;
        }
        Iterator it = profileListByVendor.iterator();
        while (it.hasNext()) {
            if (!str.equalsIgnoreCase((String) it.next())) {
                return -6;
            }
        }
        return 0;
    }

    public int stopValidation(String str) {
        if (str == null || str.isEmpty()) {
            return -7;
        }
        List profileListByVendor = this.mKnoxNwFilterHelper.getProfileListByVendor(Binder.getCallingUid());
        if (profileListByVendor.isEmpty()) {
            return -2;
        }
        Iterator it = profileListByVendor.iterator();
        while (it.hasNext()) {
            if (!str.equalsIgnoreCase((String) it.next())) {
                return -6;
            }
        }
        return 0;
    }
}
