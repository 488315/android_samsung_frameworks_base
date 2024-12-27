package org.tensorflow.lite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface InterpreterApi extends AutoCloseable {
    void allocateTensors();

    @Override // java.lang.AutoCloseable
    void close();

    int getInputIndex(String opName);

    Tensor getInputTensor(int inputIndex);

    int getInputTensorCount();

    Long getLastNativeInferenceDurationNanoseconds();

    int getOutputIndex(String opName);

    Tensor getOutputTensor(int outputIndex);

    int getOutputTensorCount();

    void resizeInput(int idx, int[] dims);

    void resizeInput(int idx, int[] dims, boolean strict);

    void run(Object input, Object output);

    void runForMultipleInputsOutputs(Object[] inputs, Map<Integer, Object> outputs);

    public static class Options {
        Boolean allowCancellation;
        final List<Delegate> delegates;
        int numThreads;
        Boolean useNNAPI;

        public Options() {
            this.numThreads = -1;
            this.delegates = new ArrayList();
        }

        public Options(Options other) {
            this.numThreads = -1;
            this.numThreads = other.numThreads;
            this.useNNAPI = other.useNNAPI;
            this.allowCancellation = other.allowCancellation;
            this.delegates = new ArrayList(other.delegates);
        }

        public Options setNumThreads(int numThreads) {
            this.numThreads = numThreads;
            return this;
        }

        public int getNumThreads() {
            return this.numThreads;
        }

        public Options setUseNNAPI(boolean useNNAPI) {
            this.useNNAPI = Boolean.valueOf(useNNAPI);
            return this;
        }

        public boolean getUseNNAPI() {
            return this.useNNAPI != null && this.useNNAPI.booleanValue();
        }

        public Options setCancellable(boolean allow) {
            this.allowCancellation = Boolean.valueOf(allow);
            return this;
        }

        public boolean isCancellable() {
            return this.allowCancellation != null && this.allowCancellation.booleanValue();
        }

        public Options addDelegate(Delegate delegate) {
            this.delegates.add(delegate);
            return this;
        }

        public List<Delegate> getDelegates() {
            return Collections.unmodifiableList(this.delegates);
        }
    }
}
