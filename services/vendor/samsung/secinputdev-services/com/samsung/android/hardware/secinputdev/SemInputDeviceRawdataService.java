package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.SystemClock;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.device.SemInputDeviceFactory;
import com.samsung.android.hardware.secinputdev.device.Touch;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* loaded from: classes.dex */
public class SemInputDeviceRawdataService {
    private static final int RAWDATA_POSTFIX_SIZE = 1;
    private static final int RAWDATA_PREFIX_SIZE = 4;
    private final String TAG;
    private final Context context;
    private boolean supportRawService;
    private final Touch touch;
    private final RemoteCallbackList<ISemInputDeviceRemoteServiceCallback> callbackList =
            new RemoteCallbackList<>();
    private final Map<String, ISemInputDeviceRemoteServiceCallback> callbackClientList =
            new HashMap();
    private final AtomicIntegerArray enabledTypes = new AtomicIntegerArray(3);
    private final StringBuilder bootingDump = new StringBuilder();
    public int PHYS_CHANNEL_X = 0;
    public int PHYS_CHANNEL_Y = 0;
    public int RAWDATA_LENGTH = 0;
    public int RAWDATA_SIZE = 0;
    private SemInputMotionController motionController = null;
    private volatile boolean isScreenOn = true;
    private int readRawdataEnable = 0;
    private int pollCount = 0;

    public SemInputDeviceRawdataService(Context context, SysinputHALInterface hal, int devid) {
        this.supportRawService = false;
        this.context = context;
        this.TAG = "SemInputRawdataService" + devid;
        this.touch = SemInputDeviceFactory.getTouch(devid);
        if (this.touch == null) {
            String log = "Touch(" + devid + ") is not registered";
            Log.e(this.TAG, log);
            this.bootingDump.append("- " + log + "\n");
        } else if (Float.compare(hal.getVersion(), 1.3f) < 0) {
            String log2 = "not support hal v" + hal.getVersion();
            Log.e(this.TAG, log2);
            this.bootingDump.append("- " + log2 + "\n");
        } else {
            this.supportRawService = initPanelInformation();
            if (!this.supportRawService) {
                return;
            }
            Log.d(this.TAG, "done");
        }
    }

    public void setMotionController(SemInputMotionController mController) {
        this.motionController = mController;
    }

    public boolean isSupport() {
        return this.supportRawService;
    }

    public int getChannelX() {
        return this.PHYS_CHANNEL_X;
    }

    public int getChannelY() {
        return this.PHYS_CHANNEL_Y;
    }

    public int getRawdataLength() {
        return this.RAWDATA_LENGTH;
    }

    private boolean initPanelInformation() {
        int xNum = 0;
        int yNum = 0;
        int rawdataLength = 0;
        int retry = 2;
        while (retry > 0) {
            try {
                String temp = this.touch.runCommand("get_x_num");
                xNum = Integer.parseInt(temp);
                String temp2 = this.touch.runCommand("get_y_num");
                yNum = Integer.parseInt(temp2);
                String temp3 = this.touch.runCommand("rawdata_length");
                if (temp3.equals(SemInputDeviceManager.RESULT_STR_NA)) {
                    rawdataLength = 0;
                } else {
                    rawdataLength = Integer.parseInt(temp3);
                }
                if (xNum > 0 && yNum > 0 && rawdataLength >= 0) {
                    break;
                }
                throw new IllegalArgumentException(
                        "channel number should be positive "
                                + String.format(
                                        "(%d,%d,%d)",
                                        Integer.valueOf(xNum),
                                        Integer.valueOf(yNum),
                                        Integer.valueOf(rawdataLength)));
            } catch (Exception e) {
                Log.e(this.TAG, "initPanelInformation: e:" + e);
                retry--;
                if (retry == 0) {
                    Log.d(this.TAG, "initPanelInformation failed");
                    this.bootingDump.append("- initPanelInformation failed: " + e + "\n");
                    return false;
                }
            }
        }
        this.PHYS_CHANNEL_X = xNum;
        this.PHYS_CHANNEL_Y = yNum;
        this.RAWDATA_LENGTH = rawdataLength;
        if (this.RAWDATA_LENGTH > 0) {
            this.RAWDATA_SIZE = this.RAWDATA_LENGTH + 4 + 1;
        } else {
            this.RAWDATA_SIZE = (this.PHYS_CHANNEL_X * this.PHYS_CHANNEL_Y) + 4 + 1;
        }
        Log.d(
                this.TAG,
                "initPanelInformation: x: "
                        + this.PHYS_CHANNEL_X
                        + " y: "
                        + this.PHYS_CHANNEL_Y
                        + " RAWDATA_SIZE: "
                        + this.RAWDATA_SIZE);
        this.bootingDump.append(
                "- x: "
                        + this.PHYS_CHANNEL_X
                        + " y: "
                        + this.PHYS_CHANNEL_Y
                        + " length: "
                        + this.RAWDATA_LENGTH
                        + " RAWDATA_SIZE: "
                        + this.RAWDATA_SIZE
                        + "\n");
        return true;
    }

    public void destroy() {
        pauseService();
        this.readRawdataEnable = 0;
        if (this.motionController != null) {
            this.motionController.destroy();
        }
        Log.d(this.TAG, "destroy");
    }

    public void restartService() {
        if (!this.supportRawService || this.isScreenOn) {
            return;
        }
        if (this.readRawdataEnable != 0) {
            Log.i(this.TAG, "restartService");
            this.touch.streamRawdata(this.readRawdataEnable);
            if (this.motionController != null) {
                this.motionController.restart();
            }
        }
        this.isScreenOn = true;
    }

    public void pauseService() {
        if (!this.supportRawService || !this.isScreenOn) {
            return;
        }
        this.isScreenOn = false;
        if (this.readRawdataEnable != 0) {
            Log.i(this.TAG, "pauseService");
            if (this.motionController != null) {
                this.motionController.pause();
            }
            this.touch.streamRawdata(0);
        }
    }

    public synchronized boolean enableService(int enabledType, int enableBit) {
        if (!this.supportRawService) {
            Log.e(this.TAG, "enableService: RawdataService is not supported");
            return false;
        }
        if ((this.readRawdataEnable & enableBit) != 0) {
            Log.e(
                    this.TAG,
                    "enableService: same motion is already enabled: "
                            + String.format(
                                    "0x%X|0x%X",
                                    Integer.valueOf(this.readRawdataEnable),
                                    Integer.valueOf(enableBit)));
            return false;
        }
        Log.i(this.TAG, "enableService: " + enabledType);
        clientCountInc(enabledType);
        this.readRawdataEnable |= enableBit;
        if (this.isScreenOn) {
            int ret = this.touch.streamRawdata(this.readRawdataEnable);
            if (ret < 0) {
                Log.e(
                        this.TAG,
                        "enableService: failed to streamRawdata("
                                + this.readRawdataEnable
                                + ") ret:"
                                + ret);
                this.readRawdataEnable &= ~enableBit;
                clientCountDec(enabledType);
                return false;
            }
        } else {
            Log.i(this.TAG, "enableService: screen is off");
        }
        Log.d(
                this.TAG,
                "enableService: total:"
                        + String.format("0x%X", Integer.valueOf(this.readRawdataEnable))
                        + " callback:"
                        + this.enabledTypes.get(1)
                        + " listener:"
                        + this.enabledTypes.get(2));
        return true;
    }

    public synchronized boolean disableService(int enabledType, int enableBit) {
        if (!this.supportRawService) {
            Log.e(this.TAG, "disableService: RawdataService is not supported");
            return false;
        }
        if (this.readRawdataEnable == 0) {
            Log.e(this.TAG, "disableService: not enabled");
            return false;
        }
        Log.i(this.TAG, "disableService: " + enabledType);
        clientCountDec(enabledType);
        this.readRawdataEnable &= ~enableBit;
        this.touch.streamRawdata(this.readRawdataEnable);
        Log.d(
                this.TAG,
                "disableService: total:"
                        + String.format("0x%X", Integer.valueOf(this.readRawdataEnable))
                        + " callback:"
                        + this.enabledTypes.get(1)
                        + " listener:"
                        + this.enabledTypes.get(2));
        return true;
    }

    public void dump(PrintWriter pw) {
        pw.println("dumping " + this.TAG + (this.isScreenOn ? "" : " off"));
        pw.print(this.bootingDump.toString());
        if (!this.supportRawService) {
            return;
        }
        pw.println(
                "- total:"
                        + String.format("0x%X", Integer.valueOf(this.readRawdataEnable))
                        + " callback:"
                        + this.enabledTypes.get(1)
                        + " listener:"
                        + this.enabledTypes.get(2));
        if (this.enabledTypes.get(1) > 0) {
            pw.println("- registered callback client list");
            for (String client : this.callbackClientList.keySet()) {
                pw.println("  " + client);
            }
        }
        if (this.motionController != null) {
            pw.println("");
            this.motionController.dump(pw);
        }
    }

    public boolean registerCallback(IBinder binder, String client) {
        Log.d(this.TAG, "registerCallback: client=" + client);
        if (!this.supportRawService) {
            Log.e(this.TAG, "registerCallback: RawdataService is not supported");
            return false;
        }
        ISemInputDeviceRemoteServiceCallback callback =
                ISemInputDeviceRemoteServiceCallback.Stub.asInterface(binder);
        if (callback == null) {
            Log.e(this.TAG, "registerCallback: binder/callback is null");
            return false;
        }
        ISemInputDeviceRemoteServiceCallback findCallback = this.callbackClientList.get(client);
        if (findCallback != null) {
            Log.e(this.TAG, "registerCallback: already registered from " + client);
            synchronized (this.callbackList) {
                this.callbackList.unregister(findCallback);
            }
            this.callbackClientList.remove(client);
            Log.d(this.TAG, "registerCallback: replace callback");
        } else {
            enableService(1, 1);
        }
        synchronized (this.callbackList) {
            this.callbackList.register(callback);
        }
        this.callbackClientList.put(client, callback);
        Log.d(this.TAG, "registerCallback: done");
        return true;
    }

    public boolean unregisterCallback(IBinder binder, String client) {
        Log.d(this.TAG, "unregisterCallback: client=" + client);
        if (!this.supportRawService) {
            Log.e(this.TAG, "unregisterCallback: RawdataService is not supported");
            return false;
        }
        ISemInputDeviceRemoteServiceCallback callback =
                ISemInputDeviceRemoteServiceCallback.Stub.asInterface(binder);
        if (callback == null) {
            Log.e(this.TAG, "registerCallback: binder/callback is null");
            return false;
        }
        ISemInputDeviceRemoteServiceCallback findCallback = this.callbackClientList.get(client);
        if (findCallback != null) {
            this.callbackClientList.remove(client);
        } else {
            Log.e(this.TAG, "unregisterCallback: not registered from " + client);
        }
        synchronized (this.callbackList) {
            boolean ret = this.callbackList.unregister(callback);
            if (!ret) {
                return false;
            }
            Log.d(this.TAG, "unregisterCallback: done");
            disableService(1, 1);
            return true;
        }
    }

    public void onReportInformation(String data) {
        if (this.supportRawService
                && data.contains(SemInputDeviceManager.REPORT_INFO_HANDEDGE)
                && this.motionController != null) {
            this.motionController.deliveryInformation(data);
        }
    }

    public void onReportRawData(int pollCount, int[] list) {
        if (!this.supportRawService) {
            return;
        }
        this.pollCount = pollCount;
        deliveryRawdata(list);
        int[] extraList = new int[this.RAWDATA_SIZE];
        for (int ii = 1; ii < this.pollCount; ii++) {
            try {
                System.arraycopy(list, this.RAWDATA_SIZE * ii, extraList, 0, this.RAWDATA_SIZE);
                deliveryRawdata(extraList);
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(this.TAG, "onReportRawData", e);
            }
        }
    }

    private void deliveryRawdata(int[] data) {
        try {
            long currentTime = SystemClock.elapsedRealtimeNanos();
            if (this.motionController != null) {
                this.motionController.deliveryRawdata(data);
            }
            if (this.enabledTypes.get(1) > 0) {
                data[this.RAWDATA_SIZE - 1] = Long.valueOf(currentTime).intValue();
                synchronized (this.callbackList) {
                    int N = this.callbackList.beginBroadcast();
                    for (int ii = 0; ii < N; ii++) {
                        try {
                            this.callbackList.getBroadcastItem(ii).deliveryRawdata(data);
                        } catch (Exception e) {
                            SemInputDeviceManagerService.loggingException(
                                    this.TAG, "deliveryRawdata:broadcast", e);
                        }
                        this.callbackList.finishBroadcast();
                    }
                }
            }
        } catch (Exception e2) {
            SemInputDeviceManagerService.loggingException(this.TAG, "deliveryRawdata", e2);
        }
    }

    public void deliveryLastData(int[] croppedVideoClip, float result) {
        try {
            if (this.enabledTypes.get(1) > 0) {
                Log.d(this.TAG, "deliveryLastData: callback: croppedVideoClip result:" + result);
                synchronized (this.callbackList) {
                    int N = this.callbackList.beginBroadcast();
                    for (int ii = 0; ii < N; ii++) {
                        try {
                            this.callbackList
                                    .getBroadcastItem(ii)
                                    .deliveryLastData(croppedVideoClip, result);
                        } catch (Exception e) {
                            SemInputDeviceManagerService.loggingException(
                                    this.TAG, "deliveryLastData:broadcast", e);
                        }
                        this.callbackList.finishBroadcast();
                    }
                }
            }
        } catch (Exception e2) {
            SemInputDeviceManagerService.loggingException(this.TAG, "deliveryLastData", e2);
        }
    }

    private void clientCountInc(int type) {
        if (type > 0 && type < 3) {
            this.enabledTypes.incrementAndGet(type);
        }
    }

    private void clientCountDec(int type) {
        if (type > 0 && type < 3) {
            this.enabledTypes.decrementAndGet(type);
            if (this.enabledTypes.get(type) < 0) {
                this.enabledTypes.set(type, 0);
            }
        }
    }
}
