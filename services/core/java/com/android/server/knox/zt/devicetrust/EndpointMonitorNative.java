package com.android.server.knox.zt.devicetrust;

import android.util.Log;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class EndpointMonitorNative {
    public static final String TAG = "EndpointMonitorNative";

    private native ArrayList nativeReadAbnormalPktData();

    private native ArrayList nativeReadExecveData();

    private native ArrayList nativeReadFsData();

    private native ArrayList nativeReadInsecurePortsData();

    private native ArrayList nativeReadLocalNwPktData();

    private native ArrayList nativeReadPktData();

    private native ArrayList nativeReadPrivEscalData();

    private native ArrayList nativeReadProcData();

    private native ArrayList nativeReadScData();

    private native ArrayList nativeReadSkData();

    private native ArrayList nativeReadSockData();

    private native int nativeSetBpfHelper(OemNetdAdapter oemNetdAdapter);

    private native int nativeSetOffsets();

    private native int nativeSetTargetFiles(ArrayList arrayList, ArrayList arrayList2);

    private native int nativeSetTracer(int i);

    private native int nativeStartAbnormalPktLogging();

    private native int nativeStartDpTracing();

    private native int nativeStartInsecurePortsLogging();

    private native int nativeStartLocalNwPktLogging();

    private native int nativeStartTracing(int i);

    private native int nativeStopTracing(int i);

    private native int nativeUpdateNetworkInterfaceData(int i, int i2);

    public final ArrayList readExecveData() {
        try {
            return nativeReadExecveData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readExecveData: " + e);
            return null;
        }
    }

    public final ArrayList readNetworkEventData(int i) {
        ArrayList arrayList = null;
        try {
            switch (i) {
                case 14:
                    arrayList = nativeReadInsecurePortsData();
                    break;
                case 15:
                    arrayList = nativeReadAbnormalPktData();
                    break;
                case 16:
                    arrayList = nativeReadLocalNwPktData();
                    break;
            }
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readNetworkEventData: " + e);
        }
        return arrayList;
    }

    public final ArrayList readPktData() {
        try {
            return nativeReadPktData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readPktData: " + e);
            return null;
        }
    }

    public final ArrayList readPrivEscalData() {
        try {
            return nativeReadPrivEscalData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readPrivEscalData: " + e);
            return null;
        }
    }

    public final ArrayList readProcData() {
        try {
            return nativeReadProcData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readProcData: " + e);
            return null;
        }
    }

    public final ArrayList readScData() {
        try {
            return nativeReadScData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readScData: " + e);
            return null;
        }
    }

    public final ArrayList readSkData() {
        try {
            return nativeReadSkData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readSkData: " + e);
            return null;
        }
    }

    public final ArrayList readSockData() {
        try {
            return nativeReadSockData();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "readSockData: " + e);
            return null;
        }
    }

    public final int setOffsets() {
        try {
            return nativeSetOffsets();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "setOffsets: " + e);
            return -1;
        }
    }

    public final int startNetworkEventLogging(int i) {
        int i2 = -1;
        try {
            switch (i) {
                case 14:
                    i2 = nativeStartInsecurePortsLogging();
                    break;
                case 15:
                    i2 = nativeStartAbnormalPktLogging();
                    break;
                case 16:
                    i2 = nativeStartLocalNwPktLogging();
                    break;
            }
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "startNetworkEventLogging: " + e);
        }
        return i2;
    }

    public final int startTracing(int i) {
        try {
            return nativeStartTracing(i);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "startTracing: " + e);
            return -1;
        }
    }

    public final int stopTracing(int i) {
        try {
            return nativeStopTracing(i);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "stopTracing: " + e);
            return -1;
        }
    }

    public final int updateNetworkInterfaceData(int i, int i2) {
        try {
            return nativeUpdateNetworkInterfaceData(i, i2);
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "nativeUpdateNetworkInterfaceData: " + e);
            return -1;
        }
    }
}
