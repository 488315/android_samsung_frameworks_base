package com.samsung.android.hardware.secinputdev.hal;

import android.os.HwBinder;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputConstants;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import vendor.samsung.hardware.sysinput.V1_3.ISehSysInputCallback;
import vendor.samsung.hardware.sysinput.V1_3.ISehSysInputDev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SysinputHAL_V1_3 extends SysinputHAL_V1_2 implements SysinputHALInterface {
    private final String TAG;
    private SysinputHALCallback callback;
    private ISehSysInputDev halService;
    private HwBinder hidlCallback;

    public SysinputHAL_V1_3() {
        super("SysinputHAL_V1_3");
        this.halService = null;
        this.callback = null;
        this.hidlCallback =
                new ISehSysInputCallback
                        .Stub() { // from class:
                                  // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_3.1
                    @Override // vendor.samsung.hardware.sysinput.V1_3.ISehSysInputCallback
                    public void onReportInformation(int inputType, String data)
                            throws RemoteException {
                        int type =
                                SysinputHAL_V1_3.convertInputDeviceTypeToDevid(
                                        Integer.valueOf(inputType));
                        SysinputHAL_V1_3.this.callback.onReportInformation(type, data);
                    }

                    @Override // vendor.samsung.hardware.sysinput.V1_3.ISehSysInputCallback
                    public void onReportRawData(int inputType, int count, ArrayList<Short> list)
                            throws RemoteException {
                        int type =
                                SysinputHAL_V1_3.convertInputDeviceTypeToDevid(
                                        Integer.valueOf(inputType));
                        SysinputHAL_V1_3.this.callback.onReportRawData(type, count, list);
                    }
                };
        this.TAG = "SysinputHAL_V1_3";
        if (getService() == null) {
            throw new NoSuchElementException();
        }
    }

    protected SysinputHAL_V1_3(String tag) {
        super(tag);
        this.halService = null;
        this.callback = null;
        this.hidlCallback =
                new ISehSysInputCallback
                        .Stub() { // from class:
                                  // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_3.1
                    @Override // vendor.samsung.hardware.sysinput.V1_3.ISehSysInputCallback
                    public void onReportInformation(int inputType, String data)
                            throws RemoteException {
                        int type =
                                SysinputHAL_V1_3.convertInputDeviceTypeToDevid(
                                        Integer.valueOf(inputType));
                        SysinputHAL_V1_3.this.callback.onReportInformation(type, data);
                    }

                    @Override // vendor.samsung.hardware.sysinput.V1_3.ISehSysInputCallback
                    public void onReportRawData(int inputType, int count, ArrayList<Short> list)
                            throws RemoteException {
                        int type =
                                SysinputHAL_V1_3.convertInputDeviceTypeToDevid(
                                        Integer.valueOf(inputType));
                        SysinputHAL_V1_3.this.callback.onReportRawData(type, count, list);
                    }
                };
        this.TAG = tag + "(V1_3)";
    }

    private synchronized ISehSysInputDev getService() {
        if (this.halService == null
                || !super.isSameService(
                        (vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev) this.halService)) {
            try {
                setService(ISehSysInputDev.getService());
                if (this.halService == null) {
                    Log.w(this.TAG, "getService: halService is null");
                    return null;
                }
                Log.d(this.TAG, "getService: " + this.halService.toString());
                if (getClass().getSimpleName().equals(this.TAG)) {
                    HALDeathReceiver deathReceiver = new HALDeathReceiver();
                    try {
                        if (this.halService != null) {
                            this.halService.linkToDeath(deathReceiver, 42L);
                            Log.i(this.TAG, "getService: register linkToDeath");
                        }
                    } catch (Exception e) {
                        SemInputDeviceManagerService.loggingException(
                                this.TAG, "getService:linkToDeath", e);
                        setServiceNullAndRecovery();
                        return null;
                    }
                }
            } catch (Exception e2) {
                SemInputDeviceManagerService.loggingException(this.TAG, "getService", e2);
                return null;
            }
        }
        return this.halService;
    }

    protected synchronized void setService(ISehSysInputDev halService) {
        if (halService == null) {
            Log.e(this.TAG, "setService: halService is null");
            return;
        }
        super.setService((vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev) halService);
        if (this.halService == null) {
            this.halService = halService;
        } else {
            ISehSysInputDev tempService = this.halService;
            synchronized (tempService) {
                try {
                    this.halService = halService;
                    Log.d(this.TAG, "setService");
                } finally {
                    th = th;
                    while (true) {
                        try {
                        } catch (Throwable th) {
                            th = th;
                        }
                    }
                }
            }
        }
    }

    private final class HALDeathReceiver implements IHwBinder.DeathRecipient {
        private HALDeathReceiver() {}

        public void serviceDied(long cookie) {
            Log.i(SysinputHAL_V1_3.this.TAG, "serviceDied: " + cookie);
            SysinputHAL_V1_3.this.setServiceNullAndRecovery();
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2
    protected void setServiceNullAndRecovery() {
        super.setServiceNullAndRecovery();
        synchronized (this) {
            this.halService = null;
        }
        SemInputDeviceManagerService.registerCallbackForHalRecovery(200);
    }

    protected synchronized boolean isSameService(ISehSysInputDev halService) {
        boolean superResult =
                super.isSameService(
                        (vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev) halService);
        if (!superResult) {
            return false;
        }
        if (this.halService == null) {
            return false;
        }
        synchronized (this.halService) {
            try {
                if (this.halService == halService) {
                    return true;
                }
                Log.d(this.TAG, "isSameService: different");
                return false;
            } finally {
                th = th;
                while (true) {
                    try {
                    } catch (Throwable th) {
                        th = th;
                    }
                }
            }
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
            default:
                return 0;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2,
              // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public float getVersion() {
        return 1.3f;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2,
              // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public ArrayList<Integer> getDeviceList(boolean forceParse) {
        ISehSysInputDev hal;
        ArrayList<Integer> integerList = null;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getDeviceList", e);
        }
        if (hal == null) {
            return new ArrayList<>();
        }
        synchronized (hal) {
            integerList = hal.getDeviceList(forceParse);
        }
        ArrayList<Integer> convertList = new ArrayList<>();
        if (integerList != null) {
            Iterator<Integer> it = integerList.iterator();
            while (it.hasNext()) {
                Integer type = it.next();
                int devid = convertInputDeviceTypeToDevid(type);
                Log.d(
                        this.TAG,
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

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2,
              // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int setProperty(int devid, SemInputConstants.Property property, String mode) {
        ISehSysInputDev hal;
        int type = convertDevidToInputDeviceType(devid);
        if (type == 0) {
            return -2;
        }
        if (property == SemInputConstants.Property.CMD) {
            if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                return runTspCmdNoRead(devid, mode);
            }
            if (!SemInputDeviceManagerService.isDevidSpen(devid)) {
                return -2;
            }
            return runSpenCmdNoRead(mode);
        }
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "setProperty", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.setProperty(type, property.toInt(), mode);
        }
        Log.d(
                this.TAG,
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

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2,
              // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String getProperty(int devid, SemInputConstants.Property property) {
        ISehSysInputDev hal;
        int type = convertDevidToInputDeviceType(devid);
        if (type == 0) {
            return "NG";
        }
        String result = "NG";
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getProperty", e);
        }
        if (hal == null) {
            return "NG";
        }
        synchronized (hal) {
            result = hal.getProperty(type, property.toInt());
        }
        result = result.trim();
        if (result.length() < 50) {
            Log.d(
                    this.TAG,
                    "getProperty("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + "): "
                            + property
                            + ":"
                            + result);
        } else {
            Log.d(
                    this.TAG,
                    "getProperty("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + "): "
                            + property);
        }
        return result;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int registerCallback(SysinputHALCallback callback) {
        ISehSysInputDev hal;
        if (callback == null) {
            Log.e(this.TAG, "registerCallback: binder is null");
            return -2;
        }
        this.callback = callback;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "registerCallback", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret =
                    hal.registerCallback(
                            ISehSysInputCallback.asInterface((IHwBinder) this.hidlCallback));
        }
        Log.d(this.TAG, "registerCallback: ret=" + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int injectRawdata(int devid, int[] list, int size) {
        try {
            ISehSysInputDev hal = getService();
            if (hal == null) {
                return -3;
            }
            ArrayList<Short> arrList = new ArrayList<>();
            for (int i : list) {
                arrList.add(Short.valueOf((short) i));
            }
            Log.d(this.TAG, "injectRawdata");
            int ret = hal.sendRawdataTsp(devid, arrList, size);
            Log.d(
                    this.TAG,
                    "injectRawdata("
                            + SysinputHALInterface.Device.getDeviceFromInt(devid)
                            + ") ret="
                            + ret);
            return 0;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "injectRawdata", e);
            return 0;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2,
              // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int streamRawdata(int devid, int mode) {
        getService();
        return super.streamRawdata(devid, mode);
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2,
              // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int activate(int devid, int enable, boolean isEarly) {
        if (SemInputDeviceManagerService.isDevidTsp(devid)) {
            return setTspEnable(devid, enable, isEarly);
        }
        if (SemInputDeviceManagerService.isDevidSpen(devid)) {
            return setSpenEnable(enable, isEarly);
        }
        if (SemInputDeviceManagerService.isDevidKeyboard(devid)) {
            return setKeyboardEnable(enable, isEarly);
        }
        return -2;
    }

    protected int setKeyboardEnable(int enable, boolean isBefore) {
        ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "setKeyboardEnable", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.setKeyboardEnable(enable, isBefore);
        }
        Log.d(this.TAG, "setKeyboardEnable," + enable + ", " + isBefore + " ret=" + ret);
        return ret;
    }
}
