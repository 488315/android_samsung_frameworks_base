package com.samsung.android.hardware.secinputdev.motion;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.ISemInputMotionCallback;
import com.samsung.android.hardware.secinputdev.SemInputCommandInterface;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.SemInputDeviceRawdataService;
import com.samsung.android.hardware.secinputdev.SemInputFeatures;
import com.samsung.android.hardware.secinputdev.SemInputMotionController;
import com.samsung.android.hardware.secinputdev.SemInputMotionEventDispatcher;
import com.samsung.android.hardware.secinputdev.external.SemInputExternal;

import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public abstract class SemInputMotion {
    protected static final int FINGER_MOVE = 2;
    protected static final int FINGER_PRESS = 1;
    protected static final int FINGER_RELEASE = 3;
    protected static final String MODEL_INPUT_LAYER_NAME = "serving_default_input_1:0";
    protected static final String MODEL_OUTPUT_LAYER_NAME = "StatefulPartitionedCall:0";
    protected static final int RAWDATA_POSTFIX_LENGTH = 1;
    protected static final int RAWDATA_PREFIX_FINGER_STATE = 3;
    protected static final int RAWDATA_PREFIX_LENGTH = 4;
    protected static final int RAWDATA_PREFIX_UNUSED0 = 0;
    protected static final int RAWDATA_PREFIX_UNUSED1 = 1;
    protected static final int RAWDATA_PREFIX_UNUSED2 = 2;
    private static final String STATIC_TAG = "SemInputMotion";
    private final String MOTION_NAME;
    protected final int PHYS_CHANNEL_X;
    protected final int PHYS_CHANNEL_Y;
    protected final int RAWDATA_LENGTH;
    private final String TAG;
    protected boolean isAvailable;
    private SemInputMLModel modelML;
    protected final StringBuilder bootingDump = new StringBuilder();
    private SemInputMotionEventDispatcher dispatcher = null;
    protected HandlerThread deliveryHandlerThread = null;
    protected Handler deliveryHandler = null;
    protected boolean deliveryPause = true;
    protected Handler settingHandler = null;
    protected HashMap<Integer, Handler> settingHandlerMap = new HashMap<>();
    protected SemInputDeviceRawdataService rawdataService = null;
    protected SemInputMotionController motionController = null;
    protected SemInputExternal.IExternalEventRegister externalEventRegister = null;
    protected SemInputCommandInterface commandOperator = null;
    private final RemoteCallbackList<ISemInputMotionCallback> eventListeners =
            new RemoteCallbackList<>();
    private final Map<String, ISemInputMotionCallback> clientCallback = new HashMap();

    protected abstract void delivery(int[] iArr);

    protected abstract void destroyDelivery();

    public abstract void dump(PrintWriter printWriter);

    public abstract int getMotionControl(String str);

    protected abstract void pauseDelivery();

    protected abstract void prepareSettings();

    protected abstract boolean prepareTensorflow(MappedByteBuffer mappedByteBuffer);

    protected abstract boolean restartDelivery();

    public abstract void setMotionControl(String str, int i);

    protected abstract void startDelivery();

    protected abstract void stopDelivery();

    public SemInputMotion(
            String tag, String fileName, int channelX, int channelY, int rawdataLength) {
        this.isAvailable = false;
        this.TAG = tag;
        this.PHYS_CHANNEL_X = channelX;
        this.PHYS_CHANNEL_Y = channelY;
        this.RAWDATA_LENGTH = rawdataLength;
        this.MOTION_NAME = this.TAG.substring(STATIC_TAG.length());
        StringBuilder log = new StringBuilder("- x:");
        log.append(this.PHYS_CHANNEL_X);
        log.append(", y:");
        log.append(this.PHYS_CHANNEL_Y);
        log.append(", rawdata length:");
        log.append(this.RAWDATA_LENGTH);
        if (this.PHYS_CHANNEL_X <= 0 || this.PHYS_CHANNEL_Y <= 0) {
            this.isAvailable = false;
            this.bootingDump.append(((Object) log) + " is invalid\n");
        } else {
            this.isAvailable = true;
            this.modelML = new SemInputMLModel(fileName);
        }
        Log.d(this.TAG, ((Object) log) + ", file:" + fileName);
    }

    public void setExternalEventRegister(
            SemInputExternal.IExternalEventRegister externalEventRegister) {
        this.externalEventRegister = externalEventRegister;
    }

    public void setCommandOperator(SemInputCommandInterface commandOperator) {
        this.commandOperator = commandOperator;
    }

    public final boolean prepare() {
        if (!this.isAvailable) {
            return this.isAvailable;
        }
        this.isAvailable = prepareTensorflow(this.modelML.getBuffer());
        this.bootingDump.append(this.modelML.getLog());
        if (this.isAvailable) {
            if (SemInputFeatures.IS_WEAROS) {
                this.deliveryHandlerThread =
                        new HandlerThread(this.MOTION_NAME + "HandlerThread", -2);
            } else {
                this.deliveryHandlerThread =
                        new HandlerThread(this.MOTION_NAME + "HandlerThread", -8);
            }
            this.deliveryHandlerThread.start();
            this.deliveryHandler = new Handler(this.deliveryHandlerThread.getLooper());
            prepareSettings();
        }
        return this.isAvailable;
    }

    public final HashMap<Integer, Handler> getSettingHandlers() {
        return this.settingHandlerMap;
    }

    public final void start() {
        startDelivery();
    }

    public final void pause() {
        if (!this.deliveryPause) {
            Log.d(this.TAG, "paused");
        }
        this.deliveryPause = true;
        pauseDelivery();
    }

    public final boolean isPaused() {
        return this.deliveryPause;
    }

    public final void restart() {
        if (this.deliveryPause) {
            Log.d(this.TAG, "restart");
        }
        if (restartDelivery()) {
            this.deliveryPause = false;
        }
    }

    public final void deliveryRawdata(final int[] rawdata) {
        if (!this.isAvailable) {
            Log.e(this.TAG, "deliveryRawdata: not available");
        } else if (!this.deliveryPause && this.deliveryHandler != null) {
            this.deliveryHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.hardware.secinputdev.motion.SemInputMotion.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SemInputMotion.this.delivery(rawdata);
                        }
                    });
        }
    }

    public final void stop() {
        stopDelivery();
    }

    public void destroy() {
        if (this.deliveryHandlerThread != null) {
            this.deliveryHandlerThread.quitSafely();
            this.deliveryHandlerThread.interrupt();
            this.deliveryHandlerThread = null;
            this.deliveryHandler = null;
        }
        destroyDelivery();
    }

    public final void setMotionController(SemInputMotionController controller) {
        this.motionController = controller;
    }

    public final void setRawdataService(SemInputDeviceRawdataService service) {
        this.rawdataService = service;
    }

    public final void registerListener(IBinder binder, String client) {
        if (binder == null) {
            return;
        }
        ISemInputMotionCallback callback = ISemInputMotionCallback.Stub.asInterface(binder);
        if (callback == null) {
            Log.e(this.TAG, "Callback/Binder is NULL");
            return;
        }
        ISemInputMotionCallback findCallback = this.clientCallback.get(client);
        if (findCallback != null) {
            Log.d(this.TAG, "registerListener: " + client + " is already registered");
            synchronized (this.eventListeners) {
                this.eventListeners.unregister(callback);
            }
            this.clientCallback.remove(client, callback);
        }
        synchronized (this.eventListeners) {
            this.eventListeners.register(callback);
        }
        this.clientCallback.put(client, callback);
        Log.d(this.TAG, "registerListener: " + client + ", done");
    }

    public final void unregisterListener(IBinder binder, String client) {
        if (binder == null) {
            return;
        }
        ISemInputMotionCallback callback = ISemInputMotionCallback.Stub.asInterface(binder);
        if (callback == null) {
            Log.e(this.TAG, "Callback/Binder is NULL");
            return;
        }
        ISemInputMotionCallback findCallback = this.clientCallback.get(client);
        if (findCallback != null) {
            synchronized (this.eventListeners) {
                this.eventListeners.unregister(callback);
            }
            this.clientCallback.remove(client, callback);
        } else {
            Log.d(this.TAG, "unregisterListener: " + client + " is not registered");
        }
        Log.d(this.TAG, "unregisterListener: " + client + ", done");
    }

    protected final void notify(int result) {
        synchronized (this.eventListeners) {
            int N = this.eventListeners.beginBroadcast();
            for (int i = 0; i < N; i++) {
                try {
                    this.eventListeners.getBroadcastItem(i).onEventChanged(result);
                } catch (Exception e) {
                    SemInputDeviceManagerService.loggingException(this.TAG, "notify:broadcast", e);
                }
            }
            this.eventListeners.finishBroadcast();
        }
        Log.d(this.TAG, "notify: " + result);
    }

    public final void printListeners(final PrintWriter pw) {
        this.clientCallback.forEach(
                new BiConsumer() { // from class:
                                   // com.samsung.android.hardware.secinputdev.motion.SemInputMotion$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        pw.println("  " + ((String) obj));
                    }
                });
    }

    public void dumpEvents(PrintWriter pw) {}

    public void reportInformation(String infomation) {}

    public String needReportInformation() {
        return null;
    }

    protected final void registerInputReceiver(
            final Context context,
            final SemInputMotionEventDispatcher.SemInputMotionEventListener listener) {
        if (this.deliveryHandler != null) {
            this.deliveryHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.hardware.secinputdev.motion.SemInputMotion$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemInputMotion.this.lambda$registerInputReceiver$1(context, listener);
                        }
                    });
        } else {
            Log.e(this.TAG, "registerInputReceiver: deliveryHandler is null!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerInputReceiver$1(
            Context context, SemInputMotionEventDispatcher.SemInputMotionEventListener listener) {
        this.dispatcher = SemInputMotionEventDispatcher.getInstance(context);
        try {
            this.dispatcher.registerMotionEventListener(listener);
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "registerInputReceiver", e);
            this.dispatcher = null;
        }
    }

    protected final void unregisterInputReceiver(
            final SemInputMotionEventDispatcher.SemInputMotionEventListener listener) {
        if (this.deliveryHandler != null) {
            this.deliveryHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.hardware.secinputdev.motion.SemInputMotion$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemInputMotion.this.lambda$unregisterInputReceiver$2(listener);
                        }
                    });
        } else {
            Log.e(this.TAG, "unregisterInputReceiver: deliveryHandler is null!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterInputReceiver$2(
            SemInputMotionEventDispatcher.SemInputMotionEventListener listener) {
        if (this.dispatcher != null) {
            try {
                try {
                    this.dispatcher.unregisterMotionEventListener(listener);
                } catch (Exception e) {
                    SemInputDeviceManagerService.loggingException(
                            this.TAG, "unregisterInputReceiver", e);
                }
            } finally {
                this.dispatcher = null;
            }
        }
    }

    protected final void inputMonitorPilferPointers(
            SemInputMotionEventDispatcher.SemInputMotionEventListener listener) {
        if (this.dispatcher != null) {
            try {
                this.dispatcher.pilferPointers(listener);
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(
                        this.TAG, "inputMonitorPilferPointers", e);
            }
        }
    }
}
