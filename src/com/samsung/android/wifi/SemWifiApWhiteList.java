package com.samsung.android.wifi;

import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemWifiApWhiteList {
    public static final int WL_ALREADY_IN_TABLE = 4;
    public static final int WL_DENY_SUCCESS = 6;
    public static final int WL_FAIL = 2;
    public static final int WL_NOT_IN_TABLE = 5;
    public static final int WL_NOT_MAC = 3;
    public static final int WL_SUCCESS = 1;
    private static volatile SemWifiApWhiteList uniqueInstance;
    private String TAG = "SemWifiApWhiteList";
    private final String HOSTAPD_DENY = "/data/misc/wifi_hostapd/hostapd.accept";
    private final int BUFFER_SIZE = 64;
    private Vector<WhiteList> mWhiteList = new Vector<>();

    public static class WhiteList {
        private boolean mEnable;
        private String mMac;
        private String mName;

        WhiteList(String str, String str2, boolean z) {
            this.mMac = str;
            this.mName = str2;
            this.mEnable = z;
        }

        public void setName(String str) {
            this.mName = str;
        }

        public String getMac() {
            return this.mMac;
        }

        public String getName() {
            return this.mName;
        }

        public void setEnable(boolean z) {
            this.mEnable = z;
        }

        public boolean getEnable() {
            return this.mEnable;
        }
    }

    private SemWifiApWhiteList() throws Throwable {
        createOrChangePermission();
        readWhiteListFile();
    }

    public static SemWifiApWhiteList getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SemWifiApWhiteList();
        }
        return uniqueInstance;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0034 -> B:17:0x0037). Please report as a decompilation issue!!! */
    private void createOrChangePermission() throws InterruptedException, IOException {
        File file = new File("/data/misc/wifi_hostapd/hostapd.accept");
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Process processExec = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "/system/bin/chmod 665 /data/misc/wifi_hostapd/hostapd.accept"});
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

    private void readWhiteListFile() throws Throwable {
        this.mWhiteList.clear();
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/misc/wifi_hostapd/hostapd.accept", StandardCharsets.UTF_8), 64);
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line != null) {
                                if (line.startsWith("#")) {
                                    boolean z = true;
                                    String strSubstring = line.substring(1);
                                    String line2 = bufferedReader2.readLine();
                                    if (bufferedReader2.readLine() != "1") {
                                        z = false;
                                    }
                                    this.mWhiteList.add(new WhiteList(line2, strSubstring, z));
                                }
                            } else {
                                bufferedReader2.close();
                                return;
                            }
                        } catch (IOException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void writeWhiteListFile() throws Throwable {
        FileWriter fileWriter;
        FileWriter fileWriter2 = null;
        try {
            try {
                try {
                    fileWriter = new FileWriter("/data/misc/wifi_hostapd/hostapd.accept", StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            Iterator<WhiteList> it = this.mWhiteList.iterator();
            while (it.hasNext()) {
                WhiteList next = it.next();
                fileWriter.write("#");
                if (next.getName() != null) {
                    fileWriter.write(next.getName());
                }
                fileWriter.write(ShaderAssembler.NEWLINE);
                fileWriter.write(next.getMac());
                fileWriter.write(ShaderAssembler.NEWLINE);
                fileWriter.write(next.getEnable() ? "1" : "0");
                fileWriter.write(ShaderAssembler.NEWLINE);
            }
            fileWriter.close();
        } catch (IOException e3) {
            e = e3;
            fileWriter2 = fileWriter;
            e.printStackTrace();
            if (fileWriter2 != null) {
                fileWriter2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            fileWriter2 = fileWriter;
            if (fileWriter2 != null) {
                try {
                    fileWriter2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public int addWhiteList(String str, String str2, boolean z) throws Throwable {
        if (!isMacAddress(str)) {
            return 3;
        }
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            if (it.next().getMac().equalsIgnoreCase(str)) {
                return 4;
            }
        }
        Log.d(this.TAG, "addWhiteList::" + str + ":," + str2 + ":" + z);
        this.mWhiteList.add(new WhiteList(str, str2, z));
        writeWhiteListFile();
        return 1;
    }

    public int removeWhiteList(String str) throws Throwable {
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList next = it.next();
            if (next.getMac().equalsIgnoreCase(str)) {
                Log.d(this.TAG, "removeWhiteList::" + str);
                boolean enable = next.getEnable();
                this.mWhiteList.remove(next);
                writeWhiteListFile();
                return enable ? 6 : 1;
            }
        }
        return 2;
    }

    public int modifyWhiteList(String str, String str2, boolean z) throws Throwable {
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList next = it.next();
            if (next.getMac().equalsIgnoreCase(str)) {
                next.setName(str2);
                boolean enable = next.getEnable();
                next.setEnable(z);
                writeWhiteListFile();
                return enable != z ? 6 : 1;
            }
        }
        return 2;
    }

    public String getDeviceName(String str) {
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList next = it.next();
            if (next.getMac().equalsIgnoreCase(str)) {
                return next.getName();
            }
        }
        return "";
    }

    public boolean isContains(String str) {
        Log.d(this.TAG, "isContains::" + str);
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            if (it.next().getMac().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<WhiteList> getIterator() {
        if (this.mWhiteList.isEmpty()) {
            return null;
        }
        return this.mWhiteList.iterator();
    }

    public int getSize() {
        return this.mWhiteList.size();
    }

    private boolean isMacAddress(String str) {
        return Pattern.compile("[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0 -9a-fA-F]{2}[-:][0-9a-fA-F]{2}").matcher(str).matches();
    }
}
