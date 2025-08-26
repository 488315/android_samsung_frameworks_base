package com.samsung.android.wifi;

import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemWifiApSmartWhiteList {
    private static final int BUFFER_SIZE = 64;
    public static final int WL_ALREADY_IN_TABLE = 4;
    public static final int WL_FAIL = 2;
    public static final int WL_NOT_IN_TABLE = 5;
    public static final int WL_NOT_MAC = 3;
    public static final int WL_SUCCESS = 1;
    private static Vector<SmartWhiteList> mSmartWhiteList;
    private static volatile SemWifiApSmartWhiteList uniqueInstance;
    private String TAG = "SemWifiApSmartWhiteList";
    private final String SMART_TETHERING_ACCEPT = "/data/misc/wifi_hostapd/smart_tethering.accept";

    public static class SmartWhiteList {
        private int mDeviceType;
        private String mMac;
        private String mName;

        SmartWhiteList(String str, String str2, int i) {
            this.mMac = str;
            this.mName = str2;
            this.mDeviceType = i;
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

        public int getDeviceType() {
            return this.mDeviceType;
        }
    }

    private SemWifiApSmartWhiteList() {
        Vector<SmartWhiteList> vector = new Vector<>(20);
        mSmartWhiteList = vector;
        synchronized (vector) {
            createOrChangePermission();
            readWhiteListFile();
        }
    }

    public static SemWifiApSmartWhiteList getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SemWifiApSmartWhiteList();
        }
        return uniqueInstance;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0034 -> B:17:0x0037). Please report as a decompilation issue!!! */
    private void createOrChangePermission() throws InterruptedException, IOException {
        File file = new File("/data/misc/wifi_hostapd/smart_tethering.accept");
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Process processExec = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "/system/bin/chmod 665 /data/misc/wifi_hostapd/smart_tethering.accept"});
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x005e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0081 -> B:48:0x0084). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readWhiteListFile() throws Throwable {
        String line;
        int iIntValue;
        mSmartWhiteList.clear();
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream("/data/misc/wifi_hostapd/smart_tethering.accept"), StandardCharsets.UTF_8), 64);
                    while (true) {
                        try {
                            line = bufferedReader3.readLine();
                            if (line == null) {
                                break;
                            }
                            if (line.startsWith("#")) {
                                String strSubstring = line.substring(1);
                                String line2 = bufferedReader3.readLine();
                                if (bufferedReader3.markSupported()) {
                                    bufferedReader3.mark(50);
                                    String line3 = bufferedReader3.readLine();
                                    if (line3 != null) {
                                        if (!line3.startsWith("#")) {
                                            if (line3 instanceof String) {
                                                try {
                                                    iIntValue = Integer.decode(line3).intValue();
                                                } catch (NumberFormatException e) {
                                                    e.printStackTrace();
                                                }
                                                mSmartWhiteList.add(new SmartWhiteList(line2, strSubstring, iIntValue));
                                            }
                                        } else {
                                            bufferedReader3.reset();
                                        }
                                        iIntValue = 0;
                                        mSmartWhiteList.add(new SmartWhiteList(line2, strSubstring, iIntValue));
                                    } else {
                                        iIntValue = 0;
                                        mSmartWhiteList.add(new SmartWhiteList(line2, strSubstring, iIntValue));
                                    }
                                }
                            }
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader2 = bufferedReader3;
                            e.printStackTrace();
                            bufferedReader = bufferedReader2;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                                bufferedReader = bufferedReader2;
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader3;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader3.close();
                    bufferedReader = line;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            bufferedReader = bufferedReader;
        }
    }

    private void writeWhiteListFile() throws Throwable {
        OutputStreamWriter outputStreamWriter;
        OutputStreamWriter outputStreamWriter2 = null;
        try {
            try {
                try {
                    outputStreamWriter = new OutputStreamWriter(new FileOutputStream("/data/misc/wifi_hostapd/smart_tethering.accept"), StandardCharsets.UTF_8);
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
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList next = it.next();
                outputStreamWriter.write("#");
                if (next.getName() != null) {
                    outputStreamWriter.write(next.getName());
                }
                outputStreamWriter.write(ShaderAssembler.NEWLINE);
                outputStreamWriter.write(next.getMac());
                outputStreamWriter.write(ShaderAssembler.NEWLINE);
                outputStreamWriter.write(Integer.toString(next.mDeviceType));
                outputStreamWriter.write(ShaderAssembler.NEWLINE);
            }
            outputStreamWriter.close();
        } catch (IOException e3) {
            e = e3;
            outputStreamWriter2 = outputStreamWriter;
            e.printStackTrace();
            if (outputStreamWriter2 != null) {
                outputStreamWriter2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            outputStreamWriter2 = outputStreamWriter;
            if (outputStreamWriter2 != null) {
                try {
                    outputStreamWriter2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public int addWhiteList(String str, String str2, int i) {
        synchronized (mSmartWhiteList) {
            if (!isMacAddress(str)) {
                return 3;
            }
            readWhiteListFile();
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                if (it.next().getMac().equalsIgnoreCase(str)) {
                    return 4;
                }
            }
            if (i == 1) {
                mSmartWhiteList.add(new SmartWhiteList(str, str2, 1));
            } else {
                mSmartWhiteList.add(new SmartWhiteList(str, str2, 0));
            }
            Log.i(this.TAG, "addWhiteList, size is " + mSmartWhiteList.size());
            writeWhiteListFile();
            return 1;
        }
    }

    public int removeWhiteList(String str) {
        synchronized (mSmartWhiteList) {
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList next = it.next();
                if (next.getMac().equalsIgnoreCase(str)) {
                    Log.i(this.TAG, "removeWhiteList::" + str.substring(9));
                    mSmartWhiteList.remove(next);
                    writeWhiteListFile();
                    return 1;
                }
            }
            return 2;
        }
    }

    public void resetWhitelist() {
        synchronized (mSmartWhiteList) {
            readWhiteListFile();
            mSmartWhiteList.clear();
            Log.e(this.TAG, "resetWhitelist");
            writeWhiteListFile();
        }
    }

    public int modifyWhiteList(String str, String str2) {
        synchronized (mSmartWhiteList) {
            readWhiteListFile();
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList next = it.next();
                if (next.getMac().equalsIgnoreCase(str)) {
                    next.setName(str2);
                    writeWhiteListFile();
                    return 1;
                }
            }
            return 2;
        }
    }

    public String getDeviceName(String str) {
        synchronized (mSmartWhiteList) {
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList next = it.next();
                if (next.getMac().equalsIgnoreCase(str)) {
                    return next.getName();
                }
            }
            return "";
        }
    }

    public boolean isContains(String str) {
        synchronized (mSmartWhiteList) {
            readWhiteListFile();
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                if (it.next().getMac().equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public Iterator<SmartWhiteList> getIterator() {
        synchronized (mSmartWhiteList) {
            readWhiteListFile();
            if (mSmartWhiteList.isEmpty()) {
                return null;
            }
            return mSmartWhiteList.iterator();
        }
    }

    public int getSize() {
        int size;
        synchronized (mSmartWhiteList) {
            size = mSmartWhiteList.size();
        }
        return size;
    }

    private boolean isMacAddress(String str) {
        return Pattern.compile("[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0 -9a-fA-F]{2}[-:][0-9a-fA-F]{2}").matcher(str).matches();
    }
}
