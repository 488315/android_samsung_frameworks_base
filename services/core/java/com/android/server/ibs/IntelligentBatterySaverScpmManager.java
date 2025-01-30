package com.android.server.ibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverScpmManager {
    public static IntelligentBatterySaverScpmManager sInstance;
    public Context mContext;
    public SharedPreferences mPref;
    public final Uri SCPM_URI_V2 = Uri.parse("content://com.samsung.android.scpm.policy/");
    public int mPolicyControlSwitch = 3;

    public IntelligentBatterySaverScpmManager(Context context) {
        this.mContext = context;
    }

    public static synchronized IntelligentBatterySaverScpmManager getInstance(Context context) {
        IntelligentBatterySaverScpmManager intelligentBatterySaverScpmManager;
        synchronized (IntelligentBatterySaverScpmManager.class) {
            if (sInstance == null) {
                sInstance = new IntelligentBatterySaverScpmManager(context);
            }
            intelligentBatterySaverScpmManager = sInstance;
        }
        return intelligentBatterySaverScpmManager;
    }

    public final void saveScpmFeatures(String str, int i) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("ibs_prefs", 0);
        this.mPref = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public final int getScpmFeatures(String str) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("ibs_prefs", 0);
        this.mPref = sharedPreferences;
        return sharedPreferences.getInt(str, 1);
    }

    public final void saveCachedToken(String str) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("ibs_prefs", 0);
        this.mPref = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("SCPMToken", str);
        edit.apply();
    }

    public final String getCachedToken() {
        this.mPref = this.mContext.getSharedPreferences("ibs_prefs", 0);
        Log.d("IntelligentBatterySaverScpmManager", "SCPMToken - " + this.mPref.getString("SCPMToken", null));
        return this.mPref.getString("SCPMToken", null);
    }

    public void registerScpm() {
        Log.d("IntelligentBatterySaverScpmManager", "registerScpm");
        if (isAvailable()) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("packageName", "android");
                bundle.putString("appId", "r8namobmbc");
                bundle.putString("version", "1.0.0");
                bundle.putString("receiverPackageName", "android");
                Bundle call = this.mContext.getContentResolver().call(this.SCPM_URI_V2, "register", "android", bundle);
                if (call != null) {
                    int i = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1);
                    String string = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                    int i2 = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                    String string2 = call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
                    if (i == 1) {
                        saveCachedToken(string);
                    } else {
                        Log.e("IntelligentBatterySaverScpmManager", "failed to register: rcode = " + i2 + ", rmsg = " + string2);
                    }
                }
            } catch (Exception e) {
                Log.e("IntelligentBatterySaverScpmManager", "cannot register package : " + e.getMessage());
            }
        }
    }

    public final boolean isAvailable() {
        return this.mContext.getPackageManager().resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0) != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x007c A[Catch: all -> 0x00e3, Exception -> 0x00e5, TRY_ENTER, TryCatch #6 {Exception -> 0x00e5, blocks: (B:12:0x007c, B:21:0x00b3), top: B:10:0x007a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00df A[Catch: IOException -> 0x00f4, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x00f4, blocks: (B:14:0x00df, B:36:0x00f0), top: B:10:0x007a }] */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b3 A[Catch: all -> 0x00e3, Exception -> 0x00e5, TRY_LEAVE, TryCatch #6 {Exception -> 0x00e5, blocks: (B:12:0x007c, B:21:0x00b3), top: B:10:0x007a, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getData() {
        Uri uri;
        Bundle bundle;
        Log.d("IntelligentBatterySaverScpmManager", "getData");
        if (isAvailable()) {
            String cachedToken = getCachedToken();
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                uri = Uri.parse("content://com.samsung.android.scpm.policy/" + cachedToken + "/ibs");
            } catch (FileNotFoundException e) {
                e = e;
                uri = null;
            } catch (Exception e2) {
                e = e2;
                uri = null;
            }
            try {
                try {
                    try {
                        try {
                            parcelFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(uri, "r");
                            Log.d("IntelligentBatterySaverScpmManager", "uri: " + uri);
                        } catch (FileNotFoundException e3) {
                            e = e3;
                            Log.e("IntelligentBatterySaverScpmManager", "File not found!");
                            e.printStackTrace();
                            bundle = new Bundle();
                            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, cachedToken);
                            if (parcelFileDescriptor == null) {
                            }
                            if (parcelFileDescriptor != null) {
                            }
                        } catch (Exception e4) {
                            e = e4;
                            Log.e("IntelligentBatterySaverScpmManager", "Open file fail!", e);
                            e.printStackTrace();
                            bundle = new Bundle();
                            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, cachedToken);
                            if (parcelFileDescriptor == null) {
                            }
                            if (parcelFileDescriptor != null) {
                            }
                        }
                        if (parcelFileDescriptor == null) {
                            Bundle call = this.mContext.getContentResolver().call(uri, "getLastError", "android", bundle);
                            Log.d("IntelligentBatterySaverScpmManager", "cannot get new policy : " + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE) + ", " + call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
                        } else {
                            Log.d("IntelligentBatterySaverScpmManager", "policy filterId : " + this.mContext.getContentResolver().call(uri, "getStatus", "android", bundle).getString("filterId"));
                            parseData(parcelFileDescriptor);
                        }
                    } catch (Throwable th) {
                        if (parcelFileDescriptor != null) {
                            try {
                                parcelFileDescriptor.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e6) {
                    e6.printStackTrace();
                    return;
                }
            } catch (Exception e7) {
                Log.e("IntelligentBatterySaverScpmManager", "getData fail because of exception!", e7);
                e7.printStackTrace();
                if (parcelFileDescriptor == null) {
                    return;
                } else {
                    parcelFileDescriptor.close();
                }
            }
            bundle = new Bundle();
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, cachedToken);
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v12, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x00b3 -> B:21:0x00b6). Please report as a decompilation issue!!! */
    public final void parseData(ParcelFileDescriptor parcelFileDescriptor) {
        FileReader fileReader;
        BufferedReader bufferedReader;
        Log.d("IntelligentBatterySaverScpmManager", "parseData");
        ?? r2 = 0;
        r2 = 0;
        r2 = 0;
        r2 = 0;
        r2 = 0;
        try {
            try {
                try {
                    fileReader = new FileReader(parcelFileDescriptor.getFileDescriptor());
                    try {
                        bufferedReader = new BufferedReader(fileReader);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else {
                            sb.append(readLine);
                        }
                    }
                    JSONObject jSONObject = new JSONObject(new JSONTokener(sb.toString()));
                    boolean z = jSONObject.getBoolean("fast_drain_policy");
                    r2 = jSONObject.getBoolean("google_app_policy");
                    Log.d("IntelligentBatterySaverScpmManager", "mFastDrainPolicy = " + z + ", mGoogleAppPolicy = " + r2);
                    if (z) {
                        saveScpmFeatures("fast_drain_policy", 1);
                    } else {
                        saveScpmFeatures("fast_drain_policy", 0);
                    }
                    if (r2 != 0) {
                        saveScpmFeatures("google_app_policy", 1);
                    } else {
                        saveScpmFeatures("google_app_policy", 0);
                    }
                    try {
                        bufferedReader.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    fileReader.close();
                } catch (Exception e3) {
                    e = e3;
                    r2 = bufferedReader;
                    Log.e("IntelligentBatterySaverScpmManager", "Unknown exception : " + e.getMessage());
                    if (r2 != 0) {
                        try {
                            r2.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    r2 = bufferedReader;
                    if (r2 != 0) {
                        try {
                            r2.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                            throw th;
                        } catch (Exception e6) {
                            e6.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
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

    public void updateSCPMParametersFromDB(IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy) {
        Log.d("IntelligentBatterySaverScpmManager", "updateSCPMParametersFromDB");
        getData();
        if (getScpmFeatures("fast_drain_policy") == 1) {
            Log.d("IntelligentBatterySaverScpmManager", "Enable fast drain policy!");
            this.mPolicyControlSwitch |= 1;
            intelligentBatterySaverFastDrainPolicy.setIBSFastDrainPolicyEnable(true);
        } else {
            Log.d("IntelligentBatterySaverScpmManager", "Disable fast drain policy!");
            this.mPolicyControlSwitch &= -2;
            intelligentBatterySaverFastDrainPolicy.setIBSFastDrainPolicyEnable(false);
        }
        if (getScpmFeatures("google_app_policy") == 1) {
            Log.d("IntelligentBatterySaverScpmManager", "Enable google app policy!");
            this.mPolicyControlSwitch |= 2;
        } else {
            Log.d("IntelligentBatterySaverScpmManager", "Disable google app policy!");
            this.mPolicyControlSwitch &= -3;
        }
    }

    public boolean isGoogleAppPolicyDisabled() {
        return (this.mPolicyControlSwitch & 2) == 0;
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("");
        printWriter.println("IntelligentBatterySaverScpmManager adapt to scpm v2 ");
        printWriter.println("IBS 's mPolicyControlSwitch:" + Integer.toBinaryString(this.mPolicyControlSwitch));
    }
}
