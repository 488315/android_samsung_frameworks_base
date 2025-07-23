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

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0034 -> B:11:0x0037). Please report as a decompilation issue!!! */
    private void createOrChangePermission() {
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
            Process exec = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "/system/bin/chmod 665 /data/misc/wifi_hostapd/smart_tethering.accept"});
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

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x0081 -> B:28:0x0084). Please report as a decompilation issue!!! */
    private void readWhiteListFile() {
        String readLine;
        int i;
        mSmartWhiteList.clear();
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        bufferedReader = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
            bufferedReader = bufferedReader;
        }
        try {
            try {
                BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream("/data/misc/wifi_hostapd/smart_tethering.accept"), StandardCharsets.UTF_8), 64);
                while (true) {
                    try {
                        readLine = bufferedReader3.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.startsWith("#")) {
                            String substring = readLine.substring(1);
                            String readLine2 = bufferedReader3.readLine();
                            if (bufferedReader3.markSupported()) {
                                bufferedReader3.mark(50);
                                String readLine3 = bufferedReader3.readLine();
                                if (readLine3 != null) {
                                    if (!readLine3.startsWith("#")) {
                                        if (readLine3 instanceof String) {
                                            try {
                                                i = Integer.decode(readLine3).intValue();
                                            } catch (NumberFormatException e2) {
                                                e2.printStackTrace();
                                            }
                                            mSmartWhiteList.add(new SmartWhiteList(readLine2, substring, i));
                                        }
                                    } else {
                                        bufferedReader3.reset();
                                    }
                                }
                            }
                            i = 0;
                            mSmartWhiteList.add(new SmartWhiteList(readLine2, substring, i));
                        }
                    } catch (IOException e3) {
                        e = e3;
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
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader3.close();
                bufferedReader = readLine;
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void writeWhiteListFile() {
        OutputStreamWriter outputStreamWriter = null;
        try {
            try {
                try {
                    OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream("/data/misc/wifi_hostapd/smart_tethering.accept"), StandardCharsets.UTF_8);
                    try {
                        Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
                        while (it.hasNext()) {
                            SmartWhiteList next = it.next();
                            outputStreamWriter2.write("#");
                            if (next.getName() != null) {
                                outputStreamWriter2.write(next.getName());
                            }
                            outputStreamWriter2.write(ShaderAssembler.NEWLINE);
                            outputStreamWriter2.write(next.getMac());
                            outputStreamWriter2.write(ShaderAssembler.NEWLINE);
                            outputStreamWriter2.write(Integer.toString(next.mDeviceType));
                            outputStreamWriter2.write(ShaderAssembler.NEWLINE);
                        }
                        outputStreamWriter2.close();
                    } catch (IOException e) {
                        e = e;
                        outputStreamWriter = outputStreamWriter2;
                        e.printStackTrace();
                        if (outputStreamWriter != null) {
                            outputStreamWriter.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        outputStreamWriter = outputStreamWriter2;
                        if (outputStreamWriter != null) {
                            try {
                                outputStreamWriter.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
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
