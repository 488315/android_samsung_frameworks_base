package androidx.coordinatorlayout.widget;

import android.graphics.Matrix;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ViewGroupUtils {
    public static final ThreadLocal sMatrix = new ThreadLocal();
    public static final ThreadLocal sRectF = new ThreadLocal();

    private ViewGroupUtils() {
    }

    public static void offsetDescendantMatrix(CoordinatorLayout coordinatorLayout, View view, Matrix matrix) {
        Object parent = view.getParent();
        if ((parent instanceof View) && parent != coordinatorLayout) {
            offsetDescendantMatrix(coordinatorLayout, (View) parent, matrix);
            matrix.preTranslate(-r0.getScrollX(), -r0.getScrollY());
        }
        matrix.preTranslate(view.getLeft(), view.getTop());
        if (view.getMatrix().isIdentity()) {
            return;
        }
        matrix.preConcat(view.getMatrix());
    }
}
