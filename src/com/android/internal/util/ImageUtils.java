package com.android.internal.util;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Size;
import com.google.android.mms.ContentType;
import java.io.IOException;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class ImageUtils {
    private static final int ALPHA_TOLERANCE = 50;
    private static final int COMPACT_BITMAP_SIZE = 64;
    private static final int TOLERANCE = 20;
    private int[] mTempBuffer;
    private Bitmap mTempCompactBitmap;
    private Canvas mTempCompactBitmapCanvas;
    private Paint mTempCompactBitmapPaint;
    private final Matrix mTempMatrix = new Matrix();

    public static boolean hasAlpha(int i) {
        return ((i >> 24) & 255) != 255;
    }

    public boolean isGrayscale(Bitmap bitmap) {
        int i;
        int i2;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (height > 64 || width > 64) {
            if (this.mTempCompactBitmap == null) {
                this.mTempCompactBitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888);
                this.mTempCompactBitmapCanvas = new Canvas(this.mTempCompactBitmap);
                Paint paint = new Paint(1);
                this.mTempCompactBitmapPaint = paint;
                paint.setFilterBitmap(true);
            }
            this.mTempMatrix.reset();
            this.mTempMatrix.setScale(64.0f / width, 64.0f / height, 0.0f, 0.0f);
            this.mTempCompactBitmapCanvas.drawColor(0, PorterDuff.Mode.SRC);
            this.mTempCompactBitmapCanvas.drawBitmap(bitmap, this.mTempMatrix, this.mTempCompactBitmapPaint);
            bitmap = this.mTempCompactBitmap;
            i = 64;
            i2 = 64;
        } else {
            i2 = height;
            i = width;
        }
        Bitmap bitmap2 = bitmap;
        int i3 = i2 * i;
        ensureBufferSize(i3);
        bitmap2.getPixels(this.mTempBuffer, 0, i, 0, 0, i, i2);
        boolean z = true;
        for (int i4 = 0; i4 < i3; i4++) {
            if (!isGrayscale(this.mTempBuffer[i4])) {
                return false;
            }
            if (z && hasAlpha(this.mTempBuffer[i4])) {
                z = false;
            }
        }
        return !z;
    }

    private void ensureBufferSize(int i) {
        int[] iArr = this.mTempBuffer;
        if (iArr == null || iArr.length < i) {
            this.mTempBuffer = new int[i];
        }
    }

    public static boolean isGrayscale(int i) {
        if (((i >> 24) & 255) < 50) {
            return true;
        }
        int i2 = (i >> 16) & 255;
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        return Math.abs(i2 - i3) < 20 && Math.abs(i2 - i4) < 20 && Math.abs(i3 - i4) < 20;
    }

    public static Bitmap buildScaledBitmap(Drawable drawable, int i, int i2) {
        return buildScaledBitmap(drawable, i, i2, false);
    }

    public static Bitmap buildScaledBitmap(Drawable drawable, int i, int i2, boolean z) {
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= i && intrinsicHeight <= i2 && (drawable instanceof BitmapDrawable)) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (intrinsicHeight <= 0 || intrinsicWidth <= 0) {
            return null;
        }
        float f = intrinsicWidth;
        float f2 = intrinsicHeight;
        float min = Math.min(i / f, i2 / f2);
        if (!z) {
            min = Math.min(1.0f, min);
        }
        int i3 = (int) (f * min);
        int i4 = (int) (min * f2);
        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, i3, i4);
        drawable.draw(canvas);
        return createBitmap;
    }

    public static int calculateSampleSize(Size size, Size size2) {
        int i = 1;
        if (size.getHeight() <= size2.getHeight() && size.getWidth() <= size2.getWidth()) {
            return 1;
        }
        int height = size.getHeight() / 2;
        int width = size.getWidth() / 2;
        while (height / i >= size2.getHeight() && width / i >= size2.getWidth()) {
            i *= 2;
        }
        return i;
    }

    public static Bitmap loadThumbnail(ContentResolver contentResolver, final Uri uri, final Size size) throws IOException {
        final ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(uri);
        try {
            final Bundle bundle = new Bundle();
            bundle.putParcelable(ContentResolver.EXTRA_SIZE, new Point(size.getWidth(), size.getHeight()));
            Bitmap decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource((Callable<AssetFileDescriptor>) new Callable() { // from class: com.android.internal.util.ImageUtils$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    AssetFileDescriptor openTypedAssetFile;
                    openTypedAssetFile = ContentProviderClient.this.openTypedAssetFile(uri, ContentType.IMAGE_UNSPECIFIED, bundle, null);
                    return openTypedAssetFile;
                }
            }), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.internal.util.ImageUtils$$ExternalSyntheticLambda1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    ImageUtils.lambda$loadThumbnail$1(Size.this, imageDecoder, imageInfo, source);
                }
            });
            if (acquireContentProviderClient != null) {
                acquireContentProviderClient.close();
            }
            return decodeBitmap;
        } catch (Throwable th) {
            if (acquireContentProviderClient != null) {
                try {
                    acquireContentProviderClient.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    static /* synthetic */ void lambda$loadThumbnail$1(Size size, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
        int calculateSampleSize = calculateSampleSize(imageInfo.getSize(), size);
        if (calculateSampleSize > 1) {
            imageDecoder.setTargetSampleSize(calculateSampleSize);
        }
    }
}
