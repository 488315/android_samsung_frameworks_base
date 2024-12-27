package com.android.server.biometrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SemHqmManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Singleton;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemBioAnalyticsManager {
    public static final boolean DEBUG = Utils.DEBUG;
    public static final AnonymousClass1 sInstance = new AnonymousClass1();
    public Context mContext;
    public DQAManager mDqaMgr;
    public int mFaceQualityBigFace;
    public int mFaceQualityNoFace;
    public int mFaceQualitySmallFace;
    public boolean mIsSensorErrorForDQA;
    public int mLatestAuthenticatedForDQA;
    public boolean mIsFirstSensorCheckForDQA = true;
    public int mFpAuthRejectConsecutively = 0;
    public long mFaceStartTime = 0;
    public final Handler mH = BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    public ArrayList mPendingRequestBeforeBootComplete = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.SemBioAnalyticsManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends Singleton {
        public final Object create() {
            return new SemBioAnalyticsManager();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DQAManager {
        public final boolean FACE_FEATURE_HAL;
        public final Context mContext;
        public final Map mFaceBigDataNameMap;
        public String mFingerprintDaemonVersion;
        public final Map mFpBigDataNameMap;
        public long mPreviousSavedTime;
        public SemHqmManager mSemHqmManager;

        public DQAManager(Context context) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            this.mFpBigDataNameMap = linkedHashMap;
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            this.mFaceBigDataNameMap = linkedHashMap2;
            this.mFingerprintDaemonVersion = null;
            this.mContext = context;
            this.mSemHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
            this.mPreviousSavedTime = 0L;
            boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face");
            this.FACE_FEATURE_HAL = hasSystemFeature;
            resetDqaData();
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                parserAndUpdateMap(readDqaDataFromFile("settings_fingerprint_ext_bigdata.xml"), linkedHashMap);
            }
            if (hasSystemFeature) {
                parserAndUpdateMap(readDqaDataFromFile("settings_face_ext_bigdata.xml"), linkedHashMap2);
            }
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
                context.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.biometrics.SemBioAnalyticsManager.DQAManager.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        Log.i("BiometricService.AM", "onBroadCastReceive [DQA]: " + action);
                        action.getClass();
                        if (!action.equals("com.sec.android.intent.action.HQM_UPDATE_REQ")) {
                            if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                                if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                                    DQAManager.writeDqaDataToFile("settings_fingerprint_ext_bigdata.xml", DQAManager.getDqaDataFormatToSave(DQAManager.this.mFpBigDataNameMap));
                                }
                                DQAManager dQAManager = DQAManager.this;
                                if (dQAManager.FACE_FEATURE_HAL) {
                                    DQAManager.writeDqaDataToFile("settings_face_ext_bigdata.xml", DQAManager.getDqaDataFormatToSave(dQAManager.mFaceBigDataNameMap));
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        DQAManager dQAManager2 = DQAManager.this;
                        dQAManager2.getClass();
                        if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                            for (Map.Entry entry : ((LinkedHashMap) dQAManager2.mFpBigDataNameMap).entrySet()) {
                                if (!((String) entry.getValue()).equals("")) {
                                    dQAManager2.sendFingerprintBigData((String) entry.getKey(), (String) entry.getValue());
                                }
                            }
                            DQAManager.writeDqaDataToFile("settings_fingerprint_ext_bigdata.xml", null);
                        }
                        if (dQAManager2.FACE_FEATURE_HAL) {
                            for (Map.Entry entry2 : ((LinkedHashMap) dQAManager2.mFaceBigDataNameMap).entrySet()) {
                                if (!((String) entry2.getValue()).equals("")) {
                                    dQAManager2.sendFaceBigData((String) entry2.getKey(), (String) entry2.getValue());
                                }
                            }
                            DQAManager.writeDqaDataToFile("settings_face_ext_bigdata.xml", null);
                        }
                        dQAManager2.resetDqaData();
                        DQAManager dQAManager3 = DQAManager.this;
                        if (TextUtils.isEmpty(dQAManager3.mFingerprintDaemonVersion)) {
                            return;
                        }
                        dQAManager3.fpHandleDqaData(new EventData(-1, 2, "FPDA", dQAManager3.mFingerprintDaemonVersion));
                    }
                }, UserHandle.ALL, intentFilter, null, BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler());
            } catch (Exception e) {
                PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(e, "DQAManager: registerBroadCastReceiver : failed, ", "BiometricService.AM");
            }
        }

        public static String getDqaDataFormatToSave(Map map) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : map.entrySet()) {
                sb.append((String) entry.getKey());
                sb.append("|");
                sb.append((String) entry.getValue());
                sb.append("|");
            }
            String sb2 = sb.toString();
            if (SemBioAnalyticsManager.DEBUG) {
                DualAppManagerService$$ExternalSyntheticOutline0.m("formatDqaDataToSave: formatData = [", sb2, "]", "BiometricService.AM");
            }
            return sb2;
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x0035  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.lang.String readDqaDataFromFile(java.lang.String r4) {
            /*
                java.io.File r0 = new java.io.File
                r1 = 0
                java.io.File r1 = android.os.Environment.getUserSystemDirectory(r1)
                r0.<init>(r1, r4)
                byte[] r4 = com.android.server.biometrics.Utils.readFile(r0)
                java.lang.String r0 = "DQAManager.readDqaDataFromFile: "
                java.lang.String r1 = "BiometricService.AM"
                if (r4 == 0) goto L30
                java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L1c
                java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Exception -> L1c
                r2.<init>(r4, r3)     // Catch: java.lang.Exception -> L1c
                goto L31
            L1c:
                r4 = move-exception
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r0)
                java.lang.String r4 = r4.getMessage()
                r2.append(r4)
                java.lang.String r4 = r2.toString()
                android.util.Log.w(r1, r4)
            L30:
                r2 = 0
            L31:
                boolean r4 = com.android.server.biometrics.SemBioAnalyticsManager.DEBUG
                if (r4 == 0) goto L38
                com.android.server.DualAppManagerService$$ExternalSyntheticOutline0.m(r0, r2, r1)
            L38:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.SemBioAnalyticsManager.DQAManager.readDqaDataFromFile(java.lang.String):java.lang.String");
        }

        public static String updateTargetWithSource(String str, String str2) {
            if (str == "") {
                return str2;
            }
            if (str2 == null || str2 == "") {
                return str;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                try {
                    JSONObject jSONObject2 = new JSONObject(str2);
                    try {
                        Iterator<String> keys = jSONObject.keys();
                        Iterator<String> keys2 = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            while (true) {
                                if (keys2.hasNext()) {
                                    String next2 = keys2.next();
                                    if (next.equals(next2)) {
                                        jSONObject2.put(next2, ((Integer) jSONObject.get(next)).intValue() + ((Integer) jSONObject2.get(next2)).intValue());
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jSONObject2.toString();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return str;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                return str2;
            }
        }

        public static void writeDqaDataToFile(String str, String str2) {
            BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler().post(new SemBioAnalyticsManager$$ExternalSyntheticLambda1(1, str2, str));
        }

        public final void fpHandleDqaData(EventData eventData) {
            if ("FPDA".equals(eventData.mFeature)) {
                this.mFingerprintDaemonVersion = eventData.mExtra;
            }
            if (!this.mFpBigDataNameMap.containsKey(eventData.mFeature)) {
                sendFingerprintBigData(eventData.mFeature, eventData.mExtra);
                return;
            }
            if (isUsingPackageNameAsExtra(eventData.mFeature)) {
                this.mFpBigDataNameMap.put(eventData.mFeature, updateAppCountNum((String) ((LinkedHashMap) this.mFpBigDataNameMap).get(eventData.mFeature), eventData.mExtra));
            } else {
                String str = (String) ((LinkedHashMap) this.mFpBigDataNameMap).get(eventData.mFeature);
                this.mFpBigDataNameMap.put(eventData.mFeature, String.valueOf((str == "" ? 0 : Integer.parseInt(str)) + 1));
            }
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.mPreviousSavedTime;
            if (j == 0 || currentTimeMillis - j > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                this.mPreviousSavedTime = currentTimeMillis;
                writeDqaDataToFile("settings_fingerprint_ext_bigdata.xml", getDqaDataFormatToSave(this.mFpBigDataNameMap));
            }
        }

        public final boolean isUsingPackageNameAsExtra(String str) {
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT && (str.equals("FPIS") || str.equals("FPIF") || str.equals("FPQP") || str.equals("FPQI") || str.equals("FPQD") || str.equals("FPQS") || str.equals("FPQF") || str.equals("FPQW") || str.equals("FPQL"))) {
                return true;
            }
            if (this.FACE_FEATURE_HAL) {
                return str.equals("FAIS") || str.equals("FAIF") || str.equals("FAQN") || str.equals("FAQB") || str.equals("FAQS");
            }
            return false;
        }

        public final void parserAndUpdateMap(String str, Map map) {
            if (str == null) {
                return;
            }
            try {
                for (Map.Entry entry : ((LinkedHashMap) map).entrySet()) {
                    String str2 = (String) entry.getKey();
                    int indexOf = str.indexOf(str2);
                    if (indexOf >= 0 && indexOf < str.length() - 6) {
                        String trim = str.substring(indexOf + 5).split("\\|")[0].trim();
                        if (trim.length() > 0) {
                            if (isUsingPackageNameAsExtra(str2)) {
                                map.put(str2, updateTargetWithSource(trim, (String) entry.getValue()));
                            } else {
                                int parseInt = Integer.parseInt(trim);
                                if (((String) entry.getValue()).length() > 0) {
                                    parseInt += Integer.valueOf((String) entry.getValue()).intValue();
                                }
                                map.put(str2, Integer.toString(parseInt));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("parserAndUpdateMap: "), "BiometricService.AM");
            }
        }

        public final void resetDqaData() {
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                this.mFpBigDataNameMap.put("FPIS", "");
                this.mFpBigDataNameMap.put("FPIF", "");
                this.mFpBigDataNameMap.put("FPGT", "");
                this.mFpBigDataNameMap.put("FPQP", "");
                this.mFpBigDataNameMap.put("FPQI", "");
                this.mFpBigDataNameMap.put("FPQD", "");
                this.mFpBigDataNameMap.put("FPQS", "");
                this.mFpBigDataNameMap.put("FPQF", "");
                this.mFpBigDataNameMap.put("FPQW", "");
                this.mFpBigDataNameMap.put("FPQL", "");
                this.mFpBigDataNameMap.put("FPMV", "");
                this.mFpBigDataNameMap.put("FPOS", "");
                this.mFpBigDataNameMap.put("FPOF", "");
                this.mFpBigDataNameMap.put("FPFS", "");
                this.mFpBigDataNameMap.put("FPFF", "");
                this.mFpBigDataNameMap.put("FFOS", "");
                this.mFpBigDataNameMap.put("FFOF", "");
                this.mFpBigDataNameMap.put("FFFS", "");
                this.mFpBigDataNameMap.put("FFFF", "");
            }
            if (this.FACE_FEATURE_HAL) {
                this.mFaceBigDataNameMap.put("FAIS", "");
                this.mFaceBigDataNameMap.put("FAIF", "");
                this.mFaceBigDataNameMap.put("FAFS", "");
                this.mFaceBigDataNameMap.put("FAFF", "");
                this.mFaceBigDataNameMap.put("FATO", "");
                this.mFaceBigDataNameMap.put("FAQN", "");
                this.mFaceBigDataNameMap.put("FAQB", "");
                this.mFaceBigDataNameMap.put("FAQS", "");
                this.mFaceBigDataNameMap.put("FALI", "");
                this.mFaceBigDataNameMap.put("FABK", "");
                this.mFaceBigDataNameMap.put("FAMO", "");
                this.mFaceBigDataNameMap.put("FALQ", "");
                this.mFaceBigDataNameMap.put("FANM", "");
            }
        }

        public final void sendFaceBigData(String str, String str2) {
            String m = XmlUtils$$ExternalSyntheticOutline0.m("\"", str, "\":\"", str2, "\"");
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "DQAManager.sendFaceBigData: " + str + ":" + str2);
            }
            if (this.mSemHqmManager == null) {
                this.mSemHqmManager = (SemHqmManager) this.mContext.getSystemService(SemHqmManager.class);
            }
            SemHqmManager semHqmManager = this.mSemHqmManager;
            if (semHqmManager != null) {
                semHqmManager.sendHWParamToHQM(0, "Camera", str, "ph", "0.0", "sec", "", m, "");
            } else {
                Log.e("BiometricService.AM", "DQAManager.sendFaceBigData: SemHqmManager is null!!");
            }
        }

        public final void sendFingerprintBigData(String str, String str2) {
            String m = XmlUtils$$ExternalSyntheticOutline0.m("\"", str, "\":\"", str2, "\"");
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "DQAManager.sendFingerprintBigData: " + str + ":" + str2);
            }
            if (this.mSemHqmManager == null) {
                this.mSemHqmManager = (SemHqmManager) this.mContext.getSystemService(SemHqmManager.class);
            }
            SemHqmManager semHqmManager = this.mSemHqmManager;
            if (semHqmManager != null) {
                semHqmManager.sendHWParamToHQM(0, "BFS", str, "ph", "0.0", "sec", "", m, "");
            } else {
                Log.e("BiometricService.AM", "DQAManager.sendFingerprintBigData: SemHqmManager is null!!");
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:33:0x0088 A[Catch: Exception -> 0x0091, TryCatch #1 {Exception -> 0x0091, blocks: (B:31:0x0082, B:33:0x0088, B:34:0x0096, B:38:0x0093), top: B:30:0x0082 }] */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0093 A[Catch: Exception -> 0x0091, TryCatch #1 {Exception -> 0x0091, blocks: (B:31:0x0082, B:33:0x0088, B:34:0x0096, B:38:0x0093), top: B:30:0x0082 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.String updateAppCountNum(java.lang.String r4, java.lang.String r5) {
            /*
                r3 = this;
                android.content.Context r3 = r3.mContext
                boolean r0 = com.android.server.biometrics.Utils.DEBUG
                java.lang.String r0 = "android.permission.USE_BIOMETRIC_INTERNAL"
                int r0 = r3.checkCallingOrSelfPermission(r0)
                r1 = 1
                if (r0 != 0) goto Lf
                r0 = r1
                goto L10
            Lf:
                r0 = 0
            L10:
                android.content.res.Resources r3 = r3.getResources()
                r2 = 17040238(0x104036e, float:2.4247032E-38)
                java.lang.String r3 = r3.getString(r2)
                android.content.ComponentName r3 = android.content.ComponentName.unflattenFromString(r3)
                r2 = 0
                if (r3 == 0) goto L27
                java.lang.String r3 = r3.getPackageName()
                goto L28
            L27:
                r3 = r2
            L28:
                if (r0 == 0) goto L2d
                if (r3 == 0) goto L2d
                goto L2e
            L2d:
                r3 = r2
            L2e:
                boolean r3 = r5.equals(r3)
                if (r3 != 0) goto L65
                java.lang.String r3 = "com.whatsapp"
                boolean r3 = r5.equals(r3)
                if (r3 != 0) goto L65
                java.lang.String r3 = "com.samsung.android.spay"
                boolean r3 = r5.equals(r3)
                if (r3 != 0) goto L65
                java.lang.String r3 = "com.kakao.talk"
                boolean r3 = r5.equals(r3)
                if (r3 != 0) goto L65
                java.lang.String r3 = "jp.naver.line.android"
                boolean r3 = r5.equals(r3)
                if (r3 != 0) goto L65
                java.lang.String r3 = "org.telegram.messenger"
                boolean r3 = r5.equals(r3)
                if (r3 == 0) goto L62
                goto L65
            L62:
                java.lang.String r5 = "others"
            L65:
                if (r4 == 0) goto L74
                java.lang.String r3 = ""
                if (r4 != r3) goto L6c
                goto L74
            L6c:
                org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Exception -> L72
                r3.<init>(r4)     // Catch: java.lang.Exception -> L72
                goto L82
            L72:
                r3 = move-exception
                goto L7a
            L74:
                org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Exception -> L72
                r3.<init>()     // Catch: java.lang.Exception -> L72
                goto L82
            L7a:
                r3.printStackTrace()
                org.json.JSONObject r3 = new org.json.JSONObject
                r3.<init>()
            L82:
                boolean r4 = r3.has(r5)     // Catch: java.lang.Exception -> L91
                if (r4 == 0) goto L93
                int r4 = r3.getInt(r5)     // Catch: java.lang.Exception -> L91
                int r4 = r4 + r1
                r3.put(r5, r4)     // Catch: java.lang.Exception -> L91
                goto L96
            L91:
                r3 = move-exception
                goto L9b
            L93:
                r3.put(r5, r1)     // Catch: java.lang.Exception -> L91
            L96:
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> L91
                return r3
            L9b:
                r3.printStackTrace()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.SemBioAnalyticsManager.DQAManager.updateAppCountNum(java.lang.String, java.lang.String):java.lang.String");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EventData {
        public String mExtra;
        public final String mExtra2;
        public String mFeature;
        public int mType;
        public final int mValue;

        public EventData(int i, int i2, String str, String str2) {
            this.mFeature = str;
            this.mExtra = str2;
            this.mValue = i;
            this.mType = i2;
        }

        public EventData(String str, String str2, String str3) {
            this.mFeature = str;
            this.mExtra = str2;
            this.mExtra2 = str3;
            this.mValue = -1;
            this.mType = 3;
        }

        public final String toString() {
            return this.mFeature + ", " + this.mExtra + ", " + this.mExtra2 + "," + this.mValue + ", " + this.mType;
        }
    }

    public static SemBioAnalyticsManager get() {
        return (SemBioAnalyticsManager) sInstance.get();
    }

    public final void faceInsertLog(EventData eventData) {
        this.mH.post(new SemBioAnalyticsManager$$ExternalSyntheticLambda0(this, eventData, 0));
    }

    public final void faceOnAuthenticatedFailure(String str) {
        faceInsertLog(new EventData(-1, 3, "FAIF", str));
        if (Utils.isFolderOpened(this.mContext) || Utils.isFlipOpened(this.mContext)) {
            faceInsertLog(new EventData(-1, 3, "FAFF", str));
        }
    }

    public final void fpHandleData(EventData eventData) {
        String str;
        if (eventData == null) {
            return;
        }
        if (DEBUG) {
            Log.d("BiometricService.AM", "SA: " + eventData.toString());
        }
        DQAManager dQAManager = this.mDqaMgr;
        if (dQAManager != null && (eventData.mType & 2) != 0) {
            dQAManager.fpHandleDqaData(eventData);
        }
        boolean z = SemBiometricFeature.FEATURE_LOGGING_MODE;
        if ("FPIS".equals(eventData.mFeature)) {
            this.mLatestAuthenticatedForDQA = 1;
        } else if ("FPIF".equals(eventData.mFeature)) {
            this.mLatestAuthenticatedForDQA = 0;
        }
        if (!SemBiometricFeature.FEATURE_LOGGING_MODE || (eventData.mType & 1) == 0) {
            return;
        }
        Intent intent = new Intent();
        Bundle m142m = AccountManagerService$$ExternalSyntheticOutline0.m142m("tracking_id", "4G2-399-4810151");
        m142m.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, eventData.mFeature);
        String str2 = eventData.mExtra;
        if (str2 != null) {
            m142m.putString("extra", str2);
        }
        if (eventData.mFeature.equals("FPIS") && (str = eventData.mExtra) != null && ("com.android.vending".equals(str) || "com.samsung.android.spay".equals(str) || "com.paypal.android.p2pmobile".equals(str) || "com.squareup.cash".equals(str) || "com.venmo".equals(str) || "com.zellepay.zell".equals(str))) {
            HashMap hashMap = new HashMap();
            hashMap.put("FINGERPRINT_IDENTIFICATION", new String[]{"extra"});
            m142m.putSerializable("personalizedData", hashMap);
        }
        m142m.putString("pkg_name", "com.android.server.biometrics.sensors.fingerprint");
        m142m.putString("type", "ev");
        m142m.putString("is_summary", "true");
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.putExtras(m142m);
        intent.setPackage("com.sec.android.diagmonagent");
        try {
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            Log.w("BiometricService.AM", "SemAnalyticsManager.insertLog: " + e.getMessage());
        }
    }

    public final void fpInsertLog(int i, String str, String str2) {
        fpInsertLog(new EventData(-1, i, str, str2));
        if ("FPIS".equals(str)) {
            int i2 = this.mFpAuthRejectConsecutively;
            if (i2 == 3 || i2 == 4 || (i2 >= 6 && i2 % 5 != 0)) {
                fpInsertLog(new EventData(-1, i, "FPFC", String.valueOf(i2)));
            }
            this.mFpAuthRejectConsecutively = 0;
            return;
        }
        if ("FPIF".equals(str)) {
            int i3 = this.mFpAuthRejectConsecutively + 1;
            this.mFpAuthRejectConsecutively = i3;
            if (i3 < 5 || i3 % 5 != 0) {
                return;
            }
            fpInsertLog(new EventData(-1, i, "FPFC", String.valueOf(i3)));
        }
    }

    public final void fpInsertLog(EventData eventData) {
        this.mH.post(new SemBioAnalyticsManager$$ExternalSyntheticLambda0(this, eventData, 1));
    }

    public final void fpInsertLogHelp(int i, int i2, int i3, String str) {
        String str2;
        switch (i) {
            case 1:
                str2 = "FPQP";
                break;
            case 2:
                str2 = "FPQI";
                break;
            case 3:
                str2 = "FPQD";
                break;
            case 4:
                str2 = "FPQS";
                break;
            case 5:
                str2 = "FPQF";
                break;
            case 6:
                if (i2 != 1001) {
                    if (i2 != 1003) {
                        if (i2 == 1006) {
                            str2 = "FPQU";
                            break;
                        }
                    } else {
                        str2 = "FPQL";
                        break;
                    }
                } else {
                    str2 = "FPQW";
                    break;
                }
            default:
                str2 = null;
                break;
        }
        if (str2 != null) {
            fpInsertLog(new EventData(-1, i3, str2, str));
        }
    }

    public final String getOperationTime() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.mFaceStartTime;
        if (j <= 0 || currentTimeMillis - j <= 0) {
            return null;
        }
        return String.valueOf(currentTimeMillis - j);
    }

    public void injectPendingList(ArrayList arrayList) {
        this.mPendingRequestBeforeBootComplete = arrayList;
    }

    public void reset() {
        this.mPendingRequestBeforeBootComplete = new ArrayList();
        this.mContext = null;
        this.mDqaMgr = null;
        this.mIsSensorErrorForDQA = false;
        this.mIsFirstSensorCheckForDQA = true;
        this.mLatestAuthenticatedForDQA = 0;
        this.mFaceQualityNoFace = 0;
        this.mFaceQualityBigFace = 0;
        this.mFaceQualitySmallFace = 0;
    }
}
