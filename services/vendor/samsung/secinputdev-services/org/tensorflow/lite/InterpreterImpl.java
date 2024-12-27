package org.tensorflow.lite;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
class InterpreterImpl implements InterpreterApi {
    NativeInterpreterWrapper wrapper;

    static class Options extends InterpreterApi.Options {
        Boolean allowBufferHandleOutput;
        Boolean allowFp16PrecisionForFp32;
        Boolean useXNNPACK;

        public Options() {}

        public Options(InterpreterApi.Options options) {
            super(options);
        }

        public Options(Options other) {
            super(other);
            this.allowFp16PrecisionForFp32 = other.allowFp16PrecisionForFp32;
            this.allowBufferHandleOutput = other.allowBufferHandleOutput;
            this.useXNNPACK = other.useXNNPACK;
        }
    }

    public InterpreterImpl(File modelFile) {
        this(modelFile, (Options) null);
    }

    public InterpreterImpl(File modelFile, Options options) {
        this.wrapper = new NativeInterpreterWrapper(modelFile.getAbsolutePath(), options);
    }

    public InterpreterImpl(ByteBuffer byteBuffer) {
        this(byteBuffer, (Options) null);
    }

    public InterpreterImpl(ByteBuffer byteBuffer, Options options) {
        this.wrapper = new NativeInterpreterWrapper(byteBuffer, options);
    }

    InterpreterImpl(NativeInterpreterWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public void run(Object input, Object output) {
        Object[] inputs = {input};
        Map<Integer, Object> outputs = new HashMap<>();
        outputs.put(0, output);
        runForMultipleInputsOutputs(inputs, outputs);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public void runForMultipleInputsOutputs(Object[] inputs, Map<Integer, Object> outputs) {
        checkNotClosed();
        this.wrapper.run(inputs, outputs);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public void allocateTensors() {
        checkNotClosed();
        this.wrapper.allocateTensors();
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public void resizeInput(int idx, int[] dims) {
        checkNotClosed();
        this.wrapper.resizeInput(idx, dims, false);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public void resizeInput(int idx, int[] dims, boolean strict) {
        checkNotClosed();
        this.wrapper.resizeInput(idx, dims, strict);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public int getInputTensorCount() {
        checkNotClosed();
        return this.wrapper.getInputTensorCount();
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public int getInputIndex(String opName) {
        checkNotClosed();
        return this.wrapper.getInputIndex(opName);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public Tensor getInputTensor(int inputIndex) {
        checkNotClosed();
        return this.wrapper.getInputTensor(inputIndex);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public int getOutputTensorCount() {
        checkNotClosed();
        return this.wrapper.getOutputTensorCount();
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public int getOutputIndex(String opName) {
        checkNotClosed();
        return this.wrapper.getOutputIndex(opName);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public Tensor getOutputTensor(int outputIndex) {
        checkNotClosed();
        return this.wrapper.getOutputTensor(outputIndex);
    }

    @Override // org.tensorflow.lite.InterpreterApi
    public Long getLastNativeInferenceDurationNanoseconds() {
        checkNotClosed();
        return this.wrapper.getLastNativeInferenceDurationNanoseconds();
    }

    int getExecutionPlanLength() {
        checkNotClosed();
        return this.wrapper.getExecutionPlanLength();
    }

    @Override // org.tensorflow.lite.InterpreterApi, java.lang.AutoCloseable
    public void close() {
        if (this.wrapper != null) {
            this.wrapper.close();
            this.wrapper = null;
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    void checkNotClosed() {
        if (this.wrapper == null) {
            throw new IllegalStateException(
                    "Internal error: The Interpreter has already been closed.");
        }
    }
}
