package com.samsung.android.hardware.secinputdev.hal;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

/* compiled from: SysinputHALFactory.java */
/* loaded from: classes.dex */
class SysinputHALEmpty implements SysinputHALInterface {
    private static final String FILE_PATH_TSP_CMD = "/sys/class/sec/tsp/cmd";
    private static final String FILE_PATH_TSP_CMD_LIST = "/sys/class/sec/tsp/cmd_list";
    private static final String FILE_PATH_TSP_SUPPORT_FEATURE =
            "/sys/class/sec/tsp/support_feature";
    private static final String TAG = "SysinputHALEmpty";

    public SysinputHALEmpty() {
        Log.i(TAG, "Empty Hal Instance");
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public float getVersion() {
        return 0.0f;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int setProperty(int devid, SemInputConstants.Property property, String mode) {
        if (devid != 1) {
            Log.e(
                    TAG,
                    "setProperty: "
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + " is not support");
            return -2;
        }
        if (property != SemInputConstants.Property.CMD) {
            Log.e(
                    TAG,
                    "setProperty("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + "): "
                            + property
                            + " is not support");
            return -5;
        }
        int ret = -7;
        try {
            FileWriter fw = new FileWriter(new File(FILE_PATH_TSP_CMD), StandardCharsets.UTF_8);
            try {
                BufferedWriter writer = new BufferedWriter(fw);
                try {
                    writer.write(mode);
                    writer.flush();
                    ret = 0;
                    Log.d(
                            TAG,
                            "setProperty("
                                    + SysinputHALInterface.Device.getDeviceFromInt(devid)
                                    + "): "
                                    + property
                                    + ", "
                                    + mode
                                    + " ret=0");
                    writer.close();
                    fw.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String getProperty(int devid, SemInputConstants.Property property) {
        String path;
        if (devid != 1) {
            Log.e(
                    TAG,
                    "getProperty: "
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + " is not support");
            return "NG";
        }
        if (property == SemInputConstants.Property.CMD_LIST) {
            path = FILE_PATH_TSP_CMD_LIST;
        } else if (property == SemInputConstants.Property.FEATURE) {
            path = FILE_PATH_TSP_SUPPORT_FEATURE;
        } else {
            Log.e(
                    TAG,
                    "getProperty("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + "): "
                            + property
                            + " is not support");
            return "NG";
        }
        StringBuffer buffer = new StringBuffer();
        try {
            FileReader fr = new FileReader(new File(path), StandardCharsets.UTF_8);
            try {
                BufferedReader reader = new BufferedReader(fr);
                while (true) {
                    try {
                        String result = reader.readLine();
                        if (result == null) {
                            break;
                        }
                        buffer.append(result + "\n");
                    } finally {
                    }
                }
                String result2 = buffer.toString().trim();
                if (result2.length() < 50) {
                    Log.d(
                            TAG,
                            "getProperty("
                                    + SysinputHALInterface.Device.getDeviceFromInt(devid)
                                    + "): "
                                    + property
                                    + ":"
                                    + result2);
                } else {
                    Log.d(
                            TAG,
                            "getProperty("
                                    + SysinputHALInterface.Device.getDeviceFromInt(devid)
                                    + "): "
                                    + property);
                }
                reader.close();
                fr.close();
                return result2;
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return "NG";
        }
    }
}
