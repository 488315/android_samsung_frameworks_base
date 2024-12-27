package com.android.server.biometrics;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.BiometricNotificationImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class AuthenticationStatsCollector {
    static final int MAXIMUM_ENROLLMENT_NOTIFICATIONS = 1;
    public final AuthenticationStatsPersister mAuthenticationStatsPersister;
    public final BiometricNotificationImpl mBiometricNotification;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public final boolean mEnabled;
    public final FaceManager mFaceManager;
    public final FingerprintManager mFingerprintManager;
    public final int mModality;
    public final PackageManager mPackageManager;
    public final float mThreshold;
    public final Map mUserAuthenticationStatsMap;

    public AuthenticationStatsCollector(
            Context context, int i, BiometricNotificationImpl biometricNotificationImpl) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.biometrics.AuthenticationStatsCollector.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra =
                                intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000
                                || !intent.getAction()
                                        .equals("android.intent.action.USER_REMOVED")) {
                            return;
                        }
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                intExtra,
                                "Removing data for user: ",
                                "AuthenticationStatsCollector");
                        AuthenticationStatsCollector authenticationStatsCollector =
                                AuthenticationStatsCollector.this;
                        ((HashMap) authenticationStatsCollector.mUserAuthenticationStatsMap)
                                .remove(Integer.valueOf(intExtra));
                        AuthenticationStatsPersister authenticationStatsPersister =
                                authenticationStatsCollector.mAuthenticationStatsPersister;
                        authenticationStatsPersister.getClass();
                        try {
                            HashSet hashSet =
                                    new HashSet(
                                            authenticationStatsPersister.mSharedPreferences
                                                    .getStringSet("frr_stats", Set.of()));
                            Iterator it = hashSet.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                JSONObject jSONObject = new JSONObject((String) it.next());
                                if ((jSONObject.has("user_id")
                                                ? jSONObject.getString("user_id")
                                                : "")
                                        .equals(String.valueOf(intExtra))) {
                                    it.remove();
                                    break;
                                }
                            }
                            authenticationStatsPersister
                                    .mSharedPreferences
                                    .edit()
                                    .putStringSet("frr_stats", hashSet)
                                    .apply();
                        } catch (JSONException unused) {
                        }
                    }
                };
        this.mContext = context;
        this.mEnabled = context.getResources().getBoolean(R.bool.config_bugReportHandlerEnabled);
        float fraction =
                context.getResources()
                        .getFraction(R.fraction.config_biometricNotificationFrrThreshold, 1, 1);
        this.mThreshold = fraction;
        this.mUserAuthenticationStatsMap = new HashMap();
        this.mModality = i;
        this.mBiometricNotification = biometricNotificationImpl;
        this.mPackageManager = context.getPackageManager();
        this.mFaceManager = (FaceManager) context.getSystemService(FaceManager.class);
        this.mFingerprintManager =
                (FingerprintManager) context.getSystemService(FingerprintManager.class);
        AuthenticationStatsPersister authenticationStatsPersister =
                new AuthenticationStatsPersister(context);
        this.mAuthenticationStatsPersister = authenticationStatsPersister;
        initializeUserAuthenticationStatsMap();
        authenticationStatsPersister
                .mSharedPreferences
                .edit()
                .putFloat("frr_threshold", fraction)
                .apply();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public AuthenticationStats getAuthenticationStatsForUser(int i) {
        return (AuthenticationStats)
                ((HashMap) this.mUserAuthenticationStatsMap).getOrDefault(Integer.valueOf(i), null);
    }

    public final void initializeUserAuthenticationStatsMap() {
        AuthenticationStatsPersister authenticationStatsPersister =
                this.mAuthenticationStatsPersister;
        authenticationStatsPersister.getClass();
        ArrayList arrayList = new ArrayList();
        for (String str :
                authenticationStatsPersister.mSharedPreferences.getStringSet(
                        "frr_stats", Set.of())) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = this.mModality;
                if (i == 4) {
                    arrayList.add(
                            new AuthenticationStats(
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "user_id", -10000),
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "face_attempts", 0),
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "face_rejections", 0),
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "enrollment_notifications", 0),
                                    i));
                } else if (i == 1) {
                    arrayList.add(
                            new AuthenticationStats(
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "user_id", -10000),
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "fingerprint_attempts", 0),
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "fingerprint_rejections", 0),
                                    AuthenticationStatsPersister.getIntValue(
                                            jSONObject, "enrollment_notifications", 0),
                                    i));
                }
            } catch (JSONException unused) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Unable to resolve authentication stats JSON: ",
                        str,
                        "AuthenticationStatsPersister");
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AuthenticationStats authenticationStats = (AuthenticationStats) it.next();
            ((HashMap) this.mUserAuthenticationStatsMap)
                    .put(Integer.valueOf(authenticationStats.mUserId), authenticationStats);
        }
    }

    public void setAuthenticationStatsForUser(int i, AuthenticationStats authenticationStats) {
        ((HashMap) this.mUserAuthenticationStatsMap).put(Integer.valueOf(i), authenticationStats);
    }
}
