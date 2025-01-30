package androidx.transition;

import android.graphics.Rect;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.transition.Transition;
import java.util.HashMap;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SidePropagation extends VisibilityPropagation {
    public final float mPropagationSpeed = 3.0f;
    public int mSide = 80;

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0096, code lost:
    
        if ((androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r19) == 1) != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a7, code lost:
    
        r6 = 3;
        r8 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00aa, code lost:
    
        r6 = 3;
        r8 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00a5, code lost:
    
        if ((androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r19) == 1) != false) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0076  */
    @Override // androidx.transition.TransitionPropagation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        long j;
        Integer num;
        TransitionValues transitionValues3 = transitionValues;
        if (transitionValues3 == null && transitionValues2 == null) {
            return 0L;
        }
        Transition.EpicenterCallback epicenterCallback = transition.mEpicenterCallback;
        Rect onGetEpicenter = epicenterCallback == null ? null : epicenterCallback.onGetEpicenter();
        if (transitionValues2 != null) {
            int i6 = 8;
            if (transitionValues3 != null && (num = (Integer) ((HashMap) transitionValues3.values).get("android:visibilityPropagation:visibility")) != null) {
                i6 = num.intValue();
            }
            if (i6 != 0) {
                transitionValues3 = transitionValues2;
                i = 1;
                int viewCoordinate = VisibilityPropagation.getViewCoordinate(transitionValues3, 0);
                int viewCoordinate2 = VisibilityPropagation.getViewCoordinate(transitionValues3, 1);
                int[] iArr = new int[2];
                viewGroup.getLocationOnScreen(iArr);
                int round = Math.round(viewGroup.getTranslationX()) + iArr[0];
                int round2 = Math.round(viewGroup.getTranslationY()) + iArr[1];
                int width = viewGroup.getWidth() + round;
                int height = viewGroup.getHeight() + round2;
                if (onGetEpicenter == null) {
                    i2 = onGetEpicenter.centerX();
                    i3 = onGetEpicenter.centerY();
                } else {
                    i2 = (round + width) / 2;
                    i3 = (round2 + height) / 2;
                }
                i4 = this.mSide;
                if (i4 != 8388611) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                } else if (i4 == 8388613) {
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                } else {
                    i5 = 3;
                }
                float abs = i4 == i5 ? i4 != 5 ? i4 != 48 ? i4 != 80 ? 0 : Math.abs(i2 - viewCoordinate) + (viewCoordinate2 - round2) : Math.abs(i2 - viewCoordinate) + (height - viewCoordinate2) : Math.abs(i3 - viewCoordinate2) + (viewCoordinate - round) : Math.abs(i3 - viewCoordinate2) + (width - viewCoordinate);
                int i7 = this.mSide;
                float width2 = abs / ((i7 != 3 || i7 == 5 || i7 == 8388611 || i7 == 8388613) ? viewGroup.getWidth() : viewGroup.getHeight());
                j = transition.mDuration;
                if (j < 0) {
                    j = 300;
                }
                return Math.round(((j * i) / this.mPropagationSpeed) * width2);
            }
        }
        i = -1;
        int viewCoordinate3 = VisibilityPropagation.getViewCoordinate(transitionValues3, 0);
        int viewCoordinate22 = VisibilityPropagation.getViewCoordinate(transitionValues3, 1);
        int[] iArr2 = new int[2];
        viewGroup.getLocationOnScreen(iArr2);
        int round3 = Math.round(viewGroup.getTranslationX()) + iArr2[0];
        int round22 = Math.round(viewGroup.getTranslationY()) + iArr2[1];
        int width3 = viewGroup.getWidth() + round3;
        int height2 = viewGroup.getHeight() + round22;
        if (onGetEpicenter == null) {
        }
        i4 = this.mSide;
        if (i4 != 8388611) {
        }
        float abs2 = i4 == i5 ? i4 != 5 ? i4 != 48 ? i4 != 80 ? 0 : Math.abs(i2 - viewCoordinate3) + (viewCoordinate22 - round22) : Math.abs(i2 - viewCoordinate3) + (height2 - viewCoordinate22) : Math.abs(i3 - viewCoordinate22) + (viewCoordinate3 - round3) : Math.abs(i3 - viewCoordinate22) + (width3 - viewCoordinate3);
        int i72 = this.mSide;
        float width22 = abs2 / ((i72 != 3 || i72 == 5 || i72 == 8388611 || i72 == 8388613) ? viewGroup.getWidth() : viewGroup.getHeight());
        j = transition.mDuration;
        if (j < 0) {
        }
        return Math.round(((j * i) / this.mPropagationSpeed) * width22);
    }
}
