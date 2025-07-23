package com.samsung.android.media;

import android.view.Surface;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public final class SemMediaPostProcessor {
    private long mNativeContext;
    private final Lock mNativeContextLock = new ReentrantLock();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorFormat {
        public static final int NV12 = 2;
        public static final int NV12_UBWC = 5;
        public static final int NV12_VENUS = 4;
        public static final int RGBA8888 = 9;
        public static final int YV12 = 11;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputFlag {
        public static final int BY_PASS = 8;
        public static final int END_OF_STREAM = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OutputFlag {
        public static final int END_OF_STREAM = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParameterKey {
        public static final int FAST_START = 2;
        public static final int FILTER_LEVEL = 5;
        public static final int FILTER_NAME = 4;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParameterValue {
        public static final int OFF = 0;
        public static final int ON = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        public static final int DEFLICKER = 2;
        public static final int FILTER = 6;
        public static final int FRC = 3;
    }

    private final native void native_configure(String[] strArr, Object[] objArr, Surface surface);

    private final native Surface native_createInputSurface();

    private final native ByteBuffer native_dequeueInputBuffer(BufferInfo bufferInfo, long j);

    private final native ByteBuffer native_dequeueOutputBuffer(BufferInfo bufferInfo, long j);

    private final native void native_finalize();

    private final native void native_flush();

    private final native Map<String, Object> native_getInputFormat();

    private final native Map<String, Object> native_getOutputFormat();

    private static final native void native_init();

    private static final native boolean native_is_supported(int i, String[] strArr, Object[] objArr);

    private final native void native_queueInputBuffer(int i, long j, int i2);

    private final native void native_release();

    private final native void native_releaseOutputBuffer(int i);

    private final native void native_renderAndReleaseOutputBuffer(int i, long j, long j2);

    private final native void native_reset();

    private final native void native_setParameter(int i, int i2);

    private final native void native_setParameter(int i, String str);

    private final native void native_setup(Object obj, int i);

    private final native void native_signalEndOfInputStream();

    public static final class BufferInfo {
        public int flags;
        public int index;
        public long timeUs;

        public void set(int i, long j, int i2) {
            this.index = i;
            this.timeUs = j;
            this.flags = i2;
        }
    }

    public static final class ProcessingFormat {
        private Map<String, Object> format;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Key {
            public static final String BUFFER_FORMAT = "bufferFormat";
            public static final String BUFFER_SIZE = "bufferSize";
            public static final String COLOR_FORMAT = "colorFormat";
            public static final String COLOR_RANGE = "colorRange";
            public static final String COLOR_STANDARD = "colorStandard";
            public static final String COLOR_TRANSFER = "colorTransfer";
            public static final String ELEVATION = "elevation";
            public static final String FILTER_LEVEL = "filterLevel";
            public static final String FILTER_NAME = "filterName";
            public static final String FPS = "fps";
            public static final String HEIGHT = "height";
            public static final String INPUT_COLOR_FORMAT = "input-color";
            public static final String OUTPUT_COLOR_FORMAT = "output-color";
            public static final String ROTATION = "rotation-degree";
            public static final String STRIDE = "stride";
            public static final String WIDTH = "width";
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
            public static final int INTEGER = 0;
            public static final int STRING = 1;
        }

        public ProcessingFormat() {
            this.format = new HashMap();
        }

        private ProcessingFormat(Map<String, Object> map) {
            this.format = new HashMap(map);
        }

        public int getValueTypeForKey(String str) {
            str.hashCode();
            switch (str) {
                case "rotation-degree":
                case "colorTransfer":
                case "colorStandard":
                case "input-color":
                case "output-color":
                case "height":
                case "filterLevel":
                case "stride":
                case "elevation":
                case "fps":
                case "width":
                case "colorFormat":
                case "colorRange":
                case "bufferFormat":
                case "bufferSize":
                    return 0;
                case "filterName":
                    return 1;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void setInteger(String str, int i) {
            if (getValueTypeForKey(str) != 0) {
                throw new IllegalArgumentException();
            }
            this.format.put(str, Integer.valueOf(i));
        }

        public int getInteger(String str) {
            if (getValueTypeForKey(str) != 0) {
                throw new IllegalArgumentException();
            }
            return ((Integer) this.format.get(str)).intValue();
        }

        public void setString(String str, String str2) {
            if (getValueTypeForKey(str) != 1) {
                throw new IllegalArgumentException();
            }
            this.format.put(str, str2);
        }

        public String getString(String str) {
            if (getValueTypeForKey(str) != 1) {
                throw new IllegalArgumentException();
            }
            return (String) this.format.get(str);
        }

        private Boolean isReserved(String str) {
            str.hashCode();
            switch (str) {
                case "rotation-degree":
                case "filterName":
                case "colorTransfer":
                case "colorStandard":
                case "input-color":
                case "output-color":
                case "height":
                case "filterLevel":
                case "stride":
                case "elevation":
                case "fps":
                case "width":
                case "colorFormat":
                case "colorRange":
                case "bufferFormat":
                case "bufferSize":
                    return true;
                default:
                    return false;
            }
        }

        public void setCustomKeyValue(String str, Object obj) {
            if (isReserved(str).booleanValue()) {
                throw new IllegalArgumentException("This key is already reserved.");
            }
            this.format.put(str, obj);
        }

        public Object getCustomKeyValue(String str) {
            if (isReserved(str).booleanValue()) {
                throw new IllegalArgumentException("This key is already reserved.");
            }
            return this.format.get(str);
        }
    }

    private final long lockAndGetContext() {
        this.mNativeContextLock.lock();
        return this.mNativeContext;
    }

    private final void setAndUnlockContext(long j) {
        this.mNativeContext = j;
        this.mNativeContextLock.unlock();
    }

    static {
        System.loadLibrary("semmediapostprocessor_jni");
        native_init();
    }

    public static SemMediaPostProcessor createByType(int i) throws IllegalArgumentException {
        return new SemMediaPostProcessor(i);
    }

    private SemMediaPostProcessor(int i) {
        native_setup(new WeakReference(this), i);
    }

    public static boolean isSupported(int i, ProcessingFormat processingFormat) {
        return native_is_supported(i, (String[]) processingFormat.format.keySet().toArray(new String[0]), processingFormat.format.values().toArray(new Object[0]));
    }

    public Surface createInputSurface() throws IllegalStateException {
        return native_createInputSurface();
    }

    public void configure(ProcessingFormat processingFormat, Surface surface) throws IllegalStateException, IllegalArgumentException {
        native_configure((String[]) processingFormat.format.keySet().toArray(new String[0]), processingFormat.format.values().toArray(new Object[0]), surface);
    }

    public void setParameter(int i, int i2) throws IllegalStateException {
        native_setParameter(i, i2);
    }

    public void setParameter(int i, String str) throws IllegalStateException {
        native_setParameter(i, str);
    }

    public ByteBuffer dequeueInputBuffer(BufferInfo bufferInfo, long j) throws IllegalStateException {
        return native_dequeueInputBuffer(bufferInfo, j);
    }

    public void queueInputBuffer(BufferInfo bufferInfo) throws IllegalStateException {
        native_queueInputBuffer(bufferInfo.index, bufferInfo.timeUs, bufferInfo.flags);
    }

    public ByteBuffer dequeueOutputBuffer(BufferInfo bufferInfo, long j) throws IllegalStateException {
        return native_dequeueOutputBuffer(bufferInfo, j);
    }

    public ProcessingFormat getOutputFormat() throws IllegalStateException {
        return new ProcessingFormat(native_getOutputFormat());
    }

    public ProcessingFormat getInputFormat() throws IllegalStateException {
        return new ProcessingFormat(native_getInputFormat());
    }

    public void signalEndOfInputStream() throws IllegalStateException {
        native_signalEndOfInputStream();
    }

    public void renderAndReleaseOutputBuffer(int i, long j, long j2) throws IllegalStateException {
        native_renderAndReleaseOutputBuffer(i, j, j2);
    }

    public void releaseOutputBuffer(int i) throws IllegalStateException {
        native_releaseOutputBuffer(i);
    }

    public void flush() throws IllegalStateException {
        native_flush();
    }

    public final void release() {
        native_release();
    }

    public void reset() throws IllegalStateException {
        native_reset();
    }

    protected void finalize() {
        native_finalize();
    }
}
