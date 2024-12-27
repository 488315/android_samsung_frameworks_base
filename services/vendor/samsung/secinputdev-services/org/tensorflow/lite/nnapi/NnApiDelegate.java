package org.tensorflow.lite.nnapi;

import org.tensorflow.lite.Delegate;
import org.tensorflow.lite.TensorFlowLite;

public class NnApiDelegate implements Delegate, AutoCloseable {
    private static final long INVALID_DELEGATE_HANDLE = 0;
    private long delegateHandle;

    private static native long createDelegate(
            int preference,
            String deviceName,
            String cacheDir,
            String modelToken,
            int maxDelegatedPartitions,
            boolean overrideDisallowCpu,
            boolean disallowCpuValue,
            boolean allowFp16,
            long nnApiSupportLibraryHandle);

    private static native void deleteDelegate(long delegateHandle);

    private static native int getNnapiErrno(long delegateHandle);

    public static final class Options {
        public static final int EXECUTION_PREFERENCE_FAST_SINGLE_ANSWER = 1;
        public static final int EXECUTION_PREFERENCE_LOW_POWER = 0;
        public static final int EXECUTION_PREFERENCE_SUSTAINED_SPEED = 2;
        public static final int EXECUTION_PREFERENCE_UNDEFINED = -1;
        private int executionPreference = -1;
        private String acceleratorName = null;
        private String cacheDir = null;
        private String modelToken = null;
        private Integer maxDelegatedPartitions = null;
        private Boolean useNnapiCpu = null;
        private Boolean allowFp16 = null;
        private long nnApiSupportLibraryHandle = 0;

        public Options setExecutionPreference(int preference) {
            this.executionPreference = preference;
            return this;
        }

        public Options setAcceleratorName(String name) {
            this.acceleratorName = name;
            return this;
        }

        public Options setCacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Options setModelToken(String modelToken) {
            this.modelToken = modelToken;
            return this;
        }

        public Options setMaxNumberOfDelegatedPartitions(int limit) {
            this.maxDelegatedPartitions = Integer.valueOf(limit);
            return this;
        }

        public Options setUseNnapiCpu(boolean enable) {
            this.useNnapiCpu = Boolean.valueOf(enable);
            return this;
        }

        public Options setAllowFp16(boolean enable) {
            this.allowFp16 = Boolean.valueOf(enable);
            return this;
        }

        public Options setNnApiSupportLibraryHandle(long handle) {
            this.nnApiSupportLibraryHandle = handle;
            return this;
        }
    }

    public NnApiDelegate(Options options) {
        TensorFlowLite.init();
        int i = options.executionPreference;
        String str = options.acceleratorName;
        String str2 = options.cacheDir;
        String str3 = options.modelToken;
        int intValue =
                options.maxDelegatedPartitions != null
                        ? options.maxDelegatedPartitions.intValue()
                        : -1;
        boolean z = true;
        boolean z2 = options.useNnapiCpu != null;
        if (options.useNnapiCpu != null && options.useNnapiCpu.booleanValue()) {
            z = false;
        }
        this.delegateHandle =
                createDelegate(
                        i,
                        str,
                        str2,
                        str3,
                        intValue,
                        z2,
                        z,
                        options.allowFp16 != null ? options.allowFp16.booleanValue() : false,
                        options.nnApiSupportLibraryHandle);
    }

    public NnApiDelegate() {
        this(new Options());
    }

    @Override // org.tensorflow.lite.Delegate
    public long getNativeHandle() {
        return this.delegateHandle;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.delegateHandle != 0) {
            deleteDelegate(this.delegateHandle);
            this.delegateHandle = 0L;
        }
    }

    public int getNnapiErrno() {
        checkNotClosed();
        return getNnapiErrno(this.delegateHandle);
    }

    public boolean hasErrors() {
        return getNnapiErrno(this.delegateHandle) != 0;
    }

    private void checkNotClosed() {
        if (this.delegateHandle == 0) {
            throw new IllegalStateException("Should not access delegate after it has been closed.");
        }
    }
}
