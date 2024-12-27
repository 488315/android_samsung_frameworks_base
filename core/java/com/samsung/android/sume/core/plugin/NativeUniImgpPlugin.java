package com.samsung.android.sume.core.plugin;

import android.graphics.Bitmap;
import android.graphics.Gainmap;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.ExifInterface;
import android.media.Image;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/* loaded from: classes6.dex */
public class NativeUniImgpPlugin implements Plugin<ImgpPlugin>, Operator {
    private static final String TAG = Def.tagOf((Class<?>) NativeUniImgpPlugin.class);
    private final ReentrantLock lock = new ReentrantLock();
    private long nativeContext;
    private MediaFormat persistentInputFormat;
    private MediaFormat persistentOutputFormat;
    private ColorFormat preferredColorFormat;

    private native int nativeCreateGainmap(
            String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeCrop(
            String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeCvtColor(
            String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeCvtData(
            String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeCvtGamutV2(String str, Object obj, String str2, Object obj2);

    private native int nativeDecode(
            String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeEncode(
            String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeEncodeHDR(
            String str, ByteBuffer byteBuffer, String str2, HashMap<String, Object> hashMap);

    private native int nativeFlipV2(String str, String str2, Object obj, String str3, Object obj2);

    private native int nativeInit(String str, String str2);

    private native int nativeMeasureQuality(
            String str,
            ByteBuffer byteBuffer,
            String str2,
            ByteBuffer byteBuffer2,
            HashMap<String, Object> hashMap);

    private native int nativeMerge(
            String str,
            String str2,
            ByteBuffer byteBuffer,
            String str3,
            HashMap<String, Object> hashMap);

    private native int nativeRelease();

    private native int nativeResize(
            String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeRotate(
            String str, ByteBuffer byteBuffer, String str2, ByteBuffer byteBuffer2);

    private native int nativeRun(
            String str,
            ByteBuffer byteBuffer,
            String str2,
            ByteBuffer byteBuffer2,
            HashMap<String, Object> hashMap);

    private static native void nativeSetup();

    private native int nativeSplit(
            String str,
            String str2,
            ByteBuffer byteBuffer,
            String str3,
            HashMap<String, Object> hashMap);

    static {
        System.loadLibrary("sume_jni.media.samsung");
        nativeSetup();
    }

    public NativeUniImgpPlugin() {
        Log.d(
                TAG,
                "NativeUniImgpPlugin: version= [core="
                        + Def.getCoreVersion()
                        + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public NativeUniImgpPlugin(
            List<ImgpType> opList,
            MediaFormat inputFormat,
            MediaFormat outputFormat,
            ColorFormat preferredColorFormat) {
        Log.d(
                TAG,
                "NativeUniImgpPlugin: version= [core="
                        + Def.getCoreVersion()
                        + NavigationBarInflaterView.SIZE_MOD_END);
        Log.d(TAG, "opList=" + opList);
        this.persistentInputFormat = inputFormat;
        this.persistentOutputFormat = outputFormat;
        if (preferredColorFormat != null) {
            this.preferredColorFormat = preferredColorFormat;
        } else {
            this.preferredColorFormat = ColorFormat.NONE;
        }
        JSONArray opArray = new JSONArray();
        for (ImgpType op : opList) {
            if (op == ImgpType.ENCODE_HDR && !isHDRSupported()) {
                throw new UnsupportedOperationException("HDR is not supported!");
            }
            opArray.put(op.stringfy());
        }
        JSONObject optionObject = new JSONObject();
        try {
            if (this.preferredColorFormat != ColorFormat.NONE) {
                optionObject.put("preferred-color-format", this.preferredColorFormat.stringfy());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nativeInit(opArray.toString(), optionObject.toString());
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
    public void bindToFixture(ImgpPlugin fixture) {
        fixture.setDescriptorLoader(
                new DescriptorLoader() { // from class:
                                         // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda1
                    @Override // com.samsung.android.sume.core.functional.DescriptorLoader
                    public final MFDescriptor load() {
                        return NativeUniImgpPlugin.lambda$bindToFixture$0();
                    }
                });
        fixture.setImgProcessor(
                ImgpType.RESIZE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda8
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.resize(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CVT_COLOR,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda9
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.cvtColor(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CVT_DATA,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda10
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.cvtData(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CVT_GAMUT,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda11
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.cvtGamut(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CVT_HDR2SDR,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda12
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.cvtHdr2Sdr(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.ROTATE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda13
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.rotate(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CROP,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda14
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.crop(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.SPLIT,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda15
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.split(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.MERGE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda16
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.merge(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.QUALITY_MEASURE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda2
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.measureQuality(
                                mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.DECODE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda3
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.decode(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.ENCODE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda4
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.encode(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.ENCODE_HDR,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda5
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.encodeHDR(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.FLIP,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda6
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.flip(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CREATE_GAINMAP,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda7
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return NativeUniImgpPlugin.this.createGainmap(
                                mediaBuffer, mutableMediaBuffer);
                    }
                });
    }

    private JSONObject bufferToJson(MediaBuffer buffer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cols", buffer.getCols());
            jsonObject.put("rows", buffer.getRows());
            jsonObject.put("data-type", buffer.getFormat().getDataType().stringfy());
            jsonObject.put(
                    android.media.MediaFormat.KEY_COLOR_FORMAT,
                    buffer.getFormat().getColorFormat().stringfy());
            jsonObject.put("color-space", buffer.getFormat().getColorSpace().stringfy());
            jsonObject.put(GenerateXML.ROTATION, buffer.getFormat().getRotation());
            if (buffer.containsExtra(Message.KEY_IN_FILE)) {
                jsonObject.put(Message.KEY_IN_FILE, (String) buffer.getExtra(Message.KEY_IN_FILE));
            }
            if (buffer.containsExtra(Message.KEY_OUT_FILE)) {
                jsonObject.put(
                        Message.KEY_OUT_FILE, (String) buffer.getExtra(Message.KEY_OUT_FILE));
            }
            if (buffer.containsExtra("exposure-value")) {
                jsonObject.put(
                        "exposure-value", ((Integer) buffer.getExtra("exposure-value")).intValue());
            }
            if (buffer.getFormat().getCodecType() != CodecType.NONE) {
                jsonObject.put("codec-type", buffer.getFormat().getCodecType().stringfy());
            }
            if (buffer.getFormat().contains("scale")) {
                jsonObject.put(
                        "scale",
                        ((Float) buffer.getFormat().get("scale", Float.valueOf(1.0f)))
                                .floatValue());
            }
            if (buffer.getFormat().getCropRect() != null) {
                jsonObject.put("crop-rect", buffer.getFormat().getCropRect().flattenToString());
            }
            if (buffer.getFormat().getSplitType() != SplitType.NONE) {
                jsonObject.put("split-type", buffer.getFormat().getSplitType().stringfy());
            }
            if (buffer.getFormat().getFlipType() != FlipType.NONE) {
                jsonObject.put("flip-type", buffer.getFormat().getFlipType().stringfy());
            }
            if (buffer.containsAllExtra("row-offset", "scan-lines")) {
                jsonObject.put("row-offset", ((Float) buffer.getExtra("row-offset")).doubleValue());
                jsonObject.put("scan-lines", ((Float) buffer.getExtra("scan-lines")).doubleValue());
            }
            if (buffer.containsAllExtra("roi-on-image", "roi-on-block")) {
                jsonObject.put(
                        "roi-on-image", ((Rect) buffer.getExtra("roi-on-image")).flattenToString());
                jsonObject.put(
                        "roi-on-block", ((Rect) buffer.getExtra("roi-on-block")).flattenToString());
            }
            if (buffer.getFormat().contains("pad-type")) {
                jsonObject.put(
                        "pad-type",
                        ((PadType) buffer.getFormat().get("pad-type", PadType.NONE)).stringfy());
            }
            if (buffer.getFormat().contains("pad-size")) {
                jsonObject.put(
                        "pad-size", ((Integer) buffer.getFormat().get("pad-size", 0)).intValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONObject createJsonImgpOption(MediaBuffer ibuf, MediaBuffer obuf) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (obuf.getFormat().getSplitType() != SplitType.NONE) {
                jsonObject.put("split-type", obuf.getFormat().getSplitType().stringfy());
            }
            if (obuf.getFormat().getFlipType() != FlipType.NONE) {
                jsonObject.put("flip-type", obuf.getFormat().getFlipType().stringfy());
            }
            if (obuf.getFormat().contains("pad-type")) {
                jsonObject.put(
                        "pad-type",
                        ((PadType) obuf.getFormat().get("pad-type", PadType.NONE)).stringfy());
            }
            if (ibuf.isNotEmpty() && obuf.isNotEmpty() && ibuf.getData().equals(obuf.getData())) {
                jsonObject.put("prefer-fast", true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private Object getNativeSupportBuffer(MediaBuffer buffer) {
        if (buffer.isEmpty()) {
            return null;
        }
        Class<?> dataClass = buffer.getDataClass();
        if (HardwareBuffer.class.isAssignableFrom(dataClass)
                || Image.class.isAssignableFrom(dataClass)) {
            return buffer.getTypedData(HardwareBuffer.class);
        }
        if (Bitmap.class.isAssignableFrom(dataClass)) {
            return buffer.getTypedData(Bitmap.class);
        }
        Log.d(TAG, "convert as default... (ByteBuffer)");
        return buffer.getTypedData(ByteBuffer.class);
    }

    public MutableMediaBuffer resize(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            if (obuf.getFormat().contains("scale")) {
                float scale = ((Float) obuf.getFormat().get("scale")).floatValue();
                outputFormat.setCols((int) (ibuf.getCols() * scale));
                outputFormat.setRows((int) (ibuf.getRows() * scale));
            } else {
                outputFormat.setShape(obuf.getFormat().getShape());
            }
            obuf.put(MediaBuffer.of(outputFormat));
        }
        ByteBuffer output = toDirectByteBuffer((ByteBuffer) obuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        Status status =
                Status.from(
                        nativeResize(
                                inputFormat.toString(),
                                input,
                                bufferToJson(obuf).toString(),
                                output));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer cvtColor(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        if (ibuf.getFormat().getColorFormat() == obuf.getFormat().getColorFormat()) {
            obuf.put(ibuf);
            return obuf;
        }
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setColorFormat(obuf.getFormat().getColorFormat());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        ByteBuffer output = toDirectByteBuffer((ByteBuffer) obuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        Status status =
                Status.from(
                        nativeCvtColor(
                                inputFormat.toString(),
                                input,
                                bufferToJson(obuf).toString(),
                                output));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer cvtData(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        if (ibuf.getFormat().getDataType() == obuf.getFormat().getDataType()) {
            obuf.put(ibuf);
            return obuf;
        }
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setDataType(obuf.getFormat().getDataType());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        ByteBuffer output = toDirectByteBuffer((ByteBuffer) obuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        Status status =
                Status.from(
                        nativeCvtData(
                                inputFormat.toString(),
                                input,
                                bufferToJson(obuf).toString(),
                                output));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer cvtGamut(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        MediaBuffer p3Buffer;
        MediaBuffer bt709Buffer;
        Log.d(TAG, "ibuf=" + ibuf);
        Log.d(TAG, "obuf=" + obuf);
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setColorSpace(obuf.getFormat().getColorSpace());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        ColorSpace inColorSpace = ibuf.getFormat().getColorSpace();
        ColorSpace outColorSpace = obuf.getFormat().getColorSpace();
        if (inColorSpace == ColorSpace.DISPLAY_P3) {
            p3Buffer = ibuf;
        } else {
            Log.d(TAG, "not supported colorSpace. force" + inColorSpace + " to display-p3");
            MutableMediaFormat p3Format = ibuf.getFormat().toMutableFormat();
            p3Format.setColorSpace(ColorSpace.DISPLAY_P3);
            p3Buffer = MediaBuffer.of(p3Format, ibuf.getData());
        }
        if (outColorSpace == ColorSpace.BT709_FR) {
            bt709Buffer = obuf;
        } else {
            Log.d(TAG, "not supported colorSpace. force" + outColorSpace + " to bt709-fr");
            MutableMediaFormat bt709Format = obuf.getFormat().toMutableFormat();
            bt709Format.setColorSpace(ColorSpace.BT709_FR);
            bt709Buffer = MediaBuffer.of(bt709Format, obuf.getData());
        }
        JSONObject inputFormat = bufferToJson(ibuf);
        Status status =
                Status.from(
                        nativeCvtGamutV2(
                                inputFormat.toString(),
                                getNativeSupportBuffer(p3Buffer),
                                bufferToJson(obuf).toString(),
                                getNativeSupportBuffer(bt709Buffer)));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer cvtHdr2Sdr(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        return obuf;
    }

    public MutableMediaBuffer rotate(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        if (obuf.getFormat().getRotation() == 0) {
            obuf.put(ibuf);
            return obuf;
        }
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setRotation(obuf.getFormat().getRotation());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        ByteBuffer output = toDirectByteBuffer((ByteBuffer) obuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        Status status =
                Status.from(
                        nativeRotate(
                                inputFormat.toString(),
                                input,
                                bufferToJson(obuf).toString(),
                                output));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer crop(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        if (obuf.getFormat().getCropRect() == null) {
            obuf.put(ibuf);
            return obuf;
        }
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setRotation(obuf.getFormat().getRotation());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        ByteBuffer output = toDirectByteBuffer((ByteBuffer) obuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat2 = bufferToJson(obuf);
        try {
            if (obuf.getFormat().getCropRect() != null) {
                outputFormat2.put("crop-rect", obuf.getFormat().getCropRect().flattenToString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Status status =
                Status.from(
                        nativeCrop(
                                inputFormat.toString(), input, outputFormat2.toString(), output));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer split(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        HashMap<String, Object> dataMap = new HashMap<>();
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat = bufferToJson(obuf);
        JSONObject optionJson = createJsonImgpOption(ibuf, obuf);
        Status status =
                Status.from(
                        nativeSplit(
                                optionJson.toString(),
                                inputFormat.toString(),
                                input,
                                outputFormat.toString(),
                                dataMap));
        Def.check(status == Status.OK);
        MediaBuffer splitBuffer = makeBufferFromMap(dataMap);
        if (splitBuffer != null) {
            obuf.put(splitBuffer);
        }
        return obuf;
    }

    public MutableMediaBuffer merge(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        HashMap<String, Object> dataMap = new HashMap<>();
        ByteBuffer input =
                ibuf.getData() != null
                        ? toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class))
                        : null;
        if (obuf.getFormat() == null || obuf.getFormat().getShape() == null) {
            MutableMediaFormat fmt = obuf.getFormat().toMutableFormat();
            fmt.setShape(ibuf.getFormat().getShape());
            obuf.setFormat(fmt);
        }
        Log.d(TAG, ibuf.toString());
        Log.d(TAG, obuf.toString());
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat = bufferToJson(obuf);
        List<MediaBuffer> ibufList = ibuf.asList();
        try {
            inputFormat.put("block-num", ibufList.size());
            int blockNum = 0;
            for (MediaBuffer blockBuf : ibufList) {
                dataMap.put("block" + blockNum + "-format", bufferToJson(blockBuf).toString());
                dataMap.put(
                        "block" + blockNum + "-data",
                        toDirectByteBuffer((ByteBuffer) blockBuf.getTypedData(ByteBuffer.class)));
                blockNum++;
            }
            JSONObject optionJson = createJsonImgpOption(ibuf, obuf);
            Status status =
                    Status.from(
                            nativeMerge(
                                    optionJson.toString(),
                                    inputFormat.toString(),
                                    input,
                                    outputFormat.toString(),
                                    dataMap));
            Def.check(status == Status.OK);
            MediaBuffer mergeBuffer = makeBufferFromMap(dataMap);
            if (mergeBuffer != null) {
                obuf.put(mergeBuffer);
            }
            return obuf;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MutableMediaBuffer measureQuality(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        Def.check(ibuf.asList().size() == 2, "ibuf size is not 2", new Object[0]);
        if (!obuf.getFormat().contains("quality-metric")) {
            Log.d(TAG, "quality-metric is not given. force to PSNR");
        } else if (((Integer) obuf.getFormat().get("quality-metric")).intValue() != 0) {
            throw new UnsupportedOperationException("currently only PSNR is supported!");
        }
        HashMap<String, Object> dataMap = new HashMap<>();
        ByteBuffer inputFirst =
                toDirectByteBuffer(
                        (ByteBuffer) ibuf.asList().get(0).getTypedData(ByteBuffer.class));
        ByteBuffer inputSecond =
                toDirectByteBuffer(
                        (ByteBuffer) ibuf.asList().get(1).getTypedData(ByteBuffer.class));
        JSONObject inputFormatFirst = bufferToJson(ibuf.asList().get(0));
        JSONObject inputFormatSecond = bufferToJson(ibuf.asList().get(1));
        Status status =
                Status.from(
                        nativeMeasureQuality(
                                inputFormatFirst.toString(),
                                inputFirst,
                                inputFormatSecond.toString(),
                                inputSecond,
                                dataMap));
        Def.check(status == Status.OK);
        Float qualityValue = (Float) dataMap.get("quality-value");
        obuf.put(MediaBuffer.scalaOf(DataType.F32C1, Shape.of(1, 1), qualityValue));
        return obuf;
    }

    public MutableMediaBuffer decode(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        HashMap<String, Object> dataMap = new HashMap<>();
        Log.d(TAG, ibuf.toString());
        Log.d(TAG, obuf.toString());
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat = bufferToJson(obuf);
        if (ibuf.getDataClass() == FileDescriptor.class) {
            dataMap.put("infile-descriptor", ibuf.getTypedData(FileDescriptor.class));
        } else if (ibuf.getFormat().contains(Message.KEY_FILE_DESCRIPTOR)) {
            dataMap.put("infile-descriptor", ibuf.getFormat().get(Message.KEY_FILE_DESCRIPTOR));
        }
        Status status =
                Status.from(
                        nativeDecode(
                                inputFormat.toString(), null, outputFormat.toString(), dataMap));
        Def.check(status == Status.OK);
        MediaBuffer newObuf = makeBufferFromMap(dataMap);
        if (newObuf != null) {
            obuf.put(newObuf);
        }
        return obuf;
    }

    public MutableMediaBuffer encode(MediaBuffer ibuf, final MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        final HashMap<String, Object> dataMap = new HashMap<>();
        ibuf.asList()
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                NativeUniImgpPlugin.this.m9198xdfdf96db(
                                        obuf, dataMap, (MediaBuffer) obj);
                            }
                        });
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat = bufferToJson(obuf);
        try {
            if (obuf.getFormat().contains(Message.KEY_OUT_FILE)) {
                outputFormat.put(
                        Message.KEY_OUT_FILE, (String) obuf.getFormat().get(Message.KEY_OUT_FILE));
            }
            if (obuf.getFormat().contains(Message.KEY_FILE_DESCRIPTOR)) {
                dataMap.put(
                        "outfile-descriptor",
                        (FileDescriptor) obuf.getFormat().get(Message.KEY_FILE_DESCRIPTOR));
            }
            if (obuf.getFormat().getCodecType() != CodecType.NONE) {
                outputFormat.put("codec-type", obuf.getFormat().getCodecType().stringfy());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Status status =
                Status.from(
                        nativeEncode(
                                inputFormat.toString(), input, outputFormat.toString(), dataMap));
        Def.check(status == Status.OK);
        return obuf;
    }

    /* renamed from: lambda$encode$1$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ void m9198xdfdf96db(MutableMediaBuffer obuf, HashMap dataMap, MediaBuffer it) {
        if (it.getFormat().contains("exif")) {
            MediaBuffer exifBuffer =
                    obuf.getFormat().getColorFormat().isPlanar() ? adjustExif(it) : it;
            dataMap.put(
                    "exif",
                    toDirectByteBuffer((ByteBuffer) exifBuffer.getTypedData(ByteBuffer.class)));
        } else if (it.getFormat().contains("icc")) {
            dataMap.put("icc", toDirectByteBuffer((ByteBuffer) it.getTypedData(ByteBuffer.class)));
        } else {
            Log.w(TAG, "Unused buffer is given for encoding" + it);
        }
    }

    public MutableMediaBuffer flip(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        Log.d(TAG, "ibuf=" + ibuf);
        Log.d(TAG, "obuf=" + obuf);
        Object input = getNativeSupportBuffer(ibuf);
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setFlipType(obuf.getFormat().getFlipType());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        Object output = getNativeSupportBuffer(obuf);
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat2 = bufferToJson(obuf);
        JSONObject optionJson = createJsonImgpOption(ibuf, obuf);
        Status status =
                Status.from(
                        nativeFlipV2(
                                optionJson.toString(),
                                inputFormat.toString(),
                                input,
                                outputFormat2.toString(),
                                output));
        Def.check(status == Status.OK);
        return obuf;
    }

    public MutableMediaBuffer encodeHDR(MediaBuffer ibuf, final MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        if (!isHDRSupported()) {
            throw new UnsupportedOperationException("HDR is not supported!");
        }
        final HashMap<String, Object> dataMap = new HashMap<>();
        Log.d(TAG, obuf.toString());
        ibuf.asList()
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda17
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                NativeUniImgpPlugin.this.m9199x6bd652a(
                                        obuf, dataMap, (MediaBuffer) obj);
                            }
                        });
        ByteBuffer input = toDirectByteBuffer((ByteBuffer) ibuf.getTypedData(ByteBuffer.class));
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat = bufferToJson(obuf);
        try {
            if (obuf.getFormat().contains(Message.KEY_OUT_FILE)) {
                outputFormat.put(
                        Message.KEY_OUT_FILE, (String) obuf.getFormat().get(Message.KEY_OUT_FILE));
            }
            if (obuf.getFormat().contains(Message.KEY_FILE_DESCRIPTOR)) {
                dataMap.put(
                        "outfile-descriptor",
                        (FileDescriptor) obuf.getFormat().get(Message.KEY_FILE_DESCRIPTOR));
            }
            if (obuf.getFormat().getCodecType() != CodecType.NONE) {
                if (obuf.getFormat().getCodecType() == CodecType.HEIF
                        && !isHeifSupported(ibuf, obuf.getFormat().getShape())) {
                    throw new UnsupportedOperationException(
                            "encode size must bigger than [512x512]");
                }
                outputFormat.put("codec-type", obuf.getFormat().getCodecType().stringfy());
            }
            if (obuf.containsExtra("exposure-value")) {
                outputFormat.put(
                        "exposure-value", ((Integer) obuf.getExtra("exposure-value")).intValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Status status =
                Status.from(
                        nativeEncodeHDR(
                                inputFormat.toString(), input, outputFormat.toString(), dataMap));
        Def.check(status == Status.OK);
        return obuf;
    }

    /* renamed from: lambda$encodeHDR$2$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ void m9199x6bd652a(MutableMediaBuffer obuf, HashMap dataMap, MediaBuffer it) {
        if (it.getFormat().contains("exif")) {
            MediaBuffer exifBuffer =
                    obuf.getFormat().getColorFormat().isPlanar() ? adjustExif(it) : it;
            dataMap.put(
                    "exif",
                    toDirectByteBuffer((ByteBuffer) exifBuffer.getTypedData(ByteBuffer.class)));
        } else if (it.getFormat().contains("icc")) {
            dataMap.put("icc", toDirectByteBuffer((ByteBuffer) it.getTypedData(ByteBuffer.class)));
        } else if (it.getFormat().contains("gain-map")) {
            dataMap.put("gain-map-format", bufferToJson(it).toString());
            dataMap.put(
                    "gain-map", toDirectByteBuffer((ByteBuffer) it.getTypedData(ByteBuffer.class)));
        }
    }

    public MutableMediaBuffer createGainmap(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        HashMap<String, Object> dataMap = new HashMap<>();
        JSONObject inputFormat = bufferToJson(ibuf);
        JSONObject outputFormat = bufferToJson(obuf);
        Bitmap inputBitmap = (Bitmap) ibuf.getTypedData(Bitmap.class);
        dataMap.put("jbitmap", inputBitmap);
        Status status =
                Status.from(
                        nativeCreateGainmap(
                                inputFormat.toString(), null, outputFormat.toString(), dataMap));
        Def.check(status == Status.OK);
        MediaBuffer gainmapBuffer = makeBufferFromMap(dataMap);
        Log.d(TAG, gainmapBuffer.toString());
        Bitmap gainmapBitmap = (Bitmap) gainmapBuffer.getTypedData(Bitmap.class);
        Log.d(TAG, "create gain-map");
        Gainmap gainmap = new Gainmap(gainmapBitmap);
        float maxContentBoost = ((Float) gainmapBuffer.getExtra("max-content-boost")).floatValue();
        float minContentBoost = ((Float) gainmapBuffer.getExtra("min-content-boost")).floatValue();
        float gamma = ((Float) gainmapBuffer.getExtra("gamma")).floatValue();
        float offsetHdr = ((Float) gainmapBuffer.getExtra("offset-hdr")).floatValue();
        float offsetSdr = ((Float) gainmapBuffer.getExtra("offset-sdr")).floatValue();
        float maxHdrCapacity = ((Float) gainmapBuffer.getExtra("max-hdr-capacity")).floatValue();
        float minHdrCapacity = ((Float) gainmapBuffer.getExtra("min-hdr-capacity")).floatValue();
        float ratioMax = (float) Math.pow(2.0d, maxContentBoost);
        float rationMin = (float) Math.pow(2.0d, minContentBoost);
        gainmap.setRatioMax(ratioMax, ratioMax, ratioMax);
        gainmap.setRatioMin(rationMin, rationMin, rationMin);
        gainmap.setGamma(gamma, gamma, gamma);
        gainmap.setEpsilonHdr(offsetHdr, offsetHdr, offsetHdr);
        gainmap.setEpsilonSdr(offsetSdr, offsetSdr, offsetSdr);
        gainmap.setDisplayRatioForFullHdr((float) Math.pow(2.0d, maxHdrCapacity));
        gainmap.setMinDisplayRatioForHdrTransition((float) Math.pow(2.0d, minHdrCapacity));
        inputBitmap.setGainmap(gainmap);
        obuf.put(MediaBuffer.of(ibuf.getFormat(), inputBitmap));
        return obuf;
    }

    public MediaBuffer readCompressedImage(MediaFormat format, String path) {
        Log.d(TAG, "read compressed image: " + path);
        String ext = path.substring(path.lastIndexOf(46) + 1).toLowerCase(Locale.ROOT);
        if (!ext.equals("jpg") && !ext.equals("heic")) {
            throw new UnsupportedOperationException("not supported yet");
        }
        MutableMediaBuffer imageBuffer = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            try {
                FileDescriptor fd = fis.getFD();
                MediaBuffer ibuf = MediaBuffer.compressedImageOf(fd);
                MutableMediaFormat fmt = format.toMutableFormat();
                if (format.getColorFormat() == ColorFormat.NONE) {
                    fmt.setColorFormat(ColorFormat.RGBA);
                }
                Log.d(TAG, "decode format: " + fmt);
                imageBuffer = MediaBuffer.mutableOf(fmt);
                decode(ibuf, imageBuffer);
                fis.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBuffer;
    }

    public boolean writeCompressedImage(MediaBuffer ibuf, String path) {
        Log.d(TAG, "write compressed image: " + path);
        try {
            FileOutputStream fos = new FileOutputStream(path);
            try {
                FileDescriptor fd = fos.getFD();
                MutableMediaFormat encodeFormat =
                        MediaFormat.mutableCompressedImageOf(ColorFormat.NV12);
                encodeFormat.set(Message.KEY_FILE_DESCRIPTOR, fd);
                String ext = path.substring(path.lastIndexOf(46) + 1).toLowerCase(Locale.ROOT);
                if (ext.equals("jpg")) {
                    encodeFormat.setCodecType(CodecType.JPEG_QURAM);
                } else if (ext.equals("heic")) {
                    encodeFormat.setCodecType(CodecType.HEIF);
                } else {
                    throw new UnsupportedOperationException("not supported yet");
                }
                if (ibuf.getFormat().contains("encode-shape")) {
                    encodeFormat.setShape((Shape) ibuf.getFormat().get("encode-shape"));
                }
                MutableMediaBuffer obuf = MediaBuffer.mutableOf(encodeFormat);
                if (ibuf.getFormat().contains("encode-hdr")) {
                    encodeHDR(ibuf, obuf);
                } else {
                    encode(ibuf, obuf);
                }
                fos.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "succes to save" + path);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0229  */
    @Override // com.samsung.android.sume.core.functional.Operator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.samsung.android.sume.core.buffer.MutableMediaBuffer run(
            com.samsung.android.sume.core.buffer.MediaBuffer r18,
            com.samsung.android.sume.core.buffer.MutableMediaBuffer r19)
            throws java.lang.UnsupportedOperationException {
        /*
            Method dump skipped, instructions count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.sume.core.plugin.NativeUniImgpPlugin.run(com.samsung.android.sume.core.buffer.MediaBuffer,"
                    + " com.samsung.android.sume.core.buffer.MutableMediaBuffer):com.samsung.android.sume.core.buffer.MutableMediaBuffer");
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ void m9201xff46b99e(HashMap dataMap, MediaBuffer buf) {
        if (buf.getFormat().contains("exif")) {
            MediaBuffer exifBuffer = buf;
            if (this.persistentOutputFormat.getColorFormat().isPlanar()
                    || this.preferredColorFormat.isPlanar()) {
                exifBuffer = adjustExif(exifBuffer);
            }
            dataMap.put(
                    "exif",
                    toDirectByteBuffer((ByteBuffer) exifBuffer.getTypedData(ByteBuffer.class)));
        } else if (buf.getFormat().contains("icc")) {
            dataMap.put("icc", toDirectByteBuffer((ByteBuffer) buf.getTypedData(ByteBuffer.class)));
        } else if (buf.getFormat().contains("gain-map")) {
            JSONObject gainMapFormat = bufferToJson(buf);
            try {
                gainMapFormat.put("gain-map", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dataMap.put("gain-map-format", gainMapFormat.toString());
            dataMap.put(
                    "gain-map",
                    toDirectByteBuffer((ByteBuffer) buf.getTypedData(ByteBuffer.class)));
        }
        if (buf.containsExtra("thumbnail")) {
            dataMap.put("thumbnail", buf.getTypedData(ByteBuffer.class));
        }
    }

    private ByteBuffer toDirectByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        if (byteBuffer.isDirect()) {
            return byteBuffer;
        }
        ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(byteBuffer.limit());
        directByteBuffer.put(byteBuffer);
        return directByteBuffer;
    }

    private MediaBuffer adjustExif(MediaBuffer exifBuffer) {
        UniExifInterface uniExifInterface =
                (UniExifInterface) exifBuffer.getTypedData(UniExifInterface.class);
        int cols = uniExifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_X_DIMENSION, 0);
        int rows = uniExifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_Y_DIMENSION, 0);
        if ((cols & 1) == 0 && (rows & 1) == 0) {
            return exifBuffer;
        }
        uniExifInterface.setAttribute(
                ExifInterface.TAG_IMAGE_WIDTH, String.valueOf((cols >> 1) << 1));
        uniExifInterface.setAttribute(
                ExifInterface.TAG_IMAGE_LENGTH, String.valueOf((rows >> 1) << 1));
        uniExifInterface.setAttribute(
                ExifInterface.TAG_PIXEL_X_DIMENSION, String.valueOf((cols >> 1) << 1));
        uniExifInterface.setAttribute(
                ExifInterface.TAG_PIXEL_Y_DIMENSION, String.valueOf((rows >> 1) << 1));
        Log.d(
                TAG,
                "adjust exif to... ["
                        + ((cols >> 1) << 1)
                        + ", "
                        + ((rows >> 1) << 1)
                        + NavigationBarInflaterView.SIZE_MOD_END);
        try {
            uniExifInterface.saveAttributes();
            return MediaBuffer.metadataBufferOf(1, uniExifInterface);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractExtraFromJson(MediaBuffer buffer, JSONObject bufferObject) {
        String str;
        String str2;
        String str3;
        try {
            if (bufferObject.has("row-offset")) {
                buffer.setExtra(
                        "row-offset", Float.valueOf((float) bufferObject.getDouble("row-offset")));
            }
            if (bufferObject.has("scan-lines")) {
                buffer.setExtra(
                        "scan-lines", Float.valueOf((float) bufferObject.getDouble("scan-lines")));
            }
            if (!bufferObject.has("roi-on-block")) {
                str = "offset-sdr";
                str2 = "offset-hdr";
                str3 = "gamma";
            } else {
                String roiOnBlockStr = bufferObject.getString("roi-on-block");
                String[] rectStr = roiOnBlockStr.replaceAll("[^0-9|,]", "").split(",");
                str = "offset-sdr";
                str2 = "offset-hdr";
                str3 = "gamma";
                buffer.setExtra(
                        "roi-on-block",
                        new Rect(
                                Integer.parseInt(rectStr[0]),
                                Integer.parseInt(rectStr[1]),
                                Integer.parseInt(rectStr[2]),
                                Integer.parseInt(rectStr[3])));
            }
            if (bufferObject.has("roi-on-image")) {
                String roiOnImageStr = bufferObject.getString("roi-on-image");
                String[] rectStr2 = roiOnImageStr.replaceAll("[^0-9|,]", "").split(",");
                buffer.setExtra(
                        "roi-on-image",
                        new Rect(
                                Integer.parseInt(rectStr2[0]),
                                Integer.parseInt(rectStr2[1]),
                                Integer.parseInt(rectStr2[2]),
                                Integer.parseInt(rectStr2[3])));
            }
            if (bufferObject.has("max-content-boost")) {
                buffer.setExtra(
                        "max-content-boost",
                        Float.valueOf((float) bufferObject.getDouble("max-content-boost")));
            }
            if (bufferObject.has("min-content-boost")) {
                buffer.setExtra(
                        "min-content-boost",
                        Float.valueOf((float) bufferObject.getDouble("min-content-boost")));
            }
            if (bufferObject.has("max-hdr-capacity")) {
                buffer.setExtra(
                        "max-hdr-capacity",
                        Float.valueOf((float) bufferObject.getDouble("max-hdr-capacity")));
            }
            if (bufferObject.has("min-hdr-capacity")) {
                buffer.setExtra(
                        "min-hdr-capacity",
                        Float.valueOf((float) bufferObject.getDouble("min-hdr-capacity")));
            }
            String str4 = str3;
            if (bufferObject.has(str4)) {
                buffer.setExtra(str4, Float.valueOf((float) bufferObject.getDouble(str4)));
            }
            String str5 = str2;
            if (bufferObject.has(str5)) {
                buffer.setExtra(str5, Float.valueOf((float) bufferObject.getDouble(str5)));
            }
            String str6 = str;
            if (bufferObject.has(str6)) {
                buffer.setExtra(str6, Float.valueOf((float) bufferObject.getDouble(str6)));
            }
        } catch (JSONException e) {
            throw new IllegalStateException("failed to get object from bufferObject: " + e);
        }
    }

    private MediaBuffer makeImageBuffer(String string, Object dataObject) {
        MutableMediaFormat format = MediaFormat.mutableImageOf(new Object[0]);
        MediaBuffer buffer = null;
        try {
            JSONObject bufferObject = new JSONObject(string);
            if (bufferObject.has("cols")) {
                format.setCols(bufferObject.getInt("cols"));
            }
            if (bufferObject.has("rows")) {
                format.setRows(bufferObject.getInt("rows"));
            }
            if (bufferObject.has("data-type")) {
                format.setDataType(DataType.valueOf(bufferObject.getString("data-type")));
            }
            if (bufferObject.has(android.media.MediaFormat.KEY_COLOR_FORMAT)) {
                format.setColorFormat(
                        ColorFormat.valueOf(
                                bufferObject.getString(
                                        android.media.MediaFormat.KEY_COLOR_FORMAT)));
            }
            if (bufferObject.has("color-space")) {
                format.setColorSpace(ColorSpace.valueOf(bufferObject.getString("color-space")));
            }
            if (dataObject instanceof ByteBuffer) {
                buffer = MediaBuffer.of(format, (ByteBuffer) dataObject);
            } else if (dataObject instanceof Bitmap) {
                buffer = MediaBuffer.of(format, (Bitmap) dataObject);
            } else {
                Log.d(TAG, "do nothing to obuf");
            }
            if (buffer != null) {
                extractExtraFromJson(buffer, bufferObject);
            }
            return buffer;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private MediaBuffer makeBufferFromMap(final HashMap<String, Object> dataMap) {
        ByteBuffer iccByteBuffer;
        ByteBuffer exifByteBuffer;
        MediaBuffer primaryBuffer = null;
        ArrayList arrayList = new ArrayList();
        List<MediaBuffer> blockBuffers = new ArrayList<>();
        if (dataMap.containsKey(Message.KEY_OUT_BUFFER)) {
            primaryBuffer =
                    makeImageBuffer(
                            (String) dataMap.get(Message.KEY_OUT_BUFFER),
                            dataMap.get("output-data"));
        }
        Integer blockNum = (Integer) dataMap.get("block-num");
        if (blockNum != null) {
            Log.d(TAG, "block num: " + blockNum);
            blockBuffers =
                    (List)
                            IntStream.range(0, blockNum.intValue())
                                    .mapToObj(
                                            new IntFunction() { // from class:
                                                                // com.samsung.android.sume.core.plugin.NativeUniImgpPlugin$$ExternalSyntheticLambda19
                                                @Override // java.util.function.IntFunction
                                                public final Object apply(int i) {
                                                    return NativeUniImgpPlugin.this.m9200x5755f344(
                                                            dataMap, i);
                                                }
                                            })
                                    .collect(Collectors.toList());
        }
        if (dataMap.containsKey("exif")
                && (exifByteBuffer = (ByteBuffer) dataMap.get("exif")) != null
                && exifByteBuffer.isDirect()) {
            arrayList.add(MediaBuffer.metadataBufferOf(1, exifByteBuffer));
        }
        if (dataMap.containsKey("icc")
                && (iccByteBuffer = (ByteBuffer) dataMap.get("icc")) != null
                && iccByteBuffer.isDirect()) {
            arrayList.add(MediaBuffer.metadataBufferOf(2, iccByteBuffer));
        }
        if (blockBuffers.size() > 1) {
            if (arrayList.size() > 0) {
                blockBuffers.addAll(arrayList);
            }
            return MediaBuffer.groupOf(primaryBuffer, blockBuffers);
        }
        if (primaryBuffer != null && arrayList.size() > 0) {
            arrayList.add(0, primaryBuffer);
            return MediaBuffer.groupOf(primaryBuffer, arrayList);
        }
        return primaryBuffer;
    }

    /* renamed from: lambda$makeBufferFromMap$4$com-samsung-android-sume-core-plugin-NativeUniImgpPlugin, reason: not valid java name */
    /* synthetic */ MediaBuffer m9200x5755f344(HashMap dataMap, int it) {
        return makeImageBuffer(
                (String) dataMap.get("block" + it + "-buffer"),
                dataMap.get("block" + it + "-data"));
    }

    private boolean isHeifSupported(MediaBuffer encodeBuffer, Shape targetShape) {
        if (encodeBuffer.getDataClass() == FileDescriptor.class) {
            return true;
        }
        return (targetShape == null || targetShape.getDimension() <= 0)
                ? encodeBuffer.getCols() >= 512 && encodeBuffer.getRows() >= 512
                : targetShape.getCols() >= 512 && targetShape.getRows() >= 512;
    }

    private boolean isHDRSupported() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_PHOTOHDR");
    }
}
