package com.android.server.enterprise.nap;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.firewall.DomainFilterNapCommon;
import com.android.server.enterprise.nap.NetworkAnalyticsConfigStore;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.nap.INetworkAnalytics;
import com.samsung.android.knox.net.nap.IStatusCallback;
import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NetworkAnalyticsService extends INetworkAnalytics.Stub implements EnterpriseServiceCallback {
    public static final boolean DBG = Debug.semIsProductDev();
    public static DeviceRootKeyServiceManager mDeviceRootKeyServiceManager;
    public static String mDeviceUniqueId;
    public NetworkAnalyticsConfigStore mConfigStore;
    public NetworkAnalyticsConnectionManager mConnectionManager;
    public Context mContext;
    public NetworkAnalyticsDataDelivery mDataDelivery;
    public NetworkAnalyticsDriver mDriver;
    public EnterpriseDeviceManager mEDM;
    public NapHandler mHandler;
    public HandlerThread mHandlerThread;
    public NetworkAnalyticsStorageHelper mStorageHelper;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public NetworkAnalyticsService() {
        this.mContext = null;
        this.mEDM = null;
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mConfigStore = null;
        this.mConnectionManager = null;
        this.mStorageHelper = null;
        this.mDataDelivery = null;
        this.mDriver = null;
    }

    public NetworkAnalyticsService(Context context) {
        this.mContext = null;
        this.mEDM = null;
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mConfigStore = null;
        this.mConnectionManager = null;
        this.mStorageHelper = null;
        this.mDataDelivery = null;
        this.mDriver = null;
        Log.d("NetworkAnalytics:Service", "Adding network analytics service.");
        this.mContext = context;
        try {
            Log.d("NetworkAnalytics:Service", "Network Platform analytics Service is going to be added to the ServiceManager list ");
            NetworkAnalyticsStorageHelper networkAnalyticsStorageHelper = NetworkAnalyticsStorageHelper.getInstance(this.mContext);
            this.mStorageHelper = networkAnalyticsStorageHelper;
            this.mConfigStore = NetworkAnalyticsConfigStore.getInstance(networkAnalyticsStorageHelper);
            this.mConnectionManager = NetworkAnalyticsConnectionManager.getInstance();
            NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery = NetworkAnalyticsDataDelivery.getInstance();
            this.mDataDelivery = networkAnalyticsDataDelivery;
            this.mDriver = NetworkAnalyticsDriver.getInstance(this.mConnectionManager, networkAnalyticsDataDelivery);
            initializeHandlerThread();
            initializeNapData();
            if (this.mHandler == null || this.mHandlerThread == null) {
                return;
            }
            sendMessageToHandler(4, 0, 0, null);
        } catch (Throwable th) {
            Log.e("NetworkAnalytics:Service", "Error occured while trying to start NPA as a system service", th);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        if (this.mHandler == null || this.mHandlerThread == null) {
            return;
        }
        sendMessageToHandler(8, i, 0, null);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        if (this.mHandler == null || this.mHandlerThread == null) {
            return;
        }
        sendMessageToHandler(9, i, 0, null);
    }

    public int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) {
        int i;
        JSONObject jSonObjectFromString;
        int validateJsonContent;
        List clientProfileNames;
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile API caller is " + contextInfo.mCallerUid);
        }
        ContextInfo enforceNetworkAnalyticsPermission = enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile API caller with updated contextInfo is " + enforceNetworkAnalyticsPermission.mCallerUid);
        }
        if (enforceNetworkAnalyticsPermission == null || str == null) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile: Invalid parameters.");
            return -4;
        }
        if (z) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile: allowUserId: " + enforceNetworkAnalyticsPermission.mContainerId + " profile:" + str);
        }
        try {
            jSonObjectFromString = this.mConfigStore.getJSonObjectFromString(str);
            validateJsonContent = this.mConfigStore.validateJsonContent(jSonObjectFromString);
        } catch (JSONException e) {
            Log.e("NetworkAnalytics:Service", "registerNetworkMonitorProfile: JSON Parsing Exception", e);
            i = -2;
        } catch (Exception e2) {
            Log.e("NetworkAnalytics:Service", "registerNetworkMonitorProfile: Exception", e2);
            i = -1;
        }
        if (validateJsonContent < 0) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile: JSON validation failed:" + validateJsonContent);
            return validateJsonContent;
        }
        i = this.mConfigStore.addProfileToDatabase(enforceNetworkAnalyticsPermission.mCallerUid, jSonObjectFromString, str, enforceNetworkAnalyticsPermission.mContainerId);
        if (i < 0) {
            Log.d("NetworkAnalytics:Service", "registerNetworkMonitorProfile: Add profile to database failed:" + i);
            return i;
        }
        if (i == 0 && (clientProfileNames = this.mConfigStore.getClientProfileNames(enforceNetworkAnalyticsPermission.mCallerUid)) != null && clientProfileNames.size() == 1) {
            if (z) {
                Log.d("NetworkAnalytics:Service", "The Knox NAP feature has been enabled");
            }
            setFeatureProperty(true);
        }
        this.mConfigStore.getProfileObjectFromJson("profile_name", str);
        if (i == 0) {
            if (enforceNetworkAnalyticsPermission.mContainerId != 0) {
                constructObjectForBroadcast(0, 0, str);
            }
            constructObjectForBroadcast(0, enforceNetworkAnalyticsPermission.mContainerId, str);
        }
        return i;
    }

    public static int versionCompare(String str, String str2) {
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int max = Math.max(split.length, split2.length);
            int i = 0;
            while (i < max) {
                int parseInt = i < split.length ? Integer.parseInt(split[i]) : 0;
                int parseInt2 = i < split2.length ? Integer.parseInt(split2[i]) : 0;
                if (parseInt > parseInt2) {
                    return 1;
                }
                if (parseInt2 > parseInt) {
                    return -1;
                }
                i++;
            }
            return 0;
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "handleNAPClientCall: Exception in Comparing Versions", e);
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0326 A[Catch: all -> 0x0392, Exception -> 0x0394, TRY_LEAVE, TryCatch #1 {Exception -> 0x0394, blocks: (B:5:0x000a, B:10:0x001f, B:12:0x0025, B:16:0x0045, B:18:0x004b, B:20:0x0051, B:21:0x0055, B:23:0x005b, B:189:0x0063, B:25:0x0086, B:28:0x0092, B:38:0x00b6, B:179:0x00bc, B:42:0x00d2, B:45:0x00d8, B:174:0x00e7, B:51:0x00f0, B:55:0x00fe, B:60:0x010e, B:67:0x011d, B:69:0x0127, B:72:0x0159, B:75:0x0162, B:77:0x016a, B:79:0x0170, B:80:0x0174, B:82:0x017a, B:85:0x0189, B:88:0x0190, B:90:0x0196, B:93:0x019c, B:104:0x01a5, B:106:0x01b5, B:108:0x01c9, B:153:0x01d2, B:110:0x0224, B:149:0x022c, B:112:0x0280, B:114:0x0288, B:116:0x0298, B:125:0x0320, B:127:0x0326, B:132:0x0353, B:135:0x036c, B:137:0x02c6, B:139:0x02ce, B:141:0x02de, B:143:0x02ee, B:157:0x01f7, B:160:0x0200, B:164:0x0250, B:169:0x0276, B:199:0x037a), top: B:4:0x000a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0353 A[Catch: all -> 0x0392, Exception -> 0x0394, TRY_ENTER, TryCatch #1 {Exception -> 0x0394, blocks: (B:5:0x000a, B:10:0x001f, B:12:0x0025, B:16:0x0045, B:18:0x004b, B:20:0x0051, B:21:0x0055, B:23:0x005b, B:189:0x0063, B:25:0x0086, B:28:0x0092, B:38:0x00b6, B:179:0x00bc, B:42:0x00d2, B:45:0x00d8, B:174:0x00e7, B:51:0x00f0, B:55:0x00fe, B:60:0x010e, B:67:0x011d, B:69:0x0127, B:72:0x0159, B:75:0x0162, B:77:0x016a, B:79:0x0170, B:80:0x0174, B:82:0x017a, B:85:0x0189, B:88:0x0190, B:90:0x0196, B:93:0x019c, B:104:0x01a5, B:106:0x01b5, B:108:0x01c9, B:153:0x01d2, B:110:0x0224, B:149:0x022c, B:112:0x0280, B:114:0x0288, B:116:0x0298, B:125:0x0320, B:127:0x0326, B:132:0x0353, B:135:0x036c, B:137:0x02c6, B:139:0x02ce, B:141:0x02de, B:143:0x02ee, B:157:0x01f7, B:160:0x0200, B:164:0x0250, B:169:0x0276, B:199:0x037a), top: B:4:0x000a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x031f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized int handleNAPClientCall(String str, Bundle bundle, boolean z) {
        int i;
        int callingUid;
        int userId;
        String packageNameForUid;
        int i2;
        int i3;
        int updateBindUserIdForProfile;
        int i4 = -1;
        try {
            callingUid = Binder.getCallingUid();
            userId = UserHandle.getUserId(callingUid);
            packageNameForUid = getPackageNameForUid(callingUid);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "handleNAPClientCall: Exception in doStart:" + z, e);
            i = -1;
        }
        if (str != null && packageNameForUid != null && userId >= 0) {
            int validateNAPClient = validateNAPClient(callingUid, userId, z, str);
            if (validateNAPClient < 0) {
                Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Caller validation failed:" + validateNAPClient + " doStart:" + z);
                return validateNAPClient;
            }
            List<NetworkAnalyticsConfigStore.NAPConfigProfile> listOfProfilesForOperation = listOfProfilesForOperation(packageNameForUid, str, userId);
            if (listOfProfilesForOperation != null && listOfProfilesForOperation.size() > 0) {
                for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile : listOfProfilesForOperation) {
                    if (nAPConfigProfile == null) {
                        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Profile does not exist for name: " + str);
                        i2 = -3;
                        if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                            return -3;
                        }
                    } else if (!nAPConfigProfile.getPackageName().equals(packageNameForUid)) {
                        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: NAP client activating un-owned profile: " + str);
                        if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                            return -18;
                        }
                        validateNAPClient = -18;
                    } else if (z && !isAllowedRecordingForUsers(nAPConfigProfile, userId)) {
                        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Client installed in wrong user trying to collect data");
                        i2 = -17;
                        if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                            return -17;
                        }
                    } else {
                        if (!z && nAPConfigProfile.getUserId() != userId) {
                            Log.d("NetworkAnalytics:Service", "handleNAPClientCall: NAP client failed to stop the connection since not started by same user");
                            return -18;
                        }
                        if (z) {
                            if (bundle == null) {
                                Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Client trying to collect data with null bundle flow information");
                                return -4;
                            }
                            int i5 = bundle.getInt("record_type", i4);
                            if (i5 >= 0 && i5 <= 2) {
                                int i6 = bundle.getInt("interval_value", -999);
                                if (i6 != -999 && (i6 < 60 || i6 > 14400)) {
                                    Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Client trying to collect data with Invalid interval information");
                                    return -4;
                                }
                                if (i6 != -999 && i6 >= 60 && i6 <= 14400 && EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() != null) {
                                    String internalVersion = EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3.getInternalVersion();
                                    String internalVersion2 = EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion();
                                    Log.d("NetworkAnalytics:Service", "handleNAPClientCall: intermediate flows : current knox version : " + internalVersion2 + " supported knox version : " + internalVersion);
                                    if (versionCompare(internalVersion2, internalVersion) < 0) {
                                        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Client trying to collect data not supported in current version");
                                        return -4;
                                    }
                                }
                                Set setActivatedProfiles = this.mConfigStore.getSetActivatedProfiles();
                                if (setActivatedProfiles != null && setActivatedProfiles.size() > 0) {
                                    Iterator it = setActivatedProfiles.iterator();
                                    while (it.hasNext()) {
                                        NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName((String) it.next());
                                        if (activeStateForName != null && 1 == activeStateForName.getActivationState() && (activeStateForName.getActivationFlowType() != i5 || activeStateForName.getActivationIntervalValue() != i6)) {
                                            Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Client trying to collect data with wrong flow or interval");
                                            return -4;
                                        }
                                    }
                                }
                                nAPConfigProfile.setFlowTypeForProfile(i5);
                                nAPConfigProfile.setIntervalValueForProfile(i6);
                                if (EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() == null) {
                                    Log.d("NetworkAnalytics:Service", "handleNAPClientCall: file descriptor failed for profile:" + nAPConfigProfile.toString() + " doStart:" + z);
                                    return -19;
                                }
                                if (versionCompare(EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion(), EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3.getInternalVersion()) < 0) {
                                    i3 = 0;
                                    int openCharDevice = this.mDriver.openCharDevice(0);
                                    if (openCharDevice < 0) {
                                        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: file descriptor failed for profile:" + nAPConfigProfile.toString() + " doStart:" + z);
                                        return openCharDevice;
                                    }
                                } else {
                                    i3 = 0;
                                    int openCharDevice2 = this.mDriver.openCharDevice(1);
                                    if (openCharDevice2 < 0) {
                                        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: file descriptor failed for profile:" + nAPConfigProfile.toString() + " doStart:" + z);
                                        return openCharDevice2;
                                    }
                                }
                                int checkNcmVersionMismatch = this.mDriver.checkNcmVersionMismatch();
                                if (checkNcmVersionMismatch < 0) {
                                    Log.d("NetworkAnalytics:Service", "handleNAPClientCall: ncm version match failed for profile:" + nAPConfigProfile.toString() + " doStart:" + z);
                                    return checkNcmVersionMismatch;
                                }
                            }
                            Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Client trying to collect data with Invalid flow information");
                            return -4;
                        }
                        i3 = 0;
                        if (nAPConfigProfile.getUserId() == -999) {
                            updateBindUserIdForProfile = this.mConfigStore.updateBindUserIdForProfile(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId(), userId);
                            if (updateBindUserIdForProfile < 0) {
                                Log.d("NetworkAnalytics:Service", "handleNAPClientCall: bind userId updation failed for profile:" + nAPConfigProfile.toString() + " doStart:" + z);
                                if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                                    return updateBindUserIdForProfile;
                                }
                                validateNAPClient = updateBindUserIdForProfile;
                                i4 = -1;
                            }
                            updateBindUserIdForProfile = activateProfile(nAPConfigProfile, !z ? 1 : i3);
                            if (updateBindUserIdForProfile >= 0) {
                                Log.d("NetworkAnalytics:Service", "handleNAPClientCall: activation failed for profile:" + nAPConfigProfile.toString() + " doStart:" + z);
                                if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                                    return updateBindUserIdForProfile;
                                }
                            } else {
                                AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
                                asyncHandlerObject.profileName = nAPConfigProfile.getProfileName();
                                asyncHandlerObject.packageName = nAPConfigProfile.getPackageName();
                                asyncHandlerObject.userId = userId;
                                asyncHandlerObject.callback = null;
                                if (z) {
                                    i3 = 1;
                                }
                                sendMessageToHandler(1, i3, nAPConfigProfile.getOpUserId(), asyncHandlerObject);
                            }
                            validateNAPClient = updateBindUserIdForProfile;
                            i4 = -1;
                        } else {
                            if (nAPConfigProfile.getUserId() != -999 && this.mConfigStore.isProfileActivatedForUser(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId()) == 0 && (updateBindUserIdForProfile = this.mConfigStore.updateBindUserIdForProfile(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId(), userId)) < 0) {
                                Log.d("NetworkAnalytics:Service", "handleNAPClientCall: bind userId updation failed for profile after default check:" + nAPConfigProfile.toString() + " doStart:" + z);
                                if (!"ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str)) {
                                    return updateBindUserIdForProfile;
                                }
                                validateNAPClient = updateBindUserIdForProfile;
                                i4 = -1;
                            }
                            updateBindUserIdForProfile = activateProfile(nAPConfigProfile, !z ? 1 : i3);
                            if (updateBindUserIdForProfile >= 0) {
                            }
                            validateNAPClient = updateBindUserIdForProfile;
                            i4 = -1;
                        }
                    }
                    validateNAPClient = i2;
                }
            }
            i = validateNAPClient;
            return i;
        }
        Log.d("NetworkAnalytics:Service", "handleNAPClientCall: Invalid parameters. doStart:" + z);
        return -4;
    }

    public final List listOfProfilesForOperation(String str, String str2, int i) {
        if (str2 == null) {
            return null;
        }
        if ("ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str2)) {
            if (i == 0) {
                return this.mConfigStore.getAllProfilesForPackage(str);
            }
            return this.mConfigStore.getAllProfilesForPackageinUser(str, i);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mConfigStore.getProfileforName(str2));
        return arrayList;
    }

    public List getNetworkMonitorProfiles(ContextInfo contextInfo) {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:Service", "getNetworkMonitorProfiles API caller is " + contextInfo.mCallerUid);
        }
        ContextInfo enforceNetworkAnalyticsPermission = enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            Log.d("NetworkAnalytics:Service", "getNetworkMonitorProfiles API caller with updated contextInfo is " + enforceNetworkAnalyticsPermission.mCallerUid);
        }
        try {
            return this.mConfigStore.getClientProfiles(enforceNetworkAnalyticsPermission.mCallerUid, enforceNetworkAnalyticsPermission.mContainerId);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "Get NVM client profiles. Exception", e);
            return null;
        }
    }

    public List getProfiles(ContextInfo contextInfo) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String packageNameForUid = getPackageNameForUid(callingUid);
        if (packageNameForUid == null) {
            Log.d("NetworkAnalytics:Service", "getProfiles: NAP client caller cannot be validated. PackageName null");
            return null;
        }
        try {
            return this.mConfigStore.getClientProfiles(packageNameForUid, userId);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "Get NVM client profiles. Exception", e);
            return null;
        }
    }

    public int isProfileActivatedForUser(ContextInfo contextInfo, String str) {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:Service", "isProfileActivatedForUser API caller is " + contextInfo.mCallerUid);
        }
        ContextInfo enforceNetworkAnalyticsPermission = enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            Log.d("NetworkAnalytics:Service", "isProfileActivatedForUser API caller with updated contextInfo is " + enforceNetworkAnalyticsPermission.mCallerUid);
        }
        int i = enforceNetworkAnalyticsPermission.mCallerUid;
        if (i <= 0 || str == null) {
            Log.d("NetworkAnalytics:Service", "isProfileActivatedForUser: Invalid parameters");
            return -4;
        }
        int doesAdminOwnProfile = this.mConfigStore.doesAdminOwnProfile(i, str);
        if (doesAdminOwnProfile < 0) {
            Log.d("NetworkAnalytics:Service", "isProfileActivatedForUser: Profile " + str + "is absent or does not belong to adminUid " + enforceNetworkAnalyticsPermission.mCallerUid + " return=" + doesAdminOwnProfile);
            return doesAdminOwnProfile;
        }
        try {
            return this.mConfigStore.isProfileActivatedForUser(str, enforceNetworkAnalyticsPermission.mContainerId);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "isProfileActivatedForUser: Remove profile to database falied. Exception", e);
            return -1;
        }
    }

    public int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile API caller is " + contextInfo.mCallerUid);
        }
        ContextInfo enforceNetworkAnalyticsPermission = enforceNetworkAnalyticsPermission(contextInfo);
        if (z) {
            Log.d("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile API caller with updated contextInfo is " + enforceNetworkAnalyticsPermission.mCallerUid);
        }
        return unregisterNetworkMonitorProfile(enforceNetworkAnalyticsPermission.mCallerUid, str);
    }

    public synchronized String getNPAVersion() {
        String str;
        try {
            if (this.mDriver.ncmVersion == null && EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() != null) {
                String internalVersion = EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3.getInternalVersion();
                String internalVersion2 = EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion();
                Log.d("TAG", "supportedVersion = " + internalVersion + "currentVersion  = " + internalVersion2);
                if (versionCompare(internalVersion2, internalVersion) < 0) {
                    int openCharDevice = this.mDriver.openCharDevice(0);
                    if (openCharDevice < 0) {
                        Log.d("NetworkAnalytics:Service", "getNPAVersion: Failed to open char device : " + openCharDevice);
                        return Integer.toString(openCharDevice);
                    }
                } else {
                    int openCharDevice2 = this.mDriver.openCharDevice(1);
                    if (openCharDevice2 < 0) {
                        Log.d("NetworkAnalytics:Service", "getNPAVersion: Failed to open char device : " + openCharDevice2);
                        return Integer.toString(openCharDevice2);
                    }
                }
            }
            str = EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() + "_0_" + this.mDriver.ncmVersion;
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "getNPAVersion Exception", e);
            str = null;
        }
        return str;
    }

    public final boolean isAllowedRecordingForUsers(NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile, int i) {
        if (i == 0) {
            Log.d("NetworkAnalytics:Service", "isAllowedRecordingForUsers: App is installed in User 0. Can record for any user.");
            return true;
        }
        if (DBG) {
            StringBuilder sb = new StringBuilder();
            sb.append("isAllowedRecordingForUsers: App allowed to record profile? ");
            sb.append(nAPConfigProfile.getOpUserId() == i);
            Log.d("NetworkAnalytics:Service", sb.toString());
        }
        return nAPConfigProfile.getOpUserId() == i;
    }

    public final int activateProfile(NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile, int i) {
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "Called activateProfile: " + nAPConfigProfile.getProfileName());
        }
        try {
            if (this.mConfigStore.isProfileActivatedForUser(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId()) == i) {
                Log.d("NetworkAnalytics:Service", "activateProfile:Profile is already in the activated state for the user.");
                return -8;
            }
            int activateProfile = this.mConfigStore.activateProfile(nAPConfigProfile.getAdminUid(), nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId(), i);
            if (activateProfile >= 0) {
                return activateProfile;
            }
            Log.d("NetworkAnalytics:Service", "Activate profile config storage returned = " + activateProfile + " for state " + i);
            return activateProfile;
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "Add profile to database falied. Exception", e);
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int unregisterNetworkMonitorProfile(int i, String str) {
        int i2;
        int i3;
        String str2;
        List clientProfileNames;
        String str3 = null;
        try {
        } catch (Exception e) {
            e = e;
            i2 = 0;
        }
        if (i <= 0 || str == null) {
            Log.d("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile: Invalid parameters");
            return -4;
        }
        int doesAdminOwnProfile = this.mConfigStore.doesAdminOwnProfile(i, str);
        if (doesAdminOwnProfile < 0) {
            Log.d("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile: Profile " + str + "is absent or does not belong to adminUid " + i + " return=" + doesAdminOwnProfile);
            return doesAdminOwnProfile;
        }
        NetworkAnalyticsConfigStore.NAPConfigProfile profileforName = this.mConfigStore.getProfileforName(str);
        if (profileforName != null) {
            i2 = profileforName.getOpUserId();
            try {
                str2 = profileforName.getJsonString();
            } catch (Exception e2) {
                e = e2;
                Log.e("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile. Exception", e);
                i3 = -1;
                str2 = str3;
                if (i3 == 0) {
                }
                return i3;
            }
        } else {
            i2 = 0;
            str2 = null;
        }
        try {
            List allActiveKeysForProfile = this.mConfigStore.getAllActiveKeysForProfile(str);
            if (allActiveKeysForProfile != null && allActiveKeysForProfile.size() > 0) {
                Iterator it = allActiveKeysForProfile.iterator();
                while (it.hasNext()) {
                    NetworkAnalyticsConfigStore.NAPConfigProfile profileforName2 = this.mConfigStore.getProfileforName(getVendorNameFromTransformedName((String) it.next()));
                    if (profileforName2 == null) {
                        Log.d("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile config profile obj is null for profile: " + str);
                        return doesAdminOwnProfile;
                    }
                    doesAdminOwnProfile = activateProfile(profileforName2, 0);
                    if (doesAdminOwnProfile < 0) {
                        Log.d("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile: deactivation failed for profile:" + profileforName2.toString());
                        return doesAdminOwnProfile;
                    }
                    AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
                    asyncHandlerObject.profileName = profileforName2.getProfileName();
                    asyncHandlerObject.packageName = profileforName2.getPackageName();
                    asyncHandlerObject.userId = profileforName2.getUserId();
                    asyncHandlerObject.callback = null;
                    sendMessageToHandler(1, 0, profileforName2.getOpUserId(), asyncHandlerObject);
                }
            }
            i3 = this.mConfigStore.removeProfileFromDatabase(i, str);
        } catch (Exception e3) {
            e = e3;
            str3 = str2;
            Log.e("NetworkAnalytics:Service", "unregisterNetworkMonitorProfile. Exception", e);
            i3 = -1;
            str2 = str3;
            if (i3 == 0) {
            }
            return i3;
        }
        if (i3 < 0) {
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "Remove profile from database falied.");
            }
            return i3;
        }
        if (i3 == 0 && ((clientProfileNames = this.mConfigStore.getClientProfileNames(i)) == null || clientProfileNames.isEmpty())) {
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "The Knox NAP feature has been disabled");
            }
            setFeatureProperty(false);
        }
        if (i3 == 0) {
            if (i2 != 0) {
                constructObjectForBroadcast(1, 0, str2);
            }
            constructObjectForBroadcast(1, i2, str2);
        }
        return i3;
    }

    public final void initializeHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("NapHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new NapHandler(this.mHandlerThread.getLooper());
    }

    public final void initializeNapData() {
        try {
            setupIntentFilter();
        } catch (Exception unused) {
            Log.e("NetworkAnalytics:Service", "Error occured while trying to ini the BroadcastReceiver");
        }
    }

    public final void sendMessageToHandler(int i, int i2, int i3, Object obj) {
        NapHandler napHandler = this.mHandler;
        if (napHandler != null) {
            this.mHandler.sendMessage(Message.obtain(napHandler, i, i2, i3, obj));
        }
    }

    public static synchronized DeviceRootKeyServiceManager getDRKService(Context context) {
        DeviceRootKeyServiceManager deviceRootKeyServiceManager;
        synchronized (NetworkAnalyticsService.class) {
            if (mDeviceRootKeyServiceManager == null) {
                mDeviceRootKeyServiceManager = new DeviceRootKeyServiceManager(context);
            }
            deviceRootKeyServiceManager = mDeviceRootKeyServiceManager;
        }
        return deviceRootKeyServiceManager;
    }

    public final String getPackageCertForPkgname(int i, String str) {
        boolean z;
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                z = DBG;
                if (z) {
                    Log.d("NetworkAnalytics:Service", "getPackageCertForPkgname: User id = " + i);
                }
                packageInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo(str, 64, i);
            } catch (NullPointerException e) {
                Log.e("NetworkAnalytics:Service", "In getPackageCertForPkgname: NullPointerException", e);
            } catch (Exception e2) {
                Log.e("NetworkAnalytics:Service", "In getPackageCertForPkgname: Exception", e2);
            }
            if (packageInfo == null) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "getPackageCertForPkgname: pkgInfo is null");
                }
                return null;
            }
            Signature[] signatureArr = packageInfo.signatures;
            int length = signatureArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Signature signature = signatureArr[i2];
                if (signature != null) {
                    str2 = signature.toCharsString();
                    break;
                }
                i2++;
            }
            return str2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int isValidAppInstalled(String str, String str2, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        PackageManagerAdapter packageManagerAdapter = PackageManagerAdapter.getInstance(this.mContext);
        if (packageManagerAdapter != null) {
            if (!packageManagerAdapter.isApplicationInstalled(str, i)) {
                Log.d("NetworkAnalytics:Service", "isValidAppInstalled: Monitor app not installed");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -15;
            }
            String packageCertForPkgname = getPackageCertForPkgname(i, str);
            if (packageCertForPkgname != null && packageCertForPkgname.length() > 0 && !str2.equals(packageCertForPkgname)) {
                Log.d("NetworkAnalytics:Service", "isValidAppInstalled: Monitor app signature is not matched.");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -13;
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 0;
    }

    public final int validateNAPClient(String str, String str2, int i) {
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "validateNAPClient");
        }
        try {
            int isValidAppInstalled = isValidAppInstalled(str, str2, i);
            if (isValidAppInstalled >= 0) {
                return 0;
            }
            Log.d("NetworkAnalytics:Service", "validateNAPClient:Valid monitor application is not installed " + isValidAppInstalled);
            return isValidAppInstalled;
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "validateNAPClient: Add profile to database failed. Exception", e);
            return -1;
        }
    }

    public final int validateNAPClient(int i, int i2, boolean z, String str) {
        String packageNameForUid = getPackageNameForUid(i);
        if (packageNameForUid == null) {
            Log.d("NetworkAnalytics:Service", "validateNAPClient: NAP client caller cannot be validated.");
            return -12;
        }
        return _validateNAPClient(packageNameForUid, i2, str);
    }

    public final int _validateNAPClient(String str, int i, String str2) {
        boolean validatePkgSignForSingleProfile;
        String packageCertForPkgname = getPackageCertForPkgname(i, str);
        if (packageCertForPkgname != null && packageCertForPkgname.length() > 0) {
            if ("ALL_REGISTERED_PROFILES_FOR_CLIENT".equals(str2)) {
                validatePkgSignForSingleProfile = this.mConfigStore.validatePkgSignForAllProfiles(str, packageCertForPkgname);
            } else {
                validatePkgSignForSingleProfile = this.mConfigStore.validatePkgSignForSingleProfile(str, packageCertForPkgname, str2);
            }
            if (!validatePkgSignForSingleProfile) {
                Log.d("NetworkAnalytics:Service", "_validateNAPClient: Package signature could not be matched for profile entry/entries");
                return -13;
            }
        }
        if (!DBG) {
            return 0;
        }
        Log.d("NetworkAnalytics:Service", "_validateNAPClient: Package validation complete.");
        return 0;
    }

    public final String getPackageNameForUid(int i) {
        String str = null;
        boolean z = false;
        for (String str2 : this.mContext.getPackageManager().getPackagesForUid(i)) {
            if (this.mConfigStore.isAddedPackage(str2)) {
                z = true;
                str = str2;
            }
        }
        if (z && str != null) {
            return str;
        }
        Log.d("NetworkAnalytics:Service", "start: Package name could not be found in activated list.");
        return null;
    }

    public final int _bindToClient(NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection) {
        boolean z = false;
        long j = 0;
        try {
            try {
                AsyncHandlerObject handlerObject = networkAnalyticsServiceConnection.getHandlerObject();
                boolean z2 = DBG;
                if (z2) {
                    Log.d("NetworkAnalytics:Service", "_bindToClient:handlerObj.profileName = " + handlerObject.toString());
                }
                INetworkAnalyticsService binderForPackage = this.mConnectionManager.getBinderForPackage(getTransformedVendorName(handlerObject.packageName, handlerObject.userId));
                IBinder asBinder = binderForPackage != null ? binderForPackage.asBinder() : null;
                if (binderForPackage != null) {
                    if (asBinder.isBinderAlive()) {
                        if (z2) {
                            Log.d("NetworkAnalytics:Service", "_bindToClient:Binder is already available for package = " + handlerObject.packageName);
                        }
                        return 0;
                    }
                    _unbindClient(handlerObject);
                }
                String str = handlerObject.packageName + "_namonitoraction";
                j = Binder.clearCallingIdentity();
                Intent intent = new Intent(str);
                List<ResolveInfo> queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 0, handlerObject.userId);
                if (queryIntentServicesAsUser.size() > 0) {
                    if (z2) {
                        Log.d("NetworkAnalytics:Service", "_bindToClient:vendorServices.size() > 0");
                    }
                    for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
                        if (resolveInfo.serviceInfo.packageName.equalsIgnoreCase(handlerObject.packageName)) {
                            boolean z3 = DBG;
                            if (z3) {
                                Log.d("NetworkAnalytics:Service", "_bindToClient:resolveInfo.serviceInfo.packageName = " + resolveInfo.serviceInfo.packageName);
                            }
                            if (z3) {
                                Log.d("NetworkAnalytics:Service", "_bindToClient:resolveInfo.serviceInfo.name = " + resolveInfo.serviceInfo.name);
                            }
                            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                            intent.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
                        }
                    }
                }
                intent.setPackage(handlerObject.packageName);
                z = this.mContext.bindServiceAsUser(intent, networkAnalyticsServiceConnection, 1, new UserHandle(handlerObject.userId));
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "_bindToClient:bindSuccess = " + z);
                }
            } catch (Exception e) {
                Log.e("NetworkAnalytics:Service", "_bindToClient:Exception", e);
            }
            return z ? 1 : -1;
        } finally {
            Binder.restoreCallingIdentity(0L);
        }
    }

    public final void _unbindClient(AsyncHandlerObject asyncHandlerObject) {
        try {
            if (asyncHandlerObject.userId == -999) {
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "_unbindClient: The profile was never activated. Returning.");
                    return;
                }
                return;
            }
            NetworkAnalyticsConfigStore.NAPConfigProfile profileforName = this.mConfigStore.getProfileforName(asyncHandlerObject.profileName);
            IStatusCallback iStatusCallback = asyncHandlerObject.callback;
            if (profileforName == null) {
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "_unbindClient: profile object null");
                }
                if (iStatusCallback != null) {
                    iStatusCallback.onCallComplete(-4);
                }
            }
            NetworkAnalyticsServiceConnection serviceConnectionForPackage = this.mConnectionManager.getServiceConnectionForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId));
            if (serviceConnectionForPackage != null) {
                this.mContext.unbindService(serviceConnectionForPackage);
                if (iStatusCallback != null) {
                    iStatusCallback.onCallComplete(0);
                }
            } else if (iStatusCallback != null) {
                iStatusCallback.onCallComplete(-11);
            }
            _cleanUpConnectionDetails(asyncHandlerObject);
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "_unbindClient: Done");
            }
        } catch (RemoteException e) {
            Log.e("NetworkAnalytics:Service", "_unbindClient: RemoteException", e);
        } catch (Exception e2) {
            Log.e("NetworkAnalytics:Service", "_unbindClient: Exception", e2);
        }
    }

    public final void _cleanUpConnectionDetails(AsyncHandlerObject asyncHandlerObject) {
        String transformedVendorName = getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId);
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "_cleanUpConnectionDetails tableKey = " + transformedVendorName);
        }
        this.mConnectionManager.removeProfileForPackage(transformedVendorName);
        this.mConnectionManager.removeBinderForPackage(transformedVendorName);
        this.mDataDelivery.removeDataRecipientsForPackage(asyncHandlerObject.packageName, asyncHandlerObject.userId);
    }

    public final boolean _activateNAPClient(AsyncHandlerObject asyncHandlerObject, int i, int i2) {
        INetworkAnalyticsService binderForPackage = this.mConnectionManager.getBinderForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId));
        NetworkAnalyticsConfigStore.NAPConfigProfile profileforName = this.mConfigStore.getProfileforName(asyncHandlerObject.profileName);
        String jsonString = profileforName != null ? profileforName.getJsonString() : null;
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:Service", "_activateNAPClient: activation:" + i);
        }
        if (binderForPackage == null || jsonString == null) {
            if (z) {
                Log.d("NetworkAnalytics:Service", "_activateNAPClient: Null values found. INetworkAnalyticsService = " + binderForPackage + " or json = " + jsonString);
            }
            return false;
        }
        int i3 = -1;
        try {
            if (1 == i) {
                String deviceUniqueKey = getDeviceUniqueKey();
                if (z) {
                    Log.d("NetworkAnalytics:Service", "_activateNAPClient: Device unique Key = " + deviceUniqueKey);
                }
                i3 = binderForPackage.onActivateProfile(jsonString, i2, deviceUniqueKey);
                if (z) {
                    Log.d("NetworkAnalytics:Service", "onActivateProfile: returnValue = " + i3);
                }
            } else {
                i3 = binderForPackage.onDeactivateProfile(asyncHandlerObject.profileName, i2);
                if (z) {
                    Log.d("NetworkAnalytics:Service", "onDeactivateProfile: returnValue = " + i3);
                }
            }
            if (z) {
                Log.d("NetworkAnalytics:Service", "_activateNAPClient: onActivate from client is " + i3);
            }
            IStatusCallback iStatusCallback = asyncHandlerObject.callback;
            if (iStatusCallback != null) {
                iStatusCallback.onCallComplete(i3);
            }
        } catch (RemoteException e) {
            Log.e("NetworkAnalytics:Service", "_activateNAPClient: Remote Exception", e);
        }
        return i3 == 0;
    }

    public final void _bindAndActivate(AsyncHandlerObject asyncHandlerObject, int i, int i2) {
        boolean await;
        boolean z;
        List<NetworkAnalyticsConfigStore.NAPConfigProfile> allProfilesForUser;
        int i3 = 0;
        boolean z2 = true;
        try {
            if (1 == i2) {
                boolean z3 = DBG;
                if (z3) {
                    Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED: Doing bind and activate.");
                }
                NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection = new NetworkAnalyticsServiceConnection(asyncHandlerObject);
                networkAnalyticsServiceConnection.countDownLatch = new CountDownLatch(1);
                int _bindToClient = _bindToClient(networkAnalyticsServiceConnection);
                IStatusCallback iStatusCallback = asyncHandlerObject.callback;
                if (-1 == _bindToClient) {
                    if (z3) {
                        Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED: _bindToClient did not succeed");
                    }
                    if (iStatusCallback != null) {
                        iStatusCallback.onCallComplete(-11);
                        return;
                    }
                    return;
                }
                if (_bindToClient == 0) {
                    if (this.mConnectionManager.isProfilePresentForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId), getTransformedVendorName(asyncHandlerObject.profileName, i))) {
                        _activateNAPClient(asyncHandlerObject, 1, i);
                        return;
                    }
                    if (z3) {
                        Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED: Honoring previous bind.");
                    }
                    networkAnalyticsServiceConnection = this.mConnectionManager.getServiceConnectionForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId));
                    await = true;
                } else {
                    await = networkAnalyticsServiceConnection.countDownLatch.await(3000L, TimeUnit.MILLISECONDS);
                }
                networkAnalyticsServiceConnection.countDownLatch = null;
                if (!await || _bindToClient < 0) {
                    z = false;
                } else {
                    if (z3) {
                        Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED: _bindToClient & countdown: " + asyncHandlerObject.toString());
                    }
                    z = _activateNAPClient(asyncHandlerObject, 1, i);
                    if (!z) {
                        return;
                    }
                    Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED: Adding to data delivery");
                    int i4 = asyncHandlerObject.userId;
                    if (i4 == 0) {
                        allProfilesForUser = this.mConfigStore.getAllProfilesForPackage(asyncHandlerObject.packageName);
                    } else {
                        allProfilesForUser = this.mConfigStore.getAllProfilesForUser(i4);
                    }
                    if (allProfilesForUser == null) {
                        Log.d("NetworkAnalytics:Service", "_bindAndActivate: no profile found for user. Not adding profile to recipient list on NetworkAnalyticsDataDelivery");
                        return;
                    }
                    for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile : allProfilesForUser) {
                        if (nAPConfigProfile.getPackageName().equalsIgnoreCase(asyncHandlerObject.packageName) && nAPConfigProfile.getUserId() == asyncHandlerObject.userId && this.mConfigStore.isProfileActivatedForUser(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId()) == 1) {
                            DataDeliveryHelper dataDeliveryHelper = new DataDeliveryHelper(nAPConfigProfile, networkAnalyticsServiceConnection, nAPConfigProfile.getOpUserId());
                            Log.d("NetworkAnalytics:Service", "Adding the profile to the delivery list _bindAndActivate " + nAPConfigProfile.getProfileName());
                            this.mDataDelivery.addNAPDataRecipient(dataDeliveryHelper);
                        }
                    }
                    if (EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion() != null) {
                        if (versionCompare(EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion(), EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_3_3.getInternalVersion()) < 0) {
                            if (this.mDriver.openCharDevice(0) < 0) {
                                Log.i("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED:Opening of character device failed.");
                                return;
                            }
                        } else if (this.mDriver.openCharDevice(1) < 0) {
                            Log.i("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED:Opening of character device failed.");
                            return;
                        }
                        this.mConnectionManager.addProfileForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId), getTransformedVendorName(asyncHandlerObject.profileName, i));
                        NetworkAnalyticsConfigStore.NAPConfigProfile profileforName = this.mConfigStore.getProfileforName(asyncHandlerObject.profileName);
                        if (profileforName == null) {
                            Log.d("NetworkAnalytics:Service", "_bindAndActivate: no profile found. Not begining to record data");
                            return;
                        }
                        if (this.mDriver.checkNcmVersionMismatch() < 0) {
                            if (activateProfile(profileforName, 0) < 0) {
                                Log.d("NetworkAnalytics:Service", "_bindAndActivate: version mismatch deactivation failed for profile : " + asyncHandlerObject.profileName + " but data collection will not happen.");
                                return;
                            }
                            sendMessageToHandler(1, 0, profileforName.getOpUserId(), asyncHandlerObject);
                            Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED:Version mismatch between user and kernel space. Deactivated this profile : " + asyncHandlerObject.profileName);
                            return;
                        }
                        NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName(getTransformedVendorName(profileforName.getProfileName(), profileforName.getOpUserId()));
                        if (activeStateForName == null) {
                            Log.d("NetworkAnalytics:Service", "_bindAndActivate: no active profile found. Not begining to record data");
                            return;
                        }
                        int activationFlowType = activeStateForName.getActivationFlowType();
                        int activationIntervalValue = activeStateForName.getActivationIntervalValue();
                        if (activationFlowType == 0 && activationIntervalValue >= 60 && activationIntervalValue <= 14400 && this.mDriver.setIntervalValueForFlow(activationIntervalValue) < 0) {
                            Log.d("NetworkAnalytics:Service", "_bindAndActivate: set interval value and deactivation failed for profile : " + asyncHandlerObject.profileName + " but data collection will not happen.");
                            return;
                        }
                        this.mDriver.beginDataRecording(activationFlowType);
                    } else {
                        Log.i("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED:Opening of character device failed.");
                        return;
                    }
                }
                if (iStatusCallback != null) {
                    if (DBG) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("_bindAndActivate: callback return = ");
                        if (_bindToClient < 0 || !z) {
                            z2 = false;
                        }
                        sb.append(z2);
                        Log.d("NetworkAnalytics:Service", sb.toString());
                    }
                    if (_bindToClient < 0 || !z) {
                        i3 = -11;
                    }
                    iStatusCallback.onCallComplete(i3);
                }
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_ACTIVATED: bind and activate complete.");
                    return;
                }
                return;
            }
            boolean z4 = DBG;
            if (z4) {
                Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_NOT_ACTIVATED: Doing deactivate and unbind.");
            }
            boolean _activateNAPClient = _activateNAPClient(asyncHandlerObject, 0, i);
            if (!_activateNAPClient && z4) {
                Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_NOT_ACTIVATED: deactivate did not succeed");
            }
            this.mConnectionManager.removeProfileForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId), getTransformedVendorName(asyncHandlerObject.profileName, i));
            this.mDataDelivery.removeNAPDataRecipient(asyncHandlerObject.profileName, i);
            if (this.mConnectionManager.isProfilePresentForPackage(getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId))) {
                if (z4) {
                    Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_NOT_ACTIVATED: Wont unbind.");
                    return;
                }
                return;
            }
            _unbindClient(asyncHandlerObject);
            if (this.mConnectionManager.getActiveProfilesNumber() <= 0) {
                if (z4) {
                    Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_NOT_ACTIVATED: Will stop data recording.");
                }
                this.mDriver.endDataRecording();
                NetworkAnalyticsDataDelivery.clearHashCacheEntire();
            }
            if (z4) {
                Log.d("NetworkAnalytics:Service", "_bindAndActivate:PROFILE_NOT_ACTIVATED: deactivate complete: " + _activateNAPClient);
            }
        } catch (RemoteException e) {
            Log.e("NetworkAnalytics:Service", "_bindAndActivate RemoteException", e);
        } catch (Exception e2) {
            Log.e("NetworkAnalytics:Service", "_bindAndActivate Exception", e2);
        }
    }

    public final void _bindAndActivate(String str, boolean z) {
        boolean z2 = DBG;
        if (z2) {
            Log.d("NetworkAnalytics:Service", "_bindAndActivate:ACTION_INITIALIZE_NAP: adding profile: " + str);
        }
        NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName(str);
        if (activeStateForName == null) {
            if (z2) {
                Log.d("NetworkAnalytics:Service", "_bindAndActivate: ERROR NAPActivationProfile is null for profile: " + str);
                return;
            }
            return;
        }
        if (activeStateForName.getActivationState() == 0) {
            if (z2) {
                Log.d("NetworkAnalytics:Service", "_bindAndActivate: ERROR NAPActivationProfile is not activated. No need to bind.");
                return;
            }
            return;
        }
        NetworkAnalyticsConfigStore.NAPConfigProfile profile = activeStateForName.getProfile();
        if (profile == null) {
            if (z2) {
                Log.d("NetworkAnalytics:Service", "_bindAndActivate: ERROR NAPConfigProfile is null for profile: " + str);
                return;
            }
            return;
        }
        if (!z || validateNAPClient(profile.getPackageName(), profile.getPackageSignature(), profile.getUserId()) >= 0) {
            AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
            asyncHandlerObject.profileName = getVendorNameFromTransformedName(str);
            asyncHandlerObject.packageName = profile.getPackageName();
            asyncHandlerObject.userId = profile.getUserId();
            asyncHandlerObject.callback = null;
            _bindAndActivate(asyncHandlerObject, getCidFromTransformedName(str), z ? 1 : 0);
            return;
        }
        if (z2) {
            Log.d("NetworkAnalytics:Service", "_bindAndActivate: ERROR Valid package has not been installed: " + profile.getPackageName());
        }
    }

    public final void _bindAndActivateOnReboot() {
        Set setActivatedProfiles = this.mConfigStore.getSetActivatedProfiles();
        if (setActivatedProfiles == null || setActivatedProfiles.size() <= 0) {
            return;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "_bindAndActivateOnReboot:ACTION_INITIALIZE_NAP: Found profile sizes " + setActivatedProfiles.size());
        }
        setFeatureProperty(true);
        Iterator it = setActivatedProfiles.iterator();
        while (it.hasNext()) {
            _bindAndActivate((String) it.next(), true);
        }
    }

    public final void _bindAndActivateOnReboot(Bundle bundle) {
        Set<String> setActivatedProfiles = this.mConfigStore.getSetActivatedProfiles();
        if (setActivatedProfiles == null || setActivatedProfiles.size() <= 0) {
            return;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "_bindAndActivateOnReboot:ACTION_INITIALIZE_NAP: Found profile sizes " + setActivatedProfiles.size());
        }
        setFeatureProperty(true);
        int i = bundle.getInt("android.intent.extra.user_handle", -10000);
        for (String str : setActivatedProfiles) {
            NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName(str);
            if (activeStateForName == null) {
                if (DBG) {
                    Log.d("NetworkAnalytics:Service", "_bindAndActivateOnReboot: ERROR NAPActivationProfile is null for profile: " + str);
                }
            } else {
                NetworkAnalyticsConfigStore.NAPConfigProfile profile = activeStateForName.getProfile();
                if (profile == null) {
                    if (DBG) {
                        Log.d("NetworkAnalytics:Service", "_bindAndActivateOnReboot: ERROR NAPConfigProfile is null for profile: " + str);
                    }
                } else if (i == profile.getUserId()) {
                    _bindAndActivate(str, true);
                }
            }
        }
    }

    public final void _unregisterAllProfiles(int i) {
        List clientProfileNames = this.mConfigStore.getClientProfileNames(i);
        if (clientProfileNames == null || clientProfileNames.size() <= 0) {
            return;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "_unregisterAllProfiles: Found profile sizes " + clientProfileNames.size());
        }
        if (clientProfileNames.size() > 0) {
            setFeatureProperty(false);
            Iterator it = clientProfileNames.iterator();
            while (it.hasNext()) {
                this.mConfigStore.updateTablesForProfileRemoval(i, (String) it.next());
            }
        }
    }

    public final void _deactivateAllProfiles() {
        Set setActivatedProfiles = this.mConfigStore.getSetActivatedProfiles();
        if (setActivatedProfiles == null || setActivatedProfiles.size() <= 0) {
            return;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "_deactivateAllProfiles: Found profile sizes " + setActivatedProfiles.size());
        }
        setFeatureProperty(false);
        Iterator it = setActivatedProfiles.iterator();
        while (it.hasNext()) {
            _bindAndActivate((String) it.next(), false);
        }
    }

    public final void _deactivateAllProfiles(int i) {
        Set<String> setActivatedProfiles = this.mConfigStore.getSetActivatedProfiles();
        if (setActivatedProfiles == null || setActivatedProfiles.size() <= 0) {
            return;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "_deactivateAllConnections: Found profile sizes " + setActivatedProfiles.size());
        }
        for (String str : setActivatedProfiles) {
            boolean z = DBG;
            if (z) {
                Log.d("NetworkAnalytics:Service", "_deactivateAllProfiles:ACTION_INITIALIZE_NAP: adding profile: " + str);
            }
            NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName(str);
            if (activeStateForName == null) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "_deactivateAllProfiles: ERROR NAPActivationProfile is null for profile: " + str);
                }
            } else if (i == activeStateForName.getAdminUid()) {
                NetworkAnalyticsConfigStore.NAPConfigProfile profile = activeStateForName.getProfile();
                if (profile == null) {
                    if (z) {
                        Log.d("NetworkAnalytics:Service", "_deactivateAllProfiles: ERROR NAPConfigProfile is null for profile: " + str);
                    }
                } else if (activateProfile(profile, 0) >= 0) {
                    AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
                    asyncHandlerObject.profileName = profile.getProfileName();
                    asyncHandlerObject.packageName = profile.getPackageName();
                    asyncHandlerObject.userId = profile.getUserId();
                    asyncHandlerObject.callback = null;
                    sendMessageToHandler(1, 0, profile.getOpUserId(), asyncHandlerObject);
                } else if (z) {
                    Log.d("NetworkAnalytics:Service", "_deactivateAllProfiles: ERROR deactivateProfile for profile: " + str);
                }
            } else if (z) {
                Log.d("NetworkAnalytics:Service", "_deactivateAllProfiles: ERROR Not the same adminUid: " + i);
            }
        }
    }

    public final String getDeviceUniqueKey() {
        DeviceRootKeyServiceManager dRKService;
        if (mDeviceUniqueId == null && (dRKService = getDRKService(this.mContext)) != null) {
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "getDeviceUniqueKey: drkService not null");
            }
            mDeviceUniqueId = dRKService.getDeviceRootKeyUID(1);
        }
        return mDeviceUniqueId;
    }

    public final void _handleNewProfileRegistration(String str) {
        NetworkAnalyticsConfigStore.NAPConfigProfile profileforName = this.mConfigStore.getProfileforName(str);
        if (profileforName == null) {
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "_handleNewProfileRegistration: profile is null for name:" + str);
                return;
            }
            return;
        }
        String packageName = profileforName.getPackageName();
        if (this.mConfigStore.isActivatedPackage(packageName)) {
            boolean z = DBG;
            if (z) {
                Log.d("NetworkAnalytics:Service", "_handleNewProfileRegistration: profile activated:" + str);
            }
            int userIdLocationOfPackage = this.mConfigStore.getUserIdLocationOfPackage(packageName);
            if (userIdLocationOfPackage < 0) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "_handleNewProfileRegistration: bind userId location fail for:" + profileforName.toString());
                    return;
                }
                return;
            }
            if (this.mConfigStore.updateBindUserIdForProfile(str, profileforName.getOpUserId(), userIdLocationOfPackage) < 0) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "_handleNewProfileRegistration: bind userId updation failed for profile:" + profileforName.toString());
                    return;
                }
                return;
            }
            if (activateProfile(profileforName, 1) < 0) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "_handleNewProfileRegistration: activation failed for profile:" + profileforName.toString());
                    return;
                }
                return;
            }
            AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
            asyncHandlerObject.profileName = profileforName.getProfileName();
            asyncHandlerObject.packageName = profileforName.getPackageName();
            asyncHandlerObject.userId = profileforName.getUserId();
            asyncHandlerObject.callback = null;
            _bindAndActivate(asyncHandlerObject, profileforName.getOpUserId(), 1);
        }
    }

    public final void _packageAddedAction(String str, int i) {
        try {
            boolean z = DBG;
            if (z) {
                Log.d("NetworkAnalytics:Service", "_packageAddedAction called");
            }
            List<String> allActiveKeysForPackage = this.mConfigStore.getAllActiveKeysForPackage(str);
            if (allActiveKeysForPackage == null || allActiveKeysForPackage.size() <= 0) {
                return;
            }
            if (z) {
                Log.d("NetworkAnalytics:Service", "_packageAddedAction activatedProfiles.size() = " + allActiveKeysForPackage.size());
            }
            for (String str2 : allActiveKeysForPackage) {
                NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName(str2);
                if (activeStateForName != null) {
                    if (DBG) {
                        Log.d("NetworkAnalytics:Service", "_packageAddedAction: ACTIVATING FOR PACKAGE ADDED.");
                    }
                    NetworkAnalyticsConfigStore.NAPConfigProfile profile = activeStateForName.getProfile();
                    if (profile.getUserId() == i) {
                        if (this.mConfigStore.validatePkgSignForSingleProfile(str, getPackageCertForPkgname(i, str), profile.getProfileName())) {
                            AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
                            asyncHandlerObject.profileName = profile.getProfileName();
                            asyncHandlerObject.packageName = profile.getPackageName();
                            asyncHandlerObject.userId = profile.getUserId();
                            asyncHandlerObject.callback = null;
                            sendMessageToHandler(1, 1, getCidFromTransformedName(str2), asyncHandlerObject);
                        } else {
                            Log.d("NetworkAnalytics:Service", "__packageAddedAction: Package signature could not be matched for profile entry : " + profile.getProfileName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "_packageAddedAction Exception", e);
        }
    }

    public final void _packageRemovedAction(String str, int i) {
        try {
            List<String> allActiveKeysForPackage = this.mConfigStore.getAllActiveKeysForPackage(str);
            if (allActiveKeysForPackage == null || allActiveKeysForPackage.size() <= 0) {
                return;
            }
            for (String str2 : allActiveKeysForPackage) {
                NetworkAnalyticsConfigStore.NAPProfileActivation activeStateForName = this.mConfigStore.getActiveStateForName(str2);
                if (activeStateForName != null) {
                    if (DBG) {
                        Log.d("NetworkAnalytics:Service", "_packageRemovedAction: DEACTIVATING FOR PACKAGE REMOVED.");
                    }
                    NetworkAnalyticsConfigStore.NAPConfigProfile profile = activeStateForName.getProfile();
                    if (profile.getUserId() == i) {
                        AsyncHandlerObject asyncHandlerObject = new AsyncHandlerObject();
                        asyncHandlerObject.profileName = profile.getProfileName();
                        asyncHandlerObject.packageName = profile.getPackageName();
                        asyncHandlerObject.userId = profile.getUserId();
                        asyncHandlerObject.callback = null;
                        sendMessageToHandler(1, 0, getCidFromTransformedName(str2), asyncHandlerObject);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("NetworkAnalytics:Service", "_packageRemovedAction Exception", e);
        }
    }

    public final ContextInfo enforceNetworkAnalyticsPermission(ContextInfo contextInfo) {
        Log.d("NetworkAnalytics:Service", "enforceNetworkAnalyticsPermission: knox version above 3.0. Validating NPA Permission");
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_NPA");
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public static String getTransformedVendorName(String str, int i) {
        return str + "__" + i;
    }

    public static int getCidFromTransformedName(String str) {
        return Integer.parseInt(str.substring(str.indexOf("__") + 2));
    }

    public static String getVendorNameFromTransformedName(String str) {
        return str.substring(0, str.indexOf("__"));
    }

    public final void _userRemovedAction(Bundle bundle) {
        int i = bundle.getInt("android.intent.extra.user_handle", -10000);
        if (i == -10000) {
            if (DBG) {
                Log.d("NetworkAnalytics:Service", "_userRemovedAction: Invalid userId received:" + i);
                return;
            }
            return;
        }
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:Service", "_userRemovedAction: Received for" + i);
        }
        List<NetworkAnalyticsConfigStore.NAPConfigProfile> allProfilesForUser = this.mConfigStore.getAllProfilesForUser(i);
        if (allProfilesForUser == null || allProfilesForUser.size() <= 0) {
            return;
        }
        if (z) {
            Log.d("NetworkAnalytics:Service", "_userRemovedAction: List of profiles for removed userId is " + allProfilesForUser.size());
        }
        for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile : allProfilesForUser) {
            unregisterNetworkMonitorProfile(nAPConfigProfile.getAdminUid(), nAPConfigProfile.getProfileName());
        }
    }

    public final void setFeatureProperty(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                DomainFilterNapCommon.getInstance(this.mContext).setNapEnabled(z);
                if (z) {
                    SystemProperties.set("net.redirect_socket_calls.hooked", "true");
                } else {
                    SystemProperties.set("net.redirect_socket_calls.hooked", "false");
                }
            } catch (Exception e) {
                Log.e("NetworkAnalytics:Service", "Unable to set NPA feature property", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setupIntentFilter() {
        if (DBG) {
            Log.d("NetworkAnalytics:Service", "setup intent filter is called");
        }
        NapReceiver napReceiver = new NapReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addCategory("android.intent.category.DEFAULT");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(napReceiver, UserHandle.ALL, intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter2.addAction("android.intent.action.USER_STARTED");
        intentFilter2.addAction("android.intent.action.USER_SWITCHED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter2.addAction("android.intent.action.AIRPLANE_MODE");
        this.mContext.registerReceiverAsUser(napReceiver, UserHandle.ALL, intentFilter2, null, null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("enterprise.container.uninstalled");
        this.mContext.registerReceiverAsUser(napReceiver, UserHandle.ALL, intentFilter3, "com.samsung.android.knox.permission.KNOX_CONTAINER", null);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("enterprise.container.admin.changed");
        this.mContext.registerReceiverAsUser(napReceiver, UserHandle.ALL, intentFilter4, "com.samsung.android.knox.permission.KNOX_CONTAINER", null);
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        this.mContext.registerReceiverAsUser(napReceiver, UserHandle.ALL, intentFilter5, null, null);
    }

    public class NapReceiver extends BroadcastReceiver {
        public NapReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String schemeSpecificPart;
            String action = intent.getAction();
            boolean z = NetworkAnalyticsService.DBG;
            if (z) {
                Log.d("NetworkAnalytics:Service", "Nap Receiver : " + action);
            }
            if (isInitialStickyBroadcast()) {
                return;
            }
            new Bundle();
            if (action.equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
                NetworkAnalyticsService.this.sendMessageToHandler(3, 0, 0, intent.getExtras());
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) {
                Uri data = intent.getData();
                schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                int i = intent.getExtras().getInt("android.intent.extra.UID", 0);
                if (z) {
                    Log.d("NetworkAnalytics:Service", "ACTION PACKAGE REMOVED packageName : " + schemeSpecificPart + " uid : " + i);
                }
                int userId = UserHandle.getUserId(i);
                NetworkAnalyticsDataDelivery.updateHashCache(i, schemeSpecificPart);
                boolean isActivatedPackage = NetworkAnalyticsService.this.mConfigStore.isActivatedPackage(schemeSpecificPart);
                if (schemeSpecificPart == null || !isActivatedPackage) {
                    return;
                }
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    Log.d("NetworkAnalytics:Service", "The package removal intent has been called before upgrade");
                    return;
                }
                AsyncHandlerObject asyncHandlerObject = NetworkAnalyticsService.this.new AsyncHandlerObject();
                asyncHandlerObject.packageName = schemeSpecificPart;
                asyncHandlerObject.userId = userId;
                NetworkAnalyticsService.this.sendMessageToHandler(6, 0, 0, asyncHandlerObject);
                return;
            }
            if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) {
                Uri data2 = intent.getData();
                schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                if (z) {
                    Log.d("NetworkAnalytics:Service", "ACTION PACKAGE ADDED packageName : " + schemeSpecificPart);
                }
                int userId2 = UserHandle.getUserId(intent.getExtras().getInt("android.intent.extra.UID", 0));
                boolean isActivatedPackage2 = NetworkAnalyticsService.this.mConfigStore.isActivatedPackage(schemeSpecificPart);
                if (schemeSpecificPart == null || !isActivatedPackage2) {
                    return;
                }
                AsyncHandlerObject asyncHandlerObject2 = NetworkAnalyticsService.this.new AsyncHandlerObject();
                asyncHandlerObject2.packageName = schemeSpecificPart;
                asyncHandlerObject2.userId = userId2;
                NetworkAnalyticsService.this.sendMessageToHandler(7, 0, 0, asyncHandlerObject2);
                return;
            }
            if (action.equals("android.intent.action.USER_REMOVED")) {
                Bundle extras = intent.getExtras();
                int i2 = extras.getInt("android.intent.extra.user_handle", -10000);
                if (i2 != -10000) {
                    NetworkAnalyticsDataDelivery.updateHashCacheForUser(i2);
                }
                NetworkAnalyticsService.this.sendMessageToHandler(11, 0, 0, extras);
                return;
            }
            if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                if (z) {
                    Log.d("NetworkAnalytics:Service", "EMERGENCY_STATE_CHANGED received");
                }
                NetworkAnalyticsService.this.sendMessageToHandler(12, 0, 0, intent.getExtras());
            }
        }
    }

    public class NapHandler extends Handler {
        public NapHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    AsyncHandlerObject asyncHandlerObject = (AsyncHandlerObject) message.obj;
                    int i = message.arg1;
                    int i2 = message.arg2;
                    if (asyncHandlerObject == null) {
                        if (NetworkAnalyticsService.DBG) {
                            Log.d("NetworkAnalytics:Service", "handleMessage:ACTIVATE_MONITOR_CLIENT:handleObj null");
                            break;
                        }
                    } else {
                        NetworkAnalyticsService.this._bindAndActivate(asyncHandlerObject, i2, i);
                        break;
                    }
                    break;
                case 3:
                    NetworkAnalyticsService.this._bindAndActivateOnReboot((Bundle) message.obj);
                    break;
                case 4:
                    NetworkAnalyticsService.this.mConfigStore.initializeTables();
                    break;
                case 5:
                    if (((AsyncHandlerObject) message.obj) == null && NetworkAnalyticsService.DBG) {
                        Log.d("NetworkAnalytics:Service", "handleMessage:UNREGISTER_CLIENT:handleObj null");
                        break;
                    }
                    break;
                case 6:
                    AsyncHandlerObject asyncHandlerObject2 = (AsyncHandlerObject) message.obj;
                    NetworkAnalyticsService.this._packageRemovedAction(asyncHandlerObject2.packageName, asyncHandlerObject2.userId);
                    break;
                case 7:
                    AsyncHandlerObject asyncHandlerObject3 = (AsyncHandlerObject) message.obj;
                    NetworkAnalyticsService.this._packageAddedAction(asyncHandlerObject3.packageName, asyncHandlerObject3.userId);
                    break;
                case 8:
                    int i3 = message.arg1;
                    if (i3 <= 0) {
                        if (NetworkAnalyticsService.DBG) {
                            Log.d("NetworkAnalytics:Service", "handleMessage: ACTION_ON_PRE_ADMIN_REMOVED: Invalid adminUid");
                            break;
                        }
                    } else {
                        NetworkAnalyticsService.this._deactivateAllProfiles(i3);
                        break;
                    }
                    break;
                case 9:
                    int i4 = message.arg1;
                    if (i4 <= 0) {
                        if (NetworkAnalyticsService.DBG) {
                            Log.d("NetworkAnalytics:Service", "handleMessage: ACTION_ON_ADMIN_REMOVED: Invalid adminUid");
                            break;
                        }
                    } else {
                        NetworkAnalyticsService.this._unregisterAllProfiles(i4);
                        break;
                    }
                    break;
                case 10:
                    NetworkAnalyticsService.this._handleNewProfileRegistration((String) message.obj);
                    break;
                case 11:
                    NetworkAnalyticsService.this._userRemovedAction((Bundle) message.obj);
                    break;
                case 12:
                    Bundle bundle = (Bundle) message.obj;
                    if (bundle != null) {
                        int i5 = bundle.getInt("reason", 0);
                        if (i5 != 5) {
                            if (i5 == 3) {
                                if (NetworkAnalyticsService.DBG) {
                                    Log.d("NetworkAnalytics:Service", "handleMessage: ACTION_ULTRA_POWER_SAVING_MODE ON");
                                }
                                NetworkAnalyticsService.this._deactivateAllProfiles();
                                break;
                            }
                        } else {
                            if (NetworkAnalyticsService.DBG) {
                                Log.d("NetworkAnalytics:Service", "handleMessage: ACTION_ULTRA_POWER_SAVING_MODE OFF");
                            }
                            NetworkAnalyticsService.this._bindAndActivateOnReboot();
                            break;
                        }
                    }
                    break;
                case 14:
                    Bundle bundle2 = (Bundle) message.obj;
                    if (bundle2 != null) {
                        NetworkAnalyticsService.this.sendStatusToClient(bundle2);
                        break;
                    }
                    break;
            }
        }
    }

    public class AsyncHandlerObject {
        public IStatusCallback callback;
        public String packageName;
        public String profileName;
        public int userId;

        public AsyncHandlerObject() {
        }

        public String toString() {
            return "[" + this.profileName + "," + this.packageName + "," + this.userId + "]";
        }
    }

    public class NetworkAnalyticsServiceConnection implements ServiceConnection {
        public AsyncHandlerObject handleObj;
        public CountDownLatch countDownLatch = null;
        public INetworkAnalyticsService napInterface = null;

        public NetworkAnalyticsServiceConnection(AsyncHandlerObject asyncHandlerObject) {
            this.handleObj = asyncHandlerObject;
        }

        public INetworkAnalyticsService getBinderObject() {
            return this.napInterface;
        }

        public AsyncHandlerObject getHandlerObject() {
            return this.handleObj;
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            List<NetworkAnalyticsConfigStore.NAPConfigProfile> allProfilesForUser;
            AsyncHandlerObject asyncHandlerObject = this.handleObj;
            String transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId);
            Log.d("NetworkAnalytics:Service", "onServiceconnected called by " + transformedVendorName);
            this.napInterface = INetworkAnalyticsService.Stub.asInterface(iBinder);
            if (this.handleObj.userId == 0) {
                allProfilesForUser = NetworkAnalyticsService.this.mConfigStore.getAllProfilesForPackage(this.handleObj.packageName);
            } else {
                allProfilesForUser = NetworkAnalyticsService.this.mConfigStore.getAllProfilesForUser(this.handleObj.userId);
            }
            if (allProfilesForUser == null) {
                return;
            }
            for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile : allProfilesForUser) {
                if (nAPConfigProfile.getPackageName().equalsIgnoreCase(this.handleObj.packageName) && nAPConfigProfile.getUserId() == this.handleObj.userId && NetworkAnalyticsService.this.mConfigStore.isProfileActivatedForUser(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId()) == 1) {
                    DataDeliveryHelper dataDeliveryHelper = new DataDeliveryHelper(nAPConfigProfile, this, nAPConfigProfile.getOpUserId());
                    Log.d("NetworkAnalytics:Service", "Adding the profile to the delivery list onServiceconnected callback " + nAPConfigProfile.getProfileName());
                    NetworkAnalyticsService.this.mDataDelivery.addNAPDataRecipient(dataDeliveryHelper);
                }
            }
            NetworkAnalyticsService.this.mConnectionManager.storeBinderForPackage(transformedVendorName, this);
            CountDownLatch countDownLatch = this.countDownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceDisconnected(ComponentName componentName) {
            AsyncHandlerObject asyncHandlerObject = this.handleObj;
            String transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId);
            Log.d("NetworkAnalytics:Service", "onServiceDisconnected called by " + transformedVendorName);
            NetworkAnalyticsService.this.mConnectionManager.removeBinderForPackage(transformedVendorName);
            NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery = NetworkAnalyticsService.this.mDataDelivery;
            AsyncHandlerObject asyncHandlerObject2 = this.handleObj;
            networkAnalyticsDataDelivery.removeDataRecipientsForPackage(asyncHandlerObject2.packageName, asyncHandlerObject2.userId);
        }

        @Override // android.content.ServiceConnection
        public synchronized void onBindingDied(ComponentName componentName) {
            String transformedVendorName;
            AsyncHandlerObject asyncHandlerObject = this.handleObj;
            Log.d("NetworkAnalytics:Service", "onBindingDied called by " + NetworkAnalyticsService.getTransformedVendorName(asyncHandlerObject.packageName, asyncHandlerObject.userId));
            List<NetworkAnalyticsConfigStore.NAPConfigProfile> allProfilesForUser = NetworkAnalyticsService.this.mConfigStore.getAllProfilesForUser(this.handleObj.userId);
            if (allProfilesForUser == null) {
                return;
            }
            for (NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile : allProfilesForUser) {
                if (nAPConfigProfile.getPackageName().equalsIgnoreCase(this.handleObj.packageName) && nAPConfigProfile.getUserId() == this.handleObj.userId && (transformedVendorName = NetworkAnalyticsService.getTransformedVendorName(nAPConfigProfile.getProfileName(), nAPConfigProfile.getOpUserId())) != null) {
                    Log.d("NetworkAnalytics:Service", "Trying to bind again after the onBindingDied for the profile " + transformedVendorName);
                    NetworkAnalyticsService.this._bindAndActivate(transformedVendorName, true);
                }
            }
        }
    }

    public final void constructObjectForBroadcast(int i, int i2, String str) {
        try {
            String profileObjectFromJson = this.mConfigStore.getProfileObjectFromJson("profile_name", str);
            String profileObjectFromJson2 = this.mConfigStore.getProfileObjectFromJson("package_name", str);
            String profileObjectFromJson3 = this.mConfigStore.getProfileObjectFromJson("package_signature", str);
            Bundle bundle = new Bundle();
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(profileObjectFromJson);
            arrayList.add(profileObjectFromJson2);
            arrayList.add(profileObjectFromJson3);
            arrayList.add(String.valueOf(i2));
            arrayList.add(String.valueOf(i));
            bundle.putStringArrayList("profileInfo", arrayList);
            if (this.mHandler == null || this.mHandlerThread == null) {
                return;
            }
            sendMessageToHandler(14, 0, 0, bundle);
        } catch (Exception unused) {
            Log.e("NetworkAnalytics:Service", "Error occured while constructing the profile object for broadcast");
        }
    }

    public final void sendStatusToClient(Bundle bundle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String str = bundle.getStringArrayList("profileInfo").get(0);
                String str2 = bundle.getStringArrayList("profileInfo").get(1);
                String str3 = bundle.getStringArrayList("profileInfo").get(2);
                int intValue = Integer.valueOf(bundle.getStringArrayList("profileInfo").get(3)).intValue();
                int intValue2 = Integer.valueOf(bundle.getStringArrayList("profileInfo").get(4)).intValue();
                Log.d("NetworkAnalytics:Service", "The System is going to send broadcast of the profile status " + intValue2);
                if (IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isPackageAvailable(str2, intValue)) {
                    Log.d("NetworkAnalytics:Service", "The package " + str2 + " has been installed in the user space " + intValue);
                    String packageCertForPkgname = getPackageCertForPkgname(intValue, str2);
                    if (packageCertForPkgname != null && packageCertForPkgname.length() > 0) {
                        if (DBG) {
                            Log.d("NetworkAnalytics:Service", "The signature of the package " + str2 + "in " + intValue + "confirmed by OS is " + packageCertForPkgname);
                        }
                        if (packageCertForPkgname.equalsIgnoreCase(str3)) {
                            Log.d("NetworkAnalytics:Service", "The signature mentioned by the admin and the signature of the package present in the device matches");
                            Intent intent = new Intent("com.samsung.android.knox.intent.action.NPA_STATUS");
                            intent.putExtra("com.samsung.android.knox.intent.extra.REGISTRATION_STATUS", intValue2);
                            intent.putExtra("com.samsung.android.knox.intent.extra.PROFILE_NAME", str);
                            intent.setPackage(str2);
                            this.mContext.sendBroadcastAsUser(intent, new UserHandle(intValue));
                        }
                    }
                }
            } catch (NullPointerException unused) {
                Log.e("NetworkAnalytics:Service", "Null pointer exception error occured while trying to send the NPA profile status to the client");
            } catch (Exception unused2) {
                Log.e("NetworkAnalytics:Service", "Error occured while trying to send the NPA profile status to the client");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
