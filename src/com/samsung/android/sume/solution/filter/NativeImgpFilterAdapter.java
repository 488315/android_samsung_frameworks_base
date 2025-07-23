package com.samsung.android.sume.solution.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.functional.OperatorMap;
import com.samsung.android.sume.core.plugin.NativeUniImgpPlugin;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ColorSpace;
import com.samsung.android.sume.core.types.FlipType;
import com.samsung.android.sume.core.types.ImgpType;
import com.samsung.android.sume.core.types.MediaType;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class NativeImgpFilterAdapter implements Operator {
    private static final String TAG = Def.tagOf((Class<?>) NativeImgpFilterAdapter.class);
    private final NativeUniImgpPlugin plugin;

    public NativeImgpFilterAdapter(MediaFormat mediaFormat, MediaFormat mediaFormat2, ColorFormat colorFormat) {
        String str = TAG;
        Log.d(str, "inputFormat=" + mediaFormat);
        Log.d(str, "outputFormat=" + mediaFormat2);
        Log.d(str, "preferred-ColorFormat=" + colorFormat);
        ArrayList arrayList = new ArrayList();
        if (mediaFormat != null) {
            if (mediaFormat.getMediaType() == MediaType.COMPRESSED_IMAGE) {
                arrayList.add(ImgpType.DECODE);
            }
            OperatorMap.inferOperations(mediaFormat.toMutableFormat(), mediaFormat2);
        }
        if (mediaFormat2.contains("scale")) {
            arrayList.add(ImgpType.RESIZE);
        }
        if (mediaFormat2.getShape() != null) {
            arrayList.add(ImgpType.RESIZE);
        }
        if (mediaFormat2.getCropRect() != null) {
            arrayList.add(ImgpType.CROP);
        }
        if (colorFormat != null && colorFormat != ColorFormat.NONE) {
            arrayList.add(ImgpType.CVT_COLOR);
        }
        if (mediaFormat != null && mediaFormat.getColorFormat() != mediaFormat2.getColorFormat() && mediaFormat.getColorFormat() != ColorFormat.NONE && mediaFormat2.getColorFormat() != ColorFormat.NONE) {
            arrayList.add(ImgpType.CVT_COLOR);
        }
        if (mediaFormat2.getColorSpace() != null && mediaFormat2.getColorSpace() != ColorSpace.NONE) {
            arrayList.add(ImgpType.CVT_GAMUT);
        }
        if (mediaFormat2.getRotation() != 0) {
            arrayList.add(ImgpType.ROTATE);
        }
        if (mediaFormat2.getFlipType() != FlipType.NONE) {
            arrayList.add(ImgpType.FLIP);
        }
        if (mediaFormat2.getMediaType() == MediaType.COMPRESSED_IMAGE && mediaFormat2.contains("codec-type")) {
            if (mediaFormat2.contains("encode-hdr")) {
                arrayList.add(ImgpType.ENCODE_HDR);
            } else {
                arrayList.add(ImgpType.ENCODE);
            }
        }
        Log.d(str, "opList=" + arrayList.size());
        this.plugin = new NativeUniImgpPlugin(arrayList, mediaFormat, mediaFormat2, colorFormat);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        return this.plugin.run(mediaBuffer, mutableMediaBuffer);
    }

    public void release() {
        this.plugin.release();
    }
}
