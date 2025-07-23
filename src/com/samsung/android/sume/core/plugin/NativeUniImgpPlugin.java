package com.samsung.android.sume.core.plugin;

import android.graphics.Bitmap;
import android.graphics.Gainmap;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.ExifInterface;
import android.media.Image;
import android.media.quality.MediaQualityContract;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.UniExifInterface;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.functional.DescriptorLoader;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import com.samsung.android.sume.core.types.CodecType;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.FlipType;
import com.samsung.android.sume.core.types.ImgpType;
import com.samsung.android.sume.core.types.PadType;
import com.samsung.android.sume.core.types.SplitType;
import com.samsung.android.sume.core.types.Status;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class NativeUniImgpPlugin implements Plugin<ImgpPlugin>, Operator {
    private static final String TAG = Def.tagOf((Class<?>) NativeUniImgpPlugin.class);
    private final ReentrantLock lock = new ReentrantLock();
    private long nativeContext;
    private MediaFormat persistentInputFormat;
    private MediaFormat persistentOutputFormat;
    private ColorFormat preferredColorFormat;

    private native int nativeCreateGainmap(String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeCrop(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeCvtColor(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeCvtData(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeCvtGamutV2(String str, Object obj, String str2, Object obj2);

    private native int nativeDecode(String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeEncode(String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeEncodeHDR(String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeFlipV2(String str, String str2, Object obj, String str3, Object obj2);

    private native int nativeInit(String str, String str2);

    private native int nativeMeasureQuality(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2, HashMap<String, Object> hashMap);

    private native int nativeMerge(String str, String str2, ByteBuffer byteBuffer, String str3, HashMap<String, Object> hashMap);

    private native int nativeRelease();

    private native int nativeResize(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeRotate(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeRun(String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2, HashMap<String, Object> hashMap);

    private static native void nativeSetup();

    private native int nativeSplit(String str, String str2, ByteBuffer byteBuffer, String str3, HashMap<String, Object> hashMap);

    public MutableMediaBuffer cvtHdr2Sdr(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        return mutableMediaBuffer;
    }

    static {
        System.loadLibrary("sume_jni.media.samsung");
        nativeSetup();
    }

    public NativeUniImgpPlugin() {
        Log.d(TAG, "NativeUniImgpPlugin: version= [core=" + Def.getCoreVersion() + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public NativeUniImgpPlugin(List<ImgpType> list, MediaFormat mediaFormat, MediaFormat mediaFormat2, ColorFormat colorFormat) {
        String str = TAG;
        Log.d(str, "NativeUniImgpPlugin: version= [core=" + Def.getCoreVersion() + NavigationBarInflaterView.SIZE_MOD_END);
        StringBuilder sb = new StringBuilder("opList=");
        sb.append(list);
        Log.d(str, sb.toString());
        this.persistentInputFormat = mediaFormat;
        this.persistentOutputFormat = mediaFormat2;
        if (colorFormat != null) {
            this.preferredColorFormat = colorFormat;
        } else {
            this.preferredColorFormat = ColorFormat.NONE;
        }
        JSONArray jSONArray = new JSONArray();
        for (ImgpType imgpType : list) {
            if (imgpType == ImgpType.ENCODE_HDR && !isHDRSupported()) {
                throw new UnsupportedOperationException("HDR is not supported!");
            }
            jSONArray.put(imgpType.stringfy());
        }
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.preferredColorFormat != ColorFormat.NONE) {
                jSONObject.put("preferred-color-format", this.preferredColorFormat.stringfy());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nativeInit(jSONArray.toString(), jSONObject.toString());
    }

    public void release() {
        if (this.nativeContext != 0) {
            try {
                this.lock.lock();
                nativeRelease();
                this.nativeContext = 0L;
            } finally {
                this.lock.unlock();
            }
        }
    }

    static /* synthetic */ MFDescriptor lambda$bindToFixture$0() {
        return new ImgpDescriptor(ImgpPlugin.Type.NATIVE_UNIIMGP);
    }

    @Override // com.samsung.android.sume.core.plugin.Plugin
    public void bindToFixture(ImgpPlugin imgpPlugin) {
        imgpPlugin.setDescriptorLoader(new DescriptorLoader() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda1
            @Override // com.samsung.android.sume.core.functional.DescriptorLoader
            public final MFDescriptor load() {
                return NativeUniImgpPlugin.lambda$bindToFixture$0();
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.RESIZE, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda8
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.resize(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CVT_COLOR, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda9
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.cvtColor(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CVT_DATA, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda10
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.cvtData(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CVT_GAMUT, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda11
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.cvtGamut(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CVT_HDR2SDR, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda12
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.cvtHdr2Sdr(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.ROTATE, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda13
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.rotate(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CROP, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda14
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.crop(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.SPLIT, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda15
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.split(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.MERGE, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda16
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.merge(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.QUALITY_MEASURE, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda2
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.measureQuality(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.DECODE, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda3
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.decode(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.ENCODE, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda4
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.encode(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.ENCODE_HDR, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda5
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.encodeHDR(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.FLIP, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda6
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.flip(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CREATE_GAINMAP, new Operator() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda7
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return NativeUniImgpPlugin.this.createGainmap(mediaBuffer, mutableMediaBuffer);
            }
        });
    }

    private JSONObject bufferToJson(MediaBuffer mediaBuffer) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cols", mediaBuffer.getCols());
            jSONObject.put("rows", mediaBuffer.getRows());
            jSONObject.put("data-type", mediaBuffer.getFormat().getDataType().stringfy());
            jSONObject.put(android.media.MediaFormat.KEY_COLOR_FORMAT, mediaBuffer.getFormat().getColorFormat().stringfy());
            jSONObject.put("color-space", mediaBuffer.getFormat().getColorSpace().stringfy());
            jSONObject.put(GenerateXML.ROTATION, mediaBuffer.getFormat().getRotation());
            if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                jSONObject.put(Message.KEY_IN_FILE, (String) mediaBuffer.getExtra(Message.KEY_IN_FILE));
            }
            if (mediaBuffer.containsExtra(Message.KEY_OUT_FILE)) {
                jSONObject.put(Message.KEY_OUT_FILE, (String) mediaBuffer.getExtra(Message.KEY_OUT_FILE));
            }
            if (mediaBuffer.containsExtra("exposure-value")) {
                jSONObject.put("exposure-value", ((Integer) mediaBuffer.getExtra("exposure-value")).intValue());
            }
            if (mediaBuffer.getFormat().getCodecType() != CodecType.NONE) {
                jSONObject.put("codec-type", mediaBuffer.getFormat().getCodecType().stringfy());
            }
            if (mediaBuffer.getFormat().contains("scale")) {
                jSONObject.put("scale", ((Float) mediaBuffer.getFormat().get("scale", Float.valueOf(1.0f))).floatValue());
            }
            if (mediaBuffer.getFormat().getCropRect() != null) {
                jSONObject.put("crop-rect", mediaBuffer.getFormat().getCropRect().flattenToString());
            }
            if (mediaBuffer.getFormat().getSplitType() != SplitType.NONE) {
                jSONObject.put("split-type", mediaBuffer.getFormat().getSplitType().stringfy());
            }
            if (mediaBuffer.getFormat().getFlipType() != FlipType.NONE) {
                jSONObject.put("flip-type", mediaBuffer.getFormat().getFlipType().stringfy());
            }
            if (mediaBuffer.containsAllExtra("row-offset", "scan-lines")) {
                jSONObject.put("row-offset", ((Float) mediaBuffer.getExtra("row-offset")).doubleValue());
                jSONObject.put("scan-lines", ((Float) mediaBuffer.getExtra("scan-lines")).doubleValue());
            }
            if (mediaBuffer.containsAllExtra("roi-on-image", "roi-on-block")) {
                jSONObject.put("roi-on-image", ((Rect) mediaBuffer.getExtra("roi-on-image")).flattenToString());
                jSONObject.put("roi-on-block", ((Rect) mediaBuffer.getExtra("roi-on-block")).flattenToString());
            }
            if (mediaBuffer.getFormat().contains("pad-type")) {
                jSONObject.put("pad-type", ((PadType) mediaBuffer.getFormat().get("pad-type", PadType.NONE)).stringfy());
            }
            if (mediaBuffer.getFormat().contains("pad-size")) {
                jSONObject.put("pad-size", ((Integer) mediaBuffer.getFormat().get("pad-size", 0)).intValue());
                return jSONObject;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private JSONObject createJsonImgpOption(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (mediaBuffer2.getFormat().getSplitType() != SplitType.NONE) {
                jSONObject.put("split-type", mediaBuffer2.getFormat().getSplitType().stringfy());
            }
            if (mediaBuffer2.getFormat().getFlipType() != FlipType.NONE) {
                jSONObject.put("flip-type", mediaBuffer2.getFormat().getFlipType().stringfy());
            }
            if (mediaBuffer2.getFormat().contains("pad-type")) {
                jSONObject.put("pad-type", ((PadType) mediaBuffer2.getFormat().get("pad-type", PadType.NONE)).stringfy());
            }
            if (mediaBuffer.isNotEmpty() && mediaBuffer2.isNotEmpty() && mediaBuffer.getData().equals(mediaBuffer2.getData())) {
                jSONObject.put("prefer-fast", true);
                return jSONObject;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private Object getNativeSupportBuffer(MediaBuffer mediaBuffer) {
        if (mediaBuffer.isEmpty()) {
            return null;
        }
        Class<?> dataClass = mediaBuffer.getDataClass();
        if (HardwareBuffer.class.isAssignableFrom(dataClass) || Image.class.isAssignableFrom(dataClass)) {
            return mediaBuffer.getTypedData(HardwareBuffer.class);
        }
        if (Bitmap.class.isAssignableFrom(dataClass)) {
            return mediaBuffer.getTypedData(Bitmap.class);
        }
        Log.d(TAG, "convert as default... (ByteBuffer)");
        return mediaBuffer.getTypedData(ByteBuffer.class);
    }

    public MutableMediaBuffer resize(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            if (mutableMediaBuffer.getFormat().contains("scale")) {
                float floatValue = ((Float) mutableMediaBuffer.getFormat().get("scale")).floatValue();
                copy.setCols((int) (mediaBuffer.getCols() * floatValue));
                copy.setRows((int) (mediaBuffer.getRows() * floatValue));
            } else {
                copy.setShape(mutableMediaBuffer.getFormat().getShape());
            }
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        Def.check(Status.from(nativeResize(bufferToJson(mediaBuffer).toString(), directByteBuffer, bufferToJson(mutableMediaBuffer).toString(), toDirectByteBuffer((ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class)))) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer cvtColor(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        if (mediaBuffer.getFormat().getColorFormat() == mutableMediaBuffer.getFormat().getColorFormat()) {
            mutableMediaBuffer.put(mediaBuffer);
            return mutableMediaBuffer;
        }
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            copy.setColorFormat(mutableMediaBuffer.getFormat().getColorFormat());
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        Def.check(Status.from(nativeCvtColor(bufferToJson(mediaBuffer).toString(), directByteBuffer, bufferToJson(mutableMediaBuffer).toString(), toDirectByteBuffer((ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class)))) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer cvtData(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        if (mediaBuffer.getFormat().getDataType() == mutableMediaBuffer.getFormat().getDataType()) {
            mutableMediaBuffer.put(mediaBuffer);
            return mutableMediaBuffer;
        }
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            copy.setDataType(mutableMediaBuffer.getFormat().getDataType());
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        Def.check(Status.from(nativeCvtData(bufferToJson(mediaBuffer).toString(), directByteBuffer, bufferToJson(mutableMediaBuffer).toString(), toDirectByteBuffer((ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class)))) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer cvtGamut(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        MediaBuffer of;
        MediaBuffer of2;
        String str = TAG;
        Log.d(str, "ibuf=" + mediaBuffer);
        Log.d(str, "obuf=" + mutableMediaBuffer);
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            copy.setColorSpace(mutableMediaBuffer.getFormat().getColorSpace());
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        ColorSpace colorSpace = mediaBuffer.getFormat().getColorSpace();
        ColorSpace colorSpace2 = mutableMediaBuffer.getFormat().getColorSpace();
        if (colorSpace == ColorSpace.DISPLAY_P3) {
            of = mediaBuffer;
        } else {
            Log.d(str, "not supported colorSpace. force" + colorSpace + " to display-p3");
            MutableMediaFormat mutableFormat = mediaBuffer.getFormat().toMutableFormat();
            mutableFormat.setColorSpace(ColorSpace.DISPLAY_P3);
            of = MediaBuffer.of(mutableFormat, mediaBuffer.getData());
        }
        if (colorSpace2 == ColorSpace.BT709_FR) {
            of2 = mutableMediaBuffer;
        } else {
            Log.d(str, "not supported colorSpace. force" + colorSpace2 + " to bt709-fr");
            MutableMediaFormat mutableFormat2 = mutableMediaBuffer.getFormat().toMutableFormat();
            mutableFormat2.setColorSpace(ColorSpace.BT709_FR);
            of2 = MediaBuffer.of(mutableFormat2, mutableMediaBuffer.getData());
        }
        Def.check(Status.from(nativeCvtGamutV2(bufferToJson(mediaBuffer).toString(), getNativeSupportBuffer(of), bufferToJson(mutableMediaBuffer).toString(), getNativeSupportBuffer(of2))) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer rotate(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        if (mutableMediaBuffer.getFormat().getRotation() == 0) {
            mutableMediaBuffer.put(mediaBuffer);
            return mutableMediaBuffer;
        }
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            copy.setRotation(mutableMediaBuffer.getFormat().getRotation());
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        Def.check(Status.from(nativeRotate(bufferToJson(mediaBuffer).toString(), directByteBuffer, bufferToJson(mutableMediaBuffer).toString(), toDirectByteBuffer((ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class)))) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer crop(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        if (mutableMediaBuffer.getFormat().getCropRect() == null) {
            mutableMediaBuffer.put(mediaBuffer);
            return mutableMediaBuffer;
        }
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            copy.setRotation(mutableMediaBuffer.getFormat().getRotation());
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        ByteBuffer directByteBuffer2 = toDirectByteBuffer((ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class));
        JSONObject bufferToJson = bufferToJson(mediaBuffer);
        JSONObject bufferToJson2 = bufferToJson(mutableMediaBuffer);
        try {
            if (mutableMediaBuffer.getFormat().getCropRect() != null) {
                bufferToJson2.put("crop-rect", mutableMediaBuffer.getFormat().getCropRect().flattenToString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Def.check(Status.from(nativeCrop(bufferToJson.toString(), directByteBuffer, bufferToJson2.toString(), directByteBuffer2)) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer split(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        HashMap<String, Object> hashMap = new HashMap<>();
        Def.check(Status.from(nativeSplit(createJsonImgpOption(mediaBuffer, mutableMediaBuffer).toString(), bufferToJson(mediaBuffer).toString(), toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)), bufferToJson(mutableMediaBuffer).toString(), hashMap)) == Status.OK);
        MediaBuffer makeBufferFromMap = makeBufferFromMap(hashMap);
        if (makeBufferFromMap != null) {
            mutableMediaBuffer.put(makeBufferFromMap);
        }
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer merge(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        HashMap<String, Object> hashMap = new HashMap<>();
        ByteBuffer directByteBuffer = mediaBuffer.getData() != null ? toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)) : null;
        if (mutableMediaBuffer.getFormat() == null || mutableMediaBuffer.getFormat().getShape() == null) {
            MutableMediaFormat mutableFormat = mutableMediaBuffer.getFormat().toMutableFormat();
            mutableFormat.setShape(mediaBuffer.getFormat().getShape());
            mutableMediaBuffer.setFormat(mutableFormat);
        }
        String str = TAG;
        Log.d(str, mediaBuffer.toString());
        Log.d(str, mutableMediaBuffer.toString());
        JSONObject bufferToJson = bufferToJson(mediaBuffer);
        JSONObject bufferToJson2 = bufferToJson(mutableMediaBuffer);
        List<MediaBuffer> asList = mediaBuffer.asList();
        try {
            bufferToJson.put("block-num", asList.size());
            int i = 0;
            for (MediaBuffer mediaBuffer2 : asList) {
                hashMap.put("block" + i + "-format", bufferToJson(mediaBuffer2).toString());
                hashMap.put("block" + i + "-data", toDirectByteBuffer((ByteBuffer) mediaBuffer2.getTypedData(ByteBuffer.class)));
                i++;
            }
            Def.check(Status.from(nativeMerge(createJsonImgpOption(mediaBuffer, mutableMediaBuffer).toString(), bufferToJson.toString(), directByteBuffer, bufferToJson2.toString(), hashMap)) == Status.OK);
            MediaBuffer makeBufferFromMap = makeBufferFromMap(hashMap);
            if (makeBufferFromMap != null) {
                mutableMediaBuffer.put(makeBufferFromMap);
            }
            return mutableMediaBuffer;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MutableMediaBuffer measureQuality(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        Def.check(mediaBuffer.asList().size() == 2, "ibuf size is not 2", new Object[0]);
        if (!mutableMediaBuffer.getFormat().contains("quality-metric")) {
            Log.d(TAG, "quality-metric is not given. force to PSNR");
        } else if (((Integer) mutableMediaBuffer.getFormat().get("quality-metric")).intValue() != 0) {
            throw new UnsupportedOperationException("currently only PSNR is supported!");
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        Def.check(Status.from(nativeMeasureQuality(bufferToJson(mediaBuffer.asList().get(0)).toString(), toDirectByteBuffer((ByteBuffer) mediaBuffer.asList().get(0).getTypedData(ByteBuffer.class)), bufferToJson(mediaBuffer.asList().get(1)).toString(), toDirectByteBuffer((ByteBuffer) mediaBuffer.asList().get(1).getTypedData(ByteBuffer.class)), hashMap)) == Status.OK);
        mutableMediaBuffer.put(MediaBuffer.scalaOf(DataType.F32C1, Shape.of(1, 1), (Float) hashMap.get("quality-value")));
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer decode(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        HashMap<String, Object> hashMap = new HashMap<>();
        String str = TAG;
        Log.d(str, mediaBuffer.toString());
        Log.d(str, mutableMediaBuffer.toString());
        JSONObject bufferToJson = bufferToJson(mediaBuffer);
        JSONObject bufferToJson2 = bufferToJson(mutableMediaBuffer);
        if (mediaBuffer.getDataClass() == FileDescriptor.class) {
            hashMap.put("infile-descriptor", mediaBuffer.getTypedData(FileDescriptor.class));
        } else if (mediaBuffer.getFormat().contains(Message.KEY_FILE_DESCRIPTOR)) {
            hashMap.put("infile-descriptor", mediaBuffer.getFormat().get(Message.KEY_FILE_DESCRIPTOR));
        }
        Def.check(Status.from(nativeDecode(bufferToJson.toString(), null, bufferToJson2.toString(), hashMap)) == Status.OK);
        MediaBuffer makeBufferFromMap = makeBufferFromMap(hashMap);
        if (makeBufferFromMap != null) {
            mutableMediaBuffer.put(makeBufferFromMap);
        }
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer encode(MediaBuffer mediaBuffer, final MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        final HashMap<String, Object> hashMap = new HashMap<>();
        mediaBuffer.asList().forEach(new Consumer() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NativeUniImgpPlugin.this.m9599xdfdf96db(mutableMediaBuffer, hashMap, (MediaBuffer) obj);
            }
        });
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        JSONObject bufferToJson = bufferToJson(mediaBuffer);
        JSONObject bufferToJson2 = bufferToJson(mutableMediaBuffer);
        try {
            if (mutableMediaBuffer.getFormat().contains(Message.KEY_OUT_FILE)) {
                bufferToJson2.put(Message.KEY_OUT_FILE, (String) mutableMediaBuffer.getFormat().get(Message.KEY_OUT_FILE));
            }
            if (mutableMediaBuffer.getFormat().contains(Message.KEY_FILE_DESCRIPTOR)) {
                hashMap.put("outfile-descriptor", (FileDescriptor) mutableMediaBuffer.getFormat().get(Message.KEY_FILE_DESCRIPTOR));
            }
            if (mutableMediaBuffer.getFormat().getCodecType() != CodecType.NONE) {
                bufferToJson2.put("codec-type", mutableMediaBuffer.getFormat().getCodecType().stringfy());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Def.check(Status.from(nativeEncode(bufferToJson.toString(), directByteBuffer, bufferToJson2.toString(), hashMap)) == Status.OK);
        return mutableMediaBuffer;
    }

    /* renamed from: lambda$encode$1$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ void m9599xdfdf96db(MutableMediaBuffer mutableMediaBuffer, HashMap hashMap, MediaBuffer mediaBuffer) {
        if (mediaBuffer.getFormat().contains("exif")) {
            if (mutableMediaBuffer.getFormat().getColorFormat().isPlanar()) {
                mediaBuffer = adjustExif(mediaBuffer);
            }
            hashMap.put("exif", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
        } else {
            if (mediaBuffer.getFormat().contains("icc")) {
                hashMap.put("icc", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
                return;
            }
            Log.w(TAG, "Unused buffer is given for encoding" + mediaBuffer);
        }
    }

    public MutableMediaBuffer flip(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        String str = TAG;
        Log.d(str, "ibuf=" + mediaBuffer);
        Log.d(str, "obuf=" + mutableMediaBuffer);
        Object nativeSupportBuffer = getNativeSupportBuffer(mediaBuffer);
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat copy = mediaBuffer.getFormat().toMutableFormat().copy();
            copy.setFlipType(mutableMediaBuffer.getFormat().getFlipType());
            mutableMediaBuffer.put(MediaBuffer.of(copy));
        }
        Def.check(Status.from(nativeFlipV2(createJsonImgpOption(mediaBuffer, mutableMediaBuffer).toString(), bufferToJson(mediaBuffer).toString(), nativeSupportBuffer, bufferToJson(mutableMediaBuffer).toString(), getNativeSupportBuffer(mutableMediaBuffer))) == Status.OK);
        return mutableMediaBuffer;
    }

    public MutableMediaBuffer encodeHDR(MediaBuffer mediaBuffer, final MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        if (!isHDRSupported()) {
            throw new UnsupportedOperationException("HDR is not supported!");
        }
        final HashMap<String, Object> hashMap = new HashMap<>();
        Log.d(TAG, mutableMediaBuffer.toString());
        mediaBuffer.asList().forEach(new Consumer() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NativeUniImgpPlugin.this.m9600x6bd652a(mutableMediaBuffer, hashMap, (MediaBuffer) obj);
            }
        });
        ByteBuffer directByteBuffer = toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class));
        JSONObject bufferToJson = bufferToJson(mediaBuffer);
        JSONObject bufferToJson2 = bufferToJson(mutableMediaBuffer);
        try {
            if (mutableMediaBuffer.getFormat().contains(Message.KEY_OUT_FILE)) {
                bufferToJson2.put(Message.KEY_OUT_FILE, (String) mutableMediaBuffer.getFormat().get(Message.KEY_OUT_FILE));
            }
            if (mutableMediaBuffer.getFormat().contains(Message.KEY_FILE_DESCRIPTOR)) {
                hashMap.put("outfile-descriptor", (FileDescriptor) mutableMediaBuffer.getFormat().get(Message.KEY_FILE_DESCRIPTOR));
            }
            if (mutableMediaBuffer.getFormat().getCodecType() != CodecType.NONE) {
                if (mutableMediaBuffer.getFormat().getCodecType() == CodecType.HEIF && !isHeifSupported(mediaBuffer, mutableMediaBuffer.getFormat().getShape())) {
                    throw new UnsupportedOperationException("encode size must bigger than [512x512]");
                }
                bufferToJson2.put("codec-type", mutableMediaBuffer.getFormat().getCodecType().stringfy());
            }
            if (mutableMediaBuffer.containsExtra("exposure-value")) {
                bufferToJson2.put("exposure-value", ((Integer) mutableMediaBuffer.getExtra("exposure-value")).intValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Def.check(Status.from(nativeEncodeHDR(bufferToJson.toString(), directByteBuffer, bufferToJson2.toString(), hashMap)) == Status.OK);
        return mutableMediaBuffer;
    }

    /* renamed from: lambda$encodeHDR$2$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ void m9600x6bd652a(MutableMediaBuffer mutableMediaBuffer, HashMap hashMap, MediaBuffer mediaBuffer) {
        if (mediaBuffer.getFormat().contains("exif")) {
            if (mutableMediaBuffer.getFormat().getColorFormat().isPlanar()) {
                mediaBuffer = adjustExif(mediaBuffer);
            }
            hashMap.put("exif", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
        } else if (mediaBuffer.getFormat().contains("icc")) {
            hashMap.put("icc", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
        } else if (mediaBuffer.getFormat().contains("gain-map")) {
            hashMap.put("gain-map-format", bufferToJson(mediaBuffer).toString());
            hashMap.put("gain-map", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
        }
    }

    public MutableMediaBuffer createGainmap(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        if (!isHDRSupported()) {
            throw new UnsupportedOperationException("HDR is not supported!");
        }
        String str = TAG;
        Log.d(str, "create Gain-map");
        HashMap<String, Object> hashMap = new HashMap<>();
        JSONObject bufferToJson = bufferToJson(mediaBuffer);
        JSONObject bufferToJson2 = bufferToJson(mutableMediaBuffer);
        Bitmap bitmap = (Bitmap) mediaBuffer.getTypedData(Bitmap.class);
        hashMap.put("jbitmap", bitmap);
        Def.check(Status.from(nativeCreateGainmap(bufferToJson.toString(), null, bufferToJson2.toString(), hashMap)) == Status.OK);
        MediaBuffer makeBufferFromMap = makeBufferFromMap(hashMap);
        Log.d(str, makeBufferFromMap.toString());
        Gainmap gainmap = new Gainmap((Bitmap) makeBufferFromMap.getTypedData(Bitmap.class));
        float floatValue = ((Float) makeBufferFromMap.getExtra("max-content-boost")).floatValue();
        float floatValue2 = ((Float) makeBufferFromMap.getExtra("min-content-boost")).floatValue();
        float floatValue3 = ((Float) makeBufferFromMap.getExtra(MediaQualityContract.PictureQuality.PARAMETER_GAMMA)).floatValue();
        float floatValue4 = ((Float) makeBufferFromMap.getExtra("offset-hdr")).floatValue();
        float floatValue5 = ((Float) makeBufferFromMap.getExtra("offset-sdr")).floatValue();
        float floatValue6 = ((Float) makeBufferFromMap.getExtra("max-hdr-capacity")).floatValue();
        float floatValue7 = ((Float) makeBufferFromMap.getExtra("min-hdr-capacity")).floatValue();
        float pow = (float) Math.pow(2.0d, floatValue);
        float pow2 = (float) Math.pow(2.0d, floatValue2);
        gainmap.setRatioMax(pow, pow, pow);
        gainmap.setRatioMin(pow2, pow2, pow2);
        gainmap.setGamma(floatValue3, floatValue3, floatValue3);
        gainmap.setEpsilonHdr(floatValue4, floatValue4, floatValue4);
        gainmap.setEpsilonSdr(floatValue5, floatValue5, floatValue5);
        gainmap.setDisplayRatioForFullHdr((float) Math.pow(2.0d, floatValue6));
        gainmap.setMinDisplayRatioForHdrTransition((float) Math.pow(2.0d, floatValue7));
        bitmap.setGainmap(gainmap);
        mutableMediaBuffer.put(MediaBuffer.of(mediaBuffer.getFormat(), bitmap));
        return mutableMediaBuffer;
    }

    public MediaBuffer readCompressedImage(MediaFormat mediaFormat, String str) {
        String str2 = TAG;
        Log.d(str2, "read compressed image: " + str);
        String lowerCase = str.substring(str.lastIndexOf(46) + 1).toLowerCase(Locale.ROOT);
        if (!lowerCase.equals("jpg") && !lowerCase.equals("heic")) {
            throw new UnsupportedOperationException("not supported yet");
        }
        MutableMediaBuffer mutableMediaBuffer = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                MediaBuffer compressedImageOf = MediaBuffer.compressedImageOf(fileInputStream.getFD());
                MutableMediaFormat mutableFormat = mediaFormat.toMutableFormat();
                if (mediaFormat.getColorFormat() == ColorFormat.NONE) {
                    mutableFormat.setColorFormat(ColorFormat.RGBA);
                }
                Log.d(str2, "decode format: " + mutableFormat);
                mutableMediaBuffer = MediaBuffer.mutableOf(mutableFormat);
                decode(compressedImageOf, mutableMediaBuffer);
                fileInputStream.close();
                return mutableMediaBuffer;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return mutableMediaBuffer;
        }
    }

    public boolean writeCompressedImage(MediaBuffer mediaBuffer, String str) {
        Log.d(TAG, "write compressed image: " + str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                FileDescriptor fd = fileOutputStream.getFD();
                MutableMediaFormat mutableCompressedImageOf = MediaFormat.mutableCompressedImageOf(ColorFormat.NV12);
                mutableCompressedImageOf.set(Message.KEY_FILE_DESCRIPTOR, fd);
                String lowerCase = str.substring(str.lastIndexOf(46) + 1).toLowerCase(Locale.ROOT);
                if (lowerCase.equals("jpg")) {
                    mutableCompressedImageOf.setCodecType(CodecType.JPEG_QURAM);
                } else if (lowerCase.equals("heic")) {
                    mutableCompressedImageOf.setCodecType(CodecType.HEIF);
                } else {
                    throw new UnsupportedOperationException("not supported yet");
                }
                if (mediaBuffer.getFormat().contains("encode-shape")) {
                    mutableCompressedImageOf.setShape((Shape) mediaBuffer.getFormat().get("encode-shape"));
                }
                MutableMediaBuffer mutableOf = MediaBuffer.mutableOf(mutableCompressedImageOf);
                if (mediaBuffer.getFormat().contains("encode-hdr")) {
                    encodeHDR(mediaBuffer, mutableOf);
                } else {
                    encode(mediaBuffer, mutableOf);
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "succes to save" + str);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x021b  */
    @Override // com.samsung.android.sume.core.functional.Operator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.samsung.android.sume.core.buffer.MutableMediaBuffer run(com.samsung.android.sume.core.buffer.MediaBuffer r15, com.samsung.android.sume.core.buffer.MutableMediaBuffer r16) throws java.lang.UnsupportedOperationException {
        /*
            Method dump skipped, instructions count: 573
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin.run(com.samsung.android.sume.core.buffer.MediaBuffer, com.samsung.android.sume.core.buffer.MutableMediaBuffer):com.samsung.android.sume.core.buffer.MutableMediaBuffer");
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ void m9602xff46b99e(HashMap hashMap, MediaBuffer mediaBuffer) {
        if (mediaBuffer.getFormat().contains("exif")) {
            hashMap.put("exif", toDirectByteBuffer((ByteBuffer) ((this.persistentOutputFormat.getColorFormat().isPlanar() || this.preferredColorFormat.isPlanar()) ? adjustExif(mediaBuffer) : mediaBuffer).getTypedData(ByteBuffer.class)));
        } else if (mediaBuffer.getFormat().contains("icc")) {
            hashMap.put("icc", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
        } else if (mediaBuffer.getFormat().contains("gain-map")) {
            JSONObject bufferToJson = bufferToJson(mediaBuffer);
            try {
                bufferToJson.put("gain-map", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            hashMap.put("gain-map-format", bufferToJson.toString());
            hashMap.put("gain-map", toDirectByteBuffer((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class)));
        }
        if (mediaBuffer.containsExtra("thumbnail")) {
            hashMap.put("thumbnail", mediaBuffer.getTypedData(ByteBuffer.class));
        }
    }

    private ByteBuffer toDirectByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        if (byteBuffer.isDirect()) {
            return byteBuffer;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.limit());
        allocateDirect.put(byteBuffer);
        return allocateDirect;
    }

    private MediaBuffer adjustExif(MediaBuffer mediaBuffer) {
        UniExifInterface uniExifInterface = (UniExifInterface) mediaBuffer.getTypedData(UniExifInterface.class);
        int attributeInt = uniExifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_X_DIMENSION, 0);
        int attributeInt2 = uniExifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_Y_DIMENSION, 0);
        if ((attributeInt & 1) == 0 && (attributeInt2 & 1) == 0) {
            return mediaBuffer;
        }
        int i = (attributeInt >> 1) << 1;
        uniExifInterface.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, String.valueOf(i));
        int i2 = (attributeInt2 >> 1) << 1;
        uniExifInterface.setAttribute(ExifInterface.TAG_IMAGE_LENGTH, String.valueOf(i2));
        uniExifInterface.setAttribute(ExifInterface.TAG_PIXEL_X_DIMENSION, String.valueOf(i));
        uniExifInterface.setAttribute(ExifInterface.TAG_PIXEL_Y_DIMENSION, String.valueOf(i2));
        Log.d(TAG, "adjust exif to... [" + i + ", " + i2 + NavigationBarInflaterView.SIZE_MOD_END);
        try {
            uniExifInterface.saveAttributes();
            return MediaBuffer.metadataBufferOf(1, uniExifInterface);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractExtraFromJson(MediaBuffer mediaBuffer, JSONObject jSONObject) {
        String str;
        String str2;
        String str3;
        char c;
        try {
            if (jSONObject.has("row-offset")) {
                mediaBuffer.setExtra("row-offset", Float.valueOf((float) jSONObject.getDouble("row-offset")));
            }
            if (jSONObject.has("scan-lines")) {
                mediaBuffer.setExtra("scan-lines", Float.valueOf((float) jSONObject.getDouble("scan-lines")));
            }
            if (!jSONObject.has("roi-on-block")) {
                str = "offset-sdr";
                str2 = "offset-hdr";
                str3 = MediaQualityContract.PictureQuality.PARAMETER_GAMMA;
                c = 0;
            } else {
                String[] split = jSONObject.getString("roi-on-block").replaceAll("[^0-9|,]", "").split(",");
                c = 0;
                int parseInt = Integer.parseInt(split[0]);
                str = "offset-sdr";
                int parseInt2 = Integer.parseInt(split[1]);
                str2 = "offset-hdr";
                int parseInt3 = Integer.parseInt(split[2]);
                String str4 = split[3];
                str3 = MediaQualityContract.PictureQuality.PARAMETER_GAMMA;
                mediaBuffer.setExtra("roi-on-block", new Rect(parseInt, parseInt2, parseInt3, Integer.parseInt(str4)));
            }
            if (jSONObject.has("roi-on-image")) {
                String[] split2 = jSONObject.getString("roi-on-image").replaceAll("[^0-9|,]", "").split(",");
                mediaBuffer.setExtra("roi-on-image", new Rect(Integer.parseInt(split2[c]), Integer.parseInt(split2[1]), Integer.parseInt(split2[2]), Integer.parseInt(split2[3])));
            }
            if (jSONObject.has("max-content-boost")) {
                mediaBuffer.setExtra("max-content-boost", Float.valueOf((float) jSONObject.getDouble("max-content-boost")));
            }
            if (jSONObject.has("min-content-boost")) {
                mediaBuffer.setExtra("min-content-boost", Float.valueOf((float) jSONObject.getDouble("min-content-boost")));
            }
            if (jSONObject.has("max-hdr-capacity")) {
                mediaBuffer.setExtra("max-hdr-capacity", Float.valueOf((float) jSONObject.getDouble("max-hdr-capacity")));
            }
            if (jSONObject.has("min-hdr-capacity")) {
                mediaBuffer.setExtra("min-hdr-capacity", Float.valueOf((float) jSONObject.getDouble("min-hdr-capacity")));
            }
            String str5 = str3;
            if (jSONObject.has(str5)) {
                mediaBuffer.setExtra(str5, Float.valueOf((float) jSONObject.getDouble(str5)));
            }
            String str6 = str2;
            if (jSONObject.has(str6)) {
                mediaBuffer.setExtra(str6, Float.valueOf((float) jSONObject.getDouble(str6)));
            }
            String str7 = str;
            if (jSONObject.has(str7)) {
                mediaBuffer.setExtra(str7, Float.valueOf((float) jSONObject.getDouble(str7)));
            }
        } catch (JSONException e) {
            throw new IllegalStateException("failed to get object from bufferObject: " + e);
        }
    }

    private MediaBuffer makeImageBuffer(String str, Object obj) {
        MediaBuffer mediaBuffer;
        MutableMediaFormat mutableImageOf = MediaFormat.mutableImageOf(new Object[0]);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("cols")) {
                mutableImageOf.setCols(jSONObject.getInt("cols"));
            }
            if (jSONObject.has("rows")) {
                mutableImageOf.setRows(jSONObject.getInt("rows"));
            }
            if (jSONObject.has("data-type")) {
                mutableImageOf.setDataType(DataType.valueOf(jSONObject.getString("data-type")));
            }
            if (jSONObject.has(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
                mutableImageOf.setColorFormat(ColorFormat.valueOf(jSONObject.getString(android.media.MediaFormat.KEY_COLOR_FORMAT)));
            }
            if (jSONObject.has("color-space")) {
                mutableImageOf.setColorSpace(ColorSpace.valueOf(jSONObject.getString("color-space")));
            }
            if (obj instanceof ByteBuffer) {
                mediaBuffer = MediaBuffer.of(mutableImageOf, (ByteBuffer) obj);
            } else if (obj instanceof Bitmap) {
                mediaBuffer = MediaBuffer.of(mutableImageOf, (Bitmap) obj);
            } else {
                Log.d(TAG, "do nothing to obuf");
                mediaBuffer = null;
            }
            if (mediaBuffer != null) {
                extractExtraFromJson(mediaBuffer, jSONObject);
            }
            return mediaBuffer;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private MediaBuffer makeBufferFromMap(final HashMap<String, Object> hashMap) {
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2;
        ArrayList arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        MediaBuffer makeImageBuffer = hashMap.containsKey(Message.KEY_OUT_BUFFER) ? makeImageBuffer((String) hashMap.get(Message.KEY_OUT_BUFFER), hashMap.get("output-data")) : null;
        Integer num = (Integer) hashMap.get("block-num");
        if (num != null) {
            Log.d(TAG, "block num: " + num);
            arrayList2 = (List) IntStream.range(0, num.intValue()).mapToObj(new IntFunction() { // from class: com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda19
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return NativeUniImgpPlugin.this.m9601x5755f344(hashMap, i);
                }
            }).collect(Collectors.toList());
        }
        if (hashMap.containsKey("exif") && (byteBuffer2 = (ByteBuffer) hashMap.get("exif")) != null && byteBuffer2.isDirect()) {
            arrayList.add(MediaBuffer.metadataBufferOf(1, byteBuffer2));
        }
        if (hashMap.containsKey("icc") && (byteBuffer = (ByteBuffer) hashMap.get("icc")) != null && byteBuffer.isDirect()) {
            arrayList.add(MediaBuffer.metadataBufferOf(2, byteBuffer));
        }
        if (arrayList2.size() > 1) {
            if (arrayList.size() > 0) {
                arrayList2.addAll(arrayList);
            }
            return MediaBuffer.groupOf(makeImageBuffer, (List<MediaBuffer>) arrayList2);
        }
        if (makeImageBuffer == null || arrayList.size() <= 0) {
            return makeImageBuffer;
        }
        arrayList.add(0, makeImageBuffer);
        return MediaBuffer.groupOf(makeImageBuffer, arrayList);
    }

    /* renamed from: lambda$makeBufferFromMap$4$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ MediaBuffer m9601x5755f344(HashMap hashMap, int i) {
        return makeImageBuffer((String) hashMap.get("block" + i + "-buffer"), hashMap.get("block" + i + "-data"));
    }

    private boolean isHeifSupported(MediaBuffer mediaBuffer, Shape shape) {
        if (mediaBuffer.getDataClass() == FileDescriptor.class) {
            return true;
        }
        return (shape == null || shape.getDimension() <= 0) ? mediaBuffer.getCols() >= 512 && mediaBuffer.getRows() >= 512 : shape.getCols() >= 512 && shape.getRows() >= 512;
    }

    private boolean isHDRSupported() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_PHOTOHDR");
    }
}
