package com.samsung.android.sume.solution.filter;

import android.p009os.BatteryManager;
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
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class NativeImgpFilterAdapter implements Operator {
  private static final String TAG = Def.tagOf((Class<?>) NativeImgpFilterAdapter.class);
  private final NativeUniImgpPlugin plugin;

  public NativeImgpFilterAdapter(
      MediaFormat inputFormat, MediaFormat outputFormat, ColorFormat preferredColorFormat) {
    String str = TAG;
    Log.m94d(str, "inputFormat=" + inputFormat);
    Log.m94d(str, "outputFormat=" + outputFormat);
    Log.m94d(str, "preferred-ColorFormat=" + preferredColorFormat);
    List<ImgpType> opList = new ArrayList<>();
    if (inputFormat != null) {
      if (inputFormat.getMediaType() == MediaType.COMPRESSED_IMAGE) {
        opList.add(ImgpType.DECODE);
      }
      OperatorMap.inferOperations(inputFormat.toMutableFormat(), outputFormat);
    }
    if (outputFormat.contains(BatteryManager.EXTRA_SCALE)) {
      opList.add(ImgpType.RESIZE);
    }
    if (outputFormat.getShape() != null) {
      opList.add(ImgpType.RESIZE);
    }
    if (outputFormat.getCropRect() != null) {
      opList.add(ImgpType.CROP);
    }
    if (preferredColorFormat != null && preferredColorFormat != ColorFormat.NONE) {
      opList.add(ImgpType.CVT_COLOR);
    }
    if (inputFormat != null
        && inputFormat.getColorFormat() != outputFormat.getColorFormat()
        && inputFormat.getColorFormat() != ColorFormat.NONE
        && outputFormat.getColorFormat() != ColorFormat.NONE) {
      opList.add(ImgpType.CVT_COLOR);
    }
    if (outputFormat.getColorSpace() != null && outputFormat.getColorSpace() != ColorSpace.NONE) {
      opList.add(ImgpType.CVT_GAMUT);
    }
    if (outputFormat.getRotation() != 0) {
      opList.add(ImgpType.ROTATE);
    }
    if (outputFormat.getFlipType() != FlipType.NONE) {
      opList.add(ImgpType.FLIP);
    }
    if (outputFormat.getMediaType() == MediaType.COMPRESSED_IMAGE
        && outputFormat.contains("codec-type")) {
      if (outputFormat.contains("encode-hdr")) {
        opList.add(ImgpType.ENCODE_HDR);
      } else {
        opList.add(ImgpType.ENCODE);
      }
    }
    Log.m94d(str, "opList=" + opList.size());
    this.plugin =
        new NativeUniImgpPlugin(
            (List) Objects.requireNonNull(opList), inputFormat, outputFormat, preferredColorFormat);
  }

  @Override // com.samsung.android.sume.core.functional.Operator
  public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf)
      throws UnsupportedOperationException {
    return this.plugin.run(ibuf, obuf);
  }

  public void release() {
    this.plugin.release();
  }
}
