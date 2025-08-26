package com.samsung.android.sume.core.plugin;

import android.media.MediaFormat;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.functional.DescriptorLoader;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import com.samsung.android.sume.core.types.ImgpType;
import java.nio.ByteBuffer;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class SimgpPlugin implements Plugin<ImgpPlugin> {
    private static final String TAG = Def.tagOf((Class<?>) SimgpPlugin.class);

    private static native int nativeCvtColor(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, String str);

    private static native int nativeResize(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, String str);

    private static native int nativeRotate(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, String str);

    static {
        System.loadLibrary("sume_mediabuffer_jni.media.samsung");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: resize, reason: merged with bridge method [inline-methods] */
    public MutableMediaBuffer m9617xd2768214(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException, JSONException {
        Log.d(TAG, "try to simgp resize: " + mediaBuffer);
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat mutableMediaFormatCopy = mediaBuffer.getFormat().toMutableFormat().copy();
            if (mutableMediaBuffer.getFormat().contains("scale")) {
                float fFloatValue = ((Float) mutableMediaBuffer.getFormat().get("scale")).floatValue();
                mutableMediaFormatCopy.setCols((int) (mediaBuffer.getCols() * fFloatValue));
                mutableMediaFormatCopy.setRows((int) (mediaBuffer.getRows() * fFloatValue));
            } else {
                mutableMediaFormatCopy.setShape(mutableMediaBuffer.getFormat().getShape());
            }
            mutableMediaBuffer.put(MediaBuffer.of(mutableMediaFormatCopy));
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("src-cols", mediaBuffer.getCols());
            jSONObject.put("src-rows", mediaBuffer.getRows());
            jSONObject.put("dst-cols", mutableMediaBuffer.getCols());
            jSONObject.put("dst-rows", mutableMediaBuffer.getRows());
            jSONObject.put(MediaFormat.KEY_COLOR_FORMAT, mediaBuffer.getFormat().getColorFormat().stringfy());
            nativeResize((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class), (ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class), jSONObject.toString());
            return mutableMediaBuffer;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cvtColor, reason: merged with bridge method [inline-methods] */
    public MutableMediaBuffer m9619xb5c9ce52(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException, JSONException {
        Log.d(TAG, "try to simgp cvtColor: " + mediaBuffer + " => " + mutableMediaBuffer.getFormat());
        if (mutableMediaBuffer.isEmpty()) {
            MutableMediaFormat mutableMediaFormatCopy = mediaBuffer.getFormat().toMutableFormat().copy();
            mutableMediaFormatCopy.setColorFormat(mutableMediaBuffer.getFormat().getColorFormat());
            mutableMediaBuffer.put(MediaBuffer.of(mutableMediaFormatCopy));
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cols", mutableMediaBuffer.getFormat().getCols());
            jSONObject.put("rows", mutableMediaBuffer.getFormat().getRows());
            jSONObject.put("src-color-format", mediaBuffer.getFormat().getColorFormat().stringfy());
            jSONObject.put("dst-color-format", mutableMediaBuffer.getFormat().getColorFormat().stringfy());
            nativeCvtColor((ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class), (ByteBuffer) mutableMediaBuffer.getTypedData(ByteBuffer.class), jSONObject.toString());
            return mutableMediaBuffer;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: rotate, reason: merged with bridge method [inline-methods] */
    public MutableMediaBuffer m9618xc4202833(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        Log.d(TAG, "try to simgp rotate: " + mediaBuffer);
        throw new UnsupportedOperationException("not yet implemented");
    }

    static /* synthetic */ MFDescriptor lambda$bindToFixture$0() {
        return new ImgpDescriptor(ImgpPlugin.Type.SIMGP);
    }

    @Override // com.samsung.android.sume.core.plugin.Plugin
    public void bindToFixture(ImgpPlugin imgpPlugin) {
        imgpPlugin.setDescriptorLoader(new DescriptorLoader() { // from class: com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda0
            @Override // com.samsung.android.sume.core.functional.DescriptorLoader
            public final MFDescriptor load() {
                return SimgpPlugin.lambda$bindToFixture$0();
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.RESIZE, new Operator() { // from class: com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda1
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return this.f$0.m9617xd2768214(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.ROTATE, new Operator() { // from class: com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda2
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return this.f$0.m9618xc4202833(mediaBuffer, mutableMediaBuffer);
            }
        });
        imgpPlugin.setImgProcessor(ImgpType.CVT_COLOR, new Operator() { // from class: com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda3
            @Override // com.samsung.android.sume.core.functional.Operator
            public final MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                return this.f$0.m9619xb5c9ce52(mediaBuffer, mutableMediaBuffer);
            }
        });
    }
}
