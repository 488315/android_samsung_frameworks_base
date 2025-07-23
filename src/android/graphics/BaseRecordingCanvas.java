package android.graphics;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.fonts.Font;
import android.graphics.text.MeasuredText;
import android.text.GraphicsOperations;
import android.text.PrecomputedText;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.FastNative;
import java.util.Objects;

/* loaded from: classes.dex */
public class BaseRecordingCanvas extends Canvas {
    @FastNative
    private static native void nDrawArc(long j, float f, float f2, float f3, float f4, float f5, float f6, boolean z, long j2);

    @FastNative
    private static native void nDrawBitmap(long j, long j2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, long j3, int i, int i2);

    @FastNative
    private static native void nDrawBitmap(long j, long j2, float f, float f2, long j3, int i, int i2, int i3);

    @FastNative
    private static native void nDrawBitmap(long j, int[] iArr, int i, int i2, float f, float f2, int i3, int i4, boolean z, long j2);

    @FastNative
    private static native void nDrawBitmapMatrix(long j, long j2, long j3, long j4);

    @FastNative
    private static native void nDrawBitmapMesh(long j, long j2, int i, int i2, float[] fArr, int i3, int[] iArr, int i4, long j3);

    @FastNative
    private static native void nDrawCircle(long j, float f, float f2, float f3, long j2);

    @FastNative
    private static native void nDrawColor(long j, int i, int i2);

    @FastNative
    private static native void nDrawColor(long j, long j2, long j3, int i);

    @FastNative
    private static native void nDrawDoubleRoundRect(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, long j2);

    @FastNative
    private static native void nDrawDoubleRoundRect(long j, float f, float f2, float f3, float f4, float[] fArr, float f5, float f6, float f7, float f8, float[] fArr2, long j2);

    @FastNative
    private static native void nDrawGlyphs(long j, int[] iArr, float[] fArr, int i, int i2, int i3, long j2, long j3);

    @FastNative
    private static native void nDrawLine(long j, float f, float f2, float f3, float f4, long j2);

    @FastNative
    private static native void nDrawLines(long j, float[] fArr, int i, int i2, long j2);

    @FastNative
    private static native void nDrawMesh(long j, long j2, int i, long j3);

    @FastNative
    private static native void nDrawNinePatch(long j, long j2, long j3, float f, float f2, float f3, float f4, long j4, int i, int i2);

    @FastNative
    private static native void nDrawOval(long j, float f, float f2, float f3, float f4, long j2);

    @FastNative
    private static native void nDrawPaint(long j, long j2);

    @FastNative
    private static native void nDrawPath(long j, long j2, long j3);

    @FastNative
    private static native void nDrawPoint(long j, float f, float f2, long j2);

    @FastNative
    private static native void nDrawPoints(long j, float[] fArr, int i, int i2, long j2);

    @FastNative
    private static native void nDrawRect(long j, float f, float f2, float f3, float f4, long j2);

    @FastNative
    private static native void nDrawRegion(long j, long j2, long j3);

    @FastNative
    private static native void nDrawRoundRect(long j, float f, float f2, float f3, float f4, float f5, float f6, long j2);

    @FastNative
    private static native void nDrawText(long j, String str, int i, int i2, float f, float f2, int i3, long j2);

    @FastNative
    private static native void nDrawText(long j, char[] cArr, int i, int i2, float f, float f2, int i3, long j2);

    @FastNative
    private static native void nDrawTextOnPath(long j, String str, long j2, float f, float f2, int i, long j3);

    @FastNative
    private static native void nDrawTextOnPath(long j, char[] cArr, int i, int i2, long j2, float f, float f2, int i3, long j3);

    @FastNative
    private static native void nDrawTextRun(long j, String str, int i, int i2, int i3, int i4, float f, float f2, boolean z, long j2);

    @FastNative
    private static native void nDrawTextRun(long j, char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, long j2, long j3);

    @FastNative
    private static native void nDrawVertices(long j, int i, int i2, float[] fArr, int i3, float[] fArr2, int i4, int[] iArr, int i5, short[] sArr, int i6, int i7, long j2);

    @FastNative
    private static native void nPunchHole(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    public BaseRecordingCanvas(long j) {
        super(j);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawArc(float f, float f2, float f3, float f4, float f5, float f6, boolean z, Paint paint) {
        nDrawArc(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, z, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawArc(RectF rectF, float f, float f2, boolean z, Paint paint) {
        drawArc(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, z, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawARGB(int i, int i2, int i3, int i4) {
        drawColor(Color.argb(i, i2, i3, i4));
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, float f, float f2, Paint paint) {
        throwIfCannotDraw(bitmap);
        nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), f, f2, paint != null ? paint.getNativeInstance() : 0L, this.mDensity, this.mScreenDensity, bitmap.mDensity);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        nDrawBitmapMatrix(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), matrix.ni(), paint != null ? paint.getNativeInstance() : 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, Rect rect, Rect rect2, Paint paint) {
        int i;
        int i2;
        int i3;
        int i4;
        rect2.getClass();
        throwIfCannotDraw(bitmap);
        long nativeInstance = paint == null ? 0L : paint.getNativeInstance();
        if (rect == null) {
            i4 = bitmap.getWidth();
            i3 = bitmap.getHeight();
            i = 0;
            i2 = 0;
        } else {
            i = rect.left;
            int i5 = rect.right;
            i2 = rect.top;
            i3 = rect.bottom;
            i4 = i5;
        }
        nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), i, i2, i4, i3, rect2.left, rect2.top, rect2.right, rect2.bottom, nativeInstance, this.mScreenDensity, bitmap.mDensity);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmap(Bitmap bitmap, Rect rect, RectF rectF, Paint paint) {
        float f;
        float f2;
        float f3;
        float f4;
        rectF.getClass();
        throwIfCannotDraw(bitmap);
        long nativeInstance = paint == null ? 0L : paint.getNativeInstance();
        if (rect == null) {
            f2 = bitmap.getWidth();
            f = bitmap.getHeight();
            f3 = 0.0f;
            f4 = 0.0f;
        } else {
            float f5 = rect.left;
            float f6 = rect.right;
            float f7 = rect.top;
            f = rect.bottom;
            f2 = f6;
            f3 = f5;
            f4 = f7;
        }
        nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), f3, f4, f2, f, rectF.left, rectF.top, rectF.right, rectF.bottom, nativeInstance, this.mScreenDensity, bitmap.mDensity);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawBitmap(int[] iArr, int i, int i2, float f, float f2, int i3, int i4, boolean z, Paint paint) {
        if (i3 < 0) {
            throw new IllegalArgumentException("width must be >= 0");
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("height must be >= 0");
        }
        if (Math.abs(i2) < i3) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int i5 = ((i4 - 1) * i2) + i;
        int length = iArr.length;
        if (i < 0 || i + i3 > length || i5 < 0 || i5 + i3 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (i3 == 0 || i4 == 0) {
            return;
        }
        nDrawBitmap(this.mNativeCanvasWrapper, iArr, i, i2, f, f2, i3, i4, z, paint != null ? paint.getNativeInstance() : 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawBitmap(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, Paint paint) {
        drawBitmap(iArr, i, i2, i3, i4, i5, i6, z, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawBitmapMesh(Bitmap bitmap, int i, int i2, float[] fArr, int i3, int[] iArr, int i4, Paint paint) {
        if ((i | i2 | i3 | i4) < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (i == 0 || i2 == 0) {
            return;
        }
        int i5 = (i + 1) * (i2 + 1);
        checkRange(fArr.length, i3, i5 * 2);
        if (iArr != null) {
            checkRange(iArr.length, i4, i5);
        }
        nDrawBitmapMesh(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), i, i2, fArr, i3, iArr, i4, paint != null ? paint.getNativeInstance() : 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawCircle(float f, float f2, float f3, Paint paint) {
        nDrawCircle(this.mNativeCanvasWrapper, f, f2, f3, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(int i) {
        nDrawColor(this.mNativeCanvasWrapper, i, BlendMode.SRC_OVER.getXfermode().porterDuffMode);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(int i, PorterDuff.Mode mode) {
        nDrawColor(this.mNativeCanvasWrapper, i, mode.nativeInt);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(int i, BlendMode blendMode) {
        nDrawColor(this.mNativeCanvasWrapper, i, blendMode.getXfermode().porterDuffMode);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawColor(long j, BlendMode blendMode) {
        nDrawColor(this.mNativeCanvasWrapper, Color.colorSpace(j).getNativeInstance(), j, blendMode.getXfermode().porterDuffMode);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawLine(float f, float f2, float f3, float f4, Paint paint) {
        nDrawLine(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawLines(float[] fArr, int i, int i2, Paint paint) {
        nDrawLines(this.mNativeCanvasWrapper, fArr, i, i2, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawLines(float[] fArr, Paint paint) {
        drawLines(fArr, 0, fArr.length, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawOval(float f, float f2, float f3, float f4, Paint paint) {
        nDrawOval(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawOval(RectF rectF, Paint paint) {
        rectF.getClass();
        drawOval(rectF.left, rectF.top, rectF.right, rectF.bottom, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPaint(Paint paint) {
        nDrawPaint(this.mNativeCanvasWrapper, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPatch(NinePatch ninePatch, Rect rect, Paint paint) {
        Bitmap bitmap = ninePatch.getBitmap();
        throwIfCannotDraw(bitmap);
        nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), ninePatch.mNativeChunk, rect.left, rect.top, rect.right, rect.bottom, paint == null ? 0L : paint.getNativeInstance(), this.mDensity, ninePatch.getDensity());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPatch(NinePatch ninePatch, RectF rectF, Paint paint) {
        Bitmap bitmap = ninePatch.getBitmap();
        throwIfCannotDraw(bitmap);
        nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), ninePatch.mNativeChunk, rectF.left, rectF.top, rectF.right, rectF.bottom, paint == null ? 0L : paint.getNativeInstance(), this.mDensity, ninePatch.getDensity());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPath(Path path, Paint paint) {
        nDrawPath(this.mNativeCanvasWrapper, path.readOnlyNI(), paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public void drawRegion(Region region, Paint paint) {
        nDrawRegion(this.mNativeCanvasWrapper, region.mNativeRegion, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas
    public final void drawPicture(Picture picture) {
        picture.endRecording();
        int save = save();
        picture.draw(this);
        restoreToCount(save);
    }

    @Override // android.graphics.Canvas
    public final void drawPicture(Picture picture, Rect rect) {
        save();
        translate(rect.left, rect.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(rect.width() / picture.getWidth(), rect.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    @Override // android.graphics.Canvas
    public final void drawPicture(Picture picture, RectF rectF) {
        save();
        translate(rectF.left, rectF.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(rectF.width() / picture.getWidth(), rectF.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPoint(float f, float f2, Paint paint) {
        nDrawPoint(this.mNativeCanvasWrapper, f, f2, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPoints(float[] fArr, int i, int i2, Paint paint) {
        nDrawPoints(this.mNativeCanvasWrapper, fArr, i, i2, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawPoints(float[] fArr, Paint paint) {
        drawPoints(fArr, 0, fArr.length, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawPosText(char[] cArr, int i, int i2, float[] fArr, Paint paint) {
        if (i < 0 || i + i2 > cArr.length || i2 * 2 > fArr.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i3 * 2;
            drawText(cArr, i + i3, 1, fArr[i4], fArr[i4 + 1], paint);
        }
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    @Deprecated
    public final void drawPosText(String str, float[] fArr, Paint paint) {
        drawPosText(str.toCharArray(), 0, str.length(), fArr, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRect(float f, float f2, float f3, float f4, Paint paint) {
        nDrawRect(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRect(Rect rect, Paint paint) {
        drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRect(RectF rectF, Paint paint) {
        nDrawRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRGB(int i, int i2, int i3) {
        drawColor(Color.rgb(i, i2, i3));
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Paint paint) {
        nDrawRoundRect(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawRoundRect(RectF rectF, float f, float f2, Paint paint) {
        drawRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, paint);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawDoubleRoundRect(RectF rectF, float f, float f2, RectF rectF2, float f3, float f4, Paint paint) {
        nDrawDoubleRoundRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, f3, f4, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawDoubleRoundRect(RectF rectF, float[] fArr, RectF rectF2, float[] fArr2, Paint paint) {
        nDrawDoubleRoundRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, fArr, rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, fArr2, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public void drawGlyphs(int[] iArr, int i, float[] fArr, int i2, int i3, Font font, Paint paint) {
        Objects.requireNonNull(iArr, "glyphIds must not be null.");
        Objects.requireNonNull(fArr, "positions must not be null.");
        Objects.requireNonNull(font, "font must not be null.");
        Objects.requireNonNull(paint, "paint must not be null.");
        Preconditions.checkArgumentNonnegative(i3);
        if (i < 0 || i + i3 > iArr.length) {
            throw new IndexOutOfBoundsException("glyphIds must have at least " + (i + i3) + " of elements");
        }
        if (i2 < 0 || (i3 * 2) + i2 > fArr.length) {
            throw new IndexOutOfBoundsException("positions must have at least " + ((i3 * 2) + i2) + " of elements");
        }
        nDrawGlyphs(this.mNativeCanvasWrapper, iArr, fArr, i, i2, i3, font.getNativePtr(), paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(char[] cArr, int i, int i2, float f, float f2, Paint paint) {
        if ((i | i2 | (i + i2) | ((cArr.length - i) - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        nDrawText(this.mNativeCanvasWrapper, cArr, i, i2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(CharSequence charSequence, int i, int i2, float f, float f2, Paint paint) {
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        if ((charSequence instanceof String) || (charSequence instanceof SpannedString) || (charSequence instanceof SpannableString)) {
            nDrawText(this.mNativeCanvasWrapper, charSequence.toString(), i, i2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        if (charSequence instanceof GraphicsOperations) {
            ((GraphicsOperations) charSequence).drawText(this, i, i2, f, f2, paint);
            return;
        }
        char[] obtain = TemporaryBuffer.obtain(i3);
        TextUtils.getChars(charSequence, i, i2, obtain, 0);
        nDrawText(this.mNativeCanvasWrapper, obtain, 0, i3, f, f2, paint.mBidiFlags, paint.getNativeInstance());
        TemporaryBuffer.recycle(obtain);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(String str, float f, float f2, Paint paint) {
        paint.set(paint);
        nDrawText(this.mNativeCanvasWrapper, str, 0, str.length(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawText(String str, int i, int i2, float f, float f2, Paint paint) {
        if ((i | i2 | (i2 - i) | (str.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        nDrawText(this.mNativeCanvasWrapper, str, i, i2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextOnPath(char[] cArr, int i, int i2, Path path, float f, float f2, Paint paint) {
        if (i < 0 || i + i2 > cArr.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        paint.set(paint);
        nDrawTextOnPath(this.mNativeCanvasWrapper, cArr, i, i2, path.readOnlyNI(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextOnPath(String str, Path path, float f, float f2, Paint paint) {
        if (str.length() > 0) {
            paint.set(paint);
            nDrawTextOnPath(this.mNativeCanvasWrapper, str, path.readOnlyNI(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
        }
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextRun(char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        if (cArr == null) {
            throw new NullPointerException("text is null");
        }
        if (paint == null) {
            throw new NullPointerException("paint is null");
        }
        paint.set(paint);
        int i5 = i3 + i4;
        if ((i | i2 | i3 | i4 | (i - i3) | (i5 - (i + i2)) | (cArr.length - i5)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        nDrawTextRun(this.mNativeCanvasWrapper, cArr, i, i2, i3, i4, f, f2, z, paint.getNativeInstance(), 0L);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawTextRun(CharSequence charSequence, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        if (charSequence == null) {
            throw new NullPointerException("text is null");
        }
        if (paint == null) {
            throw new NullPointerException("paint is null");
        }
        int i5 = i - i3;
        int i6 = i2 - i;
        if ((i | i2 | i3 | i4 | i5 | i6 | (i4 - i2) | (charSequence.length() - i4)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        paint.set(paint);
        if ((charSequence instanceof String) || (charSequence instanceof SpannedString) || (charSequence instanceof SpannableString)) {
            nDrawTextRun(this.mNativeCanvasWrapper, charSequence.toString(), i, i2, i3, i4, f, f2, z, paint.getNativeInstance());
            return;
        }
        if (charSequence instanceof GraphicsOperations) {
            ((GraphicsOperations) charSequence).drawTextRun(this, i, i2, i3, i4, f, f2, z, paint);
            return;
        }
        if (charSequence instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText) charSequence;
            int findParaIndex = precomputedText.findParaIndex(i);
            if (i2 <= precomputedText.getParagraphEnd(findParaIndex)) {
                int paragraphStart = precomputedText.getParagraphStart(findParaIndex);
                drawTextRun(precomputedText.getMeasuredParagraph(findParaIndex).getMeasuredText(), i - paragraphStart, i2 - paragraphStart, i3 - paragraphStart, i4 - paragraphStart, f, f2, z, paint);
                return;
            }
        }
        int i7 = i4 - i3;
        char[] obtain = TemporaryBuffer.obtain(i7);
        TextUtils.getChars(charSequence, i3, i4, obtain, 0);
        nDrawTextRun(this.mNativeCanvasWrapper, obtain, i5, i6, 0, i7, f, f2, z, paint.getNativeInstance(), 0L);
        TemporaryBuffer.recycle(obtain);
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public void drawTextRun(MeasuredText measuredText, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        nDrawTextRun(this.mNativeCanvasWrapper, measuredText.getChars(), i, i2 - i, i3, i4 - i3, f, f2, z, paint.getNativeInstance(), measuredText.getNativePtr());
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public final void drawVertices(Canvas.VertexMode vertexMode, int i, float[] fArr, int i2, float[] fArr2, int i3, int[] iArr, int i4, short[] sArr, int i5, int i6, Paint paint) {
        int i7;
        int i8;
        int i9;
        int i10;
        checkRange(fArr.length, i2, i);
        if (fArr2 != null) {
            i7 = i3;
            checkRange(fArr2.length, i7, i);
        } else {
            i7 = i3;
        }
        if (iArr != null) {
            i8 = i4;
            checkRange(iArr.length, i8, i / 2);
        } else {
            i8 = i4;
        }
        if (sArr != null) {
            i9 = i5;
            i10 = i6;
            checkRange(sArr.length, i9, i10);
        } else {
            i9 = i5;
            i10 = i6;
        }
        nDrawVertices(this.mNativeCanvasWrapper, vertexMode.nativeInt, i, fArr, i2, fArr2, i7, iArr, i8, sArr, i9, i10, paint.getNativeInstance());
    }

    @Override // android.graphics.BaseCanvas
    public final void drawMesh(Mesh mesh, BlendMode blendMode, Paint paint) {
        if (blendMode == null) {
            blendMode = BlendMode.MODULATE;
        }
        nDrawMesh(this.mNativeCanvasWrapper, mesh.getNativeWrapperInstance(), blendMode.getXfermode().porterDuffMode, paint.getNativeInstance());
    }

    @Override // android.graphics.BaseCanvas
    public void punchHole(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        nPunchHole(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, f7);
    }
}
