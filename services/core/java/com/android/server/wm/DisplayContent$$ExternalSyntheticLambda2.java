package com.android.server.wm;

import android.graphics.Matrix;
import android.graphics.Region;

import com.android.internal.util.ToBooleanFunction;
import com.android.server.wm.utils.RegionUtils;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda2
        implements ToBooleanFunction {
    public final /* synthetic */ RecentsAnimationController f$0;
    public final /* synthetic */ Set f$1;
    public final /* synthetic */ Set f$2;
    public final /* synthetic */ Matrix f$3;
    public final /* synthetic */ float[] f$4;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda2(
            RecentsAnimationController recentsAnimationController,
            Set set,
            Set set2,
            Matrix matrix,
            float[] fArr) {
        this.f$0 = recentsAnimationController;
        this.f$1 = set;
        this.f$2 = set2;
        this.f$3 = matrix;
        this.f$4 = fArr;
    }

    public final boolean apply(Object obj) {
        RecentsAnimationController recentsAnimationController = this.f$0;
        Set set = this.f$1;
        Set set2 = this.f$2;
        Matrix matrix = this.f$3;
        float[] fArr = this.f$4;
        WindowState windowState = (WindowState) obj;
        if (recentsAnimationController != null
                && recentsAnimationController.shouldApplyInputConsumer(
                        windowState.mActivityRecord)) {
            return false;
        }
        if (windowState.isVisible() && !windowState.inPinnedWindowingMode()) {
            set.addAll(
                    windowState.getRectsInScreenSpace(windowState.mKeepClearAreas, matrix, fArr));
            set2.addAll(
                    windowState.getRectsInScreenSpace(
                            windowState.mUnrestrictedKeepClearAreas, matrix, fArr));
            if (windowState.mIsImWindow) {
                Region obtain = Region.obtain();
                windowState.getEffectiveTouchableRegion(obtain);
                RegionUtils.forEachRect(
                        obtain, new DisplayContent$$ExternalSyntheticLambda20(1, set2));
                obtain.recycle();
            }
        }
        return windowState.mAttrs.type == 1 && windowState.getWindowingMode() == 1;
    }
}
