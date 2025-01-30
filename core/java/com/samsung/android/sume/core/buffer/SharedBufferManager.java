package com.samsung.android.sume.core.buffer;

import android.hardware.HardwareBuffer;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.types.ColorFormat;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class SharedBufferManager {
  private static volatile SharedBufferManager sInstance;

  /* renamed from: com.samsung.android.sume.core.buffer.SharedBufferManager$1 */
  static /* synthetic */ class C52561 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$ColorFormat =
        new int[ColorFormat.values().length];
  }

  private static native int nativeByte2HwBuffer(
      Buffer buffer, HardwareBuffer hardwareBuffer, String str);

  private static native int nativeHw2ByteBuffer(
      HardwareBuffer hardwareBuffer, Buffer buffer, String str);

  private static native long nativeLockHwBuffer(HardwareBuffer hardwareBuffer);

  private static native void nativeUnLockHwBuffer(HardwareBuffer hardwareBuffer);

  public static SharedBufferManager getInstance() {
    if (sInstance == null) {
      synchronized (SharedBufferManager.class) {
        if (sInstance == null) {
          sInstance = new SharedBufferManager();
        }
      }
    }
    return sInstance;
  }

  private SharedBufferManager() {}

  private HardwareBuffer createAsImage(MediaFormat format) {
    int i =
        C52561.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[
            format.getColorFormat().ordinal()];
    return HardwareBuffer.create((int) (format.size() <= 0 ? 1L : format.size()), 1, 33, 1, 51L);
  }

  private HardwareBuffer createAsAudio(MutableMediaFormat format) {
    return HardwareBuffer.create((int) (format.size() <= 0 ? 1L : format.size()), 1, 33, 1, 51L);
  }

  public static HardwareBuffer create(MediaFormat format) {
    if (format.getMediaType().isAudio()) {
      return getInstance().createAsAudio((MutableMediaFormat) format);
    }
    return getInstance().createAsImage(format);
  }

  public static void copyFromByteBuffer(
      MediaFormat format, ByteBuffer srcBuffer, HardwareBuffer dstBuffer) {
    try {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("cols", format.getCols());
      jsonParams.put("rows", format.getRows());
      jsonParams.put(Contract.DatabaseSize.PATH, format.size());
      jsonParams.put(
          android.media.MediaFormat.KEY_COLOR_FORMAT, format.getColorFormat().stringfy());
      nativeByte2HwBuffer(srcBuffer, dstBuffer, jsonParams.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public static void copyToByteBuffer(
      MediaFormat format, HardwareBuffer srcBuffer, ByteBuffer dstBuffer) {
    try {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("cols", format.getShape().getCols());
      jsonParams.put("rows", format.getShape().getRows());
      jsonParams.put(Contract.DatabaseSize.PATH, format.size());
      jsonParams.put(
          android.media.MediaFormat.KEY_COLOR_FORMAT, format.getColorFormat().stringfy());
      nativeHw2ByteBuffer(srcBuffer, dstBuffer, jsonParams.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public static void copyFromBuffer(MediaBuffer src, HardwareBuffer dst) {
    JSONObject jsonParams = new JSONObject();
    try {
      jsonParams.put("cols", src.getCols());
      jsonParams.put("rows", src.getRows());
      jsonParams.put(Contract.DatabaseSize.PATH, src.size());
      jsonParams.put(
          android.media.MediaFormat.KEY_COLOR_FORMAT, src.getFormat().getColorFormat().stringfy());
      nativeByte2HwBuffer((Buffer) src.getTypedData(ByteBuffer.class), dst, jsonParams.toString());
    } catch (JSONException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("");
    }
  }

  static {
    System.loadLibrary("sume_mediabuffer_jni.media.samsung");
  }
}
