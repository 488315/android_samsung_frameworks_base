package vendor.samsung.frameworks.codecsolution;

import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;

/* loaded from: classes.dex */
public class SCPMV2Helper {
    private static final String CODEC_SOLUTION_APP_ID = "oqfypdqvry";
    private static final String CODEC_SOLUTION_PACKAGE_NAME = "android";
    private static final String CODEC_SOLUTION_POLICY_GROUP = "CODEC_SOLUTOIN";
    private static final String CODEC_SOLUTION_VERSION_DUMMY = "0.0.0";
    private static final String HDR2SDR_CONVERTER_POLICY_ID = "Ai8DCJEAJf";
    private static final String HDR2SDR_CONVERTER_POLICY_NAME = "HDR2SDR_CONVERTER";
    private static final String HDR2SDR_CONVERTER_VERSION_STRING = "hdr2sdr.dummy";
    private static final String OFFLOAD_OPUS_DEC_ENABLE_POLICY_NAME = "opus-offload-enable-list";
    private static final String SCPMV2_KEY_APP_ID = "appId";
    private static final String SCPMV2_KEY_APP_SIGNATURE = "appSignature";
    private static final String SCPMV2_KEY_PACKAGE_NAME = "packageName";
    private static final String SCPMV2_KEY_RCODE = "rcode";
    private static final String SCPMV2_KEY_RECEIVER_PACKAGE_NAME = "receiverPackageName";
    private static final String SCPMV2_KEY_RESULT = "result";
    private static final String SCPMV2_KEY_RMSG = "rmsg";
    private static final String SCPMV2_KEY_TOKEN = "token";
    private static final String SCPMV2_KEY_VERSION = "version";
    private static final String SCPMV2_METHOD_GET_LAST_ERROR = "getLastError";
    private static final String SCPMV2_METHOD_GET_STATUS = "getStatus";
    private static final String SCPMV2_METHOD_INITIALIZE = "initialize";
    private static final String SCPMV2_METHOD_REGISTER = "register";
    private static final String SCPMV2_METHOD_UNREGISTER = "unregister";
    private static final String SCPMV2_PACKAGE_NAME = "com.samsung.android.scpm";
    private static final String SCPMV2_PROVIDER_AUTHORITY = "com.samsung.android.scpm.policy";
    private static final Uri SCPMV2_PROVIDER_URI =
            Uri.parse("content://com.samsung.android.scpm.policy/");
    private static final String TAG = "CodecSolution_SCPMV2Helper";
    private Context mContext;
    private String mToken = null;
    private JSONArray mHDR2SDRAllowlist = null;
    private JSONArray mOpusOffloadEnabledList = null;

    public SCPMV2Helper(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.FileReader, java.io.Reader] */
    private boolean initH2SCAllowlist() {
        FileReader fileReader = "Exception : Failed to get last error : ";
        Log.d(TAG, "initH2SCAllowlist()");
        if (this.mToken == null) {
            Log.i(TAG, "Not registered yet");
            return false;
        }
        Uri parse =
                Uri.parse(
                        "content://com.samsung.android.scpm.policy/"
                                + this.mToken
                                + "/HDR2SDR_CONVERTER");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ParcelFileDescriptor openFileDescriptor =
                    this.mContext.getContentResolver().openFileDescriptor(parse, "r");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            BufferedReader bufferedReader = null;
            Bundle bundle = null;
            r5 = null;
            BufferedReader bufferedReader2 = null;
            bufferedReader = null;
            try {
                if (openFileDescriptor == null) {
                    Log.w(TAG, "ParcelFileDescriptor is null");
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(SCPMV2_KEY_TOKEN, this.mToken);
                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        try {
                            bundle =
                                    this.mContext
                                            .getContentResolver()
                                            .call(
                                                    parse,
                                                    SCPMV2_METHOD_GET_LAST_ERROR,
                                                    CODEC_SOLUTION_PACKAGE_NAME,
                                                    bundle2);
                        } finally {
                        }
                    } catch (Exception e) {
                        Log.w(TAG, "Exception : Failed to get last error : " + e.getMessage());
                        e.printStackTrace();
                    }
                    if (bundle == null) {
                        Log.w(TAG, "Bundle is null");
                    } else {
                        Log.w(
                                TAG,
                                "Failed to get new policy : rcode["
                                        + bundle.getInt(SCPMV2_KEY_RCODE, -1)
                                        + "], rmsg["
                                        + bundle.getString(SCPMV2_KEY_RMSG, "null")
                                        + "]");
                    }
                    return false;
                }
                try {
                    fileReader = new FileReader(openFileDescriptor.getFileDescriptor());
                    try {
                        BufferedReader bufferedReader3 = new BufferedReader(fileReader);
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String readLine = bufferedReader3.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                            }
                            JSONArray jSONArray =
                                    new JSONObject(new JSONTokener(sb.toString()))
                                            .getJSONArray("hdr2sdr_converter_allowlist");
                            this.mHDR2SDRAllowlist = jSONArray;
                            if (jSONArray == null) {
                                Log.w(TAG, "Failed to get allowlist array");
                                try {
                                    bufferedReader3.close();
                                    fileReader.close();
                                    openFileDescriptor.close();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                return false;
                            }
                            try {
                                bufferedReader3.close();
                                fileReader.close();
                                openFileDescriptor.close();
                                return true;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return true;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            bufferedReader2 = bufferedReader3;
                            Log.w(TAG, "Unknown exception : " + e.getMessage());
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                    return false;
                                }
                            }
                            if (fileReader != 0) {
                                fileReader.close();
                            }
                            openFileDescriptor.close();
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader3;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e6) {
                                    e6.printStackTrace();
                                    throw th;
                                }
                            }
                            if (fileReader != 0) {
                                fileReader.close();
                            }
                            openFileDescriptor.close();
                            throw th;
                        }
                    } catch (Exception e7) {
                        e = e7;
                    }
                } catch (Exception e8) {
                    e = e8;
                    fileReader = 0;
                } catch (Throwable th2) {
                    th = th2;
                    fileReader = 0;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e9) {
            Log.w(TAG, "Exception : Failed to open fd : " + e9.getMessage());
            e9.printStackTrace();
            return false;
        } finally {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.FileReader, java.io.Reader] */
    private boolean initOpusOffloadEnabledList() {
        FileReader fileReader = "Exception : Failed to get last error : ";
        Log.d(TAG, "initOpusOffloadEnabledList()");
        if (this.mToken == null) {
            Log.i(TAG, "Not registered yet");
            return false;
        }
        Uri parse =
                Uri.parse(
                        "content://com.samsung.android.scpm.policy/"
                                + this.mToken
                                + "/opus-offload-enable-list");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ParcelFileDescriptor openFileDescriptor =
                    this.mContext.getContentResolver().openFileDescriptor(parse, "r");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            BufferedReader bufferedReader = null;
            Bundle bundle = null;
            r5 = null;
            BufferedReader bufferedReader2 = null;
            bufferedReader = null;
            try {
                if (openFileDescriptor == null) {
                    Log.w(TAG, " ParcelFileDescriptor is null");
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(SCPMV2_KEY_TOKEN, this.mToken);
                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        try {
                            bundle =
                                    this.mContext
                                            .getContentResolver()
                                            .call(
                                                    parse,
                                                    SCPMV2_METHOD_GET_LAST_ERROR,
                                                    CODEC_SOLUTION_PACKAGE_NAME,
                                                    bundle2);
                        } finally {
                        }
                    } catch (Exception e) {
                        Log.w(TAG, "Exception : Failed to get last error : " + e.getMessage());
                        e.printStackTrace();
                    }
                    if (bundle == null) {
                        Log.w(TAG, "Bundle is null");
                    } else {
                        Log.w(
                                TAG,
                                "Failed to get new policy : rcode["
                                        + bundle.getInt(SCPMV2_KEY_RCODE, -1)
                                        + "], rmsg["
                                        + bundle.getString(SCPMV2_KEY_RMSG, "null")
                                        + "]");
                    }
                    return false;
                }
                try {
                    fileReader = new FileReader(openFileDescriptor.getFileDescriptor());
                    try {
                        BufferedReader bufferedReader3 = new BufferedReader(fileReader);
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String readLine = bufferedReader3.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                            }
                            JSONArray jSONArray =
                                    new JSONObject(new JSONTokener(sb.toString()))
                                            .getJSONArray("opus_offload_allowlist");
                            this.mOpusOffloadEnabledList = jSONArray;
                            if (jSONArray == null) {
                                Log.w(TAG, " Failed to get enabledlist array");
                                try {
                                    bufferedReader3.close();
                                    fileReader.close();
                                    openFileDescriptor.close();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                return false;
                            }
                            try {
                                bufferedReader3.close();
                                fileReader.close();
                                openFileDescriptor.close();
                                return true;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return true;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            bufferedReader2 = bufferedReader3;
                            Log.w(TAG, "Unknown exception : " + e.getMessage());
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                    return false;
                                }
                            }
                            if (fileReader != 0) {
                                fileReader.close();
                            }
                            openFileDescriptor.close();
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader3;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e6) {
                                    e6.printStackTrace();
                                    throw th;
                                }
                            }
                            if (fileReader != 0) {
                                fileReader.close();
                            }
                            openFileDescriptor.close();
                            throw th;
                        }
                    } catch (Exception e7) {
                        e = e7;
                    }
                } catch (Exception e8) {
                    e = e8;
                    fileReader = 0;
                } catch (Throwable th2) {
                    th = th2;
                    fileReader = 0;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e9) {
            Log.w(TAG, " Exception : Failed to open fd : " + e9.getMessage());
            e9.printStackTrace();
            return false;
        } finally {
        }
    }

    public String getH2SCAllowlistVersion() {
        Log.d(TAG, "getH2SCAllowlistVersion()");
        if (!isAvailable()) {
            Log.w(TAG, "SCPMv2 is not available.");
            return "Not available";
        }
        if (this.mHDR2SDRAllowlist == null) {
            if (!initH2SCAllowlist()) {
                Log.w(TAG, "Failed to init H2SCAllowlist.");
                return "Not updated";
            }
            if (this.mHDR2SDRAllowlist == null) {
                Log.w(TAG, "Allowlist is null.");
                return "Not updated";
            }
        }
        for (int i = 0; i < this.mHDR2SDRAllowlist.length(); i++) {
            try {
                JSONObject jSONObject = this.mHDR2SDRAllowlist.getJSONObject(i);
                String string = jSONObject.getString("package");
                if (string != null && string.equals(HDR2SDR_CONVERTER_VERSION_STRING)) {
                    String string2 = jSONObject.getString("support");
                    return string2 == null ? "null" : string2;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }
        return "Not found";
    }

    public String getToken() {
        return this.mToken;
    }

    public boolean isAvailable() {
        return this.mContext
                        .getPackageManager()
                        .resolveContentProvider(SCPMV2_PROVIDER_AUTHORITY, 0)
                != null;
    }

    public Utils.QueryResult isInH2SCAllowlist(String str, String str2) {
        Log.d(TAG, "isInH2SCAllowlist : procName(" + str + "), chipVendor(" + str2 + ")");
        if (str == null || str2 == null) {
            Log.w(TAG, "Invalid args.");
            return Utils.QueryResult.NOT_FOUND;
        }
        if (!isAvailable()) {
            Log.w(TAG, "SCPMv2 is not available.");
            return Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
        }
        if (this.mHDR2SDRAllowlist == null) {
            if (!initH2SCAllowlist()) {
                Log.w(TAG, "Failed to init H2SCAllowlist.");
                return Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
            }
            if (this.mHDR2SDRAllowlist == null) {
                Log.w(TAG, "Allowlist is null.");
                return Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
            }
        }
        for (int i = 0; i < this.mHDR2SDRAllowlist.length(); i++) {
            try {
                JSONObject jSONObject = this.mHDR2SDRAllowlist.getJSONObject(i);
                String string = jSONObject.getString("package");
                if (string != null && string.equals(str)) {
                    String string2 = jSONObject.getString("support");
                    if (string2 == null) {
                        return Utils.QueryResult.NOT_FOUND;
                    }
                    if (!string2.contains(str2)) {
                        return Utils.QueryResult.NOT_FOUND;
                    }
                    Log.i(TAG, "support " + i);
                    return Utils.QueryResult.FOUND;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Utils.QueryResult.NOT_FOUND;
    }

    public Utils.QueryResult isInOpusOffloadEnabledList(String str, int i) {
        Log.d(TAG, "isInOpusOffloadEnabledList : param1(" + str + ")");
        if (str == null) {
            Log.w(TAG, "Invalid args.");
            return Utils.QueryResult.NOT_FOUND;
        }
        if (!isAvailable()) {
            Log.w(TAG, "SCPMv2 is not available.");
            return Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
        }
        if (this.mOpusOffloadEnabledList == null) {
            if (!initOpusOffloadEnabledList()) {
                Log.w(TAG, "Failed to init isInOpusOffloadEnabledList.");
                return Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
            }
            if (this.mOpusOffloadEnabledList == null) {
                Log.w(TAG, "enabledList is null.");
                return Utils.QueryResult.SERVER_IS_NOT_AVAILABLE;
            }
        }
        int i2 = 0;
        while (true) {
            try {
                if (i2 >= this.mOpusOffloadEnabledList.length()) {
                    break;
                }
                JSONObject jSONObject = this.mOpusOffloadEnabledList.getJSONObject(i2);
                String string = jSONObject.getString("model");
                int i3 = jSONObject.getInt(SCPMV2_KEY_VERSION);
                Log.i(TAG, "enabled_model, support_minimum_version :" + string + " " + i3);
                if (!str.contains(string)) {
                    i2++;
                } else {
                    if (i >= i3) {
                        Log.i(TAG, "this model is supported ");
                        return Utils.QueryResult.FOUND;
                    }
                    Log.i(TAG, "this model OPUS VERSION is smaller than supported version ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Utils.QueryResult.NOT_FOUND;
    }

    public boolean registerToScpm() {
        Log.d(TAG, "registerToScpm()");
        Bundle bundle = new Bundle();
        bundle.putString(SCPMV2_KEY_PACKAGE_NAME, CODEC_SOLUTION_PACKAGE_NAME);
        bundle.putString(SCPMV2_KEY_APP_ID, CODEC_SOLUTION_APP_ID);
        bundle.putString(SCPMV2_KEY_VERSION, CODEC_SOLUTION_VERSION_DUMMY);
        bundle.putString(SCPMV2_KEY_RECEIVER_PACKAGE_NAME, CODEC_SOLUTION_PACKAGE_NAME);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(
                                    SCPMV2_PROVIDER_URI,
                                    SCPMV2_METHOD_REGISTER,
                                    CODEC_SOLUTION_PACKAGE_NAME,
                                    bundle);
            if (call == null) {
                Log.w(TAG, "Bundle is null");
                return false;
            }
            int i = call.getInt(SCPMV2_KEY_RCODE, 1);
            String string = call.getString(SCPMV2_KEY_RMSG, "null");
            if (i == 2) {
                Log.w(TAG, "Failed to register : rcode[" + i + "], rmsg[" + string + "]");
                return false;
            }
            String string2 = call.getString(SCPMV2_KEY_TOKEN, null);
            if (string2 == null) {
                Log.w(TAG, "token is null");
                return false;
            }
            if (this.mToken != null) {
                Log.w(TAG, "token is already set. initialize...");
                this.mToken = null;
                return false;
            }
            Log.d(TAG, "token[" + string2 + "]");
            this.mToken = string2;
            return true;
        } catch (Exception e) {
            Log.w(TAG, "Exception : Failed to register : " + e.getMessage());
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setmOpusOffloadEnabledListToNull() {
        this.mOpusOffloadEnabledList = null;
    }
}
