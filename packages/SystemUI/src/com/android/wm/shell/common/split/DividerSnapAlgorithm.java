package com.android.wm.shell.common.split;

import android.R;
import android.content.res.Resources;
import android.graphics.Rect;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayLayout;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.stream.IntStream;

/* loaded from: classes3.dex */
public class DividerSnapAlgorithm {
    public final int mDensityDpi;
    public final SnapTarget mDismissEndTarget;
    public final SnapTarget mDismissStartTarget;
    public final int mDisplayDeviceType;
    public final int mDisplayHeight;
    public final int mDisplayWidth;
    public final int mDividerSize;
    public final SnapTarget mFirstSplitTarget;
    public final boolean mFreeSnapMode;
    public final Rect mInsets;
    public final boolean mIsHorizontalDivision;
    public final boolean mIsLeftRightSplit;
    public final SnapTarget mLastSplitTarget;
    public final SnapTarget mMiddleTarget;
    public final float mMinDismissVelocityPxPerSecond;
    public final float mMinFlingVelocityPxPerSecond;
    public final int mMinimalSizeResizableTask;
    public final int mMultiSplitMinimalSize;
    public final int mNaviBarPosition;
    public final int mNaviBarSize;
    public final boolean mParallelMultiSplit;
    public final int mSnapMode;
    public final SnapTarget mStashEndTarget;
    public final SnapTarget mStashStartTarget;
    public final ArrayList mTargets;

    public class SnapTarget {
        public final float distanceMultiplier;
        public final int position;
        public final int snapPosition;

        public SnapTarget(int i, int i2) {
            this(i, i2, 1.0f);
        }

        public SnapTarget(int i, int i2, float f) {
            this.position = i;
            this.snapPosition = i2;
            this.distanceMultiplier = f;
        }
    }

    public DividerSnapAlgorithm(Resources resources, int i, int i2, int i3, boolean z, Rect rect, Rect rect2, int i4) {
        this(resources, i, i2, i3, z, rect, rect2, i4, false, true, false, false);
    }

    public final void addMiddleTarget(boolean z) {
        int i;
        int i2;
        Rect rect = this.mInsets;
        int i3 = z ? rect.left : rect.top;
        if (z) {
            i = rect.right;
            i2 = this.mDisplayWidth;
        } else {
            i = rect.bottom;
            i2 = this.mDisplayHeight;
        }
        this.mTargets.add(new SnapTarget(AbsActionBarView$$ExternalSyntheticOutline0.m(i2 - i, i3, 2, i3) - (this.mDividerSize / 2), 1));
    }

    public final void addNonDismissingTargets(int i, int i2, int i3, boolean z) {
        if (!CoreRune.MW_PARALLEL_MULTI_SPLIT_SUPPORT_RESIZE && this.mParallelMultiSplit) {
            addMiddleTarget(z);
            return;
        }
        int startInset = i - getStartInset();
        int i4 = this.mMinimalSizeResizableTask;
        if (startInset >= i4) {
            this.mTargets.add(new SnapTarget(i, 0));
        }
        addMiddleTarget(z);
        if ((i3 - (this.mIsLeftRightSplit ? this.mInsets.right : this.mInsets.bottom)) - (this.mDividerSize + i2) < i4) {
            return;
        }
        this.mTargets.add(new SnapTarget(i2, 2));
    }

    public final SnapTarget calculateNonDismissingSnapTarget(int i) {
        SnapTarget snapTargetSnap = snap(i, false);
        return snapTargetSnap == this.mDismissStartTarget ? this.mFirstSplitTarget : snapTargetSnap == this.mDismissEndTarget ? this.mLastSplitTarget : snapTargetSnap;
    }

    public final SnapTarget calculateSnapTarget(int i, boolean z) {
        int i2 = this.mFirstSplitTarget.position;
        float f = this.mMinDismissVelocityPxPerSecond;
        if (i < i2 && 0.0f < (-f)) {
            return this.mDismissStartTarget;
        }
        SnapTarget snapTarget = this.mLastSplitTarget;
        return (i <= snapTarget.position || 0.0f <= f) ? Math.abs(0.0f) < this.mMinFlingVelocityPxPerSecond ? snap(i, z) : snapTarget : this.mDismissEndTarget;
    }

    public final int getMinimalSize() {
        return this.mDensityDpi >= ((MultiWindowUtils.isTablet() || (CoreRune.MW_MULTI_SPLIT_FOR_COVER_DISPLAY && this.mDisplayDeviceType == 5)) ? VolteConstants.ErrorCode.BAD_EXTENSION : 460) ? this.mMinimalSizeResizableTask : this.mMultiSplitMinimalSize;
    }

    public final int getStartInset() {
        return this.mIsLeftRightSplit ? this.mInsets.left : this.mInsets.top;
    }

    public final SnapTarget getStashEndTarget() {
        SnapTarget snapTarget = this.mStashEndTarget;
        return snapTarget == null ? this.mLastSplitTarget : snapTarget;
    }

    public final SnapTarget getStashStartTarget() {
        SnapTarget snapTarget = this.mStashStartTarget;
        return snapTarget == null ? this.mFirstSplitTarget : snapTarget;
    }

    public final int getTargetMinimalRatio() {
        float f;
        int i;
        int i2;
        boolean z = this.mIsHorizontalDivision;
        int i3 = this.mMultiSplitMinimalSize;
        if (z) {
            f = i3;
            Rect rect = this.mInsets;
            i = this.mDisplayHeight - rect.top;
            i2 = rect.bottom;
        } else {
            f = i3;
            Rect rect2 = this.mInsets;
            i = this.mDisplayWidth - rect2.left;
            i2 = rect2.right;
        }
        return ((double) (f / ((float) (i - i2)))) >= 0.35d ? 40 : 30;
    }

    public final SnapTarget snap(int i, boolean z) {
        SnapTarget snapTarget;
        SnapTarget snapTarget2;
        SnapTarget snapTarget3;
        if (this.mFreeSnapMode && (snapTarget = this.mFirstSplitTarget) != (snapTarget2 = this.mMiddleTarget) && (snapTarget3 = this.mLastSplitTarget) != snapTarget2 && snapTarget.position < i && i < snapTarget3.position) {
            return new SnapTarget(i, 10);
        }
        int size = this.mTargets.size();
        int i2 = -1;
        float f = Float.MAX_VALUE;
        for (int i3 = 0; i3 < size; i3++) {
            SnapTarget snapTarget4 = (SnapTarget) this.mTargets.get(i3);
            float fAbs = Math.abs(i - snapTarget4.position);
            if (z) {
                fAbs /= snapTarget4.distanceMultiplier;
            }
            if (fAbs < f) {
                i2 = i3;
                f = fAbs;
            }
        }
        return (SnapTarget) this.mTargets.get(i2);
    }

    public DividerSnapAlgorithm(Resources resources, int i, int i2, int i3, boolean z, Rect rect, Rect rect2, int i4, boolean z2, boolean z3, boolean z4, boolean z5) throws Resources.NotFoundException {
        int i5;
        float f;
        ArrayList arrayList = new ArrayList();
        this.mTargets = arrayList;
        Rect rect3 = new Rect();
        this.mInsets = rect3;
        Rect rect4 = new Rect();
        this.mMinFlingVelocityPxPerSecond = resources.getDisplayMetrics().density * 400.0f;
        this.mMinDismissVelocityPxPerSecond = resources.getDisplayMetrics().density * 600.0f;
        this.mDividerSize = i3;
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        this.mIsLeftRightSplit = z;
        rect3.set(rect);
        rect4.set(rect2);
        this.mIsHorizontalDivision = z;
        boolean z6 = CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM;
        if (z6) {
            this.mSnapMode = z2 ? 3 : 20;
        } else {
            this.mSnapMode = z2 ? 3 : 10;
        }
        this.mFreeSnapMode = resources.getBoolean(R.bool.config_dreamsEnabledByDefault);
        float fraction = resources.getFraction(R.fraction.docked_stack_divider_fixed_ratio, 1, 1);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.immersive_mode_cling_width);
        this.mMinimalSizeResizableTask = dimensionPixelSize;
        boolean z7 = resources.getBoolean(R.bool.config_keepDreamingWhenUnplugging);
        int dimensionPixelSize2 = z3 ? resources.getDimensionPixelSize(17106398) : 0;
        this.mDensityDpi = resources.getConfiguration().densityDpi;
        this.mMultiSplitMinimalSize = resources.getDimensionPixelSize(R.dimen.secondary_content_alpha_material_light);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.secondary_rounded_corner_radius);
        int i6 = resources.getConfiguration().semDisplayDeviceType;
        this.mDisplayDeviceType = i6;
        this.mParallelMultiSplit = z4;
        if (!z5) {
            int iNavigationBarPosition = DisplayLayout.navigationBarPosition(resources, i, i2, resources.getConfiguration().windowConfiguration.getRotation());
            this.mNaviBarPosition = iNavigationBarPosition;
            if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY || (CoreRune.MW_MULTI_SPLIT_FOR_COVER_DISPLAY && i6 == 5)) {
                if (z) {
                    this.mNaviBarSize = iNavigationBarPosition == 1 ? rect.left : rect.right;
                } else {
                    this.mNaviBarSize = rect.bottom;
                }
            } else if (z) {
                this.mNaviBarSize = 0;
            } else {
                this.mNaviBarSize = rect.bottom;
            }
        }
        arrayList.clear();
        int i7 = z ? i : i2;
        int i8 = -i3;
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && z4) {
            if (i4 == 3) {
                i8 = rect3.left;
            } else if (i4 == 4) {
                i8 = rect3.top;
            }
        } else if (i4 == 3) {
            i8 += rect3.left;
        }
        arrayList.add(new SnapTarget(i8, 11, 0.35f));
        int i9 = this.mNaviBarSize;
        int i10 = (int) (((i7 - i3) - i9) * 0.1f);
        boolean z8 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        int i11 = this.mNaviBarPosition;
        if (z8 && z5) {
            i5 = i10;
        } else if (z && i11 == 1) {
            i5 = i10;
            arrayList.add(new SnapTarget(i8 + i10 + i9, 14, 0.35f));
        } else {
            i5 = i10;
            arrayList.add(new SnapTarget(i8 + i5, 14, 0.35f));
        }
        int i12 = this.mSnapMode;
        if (i12 == 0) {
            int i13 = z ? rect3.left : rect3.top;
            int i14 = z ? i - rect3.right : i2 - rect3.bottom;
            int iFloor = (int) Math.floor(((z ? i2 - rect3.bottom : i - rect3.right) - (z ? rect3.top : rect3.left)) * 0.5625f);
            addNonDismissingTargets(i13 + iFloor, (i14 - iFloor) - i3, i7, z);
        } else if (i12 == 1) {
            int i15 = z ? rect3.left : rect3.top;
            int i16 = z ? i - rect3.right : i2 - rect3.bottom;
            int iMax = ((int) (fraction * (i16 - i15))) - (i3 / 2);
            iMax = z7 ? Math.max(iMax, dimensionPixelSize) : iMax;
            addNonDismissingTargets(i15 + iMax, (i16 - iMax) - i3, i7, z);
        } else if (i12 == 2) {
            addMiddleTarget(z);
        } else if (i12 == 3) {
            int i17 = dimensionPixelSize2 + rect3.top;
            if (z) {
                if (i4 == 1) {
                    i17 += rect3.left;
                } else if (i4 == 3) {
                    i17 = ((i - i17) - rect3.right) - i3;
                }
            }
            arrayList.add(new SnapTarget(i17, 13));
        } else if (i12 == 4) {
            int i18 = z ? i : i2;
            int i19 = ((int) (0.33f * i18)) - (i3 / 2);
            int asInt = IntStream.of(getStartInset(), z ? rect3.right : rect3.bottom, z ? rect4.left : rect4.top, z ? rect4.right : rect4.bottom).max().getAsInt();
            addNonDismissingTargets(asInt + i19, ((i18 - asInt) - i19) - i3, i7, z);
        } else if (i12 == 10) {
            int i20 = z ? rect3.left : rect3.top;
            int i21 = z ? i - rect3.right : i2 - rect3.bottom;
            int iMax2 = Math.max(Math.round(Math.min(i, i2) * 0.5625f), dimensionPixelSize);
            addNonDismissingTargets(i20 + iMax2, (i21 - iMax2) - i3, i7, z);
        } else if (i12 == 20 && z6) {
            int i22 = z ? rect3.left : rect3.top;
            int i23 = z ? i - rect3.right : i2 - rect3.bottom;
            int minimalSize = getMinimalSize();
            int i24 = i22 + minimalSize;
            int i25 = (i23 - minimalSize) - i3;
            if (i25 - i24 <= dimensionPixelSize3) {
                addMiddleTarget(z);
            } else {
                addNonDismissingTargets(i24, i25, i7, z);
            }
        }
        if (z8 && z5) {
            f = 0.35f;
        } else if (z && i11 == 1) {
            f = 0.35f;
            arrayList.add(new SnapTarget(i7 - i5, 15, 0.35f));
        } else {
            f = 0.35f;
            arrayList.add(new SnapTarget((i7 - i5) - i9, 15, 0.35f));
        }
        arrayList.add(new SnapTarget(i7, 12, f));
        if (z8 && z5) {
            this.mFirstSplitTarget = (SnapTarget) arrayList.get(1);
            this.mLastSplitTarget = (SnapTarget) AlertController$$ExternalSyntheticOutline0.m(2, arrayList);
            this.mDismissStartTarget = (SnapTarget) arrayList.get(0);
            this.mDismissEndTarget = (SnapTarget) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
            this.mStashStartTarget = null;
            this.mStashEndTarget = null;
        } else {
            this.mFirstSplitTarget = (SnapTarget) arrayList.get(2);
            this.mStashStartTarget = (SnapTarget) arrayList.get(1);
            this.mLastSplitTarget = (SnapTarget) AlertController$$ExternalSyntheticOutline0.m(3, arrayList);
            this.mStashEndTarget = (SnapTarget) AlertController$$ExternalSyntheticOutline0.m(2, arrayList);
            this.mDismissStartTarget = (SnapTarget) arrayList.get(0);
            this.mDismissEndTarget = (SnapTarget) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
        }
        SnapTarget snapTarget = (SnapTarget) arrayList.get(arrayList.size() / 2);
        this.mMiddleTarget = snapTarget;
        snapTarget.getClass();
    }
}
