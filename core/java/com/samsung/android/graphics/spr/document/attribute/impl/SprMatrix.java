package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.Matrix;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprMatrix {
  public static Matrix fromSPR(SprInputStream in) throws IOException {
    Matrix matrix = new Matrix();
    float A = in.readFloat();
    float B = in.readFloat();
    float C = in.readFloat();
    float D = in.readFloat();
    float E = in.readFloat();
    float F = in.readFloat();
    matrix.setValues(new float[] {A, B, C, D, E, F, 0.0f, 0.0f, 1.0f});
    return matrix;
  }

  public static void toSPR(DataOutputStream out, Matrix matrix) throws IOException {
    if (matrix == null) {
      out.writeFloat(1.0f);
      out.writeFloat(0.0f);
      out.writeFloat(0.0f);
      out.writeFloat(0.0f);
      out.writeFloat(1.0f);
      out.writeFloat(0.0f);
      return;
    }
    float[] values = new float[9];
    matrix.getValues(values);
    out.writeFloat(values[0]);
    out.writeFloat(values[1]);
    out.writeFloat(values[2]);
    out.writeFloat(values[3]);
    out.writeFloat(values[4]);
    out.writeFloat(values[5]);
  }
}
