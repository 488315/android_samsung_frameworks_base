package com.android.server.enterprise.threatdefense;

import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class RUFSPolicy {
    public static final String TAG = "RUFSPolicy";
    public String mPackageName;
    public int mPolicyVersion;

    public RUFSPolicy(String str) {
        this.mPackageName = str;
        int latestPolicy = getLatestPolicy();
        this.mPolicyVersion = latestPolicy;
        Log.i(TAG, "package=" + this.mPackageName + ", version=" + latestPolicy);
    }

    public int getPolicyVersion() {
        return this.mPolicyVersion;
    }

    public final int getLatestPolicy() {
        String readDecodedData;
        String readDecodedData2;
        try {
            File file = new File("/system/etc/mtdlist.output.txt");
            File file2 = new File("/data/system/.aasa/AASApolicy/mtdlist.output.txt");
            RUFSParser rUFSParser = null;
            RUFSParser rUFSParser2 = (!file.exists() || (readDecodedData2 = readDecodedData(readData("/system/etc/mtdlist.output.txt"))) == null) ? null : new RUFSParser(readDecodedData2);
            if (file2.exists() && (readDecodedData = readDecodedData(readData("/data/system/.aasa/AASApolicy/mtdlist.output.txt"))) != null) {
                rUFSParser = new RUFSParser(readDecodedData);
            }
            boolean z = false;
            if (rUFSParser2 == null && rUFSParser == null) {
                Log.i(TAG, "Default : No RUFS policy files");
                return 0;
            }
            if (rUFSParser2 != null) {
                if (rUFSParser != null) {
                    if (rUFSParser2.getPolicyVersion() >= rUFSParser.getPolicyVersion()) {
                        Log.i(TAG, "Using System parser");
                        z = true;
                    }
                    Log.i(TAG, "Using Data parser");
                } else {
                    Log.i(TAG, "System Parser Exists Only. ");
                    z = true;
                }
            }
            Log.d(TAG, "Read system path=" + z);
            if (!z) {
                rUFSParser2 = rUFSParser;
            }
            return readPolicyVersion(rUFSParser2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -104;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return -109;
        } catch (JSONException e3) {
            e3.printStackTrace();
            return -107;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x007a, code lost:
    
        r8 = java.lang.Integer.parseInt(r1[1]);
        android.util.Log.d(r2, "Exception package : " + r1[0] + " version : " + r1[1] + "==" + r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int readPolicyVersion(RUFSParser rUFSParser) {
        int i;
        String str;
        if (rUFSParser == null) {
            return -106;
        }
        try {
            String str2 = TAG;
            Log.d(str2, "RUFSParser : " + rUFSParser.toString());
            ArrayList exceptionList = rUFSParser.getExceptionList();
            i = rUFSParser.getVersion();
            Log.d(str2, "Parser version : " + i);
            if (exceptionList != null) {
                Iterator it = exceptionList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String str3 = (String) it.next();
                    String str4 = TAG;
                    Log.i(str4, "exception list : " + str3);
                    String[] split = str3.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                    if (split.length > 1 && (str = this.mPackageName) != null && str.equals(split[0])) {
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            i = -104;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            i = -109;
        }
        Log.d(TAG, "Parder ret : " + i);
        return i;
    }

    public final String readDecodedData(String str) {
        String verifiedData = new MTDSignature(str).getVerifiedData();
        if (verifiedData == null) {
            Log.e(TAG, "Signature verification failed");
        }
        return verifiedData;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r8v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final String readData(String e) {
        ?? r1;
        String str = null;
        try {
            try {
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e2) {
            e = e2;
            r1 = TAG;
            Log.e(r1, "IOException", e);
        }
        if (e == 0) {
            return null;
        }
        try {
            r1 = new FileInputStream((String) e);
            try {
                e = new BufferedReader(new InputStreamReader((InputStream) r1, StandardCharsets.UTF_8));
                try {
                    String str2 = (String) e.lines().collect(Collectors.joining(System.lineSeparator()));
                    try {
                        e.close();
                        r1.close();
                        e = e;
                    } catch (IOException e3) {
                        Log.e(TAG, "IOException", e3);
                        e = e3;
                    }
                    str = str2;
                } catch (FileNotFoundException e4) {
                    e = e4;
                    Log.e(TAG, "FileNotFoundException : " + e.getMessage());
                    if (e != 0) {
                        e.close();
                    }
                    if (r1 != 0) {
                        r1.close();
                    }
                    return str;
                } catch (SecurityException e5) {
                    e = e5;
                    Log.e(TAG, "SecurityException", e);
                    if (e != 0) {
                        e.close();
                    }
                    if (r1 != 0) {
                        r1.close();
                    }
                    return str;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                e = 0;
            } catch (SecurityException e7) {
                e = e7;
                e = 0;
            } catch (Throwable th2) {
                th = th2;
                e = 0;
                if (e != 0) {
                    try {
                        e.close();
                    } catch (IOException e8) {
                        Log.e(TAG, "IOException", e8);
                        throw th;
                    }
                }
                if (r1 != 0) {
                    r1.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e9) {
            e = e9;
            e = 0;
            r1 = 0;
        } catch (SecurityException e10) {
            e = e10;
            e = 0;
            r1 = 0;
        } catch (Throwable th3) {
            r1 = 0;
            th = th3;
            e = 0;
        }
        return str;
    }
}
