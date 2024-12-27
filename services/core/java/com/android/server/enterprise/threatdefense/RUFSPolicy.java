package com.android.server.enterprise.threatdefense;

import android.util.Log;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public final class RUFSPolicy {
    public final String mPackageName;
    public final int mPolicyVersion;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RUFSPolicy(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "RUFSPolicy"
            r7.<init>()
            r7.mPackageName = r8
            java.lang.String r8 = "/data/system/.aasa/AASApolicy/mtdlist.output.txt"
            java.lang.String r1 = "/system/etc/mtdlist.output.txt"
            java.lang.String r2 = "Read system path="
            java.io.File r3 = new java.io.File     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r3.<init>(r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            java.io.File r4 = new java.io.File     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r4.<init>(r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            boolean r3 = r3.exists()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            java.lang.String r5 = "Signature verification failed"
            r6 = 0
            if (r3 == 0) goto L43
            java.lang.String r1 = readData(r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            com.android.server.enterprise.threatdefense.MTDSignature r3 = new com.android.server.enterprise.threatdefense.MTDSignature     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r3.<init>(r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            java.lang.String r1 = r3.getVerifiedData()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            if (r1 != 0) goto L32
            android.util.Log.e(r0, r5)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
        L32:
            if (r1 == 0) goto L43
            com.android.server.enterprise.threatdefense.RUFSParser r3 = new com.android.server.enterprise.threatdefense.RUFSParser     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r3.<init>(r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            goto L44
        L3a:
            r8 = move-exception
            goto La7
        L3d:
            r8 = move-exception
            goto Lad
        L40:
            r8 = move-exception
            goto Lb3
        L43:
            r3 = r6
        L44:
            boolean r1 = r4.exists()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            if (r1 == 0) goto L63
            java.lang.String r8 = readData(r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            com.android.server.enterprise.threatdefense.MTDSignature r1 = new com.android.server.enterprise.threatdefense.MTDSignature     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r1.<init>(r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            java.lang.String r8 = r1.getVerifiedData()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            if (r8 != 0) goto L5c
            android.util.Log.e(r0, r5)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
        L5c:
            if (r8 == 0) goto L63
            com.android.server.enterprise.threatdefense.RUFSParser r6 = new com.android.server.enterprise.threatdefense.RUFSParser     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r6.<init>(r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
        L63:
            r8 = 0
            if (r3 != 0) goto L6e
            if (r6 != 0) goto L6e
            java.lang.String r1 = "Default : No RUFS policy files"
            android.util.Log.i(r0, r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            goto Lb8
        L6e:
            if (r3 == 0) goto L8f
            r1 = 1
            if (r6 == 0) goto L89
            int r4 = r3.getPolicyVersion()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            int r5 = r6.getPolicyVersion()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            if (r4 < r5) goto L83
            java.lang.String r8 = "Using System parser"
            android.util.Log.i(r0, r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r8 = r1
        L83:
            java.lang.String r1 = "Using Data parser"
            android.util.Log.i(r0, r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            goto L8f
        L89:
            java.lang.String r8 = "System Parser Exists Only. "
            android.util.Log.i(r0, r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r8 = r1
        L8f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r1.<init>(r2)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            r1.append(r8)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            java.lang.String r1 = r1.toString()     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            android.util.Log.d(r0, r1)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            if (r8 == 0) goto La1
            goto La2
        La1:
            r3 = r6
        La2:
            int r8 = r7.readPolicyVersion(r3)     // Catch: java.lang.NumberFormatException -> L3a org.json.JSONException -> L3d java.lang.SecurityException -> L40
            goto Lb8
        La7:
            r8.printStackTrace()
            r8 = -104(0xffffffffffffff98, float:NaN)
            goto Lb8
        Lad:
            r8.printStackTrace()
            r8 = -107(0xffffffffffffff95, float:NaN)
            goto Lb8
        Lb3:
            r8.printStackTrace()
            r8 = -109(0xffffffffffffff93, float:NaN)
        Lb8:
            r7.mPolicyVersion = r8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "package="
            r1.<init>(r2)
            java.lang.String r7 = r7.mPackageName
            r1.append(r7)
            java.lang.String r7 = ", version="
            r1.append(r7)
            r1.append(r8)
            java.lang.String r7 = r1.toString()
            android.util.Log.i(r0, r7)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.threatdefense.RUFSPolicy.<init>(java.lang.String):void");
    }

    public static String readData(String e) {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader = null;
        bufferedReader = null;
        r3 = null;
        r3 = null;
        r3 = null;
        r3 = null;
        String str = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream((String) e);
                    try {
                        e =
                                new BufferedReader(
                                        new InputStreamReader(
                                                fileInputStream, StandardCharsets.UTF_8));
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        e = 0;
                    } catch (SecurityException e3) {
                        e = e3;
                        e = 0;
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e4) {
                                Log.e("RUFSPolicy", "IOException", e4);
                                throw th;
                            }
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e5) {
                    e = e5;
                    e = 0;
                    fileInputStream = null;
                } catch (SecurityException e6) {
                    e = e6;
                    e = 0;
                    fileInputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = null;
                }
                try {
                    String str2 =
                            (String) e.lines().collect(Collectors.joining(System.lineSeparator()));
                    try {
                        e.close();
                        fileInputStream.close();
                        e = e;
                    } catch (IOException e7) {
                        Log.e("RUFSPolicy", "IOException", e7);
                        e = e7;
                    }
                    str = str2;
                } catch (FileNotFoundException e8) {
                    e = e8;
                    Log.e("RUFSPolicy", "FileNotFoundException : " + e.getMessage());
                    if (e != 0) {
                        e.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return str;
                } catch (SecurityException e9) {
                    e = e9;
                    Log.e("RUFSPolicy", "SecurityException", e);
                    if (e != 0) {
                        e.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return str;
                }
            } catch (IOException e10) {
                e = e10;
                Log.e("RUFSPolicy", "IOException", e);
            }
            return str;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = e;
        }
    }

    public final int readPolicyVersion(RUFSParser rUFSParser) {
        int i;
        ArrayList exceptionList;
        String str;
        String str2;
        if (rUFSParser == null) {
            return -106;
        }
        try {
            try {
                Log.d("RUFSPolicy", "RUFSParser : " + rUFSParser.toString());
                exceptionList = rUFSParser.getExceptionList();
                str = rUFSParser.mVersion;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                i = -104;
            }
        } catch (SecurityException e2) {
            e2.printStackTrace();
            i = -109;
        }
        try {
            i = Integer.parseInt(str);
            Log.d("RUFSPolicy", "Parser version : " + i);
            if (exceptionList != null) {
                Iterator it = exceptionList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String str3 = (String) it.next();
                    Log.i("RUFSPolicy", "exception list : " + str3);
                    String[] split = str3.split(":");
                    if (split.length > 1
                            && (str2 = this.mPackageName) != null
                            && str2.equals(split[0])) {
                        i = Integer.parseInt(split[1]);
                        Log.d(
                                "RUFSPolicy",
                                "Exception package : "
                                        + split[0]
                                        + " version : "
                                        + split[1]
                                        + "=="
                                        + i);
                        break;
                    }
                }
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Parder ret : ", "RUFSPolicy");
            return i;
        } catch (NumberFormatException unused) {
            throw new NumberFormatException("RUFSParser, Invalid format : " + str);
        }
    }
}
