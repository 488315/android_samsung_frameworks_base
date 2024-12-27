package org.tensorflow.lite;

import org.tensorflow.lite.nnapi.NnApiDelegate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class NativeInterpreterWrapper implements AutoCloseable {
    private static final int ERROR_BUFFER_SIZE = 512;
    private long cancellationFlagHandle;
    private final List<Delegate> delegates;
    long errorHandle;
    private long inferenceDurationNanoseconds;
    private TensorImpl[] inputTensors;
    private Map<String, Integer> inputsIndexes;
    long interpreterHandle;
    private boolean isMemoryAllocated;
    private ByteBuffer modelByteBuffer;
    private long modelHandle;
    private boolean originalGraphHasUnresolvedFlexOp;
    private TensorImpl[] outputTensors;
    private Map<String, Integer> outputsIndexes;
    private final List<AutoCloseable> ownedDelegates;
    private Map<String, NativeSignatureRunnerWrapper> signatureRunnerMap;
    private Map<Integer, Integer> tensorToInputsIndexes;
    private Map<Integer, Integer> tensorToOutputsIndexes;

    private static native long allocateTensors(long interpreterHandle, long errorHandle);

    private static native void allowBufferHandleOutput(long interpreterHandle, boolean allow);

    private static native void allowFp16PrecisionForFp32(long interpreterHandle, boolean allow);

    private static native long createCancellationFlag(long interpreterHandle);

    private static native long createErrorReporter(int size);

    private static native long createInterpreter(
            long modelHandle, long errorHandle, int numThreads, List<Long> delegateHandles);

    private static native long createModel(String modelPathOrBuffer, long errorHandle);

    private static native long createModelWithBuffer(ByteBuffer modelBuffer, long errorHandle);

    private static native XnnpackDelegate createXNNPACKDelegate(
            long interpreterHandle, long errorHandle, int state, int numThreads);

    private static native void delete(long errorHandle, long modelHandle, long interpreterHandle);

    private static native long deleteCancellationFlag(long cancellationFlagHandle);

    private static native int getExecutionPlanLength(long interpreterHandle);

    private static native int getInputCount(long interpreterHandle);

    private static native String[] getInputNames(long interpreterHandle);

    private static native int getInputTensorIndex(long interpreterHandle, int inputIdx);

    private static native int getOutputCount(long interpreterHandle);

    private static native int getOutputDataType(long interpreterHandle, int outputIdx);

    private static native String[] getOutputNames(long interpreterHandle);

    private static native int getOutputTensorIndex(long interpreterHandle, int outputIdx);

    private static native String[] getSignatureKeys(long interpreterHandle);

    private static native boolean hasUnresolvedFlexOp(long interpreterHandle);

    private static native void resetVariableTensors(long interpreterHandle, long errorHandle);

    private static native boolean resizeInput(
            long interpreterHandle, long errorHandle, int inputIdx, int[] dims, boolean strict);

    private static native void run(long interpreterHandle, long errorHandle);

    private static native void setCancelled(
            long interpreterHandle, long cancellationFlagHandle, boolean value);

    NativeInterpreterWrapper(String modelPath) {
        this(modelPath, (InterpreterImpl.Options) null);
    }

    NativeInterpreterWrapper(ByteBuffer byteBuffer) {
        this(byteBuffer, (InterpreterImpl.Options) null);
    }

    NativeInterpreterWrapper(String modelPath, InterpreterImpl.Options options) {
        this.cancellationFlagHandle = 0L;
        this.inferenceDurationNanoseconds = -1L;
        this.isMemoryAllocated = false;
        this.originalGraphHasUnresolvedFlexOp = false;
        this.delegates = new ArrayList();
        this.ownedDelegates = new ArrayList();
        TensorFlowLite.init();
        long errorHandle = createErrorReporter(512);
        long modelHandle = createModel(modelPath, errorHandle);
        init(errorHandle, modelHandle, options);
    }

    NativeInterpreterWrapper(ByteBuffer buffer, InterpreterImpl.Options options) {
        this.cancellationFlagHandle = 0L;
        this.inferenceDurationNanoseconds = -1L;
        this.isMemoryAllocated = false;
        this.originalGraphHasUnresolvedFlexOp = false;
        this.delegates = new ArrayList();
        this.ownedDelegates = new ArrayList();
        TensorFlowLite.init();
        if (buffer == null
                || (!(buffer instanceof MappedByteBuffer)
                        && (!buffer.isDirect() || buffer.order() != ByteOrder.nativeOrder()))) {
            throw new IllegalArgumentException(
                    "Model ByteBuffer should be either a MappedByteBuffer of the model file, or a"
                        + " direct ByteBuffer using ByteOrder.nativeOrder() which contains bytes of"
                        + " model content.");
        }
        this.modelByteBuffer = buffer;
        long errorHandle = createErrorReporter(512);
        long modelHandle = createModelWithBuffer(this.modelByteBuffer, errorHandle);
        init(errorHandle, modelHandle, options);
    }

    private void init(long errorHandle, long modelHandle, InterpreterImpl.Options options) {
        if (options == null) {
            options = new InterpreterImpl.Options();
        }
        this.errorHandle = errorHandle;
        this.modelHandle = modelHandle;
        ArrayList<Long> delegateHandles = new ArrayList<>();
        this.interpreterHandle =
                createInterpreter(
                        modelHandle, errorHandle, options.getNumThreads(), delegateHandles);
        this.originalGraphHasUnresolvedFlexOp = hasUnresolvedFlexOp(this.interpreterHandle);
        addDelegates(options);
        delegateHandles.ensureCapacity(this.delegates.size());
        for (Delegate delegate : this.delegates) {
            delegateHandles.add(Long.valueOf(delegate.getNativeHandle()));
        }
        if (!delegateHandles.isEmpty()) {
            delete(0L, 0L, this.interpreterHandle);
            this.interpreterHandle =
                    createInterpreter(
                            modelHandle, errorHandle, options.getNumThreads(), delegateHandles);
        }
        if (options.allowFp16PrecisionForFp32 != null) {
            allowFp16PrecisionForFp32(
                    this.interpreterHandle, options.allowFp16PrecisionForFp32.booleanValue());
        }
        if (options.allowBufferHandleOutput != null) {
            allowBufferHandleOutput(
                    this.interpreterHandle, options.allowBufferHandleOutput.booleanValue());
        }
        if (options.isCancellable()) {
            this.cancellationFlagHandle = createCancellationFlag(this.interpreterHandle);
        }
        this.inputTensors = new TensorImpl[getInputCount(this.interpreterHandle)];
        this.outputTensors = new TensorImpl[getOutputCount(this.interpreterHandle)];
        if (options.allowFp16PrecisionForFp32 != null) {
            allowFp16PrecisionForFp32(
                    this.interpreterHandle, options.allowFp16PrecisionForFp32.booleanValue());
        }
        if (options.allowBufferHandleOutput != null) {
            allowBufferHandleOutput(
                    this.interpreterHandle, options.allowBufferHandleOutput.booleanValue());
        }
        allocateTensors(this.interpreterHandle, errorHandle);
        this.isMemoryAllocated = true;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        for (int i = 0; i < this.inputTensors.length; i++) {
            if (this.inputTensors[i] != null) {
                this.inputTensors[i].close();
                this.inputTensors[i] = null;
            }
        }
        for (int i2 = 0; i2 < this.outputTensors.length; i2++) {
            if (this.outputTensors[i2] != null) {
                this.outputTensors[i2].close();
                this.outputTensors[i2] = null;
            }
        }
        delete(this.errorHandle, this.modelHandle, this.interpreterHandle);
        deleteCancellationFlag(this.cancellationFlagHandle);
        this.errorHandle = 0L;
        this.modelHandle = 0L;
        this.interpreterHandle = 0L;
        this.cancellationFlagHandle = 0L;
        this.modelByteBuffer = null;
        this.inputsIndexes = null;
        this.outputsIndexes = null;
        this.isMemoryAllocated = false;
        this.delegates.clear();
        for (AutoCloseable ownedDelegate : this.ownedDelegates) {
            try {
                ownedDelegate.close();
            } catch (Exception e) {
                System.err.println("Failed to close flex delegate: " + e);
            }
        }
        this.ownedDelegates.clear();
    }

    public void runSignature(
            Map<String, Object> inputs, Map<String, Object> outputs, String signatureKey) {
        this.inferenceDurationNanoseconds = -1L;
        if (inputs == null || inputs.isEmpty()) {
            throw new IllegalArgumentException("Input error: Inputs should not be null or empty.");
        }
        if (outputs == null) {
            throw new IllegalArgumentException("Input error: Outputs should not be null.");
        }
        NativeSignatureRunnerWrapper signatureRunnerWrapper =
                getSignatureRunnerWrapper(signatureKey);
        int subgraphIndex = signatureRunnerWrapper.getSubgraphIndex();
        if (subgraphIndex == 0) {
            initTensorIndexesMaps();
            Object[] inputsList = new Object[inputs.size()];
            for (Map.Entry<String, Object> input : inputs.entrySet()) {
                inputsList[signatureRunnerWrapper.getInputIndex(input.getKey())] = input.getValue();
            }
            Map<Integer, Object> outputsWithOutputIndex = new TreeMap<>();
            for (Map.Entry<String, Object> output : outputs.entrySet()) {
                outputsWithOutputIndex.put(
                        Integer.valueOf(signatureRunnerWrapper.getOutputIndex(output.getKey())),
                        output.getValue());
            }
            run(inputsList, outputsWithOutputIndex);
            return;
        }
        for (Map.Entry<String, Object> input2 : inputs.entrySet()) {
            TensorImpl tensor = getInputTensor(input2.getKey(), signatureKey);
            int[] newShape = tensor.getInputShapeIfDifferent(input2.getValue());
            if (newShape != null) {
                signatureRunnerWrapper.resizeInput(input2.getKey(), newShape);
            }
        }
        signatureRunnerWrapper.allocateTensorsIfNeeded();
        for (Map.Entry<String, Object> input3 : inputs.entrySet()) {
            signatureRunnerWrapper.getInputTensor(input3.getKey()).setTo(input3.getValue());
        }
        long inferenceStartNanos = System.nanoTime();
        signatureRunnerWrapper.invoke();
        long inferenceDurationNanoseconds = System.nanoTime() - inferenceStartNanos;
        for (Map.Entry<String, Object> output2 : outputs.entrySet()) {
            if (output2.getValue() != null) {
                signatureRunnerWrapper.getOutputTensor(output2.getKey()).copyTo(output2.getValue());
            }
        }
        this.inferenceDurationNanoseconds = inferenceDurationNanoseconds;
    }

    void run(Object[] inputs, Map<Integer, Object> outputs) {
        this.inferenceDurationNanoseconds = -1L;
        if (inputs == null || inputs.length == 0) {
            throw new IllegalArgumentException("Input error: Inputs should not be null or empty.");
        }
        if (outputs == null) {
            throw new IllegalArgumentException("Input error: Outputs should not be null.");
        }
        for (int i = 0; i < inputs.length; i++) {
            TensorImpl tensor = getInputTensor(i);
            int[] newShape = tensor.getInputShapeIfDifferent(inputs[i]);
            if (newShape != null) {
                resizeInput(i, newShape);
            }
        }
        boolean allocatedTensors = allocateTensorsIfNeeded();
        for (int i2 = 0; i2 < inputs.length; i2++) {
            getInputTensor(i2).setTo(inputs[i2]);
        }
        long inferenceStartNanos = System.nanoTime();
        run(this.interpreterHandle, this.errorHandle);
        long inferenceDurationNanoseconds = System.nanoTime() - inferenceStartNanos;
        if (allocatedTensors) {
            for (int i3 = 0; i3 < this.outputTensors.length; i3++) {
                if (this.outputTensors[i3] != null) {
                    this.outputTensors[i3].refreshShape();
                }
            }
        }
        for (Map.Entry<Integer, Object> output : outputs.entrySet()) {
            if (output.getValue() != null) {
                getOutputTensor(output.getKey().intValue()).copyTo(output.getValue());
            }
        }
        this.inferenceDurationNanoseconds = inferenceDurationNanoseconds;
    }

    void resizeInput(int idx, int[] dims) {
        resizeInput(idx, dims, false);
    }

    void resizeInput(int idx, int[] dims, boolean strict) {
        if (resizeInput(this.interpreterHandle, this.errorHandle, idx, dims, strict)) {
            this.isMemoryAllocated = false;
            if (this.inputTensors[idx] != null) {
                this.inputTensors[idx].refreshShape();
            }
        }
    }

    void allocateTensors() {
        allocateTensorsIfNeeded();
    }

    private boolean allocateTensorsIfNeeded() {
        if (this.isMemoryAllocated) {
            return false;
        }
        this.isMemoryAllocated = true;
        allocateTensors(this.interpreterHandle, this.errorHandle);
        for (int i = 0; i < this.outputTensors.length; i++) {
            if (this.outputTensors[i] != null) {
                this.outputTensors[i].refreshShape();
            }
        }
        return true;
    }

    int getInputIndex(String name) {
        if (this.inputsIndexes == null) {
            String[] names = getInputNames(this.interpreterHandle);
            this.inputsIndexes = new HashMap();
            if (names != null) {
                for (int i = 0; i < names.length; i++) {
                    this.inputsIndexes.put(names[i], Integer.valueOf(i));
                }
            }
        }
        if (this.inputsIndexes.containsKey(name)) {
            return this.inputsIndexes.get(name).intValue();
        }
        throw new IllegalArgumentException(
                String.format(
                        "Input error: '%s' is not a valid name for any input. Names of inputs and"
                            + " their indexes are %s",
                        name, this.inputsIndexes));
    }

    private void initTensorIndexesMaps() {
        if (this.tensorToInputsIndexes != null) {
            return;
        }
        this.tensorToInputsIndexes = new HashMap();
        this.tensorToOutputsIndexes = new HashMap();
        int inputCount = getInputTensorCount();
        for (int i = 0; i < inputCount; i++) {
            int tensorIndex = getInputTensorIndex(this.interpreterHandle, i);
            this.tensorToInputsIndexes.put(Integer.valueOf(tensorIndex), Integer.valueOf(i));
        }
        int outputCount = getOutputTensorCount();
        for (int i2 = 0; i2 < outputCount; i2++) {
            int tensorIndex2 = getOutputTensorIndex(this.interpreterHandle, i2);
            this.tensorToOutputsIndexes.put(Integer.valueOf(tensorIndex2), Integer.valueOf(i2));
        }
    }

    int getOutputIndex(String name) {
        if (this.outputsIndexes == null) {
            String[] names = getOutputNames(this.interpreterHandle);
            this.outputsIndexes = new HashMap();
            if (names != null) {
                for (int i = 0; i < names.length; i++) {
                    this.outputsIndexes.put(names[i], Integer.valueOf(i));
                }
            }
        }
        if (this.outputsIndexes.containsKey(name)) {
            return this.outputsIndexes.get(name).intValue();
        }
        throw new IllegalArgumentException(
                String.format(
                        "Input error: '%s' is not a valid name for any output. Names of outputs and"
                            + " their indexes are %s",
                        name, this.outputsIndexes));
    }

    Long getLastNativeInferenceDurationNanoseconds() {
        if (this.inferenceDurationNanoseconds < 0) {
            return null;
        }
        return Long.valueOf(this.inferenceDurationNanoseconds);
    }

    int getInputTensorCount() {
        return this.inputTensors.length;
    }

    TensorImpl getInputTensor(int index) {
        if (index < 0 || index >= this.inputTensors.length) {
            throw new IllegalArgumentException("Invalid input Tensor index: " + index);
        }
        TensorImpl inputTensor = this.inputTensors[index];
        if (inputTensor == null) {
            TensorImpl[] tensorImplArr = this.inputTensors;
            TensorImpl inputTensor2 =
                    TensorImpl.fromIndex(
                            this.interpreterHandle,
                            getInputTensorIndex(this.interpreterHandle, index));
            tensorImplArr[index] = inputTensor2;
            return inputTensor2;
        }
        return inputTensor;
    }

    TensorImpl getInputTensor(String inputName, String signatureKey) {
        if (inputName == null) {
            throw new IllegalArgumentException("Invalid input tensor name provided (null)");
        }
        NativeSignatureRunnerWrapper signatureRunnerWrapper =
                getSignatureRunnerWrapper(signatureKey);
        int subgraphIndex = signatureRunnerWrapper.getSubgraphIndex();
        if (subgraphIndex > 0) {
            return signatureRunnerWrapper.getInputTensor(inputName);
        }
        int inputIndex = signatureRunnerWrapper.getInputIndex(inputName);
        return getInputTensor(inputIndex);
    }

    public String[] getSignatureKeys() {
        return getSignatureKeys(this.interpreterHandle);
    }

    String[] getSignatureInputs(String signatureKey) {
        return getSignatureRunnerWrapper(signatureKey).inputNames();
    }

    String[] getSignatureOutputs(String signatureKey) {
        return getSignatureRunnerWrapper(signatureKey).outputNames();
    }

    int getOutputTensorCount() {
        return this.outputTensors.length;
    }

    TensorImpl getOutputTensor(int index) {
        if (index < 0 || index >= this.outputTensors.length) {
            throw new IllegalArgumentException("Invalid output Tensor index: " + index);
        }
        TensorImpl outputTensor = this.outputTensors[index];
        if (outputTensor == null) {
            TensorImpl[] tensorImplArr = this.outputTensors;
            TensorImpl outputTensor2 =
                    TensorImpl.fromIndex(
                            this.interpreterHandle,
                            getOutputTensorIndex(this.interpreterHandle, index));
            tensorImplArr[index] = outputTensor2;
            return outputTensor2;
        }
        return outputTensor;
    }

    TensorImpl getOutputTensor(String outputName, String signatureKey) {
        if (outputName == null) {
            throw new IllegalArgumentException("Invalid output tensor name provided (null)");
        }
        NativeSignatureRunnerWrapper signatureRunnerWrapper =
                getSignatureRunnerWrapper(signatureKey);
        int subgraphIndex = signatureRunnerWrapper.getSubgraphIndex();
        if (subgraphIndex > 0) {
            return signatureRunnerWrapper.getOutputTensor(outputName);
        }
        int outputIndex = signatureRunnerWrapper.getOutputIndex(outputName);
        return getOutputTensor(outputIndex);
    }

    int getExecutionPlanLength() {
        return getExecutionPlanLength(this.interpreterHandle);
    }

    void setCancelled(boolean value) {
        if (this.cancellationFlagHandle == 0) {
            throw new IllegalStateException(
                    "Cannot cancel the inference. Have you called"
                        + " InterpreterApi.Options.setCancellable?");
        }
        setCancelled(this.interpreterHandle, this.cancellationFlagHandle, value);
    }

    private void addDelegates(InterpreterImpl.Options options) {
        Delegate optionalFlexDelegate;
        if (this.originalGraphHasUnresolvedFlexOp
                && (optionalFlexDelegate = maybeCreateFlexDelegate(options.getDelegates()))
                        != null) {
            this.ownedDelegates.add((AutoCloseable) optionalFlexDelegate);
            this.delegates.add(optionalFlexDelegate);
        }
        this.delegates.addAll(options.getDelegates());
        if (options.getUseNNAPI()) {
            NnApiDelegate optionalNnApiDelegate = new NnApiDelegate();
            this.ownedDelegates.add(optionalNnApiDelegate);
            this.delegates.add(optionalNnApiDelegate);
        }
        maybeAddXnnpackDelegate(options);
    }

    private void maybeAddXnnpackDelegate(InterpreterImpl.Options options) {
        int i = -1;
        if (options.useXNNPACK != null) {
            i = options.useXNNPACK.booleanValue() ? 1 : 0;
        }
        if (i == 1) {
            this.delegates.add(
                    createXNNPACKDelegate(
                            this.interpreterHandle, this.errorHandle, i, options.getNumThreads()));
        }
    }

    private NativeSignatureRunnerWrapper getSignatureRunnerWrapper(String signatureKey) {
        if (this.signatureRunnerMap == null) {
            this.signatureRunnerMap = new HashMap();
        }
        if (!this.signatureRunnerMap.containsKey(signatureKey)) {
            this.signatureRunnerMap.put(
                    signatureKey,
                    new NativeSignatureRunnerWrapper(
                            this.interpreterHandle, this.errorHandle, signatureKey));
        }
        return this.signatureRunnerMap.get(signatureKey);
    }

    private static Delegate maybeCreateFlexDelegate(List<Delegate> delegates) {
        try {
            Class<?> clazz = Class.forName("org.tensorflow.lite.flex.FlexDelegate");
            for (Delegate delegate : delegates) {
                if (clazz.isInstance(delegate)) {
                    return null;
                }
            }
            return (Delegate) clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
