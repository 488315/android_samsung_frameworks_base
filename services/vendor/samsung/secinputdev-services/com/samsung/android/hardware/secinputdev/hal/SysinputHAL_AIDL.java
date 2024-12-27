package com.samsung.android.hardware.secinputdev.hal;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputConstants;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.SemInputDumpsysData;

import vendor.samsung.hardware.sysinput.ISehSysInputCallback;
import vendor.samsung.hardware.sysinput.ISehSysInputDev;
import vendor.samsung.hardware.sysinput.SehIntStringParcel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SysinputHAL_AIDL implements SysinputHALInterface {
    private static final String SEPARATOR_AT_SIGN = "@";
    private static final String SERVICE_NAME =
            "vendor.samsung.hardware.sysinput.ISehSysInputDev/default";
    private static final String TAG = "SysinputHAL_AIDL";
    private static final long TIMEOUT_SECONDS = 60;
    private final ConcurrentHashMap<String, String> resultMap = new ConcurrentHashMap<>();
    private final SemInputDumpsysData timeoutData = new SemInputDumpsysData(50);
    private final Lock lock = new ReentrantLock();
    private final Map<Integer, Condition> deviceConditions = new HashMap();
    private final HashSet<String> timeoutKeys = new HashSet<>();
    private ISehSysInputDev halService = null;
    private int halVersion = 0;
    private SysinputHALCallback callback = null;
    private IBinder aidlCallback =
            new ISehSysInputCallback
                    .Stub() { // from class:
                              // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_AIDL.1
                @Override // vendor.samsung.hardware.sysinput.ISehSysInputCallback
                public void onReportInformation(int inputType, String data) throws RemoteException {
                    int type =
                            SysinputHAL_AIDL.convertInputDeviceTypeToDevid(
                                    Integer.valueOf(inputType));
                    SysinputHAL_AIDL.this.callback.onReportInformationAidl(type, data);
                }

                @Override // vendor.samsung.hardware.sysinput.ISehSysInputCallback
                public void onReportRawData(int inputType, int count, int[] list)
                        throws RemoteException {
                    int type =
                            SysinputHAL_AIDL.convertInputDeviceTypeToDevid(
                                    Integer.valueOf(inputType));
                    SysinputHAL_AIDL.this.callback.onReportRawData(type, count, list);
                }

                @Override // vendor.samsung.hardware.sysinput.ISehSysInputCallback
                public int getInterfaceVersion() {
                    return 2;
                }

                @Override // vendor.samsung.hardware.sysinput.ISehSysInputCallback
                public String getInterfaceHash() {
                    return "ebc882a8076245906ae71306e8e0706f50e728ae";
                }
            };

    public SysinputHAL_AIDL() {
        if (getService() == null) {
            throw new NoSuchElementException();
        }
        this.deviceConditions.put(1, this.lock.newCondition());
        this.deviceConditions.put(2, this.lock.newCondition());
        this.deviceConditions.put(11, this.lock.newCondition());
        this.deviceConditions.put(31, this.lock.newCondition());
        this.deviceConditions.put(41, this.lock.newCondition());
    }

    private synchronized ISehSysInputDev getService() {
        if (this.halService == null) {
            try {
                this.halService =
                        ISehSysInputDev.Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
                if (this.halService == null) {
                    Log.w(TAG, "getService: halService is null");
                    return null;
                }
                Log.d(TAG, "getService: " + this.halService.toString());
                getVersion();
                HALDeathReceiver deathReceiver = new HALDeathReceiver();
                try {
                    if (this.halService != null) {
                        this.halService.asBinder().linkToDeath(deathReceiver, 0);
                        Log.i(TAG, "getService:linkToDeath");
                    }
                } catch (Exception e) {
                    setServiceNullAndRecovery();
                    SemInputDeviceManagerService.loggingException(TAG, "getService:linkToDeath", e);
                    return null;
                }
            } catch (Exception e2) {
                SemInputDeviceManagerService.loggingException(TAG, "getService", e2);
                return null;
            }
        }
        return this.halService;
    }

    private final class HALDeathReceiver implements IBinder.DeathRecipient {
        private HALDeathReceiver() {}

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.i(SysinputHAL_AIDL.TAG, "RIP HAL");
            SysinputHAL_AIDL.this.setServiceNullAndRecovery();
        }
    }

    protected void setServiceNullAndRecovery() {
        synchronized (this) {
            this.halService = null;
        }
        SemInputDeviceManagerService.registerCallbackForHalRecovery(100);
        this.lock.lock();
        try {
            for (Condition condition : this.deviceConditions.values()) {
                condition.signalAll();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public static int convertInputDeviceTypeToDevid(Integer type) {
        switch (type.intValue()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 20:
                return 11;
            case 30:
                return 21;
            case 40:
                return 31;
            case 50:
                return 41;
            default:
                return 0;
        }
    }

    public static int convertDevidToInputDeviceType(int devid) {
        switch (devid) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 11:
                return 20;
            case 21:
                return 30;
            case SemInputDeviceManager.DEVID_KEYBOARD /* 31 */:
                return 40;
            case SemInputDeviceManager.DEVID_TAAS /* 41 */:
                return 50;
            default:
                return 0;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public float getVersion() {
        if (this.halVersion == 0) {
            try {
                this.halVersion = this.halService.getInterfaceVersion();
                Log.i(TAG, "getVersion: interface=2, hal=" + this.halVersion);
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "getVersion", e);
            }
        }
        return this.halVersion;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public ArrayList<Integer> getDeviceList(boolean forceParse) {
        ISehSysInputDev hal;
        int[] array;
        ArrayList<Integer> convertList = new ArrayList<>();
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "getDeviceList", e);
        }
        if (hal == null) {
            return convertList;
        }
        synchronized (hal) {
            array = hal.getDeviceList(forceParse);
        }
        if (array != null) {
            for (int type : array) {
                int devid = convertInputDeviceTypeToDevid(Integer.valueOf(type));
                Log.d(
                        TAG,
                        "getDeviceList: InputDeviceType:"
                                + type
                                + " support "
                                + SysinputHALInterface.Device.getDeviceFromInt(devid));
                if (devid != 0) {
                    convertList.add(Integer.valueOf(devid));
                }
            }
        }
        return convertList;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int setProperty(int devid, SemInputConstants.Property property, String mode) {
        ISehSysInputDev hal;
        int type = convertDevidToInputDeviceType(devid);
        if (type == 0) {
            return -2;
        }
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "setProperty", e);
        }
        if (hal == null) {
            return -3;
        }
        String key =
                SysinputHALInterface.Device.getDeviceFromInt(devid).getName()
                        + SEPARATOR_AT_SIGN
                        + property.getNodeName()
                        + SEPARATOR_AT_SIGN
                        + mode;
        synchronized (hal) {
            ret = hal.setProperty(type, property.toInt(), mode);
        }
        if (1 == ret) {
            String result = getInformation(devid, key);
            if ("NG".equals(result)) {
                ret = -9;
            } else {
                ret = Integer.parseInt(result);
            }
        }
        Log.d(
                TAG,
                "setProperty("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + property
                        + ", "
                        + mode
                        + " ret="
                        + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String getProperty(int devid, SemInputConstants.Property property) {
        int type = convertDevidToInputDeviceType(devid);
        if (type == 0) {
            return "NG";
        }
        String result = "NG";
        try {
            ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            String key =
                    SysinputHALInterface.Device.getDeviceFromInt(devid).getName()
                            + SEPARATOR_AT_SIGN
                            + property.getNodeName();
            synchronized (hal) {
                result = hal.getProperty(type, property.toInt());
            }
            if ("WAIT".equals(result)) {
                result = getInformation(devid, key);
            }
            String result2 = result.trim();
            Log.i(
                    TAG,
                    "getProperty("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + "): "
                            + property
                            + ":"
                            + getStringForLog(result2));
            return result2;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "getProperty", e);
            return result;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int registerCallback(SysinputHALCallback callback) {
        ISehSysInputDev hal;
        if (callback == null) {
            Log.e(TAG, "registerCallback: binder is null");
            return -2;
        }
        this.callback = callback;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "registerCallback", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.registerCallback(ISehSysInputCallback.Stub.asInterface(this.aidlCallback));
        }
        Log.d(TAG, "registerCallback: ret=" + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String getKeyState(int keycode) {
        ISehSysInputDev hal;
        SehIntStringParcel output = new SehIntStringParcel();
        output.retval = -7;
        output.outbuf = "";
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "getKeyState", e);
        }
        if (hal == null) {
            return output.outbuf;
        }
        synchronized (hal) {
            hal.getKeyState(keycode, output);
        }
        output.outbuf = output.outbuf.trim();
        Log.d(TAG, "getKeyState(" + keycode + "): " + output.outbuf + " ret=" + output.retval);
        return output.outbuf;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String runCommand(int devid, String cmdname) {
        SehIntStringParcel output = new SehIntStringParcel();
        output.retval = -7;
        output.outbuf = "NG";
        try {
            ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            String key =
                    SysinputHALInterface.Device.getDeviceFromInt(devid).getName()
                            + "@cmd@"
                            + cmdname;
            synchronized (hal) {
                hal.runCommand(convertDevidToInputDeviceType(devid), cmdname, output);
            }
            if (1 == output.retval) {
                output.outbuf = getInformation(devid, key);
            }
            output.outbuf = output.outbuf.trim();
            Log.i(
                    TAG,
                    "runCommand("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + "): "
                            + cmdname
                            + ":"
                            + getStringForLog(output.outbuf)
                            + " ret="
                            + output.retval);
            return output.outbuf;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "runCommand", e);
            return "NG";
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int injectRawdata(int devid, int[] list, int size) {
        try {
            ISehSysInputDev hal = getService();
            if (hal != null) {
                Log.d(
                        TAG,
                        "injectRawdata("
                                + SysinputHALInterface.Device.getDeviceFromInt(devid)
                                + ")");
                int ret = hal.injectRawdata(convertDevidToInputDeviceType(devid), list, size);
                Log.d(
                        TAG,
                        "injectRawdata("
                                + SysinputHALInterface.Device.getDeviceFromInt(devid)
                                + ") ret="
                                + ret);
                return 0;
            }
            return -3;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "injectRawdata", e);
            return 0;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int activate(int devid, int enable, boolean isBefore) {
        ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "activate", e);
        }
        if (hal == null) {
            return -3;
        }
        String key =
                SysinputHALInterface.Device.getDeviceFromInt(devid).getName()
                        + SEPARATOR_AT_SIGN
                        + SemInputConstants.Property.ENABLED.getNodeName()
                        + SEPARATOR_AT_SIGN
                        + enable
                        + (isBefore ? ",0" : ",1");
        synchronized (hal) {
            ret = hal.activate(convertDevidToInputDeviceType(devid), enable, isBefore);
        }
        if (1 == ret) {
            String result = getInformation(devid, key);
            if ("NG".equals(result)) {
                return -9;
            }
            ret = Integer.parseInt(result);
        }
        Log.d(
                TAG,
                "activate("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "),"
                        + enable
                        + (isBefore ? ",0" : ",1")
                        + " ret="
                        + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int streamRawdata(int devid, int mode) {
        ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "streamRawdata", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.streamRawdata(convertDevidToInputDeviceType(devid), mode);
        }
        Log.d(
                TAG,
                "streamRawdata("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "),"
                        + mode
                        + " ret="
                        + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String readTaas() {
        ISehSysInputDev hal;
        SehIntStringParcel output = new SehIntStringParcel();
        output.retval = -7;
        output.outbuf = "NG";
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "readTaas", e);
        }
        if (hal == null) {
            return "NG";
        }
        String key = SysinputHALInterface.Device.TAAS.getName() + SEPARATOR_AT_SIGN + "Read";
        synchronized (hal) {
            hal.readTaas(output);
        }
        if ("WAIT".equals(output.outbuf)) {
            output.outbuf = getInformation(41, key);
        }
        output.outbuf = output.outbuf.trim();
        Log.d(TAG, "readTaas: ret=" + output.retval);
        return output.outbuf;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int writeTaas(String wstr) {
        ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "writeTaas", e);
        }
        if (hal == null) {
            return -3;
        }
        String key = SysinputHALInterface.Device.TAAS.getName() + SEPARATOR_AT_SIGN + "Write";
        synchronized (hal) {
            ret = hal.writeTaas(wstr);
        }
        if (1 == ret) {
            String result = getInformation(41, key);
            if ("NG".equals(result)) {
                return -9;
            }
            ret = Integer.parseInt(result);
        }
        Log.d(TAG, "writeTaas: ret=" + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public void dumpEvents(PrintWriter pw) {
        pw.println("- AIDL timeout data: max " + this.timeoutData.getMaxQueueSize());
        for (String data : this.timeoutData.getQueue()) {
            pw.println("  " + data);
        }
        pw.println("  end SysinputHAL_AIDL event");
    }

    private String getInformation(int devid, String key) {
        this.lock.lock();
        try {
            if (this.resultMap.get(key) == null) {
                boolean isDone =
                        this.deviceConditions
                                .get(Integer.valueOf(devid))
                                .await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
                if (!isDone) {
                    Log.i(TAG, "getInformation: timeout " + key);
                    this.timeoutKeys.add(key);
                    this.timeoutData.createDataAndAddQueue(key);
                    return "NG";
                }
            }
            this.lock.unlock();
            String result = this.resultMap.get(key);
            if (result == null) {
                return "NG";
            }
            this.resultMap.remove(key);
            return result;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "getInformation:await:" + key, e);
            return "NG";
        } finally {
            this.lock.unlock();
        }
    }

    public static String getStringForLog(String text) {
        if (text.length() > 30) {
            return text.substring(0, 20) + "...";
        }
        return text;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public void onReportInformation(int devid, String data) {
        ResultSeparator separator = new ResultSeparator(devid, data);
        this.lock.lock();
        try {
            try {
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "onReportInformation", e);
            }
            if (this.timeoutKeys.contains(separator.getKey())) {
                Log.e(TAG, "onReportInformation: already timeout: " + data);
                this.timeoutKeys.remove(separator.getKey());
            } else {
                this.resultMap.put(separator.getKey(), separator.getResult());
                this.deviceConditions.get(Integer.valueOf(devid)).signalAll();
            }
        } finally {
            this.lock.unlock();
        }
    }

    private static class ResultSeparator {
        private static final String SEPARATOR_COLON_SIGN = ":";
        private final String key;
        private final String result;

        public ResultSeparator(int devid, String data) {
            String prefix =
                    SysinputHALInterface.Device.getDeviceFromInt(devid).getName()
                            + SysinputHAL_AIDL.SEPARATOR_AT_SIGN;
            if (data.contains(SEPARATOR_COLON_SIGN)) {
                String[] resultSplits = data.split(SEPARATOR_COLON_SIGN);
                this.key = prefix + resultSplits[0];
                this.result = data.substring(resultSplits[0].length() + 1).trim();
            } else {
                String[] dataSplits = data.split(SysinputHAL_AIDL.SEPARATOR_AT_SIGN);
                this.key = prefix + dataSplits[0];
                this.result = dataSplits[1].trim();
            }
        }

        public String getResult() {
            return this.result;
        }

        public String getKey() {
            return this.key;
        }

        public String toString() {
            return "("
                    + this.key
                    + SEPARATOR_COLON_SIGN
                    + SysinputHAL_AIDL.getStringForLog(this.result)
                    + ")";
        }
    }
}
