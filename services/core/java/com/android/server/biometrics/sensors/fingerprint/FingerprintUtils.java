package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.BiometricUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class FingerprintUtils implements BiometricUtils {
    static final String INTENT_ACTION_ADD_FINGERPRINT = "com.samsung.android.intent.action.FINGERPRINT_ADDED";
    static final String INTENT_ACTION_REMOVE_FINGERPRINT = "com.samsung.android.intent.action.FINGERPRINT_REMOVED";
    static final String INTENT_ACTION_RESET_FINGERPRINT = "com.samsung.android.intent.action.FINGERPRINT_RESET";
    static final String INTENT_DEFAULT_VERIFICATION_TYPE = "Device Credential";
    static final String INTENT_KEY_FINGERPRINT_INDEX = "fingerIndex";
    static final String INTENT_KEY_VERIFICATION_TYPE = "verificationType";
    public static final Object sInstanceLock = new Object();
    public static SparseArray sInstances;
    public final String mFileName;
    public boolean mIsBootComplete;
    ArrayList mPendingRunnableAfterBootCompletion;
    public final SparseArray mUserStates = new SparseArray();

    public static boolean isKnownAcquiredCode(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                return true;
            case 8:
            default:
                return false;
        }
    }

    public static boolean isKnownErrorCode(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public static boolean semIsAuthenticationFailedReasonEvent(int i) {
        return i >= 40000 && i <= 49999;
    }

    public static boolean semIsInternalClientFreeEvent(int i, int i2) {
        if (i != 6) {
            return false;
        }
        return i2 == 20001 || i2 == 20002 || i2 == 30001 || i2 == 30002;
    }

    public static boolean semIsQualityFailedEvent(int i, int i2) {
        switch (i) {
            case 6:
                if (i2 != 1001 && i2 != 1003 && i2 != 1002) {
                    return false;
                }
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public static boolean semIsRequestCommandEvent(int i, int i2) {
        if (i != 6) {
            return false;
        }
        return i2 == 10008 || i2 == 10009;
    }

    public static boolean semIsSkipEvent(int i, int i2) {
        return i == 6 && i2 == 10004;
    }

    public static int semUpdateVendorInfo(int i, int i2) {
        if (i == 6 && i2 == 70001) {
            return 10004;
        }
        return i2;
    }

    public static FingerprintUtils getInstance(int i) {
        return getInstance(i, null);
    }

    public static FingerprintUtils getInstance(int i, String str) {
        FingerprintUtils fingerprintUtils;
        synchronized (sInstanceLock) {
            if (sInstances == null) {
                sInstances = new SparseArray();
            }
            if (sInstances.get(i) == null) {
                if (str == null) {
                    str = "settings_fingerprint_" + i + ".xml";
                }
                sInstances.put(i, new FingerprintUtils(str));
            }
            fingerprintUtils = (FingerprintUtils) sInstances.get(i);
        }
        return fingerprintUtils;
    }

    public static FingerprintUtils getLegacyInstance(int i) {
        return getInstance(i, "settings_fingerprint.xml");
    }

    public FingerprintUtils(String str) {
        this.mFileName = str;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public List getBiometricsForUser(Context context, int i) {
        return getStateForUser(context, i).getBiometrics();
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void addBiometricForUser(Context context, int i, Fingerprint fingerprint) {
        if (TextUtils.isEmpty(fingerprint.getName())) {
            fingerprint.setName(getUniqueName(context, i));
        }
        getStateForUser(context, i).addBiometric(fingerprint);
        semSendAddTemplateBroadcast(context, i, fingerprint.getBiometricId());
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void removeBiometricForUser(Context context, int i, int i2) {
        if (getStateForUser(context, i).getBiometrics().isEmpty()) {
            return;
        }
        getStateForUser(context, i).removeBiometric(i2);
        semSendRemoveTemplateBroadcast(context, i, i2);
        SemBioAnalyticsManager.get().fpInsertLogRemove(i2);
        SemBioLoggingManager.get().fpRemoved("client", 0);
    }

    public void renameBiometricForUser(Context context, int i, int i2, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        getStateForUser(context, i).renameBiometric(i2, charSequence);
    }

    public CharSequence getUniqueName(Context context, int i) {
        return getStateForUser(context, i).getUniqueName();
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void setInvalidationInProgress(Context context, int i, boolean z) {
        getStateForUser(context, i).setInvalidationInProgress(z);
    }

    public boolean isInvalidationInProgress(Context context, int i) {
        return getStateForUser(context, i).isInvalidationInProgress();
    }

    public void putStateForUserForTest(int i, FingerprintUserState fingerprintUserState) {
        synchronized (this) {
            this.mUserStates.put(i, fingerprintUserState);
        }
    }

    public final FingerprintUserState getStateForUser(Context context, int i) {
        FingerprintUserState fingerprintUserState;
        synchronized (this) {
            fingerprintUserState = (FingerprintUserState) this.mUserStates.get(i);
            if (fingerprintUserState == null) {
                fingerprintUserState = new FingerprintUserState(context, i, this.mFileName);
                this.mUserStates.put(i, fingerprintUserState);
            }
        }
        return fingerprintUserState;
    }

    public static void resetForTest() {
        synchronized (sInstanceLock) {
            sInstances = null;
        }
    }

    public static int semGetMaxTemplateNumberFromSPF() {
        String[] split = "google_touch_display_optical,settings=3,screen_off".split(",");
        int length = split.length;
        for (int i = 0; i < length; i++) {
            String str = split[i];
            if (str.startsWith("settings=")) {
                try {
                    return Integer.parseInt(str.substring(9));
                } catch (Exception e) {
                    Slog.e("FingerprintService", "semGetMaxTemplateNumber: failed to read sensor config", e);
                }
            }
        }
        return 4;
    }

    public static int semGetSensorType() {
        int semGetSensorPosition = FingerprintManager.semGetSensorPosition();
        int i = 1;
        if (semGetSensorPosition == 1) {
            return 5;
        }
        if (semGetSensorPosition == 2) {
            return SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC ? 2 : 3;
        }
        if (semGetSensorPosition != 3) {
            i = 4;
            if (semGetSensorPosition != 4) {
                return 0;
            }
        }
        return i;
    }

    public final void semSendAddTemplateBroadcast(Context context, int i, int i2) {
        semSendTemplateChangedBroadCast(context, new Intent(INTENT_ACTION_ADD_FINGERPRINT), i, i2);
    }

    public final void semSendRemoveTemplateBroadcast(Context context, int i, int i2) {
        Intent intent = new Intent();
        if (getStateForUser(context, i).getBiometrics().isEmpty()) {
            intent.setAction(INTENT_ACTION_RESET_FINGERPRINT);
        } else {
            intent.setAction(INTENT_ACTION_REMOVE_FINGERPRINT);
        }
        semSendTemplateChangedBroadCast(context, intent, i, i2);
    }

    public final void semSendTemplateChangedBroadCast(final Context context, final Intent intent, final int i, final int i2) {
        Runnable runnable = new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FingerprintUtils.lambda$semSendTemplateChangedBroadCast$0(intent, i2, context, i);
            }
        };
        if (!this.mIsBootComplete) {
            if (this.mPendingRunnableAfterBootCompletion == null) {
                this.mPendingRunnableAfterBootCompletion = new ArrayList();
            }
            this.mPendingRunnableAfterBootCompletion.add(runnable);
            return;
        }
        runnable.run();
    }

    public static /* synthetic */ void lambda$semSendTemplateChangedBroadCast$0(Intent intent, int i, Context context, int i2) {
        intent.putExtra(INTENT_KEY_FINGERPRINT_INDEX, i);
        intent.putExtra(INTENT_KEY_VERIFICATION_TYPE, INTENT_DEFAULT_VERIFICATION_TYPE);
        intent.addFlags(16777216);
        intent.addFlags(268435456);
        try {
            context.sendBroadcastAsUser(intent, new UserHandle(i2));
            if (Utils.DEBUG) {
                Slog.i("FingerprintService", "[" + intent.getAction() + "] is sent with fingerId " + i);
            } else {
                Slog.i("FingerprintService", "[" + intent.getAction() + "] is sent");
            }
        } catch (Exception e) {
            Slog.e("FingerprintService", "semSendTemplateChangedBroadCast: failed, " + e.getMessage());
        }
    }

    public static boolean semHasPrivilegedFlag(Bundle bundle, int i) {
        return (bundle == null || (bundle.getInt("sem_privileged_attr", 0) & i) == 0) ? false : true;
    }

    public void onBootComplete() {
        this.mIsBootComplete = true;
        ArrayList arrayList = this.mPendingRunnableAfterBootCompletion;
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.mPendingRunnableAfterBootCompletion.clear();
        this.mPendingRunnableAfterBootCompletion = null;
    }

    public CharSequence getFingerprintName(Context context, int i, int i2) {
        List<Fingerprint> biometricsForUser = getBiometricsForUser(context, i2);
        if (biometricsForUser == null) {
            return "";
        }
        for (Fingerprint fingerprint : biometricsForUser) {
            if (fingerprint.getBiometricId() == i) {
                return fingerprint.getName();
            }
        }
        return "";
    }

    public static FingerprintAuthenticateOptions copyOptions(int i, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        return new FingerprintAuthenticateOptions.Builder().setUserId(i).setSensorId(fingerprintAuthenticateOptions.getSensorId()).setOpPackageName(fingerprintAuthenticateOptions.getOpPackageName()).setAttributionTag(fingerprintAuthenticateOptions.getAttributionTag()).setDisplayState(fingerprintAuthenticateOptions.getDisplayState()).setIgnoreEnrollmentState(fingerprintAuthenticateOptions.isIgnoreEnrollmentState()).build();
    }

    public static void semGetSideSensorPosition(Bundle bundle) {
        byte[] readFile = Utils.readFile(new File("/sys/class/fingerprint/fingerprint/position"));
        if (readFile != null) {
            try {
                ArrayList arrayList = new ArrayList(Arrays.asList(new String(readFile, StandardCharsets.UTF_8).trim().split("\\,")));
                if (arrayList.size() < 2 || ((String) arrayList.get(0)).equals("NA")) {
                    return;
                }
                if (arrayList.size() == 2) {
                    arrayList.add("0");
                }
                bundle.putStringArray("sem_area", (String[]) arrayList.toArray(new String[arrayList.size()]));
            } catch (Exception e) {
                Slog.w("FingerprintService", "semGetSensorPosition: " + e.getMessage());
            }
        }
    }
}
