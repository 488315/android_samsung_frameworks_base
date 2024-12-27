package com.samsung.android.hardware.secinputdev;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.external.SemInputExternal;
import com.samsung.android.hardware.secinputdev.motion.SemInputMotion;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SemInputMotionController {
    private static final String PACKAGE_NAME = "com.samsung.android.hardware.secinputdev.motion.";
    private SemInputCommandInterface commandOperator;
    private final Context context;
    private String TAG = "SemInputMotionController";
    private final SemInputDumpsysData eventDumpsys = new SemInputDumpsysData(100);
    private final StringBuilder bootingDump = new StringBuilder();
    private final HashMap<SemInputConstants.MotionType, InputMotion> motionList = new HashMap<>();
    private final HashMap<String, LinkedList> reportInformationList = new HashMap<>();
    private final Map<SemInputConstants.MotionType, String> availableMotions = new HashMap();
    private HashMap<Integer, SettingInformation> availableSettings = new HashMap<>();
    private final HashMap<Integer, Handler> settingHandlerMap = new HashMap<>();
    private SemInputDeviceRawdataService rawdataService = null;
    private SemInputSettingObserver settingObserver = null;
    private Handler settingHandler = null;
    private volatile boolean isScreenOn = true;
    private boolean isSupport = false;
    private SemInputExternal.IExternalEventRegister externalEventRegister = null;

    public SemInputMotionController(
            Context context, int devid, SemInputCommandInterface commandOperator) {
        this.commandOperator = null;
        this.context = context;
        this.commandOperator = commandOperator;
        this.TAG += devid;
        if (SemInputFeatures.SUPPORT_PALMMUTE) {
            this.availableMotions.put(
                    SemInputConstants.MotionType.PALM_MUTE, "SemInputMotionPalmMute");
            this.availableSettings.put(
                    1, new SettingInformation(SemInputConstants.MotionType.PALM_MUTE, 0));
            this.availableSettings.put(
                    10, new SettingInformation(SemInputConstants.MotionType.PALM_MUTE, 7));
            this.availableSettings.put(
                    11, new SettingInformation(SemInputConstants.MotionType.PALM_MUTE, 2));
        }
        if (SemInputFeatures.SUPPORT_AIVF) {
            this.availableMotions.put(
                    SemInputConstants.MotionType.AIVF, "SemInputMotionVolumeForce");
            this.availableSettings.put(
                    2, new SettingInformation(SemInputConstants.MotionType.AIVF, 1));
            this.availableSettings.put(
                    Integer.valueOf(SemInputSettingObserver.HandlerMessage.LONG_PRESS_TIMEOUT),
                    new SettingInformation(SemInputConstants.MotionType.AIVF, 0));
            this.availableSettings.put(
                    Integer.valueOf(SemInputSettingObserver.HandlerMessage.TAP_DURATION_ENABLED),
                    new SettingInformation(SemInputConstants.MotionType.AIVF, 0));
        }
        if (SemInputFeatures.SUPPORT_APD) {
            this.availableMotions.put(
                    SemInputConstants.MotionType.APD, "SemInputMotionPocketDetect");
        }
        if (SemInputFeatures.SUPPORT_AWD) {
            this.availableMotions.put(SemInputConstants.MotionType.AWD, "SemInputMotionAWD");
            Log.i(this.TAG, "prepare: SUPPORT_AWD: default: 1");
            this.availableSettings.put(
                    81, new SettingInformation(SemInputConstants.MotionType.AWD, 1));
        }
    }

    public void setExternalEventRegister(SemInputExternal.IExternalEventRegister iregister) {
        this.externalEventRegister = iregister;
    }

    public void setRawdataService(SemInputDeviceRawdataService service) {
        this.rawdataService = service;
    }

    public boolean enableRawdataService(SemInputConstants.MotionType motionType) {
        if (this.rawdataService != null) {
            boolean result = this.rawdataService.enableService(2, 1 << motionType.toInt());
            if (!result) {
                this.eventDumpsys.createDataAndAddQueue(
                        motionType + "++: failed to enable RawdataService");
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean disableRawdataService(SemInputConstants.MotionType motionType) {
        if (this.rawdataService != null) {
            return this.rawdataService.disableService(2, 1 << motionType.toInt());
        }
        return false;
    }

    public ArrayList<String> getMotionClients(SemInputConstants.MotionType motionType) {
        InputMotion inputMotion = this.motionList.get(motionType);
        if (inputMotion == null) {
            return null;
        }
        ArrayList<String> clients = new ArrayList<>();
        synchronized (inputMotion.clients) {
            Iterator it = inputMotion.clients.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                clients.add(str);
            }
        }
        return clients;
    }

    public boolean isSupport() {
        return this.isSupport;
    }

    public void prepare(int feature) {
        int channelX;
        String log;
        if (this.isSupport) {
            Log.e(this.TAG, "prepare: already called");
            return;
        }
        if (this.rawdataService == null) {
            Log.e(this.TAG, "prepare: rawdataService is null");
            this.bootingDump.append("- rawdataService is null\n");
            return;
        }
        int channelX2 = this.rawdataService.getChannelX();
        int channelY = this.rawdataService.getChannelY();
        int rawLength = this.rawdataService.getRawdataLength();
        for (Map.Entry<SemInputConstants.MotionType, String> element :
                this.availableMotions.entrySet()) {
            InputMotion inputMotion = new InputMotion(element.getKey());
            if (!inputMotion.motionType.isFeatureEnabled(feature)) {
                String log2 = inputMotion.motionType + ": not support at support_feature";
                Log.i(this.TAG, log2);
                this.bootingDump.append("- " + log2 + "\n");
            } else {
                try {
                    Class classObject = Class.forName(PACKAGE_NAME + element.getValue());
                    Constructor constructor =
                            classObject.getConstructor(
                                    Context.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                    inputMotion.motion =
                            (SemInputMotion)
                                    constructor.newInstance(
                                            this.context,
                                            Integer.valueOf(channelX2),
                                            Integer.valueOf(channelY),
                                            Integer.valueOf(rawLength));
                    inputMotion.motion.setExternalEventRegister(this.externalEventRegister);
                    inputMotion.motion.setCommandOperator(this.commandOperator);
                    inputMotion.isAvailable = inputMotion.motion.prepare();
                    if (inputMotion.isAvailable) {
                        this.isSupport = true;
                        log = element.getKey() + ": available";
                        this.motionList.put(element.getKey(), inputMotion);
                        for (Map.Entry<Integer, Handler> settings :
                                inputMotion.motion.getSettingHandlers().entrySet()) {
                            Class classObject2 = classObject;
                            channelX = channelX2;
                            try {
                                this.settingHandlerMap.put(settings.getKey(), settings.getValue());
                                classObject = classObject2;
                                channelX2 = channelX;
                            } catch (InvocationTargetException e) {
                                e = e;
                                Throwable throwable = e.getCause();
                                log = throwable.toString();
                                Log.i(this.TAG, log);
                                this.bootingDump.append("- " + log + "\n");
                                channelX2 = channelX;
                            } catch (Exception e2) {
                                e = e2;
                                SemInputDeviceManagerService.loggingException(
                                        this.TAG, "prepare:" + element.getValue(), e);
                                log = element.getKey() + ": " + e;
                                Log.i(this.TAG, log);
                                this.bootingDump.append("- " + log + "\n");
                                channelX2 = channelX;
                            }
                        }
                        channelX = channelX2;
                    } else {
                        try {
                            log = element.getKey() + ": prepare failed";
                            inputMotion.motion.destroy();
                            channelX = channelX2;
                        } catch (InvocationTargetException e3) {
                            e = e3;
                            channelX = channelX2;
                            Throwable throwable2 = e.getCause();
                            log = throwable2.toString();
                            Log.i(this.TAG, log);
                            this.bootingDump.append("- " + log + "\n");
                            channelX2 = channelX;
                        } catch (Exception e4) {
                            e = e4;
                            channelX = channelX2;
                            SemInputDeviceManagerService.loggingException(
                                    this.TAG, "prepare:" + element.getValue(), e);
                            log = element.getKey() + ": " + e;
                            Log.i(this.TAG, log);
                            this.bootingDump.append("- " + log + "\n");
                            channelX2 = channelX;
                        }
                    }
                } catch (InvocationTargetException e5) {
                    e = e5;
                    channelX = channelX2;
                } catch (Exception e6) {
                    e = e6;
                    channelX = channelX2;
                }
                Log.i(this.TAG, log);
                this.bootingDump.append("- " + log + "\n");
                channelX2 = channelX;
            }
        }
        if (!this.isSupport) {
            Log.e(this.TAG, "prepare: all motions are not supported");
            return;
        }
        registerSettingObserver();
        registerDeliveryInformaton();
        if (SemInputFeatures.SUPPORT_AIVF) {
            setExtraForAIVF();
        }
        if (SemInputFeatures.SUPPORT_APD) {
            setExtraForAPD();
        }
        setExtraForStream();
    }

    private void setExtraForAIVF() {
        InputMotion inputMotion = this.motionList.get(SemInputConstants.MotionType.AIVF);
        if (inputMotion != null) {
            inputMotion.motion.setRawdataService(this.rawdataService);
            inputMotion.motion.setMotionController(this);
        }
    }

    private void setExtraForAPD() {
        InputMotion inputMotion = this.motionList.get(SemInputConstants.MotionType.APD);
        if (inputMotion != null) {
            inputMotion.settingOn = true;
        }
    }

    private void setExtraForStream() {
        InputMotion inputMotion = this.motionList.get(SemInputConstants.MotionType.STREAM);
        if (inputMotion != null) {
            inputMotion.settingOn = true;
            inputMotion.enable(this.TAG, null);
        }
    }

    private void registerSettingObserver() {
        Log.d(this.TAG, "registerSettingObserver");
        this.settingHandler = new SettingHandler(Looper.myLooper());
        this.settingObserver = SemInputSettingObserver.getInstance(this.context);
        for (Map.Entry<Integer, SettingInformation> element : this.availableSettings.entrySet()) {
            InputMotion inputMotion = this.motionList.get(element.getValue().type);
            if (inputMotion != null && inputMotion.isAvailable) {
                Handler handler = this.settingHandlerMap.get(element.getKey());
                if (handler != null) {
                    this.settingObserver.addObserver(
                            handler, element.getKey().intValue(), element.getValue().defaultValue);
                } else {
                    this.settingObserver.addObserver(
                            this.settingHandler,
                            element.getKey().intValue(),
                            element.getValue().defaultValue);
                }
                this.bootingDump.append("- Setting " + element.getKey() + " registered\n");
                Log.d(this.TAG, "registerSettingObserver: " + element.getKey());
            }
        }
    }

    private void registerDeliveryInformaton() {
        Log.i(this.TAG, "registerDeliveryInformaton");
        this.reportInformationList.clear();
        if (SemInputFeatures.SUPPORT_PALMSWIPE) {
            LinkedList handedgelist = new LinkedList();
            for (InputMotion element : this.motionList.values()) {
                String info = element.motion.needReportInformation();
                if (info != null) {
                    Log.i(this.TAG, "registerDeliveryInformaton: return: " + info);
                    if (info.equals(SemInputDeviceManager.REPORT_INFO_HANDEDGE)) {
                        handedgelist.add(element);
                    }
                }
            }
            if (!handedgelist.isEmpty()) {
                this.reportInformationList.put(
                        SemInputDeviceManager.REPORT_INFO_HANDEDGE, handedgelist);
            }
        }
    }

    public void destroy() {
        Log.d(this.TAG, "destroy");
        for (InputMotion element : this.motionList.values()) {
            if (element.isAvailable) {
                element.motion.destroy();
            }
        }
    }

    public void restart() {
        Log.d(this.TAG, "restart");
        for (InputMotion element : this.motionList.values()) {
            if (element.settingOn && element.getClientsSize() >= 1) {
                element.motion.restart();
            }
        }
        this.isScreenOn = true;
    }

    public void pause() {
        this.isScreenOn = false;
        for (InputMotion element : this.motionList.values()) {
            if (element.settingOn && element.getClientsSize() >= 1) {
                element.motion.pause();
            }
        }
    }

    public boolean enableMotion(SemInputConstants.MotionType motionType, String client) {
        return enableMotion(motionType, client, null);
    }

    public boolean enableMotion(
            SemInputConstants.MotionType motionType, String client, IBinder binder) {
        InputMotion inputMotion = this.motionList.get(motionType);
        if (inputMotion == null) {
            Log.i(this.TAG, "enableMotion: " + motionType + " not supported, " + client);
            this.eventDumpsys.createDataAndAddQueue(motionType + "++: not supported, " + client);
            return false;
        }
        Log.i(this.TAG, "enableMotion: " + motionType + ", client=" + client);
        return inputMotion.enable(client, binder);
    }

    public boolean disableMotion(SemInputConstants.MotionType motionType, String client) {
        return disableMotion(motionType, client, null);
    }

    public boolean disableMotion(
            SemInputConstants.MotionType motionType, String client, IBinder binder) {
        InputMotion inputMotion = this.motionList.get(motionType);
        if (inputMotion == null) {
            Log.i(this.TAG, "disableMotion: " + motionType + " not supported, " + client);
            this.eventDumpsys.createDataAndAddQueue(motionType + "--: not supported, " + client);
            return false;
        }
        Log.i(this.TAG, "disableMotion: " + motionType + ", client=" + client);
        return inputMotion.disable(client, binder);
    }

    private class InputMotion {
        private static final int ERR_EXIST_CLIENT = -3;
        private static final int ERR_NOT_EXIST_CLIENT = -2;
        private static final int ERR_NOT_STARTED = -1;
        private final String DISABLE_PREFIX;
        private final String ENABLE_PREFIX;
        private final SemInputConstants.MotionType motionType;
        private SemInputMotion motion = null;
        private boolean settingOn = false;
        private boolean isAvailable = false;
        private final ArrayList<String> clients = new ArrayList<>();

        public InputMotion(SemInputConstants.MotionType motionType) {
            this.motionType = motionType;
            this.ENABLE_PREFIX = "enable: " + motionType;
            this.DISABLE_PREFIX = "disable: " + motionType;
        }

        public SemInputConstants.MotionType getType() {
            return this.motionType;
        }

        private boolean addToClients(String client) {
            synchronized (this.clients) {
                if (this.clients.contains(client) || !this.clients.add(client)) {
                    return false;
                }
                logging(this.ENABLE_PREFIX, "++: " + client, "");
                return true;
            }
        }

        private boolean removeFromClients(String client) {
            synchronized (this.clients) {
                if (!this.clients.remove(client)) {
                    return false;
                }
                logging(this.DISABLE_PREFIX, "--: " + client, "");
                return true;
            }
        }

        public int getClientsSize() {
            int size;
            synchronized (this.clients) {
                size = this.clients.size();
            }
            return size;
        }

        private boolean containsClient(String client) {
            boolean contains;
            synchronized (this.clients) {
                contains = this.clients.contains(client);
            }
            return contains;
        }

        public void printClients(PrintWriter pw) {
            synchronized (this.clients) {
                Iterator<String> it = this.clients.iterator();
                while (it.hasNext()) {
                    String str = it.next();
                    if (pw != null) {
                        pw.println("  " + str);
                    } else {
                        Log.i(SemInputMotionController.this.TAG, "  - " + str);
                    }
                }
            }
        }

        private ArrayList<String> getDestroyedClients() {
            ArrayList<String> destroyedPackages = new ArrayList<>();
            ArrayList<String> runningPackages = new ArrayList<>();
            if (getClientsSize() < 1) {
                return destroyedPackages;
            }
            List<ActivityManager.RunningTaskInfo> tasks = Collections.emptyList();
            try {
                ActivityManager activityManager =
                        (ActivityManager)
                                SemInputMotionController.this.context.getSystemService("activity");
                if (activityManager != null) {
                    tasks = activityManager.getRunningTasks(5);
                }
            } catch (SecurityException e) {
                Log.e(SemInputMotionController.this.TAG, "Activity Running Task Info Error");
            }
            for (ActivityManager.RunningTaskInfo info : tasks) {
                String packageName = info.topActivity.getPackageName();
                if (!runningPackages.contains(packageName)) {
                    runningPackages.add(packageName);
                }
            }
            synchronized (this.clients) {
                Iterator<String> it = this.clients.iterator();
                while (it.hasNext()) {
                    String aClient = it.next();
                    if (runningPackages.contains(aClient)) {
                        Log.i(
                                SemInputMotionController.this.TAG,
                                "Still Running Client: " + aClient);
                    } else {
                        Log.i(SemInputMotionController.this.TAG, "Destroyed Client: " + aClient);
                        destroyedPackages.add(aClient);
                    }
                }
            }
            return destroyedPackages;
        }

        public void handleDestroyedClients() {
            ArrayList<String> destroyedPackages = getDestroyedClients();
            if (destroyedPackages.size() < 1) {
                return;
            }
            Log.i(SemInputMotionController.this.TAG, "handleDestroyedClients");
            Iterator<String> it = destroyedPackages.iterator();
            while (it.hasNext()) {
                String destroyed = it.next();
                Log.i(
                        SemInputMotionController.this.TAG,
                        "disable - destroyed client: " + destroyed);
                disable(destroyed, null);
            }
        }

        public boolean enable(String client, IBinder binder) {
            if (!checkEnableCondition(client)) {
                return false;
            }
            if (addToClients(client)) {
                register(binder, client);
                start();
                return true;
            }
            handleErrorEnable(client);
            return false;
        }

        private boolean checkEnableCondition(String client) {
            if (this.settingOn && this.isAvailable && this.motion != null) {
                return true;
            }
            logging(
                    this.ENABLE_PREFIX,
                    "++: not available" + (this.settingOn ? "" : " (setting off)"),
                    client);
            return false;
        }

        private void register(IBinder binder, String client) {
            this.motion.registerListener(binder, client);
        }

        private void start() {
            if (getClientsSize() == 1) {
                Log.i(SemInputMotionController.this.TAG, this.ENABLE_PREFIX + "++: start");
                this.motion.start();
                if (SemInputMotionController.this.isScreenOn) {
                    this.motion.restart();
                } else {
                    this.motion.pause();
                }
                SemInputMotionController.this.enableRawdataService(this.motionType);
                return;
            }
            Log.d(SemInputMotionController.this.TAG, this.ENABLE_PREFIX + "++: restart");
            if (this.motion.isPaused()) {
                this.motion.restart();
            }
            printClients(null);
        }

        private void handleErrorEnable(String client) {
            if (containsClient(client)) {
                if (this.motion.isPaused()) {
                    this.motion.restart();
                }
                logging(this.ENABLE_PREFIX, "++: failed, same client " + client, "restart");
            } else {
                logging(this.ENABLE_PREFIX, "++: failed to add client " + client, "");
            }
            printClients(null);
        }

        public boolean disable(String client, IBinder binder) {
            if (removeFromClients(client)) {
                stop();
                unregister(binder, client);
                return true;
            }
            handleErrorDisable(client);
            return false;
        }

        private void unregister(IBinder binder, String client) {
            this.motion.unregisterListener(binder, client);
        }

        private void stop() {
            if (getClientsSize() == 0) {
                Log.i(SemInputMotionController.this.TAG, this.DISABLE_PREFIX + "--: stop");
                SemInputMotionController.this.disableRawdataService(this.motionType);
                this.motion.stop();
            } else {
                Log.i(
                        SemInputMotionController.this.TAG,
                        this.DISABLE_PREFIX + "--: still enabled: " + getClientsSize());
                printClients(null);
            }
        }

        private void handleErrorDisable(String client) {
            if (getClientsSize() == 0) {
                logging(this.DISABLE_PREFIX, "--: not started", client);
            } else {
                logging(this.DISABLE_PREFIX, "--: no such client " + client, "");
            }
            printClients(null);
        }

        private void logging(String prefix, String message, String append) {
            Log.d(SemInputMotionController.this.TAG, prefix + message);
            SemInputMotionController.this.eventDumpsys.createDataAndAddQueue(
                    this.motionType + message + (append.isEmpty() ? "" : ", " + append));
        }
    }

    public void deliveryInformation(String data) {
        if (data.contains(SemInputDeviceManager.REPORT_INFO_HANDEDGE)) {
            LinkedList<InputMotion> list =
                    this.reportInformationList.get(SemInputDeviceManager.REPORT_INFO_HANDEDGE);
            if (list.isEmpty()) {
                return;
            }
            Iterator<InputMotion> it = list.iterator();
            while (it.hasNext()) {
                InputMotion element = it.next();
                element.motion.reportInformation(data);
            }
        }
    }

    public void deliveryRawdata(int[] rawdata) {
        for (InputMotion element : this.motionList.values()) {
            if (element.settingOn && element.getClientsSize() >= 1) {
                element.motion.deliveryRawdata(rawdata);
            }
        }
    }

    public int setMotionControl(String subtype, int control) {
        if (subtype == null) {
            Log.i(this.TAG, "setMotionControl: subtype is null " + control);
            return -4;
        }
        int ret = -3;
        for (InputMotion element : this.motionList.values()) {
            if (element.isAvailable
                    && (SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL.equals(subtype)
                            || subtype.contains(element.motionType.getName()))) {
                element.motion.setMotionControl(subtype, control);
                ret = 0;
            }
        }
        return ret;
    }

    public int isEnableMotion(SemInputConstants.MotionType motionType) {
        InputMotion inputMotion = this.motionList.get(motionType);
        if (inputMotion == null || !inputMotion.settingOn || inputMotion.getClientsSize() < 1) {
            Log.d(this.TAG, "isEnableMotion: " + motionType + " is disabled");
            return 0;
        }
        Log.d(this.TAG, "isEnableMotion: " + motionType + " is enabled");
        return 1;
    }

    public int getMotionControl(String subtype) {
        if (subtype == null) {
            Log.i(this.TAG, "getMotionControl: subtype is null");
            return -4;
        }
        for (InputMotion element : this.motionList.values()) {
            if (subtype.contains(element.motionType.getName()) && element.isAvailable) {
                return element.motion.getMotionControl(subtype);
            }
        }
        return -3;
    }

    public void dump(PrintWriter pw) {
        pw.println("dumping " + this.TAG);
        pw.print(this.bootingDump.toString());
        for (InputMotion element : this.motionList.values()) {
            if (element.motion != null) {
                pw.println("");
                element.motion.dump(pw);
                pw.println(
                        "- available:"
                                + element.isAvailable
                                + ", setting:"
                                + element.settingOn
                                + ", enabled:"
                                + element.getClientsSize());
                pw.println("- clients list");
                element.printClients(pw);
                pw.println("- listener list");
                element.motion.printListeners(pw);
            }
        }
    }

    public void dumpEvents(PrintWriter pw) {
        pw.println("- " + this.TAG + " event data: max " + this.eventDumpsys.getMaxQueueSize());
        for (String data : this.eventDumpsys.getQueue()) {
            pw.println("  " + data);
        }
        pw.println("  end " + this.TAG + " event");
        for (InputMotion element : this.motionList.values()) {
            if (element.motion != null && element.isAvailable) {
                pw.println("");
                element.motion.dumpEvents(pw);
            }
        }
    }

    private static class SettingInformation {
        private final int defaultValue;
        private final SemInputConstants.MotionType type;

        public SettingInformation(SemInputConstants.MotionType type, int defaultValue) {
            this.type = type;
            this.defaultValue = defaultValue;
        }
    }

    private class SettingHandler extends Handler implements SemInputSettingObserver.HandlerMessage {
        SettingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setMotions(SemInputConstants.MotionType.PALM_MUTE, msg.arg1);
                    Log.i(
                            SemInputMotionController.this.TAG,
                            "SettingHandler PALM_MUTE: " + msg.arg1);
                    break;
                case 2:
                    setMotions(SemInputConstants.MotionType.AIVF, msg.arg1);
                    Log.i(SemInputMotionController.this.TAG, "SettingHandler AIVF: " + msg.arg1);
                    break;
                case 3:
                    setMotions(SemInputConstants.MotionType.PALM_SWIPE, msg.arg1);
                    Log.i(
                            SemInputMotionController.this.TAG,
                            "SettingHandler PALM_SWIPE: " + msg.arg1);
                    break;
                case SemInputSettingObserver.HandlerMessage.AUTO_WATER_DETECTION /* 81 */:
                    setMotions(SemInputConstants.MotionType.AWD, msg.arg1);
                    Log.i(SemInputMotionController.this.TAG, "SettingHandler AWD: " + msg.arg1);
                    break;
                default:
                    Log.d(SemInputMotionController.this.TAG, "SettingHandler: " + msg);
                    break;
            }
        }

        private void setMotions(SemInputConstants.MotionType type, int value) {
            InputMotion inputMotion =
                    (InputMotion) SemInputMotionController.this.motionList.get(type);
            if (inputMotion != null) {
                Log.i(SemInputMotionController.this.TAG, "setMotions: " + type + ", " + value);
                inputMotion.settingOn = value != 0;
            }
        }
    }
}
