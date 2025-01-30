package com.android.server.enterprise.nap;

import android.content.ContentValues;
import android.util.Log;
import com.samsung.android.knox.net.nap.Profile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NetworkAnalyticsConfigStore {
    public static final boolean DBG = NetworkAnalyticsService.DBG;
    public static NetworkAnalyticsConfigStore mInstance;
    public ConcurrentHashMap activationMap;
    public ConcurrentHashMap adminToProfileMap;
    public NetworkAnalyticsStorageHelper mStorageHelper;
    public ConcurrentHashMap profileMap;

    public NetworkAnalyticsConfigStore(NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper) {
        this.profileMap = null;
        this.adminToProfileMap = null;
        this.activationMap = null;
        this.mStorageHelper = networkAnalyticsStorageHelper;
        this.profileMap = new ConcurrentHashMap();
        this.adminToProfileMap = new ConcurrentHashMap();
        this.activationMap = new ConcurrentHashMap();
    }

    public static NetworkAnalyticsConfigStore getInstance(NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper) {
        if (mInstance == null) {
            mInstance = new NetworkAnalyticsConfigStore(networkAnalyticsStorageHelper);
        }
        return mInstance;
    }

    public void initializeTables() {
        initializeProfileTable();
        initializeActivationTable();
    }

    public int addProfileToDatabase(int i, JSONObject jSONObject, String str, int i2) {
        if (i <= 0) {
            Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: Invalid admin userId");
            return -4;
        }
        String optString = jSONObject.optString("profile_name", null);
        String optString2 = jSONObject.optString("package_name", null);
        String optString3 = jSONObject.optString("package_signature", null);
        int i3 = -1;
        int optInt = jSONObject.optInt("flags", -1);
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileName", optString);
        contentValues.put("client_userid", (Integer) (-999));
        contentValues.put("op_userid", Integer.valueOf(i2));
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("pkgName", optString2);
        contentValues.put("pkgSign", optString3);
        contentValues.put("flags", Integer.valueOf(optInt));
        contentValues.put("jsondata", str);
        contentValues.put("activeState", (Integer) 0);
        contentValues.put("flowType", (Integer) (-999));
        contentValues.put("intervalValue", (Integer) (-999));
        boolean putDataByFields = this.mStorageHelper.putDataByFields("NAPProfileTable", null, null, contentValues);
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: Added to database:" + putDataByFields);
        }
        if (putDataByFields) {
            Builder builder = new Builder(optString);
            builder.opUserId(i2);
            builder.jsonString(str);
            builder.packageName(optString2);
            builder.packageSignature(optString3);
            builder.adminUid(i);
            builder.flags(optInt);
            builder.userId(-999);
            builder.flowTypeForProfile(-999);
            builder.intervalValueForProfile(-999);
            NAPConfigProfile build = builder.build();
            this.profileMap.put(optString, build);
            if (this.adminToProfileMap.get(Integer.valueOf(i)) == null) {
                if (z) {
                    Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: adminToProfileMap.get(adminUid) = null");
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(build);
                this.adminToProfileMap.putIfAbsent(Integer.valueOf(i), arrayList);
            } else {
                if (z) {
                    Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: adminToProfileMap.get(adminUid) is present");
                }
                ((List) this.adminToProfileMap.get(Integer.valueOf(i))).add(build);
            }
            i3 = 0;
        }
        if (z) {
            Log.d("NetworkAnalytics:ConfigStore", "addProfileToDatabase: returnValue = " + i3);
        }
        return i3;
    }

    public int validateJsonContent(JSONObject jSONObject) {
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
                Log.d("NetworkAnalytics:ConfigStore", "validateJsonContent: Profile name already exists " + optString);
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

    public List getClientProfileNames(int i) {
        if (this.adminToProfileMap.get(Integer.valueOf(i)) == null || ((List) this.adminToProfileMap.get(Integer.valueOf(i))).size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((List) this.adminToProfileMap.get(Integer.valueOf(i))).iterator();
        while (it.hasNext()) {
            arrayList.add(((NAPConfigProfile) it.next()).getProfileName());
        }
        return arrayList;
    }

    public List getClientProfiles(int i, int i2) {
        if (this.adminToProfileMap.get(Integer.valueOf(i)) == null || ((List) this.adminToProfileMap.get(Integer.valueOf(i))).size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (NAPConfigProfile nAPConfigProfile : (List) this.adminToProfileMap.get(Integer.valueOf(i))) {
            if (nAPConfigProfile.getOpUserId() == i2) {
                arrayList.add(nAPConfigProfile.getJsonString());
            }
        }
        return arrayList;
    }

    public List getClientProfiles(String str, int i) {
        ArrayList arrayList = this.profileMap.keySet().size() > 0 ? new ArrayList() : null;
        Iterator it = this.profileMap.keySet().iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.getPackageName().equals(str) && (i == 0 || i == nAPConfigProfile.getOpUserId())) {
                NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(NetworkAnalyticsService.getTransformedVendorName(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId()));
                arrayList.add(new Profile(nAPProfileActivation == null ? 0 : nAPProfileActivation.getActivationState(), nAPConfigProfile.getJsonString(), nAPConfigProfile.getOpUserId()));
            }
        }
        return arrayList;
    }

    public int removeProfileFromDatabase(int i, String str) {
        if (this.profileMap.get(str) == null) {
            Log.d("NetworkAnalytics:ConfigStore", "removeProfileFromDatabase: Profile name not found " + str);
            return -4;
        }
        boolean deleteDataByFields = this.mStorageHelper.deleteDataByFields("NAPProfileTable", new String[]{"profileName"}, new String[]{str});
        if (DBG) {
            Log.d("NetworkAnalytics:ConfigStore", "removeProfileFromDatabase: deleteDataByFields? " + deleteDataByFields);
        }
        if (!deleteDataByFields) {
            return -1;
        }
        updateTablesForProfileRemoval(i, str);
        return 0;
    }

    public void updateTablesForProfileRemoval(int i, String str) {
        List list = (List) this.adminToProfileMap.get(Integer.valueOf(i));
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) list.get(i2);
            if (nAPConfigProfile.getProfileName() == null || !nAPConfigProfile.getProfileName().equals(str)) {
                i2++;
            } else {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "updateTablesForProfileRemoval: profile found " + nAPConfigProfile.getProfileName());
                }
                ((List) this.adminToProfileMap.get(Integer.valueOf(i))).remove(i2);
            }
        }
        this.profileMap.remove(str);
        for (String str2 : this.activationMap.keySet()) {
            if (NetworkAnalyticsService.getVendorNameFromTransformedName(str2).equals(str)) {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "updateTablesForProfileRemoval: removing from activation map " + str2);
                }
                this.activationMap.remove(str2);
            }
        }
    }

    public List getAllProfilesForUser(int i) {
        Set keySet = this.profileMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.getOpUserId() == i) {
                arrayList.add(nAPConfigProfile);
            }
        }
        return arrayList;
    }

    public int activateProfile(int i, String str, int i2, int i3) {
        NAPConfigProfile nAPConfigProfile;
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
        String transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(str, i2);
        ContentValues contentValues = new ContentValues();
        contentValues.put("activeState", Integer.valueOf(i3));
        if (1 == i3) {
            contentValues.put("flowType", Integer.valueOf(nAPConfigProfile.getFlowTypeForProfile()));
        } else if (i3 == 0) {
            contentValues.put("flowType", (Integer) (-999));
        }
        if (1 == i3) {
            contentValues.put("intervalValue", Integer.valueOf(nAPConfigProfile.getIntervalValueForProfile()));
        } else if (i3 == 0) {
            contentValues.put("intervalValue", (Integer) (-999));
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("profileName", str);
        contentValues2.put("op_userid", Integer.valueOf(i2));
        contentValues2.put("adminUid", Integer.valueOf(i));
        i4 = this.mStorageHelper.updateFields("NAPProfileTable", contentValues, contentValues2);
        if (DBG) {
            Log.d("NetworkAnalytics:ConfigStore", "activateProfile: returnStatus = " + i4);
        }
        if (i4 > 0) {
            if (this.activationMap.containsKey(transformedVendorName)) {
                NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(transformedVendorName);
                nAPProfileActivation.setActivationState(i3);
                if (1 == i3) {
                    nAPProfileActivation.setActivationFlowType(nAPConfigProfile.getFlowTypeForProfile());
                } else if (i3 == 0) {
                    nAPProfileActivation.setActivationFlowType(-999);
                }
                if (1 == i3) {
                    nAPProfileActivation.setActivationIntervalValue(nAPConfigProfile.getIntervalValueForProfile());
                } else if (i3 == 0) {
                    nAPProfileActivation.setActivationIntervalValue(-999);
                }
            } else {
                NAPProfileActivation nAPProfileActivation2 = new NAPProfileActivation((NAPConfigProfile) this.profileMap.get(str), i);
                nAPProfileActivation2.setActivationState(i3);
                if (1 == i3) {
                    nAPProfileActivation2.setActivationFlowType(nAPConfigProfile.getFlowTypeForProfile());
                } else if (i3 == 0) {
                    nAPProfileActivation2.setActivationFlowType(-999);
                }
                if (1 == i3) {
                    nAPProfileActivation2.setActivationIntervalValue(nAPConfigProfile.getIntervalValueForProfile());
                } else if (i3 == 0) {
                    nAPProfileActivation2.setActivationIntervalValue(-999);
                }
                this.activationMap.put(transformedVendorName, nAPProfileActivation2);
            }
            i4 = 0;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:ConfigStore", "activateProfile: final returnStatus = " + i4);
        }
        return i4;
    }

    public NAPConfigProfile getProfileforName(String str) {
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

    public int updateBindUserIdForProfile(String str, int i, int i2) {
        if (DBG) {
            Log.d("NetworkAnalytics:ConfigStore", "updateBindUserIdForProfile updating binduserid for profile:" + str + " to:" + i2);
        }
        int i3 = -1;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("client_userid", Integer.valueOf(i2));
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("profileName", str);
            contentValues2.put("op_userid", Integer.valueOf(i));
            i3 = this.mStorageHelper.updateFields("NAPProfileTable", contentValues, contentValues2);
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
            nAPConfigProfile.setUserId(i2);
        }
        if (DBG) {
            Log.d("NetworkAnalytics:ConfigStore", "updateBindUserIdForProfile returnValue:" + i3);
        }
        return i3;
    }

    public List getAllProfilesForPackage(String str) {
        Set keySet = this.profileMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.getPackageName().equals(str)) {
                arrayList.add(nAPConfigProfile);
            }
        }
        return arrayList;
    }

    public List getAllProfilesForPackageinUser(String str, int i) {
        Set keySet = this.profileMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.getPackageName().equals(str) && nAPConfigProfile.getOpUserId() == i) {
                arrayList.add(nAPConfigProfile);
            }
        }
        return arrayList;
    }

    public NAPProfileActivation getActiveStateForName(String str) {
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

    public List getAllActiveKeysForProfile(String str) {
        NAPProfileActivation nAPProfileActivation;
        Set<String> keySet = this.activationMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : keySet) {
            if (NetworkAnalyticsService.getVendorNameFromTransformedName(str2).equals(str) && (nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(str2)) != null && 1 == nAPProfileActivation.getActivationState()) {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "getAllActiveKeysForProfile: Adding active key for profile" + str2);
                }
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public List getAllActiveKeysForPackage(String str) {
        Set<String> keySet = this.activationMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : keySet) {
            NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(str2);
            if (nAPProfileActivation != null && nAPProfileActivation.getProfile().getPackageName().equals(str) && 1 == nAPProfileActivation.getActivationState()) {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "getAllActiveKeysForPackage: Adding active key for profile" + str2);
                }
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public Set getSetActivatedProfiles() {
        return this.activationMap.keySet();
    }

    public boolean isActivatedPackage(String str) {
        NAPConfigProfile profile;
        Set keySet = this.activationMap.keySet();
        if (keySet != null && keySet.size() > 0) {
            Iterator it = keySet.iterator();
            while (it.hasNext()) {
                NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get((String) it.next());
                if (nAPProfileActivation != null && (profile = nAPProfileActivation.getProfile()) != null && profile.getPackageName().equals(str) && 1 == nAPProfileActivation.getActivationState()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAddedPackage(String str) {
        Set keySet = this.profileMap.keySet();
        if (keySet != null && keySet.size() > 0) {
            Iterator it = keySet.iterator();
            while (it.hasNext()) {
                NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
                if (nAPConfigProfile != null && nAPConfigProfile.getPackageName().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getUserIdLocationOfPackage(String str) {
        NAPConfigProfile profile;
        Set keySet = this.activationMap.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return -1;
        }
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            NAPProfileActivation nAPProfileActivation = (NAPProfileActivation) this.activationMap.get((String) it.next());
            if (nAPProfileActivation != null && (profile = nAPProfileActivation.getProfile()) != null && profile.getPackageName().equals(str) && 1 == nAPProfileActivation.getActivationState()) {
                return profile.getUserId();
            }
        }
        return -1;
    }

    public boolean validatePkgSignForAllProfiles(String str, String str2) {
        Iterator it = this.profileMap.keySet().iterator();
        while (it.hasNext()) {
            NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get((String) it.next());
            if (nAPConfigProfile != null && nAPConfigProfile.getPackageName().equals(str) && !nAPConfigProfile.getPackageSignature().equals(str2)) {
                if (!DBG) {
                    return false;
                }
                Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures Dont match!!! ");
                Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures in profile");
                Log.d("NetworkAnalytics:ConfigStore", nAPConfigProfile.getPackageSignature());
                Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures in parameter ");
                Log.d("NetworkAnalytics:ConfigStore", str2);
                return false;
            }
        }
        if (!DBG) {
            return true;
        }
        Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Valid package signature for " + str);
        return true;
    }

    public boolean validatePkgSignForSingleProfile(String str, String str2, String str3) {
        NAPConfigProfile nAPConfigProfile;
        for (String str4 : this.profileMap.keySet()) {
            if (str4.equals(str3) && (nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str4)) != null && nAPConfigProfile.getPackageName().equals(str) && !nAPConfigProfile.getPackageSignature().equals(str2)) {
                if (!DBG) {
                    return false;
                }
                Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures Dont match!!! ");
                Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures in profile");
                Log.d("NetworkAnalytics:ConfigStore", nAPConfigProfile.getPackageSignature());
                Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Signatures in parameter ");
                Log.d("NetworkAnalytics:ConfigStore", str2);
                return false;
            }
        }
        if (!DBG) {
            return true;
        }
        Log.d("NetworkAnalytics:ConfigStore", "validatePkgSignForAllProfiles: Valid package signature for " + str);
        return true;
    }

    public int doesAdminOwnProfile(int i, String str) {
        NAPConfigProfile nAPConfigProfile = (NAPConfigProfile) this.profileMap.get(str);
        if (nAPConfigProfile == null) {
            Log.d("NetworkAnalytics:ConfigStore", "doesAdminOwnProfile: Profile name not found " + str);
            return -3;
        }
        if (nAPConfigProfile.getAdminUid() == i) {
            return 0;
        }
        Log.d("NetworkAnalytics:ConfigStore", "doesAdminOwnProfile: Profile " + str + " does not belong to adminUid " + i);
        return -7;
    }

    public int isProfileActivatedForUser(String str, int i) {
        NAPProfileActivation nAPProfileActivation;
        String transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(str, i);
        if (!this.activationMap.containsKey(transformedVendorName) || (nAPProfileActivation = (NAPProfileActivation) this.activationMap.get(transformedVendorName)) == null) {
            return 0;
        }
        return nAPProfileActivation.getActivationState();
    }

    public String getProfileObjectFromJson(String str, String str2) {
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

    public JSONObject getJSonObjectFromString(String str) {
        return new JSONObject(str).getJSONObject("NETWORK_ANALYTICS_PARAMETERS").getJSONObject("profile_attribute");
    }

    public final void initializeProfileTable() {
        try {
            ArrayList dataByFields = this.mStorageHelper.getDataByFields("NAPProfileTable", null, null, null);
            if (dataByFields != null && dataByFields.size() > 0) {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "initializeProfileTable: Initializing tables. Cursor present.");
                }
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    String asString = contentValues.getAsString("profileName");
                    String asString2 = contentValues.getAsString("pkgName");
                    String asString3 = contentValues.getAsString("pkgSign");
                    String asString4 = contentValues.getAsString("jsondata");
                    int intValue = contentValues.getAsInteger("client_userid").intValue();
                    int intValue2 = contentValues.getAsInteger("op_userid").intValue();
                    int intValue3 = contentValues.getAsInteger("adminUid").intValue();
                    int intValue4 = contentValues.getAsInteger("flags").intValue();
                    int intValue5 = contentValues.getAsInteger("flowType").intValue();
                    int intValue6 = contentValues.getAsInteger("intervalValue").intValue();
                    Builder builder = new Builder(asString);
                    builder.opUserId(intValue2);
                    builder.jsonString(asString4);
                    builder.packageName(asString2);
                    builder.packageSignature(asString3);
                    builder.adminUid(intValue3);
                    builder.flags(intValue4);
                    builder.userId(intValue);
                    builder.flowTypeForProfile(intValue5);
                    builder.intervalValueForProfile(intValue6);
                    NAPConfigProfile build = builder.build();
                    if (DBG) {
                        Log.d("NetworkAnalytics:ConfigStore", "initializeProfileTable: Config Profile = " + build);
                    }
                    this.profileMap.put(asString, build);
                    if (this.adminToProfileMap.get(Integer.valueOf(intValue3)) == null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(build);
                        this.adminToProfileMap.putIfAbsent(Integer.valueOf(intValue3), arrayList);
                    } else {
                        ((List) this.adminToProfileMap.get(Integer.valueOf(intValue3))).add(build);
                    }
                }
            }
            if (DBG) {
                Log.d("NetworkAnalytics:ConfigStore", "initializeProfileTable: Exiting.");
            }
        } catch (Exception e) {
            Log.e("NetworkAnalytics:ConfigStore", "Error while initialize profile table" + Log.getStackTraceString(e));
        }
    }

    public final void initializeActivationTable() {
        try {
            ArrayList dataByFields = this.mStorageHelper.getDataByFields("NAPProfileTable", null, null, null);
            if (dataByFields != null && dataByFields.size() > 0) {
                if (DBG) {
                    Log.d("NetworkAnalytics:ConfigStore", "initializeActivationTable: Initializing tables. Cursor present.");
                }
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    String asString = contentValues.getAsString("profileName");
                    int intValue = contentValues.getAsInteger("op_userid").intValue();
                    int intValue2 = contentValues.getAsInteger("adminUid").intValue();
                    int intValue3 = contentValues.getAsInteger("activeState").intValue();
                    int intValue4 = contentValues.getAsInteger("flowType").intValue();
                    int intValue5 = contentValues.getAsInteger("intervalValue").intValue();
                    if (DBG) {
                        Log.d("NetworkAnalytics:ConfigStore", "initializeActivationTable: Config Profile = " + this.profileMap.get(asString));
                    }
                    NAPProfileActivation nAPProfileActivation = new NAPProfileActivation((NAPConfigProfile) this.profileMap.get(asString), intValue2);
                    nAPProfileActivation.setActivationState(intValue3);
                    nAPProfileActivation.setActivationFlowType(intValue4);
                    nAPProfileActivation.setActivationIntervalValue(intValue5);
                    this.activationMap.put(NetworkAnalyticsService.getTransformedVendorName(asString, intValue), nAPProfileActivation);
                }
            }
            if (DBG) {
                Log.d("NetworkAnalytics:ConfigStore", "initializeActivationTable: Exiting.");
            }
        } catch (Exception e) {
            Log.e("NetworkAnalytics:ConfigStore", "Error while initialize activation table" + Log.getStackTraceString(e));
        }
    }

    public class NAPProfileActivation {
        public int activationFlowType;
        public int activationIntervalValue;
        public int activationState;
        public int adminUid;
        public NAPConfigProfile profile;

        public NAPProfileActivation(NAPConfigProfile nAPConfigProfile, int i) {
            this.profile = nAPConfigProfile;
            this.adminUid = i;
        }

        public NAPConfigProfile getProfile() {
            return this.profile;
        }

        public int getActivationState() {
            return this.activationState;
        }

        public int getAdminUid() {
            return this.adminUid;
        }

        public void setActivationState(int i) {
            this.activationState = i;
        }

        public int getActivationFlowType() {
            return this.activationFlowType;
        }

        public void setActivationFlowType(int i) {
            this.activationFlowType = i;
        }

        public int getActivationIntervalValue() {
            return this.activationIntervalValue;
        }

        public void setActivationIntervalValue(int i) {
            this.activationIntervalValue = i;
        }
    }

    public class NAPConfigProfile {
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

        public String getProfileName() {
            return this.profileName;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public String getPackageSignature() {
            return this.packageSignature;
        }

        public String getJsonString() {
            return this.jsonString;
        }

        public int getAdminUid() {
            return this.adminUid;
        }

        public int getUserId() {
            return this.userId;
        }

        public int getOpUserId() {
            return this.opUserId;
        }

        public void setUserId(int i) {
            this.userId = i;
        }

        public int getFlags() {
            return this.flags;
        }

        public int getFlowTypeForProfile() {
            return this.flowTypeForProfile;
        }

        public void setFlowTypeForProfile(int i) {
            this.flowTypeForProfile = i;
        }

        public int getIntervalValueForProfile() {
            return this.intervalValueForProfile;
        }

        public void setIntervalValueForProfile(int i) {
            this.intervalValueForProfile = i;
        }

        public String toString() {
            return "profileName=" + this.profileName + ",packageName=" + this.packageName + ",adminUid=" + this.adminUid + ",flags=" + this.flags + ",userId=" + this.userId + ",opUserId=" + this.opUserId;
        }
    }

    public class Builder {
        public int adminUid;
        public int flags;
        public int flowTypeForProfile;
        public int intervalValueForProfile;
        public String jsonString;
        public int opUserId;
        public String packageName;
        public String packageSignature;
        public String profileName;
        public int userId;

        public Builder(String str) {
            this.profileName = str;
        }

        public Builder opUserId(int i) {
            this.opUserId = i;
            return this;
        }

        public Builder jsonString(String str) {
            this.jsonString = str;
            return this;
        }

        public Builder packageName(String str) {
            this.packageName = str;
            return this;
        }

        public Builder packageSignature(String str) {
            this.packageSignature = str;
            return this;
        }

        public Builder adminUid(int i) {
            this.adminUid = i;
            return this;
        }

        public Builder flags(int i) {
            this.flags = i;
            return this;
        }

        public Builder userId(int i) {
            this.userId = i;
            return this;
        }

        public Builder flowTypeForProfile(int i) {
            this.flowTypeForProfile = i;
            return this;
        }

        public Builder intervalValueForProfile(int i) {
            this.intervalValueForProfile = i;
            return this;
        }

        public NAPConfigProfile build() {
            return NetworkAnalyticsConfigStore.this.new NAPConfigProfile(this);
        }
    }
}
