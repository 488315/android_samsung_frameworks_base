package com.samsung.android.wifi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        WhiteList(String mac, String name, boolean enable) {
            this.mMac = mac;
            this.mName = name;
            this.mEnable = enable;
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

        public void setEnable(boolean enable) {
            this.mEnable = enable;
        }

        public boolean getEnable() {
            return this.mEnable;
        }
    }

    private SemWifiApWhiteList() {
        createOrChangePermission();
        readWhiteListFile();
    }

    public static SemWifiApWhiteList getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SemWifiApWhiteList();
        }
        return uniqueInstance;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0034 -> B:11:0x0037). Please report as a decompilation issue!!! */
    private void createOrChangePermission() {
        File file = new File("/data/misc/wifi_hostapd/hostapd.accept");
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
                    "/system/bin/chmod 665 /data/misc/wifi_hostapd/hostapd.accept"
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
        this.mWhiteList.clear();
        BufferedReader buf = null;
        try {
            try {
                buf =
                        new BufferedReader(
                                new FileReader(
                                        "/data/misc/wifi_hostapd/hostapd.accept",
                                        StandardCharsets.UTF_8),
                                64);
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
                    if (bufReadLine.startsWith("#")) {
                        boolean z = true;
                        String name = bufReadLine.substring(1);
                        String mac = buf.readLine();
                        String strenable = buf.readLine();
                        if (strenable != "1") {
                            z = false;
                        }
                        boolean enable = z;
                        this.mWhiteList.add(new WhiteList(mac, name, enable));
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (buf != null) {
                    try {
                        buf.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        } catch (Throwable th) {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    private void writeWhiteListFile() {
        FileWriter fw = null;
        try {
            try {
                try {
                    fw =
                            new FileWriter(
                                    "/data/misc/wifi_hostapd/hostapd.accept",
                                    StandardCharsets.UTF_8);
                    Iterator<WhiteList> it = this.mWhiteList.iterator();
                    while (it.hasNext()) {
                        WhiteList wl = it.next();
                        fw.write("#");
                        if (wl.getName() != null) {
                            fw.write(wl.getName());
                        }
                        fw.write("\n");
                        fw.write(wl.getMac());
                        fw.write("\n");
                        fw.write(wl.getEnable() ? "1" : "0");
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

    public int addWhiteList(String mac, String name, boolean enable) {
        if (!isMacAddress(mac)) {
            return 3;
        }
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            if (it.next().getMac().equalsIgnoreCase(mac)) {
                return 4;
            }
        }
        Log.d(this.TAG, "addWhiteList::" + mac + ":," + name + ":" + enable);
        this.mWhiteList.add(new WhiteList(mac, name, enable));
        writeWhiteListFile();
        return 1;
    }

    public int removeWhiteList(String mac) {
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList wl = it.next();
            if (wl.getMac().equalsIgnoreCase(mac)) {
                Log.d(this.TAG, "removeWhiteList::" + mac);
                boolean oldenable = wl.getEnable();
                this.mWhiteList.remove(wl);
                writeWhiteListFile();
                if (oldenable) {
                    return 6;
                }
                return 1;
            }
        }
        return 2;
    }

    public int modifyWhiteList(String mac, String name, boolean enable) {
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList wl = it.next();
            if (wl.getMac().equalsIgnoreCase(mac)) {
                wl.setName(name);
                boolean oldenable = wl.getEnable();
                wl.setEnable(enable);
                writeWhiteListFile();
                if (oldenable != enable) {
                    return 6;
                }
                return 1;
            }
        }
        return 2;
    }

    public String getDeviceName(String mac) {
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList wl = it.next();
            if (wl.getMac().equalsIgnoreCase(mac)) {
                return wl.getName();
            }
        }
        return "";
    }

    public boolean isContains(String mac) {
        Log.d(this.TAG, "isContains::" + mac);
        Iterator<WhiteList> it = this.mWhiteList.iterator();
        while (it.hasNext()) {
            WhiteList wl = it.next();
            if (wl.getMac().equalsIgnoreCase(mac)) {
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

    private boolean isMacAddress(String macAddressCandidate) {
        Pattern macPattern =
                Pattern.compile(
                        "[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0"
                            + " -9a-fA-F]{2}[-:][0-9a-fA-F]{2}");
        Matcher m = macPattern.matcher(macAddressCandidate);
        return m.matches();
    }
}
