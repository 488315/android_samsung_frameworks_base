package android.view.animation;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class Transformation {
    public static final int TYPE_ALPHA = 1;
    public static final int TYPE_BOTH = 3;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_MATRIX = 2;
    protected float mAlpha;
    private boolean mHasClipRect;
    protected Matrix mMatrix;
    protected int mTransformationType;
    private Rect mClipRect = new Rect();
    private Insets mInsets = Insets.NONE;

    public Transformation() {
        clear();
    }

    public void clear() {
        Matrix matrix = this.mMatrix;
        if (matrix == null) {
            this.mMatrix = new Matrix();
        } else {
            matrix.reset();
        }
        this.mClipRect.setEmpty();
        this.mHasClipRect = false;
        this.mAlpha = 1.0f;
        this.mTransformationType = 3;
        this.mInsets = Insets.NONE;
    }

    public int getTransformationType() {
        return this.mTransformationType;
    }

    public void setTransformationType(int i) {
        this.mTransformationType = i;
    }

    public void set(Transformation transformation) {
        this.mAlpha = transformation.getAlpha();
        this.mMatrix.set(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            setClipRect(transformation.getClipRect());
        } else {
            this.mHasClipRect = false;
            this.mClipRect.setEmpty();
        }
        this.mTransformationType = transformation.getTransformationType();
    }

    public void compose(Transformation transformation) {
        this.mAlpha *= transformation.getAlpha();
        this.mMatrix.preConcat(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            Rect clipRect = transformation.getClipRect();
            if (this.mHasClipRect) {
                setClipRect(this.mClipRect.left + clipRect.left, this.mClipRect.top + clipRect.top, this.mClipRect.right + clipRect.right, this.mClipRect.bottom + clipRect.bottom);
            } else {
                setClipRect(clipRect);
            }
        }
        setInsets(Insets.add(getInsets(), transformation.getInsets()));
    }

    public void postCompose(Transformation transformation) {
        this.mAlpha *= transformation.getAlpha();
        this.mMatrix.postConcat(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            Rect clipRect = transformation.getClipRect();
            if (this.mHasClipRect) {
                setClipRect(this.mClipRect.left + clipRect.left, this.mClipRect.top + clipRect.top, this.mClipRect.right + clipRect.right, this.mClipRect.bottom + clipRect.bottom);
            } else {
                setClipRect(clipRect);
            }
        }
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void setClipRect(Rect rect) {
        setClipRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setClipRect(int i, int i2, int i3, int i4) {
        this.mClipRect.set(i, i2, i3, i4);
        this.mHasClipRect = true;
    }

    public Rect getClipRect() {
        return this.mClipRect;
    }

    public boolean hasClipRect() {
        return this.mHasClipRect;
    }

    public void setInsets(Insets insets) {
        this.mInsets = insets;
    }

    public void setInsets(int i, int i2, int i3, int i4) {
        this.mInsets = Insets.of(i, i2, i3, i4);
    }

    public Insets getInsets() {
        return this.mInsets;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("Transformation");
        toShortString(sb);
        return sb.toString();
    }

    public String toShortString() {
        StringBuilder sb = new StringBuilder(64);
        toShortString(sb);
        return sb.toString();
    }

    public void toShortString(StringBuilder sb) {
        sb.append("{alpha=");
        sb.append(this.mAlpha);
        sb.append(" matrix=");
        sb.append(this.mMatrix.toShortString());
        sb.append('}');
    }

    public void printShortString(PrintWriter printWriter) {
        printWriter.print("{alpha=");
        printWriter.print(this.mAlpha);
        printWriter.print(" matrix=");
        this.mMatrix.dump(printWriter);
        printWriter.print('}');
    }
}
