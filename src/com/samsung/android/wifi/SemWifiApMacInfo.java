package com.samsung.android.wifi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemWifiApMacInfo {
    private static final int BUFFER_SIZE = 64;
    private static final String WIFI_MAC_INFO = "/data/misc/wifi_hostapd/wifimac.info";
    public static final int WL_FAIL = 2;
    public static final int WL_SUCCESS = 1;
    private static volatile SemWifiApMacInfo uniqueInstance;
    private String TAG = "SemWifiApMacInfo";

    private SemWifiApMacInfo() {
        createOrChangePermission();
    }

    public static SemWifiApMacInfo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SemWifiApMacInfo();
        }
        return uniqueInstance;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0034 -> B:11:0x0037). Please report as a decompilation issue!!! */
    private void createOrChangePermission() {
        File file = new File(WIFI_MAC_INFO);
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "/system/bin/chmod 665 /data/misc/wifi_hostapd/wifimac.info"});
            try {
                exec.waitFor();
                exec.destroy();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String readWifiMacInfo() {
        /*
            r4 = this;
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            java.lang.String r2 = "/data/misc/wifi_hostapd/wifimac.info"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            r3 = 64
            r1.<init>(r2, r3)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L27
            java.lang.String r2 = r1.readLine()     // Catch: java.io.IOException -> L23 java.lang.Throwable -> L53
            r1.close()     // Catch: java.io.IOException -> L1e
            goto L37
        L1e:
            r1 = move-exception
            r1.printStackTrace()
            goto L37
        L23:
            r2 = move-exception
            goto L29
        L25:
            r4 = move-exception
            goto L55
        L27:
            r2 = move-exception
            r1 = r0
        L29:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L53
            if (r1 == 0) goto L36
            r1.close()     // Catch: java.io.IOException -> L32
            goto L36
        L32:
            r1 = move-exception
            r1.printStackTrace()
        L36:
            r2 = r0
        L37:
            java.lang.String r4 = r4.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "JDM MAC"
            r1.<init>(r3)
            if (r2 == 0) goto L48
            r0 = 9
            java.lang.String r0 = r2.substring(r0)
        L48:
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.d(r4, r0)
            return r2
        L53:
            r4 = move-exception
            r0 = r1
        L55:
            if (r0 == 0) goto L5f
            r0.close()     // Catch: java.io.IOException -> L5b
            goto L5f
        L5b:
            r0 = move-exception
            r0.printStackTrace()
        L5f:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wifi.SemWifiApMacInfo.readWifiMacInfo():java.lang.String");
    }

    public void writeWifiMacInfo(String str) {
        OutputStreamWriter outputStreamWriter;
        if (str != null && !str.isEmpty() && isMacAddress(str) && str.length() >= 17) {
            if (str.length() == 17 && str.substring(9).equals("00:00:00")) {
                return;
            }
            synchronized (this) {
                OutputStreamWriter outputStreamWriter2 = null;
                try {
                    try {
                        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(WIFI_MAC_INFO), StandardCharsets.UTF_8);
                        try {
                            outputStreamWriter.write(str);
                        } catch (IOException e) {
                            e = e;
                            outputStreamWriter2 = outputStreamWriter;
                            e.printStackTrace();
                            if (outputStreamWriter2 != null) {
                                try {
                                    outputStreamWriter2.close();
                                } catch (IOException e2) {
                                    e = e2;
                                    e.printStackTrace();
                                }
                            }
                        } catch (Throwable th) {
                            th = th;
                            outputStreamWriter2 = outputStreamWriter;
                            if (outputStreamWriter2 != null) {
                                try {
                                    outputStreamWriter2.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e4) {
                        e = e4;
                    }
                    try {
                        outputStreamWriter.close();
                    } catch (IOException e5) {
                        e = e5;
                        e.printStackTrace();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
    }

    private boolean isMacAddress(String str) {
        return Pattern.compile("[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0 -9a-fA-F]{2}[-:][0-9a-fA-F]{2}").matcher(str).matches();
    }
}
