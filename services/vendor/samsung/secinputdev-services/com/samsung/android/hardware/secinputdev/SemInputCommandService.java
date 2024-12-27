package com.samsung.android.hardware.secinputdev;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.device.SemInputDevice;
import com.samsung.android.hardware.secinputdev.device.SemInputDeviceFactory;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemInputCommandService {
    private static final String TAG = "SemInputCommandService";
    private final HashMap<Integer, String> allDeviceList;
    private final SemInputDumpsysData enabledDumpsys;
    private final HashMap<SemInputConstants.Command, String> lastCommand;
    private final HashMap<Integer, SemInputDevice> supportDeviceList;
    private final SysinputHALInterface sysinputHAL;

    public SemInputCommandService(SysinputHALInterface hal) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        this.allDeviceList = hashMap;
        HashMap<Integer, SemInputDevice> hashMap2 = new HashMap<>();
        this.supportDeviceList = hashMap2;
        this.lastCommand = new HashMap<>();
        this.enabledDumpsys = new SemInputDumpsysData(100);
        this.sysinputHAL = hal;
        hashMap.put(1, "TSP");
        hashMap.put(2, "TSP_SUB");
        hashMap.put(11, "SPEN");
        hashMap.put(31, "KEYBOARD");
        getSupportDeviceList();
        for (SemInputDevice device : hashMap2.values()) {
            Log.d(TAG, "find " + device.toString());
        }
    }

    public void getSupportDeviceList() {
        ArrayList<Integer> list = this.sysinputHAL.getDeviceList(false);
        if (Float.compare(this.sysinputHAL.getVersion(), 1.2f) <= 0 && list.size() == 0) {
            Log.d(
                    TAG,
                    "getSupportDeviceList: hal might be OLD version. Check all possible devices");
            Iterator<Integer> it = this.allDeviceList.keySet().iterator();
            while (it.hasNext()) {
                checkAndRegisterSupportDevice(it.next().intValue());
            }
            return;
        }
        Iterator<Integer> it2 = list.iterator();
        while (it2.hasNext()) {
            Integer devid = it2.next();
            if (this.supportDeviceList.containsKey(devid)) {
                Log.d(TAG, "getSupportDeviceList: exists " + this.supportDeviceList.get(devid));
            } else {
                SemInputDevice device = registerSupportDevice(devid.intValue());
                if (device == null) {
                    Log.e(TAG, "getSupportDeviceList: not supportDevice " + devid);
                } else {
                    restoreCommands(device);
                }
            }
        }
    }

    private int checkAndRegisterSupportDevice(int devid) {
        String deviceName = this.allDeviceList.get(Integer.valueOf(devid));
        if (deviceName == null) {
            return -2;
        }
        Log.i(TAG, "checkAndRegisterSupportDevice: " + deviceName + "(" + devid + ")");
        String cmdlist = getCommandList(devid);
        if ("NG".equals(cmdlist)) {
            return -2;
        }
        int feature = getSupportFeature(devid);
        this.supportDeviceList.put(
                Integer.valueOf(devid),
                SemInputDeviceFactory.create(deviceName, devid, feature, cmdlist));
        return 0;
    }

    private SemInputDevice registerSupportDevice(int devid) {
        String deviceName = this.allDeviceList.get(Integer.valueOf(devid));
        if (deviceName == null) {
            return null;
        }
        String cmdlist = getCommandList(devid);
        int feature = getSupportFeature(devid);
        SemInputDevice device = SemInputDeviceFactory.create(deviceName, devid, feature, cmdlist);
        this.supportDeviceList.put(Integer.valueOf(devid), device);
        return device;
    }

    private void restoreCommands(SemInputDevice device) {
        for (Map.Entry<SemInputConstants.Command, String> entry : this.lastCommand.entrySet()) {
            String[] params = entry.getValue().split(",");
            boolean needRestore = false;
            for (String param : params) {
                if (!"0".equals(param)) {
                    needRestore = true;
                }
            }
            if (entry.getKey() == SemInputConstants.Command.FOLD_STATE) {
                needRestore = true;
            }
            if (needRestore) {
                Log.d(
                        TAG,
                        "restoreCommands("
                                + device.getName()
                                + "): "
                                + entry.getKey()
                                + ","
                                + entry.getValue());
                setProperty(device, entry.getKey(), entry.getValue());
            }
        }
    }

    public String getCommandList(int devid) {
        String result = this.sysinputHAL.getProperty(devid, SemInputConstants.Property.CMD_LIST);
        return result.replaceAll("\n", ",");
    }

    private int getSupportFeature(int devid) {
        String result = this.sysinputHAL.getProperty(devid, SemInputConstants.Property.FEATURE);
        if ("NG".equals(result)) {
            return 0;
        }
        try {
            int ret = Integer.parseInt(result);
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "getSupportFeature: " + e);
            return 0;
        }
    }

    public boolean isSupportSysinputEnabled() {
        return isFeatureEnabled("isSupportSysinputEnabled", 32);
    }

    public boolean isSupportProxLpScanEnabled() {
        return isFeatureEnabled("isSupportProxLpScanEnabled", 64);
    }

    public boolean isSupportInputMonitorEnabled() {
        return isFeatureEnabled(
                "isSupportInputMonitorEnabled", SemInputDeviceManager.SUPPORT_INPUT_MONITOR);
    }

    public boolean isSupportAot() {
        return isFeatureEnabled("isSupportAot", 1);
    }

    private boolean isFeatureEnabled(String name, int enableBit) {
        boolean enabled;
        Iterator<SemInputDevice> it = this.supportDeviceList.values().iterator();
        do {
            if (!it.hasNext()) {
                return false;
            }
            SemInputDevice device = it.next();
            enabled = (device.getSupportFeature() & enableBit) != 0;
            Log.d(
                    TAG,
                    name
                            + "("
                            + device.getName()
                            + "): "
                            + enabled
                            + String.format(
                                    " [0x%X|0x%X]",
                                    Integer.valueOf(device.getSupportFeature()),
                                    Integer.valueOf(enableBit)));
        } while (!enabled);
        return enabled;
    }

    public int getSupportDevice(int devid) {
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device != null) {
            Log.d(TAG, "getSupportDevice: supported " + device);
            return 0;
        }
        String deviceName = this.allDeviceList.get(Integer.valueOf(devid));
        Log.e(TAG, "getSupportDevice: not supported " + deviceName + "(" + devid + ")");
        return -1;
    }

    public String getKeyPressStateAll() {
        Log.d(TAG, "getKeyPressStateAll ++");
        String volumedown = isKeyPressedByKeycode(SemInputDeviceManager.KEY_VOLUMEDOWN) ? "1" : "0";
        String volumeup = isKeyPressedByKeycode(SemInputDeviceManager.KEY_VOLUMEUP) ? "1" : "0";
        String power = isKeyPressedByKeycode(SemInputDeviceManager.KEY_POWER) ? "1" : "0";
        String hot = isKeyPressedByKeycode(SemInputDeviceManager.KEY_HOT) ? "1" : "0";
        String emergency = isKeyPressedByKeycode(SemInputDeviceManager.KEY_EMERGENCY) ? "1" : "0";
        String micmute = isKeyPressedByKeycode(SemInputDeviceManager.KEY_MICMUTE) ? "1" : "0";
        String recent = isKeyPressedByKeycode(SemInputDeviceManager.KEY_RECENT) ? "1" : "0";
        String home = isKeyPressedByKeycode(SemInputDeviceManager.KEY_HOME) ? "1" : "0";
        String back = isKeyPressedByKeycode(SemInputDeviceManager.KEY_BACK) ? "1" : "0";
        StringBuilder strbuilder = new StringBuilder("");
        strbuilder.append("114:" + volumedown + ",");
        strbuilder.append("115:" + volumeup + ",");
        strbuilder.append("116:" + power + ",");
        strbuilder.append("252:" + hot + ",");
        strbuilder.append("672:" + emergency + ",");
        strbuilder.append("248:" + micmute + ",");
        strbuilder.append("254:" + recent + ",");
        strbuilder.append("172:" + home + ",");
        strbuilder.append("158:" + back);
        Log.d(TAG, "getKeyPressStateAll: " + strbuilder.toString());
        return strbuilder.toString();
    }

    public boolean isKeyPressedByKeycode(int keycode) {
        try {
            int keystate = Integer.parseInt(this.sysinputHAL.getKeyState(keycode));
            Log.d(TAG, "isKeyPressedByKeycode: " + keycode + ":" + keystate + "");
            if (keystate != 1) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "isKeyPressedByKeycode: " + e);
            return false;
        }
    }

    public int activate(int devid, int mode, boolean state, String caller, boolean useThread) {
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device == null) {
            return -2;
        }
        Result result = new Result();
        int ret = device.activate(mode, state, result);
        if (!useThread && ret == 1) {
            ret = result.getInteger();
        }
        addToEnabledDumpsys(device.getFormatName(), mode, state, caller, ret);
        return ret;
    }

    public int activate(int devid, int mode, boolean state, boolean useThread) {
        return activate(devid, mode, state, "", useThread);
    }

    public int setPropertyAllTouchAndSpen(SemInputConstants.Command command, String mode) {
        int retSpen = setProperty(11, command, mode);
        int retTouch = setPropertyAllTouch(command, mode);
        Log.i(
                TAG,
                "setPropertyAllTouchAndSpen: "
                        + command
                        + ","
                        + mode
                        + " retTouch="
                        + retTouch
                        + " retSpen="
                        + retSpen);
        if (retSpen == -2) {
            return retTouch;
        }
        if (retTouch == 0 && retSpen < 0) {
            return retSpen;
        }
        return retTouch;
    }

    public int setPropertyAllTouch(SemInputConstants.Command command, String mode) {
        int retExtra = setProperty(2, command, mode);
        int retDefault = setProperty(1, command, mode);
        Log.i(
                TAG,
                "setPropertyAllTouch: "
                        + command
                        + ","
                        + mode
                        + " retDefault="
                        + retDefault
                        + " retExtra="
                        + retExtra);
        if (retExtra == -2) {
            return retDefault;
        }
        if (retDefault == 0 && retExtra < 0) {
            return retExtra;
        }
        return retDefault;
    }

    public int setProperty(int devid, SemInputConstants.Command command, String mode) {
        if (command.needUpdate()) {
            this.lastCommand.put(command, mode);
        }
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device == null) {
            return -2;
        }
        return setProperty(device, command, mode);
    }

    private int setProperty(SemInputDevice device, SemInputConstants.Command command, String mode) {
        return device.setProperty(command, mode, new Result());
    }

    public int setProperty(int devid, SemInputConstants.Property property, String mode) {
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device == null) {
            return -2;
        }
        return device.setProperty(property, mode);
    }

    public String getProperty(int devid, SemInputConstants.Property property) {
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device == null) {
            return SemInputDeviceManager.RESULT_STR_NA;
        }
        return device.getProperty(property);
    }

    public String runCommand(int devid, String cmd) {
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device == null) {
            Log.d(TAG, "runCommand: not supported device(" + devid + "): " + cmd);
            return SemInputDeviceManager.RESULT_STR_NA;
        }
        return device.runCommand(cmd);
    }

    public int getTspSupportFeature(int devid) {
        SemInputDevice device = this.supportDeviceList.get(Integer.valueOf(devid));
        if (device == null) {
            Log.e(TAG, "getTspSupportFeature(" + devid + "): device is not exist");
            return 0;
        }
        Log.d(
                TAG,
                "getTspSupportFeature("
                        + device.getName()
                        + "): "
                        + String.format("0x%X", Integer.valueOf(device.getSupportFeature())));
        return device.getSupportFeature();
    }

    private void addToEnabledDumpsys(
            String name, int mode, boolean state, String caller, int retVal) {
        switch (mode) {
            case 1:
                if (state) {
                    this.enabledDumpsys.createDataAndAddQueue(name + " OFF(" + retVal + ")");
                    break;
                }
                break;
            case 2:
                if (!state) {
                    this.enabledDumpsys.createDataAndAddQueue(name + " ON (" + retVal + ")");
                    break;
                }
                break;
            case 3:
            case 4:
                break;
            case 21:
                this.enabledDumpsys.createDataAndAddQueue(
                        name + " FORCE_OFF(" + retVal + "): " + caller);
                break;
            case 22:
                this.enabledDumpsys.createDataAndAddQueue(
                        name + " FORCE_ON (" + retVal + "): " + caller);
                break;
            default:
                this.enabledDumpsys.createDataAndAddQueue(name + " mode:" + mode + ", " + caller);
                break;
        }
    }

    public void dump(PrintWriter pw) {
        pw.println("dumping SemInputCommandService");
        pw.println("- last command value");
        for (Map.Entry<SemInputConstants.Command, String> entry : this.lastCommand.entrySet()) {
            pw.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        pw.println("- support device list");
        for (SemInputDevice device : this.supportDeviceList.values()) {
            pw.println("  " + device.toString());
            pw.println("    " + device.getSupportCommands());
            pw.println("    " + device.getExecutorInformation());
        }
    }

    public void dumpEvents(PrintWriter pw) {
        pw.println("- enabled data: max " + this.enabledDumpsys.getMaxQueueSize());
        for (String data : this.enabledDumpsys.getQueue()) {
            pw.println("  " + data);
        }
        pw.println("  end SemInputCommandService enabled");
    }

    public static class Result {
        private boolean isUpdated;
        private final Lock lock;
        private int resultInteger;
        private String resultString;
        private final Condition updated;

        public Result() {
            ReentrantLock reentrantLock = new ReentrantLock();
            this.lock = reentrantLock;
            this.updated = reentrantLock.newCondition();
            this.resultInteger = -20;
            this.resultString = "NG";
            this.isUpdated = false;
        }

        public int getInteger() {
            waitUpdate();
            return this.resultInteger;
        }

        public String getString() {
            waitUpdate();
            return this.resultString;
        }

        public void set(int result) {
            this.lock.lock();
            try {
                this.resultInteger = result;
                this.isUpdated = true;
                this.updated.signalAll();
            } finally {
                this.lock.unlock();
            }
        }

        public void set(String result) {
            this.lock.lock();
            try {
                this.resultString = result;
                this.isUpdated = true;
                this.updated.signalAll();
            } finally {
                this.lock.unlock();
            }
        }

        private void waitUpdate() {
            this.lock.lock();
            while (!this.isUpdated) {
                try {
                    try {
                        this.updated.await();
                    } catch (InterruptedException e) {
                        Log.d(SemInputCommandService.TAG, "waitUpdate: interrupted");
                    }
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }
}
