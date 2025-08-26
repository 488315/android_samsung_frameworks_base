package com.google.android.material.motion;

import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class MaterialSideContainerBackHelper extends MaterialBackAnimationHelper {
    public final float maxScaleXDistanceGrow;
    public final float maxScaleXDistanceShrink;
    public final float maxScaleYDistance;

    public MaterialSideContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistanceShrink = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_x_distance_shrink);
        this.maxScaleXDistanceGrow = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_x_distance_grow);
        this.maxScaleYDistance = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_y_distance);
    }

    public void updateBackProgress(float f, boolean z, int i) {
        float interpolation = this.progressInterpolator.getInterpolation(f);
        View view = this.view;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z2 = (Gravity.getAbsoluteGravity(i, view.getLayoutDirection()) & 3) == 3;
        boolean z3 = z == z2;
        int width = this.view.getWidth();
        int height = this.view.getHeight();
        float f2 = width;
        if (f2 > 0.0f) {
            float f3 = height;
            if (f3 <= 0.0f) {
                return;
            }
            float f4 = this.maxScaleXDistanceShrink / f2;
            float f5 = this.maxScaleXDistanceGrow / f2;
            float f6 = this.maxScaleYDistance / f3;
            View view2 = this.view;
            if (z2) {
                f2 = 0.0f;
            }
            view2.setPivotX(f2);
            if (!z3) {
                f5 = -f4;
            }
            float fLerp = AnimationUtils.lerp(0.0f, f5, interpolation);
            float f7 = fLerp + 1.0f;
            this.view.setScaleX(f7);
            float fLerp2 = 1.0f - AnimationUtils.lerp(0.0f, f6, interpolation);
            this.view.setScaleY(fLerp2);
            View view3 = this.view;
            if (view3 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view3;
                for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                    View childAt = viewGroup.getChildAt(i2);
                    childAt.setPivotX(z2 ? childAt.getWidth() + (width - childAt.getRight()) : -childAt.getLeft());
                    childAt.setPivotY(-childAt.getTop());
                    float f8 = z3 ? 1.0f - fLerp : 1.0f;
                    float f9 = fLerp2 != 0.0f ? (f7 / fLerp2) * f8 : 1.0f;
                    childAt.setScaleX(f8);
                    childAt.setScaleY(f9);
                }
            }
        }
    }
}
