package com.android.server.notification.edgelighting;

import android.app.INotificationManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.notification.edgelighting.policy.EdgeLightingPolicyRepository;

import com.samsung.android.cover.CoverManager;
import com.samsung.android.edge.EdgeLightingPolicyInfo;
import com.samsung.android.feature.SemFloatingFeature;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EdgeLightingPolicyManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final Uri DESKTOP_SETTINGS_URI;
    public static final boolean EMERGENCY_MODE_ENABLED;
    public final EdgeLightingPolicyRepository mBlackPolicy;
    public final Context mContext;
    public CoverManager mCoverManager;
    public final AnonymousClass1 mDesktopSettingsObserver;
    public boolean mDisableNotificationAlerts;
    public final AnonymousClass1 mHeadsUpObserver;
    public boolean mIsTouchpadEnabled;
    public INotificationManager mNm;
    public final PackageManager mPackageManager;
    public ComponentName mResumedComponent;
    public long mResumedComponentTime;
    public int mStatusBarDisabled1;
    public int mSuppressed;
    public boolean mUseHeadsUp;
    public int mUserId;
    public boolean mVrMode;
    public final AnonymousClass3 mVrStateCallbacks;
    public final EdgeLightingPolicyRepository mWhitePolicy;
    public int mPolicyType = 0;
    public long mPolicyVersion = 0;
    public final ArrayList mPriorityPolicy = new ArrayList();
    public final HashMap mNotificationMap = new HashMap();
    public final NotificationGroup mNotificationGroup = new NotificationGroup();
    public final ArrayList mDisableRecords = new ArrayList();
    public final SparseArray mDisabledPackages = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.edgelighting.EdgeLightingPolicyManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ EdgeLightingPolicyManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(
                EdgeLightingPolicyManager edgeLightingPolicyManager, Handler handler, int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = edgeLightingPolicyManager;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    EdgeLightingPolicyManager edgeLightingPolicyManager = this.this$0;
                    boolean z2 = !edgeLightingPolicyManager.mDisableNotificationAlerts;
                    edgeLightingPolicyManager.mUseHeadsUp = z2;
                    boolean z3 = EdgeLightingPolicyManager.DEBUG;
                    Log.d(
                            "EdgeLightingPolicyManager",
                            "heads up is ".concat(z2 ? "enabled" : "disabled"));
                    break;
                default:
                    super.onChange(z);
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            switch (this.$r8$classId) {
                case 1:
                    super.onChange(z, uri);
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment != null && lastPathSegment.equals("touchpad_enabled")) {
                        EdgeLightingPolicyManager edgeLightingPolicyManager = this.this$0;
                        edgeLightingPolicyManager.getClass();
                        Bundle bundle = new Bundle();
                        bundle.putString("key", lastPathSegment);
                        String str = "false";
                        bundle.putString("def", "false");
                        try {
                            Bundle call =
                                    edgeLightingPolicyManager
                                            .mContext
                                            .getContentResolver()
                                            .call(
                                                    EdgeLightingPolicyManager.DESKTOP_SETTINGS_URI,
                                                    "getSettings",
                                                    (String) null,
                                                    bundle);
                            if (call != null) {
                                str = call.getString(lastPathSegment);
                            }
                        } catch (IllegalArgumentException e) {
                            Log.e("EdgeLightingPolicyManager", "Failed to get settings", e);
                        }
                        edgeLightingPolicyManager.mIsTouchpadEnabled = "true".equals(str);
                        break;
                    }
                    break;
                default:
                    super.onChange(z, uri);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisableRecord implements IBinder.DeathRecipient {
        public String packageName;
        public IBinder token;
        public int what;

        public DisableRecord() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = EdgeLightingPolicyManager.DEBUG;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("binder died for packageName = "),
                    this.packageName,
                    "EdgeLightingPolicyManager");
            EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.this;
            String str = this.packageName;
            IBinder iBinder = this.token;
            synchronized (edgeLightingPolicyManager.mDisableRecords) {
                edgeLightingPolicyManager.manageDisableRecoredLocked(0, iBinder, str);
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GroupNotificationData {
        public final HashMap mChildMap = new LinkedHashMap();
        public NotificationData mSumaaryNotification;

        public final NotificationData getLastChild() {
            if (this.mChildMap.size() <= 0) {
                return null;
            }
            Map.Entry entry =
                    (Map.Entry) new ArrayList(this.mChildMap.entrySet()).get(r0.size() - 1);
            boolean z = EdgeLightingPolicyManager.DEBUG;
            Slog.d("EdgeLightingPolicyManager.NotificationGroup", " last child " + entry);
            return (NotificationData) entry.getValue();
        }

        public final void putChild(String str, NotificationData notificationData) {
            synchronized (this.mChildMap) {
                try {
                    if (this.mChildMap.containsKey(str)) {
                        boolean z = EdgeLightingPolicyManager.DEBUG;
                        Slog.d(
                                "EdgeLightingPolicyManager.NotificationGroup",
                                "Remove previous child data. " + str);
                        this.mChildMap.remove(str);
                    }
                    this.mChildMap.put(str, notificationData);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("summary : ");
            sb.append(this.mSumaaryNotification);
            sb.append(" , child : ");
            sb.append(this.mChildMap.size());
            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingPolicyManager.DEBUG) {
                synchronized (this.mChildMap) {
                    try {
                        for (Map.Entry entry : this.mChildMap.entrySet()) {
                            sb.append(
                                    "key = "
                                            + ((String) entry.getKey())
                                            + " , value = "
                                            + entry.getValue()
                                            + ",");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            sb.append(" } ");
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationData {
        public Bundle mNotificationInfo;
        public long mTime;

        public final String toString() {
            return " NotificationData { tag= "
                    + this.mNotificationInfo.getString("noti_tag")
                    + " } ";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationGroup {
        public final HashMap mGroupMap = new HashMap();

        public final void dump() {
            synchronized (this.mGroupMap) {
                try {
                    if (EdgeLightingPolicyManager.DEBUG) {
                        for (Map.Entry entry : this.mGroupMap.entrySet()) {
                            StringBuilder sb = new StringBuilder();
                            boolean z = EdgeLightingPolicyManager.DEBUG;
                            sb.append("EdgeLightingPolicyManager");
                            sb.append(".NotificationGroup");
                            Slog.d(
                                    sb.toString(),
                                    "group key = "
                                            + ((String) entry.getKey())
                                            + " ,value= "
                                            + entry.getValue());
                        }
                    } else {
                        Slog.d(
                                "EdgeLightingPolicyManager.NotificationGroup",
                                "group notification count : " + this.mGroupMap.size());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateNotificationData(
                StatusBarNotification statusBarNotification, NotificationData notificationData) {
            NotificationData notificationData2;
            synchronized (this.mGroupMap) {
                if (statusBarNotification.getNotification().isGroupSummary()
                        && notificationData.mNotificationInfo.getBoolean("isHeadup", false)) {
                    boolean z = EdgeLightingPolicyManager.DEBUG;
                    Slog.d(
                            "EdgeLightingPolicyManager.NotificationGroup",
                            "updateNotificationData replace :"
                                    + statusBarNotification.getGroupKey()
                                    + ", group  size="
                                    + this.mGroupMap.size());
                    GroupNotificationData groupNotificationData =
                            (GroupNotificationData)
                                    this.mGroupMap.get(statusBarNotification.getGroupKey());
                    if (groupNotificationData != null) {
                        String key = statusBarNotification.getKey();
                        synchronized (groupNotificationData.mChildMap) {
                            notificationData2 =
                                    (NotificationData) groupNotificationData.mChildMap.get(key);
                        }
                        if (notificationData2 != null) {
                            notificationData.mNotificationInfo.putCharSequence(
                                    "tickerText",
                                    notificationData2.mNotificationInfo.getCharSequence(
                                            "tickerText"));
                            notificationData.mNotificationInfo.putCharSequence(
                                    "titleText",
                                    notificationData2.mNotificationInfo.getCharSequence(
                                            "titleText"));
                            notificationData.mNotificationInfo.putCharSequence(
                                    "text",
                                    notificationData2.mNotificationInfo.getCharSequence("text"));
                            notificationData.mNotificationInfo.putCharSequence(
                                    "subText",
                                    notificationData2.mNotificationInfo.getCharSequence("subText"));
                        }
                    } else {
                        groupNotificationData = new GroupNotificationData();
                    }
                    groupNotificationData.mSumaaryNotification = notificationData;
                    this.mGroupMap.put(statusBarNotification.getGroupKey(), groupNotificationData);
                } else {
                    GroupNotificationData groupNotificationData2 =
                            (GroupNotificationData)
                                    this.mGroupMap.get(statusBarNotification.getGroupKey());
                    if (groupNotificationData2 == null) {
                        groupNotificationData2 = new GroupNotificationData();
                    }
                    if (statusBarNotification.getNotification().isGroupSummary()) {
                        groupNotificationData2.mSumaaryNotification = notificationData;
                    } else {
                        groupNotificationData2.putChild(
                                statusBarNotification.getKey(), notificationData);
                    }
                    this.mGroupMap.put(statusBarNotification.getGroupKey(), groupNotificationData2);
                    dump();
                }
            }
        }
    }

    static {
        EMERGENCY_MODE_ENABLED =
                SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING")
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING");
        DESKTOP_SETTINGS_URI =
                Uri.parse(
                        "content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    public EdgeLightingPolicyManager(Context context) {
        Handler handler = new Handler();
        this.mUseHeadsUp = false;
        this.mStatusBarDisabled1 = 0;
        this.mSuppressed = 0;
        this.mUserId = 0;
        this.mDisableNotificationAlerts = false;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, handler, 0);
        this.mHeadsUpObserver = anonymousClass1;
        AnonymousClass1 anonymousClass12 = new AnonymousClass1(this, handler, 1);
        IVrStateCallbacks.Stub stub =
                new IVrStateCallbacks
                        .Stub() { // from class:
                                  // com.android.server.notification.edgelighting.EdgeLightingPolicyManager.3
                    public final void onVrStateChanged(boolean z) {
                        EdgeLightingPolicyManager.this.mVrMode = z;
                    }
                };
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mWhitePolicy = new EdgeLightingPolicyRepository();
        this.mBlackPolicy = new EdgeLightingPolicyRepository();
        anonymousClass1.onChange(true);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("heads_up_notifications_enabled"),
                        true,
                        anonymousClass1);
        anonymousClass12.onChange(true);
        context.getContentResolver()
                .registerContentObserver(DESKTOP_SETTINGS_URI, true, anonymousClass12);
        this.mNm = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        IVrManager asInterface =
                IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(stub);
            } catch (RemoteException e) {
                Slog.e(
                        "EdgeLightingPolicyManager",
                        "Failed to register VR mode state listener: " + e);
            }
        }
    }

    public static boolean isEmptyText(Bundle bundle) {
        if (bundle == null) {
            return true;
        }
        return TextUtils.isEmpty(bundle.getCharSequence("tickerText", null))
                && TextUtils.isEmpty(bundle.getCharSequence("titleText", null))
                && TextUtils.isEmpty(bundle.getCharSequence("text", null))
                && TextUtils.isEmpty(bundle.getCharSequence("subText", null));
    }

    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(
                "-EdgeLightingPolicy start : type = "
                        + this.mPolicyType
                        + ", version = "
                        + this.mPolicyVersion);
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            synchronized (this.mNotificationMap) {
                try {
                    for (Map.Entry entry : this.mNotificationMap.entrySet()) {
                        printWriter.println(
                                "  " + ((String) entry.getKey()) + ":" + entry.getValue());
                    }
                    printWriter.println("");
                } finally {
                }
            }
        }
        synchronized (this.mDisableRecords) {
            try {
                Iterator it = this.mDisableRecords.iterator();
                while (it.hasNext()) {
                    DisableRecord disableRecord = (DisableRecord) it.next();
                    printWriter.println(
                            "  [DisableRecord] : "
                                    + disableRecord.packageName
                                    + " "
                                    + disableRecord.what);
                }
                printWriter.println("");
            } finally {
            }
        }
        synchronized (this.mDisabledPackages) {
            try {
                int size = this.mDisabledPackages.size();
                for (int i = 0; i < size; i++) {
                    printWriter.println(
                            "  [DisabledPackages] : type "
                                    + this.mDisabledPackages.keyAt(i)
                                    + " "
                                    + ((ArraySet) this.mDisabledPackages.valueAt(i)).toString());
                }
                printWriter.println("");
            } finally {
            }
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
            this.mWhitePolicy.dump(printWriter, "white");
            this.mBlackPolicy.dump(printWriter, "black");
        }
        printWriter.println("mSuppressed=" + this.mSuppressed);
        printWriter.println("");
    }

    public final boolean isAcceptableApplication(int i, int i2, String str, boolean z) {
        boolean z2;
        int i3;
        EdgeLightingPolicyInfo policyInfoWithPackageName;
        ApplicationInfo applicationInfo;
        EdgeLightingPolicyInfo policyInfoWithPackageName2;
        EdgeLightingPolicyRepository edgeLightingPolicyRepository = this.mWhitePolicy;
        EdgeLightingPolicyInfo edgeLightingPolicyInfo = null;
        EdgeLightingPolicyInfo policyInfoWithPackageName3 =
                (edgeLightingPolicyRepository == null || edgeLightingPolicyRepository.size() < 1)
                        ? null
                        : edgeLightingPolicyRepository.getPolicyInfoWithPackageName(str);
        boolean z3 = EdgeLightingHistory.IS_DEV_DEBUG;
        boolean z4 = DEBUG;
        if (z3 || z4) {
            StringBuffer stringBuffer = new StringBuffer("isAcceptableApplication:  pkg=");
            stringBuffer.append(str);
            stringBuffer.append(" , range=");
            stringBuffer.append(i);
            stringBuffer.append(" , includeAllApp=");
            stringBuffer.append(z);
            stringBuffer.append(" , userId=");
            stringBuffer.append(i2);
            stringBuffer.append(" , infoRange=");
            stringBuffer.append(
                    policyInfoWithPackageName3 != null ? policyInfoWithPackageName3.range : 0);
            stringBuffer.append(" , infoCategory=");
            stringBuffer.append(
                    policyInfoWithPackageName3 != null ? policyInfoWithPackageName3.category : 0);
            Slog.d("EdgeLightingPolicyManager", stringBuffer.toString());
        }
        if ((65280 & i) != 0) {
            return (policyInfoWithPackageName3 == null
                            || (policyInfoWithPackageName3.range & i) == 0)
                    ? false
                    : true;
        }
        if (z || (this.mPolicyType & 1) != 0) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            List queryIntentActivitiesAsUser =
                    this.mPackageManager.queryIntentActivitiesAsUser(intent, 0, i2);
            z2 = queryIntentActivitiesAsUser != null && queryIntentActivitiesAsUser.size() > 0;
            if (z2
                    && policyInfoWithPackageName3 != null
                    && (policyInfoWithPackageName3.range & i) == 0) {
                z2 = false;
            }
        } else {
            if (edgeLightingPolicyRepository.size() < 1
                    || (policyInfoWithPackageName2 =
                                    edgeLightingPolicyRepository.getPolicyInfoWithPackageName(str))
                            == null
                    || (policyInfoWithPackageName2.range & i) == 0) {
                policyInfoWithPackageName2 = null;
            }
            z2 = policyInfoWithPackageName2 != null;
            if (z3 || z4) {
                StringBuffer stringBuffer2 =
                        new StringBuffer("isAcceptableApplication: getAcceptablePolicy ");
                stringBuffer2.append(z2);
                Slog.d("EdgeLightingPolicyManager", stringBuffer2.toString());
            }
        }
        if (z2 && (this.mPolicyType & 2) != 0) {
            try {
                applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "getApplicationInfo : packageName = ", str, "EdgeLightingPolicyManager");
                applicationInfo = null;
            }
            z2 =
                    (applicationInfo == null
                                    || applicationInfo.isSystemApp()
                                    || applicationInfo.isUpdatedSystemApp())
                            ? false
                            : true;
        }
        try {
            i3 =
                    i2 < 0
                            ? this.mPackageManager.getPackageUidAsUser(str, 0, this.mUserId)
                            : this.mPackageManager.getPackageUidAsUser(str, 0, i2);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                    e, "getPackageUidAsUser failed - ", "EdgeLightingPolicyManager");
            i3 = 0;
        }
        if ((i & 1) != 0) {
            try {
                if (this.mNm == null) {
                    this.mNm =
                            INotificationManager.Stub.asInterface(
                                    ServiceManager.getService("notification"));
                }
                INotificationManager iNotificationManager = this.mNm;
                if (iNotificationManager != null) {
                    z2 = iNotificationManager.isEdgeLightingAllowed(str, i3);
                }
                if (EdgeLightingHistory.IS_DEV_DEBUG || z4) {
                    Slog.d(
                            "EdgeLightingPolicyManager",
                            "isAcceptableApplication - accept rechecked by isEdgeLightingAllowed = "
                                    + z2
                                    + " uid = "
                                    + i3);
                }
            } catch (Exception e2) {
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                        e2, "isEdgeLightingAllowed failed - ", "EdgeLightingPolicyManager");
            }
        }
        if (!z2 || (this.mPolicyType & 4) == 0) {
            return z2;
        }
        EdgeLightingPolicyRepository edgeLightingPolicyRepository2 = this.mBlackPolicy;
        if (edgeLightingPolicyRepository2.size() >= 1
                && (policyInfoWithPackageName =
                                edgeLightingPolicyRepository2.getPolicyInfoWithPackageName(str))
                        != null
                && (i & policyInfoWithPackageName.range) != 0) {
            edgeLightingPolicyInfo = policyInfoWithPackageName;
        }
        boolean z5 = edgeLightingPolicyInfo == null;
        if (z2 && !z5) {
            try {
                INotificationManager iNotificationManager2 = this.mNm;
                if (iNotificationManager2 != null) {
                    iNotificationManager2.setAllowEdgeLighting(str, i3, false);
                }
                if (EdgeLightingHistory.IS_DEV_DEBUG || z4) {
                    Slog.d(
                            "EdgeLightingPolicyManager",
                            "Block list app so setAllowEdgeLighting to false");
                }
            } catch (Exception e3) {
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                        e3, "setAllowEdgeLighting failed - ", "EdgeLightingPolicyManager");
            }
        }
        if (EdgeLightingHistory.IS_DEV_DEBUG || z4) {
            StringBuffer stringBuffer3 =
                    new StringBuffer("isAcceptableApplication: getRejectablePolicy ");
            stringBuffer3.append(z5);
            Slog.d("EdgeLightingPolicyManager", stringBuffer3.toString());
        }
        return z5;
    }

    public final boolean isAllowEdgelighting() {
        int i = this.mSuppressed;
        return i == 0 || (i & 16) == 0;
    }

    public final boolean isEdgeLightingDisabled() {
        synchronized (this.mDisableRecords) {
            try {
                if (this.mDisableRecords.size() <= 0) {
                    return false;
                }
                Log.d("EdgeLightingPolicyManager", "EdgeLighting is disabled");
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isEdgeLightingDisabledByPackage(String str) {
        if (str == null) {
            return false;
        }
        synchronized (this.mDisabledPackages) {
            try {
                if (this.mDisabledPackages.size() == 0) {
                    return false;
                }
                ArraySet arraySet = (ArraySet) this.mDisabledPackages.get(1);
                if (arraySet != null && arraySet.size() != 0) {
                    return arraySet.contains(str);
                }
                return false;
            } finally {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0091 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEdgeLightingRestricted(int r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.semMobileKeyboardCovered
            r1 = 0
            r2 = 1
            java.lang.String r3 = "EdgeLightingPolicyManager"
            if (r0 == r2) goto L51
            com.samsung.android.cover.CoverManager r0 = r7.mCoverManager
            if (r0 != 0) goto L1f
            com.samsung.android.cover.CoverManager r0 = new com.samsung.android.cover.CoverManager
            android.content.Context r4 = r7.mContext
            r0.<init>(r4)
            r7.mCoverManager = r0
        L1f:
            com.samsung.android.cover.CoverManager r0 = r7.mCoverManager
            r4 = 2
            if (r0 == 0) goto L33
            com.samsung.android.cover.CoverState r0 = r0.getCoverState()
            if (r0 == 0) goto L33
            boolean r5 = r0.getSwitchState()
            int r0 = r0.getType()
            goto L35
        L33:
            r5 = r2
            r0 = r4
        L35:
            if (r5 != 0) goto L51
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r6 = "getEdgeLightingRestrictState coverOpen="
            r7.<init>(r6)
            r7.append(r5)
            java.lang.String r5 = ", type="
            r7.append(r5)
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            android.util.Slog.d(r3, r7)
            goto L87
        L51:
            boolean r0 = com.android.server.notification.edgelighting.EdgeLightingPolicyManager.EMERGENCY_MODE_ENABLED
            if (r0 == 0) goto L60
            android.content.Context r0 = r7.mContext
            boolean r0 = com.samsung.android.emergencymode.SemEmergencyManager.isEmergencyMode(r0)
            if (r0 == 0) goto L60
            r4 = 10
            goto L87
        L60:
            android.content.Context r0 = r7.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.semDesktopModeEnabled
            if (r0 != r2) goto L86
            android.content.Context r7 = r7.mContext
            android.content.ContentResolver r7 = r7.getContentResolver()
            java.lang.String r0 = "new_dex"
            r4 = -2
            int r7 = android.provider.Settings.System.getIntForUser(r7, r0, r1, r4)
            if (r7 == r2) goto L86
            java.lang.String r7 = "Desktop mode enabled and it is not new dex mode"
            android.util.Slog.d(r3, r7)
            r4 = 11
            goto L87
        L86:
            r4 = r1
        L87:
            if (r4 == 0) goto L91
            java.lang.String r7 = "EdgeLighting is Restricted("
            java.lang.String r0 = "), range = "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r4, r8, r7, r0, r3)
            return r2
        L91:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.edgelighting.EdgeLightingPolicyManager.isEdgeLightingRestricted(int):boolean");
    }

    public final boolean isHUNPeeked() {
        if (this.mVrMode) {
            Slog.d("EdgeLightingPolicyManager", "isHUNPeeked : Vr mode");
            return false;
        }
        if (!"1".equals(SystemProperties.get("net.mirrorlink.on"))) {
            return true;
        }
        Slog.d("EdgeLightingPolicyManager", "isHUNPeeked : mirror link on");
        return false;
    }

    public final void manageDisableRecoredLocked(int i, IBinder iBinder, String str) {
        DisableRecord disableRecord;
        int size = this.mDisableRecords.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                disableRecord = null;
                break;
            }
            disableRecord = (DisableRecord) this.mDisableRecords.get(i2);
            if (disableRecord.token.equals(iBinder)) {
                break;
            } else {
                i2++;
            }
        }
        if (i == 0 || !iBinder.isBinderAlive()) {
            if (disableRecord != null) {
                this.mDisableRecords.remove(i2);
                disableRecord.token.unlinkToDeath(disableRecord, 0);
                return;
            }
            return;
        }
        if (disableRecord == null) {
            disableRecord = new DisableRecord();
            try {
                iBinder.linkToDeath(disableRecord, 0);
                this.mDisableRecords.add(disableRecord);
            } catch (RemoteException e) {
                Slog.d(
                        "EdgeLightingPolicyManager",
                        "manageDisableRecoredLocked : " + e.getMessage());
                return;
            }
        }
        disableRecord.what = i;
        disableRecord.token = iBinder;
        disableRecord.packageName = str;
    }

    public final Bundle putNotification(
            StatusBarNotification statusBarNotification,
            boolean z,
            boolean z2,
            int i,
            boolean z3,
            boolean z4) {
        CharSequence charSequence;
        CharSequence charSequence2;
        Boolean bool;
        CharSequence charSequence3;
        CharSequence charSequence4;
        Bundle bundle;
        GroupNotificationData groupNotificationData;
        NotificationData notificationData;
        if (statusBarNotification.getNotification() == null) {
            return null;
        }
        Bundle bundle2 = statusBarNotification.getNotification().extras;
        CharSequence charSequence5 =
                statusBarNotification.getNotification().tickerText != null
                        ? statusBarNotification.getNotification().tickerText
                        : null;
        Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
        String groupKey = statusBarNotification.getGroupKey();
        Boolean bool2 = Boolean.FALSE;
        if (bundle2 != null) {
            charSequence3 = bundle2.getCharSequence("android.title");
            CharSequence charSequence6 = bundle2.getCharSequence("android.text");
            charSequence2 = bundle2.getCharSequence("android.subText");
            charSequence = charSequence6;
            bool = Boolean.valueOf(bundle2.getBoolean("android.showSmallIcon"));
        } else {
            charSequence = null;
            charSequence2 = null;
            bool = bool2;
            charSequence3 = null;
        }
        StringBuilder sb = new StringBuilder("putNotification tickerText:");
        sb.append(charSequence5 != null);
        sb.append(",titleText:");
        sb.append(charSequence3 != null);
        sb.append(",text:");
        sb.append(charSequence != null);
        sb.append(",sub:");
        sb.append(charSequence2 != null);
        sb.append("cn= + ");
        sb.append(this.mResumedComponent);
        Slog.d("EdgeLightingPolicyManager", sb.toString());
        synchronized (this.mNotificationMap) {
            try {
                if (this.mNotificationMap.size() < 10) {
                    charSequence4 = charSequence5;
                } else {
                    long currentTimeMillis = System.currentTimeMillis();
                    Iterator it = this.mNotificationMap.entrySet().iterator();
                    while (it.hasNext()) {
                        CharSequence charSequence7 = charSequence5;
                        if (((NotificationData) ((Map.Entry) it.next()).getValue()).mTime + 10000
                                < currentTimeMillis) {
                            it.remove();
                        }
                        charSequence5 = charSequence7;
                    }
                    charSequence4 = charSequence5;
                }
            } finally {
            }
        }
        synchronized (this.mNotificationMap) {
            int i2 = statusBarNotification.getNotification().ledARGB;
            if (i2 == 0
                    && (notificationData =
                                    (NotificationData)
                                            this.mNotificationMap.get(
                                                    statusBarNotification.getPackageName()))
                            != null) {
                i2 = notificationData.mNotificationInfo.getInt("color", 0);
            }
            int i3 = i2;
            int i4 = -1000;
            try {
                INotificationManager iNotificationManager = this.mNm;
                if (iNotificationManager == null) {
                    Slog.d(
                            "EdgeLightingPolicyManager",
                            "INotificationManager is null in putNotification, try reload");
                    this.mNm =
                            INotificationManager.Stub.asInterface(
                                    ServiceManager.getService("notification"));
                } else {
                    i4 =
                            iNotificationManager.getLockScreenNotificationVisibilityForPackage(
                                    statusBarNotification.getPackageName(),
                                    statusBarNotification.getUid());
                }
            } catch (RemoteException e) {
                Slog.d(
                        "EdgeLightingPolicyManager",
                        "getLockScreenNotificationVisibilityForPackage failed - " + e);
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            bundle = new Bundle();
            bundle.putBoolean("isHeadup", z);
            bundle.putBoolean("isUpdate", z2);
            bundle.putCharSequence("tickerText", charSequence4);
            bundle.putCharSequence("titleText", charSequence3);
            bundle.putCharSequence("text", charSequence);
            bundle.putCharSequence("subText", charSequence2);
            bundle.putParcelable("smallIcon", smallIcon);
            bundle.putInt("flag", statusBarNotification.getNotification().flags);
            bundle.putInt("color", i3);
            bundle.putInt("notification_color", statusBarNotification.getNotification().color);
            bundle.putInt("priority", statusBarNotification.getNotification().priority);
            ComponentName componentName = this.mResumedComponent;
            bundle.putString(
                    "component", componentName != null ? componentName.flattenToString() : null);
            bundle.putLong("component_time", this.mResumedComponentTime);
            bundle.putString("group_key", groupKey);
            bundle.putParcelable(
                    "content_intent", statusBarNotification.getNotification().contentIntent);
            bundle.putInt("user_id", statusBarNotification.getUser().getIdentifier());
            bundle.putString("noti_key", statusBarNotification.getKey());
            bundle.putInt("noti_id", statusBarNotification.getId());
            bundle.putString("noti_tag", statusBarNotification.getTag());
            bundle.putInt("noti_visiblity", i);
            bundle.putInt("package_visiblity", i4);
            bundle.putBoolean(
                    "alert_group",
                    statusBarNotification.isGroup()
                            && statusBarNotification
                                    .getNotification()
                                    .suppressAlertingDueToGrouping());
            bundle.putCharSequence("category", statusBarNotification.getNotification().category);
            bundle.putBoolean("audible_alert", z3);
            bundle.putCharSequence(
                    "channel_id", statusBarNotification.getNotification().getChannelId());
            bundle.putBoolean("bubble", z4);
            bundle.putBoolean("show_small_icon", bool.booleanValue());
            if (statusBarNotification.getNotification().actions != null) {
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                for (Notification.Action action : statusBarNotification.getNotification().actions) {
                    arrayList.add(action);
                }
                bundle.putParcelableArrayList("noti_actions", arrayList);
            }
            NotificationData notificationData2 = new NotificationData();
            notificationData2.mTime = currentTimeMillis2;
            notificationData2.mNotificationInfo = bundle;
            if (groupKey != null) {
                this.mNotificationGroup.updateNotificationData(
                        statusBarNotification, notificationData2);
            }
            if (statusBarNotification.getNotification().isGroupSummary()) {
                NotificationGroup notificationGroup = this.mNotificationGroup;
                String groupKey2 = statusBarNotification.getGroupKey();
                synchronized (notificationGroup.mGroupMap) {
                    groupNotificationData =
                            (GroupNotificationData) notificationGroup.mGroupMap.get(groupKey2);
                }
                if (groupNotificationData != null && groupNotificationData.getLastChild() != null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(bundle.getCharSequence("tickerText"));
                    sb2.append(" -> ");
                    sb2.append(
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("tickerText"));
                    sb2.append(" , ");
                    bundle.putCharSequence(
                            "tickerText",
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("tickerText"));
                    sb2.append(bundle.getCharSequence("titleText"));
                    sb2.append(" -> ");
                    sb2.append(
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("titleText"));
                    sb2.append(" , ");
                    bundle.putCharSequence(
                            "titleText",
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("titleText"));
                    sb2.append(bundle.getCharSequence("text"));
                    sb2.append(" -> ");
                    sb2.append(
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("text"));
                    sb2.append(" , ");
                    bundle.putCharSequence(
                            "text",
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("text"));
                    sb2.append(bundle.getCharSequence("subText"));
                    sb2.append(" -> ");
                    sb2.append(
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("subText"));
                    bundle.putCharSequence(
                            "subText",
                            groupNotificationData
                                    .getLastChild()
                                    .mNotificationInfo
                                    .getCharSequence("subText"));
                    if (DEBUG) {
                        Slog.i(
                                "EdgeLightingPolicyManager",
                                " Summary notification display. Change text " + sb2.toString());
                    }
                }
            }
            if (!statusBarNotification.isGroup()
                    && !statusBarNotification.getNotification().suppressAlertingDueToGrouping()) {
                this.mNotificationMap.put(
                        statusBarNotification.getPackageName(), notificationData2);
            }
        }
        return bundle;
    }
}
