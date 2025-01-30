package com.android.server.knox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class KnoxAnalyticsContainer {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public final AnalyticsHandler analyticsHandler;
    public final BasicContainerAnalytics basicContainerAnalytics;
    public Context context;
    public final DevicePolicyManagerAnalytics dpmAnalytics;
    public HandlerThread handlerThread;
    public final IKnoxAnalyticsContainer ifKnoxAnalyticsContainer;
    public final SeparatedAppsAnalytics separatedAppsAnalytics;
    public final String USAGE_TIME = "usageTime";
    public final String APP_LAUNCH_COUNT = "appLaunchCount";
    public final String PACKAGE_NAME = "package_name";
    public final String LAST_EVENT_SENT_DATE = "last_event_sent_date";
    public final HashMap locationRestrictionMap = new HashMap();
    public int mPostActiveUserId = 0;
    public String mPostActivePackage = "";
    public long mPostActiveTime = 0;

    public final int getPasswordType(int i) {
        if (i == 65536) {
            return 2;
        }
        if (i == 131072 || i == 196608) {
            return 3;
        }
        return (i == 262144 || i == 327680 || i == 393216) ? 4 : 1;
    }

    public class AnalyticsHandler extends Handler {
        public AnalyticsHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.d("AnalyticsContainerHandler", "handleMessage : " + message);
            int i = message.what;
            if (i == 1) {
                KnoxAnalyticsContainer.this.handleSendLocationLog(message.arg1);
            } else {
                if (i != 2) {
                    return;
                }
                KnoxAnalyticsContainer.this.sendSnapshotLog(message.arg1);
            }
        }
    }

    public KnoxAnalyticsContainer(Context context, IKnoxAnalyticsContainer iKnoxAnalyticsContainer) {
        this.handlerThread = null;
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainer;
        this.context = context;
        HandlerThread handlerThread = new HandlerThread("KnoxAnalyticsContainer", 10);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.analyticsHandler = new AnalyticsHandler(this.handlerThread.getLooper());
        this.separatedAppsAnalytics = new SeparatedAppsAnalytics(iKnoxAnalyticsContainer, context);
        this.basicContainerAnalytics = new BasicContainerAnalytics(iKnoxAnalyticsContainer, context);
        this.dpmAnalytics = new DevicePolicyManagerAnalytics(iKnoxAnalyticsContainer, context);
    }

    public void logEventActivationForAppSep(ArrayList arrayList, ArrayList arrayList2) {
        this.separatedAppsAnalytics.logEventActivationForAppSep(arrayList, arrayList2);
    }

    public void onSeparatedAppsCreated() {
        this.separatedAppsAnalytics.onSeparatedAppsCreated();
    }

    public void onSeparatedAppsPolicyUpdated() {
        this.separatedAppsAnalytics.onSeparatedAppsPolicyUpdated();
    }

    public void logEventDeactivationForAppSep() {
        this.separatedAppsAnalytics.logEventDeactivationForAppSep();
    }

    public void logDpmsKA(Bundle bundle) {
        this.dpmAnalytics.logDpmsKA(bundle);
    }

    public void logEventAccountChanged(int i, String str, int i2) {
        this.basicContainerAnalytics.logEventAccountChanged(i, str, i2);
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.Serializable, java.lang.String[]] */
    public void logEvent(Bundle bundle, String str) {
        if (bundle == null || this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId() <= 0) {
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_CONTAINER", 5, str);
            if (bundle != null) {
                for (String str2 : bundle.keySet()) {
                    Object obj = bundle.get(str2);
                    if (obj instanceof Integer) {
                        knoxAnalyticsData.setProperty(str2, (Integer) obj);
                    } else if (obj instanceof String) {
                        knoxAnalyticsData.setProperty(str2, (String) obj);
                    } else if (obj instanceof Long) {
                        knoxAnalyticsData.setProperty(str2, (Long) obj);
                    } else if (obj instanceof String[]) {
                        knoxAnalyticsData.setProperty(str2, (String[]) obj);
                    }
                }
            }
            this.ifKnoxAnalyticsContainer.sendAnalyticsLog(knoxAnalyticsData);
            if (DEBUG) {
                Log.d("PersonaManagerService:KnoxAnalytics", str + " / " + knoxAnalyticsData.toString());
            }
        }
    }

    public void postActiveUserChange(final int i, final ComponentName componentName, final boolean z) {
        this.analyticsHandler.post(new Runnable() { // from class: com.android.server.knox.KnoxAnalyticsContainer.1
            @Override // java.lang.Runnable
            public void run() {
                KnoxAnalyticsContainer.this.postActiveUserChangeInternal(i, componentName, z);
            }
        });
    }

    public final void postActiveUserChangeInternal(int i, ComponentName componentName, boolean z) {
        if (z && componentName != null && componentName.getPackageName() != null) {
            String packageName = componentName.getPackageName();
            if (this.mPostActiveUserId != i || !this.mPostActivePackage.equals(packageName)) {
                if (SemPersonaManager.isKnoxId(this.mPostActiveUserId)) {
                    checkTimeAndSendAnalytics(this.mPostActiveUserId, this.mPostActivePackage, SystemClock.elapsedRealtime() - this.mPostActiveTime);
                }
                this.mPostActiveUserId = i;
                this.mPostActivePackage = packageName;
                this.mPostActiveTime = SystemClock.elapsedRealtime();
            }
            if ((this.ifKnoxAnalyticsContainer.isDoEnabled(0) || this.ifKnoxAnalyticsContainer.isKnoxId(i) || (i == this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId() && i > 0)) && isActvtStmpNeeded(i)) {
                try {
                    if (i == this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId() && i > 0) {
                        this.separatedAppsAnalytics.onAcitivtyChange(this.mPostActiveUserId, this.mPostActivePackage);
                    } else {
                        this.basicContainerAnalytics.logActivityChange(i, this.mPostActiveUserId, this.mPostActivePackage);
                    }
                } catch (Exception e) {
                    Log.d("PersonaManagerService:KnoxAnalytics", "activity_stamp KA failed" + e);
                }
            }
        }
        this.analyticsHandler.sendMessage(this.analyticsHandler.obtainMessage(1, i, 0));
    }

    public final boolean isActvtStmpNeeded(int i) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
            if (i > 0 && this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId() == i) {
                String string = defaultSharedPreferences.getString("log_ka_actvt_stmp_as", "");
                String format = simpleDateFormat.format(new Date());
                if (!string.equals("") && format.equals(string)) {
                    return false;
                }
                Log.d("PersonaManagerService:KnoxAnalytics", "isActvtStmpNeeded for AS " + i);
                SharedPreferences.Editor edit = defaultSharedPreferences.edit();
                edit.putString("log_ka_actvt_stmp_as", format);
                edit.apply();
                return true;
            }
            String string2 = defaultSharedPreferences.getString("log_ka_actvt_stmp", "");
            String format2 = simpleDateFormat.format(new Date());
            if (!string2.equals("") && format2.equals(string2)) {
                return false;
            }
            Log.d("PersonaManagerService:KnoxAnalytics", "isActvtStmpNeeded " + i);
            SharedPreferences.Editor edit2 = defaultSharedPreferences.edit();
            edit2.putString("log_ka_actvt_stmp", format2);
            edit2.apply();
            return true;
        } catch (IllegalStateException unused) {
            Log.d("PersonaManagerService:KnoxAnalytics", "IllegalStateExcpetion. User may be in locked.");
            return false;
        }
    }

    public final void handleSendLocationLog(int i) {
        try {
            if (i == 0) {
                if (((Integer) this.locationRestrictionMap.get(Integer.valueOf(i))).intValue() == (this.ifKnoxAnalyticsContainer.getLocationProvidersAllowed(i) == 0 ? 0 : 1)) {
                    return;
                }
            } else {
                if (((Integer) this.locationRestrictionMap.get(Integer.valueOf(i))).intValue() == (true ^ this.ifKnoxAnalyticsContainer.hasUserRestriction("no_share_location", i))) {
                    return;
                }
            }
        } catch (Exception unused) {
        }
        requestSendSnapshotLog(i);
    }

    public void requestSendSnapshotLog(int i) {
        this.analyticsHandler.sendMessage(this.analyticsHandler.obtainMessage(2, i, 0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void sendSnapshotLog(int i) {
        int i2;
        try {
            if (SemPersonaManager.isDualAppId(i)) {
                return;
            }
            Bundle bundle = new Bundle();
            int containerType = this.basicContainerAnalytics.getContainerType(i);
            bundle.putInt("cTp", containerType);
            int i3 = 1;
            bundle.putInt("OToE", i == 0 ? 0 : 1);
            int knoxScreenTimeOut = this.ifKnoxAnalyticsContainer.getKnoxScreenTimeOut(i);
            if (knoxScreenTimeOut == 0) {
                i2 = 1;
            } else if (knoxScreenTimeOut == -1) {
                i2 = 2;
            } else if (knoxScreenTimeOut == -2) {
                i2 = 3;
            } else {
                knoxScreenTimeOut = Math.max(knoxScreenTimeOut, 5000);
                i2 = 0;
            }
            bundle.putInt("lckTmoutTp", i2);
            bundle.putInt("lckTmoutV", knoxScreenTimeOut);
            LockPatternUtils lockPatternUtils = new LockPatternUtils(this.context);
            int biometricState = lockPatternUtils.getBiometricState(1, i);
            int biometricState2 = lockPatternUtils.getBiometricState(256, i) << 2;
            Object[] objArr = this.ifKnoxAnalyticsContainer.getKnoxFingerprintPlus(i) == 1;
            bundle.putInt("lckTp", getPasswordType(this.ifKnoxAnalyticsContainer.getActivePasswordQuality(i)));
            if (DEBUG) {
                Log.d("STATUS_SNAPSHOT", "bioInf " + (biometricState | biometricState2));
            }
            bundle.putInt("bioInf", biometricState2 | biometricState);
            bundle.putInt("mfaEnb", objArr != false ? 1 : 0);
            bundle.putString("disPrnSrv", this.ifKnoxAnalyticsContainer.getDisabledPrintServices(i));
            bundle.putInt("clrId", this.ifKnoxAnalyticsContainer.getCallerIdToShow(i) == 1 ? 1 : 0);
            bundle.putInt("nSntz", this.ifKnoxAnalyticsContainer.getLockScreenAllowPrivateNotification(i));
            if (i != 0) {
                i3 = 1 ^ (this.ifKnoxAnalyticsContainer.hasUserRestriction("no_share_location", i) ? 1 : 0);
            } else if (this.ifKnoxAnalyticsContainer.getLocationProvidersAllowed(i) == 0) {
                i3 = 0;
            }
            bundle.putInt("lct", i3);
            this.locationRestrictionMap.put(Integer.valueOf(i), Integer.valueOf(i3));
            if (containerType == 2) {
                ComponentName adminComponentForLegacy = this.ifKnoxAnalyticsContainer.getAdminComponentForLegacy(i);
                String packageName = adminComponentForLegacy != null ? adminComponentForLegacy.getPackageName() : "unknown";
                bundle.putString("daPn", packageName);
                bundle.putString("daPv", this.ifKnoxAnalyticsContainer.getPackageInfo(packageName, 0).versionName);
            } else {
                bundle.putString("daPn", "");
                bundle.putString("daPv", "");
            }
            logEvent(bundle, "STATUS_SNAPSHOT");
        } catch (Exception e) {
            Log.d("PersonaManagerService:KnoxAnalytics", "sendSnapshotLog Failed." + e);
        }
    }

    public void onBroadcastIntentReceived(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
        if ("com.samsung.android.knox.profilepolicy.intent.action.update".equals(intent.getAction())) {
            this.basicContainerAnalytics.logProfilePolicyRestriction(intent.getStringExtra("restrictionName"), intent.getIntExtra("restrictionAllowed", 0), intent.getIntExtra(KnoxCustomManagerService.CONTAINER_ID_ZERO, 0));
        }
        if (((this.ifKnoxAnalyticsContainer.isDoEnabled(intExtra) || intExtra != 0) && !this.ifKnoxAnalyticsContainer.isSecureFolderId(intExtra) && (intExtra == 0 || this.ifKnoxAnalyticsContainer.isKnoxId(intExtra) || intExtra == this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId())) || "samsung.knox.intent.action.RCP_POLICY_CHANGED".equals(intent.getAction()) || "android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction()) || "android.intent.action.SCREEN_OFF".equals(intent.getAction()) || "samsung.knox.intent.action.rcp.MOVEMENT".equals(intent.getAction())) {
            if (("android.intent.action.PACKAGE_ADDED".equals(intent.getAction()) || "android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) && SemPersonaManager.getKnoxContainerVersion().compareTo(SemPersonaManager.KnoxContainerVersion.KNOX_CONTAINER_VERSION_3_3_0) >= 0) {
                if (intent.getData() == null) {
                    return;
                }
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra)) {
                    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DUALDAR_PACKAGE_ADDED");
                    boolean equals = "android.intent.action.PACKAGE_ADDED".equals(intent.getAction());
                    knoxAnalyticsData.setProperty("pN", schemeSpecificPart);
                    knoxAnalyticsData.setProperty("add", equals ? 1 : 0);
                    Log.d("PersonaManagerService:DualDARAnalytics", "On Pkg Add, Data values : packageName = " + schemeSpecificPart + ", add = " + (equals ? 1 : 0));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Payload / ");
                    sb.append(knoxAnalyticsData.toString());
                    Log.d("PersonaManagerService:DualDARAnalytics", sb.toString());
                    KnoxAnalytics.log(knoxAnalyticsData);
                    return;
                }
                if (intExtra == this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId()) {
                    if (intExtra != 0) {
                        this.separatedAppsAnalytics.onPackageChanged(intExtra, schemeSpecificPart, "android.intent.action.PACKAGE_ADDED".equals(intent.getAction()) ? 1 : 0);
                        return;
                    }
                    return;
                } else {
                    if (this.ifKnoxAnalyticsContainer.isKnoxId(intExtra)) {
                        this.basicContainerAnalytics.logPackageChanged(intExtra, schemeSpecificPart, "android.intent.action.PACKAGE_ADDED".equals(intent.getAction()) ? 1 : 0);
                        return;
                    }
                    return;
                }
            }
            if ("android.intent.action.MANAGED_PROFILE_AVAILABLE".equals(intent.getAction())) {
                this.basicContainerAnalytics.logWorkModeOn(intExtra);
                return;
            }
            if ("android.intent.action.MANAGED_PROFILE_UNAVAILABLE".equals(intent.getAction())) {
                this.basicContainerAnalytics.logWorkModeOff(intExtra);
                return;
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction()) || "android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                int i = this.mPostActiveUserId;
                if (i != 0 && !SemPersonaManager.isSecureFolderId(i) && SemPersonaManager.isKnoxId(this.mPostActiveUserId)) {
                    checkTimeAndSendAnalytics(this.mPostActiveUserId, this.mPostActivePackage, SystemClock.elapsedRealtime() - this.mPostActiveTime);
                }
                this.mPostActiveUserId = 0;
                this.mPostActivePackage = "";
                this.mPostActiveTime = SystemClock.elapsedRealtime();
                return;
            }
            if ("samsung.knox.intent.action.rcp.MOVEMENT".equals(intent.getAction())) {
                this.basicContainerAnalytics.logMoveToKnox(intExtra, intent.getBooleanExtra("move_to_knox", false));
            } else if ("samsung.knox.intent.action.CHANGE_LOCK_TYPE".equals(intent.getAction())) {
                requestSendSnapshotLog(intExtra);
            }
        }
    }

    public void knoxAnalyticsContainer(int i, String str) {
        String clientInfo;
        int i2;
        if (i == 0 || SemPersonaManager.isSecureFolderId(i) || !SemPersonaManager.isKnoxId(i) || !SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            return;
        }
        Log.d("PersonaManagerService:DualDARAnalytics", "DualDAR Logging");
        if (str.equals("DUAL_DAR_USER_ADDED")) {
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_DUALDAR", 1, "TYPE_OF_DUALDAR");
            IDualDARPolicy asInterface = IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean isDualDARIntentProvision = ContainerDependencyWrapper.isDualDARIntentProvision(this.context);
            boolean isDualDARTrialPeriod = ContainerDependencyWrapper.isDualDARTrialPeriod(this.context);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int dualDARType = ContainerDependencyWrapper.getDualDARType(i);
            Log.d("PersonaManagerService:DualDARAnalytics", "Dual Dar Container Type Created: " + dualDARType);
            if (SemPersonaManager.isDualDARNativeCrypto(i)) {
                clientInfo = "Samsung Built-In";
                i2 = 1;
            } else {
                if (asInterface != null) {
                    try {
                        clientInfo = asInterface.getClientInfo(i);
                    } catch (RemoteException unused) {
                        Log.e("PersonaManagerService:DualDARAnalytics", "getClientInfo failed");
                    }
                    i2 = 0;
                }
                clientInfo = " ";
                i2 = 0;
            }
            knoxAnalyticsData.setProperty("dTp", dualDARType);
            knoxAnalyticsData.setProperty("sLyr", i2);
            knoxAnalyticsData.setProperty("sLyrName", clientInfo);
            if (!isDualDARIntentProvision && isDualDARTrialPeriod) {
                knoxAnalyticsData.setProperty("dAppTp", 0);
            } else {
                knoxAnalyticsData.setProperty("dAppTp", 1);
            }
            Log.d("PersonaManagerService:DualDARAnalytics", "Payload / " + knoxAnalyticsData.toString());
            KnoxAnalytics.log(knoxAnalyticsData);
            KnoxAnalyticsData knoxAnalyticsData2 = new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DAR_AUTH_TYPE");
            knoxAnalyticsData2.setProperty("lckTp", 0);
            Log.d("PersonaManagerService:DualDARAnalytics", "Payload / " + knoxAnalyticsData2.toString());
            this.ifKnoxAnalyticsContainer.sendAnalyticsLog(knoxAnalyticsData2);
        }
    }

    public void onWorkProfileAdded(int i, String str) {
        if (SemPersonaManager.isDualAppId(i)) {
            return;
        }
        this.basicContainerAnalytics.logWorkProfileAdded(i, str);
    }

    public void onWorkProfileRemoved(int i) {
        if (this.ifKnoxAnalyticsContainer.isSecureFolderId(i)) {
            return;
        }
        this.basicContainerAnalytics.logWorkProfileRemoved(i);
    }

    public void onDeviceOwnerChanged(String str) {
        this.basicContainerAnalytics.logDeviceOwnerChanged(str);
    }

    public final JSONObject createNewJSONObjectForGivenPackage(String str, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("usageTime", j);
            jSONObject.put("package_name", str);
            jSONObject.put("appLaunchCount", 1);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public final void saveJSONObjectForGivenPackage(SharedPreferences sharedPreferences, SharedPreferences.Editor editor, String str, long j) {
        JSONArray jSONArray;
        try {
            String string = sharedPreferences.getString("eventJSONArray", "");
            if (DEBUG) {
                Log.e("saveJSONObjectForGivenPackage", string);
            }
            if (!TextUtils.isEmpty(string)) {
                jSONArray = new JSONArray(string);
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= jSONArray.length()) {
                        break;
                    }
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                    if (jSONObject.getString("package_name").equals(str)) {
                        long j2 = jSONObject.getLong("usageTime") + j;
                        int i2 = jSONObject.getInt("appLaunchCount") + 1;
                        jSONObject.put("usageTime", j2);
                        jSONObject.put("appLaunchCount", i2);
                        jSONObject.put("package_name", str);
                        z = true;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    jSONArray.put(createNewJSONObjectForGivenPackage(str, j));
                }
            } else {
                jSONArray = new JSONArray();
                jSONArray.put(createNewJSONObjectForGivenPackage(str, j));
            }
            saveJSONArray(jSONArray, editor);
            if (DEBUG) {
                Log.e("KnoxAnalyticsContainer", jSONArray.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final void saveJSONArray(JSONArray jSONArray, SharedPreferences.Editor editor) {
        editor.remove("eventJSONArray").putString("eventJSONArray", jSONArray.toString()).apply();
    }

    public final void deleteAppUsageEntry(SharedPreferences.Editor editor) {
        editor.remove("eventJSONArray").apply();
    }

    public final boolean ifSameDate() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        long currentTimeMillis = System.currentTimeMillis();
        long j = defaultSharedPreferences.getLong("last_event_sent_date", 0L);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j);
        return calendar2.get(1) == calendar.get(1) && calendar2.get(2) == calendar.get(2) && calendar2.get(5) == calendar.get(5);
    }

    public final void checkTimeAndSendAnalytics(int i, String str, long j) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        if (!ifSameDate()) {
            sendWorkUsageLogs(defaultSharedPreferences, i);
            edit.putLong("last_event_sent_date", System.currentTimeMillis());
            edit.apply();
        }
        saveJSONObjectForGivenPackage(defaultSharedPreferences, edit, str, j);
    }

    public final void sendWorkUsageLogs(SharedPreferences sharedPreferences, int i) {
        String string = sharedPreferences.getString("eventJSONArray", "");
        Log.e("KnoxAnalyticsContainer", "sendWorkUsageLogs " + string);
        try {
            if (TextUtils.isEmpty(string)) {
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                this.basicContainerAnalytics.logWorkAppUsgae(i, jSONObject.getString("package_name"), jSONObject.getLong("usageTime"), jSONObject.getInt("appLaunchCount"));
            }
            if (DEBUG) {
                Log.e("KnoxAnalyticsContainer", "sendWorkUsageLogs " + jSONArray);
            }
            deleteAppUsageEntry(sharedPreferences.edit());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
