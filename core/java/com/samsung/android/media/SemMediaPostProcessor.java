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

/* loaded from: classes5.dex */
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

    /* renamed from: ON */
    public static final int f3025ON = 1;
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

    public void set(int index, long timeUs, int flags) {
      this.index = index;
      this.timeUs = timeUs;
      this.flags = flags;
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

    private ProcessingFormat(Map<String, Object> format) {
      this.format = new HashMap(format);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int getValueTypeForKey(String key) {
      char c;
      switch (key.hashCode()) {
        case -2087124997:
          if (key.equals(Key.ROTATION)) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case -1553252829:
          if (key.equals(Key.FILTER_NAME)) {
            c = 15;
            break;
          }
          c = 65535;
          break;
        case -1515046802:
          if (key.equals(Key.COLOR_TRANSFER)) {
            c = '\r';
            break;
          }
          c = 65535;
          break;
        case -1483301056:
          if (key.equals(Key.COLOR_STANDARD)) {
            c = '\f';
            break;
          }
          c = 65535;
          break;
        case -1409672288:
          if (key.equals(Key.INPUT_COLOR_FORMAT)) {
            c = '\b';
            break;
          }
          c = 65535;
          break;
        case -1234053225:
          if (key.equals(Key.OUTPUT_COLOR_FORMAT)) {
            c = '\t';
            break;
          }
          c = 65535;
          break;
        case -1221029593:
          if (key.equals("height")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case -907916564:
          if (key.equals(Key.FILTER_LEVEL)) {
            c = 14;
            break;
          }
          c = 65535;
          break;
        case -891986215:
          if (key.equals("stride")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case -4379043:
          if (key.equals(Key.ELEVATION)) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        case 101609:
          if (key.equals(Key.FPS)) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 113126854:
          if (key.equals("width")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 605404474:
          if (key.equals(Key.COLOR_FORMAT)) {
            c = '\n';
            break;
          }
          c = 65535;
          break;
        case 1277116314:
          if (key.equals(Key.COLOR_RANGE)) {
            c = 11;
            break;
          }
          c = 65535;
          break;
        case 1865435223:
          if (key.equals(Key.BUFFER_FORMAT)) {
            c = 7;
            break;
          }
          c = 65535;
          break;
        case 1906231393:
          if (key.equals(Key.BUFFER_SIZE)) {
            c = 6;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case '\b':
        case '\t':
        case '\n':
        case 11:
        case '\f':
        case '\r':
        case 14:
          return 0;
        case 15:
          return 1;
        default:
          throw new IllegalArgumentException();
      }
    }

    public void setInteger(String key, int value) {
      if (getValueTypeForKey(key) != 0) {
        throw new IllegalArgumentException();
      }
      this.format.put(key, Integer.valueOf(value));
    }

    public int getInteger(String key) {
      if (getValueTypeForKey(key) != 0) {
        throw new IllegalArgumentException();
      }
      return ((Integer) this.format.get(key)).intValue();
    }

    public void setString(String key, String value) {
      if (getValueTypeForKey(key) != 1) {
        throw new IllegalArgumentException();
      }
      this.format.put(key, value);
    }

    public String getString(String key) {
      if (getValueTypeForKey(key) != 1) {
        throw new IllegalArgumentException();
      }
      return (String) this.format.get(key);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Boolean isReserved(String key) {
      char c;
      switch (key.hashCode()) {
        case -2087124997:
          if (key.equals(Key.ROTATION)) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case -1553252829:
          if (key.equals(Key.FILTER_NAME)) {
            c = 14;
            break;
          }
          c = 65535;
          break;
        case -1515046802:
          if (key.equals(Key.COLOR_TRANSFER)) {
            c = '\r';
            break;
          }
          c = 65535;
          break;
        case -1483301056:
          if (key.equals(Key.COLOR_STANDARD)) {
            c = '\f';
            break;
          }
          c = 65535;
          break;
        case -1409672288:
          if (key.equals(Key.INPUT_COLOR_FORMAT)) {
            c = '\b';
            break;
          }
          c = 65535;
          break;
        case -1234053225:
          if (key.equals(Key.OUTPUT_COLOR_FORMAT)) {
            c = '\t';
            break;
          }
          c = 65535;
          break;
        case -1221029593:
          if (key.equals("height")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case -907916564:
          if (key.equals(Key.FILTER_LEVEL)) {
            c = 15;
            break;
          }
          c = 65535;
          break;
        case -891986215:
          if (key.equals("stride")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case -4379043:
          if (key.equals(Key.ELEVATION)) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        case 101609:
          if (key.equals(Key.FPS)) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 113126854:
          if (key.equals("width")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 605404474:
          if (key.equals(Key.COLOR_FORMAT)) {
            c = '\n';
            break;
          }
          c = 65535;
          break;
        case 1277116314:
          if (key.equals(Key.COLOR_RANGE)) {
            c = 11;
            break;
          }
          c = 65535;
          break;
        case 1865435223:
          if (key.equals(Key.BUFFER_FORMAT)) {
            c = 7;
            break;
          }
          c = 65535;
          break;
        case 1906231393:
          if (key.equals(Key.BUFFER_SIZE)) {
            c = 6;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case '\b':
        case '\t':
        case '\n':
        case 11:
        case '\f':
        case '\r':
        case 14:
        case 15:
          return true;
        default:
          return false;
      }
    }

    public void setCustomKeyValue(String key, Object value) {
      if (isReserved(key).booleanValue()) {
        throw new IllegalArgumentException("This key is already reserved.");
      }
      this.format.put(key, value);
    }

    public Object getCustomKeyValue(String key) {
      if (isReserved(key).booleanValue()) {
        throw new IllegalArgumentException("This key is already reserved.");
      }
      return this.format.get(key);
    }
  }

  private final long lockAndGetContext() {
    this.mNativeContextLock.lock();
    return this.mNativeContext;
  }

  private final void setAndUnlockContext(long context) {
    this.mNativeContext = context;
    this.mNativeContextLock.unlock();
  }

  static {
    System.loadLibrary("semmediapostprocessor_jni");
    native_init();
  }

  public static SemMediaPostProcessor createByType(int type) throws IllegalArgumentException {
    return new SemMediaPostProcessor(type);
  }

  private SemMediaPostProcessor(int type) {
    native_setup(new WeakReference(this), type);
  }

  public static boolean isSupported(int type, ProcessingFormat config) {
    String[] keys = (String[]) config.format.keySet().toArray(new String[0]);
    Object[] values = config.format.values().toArray(new Object[0]);
    return native_is_supported(type, keys, values);
  }

  public Surface createInputSurface() throws IllegalStateException {
    return native_createInputSurface();
  }

  public void configure(ProcessingFormat config, Surface surface)
      throws IllegalStateException, IllegalArgumentException {
    String[] keys = (String[]) config.format.keySet().toArray(new String[0]);
    Object[] values = config.format.values().toArray(new Object[0]);
    native_configure(keys, values, surface);
  }

  public void setParameter(int key, int value) throws IllegalStateException {
    native_setParameter(key, value);
  }

  public void setParameter(int key, String value) throws IllegalStateException {
    native_setParameter(key, value);
  }

  public ByteBuffer dequeueInputBuffer(BufferInfo bufferInfo, long timeoutUs)
      throws IllegalStateException {
    return native_dequeueInputBuffer(bufferInfo, timeoutUs);
  }

  public void queueInputBuffer(BufferInfo bufferInfo) throws IllegalStateException {
    native_queueInputBuffer(bufferInfo.index, bufferInfo.timeUs, bufferInfo.flags);
  }

  public ByteBuffer dequeueOutputBuffer(BufferInfo bufferInfo, long timeoutUs)
      throws IllegalStateException {
    return native_dequeueOutputBuffer(bufferInfo, timeoutUs);
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

  public void renderAndReleaseOutputBuffer(int index, long timeUs, long realTimeNs)
      throws IllegalStateException {
    native_renderAndReleaseOutputBuffer(index, timeUs, realTimeNs);
  }

  public void releaseOutputBuffer(int index) throws IllegalStateException {
    native_releaseOutputBuffer(index);
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
