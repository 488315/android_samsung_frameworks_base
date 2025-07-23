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

/* loaded from: classes6.dex */
public final class SharedBufferManager {
    private static volatile SharedBufferManager sInstance;

    /* renamed from: com.samsung.android.sume.core.buffer.SharedBufferManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$ColorFormat = new int[ColorFormat.values().length];
    }

    private static native int nativeByte2HwBuffer(Buffer buffer, HardwareBuffer hardwareBuffer, String str);

    private static native int nativeHw2ByteBuffer(HardwareBuffer hardwareBuffer, Buffer buffer, String str);

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

    private SharedBufferManager() {
    }

    private HardwareBuffer createAsImage(MediaFormat mediaFormat) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[mediaFormat.getColorFormat().ordinal()];
        return HardwareBuffer.create((int) (mediaFormat.size() <= 0 ? 1L : mediaFormat.size()), 1, 33, 1, 51L);
    }

    private HardwareBuffer createAsAudio(MutableMediaFormat mutableMediaFormat) {
        return HardwareBuffer.create((int) (mutableMediaFormat.size() <= 0 ? 1L : mutableMediaFormat.size()), 1, 33, 1, 51L);
    }

    public static HardwareBuffer create(MediaFormat mediaFormat) {
        if (mediaFormat.getMediaType().isAudio()) {
            return getInstance().createAsAudio((MutableMediaFormat) mediaFormat);
        }
        return getInstance().createAsImage(mediaFormat);
    }

    public static void copyFromByteBuffer(MediaFormat mediaFormat, ByteBuffer byteBuffer, HardwareBuffer hardwareBuffer) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cols", mediaFormat.getCols());
            jSONObject.put("rows", mediaFormat.getRows());
            jSONObject.put(Contract.DatabaseSize.PATH, mediaFormat.size());
            jSONObject.put(android.media.MediaFormat.KEY_COLOR_FORMAT, mediaFormat.getColorFormat().stringfy());
            nativeByte2HwBuffer(byteBuffer, hardwareBuffer, jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void copyToByteBuffer(MediaFormat mediaFormat, HardwareBuffer hardwareBuffer, ByteBuffer byteBuffer) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cols", mediaFormat.getShape().getCols());
            jSONObject.put("rows", mediaFormat.getShape().getRows());
            jSONObject.put(Contract.DatabaseSize.PATH, mediaFormat.size());
            jSONObject.put(android.media.MediaFormat.KEY_COLOR_FORMAT, mediaFormat.getColorFormat().stringfy());
            nativeHw2ByteBuffer(hardwareBuffer, byteBuffer, jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void copyFromBuffer(MediaBuffer mediaBuffer, HardwareBuffer hardwareBuffer) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cols", mediaBuffer.getCols());
            jSONObject.put("rows", mediaBuffer.getRows());
            jSONObject.put(Contract.DatabaseSize.PATH, mediaBuffer.size());
            jSONObject.put(android.media.MediaFormat.KEY_COLOR_FORMAT, mediaBuffer.getFormat().getColorFormat().stringfy());
            nativeByte2HwBuffer((Buffer) mediaBuffer.getTypedData(ByteBuffer.class), hardwareBuffer, jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("");
        }
    }

    static {
        System.loadLibrary("sume_mediabuffer_jni.media.samsung");
    }
}
