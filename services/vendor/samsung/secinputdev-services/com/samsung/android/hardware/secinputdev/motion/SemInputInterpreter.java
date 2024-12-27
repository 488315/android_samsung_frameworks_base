package com.samsung.android.hardware.secinputdev.motion;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.SemInputFeatures;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.TensorFlowLite;

import java.io.File;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/* loaded from: classes.dex */
public class SemInputInterpreter {
    public static final int MODE_DISABLE = 0;
    public static final int MODE_ENABLE = 1;
    public static final int MODE_ENABLE_AGAIN = 3;
    public static final int MODE_ENABLE_AGAIN_GPU = 4;
    public static final int MODE_ENABLE_AGAIN_NO_XNNPACK = 5;
    public static final int MODE_ENABLE_NO_PREDICT = 2;
    private static final String TAG = "SemInputInterpreter";
    private File fileModel;
    TensorInformation inputTensor;
    private Interpreter interpreter;
    private MappedByteBuffer mappedBuffer;
    private Map<Integer, Object> outputMap;
    TensorInformation outputTensor;
    private Semaphore semaphore;

    private static class TensorInformation {
        public int count;
        public Object[] object;
        public int[] size;
        public DataType[] type;

        private TensorInformation() {}
    }

    public SemInputInterpreter(MappedByteBuffer buffer) {
        this.interpreter = null;
        this.mappedBuffer = null;
        this.fileModel = null;
        this.outputMap = new HashMap();
        if (buffer == null) {
            Log.i(TAG, "MappedByteBuffer is null");
        }
        this.mappedBuffer = buffer;
        initialize();
    }

    public SemInputInterpreter(File model) {
        this.interpreter = null;
        this.mappedBuffer = null;
        this.fileModel = null;
        this.outputMap = new HashMap();
        if (model == null) {
            Log.i(TAG, "file is null");
        }
        this.fileModel = model;
        initialize();
    }

    private void initialize() {
        this.semaphore = new Semaphore(1);
        this.inputTensor = new TensorInformation();
        this.outputTensor = new TensorInformation();
    }

    public boolean create(int setting) {
        if (!SemInputFeatures.SUPPORT_TFLITE) {
            Log.i(TAG, "No Support TFLITE: mode " + setting);
            return false;
        }
        if (setting == 2) {
            Log.i(TAG, "Not necessary interpreter");
            return true;
        }
        Interpreter.Options options = makeOptions(setting);
        if (options == null) {
            Log.i(TAG, "Interpreter.Options is null");
            return false;
        }
        try {
            this.semaphore.acquire();
            if (this.interpreter != null) {
                this.semaphore.release();
                close();
                this.semaphore.acquire();
            }
            if (this.mappedBuffer != null) {
                this.interpreter = new Interpreter(this.mappedBuffer, options);
            } else if (this.fileModel != null) {
                this.interpreter = new Interpreter(this.fileModel, options);
            }
            Log.d(
                    TAG,
                    "createInterpreter: runtimeVersion "
                            + TensorFlowLite.runtimeVersion()
                            + " schemaVersion: "
                            + TensorFlowLite.schemaVersion());
            return true;
        } catch (Throwable e) {
            try {
                SemInputDeviceManagerService.loggingThrowable(TAG, "create: ", e);
                return false;
            } finally {
                this.semaphore.release();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0112 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0113 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int[] getInputIndex(java.lang.String[] r13) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.hardware.secinputdev.motion.SemInputInterpreter.getInputIndex(java.lang.String[]):int[]");
    }

    public int getInputSize(int index) {
        if (index >= this.inputTensor.count) {
            Log.i(TAG, "wrong index: " + index);
            return 0;
        }
        int size = this.inputTensor.size[index];
        switch (this.inputTensor.type[index]) {
            case FLOAT32:
                size /= 4;
                break;
            case INT64:
                size /= 8;
                break;
            case INT32:
                size /= 4;
                break;
        }
        Log.i(TAG, "getInputSize: " + size);
        return size;
    }

    public void setInputObject(int index, Object object) {
        if (index >= this.inputTensor.count) {
            Log.i(TAG, "wrong index: " + index);
        } else {
            this.inputTensor.object[index] = object;
        }
    }

    public int[] getOutputIndex(String[] opNames) {
        int tensorOutputCount;
        if (this.interpreter == null) {
            return null;
        }
        int[] indices = null;
        try {
            this.semaphore.acquire();
            tensorOutputCount = this.interpreter.getOutputTensorCount();
            Log.d(TAG, "getOutputTensorCount: " + tensorOutputCount);
        } finally {
            try {
                return indices;
            } finally {
            }
        }
        if (opNames.length != tensorOutputCount) {
            Log.d(TAG, "size doesn't match: " + opNames.length);
            return null;
        }
        indices = new int[tensorOutputCount];
        int count = 0;
        this.outputTensor.count = tensorOutputCount;
        this.outputTensor.size = new int[tensorOutputCount];
        this.outputTensor.type = new DataType[tensorOutputCount];
        int length = opNames.length;
        int i = 0;
        while (i < length) {
            String input = opNames[i];
            int index = this.interpreter.getOutputIndex(input);
            Log.d(TAG, " getOutputIndex: " + input + " index: " + index);
            Tensor tensor = this.interpreter.getOutputTensor(index);
            this.outputTensor.size[index] = tensor.numBytes();
            this.outputTensor.type[index] = tensor.dataType();
            int count2 = count + 1;
            indices[count] = index;
            Log.d(
                    TAG,
                    "tensor size: "
                            + this.outputTensor.size[index]
                            + " tensor type: "
                            + this.outputTensor.type[index]);
            i++;
            count = count2;
        }
        return indices;
    }

    public int getOutputSize(int index) {
        if (index >= this.outputTensor.count) {
            Log.i(TAG, "wrong index: " + index);
            return 0;
        }
        int size = this.outputTensor.size[index];
        switch (this.outputTensor.type[index]) {
            case FLOAT32:
                size /= 4;
                break;
            case INT64:
                size /= 8;
                break;
            case INT32:
                size /= 4;
                break;
        }
        Log.i(TAG, "getOutputSize: " + size);
        return size;
    }

    public void setOutputObject(int index, Object buffer) {
        if (index >= this.outputTensor.count) {
            Log.i(TAG, "wrong index: " + index);
        } else {
            this.outputMap.put(Integer.valueOf(index), buffer);
        }
    }

    public void run() {
        try {
            try {
                this.semaphore.acquire();
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "run: ", e);
            }
            if (this.interpreter == null) {
                return;
            }
            this.interpreter.runForMultipleInputsOutputs(this.inputTensor.object, this.outputMap);
        } finally {
            this.semaphore.release();
        }
    }

    public boolean runSignature(Map input, Map output, String signature) {
        try {
            this.semaphore.acquire();
            if (this.interpreter == null) {
                return false;
            }
            this.interpreter.runSignature(input, output, signature);
            this.semaphore.release();
            return true;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "runSignature: ", e);
            return false;
        } finally {
            this.semaphore.release();
        }
    }

    public void close() {
        try {
            try {
                this.semaphore.acquire();
                if (this.interpreter != null) {
                    this.interpreter.close();
                    this.interpreter = null;
                }
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "close: ", e);
            }
        } finally {
            this.semaphore.release();
        }
    }

    public void reset() {
        try {
            try {
                this.semaphore.acquire();
                if (this.interpreter != null) {
                    this.interpreter.resetVariableTensors();
                }
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "reset: ", e);
            }
        } finally {
            this.semaphore.release();
        }
    }

    private Interpreter.Options makeOptions(int setmode) {
        Interpreter.Options options;
        if (setmode == 4 && SemInputFeatures.SUPPORT_TFLITE_GPU) {
            options = new SemInputInterpreterOptionsGPU(1, false).get();
        } else {
            boolean xnnpack = true;
            if (setmode == 5) {
                xnnpack = false;
            }
            options = new SemInputInterpreterOptions(1, false, xnnpack).get();
        }
        if (options != null) {
            options.setCancellable(true);
        }
        return options;
    }

    private static class SemInputInterpreterOptions {
        private static final String TAG = "SemInputInterpreterOptions";
        private Interpreter.Options interpreterOptions = null;

        public SemInputInterpreterOptions(int threads, boolean nnapi, boolean xnnpack) {
            create(threads, nnapi, xnnpack);
        }

        public Interpreter.Options get() {
            return this.interpreterOptions;
        }

        public Interpreter.Options create(int threads, boolean nnapi, boolean xnnpack) {
            try {
                Log.i(
                        TAG,
                        "threads:"
                                + threads
                                + (nnapi ? " use nnapi " : " no nnapi ")
                                + (xnnpack ? "use xnnpack" : "no xnnpack"));
                this.interpreterOptions = null;
                this.interpreterOptions = new Interpreter.Options();
                this.interpreterOptions.setNumThreads(threads);
                this.interpreterOptions.setUseNNAPI(nnapi);
                this.interpreterOptions.setUseXNNPACK(xnnpack);
                Log.i(TAG, "Done");
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "InterpreterOptions create:", e);
            }
            return this.interpreterOptions;
        }
    }

    private static class SemInputInterpreterOptionsGPU {
        private static final String TAG = "SemInputInterpreterOptionsGPU";

        public SemInputInterpreterOptionsGPU(int threads, boolean nnapi) {
            Log.i(TAG, "No support");
        }

        public Interpreter.Options get() {
            Log.i(TAG, "No support");
            return null;
        }

        public Interpreter.Options create(int threads, boolean nnapi, boolean checkCompatibility) {
            Log.i(TAG, "No support");
            return null;
        }
    }
}
