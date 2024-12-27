package com.android.server.ibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IntelligentBatterySaverScpmManager {
    public static IntelligentBatterySaverScpmManager sInstance;
    public final Context mContext;
    public SharedPreferences mPref;
    public final Uri SCPM_URI_V2 = Uri.parse("content://com.samsung.android.scpm.policy/");
    public int mPolicyControlSwitch = 3;

    public IntelligentBatterySaverScpmManager(Context context) {
        this.mContext = context;
    }

    public static synchronized IntelligentBatterySaverScpmManager getInstance(Context context) {
        IntelligentBatterySaverScpmManager intelligentBatterySaverScpmManager;
        synchronized (IntelligentBatterySaverScpmManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new IntelligentBatterySaverScpmManager(context);
                }
                intelligentBatterySaverScpmManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return intelligentBatterySaverScpmManager;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0087 -> B:20:0x00bb). Please report as a decompilation issue!!! */
    public final void parseData(ParcelFileDescriptor parcelFileDescriptor) {
        FileReader fileReader;
        Log.d("IntelligentBatterySaverScpmManager", "parseData");
        BufferedReader bufferedReader = null;
        try {
            try {
                fileReader = new FileReader(parcelFileDescriptor.getFileDescriptor());
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(fileReader);
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null) {
                                    break;
                                } else {
                                    sb.append(readLine);
                                }
                            }
                            JSONObject jSONObject = new JSONObject(new JSONTokener(sb.toString()));
                            boolean z = jSONObject.getBoolean("fast_drain_policy");
                            boolean z2 = jSONObject.getBoolean("google_app_policy");
                            Log.d(
                                    "IntelligentBatterySaverScpmManager",
                                    "mFastDrainPolicy = " + z + ", mGoogleAppPolicy = " + z2);
                            if (z) {
                                saveScpmFeatures(1, "fast_drain_policy");
                            } else {
                                saveScpmFeatures(0, "fast_drain_policy");
                            }
                            if (z2) {
                                saveScpmFeatures(1, "google_app_policy");
                            } else {
                                saveScpmFeatures(0, "google_app_policy");
                            }
                            try {
                                bufferedReader2.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            fileReader.close();
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            Log.e(
                                    "IntelligentBatterySaverScpmManager",
                                    "Unknown exception : " + e.getMessage());
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (fileReader != null) {
                                fileReader.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (fileReader == null) {
                                throw th;
                            }
                            try {
                                fileReader.close();
                                throw th;
                            } catch (Exception e5) {
                                e5.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e6) {
                    e = e6;
                }
            } catch (Exception e7) {
                e = e7;
                fileReader = null;
            } catch (Throwable th3) {
                th = th3;
                fileReader = null;
            }
        } catch (Exception e8) {
            e8.printStackTrace();
        }
    }

    public final void registerScpm() {
        Log.d("IntelligentBatterySaverScpmManager", "registerScpm");
        if (this.mContext
                        .getPackageManager()
                        .resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0)
                != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("packageName", "android");
                bundle.putString("appId", "r8namobmbc");
                bundle.putString("version", "1.0.0");
                bundle.putString("receiverPackageName", "android");
                Bundle call =
                        this.mContext
                                .getContentResolver()
                                .call(this.SCPM_URI_V2, "register", "android", bundle);
                if (call != null) {
                    int i = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1);
                    String string = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                    int i2 = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                    String string2 =
                            call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
                    if (i == 1) {
                        SharedPreferences sharedPreferences =
                                this.mContext.getSharedPreferences("ibs_prefs", 0);
                        this.mPref = sharedPreferences;
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("SCPMToken", string);
                        edit.apply();
                    } else {
                        Log.e(
                                "IntelligentBatterySaverScpmManager",
                                "failed to register: rcode = " + i2 + ", rmsg = " + string2);
                    }
                }
            } catch (Exception e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("cannot register package : "),
                        "IntelligentBatterySaverScpmManager");
            }
        }
    }

    public final void saveScpmFeatures(int i, String str) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("ibs_prefs", 0);
        this.mPref = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(str, i);
        edit.apply();
    }
}
