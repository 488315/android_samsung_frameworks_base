package com.android.server.enterprise.nap;

import android.content.ContentValues;
import android.util.Log;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.Profile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NetworkAnalyticsConfigStore {
    public static final boolean DBG = NetworkAnalyticsService.DBG;
    public static NetworkAnalyticsConfigStore mInstance;
    public ConcurrentHashMap activationMap;
    public ConcurrentHashMap adminToProfileMap;
    public NetworkAnalyticsStorageHelper mStorageHelper;
    public ConcurrentHashMap profileMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public int adminUid;
        public int flags;
        public int flowTypeForProfile;
        public int intervalValueForProfile;
        public String jsonString;
        public int opUserId;
        public String packageName;
        public String packageSignature;
        public final String profileName;
        public final /* synthetic */ NetworkAnalyticsConfigStore this$0;
        public int userId;

        public Builder(NetworkAnalyticsConfigStore networkAnalyticsConfigStore, String str) {
            this.profileName = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NAPConfigProfile {
        public final int adminUid;
        public final int flags;
        public int flowTypeForProfile;
        public int intervalValueForProfile;
        public final String jsonString;
        public final int opUserId;
        public final String packageName;
        public final String packageSignature;
        public final String profileName;
        public int userId;

        public NAPConfigProfile(Builder builder) {
            this.profileName = builder.profileName;
            this.packageName = builder.packageName;
            this.packageSignature = builder.packageSignature;
            this.jsonString = builder.jsonString;
            this.adminUid = builder.adminUid;
            this.userId = builder.userId;
            this.opUserId = builder.opUserId;
            this.flags = builder.flags;
            this.flowTypeForProfile = builder.flowTypeForProfile;
            this.intervalValueForProfile = builder.intervalValueForProfile;
        }

        public final String toString() {
            return "profileName=" + this.profileName + ",packageName=" + this.packageName + ",adminUid=" + this.adminUid + ",flags=" + this.flags + ",userId=" + this.userId + ",opUserId=" + this.opUserId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NAPProfileActivation {
        public int activationFlowType;
        public int activationIntervalValue;
        public int activationState;
        public final int adminUid;
        public final NAPConfigProfile profile;

        public NAPProfileActivation(NAPConfigProfile nAPConfigProfile, int i) {
            this.profile = nAPConfigProfile;
            this.adminUid = i;
        }
    }

    public static NetworkAnalyticsConfigStore getInstance(NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper) {
        if (mInstance == null) {
            NetworkAnalyticsConfigStore networkAnalyticsConfigStore = new NetworkAnalyticsConfigStore();
            networkAnalyticsConfigStore.profileMap = null;
            networkAnalyticsConfigStore.adminToProfileMap = null;
            networkAnalyticsConfigStore.activationMap = null;
            networkAnalyticsConfigStore.mStorageHelper = networkAnalyticsStorageHelper;
            networkAnalyticsConfigStore.profileMap = new ConcurrentHashMap();
            networkAnalyticsConfigStore.adminToProfileMap = new ConcurrentHashMap();
            networkAnalyticsConfigStore.activationMap = new ConcurrentHashMap();
            mInstance = networkAnalyticsConfigStore;
        }
        return mInstance;
    }

    public static String getProfileObjectFromJson(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str2).getJSONObject("NETWORK_ANALYTICS_PARAMETERS").getJSONObject("profile_attribute");
            if (jSONObject != null) {
                return jSONObject.optString(str, null);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public final int activateProfile(int i, int i2, int i3, String str) {
        NAPConfigProfile nAPConfigProfile;
        boolean z = DBG;
        int i4 = -1;
        try {
            nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:ConfigStore", "activateProfile: Exception ", e);
        }
        if (nAPConfigProfile == null) {
            Log.d("NetworkAnalytics:ConfigStore", "removeProfileFromDatabase: Profile name not found " + str);
            return -4;
        }
        String transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(i2, str);
        ContentValues contentValues = new ContentValues();
        contentValues.put("activeState", Integer.valueOf(i3));
        if (1 == i3) {
            contentValues.put("flowType", Integer.valueOf(nAPConfigProfile.flowTypeForProfile));
        } else if (i3 == 0) {
            contentValues.put("flowType", (Integer) (-999));
        }
        if (1 == i3) {
            contentValues.put("intervalValue", Integer.valueOf(nAPConfigProfile.intervalValueForProfile));
        } else if (i3 == 0) {
            contentValues.put("intervalValue", (Integer) (-999));
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("profileName", str);
        contentValues2.put("op_userid", Integer.valueOf(i2));
        contentValues2.put("adminUid", Integer.valueOf(i));
        this.mStorageHelper.getClass();
        i4 = NetworkAnalyticsStorageHelper.mEDM.update("NAPProfileTable", contentValues, contentValues2);
        if (z) {
            Log.d("NetworkAnalytics:ConfigStore", "activateProfile: returnStatus = " + i4);
        }
        if (i4 > 0) {
            if (this.activationMap.containsKey(transformedVendorName)) {
                NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(transformedVendorName);
                nAPProfileActivation.activationState = i3;
                if (1 == i3) {
                    nAPProfileActivation.activationFlowType = nAPConfigProfile.flowTypeForProfile;
                } else if (i3 == 0) {
                    nAPProfileActivation.activationFlowType = -999;
                }
                if (1 == i3) {
                    nAPProfileActivation.activationIntervalValue = nAPConfigProfile.intervalValueForProfile;
                } else if (i3 == 0) {
                    nAPProfileActivation.activationIntervalValue = -999;
                }
            } else {
                NAPProfileActivation nAPProfileActivation2 = new NAPProfileActivation((NAPConfigProfile) this.profileMap.get(str), i);
                nAPProfileActivation2.activationState = i3;
                if (1 == i3) {
                    nAPProfileActivation2.activationFlowType = nAPConfigProfile.flowTypeForProfile;
                } else if (i3 == 0) {
                    nAPProfileActivation2.activationFlowType = -999;
                }
                if (1 == i3) {
                    nAPProfileActivation2.activationIntervalValue = nAPConfigProfile.intervalValueForProfile;
                } else if (i3 == 0) {
                    nAPProfileActivation2.activationIntervalValue = -999;
                }
                this.activationMap.put(transformedVendorName, nAPProfileActivation2);
            }
            i4 = 0;
        }
        if (z) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i4, "activateProfile: final returnStatus = ", "NetworkAnalytics:ConfigStore");
        }
        return i4;
    }

    public final int addProfileToDatabase(int i, JSONObject jSONObject, String str, int i2) {
        if (i <= 0) {
            Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: Invalid admin userId");
            return -4;
        }
        String optString = jSONObject.optString("profile_name", null);
        String optString2 = jSONObject.optString("package_name", null);
        String optString3 = jSONObject.optString("package_signature", null);
        int i3 = -1;
        int optInt = jSONObject.optInt("flags", -1);
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("profileName", optString);
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(-999, m, "client_userid", i2, "op_userid");
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(i, m, "adminUid", "pkgName", optString2);
        m.put("pkgSign", optString3);
        m.put("flags", Integer.valueOf(optInt));
        m.put("jsondata", str);
        m.put("activeState", (Integer) 0);
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(-999, m, "flowType", -999, "intervalValue");
        this.mStorageHelper.getClass();
        boolean putDataByFields = NetworkAnalyticsStorageHelper.mEDM.putDataByFields("NAPProfileTable", null, null, m);
        boolean z = DBG;
        if (z) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("addProfileToDatabase: Added to database:", "NetworkAnalytics:ConfigStore", putDataByFields);
        }
        if (putDataByFields) {
            Builder builder = new Builder(this, optString);
            builder.opUserId = i2;
            builder.jsonString = str;
            builder.packageName = optString2;
            builder.packageSignature = optString3;
            builder.adminUid = i;
            builder.flags = optInt;
            builder.userId = -999;
            builder.flowTypeForProfile = -999;
            builder.intervalValueForProfile = -999;
            NAPConfigProfile nAPConfigProfile = new NAPConfigProfile(builder);
            this.profileMap.put(optString, nAPConfigProfile);
            if (this.adminToProfileMap.get(Integer.valueOf(i)) == null) {
                if (z) {
                    Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: adminToProfileMap.get(adminUid) = null");
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(nAPConfigProfile);
                this.adminToProfileMap.putIfAbsent(Integer.valueOf(i), arrayList);
            } else {
                if (z) {
                    Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: adminToProfileMap.get(adminUid) is present");
                }
                ((List) this.adminToProfileMap.get(Integer.valueOf(i))).add(nAPConfigProfile);
            }
            i3 = 0;
        }
        if (z) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i3, "addProfileToDatabase: returnValue = ", "NetworkAnalytics:ConfigStore");
        }
        return i3;
    }

    public final int doesAdminOwnProfile(int i, String str) {
        NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str);
        if (nAPConfigProfile == null) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("doesAdminOwnProfile: Profile name not found ", str, "NetworkAnalytics:ConfigStore");
            return -3;
        }
        if (nAPConfigProfile.adminUid == i) {
            return 0;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "doesAdminOwnProfile: Profile ", str, " does not belong to adminUid ", "NetworkAnalytics:ConfigStore");
        return -7;
    }

    public final NAPProfileActivation getActiveStateForName(String str) {
        if (str == null) {
            Log.d("NetworkAnalytics:ConfigStore", "getActiveStateForName: Null profile Name.");
            return null;
        }
        NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(str);
        if (nAPProfileActivation != null) {
            return nAPProfileActivation;
        }
        Log.d("NetworkAnalytics:ConfigStore", "getActiveStateForName: Invalid profile Name.");
        return null;
    }

    public final List getAllActiveKeysForPackage(String str) {
        Set<String> keySet = this.activationMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : keySet) {
            NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(str2);
            if (nAPProfileActivation != null && nAPProfileActivation.profile.packageName.equals(str) && 1 == nAPProfileActivation.activationState) {
                if (DBG) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m("getAllActiveKeysForPackage: Adding active key for profile", str2, "NetworkAnalytics:ConfigStore");
                }
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public final List getAllActiveKeysForProfile(String str) {
        NAPProfileActivation nAPProfileActivation;
        Set<String> keySet = this.activationMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : keySet) {
            if (str2.substring(0, str2.indexOf("__")).equals(str) && (nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(str2)) != null && 1 == nAPProfileActivation.activationState) {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "getAllActiveKeysForProfile: Adding active key for profile".concat(str2));
                }
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public final List getAllProfilesForPackage(String str) {
        Set keySet = this.profileMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.packageName.equals(str)) {
                arrayList.add(nAPConfigProfile);
            }
        }
        return arrayList;
    }

    public final List getAllProfilesForUser(int i) {
        Set keySet = this.profileMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.opUserId == i) {
                arrayList.add(nAPConfigProfile);
            }
        }
        return arrayList;
    }

    public final List getClientProfileNames(int i) {
        if (this.adminToProfileMap.get(Integer.valueOf(i)) == null || ((List) this.adminToProfileMap.get(Integer.valueOf(i))).size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((List) this.adminToProfileMap.get(Integer.valueOf(i))).iterator();
        while (it.hasNext()) {
            arrayList.add(((NAPConfigProfile) it.next()).profileName);
        }
        return arrayList;
    }

    public final List getClientProfiles(int i, int i2) {
        if (this.adminToProfileMap.get(Integer.valueOf(i)) == null || ((List) this.adminToProfileMap.get(Integer.valueOf(i))).size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (NAPConfigProfile nAPConfigProfile : (List) this.adminToProfileMap.get(Integer.valueOf(i))) {
            if (nAPConfigProfile.opUserId == i2) {
                arrayList.add(nAPConfigProfile.jsonString);
            }
        }
        return arrayList;
    }

    public final List getClientProfiles(int i, String str) {
        ArrayList arrayList = this.profileMap.keySet().size() > 0 ? new ArrayList() : null;
        Iterator it = this.profileMap.keySet().iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.packageName.equals(str)) {
                int i2 = nAPConfigProfile.opUserId;
                if (i == 0 || i == i2) {
                    NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(NetworkAnalyticsService.getTransformedVendorName(i2, nAPConfigProfile.profileName));
                    arrayList.add(new Profile(nAPProfileActivation == null ? 0 : nAPProfileActivation.activationState, nAPConfigProfile.jsonString, i2));
                }
            }
        }
        return arrayList;
    }

    public final NAPConfigProfile getProfileforName(String str) {
        if (str == null) {
            Log.d("NetworkAnalytics:ConfigStore", "getPackageForProfile: Null profile Name.");
            return null;
        }
        NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str);
        if (nAPConfigProfile != null) {
            return nAPConfigProfile;
        }
        Log.d("NetworkAnalytics:ConfigStore", "getPackageForProfile: Invalid profile Name.");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0211 A[Catch: Exception -> 0x0178, TRY_LEAVE, TryCatch #3 {Exception -> 0x0178, blocks: (B:42:0x015d, B:45:0x0171, B:46:0x017b, B:47:0x017f, B:49:0x0185, B:51:0x01c5, B:53:0x01e5, B:58:0x0211), top: B:41:0x015d }] */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initializeTables() {
        /*
            Method dump skipped, instructions count: 547
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.nap.NetworkAnalyticsConfigStore.initializeTables():void");
    }

    public final boolean isActivatedPackage(String str) {
        NAPConfigProfile nAPConfigProfile;
        Set keySet = this.activationMap.keySet();
        if (keySet != null && keySet.size() > 0) {
            Iterator it = keySet.iterator();
            while (it.hasNext()) {
                NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get((String) it.next());
                if (nAPProfileActivation != null && (nAPConfigProfile = nAPProfileActivation.profile) != null && nAPConfigProfile.packageName.equals(str) && 1 == nAPProfileActivation.activationState) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int isProfileActivatedForUser(int i, String str) {
        NAPProfileActivation nAPProfileActivation;
        String transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(i, str);
        if (!this.activationMap.containsKey(transformedVendorName) || (nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(transformedVendorName)) == null) {
            return 0;
        }
        return nAPProfileActivation.activationState;
    }

    public final int removeProfileFromDatabase(int i, String str) {
        if (this.profileMap.get(str) == null) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("removeProfileFromDatabase: Profile name not found ", str, "NetworkAnalytics:ConfigStore");
            return -4;
        }
        this.mStorageHelper.getClass();
        boolean deleteDataByFields = NetworkAnalyticsStorageHelper.mEDM.deleteDataByFields("NAPProfileTable", new String[]{"profileName"}, new String[]{str});
        if (DBG) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("removeProfileFromDatabase: deleteDataByFields? ", "NetworkAnalytics:ConfigStore", deleteDataByFields);
        }
        if (!deleteDataByFields) {
            return -1;
        }
        updateTablesForProfileRemoval(i, str);
        return 0;
    }

    public final int updateBindUserIdForProfile(int i, int i2, String str) {
        boolean z = DBG;
        if (z) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i2, "updateBindUserIdForProfile updating binduserid for profile:", str, " to:", "NetworkAnalytics:ConfigStore");
        }
        int i3 = -1;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("client_userid", Integer.valueOf(i2));
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("profileName", str);
            contentValues2.put("op_userid", Integer.valueOf(i));
            this.mStorageHelper.getClass();
            i3 = NetworkAnalyticsStorageHelper.mEDM.update("NAPProfileTable", contentValues, contentValues2);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:ConfigStore", "updateBindUserIdForProfile: Exception ", e);
        }
        if (i3 < 0) {
            Log.d("NetworkAnalytics:ConfigStore", "updateBindUserIdForProfile failed with returnValue:" + i3);
            return i3;
        }
        NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str);
        if (nAPConfigProfile != null) {
            Log.d("NetworkAnalytics:ConfigStore", "updateBindUserIdForProfile: Profile userId updated to " + i2);
            nAPConfigProfile.userId = i2;
        }
        if (z) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i3, "updateBindUserIdForProfile returnValue:", "NetworkAnalytics:ConfigStore");
        }
        return i3;
    }

    public final void updateTablesForProfileRemoval(int i, String str) {
        boolean z;
        List list = (List) this.adminToProfileMap.get(Integer.valueOf(i));
        int i2 = 0;
        while (true) {
            int size = list.size();
            z = DBG;
            if (i2 >= size) {
                break;
            }
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) list.get(i2);
            String str2 = nAPConfigProfile.profileName;
            if (str2 == null || !str2.equals(str)) {
                i2++;
            } else {
                if (z) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("updateTablesForProfileRemoval: profile found "), nAPConfigProfile.profileName, "NetworkAnalytics:ConfigStore");
                }
                ((List) this.adminToProfileMap.get(Integer.valueOf(i))).remove(i2);
            }
        }
        this.profileMap.remove(str);
        for (String str3 : this.activationMap.keySet()) {
            if (str3.substring(0, str3.indexOf("__")).equals(str)) {
                if (z) {
                    Log.d("NetworkAnalytics:ConfigStore", "updateTablesForProfileRemoval: removing from activation map ".concat(str3));
                }
                this.activationMap.remove(str3);
            }
        }
    }

    public final int validateJsonContent(JSONObject jSONObject) {
        if (jSONObject == null) {
            Log.d("NetworkAnalytics:ConfigStore", "validateJsonContent: Invalid parameters");
            return -4;
        }
        if (jSONObject != null) {
            String optString = jSONObject.optString("profile_name", null);
            if (optString == null) {
                Log.d("NetworkAnalytics:ConfigStore", "validateJsonContent: Invalid profile name.");
                return -5;
            }
            if (this.profileMap.get(optString) != null) {
                DualAppManagerService$$ExternalSyntheticOutline0.m("validateJsonContent: Profile name already exists ", optString, "NetworkAnalytics:ConfigStore");
                return -6;
            }
            String optString2 = jSONObject.optString("package_name", null);
            String optString3 = jSONObject.optString("package_signature", null);
            int optInt = jSONObject.optInt("flags", -1);
            if (optString2 == null || optString3 == null || optInt < 0) {
                Log.d("NetworkAnalytics:ConfigStore", "validateJsonContent: Invalid flags or packageName or signature.");
                return -5;
            }
        }
        return 0;
    }

    public final boolean validatePkgSignForSingleProfile(String str, String str2, String str3) {
        NAPConfigProfile nAPConfigProfile;
        Iterator it = this.profileMap.keySet().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            boolean z = DBG;
            if (!hasNext) {
                if (!z) {
                    return true;
                }
                DualAppManagerService$$ExternalSyntheticOutline0.m("validatePkgSignForAllProfiles: Valid package signature for ", str, "NetworkAnalytics:ConfigStore");
                return true;
            }
            String str4 = (String) it.next();
            if (str4.equals(str3) && (nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str4)) != null && nAPConfigProfile.packageName.equals(str)) {
                String str5 = nAPConfigProfile.packageSignature;
                if (!str5.equals(str2)) {
                    if (!z) {
                        return false;
                    }
                    Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures Dont match!!! ");
                    Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures in profile");
                    Log.d("NetworkAnalytics:ConfigStore", str5);
                    Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures in parameter ");
                    Log.d("NetworkAnalytics:ConfigStore", str2);
                    return false;
                }
            }
        }
    }
}
