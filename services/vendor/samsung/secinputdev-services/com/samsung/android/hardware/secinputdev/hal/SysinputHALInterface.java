package com.samsung.android.hardware.secinputdev.hal;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputConstants;

import java.io.PrintWriter;
import java.util.ArrayList;

public interface SysinputHALInterface {
    public static final String RESULT_STR_NG = "NG";

    float getVersion();

    public enum Device {
        TSP(1),
        TSP_SUB(2),
        KEY(21),
        SPEN(11),
        KEYBOARD(31),
        TAAS(41);

        private int value;

        Device(int value) {
            this.value = value;
        }

        public int toInt() {
            return this.value;
        }

        public static Device getDeviceFromInt(int devid) {
            for (Device device : values()) {
                if (device.value == devid) {
                    return device;
                }
            }
            return null;
        }

        public String getName() {
            return super.toString();
        }

        @Override // java.lang.Enum
        public String toString() {
            return super.toString() + "(" + this.value + ")";
        }
    }

    default ArrayList<Integer> getDeviceList(boolean forceParse) {
        Log.e("SysinputHALInterface", "getDeviceList forceParse: not support");
        return new ArrayList<>();
    }

    default int registerCallback(SysinputHALCallback callback) {
        Log.e("SysinputHALInterface", "registerCallback(IBinder): not support");
        return -5;
    }

    default int streamRawdata(int devid, int mode) {
        Log.e("SysinputHALInterface", "streamRawdata: not support");
        return -5;
    }

    default int injectRawdata(int devid, int[] list, int size) {
        Log.e("SysinputHALInterface", "injectRawdata: not support");
        return -5;
    }

    default int activate(int devid, int enable, boolean isEarly) {
        Log.e("SysinputHALInterface", "activate: not support");
        return -5;
    }

    default String runCommand(int devid, String cmdname) {
        Log.e("SysinputHALInterface", "runCommand: not support");
        return "NG";
    }

    default int setProperty(int devid, SemInputConstants.Property property, String mode) {
        Log.e("SysinputHALInterface", "setProperty: not support");
        return -5;
    }

    default String getProperty(int devid, SemInputConstants.Property property) {
        Log.e("SysinputHALInterface", "getProperty: not support");
        return "NG";
    }

    default String getKeyState(int keycode) {
        Log.e("SysinputHALInterface", "getKeyState: not support");
        return "NG";
    }

    default String readTaas() {
        Log.e("SysinputHALInterface", "readTaas: not support");
        return "NG";
    }

    default int writeTaas(String wstr) {
        Log.e("SysinputHALInterface", "writeTaas: not support");
        return -5;
    }

    default void onReportInformation(int devid, String data) {
        Log.e("SysinputHALInterface", "onReportInformation: not support");
    }

    default void dumpEvents(PrintWriter pw) {}
}
