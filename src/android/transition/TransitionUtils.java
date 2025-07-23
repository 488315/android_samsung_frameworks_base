package android.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/* loaded from: classes4.dex */
public class TransitionUtils {
    private static int MAX_IMAGE_SIZE = 1048576;

    static Animator mergeAnimators(Animator animator, Animator animator2) {
        if (animator == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, animator2);
        return animatorSet;
    }

    public static Transition mergeTransitions(Transition... transitionArr) {
        int i = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < transitionArr.length; i3++) {
            if (transitionArr[i3] != null) {
                i2++;
                i = i3;
            }
        }
        if (i2 == 0) {
            return null;
        }
        if (i2 == 1) {
            return transitionArr[i];
        }
        TransitionSet transitionSet = new TransitionSet();
        for (Transition transition : transitionArr) {
            if (transition != null) {
                transitionSet.addTransition(transition);
            }
        }
        return transitionSet;
    }

    public static View copyViewImage(ViewGroup viewGroup, View view, View view2) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(-view2.getScrollX(), -view2.getScrollY());
        view.transformMatrixToGlobal(matrix);
        viewGroup.transformMatrixToLocal(matrix);
        RectF rectF = new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
        matrix.mapRect(rectF);
        int round = Math.round(rectF.left);
        int round2 = Math.round(rectF.top);
        int round3 = Math.round(rectF.right);
        int round4 = Math.round(rectF.bottom);
        ImageView imageView = new ImageView(view.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap createViewBitmap = createViewBitmap(view, matrix, rectF, viewGroup);
        if (createViewBitmap != null) {
            imageView.setImageBitmap(createViewBitmap);
        }
        imageView.measure(View.MeasureSpec.makeMeasureSpec(round3 - round, 1073741824), View.MeasureSpec.makeMeasureSpec(round4 - round2, 1073741824));
        imageView.layout(round, round2, round3, round4);
        return imageView;
    }

    public static Bitmap createDrawableBitmap(Drawable drawable, View view) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return null;
        }
        float min = Math.min(1.0f, MAX_IMAGE_SIZE / (intrinsicWidth * intrinsicHeight));
        if ((drawable instanceof BitmapDrawable) && min == 1.0f) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int i = (int) (intrinsicWidth * min);
        int i2 = (int) (intrinsicHeight * min);
        Picture picture = new Picture();
        Canvas beginRecording = picture.beginRecording(intrinsicWidth, intrinsicHeight);
        Rect bounds = drawable.getBounds();
        int i3 = bounds.left;
        int i4 = bounds.top;
        int i5 = bounds.right;
        int i6 = bounds.bottom;
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(beginRecording);
        drawable.setBounds(i3, i4, i5, i6);
        picture.endRecording();
        return Bitmap.createBitmap(picture);
    }

    public static Bitmap createViewBitmap(View view, Matrix matrix, RectF rectF, ViewGroup viewGroup) {
        int i;
        ViewGroup viewGroup2;
        boolean isAttachedToWindow = view.isAttachedToWindow();
        Bitmap bitmap = null;
        if (isAttachedToWindow) {
            i = 0;
            viewGroup2 = null;
        } else {
            if (viewGroup == null || !viewGroup.isAttachedToWindow()) {
                return null;
            }
            viewGroup2 = (ViewGroup) view.getParent();
            i = viewGroup2.indexOfChild(view);
            viewGroup.getOverlay().add(view);
        }
        int round = Math.round(rectF.width());
        int round2 = Math.round(rectF.height());
        if (round > 0 && round2 > 0) {
            float min = Math.min(1.0f, MAX_IMAGE_SIZE / (round * round2));
            matrix.postTranslate(-rectF.left, -rectF.top);
            matrix.postScale(min, min);
            Picture picture = new Picture();
            Canvas beginRecording = picture.beginRecording((int) (round * min), (int) (round2 * min));
            beginRecording.concat(matrix);
            view.draw(beginRecording);
            picture.endRecording();
            bitmap = Bitmap.createBitmap(picture);
        }
        if (!isAttachedToWindow) {
            viewGroup.getOverlay().remove(view);
            viewGroup2.addView(view, i);
        }
        return bitmap;
    }

    public static class MatrixEvaluator implements TypeEvaluator<Matrix> {
        float[] mTempStartValues = new float[9];
        float[] mTempEndValues = new float[9];
        Matrix mTempMatrix = new Matrix();

        @Override // android.animation.TypeEvaluator
        public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
            matrix.getValues(this.mTempStartValues);
            matrix2.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mTempEndValues;
                float f2 = fArr[i];
                float f3 = this.mTempStartValues[i];
                fArr[i] = f3 + ((f2 - f3) * f);
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }
}
