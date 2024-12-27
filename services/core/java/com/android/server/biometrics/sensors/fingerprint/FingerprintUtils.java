package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.BiometricUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintUtils implements BiometricUtils {
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

    public FingerprintUtils(String str) {
        this.mFileName = str;
    }

    public static FingerprintAuthenticateOptions copyOptions(int i, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        return new FingerprintAuthenticateOptions.Builder().setUserId(i).setSensorId(fingerprintAuthenticateOptions.getSensorId()).setOpPackageName(fingerprintAuthenticateOptions.getOpPackageName()).setAttributionTag(fingerprintAuthenticateOptions.getAttributionTag()).setDisplayState(fingerprintAuthenticateOptions.getDisplayState()).setIgnoreEnrollmentState(fingerprintAuthenticateOptions.isIgnoreEnrollmentState()).build();
    }

    public static FingerprintUtils getInstance(int i) {
        FingerprintUtils fingerprintUtils;
        synchronized (sInstanceLock) {
            try {
                if (sInstances == null) {
                    sInstances = new SparseArray();
                }
                if (sInstances.get(i) == null) {
                    sInstances.put(i, new FingerprintUtils("settings_fingerprint_" + i + ".xml"));
                }
                fingerprintUtils = (FingerprintUtils) sInstances.get(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return fingerprintUtils;
    }

    public static void resetForTest() {
        synchronized (sInstanceLock) {
            sInstances = null;
        }
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

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final void addBiometricForUser(Context context, int i, Fingerprint fingerprint) {
        if (TextUtils.isEmpty(fingerprint.getName())) {
            fingerprint.setName(getStateForUser(context, i).getUniqueName());
        }
        FingerprintUserState stateForUser = getStateForUser(context, i);
        synchronized (stateForUser) {
            stateForUser.mBiometrics.add(fingerprint);
            AsyncTask.execute(stateForUser.mWriteStateRunnable);
        }
        semSendTemplateChangedBroadCast(i, fingerprint.getBiometricId(), context, new Intent(INTENT_ACTION_ADD_FINGERPRINT));
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final List getBiometricsForUser(Context context, int i) {
        return getStateForUser(context, i).getBiometrics();
    }

    public final FingerprintUserState getStateForUser(Context context, int i) {
        FingerprintUserState fingerprintUserState;
        synchronized (this) {
            try {
                fingerprintUserState = (FingerprintUserState) this.mUserStates.get(i);
                if (fingerprintUserState == null) {
                    fingerprintUserState = new FingerprintUserState(context, this.mFileName, i);
                    this.mUserStates.put(i, fingerprintUserState);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return fingerprintUserState;
    }

    public void putStateForUserForTest(int i, FingerprintUserState fingerprintUserState) {
        synchronized (this) {
            this.mUserStates.put(i, fingerprintUserState);
        }
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final void removeBiometricForUser(Context context, int i, int i2) {
        if (((ArrayList) getStateForUser(context, i).getBiometrics()).isEmpty()) {
            return;
        }
        getStateForUser(context, i).removeBiometric(i2);
        Intent intent = new Intent();
        if (((ArrayList) getStateForUser(context, i).getBiometrics()).isEmpty()) {
            intent.setAction(INTENT_ACTION_RESET_FINGERPRINT);
        } else {
            intent.setAction(INTENT_ACTION_REMOVE_FINGERPRINT);
        }
        semSendTemplateChangedBroadCast(i, i2, context, intent);
        SemBioAnalyticsManager semBioAnalyticsManager = SemBioAnalyticsManager.get();
        semBioAnalyticsManager.getClass();
        semBioAnalyticsManager.fpInsertLog(new SemBioAnalyticsManager.EventData(-1, 3, "FPRM", i2 == -1 ? "ALL" : Integer.toString(i2)));
        final SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
        semBioLoggingManager.getFpHandler().post(new Runnable() { // from class: com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda0
            public final /* synthetic */ String f$1 = "client";
            public final /* synthetic */ int f$2 = 0;

            @Override // java.lang.Runnable
            public final void run() {
                SemBioLoggingManager semBioLoggingManager2 = SemBioLoggingManager.this;
                String str = this.f$1;
                int i3 = this.f$2;
                semBioLoggingManager2.getClass();
                SemBioLoggingManager.LoggingInfo loggingInfo = new SemBioLoggingManager.LoggingInfo();
                loggingInfo.mType = "R";
                long currentTimeMillis = System.currentTimeMillis();
                loggingInfo.mStartTime = currentTimeMillis;
                loggingInfo.mResultTime = currentTimeMillis;
                loggingInfo.mPackageName = str;
                loggingInfo.mExtra = i3;
                semBioLoggingManager2.fpAddLoggingInfo(loggingInfo);
            }
        });
    }

    public final void semSendTemplateChangedBroadCast(final int i, final int i2, final Context context, final Intent intent) {
        Runnable runnable = new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Intent intent2 = intent;
                int i3 = i2;
                Context context2 = context;
                int i4 = i;
                intent2.putExtra("fingerIndex", i3);
                intent2.putExtra("verificationType", "Device Credential");
                intent2.addFlags(16777216);
                intent2.addFlags(268435456);
                try {
                    context2.sendBroadcastAsUser(intent2, new UserHandle(i4));
                    if (Utils.DEBUG) {
                        Slog.i("FingerprintService", "[" + intent2.getAction() + "] is sent with fingerId " + i3);
                    } else {
                        Slog.i("FingerprintService", "[" + intent2.getAction() + "] is sent");
                    }
                } catch (Exception e) {
                    NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("semSendTemplateChangedBroadCast: failed, "), "FingerprintService");
                }
            }
        };
        if (this.mIsBootComplete) {
            runnable.run();
            return;
        }
        if (this.mPendingRunnableAfterBootCompletion == null) {
            this.mPendingRunnableAfterBootCompletion = new ArrayList();
        }
        this.mPendingRunnableAfterBootCompletion.add(runnable);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final void setInvalidationInProgress(Context context, int i, boolean z) {
        FingerprintUserState stateForUser = getStateForUser(context, i);
        synchronized (stateForUser) {
            stateForUser.mInvalidationInProgress = z;
            AsyncTask.execute(stateForUser.mWriteStateRunnable);
        }
    }
}
