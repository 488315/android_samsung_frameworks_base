package com.samsung.android.hardware.secinputdev;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.external.SemInputExternal;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALCallback;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface;

import java.io.PrintWriter;
import java.util.HashMap;

public class SemInputDeviceMotionHelper {
    private static final String TAG = "SemInputMotionHelper";
    private SemInputCommandInterface commandOperator;
    private SemInputExternal.IExternalEventRegister externalEventRegister;
    private final Handler mainHandler;
    private final HashMap<SemInputConstants.Device, SemInputDeviceRawdataService> rawdataServices =
            new HashMap<>();
    private final HashMap<SemInputConstants.Device, SemInputMotionController> motionControllers =
            new HashMap<>();
    private final HashMap<SemInputConstants.Device, Boolean> checkTables = new HashMap<>();

    public SemInputDeviceMotionHelper(
            Handler mainHandler,
            SemInputExternal.IExternalEventRegister externalRegister,
            SemInputCommandInterface commandOperator) {
        this.externalEventRegister = null;
        this.commandOperator = null;
        this.mainHandler = mainHandler;
        this.externalEventRegister = externalRegister;
        this.commandOperator = commandOperator;
        this.checkTables.put(SemInputConstants.Device.DEFAULT_TSP, false);
        if (!SemInputFeatures.IS_WEAROS) {
            this.checkTables.put(SemInputConstants.Device.EXTRA_TSP, false);
        }
    }

    public void registerRawdataService(
            SemInputConstants.Device device,
            Context context,
            SysinputHALInterface sysinputHAL,
            int feature) {
        boolean isChecked = this.checkTables.get(device).booleanValue();
        if (!isChecked) {
            int devid = device.toInt();
            SemInputDeviceRawdataService rawdataService =
                    new SemInputDeviceRawdataService(context, sysinputHAL, devid);
            if (!rawdataService.isSupport()) {
                this.checkTables.put(device, true);
                Log.i(TAG, "registerRawdataService: RawdataService not support for " + device);
                return;
            }
            SysinputHALCallback.setRawdataService(devid, rawdataService);
            this.rawdataServices.put(device, rawdataService);
            SemInputMotionController motionController =
                    new SemInputMotionController(context, devid, this.commandOperator);
            motionController.setExternalEventRegister(this.externalEventRegister);
            motionController.setRawdataService(rawdataService);
            motionController.prepare(feature);
            if (!motionController.isSupport()) {
                this.checkTables.put(device, true);
                Log.i(TAG, "registerRawdataService: MotionController not support for " + device);
            } else {
                rawdataService.setMotionController(motionController);
                this.checkTables.put(device, true);
                this.motionControllers.put(device, motionController);
                Log.i(TAG, "registerRawdataService: " + device + " done");
            }
        }
    }

    public boolean registerListener(
            IBinder binder, SemInputConstants.MotionType motionType, String client) {
        boolean result = false;
        switch (motionType) {
            case NONE:
            case CALLBACK:
                for (SemInputDeviceRawdataService rawdataService : this.rawdataServices.values()) {
                    result |= rawdataService.registerCallback(binder, client);
                }
                if (!result) {
                    Log.i(
                            TAG,
                            "registerListener(CALLBACK): maybe not supported or not yet enabled");
                }
                return result;
            case APD:
                for (SemInputMotionController motionController : this.motionControllers.values()) {
                    result |= motionController.enableMotion(motionType, client, binder);
                }
                if (!result) {
                    Log.i(TAG, "registerListener(APD): maybe not supported or not yet enabled");
                }
                return result;
            default:
                Log.e(TAG, "registerListener: not defined type #" + motionType);
                return false;
        }
    }

    public boolean unregisterListener(
            IBinder binder, SemInputConstants.MotionType motionType, String client) {
        boolean result = false;
        switch (motionType) {
            case NONE:
            case CALLBACK:
                for (SemInputDeviceRawdataService rawdataService : this.rawdataServices.values()) {
                    result |= rawdataService.unregisterCallback(binder, client);
                }
                if (!result) {
                    Log.i(
                            TAG,
                            "unregisterListener(CALLBACK): maybe not supported or not yet enabled");
                }
                return result;
            case APD:
                for (SemInputMotionController motionController : this.motionControllers.values()) {
                    result |= motionController.disableMotion(motionType, client, binder);
                }
                if (!result) {
                    Log.i(TAG, "unregisterListener(APD): maybe not supported or not yet enabled");
                }
                return result;
            default:
                Log.e(TAG, "unregisterListener: not defined type #" + motionType);
                return false;
        }
    }

    public int enableMotion(
            SemInputConstants.MotionType motionType, boolean enable, String client) {
        boolean ret = false;
        for (SemInputMotionController motionController : this.motionControllers.values()) {
            if (enable) {
                ret = motionController.enableMotion(motionType, client);
            } else {
                ret = motionController.disableMotion(motionType, client);
            }
        }
        int retval = ret ? 0 : -3;
        Log.i(TAG, "enableMotion: " + motionType + ", client=" + client + ", ret=" + retval);
        return retval;
    }

    public int isEnableMotion(SemInputConstants.MotionType motionType, String client) {
        int retval = -1;
        for (SemInputMotionController motionController : this.motionControllers.values()) {
            if (retval < 0) {
                retval = 0;
            }
            retval |= motionController.isEnableMotion(motionType);
        }
        Log.i(TAG, "isEnableMotion: " + motionType + ", client=" + client + ", ret=" + retval);
        return retval;
    }

    public int setMotionControl(String subtype, int control, String client) {
        int retval = -1;
        for (SemInputMotionController motionController : this.motionControllers.values()) {
            int result = motionController.setMotionControl(subtype, control);
            if (result != -1) {
                retval = result;
            }
        }
        Log.i(
                TAG,
                "setMotionControl: "
                        + subtype
                        + ": "
                        + control
                        + ", client="
                        + client
                        + ", ret="
                        + retval);
        return retval;
    }

    public int getMotionControl(String subtype, String client) {
        for (SemInputMotionController motionController : this.motionControllers.values()) {
            int value = motionController.getMotionControl(subtype);
            if (value != -3) {
                Log.i(
                        TAG,
                        "getMotionControl: " + subtype + ", client=" + client + ", ret=" + value);
                return value;
            }
        }
        Log.i(TAG, "getMotionControl: maybe not supported or not yet enabled");
        return -1;
    }

    public void forcePause() {
        for (SemInputDeviceRawdataService rawdataService : this.rawdataServices.values()) {
            rawdataService.pauseService();
        }
    }

    public void pause(SemInputConstants.Device device, boolean useHandler) {
        final SemInputDeviceRawdataService rawdataService = this.rawdataServices.get(device);
        if (rawdataService != null) {
            if (useHandler) {
                this.mainHandler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.hardware.secinputdev.SemInputDeviceMotionHelper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemInputDeviceRawdataService.this.pauseService();
                            }
                        });
            } else {
                rawdataService.pauseService();
            }
        }
    }

    public void restart(SemInputConstants.Device device, boolean useHandler) {
        final SemInputDeviceRawdataService rawdataService = this.rawdataServices.get(device);
        if (rawdataService != null) {
            if (useHandler) {
                this.mainHandler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.hardware.secinputdev.SemInputDeviceMotionHelper$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemInputDeviceRawdataService.this.restartService();
                            }
                        });
            } else {
                rawdataService.restartService();
            }
        }
    }

    public void onShutdown() {
        for (SemInputDeviceRawdataService rawdataService : this.rawdataServices.values()) {
            rawdataService.destroy();
        }
    }

    public void dump(PrintWriter pw) {
        for (SemInputDeviceRawdataService rawdataService : this.rawdataServices.values()) {
            pw.println("");
            rawdataService.dump(pw);
        }
    }

    public void dumpEvents(PrintWriter pw) {
        for (SemInputMotionController motionController : this.motionControllers.values()) {
            pw.println("");
            motionController.dumpEvents(pw);
        }
    }
}
