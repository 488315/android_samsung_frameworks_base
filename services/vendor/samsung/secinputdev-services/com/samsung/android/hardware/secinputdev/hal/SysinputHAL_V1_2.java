package com.samsung.android.hardware.secinputdev.hal;

import android.os.IHwBinder;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputConstants;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev;
import vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class SysinputHAL_V1_2 implements SysinputHALInterface {
    private final String TAG;
    private ISehSysInputDev halService;

    public SysinputHAL_V1_2() {
        this.halService = null;
        this.TAG = "SysinputHAL_V1_2";
        if (getService() == null) {
            throw new NoSuchElementException();
        }
    }

    protected SysinputHAL_V1_2(String tag) {
        this.halService = null;
        this.TAG = tag + "(V1_2)";
    }

    private synchronized ISehSysInputDev getService() {
        if (this.halService == null) {
            try {
                this.halService = ISehSysInputDev.getService();
                if (this.halService == null) {
                    Log.w(this.TAG, "getService: halService is null");
                    return null;
                }
                Log.d(this.TAG, "getService: " + this.halService.toString());
                if (getClass().getSimpleName().equals(this.TAG)) {
                    HALDeathReceiver deathReceiver = new HALDeathReceiver();
                    try {
                        if (this.halService != null) {
                            this.halService.linkToDeath(deathReceiver, 41L);
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

    private final class HALDeathReceiver implements IHwBinder.DeathRecipient {
        private HALDeathReceiver() {}

        public void serviceDied(long cookie) {
            Log.i(SysinputHAL_V1_2.this.TAG, "serviceDied: " + cookie);
            SysinputHAL_V1_2.this.setServiceNullAndRecovery();
        }
    }

    protected void setServiceNullAndRecovery() {
        synchronized (this) {
            this.halService = null;
        }
    }

    protected synchronized void setService(ISehSysInputDev halService) {
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

    protected synchronized boolean isSameService(ISehSysInputDev halService) {
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

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public float getVersion() {
        return 1.2f;
    }

    public static int convertInputDeviceTypeToDevid(Integer type) {
        switch (type.intValue()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 11;
            case 4:
                return 21;
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
                return 3;
            case 21:
                return 4;
            default:
                return 0;
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public ArrayList<Integer> getDeviceList(boolean forceParse) {
        String result = runTspCmd(0, "getDeviceList");
        ArrayList<Integer> list = new ArrayList<>();
        if ("NG".equals(result)) {
            return list;
        }
        String[] devices = result.split(",");
        for (String type : devices) {
            if (!type.isEmpty()) {
                try {
                    int devid =
                            convertInputDeviceTypeToDevid(Integer.valueOf(Integer.parseInt(type)));
                    Log.d(
                            this.TAG,
                            "getDeviceList: InputDeviceType:"
                                    + type
                                    + " support "
                                    + SysinputHALInterface.Device.getDeviceFromInt(devid));
                    if (devid != 0) {
                        list.add(Integer.valueOf(devid));
                    }
                } catch (Exception e) {
                    SemInputDeviceManagerService.loggingException(this.TAG, "getDeviceList", e);
                }
            }
        }
        return list;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String getKeyState(final int keycode) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            ISehSysInputDev hal = getService();
            if (hal == null) {
                return "";
            }
            synchronized (hal) {
                hal.getKeyCodePressed(
                        keycode,
                        new ISehSysInputDev
                                .getKeyCodePressedCallback() { // from class:
                                                               // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda8
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getKeyCodePressedCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getKeyState$0(keycode, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty()) {
                return "";
            }
            return list.get(0).replaceAll("\n", "");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getKeyState", e);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getKeyState$0(
            int keycode, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(this.TAG, "getKeyState(" + keycode + "): " + buff2 + " ret=" + ret);
        list.add(buff2);
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String runCommand(int devid, String cmdname) {
        if (SemInputDeviceManagerService.isDevidTsp(devid)) {
            return runTspCmd(devid, cmdname);
        }
        if (SemInputDeviceManagerService.isDevidSpen(devid)) {
            return runSpenCmd(cmdname);
        }
        return "NG";
    }

    protected String runTspCmd(final int devid, String cmdname) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.runTspCmd(
                        devid,
                        cmdname,
                        new ISehSysInputDev
                                .runTspCmdCallback() { // from class:
                                                       // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda5
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.runTspCmdCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$runTspCmd$1(devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            String str = list.get(0).replaceAll("\n", "");
            try {
                if (cmdname.contains("factory_cmd_result_all")) {
                    return str;
                }
                String[] array = str.split(":");
                if (cmdname.equals(array[0]) && array.length > 1) {
                    return str.substring(array[0].length() + 1);
                }
                return "NG";
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(this.TAG, "runTspCmd:split", e);
                return "NG";
            }
        } catch (Exception e2) {
            SemInputDeviceManagerService.loggingException(this.TAG, "runTspCmd", e2);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runTspCmd$1(
            int devid, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(
                this.TAG,
                "runTspCmd("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + buff2
                        + " ret="
                        + ret);
        list.add(buff2);
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int setProperty(int devid, SemInputConstants.Property property, String mode) {
        if (property == SemInputConstants.Property.CMD) {
            if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                return runTspCmdNoRead(devid, mode);
            }
            if (SemInputDeviceManagerService.isDevidSpen(devid)) {
                return runSpenCmdNoRead(mode);
            }
        }
        Log.e(
                this.TAG,
                "setProperty("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + property
                        + ", "
                        + mode
                        + " is not supported");
        return -5;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String getProperty(int devid, SemInputConstants.Property property) {
        switch (property) {
            case FEATURE:
                if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                    return getTspSupportFeature(devid);
                }
                if (SemInputDeviceManagerService.isDevidSpen(devid)) {
                    return getSpenSupportFeature();
                }
                break;
            case CMD_LIST:
                if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                    return getTspCommandList(devid);
                }
                if (SemInputDeviceManagerService.isDevidSpen(devid)) {
                    return getSpenCommandList();
                }
                break;
            case SCRUB_POS:
                if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                    return getTspScrubPosition(devid);
                }
                break;
            case FOD_INFO:
                if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                    return getTspFodInformation(devid);
                }
                break;
            case FOD_POS:
                if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                    return getTspFodPosition(devid);
                }
                break;
            case AOD_ACTIVE_AREA:
                if (SemInputDeviceManagerService.isDevidTsp(devid)) {
                    return getTspAodActiveArea(devid);
                }
                break;
            case EPEN_POS:
                if (SemInputDeviceManagerService.isDevidSpen(devid)) {
                    return getSpenPosition();
                }
                break;
        }
        Log.e(
                this.TAG,
                "getProperty("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + property
                        + " is not supported");
        return "NG";
    }

    protected int runTspCmdNoRead(int devid, String cmdname) {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "runTspCmdNoRead", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.runTspCmdNoRead(devid, cmdname);
        }
        Log.d(
                this.TAG,
                "runTspCmdNoRead("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + cmdname
                        + " ret="
                        + ret);
        return ret;
    }

    protected String getTspScrubPosition(final int devid) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getTspScrubPosition(
                        devid,
                        new ISehSysInputDev
                                .getTspScrubPositionCallback() { // from class:
                                                                 // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda4
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspScrubPositionCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getTspScrubPosition$2(
                                        devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty()) {
                return "NG";
            }
            return list.get(0).replaceAll("\n", "");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getTspScrubPosition", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTspScrubPosition$2(
            int devid, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(
                this.TAG,
                "getTspScrubPosition("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + buff2
                        + " ret="
                        + ret);
        list.add(buff2);
    }

    protected String getTspSupportFeature(final int devid) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getTspSupportFeature(
                        devid,
                        new ISehSysInputDev
                                .getTspSupportFeatureCallback() { // from class:
                                                                  // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda11
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspSupportFeatureCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getTspSupportFeature$3(
                                        devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || list.get(0).equals("NG")) {
                return "NG";
            }
            return list.get(0);
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getTspSupportFeature", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTspSupportFeature$3(
            int devid, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(
                this.TAG,
                "getTspSupportFeature("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + buff2
                        + " ret="
                        + ret);
        list.add(buff2);
    }

    protected String getSpenSupportFeature() {
        return getTspSupportFeature(convertDevidToInputDeviceType(11));
    }

    protected String getTspCommandList(final int devid) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getTspCommandList(
                        devid,
                        new ISehSysInputDev
                                .getTspCommandListCallback() { // from class:
                                                               // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda6
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspCommandListCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getTspCommandList$4(
                                        devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            return list.get(0);
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getTspCommandList", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTspCommandList$4(
            int devid, ArrayList list, int ret, String buff) {
        Log.d(
                this.TAG,
                "getTspCommandList("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + ") ret="
                        + ret);
        list.add(buff.trim());
    }

    protected String getTspAodActiveArea(final int devid) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getTspAodActiveArea(
                        devid,
                        new ISehSysInputDev
                                .getTspAodActiveAreaCallback() { // from class:
                                                                 // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda7
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspAodActiveAreaCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getTspAodActiveArea$5(
                                        devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            return list.get(0).replaceAll("\n", "");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getTspAodActiveArea", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTspAodActiveArea$5(
            int devid, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(
                this.TAG,
                "getTspAodActiveArea("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + buff2
                        + " ret="
                        + ret);
        list.add(buff2);
    }

    protected String getTspFodInformation(final int devid) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getTspFodInformation(
                        devid,
                        new ISehSysInputDev
                                .getTspFodInformationCallback() { // from class:
                                                                  // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda3
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspFodInformationCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getTspFodInformation$6(
                                        devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            return list.get(0).replaceAll("\n", "");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getTspFodInformation", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTspFodInformation$6(
            int devid, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(
                this.TAG,
                "getTspFodInformation("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + buff2
                        + " ret="
                        + ret);
        list.add(buff2);
    }

    protected String getTspFodPosition(final int devid) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getTspFodPosition(
                        devid,
                        new ISehSysInputDev
                                .getTspFodPositionCallback() { // from class:
                                                               // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda1
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspFodPositionCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getTspFodPosition$7(
                                        devid, list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            return list.get(0).replaceAll("\n", "");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getTspFodPosition", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTspFodPosition$7(
            int devid, ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(
                this.TAG,
                "getTspFodPosition("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "): "
                        + buff2
                        + " ret="
                        + ret);
        list.add(buff2);
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int activate(int devid, int enable, boolean isEarly) {
        if (SemInputDeviceManagerService.isDevidTsp(devid)) {
            return setTspEnable(devid, enable, isEarly);
        }
        if (SemInputDeviceManagerService.isDevidSpen(devid)) {
            return setSpenEnable(enable, isEarly);
        }
        return -2;
    }

    protected int setTspEnable(int devid, int enable, boolean isBefore) {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "setTspEnable", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.setTspEnable(devid, enable, isBefore);
        }
        Log.d(
                this.TAG,
                "setTspEnable("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "),"
                        + enable
                        + ","
                        + isBefore
                        + " ret="
                        + ret);
        return ret;
    }

    protected String runSpenCmd(String cmdname) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.runSpenCmd(
                        cmdname,
                        new ISehSysInputDev
                                .runSpenCmdCallback() { // from class:
                                                        // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda2
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.runSpenCmdCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$runSpenCmd$8(list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            String str = list.get(0).replaceAll("\n", "");
            try {
                String[] array = str.split(":");
                if (cmdname.equals(array[0]) && array.length > 1) {
                    return array[1];
                }
                return "NG";
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(this.TAG, "runSpenCmd:split", e);
                return "NG";
            }
        } catch (Exception e2) {
            SemInputDeviceManagerService.loggingException(this.TAG, "runSpenCmd", e2);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runSpenCmd$8(ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(this.TAG, "runSpenCmd: " + buff2 + " ret=" + ret);
        list.add(buff2);
    }

    protected int runSpenCmdNoRead(String cmdname) {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "runSpenCmdNoRead", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.runSpenCmdNoRead(cmdname);
        }
        Log.d(this.TAG, "runSpenCmdNoRead: " + cmdname + " ret=" + ret);
        return ret;
    }

    protected String getSpenPosition() {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getSpenPosition(
                        new ISehSysInputDev
                                .getSpenPositionCallback() { // from class:
                                                             // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda12
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getSpenPositionCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getSpenPosition$9(list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            return list.get(0).replaceAll("\n", "");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getSpenPosition", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSpenPosition$9(ArrayList list, int ret, String buff) {
        String buff2 = buff.trim();
        Log.d(this.TAG, "getSpenPosition: " + buff2 + " ret=" + ret);
        list.add(buff2);
    }

    protected String getSpenCommandList() {
        final ArrayList<String> list = new ArrayList<>();
        try {
            vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal = getService();
            if (hal == null) {
                return "NG";
            }
            synchronized (hal) {
                hal.getSpenCommandList(
                        new ISehSysInputDev
                                .getSpenCommandListCallback() { // from class:
                                                                // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda0
                            @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getSpenCommandListCallback
                            public final void onValues(int i, String str) {
                                SysinputHAL_V1_2.this.lambda$getSpenCommandList$10(list, i, str);
                            }
                        });
            }
            if (list.size() == 0 || list.get(0).isEmpty() || "NG".equals(list.get(0))) {
                return "NG";
            }
            return list.get(0).replaceAll("\n", ",");
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "getSpenCommandList", e);
            return "NG";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSpenCommandList$10(ArrayList list, int ret, String buff) {
        Log.d(this.TAG, "getSpenCommandList: ret=" + ret);
        list.add(buff.trim());
    }

    protected int setSpenEnable(int enable, boolean isBefore) {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "setSpenEnable", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.setSpenEnable(enable, isBefore);
        }
        Log.d(this.TAG, "setSpenEnable," + enable + ", " + isBefore + " ret=" + ret);
        return ret;
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int streamRawdata(final int devid, final int mode) {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        final ArrayList<Short> maxlist = new ArrayList<>();
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "streamRawdata", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            hal.initTspRawData(
                    devid,
                    mode,
                    new ISehSysInputDev
                            .initTspRawDataCallback() { // from class:
                                                        // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda10
                        @Override // vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev.initTspRawDataCallback
                        public final void onValues(int i, ArrayList arrayList) {
                            SysinputHAL_V1_2.this.lambda$streamRawdata$11(
                                    maxlist, devid, mode, i, arrayList);
                        }
                    });
        }
        if (maxlist.size() == 0) {
            return -7;
        }
        int result = maxlist.get(0).intValue();
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$streamRawdata$11(
            ArrayList maxlist, int devid, int mode, int ret, ArrayList list) {
        maxlist.add(Short.valueOf((short) ret));
        Log.d(
                this.TAG,
                "streamRawdata("
                        + SysinputHALInterface.Device.getDeviceFromInt(devid)
                        + "),"
                        + mode
                        + " ret="
                        + ret);
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public String readTaas() {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        final ArrayList<String> list = new ArrayList<>();
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "readTaas", e);
        }
        if (hal == null) {
            return "NG";
        }
        synchronized (hal) {
            hal.readTaas(
                    new ISehSysInputDev
                            .readTaasCallback() { // from class:
                                                  // com.samsung.android.hardware.secinputdev.hal.SysinputHAL_V1_2$$ExternalSyntheticLambda9
                        @Override // vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev.readTaasCallback
                        public final void onValues(int i, String str) {
                            SysinputHAL_V1_2.this.lambda$readTaas$12(list, i, str);
                        }
                    });
        }
        if (list.size() == 0 || list.get(0).isEmpty()) {
            return "NG";
        }
        return list.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readTaas$12(ArrayList list, int ret, String buff) {
        Log.d(this.TAG, "readTaas: ret=" + ret);
        list.add(buff);
    }

    @Override // com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface
    public int writeTaas(String wstr) {
        vendor.samsung.hardware.sysinput.V1_2.ISehSysInputDev hal;
        int ret = -7;
        try {
            hal = getService();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "writeTaas", e);
        }
        if (hal == null) {
            return -3;
        }
        synchronized (hal) {
            ret = hal.writeTaas(wstr);
        }
        Log.d(this.TAG, "writeTaas: ret=" + ret);
        return ret;
    }
}
