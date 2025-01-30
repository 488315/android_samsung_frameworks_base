package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.transition.Transition;
import com.android.systemui.R;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Explode extends Visibility {
    public final int[] mTempLoc;
    public static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    public static final TimeInterpolator sAccelerate = new AccelerateInterpolator();

    public Explode() {
        this.mTempLoc = new int[2];
        this.mPropagation = new CircularPropagation();
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] iArr = this.mTempLoc;
        View view = transitionValues.view;
        view.getLocationOnScreen(iArr);
        int[] iArr2 = this.mTempLoc;
        int i = iArr2[0];
        int i2 = iArr2[1];
        int width = view.getWidth() + i;
        int height = view.getHeight() + i2;
        ((HashMap) transitionValues.values).put("android:explode:screenBounds", new Rect(i, i2, width, height));
    }

    public final void calculateOut(View view, Rect rect, int[] iArr) {
        int centerX;
        int centerY;
        view.getLocationOnScreen(this.mTempLoc);
        int[] iArr2 = this.mTempLoc;
        int i = iArr2[0];
        int i2 = iArr2[1];
        Transition.EpicenterCallback epicenterCallback = this.mEpicenterCallback;
        Rect onGetEpicenter = epicenterCallback == null ? null : epicenterCallback.onGetEpicenter();
        if (onGetEpicenter == null) {
            centerX = Math.round(view.getTranslationX()) + (view.getWidth() / 2) + i;
            centerY = Math.round(view.getTranslationY()) + (view.getHeight() / 2) + i2;
        } else {
            centerX = onGetEpicenter.centerX();
            centerY = onGetEpicenter.centerY();
        }
        float centerX2 = rect.centerX() - centerX;
        float centerY2 = rect.centerY() - centerY;
        if (centerX2 == 0.0f && centerY2 == 0.0f) {
            centerX2 = ((float) (Math.random() * 2.0d)) - 1.0f;
            centerY2 = ((float) (Math.random() * 2.0d)) - 1.0f;
        }
        float sqrt = (float) Math.sqrt((centerY2 * centerY2) + (centerX2 * centerX2));
        int i3 = centerX - i;
        int i4 = centerY - i2;
        float max = Math.max(i3, view.getWidth() - i3);
        float max2 = Math.max(i4, view.getHeight() - i4);
        float sqrt2 = (float) Math.sqrt((max2 * max2) + (max * max));
        iArr[0] = Math.round((centerX2 / sqrt) * sqrt2);
        iArr[1] = Math.round(sqrt2 * (centerY2 / sqrt));
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Visibility
    public final Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        Rect rect = (Rect) ((HashMap) transitionValues2.values).get("android:explode:screenBounds");
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        calculateOut(viewGroup, rect, this.mTempLoc);
        int[] iArr = this.mTempLoc;
        return TranslationAnimationCreator.createAnimation(view, transitionValues2, rect.left, rect.top, translationX + iArr[0], translationY + iArr[1], translationX, translationY, sDecelerate, this);
    }

    @Override // androidx.transition.Visibility
    public final Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        float f;
        float f2;
        if (transitionValues == null) {
            return null;
        }
        Rect rect = (Rect) ((HashMap) transitionValues.values).get("android:explode:screenBounds");
        int i = rect.left;
        int i2 = rect.top;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        int[] iArr = (int[]) transitionValues.view.getTag(R.id.transition_position);
        if (iArr != null) {
            f = (r8 - rect.left) + translationX;
            f2 = (r1 - rect.top) + translationY;
            rect.offsetTo(iArr[0], iArr[1]);
        } else {
            f = translationX;
            f2 = translationY;
        }
        calculateOut(viewGroup, rect, this.mTempLoc);
        int[] iArr2 = this.mTempLoc;
        return TranslationAnimationCreator.createAnimation(view, transitionValues, i, i2, translationX, translationY, f + iArr2[0], f2 + iArr2[1], sAccelerate, this);
    }

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempLoc = new int[2];
        this.mPropagation = new CircularPropagation();
    }
}
