package com.android.server.companion.datatransfer.contextsync;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;

/* loaded from: classes.dex */
public abstract class BitmapUtils {
  public static byte[] renderDrawableToByteArray(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
      if (bitmap.getWidth() > 256 || bitmap.getHeight() > 256) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
        byte[] renderBitmapToByteArray = renderBitmapToByteArray(createScaledBitmap);
        createScaledBitmap.recycle();
        return renderBitmapToByteArray;
      }
      return renderBitmapToByteArray(bitmap);
    }
    Bitmap createBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
    try {
      Canvas canvas = new Canvas(createBitmap);
      drawable.setBounds(0, 0, createBitmap.getWidth(), createBitmap.getHeight());
      drawable.draw(canvas);
      return renderBitmapToByteArray(createBitmap);
    } finally {
      createBitmap.recycle();
    }
  }

  public static byte[] renderBitmapToByteArray(Bitmap bitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bitmap.getByteCount());
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }
}
