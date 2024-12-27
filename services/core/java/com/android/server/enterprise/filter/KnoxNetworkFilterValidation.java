package com.android.server.enterprise.filter;

import android.app.AppGlobals;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Debug;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public final class KnoxNetworkFilterValidation {
    public static KnoxNetworkFilterValidation mKnoxNwFilterApiValidation;
    public KnoxNetworkFilterHelper mKnoxNwFilterHelper;

    static {
        Debug.semIsProductDev();
    }

    public final int getInstanceValidation(int i) {
        int userId = UserHandle.getUserId(i);
        KnoxNetworkFilterHelper knoxNetworkFilterHelper = this.mKnoxNwFilterHelper;
        knoxNetworkFilterHelper.getClass();
        String packageNameForUid = KnoxNetworkFilterHelper.getPackageNameForUid(i);
        boolean isAppRegistered =
                knoxNetworkFilterHelper.isAppRegistered(
                        userId,
                        packageNameForUid,
                        knoxNetworkFilterHelper.getSignature(userId, packageNameForUid));
        if (knoxNetworkFilterHelper.isAppAuthorized(i, packageNameForUid)) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i,
                    "getInstanceValidation: isAppAuthorized returns true for ",
                    packageNameForUid,
                    "knoxNwFilter-KnoxNetworkFilterValidation");
            return 0;
        }
        if (isAppRegistered) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i,
                    "getInstanceValidation: isAppRegistered returns true for ",
                    packageNameForUid,
                    "knoxNwFilter-KnoxNetworkFilterValidation");
            return 0;
        }
        AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                i,
                "getInstanceValidation: isAppRegistered/isAppAuthorized returns false for ",
                packageNameForUid,
                "knoxNwFilter-KnoxNetworkFilterValidation");
        return -5;
    }

    public final void prepareFilteringValidation(int i, String str) {
        String[] strArr;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mKnoxNwFilterHelper.getClass();
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            boolean z = false;
            try {
                PackageInfo packageInfo =
                        AppGlobals.getPackageManager().getPackageInfo(str, 4096L, i);
                if (packageInfo != null
                        && (strArr = packageInfo.requestedPermissions) != null
                        && strArr.length > 0) {
                    int length = strArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (strArr[i2].equalsIgnoreCase(
                                "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER")) {
                            z = true;
                            break;
                        }
                        i2++;
                    }
                }
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            if (z) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } else {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterValidation",
                        "prepare filtering failed since permission not included in manifest");
                throw new SecurityException("manifest permission not present");
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final int registerListenersValidation(String str, String str2) {
        int instanceValidation = getInstanceValidation(Binder.getCallingUid());
        if (instanceValidation != 0) {
            throw new SecurityException();
        }
        if (str == null || str.isEmpty()) {
            return -7;
        }
        this.mKnoxNwFilterHelper.getClass();
        int vendorUidByProfile = KnoxNetworkFilterHelper.getVendorUidByProfile(str);
        if (vendorUidByProfile != -1 && vendorUidByProfile != Binder.getCallingUid()) {
            return -12;
        }
        ArrayList arrayList =
                (ArrayList) KnoxNetworkFilterHelper.getProfileListByVendor(Binder.getCallingUid());
        if (arrayList.isEmpty()) {
            return -2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (!str.equalsIgnoreCase(str3)) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterValidation",
                        "profile mismatch occurred between: " + str + " " + str3);
                return -6;
            }
        }
        if (str2 != null && !str2.isEmpty()) {
            try {
                new JSONObject(str2);
            } catch (JSONException unused) {
                return -3;
            }
        }
        return instanceValidation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0076, code lost:

       if (r6.isEmpty() == false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0058, code lost:

       if (r6.isEmpty() == false) goto L26;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setConfigValidation(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            int r0 = android.os.Binder.getCallingUid()
            int r0 = r4.getInstanceValidation(r0)
            if (r0 != 0) goto L93
            if (r5 == 0) goto L91
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto L14
            goto L91
        L14:
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r4 = r4.mKnoxNwFilterHelper
            r4.getClass()
            int r4 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getVendorUidByProfile(r5)
            r1 = -1
            if (r4 == r1) goto L29
            int r1 = android.os.Binder.getCallingUid()
            if (r4 == r1) goto L29
            r4 = -12
            return r4
        L29:
            int r4 = android.os.Binder.getCallingUid()
            java.util.List r4 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getProfileListByVendor(r4)
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            boolean r1 = r4.isEmpty()
            r2 = -2
            r3 = -6
            if (r1 != 0) goto L52
            java.util.Iterator r4 = r4.iterator()
        L3f:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L5b
            java.lang.Object r1 = r4.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r1 = r5.equalsIgnoreCase(r1)
            if (r1 != 0) goto L3f
            return r3
        L52:
            if (r6 == 0) goto L90
            boolean r4 = r6.isEmpty()
            if (r4 == 0) goto L5b
            goto L90
        L5b:
            int r4 = android.os.Binder.getCallingUid()
            int r4 = android.os.UserHandle.getUserId(r4)
            java.lang.String r4 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.getProfilebyUserId(r4)
            if (r4 == 0) goto L70
            boolean r4 = r5.equalsIgnoreCase(r4)
            if (r4 != 0) goto L79
            return r3
        L70:
            if (r6 == 0) goto L90
            boolean r4 = r6.isEmpty()
            if (r4 == 0) goto L79
            goto L90
        L79:
            if (r6 == 0) goto L8f
            boolean r4 = r6.isEmpty()
            if (r4 != 0) goto L8f
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch: org.json.JSONException -> L87
            r4.<init>(r6)     // Catch: org.json.JSONException -> L87
            goto L8f
        L87:
            org.json.JSONArray r4 = new org.json.JSONArray     // Catch: org.json.JSONException -> L8d
            r4.<init>(r6)     // Catch: org.json.JSONException -> L8d
            goto L8f
        L8d:
            r4 = -3
            return r4
        L8f:
            return r0
        L90:
            return r2
        L91:
            r4 = -7
            return r4
        L93:
            java.lang.SecurityException r4 = new java.lang.SecurityException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterValidation.setConfigValidation(java.lang.String,"
                    + " java.lang.String):int");
    }

    public final int startValidation(String str) {
        int instanceValidation = getInstanceValidation(Binder.getCallingUid());
        if (instanceValidation != 0) {
            throw new SecurityException();
        }
        if (str == null || str.isEmpty()) {
            return -7;
        }
        this.mKnoxNwFilterHelper.getClass();
        int vendorUidByProfile = KnoxNetworkFilterHelper.getVendorUidByProfile(str);
        if (vendorUidByProfile != -1 && vendorUidByProfile != Binder.getCallingUid()) {
            return -12;
        }
        ArrayList arrayList =
                (ArrayList) KnoxNetworkFilterHelper.getProfileListByVendor(Binder.getCallingUid());
        if (arrayList.isEmpty()) {
            instanceValidation = -2;
        } else {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (!str.equalsIgnoreCase((String) it.next())) {
                    return -6;
                }
            }
        }
        if (instanceValidation != 0) {
            return instanceValidation;
        }
        String retrieveListenersFromCache = KnoxNetworkFilterHelper.retrieveListenersFromCache(str);
        if (retrieveListenersFromCache != null && !retrieveListenersFromCache.isEmpty()) {
            return instanceValidation;
        }
        Log.e(
                "knoxNwFilter-KnoxNetworkFilterValidation",
                "start API failed for profile " + str + " since listener config is not set");
        return -8;
    }

    public final int stopValidation(int i, String str) {
        int instanceValidation = getInstanceValidation(i);
        if (instanceValidation != 0) {
            throw new SecurityException();
        }
        if (str == null || str.isEmpty()) {
            return -7;
        }
        this.mKnoxNwFilterHelper.getClass();
        int vendorUidByProfile = KnoxNetworkFilterHelper.getVendorUidByProfile(str);
        if (vendorUidByProfile != -1 && vendorUidByProfile != i) {
            return -12;
        }
        ArrayList arrayList = (ArrayList) KnoxNetworkFilterHelper.getProfileListByVendor(i);
        if (arrayList.isEmpty()) {
            return -2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (!str.equalsIgnoreCase((String) it.next())) {
                return -6;
            }
        }
        return instanceValidation;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int unregisterApplicationValidation(
            com.samsung.android.knox.ContextInfo r9, java.lang.String r10) {
        /*
            r8 = this;
            if (r10 == 0) goto La7
            boolean r0 = r10.isEmpty()
            if (r0 == 0) goto La
            goto La7
        La:
            int r0 = r9.mCallerUid
            int r0 = android.os.UserHandle.getUserId(r0)
            int r9 = r9.mCallerUid
            com.android.server.enterprise.filter.KnoxNetworkFilterHelper r8 = r8.mKnoxNwFilterHelper
            com.android.server.enterprise.storage.EdmStorageProvider r1 = r8.mEDM
            java.lang.String r2 = "pkgName"
            java.lang.String[] r3 = new java.lang.String[]{r2}
            java.lang.String[] r4 = new java.lang.String[]{r10}
            java.lang.String r5 = "adminUid"
            java.lang.String[] r6 = new java.lang.String[]{r5}
            java.lang.String r7 = "NwFilterMgr"
            java.util.ArrayList r1 = r1.getDataByFields(r7, r3, r4, r6)
            int r3 = r1.size()
            r4 = -1
            if (r3 <= 0) goto L4d
            java.util.Iterator r1 = r1.iterator()
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L4d
            java.lang.Object r1 = r1.next()
            android.content.ContentValues r1 = (android.content.ContentValues) r1
            java.lang.Integer r1 = r1.getAsInteger(r5)
            int r1 = r1.intValue()
            goto L4e
        L4d:
            r1 = r4
        L4e:
            if (r1 != r4) goto L52
            r8 = -2
            return r8
        L52:
            if (r1 == r9) goto L56
            r8 = -7
            return r8
        L56:
            boolean r9 = com.android.server.enterprise.filter.KnoxNetworkFilterHelper.isPackageInstalled(r0, r10)
            if (r9 == 0) goto La5
            java.lang.String r9 = r8.getSignature(r0, r10)
            com.android.server.enterprise.storage.EdmStorageProvider r8 = r8.mEDM
            java.lang.String[] r0 = new java.lang.String[]{r2}
            java.lang.String[] r10 = new java.lang.String[]{r10}
            java.lang.String r1 = "pkgSign"
            java.lang.String[] r2 = new java.lang.String[]{r1}
            java.util.ArrayList r8 = r8.getDataByFields(r7, r0, r10, r2)
            int r10 = r8.size()
            if (r10 <= 0) goto L90
            java.util.Iterator r8 = r8.iterator()
            boolean r10 = r8.hasNext()
            if (r10 == 0) goto L90
            java.lang.Object r8 = r8.next()
            android.content.ContentValues r8 = (android.content.ContentValues) r8
            java.lang.String r8 = r8.getAsString(r1)
            goto L91
        L90:
            r8 = 0
        L91:
            if (r8 == 0) goto La3
            boolean r10 = r8.isEmpty()
            if (r10 != 0) goto La3
            if (r9 == 0) goto La3
            boolean r8 = r9.equalsIgnoreCase(r8)
            if (r8 != 0) goto La3
            r8 = -3
            return r8
        La3:
            r8 = 0
            return r8
        La5:
            r8 = -6
            return r8
        La7:
            r8 = -10
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.filter.KnoxNetworkFilterValidation.unregisterApplicationValidation(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String):int");
    }
}
