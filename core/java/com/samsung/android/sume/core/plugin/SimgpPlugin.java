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
import com.samsung.android.sume.core.types.ImgpType;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;

public class SimgpPlugin implements Plugin<ImgpPlugin> {
    private static final String TAG = Def.tagOf((Class<?>) SimgpPlugin.class);

    private static native int nativeCvtColor(
            ByteBuffer byteBuffer, ByteBuffer byteBuffer2, String str);

    private static native int nativeResize(
            ByteBuffer byteBuffer, ByteBuffer byteBuffer2, String str);

    private static native int nativeRotate(
            ByteBuffer byteBuffer, ByteBuffer byteBuffer2, String str);

    static {
        System.loadLibrary("sume_mediabuffer_jni.media.samsung");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: resize, reason: merged with bridge method [inline-methods] */
    public MutableMediaBuffer m9203xd2768214(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        Log.d(TAG, "try to simgp resize: " + ibuf);
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
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("src-cols", ibuf.getCols());
            jsonParams.put("src-rows", ibuf.getRows());
            jsonParams.put("dst-cols", obuf.getCols());
            jsonParams.put("dst-rows", obuf.getRows());
            jsonParams.put(
                    MediaFormat.KEY_COLOR_FORMAT, ibuf.getFormat().getColorFormat().stringfy());
            nativeResize(
                    (ByteBuffer) ibuf.getTypedData(ByteBuffer.class),
                    (ByteBuffer) obuf.getTypedData(ByteBuffer.class),
                    jsonParams.toString());
            return obuf;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cvtColor, reason: merged with bridge method [inline-methods] */
    public MutableMediaBuffer m9205xb5c9ce52(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        Log.d(TAG, "try to simgp cvtColor: " + ibuf + " => " + obuf.getFormat());
        if (obuf.isEmpty()) {
            MutableMediaFormat outputFormat = ibuf.getFormat().toMutableFormat().copy();
            outputFormat.setColorFormat(obuf.getFormat().getColorFormat());
            obuf.put(MediaBuffer.of(outputFormat));
        }
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("cols", obuf.getFormat().getCols());
            jsonParams.put("rows", obuf.getFormat().getRows());
            jsonParams.put("src-color-format", ibuf.getFormat().getColorFormat().stringfy());
            jsonParams.put("dst-color-format", obuf.getFormat().getColorFormat().stringfy());
            nativeCvtColor(
                    (ByteBuffer) ibuf.getTypedData(ByteBuffer.class),
                    (ByteBuffer) obuf.getTypedData(ByteBuffer.class),
                    jsonParams.toString());
            return obuf;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: rotate, reason: merged with bridge method [inline-methods] */
    public MutableMediaBuffer m9204xc4202833(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        Log.d(TAG, "try to simgp rotate: " + ibuf);
        throw new UnsupportedOperationException("not yet implemented");
    }

    static /* synthetic */ MFDescriptor lambda$bindToFixture$0() {
        return new ImgpDescriptor(ImgpPlugin.Type.SIMGP);
    }

    @Override // com.samsung.android.sume.core.plugin.Plugin
    public void bindToFixture(ImgpPlugin fixture) {
        fixture.setDescriptorLoader(
                new DescriptorLoader() { // from class:
                                         // com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.sume.core.functional.DescriptorLoader
                    public final MFDescriptor load() {
                        return SimgpPlugin.lambda$bindToFixture$0();
                    }
                });
        fixture.setImgProcessor(
                ImgpType.RESIZE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda1
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return SimgpPlugin.this.m9203xd2768214(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.ROTATE,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda2
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return SimgpPlugin.this.m9204xc4202833(mediaBuffer, mutableMediaBuffer);
                    }
                });
        fixture.setImgProcessor(
                ImgpType.CVT_COLOR,
                new Operator() { // from class:
                                 // com.samsung.android.sume.core.plugin.SimgpPlugin$$ExternalSyntheticLambda3
                    @Override // com.samsung.android.sume.core.functional.Operator
                    public final MutableMediaBuffer run(
                            MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
                        return SimgpPlugin.this.m9205xb5c9ce52(mediaBuffer, mutableMediaBuffer);
                    }
                });
    }
}
