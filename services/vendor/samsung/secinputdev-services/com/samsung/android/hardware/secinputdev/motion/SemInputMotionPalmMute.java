package com.samsung.android.hardware.secinputdev.motion;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowMetrics;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.SemInputDumpsysData;
import com.samsung.android.hardware.secinputdev.SemInputMotionEventDispatcher;
import com.samsung.android.hardware.secinputdev.SemInputSettingObserver;
import com.samsung.android.hardware.secinputdev.external.SemInputExternal;

import java.io.PrintWriter;
import java.nio.FloatBuffer;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemInputMotionPalmMute extends SemInputMotion {
    private static final String PERMISSION_PALM_MOTION = "com.samsung.permission.PALM_MOTION";
    private static final String TAG = "SemInputMotionPalmMute";
    private static final String modelFileName = "palm_classifier.tflite";
    private final SemInputExternal.IBroadcastReceiver broadcastListener;
    private final Context context;
    private FloatBuffer inputTensorBuffer;
    private int inputTensorIndex;
    private SemInputInterpreter interpreter;
    private boolean isGameMode;
    private boolean isPalmDown;
    private int matchedFrameNumber;
    private float maxRate;
    private final SemInputDumpsysData motionDumpsys;
    private SemInputMotionEventDispatcher.SemInputMotionEventListener motionEventListener;
    private FloatBuffer outputTensorBuffer;
    private int outputTensorIndex;
    private PalmEventFunction palmEventFunction;
    private int triggerFrameNumber;
    private float triggerThreshold;

    public SemInputMotionPalmMute(Context context, int channelX, int channelY, int rawLength) {
        super(TAG, modelFileName, channelX, channelY, rawLength);
        this.motionDumpsys = new SemInputDumpsysData(50);
        this.isPalmDown = false;
        this.maxRate = 0.0f;
        this.triggerFrameNumber = 2;
        this.triggerThreshold = 0.7f;
        this.matchedFrameNumber = 0;
        this.interpreter = null;
        this.palmEventFunction = null;
        this.isGameMode = false;
        this.inputTensorBuffer = null;
        this.inputTensorIndex = 0;
        this.outputTensorBuffer = null;
        this.outputTensorIndex = 0;
        this.broadcastListener =
                new SemInputExternal
                        .IBroadcastReceiver() { // from class:
                                                // com.samsung.android.hardware.secinputdev.motion.SemInputMotionPalmMute.1
                    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IBroadcastReceiver
                    public void onGameMode(String gameMode, String scanRate, String fastResponse) {
                        Log.d(SemInputMotionPalmMute.TAG, "onGameMode: " + gameMode);
                        SemInputMotionPalmMute.this.isGameMode = "1".equals(gameMode);
                        if (SemInputMotionPalmMute.this.isGameMode
                                && SemInputMotionPalmMute.this.palmEventFunction == null) {
                            SemInputMotionPalmMute.this.palmEventFunction =
                                    SemInputMotionPalmMute.this
                                    .new PalmEventFunction(SemInputMotionPalmMute.this.context);
                        }
                    }
                };
        this.motionEventListener =
                new SemInputMotionEventDispatcher
                        .SemInputMotionEventListener() { // from class:
                                                         // com.samsung.android.hardware.secinputdev.motion.SemInputMotionPalmMute.2
                    @Override // com.samsung.android.hardware.secinputdev.SemInputMotionEventDispatcher.SemInputMotionEventListener
                    public void onMotionEvent(MotionEvent event) {
                        if (SemInputMotionPalmMute.this.isGameMode
                                && !SemInputMotionPalmMute.this.deliveryPause
                                && SemInputMotionPalmMute.this.palmEventFunction != null) {
                            SemInputMotionPalmMute.this.palmEventFunction.onRecognition(event);
                        }
                    }
                };
        this.context = context;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected boolean prepareTensorflow(MappedByteBuffer mappedByteBuffer) {
        if (mappedByteBuffer == null) {
            Log.d(TAG, "prepareTensorflow: failed to load model file");
            return false;
        }
        this.interpreter = new SemInputInterpreter(mappedByteBuffer);
        if (!this.interpreter.create(1)) {
            return false;
        }
        String[] inputOp = {"serving_default_input_layer:0"};
        int[] inputIndices = this.interpreter.getInputIndex(inputOp);
        if (inputIndices == null || inputIndices.length != 1) {
            Log.d(TAG, "input tensor count is different: ");
            this.interpreter.close();
            return false;
        }
        this.inputTensorIndex = inputIndices[0];
        this.inputTensorBuffer =
                FloatBuffer.allocate(this.interpreter.getInputSize(this.inputTensorIndex));
        this.interpreter.setInputObject(this.inputTensorIndex, this.inputTensorBuffer);
        String[] outputOp = {"StatefulPartitionedCall:0"};
        int[] outIndices = this.interpreter.getOutputIndex(outputOp);
        if (outIndices == null || outIndices.length != 1) {
            Log.d(TAG, "out tensor count is different: ");
            this.interpreter.close();
            return false;
        }
        this.outputTensorIndex = outIndices[0];
        this.outputTensorBuffer =
                FloatBuffer.allocate(this.interpreter.getOutputSize(this.outputTensorIndex));
        this.interpreter.setOutputObject(this.outputTensorIndex, this.outputTensorBuffer);
        if (this.externalEventRegister != null) {
            Log.d(TAG, "externalEventRegister: BROADCAST_GAME ");
            this.externalEventRegister.registerBroadcastReceiver(
                    SemInputExternal.Event.BROADCAST_GAME, this.broadcastListener);
        } else {
            Log.d(TAG, "externalEventRegister is null ");
        }
        return true;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void prepareSettings() {
        this.settingHandler = new MySettingHandler(Looper.myLooper());
        this.settingHandlerMap.put(10, this.settingHandler);
        this.settingHandlerMap.put(11, this.settingHandler);
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void startDelivery() {
        this.maxRate = 0.0f;
        restart();
        registerInputReceiver(this.context, this.motionEventListener);
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void pauseDelivery() {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void delivery(int[] rawdata) {
        if (this.isGameMode) {
            return;
        }
        if (rawdata[3] == 1) {
            resetInterpreter();
        }
        if (this.isPalmDown) {
            if (rawdata[3] == 3) {
                sendActionPalmUp();
                return;
            }
            return;
        }
        long startTime = SystemClock.elapsedRealtimeNanos();
        float positiveRate = predict(rawdata);
        String inference = String.format("%.03f", Float.valueOf(positiveRate));
        long endTime = SystemClock.elapsedRealtimeNanos();
        long elapsedTime = Math.round((endTime - startTime) / 1000000.0d);
        Log.d(
                TAG,
                ""
                        + elapsedTime
                        + " ms, Rate: "
                        + inference
                        + " Frame: "
                        + this.matchedFrameNumber
                        + "/"
                        + this.triggerFrameNumber);
        if (Float.compare(positiveRate, this.triggerThreshold) >= 0) {
            if (!this.isPalmDown) {
                int i = this.matchedFrameNumber + 1;
                this.matchedFrameNumber = i;
                if (i >= this.triggerFrameNumber) {
                    sendActionPalmDown();
                    this.motionDumpsys.createDataAndAddQueue("palm [" + inference + "]");
                    inputMonitorPilferPointers(this.motionEventListener);
                }
            }
        } else {
            this.matchedFrameNumber = 0;
        }
        if (positiveRate > this.maxRate) {
            this.maxRate = positiveRate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendActionPalmDown() {
        Log.i(TAG, "sendActionPalmDown");
        Intent intent = new Intent("com.samsung.android.motion.PALM_DOWN");
        this.context.sendBroadcastAsUser(intent, UserHandle.ALL, PERMISSION_PALM_MOTION);
        this.isPalmDown = true;
        this.matchedFrameNumber = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendActionPalmUp() {
        Log.i(TAG, "sendActionPalmUp");
        Intent intent = new Intent("com.samsung.android.motion.PALM_UP");
        this.context.sendBroadcastAsUser(intent, UserHandle.ALL, PERMISSION_PALM_MOTION);
        this.isPalmDown = false;
        pause();
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public void setMotionControl(String subtype, int control) {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public int getMotionControl(String subtype) {
        return -1;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void stopDelivery() {
        if (Float.compare(this.maxRate, this.triggerThreshold) < 0) {
            this.motionDumpsys.createDataAndAddQueue(
                    "fail [" + String.format("%.03f", Float.valueOf(this.maxRate)) + "]");
        }
        if (this.isPalmDown) {
            sendActionPalmUp();
        }
        unregisterInputReceiver(this.motionEventListener);
        resetInterpreter();
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected boolean restartDelivery() {
        resetInterpreter();
        return true;
    }

    private void resetInterpreter() {
        if (this.interpreter != null) {
            this.interpreter.reset();
            Log.d(TAG, "interpreter reset");
        }
        this.matchedFrameNumber = 0;
        this.isPalmDown = false;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void destroyDelivery() {
        Log.d(TAG, "destroyDelivery");
        stopDelivery();
        if (this.interpreter != null) {
            this.interpreter.close();
            this.interpreter = null;
        }
    }

    private class MySettingHandler extends Handler
            implements SemInputSettingObserver.HandlerMessage {
        MySettingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    SemInputMotionPalmMute.this.setThreshold(msg.arg1);
                    break;
                case 11:
                    SemInputMotionPalmMute.this.setFrameNumber(msg.arg1);
                    break;
                default:
                    Log.d(SemInputMotionPalmMute.TAG, "SettingHandler: " + msg);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setThreshold(int value) {
        if (value < 5 || value > 10) {
            Log.i(TAG, "setThreshold: out of range " + value);
        } else {
            this.triggerThreshold = value * 0.1f;
            Log.i(TAG, "setThreshold: changed " + this.triggerThreshold);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrameNumber(int value) {
        if (value < 1 || value >= 10) {
            Log.i(TAG, "setFrameNumber: out of range " + value);
        } else {
            this.triggerFrameNumber = value;
            Log.i(TAG, "setFrameNumber: changed " + this.triggerFrameNumber);
        }
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public void dump(PrintWriter pw) {
        pw.println("dumping SemInputMotionPalmMute");
        pw.print(this.bootingDump.toString());
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public void dumpEvents(PrintWriter pw) {
        pw.println("- PalmMute event data: max " + this.motionDumpsys.getMaxQueueSize());
        for (String data : this.motionDumpsys.getQueue()) {
            pw.println("  " + data);
        }
        pw.println("  end SemInputMotionPalmMute event");
    }

    private float predict(int[] rawdata) {
        if (this.interpreter == null || !convertToFloatBuffer(rawdata)) {
            return 0.0f;
        }
        try {
            this.outputTensorBuffer.rewind();
            this.interpreter.run();
            this.outputTensorBuffer.rewind();
            float inference = this.outputTensorBuffer.get(0);
            return inference;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "predict:output", e);
            return 0.0f;
        }
    }

    private boolean convertToFloatBuffer(int[] rawdata) {
        try {
            this.inputTensorBuffer.rewind();
            for (int ii = 0; ii < this.PHYS_CHANNEL_X * this.PHYS_CHANNEL_Y; ii++) {
                this.inputTensorBuffer.put(ii, rawdata[ii + 4]);
            }
            this.inputTensorBuffer.rewind();
            return true;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "convertToFloatBuffer", e);
            return false;
        }
    }

    private class PalmEventFunction {
        private static final String TAG = "SemInputPalmEventFunction";
        private static final int THRESHOLD_PALM_COUNT = 1;
        private MotionEvent event;
        private boolean isPalmDownDone;
        private boolean isReady;
        private int moveCount = 0;
        private PalmCalculation palmCalculation;

        public PalmEventFunction(Context context) {
            this.isPalmDownDone = false;
            this.isReady = false;
            Log.d(TAG, "create");
            this.isReady = false;
            this.isPalmDownDone = false;
            this.palmCalculation = new PalmCalculation(context);
        }

        public void onRecognition(MotionEvent mEvent) {
            if (mEvent.getPalm() == -2.0f || this.palmCalculation == null) {
                return;
            }
            this.event = mEvent;
            ready();
            int action = this.event.getAction();
            if (action == 0) {
                this.palmCalculation.initialize();
                this.isPalmDownDone = false;
            }
            if (this.isReady) {
                this.palmCalculation.update(this.event);
                process();
            }
            if (action == 1 || action == 3) {
                this.isPalmDownDone = false;
                this.isReady = false;
            }
        }

        private void ready() {
            if (this.isReady) {
                return;
            }
            int action = this.event.getAction();
            if (action == 0) {
                this.moveCount = 0;
                this.palmCalculation.updateScreenInfo();
            } else if (action == 2) {
                this.moveCount++;
            }
            if (this.palmCalculation.updateReadyPalmInfo(this.event, this.moveCount)) {
                this.isReady = true;
                Log.d(TAG, "ready");
            }
        }

        public void process() {
            int count = this.palmCalculation.getFingerCount();
            if (count < 1) {
                return;
            }
            if (!this.isPalmDownDone) {
                checkPalmDown(count);
                if (this.isPalmDownDone) {
                    SemInputMotionPalmMute.this.sendActionPalmDown();
                }
            }
            if (this.event.getAction() == 1 && this.isPalmDownDone) {
                SemInputMotionPalmMute.this.sendActionPalmUp();
                this.isReady = false;
                this.isPalmDownDone = false;
            }
        }

        private void checkPalmDown(int count) {
            this.isPalmDownDone = false;
            if (this.palmCalculation.isPalmDown()) {
                if (count == 1) {
                    if (this.palmCalculation.getSumEccen() < 2.0f) {
                        this.isPalmDownDone = true;
                        Log.d(TAG, "sumEccen: " + this.palmCalculation.getSumEccen());
                        return;
                    }
                    return;
                }
                if (this.palmCalculation.isOverVariance()) {
                    this.isPalmDownDone = true;
                    Log.d(TAG, "over variance");
                }
            }
        }
    }

    private static class PalmCalculation {
        private static final int PALM_DOWN = 1;
        private static final float THRESHOLD_MAJOR_TOUCH = 1000.0f;
        private static final int TOLERANCE_EVENT_COUNT = 50;
        private Context context;
        private int screenWidth = 0;
        private int screenHeight = 0;
        private int baseScreenWidth = 0;
        private float sumEccen = 0.0f;
        private float sumMajor = 0.0f;
        private float varianceX = 0.0f;
        private float varianceY = 0.0f;
        private boolean isPalm = false;
        private int fingerCount = 0;
        private int palmFlag = 0;
        private ArrayList<TouchPoint> touchPoints = new ArrayList<>();

        public PalmCalculation(Context context) {
            this.context = context;
            initialize();
        }

        public void initialize() {
            this.fingerCount = 0;
            this.sumEccen = 0.0f;
            this.sumMajor = 0.0f;
            this.palmFlag = 0;
            this.touchPoints.clear();
            this.baseScreenWidth = Math.min(this.screenWidth, this.screenHeight);
        }

        public float getSumEccen() {
            return this.sumEccen;
        }

        public int getFingerCount() {
            return this.fingerCount;
        }

        public boolean isPalmDown() {
            return (this.palmFlag & 1) == 1 || this.sumMajor >= THRESHOLD_MAJOR_TOUCH;
        }

        public boolean isOverVariance() {
            int base = this.baseScreenWidth / (this.fingerCount + 3);
            return this.varianceX > ((float) base) && this.varianceX > ((float) base);
        }

        public void update(MotionEvent event) {
            initialize();
            this.fingerCount = event.getPointerCount();
            int sumX = 0;
            int sumY = 0;
            float sumMinor = 0.0f;
            for (int i = 0; i < this.fingerCount; i++) {
                float x = event.getX(i);
                float y = event.getY(i);
                this.touchPoints.add(new TouchPoint(x, y));
                sumX = (int) (sumX + x);
                sumY = (int) (sumY + y);
                this.sumMajor += event.getTouchMajor(i);
                sumMinor += event.getTouchMinor(i);
                this.palmFlag |= (int) event.getPalm(i);
            }
            float meanX = sumX / this.fingerCount;
            float meanY = sumY / this.fingerCount;
            this.sumEccen = this.sumMajor / sumMinor;
            this.varianceX = 0.0f;
            this.varianceY = 0.0f;
            Iterator<TouchPoint> it = this.touchPoints.iterator();
            while (it.hasNext()) {
                TouchPoint t = it.next();
                this.varianceX =
                        (float) (this.varianceX + Math.sqrt((t.x - meanX) * (t.x - meanX)));
                this.varianceY =
                        (float) (this.varianceY + Math.sqrt((t.y - meanY) * (t.y - meanY)));
            }
            this.varianceX /= this.fingerCount;
            this.varianceY /= this.fingerCount;
        }

        public boolean updateReadyPalmInfo(MotionEvent event, int move) {
            int fingerPointCnt = event.getPointerCount();
            this.isPalm = false;
            this.sumMajor = 0.0f;
            int i = 0;
            while (true) {
                if (i >= fingerPointCnt) {
                    break;
                }
                if (event.getPalm(i) == 1.0f) {
                    this.isPalm = true;
                    break;
                }
                this.sumMajor += event.getTouchMajor(i);
                i++;
            }
            return this.isPalm || (this.sumMajor >= THRESHOLD_MAJOR_TOUCH && move < 50);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateScreenInfo() {
            WindowMetrics mainWindowMetrics;
            WindowManager mainWindowManager =
                    (WindowManager) this.context.getSystemService(WindowManager.class);
            if (mainWindowManager != null
                    && (mainWindowMetrics = mainWindowManager.getCurrentWindowMetrics()) != null) {
                this.screenWidth = mainWindowMetrics.getBounds().width();
                this.screenHeight = mainWindowMetrics.getBounds().height();
            }
        }
    }

    private static class TouchPoint {
        float x;
        float y;

        public TouchPoint(float inX, float inY) {
            this.x = inX;
            this.y = inY;
        }
    }
}
