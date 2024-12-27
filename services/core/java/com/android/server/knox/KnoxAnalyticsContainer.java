package com.android.server.knox;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.ddar.DDCache;
import com.android.server.pm.PersonaServiceHelper;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxAnalyticsContainer {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public final AnalyticsHandler analyticsHandler;
    public final BasicContainerAnalytics basicContainerAnalytics;
    public final Context context;
    public final DevicePolicyManagerAnalytics dpmAnalytics;
    public final IKnoxAnalyticsContainerImpl ifKnoxAnalyticsContainer;
    public final SeparatedAppsAnalytics separatedAppsAnalytics;
    public final HashMap locationRestrictionMap = new HashMap();
    public int mPostActiveUserId = 0;
    public String mPostActivePackage = "";
    public long mPostActiveTime = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnalyticsHandler extends Handler {
        public AnalyticsHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            Log.d("AnalyticsContainerHandler", "handleMessage : " + message);
            int i2 = message.what;
            KnoxAnalyticsContainer knoxAnalyticsContainer = KnoxAnalyticsContainer.this;
            if (i2 == 1) {
                int i3 = message.arg1;
                IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl =
                        knoxAnalyticsContainer.ifKnoxAnalyticsContainer;
                iKnoxAnalyticsContainerImpl.getClass();
                try {
                    if (i3 == 0) {
                        if (((Integer)
                                                knoxAnalyticsContainer.locationRestrictionMap.get(
                                                        Integer.valueOf(i3)))
                                        .intValue()
                                == (Settings.Secure.getIntForUser(
                                                        iKnoxAnalyticsContainerImpl.mContext
                                                                .getContentResolver(),
                                                        "location_providers_allowed",
                                                        0,
                                                        i3)
                                                == 0
                                        ? 0
                                        : 1)) {
                            return;
                        }
                    } else {
                        if (((Integer)
                                                knoxAnalyticsContainer.locationRestrictionMap.get(
                                                        Integer.valueOf(i3)))
                                        .intValue()
                                == (!iKnoxAnalyticsContainerImpl
                                        .getUserManager()
                                        .hasUserRestriction(
                                                "no_share_location", new UserHandle(i3)))) {
                            return;
                        }
                    }
                } catch (Exception unused) {
                }
                AnalyticsHandler analyticsHandler = knoxAnalyticsContainer.analyticsHandler;
                analyticsHandler.sendMessage(analyticsHandler.obtainMessage(2, i3, 0));
                return;
            }
            if (i2 != 2) {
                return;
            }
            int i4 = message.arg1;
            IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl2 =
                    knoxAnalyticsContainer.ifKnoxAnalyticsContainer;
            try {
                if (iKnoxAnalyticsContainerImpl2.isLoggingAllowedForUser(i4)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(
                            "cTp",
                            knoxAnalyticsContainer.basicContainerAnalytics.getContainerType(i4));
                    bundle.putInt("OToE", (i4 == 0 ? 1 : 0) ^ 1);
                    int intForUser =
                            SemPersonaManager.isSecureFolderId(i4)
                                    ? Settings.System.getIntForUser(
                                            iKnoxAnalyticsContainerImpl2.mContext
                                                    .getContentResolver(),
                                            "knox_screen_off_timeout",
                                            -1,
                                            i4)
                                    : Settings.Secure.getIntForUser(
                                            iKnoxAnalyticsContainerImpl2.mContext
                                                    .getContentResolver(),
                                            "knox_screen_off_timeout",
                                            -1,
                                            i4);
                    if (intForUser == 0) {
                        i = 1;
                    } else if (intForUser == -1) {
                        i = 2;
                    } else if (intForUser == -2) {
                        i = 3;
                    } else {
                        intForUser = Math.max(intForUser, 5000);
                        i = 0;
                    }
                    bundle.putInt("lckTmoutTp", i);
                    bundle.putInt("lckTmoutV", intForUser);
                    LockPatternUtils lockPatternUtils =
                            new LockPatternUtils(knoxAnalyticsContainer.context);
                    int biometricState = lockPatternUtils.getBiometricState(1, i4);
                    int biometricState2 = lockPatternUtils.getBiometricState(256, i4) << 2;
                    int i5 =
                            Settings.System.getIntForUser(
                                                    iKnoxAnalyticsContainerImpl2.mContext
                                                            .getContentResolver(),
                                                    "knox_finger_print_plus",
                                                    0,
                                                    i4)
                                            == 1
                                    ? 1
                                    : 0;
                    int activePasswordQuality =
                            new LockPatternUtils(iKnoxAnalyticsContainerImpl2.mContext)
                                    .getActivePasswordQuality(i4);
                    bundle.putInt(
                            "lckTp",
                            activePasswordQuality != 65536
                                    ? (activePasswordQuality == 131072
                                                    || activePasswordQuality == 196608)
                                            ? 3
                                            : (activePasswordQuality == 262144
                                                            || activePasswordQuality == 327680
                                                            || activePasswordQuality == 393216)
                                                    ? 4
                                                    : 1
                                    : 2);
                    if (KnoxAnalyticsContainer.DEBUG) {
                        Log.d("STATUS_SNAPSHOT", "bioInf " + (biometricState | biometricState2));
                    }
                    bundle.putInt("bioInf", biometricState | biometricState2);
                    bundle.putInt("mfaEnb", i5);
                    bundle.putString(
                            "disPrnSrv",
                            Settings.Secure.getStringForUser(
                                    iKnoxAnalyticsContainerImpl2.mContext.getContentResolver(),
                                    "disabled_print_services",
                                    i4));
                    UserInfo userInfo =
                            iKnoxAnalyticsContainerImpl2.getUserManager().getUserInfo(i4);
                    ContentResolver contentResolver =
                            iKnoxAnalyticsContainerImpl2.mContext.getContentResolver();
                    StringBuilder sb = new StringBuilder("caller_id_to_show_");
                    sb.append(userInfo.name);
                    bundle.putInt(
                            "clrId",
                            Settings.System.getIntForUser(contentResolver, sb.toString(), 1, i4)
                                            == 1
                                    ? 1
                                    : 0);
                    bundle.putInt(
                            "nSntz",
                            Settings.Secure.getIntForUser(
                                    iKnoxAnalyticsContainerImpl2.mContext.getContentResolver(),
                                    "lock_screen_allow_private_notifications",
                                    0,
                                    i4));
                    if (i4 != 0) {
                        r5 =
                                1
                                        ^ (iKnoxAnalyticsContainerImpl2
                                                        .getUserManager()
                                                        .hasUserRestriction(
                                                                "no_share_location",
                                                                new UserHandle(i4))
                                                ? 1
                                                : 0);
                    } else if (Settings.Secure.getIntForUser(
                                    iKnoxAnalyticsContainerImpl2.mContext.getContentResolver(),
                                    "location_providers_allowed",
                                    0,
                                    i4)
                            == 0) {
                        r5 = 0;
                    }
                    bundle.putInt("lct", r5);
                    knoxAnalyticsContainer.locationRestrictionMap.put(
                            Integer.valueOf(i4), Integer.valueOf(r5));
                    String deviceOwnerPackage = IKnoxAnalyticsContainerImpl.getDeviceOwnerPackage();
                    String profileOwnerPackage =
                            iKnoxAnalyticsContainerImpl2.getProfileOwnerPackage(i4);
                    if (deviceOwnerPackage == null) {
                        deviceOwnerPackage = profileOwnerPackage;
                    }
                    String str =
                            TextUtils.isEmpty(deviceOwnerPackage)
                                    ? null
                                    : IKnoxAnalyticsContainerImpl.getPackageInfo(
                                                    0, deviceOwnerPackage)
                                            .versionName;
                    bundle.putString("daPn", deviceOwnerPackage);
                    bundle.putString("daPv", str);
                    knoxAnalyticsContainer.logEvent(bundle);
                }
            } catch (Exception e) {
                Log.d("PersonaManagerService:KnoxAnalytics", "sendSnapshotLog Failed." + e);
            }
        }
    }

    public KnoxAnalyticsContainer(
            Context context, IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl) {
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainerImpl;
        this.context = context;
        HandlerThread handlerThread = new HandlerThread("KnoxAnalyticsContainer", 10);
        handlerThread.start();
        this.analyticsHandler = new AnalyticsHandler(handlerThread.getLooper());
        this.separatedAppsAnalytics = new SeparatedAppsAnalytics(iKnoxAnalyticsContainerImpl);
        this.basicContainerAnalytics =
                new BasicContainerAnalytics(context, iKnoxAnalyticsContainerImpl);
        this.dpmAnalytics = new DevicePolicyManagerAnalytics(context, iKnoxAnalyticsContainerImpl);
    }

    public static JSONObject createNewJSONObjectForGivenPackage(long j, String str) {
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

    /* JADX WARN: Removed duplicated region for block: B:41:0x010f A[Catch: JSONException -> 0x0137, TryCatch #0 {JSONException -> 0x0137, blocks: (B:39:0x0109, B:41:0x010f, B:42:0x0115, B:44:0x011b, B:47:0x013b, B:48:0x014d), top: B:38:0x0109 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkTimeAndSendAnalytics(int r22, java.lang.String r23, long r24) {
        /*
            Method dump skipped, instructions count: 359
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.KnoxAnalyticsContainer.checkTimeAndSendAnalytics(int,"
                    + " java.lang.String, long):void");
    }

    public final void knoxAnalyticsContainer(int i) {
        String clientInfo;
        int i2;
        if (this.ifKnoxAnalyticsContainer.isLoggingAllowedForUser(i)
                && SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            Log.d("PersonaManagerService:DualDARAnalytics", "DualDAR Logging");
            KnoxAnalyticsData knoxAnalyticsData =
                    new KnoxAnalyticsData("KNOX_DUALDAR", 1, "TYPE_OF_DUALDAR");
            IDualDARPolicy asInterface =
                    IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            ContainerDependencyWrapper containerDependencyWrapper =
                    ContainerDependencyWrapper.sInstance;
            String str = DDCache.getInstance().get(0, "IS_DUAL_DAR_INTENT_PROVISIONING");
            boolean z = !TextUtils.isEmpty(str) && str.equalsIgnoreCase("TRUE");
            String str2 = DDCache.getInstance().get(0, "IS_DUAL_DAR_TRIAL_PERIOD");
            boolean z2 = !TextUtils.isEmpty(str2) && str2.equalsIgnoreCase("TRUE");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int dualDARType = PersonaServiceHelper.getDualDARType(i);
            Log.d(
                    "PersonaManagerService:DualDARAnalytics",
                    "Dual Dar Container Type Created: " + dualDARType);
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
            if (z || !z2) {
                knoxAnalyticsData.setProperty("dAppTp", 1);
            } else {
                knoxAnalyticsData.setProperty("dAppTp", 0);
            }
            Log.d(
                    "PersonaManagerService:DualDARAnalytics",
                    "Payload / " + knoxAnalyticsData.toString());
            KnoxAnalytics.log(knoxAnalyticsData);
            KnoxAnalyticsData knoxAnalyticsData2 =
                    new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DAR_AUTH_TYPE");
            knoxAnalyticsData2.setProperty("lckTp", 0);
            Log.d(
                    "PersonaManagerService:DualDARAnalytics",
                    "Payload / " + knoxAnalyticsData2.toString());
            IKnoxAnalyticsContainerImpl.sendAnalyticsLog(knoxAnalyticsData2);
        }
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.Serializable, java.lang.String[]] */
    public final void logEvent(Bundle bundle) {
        IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl = this.ifKnoxAnalyticsContainer;
        if (iKnoxAnalyticsContainerImpl.personaManagerService.getAppSeparationId() > 0) {
            return;
        }
        KnoxAnalyticsData knoxAnalyticsData =
                new KnoxAnalyticsData("KNOX_CONTAINER", 6, "STATUS_SNAPSHOT");
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof Integer) {
                knoxAnalyticsData.setProperty(str, (Integer) obj);
            } else if (obj instanceof String) {
                knoxAnalyticsData.setProperty(str, (String) obj);
            } else if (obj instanceof Long) {
                knoxAnalyticsData.setProperty(str, (Long) obj);
            } else if (obj instanceof String[]) {
                knoxAnalyticsData.setProperty(str, (String[]) obj);
            }
        }
        iKnoxAnalyticsContainerImpl.getClass();
        IKnoxAnalyticsContainerImpl.sendAnalyticsLog(knoxAnalyticsData);
        if (DEBUG) {
            Log.d(
                    "PersonaManagerService:KnoxAnalytics",
                    "STATUS_SNAPSHOT / " + knoxAnalyticsData.toString());
        }
    }

    public final void logEventActivationForAppSep(ArrayList arrayList, ArrayList arrayList2) {
        SeparatedAppsAnalytics separatedAppsAnalytics = this.separatedAppsAnalytics;
        separatedAppsAnalytics.getClass();
        try {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                Bundle bundle = new Bundle();
                bundle.putString(KnoxAnalyticsDataConverter.EVENT, "PACKAGE_INFO");
                bundle.putString("pN", str);
                bundle.putInt("add", 1);
                bundle.putInt("noIP", 0);
                bundle.putInt("noWP", arrayList2.size());
                separatedAppsAnalytics.logEvent(bundle, "PACKAGE_INFO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onSeparatedAppsCreated() {
        SeparatedAppsAnalytics separatedAppsAnalytics = this.separatedAppsAnalytics;
        separatedAppsAnalytics.ifKnoxAnalyticsContainer.getClass();
        Bundle appSeparationConfig = KnoxContainerManager.getAppSeparationConfig();
        if (appSeparationConfig == null) {
            return;
        }
        boolean z = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
        String deviceOwnerPackage = IKnoxAnalyticsContainerImpl.getDeviceOwnerPackage();
        Bundle m =
                FreecessController$$ExternalSyntheticOutline0.m(
                        z ? 1 : 0,
                        KnoxAnalyticsDataConverter.EVENT,
                        "APP_SEPARATION_CREATED",
                        "wP");
        m.putString("pN", deviceOwnerPackage);
        m.putString(
                "pV",
                IKnoxAnalyticsContainerImpl.getPackageInfo(0, deviceOwnerPackage).versionName);
        separatedAppsAnalytics.logEvent(m, "APP_SEPARATION_CREATED");
    }

    public final void onSeparatedAppsPolicyUpdated() {
        SeparatedAppsAnalytics separatedAppsAnalytics = this.separatedAppsAnalytics;
        separatedAppsAnalytics.ifKnoxAnalyticsContainer.getClass();
        Bundle appSeparationConfig = KnoxContainerManager.getAppSeparationConfig();
        if (appSeparationConfig == null) {
            return;
        }
        boolean z = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
        ArrayList<String> stringArrayList =
                appSeparationConfig.getStringArrayList("APP_SEPARATION_APP_LIST");
        String str = "";
        if (stringArrayList != null) {
            String str2 = "";
            for (String str3 : stringArrayList) {
                str2 =
                        str2.equals("")
                                ? str3
                                : AnyMotionDetector$$ExternalSyntheticOutline0.m(str3, ",", str2);
            }
            str = str2;
        }
        Bundle m =
                FreecessController$$ExternalSyntheticOutline0.m(
                        z ? 1 : 0,
                        KnoxAnalyticsDataConverter.EVENT,
                        "APP_SEPARATION_POLICYUPDATE",
                        "wP");
        m.putString("wLp", str);
        separatedAppsAnalytics.logEvent(m, "APP_SEPARATION_POLICYUPDATE");
    }
}
