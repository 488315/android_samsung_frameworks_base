package com.samsung.android.wifi;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private SemWifiApMacInfo() throws InterruptedException, IOException {
        createOrChangePermission();
    }

    public static SemWifiApMacInfo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SemWifiApMacInfo();
        }
        return uniqueInstance;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0034 -> B:17:0x0037). Please report as a decompilation issue!!! */
    private void createOrChangePermission() throws InterruptedException, IOException {
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
            Process processExec = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "/system/bin/chmod 665 /data/misc/wifi_hostapd/wifimac.info"});
            try {
                processExec.waitFor();
                processExec.destroy();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String readWifiMacInfo() throws Throwable {
        BufferedReader bufferedReader;
        String line;
        StringBuilder sb;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(WIFI_MAC_INFO), StandardCharsets.UTF_8), 64);
                try {
                    line = bufferedReader.readLine();
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    line = null;
                    String str = this.TAG;
                    sb = new StringBuilder("JDM MAC");
                    sb.append(line != null ? line.substring(9) : null);
                    Log.d(str, sb.toString());
                    return line;
                }
            } catch (Throwable th) {
                th = th;
                bufferedReader2 = sb;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedReader2 != null) {
            }
            throw th;
        }
        String str2 = this.TAG;
        sb = new StringBuilder("JDM MAC");
        sb.append(line != null ? line.substring(9) : null);
        Log.d(str2, sb.toString());
        return line;
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
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (IOException e) {
                    e = e;
                }
                try {
                    outputStreamWriter.write(str);
                    try {
                        outputStreamWriter.close();
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                    }
                } catch (IOException e3) {
                    e = e3;
                    outputStreamWriter2 = outputStreamWriter;
                    e.printStackTrace();
                    if (outputStreamWriter2 != null) {
                        try {
                            outputStreamWriter2.close();
                        } catch (IOException e4) {
                            e = e4;
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStreamWriter2 = outputStreamWriter;
                    if (outputStreamWriter2 != null) {
                        try {
                            outputStreamWriter2.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                        throw th;
                    }
                    throw th;
                }
            }
        }
    }

    private boolean isMacAddress(String str) {
        return Pattern.compile("[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0 -9a-fA-F]{2}[-:][0-9a-fA-F]{2}").matcher(str).matches();
    }
}
