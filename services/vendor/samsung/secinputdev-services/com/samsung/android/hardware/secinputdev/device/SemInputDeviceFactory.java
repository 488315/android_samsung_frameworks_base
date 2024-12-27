package com.samsung.android.hardware.secinputdev.device;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SemInputDeviceFactory {
    private static final Map<Integer, SemInputDevice> devices = new HashMap();

    public static synchronized SemInputDevice create(
            String name, int devid, int feature, String cmdlist) {
        SemInputDevice device;
        synchronized (SemInputDeviceFactory.class) {
            SemInputDevice device2 = devices.get(Integer.valueOf(devid));
            if (device2 != null) {
                return device2;
            }
            if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                device = new Touch(name, devid, feature, cmdlist);
            } else if (SemInputDeviceManagerService.isDevidSpen(devid)) {
                device = new Spen(name, devid, feature, cmdlist);
            } else if (SemInputDeviceManagerService.isDevidKeyboard(devid)) {
                device = new Keyboard(name, devid, feature, cmdlist);
            } else if (devid == 41) {
                device = new Taas(name, devid);
            } else {
                device = new NotDefined();
            }
            devices.put(Integer.valueOf(devid), device);
            return device;
        }
    }

    public static synchronized SemInputDevice get(int devid) {
        SemInputDevice semInputDevice;
        synchronized (SemInputDeviceFactory.class) {
            semInputDevice = devices.get(Integer.valueOf(devid));
        }
        return semInputDevice;
    }

    public static synchronized Touch getTouch(int devid) {
        synchronized (SemInputDeviceFactory.class) {
            if (!SemInputDeviceManagerService.isDevidTsp(devid)) {
                return null;
            }
            return (Touch) devices.get(Integer.valueOf(devid));
        }
    }
}
