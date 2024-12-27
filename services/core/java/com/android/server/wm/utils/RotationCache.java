package com.android.server.wm.utils;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.DisplayCutout;
import android.view.DisplayShape;
import android.view.PrivacyIndicatorBounds;
import android.view.RoundedCorners;

import com.android.server.wm.DisplayContent;
import com.android.server.wm.DisplayContent$$ExternalSyntheticLambda36;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RotationCache {
    public final SparseArray mCache = new SparseArray(4);
    public Object mCachedFor;
    public final DisplayContent$$ExternalSyntheticLambda36 mComputation;

    public RotationCache(
            DisplayContent$$ExternalSyntheticLambda36 displayContent$$ExternalSyntheticLambda36) {
        this.mComputation = displayContent$$ExternalSyntheticLambda36;
    }

    public final Object getOrCompute(int i, Object obj) {
        PrivacyIndicatorBounds $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE;
        if (obj != this.mCachedFor) {
            this.mCache.clear();
            this.mCachedFor = obj;
        }
        int indexOfKey = this.mCache.indexOfKey(i);
        if (indexOfKey >= 0) {
            return this.mCache.valueAt(indexOfKey);
        }
        DisplayContent$$ExternalSyntheticLambda36 displayContent$$ExternalSyntheticLambda36 =
                this.mComputation;
        switch (displayContent$$ExternalSyntheticLambda36.$r8$classId) {
            case 0:
                $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE =
                        DisplayContent.$r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE(
                                displayContent$$ExternalSyntheticLambda36.f$0,
                                (DisplayCutout) obj,
                                i);
                break;
            case 1:
                $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE = (RoundedCorners) obj;
                DisplayContent displayContent = displayContent$$ExternalSyntheticLambda36.f$0;
                displayContent.getClass();
                if ($r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE != null
                        && $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE
                                != RoundedCorners.NO_ROUNDED_CORNERS) {
                    if (i != 0) {
                        boolean z = displayContent.mIsSizeForced;
                        $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE =
                                $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE.rotate(
                                        i,
                                        z
                                                ? displayContent.mBaseDisplayWidth
                                                : displayContent.mInitialDisplayWidth,
                                        z
                                                ? displayContent.mBaseDisplayHeight
                                                : displayContent.mInitialDisplayHeight);
                        break;
                    }
                } else {
                    $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE = RoundedCorners.NO_ROUNDED_CORNERS;
                    break;
                }
                break;
            case 2:
                PrivacyIndicatorBounds privacyIndicatorBounds = (PrivacyIndicatorBounds) obj;
                displayContent$$ExternalSyntheticLambda36.f$0.getClass();
                if (privacyIndicatorBounds != null) {
                    $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE = privacyIndicatorBounds.rotate(i);
                    break;
                } else {
                    $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE =
                            new PrivacyIndicatorBounds(new Rect[4], i);
                    break;
                }
            default:
                $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE = (DisplayShape) obj;
                displayContent$$ExternalSyntheticLambda36.f$0.getClass();
                if ($r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE != null) {
                    if (i != 0) {
                        $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE =
                                $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE.setRotation(i);
                        break;
                    }
                } else {
                    $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE = DisplayShape.NONE;
                    break;
                }
                break;
        }
        this.mCache.put(i, $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE);
        return $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE;
    }
}
