package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Rect;

/* loaded from: classes.dex */
public interface Canvas {
    /* renamed from: clipPath-mtrdD-E$default, reason: not valid java name */
    static void m454clipPathmtrdDE$default(Canvas canvas, Path path) {
        ClipOp.Companion.getClass();
        canvas.mo425clipPathmtrdDE(path, ClipOp.Intersect);
    }

    /* renamed from: clipRect-mtrdD-E$default, reason: not valid java name */
    static void m455clipRectmtrdDE$default(Canvas canvas, Rect rect) {
        ClipOp.Companion.getClass();
        int i = ClipOp.Intersect;
        canvas.mo426clipRectN_I0leg(rect.left, rect.top, rect.right, rect.bottom, i);
    }

    /* renamed from: clipPath-mtrdD-E */
    void mo425clipPathmtrdDE(Path path, int i);

    /* renamed from: clipRect-N_I0leg */
    void mo426clipRectN_I0leg(float f, float f2, float f3, float f4, int i);

    /* renamed from: concat-58bKbWc */
    void mo427concat58bKbWc(float[] fArr);

    void disableZ();

    void drawArc(float f, float f2, float f3, float f4, float f5, float f6, Paint paint);

    /* renamed from: drawCircle-9KIMszo */
    void mo428drawCircle9KIMszo(float f, long j, Paint paint);

    /* renamed from: drawImage-d-4ec7I */
    void mo429drawImaged4ec7I(ImageBitmap imageBitmap, Paint paint);

    /* renamed from: drawImageRect-HPBpro0 */
    void mo430drawImageRectHPBpro0(ImageBitmap imageBitmap, long j, long j2, long j3, Paint paint);

    /* renamed from: drawLine-Wko1d7g */
    void mo431drawLineWko1d7g(long j, long j2, Paint paint);

    void drawPath(Path path, Paint paint);

    void drawRect(float f, float f2, float f3, float f4, Paint paint);

    void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Paint paint);

    void enableZ();

    void restore();

    void rotate(float f);

    void save();

    void saveLayer(Rect rect, Paint paint);

    void scale(float f, float f2);

    void translate(float f, float f2);
}
