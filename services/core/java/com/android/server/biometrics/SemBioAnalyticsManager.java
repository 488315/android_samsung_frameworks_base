package com.android.server.biometrics;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SemHqmManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Singleton;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.clipboard.ClipboardService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SemBioAnalyticsManager {
    public static final boolean DEBUG = Utils.DEBUG;
    public static final Singleton sInstance = new Singleton() { // from class: com.android.server.biometrics.SemBioAnalyticsManager.1
        /* renamed from: create, reason: merged with bridge method [inline-methods] */
        public SemBioAnalyticsManager m3470create() {
            return new SemBioAnalyticsManager();
        }
    };
    public Context mContext;
    public DQAManager mDqaMgr;
    public int mFaceQualityBigFace;
    public int mFaceQualityFakeFace;
    public int mFaceQualityLow;
    public int mFaceQualityMisAligned;
    public int mFaceQualityNoFace;
    public int mFaceQualitySmallFace;
    public int mFpAuthRejectConsecutively;

    /* renamed from: mH */
    public final Handler f1651mH;
    public boolean mHasGestureEventForDQA;
    public boolean mIsFirstSensorCheckForDQA;
    public boolean mIsSensorErrorForDQA;
    public boolean mIsSupportDMA;
    public int mLatestAuthenticatedForDQA;
    public ArrayList mPendingRequestBeforeBootComplete;

    public class EventData {
        public String mExtra;
        public String mFeature;
        public int mType;
        public int mValue;

        public EventData() {
        }

        public EventData(String str, String str2, int i, int i2) {
            this.mFeature = str;
            this.mExtra = str2;
            this.mValue = i;
            this.mType = i2;
        }

        public String toString() {
            return this.mFeature + ", " + this.mExtra + "," + this.mValue + ", " + this.mType;
        }
    }

    public static SemBioAnalyticsManager getInstance() {
        return (SemBioAnalyticsManager) sInstance.get();
    }

    public static SemBioAnalyticsManager get() {
        return (SemBioAnalyticsManager) sInstance.get();
    }

    public SemBioAnalyticsManager() {
        this.mIsFirstSensorCheckForDQA = true;
        this.mFpAuthRejectConsecutively = 0;
        this.f1651mH = SemBioFgThread.get().getHandler();
        this.mPendingRequestBeforeBootComplete = new ArrayList();
    }

    public void reset() {
        this.mPendingRequestBeforeBootComplete = new ArrayList();
        this.mContext = null;
        this.mDqaMgr = null;
        this.mIsSupportDMA = false;
        this.mIsSensorErrorForDQA = false;
        this.mIsFirstSensorCheckForDQA = true;
        this.mHasGestureEventForDQA = false;
        this.mLatestAuthenticatedForDQA = 0;
        this.mFaceQualityNoFace = 0;
        this.mFaceQualityLow = 0;
        this.mFaceQualityFakeFace = 0;
        this.mFaceQualityBigFace = 0;
        this.mFaceQualitySmallFace = 0;
        this.mFaceQualityMisAligned = 0;
    }

    public void injectPendingList(ArrayList arrayList) {
        this.mPendingRequestBeforeBootComplete = arrayList;
    }

    public void onBootComplete(final Context context) {
        this.f1651mH.post(new Runnable() { // from class: com.android.server.biometrics.SemBioAnalyticsManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemBioAnalyticsManager.this.lambda$onBootComplete$0(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootComplete$0(Context context) {
        this.mContext = context;
        this.mDqaMgr = new DQAManager(context);
        this.mIsSupportDMA = isSupportDMALogging(this.mContext);
        Iterator it = this.mPendingRequestBeforeBootComplete.iterator();
        while (it.hasNext()) {
            fpHandleData((EventData) it.next());
        }
        this.mPendingRequestBeforeBootComplete.clear();
        this.mPendingRequestBeforeBootComplete = null;
    }

    public void fpHalInfo(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            fpInsertLog("FPDA", str, -1, 2);
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        fpInsertLog("FPDS", str2, -1, 2);
    }

    public final void fpInsertLog(final EventData eventData) {
        this.f1651mH.post(new Runnable() { // from class: com.android.server.biometrics.SemBioAnalyticsManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemBioAnalyticsManager.this.lambda$fpInsertLog$1(eventData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fpInsertLog$1(EventData eventData) {
        if (this.mContext == null) {
            this.mPendingRequestBeforeBootComplete.add(eventData);
        } else {
            fpHandleData(eventData);
        }
    }

    public void fpInsertLog(String str, String str2, int i, int i2) {
        fpInsertLog(new EventData(str, str2, i, i2));
        fpInsertLogExt(str, str2, i, i2);
    }

    public final void fpInsertLogExt(String str, String str2, int i, int i2) {
        if ("FPIS".equals(str)) {
            int i3 = this.mFpAuthRejectConsecutively;
            if (i3 == 3 || i3 == 4 || (i3 >= 6 && i3 % 5 != 0)) {
                fpInsertLog(new EventData("FPFC", String.valueOf(i3), i, i2));
            }
            this.mFpAuthRejectConsecutively = 0;
            return;
        }
        if ("FPIF".equals(str)) {
            int i4 = this.mFpAuthRejectConsecutively + 1;
            this.mFpAuthRejectConsecutively = i4;
            if (i4 < 5 || i4 % 5 != 0) {
                return;
            }
            fpInsertLog(new EventData("FPFC", String.valueOf(i4), i, i2));
        }
    }

    public void fpInsertLogHelp(int i, int i2, String str, int i3) {
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
                        if (i2 != 1006) {
                            if (FingerprintUtils.semIsAuthenticationFailedReasonEvent(i2)) {
                                str2 = "FPNR";
                                break;
                            }
                        } else {
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
            fpInsertLog(new EventData(str2, str, -1, i3));
        }
    }

    public void fpInsertLogRemove(int i) {
        fpInsertLog(new EventData("FPRM", i == -1 ? "ALL" : Integer.toString(i), -1, 3));
    }

    public void fpInsertLogError(int i, int i2, String str) {
        EventData eventData;
        if (i != 1) {
            if (i != 7) {
                if (i == 8) {
                    eventData = new EventData();
                    eventData.mFeature = "FPER";
                    eventData.mExtra = Integer.toString(i2);
                    eventData.mType = 3;
                } else if (i != 9) {
                    eventData = null;
                }
            }
            EventData eventData2 = new EventData();
            eventData2.mFeature = "FPIB";
            eventData2.mExtra = str;
            eventData2.mType = 3;
            eventData = eventData2;
        } else {
            eventData = new EventData();
            eventData.mFeature = "FPER";
            eventData.mExtra = Integer.toString(i);
            eventData.mType = 3;
        }
        if (eventData != null) {
            fpInsertLog(eventData);
        }
        if (i == 8 && i2 == 1007) {
            EventData eventData3 = new EventData();
            eventData3.mFeature = "FPPD";
            eventData3.mExtra = null;
            eventData3.mType = 2;
            fpInsertLog(eventData3);
        }
    }

    public void fpInsertLogSensorStatus(int i) {
        if (i == 100042) {
            if (!this.mIsSensorErrorForDQA) {
                String str = "pre:" + this.mLatestAuthenticatedForDQA + ";gesture:" + this.mHasGestureEventForDQA + ";first:" + this.mIsFirstSensorCheckForDQA;
                Log.d("BiometricService.AM", "fpInsertLogSensorStatus: " + i + ", " + str);
                fpInsertLog(new EventData("FPST", str, -1, 2));
                this.mIsSensorErrorForDQA = true;
            }
        } else {
            this.mIsSensorErrorForDQA = false;
        }
        this.mIsFirstSensorCheckForDQA = false;
    }

    public void faceInitHelpEvent() {
        this.mFaceQualityNoFace = 0;
        this.mFaceQualityLow = 0;
        this.mFaceQualityFakeFace = 0;
        this.mFaceQualityBigFace = 0;
        this.mFaceQualitySmallFace = 0;
        this.mFaceQualityMisAligned = 0;
    }

    public void faceCountHelpEvent(int i, int i2) {
        if (i == 1) {
            this.mFaceQualityLow++;
            return;
        }
        if (i == 11) {
            this.mFaceQualityNoFace++;
            return;
        }
        if (i != 22) {
            switch (i) {
                case 4:
                    this.mFaceQualityBigFace++;
                    break;
                case 5:
                    this.mFaceQualitySmallFace++;
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    this.mFaceQualityMisAligned++;
                    break;
            }
            return;
        }
        if (i2 == 1005) {
            this.mFaceQualityFakeFace++;
        } else if (i2 == 1006 || i2 == 1008 || i2 == 1012 || i2 == 1014) {
            this.mFaceQualityMisAligned++;
        }
    }

    public final void faceInsertLog(final EventData eventData) {
        this.f1651mH.post(new Runnable() { // from class: com.android.server.biometrics.SemBioAnalyticsManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SemBioAnalyticsManager.this.lambda$faceInsertLog$2(eventData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$faceInsertLog$2(EventData eventData) {
        if (this.mContext == null) {
            this.mPendingRequestBeforeBootComplete.add(eventData);
        } else {
            faceHandleData(eventData);
        }
    }

    public void faceOnAuthenticatedSuccess(String str) {
        faceInsertLog(new EventData("FAIS", str, -1, 3));
        if (Utils.isFolderOpened(this.mContext) || Utils.isFlipOpened(this.mContext)) {
            faceInsertLog(new EventData("FAFS", str, -1, 3));
        }
    }

    public void faceOnAuthenticatedFailure(String str, int i) {
        faceOnAuthenticatedFailure(str);
        switch (i) {
            case 100002:
                faceInsertLog(new EventData("FALI", str, -1, 3));
                break;
            case 100003:
                faceInsertLog(new EventData("FALQ", str, -1, 3));
                break;
            case 100004:
            default:
                faceInsertLog(new EventData("FANM", str, -1, 3));
                break;
            case 100005:
                faceInsertLog(new EventData("FABK", str, -1, 3));
                break;
            case 100006:
                faceInsertLog(new EventData("FAMO", str, -1, 3));
                break;
        }
    }

    public void faceOnAuthenticatedFailureForHIDL(String str, int i, int i2, int i3, int i4) {
        faceOnAuthenticatedFailure(str);
        if (i >= 4) {
            faceInsertLog(new EventData("FALI", str, -1, 3));
            return;
        }
        if (i2 >= 4) {
            faceInsertLog(new EventData("FABK", str, -1, 3));
            return;
        }
        if (i3 >= 4) {
            faceInsertLog(new EventData("FAMO", str, -1, 3));
        } else if (i4 >= 7) {
            faceInsertLog(new EventData("FALQ", str, -1, 3));
        } else {
            faceInsertLog(new EventData("FANM", str, -1, 3));
        }
    }

    public void faceOnAuthenticatedFailure(String str) {
        faceInsertLog(new EventData("FAIF", str, -1, 3));
        if (Utils.isFolderOpened(this.mContext) || Utils.isFlipOpened(this.mContext)) {
            faceInsertLog(new EventData("FAFF", str, -1, 3));
        }
    }

    public void faceOnError(String str, int i, int i2) {
        if (i == 8 && i2 == 1006) {
            faceInsertLog(new EventData("FAMK", str, -1, 3));
        } else if (i == 7 || i == 9) {
            faceInsertLog(new EventData("FAIB", str, -1, 3));
        }
    }

    public void faceOnTimeout(String str) {
        faceInsertLog(new EventData("FATO", str, -1, 3));
        int i = this.mFaceQualityNoFace;
        if (i > 0) {
            faceInsertLog(new EventData("FAQN", str, i, 3));
        }
        int i2 = this.mFaceQualityLow;
        if (i2 > 0) {
            faceInsertLog(new EventData("FAQL", str, i2, 3));
        }
        int i3 = this.mFaceQualityFakeFace;
        if (i3 > 0) {
            faceInsertLog(new EventData("FAQF", str, i3, 3));
        }
        int i4 = this.mFaceQualityBigFace;
        if (i4 > 0) {
            faceInsertLog(new EventData("FAQB", str, i4, 3));
        }
        int i5 = this.mFaceQualitySmallFace;
        if (i5 > 0) {
            faceInsertLog(new EventData("FAQS", str, i5, 3));
        }
        int i6 = this.mFaceQualityMisAligned;
        if (i6 > 0) {
            faceInsertLog(new EventData("FAQM", str, i6, 3));
        }
        if (this.mFaceQualityNoFace > 0 && this.mFaceQualityLow == 0 && this.mFaceQualityFakeFace == 0 && this.mFaceQualityBigFace == 0 && this.mFaceQualitySmallFace == 0 && this.mFaceQualityMisAligned == 0) {
            faceInsertLog(new EventData("FANF", str, -1, 1));
        }
        faceInitHelpEvent();
    }

    public int getFaceTimeoutDetailCondition() {
        int i = this.mFaceQualityNoFace;
        int i2 = i > 0 ? 1 : 0;
        int i3 = this.mFaceQualityLow;
        if (i3 > 0) {
            i2 |= 2;
        }
        int i4 = this.mFaceQualityFakeFace;
        if (i4 > 0) {
            i2 |= 4;
        }
        int i5 = this.mFaceQualityBigFace;
        if (i5 > 0) {
            i2 |= 8;
        }
        int i6 = this.mFaceQualitySmallFace;
        if (i6 > 0) {
            i2 |= 16;
        }
        int i7 = this.mFaceQualityMisAligned;
        if (i7 > 0) {
            i2 |= 32;
        }
        return (i > 0 && i3 == 0 && i4 == 0 && i5 == 0 && i6 == 0 && i7 == 0) ? i2 | 64 : i2;
    }

    public void faceOnEnrollmentSuccess(String str) {
        faceInsertLog(new EventData("FAEN", str, -1, 3));
    }

    public void faceOnRemoved(String str) {
        faceInsertLog(new EventData("FARM", str, -1, 3));
    }

    public final boolean isSupportDMALogging(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
            if (packageInfo != null) {
                return packageInfo.versionCode >= 540000000;
            }
        } catch (Exception e) {
            Log.w("BiometricService.AM", "isSupportDMALogging: " + e.getMessage());
        }
        return false;
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
        if (SemBiometricFeature.FP_FEATURE_GESTURE_MODE && !this.mHasGestureEventForDQA && "FPGT".equals(eventData.mFeature)) {
            this.mHasGestureEventForDQA = true;
        }
        if ("FPIS".equals(eventData.mFeature)) {
            this.mLatestAuthenticatedForDQA = 1;
        } else if ("FPIF".equals(eventData.mFeature)) {
            this.mLatestAuthenticatedForDQA = 0;
        }
        if (!SemBiometricFeature.FEATURE_LOGGING_MODE || (eventData.mType & 1) == 0) {
            return;
        }
        Intent intent = new Intent();
        if (this.mIsSupportDMA) {
            Bundle bundle = new Bundle();
            bundle.putString("tracking_id", "4G2-399-4810151");
            bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, eventData.mFeature);
            String str2 = eventData.mExtra;
            if (str2 != null) {
                bundle.putString("extra", str2);
            }
            if (eventData.mFeature.equals("FPIS") && (str = eventData.mExtra) != null && Utils.isRuneStoneApp(this.mContext, str)) {
                HashMap hashMap = new HashMap();
                hashMap.put("FINGERPRINT_IDENTIFICATION", new String[]{"extra"});
                bundle.putSerializable("personalizedData", hashMap);
            }
            bundle.putString("pkg_name", "com.android.server.biometrics.sensors.fingerprint");
            bundle.putString("type", "ev");
            intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
            intent.putExtras(bundle);
            intent.setPackage("com.sec.android.diagmonagent");
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", "com.samsung.android.fingerprint.service");
            contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, eventData.mFeature);
            String str3 = eventData.mExtra;
            if (str3 != null) {
                contentValues.put("extra", str3);
            }
            intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
            intent.putExtra("data", contentValues);
            intent.setPackage("com.samsung.android.providers.context");
        }
        try {
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            Log.w("BiometricService.AM", "SemAnalyticsManager.insertLog: " + e.getMessage());
        }
    }

    public final void faceHandleData(EventData eventData) {
        if (eventData == null) {
            return;
        }
        if (DEBUG) {
            Log.d("BiometricService.AM", "SA: " + eventData.toString());
        }
        DQAManager dQAManager = this.mDqaMgr;
        if (dQAManager != null && (eventData.mType & 2) != 0) {
            dQAManager.faceHandleDqaData(eventData);
        }
        if (!SemBiometricFeature.FEATURE_LOGGING_MODE || (eventData.mType & 1) == 0) {
            return;
        }
        Intent intent = new Intent();
        if (this.mIsSupportDMA) {
            Bundle bundle = new Bundle();
            bundle.putString("tracking_id", "4G3-399-5448102");
            bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, eventData.mFeature);
            String str = eventData.mExtra;
            if (str != null) {
                bundle.putString("extra", str);
            }
            bundle.putString("pkg_name", "com.android.server.biometrics.sensors.face");
            bundle.putString("type", "ev");
            intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
            intent.putExtras(bundle);
            intent.setPackage("com.sec.android.diagmonagent");
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", "com.samsung.android.bio.face.service");
            contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, eventData.mFeature);
            String str2 = eventData.mExtra;
            if (str2 != null) {
                contentValues.put("extra", str2);
            }
            intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
            intent.putExtra("data", contentValues);
            intent.setPackage("com.samsung.android.providers.context");
        }
        try {
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            Log.w("BiometricService.AM", "SemAnalyticsManager.insertLog: " + e.getMessage());
        }
    }

    public class DQAManager {
        public final boolean FACE_FEATURE_HAL;
        public final Context mContext;
        public SemHqmManager mSemHqmManager;
        public final Map mFpBigDataNameMap = new LinkedHashMap();
        public final Map mFaceBigDataNameMap = new LinkedHashMap();
        public String mFingerprintDaemonVersion = null;
        public long mPreviousSavedTime = 0;

        public DQAManager(Context context) {
            this.mContext = context;
            this.mSemHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
            boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face");
            this.FACE_FEATURE_HAL = hasSystemFeature;
            resetDqaData();
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                fpParseAndUpdateMap(readDqaDataFromFile("settings_fingerprint_ext_bigdata.xml"));
            }
            if (hasSystemFeature) {
                faceParseAndUpdateMap(readDqaDataFromFile("settings_face_ext_bigdata.xml"));
            }
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
                context.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.biometrics.SemBioAnalyticsManager.DQAManager.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        Log.i("BiometricService.AM", "onBroadCastReceive [DQA]: " + action);
                        action.hashCode();
                        if (action.equals("com.sec.android.intent.action.HQM_UPDATE_REQ")) {
                            DQAManager.this.send();
                            DQAManager.this.fpSendDaemonVersion();
                        } else if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                                DQAManager dQAManager = DQAManager.this;
                                dQAManager.writeDqaDataToFile("settings_fingerprint_ext_bigdata.xml", dQAManager.fpGetDqaDataFormatToSave());
                            }
                            if (DQAManager.this.FACE_FEATURE_HAL) {
                                DQAManager dQAManager2 = DQAManager.this;
                                dQAManager2.writeDqaDataToFile("settings_face_ext_bigdata.xml", dQAManager2.faceGetDqaDataFormatToSave());
                            }
                        }
                    }
                }, UserHandle.ALL, intentFilter, null, SemBioFgThread.get().getHandler());
            } catch (Exception e) {
                Slog.i("BiometricService.AM", "DQAManager: registerBroadCastReceiver : failed, " + e);
            }
        }

        public final void fpSendDaemonVersion() {
            if (TextUtils.isEmpty(this.mFingerprintDaemonVersion)) {
                return;
            }
            fpHandleDqaData(new EventData("FPDA", this.mFingerprintDaemonVersion, -1, 2));
        }

        public void fpHandleDqaData(EventData eventData) {
            if ("FPDA".equals(eventData.mFeature)) {
                this.mFingerprintDaemonVersion = eventData.mExtra;
            }
            if (this.mFpBigDataNameMap.containsKey(eventData.mFeature)) {
                this.mFpBigDataNameMap.put(eventData.mFeature, Integer.valueOf(((Integer) this.mFpBigDataNameMap.get(eventData.mFeature)).intValue() + 1));
                if (needToSaveBigFeatures()) {
                    writeDqaDataToFile("settings_fingerprint_ext_bigdata.xml", fpGetDqaDataFormatToSave());
                    return;
                }
                return;
            }
            sendFingerprintBigData(eventData.mFeature, eventData.mExtra);
        }

        public void faceHandleDqaData(EventData eventData) {
            if (this.mFaceBigDataNameMap.containsKey(eventData.mFeature)) {
                this.mFaceBigDataNameMap.put(eventData.mFeature, Integer.valueOf(((Integer) this.mFaceBigDataNameMap.get(eventData.mFeature)).intValue() + 1));
                if (needToSaveBigFeatures()) {
                    writeDqaDataToFile("settings_face_ext_bigdata.xml", faceGetDqaDataFormatToSave());
                    return;
                }
                return;
            }
            sendFaceBigData(eventData.mFeature, eventData.mExtra);
        }

        public final void send() {
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                for (String str : this.mFpBigDataNameMap.keySet()) {
                    int intValue = ((Integer) this.mFpBigDataNameMap.get(str)).intValue();
                    if (intValue > 0) {
                        sendFingerprintBigData(str, String.valueOf(intValue));
                    }
                }
                writeDqaDataToFile("settings_fingerprint_ext_bigdata.xml", null);
            }
            if (this.FACE_FEATURE_HAL) {
                for (String str2 : this.mFaceBigDataNameMap.keySet()) {
                    int intValue2 = ((Integer) this.mFaceBigDataNameMap.get(str2)).intValue();
                    if (intValue2 > 0) {
                        sendFaceBigData(str2, String.valueOf(intValue2));
                    }
                }
                writeDqaDataToFile("settings_face_ext_bigdata.xml", null);
            }
            resetDqaData();
        }

        public final void fpParseAndUpdateMap(String str) {
            if (str != null) {
                try {
                    for (String str2 : this.mFpBigDataNameMap.keySet()) {
                        int indexOf = str.indexOf(str2);
                        if (indexOf >= 0) {
                            this.mFpBigDataNameMap.put(str2, Integer.valueOf(Integer.parseInt(str.substring(indexOf + 5).split(XmlUtils.STRING_ARRAY_SEPARATOR)[0].trim()) + ((Integer) this.mFpBigDataNameMap.get(str2)).intValue()));
                        }
                    }
                } catch (Exception e) {
                    Log.w("BiometricService.AM", "DQAManager.fpParseAndUpdateMap: " + e.getMessage());
                }
            }
        }

        public final void faceParseAndUpdateMap(String str) {
            if (str != null) {
                try {
                    for (String str2 : this.mFaceBigDataNameMap.keySet()) {
                        int indexOf = str.indexOf(str2);
                        if (indexOf >= 0) {
                            this.mFaceBigDataNameMap.put(str2, Integer.valueOf(Integer.parseInt(str.substring(indexOf + 5).split(XmlUtils.STRING_ARRAY_SEPARATOR)[0].trim()) + ((Integer) this.mFaceBigDataNameMap.get(str2)).intValue()));
                        }
                    }
                } catch (Exception e) {
                    Log.w("BiometricService.AM", "DQAManager.faceParseAndUpdateMap: " + e.getMessage());
                }
            }
        }

        public final void resetDqaData() {
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                this.mFpBigDataNameMap.put("FPIS", 0);
                this.mFpBigDataNameMap.put("FPIF", 0);
                this.mFpBigDataNameMap.put("FPGT", 0);
                this.mFpBigDataNameMap.put("FPQP", 0);
                this.mFpBigDataNameMap.put("FPQI", 0);
                this.mFpBigDataNameMap.put("FPQD", 0);
                this.mFpBigDataNameMap.put("FPQS", 0);
                this.mFpBigDataNameMap.put("FPQF", 0);
                this.mFpBigDataNameMap.put("FPQW", 0);
                this.mFpBigDataNameMap.put("FPQL", 0);
                this.mFpBigDataNameMap.put("FPMV", 0);
                this.mFpBigDataNameMap.put("FPOS", 0);
                this.mFpBigDataNameMap.put("FPOF", 0);
                this.mFpBigDataNameMap.put("FPFS", 0);
                this.mFpBigDataNameMap.put("FPFF", 0);
                this.mFpBigDataNameMap.put("FFOS", 0);
                this.mFpBigDataNameMap.put("FFOF", 0);
                this.mFpBigDataNameMap.put("FFFS", 0);
                this.mFpBigDataNameMap.put("FFFF", 0);
            }
            if (this.FACE_FEATURE_HAL) {
                this.mFaceBigDataNameMap.put("FAIS", 0);
                this.mFaceBigDataNameMap.put("FAIF", 0);
                this.mFaceBigDataNameMap.put("FAFS", 0);
                this.mFaceBigDataNameMap.put("FAFF", 0);
                this.mFaceBigDataNameMap.put("FATO", 0);
                this.mFaceBigDataNameMap.put("FAQN", 0);
                this.mFaceBigDataNameMap.put("FAQL", 0);
                this.mFaceBigDataNameMap.put("FAQF", 0);
                this.mFaceBigDataNameMap.put("FAQB", 0);
                this.mFaceBigDataNameMap.put("FAQS", 0);
                this.mFaceBigDataNameMap.put("FAQM", 0);
                this.mFaceBigDataNameMap.put("FALI", 0);
                this.mFaceBigDataNameMap.put("FABK", 0);
                this.mFaceBigDataNameMap.put("FAMO", 0);
                this.mFaceBigDataNameMap.put("FALQ", 0);
                this.mFaceBigDataNameMap.put("FANM", 0);
            }
        }

        public final void writeDqaDataToFile(final String str, final String str2) {
            SemBioFgThread.get().getHandler().post(new Runnable() { // from class: com.android.server.biometrics.SemBioAnalyticsManager$DQAManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemBioAnalyticsManager.DQAManager.lambda$writeDqaDataToFile$0(str2, str);
                }
            });
        }

        public static /* synthetic */ void lambda$writeDqaDataToFile$0(String str, String str2) {
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "DQAManager.writeDqaData: " + str);
            }
            File file = new File(Environment.getUserSystemDirectory(0), str2);
            if (str == null) {
                file.delete();
            } else {
                Utils.writeFile(file, str.getBytes());
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x0038  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final String readDqaDataFromFile(String str) {
            String str2;
            byte[] readFile = Utils.readFile(new File(Environment.getUserSystemDirectory(0), str));
            if (readFile != null) {
                try {
                    str2 = new String(readFile);
                } catch (Exception e) {
                    Log.w("BiometricService.AM", "DQAManager.readDqaDataFromFile: " + e.getMessage());
                }
                if (SemBioAnalyticsManager.DEBUG) {
                    Log.d("BiometricService.AM", "DQAManager.readDqaDataFromFile: " + str2);
                }
                return str2;
            }
            str2 = null;
            if (SemBioAnalyticsManager.DEBUG) {
            }
            return str2;
        }

        public final boolean needToSaveBigFeatures() {
            if (this.mPreviousSavedTime != 0 && System.currentTimeMillis() - this.mPreviousSavedTime <= ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                return false;
            }
            this.mPreviousSavedTime = System.currentTimeMillis();
            return true;
        }

        public final String fpGetDqaDataFormatToSave() {
            StringBuilder sb = new StringBuilder();
            try {
                for (String str : this.mFpBigDataNameMap.keySet()) {
                    sb.append(str);
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                    sb.append(this.mFpBigDataNameMap.get(str));
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                }
            } catch (Exception e) {
                Log.w("BiometricService.AM", "formatDqaDataToSave: " + e.toString());
            }
            String sb2 = sb.toString();
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "formatDqaDataToSave: formatData = [" + sb2 + "]");
            }
            return sb2;
        }

        public final String faceGetDqaDataFormatToSave() {
            StringBuilder sb = new StringBuilder();
            try {
                for (String str : this.mFaceBigDataNameMap.keySet()) {
                    sb.append(str);
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                    sb.append(this.mFaceBigDataNameMap.get(str));
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                }
            } catch (Exception e) {
                Log.w("BiometricService.AM", "formatDqaDataToSave: " + e.toString());
            }
            String sb2 = sb.toString();
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "formatDqaDataToSave: formatData = [" + sb2 + "]");
            }
            return sb2;
        }

        public final void sendFingerprintBigData(String str, String str2) {
            String str3 = "\"" + str + "\":\"" + str2 + "\"";
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "DQAManager.sendFingerprintBigData: " + str + XmlUtils.STRING_ARRAY_SEPARATOR + str2);
            }
            if (this.mSemHqmManager == null) {
                this.mSemHqmManager = (SemHqmManager) this.mContext.getSystemService(SemHqmManager.class);
            }
            SemHqmManager semHqmManager = this.mSemHqmManager;
            if (semHqmManager != null) {
                semHqmManager.sendHWParamToHQM(0, "BFS", str, "ph", "0.0", "sec", "", str3, "");
            } else {
                Log.e("BiometricService.AM", "DQAManager.sendFingerprintBigData: SemHqmManager is null!!");
            }
        }

        public final void sendFaceBigData(String str, String str2) {
            String str3 = "\"" + str + "\":\"" + str2 + "\"";
            if (SemBioAnalyticsManager.DEBUG) {
                Log.d("BiometricService.AM", "DQAManager.sendFaceBigData: " + str + XmlUtils.STRING_ARRAY_SEPARATOR + str2);
            }
            if (this.mSemHqmManager == null) {
                this.mSemHqmManager = (SemHqmManager) this.mContext.getSystemService(SemHqmManager.class);
            }
            SemHqmManager semHqmManager = this.mSemHqmManager;
            if (semHqmManager != null) {
                semHqmManager.sendHWParamToHQM(0, "Camera", str, "ph", "0.0", "sec", "", str3, "");
            } else {
                Log.e("BiometricService.AM", "DQAManager.sendFaceBigData: SemHqmManager is null!!");
            }
        }
    }
}
