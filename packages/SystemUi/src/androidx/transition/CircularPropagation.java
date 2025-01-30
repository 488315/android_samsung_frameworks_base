package androidx.transition;

import android.graphics.Rect;
import android.view.ViewGroup;
import androidx.transition.Transition;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CircularPropagation extends VisibilityPropagation {
    public final float mPropagationSpeed = 3.0f;

    /* JADX WARN: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0038  */
    @Override // androidx.transition.TransitionPropagation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        Rect onGetEpicenter;
        int round;
        int i2;
        long j;
        Integer num;
        if (transitionValues == null && transitionValues2 == null) {
            return 0L;
        }
        if (transitionValues2 != null) {
            int i3 = 8;
            if (transitionValues != null && (num = (Integer) ((HashMap) transitionValues.values).get("android:visibilityPropagation:visibility")) != null) {
                i3 = num.intValue();
            }
            if (i3 != 0) {
                transitionValues = transitionValues2;
                i = 1;
                int viewCoordinate = VisibilityPropagation.getViewCoordinate(transitionValues, 0);
                int viewCoordinate2 = VisibilityPropagation.getViewCoordinate(transitionValues, 1);
                Transition.EpicenterCallback epicenterCallback = transition.mEpicenterCallback;
                onGetEpicenter = epicenterCallback != null ? null : epicenterCallback.onGetEpicenter();
                if (onGetEpicenter == null) {
                    i2 = onGetEpicenter.centerX();
                    round = onGetEpicenter.centerY();
                } else {
                    viewGroup.getLocationOnScreen(new int[2]);
                    int round2 = Math.round(viewGroup.getTranslationX() + (viewGroup.getWidth() / 2) + r6[0]);
                    round = Math.round(viewGroup.getTranslationY() + (viewGroup.getHeight() / 2) + r6[1]);
                    i2 = round2;
                }
                float f = i2 - viewCoordinate;
                float f2 = round - viewCoordinate2;
                float sqrt = (float) Math.sqrt((f2 * f2) + (f * f));
                float width = viewGroup.getWidth() - 0.0f;
                float height = viewGroup.getHeight() - 0.0f;
                float sqrt2 = sqrt / ((float) Math.sqrt((height * height) + (width * width)));
                j = transition.mDuration;
                if (j < 0) {
                    j = 300;
                }
                return Math.round(((j * i) / this.mPropagationSpeed) * sqrt2);
            }
        }
        i = -1;
        int viewCoordinate3 = VisibilityPropagation.getViewCoordinate(transitionValues, 0);
        int viewCoordinate22 = VisibilityPropagation.getViewCoordinate(transitionValues, 1);
        Transition.EpicenterCallback epicenterCallback2 = transition.mEpicenterCallback;
        if (epicenterCallback2 != null) {
        }
        if (onGetEpicenter == null) {
        }
        float f3 = i2 - viewCoordinate3;
        float f22 = round - viewCoordinate22;
        float sqrt3 = (float) Math.sqrt((f22 * f22) + (f3 * f3));
        float width2 = viewGroup.getWidth() - 0.0f;
        float height2 = viewGroup.getHeight() - 0.0f;
        float sqrt22 = sqrt3 / ((float) Math.sqrt((height2 * height2) + (width2 * width2)));
        j = transition.mDuration;
        if (j < 0) {
        }
        return Math.round(((j * i) / this.mPropagationSpeed) * sqrt22);
    }
}
