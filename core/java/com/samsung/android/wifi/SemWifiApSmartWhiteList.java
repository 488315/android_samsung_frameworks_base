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
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        SmartWhiteList(String mac, String name, int mDeviceType) {
            this.mDeviceType = 0;
            this.mMac = mac;
            this.mName = name;
            this.mDeviceType = mDeviceType;
        }

        public void setName(String name) {
            this.mName = name;
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
        mSmartWhiteList = new Vector<>(20);
        synchronized (mSmartWhiteList) {
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
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String[] cmd = {
                    "/system/bin/sh",
                    "-c",
                    "/system/bin/chmod 665 /data/misc/wifi_hostapd/smart_tethering.accept"
                };
                Process p = Runtime.getRuntime().exec(cmd);
                try {
                    p.waitFor();
                    p.destroy();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    private void readWhiteListFile() {
        mSmartWhiteList.clear();
        BufferedReader buf = null;
        try {
            try {
                FileInputStream fis =
                        new FileInputStream("/data/misc/wifi_hostapd/smart_tethering.accept");
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                buf = new BufferedReader(isr, 64);
                while (true) {
                    String bufReadLine = buf.readLine();
                    if (bufReadLine == null) {
                        try {
                            buf.close();
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    int devicetype = 0;
                    if (bufReadLine.startsWith("#")) {
                        String name = bufReadLine.substring(1);
                        String mac = buf.readLine();
                        if (buf.markSupported()) {
                            buf.mark(50);
                            String bufReadLine2 = buf.readLine();
                            if (bufReadLine2 != null) {
                                if (bufReadLine2.startsWith("#")) {
                                    buf.reset();
                                } else if (bufReadLine2 instanceof String) {
                                    try {
                                        devicetype = Integer.decode(bufReadLine2).intValue();
                                    } catch (NumberFormatException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }
                        mSmartWhiteList.add(new SmartWhiteList(mac, name, devicetype));
                    }
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                if (buf != null) {
                    try {
                        buf.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
            }
        } catch (Throwable th) {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    private void writeWhiteListFile() {
        OutputStreamWriter fw = null;
        try {
            try {
                try {
                    fw =
                            new OutputStreamWriter(
                                    new FileOutputStream(
                                            "/data/misc/wifi_hostapd/smart_tethering.accept"),
                                    StandardCharsets.UTF_8);
                    Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
                    while (it.hasNext()) {
                        SmartWhiteList wl = it.next();
                        fw.write("#");
                        if (wl.getName() != null) {
                            fw.write(wl.getName());
                        }
                        fw.write("\n");
                        fw.write(wl.getMac());
                        fw.write("\n");
                        fw.write(Integer.toString(wl.mDeviceType));
                        fw.write("\n");
                    }
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (fw != null) {
                        fw.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fw.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public int addWhiteList(String mac, String name, int devicetype) {
        synchronized (mSmartWhiteList) {
            if (!isMacAddress(mac)) {
                return 3;
            }
            readWhiteListFile();
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                if (it.next().getMac().equalsIgnoreCase(mac)) {
                    return 4;
                }
            }
            if (devicetype == 1) {
                mSmartWhiteList.add(new SmartWhiteList(mac, name, 1));
            } else {
                mSmartWhiteList.add(new SmartWhiteList(mac, name, 0));
            }
            Log.i(this.TAG, "addWhiteList, size is " + mSmartWhiteList.size());
            writeWhiteListFile();
            return 1;
        }
    }

    public int removeWhiteList(String mac) {
        synchronized (mSmartWhiteList) {
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList wl = it.next();
                if (wl.getMac().equalsIgnoreCase(mac)) {
                    Log.i(this.TAG, "removeWhiteList::" + mac.substring(9));
                    mSmartWhiteList.remove(wl);
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

    public int modifyWhiteList(String mac, String name) {
        synchronized (mSmartWhiteList) {
            readWhiteListFile();
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList wl = it.next();
                if (wl.getMac().equalsIgnoreCase(mac)) {
                    wl.setName(name);
                    writeWhiteListFile();
                    return 1;
                }
            }
            return 2;
        }
    }

    public String getDeviceName(String mac) {
        synchronized (mSmartWhiteList) {
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList wl = it.next();
                if (wl.getMac().equalsIgnoreCase(mac)) {
                    return wl.getName();
                }
            }
            return "";
        }
    }

    public boolean isContains(String mac) {
        synchronized (mSmartWhiteList) {
            readWhiteListFile();
            Iterator<SmartWhiteList> it = mSmartWhiteList.iterator();
            while (it.hasNext()) {
                SmartWhiteList wl = it.next();
                if (wl.getMac().equalsIgnoreCase(mac)) {
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

    private boolean isMacAddress(String macAddressCandidate) {
        Pattern macPattern =
                Pattern.compile(
                        "[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0"
                            + " -9a-fA-F]{2}[-:][0-9a-fA-F]{2}");
        Matcher m = macPattern.matcher(macAddressCandidate);
        return m.matches();
    }
}
